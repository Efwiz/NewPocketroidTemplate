package org.jetbrains.kotlin.analysis.decompiler.stub.flags;

import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.lexer.KtModifierKeywordToken;
import org.jetbrains.kotlin.lexer.KtTokens;
import org.jetbrains.kotlin.metadata.ProtoBuf;
import org.jetbrains.kotlin.metadata.deserialization.Flags;

/* JADX INFO: compiled from: flags.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000\u0016\n\u0000\n\u0002\u0018\u0002\n\u0002\b,\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u001a\u0018\u0010,\u001a\u00020\u00012\u0006\u0010-\u001a\u00020.2\u0006\u0010/\u001a\u000200H\u0002\"\u0011\u0010\u0000\u001a\u00020\u0001¢\u0006\b\n\u0000\u001a\u0004\b\u0002\u0010\u0003\"\u0011\u0010\u0004\u001a\u00020\u0001¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0003\"\u0011\u0010\u0006\u001a\u00020\u0001¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\u0003\"\u0011\u0010\b\u001a\u00020\u0001¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\u0003\"\u0011\u0010\n\u001a\u00020\u0001¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\u0003\"\u0011\u0010\f\u001a\u00020\u0001¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u0003\"\u0011\u0010\u000e\u001a\u00020\u0001¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0003\"\u0011\u0010\u0010\u001a\u00020\u0001¢\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0003\"\u0011\u0010\u0012\u001a\u00020\u0001¢\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u0003\"\u0011\u0010\u0014\u001a\u00020\u0001¢\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\u0003\"\u0011\u0010\u0016\u001a\u00020\u0001¢\u0006\b\n\u0000\u001a\u0004\b\u0017\u0010\u0003\"\u0011\u0010\u0018\u001a\u00020\u0001¢\u0006\b\n\u0000\u001a\u0004\b\u0019\u0010\u0003\"\u0011\u0010\u001a\u001a\u00020\u0001¢\u0006\b\n\u0000\u001a\u0004\b\u001b\u0010\u0003\"\u0011\u0010\u001c\u001a\u00020\u0001¢\u0006\b\n\u0000\u001a\u0004\b\u001d\u0010\u0003\"\u0011\u0010\u001e\u001a\u00020\u0001¢\u0006\b\n\u0000\u001a\u0004\b\u001f\u0010\u0003\"\u0011\u0010 \u001a\u00020\u0001¢\u0006\b\n\u0000\u001a\u0004\b!\u0010\u0003\"\u0011\u0010\"\u001a\u00020\u0001¢\u0006\b\n\u0000\u001a\u0004\b#\u0010\u0003\"\u0011\u0010$\u001a\u00020\u0001¢\u0006\b\n\u0000\u001a\u0004\b%\u0010\u0003\"\u0011\u0010&\u001a\u00020\u0001¢\u0006\b\n\u0000\u001a\u0004\b'\u0010\u0003\"\u0011\u0010(\u001a\u00020\u0001¢\u0006\b\n\u0000\u001a\u0004\b)\u0010\u0003\"\u0011\u0010*\u001a\u00020\u0001¢\u0006\b\n\u0000\u001a\u0004\b+\u0010\u0003¨\u00061"}, d2 = {"CONST", "Lorg/jetbrains/kotlin/analysis/decompiler/stub/flags/FlagsToModifiers;", "getCONST", "()Lorg/jetbrains/kotlin/analysis/decompiler/stub/flags/FlagsToModifiers;", "DATA", "getDATA", "EXPECT_CLASS", "getEXPECT_CLASS", "EXPECT_FUNCTION", "getEXPECT_FUNCTION", "EXPECT_PROPERTY", "getEXPECT_PROPERTY", "EXTERNAL_ACCESSOR", "getEXTERNAL_ACCESSOR", "EXTERNAL_CLASS", "getEXTERNAL_CLASS", "EXTERNAL_FUN", "getEXTERNAL_FUN", "EXTERNAL_PROPERTY", "getEXTERNAL_PROPERTY", "FUN_INTERFACE", "getFUN_INTERFACE", "INFIX", "getINFIX", "INLINE", "getINLINE", "INLINE_ACCESSOR", "getINLINE_ACCESSOR", "INNER", "getINNER", "LATEINIT", "getLATEINIT", "MODALITY", "getMODALITY", "OPERATOR", "getOPERATOR", "SUSPEND", "getSUSPEND", "TAILREC", "getTAILREC", "VALUE_CLASS", "getVALUE_CLASS", "VISIBILITY", "getVISIBILITY", "createBooleanFlagToModifier", "flagField", "Lorg/jetbrains/kotlin/metadata/deserialization/Flags$BooleanFlagField;", "ktModifierKeywordToken", "Lorg/jetbrains/kotlin/lexer/KtModifierKeywordToken;", "decompiler-to-stubs"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class FlagsKt {
    private static final FlagsToModifiers CONST;
    private static final FlagsToModifiers DATA;
    private static final FlagsToModifiers EXPECT_CLASS;
    private static final FlagsToModifiers EXPECT_FUNCTION;
    private static final FlagsToModifiers EXPECT_PROPERTY;
    private static final FlagsToModifiers EXTERNAL_ACCESSOR;
    private static final FlagsToModifiers EXTERNAL_CLASS;
    private static final FlagsToModifiers EXTERNAL_FUN;
    private static final FlagsToModifiers EXTERNAL_PROPERTY;
    private static final FlagsToModifiers FUN_INTERFACE;
    private static final FlagsToModifiers INFIX;
    private static final FlagsToModifiers INLINE;
    private static final FlagsToModifiers INLINE_ACCESSOR;
    private static final FlagsToModifiers INNER;
    private static final FlagsToModifiers LATEINIT;
    private static final FlagsToModifiers OPERATOR;
    private static final FlagsToModifiers SUSPEND;
    private static final FlagsToModifiers TAILREC;
    private static final FlagsToModifiers VALUE_CLASS;
    private static final FlagsToModifiers MODALITY = new FlagsToModifiers() { // from class: org.jetbrains.kotlin.analysis.decompiler.stub.flags.FlagsKt$MODALITY$1

        /* JADX INFO: compiled from: flags.kt */
        @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
        public /* synthetic */ class WhenMappings {
            public static final /* synthetic */ int[] $EnumSwitchMapping$0;

            static {
                int[] iArr = new int[ProtoBuf.Modality.values().length];
                try {
                    iArr[ProtoBuf.Modality.ABSTRACT.ordinal()] = 1;
                } catch (NoSuchFieldError unused) {
                }
                try {
                    iArr[ProtoBuf.Modality.FINAL.ordinal()] = 2;
                } catch (NoSuchFieldError unused2) {
                }
                try {
                    iArr[ProtoBuf.Modality.OPEN.ordinal()] = 3;
                } catch (NoSuchFieldError unused3) {
                }
                try {
                    iArr[ProtoBuf.Modality.SEALED.ordinal()] = 4;
                } catch (NoSuchFieldError unused4) {
                }
                $EnumSwitchMapping$0 = iArr;
            }
        }

        @Override // org.jetbrains.kotlin.analysis.decompiler.stub.flags.FlagsToModifiers
        public KtModifierKeywordToken getModifiers(int flags) {
            ProtoBuf.Modality modality = Flags.MODALITY.get(flags);
            int i = modality == null ? -1 : WhenMappings.$EnumSwitchMapping$0[modality.ordinal()];
            if (i == -1) {
                throw new IllegalStateException("Unexpected modality: null");
            }
            if (i == 1) {
                KtModifierKeywordToken ABSTRACT_KEYWORD = KtTokens.ABSTRACT_KEYWORD;
                Intrinsics.checkNotNullExpressionValue(ABSTRACT_KEYWORD, "ABSTRACT_KEYWORD");
                return ABSTRACT_KEYWORD;
            }
            if (i == 2) {
                KtModifierKeywordToken FINAL_KEYWORD = KtTokens.FINAL_KEYWORD;
                Intrinsics.checkNotNullExpressionValue(FINAL_KEYWORD, "FINAL_KEYWORD");
                return FINAL_KEYWORD;
            }
            if (i == 3) {
                KtModifierKeywordToken OPEN_KEYWORD = KtTokens.OPEN_KEYWORD;
                Intrinsics.checkNotNullExpressionValue(OPEN_KEYWORD, "OPEN_KEYWORD");
                return OPEN_KEYWORD;
            }
            if (i == 4) {
                KtModifierKeywordToken SEALED_KEYWORD = KtTokens.SEALED_KEYWORD;
                Intrinsics.checkNotNullExpressionValue(SEALED_KEYWORD, "SEALED_KEYWORD");
                return SEALED_KEYWORD;
            }
            throw new NoWhenBranchMatchedException();
        }
    };
    private static final FlagsToModifiers VISIBILITY = new FlagsToModifiers() { // from class: org.jetbrains.kotlin.analysis.decompiler.stub.flags.FlagsKt$VISIBILITY$1

        /* JADX INFO: compiled from: flags.kt */
        @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
        public /* synthetic */ class WhenMappings {
            public static final /* synthetic */ int[] $EnumSwitchMapping$0;

            static {
                int[] iArr = new int[ProtoBuf.Visibility.values().length];
                try {
                    iArr[ProtoBuf.Visibility.PRIVATE.ordinal()] = 1;
                } catch (NoSuchFieldError unused) {
                }
                try {
                    iArr[ProtoBuf.Visibility.PRIVATE_TO_THIS.ordinal()] = 2;
                } catch (NoSuchFieldError unused2) {
                }
                try {
                    iArr[ProtoBuf.Visibility.INTERNAL.ordinal()] = 3;
                } catch (NoSuchFieldError unused3) {
                }
                try {
                    iArr[ProtoBuf.Visibility.PROTECTED.ordinal()] = 4;
                } catch (NoSuchFieldError unused4) {
                }
                try {
                    iArr[ProtoBuf.Visibility.PUBLIC.ordinal()] = 5;
                } catch (NoSuchFieldError unused5) {
                }
                $EnumSwitchMapping$0 = iArr;
            }
        }

        @Override // org.jetbrains.kotlin.analysis.decompiler.stub.flags.FlagsToModifiers
        public KtModifierKeywordToken getModifiers(int flags) {
            ProtoBuf.Visibility visibility = Flags.VISIBILITY.get(flags);
            int i = visibility == null ? -1 : WhenMappings.$EnumSwitchMapping$0[visibility.ordinal()];
            if (i == 1 || i == 2) {
                return KtTokens.PRIVATE_KEYWORD;
            }
            if (i == 3) {
                return KtTokens.INTERNAL_KEYWORD;
            }
            if (i == 4) {
                return KtTokens.PROTECTED_KEYWORD;
            }
            if (i == 5) {
                return KtTokens.PUBLIC_KEYWORD;
            }
            throw new IllegalStateException("Unexpected visibility: " + visibility);
        }
    };

    static {
        Flags.BooleanFlagField IS_INNER = Flags.IS_INNER;
        Intrinsics.checkNotNullExpressionValue(IS_INNER, "IS_INNER");
        KtModifierKeywordToken INNER_KEYWORD = KtTokens.INNER_KEYWORD;
        Intrinsics.checkNotNullExpressionValue(INNER_KEYWORD, "INNER_KEYWORD");
        INNER = createBooleanFlagToModifier(IS_INNER, INNER_KEYWORD);
        Flags.BooleanFlagField IS_CONST = Flags.IS_CONST;
        Intrinsics.checkNotNullExpressionValue(IS_CONST, "IS_CONST");
        KtModifierKeywordToken CONST_KEYWORD = KtTokens.CONST_KEYWORD;
        Intrinsics.checkNotNullExpressionValue(CONST_KEYWORD, "CONST_KEYWORD");
        CONST = createBooleanFlagToModifier(IS_CONST, CONST_KEYWORD);
        Flags.BooleanFlagField IS_LATEINIT = Flags.IS_LATEINIT;
        Intrinsics.checkNotNullExpressionValue(IS_LATEINIT, "IS_LATEINIT");
        KtModifierKeywordToken LATEINIT_KEYWORD = KtTokens.LATEINIT_KEYWORD;
        Intrinsics.checkNotNullExpressionValue(LATEINIT_KEYWORD, "LATEINIT_KEYWORD");
        LATEINIT = createBooleanFlagToModifier(IS_LATEINIT, LATEINIT_KEYWORD);
        Flags.BooleanFlagField IS_OPERATOR = Flags.IS_OPERATOR;
        Intrinsics.checkNotNullExpressionValue(IS_OPERATOR, "IS_OPERATOR");
        KtModifierKeywordToken OPERATOR_KEYWORD = KtTokens.OPERATOR_KEYWORD;
        Intrinsics.checkNotNullExpressionValue(OPERATOR_KEYWORD, "OPERATOR_KEYWORD");
        OPERATOR = createBooleanFlagToModifier(IS_OPERATOR, OPERATOR_KEYWORD);
        Flags.BooleanFlagField IS_INFIX = Flags.IS_INFIX;
        Intrinsics.checkNotNullExpressionValue(IS_INFIX, "IS_INFIX");
        KtModifierKeywordToken INFIX_KEYWORD = KtTokens.INFIX_KEYWORD;
        Intrinsics.checkNotNullExpressionValue(INFIX_KEYWORD, "INFIX_KEYWORD");
        INFIX = createBooleanFlagToModifier(IS_INFIX, INFIX_KEYWORD);
        Flags.BooleanFlagField IS_DATA = Flags.IS_DATA;
        Intrinsics.checkNotNullExpressionValue(IS_DATA, "IS_DATA");
        KtModifierKeywordToken DATA_KEYWORD = KtTokens.DATA_KEYWORD;
        Intrinsics.checkNotNullExpressionValue(DATA_KEYWORD, "DATA_KEYWORD");
        DATA = createBooleanFlagToModifier(IS_DATA, DATA_KEYWORD);
        Flags.BooleanFlagField IS_EXTERNAL_FUNCTION = Flags.IS_EXTERNAL_FUNCTION;
        Intrinsics.checkNotNullExpressionValue(IS_EXTERNAL_FUNCTION, "IS_EXTERNAL_FUNCTION");
        KtModifierKeywordToken EXTERNAL_KEYWORD = KtTokens.EXTERNAL_KEYWORD;
        Intrinsics.checkNotNullExpressionValue(EXTERNAL_KEYWORD, "EXTERNAL_KEYWORD");
        EXTERNAL_FUN = createBooleanFlagToModifier(IS_EXTERNAL_FUNCTION, EXTERNAL_KEYWORD);
        Flags.BooleanFlagField IS_EXTERNAL_PROPERTY = Flags.IS_EXTERNAL_PROPERTY;
        Intrinsics.checkNotNullExpressionValue(IS_EXTERNAL_PROPERTY, "IS_EXTERNAL_PROPERTY");
        KtModifierKeywordToken EXTERNAL_KEYWORD2 = KtTokens.EXTERNAL_KEYWORD;
        Intrinsics.checkNotNullExpressionValue(EXTERNAL_KEYWORD2, "EXTERNAL_KEYWORD");
        EXTERNAL_PROPERTY = createBooleanFlagToModifier(IS_EXTERNAL_PROPERTY, EXTERNAL_KEYWORD2);
        Flags.BooleanFlagField IS_EXTERNAL_ACCESSOR = Flags.IS_EXTERNAL_ACCESSOR;
        Intrinsics.checkNotNullExpressionValue(IS_EXTERNAL_ACCESSOR, "IS_EXTERNAL_ACCESSOR");
        KtModifierKeywordToken EXTERNAL_KEYWORD3 = KtTokens.EXTERNAL_KEYWORD;
        Intrinsics.checkNotNullExpressionValue(EXTERNAL_KEYWORD3, "EXTERNAL_KEYWORD");
        EXTERNAL_ACCESSOR = createBooleanFlagToModifier(IS_EXTERNAL_ACCESSOR, EXTERNAL_KEYWORD3);
        Flags.BooleanFlagField IS_EXTERNAL_CLASS = Flags.IS_EXTERNAL_CLASS;
        Intrinsics.checkNotNullExpressionValue(IS_EXTERNAL_CLASS, "IS_EXTERNAL_CLASS");
        KtModifierKeywordToken EXTERNAL_KEYWORD4 = KtTokens.EXTERNAL_KEYWORD;
        Intrinsics.checkNotNullExpressionValue(EXTERNAL_KEYWORD4, "EXTERNAL_KEYWORD");
        EXTERNAL_CLASS = createBooleanFlagToModifier(IS_EXTERNAL_CLASS, EXTERNAL_KEYWORD4);
        Flags.BooleanFlagField IS_INLINE = Flags.IS_INLINE;
        Intrinsics.checkNotNullExpressionValue(IS_INLINE, "IS_INLINE");
        KtModifierKeywordToken INLINE_KEYWORD = KtTokens.INLINE_KEYWORD;
        Intrinsics.checkNotNullExpressionValue(INLINE_KEYWORD, "INLINE_KEYWORD");
        INLINE = createBooleanFlagToModifier(IS_INLINE, INLINE_KEYWORD);
        Flags.BooleanFlagField IS_INLINE_ACCESSOR = Flags.IS_INLINE_ACCESSOR;
        Intrinsics.checkNotNullExpressionValue(IS_INLINE_ACCESSOR, "IS_INLINE_ACCESSOR");
        KtModifierKeywordToken INLINE_KEYWORD2 = KtTokens.INLINE_KEYWORD;
        Intrinsics.checkNotNullExpressionValue(INLINE_KEYWORD2, "INLINE_KEYWORD");
        INLINE_ACCESSOR = createBooleanFlagToModifier(IS_INLINE_ACCESSOR, INLINE_KEYWORD2);
        Flags.BooleanFlagField IS_VALUE_CLASS = Flags.IS_VALUE_CLASS;
        Intrinsics.checkNotNullExpressionValue(IS_VALUE_CLASS, "IS_VALUE_CLASS");
        KtModifierKeywordToken VALUE_KEYWORD = KtTokens.VALUE_KEYWORD;
        Intrinsics.checkNotNullExpressionValue(VALUE_KEYWORD, "VALUE_KEYWORD");
        VALUE_CLASS = createBooleanFlagToModifier(IS_VALUE_CLASS, VALUE_KEYWORD);
        Flags.BooleanFlagField IS_FUN_INTERFACE = Flags.IS_FUN_INTERFACE;
        Intrinsics.checkNotNullExpressionValue(IS_FUN_INTERFACE, "IS_FUN_INTERFACE");
        KtModifierKeywordToken FUN_KEYWORD = KtTokens.FUN_KEYWORD;
        Intrinsics.checkNotNullExpressionValue(FUN_KEYWORD, "FUN_KEYWORD");
        FUN_INTERFACE = createBooleanFlagToModifier(IS_FUN_INTERFACE, FUN_KEYWORD);
        Flags.BooleanFlagField IS_TAILREC = Flags.IS_TAILREC;
        Intrinsics.checkNotNullExpressionValue(IS_TAILREC, "IS_TAILREC");
        KtModifierKeywordToken TAILREC_KEYWORD = KtTokens.TAILREC_KEYWORD;
        Intrinsics.checkNotNullExpressionValue(TAILREC_KEYWORD, "TAILREC_KEYWORD");
        TAILREC = createBooleanFlagToModifier(IS_TAILREC, TAILREC_KEYWORD);
        Flags.BooleanFlagField IS_SUSPEND = Flags.IS_SUSPEND;
        Intrinsics.checkNotNullExpressionValue(IS_SUSPEND, "IS_SUSPEND");
        KtModifierKeywordToken SUSPEND_KEYWORD = KtTokens.SUSPEND_KEYWORD;
        Intrinsics.checkNotNullExpressionValue(SUSPEND_KEYWORD, "SUSPEND_KEYWORD");
        SUSPEND = createBooleanFlagToModifier(IS_SUSPEND, SUSPEND_KEYWORD);
        Flags.BooleanFlagField IS_EXPECT_CLASS = Flags.IS_EXPECT_CLASS;
        Intrinsics.checkNotNullExpressionValue(IS_EXPECT_CLASS, "IS_EXPECT_CLASS");
        KtModifierKeywordToken EXPECT_KEYWORD = KtTokens.EXPECT_KEYWORD;
        Intrinsics.checkNotNullExpressionValue(EXPECT_KEYWORD, "EXPECT_KEYWORD");
        EXPECT_CLASS = createBooleanFlagToModifier(IS_EXPECT_CLASS, EXPECT_KEYWORD);
        Flags.BooleanFlagField IS_EXPECT_FUNCTION = Flags.IS_EXPECT_FUNCTION;
        Intrinsics.checkNotNullExpressionValue(IS_EXPECT_FUNCTION, "IS_EXPECT_FUNCTION");
        KtModifierKeywordToken EXPECT_KEYWORD2 = KtTokens.EXPECT_KEYWORD;
        Intrinsics.checkNotNullExpressionValue(EXPECT_KEYWORD2, "EXPECT_KEYWORD");
        EXPECT_FUNCTION = createBooleanFlagToModifier(IS_EXPECT_FUNCTION, EXPECT_KEYWORD2);
        Flags.BooleanFlagField IS_EXPECT_PROPERTY = Flags.IS_EXPECT_PROPERTY;
        Intrinsics.checkNotNullExpressionValue(IS_EXPECT_PROPERTY, "IS_EXPECT_PROPERTY");
        KtModifierKeywordToken EXPECT_KEYWORD3 = KtTokens.EXPECT_KEYWORD;
        Intrinsics.checkNotNullExpressionValue(EXPECT_KEYWORD3, "EXPECT_KEYWORD");
        EXPECT_PROPERTY = createBooleanFlagToModifier(IS_EXPECT_PROPERTY, EXPECT_KEYWORD3);
    }

    public static final FlagsToModifiers getMODALITY() {
        return MODALITY;
    }

    public static final FlagsToModifiers getVISIBILITY() {
        return VISIBILITY;
    }

    public static final FlagsToModifiers getINNER() {
        return INNER;
    }

    public static final FlagsToModifiers getCONST() {
        return CONST;
    }

    public static final FlagsToModifiers getLATEINIT() {
        return LATEINIT;
    }

    public static final FlagsToModifiers getOPERATOR() {
        return OPERATOR;
    }

    public static final FlagsToModifiers getINFIX() {
        return INFIX;
    }

    public static final FlagsToModifiers getDATA() {
        return DATA;
    }

    public static final FlagsToModifiers getEXTERNAL_FUN() {
        return EXTERNAL_FUN;
    }

    public static final FlagsToModifiers getEXTERNAL_PROPERTY() {
        return EXTERNAL_PROPERTY;
    }

    public static final FlagsToModifiers getEXTERNAL_ACCESSOR() {
        return EXTERNAL_ACCESSOR;
    }

    public static final FlagsToModifiers getEXTERNAL_CLASS() {
        return EXTERNAL_CLASS;
    }

    public static final FlagsToModifiers getINLINE() {
        return INLINE;
    }

    public static final FlagsToModifiers getINLINE_ACCESSOR() {
        return INLINE_ACCESSOR;
    }

    public static final FlagsToModifiers getVALUE_CLASS() {
        return VALUE_CLASS;
    }

    public static final FlagsToModifiers getFUN_INTERFACE() {
        return FUN_INTERFACE;
    }

    public static final FlagsToModifiers getTAILREC() {
        return TAILREC;
    }

    public static final FlagsToModifiers getSUSPEND() {
        return SUSPEND;
    }

    public static final FlagsToModifiers getEXPECT_CLASS() {
        return EXPECT_CLASS;
    }

    public static final FlagsToModifiers getEXPECT_FUNCTION() {
        return EXPECT_FUNCTION;
    }

    public static final FlagsToModifiers getEXPECT_PROPERTY() {
        return EXPECT_PROPERTY;
    }

    private static final FlagsToModifiers createBooleanFlagToModifier(Flags.BooleanFlagField booleanFlagField, KtModifierKeywordToken ktModifierKeywordToken) {
        return new BooleanFlagToModifier(booleanFlagField, ktModifierKeywordToken);
    }
}
