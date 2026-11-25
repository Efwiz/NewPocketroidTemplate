package org.jetbrains.kotlin.codegen.inline;

import androidx.exifinterface.media.ExifInterface;
import com.reandroid.dex.sections.Marker;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.codegen.optimization.common.InsnSequence;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.org.objectweb.asm.tree.LabelNode;

/* JADX INFO: compiled from: TryBlockClustering.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000 \n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u001a*\u0010\u0005\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\b0\u00070\u0006\"\b\b\u0000\u0010\b*\u00020\t2\f\u0010\n\u001a\b\u0012\u0004\u0012\u0002H\b0\u0006\"\u0015\u0010\u0000\u001a\u00020\u0001*\u00020\u00028F¢\u0006\u0006\u001a\u0004\b\u0003\u0010\u0004¨\u0006\u000b"}, d2 = {"bodyInstuctions", "Lorg/jetbrains/kotlin/codegen/optimization/common/InsnSequence;", "Lorg/jetbrains/kotlin/codegen/inline/TryCatchBlockNodeInfo;", "getBodyInstuctions", "(Lorg/jetbrains/kotlin/codegen/inline/TryCatchBlockNodeInfo;)Lorg/jetbrains/kotlin/codegen/optimization/common/InsnSequence;", "doClustering", "", "Lorg/jetbrains/kotlin/codegen/inline/TryBlockCluster;", ExifInterface.GPS_DIRECTION_TRUE, "Lorg/jetbrains/kotlin/codegen/inline/IntervalWithHandler;", "blocks", Marker.BACKEND}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class TryBlockClusteringKt {
    public static final InsnSequence getBodyInstuctions(TryCatchBlockNodeInfo tryCatchBlockNodeInfo) {
        Intrinsics.checkNotNullParameter(tryCatchBlockNodeInfo, "<this>");
        return new InsnSequence(tryCatchBlockNodeInfo.getStartLabel(), tryCatchBlockNodeInfo.getEndLabel());
    }

    /* JADX INFO: compiled from: TryBlockClustering.kt */
    @Metadata(d1 = {"\u0000'\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\n\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000*\u0001\u0000\b\u008a\b\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003¢\u0006\u0002\u0010\u0005J\t\u0010\t\u001a\u00020\u0003HÆ\u0003J\t\u0010\n\u001a\u00020\u0003HÆ\u0003J(\u0010\u000b\u001a\b\u0012\u0004\u0012\u00028\u00000\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u0003HÆ\u0001¢\u0006\u0002\u0010\fJ\u0013\u0010\r\u001a\u00020\u000e2\b\u0010\u000f\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u0010\u001a\u00020\u0011HÖ\u0001J\t\u0010\u0012\u001a\u00020\u0013HÖ\u0001R\u0011\u0010\u0004\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\u0007¨\u0006\u0014"}, d2 = {"org/jetbrains/kotlin/codegen/inline/TryBlockClusteringKt$doClustering$TryBlockInterval", "", "startLabel", "Lorg/jetbrains/org/objectweb/asm/tree/LabelNode;", "endLabel", "(Lorg/jetbrains/org/objectweb/asm/tree/LabelNode;Lorg/jetbrains/org/objectweb/asm/tree/LabelNode;)V", "getEndLabel", "()Lorg/jetbrains/org/objectweb/asm/tree/LabelNode;", "getStartLabel", "component1", "component2", ConfigConstants.CONFIG_RENAMELIMIT_COPY, "(Lorg/jetbrains/org/objectweb/asm/tree/LabelNode;Lorg/jetbrains/org/objectweb/asm/tree/LabelNode;)Lorg/jetbrains/kotlin/codegen/inline/TryBlockClusteringKt$doClustering$TryBlockInterval;", Namer.EQUALS_METHOD_NAME, "", "other", "hashCode", "", "toString", "", Marker.BACKEND}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final /* data */ class TryBlockInterval {
        private final LabelNode endLabel;
        private final LabelNode startLabel;

        public static /* synthetic */ TryBlockInterval copy$default(TryBlockInterval tryBlockInterval, LabelNode labelNode, LabelNode labelNode2, int i, Object obj) {
            if ((i & 1) != 0) {
                labelNode = tryBlockInterval.startLabel;
            }
            if ((i & 2) != 0) {
                labelNode2 = tryBlockInterval.endLabel;
            }
            return tryBlockInterval.copy(labelNode, labelNode2);
        }

        /* JADX INFO: renamed from: component1, reason: from getter */
        public final LabelNode getStartLabel() {
            return this.startLabel;
        }

        /* JADX INFO: renamed from: component2, reason: from getter */
        public final LabelNode getEndLabel() {
            return this.endLabel;
        }

        public final TryBlockInterval<T> copy(LabelNode startLabel, LabelNode endLabel) {
            Intrinsics.checkNotNullParameter(startLabel, "startLabel");
            Intrinsics.checkNotNullParameter(endLabel, "endLabel");
            return new TryBlockInterval(startLabel, endLabel);
        }

        public boolean equals(Object other) {
            if (this == other) {
                return true;
            }
            if (!(other instanceof TryBlockInterval)) {
                return false;
            }
            TryBlockInterval tryBlockInterval = (TryBlockInterval) other;
            return Intrinsics.areEqual(this.startLabel, tryBlockInterval.startLabel) && Intrinsics.areEqual(this.endLabel, tryBlockInterval.endLabel);
        }

        public int hashCode() {
            return (this.startLabel.hashCode() * 31) + this.endLabel.hashCode();
        }

        public String toString() {
            return "TryBlockInterval(startLabel=" + this.startLabel + ", endLabel=" + this.endLabel + ')';
        }

        public TryBlockInterval(LabelNode startLabel, LabelNode endLabel) {
            Intrinsics.checkNotNullParameter(startLabel, "startLabel");
            Intrinsics.checkNotNullParameter(endLabel, "endLabel");
            this.startLabel = startLabel;
            this.endLabel = endLabel;
        }

        public final LabelNode getEndLabel() {
            return this.endLabel;
        }

        public final LabelNode getStartLabel() {
            return this.startLabel;
        }
    }

    public static final <T extends IntervalWithHandler> List<TryBlockCluster<T>> doClustering(List<? extends T> blocks) {
        Intrinsics.checkNotNullParameter(blocks, "blocks");
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        Iterator<T> it = blocks.iterator();
        while (it.hasNext()) {
            IntervalWithHandler intervalWithHandler = (IntervalWithHandler) it.next();
            TryBlockInterval tryBlockInterval = new TryBlockInterval(InlineCodegenUtilsKt.firstLabelInChain(intervalWithHandler.getStartLabel()), InlineCodegenUtilsKt.firstLabelInChain(intervalWithHandler.getEndLabel()));
            LinkedHashMap linkedHashMap2 = linkedHashMap;
            Object tryBlockCluster = linkedHashMap2.get(tryBlockInterval);
            if (tryBlockCluster == null) {
                tryBlockCluster = new TryBlockCluster(new ArrayList());
                linkedHashMap2.put(tryBlockInterval, tryBlockCluster);
            }
            ((TryBlockCluster) tryBlockCluster).getBlocks().add(intervalWithHandler);
        }
        Collection collectionValues = linkedHashMap.values();
        Intrinsics.checkNotNullExpressionValue(collectionValues, "<get-values>(...)");
        return CollectionsKt.toList(collectionValues);
    }
}
