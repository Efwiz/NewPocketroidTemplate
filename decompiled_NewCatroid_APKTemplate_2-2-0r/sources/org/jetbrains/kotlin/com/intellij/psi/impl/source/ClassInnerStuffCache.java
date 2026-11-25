package org.jetbrains.kotlin.com.intellij.psi.impl.source;

import com.reandroid.arsc.value.CompoundEntry;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentMap;
import java.util.function.Function;
import org.jetbrains.kotlin.com.intellij.openapi.diagnostic.Logger;
import org.jetbrains.kotlin.com.intellij.openapi.util.Condition;
import org.jetbrains.kotlin.com.intellij.openapi.util.Pair;
import org.jetbrains.kotlin.com.intellij.openapi.util.Ref;
import org.jetbrains.kotlin.com.intellij.pom.java.LanguageLevel;
import org.jetbrains.kotlin.com.intellij.psi.ExternallyDefinedPsiElement;
import org.jetbrains.kotlin.com.intellij.psi.HierarchicalMethodSignature;
import org.jetbrains.kotlin.com.intellij.psi.JavaPsiFacade;
import org.jetbrains.kotlin.com.intellij.psi.PsiAnonymousClass;
import org.jetbrains.kotlin.com.intellij.psi.PsiArrayType;
import org.jetbrains.kotlin.com.intellij.psi.PsiClass;
import org.jetbrains.kotlin.com.intellij.psi.PsiClassType;
import org.jetbrains.kotlin.com.intellij.psi.PsiCodeBlock;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiField;
import org.jetbrains.kotlin.com.intellij.psi.PsiFile;
import org.jetbrains.kotlin.com.intellij.psi.PsiIdentifier;
import org.jetbrains.kotlin.com.intellij.psi.PsiMember;
import org.jetbrains.kotlin.com.intellij.psi.PsiMethod;
import org.jetbrains.kotlin.com.intellij.psi.PsiModifierList;
import org.jetbrains.kotlin.com.intellij.psi.PsiParameterList;
import org.jetbrains.kotlin.com.intellij.psi.PsiRecordComponent;
import org.jetbrains.kotlin.com.intellij.psi.PsiRecordHeader;
import org.jetbrains.kotlin.com.intellij.psi.PsiReferenceList;
import org.jetbrains.kotlin.com.intellij.psi.PsiSubstitutor;
import org.jetbrains.kotlin.com.intellij.psi.PsiType;
import org.jetbrains.kotlin.com.intellij.psi.PsiTypeElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiTypeParameter;
import org.jetbrains.kotlin.com.intellij.psi.PsiTypeParameterList;
import org.jetbrains.kotlin.com.intellij.psi.SyntheticElement;
import org.jetbrains.kotlin.com.intellij.psi.augment.PsiAugmentProvider;
import org.jetbrains.kotlin.com.intellij.psi.impl.PsiClassImplUtil;
import org.jetbrains.kotlin.com.intellij.psi.impl.PsiImplUtil;
import org.jetbrains.kotlin.com.intellij.psi.impl.PsiSuperMethodImplUtil;
import org.jetbrains.kotlin.com.intellij.psi.impl.light.LightElement;
import org.jetbrains.kotlin.com.intellij.psi.impl.light.LightIdentifier;
import org.jetbrains.kotlin.com.intellij.psi.impl.light.LightModifierList;
import org.jetbrains.kotlin.com.intellij.psi.impl.light.LightParameter;
import org.jetbrains.kotlin.com.intellij.psi.impl.light.LightParameterListBuilder;
import org.jetbrains.kotlin.com.intellij.psi.impl.light.LightReferenceListBuilder;
import org.jetbrains.kotlin.com.intellij.psi.javadoc.PsiDocComment;
import org.jetbrains.kotlin.com.intellij.psi.search.GlobalSearchScope;
import org.jetbrains.kotlin.com.intellij.psi.util.CachedValuesManager;
import org.jetbrains.kotlin.com.intellij.psi.util.MethodSignature;
import org.jetbrains.kotlin.com.intellij.psi.util.MethodSignatureBackedByPsiMethod;
import org.jetbrains.kotlin.com.intellij.psi.util.PsiUtil;
import org.jetbrains.kotlin.com.intellij.util.ArrayUtil;
import org.jetbrains.kotlin.com.intellij.util.IncorrectOperationException;
import org.jetbrains.kotlin.com.intellij.util.ObjectUtils;
import org.jetbrains.kotlin.com.intellij.util.containers.ConcurrentFactoryMap;
import org.jetbrains.kotlin.com.intellij.util.containers.ContainerUtil;
import org.jetbrains.kotlin.com.intellij.util.containers.Interner;
import org.jetbrains.kotlin.com.intellij.util.containers.JBIterable;

/* JADX INFO: loaded from: classes3.dex */
public final class ClassInnerStuffCache {
    private final PsiExtensibleClass myClass;
    private final Ref<Pair<Long, Interner<PsiMember>>> myInterner;

    private enum EnumMethodKind {
        ValueOf,
        Values
    }

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str;
        int i2;
        switch (i) {
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
            case 9:
            case 10:
            case 11:
            case 12:
            case 13:
            case 14:
            case 15:
                str = "@NotNull method %s.%s must not return null";
                break;
            default:
                str = "Argument for @NotNull parameter '%s' of %s.%s must not be null";
                break;
        }
        switch (i) {
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
            case 9:
            case 10:
            case 11:
            case 12:
            case 13:
            case 14:
            case 15:
                i2 = 2;
                break;
            default:
                i2 = 3;
                break;
        }
        Object[] objArr = new Object[i2];
        switch (i) {
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
            case 9:
            case 10:
            case 11:
            case 12:
            case 13:
            case 14:
            case 15:
                objArr[0] = "org/jetbrains/kotlin/com/intellij/psi/impl/source/ClassInnerStuffCache";
                break;
            default:
                objArr[0] = "aClass";
                break;
        }
        switch (i) {
            case 1:
                objArr[1] = "getConstructors";
                break;
            case 2:
                objArr[1] = "getFields";
                break;
            case 3:
                objArr[1] = "getMethods";
                break;
            case 4:
                objArr[1] = "getInnerClasses";
                break;
            case 5:
                objArr[1] = "getRecordComponents";
                break;
            case 6:
            case 7:
                objArr[1] = "findMethodsByName";
                break;
            case 8:
                objArr[1] = "calcFields";
                break;
            case 9:
                objArr[1] = "internMembers";
                break;
            case 10:
                objArr[1] = "calcMethods";
                break;
            case 11:
                objArr[1] = "calcInnerClasses";
                break;
            case 12:
                objArr[1] = "calcRecordComponents";
                break;
            case 13:
                objArr[1] = "getFieldsMap";
                break;
            case 14:
                objArr[1] = "getMethodsMap";
                break;
            case 15:
                objArr[1] = "getInnerClassesMap";
                break;
            default:
                objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/impl/source/ClassInnerStuffCache";
                break;
        }
        switch (i) {
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
            case 9:
            case 10:
            case 11:
            case 12:
            case 13:
            case 14:
            case 15:
                break;
            default:
                objArr[2] = "<init>";
                break;
        }
        String str2 = String.format(str, objArr);
        switch (i) {
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
            case 9:
            case 10:
            case 11:
            case 12:
            case 13:
            case 14:
            case 15:
                throw new IllegalStateException(str2);
            default:
                throw new IllegalArgumentException(str2);
        }
    }

    public ClassInnerStuffCache(PsiExtensibleClass psiExtensibleClass) {
        if (psiExtensibleClass == null) {
            $$$reportNull$$$0(0);
        }
        this.myInterner = Ref.create();
        this.myClass = psiExtensibleClass;
    }

    public PsiMethod[] getConstructors() {
        PsiMethod[] psiMethodArr = (PsiMethod[]) copy((PsiMethod[]) CachedValuesManager.getProjectPsiDependentCache(this.myClass, new Function() { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.source.ClassInnerStuffCache$$ExternalSyntheticLambda10
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return PsiImplUtil.getConstructors((PsiExtensibleClass) obj);
            }
        }));
        if (psiMethodArr == null) {
            $$$reportNull$$$0(1);
        }
        return psiMethodArr;
    }

    public PsiField[] getFields() {
        PsiField[] psiFieldArr = (PsiField[]) copy((PsiField[]) CachedValuesManager.getProjectPsiDependentCache(this.myClass, new Function() { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.source.ClassInnerStuffCache$$ExternalSyntheticLambda12
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return this.f$0.m6636x7ea0d77c((PsiExtensibleClass) obj);
            }
        }));
        if (psiFieldArr == null) {
            $$$reportNull$$$0(2);
        }
        return psiFieldArr;
    }

    /* JADX INFO: renamed from: lambda$getFields$0$org-jetbrains-kotlin-com-intellij-psi-impl-source-ClassInnerStuffCache, reason: not valid java name */
    /* synthetic */ PsiField[] m6636x7ea0d77c(PsiExtensibleClass psiExtensibleClass) {
        return calcFields();
    }

    public PsiMethod[] getMethods() {
        PsiMethod[] psiMethodArr = (PsiMethod[]) copy((PsiMethod[]) CachedValuesManager.getProjectPsiDependentCache(this.myClass, new Function() { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.source.ClassInnerStuffCache$$ExternalSyntheticLambda1
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return this.f$0.m6640x380f34d4((PsiExtensibleClass) obj);
            }
        }));
        if (psiMethodArr == null) {
            $$$reportNull$$$0(3);
        }
        return psiMethodArr;
    }

    /* JADX INFO: renamed from: lambda$getMethods$1$org-jetbrains-kotlin-com-intellij-psi-impl-source-ClassInnerStuffCache, reason: not valid java name */
    /* synthetic */ PsiMethod[] m6640x380f34d4(PsiExtensibleClass psiExtensibleClass) {
        return calcMethods();
    }

    public PsiClass[] getInnerClasses() {
        PsiClass[] psiClassArr = (PsiClass[]) copy((PsiClass[]) CachedValuesManager.getProjectPsiDependentCache(this.myClass, new Function() { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.source.ClassInnerStuffCache$$ExternalSyntheticLambda9
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return this.f$0.m6638xdff7fa3((PsiExtensibleClass) obj);
            }
        }));
        if (psiClassArr == null) {
            $$$reportNull$$$0(4);
        }
        return psiClassArr;
    }

    /* JADX INFO: renamed from: lambda$getInnerClasses$2$org-jetbrains-kotlin-com-intellij-psi-impl-source-ClassInnerStuffCache, reason: not valid java name */
    /* synthetic */ PsiClass[] m6638xdff7fa3(PsiExtensibleClass psiExtensibleClass) {
        return calcInnerClasses();
    }

    public PsiRecordComponent[] getRecordComponents() {
        PsiRecordComponent[] psiRecordComponentArr = (PsiRecordComponent[]) copy((PsiRecordComponent[]) CachedValuesManager.getProjectPsiDependentCache(this.myClass, new Function() { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.source.ClassInnerStuffCache$$ExternalSyntheticLambda6
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return this.f$0.m6642x501c5d8b((PsiExtensibleClass) obj);
            }
        }));
        if (psiRecordComponentArr == null) {
            $$$reportNull$$$0(5);
        }
        return psiRecordComponentArr;
    }

    /* JADX INFO: renamed from: lambda$getRecordComponents$3$org-jetbrains-kotlin-com-intellij-psi-impl-source-ClassInnerStuffCache, reason: not valid java name */
    /* synthetic */ PsiRecordComponent[] m6642x501c5d8b(PsiExtensibleClass psiExtensibleClass) {
        return calcRecordComponents();
    }

    public PsiField findFieldByName(String str, boolean z) {
        if (z) {
            return PsiClassImplUtil.findFieldByName(this.myClass, str, true);
        }
        return (PsiField) ((Map) CachedValuesManager.getProjectPsiDependentCache(this.myClass, new Function() { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.source.ClassInnerStuffCache$$ExternalSyntheticLambda0
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return this.f$0.m6633x95e8f7c4((PsiExtensibleClass) obj);
            }
        })).get(str);
    }

    /* JADX INFO: renamed from: lambda$findFieldByName$4$org-jetbrains-kotlin-com-intellij-psi-impl-source-ClassInnerStuffCache, reason: not valid java name */
    /* synthetic */ Map m6633x95e8f7c4(PsiExtensibleClass psiExtensibleClass) {
        return getFieldsMap();
    }

    public PsiMethod[] findMethodsByName(String str, boolean z) {
        if (z) {
            PsiMethod[] psiMethodArrFindMethodsByName = PsiClassImplUtil.findMethodsByName(this.myClass, str, true);
            if (psiMethodArrFindMethodsByName == null) {
                $$$reportNull$$$0(6);
            }
            return psiMethodArrFindMethodsByName;
        }
        PsiMethod[] psiMethodArr = (PsiMethod[]) copy((PsiMethod[]) ObjectUtils.notNull((PsiMethod[]) ((Map) CachedValuesManager.getProjectPsiDependentCache(this.myClass, new Function() { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.source.ClassInnerStuffCache$$ExternalSyntheticLambda8
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return this.f$0.m6635x4dd3ed6b((PsiExtensibleClass) obj);
            }
        })).get(str), PsiMethod.EMPTY_ARRAY));
        if (psiMethodArr == null) {
            $$$reportNull$$$0(7);
        }
        return psiMethodArr;
    }

    /* JADX INFO: renamed from: lambda$findMethodsByName$5$org-jetbrains-kotlin-com-intellij-psi-impl-source-ClassInnerStuffCache, reason: not valid java name */
    /* synthetic */ Map m6635x4dd3ed6b(PsiExtensibleClass psiExtensibleClass) {
        return getMethodsMap();
    }

    public PsiClass findInnerClassByName(String str, boolean z) {
        if (z) {
            return PsiClassImplUtil.findInnerByName(this.myClass, str, true);
        }
        return (PsiClass) ((Map) CachedValuesManager.getProjectPsiDependentCache(this.myClass, new Function() { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.source.ClassInnerStuffCache$$ExternalSyntheticLambda11
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return this.f$0.m6634xca91934e((PsiExtensibleClass) obj);
            }
        })).get(str);
    }

    /* JADX INFO: renamed from: lambda$findInnerClassByName$6$org-jetbrains-kotlin-com-intellij-psi-impl-source-ClassInnerStuffCache, reason: not valid java name */
    /* synthetic */ Map m6634xca91934e(PsiExtensibleClass psiExtensibleClass) {
        return getInnerClassesMap();
    }

    PsiMethod getValuesMethod() {
        if (!this.myClass.isEnum() || isAnonymousClass() || classNameIsSealed()) {
            return null;
        }
        return (PsiMethod) internMember((PsiMethod) CachedValuesManager.getProjectPsiDependentCache(this.myClass, new Function() { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.source.ClassInnerStuffCache$$ExternalSyntheticLambda7
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return ClassInnerStuffCache.makeValuesMethod((PsiExtensibleClass) obj);
            }
        }));
    }

    private boolean classNameIsSealed() {
        return "sealed".equals(this.myClass.get$name()) && PsiUtil.getLanguageLevel(this.myClass).isAtLeast(LanguageLevel.JDK_16_PREVIEW);
    }

    private PsiMethod getValueOfMethod() {
        if (!this.myClass.isEnum() || isAnonymousClass()) {
            return null;
        }
        return (PsiMethod) internMember((PsiMethod) CachedValuesManager.getProjectPsiDependentCache(this.myClass, new Function() { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.source.ClassInnerStuffCache$$ExternalSyntheticLambda3
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return ClassInnerStuffCache.makeValueOfMethod((PsiExtensibleClass) obj);
            }
        }));
    }

    private boolean isAnonymousClass() {
        return this.myClass.get$name() == null || (this.myClass instanceof PsiAnonymousClass);
    }

    private static <T> T[] copy(T[] tArr) {
        return tArr.length == 0 ? tArr : (T[]) ((Object[]) tArr.clone());
    }

    private PsiField[] calcFields() {
        PsiField[] psiFieldArr = (PsiField[]) ArrayUtil.mergeCollections(this.myClass.getOwnFields(), internMembers(PsiAugmentProvider.collectAugments(this.myClass, PsiField.class, null)), PsiField.ARRAY_FACTORY);
        if (psiFieldArr == null) {
            $$$reportNull$$$0(8);
        }
        return psiFieldArr;
    }

    private <T extends PsiMember> List<T> internMembers(List<T> list) {
        List<T> map = ContainerUtil.map((Collection) list, new org.jetbrains.kotlin.com.intellij.util.Function() { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.source.ClassInnerStuffCache$$ExternalSyntheticLambda5
            @Override // org.jetbrains.kotlin.com.intellij.util.Function
            public final Object fun(Object obj) {
                return this.f$0.internMember((PsiMember) obj);
            }
        });
        if (map == null) {
            $$$reportNull$$$0(9);
        }
        return map;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public <T extends PsiMember> T internMember(T t) {
        T t2;
        if (t == null) {
            return null;
        }
        long modificationCount = this.myClass.getManager().getModificationTracker().getModificationCount();
        synchronized (this.myInterner) {
            Pair<Long, Interner<PsiMember>> pair = this.myInterner.get();
            if (pair == null || pair.first.longValue() != modificationCount) {
                Ref<Pair<Long, Interner<PsiMember>>> ref = this.myInterner;
                Pair<Long, Interner<PsiMember>> pairCreate = Pair.create(Long.valueOf(modificationCount), Interner.createWeakInterner());
                ref.set(pairCreate);
                pair = pairCreate;
            }
            t2 = (T) pair.second.intern(t);
        }
        return t2;
    }

    private PsiMethod[] calcMethods() {
        List<PsiMethod> ownMethods = this.myClass.getOwnMethods();
        List listInternMembers = internMembers(PsiAugmentProvider.collectAugments(this.myClass, PsiMethod.class, null));
        if (this.myClass.isEnum()) {
            ArrayList arrayList = new ArrayList(listInternMembers);
            ContainerUtil.addIfNotNull(arrayList, getValuesMethod());
            ContainerUtil.addIfNotNull(arrayList, getValueOfMethod());
            listInternMembers = arrayList;
        }
        PsiMethod[] psiMethodArr = (PsiMethod[]) ArrayUtil.mergeCollections(ownMethods, listInternMembers, PsiMethod.ARRAY_FACTORY);
        if (psiMethodArr == null) {
            $$$reportNull$$$0(10);
        }
        return psiMethodArr;
    }

    private PsiClass[] calcInnerClasses() {
        PsiClass[] psiClassArr = (PsiClass[]) ArrayUtil.mergeCollections(this.myClass.getOwnInnerClasses(), internMembers(PsiAugmentProvider.collectAugments(this.myClass, PsiClass.class, null)), PsiClass.ARRAY_FACTORY);
        if (psiClassArr == null) {
            $$$reportNull$$$0(11);
        }
        return psiClassArr;
    }

    private PsiRecordComponent[] calcRecordComponents() {
        PsiRecordHeader recordHeader = this.myClass.getRecordHeader();
        PsiRecordComponent[] recordComponents = recordHeader == null ? PsiRecordComponent.EMPTY_ARRAY : recordHeader.getRecordComponents();
        if (recordComponents == null) {
            $$$reportNull$$$0(12);
        }
        return recordComponents;
    }

    private Map<String, PsiField> getFieldsMap() {
        final HashMap map = new HashMap();
        for (PsiField psiField : this.myClass.getOwnFields()) {
            String myName = psiField.get$name();
            if (!map.containsKey(myName)) {
                map.put(myName, psiField);
            }
        }
        ConcurrentMap concurrentMapCreateMap = ConcurrentFactoryMap.createMap(new org.jetbrains.kotlin.com.intellij.util.Function() { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.source.ClassInnerStuffCache$$ExternalSyntheticLambda4
            @Override // org.jetbrains.kotlin.com.intellij.util.Function
            public final Object fun(Object obj) {
                return this.f$0.m6637x696082bd(map, (String) obj);
            }
        });
        if (concurrentMapCreateMap == null) {
            $$$reportNull$$$0(13);
        }
        return concurrentMapCreateMap;
    }

    /* JADX INFO: renamed from: lambda$getFieldsMap$7$org-jetbrains-kotlin-com-intellij-psi-impl-source-ClassInnerStuffCache, reason: not valid java name */
    /* synthetic */ PsiField m6637x696082bd(Map map, String str) {
        PsiField psiField = (PsiField) map.get(str);
        return psiField != null ? psiField : (PsiField) internMember((PsiField) ContainerUtil.getFirstItem(PsiAugmentProvider.collectAugments(this.myClass, PsiField.class, str)));
    }

    private Map<String, PsiMethod[]> getMethodsMap() {
        final List<PsiMethod> ownMethods = this.myClass.getOwnMethods();
        ConcurrentMap concurrentMapCreateMap = ConcurrentFactoryMap.createMap(new org.jetbrains.kotlin.com.intellij.util.Function() { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.source.ClassInnerStuffCache$$ExternalSyntheticLambda13
            @Override // org.jetbrains.kotlin.com.intellij.util.Function
            public final Object fun(Object obj) {
                return this.f$0.m6641xc884562(ownMethods, (String) obj);
            }
        });
        if (concurrentMapCreateMap == null) {
            $$$reportNull$$$0(14);
        }
        return concurrentMapCreateMap;
    }

    /* JADX INFO: renamed from: lambda$getMethodsMap$9$org-jetbrains-kotlin-com-intellij-psi-impl-source-ClassInnerStuffCache, reason: not valid java name */
    /* synthetic */ PsiMethod[] m6641xc884562(List list, final String str) {
        return (PsiMethod[]) JBIterable.from(list).filter(new Condition() { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.source.ClassInnerStuffCache$$ExternalSyntheticLambda2
            @Override // org.jetbrains.kotlin.com.intellij.openapi.util.Condition
            public final boolean value(Object obj) {
                return str.equals(((PsiMethod) obj).get$name());
            }
        }).append(CompoundEntry.NAME_values.equals(str) ? getValuesMethod() : null).append("valueOf".equals(str) ? getValueOfMethod() : null).append((Iterable) internMembers(PsiAugmentProvider.collectAugments(this.myClass, PsiMethod.class, str))).toArray(PsiMethod.EMPTY_ARRAY);
    }

    private Map<String, PsiClass> getInnerClassesMap() {
        final HashMap map = new HashMap();
        for (PsiClass psiClass : this.myClass.getOwnInnerClasses()) {
            String name2 = psiClass.get$name();
            if (name2 == null) {
                Logger.getInstance((Class<?>) ClassInnerStuffCache.class).error(psiClass);
            } else if (!(psiClass instanceof ExternallyDefinedPsiElement) || !map.containsKey(name2)) {
                map.put(name2, psiClass);
            }
        }
        ConcurrentMap concurrentMapCreateMap = ConcurrentFactoryMap.createMap(new org.jetbrains.kotlin.com.intellij.util.Function() { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.source.ClassInnerStuffCache$$ExternalSyntheticLambda14
            @Override // org.jetbrains.kotlin.com.intellij.util.Function
            public final Object fun(Object obj) {
                return this.f$0.m6639xd97e280a(map, (String) obj);
            }
        });
        if (concurrentMapCreateMap == null) {
            $$$reportNull$$$0(15);
        }
        return concurrentMapCreateMap;
    }

    /* JADX INFO: renamed from: lambda$getInnerClassesMap$10$org-jetbrains-kotlin-com-intellij-psi-impl-source-ClassInnerStuffCache, reason: not valid java name */
    /* synthetic */ PsiClass m6639xd97e280a(Map map, String str) {
        PsiClass psiClass = (PsiClass) map.get(str);
        return psiClass != null ? psiClass : (PsiClass) internMember((PsiClass) ContainerUtil.getFirstItem(PsiAugmentProvider.collectAugments(this.myClass, PsiClass.class, str)));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static PsiMethod makeValuesMethod(PsiExtensibleClass psiExtensibleClass) {
        return new EnumSyntheticMethod(psiExtensibleClass, EnumMethodKind.Values);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static PsiMethod makeValueOfMethod(PsiExtensibleClass psiExtensibleClass) {
        return new EnumSyntheticMethod(psiExtensibleClass, EnumMethodKind.ValueOf);
    }

    private static class EnumSyntheticMethod extends LightElement implements PsiMethod, SyntheticElement {
        private final PsiClass myClass;
        private final EnumMethodKind myKind;
        private final LightModifierList myModifierList;
        private final LightParameterListBuilder myParameterList;
        private final PsiType myReturnType;

        private static /* synthetic */ void $$$reportNull$$$0(int i) {
            String str;
            int i2;
            switch (i) {
                case 1:
                case 2:
                case 3:
                case 4:
                case 5:
                case 7:
                case 8:
                case 9:
                case 10:
                case 11:
                case 12:
                case 13:
                case 16:
                case 17:
                    str = "@NotNull method %s.%s must not return null";
                    break;
                case 6:
                case 14:
                case 15:
                default:
                    str = "Argument for @NotNull parameter '%s' of %s.%s must not be null";
                    break;
            }
            switch (i) {
                case 1:
                case 2:
                case 3:
                case 4:
                case 5:
                case 7:
                case 8:
                case 9:
                case 10:
                case 11:
                case 12:
                case 13:
                case 16:
                case 17:
                    i2 = 2;
                    break;
                case 6:
                case 14:
                case 15:
                default:
                    i2 = 3;
                    break;
            }
            Object[] objArr = new Object[i2];
            switch (i) {
                case 1:
                case 2:
                case 3:
                case 4:
                case 5:
                case 7:
                case 8:
                case 9:
                case 10:
                case 11:
                case 12:
                case 13:
                case 16:
                case 17:
                    objArr[0] = "org/jetbrains/kotlin/com/intellij/psi/impl/source/ClassInnerStuffCache$EnumSyntheticMethod";
                    break;
                case 6:
                    objArr[0] = "substitutor";
                    break;
                case 14:
                case 15:
                    objArr[0] = "name";
                    break;
                default:
                    objArr[0] = "enumClass";
                    break;
            }
            switch (i) {
                case 1:
                case 2:
                    objArr[1] = "createReturnType";
                    break;
                case 3:
                    objArr[1] = "createParameterList";
                    break;
                case 4:
                    objArr[1] = "getParameterList";
                    break;
                case 5:
                    objArr[1] = "getThrowsList";
                    break;
                case 6:
                case 14:
                case 15:
                default:
                    objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/impl/source/ClassInnerStuffCache$EnumSyntheticMethod";
                    break;
                case 7:
                    objArr[1] = "getSignature";
                    break;
                case 8:
                case 9:
                case 10:
                    objArr[1] = "findSuperMethods";
                    break;
                case 11:
                    objArr[1] = "findSuperMethodSignaturesIncludingStatic";
                    break;
                case 12:
                    objArr[1] = "findDeepestSuperMethods";
                    break;
                case 13:
                    objArr[1] = "getModifierList";
                    break;
                case 16:
                    objArr[1] = "getHierarchicalMethodSignature";
                    break;
                case 17:
                    objArr[1] = "getTypeParameters";
                    break;
            }
            switch (i) {
                case 1:
                case 2:
                case 3:
                case 4:
                case 5:
                case 7:
                case 8:
                case 9:
                case 10:
                case 11:
                case 12:
                case 13:
                case 16:
                case 17:
                    break;
                case 6:
                    objArr[2] = "getSignature";
                    break;
                case 14:
                    objArr[2] = "hasModifierProperty";
                    break;
                case 15:
                    objArr[2] = "setName";
                    break;
                default:
                    objArr[2] = "<init>";
                    break;
            }
            String str2 = String.format(str, objArr);
            switch (i) {
                case 1:
                case 2:
                case 3:
                case 4:
                case 5:
                case 7:
                case 8:
                case 9:
                case 10:
                case 11:
                case 12:
                case 13:
                case 16:
                case 17:
                    throw new IllegalStateException(str2);
                case 6:
                case 14:
                case 15:
                default:
                    throw new IllegalArgumentException(str2);
            }
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.PsiMethod, org.jetbrains.kotlin.com.intellij.psi.PsiParameterListOwner
        public PsiCodeBlock getBody() {
            return null;
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.PsiJavaDocumentedElement
        /* JADX INFO: renamed from: getDocComment */
        public PsiDocComment mo6091getDocComment() {
            return null;
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.PsiMethod
        public PsiTypeElement getReturnTypeElement() {
            return null;
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.PsiTypeParameterListOwner
        /* JADX INFO: renamed from: getTypeParameterList */
        public PsiTypeParameterList mo6093getTypeParameterList() {
            return null;
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.PsiTypeParameterListOwner
        public boolean hasTypeParameters() {
            return false;
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.PsiMethod
        public boolean isConstructor() {
            return false;
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.PsiDocCommentOwner
        public boolean isDeprecated() {
            return false;
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.PsiMethod
        public boolean isVarArgs() {
            return false;
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.impl.light.LightElement, java.util.concurrent.atomic.AtomicReference, org.jetbrains.kotlin.com.intellij.psi.PsiElement
        public String toString() {
            return null;
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        EnumSyntheticMethod(PsiClass psiClass, EnumMethodKind enumMethodKind) {
            super(psiClass.getManager(), psiClass.getLanguage());
            if (psiClass == null) {
                $$$reportNull$$$0(0);
            }
            this.myClass = psiClass;
            this.myKind = enumMethodKind;
            this.myReturnType = createReturnType();
            this.myParameterList = createParameterList();
            this.myModifierList = createModifierList();
        }

        private PsiType createReturnType() {
            PsiClassType psiClassTypeCreateType = JavaPsiFacade.getElementFactory(getProject()).createType(this.myClass);
            if (this.myKind != EnumMethodKind.Values) {
                if (psiClassTypeCreateType == null) {
                    $$$reportNull$$$0(2);
                }
                return psiClassTypeCreateType;
            }
            PsiArrayType psiArrayTypeCreateArrayType = psiClassTypeCreateType.createArrayType();
            if (psiArrayTypeCreateArrayType == null) {
                $$$reportNull$$$0(1);
            }
            return psiArrayTypeCreateArrayType;
        }

        private LightModifierList createModifierList() {
            return new LightModifierList(this.myManager, getLanguage(), "public", "static") { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.source.ClassInnerStuffCache.EnumSyntheticMethod.1
                @Override // org.jetbrains.kotlin.com.intellij.psi.impl.light.LightElement, org.jetbrains.kotlin.com.intellij.psi.PsiElement
                /* JADX INFO: renamed from: getParent */
                public PsiElement getLightParent() {
                    return EnumSyntheticMethod.this;
                }
            };
        }

        private LightParameterListBuilder createParameterList() {
            LightParameterListBuilder lightParameterListBuilder = new LightParameterListBuilder(this.myManager, getLanguage());
            if (this.myKind == EnumMethodKind.ValueOf) {
                lightParameterListBuilder.addParameter(new LightParameter("name", (PsiType) PsiType.getJavaLangString(this.myManager, GlobalSearchScope.allScope(getProject())), (PsiElement) this, getLanguage(), false));
            }
            return lightParameterListBuilder;
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.impl.light.LightElement, org.jetbrains.kotlin.com.intellij.psi.PsiElement
        public int getTextOffset() {
            return this.myClass.getTextOffset();
        }

        public boolean equals(Object obj) {
            if (this != obj) {
                if (obj instanceof EnumSyntheticMethod) {
                    EnumSyntheticMethod enumSyntheticMethod = (EnumSyntheticMethod) obj;
                    if (!this.myClass.equals(enumSyntheticMethod.myClass) || this.myKind != enumSyntheticMethod.myKind) {
                    }
                }
                return false;
            }
            return true;
        }

        public int hashCode() {
            return Objects.hash(this.myClass, this.myKind);
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.PsiJvmMember
        public PsiClass getContainingClass() {
            return this.myClass;
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.PsiMethod
        public PsiType getReturnType() {
            return this.myReturnType;
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.PsiMethod, org.jetbrains.kotlin.com.intellij.psi.PsiParameterListOwner
        public PsiParameterList getParameterList() {
            LightParameterListBuilder lightParameterListBuilder = this.myParameterList;
            if (lightParameterListBuilder == null) {
                $$$reportNull$$$0(4);
            }
            return lightParameterListBuilder;
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.PsiMethod
        public PsiReferenceList getThrowsList() {
            LightReferenceListBuilder lightReferenceListBuilder = new LightReferenceListBuilder(this.myManager, getLanguage(), PsiReferenceList.Role.THROWS_LIST);
            if (this.myKind == EnumMethodKind.ValueOf) {
                lightReferenceListBuilder.addReference("java.lang.IllegalArgumentException");
            }
            return lightReferenceListBuilder;
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.PsiMethod
        public MethodSignature getSignature(PsiSubstitutor psiSubstitutor) {
            if (psiSubstitutor == null) {
                $$$reportNull$$$0(6);
            }
            MethodSignatureBackedByPsiMethod methodSignatureBackedByPsiMethodCreate = MethodSignatureBackedByPsiMethod.create(this, psiSubstitutor);
            if (methodSignatureBackedByPsiMethodCreate == null) {
                $$$reportNull$$$0(7);
            }
            return methodSignatureBackedByPsiMethodCreate;
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.PsiMethod, org.jetbrains.kotlin.com.intellij.psi.PsiNameIdentifierOwner
        /* JADX INFO: renamed from: getNameIdentifier */
        public PsiIdentifier mo7392getNameIdentifier() {
            return new LightIdentifier(this.myManager, get$name());
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.navigation.NavigationItem, org.jetbrains.kotlin.com.intellij.pom.PomNamedTarget, org.jetbrains.kotlin.com.intellij.psi.PsiNamedElement, org.jetbrains.kotlin.com.intellij.psi.PsiQualifiedNamedElement
        /* JADX INFO: renamed from: getName */
        public String get$name() {
            if (this.myKind == EnumMethodKind.ValueOf) {
                return "valueOf";
            }
            return CompoundEntry.NAME_values;
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.PsiMethod
        public PsiMethod[] findSuperMethods() {
            PsiMethod[] psiMethodArrFindSuperMethods = PsiSuperMethodImplUtil.findSuperMethods(this);
            if (psiMethodArrFindSuperMethods == null) {
                $$$reportNull$$$0(8);
            }
            return psiMethodArrFindSuperMethods;
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.PsiMethod
        public PsiMethod[] findSuperMethods(boolean z) {
            PsiMethod[] psiMethodArrFindSuperMethods = PsiSuperMethodImplUtil.findSuperMethods(this, z);
            if (psiMethodArrFindSuperMethods == null) {
                $$$reportNull$$$0(9);
            }
            return psiMethodArrFindSuperMethods;
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.PsiMethod
        public PsiMethod[] findSuperMethods(PsiClass psiClass) {
            PsiMethod[] psiMethodArrFindSuperMethods = PsiSuperMethodImplUtil.findSuperMethods(this, psiClass);
            if (psiMethodArrFindSuperMethods == null) {
                $$$reportNull$$$0(10);
            }
            return psiMethodArrFindSuperMethods;
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.PsiMethod
        public List<MethodSignatureBackedByPsiMethod> findSuperMethodSignaturesIncludingStatic(boolean z) {
            List<MethodSignatureBackedByPsiMethod> listFindSuperMethodSignaturesIncludingStatic = PsiSuperMethodImplUtil.findSuperMethodSignaturesIncludingStatic(this, z);
            if (listFindSuperMethodSignaturesIncludingStatic == null) {
                $$$reportNull$$$0(11);
            }
            return listFindSuperMethodSignaturesIncludingStatic;
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.PsiMethod
        public PsiMethod findDeepestSuperMethod() {
            return PsiSuperMethodImplUtil.findDeepestSuperMethod(this);
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.PsiMethod
        public PsiMethod[] findDeepestSuperMethods() {
            PsiMethod[] psiMethodArr = PsiMethod.EMPTY_ARRAY;
            if (psiMethodArr == null) {
                $$$reportNull$$$0(12);
            }
            return psiMethodArr;
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.PsiMethod, org.jetbrains.kotlin.com.intellij.psi.PsiModifierListOwner
        public PsiModifierList getModifierList() {
            LightModifierList lightModifierList = this.myModifierList;
            if (lightModifierList == null) {
                $$$reportNull$$$0(13);
            }
            return lightModifierList;
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.PsiModifierListOwner
        public boolean hasModifierProperty(String str) {
            if (str == null) {
                $$$reportNull$$$0(14);
            }
            return str.equals("public") || str.equals("static");
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.PsiMethod, org.jetbrains.kotlin.com.intellij.pom.PomRenameableTarget
        public PsiElement setName(String str) throws IncorrectOperationException {
            if (str == null) {
                $$$reportNull$$$0(15);
            }
            throw new IncorrectOperationException();
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.PsiMethod
        public HierarchicalMethodSignature getHierarchicalMethodSignature() {
            HierarchicalMethodSignature hierarchicalMethodSignature = PsiSuperMethodImplUtil.getHierarchicalMethodSignature(this);
            if (hierarchicalMethodSignature == null) {
                $$$reportNull$$$0(16);
            }
            return hierarchicalMethodSignature;
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.impl.light.LightElement, org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.psi.PsiElement
        public PsiFile getContainingFile() {
            return this.myClass.getContainingFile();
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.PsiTypeParameterListOwner
        public PsiTypeParameter[] getTypeParameters() {
            PsiTypeParameter[] psiTypeParameterArr = PsiTypeParameter.EMPTY_ARRAY;
            if (psiTypeParameterArr == null) {
                $$$reportNull$$$0(17);
            }
            return psiTypeParameterArr;
        }
    }
}
