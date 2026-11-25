package org.jetbrains.kotlin.resolve.calls.context;

import kotlin.jvm.functions.Function1;
import org.jetbrains.kotlin.codegen.coroutines.CoroutineCodegenUtilKt;
import org.jetbrains.kotlin.config.LanguageVersionSettings;
import org.jetbrains.kotlin.psi.Call;
import org.jetbrains.kotlin.psi.KtExpression;
import org.jetbrains.kotlin.resolve.BindingTrace;
import org.jetbrains.kotlin.resolve.StatementFilter;
import org.jetbrains.kotlin.resolve.calls.components.InferenceSession;
import org.jetbrains.kotlin.resolve.calls.model.MutableDataFlowInfoForArguments;
import org.jetbrains.kotlin.resolve.calls.smartcasts.DataFlowInfo;
import org.jetbrains.kotlin.resolve.calls.smartcasts.DataFlowValueFactory;
import org.jetbrains.kotlin.resolve.scopes.LexicalScope;
import org.jetbrains.kotlin.types.KotlinType;

/* JADX INFO: loaded from: classes6.dex */
public class BasicCallResolutionContext extends CallResolutionContext<BasicCallResolutionContext> {
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        Object[] objArr = new Object[3];
        switch (i) {
            case 1:
            case 15:
            case 25:
            case 40:
                objArr[0] = "scope";
                break;
            case 2:
            case 16:
            case 26:
            case 34:
            case 37:
                objArr[0] = "call";
                break;
            case 3:
            case 17:
            case 27:
            case 42:
                objArr[0] = "expectedType";
                break;
            case 4:
            case 18:
            case 28:
            case 41:
                objArr[0] = "dataFlowInfo";
                break;
            case 5:
            case 19:
            case 29:
            case 43:
                objArr[0] = "contextDependency";
                break;
            case 6:
            case 20:
            case 30:
            case 35:
            case 38:
                objArr[0] = "checkArguments";
                break;
            case 7:
            case 44:
                objArr[0] = "resolutionResultsCache";
                break;
            case 8:
            case 45:
                objArr[0] = "statementFilter";
                break;
            case 9:
            case 46:
                objArr[0] = "callPosition";
                break;
            case 10:
            case 47:
                objArr[0] = "expressionContextProvider";
                break;
            case 11:
            case 21:
            case 31:
            case 48:
                objArr[0] = "languageVersionSettings";
                break;
            case 12:
            case 22:
            case 32:
            case 49:
                objArr[0] = "dataFlowValueFactory";
                break;
            case 13:
            case 23:
            case 50:
                objArr[0] = "inferenceSession";
                break;
            case 14:
            case 24:
            case 39:
            default:
                objArr[0] = "trace";
                break;
            case 33:
            case 36:
                objArr[0] = "context";
                break;
            case 51:
                objArr[0] = "newCall";
                break;
        }
        objArr[1] = "org/jetbrains/kotlin/resolve/calls/context/BasicCallResolutionContext";
        switch (i) {
            case 14:
            case 15:
            case 16:
            case 17:
            case 18:
            case 19:
            case 20:
            case 21:
            case 22:
            case 23:
            case 24:
            case 25:
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
            case 38:
            case 39:
            case 40:
            case 41:
            case 42:
            case 43:
            case 44:
            case 45:
            case 46:
            case 47:
            case 48:
            case 49:
            case 50:
                objArr[2] = CoroutineCodegenUtilKt.SUSPEND_FUNCTION_CREATE_METHOD_NAME;
                break;
            case 51:
                objArr[2] = "replaceCall";
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
    private BasicCallResolutionContext(BindingTrace bindingTrace, LexicalScope lexicalScope, Call call, KotlinType kotlinType, DataFlowInfo dataFlowInfo, ContextDependency contextDependency, CheckArgumentTypesMode checkArgumentTypesMode, ResolutionResultsCache resolutionResultsCache, MutableDataFlowInfoForArguments mutableDataFlowInfoForArguments, StatementFilter statementFilter, boolean z, boolean z2, boolean z3, CallPosition callPosition, Function1<KtExpression, KtExpression> function1, LanguageVersionSettings languageVersionSettings, DataFlowValueFactory dataFlowValueFactory, InferenceSession inferenceSession) {
        super(bindingTrace, lexicalScope, call, kotlinType, dataFlowInfo, contextDependency, checkArgumentTypesMode, resolutionResultsCache, mutableDataFlowInfoForArguments, statementFilter, z, z2, z3, callPosition, function1, languageVersionSettings, dataFlowValueFactory, inferenceSession);
        if (bindingTrace == null) {
            $$$reportNull$$$0(0);
        }
        if (lexicalScope == null) {
            $$$reportNull$$$0(1);
        }
        if (call == null) {
            $$$reportNull$$$0(2);
        }
        if (kotlinType == null) {
            $$$reportNull$$$0(3);
        }
        if (dataFlowInfo == null) {
            $$$reportNull$$$0(4);
        }
        if (contextDependency == null) {
            $$$reportNull$$$0(5);
        }
        if (checkArgumentTypesMode == null) {
            $$$reportNull$$$0(6);
        }
        if (resolutionResultsCache == null) {
            $$$reportNull$$$0(7);
        }
        if (statementFilter == null) {
            $$$reportNull$$$0(8);
        }
        if (callPosition == null) {
            $$$reportNull$$$0(9);
        }
        if (function1 == null) {
            $$$reportNull$$$0(10);
        }
        if (languageVersionSettings == null) {
            $$$reportNull$$$0(11);
        }
        if (dataFlowValueFactory == null) {
            $$$reportNull$$$0(12);
        }
        if (inferenceSession == null) {
            $$$reportNull$$$0(13);
        }
    }

    public static BasicCallResolutionContext create(BindingTrace bindingTrace, LexicalScope lexicalScope, Call call, KotlinType kotlinType, DataFlowInfo dataFlowInfo, ContextDependency contextDependency, CheckArgumentTypesMode checkArgumentTypesMode, boolean z, LanguageVersionSettings languageVersionSettings, DataFlowValueFactory dataFlowValueFactory, InferenceSession inferenceSession) {
        if (bindingTrace == null) {
            $$$reportNull$$$0(14);
        }
        if (lexicalScope == null) {
            $$$reportNull$$$0(15);
        }
        if (call == null) {
            $$$reportNull$$$0(16);
        }
        if (kotlinType == null) {
            $$$reportNull$$$0(17);
        }
        if (dataFlowInfo == null) {
            $$$reportNull$$$0(18);
        }
        if (contextDependency == null) {
            $$$reportNull$$$0(19);
        }
        if (checkArgumentTypesMode == null) {
            $$$reportNull$$$0(20);
        }
        if (languageVersionSettings == null) {
            $$$reportNull$$$0(21);
        }
        if (dataFlowValueFactory == null) {
            $$$reportNull$$$0(22);
        }
        if (inferenceSession == null) {
            $$$reportNull$$$0(23);
        }
        return new BasicCallResolutionContext(bindingTrace, lexicalScope, call, kotlinType, dataFlowInfo, contextDependency, checkArgumentTypesMode, new ResolutionResultsCacheImpl(), null, StatementFilter.NONE, z, false, false, CallPosition.Unknown.INSTANCE, DEFAULT_EXPRESSION_CONTEXT_PROVIDER, languageVersionSettings, dataFlowValueFactory, inferenceSession);
    }

    public static BasicCallResolutionContext create(BindingTrace bindingTrace, LexicalScope lexicalScope, Call call, KotlinType kotlinType, DataFlowInfo dataFlowInfo, ContextDependency contextDependency, CheckArgumentTypesMode checkArgumentTypesMode, boolean z, LanguageVersionSettings languageVersionSettings, DataFlowValueFactory dataFlowValueFactory) {
        if (bindingTrace == null) {
            $$$reportNull$$$0(24);
        }
        if (lexicalScope == null) {
            $$$reportNull$$$0(25);
        }
        if (call == null) {
            $$$reportNull$$$0(26);
        }
        if (kotlinType == null) {
            $$$reportNull$$$0(27);
        }
        if (dataFlowInfo == null) {
            $$$reportNull$$$0(28);
        }
        if (contextDependency == null) {
            $$$reportNull$$$0(29);
        }
        if (checkArgumentTypesMode == null) {
            $$$reportNull$$$0(30);
        }
        if (languageVersionSettings == null) {
            $$$reportNull$$$0(31);
        }
        if (dataFlowValueFactory == null) {
            $$$reportNull$$$0(32);
        }
        return new BasicCallResolutionContext(bindingTrace, lexicalScope, call, kotlinType, dataFlowInfo, contextDependency, checkArgumentTypesMode, new ResolutionResultsCacheImpl(), null, StatementFilter.NONE, z, false, false, CallPosition.Unknown.INSTANCE, DEFAULT_EXPRESSION_CONTEXT_PROVIDER, languageVersionSettings, dataFlowValueFactory, InferenceSession.INSTANCE.getDefault());
    }

    public static BasicCallResolutionContext create(ResolutionContext<?> resolutionContext, Call call, CheckArgumentTypesMode checkArgumentTypesMode, MutableDataFlowInfoForArguments mutableDataFlowInfoForArguments) {
        if (resolutionContext == null) {
            $$$reportNull$$$0(33);
        }
        if (call == null) {
            $$$reportNull$$$0(34);
        }
        if (checkArgumentTypesMode == null) {
            $$$reportNull$$$0(35);
        }
        return new BasicCallResolutionContext(resolutionContext.trace, resolutionContext.scope, call, resolutionContext.expectedType, resolutionContext.dataFlowInfo, resolutionContext.contextDependency, checkArgumentTypesMode, resolutionContext.resolutionResultsCache, mutableDataFlowInfoForArguments, resolutionContext.statementFilter, resolutionContext.isAnnotationContext, resolutionContext.isDebuggerContext, resolutionContext.collectAllCandidates, resolutionContext.callPosition, resolutionContext.expressionContextProvider, resolutionContext.languageVersionSettings, resolutionContext.dataFlowValueFactory, resolutionContext.inferenceSession);
    }

    public static BasicCallResolutionContext create(ResolutionContext resolutionContext, Call call, CheckArgumentTypesMode checkArgumentTypesMode) {
        if (resolutionContext == null) {
            $$$reportNull$$$0(36);
        }
        if (call == null) {
            $$$reportNull$$$0(37);
        }
        if (checkArgumentTypesMode == null) {
            $$$reportNull$$$0(38);
        }
        return create(resolutionContext, call, checkArgumentTypesMode, null);
    }

    @Override // org.jetbrains.kotlin.resolve.calls.context.ResolutionContext
    protected BasicCallResolutionContext create(BindingTrace bindingTrace, LexicalScope lexicalScope, DataFlowInfo dataFlowInfo, KotlinType kotlinType, ContextDependency contextDependency, ResolutionResultsCache resolutionResultsCache, StatementFilter statementFilter, boolean z, CallPosition callPosition, Function1<KtExpression, KtExpression> function1, LanguageVersionSettings languageVersionSettings, DataFlowValueFactory dataFlowValueFactory, InferenceSession inferenceSession) {
        if (bindingTrace == null) {
            $$$reportNull$$$0(39);
        }
        if (lexicalScope == null) {
            $$$reportNull$$$0(40);
        }
        if (dataFlowInfo == null) {
            $$$reportNull$$$0(41);
        }
        if (kotlinType == null) {
            $$$reportNull$$$0(42);
        }
        if (contextDependency == null) {
            $$$reportNull$$$0(43);
        }
        if (resolutionResultsCache == null) {
            $$$reportNull$$$0(44);
        }
        if (statementFilter == null) {
            $$$reportNull$$$0(45);
        }
        if (callPosition == null) {
            $$$reportNull$$$0(46);
        }
        if (function1 == null) {
            $$$reportNull$$$0(47);
        }
        if (languageVersionSettings == null) {
            $$$reportNull$$$0(48);
        }
        if (dataFlowValueFactory == null) {
            $$$reportNull$$$0(49);
        }
        if (inferenceSession == null) {
            $$$reportNull$$$0(50);
        }
        return new BasicCallResolutionContext(bindingTrace, lexicalScope, this.call, kotlinType, dataFlowInfo, contextDependency, this.checkArguments, resolutionResultsCache, this.dataFlowInfoForArguments, statementFilter, this.isAnnotationContext, this.isDebuggerContext, z, callPosition, function1, languageVersionSettings, dataFlowValueFactory, inferenceSession);
    }

    public BasicCallResolutionContext replaceCall(Call call) {
        if (call == null) {
            $$$reportNull$$$0(51);
        }
        return new BasicCallResolutionContext(this.trace, this.scope, call, this.expectedType, this.dataFlowInfo, this.contextDependency, this.checkArguments, this.resolutionResultsCache, this.dataFlowInfoForArguments, this.statementFilter, this.isAnnotationContext, this.isDebuggerContext, this.collectAllCandidates, this.callPosition, this.expressionContextProvider, this.languageVersionSettings, this.dataFlowValueFactory, this.inferenceSession);
    }
}
