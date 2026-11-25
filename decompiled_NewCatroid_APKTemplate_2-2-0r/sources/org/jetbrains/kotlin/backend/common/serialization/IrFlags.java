package org.jetbrains.kotlin.backend.common.serialization;

import org.jetbrains.kotlin.metadata.ProtoBuf;
import org.jetbrains.kotlin.metadata.deserialization.Flags;

/* JADX INFO: loaded from: classes2.dex */
public class IrFlags extends Flags {
    public static final Flags.BooleanFlagField IS_ASSIGNABLE;
    public static final Flags.BooleanFlagField IS_EXTERNAL_FIELD;
    public static final Flags.BooleanFlagField IS_FAKE_OVERRIDE;
    public static final Flags.BooleanFlagField IS_FINAL;
    public static final Flags.BooleanFlagField IS_HIDDEN;
    public static final Flags.BooleanFlagField IS_LOCAL_CONST;
    public static final Flags.BooleanFlagField IS_LOCAL_LATEINIT;
    public static final Flags.BooleanFlagField IS_LOCAL_VAR;
    public static final Flags.BooleanFlagField IS_REIFIED;
    public static final Flags.BooleanFlagField IS_STATIC;
    public static final Flags.FlagField<ProtoBuf.TypeParameter.Variance> VARIANCE;
    public static final Flags.BooleanFlagField IS_PRIMARY = Flags.FlagField.booleanAfter(IS_EXPECT_FUNCTION);
    public static final Flags.BooleanFlagField IS_ACTUAL = Flags.FlagField.booleanAfter(VISIBILITY);

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", "visibility", "org/jetbrains/kotlin/backend/common/serialization/IrFlags", "getConstructorFlags"));
    }

    private IrFlags() {
    }

    static {
        Flags.FlagField<ProtoBuf.TypeParameter.Variance> flagFieldAfter = Flags.FlagField.after(HAS_ANNOTATIONS, ProtoBuf.TypeParameter.Variance.values());
        VARIANCE = flagFieldAfter;
        IS_REIFIED = Flags.FlagField.booleanAfter(flagFieldAfter);
        Flags.BooleanFlagField booleanFlagFieldBooleanAfter = Flags.FlagField.booleanAfter(VISIBILITY);
        IS_FINAL = booleanFlagFieldBooleanAfter;
        Flags.BooleanFlagField booleanFlagFieldBooleanAfter2 = Flags.FlagField.booleanAfter(booleanFlagFieldBooleanAfter);
        IS_EXTERNAL_FIELD = booleanFlagFieldBooleanAfter2;
        Flags.BooleanFlagField booleanFlagFieldBooleanAfter3 = Flags.FlagField.booleanAfter(booleanFlagFieldBooleanAfter2);
        IS_STATIC = booleanFlagFieldBooleanAfter3;
        IS_FAKE_OVERRIDE = Flags.FlagField.booleanAfter(booleanFlagFieldBooleanAfter3);
        Flags.BooleanFlagField booleanFlagFieldBooleanAfter4 = Flags.FlagField.booleanAfter(IS_NOINLINE);
        IS_HIDDEN = booleanFlagFieldBooleanAfter4;
        IS_ASSIGNABLE = Flags.FlagField.booleanAfter(booleanFlagFieldBooleanAfter4);
        Flags.BooleanFlagField booleanFlagFieldBooleanAfter5 = Flags.FlagField.booleanAfter(HAS_ANNOTATIONS);
        IS_LOCAL_VAR = booleanFlagFieldBooleanAfter5;
        Flags.BooleanFlagField booleanFlagFieldBooleanAfter6 = Flags.FlagField.booleanAfter(booleanFlagFieldBooleanAfter5);
        IS_LOCAL_CONST = booleanFlagFieldBooleanAfter6;
        IS_LOCAL_LATEINIT = Flags.FlagField.booleanAfter(booleanFlagFieldBooleanAfter6);
    }

    public static int getConstructorFlags(boolean z, ProtoBuf.Visibility visibility, boolean z2, boolean z3, boolean z4, boolean z5) {
        if (visibility == null) {
            $$$reportNull$$$0(0);
        }
        return HAS_ANNOTATIONS.toFlags(Boolean.valueOf(z)) | VISIBILITY.toFlags(visibility) | IS_INLINE.toFlags(Boolean.valueOf(z2)) | IS_EXTERNAL_FUNCTION.toFlags(Boolean.valueOf(z3)) | IS_EXPECT_FUNCTION.toFlags(Boolean.valueOf(z4)) | IS_PRIMARY.toFlags(Boolean.valueOf(z5));
    }

    public static int getTypeAliasFlags(boolean z, ProtoBuf.Visibility visibility, boolean z2) {
        return HAS_ANNOTATIONS.toFlags(Boolean.valueOf(z)) | VISIBILITY.toFlags(visibility) | IS_ACTUAL.toFlags(Boolean.valueOf(z2));
    }

    public static int getTypeParameterFlags(boolean z, ProtoBuf.TypeParameter.Variance variance, boolean z2) {
        return HAS_ANNOTATIONS.toFlags(Boolean.valueOf(z)) | VARIANCE.toFlags(variance) | IS_REIFIED.toFlags(Boolean.valueOf(z2));
    }

    public static int getFieldFlags(boolean z, ProtoBuf.Visibility visibility, boolean z2, boolean z3, boolean z4) {
        return HAS_ANNOTATIONS.toFlags(Boolean.valueOf(z)) | VISIBILITY.toFlags(visibility) | IS_FINAL.toFlags(Boolean.valueOf(z2)) | IS_EXTERNAL_FIELD.toFlags(Boolean.valueOf(z3)) | IS_STATIC.toFlags(Boolean.valueOf(z4));
    }

    public static int getValueParameterFlags(boolean z, boolean z2, boolean z3, boolean z4, boolean z5, boolean z6) {
        return HAS_ANNOTATIONS.toFlags(Boolean.valueOf(z)) | DECLARES_DEFAULT_VALUE.toFlags(Boolean.valueOf(z2)) | IS_CROSSINLINE.toFlags(Boolean.valueOf(z3)) | IS_NOINLINE.toFlags(Boolean.valueOf(z4)) | IS_HIDDEN.toFlags(Boolean.valueOf(z5)) | IS_ASSIGNABLE.toFlags(Boolean.valueOf(z6));
    }

    public static int getLocalFlags(boolean z, boolean z2, boolean z3, boolean z4) {
        return HAS_ANNOTATIONS.toFlags(Boolean.valueOf(z)) | IS_LOCAL_VAR.toFlags(Boolean.valueOf(z2)) | IS_LOCAL_CONST.toFlags(Boolean.valueOf(z3)) | IS_LOCAL_LATEINIT.toFlags(Boolean.valueOf(z4));
    }
}
