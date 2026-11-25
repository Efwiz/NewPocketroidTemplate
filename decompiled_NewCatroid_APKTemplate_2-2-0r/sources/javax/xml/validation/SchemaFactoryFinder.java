package javax.xml.validation;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Properties;
import name.antonsmirnov.firmata.FormatHelper;

/* JADX INFO: loaded from: classes11.dex */
final class SchemaFactoryFinder {
    private static final int DEFAULT_LINE_LENGTH = 80;
    private static final Class SERVICE_CLASS;
    private static final String SERVICE_ID;
    private static final String W3C_XML_SCHEMA10_NS_URI = "http://www.w3.org/XML/XMLSchema/v1.0";
    private static final String W3C_XML_SCHEMA11_NS_URI = "http://www.w3.org/XML/XMLSchema/v1.1";
    static /* synthetic */ Class class$javax$xml$validation$SchemaFactory = null;
    private static boolean debug = false;
    private final ClassLoader classLoader;
    private static Properties cacheProps = new Properties();
    private static boolean firstTime = true;

    private static abstract class SingleIterator implements Iterator {
        private boolean seen;

        private SingleIterator() {
            this.seen = false;
        }

        @Override // java.util.Iterator
        public final boolean hasNext() {
            return !this.seen;
        }

        @Override // java.util.Iterator
        public final Object next() {
            if (this.seen) {
                throw new NoSuchElementException();
            }
            this.seen = true;
            return value();
        }

        @Override // java.util.Iterator
        public final void remove() {
            throw new UnsupportedOperationException();
        }

        protected abstract Object value();
    }

    static {
        boolean z = true;
        try {
            String systemProperty = SecuritySupport.getSystemProperty("jaxp.debug");
            if (systemProperty == null || "false".equals(systemProperty)) {
                z = false;
            }
            debug = z;
        } catch (Exception unused) {
            debug = false;
        }
        Class clsClass$ = class$javax$xml$validation$SchemaFactory;
        if (clsClass$ == null) {
            clsClass$ = class$("javax.xml.validation.SchemaFactory");
            class$javax$xml$validation$SchemaFactory = clsClass$;
        }
        SERVICE_CLASS = clsClass$;
        SERVICE_ID = new StringBuffer("META-INF/services/").append(clsClass$.getName()).toString();
    }

    public SchemaFactoryFinder(ClassLoader classLoader) {
        this.classLoader = classLoader;
        if (debug) {
            debugDisplayClassLoader();
        }
    }

    private SchemaFactory _newFactory(String str) {
        String str2;
        SchemaFactory schemaFactoryCreateInstance;
        String string = new StringBuffer().append(SERVICE_CLASS.getName()).append(":").append(str).toString();
        try {
            if (debug) {
                debugPrintln(new StringBuffer("Looking up system property '").append(string).append(FormatHelper.QUOTE).toString());
            }
            String systemProperty = SecuritySupport.getSystemProperty(string);
            if (systemProperty != null && systemProperty.length() > 0) {
                if (debug) {
                    debugPrintln(new StringBuffer("The value is '").append(systemProperty).append(FormatHelper.QUOTE).toString());
                }
                SchemaFactory schemaFactoryCreateInstance2 = createInstance(systemProperty);
                if (schemaFactoryCreateInstance2 != null) {
                    return schemaFactoryCreateInstance2;
                }
            } else if (debug) {
                debugPrintln("The property is undefined.");
            }
        } catch (ThreadDeath e) {
            throw e;
        } catch (VirtualMachineError e2) {
            throw e2;
        } catch (Throwable th) {
            if (debug) {
                debugPrintln(new StringBuffer("failed to look up system property '").append(string).append(FormatHelper.QUOTE).toString());
                th.printStackTrace();
            }
        }
        String string2 = new StringBuffer().append(SecuritySupport.getSystemProperty("java.home")).append(File.separator).append("lib").append(File.separator).append("jaxp.properties").toString();
        try {
            if (firstTime) {
                synchronized (cacheProps) {
                    if (firstTime) {
                        File file = new File(string2);
                        firstTime = false;
                        if (SecuritySupport.doesFileExist(file)) {
                            if (debug) {
                                debugPrintln(new StringBuffer("Read properties file ").append(file).toString());
                            }
                            cacheProps.load(SecuritySupport.getFileInputStream(file));
                        }
                    }
                }
            }
            String property = cacheProps.getProperty(string);
            if (debug) {
                debugPrintln(new StringBuffer("found ").append(property).append(" in $java.home/jaxp.properties").toString());
            }
            if (property != null && (schemaFactoryCreateInstance = createInstance(property)) != null) {
                return schemaFactoryCreateInstance;
            }
        } catch (Exception e3) {
            if (debug) {
                e3.printStackTrace();
            }
        }
        Iterator itCreateServiceFileIterator = createServiceFileIterator();
        while (itCreateServiceFileIterator.hasNext()) {
            URL url = (URL) itCreateServiceFileIterator.next();
            if (debug) {
                debugPrintln(new StringBuffer("looking into ").append(url).toString());
            }
            try {
                SchemaFactory schemaFactoryLoadFromServicesFile = loadFromServicesFile(str, url.toExternalForm(), SecuritySupport.getURLInputStream(url));
                if (schemaFactoryLoadFromServicesFile != null) {
                    return schemaFactoryLoadFromServicesFile;
                }
            } catch (IOException e4) {
                if (debug) {
                    debugPrintln(new StringBuffer("failed to read ").append(url).toString());
                    e4.printStackTrace();
                }
            }
        }
        if (str.equals("http://www.w3.org/2001/XMLSchema") || str.equals(W3C_XML_SCHEMA10_NS_URI)) {
            if (debug) {
                debugPrintln("attempting to use the platform default XML Schema 1.0 validator");
            }
            str2 = "org.apache.xerces.jaxp.validation.XMLSchemaFactory";
        } else {
            if (!str.equals(W3C_XML_SCHEMA11_NS_URI)) {
                if (!debug) {
                    return null;
                }
                debugPrintln("all things were tried, but none was found. bailing out.");
                return null;
            }
            if (debug) {
                debugPrintln("attempting to use the platform default XML Schema 1.1 validator");
            }
            str2 = "org.apache.xerces.jaxp.validation.XMLSchema11Factory";
        }
        return createInstance(str2);
    }

    static /* synthetic */ Class class$(String str) {
        try {
            return Class.forName(str);
        } catch (ClassNotFoundException e) {
            throw new NoClassDefFoundError(e.getMessage());
        }
    }

    private Iterator createServiceFileIterator() {
        ClassLoader classLoader = this.classLoader;
        if (classLoader == null) {
            return new SingleIterator() { // from class: javax.xml.validation.SchemaFactoryFinder.1
                static /* synthetic */ Class class$javax$xml$validation$SchemaFactoryFinder;

                static /* synthetic */ Class class$(String str) {
                    try {
                        return Class.forName(str);
                    } catch (ClassNotFoundException e) {
                        throw new NoClassDefFoundError(e.getMessage());
                    }
                }

                @Override // javax.xml.validation.SchemaFactoryFinder.SingleIterator
                protected Object value() {
                    Class clsClass$ = class$javax$xml$validation$SchemaFactoryFinder;
                    if (clsClass$ == null) {
                        clsClass$ = class$("javax.xml.validation.SchemaFactoryFinder");
                        class$javax$xml$validation$SchemaFactoryFinder = clsClass$;
                    }
                    return SecuritySupport.getResourceAsURL(clsClass$.getClassLoader(), SchemaFactoryFinder.SERVICE_ID);
                }
            };
        }
        try {
            String str = SERVICE_ID;
            final Enumeration resources = SecuritySupport.getResources(classLoader, str);
            if (debug && !resources.hasMoreElements()) {
                debugPrintln(new StringBuffer("no ").append(str).append(" file was found").toString());
            }
            return new Iterator() { // from class: javax.xml.validation.SchemaFactoryFinder.2
                @Override // java.util.Iterator
                public boolean hasNext() {
                    return resources.hasMoreElements();
                }

                @Override // java.util.Iterator
                public Object next() {
                    return resources.nextElement();
                }

                @Override // java.util.Iterator
                public void remove() {
                    throw new UnsupportedOperationException();
                }
            };
        } catch (IOException e) {
            if (debug) {
                debugPrintln(new StringBuffer("failed to enumerate resources ").append(SERVICE_ID).toString());
                e.printStackTrace();
            }
            return new ArrayList().iterator();
        }
    }

    private void debugDisplayClassLoader() {
        try {
            if (this.classLoader == SecuritySupport.getContextClassLoader()) {
                debugPrintln(new StringBuffer("using thread context class loader (").append(this.classLoader).append(") for search").toString());
                return;
            }
        } catch (ThreadDeath e) {
            throw e;
        } catch (VirtualMachineError e2) {
            throw e2;
        } catch (Throwable unused) {
        }
        debugPrintln((this.classLoader == ClassLoader.getSystemClassLoader() ? new StringBuffer("using system class loader (") : new StringBuffer("using class loader (")).append(this.classLoader).append(") for search").toString());
    }

    private static void debugPrintln(String str) {
        if (debug) {
            System.err.println(new StringBuffer("JAXP: ").append(str).toString());
        }
    }

    private SchemaFactory loadFromServicesFile(String str, String str2, InputStream inputStream) {
        BufferedReader bufferedReader;
        SchemaFactory schemaFactoryCreateInstance;
        if (debug) {
            debugPrintln(new StringBuffer("Reading ").append(str2).toString());
        }
        try {
            bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"), 80);
            while (true) {
                try {
                    String line = bufferedReader.readLine();
                    if (line == null) {
                        schemaFactoryCreateInstance = null;
                        break;
                    }
                    int iIndexOf = line.indexOf(35);
                    if (iIndexOf != -1) {
                        line = line.substring(0, iIndexOf);
                    }
                    String strTrim = line.trim();
                    if (strTrim.length() != 0) {
                        try {
                            schemaFactoryCreateInstance = createInstance(strTrim);
                            if (schemaFactoryCreateInstance.isSchemaLanguageSupported(str)) {
                                break;
                            }
                        } catch (Exception unused) {
                            continue;
                        }
                    }
                } catch (IOException unused2) {
                }
            }
        } catch (UnsupportedEncodingException unused3) {
            bufferedReader = new BufferedReader(new InputStreamReader(inputStream), 80);
        }
        try {
            bufferedReader.close();
        } catch (IOException unused4) {
        }
        return schemaFactoryCreateInstance;
    }

    private static String which(Class cls) {
        return which(cls.getName(), cls.getClassLoader());
    }

    private static String which(String str, ClassLoader classLoader) {
        String string = new StringBuffer().append(str.replace('.', '/')).append(".class").toString();
        if (classLoader == null) {
            classLoader = ClassLoader.getSystemClassLoader();
        }
        URL resourceAsURL = SecuritySupport.getResourceAsURL(classLoader, string);
        if (resourceAsURL != null) {
            return resourceAsURL.toString();
        }
        return null;
    }

    SchemaFactory createInstance(String str) {
        try {
            if (debug) {
                debugPrintln(new StringBuffer("instanciating ").append(str).toString());
            }
            ClassLoader classLoader = this.classLoader;
            Class<?> clsLoadClass = classLoader != null ? classLoader.loadClass(str) : Class.forName(str);
            if (debug) {
                debugPrintln(new StringBuffer("loaded it from ").append(which(clsLoadClass)).toString());
            }
            Object objNewInstance = clsLoadClass.newInstance();
            if (objNewInstance instanceof SchemaFactory) {
                return (SchemaFactory) objNewInstance;
            }
            if (!debug) {
                return null;
            }
            debugPrintln(new StringBuffer().append(str).append(" is not assignable to ").append(SERVICE_CLASS.getName()).toString());
            return null;
        } catch (ThreadDeath e) {
            throw e;
        } catch (VirtualMachineError e2) {
            throw e2;
        } catch (Throwable th) {
            debugPrintln(new StringBuffer("failed to instanciate ").append(str).toString());
            if (!debug) {
                return null;
            }
            th.printStackTrace();
            return null;
        }
    }

    public SchemaFactory newFactory(String str) {
        StringBuffer stringBufferAppend;
        str.getClass();
        SchemaFactory schemaFactory_newFactory = _newFactory(str);
        if (debug) {
            if (schemaFactory_newFactory != null) {
                stringBufferAppend = new StringBuffer("factory '");
                stringBufferAppend = stringBufferAppend.append(schemaFactory_newFactory.getClass().getName()).append("' was found for ");
            } else {
                stringBufferAppend = new StringBuffer("unable to find a factory for ");
            }
            debugPrintln(stringBufferAppend.append(str).toString());
        }
        return schemaFactory_newFactory;
    }
}
