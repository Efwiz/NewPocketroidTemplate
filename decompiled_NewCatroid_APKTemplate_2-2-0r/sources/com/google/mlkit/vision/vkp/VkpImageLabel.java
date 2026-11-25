package com.google.mlkit.vision.vkp;

import android.text.TextUtils;
import com.google.android.gms.internal.mlkit_vision_internal_vkp.zzgw;

/* JADX INFO: compiled from: com.google.mlkit:vision-internal-vkp@@18.2.0 */
/* JADX INFO: loaded from: classes10.dex */
public abstract class VkpImageLabel {
    public static VkpImageLabel zza(zzgw zzgwVar) {
        String strZzf = zzgwVar.zzf();
        if (TextUtils.isEmpty(strZzf)) {
            strZzf = zzgwVar.zze();
        }
        return new AutoValue_VkpImageLabel(zzgwVar.zze(), strZzf, zzgwVar.zza(), zzgwVar.zzc());
    }

    public abstract String getClassName();

    public abstract int getIndex();

    public abstract float getScore();

    public abstract String getText();
}
