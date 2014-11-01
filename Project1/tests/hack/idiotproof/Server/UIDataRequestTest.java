package hack.idiotproof.Server;

import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.*;

public class UIDataRequestTest {
    @Test
    public void testToByteArray() {
        UIDataRequest data = new UIDataRequest(UIDataRequest.SubEnum.NEW_ENTITY, "MSFT");

        try {
            byte[] bytes = data.toByteArray();
            UIDataRequest data2 = new UIDataRequest(bytes);

            assert data2.value.equals(data.value);
            assert data2.submissionType.ordinal() == data.submissionType.ordinal();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}