package org.apache.commons.compress.harmony.unpack200.bytecode;

import com.huawei.hms.ml.language.common.utils.Constant;
import java.util.List;

/* JADX INFO: loaded from: classes13.dex */
public class CPField extends CPMember {
    public CPField(CPUTF8 cputf8, CPUTF8 cputf9, long j, List<Attribute> list) {
        super(cputf8, cputf9, j, list);
    }

    @Override // org.apache.commons.compress.harmony.unpack200.bytecode.CPMember, org.apache.commons.compress.harmony.unpack200.bytecode.ClassFileEntry
    public String toString() {
        return "Field: " + this.f454name + "(" + this.descriptor + Constant.AFTER_QUTO;
    }
}
