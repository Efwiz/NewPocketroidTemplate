package org.eclipse.jgit.transport;

import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.Iterator;
import java.util.ServiceLoader;
import org.eclipse.jgit.errors.TransportException;
import org.eclipse.jgit.util.FS;
import org.eclipse.jgit.util.SystemReader;

/* JADX INFO: loaded from: classes13.dex */
public abstract class SshSessionFactory {
    private static SshSessionFactory INSTANCE = loadSshSessionFactory();

    public abstract RemoteSession getSession(URIish uRIish, CredentialsProvider credentialsProvider, FS fs, int i) throws TransportException;

    public abstract String getType();

    private static SshSessionFactory loadSshSessionFactory() {
        Iterator it = ServiceLoader.load(SshSessionFactory.class).iterator();
        if (it.hasNext()) {
            return (SshSessionFactory) it.next();
        }
        return null;
    }

    public static SshSessionFactory getInstance() {
        return INSTANCE;
    }

    public static void setInstance(SshSessionFactory sshSessionFactory) {
        if (sshSessionFactory != null) {
            INSTANCE = sshSessionFactory;
        } else {
            INSTANCE = loadSshSessionFactory();
        }
    }

    public static String getLocalUserName() {
        return (String) AccessController.doPrivileged(new PrivilegedAction() { // from class: org.eclipse.jgit.transport.SshSessionFactory$$ExternalSyntheticLambda0
            @Override // java.security.PrivilegedAction
            public final Object run() {
                return SystemReader.getInstance().getProperty("user.name");
            }
        });
    }

    public void releaseSession(RemoteSession remoteSession) {
        remoteSession.disconnect();
    }
}
