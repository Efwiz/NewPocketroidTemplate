package org.jetbrains.kotlin.incremental.classpathDiff;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.collections.SetsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt;
import org.jetbrains.kotlin.name.ClassId;
import org.jetbrains.kotlin.resolve.jvm.JvmClassName;

/* JADX INFO: compiled from: Impact.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0002\u0010\"\n\u0000\n\u0002\u0010\u001c\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\bÂ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J(\u0010\u0003\u001a\u0014\u0012\u0004\u0012\u00020\u0005\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00050\u00060\u00042\f\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\t0\bH\u0002J(\u0010\n\u001a\u0014\u0012\u0004\u0012\u00020\u0005\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00050\u00060\u00042\f\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\t0\bH\u0002J\u0016\u0010\u000b\u001a\u00020\f2\f\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\t0\bH\u0016J\u0016\u0010\r\u001a\u00020\u000e2\f\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\t0\bH\u0016¨\u0006\u000f"}, d2 = {"Lorg/jetbrains/kotlin/incremental/classpathDiff/SupertypesInheritorsImpact;", "Lorg/jetbrains/kotlin/incremental/classpathDiff/Impact;", "()V", "getClassIdToSubclassesMap", "", "Lorg/jetbrains/kotlin/name/ClassId;", "", "allClasses", "", "Lorg/jetbrains/kotlin/incremental/classpathDiff/AccessibleClassSnapshot;", "getClassIdToSupertypesMap", "getResolver", "Lorg/jetbrains/kotlin/incremental/classpathDiff/ImpactedSymbolsResolver;", "getReverseResolver", "Lorg/jetbrains/kotlin/incremental/classpathDiff/ImpactingClassesResolver;", "incremental-compilation-impl"}, k = 1, mv = {1, 9, 0}, xi = 48)
final class SupertypesInheritorsImpact implements Impact {
    public static final SupertypesInheritorsImpact INSTANCE = new SupertypesInheritorsImpact();

    private SupertypesInheritorsImpact() {
    }

    @Override // org.jetbrains.kotlin.incremental.classpathDiff.Impact
    public ImpactedSymbolsResolver getResolver(Iterable<? extends AccessibleClassSnapshot> allClasses) {
        Intrinsics.checkNotNullParameter(allClasses, "allClasses");
        final Map<ClassId, Set<ClassId>> classIdToSubclassesMap = getClassIdToSubclassesMap(allClasses);
        return new ImpactedSymbolsResolver() { // from class: org.jetbrains.kotlin.incremental.classpathDiff.SupertypesInheritorsImpact.getResolver.1
            @Override // org.jetbrains.kotlin.incremental.classpathDiff.ImpactedSymbolsResolver
            public Set<ClassId> getImpactedClasses(ClassId classId) {
                Intrinsics.checkNotNullParameter(classId, "classId");
                Set<ClassId> set = classIdToSubclassesMap.get(classId);
                return set == null ? SetsKt.emptySet() : set;
            }

            @Override // org.jetbrains.kotlin.incremental.classpathDiff.ImpactedSymbolsResolver
            public Set<ClassMembers> getImpactedClassMembers(ClassMembers classMembers) {
                Intrinsics.checkNotNullParameter(classMembers, "classMembers");
                Set<ClassId> set = classIdToSubclassesMap.get(classMembers.getClassId());
                if (set == null) {
                    return SetsKt.emptySet();
                }
                LinkedHashSet linkedHashSet = new LinkedHashSet();
                Iterator<T> it = set.iterator();
                while (it.hasNext()) {
                    linkedHashSet.add(new ClassMembers((ClassId) it.next(), classMembers.getMemberNames()));
                }
                return linkedHashSet;
            }
        };
    }

    @Override // org.jetbrains.kotlin.incremental.classpathDiff.Impact
    public ImpactingClassesResolver getReverseResolver(Iterable<? extends AccessibleClassSnapshot> allClasses) {
        Intrinsics.checkNotNullParameter(allClasses, "allClasses");
        final Map<ClassId, Set<ClassId>> classIdToSupertypesMap = getClassIdToSupertypesMap(allClasses);
        return new ImpactingClassesResolver() { // from class: org.jetbrains.kotlin.incremental.classpathDiff.SupertypesInheritorsImpact.getReverseResolver.1
            @Override // org.jetbrains.kotlin.incremental.classpathDiff.ImpactingClassesResolver
            public Set<ClassId> getImpactingClasses(ClassId classId) {
                Intrinsics.checkNotNullParameter(classId, "classId");
                Set<ClassId> set = classIdToSupertypesMap.get(classId);
                return set == null ? SetsKt.emptySet() : set;
            }
        };
    }

    private final Map<ClassId, Set<ClassId>> getClassIdToSubclassesMap(Iterable<? extends AccessibleClassSnapshot> allClasses) {
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        for (Map.Entry<ClassId, Set<ClassId>> entry : getClassIdToSupertypesMap(allClasses).entrySet()) {
            ClassId key = entry.getKey();
            for (ClassId classId : entry.getValue()) {
                Object obj = linkedHashMap.get(classId);
                if (obj == null) {
                    obj = (Set) new LinkedHashSet();
                    linkedHashMap.put(classId, obj);
                }
                ((Set) obj).add(key);
            }
        }
        return linkedHashMap;
    }

    private final Map<ClassId, Set<ClassId>> getClassIdToSupertypesMap(Iterable<? extends AccessibleClassSnapshot> allClasses) {
        LinkedHashSet linkedHashSetEmptySet;
        LinkedHashMap linkedHashMap = new LinkedHashMap(RangesKt.coerceAtLeast(MapsKt.mapCapacity(CollectionsKt.collectionSizeOrDefault(allClasses, 10)), 16));
        for (AccessibleClassSnapshot accessibleClassSnapshot : allClasses) {
            Pair pair = TuplesKt.to(JvmClassName.byClassId(accessibleClassSnapshot.getClassId()), accessibleClassSnapshot.getClassId());
            linkedHashMap.put(pair.getFirst(), pair.getSecond());
        }
        ArrayList arrayList = new ArrayList();
        for (AccessibleClassSnapshot accessibleClassSnapshot2 : allClasses) {
            if (!(accessibleClassSnapshot2 instanceof RegularKotlinClassSnapshot)) {
                if (accessibleClassSnapshot2 instanceof PackageFacadeKotlinClassSnapshot ? true : accessibleClassSnapshot2 instanceof MultifileClassKotlinClassSnapshot) {
                    linkedHashSetEmptySet = SetsKt.emptySet();
                } else if (accessibleClassSnapshot2 instanceof JavaClassSnapshot) {
                    List<JvmClassName> supertypes = ((JavaClassSnapshot) accessibleClassSnapshot2).getSupertypes();
                    LinkedHashSet linkedHashSet = new LinkedHashSet();
                    Iterator<T> it = supertypes.iterator();
                    while (it.hasNext()) {
                        ClassId classId = (ClassId) linkedHashMap.get((JvmClassName) it.next());
                        if (classId != null) {
                            linkedHashSet.add(classId);
                        }
                    }
                    linkedHashSetEmptySet = linkedHashSet;
                } else {
                    throw new NoWhenBranchMatchedException();
                }
            } else {
                List<JvmClassName> supertypes2 = ((RegularKotlinClassSnapshot) accessibleClassSnapshot2).getSupertypes();
                LinkedHashSet linkedHashSet2 = new LinkedHashSet();
                Iterator<T> it2 = supertypes2.iterator();
                while (it2.hasNext()) {
                    ClassId classId2 = (ClassId) linkedHashMap.get((JvmClassName) it2.next());
                    if (classId2 != null) {
                        linkedHashSet2.add(classId2);
                    }
                }
                linkedHashSetEmptySet = linkedHashSet2;
            }
            Pair pair2 = !linkedHashSetEmptySet.isEmpty() ? TuplesKt.to(accessibleClassSnapshot2.getClassId(), linkedHashSetEmptySet) : null;
            if (pair2 != null) {
                arrayList.add(pair2);
            }
        }
        return MapsKt.toMap(arrayList);
    }
}
