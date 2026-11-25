package org.eclipse.jgit.lib;

import java.util.Iterator;
import java.util.ServiceConfigurationError;
import java.util.ServiceLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/* JADX INFO: loaded from: classes13.dex */
public abstract class GpgSignatureVerifierFactory {
    private static final Logger LOG = LoggerFactory.getLogger((Class<?>) GpgSignatureVerifierFactory.class);
    private static volatile GpgSignatureVerifierFactory defaultFactory = loadDefault();

    public abstract GpgSignatureVerifier getVerifier();

    private static GpgSignatureVerifierFactory loadDefault() {
        try {
            Iterator it = ServiceLoader.load(GpgSignatureVerifierFactory.class).iterator();
            if (it.hasNext()) {
                return (GpgSignatureVerifierFactory) it.next();
            }
            return null;
        } catch (ServiceConfigurationError e) {
            LOG.error(e.getMessage(), (Throwable) e);
            return null;
        }
    }

    public static GpgSignatureVerifierFactory getDefault() {
        return defaultFactory;
    }

    public static void setDefault(GpgSignatureVerifierFactory gpgSignatureVerifierFactory) {
        defaultFactory = gpgSignatureVerifierFactory;
    }
}
