package org.jetbrains.kotlin.codegen.inline;

import androidx.exifinterface.media.ExifInterface;
import com.reandroid.dex.sections.Marker;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.codegen.inline.SplittableInterval;
import org.jetbrains.kotlin.com.google.common.collect.LinkedListMultimap;
import org.jetbrains.kotlin.metadata.jvm.deserialization.JvmProtoBufUtil;
import org.jetbrains.org.objectweb.asm.tree.LabelNode;

/* JADX INFO: compiled from: CoveringTryCatchNodeProcessor.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000`\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010#\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0010!\n\u0002\u0010 \n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\t\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\u0018\u0000*\u000e\b\u0000\u0010\u0001*\b\u0012\u0004\u0012\u0002H\u00010\u00022\u00020\u0003B\r\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\u0013\u0010\u0017\u001a\u00020\u00182\u0006\u0010\u0019\u001a\u00028\u0000¢\u0006\u0002\u0010\u001aJ<\u0010\u001b\u001a&\u0012\f\u0012\n \u0012*\u0004\u0018\u00018\u00008\u0000 \u0012*\u0012\u0012\f\u0012\n \u0012*\u0004\u0018\u00018\u00008\u0000\u0018\u00010\u001d0\u001c2\u0006\u0010\u001e\u001a\u00020\u00112\u0006\u0010\u001f\u001a\u00020 H\u0002J\u0016\u0010!\u001a\u00020\u00182\u0006\u0010\u001e\u001a\u00020\u00112\u0006\u0010\"\u001a\u00020 J\u001d\u0010#\u001a\u00020\u00182\u0006\u0010$\u001a\u00020\u00112\u0006\u0010%\u001a\u00028\u0000H\u0002¢\u0006\u0002\u0010&J\u001d\u0010'\u001a\u00020\u00182\u0006\u0010(\u001a\u00020\u00112\u0006\u0010%\u001a\u00028\u0000H\u0002¢\u0006\u0002\u0010&J)\u0010)\u001a\b\u0012\u0004\u0012\u00028\u00000*2\u0006\u0010+\u001a\u00028\u00002\u0006\u0010,\u001a\u00020-2\u0006\u0010.\u001a\u00020 ¢\u0006\u0002\u0010/J\u0016\u00100\u001a\u00020\u00182\u0006\u0010,\u001a\u00020-2\u0006\u0010.\u001a\u00020 J)\u00101\u001a\b\u0012\u0004\u0012\u00028\u00000*2\u0006\u0010+\u001a\u00028\u00002\u0006\u0010,\u001a\u00020-2\u0006\u0010.\u001a\u00020 ¢\u0006\u0002\u0010/J\"\u00102\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00028\u00000*0\u001d2\u0006\u0010,\u001a\u00020-2\u0006\u0010.\u001a\u00020 R\u0017\u0010\u0007\u001a\b\u0012\u0004\u0012\u00028\u00000\b¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u0017\u0010\u000b\u001a\b\u0012\u0004\u0012\u00028\u00000\f¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000eRQ\u0010\u000f\u001aB\u0012\f\u0012\n \u0012*\u0004\u0018\u00010\u00110\u0011\u0012\f\u0012\n \u0012*\u0004\u0018\u00018\u00008\u0000 \u0012* \u0012\f\u0012\n \u0012*\u0004\u0018\u00010\u00110\u0011\u0012\f\u0012\n \u0012*\u0004\u0018\u00018\u00008\u0000\u0018\u00010\u00100\u0010¢\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u0014RQ\u0010\u0015\u001aB\u0012\f\u0012\n \u0012*\u0004\u0018\u00010\u00110\u0011\u0012\f\u0012\n \u0012*\u0004\u0018\u00018\u00008\u0000 \u0012* \u0012\f\u0012\n \u0012*\u0004\u0018\u00010\u00110\u0011\u0012\f\u0012\n \u0012*\u0004\u0018\u00018\u00008\u0000\u0018\u00010\u00100\u0010¢\u0006\b\n\u0000\u001a\u0004\b\u0016\u0010\u0014R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000¨\u00063"}, d2 = {"Lorg/jetbrains/kotlin/codegen/inline/IntervalMetaInfo;", ExifInterface.GPS_DIRECTION_TRUE, "Lorg/jetbrains/kotlin/codegen/inline/SplittableInterval;", "", "processor", "Lorg/jetbrains/kotlin/codegen/inline/CoveringTryCatchNodeProcessor;", "(Lorg/jetbrains/kotlin/codegen/inline/CoveringTryCatchNodeProcessor;)V", "allIntervals", "Ljava/util/ArrayList;", "getAllIntervals", "()Ljava/util/ArrayList;", "currentIntervals", "", "getCurrentIntervals", "()Ljava/util/Set;", "intervalEnds", "Lorg/jetbrains/kotlin/com/google/common/collect/LinkedListMultimap;", "Lorg/jetbrains/org/objectweb/asm/tree/LabelNode;", JvmProtoBufUtil.PLATFORM_TYPE_ID, "getIntervalEnds", "()Lcom/google/common/collect/LinkedListMultimap;", "intervalStarts", "getIntervalStarts", "addNewInterval", "", "newInfo", "(Lorg/jetbrains/kotlin/codegen/inline/SplittableInterval;)V", "getInterval", "", "", "curIns", "isOpen", "", "processCurrent", "directOrder", "remapEndLabel", "oldEnd", "remapped", "(Lorg/jetbrains/org/objectweb/asm/tree/LabelNode;Lorg/jetbrains/kotlin/codegen/inline/SplittableInterval;)V", "remapStartLabel", "oldStart", "split", "Lorg/jetbrains/kotlin/codegen/inline/SplitPair;", "interval", "by", "Lorg/jetbrains/kotlin/codegen/inline/Interval;", "keepStart", "(Lorg/jetbrains/kotlin/codegen/inline/SplittableInterval;Lorg/jetbrains/kotlin/codegen/inline/Interval;Z)Lorg/jetbrains/kotlin/codegen/inline/SplitPair;", "splitAndRemoveCurrentIntervals", "splitAndRemoveIntervalFromCurrents", "splitCurrentIntervals", Marker.BACKEND}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class IntervalMetaInfo<T extends SplittableInterval<? extends T>> {
    private final ArrayList<T> allIntervals;
    private final Set<T> currentIntervals;
    private final LinkedListMultimap<LabelNode, T> intervalEnds;
    private final LinkedListMultimap<LabelNode, T> intervalStarts;
    private final CoveringTryCatchNodeProcessor processor;

    public IntervalMetaInfo(CoveringTryCatchNodeProcessor processor) {
        Intrinsics.checkNotNullParameter(processor, "processor");
        this.processor = processor;
        this.intervalStarts = LinkedListMultimap.create();
        this.intervalEnds = LinkedListMultimap.create();
        this.allIntervals = new ArrayList<>();
        this.currentIntervals = new LinkedHashSet();
    }

    public final LinkedListMultimap<LabelNode, T> getIntervalStarts() {
        return this.intervalStarts;
    }

    public final LinkedListMultimap<LabelNode, T> getIntervalEnds() {
        return this.intervalEnds;
    }

    public final ArrayList<T> getAllIntervals() {
        return this.allIntervals;
    }

    public final Set<T> getCurrentIntervals() {
        return this.currentIntervals;
    }

    public final void addNewInterval(T newInfo) {
        Intrinsics.checkNotNullParameter(newInfo, "newInfo");
        newInfo.verify(this.processor);
        this.intervalStarts.put(newInfo.getStartLabel(), newInfo);
        this.intervalEnds.put(newInfo.getEndLabel(), newInfo);
        this.allIntervals.add(newInfo);
    }

    private final void remapStartLabel(LabelNode oldStart, T remapped) {
        remapped.verify(this.processor);
        this.intervalStarts.remove(oldStart, remapped);
        this.intervalStarts.put(remapped.getStartLabel(), remapped);
    }

    private final void remapEndLabel(LabelNode oldEnd, T remapped) {
        remapped.verify(this.processor);
        this.intervalEnds.remove(oldEnd, remapped);
        this.intervalEnds.put(remapped.getEndLabel(), remapped);
    }

    public final List<SplitPair<T>> splitCurrentIntervals(Interval by, boolean keepStart) {
        Intrinsics.checkNotNullParameter(by, "by");
        Set<T> set = this.currentIntervals;
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(set, 10));
        Iterator<T> it = set.iterator();
        while (it.hasNext()) {
            arrayList.add(split((SplittableInterval) it.next(), by, keepStart));
        }
        return arrayList;
    }

    public final void splitAndRemoveCurrentIntervals(Interval by, boolean keepStart) {
        Intrinsics.checkNotNullParameter(by, "by");
        Iterator it = CollectionsKt.toList(this.currentIntervals).iterator();
        while (it.hasNext()) {
            splitAndRemoveIntervalFromCurrents((SplittableInterval) it.next(), by, keepStart);
        }
    }

    public final void processCurrent(LabelNode curIns, boolean directOrder) {
        Intrinsics.checkNotNullParameter(curIns, "curIns");
        List<T> interval = getInterval(curIns, directOrder);
        Intrinsics.checkNotNullExpressionValue(interval, "getInterval(...)");
        for (T t : interval) {
            Set<T> set = this.currentIntervals;
            Intrinsics.checkNotNull(t);
            set.add((T) t);
        }
        List<T> interval2 = getInterval(curIns, !directOrder);
        Intrinsics.checkNotNullExpressionValue(interval2, "getInterval(...)");
        Iterator<T> it = interval2.iterator();
        while (it.hasNext()) {
            this.currentIntervals.remove((SplittableInterval) it.next());
        }
    }

    public final SplitPair<T> split(T interval, Interval by, boolean keepStart) {
        Intrinsics.checkNotNullParameter(interval, "interval");
        Intrinsics.checkNotNullParameter(by, "by");
        SplitPair<T> splitPairSplit = interval.split(by, keepStart);
        if (!keepStart) {
            remapStartLabel(((SplittableInterval) splitPairSplit.getNewPart()).getStartLabel(), (SplittableInterval) splitPairSplit.getPatchedPart());
        } else {
            remapEndLabel(((SplittableInterval) splitPairSplit.getNewPart()).getEndLabel(), (SplittableInterval) splitPairSplit.getPatchedPart());
        }
        addNewInterval((SplittableInterval) splitPairSplit.getNewPart());
        return splitPairSplit;
    }

    public final SplitPair<T> splitAndRemoveIntervalFromCurrents(T interval, Interval by, boolean keepStart) {
        Intrinsics.checkNotNullParameter(interval, "interval");
        Intrinsics.checkNotNullParameter(by, "by");
        SplitPair<T> splitPairSplit = split(interval, by, keepStart);
        this.currentIntervals.remove(splitPairSplit.getPatchedPart());
        return splitPairSplit;
    }

    private final List<T> getInterval(LabelNode curIns, boolean isOpen) {
        return (isOpen ? this.intervalStarts : this.intervalEnds).get(curIns);
    }
}
