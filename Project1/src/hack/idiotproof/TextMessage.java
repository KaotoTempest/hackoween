package hack.idiotproof;

import com.twilio.sdk.TwilioRestClient;
import com.twilio.sdk.TwilioRestException;
import com.twilio.sdk.resource.factory.MessageFactory;
import com.twilio.sdk.resource.instance.Message;
import com.twilio.sdk.resource.list.MessageList;
import hack.idiotproof.fhritp.DataTree;
import hack.idiotproof.fhritp.FHRITPRequest;
import hack.idiotproof.fhritp.DataTree;
import hack.idiotproof.fhritp.SingleResponse;
import org.apache.commons.lang.StringUtils;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import javax.xml.soap.Text;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Liam on 01/11/2014.
 */
public class TextMessage implements Runnable  {
    public static final String ACCOUNT_SID = "ACcf384aa58fd8bc648d4588122ba4188c";
    public static final String AUTH_TOKEN = "7d2612d242154add2764ba69b6549ba8";
    private static List<String> processedList = new ArrayList<String>();

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

    public void checkMessages(Date currentTime) throws Exception {


        TwilioRestClient client = new TwilioRestClient(ACCOUNT_SID, AUTH_TOKEN);
        MessageList messages = client.getAccount().getMessages();

        for (Message message : messages) {
            System.out.println("000000000000000000000000000000000" + message.getStatus());
            if (!message.getFrom().equals("+441133202261")) {
                System.out.println(message.getFrom());
                String currentMessage = message.getBody();
                String[] splitString = StringUtils.split(currentMessage);
                List<String> resultStrings = new ArrayList<String>();

                for (String s : splitString) {
                    System.out.println(s);
                    resultStrings.add(s);
                }
                SingleResponse singleResponse = new SingleResponse();


                // String result = history.getHistory(splitString[0]+" "+splitString[1]+" "+splitString[2], splitString[3],) todo drago's bloomburg function

                System.out.println("bing");
               // if(splitString.length > 3)
                this.sendAlertMessage(message.getFrom(),  "MOLOZ");


            }
        }
    }


    public static void main(String args[]){

        System.out.println("Muie");
        TextMessage message = new TextMessage();
        Thread thisThread = new Thread(message);
        thisThread.start();
    }

    @Override
    public void run() {
        while(true) {
            try {
                Date currentTime = new Date(System.currentTimeMillis());
                Thread.sleep(50);
                this.checkMessages(currentTime);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}








