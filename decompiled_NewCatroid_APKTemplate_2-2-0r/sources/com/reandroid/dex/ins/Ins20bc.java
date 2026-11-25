package com.reandroid.dex.ins;

import com.reandroid.dex.id.IdItem;
import com.reandroid.dex.sections.SectionType;

/* JADX INFO: loaded from: classes11.dex */
public class Ins20bc extends Size4Ins {
    public Ins20bc(Opcode<?> opcode) {
        super(opcode);
    }

    public int getVerificationError() {
        return getByteUnsigned(1) & 63;
    }

    public void setVerificationError(int i) {
        setByte(1, (i & 63) | (getByteUnsigned(1) & 192));
    }

    public int getReferenceTypeValue() {
        return (getByteUnsigned(1) >> 6) & 3;
    }

    public void setReferenceTypeValue(int i) {
        setByte(1, ((i & 3) << 6) | (getByteUnsigned(1) & 63));
    }

    @Override // com.reandroid.dex.ins.SizeXIns
    public SectionType<? extends IdItem> getSectionType() {
        return SectionType.getReferenceType(getReferenceTypeValue());
    }

    public void setSectionType(SectionType<?> sectionType) {
        setReferenceTypeValue(sectionType.getReferenceType());
    }

    @Override // com.reandroid.dex.ins.Size4Ins, com.reandroid.dex.ins.SizeXIns
    public int getData() {
        return getShortUnsigned(2);
    }

    @Override // com.reandroid.dex.ins.Size4Ins, com.reandroid.dex.ins.SizeXIns
    public void setData(int i) {
        setShort(2, i);
    }
}
