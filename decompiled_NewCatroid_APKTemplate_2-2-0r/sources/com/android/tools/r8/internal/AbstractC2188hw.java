package com.android.tools.r8.internal;

import java.util.function.Consumer;
import name.antonsmirnov.firmata.FormatHelper;

/* JADX INFO: renamed from: com.android.tools.r8.internal.hw, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
abstract class AbstractC2188hw extends AbstractC1116Jv {
    private String a;
    private Object b;
    private AbstractC2509m2 c;

    private AbstractC2188hw() {
        this.a = null;
        this.b = null;
        this.c = null;
    }

    AbstractC2509m2 a(Consumer consumer, String str) {
        return null;
    }

    final AbstractC2509m2 b(Consumer consumer, String str) {
        AbstractC2509m2 abstractC2509m2A = a(consumer.andThen(new Consumer() { // from class: com.android.tools.r8.internal.hw$$ExternalSyntheticLambda0
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                this.f$0.a(obj);
            }
        }), str);
        if (abstractC2509m2A == null) {
            return null;
        }
        if (this.b != null || this.c != null) {
            a(str);
            throw null;
        }
        this.a = str;
        this.c = abstractC2509m2A;
        return abstractC2509m2A;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(String str) {
        throw new C2883qv("Multiple declarations defining " + a() + ": '" + this.a + "' and '" + str + FormatHelper.QUOTE);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(Object obj) {
        this.b = obj;
    }
}
