package com.reandroid.dex.ins;

import com.reandroid.arsc.base.Block;
import com.reandroid.arsc.item.IntegerReference;
import com.reandroid.arsc.item.ShortItem;
import com.reandroid.dex.data.InstructionList;
import com.reandroid.dex.smali.SmaliRegion;
import com.reandroid.dex.smali.model.SmaliInstruction;
import java.io.IOException;
import java.util.Iterator;

/* JADX INFO: loaded from: classes11.dex */
public abstract class PayloadData extends Ins implements SmaliRegion {
    @Override // com.reandroid.dex.ins.Ins
    public abstract void fromSmali(SmaliInstruction smaliInstruction) throws IOException;

    public abstract Iterator<IntegerReference> getReferences();

    public PayloadData(int i, Opcode<?> opcode) {
        super(i + 1, opcode);
        ShortItem shortItem = new ShortItem();
        shortItem.set(opcode.getValue());
        addChild(0, shortItem);
    }

    void updateNopAlignment() {
        InstructionList instructionList = getInstructionList();
        if (instructionList == null || instructionList.countUpTo(this) % 4 == 0) {
            return;
        }
        InsNop nopAlignment = getNopAlignment();
        if (nopAlignment != null) {
            instructionList.remove(nopAlignment);
        } else {
            instructionList.add(false, getIndex(), Opcode.NOP.newInstance());
        }
    }

    private InsNop getNopAlignment() {
        InstructionList instructionList = getInstructionList();
        if (instructionList == null) {
            return null;
        }
        Ins ins = instructionList.get(getIndex() - 1);
        if (ins instanceof InsNop) {
            return (InsNop) ins;
        }
        return null;
    }

    @Override // com.reandroid.arsc.base.BlockContainer
    protected void onPreRefresh() {
        updateNopAlignment();
        super.onPreRefresh();
    }

    @Override // com.reandroid.dex.ins.Ins
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        PayloadData payloadData = (PayloadData) obj;
        if (getIndex() != payloadData.getIndex()) {
            return false;
        }
        return Block.areEqual(getChildes(), payloadData.getChildes());
    }

    public int hashCode() {
        return Block.hashCodeOf(getChildes()) + getIndex();
    }
}
