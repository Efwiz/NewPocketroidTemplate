package org.jetbrains.kotlin.backend.common.serialization.encodings;

import kotlin.Metadata;
import kotlin.jvm.JvmInline;
import kotlin.jvm.internal.DefaultConstructorMarker;
import org.catrobat.catroid.web.ServerAuthenticationConstants;
import org.jetbrains.kotlin.js.translate.context.Namer;

/* JADX INFO: compiled from: BinaryNameAndType.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0002\b\u0006\n\u0002\u0010\u000e\n\u0002\b\u0004\b\u0087@\u0018\u0000 \u00182\u00020\u0001:\u0001\u0018B\u000f\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0004\b\u0004\u0010\u0005J\u001a\u0010\r\u001a\u00020\u000e2\b\u0010\u000f\u001a\u0004\u0018\u00010\u0001HÖ\u0003¢\u0006\u0004\b\u0010\u0010\u0011J\u0010\u0010\u0012\u001a\u00020\bHÖ\u0001¢\u0006\u0004\b\u0013\u0010\nJ\u0010\u0010\u0014\u001a\u00020\u0015HÖ\u0001¢\u0006\u0004\b\u0016\u0010\u0017R\u0016\u0010\u0002\u001a\u00020\u0003X\u0082\u0004ø\u0001\u0000ø\u0001\u0001¢\u0006\u0004\n\u0002\u0010\u0006R\u0011\u0010\u0007\u001a\u00020\b8F¢\u0006\u0006\u001a\u0004\b\t\u0010\nR\u0011\u0010\u000b\u001a\u00020\b8F¢\u0006\u0006\u001a\u0004\b\f\u0010\n\u0088\u0001\u0002\u0092\u0001\u00020\u0003\u0082\u0002\u000b\n\u0005\b¡\u001e0\u0001\n\u0002\b!¨\u0006\u0019"}, d2 = {"Lorg/jetbrains/kotlin/backend/common/serialization/encodings/BinaryNameAndType;", "", "decoded", "Lorg/jetbrains/kotlin/backend/common/serialization/encodings/BinaryLattice;", "constructor-impl", "(J)J", "J", "nameIndex", "", "getNameIndex-impl", "(J)I", "typeIndex", "getTypeIndex-impl", Namer.EQUALS_METHOD_NAME, "", "other", "equals-impl", "(JLjava/lang/Object;)Z", "hashCode", "hashCode-impl", "toString", "", "toString-impl", "(J)Ljava/lang/String;", "Companion", "ir.serialization.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
@JvmInline
public final class BinaryNameAndType {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private final long decoded;

    /* JADX INFO: renamed from: box-impl, reason: not valid java name */
    public static final /* synthetic */ BinaryNameAndType m6191boximpl(long j) {
        return new BinaryNameAndType(j);
    }

    /* JADX INFO: renamed from: constructor-impl, reason: not valid java name */
    public static long m6192constructorimpl(long j) {
        return j;
    }

    /* JADX INFO: renamed from: equals-impl, reason: not valid java name */
    public static boolean m6193equalsimpl(long j, Object obj) {
        return (obj instanceof BinaryNameAndType) && BinaryLattice.m6184equalsimpl0(j, ((BinaryNameAndType) obj).getDecoded());
    }

    /* JADX INFO: renamed from: equals-impl0, reason: not valid java name */
    public static final boolean m6194equalsimpl0(long j, long j2) {
        return BinaryLattice.m6184equalsimpl0(j, j2);
    }

    /* JADX INFO: renamed from: hashCode-impl, reason: not valid java name */
    public static int m6197hashCodeimpl(long j) {
        return BinaryLattice.m6187hashCodeimpl(j);
    }

    /* JADX INFO: renamed from: toString-impl, reason: not valid java name */
    public static String m6198toStringimpl(long j) {
        return "BinaryNameAndType(decoded=" + ((Object) BinaryLattice.m6188toStringimpl(j)) + ')';
    }

    public boolean equals(Object obj) {
        return m6193equalsimpl(this.decoded, obj);
    }

    public int hashCode() {
        return m6197hashCodeimpl(this.decoded);
    }

    public String toString() {
        return m6198toStringimpl(this.decoded);
    }

    /* JADX INFO: renamed from: unbox-impl, reason: not valid java name and from getter */
    public final /* synthetic */ long getDecoded() {
        return this.decoded;
    }

    private /* synthetic */ BinaryNameAndType(long j) {
        this.decoded = j;
    }

    /* JADX INFO: renamed from: getNameIndex-impl, reason: not valid java name */
    public static final int m6195getNameIndeximpl(long j) {
        return BinaryLattice.m6185getFirstimpl(j);
    }

    /* JADX INFO: renamed from: getTypeIndex-impl, reason: not valid java name */
    public static final int m6196getTypeIndeximpl(long j) {
        return BinaryLattice.m6186getSecondimpl(j);
    }

    /* JADX INFO: compiled from: BinaryNameAndType.kt */
    @Metadata(d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\t\n\u0002\b\u0004\n\u0002\u0010\b\n\u0002\b\u0002\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u001b\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006ø\u0001\u0000ø\u0001\u0001¢\u0006\u0004\b\u0007\u0010\bJ\u0016\u0010\t\u001a\u00020\u00062\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\u000b\u0082\u0002\u000b\n\u0002\b!\n\u0005\b¡\u001e0\u0001¨\u0006\r"}, d2 = {"Lorg/jetbrains/kotlin/backend/common/serialization/encodings/BinaryNameAndType$Companion;", "", "()V", "decode", "Lorg/jetbrains/kotlin/backend/common/serialization/encodings/BinaryNameAndType;", ServerAuthenticationConstants.SIGNIN_GOOGLE_CODE_KEY, "", "decode-WXC2TjU", "(J)J", "encode", "nameIndex", "", "typeIndex", "ir.serialization.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final long encode(int nameIndex, int typeIndex) {
            return BinaryLattice.INSTANCE.encode(nameIndex, typeIndex);
        }

        /* JADX INFO: renamed from: decode-WXC2TjU, reason: not valid java name */
        public final long m6200decodeWXC2TjU(long code) {
            return BinaryNameAndType.m6192constructorimpl(BinaryLattice.INSTANCE.m6190decode9PDhHO0(code));
        }
    }
}
