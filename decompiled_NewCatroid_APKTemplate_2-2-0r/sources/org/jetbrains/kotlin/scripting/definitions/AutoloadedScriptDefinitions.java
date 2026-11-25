package org.jetbrains.kotlin.scripting.definitions;

import java.io.File;
import java.util.List;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.LazyThreadSafetyMode;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlin.script.experimental.api.ScriptDiagnostic;
import kotlin.script.experimental.host.ScriptingHostConfiguration;
import kotlin.script.experimental.jvm.impl.PathUtilKt;
import kotlin.sequences.Sequence;
import kotlin.sequences.SequencesKt;
import org.jetbrains.kotlin.utils.PathUtil;

/* JADX INFO: compiled from: AutoloadedScriptDefinitions.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000B\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\u0018\u0000 \u00182\u00020\u0001:\u0001\u0018B3\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u001c\u0010\u0006\u001a\u0018\u0012\u0004\u0012\u00020\b\u0012\u0004\u0012\u00020\t\u0012\u0004\u0012\u00020\n0\u0007j\u0002`\u000b¢\u0006\u0002\u0010\fR\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u001b\u0010\r\u001a\u00020\u000e8BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\u0011\u0010\u0012\u001a\u0004\b\u000f\u0010\u0010R\u001a\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\u00150\u0014X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0016\u0010\u0017R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R$\u0010\u0006\u001a\u0018\u0012\u0004\u0012\u00020\b\u0012\u0004\u0012\u00020\t\u0012\u0004\u0012\u00020\n0\u0007j\u0002`\u000bX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0019"}, d2 = {"Lorg/jetbrains/kotlin/scripting/definitions/AutoloadedScriptDefinitions;", "Lorg/jetbrains/kotlin/scripting/definitions/ScriptDefinitionsSource;", "hostConfiguration", "Lkotlin/script/experimental/host/ScriptingHostConfiguration;", "baseClassloader", "Ljava/lang/ClassLoader;", "messageReporter", "Lkotlin/Function2;", "Lkotlin/script/experimental/api/ScriptDiagnostic$Severity;", "", "", "Lorg/jetbrains/kotlin/scripting/definitions/MessageReporter;", "(Lkotlin/script/experimental/host/ScriptingHostConfiguration;Ljava/lang/ClassLoader;Lkotlin/jvm/functions/Function2;)V", "basePath", "Ljava/io/File;", "getBasePath", "()Ljava/io/File;", "basePath$delegate", "Lkotlin/Lazy;", "definitions", "Lkotlin/sequences/Sequence;", "Lorg/jetbrains/kotlin/scripting/definitions/ScriptDefinition;", "getDefinitions", "()Lkotlin/sequences/Sequence;", "Companion", PathUtil.KOTLIN_SCRIPTING_COMPILER_IMPL_NAME}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class AutoloadedScriptDefinitions implements ScriptDefinitionsSource {
    private static final List<String> MAIN_KTS_JARS = CollectionsKt.listOf((Object[]) new String[]{PathUtil.MAIN_KTS_NAME, PathUtil.KOTLIN_JAVA_STDLIB_NAME, PathUtil.KOTLIN_JAVA_SCRIPT_RUNTIME_NAME, PathUtil.KOTLIN_JAVA_REFLECT_NAME});
    private final ClassLoader baseClassloader;

    /* JADX INFO: renamed from: basePath$delegate, reason: from kotlin metadata */
    private final Lazy basePath;
    private final Sequence<ScriptDefinition> definitions;
    private final ScriptingHostConfiguration hostConfiguration;
    private final Function2<ScriptDiagnostic.Severity, String, Unit> messageReporter;

    /* JADX WARN: Multi-variable type inference failed */
    public AutoloadedScriptDefinitions(ScriptingHostConfiguration hostConfiguration, ClassLoader baseClassloader, Function2<? super ScriptDiagnostic.Severity, ? super String, Unit> messageReporter) {
        Intrinsics.checkNotNullParameter(hostConfiguration, "hostConfiguration");
        Intrinsics.checkNotNullParameter(baseClassloader, "baseClassloader");
        Intrinsics.checkNotNullParameter(messageReporter, "messageReporter");
        this.hostConfiguration = hostConfiguration;
        this.baseClassloader = baseClassloader;
        this.messageReporter = messageReporter;
        this.basePath = LazyKt.lazy(LazyThreadSafetyMode.PUBLICATION, (Function0) new Function0<File>() { // from class: org.jetbrains.kotlin.scripting.definitions.AutoloadedScriptDefinitions$basePath$2
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // kotlin.jvm.functions.Function0
            public final File invoke() {
                File parentFile;
                File fileTryGetResourcePathForClass = PathUtilKt.tryGetResourcePathForClass(AutoloadedScriptDefinitions.class);
                if (fileTryGetResourcePathForClass != null && (parentFile = fileTryGetResourcePathForClass.getParentFile()) != null) {
                    if (!parentFile.isDirectory()) {
                        parentFile = null;
                    }
                    if (parentFile != null) {
                        return parentFile;
                    }
                }
                return new File(".");
            }
        });
        this.definitions = SequencesKt.sequence(new AutoloadedScriptDefinitions$definitions$1(this, null));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final File getBasePath() {
        return (File) this.basePath.getValue();
    }

    @Override // org.jetbrains.kotlin.scripting.definitions.ScriptDefinitionsSource
    public Sequence<ScriptDefinition> getDefinitions() {
        return this.definitions;
    }
}
