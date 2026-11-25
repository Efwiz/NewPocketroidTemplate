package com.google.android.gms.cast.internal;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/* JADX INFO: compiled from: com.google.android.gms:play-services-cast@@21.4.0 */
/* JADX INFO: loaded from: classes9.dex */
public class zzd extends zzp {
    private final List zzb;

    public zzd(String str, String str2, String str3) {
        super(str, "MediaControlChannel", null);
        this.zzb = Collections.synchronizedList(new ArrayList());
    }

    protected final List zza() {
        return this.zzb;
    }

    protected final void zzb() {
        synchronized (this.zzb) {
            Iterator it = this.zzb.iterator();
            while (it.hasNext()) {
                ((zzau) it.next()).zzc(2002);
            }
        }
    }

    protected final void zzc(zzau zzauVar) {
        this.zzb.add(zzauVar);
    }
}
