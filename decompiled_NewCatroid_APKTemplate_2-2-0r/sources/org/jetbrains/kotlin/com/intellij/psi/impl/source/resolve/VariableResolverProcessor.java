package org.jetbrains.kotlin.com.intellij.psi.impl.source.resolve;

import com.google.android.gms.analytics.ecommerce.ProductAction;
import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;
import org.jetbrains.kotlin.com.intellij.openapi.util.Key;
import org.jetbrains.kotlin.com.intellij.psi.JavaCodeFragment;
import org.jetbrains.kotlin.com.intellij.psi.JavaPsiFacade;
import org.jetbrains.kotlin.com.intellij.psi.JavaResolveResult;
import org.jetbrains.kotlin.com.intellij.psi.PsiArrayType;
import org.jetbrains.kotlin.com.intellij.psi.PsiClass;
import org.jetbrains.kotlin.com.intellij.psi.PsiClassType;
import org.jetbrains.kotlin.com.intellij.psi.PsiDeclarationStatement;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiElementFactory;
import org.jetbrains.kotlin.com.intellij.psi.PsiExpression;
import org.jetbrains.kotlin.com.intellij.psi.PsiField;
import org.jetbrains.kotlin.com.intellij.psi.PsiFile;
import org.jetbrains.kotlin.com.intellij.psi.PsiJavaCodeReferenceElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiMember;
import org.jetbrains.kotlin.com.intellij.psi.PsiModifierListOwner;
import org.jetbrains.kotlin.com.intellij.psi.PsiSubstitutor;
import org.jetbrains.kotlin.com.intellij.psi.PsiType;
import org.jetbrains.kotlin.com.intellij.psi.PsiTypeParameter;
import org.jetbrains.kotlin.com.intellij.psi.PsiVariable;
import org.jetbrains.kotlin.com.intellij.psi.ResolveState;
import org.jetbrains.kotlin.com.intellij.psi.filters.ElementFilter;
import org.jetbrains.kotlin.com.intellij.psi.infos.CandidateInfo;
import org.jetbrains.kotlin.com.intellij.psi.scope.ElementClassFilter;
import org.jetbrains.kotlin.com.intellij.psi.scope.ElementClassHint;
import org.jetbrains.kotlin.com.intellij.psi.scope.JavaScopeProcessorEvent;
import org.jetbrains.kotlin.com.intellij.psi.scope.PsiConflictResolver;
import org.jetbrains.kotlin.com.intellij.psi.scope.PsiScopeProcessor;
import org.jetbrains.kotlin.com.intellij.psi.scope.conflictResolvers.JavaVariableConflictResolver;
import org.jetbrains.kotlin.com.intellij.psi.scope.processor.ConflictFilterProcessor;
import org.jetbrains.kotlin.com.intellij.psi.util.PsiUtil;
import org.jetbrains.kotlin.com.intellij.util.SmartList;
import org.jetbrains.kotlin.js.translate.context.Namer;

/* JADX INFO: loaded from: classes3.dex */
public class VariableResolverProcessor extends ConflictFilterProcessor implements ElementClassHint {
    private static final ElementFilter ourFilter = ElementClassFilter.VARIABLE;
    private final PsiClass myAccessClass;
    private PsiElement myCurrentFileContext;
    private StaticScope myStaticScopeFlag;

    private enum StaticScope {
        NON_STATIC,
        STATIC,
        STATIC_NO_CONSTANTS
    }

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        Object[] objArr = new Object[3];
        switch (i) {
            case 1:
                objArr[0] = "placeFile";
                break;
            case 2:
                objArr[0] = "cachedResult";
                break;
            case 3:
                objArr[0] = "event";
                break;
            case 4:
            case 6:
            case 8:
                objArr[0] = CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD;
                break;
            case 5:
                objArr[0] = "substitutor";
                break;
            case 7:
                objArr[0] = Namer.METADATA_CLASS_KIND;
                break;
            case 9:
                objArr[0] = "state";
                break;
            case 10:
                objArr[0] = "hintKey";
                break;
            default:
                objArr[0] = "place";
                break;
        }
        objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/impl/source/resolve/VariableResolverProcessor";
        switch (i) {
            case 2:
                objArr[2] = "stopAtFoundResult";
                break;
            case 3:
                objArr[2] = "handleEvent";
                break;
            case 4:
            case 5:
                objArr[2] = ProductAction.ACTION_ADD;
                break;
            case 6:
                objArr[2] = "isStaticProblem";
                break;
            case 7:
                objArr[2] = "shouldProcess";
                break;
            case 8:
            case 9:
                objArr[2] = "execute";
                break;
            case 10:
                objArr[2] = "getHint";
                break;
            default:
                objArr[2] = "<init>";
                break;
        }
        throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public VariableResolverProcessor(PsiJavaCodeReferenceElement psiJavaCodeReferenceElement, PsiFile psiFile) {
        PsiClass psiClassResolve;
        super(psiJavaCodeReferenceElement.getReferenceName(), ourFilter, new PsiConflictResolver[]{new JavaVariableConflictResolver()}, new SmartList(), psiJavaCodeReferenceElement, psiFile);
        if (psiJavaCodeReferenceElement == null) {
            $$$reportNull$$$0(0);
        }
        if (psiFile == null) {
            $$$reportNull$$$0(1);
        }
        this.myStaticScopeFlag = StaticScope.NON_STATIC;
        PsiElement qualifier = psiJavaCodeReferenceElement.getQualifier();
        PsiClass psiClass = null;
        if (qualifier instanceof PsiExpression) {
            JavaResolveResult accessObjectClass = PsiUtil.getAccessObjectClass((PsiExpression) qualifier);
            PsiElement element = accessObjectClass.getElement();
            if (element instanceof PsiTypeParameter) {
                PsiElementFactory elementFactory = JavaPsiFacade.getElementFactory(psiFile.getProject());
                PsiType psiTypeSubstitute = accessObjectClass.getSubstitutor().substitute(elementFactory.createType((PsiTypeParameter) element));
                if (psiTypeSubstitute instanceof PsiArrayType) {
                    psiClassResolve = elementFactory.getArrayClass(PsiUtil.getLanguageLevel(psiFile));
                } else {
                    psiClassResolve = psiTypeSubstitute instanceof PsiClassType ? ((PsiClassType) psiTypeSubstitute).resolve() : psiClassResolve;
                }
                psiClass = psiClassResolve;
            } else if (element instanceof PsiClass) {
                psiClass = (PsiClass) element;
            }
        }
        this.myAccessClass = psiClass;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.scope.processor.ConflictFilterProcessor
    protected boolean stopAtFoundResult(JavaResolveResult javaResolveResult) {
        if (javaResolveResult == null) {
            $$$reportNull$$$0(2);
        }
        if (!super.stopAtFoundResult(javaResolveResult)) {
            return false;
        }
        if (!(this.myPlaceFile instanceof JavaCodeFragment) || ((JavaCodeFragment) this.myPlaceFile).getVisibilityChecker() == null) {
            return true;
        }
        PsiElement element = javaResolveResult.getElement();
        if (!(element instanceof PsiMember)) {
            return true;
        }
        PsiMember psiMember = (PsiMember) element;
        return JavaResolveUtil.isAccessible(psiMember, psiMember.getContainingClass(), psiMember.mo6095getModifierList(), this.myPlace, this.myAccessClass, javaResolveResult.getCurrentFileResolveScope(), null);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.scope.processor.ConflictFilterProcessor, org.jetbrains.kotlin.com.intellij.psi.scope.processor.FilterScopeProcessor, org.jetbrains.kotlin.com.intellij.psi.scope.PsiScopeProcessor
    public final void handleEvent(PsiScopeProcessor.Event event, Object obj) {
        if (event == null) {
            $$$reportNull$$$0(3);
        }
        super.handleEvent(event, obj);
        if (JavaScopeProcessorEvent.isEnteringStaticScope(event, obj)) {
            if (this.myStaticScopeFlag != StaticScope.STATIC_NO_CONSTANTS) {
                this.myStaticScopeFlag = ((obj instanceof PsiClass) && (((PsiClass) obj).getLightOwner() instanceof PsiDeclarationStatement)) ? StaticScope.STATIC_NO_CONSTANTS : StaticScope.STATIC;
            }
        } else if (JavaScopeProcessorEvent.SET_CURRENT_FILE_CONTEXT.equals(event)) {
            this.myCurrentFileContext = (PsiElement) obj;
        }
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.scope.processor.ConflictFilterProcessor, org.jetbrains.kotlin.com.intellij.psi.scope.processor.FilterScopeProcessor
    public void add(PsiElement psiElement, PsiSubstitutor psiSubstitutor) {
        if (psiElement == null) {
            $$$reportNull$$$0(4);
        }
        if (psiSubstitutor == null) {
            $$$reportNull$$$0(5);
        }
        add(new CandidateInfo(psiElement, psiSubstitutor, this.myPlace, this.myAccessClass, isStaticProblem(psiElement), this.myCurrentFileContext));
    }

    private boolean isStaticProblem(PsiElement psiElement) {
        if (psiElement == null) {
            $$$reportNull$$$0(6);
        }
        if (this.myStaticScopeFlag == StaticScope.NON_STATIC || ((PsiModifierListOwner) psiElement).hasModifierProperty("static")) {
            return false;
        }
        if (psiElement instanceof PsiField) {
            return true;
        }
        return (this.myStaticScopeFlag == StaticScope.STATIC && (psiElement instanceof PsiVariable) && PsiUtil.isCompileTimeConstant((PsiVariable) psiElement)) ? false : true;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.scope.ElementClassHint
    public boolean shouldProcess(ElementClassHint.DeclarationKind declarationKind) {
        if (declarationKind == null) {
            $$$reportNull$$$0(7);
        }
        return declarationKind == ElementClassHint.DeclarationKind.VARIABLE || declarationKind == ElementClassHint.DeclarationKind.FIELD || declarationKind == ElementClassHint.DeclarationKind.ENUM_CONST;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.scope.processor.ConflictFilterProcessor, org.jetbrains.kotlin.com.intellij.psi.scope.processor.FilterScopeProcessor, org.jetbrains.kotlin.com.intellij.psi.scope.PsiScopeProcessor
    public boolean execute(PsiElement psiElement, ResolveState resolveState) {
        if (psiElement == null) {
            $$$reportNull$$$0(8);
        }
        if (resolveState == null) {
            $$$reportNull$$$0(9);
        }
        if (!(psiElement instanceof PsiField) && (this.myName == null || PsiUtil.checkName(psiElement, this.myName, this.myPlace))) {
            super.execute(psiElement, resolveState);
            return this.myResults.isEmpty();
        }
        return super.execute(psiElement, resolveState);
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // org.jetbrains.kotlin.com.intellij.psi.scope.processor.ConflictFilterProcessor, org.jetbrains.kotlin.com.intellij.psi.scope.processor.FilterScopeProcessor, org.jetbrains.kotlin.com.intellij.psi.scope.PsiScopeProcessor
    public <T> T getHint(Key<T> key) {
        if (key == null) {
            $$$reportNull$$$0(10);
        }
        return key == ElementClassHint.KEY ? this : (T) super.getHint(key);
    }
}
