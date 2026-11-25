package org.jetbrains.kotlin.backend.common.serialization.encodings;

import kotlin.Metadata;
import kotlin.jvm.JvmInline;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.catrobat.catroid.web.ServerAuthenticationConstants;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.types.Variance;

/* JADX INFO: compiled from: BinaryTypeProjection.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\t\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0010\u000e\n\u0002\b\u0006\b\u0087@\u0018\u0000  2\u00020\u0001:\u0001 B\u000f\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0004\b\u0004\u0010\u0005J\u001a\u0010\u0014\u001a\u00020\t2\b\u0010\u0015\u001a\u0004\u0018\u00010\u0001HÖ\u0003¢\u0006\u0004\b\u0016\u0010\u0017J\u0010\u0010\u0018\u001a\u00020\rHÖ\u0001¢\u0006\u0004\b\u0019\u0010\u000fJ\u0010\u0010\u001a\u001a\u00020\u001bHÖ\u0001¢\u0006\u0004\b\u001c\u0010\u001dJ\u000f\u0010\u001e\u001a\u00020\rH\u0002¢\u0006\u0004\b\u001f\u0010\u000fR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007R\u0011\u0010\b\u001a\u00020\t8F¢\u0006\u0006\u001a\u0004\b\n\u0010\u000bR\u0011\u0010\f\u001a\u00020\r8F¢\u0006\u0006\u001a\u0004\b\u000e\u0010\u000fR\u0011\u0010\u0010\u001a\u00020\u00118F¢\u0006\u0006\u001a\u0004\b\u0012\u0010\u0013\u0088\u0001\u0002¨\u0006!"}, d2 = {"Lorg/jetbrains/kotlin/backend/common/serialization/encodings/BinaryTypeProjection;", "", ServerAuthenticationConstants.SIGNIN_GOOGLE_CODE_KEY, "", "constructor-impl", "(J)J", "getCode", "()J", "isStarProjection", "", "isStarProjection-impl", "(J)Z", "typeIndex", "", "getTypeIndex-impl", "(J)I", "variance", "Lorg/jetbrains/kotlin/types/Variance;", "getVariance-impl", "(J)Lorg/jetbrains/kotlin/types/Variance;", Namer.EQUALS_METHOD_NAME, "other", "equals-impl", "(JLjava/lang/Object;)Z", "hashCode", "hashCode-impl", "toString", "", "toString-impl", "(J)Ljava/lang/String;", "varianceId", "varianceId-impl", "Companion", "ir.serialization.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
@JvmInline
public final class BinaryTypeProjection {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    public static final long STAR_CODE = 0;
    private final long code;

    /* JADX INFO: renamed from: box-impl, reason: not valid java name */
    public static final /* synthetic */ BinaryTypeProjection m6212boximpl(long j) {
        return new BinaryTypeProjection(j);
    }

    /* JADX INFO: renamed from: constructor-impl, reason: not valid java name */
    public static long m6213constructorimpl(long j) {
        return j;
    }

    /* JADX INFO: renamed from: equals-impl, reason: not valid java name */
    public static boolean m6214equalsimpl(long j, Object obj) {
        return (obj instanceof BinaryTypeProjection) && j == ((BinaryTypeProjection) obj).m6222unboximpl();
    }

    /* JADX INFO: renamed from: equals-impl0, reason: not valid java name */
    public static final boolean m6215equalsimpl0(long j, long j2) {
        return j == j2;
    }

    /* JADX INFO: renamed from: getTypeIndex-impl, reason: not valid java name */
    public static final int m6216getTypeIndeximpl(long j) {
        return (int) (j >>> 2);
    }

    /* JADX INFO: renamed from: hashCode-impl, reason: not valid java name */
    public static int m6218hashCodeimpl(long j) {
        return Long.hashCode(j);
    }

    /* JADX INFO: renamed from: isStarProjection-impl, reason: not valid java name */
    public static final boolean m6219isStarProjectionimpl(long j) {
        return j == 0;
    }

    /* JADX INFO: renamed from: toString-impl, reason: not valid java name */
    public static String m6220toStringimpl(long j) {
        return "BinaryTypeProjection(code=" + j + ')';
    }

    /* JADX INFO: renamed from: varianceId-impl, reason: not valid java name */
    private static final int m6221varianceIdimpl(long j) {
        return ((int) (j & 3)) - 1;
    }

    public boolean equals(Object obj) {
        return m6214equalsimpl(this.code, obj);
    }

    public int hashCode() {
        return m6218hashCodeimpl(this.code);
    }

    public String toString() {
        return m6220toStringimpl(this.code);
    }

    /* JADX INFO: renamed from: unbox-impl, reason: not valid java name */
    public final /* synthetic */ long m6222unboximpl() {
        return this.code;
    }

    private /* synthetic */ BinaryTypeProjection(long j) {
        this.code = j;
    }

    public final long getCode() {
        return this.code;
    }

    /* JADX INFO: renamed from: getVariance-impl, reason: not valid java name */
    public static final Variance m6217getVarianceimpl(long j) {
        m6219isStarProjectionimpl(j);
        return Variance.values()[m6221varianceIdimpl(j)];
    }

    /* JADX INFO: compiled from: BinaryTypeProjection.kt */
    @Metadata(d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\t\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u001b\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u0004ø\u0001\u0000ø\u0001\u0001¢\u0006\u0004\b\b\u0010\tJ\u0016\u0010\n\u001a\u00020\u00042\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000eR\u000e\u0010\u0003\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000\u0082\u0002\u000b\n\u0002\b!\n\u0005\b¡\u001e0\u0001¨\u0006\u000f"}, d2 = {"Lorg/jetbrains/kotlin/backend/common/serialization/encodings/BinaryTypeProjection$Companion;", "", "()V", "STAR_CODE", "", "decode", "Lorg/jetbrains/kotlin/backend/common/serialization/encodings/BinaryTypeProjection;", ServerAuthenticationConstants.SIGNIN_GOOGLE_CODE_KEY, "decode-2ztXSlc", "(J)J", "encodeType", "variance", "Lorg/jetbrains/kotlin/types/Variance;", "typeIndex", "", "ir.serialization.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final long encodeType(Variance variance, int typeIndex) {
            Intrinsics.checkNotNullParameter(variance, "variance");
            return ((long) (variance.ordinal() + 1)) | (((long) typeIndex) << 2);
        }

        /* JADX INFO: renamed from: decode-2ztXSlc, reason: not valid java name */
        public final long m6223decode2ztXSlc(long code) {
            return BinaryTypeProjection.m6213constructorimpl(code);
        }
    }
}
