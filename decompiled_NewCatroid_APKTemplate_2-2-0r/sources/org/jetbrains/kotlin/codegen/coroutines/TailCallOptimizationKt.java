package org.jetbrains.kotlin.codegen.coroutines;

import com.reandroid.dex.sections.Marker;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.collections.SetsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.IntRange;
import org.jetbrains.kotlin.codegen.inline.InlineCodegenUtilsKt;
import org.jetbrains.kotlin.codegen.optimization.boxing.PopBackwardPropagationTransformerKt;
import org.jetbrains.kotlin.codegen.optimization.common.ControlFlowGraph;
import org.jetbrains.kotlin.codegen.optimization.common.UtilKt;
import org.jetbrains.kotlin.codegen.optimization.fixStack.StackTransformationUtilsKt;
import org.jetbrains.kotlin.codegen.optimization.transformer.MethodTransformer;
import org.jetbrains.kotlin.resolve.jvm.AsmTypes;
import org.jetbrains.kotlin.utils.addToStdlib.AddToStdlibKt;
import org.jetbrains.org.objectweb.asm.Label;
import org.jetbrains.org.objectweb.asm.Type;
import org.jetbrains.org.objectweb.asm.commons.InstructionAdapter;
import org.jetbrains.org.objectweb.asm.tree.AbstractInsnNode;
import org.jetbrains.org.objectweb.asm.tree.InsnList;
import org.jetbrains.org.objectweb.asm.tree.JumpInsnNode;
import org.jetbrains.org.objectweb.asm.tree.LabelNode;
import org.jetbrains.org.objectweb.asm.tree.LocalVariableNode;
import org.jetbrains.org.objectweb.asm.tree.MethodNode;
import org.jetbrains.org.objectweb.asm.tree.TryCatchBlockNode;
import org.jetbrains.org.objectweb.asm.tree.VarInsnNode;
import org.jetbrains.org.objectweb.asm.tree.analysis.BasicValue;
import org.jetbrains.org.objectweb.asm.tree.analysis.Frame;

/* JADX INFO: compiled from: TailCallOptimization.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u00004\n\u0000\n\u0002\u0010\"\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000b\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0000\u001a\u001a\u0010\u000b\u001a\u00020\f*\u00020\r2\f\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u00100\u000fH\u0000\u001a\"\u0010\u0011\u001a\u00020\u0004*\u00020\r2\f\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u00100\u000f2\u0006\u0010\u0012\u001a\u00020\u0004H\u0000\u001a\u0016\u0010\u0013\u001a\u00020\u0004*\u0004\u0018\u00010\u00052\u0006\u0010\u0014\u001a\u00020\rH\u0002\u001a\u0010\u0010\u0015\u001a\u0004\u0018\u00010\u0005*\u0004\u0018\u00010\u0005H\u0002\u001a\u0010\u0010\u0016\u001a\u0004\u0018\u00010\u0017*\u0004\u0018\u00010\u0017H\u0002\"\u0014\u0010\u0000\u001a\b\u0012\u0004\u0012\u00020\u00020\u0001X\u0082\u0004¢\u0006\u0002\n\u0000\"\u0018\u0010\u0003\u001a\u00020\u0004*\u00020\u00058BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\u0003\u0010\u0006\"\u0018\u0010\u0007\u001a\u00020\u0004*\u00020\u00058BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\u0007\u0010\u0006\"\u001a\u0010\b\u001a\u0004\u0018\u00010\u0005*\u00020\u00058BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\t\u0010\n¨\u0006\u0018"}, d2 = {"SAFE_OPCODES", "", "", "isPopBeforeReturnUnit", "", "Lorg/jetbrains/org/objectweb/asm/tree/AbstractInsnNode;", "(Lorg/jetbrains/org/objectweb/asm/tree/AbstractInsnNode;)Z", "isReturnUnit", "nextMeaningful", "getNextMeaningful", "(Lorg/jetbrains/org/objectweb/asm/tree/AbstractInsnNode;)Lorg/jetbrains/org/objectweb/asm/tree/AbstractInsnNode;", "addCoroutineSuspendedChecks", "", "Lorg/jetbrains/org/objectweb/asm/tree/MethodNode;", "suspensionPoints", "", "Lorg/jetbrains/kotlin/codegen/coroutines/SuspensionPoint;", "allSuspensionPointsAreTailCalls", "optimizeReturnUnit", "isInvisibleInDebugVarInsn", "methodNode", "skipUntilMeaningful", "toFromSuspensionPoint", "Lorg/jetbrains/org/objectweb/asm/tree/analysis/BasicValue;", Marker.BACKEND}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class TailCallOptimizationKt {
    private static final Set<Integer> SAFE_OPCODES;

    public static final boolean allSuspensionPointsAreTailCalls(MethodNode methodNode, List<SuspensionPoint> suspensionPoints, boolean z) {
        Intrinsics.checkNotNullParameter(methodNode, "<this>");
        Intrinsics.checkNotNullParameter(suspensionPoints, "suspensionPoints");
        Frame[] frameArrAnalyze = MethodTransformer.analyze("fake", methodNode, new TcoInterpreter(suspensionPoints));
        Intrinsics.checkNotNullExpressionValue(frameArrAnalyze, "analyze(...)");
        ControlFlowGraph controlFlowGraphBuild$default = ControlFlowGraph.Companion.build$default(ControlFlowGraph.INSTANCE, methodNode, false, 2, null);
        List<SuspensionPoint> list = suspensionPoints;
        if (!(list instanceof Collection) || !list.isEmpty()) {
            for (SuspensionPoint suspensionPoint : list) {
                int iIndexOf = methodNode.instructions.indexOf(suspensionPoint.getSuspensionCallBegin());
                List<TryCatchBlockNode> tryCatchBlocks = methodNode.tryCatchBlocks;
                Intrinsics.checkNotNullExpressionValue(tryCatchBlocks, "tryCatchBlocks");
                List<TryCatchBlockNode> list2 = tryCatchBlocks;
                if (!(list2 instanceof Collection) || !list2.isEmpty()) {
                    for (TryCatchBlockNode tryCatchBlockNode : list2) {
                        if (iIndexOf >= methodNode.instructions.indexOf(tryCatchBlockNode.start) && methodNode.instructions.indexOf(tryCatchBlockNode.end) > iIndexOf) {
                            return false;
                        }
                    }
                }
                if (!allSuspensionPointsAreTailCalls$transitiveSuccessorsAreSafeOrReturns(suspensionPoint.getSuspensionCallEnd(), z, frameArrAnalyze, methodNode, controlFlowGraphBuild$default)) {
                    return false;
                }
            }
        }
        return true;
    }

    private static final boolean allSuspensionPointsAreTailCalls$isSafe(AbstractInsnNode abstractInsnNode, MethodNode methodNode) {
        return !UtilKt.isMeaningful(abstractInsnNode) || SAFE_OPCODES.contains(Integer.valueOf(abstractInsnNode.getOpcode())) || isInvisibleInDebugVarInsn(abstractInsnNode, methodNode) || InlineCodegenUtilsKt.isInlineMarker(abstractInsnNode);
    }

    private static final boolean allSuspensionPointsAreTailCalls$transitiveSuccessorsAreSafeOrReturns(AbstractInsnNode abstractInsnNode, boolean z, Frame<BasicValue>[] frameArr, MethodNode methodNode, ControlFlowGraph controlFlowGraph) {
        Set setMutableSetOf = SetsKt.mutableSetOf(abstractInsnNode);
        List listMutableListOf = CollectionsKt.mutableListOf(abstractInsnNode);
        while (!listMutableListOf.isEmpty()) {
            AbstractInsnNode abstractInsnNode2 = (AbstractInsnNode) AddToStdlibKt.popLast(listMutableListOf);
            if (abstractInsnNode2.getOpcode() == 176 || (z && isPopBeforeReturnUnit(abstractInsnNode2))) {
                Frame<BasicValue> frame = frameArr[methodNode.instructions.indexOf(abstractInsnNode2)];
                BasicValue basicValue = frame != null ? (BasicValue) StackTransformationUtilsKt.top(frame) : null;
                if (!(basicValue == null ? true : basicValue instanceof FromSuspensionPointValue)) {
                    return false;
                }
            } else {
                if (abstractInsnNode2 != abstractInsnNode && !allSuspensionPointsAreTailCalls$isSafe(abstractInsnNode2, methodNode)) {
                    return false;
                }
                Iterator<Integer> it = controlFlowGraph.getSuccessorsIndices(abstractInsnNode2).iterator();
                while (it.hasNext()) {
                    AbstractInsnNode abstractInsnNode3 = methodNode.instructions.get(it.next().intValue());
                    Intrinsics.checkNotNull(abstractInsnNode3);
                    if (setMutableSetOf.add(abstractInsnNode3)) {
                        listMutableListOf.add(abstractInsnNode3);
                    }
                }
            }
        }
        return true;
    }

    public static final void addCoroutineSuspendedChecks(MethodNode methodNode, List<SuspensionPoint> suspensionPoints) {
        Intrinsics.checkNotNullParameter(methodNode, "<this>");
        Intrinsics.checkNotNullParameter(suspensionPoints, "suspensionPoints");
        for (SuspensionPoint suspensionPoint : suspensionPoints) {
            AbstractInsnNode nextMeaningful = getNextMeaningful(suspensionPoint.getSuspensionCallEnd());
            if (nextMeaningful == null || nextMeaningful.getOpcode() != 176) {
                InsnList insnList = methodNode.instructions;
                AbstractInsnNode suspensionCallEnd = suspensionPoint.getSuspensionCallEnd();
                MethodNode methodNode2 = new MethodNode();
                InstructionAdapter instructionAdapter = new InstructionAdapter(methodNode2);
                Label label = new Label();
                instructionAdapter.dup();
                CoroutineCodegenUtilKt.loadCoroutineSuspendedMarker(instructionAdapter);
                instructionAdapter.ifacmpne(label);
                instructionAdapter.areturn(AsmTypes.OBJECT_TYPE);
                instructionAdapter.mark(label);
                Unit unit = Unit.INSTANCE;
                InsnList instructions = methodNode2.instructions;
                Intrinsics.checkNotNullExpressionValue(instructions, "instructions");
                insnList.insert(suspensionCallEnd, instructions);
            }
        }
    }

    private static final AbstractInsnNode skipUntilMeaningful(AbstractInsnNode abstractInsnNode) {
        if (abstractInsnNode == null) {
            return null;
        }
        LinkedHashSet linkedHashSet = new LinkedHashSet();
        while (abstractInsnNode != null && linkedHashSet.add(abstractInsnNode)) {
            if (abstractInsnNode.getOpcode() == 0 || !UtilKt.isMeaningful(abstractInsnNode)) {
                abstractInsnNode = abstractInsnNode.getNext();
            } else {
                if (abstractInsnNode.getOpcode() != 167) {
                    return abstractInsnNode;
                }
                abstractInsnNode = ((JumpInsnNode) abstractInsnNode).label;
            }
        }
        return null;
    }

    private static final AbstractInsnNode getNextMeaningful(AbstractInsnNode abstractInsnNode) {
        return skipUntilMeaningful(abstractInsnNode.getNext());
    }

    private static final boolean isReturnUnit(AbstractInsnNode abstractInsnNode) {
        AbstractInsnNode nextMeaningful;
        return PopBackwardPropagationTransformerKt.isUnitInstance(abstractInsnNode) && (nextMeaningful = getNextMeaningful(abstractInsnNode)) != null && (nextMeaningful.getOpcode() == 176 || isPopBeforeReturnUnit(nextMeaningful));
    }

    private static final boolean isPopBeforeReturnUnit(AbstractInsnNode abstractInsnNode) {
        AbstractInsnNode nextMeaningful;
        return abstractInsnNode.getOpcode() == 87 && (nextMeaningful = getNextMeaningful(abstractInsnNode)) != null && isReturnUnit(nextMeaningful);
    }

    private static final boolean isInvisibleInDebugVarInsn(AbstractInsnNode abstractInsnNode, MethodNode methodNode) {
        InsnList insnList = methodNode.instructions;
        int iIndexOf = insnList.indexOf(abstractInsnNode);
        if (abstractInsnNode instanceof VarInsnNode) {
            List<LocalVariableNode> localVariables = methodNode.localVariables;
            Intrinsics.checkNotNullExpressionValue(localVariables, "localVariables");
            List<LocalVariableNode> list = localVariables;
            if (!(list instanceof Collection) || !list.isEmpty()) {
                for (LocalVariableNode localVariableNode : list) {
                    if (localVariableNode.index == ((VarInsnNode) abstractInsnNode).var) {
                        LabelNode labelNode = localVariableNode.start;
                        Intrinsics.checkNotNull(insnList);
                        int iIndexOf2 = insnList.indexOf(labelNode);
                        if (iIndexOf > insnList.indexOf(localVariableNode.end) || iIndexOf2 > iIndexOf) {
                        }
                    }
                }
            }
            return true;
        }
        return false;
    }

    static {
        Set setCreateSetBuilder = SetsKt.createSetBuilder();
        setCreateSetBuilder.add(0);
        Set set = setCreateSetBuilder;
        CollectionsKt.addAll(set, new IntRange(87, 95));
        CollectionsKt.addAll(set, new IntRange(153, 167));
        setCreateSetBuilder.add(192);
        SAFE_OPCODES = SetsKt.build(setCreateSetBuilder);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final BasicValue toFromSuspensionPoint(BasicValue basicValue) {
        Type type;
        return (basicValue == null || (type = basicValue.getType()) == null || type.getSort() != 10) ? basicValue : FromSuspensionPointValue.INSTANCE;
    }
}
