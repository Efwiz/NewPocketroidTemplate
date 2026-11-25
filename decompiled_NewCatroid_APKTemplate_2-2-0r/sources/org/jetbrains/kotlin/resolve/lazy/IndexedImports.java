package org.jetbrains.kotlin.resolve.lazy;

import kotlin.Metadata;
import kotlin.collections.ArraysKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.psi.KtImportInfo;

/* JADX INFO: compiled from: LazyImportScope.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0011\n\u0002\b\u0005\n\u0002\u0010\u001c\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0016\u0018\u0000*\b\b\u0000\u0010\u0001*\u00020\u00022\u00020\u0003B\u0013\u0012\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00028\u00000\u0005¢\u0006\u0002\u0010\u0006J\u0016\u0010\n\u001a\b\u0012\u0004\u0012\u00028\u00000\u000b2\u0006\u0010\f\u001a\u00020\rH\u0016R\u0019\u0010\u0004\u001a\b\u0012\u0004\u0012\u00028\u00000\u0005¢\u0006\n\n\u0002\u0010\t\u001a\u0004\b\u0007\u0010\b¨\u0006\u000e"}, d2 = {"Lorg/jetbrains/kotlin/resolve/lazy/IndexedImports;", "I", "Lorg/jetbrains/kotlin/psi/KtImportInfo;", "", "imports", "", "([Lorg/jetbrains/kotlin/psi/KtImportInfo;)V", "getImports", "()[Lorg/jetbrains/kotlin/psi/KtImportInfo;", "[Lorg/jetbrains/kotlin/psi/KtImportInfo;", "importsForName", "", "name", "Lorg/jetbrains/kotlin/name/Name;", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
public class IndexedImports<I extends KtImportInfo> {
    private final I[] imports;

    public IndexedImports(I[] imports) {
        Intrinsics.checkNotNullParameter(imports, "imports");
        this.imports = imports;
    }

    public final I[] getImports() {
        return this.imports;
    }

    public Iterable<I> importsForName(Name name2) {
        Intrinsics.checkNotNullParameter(name2, "name");
        return ArraysKt.asIterable(this.imports);
    }
}
