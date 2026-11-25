package com.reandroid.dex.debug;

import com.reandroid.dex.id.IdItem;
import com.reandroid.dex.id.StringId;
import com.reandroid.dex.key.StringKey;
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
public class DebugStartLocalExtended extends DebugStartLocal {
    private final Base1Ule128IdItemReference<StringId> mSignature;

    public DebugStartLocalExtended() {
        super(1, DebugElementType.START_LOCAL_EXTENDED);
        Base1Ule128IdItemReference<StringId> base1Ule128IdItemReference = new Base1Ule128IdItemReference<>(SectionType.STRING_ID);
        this.mSignature = base1Ule128IdItemReference;
        addChild(4, base1Ule128IdItemReference);
    }

    @Override // com.reandroid.dex.debug.DebugStartLocal, com.reandroid.dex.debug.DebugElement
    public boolean isValid() {
        return (isRemoved() || this.mSignature.getItem() == null) ? false : true;
    }

    public String getSignature() {
        StringId stringId = (StringId) this.mSignature.getItem();
        if (stringId != null) {
            return stringId.getString();
        }
        return null;
    }

    public StringKey getSignatureKey() {
        return (StringKey) this.mSignature.getKey();
    }

    public void setSignature(String str) {
        this.mSignature.setItem(StringKey.create(str));
    }

    public void setSignature(StringKey stringKey) {
        this.mSignature.setItem(stringKey);
    }

    @Override // com.reandroid.dex.debug.DebugStartLocal, com.reandroid.dex.debug.DebugRegisterNumber, com.reandroid.dex.debug.DebugElement, com.reandroid.dex.ins.ExtraLine
    public void appendExtra(SmaliWriter smaliWriter) throws IOException {
        if (isValid()) {
            super.appendExtra(smaliWriter);
            smaliWriter.append(", ");
            this.mSignature.append(smaliWriter);
        }
    }

    @Override // com.reandroid.dex.debug.DebugStartLocal, com.reandroid.dex.debug.DebugElement
    public DebugElementType<DebugStartLocalExtended> getElementType() {
        return DebugElementType.START_LOCAL_EXTENDED;
    }

    @Override // com.reandroid.dex.debug.DebugStartLocal, com.reandroid.dex.debug.DebugElement
    public Iterator<IdItem> usedIds() {
        return CombiningIterator.two(super.usedIds(), SingleIterator.of(this.mSignature.getItem()));
    }

    @Override // com.reandroid.dex.debug.DebugStartLocal, com.reandroid.dex.debug.DebugRegisterNumber, com.reandroid.dex.debug.DebugElement
    public void merge(DebugElement debugElement) {
        super.merge(debugElement);
        this.mSignature.setItem(((DebugStartLocalExtended) debugElement).mSignature.getKey());
    }

    @Override // com.reandroid.dex.debug.DebugStartLocal, com.reandroid.dex.debug.DebugRegisterNumber, com.reandroid.dex.debug.DebugElement
    public void fromSmali(SmaliDebugElement smaliDebugElement) throws IOException {
        super.fromSmali(smaliDebugElement);
        setSignature(((SmaliDebugLocal) smaliDebugElement).getSignature());
    }

    @Override // com.reandroid.dex.debug.DebugStartLocal, com.reandroid.dex.debug.DebugRegisterNumber, com.reandroid.dex.debug.DebugElement
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        DebugStartLocalExtended debugStartLocalExtended = (DebugStartLocalExtended) obj;
        return getFlag() == debugStartLocalExtended.getFlag() && Objects.equals(getName(), debugStartLocalExtended.getName()) && Objects.equals(getType(), debugStartLocalExtended.getType()) && Objects.equals(getSignature(), debugStartLocalExtended.getSignature());
    }

    @Override // com.reandroid.dex.debug.DebugStartLocal, com.reandroid.dex.debug.DebugRegisterNumber, com.reandroid.dex.debug.DebugElement
    public int hashCode() {
        int flag = getFlag() * 31;
        String name2 = getName();
        if (name2 != null) {
            flag += name2.hashCode();
        }
        int iHashCode = flag * 31;
        String type = getType();
        if (type != null) {
            iHashCode += type.hashCode();
        }
        int i = iHashCode * 31;
        String signature = getSignature();
        return signature != null ? i + signature.hashCode() : i;
    }

    @Override // com.reandroid.dex.debug.DebugStartLocal, com.reandroid.dex.debug.DebugRegisterNumber, com.reandroid.dex.debug.DebugElement
    public String toString() {
        return super.toString() + ", " + this.mSignature;
    }
}
