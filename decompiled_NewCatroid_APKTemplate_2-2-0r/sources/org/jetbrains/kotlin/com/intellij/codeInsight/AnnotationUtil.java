package org.jetbrains.kotlin.com.intellij.codeInsight;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.IntFunction;
import java.util.function.Predicate;
import java.util.stream.Stream;
import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;
import org.jetbrains.kotlin.com.intellij.openapi.project.Project;
import org.jetbrains.kotlin.com.intellij.openapi.util.Condition;
import org.jetbrains.kotlin.com.intellij.psi.HierarchicalMethodSignature;
import org.jetbrains.kotlin.com.intellij.psi.JavaPsiFacade;
import org.jetbrains.kotlin.com.intellij.psi.LambdaUtil;
import org.jetbrains.kotlin.com.intellij.psi.PsiAnnotation;
import org.jetbrains.kotlin.com.intellij.psi.PsiClass;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiLambdaExpression;
import org.jetbrains.kotlin.com.intellij.psi.PsiLocalVariable;
import org.jetbrains.kotlin.com.intellij.psi.PsiMethod;
import org.jetbrains.kotlin.com.intellij.psi.PsiModifierList;
import org.jetbrains.kotlin.com.intellij.psi.PsiModifierListOwner;
import org.jetbrains.kotlin.com.intellij.psi.PsiNameValuePair;
import org.jetbrains.kotlin.com.intellij.psi.PsiParameter;
import org.jetbrains.kotlin.com.intellij.psi.PsiParameterList;
import org.jetbrains.kotlin.com.intellij.psi.PsiResolveHelper;
import org.jetbrains.kotlin.com.intellij.psi.util.CachedValueProvider;
import org.jetbrains.kotlin.com.intellij.psi.util.CachedValuesManager;
import org.jetbrains.kotlin.com.intellij.psi.util.InheritanceUtil;
import org.jetbrains.kotlin.com.intellij.psi.util.PsiModificationTracker;
import org.jetbrains.kotlin.com.intellij.psi.util.PsiUtilCore;
import org.jetbrains.kotlin.com.intellij.util.Consumer;
import org.jetbrains.kotlin.com.intellij.util.Function;
import org.jetbrains.kotlin.com.intellij.util.Processor;
import org.jetbrains.kotlin.com.intellij.util.Processors;
import org.jetbrains.kotlin.com.intellij.util.SmartList;
import org.jetbrains.kotlin.com.intellij.util.containers.ConcurrentFactoryMap;
import org.jetbrains.kotlin.com.intellij.util.containers.ContainerUtil;
import org.jetbrains.kotlin.js.translate.context.Namer;

/* JADX INFO: loaded from: classes3.dex */
public class AnnotationUtil {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private static final String[] SIMPLE_NAMES = {"NotNull", "Nullable", "NonNls", "PropertyKey", "TestOnly", "Language", "Identifier", "Pattern", "PrintFormat", "RegExp", "Subst"};

    /* JADX WARN: Code duplicated, block: B:11:0x001f  */
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str;
        int i2;
        if (i != 7 && i != 17 && i != 43 && i != 60 && i != 68 && i != 69) {
            switch (i) {
                case 13:
                case 14:
                case 15:
                    str = "@NotNull method %s.%s must not return null";
                    break;
                default:
                    str = "Argument for @NotNull parameter '%s' of %s.%s must not be null";
                    break;
            }
        } else {
            str = "@NotNull method %s.%s must not return null";
        }
        if (i != 7 && i != 17 && i != 43 && i != 60 && i != 68 && i != 69) {
            switch (i) {
                case 13:
                case 14:
                case 15:
                    i2 = 2;
                    break;
                default:
                    i2 = 3;
                    break;
            }
        } else {
            i2 = 2;
        }
        Object[] objArr = new Object[i2];
        switch (i) {
            case 5:
            case 8:
            case 18:
            case 20:
            case 22:
            case 30:
            case 32:
            case 34:
            case 52:
            case 62:
            case 64:
            case 66:
                objArr[0] = "listOwner";
                break;
            case 6:
            case 9:
            case 11:
            case 12:
            case 19:
            case 21:
            case 23:
            default:
                objArr[0] = "annotationNames";
                break;
            case 7:
            case 13:
            case 14:
            case 15:
            case 17:
            case 43:
            case 60:
            case 68:
            case 69:
                objArr[0] = "org/jetbrains/kotlin/com/intellij/codeInsight/AnnotationUtil";
                break;
            case 10:
            case 16:
            case 44:
                objArr[0] = CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD;
                break;
            case 24:
            case 26:
                objArr[0] = "result";
                break;
            case 25:
                objArr[0] = "parameter";
                break;
            case 27:
                objArr[0] = "signature";
                break;
            case 28:
                objArr[0] = "place";
                break;
            case 29:
                objArr[0] = "resolveHelper";
                break;
            case 31:
            case 39:
            case 59:
            case 63:
                objArr[0] = "annotations";
                break;
            case 33:
                objArr[0] = "annotationFqn";
                break;
            case 35:
            case 38:
            case 65:
            case 67:
                objArr[0] = "annotationFQN";
                break;
            case 36:
            case 37:
                objArr[0] = "elt";
                break;
            case 40:
                objArr[0] = "pair";
                break;
            case 41:
            case 42:
                objArr[0] = "owner";
                break;
            case 45:
            case 46:
            case 54:
                objArr[0] = "annotation";
                break;
            case 47:
            case 48:
            case 49:
            case 50:
                objArr[0] = "anno";
                break;
            case 51:
                objArr[0] = "attrValue";
                break;
            case 53:
                objArr[0] = "annotationClass";
                break;
            case 55:
                objArr[0] = "parameterList";
                break;
            case 56:
                objArr[0] = "valueMap";
                break;
            case 57:
                objArr[0] = "annotations1";
                break;
            case 58:
                objArr[0] = "annotations2";
                break;
            case 61:
                objArr[0] = Namer.METADATA_SIMPLE_NAME;
                break;
        }
        if (i == 7) {
            objArr[1] = "findAllAnnotations";
        } else if (i == 17) {
            objArr[1] = "getSuperAnnotationOwners";
        } else if (i == 43) {
            objArr[1] = "getAllAnnotations";
        } else if (i == 60) {
            objArr[1] = "buildAnnotationMap";
        } else if (i != 68 && i != 69) {
            switch (i) {
                case 13:
                case 14:
                case 15:
                    objArr[1] = "findAnnotations";
                    break;
                default:
                    objArr[1] = "org/jetbrains/kotlin/com/intellij/codeInsight/AnnotationUtil";
                    break;
            }
        } else {
            objArr[1] = "arrayAttributeValues";
        }
        switch (i) {
            case 5:
            case 6:
                objArr[2] = "findAllAnnotations";
                break;
            case 7:
            case 13:
            case 14:
            case 15:
            case 17:
            case 43:
            case 60:
            case 68:
            case 69:
                break;
            case 8:
            case 9:
                objArr[2] = "findOwnAnnotations";
                break;
            case 10:
            case 11:
                objArr[2] = "findNonCodeAnnotations";
                break;
            case 12:
                objArr[2] = "findAnnotations";
                break;
            case 16:
                objArr[2] = "getSuperAnnotationOwners";
                break;
            case 18:
            case 19:
            case 20:
            case 21:
            case 52:
            case 53:
                objArr[2] = "findAnnotationInHierarchy";
                break;
            case 22:
            case 23:
                objArr[2] = "findAnnotationAndOwnerInHierarchy";
                break;
            case 24:
            case 25:
                objArr[2] = "collectSuperParameters";
                break;
            case 26:
            case 27:
            case 28:
            case 29:
                objArr[2] = "collectSuperMethods";
                break;
            case 30:
            case 31:
            case 32:
            case 33:
            case 34:
            case 35:
            case 62:
            case 63:
            case 64:
            case 65:
            case 66:
            case 67:
                objArr[2] = "isAnnotated";
                break;
            case 36:
            case 37:
            case 38:
                objArr[2] = "isAnnotatingApplicable";
                break;
            case 39:
                objArr[2] = "checkAnnotatedUsingPatterns";
                break;
            case 40:
                objArr[2] = "getAnnotationMethod";
                break;
            case 41:
            case 42:
                objArr[2] = "getAllAnnotations";
                break;
            case 44:
                objArr[2] = "isInsideAnnotation";
                break;
            case 45:
                objArr[2] = "isInferredAnnotation";
                break;
            case 46:
                objArr[2] = "isExternalAnnotation";
                break;
            case 47:
            case 51:
                objArr[2] = "getStringAttributeValue";
                break;
            case 48:
                objArr[2] = "getBooleanAttributeValue";
                break;
            case 49:
                objArr[2] = "getLongAttributeValue";
                break;
            case 50:
                objArr[2] = "getDeclaredStringAttributeValue";
                break;
            case 54:
                objArr[2] = "findDeclaredAttribute";
                break;
            case 55:
            case 56:
                objArr[2] = "fillValueMap";
                break;
            case 57:
            case 58:
                objArr[2] = "equal";
                break;
            case 59:
                objArr[2] = "buildAnnotationMap";
                break;
            case 61:
                objArr[2] = "isJetbrainsAnnotation";
                break;
            default:
                objArr[2] = "findAnnotation";
                break;
        }
        String str2 = String.format(str, objArr);
        if (i != 7 && i != 17 && i != 43 && i != 60 && i != 68 && i != 69) {
            switch (i) {
                case 13:
                case 14:
                case 15:
                    break;
                default:
                    throw new IllegalArgumentException(str2);
            }
        }
        throw new IllegalStateException(str2);
    }

    public static PsiAnnotation findAnnotation(PsiModifierListOwner psiModifierListOwner, String... strArr) {
        if (strArr == null) {
            $$$reportNull$$$0(0);
        }
        return findAnnotation(psiModifierListOwner, false, strArr);
    }

    public static PsiAnnotation findAnnotation(PsiModifierListOwner psiModifierListOwner, boolean z, String... strArr) {
        if (strArr == null) {
            $$$reportNull$$$0(1);
        }
        if (strArr.length == 0) {
            return null;
        }
        return findAnnotation(psiModifierListOwner, (Collection<String>) (strArr.length == 1 ? Collections.singleton(strArr[0]) : ContainerUtil.newHashSet(strArr)), z);
    }

    public static PsiAnnotation findAnnotation(PsiModifierListOwner psiModifierListOwner, Collection<String> collection, boolean z) {
        if (collection == null) {
            $$$reportNull$$$0(4);
        }
        if (psiModifierListOwner == null) {
            return null;
        }
        List<PsiAnnotation> listFindAllAnnotations = findAllAnnotations(psiModifierListOwner, collection, z);
        if (listFindAllAnnotations.isEmpty()) {
            return null;
        }
        return listFindAllAnnotations.get(0);
    }

    public static List<PsiAnnotation> findAllAnnotations(PsiModifierListOwner psiModifierListOwner, Collection<String> collection, boolean z) {
        if (psiModifierListOwner == null) {
            $$$reportNull$$$0(5);
        }
        if (collection == null) {
            $$$reportNull$$$0(6);
        }
        List<PsiAnnotation> listFindOwnAnnotations = findOwnAnnotations(psiModifierListOwner, collection);
        List<PsiAnnotation> smartList = null;
        List<PsiAnnotation> listFindNonCodeAnnotations = z ? null : findNonCodeAnnotations(psiModifierListOwner, collection);
        if (listFindOwnAnnotations != null || listFindNonCodeAnnotations != null) {
            smartList = new SmartList<>();
            if (listFindOwnAnnotations != null) {
                smartList.addAll(listFindOwnAnnotations);
            }
            if (listFindNonCodeAnnotations != null) {
                smartList.addAll(listFindNonCodeAnnotations);
            }
        }
        if (smartList == null) {
            smartList = Collections.emptyList();
        }
        if (smartList == null) {
            $$$reportNull$$$0(7);
        }
        return smartList;
    }

    private static List<PsiAnnotation> findOwnAnnotations(PsiModifierListOwner psiModifierListOwner, Iterable<String> iterable) {
        if (psiModifierListOwner == null) {
            $$$reportNull$$$0(8);
        }
        if (iterable == null) {
            $$$reportNull$$$0(9);
        }
        PsiModifierList psiModifierListMo6095getModifierList = psiModifierListOwner.mo6095getModifierList();
        SmartList smartList = null;
        if (psiModifierListMo6095getModifierList == null) {
            return null;
        }
        for (final PsiAnnotation psiAnnotation : psiModifierListMo6095getModifierList.getAnnotations()) {
            Objects.requireNonNull(psiAnnotation);
            if (ContainerUtil.exists(iterable, new Condition() { // from class: org.jetbrains.kotlin.com.intellij.codeInsight.AnnotationUtil$$ExternalSyntheticLambda5
                @Override // org.jetbrains.kotlin.com.intellij.openapi.util.Condition
                public final boolean value(Object obj) {
                    return psiAnnotation.hasQualifiedName((String) obj);
                }
            }) && isApplicableToDeclaration(psiAnnotation, psiModifierListMo6095getModifierList)) {
                if (smartList == null) {
                    smartList = new SmartList();
                }
                smartList.add(psiAnnotation);
            }
        }
        return smartList;
    }

    private static boolean isApplicableToDeclaration(PsiAnnotation psiAnnotation, PsiModifierList psiModifierList) {
        PsiAnnotation.TargetType[] targetsForLocation = AnnotationTargetUtil.getTargetsForLocation(psiModifierList);
        return targetsForLocation.length == 0 || AnnotationTargetUtil.findAnnotationTarget(psiAnnotation, (PsiAnnotation.TargetType[]) Stream.of((Object[]) targetsForLocation).filter(new Predicate() { // from class: org.jetbrains.kotlin.com.intellij.codeInsight.AnnotationUtil$$ExternalSyntheticLambda1
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return AnnotationUtil.lambda$isApplicableToDeclaration$0((PsiAnnotation.TargetType) obj);
            }
        }).toArray(new IntFunction() { // from class: org.jetbrains.kotlin.com.intellij.codeInsight.AnnotationUtil$$ExternalSyntheticLambda2
            @Override // java.util.function.IntFunction
            public final Object apply(int i) {
                return AnnotationUtil.lambda$isApplicableToDeclaration$1(i);
            }
        })) != null;
    }

    static /* synthetic */ boolean lambda$isApplicableToDeclaration$0(PsiAnnotation.TargetType targetType) {
        return targetType != PsiAnnotation.TargetType.TYPE_USE;
    }

    static /* synthetic */ PsiAnnotation.TargetType[] lambda$isApplicableToDeclaration$1(int i) {
        return new PsiAnnotation.TargetType[i];
    }

    private static List<PsiAnnotation> findNonCodeAnnotations(PsiModifierListOwner psiModifierListOwner, Collection<String> collection) {
        if (psiModifierListOwner == null) {
            $$$reportNull$$$0(10);
        }
        if (collection == null) {
            $$$reportNull$$$0(11);
        }
        if (psiModifierListOwner instanceof PsiLocalVariable) {
            return null;
        }
        final PsiModifierListOwner psiModifierListOwnerNormalize = AnnotationCacheOwnerNormalizer.normalize(psiModifierListOwner);
        return (List) ((Map) CachedValuesManager.getCachedValue((PsiElement) psiModifierListOwnerNormalize, new CachedValueProvider() { // from class: org.jetbrains.kotlin.com.intellij.codeInsight.AnnotationUtil$$ExternalSyntheticLambda3
            @Override // org.jetbrains.kotlin.com.intellij.psi.util.CachedValueProvider
            public final CachedValueProvider.Result compute() {
                return CachedValueProvider.Result.create(ConcurrentFactoryMap.createMap(new Function() { // from class: org.jetbrains.kotlin.com.intellij.codeInsight.AnnotationUtil$$ExternalSyntheticLambda0
                    @Override // org.jetbrains.kotlin.com.intellij.util.Function
                    public final Object fun(Object obj) {
                        return AnnotationUtil.lambda$findNonCodeAnnotations$2(psiModifierListOwner, (Collection) obj);
                    }
                }), PsiModificationTracker.MODIFICATION_COUNT);
            }
        })).get(collection);
    }

    static /* synthetic */ List lambda$findNonCodeAnnotations$2(PsiModifierListOwner psiModifierListOwner, Collection collection) {
        PsiUtilCore.ensureValid(psiModifierListOwner);
        Project project = psiModifierListOwner.getProject();
        ExternalAnnotationsManager externalAnnotationsManager = ExternalAnnotationsManager.getInstance(project);
        Iterator it = collection.iterator();
        SmartList smartList = null;
        while (it.hasNext()) {
            List<PsiAnnotation> listFindExternalAnnotations = externalAnnotationsManager.findExternalAnnotations(psiModifierListOwner, (String) it.next());
            if (!listFindExternalAnnotations.isEmpty()) {
                if (smartList == null) {
                    smartList = new SmartList();
                }
                smartList.addAll(listFindExternalAnnotations);
            }
        }
        InferredAnnotationsManager inferredAnnotationsManager = InferredAnnotationsManager.getInstance(project);
        Iterator it2 = collection.iterator();
        while (it2.hasNext()) {
            PsiAnnotation psiAnnotationFindInferredAnnotation = inferredAnnotationsManager.findInferredAnnotation(psiModifierListOwner, (String) it2.next());
            if (psiAnnotationFindInferredAnnotation != null) {
                if (smartList == null) {
                    smartList = new SmartList();
                }
                smartList.add(psiAnnotationFindInferredAnnotation);
            }
        }
        return smartList;
    }

    public static <T extends PsiModifierListOwner> List<T> getSuperAnnotationOwners(T t) {
        if (t == null) {
            $$$reportNull$$$0(16);
        }
        final PsiModifierListOwner psiModifierListOwnerNormalize = AnnotationCacheOwnerNormalizer.normalize(t);
        List<T> list = (List) CachedValuesManager.getCachedValue((PsiElement) psiModifierListOwnerNormalize, new CachedValueProvider() { // from class: org.jetbrains.kotlin.com.intellij.codeInsight.AnnotationUtil$$ExternalSyntheticLambda6
            @Override // org.jetbrains.kotlin.com.intellij.psi.util.CachedValueProvider
            public final CachedValueProvider.Result compute() {
                return AnnotationUtil.lambda$getSuperAnnotationOwners$4(psiModifierListOwnerNormalize);
            }
        });
        if (list == null) {
            $$$reportNull$$$0(17);
        }
        return list;
    }

    static /* synthetic */ CachedValueProvider.Result lambda$getSuperAnnotationOwners$4(PsiModifierListOwner psiModifierListOwner) {
        List listAsList;
        LinkedHashSet linkedHashSet = new LinkedHashSet();
        if (psiModifierListOwner instanceof PsiMethod) {
            if (!psiModifierListOwner.hasModifierProperty("static")) {
                collectSuperMethods(linkedHashSet, ((PsiMethod) psiModifierListOwner).getHierarchicalMethodSignature(), psiModifierListOwner, JavaPsiFacade.getInstance(psiModifierListOwner.getProject()).getResolveHelper());
            }
        } else if (psiModifierListOwner instanceof PsiClass) {
            InheritanceUtil.processSupers((PsiClass) psiModifierListOwner, false, (Processor<? super PsiClass>) Processors.cancelableCollectProcessor(linkedHashSet));
        } else if (psiModifierListOwner instanceof PsiParameter) {
            collectSuperParameters(linkedHashSet, (PsiParameter) psiModifierListOwner);
        }
        if (linkedHashSet.isEmpty()) {
            listAsList = Collections.emptyList();
        } else {
            listAsList = Arrays.asList((PsiModifierListOwner[]) linkedHashSet.toArray(new PsiModifierListOwner[0]));
        }
        return CachedValueProvider.Result.create(listAsList, PsiModificationTracker.MODIFICATION_COUNT);
    }

    static final class AnnotationAndOwner {
        final PsiAnnotation annotation;
        final PsiModifierListOwner owner;

        private static /* synthetic */ void $$$reportNull$$$0(int i) {
            Object[] objArr = new Object[3];
            if (i != 1) {
                objArr[0] = "owner";
            } else {
                objArr[0] = "annotation";
            }
            objArr[1] = "org/jetbrains/kotlin/com/intellij/codeInsight/AnnotationUtil$AnnotationAndOwner";
            objArr[2] = "<init>";
            throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
        }

        AnnotationAndOwner(PsiModifierListOwner psiModifierListOwner, PsiAnnotation psiAnnotation) {
            if (psiModifierListOwner == null) {
                $$$reportNull$$$0(0);
            }
            if (psiAnnotation == null) {
                $$$reportNull$$$0(1);
            }
            this.owner = psiModifierListOwner;
            this.annotation = psiAnnotation;
        }
    }

    static AnnotationAndOwner findAnnotationAndOwnerInHierarchy(PsiModifierListOwner psiModifierListOwner, Set<String> set, boolean z) {
        if (psiModifierListOwner == null) {
            $$$reportNull$$$0(22);
        }
        if (set == null) {
            $$$reportNull$$$0(23);
        }
        PsiAnnotation psiAnnotationFindAnnotation = findAnnotation(psiModifierListOwner, set, z);
        if (psiAnnotationFindAnnotation != null) {
            return new AnnotationAndOwner(psiModifierListOwner, psiAnnotationFindAnnotation);
        }
        for (PsiModifierListOwner psiModifierListOwner2 : getSuperAnnotationOwners(psiModifierListOwner)) {
            PsiAnnotation psiAnnotationFindAnnotation2 = findAnnotation(psiModifierListOwner2, set, z);
            if (psiAnnotationFindAnnotation2 != null) {
                return new AnnotationAndOwner(psiModifierListOwner2, psiAnnotationFindAnnotation2);
            }
        }
        return null;
    }

    private static void collectSuperParameters(final Set<? super PsiModifierListOwner> set, PsiParameter psiParameter) {
        if (set == null) {
            $$$reportNull$$$0(24);
        }
        if (psiParameter == null) {
            $$$reportNull$$$0(25);
        }
        PsiElement parent = psiParameter.getLightParent();
        if (parent instanceof PsiParameterList) {
            final int parameterIndex = ((PsiParameterList) parent).getParameterIndex(psiParameter);
            Consumer consumer = new Consumer() { // from class: org.jetbrains.kotlin.com.intellij.codeInsight.AnnotationUtil$$ExternalSyntheticLambda4
                @Override // org.jetbrains.kotlin.com.intellij.util.Consumer
                public final void consume(Object obj) {
                    AnnotationUtil.lambda$collectSuperParameters$5(parameterIndex, set, (PsiMethod) obj);
                }
            };
            PsiElement lightOwner = parent.getLightParent();
            if (lightOwner instanceof PsiLambdaExpression) {
                PsiMethod functionalInterfaceMethod = LambdaUtil.getFunctionalInterfaceMethod(((PsiLambdaExpression) lightOwner).getFunctionalInterfaceType());
                if (functionalInterfaceMethod != null) {
                    consumer.consume(functionalInterfaceMethod);
                    Iterator it = getSuperAnnotationOwners(functionalInterfaceMethod).iterator();
                    while (it.hasNext()) {
                        consumer.consume((PsiMethod) it.next());
                    }
                    return;
                }
                return;
            }
            if (lightOwner instanceof PsiMethod) {
                Iterator it2 = getSuperAnnotationOwners((PsiMethod) lightOwner).iterator();
                while (it2.hasNext()) {
                    consumer.consume((PsiMethod) it2.next());
                }
            }
        }
    }

    static /* synthetic */ void lambda$collectSuperParameters$5(int i, Set set, PsiMethod psiMethod) {
        PsiParameter[] parameters = psiMethod.getParameterList().getParameters();
        if (i < parameters.length) {
            set.add(parameters[i]);
        }
    }

    private static void collectSuperMethods(Set<? super PsiModifierListOwner> set, HierarchicalMethodSignature hierarchicalMethodSignature, PsiElement psiElement, PsiResolveHelper psiResolveHelper) {
        if (set == null) {
            $$$reportNull$$$0(26);
        }
        if (hierarchicalMethodSignature == null) {
            $$$reportNull$$$0(27);
        }
        if (psiElement == null) {
            $$$reportNull$$$0(28);
        }
        if (psiResolveHelper == null) {
            $$$reportNull$$$0(29);
        }
        for (HierarchicalMethodSignature hierarchicalMethodSignature2 : hierarchicalMethodSignature.getSuperSignatures()) {
            PsiMethod method = hierarchicalMethodSignature2.getMethod();
            if (psiResolveHelper.isAccessible(method, psiElement, null) && set.add(method)) {
                collectSuperMethods(set, hierarchicalMethodSignature2, psiElement, psiResolveHelper);
            }
        }
    }

    public static boolean isInferredAnnotation(PsiAnnotation psiAnnotation) {
        if (psiAnnotation == null) {
            $$$reportNull$$$0(45);
        }
        return InferredAnnotationsManager.getInstance(psiAnnotation.getProject()).isInferredAnnotation(psiAnnotation);
    }

    public static boolean isExternalAnnotation(PsiAnnotation psiAnnotation) {
        if (psiAnnotation == null) {
            $$$reportNull$$$0(46);
        }
        return ExternalAnnotationsManager.getInstance(psiAnnotation.getProject()).isExternalAnnotation(psiAnnotation);
    }

    public static PsiNameValuePair findDeclaredAttribute(PsiAnnotation psiAnnotation, String str) {
        if (psiAnnotation == null) {
            $$$reportNull$$$0(54);
        }
        if ("value".equals(str)) {
            str = null;
        }
        for (PsiNameValuePair psiNameValuePair : psiAnnotation.getParameterList().getAttributes()) {
            String name2 = psiNameValuePair.getName();
            if (Objects.equals(name2, str) || (str == null && "value".equals(name2))) {
                return psiNameValuePair;
            }
        }
        return null;
    }
}
