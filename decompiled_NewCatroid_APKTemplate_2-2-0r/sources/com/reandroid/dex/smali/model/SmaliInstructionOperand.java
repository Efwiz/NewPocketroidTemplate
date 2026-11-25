package com.reandroid.dex.smali.model;

import com.reandroid.dex.common.OperandType;
import com.reandroid.dex.id.IdItem;
import com.reandroid.dex.ins.Opcode;
import com.reandroid.dex.key.FieldKey;
import com.reandroid.dex.key.Key;
import com.reandroid.dex.key.MethodKey;
import com.reandroid.dex.key.StringKey;
import com.reandroid.dex.key.TypeKey;
import com.reandroid.dex.sections.SectionType;
import com.reandroid.dex.smali.SmaliParseException;
import com.reandroid.dex.smali.SmaliReader;
import com.reandroid.dex.smali.SmaliWriter;
import java.io.IOException;

/* JADX INFO: loaded from: classes11.dex */
public abstract class SmaliInstructionOperand extends Smali {
    public static final SmaliInstructionOperand NO_OPERAND = new SmaliInstructionOperand() { // from class: com.reandroid.dex.smali.model.SmaliInstructionOperand.1
        @Override // com.reandroid.dex.smali.model.SmaliInstructionOperand, com.reandroid.dex.smali.model.Smali, com.reandroid.dex.smali.SmaliFormat
        public void append(SmaliWriter smaliWriter) {
        }

        @Override // com.reandroid.dex.smali.model.SmaliInstructionOperand
        public int getIntegerData() {
            return -1;
        }

        @Override // com.reandroid.dex.smali.model.SmaliInstructionOperand
        public long getLongData() {
            return -1L;
        }

        @Override // com.reandroid.dex.smali.model.Smali
        void setParent(Smali smali) {
        }

        @Override // com.reandroid.dex.smali.model.SmaliInstructionOperand
        public OperandType getOperandType() {
            return OperandType.NONE;
        }

        @Override // com.reandroid.dex.smali.model.SmaliInstructionOperand
        public void parse(Opcode<?> opcode, SmaliReader smaliReader) {
            smaliReader.skipSpaces();
        }
    };

    @Override // com.reandroid.dex.smali.model.Smali, com.reandroid.dex.smali.SmaliFormat
    public abstract void append(SmaliWriter smaliWriter) throws IOException;

    public abstract int getIntegerData() throws IOException;

    public abstract OperandType getOperandType();

    public abstract void parse(Opcode<?> opcode, SmaliReader smaliReader) throws IOException;

    public long getLongData() throws IOException {
        return ((long) getIntegerData()) & 4294967295L;
    }

    @Override // com.reandroid.dex.smali.SmaliParser
    public final void parse(SmaliReader smaliReader) throws IOException {
        throw new RuntimeException("Must call parse(Opcode, SmaliReader)");
    }

    public static class SmaliLabelOperand extends SmaliInstructionOperand {
        private final SmaliLabel label;

        public SmaliLabelOperand() {
            SmaliLabel smaliLabel = new SmaliLabel();
            this.label = smaliLabel;
            smaliLabel.setParent(this);
        }

        public SmaliLabel getLabel() {
            return this.label;
        }

        @Override // com.reandroid.dex.smali.model.SmaliInstructionOperand
        public int getIntegerData() throws IOException {
            return getLabel().getIntegerData();
        }

        @Override // com.reandroid.dex.smali.model.SmaliInstructionOperand
        public OperandType getOperandType() {
            return OperandType.LABEL;
        }

        @Override // com.reandroid.dex.smali.model.SmaliInstructionOperand, com.reandroid.dex.smali.model.Smali, com.reandroid.dex.smali.SmaliFormat
        public void append(SmaliWriter smaliWriter) throws IOException {
            getLabel().append(smaliWriter);
        }

        @Override // com.reandroid.dex.smali.model.SmaliInstructionOperand
        public void parse(Opcode<?> opcode, SmaliReader smaliReader) throws IOException {
            getLabel().parse(smaliReader);
        }
    }

    public static class SmaliHexOperand extends SmaliInstructionOperand {
        private SmaliValueNumber<?> valueNumber = new SmaliValueInteger();

        public Number getNumber() {
            return this.valueNumber.getNumber();
        }

        public void setNumber(Number number) {
            setNumberValue(SmaliValueNumber.createFor(number));
        }

        public SmaliValueNumber<?> getValueNumber() {
            return this.valueNumber;
        }

        public void setNumberValue(SmaliValueNumber<?> smaliValueNumber) {
            this.valueNumber = smaliValueNumber;
            if (smaliValueNumber != null) {
                smaliValueNumber.setParent(this);
            }
        }

        @Override // com.reandroid.dex.smali.model.SmaliInstructionOperand
        public int getIntegerData() {
            return this.valueNumber.unsignedInt();
        }

        @Override // com.reandroid.dex.smali.model.SmaliInstructionOperand
        public OperandType getOperandType() {
            return OperandType.HEX;
        }

        @Override // com.reandroid.dex.smali.model.SmaliInstructionOperand
        public long getLongData() {
            return this.valueNumber.unsignedLong();
        }

        @Override // com.reandroid.dex.smali.model.SmaliInstructionOperand, com.reandroid.dex.smali.model.Smali, com.reandroid.dex.smali.SmaliFormat
        public void append(SmaliWriter smaliWriter) throws IOException {
            smaliWriter.appendOptional(getValueNumber());
        }

        @Override // com.reandroid.dex.smali.model.SmaliInstructionOperand
        public void parse(Opcode<?> opcode, SmaliReader smaliReader) throws IOException {
            smaliReader.skipSpaces();
            SmaliValueNumber<?> smaliValueNumberCreateNumber = SmaliValueNumber.createNumber(smaliReader);
            setNumberValue(smaliValueNumberCreateNumber);
            smaliValueNumberCreateNumber.parse(smaliReader);
        }
    }

    public static class SmaliDecimalOperand extends SmaliInstructionOperand {
        private int number;

        public int getNumber() {
            return this.number;
        }

        public void setNumber(int i) {
            this.number = i;
        }

        @Override // com.reandroid.dex.smali.model.SmaliInstructionOperand
        public int getIntegerData() {
            return getNumber();
        }

        @Override // com.reandroid.dex.smali.model.SmaliInstructionOperand
        public OperandType getOperandType() {
            return OperandType.DECIMAL;
        }

        @Override // com.reandroid.dex.smali.model.SmaliInstructionOperand, com.reandroid.dex.smali.model.Smali, com.reandroid.dex.smali.SmaliFormat
        public void append(SmaliWriter smaliWriter) throws IOException {
            smaliWriter.appendInteger(getNumber());
        }

        @Override // com.reandroid.dex.smali.model.SmaliInstructionOperand
        public void parse(Opcode<?> opcode, SmaliReader smaliReader) throws IOException {
            smaliReader.skipSpaces();
            setNumber(smaliReader.readInteger());
        }
    }

    public static class SmaliKeyOperand extends SmaliInstructionOperand {
        private Key key;

        @Override // com.reandroid.dex.smali.model.SmaliInstructionOperand
        public int getIntegerData() {
            return -1;
        }

        @Override // com.reandroid.dex.smali.model.SmaliInstructionOperand
        public long getLongData() {
            return -1L;
        }

        public Key getKey() {
            return this.key;
        }

        public void setKey(Key key) {
            this.key = key;
        }

        @Override // com.reandroid.dex.smali.model.SmaliInstructionOperand
        public OperandType getOperandType() {
            return OperandType.KEY;
        }

        @Override // com.reandroid.dex.smali.model.SmaliInstructionOperand, com.reandroid.dex.smali.model.Smali, com.reandroid.dex.smali.SmaliFormat
        public void append(SmaliWriter smaliWriter) throws IOException {
            Key key = getKey();
            if (key != null) {
                key.append(smaliWriter);
            }
        }

        @Override // com.reandroid.dex.smali.model.SmaliInstructionOperand
        public void parse(Opcode<?> opcode, SmaliReader smaliReader) throws IOException {
            Key key;
            SectionType<? extends IdItem> sectionType = opcode.getSectionType();
            if (sectionType == SectionType.STRING_ID) {
                key = StringKey.read(smaliReader);
            } else if (sectionType == SectionType.TYPE_ID) {
                key = TypeKey.read(smaliReader);
            } else if (sectionType == SectionType.FIELD_ID) {
                key = FieldKey.read(smaliReader);
            } else if (sectionType == SectionType.METHOD_ID) {
                key = MethodKey.read(smaliReader);
            } else {
                throw new SmaliParseException("Invalid key", smaliReader);
            }
            setKey(key);
        }
    }
}
