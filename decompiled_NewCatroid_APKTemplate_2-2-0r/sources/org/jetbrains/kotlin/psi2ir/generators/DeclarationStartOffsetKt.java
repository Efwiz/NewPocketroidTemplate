package org.jetbrains.kotlin.psi2ir.generators;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.tree.TokenSet;
import org.jetbrains.kotlin.lexer.KtTokens;
import org.jetbrains.kotlin.psi.KtClassOrObject;
import org.jetbrains.kotlin.psi.KtElement;
import org.jetbrains.kotlin.psi.KtNamedFunction;
import org.jetbrains.kotlin.psi.KtParameter;
import org.jetbrains.kotlin.psi.KtPrimaryConstructor;
import org.jetbrains.kotlin.psi.KtProperty;
import org.jetbrains.kotlin.psi.KtPropertyAccessor;
import org.jetbrains.kotlin.psi.KtPureClassOrObject;
import org.jetbrains.kotlin.psi.KtPureElement;
import org.jetbrains.kotlin.psi.KtSecondaryConstructor;
import org.jetbrains.kotlin.psi.psiUtil.PsiUtilsKt;
import org.jetbrains.kotlin.psi2ir.KotlinUtilsKt;

/* JADX INFO: compiled from: declarationStartOffset.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\"\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\b\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\u001a\u0013\u0010\u0007\u001a\u0004\u0018\u00010\b*\u00020\tH\u0000¢\u0006\u0002\u0010\n\u001a\u0013\u0010\u000b\u001a\u0004\u0018\u00010\b*\u00020\fH\u0000¢\u0006\u0002\u0010\r\u001a\u0013\u0010\u000e\u001a\u0004\u0018\u00010\b*\u00020\fH\u0000¢\u0006\u0002\u0010\r\"\u0013\u0010\u0000\u001a\u00070\u0001¢\u0006\u0002\b\u0002X\u0082\u0004¢\u0006\u0002\n\u0000\"\u0013\u0010\u0003\u001a\u00070\u0001¢\u0006\u0002\b\u0002X\u0082\u0004¢\u0006\u0002\n\u0000\"\u0013\u0010\u0004\u001a\u00070\u0001¢\u0006\u0002\b\u0002X\u0082\u0004¢\u0006\u0002\n\u0000\"\u0013\u0010\u0005\u001a\u00070\u0001¢\u0006\u0002\b\u0002X\u0082\u0004¢\u0006\u0002\n\u0000\"\u0013\u0010\u0006\u001a\u00070\u0001¢\u0006\u0002\b\u0002X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u000f"}, d2 = {"ACCESSOR_DECL_TOKENS", "Lorg/jetbrains/kotlin/com/intellij/psi/tree/TokenSet;", "Lorg/jetbrains/annotations/NotNull;", "CLASS_DECL_TOKENS", "CONSTRUCTOR_DECL_TOKENS", "FUNCTION_DECL_TOKENS", "PROPERTY_DECL_TOKENS", "getStartOffsetOfClassDeclarationOrNull", "", "Lorg/jetbrains/kotlin/psi/KtPureClassOrObject;", "(Lorg/jetbrains/kotlin/psi/KtPureClassOrObject;)Ljava/lang/Integer;", "getStartOffsetOfConstructorDeclarationKeywordOrNull", "Lorg/jetbrains/kotlin/psi/KtPureElement;", "(Lorg/jetbrains/kotlin/psi/KtPureElement;)Ljava/lang/Integer;", "getStartOffsetOfFunctionDeclarationKeywordOrNull", "ir.psi2ir"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class DeclarationStartOffsetKt {
    private static final TokenSet ACCESSOR_DECL_TOKENS;
    private static final TokenSet CLASS_DECL_TOKENS;
    private static final TokenSet CONSTRUCTOR_DECL_TOKENS;
    private static final TokenSet FUNCTION_DECL_TOKENS;
    private static final TokenSet PROPERTY_DECL_TOKENS;

    static {
        TokenSet tokenSetCreate = TokenSet.create(KtTokens.FUN_KEYWORD);
        Intrinsics.checkNotNullExpressionValue(tokenSetCreate, "create(...)");
        FUNCTION_DECL_TOKENS = tokenSetCreate;
        TokenSet tokenSetCreate2 = TokenSet.create(KtTokens.GET_KEYWORD, KtTokens.SET_KEYWORD);
        Intrinsics.checkNotNullExpressionValue(tokenSetCreate2, "create(...)");
        ACCESSOR_DECL_TOKENS = tokenSetCreate2;
        TokenSet tokenSetCreate3 = TokenSet.create(KtTokens.VAL_KEYWORD, KtTokens.VAR_KEYWORD);
        Intrinsics.checkNotNullExpressionValue(tokenSetCreate3, "create(...)");
        PROPERTY_DECL_TOKENS = tokenSetCreate3;
        TokenSet tokenSetCreate4 = TokenSet.create(KtTokens.CLASS_KEYWORD, KtTokens.INTERFACE_KEYWORD);
        Intrinsics.checkNotNullExpressionValue(tokenSetCreate4, "create(...)");
        CLASS_DECL_TOKENS = tokenSetCreate4;
        TokenSet tokenSetCreate5 = TokenSet.create(KtTokens.CONSTRUCTOR_KEYWORD);
        Intrinsics.checkNotNullExpressionValue(tokenSetCreate5, "create(...)");
        CONSTRUCTOR_DECL_TOKENS = tokenSetCreate5;
    }

    public static final Integer getStartOffsetOfFunctionDeclarationKeywordOrNull(KtPureElement ktPureElement) {
        Intrinsics.checkNotNullParameter(ktPureElement, "<this>");
        if (ktPureElement instanceof KtNamedFunction) {
            return KotlinUtilsKt.getChildTokenStartOffsetOrNull((KtElement) ktPureElement, FUNCTION_DECL_TOKENS);
        }
        if (ktPureElement instanceof KtPropertyAccessor) {
            return KotlinUtilsKt.getChildTokenStartOffsetOrNull((KtElement) ktPureElement, ACCESSOR_DECL_TOKENS);
        }
        if (!(ktPureElement instanceof KtProperty) && !(ktPureElement instanceof KtParameter)) {
            if (ktPureElement instanceof KtClassOrObject) {
                return KotlinUtilsKt.getChildTokenStartOffsetOrNull((KtElement) ktPureElement, CLASS_DECL_TOKENS);
            }
            return null;
        }
        return KotlinUtilsKt.getChildTokenStartOffsetOrNull((KtElement) ktPureElement, PROPERTY_DECL_TOKENS);
    }

    public static final Integer getStartOffsetOfClassDeclarationOrNull(KtPureClassOrObject ktPureClassOrObject) {
        Intrinsics.checkNotNullParameter(ktPureClassOrObject, "<this>");
        if (ktPureClassOrObject instanceof KtClassOrObject) {
            return Integer.valueOf(PsiUtilsKt.getStartOffsetSkippingComments((PsiElement) ktPureClassOrObject));
        }
        return null;
    }

    public static final Integer getStartOffsetOfConstructorDeclarationKeywordOrNull(KtPureElement ktPureElement) {
        KtElement ktElement;
        Integer childTokenStartOffsetOrNull;
        Intrinsics.checkNotNullParameter(ktPureElement, "<this>");
        if (!(ktPureElement instanceof KtPrimaryConstructor) && !(ktPureElement instanceof KtSecondaryConstructor)) {
            if (!(ktPureElement instanceof KtClassOrObject)) {
                return null;
            }
            KtPrimaryConstructor primaryConstructor = ((KtClassOrObject) ktPureElement).getPrimaryConstructor();
            return ((primaryConstructor == null || (childTokenStartOffsetOrNull = getStartOffsetOfConstructorDeclarationKeywordOrNull(primaryConstructor)) == null) && (childTokenStartOffsetOrNull = KotlinUtilsKt.getChildTokenStartOffsetOrNull((ktElement = (KtElement) ktPureElement), CONSTRUCTOR_DECL_TOKENS)) == null) ? KotlinUtilsKt.getChildTokenStartOffsetOrNull(ktElement, CLASS_DECL_TOKENS) : childTokenStartOffsetOrNull;
        }
        return KotlinUtilsKt.getChildTokenStartOffsetOrNull((KtElement) ktPureElement, CONSTRUCTOR_DECL_TOKENS);
    }
}
