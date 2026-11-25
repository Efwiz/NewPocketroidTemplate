package org.jetbrains.kotlin.resolve.calls.context;

import kotlin.jvm.functions.Function1;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiFile;
import org.jetbrains.kotlin.config.LanguageVersionSettings;
import org.jetbrains.kotlin.psi.KtExpression;
import org.jetbrains.kotlin.resolve.BindingTrace;
import org.jetbrains.kotlin.resolve.StatementFilter;
import org.jetbrains.kotlin.resolve.calls.components.InferenceSession;
import org.jetbrains.kotlin.resolve.calls.context.ResolutionContext;
import org.jetbrains.kotlin.resolve.calls.smartcasts.DataFlowInfo;
import org.jetbrains.kotlin.resolve.calls.smartcasts.DataFlowValueFactory;
import org.jetbrains.kotlin.resolve.scopes.LexicalScope;
import org.jetbrains.kotlin.types.KotlinType;
import org.jetbrains.kotlin.types.TypeUtils;

/* JADX INFO: loaded from: classes6.dex */
public abstract class ResolutionContext<Context extends ResolutionContext<Context>> {
    public static final Function1<KtExpression, KtExpression> DEFAULT_EXPRESSION_CONTEXT_PROVIDER = new Function1() { // from class: org.jetbrains.kotlin.resolve.calls.context.ResolutionContext$$ExternalSyntheticLambda0
        @Override // kotlin.jvm.functions.Function1
        public final Object invoke(Object obj) {
            return ResolutionContext.lambda$static$0((KtExpression) obj);
        }
    };
    public final CallPosition callPosition;
    public final boolean collectAllCandidates;
    public final ContextDependency contextDependency;
    public final DataFlowInfo dataFlowInfo;
    public final DataFlowValueFactory dataFlowValueFactory;
    public final KotlinType expectedType;
    public final Function1<KtExpression, KtExpression> expressionContextProvider;
    public final InferenceSession inferenceSession;
    public final boolean isAnnotationContext;
    public final boolean isDebuggerContext;
    public final LanguageVersionSettings languageVersionSettings;
    public final ResolutionResultsCache resolutionResultsCache;
    public final LexicalScope scope;
    public final StatementFilter statementFilter;
    public final BindingTrace trace;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str;
        int i2;
        switch (i) {
            case 12:
            case 14:
            case 16:
            case 18:
            case 19:
            case 20:
            case 22:
            case 24:
            case 26:
            case 28:
            case 29:
            case 31:
            case 33:
            case 35:
                str = "@NotNull method %s.%s must not return null";
                break;
            case 13:
            case 15:
            case 17:
            case 21:
            case 23:
            case 25:
            case 27:
            case 30:
            case 32:
            case 34:
            default:
                str = "Argument for @NotNull parameter '%s' of %s.%s must not be null";
                break;
        }
        switch (i) {
            case 12:
            case 14:
            case 16:
            case 18:
            case 19:
            case 20:
            case 22:
            case 24:
            case 26:
            case 28:
            case 29:
            case 31:
            case 33:
            case 35:
                i2 = 2;
                break;
            case 13:
            case 15:
            case 17:
            case 21:
            case 23:
            case 25:
            case 27:
            case 30:
            case 32:
            case 34:
            default:
                i2 = 3;
                break;
        }
        Object[] objArr = new Object[i2];
        switch (i) {
            case 1:
                objArr[0] = "scope";
                break;
            case 2:
                objArr[0] = "expectedType";
                break;
            case 3:
                objArr[0] = "dataFlowInfo";
                break;
            case 4:
                objArr[0] = "contextDependency";
                break;
            case 5:
                objArr[0] = "resolutionResultsCache";
                break;
            case 6:
            case 30:
                objArr[0] = "statementFilter";
                break;
            case 7:
            case 32:
                objArr[0] = "callPosition";
                break;
            case 8:
            case 34:
                objArr[0] = "expressionContextProvider";
                break;
            case 9:
                objArr[0] = "languageVersionSettings";
                break;
            case 10:
                objArr[0] = "factory";
                break;
            case 11:
                objArr[0] = "inferenceSession";
                break;
            case 12:
            case 14:
            case 16:
            case 18:
            case 19:
            case 20:
            case 22:
            case 24:
            case 26:
            case 28:
            case 29:
            case 31:
            case 33:
            case 35:
                objArr[0] = "org/jetbrains/kotlin/resolve/calls/context/ResolutionContext";
                break;
            case 13:
            default:
                objArr[0] = "trace";
                break;
            case 15:
                objArr[0] = "newDataFlowInfo";
                break;
            case 17:
                objArr[0] = "newInferenceSession";
                break;
            case 21:
                objArr[0] = "newScope";
                break;
            case 23:
                objArr[0] = "newContextDependency";
                break;
            case 25:
                objArr[0] = "newResolutionResultsCache";
                break;
            case 27:
                objArr[0] = "traceAndCache";
                break;
            case 36:
                objArr[0] = "expression";
                break;
            case 37:
                objArr[0] = "classes";
                break;
        }
        switch (i) {
            case 12:
                objArr[1] = "self";
                break;
            case 13:
            case 15:
            case 17:
            case 21:
            case 23:
            case 25:
            case 27:
            case 30:
            case 32:
            case 34:
            default:
                objArr[1] = "org/jetbrains/kotlin/resolve/calls/context/ResolutionContext";
                break;
            case 14:
                objArr[1] = "replaceBindingTrace";
                break;
            case 16:
                objArr[1] = "replaceDataFlowInfo";
                break;
            case 18:
                objArr[1] = "replaceInferenceSession";
                break;
            case 19:
            case 20:
                objArr[1] = "replaceExpectedType";
                break;
            case 22:
                objArr[1] = "replaceScope";
                break;
            case 24:
                objArr[1] = "replaceContextDependency";
                break;
            case 26:
                objArr[1] = "replaceResolutionResultsCache";
                break;
            case 28:
                objArr[1] = "replaceTraceAndCache";
                break;
            case 29:
                objArr[1] = "replaceCollectAllCandidates";
                break;
            case 31:
                objArr[1] = "replaceStatementFilter";
                break;
            case 33:
                objArr[1] = "replaceCallPosition";
                break;
            case 35:
                objArr[1] = "replaceExpressionContextProvider";
                break;
        }
        switch (i) {
            case 12:
            case 14:
            case 16:
            case 18:
            case 19:
            case 20:
            case 22:
            case 24:
            case 26:
            case 28:
            case 29:
            case 31:
            case 33:
            case 35:
                break;
            case 13:
                objArr[2] = "replaceBindingTrace";
                break;
            case 15:
                objArr[2] = "replaceDataFlowInfo";
                break;
            case 17:
                objArr[2] = "replaceInferenceSession";
                break;
            case 21:
                objArr[2] = "replaceScope";
                break;
            case 23:
                objArr[2] = "replaceContextDependency";
                break;
            case 25:
                objArr[2] = "replaceResolutionResultsCache";
                break;
            case 27:
                objArr[2] = "replaceTraceAndCache";
                break;
            case 30:
                objArr[2] = "replaceStatementFilter";
                break;
            case 32:
                objArr[2] = "replaceCallPosition";
                break;
            case 34:
                objArr[2] = "replaceExpressionContextProvider";
                break;
            case 36:
            case 37:
                objArr[2] = "getContextParentOfType";
                break;
            default:
                objArr[2] = "<init>";
                break;
        }
        String str2 = String.format(str, objArr);
        switch (i) {
            case 12:
            case 14:
            case 16:
            case 18:
            case 19:
            case 20:
            case 22:
            case 24:
            case 26:
            case 28:
            case 29:
            case 31:
            case 33:
            case 35:
                throw new IllegalStateException(str2);
            case 13:
            case 15:
            case 17:
            case 21:
            case 23:
            case 25:
            case 27:
            case 30:
            case 32:
            case 34:
            default:
                throw new IllegalArgumentException(str2);
        }
    }

    static /* synthetic */ KtExpression lambda$static$0(KtExpression ktExpression) {
        return null;
    }

    private Context self() {
        return this;
    }

    protected abstract Context create(BindingTrace bindingTrace, LexicalScope lexicalScope, DataFlowInfo dataFlowInfo, KotlinType kotlinType, ContextDependency contextDependency, ResolutionResultsCache resolutionResultsCache, StatementFilter statementFilter, boolean z, CallPosition callPosition, Function1<KtExpression, KtExpression> function1, LanguageVersionSettings languageVersionSettings, DataFlowValueFactory dataFlowValueFactory, InferenceSession inferenceSession);

    protected ResolutionContext(BindingTrace bindingTrace, LexicalScope lexicalScope, KotlinType kotlinType, DataFlowInfo dataFlowInfo, ContextDependency contextDependency, ResolutionResultsCache resolutionResultsCache, StatementFilter statementFilter, boolean z, boolean z2, boolean z3, CallPosition callPosition, Function1<KtExpression, KtExpression> function1, LanguageVersionSettings languageVersionSettings, DataFlowValueFactory dataFlowValueFactory, InferenceSession inferenceSession) {
        if (bindingTrace == null) {
            $$$reportNull$$$0(0);
        }
        if (lexicalScope == null) {
            $$$reportNull$$$0(1);
        }
        if (kotlinType == null) {
            $$$reportNull$$$0(2);
        }
        if (dataFlowInfo == null) {
            $$$reportNull$$$0(3);
        }
        if (contextDependency == null) {
            $$$reportNull$$$0(4);
        }
        if (resolutionResultsCache == null) {
            $$$reportNull$$$0(5);
        }
        if (statementFilter == null) {
            $$$reportNull$$$0(6);
        }
        if (callPosition == null) {
            $$$reportNull$$$0(7);
        }
        if (function1 == null) {
            $$$reportNull$$$0(8);
        }
        if (languageVersionSettings == null) {
            $$$reportNull$$$0(9);
        }
        if (dataFlowValueFactory == null) {
            $$$reportNull$$$0(10);
        }
        if (inferenceSession == null) {
            $$$reportNull$$$0(11);
        }
        this.trace = bindingTrace;
        this.scope = lexicalScope;
        this.expectedType = kotlinType;
        this.dataFlowInfo = dataFlowInfo;
        this.contextDependency = contextDependency;
        this.resolutionResultsCache = resolutionResultsCache;
        this.statementFilter = statementFilter;
        this.isAnnotationContext = z;
        this.isDebuggerContext = z2;
        this.collectAllCandidates = z3;
        this.callPosition = callPosition;
        this.expressionContextProvider = function1;
        this.languageVersionSettings = languageVersionSettings;
        this.dataFlowValueFactory = dataFlowValueFactory;
        this.inferenceSession = inferenceSession;
    }

    public Context replaceBindingTrace(BindingTrace bindingTrace) {
        if (bindingTrace == null) {
            $$$reportNull$$$0(13);
        }
        if (this.trace == bindingTrace) {
            return (Context) self();
        }
        Context context = (Context) create(bindingTrace, this.scope, this.dataFlowInfo, this.expectedType, this.contextDependency, this.resolutionResultsCache, this.statementFilter, this.collectAllCandidates, this.callPosition, this.expressionContextProvider, this.languageVersionSettings, this.dataFlowValueFactory, this.inferenceSession);
        if (context == null) {
            $$$reportNull$$$0(14);
        }
        return context;
    }

    public Context replaceDataFlowInfo(DataFlowInfo dataFlowInfo) {
        if (dataFlowInfo == null) {
            $$$reportNull$$$0(15);
        }
        if (dataFlowInfo == this.dataFlowInfo) {
            return (Context) self();
        }
        Context context = (Context) create(this.trace, this.scope, dataFlowInfo, this.expectedType, this.contextDependency, this.resolutionResultsCache, this.statementFilter, this.collectAllCandidates, this.callPosition, this.expressionContextProvider, this.languageVersionSettings, this.dataFlowValueFactory, this.inferenceSession);
        if (context == null) {
            $$$reportNull$$$0(16);
        }
        return context;
    }

    public Context replaceInferenceSession(InferenceSession inferenceSession) {
        if (inferenceSession == null) {
            $$$reportNull$$$0(17);
        }
        if (inferenceSession == this.inferenceSession) {
            return (Context) self();
        }
        Context context = (Context) create(this.trace, this.scope, this.dataFlowInfo, this.expectedType, this.contextDependency, this.resolutionResultsCache, this.statementFilter, this.collectAllCandidates, this.callPosition, this.expressionContextProvider, this.languageVersionSettings, this.dataFlowValueFactory, inferenceSession);
        if (context == null) {
            $$$reportNull$$$0(18);
        }
        return context;
    }

    public Context replaceExpectedType(KotlinType kotlinType) {
        if (kotlinType == null) {
            Context context = (Context) replaceExpectedType(TypeUtils.NO_EXPECTED_TYPE);
            if (context == null) {
                $$$reportNull$$$0(19);
            }
            return context;
        }
        if (this.expectedType == kotlinType) {
            return (Context) self();
        }
        Context context2 = (Context) create(this.trace, this.scope, this.dataFlowInfo, kotlinType, this.contextDependency, this.resolutionResultsCache, this.statementFilter, this.collectAllCandidates, this.callPosition, this.expressionContextProvider, this.languageVersionSettings, this.dataFlowValueFactory, this.inferenceSession);
        if (context2 == null) {
            $$$reportNull$$$0(20);
        }
        return context2;
    }

    public Context replaceScope(LexicalScope lexicalScope) {
        if (lexicalScope == null) {
            $$$reportNull$$$0(21);
        }
        if (lexicalScope == this.scope) {
            return (Context) self();
        }
        Context context = (Context) create(this.trace, lexicalScope, this.dataFlowInfo, this.expectedType, this.contextDependency, this.resolutionResultsCache, this.statementFilter, this.collectAllCandidates, this.callPosition, this.expressionContextProvider, this.languageVersionSettings, this.dataFlowValueFactory, this.inferenceSession);
        if (context == null) {
            $$$reportNull$$$0(22);
        }
        return context;
    }

    public Context replaceContextDependency(ContextDependency contextDependency) {
        if (contextDependency == null) {
            $$$reportNull$$$0(23);
        }
        if (contextDependency == this.contextDependency) {
            return (Context) self();
        }
        Context context = (Context) create(this.trace, this.scope, this.dataFlowInfo, this.expectedType, contextDependency, this.resolutionResultsCache, this.statementFilter, this.collectAllCandidates, this.callPosition, this.expressionContextProvider, this.languageVersionSettings, this.dataFlowValueFactory, this.inferenceSession);
        if (context == null) {
            $$$reportNull$$$0(24);
        }
        return context;
    }

    public Context replaceResolutionResultsCache(ResolutionResultsCache resolutionResultsCache) {
        if (resolutionResultsCache == null) {
            $$$reportNull$$$0(25);
        }
        if (resolutionResultsCache == this.resolutionResultsCache) {
            return (Context) self();
        }
        Context context = (Context) create(this.trace, this.scope, this.dataFlowInfo, this.expectedType, this.contextDependency, resolutionResultsCache, this.statementFilter, this.collectAllCandidates, this.callPosition, this.expressionContextProvider, this.languageVersionSettings, this.dataFlowValueFactory, this.inferenceSession);
        if (context == null) {
            $$$reportNull$$$0(26);
        }
        return context;
    }

    public Context replaceTraceAndCache(TemporaryTraceAndCache temporaryTraceAndCache) {
        if (temporaryTraceAndCache == null) {
            $$$reportNull$$$0(27);
        }
        Context context = (Context) replaceBindingTrace(temporaryTraceAndCache.trace).replaceResolutionResultsCache(temporaryTraceAndCache.cache);
        if (context == null) {
            $$$reportNull$$$0(28);
        }
        return context;
    }

    public Context replaceCollectAllCandidates(boolean z) {
        Context context = (Context) create(this.trace, this.scope, this.dataFlowInfo, this.expectedType, this.contextDependency, this.resolutionResultsCache, this.statementFilter, z, this.callPosition, this.expressionContextProvider, this.languageVersionSettings, this.dataFlowValueFactory, this.inferenceSession);
        if (context == null) {
            $$$reportNull$$$0(29);
        }
        return context;
    }

    public Context replaceStatementFilter(StatementFilter statementFilter) {
        if (statementFilter == null) {
            $$$reportNull$$$0(30);
        }
        Context context = (Context) create(this.trace, this.scope, this.dataFlowInfo, this.expectedType, this.contextDependency, this.resolutionResultsCache, statementFilter, this.collectAllCandidates, this.callPosition, this.expressionContextProvider, this.languageVersionSettings, this.dataFlowValueFactory, this.inferenceSession);
        if (context == null) {
            $$$reportNull$$$0(31);
        }
        return context;
    }

    public Context replaceCallPosition(CallPosition callPosition) {
        if (callPosition == null) {
            $$$reportNull$$$0(32);
        }
        Context context = (Context) create(this.trace, this.scope, this.dataFlowInfo, this.expectedType, this.contextDependency, this.resolutionResultsCache, this.statementFilter, this.collectAllCandidates, callPosition, this.expressionContextProvider, this.languageVersionSettings, this.dataFlowValueFactory, this.inferenceSession);
        if (context == null) {
            $$$reportNull$$$0(33);
        }
        return context;
    }

    public Context replaceExpressionContextProvider(Function1<KtExpression, KtExpression> function1) {
        if (function1 == null) {
            $$$reportNull$$$0(34);
        }
        Context context = (Context) create(this.trace, this.scope, this.dataFlowInfo, this.expectedType, this.contextDependency, this.resolutionResultsCache, this.statementFilter, this.collectAllCandidates, this.callPosition, function1, this.languageVersionSettings, this.dataFlowValueFactory, this.inferenceSession);
        if (context == null) {
            $$$reportNull$$$0(35);
        }
        return context;
    }

    @SafeVarargs
    public final <T extends PsiElement> T getContextParentOfType(KtExpression ktExpression, Class<? extends T>... clsArr) {
        KtExpression ktExpressionInvoke;
        if (ktExpression == null) {
            $$$reportNull$$$0(36);
        }
        if (clsArr == null) {
            $$$reportNull$$$0(37);
        }
        KtExpression ktExpressionInvoke2 = this.expressionContextProvider.invoke(ktExpression);
        if (ktExpressionInvoke2 == null) {
            ktExpressionInvoke2 = (T) ktExpression.getParent();
        }
        while (ktExpressionInvoke2 != null) {
            for (Class<? extends T> cls : clsArr) {
                if (cls.isInstance(ktExpressionInvoke2)) {
                    return ktExpressionInvoke2;
                }
            }
            if (ktExpressionInvoke2 instanceof PsiFile) {
                return null;
            }
            ktExpressionInvoke2 = (!(ktExpressionInvoke2 instanceof KtExpression) || (ktExpressionInvoke = this.expressionContextProvider.invoke(ktExpressionInvoke2)) == null) ? (T) ktExpressionInvoke2.getParent() : ktExpressionInvoke;
        }
        return null;
    }
}
