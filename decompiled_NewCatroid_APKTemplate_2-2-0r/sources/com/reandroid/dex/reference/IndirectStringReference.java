package com.reandroid.dex.reference;

import com.reandroid.dex.common.SectionItem;
import com.reandroid.dex.id.StringId;
import com.reandroid.dex.key.StringKey;
import com.reandroid.dex.sections.SectionType;

/* JADX INFO: loaded from: classes11.dex */
public class IndirectStringReference extends IdItemIndirectReference<StringId> {
    public IndirectStringReference(SectionItem sectionItem, int i, int i2) {
        super(SectionType.STRING_ID, sectionItem, i, i2);
    }

    public String getString() {
        StringId stringId = (StringId) getItem();
        if (stringId != null) {
            return stringId.getString();
        }
        return null;
    }

    public void setString(String str) {
        setItem(StringKey.create(str));
    }

    public static boolean equals(IndirectStringReference indirectStringReference, IndirectStringReference indirectStringReference2) {
        if (indirectStringReference == indirectStringReference2) {
            return true;
        }
        if (indirectStringReference == null) {
            return false;
        }
        return StringId.equals((StringId) indirectStringReference.getItem(), (StringId) indirectStringReference2.getItem());
    }
}
