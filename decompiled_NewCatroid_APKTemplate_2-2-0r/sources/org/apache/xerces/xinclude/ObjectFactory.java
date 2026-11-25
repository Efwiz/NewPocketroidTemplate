package org.apache.xerces.xinclude;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.Properties;

/* JADX INFO: loaded from: classes13.dex */
final class ObjectFactory {
    private static final int DEFAULT_LINE_LENGTH = 80;
    private static final String DEFAULT_PROPERTIES_FILENAME = "xerces.properties";
    private static final boolean DEBUG = isDebugEnabled();
    private static Properties fXercesProperties = null;
    private static long fLastModified = -1;

    static final class ConfigurationError extends Error {
        static final long serialVersionUID = 5061904944269807898L;
        private Exception exception;

        ConfigurationError(String str, Exception exc) {
            super(str);
            this.exception = exc;
        }

        Exception getException() {
            return this.exception;
        }
    }

    ObjectFactory() {
    }

    static Object createObject(String str, String str2) throws ConfigurationError {
        return createObject(str, null, str2);
    }

    /* JADX WARN: Code duplicated, block: B:105:0x00ca A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:118:0x00ae A[EXC_TOP_SPLITTER, PHI: r1
      0x00ae: PHI (r1v14 java.io.FileInputStream) = (r1v13 java.io.FileInputStream), (r1v18 java.io.FileInputStream) binds: [B:45:0x00b9, B:38:0x00ac] A[DONT_GENERATE, DONT_INLINE], SYNTHETIC] */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v4, types: [java.io.FileInputStream] */
    /* JADX WARN: Type inference failed for: r0v5 */
    /* JADX WARN: Type inference failed for: r0v8 */
    static Object createObject(String str, String str2, String str3) throws Throwable {
        FileInputStream fileInputStream;
        String property;
        File file;
        boolean fileExists;
        FileInputStream fileInputStream2;
        boolean z = DEBUG;
        if (z) {
            debugPrintln("debug is on");
        }
        ClassLoader classLoaderFindClassLoader = findClassLoader();
        try {
            String systemProperty = SecuritySupport.getSystemProperty(str);
            if (systemProperty != null && systemProperty.length() > 0) {
                if (z) {
                    debugPrintln("found system property, value=" + systemProperty);
                }
                return newInstance(systemProperty, classLoaderFindClassLoader, true);
            }
        } catch (SecurityException unused) {
        }
        FileInputStream fileInputStream3 = null;
        ?? r0 = 0;
        if (str2 == null) {
            try {
                str2 = SecuritySupport.getSystemProperty("java.home") + File.separator + "lib" + File.separator + DEFAULT_PROPERTIES_FILENAME;
                file = new File(str2);
                try {
                    fileExists = SecuritySupport.getFileExists(file);
                } catch (SecurityException unused2) {
                    fLastModified = -1L;
                    fXercesProperties = null;
                    fileExists = false;
                }
            } catch (SecurityException unused3) {
                file = null;
            }
            synchronized (ObjectFactory.class) {
                try {
                    try {
                        try {
                            long j = fLastModified;
                            if (j >= 0) {
                                if (fileExists) {
                                    long lastModified = SecuritySupport.getLastModified(file);
                                    fLastModified = lastModified;
                                    if (j < lastModified) {
                                        fXercesProperties = new Properties();
                                        fileInputStream2 = SecuritySupport.getFileInputStream(file);
                                        try {
                                            fXercesProperties.load(fileInputStream2);
                                            if (fileInputStream2 != null) {
                                                try {
                                                    fileInputStream2.close();
                                                } catch (IOException unused4) {
                                                }
                                            }
                                        } catch (Exception unused5) {
                                            fXercesProperties = null;
                                            fLastModified = -1L;
                                            if (fileInputStream2 != null) {
                                                fileInputStream2.close();
                                            }
                                        }
                                    }
                                }
                                if (!fileExists) {
                                    fLastModified = -1L;
                                    fXercesProperties = null;
                                }
                                fileInputStream2 = null;
                                if (fileInputStream2 != null) {
                                    fileInputStream2.close();
                                }
                            } else {
                                if (fileExists) {
                                    fLastModified = SecuritySupport.getLastModified(file);
                                    fXercesProperties = new Properties();
                                    fileInputStream2 = SecuritySupport.getFileInputStream(file);
                                    fXercesProperties.load(fileInputStream2);
                                } else {
                                    fileInputStream2 = null;
                                }
                                if (fileInputStream2 != null) {
                                    fileInputStream2.close();
                                }
                            }
                        } catch (Throwable th) {
                            th = th;
                            r0 = file;
                            if (r0 != 0) {
                                try {
                                    r0.close();
                                } catch (IOException unused6) {
                                }
                            }
                            throw th;
                        }
                    } catch (Exception unused7) {
                        fileInputStream2 = null;
                    } catch (Throwable th2) {
                        th = th2;
                        if (r0 != 0) {
                            r0.close();
                        }
                        throw th;
                    }
                } catch (Throwable th3) {
                    throw th3;
                }
            }
            Properties properties = fXercesProperties;
            property = properties != null ? properties.getProperty(str) : null;
        } else {
            try {
                fileInputStream = SecuritySupport.getFileInputStream(new File(str2));
                try {
                    Properties properties2 = new Properties();
                    properties2.load(fileInputStream);
                    String property2 = properties2.getProperty(str);
                    if (fileInputStream != null) {
                        try {
                            fileInputStream.close();
                        } catch (IOException unused8) {
                        }
                    }
                    property = property2;
                } catch (Exception unused9) {
                    if (fileInputStream != null) {
                        try {
                            fileInputStream.close();
                        } catch (IOException unused10) {
                        }
                    }
                } catch (Throwable th4) {
                    th = th4;
                    fileInputStream3 = fileInputStream;
                    if (fileInputStream3 != null) {
                        try {
                            fileInputStream3.close();
                        } catch (IOException unused11) {
                        }
                    }
                    throw th;
                }
            } catch (Exception unused12) {
                fileInputStream = null;
            } catch (Throwable th5) {
                th = th5;
            }
        }
        if (property != null) {
            if (DEBUG) {
                debugPrintln("found in " + str2 + ", value=" + property);
            }
            return newInstance(property, classLoaderFindClassLoader, true);
        }
        Object objFindJarServiceProvider = findJarServiceProvider(str);
        if (objFindJarServiceProvider != null) {
            return objFindJarServiceProvider;
        }
        if (str3 == null) {
            throw new ConfigurationError("Provider for " + str + " cannot be found", null);
        }
        if (DEBUG) {
            debugPrintln("using fallback, value=" + str3);
        }
        return newInstance(str3, classLoaderFindClassLoader, true);
    }

    private static void debugPrintln(String str) {
        if (DEBUG) {
            System.err.println("XERCES: " + str);
        }
    }

    static ClassLoader findClassLoader() throws ConfigurationError {
        ClassLoader contextClassLoader = SecuritySupport.getContextClassLoader();
        ClassLoader systemClassLoader = SecuritySupport.getSystemClassLoader();
        for (ClassLoader parentClassLoader = systemClassLoader; contextClassLoader != parentClassLoader; parentClassLoader = SecuritySupport.getParentClassLoader(parentClassLoader)) {
            if (parentClassLoader == null) {
                return contextClassLoader;
            }
        }
        ClassLoader classLoader = ObjectFactory.class.getClassLoader();
        for (ClassLoader parentClassLoader2 = systemClassLoader; classLoader != parentClassLoader2; parentClassLoader2 = SecuritySupport.getParentClassLoader(parentClassLoader2)) {
            if (parentClassLoader2 == null) {
                return classLoader;
            }
        }
        return systemClassLoader;
    }

    private static Object findJarServiceProvider(String str) throws ConfigurationError {
        BufferedReader bufferedReader;
        ClassLoader classLoader;
        String str2 = "META-INF/services/" + str;
        ClassLoader classLoaderFindClassLoader = findClassLoader();
        InputStream resourceAsStream = SecuritySupport.getResourceAsStream(classLoaderFindClassLoader, str2);
        if (resourceAsStream == null && classLoaderFindClassLoader != (classLoader = ObjectFactory.class.getClassLoader())) {
            resourceAsStream = SecuritySupport.getResourceAsStream(classLoader, str2);
            classLoaderFindClassLoader = classLoader;
        }
        if (resourceAsStream == null) {
            return null;
        }
        if (DEBUG) {
            debugPrintln("found jar resource=" + str2 + " using ClassLoader: " + classLoaderFindClassLoader);
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
                if (DEBUG) {
                    debugPrintln("found in resource, value=" + line);
                }
                return newInstance(line, classLoaderFindClassLoader, false);
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

    static Class findProviderClass(String str, ClassLoader classLoader, boolean z) throws ConfigurationError, ClassNotFoundException {
        SecurityManager securityManager = System.getSecurityManager();
        if (securityManager != null) {
            int iLastIndexOf = str.lastIndexOf(46);
            securityManager.checkPackageAccess(iLastIndexOf != -1 ? str.substring(0, iLastIndexOf) : str);
        }
        if (classLoader != null) {
            try {
                return classLoader.loadClass(str);
            } catch (ClassNotFoundException e) {
                if (!z) {
                    throw e;
                }
                ClassLoader classLoader2 = ObjectFactory.class.getClassLoader();
                if (classLoader2 != null) {
                    if (classLoader != classLoader2) {
                        return classLoader2.loadClass(str);
                    }
                    throw e;
                }
            }
        }
        return Class.forName(str);
    }

    private static boolean isDebugEnabled() {
        try {
            String systemProperty = SecuritySupport.getSystemProperty("xerces.debug");
            return (systemProperty == null || "false".equals(systemProperty)) ? false : true;
        } catch (SecurityException unused) {
            return false;
        }
    }

    static Object newInstance(String str, ClassLoader classLoader, boolean z) throws ConfigurationError {
        try {
            Class clsFindProviderClass = findProviderClass(str, classLoader, z);
            Object objNewInstance = clsFindProviderClass.newInstance();
            if (DEBUG) {
                debugPrintln("created new instance of " + clsFindProviderClass + " using ClassLoader: " + classLoader);
            }
            return objNewInstance;
        } catch (ClassNotFoundException e) {
            throw new ConfigurationError("Provider " + str + " not found", e);
        } catch (Exception e2) {
            throw new ConfigurationError("Provider " + str + " could not be instantiated: " + e2, e2);
        }
    }
}
