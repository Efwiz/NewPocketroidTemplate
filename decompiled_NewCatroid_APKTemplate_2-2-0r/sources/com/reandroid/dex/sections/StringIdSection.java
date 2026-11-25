package com.reandroid.dex.sections;

import com.reandroid.dex.base.IntegerPair;
import com.reandroid.dex.common.SectionItem;
import com.reandroid.dex.id.StringId;
import com.reandroid.dex.id.TypeId;
import com.reandroid.dex.key.Key;
import com.reandroid.dex.key.StringKey;
import com.reandroid.dex.key.TypeKey;
import com.reandroid.utils.collection.CollectionUtil;

/* JADX INFO: loaded from: classes11.dex */
public class StringIdSection extends IdSection<StringId> {
    StringIdSection(IntegerPair integerPair, SectionType<StringId> sectionType) {
        super(sectionType, new StringIdArray(integerPair, sectionType.getCreator()));
    }

    @Override // com.reandroid.dex.sections.Section
    public StringIdArray getItemArray() {
        return (StringIdArray) super.getItemArray();
    }

    @Override // com.reandroid.dex.sections.IdSection, com.reandroid.arsc.base.BlockContainer
    protected void onPreRefresh() {
        CollectionUtil.walk(Marker.parse(this));
        super.onPreRefresh();
    }

    @Override // com.reandroid.dex.sections.Section
    boolean keyChanged(SectionItem sectionItem, Key key) {
        char cCharAt;
        boolean zKeyChanged = super.keyChanged(sectionItem, key);
        if (key instanceof StringKey) {
            String string = ((StringKey) key).getString();
            if (string.length() > 0 && ((cCharAt = string.charAt(0)) == 'L' || cCharAt == '[')) {
                updateTypeId(new TypeKey(string));
            }
        }
        return zKeyChanged;
    }

    private void updateTypeId(TypeKey typeKey) {
        TypeId typeId;
        SectionList sectionList = getSectionList();
        if (sectionList == null || (typeId = (TypeId) sectionList.getSectionItem(SectionType.TYPE_ID, typeKey)) == null) {
            return;
        }
        typeId.getKey();
    }
}
