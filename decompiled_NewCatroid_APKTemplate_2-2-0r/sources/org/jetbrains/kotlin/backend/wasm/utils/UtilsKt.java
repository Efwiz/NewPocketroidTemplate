package org.jetbrains.kotlin.backend.wasm.utils;

import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.descriptors.Modality;
import org.jetbrains.kotlin.ir.IrBuiltIns;
import org.jetbrains.kotlin.ir.declarations.IrClass;
import org.jetbrains.kotlin.ir.declarations.IrVariable;
import org.jetbrains.kotlin.ir.expressions.IrCatch;
import org.jetbrains.kotlin.ir.expressions.IrTry;
import org.jetbrains.kotlin.ir.symbols.IrVariableSymbol;

/* JADX INFO: compiled from: Utils.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000\u001a\n\u0000\n\u0002\u0010\u000b\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u001a\u0014\u0010\u0004\u001a\u00020\u0001*\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u0007H\u0000\"\u0018\u0010\u0000\u001a\u00020\u0001*\u00020\u00028@X\u0080\u0004¢\u0006\u0006\u001a\u0004\b\u0000\u0010\u0003¨\u0006\b"}, d2 = {"isAbstractOrSealed", "", "Lorg/jetbrains/kotlin/ir/declarations/IrClass;", "(Lorg/jetbrains/kotlin/ir/declarations/IrClass;)Z", "isCanonical", "Lorg/jetbrains/kotlin/ir/expressions/IrTry;", "builtIns", "Lorg/jetbrains/kotlin/ir/IrBuiltIns;", "backend.wasm"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class UtilsKt {
    public static final boolean isCanonical(IrTry irTry, IrBuiltIns builtIns) {
        IrVariable catchParameter;
        IrVariableSymbol symbol;
        IrVariable owner;
        Intrinsics.checkNotNullParameter(irTry, "<this>");
        Intrinsics.checkNotNullParameter(builtIns, "builtIns");
        IrCatch irCatch = (IrCatch) CollectionsKt.singleOrNull((List) irTry.getCatches());
        return Intrinsics.areEqual((irCatch == null || (catchParameter = irCatch.getCatchParameter()) == null || (symbol = catchParameter.getSymbol()) == null || (owner = symbol.getOwner()) == null) ? null : owner.getType(), builtIns.getThrowableType()) && irTry.getFinallyExpression() == null;
    }

    public static final boolean isAbstractOrSealed(IrClass irClass) {
        Intrinsics.checkNotNullParameter(irClass, "<this>");
        return irClass.getModality() == Modality.ABSTRACT || irClass.getModality() == Modality.SEALED;
    }
}
