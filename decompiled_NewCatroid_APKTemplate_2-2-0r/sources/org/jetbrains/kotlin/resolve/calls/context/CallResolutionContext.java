package org.jetbrains.kotlin.resolve.calls.context;

import kotlin.jvm.functions.Function1;
import org.jetbrains.kotlin.config.LanguageVersionSettings;
import org.jetbrains.kotlin.psi.Call;
import org.jetbrains.kotlin.psi.KtExpression;
import org.jetbrains.kotlin.resolve.BindingTrace;
import org.jetbrains.kotlin.resolve.StatementFilter;
import org.jetbrains.kotlin.resolve.calls.components.InferenceSession;
import org.jetbrains.kotlin.resolve.calls.context.CallResolutionContext;
import org.jetbrains.kotlin.resolve.calls.model.DataFlowInfoForArgumentsImpl;
import org.jetbrains.kotlin.resolve.calls.model.MutableDataFlowInfoForArguments;
import org.jetbrains.kotlin.resolve.calls.smartcasts.DataFlowInfo;
import org.jetbrains.kotlin.resolve.calls.smartcasts.DataFlowValueFactory;
import org.jetbrains.kotlin.resolve.scopes.LexicalScope;
import org.jetbrains.kotlin.types.KotlinType;

/* JADX INFO: loaded from: classes6.dex */
public abstract class CallResolutionContext<Context extends CallResolutionContext<Context>> extends ResolutionContext<Context> {
    public final Call call;
    public final CheckArgumentTypesMode checkArguments;
    public final MutableDataFlowInfoForArguments dataFlowInfoForArguments;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        Object[] objArr = new Object[3];
        switch (i) {
            case 1:
                objArr[0] = "scope";
                break;
            case 2:
                objArr[0] = "call";
                break;
            case 3:
                objArr[0] = "expectedType";
                break;
            case 4:
                objArr[0] = "dataFlowInfo";
                break;
            case 5:
                objArr[0] = "contextDependency";
                break;
            case 6:
                objArr[0] = "checkArguments";
                break;
            case 7:
                objArr[0] = "resolutionResultsCache";
                break;
            case 8:
                objArr[0] = "statementFilter";
                break;
            case 9:
                objArr[0] = "callPosition";
                break;
            case 10:
                objArr[0] = "expressionContextProvider";
                break;
            case 11:
                objArr[0] = "languageVersionSettings";
                break;
            case 12:
                objArr[0] = "dataFlowValueFactory";
                break;
            case 13:
                objArr[0] = "inferenceSession";
                break;
            default:
                objArr[0] = "trace";
                break;
        }
        objArr[1] = "org/jetbrains/kotlin/resolve/calls/context/CallResolutionContext";
        objArr[2] = "<init>";
        throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    protected CallResolutionContext(BindingTrace bindingTrace, LexicalScope lexicalScope, Call call, KotlinType kotlinType, DataFlowInfo dataFlowInfo, ContextDependency contextDependency, CheckArgumentTypesMode checkArgumentTypesMode, ResolutionResultsCache resolutionResultsCache, MutableDataFlowInfoForArguments mutableDataFlowInfoForArguments, StatementFilter statementFilter, boolean z, boolean z2, boolean z3, CallPosition callPosition, Function1<KtExpression, KtExpression> function1, LanguageVersionSettings languageVersionSettings, DataFlowValueFactory dataFlowValueFactory, InferenceSession inferenceSession) {
        super(bindingTrace, lexicalScope, kotlinType, dataFlowInfo, contextDependency, resolutionResultsCache, statementFilter, z, z2, z3, callPosition, function1, languageVersionSettings, dataFlowValueFactory, inferenceSession);
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
        this.call = call;
        this.checkArguments = checkArgumentTypesMode;
        if (mutableDataFlowInfoForArguments != null) {
            this.dataFlowInfoForArguments = mutableDataFlowInfoForArguments;
        } else if (checkArgumentTypesMode == CheckArgumentTypesMode.CHECK_VALUE_ARGUMENTS) {
            this.dataFlowInfoForArguments = new DataFlowInfoForArgumentsImpl(dataFlowInfo, call);
        } else {
            this.dataFlowInfoForArguments = new MutableDataFlowInfoForArguments.WithoutArgumentsCheck(dataFlowInfo);
        }
    }
}
