package hack.idiotproof.App;

import hack.idiotproof.App.Components.CircleComponent;
import hack.idiotproof.App.Components.Final;
import hack.idiotproof.App.Components.SpecialComponent;
import hack.idiotproof.fhritp.DataTree;
import hack.idiotproof.fhritp.FHRITPTreeNode;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Dimension2D;
import java.awt.geom.Point2D;
import java.util.*;
import java.util.List;

/**
 * Created by Adam Bedford on 01/11/2014.
 */
public class DesktopApp extends JPanel {
    private FHRITPTreeNode currentElement;

    public DesktopApp(LayoutManager layoutManager, boolean b) {
        super(layoutManager, b);
        this.setBackground(Color.black);
    }

    public DesktopApp(LayoutManager layoutManager) {
        super(layoutManager);
        this.setBackground(Color.black);
    }

    public DesktopApp(boolean b) {
        super(b);
        this.setBackground(Color.black);
    }

    public DesktopApp() {
        this.setBackground(Color.black);
    }

    public static class AnimationState {
        Point2D.Float loc;
        Dimension2D dim;
        int opacity = 255;

        public static AnimationState fromComponent(Component c) {
            AnimationState state = new AnimationState();
            state.dim = new Dimension((int) c.getBounds().getWidth(), (int) c.getBounds().getHeight());
            state.loc = new Point2D.Float((float) c.getBounds().getCenterX(), (float) c.getBounds().getCenterY());
            if (c instanceof SpecialComponent) {
                state.opacity = ((SpecialComponent) c).getAlpha();
            }
            return state;
        }

        public AnimationState setOpacity(int opacity) {
            this.opacity = opacity;
            return this;
        }
    }

    public class Animator implements Runnable {
        Component parent = null;
        List<Pair<Component, Pair<AnimationState, AnimationState>>> map = new LinkedList<>();
        private Final _final;

        public void add(Component c, Pair<AnimationState, AnimationState> states) {
            map.add(new Pair<>(c, states));
        }

        public void setParent(Component parent) {
            this.parent = parent;
        }

        public void addFinal(Final _final) {
            this._final = _final;
        }

        @Override
        public void run() {
            float t = 0;

            while (t < 1) {
                for (Pair<Component, Pair<AnimationState, AnimationState>> pair : map) {
                    Component first = pair.getFirst();

                    AnimationState startState = pair.getSecond().getFirst();
                    AnimationState endState = pair.getSecond().getSecond();

                    int x = (int) (startState.loc.x * (1 - t) + endState.loc.x * t);
                    int y = (int) (startState.loc.y * (1 - t) + endState.loc.y * t);
                    int w = (int) (startState.dim.getWidth() * (1 - t) + endState.dim.getWidth() * t);
                    int h = (int) (startState.dim.getHeight() * (1 - t) + endState.dim.getHeight() * t);

                    if (first instanceof SpecialComponent) {
                        int alpha = (int) (startState.opacity * (1 - t) + endState.opacity * t);
                        ((SpecialComponent) first).setAlpha(alpha);
                    }

                    first.setBounds(x - w / 2, y - h / 2, w, h);
                }

                if (parent != null)
                    parent.repaint();

                try {
                    Thread.sleep(25);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                t += .025;
            }
            _final.doFinal();
        }
    }

    public void setCurrentElement(final FHRITPTreeNode node) {
        currentElement = node;
        final List<Component> allComponents = new LinkedList<>();

        String text = (String) currentElement.getUserObject();
        if(text.contains("=")){
            text = text.split("\\=")[1];
        }

        final CircleComponent circle = new CircleComponent(text);
        circle.setBounds(100, 100, 200, 200);
        allComponents.add(circle);

        removeAll();
        add(circle);

        int childCount = node.getChildCount();

        float orbit = 150;
        float startAngle = -30 * (childCount - 1) / 2;
        float angle = startAngle;

        final AnimationState parentState = new AnimationState();
        parentState.loc = new Point2D.Float(200 - orbit, 200);
        parentState.dim = new Dimension(70, 70);

        if (node.getParent() != null) {
            text = (String) ((FHRITPTreeNode) node.getParent()).getUserObject();
            if(text.contains("=")){
                text = text.split("\\=")[0];
            }

            final CircleComponent parent = new CircleComponent(text);
            parent.setBounds(200 - (int) orbit - 35, 200 - 35, 70, 70);
            add(parent);
            allComponents.add(parent);
            parent.addMouseListener(new MouseListener() {
                @Override
                public void mouseClicked(MouseEvent mouseEvent) {
                    Animator animator = new Animator();
                    animator.setParent(DesktopApp.this);
                    animator.add(parent, new Pair<>(AnimationState.fromComponent(parent), AnimationState.fromComponent(circle)));
                    animator.addFinal(new Final() {
                        public void doFinal() {
                            DesktopApp.this.setCurrentElement((FHRITPTreeNode) node.getParent());
                            DesktopApp.this.repaint();
                        }
                    });
                    for (Component c : allComponents)
                        if (c != parent) {
                            animator.add(c, new Pair<>(AnimationState.fromComponent(c), AnimationState.fromComponent(c).setOpacity(0)));
                        }

                    new Thread(animator).start();
                }

                @Override
                public void mousePressed(MouseEvent mouseEvent) {

                }

                @Override
                public void mouseReleased(MouseEvent mouseEvent) {

                }

                @Override
                public void mouseEntered(MouseEvent mouseEvent) {

                }

                @Override
                public void mouseExited(MouseEvent mouseEvent) {

                }
            });
        }

        for (int idx = 0; idx < childCount; idx++) {
            final FHRITPTreeNode node1 = (FHRITPTreeNode) node.getChildAt(idx);

            text = (String) node1.getUserObject();
            if(text.contains("=")){
                text = text.split("\\=")[0];
            }

            final CircleComponent orb = new CircleComponent(text);
            allComponents.add(orb);
            float x = (float) (orbit * Math.cos(angle * Math.PI / 180));
            float y = (float) (orbit * Math.sin(angle * Math.PI / 180));
            orb.setBounds(200 + (int) x - 35, 200 + (int) y - 35, 70, 70);

            orb.addMouseListener(new MouseListener() {
                @Override
                public void mouseClicked(MouseEvent mouseEvent) {
                    Animator animator = new Animator();
                    animator.setParent(DesktopApp.this);
                    animator.add(orb, new Pair<>(AnimationState.fromComponent(orb), AnimationState.fromComponent(circle)));
                    animator.add(circle, new Pair<>(AnimationState.fromComponent(circle), parentState));
                    animator.addFinal(new Final() {
                        public void doFinal() {
                            DesktopApp.this.setCurrentElement(node1);
                            DesktopApp.this.repaint();
                        }
                    });
                    for (Component c : allComponents)
                        if (c != orb && c != circle) {
                            animator.add(c, new Pair<>(AnimationState.fromComponent(c), AnimationState.fromComponent(c).setOpacity(0)));
                        }

                    new Thread(animator).start();
                }

                @Override
                public void mousePressed(MouseEvent mouseEvent) {

                }

                @Override
                public void mouseReleased(MouseEvent mouseEvent) {

                }

                @Override
                public void mouseEntered(MouseEvent mouseEvent) {

                }

                @Override
                public void mouseExited(MouseEvent mouseEvent) {

                }
            });

            angle += 30;

            add(orb);
        }
    }

    public static void main(String[] args) {
        DataTree tree = new DataTree();

        String[] branch1 = {"Root", "Row1a", "Row2"};
        java.util.List<String> b1 = new LinkedList<>();
        Collections.addAll(b1, branch1);

        String[] branch2 = {"Root", "Row1b", "Row2a"};
        java.util.List<String> b2 = new LinkedList<>();
        Collections.addAll(b2, branch2);

        String[] branch3 = {"Root", "Row1b", "Row2b"};
        java.util.List<String> b3 = new LinkedList<>();
        Collections.addAll(b3, branch3);

        tree.add(b1);
        tree.add(b2);
        tree.add(b3);

        DesktopApp app = new DesktopApp();
        app.setLayout(null);
        FHRITPTreeNode node = new FHRITPTreeNode();
        node.setUserObject("Portfolio");
        FHRITPTreeNode msft = new FHRITPTreeNode("MSFT");
        node.add(msft);
        msft.add(new FHRITPTreeNode("PX_LAST=1.0"));
        msft.add(new FHRITPTreeNode("UK"));
        node.add(new FHRITPTreeNode("IBM"));
        node.add(new FHRITPTreeNode("AAPL"));
        node.add(new FHRITPTreeNode("TOSH"));
        app.setCurrentElement(node);

        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

//        app.addComponentListener(new ComponentListener() {
//            @Override
//            public void componentResized(ComponentEvent componentEvent) {
//                DesktopApp cmp = (DesktopApp) componentEvent.getComponent();
//                for (Component component : cmp.getComponents()) {
//                    if (component instanceof SpecialComponent) {
//                        SpecialComponent c = (SpecialComponent) component;
//
//                        double sx = cmp.getBounds().getWidth() / 400.0;
//                        double sy = cmp.getBounds().getHeight() / 400.0;
//
//                        double s = Math.min(sx, sy);
//
//                        if(s > 0) {
//                            c.setScale(s);
//
//                            Rectangle bounds = c.getBounds();
//                            c.setBounds(new Rectangle((int) (bounds.getX() * s), (int) (bounds.getY() * s),
//                                    (int) (bounds.getWidth() * s), (int) (bounds.getHeight() * s)));
//                        }
//                    }
//                }
//
//            }
//
//            @Override
//            public void componentMoved(ComponentEvent componentEvent) {
//
//            }
//
//            @Override
//            public void componentShown(ComponentEvent componentEvent) {
//
//            }
//
//            @Override
//            public void componentHidden(ComponentEvent componentEvent) {
//
//            }
//        });
        frame.add(app);
        frame.setVisible(true);
        Insets insets = frame.getInsets();
        frame.setSize(400 + insets.left + insets.right, 400 + insets.top + insets.bottom);
    }
}
