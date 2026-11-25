package kotlin.script.experimental.api;

import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.utils.PathUtil;

/* JADX INFO: compiled from: scriptData.kt */
/* JADX INFO: loaded from: classes12.dex */
@Metadata(d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\f\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B!\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\n\b\u0002\u0010\u0006\u001a\u0004\u0018\u00010\u0007¢\u0006\u0002\u0010\bJ\t\u0010\u000f\u001a\u00020\u0003HÆ\u0003J\t\u0010\u0010\u001a\u00020\u0005HÆ\u0003J\u000b\u0010\u0011\u001a\u0004\u0018\u00010\u0007HÆ\u0003J)\u0010\u0012\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00052\n\b\u0002\u0010\u0006\u001a\u0004\u0018\u00010\u0007HÆ\u0001J\u0013\u0010\u0013\u001a\u00020\u00142\b\u0010\u0015\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u0016\u001a\u00020\u0017HÖ\u0001J\t\u0010\u0018\u001a\u00020\u0019HÖ\u0001R\u0013\u0010\u0006\u001a\u0004\u0018\u00010\u0007¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000e¨\u0006\u001a"}, d2 = {"Lkotlin/script/experimental/api/ScriptConfigurationRefinementContext;", "", "script", "Lkotlin/script/experimental/api/SourceCode;", "compilationConfiguration", "Lkotlin/script/experimental/api/ScriptCompilationConfiguration;", "collectedData", "Lkotlin/script/experimental/api/ScriptCollectedData;", "(Lkotlin/script/experimental/api/SourceCode;Lkotlin/script/experimental/api/ScriptCompilationConfiguration;Lkotlin/script/experimental/api/ScriptCollectedData;)V", "getCollectedData", "()Lkotlin/script/experimental/api/ScriptCollectedData;", "getCompilationConfiguration", "()Lkotlin/script/experimental/api/ScriptCompilationConfiguration;", "getScript", "()Lkotlin/script/experimental/api/SourceCode;", "component1", "component2", "component3", ConfigConstants.CONFIG_RENAMELIMIT_COPY, Namer.EQUALS_METHOD_NAME, "", "other", "hashCode", "", "toString", "", PathUtil.KOTLIN_SCRIPTING_COMMON_NAME}, k = 1, mv = {1, 9, 0}, xi = 48)
public final /* data */ class ScriptConfigurationRefinementContext {
    private final ScriptCollectedData collectedData;
    private final ScriptCompilationConfiguration compilationConfiguration;
    private final SourceCode script;

    public static /* synthetic */ ScriptConfigurationRefinementContext copy$default(ScriptConfigurationRefinementContext scriptConfigurationRefinementContext, SourceCode sourceCode, ScriptCompilationConfiguration scriptCompilationConfiguration, ScriptCollectedData scriptCollectedData, int i, Object obj) {
        if ((i & 1) != 0) {
            sourceCode = scriptConfigurationRefinementContext.script;
        }
        if ((i & 2) != 0) {
            scriptCompilationConfiguration = scriptConfigurationRefinementContext.compilationConfiguration;
        }
        if ((i & 4) != 0) {
            scriptCollectedData = scriptConfigurationRefinementContext.collectedData;
        }
        return scriptConfigurationRefinementContext.copy(sourceCode, scriptCompilationConfiguration, scriptCollectedData);
    }

    /* JADX INFO: renamed from: component1, reason: from getter */
    public final SourceCode getScript() {
        return this.script;
    }

    /* JADX INFO: renamed from: component2, reason: from getter */
    public final ScriptCompilationConfiguration getCompilationConfiguration() {
        return this.compilationConfiguration;
    }

    /* JADX INFO: renamed from: component3, reason: from getter */
    public final ScriptCollectedData getCollectedData() {
        return this.collectedData;
    }

    public final ScriptConfigurationRefinementContext copy(SourceCode script, ScriptCompilationConfiguration compilationConfiguration, ScriptCollectedData collectedData) {
        Intrinsics.checkNotNullParameter(script, "script");
        Intrinsics.checkNotNullParameter(compilationConfiguration, "compilationConfiguration");
        return new ScriptConfigurationRefinementContext(script, compilationConfiguration, collectedData);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof ScriptConfigurationRefinementContext)) {
            return false;
        }
        ScriptConfigurationRefinementContext scriptConfigurationRefinementContext = (ScriptConfigurationRefinementContext) other;
        return Intrinsics.areEqual(this.script, scriptConfigurationRefinementContext.script) && Intrinsics.areEqual(this.compilationConfiguration, scriptConfigurationRefinementContext.compilationConfiguration) && Intrinsics.areEqual(this.collectedData, scriptConfigurationRefinementContext.collectedData);
    }

    public int hashCode() {
        int iHashCode = ((this.script.hashCode() * 31) + this.compilationConfiguration.hashCode()) * 31;
        ScriptCollectedData scriptCollectedData = this.collectedData;
        return iHashCode + (scriptCollectedData == null ? 0 : scriptCollectedData.hashCode());
    }

    public String toString() {
        return "ScriptConfigurationRefinementContext(script=" + this.script + ", compilationConfiguration=" + this.compilationConfiguration + ", collectedData=" + this.collectedData + ')';
    }

    public ScriptConfigurationRefinementContext(SourceCode script, ScriptCompilationConfiguration compilationConfiguration, ScriptCollectedData scriptCollectedData) {
        Intrinsics.checkNotNullParameter(script, "script");
        Intrinsics.checkNotNullParameter(compilationConfiguration, "compilationConfiguration");
        this.script = script;
        this.compilationConfiguration = compilationConfiguration;
        this.collectedData = scriptCollectedData;
    }

    public /* synthetic */ ScriptConfigurationRefinementContext(SourceCode sourceCode, ScriptCompilationConfiguration scriptCompilationConfiguration, ScriptCollectedData scriptCollectedData, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(sourceCode, scriptCompilationConfiguration, (i & 4) != 0 ? null : scriptCollectedData);
    }

    public final SourceCode getScript() {
        return this.script;
    }

    public final ScriptCompilationConfiguration getCompilationConfiguration() {
        return this.compilationConfiguration;
    }

    public final ScriptCollectedData getCollectedData() {
        return this.collectedData;
    }
}
