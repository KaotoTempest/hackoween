package hack.idiotproof.Server;

import org.junit.Test;

import java.io.IOException;
import java.util.Collections;

import static org.junit.Assert.*;

public class UIDataStructureTest {
    @Test
    public void testToByteArray() {
        UIDataStructure data = new UIDataStructure(1, "Parent", "0.00",
                Collections.singletonList("Child"),
                UIDataStructure.typeEnum.VALUE);

        byte[] bytes = new byte[0];
        UIDataStructure datab;
        try {
            bytes = data.toByteArray();
            datab = new UIDataStructure(bytes);
            System.out.println(bytes.length);

            assert datab.parent.equals(data.parent);
            assert datab.value.equals(data.value);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}