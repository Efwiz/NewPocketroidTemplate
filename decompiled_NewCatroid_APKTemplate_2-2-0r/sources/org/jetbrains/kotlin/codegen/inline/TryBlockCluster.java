package org.jetbrains.kotlin.codegen.inline;

import androidx.exifinterface.media.ExifInterface;
import com.reandroid.dex.sections.Marker;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.codegen.inline.IntervalWithHandler;

/* JADX INFO: compiled from: TryBlockClustering.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010!\n\u0002\b\u0007\u0018\u0000*\b\b\u0000\u0010\u0001*\u00020\u00022\u00020\u0003B\u0013\u0012\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00028\u00000\u0005¢\u0006\u0002\u0010\u0006R\u0017\u0010\u0004\u001a\b\u0012\u0004\u0012\u00028\u00000\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0013\u0010\t\u001a\u0004\u0018\u00018\u00008F¢\u0006\u0006\u001a\u0004\b\n\u0010\u000b¨\u0006\f"}, d2 = {"Lorg/jetbrains/kotlin/codegen/inline/TryBlockCluster;", ExifInterface.GPS_DIRECTION_TRUE, "Lorg/jetbrains/kotlin/codegen/inline/IntervalWithHandler;", "", "blocks", "", "(Ljava/util/List;)V", "getBlocks", "()Ljava/util/List;", "defaultHandler", "getDefaultHandler", "()Lorg/jetbrains/kotlin/codegen/inline/IntervalWithHandler;", Marker.BACKEND}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class TryBlockCluster<T extends IntervalWithHandler> {
    private final List<T> blocks;

    public TryBlockCluster(List<T> blocks) {
        Intrinsics.checkNotNullParameter(blocks, "blocks");
        this.blocks = blocks;
    }

    public final List<T> getBlocks() {
        return this.blocks;
    }

    public final T getDefaultHandler() {
        Object next;
        Iterator<T> it = this.blocks.iterator();
        while (it.hasNext()) {
            next = it.next();
            if (((IntervalWithHandler) next).getType() == null) {
                return (T) next;
            }
        }
        next = null;
        return (T) next;
    }
}
