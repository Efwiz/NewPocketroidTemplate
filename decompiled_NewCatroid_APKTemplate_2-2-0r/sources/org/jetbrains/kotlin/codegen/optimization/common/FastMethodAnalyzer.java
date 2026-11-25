package org.jetbrains.kotlin.codegen.optimization.common;

import androidx.constraintlayout.core.motion.utils.TypedValues;
import androidx.exifinterface.media.ExifInterface;
import com.reandroid.dex.sections.Marker;
import io.netty.util.internal.StringUtil;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.apache.commons.compress.archivers.ArchiveStreamFactory;
import org.jetbrains.kotlin.codegen.inline.InlineCodegenUtilsKt;
import org.jetbrains.kotlin.metadata.jvm.deserialization.JvmProtoBufUtil;
import org.jetbrains.kotlin.utils.SmartList;
import org.jetbrains.org.objectweb.asm.Type;
import org.jetbrains.org.objectweb.asm.tree.AbstractInsnNode;
import org.jetbrains.org.objectweb.asm.tree.InsnList;
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

/* JADX INFO: compiled from: FastMethodAnalyzer.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000~\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\u0011\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0018\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u0015\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0002\b\u0010\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\b\u0016\u0018\u0000 >*\b\b\u0000\u0010\u0001*\u00020\u00022\u00020\u0003:\u0001>B/\b\u0007\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\f\u0010\b\u001a\b\u0012\u0004\u0012\u00028\u00000\t\u0012\b\b\u0002\u0010\n\u001a\u00020\u000b¢\u0006\u0002\u0010\fJ\u0019\u0010!\u001a\u0010\u0012\f\u0012\n\u0012\u0004\u0012\u00028\u0000\u0018\u00010\u000f0\u000e¢\u0006\u0002\u0010\"J\b\u0010#\u001a\u00020$H\u0002J\u0010\u0010%\u001a\u00020$2\u0006\u0010&\u001a\u00020\u0007H\u0002J\u0016\u0010'\u001a\n\u0012\u0004\u0012\u00028\u0000\u0018\u00010\u000f2\u0006\u0010(\u001a\u00020\u0016J\u001b\u0010)\u001a\u00020$2\f\u0010*\u001a\b\u0012\u0004\u0012\u00028\u00000\u000fH\u0000¢\u0006\u0002\b+J(\u0010,\u001a\u00020$2\u0006\u0010-\u001a\u00020\u001c2\f\u0010.\u001a\b\u0012\u0004\u0012\u00028\u00000\u000f2\b\b\u0002\u0010/\u001a\u00020\u000bH\u0002J\u001e\u00100\u001a\b\u0012\u0004\u0012\u00028\u00000\u000f2\u0006\u00101\u001a\u00020\u001c2\u0006\u00102\u001a\u00020\u001cH\u0014J.\u00103\u001a\u00020$2\u0006\u00104\u001a\u0002052\f\u0010*\u001a\b\u0012\u0004\u0012\u00028\u00000\u000f2\u0006\u0010(\u001a\u00020\u001c2\u0006\u00106\u001a\u00020\u001cH\u0002J\u001e\u00107\u001a\u00020$2\u0006\u00104\u001a\u0002082\f\u0010*\u001a\b\u0012\u0004\u0012\u00028\u00000\u000fH\u0002J\u001e\u00109\u001a\u00020$2\f\u0010*\u001a\b\u0012\u0004\u0012\u00028\u00000\u000f2\u0006\u0010(\u001a\u00020\u001cH\u0002J\u001e\u0010:\u001a\u00020$2\u0006\u00104\u001a\u00020;2\f\u0010*\u001a\b\u0012\u0004\u0012\u00028\u00000\u000fH\u0002J\u0012\u0010<\u001a\u00020\u0005*\b\u0012\u0004\u0012\u00028\u00000\u000fH\u0002J\f\u0010=\u001a\u00020\u001c*\u00020\u0016H\u0002R\u001e\u0010\r\u001a\u0010\u0012\f\u0012\n\u0012\u0004\u0012\u00028\u0000\u0018\u00010\u000f0\u000eX\u0082\u0004¢\u0006\u0004\n\u0002\u0010\u0010R\u001e\u0010\u0011\u001a\u0010\u0012\f\u0012\n\u0012\u0004\u0012\u00020\u0013\u0018\u00010\u00120\u000eX\u0082\u0004¢\u0006\u0004\n\u0002\u0010\u0014R6\u0010\u0015\u001a(\u0012\f\u0012\n \u0017*\u0004\u0018\u00010\u00160\u0016 \u0017*\u0014\u0012\u000e\b\u0001\u0012\n \u0017*\u0004\u0018\u00010\u00160\u0016\u0018\u00010\u000e0\u000eX\u0082\u0004¢\u0006\u0004\n\u0002\u0010\u0018R\u0014\u0010\b\u001a\b\u0012\u0004\u0012\u00028\u00000\tX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0019\u001a\u00020\u001aX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u001b\u001a\u00020\u001cX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u000bX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u001d\u001a\u00020\u001eX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u001f\u001a\u00020\u001aX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010 \u001a\u00020\u001cX\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006?"}, d2 = {"Lorg/jetbrains/kotlin/codegen/optimization/common/FastMethodAnalyzer;", ExifInterface.GPS_MEASUREMENT_INTERRUPTED, "Lorg/jetbrains/org/objectweb/asm/tree/analysis/Value;", "", "owner", "", "method", "Lorg/jetbrains/org/objectweb/asm/tree/MethodNode;", "interpreter", "Lorg/jetbrains/org/objectweb/asm/tree/analysis/Interpreter;", "pruneExceptionEdges", "", "(Ljava/lang/String;Lorg/jetbrains/org/objectweb/asm/tree/MethodNode;Lorg/jetbrains/org/objectweb/asm/tree/analysis/Interpreter;Z)V", "frames", "", "Lorg/jetbrains/org/objectweb/asm/tree/analysis/Frame;", "[Lorg/jetbrains/org/objectweb/asm/tree/analysis/Frame;", "handlers", "", "Lorg/jetbrains/org/objectweb/asm/tree/TryCatchBlockNode;", "[Ljava/util/List;", "insnsArray", "Lorg/jetbrains/org/objectweb/asm/tree/AbstractInsnNode;", JvmProtoBufUtil.PLATFORM_TYPE_ID, "[Lorg/jetbrains/org/objectweb/asm/tree/AbstractInsnNode;", "isMergeNode", "", "nInsns", "", "queue", "", "queued", "top", "analyze", "()[Lorg/jetbrains/org/objectweb/asm/tree/analysis/Frame;", "checkAssertions", "", "computeExceptionHandlersForEachInsn", "m", "getFrame", "insn", "initLocals", "current", "initLocals$backend", "mergeControlFlowEdge", "dest", TypedValues.AttributesType.S_FRAME, "canReuse", "newFrame", "nLocals", "nStack", "visitJumpInsnNode", "insnNode", "Lorg/jetbrains/org/objectweb/asm/tree/JumpInsnNode;", "insnOpcode", "visitLookupSwitchInsnNode", "Lorg/jetbrains/org/objectweb/asm/tree/LookupSwitchInsnNode;", "visitOpInsn", "visitTableSwitchInsnNode", "Lorg/jetbrains/org/objectweb/asm/tree/TableSwitchInsnNode;", ArchiveStreamFactory.DUMP, "indexOf", "Companion", Marker.BACKEND}, k = 1, mv = {1, 9, 0}, xi = 48)
public class FastMethodAnalyzer<V extends Value> {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private final Frame<V>[] frames;
    private final List<TryCatchBlockNode>[] handlers;
    private final AbstractInsnNode[] insnsArray;
    private final Interpreter<V> interpreter;
    private final boolean[] isMergeNode;
    private final MethodNode method;
    private final int nInsns;
    private final String owner;
    private final boolean pruneExceptionEdges;
    private final int[] queue;
    private final boolean[] queued;
    private int top;

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public FastMethodAnalyzer(String owner, MethodNode method, Interpreter<V> interpreter) {
        this(owner, method, interpreter, false, 8, null);
        Intrinsics.checkNotNullParameter(owner, "owner");
        Intrinsics.checkNotNullParameter(method, "method");
        Intrinsics.checkNotNullParameter(interpreter, "interpreter");
    }

    public FastMethodAnalyzer(String owner, MethodNode method, Interpreter<V> interpreter, boolean z) {
        Intrinsics.checkNotNullParameter(owner, "owner");
        Intrinsics.checkNotNullParameter(method, "method");
        Intrinsics.checkNotNullParameter(interpreter, "interpreter");
        this.owner = owner;
        this.method = method;
        this.interpreter = interpreter;
        this.pruneExceptionEdges = z;
        this.insnsArray = method.instructions.toArray();
        int size = method.instructions.size();
        this.nInsns = size;
        this.isMergeNode = INSTANCE.findMergeNodes(method);
        this.frames = new Frame[size];
        this.handlers = new List[size];
        this.queued = new boolean[size];
        this.queue = new int[size];
    }

    public /* synthetic */ FastMethodAnalyzer(String str, MethodNode methodNode, Interpreter interpreter, boolean z, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(str, methodNode, interpreter, (i & 8) != 0 ? false : z);
    }

    protected Frame<V> newFrame(int nLocals, int nStack) {
        return new Frame<>(nLocals, nStack);
    }

    public final Frame<V>[] analyze() throws AnalyzerException {
        boolean z;
        AbstractInsnNode abstractInsnNode;
        char c;
        AbstractInsnNode abstractInsnNode2;
        if (this.nInsns == 0) {
            return this.frames;
        }
        checkAssertions();
        computeExceptionHandlersForEachInsn(this.method);
        boolean[] zArr = new boolean[this.nInsns];
        Iterator<TryCatchBlockNode> it = this.method.tryCatchBlocks.iterator();
        while (true) {
            z = true;
            if (!it.hasNext()) {
                break;
            }
            LabelNode start = it.next().start;
            Intrinsics.checkNotNullExpressionValue(start, "start");
            zArr[indexOf(start) + 1] = true;
        }
        Frame<V> frameNewFrame = newFrame(this.method.maxLocals, this.method.maxStack);
        Frame<V> frameNewFrame2 = newFrame(this.method.maxLocals, this.method.maxStack);
        initLocals$backend(frameNewFrame);
        mergeControlFlowEdge$default(this, 0, frameNewFrame, false, 4, null);
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
                AbstractInsnNode abstractInsnNode3 = this.method.instructions.get(i3);
                try {
                    try {
                        int opcode = abstractInsnNode3.getOpcode();
                        try {
                            int type = abstractInsnNode3.getType();
                            if (type == 8 || type == 15 || type == 14 || opcode == 0) {
                                mergeControlFlowEdge(i3 + 1, frame, z);
                            } else {
                                frameNewFrame.init(frame).execute(abstractInsnNode3, this.interpreter);
                                if (type == 7) {
                                    Intrinsics.checkNotNull(abstractInsnNode3, "null cannot be cast to non-null type org.jetbrains.org.objectweb.asm.tree.JumpInsnNode");
                                    visitJumpInsnNode((JumpInsnNode) abstractInsnNode3, frameNewFrame, i3, opcode);
                                } else if (type == 12) {
                                    Intrinsics.checkNotNull(abstractInsnNode3, "null cannot be cast to non-null type org.jetbrains.org.objectweb.asm.tree.LookupSwitchInsnNode");
                                    visitLookupSwitchInsnNode((LookupSwitchInsnNode) abstractInsnNode3, frameNewFrame);
                                } else if (type == 11) {
                                    Intrinsics.checkNotNull(abstractInsnNode3, "null cannot be cast to non-null type org.jetbrains.org.objectweb.asm.tree.TableSwitchInsnNode");
                                    visitTableSwitchInsnNode((TableSwitchInsnNode) abstractInsnNode3, frameNewFrame);
                                } else if (opcode != 191 && (opcode < 172 || opcode > 177)) {
                                    visitOpInsn(frameNewFrame, i3);
                                }
                            }
                            if (!this.pruneExceptionEdges || ((54 <= opcode && opcode < 59) || opcode == 132 || zArr[i3])) {
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
                                        frameNewFrame2.init(frame);
                                        frameNewFrame2.clearStack();
                                        frameNewFrame2.push(this.interpreter.newExceptionValue(tryCatchBlockNode, frameNewFrame2, objectType));
                                        c = StringUtil.SPACE;
                                        abstractInsnNode = abstractInsnNode3;
                                        Frame<V> frame2 = frame;
                                        try {
                                            mergeControlFlowEdge$default(this, iIndexOf, frameNewFrame2, false, 4, null);
                                            frame = frame2;
                                            abstractInsnNode3 = abstractInsnNode;
                                            z = true;
                                        } catch (AnalyzerException e) {
                                            e = e;
                                            abstractInsnNode2 = abstractInsnNode;
                                            AbstractInsnNode abstractInsnNode4 = e.node;
                                            StringBuilder sbAppend = new StringBuilder("Error at instruction #").append(i3).append(c);
                                            InsnList instructions = this.method.instructions;
                                            Intrinsics.checkNotNullExpressionValue(instructions, "instructions");
                                            throw new AnalyzerException(abstractInsnNode4, sbAppend.append(InlineCodegenUtilsKt.insnText(abstractInsnNode2, instructions)).append(": ").append(e.getMessage()).append("\ncurrent: ").append(dump(frameNewFrame)).toString(), e);
                                        } catch (Exception e2) {
                                            e = e2;
                                            StringBuilder sbAppend2 = new StringBuilder("Error at instruction #").append(i3).append(c);
                                            InsnList instructions2 = this.method.instructions;
                                            Intrinsics.checkNotNullExpressionValue(instructions2, "instructions");
                                            AbstractInsnNode abstractInsnNode5 = abstractInsnNode;
                                            throw new AnalyzerException(abstractInsnNode5, sbAppend2.append(InlineCodegenUtilsKt.insnText(abstractInsnNode5, instructions2)).append(": ").append(e.getMessage()).append("\ncurrent: ").append(dump(frameNewFrame)).toString(), e);
                                        }
                                    }
                                } else {
                                    continue;
                                }
                            }
                        } catch (AnalyzerException e3) {
                            e = e3;
                            abstractInsnNode = abstractInsnNode3;
                            c = StringUtil.SPACE;
                        }
                    } catch (AnalyzerException e4) {
                        e = e4;
                        abstractInsnNode2 = abstractInsnNode3;
                        c = StringUtil.SPACE;
                    }
                } catch (Exception e5) {
                    e = e5;
                    abstractInsnNode = abstractInsnNode3;
                    c = StringUtil.SPACE;
                }
            } else {
                return this.frames;
            }
        }
    }

    public final void initLocals$backend(Frame<V> current) {
        Intrinsics.checkNotNullParameter(current, "current");
        current.setReturn(this.interpreter.newReturnTypeValue(Type.getReturnType(this.method.desc)));
        Type[] argumentTypes = Type.getArgumentTypes(this.method.desc);
        int i = 1;
        boolean z = (this.method.access & 8) == 0;
        if (z) {
            current.setLocal(0, this.interpreter.newParameterValue(true, 0, Type.getObjectType(this.owner)));
        } else {
            i = 0;
        }
        Intrinsics.checkNotNull(argumentTypes);
        for (Type type : argumentTypes) {
            current.setLocal(i, this.interpreter.newParameterValue(z, i, type));
            int i2 = i + 1;
            if (type.getSize() == 2) {
                current.setLocal(i2, this.interpreter.newEmptyValue(i2));
                i += 2;
            } else {
                i = i2;
            }
        }
        while (i < this.method.maxLocals) {
            current.setLocal(i, this.interpreter.newEmptyValue(i));
            i++;
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
        AbstractInsnNode[] insnsArray = this.insnsArray;
        Intrinsics.checkNotNullExpressionValue(insnsArray, "insnsArray");
        for (AbstractInsnNode abstractInsnNode : insnsArray) {
            if (abstractInsnNode.getOpcode() == 168 || abstractInsnNode.getOpcode() == 169) {
                throw new AssertionError("Subroutines are deprecated since Java 6");
            }
        }
    }

    private final void visitOpInsn(Frame<V> current, int insn) throws AnalyzerException {
        mergeControlFlowEdge$default(this, insn + 1, current, false, 4, null);
    }

    private final void visitTableSwitchInsnNode(TableSwitchInsnNode insnNode, Frame<V> current) throws AnalyzerException {
        LabelNode dflt = insnNode.dflt;
        Intrinsics.checkNotNullExpressionValue(dflt, "dflt");
        mergeControlFlowEdge$default(this, indexOf(dflt), current, false, 4, null);
        List<LabelNode> labels = insnNode.labels;
        Intrinsics.checkNotNullExpressionValue(labels, "labels");
        for (LabelNode labelNode : CollectionsKt.asReversedMutable(labels)) {
            Intrinsics.checkNotNull(labelNode);
            mergeControlFlowEdge$default(this, indexOf(labelNode), current, false, 4, null);
        }
    }

    private final void visitLookupSwitchInsnNode(LookupSwitchInsnNode insnNode, Frame<V> current) throws AnalyzerException {
        LabelNode dflt = insnNode.dflt;
        Intrinsics.checkNotNullExpressionValue(dflt, "dflt");
        mergeControlFlowEdge$default(this, indexOf(dflt), current, false, 4, null);
        for (LabelNode labelNode : insnNode.labels) {
            Intrinsics.checkNotNull(labelNode);
            mergeControlFlowEdge$default(this, indexOf(labelNode), current, false, 4, null);
        }
    }

    private final void visitJumpInsnNode(JumpInsnNode insnNode, Frame<V> current, int insn, int insnOpcode) throws AnalyzerException {
        LabelNode label = insnNode.label;
        Intrinsics.checkNotNullExpressionValue(label, "label");
        mergeControlFlowEdge$default(this, indexOf(label), current, false, 4, null);
        if (insnOpcode != 167) {
            mergeControlFlowEdge$default(this, insn + 1, current, false, 4, null);
        }
    }

    private final void computeExceptionHandlersForEachInsn(MethodNode m) {
        for (TryCatchBlockNode tryCatchBlockNode : m.tryCatchBlocks) {
            LabelNode start = tryCatchBlockNode.start;
            Intrinsics.checkNotNullExpressionValue(start, "start");
            LabelNode end = tryCatchBlockNode.end;
            Intrinsics.checkNotNullExpressionValue(end, "end");
            int iIndexOf = indexOf(end);
            for (int iIndexOf2 = indexOf(start); iIndexOf2 < iIndexOf; iIndexOf2++) {
                AbstractInsnNode abstractInsnNode = this.insnsArray[iIndexOf2];
                Intrinsics.checkNotNullExpressionValue(abstractInsnNode, "get(...)");
                if (UtilKt.isMeaningful(abstractInsnNode)) {
                    SmartList smartList = this.handlers[iIndexOf2];
                    if (smartList == null) {
                        smartList = new SmartList();
                        this.handlers[iIndexOf2] = smartList;
                    }
                    Intrinsics.checkNotNull(tryCatchBlockNode);
                    smartList.add(tryCatchBlockNode);
                }
            }
        }
    }

    static /* synthetic */ void mergeControlFlowEdge$default(FastMethodAnalyzer fastMethodAnalyzer, int i, Frame frame, boolean z, int i2, Object obj) throws AnalyzerException {
        if (obj != null) {
            throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: mergeControlFlowEdge");
        }
        if ((i2 & 4) != 0) {
            z = false;
        }
        fastMethodAnalyzer.mergeControlFlowEdge(i, frame, z);
    }

    /* JADX WARN: Code duplicated, block: B:15:0x0039  */
    /* JADX WARN: Code duplicated, block: B:17:0x003f  */
    /* JADX WARN: Code duplicated, block: B:24:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:25:? A[RETURN, SYNTHETIC] */
    private final void mergeControlFlowEdge(int dest, Frame<V> frame, boolean canReuse) throws AnalyzerException {
        boolean zMerge;
        boolean[] zArr;
        Frame<V>[] frameArr = this.frames;
        Frame<V> frame2 = frameArr[dest];
        if (canReuse && !this.isMergeNode[dest]) {
            frameArr[dest] = frame;
        } else if (frame2 == null) {
            Frame<V> frameNewFrame = newFrame(frame.getLocals(), frame.getMaxStackSize());
            frameNewFrame.init(frame);
            Unit unit = Unit.INSTANCE;
            frameArr[dest] = frameNewFrame;
        } else {
            if (!this.isMergeNode[dest]) {
                frame2.init(frame);
            } else {
                try {
                    zMerge = frame2.merge(frame, this.interpreter);
                } catch (AnalyzerException e) {
                    throw new AnalyzerException(null, e.getMessage() + "\nframe: " + dump(frame) + "\noldFrame: " + dump(frame2));
                }
            }
            if (zMerge) {
                zArr = this.queued;
                if (zArr[dest]) {
                }
                zArr[dest] = true;
                int[] iArr = this.queue;
                int i = this.top;
                this.top = i + 1;
                iArr[i] = dest;
            }
        }
        zMerge = true;
        if (zMerge) {
            zArr = this.queued;
            if (zArr[dest]) {
                zArr[dest] = true;
                int[] iArr2 = this.queue;
                int i2 = this.top;
                this.top = i2 + 1;
                iArr2[i2] = dest;
            }
        }
    }

    private final String dump(Frame<V> frame) {
        StringBuilder sb = new StringBuilder("{\n  locals: [\n");
        int i = this.method.maxLocals;
        for (int i2 = 0; i2 < i; i2++) {
            sb.append("    #" + i2 + ": " + frame.getLocal(i2) + '\n');
        }
        sb.append("  ]\n  stack: size=");
        int stackSize = frame.getStackSize();
        sb.append(stackSize);
        if (stackSize == 0) {
            sb.append(" []\n");
        } else {
            sb.append(" [\n");
            for (int i3 = 0; i3 < stackSize; i3++) {
                sb.append("    #" + i3 + ": " + frame.getStack(i3) + '\n');
            }
            sb.append("  ]\n");
        }
        sb.append("}\n");
        String string = sb.toString();
        Intrinsics.checkNotNullExpressionValue(string, "toString(...)");
        return string;
    }

    /* JADX INFO: compiled from: FastMethodAnalyzer.kt */
    @Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u0018\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u000e\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006¨\u0006\u0007"}, d2 = {"Lorg/jetbrains/kotlin/codegen/optimization/common/FastMethodAnalyzer$Companion;", "", "()V", "findMergeNodes", "", "method", "Lorg/jetbrains/org/objectweb/asm/tree/MethodNode;", Marker.BACKEND}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        /* JADX WARN: Type inference failed for: r1v1, types: [java.util.ListIterator] */
        public final boolean[] findMergeNodes(MethodNode method) {
            Intrinsics.checkNotNullParameter(method, "method");
            boolean[] zArr = new boolean[method.instructions.size()];
            ?? it = method.instructions.iterator();
            while (it.hasNext()) {
                AbstractInsnNode abstractInsnNode = (AbstractInsnNode) it.next();
                int type = abstractInsnNode.getType();
                if (type == 7) {
                    Intrinsics.checkNotNull(abstractInsnNode, "null cannot be cast to non-null type org.jetbrains.org.objectweb.asm.tree.JumpInsnNode");
                    zArr[method.instructions.indexOf(((JumpInsnNode) abstractInsnNode).label)] = true;
                } else if (type == 11) {
                    Intrinsics.checkNotNull(abstractInsnNode, "null cannot be cast to non-null type org.jetbrains.org.objectweb.asm.tree.TableSwitchInsnNode");
                    TableSwitchInsnNode tableSwitchInsnNode = (TableSwitchInsnNode) abstractInsnNode;
                    zArr[method.instructions.indexOf(tableSwitchInsnNode.dflt)] = true;
                    Iterator<LabelNode> it2 = tableSwitchInsnNode.labels.iterator();
                    while (it2.hasNext()) {
                        zArr[method.instructions.indexOf(it2.next())] = true;
                    }
                } else if (type == 12) {
                    Intrinsics.checkNotNull(abstractInsnNode, "null cannot be cast to non-null type org.jetbrains.org.objectweb.asm.tree.LookupSwitchInsnNode");
                    LookupSwitchInsnNode lookupSwitchInsnNode = (LookupSwitchInsnNode) abstractInsnNode;
                    zArr[method.instructions.indexOf(lookupSwitchInsnNode.dflt)] = true;
                    Iterator<LabelNode> it3 = lookupSwitchInsnNode.labels.iterator();
                    while (it3.hasNext()) {
                        zArr[method.instructions.indexOf(it3.next())] = true;
                    }
                }
            }
            Iterator<TryCatchBlockNode> it4 = method.tryCatchBlocks.iterator();
            while (it4.hasNext()) {
                zArr[method.instructions.indexOf(it4.next().handler)] = true;
            }
            return zArr;
        }
    }
}
