package com.reandroid.dex.debug;

import com.reandroid.arsc.io.BlockReader;
import com.reandroid.arsc.item.ByteItem;
import com.reandroid.dex.base.DexException;
import com.reandroid.dex.data.FixedDexContainerWithTool;
import com.reandroid.dex.id.IdItem;
import com.reandroid.dex.ins.ExtraLine;
import com.reandroid.dex.ins.Ins;
import com.reandroid.dex.smali.SmaliDirective;
import com.reandroid.dex.smali.SmaliWriter;
import com.reandroid.dex.smali.model.SmaliDebugElement;
import com.reandroid.utils.collection.EmptyIterator;
import java.io.IOException;
import java.util.Iterator;

/* JADX INFO: loaded from: classes11.dex */
public abstract class DebugElement extends FixedDexContainerWithTool implements ExtraLine {
    private int address;
    private final ByteItem elementType;
    private int lineNumber;
    private Ins targetIns;

    int getAddressDiff() {
        return 0;
    }

    public abstract DebugElementType<?> getElementType();

    int getLineDiff() {
        return 0;
    }

    @Override // com.reandroid.dex.ins.ExtraLine
    public int getSortOrder() {
        return 1;
    }

    @Override // com.reandroid.dex.ins.ExtraLine
    public boolean isEqualExtraLine(Object obj) {
        return obj == this;
    }

    void setLineDiff(int i) {
    }

    DebugElement(int i, int i2) {
        super(i + 1);
        ByteItem byteItem = new ByteItem();
        this.elementType = byteItem;
        byteItem.set((byte) i2);
        addChild(0, byteItem);
    }

    DebugElement(int i, DebugElementType<?> debugElementType) {
        this(i, debugElementType.getFlag());
    }

    DebugElement(DebugElementType<?> debugElementType) {
        this(0, debugElementType.getFlag());
    }

    @Override // com.reandroid.dex.ins.ExtraLine
    public Ins getTargetIns() {
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

    public void removeSelf() {
        DebugSequence debugSequence = getDebugSequence();
        if (debugSequence != null) {
            debugSequence.remove(this);
        }
    }

    public boolean isValid() {
        return !isRemoved();
    }

    void setAddressDiff(int i) {
        DebugAdvancePc orCreateDebugAdvancePc;
        if (i == 0 || (orCreateDebugAdvancePc = getOrCreateDebugAdvancePc()) == null) {
            return;
        }
        orCreateDebugAdvancePc.setAddressDiff(i);
    }

    @Override // com.reandroid.dex.ins.ExtraLine
    public boolean isRemoved() {
        return getDebugSequence() == null;
    }

    DebugSequence getDebugSequence() {
        DebugSequence debugSequence = (DebugSequence) getParent(DebugSequence.class);
        if (debugSequence == null || !debugSequence.isRemoved()) {
            return debugSequence;
        }
        return null;
    }

    @Override // com.reandroid.dex.ins.ExtraLine
    public int getTargetAddress() {
        return this.address;
    }

    @Override // com.reandroid.dex.ins.ExtraLine
    public void setTargetAddress(int i) {
        DebugElement next = this;
        while (next.updateTargetAddress(i) && (next = next.getNext()) != null) {
            i += next.getAddressDiff();
        }
    }

    private boolean updateTargetAddress(int i) {
        int i2 = 0;
        if (i == getTargetAddress()) {
            return false;
        }
        DebugElement previous = getPrevious();
        if (previous == null) {
            i2 = i;
        } else {
            int targetAddress = i - previous.getTargetAddress();
            if (targetAddress >= 0) {
                i2 = targetAddress;
            }
        }
        setAddressDiff(i2);
        this.address = i;
        return true;
    }

    private DebugElement getPrevious() {
        DebugSequence debugSequence;
        int index = getIndex();
        if (index > 0 && (debugSequence = getDebugSequence()) != null) {
            return debugSequence.get(index - 1);
        }
        return null;
    }

    private DebugElement getNext() {
        DebugSequence debugSequence;
        int index = getIndex();
        if (index >= 0 && (debugSequence = getDebugSequence()) != null) {
            return debugSequence.get(index + 1);
        }
        return null;
    }

    private DebugAdvancePc getOrCreateDebugAdvancePc() {
        DebugSequence debugSequence;
        DebugAdvancePc debugAdvancePc = getDebugAdvancePc();
        return (debugAdvancePc == null && (debugSequence = getDebugSequence()) != null) ? (DebugAdvancePc) debugSequence.createAtPosition(DebugElementType.ADVANCE_PC, getIndex()) : debugAdvancePc;
    }

    private DebugAdvancePc getDebugAdvancePc() {
        DebugSequence debugSequence = getDebugSequence();
        if (debugSequence == null) {
            return null;
        }
        DebugElement debugElement = debugSequence.get(getIndex() - 1);
        if (debugElement instanceof DebugAdvanceLine) {
            debugElement = debugSequence.get(debugElement.getIndex() - 1);
        }
        if (debugElement instanceof DebugAdvancePc) {
            return (DebugAdvancePc) debugElement;
        }
        return null;
    }

    int getLineNumber() {
        return this.lineNumber;
    }

    void setLineNumber(int i) {
        this.lineNumber = i;
    }

    int getFlag() {
        int iUnsignedInt = this.elementType.unsignedInt();
        if (iUnsignedInt > 10) {
            return 10;
        }
        return iUnsignedInt;
    }

    int getFlagOffset() {
        int iUnsignedInt = this.elementType.unsignedInt();
        if (iUnsignedInt < 10) {
            return 0;
        }
        return iUnsignedInt - 10;
    }

    void setFlagOffset(int i) {
        int flag = getFlag();
        if (flag < 10) {
            if (i != 0) {
                throw new IllegalArgumentException("Can not set offset for: " + getElementType());
            }
        } else {
            if (i < 0 || i > 245) {
                throw new DexException("Value out of range should be [0 - 245]: " + i + ", prev = " + getFlagOffset());
            }
            this.elementType.set((byte) (flag + i));
        }
    }

    public SmaliDirective getSmaliDirective() {
        return getElementType().getSmaliDirective();
    }

    void cacheValues(DebugSequence debugSequence, DebugElement debugElement) {
        int i;
        int lineStart;
        if (debugElement == null) {
            lineStart = debugSequence.getLineStart();
            i = 0;
        } else {
            int targetAddress = debugElement.getTargetAddress();
            int lineNumber = debugElement.getLineNumber();
            i = targetAddress;
            lineStart = lineNumber;
        }
        int addressDiff = i + getAddressDiff();
        int lineDiff = lineStart + getLineDiff();
        this.address = addressDiff;
        this.lineNumber = lineDiff;
    }

    void updateValues(DebugSequence debugSequence, DebugElement debugElement) {
        int i;
        int lineStart;
        if (debugElement == this) {
            return;
        }
        if (debugElement == null || debugElement.getParent() != null) {
            if (debugElement == null) {
                lineStart = debugSequence.getLineStart();
                i = 0;
            } else {
                int targetAddress = debugElement.getTargetAddress();
                int lineNumber = debugElement.getLineNumber();
                i = targetAddress;
                lineStart = lineNumber;
            }
            int targetAddress2 = getTargetAddress() - i;
            int lineNumber2 = getLineNumber() - lineStart;
            setAddressDiff(targetAddress2);
            setLineDiff(lineNumber2);
        }
    }

    void onPreRemove(DebugSequence debugSequence) {
        transferLineOffset(debugSequence);
    }

    private void transferLineOffset(DebugSequence debugSequence) {
        DebugElement debugElement;
        int lineDiff;
        int lineDiff2 = getLineDiff();
        if (lineDiff2 == 0) {
            return;
        }
        DebugElement debugElement2 = debugSequence.get(getIndex() - 1);
        if (debugElement2 == null) {
            debugSequence.setLineStart(debugSequence.getLineStart() + lineDiff2);
            return;
        }
        int lineDiff3 = 245 - debugElement2.getLineDiff();
        if (lineDiff3 > 0) {
            if (lineDiff2 > lineDiff3) {
                debugElement2.setLineDiff(debugElement2.getLineDiff() + lineDiff3);
                lineDiff2 -= lineDiff3;
            } else {
                debugElement2.setLineDiff(debugElement2.getLineDiff() + lineDiff2);
                lineDiff2 = 0;
            }
        }
        if (lineDiff2 == 0 || (debugElement = debugSequence.get(getIndex() + 1)) == null || (lineDiff = 245 - debugElement.getLineDiff()) <= 0) {
            return;
        }
        if (lineDiff2 > lineDiff) {
            debugElement.setLineDiff(debugElement2.getLineDiff() + lineDiff);
        } else {
            debugElement.setLineDiff(debugElement2.getLineDiff() + lineDiff2);
        }
    }

    @Override // com.reandroid.dex.base.FixedDexContainer, com.reandroid.arsc.base.BlockContainer, com.reandroid.arsc.base.Block
    public void onReadBytes(BlockReader blockReader) throws IOException {
        super.nonCheckRead(blockReader);
    }

    @Override // com.reandroid.dex.ins.ExtraLine
    public void appendExtra(SmaliWriter smaliWriter) throws IOException {
        if (isValid()) {
            getSmaliDirective().append(smaliWriter);
        }
    }

    public Iterator<IdItem> usedIds() {
        return EmptyIterator.of();
    }

    public void merge(DebugElement debugElement) {
        this.elementType.set(debugElement.elementType.get());
    }

    public void fromSmali(SmaliDebugElement smaliDebugElement) throws IOException {
        setTargetAddress(smaliDebugElement.getAddress());
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        return obj != null && getClass() == obj.getClass() && this.elementType.get() == ((DebugElement) obj).elementType.get();
    }

    public int hashCode() {
        return this.elementType.get();
    }

    public String toString() {
        return "Type = " + getElementType();
    }
}
