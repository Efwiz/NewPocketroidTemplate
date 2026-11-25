package com.google.android.gms.internal.mlkit_language_id;

/* JADX INFO: compiled from: com.google.mlkit:language-id@@16.1.1 */
/* JADX INFO: loaded from: classes9.dex */
public final class zzci {

    /* JADX INFO: compiled from: com.google.mlkit:language-id@@16.1.1 */
    public static final class zza extends zzeo<zza, C0046zza> implements zzgb {
        private static final zzex<Integer, zzdd> zzd = new zzcj();
        private static final zza zze;
        private static volatile zzgj<zza> zzf;
        private zzeu zzc = zzk();

        private zza() {
        }

        /* JADX INFO: renamed from: com.google.android.gms.internal.mlkit_language_id.zzci$zza$zza, reason: collision with other inner class name */
        /* JADX INFO: compiled from: com.google.mlkit:language-id@@16.1.1 */
        public static final class C0046zza extends zzeo.zzb<zza, C0046zza> implements zzgb {
            private C0046zza() {
                super(zza.zze);
            }

            /* synthetic */ C0046zza(zzch zzchVar) {
                this();
            }
        }

        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Type inference failed for: r1v13 */
        /* JADX WARN: Type inference failed for: r1v14, types: [com.google.android.gms.internal.mlkit_language_id.zzeo$zza, com.google.android.gms.internal.mlkit_language_id.zzgj<com.google.android.gms.internal.mlkit_language_id.zzci$zza>] */
        /* JADX WARN: Type inference failed for: r1v17 */
        /* JADX WARN: Type inference failed for: r1v18 */
        @Override // com.google.android.gms.internal.mlkit_language_id.zzeo
        protected final Object zza(int i, Object obj, Object obj2) {
            ?? r1;
            zzch zzchVar = null;
            switch (zzch.zza[i - 1]) {
                case 1:
                    return new zza();
                case 2:
                    return new C0046zza(zzchVar);
                case 3:
                    return zza(zze, "\u0001\u0001\u0000\u0000\u0001\u0001\u0001\u0000\u0001\u0000\u0001\u001e", new Object[]{"zzc", zzdd.zzb()});
                case 4:
                    return zze;
                case 5:
                    zzgj<zza> zzgjVar = zzf;
                    Object obj3 = zzgjVar;
                    if (zzgjVar == null) {
                        synchronized (zza.class) {
                            zzgj<zza> zzgjVar2 = zzf;
                            r1 = zzgjVar2;
                            if (zzgjVar2 == null) {
                                ?? zzaVar = new zzeo.zza(zze);
                                zzf = zzaVar;
                                r1 = zzaVar;
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

        /* JADX WARN: Type inference failed for: r0v0, types: [com.google.android.gms.internal.mlkit_language_id.zzcj, com.google.android.gms.internal.mlkit_language_id.zzex<java.lang.Integer, com.google.android.gms.internal.mlkit_language_id.zzdd>] */
        static {
            zza zzaVar = new zza();
            zze = zzaVar;
            zzeo.zza((Class<zza>) zza.class, zzaVar);
        }
    }

    /* JADX INFO: compiled from: com.google.mlkit:language-id@@16.1.1 */
    public static final class zzb extends zzeo<zzb, C0047zzb> implements zzgb {
        private static final zzb zzj;
        private static volatile zzgj<zzb> zzk;
        private int zzc;
        private int zzd;
        private int zze;
        private int zzf;
        private boolean zzg;
        private boolean zzh;
        private float zzi;

        /* JADX INFO: compiled from: com.google.mlkit:language-id@@16.1.1 */
        public enum zza implements zzet {
            CLASSIFICATION_UNKNOWN(0),
            CLASSIFICATION_NONE(1),
            CLASSIFICATION_ALL(2);

            private static final zzes<zza> zzd = new zzcl();
            private final int zze;

            @Override // com.google.android.gms.internal.mlkit_language_id.zzet
            public final int zza() {
                return this.zze;
            }

            public static zzev zzb() {
                return zzck.zza;
            }

            @Override // java.lang.Enum
            public final String toString() {
                StringBuilder sb = new StringBuilder("<");
                sb.append(getClass().getName()).append('@').append(Integer.toHexString(System.identityHashCode(this)));
                sb.append(" number=").append(this.zze);
                return sb.append(" name=").append(name()).append('>').toString();
            }

            zza(int i) {
                this.zze = i;
            }
        }

        /* JADX INFO: compiled from: com.google.mlkit:language-id@@16.1.1 */
        public enum zzc implements zzet {
            LANDMARK_UNKNOWN(0),
            LANDMARK_NONE(1),
            LANDMARK_ALL(2),
            LANDMARK_CONTOUR(3);

            private static final zzes<zzc> zze = new zzcm();
            private final int zzf;

            @Override // com.google.android.gms.internal.mlkit_language_id.zzet
            public final int zza() {
                return this.zzf;
            }

            public static zzev zzb() {
                return zzcn.zza;
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

        /* JADX INFO: compiled from: com.google.mlkit:language-id@@16.1.1 */
        public enum zzd implements zzet {
            MODE_UNKNOWN(0),
            MODE_ACCURATE(1),
            MODE_FAST(2),
            MODE_SELFIE(3);

            private static final zzes<zzd> zze = new zzcp();
            private final int zzf;

            @Override // com.google.android.gms.internal.mlkit_language_id.zzet
            public final int zza() {
                return this.zzf;
            }

            public static zzev zzb() {
                return zzco.zza;
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

        /* JADX INFO: renamed from: com.google.android.gms.internal.mlkit_language_id.zzci$zzb$zzb, reason: collision with other inner class name */
        /* JADX INFO: compiled from: com.google.mlkit:language-id@@16.1.1 */
        public static final class C0047zzb extends zzeo.zzb<zzb, C0047zzb> implements zzgb {
            private C0047zzb() {
                super(zzb.zzj);
            }

            /* synthetic */ C0047zzb(zzch zzchVar) {
                this();
            }
        }

        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Type inference failed for: r11v12 */
        /* JADX WARN: Type inference failed for: r11v13, types: [com.google.android.gms.internal.mlkit_language_id.zzeo$zza, com.google.android.gms.internal.mlkit_language_id.zzgj<com.google.android.gms.internal.mlkit_language_id.zzci$zzb>] */
        /* JADX WARN: Type inference failed for: r11v16 */
        /* JADX WARN: Type inference failed for: r11v17 */
        @Override // com.google.android.gms.internal.mlkit_language_id.zzeo
        protected final Object zza(int i, Object obj, Object obj2) {
            ?? r11;
            zzch zzchVar = null;
            switch (zzch.zza[i - 1]) {
                case 1:
                    return new zzb();
                case 2:
                    return new C0047zzb(zzchVar);
                case 3:
                    return zza(zzj, "\u0001\u0006\u0000\u0001\u0001\u0006\u0006\u0000\u0000\u0000\u0001ဌ\u0000\u0002ဌ\u0001\u0003ဌ\u0002\u0004ဇ\u0003\u0005ဇ\u0004\u0006ခ\u0005", new Object[]{"zzc", "zzd", zzd.zzb(), "zze", zzc.zzb(), "zzf", zza.zzb(), "zzg", "zzh", "zzi"});
                case 4:
                    return zzj;
                case 5:
                    zzgj<zzb> zzgjVar = zzk;
                    Object obj3 = zzgjVar;
                    if (zzgjVar == null) {
                        synchronized (zzb.class) {
                            zzgj<zzb> zzgjVar2 = zzk;
                            r11 = zzgjVar2;
                            if (zzgjVar2 == null) {
                                ?? zzaVar = new zzeo.zza(zzj);
                                zzk = zzaVar;
                                r11 = zzaVar;
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
            zzeo.zza((Class<zzb>) zzb.class, zzbVar);
        }
    }
}
