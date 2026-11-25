package com.reandroid.dex.smali.model;

import com.reandroid.dex.ins.InsArrayData;
import com.reandroid.dex.ins.Opcode;
import com.reandroid.dex.smali.SmaliDirective;
import com.reandroid.dex.smali.SmaliParseException;
import com.reandroid.dex.smali.SmaliReader;
import java.io.IOException;
import name.antonsmirnov.firmata.FormatHelper;

/* JADX INFO: loaded from: classes11.dex */
public class SmaliPayloadArray extends SmaliInstructionPayload<SmaliValueNumber<?>> {
    public SmaliPayloadArray() {
        super(new SmaliInstructionOperand.SmaliDecimalOperand());
    }

    public int[] unsignedInt() {
        SmaliSet<SmaliValueNumber<?>> entries = getEntries();
        int size = entries.size();
        int[] iArr = new int[size];
        for (int i = 0; i < size; i++) {
            iArr[i] = ((SmaliValueNumber) entries.get(i)).unsignedInt();
        }
        return iArr;
    }

    public long[] unsignedLong() {
        SmaliSet<SmaliValueNumber<?>> entries = getEntries();
        int size = entries.size();
        long[] jArr = new long[size];
        for (int i = 0; i < size; i++) {
            jArr[i] = ((SmaliValueNumber) entries.get(i)).unsignedLong();
        }
        return jArr;
    }

    @Override // com.reandroid.dex.smali.model.SmaliInstructionPayload, com.reandroid.dex.smali.model.SmaliInstruction
    public SmaliInstructionOperand.SmaliDecimalOperand getOperand() {
        return (SmaliInstructionOperand.SmaliDecimalOperand) super.getOperand();
    }

    public int getWidth() {
        return getOperand().getNumber();
    }

    public void setWidth(int i) {
        getOperand().setNumber(i);
    }

    @Override // com.reandroid.dex.smali.model.SmaliInstructionPayload, com.reandroid.dex.smali.model.SmaliInstruction
    public int getCodeUnits() {
        int width = (getWidth() * getEntries().size()) + 8;
        return (width + ((2 - (width % 2)) % 2)) / 2;
    }

    @Override // com.reandroid.dex.smali.SmaliRegion
    public SmaliDirective getSmaliDirective() {
        return SmaliDirective.ARRAY_DATA;
    }

    @Override // com.reandroid.dex.smali.model.SmaliInstructionPayload, com.reandroid.dex.smali.model.SmaliInstruction
    public Opcode<InsArrayData> getOpcode() {
        return Opcode.ARRAY_PAYLOAD;
    }

    @Override // com.reandroid.dex.smali.model.SmaliInstructionPayload
    public SmaliValueNumber<?> newEntry(SmaliReader smaliReader) throws IOException {
        SmaliValue smaliValueCreate = SmaliValue.create(smaliReader);
        if (!(smaliValueCreate instanceof SmaliValueNumber)) {
            throw new SmaliParseException("Unrecognized array data entry", smaliReader);
        }
        SmaliValueNumber<?> smaliValueNumber = (SmaliValueNumber) smaliValueCreate;
        if (smaliValueNumber.getWidth() <= getWidth()) {
            return smaliValueNumber;
        }
        throw new SmaliParseException("Value out of range", smaliReader);
    }

    @Override // com.reandroid.dex.smali.model.SmaliInstructionPayload
    void parseOperand(Opcode<?> opcode, SmaliReader smaliReader) throws IOException {
        smaliReader.skipWhitespacesOrComment();
        int iPosition = smaliReader.position();
        SmaliInstructionOperand.SmaliDecimalOperand operand = getOperand();
        operand.parse(opcode, smaliReader);
        int number = operand.getNumber();
        if (number < 1 || number > 8) {
            smaliReader.position(iPosition);
            throw new SmaliParseException("Array width out of range (1 .. 8) : '" + number + FormatHelper.QUOTE, smaliReader);
        }
    }
}
