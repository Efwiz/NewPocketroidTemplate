package com.google.android.gms.internal.mlkit_vision_internal_vkp;

/* JADX INFO: compiled from: com.google.mlkit:vision-internal-vkp@@18.2.0 */
/* JADX INFO: loaded from: classes9.dex */
final class zzkm extends zzko {
    final /* synthetic */ zzkn zza;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    zzkm(zzkn zzknVar, zzkp zzkpVar, CharSequence charSequence) {
        super(zzkpVar, charSequence);
        this.zza = zzknVar;
    }

    @Override // com.google.android.gms.internal.mlkit_vision_internal_vkp.zzko
    public final int zzc(int i) {
        return i + this.zza.zza.length();
    }

    @Override // com.google.android.gms.internal.mlkit_vision_internal_vkp.zzko
    public final int zzd(int i) {
        int length = this.zza.zza.length();
        int length2 = this.zzb.length() - length;
        while (i <= length2) {
            for (int i2 = 0; i2 < length; i2++) {
                if (this.zzb.charAt(i2 + i) != this.zza.zza.charAt(i2)) {
                    i++;
                }
            }
            return i;
        }
        return -1;
    }
}
