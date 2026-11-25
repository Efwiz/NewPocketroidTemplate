package org.jetbrains.kotlin.com.intellij.psi.impl.source.resolve;

import java.util.function.Predicate;
import java.util.stream.Stream;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.com.intellij.openapi.diagnostic.Logger;
import org.jetbrains.kotlin.com.intellij.openapi.project.Project;
import org.jetbrains.kotlin.com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.kotlin.com.intellij.pom.java.LanguageLevel;
import org.jetbrains.kotlin.com.intellij.psi.JavaModuleSystem;
import org.jetbrains.kotlin.com.intellij.psi.JavaPsiFacade;
import org.jetbrains.kotlin.com.intellij.psi.JavaResolveResult;
import org.jetbrains.kotlin.com.intellij.psi.PsiAnonymousClass;
import org.jetbrains.kotlin.com.intellij.psi.PsiCallExpression;
import org.jetbrains.kotlin.com.intellij.psi.PsiClass;
import org.jetbrains.kotlin.com.intellij.psi.PsiClassType;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiExpression;
import org.jetbrains.kotlin.com.intellij.psi.PsiExpressionList;
import org.jetbrains.kotlin.com.intellij.psi.PsiFile;
import org.jetbrains.kotlin.com.intellij.psi.PsiInferenceHelper;
import org.jetbrains.kotlin.com.intellij.psi.PsiJavaCodeReferenceElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiManager;
import org.jetbrains.kotlin.com.intellij.psi.PsiMember;
import org.jetbrains.kotlin.com.intellij.psi.PsiMethod;
import org.jetbrains.kotlin.com.intellij.psi.PsiMethodCallExpression;
import org.jetbrains.kotlin.com.intellij.psi.PsiModifierList;
import org.jetbrains.kotlin.com.intellij.psi.PsiNewExpression;
import org.jetbrains.kotlin.com.intellij.psi.PsiPackage;
import org.jetbrains.kotlin.com.intellij.psi.PsiParameter;
import org.jetbrains.kotlin.com.intellij.psi.PsiReferenceExpression;
import org.jetbrains.kotlin.com.intellij.psi.PsiResolveHelper;
import org.jetbrains.kotlin.com.intellij.psi.PsiSubstitutor;
import org.jetbrains.kotlin.com.intellij.psi.PsiType;
import org.jetbrains.kotlin.com.intellij.psi.PsiTypeParameter;
import org.jetbrains.kotlin.com.intellij.psi.PsiVariable;
import org.jetbrains.kotlin.com.intellij.psi.ResolveState;
import org.jetbrains.kotlin.com.intellij.psi.impl.JavaPsiImplementationHelper;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.resolve.graphInference.PsiGraphInferenceHelper;
import org.jetbrains.kotlin.com.intellij.psi.infos.CandidateInfo;
import org.jetbrains.kotlin.com.intellij.psi.infos.MethodCandidateInfo;
import org.jetbrains.kotlin.com.intellij.psi.scope.MethodProcessorSetupFailedException;
import org.jetbrains.kotlin.com.intellij.psi.scope.PsiConflictResolver;
import org.jetbrains.kotlin.com.intellij.psi.scope.processor.MethodCandidatesProcessor;
import org.jetbrains.kotlin.com.intellij.psi.scope.processor.MethodResolverProcessor;
import org.jetbrains.kotlin.com.intellij.psi.scope.util.PsiScopesUtil;
import org.jetbrains.kotlin.com.intellij.psi.util.PsiUtil;
import org.jetbrains.kotlin.com.intellij.util.IncorrectOperationException;
import org.jetbrains.kotlin.com.intellij.util.SmartList;

/* JADX INFO: loaded from: classes3.dex */
public class PsiResolveHelperImpl implements PsiResolveHelper {
    private static final Logger LOG = Logger.getInstance((Class<?>) PsiResolveHelperImpl.class);
    private final PsiManager myManager;

    /* JADX WARN: Code duplicated, block: B:72:0x00f5  */
    /* JADX WARN: Code duplicated, block: B:73:0x00f8  */
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = (i == 3 || i == 25 || i == 37 || i == 45 || i == 50 || i == 7 || i == 8 || i == 9 || i == 21 || i == 22 || i == 56 || i == 57) ? "@NotNull method %s.%s must not return null" : "Argument for @NotNull parameter '%s' of %s.%s must not be null";
        Object[] objArr = new Object[(i == 3 || i == 25 || i == 37 || i == 45 || i == 50 || i == 7 || i == 8 || i == 9 || i == 21 || i == 22 || i == 56 || i == 57) ? 2 : 3];
        switch (i) {
            case 1:
                objArr[0] = "manager";
                break;
            case 2:
            case 5:
                objArr[0] = "argumentList";
                break;
            case 3:
            case 7:
            case 8:
            case 9:
            case 21:
            case 22:
            case 25:
            case 37:
            case 45:
            case 50:
            case 56:
            case 57:
                objArr[0] = "org/jetbrains/kotlin/com/intellij/psi/impl/source/resolve/PsiResolveHelperImpl";
                break;
            case 4:
                objArr[0] = ModuleXmlParser.TYPE;
                break;
            case 6:
            case 15:
            case 17:
            case 19:
                objArr[0] = "place";
                break;
            case 10:
            case 11:
            case 12:
            case 13:
                objArr[0] = "referenceText";
                break;
            case 14:
            case 16:
                objArr[0] = "member";
                break;
            case 18:
                objArr[0] = "pkg";
                break;
            case 20:
                objArr[0] = "expr";
                break;
            case 23:
            case 24:
                objArr[0] = "call";
                break;
            case 26:
                objArr[0] = "typeParameter";
                break;
            case 27:
            case 31:
            case 51:
                objArr[0] = "partialSubstitutor";
                break;
            case 28:
            case 33:
            case 40:
                objArr[0] = "policy";
                break;
            case 29:
            case 35:
            case 43:
                objArr[0] = "parameters";
                break;
            case 30:
            case 36:
            case 44:
                objArr[0] = "arguments";
                break;
            case 32:
            case 39:
                objArr[0] = "parent";
                break;
            case 34:
            case 42:
            case 47:
            case 53:
                objArr[0] = "typeParameters";
                break;
            case 38:
                objArr[0] = "currentCandidate";
                break;
            case 41:
            case 46:
            case 52:
            case 58:
                objArr[0] = "languageLevel";
                break;
            case 48:
            case 54:
                objArr[0] = "leftTypes";
                break;
            case 49:
            case 55:
                objArr[0] = "rightTypes";
                break;
            default:
                objArr[0] = "project";
                break;
        }
        if (i == 3) {
            objArr[1] = "resolveConstructor";
        } else if (i == 25) {
            objArr[1] = "getReferencedMethodCandidates";
        } else if (i == 37 || i == 45 || i == 50) {
            objArr[1] = "inferTypeArguments";
        } else if (i == 7 || i == 8 || i == 9) {
            objArr[1] = "multiResolveConstructor";
        } else if (i == 21 || i == 22) {
            objArr[1] = "getReferencedMethodCandidates";
        } else if (i == 56) {
            objArr[1] = "inferTypeArguments";
        } else if (i != 57) {
            objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/impl/source/resolve/PsiResolveHelperImpl";
        } else {
            objArr[1] = "getEffectiveLanguageLevel";
        }
        switch (i) {
            case 2:
                objArr[2] = "resolveConstructor";
                break;
            case 3:
            case 7:
            case 8:
            case 9:
            case 21:
            case 22:
            case 25:
            case 37:
            case 45:
            case 50:
            case 56:
            case 57:
                break;
            case 4:
            case 5:
            case 6:
                objArr[2] = "multiResolveConstructor";
                break;
            case 10:
                objArr[2] = "resolveReferencedClass";
                break;
            case 11:
                objArr[2] = "resolveReferencedVariable";
                break;
            case 12:
                objArr[2] = "resolveAccessibleReferencedVariable";
                break;
            case 13:
                objArr[2] = "resolveVar";
                break;
            case 14:
            case 15:
            case 16:
            case 17:
            case 18:
            case 19:
                objArr[2] = "isAccessible";
                break;
            case 20:
            case 24:
                objArr[2] = "getReferencedMethodCandidates";
                break;
            case 23:
                objArr[2] = "hasOverloads";
                break;
            case 26:
            case 27:
            case 28:
            case 29:
            case 30:
                objArr[2] = "inferTypeForMethodTypeParameter";
                break;
            case 31:
            case 32:
            case 33:
            case 34:
            case 35:
            case 36:
            case 38:
            case 39:
            case 40:
            case 41:
            case 42:
            case 43:
            case 44:
            case 46:
            case 47:
            case 48:
            case 49:
            case 51:
            case 52:
            case 53:
            case 54:
            case 55:
                objArr[2] = "inferTypeArguments";
                break;
            case 58:
                objArr[2] = "getInferenceHelper";
                break;
            default:
                objArr[2] = "<init>";
                break;
        }
        String str2 = String.format(str, objArr);
        if (i != 3 && i != 25 && i != 37 && i != 45 && i != 50 && i != 7 && i != 8 && i != 9 && i != 21 && i != 22 && i != 56 && i != 57) {
            throw new IllegalArgumentException(str2);
        }
        throw new IllegalStateException(str2);
    }

    public PsiResolveHelperImpl(Project project) {
        if (project == null) {
            $$$reportNull$$$0(0);
        }
        this.myManager = PsiManager.getInstance(project);
    }

    @Deprecated
    public PsiResolveHelperImpl(PsiManager psiManager) {
        if (psiManager == null) {
            $$$reportNull$$$0(1);
        }
        this.myManager = psiManager;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiResolveHelper
    public JavaResolveResult resolveConstructor(PsiClassType psiClassType, PsiExpressionList psiExpressionList, PsiElement psiElement) {
        if (psiExpressionList == null) {
            $$$reportNull$$$0(2);
        }
        JavaResolveResult[] javaResolveResultArrMultiResolveConstructor = multiResolveConstructor(psiClassType, psiExpressionList, psiElement);
        JavaResolveResult javaResolveResult = javaResolveResultArrMultiResolveConstructor.length == 1 ? javaResolveResultArrMultiResolveConstructor[0] : JavaResolveResult.EMPTY;
        if (javaResolveResult == null) {
            $$$reportNull$$$0(3);
        }
        return javaResolveResult;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiResolveHelper
    public JavaResolveResult[] multiResolveConstructor(PsiClassType psiClassType, PsiExpressionList psiExpressionList, PsiElement psiElement) {
        MethodResolverProcessor methodResolverProcessor;
        if (psiClassType == null) {
            $$$reportNull$$$0(4);
        }
        if (psiExpressionList == null) {
            $$$reportNull$$$0(5);
        }
        if (psiElement == null) {
            $$$reportNull$$$0(6);
        }
        PsiClassType.ClassResolveResult classResolveResultResolveGenerics = psiClassType.resolveGenerics();
        PsiClass element = classResolveResultResolveGenerics.getElement();
        if (element == null) {
            JavaResolveResult[] javaResolveResultArr = JavaResolveResult.EMPTY_ARRAY;
            if (javaResolveResultArr == null) {
                $$$reportNull$$$0(7);
            }
            return javaResolveResultArr;
        }
        PsiSubstitutor substitutor = classResolveResultResolveGenerics.getSubstitutor();
        if (psiExpressionList.getLightParent() instanceof PsiAnonymousClass) {
            PsiAnonymousClass psiAnonymousClass = (PsiAnonymousClass) psiExpressionList.getLightParent();
            methodResolverProcessor = new MethodResolverProcessor(psiAnonymousClass, psiExpressionList, psiElement, psiElement.getContainingFile());
            element = psiAnonymousClass.getBaseClassType().resolve();
            if (element == null) {
                JavaResolveResult[] javaResolveResultArr2 = JavaResolveResult.EMPTY_ARRAY;
                if (javaResolveResultArr2 == null) {
                    $$$reportNull$$$0(8);
                }
                return javaResolveResultArr2;
            }
        } else {
            methodResolverProcessor = new MethodResolverProcessor(null, psiExpressionList, psiElement, psiElement.getContainingFile());
        }
        ResolveState resolveStatePut = ResolveState.initial().put(PsiSubstitutor.KEY, substitutor);
        PsiMethod[] constructors = element.getConstructors();
        int length = constructors.length;
        for (int i = 0; i < length && methodResolverProcessor.execute(constructors[i], resolveStatePut); i++) {
        }
        JavaResolveResult[] result = methodResolverProcessor.getResult();
        if (result == null) {
            $$$reportNull$$$0(9);
        }
        return result;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiResolveHelper
    public PsiClass resolveReferencedClass(String str, PsiElement psiElement) {
        if (str == null) {
            $$$reportNull$$$0(10);
        }
        try {
            PsiJavaCodeReferenceElement psiJavaCodeReferenceElementCreateReferenceFromText = JavaPsiFacade.getInstance(this.myManager.getProject()).getParserFacade().createReferenceFromText(str, psiElement);
            PsiFile containingFile = psiJavaCodeReferenceElementCreateReferenceFromText.getContainingFile();
            LOG.assertTrue(containingFile.isValid(), str);
            return ResolveClassUtil.resolveClass(psiJavaCodeReferenceElementCreateReferenceFromText, containingFile);
        } catch (IncorrectOperationException unused) {
            return null;
        }
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiResolveHelper
    public PsiVariable resolveReferencedVariable(String str, PsiElement psiElement) {
        if (str == null) {
            $$$reportNull$$$0(11);
        }
        return resolveVar(str, psiElement, null);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiResolveHelper
    public PsiVariable resolveAccessibleReferencedVariable(String str, PsiElement psiElement) {
        if (str == null) {
            $$$reportNull$$$0(12);
        }
        boolean[] zArr = new boolean[1];
        PsiVariable psiVariableResolveVar = resolveVar(str, psiElement, zArr);
        if (zArr[0]) {
            return null;
        }
        return psiVariableResolveVar;
    }

    private PsiVariable resolveVar(String str, PsiElement psiElement, boolean[] zArr) {
        if (str == null) {
            $$$reportNull$$$0(13);
        }
        try {
            return ResolveVariableUtil.resolveVariable(JavaPsiFacade.getInstance(this.myManager.getProject()).getParserFacade().createReferenceFromText(str, psiElement), zArr, null);
        } catch (IncorrectOperationException unused) {
            return null;
        }
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiResolveHelper
    public boolean isAccessible(PsiMember psiMember, PsiElement psiElement, PsiClass psiClass) {
        if (psiMember == null) {
            $$$reportNull$$$0(14);
        }
        if (psiElement == null) {
            $$$reportNull$$$0(15);
        }
        return isAccessible(psiMember, psiMember.getModifierList(), psiElement, psiClass, null);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiResolveHelper
    public boolean isAccessible(final PsiMember psiMember, PsiModifierList psiModifierList, final PsiElement psiElement, PsiClass psiClass, PsiElement psiElement2) {
        if (psiMember == null) {
            $$$reportNull$$$0(16);
        }
        if (psiElement == null) {
            $$$reportNull$$$0(17);
        }
        boolean zIsAccessible = JavaResolveUtil.isAccessible(psiMember, psiMember.getContainingClass(), psiModifierList, psiElement, psiClass, psiElement2);
        return (zIsAccessible && (psiMember instanceof PsiClass) && !(psiMember instanceof PsiTypeParameter)) ? isAccessible(new Predicate() { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.source.resolve.PsiResolveHelperImpl$$ExternalSyntheticLambda0
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((JavaModuleSystem) obj).isAccessible((PsiClass) psiMember, psiElement);
            }
        }) : zIsAccessible;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiResolveHelper
    public boolean isAccessible(final PsiPackage psiPackage, final PsiElement psiElement) {
        if (psiPackage == null) {
            $$$reportNull$$$0(18);
        }
        if (psiElement == null) {
            $$$reportNull$$$0(19);
        }
        return isAccessible(new Predicate() { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.source.resolve.PsiResolveHelperImpl$$ExternalSyntheticLambda1
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((JavaModuleSystem) obj).isAccessible(psiPackage.getQualifiedName(), null, psiElement);
            }
        });
    }

    private static boolean isAccessible(Predicate<? super JavaModuleSystem> predicate) {
        return Stream.of((Object[]) JavaModuleSystem.EP_NAME.getExtensions()).allMatch(predicate);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiResolveHelper
    public CandidateInfo[] getReferencedMethodCandidates(PsiCallExpression psiCallExpression, boolean z, final boolean z2) {
        if (psiCallExpression == null) {
            $$$reportNull$$$0(20);
        }
        MethodCandidatesProcessor methodCandidatesProcessor = new MethodCandidatesProcessor(psiCallExpression, psiCallExpression.getContainingFile()) { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.source.resolve.PsiResolveHelperImpl.1
            @Override // org.jetbrains.kotlin.com.intellij.psi.scope.processor.MethodCandidatesProcessor
            protected boolean acceptVarargs() {
                return z2;
            }
        };
        try {
            PsiScopesUtil.setupAndRunProcessor(methodCandidatesProcessor, psiCallExpression, z);
            CandidateInfo[] candidates = methodCandidatesProcessor.getCandidates();
            if (candidates == null) {
                $$$reportNull$$$0(22);
            }
            return candidates;
        } catch (MethodProcessorSetupFailedException unused) {
            CandidateInfo[] candidateInfoArr = CandidateInfo.EMPTY_ARRAY;
            if (candidateInfoArr == null) {
                $$$reportNull$$$0(21);
            }
            return candidateInfoArr;
        }
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiResolveHelper
    public boolean hasOverloads(PsiCallExpression psiCallExpression) {
        PsiJavaCodeReferenceElement classOrAnonymousClassReference;
        if (psiCallExpression == null) {
            $$$reportNull$$$0(23);
        }
        MethodCandidatesProcessor methodCandidatesProcessor = new MethodCandidatesProcessor(psiCallExpression, psiCallExpression.getContainingFile(), new PsiConflictResolver[0], new SmartList()) { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.source.resolve.PsiResolveHelperImpl.2
            @Override // org.jetbrains.kotlin.com.intellij.psi.scope.processor.MethodCandidatesProcessor
            protected boolean acceptVarargs() {
                return true;
            }
        };
        if (psiCallExpression instanceof PsiMethodCallExpression) {
            PsiReferenceExpression methodExpression = ((PsiMethodCallExpression) psiCallExpression).getMethodExpression();
            methodCandidatesProcessor.setIsConstructor(false);
            methodCandidatesProcessor.setName(methodExpression.getCustomReferenceName());
            PsiScopesUtil.resolveAndWalk(methodCandidatesProcessor, methodExpression, null);
        } else if ((psiCallExpression instanceof PsiNewExpression) && (classOrAnonymousClassReference = ((PsiNewExpression) psiCallExpression).getClassOrAnonymousClassReference()) != null) {
            methodCandidatesProcessor.setIsConstructor(true);
            methodCandidatesProcessor.setName(classOrAnonymousClassReference.getCustomReferenceName());
            PsiScopesUtil.resolveAndWalk(methodCandidatesProcessor, classOrAnonymousClassReference, null);
        }
        return methodCandidatesProcessor.getCandidates().length > 1;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiResolveHelper
    public CandidateInfo[] getReferencedMethodCandidates(PsiCallExpression psiCallExpression, boolean z) {
        if (psiCallExpression == null) {
            $$$reportNull$$$0(24);
        }
        CandidateInfo[] referencedMethodCandidates = getReferencedMethodCandidates(psiCallExpression, z, false);
        if (referencedMethodCandidates == null) {
            $$$reportNull$$$0(25);
        }
        return referencedMethodCandidates;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiResolveHelper
    public PsiType inferTypeForMethodTypeParameter(PsiTypeParameter psiTypeParameter, PsiParameter[] psiParameterArr, PsiExpression[] psiExpressionArr, PsiSubstitutor psiSubstitutor, PsiElement psiElement, ParameterTypeInferencePolicy parameterTypeInferencePolicy) {
        if (psiTypeParameter == null) {
            $$$reportNull$$$0(26);
        }
        if (psiSubstitutor == null) {
            $$$reportNull$$$0(27);
        }
        if (parameterTypeInferencePolicy == null) {
            $$$reportNull$$$0(28);
        }
        if (psiParameterArr == null) {
            $$$reportNull$$$0(29);
        }
        if (psiExpressionArr == null) {
            $$$reportNull$$$0(30);
        }
        return getInferenceHelper(PsiUtil.getLanguageLevel(psiElement != null ? psiElement : psiTypeParameter)).inferTypeForMethodTypeParameter(psiTypeParameter, psiParameterArr, psiExpressionArr, psiSubstitutor, psiElement, parameterTypeInferencePolicy);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiResolveHelper
    public PsiSubstitutor inferTypeArguments(PsiTypeParameter[] psiTypeParameterArr, PsiParameter[] psiParameterArr, PsiExpression[] psiExpressionArr, PsiSubstitutor psiSubstitutor, PsiElement psiElement, ParameterTypeInferencePolicy parameterTypeInferencePolicy) {
        if (psiSubstitutor == null) {
            $$$reportNull$$$0(31);
        }
        if (psiElement == null) {
            $$$reportNull$$$0(32);
        }
        if (parameterTypeInferencePolicy == null) {
            $$$reportNull$$$0(33);
        }
        if (psiTypeParameterArr == null) {
            $$$reportNull$$$0(34);
        }
        if (psiParameterArr == null) {
            $$$reportNull$$$0(35);
        }
        if (psiExpressionArr == null) {
            $$$reportNull$$$0(36);
        }
        PsiSubstitutor psiSubstitutorInferTypeArguments = getInferenceHelper(PsiUtil.getLanguageLevel(psiElement)).inferTypeArguments(psiTypeParameterArr, psiParameterArr, psiExpressionArr, null, psiSubstitutor, psiElement, parameterTypeInferencePolicy, PsiUtil.getLanguageLevel(psiElement));
        if (psiSubstitutorInferTypeArguments == null) {
            $$$reportNull$$$0(37);
        }
        return psiSubstitutorInferTypeArguments;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiResolveHelper
    public PsiSubstitutor inferTypeArguments(PsiTypeParameter[] psiTypeParameterArr, PsiParameter[] psiParameterArr, PsiExpression[] psiExpressionArr, MethodCandidateInfo methodCandidateInfo, PsiElement psiElement, ParameterTypeInferencePolicy parameterTypeInferencePolicy, LanguageLevel languageLevel) {
        if (methodCandidateInfo == null) {
            $$$reportNull$$$0(38);
        }
        if (psiElement == null) {
            $$$reportNull$$$0(39);
        }
        if (parameterTypeInferencePolicy == null) {
            $$$reportNull$$$0(40);
        }
        if (languageLevel == null) {
            $$$reportNull$$$0(41);
        }
        if (psiTypeParameterArr == null) {
            $$$reportNull$$$0(42);
        }
        if (psiParameterArr == null) {
            $$$reportNull$$$0(43);
        }
        if (psiExpressionArr == null) {
            $$$reportNull$$$0(44);
        }
        PsiSubstitutor psiSubstitutorInferTypeArguments = getInferenceHelper(languageLevel).inferTypeArguments(psiTypeParameterArr, psiParameterArr, psiExpressionArr, methodCandidateInfo, methodCandidateInfo.getSiteSubstitutor(), psiElement, parameterTypeInferencePolicy, languageLevel);
        if (psiSubstitutorInferTypeArguments == null) {
            $$$reportNull$$$0(45);
        }
        return psiSubstitutorInferTypeArguments;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiResolveHelper
    public PsiSubstitutor inferTypeArguments(PsiTypeParameter[] psiTypeParameterArr, PsiType[] psiTypeArr, PsiType[] psiTypeArr2, LanguageLevel languageLevel) {
        if (languageLevel == null) {
            $$$reportNull$$$0(46);
        }
        if (psiTypeParameterArr == null) {
            $$$reportNull$$$0(47);
        }
        if (psiTypeArr == null) {
            $$$reportNull$$$0(48);
        }
        if (psiTypeArr2 == null) {
            $$$reportNull$$$0(49);
        }
        PsiSubstitutor psiSubstitutorInferTypeArguments = inferTypeArguments(psiTypeParameterArr, psiTypeArr, psiTypeArr2, PsiSubstitutor.EMPTY, languageLevel);
        if (psiSubstitutorInferTypeArguments == null) {
            $$$reportNull$$$0(50);
        }
        return psiSubstitutorInferTypeArguments;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiResolveHelper
    public PsiSubstitutor inferTypeArguments(PsiTypeParameter[] psiTypeParameterArr, PsiType[] psiTypeArr, PsiType[] psiTypeArr2, PsiSubstitutor psiSubstitutor, LanguageLevel languageLevel) {
        if (psiSubstitutor == null) {
            $$$reportNull$$$0(51);
        }
        if (languageLevel == null) {
            $$$reportNull$$$0(52);
        }
        if (psiTypeParameterArr == null) {
            $$$reportNull$$$0(53);
        }
        if (psiTypeArr == null) {
            $$$reportNull$$$0(54);
        }
        if (psiTypeArr2 == null) {
            $$$reportNull$$$0(55);
        }
        PsiSubstitutor psiSubstitutorInferTypeArguments = getInferenceHelper(languageLevel).inferTypeArguments(psiTypeParameterArr, psiTypeArr, psiTypeArr2, psiSubstitutor, languageLevel);
        if (psiSubstitutorInferTypeArguments == null) {
            $$$reportNull$$$0(56);
        }
        return psiSubstitutorInferTypeArguments;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiResolveHelper
    public PsiType getSubstitutionForTypeParameter(PsiTypeParameter psiTypeParameter, PsiType psiType, PsiType psiType2, boolean z, LanguageLevel languageLevel) {
        return getInferenceHelper(languageLevel).getSubstitutionForTypeParameter(psiTypeParameter, psiType, psiType2, z, languageLevel);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiResolveHelper
    public LanguageLevel getEffectiveLanguageLevel(VirtualFile virtualFile) {
        LanguageLevel effectiveLanguageLevel = JavaPsiImplementationHelper.getInstance(this.myManager.getProject()).getEffectiveLanguageLevel(virtualFile);
        if (effectiveLanguageLevel == null) {
            $$$reportNull$$$0(57);
        }
        return effectiveLanguageLevel;
    }

    public PsiInferenceHelper getInferenceHelper(LanguageLevel languageLevel) {
        if (languageLevel == null) {
            $$$reportNull$$$0(58);
        }
        if (languageLevel.isAtLeast(LanguageLevel.JDK_1_8)) {
            return new PsiGraphInferenceHelper(this.myManager);
        }
        return new PsiOldInferenceHelper(this.myManager);
    }
}
