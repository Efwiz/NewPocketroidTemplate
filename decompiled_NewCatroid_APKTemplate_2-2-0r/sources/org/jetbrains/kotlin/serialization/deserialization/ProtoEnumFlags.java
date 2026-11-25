package org.jetbrains.kotlin.serialization.deserialization;

import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.descriptors.ClassKind;
import org.jetbrains.kotlin.descriptors.Modality;
import org.jetbrains.kotlin.descriptors.Visibilities;
import org.jetbrains.kotlin.descriptors.Visibility;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.metadata.ProtoBuf;
import org.jetbrains.kotlin.types.Variance;

/* JADX INFO: compiled from: ProtoEnumFlags.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000B\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0016\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bJ\u0010\u0010\u0003\u001a\u00020\u00062\b\u0010\u0005\u001a\u0004\u0018\u00010\u0004J\u000e\u0010\t\u001a\u00020\n2\u0006\u0010\t\u001a\u00020\u000bJ\u0010\u0010\t\u001a\u00020\u000b2\b\u0010\t\u001a\u0004\u0018\u00010\nJ\u000e\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000fJ\u000e\u0010\f\u001a\u00020\r2\u0006\u0010\f\u001a\u00020\u0010J\u000e\u0010\f\u001a\u00020\u00102\u0006\u0010\f\u001a\u00020\rJ\u000e\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0011\u001a\u00020\u0013J\u0010\u0010\u0011\u001a\u00020\u00132\b\u0010\u0011\u001a\u0004\u0018\u00010\u0012¨\u0006\u0014"}, d2 = {"Lorg/jetbrains/kotlin/serialization/deserialization/ProtoEnumFlags;", "", "()V", "classKind", "Lorg/jetbrains/kotlin/metadata/ProtoBuf$Class$Kind;", Namer.METADATA_CLASS_KIND, "Lorg/jetbrains/kotlin/descriptors/ClassKind;", "isCompanionObject", "", "modality", "Lorg/jetbrains/kotlin/metadata/ProtoBuf$Modality;", "Lorg/jetbrains/kotlin/descriptors/Modality;", "variance", "Lorg/jetbrains/kotlin/types/Variance;", "projection", "Lorg/jetbrains/kotlin/metadata/ProtoBuf$Type$Argument$Projection;", "Lorg/jetbrains/kotlin/metadata/ProtoBuf$TypeParameter$Variance;", "visibility", "Lorg/jetbrains/kotlin/metadata/ProtoBuf$Visibility;", "Lorg/jetbrains/kotlin/descriptors/Visibility;", "deserialization.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ProtoEnumFlags {
    public static final ProtoEnumFlags INSTANCE = new ProtoEnumFlags();

    /* JADX INFO: compiled from: ProtoEnumFlags.kt */
    @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
    public /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;
        public static final /* synthetic */ int[] $EnumSwitchMapping$1;
        public static final /* synthetic */ int[] $EnumSwitchMapping$2;
        public static final /* synthetic */ int[] $EnumSwitchMapping$3;
        public static final /* synthetic */ int[] $EnumSwitchMapping$4;
        public static final /* synthetic */ int[] $EnumSwitchMapping$5;
        public static final /* synthetic */ int[] $EnumSwitchMapping$6;
        public static final /* synthetic */ int[] $EnumSwitchMapping$7;

        static {
            int[] iArr = new int[ProtoBuf.Modality.values().length];
            try {
                iArr[ProtoBuf.Modality.FINAL.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[ProtoBuf.Modality.OPEN.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                iArr[ProtoBuf.Modality.ABSTRACT.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                iArr[ProtoBuf.Modality.SEALED.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            $EnumSwitchMapping$0 = iArr;
            int[] iArr2 = new int[Modality.values().length];
            try {
                iArr2[Modality.FINAL.ordinal()] = 1;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                iArr2[Modality.OPEN.ordinal()] = 2;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                iArr2[Modality.ABSTRACT.ordinal()] = 3;
            } catch (NoSuchFieldError unused7) {
            }
            try {
                iArr2[Modality.SEALED.ordinal()] = 4;
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
            int[] iArr4 = new int[ProtoBuf.Class.Kind.values().length];
            try {
                iArr4[ProtoBuf.Class.Kind.CLASS.ordinal()] = 1;
            } catch (NoSuchFieldError unused15) {
            }
            try {
                iArr4[ProtoBuf.Class.Kind.INTERFACE.ordinal()] = 2;
            } catch (NoSuchFieldError unused16) {
            }
            try {
                iArr4[ProtoBuf.Class.Kind.ENUM_CLASS.ordinal()] = 3;
            } catch (NoSuchFieldError unused17) {
            }
            try {
                iArr4[ProtoBuf.Class.Kind.ENUM_ENTRY.ordinal()] = 4;
            } catch (NoSuchFieldError unused18) {
            }
            try {
                iArr4[ProtoBuf.Class.Kind.ANNOTATION_CLASS.ordinal()] = 5;
            } catch (NoSuchFieldError unused19) {
            }
            try {
                iArr4[ProtoBuf.Class.Kind.OBJECT.ordinal()] = 6;
            } catch (NoSuchFieldError unused20) {
            }
            try {
                iArr4[ProtoBuf.Class.Kind.COMPANION_OBJECT.ordinal()] = 7;
            } catch (NoSuchFieldError unused21) {
            }
            $EnumSwitchMapping$3 = iArr4;
            int[] iArr5 = new int[ClassKind.values().length];
            try {
                iArr5[ClassKind.CLASS.ordinal()] = 1;
            } catch (NoSuchFieldError unused22) {
            }
            try {
                iArr5[ClassKind.INTERFACE.ordinal()] = 2;
            } catch (NoSuchFieldError unused23) {
            }
            try {
                iArr5[ClassKind.ENUM_CLASS.ordinal()] = 3;
            } catch (NoSuchFieldError unused24) {
            }
            try {
                iArr5[ClassKind.ENUM_ENTRY.ordinal()] = 4;
            } catch (NoSuchFieldError unused25) {
            }
            try {
                iArr5[ClassKind.ANNOTATION_CLASS.ordinal()] = 5;
            } catch (NoSuchFieldError unused26) {
            }
            try {
                iArr5[ClassKind.OBJECT.ordinal()] = 6;
            } catch (NoSuchFieldError unused27) {
            }
            $EnumSwitchMapping$4 = iArr5;
            int[] iArr6 = new int[ProtoBuf.TypeParameter.Variance.values().length];
            try {
                iArr6[ProtoBuf.TypeParameter.Variance.IN.ordinal()] = 1;
            } catch (NoSuchFieldError unused28) {
            }
            try {
                iArr6[ProtoBuf.TypeParameter.Variance.OUT.ordinal()] = 2;
            } catch (NoSuchFieldError unused29) {
            }
            try {
                iArr6[ProtoBuf.TypeParameter.Variance.INV.ordinal()] = 3;
            } catch (NoSuchFieldError unused30) {
            }
            $EnumSwitchMapping$5 = iArr6;
            int[] iArr7 = new int[ProtoBuf.Type.Argument.Projection.values().length];
            try {
                iArr7[ProtoBuf.Type.Argument.Projection.IN.ordinal()] = 1;
            } catch (NoSuchFieldError unused31) {
            }
            try {
                iArr7[ProtoBuf.Type.Argument.Projection.OUT.ordinal()] = 2;
            } catch (NoSuchFieldError unused32) {
            }
            try {
                iArr7[ProtoBuf.Type.Argument.Projection.INV.ordinal()] = 3;
            } catch (NoSuchFieldError unused33) {
            }
            try {
                iArr7[ProtoBuf.Type.Argument.Projection.STAR.ordinal()] = 4;
            } catch (NoSuchFieldError unused34) {
            }
            $EnumSwitchMapping$6 = iArr7;
            int[] iArr8 = new int[Variance.values().length];
            try {
                iArr8[Variance.IN_VARIANCE.ordinal()] = 1;
            } catch (NoSuchFieldError unused35) {
            }
            try {
                iArr8[Variance.OUT_VARIANCE.ordinal()] = 2;
            } catch (NoSuchFieldError unused36) {
            }
            try {
                iArr8[Variance.INVARIANT.ordinal()] = 3;
            } catch (NoSuchFieldError unused37) {
            }
            $EnumSwitchMapping$7 = iArr8;
        }
    }

    private ProtoEnumFlags() {
    }

    public final Modality modality(ProtoBuf.Modality modality) {
        int i = modality == null ? -1 : WhenMappings.$EnumSwitchMapping$0[modality.ordinal()];
        if (i == 1) {
            return Modality.FINAL;
        }
        if (i == 2) {
            return Modality.OPEN;
        }
        if (i == 3) {
            return Modality.ABSTRACT;
        }
        if (i == 4) {
            return Modality.SEALED;
        }
        return Modality.FINAL;
    }

    public final ProtoBuf.Modality modality(Modality modality) {
        Intrinsics.checkNotNullParameter(modality, "modality");
        int i = WhenMappings.$EnumSwitchMapping$1[modality.ordinal()];
        if (i == 1) {
            return ProtoBuf.Modality.FINAL;
        }
        if (i == 2) {
            return ProtoBuf.Modality.OPEN;
        }
        if (i == 3) {
            return ProtoBuf.Modality.ABSTRACT;
        }
        if (i == 4) {
            return ProtoBuf.Modality.SEALED;
        }
        throw new NoWhenBranchMatchedException();
    }

    public final Visibility visibility(ProtoBuf.Visibility visibility) {
        switch (visibility == null ? -1 : WhenMappings.$EnumSwitchMapping$2[visibility.ordinal()]) {
            case 1:
                return Visibilities.Internal.INSTANCE;
            case 2:
                return Visibilities.Private.INSTANCE;
            case 3:
                return Visibilities.PrivateToThis.INSTANCE;
            case 4:
                return Visibilities.Protected.INSTANCE;
            case 5:
                return Visibilities.Public.INSTANCE;
            case 6:
                return Visibilities.Local.INSTANCE;
            default:
                return Visibilities.Private.INSTANCE;
        }
    }

    public final ProtoBuf.Visibility visibility(Visibility visibility) {
        Intrinsics.checkNotNullParameter(visibility, "visibility");
        if (Intrinsics.areEqual(visibility, Visibilities.Internal.INSTANCE)) {
            return ProtoBuf.Visibility.INTERNAL;
        }
        if (Intrinsics.areEqual(visibility, Visibilities.Public.INSTANCE)) {
            return ProtoBuf.Visibility.PUBLIC;
        }
        if (Intrinsics.areEqual(visibility, Visibilities.Private.INSTANCE)) {
            return ProtoBuf.Visibility.PRIVATE;
        }
        if (Intrinsics.areEqual(visibility, Visibilities.PrivateToThis.INSTANCE)) {
            return ProtoBuf.Visibility.PRIVATE_TO_THIS;
        }
        if (Intrinsics.areEqual(visibility, Visibilities.Protected.INSTANCE)) {
            return ProtoBuf.Visibility.PROTECTED;
        }
        if (Intrinsics.areEqual(visibility, Visibilities.Local.INSTANCE)) {
            return ProtoBuf.Visibility.LOCAL;
        }
        throw new IllegalArgumentException("Unknown visibility: " + visibility);
    }

    public final ClassKind classKind(ProtoBuf.Class.Kind kind) {
        switch (kind == null ? -1 : WhenMappings.$EnumSwitchMapping$3[kind.ordinal()]) {
            case 1:
                return ClassKind.CLASS;
            case 2:
                return ClassKind.INTERFACE;
            case 3:
                return ClassKind.ENUM_CLASS;
            case 4:
                return ClassKind.ENUM_ENTRY;
            case 5:
                return ClassKind.ANNOTATION_CLASS;
            case 6:
            case 7:
                return ClassKind.OBJECT;
            default:
                return ClassKind.CLASS;
        }
    }

    public final ProtoBuf.Class.Kind classKind(ClassKind kind, boolean isCompanionObject) {
        Intrinsics.checkNotNullParameter(kind, "kind");
        if (isCompanionObject) {
            return ProtoBuf.Class.Kind.COMPANION_OBJECT;
        }
        switch (WhenMappings.$EnumSwitchMapping$4[kind.ordinal()]) {
            case 1:
                return ProtoBuf.Class.Kind.CLASS;
            case 2:
                return ProtoBuf.Class.Kind.INTERFACE;
            case 3:
                return ProtoBuf.Class.Kind.ENUM_CLASS;
            case 4:
                return ProtoBuf.Class.Kind.ENUM_ENTRY;
            case 5:
                return ProtoBuf.Class.Kind.ANNOTATION_CLASS;
            case 6:
                return ProtoBuf.Class.Kind.OBJECT;
            default:
                throw new NoWhenBranchMatchedException();
        }
    }

    public final Variance variance(ProtoBuf.TypeParameter.Variance variance) {
        Intrinsics.checkNotNullParameter(variance, "variance");
        int i = WhenMappings.$EnumSwitchMapping$5[variance.ordinal()];
        if (i == 1) {
            return Variance.IN_VARIANCE;
        }
        if (i == 2) {
            return Variance.OUT_VARIANCE;
        }
        if (i == 3) {
            return Variance.INVARIANT;
        }
        throw new NoWhenBranchMatchedException();
    }

    public final Variance variance(ProtoBuf.Type.Argument.Projection projection) {
        Intrinsics.checkNotNullParameter(projection, "projection");
        int i = WhenMappings.$EnumSwitchMapping$6[projection.ordinal()];
        if (i == 1) {
            return Variance.IN_VARIANCE;
        }
        if (i == 2) {
            return Variance.OUT_VARIANCE;
        }
        if (i == 3) {
            return Variance.INVARIANT;
        }
        if (i != 4) {
            throw new NoWhenBranchMatchedException();
        }
        throw new IllegalArgumentException("Only IN, OUT and INV are supported. Actual argument: " + projection);
    }

    public final ProtoBuf.TypeParameter.Variance variance(Variance variance) {
        Intrinsics.checkNotNullParameter(variance, "variance");
        int i = WhenMappings.$EnumSwitchMapping$7[variance.ordinal()];
        if (i == 1) {
            return ProtoBuf.TypeParameter.Variance.IN;
        }
        if (i == 2) {
            return ProtoBuf.TypeParameter.Variance.OUT;
        }
        if (i == 3) {
            return ProtoBuf.TypeParameter.Variance.INV;
        }
        throw new NoWhenBranchMatchedException();
    }
}
