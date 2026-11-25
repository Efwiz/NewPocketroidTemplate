package kotlin.script.experimental.api;

import java.io.Serializable;
import kotlin.Metadata;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.utils.PathUtil;

/* JADX INFO: compiled from: scriptEvaluation.kt */
/* JADX INFO: loaded from: classes12.dex */
@Metadata(d1 = {"\u0000<\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\b\u0086\b\u0018\u0000 \u00152\u00020\u0001:\u0001\u0015B#\u0012\u001c\u0010\u0002\u001a\u0018\u0012\u0004\u0012\u00020\u0004\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00060\u00050\u0003j\u0002`\u0007¢\u0006\u0002\u0010\bJ\u001f\u0010\u000b\u001a\u0018\u0012\u0004\u0012\u00020\u0004\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00060\u00050\u0003j\u0002`\u0007HÆ\u0003J)\u0010\f\u001a\u00020\u00002\u001e\b\u0002\u0010\u0002\u001a\u0018\u0012\u0004\u0012\u00020\u0004\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00060\u00050\u0003j\u0002`\u0007HÆ\u0001J\u0013\u0010\r\u001a\u00020\u000e2\b\u0010\u000f\u001a\u0004\u0018\u00010\u0010HÖ\u0003J\t\u0010\u0011\u001a\u00020\u0012HÖ\u0001J\t\u0010\u0013\u001a\u00020\u0014HÖ\u0001R'\u0010\u0002\u001a\u0018\u0012\u0004\u0012\u00020\u0004\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00060\u00050\u0003j\u0002`\u0007¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\n¨\u0006\u0016"}, d2 = {"Lkotlin/script/experimental/api/RefineEvaluationConfigurationData;", "Ljava/io/Serializable;", "handler", "Lkotlin/Function1;", "Lkotlin/script/experimental/api/ScriptEvaluationConfigurationRefinementContext;", "Lkotlin/script/experimental/api/ResultWithDiagnostics;", "Lkotlin/script/experimental/api/ScriptEvaluationConfiguration;", "Lkotlin/script/experimental/api/RefineScriptEvaluationConfigurationHandler;", "(Lkotlin/jvm/functions/Function1;)V", "getHandler", "()Lkotlin/jvm/functions/Function1;", "component1", ConfigConstants.CONFIG_RENAMELIMIT_COPY, Namer.EQUALS_METHOD_NAME, "", "other", "", "hashCode", "", "toString", "", "Companion", PathUtil.KOTLIN_SCRIPTING_COMMON_NAME}, k = 1, mv = {1, 9, 0}, xi = 48)
public final /* data */ class RefineEvaluationConfigurationData implements Serializable {
    private static final long serialVersionUID = 1;
    private final Function1<ScriptEvaluationConfigurationRefinementContext, ResultWithDiagnostics<ScriptEvaluationConfiguration>> handler;

    /* JADX WARN: Multi-variable type inference failed */
    public static /* synthetic */ RefineEvaluationConfigurationData copy$default(RefineEvaluationConfigurationData refineEvaluationConfigurationData, Function1 function1, int i, Object obj) {
        if ((i & 1) != 0) {
            function1 = refineEvaluationConfigurationData.handler;
        }
        return refineEvaluationConfigurationData.copy(function1);
    }

    public final Function1<ScriptEvaluationConfigurationRefinementContext, ResultWithDiagnostics<ScriptEvaluationConfiguration>> component1() {
        return this.handler;
    }

    public final RefineEvaluationConfigurationData copy(Function1<? super ScriptEvaluationConfigurationRefinementContext, ? extends ResultWithDiagnostics<? extends ScriptEvaluationConfiguration>> handler) {
        Intrinsics.checkNotNullParameter(handler, "handler");
        return new RefineEvaluationConfigurationData(handler);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        return (other instanceof RefineEvaluationConfigurationData) && Intrinsics.areEqual(this.handler, ((RefineEvaluationConfigurationData) other).handler);
    }

    public int hashCode() {
        return this.handler.hashCode();
    }

    public String toString() {
        return "RefineEvaluationConfigurationData(handler=" + this.handler + ')';
    }

    /* JADX WARN: Multi-variable type inference failed */
    public RefineEvaluationConfigurationData(Function1<? super ScriptEvaluationConfigurationRefinementContext, ? extends ResultWithDiagnostics<? extends ScriptEvaluationConfiguration>> handler) {
        Intrinsics.checkNotNullParameter(handler, "handler");
        this.handler = handler;
    }

    public final Function1<ScriptEvaluationConfigurationRefinementContext, ResultWithDiagnostics<ScriptEvaluationConfiguration>> getHandler() {
        return this.handler;
    }
}
