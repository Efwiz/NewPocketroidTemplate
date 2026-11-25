package org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.java;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import org.jetbrains.kotlin.com.intellij.openapi.util.Computable;
import org.jetbrains.kotlin.com.intellij.openapi.util.text.StringUtil;
import org.jetbrains.kotlin.com.intellij.pom.java.LanguageLevel;
import org.jetbrains.kotlin.com.intellij.psi.JavaPsiFacade;
import org.jetbrains.kotlin.com.intellij.psi.JavaResolveResult;
import org.jetbrains.kotlin.com.intellij.psi.LambdaUtil;
import org.jetbrains.kotlin.com.intellij.psi.PsiArrayType;
import org.jetbrains.kotlin.com.intellij.psi.PsiClass;
import org.jetbrains.kotlin.com.intellij.psi.PsiClassType;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiElementFactory;
import org.jetbrains.kotlin.com.intellij.psi.PsiExpression;
import org.jetbrains.kotlin.com.intellij.psi.PsiExpressionList;
import org.jetbrains.kotlin.com.intellij.psi.PsiFile;
import org.jetbrains.kotlin.com.intellij.psi.PsiIdentifier;
import org.jetbrains.kotlin.com.intellij.psi.PsiMethod;
import org.jetbrains.kotlin.com.intellij.psi.PsiMethodReferenceExpression;
import org.jetbrains.kotlin.com.intellij.psi.PsiMethodReferenceUtil;
import org.jetbrains.kotlin.com.intellij.psi.PsiSubstitutor;
import org.jetbrains.kotlin.com.intellij.psi.PsiSuperExpression;
import org.jetbrains.kotlin.com.intellij.psi.PsiType;
import org.jetbrains.kotlin.com.intellij.psi.PsiTypeParameter;
import org.jetbrains.kotlin.com.intellij.psi.ResolveState;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.resolve.ParameterTypeInferencePolicy;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.resolve.ResolveCache;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.resolve.graphInference.InferenceSession;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.resolve.graphInference.constraints.PsiMethodReferenceCompatibilityConstraint;
import org.jetbrains.kotlin.com.intellij.psi.infos.CandidateInfo;
import org.jetbrains.kotlin.com.intellij.psi.infos.ClassCandidateInfo;
import org.jetbrains.kotlin.com.intellij.psi.infos.MethodCandidateInfo;
import org.jetbrains.kotlin.com.intellij.psi.scope.JavaScopeProcessorEvent;
import org.jetbrains.kotlin.com.intellij.psi.scope.PsiConflictResolver;
import org.jetbrains.kotlin.com.intellij.psi.scope.conflictResolvers.JavaMethodsConflictResolver;
import org.jetbrains.kotlin.com.intellij.psi.scope.processor.MethodCandidatesProcessor;
import org.jetbrains.kotlin.com.intellij.psi.util.MethodSignature;
import org.jetbrains.kotlin.com.intellij.psi.util.PsiTreeUtil;
import org.jetbrains.kotlin.com.intellij.psi.util.PsiUtil;
import org.jetbrains.kotlin.com.intellij.psi.util.TypeConversionUtil;
import org.jetbrains.kotlin.com.intellij.util.Function;
import org.jetbrains.kotlin.com.intellij.util.SmartList;
import org.jetbrains.kotlin.com.intellij.util.containers.FactoryMap;

/* JADX INFO: loaded from: classes3.dex */
public class MethodReferenceResolver implements ResolveCache.PolyVariantContextResolver<PsiMethodReferenceExpressionImpl> {
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = (i == 2 || i == 3 || i == 4 || i == 5 || i == 13) ? "@NotNull method %s.%s must not return null" : "Argument for @NotNull parameter '%s' of %s.%s must not be null";
        Object[] objArr = new Object[(i == 2 || i == 3 || i == 4 || i == 5 || i == 13) ? 2 : 3];
        switch (i) {
            case 1:
                objArr[0] = "containingFile";
                break;
            case 2:
            case 3:
            case 4:
            case 5:
            case 13:
                objArr[0] = "org/jetbrains/kotlin/com/intellij/psi/impl/source/tree/java/MethodReferenceResolver";
                break;
            case 6:
                objArr[0] = "psiClass";
                break;
            case 7:
            case 11:
                objArr[0] = "containingClass";
                break;
            case 8:
            default:
                objArr[0] = "reference";
                break;
            case 9:
                objArr[0] = "referenceExpression";
                break;
            case 10:
                objArr[0] = "qualifierResolveResult";
                break;
            case 12:
                objArr[0] = "substitutor";
                break;
            case 14:
                objArr[0] = "signature";
                break;
        }
        if (i == 2 || i == 3 || i == 4 || i == 5) {
            objArr[1] = "resolve";
        } else if (i != 13) {
            objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/impl/source/tree/java/MethodReferenceResolver";
        } else {
            objArr[1] = "composeReturnType";
        }
        switch (i) {
            case 2:
            case 3:
            case 4:
            case 5:
            case 13:
                break;
            case 6:
                objArr[2] = "canBeConstructed";
                break;
            case 7:
            case 8:
                objArr[2] = "isLocatedInStaticContext";
                break;
            case 9:
            case 10:
                objArr[2] = "createResolver";
                break;
            case 11:
            case 12:
                objArr[2] = "composeReturnType";
                break;
            case 14:
                objArr[2] = "arrayCreationSignature";
                break;
            default:
                objArr[2] = "resolve";
                break;
        }
        String str2 = String.format(str, objArr);
        if (i != 2 && i != 3 && i != 4 && i != 5 && i != 13) {
            throw new IllegalArgumentException(str2);
        }
        throw new IllegalStateException(str2);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r15v0, types: [org.jetbrains.kotlin.com.intellij.psi.PsiClass, org.jetbrains.kotlin.com.intellij.psi.PsiElement] */
    /* JADX WARN: Type inference failed for: r1v11 */
    /* JADX WARN: Type inference failed for: r1v12 */
    /* JADX WARN: Type inference failed for: r1v4 */
    /* JADX WARN: Type inference failed for: r1v5, types: [org.jetbrains.kotlin.com.intellij.psi.scope.PsiScopeProcessor, org.jetbrains.kotlin.com.intellij.psi.scope.processor.MethodCandidatesProcessor] */
    /* JADX WARN: Type inference failed for: r1v7, types: [org.jetbrains.kotlin.com.intellij.psi.scope.processor.MethodCandidatesProcessor] */
    /* JADX WARN: Type inference failed for: r3v0, types: [org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.java.MethodReferenceResolver$1, org.jetbrains.kotlin.com.intellij.psi.scope.processor.MethodCandidatesProcessor] */
    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.resolve.ResolveCache.PolyVariantContextResolver
    public JavaResolveResult[] resolve(final PsiMethodReferenceExpressionImpl psiMethodReferenceExpressionImpl, PsiFile psiFile, boolean z) {
        PsiMethodReferenceExpressionImpl psiMethodReferenceExpressionImpl2;
        ?? r1;
        ?? r2;
        if (psiMethodReferenceExpressionImpl == null) {
            $$$reportNull$$$0(0);
        }
        if (psiFile == null) {
            $$$reportNull$$$0(1);
        }
        final PsiMethodReferenceUtil.QualifierResolveResult qualifierResolveResult = PsiMethodReferenceUtil.getQualifierResolveResult(psiMethodReferenceExpressionImpl);
        final ?? containingClass = qualifierResolveResult.getContainingClass();
        PsiSubstitutor substitutor = qualifierResolveResult.getSubstitutor();
        if (containingClass != 0) {
            PsiElement referenceNameElement = psiMethodReferenceExpressionImpl.getReferenceNameElement();
            boolean zIsConstructor = psiMethodReferenceExpressionImpl.isConstructor();
            if ((referenceNameElement instanceof PsiIdentifier) || zIsConstructor) {
                if (zIsConstructor && !canBeConstructed(containingClass)) {
                    JavaResolveResult[] javaResolveResultArr = JavaResolveResult.EMPTY_ARRAY;
                    if (javaResolveResultArr == null) {
                        $$$reportNull$$$0(2);
                    }
                    return javaResolveResultArr;
                }
                final PsiType functionalInterfaceType = psiMethodReferenceExpressionImpl.getFunctionalInterfaceType();
                PsiClassType.ClassResolveResult classResolveResultResolveGenericsClassInType = PsiUtil.resolveGenericsClassInType(functionalInterfaceType);
                final PsiMethod functionalInterfaceMethod = LambdaUtil.getFunctionalInterfaceMethod(classResolveResultResolveGenericsClassInType);
                ClassCandidateInfo classCandidateInfo = null;
                MethodSignature signature = functionalInterfaceMethod != null ? functionalInterfaceMethod.getSignature(functionalInterfaceMethod != null ? LambdaUtil.getSubstitutor(functionalInterfaceMethod, classResolveResultResolveGenericsClassInType) : null) : null;
                final PsiType functionalInterfaceReturnType = LambdaUtil.getFunctionalInterfaceReturnType(functionalInterfaceType);
                if (zIsConstructor && containingClass.getConstructors().length == 0) {
                    if (functionalInterfaceReturnType != null) {
                        PsiClassType psiClassTypeComposeReturnType = composeReturnType(containingClass, substitutor);
                        InferenceSession inferenceSession = new InferenceSession(containingClass.getTypeParameters(), substitutor, psiMethodReferenceExpressionImpl.getManager(), null);
                        if (!inferenceSession.isProperType(inferenceSession.substituteWithInferenceVariables(psiClassTypeComposeReturnType)) || !inferenceSession.isProperType(functionalInterfaceReturnType)) {
                            inferenceSession.registerReturnTypeConstraints(psiClassTypeComposeReturnType, functionalInterfaceReturnType, psiMethodReferenceExpressionImpl);
                            substitutor = inferenceSession.infer();
                        }
                    }
                    boolean zIsArrayClass = PsiUtil.isArrayClass(containingClass);
                    if (signature == null || ((!zIsArrayClass && ((containingClass.getContainingClass() == null || !isLocatedInStaticContext(containingClass, psiMethodReferenceExpressionImpl)) && signature.getParameterTypes().length == 0)) || (zIsArrayClass && arrayCreationSignature(signature)))) {
                        classCandidateInfo = new ClassCandidateInfo(containingClass, substitutor);
                    }
                    JavaResolveResult[] javaResolveResultArr2 = classCandidateInfo == null ? JavaResolveResult.EMPTY_ARRAY : new JavaResolveResult[]{classCandidateInfo};
                    if (javaResolveResultArr2 == null) {
                        $$$reportNull$$$0(3);
                    }
                    return javaResolveResultArr2;
                }
                final MethodSignature methodSignature = signature;
                ?? r3 = new MethodCandidatesProcessor(psiMethodReferenceExpressionImpl, psiFile, new PsiConflictResolver[]{createResolver(psiMethodReferenceExpressionImpl, qualifierResolveResult, functionalInterfaceMethod, signature)}, new SmartList()) { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.java.MethodReferenceResolver.1
                    private static /* synthetic */ void $$$reportNull$$$0(int i) {
                        Object[] objArr = new Object[3];
                        if (i != 1) {
                            objArr[0] = "method";
                        } else {
                            objArr[0] = "substitutor";
                        }
                        objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/impl/source/tree/java/MethodReferenceResolver$1";
                        objArr[2] = "createCandidateInfo";
                        throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
                    }

                    @Override // org.jetbrains.kotlin.com.intellij.psi.scope.processor.MethodCandidatesProcessor
                    protected boolean acceptVarargs() {
                        return true;
                    }

                    @Override // org.jetbrains.kotlin.com.intellij.psi.scope.processor.MethodCandidatesProcessor
                    protected MethodCandidateInfo createCandidateInfo(PsiMethod psiMethod, PsiSubstitutor psiSubstitutor, boolean z2, boolean z3, boolean z4) {
                        if (psiMethod == null) {
                            $$$reportNull$$$0(0);
                        }
                        if (psiSubstitutor == null) {
                            $$$reportNull$$$0(1);
                        }
                        PsiExpressionList argumentList = getArgumentList();
                        PsiType[] typeParameters = psiMethodReferenceExpressionImpl.getTypeParameters();
                        return new C02991(psiMethod, psiSubstitutor, !z3, z2, argumentList, this.myCurrentFileContext, argumentList != null ? argumentList.getExpressionTypes() : null, (!psiMethod.hasTypeParameters() || typeParameters.length <= 0) ? null : typeParameters, getLanguageLevel(), z4, psiSubstitutor, psiMethod);
                    }

                    /* JADX INFO: renamed from: org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.java.MethodReferenceResolver$1$1, reason: invalid class name and collision with other inner class name */
                    class C02991 extends MethodCandidateInfo {
                        final /* synthetic */ PsiMethod val$method;
                        final /* synthetic */ PsiSubstitutor val$substitutor;
                        final /* synthetic */ boolean val$varargs;

                        private static /* synthetic */ void $$$reportNull$$$0(int i) {
                            String str = i != 1 ? "Argument for @NotNull parameter '%s' of %s.%s must not be null" : "@NotNull method %s.%s must not return null";
                            Object[] objArr = new Object[i != 1 ? 3 : 2];
                            if (i != 1) {
                                objArr[0] = "policy";
                            } else {
                                objArr[0] = "org/jetbrains/kotlin/com/intellij/psi/impl/source/tree/java/MethodReferenceResolver$1$1";
                            }
                            if (i != 1) {
                                objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/impl/source/tree/java/MethodReferenceResolver$1$1";
                            } else {
                                objArr[1] = "inferTypeArguments";
                            }
                            if (i != 1) {
                                objArr[2] = "inferTypeArguments";
                            }
                            String str2 = String.format(str, objArr);
                            if (i == 1) {
                                throw new IllegalStateException(str2);
                            }
                        }

                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                        C02991(PsiElement psiElement, PsiSubstitutor psiSubstitutor, boolean z, boolean z2, PsiElement psiElement2, PsiElement psiElement3, PsiType[] psiTypeArr, PsiType[] psiTypeArr2, LanguageLevel languageLevel, boolean z3, PsiSubstitutor psiSubstitutor2, PsiMethod psiMethod) {
                            super(psiElement, psiSubstitutor, z, z2, psiElement2, psiElement3, psiTypeArr, psiTypeArr2, languageLevel);
                            this.val$varargs = z3;
                            this.val$substitutor = psiSubstitutor2;
                            this.val$method = psiMethod;
                        }

                        @Override // org.jetbrains.kotlin.com.intellij.psi.infos.MethodCandidateInfo
                        public boolean isVarargs() {
                            return this.val$varargs;
                        }

                        @Override // org.jetbrains.kotlin.com.intellij.psi.infos.MethodCandidateInfo
                        public PsiSubstitutor inferTypeArguments(ParameterTypeInferencePolicy parameterTypeInferencePolicy, boolean z) {
                            PsiSubstitutor psiSubstitutorInferTypeArguments;
                            if (parameterTypeInferencePolicy == null) {
                                $$$reportNull$$$0(0);
                            }
                            if (z) {
                                psiSubstitutorInferTypeArguments = inferTypeArguments(true);
                            } else {
                                psiSubstitutorInferTypeArguments = (PsiSubstitutor) Objects.requireNonNull((PsiSubstitutor) MethodCandidateInfo.ourOverloadGuard.doPreventingRecursion(psiMethodReferenceExpressionImpl, false, new Computable() { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.java.MethodReferenceResolver$1$1$$ExternalSyntheticLambda0
                                    @Override // org.jetbrains.kotlin.com.intellij.openapi.util.Computable
                                    public final Object compute() {
                                        return this.f$0.m6677x7ef5a897();
                                    }
                                }));
                            }
                            if (psiSubstitutorInferTypeArguments == null) {
                                $$$reportNull$$$0(1);
                            }
                            return psiSubstitutorInferTypeArguments;
                        }

                        /* JADX INFO: renamed from: lambda$inferTypeArguments$0$org-jetbrains-kotlin-com-intellij-psi-impl-source-tree-java-MethodReferenceResolver$1$1, reason: not valid java name */
                        /* synthetic */ PsiSubstitutor m6677x7ef5a897() {
                            return inferTypeArguments(false);
                        }

                        private PsiSubstitutor inferTypeArguments(boolean z) {
                            PsiType psiTypeSubstitute;
                            if (functionalInterfaceMethod == null) {
                                return this.val$substitutor;
                            }
                            InferenceSession inferenceSession = new InferenceSession(this.val$method.getTypeParameters(), this.val$substitutor, psiMethodReferenceExpressionImpl.getManager(), psiMethodReferenceExpressionImpl);
                            inferenceSession.initThrowsConstraints(this.val$method);
                            PsiSubstitutor psiSubstitutorCollectApplicabilityConstraints = inferenceSession.collectApplicabilityConstraints(psiMethodReferenceExpressionImpl, this, functionalInterfaceType);
                            if (psiSubstitutorCollectApplicabilityConstraints != null) {
                                return psiSubstitutorCollectApplicabilityConstraints;
                            }
                            if (!inferenceSession.repeatInferencePhases()) {
                                List<String> incompatibleErrorMessages = inferenceSession.getIncompatibleErrorMessages();
                                if (incompatibleErrorMessages != null) {
                                    setApplicabilityError(StringUtil.join((Collection<String>) incompatibleErrorMessages, "\n"));
                                }
                                return this.val$substitutor;
                            }
                            if (z && !PsiType.VOID.equals(functionalInterfaceReturnType) && functionalInterfaceReturnType != null) {
                                if (this.val$method.isConstructor()) {
                                    psiTypeSubstitute = MethodReferenceResolver.composeReturnType(containingClass, this.val$substitutor);
                                } else {
                                    psiTypeSubstitute = PsiMethodReferenceCompatibilityConstraint.getSubstitutor(methodSignature, qualifierResolveResult, this.val$method, containingClass, psiMethodReferenceExpressionImpl).substitute(this.val$method.getReturnType());
                                }
                                if (psiTypeSubstitute != null) {
                                    inferenceSession.registerReturnTypeConstraints(psiTypeSubstitute, functionalInterfaceReturnType, psiMethodReferenceExpressionImpl);
                                }
                            }
                            return inferenceSession.infer(this.val$method.getParameterList().getParameters(), null, null, null);
                        }

                        @Override // org.jetbrains.kotlin.com.intellij.psi.infos.MethodCandidateInfo
                        public boolean isApplicable() {
                            if (methodSignature == null || getInferenceErrorMessageAssumeAlreadyComputed() != null) {
                                return false;
                            }
                            PsiType[] parameterTypes = methodSignature.getParameterTypes();
                            return MethodReferenceConflictResolver.isApplicableByFirstSearch(this, parameterTypes, PsiMethodReferenceUtil.isSecondSearchPossible(parameterTypes, qualifierResolveResult, psiMethodReferenceExpressionImpl), psiMethodReferenceExpressionImpl, functionalInterfaceMethod.isVarArgs(), functionalInterfaceMethod) != null;
                        }
                    }
                };
                r3.setIsConstructor(zIsConstructor);
                r3.setName(zIsConstructor ? containingClass.get$name() : referenceNameElement.getText());
                PsiExpression qualifierExpression = psiMethodReferenceExpressionImpl.getQualifierExpression();
                if (qualifierExpression == null || (!(qualifierExpression.getType() instanceof PsiArrayType) && !(qualifierExpression instanceof PsiSuperExpression))) {
                    r3.setAccessClass(containingClass);
                }
                if (qualifierResolveResult.isReferenceTypeQualified()) {
                    psiMethodReferenceExpressionImpl2 = psiMethodReferenceExpressionImpl;
                    r2 = r3;
                    if (isLocatedInStaticContext(containingClass, psiMethodReferenceExpressionImpl2)) {
                        r1 = r2;
                        r2.handleEvent(JavaScopeProcessorEvent.START_STATIC, null);
                        r1 = r2;
                    }
                } else {
                    psiMethodReferenceExpressionImpl2 = psiMethodReferenceExpressionImpl;
                    r1 = r3;
                }
                r1 = r2;
                containingClass.processDeclarations(r1, ResolveState.initial().put(PsiSubstitutor.KEY, substitutor), psiMethodReferenceExpressionImpl2, psiMethodReferenceExpressionImpl2);
                JavaResolveResult[] result = r1.getResult();
                if (result == null) {
                    $$$reportNull$$$0(4);
                }
                return result;
            }
        }
        JavaResolveResult[] javaResolveResultArr3 = JavaResolveResult.EMPTY_ARRAY;
        if (javaResolveResultArr3 == null) {
            $$$reportNull$$$0(5);
        }
        return javaResolveResultArr3;
    }

    public static boolean canBeConstructed(PsiClass psiClass) {
        if (psiClass == null) {
            $$$reportNull$$$0(6);
        }
        return (psiClass.isEnum() || psiClass.hasModifierProperty("abstract") || (psiClass instanceof PsiTypeParameter)) ? false : true;
    }

    private static boolean isLocatedInStaticContext(PsiClass psiClass, PsiMethodReferenceExpression psiMethodReferenceExpression) {
        if (psiClass == null) {
            $$$reportNull$$$0(7);
        }
        if (psiMethodReferenceExpression == null) {
            $$$reportNull$$$0(8);
        }
        PsiClass containingClass = psiClass.getContainingClass();
        if (containingClass != null && psiClass.hasModifierProperty("static")) {
            return false;
        }
        if (!PsiTreeUtil.isAncestor(containingClass != null ? containingClass : psiClass, psiMethodReferenceExpression, false)) {
            psiClass = null;
        } else if (containingClass != null) {
            psiClass = containingClass;
        }
        return PsiUtil.getEnclosingStaticElement(psiMethodReferenceExpression, psiClass) != null;
    }

    protected PsiConflictResolver createResolver(PsiMethodReferenceExpressionImpl psiMethodReferenceExpressionImpl, PsiMethodReferenceUtil.QualifierResolveResult qualifierResolveResult, PsiMethod psiMethod, MethodSignature methodSignature) {
        if (psiMethodReferenceExpressionImpl == null) {
            $$$reportNull$$$0(9);
        }
        if (qualifierResolveResult == null) {
            $$$reportNull$$$0(10);
        }
        return new MethodReferenceConflictResolver(psiMethodReferenceExpressionImpl, qualifierResolveResult, methodSignature, psiMethod);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static PsiClassType composeReturnType(PsiClass psiClass, PsiSubstitutor psiSubstitutor) {
        if (psiClass == null) {
            $$$reportNull$$$0(11);
        }
        if (psiSubstitutor == null) {
            $$$reportNull$$$0(12);
        }
        boolean zIsRawSubstitutor = PsiUtil.isRawSubstitutor(psiClass, psiSubstitutor);
        PsiElementFactory elementFactory = JavaPsiFacade.getElementFactory(psiClass.getProject());
        if (zIsRawSubstitutor) {
            psiSubstitutor = PsiSubstitutor.EMPTY;
        }
        PsiClassType psiClassTypeCreateType = elementFactory.createType(psiClass, psiSubstitutor);
        if (psiClassTypeCreateType == null) {
            $$$reportNull$$$0(13);
        }
        return psiClassTypeCreateType;
    }

    /* JADX INFO: Access modifiers changed from: private */
    static final class MethodReferenceConflictResolver extends JavaMethodsConflictResolver {
        private final boolean myFunctionalMethodVarArgs;
        private final PsiMethod myInterfaceMethod;
        private final PsiMethodReferenceUtil.QualifierResolveResult myQualifierResolveResult;
        private final PsiMethodReferenceExpressionImpl myReferenceExpression;
        private final MethodSignature mySignature;

        private static /* synthetic */ void $$$reportNull$$$0(int i) {
            Object[] objArr = new Object[3];
            switch (i) {
                case 1:
                    objArr[0] = "qualifierResolveResult";
                    break;
                case 2:
                case 4:
                case 7:
                case 10:
                case 14:
                    objArr[0] = "conflict";
                    break;
                case 3:
                    objArr[0] = "conflicts";
                    break;
                case 5:
                default:
                    objArr[0] = "referenceExpression";
                    break;
                case 6:
                case 9:
                    objArr[0] = "functionalInterfaceParamTypes";
                    break;
                case 8:
                    objArr[0] = "parameterTypes";
                    break;
                case 11:
                case 15:
                    objArr[0] = "firstCandidates";
                    break;
                case 12:
                case 16:
                    objArr[0] = "secondCandidates";
                    break;
                case 13:
                    objArr[0] = "method";
                    break;
            }
            objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/impl/source/tree/java/MethodReferenceResolver$MethodReferenceConflictResolver";
            switch (i) {
                case 2:
                    objArr[2] = "getPertinentApplicabilityLevel";
                    break;
                case 3:
                    objArr[2] = "guardedOverloadResolution";
                    break;
                case 4:
                case 5:
                case 6:
                    objArr[2] = "isApplicableByFirstSearch";
                    break;
                case 7:
                case 8:
                case 9:
                    objArr[2] = "isCorrectAssignment";
                    break;
                case 10:
                    objArr[2] = "markNotApplicable";
                    break;
                case 11:
                case 12:
                    objArr[2] = "resolveConflicts";
                    break;
                case 13:
                case 14:
                    objArr[2] = "nonComparable";
                    break;
                case 15:
                case 16:
                    objArr[2] = "filterStaticCorrectCandidates";
                    break;
                default:
                    objArr[2] = "<init>";
                    break;
            }
            throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        private MethodReferenceConflictResolver(PsiMethodReferenceExpressionImpl psiMethodReferenceExpressionImpl, PsiMethodReferenceUtil.QualifierResolveResult qualifierResolveResult, MethodSignature methodSignature, PsiMethod psiMethod) {
            super(psiMethodReferenceExpressionImpl, methodSignature != null ? methodSignature.getParameterTypes() : PsiType.EMPTY_ARRAY, PsiUtil.getLanguageLevel(psiMethodReferenceExpressionImpl), psiMethodReferenceExpressionImpl.getContainingFile());
            boolean z = false;
            if (psiMethodReferenceExpressionImpl == null) {
                $$$reportNull$$$0(0);
            }
            if (qualifierResolveResult == null) {
                $$$reportNull$$$0(1);
            }
            this.myReferenceExpression = psiMethodReferenceExpressionImpl;
            this.myQualifierResolveResult = qualifierResolveResult;
            if (psiMethod != null && psiMethod.isVarArgs()) {
                z = true;
            }
            this.myFunctionalMethodVarArgs = z;
            this.mySignature = methodSignature;
            this.myInterfaceMethod = psiMethod;
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.scope.conflictResolvers.JavaMethodsConflictResolver
        protected int getPertinentApplicabilityLevel(MethodCandidateInfo methodCandidateInfo, Map<MethodCandidateInfo, PsiSubstitutor> map) {
            if (methodCandidateInfo == null) {
                $$$reportNull$$$0(2);
            }
            return methodCandidateInfo.isVarargs() ? 2 : 3;
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.scope.conflictResolvers.JavaMethodsConflictResolver
        protected CandidateInfo guardedOverloadResolution(List<CandidateInfo> list) {
            if (list == null) {
                $$$reportNull$$$0(3);
            }
            if (this.mySignature == null || list.isEmpty()) {
                return null;
            }
            if (list.size() == 1) {
                return list.get(0);
            }
            Map<MethodCandidateInfo, PsiSubstitutor> mapCreate = FactoryMap.create(new Function() { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.java.MethodReferenceResolver$MethodReferenceConflictResolver$$ExternalSyntheticLambda0
                @Override // org.jetbrains.kotlin.com.intellij.util.Function
                public final Object fun(Object obj) {
                    return ((MethodCandidateInfo) obj).getSubstitutor(false);
                }
            });
            checkSameSignatures(list, mapCreate);
            if (list.size() == 1) {
                return list.get(0);
            }
            checkAccessStaticLevels(list, true);
            if (list.size() == 1) {
                return list.get(0);
            }
            PsiType[] parameterTypes = this.mySignature.getParameterTypes();
            boolean zIsSecondSearchPossible = PsiMethodReferenceUtil.isSecondSearchPossible(parameterTypes, this.myQualifierResolveResult, this.myReferenceExpression);
            List<CandidateInfo> arrayList = new ArrayList<>();
            List<CandidateInfo> arrayList2 = new ArrayList<>();
            for (CandidateInfo candidateInfo : list) {
                if (candidateInfo instanceof MethodCandidateInfo) {
                    List<CandidateInfo> list2 = arrayList2;
                    Boolean boolIsApplicableByFirstSearch = isApplicableByFirstSearch(candidateInfo, parameterTypes, zIsSecondSearchPossible, this.myReferenceExpression, this.myFunctionalMethodVarArgs, this.myInterfaceMethod);
                    if (boolIsApplicableByFirstSearch != null) {
                        (boolIsApplicableByFirstSearch.booleanValue() ? arrayList : list2).add(candidateInfo);
                    }
                    arrayList2 = list2;
                }
            }
            List<CandidateInfo> list3 = arrayList2;
            if (this.myQualifierResolveResult.isReferenceTypeQualified() && (this.myReferenceExpression.getReferenceNameElement() instanceof PsiIdentifier)) {
                int iCheckApplicability = checkApplicability(arrayList);
                List<CandidateInfo> arrayList3 = new ArrayList<>(arrayList);
                checkSpecifics(arrayList3, iCheckApplicability, mapCreate, 0);
                int iCheckApplicability2 = checkApplicability(list3);
                List<CandidateInfo> arrayList4 = new ArrayList<>(list3);
                checkSpecifics(arrayList4, iCheckApplicability2, mapCreate, 1);
                CandidateInfo candidateInfoFilterStaticCorrectCandidates = filterStaticCorrectCandidates(arrayList3, list3, true);
                if (candidateInfoFilterStaticCorrectCandidates != null) {
                    return candidateInfoFilterStaticCorrectCandidates;
                }
                CandidateInfo candidateInfoFilterStaticCorrectCandidates2 = filterStaticCorrectCandidates(arrayList4, arrayList, false);
                if (candidateInfoFilterStaticCorrectCandidates2 != null) {
                    return candidateInfoFilterStaticCorrectCandidates2;
                }
                list.clear();
                list.addAll(arrayList3);
                list.addAll(arrayList4);
                return null;
            }
            CandidateInfo candidateInfoResolveConflicts = resolveConflicts(arrayList, list3, mapCreate, 3);
            if (candidateInfoResolveConflicts != null) {
                return candidateInfoResolveConflicts;
            }
            CandidateInfo candidateInfoResolveConflicts2 = resolveConflicts(arrayList, list3, mapCreate, 2);
            if (candidateInfoResolveConflicts2 != null) {
                return candidateInfoResolveConflicts2;
            }
            if (arrayList.isEmpty() && list3.isEmpty()) {
                return null;
            }
            list.clear();
            arrayList.addAll(list3);
            list.addAll(arrayList);
            return null;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static Boolean isApplicableByFirstSearch(CandidateInfo candidateInfo, PsiType[] psiTypeArr, boolean z, PsiMethodReferenceExpression psiMethodReferenceExpression, boolean z2, PsiMethod psiMethod) {
            PsiClass containingClass;
            if (candidateInfo == null) {
                $$$reportNull$$$0(4);
            }
            if (psiMethodReferenceExpression == null) {
                $$$reportNull$$$0(5);
            }
            if (psiTypeArr == null) {
                $$$reportNull$$$0(6);
            }
            MethodCandidateInfo methodCandidateInfo = (MethodCandidateInfo) candidateInfo;
            PsiMethod element = methodCandidateInfo.getElement();
            PsiSubstitutor substitutor = methodCandidateInfo.getSubstitutor(false);
            if (methodCandidateInfo.getInferenceErrorMessage() != null) {
                return null;
            }
            PsiType[] parameterTypes = element.getSignature(substitutor).getParameterTypes();
            boolean zIsVarargs = methodCandidateInfo.isVarargs();
            if (zIsVarargs && (!element.isVarArgs() || z2)) {
                return null;
            }
            if (z && zIsVarargs && isCorrectAssignment(parameterTypes, psiTypeArr, psiMethod, true, candidateInfo, 0) && isCorrectAssignment(parameterTypes, psiTypeArr, psiMethod, true, candidateInfo, 1)) {
                return Boolean.valueOf(element.hasModifierProperty("static"));
            }
            if ((!zIsVarargs && psiTypeArr.length != parameterTypes.length) || !isCorrectAssignment(parameterTypes, psiTypeArr, psiMethod, zIsVarargs, candidateInfo, 0)) {
                return (z && (zIsVarargs || psiTypeArr.length == parameterTypes.length + 1) && isCorrectAssignment(parameterTypes, psiTypeArr, psiMethod, zIsVarargs, candidateInfo, 1)) ? false : null;
            }
            if (element.hasModifierProperty("static") && (containingClass = element.getContainingClass()) != null && containingClass.isInterface()) {
                if (!containingClass.getManager().areElementsEquivalent(PsiMethodReferenceUtil.getQualifierResolveResult(psiMethodReferenceExpression).getContainingClass(), containingClass)) {
                    return null;
                }
            }
            return true;
        }

        private static boolean isCorrectAssignment(PsiType[] psiTypeArr, PsiType[] psiTypeArr2, PsiMethod psiMethod, boolean z, CandidateInfo candidateInfo, int i) {
            if (candidateInfo == null) {
                $$$reportNull$$$0(7);
            }
            if (psiTypeArr == null) {
                $$$reportNull$$$0(8);
            }
            if (psiTypeArr2 == null) {
                $$$reportNull$$$0(9);
            }
            int iMin = Math.min(psiTypeArr.length, psiTypeArr2.length - i);
            for (int i2 = 0; i2 < iMin; i2++) {
                PsiType psiTypeCaptureToplevelWildcards = PsiUtil.captureToplevelWildcards(psiTypeArr2[i2 + i], psiMethod.getParameterList().getParameters()[i2]);
                PsiType psiType = psiTypeArr[i2];
                if (z && i2 == psiTypeArr.length - 1) {
                    if (!TypeConversionUtil.isAssignable(psiType, psiTypeCaptureToplevelWildcards) && !TypeConversionUtil.isAssignable(((PsiArrayType) psiType).getComponentType(), psiTypeCaptureToplevelWildcards)) {
                        markNotApplicable(candidateInfo);
                        return false;
                    }
                } else if (!TypeConversionUtil.isAssignable(psiType, psiTypeCaptureToplevelWildcards)) {
                    markNotApplicable(candidateInfo);
                    return false;
                }
            }
            return !z || psiTypeArr.length - 1 <= psiTypeArr2.length - i;
        }

        private static void markNotApplicable(CandidateInfo candidateInfo) {
            if (candidateInfo == null) {
                $$$reportNull$$$0(10);
            }
            if (candidateInfo instanceof MethodCandidateInfo) {
                ((MethodCandidateInfo) candidateInfo).markNotApplicable();
            }
        }

        private CandidateInfo resolveConflicts(List<CandidateInfo> list, List<CandidateInfo> list2, Map<MethodCandidateInfo, PsiSubstitutor> map, int i) {
            if (list == null) {
                $$$reportNull$$$0(11);
            }
            if (list2 == null) {
                $$$reportNull$$$0(12);
            }
            int iCheckApplicability = checkApplicability(list);
            checkSpecifics(list, i, map, 0);
            int iCheckApplicability2 = checkApplicability(list2);
            checkSpecifics(list2, i, map, 1);
            if (iCheckApplicability < iCheckApplicability2) {
                if (list2.size() == 1) {
                    return list2.get(0);
                }
                return null;
            }
            if (iCheckApplicability2 < iCheckApplicability) {
                if (list.size() == 1) {
                    return list.get(0);
                }
                return null;
            }
            if (list.size() + list2.size() != 1) {
                return null;
            }
            if (list.isEmpty()) {
                return list2.get(0);
            }
            return list.get(0);
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.scope.conflictResolvers.JavaMethodsConflictResolver
        protected boolean nonComparable(CandidateInfo candidateInfo, CandidateInfo candidateInfo2, boolean z) {
            if (candidateInfo == null) {
                $$$reportNull$$$0(13);
            }
            if (candidateInfo2 == null) {
                $$$reportNull$$$0(14);
            }
            if (candidateInfo == candidateInfo2) {
                return true;
            }
            PsiElement element = candidateInfo.getElement();
            PsiElement element2 = candidateInfo2.getElement();
            if ((element instanceof PsiMethod) && (element2 instanceof PsiMethod)) {
                return z && ((PsiMethod) element).getParameterList().getParametersCount() != ((PsiMethod) element2).getParameterList().getParametersCount();
            }
            return false;
        }

        private static CandidateInfo filterStaticCorrectCandidates(List<CandidateInfo> list, List<CandidateInfo> list2, boolean z) {
            if (list == null) {
                $$$reportNull$$$0(15);
            }
            if (list2 == null) {
                $$$reportNull$$$0(16);
            }
            if (list.size() == 1) {
                CandidateInfo candidateInfo = list.get(0);
                PsiElement element = candidateInfo.getElement();
                if ((element instanceof PsiMethod) && z == ((PsiMethod) element).hasModifierProperty("static")) {
                    Iterator<CandidateInfo> it = list2.iterator();
                    while (it.hasNext()) {
                        PsiElement element2 = it.next().getElement();
                        if ((element2 instanceof PsiMethod) && z != ((PsiMethod) element2).hasModifierProperty("static")) {
                            return null;
                        }
                    }
                    return candidateInfo;
                }
            }
            return null;
        }
    }

    private static boolean arrayCreationSignature(MethodSignature methodSignature) {
        if (methodSignature == null) {
            $$$reportNull$$$0(14);
        }
        PsiType[] parameterTypes = methodSignature.getParameterTypes();
        if (parameterTypes.length == 1 && parameterTypes[0] != null) {
            if (TypeConversionUtil.isAssignable(PsiType.INT, parameterTypes[0])) {
                return true;
            }
            PsiType psiType = parameterTypes[0];
            if (psiType instanceof PsiClassType) {
                return ((PsiClassType) psiType).resolve() instanceof PsiTypeParameter;
            }
        }
        return false;
    }
}
