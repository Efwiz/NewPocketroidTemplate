package com.google.android.gms.internal.mlkit_vision_pose_common;

/* JADX INFO: compiled from: com.google.mlkit:pose-detection-common@@17.0.0 */
/* JADX INFO: loaded from: classes9.dex */
public enum zzeq implements zzgf {
    UNRECOGNIZED(0),
    CODE_128(1),
    CODE_39(2),
    CODE_93(3),
    CODABAR(4),
    DATA_MATRIX(5),
    EAN_13(6),
    EAN_8(7),
    ITF(8),
    QR_CODE(9),
    UPC_A(10),
    UPC_E(11),
    PDF417(12),
    AZTEC(13),
    DATABAR(14),
    TEZ_CODE(16);

    private static final zzgi<zzeq> zzq = new zzgi<zzeq>() { // from class: com.google.android.gms.internal.mlkit_vision_pose_common.zzes
    };
    private final int zzr;

    @Override // com.google.android.gms.internal.mlkit_vision_pose_common.zzgf
    public final int zza() {
        return this.zzr;
    }

    public static zzgh zzb() {
        return zzer.zza;
    }

    @Override // java.lang.Enum
    public final String toString() {
        StringBuilder sb = new StringBuilder("<");
        sb.append(getClass().getName()).append('@').append(Integer.toHexString(System.identityHashCode(this)));
        sb.append(" number=").append(this.zzr);
        return sb.append(" name=").append(name()).append('>').toString();
    }

    zzeq(int i) {
        this.zzr = i;
    }
}
