package org.jetbrains.kotlin.ir.interpreter.exceptions;

import kotlin.Metadata;

/* JADX INFO: compiled from: InterpreterError.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002¨\u0006\u0003"}, d2 = {"Lorg/jetbrains/kotlin/ir/interpreter/exceptions/InterpreterEmptyReturnStackError;", "Lorg/jetbrains/kotlin/ir/interpreter/exceptions/InterpreterError;", "()V", "ir.interpreter"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class InterpreterEmptyReturnStackError extends InterpreterError {
    public InterpreterEmptyReturnStackError() {
        super("Return values stack is empty");
    }
}
