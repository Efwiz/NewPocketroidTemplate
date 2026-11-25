package org.jetbrains.kotlin.ir.backend.js.lower.inline;

import java.util.List;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.backend.common.DeclarationTransformer;
import org.jetbrains.kotlin.backend.common.Mapping;
import org.jetbrains.kotlin.ir.DeepCopyIrTreeWithDeclarationsKt;
import org.jetbrains.kotlin.ir.IrElement;
import org.jetbrains.kotlin.ir.backend.js.JsIrBackendContext;
import org.jetbrains.kotlin.ir.declarations.IrDeclaration;
import org.jetbrains.kotlin.ir.declarations.IrFunction;
import org.jetbrains.kotlin.ir.util.PatchDeclarationParentsKt;

/* JADX INFO: compiled from: SaveInlineFunctionsBeforeInlining.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0000\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0018\u0010\b\u001a\n\u0012\u0004\u0012\u00020\n\u0018\u00010\t2\u0006\u0010\u000b\u001a\u00020\nH\u0016R\u001a\u0010\u0005\u001a\u000e\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020\u00070\u0006X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\f"}, d2 = {"Lorg/jetbrains/kotlin/ir/backend/js/lower/inline/SaveInlineFunctionsBeforeInlining;", "Lorg/jetbrains/kotlin/backend/common/DeclarationTransformer;", "context", "Lorg/jetbrains/kotlin/ir/backend/js/JsIrBackendContext;", "(Lorg/jetbrains/kotlin/ir/backend/js/JsIrBackendContext;)V", "inlineFunctionsBeforeInlining", "Lorg/jetbrains/kotlin/backend/common/Mapping$Delegate;", "Lorg/jetbrains/kotlin/ir/declarations/IrFunction;", "transformFlat", "", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclaration;", "declaration", "backend.js"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class SaveInlineFunctionsBeforeInlining implements DeclarationTransformer {
    private final Mapping.Delegate<IrFunction, IrFunction> inlineFunctionsBeforeInlining;

    public SaveInlineFunctionsBeforeInlining(JsIrBackendContext context) {
        Intrinsics.checkNotNullParameter(context, "context");
        this.inlineFunctionsBeforeInlining = context.getMapping().getInlineFunctionsBeforeInlining();
    }

    @Override // org.jetbrains.kotlin.backend.common.DeclarationTransformer
    public List<IrDeclaration> transformFlat(IrDeclaration declaration) {
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        if (!(declaration instanceof IrFunction) || !((IrFunction) declaration).getIsInline()) {
            return null;
        }
        Mapping.Delegate<IrFunction, IrFunction> delegate = this.inlineFunctionsBeforeInlining;
        IrElement irElementDeepCopyWithVariables = DeepCopyIrTreeWithDeclarationsKt.deepCopyWithVariables(declaration);
        PatchDeclarationParentsKt.patchDeclarationParents((IrFunction) irElementDeepCopyWithVariables, declaration.getParent());
        Unit unit = Unit.INSTANCE;
        delegate.set(declaration, (IrFunction) irElementDeepCopyWithVariables);
        return null;
    }
}
