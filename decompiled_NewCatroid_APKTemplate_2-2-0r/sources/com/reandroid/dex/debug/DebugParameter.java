package com.reandroid.dex.debug;

import com.reandroid.dex.data.DebugInfo;
import com.reandroid.dex.id.IdItem;
import com.reandroid.dex.id.StringId;
import com.reandroid.dex.key.StringKey;
import com.reandroid.dex.reference.Base1Ule128IdItemReference;
import com.reandroid.dex.sections.SectionType;
import com.reandroid.dex.smali.SmaliFormat;
import com.reandroid.dex.smali.SmaliWriter;
import com.reandroid.utils.collection.SingleIterator;
import java.io.IOException;
import java.util.Iterator;

/* JADX INFO: loaded from: classes11.dex */
public class DebugParameter extends Base1Ule128IdItemReference<StringId> implements SmaliFormat {
    public DebugParameter() {
        super(SectionType.STRING_ID);
    }

    public String getName() {
        StringId nameId = getNameId();
        if (nameId != null) {
            return nameId.getString();
        }
        return null;
    }

    public void setName(String str) {
        if (str == null || str.length() == 0) {
            setItem((IdItem) null);
        } else {
            setItem(new StringKey(str));
        }
    }

    public StringId getNameId() {
        if (isRemoved()) {
            return null;
        }
        return (StringId) getItem();
    }

    public boolean isRemoved() {
        DebugInfo debugInfo = (DebugInfo) getParentInstance(DebugInfo.class);
        if (debugInfo != null) {
            return debugInfo.isRemoved();
        }
        return true;
    }

    @Override // com.reandroid.dex.reference.Base1Ule128IdItemReference, com.reandroid.dex.smali.SmaliFormat
    public void append(SmaliWriter smaliWriter) throws IOException {
        StringId nameId = getNameId();
        if (nameId == null) {
            return;
        }
        smaliWriter.append(", ");
        nameId.append(smaliWriter);
    }

    public Iterator<IdItem> usedIds() {
        return SingleIterator.of(getItem());
    }

    public void merge(DebugParameter debugParameter) {
        setItem(debugParameter.getKey());
    }

    @Override // com.reandroid.dex.reference.Base1Ule128IdItemReference, com.reandroid.dex.base.Le128
    public String toString() {
        if (getItem() == null) {
            return super.toString();
        }
        return ".param p **, \"" + getNameId() + "\"";
    }
}
