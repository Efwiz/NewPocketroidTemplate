package org.jetbrains.kotlin.library.metadata.resolver.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.cli.common.arguments.DevModeOverwritingStrategies;
import org.jetbrains.kotlin.com.intellij.psi.PsiTreeChangeEvent;
import org.jetbrains.kotlin.library.KotlinLibrary;
import org.jetbrains.kotlin.library.metadata.PackageAccessHandler;
import org.jetbrains.kotlin.library.metadata.resolver.KotlinLibraryResolveResult;
import org.jetbrains.kotlin.library.metadata.resolver.KotlinResolvedLibrary;

/* JADX INFO: compiled from: KotlinLibraryResolverImpl.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000F\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u001c\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\u0018\u00002\u00020\u0001B\u0013\u0012\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003¢\u0006\u0002\u0010\u0005J\u001c\u0010\u000b\u001a\u00020\u00002\u0012\u0010\f\u001a\u000e\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u000e0\rH\u0016J\"\u0010\u000f\u001a\u00020\u00102\u0018\u0010\u0011\u001a\u0014\u0012\u0004\u0012\u00020\u0013\u0012\u0004\u0012\u00020\u0014\u0012\u0004\u0012\u00020\u00100\u0012H\u0016J6\u0010\u0015\u001a\b\u0012\u0004\u0012\u00020\u00040\u00032&\u0010\u0016\u001a\"\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00040\u0017\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00040\u0003\u0018\u00010\rj\u0004\u0018\u0001`\u0018H\u0016J\b\u0010\u0019\u001a\u00020\u001aH\u0016R!\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\u00040\u00038BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\t\u0010\n\u001a\u0004\b\u0007\u0010\bR\u0014\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u001b"}, d2 = {"Lorg/jetbrains/kotlin/library/metadata/resolver/impl/KotlinLibraryResolverResultImpl;", "Lorg/jetbrains/kotlin/library/metadata/resolver/KotlinLibraryResolveResult;", PsiTreeChangeEvent.PROP_ROOTS, "", "Lorg/jetbrains/kotlin/library/metadata/resolver/KotlinResolvedLibrary;", "(Ljava/util/List;)V", DevModeOverwritingStrategies.ALL, "getAll", "()Ljava/util/List;", "all$delegate", "Lkotlin/Lazy;", "filterRoots", "predicate", "Lkotlin/Function1;", "", "forEach", "", "action", "Lkotlin/Function2;", "Lorg/jetbrains/kotlin/library/KotlinLibrary;", "Lorg/jetbrains/kotlin/library/metadata/PackageAccessHandler;", "getFullResolvedList", "order", "", "Lorg/jetbrains/kotlin/library/metadata/resolver/LibraryOrder;", "toString", "", "kotlin-util-klib-metadata"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class KotlinLibraryResolverResultImpl implements KotlinLibraryResolveResult {

    /* JADX INFO: renamed from: all$delegate, reason: from kotlin metadata */
    private final Lazy all;
    private final List<KotlinResolvedLibrary> roots;

    /* JADX WARN: Multi-variable type inference failed */
    public KotlinLibraryResolverResultImpl(List<? extends KotlinResolvedLibrary> roots) {
        Intrinsics.checkNotNullParameter(roots, "roots");
        this.roots = roots;
        this.all = LazyKt.lazy(new Function0<List<? extends KotlinResolvedLibrary>>() { // from class: org.jetbrains.kotlin.library.metadata.resolver.impl.KotlinLibraryResolverResultImpl$all$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final List<? extends KotlinResolvedLibrary> invoke() {
                ArrayList arrayList;
                LinkedHashSet linkedHashSet = new LinkedHashSet();
                linkedHashSet.addAll(this.this$0.roots);
                LinkedHashSet linkedHashSet2 = linkedHashSet;
                ArrayList list = CollectionsKt.toList(linkedHashSet2);
                do {
                    List list2 = list;
                    ArrayList arrayList2 = new ArrayList(CollectionsKt.collectionSizeOrDefault(list2, 10));
                    Iterator it = list2.iterator();
                    while (it.hasNext()) {
                        arrayList2.add(((KotlinResolvedLibrary) it.next()).getResolvedDependencies());
                    }
                    List listFlatten = CollectionsKt.flatten(arrayList2);
                    ArrayList arrayList3 = new ArrayList();
                    for (Object obj : listFlatten) {
                        if (!linkedHashSet.contains((KotlinResolvedLibrary) obj)) {
                            arrayList3.add(obj);
                        }
                    }
                    list = arrayList3;
                    arrayList = list;
                    linkedHashSet.addAll(arrayList);
                } while (!arrayList.isEmpty());
                return CollectionsKt.toList(linkedHashSet2);
            }
        });
    }

    @Override // org.jetbrains.kotlin.library.metadata.resolver.KotlinLibraryResolveResult
    public /* bridge */ /* synthetic */ KotlinLibraryResolveResult filterRoots(Function1 function1) {
        return filterRoots((Function1<? super KotlinResolvedLibrary, Boolean>) function1);
    }

    private final List<KotlinResolvedLibrary> getAll() {
        return (List) this.all.getValue();
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // org.jetbrains.kotlin.library.metadata.resolver.KotlinLibraryResolveResult
    public KotlinLibraryResolverResultImpl filterRoots(Function1<? super KotlinResolvedLibrary, Boolean> predicate) {
        Intrinsics.checkNotNullParameter(predicate, "predicate");
        List<KotlinResolvedLibrary> list = this.roots;
        ArrayList arrayList = new ArrayList();
        for (Object obj : list) {
            if (predicate.invoke(obj).booleanValue()) {
                arrayList.add(obj);
            }
        }
        return new KotlinLibraryResolverResultImpl(arrayList);
    }

    @Override // org.jetbrains.kotlin.library.metadata.resolver.KotlinLibraryResolveResult
    public List<KotlinResolvedLibrary> getFullResolvedList(Function1<? super Iterable<? extends KotlinResolvedLibrary>, ? extends List<? extends KotlinResolvedLibrary>> order) {
        List<KotlinResolvedLibrary> list;
        return (order == null || (list = (List) order.invoke(getAll())) == null) ? getAll() : list;
    }

    @Override // org.jetbrains.kotlin.library.metadata.resolver.KotlinLibraryResolveResult
    public void forEach(Function2<? super KotlinLibrary, ? super PackageAccessHandler, Unit> action) {
        Intrinsics.checkNotNullParameter(action, "action");
        for (KotlinResolvedLibrary kotlinResolvedLibrary : getAll()) {
            action.invoke(kotlinResolvedLibrary.getLibrary(), kotlinResolvedLibrary);
        }
    }

    public String toString() {
        return "roots=" + this.roots + ", all=" + getAll();
    }
}
