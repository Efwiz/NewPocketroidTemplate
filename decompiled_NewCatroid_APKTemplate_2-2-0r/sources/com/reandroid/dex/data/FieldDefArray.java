package com.reandroid.dex.data;

import com.reandroid.arsc.base.Creator;
import com.reandroid.arsc.item.IntegerReference;
import com.reandroid.dex.smali.model.SmaliField;
import java.util.Iterator;

/* JADX INFO: loaded from: classes11.dex */
public class FieldDefArray extends DefArray<FieldDef> {
    private static final Creator<FieldDef> CREATOR = new Creator<FieldDef>() { // from class: com.reandroid.dex.data.FieldDefArray.1
        @Override // com.reandroid.arsc.base.Creator, com.reandroid.arsc.base.BlockArrayCreator
        public FieldDef[] newArrayInstance(int i) {
            return new FieldDef[i];
        }

        @Override // com.reandroid.arsc.base.Creator, com.reandroid.arsc.base.BlockCreator
        public FieldDef newInstance() {
            return new FieldDef();
        }
    };

    public FieldDefArray(IntegerReference integerReference) {
        super(integerReference, CREATOR);
    }

    @Override // com.reandroid.dex.data.DefArray
    void sortAnnotations() {
        AnnotationsDirectory annotationsDirectory = getAnnotationsDirectory();
        if (annotationsDirectory != null) {
            annotationsDirectory.sortFields();
        }
    }

    public void fromSmali(Iterator<SmaliField> it) {
        while (it.hasNext()) {
            ((FieldDef) createNext()).fromSmali(it.next());
        }
    }

    public void fromSmali(SmaliField smaliField) {
        ((FieldDef) createNext()).fromSmali(smaliField);
    }
}
