package com.google.android.gms.phenotype;

import java.util.Comparator;

/* JADX INFO: loaded from: classes9.dex */
final class zzj implements Comparator<zzi> {
    zzj() {
    }

    @Override // java.util.Comparator
    public final /* synthetic */ int compare(zzi zziVar, zzi zziVar2) {
        zzi zziVar3 = zziVar;
        zzi zziVar4 = zziVar2;
        return zziVar3.zzah == zziVar4.zzah ? zziVar3.f146name.compareTo(zziVar4.f146name) : zziVar3.zzah - zziVar4.zzah;
    }
}
