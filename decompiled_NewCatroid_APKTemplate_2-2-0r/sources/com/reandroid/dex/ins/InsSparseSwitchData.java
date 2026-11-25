package com.reandroid.dex.ins;

import com.reandroid.arsc.base.Creator;
import com.reandroid.arsc.item.IntegerItem;
import com.reandroid.arsc.item.IntegerReference;
import com.reandroid.arsc.item.ShortItem;
import com.reandroid.common.ArraySupplier;
import com.reandroid.dex.base.CountedList;
import com.reandroid.dex.smali.SmaliDirective;
import com.reandroid.dex.smali.SmaliWriter;
import com.reandroid.dex.smali.model.SmaliInstruction;
import com.reandroid.dex.smali.model.SmaliPayloadSparseSwitch;
import com.reandroid.dex.smali.model.SmaliSet;
import com.reandroid.dex.smali.model.SmaliSparseSwitchEntry;
import com.reandroid.utils.CompareUtil;
import com.reandroid.utils.HexUtil;
import com.reandroid.utils.ObjectsUtil;
import com.reandroid.utils.collection.ArraySupplierIterator;
import java.io.IOException;
import java.util.Comparator;
import java.util.Iterator;

/* JADX INFO: loaded from: classes11.dex */
public class InsSparseSwitchData extends InsSwitchPayload implements ArraySupplier<SparseSwitchEntry> {
    private final ShortItem elementCount;
    final CountedList<IntegerItem> elements;
    final CountedList<EntryKey> keys;
    boolean mSortRequired;

    public InsSparseSwitchData() {
        super(3, Opcode.SPARSE_SWITCH_PAYLOAD);
        ShortItem shortItem = new ShortItem();
        this.elementCount = shortItem;
        Creator<IntegerItem> creator = new Creator<IntegerItem>() { // from class: com.reandroid.dex.ins.InsSparseSwitchData.1
            @Override // com.reandroid.arsc.base.Creator, com.reandroid.arsc.base.BlockArrayCreator
            public IntegerItem[] newArrayInstance(int i) {
                return new IntegerItem[i];
            }

            @Override // com.reandroid.arsc.base.Creator, com.reandroid.arsc.base.BlockCreator
            public IntegerItem newInstance() {
                return new IntegerItem();
            }
        };
        Creator<EntryKey> creator2 = new Creator<EntryKey>() { // from class: com.reandroid.dex.ins.InsSparseSwitchData.2
            @Override // com.reandroid.arsc.base.Creator, com.reandroid.arsc.base.BlockArrayCreator
            public EntryKey[] newArrayInstance(int i) {
                return new EntryKey[i];
            }

            @Override // com.reandroid.arsc.base.Creator, com.reandroid.arsc.base.BlockCreator
            public EntryKey newInstance() {
                return new EntryKey();
            }
        };
        CountedList<IntegerItem> countedList = new CountedList<>(shortItem, creator);
        this.elements = countedList;
        CountedList<EntryKey> countedList2 = new CountedList<>(shortItem, creator2);
        this.keys = countedList2;
        addChild(1, shortItem);
        addChild(2, countedList);
        addChild(3, countedList2);
    }

    @Override // java.lang.Iterable
    public Iterator<InsSwitchPayload.SwitchEntry> iterator() {
        return (Iterator) ObjectsUtil.cast(getLabels());
    }

    public SparseSwitchEntry newEntry() {
        int count = getCount();
        setCount(count + 1);
        return get(count);
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.reandroid.common.ArraySupplier
    public SparseSwitchEntry get(int i) {
        if (i < 0 || i >= getCount()) {
            return null;
        }
        return new SparseSwitchEntry(this, this.elements.get(i), this.keys.get(i));
    }

    @Override // com.reandroid.common.CountSupplier
    public int getCount() {
        return this.elements.size();
    }

    public void setCount(int i) {
        boolean zIsLinked;
        int count = getCount();
        InsBlockList insBlockList = getInsBlockList();
        if (i == count || insBlockList == null) {
            zIsLinked = false;
        } else {
            zIsLinked = insBlockList.isLinked();
            insBlockList.link();
        }
        this.elements.setSize(i);
        this.keys.setSize(i);
        this.elementCount.set(i);
        if (insBlockList == null || zIsLinked) {
            return;
        }
        insBlockList.unlink();
    }

    public void sort() {
        if (this.mSortRequired) {
            this.mSortRequired = false;
            Comparator comparator = new Comparator() { // from class: com.reandroid.dex.ins.InsSparseSwitchData$$ExternalSyntheticLambda0
                @Override // java.util.Comparator
                public final int compare(Object obj, Object obj2) {
                    return CompareUtil.compare(((IntegerItem) obj).get(), ((IntegerItem) obj2).get());
                }
            };
            if (this.elements.needsSort(comparator)) {
                this.elements.sort(comparator, this.keys);
            }
        }
    }

    @Override // com.reandroid.dex.ins.LabelsSet
    public Iterator<SparseSwitchEntry> getLabels() {
        return new ArraySupplierIterator(this);
    }

    public int getBaseAddress() {
        InsSparseSwitch insSparseSwitch = getSwitch();
        if (insSparseSwitch == null) {
            return 0;
        }
        return insSparseSwitch.getAddress();
    }

    @Override // com.reandroid.dex.ins.InsSwitchPayload
    public Opcode<InsSparseSwitch> getSwitchOpcode() {
        return Opcode.SPARSE_SWITCH;
    }

    @Override // com.reandroid.dex.ins.InsSwitchPayload
    public InsSparseSwitch getSwitch() {
        return (InsSparseSwitch) super.getSwitch();
    }

    @Override // com.reandroid.dex.ins.PayloadData, com.reandroid.arsc.base.BlockContainer
    protected void onPreRefresh() {
        sort();
        super.onPreRefresh();
    }

    /* JADX WARN: Multi-variable type inference failed */
    void fromPackedSwitchData(PackedSwitchDataList packedSwitchDataList) {
        int size = packedSwitchDataList.size();
        setCount(size);
        for (int i = 0; i < size; i++) {
            get(i).fromPackedSwitch((PackedSwitchDataList.PackedSwitchEntry) packedSwitchDataList.get(i));
        }
    }

    @Override // com.reandroid.dex.ins.Ins
    public void merge(Ins ins) {
        InsSparseSwitchData insSparseSwitchData = (InsSparseSwitchData) ins;
        int count = insSparseSwitchData.getCount();
        setCount(count);
        for (int i = 0; i < count; i++) {
            get(i).merge(insSparseSwitchData.get(i));
        }
    }

    @Override // com.reandroid.dex.ins.PayloadData, com.reandroid.dex.ins.Ins
    public void fromSmali(SmaliInstruction smaliInstruction) throws IOException {
        validateOpcode(smaliInstruction);
        SmaliSet<T> entries = ((SmaliPayloadSparseSwitch) smaliInstruction).getEntries();
        int size = entries.size();
        setCount(size);
        for (int i = 0; i < size; i++) {
            get(i).fromSmali((SmaliSparseSwitchEntry) entries.get(i));
        }
        this.mSortRequired = true;
    }

    @Override // com.reandroid.dex.ins.Ins
    public void appendCode(SmaliWriter smaliWriter) throws IOException {
        getSmaliDirective().append(smaliWriter);
        int count = getCount();
        smaliWriter.indentPlus();
        for (int i = 0; i < count; i++) {
            get(i).append(smaliWriter);
        }
        smaliWriter.indentMinus();
        smaliWriter.newLine();
        getSmaliDirective().appendEnd(smaliWriter);
    }

    @Override // com.reandroid.dex.smali.SmaliRegion
    public SmaliDirective getSmaliDirective() {
        return SmaliDirective.SPARSE_SWITCH;
    }

    static class EntryKey extends IntegerItem {
        private Ins targetIns;

        EntryKey() {
        }

        public Ins getTargetIns() {
            return this.targetIns;
        }

        public void setTargetIns(Ins ins) {
            this.targetIns = ins;
        }
    }

    public static class SparseSwitchEntry implements InsSwitchPayload.SwitchEntry {
        private final IntegerReference element;
        private final EntryKey key;
        private final InsSparseSwitchData payload;

        @Override // com.reandroid.dex.ins.ExtraLine
        public int getSortOrder() {
            return 5;
        }

        public SparseSwitchEntry(InsSparseSwitchData insSparseSwitchData, IntegerReference integerReference, EntryKey entryKey) {
            this.payload = insSparseSwitchData;
            this.element = integerReference;
            this.key = entryKey;
        }

        @Override // com.reandroid.dex.ins.InsSwitchPayload.SwitchEntry
        public InsSparseSwitchData getPayload() {
            return this.payload;
        }

        @Override // com.reandroid.arsc.item.IntegerReference
        public int get() {
            return this.element.get();
        }

        @Override // com.reandroid.arsc.item.IntegerReference
        public void set(int i) {
            if (i != this.element.get()) {
                this.element.set(i);
                this.payload.mSortRequired = true;
            }
        }

        @Override // com.reandroid.dex.ins.ExtraLine
        public Ins getTargetIns() {
            Ins targetIns = this.key.getTargetIns();
            if (targetIns != null) {
                return targetIns;
            }
            setTargetIns(findTargetIns());
            return this.key.getTargetIns();
        }

        @Override // com.reandroid.dex.ins.ExtraLine
        public void setTargetIns(Ins ins) {
            if (ins != this.key.getTargetIns()) {
                this.key.setTargetIns(ins);
                if (ins != null) {
                    ins.addExtraLine(this);
                }
            }
        }

        public int getKey() {
            return this.key.get();
        }

        public void setKey(int i) {
            this.key.set(i);
        }

        @Override // com.reandroid.dex.ins.Label
        public int getAddress() {
            return this.payload.getAddress();
        }

        @Override // com.reandroid.dex.ins.ExtraLine
        public int getTargetAddress() {
            return getKey() + this.payload.getBaseAddress();
        }

        @Override // com.reandroid.dex.ins.ExtraLine
        public void setTargetAddress(int i) {
            setKey(i - this.payload.getBaseAddress());
        }

        @Override // com.reandroid.dex.ins.Label
        public String getLabelName() {
            return HexUtil.toHex(":sswitch_", getTargetAddress(), 1);
        }

        @Override // com.reandroid.dex.smali.SmaliFormat
        public void append(SmaliWriter smaliWriter) throws IOException {
            smaliWriter.newLine();
            int i = get();
            smaliWriter.appendHex(i);
            smaliWriter.append(" -> ");
            smaliWriter.appendLabelName(getLabelName());
            smaliWriter.appendResourceIdComment(i);
        }

        @Override // com.reandroid.dex.ins.Label, com.reandroid.dex.ins.ExtraLine
        public void appendExtra(SmaliWriter smaliWriter) throws IOException {
            smaliWriter.appendLabelName(getLabelName());
            smaliWriter.appendComment(HexUtil.toSignedHex(get()));
        }

        public void fromPackedSwitch(PackedSwitchDataList.PackedSwitchEntry packedSwitchEntry) {
            set(packedSwitchEntry.get());
            Ins targetIns = packedSwitchEntry.getTargetIns();
            setTargetAddress(targetIns.getAddress());
            setTargetIns(targetIns);
            targetIns.addExtraLine(this);
        }

        public void merge(SparseSwitchEntry sparseSwitchEntry) {
            set(sparseSwitchEntry.get());
            setKey(sparseSwitchEntry.getKey());
        }

        public void fromSmali(SmaliSparseSwitchEntry smaliSparseSwitchEntry) throws IOException {
            set(smaliSparseSwitchEntry.getValue());
            setKey(smaliSparseSwitchEntry.getRelativeOffset().intValue());
        }

        public int hashCode() {
            return ObjectsUtil.hash(this.payload, this.element);
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || getClass() != obj.getClass()) {
                return false;
            }
            SparseSwitchEntry sparseSwitchEntry = (SparseSwitchEntry) obj;
            return this.element == sparseSwitchEntry.element && this.payload == sparseSwitchEntry.payload;
        }

        public String toString() {
            return HexUtil.toHex8(get()) + " -> " + getKey();
        }
    }
}
