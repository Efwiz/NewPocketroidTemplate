package com.reandroid.dex.ins;

import com.reandroid.arsc.item.IntegerItem;
import com.reandroid.arsc.item.ShortItem;
import com.reandroid.dex.smali.SmaliDirective;
import com.reandroid.dex.smali.SmaliWriter;
import com.reandroid.dex.smali.model.SmaliInstruction;
import com.reandroid.dex.smali.model.SmaliPackedSwitchEntry;
import com.reandroid.dex.smali.model.SmaliPayloadPackedSwitch;
import com.reandroid.dex.smali.model.SmaliSet;
import com.reandroid.utils.HexUtil;
import com.reandroid.utils.ObjectsUtil;
import java.io.IOException;
import java.util.Iterator;

/* JADX INFO: loaded from: classes11.dex */
public class InsPackedSwitchData extends InsSwitchPayload {
    private final ShortItem elementCount;
    private final PackedSwitchDataList elements;
    private final IntegerItem firstKey;
    private InsSparseSwitchData mReplacement;

    public InsPackedSwitchData() {
        super(3, Opcode.PACKED_SWITCH_PAYLOAD);
        ShortItem shortItem = new ShortItem();
        this.elementCount = shortItem;
        IntegerItem integerItem = new IntegerItem();
        this.firstKey = integerItem;
        PackedSwitchDataList packedSwitchDataList = new PackedSwitchDataList(this, shortItem);
        this.elements = packedSwitchDataList;
        addChild(1, shortItem);
        addChild(2, integerItem);
        addChild(3, packedSwitchDataList);
    }

    public int getFirstKey() {
        return this.firstKey.get();
    }

    public void setFirstKey(int i) {
        this.firstKey.set(i);
    }

    @Override // java.lang.Iterable
    public Iterator<InsSwitchPayload.SwitchEntry> iterator() {
        return (Iterator) ObjectsUtil.cast(this.elements.getLabels());
    }

    void onDataChange(int i, int i2) {
        replaceBySparse().get(i).set(i2);
    }

    public InsSparseSwitchData replaceBySparse() {
        InsSparseSwitchData insSparseSwitchData = this.mReplacement;
        if (insSparseSwitchData != null) {
            return insSparseSwitchData;
        }
        InsBlockList insBlockList = getInsBlockList();
        Object objLinkLocked = insBlockList.linkLocked();
        InsSparseSwitch sparseSwitchReplacement = getSwitch().getSparseSwitchReplacement();
        InsSparseSwitchData insSparseSwitchData2 = (InsSparseSwitchData) Opcode.SPARSE_SWITCH_PAYLOAD.newInstance();
        this.mReplacement = insSparseSwitchData2;
        insSparseSwitchData2.setSwitch(sparseSwitchReplacement);
        insSparseSwitchData2.fromPackedSwitchData(this.elements);
        replace(insSparseSwitchData2);
        insBlockList.unlinkLocked(objLinkLocked);
        return insSparseSwitchData2;
    }

    @Override // com.reandroid.dex.ins.InsSwitchPayload
    public Opcode<InsPackedSwitch> getSwitchOpcode() {
        return Opcode.PACKED_SWITCH;
    }

    @Override // com.reandroid.dex.ins.InsSwitchPayload
    public InsPackedSwitch getSwitch() {
        return (InsPackedSwitch) super.getSwitch();
    }

    @Override // com.reandroid.dex.smali.SmaliRegion
    public SmaliDirective getSmaliDirective() {
        return SmaliDirective.PACKED_SWITCH;
    }

    @Override // com.reandroid.dex.ins.LabelsSet
    public Iterator<PackedSwitchDataList.PackedSwitchEntry> getLabels() {
        return this.elements.getLabels();
    }

    @Override // com.reandroid.dex.ins.Ins
    public void merge(Ins ins) {
        InsPackedSwitchData insPackedSwitchData = (InsPackedSwitchData) ins;
        setFirstKey(insPackedSwitchData.getFirstKey());
        this.elements.merge(insPackedSwitchData.elements);
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.reandroid.dex.ins.PayloadData, com.reandroid.dex.ins.Ins
    public void fromSmali(SmaliInstruction smaliInstruction) throws IOException {
        validateOpcode(smaliInstruction);
        SmaliPayloadPackedSwitch smaliPayloadPackedSwitch = (SmaliPayloadPackedSwitch) smaliInstruction;
        setFirstKey(smaliPayloadPackedSwitch.getFirstKey());
        SmaliSet<T> entries = smaliPayloadPackedSwitch.getEntries();
        int size = entries.size();
        PackedSwitchDataList packedSwitchDataList = this.elements;
        packedSwitchDataList.setSize(size);
        for (int i = 0; i < size; i++) {
            ((PackedSwitchDataList.PackedSwitchEntry) packedSwitchDataList.get(i)).setAddress(((SmaliPackedSwitchEntry) entries.get(i)).getRelativeOffset().intValue());
        }
    }

    @Override // com.reandroid.dex.ins.Ins
    public void appendCode(SmaliWriter smaliWriter) throws IOException {
        getSmaliDirective().append(smaliWriter);
        smaliWriter.append((CharSequence) HexUtil.toHex(this.firstKey.get(), 1));
        smaliWriter.indentPlus();
        this.elements.append(smaliWriter);
        smaliWriter.indentMinus();
        getSmaliDirective().appendEnd(smaliWriter);
    }

    @Override // com.reandroid.dex.ins.Ins
    public String toString() {
        return "InsPackedSwitchData{elementCount=" + this.elementCount + ", firstKey=" + this.firstKey + ", elements=" + this.elements + '}';
    }
}
