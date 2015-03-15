package tum.toolcloud.server.epcisclient;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;


/**
 * Created by IBM on 28-Dec-14.
 */
public class EventXMLHandler extends DefaultHandler {
    Boolean currentEvent = false;
    String currentValue = "";
    Event event = null;
    private List<Event> eventsList = new ArrayList();

    public List<Event> getEventsList() {
        return  eventsList;
    }

    // Called when tag starts
    @Override
    public void startElement(String uri, String localName, String qName,
                             Attributes attributes) throws SAXException {
    	

        currentEvent = true;
        currentValue = "";
        if (qName.equals("AggregationEvent")) {
            event = new Event();
        }

    }

    // Called when tag closing
    @Override
    public void endElement(String uri, String localName, String qName)
            throws SAXException {

        currentEvent = false;

        /** set value */


        if (qName.equalsIgnoreCase("eventTime")) {
            event.setEventTime(currentValue);
        } else if (qName.equalsIgnoreCase("recordTime")) {
            event.setRecordTime(currentValue);
        }else if (qName.equalsIgnoreCase("eventTimeZoneOffset")) {
            event.setEventTimeZoneOffset(currentValue);
        }
        else if (qName.equalsIgnoreCase("parentID")) {
            event.setParentID(currentValue);
        }
        else if (qName.equalsIgnoreCase("action")) {
            event.setAction(currentValue);
        }
        else if (qName.equalsIgnoreCase("readPoint")) {
            event.setReadPoint(currentValue);
        }
        else if (qName.equalsIgnoreCase("epc"))
            event.getChildEPCs().add(currentValue);
        else if (qName.equalsIgnoreCase("AggregationEvent"))
        {
            if (event.getChildEPCs().size()>1) {
                for (int i = 0; i < event.getChildEPCs().size() - 1; i++) {
                    // will create same event with no epcs
                    Event newEvent = new Event(event);
                    newEvent.getChildEPCs().add(event.getChildEPCs().get(i));
                    eventsList.add(newEvent);
                }

                String lastEPC = event.getChildEPCs().get(event.getChildEPCs().size() - 1);
                event.getChildEPCs().clear();
                event.getChildEPCs().add(lastEPC);
            }
            eventsList.add(event);

        }





    }

    // Called to get tag characters
    @Override
    public void characters(char[] ch, int start, int length)
            throws SAXException {

        if (currentEvent) {
            currentValue = currentValue +  new String(ch, start, length);
        }

    }
}
