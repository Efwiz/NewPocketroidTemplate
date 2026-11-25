package org.jetbrains.kotlin.incremental.js;

import java.security.MessageDigest;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: IncrementalResultsConsumer.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\f\n\u0000\n\u0002\u0010\t\n\u0002\u0010\u0012\n\u0000\u001a\f\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\u0002¨\u0006\u0003"}, d2 = {"md5", "", "", "js.config"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class IncrementalResultsConsumerKt {
    /* JADX INFO: Access modifiers changed from: private */
    public static final long md5(byte[] bArr) {
        byte[] bArrDigest = MessageDigest.getInstance("MD5").digest(bArr);
        Intrinsics.checkNotNull(bArrDigest);
        return (((long) bArrDigest[0]) & 255) | ((((long) bArrDigest[1]) & 255) << 8) | ((((long) bArrDigest[2]) & 255) << 16) | ((((long) bArrDigest[3]) & 255) << 24) | ((((long) bArrDigest[4]) & 255) << 32) | ((((long) bArrDigest[5]) & 255) << 40) | ((((long) bArrDigest[6]) & 255) << 48) | ((255 & ((long) bArrDigest[7])) << 56);
    }
}
