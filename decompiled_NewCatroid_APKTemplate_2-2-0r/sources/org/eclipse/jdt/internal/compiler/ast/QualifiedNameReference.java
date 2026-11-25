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
import org.eclipse.jdt.internal.compiler.lookup.MethodScope;
import org.eclipse.jdt.internal.compiler.lookup.MissingTypeBinding;
import org.eclipse.jdt.internal.compiler.lookup.ProblemFieldBinding;
import org.eclipse.jdt.internal.compiler.lookup.ProblemReferenceBinding;
import org.eclipse.jdt.internal.compiler.lookup.ReferenceBinding;
import org.eclipse.jdt.internal.compiler.lookup.Scope;
import org.eclipse.jdt.internal.compiler.lookup.SourceTypeBinding;
import org.eclipse.jdt.internal.compiler.lookup.SyntheticMethodBinding;
import org.eclipse.jdt.internal.compiler.lookup.TypeBinding;
import org.eclipse.jdt.internal.compiler.lookup.VariableBinding;

/* JADX INFO: loaded from: classes14.dex */
public class QualifiedNameReference extends NameReference {
    public TypeBinding genericCast;
    public int indexOfFirstFieldBinding;
    public FieldBinding[] otherBindings;
    int[] otherDepths;
    public TypeBinding[] otherGenericCasts;
    public long[] sourcePositions;
    public SyntheticMethodBinding[] syntheticReadAccessors;
    public SyntheticMethodBinding syntheticWriteAccessor;
    public char[][] tokens;

    @Override // org.eclipse.jdt.internal.compiler.lookup.InvocationSite
    public TypeBinding[] genericTypeArguments() {
        return null;
    }

    public QualifiedNameReference(char[][] cArr, long[] jArr, int i, int i2) {
        this.tokens = cArr;
        this.sourcePositions = jArr;
        this.sourceStart = i;
        this.sourceEnd = i2;
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.Reference
    public FlowInfo analyseAssignment(BlockScope blockScope, FlowContext flowContext, FlowInfo flowInfo, Assignment assignment, boolean z) {
        FieldBinding fieldBinding;
        int i;
        FieldBinding[] fieldBindingArr = this.otherBindings;
        int i2 = 0;
        int length = fieldBindingArr == null ? 0 : fieldBindingArr.length;
        boolean z2 = length == 0 || !fieldBindingArr[0].isStatic();
        boolean z3 = blockScope.compilerOptions().complianceLevel >= ClassFileConstants.JDK1_4;
        int i3 = this.bits & 7;
        if (i3 != 1) {
            if (i3 == 2) {
                LocalVariableBinding localVariableBinding = (LocalVariableBinding) this.binding;
                if (!flowInfo.isDefinitelyAssigned(localVariableBinding)) {
                    blockScope.problemReporter().uninitializedLocalVariable(localVariableBinding, this);
                }
                if ((flowInfo.tagBits & 3) == 0) {
                    localVariableBinding.useFlag = 1;
                } else if (localVariableBinding.useFlag == 0) {
                    localVariableBinding.useFlag = 2;
                }
                if (z2) {
                    checkInternalNPE(blockScope, flowContext, flowInfo, true);
                }
            }
            fieldBinding = null;
        } else {
            fieldBinding = (FieldBinding) this.binding;
            if (z2 || z3) {
                manageSyntheticAccessIfNecessary(blockScope, fieldBinding, 0, flowInfo);
            }
            if (fieldBinding.isBlankFinal() && this.otherBindings != null && blockScope.needBlankFinalFieldInitializationCheck(fieldBinding) && !flowContext.getInitsForFinalBlankInitializationCheck(fieldBinding.declaringClass.original(), flowInfo).isDefinitelyAssigned(fieldBinding)) {
                blockScope.problemReporter().uninitializedBlankFinalField(fieldBinding, this);
            }
        }
        if (z2) {
            manageEnclosingInstanceAccessIfNecessary(blockScope, flowInfo);
        }
        if (this.otherBindings != null) {
            while (true) {
                i = length - 1;
                if (i2 >= i) {
                    break;
                }
                FieldBinding[] fieldBindingArr2 = this.otherBindings;
                FieldBinding fieldBinding2 = fieldBindingArr2[i2];
                i2++;
                if (!fieldBindingArr2[i2].isStatic() || z3) {
                    manageSyntheticAccessIfNecessary(blockScope, fieldBinding2, i2, flowInfo);
                }
            }
            fieldBinding = this.otherBindings[i];
        }
        if (z) {
            if (length == 0 && fieldBinding.isBlankFinal() && blockScope.needBlankFinalFieldInitializationCheck(fieldBinding) && !flowContext.getInitsForFinalBlankInitializationCheck(fieldBinding.declaringClass, flowInfo).isDefinitelyAssigned(fieldBinding)) {
                blockScope.problemReporter().uninitializedBlankFinalField(fieldBinding, this);
            }
            manageSyntheticAccessIfNecessary(blockScope, fieldBinding, length, flowInfo);
        }
        if (assignment.expression != null) {
            flowInfo = assignment.expression.analyseCode(blockScope, flowContext, flowInfo).unconditionalInits();
        }
        if (fieldBinding.isFinal()) {
            if (length == 0 && this.indexOfFirstFieldBinding == 1 && fieldBinding.isBlankFinal() && !z && blockScope.allowBlankFinalFieldAssignment(fieldBinding)) {
                if (flowInfo.isPotentiallyAssigned(fieldBinding)) {
                    blockScope.problemReporter().duplicateInitializationOfBlankFinalField(fieldBinding, this);
                } else {
                    flowContext.recordSettingFinal(fieldBinding, this, flowInfo);
                }
                flowInfo.markAsDefinitelyAssigned(fieldBinding);
            } else {
                blockScope.problemReporter().cannotAssignToFinalField(fieldBinding, this);
                if (length == 0 && blockScope.allowBlankFinalFieldAssignment(fieldBinding)) {
                    flowInfo.markAsDefinitelyAssigned(fieldBinding);
                }
            }
        }
        manageSyntheticAccessIfNecessary(blockScope, fieldBinding, -1, flowInfo);
        return flowInfo;
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.Reference, org.eclipse.jdt.internal.compiler.ast.Expression, org.eclipse.jdt.internal.compiler.ast.Statement
    public FlowInfo analyseCode(BlockScope blockScope, FlowContext flowContext, FlowInfo flowInfo) {
        return analyseCode(blockScope, flowContext, flowInfo, true);
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.Expression
    public FlowInfo analyseCode(BlockScope blockScope, FlowContext flowContext, FlowInfo flowInfo, boolean z) {
        boolean z2;
        boolean z3;
        FieldBinding[] fieldBindingArr = this.otherBindings;
        int length = fieldBindingArr == null ? 0 : fieldBindingArr.length;
        if (length == 0) {
            z2 = z;
        } else {
            z2 = !fieldBindingArr[0].isStatic();
        }
        boolean z4 = blockScope.compilerOptions().complianceLevel >= ClassFileConstants.JDK1_4;
        int i = this.bits & 7;
        if (i == 1) {
            if (z2 || z4) {
                manageSyntheticAccessIfNecessary(blockScope, (FieldBinding) this.binding, 0, flowInfo);
            }
            FieldBinding fieldBinding = (FieldBinding) this.binding;
            if (this.indexOfFirstFieldBinding == 1 && fieldBinding.isBlankFinal() && blockScope.needBlankFinalFieldInitializationCheck(fieldBinding) && !flowContext.getInitsForFinalBlankInitializationCheck(fieldBinding.declaringClass.original(), flowInfo).isDefinitelyAssigned(fieldBinding)) {
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
        if (z2) {
            checkInternalNPE(blockScope, flowContext, flowInfo, true);
        }
        if (z2) {
            manageEnclosingInstanceAccessIfNecessary(blockScope, flowInfo);
        }
        if (this.otherBindings != null) {
            for (int i2 = 0; i2 < length; i2++) {
                if (i2 < length - 1) {
                    z3 = !this.otherBindings[i2 + 1].isStatic();
                } else {
                    z3 = z;
                }
                if (z3 || z4) {
                    manageSyntheticAccessIfNecessary(blockScope, this.otherBindings[i2], i2 + 1, flowInfo);
                }
            }
        }
        return flowInfo;
    }

    private void checkInternalNPE(BlockScope blockScope, FlowContext flowContext, FlowInfo flowInfo, boolean z) {
        LocalVariableBinding localVariableBinding;
        if ((this.bits & 7) == 2 && (localVariableBinding = (LocalVariableBinding) this.binding) != null && (localVariableBinding.type.tagBits & 2) == 0 && (z || localVariableBinding.type.f626id != 11)) {
            if ((this.bits & 131072) == 0) {
                flowContext.recordUsingNullReference(blockScope, localVariableBinding, this, 3, flowInfo);
            }
            flowInfo.markAsComparedEqualToNonNull(localVariableBinding);
            flowContext.markFinallyNullStatus(localVariableBinding, 4);
        }
        if (this.otherBindings != null) {
            if ((this.bits & 7) == 1) {
                checkNullableFieldDereference(blockScope, (FieldBinding) this.binding, this.sourcePositions[this.indexOfFirstFieldBinding - 1], flowContext, 0);
            }
            int length = this.otherBindings.length - 1;
            for (int i = 0; i < length; i++) {
                checkNullableFieldDereference(blockScope, this.otherBindings[i], this.sourcePositions[this.indexOfFirstFieldBinding + i], flowContext, 0);
            }
        }
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.Reference, org.eclipse.jdt.internal.compiler.ast.Expression
    public boolean checkNPE(BlockScope blockScope, FlowContext flowContext, FlowInfo flowInfo, int i) {
        FieldBinding fieldBinding;
        long j;
        long j2;
        if (super.checkNPE(blockScope, flowContext, flowInfo, i)) {
            return true;
        }
        FieldBinding[] fieldBindingArr = this.otherBindings;
        if (fieldBindingArr == null) {
            if ((this.bits & 7) == 1) {
                fieldBinding = (FieldBinding) this.binding;
                j2 = this.sourcePositions[0];
            } else {
                fieldBinding = null;
                j2 = 0;
            }
            j = j2;
        } else {
            fieldBinding = fieldBindingArr[fieldBindingArr.length - 1];
            long[] jArr = this.sourcePositions;
            j = jArr[jArr.length - 1];
        }
        FieldBinding fieldBinding2 = fieldBinding;
        if (fieldBinding2 != null) {
            return checkNullableFieldDereference(blockScope, fieldBinding2, j, flowContext, i);
        }
        return false;
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.Expression
    public void computeConversion(Scope scope, TypeBinding typeBinding, TypeBinding typeBinding2) {
        FieldBinding fieldBinding;
        if (typeBinding == null || typeBinding2 == null) {
            return;
        }
        FieldBinding[] fieldBindingArr = this.otherBindings;
        int length = fieldBindingArr == null ? 0 : fieldBindingArr.length;
        if (length == 0) {
            fieldBinding = ((this.bits & 1) == 0 || this.binding == null || !this.binding.isValidBinding()) ? null : (FieldBinding) this.binding;
        } else {
            fieldBinding = fieldBindingArr[length - 1];
        }
        if (fieldBinding != null) {
            TypeBinding typeBinding3 = fieldBinding.original().type;
            if (typeBinding3.leafComponentType().isTypeVariable()) {
                TypeBinding typeBindingGenericCast = typeBinding3.genericCast((typeBinding2.isBaseType() || !typeBinding.isBaseType()) ? typeBinding : typeBinding2);
                setGenericCast(length, typeBindingGenericCast);
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
        int i = codeStream.position;
        FieldBinding fieldBindingGenerateReadSequence = generateReadSequence(blockScope, codeStream);
        codeStream.recordPositionsFrom(i, this.sourceStart);
        assignment.expression.generateCode(blockScope, codeStream, true);
        fieldStore(blockScope, codeStream, fieldBindingGenerateReadSequence, this.syntheticWriteAccessor, getFinalReceiverType(), false, z);
        if (z) {
            codeStream.generateImplicitConversion(assignment.implicitConversion);
        }
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.Expression
    public void generateCode(BlockScope blockScope, CodeStream codeStream, boolean z) {
        int i = codeStream.position;
        if (this.constant == Constant.NotAConstant) {
            FieldBinding fieldBindingGenerateReadSequence = generateReadSequence(blockScope, codeStream);
            if (fieldBindingGenerateReadSequence != null) {
                boolean zIsStatic = fieldBindingGenerateReadSequence.isStatic();
                Constant constant = fieldBindingGenerateReadSequence.constant();
                if (constant != Constant.NotAConstant) {
                    if (!zIsStatic) {
                        codeStream.invokeObjectGetClass();
                        codeStream.pop();
                    }
                    if (z) {
                        codeStream.generateConstant(constant, this.implicitConversion);
                    }
                } else {
                    boolean z2 = fieldBindingGenerateReadSequence == this.binding && (this.indexOfFirstFieldBinding == 1 || TypeBinding.equalsEquals(fieldBindingGenerateReadSequence.declaringClass, blockScope.enclosingReceiverType())) && this.otherBindings == null;
                    FieldBinding[] fieldBindingArr = this.otherBindings;
                    TypeBinding genericCast = getGenericCast(fieldBindingArr == null ? 0 : fieldBindingArr.length);
                    if (z || !((z2 || blockScope.compilerOptions().complianceLevel < ClassFileConstants.JDK1_4) && (this.implicitConversion & 1024) == 0 && genericCast == null)) {
                        int i2 = codeStream.position;
                        if (fieldBindingGenerateReadSequence.declaringClass == null) {
                            codeStream.arraylength();
                            if (z) {
                                codeStream.generateImplicitConversion(this.implicitConversion);
                            } else {
                                codeStream.pop();
                            }
                        } else {
                            SyntheticMethodBinding[] syntheticMethodBindingArr = this.syntheticReadAccessors;
                            SyntheticMethodBinding syntheticMethodBinding = syntheticMethodBindingArr == null ? null : syntheticMethodBindingArr[syntheticMethodBindingArr.length - 1];
                            if (syntheticMethodBinding == null) {
                                TypeBinding constantPoolDeclaringClass = CodeStream.getConstantPoolDeclaringClass(blockScope, fieldBindingGenerateReadSequence, getFinalReceiverType(), z2);
                                if (zIsStatic) {
                                    codeStream.fieldAccess(Opcodes.OPC_getstatic, fieldBindingGenerateReadSequence, constantPoolDeclaringClass);
                                } else {
                                    codeStream.fieldAccess(Opcodes.OPC_getfield, fieldBindingGenerateReadSequence, constantPoolDeclaringClass);
                                }
                            } else {
                                codeStream.invoke(Opcodes.OPC_invokestatic, syntheticMethodBinding, null);
                            }
                            if (genericCast != null) {
                                codeStream.checkcast(genericCast);
                            }
                            if (z) {
                                codeStream.generateImplicitConversion(this.implicitConversion);
                            } else {
                                boolean z3 = (this.implicitConversion & 1024) != 0;
                                if (z3) {
                                    codeStream.generateImplicitConversion(this.implicitConversion);
                                }
                                int i3 = (z3 ? postConversionType(blockScope) : fieldBindingGenerateReadSequence.type).f626id;
                                if (i3 == 7 || i3 == 8) {
                                    codeStream.pop2();
                                } else {
                                    codeStream.pop();
                                }
                            }
                        }
                        long[] jArr = this.sourcePositions;
                        codeStream.recordPositionsFrom(i2, (int) (jArr[jArr.length - 1] >>> 32));
                    } else if (!zIsStatic) {
                        codeStream.invokeObjectGetClass();
                        codeStream.pop();
                    }
                }
            }
        } else if (z) {
            codeStream.generateConstant(this.constant, this.implicitConversion);
        }
        codeStream.recordPositionsFrom(i, this.sourceStart);
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.Reference
    public void generateCompoundAssignment(BlockScope blockScope, CodeStream codeStream, Expression expression, int i, int i2, boolean z) {
        FieldBinding fieldBindingGenerateReadSequence = generateReadSequence(blockScope, codeStream);
        reportOnlyUselesslyReadPrivateField(blockScope, fieldBindingGenerateReadSequence, z);
        TypeBinding constantPoolDeclaringClass = CodeStream.getConstantPoolDeclaringClass(blockScope, fieldBindingGenerateReadSequence, getFinalReceiverType(), fieldBindingGenerateReadSequence == this.binding && (this.indexOfFirstFieldBinding == 1 || TypeBinding.equalsEquals(fieldBindingGenerateReadSequence.declaringClass, blockScope.enclosingReceiverType())) && this.otherBindings == null);
        SyntheticMethodBinding[] syntheticMethodBindingArr = this.syntheticReadAccessors;
        SyntheticMethodBinding syntheticMethodBinding = syntheticMethodBindingArr == null ? null : syntheticMethodBindingArr[syntheticMethodBindingArr.length - 1];
        if (!fieldBindingGenerateReadSequence.isStatic()) {
            codeStream.dup();
            if (syntheticMethodBinding == null) {
                codeStream.fieldAccess(Opcodes.OPC_getfield, fieldBindingGenerateReadSequence, constantPoolDeclaringClass);
            } else {
                codeStream.invoke(Opcodes.OPC_invokestatic, syntheticMethodBinding, null);
            }
        } else if (syntheticMethodBinding == null) {
            codeStream.fieldAccess(Opcodes.OPC_getstatic, fieldBindingGenerateReadSequence, constantPoolDeclaringClass);
        } else {
            codeStream.invoke(Opcodes.OPC_invokestatic, syntheticMethodBinding, null);
        }
        int i3 = (this.implicitConversion & 255) >> 4;
        if (i3 == 0 || i3 == 1 || i3 == 11) {
            codeStream.generateStringConcatenationAppend(blockScope, null, expression);
        } else {
            FieldBinding[] fieldBindingArr = this.otherBindings;
            TypeBinding genericCast = getGenericCast(fieldBindingArr != null ? fieldBindingArr.length : 0);
            if (genericCast != null) {
                codeStream.checkcast(genericCast);
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
        fieldStore(blockScope, codeStream, fieldBindingGenerateReadSequence, this.syntheticWriteAccessor, getFinalReceiverType(), false, z);
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.Reference
    public void generatePostIncrement(BlockScope blockScope, CodeStream codeStream, CompoundAssignment compoundAssignment, boolean z) {
        FieldBinding fieldBindingGenerateReadSequence = generateReadSequence(blockScope, codeStream);
        reportOnlyUselesslyReadPrivateField(blockScope, fieldBindingGenerateReadSequence, z);
        TypeBinding constantPoolDeclaringClass = CodeStream.getConstantPoolDeclaringClass(blockScope, fieldBindingGenerateReadSequence, getFinalReceiverType(), fieldBindingGenerateReadSequence == this.binding && (this.indexOfFirstFieldBinding == 1 || TypeBinding.equalsEquals(fieldBindingGenerateReadSequence.declaringClass, blockScope.enclosingReceiverType())) && this.otherBindings == null);
        SyntheticMethodBinding[] syntheticMethodBindingArr = this.syntheticReadAccessors;
        SyntheticMethodBinding syntheticMethodBinding = syntheticMethodBindingArr == null ? null : syntheticMethodBindingArr[syntheticMethodBindingArr.length - 1];
        if (!fieldBindingGenerateReadSequence.isStatic()) {
            codeStream.dup();
            if (syntheticMethodBinding == null) {
                codeStream.fieldAccess(Opcodes.OPC_getfield, fieldBindingGenerateReadSequence, null);
            } else {
                codeStream.invoke(Opcodes.OPC_invokestatic, syntheticMethodBinding, null);
            }
        } else if (syntheticMethodBinding == null) {
            codeStream.fieldAccess(Opcodes.OPC_getstatic, fieldBindingGenerateReadSequence, constantPoolDeclaringClass);
        } else {
            codeStream.invoke(Opcodes.OPC_invokestatic, syntheticMethodBinding, constantPoolDeclaringClass);
        }
        FieldBinding[] fieldBindingArr = this.otherBindings;
        TypeBinding genericCast = getGenericCast(fieldBindingArr != null ? fieldBindingArr.length : 0);
        if (genericCast != null) {
            codeStream.checkcast(genericCast);
        } else {
            genericCast = fieldBindingGenerateReadSequence.type;
        }
        if (z) {
            if (fieldBindingGenerateReadSequence.isStatic()) {
                int i = genericCast.f626id;
                if (i == 7 || i == 8) {
                    codeStream.dup2();
                } else {
                    codeStream.dup();
                }
            } else {
                int i2 = genericCast.f626id;
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
        fieldStore(blockScope, codeStream, fieldBindingGenerateReadSequence, this.syntheticWriteAccessor, getFinalReceiverType(), false, false);
    }

    public FieldBinding generateReadSequence(BlockScope blockScope, CodeStream codeStream) {
        FieldBinding fieldBindingOriginal;
        TypeBinding typeBinding;
        TypeBinding typeBinding2;
        TypeBinding typeBinding3;
        FieldBinding[] fieldBindingArr = this.otherBindings;
        int length = fieldBindingArr == null ? 0 : fieldBindingArr.length;
        boolean z = length == 0 || !fieldBindingArr[0].isStatic();
        boolean z2 = blockScope.compilerOptions().complianceLevel >= ClassFileConstants.JDK1_4;
        int i = this.bits & 7;
        if (i == 1) {
            fieldBindingOriginal = ((FieldBinding) this.binding).original();
            typeBinding = this.genericCast;
            typeBinding2 = this.actualReceiverType;
            if (fieldBindingOriginal.constant() == Constant.NotAConstant && ((z && !fieldBindingOriginal.isStatic()) || typeBinding != null)) {
                int i2 = codeStream.position;
                if ((this.bits & ASTNode.DepthMASK) != 0) {
                    ReferenceBinding referenceBindingEnclosingTypeAt = blockScope.enclosingSourceType().enclosingTypeAt((this.bits & ASTNode.DepthMASK) >> 5);
                    codeStream.generateOuterAccess(blockScope.getEmulationPath(referenceBindingEnclosingTypeAt, true, false), this, referenceBindingEnclosingTypeAt, blockScope);
                } else {
                    generateReceiver(codeStream);
                }
                codeStream.recordPositionsFrom(i2, this.sourceStart);
            }
        } else {
            if (i != 2) {
                return null;
            }
            LocalVariableBinding localVariableBinding = (LocalVariableBinding) this.binding;
            TypeBinding typeBinding4 = localVariableBinding.type;
            if (z) {
                Constant constant = localVariableBinding.constant();
                if (constant != Constant.NotAConstant) {
                    codeStream.generateConstant(constant, 0);
                } else if ((this.bits & 524288) != 0) {
                    checkEffectiveFinality(localVariableBinding, blockScope);
                    codeStream.generateOuterAccess(blockScope.getEmulationPath(localVariableBinding), this, localVariableBinding, blockScope);
                } else {
                    codeStream.load(localVariableBinding);
                }
            }
            fieldBindingOriginal = null;
            typeBinding2 = typeBinding4;
            typeBinding = null;
        }
        int length2 = this.sourcePositions.length;
        if (this.otherBindings == null) {
            return fieldBindingOriginal;
        }
        int i3 = 0;
        FieldBinding fieldBinding = fieldBindingOriginal;
        while (i3 < length) {
            int i4 = codeStream.position;
            FieldBinding fieldBindingOriginal2 = this.otherBindings[i3].original();
            TypeBinding[] typeBindingArr = this.otherGenericCasts;
            TypeBinding typeBinding5 = typeBindingArr == null ? null : typeBindingArr[i3];
            if (fieldBinding != null) {
                boolean zIsStatic = fieldBindingOriginal2.isStatic();
                Constant constant2 = fieldBinding.constant();
                if (constant2 != Constant.NotAConstant) {
                    if (i3 > 0 && !fieldBinding.isStatic()) {
                        codeStream.invokeObjectGetClass();
                        codeStream.pop();
                    }
                    if (!zIsStatic) {
                        codeStream.generateConstant(constant2, 0);
                    }
                } else {
                    if (!zIsStatic || ((i3 > 0 && z2) || typeBinding != null)) {
                        SyntheticMethodBinding[] syntheticMethodBindingArr = this.syntheticReadAccessors;
                        SyntheticMethodBinding syntheticMethodBinding = syntheticMethodBindingArr == null ? null : syntheticMethodBindingArr[i3];
                        if (syntheticMethodBinding == null) {
                            TypeBinding constantPoolDeclaringClass = CodeStream.getConstantPoolDeclaringClass(blockScope, fieldBinding, typeBinding2, i3 == 0 && this.indexOfFirstFieldBinding == 1);
                            if (fieldBinding.isStatic()) {
                                codeStream.fieldAccess(Opcodes.OPC_getstatic, fieldBinding, constantPoolDeclaringClass);
                            } else {
                                codeStream.fieldAccess(Opcodes.OPC_getfield, fieldBinding, constantPoolDeclaringClass);
                            }
                        } else {
                            codeStream.invoke(Opcodes.OPC_invokestatic, syntheticMethodBinding, null);
                        }
                        if (typeBinding != null) {
                            codeStream.checkcast(typeBinding);
                        } else {
                            typeBinding = fieldBinding.type;
                        }
                        if (zIsStatic) {
                            codeStream.pop();
                        }
                        typeBinding3 = typeBinding;
                    } else {
                        if (fieldBinding == fieldBindingOriginal) {
                            if (fieldBinding.isStatic() && TypeBinding.notEquals(fieldBindingOriginal.declaringClass, this.actualReceiverType.erasure())) {
                                SyntheticMethodBinding[] syntheticMethodBindingArr2 = this.syntheticReadAccessors;
                                SyntheticMethodBinding syntheticMethodBinding2 = syntheticMethodBindingArr2 == null ? null : syntheticMethodBindingArr2[i3];
                                if (syntheticMethodBinding2 == null) {
                                    codeStream.fieldAccess(Opcodes.OPC_getstatic, fieldBinding, CodeStream.getConstantPoolDeclaringClass(blockScope, fieldBinding, typeBinding2, i3 == 0 && this.indexOfFirstFieldBinding == 1));
                                } else {
                                    codeStream.invoke(Opcodes.OPC_invokestatic, syntheticMethodBinding2, null);
                                }
                                codeStream.pop();
                            }
                        } else if (!fieldBinding.isStatic()) {
                            codeStream.invokeObjectGetClass();
                            codeStream.pop();
                        }
                        typeBinding3 = fieldBinding.type;
                    }
                    int i5 = ((length2 - length) + i3) - 1;
                    if (i5 >= 0) {
                        codeStream.recordPositionsFrom(i4, (int) (this.sourcePositions[i5] >>> 32));
                    }
                    typeBinding2 = typeBinding3;
                }
            }
            i3++;
            typeBinding = typeBinding5;
            fieldBinding = fieldBindingOriginal2;
        }
        return fieldBinding;
    }

    public void generateReceiver(CodeStream codeStream) {
        codeStream.aload_0();
    }

    protected FieldBinding getCodegenBinding(int i) {
        if (i == 0) {
            return ((FieldBinding) this.binding).original();
        }
        return this.otherBindings[i - 1].original();
    }

    protected TypeBinding getFinalReceiverType() {
        FieldBinding[] fieldBindingArr = this.otherBindings;
        int length = fieldBindingArr == null ? 0 : fieldBindingArr.length;
        if (length == 0) {
            return this.actualReceiverType;
        }
        if (length == 1) {
            TypeBinding typeBinding = this.genericCast;
            return typeBinding != null ? typeBinding : ((VariableBinding) this.binding).type;
        }
        TypeBinding[] typeBindingArr = this.otherGenericCasts;
        TypeBinding typeBinding2 = typeBindingArr == null ? null : typeBindingArr[length - 2];
        return typeBinding2 != null ? typeBinding2 : fieldBindingArr[length - 2].type;
    }

    protected TypeBinding getGenericCast(int i) {
        if (i == 0) {
            return this.genericCast;
        }
        TypeBinding[] typeBindingArr = this.otherGenericCasts;
        if (typeBindingArr == null) {
            return null;
        }
        return typeBindingArr[i - 1];
    }

    public TypeBinding getOtherFieldBindings(BlockScope blockScope) {
        int length = this.tokens.length;
        FieldBinding fieldBinding = (this.bits & 1) != 0 ? (FieldBinding) this.binding : null;
        TypeBinding typeBinding = ((VariableBinding) this.binding).type;
        int i = this.indexOfFirstFieldBinding;
        if (i == length) {
            this.constant = ((FieldBinding) this.binding).constant(blockScope);
            return (typeBinding == null || (this.bits & 8192) != 0) ? typeBinding : typeBinding.capture(blockScope, this.sourceStart, this.sourceEnd);
        }
        int i2 = length - i;
        this.otherBindings = new FieldBinding[i2];
        this.otherDepths = new int[i2];
        this.constant = ((VariableBinding) this.binding).constant(blockScope);
        int i3 = (this.bits & ASTNode.DepthMASK) >> 5;
        while (i < length) {
            char[] cArr = this.tokens[i];
            if (typeBinding == null) {
                return null;
            }
            this.bits &= -8161;
            long j = this.sourcePositions[i];
            FieldBinding field = blockScope.getField(typeBinding.capture(blockScope, (int) (j >>> 32), (int) j), cArr, this);
            int i4 = i - this.indexOfFirstFieldBinding;
            this.otherBindings[i4] = field;
            this.otherDepths[i4] = (this.bits & ASTNode.DepthMASK) >> 5;
            if (field.isValidBinding()) {
                if (fieldBinding != null) {
                    TypeBinding erasureCompatibleType = typeBinding.getErasureCompatibleType(field.declaringClass);
                    FieldBinding fieldBindingOriginal = fieldBinding.original();
                    if (TypeBinding.notEquals(erasureCompatibleType, typeBinding) || fieldBindingOriginal.type.leafComponentType().isTypeVariable()) {
                        setGenericCast(i - 1, fieldBindingOriginal.type.genericCast(erasureCompatibleType));
                    }
                }
                int i5 = i + 1;
                if (isFieldUseDeprecated(field, blockScope, i5 == length ? this.bits : 0)) {
                    blockScope.problemReporter().deprecatedField(field, this);
                }
                if (this.constant != Constant.NotAConstant) {
                    this.constant = field.constant(blockScope);
                }
                if (field.isStatic()) {
                    if ((field.modifiers & 16384) != 0) {
                        ReferenceBinding referenceBinding = field.original().declaringClass;
                        MethodScope methodScope = blockScope.methodScope();
                        SourceTypeBinding sourceTypeBindingEnclosingSourceType = methodScope.enclosingSourceType();
                        if ((this.bits & 8192) == 0 && TypeBinding.equalsEquals(sourceTypeBindingEnclosingSourceType, referenceBinding) && methodScope.lastVisibleFieldID >= 0 && field.f627id >= methodScope.lastVisibleFieldID && (!field.isStatic() || methodScope.isStatic)) {
                            blockScope.problemReporter().forwardReference(this, i, field);
                        }
                        if ((TypeBinding.equalsEquals(sourceTypeBindingEnclosingSourceType, referenceBinding) || TypeBinding.equalsEquals(sourceTypeBindingEnclosingSourceType.superclass, referenceBinding)) && field.constant(blockScope) == Constant.NotAConstant && !methodScope.isStatic && methodScope.isInsideInitializerOrConstructor()) {
                            blockScope.problemReporter().enumStaticFieldUsedDuringInitialization(field, this);
                        }
                    }
                    blockScope.problemReporter().nonStaticAccessToStaticField(this, field, i);
                    if (TypeBinding.notEquals(field.declaringClass, typeBinding)) {
                        blockScope.problemReporter().indirectAccessToStaticField(this, field);
                    }
                }
                typeBinding = field.type;
                i = i5;
                fieldBinding = field;
            } else {
                this.constant = Constant.NotAConstant;
                blockScope.problemReporter().invalidField(this, field, i, typeBinding);
                setDepth(i3);
                return null;
            }
        }
        setDepth(i3);
        TypeBinding typeBinding2 = this.otherBindings[i2 - 1].type;
        return (typeBinding2 == null || (this.bits & 8192) != 0) ? typeBinding2 : typeBinding2.capture(blockScope, this.sourceStart, this.sourceEnd);
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.Reference
    public boolean isEquivalent(Reference reference) {
        int length;
        if (reference instanceof FieldReference) {
            return reference.isEquivalent(this);
        }
        if (!(reference instanceof QualifiedNameReference)) {
            return false;
        }
        QualifiedNameReference qualifiedNameReference = (QualifiedNameReference) reference;
        if (this.tokens.length != qualifiedNameReference.tokens.length || this.binding != qualifiedNameReference.binding) {
            return false;
        }
        FieldBinding[] fieldBindingArr = this.otherBindings;
        if (fieldBindingArr == null) {
            return qualifiedNameReference.otherBindings == null;
        }
        FieldBinding[] fieldBindingArr2 = qualifiedNameReference.otherBindings;
        if (fieldBindingArr2 == null || (length = fieldBindingArr.length) != fieldBindingArr2.length) {
            return false;
        }
        for (int i = 0; i < length; i++) {
            if (this.otherBindings[i] != qualifiedNameReference.otherBindings[i]) {
                return false;
            }
        }
        return true;
    }

    public boolean isFieldAccess() {
        return this.otherBindings != null || (this.bits & 7) == 1;
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.NameReference, org.eclipse.jdt.internal.compiler.ast.Reference
    public FieldBinding lastFieldBinding() {
        FieldBinding[] fieldBindingArr = this.otherBindings;
        if (fieldBindingArr != null) {
            return fieldBindingArr[fieldBindingArr.length - 1];
        }
        if (this.binding == null || (this.bits & 7) != 1) {
            return null;
        }
        return (FieldBinding) this.binding;
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

    public void manageSyntheticAccessIfNecessary(BlockScope blockScope, FieldBinding fieldBinding, int i, FlowInfo flowInfo) {
        int i2;
        int length;
        int length2;
        if ((flowInfo.tagBits & 1) == 0 && fieldBinding.constant(blockScope) == Constant.NotAConstant) {
            if (fieldBinding.isPrivate()) {
                if (i < 0) {
                    FieldBinding[] fieldBindingArr = this.otherBindings;
                    length2 = fieldBindingArr == null ? 0 : fieldBindingArr.length;
                } else {
                    length2 = i;
                }
                FieldBinding codegenBinding = getCodegenBinding(length2);
                ReferenceBinding referenceBinding = codegenBinding.declaringClass;
                if (TypeBinding.notEquals(referenceBinding, blockScope.enclosingSourceType())) {
                    setSyntheticAccessor(fieldBinding, i, ((SourceTypeBinding) referenceBinding).addSyntheticMethod(codegenBinding, i >= 0, false));
                    blockScope.problemReporter().needToEmulateFieldAccess(codegenBinding, this, i >= 0);
                    return;
                }
                return;
            }
            if (fieldBinding.isProtected()) {
                if (i == 0 || (i < 0 && this.otherDepths == null)) {
                    i2 = (this.bits & ASTNode.DepthMASK) >> 5;
                } else {
                    int[] iArr = this.otherDepths;
                    i2 = iArr[i < 0 ? iArr.length - 1 : i - 1];
                }
                if (i2 <= 0 || fieldBinding.declaringClass.getPackage() == blockScope.enclosingSourceType().getPackage()) {
                    return;
                }
                if (i < 0) {
                    FieldBinding[] fieldBindingArr2 = this.otherBindings;
                    length = fieldBindingArr2 == null ? 0 : fieldBindingArr2.length;
                } else {
                    length = i;
                }
                FieldBinding codegenBinding2 = getCodegenBinding(length);
                setSyntheticAccessor(fieldBinding, i, ((SourceTypeBinding) blockScope.enclosingSourceType().enclosingTypeAt(i2)).addSyntheticMethod(codegenBinding2, i >= 0, false));
                blockScope.problemReporter().needToEmulateFieldAccess(codegenBinding2, this, i >= 0);
            }
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:14:0x001d, code lost:
    
        if (r0 != 2) goto L15;
     */
    @Override // org.eclipse.jdt.internal.compiler.ast.Expression
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public org.eclipse.jdt.internal.compiler.impl.Constant optimizedBooleanConstant() {
        /*
            r3 = this;
            org.eclipse.jdt.internal.compiler.lookup.TypeBinding r0 = r3.resolvedType
            int r0 = r0.f626id
            r1 = 5
            if (r0 == r1) goto Lc
            r1 = 33
            if (r0 == r1) goto Lc
            goto L1f
        Lc:
            org.eclipse.jdt.internal.compiler.impl.Constant r0 = r3.constant
            org.eclipse.jdt.internal.compiler.impl.Constant r1 = org.eclipse.jdt.internal.compiler.impl.Constant.NotAConstant
            if (r0 == r1) goto L15
            org.eclipse.jdt.internal.compiler.impl.Constant r0 = r3.constant
            return r0
        L15:
            int r0 = r3.bits
            r0 = r0 & 7
            r1 = 1
            if (r0 == r1) goto L22
            r2 = 2
            if (r0 == r2) goto L2f
        L1f:
            org.eclipse.jdt.internal.compiler.impl.Constant r0 = org.eclipse.jdt.internal.compiler.impl.Constant.NotAConstant
            return r0
        L22:
            org.eclipse.jdt.internal.compiler.lookup.FieldBinding[] r0 = r3.otherBindings
            if (r0 != 0) goto L2f
            org.eclipse.jdt.internal.compiler.lookup.Binding r0 = r3.binding
            org.eclipse.jdt.internal.compiler.lookup.FieldBinding r0 = (org.eclipse.jdt.internal.compiler.lookup.FieldBinding) r0
            org.eclipse.jdt.internal.compiler.impl.Constant r0 = r0.constant()
            return r0
        L2f:
            org.eclipse.jdt.internal.compiler.lookup.FieldBinding[] r0 = r3.otherBindings
            int r2 = r0.length
            int r2 = r2 - r1
            r0 = r0[r2]
            org.eclipse.jdt.internal.compiler.impl.Constant r0 = r0.constant()
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: org.eclipse.jdt.internal.compiler.ast.QualifiedNameReference.optimizedBooleanConstant():org.eclipse.jdt.internal.compiler.impl.Constant");
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.Expression
    public TypeBinding postConversionType(Scope scope) {
        TypeBinding typeBinding = this.resolvedType;
        FieldBinding[] fieldBindingArr = this.otherBindings;
        TypeBinding genericCast = getGenericCast(fieldBindingArr == null ? 0 : fieldBindingArr.length);
        if (genericCast != null) {
            typeBinding = genericCast;
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
        for (int i2 = 0; i2 < this.tokens.length; i2++) {
            if (i2 > 0) {
                stringBuffer.append('.');
            }
            stringBuffer.append(this.tokens[i2]);
        }
        return stringBuffer;
    }

    public TypeBinding reportError(BlockScope blockScope) {
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

    /* JADX WARN: Code restructure failed: missing block: B:7:0x0027, code lost:
    
        if (r0 != 7) goto L113;
     */
    @Override // org.eclipse.jdt.internal.compiler.ast.Expression
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public org.eclipse.jdt.internal.compiler.lookup.TypeBinding resolveType(org.eclipse.jdt.internal.compiler.lookup.BlockScope r14) {
        /*
            Method dump skipped, instruction units count: 535
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: org.eclipse.jdt.internal.compiler.ast.QualifiedNameReference.resolveType(org.eclipse.jdt.internal.compiler.lookup.BlockScope):org.eclipse.jdt.internal.compiler.lookup.TypeBinding");
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.NameReference, org.eclipse.jdt.internal.compiler.lookup.InvocationSite
    public void setFieldIndex(int i) {
        this.indexOfFirstFieldBinding = i;
    }

    protected void setGenericCast(int i, TypeBinding typeBinding) {
        if (typeBinding == null) {
            return;
        }
        if (i == 0) {
            this.genericCast = typeBinding;
            return;
        }
        if (this.otherGenericCasts == null) {
            this.otherGenericCasts = new TypeBinding[this.otherBindings.length];
        }
        this.otherGenericCasts[i - 1] = typeBinding;
    }

    protected void setSyntheticAccessor(FieldBinding fieldBinding, int i, SyntheticMethodBinding syntheticMethodBinding) {
        if (i < 0) {
            this.syntheticWriteAccessor = syntheticMethodBinding;
            return;
        }
        if (this.syntheticReadAccessors == null) {
            FieldBinding[] fieldBindingArr = this.otherBindings;
            this.syntheticReadAccessors = new SyntheticMethodBinding[fieldBindingArr != null ? 1 + fieldBindingArr.length : 1];
        }
        this.syntheticReadAccessors[i] = syntheticMethodBinding;
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
        return new String(this.tokens[0]);
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.NameReference
    public char[][] getName() {
        return this.tokens;
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.Expression
    public VariableBinding nullAnnotatedVariableBinding(boolean z) {
        FieldBinding fieldBinding;
        if (this.binding == null || !isFieldAccess()) {
            return null;
        }
        FieldBinding[] fieldBindingArr = this.otherBindings;
        if (fieldBindingArr == null) {
            fieldBinding = (FieldBinding) this.binding;
        } else {
            fieldBinding = fieldBindingArr[fieldBindingArr.length - 1];
        }
        if (z || fieldBinding.isNullable() || fieldBinding.isNonNull()) {
            return fieldBinding;
        }
        return null;
    }
}
