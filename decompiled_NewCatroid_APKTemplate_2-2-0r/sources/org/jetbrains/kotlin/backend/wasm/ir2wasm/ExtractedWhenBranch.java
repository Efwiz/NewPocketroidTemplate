package org.jetbrains.kotlin.backend.wasm.ir2wasm;

import androidx.exifinterface.media.ExifInterface;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.ir.expressions.IrExpression;

/* JADX INFO: compiled from: OptimisedWhenGenerator.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\b\u0002\u0018\u0000*\u0004\b\u0000\u0010\u00012\u00020\u0002B!\u0012\u0012\u0010\u0003\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00028\u00000\u00050\u0004\u0012\u0006\u0010\u0006\u001a\u00020\u0007¢\u0006\u0002\u0010\bR\u001d\u0010\u0003\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00028\u00000\u00050\u0004¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u0011\u0010\u0006\u001a\u00020\u0007¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\f¨\u0006\r"}, d2 = {"Lorg/jetbrains/kotlin/backend/wasm/ir2wasm/ExtractedWhenBranch;", ExifInterface.GPS_DIRECTION_TRUE, "", "conditions", "", "Lorg/jetbrains/kotlin/backend/wasm/ir2wasm/ExtractedWhenCondition;", "expression", "Lorg/jetbrains/kotlin/ir/expressions/IrExpression;", "(Ljava/util/List;Lorg/jetbrains/kotlin/ir/expressions/IrExpression;)V", "getConditions", "()Ljava/util/List;", "getExpression", "()Lorg/jetbrains/kotlin/ir/expressions/IrExpression;", "backend.wasm"}, k = 1, mv = {1, 9, 0}, xi = 48)
final class ExtractedWhenBranch<T> {
    private final List<ExtractedWhenCondition<T>> conditions;
    private final IrExpression expression;

    public ExtractedWhenBranch(List<ExtractedWhenCondition<T>> conditions, IrExpression expression) {
        Intrinsics.checkNotNullParameter(conditions, "conditions");
        Intrinsics.checkNotNullParameter(expression, "expression");
        this.conditions = conditions;
        this.expression = expression;
    }

    public final List<ExtractedWhenCondition<T>> getConditions() {
        return this.conditions;
    }

    public final IrExpression getExpression() {
        return this.expression;
    }
}
