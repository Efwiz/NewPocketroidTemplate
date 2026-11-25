package com.google.android.gms.internal.mlkit_vision_pose_common;

/* JADX INFO: compiled from: com.google.mlkit:pose-detection-common@@17.0.0 */
/* JADX INFO: loaded from: classes9.dex */
public enum zzjz implements zzgf {
    UNKNOWN_EVENT_TYPE(0),
    VALIDATION_TEST(1),
    CONTINUOUS_FEEDBACK(2);

    private static final zzgi<zzjz> zzd = new zzgi<zzjz>() { // from class: com.google.android.gms.internal.mlkit_vision_pose_common.zzjy
    };
    private final int zze;

    @Override // com.google.android.gms.internal.mlkit_vision_pose_common.zzgf
    public final int zza() {
        return this.zze;
    }

    public static zzgh zzb() {
        return zzka.zza;
    }

    @Override // java.lang.Enum
    public final String toString() {
        StringBuilder sb = new StringBuilder("<");
        sb.append(getClass().getName()).append('@').append(Integer.toHexString(System.identityHashCode(this)));
        sb.append(" number=").append(this.zze);
        return sb.append(" name=").append(name()).append('>').toString();
    }

    zzjz(int i) {
        this.zze = i;
    }
}
