package org.apache.xerces.parsers;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.security.PrivilegedActionException;
import java.security.PrivilegedExceptionAction;

/* JADX INFO: loaded from: classes13.dex */
final class SecuritySupport {
    private SecuritySupport() {
    }

    static ClassLoader getContextClassLoader() {
        return (ClassLoader) AccessController.doPrivileged(new PrivilegedAction() { // from class: org.apache.xerces.parsers.SecuritySupport.1
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

    static boolean getFileExists(final File file) {
        return ((Boolean) AccessController.doPrivileged(new PrivilegedAction() { // from class: org.apache.xerces.parsers.SecuritySupport.7
            @Override // java.security.PrivilegedAction
            public Object run() {
                return file.exists() ? Boolean.TRUE : Boolean.FALSE;
            }
        })).booleanValue();
    }

    static FileInputStream getFileInputStream(final File file) throws FileNotFoundException {
        try {
            return (FileInputStream) AccessController.doPrivileged(new PrivilegedExceptionAction() { // from class: org.apache.xerces.parsers.SecuritySupport.5
                @Override // java.security.PrivilegedExceptionAction
                public Object run() throws FileNotFoundException {
                    return new FileInputStream(file);
                }
            });
        } catch (PrivilegedActionException e) {
            throw ((FileNotFoundException) e.getException());
        }
    }

    static long getLastModified(final File file) {
        return ((Long) AccessController.doPrivileged(new PrivilegedAction() { // from class: org.apache.xerces.parsers.SecuritySupport.8
            @Override // java.security.PrivilegedAction
            public Object run() {
                return new Long(file.lastModified());
            }
        })).longValue();
    }

    static ClassLoader getParentClassLoader(final ClassLoader classLoader) {
        return (ClassLoader) AccessController.doPrivileged(new PrivilegedAction() { // from class: org.apache.xerces.parsers.SecuritySupport.3
            @Override // java.security.PrivilegedAction
            public Object run() {
                ClassLoader parent;
                try {
                    parent = classLoader.getParent();
                } catch (SecurityException unused) {
                    parent = null;
                }
                if (parent == classLoader) {
                    return null;
                }
                return parent;
            }
        });
    }

    static InputStream getResourceAsStream(final ClassLoader classLoader, final String str) {
        return (InputStream) AccessController.doPrivileged(new PrivilegedAction() { // from class: org.apache.xerces.parsers.SecuritySupport.6
            @Override // java.security.PrivilegedAction
            public Object run() {
                ClassLoader classLoader2 = classLoader;
                return classLoader2 == null ? ClassLoader.getSystemResourceAsStream(str) : classLoader2.getResourceAsStream(str);
            }
        });
    }

    static ClassLoader getSystemClassLoader() {
        return (ClassLoader) AccessController.doPrivileged(new PrivilegedAction() { // from class: org.apache.xerces.parsers.SecuritySupport.2
            @Override // java.security.PrivilegedAction
            public Object run() {
                try {
                    return ClassLoader.getSystemClassLoader();
                } catch (SecurityException unused) {
                    return null;
                }
            }
        });
    }

    static String getSystemProperty(final String str) {
        return (String) AccessController.doPrivileged(new PrivilegedAction() { // from class: org.apache.xerces.parsers.SecuritySupport.4
            @Override // java.security.PrivilegedAction
            public Object run() {
                return System.getProperty(str);
            }
        });
    }
}
