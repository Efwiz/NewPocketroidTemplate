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

/* JADX INFO: compiled from: InstructionVisitorWithResult.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000Î\u0001\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b&\u0018\u0000*\u0006\b\u0000\u0010\u0001 \u00012\u00020\u0002B\u0005¢\u0006\u0002\u0010\u0003J\u0015\u0010\u0004\u001a\u00028\u00002\u0006\u0010\u0005\u001a\u00020\u0006H\u0016¢\u0006\u0002\u0010\u0007J\u0015\u0010\b\u001a\u00028\u00002\u0006\u0010\u0005\u001a\u00020\tH\u0016¢\u0006\u0002\u0010\nJ\u0015\u0010\u000b\u001a\u00028\u00002\u0006\u0010\u0005\u001a\u00020\fH\u0016¢\u0006\u0002\u0010\rJ\u0015\u0010\u000e\u001a\u00028\u00002\u0006\u0010\u0005\u001a\u00020\u000fH\u0016¢\u0006\u0002\u0010\u0010J\u0015\u0010\u0011\u001a\u00028\u00002\u0006\u0010\u0005\u001a\u00020\u0012H&¢\u0006\u0002\u0010\u0013J\u0015\u0010\u0014\u001a\u00028\u00002\u0006\u0010\u0005\u001a\u00020\u0015H\u0016¢\u0006\u0002\u0010\u0016J\u0015\u0010\u0017\u001a\u00028\u00002\u0006\u0010\u0005\u001a\u00020\u0018H\u0016¢\u0006\u0002\u0010\u0019J\u0015\u0010\u001a\u001a\u00028\u00002\u0006\u0010\u0005\u001a\u00020\u001bH\u0016¢\u0006\u0002\u0010\u001cJ\u0015\u0010\u001d\u001a\u00028\u00002\u0006\u0010\u0005\u001a\u00020\u001eH\u0016¢\u0006\u0002\u0010\u001fJ\u0015\u0010 \u001a\u00028\u00002\u0006\u0010\u0005\u001a\u00020!H\u0016¢\u0006\u0002\u0010\"J\u0015\u0010#\u001a\u00028\u00002\u0006\u0010\u0005\u001a\u00020$H\u0016¢\u0006\u0002\u0010%J\u0015\u0010&\u001a\u00028\u00002\u0006\u0010\u0005\u001a\u00020'H\u0016¢\u0006\u0002\u0010(J\u0015\u0010)\u001a\u00028\u00002\u0006\u0010\u0005\u001a\u00020*H\u0016¢\u0006\u0002\u0010+J\u0015\u0010,\u001a\u00028\u00002\u0006\u0010\u0005\u001a\u00020-H\u0016¢\u0006\u0002\u0010.J\u0015\u0010/\u001a\u00028\u00002\u0006\u0010\u0005\u001a\u000200H\u0016¢\u0006\u0002\u00101J\u0015\u00102\u001a\u00028\u00002\u0006\u0010\u0005\u001a\u000203H\u0016¢\u0006\u0002\u00104J\u0015\u00105\u001a\u00028\u00002\u0006\u0010\u0005\u001a\u000206H\u0016¢\u0006\u0002\u00107J\u0015\u00108\u001a\u00028\u00002\u0006\u0010\u0005\u001a\u000209H\u0016¢\u0006\u0002\u0010:J\u0015\u0010;\u001a\u00028\u00002\u0006\u0010\u0005\u001a\u00020<H\u0016¢\u0006\u0002\u0010=J\u0015\u0010>\u001a\u00028\u00002\u0006\u0010\u0005\u001a\u00020?H\u0016¢\u0006\u0002\u0010@J\u0015\u0010A\u001a\u00028\u00002\u0006\u0010\u0005\u001a\u00020BH\u0016¢\u0006\u0002\u0010CJ\u0015\u0010D\u001a\u00028\u00002\u0006\u0010\u0005\u001a\u00020EH\u0016¢\u0006\u0002\u0010FJ\u0015\u0010G\u001a\u00028\u00002\u0006\u0010\u0005\u001a\u00020HH\u0016¢\u0006\u0002\u0010IJ\u0015\u0010J\u001a\u00028\u00002\u0006\u0010\u0005\u001a\u00020KH\u0016¢\u0006\u0002\u0010L¨\u0006M"}, d2 = {"Lorg/jetbrains/kotlin/cfg/pseudocode/instructions/InstructionVisitorWithResult;", "R", "", "()V", "visitAccessInstruction", "instruction", "Lorg/jetbrains/kotlin/cfg/pseudocode/instructions/eval/AccessValueInstruction;", "(Lorg/jetbrains/kotlin/cfg/pseudocode/instructions/eval/AccessValueInstruction;)Ljava/lang/Object;", "visitCallInstruction", "Lorg/jetbrains/kotlin/cfg/pseudocode/instructions/eval/CallInstruction;", "(Lorg/jetbrains/kotlin/cfg/pseudocode/instructions/eval/CallInstruction;)Ljava/lang/Object;", "visitConditionalJump", "Lorg/jetbrains/kotlin/cfg/pseudocode/instructions/jumps/ConditionalJumpInstruction;", "(Lorg/jetbrains/kotlin/cfg/pseudocode/instructions/jumps/ConditionalJumpInstruction;)Ljava/lang/Object;", "visitInlinedFunctionDeclarationInstruction", "Lorg/jetbrains/kotlin/cfg/pseudocode/instructions/special/InlinedLocalFunctionDeclarationInstruction;", "(Lorg/jetbrains/kotlin/cfg/pseudocode/instructions/special/InlinedLocalFunctionDeclarationInstruction;)Ljava/lang/Object;", "visitInstruction", "Lorg/jetbrains/kotlin/cfg/pseudocode/instructions/Instruction;", "(Lorg/jetbrains/kotlin/cfg/pseudocode/instructions/Instruction;)Ljava/lang/Object;", "visitInstructionWithNext", "Lorg/jetbrains/kotlin/cfg/pseudocode/instructions/InstructionWithNext;", "(Lorg/jetbrains/kotlin/cfg/pseudocode/instructions/InstructionWithNext;)Ljava/lang/Object;", "visitJump", "Lorg/jetbrains/kotlin/cfg/pseudocode/instructions/jumps/AbstractJumpInstruction;", "(Lorg/jetbrains/kotlin/cfg/pseudocode/instructions/jumps/AbstractJumpInstruction;)Ljava/lang/Object;", "visitLoadUnitValue", "Lorg/jetbrains/kotlin/cfg/pseudocode/instructions/eval/LoadUnitValueInstruction;", "(Lorg/jetbrains/kotlin/cfg/pseudocode/instructions/eval/LoadUnitValueInstruction;)Ljava/lang/Object;", "visitLocalFunctionDeclarationInstruction", "Lorg/jetbrains/kotlin/cfg/pseudocode/instructions/special/LocalFunctionDeclarationInstruction;", "(Lorg/jetbrains/kotlin/cfg/pseudocode/instructions/special/LocalFunctionDeclarationInstruction;)Ljava/lang/Object;", "visitMagic", "Lorg/jetbrains/kotlin/cfg/pseudocode/instructions/eval/MagicInstruction;", "(Lorg/jetbrains/kotlin/cfg/pseudocode/instructions/eval/MagicInstruction;)Ljava/lang/Object;", "visitMarkInstruction", "Lorg/jetbrains/kotlin/cfg/pseudocode/instructions/special/MarkInstruction;", "(Lorg/jetbrains/kotlin/cfg/pseudocode/instructions/special/MarkInstruction;)Ljava/lang/Object;", "visitMerge", "Lorg/jetbrains/kotlin/cfg/pseudocode/instructions/eval/MergeInstruction;", "(Lorg/jetbrains/kotlin/cfg/pseudocode/instructions/eval/MergeInstruction;)Ljava/lang/Object;", "visitNondeterministicJump", "Lorg/jetbrains/kotlin/cfg/pseudocode/instructions/jumps/NondeterministicJumpInstruction;", "(Lorg/jetbrains/kotlin/cfg/pseudocode/instructions/jumps/NondeterministicJumpInstruction;)Ljava/lang/Object;", "visitOperation", "Lorg/jetbrains/kotlin/cfg/pseudocode/instructions/eval/OperationInstruction;", "(Lorg/jetbrains/kotlin/cfg/pseudocode/instructions/eval/OperationInstruction;)Ljava/lang/Object;", "visitReadValue", "Lorg/jetbrains/kotlin/cfg/pseudocode/instructions/eval/ReadValueInstruction;", "(Lorg/jetbrains/kotlin/cfg/pseudocode/instructions/eval/ReadValueInstruction;)Ljava/lang/Object;", "visitReturnNoValue", "Lorg/jetbrains/kotlin/cfg/pseudocode/instructions/jumps/ReturnNoValueInstruction;", "(Lorg/jetbrains/kotlin/cfg/pseudocode/instructions/jumps/ReturnNoValueInstruction;)Ljava/lang/Object;", "visitReturnValue", "Lorg/jetbrains/kotlin/cfg/pseudocode/instructions/jumps/ReturnValueInstruction;", "(Lorg/jetbrains/kotlin/cfg/pseudocode/instructions/jumps/ReturnValueInstruction;)Ljava/lang/Object;", "visitSubroutineEnter", "Lorg/jetbrains/kotlin/cfg/pseudocode/instructions/special/SubroutineEnterInstruction;", "(Lorg/jetbrains/kotlin/cfg/pseudocode/instructions/special/SubroutineEnterInstruction;)Ljava/lang/Object;", "visitSubroutineExit", "Lorg/jetbrains/kotlin/cfg/pseudocode/instructions/special/SubroutineExitInstruction;", "(Lorg/jetbrains/kotlin/cfg/pseudocode/instructions/special/SubroutineExitInstruction;)Ljava/lang/Object;", "visitSubroutineSink", "Lorg/jetbrains/kotlin/cfg/pseudocode/instructions/special/SubroutineSinkInstruction;", "(Lorg/jetbrains/kotlin/cfg/pseudocode/instructions/special/SubroutineSinkInstruction;)Ljava/lang/Object;", "visitThrowExceptionInstruction", "Lorg/jetbrains/kotlin/cfg/pseudocode/instructions/jumps/ThrowExceptionInstruction;", "(Lorg/jetbrains/kotlin/cfg/pseudocode/instructions/jumps/ThrowExceptionInstruction;)Ljava/lang/Object;", "visitUnconditionalJump", "Lorg/jetbrains/kotlin/cfg/pseudocode/instructions/jumps/UnconditionalJumpInstruction;", "(Lorg/jetbrains/kotlin/cfg/pseudocode/instructions/jumps/UnconditionalJumpInstruction;)Ljava/lang/Object;", "visitVariableDeclarationInstruction", "Lorg/jetbrains/kotlin/cfg/pseudocode/instructions/special/VariableDeclarationInstruction;", "(Lorg/jetbrains/kotlin/cfg/pseudocode/instructions/special/VariableDeclarationInstruction;)Ljava/lang/Object;", "visitWriteValue", "Lorg/jetbrains/kotlin/cfg/pseudocode/instructions/eval/WriteValueInstruction;", "(Lorg/jetbrains/kotlin/cfg/pseudocode/instructions/eval/WriteValueInstruction;)Ljava/lang/Object;", "cfg"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class InstructionVisitorWithResult<R> {
    public abstract R visitInstruction(Instruction instruction);

    public R visitAccessInstruction(AccessValueInstruction instruction) {
        Intrinsics.checkNotNullParameter(instruction, "instruction");
        return visitInstructionWithNext(instruction);
    }

    public R visitReadValue(ReadValueInstruction instruction) {
        Intrinsics.checkNotNullParameter(instruction, "instruction");
        return visitAccessInstruction(instruction);
    }

    public R visitLocalFunctionDeclarationInstruction(LocalFunctionDeclarationInstruction instruction) {
        Intrinsics.checkNotNullParameter(instruction, "instruction");
        return visitInstructionWithNext(instruction);
    }

    public R visitInlinedFunctionDeclarationInstruction(InlinedLocalFunctionDeclarationInstruction instruction) {
        Intrinsics.checkNotNullParameter(instruction, "instruction");
        return visitLocalFunctionDeclarationInstruction(instruction);
    }

    public R visitVariableDeclarationInstruction(VariableDeclarationInstruction instruction) {
        Intrinsics.checkNotNullParameter(instruction, "instruction");
        return visitInstructionWithNext(instruction);
    }

    public R visitUnconditionalJump(UnconditionalJumpInstruction instruction) {
        Intrinsics.checkNotNullParameter(instruction, "instruction");
        return visitJump(instruction);
    }

    public R visitConditionalJump(ConditionalJumpInstruction instruction) {
        Intrinsics.checkNotNullParameter(instruction, "instruction");
        return visitJump(instruction);
    }

    public R visitReturnValue(ReturnValueInstruction instruction) {
        Intrinsics.checkNotNullParameter(instruction, "instruction");
        return visitJump(instruction);
    }

    public R visitReturnNoValue(ReturnNoValueInstruction instruction) {
        Intrinsics.checkNotNullParameter(instruction, "instruction");
        return visitJump(instruction);
    }

    public R visitThrowExceptionInstruction(ThrowExceptionInstruction instruction) {
        Intrinsics.checkNotNullParameter(instruction, "instruction");
        return visitJump(instruction);
    }

    public R visitNondeterministicJump(NondeterministicJumpInstruction instruction) {
        Intrinsics.checkNotNullParameter(instruction, "instruction");
        return visitInstruction(instruction);
    }

    public R visitSubroutineExit(SubroutineExitInstruction instruction) {
        Intrinsics.checkNotNullParameter(instruction, "instruction");
        return visitInstruction(instruction);
    }

    public R visitSubroutineSink(SubroutineSinkInstruction instruction) {
        Intrinsics.checkNotNullParameter(instruction, "instruction");
        return visitInstruction(instruction);
    }

    public R visitJump(AbstractJumpInstruction instruction) {
        Intrinsics.checkNotNullParameter(instruction, "instruction");
        return visitInstruction(instruction);
    }

    public R visitInstructionWithNext(InstructionWithNext instruction) {
        Intrinsics.checkNotNullParameter(instruction, "instruction");
        return visitInstruction(instruction);
    }

    public R visitSubroutineEnter(SubroutineEnterInstruction instruction) {
        Intrinsics.checkNotNullParameter(instruction, "instruction");
        return visitInstructionWithNext(instruction);
    }

    public R visitWriteValue(WriteValueInstruction instruction) {
        Intrinsics.checkNotNullParameter(instruction, "instruction");
        return visitAccessInstruction(instruction);
    }

    public R visitLoadUnitValue(LoadUnitValueInstruction instruction) {
        Intrinsics.checkNotNullParameter(instruction, "instruction");
        return visitInstructionWithNext(instruction);
    }

    public R visitOperation(OperationInstruction instruction) {
        Intrinsics.checkNotNullParameter(instruction, "instruction");
        return visitInstructionWithNext(instruction);
    }

    public R visitCallInstruction(CallInstruction instruction) {
        Intrinsics.checkNotNullParameter(instruction, "instruction");
        return visitOperation(instruction);
    }

    public R visitMerge(MergeInstruction instruction) {
        Intrinsics.checkNotNullParameter(instruction, "instruction");
        return visitOperation(instruction);
    }

    public R visitMarkInstruction(MarkInstruction instruction) {
        Intrinsics.checkNotNullParameter(instruction, "instruction");
        return visitInstructionWithNext(instruction);
    }

    public R visitMagic(MagicInstruction instruction) {
        Intrinsics.checkNotNullParameter(instruction, "instruction");
        return visitOperation(instruction);
    }
}
