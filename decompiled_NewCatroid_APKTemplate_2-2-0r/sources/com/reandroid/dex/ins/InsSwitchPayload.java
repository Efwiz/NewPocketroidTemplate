package com.reandroid.dex.ins;

import com.reandroid.arsc.item.IntegerReference;
import com.reandroid.dex.common.Register;
import com.reandroid.dex.common.RegistersTable;
import com.reandroid.dex.data.InstructionList;
import com.reandroid.dex.smali.SmaliFormat;
import com.reandroid.dex.smali.SmaliRegion;
import com.reandroid.utils.ObjectsUtil;
import java.util.Iterator;
import java.util.List;
import name.antonsmirnov.firmata.FormatHelper;

/* JADX INFO: loaded from: classes11.dex */
public abstract class InsSwitchPayload extends PayloadData implements Iterable<SwitchEntry>, LabelsSet, SmaliRegion, SmaliFormat {
    private InsSwitch insSwitch;

    public abstract Opcode<? extends InsSwitch> getSwitchOpcode();

    public InsSwitchPayload(int i, Opcode<?> opcode) {
        super(i, opcode);
    }

    @Override // com.reandroid.dex.ins.Ins
    public void updateTargetAddress() {
        super.updateTargetAddress();
        getSwitch().setTargetIns(this);
        getSwitch().updateTargetAddress();
        Iterator<SwitchEntry> it = iterator();
        while (it.hasNext()) {
            it.next().updateTargetAddress();
        }
    }

    @Override // com.reandroid.dex.ins.Ins
    void linkTargetIns() {
        super.linkTargetIns();
        Iterator<SwitchEntry> it = iterator();
        while (it.hasNext()) {
            it.next().getTargetIns();
        }
    }

    @Override // com.reandroid.dex.ins.Ins
    void unLinkTargetIns() {
        super.unLinkTargetIns();
        Iterator<SwitchEntry> it = iterator();
        while (it.hasNext()) {
            it.next().setTargetIns(null);
        }
    }

    @Override // com.reandroid.dex.ins.PayloadData
    public Iterator<IntegerReference> getReferences() {
        return (Iterator) ObjectsUtil.cast(iterator());
    }

    public InsSwitch getSwitch() {
        InsSwitch insSwitchFindOnExtraLines = this.insSwitch;
        if (insSwitchFindOnExtraLines == null) {
            insSwitchFindOnExtraLines = findOnExtraLines();
            if (insSwitchFindOnExtraLines == null) {
                insSwitchFindOnExtraLines = findByAddress();
            }
            this.insSwitch = insSwitchFindOnExtraLines;
            if (insSwitchFindOnExtraLines != null) {
                insSwitchFindOnExtraLines.setPayload(this);
            }
        }
        return insSwitchFindOnExtraLines;
    }

    public void setSwitch(InsSwitch insSwitch) {
        if (insSwitch == null) {
            this.insSwitch = null;
        } else {
            if (getSwitchOpcode() != insSwitch.getOpcode()) {
                throw new ClassCastException("Incompatible switch opcode: '" + getSwitchOpcode() + "' vs '" + insSwitch.getOpcode() + FormatHelper.QUOTE);
            }
            this.insSwitch = insSwitch;
            addExtraLine(insSwitch);
            insSwitch.setPayload(this);
        }
    }

    private InsSwitch findOnExtraLines() {
        Iterator<ExtraLine> extraLines = getExtraLines();
        while (extraLines.hasNext()) {
            ExtraLine next = extraLines.next();
            if (next instanceof InsSwitch) {
                InsSwitch insSwitch = (InsSwitch) next;
                if (insSwitch.getOpcode() == getSwitchOpcode()) {
                    return insSwitch;
                }
            }
        }
        return null;
    }

    private InsSwitch findByAddress() {
        InstructionList instructionList = getInstructionList();
        if (instructionList == null) {
            return null;
        }
        Iterator it = instructionList.iterator(getSwitchOpcode());
        int address = getAddress();
        while (it.hasNext()) {
            InsSwitch insSwitch = (InsSwitch) it.next();
            if (insSwitch.getTargetAddress() == address) {
                return insSwitch;
            }
        }
        return null;
    }

    public boolean replaceByIfEq() {
        InstructionList instructionList = getInstructionList();
        RegistersTable registersTable = instructionList.getRegistersTable();
        int localRegistersCount = registersTable.getLocalRegistersCount();
        InsSwitch insSwitch = getSwitch();
        if (!registersTable.ensureLocalRegistersCount(localRegistersCount + 1)) {
            List<Register> localFreeRegisters = instructionList.getLocalFreeRegisters(insSwitch.getIndex());
            if (localFreeRegisters.isEmpty()) {
                return false;
            }
            localRegistersCount = localFreeRegisters.get(0).getValue();
        }
        if (localRegistersCount > 15) {
            return false;
        }
        replaceByIfEq(localRegistersCount);
        return true;
    }

    public void replaceByIfEq(int i) {
        InsSwitch insSwitch = getSwitch();
        InsBlockList insBlockList = getInsBlockList();
        Object objLink = insBlockList.link(new Object());
        linkTargetIns();
        Iterator<SwitchEntry> it = iterator();
        while (it.hasNext()) {
            it.next().addEquivalentIfEq(i);
        }
        InstructionList instructionList = getInstructionList();
        instructionList.remove(insSwitch);
        instructionList.remove(this);
        insBlockList.unlinkLocked(objLink);
    }

    public interface SwitchEntry extends IntegerReference, Label, SmaliFormat {
        InsSwitchPayload getPayload();

        @Override // com.reandroid.dex.ins.ExtraLine
        default boolean isRemoved() {
            return getPayload().isRemoved();
        }

        default void updateTargetAddress() {
            setTargetAddress(getTargetIns().getAddress());
        }

        default void addEquivalentIfEq(int i) {
            InsSwitch insSwitch = getPayload().getSwitch();
            InstructionList instructionList = insSwitch.getInstructionList();
            Ins targetIns = getTargetIns();
            Ins ins = (Ins) instructionList.createConstIntegerAt(insSwitch.getIndex() + 1, i, get());
            Ins22t ins22t = (Ins22t) Opcode.IF_EQ.newInstance();
            ins22t.setRegister(0, insSwitch.getRegister());
            ins22t.setRegister(1, i);
            ins22t.setTargetIns(targetIns);
            instructionList.add(ins.getIndex() + 1, ins22t);
            ins22t.setTargetIns(targetIns);
        }

        default Ins findTargetIns() {
            InsBlockList insBlockList = getPayload().getInsBlockList();
            if (insBlockList != null) {
                return insBlockList.getAtAddress(getTargetAddress());
            }
            return null;
        }
    }
}
