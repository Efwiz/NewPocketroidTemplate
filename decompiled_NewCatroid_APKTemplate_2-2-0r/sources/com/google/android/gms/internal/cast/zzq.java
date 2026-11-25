package com.google.android.gms.internal.cast;

/* JADX INFO: compiled from: com.google.android.gms:play-services-cast-framework@@21.4.0 */
/* JADX INFO: loaded from: classes9.dex */
public final class zzq {
    private final String zza;
    private final long zzb;
    private final int zzc;
    private final long zzd;
    private final long zze;
    private long zzf;

    public zzq(zzp zzpVar) {
        this.zza = zzpVar.zza;
        this.zzb = zzpVar.zzb;
        this.zzc = zzpVar.zzc;
        this.zzd = zzpVar.zzd;
        this.zze = zzpVar.zze;
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Code duplicated, block: B:71:0x0127  */
    public final zznq zza() {
        String str = this.zza;
        zznp zznpVarZza = zznq.zza();
        int i = 15;
        switch (str) {
            case "load":
                i = 2;
                break;
            case "play":
                i = 3;
                break;
            case "pause":
                i = 4;
                break;
            case "stop":
                i = 5;
                break;
            case "seek":
                i = 6;
                break;
            case "volume":
                i = 7;
                break;
            case "mute":
                i = 8;
                break;
            case "volume-mute":
                i = 9;
                break;
            case "status":
                i = 10;
                break;
            case "activeTracks":
                i = 11;
                break;
            case "trackStyle":
                i = 12;
                break;
            case "queueInsert":
                i = 13;
                break;
            case "queueUpdate":
                i = 14;
                break;
            case "queueRemove":
                break;
            case "queueReorder":
                i = 16;
                break;
            case "queueFetchItemIds":
                i = 17;
                break;
            case "queueFetchItemRange":
                i = 18;
                break;
            case "queueFetchItems":
                i = 19;
                break;
            case "setPlaybackRate":
                i = 20;
                break;
            case "skipAd":
                i = 21;
                break;
            case "launch":
                i = 22;
                break;
            case "setPlaybackDevices":
                i = 23;
                break;
            default:
                i = 1;
                break;
        }
        zznpVarZza.zze(i);
        zznpVarZza.zzb((int) this.zzb);
        zznpVarZza.zzd(this.zzc);
        zznpVarZza.zzc((int) (this.zzd - this.zzf));
        zznpVarZza.zza((int) (this.zze - this.zzf));
        return (zznq) zznpVarZza.zzq();
    }

    public final void zzb(long j) {
        this.zzf = j;
    }
}
