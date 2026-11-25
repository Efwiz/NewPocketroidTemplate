package org.eclipse.jdt.internal.compiler.ast;

import org.eclipse.jdt.core.compiler.CharOperation;
import org.eclipse.jdt.internal.compiler.ASTVisitor;
import org.eclipse.jdt.internal.compiler.classfmt.ClassFileConstants;
import org.eclipse.jdt.internal.compiler.codegen.CodeStream;
import org.eclipse.jdt.internal.compiler.codegen.Opcodes;
import org.eclipse.jdt.internal.compiler.flow.FlowContext;
import org.eclipse.jdt.internal.compiler.flow.FlowInfo;
import org.eclipse.jdt.internal.compiler.impl.Constant;
import org.eclipse.jdt.internal.compiler.lookup.BlockScope;
import org.eclipse.jdt.internal.compiler.lookup.ClassScope;
import org.eclipse.jdt.internal.compiler.lookup.FieldBinding;
import org.eclipse.jdt.internal.compiler.lookup.LocalVariableBinding;
import org.eclipse.jdt.internal.compiler.lookup.MethodBinding;
import org.eclipse.jdt.internal.compiler.lookup.MethodScope;
import org.eclipse.jdt.internal.compiler.lookup.MissingTypeBinding;
import org.eclipse.jdt.internal.compiler.lookup.ProblemFieldBinding;
import org.eclipse.jdt.internal.compiler.lookup.ProblemReferenceBinding;
import org.eclipse.jdt.internal.compiler.lookup.ReferenceBinding;
import org.eclipse.jdt.internal.compiler.lookup.Scope;
import org.eclipse.jdt.internal.compiler.lookup.SourceTypeBinding;
import org.eclipse.jdt.internal.compiler.lookup.TagBits;
import org.eclipse.jdt.internal.compiler.lookup.TypeBinding;
import org.eclipse.jdt.internal.compiler.lookup.VariableBinding;
import org.eclipse.jdt.internal.compiler.problem.AbortMethod;

/* JADX INFO: loaded from: classes14.dex */
public class SingleNameReference extends NameReference implements OperatorIds {
    public static final int READ = 0;
    public static final int WRITE = 1;
    public TypeBinding genericCast;
    public MethodBinding[] syntheticAccessors;
    public char[] token;

    @Override // org.eclipse.jdt.internal.compiler.lookup.InvocationSite
    public TypeBinding[] genericTypeArguments() {
        return null;
    }

    public SingleNameReference(char[] cArr, long j) {
        this.token = cArr;
        this.sourceStart = (int) (j >>> 32);
        this.sourceEnd = (int) j;
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.Reference
    public FlowInfo analyseAssignment(BlockScope blockScope, FlowContext flowContext, FlowInfo flowInfo, Assignment assignment, boolean z) {
        FlowInfo flowInfoUnconditionalInits = flowInfo;
        boolean z2 = (flowInfoUnconditionalInits.tagBits & 3) == 0;
        if (z) {
            int i = this.bits & 7;
            if (i == 1) {
                FieldBinding fieldBinding = (FieldBinding) this.binding;
                if (fieldBinding.isBlankFinal() && blockScope.needBlankFinalFieldInitializationCheck(fieldBinding) && !flowContext.getInitsForFinalBlankInitializationCheck(fieldBinding.declaringClass.original(), flowInfoUnconditionalInits).isDefinitelyAssigned(fieldBinding)) {
                    blockScope.problemReporter().uninitializedBlankFinalField(fieldBinding, this);
                }
                manageSyntheticAccessIfNecessary(blockScope, flowInfoUnconditionalInits, true);
            } else if (i == 2) {
                LocalVariableBinding localVariableBinding = (LocalVariableBinding) this.binding;
                if (!flowInfoUnconditionalInits.isDefinitelyAssigned(localVariableBinding)) {
                    blockScope.problemReporter().uninitializedLocalVariable(localVariableBinding, this);
                }
                if (localVariableBinding.useFlag != 1) {
                    if (z2 && (this.implicitConversion & 1024) != 0) {
                        localVariableBinding.useFlag = 1;
                    } else if (localVariableBinding.useFlag <= 0) {
                        localVariableBinding.useFlag--;
                    }
                }
            }
        }
        if (assignment.expression != null) {
            flowInfoUnconditionalInits = assignment.expression.analyseCode(blockScope, flowContext, flowInfoUnconditionalInits).unconditionalInits();
        }
        int i2 = this.bits & 7;
        if (i2 == 1) {
            manageSyntheticAccessIfNecessary(blockScope, flowInfoUnconditionalInits, false);
            FieldBinding fieldBinding2 = (FieldBinding) this.binding;
            if (fieldBinding2.isFinal()) {
                if (!z && fieldBinding2.isBlankFinal() && blockScope.allowBlankFinalFieldAssignment(fieldBinding2)) {
                    if (flowInfoUnconditionalInits.isPotentiallyAssigned(fieldBinding2)) {
                        blockScope.problemReporter().duplicateInitializationOfBlankFinalField(fieldBinding2, this);
                    } else {
                        flowContext.recordSettingFinal(fieldBinding2, this, flowInfoUnconditionalInits);
                    }
                    flowInfoUnconditionalInits.markAsDefinitelyAssigned(fieldBinding2);
                } else {
                    blockScope.problemReporter().cannotAssignToFinalField(fieldBinding2, this);
                }
            } else if (!z && ((fieldBinding2.isNonNull() || fieldBinding2.type.isTypeVariable()) && TypeBinding.equalsEquals(fieldBinding2.declaringClass, blockScope.enclosingReceiverType()))) {
                flowInfoUnconditionalInits.markAsDefinitelyAssigned(fieldBinding2);
            }
        } else if (i2 == 2) {
            LocalVariableBinding localVariableBinding2 = (LocalVariableBinding) this.binding;
            boolean zIsFinal = localVariableBinding2.isFinal();
            if (!flowInfoUnconditionalInits.isDefinitelyAssigned(localVariableBinding2)) {
                this.bits |= 8;
            } else {
                this.bits &= -9;
            }
            if (flowInfoUnconditionalInits.isPotentiallyAssigned(localVariableBinding2) || (this.bits & 524288) != 0) {
                localVariableBinding2.tagBits &= -2049;
                if (!zIsFinal && (this.bits & 524288) != 0) {
                    blockScope.problemReporter().cannotReferToNonEffectivelyFinalOuterLocal(localVariableBinding2, this);
                }
            }
            if (!zIsFinal && (localVariableBinding2.tagBits & 2048) != 0 && (localVariableBinding2.tagBits & 1024) == 0) {
                flowContext.recordSettingFinal(localVariableBinding2, this, flowInfoUnconditionalInits);
            } else if (zIsFinal) {
                if ((this.bits & ASTNode.DepthMASK) == 0) {
                    if ((z2 && z) || !localVariableBinding2.isBlankFinal()) {
                        blockScope.problemReporter().cannotAssignToFinalLocal(localVariableBinding2, this);
                    } else if (flowInfoUnconditionalInits.isPotentiallyAssigned(localVariableBinding2)) {
                        blockScope.problemReporter().duplicateInitializationOfFinalLocal(localVariableBinding2, this);
                    } else if ((this.bits & 524288) != 0) {
                        blockScope.problemReporter().cannotAssignToFinalOuterLocal(localVariableBinding2, this);
                    } else {
                        flowContext.recordSettingFinal(localVariableBinding2, this, flowInfoUnconditionalInits);
                    }
                } else {
                    blockScope.problemReporter().cannotAssignToFinalOuterLocal(localVariableBinding2, this);
                }
            } else if ((localVariableBinding2.tagBits & 1024) != 0) {
                blockScope.problemReporter().parameterAssignment(localVariableBinding2, this);
            }
            flowInfoUnconditionalInits.markAsDefinitelyAssigned(localVariableBinding2);
        }
        manageEnclosingInstanceAccessIfNecessary(blockScope, flowInfoUnconditionalInits);
        return flowInfoUnconditionalInits;
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.Reference, org.eclipse.jdt.internal.compiler.ast.Expression, org.eclipse.jdt.internal.compiler.ast.Statement
    public FlowInfo analyseCode(BlockScope blockScope, FlowContext flowContext, FlowInfo flowInfo) {
        return analyseCode(blockScope, flowContext, flowInfo, true);
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.Expression
    public FlowInfo analyseCode(BlockScope blockScope, FlowContext flowContext, FlowInfo flowInfo, boolean z) {
        int i = this.bits & 7;
        if (i == 1) {
            if (z || blockScope.compilerOptions().complianceLevel >= ClassFileConstants.JDK1_4) {
                manageSyntheticAccessIfNecessary(blockScope, flowInfo, true);
            }
            FieldBinding fieldBinding = (FieldBinding) this.binding;
            if (fieldBinding.isBlankFinal() && blockScope.needBlankFinalFieldInitializationCheck(fieldBinding) && !flowContext.getInitsForFinalBlankInitializationCheck(fieldBinding.declaringClass.original(), flowInfo).isDefinitelyAssigned(fieldBinding)) {
                blockScope.problemReporter().uninitializedBlankFinalField(fieldBinding, this);
            }
        } else if (i == 2) {
            LocalVariableBinding localVariableBinding = (LocalVariableBinding) this.binding;
            if (!flowInfo.isDefinitelyAssigned(localVariableBinding)) {
                blockScope.problemReporter().uninitializedLocalVariable(localVariableBinding, this);
            }
            if ((flowInfo.tagBits & 3) == 0) {
                localVariableBinding.useFlag = 1;
            } else if (localVariableBinding.useFlag == 0) {
                localVariableBinding.useFlag = 2;
            }
        }
        if (z) {
            manageEnclosingInstanceAccessIfNecessary(blockScope, flowInfo);
        }
        return flowInfo;
    }

    public TypeBinding checkFieldAccess(BlockScope blockScope) {
        FieldBinding fieldBinding = (FieldBinding) this.binding;
        this.constant = fieldBinding.constant(blockScope);
        this.bits &= -8;
        this.bits |= 1;
        MethodScope methodScope = blockScope.methodScope();
        if (fieldBinding.isStatic()) {
            ReferenceBinding referenceBinding = fieldBinding.declaringClass;
            if (referenceBinding.isEnum()) {
                SourceTypeBinding sourceTypeBindingEnclosingSourceType = blockScope.enclosingSourceType();
                if (this.constant == Constant.NotAConstant && !methodScope.isStatic && ((TypeBinding.equalsEquals(sourceTypeBindingEnclosingSourceType, referenceBinding) || TypeBinding.equalsEquals(sourceTypeBindingEnclosingSourceType.superclass, referenceBinding)) && methodScope.isInsideInitializerOrConstructor())) {
                    blockScope.problemReporter().enumStaticFieldUsedDuringInitialization(fieldBinding, this);
                }
            }
        } else {
            if (blockScope.compilerOptions().getSeverity(4194304) != 256) {
                blockScope.problemReporter().unqualifiedFieldAccess(this, fieldBinding);
            }
            if (methodScope.isStatic) {
                blockScope.problemReporter().staticFieldAccessToNonStaticVariable(this, fieldBinding);
                return fieldBinding.type;
            }
            blockScope.tagAsAccessingEnclosingInstanceStateOf(fieldBinding.declaringClass, false);
        }
        if (isFieldUseDeprecated(fieldBinding, blockScope, this.bits)) {
            blockScope.problemReporter().deprecatedField(fieldBinding, this);
        }
        if ((this.bits & 8192) == 0 && TypeBinding.equalsEquals(methodScope.enclosingSourceType(), fieldBinding.original().declaringClass) && methodScope.lastVisibleFieldID >= 0 && fieldBinding.f627id >= methodScope.lastVisibleFieldID && (!fieldBinding.isStatic() || methodScope.isStatic)) {
            blockScope.problemReporter().forwardReference(this, 0, fieldBinding);
            this.bits |= 536870912;
        }
        return fieldBinding.type;
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.Reference, org.eclipse.jdt.internal.compiler.ast.Expression
    public boolean checkNPE(BlockScope blockScope, FlowContext flowContext, FlowInfo flowInfo, int i) {
        if (super.checkNPE(blockScope, flowContext, flowInfo, i) || !blockScope.compilerOptions().isAnnotationBasedNullAnalysisEnabled || !(this.binding instanceof FieldBinding)) {
            return false;
        }
        return checkNullableFieldDereference(blockScope, (FieldBinding) this.binding, ((long) this.sourceEnd) + (((long) this.sourceStart) << 32), flowContext, i);
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.Expression
    public void computeConversion(Scope scope, TypeBinding typeBinding, TypeBinding typeBinding2) {
        TypeBinding typeBinding3;
        if (typeBinding == null || typeBinding2 == null) {
            return;
        }
        if (this.binding != null && this.binding.isValidBinding()) {
            if ((this.bits & 1) != 0) {
                typeBinding3 = ((FieldBinding) this.binding).original().type;
            } else {
                typeBinding3 = (this.bits & 2) != 0 ? ((LocalVariableBinding) this.binding).type : null;
            }
            if (typeBinding3 != null && typeBinding3.leafComponentType().isTypeVariable()) {
                TypeBinding typeBindingGenericCast = typeBinding3.genericCast(scope.boxing((typeBinding2.isBaseType() || !typeBinding.isBaseType()) ? typeBinding : typeBinding2));
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
    public void generateAssignment(BlockScope blockScope, CodeStream codeStream, Assignment assignment, boolean z) {
        if (assignment.expression.isCompactableOperation()) {
            BinaryExpression binaryExpression = (BinaryExpression) assignment.expression;
            int i = (binaryExpression.bits & ASTNode.OperatorMASK) >> 6;
            if (binaryExpression.left instanceof SingleNameReference) {
                SingleNameReference singleNameReference = (SingleNameReference) binaryExpression.left;
                if (singleNameReference.binding == this.binding) {
                    MethodBinding[] methodBindingArr = this.syntheticAccessors;
                    singleNameReference.generateCompoundAssignment(blockScope, codeStream, methodBindingArr != null ? methodBindingArr[1] : null, binaryExpression.right, i, binaryExpression.implicitConversion, z);
                    if (z) {
                        codeStream.generateImplicitConversion(assignment.implicitConversion);
                        return;
                    }
                    return;
                }
            }
            if ((binaryExpression.right instanceof SingleNameReference) && (i == 14 || i == 15)) {
                SingleNameReference singleNameReference2 = (SingleNameReference) binaryExpression.right;
                if (singleNameReference2.binding == this.binding && binaryExpression.left.constant != Constant.NotAConstant && ((binaryExpression.left.implicitConversion & 255) >> 4) != 11 && ((binaryExpression.right.implicitConversion & 255) >> 4) != 11) {
                    MethodBinding[] methodBindingArr2 = this.syntheticAccessors;
                    singleNameReference2.generateCompoundAssignment(blockScope, codeStream, methodBindingArr2 != null ? methodBindingArr2[1] : null, binaryExpression.left, i, binaryExpression.implicitConversion, z);
                    if (z) {
                        codeStream.generateImplicitConversion(assignment.implicitConversion);
                        return;
                    }
                    return;
                }
            }
        }
        int i2 = this.bits & 7;
        if (i2 == 1) {
            int i3 = codeStream.position;
            FieldBinding fieldBindingOriginal = ((FieldBinding) this.binding).original();
            if (!fieldBindingOriginal.isStatic()) {
                if ((this.bits & ASTNode.DepthMASK) != 0) {
                    ReferenceBinding referenceBindingEnclosingTypeAt = blockScope.enclosingSourceType().enclosingTypeAt((this.bits & ASTNode.DepthMASK) >> 5);
                    codeStream.generateOuterAccess(blockScope.getEmulationPath(referenceBindingEnclosingTypeAt, true, false), this, referenceBindingEnclosingTypeAt, blockScope);
                } else {
                    generateReceiver(codeStream);
                }
            }
            codeStream.recordPositionsFrom(i3, this.sourceStart);
            assignment.expression.generateCode(blockScope, codeStream, true);
            MethodBinding[] methodBindingArr3 = this.syntheticAccessors;
            fieldStore(blockScope, codeStream, fieldBindingOriginal, methodBindingArr3 == null ? null : methodBindingArr3[1], this.actualReceiverType, true, z);
            if (z) {
                codeStream.generateImplicitConversion(assignment.implicitConversion);
                return;
            }
            return;
        }
        if (i2 != 2) {
            return;
        }
        LocalVariableBinding localVariableBinding = (LocalVariableBinding) this.binding;
        if (localVariableBinding.resolvedPosition != -1) {
            assignment.expression.generateCode(blockScope, codeStream, true);
            if (localVariableBinding.type.isArrayType() && (assignment.expression instanceof CastExpression) && ((CastExpression) assignment.expression).innermostCastedExpression().resolvedType == TypeBinding.NULL) {
                codeStream.checkcast(localVariableBinding.type);
            }
            codeStream.store(localVariableBinding, z);
            if ((this.bits & 8) != 0) {
                localVariableBinding.recordInitializationStartPC(codeStream.position);
            }
            if (z) {
                codeStream.generateImplicitConversion(assignment.implicitConversion);
                return;
            }
            return;
        }
        if (assignment.expression.constant != Constant.NotAConstant) {
            if (z) {
                codeStream.generateConstant(assignment.expression.constant, assignment.implicitConversion);
                return;
            }
            return;
        }
        assignment.expression.generateCode(blockScope, codeStream, true);
        if (z) {
            codeStream.generateImplicitConversion(assignment.implicitConversion);
            return;
        }
        int i4 = localVariableBinding.type.f626id;
        if (i4 == 7 || i4 == 8) {
            codeStream.pop2();
        } else {
            codeStream.pop();
        }
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.Expression
    public void generateCode(BlockScope blockScope, CodeStream codeStream, boolean z) {
        MethodBinding methodBinding;
        MethodBinding methodBinding2;
        int i = codeStream.position;
        if (this.constant != Constant.NotAConstant) {
            if (z) {
                codeStream.generateConstant(this.constant, this.implicitConversion);
            }
            codeStream.recordPositionsFrom(i, this.sourceStart);
            return;
        }
        int i2 = this.bits & 7;
        if (i2 == 1) {
            FieldBinding fieldBindingOriginal = ((FieldBinding) this.binding).original();
            Constant constant = fieldBindingOriginal.constant();
            if (constant != Constant.NotAConstant) {
                if (z) {
                    codeStream.generateConstant(constant, this.implicitConversion);
                }
                codeStream.recordPositionsFrom(i, this.sourceStart);
                return;
            }
            if (fieldBindingOriginal.isStatic()) {
                if (!z && TypeBinding.equalsEquals(((FieldBinding) this.binding).original().declaringClass, this.actualReceiverType.erasure()) && (this.implicitConversion & 1024) == 0 && this.genericCast == null) {
                    codeStream.recordPositionsFrom(i, this.sourceStart);
                    return;
                }
                MethodBinding[] methodBindingArr = this.syntheticAccessors;
                if (methodBindingArr == null || (methodBinding2 = methodBindingArr[0]) == null) {
                    codeStream.fieldAccess(Opcodes.OPC_getstatic, fieldBindingOriginal, CodeStream.getConstantPoolDeclaringClass((Scope) blockScope, fieldBindingOriginal, this.actualReceiverType, true));
                } else {
                    codeStream.invoke(Opcodes.OPC_invokestatic, methodBinding2, null);
                }
            } else {
                if (!z && (this.implicitConversion & 1024) == 0 && this.genericCast == null) {
                    codeStream.recordPositionsFrom(i, this.sourceStart);
                    return;
                }
                if ((this.bits & ASTNode.DepthMASK) != 0) {
                    ReferenceBinding referenceBindingEnclosingTypeAt = blockScope.enclosingSourceType().enclosingTypeAt((this.bits & ASTNode.DepthMASK) >> 5);
                    codeStream.generateOuterAccess(blockScope.getEmulationPath(referenceBindingEnclosingTypeAt, true, false), this, referenceBindingEnclosingTypeAt, blockScope);
                } else {
                    generateReceiver(codeStream);
                }
                MethodBinding[] methodBindingArr2 = this.syntheticAccessors;
                if (methodBindingArr2 == null || (methodBinding = methodBindingArr2[0]) == null) {
                    codeStream.fieldAccess(Opcodes.OPC_getfield, fieldBindingOriginal, CodeStream.getConstantPoolDeclaringClass((Scope) blockScope, fieldBindingOriginal, this.actualReceiverType, true));
                } else {
                    codeStream.invoke(Opcodes.OPC_invokestatic, methodBinding, null);
                }
            }
        } else if (i2 == 2) {
            LocalVariableBinding localVariableBinding = (LocalVariableBinding) this.binding;
            if (localVariableBinding.resolvedPosition == -1) {
                if (z) {
                    localVariableBinding.useFlag = 1;
                    throw new AbortMethod(CodeStream.RESTART_CODE_GEN_FOR_UNUSED_LOCALS_MODE, null);
                }
                codeStream.recordPositionsFrom(i, this.sourceStart);
                return;
            }
            if (!z && (this.implicitConversion & 1024) == 0) {
                codeStream.recordPositionsFrom(i, this.sourceStart);
                return;
            } else if ((this.bits & 524288) != 0) {
                checkEffectiveFinality(localVariableBinding, blockScope);
                codeStream.generateOuterAccess(blockScope.getEmulationPath(localVariableBinding), this, localVariableBinding, blockScope);
            } else {
                codeStream.load(localVariableBinding);
            }
        } else {
            codeStream.recordPositionsFrom(i, this.sourceStart);
            return;
        }
        TypeBinding typeBinding = this.genericCast;
        if (typeBinding != null) {
            codeStream.checkcast(typeBinding);
        }
        if (z) {
            codeStream.generateImplicitConversion(this.implicitConversion);
        } else {
            boolean z2 = (this.implicitConversion & 1024) != 0;
            if (z2) {
                codeStream.generateImplicitConversion(this.implicitConversion);
            }
            int i3 = (z2 ? postConversionType(blockScope) : this.resolvedType).f626id;
            if (i3 == 7 || i3 == 8) {
                codeStream.pop2();
            } else {
                codeStream.pop();
            }
        }
        codeStream.recordPositionsFrom(i, this.sourceStart);
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.Reference
    public void generateCompoundAssignment(BlockScope blockScope, CodeStream codeStream, Expression expression, int i, int i2, boolean z) {
        int i3 = this.bits & 7;
        if (i3 == 1) {
            reportOnlyUselesslyReadPrivateField(blockScope, (FieldBinding) this.binding, z);
        } else if (i3 == 2) {
            Reference.reportOnlyUselesslyReadLocal(blockScope, (LocalVariableBinding) this.binding, z);
        }
        MethodBinding[] methodBindingArr = this.syntheticAccessors;
        generateCompoundAssignment(blockScope, codeStream, methodBindingArr == null ? null : methodBindingArr[1], expression, i, i2, z);
    }

    public void generateCompoundAssignment(BlockScope blockScope, CodeStream codeStream, MethodBinding methodBinding, Expression expression, int i, int i2, boolean z) {
        MethodBinding methodBinding2;
        MethodBinding methodBinding3;
        int i3 = this.bits & 7;
        if (i3 == 1) {
            FieldBinding fieldBindingOriginal = ((FieldBinding) this.binding).original();
            if (fieldBindingOriginal.isStatic()) {
                MethodBinding[] methodBindingArr = this.syntheticAccessors;
                if (methodBindingArr == null || (methodBinding3 = methodBindingArr[0]) == null) {
                    codeStream.fieldAccess(Opcodes.OPC_getstatic, fieldBindingOriginal, CodeStream.getConstantPoolDeclaringClass((Scope) blockScope, fieldBindingOriginal, this.actualReceiverType, true));
                } else {
                    codeStream.invoke(Opcodes.OPC_invokestatic, methodBinding3, null);
                }
            } else {
                if ((this.bits & ASTNode.DepthMASK) != 0) {
                    ReferenceBinding referenceBindingEnclosingTypeAt = blockScope.enclosingSourceType().enclosingTypeAt((this.bits & ASTNode.DepthMASK) >> 5);
                    codeStream.generateOuterAccess(blockScope.getEmulationPath(referenceBindingEnclosingTypeAt, true, false), this, referenceBindingEnclosingTypeAt, blockScope);
                } else {
                    codeStream.aload_0();
                }
                codeStream.dup();
                MethodBinding[] methodBindingArr2 = this.syntheticAccessors;
                if (methodBindingArr2 == null || (methodBinding2 = methodBindingArr2[0]) == null) {
                    codeStream.fieldAccess(Opcodes.OPC_getfield, fieldBindingOriginal, CodeStream.getConstantPoolDeclaringClass((Scope) blockScope, fieldBindingOriginal, this.actualReceiverType, true));
                } else {
                    codeStream.invoke(Opcodes.OPC_invokestatic, methodBinding2, null);
                }
            }
        } else if (i3 == 2) {
            LocalVariableBinding localVariableBinding = (LocalVariableBinding) this.binding;
            int i4 = localVariableBinding.type.f626id;
            if (i4 == 10) {
                Constant constant = expression.constant;
                if (localVariableBinding.resolvedPosition == -1) {
                    if (z) {
                        localVariableBinding.useFlag = 1;
                        throw new AbortMethod(CodeStream.RESTART_CODE_GEN_FOR_UNUSED_LOCALS_MODE, null);
                    }
                    if (constant == Constant.NotAConstant) {
                        expression.generateCode(blockScope, codeStream, false);
                        return;
                    }
                    return;
                }
                if (constant != Constant.NotAConstant && constant.typeID() != 9 && constant.typeID() != 8) {
                    if (i == 13) {
                        int i5 = -constant.intValue();
                        if (i5 == ((short) i5)) {
                            codeStream.iinc(localVariableBinding.resolvedPosition, i5);
                            if (z) {
                                codeStream.load(localVariableBinding);
                                return;
                            }
                            return;
                        }
                    } else if (i == 14) {
                        int iIntValue = constant.intValue();
                        if (iIntValue == ((short) iIntValue)) {
                            codeStream.iinc(localVariableBinding.resolvedPosition, iIntValue);
                            if (z) {
                                codeStream.load(localVariableBinding);
                                return;
                            }
                            return;
                        }
                    }
                }
            } else if (i4 == 11) {
                codeStream.generateStringConcatenationAppend(blockScope, this, expression);
                if (z) {
                    codeStream.dup();
                }
                codeStream.store(localVariableBinding, false);
                return;
            }
            if (localVariableBinding.resolvedPosition == -1) {
                Constant constant2 = expression.constant;
                if (z) {
                    localVariableBinding.useFlag = 1;
                    throw new AbortMethod(CodeStream.RESTART_CODE_GEN_FOR_UNUSED_LOCALS_MODE, null);
                }
                if (constant2 == Constant.NotAConstant) {
                    expression.generateCode(blockScope, codeStream, false);
                    return;
                }
                return;
            }
            codeStream.load(localVariableBinding);
        }
        int i6 = (this.implicitConversion & 255) >> 4;
        if (i6 == 0 || i6 == 1 || i6 == 11) {
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
            codeStream.sendOperator(i, i6);
            codeStream.generateImplicitConversion(i2);
        }
        int i7 = this.bits & 7;
        if (i7 == 1) {
            fieldStore(blockScope, codeStream, ((FieldBinding) this.binding).original(), methodBinding, this.actualReceiverType, true, z);
            return;
        }
        if (i7 != 2) {
            return;
        }
        LocalVariableBinding localVariableBinding2 = (LocalVariableBinding) this.binding;
        if (z) {
            int i8 = localVariableBinding2.type.f626id;
            if (i8 == 7 || i8 == 8) {
                codeStream.dup2();
            } else {
                codeStream.dup();
            }
        }
        codeStream.store(localVariableBinding2, false);
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.Reference
    public void generatePostIncrement(BlockScope blockScope, CodeStream codeStream, CompoundAssignment compoundAssignment, boolean z) {
        MethodBinding methodBinding;
        TypeBinding typeBinding;
        MethodBinding methodBinding2;
        int i = this.bits & 7;
        if (i != 1) {
            if (i != 2) {
                return;
            }
            LocalVariableBinding localVariableBinding = (LocalVariableBinding) this.binding;
            Reference.reportOnlyUselesslyReadLocal(blockScope, localVariableBinding, z);
            if (localVariableBinding.resolvedPosition == -1) {
                if (z) {
                    localVariableBinding.useFlag = 1;
                    throw new AbortMethod(CodeStream.RESTART_CODE_GEN_FOR_UNUSED_LOCALS_MODE, null);
                }
                return;
            }
            if (TypeBinding.equalsEquals(localVariableBinding.type, TypeBinding.INT)) {
                if (z) {
                    codeStream.load(localVariableBinding);
                }
                if (compoundAssignment.operator == 14) {
                    codeStream.iinc(localVariableBinding.resolvedPosition, 1);
                    return;
                } else {
                    codeStream.iinc(localVariableBinding.resolvedPosition, -1);
                    return;
                }
            }
            codeStream.load(localVariableBinding);
            if (z) {
                int i2 = localVariableBinding.type.f626id;
                if (i2 == 7 || i2 == 8) {
                    codeStream.dup2();
                } else {
                    codeStream.dup();
                }
            }
            codeStream.generateImplicitConversion(this.implicitConversion);
            codeStream.generateConstant(compoundAssignment.expression.constant, this.implicitConversion);
            codeStream.sendOperator(compoundAssignment.operator, this.implicitConversion & 15);
            codeStream.generateImplicitConversion(compoundAssignment.preAssignImplicitConversion);
            codeStream.store(localVariableBinding, false);
            return;
        }
        FieldBinding fieldBinding = (FieldBinding) this.binding;
        reportOnlyUselesslyReadPrivateField(blockScope, fieldBinding, z);
        FieldBinding fieldBindingOriginal = fieldBinding.original();
        if (fieldBindingOriginal.isStatic()) {
            MethodBinding[] methodBindingArr = this.syntheticAccessors;
            if (methodBindingArr == null || (methodBinding2 = methodBindingArr[0]) == null) {
                codeStream.fieldAccess(Opcodes.OPC_getstatic, fieldBindingOriginal, CodeStream.getConstantPoolDeclaringClass((Scope) blockScope, fieldBindingOriginal, this.actualReceiverType, true));
            } else {
                codeStream.invoke(Opcodes.OPC_invokestatic, methodBinding2, null);
            }
        } else {
            if ((this.bits & ASTNode.DepthMASK) != 0) {
                ReferenceBinding referenceBindingEnclosingTypeAt = blockScope.enclosingSourceType().enclosingTypeAt((this.bits & ASTNode.DepthMASK) >> 5);
                codeStream.generateOuterAccess(blockScope.getEmulationPath(referenceBindingEnclosingTypeAt, true, false), this, referenceBindingEnclosingTypeAt, blockScope);
            } else {
                codeStream.aload_0();
            }
            codeStream.dup();
            MethodBinding[] methodBindingArr2 = this.syntheticAccessors;
            if (methodBindingArr2 == null || (methodBinding = methodBindingArr2[0]) == null) {
                codeStream.fieldAccess(Opcodes.OPC_getfield, fieldBindingOriginal, CodeStream.getConstantPoolDeclaringClass((Scope) blockScope, fieldBindingOriginal, this.actualReceiverType, true));
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
            if (fieldBindingOriginal.isStatic()) {
                int i3 = typeBinding.f626id;
                if (i3 == 7 || i3 == 8) {
                    codeStream.dup2();
                } else {
                    codeStream.dup();
                }
            } else {
                int i4 = typeBinding.f626id;
                if (i4 == 7 || i4 == 8) {
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
        fieldStore(blockScope, codeStream, fieldBindingOriginal, methodBindingArr3 != null ? methodBindingArr3[1] : null, this.actualReceiverType, true, false);
    }

    public void generateReceiver(CodeStream codeStream) {
        codeStream.aload_0();
    }

    /* JADX WARN: Code duplicated, block: B:12:0x0020  */
    @Override // org.eclipse.jdt.internal.compiler.ast.Reference
    public boolean isEquivalent(Reference reference) {
        char[] cArr;
        if (reference instanceof SingleNameReference) {
            cArr = ((SingleNameReference) reference).token;
        } else if (reference instanceof FieldReference) {
            FieldReference fieldReference = (FieldReference) reference;
            if (!fieldReference.receiver.isThis() || (fieldReference.receiver instanceof QualifiedThisReference)) {
                cArr = null;
            } else {
                cArr = fieldReference.token;
            }
        } else {
            cArr = null;
        }
        return cArr != null && CharOperation.equals(this.token, cArr);
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.Expression
    public LocalVariableBinding localVariableBinding() {
        if ((this.bits & 7) != 2) {
            return null;
        }
        return (LocalVariableBinding) this.binding;
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.Expression
    public VariableBinding nullAnnotatedVariableBinding(boolean z) {
        int i = this.bits & 7;
        if (i != 1 && i != 2) {
            return null;
        }
        if (z || (((VariableBinding) this.binding).tagBits & TagBits.AnnotationNullMASK) != 0) {
            return (VariableBinding) this.binding;
        }
        return null;
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.Reference, org.eclipse.jdt.internal.compiler.ast.Expression
    public int nullStatus(FlowInfo flowInfo, FlowContext flowContext) {
        if ((this.implicitConversion & 512) != 0) {
            return 4;
        }
        LocalVariableBinding localVariableBinding = localVariableBinding();
        if (localVariableBinding != null) {
            return flowInfo.nullStatus(localVariableBinding);
        }
        return super.nullStatus(flowInfo, flowContext);
    }

    public void manageEnclosingInstanceAccessIfNecessary(BlockScope blockScope, FlowInfo flowInfo) {
        LocalVariableBinding localVariableBinding;
        if (!((this.bits & ASTNode.DepthMASK) == 0 && (this.bits & 524288) == 0) && this.constant == Constant.NotAConstant && (this.bits & 7) == 2 && (localVariableBinding = (LocalVariableBinding) this.binding) != null && (localVariableBinding.tagBits & 256) == 0) {
            int i = localVariableBinding.useFlag;
            if (i == 1 || i == 2) {
                blockScope.emulateOuterAccess(localVariableBinding);
            }
        }
    }

    public void manageSyntheticAccessIfNecessary(BlockScope blockScope, FlowInfo flowInfo, boolean z) {
        if ((flowInfo.tagBits & 1) == 0 && this.constant == Constant.NotAConstant && (this.bits & 1) != 0) {
            FieldBinding fieldBindingOriginal = ((FieldBinding) this.binding).original();
            if ((this.bits & ASTNode.DepthMASK) != 0) {
                if (fieldBindingOriginal.isPrivate() || (fieldBindingOriginal.isProtected() && fieldBindingOriginal.declaringClass.getPackage() != blockScope.enclosingSourceType().getPackage())) {
                    if (this.syntheticAccessors == null) {
                        this.syntheticAccessors = new MethodBinding[2];
                    }
                    this.syntheticAccessors[!z ? 1 : 0] = ((SourceTypeBinding) blockScope.enclosingSourceType().enclosingTypeAt((this.bits & ASTNode.DepthMASK) >> 5)).addSyntheticMethod(fieldBindingOriginal, z, false);
                    blockScope.problemReporter().needToEmulateFieldAccess(fieldBindingOriginal, this, z);
                }
            }
        }
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
        return stringBuffer.append(this.token);
    }

    public TypeBinding reportError(BlockScope blockScope) {
        this.constant = Constant.NotAConstant;
        if (this.binding instanceof ProblemFieldBinding) {
            blockScope.problemReporter().invalidField(this, (FieldBinding) this.binding);
            return null;
        }
        if ((this.binding instanceof ProblemReferenceBinding) || (this.binding instanceof MissingTypeBinding)) {
            blockScope.problemReporter().invalidType(this, (TypeBinding) this.binding);
            return null;
        }
        blockScope.problemReporter().unresolvableReference(this, this.binding);
        return null;
    }

    /* JADX WARN: Code restructure failed: missing block: B:11:0x0033, code lost:
    
        if (r0 != 7) goto L48;
     */
    @Override // org.eclipse.jdt.internal.compiler.ast.Expression
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public org.eclipse.jdt.internal.compiler.lookup.TypeBinding resolveType(org.eclipse.jdt.internal.compiler.lookup.BlockScope r7) {
        /*
            Method dump skipped, instruction units count: 237
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: org.eclipse.jdt.internal.compiler.ast.SingleNameReference.resolveType(org.eclipse.jdt.internal.compiler.lookup.BlockScope):org.eclipse.jdt.internal.compiler.lookup.TypeBinding");
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.Expression, org.eclipse.jdt.internal.compiler.ast.ASTNode
    public void traverse(ASTVisitor aSTVisitor, BlockScope blockScope) {
        aSTVisitor.visit(this, blockScope);
        aSTVisitor.endVisit(this, blockScope);
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.Expression
    public void traverse(ASTVisitor aSTVisitor, ClassScope classScope) {
        aSTVisitor.visit(this, classScope);
        aSTVisitor.endVisit(this, classScope);
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.NameReference
    public String unboundReferenceErrorName() {
        return new String(this.token);
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.NameReference
    public char[][] getName() {
        return new char[][]{this.token};
    }
}
