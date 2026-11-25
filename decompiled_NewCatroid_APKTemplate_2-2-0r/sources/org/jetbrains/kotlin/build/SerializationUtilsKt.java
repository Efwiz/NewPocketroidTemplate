package org.jetbrains.kotlin.build;

import androidx.exifinterface.media.ExifInterface;
import com.huawei.hms.framework.common.ContainerUtils;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.collections.ArraysKt;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.comparisons.ComparisonsKt;
import kotlin.jvm.JvmClassMappingKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Reflection;
import kotlin.ranges.RangesKt;
import kotlin.reflect.KClass;
import kotlin.reflect.KClassifier;
import kotlin.reflect.KFunction;
import kotlin.reflect.KParameter;
import kotlin.reflect.KProperty1;
import kotlin.reflect.full.KClasses;
import kotlin.text.StringsKt;
import org.jetbrains.kotlin.cli.common.arguments.ArgumentUtilsKt;

/* JADX INFO: compiled from: serializationUtils.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000*\n\u0002\b\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010$\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\b\u0003\u001a$\u0010\u0000\u001a\u0004\u0018\u0001H\u0001\"\n\b\u0000\u0010\u0001\u0018\u0001*\u00020\u00022\u0006\u0010\u0003\u001a\u00020\u0004H\u0086\b¢\u0006\u0002\u0010\u0005\u001a-\u0010\u0000\u001a\u0004\u0018\u0001H\u0001\"\b\b\u0000\u0010\u0001*\u00020\u00022\u0006\u0010\u0003\u001a\u00020\u00042\f\u0010\u0006\u001a\b\u0012\u0004\u0012\u0002H\u00010\u0007¢\u0006\u0002\u0010\b\u001a\"\u0010\t\u001a\u00020\u0004\"\n\b\u0000\u0010\u0001\u0018\u0001*\u00020\u00022\u0006\u0010\n\u001a\u0002H\u0001H\u0086\b¢\u0006\u0002\u0010\u000b\u001a+\u0010\t\u001a\u00020\u0004\"\b\b\u0000\u0010\u0001*\u00020\u00022\u0006\u0010\n\u001a\u0002H\u00012\f\u0010\u0006\u001a\b\u0012\u0004\u0012\u0002H\u00010\u0007¢\u0006\u0002\u0010\f\u001a9\u0010\r\u001a\u000e\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u00040\u000e\"\b\b\u0000\u0010\u0001*\u00020\u00022\u0006\u0010\u000f\u001a\u0002H\u00012\u000e\b\u0002\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u00040\u0011¢\u0006\u0002\u0010\u0012\u001a\u0010\u0010\u0013\u001a\u00020\u0004*\b\u0012\u0004\u0012\u00020\u00040\u0011¨\u0006\u0014"}, d2 = {"deserializeFromPlainText", ExifInterface.GPS_DIRECTION_TRUE, "", "str", "", "(Ljava/lang/String;)Ljava/lang/Object;", "klass", "Lkotlin/reflect/KClass;", "(Ljava/lang/String;Lkotlin/reflect/KClass;)Ljava/lang/Object;", "serializeToPlainText", "instance", "(Ljava/lang/Object;)Ljava/lang/String;", "(Ljava/lang/Object;Lkotlin/reflect/KClass;)Ljava/lang/String;", "transformClassToPropertiesMap", "", "classToTransform", "excludedProperties", "", "(Ljava/lang/Object;Ljava/util/List;)Ljava/util/Map;", "joinToReadableString", "kotlin-build-common"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class SerializationUtilsKt {
    public static final /* synthetic */ <T> String serializeToPlainText(T instance) {
        Intrinsics.checkNotNullParameter(instance, "instance");
        Intrinsics.reifiedOperationMarker(4, ExifInterface.GPS_DIRECTION_TRUE);
        return serializeToPlainText(instance, Reflection.getOrCreateKotlinClass(Object.class));
    }

    public static final <T> String serializeToPlainText(T instance, KClass<T> klass) {
        Intrinsics.checkNotNullParameter(instance, "instance");
        Intrinsics.checkNotNullParameter(klass, "klass");
        ArrayList arrayList = new ArrayList();
        for (KProperty1 kProperty1 : KClasses.getMemberProperties(klass)) {
            Object obj = kProperty1.get(instance);
            if (obj != null) {
                arrayList.add(kProperty1.getName() + '=' + obj);
            }
        }
        return CollectionsKt.joinToString$default(arrayList, "\n", null, null, 0, null, null, 62, null);
    }

    public static final /* synthetic */ <T> T deserializeFromPlainText(String str) {
        Intrinsics.checkNotNullParameter(str, "str");
        Intrinsics.reifiedOperationMarker(4, ExifInterface.GPS_DIRECTION_TRUE);
        return (T) deserializeFromPlainText(str, Reflection.getOrCreateKotlinClass(Object.class));
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static final <T> T deserializeFromPlainText(String str, KClass<T> klass) {
        Object objValueOf;
        Intrinsics.checkNotNullParameter(str, "str");
        Intrinsics.checkNotNullParameter(klass, "klass");
        ArrayList arrayList = new ArrayList();
        List listSplit$default = StringsKt.split$default((CharSequence) str, new String[]{"\n"}, false, 0, 6, (Object) null);
        ArrayList arrayList2 = new ArrayList();
        for (T t : listSplit$default) {
            if (!StringsKt.isBlank((String) t)) {
                arrayList2.add(t);
            }
        }
        ArrayList<String> arrayList3 = arrayList2;
        LinkedHashMap linkedHashMap = new LinkedHashMap(RangesKt.coerceAtLeast(MapsKt.mapCapacity(CollectionsKt.collectionSizeOrDefault(arrayList3, 10)), 16));
        for (String str2 : arrayList3) {
            Pair pair = TuplesKt.to(StringsKt.substringBefore$default(str2, ContainerUtils.KEY_VALUE_DELIMITER, (String) null, 2, (Object) null), StringsKt.substringAfter$default(str2, ContainerUtils.KEY_VALUE_DELIMITER, (String) null, 2, (Object) null));
            linkedHashMap.put(pair.getFirst(), pair.getSecond());
        }
        KFunction primaryConstructor = KClasses.getPrimaryConstructor(klass);
        if (primaryConstructor == null) {
            throw new IllegalStateException(JvmClassMappingKt.getJavaClass((KClass) klass) + " does not have primary constructor");
        }
        for (KParameter kParameter : CollectionsKt.sortedWith(primaryConstructor.getParameters(), new Comparator() { // from class: org.jetbrains.kotlin.build.SerializationUtilsKt$deserializeFromPlainText$$inlined$sortedBy$1
            @Override // java.util.Comparator
            public final int compare(T t2, T t3) {
                return ComparisonsKt.compareValues(Integer.valueOf(((KParameter) t2).getIndex()), Integer.valueOf(((KParameter) t3).getIndex()));
            }
        })) {
            String str3 = (String) linkedHashMap.get(kParameter.getName());
            if (str3 == null) {
                if (!kParameter.getType().isMarkedNullable()) {
                    return null;
                }
                arrayList.add(null);
            } else {
                KClassifier classifier = kParameter.getType().getClassifier();
                if (Intrinsics.areEqual(classifier, Reflection.getOrCreateKotlinClass(Integer.TYPE))) {
                    objValueOf = Integer.valueOf(Integer.parseInt(str3));
                } else if (Intrinsics.areEqual(classifier, Reflection.getOrCreateKotlinClass(Boolean.TYPE))) {
                    objValueOf = Boolean.valueOf(Boolean.parseBoolean(str3));
                } else if (!Intrinsics.areEqual(classifier, Reflection.getOrCreateKotlinClass(String.class))) {
                    objValueOf = str3;
                    throw new IllegalStateException("Unexpected property type: " + kParameter.getType());
                }
                objValueOf = str3;
                arrayList.add(objValueOf);
            }
        }
        Object[] array = arrayList.toArray(new Object[0]);
        return (T) primaryConstructor.call(Arrays.copyOf(array, array.length));
    }

    public static /* synthetic */ Map transformClassToPropertiesMap$default(Object obj, List list, int i, Object obj2) {
        if ((i & 2) != 0) {
            list = CollectionsKt.emptyList();
        }
        return transformClassToPropertiesMap(obj, list);
    }

    public static final <T> Map<String, String> transformClassToPropertiesMap(T classToTransform, List<String> excludedProperties) {
        String strValueOf;
        Intrinsics.checkNotNullParameter(classToTransform, "classToTransform");
        Intrinsics.checkNotNullParameter(excludedProperties, "excludedProperties");
        List listCollectProperties = ArgumentUtilsKt.collectProperties(Reflection.getOrCreateKotlinClass(classToTransform.getClass()), false);
        ArrayList arrayList = new ArrayList();
        for (T t : listCollectProperties) {
            if (!excludedProperties.contains(((KProperty1) t).getName())) {
                arrayList.add(t);
            }
        }
        ArrayList<KProperty1> arrayList2 = arrayList;
        LinkedHashMap linkedHashMap = new LinkedHashMap(RangesKt.coerceAtLeast(MapsKt.mapCapacity(CollectionsKt.collectionSizeOrDefault(arrayList2, 10)), 16));
        for (KProperty1 kProperty1 : arrayList2) {
            String name2 = kProperty1.getName();
            if (kProperty1.get(classToTransform) instanceof Object[]) {
                Object obj = kProperty1.get(classToTransform);
                Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.Array<*>");
                strValueOf = ArraysKt.joinToString$default((Object[]) obj, ",", (CharSequence) null, (CharSequence) null, 0, (CharSequence) null, (Function1) null, 62, (Object) null);
            } else {
                strValueOf = String.valueOf(kProperty1.get(classToTransform));
            }
            linkedHashMap.put(name2, strValueOf);
        }
        return linkedHashMap;
    }

    public static final String joinToReadableString(List<String> list) {
        Intrinsics.checkNotNullParameter(list, "<this>");
        if (list.size() > 5) {
            return CollectionsKt.joinToString$default(CollectionsKt.take(list, 5), null, null, null, 0, null, null, 63, null) + " and " + (list.size() - 5) + " more";
        }
        if (list.size() > 1) {
            return CollectionsKt.joinToString$default(CollectionsKt.dropLast(list, 1), null, null, null, 0, null, null, 63, null) + " and " + ((String) CollectionsKt.last((List) list));
        }
        return list.size() == 1 ? (String) CollectionsKt.single((List) list) : "";
    }
}
