package org.eclipse.jgit.lib;

import java.util.Iterator;
import java.util.ServiceConfigurationError;
import java.util.ServiceLoader;
import org.eclipse.jgit.annotations.Nullable;
import org.eclipse.jgit.api.errors.CanceledException;
import org.eclipse.jgit.transport.CredentialsProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/* JADX INFO: loaded from: classes13.dex */
public abstract class GpgSigner {
    private static final Logger LOG = LoggerFactory.getLogger((Class<?>) GpgSigner.class);
    private static GpgSigner defaultSigner = loadGpgSigner();

    public abstract boolean canLocateSigningKey(@Nullable String str, PersonIdent personIdent, CredentialsProvider credentialsProvider) throws CanceledException;

    public abstract void sign(CommitBuilder commitBuilder, @Nullable String str, PersonIdent personIdent, CredentialsProvider credentialsProvider) throws CanceledException;

    private static GpgSigner loadGpgSigner() {
        try {
            Iterator it = ServiceLoader.load(GpgSigner.class).iterator();
            if (it.hasNext()) {
                return (GpgSigner) it.next();
            }
            return null;
        } catch (ServiceConfigurationError e) {
            LOG.error(e.getMessage(), (Throwable) e);
            return null;
        }
    }

    public static GpgSigner getDefault() {
        return defaultSigner;
    }

    public static void setDefault(GpgSigner gpgSigner) {
        defaultSigner = gpgSigner;
    }
}
