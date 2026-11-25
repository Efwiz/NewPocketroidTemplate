package org.jetbrains.kotlin.codegen.optimization.fixStack;

import androidx.constraintlayout.core.motion.utils.TypedValues;
import androidx.exifinterface.media.ExifInterface;
import com.reandroid.dex.sections.Marker;
import io.netty.util.internal.StringUtil;
import java.util.ArrayList;
import java.util.List;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.codegen.inline.InlineCodegenUtilsKt;
import org.jetbrains.org.objectweb.asm.Type;
import org.jetbrains.org.objectweb.asm.tree.AbstractInsnNode;
import org.jetbrains.org.objectweb.asm.tree.JumpInsnNode;
import org.jetbrains.org.objectweb.asm.tree.LabelNode;
import org.jetbrains.org.objectweb.asm.tree.LookupSwitchInsnNode;
import org.jetbrains.org.objectweb.asm.tree.MethodNode;
import org.jetbrains.org.objectweb.asm.tree.TableSwitchInsnNode;
import org.jetbrains.org.objectweb.asm.tree.TryCatchBlockNode;
import org.jetbrains.org.objectweb.asm.tree.analysis.AnalyzerException;
import org.jetbrains.org.objectweb.asm.tree.analysis.Frame;
import org.jetbrains.org.objectweb.asm.tree.analysis.Interpreter;
import org.jetbrains.org.objectweb.asm.tree.analysis.Value;

/* JADX INFO: compiled from: FastStackAnalyzer.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000\u0080\u0001\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0011\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u0015\n\u0000\n\u0002\u0010\u0018\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0002\b\u000f\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0010\u0018\u0000*\b\b\u0000\u0010\u0001*\u00020\u00022\u00020\u0003B#\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\f\u0010\b\u001a\b\u0012\u0004\u0012\u00028\u00000\t¢\u0006\u0002\u0010\nJ\u0019\u0010#\u001a\u0010\u0012\f\u0012\n\u0012\u0004\u0012\u00028\u0000\u0018\u00010\r0\f¢\u0006\u0002\u0010$J\b\u0010%\u001a\u00020&H\u0002J\b\u0010'\u001a\u00020&H\u0002J\u0016\u0010(\u001a\n\u0012\u0004\u0012\u00028\u0000\u0018\u00010\r2\u0006\u0010)\u001a\u00020\u0014J&\u0010*\u001a\u00020&2\f\u0010+\u001a\b\u0012\u0004\u0012\u00028\u00000\r2\u0006\u0010,\u001a\u00020\u00072\u0006\u0010\u0004\u001a\u00020\u0005H\u0002J\u001e\u0010-\u001a\u00020&2\u0006\u0010.\u001a\u00020\u001d2\f\u0010/\u001a\b\u0012\u0004\u0012\u00028\u00000\rH\u0002J\u001e\u00100\u001a\b\u0012\u0004\u0012\u00028\u00000\r2\u0006\u00101\u001a\u00020\u001d2\u0006\u00102\u001a\u00020\u001dH\u0014J&\u00103\u001a\u00020&2\f\u0010+\u001a\b\u0012\u0004\u0012\u00028\u00000\r2\u0006\u0010)\u001a\u00020\u001d2\u0006\u00104\u001a\u00020\u001dH\u0002J\u0018\u00105\u001a\u0002062\u0006\u0010)\u001a\u00020\u001d2\u0006\u00107\u001a\u00020\u001dH\u0014J\u0018\u00108\u001a\u0002062\u0006\u0010)\u001a\u00020\u001d2\u0006\u00107\u001a\u00020\u001dH\u0014J.\u00109\u001a\u00020&2\u0006\u0010:\u001a\u00020;2\f\u0010+\u001a\b\u0012\u0004\u0012\u00028\u00000\r2\u0006\u0010)\u001a\u00020\u001d2\u0006\u0010<\u001a\u00020\u001dH\u0002J&\u0010=\u001a\u00020&2\u0006\u0010:\u001a\u00020>2\f\u0010+\u001a\b\u0012\u0004\u0012\u00028\u00000\r2\u0006\u0010)\u001a\u00020\u001dH\u0002J\u001e\u0010?\u001a\u00020&2\f\u0010@\u001a\b\u0012\u0004\u0012\u00028\u00000\r2\u0006\u0010)\u001a\u00020\u001dH\u0002J\u001e\u0010A\u001a\u00020&2\f\u0010+\u001a\b\u0012\u0004\u0012\u00028\u00000\r2\u0006\u0010)\u001a\u00020\u001dH\u0002J&\u0010B\u001a\u00020&2\u0006\u0010:\u001a\u00020C2\f\u0010+\u001a\b\u0012\u0004\u0012\u00028\u00000\r2\u0006\u0010)\u001a\u00020\u001dH\u0002J\f\u0010D\u001a\u00020\u001d*\u00020\u0014H\u0002R\u001e\u0010\u000b\u001a\u0010\u0012\f\u0012\n\u0012\u0004\u0012\u00028\u0000\u0018\u00010\r0\fX\u0082\u0004¢\u0006\u0004\n\u0002\u0010\u000eR\u001e\u0010\u000f\u001a\u0010\u0012\f\u0012\n\u0012\u0004\u0012\u00020\u0011\u0018\u00010\u00100\fX\u0082\u0004¢\u0006\u0004\n\u0002\u0010\u0012R\u001c\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\u00140\fX\u0084\u0004¢\u0006\n\n\u0002\u0010\u0017\u001a\u0004\b\u0015\u0010\u0016R\u001a\u0010\b\u001a\b\u0012\u0004\u0012\u00028\u00000\tX\u0084\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0018\u0010\u0019R\u0011\u0010\u0006\u001a\u00020\u0007¢\u0006\b\n\u0000\u001a\u0004\b\u001a\u0010\u001bR\u000e\u0010\u001c\u001a\u00020\u001dX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u001e\u001a\u00020\u001fX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010 \u001a\u00020!X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\"\u001a\u00020\u001dX\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006E"}, d2 = {"Lorg/jetbrains/kotlin/codegen/optimization/fixStack/FastStackAnalyzer;", ExifInterface.GPS_MEASUREMENT_INTERRUPTED, "Lorg/jetbrains/org/objectweb/asm/tree/analysis/Value;", "", "owner", "", "method", "Lorg/jetbrains/org/objectweb/asm/tree/MethodNode;", "interpreter", "Lorg/jetbrains/org/objectweb/asm/tree/analysis/Interpreter;", "(Ljava/lang/String;Lorg/jetbrains/org/objectweb/asm/tree/MethodNode;Lorg/jetbrains/org/objectweb/asm/tree/analysis/Interpreter;)V", "frames", "", "Lorg/jetbrains/org/objectweb/asm/tree/analysis/Frame;", "[Lorg/jetbrains/org/objectweb/asm/tree/analysis/Frame;", "handlers", "", "Lorg/jetbrains/org/objectweb/asm/tree/TryCatchBlockNode;", "[Ljava/util/List;", "insnsArray", "Lorg/jetbrains/org/objectweb/asm/tree/AbstractInsnNode;", "getInsnsArray", "()[Lorg/jetbrains/org/objectweb/asm/tree/AbstractInsnNode;", "[Lorg/jetbrains/org/objectweb/asm/tree/AbstractInsnNode;", "getInterpreter", "()Lorg/jetbrains/org/objectweb/asm/tree/analysis/Interpreter;", "getMethod", "()Lorg/jetbrains/org/objectweb/asm/tree/MethodNode;", "nInsns", "", "queue", "", "queued", "", "top", "analyze", "()[Lorg/jetbrains/org/objectweb/asm/tree/analysis/Frame;", "checkAssertions", "", "computeExceptionEdges", "getFrame", "insn", "initControlFlowAnalysis", "current", "m", "mergeControlFlowEdge", "dest", TypedValues.AttributesType.S_FRAME, "newFrame", "nLocals", "nStack", "processControlFlowEdge", "jump", "visitControlFlowEdge", "", "successor", "visitControlFlowExceptionEdge", "visitJumpInsnNode", "insnNode", "Lorg/jetbrains/org/objectweb/asm/tree/JumpInsnNode;", "insnOpcode", "visitLookupSwitchInsnNode", "Lorg/jetbrains/org/objectweb/asm/tree/LookupSwitchInsnNode;", "visitNopInsn", "f", "visitOpInsn", "visitTableSwitchInsnNode", "Lorg/jetbrains/org/objectweb/asm/tree/TableSwitchInsnNode;", "indexOf", Marker.BACKEND}, k = 1, mv = {1, 9, 0}, xi = 48)
public class FastStackAnalyzer<V extends Value> {
    private final Frame<V>[] frames;
    private final List<TryCatchBlockNode>[] handlers;
    private final AbstractInsnNode[] insnsArray;
    private final Interpreter<V> interpreter;
    private final MethodNode method;
    private final int nInsns;
    private final String owner;
    private final int[] queue;
    private final boolean[] queued;
    private int top;

    protected boolean visitControlFlowEdge(int insn, int successor) {
        return true;
    }

    protected boolean visitControlFlowExceptionEdge(int insn, int successor) {
        return true;
    }

    public FastStackAnalyzer(String owner, MethodNode method, Interpreter<V> interpreter) {
        Intrinsics.checkNotNullParameter(owner, "owner");
        Intrinsics.checkNotNullParameter(method, "method");
        Intrinsics.checkNotNullParameter(interpreter, "interpreter");
        this.owner = owner;
        this.method = method;
        this.interpreter = interpreter;
        AbstractInsnNode[] array = method.instructions.toArray();
        Intrinsics.checkNotNullExpressionValue(array, "toArray(...)");
        this.insnsArray = array;
        int length = array.length;
        this.nInsns = length;
        this.frames = new Frame[length];
        this.handlers = new List[length];
        this.queued = new boolean[length];
        this.queue = new int[length];
    }

    public final MethodNode getMethod() {
        return this.method;
    }

    protected final Interpreter<V> getInterpreter() {
        return this.interpreter;
    }

    protected final AbstractInsnNode[] getInsnsArray() {
        return this.insnsArray;
    }

    protected Frame<V> newFrame(int nLocals, int nStack) {
        return new Frame<>(nLocals, nStack);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final Frame<V>[] analyze() throws AnalyzerException {
        if (this.nInsns == 0) {
            return this.frames;
        }
        checkAssertions();
        computeExceptionEdges();
        Frame<V> frameNewFrame = newFrame(this.method.maxLocals, this.method.maxStack);
        Frame<V> frameNewFrame2 = newFrame(this.method.maxLocals, this.method.maxStack);
        initControlFlowAnalysis(frameNewFrame, this.method, this.owner);
        while (true) {
            int i = this.top;
            if (i > 0) {
                int[] iArr = this.queue;
                int i2 = i - 1;
                this.top = i2;
                int i3 = iArr[i2];
                Frame<V> frame = this.frames[i3];
                Intrinsics.checkNotNull(frame);
                this.queued[i3] = false;
                AbstractInsnNode abstractInsnNode = this.method.instructions.get(i3);
                int opcode = abstractInsnNode.getOpcode();
                int type = abstractInsnNode.getType();
                if (type == 8 || type == 14 || type == 15) {
                    visitNopInsn(frame, i3);
                } else {
                    try {
                        frameNewFrame.init(frame);
                        if (opcode != 177) {
                            frameNewFrame.execute(abstractInsnNode, this.interpreter);
                        }
                        if (type == 7) {
                            Intrinsics.checkNotNull(abstractInsnNode, "null cannot be cast to non-null type org.jetbrains.org.objectweb.asm.tree.JumpInsnNode");
                            visitJumpInsnNode((JumpInsnNode) abstractInsnNode, frameNewFrame, i3, opcode);
                        } else if (type == 12) {
                            Intrinsics.checkNotNull(abstractInsnNode, "null cannot be cast to non-null type org.jetbrains.org.objectweb.asm.tree.LookupSwitchInsnNode");
                            visitLookupSwitchInsnNode((LookupSwitchInsnNode) abstractInsnNode, frameNewFrame, i3);
                        } else if (type == 11) {
                            Intrinsics.checkNotNull(abstractInsnNode, "null cannot be cast to non-null type org.jetbrains.org.objectweb.asm.tree.TableSwitchInsnNode");
                            visitTableSwitchInsnNode((TableSwitchInsnNode) abstractInsnNode, frameNewFrame, i3);
                        } else if (opcode != 191 && (opcode < 172 || opcode > 177)) {
                            visitOpInsn(frameNewFrame, i3);
                        }
                    } catch (AnalyzerException e) {
                        throw new AnalyzerException(e.node, "Error at instruction #" + i3 + StringUtil.SPACE + InlineCodegenUtilsKt.getInsnText(abstractInsnNode) + ": " + e.getMessage(), e);
                    } catch (Exception e2) {
                        throw new AnalyzerException(abstractInsnNode, "Error at instruction #" + i3 + StringUtil.SPACE + InlineCodegenUtilsKt.getInsnText(abstractInsnNode) + ": " + e2.getMessage(), e2);
                    }
                }
                List<TryCatchBlockNode> list = this.handlers[i3];
                if (list != null) {
                    for (TryCatchBlockNode tryCatchBlockNode : list) {
                        String str = tryCatchBlockNode.type;
                        if (str == null) {
                            str = "java/lang/Throwable";
                        } else {
                            Intrinsics.checkNotNull(str);
                        }
                        Type objectType = Type.getObjectType(str);
                        LabelNode handler = tryCatchBlockNode.handler;
                        Intrinsics.checkNotNullExpressionValue(handler, "handler");
                        int iIndexOf = indexOf(handler);
                        LabelNode handler2 = tryCatchBlockNode.handler;
                        Intrinsics.checkNotNullExpressionValue(handler2, "handler");
                        if (visitControlFlowExceptionEdge(i3, indexOf(handler2))) {
                            frameNewFrame2.init(frame);
                            frameNewFrame2.clearStack();
                            frameNewFrame2.push(this.interpreter.newValue(objectType));
                            mergeControlFlowEdge(iIndexOf, frameNewFrame2);
                        }
                    }
                }
            } else {
                return this.frames;
            }
        }
    }

    private final int indexOf(AbstractInsnNode abstractInsnNode) {
        return this.method.instructions.indexOf(abstractInsnNode);
    }

    public final Frame<V> getFrame(AbstractInsnNode insn) {
        Intrinsics.checkNotNullParameter(insn, "insn");
        return this.frames[indexOf(insn)];
    }

    private final void checkAssertions() {
        for (AbstractInsnNode abstractInsnNode : this.insnsArray) {
            if (abstractInsnNode.getOpcode() == 168 || abstractInsnNode.getOpcode() == 169) {
                throw new AssertionError("Subroutines are deprecated since Java 6");
            }
        }
    }

    private final void visitOpInsn(Frame<V> current, int insn) {
        processControlFlowEdge(current, insn, insn + 1);
    }

    private final void visitTableSwitchInsnNode(TableSwitchInsnNode insnNode, Frame<V> current, int insn) {
        LabelNode dflt = insnNode.dflt;
        Intrinsics.checkNotNullExpressionValue(dflt, "dflt");
        processControlFlowEdge(current, insn, indexOf(dflt));
        List<LabelNode> labels = insnNode.labels;
        Intrinsics.checkNotNullExpressionValue(labels, "labels");
        for (LabelNode labelNode : CollectionsKt.reversed(labels)) {
            Intrinsics.checkNotNull(labelNode);
            processControlFlowEdge(current, insn, indexOf(labelNode));
        }
    }

    private final void visitLookupSwitchInsnNode(LookupSwitchInsnNode insnNode, Frame<V> current, int insn) {
        LabelNode dflt = insnNode.dflt;
        Intrinsics.checkNotNullExpressionValue(dflt, "dflt");
        processControlFlowEdge(current, insn, indexOf(dflt));
        for (LabelNode labelNode : insnNode.labels) {
            Intrinsics.checkNotNull(labelNode);
            processControlFlowEdge(current, insn, indexOf(labelNode));
        }
    }

    private final void visitJumpInsnNode(JumpInsnNode insnNode, Frame<V> current, int insn, int insnOpcode) {
        if (insnOpcode != 167) {
            processControlFlowEdge(current, insn, insn + 1);
        }
        LabelNode label = insnNode.label;
        Intrinsics.checkNotNullExpressionValue(label, "label");
        processControlFlowEdge(current, insn, indexOf(label));
    }

    private final void visitNopInsn(Frame<V> f, int insn) {
        processControlFlowEdge(f, insn, insn + 1);
    }

    private final void processControlFlowEdge(Frame<V> current, int insn, int jump) {
        if (visitControlFlowEdge(insn, jump)) {
            mergeControlFlowEdge(jump, current);
        }
    }

    private final void initControlFlowAnalysis(Frame<V> current, MethodNode m, String owner) {
        int i;
        current.setReturn(this.interpreter.newValue(Type.getReturnType(m.desc)));
        Type[] argumentTypes = Type.getArgumentTypes(m.desc);
        if ((m.access & 8) == 0) {
            current.setLocal(0, this.interpreter.newValue(Type.getObjectType(owner)));
            i = 1;
        } else {
            i = 0;
        }
        Intrinsics.checkNotNull(argumentTypes);
        for (Type type : argumentTypes) {
            int i2 = i + 1;
            current.setLocal(i, this.interpreter.newValue(type));
            if (type.getSize() == 2) {
                i += 2;
                current.setLocal(i2, this.interpreter.newValue(null));
            } else {
                i = i2;
            }
        }
        while (i < m.maxLocals) {
            current.setLocal(i, this.interpreter.newValue(null));
            i++;
        }
        mergeControlFlowEdge(0, current);
    }

    private final void computeExceptionEdges() {
        for (TryCatchBlockNode tryCatchBlockNode : this.method.tryCatchBlocks) {
            LabelNode start = tryCatchBlockNode.start;
            Intrinsics.checkNotNullExpressionValue(start, "start");
            int iIndexOf = indexOf(start);
            ArrayList arrayList = this.handlers[iIndexOf];
            if (arrayList == null) {
                arrayList = new ArrayList();
                this.handlers[iIndexOf] = arrayList;
            }
            Intrinsics.checkNotNull(tryCatchBlockNode);
            arrayList.add(tryCatchBlockNode);
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    private final void mergeControlFlowEdge(int dest, Frame<V> frame) {
        Frame<V>[] frameArr = this.frames;
        if (frameArr[dest] == null) {
            Frame<V> frameNewFrame = newFrame(frame.getLocals(), frame.getMaxStackSize());
            frameNewFrame.init(frame);
            Unit unit = Unit.INSTANCE;
            frameArr[dest] = frameNewFrame;
            boolean[] zArr = this.queued;
            if (zArr[dest]) {
                return;
            }
            zArr[dest] = true;
            int[] iArr = this.queue;
            int i = this.top;
            this.top = i + 1;
            iArr[i] = dest;
        }
    }
}
