package org.jetbrains.kotlin.ir.util;

import kotlin.Metadata;
import org.jetbrains.kotlin.ir.expressions.IrExpression;
import org.jetbrains.kotlin.ir.types.IrType;

/* JADX INFO: compiled from: emptyArraysForMemberAccess.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000\u001e\n\u0000\n\u0002\u0010\u0011\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0004\u001a\u001b\u0010\u0007\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00020\u00012\u0006\u0010\b\u001a\u00020\t¢\u0006\u0002\u0010\n\u001a\u001b\u0010\u000b\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00050\u00012\u0006\u0010\b\u001a\u00020\t¢\u0006\u0002\u0010\f\"\u0018\u0010\u0000\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00020\u0001X\u0082\u0004¢\u0006\u0004\n\u0002\u0010\u0003\"\u0018\u0010\u0004\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00050\u0001X\u0082\u0004¢\u0006\u0004\n\u0002\u0010\u0006¨\u0006\r"}, d2 = {"EMPTY_EXPRESSION_ARRAY", "", "Lorg/jetbrains/kotlin/ir/expressions/IrExpression;", "[Lorg/jetbrains/kotlin/ir/expressions/IrExpression;", "EMPTY_TYPE_ARRAY", "Lorg/jetbrains/kotlin/ir/types/IrType;", "[Lorg/jetbrains/kotlin/ir/types/IrType;", "initializeParameterArguments", "size", "", "(I)[Lorg/jetbrains/kotlin/ir/expressions/IrExpression;", "initializeTypeArguments", "(I)[Lorg/jetbrains/kotlin/ir/types/IrType;", "ir.tree"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class EmptyArraysForMemberAccessKt {
    private static final IrType[] EMPTY_TYPE_ARRAY = new IrType[0];
    private static final IrExpression[] EMPTY_EXPRESSION_ARRAY = new IrExpression[0];

    public static final IrType[] initializeTypeArguments(int i) {
        return i == 0 ? EMPTY_TYPE_ARRAY : new IrType[i];
    }

    public static final IrExpression[] initializeParameterArguments(int i) {
        return i == 0 ? EMPTY_EXPRESSION_ARRAY : new IrExpression[i];
    }
}
