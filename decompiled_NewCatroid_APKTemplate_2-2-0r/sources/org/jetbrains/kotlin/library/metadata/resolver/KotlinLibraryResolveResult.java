package org.jetbrains.kotlin.library.metadata.resolver;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import org.jetbrains.kotlin.library.KotlinLibrary;
import org.jetbrains.kotlin.library.metadata.PackageAccessHandler;

/* JADX INFO: compiled from: KotlinLibraryResolver.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000@\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0000\n\u0002\u0010\u001c\n\u0002\u0018\u0002\n\u0002\b\u0002\bf\u0018\u00002\u00020\u0001J\u001c\u0010\u0002\u001a\u00020\u00002\u0012\u0010\u0003\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00060\u0004H&J\"\u0010\u0007\u001a\u00020\b2\u0018\u0010\t\u001a\u0014\u0012\u0004\u0012\u00020\u000b\u0012\u0004\u0012\u00020\f\u0012\u0004\u0012\u00020\b0\nH&J8\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u000b0\u000e2(\b\u0002\u0010\u000f\u001a\"\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00050\u0010\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00050\u000e\u0018\u00010\u0004j\u0004\u0018\u0001`\u0011H\u0016J8\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\u00050\u000e2(\b\u0002\u0010\u000f\u001a\"\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00050\u0010\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00050\u000e\u0018\u00010\u0004j\u0004\u0018\u0001`\u0011H&ø\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001¨\u0006\u0013À\u0006\u0001"}, d2 = {"Lorg/jetbrains/kotlin/library/metadata/resolver/KotlinLibraryResolveResult;", "", "filterRoots", "predicate", "Lkotlin/Function1;", "Lorg/jetbrains/kotlin/library/metadata/resolver/KotlinResolvedLibrary;", "", "forEach", "", "action", "Lkotlin/Function2;", "Lorg/jetbrains/kotlin/library/KotlinLibrary;", "Lorg/jetbrains/kotlin/library/metadata/PackageAccessHandler;", "getFullList", "", "order", "", "Lorg/jetbrains/kotlin/library/metadata/resolver/LibraryOrder;", "getFullResolvedList", "kotlin-util-klib-metadata"}, k = 1, mv = {1, 9, 0}, xi = 48)
public interface KotlinLibraryResolveResult {
    KotlinLibraryResolveResult filterRoots(Function1<? super KotlinResolvedLibrary, Boolean> predicate);

    void forEach(Function2<? super KotlinLibrary, ? super PackageAccessHandler, Unit> action);

    List<KotlinResolvedLibrary> getFullResolvedList(Function1<? super Iterable<? extends KotlinResolvedLibrary>, ? extends List<? extends KotlinResolvedLibrary>> order);

    /* JADX WARN: Multi-variable type inference failed */
    static /* synthetic */ List getFullList$default(KotlinLibraryResolveResult kotlinLibraryResolveResult, Function1 function1, int i, Object obj) {
        if (obj != null) {
            throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: getFullList");
        }
        if ((i & 1) != 0) {
            function1 = null;
        }
        return kotlinLibraryResolveResult.getFullList(function1);
    }

    default List<KotlinLibrary> getFullList(Function1<? super Iterable<? extends KotlinResolvedLibrary>, ? extends List<? extends KotlinResolvedLibrary>> order) {
        List<KotlinResolvedLibrary> fullResolvedList = getFullResolvedList(order);
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(fullResolvedList, 10));
        Iterator<T> it = fullResolvedList.iterator();
        while (it.hasNext()) {
            arrayList.add(((KotlinResolvedLibrary) it.next()).getLibrary());
        }
        return arrayList;
    }

    /* JADX WARN: Multi-variable type inference failed */
    static /* synthetic */ List getFullResolvedList$default(KotlinLibraryResolveResult kotlinLibraryResolveResult, Function1 function1, int i, Object obj) {
        if (obj != null) {
            throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: getFullResolvedList");
        }
        if ((i & 1) != 0) {
            function1 = null;
        }
        return kotlinLibraryResolveResult.getFullResolvedList(function1);
    }
}
