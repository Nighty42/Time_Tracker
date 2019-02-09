package com.zeiterfassung.manager;

import android.content.Context;
import android.util.Xml;

import com.zeiterfassung.model.Appointment;
import com.zeiterfassung.model.Project;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;
import org.xmlpull.v1.XmlSerializer;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.StringReader;

public class FileManager {
    private static final String fileName = "test.csv";
    private static final String xmlFileName = "test.xml";

    public static void read(Context context) {
        FileInputStream fileInputStream = null;
        InputStreamReader inputStreamReader = null;
        BufferedReader bufferedReader = null;
        StringBuilder stringBuilder = new StringBuilder();
        String line;

        try {
            fileInputStream = context.openFileInput(fileName);
            inputStreamReader = new InputStreamReader(fileInputStream);
            bufferedReader = new BufferedReader(inputStreamReader);

            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line);
                Project.addItem(new Project(line));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (bufferedReader != null) {
                    bufferedReader.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            try {
                if (inputStreamReader != null) {
                    inputStreamReader.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            try {
                if (fileInputStream != null) {
                    fileInputStream.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void write(Context context) {
        FileOutputStream outputStream = null;
        OutputStreamWriter outputStreamWriter = null;

        try {
            outputStream = context.openFileOutput(fileName, Context.MODE_PRIVATE);
            outputStreamWriter = new OutputStreamWriter(outputStream);

            for (Project project : Project.getList()) {
                outputStreamWriter.append(project.toString());
                outputStreamWriter.append("\n\r");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (outputStreamWriter != null) {
                    outputStreamWriter.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            try {
                if (outputStream != null) {
                    outputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void readXML(Context context) {
        Exception exception = null;

        FileInputStream inputStream = null;
        InputStreamReader inputStreamReader = null;
        XmlSerializer serializer = Xml.newSerializer();
        String xmlData = null;

        try {
            inputStream = context.openFileInput(xmlFileName);
            inputStreamReader = new InputStreamReader(inputStream);

            char[] buffer = new char[inputStream.available()];
            // inputStreamReader.read(buffer);
            xmlData = new String(buffer);

        } catch (Exception e) {
            e.printStackTrace();
            exception = e;
        } finally {
            try {
                if (inputStreamReader != null) {
                    inputStreamReader.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
                exception = e;
            }

            try {
                if (inputStream != null) {
                    inputStream.close();
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
        OutputStreamWriter outputStreamWriter = null;
        XmlSerializer serializer = Xml.newSerializer();

        try {
            outputStream = context.openFileOutput(xmlFileName, Context.MODE_PRIVATE);
            outputStreamWriter = new OutputStreamWriter(outputStream);

            serializer.setOutput(outputStreamWriter);
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
                serializer.attribute("", "date", String.valueOf(appointment.getDate()));
                serializer.attribute("", "fromTime", String.valueOf(appointment.getFromTime()));
                serializer.attribute("", "toTime", String.valueOf(appointment.getToTime()));
                serializer.attribute("", "project", String.valueOf(appointment.getProject().getName()));
                serializer.attribute("", "appointmentType", String.valueOf(appointment.getAppointmentType().getId()));
                serializer.attribute("", "description", String.valueOf(appointment.getDescription()));
                serializer.endTag("", "appointment");
            }

            serializer.endTag("", "appointments");
            serializer.endTag("", "timetracker");
            serializer.endDocument();

            serializer.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (outputStreamWriter != null) {
                    outputStreamWriter.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            try {
                if (outputStream != null) {
                    outputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}