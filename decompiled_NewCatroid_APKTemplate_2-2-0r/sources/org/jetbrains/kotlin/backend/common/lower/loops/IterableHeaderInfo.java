package org.jetbrains.kotlin.backend.common.lower.loops;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.ir.declarations.IrVariable;

/* JADX INFO: compiled from: HeaderInfo.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\b\u0000\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\n\u0010\u0007\u001a\u0004\u0018\u00010\u0001H\u0016R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\b"}, d2 = {"Lorg/jetbrains/kotlin/backend/common/lower/loops/IterableHeaderInfo;", "Lorg/jetbrains/kotlin/backend/common/lower/loops/HeaderInfo;", "iteratorVariable", "Lorg/jetbrains/kotlin/ir/declarations/IrVariable;", "(Lorg/jetbrains/kotlin/ir/declarations/IrVariable;)V", "getIteratorVariable", "()Lorg/jetbrains/kotlin/ir/declarations/IrVariable;", "asReversed", "ir.backend.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class IterableHeaderInfo extends HeaderInfo {
    private final IrVariable iteratorVariable;

    @Override // org.jetbrains.kotlin.backend.common.lower.loops.HeaderInfo
    public HeaderInfo asReversed() {
        return null;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public IterableHeaderInfo(IrVariable iteratorVariable) {
        super(null);
        Intrinsics.checkNotNullParameter(iteratorVariable, "iteratorVariable");
        this.iteratorVariable = iteratorVariable;
    }

    public final IrVariable getIteratorVariable() {
        return this.iteratorVariable;
    }
}
