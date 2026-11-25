package kotlin.script.experimental.jvmhost.repl;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.script.experimental.api.ScriptCompilationConfiguration;
import kotlin.script.experimental.api.SourceCode;
import org.jetbrains.kotlin.cli.common.repl.ReplCodeLine;

/* JADX INFO: compiled from: legacyReplCompilation.kt */
/* JADX INFO: loaded from: classes12.dex */
@Metadata(d1 = {"\u0000\u0012\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u001a\u0014\u0010\u0000\u001a\u00020\u0001*\u00020\u00022\u0006\u0010\u0003\u001a\u00020\u0004H\u0000¨\u0006\u0005"}, d2 = {"toSourceCode", "Lkotlin/script/experimental/api/SourceCode;", "Lorg/jetbrains/kotlin/cli/common/repl/ReplCodeLine;", "compilationConfiguration", "Lkotlin/script/experimental/api/ScriptCompilationConfiguration;", "kotlin-scripting-jvm-host-unshaded"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class LegacyReplCompilationKt {
    public static final SourceCode toSourceCode(ReplCodeLine replCodeLine, ScriptCompilationConfiguration compilationConfiguration) {
        Intrinsics.checkNotNullParameter(replCodeLine, "<this>");
        Intrinsics.checkNotNullParameter(compilationConfiguration, "compilationConfiguration");
        return new SourceCodeFromReplCodeLine(replCodeLine, compilationConfiguration);
    }
}
