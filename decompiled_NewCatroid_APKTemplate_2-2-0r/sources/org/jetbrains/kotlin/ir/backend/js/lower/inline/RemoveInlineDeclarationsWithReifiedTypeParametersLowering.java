package org.jetbrains.kotlin.ir.backend.js.lower.inline;

import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.backend.common.DeclarationTransformer;
import org.jetbrains.kotlin.backend.common.ir.IrUtilsKt;
import org.jetbrains.kotlin.ir.declarations.IrDeclaration;
import org.jetbrains.kotlin.ir.declarations.IrFunction;
import org.jetbrains.kotlin.ir.declarations.IrProperty;
import org.jetbrains.kotlin.ir.declarations.IrSimpleFunction;

/* JADX INFO: compiled from: RemoveInlineDeclarationsWithReifiedTypeParametersLowering.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0018\u0010\u0003\u001a\n\u0012\u0004\u0012\u00020\u0005\u0018\u00010\u00042\u0006\u0010\u0006\u001a\u00020\u0005H\u0016¨\u0006\u0007"}, d2 = {"Lorg/jetbrains/kotlin/ir/backend/js/lower/inline/RemoveInlineDeclarationsWithReifiedTypeParametersLowering;", "Lorg/jetbrains/kotlin/backend/common/DeclarationTransformer;", "()V", "transformFlat", "", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclaration;", "declaration", "backend.js"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class RemoveInlineDeclarationsWithReifiedTypeParametersLowering implements DeclarationTransformer {
    @Override // org.jetbrains.kotlin.backend.common.DeclarationTransformer
    public List<IrDeclaration> transformFlat(IrDeclaration declaration) {
        IrSimpleFunction getter;
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        if (((declaration instanceof IrFunction) && IrUtilsKt.isInlineFunWithReifiedParameter((IrFunction) declaration)) || ((declaration instanceof IrProperty) && (getter = ((IrProperty) declaration).getGetter()) != null && IrUtilsKt.isInlineFunWithReifiedParameter(getter))) {
            return CollectionsKt.emptyList();
        }
        return null;
    }
}
