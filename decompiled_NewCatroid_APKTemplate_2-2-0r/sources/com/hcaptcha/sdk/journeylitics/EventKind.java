package com.hcaptcha.sdk.journeylitics;

import com.fasterxml.jackson.annotation.JsonValue;

/* JADX INFO: compiled from: r8-map-id-071b77f92f575d77028d4852bce4d6e5f9d65cb60a14a6342bd357f80a49e8cf */
/* JADX INFO: loaded from: classes10.dex */
enum EventKind {
    screen("screen"),
    click("click"),
    drag("drag"),
    gesture("gesture"),
    edit("edit");

    private final String value;

    EventKind(String str) {
        this.value = str;
    }

    @JsonValue
    public String getValue() {
        return this.value;
    }
}
