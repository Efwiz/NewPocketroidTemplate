package com.hivemq.client.internal.logging;

/* JADX INFO: loaded from: classes10.dex */
class InternalNoopLogger implements InternalLogger {
    static final InternalLogger INSTANCE = new InternalNoopLogger();

    @Override // com.hivemq.client.internal.logging.InternalLogger
    public void error(String str) {
    }

    @Override // com.hivemq.client.internal.logging.InternalLogger
    public void error(String str, Object obj) {
    }

    @Override // com.hivemq.client.internal.logging.InternalLogger
    public void error(String str, Object obj, Object obj2) {
    }

    @Override // com.hivemq.client.internal.logging.InternalLogger
    public void error(String str, Throwable th) {
    }

    @Override // com.hivemq.client.internal.logging.InternalLogger
    public void warn(String str) {
    }

    @Override // com.hivemq.client.internal.logging.InternalLogger
    public void warn(String str, Object obj) {
    }

    @Override // com.hivemq.client.internal.logging.InternalLogger
    public void warn(String str, Object obj, Object obj2) {
    }

    private InternalNoopLogger() {
    }
}
