package org.jetbrains.kotlin.resolve.calls.tower;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.builtins.FunctionTypesKt;
import org.jetbrains.kotlin.builtins.KotlinBuiltIns;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.tree.IElementType;
import org.jetbrains.kotlin.com.intellij.psi.util.PsiTreeUtil;
import org.jetbrains.kotlin.config.LanguageVersionSettings;
import org.jetbrains.kotlin.descriptors.CallableDescriptor;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.descriptors.FunctionDescriptor;
import org.jetbrains.kotlin.descriptors.ModuleDescriptor;
import org.jetbrains.kotlin.descriptors.ValueParameterDescriptor;
import org.jetbrains.kotlin.descriptors.annotations.Annotations;
import org.jetbrains.kotlin.lexer.KtTokens;
import org.jetbrains.kotlin.psi.Call;
import org.jetbrains.kotlin.psi.KtBinaryExpressionWithTypeRHS;
import org.jetbrains.kotlin.psi.KtBlockExpression;
import org.jetbrains.kotlin.psi.KtCallableReferenceExpression;
import org.jetbrains.kotlin.psi.KtElement;
import org.jetbrains.kotlin.psi.KtExpression;
import org.jetbrains.kotlin.psi.KtFunctionLiteral;
import org.jetbrains.kotlin.psi.KtLambdaArgument;
import org.jetbrains.kotlin.psi.KtLambdaExpression;
import org.jetbrains.kotlin.psi.KtPsiUtil;
import org.jetbrains.kotlin.psi.KtReturnExpression;
import org.jetbrains.kotlin.psi.KtTypeReference;
import org.jetbrains.kotlin.psi.ValueArgument;
import org.jetbrains.kotlin.psi.psiUtil.KtPsiUtilKt;
import org.jetbrains.kotlin.resolve.BindingContext;
import org.jetbrains.kotlin.resolve.BindingTrace;
import org.jetbrains.kotlin.resolve.KtDescriptorUtilKt;
import org.jetbrains.kotlin.resolve.MissingSupertypesResolver;
import org.jetbrains.kotlin.resolve.RecursiveContractHelperKt;
import org.jetbrains.kotlin.resolve.StatementFilter;
import org.jetbrains.kotlin.resolve.TemporaryBindingTrace;
import org.jetbrains.kotlin.resolve.TypeResolver;
import org.jetbrains.kotlin.resolve.calls.ArgumentTypeResolver;
import org.jetbrains.kotlin.resolve.calls.KotlinCallResolver;
import org.jetbrains.kotlin.resolve.calls.components.ClassicTypeSystemContextForCSKt;
import org.jetbrains.kotlin.resolve.calls.components.InferenceSession;
import org.jetbrains.kotlin.resolve.calls.components.KotlinResolutionCallbacks;
import org.jetbrains.kotlin.resolve.calls.components.PostponedArgumentsAnalyzer;
import org.jetbrains.kotlin.resolve.calls.components.ReturnArgumentsAnalysisResult;
import org.jetbrains.kotlin.resolve.calls.components.ReturnArgumentsInfo;
import org.jetbrains.kotlin.resolve.calls.components.candidate.CallableReferenceResolutionCandidate;
import org.jetbrains.kotlin.resolve.calls.components.candidate.SimpleResolutionCandidate;
import org.jetbrains.kotlin.resolve.calls.context.BasicCallResolutionContext;
import org.jetbrains.kotlin.resolve.calls.context.ContextDependency;
import org.jetbrains.kotlin.resolve.calls.inference.BuilderInferenceSession;
import org.jetbrains.kotlin.resolve.calls.inference.NewConstraintSystem;
import org.jetbrains.kotlin.resolve.calls.inference.components.KotlinConstraintSystemCompleter;
import org.jetbrains.kotlin.resolve.calls.inference.components.ResultTypeResolver;
import org.jetbrains.kotlin.resolve.calls.inference.components.TypeVariableDirectionCalculator;
import org.jetbrains.kotlin.resolve.calls.inference.model.ConstraintStorage;
import org.jetbrains.kotlin.resolve.calls.inference.model.NewTypeVariable;
import org.jetbrains.kotlin.resolve.calls.inference.model.TypeVariableTypeConstructor;
import org.jetbrains.kotlin.resolve.calls.inference.model.VariableWithConstraints;
import org.jetbrains.kotlin.resolve.calls.model.CallableReferenceKotlinCallArgument;
import org.jetbrains.kotlin.resolve.calls.model.KotlinCall;
import org.jetbrains.kotlin.resolve.calls.model.KotlinCallArgument;
import org.jetbrains.kotlin.resolve.calls.model.KotlinCallComponents;
import org.jetbrains.kotlin.resolve.calls.model.LHSResult;
import org.jetbrains.kotlin.resolve.calls.model.LambdaKotlinCallArgument;
import org.jetbrains.kotlin.resolve.calls.model.ReceiverKotlinCallArgument;
import org.jetbrains.kotlin.resolve.calls.model.ResolvedCallAtom;
import org.jetbrains.kotlin.resolve.calls.model.ResolvedLambdaAtom;
import org.jetbrains.kotlin.resolve.calls.smartcasts.DataFlowInfo;
import org.jetbrains.kotlin.resolve.calls.smartcasts.DataFlowValueFactory;
import org.jetbrains.kotlin.resolve.calls.tasks.TracingStrategy;
import org.jetbrains.kotlin.resolve.calls.tasks.TracingStrategyImpl;
import org.jetbrains.kotlin.resolve.calls.util.CallMaker;
import org.jetbrains.kotlin.resolve.calls.util.CallUtilKt;
import org.jetbrains.kotlin.resolve.constants.CompileTimeConstant;
import org.jetbrains.kotlin.resolve.constants.IntegerValueTypeConstant;
import org.jetbrains.kotlin.resolve.constants.evaluate.ConstantExpressionEvaluator;
import org.jetbrains.kotlin.resolve.deprecation.DeprecationResolver;
import org.jetbrains.kotlin.resolve.descriptorUtil.DescriptorUtilsKt;
import org.jetbrains.kotlin.resolve.inline.InlineUtil;
import org.jetbrains.kotlin.resolve.scopes.LexicalScope;
import org.jetbrains.kotlin.types.KotlinType;
import org.jetbrains.kotlin.types.SimpleType;
import org.jetbrains.kotlin.types.StubTypeForBuilderInference;
import org.jetbrains.kotlin.types.TypeApproximator;
import org.jetbrains.kotlin.types.TypeApproximatorConfiguration;
import org.jetbrains.kotlin.types.TypeUtils;
import org.jetbrains.kotlin.types.UnwrappedType;
import org.jetbrains.kotlin.types.expressions.DoubleColonExpressionResolver;
import org.jetbrains.kotlin.types.expressions.ExpressionTypingServices;
import org.jetbrains.kotlin.types.expressions.KotlinTypeInfo;
import org.jetbrains.kotlin.types.model.KotlinTypeMarker;
import org.jetbrains.kotlin.types.typeUtil.TypeUtilsKt;
import org.jetbrains.kotlin.util.slicedMap.WritableSlice;

/* JADX INFO: compiled from: KotlinResolutionCallbacksImpl.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000Ê\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\"\n\u0002\u0010&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001:\u0001xB\u00ad\u0001\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\t\u0012\u0006\u0010\n\u001a\u00020\u000b\u0012\u0006\u0010\f\u001a\u00020\r\u0012\u0006\u0010\u000e\u001a\u00020\u000f\u0012\u0006\u0010\u0010\u001a\u00020\u0011\u0012\u0006\u0010\u0012\u001a\u00020\u0013\u0012\u0006\u0010\u0014\u001a\u00020\u0015\u0012\u0006\u0010\u0016\u001a\u00020\u0017\u0012\u0006\u0010\u0018\u001a\u00020\u0019\u0012\u0006\u0010\u001a\u001a\u00020\u001b\u0012\u0006\u0010\u001c\u001a\u00020\u001d\u0012\u0006\u0010\u001e\u001a\u00020\u001f\u0012\u0006\u0010 \u001a\u00020!\u0012\u0006\u0010\"\u001a\u00020#\u0012\u0006\u0010$\u001a\u00020%\u0012\u0006\u0010&\u001a\u00020'\u0012\u0006\u0010(\u001a\u00020)\u0012\u0006\u0010*\u001a\u00020+¢\u0006\u0002\u0010,Jd\u00101\u001a\u0002022\u0006\u00103\u001a\u0002042\u0006\u00105\u001a\u0002062\b\u00107\u001a\u0004\u0018\u0001082\f\u00109\u001a\b\u0012\u0004\u0012\u0002080:2\f\u0010;\u001a\b\u0012\u0004\u0012\u0002080:2\b\u0010<\u001a\u0004\u0018\u0001082\u0006\u0010=\u001a\u00020>2\u0012\u0010?\u001a\u000e\u0012\u0004\u0012\u00020A\u0012\u0004\u0012\u00020B0@H\u0016J\u0010\u0010C\u001a\u00020D2\u0006\u0010E\u001a\u00020FH\u0016J\u0014\u0010G\u001a\u0002062\n\u0010H\u001a\u0006\u0012\u0002\b\u00030IH\u0002J\u0012\u0010J\u001a\u0004\u0018\u00010K2\u0006\u0010L\u001a\u00020MH\u0002J\u0012\u0010J\u001a\u0004\u0018\u00010K2\u0006\u0010N\u001a\u00020OH\u0016J\b\u0010P\u001a\u00020QH\u0016J\u0010\u0010R\u001a\u00020D2\u0006\u0010S\u001a\u00020FH\u0016J\u001a\u0010T\u001a\u00020M2\u0006\u0010U\u001a\u00020M2\b\u0010V\u001a\u0004\u0018\u00010WH\u0002J\u001a\u0010X\u001a\u0004\u0018\u00010Y2\u0006\u0010Z\u001a\u00020Q2\u0006\u0010[\u001a\u00020\\H\u0016J\u001c\u0010]\u001a\u00060^R\u00020\u00172\u0006\u0010_\u001a\u00020`2\u0006\u0010a\u001a\u00020bH\u0016J\u0012\u0010c\u001a\u0004\u0018\u0001082\u0006\u0010S\u001a\u00020FH\u0016J\u0012\u0010d\u001a\u0004\u0018\u00010M2\u0006\u0010e\u001a\u00020fH\u0002J\u0010\u0010g\u001a\u00020h2\u0006\u0010i\u001a\u00020bH\u0016J\u0018\u0010j\u001a\u0002062\u0006\u0010S\u001a\u00020F2\u0006\u0010k\u001a\u000208H\u0016J\"\u0010l\u001a\u00020D2\u0018\u0010m\u001a\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020p\u0012\u0004\u0012\u00020q0o0nH\u0016J(\u0010r\u001a\b\u0012\u0004\u0012\u00020t0s2\u0006\u0010N\u001a\u00020u2\b\u0010k\u001a\u0004\u0018\u0001082\u0006\u0010v\u001a\u00020wH\u0016R\u000e\u0010\b\u001a\u00020\tX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u001c\u001a\u00020\u001dX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0012\u001a\u00020\u0013X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\u000fX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010 \u001a\u00020!X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u001e\u001a\u00020\u001fX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u0010\u001a\u00020\u0011X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b-\u0010.R\u000e\u0010(\u001a\u00020)X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u001a\u001a\u00020\u001bX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\rX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u000bX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010&\u001a\u00020'X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\"\u001a\u00020#X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0018\u001a\u00020\u0019X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0016\u001a\u00020\u0017X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010*\u001a\u00020+X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010$\u001a\u00020%X\u0082\u0004¢\u0006\u0002\n\u0000R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b/\u00100R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0014\u001a\u00020\u0015X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006y"}, d2 = {"Lorg/jetbrains/kotlin/resolve/calls/tower/KotlinResolutionCallbacksImpl;", "Lorg/jetbrains/kotlin/resolve/calls/components/KotlinResolutionCallbacks;", "trace", "Lorg/jetbrains/kotlin/resolve/BindingTrace;", "expressionTypingServices", "Lorg/jetbrains/kotlin/types/expressions/ExpressionTypingServices;", "typeApproximator", "Lorg/jetbrains/kotlin/types/TypeApproximator;", "argumentTypeResolver", "Lorg/jetbrains/kotlin/resolve/calls/ArgumentTypeResolver;", "languageVersionSettings", "Lorg/jetbrains/kotlin/config/LanguageVersionSettings;", "kotlinToResolvedCallTransformer", "Lorg/jetbrains/kotlin/resolve/calls/tower/KotlinToResolvedCallTransformer;", "dataFlowValueFactory", "Lorg/jetbrains/kotlin/resolve/calls/smartcasts/DataFlowValueFactory;", "inferenceSession", "Lorg/jetbrains/kotlin/resolve/calls/components/InferenceSession;", "constantExpressionEvaluator", "Lorg/jetbrains/kotlin/resolve/constants/evaluate/ConstantExpressionEvaluator;", "typeResolver", "Lorg/jetbrains/kotlin/resolve/TypeResolver;", "psiCallResolver", "Lorg/jetbrains/kotlin/resolve/calls/tower/PSICallResolver;", "postponedArgumentsAnalyzer", "Lorg/jetbrains/kotlin/resolve/calls/components/PostponedArgumentsAnalyzer;", "kotlinConstraintSystemCompleter", "Lorg/jetbrains/kotlin/resolve/calls/inference/components/KotlinConstraintSystemCompleter;", "callComponents", "Lorg/jetbrains/kotlin/resolve/calls/model/KotlinCallComponents;", "doubleColonExpressionResolver", "Lorg/jetbrains/kotlin/types/expressions/DoubleColonExpressionResolver;", "deprecationResolver", "Lorg/jetbrains/kotlin/resolve/deprecation/DeprecationResolver;", "moduleDescriptor", "Lorg/jetbrains/kotlin/descriptors/ModuleDescriptor;", "topLevelCallContext", "Lorg/jetbrains/kotlin/resolve/calls/context/BasicCallResolutionContext;", "missingSupertypesResolver", "Lorg/jetbrains/kotlin/resolve/MissingSupertypesResolver;", "kotlinCallResolver", "Lorg/jetbrains/kotlin/resolve/calls/KotlinCallResolver;", "resultTypeResolver", "Lorg/jetbrains/kotlin/resolve/calls/inference/components/ResultTypeResolver;", "(Lorg/jetbrains/kotlin/resolve/BindingTrace;Lorg/jetbrains/kotlin/types/expressions/ExpressionTypingServices;Lorg/jetbrains/kotlin/types/TypeApproximator;Lorg/jetbrains/kotlin/resolve/calls/ArgumentTypeResolver;Lorg/jetbrains/kotlin/config/LanguageVersionSettings;Lorg/jetbrains/kotlin/resolve/calls/tower/KotlinToResolvedCallTransformer;Lorg/jetbrains/kotlin/resolve/calls/smartcasts/DataFlowValueFactory;Lorg/jetbrains/kotlin/resolve/calls/components/InferenceSession;Lorg/jetbrains/kotlin/resolve/constants/evaluate/ConstantExpressionEvaluator;Lorg/jetbrains/kotlin/resolve/TypeResolver;Lorg/jetbrains/kotlin/resolve/calls/tower/PSICallResolver;Lorg/jetbrains/kotlin/resolve/calls/components/PostponedArgumentsAnalyzer;Lorg/jetbrains/kotlin/resolve/calls/inference/components/KotlinConstraintSystemCompleter;Lorg/jetbrains/kotlin/resolve/calls/model/KotlinCallComponents;Lorg/jetbrains/kotlin/types/expressions/DoubleColonExpressionResolver;Lorg/jetbrains/kotlin/resolve/deprecation/DeprecationResolver;Lorg/jetbrains/kotlin/descriptors/ModuleDescriptor;Lorg/jetbrains/kotlin/resolve/calls/context/BasicCallResolutionContext;Lorg/jetbrains/kotlin/resolve/MissingSupertypesResolver;Lorg/jetbrains/kotlin/resolve/calls/KotlinCallResolver;Lorg/jetbrains/kotlin/resolve/calls/inference/components/ResultTypeResolver;)V", "getInferenceSession", "()Lorg/jetbrains/kotlin/resolve/calls/components/InferenceSession;", "getTrace", "()Lorg/jetbrains/kotlin/resolve/BindingTrace;", "analyzeAndGetLambdaReturnArguments", "Lorg/jetbrains/kotlin/resolve/calls/components/ReturnArgumentsAnalysisResult;", "lambdaArgument", "Lorg/jetbrains/kotlin/resolve/calls/model/LambdaKotlinCallArgument;", "isSuspend", "", "receiverType", "Lorg/jetbrains/kotlin/types/UnwrappedType;", "contextReceiversTypes", "", "parameters", "expectedReturnType", "annotations", "Lorg/jetbrains/kotlin/descriptors/annotations/Annotations;", "stubsForPostponedVariables", "", "Lorg/jetbrains/kotlin/resolve/calls/inference/model/NewTypeVariable;", "Lorg/jetbrains/kotlin/types/StubTypeForBuilderInference;", "bindStubResolvedCallForCandidate", "", "candidate", "Lorg/jetbrains/kotlin/resolve/calls/model/ResolvedCallAtom;", "constantCanBeConvertedToUnsigned", "constant", "Lorg/jetbrains/kotlin/resolve/constants/CompileTimeConstant;", "convertSignedConstantToUnsigned", "Lorg/jetbrains/kotlin/resolve/constants/IntegerValueTypeConstant;", "expression", "Lorg/jetbrains/kotlin/psi/KtExpression;", "argument", "Lorg/jetbrains/kotlin/resolve/calls/model/KotlinCallArgument;", "createEmptyConstraintSystem", "Lorg/jetbrains/kotlin/resolve/calls/inference/NewConstraintSystem;", "disableContractsIfNecessary", "resolvedAtom", "findCommonParent", "callElement", "receiver", "Lorg/jetbrains/kotlin/resolve/calls/model/ReceiverKotlinCallArgument;", "findResultType", "Lorg/jetbrains/kotlin/types/KotlinType;", "constraintSystem", "typeVariable", "Lorg/jetbrains/kotlin/resolve/calls/inference/model/TypeVariableTypeConstructor;", "getCandidateFactoryForInvoke", "Lorg/jetbrains/kotlin/resolve/calls/tower/PSICallResolver$FactoryProviderForInvoke;", "scopeTower", "Lorg/jetbrains/kotlin/resolve/calls/tower/ImplicitScopeTower;", "kotlinCall", "Lorg/jetbrains/kotlin/resolve/calls/model/KotlinCall;", "getExpectedTypeFromAsExpressionAndRecordItInTrace", "getLastDeparentesizedExpression", "psiCallArgument", "Lorg/jetbrains/kotlin/resolve/calls/tower/PSIKotlinCallArgument;", "getLhsResult", "Lorg/jetbrains/kotlin/resolve/calls/model/LHSResult;", "call", "isCompileTimeConstant", "expectedType", "recordInlinabilityOfLambda", "atom", "", "", "Lorg/jetbrains/kotlin/resolve/calls/components/candidate/SimpleResolutionCandidate;", "Lorg/jetbrains/kotlin/resolve/calls/model/ResolvedLambdaAtom;", "resolveCallableReferenceArgument", "", "Lorg/jetbrains/kotlin/resolve/calls/components/candidate/CallableReferenceResolutionCandidate;", "Lorg/jetbrains/kotlin/resolve/calls/model/CallableReferenceKotlinCallArgument;", "baseSystem", "Lorg/jetbrains/kotlin/resolve/calls/inference/model/ConstraintStorage;", "LambdaInfo", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class KotlinResolutionCallbacksImpl implements KotlinResolutionCallbacks {
    private final ArgumentTypeResolver argumentTypeResolver;
    private final KotlinCallComponents callComponents;
    private final ConstantExpressionEvaluator constantExpressionEvaluator;
    private final DataFlowValueFactory dataFlowValueFactory;
    private final DeprecationResolver deprecationResolver;
    private final DoubleColonExpressionResolver doubleColonExpressionResolver;
    private final ExpressionTypingServices expressionTypingServices;
    private final InferenceSession inferenceSession;
    private final KotlinCallResolver kotlinCallResolver;
    private final KotlinConstraintSystemCompleter kotlinConstraintSystemCompleter;
    private final KotlinToResolvedCallTransformer kotlinToResolvedCallTransformer;
    private final LanguageVersionSettings languageVersionSettings;
    private final MissingSupertypesResolver missingSupertypesResolver;
    private final ModuleDescriptor moduleDescriptor;
    private final PostponedArgumentsAnalyzer postponedArgumentsAnalyzer;
    private final PSICallResolver psiCallResolver;
    private final ResultTypeResolver resultTypeResolver;
    private final BasicCallResolutionContext topLevelCallContext;
    private final BindingTrace trace;
    private final TypeApproximator typeApproximator;
    private final TypeResolver typeResolver;

    public KotlinResolutionCallbacksImpl(BindingTrace trace, ExpressionTypingServices expressionTypingServices, TypeApproximator typeApproximator, ArgumentTypeResolver argumentTypeResolver, LanguageVersionSettings languageVersionSettings, KotlinToResolvedCallTransformer kotlinToResolvedCallTransformer, DataFlowValueFactory dataFlowValueFactory, InferenceSession inferenceSession, ConstantExpressionEvaluator constantExpressionEvaluator, TypeResolver typeResolver, PSICallResolver psiCallResolver, PostponedArgumentsAnalyzer postponedArgumentsAnalyzer, KotlinConstraintSystemCompleter kotlinConstraintSystemCompleter, KotlinCallComponents callComponents, DoubleColonExpressionResolver doubleColonExpressionResolver, DeprecationResolver deprecationResolver, ModuleDescriptor moduleDescriptor, BasicCallResolutionContext topLevelCallContext, MissingSupertypesResolver missingSupertypesResolver, KotlinCallResolver kotlinCallResolver, ResultTypeResolver resultTypeResolver) {
        Intrinsics.checkNotNullParameter(trace, "trace");
        Intrinsics.checkNotNullParameter(expressionTypingServices, "expressionTypingServices");
        Intrinsics.checkNotNullParameter(typeApproximator, "typeApproximator");
        Intrinsics.checkNotNullParameter(argumentTypeResolver, "argumentTypeResolver");
        Intrinsics.checkNotNullParameter(languageVersionSettings, "languageVersionSettings");
        Intrinsics.checkNotNullParameter(kotlinToResolvedCallTransformer, "kotlinToResolvedCallTransformer");
        Intrinsics.checkNotNullParameter(dataFlowValueFactory, "dataFlowValueFactory");
        Intrinsics.checkNotNullParameter(inferenceSession, "inferenceSession");
        Intrinsics.checkNotNullParameter(constantExpressionEvaluator, "constantExpressionEvaluator");
        Intrinsics.checkNotNullParameter(typeResolver, "typeResolver");
        Intrinsics.checkNotNullParameter(psiCallResolver, "psiCallResolver");
        Intrinsics.checkNotNullParameter(postponedArgumentsAnalyzer, "postponedArgumentsAnalyzer");
        Intrinsics.checkNotNullParameter(kotlinConstraintSystemCompleter, "kotlinConstraintSystemCompleter");
        Intrinsics.checkNotNullParameter(callComponents, "callComponents");
        Intrinsics.checkNotNullParameter(doubleColonExpressionResolver, "doubleColonExpressionResolver");
        Intrinsics.checkNotNullParameter(deprecationResolver, "deprecationResolver");
        Intrinsics.checkNotNullParameter(moduleDescriptor, "moduleDescriptor");
        Intrinsics.checkNotNullParameter(topLevelCallContext, "topLevelCallContext");
        Intrinsics.checkNotNullParameter(missingSupertypesResolver, "missingSupertypesResolver");
        Intrinsics.checkNotNullParameter(kotlinCallResolver, "kotlinCallResolver");
        Intrinsics.checkNotNullParameter(resultTypeResolver, "resultTypeResolver");
        this.trace = trace;
        this.expressionTypingServices = expressionTypingServices;
        this.typeApproximator = typeApproximator;
        this.argumentTypeResolver = argumentTypeResolver;
        this.languageVersionSettings = languageVersionSettings;
        this.kotlinToResolvedCallTransformer = kotlinToResolvedCallTransformer;
        this.dataFlowValueFactory = dataFlowValueFactory;
        this.inferenceSession = inferenceSession;
        this.constantExpressionEvaluator = constantExpressionEvaluator;
        this.typeResolver = typeResolver;
        this.psiCallResolver = psiCallResolver;
        this.postponedArgumentsAnalyzer = postponedArgumentsAnalyzer;
        this.kotlinConstraintSystemCompleter = kotlinConstraintSystemCompleter;
        this.callComponents = callComponents;
        this.doubleColonExpressionResolver = doubleColonExpressionResolver;
        this.deprecationResolver = deprecationResolver;
        this.moduleDescriptor = moduleDescriptor;
        this.topLevelCallContext = topLevelCallContext;
        this.missingSupertypesResolver = missingSupertypesResolver;
        this.kotlinCallResolver = kotlinCallResolver;
        this.resultTypeResolver = resultTypeResolver;
    }

    public final BindingTrace getTrace() {
        return this.trace;
    }

    @Override // org.jetbrains.kotlin.resolve.calls.components.KotlinResolutionCallbacks
    public InferenceSession getInferenceSession() {
        return this.inferenceSession;
    }

    /* JADX INFO: compiled from: KotlinResolutionCallbacksImpl.kt */
    @Metadata(d1 = {"\u00004\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\u0018\u0000 \u00162\u00020\u0001:\u0001\u0016B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006R\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u0011\u0010\u000b\u001a\u00020\f¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000eR=\u0010\u000f\u001a.\u0012\u0012\u0012\u0010\u0012\u0004\u0012\u00020\u0012\u0012\u0006\u0012\u0004\u0018\u00010\f0\u00110\u0010j\u0016\u0012\u0012\u0012\u0010\u0012\u0004\u0012\u00020\u0012\u0012\u0006\u0012\u0004\u0018\u00010\f0\u0011`\u0013¢\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\u0015¨\u0006\u0017"}, d2 = {"Lorg/jetbrains/kotlin/resolve/calls/tower/KotlinResolutionCallbacksImpl$LambdaInfo;", "", "expectedType", "Lorg/jetbrains/kotlin/types/UnwrappedType;", "contextDependency", "Lorg/jetbrains/kotlin/resolve/calls/context/ContextDependency;", "(Lorg/jetbrains/kotlin/types/UnwrappedType;Lorg/jetbrains/kotlin/resolve/calls/context/ContextDependency;)V", "getContextDependency", "()Lorg/jetbrains/kotlin/resolve/calls/context/ContextDependency;", "getExpectedType", "()Lorg/jetbrains/kotlin/types/UnwrappedType;", "lastExpressionInfo", "Lorg/jetbrains/kotlin/resolve/calls/tower/LambdaContextInfo;", "getLastExpressionInfo", "()Lorg/jetbrains/kotlin/resolve/calls/tower/LambdaContextInfo;", "returnStatements", "Ljava/util/ArrayList;", "Lkotlin/Pair;", "Lorg/jetbrains/kotlin/psi/KtReturnExpression;", "Lkotlin/collections/ArrayList;", "getReturnStatements", "()Ljava/util/ArrayList;", "Companion", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class LambdaInfo {

        /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
        public static final Companion INSTANCE = new Companion(null);
        private static final LambdaInfo STUB_EMPTY;
        private final ContextDependency contextDependency;
        private final UnwrappedType expectedType;
        private final LambdaContextInfo lastExpressionInfo;
        private final ArrayList<Pair<KtReturnExpression, LambdaContextInfo>> returnStatements;

        public LambdaInfo(UnwrappedType expectedType, ContextDependency contextDependency) {
            Intrinsics.checkNotNullParameter(expectedType, "expectedType");
            Intrinsics.checkNotNullParameter(contextDependency, "contextDependency");
            this.expectedType = expectedType;
            this.contextDependency = contextDependency;
            this.returnStatements = new ArrayList<>();
            this.lastExpressionInfo = new LambdaContextInfo(null, null, null, null, 15, null);
        }

        public final ContextDependency getContextDependency() {
            return this.contextDependency;
        }

        public final UnwrappedType getExpectedType() {
            return this.expectedType;
        }

        public final ArrayList<Pair<KtReturnExpression, LambdaContextInfo>> getReturnStatements() {
            return this.returnStatements;
        }

        public final LambdaContextInfo getLastExpressionInfo() {
            return this.lastExpressionInfo;
        }

        /* JADX INFO: compiled from: KotlinResolutionCallbacksImpl.kt */
        @Metadata(d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u0011\u0010\u0003\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u0007"}, d2 = {"Lorg/jetbrains/kotlin/resolve/calls/tower/KotlinResolutionCallbacksImpl$LambdaInfo$Companion;", "", "()V", "STUB_EMPTY", "Lorg/jetbrains/kotlin/resolve/calls/tower/KotlinResolutionCallbacksImpl$LambdaInfo;", "getSTUB_EMPTY", "()Lorg/jetbrains/kotlin/resolve/calls/tower/KotlinResolutionCallbacksImpl$LambdaInfo;", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
        public static final class Companion {
            public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
                this();
            }

            private Companion() {
            }

            public final LambdaInfo getSTUB_EMPTY() {
                return LambdaInfo.STUB_EMPTY;
            }
        }

        static {
            SimpleType NO_EXPECTED_TYPE = TypeUtils.NO_EXPECTED_TYPE;
            Intrinsics.checkNotNullExpressionValue(NO_EXPECTED_TYPE, "NO_EXPECTED_TYPE");
            STUB_EMPTY = new LambdaInfo(NO_EXPECTED_TYPE, ContextDependency.INDEPENDENT);
        }
    }

    @Override // org.jetbrains.kotlin.resolve.calls.components.KotlinResolutionCallbacks
    public KotlinType findResultType(NewConstraintSystem constraintSystem, TypeVariableTypeConstructor typeVariable) {
        Intrinsics.checkNotNullParameter(constraintSystem, "constraintSystem");
        Intrinsics.checkNotNullParameter(typeVariable, "typeVariable");
        VariableWithConstraints variableWithConstraints = constraintSystem.getBuilder().currentStorage().getNotFixedTypeVariables().get(typeVariable);
        if (variableWithConstraints == null) {
            return null;
        }
        KotlinTypeMarker kotlinTypeMarkerFindResultType = this.resultTypeResolver.findResultType(constraintSystem.asConstraintSystemCompleterContext(), variableWithConstraints, TypeVariableDirectionCalculator.ResolveDirection.UNKNOWN);
        Intrinsics.checkNotNull(kotlinTypeMarkerFindResultType, "null cannot be cast to non-null type org.jetbrains.kotlin.types.KotlinType");
        return (KotlinType) kotlinTypeMarkerFindResultType;
    }

    @Override // org.jetbrains.kotlin.resolve.calls.components.KotlinResolutionCallbacks
    public NewConstraintSystem createEmptyConstraintSystem() {
        return ClassicTypeSystemContextForCSKt.NewConstraintSystemImpl(this.callComponents.getConstraintInjector(), this.callComponents.getBuiltIns(), this.callComponents.getKotlinTypeRefiner(), this.callComponents.getLanguageVersionSettings());
    }

    @Override // org.jetbrains.kotlin.resolve.calls.components.KotlinResolutionCallbacks
    public Collection<CallableReferenceResolutionCandidate> resolveCallableReferenceArgument(CallableReferenceKotlinCallArgument argument, UnwrappedType expectedType, ConstraintStorage baseSystem) {
        Intrinsics.checkNotNullParameter(argument, "argument");
        Intrinsics.checkNotNullParameter(baseSystem, "baseSystem");
        return this.kotlinCallResolver.resolveCallableReferenceArgument(argument, expectedType, baseSystem, this);
    }

    @Override // org.jetbrains.kotlin.resolve.calls.components.KotlinResolutionCallbacks
    public PSICallResolver.FactoryProviderForInvoke getCandidateFactoryForInvoke(ImplicitScopeTower scopeTower, KotlinCall kotlinCall) {
        Intrinsics.checkNotNullParameter(scopeTower, "scopeTower");
        Intrinsics.checkNotNullParameter(kotlinCall, "kotlinCall");
        return new PSICallResolver.FactoryProviderForInvoke(this.psiCallResolver, this.topLevelCallContext, scopeTower, (PSIKotlinCallImpl) kotlinCall);
    }

    /* JADX WARN: Code duplicated, block: B:80:0x028c  */
    /* JADX WARN: Multi-variable type inference failed */
    @Override // org.jetbrains.kotlin.resolve.calls.components.KotlinResolutionCallbacks
    public ReturnArgumentsAnalysisResult analyzeAndGetLambdaReturnArguments(LambdaKotlinCallArgument lambdaArgument, boolean isSuspend, UnwrappedType receiverType, List<? extends UnwrappedType> contextReceiversTypes, List<? extends UnwrappedType> parameters, UnwrappedType expectedReturnType, Annotations annotations, Map<NewTypeVariable, StubTypeForBuilderInference> stubsForPostponedVariables) {
        SimpleType simpleType;
        LambdaInfo lambdaInfo;
        BuilderInferenceSession builderInferenceSession;
        DataFlowInfo dataFlowInfoAfter;
        KotlinBuiltIns kotlinBuiltIns;
        KotlinCallArgument emptyLabeledReturn;
        KotlinTypeInfo typeInfo;
        Intrinsics.checkNotNullParameter(lambdaArgument, "lambdaArgument");
        Intrinsics.checkNotNullParameter(contextReceiversTypes, "contextReceiversTypes");
        Intrinsics.checkNotNullParameter(parameters, "parameters");
        Intrinsics.checkNotNullParameter(annotations, "annotations");
        Intrinsics.checkNotNullParameter(stubsForPostponedVariables, "stubsForPostponedVariables");
        PSIKotlinCallArgument psiCallArgument = NewCallArgumentsKt.getPsiCallArgument(lambdaArgument);
        Intrinsics.checkNotNull(psiCallArgument, "null cannot be cast to non-null type org.jetbrains.kotlin.resolve.calls.tower.PSIFunctionKotlinCallArgument");
        PSIFunctionKotlinCallArgument pSIFunctionKotlinCallArgument = (PSIFunctionKotlinCallArgument) psiCallArgument;
        BasicCallResolutionContext outerCallContext = pSIFunctionKotlinCallArgument.getOuterCallContext();
        if (expectedReturnType == null) {
            SimpleType NO_EXPECTED_TYPE = TypeUtils.NO_EXPECTED_TYPE;
            Intrinsics.checkNotNullExpressionValue(NO_EXPECTED_TYPE, "NO_EXPECTED_TYPE");
            simpleType = NO_EXPECTED_TYPE;
        } else {
            simpleType = expectedReturnType;
        }
        LambdaInfo lambdaInfo2 = new LambdaInfo(simpleType, expectedReturnType == null ? ContextDependency.DEPENDENT : ContextDependency.INDEPENDENT);
        KotlinBuiltIns builtIns = DescriptorUtilsKt.getBuiltIns(outerCallContext.scope.getOwnerDescriptor());
        PSIKotlinCallArgument pSIKotlinCallArgumentAnalyzeAndGetLambdaReturnArguments$createCallArgument = null;
        KotlinType kotlinTypeRefineType = receiverType != null ? this.callComponents.getKotlinTypeChecker().getKotlinTypeRefiner().refineType((KotlinTypeMarker) receiverType) : null;
        List<? extends UnwrappedType> list = contextReceiversTypes;
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(list, 10));
        Iterator<T> it = list.iterator();
        while (it.hasNext()) {
            arrayList.add(this.callComponents.getKotlinTypeChecker().getKotlinTypeRefiner().refineType((KotlinTypeMarker) it.next()));
        }
        KotlinBuiltIns kotlinBuiltIns2 = builtIns;
        SimpleType simpleTypeCreateFunctionType = FunctionTypesKt.createFunctionType(builtIns, annotations, kotlinTypeRefineType, arrayList, parameters, null, lambdaInfo2.getExpectedType(), isSuspend);
        UnwrappedType unwrappedTypeApproximateToSubType = this.typeApproximator.approximateToSubType((UnwrappedType) simpleTypeCreateFunctionType, (TypeApproximatorConfiguration) TypeApproximatorConfiguration.LocalDeclaration.INSTANCE);
        if (unwrappedTypeApproximateToSubType != null) {
            simpleTypeCreateFunctionType = unwrappedTypeApproximateToSubType;
        }
        if (stubsForPostponedVariables.isEmpty()) {
            lambdaInfo = lambdaInfo2;
            builderInferenceSession = null;
        } else {
            lambdaInfo = lambdaInfo2;
            BuilderInferenceSession builderInferenceSession2 = new BuilderInferenceSession(this.psiCallResolver, this.postponedArgumentsAnalyzer, this.kotlinConstraintSystemCompleter, this.callComponents, kotlinBuiltIns2, this.topLevelCallContext, stubsForPostponedVariables, this.trace, this.kotlinToResolvedCallTransformer, this.expressionTypingServices, this.argumentTypeResolver, this.doubleColonExpressionResolver, this.deprecationResolver, this.moduleDescriptor, this.typeApproximator, this.missingSupertypesResolver, lambdaArgument);
            lambdaArgument.setBuilderInferenceSession(builderInferenceSession2);
            builderInferenceSession = builderInferenceSession2;
        }
        TemporaryBindingTrace temporaryBindingTraceCreate = builderInferenceSession != null ? TemporaryBindingTrace.create(this.trace, "Trace to resolve builder inference lambda: " + lambdaArgument) : null;
        (temporaryBindingTraceCreate != null ? temporaryBindingTraceCreate : this.trace).record(BindingContext.NEW_INFERENCE_LAMBDA_INFO, pSIFunctionKotlinCallArgument.getKtFunction(), lambdaInfo);
        BasicCallResolutionContext basicCallResolutionContext = (BasicCallResolutionContext) ((BasicCallResolutionContext) ((BasicCallResolutionContext) ((BasicCallResolutionContext) outerCallContext.replaceBindingTrace(temporaryBindingTraceCreate != null ? temporaryBindingTraceCreate : this.trace)).replaceContextDependency(lambdaInfo.getContextDependency())).replaceExpectedType(simpleTypeCreateFunctionType)).replaceDataFlowInfo(pSIFunctionKotlinCallArgument.getDataFlowInfoBeforeThisArgument());
        if (builderInferenceSession != null) {
            basicCallResolutionContext = (BasicCallResolutionContext) basicCallResolutionContext.replaceInferenceSession(builderInferenceSession);
        }
        Intrinsics.checkNotNullExpressionValue(basicCallResolutionContext, "let(...)");
        KotlinTypeInfo typeInfo2 = this.expressionTypingServices.getTypeInfo(pSIFunctionKotlinCallArgument.getExpression(), basicCallResolutionContext);
        Intrinsics.checkNotNullExpressionValue(typeInfo2, "getTypeInfo(...)");
        (temporaryBindingTraceCreate != null ? temporaryBindingTraceCreate : this.trace).record(BindingContext.NEW_INFERENCE_LAMBDA_INFO, pSIFunctionKotlinCallArgument.getKtFunction(), LambdaInfo.INSTANCE.getSTUB_EMPTY());
        boolean z = true;
        if (builderInferenceSession != null && builderInferenceSession.getHasInapplicableCall()) {
            return new ReturnArgumentsAnalysisResult(ReturnArgumentsInfo.INSTANCE.getEmpty(), builderInferenceSession, true);
        }
        if (temporaryBindingTraceCreate != null) {
            temporaryBindingTraceCreate.commit();
        }
        ArrayList<Pair<KtReturnExpression, LambdaContextInfo>> returnStatements = lambdaInfo.getReturnStatements();
        ArrayList arrayList2 = new ArrayList();
        Iterator<T> it2 = returnStatements.iterator();
        boolean z2 = false;
        boolean z3 = false;
        while (it2.hasNext()) {
            Pair pair = (Pair) it2.next();
            KtReturnExpression ktReturnExpression = (KtReturnExpression) pair.component1();
            LambdaContextInfo lambdaContextInfo = (LambdaContextInfo) pair.component2();
            KtExpression returnedExpression = ktReturnExpression.getReturnedExpression();
            if (returnedExpression == null) {
                kotlinBuiltIns = kotlinBuiltIns2;
                emptyLabeledReturn = new EmptyLabeledReturn(ktReturnExpression, kotlinBuiltIns);
                z2 = true;
            } else if (lambdaContextInfo != null && (typeInfo = lambdaContextInfo.getTypeInfo()) != null) {
                kotlinBuiltIns = kotlinBuiltIns2;
                emptyLabeledReturn = analyzeAndGetLambdaReturnArguments$createCallArgument(outerCallContext, this, returnedExpression, typeInfo, lambdaContextInfo.getLexicalScope(), lambdaContextInfo.getTrace());
            } else {
                throw new AssertionError("typeInfo should be non-null for return with expression");
            }
            if (emptyLabeledReturn != null) {
                arrayList2.add(emptyLabeledReturn);
            }
            kotlinBuiltIns2 = kotlinBuiltIns;
            lambdaInfo = lambdaInfo;
            outerCallContext = outerCallContext;
            z3 = true;
        }
        BasicCallResolutionContext basicCallResolutionContext2 = outerCallContext;
        LambdaInfo lambdaInfo3 = lambdaInfo;
        ArrayList arrayList3 = arrayList2;
        KtExpression lastDeparentesizedExpression = getLastDeparentesizedExpression(pSIFunctionKotlinCallArgument);
        if (lastDeparentesizedExpression != null) {
            ArrayList<Pair<KtReturnExpression, LambdaContextInfo>> returnStatements2 = lambdaInfo3.getReturnStatements();
            if ((returnStatements2 instanceof Collection) && returnStatements2.isEmpty()) {
                KotlinType type = this.trace.getType(lastDeparentesizedExpression);
                LambdaContextInfo lastExpressionInfo = lambdaInfo3.getLastExpressionInfo();
                dataFlowInfoAfter = lastExpressionInfo.getDataFlowInfoAfter();
                if (dataFlowInfoAfter == null) {
                    dataFlowInfoAfter = typeInfo2.getDataFlowInfo();
                }
                pSIKotlinCallArgumentAnalyzeAndGetLambdaReturnArguments$createCallArgument = analyzeAndGetLambdaReturnArguments$createCallArgument(basicCallResolutionContext2, this, lastDeparentesizedExpression, new KotlinTypeInfo(type, dataFlowInfoAfter, false, null, 12, null), lastExpressionInfo.getLexicalScope(), lastExpressionInfo.getTrace());
            } else {
                Iterator<T> it3 = returnStatements2.iterator();
                while (true) {
                    if (it3.hasNext()) {
                        if (Intrinsics.areEqual((KtReturnExpression) ((Pair) it3.next()).component1(), lastDeparentesizedExpression)) {
                        }
                    } else {
                        KotlinType type2 = this.trace.getType(lastDeparentesizedExpression);
                        LambdaContextInfo lastExpressionInfo2 = lambdaInfo3.getLastExpressionInfo();
                        dataFlowInfoAfter = lastExpressionInfo2.getDataFlowInfoAfter();
                        if (dataFlowInfoAfter == null) {
                            dataFlowInfoAfter = typeInfo2.getDataFlowInfo();
                        }
                        pSIKotlinCallArgumentAnalyzeAndGetLambdaReturnArguments$createCallArgument = analyzeAndGetLambdaReturnArguments$createCallArgument(basicCallResolutionContext2, this, lastDeparentesizedExpression, new KotlinTypeInfo(type2, dataFlowInfoAfter, false, null, 12, null), lastExpressionInfo2.getLexicalScope(), lastExpressionInfo2.getTrace());
                    }
                }
            }
        }
        PSIKotlinCallArgument pSIKotlinCallArgument = pSIKotlinCallArgumentAnalyzeAndGetLambdaReturnArguments$createCallArgument;
        boolean z4 = (expectedReturnType != null && TypeUtilsKt.isUnit(expectedReturnType)) || z2;
        if (z4 || pSIKotlinCallArgument == null) {
            z = z3;
        } else {
            arrayList3.add(pSIKotlinCallArgument);
        }
        return new ReturnArgumentsAnalysisResult(new ReturnArgumentsInfo(arrayList3, pSIKotlinCallArgument, z4, z), builderInferenceSession, false, 4, null);
    }

    /* JADX WARN: Multi-variable type inference failed */
    private static final PSIKotlinCallArgument analyzeAndGetLambdaReturnArguments$createCallArgument(BasicCallResolutionContext basicCallResolutionContext, KotlinResolutionCallbacksImpl kotlinResolutionCallbacksImpl, KtExpression ktExpression, KotlinTypeInfo kotlinTypeInfo, LexicalScope lexicalScope, BindingTrace bindingTrace) {
        BasicCallResolutionContext basicCallResolutionContext2;
        if (lexicalScope != null) {
            Context contextReplaceScope = basicCallResolutionContext.replaceScope(lexicalScope);
            Intrinsics.checkNotNullExpressionValue(contextReplaceScope, "replaceScope(...)");
            basicCallResolutionContext2 = (BasicCallResolutionContext) contextReplaceScope;
        } else {
            basicCallResolutionContext2 = basicCallResolutionContext;
        }
        if (bindingTrace != null) {
            Context contextReplaceBindingTrace = basicCallResolutionContext2.replaceBindingTrace(bindingTrace);
            Intrinsics.checkNotNullExpressionValue(contextReplaceBindingTrace, "replaceBindingTrace(...)");
            basicCallResolutionContext2 = (BasicCallResolutionContext) contextReplaceBindingTrace;
        }
        DataFlowInfo dataFlowInfo = kotlinTypeInfo.getDataFlowInfo();
        ValueArgument valueArgumentMakeExternalValueArgument = CallMaker.makeExternalValueArgument(ktExpression);
        Intrinsics.checkNotNullExpressionValue(valueArgumentMakeExternalValueArgument, "makeExternalValueArgument(...)");
        PSIKotlinCallArgument pSIKotlinCallArgumentProcessFunctionalExpression = NewCallArgumentsKt.processFunctionalExpression(basicCallResolutionContext2, ktExpression, dataFlowInfo, valueArgumentMakeExternalValueArgument, null, DescriptorUtilsKt.getBuiltIns(basicCallResolutionContext.scope.getOwnerDescriptor()), kotlinResolutionCallbacksImpl.typeResolver);
        if (pSIKotlinCallArgumentProcessFunctionalExpression != null) {
            NewCallArgumentsKt.setResultDataFlowInfoIfRelevant(pSIKotlinCallArgumentProcessFunctionalExpression, kotlinTypeInfo.getDataFlowInfo());
            return pSIKotlinCallArgumentProcessFunctionalExpression;
        }
        KtExpression ktExpressionDeparenthesize = KtPsiUtil.deparenthesize(ktExpression);
        if (ktExpressionDeparenthesize == null) {
            ktExpressionDeparenthesize = ktExpression;
        }
        Intrinsics.checkNotNull(ktExpressionDeparenthesize);
        if (ktExpressionDeparenthesize instanceof KtCallableReferenceExpression) {
            PSICallResolver pSICallResolver = kotlinResolutionCallbacksImpl.psiCallResolver;
            KtCallableReferenceExpression ktCallableReferenceExpression = (KtCallableReferenceExpression) ktExpressionDeparenthesize;
            DataFlowInfo empty = DataFlowInfo.INSTANCE.getEMPTY();
            ValueArgument valueArgumentMakeExternalValueArgument2 = CallMaker.makeExternalValueArgument(ktExpressionDeparenthesize);
            Intrinsics.checkNotNullExpressionValue(valueArgumentMakeExternalValueArgument2, "makeExternalValueArgument(...)");
            TracingStrategy tracingStrategyCreate = TracingStrategyImpl.create(ktCallableReferenceExpression.getCallableReference(), basicCallResolutionContext2.call);
            Intrinsics.checkNotNullExpressionValue(tracingStrategyCreate, "create(...)");
            return pSICallResolver.createCallableReferenceKotlinCallArgument(basicCallResolutionContext2, ktCallableReferenceExpression, empty, valueArgumentMakeExternalValueArgument2, null, basicCallResolutionContext, tracingStrategyCreate);
        }
        BindingContext bindingContext = kotlinResolutionCallbacksImpl.trace.getContext();
        Intrinsics.checkNotNullExpressionValue(bindingContext, "getBindingContext(...)");
        StatementFilter statementFilter = basicCallResolutionContext.statementFilter;
        Intrinsics.checkNotNullExpressionValue(statementFilter, "statementFilter");
        DeclarationDescriptor ownerDescriptor = basicCallResolutionContext.scope.getOwnerDescriptor();
        ValueArgument valueArgumentMakeExternalValueArgument3 = CallMaker.makeExternalValueArgument(ktExpression);
        Intrinsics.checkNotNullExpressionValue(valueArgumentMakeExternalValueArgument3, "makeExternalValueArgument(...)");
        DataFlowInfo empty2 = DataFlowInfo.INSTANCE.getEMPTY();
        LanguageVersionSettings languageVersionSettings = kotlinResolutionCallbacksImpl.languageVersionSettings;
        DataFlowValueFactory dataFlowValueFactory = kotlinResolutionCallbacksImpl.dataFlowValueFactory;
        Call call = basicCallResolutionContext.call;
        Intrinsics.checkNotNullExpressionValue(call, "call");
        return NewCallArgumentsKt.createSimplePSICallArgument(bindingContext, statementFilter, ownerDescriptor, valueArgumentMakeExternalValueArgument3, empty2, kotlinTypeInfo, languageVersionSettings, dataFlowValueFactory, call);
    }

    private final KtExpression getLastDeparentesizedExpression(PSIKotlinCallArgument psiCallArgument) {
        List<KtExpression> statements;
        KtExpression ktExpressionLastBlockStatementOrThis = null;
        if (psiCallArgument instanceof LambdaKotlinCallArgumentImpl) {
            KtBlockExpression bodyExpression = ((LambdaKotlinCallArgumentImpl) psiCallArgument).getKtLambdaExpression().getBodyExpression();
            if (bodyExpression != null && (statements = bodyExpression.getStatements()) != null) {
                ktExpressionLastBlockStatementOrThis = (KtExpression) CollectionsKt.lastOrNull((List) statements);
            }
        } else {
            Intrinsics.checkNotNull(psiCallArgument, "null cannot be cast to non-null type org.jetbrains.kotlin.resolve.calls.tower.FunctionExpressionImpl");
            KtExpression bodyExpression2 = ((FunctionExpressionImpl) psiCallArgument).getKtFunction().getBodyExpression();
            if (bodyExpression2 != null) {
                ktExpressionLastBlockStatementOrThis = KtPsiUtilKt.lastBlockStatementOrThis(bodyExpression2);
            }
        }
        return KtPsiUtil.deparenthesize(ktExpressionLastBlockStatementOrThis);
    }

    @Override // org.jetbrains.kotlin.resolve.calls.components.KotlinResolutionCallbacks
    public void bindStubResolvedCallForCandidate(ResolvedCallAtom candidate) {
        Intrinsics.checkNotNullParameter(candidate, "candidate");
        this.kotlinToResolvedCallTransformer.createStubResolvedCallAndWriteItToTrace(candidate, this.trace, CollectionsKt.emptyList(), null);
    }

    @Override // org.jetbrains.kotlin.resolve.calls.components.KotlinResolutionCallbacks
    public boolean isCompileTimeConstant(ResolvedCallAtom resolvedAtom, UnwrappedType expectedType) {
        KotlinType returnType;
        Intrinsics.checkNotNullParameter(resolvedAtom, "resolvedAtom");
        Intrinsics.checkNotNullParameter(expectedType, "expectedType");
        CallableDescriptor callableDescriptor = resolvedAtom.get_candidateDescriptor();
        if (!KotlinBuiltIns.isUnderKotlinPackage(callableDescriptor) || (returnType = callableDescriptor.getReturnType()) == null || !KotlinBuiltIns.isPrimitiveTypeOrNullablePrimitiveType(returnType)) {
            return false;
        }
        UnwrappedType unwrappedType = expectedType;
        if (!KotlinBuiltIns.isPrimitiveTypeOrNullablePrimitiveType(unwrappedType)) {
            return false;
        }
        KtElement callElement = PSIKotlinCallsKt.getPsiKotlinCall(resolvedAtom.getAtom()).getPsiCall().getCallElement();
        KtExpression ktExpression = callElement instanceof KtExpression ? (KtExpression) callElement : null;
        if (ktExpression == null) {
            return false;
        }
        KtExpression ktExpressionFindCommonParent = findCommonParent(ktExpression, PSIKotlinCallsKt.getPsiKotlinCall(resolvedAtom.getAtom()).getExplicitReceiver());
        TemporaryBindingTrace temporaryBindingTraceCreate = TemporaryBindingTrace.create(this.trace, "Trace to check if some expression is constant, we have to avoid writing probably wrong COMPILE_TIME_VALUE slice");
        Intrinsics.checkNotNullExpressionValue(temporaryBindingTraceCreate, "create(...)");
        return this.constantExpressionEvaluator.evaluateExpression(ktExpressionFindCommonParent, temporaryBindingTraceCreate, unwrappedType) != null;
    }

    private final KtExpression findCommonParent(KtExpression callElement, ReceiverKotlinCallArgument receiver) {
        if (receiver == null) {
            return callElement;
        }
        PsiElement psiElementFindCommonParent = PsiTreeUtil.findCommonParent(callElement, NewCallArgumentsKt.getPsiExpression(receiver));
        KtExpression ktExpression = psiElementFindCommonParent instanceof KtExpression ? (KtExpression) psiElementFindCommonParent : null;
        return ktExpression == null ? callElement : ktExpression;
    }

    @Override // org.jetbrains.kotlin.resolve.calls.components.KotlinResolutionCallbacks
    public UnwrappedType getExpectedTypeFromAsExpressionAndRecordItInTrace(ResolvedCallAtom resolvedAtom) {
        Call psiCall;
        KtExpression calleeExpression;
        KtBinaryExpressionWithTypeRHS binaryWithTypeParent;
        KotlinType kotlinTypeMakeNullable;
        Intrinsics.checkNotNullParameter(resolvedAtom, "resolvedAtom");
        CallableDescriptor callableDescriptor = resolvedAtom.get_candidateDescriptor();
        FunctionDescriptor functionDescriptor = callableDescriptor instanceof FunctionDescriptor ? (FunctionDescriptor) callableDescriptor : null;
        if (functionDescriptor == null) {
            return null;
        }
        KotlinCall atom = resolvedAtom.getAtom();
        PSIKotlinCall pSIKotlinCall = atom instanceof PSIKotlinCall ? (PSIKotlinCall) atom : null;
        if (pSIKotlinCall == null || (psiCall = pSIKotlinCall.getPsiCall()) == null || psiCall.getTypeArgumentList() != null || !KtDescriptorUtilKt.isFunctionForExpectTypeFromCastFeature(functionDescriptor) || (calleeExpression = psiCall.getCalleeExpression()) == null || (binaryWithTypeParent = KtPsiUtilKt.getBinaryWithTypeParent(calleeExpression)) == null) {
            return null;
        }
        IElementType referencedNameElementType = binaryWithTypeParent.getOperationReference().getReferencedNameElementType();
        if (!Intrinsics.areEqual(referencedNameElementType, KtTokens.AS_KEYWORD) && !Intrinsics.areEqual(referencedNameElementType, KtTokens.AS_SAFE)) {
            referencedNameElementType = null;
        }
        if (referencedNameElementType == null) {
            return null;
        }
        BindingTrace bindingTrace = this.trace;
        WritableSlice<KtTypeReference, KotlinType> writableSlice = BindingContext.TYPE;
        KtTypeReference right = binaryWithTypeParent.getRight();
        if (right == null || (kotlinTypeMakeNullable = (KotlinType) bindingTrace.get(writableSlice, right)) == null) {
            return null;
        }
        if (Intrinsics.areEqual(referencedNameElementType, KtTokens.AS_SAFE)) {
            kotlinTypeMakeNullable = TypeUtilsKt.makeNullable(kotlinTypeMakeNullable);
        }
        UnwrappedType unwrappedTypeUnwrap = kotlinTypeMakeNullable.unwrap();
        this.trace.record(BindingContext.CAST_TYPE_USED_AS_EXPECTED_TYPE, binaryWithTypeParent);
        return unwrappedTypeUnwrap;
    }

    @Override // org.jetbrains.kotlin.resolve.calls.components.KotlinResolutionCallbacks
    public void disableContractsIfNecessary(ResolvedCallAtom resolvedAtom) {
        Intrinsics.checkNotNullParameter(resolvedAtom, "resolvedAtom");
        KotlinCall atom = resolvedAtom.getAtom();
        PSIKotlinCall pSIKotlinCall = atom instanceof PSIKotlinCall ? (PSIKotlinCall) atom : null;
        if (pSIKotlinCall == null) {
            return;
        }
        Call psiCall = pSIKotlinCall.getPsiCall();
        CallableDescriptor callableDescriptor = resolvedAtom.get_candidateDescriptor();
        LexicalScope scope = this.topLevelCallContext.scope;
        Intrinsics.checkNotNullExpressionValue(scope, "scope");
        RecursiveContractHelperKt.disableContractsInsideContractsBlock(psiCall, callableDescriptor, scope, this.trace);
    }

    @Override // org.jetbrains.kotlin.resolve.calls.components.KotlinResolutionCallbacks
    public IntegerValueTypeConstant convertSignedConstantToUnsigned(KotlinCallArgument argument) {
        Intrinsics.checkNotNullParameter(argument, "argument");
        KtExpression psiExpression = NewCallArgumentsKt.getPsiExpression(argument);
        if (psiExpression == null) {
            return null;
        }
        return convertSignedConstantToUnsigned(psiExpression);
    }

    @Override // org.jetbrains.kotlin.resolve.calls.components.KotlinResolutionCallbacks
    public void recordInlinabilityOfLambda(Set<? extends Map.Entry<? extends SimpleResolutionCandidate, ResolvedLambdaAtom>> atom) {
        KtLambdaExpression lambdaExpression;
        KtFunctionLiteral functionLiteral;
        ValueParameterDescriptor valueParameterDescriptor;
        Intrinsics.checkNotNullParameter(atom, "atom");
        Set<? extends Map.Entry<? extends SimpleResolutionCandidate, ResolvedLambdaAtom>> set = atom;
        ValueArgument valueArgument = NewCallArgumentsKt.getPsiCallArgument(((ResolvedLambdaAtom) ((Map.Entry) CollectionsKt.first(set)).getValue()).getAtom()).getValueArgument();
        KtLambdaArgument ktLambdaArgument = valueArgument instanceof KtLambdaArgument ? (KtLambdaArgument) valueArgument : null;
        if (ktLambdaArgument == null || (lambdaExpression = ktLambdaArgument.getLambdaExpression()) == null || (functionLiteral = lambdaExpression.getFunctionLiteral()) == null) {
            return;
        }
        boolean z = true;
        if (!(set instanceof Collection) || !set.isEmpty()) {
            Iterator<T> it = set.iterator();
            while (it.hasNext()) {
                Map.Entry entry = (Map.Entry) it.next();
                SimpleResolutionCandidate simpleResolutionCandidate = (SimpleResolutionCandidate) entry.getKey();
                ResolvedLambdaAtom resolvedLambdaAtom = (ResolvedLambdaAtom) entry.getValue();
                if (!InlineUtil.isInline(simpleResolutionCandidate.getResolvedCall().get_candidateDescriptor()) || (valueParameterDescriptor = simpleResolutionCandidate.getResolvedCall().getArgumentToCandidateParameter().get(resolvedLambdaAtom.getAtom())) == null) {
                    return;
                }
                if (!InlineUtil.isInlineParameter(valueParameterDescriptor)) {
                    z = false;
                    break;
                }
            }
        }
        Boolean boolValueOf = Boolean.valueOf(z);
        this.trace.record(BindingContext.NEW_INFERENCE_IS_LAMBDA_FOR_OVERLOAD_RESOLUTION_INLINE, functionLiteral, boolValueOf.booleanValue() ? boolValueOf : null);
    }

    @Override // org.jetbrains.kotlin.resolve.calls.components.KotlinResolutionCallbacks
    public LHSResult getLhsResult(KotlinCall call) {
        Intrinsics.checkNotNullParameter(call, "call");
        KtCallableReferenceExpression ktCallableReferenceExpressionExtractCallableReferenceExpression = CallUtilKt.extractCallableReferenceExpression(call);
        if (ktCallableReferenceExpressionExtractCallableReferenceExpression == null) {
            throw new IllegalStateException("Not a callable reference");
        }
        return this.psiCallResolver.getLhsResult(this.topLevelCallContext, ktCallableReferenceExpressionExtractCallableReferenceExpression).component2();
    }

    private final IntegerValueTypeConstant convertSignedConstantToUnsigned(KtExpression expression) {
        CompileTimeConstant<?> compileTimeConstant = (CompileTimeConstant) this.trace.get(BindingContext.COMPILE_TIME_VALUE, expression);
        if ((compileTimeConstant instanceof IntegerValueTypeConstant) && constantCanBeConvertedToUnsigned(compileTimeConstant)) {
            return IntegerValueTypeConstant.INSTANCE.convertToUnsignedConstant((IntegerValueTypeConstant) compileTimeConstant, this.moduleDescriptor);
        }
        return null;
    }

    private final boolean constantCanBeConvertedToUnsigned(CompileTimeConstant<?> constant) {
        return !constant.isError() && constant.getParameters().isPure();
    }
}
