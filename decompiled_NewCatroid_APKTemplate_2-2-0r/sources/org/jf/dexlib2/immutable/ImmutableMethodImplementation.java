package org.jf.dexlib2.immutable;

import java.util.List;
import org.jf.dexlib2.iface.ExceptionHandler;
import org.jf.dexlib2.iface.MethodImplementation;
import org.jf.dexlib2.iface.TryBlock;
import org.jf.dexlib2.iface.debug.DebugItem;
import org.jf.dexlib2.iface.instruction.Instruction;
import org.jf.dexlib2.immutable.debug.ImmutableDebugItem;
import org.jf.dexlib2.immutable.instruction.ImmutableInstruction;
import org.jf.util.ImmutableUtils;

/* JADX INFO: loaded from: classes6.dex */
public class ImmutableMethodImplementation implements MethodImplementation {
    protected final List<? extends ImmutableDebugItem> debugItems;
    protected final List<? extends ImmutableInstruction> instructions;
    protected final int registerCount;
    protected final List<? extends ImmutableTryBlock> tryBlocks;

    public ImmutableMethodImplementation(int i, Iterable<? extends Instruction> iterable, List<? extends TryBlock<? extends ExceptionHandler>> list, Iterable<? extends DebugItem> iterable2) {
        this.registerCount = i;
        this.instructions = ImmutableInstruction.immutableListOf(iterable);
        this.tryBlocks = ImmutableTryBlock.immutableListOf(list);
        this.debugItems = ImmutableDebugItem.immutableListOf(iterable2);
    }

    public ImmutableMethodImplementation(int i, List<? extends ImmutableInstruction> list, List<? extends ImmutableTryBlock> list2, List<? extends ImmutableDebugItem> list3) {
        this.registerCount = i;
        this.instructions = ImmutableUtils.nullToEmptyList(list);
        this.tryBlocks = ImmutableUtils.nullToEmptyList(list2);
        this.debugItems = ImmutableUtils.nullToEmptyList(list3);
    }

    public static ImmutableMethodImplementation of(MethodImplementation methodImplementation) {
        if (methodImplementation == null) {
            return null;
        }
        if (methodImplementation instanceof ImmutableMethodImplementation) {
            return (ImmutableMethodImplementation) methodImplementation;
        }
        return new ImmutableMethodImplementation(methodImplementation.getRegisterCount(), methodImplementation.getInstructions(), methodImplementation.getTryBlocks(), methodImplementation.getDebugItems());
    }

    @Override // org.jf.dexlib2.iface.MethodImplementation
    public int getRegisterCount() {
        return this.registerCount;
    }

    @Override // org.jf.dexlib2.iface.MethodImplementation
    public List<? extends ImmutableInstruction> getInstructions() {
        return this.instructions;
    }

    @Override // org.jf.dexlib2.iface.MethodImplementation
    public List<? extends ImmutableTryBlock> getTryBlocks() {
        return this.tryBlocks;
    }

    @Override // org.jf.dexlib2.iface.MethodImplementation
    public List<? extends ImmutableDebugItem> getDebugItems() {
        return this.debugItems;
    }
}
