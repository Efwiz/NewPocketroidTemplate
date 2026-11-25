package org.jetbrains.kotlin.scripting.definitions;

import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.script.experimental.api.ScriptEvaluationConfiguration;
import kotlin.script.experimental.api.ScriptEvaluationKt;
import kotlin.script.experimental.host.ScriptingHostConfiguration;
import org.jetbrains.kotlin.utils.PathUtil;

/* JADX INFO: compiled from: ScriptEvaluationConfigurationFromDefinition.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\n¨\u0006\u000b"}, d2 = {"Lorg/jetbrains/kotlin/scripting/definitions/ScriptEvaluationConfigurationFromDefinition;", "Lkotlin/script/experimental/api/ScriptEvaluationConfiguration;", "hostConfiguration", "Lkotlin/script/experimental/host/ScriptingHostConfiguration;", "scriptDefinition", "Lorg/jetbrains/kotlin/scripting/definitions/KotlinScriptDefinition;", "(Lkotlin/script/experimental/host/ScriptingHostConfiguration;Lorg/jetbrains/kotlin/scripting/definitions/KotlinScriptDefinition;)V", "getHostConfiguration", "()Lkotlin/script/experimental/host/ScriptingHostConfiguration;", "getScriptDefinition", "()Lorg/jetbrains/kotlin/scripting/definitions/KotlinScriptDefinition;", PathUtil.KOTLIN_SCRIPTING_COMPILER_IMPL_NAME}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ScriptEvaluationConfigurationFromDefinition extends ScriptEvaluationConfiguration {
    private final ScriptingHostConfiguration hostConfiguration;
    private final KotlinScriptDefinition scriptDefinition;

    public final ScriptingHostConfiguration getHostConfiguration() {
        return this.hostConfiguration;
    }

    public final KotlinScriptDefinition getScriptDefinition() {
        return this.scriptDefinition;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ScriptEvaluationConfigurationFromDefinition(final ScriptingHostConfiguration hostConfiguration, KotlinScriptDefinition scriptDefinition) {
        super(new Function1<ScriptEvaluationConfiguration.Builder, Unit>() { // from class: org.jetbrains.kotlin.scripting.definitions.ScriptEvaluationConfigurationFromDefinition.1
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(ScriptEvaluationConfiguration.Builder builder) {
                invoke2(builder);
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(ScriptEvaluationConfiguration.Builder builder) {
                Intrinsics.checkNotNullParameter(builder, "$this$null");
                builder.invoke(ScriptEvaluationKt.getHostConfiguration(builder), hostConfiguration);
            }
        });
        Intrinsics.checkNotNullParameter(hostConfiguration, "hostConfiguration");
        Intrinsics.checkNotNullParameter(scriptDefinition, "scriptDefinition");
        this.hostConfiguration = hostConfiguration;
        this.scriptDefinition = scriptDefinition;
    }
}
