package org.jetbrains.kotlin.cli.common.arguments;

import android.Manifest;
import androidx.constraintlayout.core.motion.utils.TypedValues;
import androidx.exifinterface.media.ExifInterface;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Reflection;
import kotlin.jvm.internal.TypeIntrinsics;
import kotlin.ranges.RangesKt;
import kotlin.reflect.KClass;
import kotlin.reflect.KMutableProperty1;
import kotlin.reflect.KProperty1;
import kotlin.reflect.KVisibility;
import kotlin.reflect.full.KClasses;
import kotlin.reflect.jvm.ReflectJvmMapping;
import org.jetbrains.kotlin.com.intellij.util.text.VersionComparatorUtil;

/* JADX INFO: compiled from: argumentUtils.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000:\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\n\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0000\u001a:\u0010\u0000\u001a\u0016\u0012\u0012\u0012\u0010\u0012\u0004\u0012\u0002H\u0003\u0012\u0006\u0012\u0004\u0018\u00010\u00040\u00020\u0001\"\b\b\u0000\u0010\u0003*\u00020\u00042\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u0002H\u00030\u00062\u0006\u0010\u0007\u001a\u00020\b\u001a\u001d\u0010\t\u001a\u0002H\u0003\"\b\b\u0000\u0010\u0003*\u00020\u00042\u0006\u0010\n\u001a\u0002H\u0003¢\u0006\u0002\u0010\u000b\u001aS\u0010\f\u001a\u0002H\u0003\"\b\b\u0000\u0010\u0003*\u00020\u00042\u0006\u0010\r\u001a\u0002H\u00032\u0006\u0010\u000e\u001a\u0002H\u00032,\b\u0002\u0010\u000f\u001a&\u0012\u0012\u0012\u0010\u0012\u0004\u0012\u0002H\u0003\u0012\u0006\u0012\u0004\u0018\u00010\u00040\u0002\u0012\u0006\u0012\u0004\u0018\u00010\u0004\u0012\u0004\u0012\u00020\b\u0018\u00010\u0010¢\u0006\u0002\u0010\u0011\u001aQ\u0010\u0012\u001a\u0002H\u0013\"\b\b\u0000\u0010\u0014*\u00020\u0004\"\b\b\u0001\u0010\u0013*\u00020\u00042\u0006\u0010\r\u001a\u0002H\u00142\u0006\u0010\u000e\u001a\u0002H\u00132 \u0010\u0015\u001a\u001c\u0012\u0012\u0012\u0010\u0012\u0004\u0012\u0002H\u0014\u0012\u0006\u0012\u0004\u0018\u00010\u00040\u0002\u0012\u0004\u0012\u00020\b0\u0016¢\u0006\u0002\u0010\u0017\u001a/\u0010\u0018\u001a\u0002H\u0013\"\b\b\u0000\u0010\u0014*\u00020\u0004\"\b\b\u0001\u0010\u0013*\u00020\u00042\u0006\u0010\r\u001a\u0002H\u00142\u0006\u0010\u000e\u001a\u0002H\u0013¢\u0006\u0002\u0010\u0019\u001a\u0083\u0001\u0010\u001a\u001a\u0002H\u0013\"\b\b\u0000\u0010\u0014*\u00020\u0004\"\b\b\u0001\u0010\u0013*\u00020\u00042\u0006\u0010\r\u001a\u0002H\u00142\u0006\u0010\u000e\u001a\u0002H\u00132\u0006\u0010\u001b\u001a\u00020\b2\u001a\u0010\u001c\u001a\u0016\u0012\u0012\u0012\u0010\u0012\u0004\u0012\u0002H\u0014\u0012\u0006\u0012\u0004\u0018\u00010\u00040\u00020\u00012,\b\u0002\u0010\u000f\u001a&\u0012\u0012\u0012\u0010\u0012\u0004\u0012\u0002H\u0014\u0012\u0006\u0012\u0004\u0018\u00010\u00040\u0002\u0012\u0006\u0012\u0004\u0018\u00010\u0004\u0012\u0004\u0012\u00020\b\u0018\u00010\u0010H\u0002¢\u0006\u0002\u0010\u001d\u001a/\u0010\u001e\u001a\u0002H\u0013\"\b\b\u0000\u0010\u0014*\u00020\u0004\"\b\b\u0001\u0010\u0013*\u0002H\u00142\u0006\u0010\r\u001a\u0002H\u00142\u0006\u0010\u000e\u001a\u0002H\u0013¢\u0006\u0002\u0010\u0019\u001a\f\u0010\u001f\u001a\u00020\u0004*\u00020\u0004H\u0002\u001a\n\u0010 \u001a\u00020!*\u00020\"¨\u0006#"}, d2 = {"collectProperties", "", "Lkotlin/reflect/KProperty1;", ExifInterface.GPS_DIRECTION_TRUE, "", "kClass", "Lkotlin/reflect/KClass;", "inheritedOnly", "", "copyBean", "bean", "(Ljava/lang/Object;)Ljava/lang/Object;", "copyBeanTo", TypedValues.TransitionType.S_FROM, "to", "filter", "Lkotlin/Function2;", "(Ljava/lang/Object;Ljava/lang/Object;Lkotlin/jvm/functions/Function2;)Ljava/lang/Object;", "copyFieldsSatisfying", "To", "From", "predicate", "Lkotlin/Function1;", "(Ljava/lang/Object;Ljava/lang/Object;Lkotlin/jvm/functions/Function1;)Ljava/lang/Object;", "copyInheritedFields", "(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;", "copyProperties", "deepCopyWhenNeeded", "propertiesToCopy", "(Ljava/lang/Object;Ljava/lang/Object;ZLjava/util/List;Lkotlin/jvm/functions/Function2;)Ljava/lang/Object;", "mergeBeans", "copyValueIfNeeded", "setApiVersionToLanguageVersionIfNeeded", "", "Lorg/jetbrains/kotlin/cli/common/arguments/CommonCompilerArguments;", "cli-common"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class ArgumentUtilsKt {
    public static final <T> T copyBean(T bean) throws IllegalAccessException, InstantiationException {
        Intrinsics.checkNotNullParameter(bean, "bean");
        Object objNewInstance = bean.getClass().newInstance();
        Intrinsics.checkNotNullExpressionValue(objNewInstance, "newInstance(...)");
        return (T) copyBeanTo$default(bean, objNewInstance, null, 4, null);
    }

    public static /* synthetic */ Object copyBeanTo$default(Object obj, Object obj2, Function2 function2, int i, Object obj3) {
        if ((i & 4) != 0) {
            function2 = null;
        }
        return copyBeanTo(obj, obj2, function2);
    }

    public static final <T> T copyBeanTo(T from, T to, Function2<? super KProperty1<T, ? extends Object>, Object, Boolean> function2) {
        Intrinsics.checkNotNullParameter(from, "from");
        Intrinsics.checkNotNullParameter(to, "to");
        return (T) copyProperties(from, to, true, collectProperties(Reflection.getOrCreateKotlinClass(from.getClass()), false), function2);
    }

    public static final <From, To extends From> To mergeBeans(From from, To to) {
        Intrinsics.checkNotNullParameter(from, "from");
        Intrinsics.checkNotNullParameter(to, "to");
        return (To) copyProperties$default(from, to, false, collectProperties(Reflection.getOrCreateKotlinClass(from.getClass()), false), null, 16, null);
    }

    public static final <From, To> To copyInheritedFields(From from, To to) {
        Intrinsics.checkNotNullParameter(from, "from");
        Intrinsics.checkNotNullParameter(to, "to");
        return (To) copyProperties$default(from, to, true, collectProperties(Reflection.getOrCreateKotlinClass(from.getClass()), true), null, 16, null);
    }

    public static final <From, To> To copyFieldsSatisfying(From from, To to, Function1<? super KProperty1<From, ? extends Object>, Boolean> predicate) {
        Intrinsics.checkNotNullParameter(from, "from");
        Intrinsics.checkNotNullParameter(to, "to");
        Intrinsics.checkNotNullParameter(predicate, "predicate");
        List<Manifest.permission> listCollectProperties = collectProperties(Reflection.getOrCreateKotlinClass(from.getClass()), false);
        ArrayList arrayList = new ArrayList();
        for (Manifest.permission permissionVar : listCollectProperties) {
            if (predicate.invoke(permissionVar).booleanValue()) {
                arrayList.add(permissionVar);
            }
        }
        return (To) copyProperties$default(from, to, true, arrayList, null, 16, null);
    }

    static /* synthetic */ Object copyProperties$default(Object obj, Object obj2, boolean z, List list, Function2 function2, int i, Object obj3) {
        if ((i & 16) != 0) {
            function2 = null;
        }
        return copyProperties(obj, obj2, z, list, function2);
    }

    private static final <From, To> To copyProperties(From from, To to, boolean z, List<? extends KProperty1<From, ? extends Object>> list, Function2<? super KProperty1<From, ? extends Object>, Object, Boolean> function2) throws IllegalAccessException, InstantiationException {
        if (Intrinsics.areEqual(from, to)) {
            return to;
        }
        Collection memberProperties = KClasses.getMemberProperties(Reflection.getOrCreateKotlinClass(to.getClass()));
        LinkedHashMap linkedHashMap = new LinkedHashMap(RangesKt.coerceAtLeast(MapsKt.mapCapacity(CollectionsKt.collectionSizeOrDefault(memberProperties, 10)), 16));
        for (Object obj : memberProperties) {
            linkedHashMap.put(((KProperty1) obj).getName(), obj);
        }
        for (KProperty1<From, ? extends Object> kProperty1 : list) {
            Object obj2 = linkedHashMap.get(kProperty1.getName());
            Object objCopyValueIfNeeded = null;
            KMutableProperty1 kMutableProperty1 = obj2 instanceof KMutableProperty1 ? (KMutableProperty1) obj2 : null;
            if (kMutableProperty1 != null) {
                Object obj3 = kProperty1.get(from);
                if (function2 == null || function2.invoke(kProperty1, obj3).booleanValue()) {
                    if (!z) {
                        objCopyValueIfNeeded = obj3;
                    } else if (obj3 != null) {
                        objCopyValueIfNeeded = copyValueIfNeeded(obj3);
                    }
                    kMutableProperty1.set(to, objCopyValueIfNeeded);
                }
            }
        }
        return to;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v13, types: [java.util.Map] */
    /* JADX WARN: Type inference failed for: r0v19 */
    /* JADX WARN: Type inference failed for: r0v22, types: [java.lang.Object] */
    /* JADX WARN: Type inference failed for: r8v7, types: [java.lang.Object] */
    private static final Object copyValueIfNeeded(Object obj) throws IllegalAccessException, InstantiationException {
        ?? AsMutableMap;
        if (obj instanceof byte[]) {
            byte[] bArr = (byte[]) obj;
            byte[] bArrCopyOf = Arrays.copyOf(bArr, bArr.length);
            Intrinsics.checkNotNullExpressionValue(bArrCopyOf, "copyOf(...)");
            return bArrCopyOf;
        }
        if (obj instanceof char[]) {
            char[] cArr = (char[]) obj;
            char[] cArrCopyOf = Arrays.copyOf(cArr, cArr.length);
            Intrinsics.checkNotNullExpressionValue(cArrCopyOf, "copyOf(...)");
            return cArrCopyOf;
        }
        if (obj instanceof short[]) {
            short[] sArr = (short[]) obj;
            short[] sArrCopyOf = Arrays.copyOf(sArr, sArr.length);
            Intrinsics.checkNotNullExpressionValue(sArrCopyOf, "copyOf(...)");
            return sArrCopyOf;
        }
        if (obj instanceof int[]) {
            int[] iArr = (int[]) obj;
            int[] iArrCopyOf = Arrays.copyOf(iArr, iArr.length);
            Intrinsics.checkNotNullExpressionValue(iArrCopyOf, "copyOf(...)");
            return iArrCopyOf;
        }
        if (obj instanceof long[]) {
            long[] jArr = (long[]) obj;
            long[] jArrCopyOf = Arrays.copyOf(jArr, jArr.length);
            Intrinsics.checkNotNullExpressionValue(jArrCopyOf, "copyOf(...)");
            return jArrCopyOf;
        }
        if (obj instanceof float[]) {
            float[] fArr = (float[]) obj;
            float[] fArrCopyOf = Arrays.copyOf(fArr, fArr.length);
            Intrinsics.checkNotNullExpressionValue(fArrCopyOf, "copyOf(...)");
            return fArrCopyOf;
        }
        if (obj instanceof double[]) {
            double[] dArr = (double[]) obj;
            double[] dArrCopyOf = Arrays.copyOf(dArr, dArr.length);
            Intrinsics.checkNotNullExpressionValue(dArrCopyOf, "copyOf(...)");
            return dArrCopyOf;
        }
        if (obj instanceof boolean[]) {
            boolean[] zArr = (boolean[]) obj;
            boolean[] zArrCopyOf = Arrays.copyOf(zArr, zArr.length);
            Intrinsics.checkNotNullExpressionValue(zArrCopyOf, "copyOf(...)");
            return zArrCopyOf;
        }
        if (obj instanceof Object[]) {
            Object[] objArr = (Object[]) obj;
            AsMutableMap = Array.newInstance(obj.getClass().getComponentType(), objArr.length);
            Intrinsics.checkNotNull(AsMutableMap, "null cannot be cast to non-null type kotlin.Array<kotlin.Any?>");
            Object[] objArr2 = (Object[]) AsMutableMap;
            Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.Array<kotlin.Any?>");
            int length = objArr.length;
            int i = 0;
            int i2 = 0;
            while (i < length) {
                Object obj2 = objArr[i];
                int i3 = i2 + 1;
                objArr2[i2] = obj2 != null ? copyValueIfNeeded(obj2) : null;
                i++;
                i2 = i3;
            }
            Intrinsics.checkNotNullExpressionValue(AsMutableMap, "apply(...)");
        } else if (!TypeIntrinsics.isMutableCollection(obj)) {
            if (!TypeIntrinsics.isMutableMap(obj)) {
                return obj;
            }
            Object objNewInstance = obj.getClass().newInstance();
            Intrinsics.checkNotNull(objNewInstance, "null cannot be cast to non-null type kotlin.collections.MutableMap<kotlin.Any?, kotlin.Any?>");
            AsMutableMap = TypeIntrinsics.asMutableMap(objNewInstance);
            for (Map.Entry entry : ((Map) obj).entrySet()) {
                Object key = entry.getKey();
                Object value = entry.getValue();
                AsMutableMap.put(key != null ? copyValueIfNeeded(key) : null, value != null ? copyValueIfNeeded(value) : null);
            }
        } else {
            Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.collections.Collection<kotlin.Any?>");
            Collection collection = (Collection) obj;
            Object objNewInstance2 = obj.getClass().newInstance();
            Intrinsics.checkNotNull(objNewInstance2, "null cannot be cast to non-null type kotlin.collections.MutableCollection<kotlin.Any?>");
            Collection collectionAsMutableCollection = TypeIntrinsics.asMutableCollection(objNewInstance2);
            Iterator it = collection.iterator();
            while (it.hasNext()) {
                Object next = it.next();
                collectionAsMutableCollection.add(next != null ? copyValueIfNeeded(next) : null);
            }
            return collectionAsMutableCollection;
        }
        return AsMutableMap;
    }

    public static final <T> List<KProperty1<T, Object>> collectProperties(KClass<T> kClass, boolean z) {
        Intrinsics.checkNotNullParameter(kClass, "kClass");
        ArrayList arrayList = new ArrayList(KClasses.getMemberProperties(kClass));
        if (z) {
            arrayList.removeAll(KClasses.getDeclaredMemberProperties(kClass));
        }
        ArrayList arrayList2 = new ArrayList();
        for (T t : arrayList) {
            KProperty1 kProperty1 = (KProperty1) t;
            if (kProperty1.getVisibility() == KVisibility.PUBLIC) {
                Intrinsics.checkNotNull(kProperty1);
                Field javaField = ReflectJvmMapping.getJavaField(kProperty1);
                if (javaField == null || !Modifier.isTransient(javaField.getModifiers())) {
                    arrayList2.add(t);
                }
            }
        }
        return arrayList2;
    }

    public static final void setApiVersionToLanguageVersionIfNeeded(CommonCompilerArguments commonCompilerArguments) {
        Intrinsics.checkNotNullParameter(commonCompilerArguments, "<this>");
        if (commonCompilerArguments.getLanguageVersion() == null || VersionComparatorUtil.compare(commonCompilerArguments.getLanguageVersion(), commonCompilerArguments.getApiVersion()) >= 0) {
            return;
        }
        commonCompilerArguments.setApiVersion(commonCompilerArguments.getLanguageVersion());
    }
}
