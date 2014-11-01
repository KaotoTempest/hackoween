package hack.idiotproof.fhritp;

import com.bloomberglp.blpapi.*;

import java.io.IOException;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by dragosmc on 11/1/2014.
 */

public class FHRITP {

    private static History history;

    public static void main(String[] args) throws Exception {

        history = new History();
        Session session = getSession();

        Service refDataSvc = session.getService("//blp/refdata");
        String dataRequest = "ReferenceDataRequest";
        String element = "securities";
        String value = "IBM US Index";
        List<String> fields = Collections.singletonList("PX_LAST");

        Request request = constructRequest(refDataSvc, dataRequest, element, value, fields);

        session.sendRequest(request, new CorrelationID(1));
        boolean continueToLoop = true;
        while (continueToLoop) {
            Event event = session.nextEvent();
            switch (event.eventType().intValue()) {
                case Event.EventType.Constants.RESPONSE: // final response
                    continueToLoop = false; // fall through
                case Event.EventType.Constants.PARTIAL_RESPONSE:
                    handleResponseEvent(event);
                    break;
                default:
                    handleOtherEvent(event);
                    break;
            }
        }
    }

    // value would be in the format SYMBOLOGY [EXCHANGE] <Key>
    // E.g: IBM US Equity
    // List of keys at page 78

    //todo: list of fields
    private static Request constructRequest(Service refDataSvc, String dataRequest, String element, String value, List<String> fields) {
        Request request = refDataSvc.createRequest(dataRequest);

        request.getElement(element).appendValue(value);

        for (String field : fields) {
            request.getElement("fields").appendValue(field);
        }

        return request;
    }

    private static Session getSession() throws IOException, InterruptedException {
        SessionOptions sessionOptions = new SessionOptions();
        sessionOptions.setServerHost("10.8.8.1");
        sessionOptions.setServerPort(8194);

        Session session = new Session(sessionOptions);

        if (!session.start()) {
            System.out.println("Could not start session.");
            System.exit(1);
        }
        if (!session.openService("//blp/refdata")) {
            System.out.println("Could not open service " +
                    "//blp/refdata");
            System.exit(1);
        }

        return session;
    }

    private static void handleResponseEvent(Event event) throws Exception {
        MessageIterator iter = event.messageIterator();
        while (iter.hasNext()) {
            Message message = iter.next();
            Element ReferenceDataResponse = message.asElement();
            if (ReferenceDataResponse.hasElement("responseError")) {
                System.exit(1);
            }
            Element securityDataArray =
                    ReferenceDataResponse.getElement("securityData");
            int numItems = securityDataArray.numValues();
            for (int i = 0; i < numItems; ++i) {
                Element securityData = securityDataArray.getValueAsElement(i);
                String security = securityData.getElementAsString("security");
                if (securityData.hasElement("securityError")) {
                    Element securityError = securityData.getElement("securityError");
                    System.out.println("* security =" + security);
                    //Element securityError = securityData.getElement( "securityError");
                    securityError.print(System.out);
                    return;
                } else {
                    Element fieldData =
                            securityData.getElement("fieldData");

                    ElementIterator elementIterator = fieldData.elementIterator();
                    while (elementIterator.hasNext()) {
                        Element element = elementIterator.next();
                       // history.add(security, element.name().toString(), element.getValueAsString());
                        //System.out.println( element.name().toString() + ": : :" + element.getValueAsString());

                    }
                }
            }
        }
        System.out.println(history.getLastValue("IBM US Index", "PX_LAST"));
    }

    private static void handleOtherEvent(Event event) throws Exception {
        System.out.println("EventType=" + event.eventType());
        MessageIterator iter = event.messageIterator();
        while (iter.hasNext()) {
            Message message = iter.next();
            System.out.println("correlationID=" +
                    message.correlationID());
            System.out.println("messageType=" + message.messageType());
            message.print(System.out);
            if (Event.EventType.Constants.SESSION_STATUS ==
                    event.eventType().intValue()
                    && "SessionTerminated" ==
                    message.messageType().toString()) {
                System.out.println("Terminating: " +
                        message.messageType());
                System.exit(1);
            }
        }
    }
}
