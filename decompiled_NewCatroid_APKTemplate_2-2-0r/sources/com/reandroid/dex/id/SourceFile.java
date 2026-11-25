package com.reandroid.dex.id;

import com.reandroid.dex.base.UsageMarker;
import com.reandroid.dex.reference.IndirectStringReference;
import com.reandroid.dex.smali.SmaliDirective;
import com.reandroid.dex.smali.SmaliRegion;
import com.reandroid.dex.smali.SmaliWriter;
import com.reandroid.utils.ObjectsUtil;
import java.io.IOException;

/* JADX INFO: loaded from: classes11.dex */
public class SourceFile extends IndirectStringReference implements SmaliRegion {
    public static final String SourceFile = ObjectsUtil.of("SourceFile");

    @Override // com.reandroid.dex.reference.IdItemIndirectReference, com.reandroid.dex.reference.IdReference
    public void checkNonNullItem(StringId stringId) {
    }

    @Override // com.reandroid.dex.reference.IdReference
    public void checkNonNullItem(StringId stringId, int i) {
    }

    SourceFile(ClassId classId, int i) {
        super(classId, i, UsageMarker.USAGE_SOURCE);
    }

    @Override // com.reandroid.arsc.item.IndirectItem
    public ClassId getBlockItem() {
        return (ClassId) super.getBlockItem();
    }

    @Override // com.reandroid.dex.smali.SmaliFormat
    public void append(SmaliWriter smaliWriter) throws IOException {
        StringId stringId = (StringId) getItem();
        if (stringId == null) {
            return;
        }
        smaliWriter.newLine();
        getSmaliDirective().append(smaliWriter);
        stringId.append(smaliWriter);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.reandroid.dex.reference.IdItemIndirectReference
    public int getItemIndex(StringId stringId) {
        if (stringId == null) {
            return -1;
        }
        return stringId.getIdx();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.reandroid.dex.reference.IdItemIndirectReference
    public StringId pullItem(int i) {
        if (i == -1) {
            return null;
        }
        return (StringId) super.pullItem(i);
    }

    @Override // com.reandroid.dex.smali.SmaliRegion
    public SmaliDirective getSmaliDirective() {
        return SmaliDirective.SOURCE;
    }

    @Override // com.reandroid.dex.reference.IdItemIndirectReference
    public String toString() {
        return SmaliWriter.toStringSafe(this);
    }
}
