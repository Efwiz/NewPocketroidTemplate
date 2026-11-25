package org.jetbrains.kotlin.serialization.deserialization;

import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.descriptors.CallableMemberDescriptor;
import org.jetbrains.kotlin.descriptors.DescriptorVisibilities;
import org.jetbrains.kotlin.descriptors.DescriptorVisibility;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.metadata.ProtoBuf;

/* JADX INFO: compiled from: ProtoEnumFlagsUtils.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\u001e\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u001a\u0012\u0010\u0000\u001a\u00020\u0001*\u00020\u00022\u0006\u0010\u0003\u001a\u00020\u0004\u001a\u0014\u0010\u0000\u001a\u00020\u0004*\u00020\u00022\b\u0010\u0003\u001a\u0004\u0018\u00010\u0001\u001a\u0012\u0010\u0005\u001a\u00020\u0006*\u00020\u00022\u0006\u0010\u0007\u001a\u00020\b\u001a\u0014\u0010\u0005\u001a\u00020\b*\u00020\u00022\b\u0010\u0005\u001a\u0004\u0018\u00010\u0006¨\u0006\t"}, d2 = {"descriptorVisibility", "Lorg/jetbrains/kotlin/metadata/ProtoBuf$Visibility;", "Lorg/jetbrains/kotlin/serialization/deserialization/ProtoEnumFlags;", "visibility", "Lorg/jetbrains/kotlin/descriptors/DescriptorVisibility;", "memberKind", "Lorg/jetbrains/kotlin/metadata/ProtoBuf$MemberKind;", Namer.METADATA_CLASS_KIND, "Lorg/jetbrains/kotlin/descriptors/CallableMemberDescriptor$Kind;", "deserialization"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class ProtoEnumFlagsUtilsKt {

    /* JADX INFO: compiled from: ProtoEnumFlagsUtils.kt */
    @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
    public /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;
        public static final /* synthetic */ int[] $EnumSwitchMapping$1;
        public static final /* synthetic */ int[] $EnumSwitchMapping$2;

        static {
            int[] iArr = new int[ProtoBuf.MemberKind.values().length];
            try {
                iArr[ProtoBuf.MemberKind.DECLARATION.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[ProtoBuf.MemberKind.FAKE_OVERRIDE.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                iArr[ProtoBuf.MemberKind.DELEGATION.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                iArr[ProtoBuf.MemberKind.SYNTHESIZED.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            $EnumSwitchMapping$0 = iArr;
            int[] iArr2 = new int[CallableMemberDescriptor.Kind.values().length];
            try {
                iArr2[CallableMemberDescriptor.Kind.DECLARATION.ordinal()] = 1;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                iArr2[CallableMemberDescriptor.Kind.FAKE_OVERRIDE.ordinal()] = 2;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                iArr2[CallableMemberDescriptor.Kind.DELEGATION.ordinal()] = 3;
            } catch (NoSuchFieldError unused7) {
            }
            try {
                iArr2[CallableMemberDescriptor.Kind.SYNTHESIZED.ordinal()] = 4;
            } catch (NoSuchFieldError unused8) {
            }
            $EnumSwitchMapping$1 = iArr2;
            int[] iArr3 = new int[ProtoBuf.Visibility.values().length];
            try {
                iArr3[ProtoBuf.Visibility.INTERNAL.ordinal()] = 1;
            } catch (NoSuchFieldError unused9) {
            }
            try {
                iArr3[ProtoBuf.Visibility.PRIVATE.ordinal()] = 2;
            } catch (NoSuchFieldError unused10) {
            }
            try {
                iArr3[ProtoBuf.Visibility.PRIVATE_TO_THIS.ordinal()] = 3;
            } catch (NoSuchFieldError unused11) {
            }
            try {
                iArr3[ProtoBuf.Visibility.PROTECTED.ordinal()] = 4;
            } catch (NoSuchFieldError unused12) {
            }
            try {
                iArr3[ProtoBuf.Visibility.PUBLIC.ordinal()] = 5;
            } catch (NoSuchFieldError unused13) {
            }
            try {
                iArr3[ProtoBuf.Visibility.LOCAL.ordinal()] = 6;
            } catch (NoSuchFieldError unused14) {
            }
            $EnumSwitchMapping$2 = iArr3;
        }
    }

    public static final CallableMemberDescriptor.Kind memberKind(ProtoEnumFlags protoEnumFlags, ProtoBuf.MemberKind memberKind) {
        Intrinsics.checkNotNullParameter(protoEnumFlags, "<this>");
        int i = memberKind == null ? -1 : WhenMappings.$EnumSwitchMapping$0[memberKind.ordinal()];
        if (i == 1) {
            return CallableMemberDescriptor.Kind.DECLARATION;
        }
        if (i == 2) {
            return CallableMemberDescriptor.Kind.FAKE_OVERRIDE;
        }
        if (i == 3) {
            return CallableMemberDescriptor.Kind.DELEGATION;
        }
        if (i == 4) {
            return CallableMemberDescriptor.Kind.SYNTHESIZED;
        }
        return CallableMemberDescriptor.Kind.DECLARATION;
    }

    public static final ProtoBuf.MemberKind memberKind(ProtoEnumFlags protoEnumFlags, CallableMemberDescriptor.Kind kind) {
        Intrinsics.checkNotNullParameter(protoEnumFlags, "<this>");
        Intrinsics.checkNotNullParameter(kind, "kind");
        int i = WhenMappings.$EnumSwitchMapping$1[kind.ordinal()];
        if (i == 1) {
            return ProtoBuf.MemberKind.DECLARATION;
        }
        if (i == 2) {
            return ProtoBuf.MemberKind.FAKE_OVERRIDE;
        }
        if (i == 3) {
            return ProtoBuf.MemberKind.DELEGATION;
        }
        if (i == 4) {
            return ProtoBuf.MemberKind.SYNTHESIZED;
        }
        throw new NoWhenBranchMatchedException();
    }

    public static final DescriptorVisibility descriptorVisibility(ProtoEnumFlags protoEnumFlags, ProtoBuf.Visibility visibility) {
        Intrinsics.checkNotNullParameter(protoEnumFlags, "<this>");
        switch (visibility == null ? -1 : WhenMappings.$EnumSwitchMapping$2[visibility.ordinal()]) {
            case 1:
                DescriptorVisibility INTERNAL = DescriptorVisibilities.INTERNAL;
                Intrinsics.checkNotNullExpressionValue(INTERNAL, "INTERNAL");
                return INTERNAL;
            case 2:
                DescriptorVisibility PRIVATE = DescriptorVisibilities.PRIVATE;
                Intrinsics.checkNotNullExpressionValue(PRIVATE, "PRIVATE");
                return PRIVATE;
            case 3:
                DescriptorVisibility PRIVATE_TO_THIS = DescriptorVisibilities.PRIVATE_TO_THIS;
                Intrinsics.checkNotNullExpressionValue(PRIVATE_TO_THIS, "PRIVATE_TO_THIS");
                return PRIVATE_TO_THIS;
            case 4:
                DescriptorVisibility PROTECTED = DescriptorVisibilities.PROTECTED;
                Intrinsics.checkNotNullExpressionValue(PROTECTED, "PROTECTED");
                return PROTECTED;
            case 5:
                DescriptorVisibility PUBLIC = DescriptorVisibilities.PUBLIC;
                Intrinsics.checkNotNullExpressionValue(PUBLIC, "PUBLIC");
                return PUBLIC;
            case 6:
                DescriptorVisibility LOCAL = DescriptorVisibilities.LOCAL;
                Intrinsics.checkNotNullExpressionValue(LOCAL, "LOCAL");
                return LOCAL;
            default:
                DescriptorVisibility PRIVATE2 = DescriptorVisibilities.PRIVATE;
                Intrinsics.checkNotNullExpressionValue(PRIVATE2, "PRIVATE");
                return PRIVATE2;
        }
    }

    public static final ProtoBuf.Visibility descriptorVisibility(ProtoEnumFlags protoEnumFlags, DescriptorVisibility visibility) {
        Intrinsics.checkNotNullParameter(protoEnumFlags, "<this>");
        Intrinsics.checkNotNullParameter(visibility, "visibility");
        if (Intrinsics.areEqual(visibility, DescriptorVisibilities.INTERNAL)) {
            return ProtoBuf.Visibility.INTERNAL;
        }
        if (Intrinsics.areEqual(visibility, DescriptorVisibilities.PUBLIC)) {
            return ProtoBuf.Visibility.PUBLIC;
        }
        if (Intrinsics.areEqual(visibility, DescriptorVisibilities.PRIVATE)) {
            return ProtoBuf.Visibility.PRIVATE;
        }
        if (Intrinsics.areEqual(visibility, DescriptorVisibilities.PRIVATE_TO_THIS)) {
            return ProtoBuf.Visibility.PRIVATE_TO_THIS;
        }
        if (Intrinsics.areEqual(visibility, DescriptorVisibilities.PROTECTED)) {
            return ProtoBuf.Visibility.PROTECTED;
        }
        if (Intrinsics.areEqual(visibility, DescriptorVisibilities.LOCAL)) {
            return ProtoBuf.Visibility.LOCAL;
        }
        throw new IllegalArgumentException("Unknown visibility: " + visibility);
    }
}
