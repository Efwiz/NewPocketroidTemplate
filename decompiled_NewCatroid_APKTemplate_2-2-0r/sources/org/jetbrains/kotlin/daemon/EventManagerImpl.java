package org.jetbrains.kotlin.daemon;

import java.util.ArrayList;
import java.util.Iterator;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: CompileServiceImpl.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0006\u0010\n\u001a\u00020\u0006J\u0016\u0010\u0003\u001a\u00020\u00062\f\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005H\u0016R-\u0010\u0003\u001a\u001e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00060\u00050\u0004j\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00060\u0005`\u0007¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\t¨\u0006\f"}, d2 = {"Lorg/jetbrains/kotlin/daemon/EventManagerImpl;", "Lorg/jetbrains/kotlin/daemon/EventManager;", "()V", "onCompilationFinished", "Ljava/util/ArrayList;", "Lkotlin/Function0;", "", "Lkotlin/collections/ArrayList;", "getOnCompilationFinished", "()Ljava/util/ArrayList;", "fireCompilationFinished", "f", "kotlin-daemon"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class EventManagerImpl implements EventManager {
    private final ArrayList<Function0<Unit>> onCompilationFinished = new ArrayList<>();

    public final ArrayList<Function0<Unit>> getOnCompilationFinished() {
        return this.onCompilationFinished;
    }

    @Override // org.jetbrains.kotlin.daemon.EventManager
    public void onCompilationFinished(Function0<Unit> f) {
        Intrinsics.checkNotNullParameter(f, "f");
        this.onCompilationFinished.add(f);
    }

    public final void fireCompilationFinished() {
        Iterator<T> it = this.onCompilationFinished.iterator();
        while (it.hasNext()) {
            ((Function0) it.next()).invoke();
        }
    }
}
