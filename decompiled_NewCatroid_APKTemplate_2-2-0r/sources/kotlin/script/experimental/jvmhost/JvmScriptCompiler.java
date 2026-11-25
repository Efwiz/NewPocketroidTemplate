package kotlin.script.experimental.jvmhost;

import kotlin.Metadata;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.script.experimental.api.CompiledScript;
import kotlin.script.experimental.api.ResultWithDiagnostics;
import kotlin.script.experimental.api.ScriptCompilationConfiguration;
import kotlin.script.experimental.api.ScriptCompilationKt;
import kotlin.script.experimental.api.ScriptCompiler;
import kotlin.script.experimental.api.SourceCode;
import kotlin.script.experimental.host.HostConfigurationKt;
import kotlin.script.experimental.host.ScriptingHostConfiguration;
import kotlin.script.experimental.jvm.JvmScriptingHostConfigurationKt;
import kotlin.script.experimental.util.PropertiesCollection;
import org.jetbrains.kotlin.scripting.compiler.plugin.ScriptCompilerProxy;
import org.jetbrains.kotlin.scripting.compiler.plugin.impl.ScriptJvmCompilerIsolated;

/* JADX INFO: compiled from: jvmScriptCompilation.kt */
/* JADX INFO: loaded from: classes12.dex */
@Metadata(d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0016\u0018\u00002\u00020\u0001B\u001b\u0012\b\b\u0002\u0010\u0002\u001a\u00020\u0003\u0012\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u0005¢\u0006\u0002\u0010\u0006J$\u0010\f\u001a\b\u0012\u0004\u0012\u00020\u000e0\r2\u0006\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u0012H\u0096B¢\u0006\u0002\u0010\u0013R\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0011\u0010\t\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000b¨\u0006\u0014"}, d2 = {"Lkotlin/script/experimental/jvmhost/JvmScriptCompiler;", "Lkotlin/script/experimental/api/ScriptCompiler;", "baseHostConfiguration", "Lkotlin/script/experimental/host/ScriptingHostConfiguration;", "compilerProxy", "Lorg/jetbrains/kotlin/scripting/compiler/plugin/ScriptCompilerProxy;", "(Lkotlin/script/experimental/host/ScriptingHostConfiguration;Lorg/jetbrains/kotlin/scripting/compiler/plugin/ScriptCompilerProxy;)V", "getCompilerProxy", "()Lorg/jetbrains/kotlin/scripting/compiler/plugin/ScriptCompilerProxy;", "hostConfiguration", "getHostConfiguration", "()Lkotlin/script/experimental/host/ScriptingHostConfiguration;", "invoke", "Lkotlin/script/experimental/api/ResultWithDiagnostics;", "Lkotlin/script/experimental/api/CompiledScript;", "script", "Lkotlin/script/experimental/api/SourceCode;", "scriptCompilationConfiguration", "Lkotlin/script/experimental/api/ScriptCompilationConfiguration;", "(Lkotlin/script/experimental/api/SourceCode;Lkotlin/script/experimental/api/ScriptCompilationConfiguration;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "kotlin-scripting-jvm-host-unshaded"}, k = 1, mv = {1, 9, 0}, xi = 48)
public class JvmScriptCompiler implements ScriptCompiler {
    private final ScriptCompilerProxy compilerProxy;
    private final ScriptingHostConfiguration hostConfiguration;

    /* JADX WARN: Multi-variable type inference failed */
    public JvmScriptCompiler() {
        this(null, 0 == true ? 1 : 0, 3, 0 == true ? 1 : 0);
    }

    @Override // kotlin.script.experimental.api.ScriptCompiler
    public Object invoke(SourceCode sourceCode, ScriptCompilationConfiguration scriptCompilationConfiguration, Continuation<? super ResultWithDiagnostics<? extends CompiledScript>> continuation) {
        return invoke$suspendImpl(this, sourceCode, scriptCompilationConfiguration, continuation);
    }

    public JvmScriptCompiler(ScriptingHostConfiguration baseHostConfiguration, ScriptJvmCompilerIsolated scriptJvmCompilerIsolated) {
        Intrinsics.checkNotNullParameter(baseHostConfiguration, "baseHostConfiguration");
        ScriptingHostConfiguration scriptingHostConfigurationWithDefaultsFrom = HostConfigurationKt.withDefaultsFrom(baseHostConfiguration, JvmScriptingHostConfigurationKt.getDefaultJvmScriptingHostConfiguration());
        this.hostConfiguration = scriptingHostConfigurationWithDefaultsFrom;
        this.compilerProxy = scriptJvmCompilerIsolated == null ? new ScriptJvmCompilerIsolated(scriptingHostConfigurationWithDefaultsFrom) : scriptJvmCompilerIsolated;
    }

    public /* synthetic */ JvmScriptCompiler(ScriptingHostConfiguration scriptingHostConfiguration, ScriptCompilerProxy scriptCompilerProxy, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this((i & 1) != 0 ? JvmScriptingHostConfigurationKt.getDefaultJvmScriptingHostConfiguration() : scriptingHostConfiguration, (i & 2) != 0 ? null : scriptCompilerProxy);
    }

    public final ScriptingHostConfiguration getHostConfiguration() {
        return this.hostConfiguration;
    }

    public final ScriptCompilerProxy getCompilerProxy() {
        return this.compilerProxy;
    }

    static /* synthetic */ Object invoke$suspendImpl(JvmScriptCompiler jvmScriptCompiler, SourceCode sourceCode, ScriptCompilationConfiguration scriptCompilationConfiguration, Continuation<? super ResultWithDiagnostics<? extends CompiledScript>> continuation) {
        return jvmScriptCompiler.compilerProxy.compile(sourceCode, ScriptCompilationKt.with(scriptCompilationConfiguration, new Function1<ScriptCompilationConfiguration.Builder, Unit>() { // from class: kotlin.script.experimental.jvmhost.JvmScriptCompiler.invoke.2
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(ScriptCompilationConfiguration.Builder builder) {
                invoke2(builder);
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(ScriptCompilationConfiguration.Builder with) {
                Intrinsics.checkNotNullParameter(with, "$this$with");
                PropertiesCollection.Key<ScriptingHostConfiguration> hostConfiguration = ScriptCompilationKt.getHostConfiguration(with);
                final JvmScriptCompiler jvmScriptCompiler2 = JvmScriptCompiler.this;
                with.update(hostConfiguration, new Function1<ScriptingHostConfiguration, ScriptingHostConfiguration>() { // from class: kotlin.script.experimental.jvmhost.JvmScriptCompiler.invoke.2.1
                    {
                        super(1);
                    }

                    @Override // kotlin.jvm.functions.Function1
                    public final ScriptingHostConfiguration invoke(ScriptingHostConfiguration scriptingHostConfiguration) {
                        return HostConfigurationKt.withDefaultsFrom(scriptingHostConfiguration, jvmScriptCompiler2.getHostConfiguration());
                    }
                });
            }
        }));
    }
}
