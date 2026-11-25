package com.android.tools.r8.internal;

import com.android.tools.r8.retrace.RetracedSingleFrame;
import java.util.function.Consumer;
import java.util.stream.Stream;

/* JADX INFO: compiled from: D8$$SyntheticClass */
/* JADX INFO: loaded from: classes7.dex */
public final /* synthetic */ class wT$$ExternalSyntheticLambda0 implements Consumer {
    public final /* synthetic */ Stream.Builder f$0;

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        this.f$0.add((RetracedSingleFrame) obj);
    }
}
