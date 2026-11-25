package org.eclipse.jdt.internal.compiler.ast;

import org.eclipse.jdt.internal.compiler.ASTVisitor;
import org.eclipse.jdt.internal.compiler.classfmt.ClassFileConstants;
import org.eclipse.jdt.internal.compiler.codegen.CodeStream;
import org.eclipse.jdt.internal.compiler.flow.FlowContext;
import org.eclipse.jdt.internal.compiler.flow.FlowInfo;
import org.eclipse.jdt.internal.compiler.flow.UnconditionalFlowInfo;
import org.eclipse.jdt.internal.compiler.impl.CompilerOptions;
import org.eclipse.jdt.internal.compiler.impl.Constant;
import org.eclipse.jdt.internal.compiler.lookup.ArrayBinding;
import org.eclipse.jdt.internal.compiler.lookup.BlockScope;
import org.eclipse.jdt.internal.compiler.lookup.InferenceContext18;
import org.eclipse.jdt.internal.compiler.lookup.InvocationSite;
import org.eclipse.jdt.internal.compiler.lookup.LocalVariableBinding;
import org.eclipse.jdt.internal.compiler.lookup.MethodBinding;
import org.eclipse.jdt.internal.compiler.lookup.ParameterizedGenericMethodBinding;
import org.eclipse.jdt.internal.compiler.lookup.PolymorphicMethodBinding;
import org.eclipse.jdt.internal.compiler.lookup.ReferenceBinding;
import org.eclipse.jdt.internal.compiler.lookup.Scope;
import org.eclipse.jdt.internal.compiler.lookup.TypeBinding;

/* JADX INFO: loaded from: classes14.dex */
public class CastExpression extends Expression {
    public TypeBinding expectedType;
    public Expression expression;
    public TypeReference type;

    public CastExpression(Expression expression, TypeReference typeReference) {
        this.expression = expression;
        this.type = typeReference;
        typeReference.bits |= 1073741824;
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.Expression, org.eclipse.jdt.internal.compiler.ast.Statement
    public FlowInfo analyseCode(BlockScope blockScope, FlowContext flowContext, FlowInfo flowInfo) {
        UnconditionalFlowInfo unconditionalFlowInfoUnconditionalInits = this.expression.analyseCode(blockScope, flowContext, flowInfo).unconditionalInits();
        this.expression.checkNPEbyUnboxing(blockScope, flowContext, flowInfo);
        flowContext.recordAbruptExit();
        return unconditionalFlowInfoUnconditionalInits;
    }

    public static void checkNeedForAssignedCast(BlockScope blockScope, TypeBinding typeBinding, CastExpression castExpression) {
        TypeBinding typeBinding2;
        if (blockScope.compilerOptions().getSeverity(67108864) == 256 || (typeBinding2 = castExpression.expression.resolvedType) == null || castExpression.resolvedType.isBaseType() || !typeBinding2.isCompatibleWith(typeBinding, blockScope)) {
            return;
        }
        if (blockScope.environment().usesNullTypeAnnotations() && NullAnnotationMatching.analyse(typeBinding, typeBinding2, -1).isAnyMismatch()) {
            return;
        }
        blockScope.problemReporter().unnecessaryCast(castExpression);
    }

    public static void checkNeedForCastCast(BlockScope blockScope, CastExpression castExpression) {
        if (blockScope.compilerOptions().getSeverity(67108864) == 256) {
            return;
        }
        CastExpression castExpression2 = (CastExpression) castExpression.expression;
        if ((castExpression2.bits & 16384) == 0) {
            return;
        }
        CastExpression castExpression3 = new CastExpression(null, castExpression.type);
        castExpression3.resolvedType = castExpression.resolvedType;
        if (castExpression3.checkCastTypesCompatibility(blockScope, castExpression.resolvedType, castExpression2.expression.resolvedType, null)) {
            blockScope.problemReporter().unnecessaryCast(castExpression2);
        }
    }

    public static void checkNeedForEnclosingInstanceCast(BlockScope blockScope, Expression expression, TypeBinding typeBinding, TypeBinding typeBinding2) {
        if (blockScope.compilerOptions().getSeverity(67108864) == 256) {
            return;
        }
        CastExpression castExpression = (CastExpression) expression;
        TypeBinding typeBinding3 = castExpression.expression.resolvedType;
        if (typeBinding3 == null) {
            return;
        }
        if (TypeBinding.equalsEquals(typeBinding3, typeBinding)) {
            blockScope.problemReporter().unnecessaryCast(castExpression);
        } else {
            if (typeBinding3 == TypeBinding.NULL || typeBinding3.isBaseType() || typeBinding3.isArrayType() || !TypeBinding.equalsEquals(typeBinding2, blockScope.getMemberType(typeBinding2.sourceName(), (ReferenceBinding) typeBinding3))) {
                return;
            }
            blockScope.problemReporter().unnecessaryCast(castExpression);
        }
    }

    public static void checkNeedForArgumentCast(BlockScope blockScope, int i, int i2, Expression expression, int i3) {
        if (blockScope.compilerOptions().getSeverity(67108864) == 256) {
            return;
        }
        if ((expression.bits & 16384) == 0 && expression.resolvedType.isBaseType()) {
            return;
        }
        CastExpression castExpression = (CastExpression) expression;
        TypeBinding typeBinding = castExpression.expression.resolvedType;
        if (typeBinding != null && typeBinding.f626id == i3) {
            blockScope.problemReporter().unnecessaryCast(castExpression);
        }
    }

    public static void checkNeedForArgumentCasts(BlockScope blockScope, Expression expression, TypeBinding typeBinding, MethodBinding methodBinding, Expression[] expressionArr, TypeBinding[] typeBindingArr, InvocationSite invocationSite) {
        if (blockScope.compilerOptions().getSeverity(67108864) == 256) {
            return;
        }
        int length = typeBindingArr.length;
        TypeBinding[] typeBindingArr2 = typeBindingArr;
        for (int i = 0; i < length; i++) {
            Expression expression2 = expressionArr[i];
            if ((expression2 instanceof CastExpression) && ((expression2.bits & 16384) != 0 || !expression2.resolvedType.isBaseType())) {
                CastExpression castExpression = (CastExpression) expression2;
                TypeBinding typeBinding2 = castExpression.expression.resolvedType;
                if (typeBinding2 == null) {
                    return;
                }
                if (TypeBinding.equalsEquals(typeBinding2, typeBindingArr[i])) {
                    blockScope.problemReporter().unnecessaryCast(castExpression);
                } else if (typeBinding2 != TypeBinding.NULL && (expression2.implicitConversion & 512) == 0) {
                    if (typeBindingArr2 == typeBindingArr) {
                        TypeBinding[] typeBindingArr3 = new TypeBinding[length];
                        System.arraycopy(typeBindingArr2, 0, typeBindingArr3, 0, length);
                        typeBindingArr2 = typeBindingArr3;
                    }
                    typeBindingArr2[i] = typeBinding2;
                }
            }
        }
        if (typeBindingArr2 != typeBindingArr) {
            checkAlternateBinding(blockScope, expression, typeBinding, methodBinding, expressionArr, typeBindingArr, typeBindingArr2, invocationSite);
        }
    }

    /* JADX WARN: Code duplicated, block: B:11:0x0022  */
    /* JADX WARN: Code duplicated, block: B:29:0x005b  */
    public static void checkNeedForArgumentCasts(BlockScope blockScope, int i, int i2, Expression expression, int i3, boolean z, Expression expression2, int i4, boolean z2) {
        if (blockScope.compilerOptions().getSeverity(67108864) == 256) {
            return;
        }
        if (z) {
            if ((expression.bits & 16384) == 0 && expression.resolvedType.isBaseType()) {
                z = false;
            } else {
                CastExpression castExpression = (CastExpression) expression;
                TypeBinding typeBinding = castExpression.expression.resolvedType;
                if (typeBinding == null) {
                    return;
                }
                int i5 = typeBinding.f626id;
                if (i5 == i3 || blockScope.environment().computeBoxingType(typeBinding).f626id == i3) {
                    blockScope.problemReporter().unnecessaryCast(castExpression);
                    z = false;
                } else if (i5 == 12) {
                    z = false;
                }
                i3 = i5;
            }
        }
        if (z2) {
            if ((expression2.bits & 16384) == 0 && expression2.resolvedType.isBaseType()) {
                z2 = false;
            } else {
                CastExpression castExpression2 = (CastExpression) expression2;
                TypeBinding typeBinding2 = castExpression2.expression.resolvedType;
                if (typeBinding2 == null) {
                    return;
                }
                int i6 = typeBinding2.f626id;
                if (i6 == i4 || blockScope.environment().computeBoxingType(typeBinding2).f626id == i4) {
                    blockScope.problemReporter().unnecessaryCast(castExpression2);
                    z2 = false;
                } else if (i6 == 12) {
                    z2 = false;
                }
                i4 = i6;
            }
        }
        if (z || z2) {
            if (i3 > 15 || i4 > 15) {
                if (i3 == 11) {
                    i4 = 1;
                } else if (i4 != 11) {
                    return;
                } else {
                    i3 = 1;
                }
            }
            if ((i2 & 986895) == (OperatorExpression.OperatorSignatures[i][(i3 << 4) + i4] & 986895)) {
                if (z) {
                    blockScope.problemReporter().unnecessaryCast((CastExpression) expression);
                }
                if (z2) {
                    blockScope.problemReporter().unnecessaryCast((CastExpression) expression2);
                }
            }
        }
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.Expression
    public boolean checkNPE(BlockScope blockScope, FlowContext flowContext, FlowInfo flowInfo, int i) {
        checkNPEbyUnboxing(blockScope, flowContext, flowInfo);
        return this.expression.checkNPE(blockScope, flowContext, flowInfo, i);
    }

    private static void checkAlternateBinding(BlockScope blockScope, Expression expression, TypeBinding typeBinding, MethodBinding methodBinding, Expression[] expressionArr, TypeBinding[] typeBindingArr, TypeBinding[] typeBindingArr2, final InvocationSite invocationSite) {
        MethodBinding method;
        int length;
        InvocationSite invocationSite2 = new InvocationSite() { // from class: org.eclipse.jdt.internal.compiler.ast.CastExpression.1
            @Override // org.eclipse.jdt.internal.compiler.lookup.InvocationSite
            public void acceptPotentiallyCompatibleMethods(MethodBinding[] methodBindingArr) {
            }

            @Override // org.eclipse.jdt.internal.compiler.lookup.InvocationSite
            public boolean checkingPotentialCompatibility() {
                return false;
            }

            @Override // org.eclipse.jdt.internal.compiler.lookup.InvocationSite
            public TypeBinding[] genericTypeArguments() {
                return null;
            }

            @Override // org.eclipse.jdt.internal.compiler.lookup.InvocationSite
            public void setActualReceiverType(ReferenceBinding referenceBinding) {
            }

            @Override // org.eclipse.jdt.internal.compiler.lookup.InvocationSite
            public void setDepth(int i) {
            }

            @Override // org.eclipse.jdt.internal.compiler.lookup.InvocationSite
            public void setFieldIndex(int i) {
            }

            @Override // org.eclipse.jdt.internal.compiler.lookup.InvocationSite
            public int sourceEnd() {
                return 0;
            }

            @Override // org.eclipse.jdt.internal.compiler.lookup.InvocationSite
            public int sourceStart() {
                return 0;
            }

            @Override // org.eclipse.jdt.internal.compiler.lookup.InvocationSite
            public boolean isSuperAccess() {
                return invocationSite.isSuperAccess();
            }

            @Override // org.eclipse.jdt.internal.compiler.lookup.InvocationSite
            public boolean isTypeAccess() {
                return invocationSite.isTypeAccess();
            }

            @Override // org.eclipse.jdt.internal.compiler.lookup.InvocationSite
            public TypeBinding invocationTargetType() {
                return invocationSite.invocationTargetType();
            }

            @Override // org.eclipse.jdt.internal.compiler.lookup.InvocationSite
            public boolean receiverIsImplicitThis() {
                return invocationSite.receiverIsImplicitThis();
            }

            @Override // org.eclipse.jdt.internal.compiler.lookup.InvocationSite
            public InferenceContext18 freshInferenceContext(Scope scope) {
                return invocationSite.freshInferenceContext(scope);
            }

            @Override // org.eclipse.jdt.internal.compiler.lookup.InvocationSite
            public ExpressionContext getExpressionContext() {
                return invocationSite.getExpressionContext();
            }

            @Override // org.eclipse.jdt.internal.compiler.lookup.InvocationSite
            public boolean isQualifiedSuper() {
                return invocationSite.isQualifiedSuper();
            }
        };
        if (methodBinding.isConstructor()) {
            method = blockScope.getConstructor((ReferenceBinding) typeBinding, typeBindingArr2, invocationSite2);
        } else if (expression.isImplicitThis()) {
            method = blockScope.getImplicitMethod(methodBinding.selector, typeBindingArr2, invocationSite2);
        } else {
            method = blockScope.getMethod(typeBinding, methodBinding.selector, typeBindingArr2, invocationSite2);
        }
        if (method == methodBinding) {
            int length2 = typeBindingArr.length;
            if (methodBinding.isVarargs() && (length = methodBinding.parameters.length) == length2) {
                int i = length - 1;
                ArrayBinding arrayBinding = (ArrayBinding) methodBinding.parameters[i];
                TypeBinding typeBinding2 = typeBindingArr2[i];
                if (arrayBinding.dimensions != typeBinding2.dimensions()) {
                    return;
                }
                if (typeBinding2.isCompatibleWith(arrayBinding.elementsType()) && typeBinding2.isCompatibleWith(arrayBinding)) {
                    return;
                }
            }
            for (int i2 = 0; i2 < length2; i2++) {
                if (TypeBinding.notEquals(typeBindingArr[i2], typeBindingArr2[i2])) {
                    blockScope.problemReporter().unnecessaryCast((CastExpression) expressionArr[i2]);
                }
            }
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:21:0x0037, code lost:
    
        return false;
     */
    @Override // org.eclipse.jdt.internal.compiler.ast.Expression
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public boolean checkUnsafeCast(org.eclipse.jdt.internal.compiler.lookup.Scope r11, org.eclipse.jdt.internal.compiler.lookup.TypeBinding r12, org.eclipse.jdt.internal.compiler.lookup.TypeBinding r13, org.eclipse.jdt.internal.compiler.lookup.TypeBinding r14, boolean r15) {
        /*
            Method dump skipped, instruction units count: 311
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: org.eclipse.jdt.internal.compiler.ast.CastExpression.checkUnsafeCast(org.eclipse.jdt.internal.compiler.lookup.Scope, org.eclipse.jdt.internal.compiler.lookup.TypeBinding, org.eclipse.jdt.internal.compiler.lookup.TypeBinding, org.eclipse.jdt.internal.compiler.lookup.TypeBinding, boolean):boolean");
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.Expression
    public void generateCode(BlockScope blockScope, CodeStream codeStream, boolean z) {
        int i = codeStream.position;
        boolean z2 = true;
        boolean z3 = (this.type.bits & 1048576) != 0;
        boolean z4 = (this.bits & 64) != 0;
        if (this.constant != Constant.NotAConstant) {
            if (z || z4 || z3) {
                codeStream.generateConstant(this.constant, this.implicitConversion);
                if (z4 || z3) {
                    codeStream.checkcast(this.type, this.resolvedType, i);
                }
                if (!z) {
                    codeStream.pop();
                }
            }
            codeStream.recordPositionsFrom(i, this.sourceStart);
            return;
        }
        Expression expression = this.expression;
        if (!z3 && !z && !z4) {
            z2 = false;
        }
        expression.generateCode(blockScope, codeStream, z2);
        if (z3 || (z4 && TypeBinding.notEquals(this.expression.postConversionType(blockScope), this.resolvedType.erasure()))) {
            codeStream.checkcast(this.type, this.resolvedType, i);
        }
        if (z) {
            codeStream.generateImplicitConversion(this.implicitConversion);
        } else if (z3 || z4) {
            int i2 = this.resolvedType.f626id;
            if (i2 == 7 || i2 == 8) {
                codeStream.pop2();
            } else {
                codeStream.pop();
            }
        }
        codeStream.recordPositionsFrom(i, this.sourceStart);
    }

    public Expression innermostCastedExpression() {
        Expression expression = this.expression;
        while (expression instanceof CastExpression) {
            expression = ((CastExpression) expression).expression;
        }
        return expression;
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.Expression
    public LocalVariableBinding localVariableBinding() {
        return this.expression.localVariableBinding();
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.Expression
    public int nullStatus(FlowInfo flowInfo, FlowContext flowContext) {
        if ((this.implicitConversion & 512) != 0) {
            return 4;
        }
        return this.expression.nullStatus(flowInfo, flowContext);
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.Expression
    public Constant optimizedBooleanConstant() {
        int i = this.resolvedType.f626id;
        if (i == 5 || i == 33) {
            return this.expression.optimizedBooleanConstant();
        }
        return Constant.NotAConstant;
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.Expression
    public StringBuffer printExpression(int i, StringBuffer stringBuffer) {
        int i2 = (this.bits & ASTNode.ParenthesizedMASK) >> 21;
        String str = "";
        for (int i3 = 0; i3 < i2; i3++) {
            stringBuffer.append('(');
            str = String.valueOf(str) + ')';
        }
        stringBuffer.append('(');
        this.type.print(0, stringBuffer).append(") ");
        return this.expression.printExpression(0, stringBuffer).append(str);
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.Expression
    public TypeBinding resolveType(BlockScope blockScope) {
        boolean z;
        MessageSend messageSend;
        MethodBinding methodBinding;
        this.constant = Constant.NotAConstant;
        boolean z2 = false;
        this.implicitConversion = 0;
        TypeBinding typeBindingResolveType = this.type.resolveType(blockScope);
        this.resolvedType = typeBindingResolveType;
        if (blockScope.compilerOptions().sourceLevel >= ClassFileConstants.JDK1_8) {
            this.expression.setExpressionContext(ExpressionContext.CASTING_CONTEXT);
            Expression expression = this.expression;
            if (expression instanceof FunctionalExpression) {
                expression.setExpectedType(this.resolvedType);
                this.bits |= 32;
            }
        }
        Expression expression2 = this.expression;
        if (expression2 instanceof CastExpression) {
            expression2.bits |= 32;
            z = true;
        } else {
            z = false;
        }
        TypeBinding typeBindingResolveType2 = this.expression.resolveType(blockScope);
        Expression expression3 = this.expression;
        if ((expression3 instanceof MessageSend) && (methodBinding = (messageSend = (MessageSend) expression3).binding) != null && methodBinding.isPolymorphic()) {
            messageSend.binding = blockScope.environment().updatePolymorphicMethodReturnType((PolymorphicMethodBinding) methodBinding, typeBindingResolveType);
            if (TypeBinding.notEquals(typeBindingResolveType2, typeBindingResolveType)) {
                this.bits |= 32;
                typeBindingResolveType2 = typeBindingResolveType;
            }
        }
        if (typeBindingResolveType != null) {
            if (typeBindingResolveType2 != null) {
                if (blockScope.compilerOptions().isAnnotationBasedNullAnalysisEnabled && NullAnnotationMatching.analyse(typeBindingResolveType, typeBindingResolveType2, -1).isAnyMismatch()) {
                    z2 = true;
                }
                if (checkCastTypesCompatibility(blockScope, typeBindingResolveType, typeBindingResolveType2, this.expression)) {
                    this.expression.computeConversion(blockScope, typeBindingResolveType, typeBindingResolveType2);
                    if ((this.bits & 128) != 0) {
                        if (blockScope.compilerOptions().reportUnavoidableGenericTypeProblems || !typeBindingResolveType2.isRawType() || !this.expression.forcedToBeRaw(blockScope.referenceContext())) {
                            blockScope.problemReporter().unsafeCast(this, blockScope);
                        }
                    } else if (z2) {
                        blockScope.problemReporter().unsafeNullnessCast(this, blockScope);
                    } else {
                        if (typeBindingResolveType.isRawType() && blockScope.compilerOptions().getSeverity(CompilerOptions.RawTypeReference) != 256) {
                            blockScope.problemReporter().rawTypeReference(this.type, typeBindingResolveType);
                        }
                        if ((this.bits & 16416) == 16384 && !isIndirectlyUsed()) {
                            blockScope.problemReporter().unnecessaryCast(this);
                        }
                    }
                } else {
                    if ((typeBindingResolveType.tagBits & 128) == 0) {
                        blockScope.problemReporter().typeCastError(this, typeBindingResolveType, typeBindingResolveType2);
                    }
                    this.bits |= 32;
                }
            }
            this.resolvedType = typeBindingResolveType.capture(blockScope, this.type.sourceStart, this.type.sourceEnd);
            if (z) {
                checkNeedForCastCast(blockScope, this);
            }
        }
        return this.resolvedType;
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.Expression, org.eclipse.jdt.internal.compiler.ast.IPolyExpression
    public void setExpectedType(TypeBinding typeBinding) {
        this.expectedType = typeBinding;
    }

    private boolean isIndirectlyUsed() {
        Expression expression = this.expression;
        if (expression instanceof MessageSend) {
            MethodBinding methodBinding = ((MessageSend) expression).binding;
            if ((methodBinding instanceof ParameterizedGenericMethodBinding) && ((ParameterizedGenericMethodBinding) methodBinding).inferredReturnType && (this.expectedType == null || TypeBinding.notEquals(this.resolvedType, this.expectedType))) {
                return true;
            }
        }
        return (this.expectedType == null || !this.resolvedType.isBaseType() || this.resolvedType.isCompatibleWith(this.expectedType)) ? false : true;
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.Expression
    public void tagAsNeedCheckCast() {
        this.bits |= 64;
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.Expression
    public void tagAsUnnecessaryCast(Scope scope, TypeBinding typeBinding) {
        this.bits |= 16384;
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.Expression, org.eclipse.jdt.internal.compiler.ast.ASTNode
    public void traverse(ASTVisitor aSTVisitor, BlockScope blockScope) {
        if (aSTVisitor.visit(this, blockScope)) {
            this.type.traverse(aSTVisitor, blockScope);
            this.expression.traverse(aSTVisitor, blockScope);
        }
        aSTVisitor.endVisit(this, blockScope);
    }
}
