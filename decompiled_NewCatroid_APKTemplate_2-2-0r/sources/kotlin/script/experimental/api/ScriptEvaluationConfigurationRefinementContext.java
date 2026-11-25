package kotlin.script.experimental.api;

import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.utils.PathUtil;

/* JADX INFO: compiled from: scriptData.kt */
/* JADX INFO: loaded from: classes12.dex */
@Metadata(d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\f\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B!\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\n\b\u0002\u0010\u0006\u001a\u0004\u0018\u00010\u0007¢\u0006\u0002\u0010\bJ\t\u0010\u000f\u001a\u00020\u0003HÆ\u0003J\t\u0010\u0010\u001a\u00020\u0005HÆ\u0003J\u000b\u0010\u0011\u001a\u0004\u0018\u00010\u0007HÆ\u0003J)\u0010\u0012\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00052\n\b\u0002\u0010\u0006\u001a\u0004\u0018\u00010\u0007HÆ\u0001J\u0013\u0010\u0013\u001a\u00020\u00142\b\u0010\u0015\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u0016\u001a\u00020\u0017HÖ\u0001J\t\u0010\u0018\u001a\u00020\u0019HÖ\u0001R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u0013\u0010\u0006\u001a\u0004\u0018\u00010\u0007¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000e¨\u0006\u001a"}, d2 = {"Lkotlin/script/experimental/api/ScriptEvaluationConfigurationRefinementContext;", "", "compiledScript", "Lkotlin/script/experimental/api/CompiledScript;", "evaluationConfiguration", "Lkotlin/script/experimental/api/ScriptEvaluationConfiguration;", "contextData", "Lkotlin/script/experimental/api/ScriptEvaluationContextData;", "(Lkotlin/script/experimental/api/CompiledScript;Lkotlin/script/experimental/api/ScriptEvaluationConfiguration;Lkotlin/script/experimental/api/ScriptEvaluationContextData;)V", "getCompiledScript", "()Lkotlin/script/experimental/api/CompiledScript;", "getContextData", "()Lkotlin/script/experimental/api/ScriptEvaluationContextData;", "getEvaluationConfiguration", "()Lkotlin/script/experimental/api/ScriptEvaluationConfiguration;", "component1", "component2", "component3", ConfigConstants.CONFIG_RENAMELIMIT_COPY, Namer.EQUALS_METHOD_NAME, "", "other", "hashCode", "", "toString", "", PathUtil.KOTLIN_SCRIPTING_COMMON_NAME}, k = 1, mv = {1, 9, 0}, xi = 48)
public final /* data */ class ScriptEvaluationConfigurationRefinementContext {
    private final CompiledScript compiledScript;
    private final ScriptEvaluationContextData contextData;
    private final ScriptEvaluationConfiguration evaluationConfiguration;

    public static /* synthetic */ ScriptEvaluationConfigurationRefinementContext copy$default(ScriptEvaluationConfigurationRefinementContext scriptEvaluationConfigurationRefinementContext, CompiledScript compiledScript, ScriptEvaluationConfiguration scriptEvaluationConfiguration, ScriptEvaluationContextData scriptEvaluationContextData, int i, Object obj) {
        if ((i & 1) != 0) {
            compiledScript = scriptEvaluationConfigurationRefinementContext.compiledScript;
        }
        if ((i & 2) != 0) {
            scriptEvaluationConfiguration = scriptEvaluationConfigurationRefinementContext.evaluationConfiguration;
        }
        if ((i & 4) != 0) {
            scriptEvaluationContextData = scriptEvaluationConfigurationRefinementContext.contextData;
        }
        return scriptEvaluationConfigurationRefinementContext.copy(compiledScript, scriptEvaluationConfiguration, scriptEvaluationContextData);
    }

    /* JADX INFO: renamed from: component1, reason: from getter */
    public final CompiledScript getCompiledScript() {
        return this.compiledScript;
    }

    /* JADX INFO: renamed from: component2, reason: from getter */
    public final ScriptEvaluationConfiguration getEvaluationConfiguration() {
        return this.evaluationConfiguration;
    }

    /* JADX INFO: renamed from: component3, reason: from getter */
    public final ScriptEvaluationContextData getContextData() {
        return this.contextData;
    }

    public final ScriptEvaluationConfigurationRefinementContext copy(CompiledScript compiledScript, ScriptEvaluationConfiguration evaluationConfiguration, ScriptEvaluationContextData contextData) {
        Intrinsics.checkNotNullParameter(compiledScript, "compiledScript");
        Intrinsics.checkNotNullParameter(evaluationConfiguration, "evaluationConfiguration");
        return new ScriptEvaluationConfigurationRefinementContext(compiledScript, evaluationConfiguration, contextData);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof ScriptEvaluationConfigurationRefinementContext)) {
            return false;
        }
        ScriptEvaluationConfigurationRefinementContext scriptEvaluationConfigurationRefinementContext = (ScriptEvaluationConfigurationRefinementContext) other;
        return Intrinsics.areEqual(this.compiledScript, scriptEvaluationConfigurationRefinementContext.compiledScript) && Intrinsics.areEqual(this.evaluationConfiguration, scriptEvaluationConfigurationRefinementContext.evaluationConfiguration) && Intrinsics.areEqual(this.contextData, scriptEvaluationConfigurationRefinementContext.contextData);
    }

    public int hashCode() {
        int iHashCode = ((this.compiledScript.hashCode() * 31) + this.evaluationConfiguration.hashCode()) * 31;
        ScriptEvaluationContextData scriptEvaluationContextData = this.contextData;
        return iHashCode + (scriptEvaluationContextData == null ? 0 : scriptEvaluationContextData.hashCode());
    }

    public String toString() {
        return "ScriptEvaluationConfigurationRefinementContext(compiledScript=" + this.compiledScript + ", evaluationConfiguration=" + this.evaluationConfiguration + ", contextData=" + this.contextData + ')';
    }

    public ScriptEvaluationConfigurationRefinementContext(CompiledScript compiledScript, ScriptEvaluationConfiguration evaluationConfiguration, ScriptEvaluationContextData scriptEvaluationContextData) {
        Intrinsics.checkNotNullParameter(compiledScript, "compiledScript");
        Intrinsics.checkNotNullParameter(evaluationConfiguration, "evaluationConfiguration");
        this.compiledScript = compiledScript;
        this.evaluationConfiguration = evaluationConfiguration;
        this.contextData = scriptEvaluationContextData;
    }

    public /* synthetic */ ScriptEvaluationConfigurationRefinementContext(CompiledScript compiledScript, ScriptEvaluationConfiguration scriptEvaluationConfiguration, ScriptEvaluationContextData scriptEvaluationContextData, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(compiledScript, scriptEvaluationConfiguration, (i & 4) != 0 ? null : scriptEvaluationContextData);
    }

    public final CompiledScript getCompiledScript() {
        return this.compiledScript;
    }

    public final ScriptEvaluationConfiguration getEvaluationConfiguration() {
        return this.evaluationConfiguration;
    }

    public final ScriptEvaluationContextData getContextData() {
        return this.contextData;
    }
}
