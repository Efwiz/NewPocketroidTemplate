package com.huawei.hms.mlsdk.langdetect.p;

import java.util.regex.Pattern;

/* JADX INFO: compiled from: MailCharSequenceNormalizer.java */
/* JADX INFO: loaded from: classes10.dex */
public class t implements o {
    private static final Pattern a = Pattern.compile("([a-zA-Z0-9_!#$%&’*+/=?`{|}~^-]+(?:\\.[a-zA-Z0-9_!#$%&’*+/=?`{|}~^-]+)*|\".+\")@[-_0-9A-Za-z]+[-_.0-9A-Za-z]+");

    @Override // com.huawei.hms.mlsdk.langdetect.p.o
    public String a(String str) {
        return a.matcher(str).replaceAll(" ");
    }
}
