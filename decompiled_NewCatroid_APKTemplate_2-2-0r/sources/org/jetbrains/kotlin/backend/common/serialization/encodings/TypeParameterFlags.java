package org.jetbrains.kotlin.backend.common.serialization.encodings;

import com.reandroid.arsc.chunk.OverlayablePolicy;
import kotlin.Metadata;
import kotlin.jvm.JvmInline;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.catrobat.catroid.web.ServerAuthenticationConstants;
import org.jetbrains.kotlin.backend.common.serialization.IrFlags;
import org.jetbrains.kotlin.ir.declarations.IrTypeParameter;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.metadata.ProtoBuf;
import org.jetbrains.kotlin.serialization.deserialization.ProtoEnumFlags;
import org.jetbrains.kotlin.types.Variance;

/* JADX INFO: compiled from: BinaryFlags.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\t\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0004\b\u0087@\u0018\u0000 \u001c2\u00020\u0001:\u0001\u001cB\u000f\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0004\b\u0004\u0010\u0005J\u001a\u0010\u0010\u001a\u00020\t2\b\u0010\u0011\u001a\u0004\u0018\u00010\u0001HÖ\u0003¢\u0006\u0004\b\u0012\u0010\u0013J\u0010\u0010\u0014\u001a\u00020\u0015HÖ\u0001¢\u0006\u0004\b\u0016\u0010\u0017J\u0010\u0010\u0018\u001a\u00020\u0019HÖ\u0001¢\u0006\u0004\b\u001a\u0010\u001bR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007R\u0011\u0010\b\u001a\u00020\t8F¢\u0006\u0006\u001a\u0004\b\n\u0010\u000bR\u0011\u0010\f\u001a\u00020\r8F¢\u0006\u0006\u001a\u0004\b\u000e\u0010\u000f\u0088\u0001\u0002¨\u0006\u001d"}, d2 = {"Lorg/jetbrains/kotlin/backend/common/serialization/encodings/TypeParameterFlags;", "", OverlayablePolicy.NAME_flags, "", "constructor-impl", "(J)J", "getFlags", "()J", "isReified", "", "isReified-impl", "(J)Z", "variance", "Lorg/jetbrains/kotlin/types/Variance;", "getVariance-impl", "(J)Lorg/jetbrains/kotlin/types/Variance;", Namer.EQUALS_METHOD_NAME, "other", "equals-impl", "(JLjava/lang/Object;)Z", "hashCode", "", "hashCode-impl", "(J)I", "toString", "", "toString-impl", "(J)Ljava/lang/String;", "Companion", "ir.serialization.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
@JvmInline
public final class TypeParameterFlags {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private final long flags;

    /* JADX INFO: renamed from: box-impl, reason: not valid java name */
    public static final /* synthetic */ TypeParameterFlags m6313boximpl(long j) {
        return new TypeParameterFlags(j);
    }

    /* JADX INFO: renamed from: constructor-impl, reason: not valid java name */
    public static long m6314constructorimpl(long j) {
        return j;
    }

    /* JADX INFO: renamed from: equals-impl, reason: not valid java name */
    public static boolean m6315equalsimpl(long j, Object obj) {
        return (obj instanceof TypeParameterFlags) && j == ((TypeParameterFlags) obj).m6321unboximpl();
    }

    /* JADX INFO: renamed from: equals-impl0, reason: not valid java name */
    public static final boolean m6316equalsimpl0(long j, long j2) {
        return j == j2;
    }

    /* JADX INFO: renamed from: hashCode-impl, reason: not valid java name */
    public static int m6318hashCodeimpl(long j) {
        return Long.hashCode(j);
    }

    /* JADX INFO: renamed from: toString-impl, reason: not valid java name */
    public static String m6320toStringimpl(long j) {
        return "TypeParameterFlags(flags=" + j + ')';
    }

    public boolean equals(Object obj) {
        return m6315equalsimpl(this.flags, obj);
    }

    public int hashCode() {
        return m6318hashCodeimpl(this.flags);
    }

    public String toString() {
        return m6320toStringimpl(this.flags);
    }

    /* JADX INFO: renamed from: unbox-impl, reason: not valid java name */
    public final /* synthetic */ long m6321unboximpl() {
        return this.flags;
    }

    private /* synthetic */ TypeParameterFlags(long j) {
        this.flags = j;
    }

    public final long getFlags() {
        return this.flags;
    }

    /* JADX INFO: renamed from: getVariance-impl, reason: not valid java name */
    public static final Variance m6317getVarianceimpl(long j) {
        ProtoEnumFlags protoEnumFlags = ProtoEnumFlags.INSTANCE;
        ProtoBuf.TypeParameter.Variance variance = IrFlags.VARIANCE.get((int) j);
        Intrinsics.checkNotNullExpressionValue(variance, "get(...)");
        return protoEnumFlags.variance(variance);
    }

    /* JADX INFO: renamed from: isReified-impl, reason: not valid java name */
    public static final boolean m6319isReifiedimpl(long j) {
        Boolean bool = IrFlags.IS_REIFIED.get((int) j);
        Intrinsics.checkNotNullExpressionValue(bool, "get(...)");
        return bool.booleanValue();
    }

    /* JADX INFO: compiled from: BinaryFlags.kt */
    @Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\t\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u001b\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006ø\u0001\u0000ø\u0001\u0001¢\u0006\u0004\b\u0007\u0010\bJ\u000e\u0010\t\u001a\u00020\u00062\u0006\u0010\n\u001a\u00020\u000b\u0082\u0002\u000b\n\u0002\b!\n\u0005\b¡\u001e0\u0001¨\u0006\f"}, d2 = {"Lorg/jetbrains/kotlin/backend/common/serialization/encodings/TypeParameterFlags$Companion;", "", "()V", "decode", "Lorg/jetbrains/kotlin/backend/common/serialization/encodings/TypeParameterFlags;", ServerAuthenticationConstants.SIGNIN_GOOGLE_CODE_KEY, "", "decode-dTrNHuw", "(J)J", "encode", "typeParameter", "Lorg/jetbrains/kotlin/ir/declarations/IrTypeParameter;", "ir.serialization.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final long encode(IrTypeParameter typeParameter) {
            Intrinsics.checkNotNullParameter(typeParameter, "typeParameter");
            return IrFlags.getTypeParameterFlags(!typeParameter.getAnnotations().isEmpty(), ProtoEnumFlags.INSTANCE.variance(typeParameter.getVariance()), typeParameter.getIsReified());
        }

        /* JADX INFO: renamed from: decode-dTrNHuw, reason: not valid java name */
        public final long m6322decodedTrNHuw(long code) {
            return TypeParameterFlags.m6314constructorimpl(code);
        }
    }
}
