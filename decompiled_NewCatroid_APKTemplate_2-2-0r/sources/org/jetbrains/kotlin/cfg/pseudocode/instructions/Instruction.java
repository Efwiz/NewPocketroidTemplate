package org.jetbrains.kotlin.cfg.pseudocode.instructions;

import java.util.Collection;
import java.util.List;
import kotlin.Metadata;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.cfg.pseudocode.PseudoValue;
import org.jetbrains.kotlin.cfg.pseudocode.Pseudocode;

/* JADX INFO: compiled from: Instruction.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000J\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u001e\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\bf\u0018\u00002\u00020\u0001J\u0010\u0010\u001d\u001a\u00020\u001e2\u0006\u0010\u001f\u001a\u00020 H&J!\u0010\u001d\u001a\u0002H!\"\u0004\b\u0000\u0010!2\f\u0010\u001f\u001a\b\u0012\u0004\u0012\u0002H!0\"H&¢\u0006\u0002\u0010#R\u0012\u0010\u0002\u001a\u00020\u0003X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0004\u0010\u0005R\u0018\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\u00000\u0007X¦\u0004¢\u0006\u0006\u001a\u0004\b\b\u0010\tR\u0012\u0010\n\u001a\u00020\u000bX¦\u0004¢\u0006\u0006\u001a\u0004\b\f\u0010\rR\u0018\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u00100\u000fX¦\u0004¢\u0006\u0006\u001a\u0004\b\u0011\u0010\u0012R\u0018\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\u00000\u0007X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0014\u0010\tR\u0018\u0010\u0015\u001a\u00020\u0016X¦\u000e¢\u0006\f\u001a\u0004\b\u0017\u0010\u0018\"\u0004\b\u0019\u0010\u001aR\u0018\u0010\u001b\u001a\b\u0012\u0004\u0012\u00020\u00000\u0007X¦\u0004¢\u0006\u0006\u001a\u0004\b\u001c\u0010\tø\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001¨\u0006$À\u0006\u0001"}, d2 = {"Lorg/jetbrains/kotlin/cfg/pseudocode/instructions/Instruction;", "", "blockScope", "Lorg/jetbrains/kotlin/cfg/pseudocode/instructions/BlockScope;", "getBlockScope", "()Lorg/jetbrains/kotlin/cfg/pseudocode/instructions/BlockScope;", ConfigConstants.CONFIG_RENAMELIMIT_COPIES, "", "getCopies", "()Ljava/util/Collection;", "dead", "", "getDead", "()Z", "inputValues", "", "Lorg/jetbrains/kotlin/cfg/pseudocode/PseudoValue;", "getInputValues", "()Ljava/util/List;", "nextInstructions", "getNextInstructions", "owner", "Lorg/jetbrains/kotlin/cfg/pseudocode/Pseudocode;", "getOwner", "()Lorg/jetbrains/kotlin/cfg/pseudocode/Pseudocode;", "setOwner", "(Lorg/jetbrains/kotlin/cfg/pseudocode/Pseudocode;)V", "previousInstructions", "getPreviousInstructions", "accept", "", "visitor", "Lorg/jetbrains/kotlin/cfg/pseudocode/instructions/InstructionVisitor;", "R", "Lorg/jetbrains/kotlin/cfg/pseudocode/instructions/InstructionVisitorWithResult;", "(Lorg/jetbrains/kotlin/cfg/pseudocode/instructions/InstructionVisitorWithResult;)Ljava/lang/Object;", "cfg"}, k = 1, mv = {1, 9, 0}, xi = 48)
public interface Instruction {
    <R> R accept(InstructionVisitorWithResult<? extends R> visitor);

    void accept(InstructionVisitor visitor);

    BlockScope getBlockScope();

    Collection<Instruction> getCopies();

    boolean getDead();

    List<PseudoValue> getInputValues();

    Collection<Instruction> getNextInstructions();

    Pseudocode getOwner();

    Collection<Instruction> getPreviousInstructions();

    void setOwner(Pseudocode pseudocode);
}
