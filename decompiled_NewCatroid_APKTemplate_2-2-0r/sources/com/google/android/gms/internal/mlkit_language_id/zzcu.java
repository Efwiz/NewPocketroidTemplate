package com.google.android.gms.internal.mlkit_language_id;

import com.google.android.gms.common.internal.LibraryVersion;
import java.util.concurrent.Callable;

/* JADX INFO: compiled from: com.google.mlkit:language-id@@16.1.1 */
/* JADX INFO: loaded from: classes9.dex */
final /* synthetic */ class zzcu implements Callable {
    static final Callable zza = new zzcu();

    private zzcu() {
    }

    @Override // java.util.concurrent.Callable
    public final Object call() {
        return LibraryVersion.getInstance().getVersion("language-id");
    }
}
