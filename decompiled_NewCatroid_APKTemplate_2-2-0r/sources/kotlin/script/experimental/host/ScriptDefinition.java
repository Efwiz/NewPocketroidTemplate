package kotlin.script.experimental.host;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.script.experimental.api.ScriptCompilationConfiguration;
import kotlin.script.experimental.api.ScriptEvaluationConfiguration;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.utils.PathUtil;

/* JADX INFO: compiled from: configurationFromTemplate.kt */
/* JADX INFO: loaded from: classes12.dex */
@Metadata(d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\t\u0010\u000b\u001a\u00020\u0003HÆ\u0003J\t\u0010\f\u001a\u00020\u0005HÆ\u0003J\u001d\u0010\r\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u0005HÆ\u0001J\u0013\u0010\u000e\u001a\u00020\u000f2\b\u0010\u0010\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u0011\u001a\u00020\u0012HÖ\u0001J\t\u0010\u0013\u001a\u00020\u0014HÖ\u0001R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\n¨\u0006\u0015"}, d2 = {"Lkotlin/script/experimental/host/ScriptDefinition;", "", "compilationConfiguration", "Lkotlin/script/experimental/api/ScriptCompilationConfiguration;", "evaluationConfiguration", "Lkotlin/script/experimental/api/ScriptEvaluationConfiguration;", "(Lkotlin/script/experimental/api/ScriptCompilationConfiguration;Lkotlin/script/experimental/api/ScriptEvaluationConfiguration;)V", "getCompilationConfiguration", "()Lkotlin/script/experimental/api/ScriptCompilationConfiguration;", "getEvaluationConfiguration", "()Lkotlin/script/experimental/api/ScriptEvaluationConfiguration;", "component1", "component2", ConfigConstants.CONFIG_RENAMELIMIT_COPY, Namer.EQUALS_METHOD_NAME, "", "other", "hashCode", "", "toString", "", PathUtil.KOTLIN_SCRIPTING_COMMON_NAME}, k = 1, mv = {1, 9, 0}, xi = 48)
public final /* data */ class ScriptDefinition {
    private final ScriptCompilationConfiguration compilationConfiguration;
    private final ScriptEvaluationConfiguration evaluationConfiguration;

    public static /* synthetic */ ScriptDefinition copy$default(ScriptDefinition scriptDefinition, ScriptCompilationConfiguration scriptCompilationConfiguration, ScriptEvaluationConfiguration scriptEvaluationConfiguration, int i, Object obj) {
        if ((i & 1) != 0) {
            scriptCompilationConfiguration = scriptDefinition.compilationConfiguration;
        }
        if ((i & 2) != 0) {
            scriptEvaluationConfiguration = scriptDefinition.evaluationConfiguration;
        }
        return scriptDefinition.copy(scriptCompilationConfiguration, scriptEvaluationConfiguration);
    }

    /* JADX INFO: renamed from: component1, reason: from getter */
    public final ScriptCompilationConfiguration getCompilationConfiguration() {
        return this.compilationConfiguration;
    }

    /* JADX INFO: renamed from: component2, reason: from getter */
    public final ScriptEvaluationConfiguration getEvaluationConfiguration() {
        return this.evaluationConfiguration;
    }

    public final ScriptDefinition copy(ScriptCompilationConfiguration compilationConfiguration, ScriptEvaluationConfiguration evaluationConfiguration) {
        Intrinsics.checkNotNullParameter(compilationConfiguration, "compilationConfiguration");
        Intrinsics.checkNotNullParameter(evaluationConfiguration, "evaluationConfiguration");
        return new ScriptDefinition(compilationConfiguration, evaluationConfiguration);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof ScriptDefinition)) {
            return false;
        }
        ScriptDefinition scriptDefinition = (ScriptDefinition) other;
        return Intrinsics.areEqual(this.compilationConfiguration, scriptDefinition.compilationConfiguration) && Intrinsics.areEqual(this.evaluationConfiguration, scriptDefinition.evaluationConfiguration);
    }

    public int hashCode() {
        return (this.compilationConfiguration.hashCode() * 31) + this.evaluationConfiguration.hashCode();
    }

    public String toString() {
        return "ScriptDefinition(compilationConfiguration=" + this.compilationConfiguration + ", evaluationConfiguration=" + this.evaluationConfiguration + ')';
    }

    public ScriptDefinition(ScriptCompilationConfiguration compilationConfiguration, ScriptEvaluationConfiguration evaluationConfiguration) {
        Intrinsics.checkNotNullParameter(compilationConfiguration, "compilationConfiguration");
        Intrinsics.checkNotNullParameter(evaluationConfiguration, "evaluationConfiguration");
        this.compilationConfiguration = compilationConfiguration;
        this.evaluationConfiguration = evaluationConfiguration;
    }

    public final ScriptCompilationConfiguration getCompilationConfiguration() {
        return this.compilationConfiguration;
    }

    public final ScriptEvaluationConfiguration getEvaluationConfiguration() {
        return this.evaluationConfiguration;
    }
}
