package hack.idiotproof.fhritp;

import com.bloomberglp.blpapi.*;

/**
 * Created by Liam on 01/11/2014.
 */
public class SingleResponse {
    public String fetch(String company, String area,String ylabel,String field ) throws Exception {
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
        request.getElement("securities").appendValue(company +" "+area+" "+ylabel);
        request.getElement("fields").appendValue(field);
        // Volume used to calculate the Volume Weighted Average Price (VWAP)
        session.sendRequest(request, new CorrelationID(1));


        boolean continueToLoop = true;
        while (continueToLoop) {
            Event event = session.nextEvent();
            switch (event.eventType().intValue()) {
                case Event.EventType.Constants.RESPONSE: // final response
                    continueToLoop = false; // fall through
                case Event.EventType.Constants.PARTIAL_RESPONSE:
                    return handleResponseEvent(event ,field);

                default:
                    handleOtherEvent(event);
                    break;
            }
        }
        return null;
    }
    private String handleResponseEvent(Event event , String field) throws Exception {
        MessageIterator iter = event.messageIterator();
        String result;
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

                    //Element securityError = securityData.getElement( "securityError");
                    securityError.print(System.out);
                    return "";
                } else {
                    Element fieldData =
                            securityData.getElement("fieldData");
                    double px_last = fieldData.getElementAsFloat64(
                            "PX_LAST");

                    // Individually output each value

                    return field+" = " + px_last;

                }
            }

        }
     return null;
    }
    private static void handleOtherEvent(Event event) throws Exception
    {

        MessageIterator iter = event.messageIterator();
        while (iter.hasNext()) {
            Message message = iter.next();


            if (Event.EventType.Constants.SESSION_STATUS ==
                    event.eventType().intValue()
                    && "SessionTerminated" ==
                    message.messageType().toString()){

                System.exit(1);
            }
        }
    }
}
