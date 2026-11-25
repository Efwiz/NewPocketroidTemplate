package com.reandroid.dex.debug;

import com.reandroid.dex.id.IdItem;
import com.reandroid.dex.id.StringId;
import com.reandroid.dex.id.TypeId;
import com.reandroid.dex.key.StringKey;
import com.reandroid.dex.key.TypeKey;
import com.reandroid.dex.reference.Base1Ule128IdItemReference;
import com.reandroid.dex.sections.SectionType;
import com.reandroid.dex.smali.SmaliWriter;
import com.reandroid.dex.smali.model.SmaliDebugElement;
import com.reandroid.dex.smali.model.SmaliDebugLocal;
import com.reandroid.utils.collection.CombiningIterator;
import com.reandroid.utils.collection.SingleIterator;
import java.io.IOException;
import java.util.Iterator;
import java.util.Objects;

/* JADX INFO: loaded from: classes11.dex */
public class DebugStartLocal extends DebugRegisterNumber {
    private final Base1Ule128IdItemReference<StringId> mName;
    private final Base1Ule128IdItemReference<TypeId> mType;

    @Override // com.reandroid.dex.debug.DebugRegisterNumber
    public /* bridge */ /* synthetic */ int getRegisterNumber() {
        return super.getRegisterNumber();
    }

    @Override // com.reandroid.dex.debug.DebugRegisterNumber
    public /* bridge */ /* synthetic */ void setRegister(int i) {
        super.setRegister(i);
    }

    DebugStartLocal(int i, int i2) {
        super(i + 2, i2);
        Base1Ule128IdItemReference<StringId> base1Ule128IdItemReference = new Base1Ule128IdItemReference<>(SectionType.STRING_ID);
        this.mName = base1Ule128IdItemReference;
        Base1Ule128IdItemReference<TypeId> base1Ule128IdItemReference2 = new Base1Ule128IdItemReference<>(SectionType.TYPE_ID);
        this.mType = base1Ule128IdItemReference2;
        addChild(2, base1Ule128IdItemReference);
        addChild(3, base1Ule128IdItemReference2);
    }

    DebugStartLocal(int i, DebugElementType<?> debugElementType) {
        this(i, debugElementType.getFlag());
    }

    public DebugStartLocal() {
        this(0, DebugElementType.START_LOCAL.getFlag());
    }

    @Override // com.reandroid.dex.debug.DebugElement
    public boolean isValid() {
        return (isRemoved() || this.mName.getItem() == null || this.mType.getItem() == null) ? false : true;
    }

    public String getName() {
        StringId stringId = (StringId) this.mName.getItem();
        if (stringId != null) {
            return stringId.getString();
        }
        return null;
    }

    public StringKey getNameKey() {
        return (StringKey) this.mName.getKey();
    }

    public void setName(String str) {
        setName(StringKey.create(str));
    }

    public void setName(StringKey stringKey) {
        this.mName.setItem(stringKey);
    }

    public String getType() {
        TypeId typeId = (TypeId) this.mType.getItem();
        if (typeId != null) {
            return typeId.getName();
        }
        return null;
    }

    public TypeId getTypeId() {
        return (TypeId) this.mType.getItem();
    }

    public TypeKey getTypeKey() {
        return (TypeKey) this.mType.getKey();
    }

    public void setType(String str) {
        this.mType.setItem(TypeKey.create(str));
    }

    public void setType(TypeKey typeKey) {
        this.mType.setItem(typeKey);
    }

    @Override // com.reandroid.dex.debug.DebugRegisterNumber, com.reandroid.dex.debug.DebugElement, com.reandroid.dex.ins.ExtraLine
    public void appendExtra(SmaliWriter smaliWriter) throws IOException {
        if (isValid()) {
            getSmaliDirective().append(smaliWriter);
            smaliWriter.appendRegister(getRegisterNumber());
            smaliWriter.append(", ");
            this.mName.append(smaliWriter);
            smaliWriter.append(':');
            this.mType.append(smaliWriter);
        }
    }

    @Override // com.reandroid.dex.debug.DebugElement
    public DebugElementType<? extends DebugStartLocal> getElementType() {
        return DebugElementType.START_LOCAL;
    }

    @Override // com.reandroid.dex.debug.DebugElement
    public Iterator<IdItem> usedIds() {
        return CombiningIterator.two(SingleIterator.of(this.mName.getItem()), SingleIterator.of(this.mType.getItem()));
    }

    @Override // com.reandroid.dex.debug.DebugRegisterNumber, com.reandroid.dex.debug.DebugElement
    public void merge(DebugElement debugElement) {
        super.merge(debugElement);
        DebugStartLocal debugStartLocal = (DebugStartLocal) debugElement;
        this.mName.setItem(debugStartLocal.mName.getKey());
        this.mType.setItem(debugStartLocal.mType.getKey());
    }

    @Override // com.reandroid.dex.debug.DebugRegisterNumber, com.reandroid.dex.debug.DebugElement
    public void fromSmali(SmaliDebugElement smaliDebugElement) throws IOException {
        super.fromSmali(smaliDebugElement);
        SmaliDebugLocal smaliDebugLocal = (SmaliDebugLocal) smaliDebugElement;
        setName(smaliDebugLocal.getName());
        setType(smaliDebugLocal.getType());
    }

    @Override // com.reandroid.dex.debug.DebugRegisterNumber, com.reandroid.dex.debug.DebugElement
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        DebugStartLocal debugStartLocal = (DebugStartLocal) obj;
        return getFlag() == debugStartLocal.getFlag() && Objects.equals(getName(), debugStartLocal.getName()) && Objects.equals(getType(), debugStartLocal.getType());
    }

    @Override // com.reandroid.dex.debug.DebugRegisterNumber, com.reandroid.dex.debug.DebugElement
    public int hashCode() {
        int flag = getFlag() * 31;
        String name2 = getName();
        if (name2 != null) {
            flag += name2.hashCode();
        }
        int i = flag * 31;
        String type = getType();
        return type != null ? i + type.hashCode() : i;
    }

    @Override // com.reandroid.dex.debug.DebugRegisterNumber, com.reandroid.dex.debug.DebugElement
    public String toString() {
        return super.toString() + ", " + this.mName + ':' + this.mType;
    }
}
