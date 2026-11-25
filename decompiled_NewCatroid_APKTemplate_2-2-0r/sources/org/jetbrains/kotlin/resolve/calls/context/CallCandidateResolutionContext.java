package org.jetbrains.kotlin.resolve.calls.context;

import kotlin.jvm.functions.Function1;
import org.jetbrains.kotlin.codegen.coroutines.CoroutineCodegenUtilKt;
import org.jetbrains.kotlin.config.LanguageVersionSettings;
import org.jetbrains.kotlin.descriptors.CallableDescriptor;
import org.jetbrains.kotlin.psi.Call;
import org.jetbrains.kotlin.psi.KtExpression;
import org.jetbrains.kotlin.resolve.BindingTrace;
import org.jetbrains.kotlin.resolve.StatementFilter;
import org.jetbrains.kotlin.resolve.calls.components.InferenceSession;
import org.jetbrains.kotlin.resolve.calls.model.MutableDataFlowInfoForArguments;
import org.jetbrains.kotlin.resolve.calls.model.MutableResolvedCall;
import org.jetbrains.kotlin.resolve.calls.smartcasts.DataFlowInfo;
import org.jetbrains.kotlin.resolve.calls.smartcasts.DataFlowValueFactory;
import org.jetbrains.kotlin.resolve.calls.tasks.TracingStrategy;
import org.jetbrains.kotlin.resolve.scopes.LexicalScope;
import org.jetbrains.kotlin.types.KotlinType;

/* JADX INFO: loaded from: classes6.dex */
public final class CallCandidateResolutionContext<D extends CallableDescriptor> extends CallResolutionContext<CallCandidateResolutionContext<D>> {
    public final MutableResolvedCall<D> candidateCall;
    public final CandidateResolveMode candidateResolveMode;
    public final TracingStrategy tracing;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        Object[] objArr = new Object[3];
        switch (i) {
            case 1:
            case 20:
            case 25:
                objArr[0] = "tracing";
                break;
            case 2:
            case 19:
            case 26:
                objArr[0] = "trace";
                break;
            case 3:
            case 27:
                objArr[0] = "scope";
                break;
            case 4:
            case 21:
                objArr[0] = "call";
                break;
            case 5:
            case 29:
                objArr[0] = "expectedType";
                break;
            case 6:
            case 28:
                objArr[0] = "dataFlowInfo";
                break;
            case 7:
            case 30:
                objArr[0] = "contextDependency";
                break;
            case 8:
                objArr[0] = "checkArguments";
                break;
            case 9:
            case 31:
                objArr[0] = "resolutionResultsCache";
                break;
            case 10:
            case 32:
                objArr[0] = "statementFilter";
                break;
            case 11:
            case 22:
                objArr[0] = "candidateResolveMode";
                break;
            case 12:
            case 33:
                objArr[0] = "callPosition";
                break;
            case 13:
            case 34:
                objArr[0] = "expressionContextProvider";
                break;
            case 14:
            case 35:
                objArr[0] = "languageVersionSettings";
                break;
            case 15:
            case 36:
                objArr[0] = "dataFlowValueFactory";
                break;
            case 16:
            case 37:
                objArr[0] = "inferenceSession";
                break;
            case 17:
            case 23:
            default:
                objArr[0] = "candidateCall";
                break;
            case 18:
            case 24:
                objArr[0] = "context";
                break;
        }
        objArr[1] = "org/jetbrains/kotlin/resolve/calls/context/CallCandidateResolutionContext";
        switch (i) {
            case 17:
            case 18:
            case 19:
            case 20:
            case 21:
            case 22:
            case 26:
            case 27:
            case 28:
            case 29:
            case 30:
            case 31:
            case 32:
            case 33:
            case 34:
            case 35:
            case 36:
            case 37:
                objArr[2] = CoroutineCodegenUtilKt.SUSPEND_FUNCTION_CREATE_METHOD_NAME;
                break;
            case 23:
            case 24:
            case 25:
                objArr[2] = "createForCallBeingAnalyzed";
                break;
            default:
                objArr[2] = "<init>";
                break;
        }
        throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
    }

    @Override // org.jetbrains.kotlin.resolve.calls.context.ResolutionContext
    protected /* bridge */ /* synthetic */ ResolutionContext create(BindingTrace bindingTrace, LexicalScope lexicalScope, DataFlowInfo dataFlowInfo, KotlinType kotlinType, ContextDependency contextDependency, ResolutionResultsCache resolutionResultsCache, StatementFilter statementFilter, boolean z, CallPosition callPosition, Function1 function1, LanguageVersionSettings languageVersionSettings, DataFlowValueFactory dataFlowValueFactory, InferenceSession inferenceSession) {
        return create(bindingTrace, lexicalScope, dataFlowInfo, kotlinType, contextDependency, resolutionResultsCache, statementFilter, z, callPosition, (Function1<KtExpression, KtExpression>) function1, languageVersionSettings, dataFlowValueFactory, inferenceSession);
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    private CallCandidateResolutionContext(MutableResolvedCall<D> mutableResolvedCall, TracingStrategy tracingStrategy, BindingTrace bindingTrace, LexicalScope lexicalScope, Call call, KotlinType kotlinType, DataFlowInfo dataFlowInfo, ContextDependency contextDependency, CheckArgumentTypesMode checkArgumentTypesMode, ResolutionResultsCache resolutionResultsCache, MutableDataFlowInfoForArguments mutableDataFlowInfoForArguments, StatementFilter statementFilter, CandidateResolveMode candidateResolveMode, boolean z, boolean z2, boolean z3, CallPosition callPosition, Function1<KtExpression, KtExpression> function1, LanguageVersionSettings languageVersionSettings, DataFlowValueFactory dataFlowValueFactory, InferenceSession inferenceSession) {
        super(bindingTrace, lexicalScope, call, kotlinType, dataFlowInfo, contextDependency, checkArgumentTypesMode, resolutionResultsCache, mutableDataFlowInfoForArguments, statementFilter, z, z2, z3, callPosition, function1, languageVersionSettings, dataFlowValueFactory, inferenceSession);
        if (mutableResolvedCall == null) {
            $$$reportNull$$$0(0);
        }
        if (tracingStrategy == null) {
            $$$reportNull$$$0(1);
        }
        if (bindingTrace == null) {
            $$$reportNull$$$0(2);
        }
        if (lexicalScope == null) {
            $$$reportNull$$$0(3);
        }
        if (call == null) {
            $$$reportNull$$$0(4);
        }
        if (kotlinType == null) {
            $$$reportNull$$$0(5);
        }
        if (dataFlowInfo == null) {
            $$$reportNull$$$0(6);
        }
        if (contextDependency == null) {
            $$$reportNull$$$0(7);
        }
        if (checkArgumentTypesMode == null) {
            $$$reportNull$$$0(8);
        }
        if (resolutionResultsCache == null) {
            $$$reportNull$$$0(9);
        }
        if (statementFilter == null) {
            $$$reportNull$$$0(10);
        }
        if (candidateResolveMode == null) {
            $$$reportNull$$$0(11);
        }
        if (callPosition == null) {
            $$$reportNull$$$0(12);
        }
        if (function1 == null) {
            $$$reportNull$$$0(13);
        }
        if (languageVersionSettings == null) {
            $$$reportNull$$$0(14);
        }
        if (dataFlowValueFactory == null) {
            $$$reportNull$$$0(15);
        }
        if (inferenceSession == null) {
            $$$reportNull$$$0(16);
        }
        this.candidateCall = mutableResolvedCall;
        this.tracing = tracingStrategy;
        this.candidateResolveMode = candidateResolveMode;
    }

    public static <D extends CallableDescriptor> CallCandidateResolutionContext<D> create(MutableResolvedCall<D> mutableResolvedCall, CallResolutionContext<?> callResolutionContext, BindingTrace bindingTrace, TracingStrategy tracingStrategy, Call call, CandidateResolveMode candidateResolveMode) {
        if (mutableResolvedCall == null) {
            $$$reportNull$$$0(17);
        }
        if (callResolutionContext == null) {
            $$$reportNull$$$0(18);
        }
        if (bindingTrace == null) {
            $$$reportNull$$$0(19);
        }
        if (tracingStrategy == null) {
            $$$reportNull$$$0(20);
        }
        if (call == null) {
            $$$reportNull$$$0(21);
        }
        if (candidateResolveMode == null) {
            $$$reportNull$$$0(22);
        }
        return new CallCandidateResolutionContext<>(mutableResolvedCall, tracingStrategy, bindingTrace, callResolutionContext.scope, call, callResolutionContext.expectedType, callResolutionContext.dataFlowInfo, callResolutionContext.contextDependency, callResolutionContext.checkArguments, callResolutionContext.resolutionResultsCache, callResolutionContext.dataFlowInfoForArguments, callResolutionContext.statementFilter, candidateResolveMode, callResolutionContext.isAnnotationContext, callResolutionContext.isDebuggerContext, callResolutionContext.collectAllCandidates, callResolutionContext.callPosition, callResolutionContext.expressionContextProvider, callResolutionContext.languageVersionSettings, callResolutionContext.dataFlowValueFactory, callResolutionContext.inferenceSession);
    }

    public static <D extends CallableDescriptor> CallCandidateResolutionContext<D> createForCallBeingAnalyzed(MutableResolvedCall<D> mutableResolvedCall, BasicCallResolutionContext basicCallResolutionContext, TracingStrategy tracingStrategy) {
        if (mutableResolvedCall == null) {
            $$$reportNull$$$0(23);
        }
        if (basicCallResolutionContext == null) {
            $$$reportNull$$$0(24);
        }
        if (tracingStrategy == null) {
            $$$reportNull$$$0(25);
        }
        return new CallCandidateResolutionContext<>(mutableResolvedCall, tracingStrategy, basicCallResolutionContext.trace, basicCallResolutionContext.scope, basicCallResolutionContext.call, basicCallResolutionContext.expectedType, basicCallResolutionContext.dataFlowInfo, basicCallResolutionContext.contextDependency, basicCallResolutionContext.checkArguments, basicCallResolutionContext.resolutionResultsCache, basicCallResolutionContext.dataFlowInfoForArguments, basicCallResolutionContext.statementFilter, CandidateResolveMode.FULLY, basicCallResolutionContext.isAnnotationContext, basicCallResolutionContext.isDebuggerContext, basicCallResolutionContext.collectAllCandidates, basicCallResolutionContext.callPosition, basicCallResolutionContext.expressionContextProvider, basicCallResolutionContext.languageVersionSettings, basicCallResolutionContext.dataFlowValueFactory, basicCallResolutionContext.inferenceSession);
    }

    @Override // org.jetbrains.kotlin.resolve.calls.context.ResolutionContext
    protected CallCandidateResolutionContext<D> create(BindingTrace bindingTrace, LexicalScope lexicalScope, DataFlowInfo dataFlowInfo, KotlinType kotlinType, ContextDependency contextDependency, ResolutionResultsCache resolutionResultsCache, StatementFilter statementFilter, boolean z, CallPosition callPosition, Function1<KtExpression, KtExpression> function1, LanguageVersionSettings languageVersionSettings, DataFlowValueFactory dataFlowValueFactory, InferenceSession inferenceSession) {
        if (bindingTrace == null) {
            $$$reportNull$$$0(26);
        }
        if (lexicalScope == null) {
            $$$reportNull$$$0(27);
        }
        if (dataFlowInfo == null) {
            $$$reportNull$$$0(28);
        }
        if (kotlinType == null) {
            $$$reportNull$$$0(29);
        }
        if (contextDependency == null) {
            $$$reportNull$$$0(30);
        }
        if (resolutionResultsCache == null) {
            $$$reportNull$$$0(31);
        }
        if (statementFilter == null) {
            $$$reportNull$$$0(32);
        }
        if (callPosition == null) {
            $$$reportNull$$$0(33);
        }
        if (function1 == null) {
            $$$reportNull$$$0(34);
        }
        if (languageVersionSettings == null) {
            $$$reportNull$$$0(35);
        }
        if (dataFlowValueFactory == null) {
            $$$reportNull$$$0(36);
        }
        if (inferenceSession == null) {
            $$$reportNull$$$0(37);
        }
        return new CallCandidateResolutionContext<>(this.candidateCall, this.tracing, bindingTrace, lexicalScope, this.call, kotlinType, dataFlowInfo, contextDependency, this.checkArguments, resolutionResultsCache, this.dataFlowInfoForArguments, statementFilter, this.candidateResolveMode, this.isAnnotationContext, this.isDebuggerContext, z, callPosition, function1, languageVersionSettings, dataFlowValueFactory, inferenceSession);
    }
}
