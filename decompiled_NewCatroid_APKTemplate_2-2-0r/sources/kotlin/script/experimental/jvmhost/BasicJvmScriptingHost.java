package kotlin.script.experimental.jvmhost;

import androidx.exifinterface.media.ExifInterface;
import java.lang.reflect.InvocationTargetException;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Reflection;
import kotlin.script.experimental.api.EvaluationResult;
import kotlin.script.experimental.api.KotlinType;
import kotlin.script.experimental.api.ResultWithDiagnostics;
import kotlin.script.experimental.api.ScriptCompilationConfiguration;
import kotlin.script.experimental.api.ScriptEvaluationConfiguration;
import kotlin.script.experimental.api.ScriptEvaluator;
import kotlin.script.experimental.api.SourceCode;
import kotlin.script.experimental.host.BasicScriptingHost;
import kotlin.script.experimental.host.ConfigurationFromTemplateKt;
import kotlin.script.experimental.host.HostConfigurationKt;
import kotlin.script.experimental.host.ScriptDefinition;
import kotlin.script.experimental.host.ScriptingHostConfiguration;
import kotlin.script.experimental.jvm.JvmScriptingHostConfigurationKt;

/* JADX INFO: compiled from: BasicJvmScriptingHost.kt */
/* JADX INFO: loaded from: classes12.dex */
@Metadata(d1 = {"\u0000L\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0016\u0018\u00002\u00020\u0001B%\u0012\n\b\u0002\u0010\u0002\u001a\u0004\u0018\u00010\u0003\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0005\u0012\b\b\u0002\u0010\u0006\u001a\u00020\u0007¢\u0006\u0002\u0010\bJ\\\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\r0\f\"\n\b\u0000\u0010\u000e\u0018\u0001*\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u00112\u0019\b\n\u0010\u0012\u001a\u0013\u0012\u0004\u0012\u00020\u0014\u0012\u0004\u0012\u00020\u00150\u0013¢\u0006\u0002\b\u00162\u0019\b\n\u0010\u0017\u001a\u0013\u0012\u0004\u0012\u00020\u0018\u0012\u0004\u0012\u00020\u00150\u0013¢\u0006\u0002\b\u0016H\u0086\bø\u0001\u0000R\u0013\u0010\u0002\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\n\u0082\u0002\u0007\n\u0005\b\u009920\u0001¨\u0006\u0019"}, d2 = {"Lkotlin/script/experimental/jvmhost/BasicJvmScriptingHost;", "Lkotlin/script/experimental/host/BasicScriptingHost;", "baseHostConfiguration", "Lkotlin/script/experimental/host/ScriptingHostConfiguration;", "compiler", "Lkotlin/script/experimental/jvmhost/JvmScriptCompiler;", "evaluator", "Lkotlin/script/experimental/api/ScriptEvaluator;", "(Lkotlin/script/experimental/host/ScriptingHostConfiguration;Lkotlin/script/experimental/jvmhost/JvmScriptCompiler;Lkotlin/script/experimental/api/ScriptEvaluator;)V", "getBaseHostConfiguration", "()Lkotlin/script/experimental/host/ScriptingHostConfiguration;", "evalWithTemplate", "Lkotlin/script/experimental/api/ResultWithDiagnostics;", "Lkotlin/script/experimental/api/EvaluationResult;", ExifInterface.GPS_DIRECTION_TRUE, "", "script", "Lkotlin/script/experimental/api/SourceCode;", "compilation", "Lkotlin/Function1;", "Lkotlin/script/experimental/api/ScriptCompilationConfiguration$Builder;", "", "Lkotlin/ExtensionFunctionType;", "evaluation", "Lkotlin/script/experimental/api/ScriptEvaluationConfiguration$Builder;", "kotlin-scripting-jvm-host-unshaded"}, k = 1, mv = {1, 9, 0}, xi = 48)
public class BasicJvmScriptingHost extends BasicScriptingHost {
    private final ScriptingHostConfiguration baseHostConfiguration;

    public BasicJvmScriptingHost() {
        this(null, null, null, 7, null);
    }

    public final ScriptingHostConfiguration getBaseHostConfiguration() {
        return this.baseHostConfiguration;
    }

    /* JADX WARN: Illegal instructions before constructor call */
    public /* synthetic */ BasicJvmScriptingHost(ScriptingHostConfiguration scriptingHostConfiguration, JvmScriptCompiler jvmScriptCompiler, kotlin.script.experimental.jvm.BasicJvmScriptEvaluator basicJvmScriptEvaluator, int i, DefaultConstructorMarker defaultConstructorMarker) {
        scriptingHostConfiguration = (i & 1) != 0 ? null : scriptingHostConfiguration;
        this(scriptingHostConfiguration, (i & 2) != 0 ? new JvmScriptCompiler(HostConfigurationKt.withDefaultsFrom(scriptingHostConfiguration, JvmScriptingHostConfigurationKt.getDefaultJvmScriptingHostConfiguration()), null, 2, null) : jvmScriptCompiler, (i & 4) != 0 ? new kotlin.script.experimental.jvm.BasicJvmScriptEvaluator() : basicJvmScriptEvaluator);
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public BasicJvmScriptingHost(ScriptingHostConfiguration scriptingHostConfiguration, JvmScriptCompiler compiler, ScriptEvaluator evaluator) {
        super(compiler, evaluator);
        Intrinsics.checkNotNullParameter(compiler, "compiler");
        Intrinsics.checkNotNullParameter(evaluator, "evaluator");
        this.baseHostConfiguration = scriptingHostConfiguration;
    }

    public static /* synthetic */ ResultWithDiagnostics evalWithTemplate$default(BasicJvmScriptingHost basicJvmScriptingHost, SourceCode script, Function1 compilation, Function1 evaluation, int i, Object obj) throws IllegalAccessException, InstantiationException, InvocationTargetException {
        if (obj == null) {
            if ((i & 2) != 0) {
                compilation = new Function1<ScriptCompilationConfiguration.Builder, Unit>() { // from class: kotlin.script.experimental.jvmhost.BasicJvmScriptingHost.evalWithTemplate.1
                    /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
                    public final void invoke2(ScriptCompilationConfiguration.Builder builder) {
                        Intrinsics.checkNotNullParameter(builder, "$this$null");
                    }

                    @Override // kotlin.jvm.functions.Function1
                    public /* bridge */ /* synthetic */ Unit invoke(ScriptCompilationConfiguration.Builder builder) {
                        invoke2(builder);
                        return Unit.INSTANCE;
                    }
                };
            }
            if ((i & 4) != 0) {
                evaluation = new Function1<ScriptEvaluationConfiguration.Builder, Unit>() { // from class: kotlin.script.experimental.jvmhost.BasicJvmScriptingHost.evalWithTemplate.2
                    /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
                    public final void invoke2(ScriptEvaluationConfiguration.Builder builder) {
                        Intrinsics.checkNotNullParameter(builder, "$this$null");
                    }

                    @Override // kotlin.jvm.functions.Function1
                    public /* bridge */ /* synthetic */ Unit invoke(ScriptEvaluationConfiguration.Builder builder) {
                        invoke2(builder);
                        return Unit.INSTANCE;
                    }
                };
            }
            Intrinsics.checkNotNullParameter(script, "script");
            Intrinsics.checkNotNullParameter(compilation, "compilation");
            Intrinsics.checkNotNullParameter(evaluation, "evaluation");
            ScriptingHostConfiguration baseHostConfiguration = basicJvmScriptingHost.getBaseHostConfiguration();
            Intrinsics.reifiedOperationMarker(4, ExifInterface.GPS_DIRECTION_TRUE);
            ScriptDefinition scriptDefinitionCreateScriptDefinitionFromTemplate = ConfigurationFromTemplateKt.createScriptDefinitionFromTemplate(new KotlinType(Reflection.getOrCreateKotlinClass(Object.class), false, 2, (DefaultConstructorMarker) null), HostConfigurationKt.withDefaultsFrom(baseHostConfiguration, JvmScriptingHostConfigurationKt.getDefaultJvmScriptingHostConfiguration()), Reflection.getOrCreateKotlinClass(ScriptCompilationConfiguration.class), compilation, evaluation);
            return basicJvmScriptingHost.eval(script, scriptDefinitionCreateScriptDefinitionFromTemplate.getCompilationConfiguration(), scriptDefinitionCreateScriptDefinitionFromTemplate.getEvaluationConfiguration());
        }
        throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: evalWithTemplate");
    }

    public final /* synthetic */ <T> ResultWithDiagnostics<EvaluationResult> evalWithTemplate(SourceCode script, Function1<? super ScriptCompilationConfiguration.Builder, Unit> compilation, Function1<? super ScriptEvaluationConfiguration.Builder, Unit> evaluation) throws IllegalAccessException, InstantiationException, InvocationTargetException {
        Intrinsics.checkNotNullParameter(script, "script");
        Intrinsics.checkNotNullParameter(compilation, "compilation");
        Intrinsics.checkNotNullParameter(evaluation, "evaluation");
        ScriptingHostConfiguration baseHostConfiguration = getBaseHostConfiguration();
        Intrinsics.reifiedOperationMarker(4, ExifInterface.GPS_DIRECTION_TRUE);
        ScriptDefinition scriptDefinitionCreateScriptDefinitionFromTemplate = ConfigurationFromTemplateKt.createScriptDefinitionFromTemplate(new KotlinType(Reflection.getOrCreateKotlinClass(Object.class), false, 2, (DefaultConstructorMarker) null), HostConfigurationKt.withDefaultsFrom(baseHostConfiguration, JvmScriptingHostConfigurationKt.getDefaultJvmScriptingHostConfiguration()), Reflection.getOrCreateKotlinClass(ScriptCompilationConfiguration.class), compilation, evaluation);
        return eval(script, scriptDefinitionCreateScriptDefinitionFromTemplate.getCompilationConfiguration(), scriptDefinitionCreateScriptDefinitionFromTemplate.getEvaluationConfiguration());
    }
}
