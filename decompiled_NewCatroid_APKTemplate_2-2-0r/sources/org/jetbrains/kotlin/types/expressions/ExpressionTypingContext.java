package org.jetbrains.kotlin.types.expressions;

import kotlin.jvm.functions.Function1;
import org.jetbrains.kotlin.codegen.coroutines.CoroutineCodegenUtilKt;
import org.jetbrains.kotlin.config.LanguageVersionSettings;
import org.jetbrains.kotlin.psi.KtExpression;
import org.jetbrains.kotlin.resolve.BindingTrace;
import org.jetbrains.kotlin.resolve.StatementFilter;
import org.jetbrains.kotlin.resolve.calls.components.InferenceSession;
import org.jetbrains.kotlin.resolve.calls.context.CallPosition;
import org.jetbrains.kotlin.resolve.calls.context.ContextDependency;
import org.jetbrains.kotlin.resolve.calls.context.ResolutionContext;
import org.jetbrains.kotlin.resolve.calls.context.ResolutionResultsCache;
import org.jetbrains.kotlin.resolve.calls.context.ResolutionResultsCacheImpl;
import org.jetbrains.kotlin.resolve.calls.smartcasts.DataFlowInfo;
import org.jetbrains.kotlin.resolve.calls.smartcasts.DataFlowValueFactory;
import org.jetbrains.kotlin.resolve.scopes.LexicalScope;
import org.jetbrains.kotlin.types.KotlinType;

/* JADX INFO: loaded from: classes6.dex */
public class ExpressionTypingContext extends ResolutionContext<ExpressionTypingContext> {
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        Object[] objArr = new Object[3];
        switch (i) {
            case 1:
            case 7:
            case 13:
            case 21:
            case 32:
            case 42:
            case 54:
                objArr[0] = "scope";
                break;
            case 2:
            case 8:
            case 14:
            case 22:
            case 33:
            case 43:
            case 55:
                objArr[0] = "dataFlowInfo";
                break;
            case 3:
            case 9:
            case 15:
            case 23:
            case 34:
            case 44:
            case 56:
                objArr[0] = "expectedType";
                break;
            case 4:
            case 10:
            case 18:
            case 26:
            case 38:
            case 50:
            case 62:
                objArr[0] = "languageVersionSettings";
                break;
            case 5:
            case 11:
            case 19:
            case 27:
            case 39:
            case 51:
            case 63:
                objArr[0] = "dataFlowValueFactory";
                break;
            case 6:
            case 12:
            case 20:
            case 31:
            case 41:
            case 53:
            default:
                objArr[0] = "trace";
                break;
            case 16:
            case 24:
            case 35:
            case 45:
            case 57:
                objArr[0] = "contextDependency";
                break;
            case 17:
            case 25:
            case 37:
            case 47:
            case 59:
                objArr[0] = "statementFilter";
                break;
            case 28:
            case 40:
            case 52:
            case 64:
                objArr[0] = "inferenceSession";
                break;
            case 29:
            case 30:
                objArr[0] = "context";
                break;
            case 36:
            case 46:
            case 58:
                objArr[0] = "resolutionResultsCache";
                break;
            case 48:
            case 60:
                objArr[0] = "callPosition";
                break;
            case 49:
            case 61:
                objArr[0] = "expressionContextProvider";
                break;
        }
        objArr[1] = "org/jetbrains/kotlin/types/expressions/ExpressionTypingContext";
        switch (i) {
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
            case 51:
            case 52:
                objArr[2] = "<init>";
                break;
            case 53:
            case 54:
            case 55:
            case 56:
            case 57:
            case 58:
            case 59:
            case 60:
            case 61:
            case 62:
            case 63:
            case 64:
                objArr[2] = CoroutineCodegenUtilKt.SUSPEND_FUNCTION_CREATE_METHOD_NAME;
                break;
            default:
                objArr[2] = "newContext";
                break;
        }
        throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
    }

    @Override // org.jetbrains.kotlin.resolve.calls.context.ResolutionContext
    protected /* bridge */ /* synthetic */ ResolutionContext create(BindingTrace bindingTrace, LexicalScope lexicalScope, DataFlowInfo dataFlowInfo, KotlinType kotlinType, ContextDependency contextDependency, ResolutionResultsCache resolutionResultsCache, StatementFilter statementFilter, boolean z, CallPosition callPosition, Function1 function1, LanguageVersionSettings languageVersionSettings, DataFlowValueFactory dataFlowValueFactory, InferenceSession inferenceSession) {
        return create(bindingTrace, lexicalScope, dataFlowInfo, kotlinType, contextDependency, resolutionResultsCache, statementFilter, z, callPosition, (Function1<KtExpression, KtExpression>) function1, languageVersionSettings, dataFlowValueFactory, inferenceSession);
    }

    public static ExpressionTypingContext newContext(BindingTrace bindingTrace, LexicalScope lexicalScope, DataFlowInfo dataFlowInfo, KotlinType kotlinType, LanguageVersionSettings languageVersionSettings, DataFlowValueFactory dataFlowValueFactory) {
        if (bindingTrace == null) {
            $$$reportNull$$$0(0);
        }
        if (lexicalScope == null) {
            $$$reportNull$$$0(1);
        }
        if (dataFlowInfo == null) {
            $$$reportNull$$$0(2);
        }
        if (kotlinType == null) {
            $$$reportNull$$$0(3);
        }
        if (languageVersionSettings == null) {
            $$$reportNull$$$0(4);
        }
        if (dataFlowValueFactory == null) {
            $$$reportNull$$$0(5);
        }
        return newContext(bindingTrace, lexicalScope, dataFlowInfo, kotlinType, ContextDependency.INDEPENDENT, StatementFilter.NONE, languageVersionSettings, dataFlowValueFactory, InferenceSession.INSTANCE.getDefault());
    }

    public static ExpressionTypingContext newContext(BindingTrace bindingTrace, LexicalScope lexicalScope, DataFlowInfo dataFlowInfo, KotlinType kotlinType, LanguageVersionSettings languageVersionSettings, DataFlowValueFactory dataFlowValueFactory, InferenceSession inferenceSession) {
        if (bindingTrace == null) {
            $$$reportNull$$$0(6);
        }
        if (lexicalScope == null) {
            $$$reportNull$$$0(7);
        }
        if (dataFlowInfo == null) {
            $$$reportNull$$$0(8);
        }
        if (kotlinType == null) {
            $$$reportNull$$$0(9);
        }
        if (languageVersionSettings == null) {
            $$$reportNull$$$0(10);
        }
        if (dataFlowValueFactory == null) {
            $$$reportNull$$$0(11);
        }
        return newContext(bindingTrace, lexicalScope, dataFlowInfo, kotlinType, ContextDependency.INDEPENDENT, StatementFilter.NONE, languageVersionSettings, dataFlowValueFactory, inferenceSession != null ? inferenceSession : InferenceSession.INSTANCE.getDefault());
    }

    public static ExpressionTypingContext newContext(BindingTrace bindingTrace, LexicalScope lexicalScope, DataFlowInfo dataFlowInfo, KotlinType kotlinType, ContextDependency contextDependency, StatementFilter statementFilter, LanguageVersionSettings languageVersionSettings, DataFlowValueFactory dataFlowValueFactory) {
        if (bindingTrace == null) {
            $$$reportNull$$$0(12);
        }
        if (lexicalScope == null) {
            $$$reportNull$$$0(13);
        }
        if (dataFlowInfo == null) {
            $$$reportNull$$$0(14);
        }
        if (kotlinType == null) {
            $$$reportNull$$$0(15);
        }
        if (contextDependency == null) {
            $$$reportNull$$$0(16);
        }
        if (statementFilter == null) {
            $$$reportNull$$$0(17);
        }
        if (languageVersionSettings == null) {
            $$$reportNull$$$0(18);
        }
        if (dataFlowValueFactory == null) {
            $$$reportNull$$$0(19);
        }
        return newContext(bindingTrace, lexicalScope, dataFlowInfo, kotlinType, contextDependency, new ResolutionResultsCacheImpl(), statementFilter, false, languageVersionSettings, dataFlowValueFactory, InferenceSession.INSTANCE.getDefault());
    }

    public static ExpressionTypingContext newContext(BindingTrace bindingTrace, LexicalScope lexicalScope, DataFlowInfo dataFlowInfo, KotlinType kotlinType, ContextDependency contextDependency, StatementFilter statementFilter, LanguageVersionSettings languageVersionSettings, DataFlowValueFactory dataFlowValueFactory, InferenceSession inferenceSession) {
        if (bindingTrace == null) {
            $$$reportNull$$$0(20);
        }
        if (lexicalScope == null) {
            $$$reportNull$$$0(21);
        }
        if (dataFlowInfo == null) {
            $$$reportNull$$$0(22);
        }
        if (kotlinType == null) {
            $$$reportNull$$$0(23);
        }
        if (contextDependency == null) {
            $$$reportNull$$$0(24);
        }
        if (statementFilter == null) {
            $$$reportNull$$$0(25);
        }
        if (languageVersionSettings == null) {
            $$$reportNull$$$0(26);
        }
        if (dataFlowValueFactory == null) {
            $$$reportNull$$$0(27);
        }
        if (inferenceSession == null) {
            $$$reportNull$$$0(28);
        }
        return newContext(bindingTrace, lexicalScope, dataFlowInfo, kotlinType, contextDependency, new ResolutionResultsCacheImpl(), statementFilter, false, languageVersionSettings, dataFlowValueFactory, inferenceSession);
    }

    public static ExpressionTypingContext newContext(ResolutionContext<?> resolutionContext) {
        if (resolutionContext == null) {
            $$$reportNull$$$0(29);
        }
        return new ExpressionTypingContext(resolutionContext.trace, resolutionContext.scope, resolutionContext.dataFlowInfo, resolutionContext.expectedType, resolutionContext.contextDependency, resolutionContext.resolutionResultsCache, resolutionContext.statementFilter, resolutionContext.isAnnotationContext, resolutionContext.isDebuggerContext, resolutionContext.collectAllCandidates, resolutionContext.callPosition, resolutionContext.expressionContextProvider, resolutionContext.languageVersionSettings, resolutionContext.dataFlowValueFactory, resolutionContext.inferenceSession);
    }

    public static ExpressionTypingContext newContext(ResolutionContext<?> resolutionContext, boolean z) {
        if (resolutionContext == null) {
            $$$reportNull$$$0(30);
        }
        return new ExpressionTypingContext(resolutionContext.trace, resolutionContext.scope, resolutionContext.dataFlowInfo, resolutionContext.expectedType, resolutionContext.contextDependency, resolutionContext.resolutionResultsCache, resolutionContext.statementFilter, resolutionContext.isAnnotationContext, z, resolutionContext.collectAllCandidates, resolutionContext.callPosition, resolutionContext.expressionContextProvider, resolutionContext.languageVersionSettings, resolutionContext.dataFlowValueFactory, resolutionContext.inferenceSession);
    }

    public static ExpressionTypingContext newContext(BindingTrace bindingTrace, LexicalScope lexicalScope, DataFlowInfo dataFlowInfo, KotlinType kotlinType, ContextDependency contextDependency, ResolutionResultsCache resolutionResultsCache, StatementFilter statementFilter, boolean z, LanguageVersionSettings languageVersionSettings, DataFlowValueFactory dataFlowValueFactory, InferenceSession inferenceSession) {
        if (bindingTrace == null) {
            $$$reportNull$$$0(31);
        }
        if (lexicalScope == null) {
            $$$reportNull$$$0(32);
        }
        if (dataFlowInfo == null) {
            $$$reportNull$$$0(33);
        }
        if (kotlinType == null) {
            $$$reportNull$$$0(34);
        }
        if (contextDependency == null) {
            $$$reportNull$$$0(35);
        }
        if (resolutionResultsCache == null) {
            $$$reportNull$$$0(36);
        }
        if (statementFilter == null) {
            $$$reportNull$$$0(37);
        }
        if (languageVersionSettings == null) {
            $$$reportNull$$$0(38);
        }
        if (dataFlowValueFactory == null) {
            $$$reportNull$$$0(39);
        }
        if (inferenceSession == null) {
            $$$reportNull$$$0(40);
        }
        return new ExpressionTypingContext(bindingTrace, lexicalScope, dataFlowInfo, kotlinType, contextDependency, resolutionResultsCache, statementFilter, z, false, false, CallPosition.Unknown.INSTANCE, DEFAULT_EXPRESSION_CONTEXT_PROVIDER, languageVersionSettings, dataFlowValueFactory, inferenceSession);
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    private ExpressionTypingContext(BindingTrace bindingTrace, LexicalScope lexicalScope, DataFlowInfo dataFlowInfo, KotlinType kotlinType, ContextDependency contextDependency, ResolutionResultsCache resolutionResultsCache, StatementFilter statementFilter, boolean z, boolean z2, boolean z3, CallPosition callPosition, Function1<KtExpression, KtExpression> function1, LanguageVersionSettings languageVersionSettings, DataFlowValueFactory dataFlowValueFactory, InferenceSession inferenceSession) {
        super(bindingTrace, lexicalScope, kotlinType, dataFlowInfo, contextDependency, resolutionResultsCache, statementFilter, z, z2, z3, callPosition, function1, languageVersionSettings, dataFlowValueFactory, inferenceSession);
        if (bindingTrace == null) {
            $$$reportNull$$$0(41);
        }
        if (lexicalScope == null) {
            $$$reportNull$$$0(42);
        }
        if (dataFlowInfo == null) {
            $$$reportNull$$$0(43);
        }
        if (kotlinType == null) {
            $$$reportNull$$$0(44);
        }
        if (contextDependency == null) {
            $$$reportNull$$$0(45);
        }
        if (resolutionResultsCache == null) {
            $$$reportNull$$$0(46);
        }
        if (statementFilter == null) {
            $$$reportNull$$$0(47);
        }
        if (callPosition == null) {
            $$$reportNull$$$0(48);
        }
        if (function1 == null) {
            $$$reportNull$$$0(49);
        }
        if (languageVersionSettings == null) {
            $$$reportNull$$$0(50);
        }
        if (dataFlowValueFactory == null) {
            $$$reportNull$$$0(51);
        }
        if (inferenceSession == null) {
            $$$reportNull$$$0(52);
        }
    }

    @Override // org.jetbrains.kotlin.resolve.calls.context.ResolutionContext
    protected ExpressionTypingContext create(BindingTrace bindingTrace, LexicalScope lexicalScope, DataFlowInfo dataFlowInfo, KotlinType kotlinType, ContextDependency contextDependency, ResolutionResultsCache resolutionResultsCache, StatementFilter statementFilter, boolean z, CallPosition callPosition, Function1<KtExpression, KtExpression> function1, LanguageVersionSettings languageVersionSettings, DataFlowValueFactory dataFlowValueFactory, InferenceSession inferenceSession) {
        if (bindingTrace == null) {
            $$$reportNull$$$0(53);
        }
        if (lexicalScope == null) {
            $$$reportNull$$$0(54);
        }
        if (dataFlowInfo == null) {
            $$$reportNull$$$0(55);
        }
        if (kotlinType == null) {
            $$$reportNull$$$0(56);
        }
        if (contextDependency == null) {
            $$$reportNull$$$0(57);
        }
        if (resolutionResultsCache == null) {
            $$$reportNull$$$0(58);
        }
        if (statementFilter == null) {
            $$$reportNull$$$0(59);
        }
        if (callPosition == null) {
            $$$reportNull$$$0(60);
        }
        if (function1 == null) {
            $$$reportNull$$$0(61);
        }
        if (languageVersionSettings == null) {
            $$$reportNull$$$0(62);
        }
        if (dataFlowValueFactory == null) {
            $$$reportNull$$$0(63);
        }
        if (inferenceSession == null) {
            $$$reportNull$$$0(64);
        }
        return new ExpressionTypingContext(bindingTrace, lexicalScope, dataFlowInfo, kotlinType, contextDependency, resolutionResultsCache, statementFilter, this.isAnnotationContext, this.isDebuggerContext, z, callPosition, function1, languageVersionSettings, dataFlowValueFactory, inferenceSession);
    }
}
