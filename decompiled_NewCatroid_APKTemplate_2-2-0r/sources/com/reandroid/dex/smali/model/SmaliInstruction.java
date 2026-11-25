package com.reandroid.dex.smali.model;

import com.reandroid.dex.common.OperandType;
import com.reandroid.dex.common.Register;
import com.reandroid.dex.common.RegisterFormat;
import com.reandroid.dex.common.RegistersTable;
import com.reandroid.dex.ins.Opcode;
import com.reandroid.dex.key.Key;
import com.reandroid.dex.smali.SmaliParseException;
import com.reandroid.dex.smali.SmaliReader;
import com.reandroid.dex.smali.SmaliWriter;
import java.io.IOException;

/* JADX INFO: loaded from: classes11.dex */
public class SmaliInstruction extends SmaliCode {
    private int address;
    private Opcode<?> opcode;
    private SmaliInstructionOperand operand;
    private SmaliRegisterSet registerSet;

    public SmaliInstruction() {
        this.opcode = Opcode.NOP;
        this.registerSet = SmaliRegisterSet.NO_REGISTER_SET;
        this.operand = SmaliInstructionOperand.NO_OPERAND;
    }

    public SmaliInstruction(Opcode<?> opcode) {
        opcode.getClass();
        this.opcode = opcode;
        initRegisterSet(opcode);
        try {
            initOperand(opcode);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Key getKey() {
        SmaliInstructionOperand operand = getOperand();
        if (operand instanceof SmaliInstructionOperand.SmaliKeyOperand) {
            return ((SmaliInstructionOperand.SmaliKeyOperand) operand).getKey();
        }
        return null;
    }

    public Number getData() throws IOException {
        SmaliInstructionOperand operand = getOperand();
        if (operand instanceof SmaliInstructionOperand.SmaliHexOperand) {
            return ((SmaliInstructionOperand.SmaliHexOperand) operand).getNumber();
        }
        if (operand instanceof SmaliInstructionOperand.SmaliLabelOperand) {
            return Integer.valueOf(operand.getIntegerData() - getAddress());
        }
        return null;
    }

    public int getAddress() {
        return this.address;
    }

    public void setAddress(int i) {
        this.address = i;
    }

    public int getCodeUnits() {
        return getOpcode().size() / 2;
    }

    public Register getRegister() {
        return getRegister(0);
    }

    public Register getRegister(int i) {
        return getRegisterSet().getRegister(i);
    }

    public int getRegistersCount() {
        return getRegisterSet().size();
    }

    public RegistersTable getRegistersTable() {
        SmaliRegisterSet registerSet = getRegisterSet();
        if (registerSet != null) {
            return registerSet.getRegistersTable();
        }
        return null;
    }

    public void setRegistersTable(RegistersTable registersTable) {
        SmaliRegisterSet registerSet = getRegisterSet();
        if (registerSet != null) {
            registerSet.setRegistersTable(registersTable);
        }
    }

    public SmaliRegisterSet getRegisterSet() {
        return this.registerSet;
    }

    public void setRegisterSet(SmaliRegisterSet smaliRegisterSet) {
        this.registerSet = smaliRegisterSet;
        if (smaliRegisterSet != null) {
            smaliRegisterSet.setParent(this);
        }
    }

    public SmaliInstructionOperand getOperand() {
        return this.operand;
    }

    public OperandType getOperandType() {
        return getOperand().getOperandType();
    }

    public void setOperand(SmaliInstructionOperand smaliInstructionOperand) {
        this.operand = smaliInstructionOperand;
        if (smaliInstructionOperand != null) {
            smaliInstructionOperand.setParent(this);
        }
    }

    public boolean hasLabelOperand(SmaliLabel smaliLabel) {
        SmaliInstructionOperand operand = getOperand();
        if (operand instanceof SmaliInstructionOperand.SmaliLabelOperand) {
            return smaliLabel.equals(((SmaliInstructionOperand.SmaliLabelOperand) operand).getLabel());
        }
        return false;
    }

    public Opcode<?> getOpcode() {
        return this.opcode;
    }

    public void initializeOpcode(Opcode<?> opcode) throws IOException {
        this.opcode = opcode;
        initRegisterSet(opcode);
        initOperand(opcode);
    }

    private void initRegisterSet(Opcode<?> opcode) {
        SmaliRegisterSet smaliRegisterSet;
        RegisterFormat registerFormat = opcode.getRegisterFormat();
        if (registerFormat == RegisterFormat.NONE) {
            smaliRegisterSet = SmaliRegisterSet.NO_REGISTER_SET;
        } else {
            smaliRegisterSet = new SmaliRegisterSet(registerFormat);
        }
        setRegisterSet(smaliRegisterSet);
    }

    private void initOperand(Opcode<?> opcode) throws IOException {
        SmaliInstructionOperand smaliDecimalOperand;
        OperandType operandType = opcode.getOperandType();
        if (operandType == OperandType.NONE) {
            smaliDecimalOperand = SmaliInstructionOperand.NO_OPERAND;
        } else if (operandType == OperandType.HEX) {
            smaliDecimalOperand = new SmaliInstructionOperand.SmaliHexOperand();
        } else if (operandType == OperandType.KEY) {
            smaliDecimalOperand = new SmaliInstructionOperand.SmaliKeyOperand();
        } else if (operandType == OperandType.LABEL) {
            smaliDecimalOperand = new SmaliInstructionOperand.SmaliLabelOperand();
        } else if (operandType == OperandType.DECIMAL) {
            smaliDecimalOperand = new SmaliInstructionOperand.SmaliDecimalOperand();
        } else {
            throw new IOException("Unknown operand type: " + operandType + ", opcode = " + opcode);
        }
        setOperand(smaliDecimalOperand);
    }

    @Override // com.reandroid.dex.smali.model.Smali, com.reandroid.dex.smali.SmaliFormat
    public void append(SmaliWriter smaliWriter) throws IOException {
        Opcode<?> opcode = getOpcode();
        if (opcode == null) {
            return;
        }
        smaliWriter.newLine();
        opcode.append(smaliWriter);
        SmaliRegisterSet registerSet = getRegisterSet();
        if (registerSet != null) {
            registerSet.append(smaliWriter);
        }
        if (opcode.getRegisterFormat() != RegisterFormat.NONE && opcode.getOperandType() != OperandType.NONE) {
            smaliWriter.append(", ");
        }
        getOperand().append(smaliWriter);
    }

    @Override // com.reandroid.dex.smali.model.SmaliCode, com.reandroid.dex.smali.SmaliParser
    public void parse(SmaliReader smaliReader) throws IOException {
        smaliReader.skipWhitespacesOrComment();
        setOrigin(smaliReader.getCurrentOrigin());
        Opcode<?> opcode = parseOpcode(smaliReader);
        getRegisterSet().parse(smaliReader);
        if (opcode.getRegisterFormat() != RegisterFormat.NONE && opcode.getOperandType() != OperandType.NONE) {
            smaliReader.skipWhitespacesOrComment();
            SmaliParseException.expect(smaliReader, ',');
            smaliReader.skipWhitespacesOrComment();
        }
        getOperand().parse(opcode, smaliReader);
    }

    private Opcode<?> parseOpcode(SmaliReader smaliReader) throws IOException {
        smaliReader.skipWhitespaces();
        Opcode<?> smali = Opcode.parseSmali(smaliReader, true);
        initializeOpcode(smali);
        smaliReader.skipSpaces();
        return smali;
    }
}
