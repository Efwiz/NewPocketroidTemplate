package com.reandroid.dex.ins;

import androidx.core.view.InputDeviceCompat;
import com.reandroid.arsc.io.BlockReader;
import com.reandroid.arsc.item.ByteArray;
import com.reandroid.dex.base.DexException;
import com.reandroid.dex.common.RegistersTable;
import com.reandroid.dex.data.InstructionList;
import com.reandroid.dex.id.IdItem;
import com.reandroid.dex.key.Key;
import com.reandroid.dex.reference.InsIdSectionReference;
import com.reandroid.dex.sections.SectionType;
import com.reandroid.dex.smali.SmaliValidateException;
import com.reandroid.dex.smali.SmaliWriter;
import com.reandroid.dex.smali.model.SmaliInstruction;
import com.reandroid.utils.HexUtil;
import com.reandroid.utils.collection.SingleIterator;
import io.netty.util.internal.StringUtil;
import java.io.IOException;
import java.util.Iterator;
import java.util.Objects;

/* JADX INFO: loaded from: classes11.dex */
public class SizeXIns extends Ins {
    private final InsIdSectionReference sectionReference;
    private final ByteArray valueBytes;

    public SizeXIns(Opcode<?> opcode) {
        super(opcode);
        ByteArray byteArray = new ByteArray();
        this.valueBytes = byteArray;
        addChild(0, byteArray);
        byteArray.setSize(opcode.size());
        byteArray.putShort(0, opcode.getValue());
        this.sectionReference = opcode.getSectionType() != null ? new InsIdSectionReference(this) : null;
    }

    public SectionType<? extends IdItem> getSectionType() {
        return getOpcode().getSectionType();
    }

    final ByteArray getValueBytes() {
        return this.valueBytes;
    }

    long getLong() {
        return getLong(this.valueBytes.getBytes(), 2);
    }

    void setLong(long j) {
        putLong(this.valueBytes.getBytes(), 2, j);
    }

    int getInteger() {
        return this.valueBytes.getInteger(2);
    }

    void setInteger(int i) {
        this.valueBytes.putInteger(2, i);
    }

    int getShortUnsigned(int i) {
        return this.valueBytes.getShortUnsigned(i);
    }

    int getShortSigned() {
        return this.valueBytes.getShort(2);
    }

    void setShort(int i, int i2) {
        if (((-65536) & i2) != 0 && i2 != ((short) i2)) {
            throw new InstructionException("Short value out of range " + HexUtil.toHex(i2, 4) + " > 0xffff", this);
        }
        this.valueBytes.putShort(i, i2);
    }

    int getByteSigned() {
        return this.valueBytes.get(1);
    }

    void setByte(int i, int i2) {
        if ((i2 & InputDeviceCompat.SOURCE_ANY) != 0 && i2 != ((byte) i2)) {
            throw new InstructionException("Byte value out of range " + HexUtil.toHex(i2 & 65535, 2) + "> 0xff", this);
        }
        this.valueBytes.put(i, (byte) i2);
    }

    int getByteUnsigned(int i) {
        return this.valueBytes.getByteUnsigned(i);
    }

    int getNibble(int i) {
        return (this.valueBytes.getByteUnsigned(i / 2) >> ((i % 2) * 4)) & 15;
    }

    void setNibble(int i, int i2) {
        if ((i2 & 15) != i2) {
            throw new InstructionException("Nibble value out of range " + HexUtil.toHex(i2, 1) + " > 0xf", this);
        }
        int i3 = i / 2;
        int i4 = (i % 2) * 4;
        this.valueBytes.put(i3, (byte) ((i2 << i4) | (this.valueBytes.getByteUnsigned(i3) & (i4 == 0 ? 240 : 15))));
    }

    @Override // com.reandroid.arsc.base.BlockContainer, com.reandroid.arsc.base.Block
    public int countBytes() {
        return this.valueBytes.countBytes();
    }

    @Override // com.reandroid.dex.base.FixedDexContainer, com.reandroid.arsc.base.BlockContainer, com.reandroid.arsc.base.Block
    public void onReadBytes(BlockReader blockReader) throws IOException {
        this.valueBytes.onReadBytes(blockReader);
        cacheSectionItem();
    }

    void cacheSectionItem() {
        InsIdSectionReference insIdSectionReference = this.sectionReference;
        if (insIdSectionReference != null) {
            insIdSectionReference.pullItem();
        }
    }

    public IdItem getSectionId() {
        InsIdSectionReference insIdSectionReference = this.sectionReference;
        if (insIdSectionReference != null) {
            return insIdSectionReference.getItem();
        }
        return null;
    }

    public void setSectionId(IdItem idItem) {
        this.sectionReference.setItem(idItem);
    }

    public Key getSectionIdKey() {
        IdItem sectionId = getSectionId();
        if (sectionId != null) {
            return sectionId.getKey();
        }
        return null;
    }

    public void setSectionIdKey(Key key) {
        this.sectionReference.setItem(key);
    }

    public int getData() {
        return getShortUnsigned(2);
    }

    public int getSignedData() {
        return getData();
    }

    public void setData(int i) {
        setShort(2, i);
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.reandroid.dex.ins.Ins
    public int getOutSize() {
        if (getOpcode().hasOutRegisters()) {
            return ((RegistersSet) this).getRegistersCount();
        }
        return 0;
    }

    @Override // com.reandroid.arsc.container.FixedBlockContainer, com.reandroid.arsc.base.BlockContainer
    protected void onRefreshed() {
        super.onRefreshed();
        InsIdSectionReference insIdSectionReference = this.sectionReference;
        if (insIdSectionReference != null) {
            insIdSectionReference.refresh();
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public RegistersIterator getRegistersIterator() {
        RegistersTable registersTable;
        if (!(this instanceof RegistersSet) || (registersTable = getRegistersTable()) == null) {
            return null;
        }
        return new RegistersIterator(registersTable, (RegistersSet) this);
    }

    private RegistersTable getRegistersTable() {
        InstructionList instructionList = (InstructionList) getParentInstance(InstructionList.class);
        if (instructionList != null) {
            return instructionList.getCodeItem();
        }
        return null;
    }

    @Override // com.reandroid.dex.ins.Ins
    public void appendCode(SmaliWriter smaliWriter) throws IOException {
        smaliWriter.append((CharSequence) getOpcode().getName());
        smaliWriter.append(StringUtil.SPACE);
        appendRegisters(smaliWriter);
        appendCodeData(smaliWriter);
    }

    void appendRegisters(SmaliWriter smaliWriter) throws IOException {
        RegistersIterator registersIterator = getRegistersIterator();
        if (registersIterator == null) {
            return;
        }
        boolean zHasOutRegisters = getOpcode().hasOutRegisters();
        if (zHasOutRegisters) {
            smaliWriter.append('{');
        }
        registersIterator.append(smaliWriter);
        if (zHasOutRegisters) {
            smaliWriter.append('}');
        }
    }

    void appendCodeData(SmaliWriter smaliWriter) throws IOException {
        smaliWriter.append(", ");
        IdItem sectionId = getSectionId();
        if (sectionId != null) {
            sectionId.append(smaliWriter);
        } else {
            appendHexData(smaliWriter);
        }
    }

    void appendHexData(SmaliWriter smaliWriter) throws IOException {
        smaliWriter.appendHex(getSignedData());
    }

    @Override // com.reandroid.dex.ins.Ins
    public void replaceKeys(Key key, Key key2) {
        Key keyReplaceKey;
        Key sectionIdKey = getSectionIdKey();
        if (sectionIdKey == null || sectionIdKey == (keyReplaceKey = sectionIdKey.replaceKey(key, key2))) {
            return;
        }
        setSectionIdKey(keyReplaceKey);
    }

    @Override // com.reandroid.dex.ins.Ins
    public Iterator<IdItem> usedIds() {
        return SingleIterator.of(getSectionId());
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.reandroid.dex.ins.Ins
    public void merge(Ins ins) {
        SizeXIns sizeXIns = (SizeXIns) ins;
        if (sizeXIns.getSectionType() == null) {
            this.valueBytes.set((byte[]) sizeXIns.valueBytes.getBytes().clone());
            return;
        }
        setSectionIdKey(sizeXIns.getSectionIdKey());
        this.sectionReference.validate();
        RegistersSet registersSet = (RegistersSet) sizeXIns;
        RegistersSet registersSet2 = (RegistersSet) this;
        int registersCount = registersSet.getRegistersCount();
        registersSet2.setRegistersCount(registersCount);
        for (int i = 0; i < registersCount; i++) {
            registersSet2.setRegister(i, registersSet.getRegister(i));
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.reandroid.dex.ins.Ins
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        SizeXIns sizeXIns = (SizeXIns) obj;
        if (getIndex() != sizeXIns.getIndex() || getOpcode() != sizeXIns.getOpcode()) {
            return false;
        }
        if ((this instanceof RegistersSet) && !areEqual((RegistersSet) this, (RegistersSet) sizeXIns)) {
            return false;
        }
        if (getSectionType() != null) {
            return Objects.equals(getSectionIdKey(), sizeXIns.getSectionIdKey());
        }
        return getData() == sizeXIns.getData();
    }

    /* JADX WARN: Multi-variable type inference failed */
    public int hashCode() {
        int data;
        int index = ((getIndex() + 1) * 31) + getOpcode().getValue();
        if (this instanceof RegistersSet) {
            RegistersSet registersSet = (RegistersSet) this;
            int registersCount = registersSet.getRegistersCount();
            index = (index * 31) + registersCount;
            for (int i = 0; i < registersCount; i++) {
                index = (index * 31) + registersSet.getRegister(i);
            }
        }
        int i2 = index * 31;
        Key sectionIdKey = getSectionIdKey();
        if (sectionIdKey != null) {
            data = sectionIdKey.hashCode();
        } else {
            data = getData();
        }
        return i2 + data;
    }

    @Override // com.reandroid.dex.ins.Ins
    public void fromSmali(SmaliInstruction smaliInstruction) throws IOException {
        try {
            validateOpcode(smaliInstruction);
            fromSmaliRegisters(smaliInstruction);
            fromSmaliKey(smaliInstruction);
            fromSmaliData(smaliInstruction);
        } catch (DexException e) {
            throw new SmaliValidateException(e.getMessage(), smaliInstruction);
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    private void fromSmaliRegisters(SmaliInstruction smaliInstruction) {
        if (this instanceof RegistersSet) {
            if (smaliInstruction.getRegistersTable() == null) {
                smaliInstruction.setRegistersTable(getRegistersTable());
            }
            int registersCount = smaliInstruction.getRegistersCount();
            RegistersSet registersSet = (RegistersSet) this;
            registersSet.setRegistersCount(registersCount);
            for (int i = 0; i < registersCount; i++) {
                registersSet.setRegister(i, smaliInstruction.getRegister(i).getValue());
            }
        }
    }

    private void fromSmaliKey(SmaliInstruction smaliInstruction) {
        if (getSectionType() == null) {
            return;
        }
        setSectionIdKey(smaliInstruction.getKey());
    }

    private void fromSmaliData(SmaliInstruction smaliInstruction) throws IOException {
        Number data = smaliInstruction.getData();
        if (data == null) {
            return;
        }
        if (data instanceof Long) {
            setLong(((Long) data).longValue());
        } else {
            setData(data.intValue());
        }
    }

    private static boolean areEqual(RegistersSet registersSet, RegistersSet registersSet2) {
        int registersCount;
        if (registersSet == registersSet2) {
            return true;
        }
        if (registersSet == null || (registersCount = registersSet.getRegistersCount()) != registersSet2.getRegistersCount()) {
            return false;
        }
        for (int i = 0; i < registersCount; i++) {
            if (registersSet.getRegister(i) != registersSet2.getRegister(i)) {
                return false;
            }
        }
        return true;
    }
}
