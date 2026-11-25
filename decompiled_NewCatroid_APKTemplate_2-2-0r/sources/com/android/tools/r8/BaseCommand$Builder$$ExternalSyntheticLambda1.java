package com.android.tools.r8;

import java.nio.file.Path;
import java.util.function.Consumer;

/* JADX INFO: compiled from: D8$$SyntheticClass */
/* JADX INFO: loaded from: classes7.dex */
public final /* synthetic */ class BaseCommand$Builder$$ExternalSyntheticLambda1 implements Consumer {
    public final /* synthetic */ BaseCommand.Builder f$0;

    public /* synthetic */ BaseCommand$Builder$$ExternalSyntheticLambda1(BaseCommand.Builder builder) {
        this.f$0 = builder;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        this.f$0.a((Path) obj);
    }
}
