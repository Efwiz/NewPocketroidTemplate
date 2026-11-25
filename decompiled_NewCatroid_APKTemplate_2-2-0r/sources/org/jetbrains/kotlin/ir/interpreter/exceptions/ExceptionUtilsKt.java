package org.jetbrains.kotlin.ir.interpreter.exceptions;

import java.util.Iterator;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.ir.declarations.IrClass;
import org.jetbrains.kotlin.ir.interpreter.IrInterpreterEnvironment;
import org.jetbrains.kotlin.ir.interpreter.state.ExceptionState;

/* JADX INFO: compiled from: ExceptionUtils.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000.\n\u0000\n\u0002\u0010\u0001\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0003\n\u0000\u001a\u0016\u0010\u0000\u001a\u00020\u00012\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003H\u0000\u001a\u0010\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bH\u0000\u001a\"\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003H\u0080\bø\u0001\u0000\u001a\"\u0010\t\u001a\u00020\u00062\u0006\u0010\n\u001a\u00020\u000b2\f\u0010\f\u001a\b\u0012\u0004\u0012\u00020\u00060\u0003H\u0080\bø\u0001\u0000\u001a\u0014\u0010\r\u001a\u00020\u0006*\u00020\u000e2\u0006\u0010\n\u001a\u00020\u000bH\u0000\u0082\u0002\u0007\n\u0005\b\u009920\u0001¨\u0006\u000f"}, d2 = {"stop", "", "lazyMessage", "Lkotlin/Function0;", "", "verify", "", "value", "", "withExceptionHandler", "environment", "Lorg/jetbrains/kotlin/ir/interpreter/IrInterpreterEnvironment;", "block", "handleUserException", "", "ir.interpreter"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class ExceptionUtilsKt {
    public static final Void stop(Function0<? extends Object> lazyMessage) {
        Intrinsics.checkNotNullParameter(lazyMessage, "lazyMessage");
        throw new InterpreterAssertionError(lazyMessage.invoke().toString());
    }

    public static final void verify(boolean z) throws InterpreterAssertionError {
        if (!z) {
            throw new InterpreterAssertionError("Assertion failed".toString());
        }
    }

    public static final void verify(boolean z, Function0<? extends Object> lazyMessage) throws InterpreterAssertionError {
        Intrinsics.checkNotNullParameter(lazyMessage, "lazyMessage");
        if (!z) {
            throw new InterpreterAssertionError(lazyMessage.invoke().toString());
        }
    }

    public static final void withExceptionHandler(IrInterpreterEnvironment environment, Function0<Unit> block) throws InterpreterException {
        Intrinsics.checkNotNullParameter(environment, "environment");
        Intrinsics.checkNotNullParameter(block, "block");
        try {
            block.invoke();
        } catch (InterpreterException e) {
            throw e;
        } catch (Throwable th) {
            handleUserException(th, environment);
        }
    }

    public static final void handleUserException(Throwable th, IrInterpreterEnvironment environment) {
        Object next;
        Intrinsics.checkNotNullParameter(th, "<this>");
        Intrinsics.checkNotNullParameter(environment, "environment");
        String simpleName = th.getClass().getSimpleName();
        Iterator<T> it = environment.getIrExceptions$ir_interpreter().iterator();
        do {
            if (!it.hasNext()) {
                next = null;
                break;
            }
            next = it.next();
        } while (!Intrinsics.areEqual(((IrClass) next).getName().asString(), simpleName));
        IrClass owner = (IrClass) next;
        if (owner == null) {
            owner = environment.getIrBuiltIns().getThrowableClass().getOwner();
        }
        environment.getCallStack().pushState(new ExceptionState(th, owner, environment.getCallStack().getStackTrace(), environment));
        environment.getCallStack().dropFramesUntilTryCatch();
    }
}
