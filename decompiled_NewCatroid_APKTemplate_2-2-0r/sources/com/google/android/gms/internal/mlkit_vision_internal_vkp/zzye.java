package com.google.android.gms.internal.mlkit_vision_internal_vkp;

/* JADX INFO: compiled from: com.google.mlkit:vision-internal-vkp@@18.2.0 */
/* JADX INFO: loaded from: classes9.dex */
public enum zzye implements zzmb {
    DELEGATE_NONE(0),
    NNAPI(1),
    GPU(2),
    HEXAGON(3),
    EDGETPU(4),
    EDGETPU_CORAL(5),
    XNNPACK(6);

    private final int zzi;

    zzye(int i) {
        this.zzi = i;
    }

    @Override // com.google.android.gms.internal.mlkit_vision_internal_vkp.zzmb
    public final int zza() {
        return this.zzi;
    }
}
