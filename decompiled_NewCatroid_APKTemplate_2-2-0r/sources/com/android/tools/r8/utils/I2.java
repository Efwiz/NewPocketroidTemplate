package com.android.tools.r8.utils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.function.Consumer;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes8.dex */
public final class I2 {
    static final /* synthetic */ boolean e = true;
    private final String a;
    private final int b;
    private boolean c;
    private final J2 d;

    private I2(String str, int i, J2 j2) {
        this.c = false;
        this.a = str;
        this.b = i;
        this.d = j2;
    }

    public final J2 a() {
        return this.d;
    }

    public final I2 b() {
        this.c = true;
        return this;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public H2 a(H2 h2) {
        int i = this.b;
        if (i == 1) {
            return new F2(this.a, h2, new Consumer() { // from class: com.android.tools.r8.utils.I2$$ExternalSyntheticLambda0
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    this.f$0.a((Field) obj);
                }
            });
        }
        if (e || i == 2) {
            return new G2(this.a, h2, new Consumer() { // from class: com.android.tools.r8.utils.I2$$ExternalSyntheticLambda1
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    this.f$0.a((Method) obj);
                }
            });
        }
        throw new AssertionError();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(Field field) {
        if (this.c) {
            field.setAccessible(true);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(Method method) {
        if (this.c) {
            method.setAccessible(true);
        }
    }
}
