package org.eclipse.jdt.internal.compiler.ast;

import org.eclipse.jdt.internal.compiler.codegen.CodeStream;
import org.eclipse.jdt.internal.compiler.codegen.ExceptionLabel;
import org.eclipse.jdt.internal.compiler.lookup.BlockScope;
import org.eclipse.jdt.internal.compiler.lookup.LocalVariableBinding;

/* JADX INFO: loaded from: classes14.dex */
public abstract class SubRoutineStatement extends Statement {
    ExceptionLabel anyExceptionLabel;

    public void enterDeclaredExceptionHandlers(CodeStream codeStream) {
    }

    public void exitDeclaredExceptionHandlers(CodeStream codeStream) {
    }

    public abstract boolean generateSubRoutineInvocation(BlockScope blockScope, CodeStream codeStream, Object obj, int i, LocalVariableBinding localVariableBinding);

    public abstract boolean isSubRoutineEscaping();

    public static void reenterAllExceptionHandlers(SubRoutineStatement[] subRoutineStatementArr, int i, CodeStream codeStream) {
        if (subRoutineStatementArr == null) {
            return;
        }
        if (i < 0) {
            i = subRoutineStatementArr.length;
        }
        for (int i2 = 0; i2 < i; i2++) {
            SubRoutineStatement subRoutineStatement = subRoutineStatementArr[i2];
            subRoutineStatement.enterAnyExceptionHandler(codeStream);
            subRoutineStatement.enterDeclaredExceptionHandlers(codeStream);
        }
    }

    public ExceptionLabel enterAnyExceptionHandler(CodeStream codeStream) {
        if (this.anyExceptionLabel == null) {
            this.anyExceptionLabel = new ExceptionLabel(codeStream, null);
        }
        this.anyExceptionLabel.placeStart();
        return this.anyExceptionLabel;
    }

    public void exitAnyExceptionHandler() {
        ExceptionLabel exceptionLabel = this.anyExceptionLabel;
        if (exceptionLabel != null) {
            exceptionLabel.placeEnd();
        }
    }

    public void placeAllAnyExceptionHandler() {
        this.anyExceptionLabel.place();
    }
}
