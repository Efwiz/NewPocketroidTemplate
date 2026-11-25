package org.eclipse.jdt.internal.compiler.ast;

import java.util.List;
import org.eclipse.jdt.internal.compiler.ASTVisitor;
import org.eclipse.jdt.internal.compiler.codegen.CodeStream;
import org.eclipse.jdt.internal.compiler.flow.FlowContext;
import org.eclipse.jdt.internal.compiler.flow.FlowInfo;
import org.eclipse.jdt.internal.compiler.flow.UnconditionalFlowInfo;
import org.eclipse.jdt.internal.compiler.impl.Constant;
import org.eclipse.jdt.internal.compiler.lookup.ArrayBinding;
import org.eclipse.jdt.internal.compiler.lookup.Binding;
import org.eclipse.jdt.internal.compiler.lookup.BlockScope;
import org.eclipse.jdt.internal.compiler.lookup.InvocationSite;
import org.eclipse.jdt.internal.compiler.lookup.LocalVariableBinding;
import org.eclipse.jdt.internal.compiler.lookup.TypeBinding;
import org.eclipse.jdt.internal.compiler.parser.RecoveryScanner;

/* JADX INFO: loaded from: classes14.dex */
public class LocalDeclaration extends AbstractVariableDeclaration {
    public LocalVariableBinding binding;

    @Override // org.eclipse.jdt.internal.compiler.ast.AbstractVariableDeclaration
    public int getKind() {
        return 4;
    }

    public boolean isArgument() {
        return false;
    }

    public boolean isReceiver() {
        return false;
    }

    public LocalDeclaration(char[] cArr, int i, int i2) {
        this.f617name = cArr;
        this.sourceStart = i;
        this.sourceEnd = i2;
        this.declarationEnd = i2;
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.AbstractVariableDeclaration, org.eclipse.jdt.internal.compiler.ast.Statement
    public FlowInfo analyseCode(BlockScope blockScope, FlowContext flowContext, FlowInfo flowInfo) {
        UnconditionalFlowInfo unconditionalFlowInfoUnconditionalCopy;
        if ((flowInfo.tagBits & 1) == 0) {
            this.bits |= 1073741824;
        }
        if (this.initialization == null) {
            return flowInfo;
        }
        this.initialization.checkNPEbyUnboxing(blockScope, flowContext, flowInfo);
        boolean z = this.binding != null && flowInfo.reachMode() == 0 && blockScope.compilerOptions().analyseResourceLeaks && FakedTrackingVariable.isAnyCloseable(this.initialization.resolvedType);
        if (z) {
            unconditionalFlowInfoUnconditionalCopy = flowInfo.unconditionalCopy();
            FakedTrackingVariable.preConnectTrackerAcrossAssignment(this, this.binding, this.initialization, flowInfo);
        } else {
            unconditionalFlowInfoUnconditionalCopy = null;
        }
        UnconditionalFlowInfo unconditionalFlowInfo = unconditionalFlowInfoUnconditionalCopy;
        UnconditionalFlowInfo unconditionalFlowInfoUnconditionalInits = this.initialization.analyseCode(blockScope, flowContext, flowInfo).unconditionalInits();
        if (z) {
            FakedTrackingVariable.handleResourceAssignment(blockScope, unconditionalFlowInfo, unconditionalFlowInfoUnconditionalInits, flowContext, this, this.initialization, this.binding);
        } else {
            FakedTrackingVariable.cleanUpAfterAssignment(blockScope, 2, this.initialization);
        }
        int iNullStatus = this.initialization.nullStatus(unconditionalFlowInfoUnconditionalInits, flowContext);
        if (!unconditionalFlowInfoUnconditionalInits.isDefinitelyAssigned(this.binding)) {
            this.bits |= 8;
        } else {
            this.bits &= -9;
        }
        unconditionalFlowInfoUnconditionalInits.markAsDefinitelyAssigned(this.binding);
        if (blockScope.compilerOptions().isAnnotationBasedNullAnalysisEnabled) {
            iNullStatus = NullAnnotationMatching.checkAssignment(blockScope, flowContext, this.binding, unconditionalFlowInfoUnconditionalInits, iNullStatus, this.initialization, this.initialization.resolvedType);
        }
        if ((this.binding.type.tagBits & 2) == 0) {
            unconditionalFlowInfoUnconditionalInits.markNullStatus(this.binding, iNullStatus);
        }
        return unconditionalFlowInfoUnconditionalInits;
    }

    public void checkModifiers() {
        if ((this.modifiers & 65519) != 0) {
            this.modifiers = (this.modifiers & (-4194305)) | 8388608;
        }
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.Statement
    public void generateCode(BlockScope blockScope, CodeStream codeStream) {
        if (this.binding.resolvedPosition != -1) {
            codeStream.addVisibleLocalVariable(this.binding);
        }
        if ((this.bits & Integer.MIN_VALUE) == 0) {
            return;
        }
        int i = codeStream.position;
        if (this.initialization != null) {
            if (this.binding.resolvedPosition < 0) {
                if (this.initialization.constant == Constant.NotAConstant) {
                    this.initialization.generateCode(blockScope, codeStream, false);
                }
            } else {
                this.initialization.generateCode(blockScope, codeStream, true);
                if (this.binding.type.isArrayType() && (this.initialization instanceof CastExpression) && ((CastExpression) this.initialization).innermostCastedExpression().resolvedType == TypeBinding.NULL) {
                    codeStream.checkcast(this.binding.type);
                }
                codeStream.store(this.binding, false);
                if ((this.bits & 8) != 0) {
                    this.binding.recordInitializationStartPC(codeStream.position);
                }
            }
        }
        codeStream.recordPositionsFrom(i, this.sourceStart);
    }

    public void getAllAnnotationContexts(int i, LocalVariableBinding localVariableBinding, List list) {
        traverseWithoutInitializer(new TypeReference.AnnotationCollector(this, i, localVariableBinding, list), null);
    }

    public void getAllAnnotationContexts(int i, int i2, List list) {
        traverse(new TypeReference.AnnotationCollector(this, i, i2, list), null);
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.AbstractVariableDeclaration, org.eclipse.jdt.internal.compiler.ast.Statement
    public void resolve(BlockScope blockScope) {
        Constant constantCastTo;
        TypeBinding typeBindingResolveType = this.type.resolveType(blockScope, true);
        this.bits |= this.type.bits & 1048576;
        checkModifiers();
        if (typeBindingResolveType != null) {
            if (typeBindingResolveType == TypeBinding.VOID) {
                blockScope.problemReporter().variableTypeCannotBeVoid(this);
                return;
            } else if (typeBindingResolveType.isArrayType() && ((ArrayBinding) typeBindingResolveType).leafComponentType == TypeBinding.VOID) {
                blockScope.problemReporter().variableTypeCannotBeVoidArray(this);
                return;
            }
        }
        Binding binding = blockScope.getBinding(this.f617name, 3, (InvocationSite) this, false);
        if (binding != null && binding.isValidBinding()) {
            boolean z = binding instanceof LocalVariableBinding;
            if (z && (this.bits & 2097152) != 0 && blockScope.isLambdaSubscope() && this.hiddenVariableDepth == 0) {
                blockScope.problemReporter().lambdaRedeclaresLocal(this);
            } else if (z && this.hiddenVariableDepth == 0) {
                blockScope.problemReporter().redefineLocal(this);
            } else {
                blockScope.problemReporter().localVariableHiding(this, binding, false);
            }
        }
        if ((this.modifiers & 16) != 0 && this.initialization == null) {
            this.modifiers |= 67108864;
        }
        LocalVariableBinding localVariableBinding = new LocalVariableBinding(this, typeBindingResolveType, this.modifiers, false);
        this.binding = localVariableBinding;
        blockScope.addLocalVariable(localVariableBinding);
        this.binding.setConstant(Constant.NotAConstant);
        if (typeBindingResolveType == null) {
            if (this.initialization != null) {
                this.initialization.resolveType(blockScope);
                return;
            }
            return;
        }
        if (this.initialization != null) {
            if (this.initialization instanceof ArrayInitializer) {
                TypeBinding typeBindingResolveTypeExpecting = this.initialization.resolveTypeExpecting(blockScope, typeBindingResolveType);
                if (typeBindingResolveTypeExpecting != null) {
                    ((ArrayInitializer) this.initialization).binding = (ArrayBinding) typeBindingResolveTypeExpecting;
                    this.initialization.computeConversion(blockScope, typeBindingResolveType, typeBindingResolveTypeExpecting);
                }
            } else {
                this.initialization.setExpressionContext(ExpressionContext.ASSIGNMENT_CONTEXT);
                this.initialization.setExpectedType(typeBindingResolveType);
                TypeBinding typeBindingResolveType2 = this.initialization.resolveType(blockScope);
                if (typeBindingResolveType2 != null) {
                    if (TypeBinding.notEquals(typeBindingResolveType, typeBindingResolveType2)) {
                        blockScope.compilationUnitScope().recordTypeConversion(typeBindingResolveType, typeBindingResolveType2);
                    }
                    if (this.initialization.isConstantValueOfTypeAssignableToType(typeBindingResolveType2, typeBindingResolveType) || typeBindingResolveType2.isCompatibleWith(typeBindingResolveType, blockScope)) {
                        this.initialization.computeConversion(blockScope, typeBindingResolveType, typeBindingResolveType2);
                        if (typeBindingResolveType2.needsUncheckedConversion(typeBindingResolveType)) {
                            blockScope.problemReporter().unsafeTypeConversion(this.initialization, typeBindingResolveType2, typeBindingResolveType);
                        }
                        if ((this.initialization instanceof CastExpression) && (this.initialization.bits & 16384) == 0) {
                            CastExpression.checkNeedForAssignedCast(blockScope, typeBindingResolveType, (CastExpression) this.initialization);
                        }
                    } else if (isBoxingCompatible(typeBindingResolveType2, typeBindingResolveType, this.initialization, blockScope)) {
                        this.initialization.computeConversion(blockScope, typeBindingResolveType, typeBindingResolveType2);
                        if ((this.initialization instanceof CastExpression) && (this.initialization.bits & 16384) == 0) {
                            CastExpression.checkNeedForAssignedCast(blockScope, typeBindingResolveType, (CastExpression) this.initialization);
                        }
                    } else if ((typeBindingResolveType.tagBits & 128) == 0) {
                        blockScope.problemReporter().typeMismatchError(typeBindingResolveType2, typeBindingResolveType, this.initialization, (ASTNode) null);
                    }
                }
            }
            if (this.binding == Expression.getDirectBinding(this.initialization)) {
                blockScope.problemReporter().assignmentHasNoEffect(this, this.f617name);
            }
            LocalVariableBinding localVariableBinding2 = this.binding;
            if (localVariableBinding2.isFinal()) {
                constantCastTo = this.initialization.constant.castTo((typeBindingResolveType.f626id << 4) + this.initialization.constant.typeID());
            } else {
                constantCastTo = Constant.NotAConstant;
            }
            localVariableBinding2.setConstant(constantCastTo);
        }
        resolveAnnotations(blockScope, this.annotations, this.binding, true);
        Annotation.isTypeUseCompatible(this.type, blockScope, this.annotations);
        if (blockScope.validateNullAnnotation(this.binding.tagBits, this.type, this.annotations)) {
            return;
        }
        this.binding.tagBits &= -108086391056891905L;
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.ASTNode
    public void traverse(ASTVisitor aSTVisitor, BlockScope blockScope) {
        if (aSTVisitor.visit(this, blockScope)) {
            if (this.annotations != null) {
                int length = this.annotations.length;
                for (int i = 0; i < length; i++) {
                    this.annotations[i].traverse(aSTVisitor, blockScope);
                }
            }
            this.type.traverse(aSTVisitor, blockScope);
            if (this.initialization != null) {
                this.initialization.traverse(aSTVisitor, blockScope);
            }
        }
        aSTVisitor.endVisit(this, blockScope);
    }

    private void traverseWithoutInitializer(ASTVisitor aSTVisitor, BlockScope blockScope) {
        if (aSTVisitor.visit(this, blockScope)) {
            if (this.annotations != null) {
                int length = this.annotations.length;
                for (int i = 0; i < length; i++) {
                    this.annotations[i].traverse(aSTVisitor, blockScope);
                }
            }
            this.type.traverse(aSTVisitor, blockScope);
        }
        aSTVisitor.endVisit(this, blockScope);
    }

    public boolean isRecoveredFromLoneIdentifier() {
        if (this.f617name == RecoveryScanner.FAKE_IDENTIFIER) {
            return ((this.type instanceof SingleTypeReference) || ((this.type instanceof QualifiedTypeReference) && !(this.type instanceof ArrayQualifiedTypeReference))) && this.initialization == null && !this.type.isBaseTypeReference();
        }
        return false;
    }
}
