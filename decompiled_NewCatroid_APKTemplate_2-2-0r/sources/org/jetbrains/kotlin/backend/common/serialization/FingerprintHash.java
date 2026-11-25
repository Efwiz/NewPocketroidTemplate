package org.jetbrains.kotlin.backend.common.serialization;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.ULong;
import kotlin.jvm.JvmInline;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import kotlin.text.UStringsKt;
import org.jetbrains.kotlin.js.translate.context.Namer;

/* JADX INFO: compiled from: FileFingerprints.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010\u0012\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0004\b\u0087@\u0018\u0000 \u00192\u00020\u0001:\u0001\u0019B\u000f\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0004\b\u0004\u0010\u0005J\u001a\u0010\b\u001a\u00020\t2\b\u0010\n\u001a\u0004\u0018\u00010\u0001HÖ\u0003¢\u0006\u0004\b\u000b\u0010\fJ\u0010\u0010\r\u001a\u00020\u000eHÖ\u0001¢\u0006\u0004\b\u000f\u0010\u0010J\r\u0010\u0011\u001a\u00020\u0012¢\u0006\u0004\b\u0013\u0010\u0014J\u000f\u0010\u0015\u001a\u00020\u0016H\u0016¢\u0006\u0004\b\u0017\u0010\u0018R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007\u0088\u0001\u0002¨\u0006\u001a"}, d2 = {"Lorg/jetbrains/kotlin/backend/common/serialization/FingerprintHash;", "", "hash", "Lorg/jetbrains/kotlin/backend/common/serialization/Hash128Bits;", "constructor-impl", "(Lorg/jetbrains/kotlin/backend/common/serialization/Hash128Bits;)Lorg/jetbrains/kotlin/backend/common/serialization/Hash128Bits;", "getHash", "()Lorg/jetbrains/kotlin/backend/common/serialization/Hash128Bits;", Namer.EQUALS_METHOD_NAME, "", "other", "equals-impl", "(Lorg/jetbrains/kotlin/backend/common/serialization/Hash128Bits;Ljava/lang/Object;)Z", "hashCode", "", "hashCode-impl", "(Lorg/jetbrains/kotlin/backend/common/serialization/Hash128Bits;)I", "toByteArray", "", "toByteArray-impl", "(Lorg/jetbrains/kotlin/backend/common/serialization/Hash128Bits;)[B", "toString", "", "toString-impl", "(Lorg/jetbrains/kotlin/backend/common/serialization/Hash128Bits;)Ljava/lang/String;", "Companion", "ir.serialization.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
@JvmInline
public final class FingerprintHash {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private final Hash128Bits hash;

    /* JADX INFO: renamed from: box-impl, reason: not valid java name */
    public static final /* synthetic */ FingerprintHash m6123boximpl(Hash128Bits hash128Bits) {
        return new FingerprintHash(hash128Bits);
    }

    /* JADX INFO: renamed from: constructor-impl, reason: not valid java name */
    public static Hash128Bits m6124constructorimpl(Hash128Bits hash) {
        Intrinsics.checkNotNullParameter(hash, "hash");
        return hash;
    }

    /* JADX INFO: renamed from: equals-impl, reason: not valid java name */
    public static boolean m6125equalsimpl(Hash128Bits hash128Bits, Object obj) {
        return (obj instanceof FingerprintHash) && Intrinsics.areEqual(hash128Bits, ((FingerprintHash) obj).m6130unboximpl());
    }

    /* JADX INFO: renamed from: equals-impl0, reason: not valid java name */
    public static final boolean m6126equalsimpl0(Hash128Bits hash128Bits, Hash128Bits hash128Bits2) {
        return Intrinsics.areEqual(hash128Bits, hash128Bits2);
    }

    /* JADX INFO: renamed from: hashCode-impl, reason: not valid java name */
    public static int m6127hashCodeimpl(Hash128Bits hash128Bits) {
        return hash128Bits.hashCode();
    }

    public boolean equals(Object obj) {
        return m6125equalsimpl(this.hash, obj);
    }

    public int hashCode() {
        return m6127hashCodeimpl(this.hash);
    }

    /* JADX INFO: renamed from: unbox-impl, reason: not valid java name */
    public final /* synthetic */ Hash128Bits m6130unboximpl() {
        return this.hash;
    }

    private /* synthetic */ FingerprintHash(Hash128Bits hash128Bits) {
        this.hash = hash128Bits;
    }

    public final Hash128Bits getHash() {
        return this.hash;
    }

    public String toString() {
        return m6129toStringimpl(this.hash);
    }

    /* JADX INFO: renamed from: toString-impl, reason: not valid java name */
    public static String m6129toStringimpl(Hash128Bits hash128Bits) {
        return UStringsKt.m4237toStringJSWoG40(hash128Bits.m6139getLowBytessVKNKU(), 36) + '.' + UStringsKt.m4237toStringJSWoG40(hash128Bits.m6138getHighBytessVKNKU(), 36);
    }

    /* JADX INFO: renamed from: toByteArray-impl, reason: not valid java name */
    public static final byte[] m6128toByteArrayimpl(Hash128Bits hash128Bits) {
        ByteBuffer byteBufferAllocate = ByteBuffer.allocate(16);
        byteBufferAllocate.putLong(hash128Bits.m6139getLowBytessVKNKU());
        byteBufferAllocate.putLong(8, hash128Bits.m6138getHighBytessVKNKU());
        byte[] bArrArray = byteBufferAllocate.array();
        Intrinsics.checkNotNullExpressionValue(bArrArray, "array(...)");
        return bArrArray;
    }

    /* JADX INFO: compiled from: FileFingerprints.kt */
    @Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0012\n\u0002\b\u0007\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u001b\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bø\u0001\u0000ø\u0001\u0001¢\u0006\u0004\b\t\u0010\nJ\u001d\u0010\u000b\u001a\u0004\u0018\u00010\u00062\u0006\u0010\f\u001a\u00020\u0004ø\u0001\u0000ø\u0001\u0001¢\u0006\u0004\b\r\u0010\u000eR\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n\u0000\u0082\u0002\u000b\n\u0002\b!\n\u0005\b¡\u001e0\u0001¨\u0006\u000f"}, d2 = {"Lorg/jetbrains/kotlin/backend/common/serialization/FingerprintHash$Companion;", "", "()V", "HASH_SEPARATOR", "", "fromByteArray", "Lorg/jetbrains/kotlin/backend/common/serialization/FingerprintHash;", "bytes", "", "fromByteArray-uGak_n4", "([B)Lorg/jetbrains/kotlin/backend/common/serialization/Hash128Bits;", "fromString", "s", "fromString-LA3xOQI", "(Ljava/lang/String;)Lorg/jetbrains/kotlin/backend/common/serialization/Hash128Bits;", "ir.serialization.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        /* JADX INFO: renamed from: fromString-LA3xOQI, reason: not valid java name */
        public final Hash128Bits m6132fromStringLA3xOQI(String s) {
            Intrinsics.checkNotNullParameter(s, "s");
            List listSplit$default = StringsKt.split$default((CharSequence) s, new String[]{"."}, false, 0, 6, (Object) null);
            ArrayList arrayList = new ArrayList();
            Iterator it = listSplit$default.iterator();
            while (it.hasNext()) {
                ULong uLongOrNull = UStringsKt.toULongOrNull((String) it.next(), 36);
                if (uLongOrNull != null) {
                    arrayList.add(uLongOrNull);
                }
            }
            ArrayList arrayList2 = arrayList;
            if (arrayList2.size() != 2) {
                arrayList2 = null;
            }
            if (arrayList2 != null) {
                return FingerprintHash.m6124constructorimpl(new Hash128Bits(((ULong) arrayList2.get(0)).getData(), ((ULong) arrayList2.get(1)).getData(), null));
            }
            return null;
        }

        /* JADX INFO: renamed from: fromByteArray-uGak_n4, reason: not valid java name */
        public final Hash128Bits m6131fromByteArrayuGak_n4(byte[] bytes) {
            Intrinsics.checkNotNullParameter(bytes, "bytes");
            ByteBuffer byteBufferWrap = ByteBuffer.wrap(bytes);
            return FingerprintHash.m6124constructorimpl(new Hash128Bits(ULong.m3072constructorimpl(byteBufferWrap.getLong(0)), ULong.m3072constructorimpl(byteBufferWrap.getLong(8)), null));
        }
    }
}
