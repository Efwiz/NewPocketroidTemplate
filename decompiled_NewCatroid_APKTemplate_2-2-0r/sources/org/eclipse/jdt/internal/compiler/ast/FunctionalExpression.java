package org.eclipse.jdt.internal.compiler.ast;

import org.eclipse.jdt.internal.compiler.CompilationResult;
import org.eclipse.jdt.internal.compiler.DefaultErrorHandlingPolicies;
import org.eclipse.jdt.internal.compiler.IErrorHandlingPolicy;
import org.eclipse.jdt.internal.compiler.flow.FlowInfo;
import org.eclipse.jdt.internal.compiler.impl.Constant;
import org.eclipse.jdt.internal.compiler.impl.ReferenceContext;
import org.eclipse.jdt.internal.compiler.lookup.BlockScope;
import org.eclipse.jdt.internal.compiler.lookup.CompilationUnitScope;
import org.eclipse.jdt.internal.compiler.lookup.ImplicitNullAnnotationVerifier;
import org.eclipse.jdt.internal.compiler.lookup.IntersectionTypeBinding18;
import org.eclipse.jdt.internal.compiler.lookup.LookupEnvironment;
import org.eclipse.jdt.internal.compiler.lookup.MethodBinding;
import org.eclipse.jdt.internal.compiler.lookup.MethodScope;
import org.eclipse.jdt.internal.compiler.lookup.MethodVerifier;
import org.eclipse.jdt.internal.compiler.lookup.ParameterizedTypeBinding;
import org.eclipse.jdt.internal.compiler.lookup.RawTypeBinding;
import org.eclipse.jdt.internal.compiler.lookup.ReferenceBinding;
import org.eclipse.jdt.internal.compiler.lookup.Scope;
import org.eclipse.jdt.internal.compiler.lookup.TypeBinding;
import org.eclipse.jdt.internal.compiler.lookup.TypeBindingVisitor;
import org.eclipse.jdt.internal.compiler.lookup.TypeVariableBinding;

/* JADX INFO: loaded from: classes14.dex */
public abstract class FunctionalExpression extends Expression {
    protected static IErrorHandlingPolicy silentErrorHandlingPolicy = DefaultErrorHandlingPolicies.ignoreAllProblems();
    protected MethodBinding actualMethodBinding;
    public MethodBinding binding;
    public CompilationResult compilationResult;
    public MethodBinding descriptor;
    public BlockScope enclosingScope;
    protected TypeBinding expectedType;
    boolean ignoreFurtherInvestigation;
    public boolean isSerializable;
    protected ExpressionContext expressionContext = ExpressionContext.VANILLA_CONTEXT;
    public int bootstrapMethodNumber = -1;
    public boolean shouldCaptureInstance = false;
    private boolean hasReportedSamProblem = false;

    public boolean argumentsTypeElided() {
        return true;
    }

    public MethodBinding getMethodBinding() {
        return null;
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.Expression, org.eclipse.jdt.internal.compiler.ast.IPolyExpression
    public boolean isBoxingCompatibleWith(TypeBinding typeBinding, Scope scope) {
        return false;
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.Expression, org.eclipse.jdt.internal.compiler.ast.IPolyExpression
    public boolean isFunctionalType() {
        return true;
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.Expression, org.eclipse.jdt.internal.compiler.ast.IPolyExpression
    public boolean isPolyExpression() {
        return true;
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.Expression, org.eclipse.jdt.internal.compiler.ast.IPolyExpression
    public boolean isPolyExpression(MethodBinding methodBinding) {
        return true;
    }

    public int nullStatus(FlowInfo flowInfo) {
        return 4;
    }

    public FunctionalExpression(CompilationResult compilationResult) {
        this.compilationResult = compilationResult;
    }

    public FunctionalExpression() {
    }

    public void setCompilationResult(CompilationResult compilationResult) {
        this.compilationResult = compilationResult;
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
    public boolean isPertinentToApplicability(TypeBinding typeBinding, MethodBinding methodBinding) {
        if (!(typeBinding instanceof TypeVariableBinding)) {
            return true;
        }
        TypeVariableBinding typeVariableBinding = (TypeVariableBinding) typeBinding;
        if (methodBinding == null) {
            return !(typeVariableBinding.declaringElement instanceof MethodBinding);
        }
        if (typeVariableBinding.declaringElement == methodBinding) {
            return false;
        }
        return (methodBinding.isConstructor() && typeVariableBinding.declaringElement == methodBinding.declaringClass) ? false : true;
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.Statement, org.eclipse.jdt.internal.compiler.ast.IPolyExpression, org.eclipse.jdt.internal.compiler.lookup.InvocationSite
    public TypeBinding invocationTargetType() {
        MethodBinding singleAbstractMethod;
        TypeBinding typeBinding = this.expectedType;
        if (typeBinding == null || (singleAbstractMethod = typeBinding.getSingleAbstractMethod(this.enclosingScope, true)) == null || singleAbstractMethod.problemId() == 17) {
            return null;
        }
        if (singleAbstractMethod.isConstructor()) {
            return singleAbstractMethod.declaringClass;
        }
        return singleAbstractMethod.returnType;
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.Statement
    public TypeBinding expectedType() {
        return this.expectedType;
    }

    public int recordFunctionalType(Scope scope) {
        while (scope != null) {
            int i = scope.kind;
            if (i == 2) {
                ReferenceContext referenceContext = ((MethodScope) scope).referenceContext;
                if (referenceContext instanceof LambdaExpression) {
                    LambdaExpression lambdaExpression = (LambdaExpression) referenceContext;
                    if (lambdaExpression != lambdaExpression.original) {
                        return 0;
                    }
                } else {
                    continue;
                }
            } else if (i == 4) {
                return ((CompilationUnitScope) scope).referenceContext.record(this);
            }
            scope = scope.parent;
        }
        return 0;
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.Expression
    public TypeBinding resolveType(BlockScope blockScope) {
        return resolveType(blockScope, false);
    }

    public TypeBinding resolveType(BlockScope blockScope, boolean z) {
        this.constant = Constant.NotAConstant;
        this.enclosingScope = blockScope;
        TypeBinding typeBinding = this.expectedType;
        MethodBinding singleAbstractMethod = typeBinding == null ? null : typeBinding.getSingleAbstractMethod(blockScope, argumentsTypeElided());
        if (singleAbstractMethod == null) {
            blockScope.problemReporter().targetTypeIsNotAFunctionalInterface(this);
            return null;
        }
        if (!singleAbstractMethod.isValidBinding()) {
            return reportSamProblem(blockScope, singleAbstractMethod);
        }
        this.descriptor = singleAbstractMethod;
        if (z || kosherDescriptor(blockScope, singleAbstractMethod, true)) {
            TypeBinding typeBinding2 = this.expectedType;
            if (typeBinding2 instanceof IntersectionTypeBinding18) {
                ReferenceBinding[] referenceBindingArr = ((IntersectionTypeBinding18) typeBinding2).intersectingTypes;
                for (ReferenceBinding referenceBinding : referenceBindingArr) {
                    if (referenceBinding.findSuperTypeOriginatingFrom(37, false) != null) {
                        this.isSerializable = true;
                        break;
                    }
                }
            } else if (typeBinding2.findSuperTypeOriginatingFrom(37, false) != null) {
                this.isSerializable = true;
            }
            LookupEnvironment lookupEnvironmentEnvironment = blockScope.environment();
            if (lookupEnvironmentEnvironment.globalOptions.isAnnotationBasedNullAnalysisEnabled) {
                if ((singleAbstractMethod.tagBits & 4096) == 0) {
                    new ImplicitNullAnnotationVerifier(lookupEnvironmentEnvironment, lookupEnvironmentEnvironment.globalOptions.inheritNullAnnotations).checkImplicitNullAnnotations(singleAbstractMethod, null, false, blockScope);
                }
                NullAnnotationMatching.checkForContradictions(singleAbstractMethod, this, blockScope);
            }
            TypeBinding typeBinding3 = this.expectedType;
            this.resolvedType = typeBinding3;
            return typeBinding3;
        }
        this.resolvedType = null;
        return null;
    }

    protected TypeBinding reportSamProblem(BlockScope blockScope, MethodBinding methodBinding) {
        if (this.hasReportedSamProblem) {
            return null;
        }
        int iProblemId = methodBinding.problemId();
        if (iProblemId == 17) {
            blockScope.problemReporter().targetTypeIsNotAFunctionalInterface(this);
            this.hasReportedSamProblem = true;
        } else if (iProblemId == 18) {
            blockScope.problemReporter().illFormedParameterizationOfFunctionalInterface(this);
            this.hasReportedSamProblem = true;
        }
        return null;
    }

    class VisibilityInspector extends TypeBindingVisitor {
        private FunctionalExpression expression;
        private Scope scope;
        private boolean shouldChatter;
        private boolean visible = true;

        public VisibilityInspector(FunctionalExpression functionalExpression, Scope scope, boolean z) {
            this.scope = scope;
            this.shouldChatter = z;
            this.expression = functionalExpression;
        }

        private void checkVisibility(ReferenceBinding referenceBinding) {
            if (referenceBinding.canBeSeenBy(this.scope)) {
                return;
            }
            this.visible = false;
            if (this.shouldChatter) {
                this.scope.problemReporter().descriptorHasInvisibleType(this.expression, referenceBinding);
            }
        }

        @Override // org.eclipse.jdt.internal.compiler.lookup.TypeBindingVisitor
        public boolean visit(ReferenceBinding referenceBinding) {
            checkVisibility(referenceBinding);
            return true;
        }

        @Override // org.eclipse.jdt.internal.compiler.lookup.TypeBindingVisitor
        public boolean visit(ParameterizedTypeBinding parameterizedTypeBinding) {
            checkVisibility(parameterizedTypeBinding);
            return true;
        }

        @Override // org.eclipse.jdt.internal.compiler.lookup.TypeBindingVisitor
        public boolean visit(RawTypeBinding rawTypeBinding) {
            checkVisibility(rawTypeBinding);
            return true;
        }

        public boolean visible(TypeBinding typeBinding) {
            TypeBindingVisitor.visit(this, typeBinding);
            return this.visible;
        }

        public boolean visible(TypeBinding[] typeBindingArr) {
            TypeBindingVisitor.visit(this, typeBindingArr);
            return this.visible;
        }
    }

    public boolean kosherDescriptor(Scope scope, MethodBinding methodBinding, boolean z) {
        VisibilityInspector visibilityInspector = new VisibilityInspector(this, scope, z);
        boolean zVisible = visibilityInspector.visible(methodBinding.returnType);
        if (!visibilityInspector.visible(methodBinding.parameters)) {
            zVisible = false;
        }
        if (!visibilityInspector.visible(methodBinding.thrownExceptions)) {
            zVisible = false;
        }
        if (visibilityInspector.visible(this.expectedType)) {
            return zVisible;
        }
        return false;
    }

    public int diagnosticsSourceEnd() {
        return this.sourceEnd;
    }

    /* JADX WARN: Type inference failed for: r1v1, types: [org.eclipse.jdt.internal.compiler.ast.FunctionalExpression$1BridgeCollector] */
    public MethodBinding[] getRequiredBridges() {
        ReferenceBinding referenceBinding;
        TypeBinding typeBinding = this.expectedType;
        if (typeBinding instanceof IntersectionTypeBinding18) {
            referenceBinding = (ReferenceBinding) ((IntersectionTypeBinding18) typeBinding).getSAMType(this.enclosingScope);
        } else {
            referenceBinding = (ReferenceBinding) typeBinding;
        }
        return new Object(referenceBinding, this.descriptor) { // from class: org.eclipse.jdt.internal.compiler.ast.FunctionalExpression.1BridgeCollector
            MethodBinding[] bridges;
            LookupEnvironment environment;
            MethodBinding method;
            Scope scope;
            char[] selector;

            {
                this.method = methodBinding;
                this.selector = methodBinding.selector;
                this.environment = FunctionalExpression.this.enclosingScope.environment();
                this.scope = FunctionalExpression.this.enclosingScope;
                collectBridges(new ReferenceBinding[]{referenceBinding});
            }

            void collectBridges(ReferenceBinding[] referenceBindingArr) {
                MethodBinding methodBindingComputeSubstituteMethod;
                int length = referenceBindingArr == null ? 0 : referenceBindingArr.length;
                for (int i = 0; i < length; i++) {
                    ReferenceBinding referenceBinding2 = referenceBindingArr[i];
                    if (referenceBinding2 != null) {
                        MethodBinding[] methods = referenceBinding2.getMethods(this.selector);
                        int length2 = methods == null ? 0 : methods.length;
                        for (int i2 = 0; i2 < length2; i2++) {
                            MethodBinding methodBinding = methods[i2];
                            if (methodBinding != null && this.method != methodBinding && !methodBinding.isStatic() && !methodBinding.redeclaresPublicObjectMethod(this.scope) && (methodBindingComputeSubstituteMethod = MethodVerifier.computeSubstituteMethod(methodBinding, this.method, this.environment)) != null && MethodVerifier.isSubstituteParameterSubsignature(this.method, methodBindingComputeSubstituteMethod, this.environment) && MethodVerifier.areReturnTypesCompatible(this.method, methodBindingComputeSubstituteMethod, this.environment)) {
                                MethodBinding methodBindingOriginal = methodBindingComputeSubstituteMethod.original();
                                MethodBinding methodBindingOriginal2 = this.method.original();
                                if (!methodBindingOriginal2.areParameterErasuresEqual(methodBindingOriginal) || TypeBinding.notEquals(methodBindingOriginal2.returnType.erasure(), methodBindingOriginal.returnType.erasure())) {
                                    add(methodBindingOriginal);
                                }
                            }
                        }
                        collectBridges(referenceBinding2.superInterfaces());
                    }
                }
            }

            void add(MethodBinding methodBinding) {
                MethodBinding[] methodBindingArr = this.bridges;
                if (methodBindingArr == null) {
                    this.bridges = new MethodBinding[]{methodBinding};
                    return;
                }
                int length = methodBindingArr.length;
                for (int i = 0; i < length; i++) {
                    if (this.bridges[i].areParameterErasuresEqual(methodBinding) && TypeBinding.equalsEquals(this.bridges[i].returnType.erasure(), methodBinding.returnType.erasure())) {
                        return;
                    }
                }
                MethodBinding[] methodBindingArr2 = this.bridges;
                MethodBinding[] methodBindingArr3 = new MethodBinding[length + 1];
                this.bridges = methodBindingArr3;
                System.arraycopy(methodBindingArr2, 0, methodBindingArr3, 0, length);
                this.bridges[length] = methodBinding;
            }

            MethodBinding[] getBridges() {
                return this.bridges;
            }
        }.getBridges();
    }

    boolean requiresBridges() {
        return getRequiredBridges() != null;
    }
}
