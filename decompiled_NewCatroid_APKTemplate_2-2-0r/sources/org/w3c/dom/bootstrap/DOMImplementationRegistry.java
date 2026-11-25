package org.w3c.dom.bootstrap;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.StringTokenizer;
import java.util.Vector;
import org.w3c.dom.DOMImplementation;
import org.w3c.dom.DOMImplementationList;
import org.w3c.dom.DOMImplementationSource;

/* JADX INFO: loaded from: classes7.dex */
public final class DOMImplementationRegistry {
    private static final String DEFAULT_DOM_IMPLEMENTATION_SOURCE = "org.apache.xerces.dom.DOMXSImplementationSourceImpl";
    private static final int DEFAULT_LINE_LENGTH = 80;
    public static final String PROPERTY = "org.w3c.dom.DOMImplementationSourceList";
    static /* synthetic */ Class class$org$w3c$dom$bootstrap$DOMImplementationRegistry;
    private Vector sources;

    private DOMImplementationRegistry(Vector vector) {
        this.sources = vector;
    }

    static /* synthetic */ Class class$(String str) {
        try {
            return Class.forName(str);
        } catch (ClassNotFoundException e) {
            throw new NoClassDefFoundError(e.getMessage());
        }
    }

    private static ClassLoader getClassLoader() {
        Class clsClass$;
        try {
            ClassLoader contextClassLoader = getContextClassLoader();
            if (contextClassLoader != null) {
                return contextClassLoader;
            }
            Class clsClass$2 = class$org$w3c$dom$bootstrap$DOMImplementationRegistry;
            if (clsClass$2 == null) {
                clsClass$2 = class$("org.w3c.dom.bootstrap.DOMImplementationRegistry");
                class$org$w3c$dom$bootstrap$DOMImplementationRegistry = clsClass$2;
            }
            return clsClass$2.getClassLoader();
        } catch (Exception unused) {
            if (class$org$w3c$dom$bootstrap$DOMImplementationRegistry == null) {
                clsClass$ = class$("org.w3c.dom.bootstrap.DOMImplementationRegistry");
                class$org$w3c$dom$bootstrap$DOMImplementationRegistry = clsClass$;
            } else {
                clsClass$ = class$org$w3c$dom$bootstrap$DOMImplementationRegistry;
            }
            return clsClass$.getClassLoader();
        }
    }

    private static ClassLoader getContextClassLoader() {
        if (isJRE11()) {
            return null;
        }
        return (ClassLoader) AccessController.doPrivileged(new PrivilegedAction() { // from class: org.w3c.dom.bootstrap.DOMImplementationRegistry.2
            @Override // java.security.PrivilegedAction
            public Object run() {
                try {
                    return Thread.currentThread().getContextClassLoader();
                } catch (SecurityException unused) {
                    return null;
                }
            }
        });
    }

    private static InputStream getResourceAsStream(final ClassLoader classLoader, final String str) {
        if (isJRE11()) {
            return classLoader == null ? ClassLoader.getSystemResourceAsStream(str) : classLoader.getResourceAsStream(str);
        }
        return (InputStream) AccessController.doPrivileged(new PrivilegedAction() { // from class: org.w3c.dom.bootstrap.DOMImplementationRegistry.4
            @Override // java.security.PrivilegedAction
            public Object run() {
                ClassLoader classLoader2 = classLoader;
                return classLoader2 == null ? ClassLoader.getSystemResourceAsStream(str) : classLoader2.getResourceAsStream(str);
            }
        });
    }

    private static String getServiceValue(ClassLoader classLoader) {
        BufferedReader bufferedReader;
        try {
            InputStream resourceAsStream = getResourceAsStream(classLoader, "META-INF/services/org.w3c.dom.DOMImplementationSourceList");
            if (resourceAsStream != null) {
                try {
                    bufferedReader = new BufferedReader(new InputStreamReader(resourceAsStream, "UTF-8"), 80);
                } catch (UnsupportedEncodingException unused) {
                    bufferedReader = new BufferedReader(new InputStreamReader(resourceAsStream), 80);
                }
                try {
                    String line = bufferedReader.readLine();
                    bufferedReader.close();
                    if (line != null && line.length() > 0) {
                        return line;
                    }
                } catch (Throwable th) {
                    bufferedReader.close();
                    throw th;
                }
            }
        } catch (Exception unused2) {
        }
        return null;
    }

    private static String getSystemProperty(final String str) {
        return isJRE11() ? System.getProperty(str) : (String) AccessController.doPrivileged(new PrivilegedAction() { // from class: org.w3c.dom.bootstrap.DOMImplementationRegistry.3
            @Override // java.security.PrivilegedAction
            public Object run() {
                return System.getProperty(str);
            }
        });
    }

    private static boolean isJRE11() {
        try {
            Class.forName("java.security.AccessController");
            return false;
        } catch (Exception unused) {
            return true;
        }
    }

    public static DOMImplementationRegistry newInstance() throws IllegalAccessException, InstantiationException, ClassNotFoundException, ClassCastException {
        Vector vector = new Vector();
        ClassLoader classLoader = getClassLoader();
        String systemProperty = getSystemProperty(PROPERTY);
        if (systemProperty == null || systemProperty.length() == 0) {
            systemProperty = getServiceValue(classLoader);
        }
        if (systemProperty == null) {
            systemProperty = DEFAULT_DOM_IMPLEMENTATION_SOURCE;
        }
        if (systemProperty != null) {
            StringTokenizer stringTokenizer = new StringTokenizer(systemProperty);
            while (stringTokenizer.hasMoreTokens()) {
                String strNextToken = stringTokenizer.nextToken();
                vector.addElement((DOMImplementationSource) (classLoader != null ? classLoader.loadClass(strNextToken) : Class.forName(strNextToken)).newInstance());
            }
        }
        return new DOMImplementationRegistry(vector);
    }

    public void addSource(DOMImplementationSource dOMImplementationSource) {
        dOMImplementationSource.getClass();
        if (this.sources.contains(dOMImplementationSource)) {
            return;
        }
        this.sources.addElement(dOMImplementationSource);
    }

    public DOMImplementation getDOMImplementation(String str) {
        int size = this.sources.size();
        for (int i = 0; i < size; i++) {
            DOMImplementation dOMImplementation = ((DOMImplementationSource) this.sources.elementAt(i)).getDOMImplementation(str);
            if (dOMImplementation != null) {
                return dOMImplementation;
            }
        }
        return null;
    }

    public DOMImplementationList getDOMImplementationList(String str) {
        final Vector vector = new Vector();
        int size = this.sources.size();
        for (int i = 0; i < size; i++) {
            DOMImplementationList dOMImplementationList = ((DOMImplementationSource) this.sources.elementAt(i)).getDOMImplementationList(str);
            for (int i2 = 0; i2 < dOMImplementationList.getLength(); i2++) {
                vector.addElement(dOMImplementationList.item(i2));
            }
        }
        return new DOMImplementationList() { // from class: org.w3c.dom.bootstrap.DOMImplementationRegistry.1
            @Override // org.w3c.dom.DOMImplementationList
            public int getLength() {
                return vector.size();
            }

            @Override // org.w3c.dom.DOMImplementationList
            public DOMImplementation item(int i3) {
                if (i3 >= 0 && i3 < vector.size()) {
                    try {
                        return (DOMImplementation) vector.elementAt(i3);
                    } catch (ArrayIndexOutOfBoundsException unused) {
                    }
                }
                return null;
            }
        };
    }
}
