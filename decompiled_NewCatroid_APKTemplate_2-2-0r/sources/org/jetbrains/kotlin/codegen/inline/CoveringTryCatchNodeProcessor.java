package org.jetbrains.kotlin.codegen.inline;

import com.reandroid.dex.sections.Marker;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.codegen.coroutines.CoroutineCodegenUtilKt;
import org.jetbrains.org.objectweb.asm.tree.AbstractInsnNode;
import org.jetbrains.org.objectweb.asm.tree.IincInsnNode;
import org.jetbrains.org.objectweb.asm.tree.LabelNode;
import org.jetbrains.org.objectweb.asm.tree.MethodNode;
import org.jetbrains.org.objectweb.asm.tree.VarInsnNode;

/* JADX INFO: compiled from: CoveringTryCatchNodeProcessor.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000R\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\b&\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0014\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\u000f0\u00122\u0006\u0010\u0013\u001a\u00020\u0014J\u0014\u0010\u0015\u001a\b\u0012\u0004\u0012\u00020\u000f0\u00122\u0006\u0010\u0013\u001a\u00020\u0014J\u0010\u0010\u0016\u001a\u00020\u00032\u0006\u0010\u0017\u001a\u00020\u0018H&J\u0018\u0010\u0019\u001a\u00020\u001a2\u0006\u0010\u001b\u001a\u00020\u00182\u0006\u0010\u001c\u001a\u00020\u001dH\u0016J\u001a\u0010\u001e\u001a\b\u0012\u0004\u0012\u00020\u000f0\u00122\f\u0010\u001f\u001a\b\u0012\u0004\u0012\u00020\u000f0\u0012J\u000e\u0010 \u001a\u00020\u001a2\u0006\u0010!\u001a\u00020\"J\u000e\u0010#\u001a\u00020\u001a2\u0006\u0010!\u001a\u00020\"R\u0017\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u001e\u0010\u000b\u001a\u00020\u00032\u0006\u0010\n\u001a\u00020\u0003@BX\u0086\u000e¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR\u0017\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u000f0\u0006¢\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\t¨\u0006$"}, d2 = {"Lorg/jetbrains/kotlin/codegen/inline/CoveringTryCatchNodeProcessor;", "", "parameterSize", "", "(I)V", "localVarsMetaInfo", "Lorg/jetbrains/kotlin/codegen/inline/IntervalMetaInfo;", "Lorg/jetbrains/kotlin/codegen/inline/LocalVarNodeWrapper;", "getLocalVarsMetaInfo", "()Lorg/jetbrains/kotlin/codegen/inline/IntervalMetaInfo;", "<set-?>", "nextFreeLocalIndex", "getNextFreeLocalIndex", "()I", "tryBlocksMetaInfo", "Lorg/jetbrains/kotlin/codegen/inline/TryCatchBlockNodeInfo;", "getTryBlocksMetaInfo", "getEndNodes", "", CoroutineCodegenUtilKt.COROUTINE_LABEL_FIELD_NAME, "Lorg/jetbrains/org/objectweb/asm/tree/LabelNode;", "getStartNodes", "instructionIndex", "inst", "Lorg/jetbrains/org/objectweb/asm/tree/AbstractInsnNode;", "processInstruction", "", "curInstr", "directOrder", "", "sortTryCatchBlocks", "intervals", "substituteLocalVarTable", "node", "Lorg/jetbrains/org/objectweb/asm/tree/MethodNode;", "substituteTryBlockNodes", Marker.BACKEND}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class CoveringTryCatchNodeProcessor {
    private int nextFreeLocalIndex;
    private final IntervalMetaInfo<TryCatchBlockNodeInfo> tryBlocksMetaInfo = new IntervalMetaInfo<>(this);
    private final IntervalMetaInfo<LocalVarNodeWrapper> localVarsMetaInfo = new IntervalMetaInfo<>(this);

    public abstract int instructionIndex(AbstractInsnNode inst);

    public CoveringTryCatchNodeProcessor(int i) {
        this.nextFreeLocalIndex = i;
    }

    public final IntervalMetaInfo<TryCatchBlockNodeInfo> getTryBlocksMetaInfo() {
        return this.tryBlocksMetaInfo;
    }

    public final IntervalMetaInfo<LocalVarNodeWrapper> getLocalVarsMetaInfo() {
        return this.localVarsMetaInfo;
    }

    public final int getNextFreeLocalIndex() {
        return this.nextFreeLocalIndex;
    }

    public final List<TryCatchBlockNodeInfo> getStartNodes(LabelNode label) {
        Intrinsics.checkNotNullParameter(label, "label");
        List<TryCatchBlockNodeInfo> list = this.tryBlocksMetaInfo.getIntervalStarts().get(label);
        Intrinsics.checkNotNullExpressionValue(list, "get(...)");
        return list;
    }

    public final List<TryCatchBlockNodeInfo> getEndNodes(LabelNode label) {
        Intrinsics.checkNotNullParameter(label, "label");
        List<TryCatchBlockNodeInfo> list = this.tryBlocksMetaInfo.getIntervalEnds().get(label);
        Intrinsics.checkNotNullExpressionValue(list, "get(...)");
        return list;
    }

    public void processInstruction(AbstractInsnNode curInstr, boolean directOrder) {
        Intrinsics.checkNotNullParameter(curInstr, "curInstr");
        boolean z = curInstr instanceof VarInsnNode;
        if (z || (curInstr instanceof IincInsnNode)) {
            this.nextFreeLocalIndex = Math.max(this.nextFreeLocalIndex, (z ? ((VarInsnNode) curInstr).var : ((IincInsnNode) curInstr).var) + InlineCodegenUtilsKt.getLoadStoreArgSize(curInstr.getOpcode()));
        }
        if (curInstr instanceof LabelNode) {
            LabelNode labelNode = (LabelNode) curInstr;
            this.tryBlocksMetaInfo.processCurrent(labelNode, directOrder);
            this.localVarsMetaInfo.processCurrent(labelNode, directOrder);
        }
    }

    public final List<TryCatchBlockNodeInfo> sortTryCatchBlocks(List<TryCatchBlockNodeInfo> intervals) {
        Intrinsics.checkNotNullParameter(intervals, "intervals");
        Collections.sort(intervals, new Comparator() { // from class: org.jetbrains.kotlin.codegen.inline.CoveringTryCatchNodeProcessor$$ExternalSyntheticLambda0
            @Override // java.util.Comparator
            public final int compare(Object obj, Object obj2) {
                return CoveringTryCatchNodeProcessor.sortTryCatchBlocks$lambda$1(this.f$0, (TryCatchBlockNodeInfo) obj, (TryCatchBlockNodeInfo) obj2);
            }
        });
        return intervals;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final int sortTryCatchBlocks$lambda$1(CoveringTryCatchNodeProcessor this$0, TryCatchBlockNodeInfo t1, TryCatchBlockNodeInfo t2) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNullParameter(t1, "t1");
        Intrinsics.checkNotNullParameter(t2, "t2");
        int iInstructionIndex = this$0.instructionIndex(t1.getHandler()) - this$0.instructionIndex(t2.getHandler());
        if (iInstructionIndex != 0) {
            return iInstructionIndex;
        }
        int iInstructionIndex2 = this$0.instructionIndex(t1.getStartLabel()) - this$0.instructionIndex(t2.getStartLabel());
        return iInstructionIndex2 == 0 ? this$0.instructionIndex(t1.getEndLabel()) - this$0.instructionIndex(t2.getEndLabel()) : iInstructionIndex2;
    }

    public final void substituteTryBlockNodes(MethodNode node) {
        Intrinsics.checkNotNullParameter(node, "node");
        node.tryCatchBlocks.clear();
        sortTryCatchBlocks(this.tryBlocksMetaInfo.getAllIntervals());
        Iterator it = CoveringTryCatchNodeProcessorKt.getMeaningfulIntervals(this.tryBlocksMetaInfo).iterator();
        while (it.hasNext()) {
            node.tryCatchBlocks.add(((TryCatchBlockNodeInfo) it.next()).getNode());
        }
    }

    public final void substituteLocalVarTable(MethodNode node) {
        Intrinsics.checkNotNullParameter(node, "node");
        node.localVariables.clear();
        Iterator it = CoveringTryCatchNodeProcessorKt.getMeaningfulIntervals(this.localVarsMetaInfo).iterator();
        while (it.hasNext()) {
            node.localVariables.add(((LocalVarNodeWrapper) it.next()).getNode());
        }
    }
}
