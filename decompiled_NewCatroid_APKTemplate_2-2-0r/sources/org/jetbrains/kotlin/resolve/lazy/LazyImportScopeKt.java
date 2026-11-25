package org.jetbrains.kotlin.resolve.lazy;

import java.util.ArrayList;
import java.util.Collection;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.psi.KtImportInfo;
import org.jetbrains.kotlin.storage.StorageManager;

/* JADX INFO: compiled from: LazyImportScope.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\u001c\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u001e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\u001a)\u0010\u0000\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001\"\n\b\u0000\u0010\u0002\u0018\u0001*\u00020\u00032\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0005H\u0086\b\u001a1\u0010\u0006\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001\"\n\b\u0000\u0010\u0002\u0018\u0001*\u00020\u00032\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u0002H\u00020\u00052\u0006\u0010\u0007\u001a\u00020\bH\u0086\b¨\u0006\t"}, d2 = {"makeAllUnderImportsIndexed", "Lorg/jetbrains/kotlin/resolve/lazy/IndexedImports;", "I", "Lorg/jetbrains/kotlin/psi/KtImportInfo;", "imports", "", "makeExplicitImportsIndexed", "storageManager", "Lorg/jetbrains/kotlin/storage/StorageManager;", "frontend"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class LazyImportScopeKt {
    public static final /* synthetic */ <I extends KtImportInfo> IndexedImports<I> makeAllUnderImportsIndexed(Collection<? extends I> imports) {
        Intrinsics.checkNotNullParameter(imports, "imports");
        ArrayList arrayList = new ArrayList();
        for (Object obj : imports) {
            if (((KtImportInfo) obj).isAllUnder()) {
                arrayList.add(obj);
            }
        }
        Intrinsics.reifiedOperationMarker(0, "I?");
        return new IndexedImports<>((KtImportInfo[]) arrayList.toArray(new Object[0]));
    }

    public static final /* synthetic */ <I extends KtImportInfo> IndexedImports<I> makeExplicitImportsIndexed(Collection<? extends I> imports, StorageManager storageManager) {
        Intrinsics.checkNotNullParameter(imports, "imports");
        Intrinsics.checkNotNullParameter(storageManager, "storageManager");
        ArrayList arrayList = new ArrayList();
        for (Object obj : imports) {
            if (!((KtImportInfo) obj).isAllUnder()) {
                arrayList.add(obj);
            }
        }
        Intrinsics.reifiedOperationMarker(0, "I?");
        return new ExplicitImportsIndexed((KtImportInfo[]) arrayList.toArray(new Object[0]), storageManager);
    }
}
