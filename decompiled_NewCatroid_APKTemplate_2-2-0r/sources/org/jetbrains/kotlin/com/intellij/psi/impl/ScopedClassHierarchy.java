package org.jetbrains.kotlin.com.intellij.psi.impl;

import androidx.constraintlayout.core.motion.utils.TypedValues;
import com.google.firebase.analytics.FirebaseAnalytics;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.com.intellij.openapi.util.Computable;
import org.jetbrains.kotlin.com.intellij.openapi.util.RecursionGuard;
import org.jetbrains.kotlin.com.intellij.openapi.util.RecursionManager;
import org.jetbrains.kotlin.com.intellij.openapi.util.text.Strings;
import org.jetbrains.kotlin.com.intellij.pom.java.LanguageLevel;
import org.jetbrains.kotlin.com.intellij.psi.JavaPsiFacade;
import org.jetbrains.kotlin.com.intellij.psi.PsiAnonymousClass;
import org.jetbrains.kotlin.com.intellij.psi.PsiClass;
import org.jetbrains.kotlin.com.intellij.psi.PsiClassType;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiElementFactory;
import org.jetbrains.kotlin.com.intellij.psi.PsiSubstitutor;
import org.jetbrains.kotlin.com.intellij.psi.PsiType;
import org.jetbrains.kotlin.com.intellij.psi.PsiTypeParameter;
import org.jetbrains.kotlin.com.intellij.psi.search.GlobalSearchScope;
import org.jetbrains.kotlin.com.intellij.psi.search.PsiSearchScopeUtil;
import org.jetbrains.kotlin.com.intellij.psi.util.CachedValueProvider;
import org.jetbrains.kotlin.com.intellij.psi.util.CachedValuesManager;
import org.jetbrains.kotlin.com.intellij.psi.util.PsiModificationTracker;
import org.jetbrains.kotlin.com.intellij.psi.util.PsiUtil;
import org.jetbrains.kotlin.com.intellij.psi.util.PsiUtilCore;
import org.jetbrains.kotlin.com.intellij.util.Function;
import org.jetbrains.kotlin.com.intellij.util.PairProcessor;
import org.jetbrains.kotlin.com.intellij.util.containers.CollectionFactory;
import org.jetbrains.kotlin.com.intellij.util.containers.ConcurrentFactoryMap;
import org.jetbrains.kotlin.com.intellij.util.containers.ContainerUtil;
import org.jetbrains.kotlin.com.intellij.util.containers.HashingStrategy;

/* JADX INFO: loaded from: classes3.dex */
class ScopedClassHierarchy {
    private static final HashingStrategy<PsiClass> CLASS_HASHING_STRATEGY = new HashingStrategy<PsiClass>() { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.ScopedClassHierarchy.1
        @Override // org.jetbrains.kotlin.com.intellij.util.containers.HashingStrategy
        public int hashCode(PsiClass psiClass) {
            if (psiClass == null) {
                return 0;
            }
            return Strings.notNullize(psiClass.getQualifiedName()).hashCode();
        }

        @Override // org.jetbrains.kotlin.com.intellij.util.containers.HashingStrategy
        public boolean equals(PsiClass psiClass, PsiClass psiClass2) {
            if (psiClass == psiClass2) {
                return true;
            }
            if (psiClass == null || psiClass2 == null) {
                return false;
            }
            String qualifiedName = psiClass.getQualifiedName();
            if (qualifiedName != null) {
                return qualifiedName.equals(psiClass2.getQualifiedName());
            }
            return psiClass.getManager().areElementsEquivalent(psiClass, psiClass2);
        }
    };
    private final Map<LanguageLevel, Map<PsiClass, PsiSubstitutor>> myAllSupersWithCapturing = ConcurrentFactoryMap.createMap(new Function() { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.ScopedClassHierarchy$$ExternalSyntheticLambda3
        @Override // org.jetbrains.kotlin.com.intellij.util.Function
        public final Object fun(Object obj) {
            return this.f$0.calcAllMemberSupers((LanguageLevel) obj);
        }
    });
    private volatile List<PsiClassType.ClassResolveResult> myImmediateSupersWithCapturing;
    private final PsiClass myPlaceClass;
    private final GlobalSearchScope myResolveScope;
    private volatile Map<PsiClass, PsiClassType.ClassResolveResult> mySupersWithSubstitutors;

    /* JADX WARN: Code duplicated, block: B:7:0x000c  */
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str;
        int i2;
        if (i != 1 && i != 4) {
            switch (i) {
                case 11:
                case 12:
                case 13:
                case 14:
                case 15:
                case 16:
                    str = "@NotNull method %s.%s must not return null";
                    break;
                default:
                    str = "Argument for @NotNull parameter '%s' of %s.%s must not be null";
                    break;
            }
        } else {
            str = "@NotNull method %s.%s must not return null";
        }
        if (i != 1 && i != 4) {
            switch (i) {
                case 11:
                case 12:
                case 13:
                case 14:
                case 15:
                case 16:
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
            case 1:
            case 4:
            case 11:
            case 12:
            case 13:
            case 14:
            case 15:
            case 16:
                objArr[0] = "org/jetbrains/kotlin/com/intellij/psi/impl/ScopedClassHierarchy";
                break;
            case 2:
                objArr[0] = "psiClass";
                break;
            case 3:
                objArr[0] = "resolveScope";
                break;
            case 5:
                objArr[0] = "derivedClass";
                break;
            case 6:
                objArr[0] = "scope";
                break;
            case 7:
            case 17:
                objArr[0] = "superClass";
                break;
            case 8:
                objArr[0] = TypedValues.TransitionType.S_FROM;
                break;
            case 9:
                objArr[0] = "to";
                break;
            case 10:
                objArr[0] = "substitutor";
                break;
            case 18:
                objArr[0] = FirebaseAnalytics.Param.LEVEL;
                break;
            default:
                objArr[0] = ModuleXmlParser.TYPE;
                break;
        }
        if (i == 1) {
            objArr[1] = "getSuperTypes";
        } else if (i != 4) {
            switch (i) {
                case 11:
                case 12:
                    objArr[1] = "mirrorSubstitutor";
                    break;
                case 13:
                case 14:
                    objArr[1] = "getImmediateSupersWithCapturing";
                    break;
                case 15:
                    objArr[1] = "calcImmediateSupersWithCapturing";
                    break;
                case 16:
                    objArr[1] = "calcAllMemberSupers";
                    break;
                default:
                    objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/impl/ScopedClassHierarchy";
                    break;
            }
        } else {
            objArr[1] = "getHierarchy";
        }
        switch (i) {
            case 1:
            case 4:
            case 11:
            case 12:
            case 13:
            case 14:
            case 15:
            case 16:
                break;
            case 2:
            case 3:
                objArr[2] = "getHierarchy";
                break;
            case 5:
            case 6:
            case 7:
                objArr[2] = "getSuperClassSubstitutor";
                break;
            case 8:
            case 9:
            case 10:
                objArr[2] = "mirrorSubstitutor";
                break;
            case 17:
            case 18:
                objArr[2] = "getSuperMembersSubstitutor";
                break;
            default:
                objArr[2] = "visitType";
                break;
        }
        String str2 = String.format(str, objArr);
        if (i != 1 && i != 4) {
            switch (i) {
                case 11:
                case 12:
                case 13:
                case 14:
                case 15:
                case 16:
                    break;
                default:
                    throw new IllegalArgumentException(str2);
            }
        }
        throw new IllegalStateException(str2);
    }

    ScopedClassHierarchy(PsiClass psiClass, GlobalSearchScope globalSearchScope) {
        this.myPlaceClass = psiClass;
        this.myResolveScope = globalSearchScope;
    }

    void visitType(PsiClassType psiClassType, Map<PsiClass, PsiClassType.ClassResolveResult> map) {
        if (psiClassType == null) {
            $$$reportNull$$$0(0);
        }
        PsiClassType.ClassResolveResult classResolveResultResolveGenerics = psiClassType.resolveGenerics();
        PsiClass element = classResolveResultResolveGenerics.getElement();
        if (element == null || InheritanceImplUtil.hasObjectQualifiedName(element) || map.containsKey(element)) {
            return;
        }
        map.put(element, classResolveResultResolveGenerics);
        for (PsiType psiType : getSuperTypes(element)) {
            PsiType psiTypeCorrectType = PsiClassImplUtil.correctType((psiClassType.isRaw() && (psiType instanceof PsiClassType)) ? ((PsiClassType) psiType).rawType() : classResolveResultResolveGenerics.getSubstitutor().substitute(psiType), this.myResolveScope);
            if (psiTypeCorrectType instanceof PsiClassType) {
                visitType((PsiClassType) psiTypeCorrectType, map);
            }
        }
    }

    private static List<PsiType> getSuperTypes(PsiClass psiClass) {
        ArrayList arrayList = new ArrayList();
        if (psiClass instanceof PsiAnonymousClass) {
            ContainerUtil.addIfNotNull(arrayList, ((PsiAnonymousClass) psiClass).getBaseClassType());
        }
        Collections.addAll(arrayList, psiClass.getExtendsListTypes());
        Collections.addAll(arrayList, psiClass.getImplementsListTypes());
        return arrayList;
    }

    static ScopedClassHierarchy getHierarchy(final PsiClass psiClass, GlobalSearchScope globalSearchScope) {
        if (psiClass == null) {
            $$$reportNull$$$0(2);
        }
        if (globalSearchScope == null) {
            $$$reportNull$$$0(3);
        }
        if (psiClass instanceof PsiAnonymousClass) {
            return new ScopedClassHierarchy(psiClass, globalSearchScope);
        }
        ScopedClassHierarchy scopedClassHierarchy = (ScopedClassHierarchy) ((Map) CachedValuesManager.getCachedValue((PsiElement) psiClass, new CachedValueProvider() { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.ScopedClassHierarchy$$ExternalSyntheticLambda2
            @Override // org.jetbrains.kotlin.com.intellij.psi.util.CachedValueProvider
            public final CachedValueProvider.Result compute() {
                return CachedValueProvider.Result.create(ConcurrentFactoryMap.createMap(new Function() { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.ScopedClassHierarchy$$ExternalSyntheticLambda1
                    @Override // org.jetbrains.kotlin.com.intellij.util.Function
                    public final Object fun(Object obj) {
                        return ScopedClassHierarchy.lambda$getHierarchy$0(psiClass, (GlobalSearchScope) obj);
                    }
                }), PsiModificationTracker.MODIFICATION_COUNT);
            }
        })).get(globalSearchScope);
        if (scopedClassHierarchy == null) {
            $$$reportNull$$$0(4);
        }
        return scopedClassHierarchy;
    }

    static /* synthetic */ ScopedClassHierarchy lambda$getHierarchy$0(PsiClass psiClass, GlobalSearchScope globalSearchScope) {
        return new ScopedClassHierarchy(psiClass, globalSearchScope);
    }

    static PsiSubstitutor getSuperClassSubstitutor(PsiClass psiClass, GlobalSearchScope globalSearchScope, PsiClass psiClass2) {
        if (psiClass == null) {
            $$$reportNull$$$0(5);
        }
        if (globalSearchScope == null) {
            $$$reportNull$$$0(6);
        }
        if (psiClass2 == null) {
            $$$reportNull$$$0(7);
        }
        ScopedClassHierarchy hierarchy = getHierarchy(psiClass, globalSearchScope);
        Map<PsiClass, PsiClassType.ClassResolveResult> mapCreateCustomHashingStrategyMap = hierarchy.mySupersWithSubstitutors;
        if (mapCreateCustomHashingStrategyMap == null) {
            mapCreateCustomHashingStrategyMap = CollectionFactory.createCustomHashingStrategyMap(CLASS_HASHING_STRATEGY);
            RecursionGuard.StackStamp stackStampMarkStack = RecursionManager.markStack();
            hierarchy.visitType(JavaPsiFacade.getElementFactory(psiClass.getProject()).createType(psiClass, PsiSubstitutor.EMPTY), mapCreateCustomHashingStrategyMap);
            if (stackStampMarkStack.mayCacheNow()) {
                hierarchy.mySupersWithSubstitutors = mapCreateCustomHashingStrategyMap;
            }
        }
        PsiClassType.ClassResolveResult classResolveResult = mapCreateCustomHashingStrategyMap.get(psiClass2);
        if (classResolveResult == null) {
            return null;
        }
        PsiClass psiClass3 = (PsiClass) Objects.requireNonNull(classResolveResult.getElement());
        PsiSubstitutor substitutor = classResolveResult.getSubstitutor();
        return psiClass3 == psiClass2 ? substitutor : mirrorSubstitutor(psiClass2, psiClass3, substitutor);
    }

    private static PsiSubstitutor mirrorSubstitutor(PsiClass psiClass, PsiClass psiClass2, PsiSubstitutor psiSubstitutor) {
        if (psiClass == null) {
            $$$reportNull$$$0(8);
        }
        if (psiClass2 == null) {
            $$$reportNull$$$0(9);
        }
        if (psiSubstitutor == null) {
            $$$reportNull$$$0(10);
        }
        Iterator<PsiTypeParameter> itTypeParametersIterator = PsiUtil.typeParametersIterator(psiClass2);
        Iterator<PsiTypeParameter> itTypeParametersIterator2 = PsiUtil.typeParametersIterator(psiClass);
        PsiSubstitutor psiSubstitutorPut = PsiSubstitutor.EMPTY;
        while (itTypeParametersIterator.hasNext()) {
            if (!itTypeParametersIterator2.hasNext()) {
                PsiSubstitutor psiSubstitutorCreateRawSubstitutor = JavaClassSupersImpl.createRawSubstitutor(psiClass2);
                if (psiSubstitutorCreateRawSubstitutor == null) {
                    $$$reportNull$$$0(11);
                }
                return psiSubstitutorCreateRawSubstitutor;
            }
            psiSubstitutorPut = psiSubstitutorPut.put(itTypeParametersIterator.next(), psiSubstitutor.substitute(itTypeParametersIterator2.next()));
        }
        if (psiSubstitutorPut == null) {
            $$$reportNull$$$0(12);
        }
        return psiSubstitutorPut;
    }

    List<PsiClassType.ClassResolveResult> getImmediateSupersWithCapturing() {
        List<PsiClassType.ClassResolveResult> list = this.myImmediateSupersWithCapturing;
        if (list == null) {
            RecursionGuard.StackStamp stackStampMarkStack = RecursionManager.markStack();
            List<PsiClassType.ClassResolveResult> list2 = (List) RecursionManager.doPreventingRecursion(this, true, new Computable() { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.ScopedClassHierarchy$$ExternalSyntheticLambda0
                @Override // org.jetbrains.kotlin.com.intellij.openapi.util.Computable
                public final Object compute() {
                    return this.f$0.m6576x9120decc();
                }
            });
            if (list2 == null) {
                List<PsiClassType.ClassResolveResult> listEmptyList = Collections.emptyList();
                if (listEmptyList == null) {
                    $$$reportNull$$$0(13);
                }
                return listEmptyList;
            }
            if (stackStampMarkStack.mayCacheNow()) {
                this.myImmediateSupersWithCapturing = list2;
            }
            list = list2;
        }
        if (list == null) {
            $$$reportNull$$$0(14);
        }
        return list;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: calcImmediateSupersWithCapturing, reason: merged with bridge method [inline-methods] */
    public List<PsiClassType.ClassResolveResult> m6576x9120decc() {
        PsiClassType.ClassResolveResult classResolveResultResolveGenerics;
        PsiClass element;
        PsiUtilCore.ensureValid(this.myPlaceClass);
        ArrayList arrayList = new ArrayList();
        for (PsiClassType psiClassType : this.myPlaceClass.getSuperTypes()) {
            PsiUtil.ensureValidType(psiClassType, this.myPlaceClass);
            PsiClassType psiClassType2 = (PsiClassType) PsiClassImplUtil.correctType(psiClassType, this.myResolveScope);
            if (psiClassType2 != null && (element = (classResolveResultResolveGenerics = ((PsiClassType) PsiUtil.captureToplevelWildcards(psiClassType2, this.myPlaceClass)).resolveGenerics()).getElement()) != null && PsiSearchScopeUtil.isInScope(this.myResolveScope, (PsiElement) element)) {
                arrayList.add(classResolveResultResolveGenerics);
            }
        }
        if (arrayList.isEmpty() && this.myPlaceClass.getExtendsListTypes().length > 0) {
            PsiClassType.ClassResolveResult classResolveResultResolveGenerics2 = PsiType.getJavaLangObject(this.myPlaceClass.getManager(), this.myResolveScope).resolveGenerics();
            if (classResolveResultResolveGenerics2.getElement() != null) {
                arrayList.add(classResolveResultResolveGenerics2);
            }
        }
        return arrayList;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public Map<PsiClass, PsiSubstitutor> calcAllMemberSupers(final LanguageLevel languageLevel) {
        final HashMap map = new HashMap();
        final PsiElementFactory elementFactory = JavaPsiFacade.getElementFactory(this.myPlaceClass.getProject());
        new PairProcessor<PsiClass, PsiSubstitutor>() { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.ScopedClassHierarchy.2
            @Override // org.jetbrains.kotlin.com.intellij.util.PairProcessor
            public boolean process(PsiClass psiClass, PsiSubstitutor psiSubstitutor) {
                if (map.containsKey(psiClass)) {
                    return true;
                }
                map.put(psiClass, psiSubstitutor);
                PsiClassImplUtil.processSuperTypes(psiClass, psiSubstitutor, elementFactory, languageLevel, ScopedClassHierarchy.this.myResolveScope, this);
                return true;
            }
        }.process(this.myPlaceClass, PsiSubstitutor.EMPTY);
        return map;
    }

    PsiSubstitutor getSuperMembersSubstitutor(PsiClass psiClass, LanguageLevel languageLevel) {
        if (psiClass == null) {
            $$$reportNull$$$0(17);
        }
        if (languageLevel == null) {
            $$$reportNull$$$0(18);
        }
        return this.myAllSupersWithCapturing.get(languageLevel).get(psiClass);
    }
}
