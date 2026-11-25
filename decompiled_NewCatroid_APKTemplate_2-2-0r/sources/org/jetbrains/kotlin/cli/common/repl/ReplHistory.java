package org.jetbrains.kotlin.cli.common.repl;

import androidx.exifinterface.media.ExifInterface;
import com.google.android.gms.analytics.ecommerce.ProductAction;
import com.huawei.hms.ml.language.common.utils.Constant;
import java.io.Serializable;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: ReplHistory.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000\\\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\b\n\u0002\b\t\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\u0018\u0000 **\u0004\b\u0000\u0010\u00012\u00020\u0002:\u0001*B5\u0012.\b\u0002\u0010\u0003\u001a(\u0012\u001a\u0012\u0018\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00028\u00000\u0005j\b\u0012\u0004\u0012\u00028\u0000`\u00070\u0004j\b\u0012\u0004\u0012\u00028\u0000`\b¢\u0006\u0002\u0010\tJ\u001b\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u00062\u0006\u0010\u0010\u001a\u00028\u0000¢\u0006\u0002\u0010\u0011J\u001c\u0010\u0012\u001a\u00020\u00132\u0014\u0010\u0014\u001a\u0010\u0012\u0004\u0012\u00020\u0015\u0018\u00010\u0004j\u0004\u0018\u0001`\u0016J\u000e\u0010\u0017\u001a\u00020\u00132\u0006\u0010\u000f\u001a\u00020\u0006J\u000e\u0010\u0017\u001a\u00020\u00132\u0006\u0010\u000f\u001a\u00020\u0015J,\u0010\u0018\u001a(\u0012\u001a\u0012\u0018\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00028\u00000\u0005j\b\u0012\u0004\u0012\u00028\u0000`\u00070\u0004j\b\u0012\u0004\u0012\u00028\u0000`\bJ\u0010\u0010\u0019\u001a\f\u0012\u0004\u0012\u00020\u00150\u0004j\u0002`\u0016J\f\u0010\u001a\u001a\b\u0012\u0004\u0012\u00028\u00000\u0004J#\u0010\u001b\u001a\u0004\u0018\u00010\u001c2\u0014\u0010\u0014\u001a\u0010\u0012\u0004\u0012\u00020\u0015\u0018\u00010\u0004j\u0004\u0018\u0001`\u0016¢\u0006\u0002\u0010\u001dJ\u0006\u0010\u001e\u001a\u00020\u0013J\u0006\u0010\u001f\u001a\u00020\u0013J\b\u0010 \u001a\u0004\u0018\u00010\u0006J \u0010!\u001a\u001c\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00028\u0000\u0018\u00010\u0005j\n\u0012\u0004\u0012\u00028\u0000\u0018\u0001`\u0007J\r\u0010\"\u001a\u0004\u0018\u00018\u0000¢\u0006\u0002\u0010#J\u000e\u0010$\u001a\u00020\u00132\u0006\u0010\u000f\u001a\u00020\u0006J,\u0010%\u001a(\u0012\u001a\u0012\u0018\u0012\u0004\u0012\u00020\u0015\u0012\u0004\u0012\u00028\u00000\u0005j\b\u0012\u0004\u0012\u00028\u0000`&0\u0004j\b\u0012\u0004\u0012\u00028\u0000`'J4\u0010(\u001a(\u0012\u001a\u0012\u0018\u0012\u0004\u0012\u00020\u0015\u0012\u0004\u0012\u00028\u00000\u0005j\b\u0012\u0004\u0012\u00028\u0000`&0\u0004j\b\u0012\u0004\u0012\u00028\u0000`'2\u0006\u0010)\u001a\u00020\u001cJ4\u0010(\u001a(\u0012\u001a\u0012\u0018\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00028\u00000\u0005j\b\u0012\u0004\u0012\u00028\u0000`\u00070\u0004j\b\u0012\u0004\u0012\u00028\u0000`\b2\u0006\u0010\u000f\u001a\u00020\u0006J4\u0010(\u001a(\u0012\u001a\u0012\u0018\u0012\u0004\u0012\u00020\u0015\u0012\u0004\u0012\u00028\u00000\u0005j\b\u0012\u0004\u0012\u00028\u0000`&0\u0004j\b\u0012\u0004\u0012\u00028\u0000`'2\u0006\u0010\u000f\u001a\u00020\u0015R4\u0010\n\u001a(\u0012\u001a\u0012\u0018\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00028\u00000\u0005j\b\u0012\u0004\u0012\u00028\u0000`\u00070\u000bj\b\u0012\u0004\u0012\u00028\u0000`\fX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006+"}, d2 = {"Lorg/jetbrains/kotlin/cli/common/repl/ReplHistory;", ExifInterface.GPS_DIRECTION_TRUE, "Ljava/io/Serializable;", "startingHistory", "", "Lkotlin/Pair;", "Lorg/jetbrains/kotlin/cli/common/repl/CompiledReplCodeLine;", "Lorg/jetbrains/kotlin/cli/common/repl/CompiledHistoryItem;", "Lorg/jetbrains/kotlin/cli/common/repl/CompiledHistoryList;", "(Ljava/util/List;)V", "history", "Ljava/util/ArrayDeque;", "Lorg/jetbrains/kotlin/cli/common/repl/CompiledHistoryStorage;", ProductAction.ACTION_ADD, "", Constant.LINE, "value", "(Lorg/jetbrains/kotlin/cli/common/repl/CompiledReplCodeLine;Ljava/lang/Object;)V", "checkHistoryIsInSync", "", "compareHistory", "Lorg/jetbrains/kotlin/cli/common/repl/ReplCodeLine;", "Lorg/jetbrains/kotlin/cli/common/repl/SourceList;", "contains", "copyAll", "copySources", "copyValues", "firstMismatchingHistory", "", "(Ljava/util/List;)Ljava/lang/Integer;", "isEmpty", "isNotEmpty", "lastCodeLine", "lastItem", "lastValue", "()Ljava/lang/Object;", "removeLast", "reset", "Lorg/jetbrains/kotlin/cli/common/repl/SourceHistoryItem;", "Lorg/jetbrains/kotlin/cli/common/repl/SourceHistoryList;", "resetToLine", "lineNumber", "Companion", "cli-common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ReplHistory<T> implements Serializable {
    private static final long serialVersionUID = 8328353000L;
    private final ArrayDeque<Pair<CompiledReplCodeLine, T>> history;

    /* JADX WARN: Multi-variable type inference failed */
    public ReplHistory() {
        this(null, 1, 0 == true ? 1 : 0);
    }

    public ReplHistory(List<? extends Pair<CompiledReplCodeLine, ? extends T>> startingHistory) {
        Intrinsics.checkNotNullParameter(startingHistory, "startingHistory");
        this.history = new ArrayDeque<>(startingHistory);
    }

    public /* synthetic */ ReplHistory(List list, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this((i & 1) != 0 ? CollectionsKt.emptyList() : list);
    }

    public final boolean isEmpty() {
        return this.history.isEmpty();
    }

    public final boolean isNotEmpty() {
        return !this.history.isEmpty();
    }

    public final void add(CompiledReplCodeLine line, T value) {
        Intrinsics.checkNotNullParameter(line, "line");
        this.history.add(TuplesKt.to(line, value));
    }

    public final boolean removeLast(CompiledReplCodeLine line) {
        Intrinsics.checkNotNullParameter(line, "line");
        if (!Intrinsics.areEqual(this.history.peekLast().getFirst(), line)) {
            return false;
        }
        this.history.removeLast();
        return true;
    }

    public final List<Pair<ReplCodeLine, T>> reset() {
        ArrayDeque<Pair<CompiledReplCodeLine, T>> arrayDeque = this.history;
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(arrayDeque, 10));
        Iterator<T> it = arrayDeque.iterator();
        while (it.hasNext()) {
            Pair pair = (Pair) it.next();
            arrayList.add(new Pair(((CompiledReplCodeLine) pair.getFirst()).getSource(), pair.getSecond()));
        }
        ArrayList arrayList2 = arrayList;
        this.history.clear();
        return arrayList2;
    }

    public final List<Pair<ReplCodeLine, T>> resetToLine(int lineNumber) {
        CompiledReplCodeLine first;
        ReplCodeLine source;
        ArrayList arrayList = new ArrayList();
        while (true) {
            Pair<CompiledReplCodeLine, T> pairPeekLast = this.history.peekLast();
            if (((pairPeekLast == null || (first = pairPeekLast.getFirst()) == null || (source = first.getSource()) == null) ? -1 : source.getNo()) > lineNumber) {
                Pair<CompiledReplCodeLine, T> pairRemoveLast = this.history.removeLast();
                arrayList.add(new Pair(pairRemoveLast.getFirst().getSource(), pairRemoveLast.getSecond()));
            } else {
                return CollectionsKt.reversed(arrayList);
            }
        }
    }

    public final List<Pair<ReplCodeLine, T>> resetToLine(ReplCodeLine line) {
        Intrinsics.checkNotNullParameter(line, "line");
        return resetToLine(line.getNo());
    }

    public final List<Pair<CompiledReplCodeLine, T>> resetToLine(CompiledReplCodeLine line) {
        CompiledReplCodeLine first;
        ReplCodeLine source;
        Intrinsics.checkNotNullParameter(line, "line");
        ArrayList arrayList = new ArrayList();
        while (true) {
            Pair<CompiledReplCodeLine, T> pairPeekLast = this.history.peekLast();
            if (((pairPeekLast == null || (first = pairPeekLast.getFirst()) == null || (source = first.getSource()) == null) ? -1 : source.getNo()) > line.getSource().getNo()) {
                arrayList.add(this.history.removeLast());
            } else {
                return CollectionsKt.reversed(arrayList);
            }
        }
    }

    public final boolean contains(ReplCodeLine line) {
        Intrinsics.checkNotNullParameter(line, "line");
        ArrayDeque<Pair<CompiledReplCodeLine, T>> arrayDeque = this.history;
        if ((arrayDeque instanceof Collection) && arrayDeque.isEmpty()) {
            return false;
        }
        Iterator<T> it = arrayDeque.iterator();
        while (it.hasNext()) {
            if (Intrinsics.areEqual(((CompiledReplCodeLine) ((Pair) it.next()).getFirst()).getSource(), line)) {
                return true;
            }
        }
        return false;
    }

    public final boolean contains(CompiledReplCodeLine line) {
        Intrinsics.checkNotNullParameter(line, "line");
        ArrayDeque<Pair<CompiledReplCodeLine, T>> arrayDeque = this.history;
        if ((arrayDeque instanceof Collection) && arrayDeque.isEmpty()) {
            return false;
        }
        Iterator<T> it = arrayDeque.iterator();
        while (it.hasNext()) {
            if (Intrinsics.areEqual(((Pair) it.next()).getFirst(), line)) {
                return true;
            }
        }
        return false;
    }

    public final Pair<CompiledReplCodeLine, T> lastItem() {
        return this.history.peekLast();
    }

    public final CompiledReplCodeLine lastCodeLine() {
        Pair<CompiledReplCodeLine, T> pairLastItem = lastItem();
        if (pairLastItem != null) {
            return pairLastItem.getFirst();
        }
        return null;
    }

    public final T lastValue() {
        Pair<CompiledReplCodeLine, T> pairLastItem = lastItem();
        if (pairLastItem != null) {
            return pairLastItem.getSecond();
        }
        return null;
    }

    public final boolean checkHistoryIsInSync(List<ReplCodeLine> compareHistory) {
        return firstMismatchingHistory(compareHistory) == null;
    }

    public final Integer firstMismatchingHistory(List<ReplCodeLine> compareHistory) {
        T next;
        ReplCodeLine replCodeLine;
        Pair pair;
        if (compareHistory == null) {
            return null;
        }
        if (compareHistory.size() != this.history.size()) {
            return compareHistory.size() > this.history.size() ? Integer.valueOf(compareHistory.get(this.history.size()).getNo()) : Integer.valueOf(((CompiledReplCodeLine) ((Pair) CollectionsKt.toList(this.history).get(compareHistory.size())).getFirst()).getSource().getNo());
        }
        Iterator<T> it = CollectionsKt.zip(this.history, compareHistory).iterator();
        do {
            if (!it.hasNext()) {
                next = (T) null;
                break;
            }
            next = it.next();
            pair = (Pair) next;
        } while (Intrinsics.areEqual(((CompiledReplCodeLine) ((Pair) pair.getFirst()).getFirst()).getSource(), pair.getSecond()));
        Pair pair2 = next;
        if (pair2 == null || (replCodeLine = (ReplCodeLine) pair2.getSecond()) == null) {
            return null;
        }
        return Integer.valueOf(replCodeLine.getNo());
    }

    public final List<ReplCodeLine> copySources() {
        ArrayDeque<Pair<CompiledReplCodeLine, T>> arrayDeque = this.history;
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(arrayDeque, 10));
        Iterator<T> it = arrayDeque.iterator();
        while (it.hasNext()) {
            arrayList.add(((CompiledReplCodeLine) ((Pair) it.next()).getFirst()).getSource());
        }
        return arrayList;
    }

    public final List<T> copyValues() {
        ArrayDeque<Pair<CompiledReplCodeLine, T>> arrayDeque = this.history;
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(arrayDeque, 10));
        Iterator<T> it = arrayDeque.iterator();
        while (it.hasNext()) {
            arrayList.add(((Pair) it.next()).getSecond());
        }
        return arrayList;
    }

    public final List<Pair<CompiledReplCodeLine, T>> copyAll() {
        return CollectionsKt.toList(this.history);
    }
}
