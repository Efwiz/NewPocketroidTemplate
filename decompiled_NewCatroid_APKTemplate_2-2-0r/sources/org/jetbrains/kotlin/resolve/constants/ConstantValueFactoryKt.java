package org.jetbrains.kotlin.resolve.constants;

import kotlin.Metadata;
import okhttp3.internal.ws.WebSocketProtocol;

/* JADX INFO: compiled from: ConstantValueFactory.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\u0018\n\u0000\n\u0002\u0010\t\n\u0002\u0010\u0005\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\n\n\u0000\u001a\n\u0010\u0000\u001a\u00020\u0001*\u00020\u0002\u001a\n\u0010\u0003\u001a\u00020\u0001*\u00020\u0004\u001a\n\u0010\u0005\u001a\u00020\u0001*\u00020\u0006¨\u0006\u0007"}, d2 = {"fromUByteToLong", "", "", "fromUIntToLong", "", "fromUShortToLong", "", "descriptors"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class ConstantValueFactoryKt {
    public static final long fromUByteToLong(byte b) {
        return ((long) b) & 255;
    }

    public static final long fromUIntToLong(int i) {
        return ((long) i) & 4294967295L;
    }

    public static final long fromUShortToLong(short s) {
        return ((long) s) & WebSocketProtocol.PAYLOAD_SHORT_MAX;
    }
}
