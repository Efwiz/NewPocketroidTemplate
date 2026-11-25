package org.jetbrains.kotlin.cli.common.repl;

import androidx.exifinterface.media.ExifInterface;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.bytebuddy.implementation.auxiliary.TypeProxy;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.js.translate.context.Namer;

/* JADX INFO: compiled from: ReplState.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\f\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u0000*\u0006\b\u0000\u0010\u0001 \u00012\u00020\u0002B\u0015\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\u0006\u0010\u0005\u001a\u00028\u0000¢\u0006\u0002\u0010\u0006J\t\u0010\f\u001a\u00020\u0004HÆ\u0003J\u000e\u0010\r\u001a\u00028\u0000HÆ\u0003¢\u0006\u0002\u0010\nJ(\u0010\u000e\u001a\b\u0012\u0004\u0012\u00028\u00000\u00002\b\b\u0002\u0010\u0003\u001a\u00020\u00042\b\b\u0002\u0010\u0005\u001a\u00028\u0000HÆ\u0001¢\u0006\u0002\u0010\u000fJ\u0013\u0010\u0010\u001a\u00020\u00112\b\u0010\u0012\u001a\u0004\u0018\u00010\u0002HÖ\u0003J\t\u0010\u0013\u001a\u00020\u0014HÖ\u0001J\t\u0010\u0015\u001a\u00020\u0016HÖ\u0001R\u0011\u0010\u0003\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0013\u0010\u0005\u001a\u00028\u0000¢\u0006\n\n\u0002\u0010\u000b\u001a\u0004\b\t\u0010\n¨\u0006\u0017"}, d2 = {"Lorg/jetbrains/kotlin/cli/common/repl/ReplHistoryRecord;", ExifInterface.GPS_DIRECTION_TRUE, "", "id", "Lorg/jetbrains/kotlin/cli/common/repl/ILineId;", "item", "(Lorg/jetbrains/kotlin/cli/common/repl/ILineId;Ljava/lang/Object;)V", "getId", "()Lorg/jetbrains/kotlin/cli/common/repl/ILineId;", "getItem", "()Ljava/lang/Object;", TypeProxy.SilentConstruction.Appender.JAVA_LANG_OBJECT_DESCRIPTOR, "component1", "component2", ConfigConstants.CONFIG_RENAMELIMIT_COPY, "(Lorg/jetbrains/kotlin/cli/common/repl/ILineId;Ljava/lang/Object;)Lorg/jetbrains/kotlin/cli/common/repl/ReplHistoryRecord;", Namer.EQUALS_METHOD_NAME, "", "other", "hashCode", "", "toString", "", "cli-common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final /* data */ class ReplHistoryRecord<T> {
    private final ILineId id;
    private final T item;

    /* JADX WARN: Multi-variable type inference failed */
    public static /* synthetic */ ReplHistoryRecord copy$default(ReplHistoryRecord replHistoryRecord, ILineId iLineId, Object obj, int i, Object obj2) {
        if ((i & 1) != 0) {
            iLineId = replHistoryRecord.id;
        }
        if ((i & 2) != 0) {
            obj = replHistoryRecord.item;
        }
        return replHistoryRecord.copy(iLineId, obj);
    }

    /* JADX INFO: renamed from: component1, reason: from getter */
    public final ILineId getId() {
        return this.id;
    }

    public final T component2() {
        return this.item;
    }

    public final ReplHistoryRecord<T> copy(ILineId id2, T item) {
        Intrinsics.checkNotNullParameter(id2, "id");
        return new ReplHistoryRecord<>(id2, item);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof ReplHistoryRecord)) {
            return false;
        }
        ReplHistoryRecord replHistoryRecord = (ReplHistoryRecord) other;
        return Intrinsics.areEqual(this.id, replHistoryRecord.id) && Intrinsics.areEqual(this.item, replHistoryRecord.item);
    }

    public int hashCode() {
        int iHashCode = this.id.hashCode() * 31;
        T t = this.item;
        return iHashCode + (t == null ? 0 : t.hashCode());
    }

    public String toString() {
        return "ReplHistoryRecord(id=" + this.id + ", item=" + this.item + ')';
    }

    public ReplHistoryRecord(ILineId id2, T t) {
        Intrinsics.checkNotNullParameter(id2, "id");
        this.id = id2;
        this.item = t;
    }

    public final ILineId getId() {
        return this.id;
    }

    public final T getItem() {
        return this.item;
    }
}
