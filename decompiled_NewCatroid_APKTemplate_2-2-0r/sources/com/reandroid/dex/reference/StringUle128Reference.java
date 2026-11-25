package com.reandroid.dex.reference;

import com.reandroid.arsc.base.BlockRefresh;
import com.reandroid.dex.id.StringId;
import com.reandroid.dex.key.StringKey;
import com.reandroid.dex.sections.SectionType;
import com.reandroid.utils.CompareUtil;

/* JADX INFO: loaded from: classes11.dex */
public class StringUle128Reference extends Ule128IdItemReference<StringId> implements BlockRefresh, Comparable<StringUle128Reference> {
    public StringUle128Reference(int i) {
        super(SectionType.STRING_ID, i);
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

    @Override // java.lang.Comparable
    public int compareTo(StringUle128Reference stringUle128Reference) {
        if (stringUle128Reference == null) {
            return -1;
        }
        return CompareUtil.compare((Comparable) getItem(), (Comparable) stringUle128Reference.getItem());
    }
}
