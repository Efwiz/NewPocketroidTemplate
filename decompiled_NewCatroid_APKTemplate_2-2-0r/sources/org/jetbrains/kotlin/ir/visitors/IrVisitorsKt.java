package org.jetbrains.kotlin.ir.visitors;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.ir.IrElement;

/* JADX INFO: compiled from: IrVisitors.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000\u0014\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u001a\u0012\u0010\u0000\u001a\u00020\u0001*\u00020\u00022\u0006\u0010\u0003\u001a\u00020\u0004\u001a\u0012\u0010\u0005\u001a\u00020\u0001*\u00020\u00022\u0006\u0010\u0003\u001a\u00020\u0004¨\u0006\u0006"}, d2 = {"acceptChildrenVoid", "", "Lorg/jetbrains/kotlin/ir/IrElement;", "visitor", "Lorg/jetbrains/kotlin/ir/visitors/IrElementVisitorVoid;", "acceptVoid", "ir.tree"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class IrVisitorsKt {
    public static final void acceptVoid(IrElement irElement, IrElementVisitorVoid visitor) {
        Intrinsics.checkNotNullParameter(irElement, "<this>");
        Intrinsics.checkNotNullParameter(visitor, "visitor");
        irElement.accept(visitor, null);
    }

    public static final void acceptChildrenVoid(IrElement irElement, IrElementVisitorVoid visitor) {
        Intrinsics.checkNotNullParameter(irElement, "<this>");
        Intrinsics.checkNotNullParameter(visitor, "visitor");
        irElement.acceptChildren(visitor, null);
    }
}
