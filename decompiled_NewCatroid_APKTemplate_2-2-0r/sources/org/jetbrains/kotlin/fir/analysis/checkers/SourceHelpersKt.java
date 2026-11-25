package org.jetbrains.kotlin.fir.analysis.checkers;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.descriptors.Visibilities;
import org.jetbrains.kotlin.descriptors.Visibility;
import org.jetbrains.kotlin.lexer.KtModifierKeywordToken;
import org.jetbrains.kotlin.lexer.KtTokens;

/* JADX INFO: compiled from: SourceHelpers.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\f\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\u001a\f\u0010\u0000\u001a\u0004\u0018\u00010\u0001*\u00020\u0002¨\u0006\u0003"}, d2 = {"toVisibilityOrNull", "Lorg/jetbrains/kotlin/descriptors/Visibility;", "Lorg/jetbrains/kotlin/lexer/KtModifierKeywordToken;", "checkers"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class SourceHelpersKt {
    public static final Visibility toVisibilityOrNull(KtModifierKeywordToken ktModifierKeywordToken) {
        Intrinsics.checkNotNullParameter(ktModifierKeywordToken, "<this>");
        if (Intrinsics.areEqual(ktModifierKeywordToken, KtTokens.PUBLIC_KEYWORD)) {
            return Visibilities.Public.INSTANCE;
        }
        if (Intrinsics.areEqual(ktModifierKeywordToken, KtTokens.PRIVATE_KEYWORD)) {
            return Visibilities.Private.INSTANCE;
        }
        if (Intrinsics.areEqual(ktModifierKeywordToken, KtTokens.PROTECTED_KEYWORD)) {
            return Visibilities.Protected.INSTANCE;
        }
        if (Intrinsics.areEqual(ktModifierKeywordToken, KtTokens.INTERNAL_KEYWORD)) {
            return Visibilities.Internal.INSTANCE;
        }
        return null;
    }
}
