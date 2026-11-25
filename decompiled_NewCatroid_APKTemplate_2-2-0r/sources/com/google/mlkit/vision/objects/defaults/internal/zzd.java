package com.google.mlkit.vision.objects.defaults.internal;

import com.google.android.gms.internal.mlkit_vision_object_detection_bundled.zzko;
import com.google.android.gms.internal.mlkit_vision_object_detection_bundled.zznz;
import com.google.mlkit.vision.common.InputImage;
import com.google.mlkit.vision.vkp.VkpStatus;
import java.util.List;

/* JADX INFO: compiled from: com.google.mlkit:object-detection@@16.2.7 */
/* JADX INFO: loaded from: classes10.dex */
public final /* synthetic */ class zzd {
    public final /* synthetic */ zze zza;
    public final /* synthetic */ List zzb;
    public final /* synthetic */ long zzc;
    public final /* synthetic */ zzko zzd;
    public final /* synthetic */ VkpStatus zze;
    public final /* synthetic */ boolean zzf;
    public final /* synthetic */ Boolean zzg;
    public final /* synthetic */ InputImage zzh;

    public /* synthetic */ zzd(zze zzeVar, List list, long j, zzko zzkoVar, VkpStatus vkpStatus, boolean z, Boolean bool, InputImage inputImage) {
        this.zza = zzeVar;
        this.zzb = list;
        this.zzc = j;
        this.zzd = zzkoVar;
        this.zze = vkpStatus;
        this.zzf = z;
        this.zzg = bool;
        this.zzh = inputImage;
    }

    public final zznz zza() {
        return this.zza.zzd(this.zzb, this.zzc, this.zzd, this.zze, this.zzf, this.zzg, this.zzh);
    }
}
