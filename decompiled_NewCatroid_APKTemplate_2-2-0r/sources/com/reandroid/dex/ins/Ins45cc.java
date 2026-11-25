package com.reandroid.dex.ins;

import com.reandroid.dex.id.ProtoId;
import com.reandroid.dex.key.ProtoKey;
import com.reandroid.dex.sections.SectionType;
import com.reandroid.dex.smali.SmaliWriter;
import java.io.IOException;

/* JADX INFO: loaded from: classes11.dex */
public class Ins45cc extends Size8Ins implements RegistersSet {
    private ProtoId mProtoId;

    @Override // com.reandroid.dex.ins.RegistersSet
    public int getRegisterLimit(int i) {
        return 15;
    }

    public Ins45cc(Opcode<?> opcode) {
        super(opcode);
    }

    public ProtoId getProtoId() {
        return this.mProtoId;
    }

    public void setProtoId(ProtoKey protoKey) {
        setProtoId((ProtoId) getSection(SectionType.PROTO_ID).getOrCreate(protoKey));
    }

    public void setProtoId(ProtoId protoId) {
        setShort(6, protoId.getIndex());
        this.mProtoId = protoId;
    }

    @Override // com.reandroid.dex.ins.RegistersSet
    public int getRegistersCount() {
        return getNibble(3);
    }

    @Override // com.reandroid.dex.ins.RegistersSet
    public void setRegistersCount(int i) {
        setNibble(3, i);
    }

    @Override // com.reandroid.dex.ins.RegistersSet
    public int getRegister(int i) {
        if (i < 4) {
            return getNibble(i + 8);
        }
        return getNibble(2);
    }

    @Override // com.reandroid.dex.ins.RegistersSet
    public void setRegister(int i, int i2) {
        if (i < 4) {
            setNibble(i + 8, i2);
        } else {
            setNibble(2, i2);
        }
    }

    @Override // com.reandroid.dex.ins.SizeXIns
    public int getData() {
        return getShortUnsigned(2);
    }

    @Override // com.reandroid.dex.ins.SizeXIns
    public void setData(int i) {
        setShort(2, i);
    }

    public int getProtoIndex() {
        return getShortUnsigned(6);
    }

    public void setProtoIndex(int i) {
        setShort(6, i);
        cacheProto();
    }

    @Override // com.reandroid.dex.ins.SizeXIns
    void cacheSectionItem() {
        super.cacheSectionItem();
        cacheProto();
    }

    @Override // com.reandroid.dex.ins.SizeXIns, com.reandroid.arsc.container.FixedBlockContainer, com.reandroid.arsc.base.BlockContainer
    protected void onRefreshed() {
        super.onRefreshed();
        refreshProtoId();
    }

    private void refreshProtoId() {
        setProtoIndex(((ProtoId) this.mProtoId.getReplace()).getIndex());
    }

    private void cacheProto() {
        this.mProtoId = (ProtoId) getSectionItem(SectionType.PROTO_ID, getProtoIndex());
    }

    @Override // com.reandroid.dex.ins.SizeXIns, com.reandroid.dex.ins.Ins
    public void appendCode(SmaliWriter smaliWriter) throws IOException {
        super.appendCode(smaliWriter);
        smaliWriter.append(", ");
        getProtoId().append(smaliWriter);
    }
}
