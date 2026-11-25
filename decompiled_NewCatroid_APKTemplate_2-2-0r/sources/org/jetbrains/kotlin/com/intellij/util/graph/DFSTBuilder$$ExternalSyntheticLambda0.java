package org.jetbrains.kotlin.com.intellij.util.graph;

import java.util.function.ObjIntConsumer;
import org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.Reference2IntOpenHashMap;

/* JADX INFO: compiled from: D8$$SyntheticClass */
/* JADX INFO: loaded from: classes3.dex */
public final /* synthetic */ class DFSTBuilder$$ExternalSyntheticLambda0 implements ObjIntConsumer {
    public final /* synthetic */ Reference2IntOpenHashMap f$0;

    public /* synthetic */ DFSTBuilder$$ExternalSyntheticLambda0(Reference2IntOpenHashMap reference2IntOpenHashMap) {
        this.f$0 = reference2IntOpenHashMap;
    }

    @Override // java.util.function.ObjIntConsumer
    public final void accept(Object obj, int i) {
        this.f$0.put(obj, i);
    }
}
