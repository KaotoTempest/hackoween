package hack.idiotproof.Server;

import java.io.*;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Adam Bedford on 01/11/2014.
 */
public class UIDataStructure {
    public enum typeEnum {
        VALUE,
        CHART
    }

    public long UID;
    public String parent;
    public String value;
    public List<String> children;
    public typeEnum type;

    public UIDataStructure(long UID, String parent, String value, List<String> children, typeEnum type) {
        this.UID = UID;
        this.parent = parent;
        this.value = value;
        this.children = children;
        this.type = type;
    }

    public UIDataStructure(byte[] bytes) throws IOException {
        ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
        DataInputStream dis = new DataInputStream(bais);

        UID = dis.readLong();
        parent = dis.readUTF();
        value = dis.readUTF();
        int noChildren = dis.readInt();
        children = new LinkedList<>();
        while (noChildren-- > 0) {
            children.add(dis.readUTF());
        }
        type = typeEnum.values()[dis.readInt()];
    }

    public byte[] toByteArray() throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(baos);

        dos.writeLong(UID);
        dos.writeUTF(parent);
        dos.writeUTF(value);
        dos.writeInt(children.size());

        for (String child : children) {
            dos.writeUTF(child);
        }

        dos.writeInt(type.ordinal());

        return baos.toByteArray();
    }
}
