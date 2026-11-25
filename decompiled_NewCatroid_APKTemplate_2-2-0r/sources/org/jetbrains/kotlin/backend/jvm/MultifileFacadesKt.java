package org.jetbrains.kotlin.backend.jvm;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.ir.declarations.IrClass;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationOrigin;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationParent;
import org.jetbrains.kotlin.ir.declarations.IrFunction;

/* JADX INFO: compiled from: MultifileFacades.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000\f\n\u0000\n\u0002\u0010\u000b\n\u0002\u0018\u0002\n\u0000\u001a\n\u0010\u0000\u001a\u00020\u0001*\u00020\u0002¨\u0006\u0003"}, d2 = {"isMultifileBridge", "", "Lorg/jetbrains/kotlin/ir/declarations/IrFunction;", "backend.jvm"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class MultifileFacadesKt {
    public static final boolean isMultifileBridge(IrFunction irFunction) {
        Intrinsics.checkNotNullParameter(irFunction, "<this>");
        IrDeclarationParent parent = irFunction.getParent();
        IrClass irClass = parent instanceof IrClass ? (IrClass) parent : null;
        return Intrinsics.areEqual(irClass != null ? irClass.getOrigin() : null, IrDeclarationOrigin.JVM_MULTIFILE_CLASS.INSTANCE);
    }
}
