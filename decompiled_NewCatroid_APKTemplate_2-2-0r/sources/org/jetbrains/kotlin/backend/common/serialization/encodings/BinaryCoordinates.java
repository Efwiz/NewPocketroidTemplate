package org.jetbrains.kotlin.backend.common.serialization.encodings;

import kotlin.Metadata;
import kotlin.jvm.JvmInline;
import kotlin.jvm.internal.DefaultConstructorMarker;
import org.catrobat.catroid.web.ServerAuthenticationConstants;
import org.jetbrains.kotlin.js.translate.context.Namer;

/* JADX INFO: compiled from: BinaryCoordinates.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\b\n\u0002\b\u0007\n\u0002\u0010\u000b\n\u0002\b\u0006\n\u0002\u0010\u000e\n\u0002\b\u0004\b\u0087@\u0018\u0000 \u001a2\u00020\u0001:\u0001\u001aB\u000f\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0004\b\u0004\u0010\u0005J\u000f\u0010\r\u001a\u00020\bH\u0002¢\u0006\u0004\b\u000e\u0010\nJ\u001a\u0010\u000f\u001a\u00020\u00102\b\u0010\u0011\u001a\u0004\u0018\u00010\u0001HÖ\u0003¢\u0006\u0004\b\u0012\u0010\u0013J\u0010\u0010\u0014\u001a\u00020\bHÖ\u0001¢\u0006\u0004\b\u0015\u0010\nJ\u0010\u0010\u0016\u001a\u00020\u0017HÖ\u0001¢\u0006\u0004\b\u0018\u0010\u0019R\u0016\u0010\u0002\u001a\u00020\u0003X\u0082\u0004ø\u0001\u0000ø\u0001\u0001¢\u0006\u0004\n\u0002\u0010\u0006R\u0011\u0010\u0007\u001a\u00020\b8F¢\u0006\u0006\u001a\u0004\b\t\u0010\nR\u0011\u0010\u000b\u001a\u00020\b8F¢\u0006\u0006\u001a\u0004\b\f\u0010\n\u0088\u0001\u0002\u0092\u0001\u00020\u0003\u0082\u0002\u000b\n\u0005\b¡\u001e0\u0001\n\u0002\b!¨\u0006\u001b"}, d2 = {"Lorg/jetbrains/kotlin/backend/common/serialization/encodings/BinaryCoordinates;", "", "decoded", "Lorg/jetbrains/kotlin/backend/common/serialization/encodings/BinaryLattice;", "constructor-impl", "(J)J", "J", "endOffset", "", "getEndOffset-impl", "(J)I", "startOffset", "getStartOffset-impl", "diff", "diff-impl", Namer.EQUALS_METHOD_NAME, "", "other", "equals-impl", "(JLjava/lang/Object;)Z", "hashCode", "hashCode-impl", "toString", "", "toString-impl", "(J)Ljava/lang/String;", "Companion", "ir.serialization.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
@JvmInline
public final class BinaryCoordinates {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private final long decoded;

    /* JADX INFO: renamed from: box-impl, reason: not valid java name */
    public static final /* synthetic */ BinaryCoordinates m6170boximpl(long j) {
        return new BinaryCoordinates(j);
    }

    /* JADX INFO: renamed from: constructor-impl, reason: not valid java name */
    public static long m6171constructorimpl(long j) {
        return j;
    }

    /* JADX INFO: renamed from: equals-impl, reason: not valid java name */
    public static boolean m6173equalsimpl(long j, Object obj) {
        return (obj instanceof BinaryCoordinates) && BinaryLattice.m6184equalsimpl0(j, ((BinaryCoordinates) obj).getDecoded());
    }

    /* JADX INFO: renamed from: equals-impl0, reason: not valid java name */
    public static final boolean m6174equalsimpl0(long j, long j2) {
        return BinaryLattice.m6184equalsimpl0(j, j2);
    }

    /* JADX INFO: renamed from: hashCode-impl, reason: not valid java name */
    public static int m6177hashCodeimpl(long j) {
        return BinaryLattice.m6187hashCodeimpl(j);
    }

    /* JADX INFO: renamed from: toString-impl, reason: not valid java name */
    public static String m6178toStringimpl(long j) {
        return "BinaryCoordinates(decoded=" + ((Object) BinaryLattice.m6188toStringimpl(j)) + ')';
    }

    public boolean equals(Object obj) {
        return m6173equalsimpl(this.decoded, obj);
    }

    public int hashCode() {
        return m6177hashCodeimpl(this.decoded);
    }

    public String toString() {
        return m6178toStringimpl(this.decoded);
    }

    /* JADX INFO: renamed from: unbox-impl, reason: not valid java name and from getter */
    public final /* synthetic */ long getDecoded() {
        return this.decoded;
    }

    private /* synthetic */ BinaryCoordinates(long j) {
        this.decoded = j;
    }

    /* JADX INFO: renamed from: diff-impl, reason: not valid java name */
    private static final int m6172diffimpl(long j) {
        return BinaryLattice.m6186getSecondimpl(j);
    }

    /* JADX INFO: renamed from: getStartOffset-impl, reason: not valid java name */
    public static final int m6176getStartOffsetimpl(long j) {
        return BinaryLattice.m6185getFirstimpl(j);
    }

    /* JADX INFO: renamed from: getEndOffset-impl, reason: not valid java name */
    public static final int m6175getEndOffsetimpl(long j) {
        return m6176getStartOffsetimpl(j) + m6172diffimpl(j);
    }

    /* JADX INFO: compiled from: BinaryCoordinates.kt */
    @Metadata(d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\t\n\u0002\b\u0004\n\u0002\u0010\b\n\u0002\b\u0002\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u001b\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006ø\u0001\u0000ø\u0001\u0001¢\u0006\u0004\b\u0007\u0010\bJ\u0016\u0010\t\u001a\u00020\u00062\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\u000b\u0082\u0002\u000b\n\u0002\b!\n\u0005\b¡\u001e0\u0001¨\u0006\r"}, d2 = {"Lorg/jetbrains/kotlin/backend/common/serialization/encodings/BinaryCoordinates$Companion;", "", "()V", "decode", "Lorg/jetbrains/kotlin/backend/common/serialization/encodings/BinaryCoordinates;", ServerAuthenticationConstants.SIGNIN_GOOGLE_CODE_KEY, "", "decode-ViYoqTc", "(J)J", "encode", "startOffset", "", "endOffset", "ir.serialization.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final long encode(int startOffset, int endOffset) {
            return BinaryLattice.INSTANCE.encode(startOffset, Math.abs(endOffset - startOffset));
        }

        /* JADX INFO: renamed from: decode-ViYoqTc, reason: not valid java name */
        public final long m6180decodeViYoqTc(long code) {
            long jM6171constructorimpl = BinaryCoordinates.m6171constructorimpl(BinaryLattice.INSTANCE.m6190decode9PDhHO0(code));
            BinaryCoordinates.m6176getStartOffsetimpl(jM6171constructorimpl);
            BinaryCoordinates.m6175getEndOffsetimpl(jM6171constructorimpl);
            return jM6171constructorimpl;
        }
    }
}
