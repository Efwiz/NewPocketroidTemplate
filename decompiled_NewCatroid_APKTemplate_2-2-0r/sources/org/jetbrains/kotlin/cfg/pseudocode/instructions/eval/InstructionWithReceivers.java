package org.jetbrains.kotlin.cfg.pseudocode.instructions.eval;

import java.util.Map;
import kotlin.Metadata;
import org.jetbrains.kotlin.cfg.pseudocode.PseudoValue;
import org.jetbrains.kotlin.cfg.pseudocode.instructions.Instruction;
import org.jetbrains.kotlin.resolve.scopes.receivers.ReceiverValue;

/* JADX INFO: compiled from: InstructionWithReceivers.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\bf\u0018\u00002\u00020\u0001R\u001e\u0010\u0002\u001a\u000e\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u00050\u0003X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0006\u0010\u0007ø\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001¨\u0006\bÀ\u0006\u0001"}, d2 = {"Lorg/jetbrains/kotlin/cfg/pseudocode/instructions/eval/InstructionWithReceivers;", "Lorg/jetbrains/kotlin/cfg/pseudocode/instructions/Instruction;", "receiverValues", "", "Lorg/jetbrains/kotlin/cfg/pseudocode/PseudoValue;", "Lorg/jetbrains/kotlin/resolve/scopes/receivers/ReceiverValue;", "getReceiverValues", "()Ljava/util/Map;", "cfg"}, k = 1, mv = {1, 9, 0}, xi = 48)
public interface InstructionWithReceivers extends Instruction {
    Map<PseudoValue, ReceiverValue> getReceiverValues();
}
