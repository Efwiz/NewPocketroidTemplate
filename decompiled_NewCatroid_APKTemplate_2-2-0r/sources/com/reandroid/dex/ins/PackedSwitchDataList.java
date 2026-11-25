package com.reandroid.dex.ins;

import com.reandroid.arsc.base.Creator;
import com.reandroid.arsc.item.IntegerItem;
import com.reandroid.arsc.item.IntegerReference;
import com.reandroid.dex.base.CountedList;
import com.reandroid.dex.smali.SmaliFormat;
import com.reandroid.dex.smali.SmaliWriter;
import com.reandroid.utils.HexUtil;
import java.io.IOException;
import java.util.Iterator;
import java.util.Objects;

/* JADX INFO: loaded from: classes11.dex */
public class PackedSwitchDataList extends CountedList<PackedSwitchEntry> implements SmaliFormat, LabelsSet {
    private static final Creator<PackedSwitchEntry> CREATOR = new Creator<PackedSwitchEntry>() { // from class: com.reandroid.dex.ins.PackedSwitchDataList.1
        @Override // com.reandroid.arsc.base.Creator, com.reandroid.arsc.base.BlockArrayCreator
        public PackedSwitchEntry[] newArrayInstance(int i) {
            return new PackedSwitchEntry[i];
        }

        @Override // com.reandroid.arsc.base.Creator, com.reandroid.arsc.base.BlockCreator
        public PackedSwitchEntry newInstance() {
            return new PackedSwitchEntry();
        }
    };
    private final InsPackedSwitchData switchData;

    public PackedSwitchDataList(InsPackedSwitchData insPackedSwitchData, IntegerReference integerReference) {
        super(integerReference, CREATOR);
        this.switchData = insPackedSwitchData;
    }

    public int getFirstKey() {
        return this.switchData.getFirstKey();
    }

    public int getBaseAddress() {
        InsPackedSwitch insPackedSwitch = this.switchData.getSwitch();
        if (insPackedSwitch == null) {
            return 0;
        }
        return insPackedSwitch.getAddress();
    }

    public InsPackedSwitchData getSwitchData() {
        return this.switchData;
    }

    void onDataChange(int i, int i2) {
        this.switchData.onDataChange(i, i2);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public void merge(PackedSwitchDataList packedSwitchDataList) {
        int size = packedSwitchDataList.size();
        setSize(size);
        for (int i = 0; i < size; i++) {
            ((PackedSwitchEntry) get(i)).merge((PackedSwitchEntry) packedSwitchDataList.get(i));
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.reandroid.dex.smali.SmaliFormat
    public void append(SmaliWriter smaliWriter) throws IOException {
        int size = size();
        for (int i = 0; i < size; i++) {
            ((PackedSwitchEntry) get(i)).append(smaliWriter);
        }
    }

    @Override // com.reandroid.dex.ins.LabelsSet
    public Iterator<PackedSwitchEntry> getLabels() {
        return iterator();
    }

    public static class PackedSwitchEntry extends IntegerItem implements InsSwitchPayload.SwitchEntry {
        private Ins targetIns;

        @Override // com.reandroid.dex.ins.ExtraLine
        public int getSortOrder() {
            return 5;
        }

        @Override // com.reandroid.dex.ins.ExtraLine
        public Ins getTargetIns() {
            Ins ins = this.targetIns;
            if (ins != null) {
                return ins;
            }
            setTargetIns(findTargetIns());
            return this.targetIns;
        }

        @Override // com.reandroid.dex.ins.ExtraLine
        public void setTargetIns(Ins ins) {
            if (ins != this.targetIns) {
                this.targetIns = ins;
                if (ins != null) {
                    ins.addExtraLine(this);
                }
            }
        }

        @Override // com.reandroid.dex.ins.InsSwitchPayload.SwitchEntry
        public InsPackedSwitchData getPayload() {
            return getParentDataList().getSwitchData();
        }

        @Override // com.reandroid.arsc.item.IntegerItem, com.reandroid.arsc.item.IntegerReference
        public int get() {
            return getParentDataList().getFirstKey() + getIndex();
        }

        @Override // com.reandroid.arsc.item.IntegerItem, com.reandroid.arsc.item.IntegerReference
        public void set(int i) {
            if (i != get()) {
                getParentDataList().onDataChange(getIndex(), i);
            }
        }

        @Override // com.reandroid.dex.smali.SmaliFormat
        public void append(SmaliWriter smaliWriter) throws IOException {
            smaliWriter.newLine();
            smaliWriter.appendLabelName(getLabelName());
            int i = get();
            smaliWriter.appendComment(HexUtil.toHex(i, 1));
            smaliWriter.appendResourceIdComment(i);
        }

        @Override // com.reandroid.dex.ins.Label, com.reandroid.dex.ins.ExtraLine
        public void appendExtra(SmaliWriter smaliWriter) throws IOException {
            smaliWriter.appendLabelName(getLabelName());
            smaliWriter.appendComment(HexUtil.toHex(get(), 1));
        }

        @Override // com.reandroid.dex.ins.Label
        public int getAddress() {
            return super.get();
        }

        public void setAddress(int i) {
            super.set(i);
        }

        @Override // com.reandroid.dex.ins.ExtraLine
        public int getTargetAddress() {
            return getParentDataList().getBaseAddress() + getAddress();
        }

        @Override // com.reandroid.dex.ins.ExtraLine
        public void setTargetAddress(int i) {
            setAddress(i - getParentDataList().getBaseAddress());
        }

        @Override // com.reandroid.dex.ins.Label
        public String getLabelName() {
            return HexUtil.toHex(":pswitch_", getTargetAddress(), 1);
        }

        private PackedSwitchDataList getParentDataList() {
            return (PackedSwitchDataList) getParent();
        }

        public void merge(PackedSwitchEntry packedSwitchEntry) {
            setAddress(packedSwitchEntry.getAddress());
        }

        public int hashCode() {
            return Objects.hash(Integer.valueOf(getIndex()));
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || getClass() != obj.getClass()) {
                return false;
            }
            PackedSwitchEntry packedSwitchEntry = (PackedSwitchEntry) obj;
            return getIndex() == packedSwitchEntry.getIndex() && getParent() == packedSwitchEntry.getParent();
        }

        @Override // com.reandroid.arsc.item.IntegerItem
        public String toString() {
            return getLabelName();
        }
    }
}
