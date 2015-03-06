package tum.toolcloud.server.epcisclient;

import java.io.ByteArrayOutputStream;
import java.io.StringReader;
import java.io.StringWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import javax.xml.namespace.QName;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.soap.MessageFactory;
import javax.xml.soap.MimeHeaders;
import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPBodyElement;
import javax.xml.soap.SOAPConnection;
import javax.xml.soap.SOAPConnectionFactory;
import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPEnvelope;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;
import javax.xml.soap.SOAPPart;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;

import tum.toolcloud.server.idao.IIntakeDAO;
import tum.toolcloud.server.idao.IMachineDAO;
import tum.toolcloud.server.idao.IToolDAO;
import tum.toolcloud.server.model.Intake;
import tum.toolcloud.server.model.Tool;

@Component
@EnableScheduling
public class ClientJob {

	public final static String QUERY_URL = "http://217.110.56.76:80/epc-evo/query?wsdl";
	public static final String QUERY_NAMESPACE = "urn:epcglobal:epcis-query:xsd:1";
	public static final String QUERY_SOAP_ACTION_PREFIX = "/";
	private static final String QUERY_METHOD = "Poll";
	public static int QUERY_MACHINE = 1;
	public static int QUERY_INTAKE = 2;

	private IMachineDAO machineDao;
	private IIntakeDAO intakeDao;
	private IToolDAO toolDao;

	private String machineSinceTime="", intakeSinceTime = "";
	SimpleDateFormat sinceTimeFormat = new SimpleDateFormat(
			"yyyy-MM-dd'T'HH:mm:ss");

	private static Logger logger = Logger.getLogger(ClientJob.class);
	private List<String> machines;
	private List<String> intakes;
	private List<String> tools;

	 @Scheduled(fixedDelayString = "${refresh.rate}")
	public void start() {
		logger.info("Starting a sync cycle");
		machines = machineDao.findIds();
		intakes = intakeDao.findIds();
		tools = toolDao.findIds();

		syncObjects(machines, intakes, tools, QUERY_MACHINE);
		syncObjects(machines, intakes, tools, QUERY_INTAKE);
		logger.info("Finished a sync cycle");
	}

	public void syncObjects(List<String> machines, List<String> intakes,
			List<String> tools, int sync_type) {
		MessageFactory factory;
		try {
			Calendar nowTime = Calendar.getInstance(
					TimeZone.getTimeZone("GMT"), Locale.getDefault());
			factory = MessageFactory.newInstance();

			SOAPMessage message = factory.createMessage();
			SOAPPart soapPart = message.getSOAPPart();
			SOAPEnvelope envelope = soapPart.getEnvelope();
			SOAPBody body = envelope.getBody();

			QName bodyName = new QName(QUERY_NAMESPACE, QUERY_METHOD, "n0");
			SOAPBodyElement bodyElement = body.addBodyElement(bodyName);

			QName queryName = new QName("queryName");
			SOAPElement symbol = bodyElement.addChildElement(queryName);
			symbol.addTextNode("SimpleEventQuery");

			// params

			QName paramsName = new QName("params");
			SOAPElement paramsSymbol = bodyElement.addChildElement(paramsName);

			QName param = new QName("param");
			QName name = new QName("name");
			QName value = new QName("value");
			SOAPElement paramSymbol = paramsSymbol.addChildElement(param);
			SOAPElement nameSymbol = paramSymbol.addChildElement(name);
			SOAPElement valueSymbol = paramSymbol.addChildElement(value);

			nameSymbol.addTextNode("eventType");
			valueSymbol.addTextNode("AggregationEvent");

			QName param2 = new QName("param");
			QName name2 = new QName("name");
			QName value2 = new QName("value");
			SOAPElement paramSymbol2 = paramsSymbol.addChildElement(param2);
			SOAPElement nameSymbol2 = paramSymbol2.addChildElement(name2);
			SOAPElement valueSymbol2 = paramSymbol2.addChildElement(value2);

			nameSymbol2.addTextNode("orderBy");
			valueSymbol2.addTextNode("recordTime");

			QName param3 = new QName("param");
			QName name3 = new QName("name");
			QName value3 = new QName("value");
			QName string = new QName("string");
			SOAPElement paramSymbol3 = paramsSymbol.addChildElement(param3);
			SOAPElement nameSymbol3 = paramSymbol3.addChildElement(name3);
			SOAPElement valueSymbol3 = paramSymbol3.addChildElement(value3);

			nameSymbol3.addTextNode("MATCH_parentID");
			// TODO based on input pick list
			// get the list of parent ids
			List<String> objects;
			String sinceTime="";
			if (sync_type == QUERY_MACHINE) {
				sinceTime = machineSinceTime;
				objects = machines;
			} else {
				objects = intakes;
				sinceTime = intakeSinceTime;
			}

			for (String object : objects) {
				SOAPElement stringSymbol3 = valueSymbol3
						.addChildElement(string);
				stringSymbol3.addTextNode(object);

			}

			if (!sinceTime.isEmpty()) {
				QName param4 = new QName("param");
				QName name4 = new QName("name");
				QName value4 = new QName("value");
				SOAPElement paramSymbol4 = paramsSymbol.addChildElement(param4);
				SOAPElement nameSymbol4 = paramSymbol4.addChildElement(name4);
				SOAPElement valueSymbol4 = paramSymbol4.addChildElement(value4);

				nameSymbol4.addTextNode("GE_eventTime");

				valueSymbol4.addTextNode(sinceTime);

			}
			// -- end of params

			// TODO externalize username and pass
			String authorization = new sun.misc.BASE64Encoder()
					.encode(("epcis:L1wrenceJ").getBytes());
			MimeHeaders hd = message.getMimeHeaders();
			hd.addHeader("Authorization", "Basic " + authorization);
			//
			// System.out.println("----------SOAP Request------------");
			// message.writeTo(System.out);

			SOAPConnectionFactory soapConnectionFactory = SOAPConnectionFactory
					.newInstance();
			SOAPConnection connection = soapConnectionFactory
					.createConnection();
			java.net.URL endpoint = new URL(QUERY_URL);

			ByteArrayOutputStream out = new ByteArrayOutputStream();
			message.writeTo(out);
			String strMsg = new String(out.toByteArray());

			logger.info("Calling " + strMsg);
			SOAPMessage response = connection.call(message, endpoint);
			
			String result = createSoapResponse(response);
			logger.info("response  " + result);
			List<Event> events = parseXML(result);
			logger.info("Read " + events.size() + " events");
			connection.close();
			if (sync_type == QUERY_MACHINE) {
				updateDBMachine(events);
			} else if (sync_type == QUERY_INTAKE) {
				updateDBIntake(events);
			}
			// connection.close();

			if (sync_type == QUERY_INTAKE) { // set based on type
				intakeSinceTime = sinceTimeFormat.format(nowTime.getTime());
				logger.info("setting intake since time to " + intakeSinceTime);
			} else {
				machineSinceTime = sinceTimeFormat.format(nowTime.getTime());
				logger.info("setting machine since time to " + machineSinceTime);
			}

		} catch (SOAPException e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		} catch (MalformedURLException e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		} finally {

		}
	}

	private void updateDBMachine(List<Event> events) {
		// events parents are machines and childs could be tools or intake
		// update the machine id on reversed pattern
		for (int i = 0; i < events.size(); i++) {
			Event currentEvent = events.get(i);
			// System.out.println(currentEvent);
			{ // set id to null
				if (intakes.contains(currentEvent.getChildEPC())) {
					// child is an intake
					Intake intake = new Intake();
					intake.setIntakeId(currentEvent.getChildEPC());
					if (currentEvent.getAction().equals("DELETE")) {
						intake.setMachineId(null);
					} else {
						intake.setMachineId(currentEvent.getParentID());
					}
					intakeDao.update(intake);
					logger.info("update aggregation intake and machine");
				} else if (tools.contains(currentEvent.getChildEPC())) {
					// child is an tool
					Tool tool = new Tool();
					tool.setToolId(currentEvent.getChildEPC());
					if (currentEvent.getAction().equals("DELETE")) {
						tool.setMachineId(null);
					} else {
						tool.setMachineId(currentEvent.getParentID());

					}
					toolDao.updateMachine(tool);
					logger.info("update aggregation tool and machine");
				}

			}
		}

	}

	private void updateDBIntake(List<Event> events) {
		// events parents are intakes are tools
		// update the intake id on reversed pattern
		for (int i = 0; i < events.size(); i++) {
			Event currentEvent = events.get(i);
			// System.out.println(currentEvent);
			{ // set id to null
				if (tools.contains(currentEvent.getChildEPC())) {
					// child is an tool
					Tool tool = new Tool();
					tool.setToolId(currentEvent.getChildEPC());
					if (currentEvent.getAction().equals("DELETE")) {
						tool.setIntakeId(null);
					} else {
						tool.setIntakeId(currentEvent.getParentID());

					}
					// System.out.println(tool);
					toolDao.update(tool);
					logger.info("update aggregation intake and tool");
				}

			}
		}

	}

	private String createSoapResponse(SOAPMessage soapResponse)
			throws Exception {
		TransformerFactory transformerFactory = TransformerFactory
				.newInstance();
		Transformer transformer = transformerFactory.newTransformer();
		Source sourceContent = soapResponse.getSOAPPart().getContent();
		StringWriter sw = new StringWriter();
		StreamResult result = new StreamResult(sw);
		transformer.transform(sourceContent, result);
		StringBuffer sb = sw.getBuffer();
		String finalstring = sb.toString();
//		 System.out.println("\n"+finalstring);
		return finalstring;

	}

	private List<Event> parseXML(String xmlInput) {

		try {

			/** Handling XML */
			SAXParserFactory spf = SAXParserFactory.newInstance();
			SAXParser sp = spf.newSAXParser();
			XMLReader xr = sp.getXMLReader();

			EventXMLHandler myXMLHandler = new EventXMLHandler();
			xr.setContentHandler(myXMLHandler);
			InputSource inStream = new InputSource();

			inStream.setCharacterStream(new StringReader(xmlInput));

			xr.parse(inStream);

			ArrayList<Event> eventsList = myXMLHandler.getEventsList();

			return eventsList;
		} catch (Exception e) {
			logger.error(e.getMessage());
			return null;
		}
	}

	public void setMachineDao(IMachineDAO machineDao) {
		this.machineDao = machineDao;
	}

	public void setIntakeDao(IIntakeDAO intakeDao) {
		this.intakeDao = intakeDao;
	}

	public void setToolDao(IToolDAO toolDao) {
		this.toolDao = toolDao;
	}
}
