package org.jetbrains.kotlin.codegen.optimization.common;

import com.reandroid.dex.sections.Marker;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.metadata.jvm.deserialization.JvmProtoBufUtil;
import org.jetbrains.org.objectweb.asm.tree.AbstractInsnNode;
import org.jetbrains.org.objectweb.asm.tree.InsnList;
import org.jetbrains.org.objectweb.asm.tree.JumpInsnNode;
import org.jetbrains.org.objectweb.asm.tree.LabelNode;
import org.jetbrains.org.objectweb.asm.tree.LocalVariableNode;
import org.jetbrains.org.objectweb.asm.tree.LookupSwitchInsnNode;
import org.jetbrains.org.objectweb.asm.tree.MethodNode;
import org.jetbrains.org.objectweb.asm.tree.TableSwitchInsnNode;
import org.jetbrains.org.objectweb.asm.tree.TryCatchBlockNode;

/* JADX INFO: compiled from: InstructionLivenessAnalyzer.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000l\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\u0011\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0018\n\u0002\b\u0003\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u0015\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0002\b\t\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0017\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\u0006\u0010\u001f\u001a\u00020\u0010J\b\u0010 \u001a\u00020!H\u0002J\u0010\u0010\"\u001a\u00020!2\u0006\u0010#\u001a\u00020\u0003H\u0002J\b\u0010$\u001a\u00020!H\u0002J\b\u0010%\u001a\u00020!H\u0002J\b\u0010&\u001a\u00020!H\u0002J\u0010\u0010'\u001a\u00020!2\u0006\u0010(\u001a\u00020\u0014H\u0002J \u0010)\u001a\u00020!2\u0006\u0010*\u001a\u00020+2\u0006\u0010(\u001a\u00020\u00142\u0006\u0010,\u001a\u00020\u0014H\u0002J\u0010\u0010-\u001a\u00020!2\u0006\u0010*\u001a\u00020.H\u0002J\u0010\u0010/\u001a\u00020!2\u0006\u0010(\u001a\u00020\u0014H\u0002J\u0010\u00100\u001a\u00020!2\u0006\u0010*\u001a\u000201H\u0002R\u001e\u0010\u0007\u001a\u0010\u0012\f\u0012\n\u0012\u0004\u0012\u00020\n\u0018\u00010\t0\bX\u0082\u0004¢\u0006\u0004\n\u0002\u0010\u000bR\u0016\u0010\f\u001a\n \u000e*\u0004\u0018\u00010\r0\rX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u000f\u001a\u00020\u0010X\u0082\u0004¢\u0006\u0002\n\u0000R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0012R\u000e\u0010\u0013\u001a\u00020\u0014X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0015\u001a\u00020\u0016X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0017\u001a\u00020\u0010X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0018\u001a\u00020\u0014X\u0082\u000e¢\u0006\u0002\n\u0000R\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0019\u0010\u001aR\u0018\u0010\u001b\u001a\u00020\u0014*\u00020\u001c8BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\u001d\u0010\u001e¨\u00062"}, d2 = {"Lorg/jetbrains/kotlin/codegen/optimization/common/InstructionLivenessAnalyzer;", "", "method", "Lorg/jetbrains/org/objectweb/asm/tree/MethodNode;", "visitExceptionHandlers", "", "(Lorg/jetbrains/org/objectweb/asm/tree/MethodNode;Z)V", "handlers", "", "", "Lorg/jetbrains/org/objectweb/asm/tree/TryCatchBlockNode;", "[Ljava/util/List;", "instructions", "Lorg/jetbrains/org/objectweb/asm/tree/InsnList;", JvmProtoBufUtil.PLATFORM_TYPE_ID, "isLive", "", "getMethod", "()Lorg/jetbrains/org/objectweb/asm/tree/MethodNode;", "nInsns", "", "queue", "", "queued", "top", "getVisitExceptionHandlers", "()Z", "indexOf", "Lorg/jetbrains/org/objectweb/asm/tree/AbstractInsnNode;", "getIndexOf", "(Lorg/jetbrains/org/objectweb/asm/tree/AbstractInsnNode;)I", "analyze", "checkAssertions", "", "computeExceptionHandlersForEachInsn", "m", "initControlFlowAnalysis", "localVariableAndTryCatchBlockLabelsAreAlwaysLive", "traverseCfg", "visitControlFlowEdge", "insn", "visitJumpInsnNode", "insnNode", "Lorg/jetbrains/org/objectweb/asm/tree/JumpInsnNode;", "insnOpcode", "visitLookupSwitchInsnNode", "Lorg/jetbrains/org/objectweb/asm/tree/LookupSwitchInsnNode;", "visitOpInsn", "visitTableSwitchInsnNode", "Lorg/jetbrains/org/objectweb/asm/tree/TableSwitchInsnNode;", Marker.BACKEND}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class InstructionLivenessAnalyzer {
    private final List<TryCatchBlockNode>[] handlers;
    private final InsnList instructions;
    private final boolean[] isLive;
    private final MethodNode method;
    private final int nInsns;
    private final int[] queue;
    private final boolean[] queued;
    private int top;
    private final boolean visitExceptionHandlers;

    public InstructionLivenessAnalyzer(MethodNode method, boolean z) {
        Intrinsics.checkNotNullParameter(method, "method");
        this.method = method;
        this.visitExceptionHandlers = z;
        InsnList insnList = method.instructions;
        this.instructions = insnList;
        int size = insnList.size();
        this.nInsns = size;
        this.isLive = new boolean[size];
        this.handlers = new List[size];
        this.queued = new boolean[size];
        this.queue = new int[size];
    }

    public /* synthetic */ InstructionLivenessAnalyzer(MethodNode methodNode, boolean z, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(methodNode, (i & 2) != 0 ? true : z);
    }

    public final MethodNode getMethod() {
        return this.method;
    }

    public final boolean getVisitExceptionHandlers() {
        return this.visitExceptionHandlers;
    }

    private final int getIndexOf(AbstractInsnNode abstractInsnNode) {
        return this.instructions.indexOf(abstractInsnNode);
    }

    public final boolean[] analyze() {
        if (this.nInsns == 0) {
            return this.isLive;
        }
        checkAssertions();
        computeExceptionHandlersForEachInsn(this.method);
        initControlFlowAnalysis();
        traverseCfg();
        localVariableAndTryCatchBlockLabelsAreAlwaysLive();
        if (this.instructions.getLast() instanceof LabelNode) {
            boolean[] zArr = this.isLive;
            AbstractInsnNode last = this.instructions.getLast();
            Intrinsics.checkNotNullExpressionValue(last, "getLast(...)");
            zArr[getIndexOf(last)] = true;
        }
        return this.isLive;
    }

    /* JADX WARN: Code duplicated, block: B:25:0x0059  */
    private final void traverseCfg() {
        List<TryCatchBlockNode> list;
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
                visitTableSwitchInsnNode((TableSwitchInsnNode) abstractInsnNode);
            } else if (type == 12) {
                Intrinsics.checkNotNull(abstractInsnNode, "null cannot be cast to non-null type org.jetbrains.org.objectweb.asm.tree.LookupSwitchInsnNode");
                visitLookupSwitchInsnNode((LookupSwitchInsnNode) abstractInsnNode);
            } else if (type == 14 || type == 15) {
                visitOpInsn(i3);
            } else if (opcode != 191 && (opcode < 172 || opcode > 177)) {
                visitOpInsn(i3);
            }
            if (this.visitExceptionHandlers && (list = this.handlers[i3]) != null) {
                Iterator<T> it = list.iterator();
                while (it.hasNext()) {
                    LabelNode handler = ((TryCatchBlockNode) it.next()).handler;
                    Intrinsics.checkNotNullExpressionValue(handler, "handler");
                    visitControlFlowEdge(getIndexOf(handler));
                }
            }
        }
    }

    private final void localVariableAndTryCatchBlockLabelsAreAlwaysLive() {
        for (LocalVariableNode localVariableNode : this.method.localVariables) {
            boolean[] zArr = this.isLive;
            LabelNode start = localVariableNode.start;
            Intrinsics.checkNotNullExpressionValue(start, "start");
            zArr[getIndexOf(start)] = true;
            boolean[] zArr2 = this.isLive;
            LabelNode end = localVariableNode.end;
            Intrinsics.checkNotNullExpressionValue(end, "end");
            zArr2[getIndexOf(end)] = true;
        }
        for (TryCatchBlockNode tryCatchBlockNode : this.method.tryCatchBlocks) {
            boolean[] zArr3 = this.isLive;
            LabelNode start2 = tryCatchBlockNode.start;
            Intrinsics.checkNotNullExpressionValue(start2, "start");
            zArr3[getIndexOf(start2)] = true;
            boolean[] zArr4 = this.isLive;
            LabelNode end2 = tryCatchBlockNode.end;
            Intrinsics.checkNotNullExpressionValue(end2, "end");
            zArr4[getIndexOf(end2)] = true;
            boolean[] zArr5 = this.isLive;
            LabelNode handler = tryCatchBlockNode.handler;
            Intrinsics.checkNotNullExpressionValue(handler, "handler");
            zArr5[getIndexOf(handler)] = true;
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
        visitControlFlowEdge(insn + 1);
    }

    private final void visitTableSwitchInsnNode(TableSwitchInsnNode insnNode) {
        LabelNode dflt = insnNode.dflt;
        Intrinsics.checkNotNullExpressionValue(dflt, "dflt");
        visitControlFlowEdge(getIndexOf(dflt));
        for (LabelNode labelNode : insnNode.labels) {
            Intrinsics.checkNotNull(labelNode);
            visitControlFlowEdge(getIndexOf(labelNode));
        }
    }

    private final void visitLookupSwitchInsnNode(LookupSwitchInsnNode insnNode) {
        LabelNode dflt = insnNode.dflt;
        Intrinsics.checkNotNullExpressionValue(dflt, "dflt");
        visitControlFlowEdge(getIndexOf(dflt));
        for (LabelNode labelNode : insnNode.labels) {
            Intrinsics.checkNotNull(labelNode);
            visitControlFlowEdge(getIndexOf(labelNode));
        }
    }

    private final void visitJumpInsnNode(JumpInsnNode insnNode, int insn, int insnOpcode) {
        if (insnOpcode != 167 && insnOpcode != 168) {
            visitControlFlowEdge(insn + 1);
        }
        LabelNode label = insnNode.label;
        Intrinsics.checkNotNullExpressionValue(label, "label");
        visitControlFlowEdge(getIndexOf(label));
    }

    private final void initControlFlowAnalysis() {
        visitControlFlowEdge(0);
    }

    private final void computeExceptionHandlersForEachInsn(MethodNode m) {
        if (this.visitExceptionHandlers) {
            for (TryCatchBlockNode tryCatchBlockNode : m.tryCatchBlocks) {
                LabelNode start = tryCatchBlockNode.start;
                Intrinsics.checkNotNullExpressionValue(start, "start");
                LabelNode end = tryCatchBlockNode.end;
                Intrinsics.checkNotNullExpressionValue(end, "end");
                int indexOf = getIndexOf(end);
                for (int indexOf2 = getIndexOf(start); indexOf2 < indexOf; indexOf2++) {
                    AbstractInsnNode abstractInsnNode = this.instructions.get(indexOf2);
                    Intrinsics.checkNotNullExpressionValue(abstractInsnNode, "get(...)");
                    if (UtilKt.isMeaningful(abstractInsnNode)) {
                        ArrayList arrayList = this.handlers[indexOf2];
                        if (arrayList == null) {
                            arrayList = new ArrayList();
                            this.handlers[indexOf2] = arrayList;
                        }
                        Intrinsics.checkNotNull(tryCatchBlockNode);
                        arrayList.add(tryCatchBlockNode);
                    }
                }
            }
        }
    }

    private final void visitControlFlowEdge(int insn) {
        boolean[] zArr = this.isLive;
        boolean z = zArr[insn];
        zArr[insn] = true;
        if (z) {
            return;
        }
        boolean[] zArr2 = this.queued;
        if (zArr2[insn]) {
            return;
        }
        zArr2[insn] = true;
        int[] iArr = this.queue;
        int i = this.top;
        this.top = i + 1;
        iArr[i] = insn;
    }
}
