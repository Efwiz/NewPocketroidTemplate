package org.jetbrains.kotlin.cfg;

import org.jetbrains.kotlin.cfg.pseudocode.instructions.Instruction;
import org.jetbrains.kotlin.cfg.pseudocode.instructions.InstructionVisitorWithResult;
import org.jetbrains.kotlin.cfg.pseudocode.instructions.eval.MagicInstruction;
import org.jetbrains.kotlin.cfg.pseudocode.instructions.eval.MergeInstruction;
import org.jetbrains.kotlin.cfg.pseudocode.instructions.jumps.AbstractJumpInstruction;
import org.jetbrains.kotlin.cfg.pseudocode.instructions.jumps.ThrowExceptionInstruction;
import org.jetbrains.kotlin.cfg.pseudocode.instructions.special.MarkInstruction;
import org.jetbrains.kotlin.cfg.pseudocode.instructions.special.SubroutineExitInstruction;
import org.jetbrains.kotlin.cfg.pseudocode.instructions.special.SubroutineSinkInstruction;
import org.jetbrains.kotlin.psi.KtElement;

/* JADX INFO: loaded from: classes2.dex */
public class TailInstructionDetector extends InstructionVisitorWithResult<Boolean> {
    private final KtElement subroutine;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        Object[] objArr = new Object[3];
        switch (i) {
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
                objArr[0] = "instruction";
                break;
            default:
                objArr[0] = "subroutine";
                break;
        }
        objArr[1] = "org/jetbrains/kotlin/cfg/TailInstructionDetector";
        switch (i) {
            case 1:
                objArr[2] = "visitInstruction";
                break;
            case 2:
                objArr[2] = "visitSubroutineExit";
                break;
            case 3:
                objArr[2] = "visitSubroutineSink";
                break;
            case 4:
                objArr[2] = "visitJump";
                break;
            case 5:
                objArr[2] = "visitThrowExceptionInstruction";
                break;
            case 6:
                objArr[2] = "visitMarkInstruction";
                break;
            case 7:
                objArr[2] = "visitMagic";
                break;
            case 8:
                objArr[2] = "visitMerge";
                break;
            default:
                objArr[2] = "<init>";
                break;
        }
        throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
    }

    public TailInstructionDetector(KtElement ktElement) {
        if (ktElement == null) {
            $$$reportNull$$$0(0);
        }
        this.subroutine = ktElement;
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // org.jetbrains.kotlin.cfg.pseudocode.instructions.InstructionVisitorWithResult
    public Boolean visitInstruction(Instruction instruction) {
        if (instruction == null) {
            $$$reportNull$$$0(1);
        }
        return false;
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // org.jetbrains.kotlin.cfg.pseudocode.instructions.InstructionVisitorWithResult
    public Boolean visitSubroutineExit(SubroutineExitInstruction subroutineExitInstruction) {
        if (subroutineExitInstruction == null) {
            $$$reportNull$$$0(2);
        }
        return Boolean.valueOf(!subroutineExitInstruction.getIsError() && subroutineExitInstruction.getSubroutine() == this.subroutine);
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // org.jetbrains.kotlin.cfg.pseudocode.instructions.InstructionVisitorWithResult
    public Boolean visitSubroutineSink(SubroutineSinkInstruction subroutineSinkInstruction) {
        if (subroutineSinkInstruction == null) {
            $$$reportNull$$$0(3);
        }
        return Boolean.valueOf(subroutineSinkInstruction.getSubroutine() == this.subroutine);
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // org.jetbrains.kotlin.cfg.pseudocode.instructions.InstructionVisitorWithResult
    public Boolean visitJump(AbstractJumpInstruction abstractJumpInstruction) {
        if (abstractJumpInstruction == null) {
            $$$reportNull$$$0(4);
        }
        return true;
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // org.jetbrains.kotlin.cfg.pseudocode.instructions.InstructionVisitorWithResult
    public Boolean visitThrowExceptionInstruction(ThrowExceptionInstruction throwExceptionInstruction) {
        if (throwExceptionInstruction == null) {
            $$$reportNull$$$0(5);
        }
        return false;
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // org.jetbrains.kotlin.cfg.pseudocode.instructions.InstructionVisitorWithResult
    public Boolean visitMarkInstruction(MarkInstruction markInstruction) {
        if (markInstruction == null) {
            $$$reportNull$$$0(6);
        }
        return true;
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // org.jetbrains.kotlin.cfg.pseudocode.instructions.InstructionVisitorWithResult
    public Boolean visitMagic(MagicInstruction magicInstruction) {
        if (magicInstruction == null) {
            $$$reportNull$$$0(7);
        }
        return Boolean.valueOf(magicInstruction.getSynthetic());
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // org.jetbrains.kotlin.cfg.pseudocode.instructions.InstructionVisitorWithResult
    public Boolean visitMerge(MergeInstruction mergeInstruction) {
        if (mergeInstruction == null) {
            $$$reportNull$$$0(8);
        }
        return true;
    }
}
