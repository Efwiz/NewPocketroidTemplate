package com.google.firebase.crashlytics.internal.common;

/* JADX INFO: loaded from: classes10.dex */
public enum DeliveryMechanism {
    DEVELOPER(1),
    USER_SIDELOAD(2),
    TEST_DISTRIBUTION(3),
    APP_STORE(4);


    /* JADX INFO: renamed from: id, reason: collision with root package name */
    private final int f162id;

    DeliveryMechanism(int i) {
        this.f162id = i;
    }

    public int getId() {
        return this.f162id;
    }

    @Override // java.lang.Enum
    public String toString() {
        return Integer.toString(this.f162id);
    }

    public static DeliveryMechanism determineFrom(String str) {
        return str != null ? APP_STORE : DEVELOPER;
    }
}
