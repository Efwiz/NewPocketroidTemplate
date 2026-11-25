package org.eclipse.jdt.internal.compiler.ast;

import org.eclipse.jdt.internal.compiler.codegen.CodeStream;
import org.eclipse.jdt.internal.compiler.codegen.Opcodes;
import org.eclipse.jdt.internal.compiler.flow.FlowContext;
import org.eclipse.jdt.internal.compiler.flow.FlowInfo;
import org.eclipse.jdt.internal.compiler.lookup.BlockScope;
import org.eclipse.jdt.internal.compiler.lookup.FieldBinding;
import org.eclipse.jdt.internal.compiler.lookup.LocalVariableBinding;
import org.eclipse.jdt.internal.compiler.lookup.MethodBinding;
import org.eclipse.jdt.internal.compiler.lookup.MethodScope;
import org.eclipse.jdt.internal.compiler.lookup.Scope;
import org.eclipse.jdt.internal.compiler.lookup.TypeBinding;

/* JADX INFO: loaded from: classes14.dex */
public abstract class Reference extends Expression {
    public abstract FlowInfo analyseAssignment(BlockScope blockScope, FlowContext flowContext, FlowInfo flowInfo, Assignment assignment, boolean z);

    @Override // org.eclipse.jdt.internal.compiler.ast.Expression, org.eclipse.jdt.internal.compiler.ast.Statement
    public FlowInfo analyseCode(BlockScope blockScope, FlowContext flowContext, FlowInfo flowInfo) {
        return flowInfo;
    }

    public FieldBinding fieldBinding() {
        return null;
    }

    public abstract void generateAssignment(BlockScope blockScope, CodeStream codeStream, Assignment assignment, boolean z);

    public abstract void generateCompoundAssignment(BlockScope blockScope, CodeStream codeStream, Expression expression, int i, int i2, boolean z);

    public abstract void generatePostIncrement(BlockScope blockScope, CodeStream codeStream, CompoundAssignment compoundAssignment, boolean z);

    public boolean isEquivalent(Reference reference) {
        return false;
    }

    public FieldBinding lastFieldBinding() {
        return null;
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.Expression
    public boolean checkNPE(BlockScope blockScope, FlowContext flowContext, FlowInfo flowInfo, int i) {
        if (flowContext.isNullcheckedFieldAccess(this)) {
            return true;
        }
        return super.checkNPE(blockScope, flowContext, flowInfo, i);
    }

    protected boolean checkNullableFieldDereference(Scope scope, FieldBinding fieldBinding, long j, FlowContext flowContext, int i) {
        if (fieldBinding == null) {
            return false;
        }
        if (i > 0 && scope.compilerOptions().enableSyntacticNullAnalysisForFields) {
            flowContext.recordNullCheckedFieldReference(this, i);
        }
        if ((fieldBinding.type.tagBits & 36028797018963968L) != 0) {
            scope.problemReporter().dereferencingNullableExpression(j, scope.environment());
            return true;
        }
        if (fieldBinding.type.isFreeTypeVariable()) {
            scope.problemReporter().fieldFreeTypeVariableReference(fieldBinding, j);
            return true;
        }
        if ((36028797018963968L & fieldBinding.tagBits) == 0) {
            return false;
        }
        scope.problemReporter().nullableFieldDereference(fieldBinding, j);
        return true;
    }

    public void fieldStore(Scope scope, CodeStream codeStream, FieldBinding fieldBinding, MethodBinding methodBinding, TypeBinding typeBinding, boolean z, boolean z2) {
        int i = codeStream.position;
        if (fieldBinding.isStatic()) {
            if (z2) {
                int i2 = fieldBinding.type.f626id;
                if (i2 == 7 || i2 == 8) {
                    codeStream.dup2();
                } else {
                    codeStream.dup();
                }
            }
            if (methodBinding == null) {
                codeStream.fieldAccess(Opcodes.OPC_putstatic, fieldBinding, CodeStream.getConstantPoolDeclaringClass(scope, fieldBinding, typeBinding, z));
            } else {
                codeStream.invoke(Opcodes.OPC_invokestatic, methodBinding, null);
            }
        } else {
            if (z2) {
                int i3 = fieldBinding.type.f626id;
                if (i3 == 7 || i3 == 8) {
                    codeStream.dup2_x1();
                } else {
                    codeStream.dup_x1();
                }
            }
            if (methodBinding == null) {
                codeStream.fieldAccess(Opcodes.OPC_putfield, fieldBinding, CodeStream.getConstantPoolDeclaringClass(scope, fieldBinding, typeBinding, z));
            } else {
                codeStream.invoke(Opcodes.OPC_invokestatic, methodBinding, null);
            }
        }
        codeStream.recordPositionsFrom(i, this.sourceStart);
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.Expression
    public int nullStatus(FlowInfo flowInfo, FlowContext flowContext) {
        if ((this.implicitConversion & 512) != 0) {
            return 4;
        }
        FieldBinding fieldBindingLastFieldBinding = lastFieldBinding();
        if (fieldBindingLastFieldBinding != null) {
            if (fieldBindingLastFieldBinding.isNonNull() || flowContext.isNullcheckedFieldAccess(this)) {
                return 4;
            }
            if (fieldBindingLastFieldBinding.isNullable()) {
                return 16;
            }
            if (fieldBindingLastFieldBinding.type.isFreeTypeVariable()) {
                return 48;
            }
        }
        if (this.resolvedType != null) {
            return FlowInfo.tagBitsToNullStatus(this.resolvedType.tagBits);
        }
        return 1;
    }

    void reportOnlyUselesslyReadPrivateField(BlockScope blockScope, FieldBinding fieldBinding, boolean z) {
        if (z) {
            fieldBinding.compoundUseFlag = 0;
            fieldBinding.modifiers |= 134217728;
        } else if (fieldBinding.isUsedOnlyInCompound()) {
            fieldBinding.compoundUseFlag--;
            if (fieldBinding.compoundUseFlag == 0 && fieldBinding.isOrEnclosedByPrivateType() && (this.implicitConversion & 1024) == 0) {
                blockScope.problemReporter().unusedPrivateField(fieldBinding.sourceField());
            }
        }
    }

    /* JADX WARN: Code duplicated, block: B:31:0x0060  */
    /* JADX WARN: Code duplicated, block: B:36:? A[RETURN, SYNTHETIC] */
    static void reportOnlyUselesslyReadLocal(BlockScope blockScope, LocalVariableBinding localVariableBinding, boolean z) {
        boolean z2;
        if (localVariableBinding.declaration == null || (localVariableBinding.declaration.bits & 1073741824) == 0 || localVariableBinding.useFlag >= 1) {
            return;
        }
        if (z) {
            localVariableBinding.useFlag = 1;
            return;
        }
        localVariableBinding.useFlag++;
        if (localVariableBinding.useFlag != 0) {
            return;
        }
        if (localVariableBinding.declaration instanceof Argument) {
            MethodScope methodScope = blockScope.methodScope();
            if (methodScope == null || methodScope.isLambdaScope()) {
                return;
            }
            MethodBinding methodBinding = ((AbstractMethodDeclaration) methodScope.referenceContext()).binding;
            boolean z3 = !methodBinding.isMain();
            if (methodBinding.isImplementing()) {
                z2 = blockScope.compilerOptions().reportUnusedParameterWhenImplementingAbstract;
            } else {
                if (methodBinding.isOverriding()) {
                    z2 = blockScope.compilerOptions().reportUnusedParameterWhenOverridingConcrete;
                }
                if (z3) {
                    blockScope.problemReporter().unusedArgument(localVariableBinding.declaration);
                    return;
                }
                return;
            }
            z3 &= z2;
            if (z3) {
                blockScope.problemReporter().unusedArgument(localVariableBinding.declaration);
                return;
            }
            return;
        }
        blockScope.problemReporter().unusedLocalVariable(localVariableBinding.declaration);
    }
}
