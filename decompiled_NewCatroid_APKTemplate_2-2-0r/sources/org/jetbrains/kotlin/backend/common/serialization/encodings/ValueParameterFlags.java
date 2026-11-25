package org.jetbrains.kotlin.backend.common.serialization.encodings;

import com.reandroid.arsc.chunk.OverlayablePolicy;
import kotlin.Metadata;
import kotlin.jvm.JvmInline;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.catrobat.catroid.web.ServerAuthenticationConstants;
import org.jetbrains.kotlin.backend.common.serialization.IrFlags;
import org.jetbrains.kotlin.ir.declarations.IrValueParameter;
import org.jetbrains.kotlin.js.translate.context.Namer;

/* JADX INFO: compiled from: BinaryFlags.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\t\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0002\b\r\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0004\b\u0087@\u0018\u0000 \u001e2\u00020\u0001:\u0001\u001eB\u000f\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0004\b\u0004\u0010\u0005J\u001a\u0010\u0012\u001a\u00020\t2\b\u0010\u0013\u001a\u0004\u0018\u00010\u0001HÖ\u0003¢\u0006\u0004\b\u0014\u0010\u0015J\u0010\u0010\u0016\u001a\u00020\u0017HÖ\u0001¢\u0006\u0004\b\u0018\u0010\u0019J\u0010\u0010\u001a\u001a\u00020\u001bHÖ\u0001¢\u0006\u0004\b\u001c\u0010\u001dR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007R\u0011\u0010\b\u001a\u00020\t8F¢\u0006\u0006\u001a\u0004\b\n\u0010\u000bR\u0011\u0010\f\u001a\u00020\t8F¢\u0006\u0006\u001a\u0004\b\r\u0010\u000bR\u0011\u0010\u000e\u001a\u00020\t8F¢\u0006\u0006\u001a\u0004\b\u000f\u0010\u000bR\u0011\u0010\u0010\u001a\u00020\t8F¢\u0006\u0006\u001a\u0004\b\u0011\u0010\u000b\u0088\u0001\u0002¨\u0006\u001f"}, d2 = {"Lorg/jetbrains/kotlin/backend/common/serialization/encodings/ValueParameterFlags;", "", OverlayablePolicy.NAME_flags, "", "constructor-impl", "(J)J", "getFlags", "()J", "isAssignable", "", "isAssignable-impl", "(J)Z", "isCrossInline", "isCrossInline-impl", "isHidden", "isHidden-impl", "isNoInline", "isNoInline-impl", Namer.EQUALS_METHOD_NAME, "other", "equals-impl", "(JLjava/lang/Object;)Z", "hashCode", "", "hashCode-impl", "(J)I", "toString", "", "toString-impl", "(J)Ljava/lang/String;", "Companion", "ir.serialization.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
@JvmInline
public final class ValueParameterFlags {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private final long flags;

    /* JADX INFO: renamed from: box-impl, reason: not valid java name */
    public static final /* synthetic */ ValueParameterFlags m6323boximpl(long j) {
        return new ValueParameterFlags(j);
    }

    /* JADX INFO: renamed from: constructor-impl, reason: not valid java name */
    public static long m6324constructorimpl(long j) {
        return j;
    }

    /* JADX INFO: renamed from: equals-impl, reason: not valid java name */
    public static boolean m6325equalsimpl(long j, Object obj) {
        return (obj instanceof ValueParameterFlags) && j == ((ValueParameterFlags) obj).m6333unboximpl();
    }

    /* JADX INFO: renamed from: equals-impl0, reason: not valid java name */
    public static final boolean m6326equalsimpl0(long j, long j2) {
        return j == j2;
    }

    /* JADX INFO: renamed from: hashCode-impl, reason: not valid java name */
    public static int m6327hashCodeimpl(long j) {
        return Long.hashCode(j);
    }

    /* JADX INFO: renamed from: toString-impl, reason: not valid java name */
    public static String m6332toStringimpl(long j) {
        return "ValueParameterFlags(flags=" + j + ')';
    }

    public boolean equals(Object obj) {
        return m6325equalsimpl(this.flags, obj);
    }

    public int hashCode() {
        return m6327hashCodeimpl(this.flags);
    }

    public String toString() {
        return m6332toStringimpl(this.flags);
    }

    /* JADX INFO: renamed from: unbox-impl, reason: not valid java name */
    public final /* synthetic */ long m6333unboximpl() {
        return this.flags;
    }

    private /* synthetic */ ValueParameterFlags(long j) {
        this.flags = j;
    }

    public final long getFlags() {
        return this.flags;
    }

    /* JADX INFO: renamed from: isCrossInline-impl, reason: not valid java name */
    public static final boolean m6329isCrossInlineimpl(long j) {
        Boolean bool = IrFlags.IS_CROSSINLINE.get((int) j);
        Intrinsics.checkNotNullExpressionValue(bool, "get(...)");
        return bool.booleanValue();
    }

    /* JADX INFO: renamed from: isNoInline-impl, reason: not valid java name */
    public static final boolean m6331isNoInlineimpl(long j) {
        Boolean bool = IrFlags.IS_NOINLINE.get((int) j);
        Intrinsics.checkNotNullExpressionValue(bool, "get(...)");
        return bool.booleanValue();
    }

    /* JADX INFO: renamed from: isHidden-impl, reason: not valid java name */
    public static final boolean m6330isHiddenimpl(long j) {
        Boolean bool = IrFlags.IS_HIDDEN.get((int) j);
        Intrinsics.checkNotNullExpressionValue(bool, "get(...)");
        return bool.booleanValue();
    }

    /* JADX INFO: renamed from: isAssignable-impl, reason: not valid java name */
    public static final boolean m6328isAssignableimpl(long j) {
        Boolean bool = IrFlags.IS_ASSIGNABLE.get((int) j);
        Intrinsics.checkNotNullExpressionValue(bool, "get(...)");
        return bool.booleanValue();
    }

    /* JADX INFO: compiled from: BinaryFlags.kt */
    @Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\t\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u001b\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006ø\u0001\u0000ø\u0001\u0001¢\u0006\u0004\b\u0007\u0010\bJ\u000e\u0010\t\u001a\u00020\u00062\u0006\u0010\n\u001a\u00020\u000b\u0082\u0002\u000b\n\u0002\b!\n\u0005\b¡\u001e0\u0001¨\u0006\f"}, d2 = {"Lorg/jetbrains/kotlin/backend/common/serialization/encodings/ValueParameterFlags$Companion;", "", "()V", "decode", "Lorg/jetbrains/kotlin/backend/common/serialization/encodings/ValueParameterFlags;", ServerAuthenticationConstants.SIGNIN_GOOGLE_CODE_KEY, "", "decode-eyNVhwA", "(J)J", "encode", "param", "Lorg/jetbrains/kotlin/ir/declarations/IrValueParameter;", "ir.serialization.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final long encode(IrValueParameter param) {
            Intrinsics.checkNotNullParameter(param, "param");
            return IrFlags.getValueParameterFlags(!param.getAnnotations().isEmpty(), param.getDefaultValue() != null, param.isCrossinline(), param.isNoinline(), param.isHidden(), param.isAssignable());
        }

        /* JADX INFO: renamed from: decode-eyNVhwA, reason: not valid java name */
        public final long m6334decodeeyNVhwA(long code) {
            return ValueParameterFlags.m6324constructorimpl(code);
        }
    }
}
