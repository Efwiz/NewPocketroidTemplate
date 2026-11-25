package org.jetbrains.kotlin.codegen.inline;

import androidx.exifinterface.media.ExifInterface;
import com.reandroid.dex.sections.Marker;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.codegen.inline.Interval;

/* JADX INFO: compiled from: TryBlockClustering.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\b\u0018\u0000*\n\b\u0000\u0010\u0001 \u0001*\u00020\u00022\u00020\u0003B\u0015\u0012\u0006\u0010\u0004\u001a\u00028\u0000\u0012\u0006\u0010\u0005\u001a\u00028\u0000¢\u0006\u0002\u0010\u0006R\u0013\u0010\u0005\u001a\u00028\u0000¢\u0006\n\n\u0002\u0010\t\u001a\u0004\b\u0007\u0010\bR\u0013\u0010\u0004\u001a\u00028\u0000¢\u0006\n\n\u0002\u0010\t\u001a\u0004\b\n\u0010\b¨\u0006\u000b"}, d2 = {"Lorg/jetbrains/kotlin/codegen/inline/SplitPair;", ExifInterface.GPS_DIRECTION_TRUE, "Lorg/jetbrains/kotlin/codegen/inline/Interval;", "", "patchedPart", "newPart", "(Lorg/jetbrains/kotlin/codegen/inline/Interval;Lorg/jetbrains/kotlin/codegen/inline/Interval;)V", "getNewPart", "()Lorg/jetbrains/kotlin/codegen/inline/Interval;", "Lorg/jetbrains/kotlin/codegen/inline/Interval;", "getPatchedPart", Marker.BACKEND}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class SplitPair<T extends Interval> {
    private final T newPart;
    private final T patchedPart;

    public SplitPair(T patchedPart, T newPart) {
        Intrinsics.checkNotNullParameter(patchedPart, "patchedPart");
        Intrinsics.checkNotNullParameter(newPart, "newPart");
        this.patchedPart = patchedPart;
        this.newPart = newPart;
    }

    public final T getNewPart() {
        return this.newPart;
    }

    public final T getPatchedPart() {
        return this.patchedPart;
    }
}
