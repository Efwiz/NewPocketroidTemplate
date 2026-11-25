package org.jetbrains.kotlin.com.intellij.psi.controlFlow;

import com.beust.jcommander.Parameters;
import java.util.ArrayList;
import java.util.List;
import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;

/* JADX INFO: loaded from: classes3.dex */
public class ControlFlowSubRange implements ControlFlow {
    private final ControlFlow myControlFlow;
    private final int myEnd;
    private List<Instruction> myInstructions;
    private final int myStart;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = (i == 1 || i == 2) ? "Argument for @NotNull parameter '%s' of %s.%s must not be null" : "@NotNull method %s.%s must not return null";
        Object[] objArr = new Object[(i == 1 || i == 2) ? 3 : 2];
        if (i == 1 || i == 2) {
            objArr[0] = CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD;
        } else {
            objArr[0] = "org/jetbrains/kotlin/com/intellij/psi/controlFlow/ControlFlowSubRange";
        }
        if (i == 1 || i == 2) {
            objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/controlFlow/ControlFlowSubRange";
        } else {
            objArr[1] = "getInstructions";
        }
        if (i == 1) {
            objArr[2] = "getStartOffset";
        } else if (i == 2) {
            objArr[2] = "getEndOffset";
        }
        String str2 = String.format(str, objArr);
        if (i != 1 && i != 2) {
            throw new IllegalStateException(str2);
        }
        throw new IllegalArgumentException(str2);
    }

    public ControlFlowSubRange(ControlFlow controlFlow, int i, int i2) {
        this.myControlFlow = controlFlow;
        this.myStart = i;
        this.myEnd = i2;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.controlFlow.ControlFlow
    public List<Instruction> getInstructions() {
        if (this.myInstructions == null) {
            ArrayList arrayList = new ArrayList(this.myEnd - this.myStart);
            List<Instruction> instructions = this.myControlFlow.getInstructions();
            for (int i = this.myStart; i < this.myEnd; i++) {
                Instruction instructionClone = instructions.get(i).clone();
                if (instructionClone instanceof BranchingInstruction) {
                    BranchingInstruction branchingInstruction = (BranchingInstruction) instructionClone;
                    branchingInstruction.offset = patchOffset(branchingInstruction.offset);
                }
                if (instructionClone instanceof CallInstruction) {
                    CallInstruction callInstruction = (CallInstruction) instructionClone;
                    callInstruction.procBegin = patchOffset(callInstruction.procBegin);
                    callInstruction.procEnd = patchOffset(callInstruction.procEnd);
                }
                if (instructionClone instanceof ReturnInstruction) {
                    ReturnInstruction returnInstruction = (ReturnInstruction) instructionClone;
                    returnInstruction.setCallInstruction(new CallInstruction(patchOffset(returnInstruction.getProcBegin()), patchOffset(returnInstruction.getProcEnd())));
                }
                arrayList.add(instructionClone);
            }
            this.myInstructions = arrayList;
        }
        List<Instruction> list = this.myInstructions;
        if (list == null) {
            $$$reportNull$$$0(0);
        }
        return list;
    }

    private int patchOffset(int i) {
        int i2 = this.myStart;
        if (i < i2) {
            i = i2;
        } else {
            int i3 = this.myEnd;
            if (i > i3) {
                i = i3;
            }
        }
        return i - i2;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.controlFlow.ControlFlow
    public int getSize() {
        return this.myEnd - this.myStart;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.controlFlow.ControlFlow
    public int getStartOffset(PsiElement psiElement) {
        if (psiElement == null) {
            $$$reportNull$$$0(1);
        }
        return patchOffset(this.myControlFlow.getStartOffset(psiElement));
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.controlFlow.ControlFlow
    public int getEndOffset(PsiElement psiElement) {
        if (psiElement == null) {
            $$$reportNull$$$0(2);
        }
        return patchOffset(this.myControlFlow.getEndOffset(psiElement));
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.controlFlow.ControlFlow
    public PsiElement getElement(int i) {
        return this.myControlFlow.getElement(this.myStart + i);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.controlFlow.ControlFlow
    public boolean isConstantConditionOccurred() {
        return this.myControlFlow.isConstantConditionOccurred();
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("CF range:[");
        sb.append(this.myStart).append(Parameters.DEFAULT_OPTION_PREFIXES).append(this.myEnd).append("]\n");
        List<Instruction> instructions = getInstructions();
        for (int i = 0; i < instructions.size(); i++) {
            sb.append(i).append(": ").append(instructions.get(i).toString()).append("\n");
        }
        return sb.toString();
    }
}
