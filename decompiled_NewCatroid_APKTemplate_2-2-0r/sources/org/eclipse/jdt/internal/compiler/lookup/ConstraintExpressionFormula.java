package org.eclipse.jdt.internal.compiler.lookup;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import org.eclipse.jdt.internal.compiler.ast.Argument;
import org.eclipse.jdt.internal.compiler.ast.ConditionalExpression;
import org.eclipse.jdt.internal.compiler.ast.Expression;
import org.eclipse.jdt.internal.compiler.ast.ExpressionContext;
import org.eclipse.jdt.internal.compiler.ast.Invocation;
import org.eclipse.jdt.internal.compiler.ast.LambdaExpression;
import org.eclipse.jdt.internal.compiler.ast.MessageSend;
import org.eclipse.jdt.internal.compiler.ast.ReferenceExpression;

/* JADX INFO: loaded from: classes14.dex */
class ConstraintExpressionFormula extends ConstraintFormula {
    boolean isSoft;
    Expression left;

    ConstraintExpressionFormula(Expression expression, TypeBinding typeBinding, int i) {
        this.left = expression;
        this.right = typeBinding;
        this.relation = i;
    }

    ConstraintExpressionFormula(Expression expression, TypeBinding typeBinding, int i, boolean z) {
        this(expression, typeBinding, i);
        this.isSoft = z;
    }

    @Override // org.eclipse.jdt.internal.compiler.lookup.ConstraintFormula
    public Object reduce(InferenceContext18 inferenceContext18) throws InferenceFailureException {
        if (this.relation == 8) {
            return this.left.isPotentiallyCompatibleWith(this.right, inferenceContext18.scope) ? TRUE : FALSE;
        }
        boolean z = true;
        if (this.right.isProperType(true)) {
            return (this.left.isCompatibleWith(this.right, inferenceContext18.scope) || this.left.isBoxingCompatibleWith(this.right, inferenceContext18.scope)) ? TRUE : FALSE;
        }
        if (!canBePolyExpression(this.left)) {
            TypeBinding typeBinding = this.left.resolvedType;
            if (typeBinding != null && typeBinding.isValidBinding()) {
                return ConstraintTypeFormula.create(typeBinding, this.right, 1, this.isSoft);
            }
            Expression expression = this.left;
            if ((expression instanceof MessageSend) && (((MessageSend) expression).actualReceiverType instanceof InferenceVariable)) {
                return null;
            }
            return FALSE;
        }
        TypeIds typeIds = this.left;
        if (typeIds instanceof Invocation) {
            Invocation invocation = (Invocation) typeIds;
            MethodBinding methodBindingBinding = invocation.binding();
            if (methodBindingBinding == null) {
                return null;
            }
            MethodBinding methodBindingShallowOriginal = methodBindingBinding.shallowOriginal();
            InferenceContext18.SuspendedInferenceRecord suspendedInferenceRecordEnterPolyInvocation = inferenceContext18.enterPolyInvocation(invocation, invocation.arguments());
            try {
                Expression[] expressionArrArguments = invocation.arguments();
                TypeBinding[] typeBindingArr = expressionArrArguments == null ? Binding.NO_PARAMETERS : new TypeBinding[expressionArrArguments.length];
                for (int i = 0; i < typeBindingArr.length; i++) {
                    typeBindingArr[i] = expressionArrArguments[i].resolvedType;
                }
                if (methodBindingBinding instanceof ParameterizedGenericMethodBinding) {
                    InferenceContext18 inferenceContext = invocation.getInferenceContext((ParameterizedGenericMethodBinding) methodBindingBinding);
                    if (inferenceContext == null) {
                        TypeBinding typeBinding2 = this.left.resolvedType;
                        if (typeBinding2 != null && typeBinding2.isValidBinding()) {
                            ConstraintTypeFormula constraintTypeFormulaCreate = ConstraintTypeFormula.create(typeBinding2, this.right, 1, this.isSoft);
                            inferenceContext18.resumeSuspendedInference(suspendedInferenceRecordEnterPolyInvocation);
                            return constraintTypeFormulaCreate;
                        }
                        ConstraintTypeFormula constraintTypeFormula = FALSE;
                        inferenceContext18.resumeSuspendedInference(suspendedInferenceRecordEnterPolyInvocation);
                        return constraintTypeFormula;
                    }
                    if (inferenceContext.stepCompleted < 1) {
                        ConstraintTypeFormula constraintTypeFormula2 = FALSE;
                        inferenceContext18.resumeSuspendedInference(suspendedInferenceRecordEnterPolyInvocation);
                        return constraintTypeFormula2;
                    }
                    inferenceContext18.integrateInnerInferenceB2(inferenceContext);
                } else {
                    inferenceContext18.inferenceKind = inferenceContext18.getInferenceKind(methodBindingBinding, typeBindingArr);
                    if (!methodBindingShallowOriginal.isConstructor() || !this.left.isPolyExpression(methodBindingShallowOriginal)) {
                        z = false;
                    }
                    inferInvocationApplicability(inferenceContext18, methodBindingShallowOriginal, typeBindingArr, z, inferenceContext18.inferenceKind);
                }
                if (inferPolyInvocationType(inferenceContext18, invocation, this.right, methodBindingShallowOriginal)) {
                    inferenceContext18.resumeSuspendedInference(suspendedInferenceRecordEnterPolyInvocation);
                    return null;
                }
                ConstraintTypeFormula constraintTypeFormula3 = FALSE;
                inferenceContext18.resumeSuspendedInference(suspendedInferenceRecordEnterPolyInvocation);
                return constraintTypeFormula3;
            } catch (Throwable th) {
                inferenceContext18.resumeSuspendedInference(suspendedInferenceRecordEnterPolyInvocation);
                throw th;
            }
        }
        if (typeIds instanceof ConditionalExpression) {
            ConditionalExpression conditionalExpression = (ConditionalExpression) typeIds;
            return new ConstraintFormula[]{new ConstraintExpressionFormula(conditionalExpression.valueIfTrue, this.right, this.relation, this.isSoft), new ConstraintExpressionFormula(conditionalExpression.valueIfFalse, this.right, this.relation, this.isSoft)};
        }
        if (!(typeIds instanceof LambdaExpression)) {
            return typeIds instanceof ReferenceExpression ? reduceReferenceExpressionCompatibility((ReferenceExpression) typeIds, inferenceContext18) : FALSE;
        }
        LambdaExpression lambdaExpression = (LambdaExpression) typeIds;
        BlockScope blockScope = lambdaExpression.enclosingScope;
        if (!this.right.isFunctionalInterface(blockScope)) {
            return FALSE;
        }
        ReferenceBinding referenceBindingFindGroundTargetType = (ReferenceBinding) this.right;
        ParameterizedTypeBinding parameterizedTypeBindingParameterizedWithWildcard = InferenceContext18.parameterizedWithWildcard(referenceBindingFindGroundTargetType);
        if (parameterizedTypeBindingParameterizedWithWildcard != null) {
            referenceBindingFindGroundTargetType = findGroundTargetType(inferenceContext18, blockScope, lambdaExpression, parameterizedTypeBindingParameterizedWithWildcard);
        }
        if (referenceBindingFindGroundTargetType == null) {
            return FALSE;
        }
        MethodBinding singleAbstractMethod = referenceBindingFindGroundTargetType.getSingleAbstractMethod(blockScope, true);
        if (singleAbstractMethod == null) {
            return FALSE;
        }
        TypeBinding[] typeBindingArr2 = singleAbstractMethod.parameters;
        if (typeBindingArr2.length != lambdaExpression.arguments().length) {
            return FALSE;
        }
        if (lambdaExpression.argumentsTypeElided()) {
            for (TypeBinding typeBinding3 : typeBindingArr2) {
                if (!typeBinding3.isProperType(true)) {
                    return FALSE;
                }
            }
        }
        LambdaExpression lambdaExpressionResolveExpressionExpecting = lambdaExpression.resolveExpressionExpecting((TypeBinding) referenceBindingFindGroundTargetType, inferenceContext18.scope, inferenceContext18);
        if (lambdaExpressionResolveExpressionExpecting == null) {
            return FALSE;
        }
        if (singleAbstractMethod.returnType == TypeBinding.VOID) {
            if (!lambdaExpressionResolveExpressionExpecting.isVoidCompatible()) {
                return FALSE;
            }
        } else if (!lambdaExpressionResolveExpressionExpecting.isValueCompatible()) {
            return FALSE;
        }
        ArrayList arrayList = new ArrayList();
        if (!lambdaExpressionResolveExpressionExpecting.argumentsTypeElided()) {
            Argument[] argumentArrArguments = lambdaExpressionResolveExpressionExpecting.arguments();
            for (int i2 = 0; i2 < typeBindingArr2.length; i2++) {
                arrayList.add(ConstraintTypeFormula.create(typeBindingArr2[i2], argumentArrArguments[i2].type.resolvedType, 4));
            }
            if (lambdaExpressionResolveExpressionExpecting.resolvedType != null) {
                arrayList.add(ConstraintTypeFormula.create(lambdaExpressionResolveExpressionExpecting.resolvedType, this.right, 2));
            }
        }
        if (singleAbstractMethod.returnType != TypeBinding.VOID) {
            TypeBinding typeBinding4 = singleAbstractMethod.returnType;
            Expression[] expressionArrResultExpressions = lambdaExpressionResolveExpressionExpecting.resultExpressions();
            int length = expressionArrResultExpressions == null ? 0 : expressionArrResultExpressions.length;
            for (int i3 = 0; i3 < length; i3++) {
                Expression expression2 = expressionArrResultExpressions[i3];
                if (!typeBinding4.isProperType(true) || expression2.resolvedType == null) {
                    arrayList.add(new ConstraintExpressionFormula(expression2, typeBinding4, 1, this.isSoft));
                } else {
                    TypeBinding typeBinding5 = expression2.resolvedType;
                    if (!expression2.isConstantValueOfTypeAssignableToType(typeBinding5, typeBinding4) && !typeBinding5.isCompatibleWith(typeBinding4) && !expression2.isBoxingCompatible(typeBinding5, typeBinding4, expression2, blockScope)) {
                        return FALSE;
                    }
                }
            }
        }
        return arrayList.size() == 0 ? TRUE : arrayList.toArray(new ConstraintFormula[arrayList.size()]);
    }

    public static ReferenceBinding findGroundTargetType(InferenceContext18 inferenceContext18, BlockScope blockScope, LambdaExpression lambdaExpression, ParameterizedTypeBinding parameterizedTypeBinding) {
        if (lambdaExpression.argumentsTypeElided()) {
            return lambdaExpression.findGroundTargetTypeForElidedLambda(blockScope, parameterizedTypeBinding);
        }
        InferenceContext18.SuspendedInferenceRecord suspendedInferenceRecordEnterLambda = inferenceContext18.enterLambda(lambdaExpression);
        try {
            return inferenceContext18.inferFunctionalInterfaceParameterization(lambdaExpression, blockScope, parameterizedTypeBinding);
        } finally {
            inferenceContext18.resumeSuspendedInference(suspendedInferenceRecordEnterLambda);
        }
    }

    private boolean canBePolyExpression(Expression expression) {
        ExpressionContext expressionContext = expression.getExpressionContext();
        if (expressionContext == ExpressionContext.VANILLA_CONTEXT) {
            this.left.setExpressionContext(ExpressionContext.ASSIGNMENT_CONTEXT);
        }
        try {
            return expression.isPolyExpression();
        } finally {
            expression.setExpressionContext(expressionContext);
        }
    }

    private Object reduceReferenceExpressionCompatibility(ReferenceExpression referenceExpression, InferenceContext18 inferenceContext18) {
        TypeBinding[] typeBindingArr;
        int i;
        TypeBinding typeBinding = this.right;
        if (typeBinding.isProperType(true)) {
            throw new IllegalStateException("Should not reach here with T being a proper type");
        }
        if (!typeBinding.isFunctionalInterface(inferenceContext18.scope)) {
            return FALSE;
        }
        MethodBinding singleAbstractMethod = typeBinding.getSingleAbstractMethod(inferenceContext18.scope, true);
        if (singleAbstractMethod == null) {
            return FALSE;
        }
        ReferenceExpression referenceExpressionResolveExpressionExpecting = referenceExpression.resolveExpressionExpecting(typeBinding, inferenceContext18.scope, inferenceContext18);
        MethodBinding methodBinding = referenceExpressionResolveExpressionExpecting != null ? referenceExpressionResolveExpressionExpecting.binding : null;
        if (methodBinding == null) {
            return FALSE;
        }
        if (referenceExpressionResolveExpressionExpecting.isExactMethodReference()) {
            ArrayList arrayList = new ArrayList();
            TypeBinding[] typeBindingArr2 = singleAbstractMethod.parameters;
            int length = typeBindingArr2.length;
            TypeBinding[] typeBindingArr3 = methodBinding.parameters;
            if (length == typeBindingArr3.length + 1) {
                arrayList.add(ConstraintTypeFormula.create(typeBindingArr2[0], referenceExpressionResolveExpressionExpecting.lhs.resolvedType, 1));
                i = 1;
            } else {
                i = 0;
            }
            for (int i2 = i; i2 < length; i2++) {
                arrayList.add(ConstraintTypeFormula.create(typeBindingArr2[i2], typeBindingArr3[i2 - i], 1));
            }
            TypeBinding typeBinding2 = singleAbstractMethod.returnType;
            if (typeBinding2 != TypeBinding.VOID) {
                TypeBinding typeBinding3 = (!methodBinding.isConstructor() || referenceExpressionResolveExpressionExpecting.isArrayConstructorReference()) ? methodBinding.returnType : methodBinding.declaringClass;
                if (typeBinding3 == TypeBinding.VOID) {
                    return FALSE;
                }
                arrayList.add(ConstraintTypeFormula.create(typeBinding3.capture(inferenceContext18.scope, referenceExpressionResolveExpressionExpecting.sourceStart, referenceExpressionResolveExpressionExpecting.sourceEnd), typeBinding2, 1));
            }
            return arrayList.toArray(new ConstraintFormula[arrayList.size()]);
        }
        int length2 = singleAbstractMethod.parameters.length;
        for (int i3 = 0; i3 < length2; i3++) {
            if (!singleAbstractMethod.parameters[i3].isProperType(true)) {
                return FALSE;
            }
        }
        if (!methodBinding.isValidBinding()) {
            return FALSE;
        }
        TypeBinding typeBinding4 = singleAbstractMethod.isConstructor() ? singleAbstractMethod.declaringClass : singleAbstractMethod.returnType;
        if (typeBinding4.f626id == 6) {
            return TRUE;
        }
        MethodBinding methodBindingShallowOriginal = methodBinding.shallowOriginal();
        TypeBinding typeBinding5 = methodBindingShallowOriginal.isConstructor() ? methodBindingShallowOriginal.declaringClass : methodBindingShallowOriginal.returnType;
        if (referenceExpressionResolveExpressionExpecting.typeArguments == null && ((methodBindingShallowOriginal.typeVariables() != Binding.NO_TYPE_VARIABLES && typeBinding5.mentionsAny(methodBindingShallowOriginal.typeVariables(), -1)) || (methodBindingShallowOriginal.isConstructor() && methodBinding.declaringClass.isRawType()))) {
            if (typeBinding.isParameterizedType()) {
                typeBindingArr = ((ParameterizedTypeBinding) typeBinding).getSingleAbstractMethod(inferenceContext18.scope, true, referenceExpressionResolveExpressionExpecting.sourceStart, referenceExpressionResolveExpressionExpecting.sourceEnd).parameters;
            } else {
                typeBindingArr = singleAbstractMethod.parameters;
            }
            InferenceContext18.SuspendedInferenceRecord suspendedInferenceRecordEnterPolyInvocation = inferenceContext18.enterPolyInvocation(referenceExpressionResolveExpressionExpecting, referenceExpressionResolveExpressionExpecting.createPseudoExpressions(typeBindingArr));
            try {
                try {
                    InferenceContext18 inferenceContext = referenceExpressionResolveExpressionExpecting.getInferenceContext((ParameterizedMethodBinding) methodBinding);
                    inferInvocationApplicability(inferenceContext18, methodBindingShallowOriginal, typeBindingArr, methodBindingShallowOriginal.isConstructor(), inferenceContext != null ? inferenceContext.inferenceKind : 1);
                    if (!inferPolyInvocationType(inferenceContext18, referenceExpressionResolveExpressionExpecting, typeBinding4, methodBindingShallowOriginal)) {
                        return FALSE;
                    }
                    if (!methodBindingShallowOriginal.isConstructor() || referenceExpressionResolveExpressionExpecting.receiverType.isRawType() || referenceExpressionResolveExpressionExpecting.receiverType.typeArguments() == null) {
                        return null;
                    }
                    inferenceContext18.resumeSuspendedInference(suspendedInferenceRecordEnterPolyInvocation);
                } catch (InferenceFailureException unused) {
                    return FALSE;
                }
            } finally {
                inferenceContext18.resumeSuspendedInference(suspendedInferenceRecordEnterPolyInvocation);
            }
        }
        TypeBinding typeBindingCapture = methodBinding.isConstructor() ? methodBinding.declaringClass : methodBinding.returnType.capture(inferenceContext18.scope, referenceExpressionResolveExpressionExpecting.sourceStart(), referenceExpressionResolveExpressionExpecting.sourceEnd());
        if (typeBindingCapture.f626id == 6) {
            return FALSE;
        }
        return ConstraintTypeFormula.create(typeBindingCapture, typeBinding4, 1, this.isSoft);
    }

    static void inferInvocationApplicability(InferenceContext18 inferenceContext18, MethodBinding methodBinding, TypeBinding[] typeBindingArr, boolean z, int i) {
        TypeBinding typeBinding;
        TypeVariableBinding[] allTypeVariables = methodBinding.getAllTypeVariables(z);
        InferenceVariable[] inferenceVariableArrCreateInitialBoundSet = inferenceContext18.createInitialBoundSet(allTypeVariables);
        int length = methodBinding.parameters.length;
        if (methodBinding.isVarargs()) {
            typeBinding = methodBinding.parameters[length - 1];
        } else {
            typeBinding = null;
        }
        inferenceContext18.createInitialConstraintsForParameters(methodBinding.parameters, i == 3, typeBinding, methodBinding);
        inferenceContext18.addThrowsContraints(allTypeVariables, inferenceVariableArrCreateInitialBoundSet, methodBinding.thrownExceptions);
    }

    static boolean inferPolyInvocationType(InferenceContext18 inferenceContext18, InvocationSite invocationSite, TypeBinding typeBinding, MethodBinding methodBinding) throws InferenceFailureException {
        if (invocationSite.genericTypeArguments() == null) {
            TypeBinding typeBinding2 = methodBinding.isConstructor() ? methodBinding.declaringClass : methodBinding.returnType;
            if (typeBinding2 == TypeBinding.VOID) {
                throw new InferenceFailureException("expression has no value");
            }
            if (inferenceContext18.usesUncheckedConversion) {
                return inferenceContext18.reduceAndIncorporate(ConstraintTypeFormula.create(inferenceContext18.environment.convertToRawType(typeBinding2, false), typeBinding, 1));
            }
            TypeBinding typeBindingSubstitute = inferenceContext18.substitute(typeBinding2);
            ParameterizedTypeBinding parameterizedTypeBindingParameterizedWithWildcard = InferenceContext18.parameterizedWithWildcard(typeBindingSubstitute);
            if (parameterizedTypeBindingParameterizedWithWildcard != null && parameterizedTypeBindingParameterizedWithWildcard.arguments != null) {
                InferenceVariable[] inferenceVariableArrAddTypeVariableSubstitutions = inferenceContext18.addTypeVariableSubstitutions(parameterizedTypeBindingParameterizedWithWildcard.arguments);
                ParameterizedTypeBinding parameterizedTypeBindingCreateParameterizedType = inferenceContext18.environment.createParameterizedType(parameterizedTypeBindingParameterizedWithWildcard.genericType(), inferenceVariableArrAddTypeVariableSubstitutions, parameterizedTypeBindingParameterizedWithWildcard.enclosingType(), parameterizedTypeBindingParameterizedWithWildcard.getTypeAnnotations());
                inferenceContext18.currentBounds.captures.put(parameterizedTypeBindingCreateParameterizedType, parameterizedTypeBindingParameterizedWithWildcard);
                TypeBinding[] typeBindingArr = parameterizedTypeBindingParameterizedWithWildcard.capture(inferenceContext18.scope, invocationSite.sourceStart(), invocationSite.sourceEnd()).arguments;
                int length = typeBindingArr.length;
                for (int i = 0; i < length; i++) {
                    if (typeBindingArr[i].isCapture() && typeBindingArr[i].isProperType(true)) {
                        inferenceContext18.currentBounds.addBound(new TypeBound(inferenceVariableArrAddTypeVariableSubstitutions[i], (CaptureBinding) typeBindingArr[i], 4), inferenceContext18.environment);
                    }
                }
                return inferenceContext18.reduceAndIncorporate(ConstraintTypeFormula.create(parameterizedTypeBindingCreateParameterizedType, typeBinding, 1));
            }
            if (typeBindingSubstitute.leafComponentType() instanceof InferenceVariable) {
                InferenceVariable inferenceVariable = (InferenceVariable) typeBindingSubstitute.leafComponentType();
                TypeBinding typeBindingLeafComponentType = typeBinding.leafComponentType();
                if (inferenceContext18.currentBounds.condition18_5_2_bullet_3_3_1(inferenceVariable, typeBindingLeafComponentType) || inferenceContext18.currentBounds.condition18_5_2_bullet_3_3_2(inferenceVariable, typeBindingLeafComponentType, inferenceContext18) || (typeBindingLeafComponentType.isPrimitiveType() && inferenceContext18.currentBounds.findWrapperTypeBound(inferenceVariable) != null)) {
                    BoundSet boundSetSolve = inferenceContext18.solve(new InferenceVariable[]{inferenceVariable});
                    if (boundSetSolve == null) {
                        return false;
                    }
                    TypeBinding typeBindingCapture = boundSetSolve.getInstantiation(inferenceVariable, null).capture(inferenceContext18.scope, invocationSite.sourceStart(), invocationSite.sourceEnd());
                    if (typeBindingSubstitute.dimensions() != 0) {
                        typeBindingCapture = inferenceContext18.environment.createArrayType(typeBindingCapture, typeBindingSubstitute.dimensions());
                    }
                    return inferenceContext18.reduceAndIncorporate(ConstraintTypeFormula.create(typeBindingCapture, typeBinding, 1));
                }
            }
            if (!inferenceContext18.reduceAndIncorporate(ConstraintTypeFormula.create(typeBindingSubstitute, typeBinding, 1))) {
                return false;
            }
        }
        return true;
    }

    @Override // org.eclipse.jdt.internal.compiler.lookup.ConstraintFormula
    Collection<InferenceVariable> inputVariables(InferenceContext18 inferenceContext18) {
        Expression expression = this.left;
        int i = 0;
        if (expression instanceof LambdaExpression) {
            if (this.right instanceof InferenceVariable) {
                return Collections.singletonList((InferenceVariable) this.right);
            }
            if (this.right.isFunctionalInterface(inferenceContext18.scope)) {
                LambdaExpression lambdaExpression = (LambdaExpression) this.left;
                MethodBinding singleAbstractMethod = this.right.getSingleAbstractMethod(inferenceContext18.scope, true);
                HashSet hashSet = new HashSet();
                if (lambdaExpression.argumentsTypeElided()) {
                    int length = singleAbstractMethod.parameters.length;
                    for (int i2 = 0; i2 < length; i2++) {
                        singleAbstractMethod.parameters[i2].collectInferenceVariables(hashSet);
                    }
                }
                if (singleAbstractMethod.returnType != TypeBinding.VOID) {
                    TypeBinding typeBinding = singleAbstractMethod.returnType;
                    LambdaExpression lambdaExpressionResolveExpressionExpecting = lambdaExpression.resolveExpressionExpecting(this.right, inferenceContext18.scope, inferenceContext18);
                    Expression[] expressionArrResultExpressions = lambdaExpressionResolveExpressionExpecting != null ? lambdaExpressionResolveExpressionExpecting.resultExpressions() : null;
                    int length2 = expressionArrResultExpressions == null ? 0 : expressionArrResultExpressions.length;
                    while (i < length2) {
                        hashSet.addAll(new ConstraintExpressionFormula(expressionArrResultExpressions[i], typeBinding, 1).inputVariables(inferenceContext18));
                        i++;
                    }
                }
                return hashSet;
            }
        } else if (expression instanceof ReferenceExpression) {
            if (this.right instanceof InferenceVariable) {
                return Collections.singletonList((InferenceVariable) this.right);
            }
            if (this.right.isFunctionalInterface(inferenceContext18.scope) && !this.left.isExactMethodReference()) {
                MethodBinding singleAbstractMethod2 = this.right.getSingleAbstractMethod(inferenceContext18.scope, true);
                HashSet hashSet2 = new HashSet();
                int length3 = singleAbstractMethod2.parameters.length;
                while (i < length3) {
                    singleAbstractMethod2.parameters[i].collectInferenceVariables(hashSet2);
                    i++;
                }
                return hashSet2;
            }
        } else if ((expression instanceof ConditionalExpression) && expression.isPolyExpression()) {
            ConditionalExpression conditionalExpression = (ConditionalExpression) this.left;
            HashSet hashSet3 = new HashSet();
            hashSet3.addAll(new ConstraintExpressionFormula(conditionalExpression.valueIfTrue, this.right, 1).inputVariables(inferenceContext18));
            hashSet3.addAll(new ConstraintExpressionFormula(conditionalExpression.valueIfFalse, this.right, 1).inputVariables(inferenceContext18));
            return hashSet3;
        }
        return EMPTY_VARIABLE_LIST;
    }

    public String toString() {
        StringBuffer stringBufferAppend = new StringBuffer().append((char) 10216);
        this.left.printExpression(4, stringBufferAppend);
        stringBufferAppend.append(relationToString(this.relation));
        appendTypeName(stringBufferAppend, this.right);
        stringBufferAppend.append((char) 10217);
        return stringBufferAppend.toString();
    }
}
