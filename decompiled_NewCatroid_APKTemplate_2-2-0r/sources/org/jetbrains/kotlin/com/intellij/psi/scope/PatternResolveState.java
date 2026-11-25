package org.jetbrains.kotlin.com.intellij.psi.scope;

import org.jetbrains.kotlin.com.intellij.openapi.util.Key;
import org.jetbrains.kotlin.com.intellij.psi.JavaTokenType;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiExpression;
import org.jetbrains.kotlin.com.intellij.psi.PsiInstanceOfExpression;
import org.jetbrains.kotlin.com.intellij.psi.PsiParenthesizedExpression;
import org.jetbrains.kotlin.com.intellij.psi.PsiPatternVariable;
import org.jetbrains.kotlin.com.intellij.psi.PsiPolyadicExpression;
import org.jetbrains.kotlin.com.intellij.psi.PsiPrefixExpression;
import org.jetbrains.kotlin.com.intellij.psi.ResolveState;

/* JADX INFO: loaded from: classes3.dex */
public enum PatternResolveState {
    WHEN_TRUE,
    WHEN_FALSE,
    WHEN_BOTH,
    WHEN_NONE;

    public static final Key<PatternResolveState> KEY = Key.create("JavaPatternDeclarationHint");

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        throw new IllegalStateException(String.format("@NotNull method %s.%s must not return null", "org/jetbrains/kotlin/com/intellij/psi/scope/PatternResolveState", "stateAtParent"));
    }

    public static PatternResolveState fromBoolean(boolean z) {
        return z ? WHEN_TRUE : WHEN_FALSE;
    }

    /* JADX INFO: renamed from: org.jetbrains.kotlin.com.intellij.psi.scope.PatternResolveState$1, reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$intellij$psi$scope$PatternResolveState;

        static {
            int[] iArr = new int[PatternResolveState.values().length];
            $SwitchMap$com$intellij$psi$scope$PatternResolveState = iArr;
            try {
                iArr[PatternResolveState.WHEN_TRUE.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$intellij$psi$scope$PatternResolveState[PatternResolveState.WHEN_FALSE.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
        }
    }

    public PatternResolveState invert() {
        int i = AnonymousClass1.$SwitchMap$com$intellij$psi$scope$PatternResolveState[ordinal()];
        if (i != 1) {
            return i != 2 ? this : WHEN_TRUE;
        }
        return WHEN_FALSE;
    }

    public ResolveState putInto(ResolveState resolveState) {
        return resolveState.put(KEY, this);
    }

    /* JADX WARN: Code duplicated, block: B:15:0x002c  */
    /* JADX WARN: Code duplicated, block: B:22:0x0045  */
    public static PatternResolveState stateAtParent(PsiPatternVariable psiPatternVariable, PsiExpression psiExpression) {
        PatternResolveState patternResolveState;
        PsiElement pattern = psiPatternVariable.getPattern();
        PatternResolveState patternResolveStateInvert = WHEN_TRUE;
        PsiElement lightParent = pattern.getLightParent();
        while (pattern != psiExpression) {
            if (!(lightParent instanceof PsiInstanceOfExpression) && !(lightParent instanceof PsiParenthesizedExpression)) {
                if (lightParent instanceof PsiPolyadicExpression) {
                    PsiPolyadicExpression psiPolyadicExpression = (PsiPolyadicExpression) lightParent;
                    if (psiPolyadicExpression.getOperationTokenType() != JavaTokenType.ANDAND && psiPolyadicExpression.getOperationTokenType() != JavaTokenType.OROR) {
                        if (!(lightParent instanceof PsiPrefixExpression) && ((PsiPrefixExpression) lightParent).getOperationTokenType() == JavaTokenType.EXCL) {
                            patternResolveStateInvert = patternResolveStateInvert.invert();
                        } else {
                            patternResolveState = WHEN_NONE;
                            if (patternResolveState == null) {
                                $$$reportNull$$$0(0);
                            }
                            return patternResolveState;
                        }
                    }
                } else {
                    if (!(lightParent instanceof PsiPrefixExpression)) {
                    }
                    patternResolveState = WHEN_NONE;
                    if (patternResolveState == null) {
                        $$$reportNull$$$0(0);
                    }
                    return patternResolveState;
                }
            }
            PsiElement psiElement = lightParent;
            lightParent = lightParent.getLightParent();
            pattern = psiElement;
        }
        if (patternResolveStateInvert == null) {
            $$$reportNull$$$0(1);
        }
        return patternResolveStateInvert;
    }
}
