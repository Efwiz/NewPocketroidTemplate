package org.jetbrains.kotlin.incremental.classpathDiff;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.collections.SetsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Reflection;
import kotlin.sequences.Sequence;
import kotlin.sequences.SequencesKt;
import org.jetbrains.kotlin.name.ClassId;
import org.jetbrains.kotlin.name.FqName;

/* JADX INFO: compiled from: ProgramSymbol.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u00008\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\"\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010$\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0005\u0018\u00002\u00020\u0001:\u0001\u0018BI\b\u0002\u0012\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003\u0012\u0018\u0010\u0005\u001a\u0014\u0012\u0004\u0012\u00020\u0004\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00070\u00030\u0006\u0012\u0018\u0010\b\u001a\u0014\u0012\u0004\u0012\u00020\t\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00070\u00030\u0006¢\u0006\u0002\u0010\nJ\f\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u00120\u0011J\u0006\u0010\u0013\u001a\u00020\u0014J\u0011\u0010\u0015\u001a\u00020\u00002\u0006\u0010\u0016\u001a\u00020\u0000H\u0086\u0002J\u0006\u0010\u0017\u001a\u00020\u0007R#\u0010\u0005\u001a\u0014\u0012\u0004\u0012\u00020\u0004\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00070\u00030\u0006¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u0017\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000eR#\u0010\b\u001a\u0014\u0012\u0004\u0012\u00020\t\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00070\u00030\u0006¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\f¨\u0006\u0019"}, d2 = {"Lorg/jetbrains/kotlin/incremental/classpathDiff/ProgramSymbolSet;", "", "classes", "", "Lorg/jetbrains/kotlin/name/ClassId;", "classMembers", "", "", "packageMembers", "Lorg/jetbrains/kotlin/name/FqName;", "(Ljava/util/Set;Ljava/util/Map;Ljava/util/Map;)V", "getClassMembers", "()Ljava/util/Map;", "getClasses", "()Ljava/util/Set;", "getPackageMembers", "asSequence", "Lkotlin/sequences/Sequence;", "Lorg/jetbrains/kotlin/incremental/classpathDiff/ProgramSymbol;", "isEmpty", "", "plus", "other", "toDebugString", "Collector", "incremental-compilation-impl"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ProgramSymbolSet {
    private final Map<ClassId, Set<String>> classMembers;
    private final Set<ClassId> classes;
    private final Map<FqName, Set<String>> packageMembers;

    public /* synthetic */ ProgramSymbolSet(Set set, Map map, Map map2, DefaultConstructorMarker defaultConstructorMarker) {
        this(set, map, map2);
    }

    /* JADX WARN: Multi-variable type inference failed */
    private ProgramSymbolSet(Set<ClassId> set, Map<ClassId, ? extends Set<String>> map, Map<FqName, ? extends Set<String>> map2) {
        this.classes = set;
        this.classMembers = map;
        this.packageMembers = map2;
    }

    public final Set<ClassId> getClasses() {
        return this.classes;
    }

    public final Map<ClassId, Set<String>> getClassMembers() {
        return this.classMembers;
    }

    public final Map<FqName, Set<String>> getPackageMembers() {
        return this.packageMembers;
    }

    /* JADX WARN: Code duplicated, block: B:16:0x003b  */
    /* JADX WARN: Code duplicated, block: B:19:0x0049  */
    /* JADX WARN: Code duplicated, block: B:29:0x005e A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:30:? A[LOOP:1: B:17:0x0043->B:30:?, LOOP_END, SYNTHETIC] */
    public final boolean isEmpty() {
        Map<FqName, Set<String>> map;
        Iterator<Map.Entry<FqName, Set<String>>> it;
        if (this.classes.isEmpty()) {
            Map<ClassId, Set<String>> map2 = this.classMembers;
            if (map2.isEmpty()) {
                map = this.packageMembers;
                if (!map.isEmpty()) {
                    it = map.entrySet().iterator();
                    while (it.hasNext()) {
                        if (!it.next().getValue().isEmpty()) {
                        }
                    }
                }
                return true;
            }
            Iterator<Map.Entry<ClassId, Set<String>>> it2 = map2.entrySet().iterator();
            while (it2.hasNext()) {
                if (!it2.next().getValue().isEmpty()) {
                }
            }
            map = this.packageMembers;
            if (!map.isEmpty()) {
                it = map.entrySet().iterator();
                while (it.hasNext()) {
                    if (!it.next().getValue().isEmpty()) {
                    }
                }
            }
            return true;
        }
        return false;
    }

    public final ProgramSymbolSet plus(ProgramSymbolSet other) {
        Intrinsics.checkNotNullParameter(other, "other");
        Collector collector = new Collector();
        collector.addClasses(this.classes);
        collector.addClasses(other.classes);
        for (Map.Entry<ClassId, Set<String>> entry : this.classMembers.entrySet()) {
            collector.addClassMembers(entry.getKey(), entry.getValue());
        }
        for (Map.Entry<ClassId, Set<String>> entry2 : other.classMembers.entrySet()) {
            collector.addClassMembers(entry2.getKey(), entry2.getValue());
        }
        for (Map.Entry<FqName, Set<String>> entry3 : this.packageMembers.entrySet()) {
            collector.addPackageMembers(entry3.getKey(), entry3.getValue());
        }
        for (Map.Entry<FqName, Set<String>> entry4 : other.packageMembers.entrySet()) {
            collector.addPackageMembers(entry4.getKey(), entry4.getValue());
        }
        return collector.getResult();
    }

    public final Sequence<ProgramSymbol> asSequence() {
        return SequencesKt.plus(SequencesKt.plus(SequencesKt.map(CollectionsKt.asSequence(this.classes), new Function1<ClassId, ClassSymbol>() { // from class: org.jetbrains.kotlin.incremental.classpathDiff.ProgramSymbolSet.asSequence.1
            @Override // kotlin.jvm.functions.Function1
            public final ClassSymbol invoke(ClassId it) {
                Intrinsics.checkNotNullParameter(it, "it");
                return new ClassSymbol(it);
            }
        }), SequencesKt.flatMap(MapsKt.asSequence(this.classMembers), new Function1<Map.Entry<? extends ClassId, ? extends Set<? extends String>>, Sequence<? extends ClassMember>>() { // from class: org.jetbrains.kotlin.incremental.classpathDiff.ProgramSymbolSet.asSequence.2
            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Sequence<? extends ClassMember> invoke(Map.Entry<? extends ClassId, ? extends Set<? extends String>> entry) {
                return invoke2((Map.Entry<ClassId, ? extends Set<String>>) entry);
            }

            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
            public final Sequence<ClassMember> invoke2(Map.Entry<ClassId, ? extends Set<String>> entry) {
                Intrinsics.checkNotNullParameter(entry, "<name for destructuring parameter 0>");
                final ClassId key = entry.getKey();
                return SequencesKt.map(CollectionsKt.asSequence(entry.getValue()), new Function1<String, ClassMember>() { // from class: org.jetbrains.kotlin.incremental.classpathDiff.ProgramSymbolSet.asSequence.2.1
                    {
                        super(1);
                    }

                    @Override // kotlin.jvm.functions.Function1
                    public final ClassMember invoke(String it) {
                        Intrinsics.checkNotNullParameter(it, "it");
                        return new ClassMember(key, it);
                    }
                });
            }
        })), SequencesKt.flatMap(MapsKt.asSequence(this.packageMembers), new Function1<Map.Entry<? extends FqName, ? extends Set<? extends String>>, Sequence<? extends PackageMember>>() { // from class: org.jetbrains.kotlin.incremental.classpathDiff.ProgramSymbolSet.asSequence.3
            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Sequence<? extends PackageMember> invoke(Map.Entry<? extends FqName, ? extends Set<? extends String>> entry) {
                return invoke2((Map.Entry<FqName, ? extends Set<String>>) entry);
            }

            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
            public final Sequence<PackageMember> invoke2(Map.Entry<FqName, ? extends Set<String>> entry) {
                Intrinsics.checkNotNullParameter(entry, "<name for destructuring parameter 0>");
                final FqName key = entry.getKey();
                return SequencesKt.map(CollectionsKt.asSequence(entry.getValue()), new Function1<String, PackageMember>() { // from class: org.jetbrains.kotlin.incremental.classpathDiff.ProgramSymbolSet.asSequence.3.1
                    {
                        super(1);
                    }

                    @Override // kotlin.jvm.functions.Function1
                    public final PackageMember invoke(String it) {
                        Intrinsics.checkNotNullParameter(it, "it");
                        return new PackageMember(key, it);
                    }
                });
            }
        }));
    }

    public final String toDebugString() {
        return Reflection.getOrCreateKotlinClass(ProgramSymbolSet.class).getSimpleName() + "(classes = " + this.classes + ", classMembers = " + this.classMembers + ", packageMembers = " + this.packageMembers + ')';
    }

    /* JADX INFO: compiled from: ProgramSymbol.kt */
    @Metadata(d1 = {"\u0000<\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010%\n\u0002\u0018\u0002\n\u0002\u0010#\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0005\n\u0002\u0010\u001e\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u000e\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u0005J\u0016\u0010\u000e\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u00052\u0006\u0010\u000f\u001a\u00020\u0007J\u001c\u0010\u0010\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u00052\f\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\u00070\u0012J\u0014\u0010\u0013\u001a\u00020\f2\f\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\u00050\u0012J\u001c\u0010\u0015\u001a\u00020\f2\u0006\u0010\u0016\u001a\u00020\n2\f\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\u00070\u0012J\u0006\u0010\u0017\u001a\u00020\u0018R \u0010\u0003\u001a\u0014\u0012\u0004\u0012\u00020\u0005\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00070\u00060\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\b\u001a\b\u0012\u0004\u0012\u00020\u00050\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R \u0010\t\u001a\u0014\u0012\u0004\u0012\u00020\n\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00070\u00060\u0004X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0019"}, d2 = {"Lorg/jetbrains/kotlin/incremental/classpathDiff/ProgramSymbolSet$Collector;", "", "()V", "classMembers", "", "Lorg/jetbrains/kotlin/name/ClassId;", "", "", "classes", "packageMembers", "Lorg/jetbrains/kotlin/name/FqName;", "addClass", "", "classId", "addClassMember", "memberName", "addClassMembers", "memberNames", "", "addClasses", "classIds", "addPackageMembers", "packageFqName", "getResult", "Lorg/jetbrains/kotlin/incremental/classpathDiff/ProgramSymbolSet;", "incremental-compilation-impl"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Collector {
        private final Set<ClassId> classes = new LinkedHashSet();
        private final Map<ClassId, Set<String>> classMembers = new LinkedHashMap();
        private final Map<FqName, Set<String>> packageMembers = new LinkedHashMap();

        public final void addClass(ClassId classId) {
            Intrinsics.checkNotNullParameter(classId, "classId");
            this.classMembers.remove(classId);
            this.classes.add(classId);
        }

        public final void addClassMembers(ClassId classId, Collection<String> memberNames) {
            Intrinsics.checkNotNullParameter(classId, "classId");
            Intrinsics.checkNotNullParameter(memberNames, "memberNames");
            if (this.classes.contains(classId) || memberNames.isEmpty()) {
                return;
            }
            Map<ClassId, Set<String>> map = this.classMembers;
            LinkedHashSet linkedHashSet = map.get(classId);
            if (linkedHashSet == null) {
                linkedHashSet = new LinkedHashSet();
                map.put(classId, linkedHashSet);
            }
            linkedHashSet.addAll(memberNames);
        }

        public final void addPackageMembers(FqName packageFqName, Collection<String> memberNames) {
            Intrinsics.checkNotNullParameter(packageFqName, "packageFqName");
            Intrinsics.checkNotNullParameter(memberNames, "memberNames");
            if (memberNames.isEmpty()) {
                return;
            }
            Map<FqName, Set<String>> map = this.packageMembers;
            LinkedHashSet linkedHashSet = map.get(packageFqName);
            if (linkedHashSet == null) {
                linkedHashSet = new LinkedHashSet();
                map.put(packageFqName, linkedHashSet);
            }
            linkedHashSet.addAll(memberNames);
        }

        public final void addClasses(Collection<ClassId> classIds) {
            Intrinsics.checkNotNullParameter(classIds, "classIds");
            Iterator<T> it = classIds.iterator();
            while (it.hasNext()) {
                addClass((ClassId) it.next());
            }
        }

        public final void addClassMember(ClassId classId, String memberName) {
            Intrinsics.checkNotNullParameter(classId, "classId");
            Intrinsics.checkNotNullParameter(memberName, "memberName");
            addClassMembers(classId, SetsKt.setOf(memberName));
        }

        public final ProgramSymbolSet getResult() {
            return new ProgramSymbolSet(this.classes, this.classMembers, this.packageMembers, null);
        }
    }
}
