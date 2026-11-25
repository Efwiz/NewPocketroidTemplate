package org.jetbrains.kotlin.com.intellij.psi.impl.source.resolve;

import java.util.Iterator;
import java.util.List;
import org.eclipse.jdt.internal.compiler.impl.CompilerOptions;
import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;
import org.jetbrains.kotlin.com.intellij.openapi.diagnostic.Logger;
import org.jetbrains.kotlin.com.intellij.openapi.util.Comparing;
import org.jetbrains.kotlin.com.intellij.openapi.util.Key;
import org.jetbrains.kotlin.com.intellij.openapi.util.text.StringUtil;
import org.jetbrains.kotlin.com.intellij.psi.CommonClassNames;
import org.jetbrains.kotlin.com.intellij.psi.JavaCodeFragment;
import org.jetbrains.kotlin.com.intellij.psi.JavaPsiFacade;
import org.jetbrains.kotlin.com.intellij.psi.JavaResolveResult;
import org.jetbrains.kotlin.com.intellij.psi.PsiClass;
import org.jetbrains.kotlin.com.intellij.psi.PsiClassType;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiExpression;
import org.jetbrains.kotlin.com.intellij.psi.PsiFile;
import org.jetbrains.kotlin.com.intellij.psi.PsiImportStatementBase;
import org.jetbrains.kotlin.com.intellij.psi.PsiJavaCodeReferenceElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiJavaFile;
import org.jetbrains.kotlin.com.intellij.psi.PsiResolveHelper;
import org.jetbrains.kotlin.com.intellij.psi.PsiSubstitutor;
import org.jetbrains.kotlin.com.intellij.psi.PsiType;
import org.jetbrains.kotlin.com.intellij.psi.ResolveState;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.PsiAnonymousClassImpl;
import org.jetbrains.kotlin.com.intellij.psi.infos.ClassCandidateInfo;
import org.jetbrains.kotlin.com.intellij.psi.scope.ElementClassHint;
import org.jetbrains.kotlin.com.intellij.psi.scope.JavaScopeProcessorEvent;
import org.jetbrains.kotlin.com.intellij.psi.scope.NameHint;
import org.jetbrains.kotlin.com.intellij.psi.scope.PsiScopeProcessor;
import org.jetbrains.kotlin.com.intellij.psi.util.PsiTreeUtil;
import org.jetbrains.kotlin.com.intellij.util.SmartList;
import org.jetbrains.kotlin.js.translate.context.Namer;

/* JADX INFO: loaded from: classes3.dex */
public class ClassResolverProcessor implements ElementClassHint, NameHint, PsiScopeProcessor {
    private PsiClass myAccessClass;
    private List<ClassCandidateInfo> myCandidates;
    private final String myClassName;
    private final PsiFile myContainingFile;
    private PsiElement myCurrentFileContext;
    private boolean myHasAccessibleCandidate;
    private boolean myHasInaccessibleCandidate;
    private final PsiElement myPlace;
    private final PsiResolveHelper myResolveHelper;
    private JavaResolveResult[] myResult;
    private static final Logger LOG = Logger.getInstance((Class<?>) ClassResolverProcessor.class);
    private static final String[] DEFAULT_PACKAGES = {CommonClassNames.DEFAULT_PACKAGE};

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = (i == 3 || i == 4 || i == 5) ? "@NotNull method %s.%s must not return null" : "Argument for @NotNull parameter '%s' of %s.%s must not be null";
        Object[] objArr = new Object[(i == 3 || i == 4 || i == 5) ? 2 : 3];
        switch (i) {
            case 1:
                objArr[0] = "startPlace";
                break;
            case 2:
                objArr[0] = "containingFile";
                break;
            case 3:
            case 4:
            case 5:
                objArr[0] = "org/jetbrains/kotlin/com/intellij/psi/impl/source/resolve/ClassResolverProcessor";
                break;
            case 6:
            case 13:
                objArr[0] = "state";
                break;
            case 7:
                objArr[0] = Namer.METADATA_CLASS_KIND;
                break;
            case 8:
                objArr[0] = "event";
                break;
            case 9:
                objArr[0] = "aClass";
                break;
            case 10:
                objArr[0] = "fqName";
                break;
            case 11:
                objArr[0] = CompilerOptions.INFO;
                break;
            case 12:
                objArr[0] = CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD;
                break;
            case 14:
                objArr[0] = "hintKey";
                break;
            default:
                objArr[0] = "className";
                break;
        }
        if (i == 3 || i == 4 || i == 5) {
            objArr[1] = "getResult";
        } else {
            objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/impl/source/resolve/ClassResolverProcessor";
        }
        switch (i) {
            case 3:
            case 4:
            case 5:
                break;
            case 6:
                objArr[2] = "getName";
                break;
            case 7:
                objArr[2] = "shouldProcess";
                break;
            case 8:
                objArr[2] = "handleEvent";
                break;
            case 9:
            case 10:
            case 11:
                objArr[2] = "dominates";
                break;
            case 12:
            case 13:
                objArr[2] = "execute";
                break;
            case 14:
                objArr[2] = "getHint";
                break;
            default:
                objArr[2] = "<init>";
                break;
        }
        String str2 = String.format(str, objArr);
        if (i != 3 && i != 4 && i != 5) {
            throw new IllegalArgumentException(str2);
        }
        throw new IllegalStateException(str2);
    }

    public ClassResolverProcessor(String str, PsiElement psiElement, PsiFile psiFile) {
        if (str == null) {
            $$$reportNull$$$0(0);
        }
        if (psiElement == null) {
            $$$reportNull$$$0(1);
        }
        if (psiFile == null) {
            $$$reportNull$$$0(2);
        }
        this.myResult = JavaResolveResult.EMPTY_ARRAY;
        this.myClassName = str;
        this.myContainingFile = psiFile;
        if ((psiFile instanceof JavaCodeFragment) && ((JavaCodeFragment) psiFile).getVisibilityChecker() != null) {
            psiElement = null;
        }
        this.myPlace = psiElement;
        if (psiElement instanceof PsiJavaCodeReferenceElement) {
            PsiElement qualifier = ((PsiJavaCodeReferenceElement) psiElement).getQualifier();
            if (qualifier instanceof PsiExpression) {
                PsiType type = ((PsiExpression) qualifier).getType();
                if (type instanceof PsiClassType) {
                    this.myAccessClass = ((PsiClassType) type).resolve();
                }
            } else if (qualifier instanceof PsiJavaCodeReferenceElement) {
                LOG.assertTrue(qualifier.isValid());
                PsiElement psiElementResolve = ((PsiJavaCodeReferenceElement) qualifier).resolve();
                if (psiElementResolve instanceof PsiClass) {
                    this.myAccessClass = (PsiClass) psiElementResolve;
                }
            }
        }
        this.myResolveHelper = JavaPsiFacade.getInstance(psiFile.getProject()).getResolveHelper();
    }

    public JavaResolveResult[] getResult() {
        JavaResolveResult[] javaResolveResultArr = this.myResult;
        if (javaResolveResultArr != null) {
            if (javaResolveResultArr == null) {
                $$$reportNull$$$0(3);
            }
            return javaResolveResultArr;
        }
        List<ClassCandidateInfo> list = this.myCandidates;
        if (list == null) {
            JavaResolveResult[] javaResolveResultArr2 = JavaResolveResult.EMPTY_ARRAY;
            this.myResult = javaResolveResultArr2;
            if (javaResolveResultArr2 == null) {
                $$$reportNull$$$0(4);
            }
            return javaResolveResultArr2;
        }
        if (this.myHasAccessibleCandidate && this.myHasInaccessibleCandidate) {
            Iterator<ClassCandidateInfo> it = list.iterator();
            while (it.hasNext()) {
                if (!it.next().isAccessible()) {
                    it.remove();
                }
            }
            this.myHasInaccessibleCandidate = false;
        }
        JavaResolveResult[] javaResolveResultArr3 = (JavaResolveResult[]) this.myCandidates.toArray(JavaResolveResult.EMPTY_ARRAY);
        this.myResult = javaResolveResultArr3;
        if (javaResolveResultArr3 == null) {
            $$$reportNull$$$0(5);
        }
        return javaResolveResultArr3;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.scope.NameHint
    public String getName(ResolveState resolveState) {
        if (resolveState == null) {
            $$$reportNull$$$0(6);
        }
        return this.myClassName;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.scope.ElementClassHint
    public boolean shouldProcess(ElementClassHint.DeclarationKind declarationKind) {
        if (declarationKind == null) {
            $$$reportNull$$$0(7);
        }
        return declarationKind == ElementClassHint.DeclarationKind.CLASS;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.scope.PsiScopeProcessor
    public void handleEvent(PsiScopeProcessor.Event event, Object obj) {
        if (event == null) {
            $$$reportNull$$$0(8);
        }
        if (event == JavaScopeProcessorEvent.SET_CURRENT_FILE_CONTEXT) {
            this.myCurrentFileContext = (PsiElement) obj;
        }
    }

    private static boolean isImported(PsiElement psiElement) {
        return psiElement instanceof PsiImportStatementBase;
    }

    private boolean isOnDemand(PsiElement psiElement, PsiClass psiClass) {
        if (isImported(psiElement)) {
            return ((PsiImportStatementBase) psiElement).isOnDemand();
        }
        String qualifiedName = psiClass.getQualifiedName();
        if (qualifiedName == null) {
            return false;
        }
        PsiFile contextFile = this.myPlace == null ? null : FileContextUtil.getContextFile(this.myContainingFile);
        boolean z = contextFile instanceof PsiJavaFile;
        String[] implicitlyImportedPackages = z ? ((PsiJavaFile) contextFile).getImplicitlyImportedPackages() : DEFAULT_PACKAGES;
        String packageName = StringUtil.getPackageName(qualifiedName);
        for (String str : implicitlyImportedPackages) {
            if (str.equals(packageName)) {
                return true;
            }
        }
        return z && ((PsiJavaFile) contextFile).getPackageName().equals(packageName);
    }

    private Domination dominates(PsiClass psiClass, boolean z, String str, ClassCandidateInfo classCandidateInfo) {
        if (psiClass == null) {
            $$$reportNull$$$0(9);
        }
        if (str == null) {
            $$$reportNull$$$0(10);
        }
        if (classCandidateInfo == null) {
            $$$reportNull$$$0(11);
        }
        PsiClass element = classCandidateInfo.getElement();
        String qualifiedName = element.getQualifiedName();
        if (str.equals(qualifiedName)) {
            return Domination.DOMINATED_BY;
        }
        PsiClass psiClassMo6092getContainingClass = psiClass.mo6092getContainingClass();
        PsiClass psiClassMo6092getContainingClass2 = element.mo6092getContainingClass();
        if (this.myAccessClass != null && !Comparing.equal(psiClassMo6092getContainingClass, psiClassMo6092getContainingClass2)) {
            if (this.myAccessClass.equals(psiClassMo6092getContainingClass)) {
                return Domination.DOMINATES;
            }
            if (this.myAccessClass.equals(psiClassMo6092getContainingClass2)) {
                return Domination.DOMINATED_BY;
            }
        }
        if (psiClassMo6092getContainingClass != null && psiClassMo6092getContainingClass2 != null && psiClassMo6092getContainingClass2.isInheritor(psiClassMo6092getContainingClass, true) && !isImported(this.myCurrentFileContext) && !isAmbiguousInherited(psiClassMo6092getContainingClass)) {
            return Domination.DOMINATED_BY;
        }
        boolean z2 = classCandidateInfo.isAccessible() && isAccessible(element);
        if (z2 && !z) {
            return Domination.DOMINATED_BY;
        }
        if (!z2 && z) {
            return Domination.DOMINATES;
        }
        boolean zIsEmpty = StringUtil.getPackageName(str).isEmpty();
        boolean z3 = qualifiedName != null && StringUtil.getPackageName(qualifiedName).isEmpty();
        if (zIsEmpty && !z3) {
            return Domination.DOMINATED_BY;
        }
        if (!zIsEmpty && z3) {
            return Domination.DOMINATES;
        }
        boolean zIsOnDemand = isOnDemand(this.myCurrentFileContext, psiClass);
        boolean zIsOnDemand2 = isOnDemand(classCandidateInfo.getCurrentFileResolveScope(), element);
        if (zIsOnDemand && !zIsOnDemand2) {
            return Domination.DOMINATED_BY;
        }
        if (!zIsOnDemand && zIsOnDemand2) {
            return Domination.DOMINATES;
        }
        return Domination.EQUAL;
    }

    private boolean isAccessible(PsiClass psiClass) {
        if (!psiClass.hasModifierProperty("private")) {
            return true;
        }
        PsiClass psiClassMo6092getContainingClass = psiClass.mo6092getContainingClass();
        for (PsiClass psiClass2 = (PsiClass) PsiTreeUtil.getContextOfType(this.myPlace, PsiClass.class, false); psiClass2 != null; psiClass2 = (PsiClass) PsiTreeUtil.getContextOfType(psiClass2, PsiClass.class)) {
            if (psiClassMo6092getContainingClass == psiClass2) {
                return true;
            }
        }
        return false;
    }

    private boolean isAmbiguousInherited(PsiClass psiClass) {
        PsiClass psiClassMo6092getContainingClass = (PsiClass) PsiTreeUtil.getContextOfType(this.myPlace, PsiClass.class);
        if ((psiClassMo6092getContainingClass instanceof PsiAnonymousClassImpl) && ((PsiAnonymousClassImpl) psiClassMo6092getContainingClass).isBaseClassReference(this.myPlace)) {
            psiClassMo6092getContainingClass = (PsiClass) PsiTreeUtil.getContextOfType(psiClassMo6092getContainingClass, PsiClass.class);
        }
        while (psiClassMo6092getContainingClass != null) {
            if (psiClassMo6092getContainingClass.isInheritor(psiClass, false)) {
                return true;
            }
            psiClassMo6092getContainingClass = psiClassMo6092getContainingClass.mo6092getContainingClass();
        }
        return false;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.scope.PsiScopeProcessor
    public boolean execute(PsiElement psiElement, ResolveState resolveState) {
        PsiClass psiClass;
        if (psiElement == null) {
            $$$reportNull$$$0(12);
        }
        if (resolveState == null) {
            $$$reportNull$$$0(13);
        }
        if (!(psiElement instanceof PsiClass)) {
            return true;
        }
        PsiClass psiClass2 = (PsiClass) psiElement;
        if (!this.myClassName.equals(psiClass2.getMyName())) {
            return true;
        }
        boolean z = this.myPlace == null || checkAccessibility(psiClass2);
        if (this.myCandidates == null) {
            this.myCandidates = new SmartList();
        } else {
            String qualifiedName = psiClass2.getQualifiedName();
            if (qualifiedName != null) {
                for (int size = this.myCandidates.size() - 1; size >= 0; size--) {
                    Domination dominationDominates = dominates(psiClass2, z && isAccessible(psiClass2), qualifiedName, this.myCandidates.get(size));
                    if (dominationDominates == Domination.DOMINATED_BY) {
                        return true;
                    }
                    if (dominationDominates == Domination.DOMINATES) {
                        this.myCandidates.remove(size);
                    }
                }
            }
        }
        this.myHasAccessibleCandidate |= z;
        this.myHasInaccessibleCandidate |= !z;
        this.myCandidates.add(new ClassCandidateInfo(psiClass2, (PsiSubstitutor) resolveState.get(PsiSubstitutor.KEY), !z, this.myCurrentFileContext));
        this.myResult = null;
        if (!z) {
            return true;
        }
        if (psiClass2.hasModifierProperty("private") && (psiClass = (PsiClass) PsiTreeUtil.getContextOfType(this.myPlace, PsiClass.class, false)) != null && !PsiTreeUtil.isAncestor(psiClass, psiClass2, false)) {
            return true;
        }
        PsiElement psiElement2 = this.myCurrentFileContext;
        if (psiElement2 instanceof PsiImportStatementBase) {
            return ((PsiImportStatementBase) psiElement2).isOnDemand();
        }
        return false;
    }

    private boolean checkAccessibility(PsiClass psiClass) {
        return this.myResolveHelper.isAccessible(psiClass, this.myPlace, this.myAccessClass);
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // org.jetbrains.kotlin.com.intellij.psi.scope.PsiScopeProcessor
    public <T> T getHint(Key<T> key) {
        if (key == null) {
            $$$reportNull$$$0(14);
        }
        if (key == ElementClassHint.KEY || key == NameHint.KEY) {
            return this;
        }
        return null;
    }
}
