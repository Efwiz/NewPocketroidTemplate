package org.xml.sax.helpers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

/* JADX INFO: loaded from: classes3.dex */
public final class XMLReaderFactory {
    private static final int DEFAULT_LINE_LENGTH = 80;
    static /* synthetic */ Class class$org$xml$sax$helpers$XMLReaderFactory = null;
    private static final String property = "org.xml.sax.driver";

    private XMLReaderFactory() {
    }

    static /* synthetic */ Class class$(String str) {
        try {
            return Class.forName(str);
        } catch (ClassNotFoundException e) {
            throw new NoClassDefFoundError(e.getMessage());
        }
    }

    /* JADX WARN: Code duplicated, block: B:20:0x0031  */
    public static XMLReader createXMLReader() throws SAXException {
        String systemProperty;
        Class clsClass$;
        InputStream resourceAsStream;
        BufferedReader bufferedReader;
        ClassLoader classLoader = NewInstance.getClassLoader();
        String line = null;
        try {
            systemProperty = SecuritySupport.getSystemProperty(property);
        } catch (Exception unused) {
            systemProperty = null;
        }
        if (systemProperty == null || systemProperty.length() == 0) {
            ClassLoader contextClassLoader = SecuritySupport.getContextClassLoader();
            if (contextClassLoader != null) {
                resourceAsStream = SecuritySupport.getResourceAsStream(contextClassLoader, "META-INF/services/org.xml.sax.driver");
                if (resourceAsStream == null) {
                    clsClass$ = class$org$xml$sax$helpers$XMLReaderFactory;
                    if (clsClass$ == null) {
                        clsClass$ = class$("org.xml.sax.helpers.XMLReaderFactory");
                        class$org$xml$sax$helpers$XMLReaderFactory = clsClass$;
                    }
                    resourceAsStream = SecuritySupport.getResourceAsStream(clsClass$.getClassLoader(), "META-INF/services/org.xml.sax.driver");
                }
            } else {
                clsClass$ = class$org$xml$sax$helpers$XMLReaderFactory;
                if (clsClass$ == null) {
                    clsClass$ = class$("org.xml.sax.helpers.XMLReaderFactory");
                    class$org$xml$sax$helpers$XMLReaderFactory = clsClass$;
                }
                resourceAsStream = SecuritySupport.getResourceAsStream(clsClass$.getClassLoader(), "META-INF/services/org.xml.sax.driver");
            }
            if (resourceAsStream != null) {
                try {
                    bufferedReader = new BufferedReader(new InputStreamReader(resourceAsStream, "UTF-8"), 80);
                } catch (UnsupportedEncodingException unused2) {
                    bufferedReader = new BufferedReader(new InputStreamReader(resourceAsStream), 80);
                }
                try {
                    line = bufferedReader.readLine();
                } catch (Exception unused3) {
                } catch (Throwable th) {
                    try {
                        bufferedReader.close();
                    } catch (IOException unused4) {
                    }
                    throw th;
                }
                try {
                    bufferedReader.close();
                } catch (IOException unused5) {
                }
            }
        } else {
            line = systemProperty;
        }
        if (line == null) {
            line = "org.apache.xerces.parsers.SAXParser";
        }
        if (line != null) {
            return loadClass(classLoader, line);
        }
        try {
            return new ParserAdapter(ParserFactory.makeParser());
        } catch (Exception unused6) {
            throw new SAXException("Can't create default XMLReader; is system property org.xml.sax.driver set?");
        }
    }

    public static XMLReader createXMLReader(String str) throws SAXException {
        return loadClass(NewInstance.getClassLoader(), str);
    }

    private static XMLReader loadClass(ClassLoader classLoader, String str) throws SAXException {
        try {
            return (XMLReader) NewInstance.newInstance(classLoader, str);
        } catch (ClassCastException e) {
            throw new SAXException(new StringBuffer("SAX2 driver class ").append(str).append(" does not implement XMLReader").toString(), e);
        } catch (ClassNotFoundException e2) {
            throw new SAXException(new StringBuffer("SAX2 driver class ").append(str).append(" not found").toString(), e2);
        } catch (IllegalAccessException e3) {
            throw new SAXException(new StringBuffer("SAX2 driver class ").append(str).append(" found but cannot be loaded").toString(), e3);
        } catch (InstantiationException e4) {
            throw new SAXException(new StringBuffer("SAX2 driver class ").append(str).append(" loaded but cannot be instantiated (no empty public constructor?)").toString(), e4);
        }
    }
}
