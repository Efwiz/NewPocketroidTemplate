package org.jetbrains.kotlin.com.intellij.psi.scope.processor;

import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;
import org.jetbrains.kotlin.com.intellij.psi.PsiClass;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiExpressionList;
import org.jetbrains.kotlin.com.intellij.psi.PsiFile;
import org.jetbrains.kotlin.com.intellij.psi.PsiMethodCallExpression;
import org.jetbrains.kotlin.com.intellij.psi.ResolveState;
import org.jetbrains.kotlin.com.intellij.psi.scope.JavaScopeProcessorEvent;
import org.jetbrains.kotlin.com.intellij.psi.scope.PsiConflictResolver;
import org.jetbrains.kotlin.com.intellij.psi.scope.PsiScopeProcessor;
import org.jetbrains.kotlin.com.intellij.psi.scope.conflictResolvers.JavaMethodsConflictResolver;
import org.jetbrains.kotlin.com.intellij.psi.util.PsiUtil;
import org.jetbrains.kotlin.com.intellij.util.SmartList;

/* JADX INFO: loaded from: classes3.dex */
public class MethodResolverProcessor extends MethodCandidatesProcessor {
    private boolean myStopAcceptingCandidates;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        Object[] objArr = new Object[3];
        switch (i) {
            case 1:
            case 4:
            case 6:
                objArr[0] = "placeFile";
                break;
            case 2:
                objArr[0] = "argumentList";
                break;
            case 3:
            case 5:
            default:
                objArr[0] = "place";
                break;
            case 7:
                objArr[0] = "resolvers";
                break;
            case 8:
                objArr[0] = "event";
                break;
            case 9:
                objArr[0] = CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD;
                break;
            case 10:
                objArr[0] = "state";
                break;
        }
        objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/scope/processor/MethodResolverProcessor";
        switch (i) {
            case 8:
                objArr[2] = "handleEvent";
                break;
            case 9:
            case 10:
                objArr[2] = "execute";
                break;
            default:
                objArr[2] = "<init>";
                break;
        }
        throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.scope.processor.MethodCandidatesProcessor
    protected boolean acceptVarargs() {
        return true;
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public MethodResolverProcessor(PsiMethodCallExpression psiMethodCallExpression, PsiFile psiFile) {
        this(psiMethodCallExpression, psiFile, new PsiConflictResolver[]{new JavaMethodsConflictResolver(psiMethodCallExpression.getArgumentList(), null, PsiUtil.getLanguageLevel(psiFile), psiFile)});
        if (psiMethodCallExpression == null) {
            $$$reportNull$$$0(0);
        }
        if (psiFile == null) {
            $$$reportNull$$$0(1);
        }
        setArgumentList(psiMethodCallExpression.getArgumentList());
        obtainTypeArguments(psiMethodCallExpression);
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public MethodResolverProcessor(PsiClass psiClass, PsiExpressionList psiExpressionList, PsiElement psiElement, PsiFile psiFile) {
        super(psiElement, psiFile, new PsiConflictResolver[]{new JavaMethodsConflictResolver(psiExpressionList, null, PsiUtil.getLanguageLevel(psiFile), psiFile)}, new SmartList());
        if (psiExpressionList == null) {
            $$$reportNull$$$0(2);
        }
        if (psiElement == null) {
            $$$reportNull$$$0(3);
        }
        if (psiFile == null) {
            $$$reportNull$$$0(4);
        }
        setIsConstructor(true);
        setAccessClass(psiClass);
        setArgumentList(psiExpressionList);
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public MethodResolverProcessor(PsiElement psiElement, PsiFile psiFile, PsiConflictResolver[] psiConflictResolverArr) {
        super(psiElement, psiFile, psiConflictResolverArr, new SmartList());
        if (psiElement == null) {
            $$$reportNull$$$0(5);
        }
        if (psiFile == null) {
            $$$reportNull$$$0(6);
        }
        if (psiConflictResolverArr == null) {
            $$$reportNull$$$0(7);
        }
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.scope.processor.MethodsProcessor, org.jetbrains.kotlin.com.intellij.psi.scope.processor.ConflictFilterProcessor, org.jetbrains.kotlin.com.intellij.psi.scope.processor.FilterScopeProcessor, org.jetbrains.kotlin.com.intellij.psi.scope.PsiScopeProcessor
    public void handleEvent(PsiScopeProcessor.Event event, Object obj) {
        if (event == null) {
            $$$reportNull$$$0(8);
        }
        if (event == JavaScopeProcessorEvent.CHANGE_LEVEL && this.myHasAccessibleStaticCorrectCandidate) {
            this.myStopAcceptingCandidates = true;
        }
        super.handleEvent(event, obj);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.scope.processor.ConflictFilterProcessor, org.jetbrains.kotlin.com.intellij.psi.scope.processor.FilterScopeProcessor, org.jetbrains.kotlin.com.intellij.psi.scope.PsiScopeProcessor
    public boolean execute(PsiElement psiElement, ResolveState resolveState) {
        if (psiElement == null) {
            $$$reportNull$$$0(9);
        }
        if (resolveState == null) {
            $$$reportNull$$$0(10);
        }
        return !this.myStopAcceptingCandidates && super.execute(psiElement, resolveState);
    }
}
