package org.jetbrains.kotlin.codegen.optimization.temporaryVals;

import androidx.constraintlayout.core.motion.utils.TypedValues;
import androidx.exifinterface.media.ExifInterface;
import com.reandroid.dex.sections.Marker;
import io.netty.util.internal.StringUtil;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.codegen.inline.InlineCodegenUtilsKt;
import org.jetbrains.kotlin.codegen.optimization.common.UtilKt;
import org.jetbrains.kotlin.codegen.optimization.temporaryVals.StoreLoadValue;
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

/* JADX INFO: compiled from: FastStoreLoadAnalyzer.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000v\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0011\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0018\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u0015\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0002\b\r\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u0000*\b\b\u0000\u0010\u0001*\u00020\u00022\u00020\u0003B#\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\f\u0010\b\u001a\b\u0012\u0004\u0012\u00028\u00000\t¢\u0006\u0002\u0010\nJ\u0019\u0010\u001f\u001a\u0010\u0012\f\u0012\n\u0012\u0004\u0012\u00028\u0000\u0018\u00010\r0\f¢\u0006\u0002\u0010 J\b\u0010!\u001a\u00020\"H\u0002J\u0010\u0010#\u001a\u00020\"2\u0006\u0010$\u001a\u00020\u0007H\u0002J\u001b\u0010%\u001a\u00020\"2\f\u0010&\u001a\b\u0012\u0004\u0012\u00028\u00000\rH\u0000¢\u0006\u0002\b'J\b\u0010(\u001a\u00020\"H\u0002J\u001e\u0010)\u001a\u00020\"2\u0006\u0010*\u001a\u00020\u001a2\f\u0010+\u001a\b\u0012\u0004\u0012\u00028\u00000\rH\u0002J\u0016\u0010,\u001a\b\u0012\u0004\u0012\u00028\u00000\r2\u0006\u0010-\u001a\u00020\u001aH\u0002J.\u0010.\u001a\u00020\"2\u0006\u0010/\u001a\u0002002\f\u0010&\u001a\b\u0012\u0004\u0012\u00028\u00000\r2\u0006\u00101\u001a\u00020\u001a2\u0006\u00102\u001a\u00020\u001aH\u0002J\u001e\u00103\u001a\u00020\"2\u0006\u0010/\u001a\u0002042\f\u0010&\u001a\b\u0012\u0004\u0012\u00028\u00000\rH\u0002J\u001e\u00105\u001a\u00020\"2\u0006\u0010/\u001a\u0002062\f\u0010&\u001a\b\u0012\u0004\u0012\u00028\u00000\rH\u0002J\f\u00107\u001a\u00020\u001a*\u00020\u0014H\u0002R\u001e\u0010\u000b\u001a\u0010\u0012\f\u0012\n\u0012\u0004\u0012\u00028\u0000\u0018\u00010\r0\fX\u0082\u0004¢\u0006\u0004\n\u0002\u0010\u000eR\u001e\u0010\u000f\u001a\u0010\u0012\f\u0012\n\u0012\u0004\u0012\u00020\u0011\u0018\u00010\u00100\fX\u0082\u0004¢\u0006\u0004\n\u0002\u0010\u0012R6\u0010\u0013\u001a(\u0012\f\u0012\n \u0015*\u0004\u0018\u00010\u00140\u0014 \u0015*\u0014\u0012\u000e\b\u0001\u0012\n \u0015*\u0004\u0018\u00010\u00140\u0014\u0018\u00010\f0\fX\u0082\u0004¢\u0006\u0004\n\u0002\u0010\u0016R\u0014\u0010\b\u001a\b\u0012\u0004\u0012\u00028\u00000\tX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0017\u001a\u00020\u0018X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0019\u001a\u00020\u001aX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u001b\u001a\u00020\u001cX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u001d\u001a\u00020\u0018X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u001e\u001a\u00020\u001aX\u0082\u000e¢\u0006\u0002\n\u0000¨\u00068"}, d2 = {"Lorg/jetbrains/kotlin/codegen/optimization/temporaryVals/FastStoreLoadAnalyzer;", ExifInterface.GPS_MEASUREMENT_INTERRUPTED, "Lorg/jetbrains/kotlin/codegen/optimization/temporaryVals/StoreLoadValue;", "", "owner", "", "method", "Lorg/jetbrains/org/objectweb/asm/tree/MethodNode;", "interpreter", "Lorg/jetbrains/kotlin/codegen/optimization/temporaryVals/StoreLoadInterpreter;", "(Ljava/lang/String;Lorg/jetbrains/org/objectweb/asm/tree/MethodNode;Lorg/jetbrains/kotlin/codegen/optimization/temporaryVals/StoreLoadInterpreter;)V", "frames", "", "Lorg/jetbrains/kotlin/codegen/optimization/temporaryVals/StoreLoadFrame;", "[Lorg/jetbrains/kotlin/codegen/optimization/temporaryVals/StoreLoadFrame;", "handlers", "", "Lorg/jetbrains/org/objectweb/asm/tree/TryCatchBlockNode;", "[Ljava/util/List;", "insnsArray", "Lorg/jetbrains/org/objectweb/asm/tree/AbstractInsnNode;", JvmProtoBufUtil.PLATFORM_TYPE_ID, "[Lorg/jetbrains/org/objectweb/asm/tree/AbstractInsnNode;", "isMergeNode", "", "nInsns", "", "queue", "", "queued", "top", "analyze", "()[Lorg/jetbrains/kotlin/codegen/optimization/temporaryVals/StoreLoadFrame;", "checkAssertions", "", "computeExceptionHandlersForEachInsn", "m", "initLocals", "current", "initLocals$backend", "initMergeNodes", "mergeControlFlowEdge", "dest", TypedValues.AttributesType.S_FRAME, "newFrame", "maxLocals", "visitJumpInsnNode", "insnNode", "Lorg/jetbrains/org/objectweb/asm/tree/JumpInsnNode;", "insn", "insnOpcode", "visitLookupSwitchInsnNode", "Lorg/jetbrains/org/objectweb/asm/tree/LookupSwitchInsnNode;", "visitTableSwitchInsnNode", "Lorg/jetbrains/org/objectweb/asm/tree/TableSwitchInsnNode;", "indexOf", Marker.BACKEND}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FastStoreLoadAnalyzer<V extends StoreLoadValue> {
    private final StoreLoadFrame<V>[] frames;
    private final List<TryCatchBlockNode>[] handlers;
    private final AbstractInsnNode[] insnsArray;
    private final StoreLoadInterpreter<V> interpreter;
    private final boolean[] isMergeNode;
    private final MethodNode method;
    private final int nInsns;
    private final String owner;
    private final int[] queue;
    private final boolean[] queued;
    private int top;

    public FastStoreLoadAnalyzer(String owner, MethodNode method, StoreLoadInterpreter<V> interpreter) {
        Intrinsics.checkNotNullParameter(owner, "owner");
        Intrinsics.checkNotNullParameter(method, "method");
        Intrinsics.checkNotNullParameter(interpreter, "interpreter");
        this.owner = owner;
        this.method = method;
        this.interpreter = interpreter;
        this.insnsArray = method.instructions.toArray();
        int size = method.instructions.size();
        this.nInsns = size;
        this.isMergeNode = new boolean[size];
        this.frames = new StoreLoadFrame[size];
        this.handlers = new List[size];
        this.queued = new boolean[size];
        this.queue = new int[size];
    }

    public final StoreLoadFrame<V>[] analyze() throws AnalyzerException {
        if (this.nInsns == 0) {
            return this.frames;
        }
        checkAssertions();
        computeExceptionHandlersForEachInsn(this.method);
        initMergeNodes();
        StoreLoadFrame<V> storeLoadFrameNewFrame = newFrame(this.method.maxLocals);
        StoreLoadFrame<V> storeLoadFrameNewFrame2 = newFrame(this.method.maxLocals);
        initLocals$backend(storeLoadFrameNewFrame);
        mergeControlFlowEdge(0, storeLoadFrameNewFrame);
        while (true) {
            int i = this.top;
            if (i > 0) {
                int[] iArr = this.queue;
                int i2 = i - 1;
                this.top = i2;
                int i3 = iArr[i2];
                StoreLoadFrame<V> storeLoadFrame = this.frames[i3];
                Intrinsics.checkNotNull(storeLoadFrame);
                this.queued[i3] = false;
                AbstractInsnNode abstractInsnNode = this.method.instructions.get(i3);
                try {
                    int opcode = abstractInsnNode.getOpcode();
                    int type = abstractInsnNode.getType();
                    if (type == 8 || type == 14 || type == 15) {
                        mergeControlFlowEdge(i3 + 1, storeLoadFrame);
                    } else {
                        StoreLoadFrame<V> storeLoadFrameInit = storeLoadFrameNewFrame.init(storeLoadFrame);
                        Intrinsics.checkNotNull(abstractInsnNode);
                        storeLoadFrameInit.execute(abstractInsnNode, this.interpreter);
                        if (type == 7) {
                            visitJumpInsnNode((JumpInsnNode) abstractInsnNode, storeLoadFrameNewFrame, i3, opcode);
                        } else if (type == 12) {
                            visitLookupSwitchInsnNode((LookupSwitchInsnNode) abstractInsnNode, storeLoadFrameNewFrame);
                        } else if (type == 11) {
                            visitTableSwitchInsnNode((TableSwitchInsnNode) abstractInsnNode, storeLoadFrameNewFrame);
                        } else if (opcode != 191 && (opcode < 172 || opcode > 177)) {
                            mergeControlFlowEdge(i3 + 1, storeLoadFrameNewFrame);
                        }
                    }
                    List<TryCatchBlockNode> list = this.handlers[i3];
                    if (list != null) {
                        Iterator<T> it = list.iterator();
                        while (it.hasNext()) {
                            LabelNode handler = ((TryCatchBlockNode) it.next()).handler;
                            Intrinsics.checkNotNullExpressionValue(handler, "handler");
                            int iIndexOf = indexOf(handler);
                            storeLoadFrameNewFrame2.init(storeLoadFrame);
                            mergeControlFlowEdge(iIndexOf, storeLoadFrameNewFrame2);
                        }
                    }
                } catch (AnalyzerException e) {
                    AbstractInsnNode abstractInsnNode2 = e.node;
                    StringBuilder sbAppend = new StringBuilder("Error at instruction #").append(i3).append(StringUtil.SPACE);
                    InsnList instructions = this.method.instructions;
                    Intrinsics.checkNotNullExpressionValue(instructions, "instructions");
                    throw new AnalyzerException(abstractInsnNode2, sbAppend.append(InlineCodegenUtilsKt.insnText(abstractInsnNode, instructions)).append(": ").append(e.getMessage()).toString(), e);
                } catch (Exception e2) {
                    StringBuilder sbAppend2 = new StringBuilder("Error at instruction #").append(i3).append(StringUtil.SPACE);
                    InsnList instructions2 = this.method.instructions;
                    Intrinsics.checkNotNullExpressionValue(instructions2, "instructions");
                    throw new AnalyzerException(abstractInsnNode, sbAppend2.append(InlineCodegenUtilsKt.insnText(abstractInsnNode, instructions2)).append(": ").append(e2.getMessage()).toString(), e2);
                }
            } else {
                return this.frames;
            }
        }
    }

    private final StoreLoadFrame<V> newFrame(int maxLocals) {
        return new StoreLoadFrame<>(maxLocals);
    }

    private final int indexOf(AbstractInsnNode abstractInsnNode) {
        return this.method.instructions.indexOf(abstractInsnNode);
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

    private final void visitTableSwitchInsnNode(TableSwitchInsnNode insnNode, StoreLoadFrame<V> current) {
        LabelNode dflt = insnNode.dflt;
        Intrinsics.checkNotNullExpressionValue(dflt, "dflt");
        mergeControlFlowEdge(indexOf(dflt), current);
        for (LabelNode labelNode : insnNode.labels) {
            Intrinsics.checkNotNull(labelNode);
            mergeControlFlowEdge(indexOf(labelNode), current);
        }
    }

    private final void visitLookupSwitchInsnNode(LookupSwitchInsnNode insnNode, StoreLoadFrame<V> current) {
        LabelNode dflt = insnNode.dflt;
        Intrinsics.checkNotNullExpressionValue(dflt, "dflt");
        mergeControlFlowEdge(indexOf(dflt), current);
        for (LabelNode labelNode : insnNode.labels) {
            Intrinsics.checkNotNull(labelNode);
            mergeControlFlowEdge(indexOf(labelNode), current);
        }
    }

    private final void visitJumpInsnNode(JumpInsnNode insnNode, StoreLoadFrame<V> current, int insn, int insnOpcode) {
        if (insnOpcode != 167) {
            mergeControlFlowEdge(insn + 1, current);
        }
        LabelNode label = insnNode.label;
        Intrinsics.checkNotNullExpressionValue(label, "label");
        mergeControlFlowEdge(indexOf(label), current);
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

    private final void initMergeNodes() {
        AbstractInsnNode[] insnsArray = this.insnsArray;
        Intrinsics.checkNotNullExpressionValue(insnsArray, "insnsArray");
        for (AbstractInsnNode abstractInsnNode : insnsArray) {
            int type = abstractInsnNode.getType();
            if (type == 7) {
                Intrinsics.checkNotNull(abstractInsnNode, "null cannot be cast to non-null type org.jetbrains.org.objectweb.asm.tree.JumpInsnNode");
                boolean[] zArr = this.isMergeNode;
                LabelNode label = ((JumpInsnNode) abstractInsnNode).label;
                Intrinsics.checkNotNullExpressionValue(label, "label");
                zArr[indexOf(label)] = true;
            } else if (type == 11) {
                Intrinsics.checkNotNull(abstractInsnNode, "null cannot be cast to non-null type org.jetbrains.org.objectweb.asm.tree.TableSwitchInsnNode");
                TableSwitchInsnNode tableSwitchInsnNode = (TableSwitchInsnNode) abstractInsnNode;
                boolean[] zArr2 = this.isMergeNode;
                LabelNode dflt = tableSwitchInsnNode.dflt;
                Intrinsics.checkNotNullExpressionValue(dflt, "dflt");
                zArr2[indexOf(dflt)] = true;
                for (LabelNode labelNode : tableSwitchInsnNode.labels) {
                    boolean[] zArr3 = this.isMergeNode;
                    Intrinsics.checkNotNull(labelNode);
                    zArr3[indexOf(labelNode)] = true;
                }
            } else if (type == 12) {
                Intrinsics.checkNotNull(abstractInsnNode, "null cannot be cast to non-null type org.jetbrains.org.objectweb.asm.tree.LookupSwitchInsnNode");
                LookupSwitchInsnNode lookupSwitchInsnNode = (LookupSwitchInsnNode) abstractInsnNode;
                boolean[] zArr4 = this.isMergeNode;
                LabelNode dflt2 = lookupSwitchInsnNode.dflt;
                Intrinsics.checkNotNullExpressionValue(dflt2, "dflt");
                zArr4[indexOf(dflt2)] = true;
                for (LabelNode labelNode2 : lookupSwitchInsnNode.labels) {
                    boolean[] zArr5 = this.isMergeNode;
                    Intrinsics.checkNotNull(labelNode2);
                    zArr5[indexOf(labelNode2)] = true;
                }
            }
        }
        for (TryCatchBlockNode tryCatchBlockNode : this.method.tryCatchBlocks) {
            boolean[] zArr6 = this.isMergeNode;
            LabelNode handler = tryCatchBlockNode.handler;
            Intrinsics.checkNotNullExpressionValue(handler, "handler");
            zArr6[indexOf(handler)] = true;
        }
    }

    public final void initLocals$backend(StoreLoadFrame<V> current) {
        int i;
        Intrinsics.checkNotNullParameter(current, "current");
        Type[] argumentTypes = Type.getArgumentTypes(this.method.desc);
        if ((this.method.access & 8) == 0) {
            Type objectType = Type.getObjectType(this.owner);
            StoreLoadInterpreter<V> storeLoadInterpreter = this.interpreter;
            Intrinsics.checkNotNull(objectType);
            current.set(0, storeLoadInterpreter.valueParameter(objectType));
            i = 1;
        } else {
            i = 0;
        }
        Intrinsics.checkNotNull(argumentTypes);
        for (Type type : argumentTypes) {
            int i2 = i + 1;
            StoreLoadInterpreter<V> storeLoadInterpreter2 = this.interpreter;
            Intrinsics.checkNotNull(type);
            current.set(i, storeLoadInterpreter2.valueParameter(type));
            if (type.getSize() == 2) {
                i += 2;
                current.set(i2, this.interpreter.uninitialized());
            } else {
                i = i2;
            }
        }
        while (i < this.method.maxLocals) {
            current.set(i, this.interpreter.uninitialized());
            i++;
        }
    }

    /* JADX WARN: Code duplicated, block: B:11:0x0029  */
    /* JADX WARN: Code duplicated, block: B:13:0x002f  */
    /* JADX WARN: Code duplicated, block: B:15:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:16:? A[RETURN, SYNTHETIC] */
    private final void mergeControlFlowEdge(int dest, StoreLoadFrame<V> frame) {
        boolean zMerge;
        boolean[] zArr;
        StoreLoadFrame<V>[] storeLoadFrameArr = this.frames;
        StoreLoadFrame<V> storeLoadFrame = storeLoadFrameArr[dest];
        if (storeLoadFrame == null) {
            storeLoadFrameArr[dest] = newFrame(frame.getMaxLocals()).init(frame);
        } else {
            if (!this.isMergeNode[dest]) {
                storeLoadFrame.init(frame);
            } else {
                zMerge = storeLoadFrame.merge(frame, this.interpreter);
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
}
