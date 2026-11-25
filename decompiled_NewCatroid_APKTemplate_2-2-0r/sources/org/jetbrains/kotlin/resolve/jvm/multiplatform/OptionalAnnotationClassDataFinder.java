package org.jetbrains.kotlin.resolve.jvm.multiplatform;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt;
import org.jetbrains.kotlin.name.ClassId;
import org.jetbrains.kotlin.serialization.deserialization.ClassData;
import org.jetbrains.kotlin.serialization.deserialization.ClassDataFinder;
import org.jetbrains.kotlin.serialization.deserialization.NameResolverUtilKt;

/* JADX INFO: compiled from: OptionalAnnotationPackageFragmentProvider.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0002\b\u0005\b\u0002\u0018\u00002\u00020\u0001B\u0013\u0012\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003¢\u0006\u0002\u0010\u0005J\u0012\u0010\u000b\u001a\u0004\u0018\u00010\u00042\u0006\u0010\f\u001a\u00020\bH\u0016R\u001d\u0010\u0006\u001a\u000e\u0012\u0004\u0012\u00020\b\u0012\u0004\u0012\u00020\u00040\u0007¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\n¨\u0006\r"}, d2 = {"Lorg/jetbrains/kotlin/resolve/jvm/multiplatform/OptionalAnnotationClassDataFinder;", "Lorg/jetbrains/kotlin/serialization/deserialization/ClassDataFinder;", "classes", "", "Lorg/jetbrains/kotlin/serialization/deserialization/ClassData;", "(Ljava/util/List;)V", "classIdToData", "", "Lorg/jetbrains/kotlin/name/ClassId;", "getClassIdToData", "()Ljava/util/Map;", "findClassData", "classId", "frontend.java"}, k = 1, mv = {1, 9, 0}, xi = 48)
final class OptionalAnnotationClassDataFinder implements ClassDataFinder {
    private final Map<ClassId, ClassData> classIdToData;

    public OptionalAnnotationClassDataFinder(List<ClassData> classes) {
        Intrinsics.checkNotNullParameter(classes, "classes");
        List<ClassData> list = classes;
        LinkedHashMap linkedHashMap = new LinkedHashMap(RangesKt.coerceAtLeast(MapsKt.mapCapacity(CollectionsKt.collectionSizeOrDefault(list, 10)), 16));
        for (Object obj : list) {
            ClassData classData = (ClassData) obj;
            linkedHashMap.put(NameResolverUtilKt.getClassId(classData.getNameResolver(), classData.getClassProto().getFqName()), obj);
        }
        this.classIdToData = linkedHashMap;
    }

    public final Map<ClassId, ClassData> getClassIdToData() {
        return this.classIdToData;
    }

    @Override // org.jetbrains.kotlin.serialization.deserialization.ClassDataFinder
    public ClassData findClassData(ClassId classId) {
        Intrinsics.checkNotNullParameter(classId, "classId");
        return this.classIdToData.get(classId);
    }
}
