package org.jetbrains.kotlin.incremental;

import androidx.exifinterface.media.ExifInterface;
import java.security.MessageDigest;
import java.util.Collection;
import java.util.Map;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.FunctionReference;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Reflection;
import kotlin.reflect.KDeclarationContainer;

/* JADX INFO: compiled from: IncrementalJvmCache.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000,\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\u000f\n\u0002\u0010\u001e\n\u0002\b\u0003\n\u0002\u0010$\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\t\n\u0002\u0010\u0012\n\u0000\u001a \u0010\u0002\u001a\u00020\u0001\"\u000e\b\u0000\u0010\u0003*\b\u0012\u0004\u0012\u0002H\u00030\u0004*\b\u0012\u0004\u0012\u0002H\u00030\u0005\u001a@\u0010\u0006\u001a\u00020\u0001\"\u000e\b\u0000\u0010\u0007*\b\u0012\u0004\u0012\u0002H\u00070\u0004\"\u0004\b\u0001\u0010\b*\u000e\u0012\u0004\u0012\u0002H\u0007\u0012\u0004\u0012\u0002H\b0\t2\u0012\u0010\n\u001a\u000e\u0012\u0004\u0012\u0002H\b\u0012\u0004\u0012\u00020\u00010\u000b\u001a\n\u0010\f\u001a\u00020\r*\u00020\u000e\"\u000e\u0010\u0000\u001a\u00020\u0001X\u0086T¢\u0006\u0002\n\u0000¨\u0006\u000f"}, d2 = {"KOTLIN_CACHE_DIRECTORY_NAME", "", "dumpCollection", ExifInterface.GPS_DIRECTION_TRUE, "", "", "dumpMap", "K", ExifInterface.GPS_MEASUREMENT_INTERRUPTED, "", "dumpValue", "Lkotlin/Function1;", "md5", "", "", "kotlin-build-common"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class IncrementalJvmCacheKt {
    public static final String KOTLIN_CACHE_DIRECTORY_NAME = "kotlin";

    public static final long md5(byte[] bArr) {
        Intrinsics.checkNotNullParameter(bArr, "<this>");
        byte[] bArrDigest = MessageDigest.getInstance("MD5").digest(bArr);
        Intrinsics.checkNotNull(bArrDigest);
        return (((long) bArrDigest[0]) & 255) | ((((long) bArrDigest[1]) & 255) << 8) | ((((long) bArrDigest[2]) & 255) << 16) | ((((long) bArrDigest[3]) & 255) << 24) | ((((long) bArrDigest[4]) & 255) << 32) | ((((long) bArrDigest[5]) & 255) << 40) | ((((long) bArrDigest[6]) & 255) << 48) | ((255 & ((long) bArrDigest[7])) << 56);
    }

    public static final <K extends Comparable<? super K>, V> String dumpMap(Map<K, ? extends V> map, Function1<? super V, String> dumpValue) {
        String strInvoke;
        Intrinsics.checkNotNullParameter(map, "<this>");
        Intrinsics.checkNotNullParameter(dumpValue, "dumpValue");
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        for (Comparable comparable : CollectionsKt.sorted(map.keySet())) {
            if (sb.length() != 1) {
                sb.append(", ");
            }
            V v = map.get(comparable);
            if (v == null || (strInvoke = dumpValue.invoke(v)) == null) {
                strInvoke = "null";
            }
            sb.append(comparable + " -> " + strInvoke);
        }
        sb.append("}");
        String string = sb.toString();
        Intrinsics.checkNotNullExpressionValue(string, "toString(...)");
        return string;
    }

    /* JADX INFO: renamed from: org.jetbrains.kotlin.incremental.IncrementalJvmCacheKt$dumpCollection$1, reason: invalid class name */
    /* JADX INFO: compiled from: IncrementalJvmCache.kt */
    @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
    /* synthetic */ class AnonymousClass1 extends FunctionReference implements Function1<Object, String> {
        public static final AnonymousClass1 INSTANCE = new AnonymousClass1();

        AnonymousClass1() {
            super(1);
        }

        @Override // kotlin.jvm.internal.CallableReference
        public final String getSignature() {
            return "toString()Ljava/lang/String;";
        }

        @Override // kotlin.jvm.internal.CallableReference, kotlin.reflect.KCallable
        public final String getName() {
            return "toString";
        }

        @Override // kotlin.jvm.internal.CallableReference
        public final KDeclarationContainer getOwner() {
            return Reflection.getOrCreateKotlinClass(Object.class);
        }

        @Override // kotlin.jvm.functions.Function1
        public final String invoke(Object p0) {
            Intrinsics.checkNotNullParameter(p0, "p0");
            return p0.toString();
        }
    }

    public static final <T extends Comparable<? super T>> String dumpCollection(Collection<? extends T> collection) {
        Intrinsics.checkNotNullParameter(collection, "<this>");
        return "[" + CollectionsKt.joinToString$default(CollectionsKt.sorted(collection), ", ", null, null, 0, null, AnonymousClass1.INSTANCE, 30, null) + ']';
    }
}
