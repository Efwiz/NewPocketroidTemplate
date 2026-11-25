package org.jetbrains.kotlin.incremental.classpathDiff;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.name.ClassId;

/* JADX INFO: compiled from: Impact.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u001c\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\bÀ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0016\u0010\u0005\u001a\u00020\u00062\f\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\t0\bH\u0016J\u0016\u0010\n\u001a\u00020\u000b2\f\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\t0\bH\u0016R\u0014\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00010\u0004X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\f"}, d2 = {"Lorg/jetbrains/kotlin/incremental/classpathDiff/AllImpacts;", "Lorg/jetbrains/kotlin/incremental/classpathDiff/Impact;", "()V", "allImpacts", "", "getResolver", "Lorg/jetbrains/kotlin/incremental/classpathDiff/ImpactedSymbolsResolver;", "allClasses", "", "Lorg/jetbrains/kotlin/incremental/classpathDiff/AccessibleClassSnapshot;", "getReverseResolver", "Lorg/jetbrains/kotlin/incremental/classpathDiff/ImpactingClassesResolver;", "incremental-compilation-impl"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class AllImpacts implements Impact {
    public static final AllImpacts INSTANCE = new AllImpacts();
    private static final List<Impact> allImpacts = CollectionsKt.listOf((Object[]) new Impact[]{SupertypesInheritorsImpact.INSTANCE, ConstantsInCompanionObjectsImpact.INSTANCE});

    private AllImpacts() {
    }

    @Override // org.jetbrains.kotlin.incremental.classpathDiff.Impact
    public ImpactedSymbolsResolver getResolver(Iterable<? extends AccessibleClassSnapshot> allClasses) {
        Intrinsics.checkNotNullParameter(allClasses, "allClasses");
        List<Impact> list = allImpacts;
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(list, 10));
        Iterator<T> it = list.iterator();
        while (it.hasNext()) {
            arrayList.add(((Impact) it.next()).getResolver(allClasses));
        }
        final ArrayList arrayList2 = arrayList;
        return new ImpactedSymbolsResolver() { // from class: org.jetbrains.kotlin.incremental.classpathDiff.AllImpacts.getResolver.1
            @Override // org.jetbrains.kotlin.incremental.classpathDiff.ImpactedSymbolsResolver
            public Set<ClassId> getImpactedClasses(ClassId classId) {
                Intrinsics.checkNotNullParameter(classId, "classId");
                List<ImpactedSymbolsResolver> list2 = arrayList2;
                LinkedHashSet linkedHashSet = new LinkedHashSet();
                Iterator<T> it2 = list2.iterator();
                while (it2.hasNext()) {
                    CollectionsKt.addAll(linkedHashSet, ((ImpactedSymbolsResolver) it2.next()).getImpactedClasses(classId));
                }
                return linkedHashSet;
            }

            @Override // org.jetbrains.kotlin.incremental.classpathDiff.ImpactedSymbolsResolver
            public Set<ClassMembers> getImpactedClassMembers(ClassMembers classMembers) {
                Intrinsics.checkNotNullParameter(classMembers, "classMembers");
                List<ImpactedSymbolsResolver> list2 = arrayList2;
                LinkedHashSet linkedHashSet = new LinkedHashSet();
                Iterator<T> it2 = list2.iterator();
                while (it2.hasNext()) {
                    CollectionsKt.addAll(linkedHashSet, ((ImpactedSymbolsResolver) it2.next()).getImpactedClassMembers(classMembers));
                }
                return linkedHashSet;
            }
        };
    }

    @Override // org.jetbrains.kotlin.incremental.classpathDiff.Impact
    public ImpactingClassesResolver getReverseResolver(Iterable<? extends AccessibleClassSnapshot> allClasses) {
        Intrinsics.checkNotNullParameter(allClasses, "allClasses");
        List<Impact> list = allImpacts;
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(list, 10));
        Iterator<T> it = list.iterator();
        while (it.hasNext()) {
            arrayList.add(((Impact) it.next()).getReverseResolver(allClasses));
        }
        final ArrayList arrayList2 = arrayList;
        return new ImpactingClassesResolver() { // from class: org.jetbrains.kotlin.incremental.classpathDiff.AllImpacts.getReverseResolver.1
            @Override // org.jetbrains.kotlin.incremental.classpathDiff.ImpactingClassesResolver
            public Set<ClassId> getImpactingClasses(ClassId classId) {
                Intrinsics.checkNotNullParameter(classId, "classId");
                List<ImpactingClassesResolver> list2 = arrayList2;
                LinkedHashSet linkedHashSet = new LinkedHashSet();
                Iterator<T> it2 = list2.iterator();
                while (it2.hasNext()) {
                    CollectionsKt.addAll(linkedHashSet, ((ImpactingClassesResolver) it2.next()).getImpactingClasses(classId));
                }
                return linkedHashSet;
            }
        };
    }
}
