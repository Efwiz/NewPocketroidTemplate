package org.jetbrains.kotlin.scripting.compiler.plugin.repl;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.script.experimental.api.SourceCode;
import org.jetbrains.kotlin.cli.common.repl.ReplCodeLine;
import org.jetbrains.kotlin.utils.PathUtil;

/* JADX INFO: compiled from: ReplCodeAnalyzer.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000(\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\u001a\u0014\u0010\u0000\u001a\u00020\u0001*\u00020\u00022\u0006\u0010\u0003\u001a\u00020\u0004H\u0000\u001a\n\u0010\u0005\u001a\u00020\u0001*\u00020\u0006*F\b\u0002\u0010\u0007\u001a\u0004\b\u0000\u0010\b\"\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020\u0001\u0012\u0004\u0012\u0002H\b`\n0\t2$\u0012 \u0012\u001e\u0012\u0004\u0012\u00020\u0001\u0012\u0004\u0012\u0002H\b0\u000bj\u000e\u0012\u0004\u0012\u00020\u0001\u0012\u0004\u0012\u0002H\b`\n0\t¨\u0006\f"}, d2 = {"addNo", "Lorg/jetbrains/kotlin/scripting/compiler/plugin/repl/SourceCodeByReplLine;", "Lkotlin/script/experimental/api/SourceCode;", "no", "", "toSourceCode", "Lorg/jetbrains/kotlin/cli/common/repl/ReplCodeLine;", "ReplSourceHistoryList", "ResultT", "", "Lkotlin/script/experimental/jvm/util/CompiledHistoryItem;", "Lkotlin/Pair;", PathUtil.KOTLIN_SCRIPTING_COMPILER_PLUGIN_NAME}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class ReplCodeAnalyzerKt {
    public static final SourceCodeByReplLine toSourceCode(ReplCodeLine replCodeLine) {
        Intrinsics.checkNotNullParameter(replCodeLine, "<this>");
        return new SourceCodeByReplLine(replCodeLine.getCode(), replCodeLine.getNo(), null, null, 12, null);
    }

    public static final SourceCodeByReplLine addNo(SourceCode sourceCode, int i) {
        Intrinsics.checkNotNullParameter(sourceCode, "<this>");
        return new SourceCodeByReplLine(sourceCode.getText(), i, sourceCode.getName(), sourceCode.getLocationId());
    }
}
