package org.jetbrains.kotlin.incremental.classpathDiff;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.collections.SetsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt;
import org.jetbrains.kotlin.name.ClassId;
import org.jetbrains.kotlin.resolve.sam.SamConversionResolverKt;

/* JADX INFO: compiled from: JavaClassChangesComputer.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000<\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J \u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u00062\u0006\u0010\b\u001a\u00020\tH\u0002J4\u0010\n\u001a\u00020\u00042\u0006\u0010\u000b\u001a\u00020\f2\f\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u000f0\u000e2\f\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u000f0\u000e2\u0006\u0010\b\u001a\u00020\tH\u0002J\"\u0010\u0011\u001a\u00020\u00122\f\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\u00060\u000e2\f\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\u00060\u000e¨\u0006\u0015"}, d2 = {"Lorg/jetbrains/kotlin/incremental/classpathDiff/JavaClassChangesComputer;", "", "()V", "collectClassChanges", "", "currentClassSnapshot", "Lorg/jetbrains/kotlin/incremental/classpathDiff/JavaClassSnapshot;", "previousClassSnapshot", "changes", "Lorg/jetbrains/kotlin/incremental/classpathDiff/ProgramSymbolSet$Collector;", "collectClassMemberChanges", "classId", "Lorg/jetbrains/kotlin/name/ClassId;", "currentMemberSnapshots", "", "Lorg/jetbrains/kotlin/incremental/classpathDiff/JavaElementSnapshot;", "previousMemberSnapshots", "compute", "Lorg/jetbrains/kotlin/incremental/classpathDiff/ProgramSymbolSet;", "currentJavaClassSnapshots", "previousJavaClassSnapshots", "incremental-compilation-impl"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class JavaClassChangesComputer {
    public static final JavaClassChangesComputer INSTANCE = new JavaClassChangesComputer();

    private JavaClassChangesComputer() {
    }

    public final ProgramSymbolSet compute(List<JavaClassSnapshot> currentJavaClassSnapshots, List<JavaClassSnapshot> previousJavaClassSnapshots) {
        Intrinsics.checkNotNullParameter(currentJavaClassSnapshots, "currentJavaClassSnapshots");
        Intrinsics.checkNotNullParameter(previousJavaClassSnapshots, "previousJavaClassSnapshots");
        List<JavaClassSnapshot> list = currentJavaClassSnapshots;
        LinkedHashMap linkedHashMap = new LinkedHashMap(RangesKt.coerceAtLeast(MapsKt.mapCapacity(CollectionsKt.collectionSizeOrDefault(list, 10)), 16));
        for (Object obj : list) {
            linkedHashMap.put(((JavaClassSnapshot) obj).getClassId(), obj);
        }
        List<JavaClassSnapshot> list2 = previousJavaClassSnapshots;
        LinkedHashMap linkedHashMap2 = new LinkedHashMap(RangesKt.coerceAtLeast(MapsKt.mapCapacity(CollectionsKt.collectionSizeOrDefault(list2, 10)), 16));
        for (Object obj2 : list2) {
            linkedHashMap2.put(((JavaClassSnapshot) obj2).getClassId(), obj2);
        }
        Set setMinus = SetsKt.minus(linkedHashMap.keySet(), (Iterable) linkedHashMap2.keySet());
        Set setMinus2 = SetsKt.minus(linkedHashMap2.keySet(), (Iterable) linkedHashMap.keySet());
        Set<ClassId> setMinus3 = SetsKt.minus(linkedHashMap.keySet(), (Iterable) setMinus);
        ProgramSymbolSet.Collector collector = new ProgramSymbolSet.Collector();
        collector.addClasses(setMinus);
        collector.addClasses(setMinus2);
        for (ClassId classId : setMinus3) {
            JavaClassChangesComputer javaClassChangesComputer = INSTANCE;
            Object obj3 = linkedHashMap.get(classId);
            Intrinsics.checkNotNull(obj3);
            Object obj4 = linkedHashMap2.get(classId);
            Intrinsics.checkNotNull(obj4);
            javaClassChangesComputer.collectClassChanges((JavaClassSnapshot) obj3, (JavaClassSnapshot) obj4, collector);
        }
        return collector.getResult();
    }

    private final void collectClassChanges(JavaClassSnapshot currentClassSnapshot, JavaClassSnapshot previousClassSnapshot, ProgramSymbolSet.Collector changes) {
        if (currentClassSnapshot.getClassAbiHash() == previousClassSnapshot.getClassAbiHash()) {
            return;
        }
        ClassId classId = currentClassSnapshot.getClassId();
        if (!Intrinsics.areEqual(classId, previousClassSnapshot.getClassId())) {
            throw new IllegalStateException("Check failed.".toString());
        }
        if (currentClassSnapshot.getClassMemberLevelSnapshot() == null || previousClassSnapshot.getClassMemberLevelSnapshot() == null) {
            changes.addClass(classId);
        } else if (currentClassSnapshot.getClassMemberLevelSnapshot().getClassAbiExcludingMembers().getAbiHash() != previousClassSnapshot.getClassMemberLevelSnapshot().getClassAbiExcludingMembers().getAbiHash()) {
            changes.addClass(classId);
        } else {
            collectClassMemberChanges(classId, currentClassSnapshot.getClassMemberLevelSnapshot().getFieldsAbi(), previousClassSnapshot.getClassMemberLevelSnapshot().getFieldsAbi(), changes);
            collectClassMemberChanges(classId, currentClassSnapshot.getClassMemberLevelSnapshot().getMethodsAbi(), previousClassSnapshot.getClassMemberLevelSnapshot().getMethodsAbi(), changes);
        }
    }

    private final void collectClassMemberChanges(ClassId classId, List<JavaElementSnapshot> currentMemberSnapshots, List<JavaElementSnapshot> previousMemberSnapshots, ProgramSymbolSet.Collector changes) {
        List<JavaElementSnapshot> list = currentMemberSnapshots;
        LinkedHashMap linkedHashMap = new LinkedHashMap(RangesKt.coerceAtLeast(MapsKt.mapCapacity(CollectionsKt.collectionSizeOrDefault(list, 10)), 16));
        for (Object obj : list) {
            linkedHashMap.put(Long.valueOf(((JavaElementSnapshot) obj).getAbiHash()), obj);
        }
        List<JavaElementSnapshot> list2 = previousMemberSnapshots;
        LinkedHashMap linkedHashMap2 = new LinkedHashMap(RangesKt.coerceAtLeast(MapsKt.mapCapacity(CollectionsKt.collectionSizeOrDefault(list2, 10)), 16));
        for (Object obj2 : list2) {
            linkedHashMap2.put(Long.valueOf(((JavaElementSnapshot) obj2).getAbiHash()), obj2);
        }
        Set setMinus = SetsKt.minus(linkedHashMap.keySet(), (Iterable) linkedHashMap2.keySet());
        Set setMinus2 = SetsKt.minus(linkedHashMap2.keySet(), (Iterable) linkedHashMap.keySet());
        Set set = setMinus;
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(set, 10));
        Iterator it = set.iterator();
        while (it.hasNext()) {
            Object obj3 = linkedHashMap.get(Long.valueOf(((Number) it.next()).longValue()));
            Intrinsics.checkNotNull(obj3);
            arrayList.add(((JavaElementSnapshot) obj3).getName());
        }
        changes.addClassMembers(classId, arrayList);
        Set set2 = setMinus2;
        ArrayList arrayList2 = new ArrayList(CollectionsKt.collectionSizeOrDefault(set2, 10));
        Iterator it2 = set2.iterator();
        while (it2.hasNext()) {
            Object obj4 = linkedHashMap2.get(Long.valueOf(((Number) it2.next()).longValue()));
            Intrinsics.checkNotNull(obj4);
            arrayList2.add(((JavaElementSnapshot) obj4).getName());
        }
        changes.addClassMembers(classId, arrayList2);
        if (setMinus.isEmpty() && setMinus2.isEmpty()) {
            return;
        }
        String strAsString = SamConversionResolverKt.getSAM_LOOKUP_NAME().asString();
        Intrinsics.checkNotNullExpressionValue(strAsString, "asString(...)");
        changes.addClassMember(classId, strAsString);
    }
}
