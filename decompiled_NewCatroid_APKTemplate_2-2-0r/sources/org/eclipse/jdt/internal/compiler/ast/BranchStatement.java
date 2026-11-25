package org.eclipse.jdt.internal.compiler.ast;

import org.eclipse.jdt.internal.compiler.codegen.BranchLabel;
import org.eclipse.jdt.internal.compiler.codegen.CodeStream;
import org.eclipse.jdt.internal.compiler.lookup.BlockScope;

/* JADX INFO: loaded from: classes14.dex */
public abstract class BranchStatement extends Statement {
    public int initStateIndex = -1;
    public char[] label;
    public SubRoutineStatement[] subroutines;
    public BranchLabel targetLabel;

    @Override // org.eclipse.jdt.internal.compiler.ast.Statement
    public void resolve(BlockScope blockScope) {
    }

    public BranchStatement(char[] cArr, int i, int i2) {
        this.label = cArr;
        this.sourceStart = i;
        this.sourceEnd = i2;
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.Statement
    public void generateCode(BlockScope blockScope, CodeStream codeStream) {
        if ((this.bits & Integer.MIN_VALUE) == 0) {
            return;
        }
        int i = codeStream.position;
        SubRoutineStatement[] subRoutineStatementArr = this.subroutines;
        if (subRoutineStatementArr != null) {
            int length = subRoutineStatementArr.length;
            for (int i2 = 0; i2 < length; i2++) {
                if (this.subroutines[i2].generateSubRoutineInvocation(blockScope, codeStream, this.targetLabel, this.initStateIndex, null)) {
                    codeStream.recordPositionsFrom(i, this.sourceStart);
                    SubRoutineStatement.reenterAllExceptionHandlers(this.subroutines, i2, codeStream);
                    int i3 = this.initStateIndex;
                    if (i3 != -1) {
                        codeStream.removeNotDefinitelyAssignedVariables(blockScope, i3);
                        codeStream.addDefinitelyAssignedVariables(blockScope, this.initStateIndex);
                        return;
                    }
                    return;
                }
            }
        }
        codeStream.goto_(this.targetLabel);
        codeStream.recordPositionsFrom(i, this.sourceStart);
        SubRoutineStatement.reenterAllExceptionHandlers(this.subroutines, -1, codeStream);
        int i4 = this.initStateIndex;
        if (i4 != -1) {
            codeStream.removeNotDefinitelyAssignedVariables(blockScope, i4);
            codeStream.addDefinitelyAssignedVariables(blockScope, this.initStateIndex);
        }
    }
}
