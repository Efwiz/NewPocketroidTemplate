package org.jetbrains.kotlin.ir.backend.js.lower;

import java.util.Iterator;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.ir.backend.js.JsIrBackendContext;
import org.jetbrains.kotlin.ir.declarations.IrFile;
import org.jetbrains.kotlin.ir.declarations.IrModuleFragment;

/* JADX INFO: compiled from: CollectNativeImplementations.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000\u0014\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u001a\u0016\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005¨\u0006\u0006"}, d2 = {"collectNativeImplementations", "", "context", "Lorg/jetbrains/kotlin/ir/backend/js/JsIrBackendContext;", "moduleFragment", "Lorg/jetbrains/kotlin/ir/declarations/IrModuleFragment;", "backend.js"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class CollectNativeImplementationsKt {
    public static final void collectNativeImplementations(JsIrBackendContext context, IrModuleFragment moduleFragment) {
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(moduleFragment, "moduleFragment");
        CollectNativeImplementationsVisitor collectNativeImplementationsVisitor = new CollectNativeImplementationsVisitor(context);
        Iterator<T> it = moduleFragment.getFiles().iterator();
        while (it.hasNext()) {
            ((IrFile) it.next()).accept(collectNativeImplementationsVisitor, null);
        }
    }
}
