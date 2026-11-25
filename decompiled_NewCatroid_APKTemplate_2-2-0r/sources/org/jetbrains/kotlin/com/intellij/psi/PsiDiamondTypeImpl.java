package org.jetbrains.kotlin.com.intellij.psi;

import com.huawei.hms.framework.common.ContainerUtils;
import com.huawei.hms.ml.language.common.utils.Constant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.com.intellij.lang.java.JavaLanguage;
import org.jetbrains.kotlin.com.intellij.openapi.diagnostic.Logger;
import org.jetbrains.kotlin.com.intellij.openapi.util.Comparing;
import org.jetbrains.kotlin.com.intellij.openapi.util.Computable;
import org.jetbrains.kotlin.com.intellij.openapi.util.text.StringUtil;
import org.jetbrains.kotlin.com.intellij.psi.codeStyle.JavaCodeStyleManager;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.java.PsiMethodCallExpressionImpl;
import org.jetbrains.kotlin.com.intellij.psi.infos.CandidateInfo;
import org.jetbrains.kotlin.com.intellij.psi.infos.MethodCandidateInfo;
import org.jetbrains.kotlin.com.intellij.psi.scope.PsiConflictResolver;
import org.jetbrains.kotlin.com.intellij.psi.scope.conflictResolvers.JavaMethodsConflictResolver;
import org.jetbrains.kotlin.com.intellij.psi.scope.processor.MethodCandidatesProcessor;
import org.jetbrains.kotlin.com.intellij.psi.search.GlobalSearchScope;
import org.jetbrains.kotlin.com.intellij.psi.util.CachedValueProvider;
import org.jetbrains.kotlin.com.intellij.psi.util.CachedValuesManager;
import org.jetbrains.kotlin.com.intellij.psi.util.PsiModificationTracker;
import org.jetbrains.kotlin.com.intellij.psi.util.PsiTreeUtil;
import org.jetbrains.kotlin.com.intellij.psi.util.PsiUtil;
import org.jetbrains.kotlin.com.intellij.psi.util.TypeConversionUtil;
import org.jetbrains.kotlin.com.intellij.util.Function;
import org.jetbrains.kotlin.com.intellij.util.IncorrectOperationException;
import org.jetbrains.kotlin.com.intellij.util.VisibilityUtil;
import org.jetbrains.kotlin.com.intellij.util.text.UniqueNameGenerator;

/* JADX INFO: loaded from: classes3.dex */
public class PsiDiamondTypeImpl extends PsiDiamondType {
    private static final Logger LOG = Logger.getInstance((Class<?>) PsiDiamondTypeImpl.class);
    private final PsiManager myManager;
    private final PsiTypeElement myTypeElement;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = (i == 2 || i == 3 || i == 5) ? "@NotNull method %s.%s must not return null" : "Argument for @NotNull parameter '%s' of %s.%s must not be null";
        Object[] objArr = new Object[(i == 2 || i == 3 || i == 5) ? 2 : 3];
        switch (i) {
            case 1:
                objArr[0] = "visitor";
                break;
            case 2:
            case 3:
            case 5:
                objArr[0] = "org/jetbrains/kotlin/com/intellij/psi/PsiDiamondTypeImpl";
                break;
            case 4:
                objArr[0] = "newExpression";
                break;
            case 6:
                objArr[0] = "staticFactoryMethod";
                break;
            case 7:
            case 8:
                objArr[0] = "psiClass";
                break;
            default:
                objArr[0] = "text";
                break;
        }
        if (i == 2) {
            objArr[1] = "getResolveScope";
        } else if (i == 3) {
            objArr[1] = "getSuperTypes";
        } else if (i != 5) {
            objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/PsiDiamondTypeImpl";
        } else {
            objArr[1] = "getAllTypeParams";
        }
        switch (i) {
            case 1:
                objArr[2] = "accept";
                break;
            case 2:
            case 3:
            case 5:
                break;
            case 4:
                objArr[2] = "getStaticFactoryCandidateInfo";
                break;
            case 6:
                objArr[2] = "createMethodCandidate";
                break;
            case 7:
                objArr[2] = "hasDefaultConstructor";
                break;
            case 8:
                objArr[2] = "haveConstructorsGenericsParameters";
                break;
            default:
                objArr[2] = "equalsToText";
                break;
        }
        String str2 = String.format(str, objArr);
        if (i != 2 && i != 3 && i != 5) {
            throw new IllegalArgumentException(str2);
        }
        throw new IllegalStateException(str2);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiType
    public boolean isValid() {
        return false;
    }

    public PsiDiamondTypeImpl(PsiManager psiManager, PsiTypeElement psiTypeElement) {
        this.myManager = psiManager;
        this.myTypeElement = psiTypeElement;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiType
    public String getPresentableText() {
        return "";
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiType
    public String getCanonicalText() {
        return "";
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiType
    public String getInternalCanonicalText() {
        return "Diamond Type";
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiType
    public boolean equalsToText(String str) {
        if (str == null) {
            $$$reportNull$$$0(0);
        }
        return str.isEmpty();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiType
    public <A> A accept(PsiTypeVisitor<A> psiTypeVisitor) {
        if (psiTypeVisitor == null) {
            $$$reportNull$$$0(1);
        }
        return psiTypeVisitor.visitDiamondType(this);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiType
    public GlobalSearchScope getResolveScope() {
        GlobalSearchScope globalSearchScopeAllScope = GlobalSearchScope.allScope(this.myManager.getProject());
        if (globalSearchScopeAllScope == null) {
            $$$reportNull$$$0(2);
        }
        return globalSearchScopeAllScope;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiType
    public PsiType[] getSuperTypes() {
        return new PsiType[]{getJavaLangObject(this.myManager, getResolveScope())};
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiDiamondType
    public PsiDiamondType.DiamondInferenceResult resolveInferredTypes() {
        PsiNewExpression newExpression = getNewExpression();
        if (newExpression == null) {
            return PsiDiamondType.DiamondInferenceResult.NULL_RESULT;
        }
        return resolveInferredTypes(newExpression);
    }

    private PsiNewExpression getNewExpression() {
        return (PsiNewExpression) PsiTreeUtil.getParentOfType(this.myTypeElement.getLightParent(), PsiNewExpression.class, true, (Class<? extends PsiElement>[]) new Class[]{PsiTypeElement.class});
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiDiamondType
    public JavaResolveResult getStaticFactory() {
        PsiNewExpression newExpression = getNewExpression();
        if (newExpression != null) {
            return getStaticFactory(newExpression, newExpression);
        }
        return null;
    }

    public static PsiDiamondType.DiamondInferenceResult resolveInferredTypes(PsiNewExpression psiNewExpression) {
        return resolveInferredTypes(psiNewExpression, psiNewExpression);
    }

    public static PsiDiamondType.DiamondInferenceResult resolveInferredTypes(PsiNewExpression psiNewExpression, PsiElement psiElement) {
        PsiAnonymousClass anonymousClass = psiNewExpression.getAnonymousClass();
        if (anonymousClass != null && !PsiUtil.isLanguageLevel9OrHigher(psiNewExpression) && (anonymousClass.getBaseClassReference().resolve() instanceof PsiClass)) {
            return PsiDiamondType.DiamondInferenceResult.ANONYMOUS_INNER_RESULT;
        }
        PsiReferenceParameterList psiReferenceParameterList = (PsiReferenceParameterList) PsiTreeUtil.getChildOfType(psiNewExpression, PsiReferenceParameterList.class);
        if (psiReferenceParameterList != null && psiReferenceParameterList.getTypeParameterElements().length > 0) {
            return PsiDiamondType.DiamondInferenceResult.EXPLICIT_CONSTRUCTOR_TYPE_ARGS;
        }
        PsiDiamondType.DiamondInferenceResult diamondInferenceResultResolveInferredTypesNoCheck = resolveInferredTypesNoCheck(psiNewExpression, psiElement);
        if (anonymousClass != null && PsiUtil.isLanguageLevel9OrHigher(psiNewExpression)) {
            InferredAnonymousTypeVisitor inferredAnonymousTypeVisitor = new InferredAnonymousTypeVisitor(psiElement);
            Iterator<PsiType> it = diamondInferenceResultResolveInferredTypesNoCheck.getInferredTypes().iterator();
            while (it.hasNext()) {
                Boolean bool = (Boolean) it.next().accept(inferredAnonymousTypeVisitor);
                if (bool != null && !bool.booleanValue()) {
                    return PsiDiamondType.DiamondInferenceResult.ANONYMOUS_INNER_RESULT;
                }
            }
        }
        return diamondInferenceResultResolveInferredTypesNoCheck;
    }

    private static JavaResolveResult getStaticFactory(final PsiNewExpression psiNewExpression, PsiElement psiElement) {
        if (psiElement == psiNewExpression && !MethodCandidateInfo.isOverloadCheck(psiNewExpression.getArgumentList())) {
            return (JavaResolveResult) CachedValuesManager.getCachedValue((PsiElement) psiNewExpression, new CachedValueProvider() { // from class: org.jetbrains.kotlin.com.intellij.psi.PsiDiamondTypeImpl$$ExternalSyntheticLambda5
                @Override // org.jetbrains.kotlin.com.intellij.psi.util.CachedValueProvider
                public final CachedValueProvider.Result compute() {
                    return PsiDiamondTypeImpl.lambda$getStaticFactory$0(psiNewExpression);
                }
            });
        }
        return getStaticFactoryCandidateInfo(psiNewExpression, psiElement);
    }

    static /* synthetic */ CachedValueProvider.Result lambda$getStaticFactory$0(PsiNewExpression psiNewExpression) {
        return new CachedValueProvider.Result(getStaticFactoryCandidateInfo(psiNewExpression, psiNewExpression), PsiModificationTracker.MODIFICATION_COUNT);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static PsiDiamondType.DiamondInferenceResult resolveInferredTypesNoCheck(PsiNewExpression psiNewExpression, PsiElement psiElement) {
        JavaResolveResult staticFactory = getStaticFactory(psiNewExpression, psiElement);
        if (staticFactory == null) {
            return PsiDiamondType.DiamondInferenceResult.NULL_RESULT;
        }
        if (!(staticFactory instanceof MethodCandidateInfo)) {
            return PsiDiamondType.DiamondInferenceResult.UNRESOLVED_CONSTRUCTOR;
        }
        Logger logger = LOG;
        logger.assertTrue(!PsiMethodCallExpressionImpl.doWePerformGenericMethodOverloadResolutionNow(psiNewExpression, PsiUtil.getLanguageLevel(psiNewExpression)), "diamond evaluation during overload resolution");
        PsiSubstitutor substitutor = staticFactory.getSubstitutor();
        MethodCandidateInfo methodCandidateInfo = (MethodCandidateInfo) staticFactory;
        final String inferenceErrorMessageAssumeAlreadyComputed = methodCandidateInfo.getInferenceErrorMessageAssumeAlreadyComputed();
        if (inferenceErrorMessageAssumeAlreadyComputed == null && methodCandidateInfo.isErased()) {
            return PsiDiamondType.DiamondInferenceResult.RAW_RESULT;
        }
        PsiMethod element = methodCandidateInfo.getElement();
        PsiTypeParameter[] typeParameters = element.getTypeParameters();
        PsiClass psiClass = (PsiClass) PsiTreeUtil.getContextOfType(element.getContext(), PsiClass.class, false);
        if (psiClass == null) {
            logger.error("failed for expression:" + psiNewExpression);
            return PsiDiamondType.DiamondInferenceResult.NULL_RESULT;
        }
        PsiTypeParameter[] typeParameters2 = psiClass.getTypeParameters();
        PsiJavaCodeReferenceElement classOrAnonymousClassReference = psiNewExpression.getClassOrAnonymousClassReference();
        logger.assertTrue(classOrAnonymousClassReference != null);
        PsiDiamondType.DiamondInferenceResult diamondInferenceResult = new PsiDiamondType.DiamondInferenceResult(classOrAnonymousClassReference.getCustomReferenceName() + "<>") { // from class: org.jetbrains.kotlin.com.intellij.psi.PsiDiamondTypeImpl.1
            @Override // org.jetbrains.kotlin.com.intellij.psi.PsiDiamondType.DiamondInferenceResult
            public String getErrorMessage() {
                return inferenceErrorMessageAssumeAlreadyComputed != null ? PsiDiamondType.DiamondInferenceResult.NULL_RESULT.getErrorMessage() : super.getErrorMessage();
            }
        };
        if (inferenceErrorMessageAssumeAlreadyComputed == null && PsiUtil.isRawSubstitutor(element, substitutor)) {
            if (!PsiUtil.isLanguageLevel8OrHigher(psiNewExpression) && (PsiUtil.skipParenthesizedExprUp(psiNewExpression.getLightParent()) instanceof PsiExpressionList)) {
                for (PsiTypeParameter psiTypeParameter : typeParameters) {
                    diamondInferenceResult.addInferredType(PsiType.getJavaLangObject(psiNewExpression.getManager(), GlobalSearchScope.allScope(psiNewExpression.getProject())));
                }
            }
            return diamondInferenceResult;
        }
        for (PsiTypeParameter psiTypeParameter2 : typeParameters) {
            for (PsiTypeParameter psiTypeParameter3 : typeParameters2) {
                if (Comparing.strEqual(psiTypeParameter3.get$name(), psiTypeParameter2.get$name())) {
                    diamondInferenceResult.addInferredType(substitutor.substitute(psiTypeParameter2));
                    break;
                }
            }
        }
        return diamondInferenceResult;
    }

    private static JavaResolveResult getStaticFactoryCandidateInfo(final PsiNewExpression psiNewExpression, final PsiElement psiElement) {
        if (psiNewExpression == null) {
            $$$reportNull$$$0(4);
        }
        return (JavaResolveResult) ourDiamondGuard.doPreventingRecursion(psiElement, false, new Computable() { // from class: org.jetbrains.kotlin.com.intellij.psi.PsiDiamondTypeImpl$$ExternalSyntheticLambda1
            @Override // org.jetbrains.kotlin.com.intellij.openapi.util.Computable
            public final Object compute() {
                return PsiDiamondTypeImpl.lambda$getStaticFactoryCandidateInfo$1(psiNewExpression, psiElement);
            }
        });
    }

    static /* synthetic */ JavaResolveResult lambda$getStaticFactoryCandidateInfo$1(PsiNewExpression psiNewExpression, PsiElement psiElement) {
        PsiFile containingFile;
        PsiExpressionList argumentList = psiNewExpression.getArgumentList();
        if (argumentList == null || (containingFile = argumentList.getContainingFile()) == null) {
            return null;
        }
        JavaMethodsConflictResolver javaMethodsConflictResolver = new JavaMethodsConflictResolver(argumentList, null, PsiUtil.getLanguageLevel(containingFile), containingFile);
        List<CandidateInfo> listCollectStaticFactories = collectStaticFactories(psiNewExpression);
        CandidateInfo candidateInfoResolveConflict = listCollectStaticFactories != null ? javaMethodsConflictResolver.resolveConflict(new ArrayList(listCollectStaticFactories)) : null;
        PsiMethod psiMethod = candidateInfoResolveConflict != null ? (PsiMethod) candidateInfoResolveConflict.getElement() : null;
        if (psiMethod == null) {
            return JavaResolveResult.EMPTY;
        }
        MethodCandidateInfo methodCandidateInfo = (MethodCandidateInfo) candidateInfoResolveConflict;
        MethodCandidateInfo methodCandidateInfoCreateMethodCandidate = createMethodCandidate(methodCandidateInfo, psiElement, false, argumentList);
        if (!psiMethod.isVarArgs()) {
            return methodCandidateInfoCreateMethodCandidate;
        }
        ArrayList arrayList = new ArrayList();
        arrayList.add(methodCandidateInfoCreateMethodCandidate);
        arrayList.add(createMethodCandidate(methodCandidateInfo, psiElement, true, argumentList));
        return javaMethodsConflictResolver.resolveConflict(arrayList);
    }

    public static List<CandidateInfo> collectStaticFactories(final PsiNewExpression psiNewExpression) {
        return (List) CachedValuesManager.getCachedValue((PsiElement) psiNewExpression, new CachedValueProvider() { // from class: org.jetbrains.kotlin.com.intellij.psi.PsiDiamondTypeImpl$$ExternalSyntheticLambda6
            @Override // org.jetbrains.kotlin.com.intellij.psi.util.CachedValueProvider
            public final CachedValueProvider.Result compute() {
                return PsiDiamondTypeImpl.lambda$collectStaticFactories$2(psiNewExpression);
            }
        });
    }

    static /* synthetic */ CachedValueProvider.Result lambda$collectStaticFactories$2(PsiNewExpression psiNewExpression) {
        return new CachedValueProvider.Result(collectStaticFactoriesInner(psiNewExpression), PsiModificationTracker.MODIFICATION_COUNT);
    }

    private static List<CandidateInfo> collectStaticFactoriesInner(final PsiNewExpression psiNewExpression) {
        final PsiClass psiClassFindClass;
        final PsiExpressionList argumentList = psiNewExpression.getArgumentList();
        if (argumentList == null || (psiClassFindClass = findClass(psiNewExpression)) == null) {
            return null;
        }
        ArrayList arrayList = new ArrayList();
        PsiMethod[] constructors = psiClassFindClass.getConstructors();
        if (constructors.length == 0) {
            constructors = new PsiMethod[]{null};
        }
        MethodCandidatesProcessor methodCandidatesProcessor = new MethodCandidatesProcessor(argumentList, argumentList.getContainingFile(), new PsiConflictResolver[0], arrayList) { // from class: org.jetbrains.kotlin.com.intellij.psi.PsiDiamondTypeImpl.2
            private static /* synthetic */ void $$$reportNull$$$0(int i) {
                Object[] objArr = new Object[3];
                if (i != 1) {
                    objArr[0] = "candidate";
                } else {
                    objArr[0] = "method";
                }
                objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/PsiDiamondTypeImpl$2";
                if (i != 1) {
                    objArr[2] = "isAccepted";
                } else {
                    objArr[2] = "getContainingClass";
                }
                throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
            }

            @Override // org.jetbrains.kotlin.com.intellij.psi.scope.processor.MethodCandidatesProcessor
            protected boolean acceptVarargs() {
                return true;
            }

            @Override // org.jetbrains.kotlin.com.intellij.psi.scope.processor.MethodCandidatesProcessor
            protected boolean isAccepted(PsiMethod psiMethod) {
                if (psiMethod != null) {
                    return true;
                }
                $$$reportNull$$$0(0);
                return true;
            }

            @Override // org.jetbrains.kotlin.com.intellij.psi.scope.processor.MethodCandidatesProcessor
            protected PsiClass getContainingClass(PsiMethod psiMethod) {
                if (psiMethod == null) {
                    $$$reportNull$$$0(1);
                }
                if (psiNewExpression.getAnonymousClass() != null) {
                    return (PsiClass) PsiTreeUtil.getContextOfType((PsiElement) argumentList, PsiClass.class, false);
                }
                return psiClassFindClass;
            }
        };
        methodCandidatesProcessor.setArgumentList(argumentList);
        for (PsiMethod psiMethod : constructors) {
            PsiMethod psiMethodGenerateStaticFactory = generateStaticFactory(psiMethod, psiClassFindClass, getAllTypeParams(psiMethod, psiClassFindClass), psiNewExpression.getClassReference());
            if (psiMethodGenerateStaticFactory != null) {
                methodCandidatesProcessor.add(psiMethodGenerateStaticFactory, PsiSubstitutor.EMPTY);
            }
        }
        return methodCandidatesProcessor.getResults();
    }

    private static PsiClass findClass(PsiNewExpression psiNewExpression) {
        String referenceName;
        PsiClass psiClassResolveClassInClassTypeOnly;
        PsiJavaCodeReferenceElement classOrAnonymousClassReference = psiNewExpression.getClassOrAnonymousClassReference();
        if (classOrAnonymousClassReference == null || (referenceName = classOrAnonymousClassReference.getCustomReferenceName()) == null) {
            return null;
        }
        PsiResolveHelper resolveHelper = JavaPsiFacade.getInstance(psiNewExpression.getProject()).getResolveHelper();
        PsiExpression qualifier = psiNewExpression.getQualifier();
        PsiElement qualifier2 = classOrAnonymousClassReference.getQualifier();
        String qualifiedName = StringUtil.getQualifiedName(qualifier2 != null ? qualifier2.getText() : "", referenceName);
        if (qualifier != null && (psiClassResolveClassInClassTypeOnly = PsiUtil.resolveClassInClassTypeOnly(qualifier.getType())) != null) {
            return psiClassResolveClassInClassTypeOnly.findInnerClassByName(qualifiedName, false);
        }
        return resolveHelper.resolveReferencedClass(qualifiedName, psiNewExpression);
    }

    private static PsiMethod generateStaticFactory(PsiMethod psiMethod, PsiClass psiClass, PsiTypeParameter[] psiTypeParameterArr, PsiJavaCodeReferenceElement psiJavaCodeReferenceElement) {
        StringBuilder sb = new StringBuilder();
        String visibilityModifier = VisibilityUtil.getVisibilityModifier(psiMethod != null ? psiMethod.getModifierList() : psiClass.getModifierList());
        if (!PsiModifier.PACKAGE_LOCAL.equals(visibilityModifier)) {
            sb.append(visibilityModifier);
            sb.append(" ");
        }
        sb.append("static <");
        final UniqueNameGenerator uniqueNameGenerator = new UniqueNameGenerator();
        sb.append(StringUtil.join(psiTypeParameterArr, new Function() { // from class: org.jetbrains.kotlin.com.intellij.psi.PsiDiamondTypeImpl$$ExternalSyntheticLambda2
            @Override // org.jetbrains.kotlin.com.intellij.util.Function
            public final Object fun(Object obj) {
                return PsiDiamondTypeImpl.lambda$generateStaticFactory$4(uniqueNameGenerator, (PsiTypeParameter) obj);
            }
        }, ", "));
        sb.append(">");
        PsiElementFactory elementFactory = JavaPsiFacade.getElementFactory(psiClass.getProject());
        String qualifiedName = psiClass.getQualifiedName();
        PsiElement qualifier = psiJavaCodeReferenceElement != null ? psiJavaCodeReferenceElement.getQualifier() : null;
        if (qualifier instanceof PsiJavaCodeReferenceElement) {
            JavaResolveResult javaResolveResultAdvancedResolve = ((PsiJavaCodeReferenceElement) qualifier).advancedResolve(false);
            PsiElement element = javaResolveResultAdvancedResolve.getElement();
            if (element instanceof PsiClass) {
                qualifiedName = elementFactory.createType((PsiClass) element, javaResolveResultAdvancedResolve.getSubstitutor()).getInternalCanonicalText() + "." + psiClass.get$name();
            }
        } else if (psiJavaCodeReferenceElement != null && qualifier == null && psiClass.getContainingClass() != null) {
            qualifiedName = null;
        }
        if (qualifiedName == null) {
            qualifiedName = psiClass.get$name();
        }
        sb.append(qualifiedName);
        PsiTypeParameter[] typeParameters = psiClass.getTypeParameters();
        sb.append("<");
        sb.append(StringUtil.join(typeParameters, new Function() { // from class: org.jetbrains.kotlin.com.intellij.psi.PsiDiamondTypeImpl$$ExternalSyntheticLambda3
            @Override // org.jetbrains.kotlin.com.intellij.util.Function
            public final Object fun(Object obj) {
                return ((PsiTypeParameter) obj).get$name();
            }
        }, ", "));
        sb.append("> ");
        sb.append(JavaCodeStyleManager.getInstance(psiClass.getProject()).suggestUniqueVariableName("staticFactory", (PsiElement) psiClass, false));
        if (psiMethod == null) {
            sb.append("()");
        } else {
            sb.append("(").append(StringUtil.join(psiMethod.getParameterList().getParameters(), new Function<PsiParameter, String>() { // from class: org.jetbrains.kotlin.com.intellij.psi.PsiDiamondTypeImpl.3
                int myIdx;

                @Override // org.jetbrains.kotlin.com.intellij.util.Function
                public String fun(PsiParameter psiParameter) {
                    StringBuilder sbAppend = new StringBuilder().append(psiParameter.getType().getCanonicalText()).append(" p");
                    int i = this.myIdx;
                    this.myIdx = i + 1;
                    return sbAppend.append(i).toString();
                }
            }, ",")).append(Constant.AFTER_QUTO);
            PsiClassType[] referencedTypes = psiMethod.getThrowsList().getReferencedTypes();
            if (referencedTypes.length > 0) {
                sb.append("throws ").append(StringUtil.join(referencedTypes, new Function() { // from class: org.jetbrains.kotlin.com.intellij.psi.PsiDiamondTypeImpl$$ExternalSyntheticLambda4
                    @Override // org.jetbrains.kotlin.com.intellij.util.Function
                    public final Object fun(Object obj) {
                        return ((PsiClassType) obj).getCanonicalText();
                    }
                }, ", "));
            }
        }
        sb.append("{}");
        try {
            return elementFactory.createMethodFromText(sb.toString(), psiMethod != null ? psiMethod : psiClass);
        } catch (IncorrectOperationException unused) {
            return null;
        }
    }

    /* JADX WARN: Code duplicated, block: B:7:0x002e  */
    static /* synthetic */ String lambda$generateStaticFactory$4(UniqueNameGenerator uniqueNameGenerator, PsiTypeParameter psiTypeParameter) {
        String str;
        if (psiTypeParameter.getLanguage().isKindOf(JavaLanguage.INSTANCE)) {
            PsiClassType[] extendsListTypes = psiTypeParameter.getExtendsListTypes();
            if (extendsListTypes.length > 0) {
                str = " extends " + StringUtil.join(extendsListTypes, new Function() { // from class: org.jetbrains.kotlin.com.intellij.psi.PsiDiamondTypeImpl$$ExternalSyntheticLambda0
                    @Override // org.jetbrains.kotlin.com.intellij.util.Function
                    public final Object fun(Object obj) {
                        return ((PsiClassType) obj).getCanonicalText();
                    }
                }, ContainerUtils.FIELD_DELIMITER);
            } else {
                str = "";
            }
        } else {
            str = "";
        }
        return uniqueNameGenerator.generateUniqueName(psiTypeParameter.get$name()) + str;
    }

    private static PsiTypeParameter[] getAllTypeParams(PsiTypeParameterListOwner psiTypeParameterListOwner, PsiClass psiClass) {
        LinkedHashSet linkedHashSet = new LinkedHashSet();
        Collections.addAll(linkedHashSet, psiClass.getTypeParameters());
        if (psiTypeParameterListOwner != null) {
            Collections.addAll(linkedHashSet, psiTypeParameterListOwner.getTypeParameters());
        }
        PsiTypeParameter[] psiTypeParameterArr = (PsiTypeParameter[]) linkedHashSet.toArray(PsiTypeParameter.EMPTY_ARRAY);
        if (psiTypeParameterArr == null) {
            $$$reportNull$$$0(5);
        }
        return psiTypeParameterArr;
    }

    private static MethodCandidateInfo createMethodCandidate(MethodCandidateInfo methodCandidateInfo, final PsiElement psiElement, final boolean z, final PsiExpressionList psiExpressionList) {
        if (methodCandidateInfo == null) {
            $$$reportNull$$$0(6);
        }
        return new MethodCandidateInfo(methodCandidateInfo.getElement(), PsiSubstitutor.EMPTY, !methodCandidateInfo.isAccessible(), false, psiExpressionList, psiElement, null, null) { // from class: org.jetbrains.kotlin.com.intellij.psi.PsiDiamondTypeImpl.4
            private PsiType[] myExpressionTypes;

            @Override // org.jetbrains.kotlin.com.intellij.psi.infos.MethodCandidateInfo
            public boolean isVarargs() {
                return z;
            }

            @Override // org.jetbrains.kotlin.com.intellij.psi.infos.MethodCandidateInfo
            protected PsiElement getParent() {
                return psiElement;
            }

            @Override // org.jetbrains.kotlin.com.intellij.psi.infos.MethodCandidateInfo
            public PsiType[] getArgumentTypes() {
                if (this.myExpressionTypes == null) {
                    PsiType[] expressionTypes = psiExpressionList.getExpressionTypes();
                    if (MethodCandidateInfo.isOverloadCheck()) {
                        return expressionTypes;
                    }
                    this.myExpressionTypes = expressionTypes;
                }
                return this.myExpressionTypes;
            }
        };
    }

    public static boolean hasDefaultConstructor(PsiClass psiClass) {
        if (psiClass == null) {
            $$$reportNull$$$0(7);
        }
        PsiMethod[] constructors = psiClass.getConstructors();
        for (PsiMethod psiMethod : constructors) {
            if (psiMethod.getParameterList().isEmpty()) {
                return true;
            }
        }
        return constructors.length == 0;
    }

    public static boolean haveConstructorsGenericsParameters(PsiClass psiClass) {
        if (psiClass == null) {
            $$$reportNull$$$0(8);
        }
        for (final PsiMethod psiMethod : psiClass.getConstructors()) {
            for (PsiParameter psiParameter : psiMethod.getParameterList().getParameters()) {
                Boolean bool = (Boolean) psiParameter.getType().accept(new PsiTypeVisitor<Boolean>() { // from class: org.jetbrains.kotlin.com.intellij.psi.PsiDiamondTypeImpl.5
                    private static /* synthetic */ void $$$reportNull$$$0(int i) {
                        Object[] objArr = new Object[3];
                        if (i == 1) {
                            objArr[0] = "classType";
                        } else if (i != 2) {
                            objArr[0] = "arrayType";
                        } else {
                            objArr[0] = "wildcardType";
                        }
                        objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/PsiDiamondTypeImpl$5";
                        if (i == 1) {
                            objArr[2] = "visitClassType";
                        } else if (i != 2) {
                            objArr[2] = "visitArrayType";
                        } else {
                            objArr[2] = "visitWildcardType";
                        }
                        throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
                    }

                    /* JADX WARN: Can't rename method to resolve collision */
                    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiTypeVisitor
                    public Boolean visitArrayType(PsiArrayType psiArrayType) {
                        if (psiArrayType == null) {
                            $$$reportNull$$$0(0);
                        }
                        return (Boolean) psiArrayType.getComponentType().accept(this);
                    }

                    /* JADX WARN: Can't rename method to resolve collision */
                    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiTypeVisitor
                    public Boolean visitClassType(PsiClassType psiClassType) {
                        Boolean bool2;
                        if (psiClassType == null) {
                            $$$reportNull$$$0(1);
                        }
                        for (PsiType psiType : psiClassType.getParameters()) {
                            if (psiType != null && (bool2 = (Boolean) psiType.accept(this)) != null && bool2.booleanValue()) {
                                return true;
                            }
                        }
                        PsiClass psiClassResolveClassInType = PsiUtil.resolveClassInType(psiClassType);
                        return Boolean.valueOf((psiClassResolveClassInType instanceof PsiTypeParameter) && ((PsiTypeParameter) psiClassResolveClassInType).getOwner() == psiMethod);
                    }

                    /* JADX WARN: Can't rename method to resolve collision */
                    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiTypeVisitor
                    public Boolean visitWildcardType(PsiWildcardType psiWildcardType) {
                        if (psiWildcardType == null) {
                            $$$reportNull$$$0(2);
                        }
                        PsiType bound = psiWildcardType.getBound();
                        if (bound == null) {
                            return false;
                        }
                        return (Boolean) bound.accept(this);
                    }
                });
                if (bool != null && bool.booleanValue()) {
                    return true;
                }
            }
        }
        return false;
    }

    private static class InferredAnonymousTypeVisitor extends PsiTypeVisitor<Boolean> {
        private final PsiElement myExpression;

        private static /* synthetic */ void $$$reportNull$$$0(int i) {
            Object[] objArr = new Object[3];
            if (i == 1) {
                objArr[0] = "capturedWildcardType";
            } else if (i == 2) {
                objArr[0] = "intersectionType";
            } else if (i != 3) {
                objArr[0] = ModuleXmlParser.TYPE;
            } else {
                objArr[0] = "classType";
            }
            objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/PsiDiamondTypeImpl$InferredAnonymousTypeVisitor";
            if (i == 1) {
                objArr[2] = "visitCapturedWildcardType";
            } else if (i == 2) {
                objArr[2] = "visitIntersectionType";
            } else if (i != 3) {
                objArr[2] = "visitType";
            } else {
                objArr[2] = "visitClassType";
            }
            throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
        }

        InferredAnonymousTypeVisitor(PsiElement psiElement) {
            this.myExpression = psiElement;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // org.jetbrains.kotlin.com.intellij.psi.PsiTypeVisitor
        public Boolean visitType(PsiType psiType) {
            if (psiType == null) {
                $$$reportNull$$$0(0);
            }
            return true;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // org.jetbrains.kotlin.com.intellij.psi.PsiTypeVisitor
        public Boolean visitCapturedWildcardType(PsiCapturedWildcardType psiCapturedWildcardType) {
            if (psiCapturedWildcardType == null) {
                $$$reportNull$$$0(1);
            }
            return false;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // org.jetbrains.kotlin.com.intellij.psi.PsiTypeVisitor
        public Boolean visitIntersectionType(PsiIntersectionType psiIntersectionType) {
            if (psiIntersectionType == null) {
                $$$reportNull$$$0(2);
            }
            return false;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // org.jetbrains.kotlin.com.intellij.psi.PsiTypeVisitor
        public Boolean visitClassType(PsiClassType psiClassType) {
            if (psiClassType == null) {
                $$$reportNull$$$0(3);
            }
            PsiClassType.ClassResolveResult classResolveResultResolveGenerics = psiClassType.resolveGenerics();
            PsiClass element = classResolveResultResolveGenerics.getElement();
            if (element != null) {
                if ((!(element instanceof PsiTypeParameter) || !TypeConversionUtil.isFreshVariable((PsiTypeParameter) element)) && PsiUtil.isAccessible(element, this.myExpression, null)) {
                    Iterator<PsiType> it = classResolveResultResolveGenerics.getSubstitutor().getSubstitutionMap().values().iterator();
                    while (it.hasNext()) {
                        PsiType next = it.next();
                        Boolean bool = next != null ? (Boolean) next.accept(this) : null;
                        if (bool != null && !bool.booleanValue()) {
                            return false;
                        }
                    }
                }
                return false;
            }
            return true;
        }
    }
}
