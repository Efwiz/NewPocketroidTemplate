package org.jetbrains.kotlin.backend.jvm.lower;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.ir.declarations.IrVariable;
import org.jetbrains.kotlin.ir.expressions.IrBlock;
import org.jetbrains.kotlin.ir.expressions.IrBranch;

/* JADX INFO: compiled from: JvmSafeCallChainFoldingLowering.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\n\b\u0000\u0018\u00002\u00020\u0001B%\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\u0007¢\u0006\u0002\u0010\tR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000bR\u0011\u0010\b\u001a\u00020\u0007¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR\u0011\u0010\u0006\u001a\u00020\u0007¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\rR\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0010¨\u0006\u0011"}, d2 = {"Lorg/jetbrains/kotlin/backend/jvm/lower/SafeCallInfo;", "", "block", "Lorg/jetbrains/kotlin/ir/expressions/IrBlock;", "tmpVal", "Lorg/jetbrains/kotlin/ir/declarations/IrVariable;", "ifNullBranch", "Lorg/jetbrains/kotlin/ir/expressions/IrBranch;", "ifNotNullBranch", "(Lorg/jetbrains/kotlin/ir/expressions/IrBlock;Lorg/jetbrains/kotlin/ir/declarations/IrVariable;Lorg/jetbrains/kotlin/ir/expressions/IrBranch;Lorg/jetbrains/kotlin/ir/expressions/IrBranch;)V", "getBlock", "()Lorg/jetbrains/kotlin/ir/expressions/IrBlock;", "getIfNotNullBranch", "()Lorg/jetbrains/kotlin/ir/expressions/IrBranch;", "getIfNullBranch", "getTmpVal", "()Lorg/jetbrains/kotlin/ir/declarations/IrVariable;", "backend.jvm.lower"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class SafeCallInfo {
    private final IrBlock block;
    private final IrBranch ifNotNullBranch;
    private final IrBranch ifNullBranch;
    private final IrVariable tmpVal;

    public SafeCallInfo(IrBlock block, IrVariable tmpVal, IrBranch ifNullBranch, IrBranch ifNotNullBranch) {
        Intrinsics.checkNotNullParameter(block, "block");
        Intrinsics.checkNotNullParameter(tmpVal, "tmpVal");
        Intrinsics.checkNotNullParameter(ifNullBranch, "ifNullBranch");
        Intrinsics.checkNotNullParameter(ifNotNullBranch, "ifNotNullBranch");
        this.block = block;
        this.tmpVal = tmpVal;
        this.ifNullBranch = ifNullBranch;
        this.ifNotNullBranch = ifNotNullBranch;
    }

    public final IrBlock getBlock() {
        return this.block;
    }

    public final IrVariable getTmpVal() {
        return this.tmpVal;
    }

    public final IrBranch getIfNullBranch() {
        return this.ifNullBranch;
    }

    public final IrBranch getIfNotNullBranch() {
        return this.ifNotNullBranch;
    }
}
