package com.reandroid.dex.ins;

import com.reandroid.dex.id.StringId;
import com.reandroid.dex.key.StringKey;

/* JADX INFO: loaded from: classes11.dex */
public class InsConstString extends Ins21c implements ConstString {
    public InsConstString() {
        super(Opcode.CONST_STRING);
    }

    @Override // com.reandroid.dex.ins.ConstString
    public String getString() {
        StringId stringId = getStringId();
        if (stringId != null) {
            return stringId.getString();
        }
        return null;
    }

    @Override // com.reandroid.dex.ins.ConstString
    public void setString(StringKey stringKey) {
        super.setSectionIdKey(stringKey);
    }

    @Override // com.reandroid.dex.ins.RegistersSet
    public int getRegister() {
        return getRegister(0);
    }

    @Override // com.reandroid.dex.ins.RegistersSet
    public void setRegister(int i) {
        setRegister(0, i);
    }

    public StringId getStringId() {
        return getSectionId();
    }

    @Override // com.reandroid.dex.ins.SizeXIns
    public StringId getSectionId() {
        return (StringId) super.getSectionId();
    }

    public InsConstStringJumbo toConstStringJumbo() {
        StringId sectionId = getSectionId();
        if (sectionId == null || !needsConvertToConstStringJumbo(sectionId)) {
            return null;
        }
        InsConstStringJumbo insConstStringJumbo = (InsConstStringJumbo) Opcode.CONST_STRING_JUMBO.newInstance();
        insConstStringJumbo.setRegister(getRegister());
        replace(insConstStringJumbo);
        insConstStringJumbo.setSectionId(sectionId);
        return insConstStringJumbo;
    }

    private boolean needsConvertToConstStringJumbo(StringId stringId) {
        int index = stringId.getIndex();
        return index != (65535 & index);
    }
}
