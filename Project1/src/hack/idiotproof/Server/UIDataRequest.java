package hack.idiotproof.Server;

import java.io.*;

/**
 * Created by Adam Bedford on 01/11/2014.
 */
public class UIDataRequest {
    public enum SubEnum{
        NEW_ENTITY,
        NEW_FIELD,
        REQUEST
    }

    public SubEnum submissionType;
    public String value;

    public UIDataRequest(SubEnum submissionType, String value) {
        this.submissionType = submissionType;
        this.value = value;
    }

    public UIDataRequest(byte[] bytes) throws IOException {
        ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
        DataInputStream dis = new DataInputStream(bais);
        submissionType = SubEnum.values()[dis.readInt()];
        value = dis.readUTF();
    }

    public byte[] toByteArray() throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(baos);

        dos.writeInt(submissionType.ordinal());
        dos.writeUTF(value);

        return baos.toByteArray();
    }
}
