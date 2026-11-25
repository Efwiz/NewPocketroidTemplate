package org.jetbrains.kotlin.backend.common.serialization.encodings;

import com.reandroid.arsc.chunk.OverlayablePolicy;
import kotlin.Metadata;
import kotlin.jvm.JvmInline;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.catrobat.catroid.web.ServerAuthenticationConstants;
import org.jetbrains.kotlin.backend.common.serialization.IrFlags;
import org.jetbrains.kotlin.descriptors.CallableMemberDescriptor;
import org.jetbrains.kotlin.descriptors.DescriptorVisibility;
import org.jetbrains.kotlin.descriptors.Modality;
import org.jetbrains.kotlin.ir.declarations.IrConstructor;
import org.jetbrains.kotlin.ir.declarations.IrSimpleFunction;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.load.java.JvmAbi;
import org.jetbrains.kotlin.metadata.ProtoBuf;
import org.jetbrains.kotlin.serialization.deserialization.ProtoEnumFlags;
import org.jetbrains.kotlin.serialization.deserialization.ProtoEnumFlagsUtilsKt;

/* JADX INFO: compiled from: BinaryFlags.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000B\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\t\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0002\b\u0013\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0004\b\u0087@\u0018\u0000 42\u00020\u0001:\u00014B\u000f\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0004\b\u0004\u0010\u0005J\u001a\u0010$\u001a\u00020\t2\b\u0010%\u001a\u0004\u0018\u00010\u0001HÖ\u0003¢\u0006\u0004\b&\u0010'J\u0010\u0010(\u001a\u00020)HÖ\u0001¢\u0006\u0004\b*\u0010+J\u000f\u0010,\u001a\u00020-H\u0002¢\u0006\u0004\b.\u0010/J\u0010\u00100\u001a\u000201HÖ\u0001¢\u0006\u0004\b2\u00103R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007R\u0011\u0010\b\u001a\u00020\t8F¢\u0006\u0006\u001a\u0004\b\n\u0010\u000bR\u0011\u0010\f\u001a\u00020\t8F¢\u0006\u0006\u001a\u0004\b\r\u0010\u000bR\u0011\u0010\u000e\u001a\u00020\t8F¢\u0006\u0006\u001a\u0004\b\u000f\u0010\u000bR\u0011\u0010\u0010\u001a\u00020\t8F¢\u0006\u0006\u001a\u0004\b\u0011\u0010\u000bR\u0011\u0010\u0012\u001a\u00020\t8F¢\u0006\u0006\u001a\u0004\b\u0013\u0010\u000bR\u0011\u0010\u0014\u001a\u00020\t8F¢\u0006\u0006\u001a\u0004\b\u0015\u0010\u000bR\u0011\u0010\u0016\u001a\u00020\t8F¢\u0006\u0006\u001a\u0004\b\u0017\u0010\u000bR\u0011\u0010\u0018\u001a\u00020\t8F¢\u0006\u0006\u001a\u0004\b\u0019\u0010\u000bR\u0011\u0010\u001a\u001a\u00020\t8F¢\u0006\u0006\u001a\u0004\b\u001b\u0010\u000bR\u0011\u0010\u001c\u001a\u00020\u001d8F¢\u0006\u0006\u001a\u0004\b\u001e\u0010\u001fR\u0011\u0010 \u001a\u00020!8F¢\u0006\u0006\u001a\u0004\b\"\u0010#\u0088\u0001\u0002¨\u00065"}, d2 = {"Lorg/jetbrains/kotlin/backend/common/serialization/encodings/FunctionFlags;", "", OverlayablePolicy.NAME_flags, "", "constructor-impl", "(J)J", "getFlags", "()J", "isExpect", "", "isExpect-impl", "(J)Z", "isExternal", "isExternal-impl", "isFakeOverride", "isFakeOverride-impl", "isInfix", "isInfix-impl", "isInline", "isInline-impl", "isOperator", "isOperator-impl", "isPrimary", "isPrimary-impl", "isSuspend", "isSuspend-impl", "isTailrec", "isTailrec-impl", "modality", "Lorg/jetbrains/kotlin/descriptors/Modality;", "getModality-impl", "(J)Lorg/jetbrains/kotlin/descriptors/Modality;", "visibility", "Lorg/jetbrains/kotlin/descriptors/DescriptorVisibility;", "getVisibility-impl", "(J)Lorg/jetbrains/kotlin/descriptors/DescriptorVisibility;", Namer.EQUALS_METHOD_NAME, "other", "equals-impl", "(JLjava/lang/Object;)Z", "hashCode", "", "hashCode-impl", "(J)I", Namer.METADATA_CLASS_KIND, "Lorg/jetbrains/kotlin/descriptors/CallableMemberDescriptor$Kind;", "kind-impl", "(J)Lorg/jetbrains/kotlin/descriptors/CallableMemberDescriptor$Kind;", "toString", "", "toString-impl", "(J)Ljava/lang/String;", "Companion", "ir.serialization.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
@JvmInline
public final class FunctionFlags {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private final long flags;

    /* JADX INFO: renamed from: box-impl, reason: not valid java name */
    public static final /* synthetic */ FunctionFlags m6254boximpl(long j) {
        return new FunctionFlags(j);
    }

    /* JADX INFO: renamed from: constructor-impl, reason: not valid java name */
    public static long m6255constructorimpl(long j) {
        return j;
    }

    /* JADX INFO: renamed from: equals-impl, reason: not valid java name */
    public static boolean m6256equalsimpl(long j, Object obj) {
        return (obj instanceof FunctionFlags) && j == ((FunctionFlags) obj).m6272unboximpl();
    }

    /* JADX INFO: renamed from: equals-impl0, reason: not valid java name */
    public static final boolean m6257equalsimpl0(long j, long j2) {
        return j == j2;
    }

    /* JADX INFO: renamed from: hashCode-impl, reason: not valid java name */
    public static int m6260hashCodeimpl(long j) {
        return Long.hashCode(j);
    }

    /* JADX INFO: renamed from: toString-impl, reason: not valid java name */
    public static String m6271toStringimpl(long j) {
        return "FunctionFlags(flags=" + j + ')';
    }

    public boolean equals(Object obj) {
        return m6256equalsimpl(this.flags, obj);
    }

    public int hashCode() {
        return m6260hashCodeimpl(this.flags);
    }

    public String toString() {
        return m6271toStringimpl(this.flags);
    }

    /* JADX INFO: renamed from: unbox-impl, reason: not valid java name */
    public final /* synthetic */ long m6272unboximpl() {
        return this.flags;
    }

    private /* synthetic */ FunctionFlags(long j) {
        this.flags = j;
    }

    public final long getFlags() {
        return this.flags;
    }

    /* JADX INFO: renamed from: getModality-impl, reason: not valid java name */
    public static final Modality m6258getModalityimpl(long j) {
        return ProtoEnumFlags.INSTANCE.modality(IrFlags.MODALITY.get((int) j));
    }

    /* JADX INFO: renamed from: getVisibility-impl, reason: not valid java name */
    public static final DescriptorVisibility m6259getVisibilityimpl(long j) {
        return ProtoEnumFlagsUtilsKt.descriptorVisibility(ProtoEnumFlags.INSTANCE, IrFlags.VISIBILITY.get((int) j));
    }

    /* JADX INFO: renamed from: isOperator-impl, reason: not valid java name */
    public static final boolean m6266isOperatorimpl(long j) {
        Boolean bool = IrFlags.IS_OPERATOR.get((int) j);
        Intrinsics.checkNotNullExpressionValue(bool, "get(...)");
        return bool.booleanValue();
    }

    /* JADX INFO: renamed from: isInfix-impl, reason: not valid java name */
    public static final boolean m6264isInfiximpl(long j) {
        Boolean bool = IrFlags.IS_INFIX.get((int) j);
        Intrinsics.checkNotNullExpressionValue(bool, "get(...)");
        return bool.booleanValue();
    }

    /* JADX INFO: renamed from: isInline-impl, reason: not valid java name */
    public static final boolean m6265isInlineimpl(long j) {
        Boolean bool = IrFlags.IS_INLINE.get((int) j);
        Intrinsics.checkNotNullExpressionValue(bool, "get(...)");
        return bool.booleanValue();
    }

    /* JADX INFO: renamed from: isTailrec-impl, reason: not valid java name */
    public static final boolean m6269isTailrecimpl(long j) {
        Boolean bool = IrFlags.IS_TAILREC.get((int) j);
        Intrinsics.checkNotNullExpressionValue(bool, "get(...)");
        return bool.booleanValue();
    }

    /* JADX INFO: renamed from: isExternal-impl, reason: not valid java name */
    public static final boolean m6262isExternalimpl(long j) {
        Boolean bool = IrFlags.IS_EXTERNAL_FUNCTION.get((int) j);
        Intrinsics.checkNotNullExpressionValue(bool, "get(...)");
        return bool.booleanValue();
    }

    /* JADX INFO: renamed from: isSuspend-impl, reason: not valid java name */
    public static final boolean m6268isSuspendimpl(long j) {
        Boolean bool = IrFlags.IS_SUSPEND.get((int) j);
        Intrinsics.checkNotNullExpressionValue(bool, "get(...)");
        return bool.booleanValue();
    }

    /* JADX INFO: renamed from: isExpect-impl, reason: not valid java name */
    public static final boolean m6261isExpectimpl(long j) {
        Boolean bool = IrFlags.IS_EXPECT_FUNCTION.get((int) j);
        Intrinsics.checkNotNullExpressionValue(bool, "get(...)");
        return bool.booleanValue();
    }

    /* JADX INFO: renamed from: isFakeOverride-impl, reason: not valid java name */
    public static final boolean m6263isFakeOverrideimpl(long j) {
        return m6270kindimpl(j) == CallableMemberDescriptor.Kind.FAKE_OVERRIDE;
    }

    /* JADX INFO: renamed from: isPrimary-impl, reason: not valid java name */
    public static final boolean m6267isPrimaryimpl(long j) {
        Boolean bool = IrFlags.IS_PRIMARY.get((int) j);
        Intrinsics.checkNotNullExpressionValue(bool, "get(...)");
        return bool.booleanValue();
    }

    /* JADX INFO: renamed from: kind-impl, reason: not valid java name */
    private static final CallableMemberDescriptor.Kind m6270kindimpl(long j) {
        return ProtoEnumFlagsUtilsKt.memberKind(ProtoEnumFlags.INSTANCE, IrFlags.MEMBER_KIND.get((int) j));
    }

    /* JADX INFO: compiled from: BinaryFlags.kt */
    @Metadata(d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\t\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u001b\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006ø\u0001\u0000ø\u0001\u0001¢\u0006\u0004\b\u0007\u0010\bJ\u000e\u0010\t\u001a\u00020\u00062\u0006\u0010\n\u001a\u00020\u000bJ\u000e\u0010\t\u001a\u00020\u00062\u0006\u0010\f\u001a\u00020\r\u0082\u0002\u000b\n\u0002\b!\n\u0005\b¡\u001e0\u0001¨\u0006\u000e"}, d2 = {"Lorg/jetbrains/kotlin/backend/common/serialization/encodings/FunctionFlags$Companion;", "", "()V", "decode", "Lorg/jetbrains/kotlin/backend/common/serialization/encodings/FunctionFlags;", ServerAuthenticationConstants.SIGNIN_GOOGLE_CODE_KEY, "", "decode-UIJRpeM", "(J)J", "encode", JvmAbi.ERASED_INLINE_CONSTRUCTOR_NAME, "Lorg/jetbrains/kotlin/ir/declarations/IrConstructor;", "function", "Lorg/jetbrains/kotlin/ir/declarations/IrSimpleFunction;", "ir.serialization.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final long encode(IrSimpleFunction function) {
            Intrinsics.checkNotNullParameter(function, "function");
            return IrFlags.getFunctionFlags(!function.getAnnotations().isEmpty(), ProtoEnumFlagsUtilsKt.descriptorVisibility(ProtoEnumFlags.INSTANCE, function.getVisibility().normalize()), ProtoEnumFlags.INSTANCE.modality(function.getModality()), function.getIsFakeOverride() ? ProtoBuf.MemberKind.FAKE_OVERRIDE : ProtoBuf.MemberKind.DECLARATION, function.getIsOperator(), function.getIsInfix(), function.getIsInline(), function.getIsTailrec(), function.getIsExternal(), function.getIsSuspend(), function.getIsExpect(), true);
        }

        public final long encode(IrConstructor constructor) {
            Intrinsics.checkNotNullParameter(constructor, "constructor");
            return IrFlags.getConstructorFlags(!constructor.getAnnotations().isEmpty(), ProtoEnumFlagsUtilsKt.descriptorVisibility(ProtoEnumFlags.INSTANCE, constructor.getVisibility().normalize()), constructor.getIsInline(), constructor.getIsExternal(), constructor.getIsExpect(), constructor.getIsPrimary());
        }

        /* JADX INFO: renamed from: decode-UIJRpeM, reason: not valid java name */
        public final long m6273decodeUIJRpeM(long code) {
            return FunctionFlags.m6255constructorimpl(code);
        }
    }
}
