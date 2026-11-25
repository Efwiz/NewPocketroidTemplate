package com.google.crypto.tink.shaded.protobuf;

import java.lang.reflect.Field;

/* JADX INFO: loaded from: classes10.dex */
final class OneofInfo {
    private final Field caseField;

    /* JADX INFO: renamed from: id, reason: collision with root package name */
    private final int f156id;
    private final Field valueField;

    public OneofInfo(int i, Field field, Field field2) {
        this.f156id = i;
        this.caseField = field;
        this.valueField = field2;
    }

    public int getId() {
        return this.f156id;
    }

    public Field getCaseField() {
        return this.caseField;
    }

    public Field getValueField() {
        return this.valueField;
    }
}
