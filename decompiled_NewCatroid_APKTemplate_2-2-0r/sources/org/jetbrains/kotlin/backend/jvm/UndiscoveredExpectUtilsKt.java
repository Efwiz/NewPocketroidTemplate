package org.jetbrains.kotlin.backend.jvm;

import java.util.Collection;
import java.util.Iterator;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.ir.util.SymbolTable;
import org.jetbrains.kotlin.psi.KtFile;
import org.jetbrains.kotlin.resolve.BindingContext;

/* JADX INFO: compiled from: UndiscoveredExpectUtils.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000\u001c\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u001e\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u001a\"\u0010\u0000\u001a\u00020\u0001*\u00020\u00022\f\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u00042\u0006\u0010\u0006\u001a\u00020\u0007H\u0000¨\u0006\b"}, d2 = {"referenceUndiscoveredExpectSymbols", "", "Lorg/jetbrains/kotlin/ir/util/SymbolTable;", "files", "", "Lorg/jetbrains/kotlin/psi/KtFile;", "bindingContext", "Lorg/jetbrains/kotlin/resolve/BindingContext;", "backend.jvm.entrypoint"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class UndiscoveredExpectUtilsKt {
    public static final void referenceUndiscoveredExpectSymbols(SymbolTable symbolTable, Collection<? extends KtFile> files, BindingContext bindingContext) {
        Intrinsics.checkNotNullParameter(symbolTable, "<this>");
        Intrinsics.checkNotNullParameter(files, "files");
        Intrinsics.checkNotNullParameter(bindingContext, "bindingContext");
        UndiscoveredExpectVisitor undiscoveredExpectVisitor = new UndiscoveredExpectVisitor(symbolTable, bindingContext);
        Iterator<T> it = files.iterator();
        while (it.hasNext()) {
            undiscoveredExpectVisitor.visitKtFile((KtFile) it.next());
        }
    }
}
