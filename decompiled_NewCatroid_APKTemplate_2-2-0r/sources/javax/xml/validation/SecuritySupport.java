package javax.xml.validation;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.security.PrivilegedActionException;
import java.security.PrivilegedExceptionAction;
import java.util.Enumeration;

/* JADX INFO: loaded from: classes11.dex */
final class SecuritySupport {
    private SecuritySupport() {
    }

    static boolean doesFileExist(final File file) {
        return ((Boolean) AccessController.doPrivileged(new PrivilegedAction() { // from class: javax.xml.validation.SecuritySupport.8
            @Override // java.security.PrivilegedAction
            public Object run() {
                return file.exists() ? Boolean.TRUE : Boolean.FALSE;
            }
        })).booleanValue();
    }

    static ClassLoader getContextClassLoader() {
        return (ClassLoader) AccessController.doPrivileged(new PrivilegedAction() { // from class: javax.xml.validation.SecuritySupport.1
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

    static FileInputStream getFileInputStream(final File file) throws FileNotFoundException {
        try {
            return (FileInputStream) AccessController.doPrivileged(new PrivilegedExceptionAction() { // from class: javax.xml.validation.SecuritySupport.3
                @Override // java.security.PrivilegedExceptionAction
                public Object run() throws FileNotFoundException {
                    return new FileInputStream(file);
                }
            });
        } catch (PrivilegedActionException e) {
            throw ((FileNotFoundException) e.getException());
        }
    }

    static InputStream getResourceAsStream(final ClassLoader classLoader, final String str) {
        return (InputStream) AccessController.doPrivileged(new PrivilegedAction() { // from class: javax.xml.validation.SecuritySupport.7
            @Override // java.security.PrivilegedAction
            public Object run() {
                ClassLoader classLoader2 = classLoader;
                return classLoader2 == null ? ClassLoader.getSystemResourceAsStream(str) : classLoader2.getResourceAsStream(str);
            }
        });
    }

    static URL getResourceAsURL(final ClassLoader classLoader, final String str) {
        return (URL) AccessController.doPrivileged(new PrivilegedAction() { // from class: javax.xml.validation.SecuritySupport.5
            @Override // java.security.PrivilegedAction
            public Object run() {
                ClassLoader classLoader2 = classLoader;
                return classLoader2 == null ? ClassLoader.getSystemResource(str) : classLoader2.getResource(str);
            }
        });
    }

    static Enumeration getResources(final ClassLoader classLoader, final String str) throws IOException {
        try {
            return (Enumeration) AccessController.doPrivileged(new PrivilegedExceptionAction() { // from class: javax.xml.validation.SecuritySupport.6
                @Override // java.security.PrivilegedExceptionAction
                public Object run() throws IOException {
                    ClassLoader classLoader2 = classLoader;
                    return classLoader2 == null ? ClassLoader.getSystemResources(str) : classLoader2.getResources(str);
                }
            });
        } catch (PrivilegedActionException e) {
            throw ((IOException) e.getException());
        }
    }

    static String getSystemProperty(final String str) {
        return (String) AccessController.doPrivileged(new PrivilegedAction() { // from class: javax.xml.validation.SecuritySupport.2
            @Override // java.security.PrivilegedAction
            public Object run() {
                return System.getProperty(str);
            }
        });
    }

    static InputStream getURLInputStream(final URL url) throws IOException {
        try {
            return (InputStream) AccessController.doPrivileged(new PrivilegedExceptionAction() { // from class: javax.xml.validation.SecuritySupport.4
                @Override // java.security.PrivilegedExceptionAction
                public Object run() throws IOException {
                    return url.openStream();
                }
            });
        } catch (PrivilegedActionException e) {
            throw ((IOException) e.getException());
        }
    }
}
