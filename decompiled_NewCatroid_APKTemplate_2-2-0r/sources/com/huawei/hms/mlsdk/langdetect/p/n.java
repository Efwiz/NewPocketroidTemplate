package com.huawei.hms.mlsdk.langdetect.p;

import com.huawei.hms.ml.common.utils.SmartLog;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Pattern;

/* JADX INFO: compiled from: ApplyFilterCharSequence.java */
/* JADX INFO: loaded from: classes10.dex */
public class n implements o {
    private static final Pattern b = Pattern.compile("^((?!\\p{L}).)*$");
    private List<o> a = new ArrayList();

    public n a(o oVar) {
        this.a.add(oVar);
        return this;
    }

    @Override // com.huawei.hms.mlsdk.langdetect.p.o
    public String a(String str) {
        Iterator<o> it = this.a.iterator();
        String strA = str;
        while (it.hasNext()) {
            strA = it.next().a(strA);
        }
        String strTrim = strA.replaceFirst("^((?=[\\p{Po}]).)+", "").replaceFirst("((?=[\\p{Po}]).)+$", "").trim();
        String str2 = b.matcher(strTrim).matches() ? "" : strTrim;
        SmartLog.d("ApplyFilterCharSequence", "input=" + str);
        SmartLog.d("ApplyFilterCharSequence", "output=" + str2);
        return str2;
    }
}
