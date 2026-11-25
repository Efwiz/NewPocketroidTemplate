package org.jetbrains.kotlin.psi.stubs.impl;

import kotlin.Metadata;
import kotlin.collections.ArraysKt;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.lexer.KtModifierKeywordToken;
import org.jetbrains.kotlin.lexer.KtTokens;
import org.jetbrains.kotlin.psi.KtModifierList;

/* JADX INFO: compiled from: ModifierMaskUtils.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\t\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000e\n\u0000\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u001c\u0010\u0003\u001a\u00020\u00042\u0012\u0010\u0005\u001a\u000e\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020\b0\u0006H\u0007J\u0010\u0010\t\u001a\u00020\u00042\u0006\u0010\n\u001a\u00020\u000bH\u0007J\u0018\u0010\f\u001a\u00020\b2\u0006\u0010\r\u001a\u00020\u00042\u0006\u0010\u000e\u001a\u00020\u0007H\u0007J\u0010\u0010\u000f\u001a\u00020\u00102\u0006\u0010\r\u001a\u00020\u0004H\u0007¨\u0006\u0011"}, d2 = {"Lorg/jetbrains/kotlin/psi/stubs/impl/ModifierMaskUtils;", "", "()V", "computeMask", "", "hasModifier", "Lkotlin/Function1;", "Lorg/jetbrains/kotlin/lexer/KtModifierKeywordToken;", "", "computeMaskFromModifierList", "modifierList", "Lorg/jetbrains/kotlin/psi/KtModifierList;", "maskHasModifier", "mask", "modifierToken", "maskToString", "", "psi"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ModifierMaskUtils {
    public static final ModifierMaskUtils INSTANCE = new ModifierMaskUtils();

    private ModifierMaskUtils() {
    }

    static {
        int length = KtTokens.MODIFIER_KEYWORDS_ARRAY.length;
    }

    @JvmStatic
    public static final long computeMaskFromModifierList(final KtModifierList modifierList) {
        Intrinsics.checkNotNullParameter(modifierList, "modifierList");
        return computeMask(new Function1<KtModifierKeywordToken, Boolean>() { // from class: org.jetbrains.kotlin.psi.stubs.impl.ModifierMaskUtils.computeMaskFromModifierList.1
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final Boolean invoke(KtModifierKeywordToken it) {
                Intrinsics.checkNotNullParameter(it, "it");
                return Boolean.valueOf(modifierList.hasModifier(it));
            }
        });
    }

    @JvmStatic
    public static final long computeMask(Function1<? super KtModifierKeywordToken, Boolean> hasModifier) {
        Intrinsics.checkNotNullParameter(hasModifier, "hasModifier");
        KtModifierKeywordToken[] MODIFIER_KEYWORDS_ARRAY = KtTokens.MODIFIER_KEYWORDS_ARRAY;
        Intrinsics.checkNotNullExpressionValue(MODIFIER_KEYWORDS_ARRAY, "MODIFIER_KEYWORDS_ARRAY");
        int length = MODIFIER_KEYWORDS_ARRAY.length;
        long j = 0;
        for (int i = 0; i < length; i++) {
            KtModifierKeywordToken ktModifierKeywordToken = MODIFIER_KEYWORDS_ARRAY[i];
            Intrinsics.checkNotNull(ktModifierKeywordToken);
            if (hasModifier.invoke(ktModifierKeywordToken).booleanValue()) {
                j |= 1 << i;
            }
        }
        return j;
    }

    @JvmStatic
    public static final boolean maskHasModifier(long mask, KtModifierKeywordToken modifierToken) {
        Intrinsics.checkNotNullParameter(modifierToken, "modifierToken");
        KtModifierKeywordToken[] MODIFIER_KEYWORDS_ARRAY = KtTokens.MODIFIER_KEYWORDS_ARRAY;
        Intrinsics.checkNotNullExpressionValue(MODIFIER_KEYWORDS_ARRAY, "MODIFIER_KEYWORDS_ARRAY");
        return (mask & (1 << ArraysKt.indexOf(MODIFIER_KEYWORDS_ARRAY, modifierToken))) != 0;
    }

    @JvmStatic
    public static final String maskToString(long mask) {
        StringBuilder sb = new StringBuilder("[");
        KtModifierKeywordToken[] MODIFIER_KEYWORDS_ARRAY = KtTokens.MODIFIER_KEYWORDS_ARRAY;
        Intrinsics.checkNotNullExpressionValue(MODIFIER_KEYWORDS_ARRAY, "MODIFIER_KEYWORDS_ARRAY");
        boolean z = true;
        for (KtModifierKeywordToken ktModifierKeywordToken : MODIFIER_KEYWORDS_ARRAY) {
            Intrinsics.checkNotNull(ktModifierKeywordToken);
            if (maskHasModifier(mask, ktModifierKeywordToken)) {
                if (!z) {
                    sb.append(" ");
                }
                sb.append(ktModifierKeywordToken.getValue());
                z = false;
            }
        }
        sb.append("]");
        String string = sb.toString();
        Intrinsics.checkNotNullExpressionValue(string, "toString(...)");
        return string;
    }
}
