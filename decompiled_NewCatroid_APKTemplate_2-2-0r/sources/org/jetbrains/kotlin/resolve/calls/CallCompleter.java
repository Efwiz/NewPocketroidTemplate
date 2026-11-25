package org.jetbrains.kotlin.resolve.calls;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.builtins.FunctionTypesKt;
import org.jetbrains.kotlin.config.LanguageFeature;
import org.jetbrains.kotlin.config.LanguageVersionSettings;
import org.jetbrains.kotlin.contracts.EffectSystem;
import org.jetbrains.kotlin.descriptors.CallableDescriptor;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.descriptors.ModuleDescriptor;
import org.jetbrains.kotlin.descriptors.ValueParameterDescriptor;
import org.jetbrains.kotlin.diagnostics.Errors;
import org.jetbrains.kotlin.psi.Call;
import org.jetbrains.kotlin.psi.KtAnnotatedExpression;
import org.jetbrains.kotlin.psi.KtBlockExpression;
import org.jetbrains.kotlin.psi.KtCallableReferenceExpression;
import org.jetbrains.kotlin.psi.KtElement;
import org.jetbrains.kotlin.psi.KtExpression;
import org.jetbrains.kotlin.psi.KtFunction;
import org.jetbrains.kotlin.psi.KtLabeledExpression;
import org.jetbrains.kotlin.psi.KtParenthesizedExpression;
import org.jetbrains.kotlin.psi.KtPsiUtil;
import org.jetbrains.kotlin.psi.KtQualifiedExpression;
import org.jetbrains.kotlin.psi.KtSafeQualifiedExpression;
import org.jetbrains.kotlin.psi.ValueArgument;
import org.jetbrains.kotlin.resolve.BindingContext;
import org.jetbrains.kotlin.resolve.BindingContextUtils;
import org.jetbrains.kotlin.resolve.BindingTrace;
import org.jetbrains.kotlin.resolve.DescriptorUtils;
import org.jetbrains.kotlin.resolve.ImplicitIntegerCoercion;
import org.jetbrains.kotlin.resolve.MissingSupertypesResolver;
import org.jetbrains.kotlin.resolve.RecursiveContractHelperKt;
import org.jetbrains.kotlin.resolve.StatementFilter;
import org.jetbrains.kotlin.resolve.StatementFilterKt;
import org.jetbrains.kotlin.resolve.TemporaryBindingTrace;
import org.jetbrains.kotlin.resolve.calls.checkers.CallChecker;
import org.jetbrains.kotlin.resolve.calls.checkers.CallCheckerContext;
import org.jetbrains.kotlin.resolve.calls.context.BasicCallResolutionContext;
import org.jetbrains.kotlin.resolve.calls.context.CallCandidateResolutionContext;
import org.jetbrains.kotlin.resolve.calls.context.CallPosition;
import org.jetbrains.kotlin.resolve.calls.context.CheckArgumentTypesMode;
import org.jetbrains.kotlin.resolve.calls.context.ResolutionResultsCache;
import org.jetbrains.kotlin.resolve.calls.inference.CallHandle;
import org.jetbrains.kotlin.resolve.calls.inference.ConstraintSystem;
import org.jetbrains.kotlin.resolve.calls.inference.ConstraintSystemCompleter;
import org.jetbrains.kotlin.resolve.calls.inference.ConstraintSystemUtilsKt;
import org.jetbrains.kotlin.resolve.calls.inference.InferenceErrorData;
import org.jetbrains.kotlin.resolve.calls.inference.TypeVariableKt;
import org.jetbrains.kotlin.resolve.calls.inference.constraintPosition.ConstraintPositionKind;
import org.jetbrains.kotlin.resolve.calls.model.ArgumentMapping;
import org.jetbrains.kotlin.resolve.calls.model.ArgumentMatch;
import org.jetbrains.kotlin.resolve.calls.model.ArgumentUnmapped;
import org.jetbrains.kotlin.resolve.calls.model.KotlinCallComponents;
import org.jetbrains.kotlin.resolve.calls.model.MutableDataFlowInfoForArguments;
import org.jetbrains.kotlin.resolve.calls.model.MutableResolvedCall;
import org.jetbrains.kotlin.resolve.calls.model.VariableAsFunctionResolvedCall;
import org.jetbrains.kotlin.resolve.calls.results.OverloadResolutionResultsImpl;
import org.jetbrains.kotlin.resolve.calls.results.ResolutionStatus;
import org.jetbrains.kotlin.resolve.calls.smartcasts.DataFlowInfo;
import org.jetbrains.kotlin.resolve.calls.smartcasts.DataFlowValueFactory;
import org.jetbrains.kotlin.resolve.calls.tasks.TracingStrategy;
import org.jetbrains.kotlin.resolve.calls.util.CallResolverUtilKt;
import org.jetbrains.kotlin.resolve.calls.util.CallUtilKt;
import org.jetbrains.kotlin.resolve.calls.util.ResolveArgumentsMode;
import org.jetbrains.kotlin.resolve.calls.util.ResolvedCallUtilKt;
import org.jetbrains.kotlin.resolve.checkers.MissingDependencySupertypeChecker;
import org.jetbrains.kotlin.resolve.constants.CompileTimeConstant;
import org.jetbrains.kotlin.resolve.constants.IntegerValueTypeConstant;
import org.jetbrains.kotlin.resolve.constants.IntegerValueTypeConstructor;
import org.jetbrains.kotlin.resolve.deprecation.DeprecationResolver;
import org.jetbrains.kotlin.resolve.scopes.LexicalScope;
import org.jetbrains.kotlin.resolve.scopes.receivers.Receiver;
import org.jetbrains.kotlin.resolve.scopes.receivers.ReceiverValue;
import org.jetbrains.kotlin.types.KotlinType;
import org.jetbrains.kotlin.types.KotlinTypeFactory;
import org.jetbrains.kotlin.types.SimpleType;
import org.jetbrains.kotlin.types.TypeAttributes;
import org.jetbrains.kotlin.types.TypeProjection;
import org.jetbrains.kotlin.types.TypeSubstitutor;
import org.jetbrains.kotlin.types.TypeUtils;
import org.jetbrains.kotlin.types.Variance;
import org.jetbrains.kotlin.types.error.ErrorScopeKind;
import org.jetbrains.kotlin.types.error.ErrorUtils;
import org.jetbrains.kotlin.types.expressions.DataFlowAnalyzer;

/* JADX INFO: compiled from: CallCompleter.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000´\u0001\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u001c\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0006\u0018\u00002\u00020\u0001B[\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\f\u0010\b\u001a\b\u0012\u0004\u0012\u00020\n0\t\u0012\u0006\u0010\u000b\u001a\u00020\f\u0012\u0006\u0010\r\u001a\u00020\u000e\u0012\u0006\u0010\u000f\u001a\u00020\u0010\u0012\u0006\u0010\u0011\u001a\u00020\u0012\u0012\u0006\u0010\u0013\u001a\u00020\u0014\u0012\u0006\u0010\u0015\u001a\u00020\u0016¢\u0006\u0002\u0010\u0017J\u0018\u0010\u0018\u001a\u00020\u00192\u0006\u0010\u001a\u001a\u00020\u001b2\u0006\u0010\u0013\u001a\u00020\u0014H\u0002J(\u0010\u001c\u001a\u00020\u0019\"\b\b\u0000\u0010\u001d*\u00020\u001e2\u0006\u0010\u001a\u001a\u00020\u001b2\f\u0010\u001f\u001a\b\u0012\u0004\u0012\u0002H\u001d0 H\u0002J(\u0010!\u001a\u00020\u0019\"\b\b\u0000\u0010\u001d*\u00020\u001e2\u0006\u0010\u001a\u001a\u00020\u001b2\f\u0010\u001f\u001a\b\u0012\u0004\u0012\u0002H\u001d0 H\u0002J4\u0010\"\u001a\b\u0012\u0004\u0012\u0002H\u001d0 \"\b\b\u0000\u0010\u001d*\u00020\u001e2\u0006\u0010\u001a\u001a\u00020\u001b2\f\u0010\u001f\u001a\b\u0012\u0004\u0012\u0002H\u001d0 2\u0006\u0010#\u001a\u00020$J\u001e\u0010%\u001a\b\u0012\u0002\b\u0003\u0018\u00010 2\u0006\u0010&\u001a\u00020'2\u0006\u0010\u001a\u001a\u00020\u001bH\u0002J\"\u0010(\u001a\u00020\u00192\u0006\u0010)\u001a\u00020*2\b\u0010+\u001a\u0004\u0018\u00010,2\u0006\u0010\u001a\u001a\u00020\u001bH\u0002J@\u0010-\u001a\u00020\u0019\"\b\b\u0000\u0010\u001d*\u00020\u001e2\u000e\u0010.\u001a\n\u0012\u0004\u0012\u0002H\u001d\u0018\u00010/2\f\u0010\u001f\u001a\b\u0012\u0004\u0012\u0002H\u001d0 2\u0006\u0010\u001a\u001a\u00020\u001b2\u0006\u0010#\u001a\u00020$H\u0002J\u0016\u00100\u001a\u0004\u0018\u0001012\n\u00102\u001a\u0006\u0012\u0002\b\u000303H\u0002J\u0018\u00104\u001a\u0002052\u0006\u0010&\u001a\u00020'2\u0006\u00106\u001a\u000207H\u0002J6\u00108\u001a\u0004\u0018\u0001092\b\u0010:\u001a\u0004\u0018\u0001092\b\u0010;\u001a\u0004\u0018\u0001092\u0006\u0010<\u001a\u00020'2\u0006\u0010=\u001a\u00020>2\u0006\u00106\u001a\u000207H\u0002J,\u0010?\u001a\u00020\u0019\"\b\b\u0000\u0010\u001d*\u00020\u001e*\b\u0012\u0004\u0012\u0002H\u001d0/2\u0006\u0010@\u001a\u0002092\u0006\u00106\u001a\u000207H\u0002J,\u0010A\u001a\u00020\u0019\"\b\b\u0000\u0010\u001d*\u00020\u001e*\b\u0012\u0004\u0012\u0002H\u001d0/2\u0006\u0010\u001a\u001a\u00020\u001b2\u0006\u0010#\u001a\u00020$H\u0002J\u0018\u0010B\u001a\u00020\u0019*\u0006\u0012\u0002\b\u00030/2\u0006\u0010C\u001a\u000207H\u0002R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\b\u001a\b\u0012\u0004\u0012\u00020\n0\tX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0015\u001a\u00020\u0016X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0011\u001a\u00020\u0012X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\u000eX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u000f\u001a\u00020\u0010X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0013\u001a\u00020\u0014X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\fX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006D"}, d2 = {"Lorg/jetbrains/kotlin/resolve/calls/CallCompleter;", "", "argumentTypeResolver", "Lorg/jetbrains/kotlin/resolve/calls/ArgumentTypeResolver;", "candidateResolver", "Lorg/jetbrains/kotlin/resolve/calls/CandidateResolver;", "dataFlowAnalyzer", "Lorg/jetbrains/kotlin/types/expressions/DataFlowAnalyzer;", "callCheckers", "", "Lorg/jetbrains/kotlin/resolve/calls/checkers/CallChecker;", "moduleDescriptor", "Lorg/jetbrains/kotlin/descriptors/ModuleDescriptor;", "deprecationResolver", "Lorg/jetbrains/kotlin/resolve/deprecation/DeprecationResolver;", "effectSystem", "Lorg/jetbrains/kotlin/contracts/EffectSystem;", "dataFlowValueFactory", "Lorg/jetbrains/kotlin/resolve/calls/smartcasts/DataFlowValueFactory;", "missingSupertypesResolver", "Lorg/jetbrains/kotlin/resolve/MissingSupertypesResolver;", "callComponents", "Lorg/jetbrains/kotlin/resolve/calls/model/KotlinCallComponents;", "(Lorg/jetbrains/kotlin/resolve/calls/ArgumentTypeResolver;Lorg/jetbrains/kotlin/resolve/calls/CandidateResolver;Lorg/jetbrains/kotlin/types/expressions/DataFlowAnalyzer;Ljava/lang/Iterable;Lorg/jetbrains/kotlin/descriptors/ModuleDescriptor;Lorg/jetbrains/kotlin/resolve/deprecation/DeprecationResolver;Lorg/jetbrains/kotlin/contracts/EffectSystem;Lorg/jetbrains/kotlin/resolve/calls/smartcasts/DataFlowValueFactory;Lorg/jetbrains/kotlin/resolve/MissingSupertypesResolver;Lorg/jetbrains/kotlin/resolve/calls/model/KotlinCallComponents;)V", "checkMissingSupertypes", "", "context", "Lorg/jetbrains/kotlin/resolve/calls/context/BasicCallResolutionContext;", "completeAllCandidates", "D", "Lorg/jetbrains/kotlin/descriptors/CallableDescriptor;", "results", "Lorg/jetbrains/kotlin/resolve/calls/results/OverloadResolutionResultsImpl;", "completeArguments", "completeCall", "tracing", "Lorg/jetbrains/kotlin/resolve/calls/tasks/TracingStrategy;", "completeCallForArgument", "expression", "Lorg/jetbrains/kotlin/psi/KtExpression;", "completeOneArgument", "argument", "Lorg/jetbrains/kotlin/psi/ValueArgument;", "parameter", "Lorg/jetbrains/kotlin/descriptors/ValueParameterDescriptor;", "completeResolvedCallAndArguments", "resolvedCall", "Lorg/jetbrains/kotlin/resolve/calls/model/MutableResolvedCall;", "createTypeForConvertableConstant", "Lorg/jetbrains/kotlin/types/SimpleType;", "constant", "Lorg/jetbrains/kotlin/resolve/constants/CompileTimeConstant;", "hasNecessarySafeCall", "", "trace", "Lorg/jetbrains/kotlin/resolve/BindingTrace;", "updateRecordedTypeForArgument", "Lorg/jetbrains/kotlin/types/KotlinType;", "updatedType", "recordedType", "argumentExpression", "statementFilter", "Lorg/jetbrains/kotlin/resolve/StatementFilter;", "completeConstraintSystem", "expectedType", "updateResolutionStatusFromConstraintSystem", "updateResultDataFlowInfoUsingEffects", "bindingTrace", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class CallCompleter {
    private final ArgumentTypeResolver argumentTypeResolver;
    private final Iterable<CallChecker> callCheckers;
    private final KotlinCallComponents callComponents;
    private final CandidateResolver candidateResolver;
    private final DataFlowAnalyzer dataFlowAnalyzer;
    private final DataFlowValueFactory dataFlowValueFactory;
    private final DeprecationResolver deprecationResolver;
    private final EffectSystem effectSystem;
    private final MissingSupertypesResolver missingSupertypesResolver;
    private final ModuleDescriptor moduleDescriptor;

    /* JADX WARN: Multi-variable type inference failed */
    public CallCompleter(ArgumentTypeResolver argumentTypeResolver, CandidateResolver candidateResolver, DataFlowAnalyzer dataFlowAnalyzer, Iterable<? extends CallChecker> callCheckers, ModuleDescriptor moduleDescriptor, DeprecationResolver deprecationResolver, EffectSystem effectSystem, DataFlowValueFactory dataFlowValueFactory, MissingSupertypesResolver missingSupertypesResolver, KotlinCallComponents callComponents) {
        Intrinsics.checkNotNullParameter(argumentTypeResolver, "argumentTypeResolver");
        Intrinsics.checkNotNullParameter(candidateResolver, "candidateResolver");
        Intrinsics.checkNotNullParameter(dataFlowAnalyzer, "dataFlowAnalyzer");
        Intrinsics.checkNotNullParameter(callCheckers, "callCheckers");
        Intrinsics.checkNotNullParameter(moduleDescriptor, "moduleDescriptor");
        Intrinsics.checkNotNullParameter(deprecationResolver, "deprecationResolver");
        Intrinsics.checkNotNullParameter(effectSystem, "effectSystem");
        Intrinsics.checkNotNullParameter(dataFlowValueFactory, "dataFlowValueFactory");
        Intrinsics.checkNotNullParameter(missingSupertypesResolver, "missingSupertypesResolver");
        Intrinsics.checkNotNullParameter(callComponents, "callComponents");
        this.argumentTypeResolver = argumentTypeResolver;
        this.candidateResolver = candidateResolver;
        this.dataFlowAnalyzer = dataFlowAnalyzer;
        this.callCheckers = callCheckers;
        this.moduleDescriptor = moduleDescriptor;
        this.deprecationResolver = deprecationResolver;
        this.effectSystem = effectSystem;
        this.dataFlowValueFactory = dataFlowValueFactory;
        this.missingSupertypesResolver = missingSupertypesResolver;
        this.callComponents = callComponents;
    }

    /* JADX WARN: Code duplicated, block: B:21:0x0065  */
    public final <D extends CallableDescriptor> OverloadResolutionResultsImpl<D> completeCall(BasicCallResolutionContext context, OverloadResolutionResultsImpl<D> results, TracingStrategy tracing) {
        KtExpression calleeExpression;
        KtExpression callElement;
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(results, "results");
        Intrinsics.checkNotNullParameter(tracing, "tracing");
        MutableResolvedCall<D> mutableResolvedCallMo7436getResultingCall = results.isSingleResult() ? results.mo7436getResultingCall() : null;
        Call call = context.call;
        Intrinsics.checkNotNullExpressionValue(call, "call");
        if (!CallResolverUtilKt.isInvokeCallOnVariable(call)) {
            completeResolvedCallAndArguments(mutableResolvedCallMo7436getResultingCall, results, context, tracing);
            completeAllCandidates(context, results);
        }
        if (context.trace.wantsDiagnostics()) {
            if (mutableResolvedCallMo7436getResultingCall == null) {
                checkMissingSupertypes(context, this.missingSupertypesResolver);
            } else {
                boolean z = mutableResolvedCallMo7436getResultingCall instanceof VariableAsFunctionResolvedCall;
                if (z) {
                    calleeExpression = ((VariableAsFunctionResolvedCall) mutableResolvedCallMo7436getResultingCall).getVariableCall().getCall().getCalleeExpression();
                } else {
                    calleeExpression = mutableResolvedCallMo7436getResultingCall.getCall().getCalleeExpression();
                }
                if (calleeExpression != null) {
                    callElement = calleeExpression;
                    if (CallUtilKt.isFakeElement(callElement)) {
                        callElement = mutableResolvedCallMo7436getResultingCall.getCall().getCallElement();
                    }
                } else {
                    callElement = mutableResolvedCallMo7436getResultingCall.getCall().getCallElement();
                }
                Intrinsics.checkNotNull(callElement);
                CallCheckerContext callCheckerContext = new CallCheckerContext(context, this.deprecationResolver, this.moduleDescriptor, this.missingSupertypesResolver, this.callComponents, null, 32, null);
                for (CallChecker callChecker : this.callCheckers) {
                    KtElement ktElement = callElement;
                    callChecker.check(mutableResolvedCallMo7436getResultingCall, ktElement, callCheckerContext);
                    if (z) {
                        callChecker.check(((VariableAsFunctionResolvedCall) mutableResolvedCallMo7436getResultingCall).getVariableCall(), ktElement, callCheckerContext);
                    }
                }
            }
        }
        if (!results.isSingleResult() || !results.mo7436getResultingCall().getStatus().isSuccess()) {
            return results;
        }
        OverloadResolutionResultsImpl<D> overloadResolutionResultsImplChangeStatusToSuccess = results.changeStatusToSuccess();
        Intrinsics.checkNotNullExpressionValue(overloadResolutionResultsImplChangeStatusToSuccess, "changeStatusToSuccess(...)");
        return overloadResolutionResultsImplChangeStatusToSuccess;
    }

    private final void checkMissingSupertypes(BasicCallResolutionContext context, MissingSupertypesResolver missingSupertypesResolver) {
        Call call = context.call;
        Intrinsics.checkNotNullExpressionValue(call, "call");
        Receiver explicitReceiver = call.getExplicitReceiver();
        ReceiverValue receiverValue = explicitReceiver instanceof ReceiverValue ? (ReceiverValue) explicitReceiver : null;
        if (receiverValue == null) {
            return;
        }
        MissingDependencySupertypeChecker missingDependencySupertypeChecker = MissingDependencySupertypeChecker.INSTANCE;
        KotlinType type = receiverValue.getType();
        Intrinsics.checkNotNullExpressionValue(type, "getType(...)");
        KtElement callElement = call.getCallElement();
        Intrinsics.checkNotNullExpressionValue(callElement, "getCallElement(...)");
        BindingTrace trace = context.trace;
        Intrinsics.checkNotNullExpressionValue(trace, "trace");
        missingDependencySupertypeChecker.checkSupertypes(type, callElement, trace, missingSupertypesResolver);
    }

    /* JADX WARN: Multi-variable type inference failed */
    private final <D extends CallableDescriptor> void completeAllCandidates(BasicCallResolutionContext context, OverloadResolutionResultsImpl<D> results) {
        Collection<MutableResolvedCall<D>> resultingCalls;
        if (context.collectAllCandidates) {
            resultingCalls = results.getAllCandidates();
            Intrinsics.checkNotNull(resultingCalls);
            Intrinsics.checkNotNull(resultingCalls);
        } else {
            resultingCalls = results.getResultingCalls();
            Intrinsics.checkNotNull(resultingCalls);
        }
        TemporaryBindingTrace temporaryBindingTraceCreate = TemporaryBindingTrace.create(context.trace, "Trace to complete a candidate that is not a resulting call");
        Intrinsics.checkNotNullExpressionValue(temporaryBindingTraceCreate, "create(...)");
        ArrayList<MutableResolvedCall<D>> arrayList = new ArrayList();
        for (Object obj : resultingCalls) {
            if (!((MutableResolvedCall) obj).isCompleted()) {
                arrayList.add(obj);
            }
        }
        for (MutableResolvedCall<D> mutableResolvedCall : arrayList) {
            Context contextReplaceBindingTrace = context.replaceBindingTrace(temporaryBindingTraceCreate);
            Intrinsics.checkNotNullExpressionValue(contextReplaceBindingTrace, "replaceBindingTrace(...)");
            TracingStrategy EMPTY = TracingStrategy.EMPTY;
            Intrinsics.checkNotNullExpressionValue(EMPTY, "EMPTY");
            completeResolvedCallAndArguments(mutableResolvedCall, results, (BasicCallResolutionContext) contextReplaceBindingTrace, EMPTY);
        }
    }

    private final <D extends CallableDescriptor> void completeResolvedCallAndArguments(MutableResolvedCall<D> resolvedCall, OverloadResolutionResultsImpl<D> results, BasicCallResolutionContext context, TracingStrategy tracing) {
        Call call = context.call;
        Intrinsics.checkNotNullExpressionValue(call, "call");
        D resultingDescriptor = resolvedCall != null ? resolvedCall.getResultingDescriptor() : null;
        LexicalScope scope = context.scope;
        Intrinsics.checkNotNullExpressionValue(scope, "scope");
        BindingTrace trace = context.trace;
        Intrinsics.checkNotNullExpressionValue(trace, "trace");
        RecursiveContractHelperKt.disableContractsInsideContractsBlock(call, resultingDescriptor, scope, trace);
        if (resolvedCall == null || resolvedCall.isCompleted() || resolvedCall.getConstraintSystem() == null) {
            completeArguments(context, results);
            if (resolvedCall != null) {
                BindingTrace trace2 = context.trace;
                Intrinsics.checkNotNullExpressionValue(trace2, "trace");
                updateResultDataFlowInfoUsingEffects(resolvedCall, trace2);
            }
            if (resolvedCall != null) {
                resolvedCall.markCallAsCompleted();
                return;
            }
            return;
        }
        KotlinType expectedType = context.expectedType;
        Intrinsics.checkNotNullExpressionValue(expectedType, "expectedType");
        BindingTrace trace3 = context.trace;
        Intrinsics.checkNotNullExpressionValue(trace3, "trace");
        completeConstraintSystem(resolvedCall, expectedType, trace3);
        completeArguments(context, results);
        updateResolutionStatusFromConstraintSystem(resolvedCall, context, tracing);
        BindingTrace trace4 = context.trace;
        Intrinsics.checkNotNullExpressionValue(trace4, "trace");
        updateResultDataFlowInfoUsingEffects(resolvedCall, trace4);
        resolvedCall.markCallAsCompleted();
    }

    private final <D extends CallableDescriptor> void completeConstraintSystem(final MutableResolvedCall<D> mutableResolvedCall, final KotlinType kotlinType, BindingTrace bindingTrace) {
        final SimpleType returnTypeFromFunctionType;
        TypeSubstitutor resultingSubstitutor;
        final KotlinType returnType = mutableResolvedCall.get$suspendPropertyDescriptor().getReturnType();
        Call call = mutableResolvedCall.getCall();
        Intrinsics.checkNotNullExpressionValue(call, "getCall(...)");
        if (!CallUtilKt.isCallableReference(call)) {
            returnTypeFromFunctionType = kotlinType;
        } else if (!TypeUtils.noExpectedType(kotlinType) && FunctionTypesKt.isFunctionOrSuspendFunctionType(kotlinType)) {
            returnTypeFromFunctionType = FunctionTypesKt.getReturnTypeFromFunctionType(kotlinType);
        } else {
            SimpleType NO_EXPECTED_TYPE = TypeUtils.NO_EXPECTED_TYPE;
            Intrinsics.checkNotNullExpressionValue(NO_EXPECTED_TYPE, "NO_EXPECTED_TYPE");
            returnTypeFromFunctionType = NO_EXPECTED_TYPE;
        }
        if (returnType != null && !TypeUtils.noExpectedType(returnTypeFromFunctionType)) {
            completeConstraintSystem$updateSystemIfNeeded(mutableResolvedCall, new Function1<ConstraintSystem.Builder, ConstraintSystem>() { // from class: org.jetbrains.kotlin.resolve.calls.CallCompleter.completeConstraintSystem.1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(1);
                }

                @Override // kotlin.jvm.functions.Function1
                public final ConstraintSystem invoke(ConstraintSystem.Builder builder) {
                    Intrinsics.checkNotNullParameter(builder, "builder");
                    KotlinType kotlinTypeCompleteConstraintSystem$typeInSystem = CallCompleter.completeConstraintSystem$typeInSystem(builder, mutableResolvedCall, returnType);
                    if (kotlinTypeCompleteConstraintSystem$typeInSystem == null) {
                        return null;
                    }
                    builder.addSubtypeConstraint(kotlinTypeCompleteConstraintSystem$typeInSystem, returnTypeFromFunctionType, ConstraintPositionKind.EXPECTED_TYPE_POSITION.position());
                    return builder.build();
                }
            });
        }
        final ConstraintSystemCompleter constraintSystemCompleter = (ConstraintSystemCompleter) bindingTrace.get(BindingContext.CONSTRAINT_SYSTEM_COMPLETER, mutableResolvedCall.getCall().getCalleeExpression());
        if (constraintSystemCompleter != null) {
            completeConstraintSystem$updateSystemIfNeeded(mutableResolvedCall, new Function1<ConstraintSystem.Builder, ConstraintSystem>() { // from class: org.jetbrains.kotlin.resolve.calls.CallCompleter.completeConstraintSystem.2
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(1);
                }

                @Override // kotlin.jvm.functions.Function1
                public final ConstraintSystem invoke(ConstraintSystem.Builder builder) {
                    Intrinsics.checkNotNullParameter(builder, "builder");
                    constraintSystemCompleter.completeConstraintSystem(builder, mutableResolvedCall);
                    ConstraintSystem constraintSystemBuild = builder.build();
                    if (ConstraintSystemUtilsKt.filterConstraintsOut(constraintSystemBuild, ConstraintPositionKind.TYPE_BOUND_POSITION).getStatus().hasOnlyErrorsDerivedFrom(ConstraintPositionKind.FROM_COMPLETER)) {
                        return null;
                    }
                    return constraintSystemBuild;
                }
            });
        }
        if (returnType != null && returnTypeFromFunctionType == TypeUtils.UNIT_EXPECTED_TYPE) {
            completeConstraintSystem$updateSystemIfNeeded(mutableResolvedCall, new Function1<ConstraintSystem.Builder, ConstraintSystem>() { // from class: org.jetbrains.kotlin.resolve.calls.CallCompleter.completeConstraintSystem.3
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(1);
                }

                @Override // kotlin.jvm.functions.Function1
                public final ConstraintSystem invoke(ConstraintSystem.Builder builder) {
                    Intrinsics.checkNotNullParameter(builder, "builder");
                    KotlinType kotlinTypeCompleteConstraintSystem$typeInSystem = CallCompleter.completeConstraintSystem$typeInSystem(builder, mutableResolvedCall, returnType);
                    if (kotlinTypeCompleteConstraintSystem$typeInSystem == null) {
                        return null;
                    }
                    builder.addSubtypeConstraint(kotlinTypeCompleteConstraintSystem$typeInSystem, this.moduleDescriptor.getBuiltIns().getUnitType(), ConstraintPositionKind.EXPECTED_TYPE_POSITION.position());
                    ConstraintSystem constraintSystemBuild = builder.build();
                    if (constraintSystemBuild.getStatus().isSuccessful()) {
                        return constraintSystemBuild;
                    }
                    return null;
                }
            });
        }
        Call call2 = mutableResolvedCall.getCall();
        Intrinsics.checkNotNullExpressionValue(call2, "getCall(...)");
        if (CallUtilKt.isCallableReference(call2) && !TypeUtils.noExpectedType(kotlinType) && FunctionTypesKt.isFunctionOrSuspendFunctionType(kotlinType)) {
            completeConstraintSystem$updateSystemIfNeeded(mutableResolvedCall, new Function1<ConstraintSystem.Builder, ConstraintSystem>() { // from class: org.jetbrains.kotlin.resolve.calls.CallCompleter.completeConstraintSystem.4
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(1);
                }

                @Override // kotlin.jvm.functions.Function1
                public final ConstraintSystem invoke(ConstraintSystem.Builder builder) {
                    Intrinsics.checkNotNullParameter(builder, "builder");
                    List<ValueParameterDescriptor> valueParameters = mutableResolvedCall.get$suspendPropertyDescriptor().getValueParameters();
                    Intrinsics.checkNotNullExpressionValue(valueParameters, "getValueParameters(...)");
                    List<Pair> listZip = CollectionsKt.zip(valueParameters, FunctionTypesKt.getValueParameterTypesFromFunctionType(kotlinType));
                    MutableResolvedCall<D> mutableResolvedCall2 = mutableResolvedCall;
                    for (Pair pair : listZip) {
                        ValueParameterDescriptor valueParameterDescriptor = (ValueParameterDescriptor) pair.component1();
                        builder.addSubtypeConstraint(CallCompleter.completeConstraintSystem$typeInSystem(builder, mutableResolvedCall2, valueParameterDescriptor.getType()), ((TypeProjection) pair.component2()).getNullableAnyType(), ConstraintPositionKind.VALUE_PARAMETER_POSITION.position(valueParameterDescriptor.getIndex()));
                    }
                    return builder.build();
                }
            });
        }
        ConstraintSystem constraintSystem = mutableResolvedCall.getConstraintSystem();
        Intrinsics.checkNotNull(constraintSystem);
        ConstraintSystem.Builder builder$default = ConstraintSystem.toBuilder$default(constraintSystem, null, 1, null);
        builder$default.fixVariables();
        ConstraintSystem constraintSystemBuild = builder$default.build();
        mutableResolvedCall.setConstraintSystem(constraintSystemBuild);
        if (this.effectSystem.getLanguageVersionSettings().supportsFeature(LanguageFeature.NewInference)) {
            resultingSubstitutor = constraintSystemBuild.getResultingSubstitutor().replaceWithContravariantApproximatingSubstitution();
        } else {
            resultingSubstitutor = constraintSystemBuild.getResultingSubstitutor();
        }
        Intrinsics.checkNotNull(resultingSubstitutor);
        mutableResolvedCall.setSubstitutor(resultingSubstitutor);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final <D extends CallableDescriptor> KotlinType completeConstraintSystem$typeInSystem(ConstraintSystem.Builder builder, MutableResolvedCall<D> mutableResolvedCall, KotlinType kotlinType) {
        if (kotlinType == null) {
            return null;
        }
        Map<CallHandle, TypeSubstitutor> typeVariableSubstitutors = builder.getTypeVariableSubstitutors();
        Call call = mutableResolvedCall.getCall();
        Intrinsics.checkNotNullExpressionValue(call, "getCall(...)");
        TypeSubstitutor typeSubstitutor = typeVariableSubstitutors.get(TypeVariableKt.toHandle(call));
        if (typeSubstitutor == null) {
            throw new IllegalStateException(("No substitutor for call: " + mutableResolvedCall.getCall()).toString());
        }
        return typeSubstitutor.substitute(kotlinType, Variance.INVARIANT);
    }

    private static final <D extends CallableDescriptor> void completeConstraintSystem$updateSystemIfNeeded(MutableResolvedCall<D> mutableResolvedCall, Function1<? super ConstraintSystem.Builder, ? extends ConstraintSystem> function1) {
        ConstraintSystem constraintSystem = mutableResolvedCall.getConstraintSystem();
        Intrinsics.checkNotNull(constraintSystem);
        ConstraintSystem constraintSystemInvoke = function1.invoke(ConstraintSystem.toBuilder$default(constraintSystem, null, 1, null));
        if (constraintSystemInvoke != null) {
            mutableResolvedCall.setConstraintSystem(constraintSystemInvoke);
        }
    }

    private final <D extends CallableDescriptor> void updateResolutionStatusFromConstraintSystem(MutableResolvedCall<D> mutableResolvedCall, BasicCallResolutionContext basicCallResolutionContext, TracingStrategy tracingStrategy) {
        CallCandidateResolutionContext<D> callCandidateResolutionContextCreateForCallBeingAnalyzed = CallCandidateResolutionContext.createForCallBeingAnalyzed(mutableResolvedCall, basicCallResolutionContext, tracingStrategy);
        Intrinsics.checkNotNullExpressionValue(callCandidateResolutionContextCreateForCallBeingAnalyzed, "createForCallBeingAnalyzed(...)");
        CandidateResolver.ValueArgumentsCheckingResult valueArgumentsCheckingResultCheckAllValueArguments = this.candidateResolver.checkAllValueArguments(callCandidateResolutionContextCreateForCallBeingAnalyzed, ResolveArgumentsMode.RESOLVE_FUNCTION_ARGUMENTS);
        ResolutionStatus status = mutableResolvedCall.getStatus();
        Intrinsics.checkNotNullExpressionValue(status, "getStatus(...)");
        ConstraintSystem constraintSystem = mutableResolvedCall.getConstraintSystem();
        Intrinsics.checkNotNull(constraintSystem);
        if (constraintSystem.getStatus().isSuccessful()) {
            if (status == ResolutionStatus.UNKNOWN_STATUS || status == ResolutionStatus.INCOMPLETE_TYPE_INFERENCE) {
                mutableResolvedCall.setStatusToSuccess();
                return;
            }
            return;
        }
        ReceiverValue extensionReceiver = mutableResolvedCall.getExtensionReceiver();
        KotlinType type = extensionReceiver != null ? extensionReceiver.getType() : null;
        D candidateDescriptor = mutableResolvedCall.get$suspendPropertyDescriptor();
        ConstraintSystem constraintSystem2 = mutableResolvedCall.getConstraintSystem();
        Intrinsics.checkNotNull(constraintSystem2);
        InferenceErrorData inferenceErrorDataCreate = InferenceErrorData.create(candidateDescriptor, constraintSystem2, valueArgumentsCheckingResultCheckAllValueArguments.getArgumentTypes(), type, basicCallResolutionContext.expectedType, basicCallResolutionContext.call);
        Intrinsics.checkNotNullExpressionValue(inferenceErrorDataCreate, "create(...)");
        tracingStrategy.typeInferenceFailed(basicCallResolutionContext, inferenceErrorDataCreate);
        mutableResolvedCall.addStatus(ResolutionStatus.OTHER_ERROR);
    }

    /* JADX WARN: Multi-variable type inference failed */
    private final <D extends CallableDescriptor> void completeArguments(final BasicCallResolutionContext context, OverloadResolutionResultsImpl<D> results) {
        AnonymousClass3 anonymousClass3;
        Function1<ValueArgument, DataFlowInfo> function1;
        SimpleType effectiveExpectedType;
        CallPosition.Unknown valueArgumentPosition;
        ValueParameterDescriptor valueParameter;
        if (context.checkArguments != CheckArgumentTypesMode.CHECK_VALUE_ARGUMENTS) {
            return;
        }
        if (results.isSingleResult()) {
            final MutableResolvedCall<D> mutableResolvedCallMo7436getResultingCall = results.mo7436getResultingCall();
            Intrinsics.checkNotNullExpressionValue(mutableResolvedCallMo7436getResultingCall, "getResultingCall(...)");
            anonymousClass3 = new Function1<ValueArgument, ArgumentMapping>() { // from class: org.jetbrains.kotlin.resolve.calls.CallCompleter.completeArguments.1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(1);
                }

                @Override // kotlin.jvm.functions.Function1
                public final ArgumentMapping invoke(ValueArgument argument) {
                    Intrinsics.checkNotNullParameter(argument, "argument");
                    ArgumentMapping argumentMapping = mutableResolvedCallMo7436getResultingCall.getArgumentMapping(argument);
                    Intrinsics.checkNotNullExpressionValue(argumentMapping, "getArgumentMapping(...)");
                    return argumentMapping;
                }
            };
            function1 = new Function1<ValueArgument, DataFlowInfo>() { // from class: org.jetbrains.kotlin.resolve.calls.CallCompleter.completeArguments.2
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(1);
                }

                @Override // kotlin.jvm.functions.Function1
                public final DataFlowInfo invoke(ValueArgument argument) {
                    Intrinsics.checkNotNullParameter(argument, "argument");
                    DataFlowInfo info = mutableResolvedCallMo7436getResultingCall.getDataFlowInfoForArguments().getInfo(argument);
                    Intrinsics.checkNotNullExpressionValue(info, "getInfo(...)");
                    return info;
                }
            };
        } else {
            anonymousClass3 = new Function1<ValueArgument, ArgumentUnmapped>() { // from class: org.jetbrains.kotlin.resolve.calls.CallCompleter.completeArguments.3
                @Override // kotlin.jvm.functions.Function1
                public final ArgumentUnmapped invoke(ValueArgument it) {
                    Intrinsics.checkNotNullParameter(it, "it");
                    return ArgumentUnmapped.INSTANCE;
                }
            };
            function1 = new Function1<ValueArgument, DataFlowInfo>() { // from class: org.jetbrains.kotlin.resolve.calls.CallCompleter.completeArguments.4
                {
                    super(1);
                }

                @Override // kotlin.jvm.functions.Function1
                public final DataFlowInfo invoke(ValueArgument it) {
                    Intrinsics.checkNotNullParameter(it, "it");
                    DataFlowInfo dataFlowInfo = context.dataFlowInfo;
                    Intrinsics.checkNotNullExpressionValue(dataFlowInfo, "dataFlowInfo");
                    return dataFlowInfo;
                }
            };
        }
        for (ValueArgument valueArgument : context.call.getValueArguments()) {
            Intrinsics.checkNotNull(valueArgument);
            ArgumentMapping argumentMappingInvoke = anonymousClass3.invoke(valueArgument);
            if (argumentMappingInvoke instanceof ArgumentMatch) {
                ArgumentMatch argumentMatch = (ArgumentMatch) argumentMappingInvoke;
                effectiveExpectedType = CallResolverUtilKt.getEffectiveExpectedType(argumentMatch.getValueParameter(), valueArgument, context);
                MutableResolvedCall<D> mutableResolvedCallMo7436getResultingCall2 = results.mo7436getResultingCall();
                Intrinsics.checkNotNullExpressionValue(mutableResolvedCallMo7436getResultingCall2, "getResultingCall(...)");
                valueArgumentPosition = new CallPosition.ValueArgumentPosition(mutableResolvedCallMo7436getResultingCall2, argumentMatch.getValueParameter(), valueArgument);
                valueParameter = argumentMatch.getValueParameter();
            } else {
                SimpleType NO_EXPECTED_TYPE = TypeUtils.NO_EXPECTED_TYPE;
                Intrinsics.checkNotNullExpressionValue(NO_EXPECTED_TYPE, "NO_EXPECTED_TYPE");
                effectiveExpectedType = NO_EXPECTED_TYPE;
                valueArgumentPosition = CallPosition.Unknown.INSTANCE;
                valueParameter = null;
            }
            Context contextReplaceCallPosition = ((BasicCallResolutionContext) ((BasicCallResolutionContext) context.replaceDataFlowInfo(function1.invoke(valueArgument))).replaceExpectedType(effectiveExpectedType)).replaceCallPosition(valueArgumentPosition);
            Intrinsics.checkNotNullExpressionValue(contextReplaceCallPosition, "replaceCallPosition(...)");
            completeOneArgument(valueArgument, valueParameter, (BasicCallResolutionContext) contextReplaceCallPosition);
        }
    }

    private final SimpleType createTypeForConvertableConstant(CompileTimeConstant<?> constant) {
        SimpleType NO_EXPECTED_TYPE = TypeUtils.NO_EXPECTED_TYPE;
        Intrinsics.checkNotNullExpressionValue(NO_EXPECTED_TYPE, "NO_EXPECTED_TYPE");
        Object value = constant.getValue(NO_EXPECTED_TYPE);
        Number number = value instanceof Number ? (Number) value : null;
        if (number == null) {
            return null;
        }
        IntegerValueTypeConstructor integerValueTypeConstructor = new IntegerValueTypeConstructor(number.longValue(), this.moduleDescriptor, constant.getParameters());
        return KotlinTypeFactory.simpleTypeWithNonTrivialMemberScope(TypeAttributes.INSTANCE.getEmpty(), integerValueTypeConstructor, CollectionsKt.emptyList(), false, ErrorUtils.createErrorScope(ErrorScopeKind.INTEGER_LITERAL_TYPE_SCOPE, true, integerValueTypeConstructor.toString()));
    }

    /* JADX WARN: Code duplicated, block: B:28:0x006b  */
    /* JADX WARN: Code duplicated, block: B:46:0x00c8 A[DONT_INVERT] */
    /* JADX WARN: Code duplicated, block: B:47:0x00ca  */
    private final void completeOneArgument(ValueArgument argument, ValueParameterDescriptor parameter, BasicCallResolutionContext context) {
        KtExpression argumentExpression;
        KtExpression lastElementDeparenthesized;
        KotlinType kotlinTypeMakeNullableTypeIfSafeReceiver;
        KotlinType kotlinTypeUpdateResultArgumentTypeIfNotDenotable;
        SimpleType simpleTypeCreateTypeForConvertableConstant;
        KotlinType kotlinTypeUpdateResultArgumentTypeIfNotDenotable2;
        if (argument.isExternal() || (argumentExpression = argument.getArgumentExpression()) == null || (lastElementDeparenthesized = KtPsiUtil.getLastElementDeparenthesized(argumentExpression, context.statementFilter)) == null) {
            return;
        }
        KotlinType type = context.trace.getType(argumentExpression);
        OverloadResolutionResultsImpl<?> overloadResolutionResultsImplCompleteCallForArgument = completeCallForArgument(lastElementDeparenthesized, context);
        CompileTimeConstant compileTimeConstant = (CompileTimeConstant) context.trace.get(BindingContext.COMPILE_TIME_VALUE, lastElementDeparenthesized);
        boolean z = (compileTimeConstant instanceof IntegerValueTypeConstant) && ((IntegerValueTypeConstant) compileTimeConstant).getConvertedFromSigned();
        if (overloadResolutionResultsImplCompleteCallForArgument == null || !overloadResolutionResultsImplCompleteCallForArgument.isSingleResult()) {
            kotlinTypeMakeNullableTypeIfSafeReceiver = type;
        } else {
            MutableResolvedCall<D> mutableResolvedCallMo7436getResultingCall = overloadResolutionResultsImplCompleteCallForArgument.mo7436getResultingCall();
            Intrinsics.checkNotNullExpressionValue(mutableResolvedCallMo7436getResultingCall, "getResultingCall(...)");
            if (z) {
                kotlinTypeMakeNullableTypeIfSafeReceiver = type;
            } else {
                kotlinTypeMakeNullableTypeIfSafeReceiver = null;
                if (mutableResolvedCallMo7436getResultingCall.hasInferredReturnType()) {
                    MutableResolvedCall<D> mutableResolvedCall = mutableResolvedCallMo7436getResultingCall;
                    CallableDescriptor resultingDescriptor = mutableResolvedCallMo7436getResultingCall.getResultingDescriptor();
                    kotlinTypeMakeNullableTypeIfSafeReceiver = ResolvedCallUtilKt.makeNullableTypeIfSafeReceiver(mutableResolvedCall, resultingDescriptor != null ? resultingDescriptor.getReturnType() : null, context);
                }
            }
        }
        if (type != null && !type.getConstructor().isDenotable() && (kotlinTypeUpdateResultArgumentTypeIfNotDenotable2 = this.argumentTypeResolver.updateResultArgumentTypeIfNotDenotable(context, argumentExpression)) != null) {
            kotlinTypeMakeNullableTypeIfSafeReceiver = kotlinTypeUpdateResultArgumentTypeIfNotDenotable2;
        }
        if (parameter != null) {
            LanguageVersionSettings languageVersionSettings = context.languageVersionSettings;
            Intrinsics.checkNotNullExpressionValue(languageVersionSettings, "languageVersionSettings");
            if (ImplicitIntegerCoercion.INSTANCE.isEnabledFor(parameter, languageVersionSettings)) {
                CompileTimeConstant<?> compileTimeConstant2 = (CompileTimeConstant) context.trace.get(BindingContext.COMPILE_TIME_VALUE, lastElementDeparenthesized);
                if (compileTimeConstant2 != null && compileTimeConstant2.getParameters().isConvertableConstVal() && (simpleTypeCreateTypeForConvertableConstant = createTypeForConvertableConstant(compileTimeConstant2)) != null) {
                    kotlinTypeUpdateResultArgumentTypeIfNotDenotable = this.argumentTypeResolver.updateResultArgumentTypeIfNotDenotable(context.trace, context.statementFilter, context.expectedType, simpleTypeCreateTypeForConvertableConstant, argumentExpression);
                }
            } else if (z) {
                context.trace.report(Errors.SIGNED_CONSTANT_CONVERTED_TO_UNSIGNED.on(lastElementDeparenthesized));
            }
            kotlinTypeUpdateResultArgumentTypeIfNotDenotable = kotlinTypeMakeNullableTypeIfSafeReceiver;
        } else {
            if (z) {
                context.trace.report(Errors.SIGNED_CONSTANT_CONVERTED_TO_UNSIGNED.on(lastElementDeparenthesized));
            }
            kotlinTypeUpdateResultArgumentTypeIfNotDenotable = kotlinTypeMakeNullableTypeIfSafeReceiver;
        }
        StatementFilter statementFilter = context.statementFilter;
        Intrinsics.checkNotNullExpressionValue(statementFilter, "statementFilter");
        BindingTrace trace = context.trace;
        Intrinsics.checkNotNullExpressionValue(trace, "trace");
        KotlinType kotlinTypeUpdateRecordedTypeForArgument = updateRecordedTypeForArgument(kotlinTypeUpdateResultArgumentTypeIfNotDenotable, type, argumentExpression, statementFilter, trace);
        BasicCallResolutionContext basicCallResolutionContext = context;
        KtFunction functionLiteralArgumentIfAny = ArgumentTypeResolver.getFunctionLiteralArgumentIfAny(argumentExpression, basicCallResolutionContext);
        if (functionLiteralArgumentIfAny != null) {
            this.argumentTypeResolver.getFunctionLiteralTypeInfo(argumentExpression, functionLiteralArgumentIfAny, context, ResolveArgumentsMode.RESOLVE_FUNCTION_ARGUMENTS, false);
        }
        KtCallableReferenceExpression callableReferenceExpressionIfAny = ArgumentTypeResolver.getCallableReferenceExpressionIfAny(argumentExpression, basicCallResolutionContext);
        if (callableReferenceExpressionIfAny != null) {
            this.argumentTypeResolver.getCallableReferenceTypeInfo(argumentExpression, callableReferenceExpressionIfAny, context, ResolveArgumentsMode.RESOLVE_FUNCTION_ARGUMENTS);
        }
        this.dataFlowAnalyzer.checkType(kotlinTypeUpdateRecordedTypeForArgument, lastElementDeparenthesized, basicCallResolutionContext);
    }

    /* JADX WARN: Multi-variable type inference failed */
    private final OverloadResolutionResultsImpl<?> completeCallForArgument(KtExpression expression, BasicCallResolutionContext context) {
        ResolutionResultsCache.CachedData resolutionResultsCachedData = GenericCandidateResolverKt.getResolutionResultsCachedData(expression, context);
        if (resolutionResultsCachedData == null) {
            return null;
        }
        OverloadResolutionResultsImpl<?> overloadResolutionResultsImplComponent1 = resolutionResultsCachedData.component1();
        BasicCallResolutionContext deferredComputation = resolutionResultsCachedData.getDeferredComputation();
        TracingStrategy tracing = resolutionResultsCachedData.getTracing();
        Context contextReplaceCallPosition = ((BasicCallResolutionContext) ((BasicCallResolutionContext) ((BasicCallResolutionContext) deferredComputation.replaceBindingTrace(context.trace)).replaceExpectedType(context.expectedType)).replaceCollectAllCandidates(false)).replaceCallPosition(context.callPosition);
        Intrinsics.checkNotNullExpressionValue(contextReplaceCallPosition, "replaceCallPosition(...)");
        return completeCall((BasicCallResolutionContext) contextReplaceCallPosition, overloadResolutionResultsImplComponent1, tracing);
    }

    private final KotlinType updateRecordedTypeForArgument(KotlinType updatedType, KotlinType recordedType, KtExpression argumentExpression, StatementFilter statementFilter, BindingTrace trace) {
        if ((!ErrorUtils.INSTANCE.containsErrorType(recordedType) && Intrinsics.areEqual(recordedType, updatedType)) || updatedType == null) {
            return updatedType;
        }
        ArrayList arrayList = new ArrayList();
        KtExpression ktExpressionUpdateRecordedTypeForArgument$deparenthesizeOrGetSelector = argumentExpression;
        while (ktExpressionUpdateRecordedTypeForArgument$deparenthesizeOrGetSelector != null) {
            arrayList.add(ktExpressionUpdateRecordedTypeForArgument$deparenthesizeOrGetSelector);
            ktExpressionUpdateRecordedTypeForArgument$deparenthesizeOrGetSelector = updateRecordedTypeForArgument$deparenthesizeOrGetSelector(statementFilter, ktExpressionUpdateRecordedTypeForArgument$deparenthesizeOrGetSelector);
        }
        boolean zHasNecessarySafeCall = false;
        for (KtExpression ktExpression : CollectionsKt.asReversedMutable(arrayList)) {
            if (!(ktExpression instanceof KtParenthesizedExpression) && !(ktExpression instanceof KtLabeledExpression) && !(ktExpression instanceof KtAnnotatedExpression)) {
                zHasNecessarySafeCall = hasNecessarySafeCall(ktExpression, trace);
            }
            BindingContextUtils.updateRecordedType(updatedType, ktExpression, trace, zHasNecessarySafeCall);
        }
        return trace.getType(argumentExpression);
    }

    private static final KtExpression updateRecordedTypeForArgument$deparenthesizeOrGetSelector(StatementFilter statementFilter, KtExpression ktExpression) {
        KtExpression ktExpressionDeparenthesizeOnce = KtPsiUtil.deparenthesizeOnce(ktExpression);
        if (!Intrinsics.areEqual(ktExpressionDeparenthesizeOnce, ktExpression)) {
            return ktExpressionDeparenthesizeOnce;
        }
        if (ktExpression instanceof KtBlockExpression) {
            return StatementFilterKt.getLastStatementInABlock(statementFilter, (KtBlockExpression) ktExpression);
        }
        KtQualifiedExpression ktQualifiedExpression = ktExpression instanceof KtQualifiedExpression ? (KtQualifiedExpression) ktExpression : null;
        if (ktQualifiedExpression != null) {
            return ktQualifiedExpression.getSelectorExpression();
        }
        return null;
    }

    private final boolean hasNecessarySafeCall(KtExpression expression, BindingTrace trace) {
        KotlinType type;
        return (expression instanceof KtSafeQualifiedExpression) && (type = trace.getType(((KtSafeQualifiedExpression) expression).getReceiverExpression())) != null && TypeUtils.isNullableType(type);
    }

    /* JADX WARN: Type inference failed for: r0v2, types: [org.jetbrains.kotlin.descriptors.CallableDescriptor] */
    private final void updateResultDataFlowInfoUsingEffects(MutableResolvedCall<?> mutableResolvedCall, BindingTrace bindingTrace) {
        ?? resultingDescriptor;
        DeclarationDescriptor containingDeclaration;
        if ((mutableResolvedCall.getDataFlowInfoForArguments() instanceof MutableDataFlowInfoForArguments.WithoutArgumentsCheck) || (resultingDescriptor = mutableResolvedCall.getResultingDescriptor()) == 0 || (containingDeclaration = resultingDescriptor.getContainingModule()) == null) {
            return;
        }
        ModuleDescriptor containingModule = DescriptorUtils.getContainingModule(containingDeclaration);
        Intrinsics.checkNotNullExpressionValue(containingModule, "getContainingModule(...)");
        MutableResolvedCall<?> mutableResolvedCall2 = mutableResolvedCall;
        mutableResolvedCall.getDataFlowInfoForArguments().updateResultInfo(this.effectSystem.getDataFlowInfoForFinishedCall(mutableResolvedCall2, bindingTrace, containingModule));
        this.effectSystem.recordDefiniteInvocations(mutableResolvedCall2, bindingTrace, containingModule);
    }
}
