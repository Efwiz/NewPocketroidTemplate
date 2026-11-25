package org.jetbrains.kotlin.com.intellij.psi.impl;

import com.huawei.hms.ml.language.common.utils.LanguageCodeUtil;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.com.intellij.openapi.diagnostic.Logger;
import org.jetbrains.kotlin.com.intellij.openapi.progress.ProgressManager;
import org.jetbrains.kotlin.com.intellij.openapi.util.Pair;
import org.jetbrains.kotlin.com.intellij.psi.CommonClassNames;
import org.jetbrains.kotlin.com.intellij.psi.HierarchicalMethodSignature;
import org.jetbrains.kotlin.com.intellij.psi.PsiClass;
import org.jetbrains.kotlin.com.intellij.psi.PsiClassType;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiMethod;
import org.jetbrains.kotlin.com.intellij.psi.PsiSubstitutor;
import org.jetbrains.kotlin.com.intellij.psi.PsiType;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.HierarchicalMethodSignatureImpl;
import org.jetbrains.kotlin.com.intellij.psi.search.GlobalSearchScope;
import org.jetbrains.kotlin.com.intellij.psi.search.searches.DeepestSuperMethodsSearch;
import org.jetbrains.kotlin.com.intellij.psi.search.searches.SuperMethodsSearch;
import org.jetbrains.kotlin.com.intellij.psi.util.CachedValueProvider;
import org.jetbrains.kotlin.com.intellij.psi.util.CachedValuesManager;
import org.jetbrains.kotlin.com.intellij.psi.util.InheritanceUtil;
import org.jetbrains.kotlin.com.intellij.psi.util.MethodSignature;
import org.jetbrains.kotlin.com.intellij.psi.util.MethodSignatureBackedByPsiMethod;
import org.jetbrains.kotlin.com.intellij.psi.util.MethodSignatureUtil;
import org.jetbrains.kotlin.com.intellij.psi.util.PsiCacheKey;
import org.jetbrains.kotlin.com.intellij.psi.util.PsiModificationTracker;
import org.jetbrains.kotlin.com.intellij.psi.util.PsiSuperMethodUtil;
import org.jetbrains.kotlin.com.intellij.psi.util.PsiUtil;
import org.jetbrains.kotlin.com.intellij.psi.util.PsiUtilCore;
import org.jetbrains.kotlin.com.intellij.psi.util.TypeConversionUtil;
import org.jetbrains.kotlin.com.intellij.util.Function;
import org.jetbrains.kotlin.com.intellij.util.NotNullFunction;
import org.jetbrains.kotlin.com.intellij.util.Processor;
import org.jetbrains.kotlin.com.intellij.util.SmartList;
import org.jetbrains.kotlin.com.intellij.util.containers.ConcurrentFactoryMap;
import org.jetbrains.kotlin.com.intellij.util.containers.hash.EqualityPolicy;
import org.jetbrains.kotlin.com.intellij.util.containers.hash.LinkedHashMap;

/* JADX INFO: loaded from: classes3.dex */
public final class PsiSuperMethodImplUtil {
    private static final Logger LOG = Logger.getInstance((Class<?>) PsiSuperMethodImplUtil.class);
    private static final PsiCacheKey<Map<MethodSignature, HierarchicalMethodSignature>, PsiClass> SIGNATURES_FOR_CLASS_KEY = PsiCacheKey.create("SIGNATURES_FOR_CLASS_KEY", new NotNullFunction() { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.PsiSuperMethodImplUtil$$ExternalSyntheticLambda3
        @Override // org.jetbrains.kotlin.com.intellij.util.NotNullFunction, org.jetbrains.kotlin.com.intellij.util.Function
        public final Object fun(Object obj) {
            PsiClass psiClass = (PsiClass) obj;
            return PsiSuperMethodImplUtil.buildMethodHierarchy(psiClass, null, PsiSubstitutor.EMPTY, true, new HashSet(), false, psiClass.getResolveScope());
        }
    });
    private static final PsiCacheKey<Map<Pair<String, GlobalSearchScope>, Map<MethodSignature, HierarchicalMethodSignature>>, PsiClass> SIGNATURES_BY_NAME_KEY = PsiCacheKey.create("SIGNATURES_BY_NAME_KEY", new Function() { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.PsiSuperMethodImplUtil$$ExternalSyntheticLambda4
        @Override // org.jetbrains.kotlin.com.intellij.util.Function
        public final Object fun(Object obj) {
            return ConcurrentFactoryMap.createMap(new Function() { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.PsiSuperMethodImplUtil$$ExternalSyntheticLambda0
                @Override // org.jetbrains.kotlin.com.intellij.util.Function
                public final Object fun(Object obj2) {
                    Pair pair = (Pair) obj2;
                    return PsiSuperMethodImplUtil.buildMethodHierarchy(psiClass, (String) pair.first, PsiSubstitutor.EMPTY, true, new HashSet(), false, (GlobalSearchScope) pair.second);
                }
            });
        }
    });

    /* JADX WARN: Code duplicated, block: B:52:0x00a6  */
    /* JADX WARN: Code duplicated, block: B:54:0x00b0  */
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = (i == 2 || i == 4 || i == 6 || i == 8 || i == 18 || i == 32 || i == 34 || i == 38 || i == 40 || i == 13 || i == 14) ? "@NotNull method %s.%s must not return null" : "Argument for @NotNull parameter '%s' of %s.%s must not be null";
        Object[] objArr = new Object[(i == 2 || i == 4 || i == 6 || i == 8 || i == 18 || i == 32 || i == 34 || i == 38 || i == 40 || i == 13 || i == 14) ? 2 : 3];
        if (i == 2 || i == 4 || i == 6 || i == 8) {
            objArr[0] = "org/jetbrains/kotlin/com/intellij/psi/impl/PsiSuperMethodImplUtil";
        } else if (i != 42) {
            switch (i) {
                case 13:
                case 14:
                case 18:
                case 32:
                case 34:
                    objArr[0] = "org/jetbrains/kotlin/com/intellij/psi/impl/PsiSuperMethodImplUtil";
                    break;
                case 15:
                case 19:
                case 28:
                case 33:
                    objArr[0] = "aClass";
                    break;
                case 16:
                    objArr[0] = "substitutor";
                    break;
                case 17:
                    objArr[0] = "visited";
                    break;
                case 20:
                    objArr[0] = "result";
                    break;
                case 21:
                    objArr[0] = "map";
                    break;
                case 22:
                case 29:
                    objArr[0] = "hierarchicalMethodSignature";
                    break;
                case 23:
                    objArr[0] = "signature";
                    break;
                case 24:
                    objArr[0] = "thisSig";
                    break;
                case 25:
                    objArr[0] = "thatSig";
                    break;
                case 26:
                    objArr[0] = "existing";
                    break;
                case 27:
                    objArr[0] = "superSignature";
                    break;
                case 30:
                    objArr[0] = "superSignatureHierarchical";
                    break;
                case 31:
                    objArr[0] = LanguageCodeUtil.HI;
                    break;
                default:
                    switch (i) {
                        case 37:
                            objArr[0] = "resolveScope";
                            break;
                        case 38:
                        case 40:
                            objArr[0] = "org/jetbrains/kotlin/com/intellij/psi/impl/PsiSuperMethodImplUtil";
                            break;
                        case 39:
                            objArr[0] = "aClass";
                            break;
                        default:
                            objArr[0] = "method";
                            break;
                    }
                    break;
            }
        } else {
            objArr[0] = "superMethodProcessor";
        }
        if (i == 2 || i == 4) {
            objArr[1] = "findSuperMethods";
        } else if (i == 6) {
            objArr[1] = "findSuperMethodsInternal";
        } else if (i == 8) {
            objArr[1] = "findSuperMethodSignaturesIncludingStatic";
        } else if (i == 18) {
            objArr[1] = "buildMethodHierarchy";
        } else if (i == 32) {
            objArr[1] = ConfigConstants.CONFIG_RENAMELIMIT_COPY;
        } else if (i == 34) {
            objArr[1] = "getVisibleSignatures";
        } else if (i == 38) {
            objArr[1] = "getHierarchicalMethodSignature";
        } else if (i == 40) {
            objArr[1] = "getSignaturesMap";
        } else if (i == 13 || i == 14) {
            objArr[1] = "findDeepestSuperMethods";
        } else {
            objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/impl/PsiSuperMethodImplUtil";
        }
        switch (i) {
            case 2:
            case 4:
            case 6:
            case 8:
            case 13:
            case 14:
            case 18:
            case 32:
            case 34:
            case 38:
            case 40:
                break;
            case 3:
            default:
                objArr[2] = "findSuperMethods";
                break;
            case 5:
                objArr[2] = "findSuperMethodsInternal";
                break;
            case 7:
                objArr[2] = "findSuperMethodSignaturesIncludingStatic";
                break;
            case 9:
                objArr[2] = "findSuperMethodSignatures";
                break;
            case 10:
                objArr[2] = "canHaveSuperMethod";
                break;
            case 11:
                objArr[2] = "findDeepestSuperMethod";
                break;
            case 12:
                objArr[2] = "findDeepestSuperMethods";
                break;
            case 15:
            case 16:
            case 17:
                objArr[2] = "buildMethodHierarchy";
                break;
            case 19:
            case 20:
            case 21:
            case 22:
            case 23:
                objArr[2] = "putInMap";
                break;
            case 24:
            case 25:
                objArr[2] = "isReturnTypeIsMoreSpecificThan";
                break;
            case 26:
            case 27:
                objArr[2] = "mergeSupers";
                break;
            case 28:
            case 29:
            case 30:
                objArr[2] = "isSuperMethod";
                break;
            case 31:
                objArr[2] = ConfigConstants.CONFIG_RENAMELIMIT_COPY;
                break;
            case 33:
                objArr[2] = "getVisibleSignatures";
                break;
            case 35:
            case 36:
            case 37:
                objArr[2] = "getHierarchicalMethodSignature";
                break;
            case 39:
                objArr[2] = "getSignaturesMap";
                break;
            case 41:
            case 42:
                objArr[2] = "processDirectSuperMethodsSmart";
                break;
        }
        String str2 = String.format(str, objArr);
        if (i != 2 && i != 4 && i != 6 && i != 8 && i != 18 && i != 32 && i != 34 && i != 38 && i != 40 && i != 13 && i != 14) {
            throw new IllegalArgumentException(str2);
        }
        throw new IllegalStateException(str2);
    }

    private PsiSuperMethodImplUtil() {
    }

    public static PsiMethod[] findSuperMethods(PsiMethod psiMethod) {
        if (psiMethod == null) {
            $$$reportNull$$$0(0);
        }
        return findSuperMethods(psiMethod, (PsiClass) null);
    }

    public static PsiMethod[] findSuperMethods(PsiMethod psiMethod, boolean z) {
        if (psiMethod == null) {
            $$$reportNull$$$0(1);
        }
        if (canHaveSuperMethod(psiMethod, z, false)) {
            return findSuperMethodsInternal(psiMethod, null);
        }
        PsiMethod[] psiMethodArr = PsiMethod.EMPTY_ARRAY;
        if (psiMethodArr == null) {
            $$$reportNull$$$0(2);
        }
        return psiMethodArr;
    }

    public static PsiMethod[] findSuperMethods(PsiMethod psiMethod, PsiClass psiClass) {
        if (psiMethod == null) {
            $$$reportNull$$$0(3);
        }
        if (canHaveSuperMethod(psiMethod, true, false)) {
            return findSuperMethodsInternal(psiMethod, psiClass);
        }
        PsiMethod[] psiMethodArr = PsiMethod.EMPTY_ARRAY;
        if (psiMethodArr == null) {
            $$$reportNull$$$0(4);
        }
        return psiMethodArr;
    }

    private static PsiMethod[] findSuperMethodsInternal(PsiMethod psiMethod, PsiClass psiClass) {
        if (psiMethod == null) {
            $$$reportNull$$$0(5);
        }
        PsiMethod[] psiMethodArrConvertMethodSignaturesToMethods = MethodSignatureUtil.convertMethodSignaturesToMethods(findSuperMethodSignatures(psiMethod, psiClass, false));
        if (psiMethodArrConvertMethodSignaturesToMethods == null) {
            $$$reportNull$$$0(6);
        }
        return psiMethodArrConvertMethodSignaturesToMethods;
    }

    public static List<MethodSignatureBackedByPsiMethod> findSuperMethodSignaturesIncludingStatic(PsiMethod psiMethod, boolean z) {
        if (psiMethod == null) {
            $$$reportNull$$$0(7);
        }
        if (canHaveSuperMethod(psiMethod, z, true)) {
            return findSuperMethodSignatures(psiMethod, null, true);
        }
        List<MethodSignatureBackedByPsiMethod> listEmptyList = Collections.emptyList();
        if (listEmptyList == null) {
            $$$reportNull$$$0(8);
        }
        return listEmptyList;
    }

    private static List<MethodSignatureBackedByPsiMethod> findSuperMethodSignatures(PsiMethod psiMethod, PsiClass psiClass, boolean z) {
        if (psiMethod == null) {
            $$$reportNull$$$0(9);
        }
        return new ArrayList(SuperMethodsSearch.search(new SuperMethodsSearch.SearchParameters(psiMethod, psiClass, true, z, true)).findAll());
    }

    private static boolean canHaveSuperMethod(PsiMethod psiMethod, boolean z, boolean z2) {
        PsiClass containingClass;
        if (psiMethod == null) {
            $$$reportNull$$$0(10);
        }
        if (psiMethod.isConstructor()) {
            return false;
        }
        if (z2 || !psiMethod.hasModifierProperty("static")) {
            return ((z && psiMethod.hasModifierProperty("private")) || (containingClass = psiMethod.getContainingClass()) == null || CommonClassNames.JAVA_LANG_OBJECT.equals(containingClass.getQualifiedName())) ? false : true;
        }
        return false;
    }

    public static PsiMethod findDeepestSuperMethod(PsiMethod psiMethod) {
        if (psiMethod == null) {
            $$$reportNull$$$0(11);
        }
        if (canHaveSuperMethod(psiMethod, true, false)) {
            return DeepestSuperMethodsSearch.search(psiMethod).findFirst();
        }
        return null;
    }

    public static PsiMethod[] findDeepestSuperMethods(PsiMethod psiMethod) {
        if (psiMethod == null) {
            $$$reportNull$$$0(12);
        }
        if (canHaveSuperMethod(psiMethod, true, false)) {
            PsiMethod[] psiMethodArr = (PsiMethod[]) DeepestSuperMethodsSearch.search(psiMethod).findAll().toArray(PsiMethod.EMPTY_ARRAY);
            if (psiMethodArr == null) {
                $$$reportNull$$$0(14);
            }
            return psiMethodArr;
        }
        PsiMethod[] psiMethodArr2 = PsiMethod.EMPTY_ARRAY;
        if (psiMethodArr2 == null) {
            $$$reportNull$$$0(13);
        }
        return psiMethodArr2;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Multi-variable type inference failed */
    public static Map<MethodSignature, HierarchicalMethodSignature> buildMethodHierarchy(PsiClass psiClass, String str, PsiSubstitutor psiSubstitutor, boolean z, Set<? super PsiClass> set, boolean z2, GlobalSearchScope globalSearchScope) {
        Iterator<Map.Entry<MethodSignature, HierarchicalMethodSignature>> it;
        if (psiClass == null) {
            $$$reportNull$$$0(15);
        }
        if (psiSubstitutor == null) {
            $$$reportNull$$$0(16);
        }
        if (set == null) {
            $$$reportNull$$$0(17);
        }
        ProgressManager.checkCanceled();
        LinkedHashMap linkedHashMap = new LinkedHashMap(new EqualityPolicy<MethodSignature>() { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.PsiSuperMethodImplUtil.1
            @Override // org.jetbrains.kotlin.com.intellij.util.containers.hash.EqualityPolicy
            public int getHashCode(MethodSignature methodSignature) {
                return methodSignature.hashCode();
            }

            @Override // org.jetbrains.kotlin.com.intellij.util.containers.hash.EqualityPolicy
            public boolean isEqual(MethodSignature methodSignature, MethodSignature methodSignature2) {
                if (methodSignature.equals(methodSignature2)) {
                    PsiMethod method = ((MethodSignatureBackedByPsiMethod) methodSignature).getMethod();
                    PsiType returnType = method.getReturnType();
                    PsiMethod method2 = ((MethodSignatureBackedByPsiMethod) methodSignature2).getMethod();
                    PsiType returnType2 = method2.getReturnType();
                    if (method.hasModifierProperty("static") || method2.hasModifierProperty("static") || MethodSignatureUtil.isReturnTypeSubstitutable(methodSignature, methodSignature2, returnType, returnType2)) {
                        return true;
                    }
                    PsiClass containingClass = method.getContainingClass();
                    PsiClass containingClass2 = method2.getContainingClass();
                    if (containingClass != null && containingClass2 != null) {
                        return containingClass.isAnnotationType() || containingClass2.isAnnotationType();
                    }
                }
                return false;
            }
        });
        final Map mapCreateErasedMethodSignatureMap = MethodSignatureUtil.createErasedMethodSignatureMap();
        LinkedHashMap linkedHashMap2 = new LinkedHashMap(new EqualityPolicy<MethodSignature>() { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.PsiSuperMethodImplUtil.2
            @Override // org.jetbrains.kotlin.com.intellij.util.containers.hash.EqualityPolicy
            public int getHashCode(MethodSignature methodSignature) {
                return methodSignature.hashCode();
            }

            @Override // org.jetbrains.kotlin.com.intellij.util.containers.hash.EqualityPolicy
            public boolean isEqual(MethodSignature methodSignature, MethodSignature methodSignature2) {
                if (!MethodSignatureUtil.areSignaturesErasureEqual(methodSignature, methodSignature2)) {
                    return false;
                }
                List list = (List) mapCreateErasedMethodSignatureMap.get(methodSignature);
                if (list == null || list.size() <= 1) {
                    return true;
                }
                PsiType returnType = ((MethodSignatureBackedByPsiMethod) methodSignature).getMethod().getReturnType();
                PsiType returnType2 = ((MethodSignatureBackedByPsiMethod) methodSignature2).getMethod().getReturnType();
                if (returnType == null && returnType2 == null) {
                    return true;
                }
                if (returnType == null || returnType2 == null) {
                    return false;
                }
                return TypeConversionUtil.erasure(methodSignature.getSubstitutor().substitute(returnType)).equals(TypeConversionUtil.erasure(methodSignature2.getSubstitutor().substitute(returnType2)));
            }
        });
        for (PsiMethod psiMethod : str == null ? psiClass.getMethods() : psiClass.findMethodsByName(str, false)) {
            PsiUtilCore.ensureValid(psiMethod);
            if (z || !psiMethod.hasModifierProperty("private")) {
                MethodSignatureBackedByPsiMethod methodSignatureBackedByPsiMethodCreate = MethodSignatureBackedByPsiMethod.create(psiMethod, PsiSubstitutor.EMPTY, z2);
                HierarchicalMethodSignatureImpl hierarchicalMethodSignatureImpl = new HierarchicalMethodSignatureImpl(MethodSignatureBackedByPsiMethod.create(psiMethod, psiSubstitutor, z2));
                List smartList = (List) mapCreateErasedMethodSignatureMap.get(methodSignatureBackedByPsiMethodCreate);
                if (smartList == null) {
                    smartList = new SmartList();
                    mapCreateErasedMethodSignatureMap.put(methodSignatureBackedByPsiMethodCreate, smartList);
                }
                smartList.add(psiMethod);
                LOG.assertTrue(hierarchicalMethodSignatureImpl.getMethod().isValid(), hierarchicalMethodSignatureImpl.getMethod().getClass());
                linkedHashMap.put(methodSignatureBackedByPsiMethodCreate, hierarchicalMethodSignatureImpl);
                linkedHashMap2.put(methodSignatureBackedByPsiMethodCreate, hierarchicalMethodSignatureImpl);
            }
        }
        for (PsiClassType.ClassResolveResult classResolveResult : PsiClassImplUtil.getScopeCorrectedSuperTypes(psiClass, globalSearchScope)) {
            PsiClass element = classResolveResult.getElement();
            if (element != null && set.add(element)) {
                PsiSubstitutor substitutor = classResolveResult.getSubstitutor();
                Map<MethodSignature, HierarchicalMethodSignature> mapBuildMethodHierarchy = buildMethodHierarchy(element, str, PsiSuperMethodUtil.obtainFinalSubstitutor(element, substitutor, psiSubstitutor, z2), false, set, (z2 || PsiUtil.isRawSubstitutor(element, substitutor)) && element.getTypeParameters().length != 0, globalSearchScope);
                set.remove(element);
                ArrayList<Pair> arrayList = new ArrayList();
                Iterator<Map.Entry<MethodSignature, HierarchicalMethodSignature>> it2 = mapBuildMethodHierarchy.entrySet().iterator();
                while (it2.hasNext()) {
                    HierarchicalMethodSignature value = it2.next().getValue();
                    MethodSignatureBackedByPsiMethod methodSignatureBackedByPsiMethodCreate2 = MethodSignatureBackedByPsiMethod.create(value.getMethod(), value.getSubstitutor(), value.isRaw());
                    PsiClass containingClass = value.getMethod().getContainingClass();
                    for (HierarchicalMethodSignature hierarchicalMethodSignature : new ArrayList(value.getSuperSignatures())) {
                        PsiClass containingClass2 = hierarchicalMethodSignature.getMethod().getContainingClass();
                        if (containingClass == null || containingClass2 == null) {
                            it = it2;
                        } else {
                            it = it2;
                            if (!containingClass.isInheritor(containingClass2, true)) {
                                arrayList.add(Pair.create(methodSignatureBackedByPsiMethodCreate2, hierarchicalMethodSignature));
                            }
                        }
                        it2 = it;
                    }
                    putInMap(psiClass, linkedHashMap, linkedHashMap2, value, methodSignatureBackedByPsiMethodCreate2);
                    it2 = it2;
                }
                for (Pair pair : arrayList) {
                    putInMap(psiClass, linkedHashMap, linkedHashMap2, (HierarchicalMethodSignature) pair.second, (MethodSignature) pair.first);
                }
            }
        }
        Iterator it3 = linkedHashMap2.entrySet().iterator();
        while (it3.hasNext()) {
            Map.Entry entry = (Map.Entry) it3.next();
            HierarchicalMethodSignatureImpl hierarchicalMethodSignatureImpl2 = (HierarchicalMethodSignatureImpl) entry.getValue();
            MethodSignature methodSignature = (MethodSignature) entry.getKey();
            if (linkedHashMap.get(methodSignature) == 0) {
                LOG.assertTrue(hierarchicalMethodSignatureImpl2.getMethod().isValid());
                linkedHashMap.put(methodSignature, hierarchicalMethodSignatureImpl2);
            }
        }
        return linkedHashMap;
    }

    private static void putInMap(PsiClass psiClass, Map<MethodSignature, HierarchicalMethodSignature> map, Map<MethodSignature, HierarchicalMethodSignatureImpl> map2, HierarchicalMethodSignature hierarchicalMethodSignature, MethodSignature methodSignature) {
        if (psiClass == null) {
            $$$reportNull$$$0(19);
        }
        if (map == null) {
            $$$reportNull$$$0(20);
        }
        if (map2 == null) {
            $$$reportNull$$$0(21);
        }
        if (hierarchicalMethodSignature == null) {
            $$$reportNull$$$0(22);
        }
        if (methodSignature == null) {
            $$$reportNull$$$0(23);
        }
        HierarchicalMethodSignatureImpl hierarchicalMethodSignatureImpl = map2.get(methodSignature);
        if (hierarchicalMethodSignatureImpl == null) {
            HierarchicalMethodSignatureImpl hierarchicalMethodSignatureImplCopy = copy(hierarchicalMethodSignature);
            LOG.assertTrue(hierarchicalMethodSignatureImplCopy.getMethod().isValid());
            map2.put(methodSignature, hierarchicalMethodSignatureImplCopy);
        } else {
            if (isReturnTypeIsMoreSpecificThan(hierarchicalMethodSignature, hierarchicalMethodSignatureImpl) && isSuperMethod(psiClass, hierarchicalMethodSignature, hierarchicalMethodSignatureImpl)) {
                HierarchicalMethodSignatureImpl hierarchicalMethodSignatureImplCopy2 = copy(hierarchicalMethodSignature);
                mergeSupers(hierarchicalMethodSignatureImplCopy2, hierarchicalMethodSignatureImpl);
                LOG.assertTrue(hierarchicalMethodSignatureImplCopy2.getMethod().isValid());
                map2.put(methodSignature, hierarchicalMethodSignatureImplCopy2);
                return;
            }
            if (isSuperMethod(psiClass, hierarchicalMethodSignatureImpl, hierarchicalMethodSignature)) {
                mergeSupers(hierarchicalMethodSignatureImpl, hierarchicalMethodSignature);
            } else {
                if (map.containsKey(methodSignature)) {
                    return;
                }
                LOG.assertTrue(hierarchicalMethodSignature.getMethod().isValid());
                map.put(methodSignature, hierarchicalMethodSignature);
            }
        }
    }

    private static boolean isReturnTypeIsMoreSpecificThan(HierarchicalMethodSignature hierarchicalMethodSignature, HierarchicalMethodSignature hierarchicalMethodSignature2) {
        if (hierarchicalMethodSignature == null) {
            $$$reportNull$$$0(24);
        }
        if (hierarchicalMethodSignature2 == null) {
            $$$reportNull$$$0(25);
        }
        PsiType psiTypeSubstitute = hierarchicalMethodSignature.getSubstitutor().substitute(hierarchicalMethodSignature.getMethod().getReturnType());
        PsiType psiTypeSubstitute2 = hierarchicalMethodSignature2.getSubstitutor().substitute(hierarchicalMethodSignature2.getMethod().getReturnType());
        PsiSubstitutor superMethodSignatureSubstitutor = MethodSignatureUtil.isSubsignature(hierarchicalMethodSignature2, hierarchicalMethodSignature) ? MethodSignatureUtil.getSuperMethodSignatureSubstitutor(hierarchicalMethodSignature, hierarchicalMethodSignature2) : null;
        if (superMethodSignatureSubstitutor != null) {
            psiTypeSubstitute = superMethodSignatureSubstitutor.substitute(psiTypeSubstitute);
            psiTypeSubstitute2 = superMethodSignatureSubstitutor.substitute(psiTypeSubstitute2);
        }
        if (psiTypeSubstitute2 == null || psiTypeSubstitute == null) {
            return false;
        }
        if (!psiTypeSubstitute2.isValid()) {
            PsiUtilCore.ensureValid(hierarchicalMethodSignature2.getMethod());
            PsiUtil.ensureValidType(psiTypeSubstitute2);
        }
        if (!psiTypeSubstitute.isValid()) {
            PsiUtilCore.ensureValid(hierarchicalMethodSignature.getMethod());
            PsiUtil.ensureValidType(psiTypeSubstitute);
        }
        return !psiTypeSubstitute2.equals(psiTypeSubstitute) && TypeConversionUtil.isAssignable(psiTypeSubstitute2, psiTypeSubstitute, false);
    }

    private static void mergeSupers(HierarchicalMethodSignatureImpl hierarchicalMethodSignatureImpl, HierarchicalMethodSignature hierarchicalMethodSignature) {
        if (hierarchicalMethodSignatureImpl == null) {
            $$$reportNull$$$0(26);
        }
        if (hierarchicalMethodSignature == null) {
            $$$reportNull$$$0(27);
        }
        for (HierarchicalMethodSignature hierarchicalMethodSignature2 : hierarchicalMethodSignatureImpl.getSuperSignatures()) {
            if (hierarchicalMethodSignature2.getMethod() == hierarchicalMethodSignature.getMethod()) {
                Iterator<HierarchicalMethodSignature> it = hierarchicalMethodSignature.getSuperSignatures().iterator();
                while (it.hasNext()) {
                    mergeSupers((HierarchicalMethodSignatureImpl) hierarchicalMethodSignature2, it.next());
                }
                return;
            }
        }
        if (hierarchicalMethodSignatureImpl.getMethod() == hierarchicalMethodSignature.getMethod()) {
            List<HierarchicalMethodSignature> superSignatures = hierarchicalMethodSignatureImpl.getSuperSignatures();
            for (HierarchicalMethodSignature hierarchicalMethodSignature3 : hierarchicalMethodSignature.getSuperSignatures()) {
                if (!superSignatures.contains(hierarchicalMethodSignature3)) {
                    hierarchicalMethodSignatureImpl.addSuperSignature(copy(hierarchicalMethodSignature3));
                }
            }
            return;
        }
        hierarchicalMethodSignatureImpl.addSuperSignature(copy(hierarchicalMethodSignature));
    }

    private static boolean isSuperMethod(PsiClass psiClass, MethodSignatureBackedByPsiMethod methodSignatureBackedByPsiMethod, MethodSignatureBackedByPsiMethod methodSignatureBackedByPsiMethod2) {
        if (psiClass == null) {
            $$$reportNull$$$0(28);
        }
        if (methodSignatureBackedByPsiMethod == null) {
            $$$reportNull$$$0(29);
        }
        if (methodSignatureBackedByPsiMethod2 == null) {
            $$$reportNull$$$0(30);
        }
        PsiMethod method = methodSignatureBackedByPsiMethod2.getMethod();
        PsiClass containingClass = method.getContainingClass();
        PsiMethod method2 = methodSignatureBackedByPsiMethod.getMethod();
        PsiClass containingClass2 = method2.getContainingClass();
        if (method.isConstructor() || psiClass.equals(containingClass) || !MethodSignatureUtil.isSubsignature(methodSignatureBackedByPsiMethod2, methodSignatureBackedByPsiMethod) || containingClass == null) {
            return false;
        }
        if (!containingClass.isInterface() && !CommonClassNames.JAVA_LANG_OBJECT.equals(containingClass.getQualifiedName())) {
            return (containingClass2 == null || containingClass2.isInterface() || psiClass.isInterface() || InheritanceUtil.isInheritorOrSelf(containingClass, containingClass2, true)) ? false : true;
        }
        if (method.hasModifierProperty("static")) {
            return false;
        }
        if (method.hasModifierProperty("default") && method2.hasModifierProperty("static") && !InheritanceUtil.isInheritorOrSelf(containingClass2, containingClass, true)) {
            return false;
        }
        if (method.hasModifierProperty("default") || method2.hasModifierProperty("default")) {
            return method.equals(method2) || !InheritanceUtil.isInheritorOrSelf(containingClass, containingClass2, true);
        }
        return true;
    }

    private static HierarchicalMethodSignatureImpl copy(HierarchicalMethodSignature hierarchicalMethodSignature) {
        if (hierarchicalMethodSignature == null) {
            $$$reportNull$$$0(31);
        }
        HierarchicalMethodSignatureImpl hierarchicalMethodSignatureImpl = new HierarchicalMethodSignatureImpl(hierarchicalMethodSignature);
        Iterator<HierarchicalMethodSignature> it = hierarchicalMethodSignature.getSuperSignatures().iterator();
        while (it.hasNext()) {
            hierarchicalMethodSignatureImpl.addSuperSignature(copy(it.next()));
        }
        return hierarchicalMethodSignatureImpl;
    }

    public static Collection<HierarchicalMethodSignature> getVisibleSignatures(PsiClass psiClass) {
        if (psiClass == null) {
            $$$reportNull$$$0(33);
        }
        Collection<HierarchicalMethodSignature> collectionValues = getSignaturesMap(psiClass).values();
        if (collectionValues == null) {
            $$$reportNull$$$0(34);
        }
        return collectionValues;
    }

    public static HierarchicalMethodSignature getHierarchicalMethodSignature(PsiMethod psiMethod) {
        if (psiMethod == null) {
            $$$reportNull$$$0(35);
        }
        return getHierarchicalMethodSignature(psiMethod, psiMethod.getResolveScope());
    }

    public static HierarchicalMethodSignature getHierarchicalMethodSignature(final PsiMethod psiMethod, GlobalSearchScope globalSearchScope) {
        if (psiMethod == null) {
            $$$reportNull$$$0(36);
        }
        if (globalSearchScope == null) {
            $$$reportNull$$$0(37);
        }
        HierarchicalMethodSignature hierarchicalMethodSignature = (HierarchicalMethodSignature) ((Map) CachedValuesManager.getCachedValue((PsiElement) psiMethod, new CachedValueProvider() { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.PsiSuperMethodImplUtil$$ExternalSyntheticLambda2
            @Override // org.jetbrains.kotlin.com.intellij.psi.util.CachedValueProvider
            public final CachedValueProvider.Result compute() {
                return CachedValueProvider.Result.create(ConcurrentFactoryMap.createMap(new Function() { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.PsiSuperMethodImplUtil$$ExternalSyntheticLambda1
                    @Override // org.jetbrains.kotlin.com.intellij.util.Function
                    public final Object fun(Object obj) {
                        return PsiSuperMethodImplUtil.lambda$getHierarchicalMethodSignature$3(psiMethod, (GlobalSearchScope) obj);
                    }
                }), PsiModificationTracker.MODIFICATION_COUNT);
            }
        })).get(globalSearchScope);
        if (hierarchicalMethodSignature == null) {
            $$$reportNull$$$0(38);
        }
        return hierarchicalMethodSignature;
    }

    static /* synthetic */ HierarchicalMethodSignature lambda$getHierarchicalMethodSignature$3(PsiMethod psiMethod, GlobalSearchScope globalSearchScope) {
        PsiClass containingClass = psiMethod.getContainingClass();
        MethodSignature signature = psiMethod.getSignature(PsiSubstitutor.EMPTY);
        HierarchicalMethodSignature hierarchicalMethodSignature = containingClass != null ? SIGNATURES_BY_NAME_KEY.getValue(containingClass).get(Pair.create(psiMethod.getName(), globalSearchScope)).get(signature) : null;
        return hierarchicalMethodSignature == null ? new HierarchicalMethodSignatureImpl((MethodSignatureBackedByPsiMethod) signature) : hierarchicalMethodSignature;
    }

    private static Map<MethodSignature, HierarchicalMethodSignature> getSignaturesMap(PsiClass psiClass) {
        if (psiClass == null) {
            $$$reportNull$$$0(39);
        }
        Map<MethodSignature, HierarchicalMethodSignature> value = SIGNATURES_FOR_CLASS_KEY.getValue(psiClass);
        if (value == null) {
            $$$reportNull$$$0(40);
        }
        return value;
    }

    public static boolean processDirectSuperMethodsSmart(PsiMethod psiMethod, Processor<? super PsiMethod> processor) {
        if (psiMethod == null) {
            $$$reportNull$$$0(41);
        }
        if (processor == null) {
            $$$reportNull$$$0(42);
        }
        PsiClass containingClass = psiMethod.getContainingClass();
        if (containingClass == null || !canHaveSuperMethod(psiMethod, true, false)) {
            return false;
        }
        HierarchicalMethodSignature hierarchicalMethodSignature = SIGNATURES_BY_NAME_KEY.getValue(containingClass).get(Pair.create(psiMethod.getName(), psiMethod.getResolveScope())).get(psiMethod.getSignature(PsiSubstitutor.EMPTY));
        if (hierarchicalMethodSignature != null) {
            Iterator<HierarchicalMethodSignature> it = hierarchicalMethodSignature.getSuperSignatures().iterator();
            while (it.hasNext()) {
                if (!processor.process(it.next().getMethod())) {
                    return false;
                }
            }
        }
        return true;
    }
}
