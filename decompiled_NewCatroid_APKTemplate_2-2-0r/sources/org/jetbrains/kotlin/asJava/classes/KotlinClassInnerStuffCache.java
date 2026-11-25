package org.jetbrains.kotlin.asJava.classes;

import androidx.exifinterface.media.ExifInterface;
import com.koushikdutta.async.http.cache.ResponseCacheMiddleware;
import gnu.trove.THashMap;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import kotlin.Lazy;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.com.intellij.openapi.diagnostic.Logger;
import org.jetbrains.kotlin.com.intellij.psi.ExternallyDefinedPsiElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiClass;
import org.jetbrains.kotlin.com.intellij.psi.PsiField;
import org.jetbrains.kotlin.com.intellij.psi.PsiMethod;
import org.jetbrains.kotlin.com.intellij.psi.impl.PsiClassImplUtil;
import org.jetbrains.kotlin.com.intellij.psi.impl.PsiImplUtil;
import org.jetbrains.kotlin.com.intellij.psi.util.PsiUtil;
import org.jetbrains.kotlin.com.intellij.util.ArrayUtil;
import org.jetbrains.kotlin.metadata.jvm.deserialization.JvmProtoBufUtil;

/* JADX INFO: compiled from: KotlinClassInnerStuffCache.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000d\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\u0011\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010$\n\u0002\u0010\u000e\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u000b\u0018\u00002\u00020\u0001:\u00016B-\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00010\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\b\b\u0002\u0010\b\u001a\u00020\t¢\u0006\u0002\u0010\nJ&\u0010)\u001a\b\u0012\u0004\u0012\u0002H*0\u0011\"\b\b\u0000\u0010**\u00020\u00012\f\u0010+\u001a\b\u0012\u0004\u0012\u0002H*0,H\u0002J\u0018\u0010-\u001a\u0004\u0018\u00010\u00172\u0006\u0010.\u001a\u00020\u00162\u0006\u0010/\u001a\u00020\tJ\u0018\u00100\u001a\u0004\u0018\u00010\u001d2\u0006\u0010.\u001a\u00020\u00162\u0006\u0010/\u001a\u00020\tJ!\u00101\u001a\b\u0012\u0004\u0012\u00020\r0\f2\u0006\u0010.\u001a\u00020\u00162\u0006\u0010/\u001a\u00020\t¢\u0006\u0002\u00102J\b\u00103\u001a\u0004\u0018\u00010\rJ\n\u00104\u001a\u0004\u0018\u00010\rH\u0002J\b\u00105\u001a\u00020\tH\u0002R\u0017\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\r0\f8F¢\u0006\u0006\u001a\u0004\b\u000e\u0010\u000fRB\u0010\u0010\u001a6\u00122\u00120\u0012\f\u0012\n \u0012*\u0004\u0018\u00010\r0\r \u0012*\u0017\u0012\u000e\b\u0001\u0012\n \u0012*\u0004\u0018\u00010\r0\r0\f¢\u0006\u0002\b\u00130\f¢\u0006\u0002\b\u00130\u0011X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00010\u0005X\u0082\u0004¢\u0006\u0002\n\u0000RT\u0010\u0014\u001aH\u0012D\u0012B\u0012\f\u0012\n \u0012*\u0004\u0018\u00010\u00160\u0016\u0012\f\u0012\n \u0012*\u0004\u0018\u00010\u00170\u0017 \u0012* \u0012\f\u0012\n \u0012*\u0004\u0018\u00010\u00160\u0016\u0012\f\u0012\n \u0012*\u0004\u0018\u00010\u00170\u0017\u0018\u00010\u00150\u00150\u0011X\u0082\u0004¢\u0006\u0002\n\u0000R\u0017\u0010\u0018\u001a\b\u0012\u0004\u0012\u00020\u00170\f8F¢\u0006\u0006\u001a\u0004\b\u0019\u0010\u001aRB\u0010\u001b\u001a6\u00122\u00120\u0012\f\u0012\n \u0012*\u0004\u0018\u00010\u00170\u0017 \u0012*\u0017\u0012\u000e\b\u0001\u0012\n \u0012*\u0004\u0018\u00010\u00170\u00170\f¢\u0006\u0002\b\u00130\f¢\u0006\u0002\b\u00130\u0011X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\tX\u0082\u0004¢\u0006\u0002\n\u0000RT\u0010\u001c\u001aH\u0012D\u0012B\u0012\f\u0012\n \u0012*\u0004\u0018\u00010\u00160\u0016\u0012\f\u0012\n \u0012*\u0004\u0018\u00010\u001d0\u001d \u0012* \u0012\f\u0012\n \u0012*\u0004\u0018\u00010\u00160\u0016\u0012\f\u0012\n \u0012*\u0004\u0018\u00010\u001d0\u001d\u0018\u00010\u00150\u00150\u0011X\u0082\u0004¢\u0006\u0002\n\u0000R\u0019\u0010\u001e\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u001d0\f8F¢\u0006\u0006\u001a\u0004\b\u001f\u0010 RB\u0010!\u001a6\u00122\u00120\u0012\f\u0012\n \u0012*\u0004\u0018\u00010\u001d0\u001d \u0012*\u0017\u0012\u000e\b\u0001\u0012\n \u0012*\u0004\u0018\u00010\u001d0\u001d0\f¢\u0006\u0002\b\u00130\f¢\u0006\u0002\b\u00130\u0011X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000Rl\u0010\"\u001a`\u0012\\\u0012Z\u0012\f\u0012\n \u0012*\u0004\u0018\u00010\u00160\u0016\u0012\u0018\u0012\u0016\u0012\u0004\u0012\u00020\r \u0012*\n\u0012\u0004\u0012\u00020\r\u0018\u00010\f0\f \u0012*,\u0012\f\u0012\n \u0012*\u0004\u0018\u00010\u00160\u0016\u0012\u0018\u0012\u0016\u0012\u0004\u0012\u00020\r \u0012*\n\u0012\u0004\u0012\u00020\r\u0018\u00010\f0\f\u0018\u00010\u00150\u00150\u0011X\u0082\u0004¢\u0006\u0002\n\u0000R\u0017\u0010#\u001a\b\u0012\u0004\u0012\u00020\r0\f8F¢\u0006\u0006\u001a\u0004\b$\u0010\u000fRB\u0010%\u001a6\u00122\u00120\u0012\f\u0012\n \u0012*\u0004\u0018\u00010\r0\r \u0012*\u0017\u0012\u000e\b\u0001\u0012\n \u0012*\u0004\u0018\u00010\r0\r0\f¢\u0006\u0002\b\u00130\f¢\u0006\u0002\b\u00130\u0011X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010&\u001a\b\u0012\u0004\u0012\u00020'0\u0011X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010(\u001a\b\u0012\u0004\u0012\u00020'0\u0011X\u0082\u0004¢\u0006\u0002\n\u0000¨\u00067"}, d2 = {"Lorg/jetbrains/kotlin/asJava/classes/KotlinClassInnerStuffCache;", "", "myClass", "Lorg/jetbrains/kotlin/asJava/classes/KtExtensibleLightClass;", "dependencies", "", "lazyCreator", "Lorg/jetbrains/kotlin/asJava/classes/KotlinClassInnerStuffCache$LazyCreator;", "generateEnumMethods", "", "(Lorg/jetbrains/kotlin/asJava/classes/KtExtensibleLightClass;Ljava/util/List;Lorg/jetbrains/kotlin/asJava/classes/KotlinClassInnerStuffCache$LazyCreator;Z)V", "constructors", "", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiMethod;", "getConstructors", "()[Lcom/intellij/psi/PsiMethod;", "constructorsCache", "Lkotlin/Lazy;", JvmProtoBufUtil.PLATFORM_TYPE_ID, "Lorg/jetbrains/annotations/NotNull;", "fieldByNameCache", "", "", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiField;", "fields", "getFields", "()[Lcom/intellij/psi/PsiField;", "fieldsCache", "innerClassByNameCache", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiClass;", "innerClasses", "getInnerClasses", "()[Lcom/intellij/psi/PsiClass;", "innerClassesCache", "methodByNameCache", "methods", "getMethods", "methodsCache", "valueOfMethodCache", "Lorg/jetbrains/kotlin/asJava/classes/KotlinEnumSyntheticMethod;", "valuesMethodCache", ResponseCacheMiddleware.CACHE, ExifInterface.GPS_DIRECTION_TRUE, "initializer", "Lkotlin/Function0;", "findFieldByName", "name", "checkBases", "findInnerClassByName", "findMethodsByName", "(Ljava/lang/String;Z)[Lcom/intellij/psi/PsiMethod;", "getValueOfMethod", "getValuesMethod", "isClassNameSealed", "LazyCreator", "light-classes-base"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class KotlinClassInnerStuffCache {
    private final Lazy<PsiMethod[]> constructorsCache;
    private final List<Object> dependencies;
    private final Lazy<Map<String, PsiField>> fieldByNameCache;
    private final Lazy<PsiField[]> fieldsCache;
    private final boolean generateEnumMethods;
    private final Lazy<Map<String, PsiClass>> innerClassByNameCache;
    private final Lazy<PsiClass[]> innerClassesCache;
    private final LazyCreator lazyCreator;
    private final Lazy<Map<String, PsiMethod[]>> methodByNameCache;
    private final Lazy<PsiMethod[]> methodsCache;
    private final KtExtensibleLightClass myClass;
    private final Lazy<KotlinEnumSyntheticMethod> valueOfMethodCache;
    private final Lazy<KotlinEnumSyntheticMethod> valuesMethodCache;

    /* JADX INFO: compiled from: KotlinClassInnerStuffCache.kt */
    @Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0000\b&\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J4\u0010\u0003\u001a\b\u0012\u0004\u0012\u0002H\u00050\u0004\"\b\b\u0000\u0010\u0005*\u00020\u00012\f\u0010\u0006\u001a\b\u0012\u0004\u0012\u0002H\u00050\u00072\f\u0010\b\u001a\b\u0012\u0004\u0012\u00020\u00010\tH&¨\u0006\n"}, d2 = {"Lorg/jetbrains/kotlin/asJava/classes/KotlinClassInnerStuffCache$LazyCreator;", "", "()V", "get", "Lkotlin/Lazy;", ExifInterface.GPS_DIRECTION_TRUE, "initializer", "Lkotlin/Function0;", "dependencies", "", "light-classes-base"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static abstract class LazyCreator {
        public abstract <T> Lazy<T> get(Function0<? extends T> initializer, List<? extends Object> dependencies);
    }

    public KotlinClassInnerStuffCache(KtExtensibleLightClass myClass, List<? extends Object> dependencies, LazyCreator lazyCreator, boolean z) {
        Intrinsics.checkNotNullParameter(myClass, "myClass");
        Intrinsics.checkNotNullParameter(dependencies, "dependencies");
        Intrinsics.checkNotNullParameter(lazyCreator, "lazyCreator");
        this.myClass = myClass;
        this.dependencies = dependencies;
        this.lazyCreator = lazyCreator;
        this.generateEnumMethods = z;
        this.constructorsCache = cache(new Function0<PsiMethod[]>() { // from class: org.jetbrains.kotlin.asJava.classes.KotlinClassInnerStuffCache$constructorsCache$1
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final PsiMethod[] invoke() {
                return PsiImplUtil.getConstructors(this.this$0.myClass);
            }
        });
        this.fieldsCache = cache(new Function0<PsiField[]>() { // from class: org.jetbrains.kotlin.asJava.classes.KotlinClassInnerStuffCache$fieldsCache$1
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final PsiField[] invoke() {
                List<PsiField> ownFields = this.this$0.myClass.getOwnFields();
                Intrinsics.checkNotNullExpressionValue(ownFields, "getOwnFields(...)");
                return (PsiField[]) ArrayUtil.mergeCollections(ownFields, LightClassPlatformUtilsKt.collectAugments(this.this$0.myClass, PsiField.class), PsiField.ARRAY_FACTORY);
            }
        });
        this.methodsCache = cache(new Function0<PsiMethod[]>() { // from class: org.jetbrains.kotlin.asJava.classes.KotlinClassInnerStuffCache$methodsCache$1
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final PsiMethod[] invoke() {
                List<PsiMethod> ownMethods = this.this$0.myClass.getOwnMethods();
                Intrinsics.checkNotNullExpressionValue(ownMethods, "getOwnMethods(...)");
                List listCollectAugments = LightClassPlatformUtilsKt.collectAugments(this.this$0.myClass, PsiMethod.class);
                if (this.this$0.generateEnumMethods && this.this$0.myClass.isEnum()) {
                    ArrayList arrayList = new ArrayList(listCollectAugments.size() + 2);
                    KotlinClassInnerStuffCache kotlinClassInnerStuffCache = this.this$0;
                    ArrayList arrayList2 = arrayList;
                    CollectionsKt.addAll(arrayList2, listCollectAugments);
                    org.jetbrains.kotlin.utils.CollectionsKt.addIfNotNull(arrayList2, kotlinClassInnerStuffCache.getValuesMethod());
                    org.jetbrains.kotlin.utils.CollectionsKt.addIfNotNull(arrayList2, kotlinClassInnerStuffCache.getValueOfMethod());
                    listCollectAugments = arrayList;
                }
                return (PsiMethod[]) ArrayUtil.mergeCollections(ownMethods, listCollectAugments, PsiMethod.ARRAY_FACTORY);
            }
        });
        this.innerClassesCache = cache(new Function0<PsiClass[]>() { // from class: org.jetbrains.kotlin.asJava.classes.KotlinClassInnerStuffCache$innerClassesCache$1
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final PsiClass[] invoke() {
                List<PsiClass> ownInnerClasses = this.this$0.myClass.getOwnInnerClasses();
                Intrinsics.checkNotNullExpressionValue(ownInnerClasses, "getOwnInnerClasses(...)");
                return (PsiClass[]) ArrayUtil.mergeCollections(ownInnerClasses, LightClassPlatformUtilsKt.collectAugments(this.this$0.myClass, PsiClass.class), PsiClass.ARRAY_FACTORY);
            }
        });
        this.fieldByNameCache = cache(new Function0<Map<String, ? extends PsiField>>() { // from class: org.jetbrains.kotlin.asJava.classes.KotlinClassInnerStuffCache$fieldByNameCache$1
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final Map<String, ? extends PsiField> invoke() {
                PsiField[] fields = this.this$0.getFields();
                if (fields.length == 0) {
                    fields = null;
                }
                if (fields == null) {
                    return MapsKt.emptyMap();
                }
                THashMap tHashMap = new THashMap(fields.length);
                for (PsiField psiField : fields) {
                    tHashMap.putIfAbsent(psiField.getMyName(), psiField);
                }
                return Collections.unmodifiableMap(tHashMap);
            }
        });
        this.methodByNameCache = cache(new Function0<Map<String, ? extends PsiMethod[]>>() { // from class: org.jetbrains.kotlin.asJava.classes.KotlinClassInnerStuffCache$methodByNameCache$1
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final Map<String, ? extends PsiMethod[]> invoke() {
                PsiMethod[] methods = this.this$0.getMethods();
                if (methods.length == 0) {
                    methods = null;
                }
                if (methods == null) {
                    return MapsKt.emptyMap();
                }
                THashMap tHashMap = new THashMap();
                HashMap map = new HashMap();
                for (PsiMethod psiMethod : methods) {
                    String myName = psiMethod.getMyName();
                    Object obj = map.get(myName);
                    if (obj == null) {
                        obj = (List) new ArrayList();
                        map.put(myName, obj);
                    }
                    ((List) obj).add(psiMethod);
                }
                for (Map.Entry entry : map.entrySet()) {
                    tHashMap.put((String) entry.getKey(), ((List) entry.getValue()).toArray(new PsiMethod[0]));
                }
                return Collections.unmodifiableMap(tHashMap);
            }
        });
        this.innerClassByNameCache = cache(new Function0<Map<String, ? extends PsiClass>>() { // from class: org.jetbrains.kotlin.asJava.classes.KotlinClassInnerStuffCache$innerClassByNameCache$1
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final Map<String, ? extends PsiClass> invoke() {
                PsiClass[] innerClasses = this.this$0.getInnerClasses();
                if (innerClasses.length == 0) {
                    innerClasses = null;
                }
                if (innerClasses == null) {
                    return MapsKt.emptyMap();
                }
                THashMap tHashMap = new THashMap();
                for (PsiClass psiClass : innerClasses) {
                    String name2 = psiClass.getMyName();
                    if (name2 == null) {
                        Logger.getInstance((Class<?>) KotlinClassInnerStuffCache.class).error(psiClass);
                    } else if (!(psiClass instanceof ExternallyDefinedPsiElement) || !tHashMap.containsKey(name2)) {
                        tHashMap.put(name2, psiClass);
                    }
                }
                return Collections.unmodifiableMap(tHashMap);
            }
        });
        this.valuesMethodCache = cache(new Function0<KotlinEnumSyntheticMethod>() { // from class: org.jetbrains.kotlin.asJava.classes.KotlinClassInnerStuffCache$valuesMethodCache$1
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final KotlinEnumSyntheticMethod invoke() {
                return new KotlinEnumSyntheticMethod(this.this$0.myClass, KotlinEnumSyntheticMethod.Kind.VALUES);
            }
        });
        this.valueOfMethodCache = cache(new Function0<KotlinEnumSyntheticMethod>() { // from class: org.jetbrains.kotlin.asJava.classes.KotlinClassInnerStuffCache$valueOfMethodCache$1
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final KotlinEnumSyntheticMethod invoke() {
                return new KotlinEnumSyntheticMethod(this.this$0.myClass, KotlinEnumSyntheticMethod.Kind.VALUE_OF);
            }
        });
    }

    public /* synthetic */ KotlinClassInnerStuffCache(KtExtensibleLightClass ktExtensibleLightClass, List list, LazyCreator lazyCreator, boolean z, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(ktExtensibleLightClass, list, lazyCreator, (i & 8) != 0 ? true : z);
    }

    private final <T> Lazy<T> cache(Function0<? extends T> initializer) {
        return this.lazyCreator.get(initializer, this.dependencies);
    }

    public final PsiMethod[] getConstructors() {
        PsiMethod[] value = this.constructorsCache.getValue();
        Intrinsics.checkNotNullExpressionValue(value, "<get-value>(...)");
        return (PsiMethod[]) KotlinClassInnerStuffCacheKt.copy(value);
    }

    public final PsiField[] getFields() {
        PsiField[] value = this.fieldsCache.getValue();
        Intrinsics.checkNotNullExpressionValue(value, "<get-value>(...)");
        return (PsiField[]) KotlinClassInnerStuffCacheKt.copy(value);
    }

    public final PsiMethod[] getMethods() {
        PsiMethod[] value = this.methodsCache.getValue();
        Intrinsics.checkNotNullExpressionValue(value, "<get-value>(...)");
        return (PsiMethod[]) KotlinClassInnerStuffCacheKt.copy(value);
    }

    public final PsiClass[] getInnerClasses() {
        PsiClass[] value = this.innerClassesCache.getValue();
        Intrinsics.checkNotNullExpressionValue(value, "<get-value>(...)");
        return (PsiClass[]) KotlinClassInnerStuffCacheKt.copy(value);
    }

    public final PsiField findFieldByName(String name2, boolean checkBases) {
        Intrinsics.checkNotNullParameter(name2, "name");
        if (checkBases) {
            return PsiClassImplUtil.findFieldByName(this.myClass, name2, true);
        }
        return this.fieldByNameCache.getValue().get(name2);
    }

    public final PsiMethod[] findMethodsByName(String name2, boolean z) {
        Intrinsics.checkNotNullParameter(name2, "name");
        if (z) {
            PsiMethod[] psiMethodArrFindMethodsByName = PsiClassImplUtil.findMethodsByName(this.myClass, name2, true);
            Intrinsics.checkNotNull(psiMethodArrFindMethodsByName);
            return psiMethodArrFindMethodsByName;
        }
        PsiMethod[] psiMethodArr = this.methodByNameCache.getValue().get(name2);
        if (psiMethodArr == null) {
            psiMethodArr = PsiMethod.EMPTY_ARRAY;
        }
        Intrinsics.checkNotNull(psiMethodArr);
        return (PsiMethod[]) KotlinClassInnerStuffCacheKt.copy(psiMethodArr);
    }

    public final PsiClass findInnerClassByName(String name2, boolean checkBases) {
        Intrinsics.checkNotNullParameter(name2, "name");
        if (checkBases) {
            return PsiClassImplUtil.findInnerByName(this.myClass, name2, true);
        }
        return this.innerClassByNameCache.getValue().get(name2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final PsiMethod getValuesMethod() {
        if (!this.myClass.isEnum() || KotlinClassInnerStuffCacheKt.isAnonymous(this.myClass) || isClassNameSealed()) {
            return null;
        }
        return this.valuesMethodCache.getValue();
    }

    public final PsiMethod getValueOfMethod() {
        if (!this.myClass.isEnum() || KotlinClassInnerStuffCacheKt.isAnonymous(this.myClass)) {
            return null;
        }
        return this.valueOfMethodCache.getValue();
    }

    private final boolean isClassNameSealed() {
        return Intrinsics.areEqual(this.myClass.getMyName(), "sealed") && PsiUtil.getLanguageLevel(this.myClass).toJavaVersion().feature >= 16;
    }
}
