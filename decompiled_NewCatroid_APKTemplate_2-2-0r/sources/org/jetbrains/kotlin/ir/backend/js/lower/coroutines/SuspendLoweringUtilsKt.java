package org.jetbrains.kotlin.ir.backend.js.lower.coroutines;

import java.util.LinkedHashSet;
import java.util.Set;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.ir.IrElement;
import org.jetbrains.kotlin.ir.expressions.IrBlock;
import org.jetbrains.kotlin.ir.visitors.IrVisitorsKt;

/* JADX INFO: compiled from: SuspendLoweringUtils.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000\u0012\n\u0000\n\u0002\u0010#\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u001a\u0014\u0010\u0000\u001a\b\u0012\u0004\u0012\u00020\u00020\u00012\u0006\u0010\u0003\u001a\u00020\u0004¨\u0006\u0005"}, d2 = {"collectSuspendableNodes", "", "Lorg/jetbrains/kotlin/ir/IrElement;", "function", "Lorg/jetbrains/kotlin/ir/expressions/IrBlock;", "backend.js"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class SuspendLoweringUtilsKt {
    public static final Set<IrElement> collectSuspendableNodes(IrBlock function) {
        int size;
        Intrinsics.checkNotNullParameter(function, "function");
        LinkedHashSet linkedHashSet = new LinkedHashSet();
        do {
            size = linkedHashSet.size();
            IrVisitorsKt.acceptVoid(function, new SuspendableNodesCollector(linkedHashSet));
        } while (size != linkedHashSet.size());
        return linkedHashSet;
    }
}
