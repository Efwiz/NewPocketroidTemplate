package org.jetbrains.kotlin.codegen.coroutines;

import com.reandroid.dex.sections.Marker;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.collections.ArraysKt;
import kotlin.collections.CollectionsKt;
import kotlin.collections.SetsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.sequences.SequencesKt;
import org.jetbrains.kotlin.codegen.optimization.common.UtilKt;
import org.jetbrains.kotlin.codegen.optimization.transformer.MethodTransformer;
import org.jetbrains.org.objectweb.asm.tree.AbstractInsnNode;
import org.jetbrains.org.objectweb.asm.tree.InsnList;
import org.jetbrains.org.objectweb.asm.tree.LabelNode;
import org.jetbrains.org.objectweb.asm.tree.LocalVariableNode;
import org.jetbrains.org.objectweb.asm.tree.MethodNode;
import org.jetbrains.org.objectweb.asm.tree.analysis.AnalyzerException;
import org.jetbrains.org.objectweb.asm.tree.analysis.BasicValue;
import org.jetbrains.org.objectweb.asm.tree.analysis.Frame;

/* JADX INFO: compiled from: RedundantLocalsEliminationMethodTransformer.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0000\u0018\u00002\u00020\u0001B\u0013\u0012\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003¢\u0006\u0002\u0010\u0005J\u0018\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000bH\u0016R\u0014\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\f"}, d2 = {"Lorg/jetbrains/kotlin/codegen/coroutines/RedundantLocalsEliminationMethodTransformer;", "Lorg/jetbrains/kotlin/codegen/optimization/transformer/MethodTransformer;", "suspensionPoints", "", "Lorg/jetbrains/kotlin/codegen/coroutines/SuspensionPoint;", "(Ljava/util/List;)V", "transform", "", "internalClassName", "", "methodNode", "Lorg/jetbrains/org/objectweb/asm/tree/MethodNode;", Marker.BACKEND}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class RedundantLocalsEliminationMethodTransformer extends MethodTransformer {
    private final List<SuspensionPoint> suspensionPoints;

    public RedundantLocalsEliminationMethodTransformer(List<SuspensionPoint> suspensionPoints) {
        Intrinsics.checkNotNullParameter(suspensionPoints, "suspensionPoints");
        this.suspensionPoints = suspensionPoints;
    }

    @Override // org.jetbrains.kotlin.codegen.optimization.transformer.MethodTransformer
    public void transform(String internalClassName, MethodNode methodNode) throws AnalyzerException {
        LinkedHashSet linkedHashSetEmptySet;
        Intrinsics.checkNotNullParameter(internalClassName, "internalClassName");
        Intrinsics.checkNotNullParameter(methodNode, "methodNode");
        List<LocalVariableNode> list = methodNode.localVariables;
        if (list != null) {
            LinkedHashSet linkedHashSet = new LinkedHashSet();
            Iterator<T> it = list.iterator();
            while (it.hasNext()) {
                linkedHashSet.add(Integer.valueOf(((LocalVariableNode) it.next()).index));
            }
            linkedHashSetEmptySet = linkedHashSet;
        } else {
            linkedHashSetEmptySet = SetsKt.emptySet();
        }
        UnitSourceInterpreter unitSourceInterpreter = new UnitSourceInterpreter(linkedHashSetEmptySet);
        Frame<BasicValue>[] frameArrRun = unitSourceInterpreter.run(internalClassName, methodNode);
        LinkedHashSet linkedHashSet2 = new LinkedHashSet();
        InsnList instructions = methodNode.instructions;
        Intrinsics.checkNotNullExpressionValue(instructions, "instructions");
        for (Pair pair : SequencesKt.zip(UtilKt.asSequence(instructions), ArraysKt.asSequence(frameArrRun))) {
            AbstractInsnNode abstractInsnNode = (AbstractInsnNode) pair.component1();
            if (((Frame) pair.component2()) != null || (abstractInsnNode instanceof LabelNode)) {
                abstractInsnNode = null;
            }
            if (abstractInsnNode != null) {
                linkedHashSet2.add(abstractInsnNode);
            }
        }
        LinkedHashSet linkedHashSet3 = linkedHashSet2;
        for (Map.Entry<AbstractInsnNode, Set<AbstractInsnNode>> entry : unitSourceInterpreter.getUnitUsageInformation().entrySet()) {
            AbstractInsnNode key = entry.getKey();
            Set<AbstractInsnNode> value = entry.getValue();
            if (!unitSourceInterpreter.getUnspillableUnitValues().contains(key) && !CoroutineTransformerMethodVisitorKt.contains(this.suspensionPoints, key)) {
                linkedHashSet3.add(key);
                CollectionsKt.addAll(linkedHashSet3, value);
            }
        }
        InsnList instructions2 = methodNode.instructions;
        Intrinsics.checkNotNullExpressionValue(instructions2, "instructions");
        UtilKt.removeAll(instructions2, linkedHashSet3);
    }
}
