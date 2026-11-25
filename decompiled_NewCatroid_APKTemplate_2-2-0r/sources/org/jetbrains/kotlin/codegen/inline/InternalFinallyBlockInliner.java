package org.jetbrains.kotlin.codegen.inline;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import org.jetbrains.kotlin.codegen.CodegenUtilKt;
import org.jetbrains.kotlin.com.google.common.collect.Lists;
import org.jetbrains.org.objectweb.asm.Label;
import org.jetbrains.org.objectweb.asm.Type;
import org.jetbrains.org.objectweb.asm.tree.AbstractInsnNode;
import org.jetbrains.org.objectweb.asm.tree.InsnList;
import org.jetbrains.org.objectweb.asm.tree.JumpInsnNode;
import org.jetbrains.org.objectweb.asm.tree.LabelNode;
import org.jetbrains.org.objectweb.asm.tree.LocalVariableNode;
import org.jetbrains.org.objectweb.asm.tree.MethodNode;
import org.jetbrains.org.objectweb.asm.tree.TryCatchBlockNode;
import org.jetbrains.org.objectweb.asm.util.Textifier;
import org.jetbrains.org.objectweb.asm.util.TraceMethodVisitor;

/* JADX INFO: loaded from: classes3.dex */
public class InternalFinallyBlockInliner extends CoveringTryCatchNodeProcessor {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private final MethodNode inlineFun;
    private final boolean properFinallySplit;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = (i == 8 || i == 19) ? "@NotNull method %s.%s must not return null" : "Argument for @NotNull parameter '%s' of %s.%s must not be null";
        Object[] objArr = new Object[(i == 8 || i == 19) ? 2 : 3];
        switch (i) {
            case 2:
                objArr[0] = "inlineFunTryBlockInfo";
                break;
            case 3:
                objArr[0] = "localVariableInfo";
                break;
            case 4:
                objArr[0] = "finallyBlockCopy";
                break;
            case 5:
                objArr[0] = "currentIns";
                break;
            case 6:
                objArr[0] = "currentCoveringNodesFromOuterMost";
                break;
            case 7:
            case 18:
                objArr[0] = "finallyInfo";
                break;
            case 8:
            case 19:
                objArr[0] = "org/jetbrains/kotlin/codegen/inline/InternalFinallyBlockInliner";
                break;
            case 9:
                objArr[0] = "updatingClusterBlocks";
                break;
            case 10:
                objArr[0] = "newFinallyStart";
                break;
            case 11:
                objArr[0] = "newFinallyEnd";
                break;
            case 12:
                objArr[0] = "tryCatchBlockPresentInFinally";
                break;
            case 13:
            case 15:
                objArr[0] = "labelsInsideFinally";
                break;
            case 14:
                objArr[0] = "insertedBlockEnd";
                break;
            case 16:
                objArr[0] = "tryCatchBlock";
                break;
            case 17:
                objArr[0] = "tryCatchBlocks";
                break;
            case 20:
                objArr[0] = "inst";
                break;
            case 21:
                objArr[0] = "curNonLocal";
                break;
            default:
                objArr[0] = "inlineFun";
                break;
        }
        if (i == 8) {
            objArr[1] = "rememberOriginalLabelNodes";
        } else if (i != 19) {
            objArr[1] = "org/jetbrains/kotlin/codegen/inline/InternalFinallyBlockInliner";
        } else {
            objArr[1] = "findTryCatchBlocksInlinedInFinally";
        }
        switch (i) {
            case 1:
            case 2:
            case 3:
                objArr[2] = "<init>";
                break;
            case 4:
            case 5:
                objArr[2] = "copyInstruction";
                break;
            case 6:
                objArr[2] = "checkCoveringBlocksInvariant";
                break;
            case 7:
                objArr[2] = "rememberOriginalLabelNodes";
                break;
            case 8:
            case 19:
                break;
            case 9:
            case 10:
            case 11:
            case 12:
            case 13:
            case 14:
                objArr[2] = "updateExceptionTable";
                break;
            case 15:
                objArr[2] = "getNewOrOldLabel";
                break;
            case 16:
            case 17:
                objArr[2] = "findFinallyBlockBody";
                break;
            case 18:
                objArr[2] = "findTryCatchBlocksInlinedInFinally";
                break;
            case 20:
                objArr[2] = "instructionIndex";
                break;
            case 21:
                objArr[2] = "flushCurrentState";
                break;
            default:
                objArr[2] = "processInlineFunFinallyBlocks";
                break;
        }
        String str2 = String.format(str, objArr);
        if (i != 8 && i != 19) {
            throw new IllegalArgumentException(str2);
        }
        throw new IllegalStateException(str2);
    }

    private static class FinallyBlockInfo {
        final AbstractInsnNode endInsExclusive;
        final AbstractInsnNode startIns;

        private static /* synthetic */ void $$$reportNull$$$0(int i) {
            Object[] objArr = new Object[3];
            if (i != 1) {
                objArr[0] = "inclusiveStart";
            } else {
                objArr[0] = "exclusiveEnd";
            }
            objArr[1] = "org/jetbrains/kotlin/codegen/inline/InternalFinallyBlockInliner$FinallyBlockInfo";
            objArr[2] = "<init>";
            throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
        }

        private FinallyBlockInfo(AbstractInsnNode abstractInsnNode, AbstractInsnNode abstractInsnNode2) {
            if (abstractInsnNode == null) {
                $$$reportNull$$$0(0);
            }
            if (abstractInsnNode2 == null) {
                $$$reportNull$$$0(1);
            }
            this.startIns = abstractInsnNode;
            this.endInsExclusive = abstractInsnNode2;
        }

        public boolean isEmpty() {
            AbstractInsnNode abstractInsnNode;
            if (!(this.startIns instanceof LabelNode)) {
                return false;
            }
            AbstractInsnNode previous = this.endInsExclusive;
            while (true) {
                abstractInsnNode = this.startIns;
                if (previous == abstractInsnNode || !(previous instanceof LabelNode)) {
                    break;
                }
                previous = previous.getPrevious();
            }
            return abstractInsnNode == previous;
        }
    }

    public static void processInlineFunFinallyBlocks(MethodNode methodNode, int i, int i2, boolean z) {
        if (methodNode == null) {
            $$$reportNull$$$0(0);
        }
        ArrayList arrayList = new ArrayList();
        Iterator<TryCatchBlockNode> it = methodNode.tryCatchBlocks.iterator();
        int i3 = 0;
        while (it.hasNext()) {
            int i4 = i3 + 1;
            arrayList.add(new TryCatchBlockNodeInfo(it.next(), i3 < i));
            i3 = i4;
        }
        ArrayList arrayList2 = new ArrayList();
        Iterator<LocalVariableNode> it2 = methodNode.localVariables.iterator();
        while (it2.hasNext()) {
            arrayList2.add(new LocalVarNodeWrapper(it2.next()));
        }
        if (hasFinallyBlocks(arrayList)) {
            new InternalFinallyBlockInliner(methodNode, arrayList, arrayList2, i2, z).processInlineFunFinallyBlocks();
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    private InternalFinallyBlockInliner(MethodNode methodNode, List<TryCatchBlockNodeInfo> list, List<LocalVarNodeWrapper> list2, int i, boolean z) {
        super(i);
        if (methodNode == null) {
            $$$reportNull$$$0(1);
        }
        if (list == null) {
            $$$reportNull$$$0(2);
        }
        if (list2 == null) {
            $$$reportNull$$$0(3);
        }
        this.inlineFun = methodNode;
        this.properFinallySplit = z;
        Iterator<TryCatchBlockNodeInfo> it = list.iterator();
        while (it.hasNext()) {
            getTryBlocksMetaInfo().addNewInterval(it.next());
        }
        Iterator<LocalVarNodeWrapper> it2 = list2.iterator();
        while (it2.hasNext()) {
            getLocalVarsMetaInfo().addNewInterval(it2.next());
        }
    }

    private int initAndGetVarIndexForNonLocalReturnValue() {
        MaxLocalsCalculator maxLocalsCalculator = new MaxLocalsCalculator(589824, this.inlineFun.access, this.inlineFun.desc, null);
        this.inlineFun.accept(maxLocalsCalculator);
        return maxLocalsCalculator.getMaxLocals();
    }

    /* JADX WARN: Code duplicated, block: B:62:0x01a9  */
    private void processInlineFunFinallyBlocks() {
        InsnList insnList;
        AbstractInsnNode abstractInsnNode;
        int iInitAndGetVarIndexForNonLocalReturnValue = initAndGetVarIndexForNonLocalReturnValue();
        InsnList insnList2 = this.inlineFun.instructions;
        AbstractInsnNode last = insnList2.getLast();
        while (last != null) {
            processInstruction(last, false);
            if (!InlineCodegenUtilsKt.isReturnOpcode(last.getOpcode()) || !InlineCodegenUtilsKt.isMarkedReturn(last)) {
                insnList = insnList2;
                last = last.getPrevious();
            } else {
                List<TryCatchBlockNodeInfo> listSortTryCatchBlocks = sortTryCatchBlocks(new ArrayList(getTryBlocksMetaInfo().getCurrentIntervals()));
                checkCoveringBlocksInvariant(Lists.reverse(listSortTryCatchBlocks));
                if (!listSortTryCatchBlocks.isEmpty()) {
                    int i = 1;
                    if (listSortTryCatchBlocks.get(listSortTryCatchBlocks.size() - 1).getOnlyCopyNotProcess()) {
                        insnList = insnList2;
                        last = last.getPrevious();
                    } else {
                        AbstractInsnNode previous = last.getPrevious();
                        AbstractInsnNode previous2 = previous.getPrevious();
                        LabelNode labelNode = (LabelNode) last.getNext();
                        Type returnType = InlineCodegenUtilsKt.getReturnType(last.getOpcode());
                        List<TryBlockCluster> listDoClustering = TryBlockClusteringKt.doClustering(listSortTryCatchBlocks);
                        checkClusterInvariant(listDoClustering);
                        ArrayList arrayList = new ArrayList();
                        int i2 = 0;
                        for (TryBlockCluster tryBlockCluster : listDoClustering) {
                            List<TryCatchBlockNodeInfo> blocks = tryBlockCluster.getBlocks();
                            TryCatchBlockNodeInfo tryCatchBlockNodeInfo = blocks.get(blocks.size() - i);
                            FinallyBlockInfo finallyBlockInfoFindFinallyBlockBody = findFinallyBlockBody(tryCatchBlockNodeInfo, getTryBlocksMetaInfo().getAllIntervals());
                            if (finallyBlockInfoFindFinallyBlockBody == null) {
                                arrayList.addAll(tryBlockCluster.getBlocks());
                            } else {
                                if (tryCatchBlockNodeInfo.getOnlyCopyNotProcess()) {
                                    throw new RuntimeException("Lambda try blocks should be skipped");
                                }
                                int i3 = i2 + 1;
                                insnList2.resetLabels();
                                List<TryCatchBlockNodePosition> listFindTryCatchBlocksInlinedInFinally = findTryCatchBlocksInlinedInFinally(finallyBlockInfoFindFinallyBlockBody);
                                MethodNode methodNodeCreateEmptyMethodNode = InlineCodegenUtilsKt.createEmptyMethodNode();
                                Label label = new Label();
                                Label labelLinkedLabel = CodegenUtilKt.linkedLabel();
                                boolean z = (returnType == Type.VOID_TYPE || finallyBlockInfoFindFinallyBlockBody.isEmpty()) ? false : true;
                                InsnList insnList3 = insnList2;
                                if (z) {
                                    methodNodeCreateEmptyMethodNode.visitVarInsn(returnType.getOpcode(54), iInitAndGetVarIndexForNonLocalReturnValue);
                                }
                                methodNodeCreateEmptyMethodNode.visitLabel(label);
                                Set<LabelNode> setRememberOriginalLabelNodes = rememberOriginalLabelNodes(finallyBlockInfoFindFinallyBlockBody);
                                AbstractInsnNode abstractInsnNode2 = previous2;
                                AbstractInsnNode abstractInsnNode3 = last;
                                for (AbstractInsnNode next = finallyBlockInfoFindFinallyBlockBody.startIns; next != finallyBlockInfoFindFinallyBlockBody.endInsExclusive; next = next.getNext()) {
                                    copyInstruction(methodNodeCreateEmptyMethodNode, next, !(next instanceof JumpInsnNode) || setRememberOriginalLabelNodes.contains(((JumpInsnNode) next).label), i3);
                                }
                                if (z) {
                                    methodNodeCreateEmptyMethodNode.visitVarInsn(returnType.getOpcode(21), iInitAndGetVarIndexForNonLocalReturnValue);
                                    iInitAndGetVarIndexForNonLocalReturnValue += returnType.getSize();
                                }
                                int i4 = iInitAndGetVarIndexForNonLocalReturnValue;
                                methodNodeCreateEmptyMethodNode.visitLabel(labelLinkedLabel);
                                InlineCodegenUtilsKt.insertNodeBefore(methodNodeCreateEmptyMethodNode, this.inlineFun, previous);
                                arrayList.addAll(blocks);
                                ArrayList arrayList2 = arrayList;
                                updateExceptionTable(this.properFinallySplit ? arrayList : blocks, label, labelNode, listFindTryCatchBlocksInlinedInFinally, setRememberOriginalLabelNodes, (LabelNode) labelLinkedLabel.info);
                                arrayList2.clear();
                                arrayList = arrayList2;
                                iInitAndGetVarIndexForNonLocalReturnValue = i4;
                                i2 = i3;
                                insnList2 = insnList3;
                                previous2 = abstractInsnNode2;
                                last = abstractInsnNode3;
                                i = 1;
                            }
                        }
                        InsnList insnList4 = insnList2;
                        AbstractInsnNode abstractInsnNode4 = last;
                        AbstractInsnNode abstractInsnNode5 = previous2;
                        AbstractInsnNode previous3 = abstractInsnNode4.getPrevious();
                        while (true) {
                            abstractInsnNode = abstractInsnNode5;
                            if (previous3 == null || previous3 == abstractInsnNode) {
                                break;
                            }
                            processInstruction(previous3, false);
                            previous3 = previous3.getPrevious();
                            abstractInsnNode5 = abstractInsnNode;
                        }
                        if (previous.getPrevious() == abstractInsnNode || previous3 == null) {
                            insnList = insnList4;
                        } else {
                            LabelNode labelNode2 = new LabelNode();
                            LabelNode labelNode3 = new LabelNode();
                            insnList = insnList4;
                            insnList.insert(previous3, labelNode2);
                            insnList.insert(abstractInsnNode4, labelNode3);
                            getLocalVarsMetaInfo().splitCurrentIntervals(new SimpleInterval(labelNode2, labelNode3), true);
                        }
                        last = previous3;
                    }
                } else {
                    insnList = insnList2;
                    last = last.getPrevious();
                }
            }
            insnList2 = insnList;
        }
        substituteTryBlockNodes(this.inlineFun);
        substituteLocalVarTable(this.inlineFun);
    }

    private static void copyInstruction(MethodNode methodNode, AbstractInsnNode abstractInsnNode, boolean z, int i) {
        if (methodNode == null) {
            $$$reportNull$$$0(4);
        }
        if (abstractInsnNode == null) {
            $$$reportNull$$$0(5);
        }
        if (z) {
            if (InlineCodegenUtilsKt.isFinallyMarker(abstractInsnNode.getNext())) {
                methodNode.visitLdcInsn(Integer.valueOf(Integer.valueOf(InlineCodegenUtilsKt.getConstant(abstractInsnNode)).intValue() + i));
                return;
            } else {
                abstractInsnNode.accept(methodNode);
                return;
            }
        }
        methodNode.instructions.add(new JumpInsnNode(abstractInsnNode.getOpcode(), ((JumpInsnNode) abstractInsnNode).label));
    }

    private static void checkCoveringBlocksInvariant(List<TryCatchBlockNodeInfo> list) {
        if (list == null) {
            $$$reportNull$$$0(6);
        }
        Iterator<TryCatchBlockNodeInfo> it = list.iterator();
        while (it.hasNext()) {
            it.next().getOnlyCopyNotProcess();
        }
    }

    private static void checkClusterInvariant(List<TryBlockCluster<TryCatchBlockNodeInfo>> list) {
        Iterator it = Lists.reverse(list).iterator();
        while (it.hasNext()) {
            ((TryCatchBlockNodeInfo) ((TryBlockCluster) it.next()).getBlocks().get(0)).getOnlyCopyNotProcess();
        }
    }

    private static Set<LabelNode> rememberOriginalLabelNodes(FinallyBlockInfo finallyBlockInfo) {
        if (finallyBlockInfo == null) {
            $$$reportNull$$$0(7);
        }
        HashSet hashSet = new HashSet();
        for (AbstractInsnNode next = finallyBlockInfo.startIns; next != finallyBlockInfo.endInsExclusive; next = next.getNext()) {
            if (next instanceof LabelNode) {
                hashSet.add((LabelNode) next);
            }
        }
        return hashSet;
    }

    private void updateExceptionTable(List<TryCatchBlockNodeInfo> list, Label label, LabelNode labelNode, List<TryCatchBlockNodePosition> list2, Set<LabelNode> set, LabelNode labelNode2) {
        if (list == null) {
            $$$reportNull$$$0(9);
        }
        if (label == null) {
            $$$reportNull$$$0(10);
        }
        if (labelNode == null) {
            $$$reportNull$$$0(11);
        }
        if (list2 == null) {
            $$$reportNull$$$0(12);
        }
        if (set == null) {
            $$$reportNull$$$0(13);
        }
        if (labelNode2 == null) {
            $$$reportNull$$$0(14);
        }
        List<TryBlockCluster> listDoClustering = TryBlockClusteringKt.doClustering(list2);
        HashMap map = new HashMap();
        IntervalMetaInfo<TryCatchBlockNodeInfo> tryBlocksMetaInfo = getTryBlocksMetaInfo();
        for (TryBlockCluster tryBlockCluster : listDoClustering) {
            List blocks = tryBlockCluster.getBlocks();
            TryCatchPosition position = ((TryCatchBlockNodePosition) blocks.get(0)).getPosition();
            if (position == TryCatchPosition.INNER) {
                Iterator it = blocks.iterator();
                while (it.hasNext()) {
                    TryCatchBlockNode node = ((TryCatchBlockNodePosition) it.next()).getNodeInfo().getNode();
                    tryBlocksMetaInfo.addNewInterval(new TryCatchBlockNodeInfo(new TryCatchBlockNode((LabelNode) node.start.getLabel().info, (LabelNode) node.end.getLabel().info, getNewOrOldLabel(node.handler, set), node.type), true));
                }
            } else if (position == TryCatchPosition.END) {
                map.put(((TryCatchBlockNodePosition) tryBlockCluster.getDefaultHandler()).getHandler(), tryBlockCluster);
            } else {
                TryBlockCluster tryBlockCluster2 = (TryBlockCluster) map.remove(((TryCatchBlockNodePosition) tryBlockCluster.getDefaultHandler()).getHandler());
                Iterator it2 = blocks.iterator();
                for (TryCatchBlockNodePosition tryCatchBlockNodePosition : tryBlockCluster2.getBlocks()) {
                    TryCatchBlockNodeInfo nodeInfo = ((TryCatchBlockNodePosition) it2.next()).getNodeInfo();
                    TryCatchBlockNodeInfo nodeInfo2 = tryCatchBlockNodePosition.getNodeInfo();
                    getTryBlocksMetaInfo().split(nodeInfo2, new SimpleInterval((LabelNode) nodeInfo2.getNode().end.getLabel().info, (LabelNode) nodeInfo.getStartLabel().getLabel().info), false);
                }
            }
        }
        if (map.size() == 1) {
            TryBlockCluster tryBlockCluster3 = (TryBlockCluster) map.values().iterator().next();
            if (((TryCatchBlockNodePosition) tryBlockCluster3.getBlocks().get(0)).getPosition() == TryCatchPosition.END) {
                Iterator it3 = tryBlockCluster3.getBlocks().iterator();
                while (it3.hasNext()) {
                    TryCatchBlockNodeInfo nodeInfo3 = ((TryCatchBlockNodePosition) it3.next()).getNodeInfo();
                    getTryBlocksMetaInfo().split(nodeInfo3, new SimpleInterval((LabelNode) nodeInfo3.getNode().end.getLabel().info, (LabelNode) labelNode2.getLabel().info), false);
                }
                map.clear();
            }
        }
        SimpleInterval simpleInterval = new SimpleInterval((LabelNode) label.info, labelNode);
        Iterator<TryCatchBlockNodeInfo> it4 = list.iterator();
        while (it4.hasNext()) {
            SplitPair<T> splitPairSplitAndRemoveIntervalFromCurrents = tryBlocksMetaInfo.splitAndRemoveIntervalFromCurrents(it4.next(), simpleInterval, false);
            checkFinally((IntervalWithHandler) splitPairSplitAndRemoveIntervalFromCurrents.getNewPart());
            checkFinally((IntervalWithHandler) splitPairSplitAndRemoveIntervalFromCurrents.getPatchedPart());
        }
        sortTryCatchBlocks(tryBlocksMetaInfo.getAllIntervals());
    }

    private static LabelNode getNewOrOldLabel(LabelNode labelNode, Set<LabelNode> set) {
        if (set == null) {
            $$$reportNull$$$0(15);
        }
        return set.contains(labelNode) ? (LabelNode) labelNode.getLabel().info : labelNode;
    }

    private static boolean hasFinallyBlocks(List<TryCatchBlockNodeInfo> list) {
        for (TryCatchBlockNodeInfo tryCatchBlockNodeInfo : list) {
            if (!tryCatchBlockNodeInfo.getOnlyCopyNotProcess() && tryCatchBlockNodeInfo.getNode().type == null) {
                return true;
            }
        }
        return false;
    }

    private FinallyBlockInfo findFinallyBlockBody(TryCatchBlockNodeInfo tryCatchBlockNodeInfo, List<TryCatchBlockNodeInfo> list) {
        if (tryCatchBlockNodeInfo == null) {
            $$$reportNull$$$0(16);
        }
        if (list == null) {
            $$$reportNull$$$0(17);
        }
        ArrayList arrayList = new ArrayList();
        boolean z = false;
        LabelNode labelNodeFirstLabelInChain = null;
        for (TryCatchBlockNodeInfo tryCatchBlockNodeInfo2 : list) {
            if (tryCatchBlockNodeInfo == tryCatchBlockNodeInfo2) {
                z = true;
            }
            if (z && tryCatchBlockNodeInfo2.getNode().type == null && ((InlineCodegenUtilsKt.firstLabelInChain(tryCatchBlockNodeInfo.getNode().start) == InlineCodegenUtilsKt.firstLabelInChain(tryCatchBlockNodeInfo2.getNode().start) && InlineCodegenUtilsKt.firstLabelInChain(tryCatchBlockNodeInfo.getNode().end) == InlineCodegenUtilsKt.firstLabelInChain(tryCatchBlockNodeInfo2.getNode().end)) || labelNodeFirstLabelInChain == InlineCodegenUtilsKt.firstLabelInChain(tryCatchBlockNodeInfo2.getNode().handler))) {
                arrayList.add(tryCatchBlockNodeInfo2);
                if (labelNodeFirstLabelInChain == null) {
                    labelNodeFirstLabelInChain = InlineCodegenUtilsKt.firstLabelInChain(tryCatchBlockNodeInfo2.getNode().handler);
                }
            }
        }
        if (arrayList.isEmpty()) {
            return null;
        }
        TryCatchBlockNodeInfo tryCatchBlockNodeInfo3 = (TryCatchBlockNodeInfo) arrayList.get(1);
        LabelNode labelNode = tryCatchBlockNodeInfo.getNode().end;
        AbstractInsnNode nextMeaningful = MethodInlinerUtilKt.getNextMeaningful(labelNode);
        Integer numValueOf = Integer.valueOf(InlineCodegenUtilsKt.getConstant(nextMeaningful));
        AbstractInsnNode next = tryCatchBlockNodeInfo3.getNode().start;
        AbstractInsnNode next2 = nextMeaningful.getNext();
        while (next != next2) {
            next2 = next2.getNext();
            if (InlineCodegenUtilsKt.isFinallyEnd(next2) && Integer.valueOf(InlineCodegenUtilsKt.getConstant(next2.getPrevious())).equals(numValueOf)) {
                next = next2.getNext();
                break;
            }
        }
        FinallyBlockInfo finallyBlockInfo = new FinallyBlockInfo(labelNode.getNext(), next);
        checkFinally(finallyBlockInfo);
        return finallyBlockInfo;
    }

    private void checkFinally(FinallyBlockInfo finallyBlockInfo) {
        checkFinally(finallyBlockInfo.startIns, finallyBlockInfo.endInsExclusive);
    }

    private void checkFinally(IntervalWithHandler intervalWithHandler) {
        checkFinally(intervalWithHandler.getStartLabel(), intervalWithHandler.getEndLabel());
    }

    private void checkFinally(AbstractInsnNode abstractInsnNode, AbstractInsnNode abstractInsnNode2) {
        if (this.inlineFun.instructions.indexOf(abstractInsnNode) >= this.inlineFun.instructions.indexOf(abstractInsnNode2)) {
            throw new AssertionError("Inconsistent finally: block end occurs before start " + traceInterval(abstractInsnNode2, abstractInsnNode));
        }
    }

    private List<TryCatchBlockNodePosition> findTryCatchBlocksInlinedInFinally(FinallyBlockInfo finallyBlockInfo) {
        if (finallyBlockInfo == null) {
            $$$reportNull$$$0(18);
        }
        ArrayList arrayList = new ArrayList();
        HashMap map = new HashMap();
        for (AbstractInsnNode next = finallyBlockInfo.startIns; next != finallyBlockInfo.endInsExclusive; next = next.getNext()) {
            if (next instanceof LabelNode) {
                LabelNode labelNode = (LabelNode) next;
                for (TryCatchBlockNodeInfo tryCatchBlockNodeInfo : getStartNodes(labelNode)) {
                    TryCatchBlockNodePosition tryCatchBlockNodePosition = new TryCatchBlockNodePosition(tryCatchBlockNodeInfo, TryCatchPosition.START);
                    map.put(tryCatchBlockNodeInfo, tryCatchBlockNodePosition);
                    arrayList.add(tryCatchBlockNodePosition);
                }
                for (TryCatchBlockNodeInfo tryCatchBlockNodeInfo2 : getEndNodes(labelNode)) {
                    TryCatchBlockNodePosition tryCatchBlockNodePosition2 = (TryCatchBlockNodePosition) map.get(tryCatchBlockNodeInfo2);
                    if (tryCatchBlockNodePosition2 != null) {
                        tryCatchBlockNodePosition2.setPosition(TryCatchPosition.INNER);
                    } else {
                        TryCatchBlockNodePosition tryCatchBlockNodePosition3 = new TryCatchBlockNodePosition(tryCatchBlockNodeInfo2, TryCatchPosition.END);
                        map.put(tryCatchBlockNodeInfo2, tryCatchBlockNodePosition3);
                        arrayList.add(tryCatchBlockNodePosition3);
                    }
                }
            }
        }
        return arrayList;
    }

    @Override // org.jetbrains.kotlin.codegen.inline.CoveringTryCatchNodeProcessor
    public int instructionIndex(AbstractInsnNode abstractInsnNode) {
        if (abstractInsnNode == null) {
            $$$reportNull$$$0(20);
        }
        return this.inlineFun.instructions.indexOf(abstractInsnNode);
    }

    private static String traceInterval(AbstractInsnNode abstractInsnNode, AbstractInsnNode abstractInsnNode2) {
        Textifier textifier = new Textifier();
        TraceMethodVisitor traceMethodVisitor = new TraceMethodVisitor(textifier);
        while (abstractInsnNode != abstractInsnNode2) {
            abstractInsnNode.accept(traceMethodVisitor);
            abstractInsnNode = abstractInsnNode.getNext();
        }
        abstractInsnNode.accept(traceMethodVisitor);
        StringWriter stringWriter = new StringWriter();
        textifier.print(new PrintWriter(stringWriter));
        return stringWriter.toString();
    }
}
