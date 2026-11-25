package org.jetbrains.kotlin.cfg.pseudocode;

import java.util.List;
import java.util.Set;
import kotlin.Metadata;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.cfg.pseudocode.instructions.Instruction;
import org.jetbrains.kotlin.cfg.pseudocode.instructions.KtElementInstruction;
import org.jetbrains.kotlin.cfg.pseudocode.instructions.special.LocalFunctionDeclarationInstruction;
import org.jetbrains.kotlin.cfg.pseudocode.instructions.special.SubroutineEnterInstruction;
import org.jetbrains.kotlin.cfg.pseudocode.instructions.special.SubroutineExitInstruction;
import org.jetbrains.kotlin.cfg.pseudocode.instructions.special.SubroutineSinkInstruction;
import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;
import org.jetbrains.kotlin.psi.KtElement;

/* JADX INFO: compiled from: Pseudocode.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000Z\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\"\n\u0002\u0018\u0002\n\u0002\b\n\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\bf\u0018\u00002\u00020\u0001J\b\u0010,\u001a\u00020\u0000H&J\u0014\u0010-\u001a\u0004\u0018\u00010.2\b\u0010/\u001a\u0004\u0018\u00010\u0007H&J\u0018\u00100\u001a\b\u0012\u0004\u0012\u00020\u00160\u00152\b\u00101\u001a\u0004\u0018\u00010.H&J\u0018\u00102\u001a\b\u0012\u0004\u0012\u00020\u00070\u00152\b\u00101\u001a\u0004\u0018\u00010.H&J\u0012\u00103\u001a\u0004\u0018\u0001042\u0006\u0010/\u001a\u00020\u0007H&J\u0010\u00105\u001a\u00020\u00032\u0006\u00106\u001a\u00020\u0016H&R\u0012\u0010\u0002\u001a\u00020\u0003X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0004\u0010\u0005R\u0012\u0010\u0006\u001a\u00020\u0007X¦\u0004¢\u0006\u0006\u001a\u0004\b\b\u0010\tR\u0012\u0010\n\u001a\u00020\u000bX¦\u0004¢\u0006\u0006\u001a\u0004\b\f\u0010\rR\u0012\u0010\u000e\u001a\u00020\u000fX¦\u0004¢\u0006\u0006\u001a\u0004\b\u0010\u0010\u0011R\u0012\u0010\u0012\u001a\u00020\u000fX¦\u0004¢\u0006\u0006\u001a\u0004\b\u0013\u0010\u0011R\u0018\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\u00160\u0015X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0017\u0010\u0018R\u0018\u0010\u0019\u001a\b\u0012\u0004\u0012\u00020\u00160\u0015X¦\u0004¢\u0006\u0006\u001a\u0004\b\u001a\u0010\u0018R\u0012\u0010\u001b\u001a\u00020\u0003X¦\u0004¢\u0006\u0006\u001a\u0004\b\u001b\u0010\u0005R\u0018\u0010\u001c\u001a\b\u0012\u0004\u0012\u00020\u001e0\u001dX¦\u0004¢\u0006\u0006\u001a\u0004\b\u001f\u0010 R\u0014\u0010!\u001a\u0004\u0018\u00010\u0000X¦\u0004¢\u0006\u0006\u001a\u0004\b\"\u0010#R\u0018\u0010$\u001a\b\u0012\u0004\u0012\u00020\u00160\u0015X¦\u0004¢\u0006\u0006\u001a\u0004\b%\u0010\u0018R\u0012\u0010&\u001a\u00020\u0000X¦\u0004¢\u0006\u0006\u001a\u0004\b'\u0010#R\u0012\u0010(\u001a\u00020)X¦\u0004¢\u0006\u0006\u001a\u0004\b*\u0010+ø\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001¨\u00067À\u0006\u0001"}, d2 = {"Lorg/jetbrains/kotlin/cfg/pseudocode/Pseudocode;", "", "containsDoWhile", "", "getContainsDoWhile", "()Z", "correspondingElement", "Lorg/jetbrains/kotlin/psi/KtElement;", "getCorrespondingElement", "()Lorg/jetbrains/kotlin/psi/KtElement;", "enterInstruction", "Lorg/jetbrains/kotlin/cfg/pseudocode/instructions/special/SubroutineEnterInstruction;", "getEnterInstruction", "()Lorg/jetbrains/kotlin/cfg/pseudocode/instructions/special/SubroutineEnterInstruction;", "errorInstruction", "Lorg/jetbrains/kotlin/cfg/pseudocode/instructions/special/SubroutineExitInstruction;", "getErrorInstruction", "()Lorg/jetbrains/kotlin/cfg/pseudocode/instructions/special/SubroutineExitInstruction;", "exitInstruction", "getExitInstruction", "instructions", "", "Lorg/jetbrains/kotlin/cfg/pseudocode/instructions/Instruction;", "getInstructions", "()Ljava/util/List;", "instructionsIncludingDeadCode", "getInstructionsIncludingDeadCode", "isInlined", "localDeclarations", "", "Lorg/jetbrains/kotlin/cfg/pseudocode/instructions/special/LocalFunctionDeclarationInstruction;", "getLocalDeclarations", "()Ljava/util/Set;", "parent", "getParent", "()Lorg/jetbrains/kotlin/cfg/pseudocode/Pseudocode;", "reversedInstructions", "getReversedInstructions", "rootPseudocode", "getRootPseudocode", "sinkInstruction", "Lorg/jetbrains/kotlin/cfg/pseudocode/instructions/special/SubroutineSinkInstruction;", "getSinkInstruction", "()Lorg/jetbrains/kotlin/cfg/pseudocode/instructions/special/SubroutineSinkInstruction;", ConfigConstants.CONFIG_RENAMELIMIT_COPY, "getElementValue", "Lorg/jetbrains/kotlin/cfg/pseudocode/PseudoValue;", CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD, "getUsages", "value", "getValueElements", "instructionForElement", "Lorg/jetbrains/kotlin/cfg/pseudocode/instructions/KtElementInstruction;", "isSideEffectFree", "instruction", "cfg"}, k = 1, mv = {1, 9, 0}, xi = 48)
public interface Pseudocode {
    Pseudocode copy();

    boolean getContainsDoWhile();

    KtElement getCorrespondingElement();

    PseudoValue getElementValue(KtElement element);

    SubroutineEnterInstruction getEnterInstruction();

    SubroutineExitInstruction getErrorInstruction();

    SubroutineExitInstruction getExitInstruction();

    List<Instruction> getInstructions();

    List<Instruction> getInstructionsIncludingDeadCode();

    Set<LocalFunctionDeclarationInstruction> getLocalDeclarations();

    Pseudocode getParent();

    List<Instruction> getReversedInstructions();

    Pseudocode getRootPseudocode();

    SubroutineSinkInstruction getSinkInstruction();

    List<Instruction> getUsages(PseudoValue value);

    List<KtElement> getValueElements(PseudoValue value);

    KtElementInstruction instructionForElement(KtElement element);

    boolean isInlined();

    boolean isSideEffectFree(Instruction instruction);
}
