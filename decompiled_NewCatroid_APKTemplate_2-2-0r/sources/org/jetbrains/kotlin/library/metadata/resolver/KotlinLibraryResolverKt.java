package org.jetbrains.kotlin.library.metadata.resolver;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import kotlin.Metadata;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: KotlinLibraryResolver.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000\u001a\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u001c\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0004\"-\u0010\u0000\u001a\u001e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00030\u0002\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00030\u00040\u0001j\u0002`\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007*:\u0010\b\"\u001a\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00030\u0002\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00030\u00040\u00012\u001a\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00030\u0002\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00030\u00040\u0001¨\u0006\t"}, d2 = {"TopologicalLibraryOrder", "Lkotlin/Function1;", "", "Lorg/jetbrains/kotlin/library/metadata/resolver/KotlinResolvedLibrary;", "", "Lorg/jetbrains/kotlin/library/metadata/resolver/LibraryOrder;", "getTopologicalLibraryOrder", "()Lkotlin/jvm/functions/Function1;", "LibraryOrder", "kotlin-util-klib-metadata"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class KotlinLibraryResolverKt {
    private static final Function1<Iterable<? extends KotlinResolvedLibrary>, List<KotlinResolvedLibrary>> TopologicalLibraryOrder = new Function1<Iterable<? extends KotlinResolvedLibrary>, List<KotlinResolvedLibrary>>() { // from class: org.jetbrains.kotlin.library.metadata.resolver.KotlinLibraryResolverKt$TopologicalLibraryOrder$1
        @Override // kotlin.jvm.functions.Function1
        public final List<KotlinResolvedLibrary> invoke(Iterable<? extends KotlinResolvedLibrary> input) {
            Intrinsics.checkNotNullParameter(input, "input");
            ArrayList arrayList = new ArrayList();
            LinkedHashSet linkedHashSet = new LinkedHashSet();
            LinkedHashSet linkedHashSet2 = new LinkedHashSet();
            for (KotlinResolvedLibrary kotlinResolvedLibrary : input) {
                if (!linkedHashSet.contains(kotlinResolvedLibrary)) {
                    invoke$visit(linkedHashSet, linkedHashSet2, kotlinResolvedLibrary, arrayList);
                }
            }
            return arrayList;
        }

        private static final void invoke$visit(Set<KotlinResolvedLibrary> set, Set<KotlinResolvedLibrary> set2, KotlinResolvedLibrary kotlinResolvedLibrary, List<KotlinResolvedLibrary> list) {
            if (set.contains(kotlinResolvedLibrary)) {
                return;
            }
            if (set2.contains(kotlinResolvedLibrary)) {
                throw new IllegalStateException(("Cyclic dependency in library graph for: " + kotlinResolvedLibrary.getLibrary().getLibraryName()).toString());
            }
            set2.add(kotlinResolvedLibrary);
            Iterator<T> it = kotlinResolvedLibrary.getResolvedDependencies().iterator();
            while (it.hasNext()) {
                invoke$visit(set, set2, (KotlinResolvedLibrary) it.next(), list);
            }
            set.add(kotlinResolvedLibrary);
            list.add(kotlinResolvedLibrary);
        }
    };

    public static final Function1<Iterable<? extends KotlinResolvedLibrary>, List<KotlinResolvedLibrary>> getTopologicalLibraryOrder() {
        return TopologicalLibraryOrder;
    }
}
