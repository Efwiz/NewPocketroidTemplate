package org.jetbrains.kotlin.cfg.pseudocode;

import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.cfg.Label;
import org.jetbrains.kotlin.cfg.pseudocode.instructions.Instruction;
import org.jetbrains.kotlin.psi.KtElement;

/* JADX INFO: compiled from: PseudocodeLabel.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u00004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\b\n\u0002\b\n\u0018\u00002\u00020\u0001B!\b\u0000\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\b\u0010\u0006\u001a\u0004\u0018\u00010\u0005¢\u0006\u0002\u0010\u0007J\u0016\u0010\u001b\u001a\u00020\u00002\u0006\u0010\u001c\u001a\u00020\u00032\u0006\u0010\u001d\u001a\u00020\u0016J\b\u0010\u001e\u001a\u00020\u000eH\u0016J\b\u0010\u001f\u001a\u00020\u0005H\u0016R\u0010\u0010\u0006\u001a\u0004\u0018\u00010\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\b\u001a\u00020\t8BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\n\u0010\u000bR\u001a\u0010\f\u001a\b\u0012\u0004\u0012\u00020\u000e0\r8BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\u000f\u0010\u0010R\u0014\u0010\u0004\u001a\u00020\u0005X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0012R\u0014\u0010\u0002\u001a\u00020\u0003X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u0014R\u001a\u0010\u0015\u001a\u00020\u0016X\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0017\u0010\u0018\"\u0004\b\u0019\u0010\u001a¨\u0006 "}, d2 = {"Lorg/jetbrains/kotlin/cfg/pseudocode/PseudocodeLabel;", "Lorg/jetbrains/kotlin/cfg/Label;", "pseudocode", "Lorg/jetbrains/kotlin/cfg/pseudocode/PseudocodeImpl;", "name", "", "comment", "(Lorg/jetbrains/kotlin/cfg/pseudocode/PseudocodeImpl;Ljava/lang/String;Ljava/lang/String;)V", "correspondingElement", "Lorg/jetbrains/kotlin/psi/KtElement;", "getCorrespondingElement", "()Lorg/jetbrains/kotlin/psi/KtElement;", "instructionList", "", "Lorg/jetbrains/kotlin/cfg/pseudocode/instructions/Instruction;", "getInstructionList", "()Ljava/util/List;", "getName", "()Ljava/lang/String;", "getPseudocode", "()Lorg/jetbrains/kotlin/cfg/pseudocode/PseudocodeImpl;", "targetInstructionIndex", "", "getTargetInstructionIndex", "()I", "setTargetInstructionIndex", "(I)V", ConfigConstants.CONFIG_RENAMELIMIT_COPY, "newPseudocode", "newLabelIndex", "resolveToInstruction", "toString", "cfg"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class PseudocodeLabel implements Label {
    private final String comment;
    private final String name;
    private final PseudocodeImpl pseudocode;
    private int targetInstructionIndex;

    public PseudocodeLabel(PseudocodeImpl pseudocode, String name2, String str) {
        Intrinsics.checkNotNullParameter(pseudocode, "pseudocode");
        Intrinsics.checkNotNullParameter(name2, "name");
        this.pseudocode = pseudocode;
        this.name = name2;
        this.comment = str;
        this.targetInstructionIndex = -1;
    }

    @Override // org.jetbrains.kotlin.cfg.Label
    public String getName() {
        return this.name;
    }

    @Override // org.jetbrains.kotlin.cfg.Label
    public PseudocodeImpl getPseudocode() {
        return this.pseudocode;
    }

    private final List<Instruction> getInstructionList() {
        return getPseudocode().getMutableInstructionList$cfg();
    }

    private final KtElement getCorrespondingElement() {
        return getPseudocode().getCorrespondingElement();
    }

    @Override // org.jetbrains.kotlin.cfg.Label
    public int getTargetInstructionIndex() {
        return this.targetInstructionIndex;
    }

    public void setTargetInstructionIndex(int i) {
        this.targetInstructionIndex = i;
    }

    public String toString() {
        return this.comment == null ? getName() : getName() + " [" + this.comment + ']';
    }

    @Override // org.jetbrains.kotlin.cfg.Label
    public Instruction resolveToInstruction() {
        int targetInstructionIndex = getTargetInstructionIndex();
        if (targetInstructionIndex < 0) {
            throw new IllegalStateException(("resolveToInstruction: unbound label " + getName() + " in subroutine " + getCorrespondingElement().getText() + " with instructions " + getInstructionList()).toString());
        }
        if (targetInstructionIndex >= getInstructionList().size()) {
            throw new IllegalStateException(("resolveToInstruction: incorrect index " + targetInstructionIndex + " for label " + getName() + " in subroutine " + getCorrespondingElement().getText() + " with instructions " + getInstructionList()).toString());
        }
        return getInstructionList().get(targetInstructionIndex);
    }

    public final PseudocodeLabel copy(PseudocodeImpl newPseudocode, int newLabelIndex) {
        Intrinsics.checkNotNullParameter(newPseudocode, "newPseudocode");
        return new PseudocodeLabel(newPseudocode, "L" + newLabelIndex, "copy of " + getName() + ", " + this.comment);
    }
}
