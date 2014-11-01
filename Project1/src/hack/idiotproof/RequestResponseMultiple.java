package hack.idiotproof;// RequestResponseMultiple.java
import com.bloomberglp.blpapi.CorrelationID;
import com.bloomberglp.blpapi.Element;
import com.bloomberglp.blpapi.Event;
import com.bloomberglp.blpapi.Message;
import com.bloomberglp.blpapi.MessageIterator;
import com.bloomberglp.blpapi.Request;
import com.bloomberglp.blpapi.Service;
import com.bloomberglp.blpapi.Session;
import com.bloomberglp.blpapi.SessionOptions;
public class RequestResponseMultiple {

    public static void main(String[] args) throws Exception {
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
        Service refDataSvc = session.getService("//blp/refdata");
        Request request = refDataSvc.createRequest("ReferenceDataRequest");
        request.getElement("securities").appendValue("AAPL US Equity");
        request.getElement("securities").appendValue("IBM US Equity");
        request.getElement("securities").appendValue("MSFT US Equity");
        request.getElement("fields").appendValue("PX_LAST"); // Last Price
        request.getElement("fields").appendValue("DS002"); // Description
        request.getElement("fields").appendValue("VWAP_VOLUME");
        // Volume used to calculate the Volume Weighted Average Price (VWAP)
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
                String security = securityData.getElementAsString(
                        "security");
                int sequenceNumber =

                        securityData.getElementAsInt32("sequenceNumber");
                if (securityData.hasElement("securityError")) {
                    Element securityError =
                            securityData.getElement("securityError");
                    System.out.println("* security =" + security);
                    //Element securityError = securityData.getElement( "securityError");
                    securityError.print(System.out);
                    return;
                } else {
                    Element fieldData =
                            securityData.getElement("fieldData");
                    double px_last = fieldData.getElementAsFloat64(
                            "PX_LAST");
                    String ds002 = fieldData.getElementAsString(
                            "DS002");
                    double vwap_volume =

                            fieldData.getElementAsFloat64("VWAP_VOLUME");
                    // Individually output each value
                    System.out.println("* security =" + security);
                    System.out.println("* sequenceNumber=" + sequenceNumber);
                    System.out.println("* px_last =" + px_last);
                    System.out.println("* ds002 =" + ds002);
                    System.out.println("* vwap_volume =" + vwap_volume);
                    System.out.println("");
                }
            }
        }
    }
    private static void handleOtherEvent(Event event) throws Exception
    {
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
                    message.messageType().toString()){
                System.out.println("Terminating: " +
                        message.messageType());
                System.exit(1);
            }
        }
    }
}