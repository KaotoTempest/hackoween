package hack.idiotproof;

import com.twilio.sdk.TwilioRestClient;
import com.twilio.sdk.TwilioRestException;
import com.twilio.sdk.resource.factory.MessageFactory;
import com.twilio.sdk.resource.instance.Message;
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


    public void sendUpdate(String phoneNumber, String textContent) throws TwilioRestException {
        TwilioRestClient client = new TwilioRestClient(ACCOUNT_SID, AUTH_TOKEN);

        // Build the parameters
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("To", phoneNumber));
        params.add(new BasicNameValuePair("From", "+441133202261"));
        params.add(new BasicNameValuePair("Body", textContent));

        MessageFactory messageFactory = client.getAccount().getMessageFactory();
        Message message = messageFactory.create(params);

    }

    public static void main(String args[]) throws TwilioRestException {

        TextMessage message = new TextMessage();
        message.sendUpdate("07563240071", "#FHRITP");
    }

}
