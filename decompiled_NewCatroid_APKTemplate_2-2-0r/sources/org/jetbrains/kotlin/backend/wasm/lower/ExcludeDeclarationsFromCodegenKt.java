package org.jetbrains.kotlin.backend.wasm.lower;

import java.util.Iterator;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.backend.wasm.WasmBackendContext;
import org.jetbrains.kotlin.backend.wasm.utils.AnnotationsKt;
import org.jetbrains.kotlin.ir.declarations.IrDeclaration;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationParent;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationWithName;
import org.jetbrains.kotlin.ir.declarations.IrFile;
import org.jetbrains.kotlin.ir.declarations.IrModuleFragment;
import org.jetbrains.kotlin.ir.util.IrUtilsKt;

/* JADX INFO: compiled from: ExcludeDeclarationsFromCodegen.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000\u0014\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u001a\u0016\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005¨\u0006\u0006"}, d2 = {"excludeDeclarationsFromCodegen", "", "context", "Lorg/jetbrains/kotlin/backend/wasm/WasmBackendContext;", "module", "Lorg/jetbrains/kotlin/ir/declarations/IrModuleFragment;", "backend.wasm"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class ExcludeDeclarationsFromCodegenKt {
    private static final boolean excludeDeclarationsFromCodegen$isExcluded(IrDeclaration irDeclaration) {
        if (AnnotationsKt.hasExcludedFromCodegenAnnotation(irDeclaration)) {
            return true;
        }
        IrDeclarationParent parent = irDeclaration.getParent();
        IrFile irFile = parent instanceof IrFile ? (IrFile) parent : null;
        return irFile != null && AnnotationsKt.hasExcludedFromCodegenAnnotation(irFile);
    }

    public static final void excludeDeclarationsFromCodegen(WasmBackendContext context, IrModuleFragment module) {
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(module, "module");
        for (IrFile irFile : module.getFiles()) {
            Iterator<IrDeclaration> it = irFile.getDeclarations().iterator();
            while (it.hasNext()) {
                IrDeclaration next = it.next();
                IrDeclarationWithName irDeclarationWithName = next instanceof IrDeclarationWithName ? (IrDeclarationWithName) next : null;
                if (irDeclarationWithName != null) {
                    IrDeclarationWithName irDeclarationWithName2 = irDeclarationWithName;
                    if (excludeDeclarationsFromCodegen$isExcluded(irDeclarationWithName2)) {
                        it.remove();
                        IrUtilsKt.addChild(context.getExcludedPackageFragment(irFile.getPackageFqName()), irDeclarationWithName2);
                    }
                }
            }
        }
    }
}
