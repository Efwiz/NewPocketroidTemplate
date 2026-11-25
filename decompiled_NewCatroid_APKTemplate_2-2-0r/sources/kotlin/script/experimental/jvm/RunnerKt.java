package kotlin.script.experimental.jvm;

import java.io.PrintStream;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.JvmClassMappingKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.script.experimental.api.EvaluationResult;
import kotlin.script.experimental.api.KotlinType;
import kotlin.script.experimental.api.ResultWithDiagnostics;
import kotlin.script.experimental.api.ScriptCompilationConfiguration;
import kotlin.script.experimental.api.ScriptCompilationKt;
import kotlin.script.experimental.api.ScriptDiagnostic;
import kotlin.script.experimental.api.ScriptEvaluationConfiguration;
import kotlin.script.experimental.host.ConfigurationFromTemplateKt;
import kotlin.script.experimental.host.HostConfigurationKt;
import kotlin.script.experimental.host.ScriptingHostConfiguration;
import kotlin.script.experimental.impl.RunSuspendKt;
import kotlin.script.experimental.jvm.impl.KJvmCompiledScript;
import kotlin.script.experimental.jvm.impl.KJvmCompiledScriptKt;
import org.jetbrains.kotlin.codegen.coroutines.CoroutineCodegenUtilKt;
import org.jetbrains.kotlin.codegen.coroutines.CoroutineConstantsKt;
import org.jetbrains.kotlin.name.SpecialNames;
import org.jetbrains.kotlin.utils.PathUtil;

/* JADX INFO: compiled from: runner.kt */
/* JADX INFO: loaded from: classes12.dex */
@Metadata(d1 = {"\u0000\u001a\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0011\n\u0002\u0010\u000e\n\u0002\b\u0002\u001a+\u0010\u0000\u001a\u00020\u00012\n\u0010\u0002\u001a\u0006\u0012\u0002\b\u00030\u00032\u0012\u0010\u0004\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00060\u0005\"\u00020\u0006¢\u0006\u0002\u0010\u0007¨\u0006\b"}, d2 = {"runCompiledScript", "", "scriptClass", "Ljava/lang/Class;", "args", "", "", "(Ljava/lang/Class;[Ljava/lang/String;)V", PathUtil.KOTLIN_SCRIPTING_JVM_NAME}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class RunnerKt {
    public static final void runCompiledScript(Class<?> scriptClass, final String... args) {
        Intrinsics.checkNotNullParameter(scriptClass, "scriptClass");
        Intrinsics.checkNotNullParameter(args, "args");
        String name2 = scriptClass.getName();
        Intrinsics.checkNotNullExpressionValue(name2, "getName(...)");
        ClassLoader classLoader = scriptClass.getClassLoader();
        Intrinsics.checkNotNullExpressionValue(classLoader, "getClassLoader(...)");
        KJvmCompiledScript kJvmCompiledScriptCreateScriptFromClassLoader = KJvmCompiledScriptKt.createScriptFromClassLoader(name2, classLoader);
        BasicJvmScriptEvaluator basicJvmScriptEvaluator = new BasicJvmScriptEvaluator();
        Object obj = kJvmCompiledScriptCreateScriptFromClassLoader.getCompilationConfiguration().get(ScriptCompilationKt.getBaseClass(ScriptCompilationConfiguration.INSTANCE));
        Intrinsics.checkNotNull(obj);
        RunSuspendKt.internalScriptingRunSuspend(new AnonymousClass1(basicJvmScriptEvaluator, kJvmCompiledScriptCreateScriptFromClassLoader, ConfigurationFromTemplateKt.createEvaluationConfigurationFromTemplate((KotlinType) obj, HostConfigurationKt.withDefaultsFrom((ScriptingHostConfiguration) kJvmCompiledScriptCreateScriptFromClassLoader.getCompilationConfiguration().get(ScriptCompilationKt.getHostConfiguration(ScriptCompilationConfiguration.INSTANCE)), JvmScriptingHostConfigurationKt.getDefaultJvmScriptingHostConfiguration()), JvmClassMappingKt.getKotlinClass(scriptClass), new Function1<ScriptEvaluationConfiguration.Builder, Unit>() { // from class: kotlin.script.experimental.jvm.RunnerKt$runCompiledScript$evaluationConfiguration$1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(ScriptEvaluationConfiguration.Builder builder) {
                invoke2(builder);
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(ScriptEvaluationConfiguration.Builder createEvaluationConfigurationFromTemplate) {
                Intrinsics.checkNotNullParameter(createEvaluationConfigurationFromTemplate, "$this$createEvaluationConfigurationFromTemplate");
                JvmScriptEvaluationConfigurationBuilder jvm = JvmScriptEvaluationKt.getJvm(createEvaluationConfigurationFromTemplate);
                final String[] strArr = args;
                createEvaluationConfigurationFromTemplate.invoke(jvm, new Function1<JvmScriptEvaluationConfigurationBuilder, Unit>() { // from class: kotlin.script.experimental.jvm.RunnerKt$runCompiledScript$evaluationConfiguration$1.1
                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    {
                        super(1);
                    }

                    @Override // kotlin.jvm.functions.Function1
                    public /* bridge */ /* synthetic */ Unit invoke(JvmScriptEvaluationConfigurationBuilder jvmScriptEvaluationConfigurationBuilder) {
                        invoke2(jvmScriptEvaluationConfigurationBuilder);
                        return Unit.INSTANCE;
                    }

                    /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
                    public final void invoke2(JvmScriptEvaluationConfigurationBuilder invoke) {
                        Intrinsics.checkNotNullParameter(invoke, "$this$invoke");
                        invoke.invoke(JvmScriptEvaluationKt.getMainArguments(invoke), strArr);
                    }
                });
            }
        }), null));
    }

    /* JADX INFO: renamed from: kotlin.script.experimental.jvm.RunnerKt$runCompiledScript$1, reason: invalid class name */
    /* JADX INFO: compiled from: runner.kt */
    @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\b\u0012\u0004\u0012\u00020\u00020\u0001H\u008a@"}, d2 = {SpecialNames.ANONYMOUS_STRING, "Lkotlin/script/experimental/api/ResultWithDiagnostics;", "Lkotlin/script/experimental/api/EvaluationResult;"}, k = 3, mv = {1, 9, 0}, xi = 48)
    @DebugMetadata(c = "kotlin.script.experimental.jvm.RunnerKt$runCompiledScript$1", f = "runner.kt", i = {}, l = {34}, m = CoroutineCodegenUtilKt.INVOKE_SUSPEND_METHOD_NAME, n = {}, s = {})
    static final class AnonymousClass1 extends SuspendLambda implements Function1<Continuation<? super ResultWithDiagnostics<? extends EvaluationResult>>, Object> {
        final /* synthetic */ ScriptEvaluationConfiguration $evaluationConfiguration;
        final /* synthetic */ BasicJvmScriptEvaluator $evaluator;
        final /* synthetic */ KJvmCompiledScript $script;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        AnonymousClass1(BasicJvmScriptEvaluator basicJvmScriptEvaluator, KJvmCompiledScript kJvmCompiledScript, ScriptEvaluationConfiguration scriptEvaluationConfiguration, Continuation<? super AnonymousClass1> continuation) {
            super(1, continuation);
            this.$evaluator = basicJvmScriptEvaluator;
            this.$script = kJvmCompiledScript;
            this.$evaluationConfiguration = scriptEvaluationConfiguration;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation<Unit> create(Continuation<?> continuation) {
            return new AnonymousClass1(this.$evaluator, this.$script, this.$evaluationConfiguration, continuation);
        }

        @Override // kotlin.jvm.functions.Function1
        public /* bridge */ /* synthetic */ Object invoke(Continuation<? super ResultWithDiagnostics<? extends EvaluationResult>> continuation) {
            return invoke2((Continuation<? super ResultWithDiagnostics<EvaluationResult>>) continuation);
        }

        /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
        public final Object invoke2(Continuation<? super ResultWithDiagnostics<EvaluationResult>> continuation) {
            return ((AnonymousClass1) create(continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) throws Throwable {
            Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                this.label = 1;
                obj = this.$evaluator.invoke(this.$script, this.$evaluationConfiguration, this);
                if (obj == coroutine_suspended) {
                    return coroutine_suspended;
                }
            } else {
                if (i != 1) {
                    throw new IllegalStateException(CoroutineConstantsKt.ILLEGAL_STATE_ERROR_MESSAGE);
                }
                ResultKt.throwOnFailure(obj);
            }
            ResultWithDiagnostics resultWithDiagnostics = (ResultWithDiagnostics) obj;
            if (resultWithDiagnostics instanceof ResultWithDiagnostics.Failure) {
                List<ScriptDiagnostic> reports = resultWithDiagnostics.getReports();
                PrintStream err = System.err;
                Intrinsics.checkNotNullExpressionValue(err, "err");
                Iterator<T> it = reports.iterator();
                while (it.hasNext()) {
                    err.println(it.next());
                }
            }
            return resultWithDiagnostics;
        }
    }
}
