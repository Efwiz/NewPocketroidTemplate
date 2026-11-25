package com.reandroid.dex.data;

import com.reandroid.arsc.base.Creator;
import com.reandroid.arsc.item.IntegerReference;
import com.reandroid.dex.smali.model.SmaliMethod;
import java.io.IOException;
import java.util.Iterator;

/* JADX INFO: loaded from: classes11.dex */
public class MethodDefArray extends DefArray<MethodDef> {
    private static final Creator<MethodDef> CREATOR = new Creator<MethodDef>() { // from class: com.reandroid.dex.data.MethodDefArray.1
        @Override // com.reandroid.arsc.base.Creator, com.reandroid.arsc.base.BlockArrayCreator
        public MethodDef[] newArrayInstance(int i) {
            return new MethodDef[i];
        }

        @Override // com.reandroid.arsc.base.Creator, com.reandroid.arsc.base.BlockCreator
        public MethodDef newInstance() {
            return new MethodDef();
        }
    };

    public MethodDefArray(IntegerReference integerReference) {
        super(integerReference, CREATOR);
    }

    @Override // com.reandroid.dex.data.DefArray
    void sortAnnotations() {
        AnnotationsDirectory annotationsDirectory = getAnnotationsDirectory();
        if (annotationsDirectory != null) {
            annotationsDirectory.sortMethods();
        }
    }

    public void fromSmali(Iterator<SmaliMethod> it) throws IOException {
        while (it.hasNext()) {
            fromSmali(it.next());
        }
    }

    public void fromSmali(SmaliMethod smaliMethod) throws IOException {
        ((MethodDef) createNext()).fromSmali(smaliMethod);
    }
}
