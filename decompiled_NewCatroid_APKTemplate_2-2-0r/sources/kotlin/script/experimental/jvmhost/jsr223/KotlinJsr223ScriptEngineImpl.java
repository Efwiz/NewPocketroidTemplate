package kotlin.script.experimental.jvmhost.jsr223;

import java.lang.ref.WeakReference;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import javax.script.Bindings;
import javax.script.ScriptContext;
import javax.script.ScriptEngineFactory;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.KClass;
import kotlin.script.experimental.api.ReplDataKt;
import kotlin.script.experimental.api.ReplScriptCompilationConfigurationBuilder;
import kotlin.script.experimental.api.ReplSnippetId;
import kotlin.script.experimental.api.ScriptCompilationConfiguration;
import kotlin.script.experimental.api.ScriptCompilationKt;
import kotlin.script.experimental.api.ScriptEvaluationConfiguration;
import kotlin.script.experimental.api.ScriptEvaluationKt;
import kotlin.script.experimental.host.HostConfigurationKt;
import kotlin.script.experimental.host.ScriptingHostConfiguration;
import kotlin.script.experimental.jvm.JvmScriptEvaluationKt;
import kotlin.script.experimental.jvm.JvmScriptingHostConfigurationKt;
import kotlin.script.experimental.jvmhost.repl.JvmReplCompiler;
import kotlin.script.experimental.jvmhost.repl.JvmReplEvaluator;
import kotlin.script.experimental.jvmhost.repl.JvmReplEvaluatorState;
import kotlin.script.experimental.util.PropertiesCollection;
import kotlin.sequences.Sequence;
import kotlin.sequences.SequencesKt;
import kotlin.text.CharsKt;
import org.jetbrains.kotlin.cli.common.repl.GenericReplCompilingEvaluatorBase;
import org.jetbrains.kotlin.cli.common.repl.IReplStageState;
import org.jetbrains.kotlin.cli.common.repl.InvokeWrapper;
import org.jetbrains.kotlin.cli.common.repl.KotlinJsr223JvmScriptEngineBase;
import org.jetbrains.kotlin.cli.common.repl.KotlinJsr223JvmScriptEngineBaseKt;
import org.jetbrains.kotlin.cli.common.repl.ReplCompilerWithoutCheck;
import org.jetbrains.kotlin.cli.common.repl.ReplFullEvaluator;
import org.jetbrains.kotlin.cli.common.repl.ReplHistoryRecord;
import org.jetbrains.kotlin.cli.common.repl.ScriptArgsWithTypes;

/* JADX INFO: compiled from: KotlinJsr223ScriptEngineImpl.kt */
/* JADX INFO: loaded from: classes12.dex */
@Metadata(d1 = {"\u0000\u008c\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u000e\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u00012\u00020\u0002BB\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\u0006\u0010\u0007\u001a\u00020\b\u0012#\u0010\t\u001a\u001f\u0012\u0013\u0012\u00110\u000b¢\u0006\f\b\f\u0012\b\b\r\u0012\u0004\b\b(\u000e\u0012\u0006\u0012\u0004\u0018\u00010\u000f0\n¢\u0006\u0002\u0010\u0010J\u001a\u0010@\u001a\u0004\u0018\u00010\u00132\u0006\u0010A\u001a\u00020B2\u0006\u0010\u000e\u001a\u00020\u000bH\u0016J\u0014\u0010C\u001a\u0006\u0012\u0002\b\u00030=2\u0006\u0010D\u001a\u00020EH\u0014J\u0012\u0010F\u001a\u0004\u0018\u00010\u000f2\u0006\u0010\u000e\u001a\u00020\u000bH\u0016R\u001a\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\u00130\u00128VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0014\u0010\u0015R\u0014\u0010\u0016\u001a\u00020\u00178VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0018\u0010\u0019R\u001b\u0010\u001a\u001a\u00020\u00068FX\u0086\u0084\u0002¢\u0006\f\n\u0004\b\u001d\u0010\u001e\u001a\u0004\b\u001b\u0010\u001cR\u001b\u0010\u001f\u001a\u00020\b8FX\u0086\u0084\u0002¢\u0006\f\n\u0004\b\"\u0010\u001e\u001a\u0004\b \u0010!R.\u0010\t\u001a\u001f\u0012\u0013\u0012\u00110\u000b¢\u0006\f\b\f\u0012\b\b\r\u0012\u0004\b\b(\u000e\u0012\u0006\u0012\u0004\u0018\u00010\u000f0\n¢\u0006\b\n\u0000\u001a\u0004\b#\u0010$R\u0016\u0010%\u001a\u0004\u0018\u00010&8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b'\u0010(R\u0011\u0010)\u001a\u00020*¢\u0006\b\n\u0000\u001a\u0004\b+\u0010,R\u0010\u0010-\u001a\u0004\u0018\u00010\u000bX\u0082\u000e¢\u0006\u0002\n\u0000R\u001b\u0010.\u001a\u00020/8BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b2\u0010\u001e\u001a\u0004\b0\u00101R\u001b\u00103\u001a\u0002048TX\u0094\u0084\u0002¢\u0006\f\n\u0004\b7\u0010\u001e\u001a\u0004\b5\u00106R\u0014\u00108\u001a\u0002098TX\u0094\u0004¢\u0006\u0006\u001a\u0004\b:\u0010;R\u0015\u0010<\u001a\u0006\u0012\u0002\b\u00030=8F¢\u0006\u0006\u001a\u0004\b>\u0010?¨\u0006G"}, d2 = {"Lkotlin/script/experimental/jvmhost/jsr223/KotlinJsr223ScriptEngineImpl;", "Lorg/jetbrains/kotlin/cli/common/repl/KotlinJsr223JvmScriptEngineBase;", "Lkotlin/script/experimental/jvmhost/jsr223/KotlinJsr223InvocableScriptEngine;", "factory", "Ljavax/script/ScriptEngineFactory;", "baseCompilationConfiguration", "Lkotlin/script/experimental/api/ScriptCompilationConfiguration;", "baseEvaluationConfiguration", "Lkotlin/script/experimental/api/ScriptEvaluationConfiguration;", "getScriptArgs", "Lkotlin/Function1;", "Ljavax/script/ScriptContext;", "Lkotlin/ParameterName;", "name", "context", "Lorg/jetbrains/kotlin/cli/common/repl/ScriptArgsWithTypes;", "(Ljavax/script/ScriptEngineFactory;Lkotlin/script/experimental/api/ScriptCompilationConfiguration;Lkotlin/script/experimental/api/ScriptEvaluationConfiguration;Lkotlin/jvm/functions/Function1;)V", "backwardInstancesHistory", "Lkotlin/sequences/Sequence;", "", "getBackwardInstancesHistory", "()Lkotlin/sequences/Sequence;", "baseClassLoader", "Ljava/lang/ClassLoader;", "getBaseClassLoader", "()Ljava/lang/ClassLoader;", "compilationConfiguration", "getCompilationConfiguration", "()Lkotlin/script/experimental/api/ScriptCompilationConfiguration;", "compilationConfiguration$delegate", "Lkotlin/Lazy;", "evaluationConfiguration", "getEvaluationConfiguration", "()Lkotlin/script/experimental/api/ScriptEvaluationConfiguration;", "evaluationConfiguration$delegate", "getGetScriptArgs", "()Lkotlin/jvm/functions/Function1;", "invokeWrapper", "Lorg/jetbrains/kotlin/cli/common/repl/InvokeWrapper;", "getInvokeWrapper", "()Lorg/jetbrains/kotlin/cli/common/repl/InvokeWrapper;", "jsr223HostConfiguration", "Lkotlin/script/experimental/host/ScriptingHostConfiguration;", "getJsr223HostConfiguration", "()Lkotlin/script/experimental/host/ScriptingHostConfiguration;", "lastScriptContext", "localEvaluator", "Lorg/jetbrains/kotlin/cli/common/repl/GenericReplCompilingEvaluatorBase;", "getLocalEvaluator", "()Lorg/jetbrains/kotlin/cli/common/repl/GenericReplCompilingEvaluatorBase;", "localEvaluator$delegate", "replCompiler", "Lorg/jetbrains/kotlin/cli/common/repl/ReplCompilerWithoutCheck;", "getReplCompiler", "()Lorg/jetbrains/kotlin/cli/common/repl/ReplCompilerWithoutCheck;", "replCompiler$delegate", "replEvaluator", "Lorg/jetbrains/kotlin/cli/common/repl/ReplFullEvaluator;", "getReplEvaluator", "()Lorg/jetbrains/kotlin/cli/common/repl/ReplFullEvaluator;", "state", "Lorg/jetbrains/kotlin/cli/common/repl/IReplStageState;", "getState", "()Lorg/jetbrains/kotlin/cli/common/repl/IReplStageState;", "compileAndEval", "script", "", "createState", "lock", "Ljava/util/concurrent/locks/ReentrantReadWriteLock;", "overrideScriptArgs", "kotlin-scripting-jvm-host-unshaded"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class KotlinJsr223ScriptEngineImpl extends KotlinJsr223JvmScriptEngineBase implements KotlinJsr223InvocableScriptEngine {

    /* JADX INFO: renamed from: compilationConfiguration$delegate, reason: from kotlin metadata */
    private final Lazy compilationConfiguration;

    /* JADX INFO: renamed from: evaluationConfiguration$delegate, reason: from kotlin metadata */
    private final Lazy evaluationConfiguration;
    private final Function1<ScriptContext, ScriptArgsWithTypes> getScriptArgs;
    private final ScriptingHostConfiguration jsr223HostConfiguration;
    private volatile ScriptContext lastScriptContext;

    /* JADX INFO: renamed from: localEvaluator$delegate, reason: from kotlin metadata */
    private final Lazy localEvaluator;

    /* JADX INFO: renamed from: replCompiler$delegate, reason: from kotlin metadata */
    private final Lazy replCompiler;

    @Override // kotlin.script.experimental.jvmhost.jsr223.KotlinJsr223InvocableScriptEngine
    public InvokeWrapper getInvokeWrapper() {
        return null;
    }

    public final Function1<ScriptContext, ScriptArgsWithTypes> getGetScriptArgs() {
        return this.getScriptArgs;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public KotlinJsr223ScriptEngineImpl(ScriptEngineFactory factory, final ScriptCompilationConfiguration baseCompilationConfiguration, final ScriptEvaluationConfiguration baseEvaluationConfiguration, Function1<? super ScriptContext, ScriptArgsWithTypes> getScriptArgs) {
        super(factory);
        Intrinsics.checkNotNullParameter(factory, "factory");
        Intrinsics.checkNotNullParameter(baseCompilationConfiguration, "baseCompilationConfiguration");
        Intrinsics.checkNotNullParameter(baseEvaluationConfiguration, "baseEvaluationConfiguration");
        Intrinsics.checkNotNullParameter(getScriptArgs, "getScriptArgs");
        this.getScriptArgs = getScriptArgs;
        this.jsr223HostConfiguration = new ScriptingHostConfiguration(new ScriptingHostConfiguration[]{JvmScriptingHostConfigurationKt.getDefaultJvmScriptingHostConfiguration()}, new Function1<ScriptingHostConfiguration.Builder, Unit>() { // from class: kotlin.script.experimental.jvmhost.jsr223.KotlinJsr223ScriptEngineImpl$jsr223HostConfiguration$1
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(ScriptingHostConfiguration.Builder builder) {
                invoke2(builder);
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(ScriptingHostConfiguration.Builder $receiver) {
                Intrinsics.checkNotNullParameter($receiver, "$this$$receiver");
                final WeakReference weakReference = new WeakReference(this.this$0);
                $receiver.invoke(KotlinJsr223HostConfigurationKt.getJsr223($receiver), new Function1<Jsr223HostConfigurationBuilder, Unit>() { // from class: kotlin.script.experimental.jvmhost.jsr223.KotlinJsr223ScriptEngineImpl$jsr223HostConfiguration$1.1
                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    {
                        super(1);
                    }

                    @Override // kotlin.jvm.functions.Function1
                    public /* bridge */ /* synthetic */ Unit invoke(Jsr223HostConfigurationBuilder jsr223HostConfigurationBuilder) {
                        invoke2(jsr223HostConfigurationBuilder);
                        return Unit.INSTANCE;
                    }

                    /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
                    public final void invoke2(Jsr223HostConfigurationBuilder invoke) {
                        Intrinsics.checkNotNullParameter(invoke, "$this$invoke");
                        PropertiesCollection.Key<Function0<ScriptContext>> getScriptContext = KotlinJsr223HostConfigurationKt.getGetScriptContext(invoke);
                        final WeakReference<KotlinJsr223ScriptEngineImpl> weakReference2 = weakReference;
                        invoke.invoke(getScriptContext, new Function0<ScriptContext>() { // from class: kotlin.script.experimental.jvmhost.jsr223.KotlinJsr223ScriptEngineImpl.jsr223HostConfiguration.1.1.1
                            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                            {
                                super(0);
                            }

                            @Override // kotlin.jvm.functions.Function0
                            public final ScriptContext invoke() {
                                KotlinJsr223ScriptEngineImpl kotlinJsr223ScriptEngineImpl = weakReference2.get();
                                if (kotlinJsr223ScriptEngineImpl == null) {
                                    return null;
                                }
                                ScriptContext scriptContext = kotlinJsr223ScriptEngineImpl.lastScriptContext;
                                return scriptContext == null ? kotlinJsr223ScriptEngineImpl.getContext() : scriptContext;
                            }
                        });
                    }
                });
            }
        });
        this.compilationConfiguration = LazyKt.lazy(new Function0<ScriptCompilationConfiguration>() { // from class: kotlin.script.experimental.jvmhost.jsr223.KotlinJsr223ScriptEngineImpl$compilationConfiguration$2
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final ScriptCompilationConfiguration invoke() {
                ScriptCompilationConfiguration[] scriptCompilationConfigurationArr = {baseCompilationConfiguration};
                final KotlinJsr223ScriptEngineImpl kotlinJsr223ScriptEngineImpl = this;
                return new ScriptCompilationConfiguration(scriptCompilationConfigurationArr, new Function1<ScriptCompilationConfiguration.Builder, Unit>() { // from class: kotlin.script.experimental.jvmhost.jsr223.KotlinJsr223ScriptEngineImpl$compilationConfiguration$2.1
                    {
                        super(1);
                    }

                    @Override // kotlin.jvm.functions.Function1
                    public /* bridge */ /* synthetic */ Unit invoke(ScriptCompilationConfiguration.Builder builder) {
                        invoke2(builder);
                        return Unit.INSTANCE;
                    }

                    /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
                    public final void invoke2(ScriptCompilationConfiguration.Builder $receiver) {
                        Intrinsics.checkNotNullParameter($receiver, "$this$$receiver");
                        ScriptCompilationConfiguration.Builder builder = $receiver;
                        PropertiesCollection.Key<ScriptingHostConfiguration> hostConfiguration = ScriptCompilationKt.getHostConfiguration(builder);
                        final KotlinJsr223ScriptEngineImpl kotlinJsr223ScriptEngineImpl2 = kotlinJsr223ScriptEngineImpl;
                        $receiver.update(hostConfiguration, new Function1<ScriptingHostConfiguration, ScriptingHostConfiguration>() { // from class: kotlin.script.experimental.jvmhost.jsr223.KotlinJsr223ScriptEngineImpl.compilationConfiguration.2.1.1
                            {
                                super(1);
                            }

                            @Override // kotlin.jvm.functions.Function1
                            public final ScriptingHostConfiguration invoke(ScriptingHostConfiguration scriptingHostConfiguration) {
                                return HostConfigurationKt.withDefaultsFrom(scriptingHostConfiguration, kotlinJsr223ScriptEngineImpl2.getJsr223HostConfiguration());
                            }
                        });
                        $receiver.invoke(ReplDataKt.getRepl(builder), new Function1<ReplScriptCompilationConfigurationBuilder, Unit>() { // from class: kotlin.script.experimental.jvmhost.jsr223.KotlinJsr223ScriptEngineImpl.compilationConfiguration.2.1.2
                            @Override // kotlin.jvm.functions.Function1
                            public /* bridge */ /* synthetic */ Unit invoke(ReplScriptCompilationConfigurationBuilder replScriptCompilationConfigurationBuilder) {
                                invoke2(replScriptCompilationConfigurationBuilder);
                                return Unit.INSTANCE;
                            }

                            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
                            public final void invoke2(ReplScriptCompilationConfigurationBuilder invoke) {
                                Intrinsics.checkNotNullParameter(invoke, "$this$invoke");
                                invoke.invoke((PropertiesCollection.Key<C02281>) ReplDataKt.getMakeSnippetIdentifier(invoke), new Function2<ScriptCompilationConfiguration, ReplSnippetId, String>() { // from class: kotlin.script.experimental.jvmhost.jsr223.KotlinJsr223ScriptEngineImpl.compilationConfiguration.2.1.2.1
                                    @Override // kotlin.jvm.functions.Function2
                                    public final String invoke(ScriptCompilationConfiguration configuration, ReplSnippetId snippetId) {
                                        Bindings bindings;
                                        Intrinsics.checkNotNullParameter(configuration, "configuration");
                                        Intrinsics.checkNotNullParameter(snippetId, "snippetId");
                                        Function0 function0 = (Function0) configuration.get(KotlinJsr223HostConfigurationKt.getGetScriptContext(KotlinJsr223HostConfigurationKt.getJsr223(ScriptCompilationConfiguration.INSTANCE)));
                                        Object obj = null;
                                        ScriptContext scriptContext = function0 != null ? (ScriptContext) function0.invoke() : null;
                                        if (scriptContext != null && (bindings = scriptContext.getBindings(100)) != null) {
                                            obj = bindings.get(KotlinJsr223JvmScriptEngineBaseKt.KOTLIN_SCRIPT_STATE_BINDINGS_KEY);
                                        }
                                        if (obj == null) {
                                            return ReplDataKt.makeDefaultSnippetIdentifier(snippetId);
                                        }
                                        StringBuilder sb = new StringBuilder("ScriptingHost");
                                        String string = Integer.toString(System.identityHashCode(obj), CharsKt.checkRadix(16));
                                        Intrinsics.checkNotNullExpressionValue(string, "toString(...)");
                                        return sb.append(string).append('_').append(ReplDataKt.makeDefaultSnippetIdentifier(snippetId)).toString();
                                    }
                                });
                            }
                        });
                    }
                });
            }
        });
        this.evaluationConfiguration = LazyKt.lazy(new Function0<ScriptEvaluationConfiguration>() { // from class: kotlin.script.experimental.jvmhost.jsr223.KotlinJsr223ScriptEngineImpl$evaluationConfiguration$2
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final ScriptEvaluationConfiguration invoke() {
                ScriptEvaluationConfiguration[] scriptEvaluationConfigurationArr = {baseEvaluationConfiguration};
                final KotlinJsr223ScriptEngineImpl kotlinJsr223ScriptEngineImpl = this;
                return new ScriptEvaluationConfiguration(scriptEvaluationConfigurationArr, new Function1<ScriptEvaluationConfiguration.Builder, Unit>() { // from class: kotlin.script.experimental.jvmhost.jsr223.KotlinJsr223ScriptEngineImpl$evaluationConfiguration$2.1
                    {
                        super(1);
                    }

                    @Override // kotlin.jvm.functions.Function1
                    public /* bridge */ /* synthetic */ Unit invoke(ScriptEvaluationConfiguration.Builder builder) {
                        invoke2(builder);
                        return Unit.INSTANCE;
                    }

                    /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
                    public final void invoke2(ScriptEvaluationConfiguration.Builder $receiver) {
                        Intrinsics.checkNotNullParameter($receiver, "$this$$receiver");
                        PropertiesCollection.Key<ScriptingHostConfiguration> hostConfiguration = ScriptEvaluationKt.getHostConfiguration($receiver);
                        final KotlinJsr223ScriptEngineImpl kotlinJsr223ScriptEngineImpl2 = kotlinJsr223ScriptEngineImpl;
                        $receiver.update(hostConfiguration, new Function1<ScriptingHostConfiguration, ScriptingHostConfiguration>() { // from class: kotlin.script.experimental.jvmhost.jsr223.KotlinJsr223ScriptEngineImpl.evaluationConfiguration.2.1.1
                            {
                                super(1);
                            }

                            @Override // kotlin.jvm.functions.Function1
                            public final ScriptingHostConfiguration invoke(ScriptingHostConfiguration scriptingHostConfiguration) {
                                return HostConfigurationKt.withDefaultsFrom(scriptingHostConfiguration, kotlinJsr223ScriptEngineImpl2.getJsr223HostConfiguration());
                            }
                        });
                    }
                });
            }
        });
        this.replCompiler = LazyKt.lazy(new Function0<JvmReplCompiler>() { // from class: kotlin.script.experimental.jvmhost.jsr223.KotlinJsr223ScriptEngineImpl$replCompiler$2
            {
                super(0);
            }

            /* JADX WARN: Multi-variable type inference failed */
            @Override // kotlin.jvm.functions.Function0
            public final JvmReplCompiler invoke() {
                return new JvmReplCompiler(this.this$0.getCompilationConfiguration(), null, 2, 0 == true ? 1 : 0);
            }
        });
        this.localEvaluator = LazyKt.lazy(new Function0<GenericReplCompilingEvaluatorBase>() { // from class: kotlin.script.experimental.jvmhost.jsr223.KotlinJsr223ScriptEngineImpl$localEvaluator$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final GenericReplCompilingEvaluatorBase invoke() {
                return new GenericReplCompilingEvaluatorBase(this.this$0.getReplCompiler(), new JvmReplEvaluator(this.this$0.getEvaluationConfiguration(), null, 2, null), null, 4, null);
            }
        });
    }

    public final ScriptingHostConfiguration getJsr223HostConfiguration() {
        return this.jsr223HostConfiguration;
    }

    public final ScriptCompilationConfiguration getCompilationConfiguration() {
        return (ScriptCompilationConfiguration) this.compilationConfiguration.getValue();
    }

    public final ScriptEvaluationConfiguration getEvaluationConfiguration() {
        return (ScriptEvaluationConfiguration) this.evaluationConfiguration.getValue();
    }

    @Override // org.jetbrains.kotlin.cli.common.repl.KotlinJsr223JvmScriptEngineBase
    protected ReplCompilerWithoutCheck getReplCompiler() {
        return (ReplCompilerWithoutCheck) this.replCompiler.getValue();
    }

    private final GenericReplCompilingEvaluatorBase getLocalEvaluator() {
        return (GenericReplCompilingEvaluatorBase) this.localEvaluator.getValue();
    }

    @Override // org.jetbrains.kotlin.cli.common.repl.KotlinJsr223JvmScriptEngineBase
    protected ReplFullEvaluator getReplEvaluator() {
        return getLocalEvaluator();
    }

    public final IReplStageState<?> getState() {
        ScriptContext context = getContext();
        Intrinsics.checkNotNullExpressionValue(context, "getContext(...)");
        return getCurrentState(context);
    }

    @Override // org.jetbrains.kotlin.cli.common.repl.KotlinJsr223JvmScriptEngineBase
    protected IReplStageState<?> createState(ReentrantReadWriteLock lock) {
        Intrinsics.checkNotNullParameter(lock, "lock");
        return getReplEvaluator().createState(lock);
    }

    @Override // org.jetbrains.kotlin.cli.common.repl.KotlinJsr223JvmScriptEngineBase
    public ScriptArgsWithTypes overrideScriptArgs(ScriptContext context) {
        Intrinsics.checkNotNullParameter(context, "context");
        return this.getScriptArgs.invoke(context);
    }

    @Override // kotlin.script.experimental.jvmhost.jsr223.KotlinJsr223InvocableScriptEngine
    public Sequence<Object> getBackwardInstancesHistory() {
        ScriptContext context = getContext();
        Intrinsics.checkNotNullExpressionValue(context, "getContext(...)");
        return SequencesKt.filterNotNull(SequencesKt.map(CollectionsKt.asSequence(CollectionsKt.asReversed(((JvmReplEvaluatorState) getCurrentState(context).asState(JvmReplEvaluatorState.class)).getHistory())), new Function1<ReplHistoryRecord<? extends Pair<? extends KClass<?>, ? extends Object>>, Object>() { // from class: kotlin.script.experimental.jvmhost.jsr223.KotlinJsr223ScriptEngineImpl$backwardInstancesHistory$1
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(ReplHistoryRecord<? extends Pair<? extends KClass<?>, ? extends Object>> it) {
                Intrinsics.checkNotNullParameter(it, "it");
                return it.getItem().getSecond();
            }
        }));
    }

    @Override // kotlin.script.experimental.jvmhost.jsr223.KotlinJsr223InvocableScriptEngine
    public ClassLoader getBaseClassLoader() {
        Object obj = getEvaluationConfiguration().get(JvmScriptEvaluationKt.getBaseClassLoader(JvmScriptEvaluationKt.getJvm(ScriptEvaluationConfiguration.INSTANCE)));
        Intrinsics.checkNotNull(obj);
        return (ClassLoader) obj;
    }

    @Override // org.jetbrains.kotlin.cli.common.repl.KotlinJsr223JvmScriptEngineBase
    public Object compileAndEval(String script, ScriptContext context) {
        Intrinsics.checkNotNullParameter(script, "script");
        Intrinsics.checkNotNullParameter(context, "context");
        this.lastScriptContext = context;
        try {
            return super.compileAndEval(script, context);
        } finally {
            this.lastScriptContext = null;
        }
    }
}
