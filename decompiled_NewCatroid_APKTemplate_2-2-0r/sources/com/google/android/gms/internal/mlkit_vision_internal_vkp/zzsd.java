package com.google.android.gms.internal.mlkit_vision_internal_vkp;

/* JADX INFO: compiled from: com.google.mlkit:vision-internal-vkp@@18.2.0 */
/* JADX INFO: loaded from: classes9.dex */
final class zzsd implements zzanw {
    static final zzanw zza = new zzsd();

    private zzsd() {
    }

    @Override // com.google.android.gms.internal.mlkit_vision_internal_vkp.zzanw
    public final boolean zza(int i) {
        if (i != 0 && i != 1 && i != 2) {
            switch (i) {
                case 4:
                case 8:
                case 16:
                case 32:
                case 64:
                case 128:
                case 256:
                case 512:
                case 1024:
                case 2048:
                case 4096:
                    break;
                default:
                    return false;
            }
        }
        return true;
    }
}
