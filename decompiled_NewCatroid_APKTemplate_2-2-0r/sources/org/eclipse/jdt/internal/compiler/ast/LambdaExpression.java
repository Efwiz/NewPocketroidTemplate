package org.eclipse.jdt.internal.compiler.ast;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;
import org.eclipse.jdt.core.compiler.CategorizedProblem;
import org.eclipse.jdt.core.compiler.CharOperation;
import org.eclipse.jdt.core.compiler.IProblem;
import org.eclipse.jdt.internal.compiler.ASTVisitor;
import org.eclipse.jdt.internal.compiler.ClassFile;
import org.eclipse.jdt.internal.compiler.CompilationResult;
import org.eclipse.jdt.internal.compiler.IErrorHandlingPolicy;
import org.eclipse.jdt.internal.compiler.codegen.CodeStream;
import org.eclipse.jdt.internal.compiler.env.ICompilationUnit;
import org.eclipse.jdt.internal.compiler.flow.ExceptionHandlingFlowContext;
import org.eclipse.jdt.internal.compiler.flow.ExceptionInferenceFlowContext;
import org.eclipse.jdt.internal.compiler.flow.FlowContext;
import org.eclipse.jdt.internal.compiler.flow.FlowInfo;
import org.eclipse.jdt.internal.compiler.flow.UnconditionalFlowInfo;
import org.eclipse.jdt.internal.compiler.impl.Constant;
import org.eclipse.jdt.internal.compiler.impl.ReferenceContext;
import org.eclipse.jdt.internal.compiler.lookup.AnnotationBinding;
import org.eclipse.jdt.internal.compiler.lookup.Binding;
import org.eclipse.jdt.internal.compiler.lookup.BlockScope;
import org.eclipse.jdt.internal.compiler.lookup.ClassScope;
import org.eclipse.jdt.internal.compiler.lookup.InferenceContext18;
import org.eclipse.jdt.internal.compiler.lookup.IntersectionTypeBinding18;
import org.eclipse.jdt.internal.compiler.lookup.LocalVariableBinding;
import org.eclipse.jdt.internal.compiler.lookup.LookupEnvironment;
import org.eclipse.jdt.internal.compiler.lookup.MethodBinding;
import org.eclipse.jdt.internal.compiler.lookup.MethodScope;
import org.eclipse.jdt.internal.compiler.lookup.ParameterizedTypeBinding;
import org.eclipse.jdt.internal.compiler.lookup.PolyTypeBinding;
import org.eclipse.jdt.internal.compiler.lookup.ProblemMethodBinding;
import org.eclipse.jdt.internal.compiler.lookup.ReferenceBinding;
import org.eclipse.jdt.internal.compiler.lookup.Scope;
import org.eclipse.jdt.internal.compiler.lookup.SourceTypeBinding;
import org.eclipse.jdt.internal.compiler.lookup.SyntheticArgumentBinding;
import org.eclipse.jdt.internal.compiler.lookup.SyntheticMethodBinding;
import org.eclipse.jdt.internal.compiler.lookup.TagBits;
import org.eclipse.jdt.internal.compiler.lookup.TypeBinding;
import org.eclipse.jdt.internal.compiler.lookup.TypeConstants;
import org.eclipse.jdt.internal.compiler.parser.Parser;
import org.eclipse.jdt.internal.compiler.problem.AbortCompilation;
import org.eclipse.jdt.internal.compiler.problem.AbortCompilationUnit;
import org.eclipse.jdt.internal.compiler.problem.AbortMethod;
import org.eclipse.jdt.internal.compiler.problem.AbortType;
import org.eclipse.jdt.internal.compiler.problem.ProblemReporter;
import org.eclipse.jdt.internal.compiler.problem.ProblemSeverities;

/* JADX INFO: loaded from: classes14.dex */
public class LambdaExpression extends FunctionalExpression implements IPolyExpression, ReferenceContext, ProblemSeverities {
    private TypeBinding[] argumentTypes;
    public Argument[] arguments;
    public int arrowPosition;
    private boolean assistNode;
    public Statement body;
    private ReferenceBinding classType;
    private HashMap<TypeBinding, LambdaExpression> copiesPerTargetType;
    private boolean hasIgnoredMandatoryErrors;
    public boolean hasParentheses;
    public InferenceContext18 inferenceContext;
    public int ordinal;
    public LambdaExpression original;
    public SyntheticArgumentBinding[] outerLocalVariables;
    private int outerLocalVariablesSlotSize;
    private boolean requiresGenericSignature;
    protected Expression[] resultExpressions;
    boolean returnsValue;
    boolean returnsVoid;
    public MethodScope scope;
    public char[] text;
    private Set thrownExceptions;
    boolean valueCompatible;
    boolean voidCompatible;
    private static final SyntheticArgumentBinding[] NO_SYNTHETIC_ARGUMENTS = new SyntheticArgumentBinding[0];
    private static final Block NO_BODY = new Block(0);

    public LambdaExpression(CompilationResult compilationResult, boolean z, boolean z2) {
        super(compilationResult);
        this.voidCompatible = true;
        this.valueCompatible = false;
        this.original = this;
        this.outerLocalVariables = NO_SYNTHETIC_ARGUMENTS;
        this.outerLocalVariablesSlotSize = 0;
        this.assistNode = false;
        this.hasIgnoredMandatoryErrors = false;
        this.resultExpressions = NO_EXPRESSIONS;
        this.assistNode = z;
        this.requiresGenericSignature = z2;
        setArguments(NO_ARGUMENTS);
        setBody(NO_BODY);
    }

    public LambdaExpression(CompilationResult compilationResult, boolean z) {
        this(compilationResult, z, false);
    }

    public void setArguments(Argument[] argumentArr) {
        this.arguments = argumentArr != null ? argumentArr : ASTNode.NO_ARGUMENTS;
        this.argumentTypes = new TypeBinding[argumentArr != null ? argumentArr.length : 0];
    }

    public Argument[] arguments() {
        return this.arguments;
    }

    public TypeBinding[] argumentTypes() {
        return this.argumentTypes;
    }

    public void setBody(Statement statement) {
        if (statement == null) {
            statement = NO_BODY;
        }
        this.body = statement;
    }

    public Statement body() {
        return this.body;
    }

    public Expression[] resultExpressions() {
        return this.resultExpressions;
    }

    public void setArrowPosition(int i) {
        this.arrowPosition = i;
    }

    public int arrowPosition() {
        return this.arrowPosition;
    }

    protected FunctionalExpression original() {
        return this.original;
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.Expression
    public void generateCode(BlockScope blockScope, CodeStream codeStream, boolean z) {
        if (this.shouldCaptureInstance) {
            this.binding.modifiers &= -9;
        } else {
            this.binding.modifiers |= 8;
        }
        SourceTypeBinding sourceTypeBindingEnclosingSourceType = blockScope.enclosingSourceType();
        boolean z2 = this.binding instanceof SyntheticMethodBinding;
        this.binding = sourceTypeBindingEnclosingSourceType.addSyntheticMethod(this);
        int i = codeStream.position;
        StringBuffer stringBuffer = new StringBuffer("(");
        if (this.shouldCaptureInstance) {
            codeStream.aload_0();
            stringBuffer.append(sourceTypeBindingEnclosingSourceType.signature());
        }
        SyntheticArgumentBinding[] syntheticArgumentBindingArr = this.outerLocalVariables;
        int length = syntheticArgumentBindingArr == null ? 0 : syntheticArgumentBindingArr.length;
        for (int i2 = 0; i2 < length; i2++) {
            SyntheticArgumentBinding syntheticArgumentBinding = this.outerLocalVariables[i2];
            if (this.shouldCaptureInstance && !z2) {
                syntheticArgumentBinding.resolvedPosition = syntheticArgumentBinding.resolvedPosition + 1;
            }
            stringBuffer.append(syntheticArgumentBinding.type.signature());
            LocalVariableBinding localVariableBinding = syntheticArgumentBinding.actualOuterLocalVariable;
            codeStream.generateOuterAccess(blockScope.getEmulationPath(localVariableBinding), this, localVariableBinding, blockScope);
        }
        stringBuffer.append(')');
        if (this.expectedType instanceof IntersectionTypeBinding18) {
            stringBuffer.append(((IntersectionTypeBinding18) this.expectedType).getSAMType(blockScope).signature());
        } else {
            stringBuffer.append(this.expectedType.signature());
        }
        codeStream.invokeDynamic(codeStream.classFile.recordBootstrapMethod(this), (this.shouldCaptureInstance ? 1 : 0) + this.outerLocalVariablesSlotSize, 1, this.descriptor.selector, stringBuffer.toString().toCharArray());
        if (!z) {
            codeStream.pop();
        }
        codeStream.recordPositionsFrom(i, this.sourceStart);
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.FunctionalExpression
    public boolean kosherDescriptor(Scope scope, MethodBinding methodBinding, boolean z) {
        if (methodBinding.typeVariables == Binding.NO_TYPE_VARIABLES) {
            return super.kosherDescriptor(scope, methodBinding, z);
        }
        if (!z) {
            return false;
        }
        scope.problemReporter().lambdaExpressionCannotImplementGenericMethod(this, methodBinding);
        return false;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r10v19 */
    /* JADX WARN: Type inference failed for: r10v22, types: [org.eclipse.jdt.internal.compiler.lookup.TypeBinding] */
    /* JADX WARN: Type inference failed for: r10v28 */
    /* JADX WARN: Type inference failed for: r10v29 */
    /* JADX WARN: Type inference failed for: r15v1 */
    /* JADX WARN: Type inference failed for: r15v10 */
    /* JADX WARN: Type inference failed for: r15v11 */
    /* JADX WARN: Type inference failed for: r15v12 */
    /* JADX WARN: Type inference failed for: r15v2, types: [org.eclipse.jdt.internal.compiler.lookup.AnnotationBinding[][]] */
    /* JADX WARN: Type inference failed for: r15v4 */
    /* JADX WARN: Type inference failed for: r15v5 */
    /* JADX WARN: Type inference failed for: r15v6 */
    /* JADX WARN: Type inference failed for: r15v7 */
    /* JADX WARN: Type inference failed for: r15v8, types: [org.eclipse.jdt.internal.compiler.lookup.ReferenceBinding, org.eclipse.jdt.internal.compiler.lookup.TypeBinding] */
    /* JADX WARN: Type inference failed for: r1v20, types: [org.eclipse.jdt.internal.compiler.problem.ProblemReporter] */
    /* JADX WARN: Type inference failed for: r20v0, types: [org.eclipse.jdt.internal.compiler.ast.ASTNode, org.eclipse.jdt.internal.compiler.ast.Expression, org.eclipse.jdt.internal.compiler.ast.FunctionalExpression, org.eclipse.jdt.internal.compiler.ast.LambdaExpression, org.eclipse.jdt.internal.compiler.impl.ReferenceContext] */
    /* JADX WARN: Type inference failed for: r3v29, types: [org.eclipse.jdt.internal.compiler.lookup.MethodBinding] */
    /* JADX WARN: Type inference failed for: r6v10 */
    /* JADX WARN: Type inference failed for: r6v15 */
    /* JADX WARN: Type inference failed for: r6v2 */
    /* JADX WARN: Type inference failed for: r6v20 */
    /* JADX WARN: Type inference failed for: r6v21 */
    /* JADX WARN: Type inference failed for: r6v22, types: [org.eclipse.jdt.internal.compiler.lookup.TypeBinding] */
    /* JADX WARN: Type inference failed for: r6v25 */
    /* JADX WARN: Type inference failed for: r6v26, types: [org.eclipse.jdt.internal.compiler.lookup.TypeBinding[]] */
    /* JADX WARN: Type inference failed for: r6v27 */
    /* JADX WARN: Type inference failed for: r6v3, types: [org.eclipse.jdt.internal.compiler.ast.ASTNode, org.eclipse.jdt.internal.compiler.lookup.TypeBinding] */
    /* JADX WARN: Type inference failed for: r6v4 */
    /* JADX WARN: Type inference failed for: r6v40 */
    /* JADX WARN: Type inference failed for: r6v41 */
    /* JADX WARN: Type inference failed for: r6v42 */
    /* JADX WARN: Type inference failed for: r6v5, types: [org.eclipse.jdt.internal.compiler.lookup.TypeBinding] */
    /* JADX WARN: Type inference failed for: r7v23 */
    /* JADX WARN: Type inference fix 'apply assigned field type' failed
    java.lang.UnsupportedOperationException: ArgType.getObject(), call class: class jadx.core.dex.instructions.args.ArgType$UnknownArg
    	at jadx.core.dex.instructions.args.ArgType.getObject(ArgType.java:596)
    	at jadx.core.dex.attributes.nodes.ClassTypeVarsAttr.getTypeVarsMapFor(ClassTypeVarsAttr.java:35)
    	at jadx.core.dex.nodes.utils.TypeUtils.replaceClassGenerics(TypeUtils.java:177)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.insertExplicitUseCast(FixTypesVisitor.java:397)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.tryFieldTypeWithNewCasts(FixTypesVisitor.java:359)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.applyFieldType(FixTypesVisitor.java:309)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.visit(FixTypesVisitor.java:94)
     */
    @Override // org.eclipse.jdt.internal.compiler.ast.FunctionalExpression
    public TypeBinding resolveType(BlockScope blockScope, boolean z) {
        boolean z2;
        long j;
        long j2;
        Object obj;
        ?? r10;
        ReferenceBinding referenceBinding;
        ?? r15;
        ReferenceBinding referenceBindingFindGroundTargetType;
        ReferenceBinding referenceBinding2;
        ?? r6;
        TypeBinding typeBinding;
        boolean zArgumentsTypeElided = argumentsTypeElided();
        Argument[] argumentArr = this.arguments;
        int length = argumentArr == null ? 0 : argumentArr.length;
        boolean z3 = true;
        if (this.constant != Constant.NotAConstant) {
            this.constant = Constant.NotAConstant;
            this.enclosingScope = blockScope;
            if (this.original == this) {
                this.ordinal = recordFunctionalType(blockScope);
            }
            if (!zArgumentsTypeElided) {
                for (int i = 0; i < length; i++) {
                    this.argumentTypes[i] = this.arguments[i].type.resolveType(blockScope, true);
                }
            }
            if (this.expectedType == null && this.expressionContext == ExpressionContext.INVOCATION_CONTEXT) {
                return new PolyTypeBinding(this);
            }
        }
        MethodScope methodScope = blockScope.methodScope();
        MethodScope methodScope2 = new MethodScope(blockScope, this, methodScope.isStatic, methodScope.lastVisibleFieldID);
        this.scope = methodScope2;
        methodScope2.isConstructorCall = methodScope.isConstructorCall;
        super.resolveType(blockScope, z);
        boolean z4 = this.descriptor != null;
        ?? r7 = 0;
        if (!z && (!z4 || this.descriptor.typeVariables != Binding.NO_TYPE_VARIABLES)) {
            this.resolvedType = null;
            return null;
        }
        this.binding = new MethodBinding(33558530, CharOperation.concat(TypeConstants.ANONYMOUS_METHOD, Integer.toString(this.ordinal).toCharArray()), z4 ? this.descriptor.returnType : TypeBinding.VOID, Binding.NO_PARAMETERS, z4 ? this.descriptor.thrownExceptions : Binding.NO_EXCEPTIONS, blockScope.enclosingSourceType());
        this.binding.typeVariables = Binding.NO_TYPE_VARIABLES;
        if (!z4 || this.descriptor.parameters.length == length) {
            z2 = false;
        } else {
            this.scope.problemReporter().lambdaSignatureMismatched(this);
            if (zArgumentsTypeElided || this.original != this) {
                this.resolvedType = null;
                return null;
            }
            this.resolvedType = null;
            z2 = true;
        }
        TypeBinding[] typeBindingArr = new TypeBinding[length];
        int i2 = 0;
        while (true) {
            j = 0;
            j2 = 128;
            if (i2 >= length) {
                break;
            }
            ?? r8 = r7;
            boolean z5 = z2;
            Argument argument = this.arguments[i2];
            if (!argument.isVarArgs()) {
                z2 = z5;
            } else if (i2 == length - 1) {
                this.binding.modifiers |= 128;
                z2 = z5;
            } else {
                this.scope.problemReporter().illegalVarargInLambda(argument);
                z2 = true;
            }
            if (!z4 || i2 >= this.descriptor.parameters.length) {
                r6 = r8;
            } else {
                typeBinding = this.descriptor.parameters[i2];
            }
            if (!zArgumentsTypeElided) {
                r6 = typeBinding;
                r6 = this.argumentTypes[i2];
            }
            if (r6 == 0) {
                z2 = true;
            } else if (r6 == TypeBinding.VOID) {
                this.scope.problemReporter().argumentTypeCannotBeVoid(this, argument);
                z2 = true;
            } else {
                if (!r6.isValidBinding()) {
                    this.binding.tagBits |= 512;
                }
                if ((r6.tagBits & 128) != 0) {
                    this.binding.tagBits |= 128;
                }
            }
            i2++;
            r7 = r8;
            z3 = true;
        }
        if (!zArgumentsTypeElided && !z2) {
            if (this.expectedType instanceof IntersectionTypeBinding18) {
                referenceBinding2 = (ReferenceBinding) ((IntersectionTypeBinding18) this.expectedType).getSAMType(blockScope);
            } else if (this.expectedType instanceof ReferenceBinding) {
                referenceBinding = (ReferenceBinding) this.expectedType;
            } else {
                r10 = r7;
            }
            if (r10 != 0) {
                r10 = referenceBinding;
                referenceBindingFindGroundTargetType = findGroundTargetType(blockScope, r10, zArgumentsTypeElided);
            } else {
                r10 = referenceBinding;
                r15 = r7;
            }
            if (r15 != 0) {
                r10 = referenceBinding2;
                this.descriptor = r15.getSingleAbstractMethod(blockScope, z3);
                if (!this.descriptor.isValidBinding()) {
                    r10 = referenceBinding2;
                    r15 = referenceBindingFindGroundTargetType;
                    reportSamProblem(blockScope, this.descriptor);
                } else {
                    if (r15 != r10 && !r15.isCompatibleWith(r10, this.scope)) {
                        blockScope.problemReporter().typeMismatchError(r15, this.expectedType, this, r7);
                        this.resolvedType = r7;
                        return r7;
                    }
                    this.resolvedType = r15;
                }
            }
        }
        r10 = referenceBinding2;
        r10 = referenceBinding2;
        r15 = referenceBindingFindGroundTargetType;
        boolean z6 = (this.requiresGenericSignature || blockScope.compilerOptions().generateGenericSignatureForLambdaExpressions) ? z3 ? 1 : 0 : false;
        ?? r16 = r7;
        int i3 = 0;
        ?? r9 = r7;
        while (i3 < length) {
            boolean z7 = z2;
            Argument argument2 = this.arguments[i3];
            TypeBinding typeBinding2 = (!z4 || i3 >= this.descriptor.parameters.length) ? null : this.descriptor.parameters[i3];
            TypeBinding typeBinding3 = zArgumentsTypeElided ? typeBinding2 : this.argumentTypes[i3];
            if (typeBinding3 == null || typeBinding3 == TypeBinding.VOID) {
                obj = null;
            } else {
                if (z4 && typeBinding2 != null && typeBinding3.isValidBinding() && TypeBinding.notEquals(typeBinding3, typeBinding2) && typeBinding2.isProperType(true)) {
                    this.scope.problemReporter().lambdaParameterTypeMismatched(argument2, argument2.type, typeBinding2);
                    obj = null;
                    this.resolvedType = null;
                } else {
                    obj = null;
                }
                if (z6) {
                    TypeBinding typeBindingLeafComponentType = typeBinding3.leafComponentType();
                    if ((typeBindingLeafComponentType instanceof ReferenceBinding) && (((ReferenceBinding) typeBindingLeafComponentType).modifiers & 1073741824) != 0) {
                        this.binding.modifiers |= 1073741824;
                    }
                }
                typeBindingArr[i3] = argument2.bind(this.scope, typeBinding3, false);
                if (argument2.annotations != null) {
                    this.binding.tagBits |= 1024;
                    if (r16 == 0) {
                        AnnotationBinding[][] annotationBindingArr = new AnnotationBinding[length][];
                        for (int i4 = 0; i4 < i3; i4++) {
                            r16 = r16;
                            annotationBindingArr[i4] = Binding.NO_ANNOTATIONS;
                        }
                        r16 = r16;
                        r16 = annotationBindingArr;
                    }
                    r16 = r16;
                    r16[i3] = argument2.binding.getAnnotations();
                } else if (r16 != 0) {
                    r16[i3] = Binding.NO_ANNOTATIONS;
                }
            }
            i3++;
            z2 = z7;
            r9 = obj;
            z3 = true;
            j = 0;
            j2 = 128;
            r16 = r16;
        }
        if (!z2) {
            this.binding.parameters = typeBindingArr;
            if (r16 != 0) {
                this.binding.setParameterAnnotations(r16);
            }
        }
        if (!zArgumentsTypeElided && !z2 && this.binding.isVarargs() && !this.binding.parameters[this.binding.parameters.length - (z3 ? 1 : 0)].isReifiable()) {
            ProblemReporter problemReporter = this.scope.problemReporter();
            Argument[] argumentArr2 = this.arguments;
            problemReporter.possibleHeapPollutionFromVararg(argumentArr2[argumentArr2.length - (z3 ? 1 : 0)]);
        }
        ReferenceBinding[] referenceBindingArr = this.binding.thrownExceptions;
        int length2 = referenceBindingArr.length;
        int i5 = 0;
        ?? r11 = r9;
        while (i5 < length2) {
            ReferenceBinding referenceBinding3 = referenceBindingArr[i5];
            if ((referenceBinding3.tagBits & j2) != 0) {
                this.binding.tagBits |= j2;
            }
            if (z6) {
                this.binding.modifiers |= referenceBinding3.modifiers & 1073741824;
            }
            i5++;
            z2 = z2;
            r11 = 0;
            z3 = true;
            j = 0;
        }
        TypeBinding typeBinding4 = this.binding.returnType;
        if (typeBinding4 != null) {
            if ((typeBinding4.tagBits & j2) != j) {
                this.binding.tagBits |= j2;
            }
            if (z6) {
                TypeBinding typeBindingLeafComponentType2 = typeBinding4.leafComponentType();
                if ((typeBindingLeafComponentType2 instanceof ReferenceBinding) && (((ReferenceBinding) typeBindingLeafComponentType2).modifiers & 1073741824) != 0) {
                    this.binding.modifiers |= 1073741824;
                }
            }
        }
        if (z4 && !z2 && blockScope.compilerOptions().isAnnotationBasedNullAnalysisEnabled) {
            if (!zArgumentsTypeElided) {
                AbstractMethodDeclaration.createArgumentBindings(this.arguments, this.binding, this.scope);
                mergeParameterNullAnnotations(blockScope);
            }
            this.binding.tagBits |= this.descriptor.tagBits & TagBits.AnnotationNullMASK;
        }
        this.binding.modifiers &= -33554433;
        Statement statement = this.body;
        if (statement instanceof Expression) {
            Expression expression = (Expression) statement;
            new ReturnStatement(expression, expression.sourceStart, expression.sourceEnd, z3).resolve(this.scope);
            if (expression.resolvedType == TypeBinding.VOID && !expression.statementExpression()) {
                this.scope.problemReporter().invalidExpressionAsStatement(expression);
            }
        } else {
            statement.resolve(this.scope);
            if (!this.returnsVoid && !this.returnsValue) {
                this.valueCompatible = this.body.doesNotCompleteNormally();
            }
        }
        if ((this.binding.tagBits & j2) != 0) {
            this.scope.problemReporter().missingTypeInLambda(this, this.binding);
        }
        if (this.shouldCaptureInstance && this.scope.isConstructorCall) {
            this.scope.problemReporter().fieldsOrThisBeforeConstructorInvocation(this);
        }
        if (!z2) {
            return this.resolvedType;
        }
        this.resolvedType = r11;
        return r11;
    }

    private ReferenceBinding findGroundTargetType(BlockScope blockScope, TypeBinding typeBinding, boolean z) {
        if (typeBinding instanceof IntersectionTypeBinding18) {
            typeBinding = ((IntersectionTypeBinding18) typeBinding).getSAMType(blockScope);
        }
        if (!(typeBinding instanceof ReferenceBinding) || !typeBinding.isValidBinding()) {
            return null;
        }
        ParameterizedTypeBinding parameterizedTypeBindingParameterizedWithWildcard = InferenceContext18.parameterizedWithWildcard(typeBinding);
        if (parameterizedTypeBindingParameterizedWithWildcard == null) {
            return (ReferenceBinding) typeBinding;
        }
        if (!z) {
            InferenceContext18 inferenceContext18 = new InferenceContext18(blockScope);
            try {
                return inferenceContext18.inferFunctionalInterfaceParameterization(this, blockScope, parameterizedTypeBindingParameterizedWithWildcard);
            } finally {
                inferenceContext18.cleanUp();
            }
        }
        return findGroundTargetTypeForElidedLambda(blockScope, parameterizedTypeBindingParameterizedWithWildcard);
    }

    public ReferenceBinding findGroundTargetTypeForElidedLambda(BlockScope blockScope, ParameterizedTypeBinding parameterizedTypeBinding) {
        TypeBinding[] nonWildcardParameterization = parameterizedTypeBinding.getNonWildcardParameterization(blockScope);
        if (nonWildcardParameterization == null) {
            return null;
        }
        return blockScope.environment().createParameterizedType(parameterizedTypeBinding.genericType(), nonWildcardParameterization, parameterizedTypeBinding.enclosingType());
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.FunctionalExpression
    public boolean argumentsTypeElided() {
        Argument[] argumentArr = this.arguments;
        return argumentArr.length > 0 && argumentArr[0].hasElidedType();
    }

    private void analyzeExceptions() {
        try {
            Statement statement = this.body;
            MethodScope methodScope = this.scope;
            ExceptionInferenceFlowContext exceptionInferenceFlowContext = new ExceptionInferenceFlowContext(null, this, Binding.NO_EXCEPTIONS, null, this.scope, FlowInfo.DEAD_END);
            statement.analyseCode(methodScope, exceptionInferenceFlowContext, UnconditionalFlowInfo.fakeInitializedFlowInfo(this.scope.outerMostMethodScope().analysisIndex, this.scope.referenceType().maxFieldCount));
            this.thrownExceptions = exceptionInferenceFlowContext.extendedExceptions == null ? Collections.emptySet() : new HashSet(exceptionInferenceFlowContext.extendedExceptions);
        } catch (Exception unused) {
        }
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.Expression, org.eclipse.jdt.internal.compiler.ast.Statement
    public FlowInfo analyseCode(BlockScope blockScope, FlowContext flowContext, FlowInfo flowInfo) {
        if (this.ignoreFurtherInvestigation) {
            return flowInfo;
        }
        FlowInfo flowInfoCopy = flowInfo.copy();
        ExceptionHandlingFlowContext exceptionHandlingFlowContext = new ExceptionHandlingFlowContext(flowContext, this, this.binding.thrownExceptions, flowContext.getInitializationContext(), this.scope, FlowInfo.DEAD_END);
        AbstractMethodDeclaration.analyseArguments(blockScope.environment(), flowInfoCopy, this.arguments, argumentsTypeElided() ? this.descriptor : this.binding);
        Argument[] argumentArr = this.arguments;
        if (argumentArr != null) {
            int length = argumentArr.length;
            for (int i = 0; i < length; i++) {
                this.bits |= this.arguments[i].bits & 1048576;
            }
        }
        FlowInfo flowInfoAnalyseCode = this.body.analyseCode(this.scope, exceptionHandlingFlowContext, flowInfoCopy);
        if (this.body instanceof Block) {
            TypeBinding typeBindingExpectedResultType = expectedResultType();
            if (typeBindingExpectedResultType == TypeBinding.VOID) {
                if ((flowInfoAnalyseCode.tagBits & 1) == 0 || ((Block) this.body).statements == null) {
                    this.bits |= 64;
                }
            } else if (flowInfoAnalyseCode != FlowInfo.DEAD_END) {
                this.scope.problemReporter().shouldReturn(typeBindingExpectedResultType, this);
            }
        } else if (blockScope.compilerOptions().isAnnotationBasedNullAnalysisEnabled && flowInfoAnalyseCode.reachMode() == 0) {
            Expression expression = (Expression) this.body;
            checkAgainstNullAnnotation(flowContext, expression, flowInfo, expression.nullStatus(flowInfoAnalyseCode, flowContext));
        }
        return flowInfo;
    }

    void validateNullAnnotations() {
        if (this.binding != null) {
            int length = this.binding.parameters.length;
            for (int i = 0; i < length; i++) {
                if (!this.scope.validateNullAnnotation(this.binding.returnType.tagBits, this.arguments[i].type, this.arguments[i].annotations)) {
                    this.binding.returnType = this.binding.returnType.withoutToplevelNullAnnotation();
                }
            }
        }
    }

    private void mergeParameterNullAnnotations(BlockScope blockScope) {
        LookupEnvironment lookupEnvironmentEnvironment = blockScope.environment();
        TypeBinding[] typeBindingArr = this.binding.parameters;
        TypeBinding[] typeBindingArr2 = this.descriptor.parameters;
        int iMin = Math.min(typeBindingArr.length, typeBindingArr2.length);
        for (int i = 0; i < iMin; i++) {
            long j = typeBindingArr[i].tagBits & TagBits.AnnotationNullMASK;
            long j2 = TagBits.AnnotationNullMASK & typeBindingArr2[i].tagBits;
            if (j == 0) {
                if (j2 != 0 && !typeBindingArr[i].isBaseType()) {
                    for (AnnotationBinding annotationBinding : typeBindingArr2[i].getTypeAnnotations()) {
                        if (annotationBinding != null && annotationBinding.getAnnotationType().hasNullBit(96)) {
                            typeBindingArr[i] = lookupEnvironmentEnvironment.createAnnotatedType(typeBindingArr[i], new AnnotationBinding[]{annotationBinding});
                        }
                    }
                }
            } else if (j != j2 && j == 72057594037927936L) {
                blockScope.problemReporter().illegalRedefinitionToNonNullParameter(this.arguments[i], this.descriptor.declaringClass, j2 == 36028797018963968L ? lookupEnvironmentEnvironment.getNullableAnnotationName() : null);
            }
        }
    }

    void checkAgainstNullAnnotation(FlowContext flowContext, Expression expression, FlowInfo flowInfo, int i) {
        if (i == 4 || (this.descriptor.returnType.tagBits & 72057594037927936L) == 0) {
            return;
        }
        flowContext.recordNullityMismatch(this.scope, expression, expression.resolvedType, this.descriptor.returnType, flowInfo, i, null);
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.FunctionalExpression, org.eclipse.jdt.internal.compiler.ast.Expression, org.eclipse.jdt.internal.compiler.ast.IPolyExpression
    public boolean isPertinentToApplicability(final TypeBinding typeBinding, final MethodBinding methodBinding) {
        if (typeBinding == null) {
            return true;
        }
        if (argumentsTypeElided() || !super.isPertinentToApplicability(typeBinding, methodBinding)) {
            return false;
        }
        Statement statement = this.body;
        if (statement instanceof Expression) {
            if (!((Expression) statement).isPertinentToApplicability(typeBinding, methodBinding)) {
                return false;
            }
        } else {
            Expression[] expressionArr = this.resultExpressions;
            if (expressionArr != NO_EXPRESSIONS) {
                for (Expression expression : expressionArr) {
                    if (!expression.isPertinentToApplicability(typeBinding, methodBinding)) {
                        return false;
                    }
                }
            } else {
                try {
                    this.body.traverse(new ASTVisitor() { // from class: org.eclipse.jdt.internal.compiler.ast.LambdaExpression.1ResultsAnalyser
                        @Override // org.eclipse.jdt.internal.compiler.ASTVisitor
                        public boolean visit(LambdaExpression lambdaExpression, BlockScope blockScope) {
                            return false;
                        }

                        @Override // org.eclipse.jdt.internal.compiler.ASTVisitor
                        public boolean visit(TypeDeclaration typeDeclaration, BlockScope blockScope) {
                            return false;
                        }

                        @Override // org.eclipse.jdt.internal.compiler.ASTVisitor
                        public boolean visit(TypeDeclaration typeDeclaration, ClassScope classScope) {
                            return false;
                        }

                        @Override // org.eclipse.jdt.internal.compiler.ASTVisitor
                        public boolean visit(ReturnStatement returnStatement, BlockScope blockScope) {
                            if (returnStatement.expression == null || returnStatement.expression.isPertinentToApplicability(typeBinding, methodBinding)) {
                                return false;
                            }
                            final LambdaExpression lambdaExpression = LambdaExpression.this;
                            throw new RuntimeException() { // from class: org.eclipse.jdt.internal.compiler.ast.LambdaExpression.1NotPertientToApplicability
                                private static final long serialVersionUID = 1;
                            };
                        }
                    }, this.scope);
                } catch (C1NotPertientToApplicability unused) {
                    return false;
                }
            }
        }
        return true;
    }

    public boolean isVoidCompatible() {
        return this.voidCompatible;
    }

    public boolean isValueCompatible() {
        return this.valueCompatible;
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.Expression
    public StringBuffer printExpression(int i, StringBuffer stringBuffer) {
        return printExpression(i, stringBuffer, false);
    }

    public StringBuffer printExpression(int i, StringBuffer stringBuffer, boolean z) {
        int i2 = (this.bits & ASTNode.ParenthesizedMASK) >> 21;
        String str = "";
        for (int i3 = 0; i3 < i2; i3++) {
            stringBuffer.append('(');
            str = String.valueOf(str) + ')';
        }
        stringBuffer.append('(');
        if (this.arguments != null) {
            for (int i4 = 0; i4 < this.arguments.length; i4++) {
                if (i4 > 0) {
                    stringBuffer.append(", ");
                }
                this.arguments[i4].print(0, stringBuffer);
            }
        }
        stringBuffer.append(") -> ");
        if (z) {
            stringBuffer.append("{}");
        } else {
            Statement statement = this.body;
            if (statement != null) {
                if (!(statement instanceof Block)) {
                    i = 0;
                }
                statement.print(i, stringBuffer);
            } else {
                stringBuffer.append("<@incubator>");
            }
        }
        return stringBuffer.append(str);
    }

    public TypeBinding expectedResultType() {
        if (this.descriptor == null || !this.descriptor.isValidBinding()) {
            return null;
        }
        return this.descriptor.returnType;
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.Expression, org.eclipse.jdt.internal.compiler.ast.ASTNode
    public void traverse(ASTVisitor aSTVisitor, BlockScope blockScope) {
        if (aSTVisitor.visit(this, blockScope)) {
            Argument[] argumentArr = this.arguments;
            if (argumentArr != null) {
                int length = argumentArr.length;
                for (int i = 0; i < length; i++) {
                    this.arguments[i].traverse(aSTVisitor, this.scope);
                }
            }
            Statement statement = this.body;
            if (statement != null) {
                statement.traverse(aSTVisitor, this.scope);
            }
        }
        aSTVisitor.endVisit(this, blockScope);
    }

    public MethodScope getScope() {
        return this.scope;
    }

    private boolean enclosingScopesHaveErrors() {
        for (Scope scope = this.enclosingScope; scope != null; scope = scope.parent) {
            ReferenceContext referenceContext = scope.referenceContext();
            if (referenceContext != null && referenceContext.hasErrors()) {
                return true;
            }
        }
        return false;
    }

    private void analyzeShape() {
        Statement statement = this.body;
        if (statement instanceof Expression) {
            this.voidCompatible = this.assistNode ? true : ((Expression) statement).statementExpression();
            this.valueCompatible = true;
            return;
        }
        if (this.assistNode) {
            this.voidCompatible = true;
            this.valueCompatible = true;
        }
        statement.traverse(new ASTVisitor() { // from class: org.eclipse.jdt.internal.compiler.ast.LambdaExpression.1ShapeComputer
            @Override // org.eclipse.jdt.internal.compiler.ASTVisitor
            public boolean visit(LambdaExpression lambdaExpression, BlockScope blockScope) {
                return false;
            }

            @Override // org.eclipse.jdt.internal.compiler.ASTVisitor
            public boolean visit(TypeDeclaration typeDeclaration, BlockScope blockScope) {
                return false;
            }

            @Override // org.eclipse.jdt.internal.compiler.ASTVisitor
            public boolean visit(TypeDeclaration typeDeclaration, ClassScope classScope) {
                return false;
            }

            @Override // org.eclipse.jdt.internal.compiler.ASTVisitor
            public boolean visit(ReturnStatement returnStatement, BlockScope blockScope) {
                if (returnStatement.expression != null) {
                    LambdaExpression.this.valueCompatible = true;
                    LambdaExpression.this.voidCompatible = false;
                    LambdaExpression.this.returnsValue = true;
                } else {
                    LambdaExpression.this.voidCompatible = true;
                    LambdaExpression.this.valueCompatible = false;
                    LambdaExpression.this.returnsVoid = true;
                }
                return false;
            }
        }, null);
        if (this.returnsValue || this.returnsVoid) {
            return;
        }
        this.valueCompatible = this.body.doesNotCompleteNormally();
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.Expression, org.eclipse.jdt.internal.compiler.ast.IPolyExpression
    public boolean isPotentiallyCompatibleWith(TypeBinding typeBinding, Scope scope) {
        if (!super.isPertinentToApplicability(typeBinding, null)) {
            return true;
        }
        MethodBinding singleAbstractMethod = typeBinding.getSingleAbstractMethod(scope, true);
        if (singleAbstractMethod == null || !singleAbstractMethod.isValidBinding() || singleAbstractMethod.parameters.length != this.arguments.length) {
            return false;
        }
        analyzeShape();
        if (singleAbstractMethod.returnType.f626id == 6) {
            if (!this.voidCompatible) {
                return false;
            }
        } else if (!this.valueCompatible) {
            return false;
        }
        return true;
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.Expression, org.eclipse.jdt.internal.compiler.ast.IPolyExpression
    public boolean isCompatibleWith(TypeBinding typeBinding, Scope scope) {
        ReferenceBinding referenceBindingFindGroundTargetType;
        MethodBinding singleAbstractMethod;
        if (!super.isPertinentToApplicability(typeBinding, null)) {
            return true;
        }
        try {
            LambdaExpression lambdaExpressionCachedResolvedCopy = cachedResolvedCopy(typeBinding, argumentsTypeElided(), false, null);
            if (lambdaExpressionCachedResolvedCopy == null || (singleAbstractMethod = (referenceBindingFindGroundTargetType = findGroundTargetType(this.enclosingScope, typeBinding, argumentsTypeElided())).getSingleAbstractMethod(this.enclosingScope, true)) == null || singleAbstractMethod.problemId() == 17) {
                return false;
            }
            if (singleAbstractMethod.returnType.f626id == 6) {
                if (!lambdaExpressionCachedResolvedCopy.voidCompatible) {
                    return false;
                }
            } else if (!lambdaExpressionCachedResolvedCopy.valueCompatible) {
                return false;
            }
            if (!isPertinentToApplicability(referenceBindingFindGroundTargetType, null)) {
                return true;
            }
            if (!kosherDescriptor(this.enclosingScope, singleAbstractMethod, false)) {
                return false;
            }
            Expression[] expressionArr = lambdaExpressionCachedResolvedCopy.resultExpressions;
            int length = expressionArr.length;
            for (int i = 0; i < length; i++) {
                if (this.enclosingScope.parameterCompatibilityLevel(expressionArr[i].resolvedType, singleAbstractMethod.returnType) == -1) {
                    Expression expression = expressionArr[i];
                    if (!expression.isConstantValueOfTypeAssignableToType(expression.resolvedType, singleAbstractMethod.returnType) && (singleAbstractMethod.returnType.f626id != 6 || (this.body instanceof Block))) {
                        return false;
                    }
                }
            }
            return true;
        } catch (CopyFailureException unused) {
            if (this.assistNode) {
                return true;
            }
            return !isPertinentToApplicability(typeBinding, null);
        }
    }

    class CopyFailureException extends RuntimeException {
        private static final long serialVersionUID = 1;

        CopyFailureException() {
        }
    }

    private LambdaExpression cachedResolvedCopy(TypeBinding typeBinding, boolean z, boolean z2, InferenceContext18 inferenceContext18) {
        MethodBinding singleAbstractMethod;
        LambdaExpression lambdaExpressionCopy;
        ReferenceBinding referenceBindingFindGroundTargetType = findGroundTargetType(this.enclosingScope, typeBinding, argumentsTypeElided());
        if (referenceBindingFindGroundTargetType == null || (singleAbstractMethod = referenceBindingFindGroundTargetType.getSingleAbstractMethod(this.enclosingScope, true)) == null || !singleAbstractMethod.isValidBinding() || singleAbstractMethod.parameters.length != this.arguments.length) {
            return null;
        }
        HashMap<TypeBinding, LambdaExpression> map = this.copiesPerTargetType;
        if (map != null) {
            lambdaExpressionCopy = map.get(referenceBindingFindGroundTargetType);
            if (lambdaExpressionCopy == null && z && this.copiesPerTargetType.values().size() > 0) {
                lambdaExpressionCopy = this.copiesPerTargetType.values().iterator().next();
            }
        } else {
            lambdaExpressionCopy = null;
        }
        IErrorHandlingPolicy iErrorHandlingPolicySwitchErrorHandlingPolicy = this.enclosingScope.problemReporter().switchErrorHandlingPolicy(silentErrorHandlingPolicy);
        if (lambdaExpressionCopy == null) {
            try {
                lambdaExpressionCopy = copy();
                if (lambdaExpressionCopy == null) {
                    throw new CopyFailureException();
                }
                lambdaExpressionCopy.setExpressionContext(this.expressionContext);
                lambdaExpressionCopy.setExpectedType(referenceBindingFindGroundTargetType);
                lambdaExpressionCopy.inferenceContext = inferenceContext18;
                TypeBinding typeBindingResolveType = lambdaExpressionCopy.resolveType(this.enclosingScope, true);
                if (typeBindingResolveType != null && typeBindingResolveType.isValidBinding()) {
                    if (this.copiesPerTargetType == null) {
                        this.copiesPerTargetType = new HashMap<>();
                    }
                    this.copiesPerTargetType.put(referenceBindingFindGroundTargetType, lambdaExpressionCopy);
                }
                this.enclosingScope.problemReporter().switchErrorHandlingPolicy(iErrorHandlingPolicySwitchErrorHandlingPolicy);
                return null;
            } catch (Throwable th) {
                this.enclosingScope.problemReporter().switchErrorHandlingPolicy(iErrorHandlingPolicySwitchErrorHandlingPolicy);
                throw th;
            }
        }
        if (z2 && lambdaExpressionCopy.thrownExceptions == null && !lambdaExpressionCopy.hasIgnoredMandatoryErrors && !enclosingScopesHaveErrors()) {
            lambdaExpressionCopy.analyzeExceptions();
        }
        this.enclosingScope.problemReporter().switchErrorHandlingPolicy(iErrorHandlingPolicySwitchErrorHandlingPolicy);
        return lambdaExpressionCopy;
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.Expression, org.eclipse.jdt.internal.compiler.ast.IPolyExpression
    public LambdaExpression resolveExpressionExpecting(TypeBinding typeBinding, Scope scope, InferenceContext18 inferenceContext18) {
        try {
            return cachedResolvedCopy(typeBinding, false, true, inferenceContext18);
        } catch (CopyFailureException unused) {
            return null;
        }
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.Expression, org.eclipse.jdt.internal.compiler.ast.IPolyExpression
    public boolean sIsMoreSpecific(TypeBinding typeBinding, TypeBinding typeBinding2, Scope scope) {
        TypeBinding typeBindingCapture;
        MethodBinding singleAbstractMethod;
        if (super.sIsMoreSpecific(typeBinding, typeBinding2, scope)) {
            return true;
        }
        if (argumentsTypeElided() || typeBinding2.findSuperTypeOriginatingFrom(typeBinding) != null || (singleAbstractMethod = (typeBindingCapture = typeBinding.capture(this.enclosingScope, this.sourceStart, this.sourceEnd)).getSingleAbstractMethod(this.enclosingScope, true)) == null || !singleAbstractMethod.isValidBinding()) {
            return false;
        }
        TypeBinding typeBinding3 = singleAbstractMethod.returnType;
        MethodBinding singleAbstractMethod2 = typeBinding2.getSingleAbstractMethod(this.enclosingScope, true);
        if (singleAbstractMethod2 == null || !singleAbstractMethod2.isValidBinding()) {
            return true;
        }
        TypeBinding typeBinding4 = singleAbstractMethod2.returnType;
        if (typeBinding4.f626id == 6) {
            return true;
        }
        if (typeBinding3.f626id == 6) {
            return false;
        }
        if (typeBinding3.isCompatibleWith(typeBinding4, scope)) {
            return true;
        }
        Expression[] expressionArr = cachedResolvedCopy(typeBindingCapture, true, false, null).resultExpressions;
        int length = expressionArr == null ? 0 : expressionArr.length;
        if (length > 0) {
            if (typeBinding3.isBaseType() && !typeBinding4.isBaseType()) {
                int i = 0;
                while (i < length && !expressionArr[i].isPolyExpression() && expressionArr[i].resolvedType.isBaseType()) {
                    i++;
                }
                if (i == length) {
                    return true;
                }
            }
            if (!typeBinding3.isBaseType() && typeBinding4.isBaseType()) {
                int i2 = 0;
                while (i2 < length && !expressionArr[i2].resolvedType.isBaseType()) {
                    i2++;
                }
                if (i2 == length) {
                    return true;
                }
            }
            if (typeBinding3.isFunctionalInterface(this.enclosingScope) && typeBinding4.isFunctionalInterface(this.enclosingScope)) {
                int i3 = 0;
                while (i3 < length && expressionArr[i3].sIsMoreSpecific(typeBinding3, typeBinding4, scope)) {
                    i3++;
                }
                if (i3 == length) {
                    return true;
                }
            }
        }
        return false;
    }

    LambdaExpression copy() {
        Parser parser = new Parser(this.enclosingScope.problemReporter(), false);
        ICompilationUnit compilationUnit = this.compilationResult.getCompilationUnit();
        LambdaExpression lambdaExpression = (LambdaExpression) parser.parseLambdaExpression(compilationUnit != null ? compilationUnit.getContents() : this.text, compilationUnit != null ? this.sourceStart : 0, (this.sourceEnd - this.sourceStart) + 1, this.enclosingScope.referenceCompilationUnit(), false);
        if (lambdaExpression != null) {
            lambdaExpression.original = this;
            lambdaExpression.assistNode = this.assistNode;
            lambdaExpression.enclosingScope = this.enclosingScope;
        }
        return lambdaExpression;
    }

    public void returnsExpression(Expression expression, TypeBinding typeBinding) {
        if (this.original == this) {
            return;
        }
        if (this.body instanceof Expression) {
            this.valueCompatible = typeBinding == null || typeBinding.f626id != 6;
            this.voidCompatible = this.assistNode ? true : ((Expression) this.body).statementExpression();
            this.resultExpressions = new Expression[]{expression};
        } else {
            if (expression != null) {
                this.returnsValue = true;
                this.voidCompatible = false;
                this.valueCompatible = !this.returnsVoid;
                Expression[] expressionArr = this.resultExpressions;
                int length = expressionArr.length;
                Expression[] expressionArr2 = new Expression[length + 1];
                System.arraycopy(expressionArr, 0, expressionArr2, 0, length);
                expressionArr2[length] = expression;
                this.resultExpressions = expressionArr2;
                return;
            }
            this.returnsVoid = true;
            this.valueCompatible = false;
            this.voidCompatible = !this.returnsValue;
        }
    }

    @Override // org.eclipse.jdt.internal.compiler.impl.ReferenceContext
    public CompilationResult compilationResult() {
        return this.compilationResult;
    }

    @Override // org.eclipse.jdt.internal.compiler.impl.ReferenceContext
    public void abort(int i, CategorizedProblem categorizedProblem) {
        if (i == 2) {
            throw new AbortCompilation(this.compilationResult, categorizedProblem);
        }
        if (i == 4) {
            throw new AbortCompilationUnit(this.compilationResult, categorizedProblem);
        }
        if (i == 8) {
            throw new AbortType(this.compilationResult, categorizedProblem);
        }
        throw new AbortMethod(this.compilationResult, categorizedProblem);
    }

    @Override // org.eclipse.jdt.internal.compiler.impl.ReferenceContext
    public CompilationUnitDeclaration getCompilationUnitDeclaration() {
        if (this.enclosingScope == null) {
            return null;
        }
        return this.enclosingScope.compilationUnitScope().referenceContext;
    }

    @Override // org.eclipse.jdt.internal.compiler.impl.ReferenceContext
    public boolean hasErrors() {
        return this.ignoreFurtherInvestigation;
    }

    @Override // org.eclipse.jdt.internal.compiler.impl.ReferenceContext
    public void tagAsHavingErrors() {
        ReferenceContext referenceContext;
        this.ignoreFurtherInvestigation = true;
        for (Scope scope = this.enclosingScope.parent; scope != null; scope = scope.parent) {
            int i = scope.kind;
            if ((i == 2 || i == 3) && (referenceContext = scope.referenceContext()) != this) {
                referenceContext.tagAsHavingErrors();
                return;
            }
        }
    }

    @Override // org.eclipse.jdt.internal.compiler.impl.ReferenceContext
    public void tagAsHavingIgnoredMandatoryErrors(int i) {
        switch (i) {
            case 99:
            case IProblem.ReturnTypeMismatch /* 16777235 */:
            case IProblem.UnhandledExceptionInDefaultConstructor /* 16777362 */:
            case IProblem.UnhandledException /* 16777384 */:
            case IProblem.UnhandledExceptionOnAutoClose /* 16778098 */:
            case IProblem.VoidMethodReturnsValue /* 67108969 */:
            case IProblem.ShouldReturnValueHintMissingDefault /* 67109635 */:
            case IProblem.TargetTypeNotAFunctionalInterface /* 553648781 */:
            case IProblem.ShouldReturnValue /* 603979884 */:
                break;
            default:
                switch (i) {
                    case IProblem.illFormedParameterizationOfFunctionalInterface /* 553648783 */:
                    case IProblem.lambdaSignatureMismatched /* 553648784 */:
                    case IProblem.lambdaParameterTypeMismatched /* 553648785 */:
                    case IProblem.IncompatibleLambdaParameterType /* 553648786 */:
                    case IProblem.NoGenericLambda /* 553648787 */:
                        break;
                    default:
                        this.hasIgnoredMandatoryErrors = true;
                        MethodScope methodScope = this.scope;
                        for (MethodScope methodScopeEnclosingLambdaScope = methodScope == null ? null : methodScope.enclosingLambdaScope(); methodScopeEnclosingLambdaScope != null; methodScopeEnclosingLambdaScope = methodScopeEnclosingLambdaScope.enclosingLambdaScope()) {
                            ((LambdaExpression) methodScopeEnclosingLambdaScope.referenceContext).hasIgnoredMandatoryErrors = true;
                        }
                        break;
                }
                break;
        }
    }

    public Set<TypeBinding> getThrownExceptions() {
        Set<TypeBinding> set = this.thrownExceptions;
        return set == null ? Collections.emptySet() : set;
    }

    public void generateCode(ClassScope classScope, ClassFile classFile) {
        boolean z;
        classFile.codeStream.wideMode = false;
        int i = 0;
        do {
            try {
                i = classFile.contentsOffset;
                generateCode(classFile);
                z = false;
            } catch (AbortMethod e) {
                z = true;
                if (e.compilationResult == CodeStream.RESTART_IN_WIDE_MODE) {
                    classFile.contentsOffset = i;
                    classFile.methodCount--;
                    classFile.codeStream.resetInWideMode();
                } else if (e.compilationResult == CodeStream.RESTART_CODE_GEN_FOR_UNUSED_LOCALS_MODE) {
                    classFile.contentsOffset = i;
                    classFile.methodCount--;
                    classFile.codeStream.resetForCodeGenUnusedLocals();
                } else {
                    throw new AbortType(this.compilationResult, e.problem);
                }
            }
        } while (z);
    }

    public void generateCode(ClassFile classFile) {
        classFile.generateMethodInfoHeader(this.binding);
        int i = classFile.contentsOffset;
        int iGenerateMethodInfoAttributes = classFile.generateMethodInfoAttributes(this.binding);
        int i2 = classFile.contentsOffset;
        classFile.generateCodeAttributeHeader();
        CodeStream codeStream = classFile.codeStream;
        codeStream.reset(this, classFile);
        this.scope.computeLocalVariablePositions(this.outerLocalVariablesSlotSize + (!this.binding.isStatic() ? 1 : 0), codeStream);
        SyntheticArgumentBinding[] syntheticArgumentBindingArr = this.outerLocalVariables;
        if (syntheticArgumentBindingArr != null) {
            int length = syntheticArgumentBindingArr.length;
            for (int i3 = 0; i3 < length; i3++) {
                SyntheticArgumentBinding syntheticArgumentBinding = this.outerLocalVariables[i3];
                codeStream.addVisibleLocalVariable(syntheticArgumentBinding);
                codeStream.record(syntheticArgumentBinding);
                syntheticArgumentBinding.recordInitializationStartPC(0);
            }
        }
        Argument[] argumentArr = this.arguments;
        if (argumentArr != null) {
            int length2 = argumentArr.length;
            for (int i4 = 0; i4 < length2; i4++) {
                LocalVariableBinding localVariableBinding = this.arguments[i4].binding;
                codeStream.addVisibleLocalVariable(localVariableBinding);
                localVariableBinding.recordInitializationStartPC(0);
            }
        }
        Statement statement = this.body;
        if (statement instanceof Block) {
            statement.generateCode(this.scope, codeStream);
            if ((this.bits & 64) != 0) {
                codeStream.return_();
            }
        } else {
            Expression expression = (Expression) statement;
            expression.generateCode(this.scope, codeStream, true);
            if (this.binding.returnType == TypeBinding.VOID) {
                codeStream.return_();
            } else {
                codeStream.generateReturnBytecode(expression);
            }
        }
        codeStream.exitUserScope(this.scope);
        codeStream.recordPositionsFrom(0, this.sourceEnd);
        try {
            classFile.completeCodeAttribute(i2);
            classFile.completeMethodInfo(this.binding, i, iGenerateMethodInfoAttributes + 1);
        } catch (NegativeArraySizeException unused) {
            throw new AbortMethod(this.scope.referenceCompilationUnit().compilationResult, null);
        }
    }

    public void addSyntheticArgument(LocalVariableBinding localVariableBinding) {
        if (this.original != this || this.binding == null) {
            return;
        }
        int length = this.outerLocalVariables.length;
        int i = 0;
        for (int i2 = 0; i2 < length; i2++) {
            if (this.outerLocalVariables[i2].actualOuterLocalVariable == localVariableBinding) {
                return;
            }
        }
        SyntheticArgumentBinding[] syntheticArgumentBindingArr = this.outerLocalVariables;
        SyntheticArgumentBinding[] syntheticArgumentBindingArr2 = new SyntheticArgumentBinding[length + 1];
        this.outerLocalVariables = syntheticArgumentBindingArr2;
        System.arraycopy(syntheticArgumentBindingArr, 0, syntheticArgumentBindingArr2, 0, length);
        SyntheticArgumentBinding[] syntheticArgumentBindingArr3 = this.outerLocalVariables;
        SyntheticArgumentBinding syntheticArgumentBinding = new SyntheticArgumentBinding(localVariableBinding);
        syntheticArgumentBindingArr3[length] = syntheticArgumentBinding;
        syntheticArgumentBinding.resolvedPosition = this.outerLocalVariablesSlotSize;
        syntheticArgumentBinding.declaringScope = this.scope;
        int length2 = this.binding.parameters.length;
        TypeBinding[] typeBindingArr = new TypeBinding[length2 + 1];
        typeBindingArr[length] = localVariableBinding.type;
        int i3 = 0;
        while (i < length2) {
            if (i == length) {
                i3++;
            }
            typeBindingArr[i3] = this.binding.parameters[i];
            i++;
            i3++;
        }
        this.binding.parameters = typeBindingArr;
        int i4 = syntheticArgumentBinding.type.f626id;
        if (i4 == 7 || i4 == 8) {
            this.outerLocalVariablesSlotSize += 2;
        } else {
            this.outerLocalVariablesSlotSize++;
        }
    }

    public SyntheticArgumentBinding getSyntheticArgument(LocalVariableBinding localVariableBinding) {
        SyntheticArgumentBinding[] syntheticArgumentBindingArr = this.outerLocalVariables;
        int length = syntheticArgumentBindingArr == null ? 0 : syntheticArgumentBindingArr.length;
        for (int i = 0; i < length; i++) {
            if (this.outerLocalVariables[i].actualOuterLocalVariable == localVariableBinding) {
                return this.outerLocalVariables[i];
            }
        }
        return null;
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.FunctionalExpression
    public MethodBinding getMethodBinding() {
        TypeBinding[] typeBindingArr;
        if (this.actualMethodBinding == null) {
            if (this.binding != null) {
                if ((this.binding instanceof SyntheticMethodBinding) && this.outerLocalVariables.length > 0) {
                    int length = this.binding.parameters.length - this.outerLocalVariables.length;
                    typeBindingArr = new TypeBinding[length];
                    System.arraycopy(this.binding.parameters, this.outerLocalVariables.length, typeBindingArr, 0, length);
                } else {
                    typeBindingArr = this.binding.parameters;
                }
                this.actualMethodBinding = new MethodBinding(this.binding.modifiers, this.binding.selector, this.binding.returnType, typeBindingArr, this.binding.thrownExceptions, this.binding.declaringClass);
                this.actualMethodBinding.tagBits = this.binding.tagBits;
            } else {
                this.actualMethodBinding = new ProblemMethodBinding(CharOperation.NO_CHAR, null, 17);
            }
        }
        return this.actualMethodBinding;
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.FunctionalExpression
    public int diagnosticsSourceEnd() {
        return this.body instanceof Block ? this.arrowPosition : this.sourceEnd;
    }

    public TypeBinding[] getMarkerInterfaces() {
        if (!(this.expectedType instanceof IntersectionTypeBinding18)) {
            return null;
        }
        LinkedHashSet linkedHashSet = new LinkedHashSet();
        IntersectionTypeBinding18 intersectionTypeBinding18 = (IntersectionTypeBinding18) this.expectedType;
        ReferenceBinding[] referenceBindingArr = intersectionTypeBinding18.intersectingTypes;
        TypeBinding sAMType = intersectionTypeBinding18.getSAMType(this.enclosingScope);
        for (ReferenceBinding referenceBinding : referenceBindingArr) {
            if (referenceBinding.isInterface() && !TypeBinding.equalsEquals(sAMType, referenceBinding) && referenceBinding.f626id != 37) {
                linkedHashSet.add(referenceBinding);
            }
        }
        if (linkedHashSet.size() > 0) {
            return (TypeBinding[]) linkedHashSet.toArray(new TypeBinding[linkedHashSet.size()]);
        }
        return null;
    }

    public ReferenceBinding getTypeBinding() {
        if (this.classType != null || this.resolvedType == null) {
            return null;
        }
        ReferenceBinding referenceBinding = new ReferenceBinding() { // from class: org.eclipse.jdt.internal.compiler.ast.LambdaExpression.1LambdaTypeBinding
            @Override // org.eclipse.jdt.internal.compiler.lookup.ReferenceBinding
            public MethodBinding[] methods() {
                return new MethodBinding[]{LambdaExpression.this.getMethodBinding()};
            }

            @Override // org.eclipse.jdt.internal.compiler.lookup.ReferenceBinding, org.eclipse.jdt.internal.compiler.lookup.TypeBinding
            public char[] sourceName() {
                return TypeConstants.LAMBDA_TYPE;
            }

            @Override // org.eclipse.jdt.internal.compiler.lookup.ReferenceBinding, org.eclipse.jdt.internal.compiler.lookup.TypeBinding
            public ReferenceBinding superclass() {
                return LambdaExpression.this.scope.getJavaLangObject();
            }

            @Override // org.eclipse.jdt.internal.compiler.lookup.ReferenceBinding, org.eclipse.jdt.internal.compiler.lookup.TypeBinding
            public ReferenceBinding[] superInterfaces() {
                return new ReferenceBinding[]{(ReferenceBinding) LambdaExpression.this.resolvedType};
            }

            @Override // org.eclipse.jdt.internal.compiler.lookup.Binding
            public char[] computeUniqueKey() {
                return LambdaExpression.this.descriptor.declaringClass.computeUniqueKey();
            }

            public String toString() {
                StringBuffer stringBuffer = new StringBuffer("()->{} implements ");
                stringBuffer.append(LambdaExpression.this.descriptor.declaringClass.sourceName());
                stringBuffer.append('.');
                stringBuffer.append(LambdaExpression.this.descriptor.toString());
                return stringBuffer.toString();
            }
        };
        this.classType = referenceBinding;
        return referenceBinding;
    }
}
