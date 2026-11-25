package org.jetbrains.kotlin.com.intellij.psi;

import com.huawei.hms.ml.language.common.utils.Constant;
import io.netty.util.internal.StringUtil;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Supplier;
import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;
import org.jetbrains.kotlin.com.intellij.codeInsight.AnnotationTargetUtil;
import org.jetbrains.kotlin.com.intellij.core.JavaPsiBundle;
import org.jetbrains.kotlin.com.intellij.openapi.diagnostic.Logger;
import org.jetbrains.kotlin.com.intellij.openapi.util.Computable;
import org.jetbrains.kotlin.com.intellij.openapi.util.registry.Registry;
import org.jetbrains.kotlin.com.intellij.psi.codeStyle.JavaCodeStyleManager;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.resolve.graphInference.PsiPolyExpressionUtil;
import org.jetbrains.kotlin.com.intellij.psi.infos.MethodCandidateInfo;
import org.jetbrains.kotlin.com.intellij.psi.util.CachedValuesManager;
import org.jetbrains.kotlin.com.intellij.psi.util.InheritanceUtil;
import org.jetbrains.kotlin.com.intellij.psi.util.MethodSignature;
import org.jetbrains.kotlin.com.intellij.psi.util.MethodSignatureBackedByPsiMethod;
import org.jetbrains.kotlin.com.intellij.psi.util.MethodSignatureUtil;
import org.jetbrains.kotlin.com.intellij.psi.util.PsiSuperMethodUtil;
import org.jetbrains.kotlin.com.intellij.psi.util.PsiTreeUtil;
import org.jetbrains.kotlin.com.intellij.psi.util.PsiTypesUtil;
import org.jetbrains.kotlin.com.intellij.psi.util.PsiUtil;
import org.jetbrains.kotlin.com.intellij.psi.util.TypeConversionUtil;
import org.jetbrains.kotlin.com.intellij.util.Consumer;
import org.jetbrains.kotlin.com.intellij.util.IncorrectOperationException;
import org.jetbrains.kotlin.com.intellij.util.ObjectUtils;
import org.jetbrains.kotlin.com.intellij.util.Processor;
import org.jetbrains.kotlin.com.intellij.util.containers.ContainerUtil;
import org.jetbrains.kotlin.js.translate.context.Namer;

/* JADX INFO: loaded from: classes3.dex */
public final class LambdaUtil {
    private static final Logger LOG = Logger.getInstance((Class<?>) LambdaUtil.class);

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = (i == 7 || i == 8 || i == 10 || i == 27 || i == 28) ? "@NotNull method %s.%s must not return null" : "Argument for @NotNull parameter '%s' of %s.%s must not be null";
        Object[] objArr = new Object[(i == 7 || i == 8 || i == 10 || i == 27 || i == 28) ? 2 : 3];
        switch (i) {
            case 1:
                objArr[0] = "result";
                break;
            case 2:
                objArr[0] = "method";
                break;
            case 3:
                objArr[0] = "resolveResult";
                break;
            case 4:
            case 5:
            case 6:
                objArr[0] = "psiClass";
                break;
            case 7:
            case 8:
            case 10:
            case 27:
            case 28:
                objArr[0] = "org/jetbrains/kotlin/com/intellij/psi/LambdaUtil";
                break;
            case 9:
                objArr[0] = "parentLambda";
                break;
            case 11:
                objArr[0] = "call";
                break;
            case 12:
            case 31:
                objArr[0] = CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD;
                break;
            case 13:
                objArr[0] = "targetType";
                break;
            case 14:
                objArr[0] = "producer";
                break;
            case 15:
                objArr[0] = "variable";
                break;
            case 16:
            case 29:
                objArr[0] = "expression";
                break;
            case 17:
            case 19:
            case 21:
            case 23:
                objArr[0] = "lambda";
                break;
            case 18:
                objArr[0] = "replacer";
                break;
            case 20:
                objArr[0] = "newFunctionSupplier";
                break;
            case 22:
                objArr[0] = "replacementText";
                break;
            case 24:
                objArr[0] = "newBodySupplier";
                break;
            case 25:
                objArr[0] = "lambdaReturnExpression";
                break;
            case 26:
                objArr[0] = "replacement";
                break;
            case 30:
                objArr[0] = "lambdaExpression";
                break;
            default:
                objArr[0] = "expr";
                break;
        }
        if (i == 7 || i == 8) {
            objArr[1] = "hasSubSignature";
        } else if (i == 10) {
            objArr[1] = "getReturnStatements";
        } else if (i == 27 || i == 28) {
            objArr[1] = "copyWithExpectedType";
        } else {
            objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/LambdaUtil";
        }
        switch (i) {
            case 1:
                objArr[2] = "getFunctionalInterfaceMethod";
                break;
            case 2:
            case 3:
                objArr[2] = "getSubstitutor";
                break;
            case 4:
                objArr[2] = "calcFunction";
                break;
            case 5:
                objArr[2] = "hasManyOwnAbstractMethods";
                break;
            case 6:
                objArr[2] = "hasManyInheritedAbstractMethods";
                break;
            case 7:
            case 8:
            case 10:
            case 27:
            case 28:
                break;
            case 9:
                objArr[2] = "getFunctionalInterfaceTypeByContainingLambda";
                break;
            case 11:
                objArr[2] = "copyTopLevelCall";
                break;
            case 12:
            case 13:
            case 14:
                objArr[2] = "performWithTargetType";
                break;
            case 15:
            case 16:
                objArr[2] = "createLambda";
                break;
            case 17:
            case 18:
            case 19:
            case 20:
            case 21:
            case 22:
                objArr[2] = "isSafeLambdaReplacement";
                break;
            case 23:
            case 24:
                objArr[2] = "isSafeLambdaBodyReplacement";
                break;
            case 25:
            case 26:
                objArr[2] = "isSafeLambdaReturnValueReplacement";
                break;
            case 29:
                objArr[2] = "resolveFunctionalInterfaceClass";
                break;
            case 30:
                objArr[2] = "specifyLambdaParameterTypes";
                break;
            case 31:
                objArr[2] = "getContainingClassOrLambda";
                break;
            default:
                objArr[2] = "getFunctionalInterfaceReturnType";
                break;
        }
        String str2 = String.format(str, objArr);
        if (i != 7 && i != 8 && i != 10 && i != 27 && i != 28) {
            throw new IllegalArgumentException(str2);
        }
        throw new IllegalStateException(str2);
    }

    public static PsiType getFunctionalInterfaceReturnType(PsiFunctionalExpression psiFunctionalExpression) {
        if (psiFunctionalExpression == null) {
            $$$reportNull$$$0(0);
        }
        return getFunctionalInterfaceReturnType(psiFunctionalExpression.getFunctionalInterfaceType());
    }

    public static PsiType getFunctionalInterfaceReturnType(PsiType psiType) {
        PsiClassType.ClassResolveResult classResolveResultResolveGenericsClassInType;
        PsiClass element;
        MethodSignature function;
        PsiType psiTypeNormalizeFunctionalType = normalizeFunctionalType(psiType);
        if (!(psiTypeNormalizeFunctionalType instanceof PsiClassType) || (element = (classResolveResultResolveGenericsClassInType = PsiUtil.resolveGenericsClassInType(psiTypeNormalizeFunctionalType)).getElement()) == null || (function = getFunction(element)) == null) {
            return null;
        }
        return classResolveResultResolveGenericsClassInType.getSubstitutor().substitute(getReturnType(element, function));
    }

    public static PsiMethod getFunctionalInterfaceMethod(PsiType psiType) {
        return getFunctionalInterfaceMethod(PsiUtil.resolveClassInClassTypeOnly(normalizeFunctionalType(psiType)));
    }

    public static PsiMethod getFunctionalInterfaceMethod(PsiElement psiElement) {
        if (psiElement instanceof PsiFunctionalExpression) {
            return getFunctionalInterfaceMethod(((PsiFunctionalExpression) psiElement).getFunctionalInterfaceType());
        }
        return null;
    }

    public static PsiMethod getFunctionalInterfaceMethod(PsiClassType.ClassResolveResult classResolveResult) {
        if (classResolveResult == null) {
            $$$reportNull$$$0(1);
        }
        return getFunctionalInterfaceMethod(classResolveResult.getElement());
    }

    public static PsiMethod getFunctionalInterfaceMethod(PsiClass psiClass) {
        MethodSignature function = getFunction(psiClass);
        if (function != null) {
            return getMethod(psiClass, function);
        }
        return null;
    }

    public static PsiType normalizeFunctionalType(PsiType psiType) {
        PsiType psiTypeExtractFunctionalConjunct;
        return (!(psiType instanceof PsiIntersectionType) || (psiTypeExtractFunctionalConjunct = extractFunctionalConjunct((PsiIntersectionType) psiType)) == null) ? psiType : psiTypeExtractFunctionalConjunct;
    }

    public static PsiSubstitutor getSubstitutor(PsiMethod psiMethod, PsiClassType.ClassResolveResult classResolveResult) {
        if (psiMethod == null) {
            $$$reportNull$$$0(2);
        }
        if (classResolveResult == null) {
            $$$reportNull$$$0(3);
        }
        PsiClass element = classResolveResult.getElement();
        Logger logger = LOG;
        logger.assertTrue(element != null);
        PsiClass containingClass = psiMethod.mo6092getContainingClass();
        logger.assertTrue(containingClass != null);
        PsiSubstitutor substitutor = classResolveResult.getSubstitutor();
        PsiSubstitutor superClassSubstitutor = TypeConversionUtil.getSuperClassSubstitutor(containingClass, element, PsiSubstitutor.EMPTY);
        for (PsiTypeParameter psiTypeParameter : superClassSubstitutor.getSubstitutionMap().keySet()) {
            substitutor = substitutor.put(psiTypeParameter, substitutor.substitute(superClassSubstitutor.substitute(psiTypeParameter)));
        }
        return substitutor;
    }

    public static boolean isFunctionalType(PsiType psiType) {
        return isFunctionalClass(PsiUtil.resolveClassInClassTypeOnly(normalizeFunctionalType(psiType)));
    }

    public static boolean isFunctionalClass(PsiClass psiClass) {
        return getFunction(psiClass) != null;
    }

    public static boolean isValidLambdaContext(PsiElement psiElement) {
        PsiSwitchBlock enclosingSwitchBlock;
        PsiSwitchExpression psiSwitchExpressionFindEnclosingExpression;
        PsiElement psiElementSkipParenthesizedExprUp = PsiUtil.skipParenthesizedExprUp(psiElement);
        if (psiElementSkipParenthesizedExprUp instanceof PsiTypeCastExpression) {
            return true;
        }
        while (true) {
            PsiElement psiElementSkipParenthesizedExprUp2 = PsiUtil.skipParenthesizedExprUp(psiElementSkipParenthesizedExprUp);
            if (isAssignmentOrInvocationContext(psiElementSkipParenthesizedExprUp2)) {
                return true;
            }
            if (psiElementSkipParenthesizedExprUp2 instanceof PsiConditionalExpression) {
                psiElementSkipParenthesizedExprUp = psiElementSkipParenthesizedExprUp2.getMyParent();
            } else if ((psiElementSkipParenthesizedExprUp2 instanceof PsiYieldStatement) && (psiSwitchExpressionFindEnclosingExpression = ((PsiYieldStatement) psiElementSkipParenthesizedExprUp2).findEnclosingExpression()) != null) {
                psiElementSkipParenthesizedExprUp = psiSwitchExpressionFindEnclosingExpression.getMyParent();
            } else {
                if (!(psiElementSkipParenthesizedExprUp2 instanceof PsiExpressionStatement)) {
                    return false;
                }
                PsiElement parent = psiElementSkipParenthesizedExprUp2.getMyParent();
                if (!(parent instanceof PsiSwitchLabeledRuleStatement) || (enclosingSwitchBlock = ((PsiSwitchLabeledRuleStatement) parent).getEnclosingSwitchBlock()) == null) {
                    return false;
                }
                psiElementSkipParenthesizedExprUp = enclosingSwitchBlock.getMyParent();
            }
        }
    }

    private static boolean isAssignmentOrInvocationContext(PsiElement psiElement) {
        return isAssignmentContext(psiElement) || isInvocationContext(psiElement);
    }

    private static boolean isInvocationContext(PsiElement psiElement) {
        return psiElement instanceof PsiExpressionList;
    }

    private static boolean isAssignmentContext(PsiElement psiElement) {
        return (psiElement instanceof PsiLambdaExpression) || (psiElement instanceof PsiReturnStatement) || (psiElement instanceof PsiAssignmentExpression) || ((psiElement instanceof PsiVariable) && !withInferredType((PsiVariable) psiElement)) || (psiElement instanceof PsiArrayInitializerExpression);
    }

    private static boolean withInferredType(PsiVariable psiVariable) {
        PsiTypeElement typeElement = psiVariable.getTypeElement();
        return typeElement != null && typeElement.isInferredType();
    }

    public static MethodSignature getFunction(PsiClass psiClass) {
        if (isPlainInterface(psiClass)) {
            return (MethodSignature) CachedValuesManager.getProjectPsiDependentCache(psiClass, new Function() { // from class: org.jetbrains.kotlin.com.intellij.psi.LambdaUtil$$ExternalSyntheticLambda7
                @Override // java.util.function.Function
                public final Object apply(Object obj) {
                    return LambdaUtil.calcFunction((PsiClass) obj);
                }
            });
        }
        return null;
    }

    private static boolean isPlainInterface(PsiClass psiClass) {
        return (psiClass == null || !psiClass.isInterface() || psiClass.isAnnotationType()) ? false : true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static MethodSignature calcFunction(PsiClass psiClass) {
        List<HierarchicalMethodSignature> listFindFunctionCandidates;
        if (psiClass == null) {
            $$$reportNull$$$0(4);
        }
        if (hasManyOwnAbstractMethods(psiClass) || hasManyInheritedAbstractMethods(psiClass) || (listFindFunctionCandidates = findFunctionCandidates(psiClass)) == null || listFindFunctionCandidates.size() != 1) {
            return null;
        }
        return listFindFunctionCandidates.get(0);
    }

    private static boolean hasManyOwnAbstractMethods(PsiClass psiClass) {
        if (psiClass == null) {
            $$$reportNull$$$0(5);
        }
        int i = 0;
        for (PsiMethod psiMethod : psiClass.getMethods()) {
            if (isDefinitelyAbstractInterfaceMethod(psiMethod) && (i = i + 1) > 1) {
                return true;
            }
        }
        return false;
    }

    private static boolean isDefinitelyAbstractInterfaceMethod(PsiMethod psiMethod) {
        return psiMethod.hasModifierProperty("abstract") && !isPublicObjectMethod(psiMethod.getName());
    }

    private static boolean isPublicObjectMethod(String str) {
        return Namer.EQUALS_METHOD_NAME.equals(str) || "hashCode".equals(str) || "toString".equals(str);
    }

    private static boolean hasManyInheritedAbstractMethods(PsiClass psiClass) {
        if (psiClass == null) {
            $$$reportNull$$$0(6);
        }
        final HashSet hashSet = new HashSet();
        final HashSet hashSet2 = new HashSet();
        InheritanceUtil.processSupers(psiClass, true, (Processor<? super PsiClass>) new Processor() { // from class: org.jetbrains.kotlin.com.intellij.psi.LambdaUtil$$ExternalSyntheticLambda10
            @Override // org.jetbrains.kotlin.com.intellij.util.Processor
            public final boolean process(Object obj) {
                return LambdaUtil.lambda$hasManyInheritedAbstractMethods$0(hashSet, hashSet2, (PsiClass) obj);
            }
        });
        hashSet.removeAll(hashSet2);
        return hashSet.size() > 1;
    }

    static /* synthetic */ boolean lambda$hasManyInheritedAbstractMethods$0(Set set, Set set2, PsiClass psiClass) {
        for (PsiMethod psiMethod : psiClass.getMethods()) {
            if (isDefinitelyAbstractInterfaceMethod(psiMethod)) {
                set.add(psiMethod.getName());
            } else if (psiMethod.hasModifierProperty("default")) {
                set2.add(psiMethod.getName());
            }
        }
        return true;
    }

    private static boolean overridesPublicObjectMethod(HierarchicalMethodSignature hierarchicalMethodSignature) {
        PsiMethod method;
        PsiClass containingClass;
        List<HierarchicalMethodSignature> superSignatures = hierarchicalMethodSignature.getSuperSignatures();
        if (superSignatures.isEmpty() && (containingClass = (method = hierarchicalMethodSignature.getMethod()).mo6092getContainingClass()) != null && CommonClassNames.JAVA_LANG_OBJECT.equals(containingClass.getQualifiedName()) && method.hasModifierProperty("public")) {
            return true;
        }
        Iterator<HierarchicalMethodSignature> it = superSignatures.iterator();
        while (it.hasNext()) {
            if (overridesPublicObjectMethod(it.next())) {
                return true;
            }
        }
        return false;
    }

    private static MethodSignature getMethodSignature(PsiMethod psiMethod, PsiClass psiClass, PsiClass psiClass2) {
        if (psiClass2 != null && psiClass2 != psiClass) {
            return psiMethod.getSignature(TypeConversionUtil.getSuperClassSubstitutor(psiClass2, psiClass, PsiSubstitutor.EMPTY));
        }
        return psiMethod.getSignature(PsiSubstitutor.EMPTY);
    }

    private static List<HierarchicalMethodSignature> hasSubSignature(List<HierarchicalMethodSignature> list) {
        for (HierarchicalMethodSignature hierarchicalMethodSignature : list) {
            Iterator<HierarchicalMethodSignature> it = list.iterator();
            while (true) {
                if (it.hasNext()) {
                    HierarchicalMethodSignature next = it.next();
                    if (hierarchicalMethodSignature.equals(next) || skipMethod(hierarchicalMethodSignature, next)) {
                    }
                } else {
                    List<HierarchicalMethodSignature> listSingletonList = Collections.singletonList(hierarchicalMethodSignature);
                    if (listSingletonList == null) {
                        $$$reportNull$$$0(7);
                    }
                    return listSingletonList;
                }
            }
        }
        if (list == null) {
            $$$reportNull$$$0(8);
        }
        return list;
    }

    private static boolean skipMethod(HierarchicalMethodSignature hierarchicalMethodSignature, HierarchicalMethodSignature hierarchicalMethodSignature2) {
        return (hierarchicalMethodSignature2.getTypeParameters().length == 0 || hierarchicalMethodSignature.getMethod().mo6092getContainingClass() == hierarchicalMethodSignature2.getMethod().mo6092getContainingClass()) ? false : true;
    }

    public static List<HierarchicalMethodSignature> findFunctionCandidates(PsiClass psiClass) {
        if (!isPlainInterface(psiClass)) {
            return null;
        }
        ArrayList arrayList = new ArrayList();
        Map<MethodSignature, Set<PsiMethod>> mapCollectOverrideEquivalents = PsiSuperMethodUtil.collectOverrideEquivalents(psiClass);
        for (HierarchicalMethodSignature hierarchicalMethodSignature : psiClass.getVisibleSignatures()) {
            PsiMethod method = hierarchicalMethodSignature.getMethod();
            if (method.hasModifierProperty("abstract") && !method.hasModifierProperty("static")) {
                Set<PsiMethod> set = mapCollectOverrideEquivalents.get(hierarchicalMethodSignature);
                if (set != null && set.size() > 1) {
                    Iterator<PsiMethod> it = set.iterator();
                    while (true) {
                        if (it.hasNext()) {
                            PsiMethod next = it.next();
                            if (next.hasModifierProperty("abstract") || MethodSignatureUtil.isSuperMethod(next, method)) {
                            }
                        }
                    }
                }
                if (!overridesPublicObjectMethod(hierarchicalMethodSignature)) {
                    arrayList.add(hierarchicalMethodSignature);
                }
            }
        }
        return hasSubSignature(arrayList);
    }

    private static PsiType getReturnType(PsiClass psiClass, MethodSignature methodSignature) {
        PsiClass containingClass;
        PsiMethod method = getMethod(psiClass, methodSignature);
        if (method == null || (containingClass = method.mo6092getContainingClass()) == null) {
            return null;
        }
        return TypeConversionUtil.getSuperClassSubstitutor(containingClass, psiClass, PsiSubstitutor.EMPTY).substitute(method.getReturnType());
    }

    private static PsiMethod getMethod(PsiClass psiClass, MethodSignature methodSignature) {
        if (methodSignature instanceof MethodSignatureBackedByPsiMethod) {
            return ((MethodSignatureBackedByPsiMethod) methodSignature).getMethod();
        }
        for (PsiMethod psiMethod : psiClass.findMethodsByName(methodSignature.getName(), true)) {
            if (MethodSignatureUtil.areSignaturesEqual(getMethodSignature(psiMethod, psiClass, psiMethod.mo6092getContainingClass()), methodSignature)) {
                return psiMethod;
            }
        }
        return null;
    }

    public static int getLambdaIdx(PsiExpressionList psiExpressionList, PsiElement psiElement) {
        PsiExpression[] expressions = psiExpressionList.getExpressions();
        for (int i = 0; i < expressions.length; i++) {
            if (PsiTreeUtil.isAncestor(expressions[i], psiElement, false)) {
                return i;
            }
        }
        return -1;
    }

    public static PsiType getFunctionalInterfaceType(PsiElement psiElement, boolean z) {
        PsiType psiTypeExtractFunctionalConjunct;
        PsiElement parent = psiElement.getMyParent();
        PsiElement psiElement2 = psiElement;
        while (true) {
            if ((parent instanceof PsiParenthesizedExpression) || (parent instanceof PsiConditionalExpression)) {
                if ((parent instanceof PsiConditionalExpression) && ((PsiConditionalExpression) parent).getCondition() == psiElement2) {
                    return PsiType.BOOLEAN;
                }
                psiElement2 = parent;
                parent = parent.getMyParent();
            } else {
                PsiType elementType = ThreadLocalTypes.getElementType(psiElement);
                if (elementType == null) {
                    elementType = ThreadLocalTypes.getElementType(psiElement2);
                }
                if (elementType != null) {
                    return elementType;
                }
                if (parent instanceof PsiArrayInitializerExpression) {
                    PsiType type = ((PsiArrayInitializerExpression) parent).getType();
                    if (type instanceof PsiArrayType) {
                        return ((PsiArrayType) type).getComponentType();
                    }
                } else {
                    if (parent instanceof PsiTypeCastExpression) {
                        PsiTypeElement castType = ((PsiTypeCastExpression) parent).getCastType();
                        PsiType type2 = castType != null ? castType.getType() : null;
                        return (!(type2 instanceof PsiIntersectionType) || (psiTypeExtractFunctionalConjunct = extractFunctionalConjunct((PsiIntersectionType) type2)) == null) ? type2 : psiTypeExtractFunctionalConjunct;
                    }
                    if (parent instanceof PsiVariable) {
                        return ((PsiVariable) parent).getType();
                    }
                    if ((parent instanceof PsiAssignmentExpression) && (psiElement instanceof PsiExpression) && !PsiUtil.isOnAssignmentLeftHand((PsiExpression) psiElement)) {
                        return ((PsiAssignmentExpression) parent).getLExpression().getType();
                    }
                    if (parent instanceof PsiExpressionList) {
                        PsiExpressionList psiExpressionList = (PsiExpressionList) parent;
                        int lambdaIdx = getLambdaIdx(psiExpressionList, psiElement);
                        if (lambdaIdx >= 0) {
                            PsiElement parent2 = psiExpressionList.getMyParent();
                            if (parent2 instanceof PsiAnonymousClass) {
                                parent2 = parent2.getMyParent();
                            }
                            if (parent2 instanceof PsiCall) {
                                PsiCall psiCall = (PsiCall) parent2;
                                JavaResolveResult diamondsAwareResolveResult = PsiDiamondType.getDiamondsAwareResolveResult(psiCall);
                                PsiElement element = diamondsAwareResolveResult.getElement();
                                LOG.assertTrue((MethodCandidateInfo.isOverloadCheck(psiCall.getArgumentList()) && (element instanceof PsiMethod) && ((PsiMethod) element).hasTypeParameters()) ? false : true);
                                return getSubstitutedType(psiElement, z, lambdaIdx, diamondsAwareResolveResult);
                            }
                        }
                    } else {
                        if (parent instanceof PsiReturnStatement) {
                            return PsiTypesUtil.getMethodReturnType(parent);
                        }
                        if (parent instanceof PsiLambdaExpression) {
                            return getFunctionalInterfaceTypeByContainingLambda((PsiLambdaExpression) parent);
                        }
                    }
                }
                PsiSwitchExpression psiSwitchExpression = (PsiSwitchExpression) PsiTreeUtil.getParentOfType(psiElement2, PsiSwitchExpression.class);
                if (psiSwitchExpression == null || !PsiUtil.getSwitchResultExpressions(psiSwitchExpression).contains(psiElement2)) {
                    return null;
                }
                return getFunctionalInterfaceType(psiSwitchExpression, z);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static PsiType getSubstitutedType(PsiElement psiElement, boolean z, int i, final JavaResolveResult javaResolveResult) {
        PsiElement element = javaResolveResult.getElement();
        if (!(element instanceof PsiMethod)) {
            return null;
        }
        PsiMethod psiMethod = (PsiMethod) element;
        final PsiParameter[] parameters = psiMethod.getParameterList().getParameters();
        final int iAdjustLambdaIdx = adjustLambdaIdx(i, psiMethod, parameters);
        if (iAdjustLambdaIdx >= parameters.length) {
            return null;
        }
        if (!z) {
            return getNormalizedType(parameters[iAdjustLambdaIdx]);
        }
        return (PsiType) PsiResolveHelper.ourGraphGuard.doPreventingRecursion(psiElement, !MethodCandidateInfo.isOverloadCheck(), new Computable() { // from class: org.jetbrains.kotlin.com.intellij.psi.LambdaUtil$$ExternalSyntheticLambda6
            @Override // org.jetbrains.kotlin.com.intellij.openapi.util.Computable
            public final Object compute() {
                return LambdaUtil.lambda$getSubstitutedType$1(parameters, iAdjustLambdaIdx, javaResolveResult);
            }
        });
    }

    static /* synthetic */ PsiType lambda$getSubstitutedType$1(PsiParameter[] psiParameterArr, int i, JavaResolveResult javaResolveResult) {
        PsiType normalizedType = getNormalizedType(psiParameterArr[i]);
        if ((javaResolveResult instanceof MethodCandidateInfo) && ((MethodCandidateInfo) javaResolveResult).isRawSubstitution()) {
            return TypeConversionUtil.erasure(normalizedType);
        }
        return javaResolveResult.getSubstitutor().substitute(normalizedType);
    }

    public static boolean processParentOverloads(final PsiFunctionalExpression psiFunctionalExpression, Consumer<? super PsiType> consumer) {
        PsiExpressionList psiExpressionList;
        final int lambdaIdx;
        JavaResolveResult[] constructorCandidates;
        LOG.assertTrue(PsiTypesUtil.getExpectedTypeByParent(psiFunctionalExpression) == null);
        PsiElement parent = psiFunctionalExpression.getMyParent();
        PsiFunctionalExpression psiFunctionalExpression2 = psiFunctionalExpression;
        while (true) {
            if (!(parent instanceof PsiParenthesizedExpression) && !(parent instanceof PsiConditionalExpression)) {
                break;
            }
            if (parent instanceof PsiConditionalExpression) {
                PsiConditionalExpression psiConditionalExpression = (PsiConditionalExpression) parent;
                if (psiConditionalExpression.getThenExpression() != psiFunctionalExpression2 && psiConditionalExpression.getElseExpression() != psiFunctionalExpression2) {
                    break;
                }
            }
            psiFunctionalExpression2 = parent;
            parent = parent.getMyParent();
        }
        if ((parent instanceof PsiExpressionList) && (lambdaIdx = getLambdaIdx((psiExpressionList = (PsiExpressionList) parent), psiFunctionalExpression)) > -1) {
            PsiElement parent2 = psiExpressionList.getMyParent();
            if (parent2 instanceof PsiAnonymousClass) {
                parent2 = parent2.getMyParent();
            }
            if (parent2 instanceof PsiMethodCallExpression) {
                constructorCandidates = ((PsiMethodCallExpression) parent2).getMethodExpression().multiResolve(true);
            } else {
                constructorCandidates = parent2 instanceof PsiConstructorCall ? getConstructorCandidates((PsiConstructorCall) parent2) : null;
            }
            if (constructorCandidates != null) {
                HashSet hashSet = new HashSet();
                for (final JavaResolveResult javaResolveResult : constructorCandidates) {
                    PsiType psiType = (PsiType) MethodCandidateInfo.ourOverloadGuard.doPreventingRecursion(psiFunctionalExpression, false, new Computable() { // from class: org.jetbrains.kotlin.com.intellij.psi.LambdaUtil$$ExternalSyntheticLambda2
                        @Override // org.jetbrains.kotlin.com.intellij.openapi.util.Computable
                        public final Object compute() {
                            return LambdaUtil.getSubstitutedType(psiFunctionalExpression, true, lambdaIdx, javaResolveResult);
                        }
                    });
                    if (psiType != null && hashSet.add(psiType)) {
                        consumer.consume(psiType);
                    }
                }
                return true;
            }
        }
        return false;
    }

    /* JADX WARN: Code duplicated, block: B:14:0x003b  */
    private static JavaResolveResult[] getConstructorCandidates(PsiConstructorCall psiConstructorCall) {
        PsiClass containingClass;
        PsiClassType psiClassTypeCreateType;
        JavaPsiFacade javaPsiFacade = JavaPsiFacade.getInstance(psiConstructorCall.getProject());
        PsiExpressionList argumentList = psiConstructorCall.getArgumentList();
        if (argumentList != null) {
            if (psiConstructorCall instanceof PsiNewExpression) {
                PsiJavaCodeReferenceElement classReference = ((PsiNewExpression) psiConstructorCall).getClassReference();
                if (classReference != null) {
                    psiClassTypeCreateType = javaPsiFacade.getElementFactory().createType(classReference);
                } else {
                    psiClassTypeCreateType = null;
                }
            } else if (!(psiConstructorCall instanceof PsiEnumConstant) || (containingClass = ((PsiEnumConstant) psiConstructorCall).mo6092getContainingClass()) == null) {
                psiClassTypeCreateType = null;
            } else {
                psiClassTypeCreateType = javaPsiFacade.getElementFactory().createType(containingClass);
            }
            if (psiClassTypeCreateType != null) {
                return javaPsiFacade.getResolveHelper().multiResolveConstructor(psiClassTypeCreateType, argumentList, psiConstructorCall);
            }
        }
        return null;
    }

    private static PsiType extractFunctionalConjunct(PsiIntersectionType psiIntersectionType) {
        MethodSignature function;
        PsiType psiType = null;
        MethodSignature methodSignature = null;
        for (PsiType psiType2 : psiIntersectionType.getConjuncts()) {
            PsiClass psiClassResolveClassInClassTypeOnly = PsiUtil.resolveClassInClassTypeOnly(psiType2);
            if (!(psiClassResolveClassInClassTypeOnly instanceof PsiTypeParameter) && (function = getFunction(psiClassResolveClassInClassTypeOnly)) != null) {
                if (methodSignature == null) {
                    methodSignature = function;
                } else if (!MethodSignatureUtil.areSignaturesEqual(methodSignature, function)) {
                    return null;
                }
                psiType = psiType2;
            }
        }
        return psiType;
    }

    private static PsiType getFunctionalInterfaceTypeByContainingLambda(PsiLambdaExpression psiLambdaExpression) {
        if (psiLambdaExpression == null) {
            $$$reportNull$$$0(9);
        }
        PsiType functionalInterfaceType = psiLambdaExpression.getFunctionalInterfaceType();
        if (functionalInterfaceType != null) {
            return getFunctionalInterfaceReturnType(functionalInterfaceType);
        }
        return null;
    }

    private static int adjustLambdaIdx(int i, PsiMethod psiMethod, PsiParameter[] psiParameterArr) {
        return (!psiMethod.isVarArgs() || i < psiParameterArr.length) ? i : psiParameterArr.length - 1;
    }

    private static PsiType getNormalizedType(PsiParameter psiParameter) {
        PsiType type = psiParameter.getType();
        return type instanceof PsiEllipsisType ? ((PsiEllipsisType) type).getComponentType() : type;
    }

    public static boolean notInferredType(PsiType psiType) {
        return (psiType instanceof PsiMethodReferenceType) || (psiType instanceof PsiLambdaExpressionType) || (psiType instanceof PsiLambdaParameterType);
    }

    public static PsiReturnStatement[] getReturnStatements(PsiLambdaExpression psiLambdaExpression) {
        PsiElement body = psiLambdaExpression.getBody();
        PsiReturnStatement[] psiReturnStatementArrFindReturnStatements = body instanceof PsiCodeBlock ? PsiUtil.findReturnStatements((PsiCodeBlock) body) : PsiReturnStatement.EMPTY_ARRAY;
        if (psiReturnStatementArrFindReturnStatements == null) {
            $$$reportNull$$$0(10);
        }
        return psiReturnStatementArrFindReturnStatements;
    }

    public static List<PsiExpression> getReturnExpressions(PsiLambdaExpression psiLambdaExpression) {
        PsiElement body = psiLambdaExpression.getBody();
        if (body instanceof PsiExpression) {
            return Collections.singletonList((PsiExpression) body);
        }
        ArrayList arrayList = new ArrayList();
        for (PsiReturnStatement psiReturnStatement : getReturnStatements(psiLambdaExpression)) {
            PsiExpression returnValue = psiReturnStatement.getReturnValue();
            if (returnValue != null) {
                arrayList.add(returnValue);
            }
        }
        return arrayList;
    }

    public static boolean isExpressionStatementExpression(PsiElement psiElement) {
        return (psiElement instanceof PsiAssignmentExpression) || PsiUtil.isIncrementDecrementOperation(psiElement) || (psiElement instanceof PsiMethodCallExpression) || ((psiElement instanceof PsiNewExpression) && !((PsiNewExpression) psiElement).isArrayCreation()) || ((psiElement instanceof PsiReferenceExpression) && !psiElement.getIsPhysical());
    }

    public static PsiExpression extractSingleExpressionFromBody(PsiElement psiElement) {
        if (psiElement instanceof PsiExpression) {
            return (PsiExpression) psiElement;
        }
        if (psiElement instanceof PsiCodeBlock) {
            PsiStatement[] statements = ((PsiCodeBlock) psiElement).getStatements();
            if (statements.length == 1) {
                PsiStatement psiStatement = statements[0];
                if (psiStatement instanceof PsiReturnStatement) {
                    return ((PsiReturnStatement) psiStatement).getReturnValue();
                }
                if (psiStatement instanceof PsiExpressionStatement) {
                    return ((PsiExpressionStatement) psiStatement).getExpression();
                }
                if (psiStatement instanceof PsiBlockStatement) {
                    return extractSingleExpressionFromBody(((PsiBlockStatement) psiStatement).getCodeBlock());
                }
            }
        } else {
            if (psiElement instanceof PsiBlockStatement) {
                return extractSingleExpressionFromBody(((PsiBlockStatement) psiElement).getCodeBlock());
            }
            if (psiElement instanceof PsiExpressionStatement) {
                return ((PsiExpressionStatement) psiElement).getExpression();
            }
        }
        return null;
    }

    public static boolean isPotentiallyCompatibleWithTypeParameter(PsiFunctionalExpression psiFunctionalExpression, PsiExpressionList psiExpressionList, PsiMethod psiMethod) {
        int lambdaIdx;
        PsiCallExpression psiCallExpression;
        if ((Registry.is("JDK8042508.bug.fixed", false) || ((psiCallExpression = (PsiCallExpression) PsiTreeUtil.getParentOfType(psiExpressionList, PsiCallExpression.class)) != null && psiCallExpression.getTypeArguments().length <= 0)) && (lambdaIdx = getLambdaIdx(psiExpressionList, psiFunctionalExpression)) >= 0) {
            PsiParameter[] parameters = psiMethod.getParameterList().getParameters();
            PsiClass psiClassResolveClassInType = PsiUtil.resolveClassInType(parameters[Math.min(lambdaIdx, parameters.length - 1)].getType());
            if ((psiClassResolveClassInType instanceof PsiTypeParameter) && ((PsiTypeParameter) psiClassResolveClassInType).getOwner() == psiMethod) {
                return true;
            }
        }
        return false;
    }

    public static Map<PsiElement, String> checkReturnTypeCompatible(PsiLambdaExpression psiLambdaExpression, PsiType psiType) {
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        if (PsiType.VOID.equals(psiType)) {
            PsiElement body = psiLambdaExpression.getBody();
            if (body instanceof PsiCodeBlock) {
                Iterator<PsiExpression> it = getReturnExpressions(psiLambdaExpression).iterator();
                while (it.hasNext()) {
                    linkedHashMap.put(it.next(), JavaPsiBundle.message("unexpected.return.value", new Object[0]));
                }
            } else if (body instanceof PsiExpression) {
                try {
                    if (!PsiUtil.isStatement(JavaPsiFacade.getElementFactory(body.getProject()).createStatementFromText(body.getText(), body))) {
                        PsiType type = ((PsiExpression) body).getType();
                        if (PsiType.VOID.equals(type)) {
                            linkedHashMap.put(body, JavaPsiBundle.message("lambda.body.must.be.a.statement.expression", new Object[0]));
                        } else {
                            linkedHashMap.put(body, JavaPsiBundle.message("bad.return.type.in.lambda.expression1", (type == PsiType.NULL || type == null) ? "<null>" : type.getPresentableText()));
                        }
                    }
                } catch (IncorrectOperationException unused) {
                }
            }
        } else if (psiType != null) {
            List<PsiExpression> returnExpressions = getReturnExpressions(psiLambdaExpression);
            for (PsiExpression psiExpression : returnExpressions) {
                PsiType type2 = psiExpression.getType();
                if (type2 != null && !psiType.isAssignableFrom(type2)) {
                    linkedHashMap.put(psiExpression, JavaPsiBundle.message("bad.return.type.in.lambda.expression", type2.getPresentableText(), psiType.getPresentableText()));
                }
            }
            PsiReturnStatement[] returnStatements = getReturnStatements(psiLambdaExpression);
            if (returnStatements.length > returnExpressions.size()) {
                for (PsiReturnStatement psiReturnStatement : returnStatements) {
                    if (psiReturnStatement.getReturnValue() == null) {
                        linkedHashMap.put(psiReturnStatement, JavaPsiBundle.message("missing.return.value.lambda", new Object[0]));
                    }
                }
            } else if (returnExpressions.isEmpty() && !psiLambdaExpression.isVoidCompatible()) {
                linkedHashMap.put(psiLambdaExpression, JavaPsiBundle.message("missing.return.value.lambda", new Object[0]));
            }
        }
        if (linkedHashMap.isEmpty()) {
            return null;
        }
        return linkedHashMap;
    }

    public static PsiType getLambdaParameterFromType(PsiType psiType, int i) {
        PsiClassType.ClassResolveResult classResolveResultResolveGenericsClassInType = PsiUtil.resolveGenericsClassInType(psiType);
        PsiMethod functionalInterfaceMethod = getFunctionalInterfaceMethod(psiType);
        if (functionalInterfaceMethod == null) {
            return null;
        }
        PsiParameter[] parameters = functionalInterfaceMethod.getParameterList().getParameters();
        if (i < parameters.length) {
            return getSubstitutor(functionalInterfaceMethod, classResolveResultResolveGenericsClassInType).substitute(parameters[i].getType());
        }
        return null;
    }

    /* JADX WARN: Code duplicated, block: B:21:0x006d  */
    public static PsiCall treeWalkUp(PsiElement psiElement) {
        PsiExpressionList argumentList;
        PsiCall psiCall;
        PsiElement parentOfType = PsiTreeUtil.getParentOfType(psiElement, (Class<? extends PsiElement>[]) new Class[]{PsiExpressionList.class, PsiLambdaExpression.class, PsiConditionalExpression.class, PsiSwitchExpression.class, PsiAssignmentExpression.class, PsiCodeBlock.class, PsiCall.class});
        PsiCall psiCall2 = null;
        while (!(parentOfType instanceof PsiCall) && !(parentOfType instanceof PsiAssignmentExpression)) {
            PsiLambdaExpression psiLambdaExpression = (PsiLambdaExpression) PsiTreeUtil.getParentOfType(parentOfType, PsiLambdaExpression.class);
            if (parentOfType instanceof PsiCodeBlock) {
                if (psiLambdaExpression != null) {
                    Iterator<PsiExpression> it = getReturnExpressions(psiLambdaExpression).iterator();
                    boolean zIsAncestor = false;
                    while (it.hasNext()) {
                        zIsAncestor |= PsiTreeUtil.isAncestor(it.next(), psiElement, false);
                    }
                    if (!zIsAncestor || ThreadLocalTypes.hasBindingFor(psiLambdaExpression)) {
                        break;
                    }
                    if (!(parentOfType instanceof PsiConditionalExpression)) {
                        break;
                    }
                    break;
                    break;
                }
                break;
            }
            if (((!(parentOfType instanceof PsiConditionalExpression) || (parentOfType instanceof PsiSwitchExpression)) && !PsiPolyExpressionUtil.isPolyExpression((PsiExpression) parentOfType)) || (((parentOfType instanceof PsiLambdaExpression) && ThreadLocalTypes.hasBindingFor(parentOfType)) || (psiCall = (PsiCall) PsiTreeUtil.getParentOfType(parentOfType, PsiCall.class, false, (Class<? extends PsiElement>[]) new Class[]{PsiVariable.class, PsiMethod.class, PsiAssignmentExpression.class, PsiTypeCastExpression.class})) == null || !PsiTreeUtil.isAncestor(psiCall.getArgumentList(), parentOfType, false) || MethodCandidateInfo.isOverloadCheck(psiCall.getArgumentList()) || (psiLambdaExpression != null && ThreadLocalTypes.hasBindingFor(psiLambdaExpression)))) {
                break;
            }
            if (!(psiCall instanceof PsiExpression) || !PsiPolyExpressionUtil.isPolyExpression((PsiExpression) psiCall)) {
                psiCall2 = psiCall;
                break;
            }
            parentOfType = PsiTreeUtil.getParentOfType(parentOfType.getMyParent(), (Class<? extends PsiElement>[]) new Class[]{PsiExpressionList.class, PsiLambdaExpression.class, PsiAssignmentExpression.class, PsiCodeBlock.class});
            psiCall2 = psiCall;
        }
        if (psiCall2 == null || (argumentList = psiCall2.getArgumentList()) == null) {
            return null;
        }
        LOG.assertTrue(!MethodCandidateInfo.isOverloadCheck(argumentList));
        return psiCall2;
    }

    public static PsiCall copyTopLevelCall(PsiCall psiCall) {
        String name2;
        PsiMethod psiMethodResolveMethod;
        if (psiCall == null) {
            $$$reportNull$$$0(11);
        }
        if (psiCall instanceof PsiEnumConstant) {
            PsiClass containingClass = ((PsiEnumConstant) psiCall).mo6092getContainingClass();
            if (containingClass == null || (name2 = containingClass.getName()) == null || (psiMethodResolveMethod = psiCall.resolveMethod()) == null) {
                return null;
            }
            PsiClass psiClass = (PsiClass) psiCall.getContainingFile().copy().add(JavaPsiFacade.getElementFactory(psiCall.getProject()).createEnum(name2));
            psiClass.add(psiMethodResolveMethod);
            return (PsiCall) psiClass.add(psiCall);
        }
        PsiElement psiElement = psiCall;
        while (true) {
            PsiElement psiElementSkipParenthesizedExprUp = PsiUtil.skipParenthesizedExprUp(psiElement.getMyParent());
            if (!(psiElementSkipParenthesizedExprUp instanceof PsiConditionalExpression) || PsiTreeUtil.isAncestor(((PsiConditionalExpression) psiElementSkipParenthesizedExprUp).getCondition(), psiElement, false)) {
                break;
            }
            psiElement = psiElementSkipParenthesizedExprUp;
        }
        PsiType expectedTypeByParent = PsiTypesUtil.getExpectedTypeByParent(psiElement);
        if (PsiTypesUtil.isDenotableType(expectedTypeByParent, psiCall)) {
            return (PsiCall) copyWithExpectedType(psiCall, expectedTypeByParent);
        }
        return (PsiCall) psiCall.copy();
    }

    public static <T> T performWithSubstitutedParameterBounds(final PsiTypeParameter[] psiTypeParameterArr, final PsiSubstitutor psiSubstitutor, final Supplier<? extends T> supplier) {
        return (T) ThreadLocalTypes.performWithTypes(new Function() { // from class: org.jetbrains.kotlin.com.intellij.psi.LambdaUtil$$ExternalSyntheticLambda1
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return LambdaUtil.lambda$performWithSubstitutedParameterBounds$3(psiTypeParameterArr, psiSubstitutor, supplier, (ThreadLocalTypes) obj);
            }
        });
    }

    static /* synthetic */ Object lambda$performWithSubstitutedParameterBounds$3(PsiTypeParameter[] psiTypeParameterArr, final PsiSubstitutor psiSubstitutor, Supplier supplier, ThreadLocalTypes threadLocalTypes) {
        for (PsiTypeParameter psiTypeParameter : psiTypeParameterArr) {
            PsiClassType[] extendsListTypes = psiTypeParameter.getExtendsListTypes();
            if (extendsListTypes.length > 0) {
                Objects.requireNonNull(psiSubstitutor);
                threadLocalTypes.forceType(psiTypeParameter, PsiIntersectionType.createIntersection(false, (PsiType[]) ContainerUtil.map(extendsListTypes, new org.jetbrains.kotlin.com.intellij.util.Function() { // from class: org.jetbrains.kotlin.com.intellij.psi.LambdaUtil$$ExternalSyntheticLambda0
                    @Override // org.jetbrains.kotlin.com.intellij.util.Function
                    public final Object fun(Object obj) {
                        return psiSubstitutor.substitute((PsiClassType) obj);
                    }
                }).toArray(PsiType.EMPTY_ARRAY)));
            }
        }
        return supplier.get();
    }

    public static <T> T performWithTargetType(final PsiElement psiElement, final PsiType psiType, final Supplier<? extends T> supplier) {
        if (psiElement == null) {
            $$$reportNull$$$0(12);
        }
        if (psiType == null) {
            $$$reportNull$$$0(13);
        }
        if (supplier == null) {
            $$$reportNull$$$0(14);
        }
        return (T) ThreadLocalTypes.performWithTypes(new Function() { // from class: org.jetbrains.kotlin.com.intellij.psi.LambdaUtil$$ExternalSyntheticLambda3
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return LambdaUtil.lambda$performWithTargetType$4(psiElement, psiType, supplier, (ThreadLocalTypes) obj);
            }
        });
    }

    static /* synthetic */ Object lambda$performWithTargetType$4(PsiElement psiElement, PsiType psiType, Supplier supplier, ThreadLocalTypes threadLocalTypes) {
        threadLocalTypes.forceType(psiElement, psiType);
        return supplier.get();
    }

    public static String createLambda(PsiVariable psiVariable, PsiExpression psiExpression) {
        if (psiVariable == null) {
            $$$reportNull$$$0(15);
        }
        if (psiExpression == null) {
            $$$reportNull$$$0(16);
        }
        return psiVariable.getName() + " -> " + psiExpression.getText();
    }

    public static boolean isIdentityLambda(PsiLambdaExpression psiLambdaExpression) {
        PsiParameterList parameterList = psiLambdaExpression.getParameterList();
        if (parameterList.getParametersCount() != 1) {
            return false;
        }
        PsiExpression psiExpressionSkipParenthesizedExprDown = PsiUtil.skipParenthesizedExprDown(extractSingleExpressionFromBody(psiLambdaExpression.getBody()));
        return (psiExpressionSkipParenthesizedExprDown instanceof PsiReferenceExpression) && ((PsiReferenceExpression) psiExpressionSkipParenthesizedExprDown).isReferenceTo(parameterList.getParameters()[0]);
    }

    private static boolean isSafeLambdaReplacement(PsiLambdaExpression psiLambdaExpression, Function<? super PsiLambdaExpression, ? extends PsiExpression> function) {
        JavaResolveResult javaResolveResultResolveMethodGenerics;
        PsiElement element;
        PsiLambdaExpression psiLambdaExpression2;
        PsiExpression psiExpressionApply;
        if (psiLambdaExpression == null) {
            $$$reportNull$$$0(17);
        }
        if (function == null) {
            $$$reportNull$$$0(18);
        }
        PsiElement body = psiLambdaExpression.getBody();
        if (body == null) {
            return false;
        }
        PsiCall psiCallTreeWalkUp = treeWalkUp(body);
        if (psiCallTreeWalkUp == null || (element = (javaResolveResultResolveMethodGenerics = psiCallTreeWalkUp.resolveMethodGenerics()).getElement()) == null) {
            return true;
        }
        String inferenceErrorMessage = javaResolveResultResolveMethodGenerics instanceof MethodCandidateInfo ? ((MethodCandidateInfo) javaResolveResultResolveMethodGenerics).getInferenceErrorMessage() : null;
        Object obj = new Object();
        PsiTreeUtil.mark(psiLambdaExpression, obj);
        PsiType type = psiCallTreeWalkUp instanceof PsiExpression ? ((PsiExpression) psiCallTreeWalkUp).getType() : null;
        PsiCall psiCallCopyTopLevelCall = copyTopLevelCall(psiCallTreeWalkUp);
        if (psiCallCopyTopLevelCall == null || (psiLambdaExpression2 = (PsiLambdaExpression) ObjectUtils.tryCast(PsiTreeUtil.releaseMark(psiCallCopyTopLevelCall, obj), PsiLambdaExpression.class)) == null || (psiExpressionApply = function.apply(psiLambdaExpression2)) == null) {
            return false;
        }
        JavaResolveResult javaResolveResultResolveMethodGenerics2 = psiCallCopyTopLevelCall.resolveMethodGenerics();
        if (!element.getManager().areElementsEquivalent(javaResolveResultResolveMethodGenerics2.getElement(), element)) {
            return false;
        }
        if (!Objects.equals(inferenceErrorMessage, javaResolveResultResolveMethodGenerics2 instanceof MethodCandidateInfo ? ((MethodCandidateInfo) javaResolveResultResolveMethodGenerics2).getInferenceErrorMessage() : null)) {
            return false;
        }
        if (psiExpressionApply instanceof PsiFunctionalExpression) {
            PsiType functionalInterfaceType = ((PsiFunctionalExpression) psiExpressionApply).getFunctionalInterfaceType();
            if (functionalInterfaceType == null) {
                return false;
            }
            PsiType functionalInterfaceType2 = psiLambdaExpression.getFunctionalInterfaceType();
            if (functionalInterfaceType2 != null && !functionalInterfaceType.getCanonicalText().equals(functionalInterfaceType2.getCanonicalText())) {
                return false;
            }
        }
        if (!(type instanceof PsiClassType) || ((PsiClassType) type).isRaw() || psiLambdaExpression.hasFormalParameterTypes()) {
            return true;
        }
        PsiExpression psiExpressionExtractSingleExpressionFromBody = extractSingleExpressionFromBody(body);
        return ((psiExpressionExtractSingleExpressionFromBody instanceof PsiMethodCallExpression) && PsiTypesUtil.isUncheckedCall(((PsiMethodCallExpression) psiExpressionExtractSingleExpressionFromBody).resolveMethodGenerics())) ? false : true;
    }

    public static boolean isSafeLambdaReplacement(PsiLambdaExpression psiLambdaExpression, final Supplier<? extends PsiExpression> supplier) {
        if (psiLambdaExpression == null) {
            $$$reportNull$$$0(19);
        }
        if (supplier == null) {
            $$$reportNull$$$0(20);
        }
        return isSafeLambdaReplacement(psiLambdaExpression, (Function<? super PsiLambdaExpression, ? extends PsiExpression>) new Function() { // from class: org.jetbrains.kotlin.com.intellij.psi.LambdaUtil$$ExternalSyntheticLambda4
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return LambdaUtil.lambda$isSafeLambdaReplacement$5(supplier, (PsiLambdaExpression) obj);
            }
        });
    }

    static /* synthetic */ PsiExpression lambda$isSafeLambdaReplacement$5(Supplier supplier, PsiLambdaExpression psiLambdaExpression) {
        PsiExpression psiExpression = (PsiExpression) supplier.get();
        if (psiExpression == null) {
            return null;
        }
        return (PsiExpression) psiLambdaExpression.replace(psiExpression);
    }

    public static boolean isSafeLambdaReplacement(final PsiLambdaExpression psiLambdaExpression, final String str) {
        if (psiLambdaExpression == null) {
            $$$reportNull$$$0(21);
        }
        if (str == null) {
            $$$reportNull$$$0(22);
        }
        return isSafeLambdaReplacement(psiLambdaExpression, (Supplier<? extends PsiExpression>) new Supplier() { // from class: org.jetbrains.kotlin.com.intellij.psi.LambdaUtil$$ExternalSyntheticLambda8
            @Override // java.util.function.Supplier
            public final Object get() {
                PsiLambdaExpression psiLambdaExpression2 = psiLambdaExpression;
                return JavaPsiFacade.getElementFactory(psiLambdaExpression2.getProject()).createExpressionFromText(str, psiLambdaExpression2.getMyParent());
            }
        });
    }

    public static boolean isSafeLambdaBodyReplacement(PsiLambdaExpression psiLambdaExpression, final Supplier<? extends PsiElement> supplier) {
        if (psiLambdaExpression == null) {
            $$$reportNull$$$0(23);
        }
        if (supplier == null) {
            $$$reportNull$$$0(24);
        }
        return isSafeLambdaReplacement(psiLambdaExpression, (Function<? super PsiLambdaExpression, ? extends PsiExpression>) new Function() { // from class: org.jetbrains.kotlin.com.intellij.psi.LambdaUtil$$ExternalSyntheticLambda5
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return LambdaUtil.lambda$isSafeLambdaBodyReplacement$7(supplier, (PsiLambdaExpression) obj);
            }
        });
    }

    static /* synthetic */ PsiExpression lambda$isSafeLambdaBodyReplacement$7(Supplier supplier, PsiLambdaExpression psiLambdaExpression) {
        PsiElement body = psiLambdaExpression.getBody();
        PsiElement psiElement = (PsiElement) supplier.get();
        if (body == null || psiElement == null) {
            return null;
        }
        body.replace(psiElement);
        return psiLambdaExpression;
    }

    public static boolean isSafeLambdaReturnValueReplacement(final PsiExpression psiExpression, final PsiExpression psiExpression2) {
        PsiLambdaExpression psiLambdaExpression;
        if (psiExpression == null) {
            $$$reportNull$$$0(25);
        }
        if (psiExpression2 == null) {
            $$$reportNull$$$0(26);
        }
        return !((psiExpression.getMyParent() instanceof PsiReturnStatement) || (psiExpression.getMyParent() instanceof PsiLambdaExpression)) || (psiLambdaExpression = (PsiLambdaExpression) PsiTreeUtil.getParentOfType((PsiElement) psiExpression, PsiLambdaExpression.class, true, (Class<? extends PsiElement>[]) new Class[]{PsiMethod.class})) == null || isSafeLambdaBodyReplacement(psiLambdaExpression, new Supplier() { // from class: org.jetbrains.kotlin.com.intellij.psi.LambdaUtil$$ExternalSyntheticLambda9
            @Override // java.util.function.Supplier
            public final Object get() {
                return LambdaUtil.lambda$isSafeLambdaReturnValueReplacement$8(psiExpression, psiExpression2);
            }
        });
    }

    static /* synthetic */ PsiElement lambda$isSafeLambdaReturnValueReplacement$8(PsiExpression psiExpression, PsiExpression psiExpression2) {
        PsiElement body;
        PsiLambdaExpression psiLambdaExpression = (PsiLambdaExpression) PsiTreeUtil.getParentOfType(psiExpression, PsiLambdaExpression.class);
        if (psiLambdaExpression == null || (body = psiLambdaExpression.getBody()) == null) {
            return null;
        }
        Object obj = new Object();
        PsiTreeUtil.mark(psiExpression, obj);
        PsiElement psiElementCopy = body.copy();
        PsiElement psiElementReleaseMark = PsiTreeUtil.releaseMark(psiElementCopy, obj);
        if (psiElementReleaseMark == null) {
            return null;
        }
        if (psiElementReleaseMark == psiElementCopy) {
            return psiElementReleaseMark.replace(psiExpression2);
        }
        psiElementReleaseMark.replace(psiExpression2);
        return psiElementCopy;
    }

    public static PsiElement copyWithExpectedType(PsiElement psiElement, PsiType psiType) {
        String canonicalText = psiType.getCanonicalText();
        if (!PsiUtil.isLanguageLevel8OrHigher(psiElement)) {
            PsiArrayInitializerExpression arrayInitializer = ((PsiNewExpression) JavaPsiFacade.getElementFactory(psiElement.getProject()).createExpressionFromText("new " + canonicalText + "[]{0}", psiElement)).getArrayInitializer();
            LOG.assertTrue(arrayInitializer != null);
            PsiElement psiElementReplace = arrayInitializer.getInitializers()[0].replace(psiElement);
            if (psiElementReplace == null) {
                $$$reportNull$$$0(27);
            }
            return psiElementReplace;
        }
        PsiLambdaExpression psiLambdaExpression = (PsiLambdaExpression) ((PsiTypeCastExpression) JavaPsiFacade.getElementFactory(psiElement.getProject()).createExpressionFromText("(java.util.concurrent.Callable<" + canonicalText + ">)() -> x", psiElement)).getOperand();
        Logger logger = LOG;
        logger.assertTrue(psiLambdaExpression != null);
        PsiElement body = psiLambdaExpression.getBody();
        logger.assertTrue(body instanceof PsiExpression);
        PsiElement psiElementReplace2 = body.replace(psiElement);
        if (psiElementReplace2 == null) {
            $$$reportNull$$$0(28);
        }
        return psiElementReplace2;
    }

    public static boolean isCapturingLambda(PsiLambdaExpression psiLambdaExpression) {
        PsiElement body = psiLambdaExpression.getBody();
        if (body == null) {
            return false;
        }
        C1CapturingLambdaVisitor c1CapturingLambdaVisitor = new C1CapturingLambdaVisitor(psiLambdaExpression);
        body.accept(c1CapturingLambdaVisitor);
        return c1CapturingLambdaVisitor.capturing;
    }

    /* JADX INFO: renamed from: org.jetbrains.kotlin.com.intellij.psi.LambdaUtil$1CapturingLambdaVisitor, reason: invalid class name */
    class C1CapturingLambdaVisitor extends JavaRecursiveElementWalkingVisitor {
        boolean capturing;
        final /* synthetic */ PsiLambdaExpression val$lambda;

        C1CapturingLambdaVisitor(PsiLambdaExpression psiLambdaExpression) {
            this.val$lambda = psiLambdaExpression;
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.JavaRecursiveElementWalkingVisitor, org.jetbrains.kotlin.com.intellij.psi.JavaElementVisitor
        public void visitReferenceExpression(PsiReferenceExpression psiReferenceExpression) {
            super.visitReferenceExpression(psiReferenceExpression);
            if (psiReferenceExpression instanceof PsiMethodReferenceExpression) {
                return;
            }
            if (!(psiReferenceExpression.getMyParent() instanceof PsiMethodCallExpression) || psiReferenceExpression.getQualifierExpression() == null) {
                PsiElement psiElementResolve = psiReferenceExpression.resolve();
                if (!(psiElementResolve instanceof PsiModifierListOwner) || ((PsiModifierListOwner) psiElementResolve).hasModifierProperty("static") || PsiTreeUtil.isAncestor(this.val$lambda, psiElementResolve, true)) {
                    return;
                }
                if ((psiElementResolve instanceof PsiClass) && ((PsiClass) psiElementResolve).mo6092getContainingClass() == null) {
                    return;
                }
                this.capturing = true;
                stopWalking();
            }
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.JavaElementVisitor
        public void visitSuperExpression(PsiSuperExpression psiSuperExpression) {
            this.capturing = true;
            stopWalking();
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.JavaElementVisitor
        public void visitThisExpression(PsiThisExpression psiThisExpression) {
            this.capturing = true;
            stopWalking();
        }
    }

    public static PsiClass resolveFunctionalInterfaceClass(PsiFunctionalExpression psiFunctionalExpression) {
        if (psiFunctionalExpression == null) {
            $$$reportNull$$$0(29);
        }
        PsiClass psiClassResolveClassInClassTypeOnly = PsiUtil.resolveClassInClassTypeOnly(psiFunctionalExpression.getGroundTargetType(getFunctionalInterfaceType(psiFunctionalExpression, false)));
        return psiClassResolveClassInClassTypeOnly instanceof PsiTypeParameter ? PsiUtil.resolveClassInClassTypeOnly(psiFunctionalExpression.getFunctionalInterfaceType()) : psiClassResolveClassInClassTypeOnly;
    }

    public static String createLambdaParameterListWithFormalTypes(PsiType psiType, PsiLambdaExpression psiLambdaExpression, boolean z) {
        PsiType psiTypeSubstitute;
        PsiClassType.ClassResolveResult classResolveResultResolveGenericsClassInType = PsiUtil.resolveGenericsClassInType(psiType);
        StringBuilder sb = new StringBuilder("(");
        PsiMethod functionalInterfaceMethod = getFunctionalInterfaceMethod(psiType);
        if (functionalInterfaceMethod == null) {
            return null;
        }
        PsiParameter[] parameters = functionalInterfaceMethod.getParameterList().getParameters();
        PsiParameter[] parameters2 = psiLambdaExpression.getParameterList().getParameters();
        if (parameters.length != parameters2.length) {
            return null;
        }
        PsiSubstitutor substitutor = getSubstitutor(functionalInterfaceMethod, classResolveResultResolveGenericsClassInType);
        for (int i = 0; i < parameters.length; i++) {
            PsiParameter psiParameter = parameters2[i];
            PsiTypeElement typeElement = psiParameter.getTypeElement();
            if (typeElement != null && !typeElement.isInferredType()) {
                psiTypeSubstitute = typeElement.getType();
            } else {
                psiTypeSubstitute = substitutor.substitute(parameters[i].getType());
                if (!PsiTypesUtil.isDenotableType(psiTypeSubstitute, psiLambdaExpression)) {
                    return null;
                }
            }
            for (PsiAnnotation psiAnnotation : psiParameter.getAnnotations()) {
                if (!AnnotationTargetUtil.isTypeAnnotation(psiAnnotation)) {
                    sb.append(psiAnnotation.getText()).append(StringUtil.SPACE);
                }
            }
            sb.append(z ? psiTypeSubstitute.getPresentableText(true) : psiTypeSubstitute.getCanonicalText(true)).append(" ").append(psiParameter.getName());
            if (i < parameters.length - 1) {
                sb.append(", ");
            }
        }
        sb.append(Constant.AFTER_QUTO);
        return sb.toString();
    }

    public static PsiParameterList specifyLambdaParameterTypes(PsiLambdaExpression psiLambdaExpression) {
        return specifyLambdaParameterTypes(psiLambdaExpression.getFunctionalInterfaceType(), psiLambdaExpression);
    }

    public static PsiParameterList specifyLambdaParameterTypes(PsiType psiType, PsiLambdaExpression psiLambdaExpression) {
        if (psiLambdaExpression == null) {
            $$$reportNull$$$0(30);
        }
        String strCreateLambdaParameterListWithFormalTypes = createLambdaParameterListWithFormalTypes(psiType, psiLambdaExpression, false);
        if (strCreateLambdaParameterListWithFormalTypes == null) {
            return null;
        }
        return (PsiParameterList) JavaCodeStyleManager.getInstance(psiLambdaExpression.getProject()).shortenClassReferences(psiLambdaExpression.getParameterList().replace(JavaPsiFacade.getElementFactory(psiLambdaExpression.getProject()).createMethodFromText("void foo" + strCreateLambdaParameterListWithFormalTypes, psiLambdaExpression).getParameterList()));
    }

    public static PsiElement getContainingClassOrLambda(PsiElement psiElement) {
        PsiElement parentOfType;
        if (psiElement == null) {
            $$$reportNull$$$0(31);
        }
        while (true) {
            parentOfType = PsiTreeUtil.getParentOfType(psiElement, (Class<? extends PsiElement>[]) new Class[]{PsiClass.class, PsiLambdaExpression.class});
            if (!(parentOfType instanceof PsiAnonymousClass) || !PsiTreeUtil.isAncestor(((PsiAnonymousClass) parentOfType).getArgumentList(), psiElement, false)) {
                break;
            }
            psiElement = parentOfType;
        }
        return parentOfType;
    }
}
