package org.eclipse.jdt.internal.compiler.ast;

import java.util.List;
import org.eclipse.jdt.core.compiler.IProblem;
import org.eclipse.jdt.internal.compiler.ASTVisitor;
import org.eclipse.jdt.internal.compiler.classfmt.ClassFileConstants;
import org.eclipse.jdt.internal.compiler.codegen.CodeStream;
import org.eclipse.jdt.internal.compiler.codegen.Opcodes;
import org.eclipse.jdt.internal.compiler.flow.FlowContext;
import org.eclipse.jdt.internal.compiler.flow.FlowInfo;
import org.eclipse.jdt.internal.compiler.impl.CompilerOptions;
import org.eclipse.jdt.internal.compiler.impl.Constant;
import org.eclipse.jdt.internal.compiler.lookup.ArrayBinding;
import org.eclipse.jdt.internal.compiler.lookup.Binding;
import org.eclipse.jdt.internal.compiler.lookup.BlockScope;
import org.eclipse.jdt.internal.compiler.lookup.ClassScope;
import org.eclipse.jdt.internal.compiler.lookup.FieldBinding;
import org.eclipse.jdt.internal.compiler.lookup.MethodScope;
import org.eclipse.jdt.internal.compiler.lookup.Scope;
import org.eclipse.jdt.internal.compiler.lookup.SourceTypeBinding;
import org.eclipse.jdt.internal.compiler.lookup.TypeBinding;
import org.eclipse.jdt.internal.compiler.problem.ProblemReporter;
import org.eclipse.jdt.internal.compiler.util.Util;

/* JADX INFO: loaded from: classes14.dex */
public class FieldDeclaration extends AbstractVariableDeclaration {
    public FieldBinding binding;
    public int endPart1Position;
    public int endPart2Position;
    public Javadoc javadoc;

    public FieldDeclaration() {
    }

    public FieldDeclaration(char[] cArr, int i, int i2) {
        this.f617name = cArr;
        this.sourceStart = i;
        this.sourceEnd = i2;
    }

    public FlowInfo analyseCode(MethodScope methodScope, FlowContext flowContext, FlowInfo flowInfo) {
        FieldBinding fieldBinding = this.binding;
        if (fieldBinding != null && !fieldBinding.isUsed() && this.binding.isOrEnclosedByPrivateType() && !methodScope.referenceCompilationUnit().compilationResult.hasSyntaxError) {
            methodScope.problemReporter().unusedPrivateField(this);
        }
        FieldBinding fieldBinding2 = this.binding;
        if (fieldBinding2 != null && fieldBinding2.isValidBinding() && this.binding.isStatic() && this.binding.constant(methodScope) == Constant.NotAConstant && this.binding.declaringClass.isNestedType() && !this.binding.declaringClass.isStatic()) {
            methodScope.problemReporter().unexpectedStaticModifierForField((SourceTypeBinding) this.binding.declaringClass, this);
        }
        if (this.initialization != null) {
            flowInfo = this.initialization.analyseCode(methodScope, flowContext, flowInfo).unconditionalInits();
            flowInfo.markAsDefinitelyAssigned(this.binding);
        }
        if (this.initialization != null && this.binding != null) {
            CompilerOptions compilerOptions = methodScope.compilerOptions();
            if (compilerOptions.isAnnotationBasedNullAnalysisEnabled && (this.binding.isNonNull() || compilerOptions.sourceLevel >= ClassFileConstants.JDK1_8)) {
                FlowInfo flowInfo2 = flowInfo;
                NullAnnotationMatching.checkAssignment(methodScope, flowContext, this.binding, flowInfo2, this.initialization.nullStatus(flowInfo, flowContext), this.initialization, this.initialization.resolvedType);
            }
            this.initialization.checkNPEbyUnboxing(methodScope, flowContext, flowInfo);
        }
        return flowInfo;
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.Statement
    public void generateCode(BlockScope blockScope, CodeStream codeStream) {
        boolean zIsStatic;
        if ((this.bits & Integer.MIN_VALUE) == 0) {
            return;
        }
        int i = codeStream.position;
        if (this.initialization != null && (!(zIsStatic = this.binding.isStatic()) || this.binding.constant() == Constant.NotAConstant)) {
            if (!zIsStatic) {
                codeStream.aload_0();
            }
            this.initialization.generateCode(blockScope, codeStream, true);
            if (zIsStatic) {
                codeStream.fieldAccess(Opcodes.OPC_putstatic, this.binding, null);
            } else {
                codeStream.fieldAccess(Opcodes.OPC_putfield, this.binding, null);
            }
        }
        codeStream.recordPositionsFrom(i, this.sourceStart);
    }

    public void getAllAnnotationContexts(int i, List list) {
        TypeReference.AnnotationCollector annotationCollector = new TypeReference.AnnotationCollector(this.type, i, list);
        int length = this.annotations.length;
        for (int i2 = 0; i2 < length; i2++) {
            this.annotations[i2].traverse(annotationCollector, (BlockScope) null);
        }
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.AbstractVariableDeclaration
    public int getKind() {
        return this.type == null ? 3 : 1;
    }

    public boolean isStatic() {
        FieldBinding fieldBinding = this.binding;
        if (fieldBinding != null) {
            return fieldBinding.isStatic();
        }
        return (this.modifiers & 8) != 0;
    }

    public boolean isFinal() {
        FieldBinding fieldBinding = this.binding;
        if (fieldBinding != null) {
            return fieldBinding.isFinal();
        }
        return (this.modifiers & 16) != 0;
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.AbstractVariableDeclaration, org.eclipse.jdt.internal.compiler.ast.Statement
    public StringBuffer printStatement(int i, StringBuffer stringBuffer) {
        Javadoc javadoc = this.javadoc;
        if (javadoc != null) {
            javadoc.print(i, stringBuffer);
        }
        return super.printStatement(i, stringBuffer);
    }

    /* JADX WARN: Code duplicated, block: B:48:0x0089  */
    public void resolve(MethodScope methodScope) {
        FieldBinding fieldBinding;
        Binding binding;
        FieldBinding fieldBindingFindField;
        if ((this.bits & 16) == 0 && (fieldBinding = this.binding) != null && fieldBinding.isValidBinding()) {
            this.bits |= 16;
            ClassScope classScopeEnclosingClassScope = methodScope.enclosingClassScope();
            if (classScopeEnclosingClassScope != null) {
                SourceTypeBinding sourceTypeBindingEnclosingSourceType = classScopeEnclosingClassScope.enclosingSourceType();
                if (sourceTypeBindingEnclosingSourceType.superclass != null && (fieldBindingFindField = classScopeEnclosingClassScope.findField(sourceTypeBindingEnclosingSourceType.superclass, this.f617name, this, false, true)) != null && fieldBindingFindField.isValidBinding() && fieldBindingFindField.original() != this.binding && fieldBindingFindField.canBeSeenBy(sourceTypeBindingEnclosingSourceType, this, methodScope)) {
                    methodScope.problemReporter().fieldHiding(this, fieldBindingFindField);
                } else {
                    Scope scope = classScopeEnclosingClassScope.parent;
                    if (scope.kind != 4 && (binding = scope.getBinding(this.f617name, 3, this, false)) != null && binding.isValidBinding() && binding != this.binding) {
                        if (binding instanceof FieldBinding) {
                            FieldBinding fieldBinding2 = (FieldBinding) binding;
                            if (fieldBinding2.original() != this.binding && (fieldBinding2.isStatic() || !sourceTypeBindingEnclosingSourceType.isStatic())) {
                                methodScope.problemReporter().fieldHiding(this, binding);
                            }
                        } else {
                            methodScope.problemReporter().fieldHiding(this, binding);
                        }
                    }
                }
            }
            if (this.type != null) {
                this.type.resolvedType = this.binding.type;
            }
            FieldBinding fieldBinding3 = methodScope.initializedField;
            int i = methodScope.lastVisibleFieldID;
            try {
                methodScope.initializedField = this.binding;
                methodScope.lastVisibleFieldID = this.binding.f627id;
                resolveAnnotations(methodScope, this.annotations, this.binding);
                if (this.annotations != null) {
                    int length = this.annotations.length;
                    for (int i2 = 0; i2 < length; i2++) {
                        TypeBinding typeBinding = this.annotations[i2].resolvedType;
                        if (typeBinding != null && (typeBinding.getAnnotationTagBits() & 9007199254740992L) != 0) {
                            this.bits |= 1048576;
                            break;
                        }
                    }
                }
                if ((this.binding.getAnnotationTagBits() & 70368744177664L) == 0 && (this.binding.modifiers & 1048576) != 0 && methodScope.compilerOptions().sourceLevel >= ClassFileConstants.JDK1_5) {
                    methodScope.problemReporter().missingDeprecatedAnnotationForField(this);
                }
                if (this.initialization == null) {
                    this.binding.setConstant(Constant.NotAConstant);
                } else {
                    this.binding.setConstant(Constant.NotAConstant);
                    TypeBinding typeBinding2 = this.binding.type;
                    this.initialization.setExpressionContext(ExpressionContext.ASSIGNMENT_CONTEXT);
                    this.initialization.setExpectedType(typeBinding2);
                    if (this.initialization instanceof ArrayInitializer) {
                        TypeBinding typeBindingResolveTypeExpecting = this.initialization.resolveTypeExpecting(methodScope, typeBinding2);
                        if (typeBindingResolveTypeExpecting != null) {
                            ((ArrayInitializer) this.initialization).binding = (ArrayBinding) typeBindingResolveTypeExpecting;
                            this.initialization.computeConversion(methodScope, typeBinding2, typeBindingResolveTypeExpecting);
                        }
                    } else {
                        TypeBinding typeBindingResolveType = this.initialization.resolveType(methodScope);
                        if (typeBindingResolveType != null) {
                            if (TypeBinding.notEquals(typeBinding2, typeBindingResolveType)) {
                                methodScope.compilationUnitScope().recordTypeConversion(typeBinding2, typeBindingResolveType);
                            }
                            if (this.initialization.isConstantValueOfTypeAssignableToType(typeBindingResolveType, typeBinding2) || typeBindingResolveType.isCompatibleWith(typeBinding2, classScopeEnclosingClassScope)) {
                                this.initialization.computeConversion(methodScope, typeBinding2, typeBindingResolveType);
                                if (typeBindingResolveType.needsUncheckedConversion(typeBinding2)) {
                                    methodScope.problemReporter().unsafeTypeConversion(this.initialization, typeBindingResolveType, typeBinding2);
                                }
                                if ((this.initialization instanceof CastExpression) && (this.initialization.bits & 16384) == 0) {
                                    CastExpression.checkNeedForAssignedCast(methodScope, typeBinding2, (CastExpression) this.initialization);
                                }
                            } else if (isBoxingCompatible(typeBindingResolveType, typeBinding2, this.initialization, methodScope)) {
                                this.initialization.computeConversion(methodScope, typeBinding2, typeBindingResolveType);
                                if ((this.initialization instanceof CastExpression) && (this.initialization.bits & 16384) == 0) {
                                    CastExpression.checkNeedForAssignedCast(methodScope, typeBinding2, (CastExpression) this.initialization);
                                }
                            } else if ((typeBinding2.tagBits & 128) == 0) {
                                methodScope.problemReporter().typeMismatchError(typeBindingResolveType, typeBinding2, this.initialization, (ASTNode) null);
                            }
                            if (this.binding.isFinal()) {
                                this.binding.setConstant(this.initialization.constant.castTo((this.binding.type.f626id << 4) + this.initialization.constant.typeID()));
                            }
                        } else {
                            this.binding.setConstant(Constant.NotAConstant);
                        }
                    }
                    if (this.binding == Expression.getDirectBinding(this.initialization)) {
                        methodScope.problemReporter().assignmentHasNoEffect(this, this.f617name);
                    }
                }
                Javadoc javadoc = this.javadoc;
                if (javadoc != null) {
                    javadoc.resolve(methodScope);
                } else {
                    FieldBinding fieldBinding4 = this.binding;
                    if (fieldBinding4 != null && fieldBinding4.declaringClass != null && !this.binding.declaringClass.isLocalType()) {
                        int iComputeOuterMostVisibility = this.binding.modifiers & 7;
                        ProblemReporter problemReporter = methodScope.problemReporter();
                        int iComputeSeverity = problemReporter.computeSeverity(IProblem.JavadocMissing);
                        if (iComputeSeverity != 256) {
                            if (classScopeEnclosingClassScope != null) {
                                iComputeOuterMostVisibility = Util.computeOuterMostVisibility(classScopeEnclosingClassScope.referenceType(), iComputeOuterMostVisibility);
                            }
                            problemReporter.javadocMissing(this.sourceStart, this.sourceEnd, iComputeSeverity, (this.binding.modifiers & (-8)) | iComputeOuterMostVisibility);
                        }
                    }
                }
            } finally {
                methodScope.initializedField = fieldBinding3;
                methodScope.lastVisibleFieldID = i;
                if (this.binding.constant(methodScope) == null) {
                    this.binding.setConstant(Constant.NotAConstant);
                }
            }
        }
    }

    public void traverse(ASTVisitor aSTVisitor, MethodScope methodScope) {
        if (aSTVisitor.visit(this, methodScope)) {
            Javadoc javadoc = this.javadoc;
            if (javadoc != null) {
                javadoc.traverse(aSTVisitor, methodScope);
            }
            if (this.annotations != null) {
                int length = this.annotations.length;
                for (int i = 0; i < length; i++) {
                    this.annotations[i].traverse(aSTVisitor, methodScope);
                }
            }
            if (this.type != null) {
                this.type.traverse(aSTVisitor, methodScope);
            }
            if (this.initialization != null) {
                this.initialization.traverse(aSTVisitor, methodScope);
            }
        }
        aSTVisitor.endVisit(this, methodScope);
    }
}
