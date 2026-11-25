package org.jetbrains.kotlin.scripting.definitions;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.script.experimental.api.ScriptCompilationConfiguration;
import kotlin.script.experimental.api.ScriptCompilationKt;
import kotlin.script.experimental.host.ScriptingHostConfiguration;
import org.jetbrains.kotlin.utils.PathUtil;

/* JADX INFO: compiled from: KotlinScriptDefinitionAdapterFromNewAPI.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\t\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006R\u0014\u0010\u0007\u001a\u00020\b8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\t\u0010\nR\u0014\u0010\u0004\u001a\u00020\u0005X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u0014\u0010\r\u001a\u00020\b8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u000e\u0010\nR\u0014\u0010\u0002\u001a\u00020\u0003X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0010¨\u0006\u0011"}, d2 = {"Lorg/jetbrains/kotlin/scripting/definitions/KotlinScriptDefinitionAdapterFromNewAPI;", "Lorg/jetbrains/kotlin/scripting/definitions/KotlinScriptDefinitionAdapterFromNewAPIBase;", "scriptCompilationConfiguration", "Lkotlin/script/experimental/api/ScriptCompilationConfiguration;", "hostConfiguration", "Lkotlin/script/experimental/host/ScriptingHostConfiguration;", "(Lkotlin/script/experimental/api/ScriptCompilationConfiguration;Lkotlin/script/experimental/host/ScriptingHostConfiguration;)V", "fileExtension", "", "getFileExtension", "()Ljava/lang/String;", "getHostConfiguration", "()Lkotlin/script/experimental/host/ScriptingHostConfiguration;", "name", "getName", "getScriptCompilationConfiguration", "()Lkotlin/script/experimental/api/ScriptCompilationConfiguration;", PathUtil.KOTLIN_SCRIPTING_COMPILER_IMPL_NAME}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class KotlinScriptDefinitionAdapterFromNewAPI extends KotlinScriptDefinitionAdapterFromNewAPIBase {
    private final ScriptingHostConfiguration hostConfiguration;
    private final ScriptCompilationConfiguration scriptCompilationConfiguration;

    @Override // org.jetbrains.kotlin.scripting.definitions.KotlinScriptDefinitionAdapterFromNewAPIBase
    public ScriptCompilationConfiguration getScriptCompilationConfiguration() {
        return this.scriptCompilationConfiguration;
    }

    @Override // org.jetbrains.kotlin.scripting.definitions.KotlinScriptDefinitionAdapterFromNewAPIBase
    public ScriptingHostConfiguration getHostConfiguration() {
        return this.hostConfiguration;
    }

    public KotlinScriptDefinitionAdapterFromNewAPI(ScriptCompilationConfiguration scriptCompilationConfiguration, ScriptingHostConfiguration hostConfiguration) {
        Intrinsics.checkNotNullParameter(scriptCompilationConfiguration, "scriptCompilationConfiguration");
        Intrinsics.checkNotNullParameter(hostConfiguration, "hostConfiguration");
        this.scriptCompilationConfiguration = scriptCompilationConfiguration;
        this.hostConfiguration = hostConfiguration;
    }

    @Override // org.jetbrains.kotlin.scripting.definitions.KotlinScriptDefinitionAdapterFromNewAPIBase, org.jetbrains.kotlin.scripting.definitions.KotlinScriptDefinition
    public String getName() {
        String str = (String) getScriptCompilationConfiguration().get(ScriptCompilationKt.getDisplayName(ScriptCompilationConfiguration.INSTANCE));
        return str == null ? super.getName() : str;
    }

    @Override // org.jetbrains.kotlin.scripting.definitions.KotlinScriptDefinition
    public String getFileExtension() {
        String str = (String) getScriptCompilationConfiguration().get(ScriptCompilationKt.getFileExtension(ScriptCompilationConfiguration.INSTANCE));
        return str == null ? super.getFileExtension() : str;
    }
}
