package org.jetbrains.kotlin.konan.util;

import java.net.InetAddress;
import java.net.UnknownHostException;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Metadata;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;

/* JADX INFO: compiled from: DependencyProcessor.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0007\bÀ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\b\u0010\r\u001a\u00020\u0007H\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n\u0000R\u001b\u0010\u0006\u001a\u00020\u00078BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\t\u0010\n\u001a\u0004\b\u0006\u0010\bR\u0011\u0010\u000b\u001a\u00020\u00078F¢\u0006\u0006\u001a\u0004\b\u000b\u0010\bR\u000e\u0010\f\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000¨\u0006\u000e"}, d2 = {"Lorg/jetbrains/kotlin/konan/util/InternalServer;", "", "()V", "host", "", "internalDomain", "isAccessible", "", "()Z", "isAccessible$delegate", "Lkotlin/Lazy;", "isAvailable", "url", "checkAccessible", "kotlin-native-utils"}, k = 1, mv = {1, 5, 1}, xi = 48)
public final class InternalServer {
    public static final InternalServer INSTANCE = new InternalServer();

    /* JADX INFO: renamed from: isAccessible$delegate, reason: from kotlin metadata */
    private static final Lazy isAccessible = LazyKt.lazy(new Function0<Boolean>() { // from class: org.jetbrains.kotlin.konan.util.InternalServer.isAccessible.2
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // kotlin.jvm.functions.Function0
        public final Boolean invoke() {
            return Boolean.valueOf(InternalServer.INSTANCE.checkAccessible());
        }
    });
    public static final String url = "https://repo.labs.intellij.net/kotlin-native";

    private InternalServer() {
    }

    /* JADX WARN: Code restructure failed: missing block: B:17:0x0036, code lost:
    
        if (r0.equals("0") != false) goto L21;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final boolean isAvailable() {
        /*
            r4 = this;
            java.lang.String r0 = "KONAN_USE_INTERNAL_SERVER"
            java.lang.String r0 = java.lang.System.getenv(r0)
            if (r0 == 0) goto L52
            int r1 = r0.hashCode()
            r2 = 48
            if (r1 == r2) goto L30
            r2 = 49
            if (r1 == r2) goto L26
            r2 = 3005871(0x2dddaf, float:4.212122E-39)
            if (r1 != r2) goto L39
            java.lang.String r1 = "auto"
            boolean r1 = r0.equals(r1)
            if (r1 == 0) goto L39
            boolean r0 = r4.isAccessible()
            goto L53
        L26:
            java.lang.String r1 = "1"
            boolean r1 = r0.equals(r1)
            if (r1 == 0) goto L39
            r0 = 1
            goto L53
        L30:
            java.lang.String r1 = "0"
            boolean r1 = r0.equals(r1)
            if (r1 == 0) goto L39
            goto L52
        L39:
            java.lang.IllegalStateException r1 = new java.lang.IllegalStateException
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            java.lang.String r3 = "unexpected environment: KONAN_USE_INTERNAL_SERVER="
            r2.<init>(r3)
            java.lang.StringBuilder r0 = r2.append(r0)
            java.lang.String r0 = r0.toString()
            java.lang.String r0 = r0.toString()
            r1.<init>(r0)
            throw r1
        L52:
            r0 = 0
        L53:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: org.jetbrains.kotlin.konan.util.InternalServer.isAvailable():boolean");
    }

    private final boolean isAccessible() {
        return ((Boolean) isAccessible.getValue()).booleanValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final boolean checkAccessible() {
        try {
            String canonicalHostName = InetAddress.getLocalHost().getCanonicalHostName();
            Intrinsics.checkNotNullExpressionValue(canonicalHostName, "getLocalHost().canonicalHostName");
            if (!StringsKt.endsWith$default(canonicalHostName, ".labs.intellij.net", false, 2, (Object) null)) {
                return false;
            }
            InetAddress.getByName("repo.labs.intellij.net");
            return true;
        } catch (UnknownHostException unused) {
            return false;
        }
    }
}
