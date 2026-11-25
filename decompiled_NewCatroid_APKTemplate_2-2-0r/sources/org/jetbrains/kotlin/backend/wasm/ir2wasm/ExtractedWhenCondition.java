package org.jetbrains.kotlin.backend.wasm.ir2wasm;

import androidx.exifinterface.media.ExifInterface;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.com.intellij.psi.PsiKeyword;
import org.jetbrains.kotlin.ir.expressions.IrCall;
import org.jetbrains.kotlin.ir.expressions.IrConst;

/* JADX INFO: compiled from: OptimisedWhenGenerator.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\b\u0002\u0018\u0000*\u0004\b\u0000\u0010\u00012\u00020\u0002B\u001b\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00028\u00000\u0006¢\u0006\u0002\u0010\u0007R\u0011\u0010\u0003\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u0017\u0010\u0005\u001a\b\u0012\u0004\u0012\u00028\u00000\u0006¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000b¨\u0006\f"}, d2 = {"Lorg/jetbrains/kotlin/backend/wasm/ir2wasm/ExtractedWhenCondition;", ExifInterface.GPS_DIRECTION_TRUE, "", "condition", "Lorg/jetbrains/kotlin/ir/expressions/IrCall;", PsiKeyword.CONST, "Lorg/jetbrains/kotlin/ir/expressions/IrConst;", "(Lorg/jetbrains/kotlin/ir/expressions/IrCall;Lorg/jetbrains/kotlin/ir/expressions/IrConst;)V", "getCondition", "()Lorg/jetbrains/kotlin/ir/expressions/IrCall;", "getConst", "()Lorg/jetbrains/kotlin/ir/expressions/IrConst;", "backend.wasm"}, k = 1, mv = {1, 9, 0}, xi = 48)
final class ExtractedWhenCondition<T> {
    private final IrCall condition;
    private final IrConst<T> const;

    public ExtractedWhenCondition(IrCall condition, IrConst<T> irConst) {
        Intrinsics.checkNotNullParameter(condition, "condition");
        Intrinsics.checkNotNullParameter(irConst, "const");
        this.condition = condition;
        this.const = irConst;
    }

    public final IrCall getCondition() {
        return this.condition;
    }

    public final IrConst<T> getConst() {
        return this.const;
    }
}
