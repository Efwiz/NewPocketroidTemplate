package kotlin.script.experimental.api;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.utils.PathUtil;

/* JADX INFO: compiled from: scriptEvaluation.kt */
/* JADX INFO: loaded from: classes12.dex */
@Metadata(d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B\u0017\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\b\u0010\u0004\u001a\u0004\u0018\u00010\u0005¢\u0006\u0002\u0010\u0006J\t\u0010\u000b\u001a\u00020\u0003HÆ\u0003J\u000b\u0010\f\u001a\u0004\u0018\u00010\u0005HÆ\u0003J\u001f\u0010\r\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u0005HÆ\u0001J\u0013\u0010\u000e\u001a\u00020\u000f2\b\u0010\u0010\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u0011\u001a\u00020\u0012HÖ\u0001J\t\u0010\u0013\u001a\u00020\u0014HÖ\u0001R\u0013\u0010\u0004\u001a\u0004\u0018\u00010\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\n¨\u0006\u0015"}, d2 = {"Lkotlin/script/experimental/api/EvaluationResult;", "", "returnValue", "Lkotlin/script/experimental/api/ResultValue;", "configuration", "Lkotlin/script/experimental/api/ScriptEvaluationConfiguration;", "(Lkotlin/script/experimental/api/ResultValue;Lkotlin/script/experimental/api/ScriptEvaluationConfiguration;)V", "getConfiguration", "()Lkotlin/script/experimental/api/ScriptEvaluationConfiguration;", "getReturnValue", "()Lkotlin/script/experimental/api/ResultValue;", "component1", "component2", ConfigConstants.CONFIG_RENAMELIMIT_COPY, Namer.EQUALS_METHOD_NAME, "", "other", "hashCode", "", "toString", "", PathUtil.KOTLIN_SCRIPTING_COMMON_NAME}, k = 1, mv = {1, 9, 0}, xi = 48)
public final /* data */ class EvaluationResult {
    private final ScriptEvaluationConfiguration configuration;
    private final ResultValue returnValue;

    public static /* synthetic */ EvaluationResult copy$default(EvaluationResult evaluationResult, ResultValue resultValue, ScriptEvaluationConfiguration scriptEvaluationConfiguration, int i, Object obj) {
        if ((i & 1) != 0) {
            resultValue = evaluationResult.returnValue;
        }
        if ((i & 2) != 0) {
            scriptEvaluationConfiguration = evaluationResult.configuration;
        }
        return evaluationResult.copy(resultValue, scriptEvaluationConfiguration);
    }

    /* JADX INFO: renamed from: component1, reason: from getter */
    public final ResultValue getReturnValue() {
        return this.returnValue;
    }

    /* JADX INFO: renamed from: component2, reason: from getter */
    public final ScriptEvaluationConfiguration getConfiguration() {
        return this.configuration;
    }

    public final EvaluationResult copy(ResultValue returnValue, ScriptEvaluationConfiguration configuration) {
        Intrinsics.checkNotNullParameter(returnValue, "returnValue");
        return new EvaluationResult(returnValue, configuration);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof EvaluationResult)) {
            return false;
        }
        EvaluationResult evaluationResult = (EvaluationResult) other;
        return Intrinsics.areEqual(this.returnValue, evaluationResult.returnValue) && Intrinsics.areEqual(this.configuration, evaluationResult.configuration);
    }

    public int hashCode() {
        int iHashCode = this.returnValue.hashCode() * 31;
        ScriptEvaluationConfiguration scriptEvaluationConfiguration = this.configuration;
        return iHashCode + (scriptEvaluationConfiguration == null ? 0 : scriptEvaluationConfiguration.hashCode());
    }

    public String toString() {
        return "EvaluationResult(returnValue=" + this.returnValue + ", configuration=" + this.configuration + ')';
    }

    public EvaluationResult(ResultValue returnValue, ScriptEvaluationConfiguration scriptEvaluationConfiguration) {
        Intrinsics.checkNotNullParameter(returnValue, "returnValue");
        this.returnValue = returnValue;
        this.configuration = scriptEvaluationConfiguration;
    }

    public final ScriptEvaluationConfiguration getConfiguration() {
        return this.configuration;
    }

    public final ResultValue getReturnValue() {
        return this.returnValue;
    }
}
