package com.reandroid.dex.data;

import com.reandroid.arsc.base.Block;
import com.reandroid.arsc.base.BlockRefresh;
import com.reandroid.arsc.io.BlockReader;
import com.reandroid.arsc.item.IndirectInteger;
import com.reandroid.arsc.item.IntegerReference;
import com.reandroid.dex.base.DexPositionAlign;
import com.reandroid.dex.base.IndirectShort;
import com.reandroid.dex.base.PositionAlignedItem;
import com.reandroid.dex.base.UsageMarker;
import com.reandroid.dex.common.RegistersTable;
import com.reandroid.dex.common.SectionItem;
import com.reandroid.dex.debug.DebugElement;
import com.reandroid.dex.id.IdItem;
import com.reandroid.dex.ins.ExtraLine;
import com.reandroid.dex.ins.Label;
import com.reandroid.dex.ins.TryBlock;
import com.reandroid.dex.key.DataKey;
import com.reandroid.dex.key.Key;
import com.reandroid.dex.key.ModifiableKeyItem;
import com.reandroid.dex.reference.DataItemIndirectReference;
import com.reandroid.dex.sections.SectionType;
import com.reandroid.dex.smali.SmaliDirective;
import com.reandroid.dex.smali.SmaliFormat;
import com.reandroid.dex.smali.SmaliWriter;
import com.reandroid.dex.smali.model.SmaliCodeTryItem;
import com.reandroid.dex.smali.model.SmaliMethod;
import com.reandroid.utils.ObjectsUtil;
import com.reandroid.utils.collection.CombiningIterator;
import com.reandroid.utils.collection.EmptyIterator;
import java.io.IOException;
import java.util.Iterator;

/* JADX INFO: loaded from: classes11.dex */
public class CodeItem extends DataItem implements RegistersTable, PositionAlignedItem, ModifiableKeyItem, SmaliFormat {
    private final DataKey<CodeItem> codeItemKey;
    private final Header header;
    private final InstructionList instructionList;
    private MethodDef methodDef;
    private TryBlock tryBlock;

    public CodeItem() {
        super(3);
        Header header = new Header(this);
        this.header = header;
        InstructionList instructionList = new InstructionList(this);
        this.instructionList = instructionList;
        this.tryBlock = null;
        this.codeItemKey = new DataKey<>(this);
        addChild(0, header);
        addChild(1, instructionList);
    }

    @Override // com.reandroid.dex.data.DataItem, com.reandroid.dex.common.SectionItem, com.reandroid.dex.key.KeyItem
    public DataKey<CodeItem> getKey() {
        return this.codeItemKey;
    }

    @Override // com.reandroid.dex.key.ModifiableKeyItem
    public void setKey(Key key) {
        merge((CodeItem) ((DataKey) key).getItem());
    }

    @Override // com.reandroid.dex.common.SectionItem
    public SectionType<CodeItem> getSectionType() {
        return SectionType.CODE;
    }

    @Override // com.reandroid.dex.common.RegistersTable
    public int getRegistersCount() {
        return this.header.registersCount.get();
    }

    @Override // com.reandroid.dex.common.RegistersTable
    public void setRegistersCount(int i) {
        this.header.registersCount.set(i);
    }

    @Override // com.reandroid.dex.common.RegistersTable
    public int getParameterRegistersCount() {
        return this.header.parameterRegisters.get();
    }

    @Override // com.reandroid.dex.common.RegistersTable
    public void setParameterRegistersCount(int i) {
        this.header.parameterRegisters.set(i);
    }

    @Override // com.reandroid.dex.common.RegistersTable
    public boolean ensureLocalRegistersCount(int i) {
        if (i == 0 || i <= getLocalRegistersCount()) {
            return true;
        }
        int parameterRegistersCount = getParameterRegistersCount();
        int localRegistersCount = i - getLocalRegistersCount();
        InstructionList instructionList = getInstructionList();
        if (!instructionList.canAddLocalRegisters(localRegistersCount)) {
            return false;
        }
        if (localRegistersCount > 0) {
            instructionList.addLocalRegisters(localRegistersCount);
        }
        setRegistersCount(i + parameterRegistersCount);
        return true;
    }

    public Iterator<DebugElement> getDebugLabels() {
        DebugInfo debugInfo = getDebugInfo();
        if (debugInfo != null) {
            return debugInfo.getExtraLines();
        }
        return EmptyIterator.of();
    }

    public DebugInfo getDebugInfo() {
        return (DebugInfo) this.header.debugInfoOffset.getItem();
    }

    public DebugInfo getOrCreateDebugInfo() {
        return (DebugInfo) this.header.debugInfoOffset.getOrCreateUniqueItem(this);
    }

    public void removeDebugInfo() {
        if (getDebugInfo() == null) {
            return;
        }
        setDebugInfo(null);
    }

    public void setDebugInfo(DebugInfo debugInfo) {
        this.header.debugInfoOffset.setItem(debugInfo);
    }

    public InstructionList getInstructionList() {
        return this.instructionList;
    }

    public IntegerReference getTryCountReference() {
        return this.header.tryBlockCount;
    }

    public Iterable<ExtraLine> getExtraLines() {
        return new Iterable() { // from class: com.reandroid.dex.data.CodeItem$$ExternalSyntheticLambda0
            @Override // java.lang.Iterable
            public final Iterator iterator() {
                return this.f$0.m2758lambda$getExtraLines$0$comreandroiddexdataCodeItem();
            }
        };
    }

    /* JADX INFO: renamed from: lambda$getExtraLines$0$com-reandroid-dex-data-CodeItem, reason: not valid java name */
    /* synthetic */ Iterator m2758lambda$getExtraLines$0$comreandroiddexdataCodeItem() {
        return CombiningIterator.two(getTryBlockLabels(), getDebugLabels());
    }

    public Iterator<Label> getTryBlockLabels() {
        TryBlock tryBlock = getTryBlock();
        if (tryBlock == null || tryBlock.isNull()) {
            return EmptyIterator.of();
        }
        return tryBlock.getLabels();
    }

    public TryBlock getTryBlock() {
        return this.tryBlock;
    }

    public TryBlock getOrCreateTryBlock() {
        initTryBlock();
        return this.tryBlock;
    }

    public void removeTryBlock() {
        TryBlock tryBlock = this.tryBlock;
        if (tryBlock == null) {
            return;
        }
        this.tryBlock = null;
        this.header.tryBlockCount.set(0);
        tryBlock.setParent(null);
    }

    public MethodDef getMethodDef() {
        return this.methodDef;
    }

    public void setMethodDef(MethodDef methodDef) {
        this.methodDef = methodDef;
    }

    IntegerReference getInstructionCodeUnitsReference() {
        return this.header.instructionCodeUnits;
    }

    IntegerReference getInstructionOutsReference() {
        return this.header.outs;
    }

    void initTryBlock() {
        if (this.tryBlock == null) {
            TryBlock tryBlock = new TryBlock(this);
            this.tryBlock = tryBlock;
            addChild(2, tryBlock);
        }
    }

    @Override // com.reandroid.dex.base.PositionAlignedItem
    public DexPositionAlign getPositionAlign() {
        TryBlock tryBlock = this.tryBlock;
        if (tryBlock != null) {
            return tryBlock.getPositionAlign();
        }
        InstructionList instructionList = this.instructionList;
        if (instructionList != null) {
            return instructionList.getBlockAlign();
        }
        return new DexPositionAlign();
    }

    @Override // com.reandroid.dex.data.DataItem, com.reandroid.dex.common.SectionItemContainer, com.reandroid.dex.base.PositionedItem
    public void removeLastAlign() {
        TryBlock tryBlock = this.tryBlock;
        if (tryBlock != null) {
            tryBlock.getPositionAlign().setSize(0);
            return;
        }
        InstructionList instructionList = this.instructionList;
        if (instructionList != null) {
            instructionList.getBlockAlign().setSize(0);
        }
    }

    public void replaceKeys(Key key, Key key2) {
        getInstructionList().replaceKeys(key, key2);
    }

    @Override // com.reandroid.dex.common.EditableItem
    public void edit() {
        editInternal(this);
    }

    @Override // com.reandroid.dex.common.SectionItem, com.reandroid.dex.common.EditableItem
    public void editInternal(Block block) {
        this.header.editInternal(block);
    }

    @Override // com.reandroid.dex.data.DataItem, com.reandroid.dex.common.IdUsageIterator
    public Iterator<IdItem> usedIds() {
        Iterator<IdItem> itUsedIds;
        DebugInfo debugInfo = getDebugInfo();
        if (debugInfo == null) {
            itUsedIds = EmptyIterator.of();
        } else {
            itUsedIds = debugInfo.usedIds();
        }
        return CombiningIterator.two(itUsedIds, getInstructionList().usedIds());
    }

    public void merge(CodeItem codeItem) {
        if (codeItem == this) {
            return;
        }
        this.header.merge(codeItem.header);
        getInstructionList().merge(codeItem.getInstructionList());
        TryBlock tryBlock = codeItem.getTryBlock();
        if (tryBlock != null) {
            getOrCreateTryBlock().merge(tryBlock);
        }
    }

    public void fromSmali(SmaliMethod smaliMethod) throws IOException {
        setRegistersCount(smaliMethod.getRegistersCount());
        setParameterRegistersCount(smaliMethod.getParameterRegistersCount());
        getInstructionList().fromSmali(smaliMethod.getCodeSet());
        Iterator<SmaliCodeTryItem> tryItems = smaliMethod.getTryItems();
        TryBlock orCreateTryBlock = tryItems.hasNext() ? getOrCreateTryBlock() : null;
        while (tryItems.hasNext()) {
            orCreateTryBlock.fromSmali(tryItems.next());
        }
        if (smaliMethod.hasDebugElements()) {
            getOrCreateDebugInfo().getDebugSequence().fromSmali(smaliMethod.getCodeSet());
        }
    }

    @Override // com.reandroid.dex.smali.SmaliFormat
    public void append(SmaliWriter smaliWriter) throws IOException {
        smaliWriter.setCurrentRegistersTable(this);
        MethodDef methodDef = getMethodDef();
        smaliWriter.newLine();
        SmaliDirective.LOCALS.append(smaliWriter);
        smaliWriter.appendInteger(getLocalRegistersCount());
        smaliWriter.appendAllWithDoubleNewLine(methodDef.getParameters(true));
        smaliWriter.appendAllWithDoubleNewLine(methodDef.getAnnotations(true));
        getInstructionList().append(smaliWriter);
        smaliWriter.setCurrentRegistersTable(null);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        CodeItem codeItem = (CodeItem) obj;
        return this.header.equals(codeItem.header) && this.instructionList.equals(codeItem.instructionList) && ObjectsUtil.equals(this.tryBlock, codeItem.tryBlock);
    }

    public int hashCode() {
        int iHashCode = ((this.header.hashCode() * 31) + this.instructionList.hashCode()) * 31;
        TryBlock tryBlock = this.tryBlock;
        return tryBlock != null ? iHashCode + tryBlock.hashCode() : iHashCode;
    }

    public String toString() {
        if (isNull()) {
            return "NULL";
        }
        return this.header.toString() + "\n instructionList=" + this.instructionList + "\n tryBlock=" + this.tryBlock + "\n debug=" + getDebugInfo();
    }

    static class Header extends SectionItem implements BlockRefresh {
        private final CodeItem codeItem;
        final DataItemIndirectReference<DebugInfo> debugInfoOffset;
        final IntegerReference instructionCodeUnits;
        final IntegerReference outs;
        final IntegerReference parameterRegisters;
        final IntegerReference registersCount;
        final IntegerReference tryBlockCount;

        public Header(CodeItem codeItem) {
            super(16);
            this.codeItem = codeItem;
            this.registersCount = new IndirectShort(this, 0);
            this.parameterRegisters = new IndirectShort(this, 2);
            this.outs = new IndirectShort(this, 4);
            this.tryBlockCount = new IndirectShort(this, 6);
            this.debugInfoOffset = new DataItemIndirectReference<>(SectionType.DEBUG_INFO, this, 8, UsageMarker.USAGE_DEBUG);
            this.instructionCodeUnits = new IndirectInteger(this, 12);
        }

        @Override // com.reandroid.arsc.base.BlockRefresh
        public void refresh() {
            this.debugInfoOffset.addUniqueUser(this.codeItem);
            this.debugInfoOffset.refresh();
        }

        @Override // com.reandroid.arsc.item.BlockItem, com.reandroid.arsc.base.Block
        public void onReadBytes(BlockReader blockReader) throws IOException {
            super.onReadBytes(blockReader);
            this.debugInfoOffset.pullItem();
            this.debugInfoOffset.addUniqueUser(this.codeItem);
            if (this.tryBlockCount.get() != 0) {
                this.codeItem.initTryBlock();
            }
        }

        public void onRemove() {
            this.debugInfoOffset.setItem((DataItem) null);
        }

        @Override // com.reandroid.dex.common.SectionItem, com.reandroid.dex.common.EditableItem
        public void editInternal(Block block) {
            this.debugInfoOffset.editInternal(block);
        }

        public void merge(Header header) {
            this.registersCount.set(header.registersCount.get());
            this.parameterRegisters.set(header.parameterRegisters.get());
            this.outs.set(header.outs.get());
            this.tryBlockCount.set(header.tryBlockCount.get());
            DebugInfo debugInfo = (DebugInfo) header.debugInfoOffset.getItem();
            if (debugInfo != null) {
                this.debugInfoOffset.setItem(debugInfo.getKey());
                this.debugInfoOffset.addUniqueUser(this.codeItem);
            }
            this.instructionCodeUnits.set(header.instructionCodeUnits.get());
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || getClass() != obj.getClass()) {
                return false;
            }
            Header header = (Header) obj;
            return this.registersCount.get() == header.registersCount.get() && this.parameterRegisters.get() == header.parameterRegisters.get() && this.outs.get() == header.outs.get() && this.tryBlockCount.get() == header.tryBlockCount.get() && this.instructionCodeUnits.get() == header.instructionCodeUnits.get() && ObjectsUtil.equals(this.debugInfoOffset.getItem(), header.debugInfoOffset.getItem());
        }

        public int hashCode() {
            int i = (((((((((this.registersCount.get() + 31) * 31) + this.parameterRegisters.get()) * 31) + this.outs.get()) * 31) + this.tryBlockCount.get()) * 31) + this.instructionCodeUnits.get()) * 31;
            DebugInfo debugInfo = (DebugInfo) this.debugInfoOffset.getItem();
            return debugInfo != null ? i + debugInfo.hashCode() : i;
        }

        public String toString() {
            return "registers=" + this.registersCount + ", parameters=" + this.parameterRegisters + ", outs=" + this.outs + ", tries=" + this.tryBlockCount + ", debugInfo=" + this.debugInfoOffset + ", codeUnits=" + this.instructionCodeUnits;
        }
    }
}
