package org.jetbrains.kotlin.codegen.optimization.common;

import androidx.datastore.preferences.protobuf.Internal;
import com.reandroid.dex.sections.Marker;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.collections.IntIterator;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.IntRange;
import org.jetbrains.org.objectweb.asm.tree.AbstractInsnNode;
import org.jetbrains.org.objectweb.asm.tree.InsnList;
import org.jetbrains.org.objectweb.asm.tree.MethodNode;

/* JADX INFO: compiled from: backwardAnalysis.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000\u001a\n\u0000\n\u0002\u0010 \n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u001a2\u0010\u0000\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001\"\u000e\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\f\u0010\u0006\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0007¨\u0006\b"}, d2 = {"analyze", "", "F", "Lorg/jetbrains/kotlin/codegen/optimization/common/VarFrame;", "node", "Lorg/jetbrains/org/objectweb/asm/tree/MethodNode;", "interpreter", "Lorg/jetbrains/kotlin/codegen/optimization/common/BackwardAnalysisInterpreter;", Marker.BACKEND}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class BackwardAnalysisKt {
    /* JADX WARN: Multi-variable type inference failed */
    public static final <F extends VarFrame<F>> List<F> analyze(MethodNode node, BackwardAnalysisInterpreter<F> interpreter) {
        boolean z;
        Intrinsics.checkNotNullParameter(node, "node");
        Intrinsics.checkNotNullParameter(interpreter, "interpreter");
        ControlFlowGraph controlFlowGraphBuild$default = ControlFlowGraph.Companion.build$default(ControlFlowGraph.INSTANCE, node, false, 2, null);
        InsnList insnList = node.instructions;
        IntRange intRange = new IntRange(1, insnList.size());
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(intRange, 10));
        Iterator<Integer> it = intRange.iterator();
        while (it.hasNext()) {
            ((IntIterator) it).nextInt();
            arrayList.add(interpreter.newFrame(node.maxLocals));
        }
        Internal.DoubleList doubleList = (List<F>) CollectionsKt.toMutableList((Collection) arrayList);
        AbstractInsnNode[] array = insnList.toArray();
        do {
            int length = array.length - 1;
            z = false;
            if (length >= 0) {
                while (true) {
                    int i = length - 1;
                    AbstractInsnNode abstractInsnNode = array[length];
                    VarFrame varFrameNewFrame = interpreter.newFrame(node.maxLocals);
                    Intrinsics.checkNotNull(abstractInsnNode);
                    Iterator<Integer> it2 = controlFlowGraphBuild$default.getSuccessorsIndices(abstractInsnNode).iterator();
                    while (it2.hasNext()) {
                        varFrameNewFrame.mergeFrom((VarFrame) doubleList.get(it2.next().intValue()));
                    }
                    if (controlFlowGraphBuild$default.getPredecessorsIndices(abstractInsnNode).size() > 1) {
                        varFrameNewFrame.markControlFlowMerge();
                    }
                    interpreter.def(varFrameNewFrame, abstractInsnNode);
                    interpreter.use(varFrameNewFrame, abstractInsnNode);
                    if (!Intrinsics.areEqual(doubleList.get(length), varFrameNewFrame)) {
                        doubleList.set(length, varFrameNewFrame);
                        z = true;
                    }
                    if (i < 0) {
                        break;
                    }
                    length = i;
                }
            }
        } while (z);
        return doubleList;
    }
}
