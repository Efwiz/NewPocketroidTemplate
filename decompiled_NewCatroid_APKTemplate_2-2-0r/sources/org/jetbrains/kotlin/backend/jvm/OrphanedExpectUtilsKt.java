package org.jetbrains.kotlin.backend.jvm;

import java.util.Iterator;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.ir.declarations.IrFile;
import org.jetbrains.kotlin.ir.declarations.IrModuleFragment;
import org.jetbrains.kotlin.ir.util.DeclarationStubGenerator;

/* JADX INFO: compiled from: OrphanedExpectUtils.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000\u0012\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u001a\u0014\u0010\u0000\u001a\u00020\u0001*\u00020\u00022\u0006\u0010\u0003\u001a\u00020\u0004H\u0000¨\u0006\u0005"}, d2 = {"stubOrphanedExpectSymbols", "", "Lorg/jetbrains/kotlin/ir/declarations/IrModuleFragment;", "stubGenerator", "Lorg/jetbrains/kotlin/ir/util/DeclarationStubGenerator;", "backend.jvm.entrypoint"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class OrphanedExpectUtilsKt {
    public static final void stubOrphanedExpectSymbols(IrModuleFragment irModuleFragment, DeclarationStubGenerator stubGenerator) {
        Intrinsics.checkNotNullParameter(irModuleFragment, "<this>");
        Intrinsics.checkNotNullParameter(stubGenerator, "stubGenerator");
        StubOrphanedExpectSymbolTransformer stubOrphanedExpectSymbolTransformer = new StubOrphanedExpectSymbolTransformer(stubGenerator);
        Iterator<T> it = irModuleFragment.getFiles().iterator();
        while (it.hasNext()) {
            stubOrphanedExpectSymbolTransformer.visitFile((IrFile) it.next());
        }
    }
}
