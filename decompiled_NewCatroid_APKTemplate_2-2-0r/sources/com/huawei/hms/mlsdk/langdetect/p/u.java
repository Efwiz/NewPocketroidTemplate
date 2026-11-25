package com.huawei.hms.mlsdk.langdetect.p;

import java.util.regex.Pattern;

/* JADX INFO: compiled from: UrlCharSequenceNormalizer.java */
/* JADX INFO: loaded from: classes10.dex */
public class u implements o {
    private static final Pattern a = Pattern.compile("(about|android|attachment|bitcoin|bitcoincash|chrome|chrome-extension|cvs|data|dns|ed2k|fax|feed|file|ftp|git|http|https|imap|irc|irc6|ircs|jar|jms|ldap|ldaps|magnet|mailto|mms|mongodb|mvn|news|nfs|nntp|proxy|redis|rediss|res|resource|rmi|rsync|rtmfp|rtmp|rtsp|sftp|shttp|sip|skype|sms|smtp|snmp|ssh|svn|telnet|tv|udp|urn|view-source|ws|wss):(//)?[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]");

    @Override // com.huawei.hms.mlsdk.langdetect.p.o
    public String a(String str) {
        return a.matcher(str).replaceAll(" ");
    }
}
