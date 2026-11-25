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
import org.jetbrains.kotlin.ir.declarations.IrProperty;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.metadata.ProtoBuf;
import org.jetbrains.kotlin.serialization.deserialization.ProtoEnumFlags;
import org.jetbrains.kotlin.serialization.deserialization.ProtoEnumFlagsUtilsKt;

/* JADX INFO: compiled from: BinaryFlags.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000B\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\t\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0002\b\u000f\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0004\b\u0087@\u0018\u0000 02\u00020\u0001:\u00010B\u000f\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0004\b\u0004\u0010\u0005J\u001a\u0010 \u001a\u00020\t2\b\u0010!\u001a\u0004\u0018\u00010\u0001HÖ\u0003¢\u0006\u0004\b\"\u0010#J\u0010\u0010$\u001a\u00020%HÖ\u0001¢\u0006\u0004\b&\u0010'J\u000f\u0010(\u001a\u00020)H\u0002¢\u0006\u0004\b*\u0010+J\u0010\u0010,\u001a\u00020-HÖ\u0001¢\u0006\u0004\b.\u0010/R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007R\u0011\u0010\b\u001a\u00020\t8F¢\u0006\u0006\u001a\u0004\b\n\u0010\u000bR\u0011\u0010\f\u001a\u00020\t8F¢\u0006\u0006\u001a\u0004\b\r\u0010\u000bR\u0011\u0010\u000e\u001a\u00020\t8F¢\u0006\u0006\u001a\u0004\b\u000f\u0010\u000bR\u0011\u0010\u0010\u001a\u00020\t8F¢\u0006\u0006\u001a\u0004\b\u0011\u0010\u000bR\u0011\u0010\u0012\u001a\u00020\t8F¢\u0006\u0006\u001a\u0004\b\u0013\u0010\u000bR\u0011\u0010\u0014\u001a\u00020\t8F¢\u0006\u0006\u001a\u0004\b\u0015\u0010\u000bR\u0011\u0010\u0016\u001a\u00020\t8F¢\u0006\u0006\u001a\u0004\b\u0017\u0010\u000bR\u0011\u0010\u0018\u001a\u00020\u00198F¢\u0006\u0006\u001a\u0004\b\u001a\u0010\u001bR\u0011\u0010\u001c\u001a\u00020\u001d8F¢\u0006\u0006\u001a\u0004\b\u001e\u0010\u001f\u0088\u0001\u0002¨\u00061"}, d2 = {"Lorg/jetbrains/kotlin/backend/common/serialization/encodings/PropertyFlags;", "", OverlayablePolicy.NAME_flags, "", "constructor-impl", "(J)J", "getFlags", "()J", "isConst", "", "isConst-impl", "(J)Z", "isDelegated", "isDelegated-impl", "isExpect", "isExpect-impl", "isExternal", "isExternal-impl", "isFakeOverride", "isFakeOverride-impl", "isLateinit", "isLateinit-impl", "isVar", "isVar-impl", "modality", "Lorg/jetbrains/kotlin/descriptors/Modality;", "getModality-impl", "(J)Lorg/jetbrains/kotlin/descriptors/Modality;", "visibility", "Lorg/jetbrains/kotlin/descriptors/DescriptorVisibility;", "getVisibility-impl", "(J)Lorg/jetbrains/kotlin/descriptors/DescriptorVisibility;", Namer.EQUALS_METHOD_NAME, "other", "equals-impl", "(JLjava/lang/Object;)Z", "hashCode", "", "hashCode-impl", "(J)I", Namer.METADATA_CLASS_KIND, "Lorg/jetbrains/kotlin/descriptors/CallableMemberDescriptor$Kind;", "kind-impl", "(J)Lorg/jetbrains/kotlin/descriptors/CallableMemberDescriptor$Kind;", "toString", "", "toString-impl", "(J)Ljava/lang/String;", "Companion", "ir.serialization.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
@JvmInline
public final class PropertyFlags {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private final long flags;

    /* JADX INFO: renamed from: box-impl, reason: not valid java name */
    public static final /* synthetic */ PropertyFlags m6285boximpl(long j) {
        return new PropertyFlags(j);
    }

    /* JADX INFO: renamed from: constructor-impl, reason: not valid java name */
    public static long m6286constructorimpl(long j) {
        return j;
    }

    /* JADX INFO: renamed from: equals-impl, reason: not valid java name */
    public static boolean m6287equalsimpl(long j, Object obj) {
        return (obj instanceof PropertyFlags) && j == ((PropertyFlags) obj).m6301unboximpl();
    }

    /* JADX INFO: renamed from: equals-impl0, reason: not valid java name */
    public static final boolean m6288equalsimpl0(long j, long j2) {
        return j == j2;
    }

    /* JADX INFO: renamed from: hashCode-impl, reason: not valid java name */
    public static int m6291hashCodeimpl(long j) {
        return Long.hashCode(j);
    }

    /* JADX INFO: renamed from: toString-impl, reason: not valid java name */
    public static String m6300toStringimpl(long j) {
        return "PropertyFlags(flags=" + j + ')';
    }

    public boolean equals(Object obj) {
        return m6287equalsimpl(this.flags, obj);
    }

    public int hashCode() {
        return m6291hashCodeimpl(this.flags);
    }

    public String toString() {
        return m6300toStringimpl(this.flags);
    }

    /* JADX INFO: renamed from: unbox-impl, reason: not valid java name */
    public final /* synthetic */ long m6301unboximpl() {
        return this.flags;
    }

    private /* synthetic */ PropertyFlags(long j) {
        this.flags = j;
    }

    public final long getFlags() {
        return this.flags;
    }

    /* JADX INFO: renamed from: getModality-impl, reason: not valid java name */
    public static final Modality m6289getModalityimpl(long j) {
        return ProtoEnumFlags.INSTANCE.modality(IrFlags.MODALITY.get((int) j));
    }

    /* JADX INFO: renamed from: getVisibility-impl, reason: not valid java name */
    public static final DescriptorVisibility m6290getVisibilityimpl(long j) {
        return ProtoEnumFlagsUtilsKt.descriptorVisibility(ProtoEnumFlags.INSTANCE, IrFlags.VISIBILITY.get((int) j));
    }

    /* JADX INFO: renamed from: isVar-impl, reason: not valid java name */
    public static final boolean m6298isVarimpl(long j) {
        Boolean bool = IrFlags.IS_VAR.get((int) j);
        Intrinsics.checkNotNullExpressionValue(bool, "get(...)");
        return bool.booleanValue();
    }

    /* JADX INFO: renamed from: isConst-impl, reason: not valid java name */
    public static final boolean m6292isConstimpl(long j) {
        Boolean bool = IrFlags.IS_CONST.get((int) j);
        Intrinsics.checkNotNullExpressionValue(bool, "get(...)");
        return bool.booleanValue();
    }

    /* JADX INFO: renamed from: isLateinit-impl, reason: not valid java name */
    public static final boolean m6297isLateinitimpl(long j) {
        Boolean bool = IrFlags.IS_LATEINIT.get((int) j);
        Intrinsics.checkNotNullExpressionValue(bool, "get(...)");
        return bool.booleanValue();
    }

    /* JADX INFO: renamed from: isExternal-impl, reason: not valid java name */
    public static final boolean m6295isExternalimpl(long j) {
        Boolean bool = IrFlags.IS_EXTERNAL_PROPERTY.get((int) j);
        Intrinsics.checkNotNullExpressionValue(bool, "get(...)");
        return bool.booleanValue();
    }

    /* JADX INFO: renamed from: isDelegated-impl, reason: not valid java name */
    public static final boolean m6293isDelegatedimpl(long j) {
        Boolean bool = IrFlags.IS_DELEGATED.get((int) j);
        Intrinsics.checkNotNullExpressionValue(bool, "get(...)");
        return bool.booleanValue();
    }

    /* JADX INFO: renamed from: isExpect-impl, reason: not valid java name */
    public static final boolean m6294isExpectimpl(long j) {
        Boolean bool = IrFlags.IS_EXPECT_PROPERTY.get((int) j);
        Intrinsics.checkNotNullExpressionValue(bool, "get(...)");
        return bool.booleanValue();
    }

    /* JADX INFO: renamed from: isFakeOverride-impl, reason: not valid java name */
    public static final boolean m6296isFakeOverrideimpl(long j) {
        return m6299kindimpl(j) == CallableMemberDescriptor.Kind.FAKE_OVERRIDE;
    }

    /* JADX INFO: renamed from: kind-impl, reason: not valid java name */
    private static final CallableMemberDescriptor.Kind m6299kindimpl(long j) {
        return ProtoEnumFlagsUtilsKt.memberKind(ProtoEnumFlags.INSTANCE, IrFlags.MEMBER_KIND.get((int) j));
    }

    /* JADX INFO: compiled from: BinaryFlags.kt */
    @Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\t\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u001b\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006ø\u0001\u0000ø\u0001\u0001¢\u0006\u0004\b\u0007\u0010\bJ\u000e\u0010\t\u001a\u00020\u00062\u0006\u0010\n\u001a\u00020\u000b\u0082\u0002\u000b\n\u0002\b!\n\u0005\b¡\u001e0\u0001¨\u0006\f"}, d2 = {"Lorg/jetbrains/kotlin/backend/common/serialization/encodings/PropertyFlags$Companion;", "", "()V", "decode", "Lorg/jetbrains/kotlin/backend/common/serialization/encodings/PropertyFlags;", ServerAuthenticationConstants.SIGNIN_GOOGLE_CODE_KEY, "", "decode-85tB66k", "(J)J", "encode", "property", "Lorg/jetbrains/kotlin/ir/declarations/IrProperty;", "ir.serialization.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final long encode(IrProperty property) {
            Intrinsics.checkNotNullParameter(property, "property");
            return IrFlags.getPropertyFlags(!property.getAnnotations().isEmpty(), ProtoEnumFlagsUtilsKt.descriptorVisibility(ProtoEnumFlags.INSTANCE, property.getVisibility().normalize()), ProtoEnumFlags.INSTANCE.modality(property.getModality()), property.getIsFakeOverride() ? ProtoBuf.MemberKind.FAKE_OVERRIDE : ProtoBuf.MemberKind.DECLARATION, property.getIsVar(), property.getGetter() != null, property.getSetter() != null, false, property.getIsConst(), property.getIsLateinit(), property.getIsExternal(), property.getIsDelegated(), property.getIsExpect());
        }

        /* JADX INFO: renamed from: decode-85tB66k, reason: not valid java name */
        public final long m6302decode85tB66k(long code) {
            return PropertyFlags.m6286constructorimpl(code);
        }
    }
}
