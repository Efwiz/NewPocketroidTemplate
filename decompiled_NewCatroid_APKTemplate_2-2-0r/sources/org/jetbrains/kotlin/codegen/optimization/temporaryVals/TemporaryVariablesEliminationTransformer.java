package org.jetbrains.kotlin.codegen.optimization.temporaryVals;

import com.reandroid.dex.sections.Marker;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.codegen.coroutines.CoroutineCodegenUtilKt;
import org.jetbrains.kotlin.codegen.inline.InlineCodegenUtilsKt;
import org.jetbrains.kotlin.codegen.optimization.common.InsnSequence;
import org.jetbrains.kotlin.codegen.optimization.common.UtilKt;
import org.jetbrains.kotlin.codegen.optimization.nullCheck.RedundantNullCheckMethodTransformerKt;
import org.jetbrains.kotlin.codegen.optimization.transformer.MethodTransformer;
import org.jetbrains.kotlin.codegen.state.GenerationState;
import org.jetbrains.kotlin.utils.SmartList;
import org.jetbrains.org.objectweb.asm.Type;
import org.jetbrains.org.objectweb.asm.tree.AbstractInsnNode;
import org.jetbrains.org.objectweb.asm.tree.FieldInsnNode;
import org.jetbrains.org.objectweb.asm.tree.InsnList;
import org.jetbrains.org.objectweb.asm.tree.InsnNode;
import org.jetbrains.org.objectweb.asm.tree.JumpInsnNode;
import org.jetbrains.org.objectweb.asm.tree.LabelNode;
import org.jetbrains.org.objectweb.asm.tree.LdcInsnNode;
import org.jetbrains.org.objectweb.asm.tree.LineNumberNode;
import org.jetbrains.org.objectweb.asm.tree.LocalVariableNode;
import org.jetbrains.org.objectweb.asm.tree.LookupSwitchInsnNode;
import org.jetbrains.org.objectweb.asm.tree.MethodNode;
import org.jetbrains.org.objectweb.asm.tree.TableSwitchInsnNode;
import org.jetbrains.org.objectweb.asm.tree.TryCatchBlockNode;
import org.jetbrains.org.objectweb.asm.tree.VarInsnNode;

/* JADX INFO: compiled from: TemporaryVariablesEliminationTransformer.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000`\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0010\u0015\n\u0002\u0010\b\n\u0002\b\u0002\u0018\u00002\u00020\u0001:\u0001#B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0010\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH\u0002J\u001e\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000e2\f\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\u00110\u0010H\u0002J\u0010\u0010\u0012\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000eH\u0002J\u0018\u0010\u0013\u001a\u00020\f2\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u0014\u001a\u00020\u0015H\u0002J\u0010\u0010\u0016\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000eH\u0002J\u0010\u0010\u0017\u001a\u00020\f2\u0006\u0010\u0018\u001a\u00020\u0019H\u0002J\u0018\u0010\u001a\u001a\u00020\f2\u0006\u0010\u001b\u001a\u00020\u001c2\u0006\u0010\u0018\u001a\u00020\u0019H\u0016J\u0014\u0010\u001d\u001a\u00020\b*\u00020\n2\u0006\u0010\u001e\u001a\u00020\u000eH\u0002J\u0018\u0010\u001f\u001a\u00020\b*\u00020\n2\n\u0010 \u001a\u00020!\"\u00020\"H\u0002R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006$"}, d2 = {"Lorg/jetbrains/kotlin/codegen/optimization/temporaryVals/TemporaryVariablesEliminationTransformer;", "Lorg/jetbrains/kotlin/codegen/optimization/transformer/MethodTransformer;", "state", "Lorg/jetbrains/kotlin/codegen/state/GenerationState;", "(Lorg/jetbrains/kotlin/codegen/state/GenerationState;)V", "temporaryValsAnalyzer", "Lorg/jetbrains/kotlin/codegen/optimization/temporaryVals/TemporaryValsAnalyzer;", "isRewritableSafeCallPart", "", "branchInsn", "Lorg/jetbrains/org/objectweb/asm/tree/AbstractInsnNode;", "optimizeTemporaryVals", "", "cfg", "Lorg/jetbrains/kotlin/codegen/optimization/temporaryVals/TemporaryVariablesEliminationTransformer$ControlFlowGraph;", "temporaryVals", "", "Lorg/jetbrains/kotlin/codegen/optimization/temporaryVals/TemporaryVal;", "processLabels", "rewriteSafeCallPart", "insnList", "Lorg/jetbrains/org/objectweb/asm/tree/InsnList;", "simplifyKnownSafeCallPatterns", "simplifyTrivialInstructions", "methodNode", "Lorg/jetbrains/org/objectweb/asm/tree/MethodNode;", "transform", "internalClassName", "", "isIntervening", "context", "matchOpcodes", "opcodes", "", "", "ControlFlowGraph", Marker.BACKEND}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class TemporaryVariablesEliminationTransformer extends MethodTransformer {
    private final GenerationState state;
    private final TemporaryValsAnalyzer temporaryValsAnalyzer;

    public TemporaryVariablesEliminationTransformer(GenerationState state) {
        Intrinsics.checkNotNullParameter(state, "state");
        this.state = state;
        this.temporaryValsAnalyzer = new TemporaryValsAnalyzer();
    }

    @Override // org.jetbrains.kotlin.codegen.optimization.transformer.MethodTransformer
    public void transform(String internalClassName, MethodNode methodNode) {
        Intrinsics.checkNotNullParameter(internalClassName, "internalClassName");
        Intrinsics.checkNotNullParameter(methodNode, "methodNode");
        if (this.state.getIsIrBackend()) {
            Iterable instructions = methodNode.instructions;
            Intrinsics.checkNotNullExpressionValue(instructions, "instructions");
            Iterable<AbstractInsnNode> iterable = instructions;
            if (!(iterable instanceof Collection) || !((Collection) iterable).isEmpty()) {
                for (AbstractInsnNode abstractInsnNode : iterable) {
                    Intrinsics.checkNotNull(abstractInsnNode);
                    if (InlineCodegenUtilsKt.isSuspendInlineMarker(abstractInsnNode)) {
                        return;
                    }
                }
            }
            simplifyTrivialInstructions(methodNode);
            ControlFlowGraph controlFlowGraph = new ControlFlowGraph(methodNode);
            processLabels(controlFlowGraph);
            simplifyKnownSafeCallPatterns(controlFlowGraph);
            List<TemporaryVal> listAnalyze = this.temporaryValsAnalyzer.analyze(internalClassName, methodNode);
            if (!listAnalyze.isEmpty()) {
                optimizeTemporaryVals(controlFlowGraph, listAnalyze);
            }
            UtilKt.removeUnusedLocalVariables(methodNode);
        }
    }

    private final void simplifyTrivialInstructions(MethodNode methodNode) {
        AbstractInsnNode next;
        InsnList insnList = methodNode.instructions;
        AbstractInsnNode[] array = insnList.toArray();
        Intrinsics.checkNotNullExpressionValue(array, "toArray(...)");
        for (AbstractInsnNode abstractInsnNode : array) {
            Intrinsics.checkNotNull(abstractInsnNode);
            if (matchOpcodes(abstractInsnNode, 21, 87) || matchOpcodes(abstractInsnNode, 23, 87) || matchOpcodes(abstractInsnNode, 25, 87)) {
                AbstractInsnNode next2 = abstractInsnNode.getNext();
                insnList.insert(abstractInsnNode, new InsnNode(0));
                insnList.remove(abstractInsnNode);
                insnList.remove(next2);
            } else if (matchOpcodes(abstractInsnNode, 24, 88) || matchOpcodes(abstractInsnNode, 22, 88)) {
                AbstractInsnNode next3 = abstractInsnNode.getNext();
                insnList.insert(abstractInsnNode, new InsnNode(0));
                insnList.remove(abstractInsnNode);
                insnList.remove(next3);
            }
        }
        List<TryCatchBlockNode> tryCatchBlocks = methodNode.tryCatchBlocks;
        Intrinsics.checkNotNullExpressionValue(tryCatchBlocks, "tryCatchBlocks");
        HashSet hashSet = new HashSet();
        Iterator<T> it = tryCatchBlocks.iterator();
        while (it.hasNext()) {
            hashSet.add(((TryCatchBlockNode) it.next()).start);
        }
        HashSet hashSet2 = hashSet;
        AbstractInsnNode[] array2 = insnList.toArray();
        Intrinsics.checkNotNullExpressionValue(array2, "toArray(...)");
        for (AbstractInsnNode abstractInsnNode2 : array2) {
            if (abstractInsnNode2.getOpcode() == 0) {
                AbstractInsnNode previous = abstractInsnNode2.getPrevious();
                if (!CollectionsKt.contains(hashSet2, previous) && (((next = abstractInsnNode2.getNext()) != null && UtilKt.isMeaningful(next)) || (previous != null && UtilKt.isMeaningful(previous)))) {
                    insnList.remove(abstractInsnNode2);
                }
            }
        }
    }

    /* JADX INFO: compiled from: TemporaryVariablesEliminationTransformer.kt */
    @Metadata(d1 = {"\u0000>\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0010 \n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0004\b\u0002\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0016\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\t2\u0006\u0010\u0010\u001a\u00020\u000bJ\u0014\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\u000b0\u00122\u0006\u0010\u000f\u001a\u00020\tJ\u000e\u0010\u0013\u001a\u00020\u00142\u0006\u0010\u000f\u001a\u00020\tJ\u0016\u0010\u0015\u001a\u00020\u00142\u0006\u0010\u000f\u001a\u00020\t2\u0006\u0010\u0016\u001a\u00020\u000bJ\u0006\u0010\u0017\u001a\u00020\u000eR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006R6\u0010\u0007\u001a*\u0012\u0004\u0012\u00020\t\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u000b0\n0\bj\u0014\u0012\u0004\u0012\u00020\t\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u000b0\n`\fX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0018"}, d2 = {"Lorg/jetbrains/kotlin/codegen/optimization/temporaryVals/TemporaryVariablesEliminationTransformer$ControlFlowGraph;", "", "methodNode", "Lorg/jetbrains/org/objectweb/asm/tree/MethodNode;", "(Lorg/jetbrains/org/objectweb/asm/tree/MethodNode;)V", "getMethodNode", "()Lorg/jetbrains/org/objectweb/asm/tree/MethodNode;", "nonTrivialPredecessors", "Ljava/util/HashMap;", "Lorg/jetbrains/org/objectweb/asm/tree/LabelNode;", "", "Lorg/jetbrains/org/objectweb/asm/tree/AbstractInsnNode;", "Lkotlin/collections/HashMap;", "addNonTrivialPredecessor", "", CoroutineCodegenUtilKt.COROUTINE_LABEL_FIELD_NAME, "pred", "getAllPredecessors", "", "hasNonTrivialPredecessors", "", "hasSinglePredecessor", "expectedPredecessor", "reset", Marker.BACKEND}, k = 1, mv = {1, 9, 0}, xi = 48)
    private static final class ControlFlowGraph {
        private final MethodNode methodNode;
        private final HashMap<LabelNode, List<AbstractInsnNode>> nonTrivialPredecessors;

        public ControlFlowGraph(MethodNode methodNode) {
            Intrinsics.checkNotNullParameter(methodNode, "methodNode");
            this.methodNode = methodNode;
            this.nonTrivialPredecessors = new HashMap<>();
        }

        public final MethodNode getMethodNode() {
            return this.methodNode;
        }

        public final void reset() {
            this.nonTrivialPredecessors.clear();
        }

        public final void addNonTrivialPredecessor(LabelNode label, AbstractInsnNode pred) {
            Intrinsics.checkNotNullParameter(label, "label");
            Intrinsics.checkNotNullParameter(pred, "pred");
            HashMap<LabelNode, List<AbstractInsnNode>> map = this.nonTrivialPredecessors;
            SmartList smartList = map.get(label);
            if (smartList == null) {
                smartList = new SmartList();
                map.put(label, smartList);
            }
            smartList.add(pred);
        }

        public final boolean hasNonTrivialPredecessors(LabelNode label) {
            Intrinsics.checkNotNullParameter(label, "label");
            return this.nonTrivialPredecessors.containsKey(label);
        }

        public final List<AbstractInsnNode> getAllPredecessors(LabelNode label) {
            int opcode;
            Intrinsics.checkNotNullParameter(label, "label");
            ArrayList arrayList = new ArrayList();
            AbstractInsnNode previous = label.getPrevious();
            if (previous.getOpcode() != 167 && ((172 > (opcode = previous.getOpcode()) || opcode >= 178) && previous.getOpcode() != 191)) {
                arrayList.add(previous);
            }
            List<AbstractInsnNode> listEmptyList = this.nonTrivialPredecessors.get(label);
            if (listEmptyList == null) {
                listEmptyList = CollectionsKt.emptyList();
            }
            arrayList.addAll(listEmptyList);
            return arrayList;
        }

        public final boolean hasSinglePredecessor(LabelNode label, AbstractInsnNode expectedPredecessor) {
            int opcode;
            Intrinsics.checkNotNullParameter(label, "label");
            Intrinsics.checkNotNullParameter(expectedPredecessor, "expectedPredecessor");
            AbstractInsnNode previous = label.getPrevious();
            if (previous.getOpcode() == 167 || ((172 <= (opcode = previous.getOpcode()) && opcode < 178) || previous.getOpcode() == 191)) {
                previous = null;
            } else if (!Intrinsics.areEqual(previous, expectedPredecessor)) {
                return false;
            }
            List<AbstractInsnNode> list = this.nonTrivialPredecessors.get(label);
            if (list == null) {
                return previous != null;
            }
            if (list.size() > 1) {
                return false;
            }
            if (list.size() == 0) {
                return Intrinsics.areEqual(previous, expectedPredecessor);
            }
            return previous == null && Intrinsics.areEqual(list.get(0), expectedPredecessor);
        }
    }

    /* JADX WARN: Type inference failed for: r3v2, types: [java.util.ListIterator] */
    private final void processLabels(ControlFlowGraph cfg) {
        cfg.reset();
        MethodNode methodNode = cfg.getMethodNode();
        InsnList insnList = methodNode.instructions;
        HashSet hashSet = new HashSet();
        AbstractInsnNode first = insnList.getFirst();
        if (first instanceof LabelNode) {
            hashSet.add(first);
        }
        AbstractInsnNode last = insnList.getLast();
        if (last instanceof LabelNode) {
            hashSet.add(last);
        }
        ?? it = insnList.iterator();
        while (it.hasNext()) {
            AbstractInsnNode abstractInsnNode = (AbstractInsnNode) it.next();
            int type = abstractInsnNode.getType();
            if (type == 7) {
                Intrinsics.checkNotNull(abstractInsnNode);
                LabelNode label = ((JumpInsnNode) abstractInsnNode).label;
                Intrinsics.checkNotNullExpressionValue(label, "label");
                processLabels$addCfgEdgeToLabel(hashSet, cfg, abstractInsnNode, label);
            } else if (type == 15) {
                Intrinsics.checkNotNull(abstractInsnNode, "null cannot be cast to non-null type org.jetbrains.org.objectweb.asm.tree.LineNumberNode");
                hashSet.add(((LineNumberNode) abstractInsnNode).start);
            } else if (type == 11) {
                Intrinsics.checkNotNull(abstractInsnNode, "null cannot be cast to non-null type org.jetbrains.org.objectweb.asm.tree.TableSwitchInsnNode");
                TableSwitchInsnNode tableSwitchInsnNode = (TableSwitchInsnNode) abstractInsnNode;
                LabelNode dflt = tableSwitchInsnNode.dflt;
                Intrinsics.checkNotNullExpressionValue(dflt, "dflt");
                processLabels$addCfgEdgeToLabel(hashSet, cfg, abstractInsnNode, dflt);
                List<LabelNode> labels = tableSwitchInsnNode.labels;
                Intrinsics.checkNotNullExpressionValue(labels, "labels");
                processLabels$addCfgEdgesToLabels(hashSet, cfg, abstractInsnNode, labels);
            } else if (type == 12) {
                Intrinsics.checkNotNull(abstractInsnNode, "null cannot be cast to non-null type org.jetbrains.org.objectweb.asm.tree.LookupSwitchInsnNode");
                LookupSwitchInsnNode lookupSwitchInsnNode = (LookupSwitchInsnNode) abstractInsnNode;
                LabelNode dflt2 = lookupSwitchInsnNode.dflt;
                Intrinsics.checkNotNullExpressionValue(dflt2, "dflt");
                processLabels$addCfgEdgeToLabel(hashSet, cfg, abstractInsnNode, dflt2);
                List<LabelNode> labels2 = lookupSwitchInsnNode.labels;
                Intrinsics.checkNotNullExpressionValue(labels2, "labels");
                processLabels$addCfgEdgesToLabels(hashSet, cfg, abstractInsnNode, labels2);
            }
        }
        for (LocalVariableNode localVariableNode : methodNode.localVariables) {
            hashSet.add(localVariableNode.start);
            hashSet.add(localVariableNode.end);
        }
        for (TryCatchBlockNode tryCatchBlockNode : methodNode.tryCatchBlocks) {
            hashSet.add(tryCatchBlockNode.start);
            hashSet.add(tryCatchBlockNode.end);
            LabelNode start = tryCatchBlockNode.start;
            Intrinsics.checkNotNullExpressionValue(start, "start");
            LabelNode handler = tryCatchBlockNode.handler;
            Intrinsics.checkNotNullExpressionValue(handler, "handler");
            processLabels$addCfgEdgeToLabel(hashSet, cfg, start, handler);
        }
        AbstractInsnNode first2 = insnList.getFirst();
        while (first2 != null) {
            if ((first2 instanceof LabelNode) && !hashSet.contains(first2)) {
                AbstractInsnNode next = ((LabelNode) first2).getNext();
                insnList.remove(first2);
                first2 = next;
            } else {
                first2 = first2.getNext();
            }
        }
    }

    private static final void processLabels$addCfgEdgeToLabel(HashSet<LabelNode> hashSet, ControlFlowGraph controlFlowGraph, AbstractInsnNode abstractInsnNode, LabelNode labelNode) {
        hashSet.add(labelNode);
        controlFlowGraph.addNonTrivialPredecessor(labelNode, abstractInsnNode);
    }

    private static final void processLabels$addCfgEdgesToLabels(HashSet<LabelNode> hashSet, ControlFlowGraph controlFlowGraph, AbstractInsnNode abstractInsnNode, Collection<? extends LabelNode> collection) {
        hashSet.addAll(collection);
        Iterator<? extends LabelNode> it = collection.iterator();
        while (it.hasNext()) {
            controlFlowGraph.addNonTrivialPredecessor(it.next(), abstractInsnNode);
        }
    }

    /* JADX WARN: Code duplicated, block: B:29:0x00e7  */
    /* JADX WARN: Code duplicated, block: B:31:0x00f3  */
    /* JADX WARN: Code duplicated, block: B:59:0x01bf A[EDGE_INSN: B:59:0x01bf->B:52:0x01bf BREAK  A[LOOP:1: B:20:0x0098->B:72:?], SYNTHETIC] */
    private final void optimizeTemporaryVals(ControlFlowGraph cfg, List<TemporaryVal> temporaryVals) {
        AbstractInsnNode next;
        InsnList insnList = cfg.getMethodNode().instructions;
        int i = 0;
        int iMax = 0;
        for (TemporaryVal temporaryVal : temporaryVals) {
            if (temporaryVal.getLoadInsns().isEmpty()) {
                int opcode = temporaryVal.getStoreInsn().getOpcode();
                insnList.insertBefore(temporaryVal.getStoreInsn(), new InsnNode((opcode == 54 || opcode == 56 || opcode == 58) ? 87 : 88));
                insnList.remove(temporaryVal.getStoreInsn());
            } else if (temporaryVal.getLoadInsns().size() == 1) {
                VarInsnNode storeInsn = temporaryVal.getStoreInsn();
                VarInsnNode varInsnNode = temporaryVal.getLoadInsns().get(i);
                if (Intrinsics.areEqual(storeInsn.getNext(), varInsnNode)) {
                    insnList.remove(storeInsn);
                    insnList.remove(varInsnNode);
                    break;
                    break;
                }
                AbstractInsnNode next2 = storeInsn.getNext();
                Intrinsics.checkNotNullExpressionValue(next2, "getNext(...)");
                VarInsnNode varInsnNode2 = varInsnNode;
                Iterator<AbstractInsnNode> it = new InsnSequence(next2, varInsnNode2).iterator();
                while (true) {
                    if (it.hasNext()) {
                        if (isIntervening(it.next(), cfg)) {
                            VarInsnNode varInsnNode3 = storeInsn;
                            if (matchOpcodes(varInsnNode3, 58, 25, 25)) {
                                AbstractInsnNode next3 = storeInsn.getNext();
                                Intrinsics.checkNotNull(next3, "null cannot be cast to non-null type org.jetbrains.org.objectweb.asm.tree.VarInsnNode");
                                VarInsnNode varInsnNode4 = (VarInsnNode) next3;
                                AbstractInsnNode next4 = varInsnNode4.getNext();
                                Intrinsics.checkNotNull(next4, "null cannot be cast to non-null type org.jetbrains.org.objectweb.asm.tree.VarInsnNode");
                                if (Intrinsics.areEqual((VarInsnNode) next4, varInsnNode)) {
                                    insnList.remove(varInsnNode3);
                                    insnList.remove(varInsnNode2);
                                    insnList.insert(varInsnNode4, new InsnNode(95));
                                    break;
                                } else {
                                    if (matchOpcodes(varInsnNode3, 58, 178, 25)) {
                                        break;
                                    }
                                    AbstractInsnNode next5 = storeInsn.getNext();
                                    Intrinsics.checkNotNull(next5, "null cannot be cast to non-null type org.jetbrains.org.objectweb.asm.tree.FieldInsnNode");
                                    FieldInsnNode fieldInsnNode = (FieldInsnNode) next5;
                                    next = fieldInsnNode.getNext();
                                    Intrinsics.checkNotNull(next, "null cannot be cast to non-null type org.jetbrains.org.objectweb.asm.tree.VarInsnNode");
                                    if (Intrinsics.areEqual((VarInsnNode) next, varInsnNode) && Type.getType(fieldInsnNode.desc).getSize() == 1) {
                                        insnList.remove(varInsnNode3);
                                        insnList.remove(varInsnNode2);
                                        insnList.insert(fieldInsnNode, new InsnNode(95));
                                        break;
                                    }
                                    break;
                                }
                            }
                            if (matchOpcodes(varInsnNode3, 58, 178, 25)) {
                                break;
                                break;
                            }
                            AbstractInsnNode next6 = storeInsn.getNext();
                            Intrinsics.checkNotNull(next6, "null cannot be cast to non-null type org.jetbrains.org.objectweb.asm.tree.FieldInsnNode");
                            FieldInsnNode fieldInsnNode2 = (FieldInsnNode) next6;
                            next = fieldInsnNode2.getNext();
                            Intrinsics.checkNotNull(next, "null cannot be cast to non-null type org.jetbrains.org.objectweb.asm.tree.VarInsnNode");
                            if (Intrinsics.areEqual((VarInsnNode) next, varInsnNode)) {
                                break;
                            }
                            break;
                            break;
                        }
                    } else {
                        insnList.remove(storeInsn);
                        insnList.remove(varInsnNode);
                        break;
                    }
                }
            } else if (temporaryVal.getLoadInsns().size() == 2) {
                VarInsnNode storeInsn2 = temporaryVal.getStoreInsn();
                VarInsnNode varInsnNode5 = storeInsn2;
                if (matchOpcodes(varInsnNode5, 58, 25, 18, 184, 25)) {
                    AbstractInsnNode next7 = storeInsn2.getNext();
                    AbstractInsnNode next8 = next7.getNext();
                    AbstractInsnNode next9 = next8.getNext();
                    AbstractInsnNode next10 = next9.getNext();
                    Intrinsics.checkNotNull(next7, "null cannot be cast to non-null type org.jetbrains.org.objectweb.asm.tree.VarInsnNode");
                    if (((VarInsnNode) next7).var == temporaryVal.getIndex()) {
                        Intrinsics.checkNotNull(next8, "null cannot be cast to non-null type org.jetbrains.org.objectweb.asm.tree.LdcInsnNode");
                        if (((LdcInsnNode) next8).cst instanceof String) {
                            Intrinsics.checkNotNull(next9);
                            if (RedundantNullCheckMethodTransformerKt.isCheckExpressionValueIsNotNull(next9) || RedundantNullCheckMethodTransformerKt.isCheckNotNullWithMessage(next9)) {
                                Intrinsics.checkNotNull(next10, "null cannot be cast to non-null type org.jetbrains.org.objectweb.asm.tree.VarInsnNode");
                                if (((VarInsnNode) next10).var == temporaryVal.getIndex()) {
                                    insnList.remove(varInsnNode5);
                                    insnList.remove(next7);
                                    insnList.insertBefore(next8, new InsnNode(89));
                                    insnList.remove(next10);
                                    iMax = Math.max(iMax, 1);
                                }
                            }
                        }
                    }
                }
            }
            i = 0;
        }
        cfg.getMethodNode().maxStack += iMax;
    }

    /* JADX WARN: Code duplicated, block: B:44:0x0174 A[LOOP:1: B:42:0x016e->B:44:0x0174, LOOP_END] */
    private final void simplifyKnownSafeCallPatterns(ControlFlowGraph cfg) {
        AbstractInsnNode next;
        InsnList insnList = cfg.getMethodNode().instructions;
        AbstractInsnNode[] array = insnList.toArray();
        Intrinsics.checkNotNullExpressionValue(array, "toArray(...)");
        int iMax = 0;
        for (AbstractInsnNode abstractInsnNode : array) {
            Intrinsics.checkNotNull(abstractInsnNode);
            if (matchOpcodes(abstractInsnNode, 25, 199)) {
                VarInsnNode varInsnNode = (VarInsnNode) abstractInsnNode;
                AbstractInsnNode next2 = varInsnNode.getNext();
                Intrinsics.checkNotNull(next2, "null cannot be cast to non-null type org.jetbrains.org.objectweb.asm.tree.JumpInsnNode");
                JumpInsnNode jumpInsnNode = (JumpInsnNode) next2;
                LabelNode labelNode = jumpInsnNode.label;
                LabelNode label = jumpInsnNode.label;
                Intrinsics.checkNotNullExpressionValue(label, "label");
                JumpInsnNode jumpInsnNode2 = jumpInsnNode;
                if (cfg.hasSinglePredecessor(label, jumpInsnNode2) && (next = labelNode.getNext()) != null) {
                    if (next.getOpcode() == 25) {
                        VarInsnNode varInsnNode2 = (VarInsnNode) next;
                        if (varInsnNode2.var == varInsnNode.var) {
                            insnList.insertBefore(jumpInsnNode2, new InsnNode(89));
                            insnList.insert(jumpInsnNode2, new InsnNode(87));
                            insnList.remove(varInsnNode2);
                            iMax = Math.max(iMax, 1);
                        } else {
                            AbstractInsnNode next3 = varInsnNode2.getNext();
                            if (next3.getOpcode() == 25) {
                                Intrinsics.checkNotNull(next3, "null cannot be cast to non-null type org.jetbrains.org.objectweb.asm.tree.VarInsnNode");
                                VarInsnNode varInsnNode3 = (VarInsnNode) next3;
                                if (varInsnNode3.var == varInsnNode.var) {
                                    insnList.insertBefore(jumpInsnNode2, new InsnNode(89));
                                    insnList.insert(jumpInsnNode2, new InsnNode(87));
                                    insnList.insert(varInsnNode2, new InsnNode(95));
                                    insnList.remove(varInsnNode3);
                                    iMax = Math.max(iMax, 1);
                                }
                            }
                        }
                    } else if (matchOpcodes(next, 178, 25)) {
                        FieldInsnNode fieldInsnNode = (FieldInsnNode) next;
                        AbstractInsnNode next4 = fieldInsnNode.getNext();
                        Intrinsics.checkNotNull(next4, "null cannot be cast to non-null type org.jetbrains.org.objectweb.asm.tree.VarInsnNode");
                        VarInsnNode varInsnNode4 = (VarInsnNode) next4;
                        if (Type.getType(fieldInsnNode.desc).getSize() == 1 && varInsnNode4.var == varInsnNode.var) {
                            insnList.insertBefore(jumpInsnNode2, new InsnNode(89));
                            insnList.insert(jumpInsnNode2, new InsnNode(87));
                            insnList.insert(fieldInsnNode, new InsnNode(95));
                            insnList.remove(varInsnNode4);
                            iMax = Math.max(iMax, 1);
                        }
                    }
                }
            } else if (matchOpcodes(abstractInsnNode, 25, 198)) {
                AbstractInsnNode next5 = abstractInsnNode.getNext();
                Intrinsics.checkNotNull(next5, "null cannot be cast to non-null type org.jetbrains.org.objectweb.asm.tree.JumpInsnNode");
                LabelNode labelNode2 = ((JumpInsnNode) next5).label;
                Intrinsics.checkNotNull(labelNode2);
                List<AbstractInsnNode> allPredecessors = cfg.getAllPredecessors(labelNode2);
                if (!(allPredecessors instanceof Collection) || !allPredecessors.isEmpty()) {
                    Iterator<T> it = allPredecessors.iterator();
                    while (true) {
                        if (it.hasNext()) {
                            if (!isRewritableSafeCallPart((AbstractInsnNode) it.next())) {
                            }
                        } else {
                            for (AbstractInsnNode abstractInsnNode2 : allPredecessors) {
                                Intrinsics.checkNotNull(insnList);
                                rewriteSafeCallPart(abstractInsnNode2, insnList);
                            }
                            insnList.insert(labelNode2, new InsnNode(87));
                            iMax = Math.max(iMax, 1);
                        }
                    }
                } else {
                    while (r8.hasNext()) {
                        Intrinsics.checkNotNull(insnList);
                        rewriteSafeCallPart(abstractInsnNode2, insnList);
                    }
                    insnList.insert(labelNode2, new InsnNode(87));
                    iMax = Math.max(iMax, 1);
                }
            }
        }
        cfg.getMethodNode().maxStack += iMax;
    }

    private final boolean isRewritableSafeCallPart(AbstractInsnNode branchInsn) {
        AbstractInsnNode previous = branchInsn.getPrevious();
        if (previous != null && matchOpcodes(previous, 25, 198)) {
            VarInsnNode varInsnNode = (VarInsnNode) previous;
            AbstractInsnNode next = varInsnNode.getNext();
            Intrinsics.checkNotNull(next, "null cannot be cast to non-null type org.jetbrains.org.objectweb.asm.tree.JumpInsnNode");
            AbstractInsnNode next2 = ((JumpInsnNode) next).getNext();
            if (next2.getOpcode() == 25) {
                Intrinsics.checkNotNull(next2, "null cannot be cast to non-null type org.jetbrains.org.objectweb.asm.tree.VarInsnNode");
                VarInsnNode varInsnNode2 = (VarInsnNode) next2;
                if (varInsnNode2.var == varInsnNode.var) {
                    return true;
                }
                AbstractInsnNode next3 = varInsnNode2.getNext();
                if (next3.getOpcode() == 25) {
                    Intrinsics.checkNotNull(next3, "null cannot be cast to non-null type org.jetbrains.org.objectweb.asm.tree.VarInsnNode");
                    if (((VarInsnNode) next3).var == varInsnNode.var) {
                        return true;
                    }
                }
            } else {
                Intrinsics.checkNotNull(next2);
                if (matchOpcodes(next2, 178, 25)) {
                    FieldInsnNode fieldInsnNode = (FieldInsnNode) next2;
                    if (Type.getType(fieldInsnNode.desc).getSize() != 1) {
                        return false;
                    }
                    AbstractInsnNode next4 = fieldInsnNode.getNext();
                    Intrinsics.checkNotNull(next4, "null cannot be cast to non-null type org.jetbrains.org.objectweb.asm.tree.VarInsnNode");
                    if (((VarInsnNode) next4).var == varInsnNode.var) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private final void rewriteSafeCallPart(AbstractInsnNode branchInsn, InsnList insnList) {
        AbstractInsnNode previous = branchInsn.getPrevious();
        Intrinsics.checkNotNull(previous, "null cannot be cast to non-null type org.jetbrains.org.objectweb.asm.tree.VarInsnNode");
        VarInsnNode varInsnNode = (VarInsnNode) previous;
        AbstractInsnNode next = varInsnNode.getNext();
        Intrinsics.checkNotNull(next, "null cannot be cast to non-null type org.jetbrains.org.objectweb.asm.tree.JumpInsnNode");
        JumpInsnNode jumpInsnNode = (JumpInsnNode) next;
        AbstractInsnNode next2 = jumpInsnNode.getNext();
        if (next2.getOpcode() == 25) {
            Intrinsics.checkNotNull(next2, "null cannot be cast to non-null type org.jetbrains.org.objectweb.asm.tree.VarInsnNode");
            VarInsnNode varInsnNode2 = (VarInsnNode) next2;
            if (varInsnNode2.var == varInsnNode.var) {
                insnList.insertBefore(jumpInsnNode, new InsnNode(89));
                insnList.remove(varInsnNode2);
                return;
            }
            AbstractInsnNode next3 = varInsnNode2.getNext();
            Intrinsics.checkNotNull(next3, "null cannot be cast to non-null type org.jetbrains.org.objectweb.asm.tree.VarInsnNode");
            insnList.insertBefore(jumpInsnNode, new InsnNode(89));
            insnList.remove((VarInsnNode) next3);
            insnList.insert(varInsnNode2, new InsnNode(95));
            return;
        }
        AbstractInsnNode next4 = next2.getNext();
        insnList.insertBefore(jumpInsnNode, new InsnNode(89));
        insnList.remove(next4);
        insnList.insert(next2, new InsnNode(95));
    }

    private final boolean matchOpcodes(AbstractInsnNode abstractInsnNode, int... iArr) {
        for (int i : iArr) {
            if (abstractInsnNode.getOpcode() != i || (abstractInsnNode = abstractInsnNode.getNext()) == null) {
                return false;
            }
        }
        return true;
    }

    private final boolean isIntervening(AbstractInsnNode abstractInsnNode, ControlFlowGraph controlFlowGraph) {
        int type = abstractInsnNode.getType();
        if (type != 0) {
            if (type == 8) {
                Intrinsics.checkNotNull(abstractInsnNode, "null cannot be cast to non-null type org.jetbrains.org.objectweb.asm.tree.LabelNode");
                return controlFlowGraph.hasNonTrivialPredecessors((LabelNode) abstractInsnNode);
            }
            if (type != 14 && type != 15) {
                return true;
            }
        } else if (abstractInsnNode.getOpcode() != 0) {
            return true;
        }
        return false;
    }
}
