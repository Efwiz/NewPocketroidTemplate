package org.jetbrains.kotlin.scripting.compiler.plugin.repl;

import com.huawei.hms.ml.language.common.utils.Constant;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.Deprecated;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.ReplaceWith;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.script.experimental.jvm.util.SnippetsHistory;
import org.jetbrains.kotlin.cli.common.repl.ILineId;
import org.jetbrains.kotlin.utils.PathUtil;

/* JADX INFO: compiled from: ReplCodeAnalyzer.kt */
/* JADX INFO: loaded from: classes6.dex */
@Deprecated(message = "This functionality is left for backwards compatibility only", replaceWith = @ReplaceWith(expression = "SnippetsHistory", imports = {}))
@Metadata(d1 = {"\u00006\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\b\u0003\u0018\u0000*\u0004\b\u0000\u0010\u00012\u000e\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u0002H\u00010\u0002B1\u0012*\b\u0002\u0010\u0004\u001a$\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00028\u00000\u00060\u0005j\u000e\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00028\u0000`\u0007¢\u0006\u0002\u0010\bJ2\u0010\t\u001a.\u0012 \u0012\u001e\u0012\u0004\u0012\u00020\n\u0012\u0004\u0012\u00028\u00000\u0006j\u000e\u0012\u0004\u0012\u00020\n\u0012\u0004\u0012\u00028\u0000`\u000b0\u0005j\b\u0012\u0004\u0012\u00028\u0000`\fJ:\u0010\r\u001a.\u0012 \u0012\u001e\u0012\u0004\u0012\u00020\n\u0012\u0004\u0012\u00028\u00000\u0006j\u000e\u0012\u0004\u0012\u00020\n\u0012\u0004\u0012\u00028\u0000`\u000b0\u0005j\b\u0012\u0004\u0012\u00028\u0000`\f2\u0006\u0010\u000e\u001a\u00020\u000f¨\u0006\u0010"}, d2 = {"Lorg/jetbrains/kotlin/scripting/compiler/plugin/repl/ResettableSnippetsHistory;", "ResultT", "Lkotlin/script/experimental/jvm/util/SnippetsHistory;", "Lorg/jetbrains/kotlin/scripting/compiler/plugin/repl/ReplCodeAnalyzerBase$CompiledCode;", "startingHistory", "", "Lkotlin/Pair;", "Lkotlin/script/experimental/jvm/util/CompiledHistoryList;", "(Ljava/util/List;)V", "reset", "Lorg/jetbrains/kotlin/scripting/compiler/plugin/repl/SourceCodeByReplLine;", "Lkotlin/script/experimental/jvm/util/CompiledHistoryItem;", "Lorg/jetbrains/kotlin/scripting/compiler/plugin/repl/ReplSourceHistoryList;", "resetToLine", Constant.LINE, "Lorg/jetbrains/kotlin/cli/common/repl/ILineId;", PathUtil.KOTLIN_SCRIPTING_COMPILER_PLUGIN_NAME}, k = 1, mv = {1, 9, 0}, xi = 48)
final class ResettableSnippetsHistory<ResultT> extends SnippetsHistory<ReplCodeAnalyzerBase.CompiledCode, ResultT> {
    /* JADX WARN: Multi-variable type inference failed */
    public ResettableSnippetsHistory() {
        this(null, 1, 0 == true ? 1 : 0);
    }

    public /* synthetic */ ResettableSnippetsHistory(List list, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this((i & 1) != 0 ? CollectionsKt.emptyList() : list);
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ResettableSnippetsHistory(List<? extends Pair<ReplCodeAnalyzerBase.CompiledCode, ? extends ResultT>> startingHistory) {
        super(startingHistory);
        Intrinsics.checkNotNullParameter(startingHistory, "startingHistory");
    }

    public final List<Pair<SourceCodeByReplLine, ResultT>> resetToLine(ILineId line) {
        ReplCodeAnalyzerBase.CompiledCode compiledCode;
        SourceCodeByReplLine source;
        Intrinsics.checkNotNullParameter(line, "line");
        ArrayList arrayList = new ArrayList();
        while (true) {
            Pair pair = (Pair) CollectionsKt.lastOrNull((List) getHistory());
            if (((pair == null || (compiledCode = (ReplCodeAnalyzerBase.CompiledCode) pair.getFirst()) == null || (source = compiledCode.getSource()) == null) ? -1 : source.getNo()) > line.getNo()) {
                Pair<ReplCodeAnalyzerBase.CompiledCode, ResultT> pairRemove = getHistory().remove(getHistory().size() - 1);
                arrayList.add(new Pair(pairRemove.getFirst().getSource(), pairRemove.getSecond()));
            } else {
                return CollectionsKt.reversed(arrayList);
            }
        }
    }

    public final List<Pair<SourceCodeByReplLine, ResultT>> reset() {
        ArrayList<Pair<ReplCodeAnalyzerBase.CompiledCode, ResultT>> history = getHistory();
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(history, 10));
        Iterator<T> it = history.iterator();
        while (it.hasNext()) {
            Pair pair = (Pair) it.next();
            arrayList.add(new Pair(((ReplCodeAnalyzerBase.CompiledCode) pair.getFirst()).getSource(), pair.getSecond()));
        }
        ArrayList arrayList2 = arrayList;
        getHistory().clear();
        return arrayList2;
    }
}
