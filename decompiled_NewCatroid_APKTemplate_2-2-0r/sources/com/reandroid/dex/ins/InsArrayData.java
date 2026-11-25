package com.reandroid.dex.ins;

import com.reandroid.arsc.item.IntegerItem;
import com.reandroid.arsc.item.IntegerReference;
import com.reandroid.arsc.item.ShortItem;
import com.reandroid.dex.base.DexBlockAlign;
import com.reandroid.dex.base.NumberArray;
import com.reandroid.dex.smali.SmaliDirective;
import com.reandroid.dex.smali.SmaliRegion;
import com.reandroid.dex.smali.SmaliValidateException;
import com.reandroid.dex.smali.SmaliWriter;
import com.reandroid.dex.smali.model.SmaliInstruction;
import com.reandroid.dex.smali.model.SmaliPayloadArray;
import com.reandroid.utils.collection.EmptyIterator;
import com.reandroid.utils.collection.InstanceIterator;
import java.io.IOException;
import java.util.Iterator;
import name.antonsmirnov.firmata.FormatHelper;

/* JADX INFO: loaded from: classes11.dex */
public class InsArrayData extends PayloadData implements SmaliRegion {
    private final DexBlockAlign blockAlign;
    private final NumberArray numberArray;

    public InsArrayData() {
        super(4, Opcode.ARRAY_PAYLOAD);
        ShortItem shortItem = new ShortItem();
        IntegerItem integerItem = new IntegerItem();
        NumberArray numberArray = new NumberArray(shortItem, integerItem);
        this.numberArray = numberArray;
        DexBlockAlign dexBlockAlign = new DexBlockAlign(numberArray);
        this.blockAlign = dexBlockAlign;
        dexBlockAlign.setAlignment(2);
        addChild(1, shortItem);
        addChild(2, integerItem);
        addChild(3, numberArray);
        addChild(4, dexBlockAlign);
    }

    public Iterator<InsFillArrayData> getInsFillArrayData() {
        InsBlockList insBlockList = getInsBlockList();
        if (insBlockList == null) {
            return EmptyIterator.of();
        }
        insBlockList.link();
        return InstanceIterator.of(getExtraLines(), InsFillArrayData.class);
    }

    public int size() {
        return getNumberArray().size();
    }

    public void setSize(int i) {
        getNumberArray().setSize(i);
        refreshAlignment();
    }

    public int getWidth() {
        return getNumberArray().getWidth();
    }

    public void setWidth(int i) {
        getNumberArray().setWidth(i);
        refreshAlignment();
    }

    public void set(byte[] bArr) {
        NumberArray numberArray = getNumberArray();
        numberArray.setSize(0);
        numberArray.setWidth(1);
        numberArray.put(bArr);
        refreshAlignment();
    }

    public void set(short[] sArr) {
        NumberArray numberArray = getNumberArray();
        numberArray.setSize(0);
        numberArray.setWidth(2);
        numberArray.put(sArr);
        refreshAlignment();
    }

    public void set(int[] iArr) {
        NumberArray numberArray = getNumberArray();
        numberArray.setSize(0);
        numberArray.put(iArr);
        refreshAlignment();
    }

    public void set(long[] jArr) {
        NumberArray numberArray = getNumberArray();
        numberArray.setSize(0);
        numberArray.setWidth(8);
        numberArray.putLong(jArr);
        refreshAlignment();
    }

    @Override // com.reandroid.dex.ins.PayloadData
    public Iterator<IntegerReference> getReferences() {
        return getNumberArray().getReferences();
    }

    public IntegerReference getReference(int i) {
        return getNumberArray().getReference(i);
    }

    public int getAsInteger(int i) {
        return getNumberArray().getAsInteger(i);
    }

    public long getLong(int i) {
        return getNumberArray().getLong(i);
    }

    public void put(int i, int i2) {
        getNumberArray().put(i, i2);
    }

    public void putLong(int i, long j) {
        getNumberArray().putLong(i, j);
    }

    public NumberArray getNumberArray() {
        return this.numberArray;
    }

    public void refreshAlignment() {
        this.blockAlign.align(this);
    }

    @Override // com.reandroid.dex.ins.Ins
    public void appendCode(SmaliWriter smaliWriter) throws IOException {
        getSmaliDirective().append(smaliWriter);
        smaliWriter.appendInteger(getWidth());
        smaliWriter.indentPlus();
        appendData(smaliWriter);
        smaliWriter.indentMinus();
        getSmaliDirective().appendEnd(smaliWriter);
    }

    private void appendData(SmaliWriter smaliWriter) throws IOException {
        NumberArray numberArray = getNumberArray();
        int width = numberArray.getWidth();
        int size = numberArray.size();
        int i = 0;
        if (width < 2) {
            while (i < size) {
                smaliWriter.newLine();
                smaliWriter.appendHex(numberArray.getByte(i));
                i++;
            }
            return;
        }
        if (width < 4) {
            while (i < size) {
                smaliWriter.newLine();
                smaliWriter.appendHex(numberArray.getShort(i));
                i++;
            }
            return;
        }
        if (width == 4) {
            while (i < size) {
                smaliWriter.newLine();
                smaliWriter.appendHex(numberArray.getInteger(i));
                i++;
            }
            return;
        }
        while (i < size) {
            smaliWriter.newLine();
            smaliWriter.appendHex(numberArray.getLong(i));
            i++;
        }
    }

    @Override // com.reandroid.dex.ins.Ins
    public void merge(Ins ins) {
        getNumberArray().merge(((InsArrayData) ins).getNumberArray());
        refreshAlignment();
    }

    @Override // com.reandroid.dex.ins.PayloadData, com.reandroid.dex.ins.Ins
    public void fromSmali(SmaliInstruction smaliInstruction) throws IOException {
        validateOpcode(smaliInstruction);
        SmaliPayloadArray smaliPayloadArray = (SmaliPayloadArray) smaliInstruction;
        int width = smaliPayloadArray.getWidth();
        if (width < 1 || width > 8) {
            throw new SmaliValidateException("Array values width out of range '" + width + FormatHelper.QUOTE, smaliInstruction);
        }
        setWidth(width);
        if (width > 4) {
            set(smaliPayloadArray.unsignedLong());
        } else {
            set(smaliPayloadArray.unsignedInt());
        }
        refreshAlignment();
    }

    @Override // com.reandroid.dex.smali.SmaliRegion
    public SmaliDirective getSmaliDirective() {
        return SmaliDirective.ARRAY_DATA;
    }
}
