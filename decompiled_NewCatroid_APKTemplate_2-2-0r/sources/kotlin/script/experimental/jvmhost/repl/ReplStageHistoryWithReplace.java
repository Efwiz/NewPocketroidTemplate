package kotlin.script.experimental.jvmhost.repl;

import androidx.exifinterface.media.ExifInterface;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.sequences.Sequence;
import kotlin.sequences.SequencesKt;
import org.apache.xerces.impl.xs.SchemaSymbols;
import org.jetbrains.kotlin.cli.common.repl.BasicReplStageHistory;
import org.jetbrains.kotlin.cli.common.repl.ILineId;
import org.jetbrains.kotlin.cli.common.repl.ReplHistoryRecord;

/* JADX INFO: compiled from: legacyReplEvaluation.kt */
/* JADX INFO: loaded from: classes12.dex */
@Metadata(d1 = {"\u00000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0002\b\u0016\u0018\u0000*\u0004\b\u0000\u0010\u00012\b\u0012\u0004\u0012\u0002H\u00010\u0002B\u000f\u0012\b\b\u0002\u0010\u0003\u001a\u00020\u0004¢\u0006\u0002\u0010\u0005J\u0014\u0010\u0006\u001a\b\u0012\u0004\u0012\u00028\u00000\u00072\u0006\u0010\b\u001a\u00020\tJ\u001b\u0010\n\u001a\u00020\u000b2\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\f\u001a\u00028\u0000¢\u0006\u0002\u0010\rJ\u001b\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\f\u001a\u00028\u0000¢\u0006\u0002\u0010\u0010¨\u0006\u0011"}, d2 = {"Lkotlin/script/experimental/jvmhost/repl/ReplStageHistoryWithReplace;", ExifInterface.GPS_DIRECTION_TRUE, "Lorg/jetbrains/kotlin/cli/common/repl/BasicReplStageHistory;", "lock", "Ljava/util/concurrent/locks/ReentrantReadWriteLock;", "(Ljava/util/concurrent/locks/ReentrantReadWriteLock;)V", "previousItems", "Lkotlin/sequences/Sequence;", "id", "Lorg/jetbrains/kotlin/cli/common/repl/ILineId;", SchemaSymbols.ATTVAL_REPLACE, "", "item", "(Lorg/jetbrains/kotlin/cli/common/repl/ILineId;Ljava/lang/Object;)Z", "replaceOrPush", "", "(Lorg/jetbrains/kotlin/cli/common/repl/ILineId;Ljava/lang/Object;)V", "kotlin-scripting-jvm-host-unshaded"}, k = 1, mv = {1, 9, 0}, xi = 48)
public class ReplStageHistoryWithReplace<T> extends BasicReplStageHistory<T> {
    /* JADX WARN: Multi-variable type inference failed */
    public ReplStageHistoryWithReplace() {
        this(null, 1, 0 == true ? 1 : 0);
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ReplStageHistoryWithReplace(ReentrantReadWriteLock lock) {
        super(lock);
        Intrinsics.checkNotNullParameter(lock, "lock");
    }

    public /* synthetic */ ReplStageHistoryWithReplace(ReentrantReadWriteLock reentrantReadWriteLock, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this((i & 1) != 0 ? new ReentrantReadWriteLock() : reentrantReadWriteLock);
    }

    public final boolean replace(ILineId id2, T item) {
        Intrinsics.checkNotNullParameter(id2, "id");
        ReentrantReadWriteLock lock = getLock();
        ReentrantReadWriteLock.ReadLock lock2 = lock.readLock();
        int i = 0;
        int readHoldCount = lock.getWriteHoldCount() == 0 ? lock.getReadHoldCount() : 0;
        for (int i2 = 0; i2 < readHoldCount; i2++) {
            lock2.unlock();
        }
        ReentrantReadWriteLock.WriteLock writeLock = lock.writeLock();
        writeLock.lock();
        try {
            int size = size();
            for (int i3 = 0; i3 < size; i3++) {
                if (Intrinsics.areEqual(((ReplHistoryRecord) get(i3)).getId(), id2)) {
                    set(i3, new ReplHistoryRecord(id2, item));
                    while (i < readHoldCount) {
                        lock2.lock();
                        i++;
                    }
                    return true;
                }
            }
            for (int i4 = 0; i < readHoldCount; i4 = i + 1) {
                lock2.lock();
            }
            return false;
        } finally {
            while (i < readHoldCount) {
                lock2.lock();
                i++;
            }
            writeLock.unlock();
        }
    }

    public final void replaceOrPush(ILineId id2, T item) {
        Intrinsics.checkNotNullParameter(id2, "id");
        if (replace(id2, item)) {
            return;
        }
        tryResetTo(id2);
        push(id2, item);
    }

    public final Sequence<T> previousItems(final ILineId id2) {
        Intrinsics.checkNotNullParameter(id2, "id");
        return SequencesKt.map(SequencesKt.takeWhile(CollectionsKt.asSequence(this), new Function1<ReplHistoryRecord<? extends T>, Boolean>() { // from class: kotlin.script.experimental.jvmhost.repl.ReplStageHistoryWithReplace.previousItems.1
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final Boolean invoke(ReplHistoryRecord<? extends T> it) {
                Intrinsics.checkNotNullParameter(it, "it");
                return Boolean.valueOf(it.getId().getNo() < id2.getNo());
            }
        }), new Function1<ReplHistoryRecord<? extends T>, T>() { // from class: kotlin.script.experimental.jvmhost.repl.ReplStageHistoryWithReplace.previousItems.2
            @Override // kotlin.jvm.functions.Function1
            public final T invoke(ReplHistoryRecord<? extends T> it) {
                Intrinsics.checkNotNullParameter(it, "it");
                return it.getItem();
            }
        });
    }
}
