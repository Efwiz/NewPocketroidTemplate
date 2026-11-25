package javax.xml.transform;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.Properties;

/* JADX INFO: loaded from: classes11.dex */
final class FactoryFinder {
    private static final int DEFAULT_LINE_LENGTH = 80;
    static /* synthetic */ Class class$javax$xml$transform$FactoryFinder = null;
    private static boolean debug = false;
    private static Properties cacheProps = new Properties();
    private static boolean firstTime = true;

    static class ConfigurationError extends Error {
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
        } catch (SecurityException unused) {
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

    private static void dPrint(String str) {
        if (debug) {
            System.err.println(new StringBuffer("JAXP: ").append(str).toString());
        }
    }

    static Object find(String str, String str2) throws ConfigurationError {
        ClassLoader contextClassLoader = SecuritySupport.getContextClassLoader();
        if (contextClassLoader == null) {
            Class clsClass$ = class$javax$xml$transform$FactoryFinder;
            if (clsClass$ == null) {
                clsClass$ = class$("javax.xml.transform.FactoryFinder");
                class$javax$xml$transform$FactoryFinder = clsClass$;
            }
            contextClassLoader = clsClass$.getClassLoader();
        }
        if (debug) {
            dPrint(new StringBuffer("find factoryId =").append(str).toString());
        }
        try {
            String systemProperty = SecuritySupport.getSystemProperty(str);
            if (systemProperty != null && systemProperty.length() > 0) {
                if (debug) {
                    dPrint(new StringBuffer("found system property, value=").append(systemProperty).toString());
                }
                return newInstance(systemProperty, contextClassLoader, true);
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
                                dPrint(new StringBuffer("Read properties file ").append(file).toString());
                            }
                            cacheProps.load(SecuritySupport.getFileInputStream(file));
                        }
                    }
                }
            }
            String property = cacheProps.getProperty(str);
            if (property != null) {
                if (debug) {
                    dPrint(new StringBuffer("found in $java.home/jaxp.properties, value=").append(property).toString());
                }
                return newInstance(property, contextClassLoader, true);
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
            dPrint(new StringBuffer("loaded from fallback value: ").append(str2).toString());
        }
        return newInstance(str2, contextClassLoader, true);
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
                clsClass$ = class$javax$xml$transform$FactoryFinder;
                if (clsClass$ == null) {
                    clsClass$ = class$("javax.xml.transform.FactoryFinder");
                    class$javax$xml$transform$FactoryFinder = clsClass$;
                }
                contextClassLoader = clsClass$.getClassLoader();
                resourceAsStream = SecuritySupport.getResourceAsStream(contextClassLoader, string);
            }
        } else {
            clsClass$ = class$javax$xml$transform$FactoryFinder;
            if (clsClass$ == null) {
                clsClass$ = class$("javax.xml.transform.FactoryFinder");
                class$javax$xml$transform$FactoryFinder = clsClass$;
            }
            contextClassLoader = clsClass$.getClassLoader();
            resourceAsStream = SecuritySupport.getResourceAsStream(contextClassLoader, string);
        }
        if (resourceAsStream == null) {
            return null;
        }
        if (debug) {
            dPrint(new StringBuffer("found jar resource=").append(string).append(" using ClassLoader: ").append(contextClassLoader).toString());
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
                    dPrint(new StringBuffer("found in resource, value=").append(line).toString());
                }
                return newInstance(line, contextClassLoader, false);
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

    /* JADX WARN: Code duplicated, block: B:4:0x0006 A[Catch: ClassNotFoundException -> 0x000b, Exception -> 0x0012, PHI: r5
      0x0006: PHI (r5v12 java.lang.ClassLoader) = (r5v0 java.lang.ClassLoader), (r5v6 java.lang.ClassLoader) binds: [B:3:0x0004, B:18:0x0027] A[DONT_GENERATE, DONT_INLINE], TRY_ENTER, TRY_LEAVE, TryCatch #1 {ClassNotFoundException -> 0x000b, blocks: (B:4:0x0006, B:20:0x002d, B:22:0x0035, B:14:0x0017, B:16:0x001b, B:17:0x0023, B:19:0x0029, B:24:0x0050), top: B:31:0x0004 }] */
    static Object newInstance(String str, ClassLoader classLoader, boolean z) throws ConfigurationError {
        Class<?> clsLoadClass;
        try {
            if (classLoader == null) {
                clsLoadClass = Class.forName(str);
            } else {
                try {
                    try {
                        clsLoadClass = classLoader.loadClass(str);
                    } catch (ClassNotFoundException e) {
                        if (!z) {
                            throw e;
                        }
                        Class clsClass$ = class$javax$xml$transform$FactoryFinder;
                        if (clsClass$ == null) {
                            clsClass$ = class$("javax.xml.transform.FactoryFinder");
                            class$javax$xml$transform$FactoryFinder = clsClass$;
                        }
                        classLoader = clsClass$.getClassLoader();
                        if (classLoader != null) {
                            clsLoadClass = classLoader.loadClass(str);
                        } else {
                            clsLoadClass = Class.forName(str);
                        }
                    }
                } catch (Exception e2) {
                    throw new ConfigurationError(new StringBuffer("Provider ").append(str).append(" could not be instantiated: ").append(e2).toString(), e2);
                }
            }
            Object objNewInstance = clsLoadClass.newInstance();
            if (debug) {
                dPrint(new StringBuffer("created new instance of ").append(clsLoadClass).append(" using ClassLoader: ").append(classLoader).toString());
            }
            return objNewInstance;
        } catch (ClassNotFoundException e3) {
            throw new ConfigurationError(new StringBuffer("Provider ").append(str).append(" not found").toString(), e3);
        }
    }
}
