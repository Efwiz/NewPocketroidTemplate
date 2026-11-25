package com.google.android.gms.internal.mlkit_vision_pose_common;

/* JADX INFO: compiled from: com.google.mlkit:pose-detection-common@@17.0.0 */
/* JADX INFO: loaded from: classes9.dex */
public final class zzdx {

    /* JADX INFO: compiled from: com.google.mlkit:pose-detection-common@@17.0.0 */
    public static final class zza extends zzgd<zza, C0070zza> implements zzhn {
        private static final zzgj<Integer, zzeq> zzd = new zzdz();
        private static final zza zze;
        private static volatile zzhv<zza> zzf;
        private zzgk zzc = zzk();

        private zza() {
        }

        /* JADX INFO: renamed from: com.google.android.gms.internal.mlkit_vision_pose_common.zzdx$zza$zza, reason: collision with other inner class name */
        /* JADX INFO: compiled from: com.google.mlkit:pose-detection-common@@17.0.0 */
        public static final class C0070zza extends zzgd.zza<zza, C0070zza> implements zzhn {
            private C0070zza() {
                super(zza.zze);
            }

            /* synthetic */ C0070zza(zzdy zzdyVar) {
                this();
            }
        }

        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Type inference failed for: r1v13 */
        /* JADX WARN: Type inference failed for: r1v14, types: [com.google.android.gms.internal.mlkit_vision_pose_common.zzgd$zzc, com.google.android.gms.internal.mlkit_vision_pose_common.zzhv<com.google.android.gms.internal.mlkit_vision_pose_common.zzdx$zza>] */
        /* JADX WARN: Type inference failed for: r1v17 */
        /* JADX WARN: Type inference failed for: r1v18 */
        @Override // com.google.android.gms.internal.mlkit_vision_pose_common.zzgd
        protected final Object zza(int i, Object obj, Object obj2) {
            ?? r1;
            zzdy zzdyVar = null;
            switch (zzdy.zza[i - 1]) {
                case 1:
                    return new zza();
                case 2:
                    return new C0070zza(zzdyVar);
                case 3:
                    return zza(zze, "\u0001\u0001\u0000\u0000\u0001\u0001\u0001\u0000\u0001\u0000\u0001\u001e", new Object[]{"zzc", zzeq.zzb()});
                case 4:
                    return zze;
                case 5:
                    zzhv<zza> zzhvVar = zzf;
                    Object obj3 = zzhvVar;
                    if (zzhvVar == null) {
                        synchronized (zza.class) {
                            zzhv<zza> zzhvVar2 = zzf;
                            r1 = zzhvVar2;
                            if (zzhvVar2 == null) {
                                ?? zzcVar = new zzgd.zzc(zze);
                                zzf = zzcVar;
                                r1 = zzcVar;
                            }
                            break;
                        }
                        obj3 = r1;
                    }
                    return obj3;
                case 6:
                    return (byte) 1;
                case 7:
                    return null;
                default:
                    throw new UnsupportedOperationException();
            }
        }

        /* JADX WARN: Type inference failed for: r0v0, types: [com.google.android.gms.internal.mlkit_vision_pose_common.zzdz, com.google.android.gms.internal.mlkit_vision_pose_common.zzgj<java.lang.Integer, com.google.android.gms.internal.mlkit_vision_pose_common.zzeq>] */
        static {
            zza zzaVar = new zza();
            zze = zzaVar;
            zzgd.zza((Class<zza>) zza.class, zzaVar);
        }
    }

    /* JADX INFO: compiled from: com.google.mlkit:pose-detection-common@@17.0.0 */
    public static final class zzb extends zzgd<zzb, zza> implements zzhn {
        private static final zzb zzj;
        private static volatile zzhv<zzb> zzk;
        private int zzc;
        private int zzd;
        private int zze;
        private int zzf;
        private boolean zzg;
        private boolean zzh;
        private float zzi;

        /* JADX INFO: renamed from: com.google.android.gms.internal.mlkit_vision_pose_common.zzdx$zzb$zzb, reason: collision with other inner class name */
        /* JADX INFO: compiled from: com.google.mlkit:pose-detection-common@@17.0.0 */
        public enum EnumC0071zzb implements zzgf {
            CLASSIFICATION_UNKNOWN(0),
            CLASSIFICATION_NONE(1),
            CLASSIFICATION_ALL(2);

            private static final zzgi<EnumC0071zzb> zzd = new zzea();
            private final int zze;

            @Override // com.google.android.gms.internal.mlkit_vision_pose_common.zzgf
            public final int zza() {
                return this.zze;
            }

            public static zzgh zzb() {
                return zzeb.zza;
            }

            @Override // java.lang.Enum
            public final String toString() {
                StringBuilder sb = new StringBuilder("<");
                sb.append(getClass().getName()).append('@').append(Integer.toHexString(System.identityHashCode(this)));
                sb.append(" number=").append(this.zze);
                return sb.append(" name=").append(name()).append('>').toString();
            }

            EnumC0071zzb(int i) {
                this.zze = i;
            }
        }

        /* JADX INFO: compiled from: com.google.mlkit:pose-detection-common@@17.0.0 */
        public enum zzc implements zzgf {
            LANDMARK_UNKNOWN(0),
            LANDMARK_NONE(1),
            LANDMARK_ALL(2),
            LANDMARK_CONTOUR(3);

            private static final zzgi<zzc> zze = new zzed();
            private final int zzf;

            @Override // com.google.android.gms.internal.mlkit_vision_pose_common.zzgf
            public final int zza() {
                return this.zzf;
            }

            public static zzgh zzb() {
                return zzec.zza;
            }

            @Override // java.lang.Enum
            public final String toString() {
                StringBuilder sb = new StringBuilder("<");
                sb.append(getClass().getName()).append('@').append(Integer.toHexString(System.identityHashCode(this)));
                sb.append(" number=").append(this.zzf);
                return sb.append(" name=").append(name()).append('>').toString();
            }

            zzc(int i) {
                this.zzf = i;
            }
        }

        /* JADX INFO: compiled from: com.google.mlkit:pose-detection-common@@17.0.0 */
        public enum zzd implements zzgf {
            MODE_UNKNOWN(0),
            MODE_ACCURATE(1),
            MODE_FAST(2),
            MODE_SELFIE(3);

            private static final zzgi<zzd> zze = new zzee();
            private final int zzf;

            @Override // com.google.android.gms.internal.mlkit_vision_pose_common.zzgf
            public final int zza() {
                return this.zzf;
            }

            public static zzgh zzb() {
                return zzef.zza;
            }

            @Override // java.lang.Enum
            public final String toString() {
                StringBuilder sb = new StringBuilder("<");
                sb.append(getClass().getName()).append('@').append(Integer.toHexString(System.identityHashCode(this)));
                sb.append(" number=").append(this.zzf);
                return sb.append(" name=").append(name()).append('>').toString();
            }

            zzd(int i) {
                this.zzf = i;
            }
        }

        private zzb() {
        }

        /* JADX INFO: compiled from: com.google.mlkit:pose-detection-common@@17.0.0 */
        public static final class zza extends zzgd.zza<zzb, zza> implements zzhn {
            private zza() {
                super(zzb.zzj);
            }

            /* synthetic */ zza(zzdy zzdyVar) {
                this();
            }
        }

        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Type inference failed for: r11v12 */
        /* JADX WARN: Type inference failed for: r11v13, types: [com.google.android.gms.internal.mlkit_vision_pose_common.zzgd$zzc, com.google.android.gms.internal.mlkit_vision_pose_common.zzhv<com.google.android.gms.internal.mlkit_vision_pose_common.zzdx$zzb>] */
        /* JADX WARN: Type inference failed for: r11v16 */
        /* JADX WARN: Type inference failed for: r11v17 */
        @Override // com.google.android.gms.internal.mlkit_vision_pose_common.zzgd
        protected final Object zza(int i, Object obj, Object obj2) {
            ?? r11;
            zzdy zzdyVar = null;
            switch (zzdy.zza[i - 1]) {
                case 1:
                    return new zzb();
                case 2:
                    return new zza(zzdyVar);
                case 3:
                    return zza(zzj, "\u0001\u0006\u0000\u0001\u0001\u0006\u0006\u0000\u0000\u0000\u0001ဌ\u0000\u0002ဌ\u0001\u0003ဌ\u0002\u0004ဇ\u0003\u0005ဇ\u0004\u0006ခ\u0005", new Object[]{"zzc", "zzd", zzd.zzb(), "zze", zzc.zzb(), "zzf", EnumC0071zzb.zzb(), "zzg", "zzh", "zzi"});
                case 4:
                    return zzj;
                case 5:
                    zzhv<zzb> zzhvVar = zzk;
                    Object obj3 = zzhvVar;
                    if (zzhvVar == null) {
                        synchronized (zzb.class) {
                            zzhv<zzb> zzhvVar2 = zzk;
                            r11 = zzhvVar2;
                            if (zzhvVar2 == null) {
                                ?? zzcVar = new zzgd.zzc(zzj);
                                zzk = zzcVar;
                                r11 = zzcVar;
                            }
                            break;
                        }
                        obj3 = r11;
                    }
                    return obj3;
                case 6:
                    return (byte) 1;
                case 7:
                    return null;
                default:
                    throw new UnsupportedOperationException();
            }
        }

        static {
            zzb zzbVar = new zzb();
            zzj = zzbVar;
            zzgd.zza((Class<zzb>) zzb.class, zzbVar);
        }
    }
}
