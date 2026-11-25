package org.jetbrains.kotlin.codegen.optimization.common;

import androidx.constraintlayout.core.motion.utils.TypedValues;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import com.reandroid.dex.sections.Marker;
import gnu.trove.TIntHashSet;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.metadata.jvm.deserialization.JvmProtoBufUtil;
import org.jetbrains.org.objectweb.asm.tree.AbstractInsnNode;
import org.jetbrains.org.objectweb.asm.tree.InsnList;
import org.jetbrains.org.objectweb.asm.tree.JumpInsnNode;
import org.jetbrains.org.objectweb.asm.tree.LabelNode;
import org.jetbrains.org.objectweb.asm.tree.LookupSwitchInsnNode;
import org.jetbrains.org.objectweb.asm.tree.MethodNode;
import org.jetbrains.org.objectweb.asm.tree.TableSwitchInsnNode;
import org.jetbrains.org.objectweb.asm.tree.TryCatchBlockNode;

/* JADX INFO: compiled from: ControlFlowGraph.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0011\n\u0002\u0010!\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010 \n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\u0018\u0000 \u00122\u00020\u0001:\u0002\u0011\u0012B\u000f\b\u0002\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0014\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\b0\f2\u0006\u0010\r\u001a\u00020\bJ\u0014\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\b0\f2\u0006\u0010\u000e\u001a\u00020\u000fJ\u0014\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\b0\f2\u0006\u0010\r\u001a\u00020\bJ\u0014\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\b0\f2\u0006\u0010\u000e\u001a\u00020\u000fR\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u001c\u0010\u0005\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\b0\u00070\u0006X\u0082\u0004¢\u0006\u0004\n\u0002\u0010\tR\u001c\u0010\n\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\b0\u00070\u0006X\u0082\u0004¢\u0006\u0004\n\u0002\u0010\t¨\u0006\u0013"}, d2 = {"Lorg/jetbrains/kotlin/codegen/optimization/common/ControlFlowGraph;", "", "insns", "Lorg/jetbrains/org/objectweb/asm/tree/InsnList;", "(Lorg/jetbrains/org/objectweb/asm/tree/InsnList;)V", "predecessors", "", "", "", "[Ljava/util/List;", "successors", "getPredecessorsIndices", "", "index", "insn", "Lorg/jetbrains/org/objectweb/asm/tree/AbstractInsnNode;", "getSuccessorsIndices", "Builder", "Companion", Marker.BACKEND}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ControlFlowGraph {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private final InsnList insns;
    private final List<Integer>[] predecessors;
    private final List<Integer>[] successors;

    public /* synthetic */ ControlFlowGraph(InsnList insnList, DefaultConstructorMarker defaultConstructorMarker) {
        this(insnList);
    }

    @JvmStatic
    public static final ControlFlowGraph build(MethodNode methodNode, boolean z) {
        return INSTANCE.build(methodNode, z);
    }

    private ControlFlowGraph(InsnList insnList) {
        this.insns = insnList;
        int size = insnList.size();
        List<Integer>[] listArr = new List[size];
        for (int i = 0; i < size; i++) {
            listArr[i] = new ArrayList(2);
        }
        this.successors = listArr;
        int size2 = this.insns.size();
        List<Integer>[] listArr2 = new List[size2];
        for (int i2 = 0; i2 < size2; i2++) {
            listArr2[i2] = new ArrayList(2);
        }
        this.predecessors = listArr2;
    }

    public final List<Integer> getSuccessorsIndices(AbstractInsnNode insn) {
        Intrinsics.checkNotNullParameter(insn, "insn");
        return getSuccessorsIndices(this.insns.indexOf(insn));
    }

    public final List<Integer> getSuccessorsIndices(int index) {
        return this.successors[index];
    }

    public final List<Integer> getPredecessorsIndices(AbstractInsnNode insn) {
        Intrinsics.checkNotNullParameter(insn, "insn");
        return getPredecessorsIndices(this.insns.indexOf(insn));
    }

    public final List<Integer> getPredecessorsIndices(int index) {
        return this.predecessors[index];
    }

    /* JADX INFO: compiled from: ControlFlowGraph.kt */
    @Metadata(d1 = {"\u0000x\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\u0011\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0015\n\u0000\n\u0002\u0010\u0018\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u000b\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\b\u0002\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\u0006\u0010\u001d\u001a\u00020\u001eJ\b\u0010\u001f\u001a\u00020 H\u0002J\b\u0010!\u001a\u00020 H\u0002J\u0010\u0010\"\u001a\u00020 2\u0006\u0010#\u001a\u00020\u0010H\u0002J\b\u0010$\u001a\u00020 H\u0002J\b\u0010%\u001a\u00020 H\u0002J\u0018\u0010&\u001a\u00020 2\u0006\u0010'\u001a\u00020\u00102\u0006\u0010(\u001a\u00020\u0010H\u0002J\u0018\u0010)\u001a\u00020 2\u0006\u0010'\u001a\u00020\u00102\u0006\u0010(\u001a\u00020\u0010H\u0002J \u0010*\u001a\u00020 2\u0006\u0010+\u001a\u00020,2\u0006\u0010#\u001a\u00020\u00102\u0006\u0010-\u001a\u00020\u0010H\u0002J\u0018\u0010.\u001a\u00020 2\u0006\u0010#\u001a\u00020\u00102\u0006\u0010+\u001a\u00020/H\u0002J\u0010\u00100\u001a\u00020 2\u0006\u0010#\u001a\u00020\u0010H\u0002J\u0018\u00101\u001a\u00020 2\u0006\u0010#\u001a\u00020\u00102\u0006\u0010+\u001a\u000202H\u0002R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u001e\u0010\u0007\u001a\u0010\u0012\f\u0012\n\u0012\u0004\u0012\u00020\n\u0018\u00010\t0\bX\u0082\u0004¢\u0006\u0004\n\u0002\u0010\u000bR\u0016\u0010\f\u001a\n \u000e*\u0004\u0018\u00010\r0\rX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u000f\u001a\u00020\u0010X\u0082\u0004¢\u0006\u0002\n\u0000R\u0016\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\u00120\bX\u0082\u0004¢\u0006\u0004\n\u0002\u0010\u0013R\u000e\u0010\u0014\u001a\u00020\u0015X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0016\u001a\u00020\u0017X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0018\u001a\u00020\u0010X\u0082\u000e¢\u0006\u0002\n\u0000R\u0018\u0010\u0019\u001a\u00020\u0010*\u00020\u001a8BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\u001b\u0010\u001c¨\u00063"}, d2 = {"Lorg/jetbrains/kotlin/codegen/optimization/common/ControlFlowGraph$Builder;", "", "method", "Lorg/jetbrains/org/objectweb/asm/tree/MethodNode;", "followExceptions", "", "(Lorg/jetbrains/org/objectweb/asm/tree/MethodNode;Z)V", "handlers", "", "", "Lorg/jetbrains/org/objectweb/asm/tree/TryCatchBlockNode;", "[Ljava/util/List;", "instructions", "Lorg/jetbrains/org/objectweb/asm/tree/InsnList;", JvmProtoBufUtil.PLATFORM_TYPE_ID, "nInsns", "", "predecessors", "Lgnu/trove/TIntHashSet;", "[Lgnu/trove/TIntHashSet;", "queue", "", "queued", "", "top", "indexOf", "Lorg/jetbrains/org/objectweb/asm/tree/AbstractInsnNode;", "getIndexOf", "(Lorg/jetbrains/org/objectweb/asm/tree/AbstractInsnNode;)I", JsonPOJOBuilder.DEFAULT_BUILD_METHOD, "Lorg/jetbrains/kotlin/codegen/optimization/common/ControlFlowGraph;", "checkAssertions", "", "computeExceptionHandlersForEachInsn", "enqueue", "insn", "initControlFlowAnalysis", "traverseCfg", "visitEdge", TypedValues.TransitionType.S_FROM, "to", "visitExceptionEdge", "visitJumpInsnNode", "insnNode", "Lorg/jetbrains/org/objectweb/asm/tree/JumpInsnNode;", "insnOpcode", "visitLookupSwitchInsnNode", "Lorg/jetbrains/org/objectweb/asm/tree/LookupSwitchInsnNode;", "visitOpInsn", "visitTableSwitchInsnNode", "Lorg/jetbrains/org/objectweb/asm/tree/TableSwitchInsnNode;", Marker.BACKEND}, k = 1, mv = {1, 9, 0}, xi = 48)
    private static final class Builder {
        private final boolean followExceptions;
        private final List<TryCatchBlockNode>[] handlers;
        private final InsnList instructions;
        private final MethodNode method;
        private final int nInsns;
        private final TIntHashSet[] predecessors;
        private final int[] queue;
        private final boolean[] queued;
        private int top;

        public Builder(MethodNode method, boolean z) {
            Intrinsics.checkNotNullParameter(method, "method");
            this.method = method;
            this.followExceptions = z;
            InsnList insnList = method.instructions;
            this.instructions = insnList;
            int size = insnList.size();
            this.nInsns = size;
            this.handlers = new List[size];
            this.queued = new boolean[size];
            this.queue = new int[size];
            TIntHashSet[] tIntHashSetArr = new TIntHashSet[size];
            for (int i = 0; i < size; i++) {
                tIntHashSetArr[i] = new TIntHashSet();
            }
            this.predecessors = tIntHashSetArr;
        }

        private final int getIndexOf(AbstractInsnNode abstractInsnNode) {
            return this.instructions.indexOf(abstractInsnNode);
        }

        public final ControlFlowGraph build() {
            InsnList instructions = this.method.instructions;
            Intrinsics.checkNotNullExpressionValue(instructions, "instructions");
            ControlFlowGraph controlFlowGraph = new ControlFlowGraph(instructions, null);
            if (this.nInsns == 0) {
                return controlFlowGraph;
            }
            checkAssertions();
            computeExceptionHandlersForEachInsn();
            initControlFlowAnalysis();
            traverseCfg();
            TIntHashSet[] tIntHashSetArr = this.predecessors;
            int length = tIntHashSetArr.length;
            for (int i = 0; i < length; i++) {
                int[] array = tIntHashSetArr[i].toArray();
                Intrinsics.checkNotNullExpressionValue(array, "toArray(...)");
                for (int i2 : array) {
                    controlFlowGraph.predecessors[i].add(Integer.valueOf(i2));
                    controlFlowGraph.successors[i2].add(Integer.valueOf(i));
                }
            }
            return controlFlowGraph;
        }

        /* JADX WARN: Code duplicated, block: B:25:0x0059  */
        private final void traverseCfg() {
            while (true) {
                int i = this.top;
                if (i <= 0) {
                    return;
                }
                int[] iArr = this.queue;
                int i2 = i - 1;
                this.top = i2;
                int i3 = iArr[i2];
                AbstractInsnNode abstractInsnNode = this.method.instructions.get(i3);
                int opcode = abstractInsnNode.getOpcode();
                int type = abstractInsnNode.getType();
                if (type == 7) {
                    Intrinsics.checkNotNull(abstractInsnNode, "null cannot be cast to non-null type org.jetbrains.org.objectweb.asm.tree.JumpInsnNode");
                    visitJumpInsnNode((JumpInsnNode) abstractInsnNode, i3, opcode);
                } else if (type == 8) {
                    visitOpInsn(i3);
                } else if (type == 11) {
                    Intrinsics.checkNotNull(abstractInsnNode, "null cannot be cast to non-null type org.jetbrains.org.objectweb.asm.tree.TableSwitchInsnNode");
                    visitTableSwitchInsnNode(i3, (TableSwitchInsnNode) abstractInsnNode);
                } else if (type == 12) {
                    Intrinsics.checkNotNull(abstractInsnNode, "null cannot be cast to non-null type org.jetbrains.org.objectweb.asm.tree.LookupSwitchInsnNode");
                    visitLookupSwitchInsnNode(i3, (LookupSwitchInsnNode) abstractInsnNode);
                } else if (type == 14 || type == 15) {
                    visitOpInsn(i3);
                } else if (opcode != 191 && (opcode < 172 || opcode > 177)) {
                    visitOpInsn(i3);
                }
                List<TryCatchBlockNode> list = this.handlers[i3];
                if (list != null) {
                    Iterator<T> it = list.iterator();
                    while (it.hasNext()) {
                        LabelNode handler = ((TryCatchBlockNode) it.next()).handler;
                        Intrinsics.checkNotNullExpressionValue(handler, "handler");
                        visitExceptionEdge(i3, getIndexOf(handler));
                    }
                }
            }
        }

        private final void checkAssertions() {
            Iterable instructions = this.instructions;
            Intrinsics.checkNotNullExpressionValue(instructions, "instructions");
            Iterable<AbstractInsnNode> iterable = instructions;
            if ((iterable instanceof Collection) && ((Collection) iterable).isEmpty()) {
                return;
            }
            for (AbstractInsnNode abstractInsnNode : iterable) {
                if (abstractInsnNode.getOpcode() == 168 || abstractInsnNode.getOpcode() == 169) {
                    throw new AssertionError("Subroutines are deprecated since Java 6");
                }
            }
        }

        private final void visitOpInsn(int insn) {
            visitEdge(insn, insn + 1);
        }

        private final void visitTableSwitchInsnNode(int insn, TableSwitchInsnNode insnNode) {
            LabelNode dflt = insnNode.dflt;
            Intrinsics.checkNotNullExpressionValue(dflt, "dflt");
            visitEdge(insn, getIndexOf(dflt));
            for (LabelNode labelNode : insnNode.labels) {
                Intrinsics.checkNotNull(labelNode);
                visitEdge(insn, getIndexOf(labelNode));
            }
        }

        private final void visitLookupSwitchInsnNode(int insn, LookupSwitchInsnNode insnNode) {
            LabelNode dflt = insnNode.dflt;
            Intrinsics.checkNotNullExpressionValue(dflt, "dflt");
            visitEdge(insn, getIndexOf(dflt));
            for (LabelNode labelNode : insnNode.labels) {
                Intrinsics.checkNotNull(labelNode);
                visitEdge(insn, getIndexOf(labelNode));
            }
        }

        private final void visitJumpInsnNode(JumpInsnNode insnNode, int insn, int insnOpcode) {
            if (insnOpcode != 167 && insnOpcode != 168) {
                visitEdge(insn, insn + 1);
            }
            LabelNode label = insnNode.label;
            Intrinsics.checkNotNullExpressionValue(label, "label");
            visitEdge(insn, getIndexOf(label));
        }

        private final void initControlFlowAnalysis() {
            this.queued[0] = true;
            int[] iArr = this.queue;
            int i = this.top;
            this.top = i + 1;
            iArr[i] = 0;
        }

        private final void computeExceptionHandlersForEachInsn() {
            for (TryCatchBlockNode tryCatchBlockNode : this.method.tryCatchBlocks) {
                LabelNode start = tryCatchBlockNode.start;
                Intrinsics.checkNotNullExpressionValue(start, "start");
                LabelNode end = tryCatchBlockNode.end;
                Intrinsics.checkNotNullExpressionValue(end, "end");
                int indexOf = getIndexOf(end);
                for (int indexOf2 = getIndexOf(start); indexOf2 < indexOf; indexOf2++) {
                    ArrayList arrayList = this.handlers[indexOf2];
                    if (arrayList == null) {
                        ArrayList arrayList2 = new ArrayList();
                        this.handlers[indexOf2] = arrayList2;
                        arrayList = arrayList2;
                    }
                    Intrinsics.checkNotNull(tryCatchBlockNode);
                    arrayList.add(tryCatchBlockNode);
                }
            }
        }

        private final void visitExceptionEdge(int from, int to) {
            if (this.followExceptions) {
                this.predecessors[to].add(from);
            }
            enqueue(to);
        }

        private final void visitEdge(int from, int to) {
            this.predecessors[to].add(from);
            enqueue(to);
        }

        private final void enqueue(int insn) {
            boolean[] zArr = this.queued;
            if (zArr[insn]) {
                return;
            }
            zArr[insn] = true;
            int[] iArr = this.queue;
            int i = this.top;
            this.top = i + 1;
            iArr[i] = insn;
        }
    }

    /* JADX INFO: compiled from: ControlFlowGraph.kt */
    @Metadata(d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u001a\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\b\b\u0002\u0010\u0007\u001a\u00020\bH\u0007¨\u0006\t"}, d2 = {"Lorg/jetbrains/kotlin/codegen/optimization/common/ControlFlowGraph$Companion;", "", "()V", JsonPOJOBuilder.DEFAULT_BUILD_METHOD, "Lorg/jetbrains/kotlin/codegen/optimization/common/ControlFlowGraph;", "node", "Lorg/jetbrains/org/objectweb/asm/tree/MethodNode;", "followExceptions", "", Marker.BACKEND}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public static /* synthetic */ ControlFlowGraph build$default(Companion companion, MethodNode methodNode, boolean z, int i, Object obj) {
            if ((i & 2) != 0) {
                z = true;
            }
            return companion.build(methodNode, z);
        }

        @JvmStatic
        public final ControlFlowGraph build(MethodNode node, boolean followExceptions) {
            Intrinsics.checkNotNullParameter(node, "node");
            return new Builder(node, followExceptions).build();
        }
    }
}
