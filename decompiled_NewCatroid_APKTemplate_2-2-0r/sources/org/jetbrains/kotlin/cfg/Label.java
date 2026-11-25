package org.jetbrains.kotlin.cfg;

import kotlin.Metadata;
import org.jetbrains.kotlin.cfg.pseudocode.Pseudocode;
import org.jetbrains.kotlin.cfg.pseudocode.instructions.Instruction;

/* JADX INFO: compiled from: Label.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\bf\u0018\u00002\u00020\u0001J\b\u0010\u000e\u001a\u00020\u000fH&R\u0012\u0010\u0002\u001a\u00020\u0003X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0004\u0010\u0005R\u0012\u0010\u0006\u001a\u00020\u0007X¦\u0004¢\u0006\u0006\u001a\u0004\b\b\u0010\tR\u0012\u0010\n\u001a\u00020\u000bX¦\u0004¢\u0006\u0006\u001a\u0004\b\f\u0010\rø\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001¨\u0006\u0010À\u0006\u0001"}, d2 = {"Lorg/jetbrains/kotlin/cfg/Label;", "", "name", "", "getName", "()Ljava/lang/String;", "pseudocode", "Lorg/jetbrains/kotlin/cfg/pseudocode/Pseudocode;", "getPseudocode", "()Lorg/jetbrains/kotlin/cfg/pseudocode/Pseudocode;", "targetInstructionIndex", "", "getTargetInstructionIndex", "()I", "resolveToInstruction", "Lorg/jetbrains/kotlin/cfg/pseudocode/instructions/Instruction;", "cfg"}, k = 1, mv = {1, 9, 0}, xi = 48)
public interface Label {
    String getName();

    Pseudocode getPseudocode();

    int getTargetInstructionIndex();

    Instruction resolveToInstruction();
}
