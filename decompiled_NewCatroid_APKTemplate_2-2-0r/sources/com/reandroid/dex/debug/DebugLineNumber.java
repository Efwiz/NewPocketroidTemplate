package com.reandroid.dex.debug;

import com.reandroid.dex.smali.SmaliWriter;
import com.reandroid.dex.smali.model.SmaliDebugElement;
import com.reandroid.dex.smali.model.SmaliLineNumber;
import java.io.IOException;

/* JADX INFO: loaded from: classes11.dex */
public class DebugLineNumber extends DebugElement {
    @Override // com.reandroid.dex.debug.DebugElement, com.reandroid.dex.ins.ExtraLine
    public int getSortOrder() {
        return 0;
    }

    public DebugLineNumber() {
        super(DebugElementType.LINE_NUMBER);
    }

    @Override // com.reandroid.dex.debug.DebugElement
    int getAddressDiff() {
        return getFlagOffset() / 15;
    }

    @Override // com.reandroid.dex.debug.DebugElement
    void setAddressDiff(int i) {
        int lineDiff = getLineDiff();
        int i2 = (i * 15) + lineDiff + 4;
        if (i2 > 245) {
            super.setAddressDiff(i);
            i2 = lineDiff + 4;
        }
        if (i2 < 0) {
            i2 = 0;
        }
        setFlagOffset(i2);
    }

    @Override // com.reandroid.dex.debug.DebugElement
    int getLineDiff() {
        return (getFlagOffset() % 15) - 4;
    }

    @Override // com.reandroid.dex.debug.DebugElement
    void setLineDiff(int i) {
        int addressDiff = getAddressDiff() * 15;
        int i2 = addressDiff + i + 4;
        if (i2 > 245) {
            getOrCreateAdvanceLine().setLineDiff(i - 4);
        } else {
            addressDiff = i2;
        }
        setFlagOffset(addressDiff);
    }

    private int getMaxLineDiff() {
        return getAddressDiff() == 16 ? 1 : 10;
    }

    @Override // com.reandroid.dex.debug.DebugElement
    public int getLineNumber() {
        return super.getLineNumber();
    }

    @Override // com.reandroid.dex.debug.DebugElement
    public void setLineNumber(int i) {
        setUpLineNumber(i);
        super.setLineNumber(i);
    }

    private int getPreviousLineNumber() {
        DebugSequence debugSequence = getDebugSequence();
        int index = getIndex();
        if (debugSequence != null && index != 0) {
            for (int i = index - 1; i >= 0; i--) {
                DebugElement debugElement = debugSequence.get(i);
                if (debugElement instanceof DebugLineNumber) {
                    return ((DebugLineNumber) debugElement).getLineNumber();
                }
            }
        }
        return 0;
    }

    private void setUpLineNumber(int i) {
        int previousLineNumber = i - getPreviousLineNumber();
        int maxLineDiff = getMaxLineDiff();
        if (previousLineNumber >= -4 && previousLineNumber <= maxLineDiff) {
            setLineDiff(previousLineNumber);
            return;
        }
        DebugSequence debugSequence = getDebugSequence();
        if (debugSequence == null) {
            return;
        }
        if (getIndex() == 0) {
            debugSequence.setLineStartInternal(i);
            setLineDiff(0);
            return;
        }
        DebugAdvanceLine orCreateAdvanceLine = getOrCreateAdvanceLine();
        if (orCreateAdvanceLine == null) {
            return;
        }
        setLineDiff(0);
        orCreateAdvanceLine.setLineDiff(previousLineNumber);
    }

    private DebugAdvanceLine getOrCreateAdvanceLine() {
        DebugSequence debugSequence;
        DebugAdvanceLine advanceLine = getAdvanceLine();
        return (advanceLine == null && (debugSequence = getDebugSequence()) != null) ? (DebugAdvanceLine) debugSequence.createAtPosition(DebugElementType.ADVANCE_LINE, getIndex()) : advanceLine;
    }

    private DebugAdvanceLine getAdvanceLine() {
        DebugSequence debugSequence = getDebugSequence();
        if (debugSequence == null) {
            return null;
        }
        DebugElement debugElement = debugSequence.get(getIndex() - 1);
        if (debugElement instanceof DebugAdvancePc) {
            debugElement = debugSequence.get(debugElement.getIndex() - 1);
        }
        if (debugElement instanceof DebugAdvanceLine) {
            return (DebugAdvanceLine) debugElement;
        }
        return null;
    }

    @Override // com.reandroid.dex.debug.DebugElement, com.reandroid.dex.ins.ExtraLine
    public boolean isEqualExtraLine(Object obj) {
        if (obj == this) {
            return true;
        }
        return (obj instanceof DebugLineNumber) && getTargetAddress() == ((DebugLineNumber) obj).getTargetAddress();
    }

    @Override // com.reandroid.dex.debug.DebugElement
    public void fromSmali(SmaliDebugElement smaliDebugElement) throws IOException {
        super.fromSmali(smaliDebugElement);
        setLineNumber(((SmaliLineNumber) smaliDebugElement).getNumber());
    }

    @Override // com.reandroid.dex.debug.DebugElement, com.reandroid.dex.ins.ExtraLine
    public void appendExtra(SmaliWriter smaliWriter) throws IOException {
        int lineNumber;
        if (!isValid() || (lineNumber = getLineNumber()) == -1) {
            return;
        }
        getSmaliDirective().append(smaliWriter);
        smaliWriter.appendInteger(lineNumber);
    }

    @Override // com.reandroid.dex.debug.DebugElement
    public DebugElementType<DebugLineNumber> getElementType() {
        return DebugElementType.LINE_NUMBER;
    }

    @Override // com.reandroid.dex.ins.ExtraLine
    public int getSortOrderFine() {
        return getLineNumber();
    }

    @Override // com.reandroid.dex.debug.DebugElement
    public String toString() {
        return ".line " + getLineNumber();
    }
}
