package org.jetbrains.kotlin.com.intellij.util.io;

import org.jetbrains.kotlin.com.intellij.openapi.diagnostic.Logger;

/* JADX INFO: loaded from: classes3.dex */
final class IOStatistics {
    static final boolean DEBUG;
    static final Logger LOG;

    IOStatistics() {
    }

    static {
        DEBUG = System.getProperty("io.access.debug") != null;
        LOG = Logger.getInstance((Class<?>) IOStatistics.class);
    }

    static void dump(String str) {
        LOG.info(str);
    }
}
