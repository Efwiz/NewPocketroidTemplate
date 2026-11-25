package org.eclipse.jdt.internal.compiler.ast;

import org.eclipse.jdt.core.compiler.CharOperation;
import org.eclipse.jdt.internal.compiler.ASTVisitor;
import org.eclipse.jdt.internal.compiler.classfmt.ClassFileConstants;
import org.eclipse.jdt.internal.compiler.codegen.CodeStream;
import org.eclipse.jdt.internal.compiler.codegen.Opcodes;
import org.eclipse.jdt.internal.compiler.flow.FlowContext;
import org.eclipse.jdt.internal.compiler.flow.FlowInfo;
import org.eclipse.jdt.internal.compiler.flow.UnconditionalFlowInfo;
import org.eclipse.jdt.internal.compiler.impl.Constant;
import org.eclipse.jdt.internal.compiler.lookup.BlockScope;
import org.eclipse.jdt.internal.compiler.lookup.FieldBinding;
import org.eclipse.jdt.internal.compiler.lookup.InferenceContext18;
import org.eclipse.jdt.internal.compiler.lookup.InvocationSite;
import org.eclipse.jdt.internal.compiler.lookup.LocalVariableBinding;
import org.eclipse.jdt.internal.compiler.lookup.MethodBinding;
import org.eclipse.jdt.internal.compiler.lookup.MethodScope;
import org.eclipse.jdt.internal.compiler.lookup.MissingTypeBinding;
import org.eclipse.jdt.internal.compiler.lookup.PackageBinding;
import org.eclipse.jdt.internal.compiler.lookup.ProblemFieldBinding;
import org.eclipse.jdt.internal.compiler.lookup.ProblemReferenceBinding;
import org.eclipse.jdt.internal.compiler.lookup.ReferenceBinding;
import org.eclipse.jdt.internal.compiler.lookup.Scope;
import org.eclipse.jdt.internal.compiler.lookup.SourceTypeBinding;
import org.eclipse.jdt.internal.compiler.lookup.TagBits;
import org.eclipse.jdt.internal.compiler.lookup.TypeBinding;
import org.eclipse.jdt.internal.compiler.lookup.VariableBinding;

/* JADX INFO: loaded from: classes14.dex */
public class FieldReference extends Reference implements InvocationSite {
    public static final int READ = 0;
    public static final int WRITE = 1;
    public TypeBinding actualReceiverType;
    public FieldBinding binding;
    public TypeBinding genericCast;
    public long nameSourcePosition;
    public Expression receiver;
    public MethodBinding[] syntheticAccessors;
    public char[] token;

    @Override // org.eclipse.jdt.internal.compiler.lookup.InvocationSite
    public InferenceContext18 freshInferenceContext(Scope scope) {
        return null;
    }

    @Override // org.eclipse.jdt.internal.compiler.lookup.InvocationSite
    public TypeBinding[] genericTypeArguments() {
        return null;
    }

    @Override // org.eclipse.jdt.internal.compiler.lookup.InvocationSite
    public void setFieldIndex(int i) {
    }

    public FieldReference(char[] cArr, long j) {
        this.token = cArr;
        this.nameSourcePosition = j;
        this.sourceStart = (int) (j >>> 32);
        this.sourceEnd = (int) (j & 4294967295L);
        this.bits |= 1;
    }

    /* JADX WARN: Code duplicated, block: B:35:0x00aa  */
    @Override // org.eclipse.jdt.internal.compiler.ast.Reference
    public FlowInfo analyseAssignment(BlockScope blockScope, FlowContext flowContext, FlowInfo flowInfo, Assignment assignment, boolean z) {
        if (z) {
            if (this.binding.isBlankFinal() && this.receiver.isThis() && blockScope.needBlankFinalFieldInitializationCheck(this.binding) && !flowContext.getInitsForFinalBlankInitializationCheck(this.binding.declaringClass.original(), flowInfo).isDefinitelyAssigned(this.binding)) {
                blockScope.problemReporter().uninitializedBlankFinalField(this.binding, this);
            }
            manageSyntheticAccessIfNecessary(blockScope, flowInfo, true);
        }
        UnconditionalFlowInfo unconditionalFlowInfoUnconditionalInits = this.receiver.analyseCode(blockScope, flowContext, flowInfo, true ^ this.binding.isStatic()).unconditionalInits();
        if (assignment.expression != null) {
            unconditionalFlowInfoUnconditionalInits = assignment.expression.analyseCode(blockScope, flowContext, unconditionalFlowInfoUnconditionalInits).unconditionalInits();
        }
        manageSyntheticAccessIfNecessary(blockScope, unconditionalFlowInfoUnconditionalInits, false);
        if (this.binding.isFinal()) {
            if (this.binding.isBlankFinal() && !z && this.receiver.isThis()) {
                Expression expression = this.receiver;
                if (!(expression instanceof QualifiedThisReference) && (expression.bits & ASTNode.ParenthesizedMASK) == 0 && blockScope.allowBlankFinalFieldAssignment(this.binding)) {
                    if (unconditionalFlowInfoUnconditionalInits.isPotentiallyAssigned(this.binding)) {
                        blockScope.problemReporter().duplicateInitializationOfBlankFinalField(this.binding, this);
                    } else {
                        flowContext.recordSettingFinal(this.binding, this, unconditionalFlowInfoUnconditionalInits);
                    }
                    unconditionalFlowInfoUnconditionalInits.markAsDefinitelyAssigned(this.binding);
                } else {
                    blockScope.problemReporter().cannotAssignToFinalField(this.binding, this);
                }
            } else {
                blockScope.problemReporter().cannotAssignToFinalField(this.binding, this);
            }
        } else if ((this.binding.isNonNull() || this.binding.type.isTypeVariable()) && !z && this.receiver.isThis()) {
            Expression expression2 = this.receiver;
            if (!(expression2 instanceof QualifiedThisReference) && TypeBinding.equalsEquals(expression2.resolvedType, this.binding.declaringClass) && (this.receiver.bits & ASTNode.ParenthesizedMASK) == 0) {
                unconditionalFlowInfoUnconditionalInits.markAsDefinitelyAssigned(this.binding);
            }
        }
        return unconditionalFlowInfoUnconditionalInits;
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.Reference, org.eclipse.jdt.internal.compiler.ast.Expression, org.eclipse.jdt.internal.compiler.ast.Statement
    public FlowInfo analyseCode(BlockScope blockScope, FlowContext flowContext, FlowInfo flowInfo) {
        return analyseCode(blockScope, flowContext, flowInfo, true);
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.Expression
    public FlowInfo analyseCode(BlockScope blockScope, FlowContext flowContext, FlowInfo flowInfo, boolean z) {
        boolean zIsStatic = this.binding.isStatic();
        this.receiver.analyseCode(blockScope, flowContext, flowInfo, !zIsStatic);
        if (!zIsStatic) {
            this.receiver.checkNPE(blockScope, flowContext, flowInfo, 1);
        }
        if (z || blockScope.compilerOptions().complianceLevel >= ClassFileConstants.JDK1_4) {
            manageSyntheticAccessIfNecessary(blockScope, flowInfo, true);
        }
        if (blockScope.compilerOptions().complianceLevel >= ClassFileConstants.JDK1_7) {
            FieldBinding fieldBinding = this.binding;
            if (this.receiver.isThis() && fieldBinding.isBlankFinal() && blockScope.needBlankFinalFieldInitializationCheck(fieldBinding) && !flowContext.getInitsForFinalBlankInitializationCheck(fieldBinding.declaringClass.original(), flowInfo).isDefinitelyAssigned(fieldBinding)) {
                blockScope.problemReporter().uninitializedBlankFinalField(fieldBinding, this);
            }
        }
        return flowInfo;
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.Reference, org.eclipse.jdt.internal.compiler.ast.Expression
    public boolean checkNPE(BlockScope blockScope, FlowContext flowContext, FlowInfo flowInfo, int i) {
        if (flowContext.isNullcheckedFieldAccess(this)) {
            return true;
        }
        return checkNullableFieldDereference(blockScope, this.binding, this.nameSourcePosition, flowContext, i);
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.Expression
    public void computeConversion(Scope scope, TypeBinding typeBinding, TypeBinding typeBinding2) {
        if (typeBinding == null || typeBinding2 == null) {
            return;
        }
        FieldBinding fieldBinding = this.binding;
        if (fieldBinding != null && fieldBinding.isValidBinding()) {
            FieldBinding fieldBindingOriginal = this.binding.original();
            if (fieldBindingOriginal.type.leafComponentType().isTypeVariable()) {
                TypeBinding typeBindingGenericCast = fieldBindingOriginal.type.genericCast((typeBinding2.isBaseType() || !typeBinding.isBaseType()) ? typeBinding : typeBinding2);
                this.genericCast = typeBindingGenericCast;
                if (typeBindingGenericCast instanceof ReferenceBinding) {
                    ReferenceBinding referenceBinding = (ReferenceBinding) typeBindingGenericCast;
                    if (!referenceBinding.canBeSeenBy(scope)) {
                        scope.problemReporter().invalidType(this, new ProblemReferenceBinding(CharOperation.splitOn('.', referenceBinding.shortReadableName()), referenceBinding, 2));
                    }
                }
            }
        }
        super.computeConversion(scope, typeBinding, typeBinding2);
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.Reference
    public FieldBinding fieldBinding() {
        return this.binding;
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.Reference
    public void generateAssignment(BlockScope blockScope, CodeStream codeStream, Assignment assignment, boolean z) {
        int i = codeStream.position;
        FieldBinding fieldBindingOriginal = this.binding.original();
        this.receiver.generateCode(blockScope, codeStream, !fieldBindingOriginal.isStatic());
        codeStream.recordPositionsFrom(i, this.sourceStart);
        assignment.expression.generateCode(blockScope, codeStream, true);
        MethodBinding[] methodBindingArr = this.syntheticAccessors;
        fieldStore(blockScope, codeStream, fieldBindingOriginal, methodBindingArr == null ? null : methodBindingArr[1], this.actualReceiverType, this.receiver.isImplicitThis(), z);
        if (z) {
            codeStream.generateImplicitConversion(assignment.implicitConversion);
        }
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.Expression
    public void generateCode(BlockScope blockScope, CodeStream codeStream, boolean z) {
        MethodBinding methodBinding;
        int i = codeStream.position;
        if (this.constant != Constant.NotAConstant) {
            if (z) {
                codeStream.generateConstant(this.constant, this.implicitConversion);
            }
            codeStream.recordPositionsFrom(i, this.sourceStart);
            return;
        }
        FieldBinding fieldBindingOriginal = this.binding.original();
        boolean zIsStatic = fieldBindingOriginal.isStatic();
        boolean z2 = this.receiver instanceof ThisReference;
        Constant constant = fieldBindingOriginal.constant();
        if (constant != Constant.NotAConstant) {
            if (!z2) {
                this.receiver.generateCode(blockScope, codeStream, !zIsStatic);
                if (!zIsStatic) {
                    codeStream.invokeObjectGetClass();
                    codeStream.pop();
                }
            }
            if (z) {
                codeStream.generateConstant(constant, this.implicitConversion);
            }
            codeStream.recordPositionsFrom(i, this.sourceStart);
            return;
        }
        if (z || !((z2 || blockScope.compilerOptions().complianceLevel < ClassFileConstants.JDK1_4) && (this.implicitConversion & 1024) == 0 && this.genericCast == null)) {
            this.receiver.generateCode(blockScope, codeStream, !zIsStatic);
            if ((this.bits & 262144) != 0) {
                codeStream.checkcast(this.actualReceiverType);
            }
            i = codeStream.position;
            if (fieldBindingOriginal.declaringClass == null) {
                codeStream.arraylength();
                if (z) {
                    codeStream.generateImplicitConversion(this.implicitConversion);
                } else {
                    codeStream.pop();
                }
            } else {
                MethodBinding[] methodBindingArr = this.syntheticAccessors;
                if (methodBindingArr == null || (methodBinding = methodBindingArr[0]) == null) {
                    TypeBinding constantPoolDeclaringClass = CodeStream.getConstantPoolDeclaringClass(blockScope, fieldBindingOriginal, this.actualReceiverType, this.receiver.isImplicitThis());
                    if (zIsStatic) {
                        codeStream.fieldAccess(Opcodes.OPC_getstatic, fieldBindingOriginal, constantPoolDeclaringClass);
                    } else {
                        codeStream.fieldAccess(Opcodes.OPC_getfield, fieldBindingOriginal, constantPoolDeclaringClass);
                    }
                } else {
                    codeStream.invoke(Opcodes.OPC_invokestatic, methodBinding, null);
                }
                TypeBinding typeBinding = this.genericCast;
                if (typeBinding != null) {
                    codeStream.checkcast(typeBinding);
                }
                if (z) {
                    codeStream.generateImplicitConversion(this.implicitConversion);
                } else {
                    boolean z3 = (this.implicitConversion & 1024) != 0;
                    if (z3) {
                        codeStream.generateImplicitConversion(this.implicitConversion);
                    }
                    int i2 = (z3 ? postConversionType(blockScope) : fieldBindingOriginal.type).f626id;
                    if (i2 == 7 || i2 == 8) {
                        codeStream.pop2();
                    } else {
                        codeStream.pop();
                    }
                }
            }
        } else if (z2) {
            if (zIsStatic && TypeBinding.notEquals(this.binding.original().declaringClass, this.actualReceiverType.erasure())) {
                MethodBinding[] methodBindingArr2 = this.syntheticAccessors;
                MethodBinding methodBinding2 = methodBindingArr2 == null ? null : methodBindingArr2[0];
                if (methodBinding2 == null) {
                    codeStream.fieldAccess(Opcodes.OPC_getstatic, fieldBindingOriginal, CodeStream.getConstantPoolDeclaringClass(blockScope, fieldBindingOriginal, this.actualReceiverType, this.receiver.isImplicitThis()));
                } else {
                    codeStream.invoke(Opcodes.OPC_invokestatic, methodBinding2, null);
                }
                int i3 = fieldBindingOriginal.type.f626id;
                if (i3 == 7 || i3 == 8) {
                    codeStream.pop2();
                } else {
                    codeStream.pop();
                }
            }
        } else {
            this.receiver.generateCode(blockScope, codeStream, !zIsStatic);
            if (!zIsStatic) {
                codeStream.invokeObjectGetClass();
                codeStream.pop();
            }
        }
        codeStream.recordPositionsFrom(i, this.sourceEnd);
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.Reference
    public void generateCompoundAssignment(BlockScope blockScope, CodeStream codeStream, Expression expression, int i, int i2, boolean z) {
        MethodBinding methodBinding;
        MethodBinding methodBinding2;
        reportOnlyUselesslyReadPrivateField(blockScope, this.binding, z);
        FieldBinding fieldBindingOriginal = this.binding.original();
        Expression expression2 = this.receiver;
        boolean zIsStatic = fieldBindingOriginal.isStatic();
        expression2.generateCode(blockScope, codeStream, !zIsStatic);
        if (zIsStatic) {
            MethodBinding[] methodBindingArr = this.syntheticAccessors;
            if (methodBindingArr == null || (methodBinding2 = methodBindingArr[0]) == null) {
                codeStream.fieldAccess(Opcodes.OPC_getstatic, fieldBindingOriginal, CodeStream.getConstantPoolDeclaringClass(blockScope, fieldBindingOriginal, this.actualReceiverType, this.receiver.isImplicitThis()));
            } else {
                codeStream.invoke(Opcodes.OPC_invokestatic, methodBinding2, null);
            }
        } else {
            codeStream.dup();
            MethodBinding[] methodBindingArr2 = this.syntheticAccessors;
            if (methodBindingArr2 == null || (methodBinding = methodBindingArr2[0]) == null) {
                codeStream.fieldAccess(Opcodes.OPC_getfield, fieldBindingOriginal, CodeStream.getConstantPoolDeclaringClass(blockScope, fieldBindingOriginal, this.actualReceiverType, this.receiver.isImplicitThis()));
            } else {
                codeStream.invoke(Opcodes.OPC_invokestatic, methodBinding, null);
            }
        }
        int i3 = (this.implicitConversion & 255) >> 4;
        if (i3 == 0 || i3 == 1 || i3 == 11) {
            codeStream.generateStringConcatenationAppend(blockScope, null, expression);
        } else {
            TypeBinding typeBinding = this.genericCast;
            if (typeBinding != null) {
                codeStream.checkcast(typeBinding);
            }
            codeStream.generateImplicitConversion(this.implicitConversion);
            if (expression == IntLiteral.One) {
                codeStream.generateConstant(expression.constant, this.implicitConversion);
            } else {
                expression.generateCode(blockScope, codeStream, true);
            }
            codeStream.sendOperator(i, i3);
            codeStream.generateImplicitConversion(i2);
        }
        MethodBinding[] methodBindingArr3 = this.syntheticAccessors;
        fieldStore(blockScope, codeStream, fieldBindingOriginal, methodBindingArr3 == null ? null : methodBindingArr3[1], this.actualReceiverType, this.receiver.isImplicitThis(), z);
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.Reference
    public void generatePostIncrement(BlockScope blockScope, CodeStream codeStream, CompoundAssignment compoundAssignment, boolean z) {
        MethodBinding methodBinding;
        TypeBinding typeBinding;
        MethodBinding methodBinding2;
        reportOnlyUselesslyReadPrivateField(blockScope, this.binding, z);
        FieldBinding fieldBindingOriginal = this.binding.original();
        Expression expression = this.receiver;
        boolean zIsStatic = fieldBindingOriginal.isStatic();
        expression.generateCode(blockScope, codeStream, !zIsStatic);
        if (zIsStatic) {
            MethodBinding[] methodBindingArr = this.syntheticAccessors;
            if (methodBindingArr == null || (methodBinding2 = methodBindingArr[0]) == null) {
                codeStream.fieldAccess(Opcodes.OPC_getstatic, fieldBindingOriginal, CodeStream.getConstantPoolDeclaringClass(blockScope, fieldBindingOriginal, this.actualReceiverType, this.receiver.isImplicitThis()));
            } else {
                codeStream.invoke(Opcodes.OPC_invokestatic, methodBinding2, null);
            }
        } else {
            codeStream.dup();
            MethodBinding[] methodBindingArr2 = this.syntheticAccessors;
            if (methodBindingArr2 == null || (methodBinding = methodBindingArr2[0]) == null) {
                codeStream.fieldAccess(Opcodes.OPC_getfield, fieldBindingOriginal, CodeStream.getConstantPoolDeclaringClass(blockScope, fieldBindingOriginal, this.actualReceiverType, this.receiver.isImplicitThis()));
            } else {
                codeStream.invoke(Opcodes.OPC_invokestatic, methodBinding, null);
            }
        }
        TypeBinding typeBinding2 = this.genericCast;
        if (typeBinding2 != null) {
            codeStream.checkcast(typeBinding2);
            typeBinding = this.genericCast;
        } else {
            typeBinding = fieldBindingOriginal.type;
        }
        if (z) {
            if (zIsStatic) {
                int i = typeBinding.f626id;
                if (i == 7 || i == 8) {
                    codeStream.dup2();
                } else {
                    codeStream.dup();
                }
            } else {
                int i2 = typeBinding.f626id;
                if (i2 == 7 || i2 == 8) {
                    codeStream.dup2_x1();
                } else {
                    codeStream.dup_x1();
                }
            }
        }
        codeStream.generateImplicitConversion(this.implicitConversion);
        codeStream.generateConstant(compoundAssignment.expression.constant, this.implicitConversion);
        codeStream.sendOperator(compoundAssignment.operator, this.implicitConversion & 15);
        codeStream.generateImplicitConversion(compoundAssignment.preAssignImplicitConversion);
        MethodBinding[] methodBindingArr3 = this.syntheticAccessors;
        fieldStore(blockScope, codeStream, fieldBindingOriginal, methodBindingArr3 == null ? null : methodBindingArr3[1], this.actualReceiverType, this.receiver.isImplicitThis(), false);
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.Reference
    public boolean isEquivalent(Reference reference) {
        char[][] thisFieldTokens = null;
        cArr = null;
        cArr = null;
        char[] cArr = null;
        if (this.receiver.isThis() && !(this.receiver instanceof QualifiedThisReference)) {
            if (reference instanceof SingleNameReference) {
                cArr = ((SingleNameReference) reference).token;
            } else if (reference instanceof FieldReference) {
                FieldReference fieldReference = (FieldReference) reference;
                if (fieldReference.receiver.isThis() && !(fieldReference.receiver instanceof QualifiedThisReference)) {
                    cArr = fieldReference.token;
                }
            }
            return cArr != null && CharOperation.equals(this.token, cArr);
        }
        char[][] thisFieldTokens2 = getThisFieldTokens(1);
        if (thisFieldTokens2 == null) {
            return false;
        }
        if (reference instanceof FieldReference) {
            thisFieldTokens = ((FieldReference) reference).getThisFieldTokens(1);
        } else if (reference instanceof QualifiedNameReference) {
            QualifiedNameReference qualifiedNameReference = (QualifiedNameReference) reference;
            if (qualifiedNameReference.binding instanceof LocalVariableBinding) {
                return false;
            }
            thisFieldTokens = qualifiedNameReference.tokens;
        }
        return CharOperation.equals(thisFieldTokens2, thisFieldTokens);
    }

    private char[][] getThisFieldTokens(int i) {
        if (this.receiver.isThis() && !(this.receiver instanceof QualifiedThisReference)) {
            char[][] cArr = new char[i][];
            cArr[0] = this.token;
            return cArr;
        }
        Expression expression = this.receiver;
        if (!(expression instanceof FieldReference)) {
            return null;
        }
        char[][] thisFieldTokens = ((FieldReference) expression).getThisFieldTokens(i + 1);
        if (thisFieldTokens != null) {
            thisFieldTokens[thisFieldTokens.length - i] = this.token;
        }
        return thisFieldTokens;
    }

    @Override // org.eclipse.jdt.internal.compiler.lookup.InvocationSite
    public boolean isSuperAccess() {
        return this.receiver.isSuper();
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.ASTNode, org.eclipse.jdt.internal.compiler.lookup.InvocationSite
    public boolean isQualifiedSuper() {
        return this.receiver.isQualifiedSuper();
    }

    @Override // org.eclipse.jdt.internal.compiler.lookup.InvocationSite
    public boolean isTypeAccess() {
        Expression expression = this.receiver;
        return expression != null && expression.isTypeReference();
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.Reference
    public FieldBinding lastFieldBinding() {
        return this.binding;
    }

    public void manageSyntheticAccessIfNecessary(BlockScope blockScope, FlowInfo flowInfo, boolean z) {
        if ((flowInfo.tagBits & 1) != 0) {
            return;
        }
        FieldBinding fieldBindingOriginal = this.binding.original();
        if (this.binding.isPrivate()) {
            if (TypeBinding.notEquals(blockScope.enclosingSourceType(), fieldBindingOriginal.declaringClass) && this.binding.constant(blockScope) == Constant.NotAConstant) {
                if (this.syntheticAccessors == null) {
                    this.syntheticAccessors = new MethodBinding[2];
                }
                this.syntheticAccessors[!z ? 1 : 0] = ((SourceTypeBinding) fieldBindingOriginal.declaringClass).addSyntheticMethod(fieldBindingOriginal, z, false);
                blockScope.problemReporter().needToEmulateFieldAccess(fieldBindingOriginal, this, z);
                return;
            }
            return;
        }
        Expression expression = this.receiver;
        if (expression instanceof QualifiedSuperReference) {
            SourceTypeBinding sourceTypeBinding = (SourceTypeBinding) ((QualifiedSuperReference) expression).currentCompatibleType;
            if (this.syntheticAccessors == null) {
                this.syntheticAccessors = new MethodBinding[2];
            }
            this.syntheticAccessors[!z ? 1 : 0] = sourceTypeBinding.addSyntheticMethod(fieldBindingOriginal, z, isSuperAccess());
            blockScope.problemReporter().needToEmulateFieldAccess(fieldBindingOriginal, this, z);
            return;
        }
        if (!this.binding.isProtected() || (this.bits & ASTNode.DepthMASK) == 0) {
            return;
        }
        PackageBinding packageBinding = this.binding.declaringClass.getPackage();
        SourceTypeBinding sourceTypeBindingEnclosingSourceType = blockScope.enclosingSourceType();
        if (packageBinding != sourceTypeBindingEnclosingSourceType.getPackage()) {
            SourceTypeBinding sourceTypeBinding2 = (SourceTypeBinding) sourceTypeBindingEnclosingSourceType.enclosingTypeAt((this.bits & ASTNode.DepthMASK) >> 5);
            if (this.syntheticAccessors == null) {
                this.syntheticAccessors = new MethodBinding[2];
            }
            this.syntheticAccessors[!z ? 1 : 0] = sourceTypeBinding2.addSyntheticMethod(fieldBindingOriginal, z, isSuperAccess());
            blockScope.problemReporter().needToEmulateFieldAccess(fieldBindingOriginal, this, z);
        }
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.Expression
    public Constant optimizedBooleanConstant() {
        if (this.resolvedType == null) {
            return Constant.NotAConstant;
        }
        int i = this.resolvedType.f626id;
        if (i == 5 || i == 33) {
            return this.constant != Constant.NotAConstant ? this.constant : this.binding.constant();
        }
        return Constant.NotAConstant;
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.Expression
    public TypeBinding postConversionType(Scope scope) {
        TypeBinding typeBinding = this.resolvedType;
        TypeBinding typeBinding2 = this.genericCast;
        if (typeBinding2 != null) {
            typeBinding = typeBinding2;
        }
        switch ((this.implicitConversion & 255) >> 4) {
            case 2:
                typeBinding = TypeBinding.CHAR;
                break;
            case 3:
                typeBinding = TypeBinding.BYTE;
                break;
            case 4:
                typeBinding = TypeBinding.SHORT;
                break;
            case 5:
                typeBinding = TypeBinding.BOOLEAN;
                break;
            case 7:
                typeBinding = TypeBinding.LONG;
                break;
            case 8:
                typeBinding = TypeBinding.DOUBLE;
                break;
            case 9:
                typeBinding = TypeBinding.FLOAT;
                break;
            case 10:
                typeBinding = TypeBinding.INT;
                break;
        }
        return (this.implicitConversion & 512) != 0 ? scope.environment().computeBoxingType(typeBinding) : typeBinding;
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.Expression
    public StringBuffer printExpression(int i, StringBuffer stringBuffer) {
        return this.receiver.printExpression(0, stringBuffer).append('.').append(this.token);
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.Expression
    public TypeBinding resolveType(BlockScope blockScope) {
        boolean z;
        Expression expression = this.receiver;
        if (expression instanceof CastExpression) {
            expression.bits |= 32;
            z = true;
        } else {
            z = false;
        }
        TypeBinding typeBindingResolveType = this.receiver.resolveType(blockScope);
        this.actualReceiverType = typeBindingResolveType;
        if (typeBindingResolveType == null) {
            this.constant = Constant.NotAConstant;
            return null;
        }
        if (z && TypeBinding.equalsEquals(((CastExpression) this.receiver).expression.resolvedType, this.actualReceiverType)) {
            blockScope.problemReporter().unnecessaryCast((CastExpression) this.receiver);
        }
        FieldBinding field = blockScope.getField(this.actualReceiverType, this.token, this);
        this.binding = field;
        if (!field.isValidBinding()) {
            this.constant = Constant.NotAConstant;
            if (this.receiver.resolvedType instanceof ProblemReferenceBinding) {
                return null;
            }
            ReferenceBinding referenceBinding = field.declaringClass;
            if (referenceBinding == null || !referenceBinding.isAnonymousType() || !(referenceBinding.superclass() instanceof MissingTypeBinding)) {
                blockScope.problemReporter().invalidField(this, this.actualReceiverType);
            }
            if (field instanceof ProblemFieldBinding) {
                ProblemFieldBinding problemFieldBinding = (ProblemFieldBinding) field;
                FieldBinding fieldBinding = problemFieldBinding.closestMatch;
                int iProblemId = problemFieldBinding.problemId();
                if ((iProblemId == 2 || iProblemId == 5 || iProblemId == 6 || iProblemId == 7) && fieldBinding != null) {
                    field = fieldBinding;
                }
            }
            if (!field.isValidBinding()) {
                return null;
            }
        }
        TypeBinding typeBinding = this.actualReceiverType;
        TypeBinding erasureCompatibleType = typeBinding.getErasureCompatibleType(field.declaringClass);
        this.actualReceiverType = erasureCompatibleType;
        this.receiver.computeConversion(blockScope, erasureCompatibleType, erasureCompatibleType);
        if (TypeBinding.notEquals(this.actualReceiverType, typeBinding) && TypeBinding.notEquals(this.receiver.postConversionType(blockScope), this.actualReceiverType)) {
            this.bits |= 262144;
        }
        if (isFieldUseDeprecated(field, blockScope, this.bits)) {
            blockScope.problemReporter().deprecatedField(field, this);
        }
        boolean zIsImplicitThis = this.receiver.isImplicitThis();
        this.constant = zIsImplicitThis ? field.constant(blockScope) : Constant.NotAConstant;
        if (field.isStatic()) {
            if (!zIsImplicitThis) {
                Expression expression2 = this.receiver;
                if (!(expression2 instanceof NameReference) || (((NameReference) expression2).bits & 4) == 0) {
                    blockScope.problemReporter().nonStaticAccessToStaticField(this, field);
                }
            }
            ReferenceBinding referenceBinding2 = this.binding.declaringClass;
            if (!zIsImplicitThis && TypeBinding.notEquals(referenceBinding2, this.actualReceiverType) && referenceBinding2.canBeSeenBy(blockScope)) {
                blockScope.problemReporter().indirectAccessToStaticField(this, field);
            }
            if (referenceBinding2.isEnum()) {
                MethodScope methodScope = blockScope.methodScope();
                SourceTypeBinding sourceTypeBindingEnclosingSourceType = blockScope.enclosingSourceType();
                if (this.constant == Constant.NotAConstant && !methodScope.isStatic && ((TypeBinding.equalsEquals(sourceTypeBindingEnclosingSourceType, referenceBinding2) || TypeBinding.equalsEquals(sourceTypeBindingEnclosingSourceType.superclass, referenceBinding2)) && methodScope.isInsideInitializerOrConstructor())) {
                    blockScope.problemReporter().enumStaticFieldUsedDuringInitialization(this.binding, this);
                }
            }
        }
        TypeBinding typeBindingCapture = field.type;
        if (typeBindingCapture != null) {
            if ((this.bits & 8192) == 0) {
                typeBindingCapture = typeBindingCapture.capture(blockScope, this.sourceStart, this.sourceEnd);
            }
            this.resolvedType = typeBindingCapture;
            if ((typeBindingCapture.tagBits & 128) != 0) {
                blockScope.problemReporter().invalidType(this, typeBindingCapture);
                return null;
            }
        }
        return typeBindingCapture;
    }

    @Override // org.eclipse.jdt.internal.compiler.lookup.InvocationSite
    public void setActualReceiverType(ReferenceBinding referenceBinding) {
        this.actualReceiverType = referenceBinding;
    }

    @Override // org.eclipse.jdt.internal.compiler.lookup.InvocationSite
    public void setDepth(int i) {
        this.bits &= -8161;
        if (i > 0) {
            this.bits = ((i & 255) << 5) | this.bits;
        }
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.Expression, org.eclipse.jdt.internal.compiler.ast.ASTNode
    public void traverse(ASTVisitor aSTVisitor, BlockScope blockScope) {
        if (aSTVisitor.visit(this, blockScope)) {
            this.receiver.traverse(aSTVisitor, blockScope);
        }
        aSTVisitor.endVisit(this, blockScope);
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.Expression
    public VariableBinding nullAnnotatedVariableBinding(boolean z) {
        FieldBinding fieldBinding = this.binding;
        if (fieldBinding == null) {
            return null;
        }
        if (z || (fieldBinding.tagBits & TagBits.AnnotationNullMASK) != 0) {
            return this.binding;
        }
        return null;
    }
}
