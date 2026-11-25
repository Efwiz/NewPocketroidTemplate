package org.jetbrains.kotlin.cfg.pseudocode.instructions;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.cfg.pseudocode.instructions.eval.AccessValueInstruction;
import org.jetbrains.kotlin.cfg.pseudocode.instructions.eval.CallInstruction;
import org.jetbrains.kotlin.cfg.pseudocode.instructions.eval.LoadUnitValueInstruction;
import org.jetbrains.kotlin.cfg.pseudocode.instructions.eval.MagicInstruction;
import org.jetbrains.kotlin.cfg.pseudocode.instructions.eval.MergeInstruction;
import org.jetbrains.kotlin.cfg.pseudocode.instructions.eval.OperationInstruction;
import org.jetbrains.kotlin.cfg.pseudocode.instructions.eval.ReadValueInstruction;
import org.jetbrains.kotlin.cfg.pseudocode.instructions.eval.WriteValueInstruction;
import org.jetbrains.kotlin.cfg.pseudocode.instructions.jumps.AbstractJumpInstruction;
import org.jetbrains.kotlin.cfg.pseudocode.instructions.jumps.ConditionalJumpInstruction;
import org.jetbrains.kotlin.cfg.pseudocode.instructions.jumps.NondeterministicJumpInstruction;
import org.jetbrains.kotlin.cfg.pseudocode.instructions.jumps.ReturnNoValueInstruction;
import org.jetbrains.kotlin.cfg.pseudocode.instructions.jumps.ReturnValueInstruction;
import org.jetbrains.kotlin.cfg.pseudocode.instructions.jumps.ThrowExceptionInstruction;
import org.jetbrains.kotlin.cfg.pseudocode.instructions.jumps.UnconditionalJumpInstruction;
import org.jetbrains.kotlin.cfg.pseudocode.instructions.special.InlinedLocalFunctionDeclarationInstruction;
import org.jetbrains.kotlin.cfg.pseudocode.instructions.special.LocalFunctionDeclarationInstruction;
import org.jetbrains.kotlin.cfg.pseudocode.instructions.special.MarkInstruction;
import org.jetbrains.kotlin.cfg.pseudocode.instructions.special.SubroutineEnterInstruction;
import org.jetbrains.kotlin.cfg.pseudocode.instructions.special.SubroutineExitInstruction;
import org.jetbrains.kotlin.cfg.pseudocode.instructions.special.SubroutineSinkInstruction;
import org.jetbrains.kotlin.cfg.pseudocode.instructions.special.VariableDeclarationInstruction;

/* JADX INFO: compiled from: InstructionVisitor.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000¢\u0001\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0016\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0016J\u0010\u0010\u0007\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\bH\u0016J\u0010\u0010\t\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\nH\u0016J\u0010\u0010\u000b\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\fH\u0016J\u0010\u0010\r\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u000eH\u0016J\u0010\u0010\u000f\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0010H\u0016J\u0010\u0010\u0011\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0012H\u0016J\u0010\u0010\u0013\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0014H\u0016J\u0010\u0010\u0015\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0016H\u0016J\u0010\u0010\u0017\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0018H\u0016J\u0010\u0010\u0019\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u001aH\u0016J\u0010\u0010\u001b\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u001cH\u0016J\u0010\u0010\u001d\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u001eH\u0016J\u0010\u0010\u001f\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020 H\u0016J\u0010\u0010!\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\"H\u0016J\u0010\u0010#\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020$H\u0016J\u0010\u0010%\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020&H\u0016J\u0010\u0010'\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020(H\u0016J\u0010\u0010)\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020*H\u0016J\u0010\u0010+\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020,H\u0016J\u0010\u0010-\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020.H\u0016J\u0010\u0010/\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u000200H\u0016J\u0010\u00101\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u000202H\u0016J\u0010\u00103\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u000204H\u0016¨\u00065"}, d2 = {"Lorg/jetbrains/kotlin/cfg/pseudocode/instructions/InstructionVisitor;", "", "()V", "visitAccessInstruction", "", "instruction", "Lorg/jetbrains/kotlin/cfg/pseudocode/instructions/eval/AccessValueInstruction;", "visitCallInstruction", "Lorg/jetbrains/kotlin/cfg/pseudocode/instructions/eval/CallInstruction;", "visitConditionalJump", "Lorg/jetbrains/kotlin/cfg/pseudocode/instructions/jumps/ConditionalJumpInstruction;", "visitInlinedLocalFunctionDeclarationInstruction", "Lorg/jetbrains/kotlin/cfg/pseudocode/instructions/special/InlinedLocalFunctionDeclarationInstruction;", "visitInstruction", "Lorg/jetbrains/kotlin/cfg/pseudocode/instructions/Instruction;", "visitInstructionWithNext", "Lorg/jetbrains/kotlin/cfg/pseudocode/instructions/InstructionWithNext;", "visitJump", "Lorg/jetbrains/kotlin/cfg/pseudocode/instructions/jumps/AbstractJumpInstruction;", "visitLoadUnitValue", "Lorg/jetbrains/kotlin/cfg/pseudocode/instructions/eval/LoadUnitValueInstruction;", "visitLocalFunctionDeclarationInstruction", "Lorg/jetbrains/kotlin/cfg/pseudocode/instructions/special/LocalFunctionDeclarationInstruction;", "visitMagic", "Lorg/jetbrains/kotlin/cfg/pseudocode/instructions/eval/MagicInstruction;", "visitMarkInstruction", "Lorg/jetbrains/kotlin/cfg/pseudocode/instructions/special/MarkInstruction;", "visitMerge", "Lorg/jetbrains/kotlin/cfg/pseudocode/instructions/eval/MergeInstruction;", "visitNondeterministicJump", "Lorg/jetbrains/kotlin/cfg/pseudocode/instructions/jumps/NondeterministicJumpInstruction;", "visitOperation", "Lorg/jetbrains/kotlin/cfg/pseudocode/instructions/eval/OperationInstruction;", "visitReadValue", "Lorg/jetbrains/kotlin/cfg/pseudocode/instructions/eval/ReadValueInstruction;", "visitReturnNoValue", "Lorg/jetbrains/kotlin/cfg/pseudocode/instructions/jumps/ReturnNoValueInstruction;", "visitReturnValue", "Lorg/jetbrains/kotlin/cfg/pseudocode/instructions/jumps/ReturnValueInstruction;", "visitSubroutineEnter", "Lorg/jetbrains/kotlin/cfg/pseudocode/instructions/special/SubroutineEnterInstruction;", "visitSubroutineExit", "Lorg/jetbrains/kotlin/cfg/pseudocode/instructions/special/SubroutineExitInstruction;", "visitSubroutineSink", "Lorg/jetbrains/kotlin/cfg/pseudocode/instructions/special/SubroutineSinkInstruction;", "visitThrowExceptionInstruction", "Lorg/jetbrains/kotlin/cfg/pseudocode/instructions/jumps/ThrowExceptionInstruction;", "visitUnconditionalJump", "Lorg/jetbrains/kotlin/cfg/pseudocode/instructions/jumps/UnconditionalJumpInstruction;", "visitVariableDeclarationInstruction", "Lorg/jetbrains/kotlin/cfg/pseudocode/instructions/special/VariableDeclarationInstruction;", "visitWriteValue", "Lorg/jetbrains/kotlin/cfg/pseudocode/instructions/eval/WriteValueInstruction;", "cfg"}, k = 1, mv = {1, 9, 0}, xi = 48)
public class InstructionVisitor {
    public void visitInstruction(Instruction instruction) {
        Intrinsics.checkNotNullParameter(instruction, "instruction");
    }

    public void visitAccessInstruction(AccessValueInstruction instruction) {
        Intrinsics.checkNotNullParameter(instruction, "instruction");
        visitInstructionWithNext(instruction);
    }

    public void visitReadValue(ReadValueInstruction instruction) {
        Intrinsics.checkNotNullParameter(instruction, "instruction");
        visitAccessInstruction(instruction);
    }

    public void visitLocalFunctionDeclarationInstruction(LocalFunctionDeclarationInstruction instruction) {
        Intrinsics.checkNotNullParameter(instruction, "instruction");
        visitInstructionWithNext(instruction);
    }

    public void visitInlinedLocalFunctionDeclarationInstruction(InlinedLocalFunctionDeclarationInstruction instruction) {
        Intrinsics.checkNotNullParameter(instruction, "instruction");
        visitLocalFunctionDeclarationInstruction(instruction);
    }

    public void visitVariableDeclarationInstruction(VariableDeclarationInstruction instruction) {
        Intrinsics.checkNotNullParameter(instruction, "instruction");
        visitInstructionWithNext(instruction);
    }

    public void visitUnconditionalJump(UnconditionalJumpInstruction instruction) {
        Intrinsics.checkNotNullParameter(instruction, "instruction");
        visitJump(instruction);
    }

    public void visitConditionalJump(ConditionalJumpInstruction instruction) {
        Intrinsics.checkNotNullParameter(instruction, "instruction");
        visitJump(instruction);
    }

    public void visitReturnValue(ReturnValueInstruction instruction) {
        Intrinsics.checkNotNullParameter(instruction, "instruction");
        visitJump(instruction);
    }

    public void visitReturnNoValue(ReturnNoValueInstruction instruction) {
        Intrinsics.checkNotNullParameter(instruction, "instruction");
        visitJump(instruction);
    }

    public void visitThrowExceptionInstruction(ThrowExceptionInstruction instruction) {
        Intrinsics.checkNotNullParameter(instruction, "instruction");
        visitJump(instruction);
    }

    public void visitNondeterministicJump(NondeterministicJumpInstruction instruction) {
        Intrinsics.checkNotNullParameter(instruction, "instruction");
        visitInstruction(instruction);
    }

    public void visitSubroutineExit(SubroutineExitInstruction instruction) {
        Intrinsics.checkNotNullParameter(instruction, "instruction");
        visitInstruction(instruction);
    }

    public void visitSubroutineSink(SubroutineSinkInstruction instruction) {
        Intrinsics.checkNotNullParameter(instruction, "instruction");
        visitInstruction(instruction);
    }

    public void visitJump(AbstractJumpInstruction instruction) {
        Intrinsics.checkNotNullParameter(instruction, "instruction");
        visitInstruction(instruction);
    }

    public void visitInstructionWithNext(InstructionWithNext instruction) {
        Intrinsics.checkNotNullParameter(instruction, "instruction");
        visitInstruction(instruction);
    }

    public void visitSubroutineEnter(SubroutineEnterInstruction instruction) {
        Intrinsics.checkNotNullParameter(instruction, "instruction");
        visitInstructionWithNext(instruction);
    }

    public void visitWriteValue(WriteValueInstruction instruction) {
        Intrinsics.checkNotNullParameter(instruction, "instruction");
        visitAccessInstruction(instruction);
    }

    public void visitLoadUnitValue(LoadUnitValueInstruction instruction) {
        Intrinsics.checkNotNullParameter(instruction, "instruction");
        visitInstructionWithNext(instruction);
    }

    public void visitOperation(OperationInstruction instruction) {
        Intrinsics.checkNotNullParameter(instruction, "instruction");
        visitInstructionWithNext(instruction);
    }

    public void visitCallInstruction(CallInstruction instruction) {
        Intrinsics.checkNotNullParameter(instruction, "instruction");
        visitOperation(instruction);
    }

    public void visitMerge(MergeInstruction instruction) {
        Intrinsics.checkNotNullParameter(instruction, "instruction");
        visitOperation(instruction);
    }

    public void visitMarkInstruction(MarkInstruction instruction) {
        Intrinsics.checkNotNullParameter(instruction, "instruction");
        visitInstructionWithNext(instruction);
    }

    public void visitMagic(MagicInstruction instruction) {
        Intrinsics.checkNotNullParameter(instruction, "instruction");
        visitOperation(instruction);
    }
}
