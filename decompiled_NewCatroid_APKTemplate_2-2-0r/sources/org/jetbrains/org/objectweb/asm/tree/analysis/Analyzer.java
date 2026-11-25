package org.jetbrains.org.objectweb.asm.tree.analysis;

import com.badlogic.gdx.graphics.GL20;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import org.jetbrains.org.objectweb.asm.Opcodes;
import org.jetbrains.org.objectweb.asm.Type;
import org.jetbrains.org.objectweb.asm.tree.AbstractInsnNode;
import org.jetbrains.org.objectweb.asm.tree.IincInsnNode;
import org.jetbrains.org.objectweb.asm.tree.InsnList;
import org.jetbrains.org.objectweb.asm.tree.JumpInsnNode;
import org.jetbrains.org.objectweb.asm.tree.LabelNode;
import org.jetbrains.org.objectweb.asm.tree.LookupSwitchInsnNode;
import org.jetbrains.org.objectweb.asm.tree.MethodNode;
import org.jetbrains.org.objectweb.asm.tree.TableSwitchInsnNode;
import org.jetbrains.org.objectweb.asm.tree.TryCatchBlockNode;
import org.jetbrains.org.objectweb.asm.tree.VarInsnNode;
import org.jetbrains.org.objectweb.asm.tree.analysis.Value;

/* JADX INFO: loaded from: classes6.dex */
public class Analyzer<V extends Value> implements Opcodes {
    private Frame<V>[] frames;
    private List<TryCatchBlockNode>[] handlers;
    private boolean[] inInstructionsToProcess;
    private InsnList insnList;
    private int insnListSize;
    private int[] instructionsToProcess;
    private final Interpreter<V> interpreter;
    private int numInstructionsToProcess;
    private Subroutine[] subroutines;

    protected void init(String str, MethodNode methodNode) throws AnalyzerException {
    }

    protected void newControlFlowEdge(int i, int i2) {
    }

    protected boolean newControlFlowExceptionEdge(int i, int i2) {
        return true;
    }

    public Analyzer(Interpreter<V> interpreter) {
        this.interpreter = interpreter;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r11v11 */
    /* JADX WARN: Type inference failed for: r11v12 */
    /* JADX WARN: Type inference failed for: r11v13 */
    /* JADX WARN: Type inference failed for: r11v14 */
    /* JADX WARN: Type inference failed for: r11v15 */
    /* JADX WARN: Type inference failed for: r11v6, types: [org.jetbrains.org.objectweb.asm.tree.analysis.Subroutine] */
    /* JADX WARN: Type inference failed for: r19v0, types: [org.jetbrains.org.objectweb.asm.tree.analysis.Analyzer, org.jetbrains.org.objectweb.asm.tree.analysis.Analyzer<V extends org.jetbrains.org.objectweb.asm.tree.analysis.Value>] */
    /* JADX WARN: Type inference failed for: r1v18, types: [org.jetbrains.org.objectweb.asm.tree.analysis.Subroutine] */
    /* JADX WARN: Type inference failed for: r1v48 */
    /* JADX WARN: Type inference failed for: r1v53 */
    /* JADX WARN: Type inference failed for: r3v19 */
    /* JADX WARN: Type inference failed for: r3v20, types: [int] */
    /* JADX WARN: Type inference failed for: r3v24 */
    /* JADX WARN: Type inference failed for: r3v25, types: [int] */
    /* JADX WARN: Type inference failed for: r3v39 */
    /* JADX WARN: Type inference failed for: r3v40 */
    /* JADX WARN: Type inference failed for: r5v2 */
    /* JADX WARN: Type inference failed for: r5v20 */
    /* JADX WARN: Type inference failed for: r5v3, types: [int] */
    /* JADX WARN: Type inference failed for: r6v1 */
    /* JADX WARN: Type inference failed for: r6v10 */
    /* JADX WARN: Type inference failed for: r6v2, types: [org.jetbrains.org.objectweb.asm.tree.analysis.Subroutine] */
    /* JADX WARN: Type inference failed for: r6v3, types: [org.jetbrains.org.objectweb.asm.tree.analysis.Subroutine] */
    /* JADX WARN: Type inference failed for: r6v4 */
    /* JADX WARN: Type inference failed for: r6v6 */
    /* JADX WARN: Type inference failed for: r6v7 */
    public Frame<V>[] analyze(String str, MethodNode methodNode) throws AnalyzerException {
        AbstractInsnNode abstractInsnNode;
        ?? r1;
        Type objectType;
        ?? r11;
        ?? r12;
        boolean z = false;
        if ((methodNode.access & GL20.GL_INVALID_ENUM) != 0) {
            Frame<V>[] frameArr = new Frame[0];
            this.frames = frameArr;
            return frameArr;
        }
        InsnList insnList = methodNode.instructions;
        this.insnList = insnList;
        int size = insnList.size();
        this.insnListSize = size;
        this.handlers = new List[size];
        this.frames = new Frame[size];
        this.subroutines = new Subroutine[size];
        this.inInstructionsToProcess = new boolean[size];
        this.instructionsToProcess = new int[size];
        this.numInstructionsToProcess = 0;
        for (int i = 0; i < methodNode.tryCatchBlocks.size(); i++) {
            TryCatchBlockNode tryCatchBlockNode = methodNode.tryCatchBlocks.get(i);
            int iIndexOf = this.insnList.indexOf(tryCatchBlockNode.end);
            for (int iIndexOf2 = this.insnList.indexOf(tryCatchBlockNode.start); iIndexOf2 < iIndexOf; iIndexOf2++) {
                List<TryCatchBlockNode> arrayList = this.handlers[iIndexOf2];
                if (arrayList == null) {
                    arrayList = new ArrayList<>();
                    this.handlers[iIndexOf2] = arrayList;
                }
                arrayList.add(tryCatchBlockNode);
            }
        }
        LabelNode labelNode = null;
        Subroutine subroutine = new Subroutine(null, methodNode.maxLocals, null);
        ArrayList arrayList2 = new ArrayList();
        findSubroutine(0, subroutine, arrayList2);
        HashMap map = new HashMap();
        while (!arrayList2.isEmpty()) {
            JumpInsnNode jumpInsnNode = (JumpInsnNode) arrayList2.remove(0);
            Subroutine subroutine2 = (Subroutine) map.get(jumpInsnNode.label);
            if (subroutine2 == null) {
                Subroutine subroutine3 = new Subroutine(jumpInsnNode.label, methodNode.maxLocals, jumpInsnNode);
                map.put(jumpInsnNode.label, subroutine3);
                findSubroutine(this.insnList.indexOf(jumpInsnNode.label), subroutine3, arrayList2);
            } else {
                subroutine2.callers.add(jumpInsnNode);
            }
        }
        for (int i2 = 0; i2 < this.insnListSize; i2++) {
            Subroutine subroutine4 = this.subroutines[i2];
            if (subroutine4 != null && subroutine4.start == null) {
                this.subroutines[i2] = null;
            }
        }
        Frame frameComputeInitialFrame = computeInitialFrame(str, methodNode);
        merge(0, frameComputeInitialFrame, null);
        init(str, methodNode);
        while (true) {
            int i3 = this.numInstructionsToProcess;
            if (i3 > 0) {
                int[] iArr = this.instructionsToProcess;
                int i4 = i3 - 1;
                this.numInstructionsToProcess = i4;
                int i5 = iArr[i4];
                Frame<V> frame = this.frames[i5];
                Subroutine subroutine5 = this.subroutines[i5];
                this.inInstructionsToProcess[i5] = z;
                try {
                    try {
                        AbstractInsnNode abstractInsnNode2 = methodNode.instructions.get(i5);
                        try {
                            int opcode = abstractInsnNode2.getOpcode();
                            int type = abstractInsnNode2.getType();
                            if (type == 8 || type == 15 || type == 14) {
                                int i6 = i5 + 1;
                                merge(i6, frame, subroutine5);
                                newControlFlowEdge(i5, i6);
                                r1 = subroutine5;
                            } else {
                                frameComputeInitialFrame.init(frame).execute(abstractInsnNode2, this.interpreter);
                                ?? subroutine6 = subroutine5 == null ? labelNode : new Subroutine(subroutine5);
                                if (abstractInsnNode2 instanceof JumpInsnNode) {
                                    JumpInsnNode jumpInsnNode2 = (JumpInsnNode) abstractInsnNode2;
                                    if (opcode != 167 && opcode != 168) {
                                        frameComputeInitialFrame.initJumpTarget(opcode, labelNode);
                                        int i7 = i5 + 1;
                                        merge(i7, frameComputeInitialFrame, subroutine6);
                                        newControlFlowEdge(i5, i7);
                                    }
                                    int iIndexOf3 = this.insnList.indexOf(jumpInsnNode2.label);
                                    frameComputeInitialFrame.initJumpTarget(opcode, jumpInsnNode2.label);
                                    if (opcode == 168) {
                                        merge(iIndexOf3, frameComputeInitialFrame, new Subroutine(jumpInsnNode2.label, methodNode.maxLocals, jumpInsnNode2));
                                    } else {
                                        merge(iIndexOf3, frameComputeInitialFrame, subroutine6);
                                    }
                                    newControlFlowEdge(i5, iIndexOf3);
                                } else if (abstractInsnNode2 instanceof LookupSwitchInsnNode) {
                                    LookupSwitchInsnNode lookupSwitchInsnNode = (LookupSwitchInsnNode) abstractInsnNode2;
                                    int iIndexOf4 = this.insnList.indexOf(lookupSwitchInsnNode.dflt);
                                    frameComputeInitialFrame.initJumpTarget(opcode, lookupSwitchInsnNode.dflt);
                                    merge(iIndexOf4, frameComputeInitialFrame, subroutine6);
                                    newControlFlowEdge(i5, iIndexOf4);
                                    for (?? r3 = z; r3 < lookupSwitchInsnNode.labels.size(); r3++) {
                                        LabelNode labelNode2 = lookupSwitchInsnNode.labels.get(r3);
                                        int iIndexOf5 = this.insnList.indexOf(labelNode2);
                                        frameComputeInitialFrame.initJumpTarget(opcode, labelNode2);
                                        merge(iIndexOf5, frameComputeInitialFrame, subroutine6);
                                        newControlFlowEdge(i5, iIndexOf5);
                                    }
                                } else if (abstractInsnNode2 instanceof TableSwitchInsnNode) {
                                    TableSwitchInsnNode tableSwitchInsnNode = (TableSwitchInsnNode) abstractInsnNode2;
                                    int iIndexOf6 = this.insnList.indexOf(tableSwitchInsnNode.dflt);
                                    frameComputeInitialFrame.initJumpTarget(opcode, tableSwitchInsnNode.dflt);
                                    merge(iIndexOf6, frameComputeInitialFrame, subroutine6);
                                    newControlFlowEdge(i5, iIndexOf6);
                                    for (?? r4 = z; r4 < tableSwitchInsnNode.labels.size(); r4++) {
                                        LabelNode labelNode3 = tableSwitchInsnNode.labels.get(r4);
                                        frameComputeInitialFrame.initJumpTarget(opcode, labelNode3);
                                        int iIndexOf7 = this.insnList.indexOf(labelNode3);
                                        merge(iIndexOf7, frameComputeInitialFrame, subroutine6);
                                        newControlFlowEdge(i5, iIndexOf7);
                                    }
                                } else {
                                    if (opcode != 169) {
                                        r11 = subroutine6;
                                        if (opcode != 191 && (opcode < 172 || opcode > 177)) {
                                            if (r11 != 0) {
                                                if (abstractInsnNode2 instanceof VarInsnNode) {
                                                    int i8 = ((VarInsnNode) abstractInsnNode2).var;
                                                    r11.localsUsed[i8] = true;
                                                    if (opcode == 22 || opcode == 24 || opcode == 55 || opcode == 57) {
                                                        r11.localsUsed[i8 + 1] = true;
                                                    }
                                                } else if (abstractInsnNode2 instanceof IincInsnNode) {
                                                    r11.localsUsed[((IincInsnNode) abstractInsnNode2).var] = true;
                                                }
                                            }
                                            int i9 = i5 + 1;
                                            merge(i9, frameComputeInitialFrame, r11);
                                            newControlFlowEdge(i5, i9);
                                            r12 = r11;
                                        }
                                    } else {
                                        if (subroutine6 == 0) {
                                            throw new AnalyzerException(abstractInsnNode2, "RET instruction outside of a subroutine");
                                        }
                                        subroutine6 = subroutine6;
                                        for (?? r5 = z; r5 < subroutine6.callers.size(); r5++) {
                                            int iIndexOf8 = this.insnList.indexOf(subroutine6.callers.get(r5));
                                            Frame<V> frame2 = this.frames[iIndexOf8];
                                            if (frame2 != null) {
                                                int i10 = iIndexOf8 + 1;
                                                merge(i10, frame2, frameComputeInitialFrame, this.subroutines[iIndexOf8], subroutine6.localsUsed);
                                                newControlFlowEdge(i5, i10);
                                            }
                                            subroutine6 = subroutine6;
                                        }
                                    }
                                    r12 = r11;
                                    r12 = r11;
                                    r12 = r11;
                                    r1 = r12;
                                }
                                r12 = subroutine6;
                                r12 = r11;
                                r12 = r11;
                                r12 = r11;
                                r1 = r12;
                            }
                            List<TryCatchBlockNode> list = this.handlers[i5];
                            if (list != null) {
                                for (TryCatchBlockNode tryCatchBlockNode2 : list) {
                                    if (tryCatchBlockNode2.type == null) {
                                        objectType = Type.getObjectType("java/lang/Throwable");
                                    } else {
                                        objectType = Type.getObjectType(tryCatchBlockNode2.type);
                                    }
                                    if (newControlFlowExceptionEdge(i5, tryCatchBlockNode2)) {
                                        Frame<V> frameNewFrame = newFrame(frame);
                                        frameNewFrame.clearStack();
                                        frameNewFrame.push(this.interpreter.newExceptionValue(tryCatchBlockNode2, frameNewFrame, objectType));
                                        merge(this.insnList.indexOf(tryCatchBlockNode2.handler), frameNewFrame, r1);
                                    }
                                }
                            }
                            z = false;
                            labelNode = null;
                        } catch (RuntimeException e) {
                            e = e;
                            abstractInsnNode = abstractInsnNode2;
                            throw new AnalyzerException(abstractInsnNode, "Error at instruction " + i5 + ": " + e.getMessage(), e);
                        }
                    } catch (RuntimeException e2) {
                        e = e2;
                        abstractInsnNode = null;
                    }
                } catch (AnalyzerException e3) {
                    throw new AnalyzerException(e3.node, "Error at instruction " + i5 + ": " + e3.getMessage(), e3);
                }
            } else {
                return this.frames;
            }
        }
    }

    private void findSubroutine(int i, Subroutine subroutine, List<AbstractInsnNode> list) throws AnalyzerException {
        ArrayList arrayList = new ArrayList();
        arrayList.add(Integer.valueOf(i));
        while (!arrayList.isEmpty()) {
            int iIntValue = ((Integer) arrayList.remove(arrayList.size() - 1)).intValue();
            if (iIntValue < 0 || iIntValue >= this.insnListSize) {
                throw new AnalyzerException(null, "Execution can fall off the end of the code");
            }
            Subroutine[] subroutineArr = this.subroutines;
            if (subroutineArr[iIntValue] == null) {
                subroutineArr[iIntValue] = new Subroutine(subroutine);
                AbstractInsnNode abstractInsnNode = this.insnList.get(iIntValue);
                if (abstractInsnNode instanceof JumpInsnNode) {
                    if (abstractInsnNode.getOpcode() == 168) {
                        list.add(abstractInsnNode);
                    } else {
                        arrayList.add(Integer.valueOf(this.insnList.indexOf(((JumpInsnNode) abstractInsnNode).label)));
                    }
                } else if (abstractInsnNode instanceof TableSwitchInsnNode) {
                    TableSwitchInsnNode tableSwitchInsnNode = (TableSwitchInsnNode) abstractInsnNode;
                    findSubroutine(this.insnList.indexOf(tableSwitchInsnNode.dflt), subroutine, list);
                    for (int size = tableSwitchInsnNode.labels.size() - 1; size >= 0; size--) {
                        arrayList.add(Integer.valueOf(this.insnList.indexOf(tableSwitchInsnNode.labels.get(size))));
                    }
                } else if (abstractInsnNode instanceof LookupSwitchInsnNode) {
                    LookupSwitchInsnNode lookupSwitchInsnNode = (LookupSwitchInsnNode) abstractInsnNode;
                    findSubroutine(this.insnList.indexOf(lookupSwitchInsnNode.dflt), subroutine, list);
                    for (int size2 = lookupSwitchInsnNode.labels.size() - 1; size2 >= 0; size2--) {
                        arrayList.add(Integer.valueOf(this.insnList.indexOf(lookupSwitchInsnNode.labels.get(size2))));
                    }
                }
                List<TryCatchBlockNode> list2 = this.handlers[iIntValue];
                if (list2 != null) {
                    Iterator<TryCatchBlockNode> it = list2.iterator();
                    while (it.hasNext()) {
                        arrayList.add(Integer.valueOf(this.insnList.indexOf(it.next().handler)));
                    }
                }
                int opcode = abstractInsnNode.getOpcode();
                if (opcode != 167 && opcode != 191) {
                    switch (opcode) {
                        case 169:
                        case 170:
                        case 171:
                        case 172:
                        case 173:
                        case 174:
                        case 175:
                        case 176:
                        case 177:
                            break;
                        default:
                            arrayList.add(Integer.valueOf(iIntValue + 1));
                            break;
                    }
                }
            }
        }
    }

    private Frame<V> computeInitialFrame(String str, MethodNode methodNode) {
        Frame<V> frameNewFrame = newFrame(methodNode.maxLocals, methodNode.maxStack);
        int i = 1;
        boolean z = (methodNode.access & 8) == 0;
        if (z) {
            frameNewFrame.setLocal(0, this.interpreter.newParameterValue(z, 0, Type.getObjectType(str)));
        } else {
            i = 0;
        }
        for (Type type : Type.getArgumentTypes(methodNode.desc)) {
            frameNewFrame.setLocal(i, this.interpreter.newParameterValue(z, i, type));
            int i2 = i + 1;
            if (type.getSize() == 2) {
                frameNewFrame.setLocal(i2, this.interpreter.newEmptyValue(i2));
                i += 2;
            } else {
                i = i2;
            }
        }
        while (i < methodNode.maxLocals) {
            frameNewFrame.setLocal(i, this.interpreter.newEmptyValue(i));
            i++;
        }
        frameNewFrame.setReturn(this.interpreter.newReturnTypeValue(Type.getReturnType(methodNode.desc)));
        return frameNewFrame;
    }

    public Frame<V>[] getFrames() {
        return this.frames;
    }

    public List<TryCatchBlockNode> getHandlers(int i) {
        return this.handlers[i];
    }

    protected Frame<V> newFrame(int i, int i2) {
        return new Frame<>(i, i2);
    }

    protected Frame<V> newFrame(Frame<? extends V> frame) {
        return new Frame<>(frame);
    }

    protected boolean newControlFlowExceptionEdge(int i, TryCatchBlockNode tryCatchBlockNode) {
        return newControlFlowExceptionEdge(i, this.insnList.indexOf(tryCatchBlockNode.handler));
    }

    /* JADX WARN: Multi-variable type inference failed */
    private void merge(int i, Frame<V> frame, Subroutine subroutine) throws AnalyzerException {
        boolean zMerge;
        Frame<V>[] frameArr = this.frames;
        Frame<V> frame2 = frameArr[i];
        if (frame2 == null) {
            frameArr[i] = newFrame(frame);
            zMerge = true;
        } else {
            zMerge = frame2.merge(frame, this.interpreter);
        }
        Subroutine[] subroutineArr = this.subroutines;
        Subroutine subroutine2 = subroutineArr[i];
        if (subroutine2 == null) {
            if (subroutine != null) {
                subroutineArr[i] = new Subroutine(subroutine);
                zMerge = true;
            }
        } else if (subroutine != null) {
            zMerge |= subroutine2.merge(subroutine);
        }
        if (zMerge) {
            boolean[] zArr = this.inInstructionsToProcess;
            if (zArr[i]) {
                return;
            }
            zArr[i] = true;
            int[] iArr = this.instructionsToProcess;
            int i2 = this.numInstructionsToProcess;
            this.numInstructionsToProcess = i2 + 1;
            iArr[i2] = i;
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    private void merge(int i, Frame<V> frame, Frame<V> frame2, Subroutine subroutine, boolean[] zArr) throws AnalyzerException {
        boolean zMerge;
        frame2.merge(frame, zArr);
        Frame<V>[] frameArr = this.frames;
        Frame<V> frame3 = frameArr[i];
        if (frame3 == null) {
            frameArr[i] = newFrame(frame2);
            zMerge = true;
        } else {
            zMerge = frame3.merge(frame2, this.interpreter);
        }
        Subroutine subroutine2 = this.subroutines[i];
        if (subroutine2 != null && subroutine != null) {
            zMerge |= subroutine2.merge(subroutine);
        }
        if (zMerge) {
            boolean[] zArr2 = this.inInstructionsToProcess;
            if (zArr2[i]) {
                return;
            }
            zArr2[i] = true;
            int[] iArr = this.instructionsToProcess;
            int i2 = this.numInstructionsToProcess;
            this.numInstructionsToProcess = i2 + 1;
            iArr[i2] = i;
        }
    }
}
