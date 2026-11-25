package org.eclipse.jdt.internal.compiler.ast;

import org.eclipse.jdt.internal.compiler.ASTVisitor;
import org.eclipse.jdt.internal.compiler.classfmt.ClassFileConstants;
import org.eclipse.jdt.internal.compiler.codegen.BranchLabel;
import org.eclipse.jdt.internal.compiler.codegen.CodeStream;
import org.eclipse.jdt.internal.compiler.flow.FlowContext;
import org.eclipse.jdt.internal.compiler.flow.FlowInfo;
import org.eclipse.jdt.internal.compiler.flow.UnconditionalFlowInfo;
import org.eclipse.jdt.internal.compiler.impl.CompilerOptions;
import org.eclipse.jdt.internal.compiler.impl.Constant;
import org.eclipse.jdt.internal.compiler.lookup.BaseTypeBinding;
import org.eclipse.jdt.internal.compiler.lookup.BlockScope;
import org.eclipse.jdt.internal.compiler.lookup.LookupEnvironment;
import org.eclipse.jdt.internal.compiler.lookup.MethodBinding;
import org.eclipse.jdt.internal.compiler.lookup.PolyTypeBinding;
import org.eclipse.jdt.internal.compiler.lookup.Scope;
import org.eclipse.jdt.internal.compiler.lookup.TypeBinding;

/* JADX INFO: loaded from: classes14.dex */
public class ConditionalExpression extends OperatorExpression implements IPolyExpression {
    public Expression condition;
    private TypeBinding expectedType;
    int ifFalseNullStatus;
    int ifTrueNullStatus;
    public Constant optimizedBooleanConstant;
    public Constant optimizedIfFalseConstant;
    public Constant optimizedIfTrueConstant;
    private TypeBinding originalValueIfFalseType;
    private TypeBinding originalValueIfTrueType;
    private boolean use18specifics;
    public Expression valueIfFalse;
    public Expression valueIfTrue;
    int trueInitStateIndex = -1;
    int falseInitStateIndex = -1;
    int mergedInitStateIndex = -1;
    private int nullStatus = 1;
    private ExpressionContext expressionContext = ExpressionContext.VANILLA_CONTEXT;
    private boolean isPolyExpression = false;

    public ConditionalExpression(Expression expression, Expression expression2, Expression expression3) {
        this.condition = expression;
        this.valueIfTrue = expression2;
        this.valueIfFalse = expression3;
        this.sourceStart = expression.sourceStart;
        this.sourceEnd = expression3.sourceEnd;
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.Expression, org.eclipse.jdt.internal.compiler.ast.Statement
    public FlowInfo analyseCode(BlockScope blockScope, FlowContext flowContext, FlowInfo flowInfo) {
        FlowInfo flowInfoConditional;
        boolean z = false;
        int i = (flowInfo.reachMode() & 3) != 0 ? 1 : 0;
        Constant constantOptimizedBooleanConstant = this.condition.optimizedBooleanConstant();
        boolean z2 = constantOptimizedBooleanConstant != Constant.NotAConstant && constantOptimizedBooleanConstant.booleanValue();
        boolean z3 = (constantOptimizedBooleanConstant == Constant.NotAConstant || constantOptimizedBooleanConstant.booleanValue()) ? false : true;
        int iReachMode = flowInfo.reachMode();
        FlowInfo flowInfoAnalyseCode = this.condition.analyseCode(blockScope, flowContext, flowInfo, constantOptimizedBooleanConstant == Constant.NotAConstant);
        flowContext.conditionalLevel++;
        FlowInfo flowInfoCopy = flowInfoAnalyseCode.initsWhenTrue().copy();
        CompilerOptions compilerOptions = blockScope.compilerOptions();
        if (z3) {
            if ((iReachMode & 3) == 0) {
                flowInfoCopy.setReachMode(1);
            }
            if (!isKnowDeadCodePattern(this.condition) || compilerOptions.reportDeadCodeInTrivialIfStatement) {
                this.valueIfTrue.complainIfUnreachable(flowInfoCopy, blockScope, i, false);
            }
        }
        this.trueInitStateIndex = blockScope.methodScope().recordInitializationStates(flowInfoCopy);
        FlowInfo flowInfoAnalyseCode2 = this.valueIfTrue.analyseCode(blockScope, flowContext, flowInfoCopy);
        this.valueIfTrue.checkNPEbyUnboxing(blockScope, flowContext, flowInfoAnalyseCode2);
        this.ifTrueNullStatus = -1;
        if (compilerOptions.enableSyntacticNullAnalysisForFields) {
            this.ifTrueNullStatus = this.valueIfTrue.nullStatus(flowInfoAnalyseCode2, flowContext);
            flowContext.expireNullCheckedFieldInfo();
        }
        FlowInfo flowInfoCopy2 = flowInfoAnalyseCode.initsWhenFalse().copy();
        if (z2) {
            if ((iReachMode & 3) == 0) {
                flowInfoCopy2.setReachMode(1);
            }
            if (!isKnowDeadCodePattern(this.condition) || compilerOptions.reportDeadCodeInTrivialIfStatement) {
                this.valueIfFalse.complainIfUnreachable(flowInfoCopy2, blockScope, i, true);
            }
        }
        this.falseInitStateIndex = blockScope.methodScope().recordInitializationStates(flowInfoCopy2);
        FlowInfo flowInfoAnalyseCode3 = this.valueIfFalse.analyseCode(blockScope, flowContext, flowInfoCopy2);
        this.valueIfFalse.checkNPEbyUnboxing(blockScope, flowContext, flowInfoAnalyseCode3);
        flowContext.conditionalLevel--;
        if (z2) {
            flowInfoConditional = flowInfoAnalyseCode2.addPotentialInitializationsFrom(flowInfoAnalyseCode3);
            int i2 = this.ifTrueNullStatus;
            if (i2 != -1) {
                this.nullStatus = i2;
            } else {
                this.nullStatus = this.valueIfTrue.nullStatus(flowInfoAnalyseCode2, flowContext);
            }
        } else if (z3) {
            FlowInfo flowInfoAddPotentialInitializationsFrom = flowInfoAnalyseCode3.addPotentialInitializationsFrom(flowInfoAnalyseCode2);
            this.nullStatus = this.valueIfFalse.nullStatus(flowInfoAnalyseCode3, flowContext);
            flowInfoConditional = flowInfoAddPotentialInitializationsFrom;
        } else {
            computeNullStatus(flowInfoAnalyseCode2, flowInfoAnalyseCode3, flowContext);
            Constant constant = this.optimizedIfTrueConstant;
            boolean z4 = (constant == null || constant == Constant.NotAConstant || !constant.booleanValue()) ? false : true;
            boolean z5 = (constant == null || constant == Constant.NotAConstant || constant.booleanValue()) ? false : true;
            Constant constant2 = this.optimizedIfFalseConstant;
            boolean z6 = (constant2 == null || constant2 == Constant.NotAConstant || !constant2.booleanValue()) ? false : true;
            if (constant2 != null && constant2 != Constant.NotAConstant && !constant2.booleanValue()) {
                z = true;
            }
            UnconditionalFlowInfo unconditionalFlowInfoUnconditionalCopy = flowInfoAnalyseCode2.initsWhenTrue().unconditionalCopy();
            UnconditionalFlowInfo unconditionalFlowInfoUnconditionalCopy2 = flowInfoAnalyseCode3.initsWhenTrue().unconditionalCopy();
            UnconditionalFlowInfo unconditionalFlowInfoUnconditionalInits = flowInfoAnalyseCode2.initsWhenFalse().unconditionalInits();
            UnconditionalFlowInfo unconditionalFlowInfoUnconditionalInits2 = flowInfoAnalyseCode3.initsWhenFalse().unconditionalInits();
            if (z5) {
                unconditionalFlowInfoUnconditionalCopy.setReachMode(1);
            }
            if (z) {
                unconditionalFlowInfoUnconditionalCopy2.setReachMode(1);
            }
            if (z4) {
                unconditionalFlowInfoUnconditionalInits.setReachMode(1);
            }
            if (z6) {
                unconditionalFlowInfoUnconditionalInits2.setReachMode(1);
            }
            flowInfoConditional = FlowInfo.conditional(unconditionalFlowInfoUnconditionalCopy.mergedWith(unconditionalFlowInfoUnconditionalCopy2), unconditionalFlowInfoUnconditionalInits.mergedWith(unconditionalFlowInfoUnconditionalInits2));
        }
        this.mergedInitStateIndex = blockScope.methodScope().recordInitializationStates(flowInfoConditional);
        flowInfoConditional.setReachMode(iReachMode);
        return flowInfoConditional;
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.Expression
    public boolean checkNPE(BlockScope blockScope, FlowContext flowContext, FlowInfo flowInfo, int i) {
        int i2 = this.nullStatus;
        if ((i2 & 2) != 0) {
            blockScope.problemReporter().expressionNullReference(this);
            return true;
        }
        if ((i2 & 16) == 0) {
            return true;
        }
        blockScope.problemReporter().expressionPotentialNullReference(this);
        return true;
    }

    private void computeNullStatus(FlowInfo flowInfo, FlowInfo flowInfo2, FlowContext flowContext) {
        if (this.ifTrueNullStatus == -1) {
            this.ifTrueNullStatus = this.valueIfTrue.nullStatus(flowInfo, flowContext);
        }
        int iNullStatus = this.valueIfFalse.nullStatus(flowInfo2, flowContext);
        this.ifFalseNullStatus = iNullStatus;
        int i = this.ifTrueNullStatus;
        if (i == iNullStatus) {
            this.nullStatus = i;
            return;
        }
        if (flowInfo.reachMode() != 0) {
            this.nullStatus = this.ifFalseNullStatus;
            return;
        }
        if (flowInfo2.reachMode() != 0) {
            this.nullStatus = this.ifTrueNullStatus;
            return;
        }
        int i2 = this.ifTrueNullStatus | this.ifFalseNullStatus;
        int i3 = (i2 & 18) != 0 ? 16 : 0;
        if ((i2 & 36) != 0) {
            i3 |= 32;
        }
        if ((i2 & 9) != 0) {
            i3 |= 8;
        }
        if (i3 > 0) {
            this.nullStatus = i3;
        }
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.Expression
    public void generateCode(BlockScope blockScope, CodeStream codeStream, boolean z) {
        int i = codeStream.position;
        if (this.constant != Constant.NotAConstant) {
            if (z) {
                codeStream.generateConstant(this.constant, this.implicitConversion);
            }
            codeStream.recordPositionsFrom(i, this.sourceStart);
            return;
        }
        Constant constantOptimizedBooleanConstant = this.condition.optimizedBooleanConstant();
        boolean z2 = constantOptimizedBooleanConstant == Constant.NotAConstant || constantOptimizedBooleanConstant.booleanValue();
        boolean z3 = constantOptimizedBooleanConstant == Constant.NotAConstant || !constantOptimizedBooleanConstant.booleanValue();
        BranchLabel branchLabel = new BranchLabel(codeStream);
        BranchLabel branchLabel2 = new BranchLabel(codeStream);
        branchLabel2.tagBits |= 2;
        this.condition.generateOptimizedBoolean(blockScope, codeStream, null, branchLabel2, constantOptimizedBooleanConstant == Constant.NotAConstant);
        int i2 = this.trueInitStateIndex;
        if (i2 != -1) {
            codeStream.removeNotDefinitelyAssignedVariables(blockScope, i2);
            codeStream.addDefinitelyAssignedVariables(blockScope, this.trueInitStateIndex);
        }
        if (z2) {
            this.valueIfTrue.generateCode(blockScope, codeStream, z);
            if (z3) {
                int i3 = codeStream.position;
                codeStream.goto_(branchLabel);
                codeStream.recordPositionsFrom(i3, this.valueIfTrue.sourceEnd);
                if (z) {
                    int i4 = this.resolvedType.f626id;
                    if (i4 == 7 || i4 == 8) {
                        codeStream.decrStackSize(2);
                    } else {
                        codeStream.decrStackSize(1);
                    }
                }
            }
        }
        if (z3) {
            int i5 = this.falseInitStateIndex;
            if (i5 != -1) {
                codeStream.removeNotDefinitelyAssignedVariables(blockScope, i5);
                codeStream.addDefinitelyAssignedVariables(blockScope, this.falseInitStateIndex);
            }
            if (branchLabel2.forwardReferenceCount() > 0) {
                branchLabel2.place();
            }
            this.valueIfFalse.generateCode(blockScope, codeStream, z);
            if (z) {
                codeStream.recordExpressionType(this.resolvedType);
            }
            if (z2) {
                branchLabel.place();
            }
        }
        int i6 = this.mergedInitStateIndex;
        if (i6 != -1) {
            codeStream.removeNotDefinitelyAssignedVariables(blockScope, i6);
        }
        if (z) {
            codeStream.generateImplicitConversion(this.implicitConversion);
        }
        codeStream.recordPositionsFrom(i, this.sourceStart);
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.Expression
    public void generateOptimizedBoolean(BlockScope blockScope, CodeStream codeStream, BranchLabel branchLabel, BranchLabel branchLabel2, boolean z) {
        Constant constant;
        Constant constant2;
        int i = codeStream.position;
        if ((this.constant != Constant.NotAConstant && this.constant.typeID() == 5) || ((this.valueIfTrue.implicitConversion & 255) >> 4) != 5 || ((this.valueIfFalse.implicitConversion & 255) >> 4) != 5) {
            super.generateOptimizedBoolean(blockScope, codeStream, branchLabel, branchLabel2, z);
            return;
        }
        Constant constant3 = this.condition.constant;
        Constant constantOptimizedBooleanConstant = this.condition.optimizedBooleanConstant();
        boolean z2 = (constant3 == Constant.NotAConstant || constant3.booleanValue()) && (constantOptimizedBooleanConstant == Constant.NotAConstant || constantOptimizedBooleanConstant.booleanValue());
        boolean z3 = (constant3 == Constant.NotAConstant || !constant3.booleanValue()) && (constantOptimizedBooleanConstant == Constant.NotAConstant || !constantOptimizedBooleanConstant.booleanValue());
        BranchLabel branchLabel3 = new BranchLabel(codeStream);
        boolean z4 = constant3 == Constant.NotAConstant && constantOptimizedBooleanConstant == Constant.NotAConstant;
        Expression expression = this.condition;
        BranchLabel branchLabel4 = new BranchLabel(codeStream);
        expression.generateOptimizedBoolean(blockScope, codeStream, null, branchLabel4, z4);
        int i2 = this.trueInitStateIndex;
        if (i2 != -1) {
            codeStream.removeNotDefinitelyAssignedVariables(blockScope, i2);
            codeStream.addDefinitelyAssignedVariables(blockScope, this.trueInitStateIndex);
        }
        if (z2) {
            this.valueIfTrue.generateOptimizedBoolean(blockScope, codeStream, branchLabel, branchLabel2, z);
            if (z3 && (branchLabel2 != null ? branchLabel != null || (constant = this.optimizedIfTrueConstant) == null || constant == Constant.NotAConstant || constant.booleanValue() : branchLabel == null || (constant2 = this.optimizedIfTrueConstant) == null || constant2 == Constant.NotAConstant || !constant2.booleanValue())) {
                int i3 = codeStream.position;
                codeStream.goto_(branchLabel3);
                codeStream.recordPositionsFrom(i3, this.valueIfTrue.sourceEnd);
            }
        }
        if (z3) {
            branchLabel4.place();
            int i4 = this.falseInitStateIndex;
            if (i4 != -1) {
                codeStream.removeNotDefinitelyAssignedVariables(blockScope, i4);
                codeStream.addDefinitelyAssignedVariables(blockScope, this.falseInitStateIndex);
            }
            this.valueIfFalse.generateOptimizedBoolean(blockScope, codeStream, branchLabel, branchLabel2, z);
            branchLabel3.place();
        }
        int i5 = this.mergedInitStateIndex;
        if (i5 != -1) {
            codeStream.removeNotDefinitelyAssignedVariables(blockScope, i5);
        }
        codeStream.recordPositionsFrom(i, this.sourceEnd);
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.Expression
    public int nullStatus(FlowInfo flowInfo, FlowContext flowContext) {
        if ((this.implicitConversion & 512) != 0) {
            return 4;
        }
        return this.nullStatus;
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.Expression
    public Constant optimizedBooleanConstant() {
        Constant constant = this.optimizedBooleanConstant;
        return constant == null ? this.constant : constant;
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.OperatorExpression
    public StringBuffer printExpressionNoParenthesis(int i, StringBuffer stringBuffer) {
        this.condition.printExpression(i, stringBuffer).append(" ? ");
        this.valueIfTrue.printExpression(0, stringBuffer).append(" : ");
        return this.valueIfFalse.printExpression(0, stringBuffer);
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.Expression
    public TypeBinding resolveType(BlockScope blockScope) {
        TypeBinding typeBindingLowerUpperBound;
        Constant constant;
        Constant constant2;
        Constant constant3;
        LookupEnvironment lookupEnvironmentEnvironment = blockScope.environment();
        long j = blockScope.compilerOptions().sourceLevel;
        boolean z = j >= ClassFileConstants.JDK1_5;
        boolean z2 = j >= ClassFileConstants.JDK1_8;
        this.use18specifics = z2;
        if (z2 && (this.expressionContext == ExpressionContext.ASSIGNMENT_CONTEXT || this.expressionContext == ExpressionContext.INVOCATION_CONTEXT)) {
            this.valueIfTrue.setExpressionContext(this.expressionContext);
            this.valueIfTrue.setExpectedType(this.expectedType);
            this.valueIfFalse.setExpressionContext(this.expressionContext);
            this.valueIfFalse.setExpectedType(this.expectedType);
        }
        if (this.constant != Constant.NotAConstant) {
            this.constant = Constant.NotAConstant;
            TypeBinding typeBindingResolveTypeExpecting = this.condition.resolveTypeExpecting(blockScope, TypeBinding.BOOLEAN);
            this.condition.computeConversion(blockScope, TypeBinding.BOOLEAN, typeBindingResolveTypeExpecting);
            Expression expression = this.valueIfTrue;
            if (expression instanceof CastExpression) {
                expression.bits |= 32;
            }
            this.originalValueIfTrueType = this.valueIfTrue.resolveType(blockScope);
            Expression expression2 = this.valueIfFalse;
            if (expression2 instanceof CastExpression) {
                expression2.bits |= 32;
            }
            TypeBinding typeBindingResolveType = this.valueIfFalse.resolveType(blockScope);
            this.originalValueIfFalseType = typeBindingResolveType;
            if (typeBindingResolveTypeExpecting == null || this.originalValueIfTrueType == null || typeBindingResolveType == null) {
                return null;
            }
        } else {
            if (this.originalValueIfTrueType.kind() == 65540) {
                this.originalValueIfTrueType = this.valueIfTrue.resolveType(blockScope);
            }
            if (this.originalValueIfFalseType.kind() == 65540) {
                this.originalValueIfFalseType = this.valueIfFalse.resolveType(blockScope);
            }
            TypeBinding typeBinding = this.originalValueIfTrueType;
            if (typeBinding == null || !typeBinding.isValidBinding()) {
                this.resolvedType = null;
                return null;
            }
            TypeBinding typeBinding2 = this.originalValueIfFalseType;
            if (typeBinding2 == null || !typeBinding2.isValidBinding()) {
                this.resolvedType = null;
                return null;
            }
        }
        if (isPolyExpression()) {
            TypeBinding typeBinding3 = this.expectedType;
            if (typeBinding3 == null || !typeBinding3.isProperType(true)) {
                return new PolyTypeBinding(this);
            }
            TypeBinding typeBinding4 = computeConversions(blockScope, this.expectedType) ? this.expectedType : null;
            this.resolvedType = typeBinding4;
            return typeBinding4;
        }
        TypeBinding typeBindingComputeBoxingType = this.originalValueIfTrueType;
        TypeBinding typeBindingComputeBoxingType2 = this.originalValueIfFalseType;
        if (z && TypeBinding.notEquals(typeBindingComputeBoxingType, typeBindingComputeBoxingType2)) {
            if (typeBindingComputeBoxingType.isBaseType()) {
                if (typeBindingComputeBoxingType2.isBaseType()) {
                    if (typeBindingComputeBoxingType == TypeBinding.NULL) {
                        typeBindingComputeBoxingType2 = lookupEnvironmentEnvironment.computeBoxingType(typeBindingComputeBoxingType2);
                    } else if (typeBindingComputeBoxingType2 == TypeBinding.NULL) {
                        typeBindingComputeBoxingType = lookupEnvironmentEnvironment.computeBoxingType(typeBindingComputeBoxingType);
                    }
                } else {
                    TypeBinding typeBindingComputeBoxingType3 = typeBindingComputeBoxingType2.isBaseType() ? typeBindingComputeBoxingType2 : lookupEnvironmentEnvironment.computeBoxingType(typeBindingComputeBoxingType2);
                    if (typeBindingComputeBoxingType.isNumericType() && typeBindingComputeBoxingType3.isNumericType()) {
                        typeBindingComputeBoxingType2 = typeBindingComputeBoxingType3;
                    } else if (typeBindingComputeBoxingType != TypeBinding.NULL) {
                        typeBindingComputeBoxingType2 = lookupEnvironmentEnvironment.computeBoxingType(typeBindingComputeBoxingType2);
                    }
                }
            } else if (typeBindingComputeBoxingType2.isBaseType()) {
                TypeBinding typeBindingComputeBoxingType4 = typeBindingComputeBoxingType.isBaseType() ? typeBindingComputeBoxingType : lookupEnvironmentEnvironment.computeBoxingType(typeBindingComputeBoxingType);
                if (typeBindingComputeBoxingType4.isNumericType() && typeBindingComputeBoxingType2.isNumericType()) {
                    typeBindingComputeBoxingType = typeBindingComputeBoxingType4;
                } else if (typeBindingComputeBoxingType2 != TypeBinding.NULL) {
                    typeBindingComputeBoxingType = lookupEnvironmentEnvironment.computeBoxingType(typeBindingComputeBoxingType);
                }
            } else {
                TypeBinding typeBindingComputeBoxingType5 = lookupEnvironmentEnvironment.computeBoxingType(typeBindingComputeBoxingType);
                TypeBinding typeBindingComputeBoxingType6 = lookupEnvironmentEnvironment.computeBoxingType(typeBindingComputeBoxingType2);
                if (typeBindingComputeBoxingType5.isNumericType() && typeBindingComputeBoxingType6.isNumericType()) {
                    typeBindingComputeBoxingType = typeBindingComputeBoxingType5;
                    typeBindingComputeBoxingType2 = typeBindingComputeBoxingType6;
                }
            }
        }
        Constant constant4 = this.condition.constant;
        if (constant4 != Constant.NotAConstant && (constant2 = this.valueIfTrue.constant) != Constant.NotAConstant && (constant3 = this.valueIfFalse.constant) != Constant.NotAConstant) {
            if (!constant4.booleanValue()) {
                constant2 = constant3;
            }
            this.constant = constant2;
        }
        if (TypeBinding.equalsEquals(typeBindingComputeBoxingType, typeBindingComputeBoxingType2)) {
            this.valueIfTrue.computeConversion(blockScope, typeBindingComputeBoxingType, this.originalValueIfTrueType);
            this.valueIfFalse.computeConversion(blockScope, typeBindingComputeBoxingType2, this.originalValueIfFalseType);
            if (TypeBinding.equalsEquals(typeBindingComputeBoxingType, TypeBinding.BOOLEAN)) {
                this.optimizedIfTrueConstant = this.valueIfTrue.optimizedBooleanConstant();
                this.optimizedIfFalseConstant = this.valueIfFalse.optimizedBooleanConstant();
                if (this.optimizedIfTrueConstant != Constant.NotAConstant && this.optimizedIfFalseConstant != Constant.NotAConstant && this.optimizedIfTrueConstant.booleanValue() == this.optimizedIfFalseConstant.booleanValue()) {
                    this.optimizedBooleanConstant = this.optimizedIfTrueConstant;
                } else {
                    Constant constantOptimizedBooleanConstant = this.condition.optimizedBooleanConstant();
                    if (constantOptimizedBooleanConstant != Constant.NotAConstant) {
                        if (constantOptimizedBooleanConstant.booleanValue()) {
                            constant = this.optimizedIfTrueConstant;
                        } else {
                            constant = this.optimizedIfFalseConstant;
                        }
                        this.optimizedBooleanConstant = constant;
                    }
                }
            }
            TypeBinding typeBindingMoreDangerousType = NullAnnotationMatching.moreDangerousType(typeBindingComputeBoxingType, typeBindingComputeBoxingType2);
            this.resolvedType = typeBindingMoreDangerousType;
            return typeBindingMoreDangerousType;
        }
        if (typeBindingComputeBoxingType.isNumericType() && typeBindingComputeBoxingType2.isNumericType()) {
            if ((TypeBinding.equalsEquals(typeBindingComputeBoxingType, TypeBinding.BYTE) && TypeBinding.equalsEquals(typeBindingComputeBoxingType2, TypeBinding.SHORT)) || (TypeBinding.equalsEquals(typeBindingComputeBoxingType, TypeBinding.SHORT) && TypeBinding.equalsEquals(typeBindingComputeBoxingType2, TypeBinding.BYTE))) {
                this.valueIfTrue.computeConversion(blockScope, TypeBinding.SHORT, this.originalValueIfTrueType);
                this.valueIfFalse.computeConversion(blockScope, TypeBinding.SHORT, this.originalValueIfFalseType);
                BaseTypeBinding baseTypeBinding = TypeBinding.SHORT;
                this.resolvedType = baseTypeBinding;
                return baseTypeBinding;
            }
            if ((TypeBinding.equalsEquals(typeBindingComputeBoxingType, TypeBinding.BYTE) || TypeBinding.equalsEquals(typeBindingComputeBoxingType, TypeBinding.SHORT) || TypeBinding.equalsEquals(typeBindingComputeBoxingType, TypeBinding.CHAR)) && TypeBinding.equalsEquals(typeBindingComputeBoxingType2, TypeBinding.INT) && this.valueIfFalse.isConstantValueOfTypeAssignableToType(typeBindingComputeBoxingType2, typeBindingComputeBoxingType)) {
                this.valueIfTrue.computeConversion(blockScope, typeBindingComputeBoxingType, this.originalValueIfTrueType);
                this.valueIfFalse.computeConversion(blockScope, typeBindingComputeBoxingType, this.originalValueIfFalseType);
                this.resolvedType = typeBindingComputeBoxingType;
                return typeBindingComputeBoxingType;
            }
            if ((TypeBinding.equalsEquals(typeBindingComputeBoxingType2, TypeBinding.BYTE) || TypeBinding.equalsEquals(typeBindingComputeBoxingType2, TypeBinding.SHORT) || TypeBinding.equalsEquals(typeBindingComputeBoxingType2, TypeBinding.CHAR)) && TypeBinding.equalsEquals(typeBindingComputeBoxingType, TypeBinding.INT) && this.valueIfTrue.isConstantValueOfTypeAssignableToType(typeBindingComputeBoxingType, typeBindingComputeBoxingType2)) {
                this.valueIfTrue.computeConversion(blockScope, typeBindingComputeBoxingType2, this.originalValueIfTrueType);
                this.valueIfFalse.computeConversion(blockScope, typeBindingComputeBoxingType2, this.originalValueIfFalseType);
                this.resolvedType = typeBindingComputeBoxingType2;
                return typeBindingComputeBoxingType2;
            }
            if (BaseTypeBinding.isNarrowing(typeBindingComputeBoxingType.f626id, 10) && BaseTypeBinding.isNarrowing(typeBindingComputeBoxingType2.f626id, 10)) {
                this.valueIfTrue.computeConversion(blockScope, TypeBinding.INT, this.originalValueIfTrueType);
                this.valueIfFalse.computeConversion(blockScope, TypeBinding.INT, this.originalValueIfFalseType);
                BaseTypeBinding baseTypeBinding2 = TypeBinding.INT;
                this.resolvedType = baseTypeBinding2;
                return baseTypeBinding2;
            }
            if (BaseTypeBinding.isNarrowing(typeBindingComputeBoxingType.f626id, 7) && BaseTypeBinding.isNarrowing(typeBindingComputeBoxingType2.f626id, 7)) {
                this.valueIfTrue.computeConversion(blockScope, TypeBinding.LONG, this.originalValueIfTrueType);
                this.valueIfFalse.computeConversion(blockScope, TypeBinding.LONG, this.originalValueIfFalseType);
                BaseTypeBinding baseTypeBinding3 = TypeBinding.LONG;
                this.resolvedType = baseTypeBinding3;
                return baseTypeBinding3;
            }
            if (BaseTypeBinding.isNarrowing(typeBindingComputeBoxingType.f626id, 9) && BaseTypeBinding.isNarrowing(typeBindingComputeBoxingType2.f626id, 9)) {
                this.valueIfTrue.computeConversion(blockScope, TypeBinding.FLOAT, this.originalValueIfTrueType);
                this.valueIfFalse.computeConversion(blockScope, TypeBinding.FLOAT, this.originalValueIfFalseType);
                BaseTypeBinding baseTypeBinding4 = TypeBinding.FLOAT;
                this.resolvedType = baseTypeBinding4;
                return baseTypeBinding4;
            }
            this.valueIfTrue.computeConversion(blockScope, TypeBinding.DOUBLE, this.originalValueIfTrueType);
            this.valueIfFalse.computeConversion(blockScope, TypeBinding.DOUBLE, this.originalValueIfFalseType);
            BaseTypeBinding baseTypeBinding5 = TypeBinding.DOUBLE;
            this.resolvedType = baseTypeBinding5;
            return baseTypeBinding5;
        }
        if (typeBindingComputeBoxingType.isBaseType() && typeBindingComputeBoxingType != TypeBinding.NULL) {
            if (z) {
                typeBindingComputeBoxingType = lookupEnvironmentEnvironment.computeBoxingType(typeBindingComputeBoxingType);
            } else {
                blockScope.problemReporter().conditionalArgumentsIncompatibleTypes(this, typeBindingComputeBoxingType, typeBindingComputeBoxingType2);
                return null;
            }
        }
        if (typeBindingComputeBoxingType2.isBaseType() && typeBindingComputeBoxingType2 != TypeBinding.NULL) {
            if (z) {
                typeBindingComputeBoxingType2 = lookupEnvironmentEnvironment.computeBoxingType(typeBindingComputeBoxingType2);
            } else {
                blockScope.problemReporter().conditionalArgumentsIncompatibleTypes(this, typeBindingComputeBoxingType, typeBindingComputeBoxingType2);
                return null;
            }
        }
        if (z) {
            if (typeBindingComputeBoxingType == TypeBinding.NULL) {
                typeBindingLowerUpperBound = typeBindingComputeBoxingType2;
            } else {
                typeBindingLowerUpperBound = typeBindingComputeBoxingType2 == TypeBinding.NULL ? typeBindingComputeBoxingType : blockScope.lowerUpperBound(new TypeBinding[]{typeBindingComputeBoxingType, typeBindingComputeBoxingType2});
            }
            if (typeBindingLowerUpperBound != null) {
                this.valueIfTrue.computeConversion(blockScope, typeBindingLowerUpperBound, this.originalValueIfTrueType);
                this.valueIfFalse.computeConversion(blockScope, typeBindingLowerUpperBound, this.originalValueIfFalseType);
                TypeBinding typeBindingCapture = typeBindingLowerUpperBound.capture(blockScope, this.sourceStart, this.sourceEnd);
                this.resolvedType = typeBindingCapture;
                return typeBindingCapture;
            }
        } else {
            if (typeBindingComputeBoxingType2.isCompatibleWith(typeBindingComputeBoxingType)) {
                this.valueIfTrue.computeConversion(blockScope, typeBindingComputeBoxingType, this.originalValueIfTrueType);
                this.valueIfFalse.computeConversion(blockScope, typeBindingComputeBoxingType, this.originalValueIfFalseType);
                this.resolvedType = typeBindingComputeBoxingType;
                return typeBindingComputeBoxingType;
            }
            if (typeBindingComputeBoxingType.isCompatibleWith(typeBindingComputeBoxingType2)) {
                this.valueIfTrue.computeConversion(blockScope, typeBindingComputeBoxingType2, this.originalValueIfTrueType);
                this.valueIfFalse.computeConversion(blockScope, typeBindingComputeBoxingType2, this.originalValueIfFalseType);
                this.resolvedType = typeBindingComputeBoxingType2;
                return typeBindingComputeBoxingType2;
            }
        }
        blockScope.problemReporter().conditionalArgumentsIncompatibleTypes(this, typeBindingComputeBoxingType, typeBindingComputeBoxingType2);
        return null;
    }

    protected boolean computeConversions(BlockScope blockScope, TypeBinding typeBinding) {
        boolean z;
        TypeBinding typeBinding2 = this.originalValueIfTrueType;
        if (typeBinding2 == null || !typeBinding2.isValidBinding()) {
            z = true;
        } else {
            if (this.valueIfTrue.isConstantValueOfTypeAssignableToType(this.originalValueIfTrueType, typeBinding) || this.originalValueIfTrueType.isCompatibleWith(typeBinding)) {
                this.valueIfTrue.computeConversion(blockScope, typeBinding, this.originalValueIfTrueType);
                if (this.originalValueIfTrueType.needsUncheckedConversion(typeBinding)) {
                    blockScope.problemReporter().unsafeTypeConversion(this.valueIfTrue, this.originalValueIfTrueType, typeBinding);
                }
                Expression expression = this.valueIfTrue;
                if ((expression instanceof CastExpression) && (expression.bits & 16416) == 0) {
                    CastExpression.checkNeedForAssignedCast(blockScope, typeBinding, (CastExpression) this.valueIfTrue);
                }
            } else if (isBoxingCompatible(this.originalValueIfTrueType, typeBinding, this.valueIfTrue, blockScope)) {
                this.valueIfTrue.computeConversion(blockScope, typeBinding, this.originalValueIfTrueType);
                Expression expression2 = this.valueIfTrue;
                if ((expression2 instanceof CastExpression) && (expression2.bits & 16416) == 0) {
                    CastExpression.checkNeedForAssignedCast(blockScope, typeBinding, (CastExpression) this.valueIfTrue);
                }
            } else {
                blockScope.problemReporter().typeMismatchError(this.originalValueIfTrueType, typeBinding, this.valueIfTrue, (ASTNode) null);
                z = false;
            }
            z = true;
        }
        TypeBinding typeBinding3 = this.originalValueIfFalseType;
        if (typeBinding3 != null && typeBinding3.isValidBinding()) {
            if (this.valueIfFalse.isConstantValueOfTypeAssignableToType(this.originalValueIfFalseType, typeBinding) || this.originalValueIfFalseType.isCompatibleWith(typeBinding)) {
                this.valueIfFalse.computeConversion(blockScope, typeBinding, this.originalValueIfFalseType);
                if (this.originalValueIfFalseType.needsUncheckedConversion(typeBinding)) {
                    blockScope.problemReporter().unsafeTypeConversion(this.valueIfFalse, this.originalValueIfFalseType, typeBinding);
                }
                Expression expression3 = this.valueIfFalse;
                if ((expression3 instanceof CastExpression) && (expression3.bits & 16416) == 0) {
                    CastExpression.checkNeedForAssignedCast(blockScope, typeBinding, (CastExpression) this.valueIfFalse);
                }
            } else if (isBoxingCompatible(this.originalValueIfFalseType, typeBinding, this.valueIfFalse, blockScope)) {
                this.valueIfFalse.computeConversion(blockScope, typeBinding, this.originalValueIfFalseType);
                Expression expression4 = this.valueIfFalse;
                if ((expression4 instanceof CastExpression) && (expression4.bits & 16416) == 0) {
                    CastExpression.checkNeedForAssignedCast(blockScope, typeBinding, (CastExpression) this.valueIfFalse);
                }
            } else {
                blockScope.problemReporter().typeMismatchError(this.originalValueIfFalseType, typeBinding, this.valueIfFalse, (ASTNode) null);
                return false;
            }
        }
        return z;
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.Expression, org.eclipse.jdt.internal.compiler.ast.IPolyExpression
    public void setExpectedType(TypeBinding typeBinding) {
        this.expectedType = typeBinding;
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.Expression, org.eclipse.jdt.internal.compiler.ast.IPolyExpression
    public void setExpressionContext(ExpressionContext expressionContext) {
        this.expressionContext = expressionContext;
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.Statement, org.eclipse.jdt.internal.compiler.ast.IPolyExpression, org.eclipse.jdt.internal.compiler.lookup.InvocationSite
    public ExpressionContext getExpressionContext() {
        return this.expressionContext;
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.Expression, org.eclipse.jdt.internal.compiler.ast.IPolyExpression
    public Expression[] getPolyExpressions() {
        Expression[] polyExpressions = this.valueIfTrue.getPolyExpressions();
        Expression[] polyExpressions2 = this.valueIfFalse.getPolyExpressions();
        if (polyExpressions.length == 0) {
            return polyExpressions2;
        }
        if (polyExpressions2.length == 0) {
            return polyExpressions;
        }
        Expression[] expressionArr = new Expression[polyExpressions.length + polyExpressions2.length];
        System.arraycopy(polyExpressions, 0, expressionArr, 0, polyExpressions.length);
        System.arraycopy(polyExpressions2, 0, expressionArr, polyExpressions.length, polyExpressions2.length);
        return expressionArr;
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.Expression, org.eclipse.jdt.internal.compiler.ast.IPolyExpression
    public boolean isPertinentToApplicability(TypeBinding typeBinding, MethodBinding methodBinding) {
        return this.valueIfTrue.isPertinentToApplicability(typeBinding, methodBinding) && this.valueIfFalse.isPertinentToApplicability(typeBinding, methodBinding);
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.Expression, org.eclipse.jdt.internal.compiler.ast.IPolyExpression
    public boolean isPotentiallyCompatibleWith(TypeBinding typeBinding, Scope scope) {
        return this.valueIfTrue.isPotentiallyCompatibleWith(typeBinding, scope) && this.valueIfFalse.isPotentiallyCompatibleWith(typeBinding, scope);
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.Expression, org.eclipse.jdt.internal.compiler.ast.IPolyExpression
    public boolean isFunctionalType() {
        return this.valueIfTrue.isFunctionalType() || this.valueIfFalse.isFunctionalType();
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.Expression, org.eclipse.jdt.internal.compiler.ast.IPolyExpression
    public boolean isPolyExpression() throws UnsupportedOperationException {
        if (!this.use18specifics) {
            return false;
        }
        if (this.isPolyExpression) {
            return true;
        }
        if ((this.expressionContext != ExpressionContext.ASSIGNMENT_CONTEXT && this.expressionContext != ExpressionContext.INVOCATION_CONTEXT) || this.originalValueIfTrueType == null || this.originalValueIfFalseType == null) {
            return false;
        }
        if (this.valueIfTrue.isPolyExpression() || this.valueIfFalse.isPolyExpression()) {
            return true;
        }
        if ((this.originalValueIfTrueType.isBaseType() || (this.originalValueIfTrueType.f626id >= 26 && this.originalValueIfTrueType.f626id <= 33)) && (this.originalValueIfFalseType.isBaseType() || (this.originalValueIfFalseType.f626id >= 26 && this.originalValueIfFalseType.f626id <= 33))) {
            return false;
        }
        this.isPolyExpression = true;
        return true;
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.Expression, org.eclipse.jdt.internal.compiler.ast.IPolyExpression
    public boolean isCompatibleWith(TypeBinding typeBinding, Scope scope) {
        if (isPolyExpression()) {
            return this.valueIfTrue.isCompatibleWith(typeBinding, scope) && this.valueIfFalse.isCompatibleWith(typeBinding, scope);
        }
        return super.isCompatibleWith(typeBinding, scope);
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.Expression, org.eclipse.jdt.internal.compiler.ast.IPolyExpression
    public boolean isBoxingCompatibleWith(TypeBinding typeBinding, Scope scope) {
        if (isPolyExpression()) {
            return (this.valueIfTrue.isCompatibleWith(typeBinding, scope) || this.valueIfTrue.isBoxingCompatibleWith(typeBinding, scope)) && (this.valueIfFalse.isCompatibleWith(typeBinding, scope) || this.valueIfFalse.isBoxingCompatibleWith(typeBinding, scope));
        }
        return super.isBoxingCompatibleWith(typeBinding, scope);
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.Expression, org.eclipse.jdt.internal.compiler.ast.IPolyExpression
    public boolean sIsMoreSpecific(TypeBinding typeBinding, TypeBinding typeBinding2, Scope scope) {
        if (super.sIsMoreSpecific(typeBinding, typeBinding2, scope)) {
            return true;
        }
        return isPolyExpression() && this.valueIfTrue.sIsMoreSpecific(typeBinding, typeBinding2, scope) && this.valueIfFalse.sIsMoreSpecific(typeBinding, typeBinding2, scope);
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.Expression, org.eclipse.jdt.internal.compiler.ast.ASTNode
    public void traverse(ASTVisitor aSTVisitor, BlockScope blockScope) {
        if (aSTVisitor.visit(this, blockScope)) {
            this.condition.traverse(aSTVisitor, blockScope);
            this.valueIfTrue.traverse(aSTVisitor, blockScope);
            this.valueIfFalse.traverse(aSTVisitor, blockScope);
        }
        aSTVisitor.endVisit(this, blockScope);
    }
}
