package org.jetbrains.kotlin.cfg.pseudocode.instructions;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import kotlin.Metadata;
import kotlin.collections.SetsKt;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.cfg.pseudocode.PseudoValue;
import org.jetbrains.kotlin.cfg.pseudocode.Pseudocode;

/* JADX INFO: compiled from: InstructionImpl.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000D\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010#\n\u0002\b\u0003\n\u0002\u0010\u001e\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\r\n\u0002\u0010\u001f\n\u0002\b\b\b&\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0006\u0010%\u001a\u00020\u0001J\b\u0010&\u001a\u00020\u0000H$J\u0014\u0010'\u001a\u0004\u0018\u00010\u00012\b\u0010(\u001a\u0004\u0018\u00010\u0001H\u0004J\u0010\u0010)\u001a\u00020\u00012\u0006\u0010*\u001a\u00020\u0000H\u0004R\u0010\u0010\u0005\u001a\u0004\u0018\u00010\u0006X\u0082\u000e¢\u0006\u0002\n\u0000R\u0016\u0010\u0007\u001a\n\u0012\u0004\u0012\u00020\u0000\u0018\u00010\bX\u0082\u000e¢\u0006\u0002\n\u0000R\u0014\u0010\u0002\u001a\u00020\u0003X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u001a\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\u00010\f8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\r\u0010\u000eR\u0014\u0010\u000f\u001a\u00020\u00108VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0011\u0010\u0012R\u001a\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\u00150\u0014X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0016\u0010\u0017R\u001a\u0010\u0018\u001a\u00020\u0010X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0019\u0010\u0012\"\u0004\b\u001a\u0010\u001bR$\u0010\u001d\u001a\u00020\u00062\u0006\u0010\u001c\u001a\u00020\u00068V@VX\u0096\u000e¢\u0006\f\u001a\u0004\b\u001e\u0010\u001f\"\u0004\b \u0010!R\u001a\u0010\"\u001a\b\u0012\u0004\u0012\u00020\u00010#X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b$\u0010\u000e¨\u0006+"}, d2 = {"Lorg/jetbrains/kotlin/cfg/pseudocode/instructions/InstructionImpl;", "Lorg/jetbrains/kotlin/cfg/pseudocode/instructions/Instruction;", "blockScope", "Lorg/jetbrains/kotlin/cfg/pseudocode/instructions/BlockScope;", "(Lorg/jetbrains/kotlin/cfg/pseudocode/instructions/BlockScope;)V", "_owner", "Lorg/jetbrains/kotlin/cfg/pseudocode/Pseudocode;", "allCopies", "", "getBlockScope", "()Lorg/jetbrains/kotlin/cfg/pseudocode/instructions/BlockScope;", ConfigConstants.CONFIG_RENAMELIMIT_COPIES, "", "getCopies", "()Ljava/util/Collection;", "dead", "", "getDead", "()Z", "inputValues", "", "Lorg/jetbrains/kotlin/cfg/pseudocode/PseudoValue;", "getInputValues", "()Ljava/util/List;", "markedAsDead", "getMarkedAsDead", "setMarkedAsDead", "(Z)V", "value", "owner", "getOwner", "()Lorg/jetbrains/kotlin/cfg/pseudocode/Pseudocode;", "setOwner", "(Lorg/jetbrains/kotlin/cfg/pseudocode/Pseudocode;)V", "previousInstructions", "", "getPreviousInstructions", ConfigConstants.CONFIG_RENAMELIMIT_COPY, "createCopy", "outgoingEdgeTo", "target", "updateCopyInfo", "instruction", "cfg"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class InstructionImpl implements Instruction {
    private Pseudocode _owner;
    private Set<InstructionImpl> allCopies;
    private final BlockScope blockScope;
    private final List<PseudoValue> inputValues;
    private boolean markedAsDead;
    private final Collection<Instruction> previousInstructions;

    protected abstract InstructionImpl createCopy();

    public InstructionImpl(BlockScope blockScope) {
        Intrinsics.checkNotNullParameter(blockScope, "blockScope");
        this.blockScope = blockScope;
        this.previousInstructions = new LinkedHashSet();
        List<PseudoValue> listEmptyList = Collections.emptyList();
        Intrinsics.checkNotNullExpressionValue(listEmptyList, "emptyList(...)");
        this.inputValues = listEmptyList;
    }

    @Override // org.jetbrains.kotlin.cfg.pseudocode.instructions.Instruction
    public BlockScope getBlockScope() {
        return this.blockScope;
    }

    @Override // org.jetbrains.kotlin.cfg.pseudocode.instructions.Instruction
    public Pseudocode getOwner() {
        Pseudocode pseudocode = this._owner;
        Intrinsics.checkNotNull(pseudocode);
        return pseudocode;
    }

    @Override // org.jetbrains.kotlin.cfg.pseudocode.instructions.Instruction
    public void setOwner(Pseudocode value) {
        Intrinsics.checkNotNullParameter(value, "value");
        Pseudocode pseudocode = this._owner;
        if (pseudocode != null) {
            Intrinsics.areEqual(pseudocode, value);
        }
        this._owner = value;
    }

    @Override // org.jetbrains.kotlin.cfg.pseudocode.instructions.Instruction
    public Collection<Instruction> getCopies() {
        Set<InstructionImpl> set = this.allCopies;
        if (set == null) {
            List listEmptyList = Collections.emptyList();
            Intrinsics.checkNotNullExpressionValue(listEmptyList, "emptyList(...)");
            return listEmptyList;
        }
        ArrayList arrayList = new ArrayList();
        for (Object obj : set) {
            if (!Intrinsics.areEqual((InstructionImpl) obj, this)) {
                arrayList.add(obj);
            }
        }
        return arrayList;
    }

    public final Instruction copy() {
        return updateCopyInfo(createCopy());
    }

    protected final Instruction updateCopyInfo(InstructionImpl instruction) {
        Intrinsics.checkNotNullParameter(instruction, "instruction");
        if (this.allCopies == null) {
            this.allCopies = SetsKt.hashSetOf(this);
        }
        instruction.allCopies = this.allCopies;
        Set<InstructionImpl> set = this.allCopies;
        Intrinsics.checkNotNull(set);
        set.add(instruction);
        return instruction;
    }

    public final boolean getMarkedAsDead() {
        return this.markedAsDead;
    }

    public final void setMarkedAsDead(boolean z) {
        this.markedAsDead = z;
    }

    @Override // org.jetbrains.kotlin.cfg.pseudocode.instructions.Instruction
    public boolean getDead() {
        Set<InstructionImpl> set = this.allCopies;
        if (set == null) {
            return this.markedAsDead;
        }
        Set<InstructionImpl> set2 = set;
        if ((set2 instanceof Collection) && set2.isEmpty()) {
            return true;
        }
        Iterator<T> it = set2.iterator();
        while (it.hasNext()) {
            if (!((InstructionImpl) it.next()).markedAsDead) {
                return false;
            }
        }
        return true;
    }

    @Override // org.jetbrains.kotlin.cfg.pseudocode.instructions.Instruction
    public Collection<Instruction> getPreviousInstructions() {
        return this.previousInstructions;
    }

    protected final Instruction outgoingEdgeTo(Instruction target) {
        Collection<Instruction> previousInstructions;
        InstructionImpl instructionImpl = (InstructionImpl) target;
        if (instructionImpl != null && (previousInstructions = instructionImpl.getPreviousInstructions()) != null) {
            previousInstructions.add(this);
        }
        return target;
    }

    @Override // org.jetbrains.kotlin.cfg.pseudocode.instructions.Instruction
    public List<PseudoValue> getInputValues() {
        return this.inputValues;
    }
}
