package com.hivemq.client.internal.logging;

import com.hivemq.client.internal.util.ClassUtil;

/* JADX INFO: loaded from: classes10.dex */
public final class InternalLoggerFactory {
    private static final boolean SLF4J_AVAILABLE = ClassUtil.isAvailable("org.slf4j.Logger");

    public static InternalLogger getLogger(Class<?> cls) {
        if (SLF4J_AVAILABLE) {
            return new InternalSlf4jLogger(cls);
        }
        return InternalNoopLogger.INSTANCE;
    }

    private InternalLoggerFactory() {
    }
}
