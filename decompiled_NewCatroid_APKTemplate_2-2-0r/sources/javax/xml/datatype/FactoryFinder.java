package javax.xml.datatype;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.util.Properties;

/* JADX INFO: loaded from: classes11.dex */
final class FactoryFinder {
    private static final String CLASS_NAME = "javax.xml.datatype.FactoryFinder";
    private static final int DEFAULT_LINE_LENGTH = 80;
    static /* synthetic */ Class class$javax$xml$datatype$FactoryFinder = null;
    private static boolean debug = false;
    private static Properties cacheProps = new Properties();
    private static boolean firstTime = true;

    static class ConfigurationError extends Error {
        private static final long serialVersionUID = -3644413026244211347L;
        private Exception exception;

        ConfigurationError(String str, Exception exc) {
            super(str);
            this.exception = exc;
        }

        Exception getException() {
            return this.exception;
        }
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
    }

    private FactoryFinder() {
    }

    static /* synthetic */ Class class$(String str) {
        try {
            return Class.forName(str);
        } catch (ClassNotFoundException e) {
            throw new NoClassDefFoundError(e.getMessage());
        }
    }

    private static void debugPrintln(String str) {
        if (debug) {
            System.err.println(new StringBuffer("javax.xml.datatype.FactoryFinder:").append(str).toString());
        }
    }

    static Object find(String str, String str2) throws ConfigurationError {
        ClassLoader classLoaderFindClassLoader = findClassLoader();
        try {
            String systemProperty = SecuritySupport.getSystemProperty(str);
            if (systemProperty != null && systemProperty.length() > 0) {
                if (debug) {
                    debugPrintln(new StringBuffer("found ").append(systemProperty).append(" in the system property ").append(str).toString());
                }
                return newInstance(systemProperty, classLoaderFindClassLoader);
            }
        } catch (SecurityException unused) {
        }
        try {
            String string = new StringBuffer().append(SecuritySupport.getSystemProperty("java.home")).append(File.separator).append("lib").append(File.separator).append("jaxp.properties").toString();
            if (firstTime) {
                synchronized (cacheProps) {
                    if (firstTime) {
                        File file = new File(string);
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
            String property = cacheProps.getProperty(str);
            if (debug) {
                debugPrintln(new StringBuffer("found ").append(property).append(" in $java.home/jaxp.properties").toString());
            }
            if (property != null) {
                return newInstance(property, classLoaderFindClassLoader);
            }
        } catch (Exception e) {
            if (debug) {
                e.printStackTrace();
            }
        }
        Object objFindJarServiceProvider = findJarServiceProvider(str);
        if (objFindJarServiceProvider != null) {
            return objFindJarServiceProvider;
        }
        if (str2 == null) {
            throw new ConfigurationError(new StringBuffer("Provider for ").append(str).append(" cannot be found").toString(), null);
        }
        if (debug) {
            debugPrintln(new StringBuffer("loaded from fallback value: ").append(str2).toString());
        }
        return newInstance(str2, classLoaderFindClassLoader);
    }

    private static ClassLoader findClassLoader() throws ConfigurationError {
        ClassLoader contextClassLoader = SecuritySupport.getContextClassLoader();
        if (debug) {
            debugPrintln(new StringBuffer("Using context class loader: ").append(contextClassLoader).toString());
        }
        if (contextClassLoader == null) {
            Class clsClass$ = class$javax$xml$datatype$FactoryFinder;
            if (clsClass$ == null) {
                clsClass$ = class$(CLASS_NAME);
                class$javax$xml$datatype$FactoryFinder = clsClass$;
            }
            contextClassLoader = clsClass$.getClassLoader();
            if (debug) {
                debugPrintln(new StringBuffer("Using the class loader of FactoryFinder: ").append(contextClassLoader).toString());
            }
        }
        return contextClassLoader;
    }

    /* JADX WARN: Code duplicated, block: B:11:0x0026  */
    private static Object findJarServiceProvider(String str) throws ConfigurationError {
        Class clsClass$;
        InputStream resourceAsStream;
        BufferedReader bufferedReader;
        String string = new StringBuffer("META-INF/services/").append(str).toString();
        ClassLoader contextClassLoader = SecuritySupport.getContextClassLoader();
        if (contextClassLoader != null) {
            resourceAsStream = SecuritySupport.getResourceAsStream(contextClassLoader, string);
            if (resourceAsStream == null) {
                clsClass$ = class$javax$xml$datatype$FactoryFinder;
                if (clsClass$ == null) {
                    clsClass$ = class$(CLASS_NAME);
                    class$javax$xml$datatype$FactoryFinder = clsClass$;
                }
                contextClassLoader = clsClass$.getClassLoader();
                resourceAsStream = SecuritySupport.getResourceAsStream(contextClassLoader, string);
            }
        } else {
            clsClass$ = class$javax$xml$datatype$FactoryFinder;
            if (clsClass$ == null) {
                clsClass$ = class$(CLASS_NAME);
                class$javax$xml$datatype$FactoryFinder = clsClass$;
            }
            contextClassLoader = clsClass$.getClassLoader();
            resourceAsStream = SecuritySupport.getResourceAsStream(contextClassLoader, string);
        }
        if (resourceAsStream == null) {
            return null;
        }
        if (debug) {
            debugPrintln(new StringBuffer("found jar resource=").append(string).append(" using ClassLoader: ").append(contextClassLoader).toString());
        }
        try {
            bufferedReader = new BufferedReader(new InputStreamReader(resourceAsStream, "UTF-8"), 80);
        } catch (UnsupportedEncodingException unused) {
            bufferedReader = new BufferedReader(new InputStreamReader(resourceAsStream), 80);
        }
        try {
            try {
                String line = bufferedReader.readLine();
                try {
                    bufferedReader.close();
                } catch (IOException unused2) {
                }
                if (line == null || "".equals(line)) {
                    return null;
                }
                if (debug) {
                    debugPrintln(new StringBuffer("found in resource, value=").append(line).toString());
                }
                return newInstance(line, contextClassLoader);
            } catch (IOException unused3) {
                return null;
            }
        } catch (IOException unused4) {
            bufferedReader.close();
            return null;
        } catch (Throwable th) {
            try {
                bufferedReader.close();
            } catch (IOException unused5) {
            }
            throw th;
        }
    }

    static Object newInstance(String str, ClassLoader classLoader) throws ConfigurationError {
        try {
            Class<?> cls = classLoader == null ? Class.forName(str) : classLoader.loadClass(str);
            if (debug) {
                debugPrintln(new StringBuffer("Loaded ").append(str).append(" from ").append(which(cls)).toString());
            }
            return cls.newInstance();
        } catch (ClassNotFoundException e) {
            throw new ConfigurationError(new StringBuffer("Provider ").append(str).append(" not found").toString(), e);
        } catch (Exception e2) {
            throw new ConfigurationError(new StringBuffer("Provider ").append(str).append(" could not be instantiated: ").append(e2).toString(), e2);
        }
    }

    private static String which(Class cls) {
        try {
            String string = new StringBuffer().append(cls.getName().replace('.', '/')).append(".class").toString();
            ClassLoader classLoader = cls.getClassLoader();
            URL resource = classLoader != null ? classLoader.getResource(string) : ClassLoader.getSystemResource(string);
            return resource != null ? resource.toString() : "unknown location";
        } catch (ThreadDeath e) {
            throw e;
        } catch (VirtualMachineError e2) {
            throw e2;
        } catch (Throwable th) {
            if (!debug) {
                return "unknown location";
            }
            th.printStackTrace();
            return "unknown location";
        }
    }
}
