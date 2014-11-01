package hack.idiotproof;

import com.twilio.sdk.TwilioRestClient;
import com.twilio.sdk.TwilioRestException;
import com.twilio.sdk.resource.factory.MessageFactory;
import com.twilio.sdk.resource.instance.Message;
import com.twilio.sdk.resource.list.MessageList;
import org.apache.commons.lang.StringUtils;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Liam on 01/11/2014.
 */
public class TextMessage {
    public static final String ACCOUNT_SID = "AC27668090d13ae2272dae65523783e067";
    public static final String AUTH_TOKEN = "abf7cccad59c70d66d2351baf81fc478";
    private static List<String> processedList = new ArrayList<String>();

    public void sendAlertMessage(String phoneNumber, String textContent) throws TwilioRestException {
        TwilioRestClient client = new TwilioRestClient(ACCOUNT_SID, AUTH_TOKEN);

        // Build the parameters
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("To", phoneNumber));
        params.add(new BasicNameValuePair("From", "+441133202261"));
        params.add(new BasicNameValuePair("Body", textContent));

        MessageFactory messageFactory = client.getAccount().getMessageFactory();
        Message message = messageFactory.create(params);

    }

    public void checkMessages(){
        TextMessage textMessage = new TextMessage();
        TwilioRestClient client = new TwilioRestClient(ACCOUNT_SID, AUTH_TOKEN);
        MessageList messages = client.getAccount().getMessages();

        for (Message message : messages) {
            if (!message.getFrom().equals("+441133202261") && !processedList.contains(message.getSid())) {
                String currentMessage = message.getBody();
                String[] splitString = StringUtils.split(currentMessage);
                if (splitString.length == 4) {
                    // String result = history.getHistory(splitString[0]+" "+splitString[1]+" "+splitString[2], splitString[3],) todo drago's bloomburg function

                    //textMessage.sendAlertMessage(message.getFrom(),"You've provided a valid field good job!");
                    processedList.add(message.getSid());

                }
            }
        }

    }

    public static void main(String args[]) throws TwilioRestException {


    }
}




