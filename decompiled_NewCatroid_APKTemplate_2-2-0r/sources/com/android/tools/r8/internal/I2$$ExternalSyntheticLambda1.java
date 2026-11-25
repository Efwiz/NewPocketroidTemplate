package com.android.tools.r8.internal;

import java.util.function.Consumer;

/* JADX INFO: compiled from: D8$$SyntheticClass */
/* JADX INFO: loaded from: classes7.dex */
public final /* synthetic */ class I2$$ExternalSyntheticLambda1 implements Consumer {
    public final /* synthetic */ InterfaceC2641no f$0;

    public /* synthetic */ I2$$ExternalSyntheticLambda1(InterfaceC2641no interfaceC2641no) {
        this.f$0 = interfaceC2641no;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        this.f$0.add((AbstractC2412ko) obj);
    }
}
