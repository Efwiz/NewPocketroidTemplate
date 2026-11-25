package kotlin.script.experimental.jvm.util;

import com.google.android.gms.analytics.ecommerce.ProductAction;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.huawei.hms.ml.language.common.utils.Constant;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.utils.PathUtil;

/* JADX INFO: compiled from: SnippetsHistory.kt */
/* JADX INFO: loaded from: classes12.dex */
@Metadata(d1 = {"\u0000>\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0002\b\u0006\b\u0016\u0018\u0000 \u001d*\u0004\b\u0000\u0010\u0001*\u0004\b\u0001\u0010\u00022\u00020\u0003:\u0001\u001dBA\u0012:\b\u0002\u0010\u0004\u001a4\u0012 \u0012\u001e\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00028\u00010\u0006j\u000e\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00028\u0001`\u00070\u0005j\u000e\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00028\u0001`\b¢\u0006\u0002\u0010\tJ\u001b\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00028\u00002\u0006\u0010\u0015\u001a\u00028\u0001¢\u0006\u0002\u0010\u0016J\u0006\u0010\u0017\u001a\u00020\u0018J\u0006\u0010\u0019\u001a\u00020\u0018J&\u0010\u001a\u001a\"\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00028\u0001\u0018\u00010\u0006j\u0010\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00028\u0001\u0018\u0001`\u0007J\r\u0010\u001b\u001a\u0004\u0018\u00018\u0001¢\u0006\u0002\u0010\u001cRF\u0010\n\u001a4\u0012 \u0012\u001e\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00028\u00010\u0006j\u000e\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00028\u0001`\u00070\u000bj\u000e\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00028\u0001`\fX\u0084\u0004¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000eR3\u0010\u000f\u001a$\u0012 \u0012\u001e\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00028\u00010\u0006j\u000e\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00028\u0001`\u00070\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0011¨\u0006\u001e"}, d2 = {"Lkotlin/script/experimental/jvm/util/SnippetsHistory;", "CompiledT", "ResultT", "Ljava/io/Serializable;", "startingHistory", "", "Lkotlin/Pair;", "Lkotlin/script/experimental/jvm/util/CompiledHistoryItem;", "Lkotlin/script/experimental/jvm/util/CompiledHistoryList;", "(Ljava/util/List;)V", "history", "Ljava/util/ArrayList;", "Lkotlin/script/experimental/jvm/util/CompiledHistoryStorage;", "getHistory", "()Ljava/util/ArrayList;", FirebaseAnalytics.Param.ITEMS, "getItems", "()Ljava/util/List;", ProductAction.ACTION_ADD, "", Constant.LINE, "value", "(Ljava/lang/Object;Ljava/lang/Object;)V", "isEmpty", "", "isNotEmpty", "lastItem", "lastValue", "()Ljava/lang/Object;", "Companion", PathUtil.KOTLIN_SCRIPTING_JVM_NAME}, k = 1, mv = {1, 9, 0}, xi = 48)
public class SnippetsHistory<CompiledT, ResultT> implements Serializable {
    private static final long serialVersionUID = 8328353001L;
    private final ArrayList<Pair<CompiledT, ResultT>> history;
    private final List<Pair<CompiledT, ResultT>> items;

    /* JADX WARN: Multi-variable type inference failed */
    public SnippetsHistory() {
        this(null, 1, 0 == true ? 1 : 0);
    }

    public SnippetsHistory(List<? extends Pair<? extends CompiledT, ? extends ResultT>> startingHistory) {
        Intrinsics.checkNotNullParameter(startingHistory, "startingHistory");
        ArrayList<Pair<CompiledT, ResultT>> arrayList = new ArrayList<>(startingHistory);
        this.history = arrayList;
        this.items = arrayList;
    }

    public /* synthetic */ SnippetsHistory(List list, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this((i & 1) != 0 ? CollectionsKt.emptyList() : list);
    }

    protected final ArrayList<Pair<CompiledT, ResultT>> getHistory() {
        return this.history;
    }

    public final void add(CompiledT line, ResultT value) {
        this.history.add(TuplesKt.to(line, value));
    }

    public final Pair<CompiledT, ResultT> lastItem() {
        return (Pair) CollectionsKt.lastOrNull((List) this.history);
    }

    public final ResultT lastValue() {
        Pair<CompiledT, ResultT> pairLastItem = lastItem();
        if (pairLastItem != null) {
            return pairLastItem.getSecond();
        }
        return null;
    }

    public final List<Pair<CompiledT, ResultT>> getItems() {
        return this.items;
    }

    public final boolean isEmpty() {
        return this.history.isEmpty();
    }

    public final boolean isNotEmpty() {
        return !this.history.isEmpty();
    }
}
