package org.jetbrains.kotlin.cli.common.repl;

import androidx.exifinterface.media.ExifInterface;
import java.util.Iterator;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.sequences.Sequence;
import kotlin.sequences.SequencesKt;

/* JADX INFO: compiled from: ReplState.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000*\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000b\n\u0002\b\u0002\u001a<\u0010\u0000\u001a\u001a\u0012\f\u0012\n\u0012\u0004\u0012\u0002H\u0003\u0018\u00010\u0002\u0012\u0006\u0012\u0004\u0018\u00010\u0004\u0018\u00010\u0001\"\u0004\b\u0000\u0010\u0003*\b\u0012\u0004\u0012\u0002H\u00030\u00052\f\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\u00040\u0007\u001aV\u0010\b\u001a\u001a\u0012\f\u0012\n\u0012\u0004\u0012\u0002H\u0003\u0018\u00010\u0002\u0012\u0006\u0012\u0004\u0018\u00010\u0004\u0018\u00010\u0001\"\u0004\b\u0000\u0010\u0003*\b\u0012\u0004\u0012\u0002H\u00030\u00052\f\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\u00040\u00072\u0018\u0010\t\u001a\u0014\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00030\u0002\u0012\u0004\u0012\u00020\u000b0\n\u001aV\u0010\f\u001a\u001a\u0012\f\u0012\n\u0012\u0004\u0012\u0002H\u0003\u0018\u00010\u0002\u0012\u0006\u0012\u0004\u0018\u00010\u0004\u0018\u00010\u0001\"\u0004\b\u0000\u0010\u0003*\b\u0012\u0004\u0012\u0002H\u00030\u00052\f\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\u00040\u00072\u0018\u0010\t\u001a\u0014\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00030\u0002\u0012\u0004\u0012\u00020\u000b0\n¨\u0006\r"}, d2 = {"firstMismatch", "Lkotlin/Pair;", "Lorg/jetbrains/kotlin/cli/common/repl/ReplHistoryRecord;", ExifInterface.GPS_DIRECTION_TRUE, "Lorg/jetbrains/kotlin/cli/common/repl/ILineId;", "Lorg/jetbrains/kotlin/cli/common/repl/IReplStageHistory;", "other", "Lkotlin/sequences/Sequence;", "firstMismatchFiltered", "predicate", "Lkotlin/Function1;", "", "firstMismatchWhile", "cli-common"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class ReplStateKt {
    public static final <T> Pair<ReplHistoryRecord<T>, ILineId> firstMismatch(IReplStageHistory<T> iReplStageHistory, Sequence<? extends ILineId> other) {
        T next;
        Pair pair;
        Intrinsics.checkNotNullParameter(iReplStageHistory, "<this>");
        Intrinsics.checkNotNullParameter(other, "other");
        ReentrantReadWriteLock.ReadLock lock = iReplStageHistory.getLock().readLock();
        lock.lock();
        try {
            Iterator<T> it = SequencesKt.zip(SequencesKt.asSequence(iReplStageHistory.iterator()), other).iterator();
            do {
                if (!it.hasNext()) {
                    next = (T) null;
                    break;
                }
                next = it.next();
                pair = (Pair) next;
            } while (Intrinsics.areEqual(((ReplHistoryRecord) pair.getFirst()).getId(), pair.getSecond()));
            Pair pair2 = next;
            return pair2 != null ? TuplesKt.to(pair2.getFirst(), pair2.getSecond()) : null;
        } finally {
            lock.unlock();
        }
    }

    public static final <T> Pair<ReplHistoryRecord<T>, ILineId> firstMismatchFiltered(IReplStageHistory<T> iReplStageHistory, Sequence<? extends ILineId> other, Function1<? super ReplHistoryRecord<? extends T>, Boolean> predicate) {
        T next;
        Pair pair;
        Intrinsics.checkNotNullParameter(iReplStageHistory, "<this>");
        Intrinsics.checkNotNullParameter(other, "other");
        Intrinsics.checkNotNullParameter(predicate, "predicate");
        ReentrantReadWriteLock.ReadLock lock = iReplStageHistory.getLock().readLock();
        lock.lock();
        try {
            Iterator<T> it = SequencesKt.zip(SequencesKt.filter(SequencesKt.asSequence(iReplStageHistory.iterator()), predicate), other).iterator();
            do {
                if (!it.hasNext()) {
                    next = (T) null;
                    break;
                }
                next = it.next();
                pair = (Pair) next;
            } while (Intrinsics.areEqual(((ReplHistoryRecord) pair.getFirst()).getId(), pair.getSecond()));
            Pair pair2 = next;
            return pair2 != null ? TuplesKt.to(pair2.getFirst(), pair2.getSecond()) : null;
        } finally {
            lock.unlock();
        }
    }

    public static final <T> Pair<ReplHistoryRecord<T>, ILineId> firstMismatchWhile(IReplStageHistory<T> iReplStageHistory, Sequence<? extends ILineId> other, Function1<? super ReplHistoryRecord<? extends T>, Boolean> predicate) {
        T next;
        Pair pair;
        Intrinsics.checkNotNullParameter(iReplStageHistory, "<this>");
        Intrinsics.checkNotNullParameter(other, "other");
        Intrinsics.checkNotNullParameter(predicate, "predicate");
        ReentrantReadWriteLock.ReadLock lock = iReplStageHistory.getLock().readLock();
        lock.lock();
        try {
            Iterator<T> it = SequencesKt.zip(SequencesKt.takeWhile(SequencesKt.asSequence(iReplStageHistory.iterator()), predicate), other).iterator();
            do {
                if (!it.hasNext()) {
                    next = (T) null;
                    break;
                }
                next = it.next();
                pair = (Pair) next;
            } while (Intrinsics.areEqual(((ReplHistoryRecord) pair.getFirst()).getId(), pair.getSecond()));
            Pair pair2 = next;
            return pair2 != null ? TuplesKt.to(pair2.getFirst(), pair2.getSecond()) : null;
        } finally {
            lock.unlock();
        }
    }
}
