package com.zeiterfassung.manager;

import android.content.Context;
import android.util.Xml;

import com.zeiterfassung.model.Appointment;
import com.zeiterfassung.model.AppointmentType;
import com.zeiterfassung.model.Project;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;
import org.xmlpull.v1.XmlSerializer;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.Date;

public class FileManager {
    private static final String xmlFileName = "test.xml";

    public static void readXML(Context context) {
        Exception exception = null;

        FileInputStream fileInputStream = null;
        String xmlData = null;

        File file = new File(context.getFilesDir(), xmlFileName);
        int length = (int) file.length();
        byte[] bytes = new byte[length];

        try {
            fileInputStream = new FileInputStream(file);
            //noinspection ResultOfMethodCallIgnored
            fileInputStream.read(bytes);

            xmlData = new String(bytes);

        } catch (Exception e) {
            e.printStackTrace();
            exception = e;
        } finally {
            try {
                if (fileInputStream != null) {
                    fileInputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
                exception = e;
            }
        }

        if (exception == null) {
            XmlPullParserFactory xmlPullParserFactory;
            XmlPullParser xmlPullParser;

            try {
                xmlPullParserFactory = XmlPullParserFactory.newInstance();
                xmlPullParserFactory.setNamespaceAware(true);
                xmlPullParser = xmlPullParserFactory.newPullParser();
                xmlPullParser.setInput(new StringReader(xmlData));

                int eventType = xmlPullParser.getEventType();

                while (eventType != XmlPullParser.END_DOCUMENT) {
                    if (eventType == XmlPullParser.START_DOCUMENT) {
                        System.out.println("Start document");
                    } else if (eventType == XmlPullParser.START_TAG) {
                        System.out.println("START_TAG: " + xmlPullParser.getName());

                        switch (xmlPullParser.getName()) {
                            case "appointment":
                                try {
                                    AppointmentType appointmentType = AppointmentType.createAndGet(xmlPullParser.getAttributeValue("", "appointmentType"));

                                    Date date = DateTimeManager.stringToDate(xmlPullParser.getAttributeValue("", "date"));
                                    Date fromTime = DateTimeManager.stringToTime(xmlPullParser.getAttributeValue("", "fromTime"));
                                    Date toTime = DateTimeManager.stringToTime(xmlPullParser.getAttributeValue("", "toTime"));
                                    Project project = Project.getList().get(Project.getList().indexOf(new Project(xmlPullParser.getAttributeValue("", "project"))));
                                    String description = xmlPullParser.getAttributeValue("", "description");

                                    Appointment appointment = new Appointment(date, fromTime, toTime, project, appointmentType, description);
                                    Appointment.addItem(appointment);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                                break;
                            case "project":
                                Project project = new Project(xmlPullParser.getAttributeValue("", "name"));
                                Project.getList().add(project);
                                break;
                        }

                    } else if (eventType == XmlPullParser.END_TAG) {
                        System.out.println("END_TAG: " + xmlPullParser.getName());
                    }

                    // TODO: Read Attributes and create Objects

                    eventType = xmlPullParser.next();
                }

            } catch (XmlPullParserException | IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void writeXML(Context context) {
        FileOutputStream outputStream = null;
        StringWriter stringWriter = new StringWriter();
        XmlSerializer serializer = Xml.newSerializer();

        try {
            outputStream = context.openFileOutput(xmlFileName, Context.MODE_PRIVATE);

            serializer.setOutput(stringWriter);
            serializer.startDocument("UTF-8", true);
            serializer.startTag("", "timetracker");
            serializer.startTag("", "projects");

            for (Project project : Project.getList()) {
                serializer.startTag("", "project");
                serializer.attribute("", "name", String.valueOf(project.getName()));
                serializer.endTag("", "project");
            }

            serializer.endTag("", "projects");
            serializer.startTag("", "appointments");

            for (Appointment appointment : Appointment.getList()) {
                serializer.startTag("", "appointment");
                serializer.attribute("", "date", DateTimeManager.dateToString(appointment.getDate()));
                serializer.attribute("", "fromTime", DateTimeManager.timeToString(appointment.getFromTime()));
                serializer.attribute("", "toTime", DateTimeManager.timeToString(appointment.getToTime()));
                serializer.attribute("", "project", String.valueOf(appointment.getProject().getName()));
                serializer.attribute("", "appointmentType", String.valueOf(appointment.getAppointmentType().getId()));
                serializer.attribute("", "description", String.valueOf(appointment.getDescription()));
                serializer.endTag("", "appointment");
            }

            serializer.endTag("", "appointments");
            serializer.endTag("", "timetracker");
            serializer.endDocument();

            serializer.flush();

            String dataWrite = stringWriter.toString();
            outputStream.write(dataWrite.getBytes());

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (outputStream != null) {
                    outputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void deleteXML(Context context) throws Exception {
        File file = new File(context.getFilesDir(), xmlFileName);

        if (file.exists()) {
            boolean deleted = file.delete();

            if (!deleted) {
                throw new Exception("File could not be deleted.");
            }
        }
    }
}