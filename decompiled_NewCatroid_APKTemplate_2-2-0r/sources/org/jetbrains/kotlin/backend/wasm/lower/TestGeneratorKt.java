package org.jetbrains.kotlin.backend.wasm.lower;

import java.util.Iterator;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.backend.common.lower.DeclarationIrBuilder;
import org.jetbrains.kotlin.backend.common.lower.LowerUtilsKt;
import org.jetbrains.kotlin.backend.wasm.WasmBackendContext;
import org.jetbrains.kotlin.ir.backend.js.lower.TestGenerator;
import org.jetbrains.kotlin.ir.builders.ExpressionHelpersKt;
import org.jetbrains.kotlin.ir.declarations.IrFile;
import org.jetbrains.kotlin.ir.declarations.IrModuleFragment;
import org.jetbrains.kotlin.ir.declarations.IrSimpleFunction;
import org.jetbrains.kotlin.ir.expressions.IrBlockBody;
import org.jetbrains.kotlin.ir.expressions.IrBody;

/* JADX INFO: compiled from: TestGenerator.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000\u0014\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u001a\u0016\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005¨\u0006\u0006"}, d2 = {"generateWasmTests", "", "context", "Lorg/jetbrains/kotlin/backend/wasm/WasmBackendContext;", "moduleFragment", "Lorg/jetbrains/kotlin/ir/declarations/IrModuleFragment;", "backend.wasm"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class TestGeneratorKt {
    public static final void generateWasmTests(WasmBackendContext context, IrModuleFragment moduleFragment) {
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(moduleFragment, "moduleFragment");
        TestGenerator testGenerator = new TestGenerator(context, true);
        Iterator it = CollectionsKt.toList(moduleFragment.getFiles()).iterator();
        while (it.hasNext()) {
            testGenerator.lower((IrFile) it.next());
        }
        if (context.getTestEntryPoints().isEmpty()) {
            return;
        }
        if (context.getWasmSymbols().getStartUnitTests() == null) {
            throw new IllegalArgumentException("kotlin.test package must be present".toString());
        }
        DeclarationIrBuilder declarationIrBuilderCreateIrBuilder$default = LowerUtilsKt.createIrBuilder$default(context, context.getWasmSymbols().getStartUnitTests(), 0, 0, 6, (Object) null);
        IrBody body = context.getWasmSymbols().getStartUnitTests().getOwner().getBody();
        Intrinsics.checkNotNull(body, "null cannot be cast to non-null type org.jetbrains.kotlin.ir.expressions.IrBlockBody");
        IrBlockBody irBlockBody = (IrBlockBody) body;
        Iterator<T> it2 = context.getTestEntryPoints().iterator();
        while (it2.hasNext()) {
            irBlockBody.getStatements().add(ExpressionHelpersKt.irCall(declarationIrBuilderCreateIrBuilder$default, (IrSimpleFunction) it2.next()));
        }
    }
}
