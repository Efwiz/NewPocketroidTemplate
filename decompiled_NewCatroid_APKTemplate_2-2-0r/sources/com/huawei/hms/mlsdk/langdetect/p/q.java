package com.huawei.hms.mlsdk.langdetect.p;

import java.util.regex.Pattern;

/* JADX INFO: compiled from: DomainCharSequenceNormalizer.java */
/* JADX INFO: loaded from: classes10.dex */
public class q implements o {
    private static final Pattern a = Pattern.compile("(https?://)?(www\\.)?[a-zA-Z0-9][a-zA-Z0-9-]{0,62}\\.(com\\.cn|net\\.cn|org\\.cn|gov\\.cn|edu\\.cn|com|net|org|gov|edu|cn|mil|int)");

    @Override // com.huawei.hms.mlsdk.langdetect.p.o
    public String a(String str) {
        return a.matcher(str).replaceAll(" ");
    }
}
