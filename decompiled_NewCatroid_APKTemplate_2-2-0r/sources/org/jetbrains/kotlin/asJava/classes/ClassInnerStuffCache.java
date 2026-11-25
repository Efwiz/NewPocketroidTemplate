package org.jetbrains.kotlin.asJava.classes;

import com.reandroid.arsc.value.CompoundEntry;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentMap;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.kotlin.asJava.builder.LightMemberOrigin;
import org.jetbrains.kotlin.asJava.elements.KtLightMethod;
import org.jetbrains.kotlin.asJava.elements.KtLightParameter;
import org.jetbrains.kotlin.cli.common.arguments.PreprocessCommandLineArgumentsKt;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.com.intellij.openapi.diagnostic.Logger;
import org.jetbrains.kotlin.com.intellij.openapi.util.Condition;
import org.jetbrains.kotlin.com.intellij.openapi.util.ModificationTracker;
import org.jetbrains.kotlin.com.intellij.openapi.util.Pair;
import org.jetbrains.kotlin.com.intellij.openapi.util.Ref;
import org.jetbrains.kotlin.com.intellij.openapi.util.TextRange;
import org.jetbrains.kotlin.com.intellij.psi.ExternallyDefinedPsiElement;
import org.jetbrains.kotlin.com.intellij.psi.HierarchicalMethodSignature;
import org.jetbrains.kotlin.com.intellij.psi.JavaElementVisitor;
import org.jetbrains.kotlin.com.intellij.psi.JavaPsiFacade;
import org.jetbrains.kotlin.com.intellij.psi.PsiAnnotation;
import org.jetbrains.kotlin.com.intellij.psi.PsiAnnotationMemberValue;
import org.jetbrains.kotlin.com.intellij.psi.PsiArrayType;
import org.jetbrains.kotlin.com.intellij.psi.PsiClass;
import org.jetbrains.kotlin.com.intellij.psi.PsiClassType;
import org.jetbrains.kotlin.com.intellij.psi.PsiCodeBlock;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiElementFactory;
import org.jetbrains.kotlin.com.intellij.psi.PsiElementVisitor;
import org.jetbrains.kotlin.com.intellij.psi.PsiField;
import org.jetbrains.kotlin.com.intellij.psi.PsiFile;
import org.jetbrains.kotlin.com.intellij.psi.PsiIdentifier;
import org.jetbrains.kotlin.com.intellij.psi.PsiMember;
import org.jetbrains.kotlin.com.intellij.psi.PsiMethod;
import org.jetbrains.kotlin.com.intellij.psi.PsiModifierList;
import org.jetbrains.kotlin.com.intellij.psi.PsiParameterList;
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
import org.jetbrains.kotlin.com.intellij.psi.util.CachedValueProvider;
import org.jetbrains.kotlin.com.intellij.psi.util.CachedValuesManager;
import org.jetbrains.kotlin.com.intellij.psi.util.MethodSignature;
import org.jetbrains.kotlin.com.intellij.psi.util.MethodSignatureBackedByPsiMethod;
import org.jetbrains.kotlin.com.intellij.util.ArrayUtil;
import org.jetbrains.kotlin.com.intellij.util.Function;
import org.jetbrains.kotlin.com.intellij.util.IncorrectOperationException;
import org.jetbrains.kotlin.com.intellij.util.ObjectUtils;
import org.jetbrains.kotlin.com.intellij.util.containers.ConcurrentFactoryMap;
import org.jetbrains.kotlin.com.intellij.util.containers.ContainerUtil;
import org.jetbrains.kotlin.com.intellij.util.containers.Interner;
import org.jetbrains.kotlin.com.intellij.util.containers.JBIterable;
import org.jetbrains.kotlin.psi.KtDeclaration;
import org.jetbrains.kotlin.psi.KtParameter;

/* JADX INFO: loaded from: classes2.dex */
public final class ClassInnerStuffCache {
    public static final String NOT_NULL_ANNOTATION_QUALIFIER = PreprocessCommandLineArgumentsKt.ARGFILE_ARGUMENT + NotNull.class.getName();
    private final KtExtensibleLightClass myClass;
    private final boolean myGenerateEnumMethods;
    private final Ref<Pair<Long, Interner<PsiMember>>> myInterner;
    private final ModificationTracker myModificationTracker;

    private enum EnumMethodKind {
        ValueOf,
        Values
    }

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str;
        int i2;
        switch (i) {
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
                str = "@NotNull method %s.%s must not return null";
                break;
            default:
                str = "Argument for @NotNull parameter '%s' of %s.%s must not be null";
                break;
        }
        switch (i) {
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
                i2 = 2;
                break;
            default:
                i2 = 3;
                break;
        }
        Object[] objArr = new Object[i2];
        switch (i) {
            case 1:
                objArr[0] = "modificationTracker";
                break;
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
                objArr[0] = "org/jetbrains/kotlin/asJava/classes/ClassInnerStuffCache";
                break;
            default:
                objArr[0] = "aClass";
                break;
        }
        switch (i) {
            case 2:
                objArr[1] = "getConstructors";
                break;
            case 3:
                objArr[1] = "getFields";
                break;
            case 4:
                objArr[1] = "getMethods";
                break;
            case 5:
                objArr[1] = "getInnerClasses";
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
                objArr[1] = "getFieldsMap";
                break;
            case 13:
                objArr[1] = "getMethodsMap";
                break;
            case 14:
                objArr[1] = "getInnerClassesMap";
                break;
            default:
                objArr[1] = "org/jetbrains/kotlin/asJava/classes/ClassInnerStuffCache";
                break;
        }
        switch (i) {
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
                break;
            default:
                objArr[2] = "<init>";
                break;
        }
        String str2 = String.format(str, objArr);
        switch (i) {
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
                throw new IllegalStateException(str2);
            default:
                throw new IllegalArgumentException(str2);
        }
    }

    public ClassInnerStuffCache(KtExtensibleLightClass ktExtensibleLightClass, boolean z, ModificationTracker modificationTracker) {
        if (ktExtensibleLightClass == null) {
            $$$reportNull$$$0(0);
        }
        if (modificationTracker == null) {
            $$$reportNull$$$0(1);
        }
        this.myInterner = Ref.create();
        this.myGenerateEnumMethods = z;
        this.myClass = ktExtensibleLightClass;
        this.myModificationTracker = modificationTracker;
    }

    public PsiMethod[] getConstructors() {
        PsiMethod[] psiMethodArr = (PsiMethod[]) copy((Object[]) CachedValuesManager.getCachedValue((PsiElement) this.myClass, new CachedValueProvider() { // from class: org.jetbrains.kotlin.asJava.classes.ClassInnerStuffCache$$ExternalSyntheticLambda4
            @Override // org.jetbrains.kotlin.com.intellij.psi.util.CachedValueProvider
            public final CachedValueProvider.Result compute() {
                return this.f$0.m6080xa70cba86();
            }
        }));
        if (psiMethodArr == null) {
            $$$reportNull$$$0(2);
        }
        return psiMethodArr;
    }

    /* JADX INFO: renamed from: lambda$getConstructors$0$org-jetbrains-kotlin-asJava-classes-ClassInnerStuffCache, reason: not valid java name */
    /* synthetic */ CachedValueProvider.Result m6080xa70cba86() {
        return CachedValueProvider.Result.createSingleDependency(PsiImplUtil.getConstructors(this.myClass), this.myModificationTracker);
    }

    public PsiField[] getFields() {
        PsiField[] psiFieldArr = (PsiField[]) copy((Object[]) CachedValuesManager.getCachedValue((PsiElement) this.myClass, new CachedValueProvider() { // from class: org.jetbrains.kotlin.asJava.classes.ClassInnerStuffCache$$ExternalSyntheticLambda7
            @Override // org.jetbrains.kotlin.com.intellij.psi.util.CachedValueProvider
            public final CachedValueProvider.Result compute() {
                return this.f$0.m6081xebdffbe5();
            }
        }));
        if (psiFieldArr == null) {
            $$$reportNull$$$0(3);
        }
        return psiFieldArr;
    }

    /* JADX INFO: renamed from: lambda$getFields$1$org-jetbrains-kotlin-asJava-classes-ClassInnerStuffCache, reason: not valid java name */
    /* synthetic */ CachedValueProvider.Result m6081xebdffbe5() {
        return CachedValueProvider.Result.createSingleDependency(calcFields(), this.myModificationTracker);
    }

    public PsiMethod[] getMethods() {
        PsiMethod[] psiMethodArr = (PsiMethod[]) copy((Object[]) CachedValuesManager.getCachedValue((PsiElement) this.myClass, new CachedValueProvider() { // from class: org.jetbrains.kotlin.asJava.classes.ClassInnerStuffCache$$ExternalSyntheticLambda8
            @Override // org.jetbrains.kotlin.com.intellij.psi.util.CachedValueProvider
            public final CachedValueProvider.Result compute() {
                return this.f$0.m6085xb1d6133d();
            }
        }));
        if (psiMethodArr == null) {
            $$$reportNull$$$0(4);
        }
        return psiMethodArr;
    }

    /* JADX INFO: renamed from: lambda$getMethods$2$org-jetbrains-kotlin-asJava-classes-ClassInnerStuffCache, reason: not valid java name */
    /* synthetic */ CachedValueProvider.Result m6085xb1d6133d() {
        return CachedValueProvider.Result.createSingleDependency(calcMethods(), this.myModificationTracker);
    }

    public PsiClass[] getInnerClasses() {
        PsiClass[] psiClassArr = (PsiClass[]) copy((Object[]) CachedValuesManager.getCachedValue((PsiElement) this.myClass, new CachedValueProvider() { // from class: org.jetbrains.kotlin.asJava.classes.ClassInnerStuffCache$$ExternalSyntheticLambda12
            @Override // org.jetbrains.kotlin.com.intellij.psi.util.CachedValueProvider
            public final CachedValueProvider.Result compute() {
                return this.f$0.m6083xd0ace44c();
            }
        }));
        if (psiClassArr == null) {
            $$$reportNull$$$0(5);
        }
        return psiClassArr;
    }

    /* JADX INFO: renamed from: lambda$getInnerClasses$3$org-jetbrains-kotlin-asJava-classes-ClassInnerStuffCache, reason: not valid java name */
    /* synthetic */ CachedValueProvider.Result m6083xd0ace44c() {
        return CachedValueProvider.Result.createSingleDependency(calcInnerClasses(), this.myModificationTracker);
    }

    public PsiField findFieldByName(String str, boolean z) {
        if (z) {
            return PsiClassImplUtil.findFieldByName(this.myClass, str, true);
        }
        return (PsiField) ((Map) CachedValuesManager.getCachedValue((PsiElement) this.myClass, new CachedValueProvider() { // from class: org.jetbrains.kotlin.asJava.classes.ClassInnerStuffCache$$ExternalSyntheticLambda6
            @Override // org.jetbrains.kotlin.com.intellij.psi.util.CachedValueProvider
            public final CachedValueProvider.Result compute() {
                return this.f$0.m6077x2e29a30e();
            }
        })).get(str);
    }

    /* JADX INFO: renamed from: lambda$findFieldByName$4$org-jetbrains-kotlin-asJava-classes-ClassInnerStuffCache, reason: not valid java name */
    /* synthetic */ CachedValueProvider.Result m6077x2e29a30e() {
        return CachedValueProvider.Result.createSingleDependency(getFieldsMap(), this.myModificationTracker);
    }

    public PsiMethod[] findMethodsByName(String str, boolean z) {
        if (z) {
            PsiMethod[] psiMethodArrFindMethodsByName = PsiClassImplUtil.findMethodsByName(this.myClass, str, true);
            if (psiMethodArrFindMethodsByName == null) {
                $$$reportNull$$$0(6);
            }
            return psiMethodArrFindMethodsByName;
        }
        PsiMethod[] psiMethodArr = (PsiMethod[]) copy((Object[]) ObjectUtils.notNull(((Map) CachedValuesManager.getCachedValue((PsiElement) this.myClass, new CachedValueProvider() { // from class: org.jetbrains.kotlin.asJava.classes.ClassInnerStuffCache$$ExternalSyntheticLambda0
            @Override // org.jetbrains.kotlin.com.intellij.psi.util.CachedValueProvider
            public final CachedValueProvider.Result compute() {
                return this.f$0.m6079x443078f5();
            }
        })).get(str), PsiMethod.EMPTY_ARRAY));
        if (psiMethodArr == null) {
            $$$reportNull$$$0(7);
        }
        return psiMethodArr;
    }

    /* JADX INFO: renamed from: lambda$findMethodsByName$5$org-jetbrains-kotlin-asJava-classes-ClassInnerStuffCache, reason: not valid java name */
    /* synthetic */ CachedValueProvider.Result m6079x443078f5() {
        return CachedValueProvider.Result.createSingleDependency(getMethodsMap(), this.myModificationTracker);
    }

    public PsiClass findInnerClassByName(String str, boolean z) {
        if (z) {
            return PsiClassImplUtil.findInnerByName(this.myClass, str, true);
        }
        return (PsiClass) ((Map) CachedValuesManager.getCachedValue((PsiElement) this.myClass, new CachedValueProvider() { // from class: org.jetbrains.kotlin.asJava.classes.ClassInnerStuffCache$$ExternalSyntheticLambda11
            @Override // org.jetbrains.kotlin.com.intellij.psi.util.CachedValueProvider
            public final CachedValueProvider.Result compute() {
                return this.f$0.m6078x1780d818();
            }
        })).get(str);
    }

    /* JADX INFO: renamed from: lambda$findInnerClassByName$6$org-jetbrains-kotlin-asJava-classes-ClassInnerStuffCache, reason: not valid java name */
    /* synthetic */ CachedValueProvider.Result m6078x1780d818() {
        return CachedValueProvider.Result.createSingleDependency(getInnerClassesMap(), this.myModificationTracker);
    }

    private PsiMethod getValuesMethod() {
        if (isEnum()) {
            return (PsiMethod) internMember((PsiMember) CachedValuesManager.getCachedValue((PsiElement) this.myClass, new CachedValueProvider() { // from class: org.jetbrains.kotlin.asJava.classes.ClassInnerStuffCache$$ExternalSyntheticLambda10
                @Override // org.jetbrains.kotlin.com.intellij.psi.util.CachedValueProvider
                public final CachedValueProvider.Result compute() {
                    return this.f$0.m6088x611bf135();
                }
            }));
        }
        return null;
    }

    /* JADX INFO: renamed from: lambda$getValuesMethod$7$org-jetbrains-kotlin-asJava-classes-ClassInnerStuffCache, reason: not valid java name */
    /* synthetic */ CachedValueProvider.Result m6088x611bf135() {
        return CachedValueProvider.Result.createSingleDependency(makeValuesMethod(this.myClass), this.myModificationTracker);
    }

    private PsiMethod getValueOfMethod() {
        if (isEnum()) {
            return (PsiMethod) internMember((PsiMember) CachedValuesManager.getCachedValue((PsiElement) this.myClass, new CachedValueProvider() { // from class: org.jetbrains.kotlin.asJava.classes.ClassInnerStuffCache$$ExternalSyntheticLambda1
                @Override // org.jetbrains.kotlin.com.intellij.psi.util.CachedValueProvider
                public final CachedValueProvider.Result compute() {
                    return this.f$0.m6087xdd80aa60();
                }
            }));
        }
        return null;
    }

    /* JADX INFO: renamed from: lambda$getValueOfMethod$8$org-jetbrains-kotlin-asJava-classes-ClassInnerStuffCache, reason: not valid java name */
    /* synthetic */ CachedValueProvider.Result m6087xdd80aa60() {
        return CachedValueProvider.Result.createSingleDependency(makeValueOfMethod(this.myClass), this.myModificationTracker);
    }

    private boolean isEnum() {
        return this.myGenerateEnumMethods && this.myClass.isEnum();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static <T> T[] copy(T[] tArr) {
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
        List<T> map = ContainerUtil.map((Collection) list, new Function() { // from class: org.jetbrains.kotlin.asJava.classes.ClassInnerStuffCache$$ExternalSyntheticLambda13
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
        long modificationCount = this.myModificationTracker.getModificationCount();
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
        if (isEnum()) {
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

    private Map<String, PsiField> getFieldsMap() {
        final HashMap map = new HashMap();
        for (PsiField psiField : this.myClass.getOwnFields()) {
            map.putIfAbsent(psiField.get$name(), psiField);
        }
        ConcurrentMap concurrentMapCreateMap = ConcurrentFactoryMap.createMap(new Function() { // from class: org.jetbrains.kotlin.asJava.classes.ClassInnerStuffCache$$ExternalSyntheticLambda5
            @Override // org.jetbrains.kotlin.com.intellij.util.Function
            public final Object fun(Object obj) {
                return this.f$0.m6082xebc71405(map, (String) obj);
            }
        });
        if (concurrentMapCreateMap == null) {
            $$$reportNull$$$0(12);
        }
        return concurrentMapCreateMap;
    }

    /* JADX INFO: renamed from: lambda$getFieldsMap$9$org-jetbrains-kotlin-asJava-classes-ClassInnerStuffCache, reason: not valid java name */
    /* synthetic */ PsiField m6082xebc71405(Map map, String str) {
        PsiField psiField = (PsiField) map.get(str);
        return psiField != null ? psiField : (PsiField) internMember((PsiMember) ContainerUtil.getFirstItem(PsiAugmentProvider.collectAugments(this.myClass, PsiField.class, str)));
    }

    private Map<String, PsiMethod[]> getMethodsMap() {
        final List<PsiMethod> ownMethods = this.myClass.getOwnMethods();
        ConcurrentMap concurrentMapCreateMap = ConcurrentFactoryMap.createMap(new Function() { // from class: org.jetbrains.kotlin.asJava.classes.ClassInnerStuffCache$$ExternalSyntheticLambda9
            @Override // org.jetbrains.kotlin.com.intellij.util.Function
            public final Object fun(Object obj) {
                return this.f$0.m6086x6e67fb03(ownMethods, (String) obj);
            }
        });
        if (concurrentMapCreateMap == null) {
            $$$reportNull$$$0(13);
        }
        return concurrentMapCreateMap;
    }

    /* JADX INFO: renamed from: lambda$getMethodsMap$11$org-jetbrains-kotlin-asJava-classes-ClassInnerStuffCache, reason: not valid java name */
    /* synthetic */ PsiMethod[] m6086x6e67fb03(List list, final String str) {
        return (PsiMethod[]) JBIterable.from(list).filter(new Condition() { // from class: org.jetbrains.kotlin.asJava.classes.ClassInnerStuffCache$$ExternalSyntheticLambda2
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
        ConcurrentMap concurrentMapCreateMap = ConcurrentFactoryMap.createMap(new Function() { // from class: org.jetbrains.kotlin.asJava.classes.ClassInnerStuffCache$$ExternalSyntheticLambda3
            @Override // org.jetbrains.kotlin.com.intellij.util.Function
            public final Object fun(Object obj) {
                return this.f$0.m6084xacf05412(map, (String) obj);
            }
        });
        if (concurrentMapCreateMap == null) {
            $$$reportNull$$$0(14);
        }
        return concurrentMapCreateMap;
    }

    /* JADX INFO: renamed from: lambda$getInnerClassesMap$12$org-jetbrains-kotlin-asJava-classes-ClassInnerStuffCache, reason: not valid java name */
    /* synthetic */ PsiClass m6084xacf05412(Map map, String str) {
        PsiClass psiClass = (PsiClass) map.get(str);
        return psiClass != null ? psiClass : (PsiClass) internMember((PsiMember) ContainerUtil.getFirstItem(PsiAugmentProvider.collectAugments(this.myClass, PsiClass.class, str)));
    }

    private static PsiMethod makeValuesMethod(KtExtensibleLightClass ktExtensibleLightClass) {
        return new EnumSyntheticMethod(ktExtensibleLightClass, EnumMethodKind.Values);
    }

    private static PsiMethod makeValueOfMethod(KtExtensibleLightClass ktExtensibleLightClass) {
        return new EnumSyntheticMethod(ktExtensibleLightClass, EnumMethodKind.ValueOf);
    }

    private static class EnumSyntheticMethod extends LightElement implements PsiMethod, SyntheticElement, KtLightMethod {
        private final KtExtensibleLightClass myClass;
        private final EnumMethodKind myKind;
        private final LightModifierList myModifierList;
        private final LightParameterListBuilder myParameterList;
        private final PsiType myReturnType;

        private static /* synthetic */ void $$$reportNull$$$0(int i) {
            String str;
            int i2;
            switch (i) {
                case 2:
                case 3:
                case 4:
                case 5:
                case 6:
                case 7:
                case 8:
                case 10:
                case 11:
                case 12:
                case 13:
                case 14:
                case 15:
                case 16:
                case 19:
                case 20:
                    str = "@NotNull method %s.%s must not return null";
                    break;
                case 9:
                case 17:
                case 18:
                default:
                    str = "Argument for @NotNull parameter '%s' of %s.%s must not be null";
                    break;
            }
            switch (i) {
                case 2:
                case 3:
                case 4:
                case 5:
                case 6:
                case 7:
                case 8:
                case 10:
                case 11:
                case 12:
                case 13:
                case 14:
                case 15:
                case 16:
                case 19:
                case 20:
                    i2 = 2;
                    break;
                case 9:
                case 17:
                case 18:
                default:
                    i2 = 3;
                    break;
            }
            Object[] objArr = new Object[i2];
            switch (i) {
                case 1:
                    objArr[0] = "visitor";
                    break;
                case 2:
                case 3:
                case 4:
                case 5:
                case 6:
                case 7:
                case 8:
                case 10:
                case 11:
                case 12:
                case 13:
                case 14:
                case 15:
                case 16:
                case 19:
                case 20:
                    objArr[0] = "org/jetbrains/kotlin/asJava/classes/ClassInnerStuffCache$EnumSyntheticMethod";
                    break;
                case 9:
                    objArr[0] = "substitutor";
                    break;
                case 17:
                case 18:
                    objArr[0] = "name";
                    break;
                default:
                    objArr[0] = "enumClass";
                    break;
            }
            switch (i) {
                case 2:
                    objArr[1] = "createNotNullAnnotation";
                    break;
                case 3:
                case 4:
                    objArr[1] = "createReturnType";
                    break;
                case 5:
                    objArr[1] = "createParameterList";
                    break;
                case 6:
                    objArr[1] = "getContainingClass";
                    break;
                case 7:
                    objArr[1] = "getParameterList";
                    break;
                case 8:
                    objArr[1] = "getThrowsList";
                    break;
                case 9:
                case 17:
                case 18:
                default:
                    objArr[1] = "org/jetbrains/kotlin/asJava/classes/ClassInnerStuffCache$EnumSyntheticMethod";
                    break;
                case 10:
                    objArr[1] = "getSignature";
                    break;
                case 11:
                case 12:
                case 13:
                    objArr[1] = "findSuperMethods";
                    break;
                case 14:
                    objArr[1] = "findSuperMethodSignaturesIncludingStatic";
                    break;
                case 15:
                    objArr[1] = "findDeepestSuperMethods";
                    break;
                case 16:
                    objArr[1] = "getModifierList";
                    break;
                case 19:
                    objArr[1] = "getHierarchicalMethodSignature";
                    break;
                case 20:
                    objArr[1] = "getTypeParameters";
                    break;
            }
            switch (i) {
                case 1:
                    objArr[2] = "accept";
                    break;
                case 2:
                case 3:
                case 4:
                case 5:
                case 6:
                case 7:
                case 8:
                case 10:
                case 11:
                case 12:
                case 13:
                case 14:
                case 15:
                case 16:
                case 19:
                case 20:
                    break;
                case 9:
                    objArr[2] = "getSignature";
                    break;
                case 17:
                    objArr[2] = "hasModifierProperty";
                    break;
                case 18:
                    objArr[2] = "setName";
                    break;
                default:
                    objArr[2] = "<init>";
                    break;
            }
            String str2 = String.format(str, objArr);
            switch (i) {
                case 2:
                case 3:
                case 4:
                case 5:
                case 6:
                case 7:
                case 8:
                case 10:
                case 11:
                case 12:
                case 13:
                case 14:
                case 15:
                case 16:
                case 19:
                case 20:
                    throw new IllegalStateException(str2);
                case 9:
                case 17:
                case 18:
                default:
                    throw new IllegalArgumentException(str2);
            }
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.PsiMethod, org.jetbrains.kotlin.com.intellij.psi.PsiParameterListOwner
        public PsiCodeBlock getBody() {
            return null;
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.PsiAnnotationMethod
        public PsiAnnotationMemberValue getDefaultValue() {
            return null;
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.PsiJavaDocumentedElement
        /* JADX INFO: renamed from: getDocComment */
        public PsiDocComment mo6091getDocComment() {
            return null;
        }

        @Override // org.jetbrains.kotlin.asJava.elements.KtLightElement
        public KtDeclaration getKotlinOrigin() {
            return null;
        }

        @Override // org.jetbrains.kotlin.asJava.elements.KtLightMember
        public LightMemberOrigin getLightMemberOrigin() {
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

        @Override // org.jetbrains.kotlin.asJava.elements.KtLightMethod
        public boolean isMangled() {
            return false;
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.PsiMethod
        public boolean isVarArgs() {
            return false;
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        EnumSyntheticMethod(KtExtensibleLightClass ktExtensibleLightClass, EnumMethodKind enumMethodKind) {
            super(ktExtensibleLightClass.getManager(), ktExtensibleLightClass.getLanguage());
            if (ktExtensibleLightClass == null) {
                $$$reportNull$$$0(0);
            }
            this.myClass = ktExtensibleLightClass;
            this.myKind = enumMethodKind;
            this.myReturnType = createReturnType();
            this.myParameterList = createParameterList();
            this.myModifierList = createModifierList();
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.impl.light.LightElement, org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.psi.PsiElement
        public void accept(PsiElementVisitor psiElementVisitor) {
            if (psiElementVisitor == null) {
                $$$reportNull$$$0(1);
            }
            if (psiElementVisitor instanceof JavaElementVisitor) {
                ((JavaElementVisitor) psiElementVisitor).visitMethod(this);
            } else {
                psiElementVisitor.visitElement(this);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public PsiAnnotation[] createNotNullAnnotation() {
            return new PsiAnnotation[]{PsiElementFactory.getInstance(getProject()).createAnnotationFromText(ClassInnerStuffCache.NOT_NULL_ANNOTATION_QUALIFIER, this.myClass)};
        }

        private PsiType createReturnType() {
            PsiClassType psiClassTypeCreateType = JavaPsiFacade.getElementFactory(getProject()).createType(this.myClass);
            if (this.myKind != EnumMethodKind.Values) {
                if (psiClassTypeCreateType == null) {
                    $$$reportNull$$$0(4);
                }
                return psiClassTypeCreateType;
            }
            PsiArrayType psiArrayTypeCreateArrayType = psiClassTypeCreateType.createArrayType();
            if (psiArrayTypeCreateArrayType == null) {
                $$$reportNull$$$0(3);
            }
            return psiArrayTypeCreateArrayType;
        }

        private LightModifierList createModifierList() {
            return new LightModifierList(this.myManager, getLanguage(), "public", "static") { // from class: org.jetbrains.kotlin.asJava.classes.ClassInnerStuffCache.EnumSyntheticMethod.1
                private final PsiAnnotation[] notNullAnnotations;

                private static /* synthetic */ void $$$reportNull$$$0(int i) {
                    String str = i != 1 ? "@NotNull method %s.%s must not return null" : "Argument for @NotNull parameter '%s' of %s.%s must not be null";
                    Object[] objArr = new Object[i != 1 ? 2 : 3];
                    if (i != 1) {
                        objArr[0] = "org/jetbrains/kotlin/asJava/classes/ClassInnerStuffCache$EnumSyntheticMethod$1";
                    } else {
                        objArr[0] = "qualifiedName";
                    }
                    if (i != 1) {
                        objArr[1] = "getAnnotations";
                    } else {
                        objArr[1] = "org/jetbrains/kotlin/asJava/classes/ClassInnerStuffCache$EnumSyntheticMethod$1";
                    }
                    if (i == 1) {
                        objArr[2] = "findAnnotation";
                    }
                    String str2 = String.format(str, objArr);
                    if (i == 1) {
                        throw new IllegalArgumentException(str2);
                    }
                }

                {
                    this.notNullAnnotations = EnumSyntheticMethod.this.createNotNullAnnotation();
                }

                @Override // org.jetbrains.kotlin.com.intellij.psi.impl.light.LightModifierList, org.jetbrains.kotlin.com.intellij.psi.PsiAnnotationOwner
                public PsiAnnotation[] getAnnotations() {
                    PsiAnnotation[] psiAnnotationArr = (PsiAnnotation[]) ClassInnerStuffCache.copy(this.notNullAnnotations);
                    if (psiAnnotationArr == null) {
                        $$$reportNull$$$0(0);
                    }
                    return psiAnnotationArr;
                }

                @Override // org.jetbrains.kotlin.com.intellij.psi.impl.light.LightModifierList, org.jetbrains.kotlin.com.intellij.psi.PsiAnnotationOwner
                public PsiAnnotation findAnnotation(String str) {
                    if (str == null) {
                        $$$reportNull$$$0(1);
                    }
                    PsiAnnotation psiAnnotation = this.notNullAnnotations[0];
                    if (str == psiAnnotation.getAnnotationFqName()) {
                        return psiAnnotation;
                    }
                    return null;
                }

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
                lightParameterListBuilder.addParameter(new MyKtLightParameter(PsiType.getJavaLangString(this.myManager, GlobalSearchScope.allScope(getProject())), this));
            }
            return lightParameterListBuilder;
        }

        private static final class MyKtLightParameter extends LightParameter implements KtLightParameter {
            private final KtLightMethod myMethod;

            private static /* synthetic */ void $$$reportNull$$$0(int i) {
                String str = i != 2 ? "Argument for @NotNull parameter '%s' of %s.%s must not be null" : "@NotNull method %s.%s must not return null";
                Object[] objArr = new Object[i != 2 ? 3 : 2];
                if (i == 1) {
                    objArr[0] = "declarationScope";
                } else if (i != 2) {
                    objArr[0] = ModuleXmlParser.TYPE;
                } else {
                    objArr[0] = "org/jetbrains/kotlin/asJava/classes/ClassInnerStuffCache$EnumSyntheticMethod$MyKtLightParameter";
                }
                if (i != 2) {
                    objArr[1] = "org/jetbrains/kotlin/asJava/classes/ClassInnerStuffCache$EnumSyntheticMethod$MyKtLightParameter";
                } else {
                    objArr[1] = "getMethod";
                }
                if (i != 2) {
                    objArr[2] = "<init>";
                }
                String str2 = String.format(str, objArr);
                if (i == 2) {
                    throw new IllegalStateException(str2);
                }
            }

            @Override // org.jetbrains.kotlin.asJava.elements.KtLightElement
            public KtParameter getKotlinOrigin() {
                return null;
            }

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            MyKtLightParameter(PsiType psiType, KtLightMethod ktLightMethod) {
                super("name", psiType, (PsiElement) ktLightMethod, ktLightMethod.getLanguage(), false);
                if (psiType == null) {
                    $$$reportNull$$$0(0);
                }
                if (ktLightMethod == null) {
                    $$$reportNull$$$0(1);
                }
                this.myMethod = ktLightMethod;
            }

            @Override // org.jetbrains.kotlin.asJava.elements.KtLightParameter
            public KtLightMethod getMethod() {
                KtLightMethod ktLightMethod = this.myMethod;
                if (ktLightMethod == null) {
                    $$$reportNull$$$0(2);
                }
                return ktLightMethod;
            }

            @Override // org.jetbrains.kotlin.com.intellij.psi.impl.light.LightElement, org.jetbrains.kotlin.com.intellij.psi.PsiElement
            /* JADX INFO: renamed from: getParent */
            public PsiElement getLightParent() {
                return this.myMethod;
            }

            @Override // org.jetbrains.kotlin.com.intellij.psi.impl.light.LightElement, org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.psi.PsiElement
            public PsiFile getContainingFile() {
                return this.myMethod.getContainingFile();
            }

            @Override // org.jetbrains.kotlin.com.intellij.psi.impl.light.LightElement, org.jetbrains.kotlin.com.intellij.psi.PsiElement
            public String getText() {
                return get$name();
            }

            @Override // org.jetbrains.kotlin.com.intellij.psi.impl.light.LightElement, org.jetbrains.kotlin.com.intellij.psi.PsiElement
            public TextRange getTextRange() {
                return TextRange.EMPTY_RANGE;
            }
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.impl.light.LightElement, org.jetbrains.kotlin.com.intellij.psi.PsiElement
        public int getTextOffset() {
            return this.myClass.getTextOffset();
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.impl.light.LightElement, java.util.concurrent.atomic.AtomicReference, org.jetbrains.kotlin.com.intellij.psi.PsiElement
        public String toString() {
            return this.myClass.getText();
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.impl.light.LightElement, org.jetbrains.kotlin.com.intellij.psi.PsiElement
        public String getText() {
            return "";
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.impl.light.LightElement, org.jetbrains.kotlin.com.intellij.psi.PsiElement
        public TextRange getTextRange() {
            return TextRange.EMPTY_RANGE;
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

        @Override // org.jetbrains.kotlin.com.intellij.psi.PsiMember, org.jetbrains.kotlin.com.intellij.psi.PsiJvmMember
        public KtExtensibleLightClass getContainingClass() {
            KtExtensibleLightClass ktExtensibleLightClass = this.myClass;
            if (ktExtensibleLightClass == null) {
                $$$reportNull$$$0(6);
            }
            return ktExtensibleLightClass;
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.PsiMethod
        public PsiType getReturnType() {
            return this.myReturnType;
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.PsiMethod, org.jetbrains.kotlin.com.intellij.psi.PsiParameterListOwner
        public PsiParameterList getParameterList() {
            LightParameterListBuilder lightParameterListBuilder = this.myParameterList;
            if (lightParameterListBuilder == null) {
                $$$reportNull$$$0(7);
            }
            return lightParameterListBuilder;
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.PsiMethod
        public PsiReferenceList getThrowsList() {
            LightReferenceListBuilder lightReferenceListBuilder = new LightReferenceListBuilder(this.myManager, getLanguage(), PsiReferenceList.Role.THROWS_LIST);
            if (this.myKind == EnumMethodKind.ValueOf) {
                lightReferenceListBuilder.addReference(IllegalArgumentException.class.getName());
                lightReferenceListBuilder.addReference(NullPointerException.class.getName());
            }
            return lightReferenceListBuilder;
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.PsiMethod
        public MethodSignature getSignature(PsiSubstitutor psiSubstitutor) {
            if (psiSubstitutor == null) {
                $$$reportNull$$$0(9);
            }
            MethodSignatureBackedByPsiMethod methodSignatureBackedByPsiMethodCreate = MethodSignatureBackedByPsiMethod.create(this, psiSubstitutor);
            if (methodSignatureBackedByPsiMethodCreate == null) {
                $$$reportNull$$$0(10);
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
                $$$reportNull$$$0(11);
            }
            return psiMethodArrFindSuperMethods;
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.PsiMethod
        public PsiMethod[] findSuperMethods(boolean z) {
            PsiMethod[] psiMethodArrFindSuperMethods = PsiSuperMethodImplUtil.findSuperMethods(this, z);
            if (psiMethodArrFindSuperMethods == null) {
                $$$reportNull$$$0(12);
            }
            return psiMethodArrFindSuperMethods;
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.PsiMethod
        public PsiMethod[] findSuperMethods(PsiClass psiClass) {
            PsiMethod[] psiMethodArrFindSuperMethods = PsiSuperMethodImplUtil.findSuperMethods(this, psiClass);
            if (psiMethodArrFindSuperMethods == null) {
                $$$reportNull$$$0(13);
            }
            return psiMethodArrFindSuperMethods;
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.PsiMethod
        public List<MethodSignatureBackedByPsiMethod> findSuperMethodSignaturesIncludingStatic(boolean z) {
            List<MethodSignatureBackedByPsiMethod> listFindSuperMethodSignaturesIncludingStatic = PsiSuperMethodImplUtil.findSuperMethodSignaturesIncludingStatic(this, z);
            if (listFindSuperMethodSignaturesIncludingStatic == null) {
                $$$reportNull$$$0(14);
            }
            return listFindSuperMethodSignaturesIncludingStatic;
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.PsiMethod
        @Deprecated
        public PsiMethod findDeepestSuperMethod() {
            return PsiSuperMethodImplUtil.findDeepestSuperMethod(this);
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.PsiMethod
        public PsiMethod[] findDeepestSuperMethods() {
            PsiMethod[] psiMethodArr = PsiMethod.EMPTY_ARRAY;
            if (psiMethodArr == null) {
                $$$reportNull$$$0(15);
            }
            return psiMethodArr;
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.PsiMethod, org.jetbrains.kotlin.com.intellij.psi.PsiModifierListOwner
        public PsiModifierList getModifierList() {
            LightModifierList lightModifierList = this.myModifierList;
            if (lightModifierList == null) {
                $$$reportNull$$$0(16);
            }
            return lightModifierList;
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.PsiModifierListOwner
        public boolean hasModifierProperty(String str) {
            if (str == null) {
                $$$reportNull$$$0(17);
            }
            return str.equals("public") || str.equals("static");
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.PsiMethod, org.jetbrains.kotlin.com.intellij.pom.PomRenameableTarget
        public PsiElement setName(String str) throws IncorrectOperationException {
            if (str == null) {
                $$$reportNull$$$0(18);
            }
            throw new IncorrectOperationException();
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.PsiMethod
        public HierarchicalMethodSignature getHierarchicalMethodSignature() {
            HierarchicalMethodSignature hierarchicalMethodSignature = PsiSuperMethodImplUtil.getHierarchicalMethodSignature(this);
            if (hierarchicalMethodSignature == null) {
                $$$reportNull$$$0(19);
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
                $$$reportNull$$$0(20);
            }
            return psiTypeParameterArr;
        }
    }
}
