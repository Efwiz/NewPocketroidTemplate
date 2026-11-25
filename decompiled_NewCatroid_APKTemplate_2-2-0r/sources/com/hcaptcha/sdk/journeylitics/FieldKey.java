package com.hcaptcha.sdk.journeylitics;

import com.android.dx.rop.code.RegisterSpec;
import com.google.android.gms.cast.HlsSegmentFormat;
import org.jetbrains.kotlin.load.java.JvmAnnotationNames;

/* JADX INFO: compiled from: r8-map-id-071b77f92f575d77028d4852bce4d6e5f9d65cb60a14a6342bd357f80a49e8cf */
/* JADX INFO: loaded from: classes10.dex */
enum FieldKey {
    KIND(JvmAnnotationNames.KIND_FIELD_NAME),
    VIEW(RegisterSpec.PREFIX),
    TIMESTAMP(HlsSegmentFormat.TS),
    META("m"),
    ID("id"),
    SCREEN("sc"),
    ACTION("ac"),
    VALUE("val"),
    X("x"),
    Y("y"),
    INDEX("idx"),
    SECTION("sct"),
    ITEM("it"),
    TARGET("tt"),
    CONTROL("ct"),
    GESTURE("gt"),
    STATE("gs"),
    TAPS("tap"),
    CONTAINER_VIEW("cv"),
    LENGTH("ln"),
    COMPOSE("comp");

    private final String jsonKey;

    FieldKey(String str) {
        this.jsonKey = str;
    }

    public String getJsonKey() {
        return this.jsonKey;
    }
}
