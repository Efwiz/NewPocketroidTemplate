package org.jetbrains.kotlin.codegen.inline;

import androidx.exifinterface.media.ExifInterface;
import com.reandroid.dex.sections.Marker;
import java.util.ArrayList;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.codegen.optimization.common.UtilKt;
import org.jetbrains.org.objectweb.asm.tree.LabelNode;
import org.jetbrains.org.objectweb.asm.tree.TryCatchBlockNode;

/* JADX INFO: compiled from: CoveringTryCatchNodeProcessor.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000 \n\u0000\n\u0002\u0010 \n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\u001a&\u0010\u0000\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001\"\u000e\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u0003*\b\u0012\u0004\u0012\u0002H\u00020\u0004\u001a\n\u0010\u0005\u001a\u00020\u0006*\u00020\u0007\u001a\n\u0010\u0005\u001a\u00020\u0006*\u00020\b¨\u0006\t"}, d2 = {"getMeaningfulIntervals", "", ExifInterface.GPS_DIRECTION_TRUE, "Lorg/jetbrains/kotlin/codegen/inline/SplittableInterval;", "Lorg/jetbrains/kotlin/codegen/inline/IntervalMetaInfo;", "isMeaningless", "", "Lorg/jetbrains/kotlin/codegen/inline/Interval;", "Lorg/jetbrains/org/objectweb/asm/tree/TryCatchBlockNode;", Marker.BACKEND}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class CoveringTryCatchNodeProcessorKt {
    public static final boolean isMeaningless(TryCatchBlockNode tryCatchBlockNode) {
        Intrinsics.checkNotNullParameter(tryCatchBlockNode, "<this>");
        LabelNode start = tryCatchBlockNode.start;
        Intrinsics.checkNotNullExpressionValue(start, "start");
        LabelNode end = tryCatchBlockNode.end;
        Intrinsics.checkNotNullExpressionValue(end, "end");
        return isMeaningless(new SimpleInterval(start, end));
    }

    public static final boolean isMeaningless(Interval interval) {
        Intrinsics.checkNotNullParameter(interval, "<this>");
        LabelNode startLabel = interval.getStartLabel();
        LabelNode endLabel = interval.getEndLabel();
        while (!Intrinsics.areEqual(endLabel, startLabel) && !UtilKt.isMeaningful(endLabel)) {
            endLabel = endLabel.getPrevious();
            Intrinsics.checkNotNullExpressionValue(endLabel, "getPrevious(...)");
        }
        return Intrinsics.areEqual(startLabel, endLabel);
    }

    public static final <T extends SplittableInterval<? extends T>> List<T> getMeaningfulIntervals(IntervalMetaInfo<T> intervalMetaInfo) {
        Intrinsics.checkNotNullParameter(intervalMetaInfo, "<this>");
        ArrayList<T> allIntervals = intervalMetaInfo.getAllIntervals();
        ArrayList arrayList = new ArrayList();
        for (Object obj : allIntervals) {
            if (!isMeaningless((SplittableInterval) obj)) {
                arrayList.add(obj);
            }
        }
        return arrayList;
    }
}
