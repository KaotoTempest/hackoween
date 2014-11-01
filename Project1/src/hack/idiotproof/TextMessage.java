package hack.idiotproof;

import com.twilio.sdk.TwilioRestClient;
import com.twilio.sdk.TwilioRestException;
import com.twilio.sdk.resource.factory.MessageFactory;
import com.twilio.sdk.resource.instance.Message;
import com.twilio.sdk.resource.list.MessageList;
import hack.idiotproof.fhritp.FHRITP;
import hack.idiotproof.fhritp.SingleResponse;
import org.apache.commons.lang.StringUtils;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Liam on 01/11/2014.
 */
public class TextMessage implements Runnable {
    public static final String ACCOUNT_SID = "ACcf384aa58fd8bc648d4588122ba4188c";
    public static final String AUTH_TOKEN = "7d2612d242154add2764ba69b6549ba8";
    private static List<String> processedList = new ArrayList<String>();

    private List<String> messageSIDList;

    public TextMessage() {
        messageSIDList = new ArrayList<>();
        MessageList messages = getMessages();
        for (Message message : messages) {
            messageSIDList.add(message.getSid());
        }


    }

    public void sendAlertMessage(String phoneNumber, String textContent) throws TwilioRestException {
        TwilioRestClient client = new TwilioRestClient(ACCOUNT_SID, AUTH_TOKEN);

        // Build the parameters
        System.out.println("phoneNo:" + phoneNumber + " " + "-" + textContent);
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("To", phoneNumber));
        params.add(new BasicNameValuePair("From", "+441484598166"));
        params.add(new BasicNameValuePair("Body", textContent));

        MessageFactory messageFactory = client.getAccount().getMessageFactory();
        Message message = messageFactory.create(params);

    }

    public void checkMessages() throws Exception {


        MessageList messages = getMessages();

        for (Message message : messages) {
            if (message.getStatus().equals("received") && !messageSIDList.contains(message.getSid())) {

                messageSIDList.add(message.getSid());
                String currentMessage = message.getBody();
                String[] splitString = StringUtils.split(currentMessage);
                List<String> resultStrings = new ArrayList<String>();

                for (String s : splitString) {
                    resultStrings.add(s);
                }
                FHRITP.dataTree.get()

                // if(splitString.length > 3)
                this.sendAlertMessage(message.getFrom(), "MOLOZ");


            }
        }
    }

    private MessageList getMessages() {
        TwilioRestClient client = new TwilioRestClient(ACCOUNT_SID, AUTH_TOKEN);
        return client.getAccount().getMessages();
    }


//    public static void main(String args[]) {
//
//        System.out.println("Muie");
//        TextMessage message = new TextMessage();
//        Thread thisThread = new Thread(message);
//        thisThread.start();
//    }

    @Override
    public void run() {
        while (true) {
            try {
                this.checkMessages();
                Thread.sleep(500);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}








