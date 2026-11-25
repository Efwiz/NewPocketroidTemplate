package com.koushikdutta.async.http.spdy;

import com.huawei.hms.ml.language.common.utils.LanguageCodeUtil;
import com.koushikdutta.async.http.Protocol;
import java.util.List;
import java.util.Locale;

/* JADX INFO: loaded from: classes10.dex */
final class SpdyTransport {
    private static final List<String> SPDY_3_PROHIBITED_HEADERS = Util.immutableList("connection", "host", "keep-alive", "proxy-connection", "transfer-encoding");
    private static final List<String> HTTP_2_PROHIBITED_HEADERS = Util.immutableList("connection", "host", "keep-alive", "proxy-connection", LanguageCodeUtil.TE, "transfer-encoding", "encoding", "upgrade");

    SpdyTransport() {
    }

    static boolean isProhibitedHeader(Protocol protocol, String str) {
        if (protocol == Protocol.SPDY_3) {
            return SPDY_3_PROHIBITED_HEADERS.contains(str.toLowerCase(Locale.US));
        }
        if (protocol == Protocol.HTTP_2) {
            return HTTP_2_PROHIBITED_HEADERS.contains(str.toLowerCase(Locale.US));
        }
        throw new AssertionError(protocol);
    }
}
