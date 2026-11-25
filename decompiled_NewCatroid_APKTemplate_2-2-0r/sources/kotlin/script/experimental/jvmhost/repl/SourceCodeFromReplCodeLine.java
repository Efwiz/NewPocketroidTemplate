package kotlin.script.experimental.jvmhost.repl;

import kotlin.Metadata;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlin.script.experimental.api.ReplDataKt;
import kotlin.script.experimental.api.ReplSnippetIdImpl;
import kotlin.script.experimental.api.ScriptCompilationConfiguration;
import kotlin.script.experimental.api.ScriptCompilationKt;
import kotlin.script.experimental.api.SourceCode;
import org.jetbrains.kotlin.cli.common.repl.ReplCodeLine;

/* JADX INFO: compiled from: legacyReplCompilation.kt */
/* JADX INFO: loaded from: classes12.dex */
@Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000e\n\u0002\b\u0007\b\u0000\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0016\u0010\t\u001a\u0004\u0018\u00010\nX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u0014\u0010\r\u001a\u00020\nX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\fR\u0014\u0010\u000f\u001a\u00020\n8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0010\u0010\f¨\u0006\u0011"}, d2 = {"Lkotlin/script/experimental/jvmhost/repl/SourceCodeFromReplCodeLine;", "Lkotlin/script/experimental/api/SourceCode;", "codeLine", "Lorg/jetbrains/kotlin/cli/common/repl/ReplCodeLine;", "compilationConfiguration", "Lkotlin/script/experimental/api/ScriptCompilationConfiguration;", "(Lorg/jetbrains/kotlin/cli/common/repl/ReplCodeLine;Lkotlin/script/experimental/api/ScriptCompilationConfiguration;)V", "getCodeLine", "()Lorg/jetbrains/kotlin/cli/common/repl/ReplCodeLine;", "locationId", "", "getLocationId", "()Ljava/lang/String;", "name", "getName", "text", "getText", "kotlin-scripting-jvm-host-unshaded"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class SourceCodeFromReplCodeLine implements SourceCode {
    private final ReplCodeLine codeLine;
    private final String locationId;
    private final String name;

    public SourceCodeFromReplCodeLine(ReplCodeLine codeLine, ScriptCompilationConfiguration compilationConfiguration) {
        Intrinsics.checkNotNullParameter(codeLine, "codeLine");
        Intrinsics.checkNotNullParameter(compilationConfiguration, "compilationConfiguration");
        this.codeLine = codeLine;
        StringBuilder sb = new StringBuilder();
        Object obj = compilationConfiguration.get(ReplDataKt.getMakeSnippetIdentifier(ReplDataKt.getRepl(ScriptCompilationConfiguration.INSTANCE)));
        Intrinsics.checkNotNull(obj);
        this.name = sb.append((String) ((Function2) obj).invoke(compilationConfiguration, new ReplSnippetIdImpl(codeLine.getNo(), codeLine.getGeneration(), 0))).append('.').append((String) compilationConfiguration.get(ScriptCompilationKt.getFileExtension(ScriptCompilationConfiguration.INSTANCE))).toString();
    }

    public final ReplCodeLine getCodeLine() {
        return this.codeLine;
    }

    @Override // kotlin.script.experimental.api.SourceCode
    public String getText() {
        return this.codeLine.getCode();
    }

    @Override // kotlin.script.experimental.api.SourceCode
    public String getName() {
        return this.name;
    }

    @Override // kotlin.script.experimental.api.SourceCode
    public String getLocationId() {
        return this.locationId;
    }
}
