package org.jetbrains.kotlin.name;

import androidx.exifinterface.media.ExifInterface;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt;
import org.jetbrains.kotlin.com.intellij.psi.CommonClassNames;

/* JADX INFO: compiled from: StandardClassIds.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000.\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0010$\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0005\u001a\f\u0010\u0003\u001a\u00020\u0004*\u00020\u0005H\u0002\u001a\f\u0010\u0006\u001a\u00020\u0004*\u00020\u0005H\u0002\u001a\u0014\u0010\u0007\u001a\u00020\b*\u00020\u00052\u0006\u0010\t\u001a\u00020\u0004H\u0002\u001a\u0014\u0010\u0007\u001a\u00020\b*\u00020\u00052\u0006\u0010\n\u001a\u00020\u0001H\u0002\u001a\f\u0010\u000b\u001a\u00020\u0004*\u00020\u0005H\u0002\u001a\f\u0010\f\u001a\u00020\u0004*\u00020\u0005H\u0002\u001a\f\u0010\r\u001a\u00020\u0004*\u00020\u0005H\u0002\u001a\f\u0010\u000e\u001a\u00020\u0004*\u00020\u0005H\u0002\u001a\f\u0010\u000f\u001a\u00020\u0004*\u00020\u0005H\u0002\u001a\f\u0010\u0010\u001a\u00020\u0004*\u00020\u0005H\u0002\u001a0\u0010\u0011\u001a\u000e\u0012\u0004\u0012\u0002H\u0013\u0012\u0004\u0012\u0002H\u00140\u0012\"\u0004\b\u0000\u0010\u0014\"\u0004\b\u0001\u0010\u0013*\u000e\u0012\u0004\u0012\u0002H\u0014\u0012\u0004\u0012\u0002H\u00130\u0012H\u0002\u001a\f\u0010\u0015\u001a\u00020\u0004*\u00020\u0005H\u0002\u001a\f\u0010\u0016\u001a\u00020\u0004*\u00020\u0005H\u0002\u001a\f\u0010\u0017\u001a\u00020\u0004*\u00020\u0005H\u0002\u001a\f\u0010\u0018\u001a\u00020\u0004*\u00020\u0005H\u0002\u001a\f\u0010\u0019\u001a\u00020\u0004*\u00020\u001aH\u0002\u001a\f\u0010\u001b\u001a\u00020\u0004*\u00020\u0005H\u0002\u001a\f\u0010\u001c\u001a\u00020\u0004*\u00020\u0005H\u0002\u001a\f\u0010\u001d\u001a\u00020\u0004*\u00020\u0005H\u0002\u001a\f\u0010\u001e\u001a\u00020\u0004*\u00020\u0004H\u0002\"\u000e\u0010\u0000\u001a\u00020\u0001X\u0082\u0004¢\u0006\u0002\n\u0000\"\u000e\u0010\u0002\u001a\u00020\u0001X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u001f"}, d2 = {"JAVA_LANG_ANNOTATION_PACKAGE", "Lorg/jetbrains/kotlin/name/FqName;", "JAVA_LANG_PACKAGE", "annotationId", "Lorg/jetbrains/kotlin/name/ClassId;", "", "baseId", "callableId", "Lorg/jetbrains/kotlin/name/CallableId;", "classId", "packageName", "collectionsId", "concurrentId", "coroutinesId", "enumsId", "internalId", "internalIrId", "inverseMap", "", ExifInterface.GPS_MEASUREMENT_INTERRUPTED, "K", "javaAnnotationId", "javaLangId", "jvmId", "jvmInternalId", "primitiveArrayId", "Lorg/jetbrains/kotlin/name/Name;", "rangesId", "reflectId", "testId", "unsignedId", "compiler.common"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class StandardClassIdsKt {
    private static final FqName JAVA_LANG_ANNOTATION_PACKAGE;
    private static final FqName JAVA_LANG_PACKAGE;

    /* JADX INFO: Access modifiers changed from: private */
    public static final ClassId baseId(String str) {
        return new ClassId(StandardClassIds.INSTANCE.getBASE_KOTLIN_PACKAGE(), Name.identifier(str));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final ClassId unsignedId(ClassId classId) {
        return new ClassId(StandardClassIds.INSTANCE.getBASE_KOTLIN_PACKAGE(), Name.identifier("U" + classId.getShortClassName().getIdentifier()));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final ClassId reflectId(String str) {
        return new ClassId(StandardClassIds.INSTANCE.getBASE_REFLECT_PACKAGE(), Name.identifier(str));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final ClassId primitiveArrayId(Name name2) {
        return new ClassId(StandardClassIds.INSTANCE.getArray().getPackageFqName(), Name.identifier(name2.getIdentifier() + StandardClassIds.INSTANCE.getArray().getShortClassName().getIdentifier()));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final ClassId collectionsId(String str) {
        return new ClassId(StandardClassIds.INSTANCE.getBASE_COLLECTIONS_PACKAGE(), Name.identifier(str));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final ClassId rangesId(String str) {
        return new ClassId(StandardClassIds.INSTANCE.getBASE_RANGES_PACKAGE(), Name.identifier(str));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final ClassId annotationId(String str) {
        return new ClassId(StandardClassIds.INSTANCE.getBASE_ANNOTATION_PACKAGE(), Name.identifier(str));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final ClassId jvmId(String str) {
        return new ClassId(StandardClassIds.INSTANCE.getBASE_JVM_PACKAGE(), Name.identifier(str));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final ClassId jvmInternalId(String str) {
        return new ClassId(StandardClassIds.INSTANCE.getBASE_JVM_INTERNAL_PACKAGE(), Name.identifier(str));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final ClassId internalId(String str) {
        return new ClassId(StandardClassIds.INSTANCE.getBASE_INTERNAL_PACKAGE(), Name.identifier(str));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final ClassId internalIrId(String str) {
        return new ClassId(StandardClassIds.INSTANCE.getBASE_INTERNAL_IR_PACKAGE(), Name.identifier(str));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final ClassId coroutinesId(String str) {
        return new ClassId(StandardClassIds.INSTANCE.getBASE_COROUTINES_PACKAGE(), Name.identifier(str));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final ClassId enumsId(String str) {
        return new ClassId(StandardClassIds.INSTANCE.getBASE_ENUMS_PACKAGE(), Name.identifier(str));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final ClassId concurrentId(String str) {
        return new ClassId(StandardClassIds.INSTANCE.getBASE_CONCURRENT_PACKAGE(), Name.identifier(str));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final ClassId testId(String str) {
        return new ClassId(StandardClassIds.INSTANCE.getBASE_TEST_PACKAGE(), Name.identifier(str));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final CallableId callableId(String str, FqName fqName) {
        Name nameIdentifier = Name.identifier(str);
        Intrinsics.checkNotNullExpressionValue(nameIdentifier, "identifier(...)");
        return new CallableId(fqName, nameIdentifier);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final CallableId callableId(String str, ClassId classId) {
        Name nameIdentifier = Name.identifier(str);
        Intrinsics.checkNotNullExpressionValue(nameIdentifier, "identifier(...)");
        return new CallableId(classId, nameIdentifier);
    }

    static {
        FqName fqName = new FqName(CommonClassNames.DEFAULT_PACKAGE);
        JAVA_LANG_PACKAGE = fqName;
        FqName fqNameChild = fqName.child(Name.identifier("annotation"));
        Intrinsics.checkNotNullExpressionValue(fqNameChild, "child(...)");
        JAVA_LANG_ANNOTATION_PACKAGE = fqNameChild;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final ClassId javaLangId(String str) {
        return new ClassId(JAVA_LANG_PACKAGE, Name.identifier(str));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final ClassId javaAnnotationId(String str) {
        return new ClassId(JAVA_LANG_ANNOTATION_PACKAGE, Name.identifier(str));
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Multi-variable type inference failed */
    public static final <K, V> Map<V, K> inverseMap(Map<K, ? extends V> map) {
        Set<Map.Entry<K, ? extends V>> setEntrySet = map.entrySet();
        LinkedHashMap linkedHashMap = new LinkedHashMap(RangesKt.coerceAtLeast(MapsKt.mapCapacity(CollectionsKt.collectionSizeOrDefault(setEntrySet, 10)), 16));
        Iterator<T> it = setEntrySet.iterator();
        while (it.hasNext()) {
            Map.Entry entry = (Map.Entry) it.next();
            Pair pair = TuplesKt.to(entry.getValue(), entry.getKey());
            linkedHashMap.put(pair.getFirst(), pair.getSecond());
        }
        return linkedHashMap;
    }
}
