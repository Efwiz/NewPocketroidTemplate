package org.jetbrains.kotlin.com.intellij.psi.scope.processor;

import java.util.List;
import org.jetbrains.kotlin.com.intellij.openapi.util.Key;
import org.jetbrains.kotlin.com.intellij.pom.java.LanguageLevel;
import org.jetbrains.kotlin.com.intellij.psi.PsiCallExpression;
import org.jetbrains.kotlin.com.intellij.psi.PsiClass;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiExpressionList;
import org.jetbrains.kotlin.com.intellij.psi.PsiFile;
import org.jetbrains.kotlin.com.intellij.psi.PsiMethod;
import org.jetbrains.kotlin.com.intellij.psi.PsiSubstitutor;
import org.jetbrains.kotlin.com.intellij.psi.PsiType;
import org.jetbrains.kotlin.com.intellij.psi.filters.ElementFilter;
import org.jetbrains.kotlin.com.intellij.psi.infos.CandidateInfo;
import org.jetbrains.kotlin.com.intellij.psi.scope.ElementClassFilter;
import org.jetbrains.kotlin.com.intellij.psi.scope.ElementClassHint;
import org.jetbrains.kotlin.com.intellij.psi.scope.JavaScopeProcessorEvent;
import org.jetbrains.kotlin.com.intellij.psi.scope.PsiConflictResolver;
import org.jetbrains.kotlin.com.intellij.psi.scope.PsiScopeProcessor;
import org.jetbrains.kotlin.com.intellij.psi.util.PsiUtil;
import org.jetbrains.kotlin.js.translate.context.Namer;

/* JADX INFO: loaded from: classes3.dex */
public abstract class MethodsProcessor extends ConflictFilterProcessor implements ElementClassHint {
    private static final ElementFilter ourFilter = ElementClassFilter.METHOD;
    PsiClass myAccessClass;
    private PsiExpressionList myArgumentList;
    protected PsiElement myCurrentFileContext;
    private boolean myIsConstructor;
    private final LanguageLevel myLanguageLevel;
    private boolean myStaticScopeFlag;
    private PsiType[] myTypeArguments;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = i != 4 ? "Argument for @NotNull parameter '%s' of %s.%s must not be null" : "@NotNull method %s.%s must not return null";
        Object[] objArr = new Object[i != 4 ? 3 : 2];
        switch (i) {
            case 1:
                objArr[0] = "place";
                break;
            case 2:
                objArr[0] = "placeFile";
                break;
            case 3:
                objArr[0] = "resolvers";
                break;
            case 4:
                objArr[0] = "org/jetbrains/kotlin/com/intellij/psi/scope/processor/MethodsProcessor";
                break;
            case 5:
                objArr[0] = "callExpression";
                break;
            case 6:
                objArr[0] = "event";
                break;
            case 7:
                objArr[0] = "method";
                break;
            case 8:
                objArr[0] = "hintKey";
                break;
            case 9:
                objArr[0] = Namer.METADATA_CLASS_KIND;
                break;
            default:
                objArr[0] = "container";
                break;
        }
        if (i != 4) {
            objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/scope/processor/MethodsProcessor";
        } else {
            objArr[1] = "getLanguageLevel";
        }
        switch (i) {
            case 4:
                break;
            case 5:
                objArr[2] = "obtainTypeArguments";
                break;
            case 6:
                objArr[2] = "handleEvent";
                break;
            case 7:
                objArr[2] = "forceAddResult";
                break;
            case 8:
                objArr[2] = "getHint";
                break;
            case 9:
                objArr[2] = "shouldProcess";
                break;
            default:
                objArr[2] = "<init>";
                break;
        }
        String str2 = String.format(str, objArr);
        if (i == 4) {
            throw new IllegalStateException(str2);
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    MethodsProcessor(PsiConflictResolver[] psiConflictResolverArr, List<CandidateInfo> list, PsiElement psiElement, PsiFile psiFile) {
        super(null, ourFilter, psiConflictResolverArr, list, psiElement, psiFile);
        if (list == null) {
            $$$reportNull$$$0(0);
        }
        if (psiElement == null) {
            $$$reportNull$$$0(1);
        }
        if (psiFile == null) {
            $$$reportNull$$$0(2);
        }
        if (psiConflictResolverArr == null) {
            $$$reportNull$$$0(3);
        }
        this.myLanguageLevel = PsiUtil.getLanguageLevel(psiFile);
    }

    public PsiExpressionList getArgumentList() {
        return this.myArgumentList;
    }

    public void setArgumentList(PsiExpressionList psiExpressionList) {
        this.myArgumentList = psiExpressionList;
    }

    public LanguageLevel getLanguageLevel() {
        LanguageLevel languageLevel = this.myLanguageLevel;
        if (languageLevel == null) {
            $$$reportNull$$$0(4);
        }
        return languageLevel;
    }

    public void obtainTypeArguments(PsiCallExpression psiCallExpression) {
        if (psiCallExpression == null) {
            $$$reportNull$$$0(5);
        }
        PsiType[] typeArguments = psiCallExpression.getTypeArguments();
        if (typeArguments.length > 0) {
            setTypeArguments(typeArguments);
        }
    }

    private void setTypeArguments(PsiType[] psiTypeArr) {
        this.myTypeArguments = psiTypeArr;
    }

    public PsiType[] getTypeArguments() {
        return this.myTypeArguments;
    }

    boolean isInStaticScope() {
        return this.myStaticScopeFlag;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.scope.processor.ConflictFilterProcessor, org.jetbrains.kotlin.com.intellij.psi.scope.processor.FilterScopeProcessor, org.jetbrains.kotlin.com.intellij.psi.scope.PsiScopeProcessor
    public void handleEvent(PsiScopeProcessor.Event event, Object obj) {
        if (event == null) {
            $$$reportNull$$$0(6);
        }
        if (JavaScopeProcessorEvent.isEnteringStaticScope(event, obj)) {
            this.myStaticScopeFlag = true;
        } else if (JavaScopeProcessorEvent.SET_CURRENT_FILE_CONTEXT.equals(event)) {
            this.myCurrentFileContext = (PsiElement) obj;
        }
    }

    public void setAccessClass(PsiClass psiClass) {
        this.myAccessClass = psiClass;
    }

    public boolean isConstructor() {
        return this.myIsConstructor;
    }

    public PsiElement getCurrentFileContext() {
        return this.myCurrentFileContext;
    }

    public void setIsConstructor(boolean z) {
        this.myIsConstructor = z;
    }

    public void forceAddResult(PsiMethod psiMethod) {
        if (psiMethod == null) {
            $$$reportNull$$$0(7);
        }
        add(new CandidateInfo(psiMethod, PsiSubstitutor.EMPTY, false, false, this.myCurrentFileContext));
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // org.jetbrains.kotlin.com.intellij.psi.scope.processor.ConflictFilterProcessor, org.jetbrains.kotlin.com.intellij.psi.scope.processor.FilterScopeProcessor, org.jetbrains.kotlin.com.intellij.psi.scope.PsiScopeProcessor
    public <T> T getHint(Key<T> key) {
        if (key == null) {
            $$$reportNull$$$0(8);
        }
        return key == ElementClassHint.KEY ? this : (T) super.getHint(key);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.scope.ElementClassHint
    public boolean shouldProcess(ElementClassHint.DeclarationKind declarationKind) {
        if (declarationKind == null) {
            $$$reportNull$$$0(9);
        }
        return declarationKind == ElementClassHint.DeclarationKind.METHOD;
    }
}
