package org.jetbrains.kotlin.cli.common.repl;

import java.io.Reader;
import java.util.Map;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import javax.script.AbstractScriptEngine;
import javax.script.Bindings;
import javax.script.Compilable;
import javax.script.CompiledScript;
import javax.script.ScriptContext;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineFactory;
import javax.script.ScriptException;
import javax.script.SimpleBindings;
import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.io.TextStreamsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import org.catrobat.catroid.web.ServerAuthenticationConstants;

/* JADX INFO: compiled from: KotlinJsr223JvmScriptEngineBase.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000\u0084\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b&\u0018\u00002\u00020\u00012\u00020\u00022\u00020\u0003:\u00010B\r\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\u0018\u0010\u0011\u001a\u0004\u0018\u00010\u00122\f\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\u00150\u0014H\u0002J\u0010\u0010\u0016\u001a\u00020\u00172\u0006\u0010\u0018\u001a\u00020\u0019H\u0016J\u0010\u0010\u0016\u001a\u00020\u00172\u0006\u0010\u0018\u001a\u00020\u001aH\u0016J\u0018\u0010\u0016\u001a\u00020\u00172\u0006\u0010\u0018\u001a\u00020\u001a2\u0006\u0010\u001b\u001a\u00020\u001cH\u0016J\u001a\u0010\u001d\u001a\u0004\u0018\u00010\u00122\u0006\u0010\u0018\u001a\u00020\u001a2\u0006\u0010\u001b\u001a\u00020\u001cH\u0016J\b\u0010\u001e\u001a\u00020\u001fH\u0016J\u0016\u0010 \u001a\u0006\u0012\u0002\b\u00030!2\b\b\u0002\u0010\"\u001a\u00020#H$J\u001a\u0010$\u001a\u0004\u0018\u00010\u00122\u0006\u0010\u0018\u001a\u00020\u00192\u0006\u0010\u001b\u001a\u00020\u001cH\u0016J\u001a\u0010$\u001a\u0004\u0018\u00010\u00122\u0006\u0010\u0018\u001a\u00020\u001a2\u0006\u0010\u001b\u001a\u00020\u001cH\u0016J\u001a\u0010$\u001a\u0004\u0018\u00010\u00122\u0006\u0010%\u001a\u00020&2\u0006\u0010\u001b\u001a\u00020\u001cH\u0016J\u0014\u0010'\u001a\u0006\u0012\u0002\b\u00030!2\u0006\u0010\u001b\u001a\u00020\u001cH\u0004J\b\u0010(\u001a\u00020\u0005H\u0016J\u0012\u0010)\u001a\u0004\u0018\u00010*2\u0006\u0010\u001b\u001a\u00020\u001cH\u0016J\u0016\u0010+\u001a\u00020,2\u0006\u0010\u001b\u001a\u00020\u001c2\u0006\u0010-\u001a\u00020\u001aJ\u0012\u0010.\u001a\u0004\u0018\u00010/2\u0006\u0010\u001b\u001a\u00020\u001cH\u0016R\u0014\u0010\u0004\u001a\u00020\u0005X\u0084\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0012\u0010\t\u001a\u00020\nX¤\u0004¢\u0006\u0006\u001a\u0004\b\u000b\u0010\fR\u0012\u0010\r\u001a\u00020\u000eX¤\u0004¢\u0006\u0006\u001a\u0004\b\u000f\u0010\u0010¨\u00061"}, d2 = {"Lorg/jetbrains/kotlin/cli/common/repl/KotlinJsr223JvmScriptEngineBase;", "Ljavax/script/AbstractScriptEngine;", "Ljavax/script/ScriptEngine;", "Ljavax/script/Compilable;", "myFactory", "Ljavax/script/ScriptEngineFactory;", "(Ljavax/script/ScriptEngineFactory;)V", "getMyFactory", "()Ljavax/script/ScriptEngineFactory;", "replCompiler", "Lorg/jetbrains/kotlin/cli/common/repl/ReplCompilerWithoutCheck;", "getReplCompiler", "()Lorg/jetbrains/kotlin/cli/common/repl/ReplCompilerWithoutCheck;", "replEvaluator", "Lorg/jetbrains/kotlin/cli/common/repl/ReplFullEvaluator;", "getReplEvaluator", "()Lorg/jetbrains/kotlin/cli/common/repl/ReplFullEvaluator;", "asJsr223EvalResult", "", "body", "Lkotlin/Function0;", "Lorg/jetbrains/kotlin/cli/common/repl/ReplEvalResult;", "compile", "Ljavax/script/CompiledScript;", "script", "Ljava/io/Reader;", "", "context", "Ljavax/script/ScriptContext;", "compileAndEval", "createBindings", "Ljavax/script/Bindings;", "createState", "Lorg/jetbrains/kotlin/cli/common/repl/IReplStageState;", "lock", "Ljava/util/concurrent/locks/ReentrantReadWriteLock;", "eval", "compiledScript", "Lorg/jetbrains/kotlin/cli/common/repl/KotlinJsr223JvmScriptEngineBase$CompiledKotlinScript;", "getCurrentState", "getFactory", "getInvokeWrapper", "Lorg/jetbrains/kotlin/cli/common/repl/InvokeWrapper;", "nextCodeLine", "Lorg/jetbrains/kotlin/cli/common/repl/ReplCodeLine;", ServerAuthenticationConstants.SIGNIN_GOOGLE_CODE_KEY, "overrideScriptArgs", "Lorg/jetbrains/kotlin/cli/common/repl/ScriptArgsWithTypes;", "CompiledKotlinScript", "cli-common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class KotlinJsr223JvmScriptEngineBase extends AbstractScriptEngine implements Compilable, ScriptEngine {
    private final ScriptEngineFactory myFactory;

    protected abstract IReplStageState<?> createState(ReentrantReadWriteLock lock);

    public InvokeWrapper getInvokeWrapper(ScriptContext context) {
        Intrinsics.checkNotNullParameter(context, "context");
        return null;
    }

    protected abstract ReplCompilerWithoutCheck getReplCompiler();

    protected abstract ReplFullEvaluator getReplEvaluator();

    public ScriptArgsWithTypes overrideScriptArgs(ScriptContext context) {
        Intrinsics.checkNotNullParameter(context, "context");
        return null;
    }

    public KotlinJsr223JvmScriptEngineBase(ScriptEngineFactory myFactory) {
        Intrinsics.checkNotNullParameter(myFactory, "myFactory");
        this.myFactory = myFactory;
    }

    protected final ScriptEngineFactory getMyFactory() {
        return this.myFactory;
    }

    public Object eval(String script, ScriptContext context) {
        Intrinsics.checkNotNullParameter(script, "script");
        Intrinsics.checkNotNullParameter(context, "context");
        return compileAndEval(script, context);
    }

    public Object eval(Reader script, ScriptContext context) {
        Intrinsics.checkNotNullParameter(script, "script");
        Intrinsics.checkNotNullParameter(context, "context");
        return compileAndEval(TextStreamsKt.readText(script), context);
    }

    public CompiledScript compile(String script) {
        Intrinsics.checkNotNullParameter(script, "script");
        ScriptContext context = getContext();
        Intrinsics.checkNotNullExpressionValue(context, "getContext(...)");
        return compile(script, context);
    }

    public CompiledScript compile(Reader script) {
        Intrinsics.checkNotNullParameter(script, "script");
        String text = TextStreamsKt.readText(script);
        ScriptContext context = getContext();
        Intrinsics.checkNotNullExpressionValue(context, "getContext(...)");
        return compile(text, context);
    }

    public Bindings createBindings() {
        Bindings simpleBindings = new SimpleBindings();
        simpleBindings.put(KotlinJsr223JvmScriptEngineBaseKt.KOTLIN_SCRIPT_ENGINE_BINDINGS_KEY, simpleBindings);
        return simpleBindings;
    }

    /* JADX INFO: renamed from: getFactory, reason: from getter */
    public ScriptEngineFactory getMyFactory() {
        return this.myFactory;
    }

    public final ReplCodeLine nextCodeLine(ScriptContext context, String code) {
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(code, "code");
        IReplStageState<?> currentState = getCurrentState(context);
        return new ReplCodeLine(currentState.getNextLineNo(), currentState.getCurrentGeneration(), code);
    }

    public static /* synthetic */ IReplStageState createState$default(KotlinJsr223JvmScriptEngineBase kotlinJsr223JvmScriptEngineBase, ReentrantReadWriteLock reentrantReadWriteLock, int i, Object obj) {
        if (obj != null) {
            throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: createState");
        }
        if ((i & 1) != 0) {
            reentrantReadWriteLock = new ReentrantReadWriteLock();
        }
        return kotlinJsr223JvmScriptEngineBase.createState(reentrantReadWriteLock);
    }

    protected final IReplStageState<?> getCurrentState(ScriptContext context) {
        Intrinsics.checkNotNullParameter(context, "context");
        Map bindings = context.getBindings(100);
        Intrinsics.checkNotNullExpressionValue(bindings, "getBindings(...)");
        Map map = bindings;
        Object objCreateState$default = map.get(KotlinJsr223JvmScriptEngineBaseKt.KOTLIN_SCRIPT_STATE_BINDINGS_KEY);
        if (objCreateState$default == null) {
            context.getBindings(100).put(KotlinJsr223JvmScriptEngineBaseKt.KOTLIN_SCRIPT_ENGINE_BINDINGS_KEY, this);
            objCreateState$default = createState$default(this, null, 1, null);
            map.put(KotlinJsr223JvmScriptEngineBaseKt.KOTLIN_SCRIPT_STATE_BINDINGS_KEY, objCreateState$default);
        }
        Intrinsics.checkNotNull(objCreateState$default, "null cannot be cast to non-null type org.jetbrains.kotlin.cli.common.repl.IReplStageState<*>");
        return (IReplStageState) objCreateState$default;
    }

    public Object compileAndEval(String script, final ScriptContext context) {
        Intrinsics.checkNotNullParameter(script, "script");
        Intrinsics.checkNotNullParameter(context, "context");
        final ReplCodeLine replCodeLineNextCodeLine = nextCodeLine(context, script);
        final IReplStageState<?> currentState = getCurrentState(context);
        return asJsr223EvalResult(new Function0<ReplEvalResult>() { // from class: org.jetbrains.kotlin.cli.common.repl.KotlinJsr223JvmScriptEngineBase.compileAndEval.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final ReplEvalResult invoke() {
                return KotlinJsr223JvmScriptEngineBase.this.getReplEvaluator().compileAndEval(currentState, replCodeLineNextCodeLine, KotlinJsr223JvmScriptEngineBase.this.overrideScriptArgs(context), KotlinJsr223JvmScriptEngineBase.this.getInvokeWrapper(context));
            }
        });
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: javax.script.ScriptException */
    public CompiledScript compile(String script, ScriptContext context) throws ScriptException {
        Intrinsics.checkNotNullParameter(script, "script");
        Intrinsics.checkNotNullParameter(context, "context");
        ReplCodeLine replCodeLineNextCodeLine = nextCodeLine(context, script);
        ReplCompileResult replCompileResultCompile = getReplCompiler().compile(getCurrentState(context), replCodeLineNextCodeLine);
        if (replCompileResultCompile instanceof ReplCompileResult.Error) {
            ReplCompileResult.Error error = (ReplCompileResult.Error) replCompileResultCompile;
            throw new ScriptException("Error" + KotlinJsr223JvmScriptEngineBaseKt.locationString(error) + ": " + error.getMessage());
        }
        if (replCompileResultCompile instanceof ReplCompileResult.Incomplete) {
            throw new ScriptException("Error: incomplete code; " + ((ReplCompileResult.Incomplete) replCompileResultCompile).getMessage());
        }
        if (!(replCompileResultCompile instanceof ReplCompileResult.CompiledClasses)) {
            throw new NoWhenBranchMatchedException();
        }
        return new CompiledKotlinScript(this, replCodeLineNextCodeLine, (ReplCompileResult.CompiledClasses) replCompileResultCompile);
    }

    public Object eval(final CompiledKotlinScript compiledScript, final ScriptContext context) {
        Intrinsics.checkNotNullParameter(compiledScript, "compiledScript");
        Intrinsics.checkNotNullParameter(context, "context");
        final IReplStageState<?> currentState = getCurrentState(context);
        return asJsr223EvalResult(new Function0<ReplEvalResult>() { // from class: org.jetbrains.kotlin.cli.common.repl.KotlinJsr223JvmScriptEngineBase.eval.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final ReplEvalResult invoke() {
                return KotlinJsr223JvmScriptEngineBase.this.getReplEvaluator().eval(currentState, compiledScript.getCompiledData(), KotlinJsr223JvmScriptEngineBase.this.overrideScriptArgs(context), KotlinJsr223JvmScriptEngineBase.this.getInvokeWrapper(context));
            }
        });
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: javax.script.ScriptException */
    private final Object asJsr223EvalResult(Function0<? extends ReplEvalResult> body) throws ScriptException {
        try {
            ReplEvalResult replEvalResultInvoke = body.invoke();
            if (replEvalResultInvoke instanceof ReplEvalResult.ValueResult) {
                return ((ReplEvalResult.ValueResult) replEvalResultInvoke).getValue();
            }
            if (replEvalResultInvoke instanceof ReplEvalResult.UnitResult) {
                return null;
            }
            if (replEvalResultInvoke instanceof ReplEvalResult.Error) {
                if (replEvalResultInvoke instanceof ReplEvalResult.Error.Runtime) {
                    ReplEvalResult.Error.Runtime runtime = (ReplEvalResult.Error.Runtime) replEvalResultInvoke;
                    if (runtime.getCause() != null) {
                        Throwable cause = runtime.getCause();
                        RuntimeException runtimeException = cause instanceof Exception ? (Exception) cause : null;
                        if (runtimeException == null) {
                            runtimeException = new RuntimeException(runtime.getCause());
                        }
                        throw new ScriptException(runtimeException);
                    }
                }
                if (replEvalResultInvoke instanceof ReplEvalResult.Error.CompileTime) {
                    ReplEvalResult.Error.CompileTime compileTime = (ReplEvalResult.Error.CompileTime) replEvalResultInvoke;
                    if (compileTime.getLocation() != null) {
                        throw new ScriptException(((ReplEvalResult.Error) replEvalResultInvoke).getMessage(), compileTime.getLocation().getPath(), compileTime.getLocation().getLine(), compileTime.getLocation().getColumn());
                    }
                }
                throw new ScriptException(((ReplEvalResult.Error) replEvalResultInvoke).getMessage());
            }
            if (replEvalResultInvoke instanceof ReplEvalResult.Incomplete) {
                throw new ScriptException("Error: incomplete code. " + ((ReplEvalResult.Incomplete) replEvalResultInvoke).getMessage());
            }
            if (replEvalResultInvoke instanceof ReplEvalResult.HistoryMismatch) {
                throw new ScriptException("Repl history mismatch at line: " + ((ReplEvalResult.HistoryMismatch) replEvalResultInvoke).getLineNo());
            }
            throw new NoWhenBranchMatchedException();
        } catch (Exception e) {
            throw new ScriptException(e);
        }
    }

    /* JADX INFO: compiled from: KotlinJsr223JvmScriptEngineBase.kt */
    @Metadata(d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u001d\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007¢\u0006\u0002\u0010\bJ\u0012\u0010\u000f\u001a\u0004\u0018\u00010\u00102\u0006\u0010\u0011\u001a\u00020\u0012H\u0016J\b\u0010\r\u001a\u00020\u0013H\u0016R\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u0011\u0010\u0006\u001a\u00020\u0007¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000e¨\u0006\u0014"}, d2 = {"Lorg/jetbrains/kotlin/cli/common/repl/KotlinJsr223JvmScriptEngineBase$CompiledKotlinScript;", "Ljavax/script/CompiledScript;", "engine", "Lorg/jetbrains/kotlin/cli/common/repl/KotlinJsr223JvmScriptEngineBase;", "codeLine", "Lorg/jetbrains/kotlin/cli/common/repl/ReplCodeLine;", "compiledData", "Lorg/jetbrains/kotlin/cli/common/repl/ReplCompileResult$CompiledClasses;", "(Lorg/jetbrains/kotlin/cli/common/repl/KotlinJsr223JvmScriptEngineBase;Lorg/jetbrains/kotlin/cli/common/repl/ReplCodeLine;Lorg/jetbrains/kotlin/cli/common/repl/ReplCompileResult$CompiledClasses;)V", "getCodeLine", "()Lorg/jetbrains/kotlin/cli/common/repl/ReplCodeLine;", "getCompiledData", "()Lorg/jetbrains/kotlin/cli/common/repl/ReplCompileResult$CompiledClasses;", "getEngine", "()Lorg/jetbrains/kotlin/cli/common/repl/KotlinJsr223JvmScriptEngineBase;", "eval", "", "context", "Ljavax/script/ScriptContext;", "Ljavax/script/ScriptEngine;", "cli-common"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class CompiledKotlinScript extends CompiledScript {
        private final ReplCodeLine codeLine;
        private final ReplCompileResult.CompiledClasses compiledData;
        private final KotlinJsr223JvmScriptEngineBase engine;

        public CompiledKotlinScript(KotlinJsr223JvmScriptEngineBase engine, ReplCodeLine codeLine, ReplCompileResult.CompiledClasses compiledData) {
            Intrinsics.checkNotNullParameter(engine, "engine");
            Intrinsics.checkNotNullParameter(codeLine, "codeLine");
            Intrinsics.checkNotNullParameter(compiledData, "compiledData");
            this.engine = engine;
            this.codeLine = codeLine;
            this.compiledData = compiledData;
        }

        public final ReplCodeLine getCodeLine() {
            return this.codeLine;
        }

        public final ReplCompileResult.CompiledClasses getCompiledData() {
            return this.compiledData;
        }

        public final KotlinJsr223JvmScriptEngineBase getEngine() {
            return this.engine;
        }

        public Object eval(ScriptContext context) {
            Intrinsics.checkNotNullParameter(context, "context");
            return this.engine.eval(this, context);
        }

        public ScriptEngine getEngine() {
            return this.engine;
        }
    }
}
