package com.hivemq.client.internal.logging;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/* JADX INFO: loaded from: classes10.dex */
class InternalSlf4jLogger implements InternalLogger {
    private final Logger delegate;

    InternalSlf4jLogger(Class<?> cls) {
        this.delegate = LoggerFactory.getLogger(cls);
    }

    @Override // com.hivemq.client.internal.logging.InternalLogger
    public void error(String str) {
        this.delegate.error(str);
    }

    @Override // com.hivemq.client.internal.logging.InternalLogger
    public void error(String str, Throwable th) {
        this.delegate.error(str, th);
    }

    @Override // com.hivemq.client.internal.logging.InternalLogger
    public void error(String str, Object obj) {
        this.delegate.error(str, obj);
    }

    @Override // com.hivemq.client.internal.logging.InternalLogger
    public void error(String str, Object obj, Object obj2) {
        this.delegate.error(str, obj, obj2);
    }

    @Override // com.hivemq.client.internal.logging.InternalLogger
    public void warn(String str) {
        this.delegate.warn(str);
    }

    @Override // com.hivemq.client.internal.logging.InternalLogger
    public void warn(String str, Object obj) {
        this.delegate.warn(str, obj);
    }

    @Override // com.hivemq.client.internal.logging.InternalLogger
    public void warn(String str, Object obj, Object obj2) {
        this.delegate.warn(str, obj, obj2);
    }
}
