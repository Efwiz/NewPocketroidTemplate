package org.jetbrains.kotlin.incremental.classpathDiff;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.collections.SetsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt;
import org.jetbrains.kotlin.name.ClassId;

/* JADX INFO: compiled from: Impact.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u001c\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\bÂ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0016\u0010\u0003\u001a\u00020\u00042\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006H\u0016J\u0016\u0010\b\u001a\u00020\t2\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006H\u0016¨\u0006\n"}, d2 = {"Lorg/jetbrains/kotlin/incremental/classpathDiff/ConstantsInCompanionObjectsImpact;", "Lorg/jetbrains/kotlin/incremental/classpathDiff/Impact;", "()V", "getResolver", "Lorg/jetbrains/kotlin/incremental/classpathDiff/ImpactedSymbolsResolver;", "allClasses", "", "Lorg/jetbrains/kotlin/incremental/classpathDiff/AccessibleClassSnapshot;", "getReverseResolver", "Lorg/jetbrains/kotlin/incremental/classpathDiff/ImpactingClassesResolver;", "incremental-compilation-impl"}, k = 1, mv = {1, 9, 0}, xi = 48)
final class ConstantsInCompanionObjectsImpact implements Impact {
    public static final ConstantsInCompanionObjectsImpact INSTANCE = new ConstantsInCompanionObjectsImpact();

    private ConstantsInCompanionObjectsImpact() {
    }

    @Override // org.jetbrains.kotlin.incremental.classpathDiff.Impact
    public ImpactingClassesResolver getReverseResolver(Iterable<? extends AccessibleClassSnapshot> allClasses) {
        List<String> constantsInCompanionObject;
        Intrinsics.checkNotNullParameter(allClasses, "allClasses");
        LinkedHashSet linkedHashSet = new LinkedHashSet();
        for (AccessibleClassSnapshot accessibleClassSnapshot : allClasses) {
            ClassId classId = null;
            RegularKotlinClassSnapshot regularKotlinClassSnapshot = accessibleClassSnapshot instanceof RegularKotlinClassSnapshot ? (RegularKotlinClassSnapshot) accessibleClassSnapshot : null;
            if (regularKotlinClassSnapshot != null && (constantsInCompanionObject = regularKotlinClassSnapshot.getConstantsInCompanionObject()) != null && !constantsInCompanionObject.isEmpty()) {
                classId = accessibleClassSnapshot.getClassId();
            }
            if (classId != null) {
                linkedHashSet.add(classId);
            }
        }
        final LinkedHashSet linkedHashSet2 = linkedHashSet;
        return new ImpactingClassesResolver() { // from class: org.jetbrains.kotlin.incremental.classpathDiff.ConstantsInCompanionObjectsImpact.getReverseResolver.1
            @Override // org.jetbrains.kotlin.incremental.classpathDiff.ImpactingClassesResolver
            public Set<ClassId> getImpactingClasses(ClassId classId2) {
                Intrinsics.checkNotNullParameter(classId2, "classId");
                if (linkedHashSet2.contains(classId2)) {
                    ClassId parentClassId = classId2.getParentClassId();
                    Intrinsics.checkNotNull(parentClassId);
                    return SetsKt.setOf(parentClassId);
                }
                return SetsKt.emptySet();
            }
        };
    }

    @Override // org.jetbrains.kotlin.incremental.classpathDiff.Impact
    public ImpactedSymbolsResolver getResolver(Iterable<? extends AccessibleClassSnapshot> allClasses) {
        List<String> constantsInCompanionObject;
        Intrinsics.checkNotNullParameter(allClasses, "allClasses");
        ArrayList arrayList = new ArrayList();
        for (AccessibleClassSnapshot accessibleClassSnapshot : allClasses) {
            Pair pair = null;
            RegularKotlinClassSnapshot regularKotlinClassSnapshot = accessibleClassSnapshot instanceof RegularKotlinClassSnapshot ? (RegularKotlinClassSnapshot) accessibleClassSnapshot : null;
            if (regularKotlinClassSnapshot != null && (constantsInCompanionObject = regularKotlinClassSnapshot.getConstantsInCompanionObject()) != null && !constantsInCompanionObject.isEmpty()) {
                pair = TuplesKt.to(accessibleClassSnapshot.getClassId(), constantsInCompanionObject);
            }
            if (pair != null) {
                arrayList.add(pair);
            }
        }
        final Map map = MapsKt.toMap(arrayList);
        Set setKeySet = map.keySet();
        final LinkedHashMap linkedHashMap = new LinkedHashMap(RangesKt.coerceAtLeast(MapsKt.mapCapacity(CollectionsKt.collectionSizeOrDefault(setKeySet, 10)), 16));
        for (Object obj : setKeySet) {
            ClassId parentClassId = ((ClassId) obj).getParentClassId();
            Intrinsics.checkNotNull(parentClassId);
            linkedHashMap.put(parentClassId, obj);
        }
        return new ImpactedSymbolsResolver() { // from class: org.jetbrains.kotlin.incremental.classpathDiff.ConstantsInCompanionObjectsImpact.getResolver.1
            @Override // org.jetbrains.kotlin.incremental.classpathDiff.ImpactedSymbolsResolver
            public Set<ClassId> getImpactedClasses(ClassId classId) {
                Intrinsics.checkNotNullParameter(classId, "classId");
                return SetsKt.setOfNotNull(linkedHashMap.get(classId));
            }

            @Override // org.jetbrains.kotlin.incremental.classpathDiff.ImpactedSymbolsResolver
            public Set<ClassMembers> getImpactedClassMembers(ClassMembers classMembers) {
                Intrinsics.checkNotNullParameter(classMembers, "classMembers");
                ClassId classId = linkedHashMap.get(classMembers.getClassId());
                if (classId != null) {
                    List<String> list = map.get(classId);
                    Intrinsics.checkNotNull(list);
                    Set<ClassMembers> of = SetsKt.setOf(new ClassMembers(classId, CollectionsKt.intersect(classMembers.getMemberNames(), CollectionsKt.toSet(list))));
                    if (of != null) {
                        return of;
                    }
                }
                return SetsKt.emptySet();
            }
        };
    }
}
