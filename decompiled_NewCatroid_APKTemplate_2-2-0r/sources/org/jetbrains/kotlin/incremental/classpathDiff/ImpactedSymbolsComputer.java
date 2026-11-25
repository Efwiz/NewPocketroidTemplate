package org.jetbrains.kotlin.incremental.classpathDiff;

import java.util.ArrayList;
import java.util.Map;
import java.util.Set;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.name.ClassId;
import org.jetbrains.kotlin.name.FqName;

/* JADX INFO: compiled from: ClasspathChangesComputer.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u001c\n\u0002\u0018\u0002\n\u0000\bÂ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u001c\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00042\f\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\b0\u0007¨\u0006\t"}, d2 = {"Lorg/jetbrains/kotlin/incremental/classpathDiff/ImpactedSymbolsComputer;", "", "()V", "computeImpactedSymbols", "Lorg/jetbrains/kotlin/incremental/classpathDiff/ProgramSymbolSet;", "changes", "allClasses", "", "Lorg/jetbrains/kotlin/incremental/classpathDiff/AccessibleClassSnapshot;", "incremental-compilation-impl"}, k = 1, mv = {1, 9, 0}, xi = 48)
final class ImpactedSymbolsComputer {
    public static final ImpactedSymbolsComputer INSTANCE = new ImpactedSymbolsComputer();

    private ImpactedSymbolsComputer() {
    }

    public final ProgramSymbolSet computeImpactedSymbols(ProgramSymbolSet changes, Iterable<? extends AccessibleClassSnapshot> allClasses) {
        Intrinsics.checkNotNullParameter(changes, "changes");
        Intrinsics.checkNotNullParameter(allClasses, "allClasses");
        ImpactedSymbolsResolver resolver = AllImpacts.INSTANCE.getResolver(allClasses);
        ProgramSymbolSet.Collector collector = new ProgramSymbolSet.Collector();
        collector.addClasses(BreadthFirstSearch.INSTANCE.findReachableNodes(changes.getClasses(), new ImpactedSymbolsComputer$computeImpactedSymbols$1$impactedClasses$1(resolver)));
        Map<ClassId, Set<String>> classMembers = changes.getClassMembers();
        ArrayList arrayList = new ArrayList(classMembers.size());
        for (Map.Entry<ClassId, Set<String>> entry : classMembers.entrySet()) {
            arrayList.add(new ClassMembers(entry.getKey(), entry.getValue()));
        }
        for (ClassMembers classMembers2 : BreadthFirstSearch.INSTANCE.findReachableNodes(arrayList, new ImpactedSymbolsComputer$computeImpactedSymbols$1$impactedClassMembers$1(resolver))) {
            collector.addClassMembers(classMembers2.getClassId(), classMembers2.getMemberNames());
        }
        for (Map.Entry<FqName, Set<String>> entry2 : changes.getPackageMembers().entrySet()) {
            collector.addPackageMembers(entry2.getKey(), entry2.getValue());
        }
        return collector.getResult();
    }
}
