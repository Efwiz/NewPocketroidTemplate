package org.jetbrains.kotlin.scripting.definitions;

import java.io.File;
import java.util.List;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlin.script.experimental.api.ScriptDiagnostic;
import kotlin.script.experimental.host.ScriptingHostConfiguration;
import kotlin.sequences.Sequence;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.utils.PathUtil;

/* JADX INFO: compiled from: ScriptiDefinitionsFromClasspathDiscoverySource.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000>\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u00002\u00020\u0001B9\u0012\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\u001c\u0010\u0007\u001a\u0018\u0012\u0004\u0012\u00020\t\u0012\u0004\u0012\u00020\n\u0012\u0004\u0012\u00020\u000b0\bj\u0002`\f¢\u0006\u0002\u0010\rR\u0014\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u00100\u000fX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0012R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R$\u0010\u0007\u001a\u0018\u0012\u0004\u0012\u00020\t\u0012\u0004\u0012\u00020\n\u0012\u0004\u0012\u00020\u000b0\bj\u0002`\fX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0013"}, d2 = {"Lorg/jetbrains/kotlin/scripting/definitions/ScriptDefinitionsFromClasspathDiscoverySource;", "Lorg/jetbrains/kotlin/scripting/definitions/ScriptDefinitionsSource;", ModuleXmlParser.CLASSPATH, "", "Ljava/io/File;", "hostConfiguration", "Lkotlin/script/experimental/host/ScriptingHostConfiguration;", "messageReporter", "Lkotlin/Function2;", "Lkotlin/script/experimental/api/ScriptDiagnostic$Severity;", "", "", "Lorg/jetbrains/kotlin/scripting/definitions/MessageReporter;", "(Ljava/util/List;Lkotlin/script/experimental/host/ScriptingHostConfiguration;Lkotlin/jvm/functions/Function2;)V", "definitions", "Lkotlin/sequences/Sequence;", "Lorg/jetbrains/kotlin/scripting/definitions/ScriptDefinition;", "getDefinitions", "()Lkotlin/sequences/Sequence;", PathUtil.KOTLIN_SCRIPTING_COMPILER_IMPL_NAME}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ScriptDefinitionsFromClasspathDiscoverySource implements ScriptDefinitionsSource {
    private final List<File> classpath;
    private final Sequence<ScriptDefinition> definitions;
    private final ScriptingHostConfiguration hostConfiguration;
    private final Function2<ScriptDiagnostic.Severity, String, Unit> messageReporter;

    /* JADX WARN: Multi-variable type inference failed */
    public ScriptDefinitionsFromClasspathDiscoverySource(List<? extends File> classpath, ScriptingHostConfiguration hostConfiguration, Function2<? super ScriptDiagnostic.Severity, ? super String, Unit> messageReporter) {
        Intrinsics.checkNotNullParameter(classpath, "classpath");
        Intrinsics.checkNotNullParameter(hostConfiguration, "hostConfiguration");
        Intrinsics.checkNotNullParameter(messageReporter, "messageReporter");
        this.classpath = classpath;
        this.hostConfiguration = hostConfiguration;
        this.messageReporter = messageReporter;
        this.definitions = ScriptiDefinitionsFromClasspathDiscoverySourceKt.discoverScriptTemplatesInClasspath(this.classpath, getClass().getClassLoader(), this.hostConfiguration, this.messageReporter);
    }

    @Override // org.jetbrains.kotlin.scripting.definitions.ScriptDefinitionsSource
    public Sequence<ScriptDefinition> getDefinitions() {
        return this.definitions;
    }
}
