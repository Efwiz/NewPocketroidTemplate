package org.jetbrains.kotlin.codegen.inline;

import androidx.exifinterface.media.ExifInterface;
import com.reandroid.dex.sections.Marker;
import kotlin.Metadata;
import org.jetbrains.kotlin.codegen.inline.Interval;

/* JADX INFO: compiled from: TryBlockClustering.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\bf\u0018\u0000*\n\b\u0000\u0010\u0001 \u0001*\u00020\u00022\u00020\u0002J\u001e\u0010\u0003\u001a\b\u0012\u0004\u0012\u00028\u00000\u00042\u0006\u0010\u0005\u001a\u00020\u00022\u0006\u0010\u0006\u001a\u00020\u0007H&ø\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001¨\u0006\bÀ\u0006\u0001"}, d2 = {"Lorg/jetbrains/kotlin/codegen/inline/SplittableInterval;", ExifInterface.GPS_DIRECTION_TRUE, "Lorg/jetbrains/kotlin/codegen/inline/Interval;", "split", "Lorg/jetbrains/kotlin/codegen/inline/SplitPair;", "splitBy", "keepStart", "", Marker.BACKEND}, k = 1, mv = {1, 9, 0}, xi = 48)
public interface SplittableInterval<T extends Interval> extends Interval {
    SplitPair<T> split(Interval splitBy, boolean keepStart);
}
