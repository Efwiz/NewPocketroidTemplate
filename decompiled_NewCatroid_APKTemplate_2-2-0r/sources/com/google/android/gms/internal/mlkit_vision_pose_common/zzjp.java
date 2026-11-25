package com.google.android.gms.internal.mlkit_vision_pose_common;

/* JADX INFO: compiled from: com.google.mlkit:pose-detection-common@@17.0.0 */
/* JADX INFO: loaded from: classes9.dex */
public final class zzjp {

    /* JADX INFO: compiled from: com.google.mlkit:pose-detection-common@@17.0.0 */
    public static final class zza extends zzgd<zza, zzb> implements zzhn {
        private static final zza zzf;
        private static volatile zzhv<zza> zzg;
        private int zzc;
        private int zzd;
        private zzj zze;

        /* JADX INFO: renamed from: com.google.android.gms.internal.mlkit_vision_pose_common.zzjp$zza$zza, reason: collision with other inner class name */
        /* JADX INFO: compiled from: com.google.mlkit:pose-detection-common@@17.0.0 */
        public enum EnumC0072zza implements zzgf {
            UNKNOWN_ENGINE(0),
            TFLITE(1);

            private static final zzgi<EnumC0072zza> zzc = new zzjt();
            private final int zzd;

            @Override // com.google.android.gms.internal.mlkit_vision_pose_common.zzgf
            public final int zza() {
                return this.zzd;
            }

            public static zzgh zzb() {
                return zzjs.zza;
            }

            @Override // java.lang.Enum
            public final String toString() {
                StringBuilder sb = new StringBuilder("<");
                sb.append(getClass().getName()).append('@').append(Integer.toHexString(System.identityHashCode(this)));
                sb.append(" number=").append(this.zzd);
                return sb.append(" name=").append(name()).append('>').toString();
            }

            EnumC0072zza(int i) {
                this.zzd = i;
            }
        }

        private zza() {
        }

        /* JADX INFO: compiled from: com.google.mlkit:pose-detection-common@@17.0.0 */
        public static final class zzb extends zzgd.zza<zza, zzb> implements zzhn {
            private zzb() {
                super(zza.zzf);
            }

            /* synthetic */ zzb(zzjr zzjrVar) {
                this();
            }
        }

        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Type inference failed for: r2v13 */
        /* JADX WARN: Type inference failed for: r2v14, types: [com.google.android.gms.internal.mlkit_vision_pose_common.zzgd$zzc, com.google.android.gms.internal.mlkit_vision_pose_common.zzhv<com.google.android.gms.internal.mlkit_vision_pose_common.zzjp$zza>] */
        /* JADX WARN: Type inference failed for: r2v17 */
        /* JADX WARN: Type inference failed for: r2v18 */
        @Override // com.google.android.gms.internal.mlkit_vision_pose_common.zzgd
        protected final Object zza(int i, Object obj, Object obj2) {
            ?? r2;
            zzjr zzjrVar = null;
            switch (zzjr.zza[i - 1]) {
                case 1:
                    return new zza();
                case 2:
                    return new zzb(zzjrVar);
                case 3:
                    return zza(zzf, "\u0001\u0002\u0000\u0001\u0001\u0002\u0002\u0000\u0000\u0000\u0001ဌ\u0000\u0002ဉ\u0001", new Object[]{"zzc", "zzd", EnumC0072zza.zzb(), "zze"});
                case 4:
                    return zzf;
                case 5:
                    zzhv<zza> zzhvVar = zzg;
                    Object obj3 = zzhvVar;
                    if (zzhvVar == null) {
                        synchronized (zza.class) {
                            zzhv<zza> zzhvVar2 = zzg;
                            r2 = zzhvVar2;
                            if (zzhvVar2 == null) {
                                ?? zzcVar = new zzgd.zzc(zzf);
                                zzg = zzcVar;
                                r2 = zzcVar;
                            }
                            break;
                        }
                        obj3 = r2;
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
            zza zzaVar = new zza();
            zzf = zzaVar;
            zzgd.zza((Class<zza>) zza.class, zzaVar);
        }
    }

    /* JADX INFO: compiled from: com.google.mlkit:pose-detection-common@@17.0.0 */
    public static final class zzb extends zzgd<zzb, zza> implements zzhn {
        private static final zzb zzn;
        private static volatile zzhv<zzb> zzo;
        private int zzc;
        private int zzd;
        private String zze = "";
        private String zzf = "";
        private String zzg = "";
        private String zzh = "";
        private String zzi = "";
        private String zzj = "";
        private String zzk = "";
        private String zzl = "";
        private String zzm = "";

        private zzb() {
        }

        /* JADX INFO: compiled from: com.google.mlkit:pose-detection-common@@17.0.0 */
        public static final class zza extends zzgd.zza<zzb, zza> implements zzhn {
            private zza() {
                super(zzb.zzn);
            }

            /* synthetic */ zza(zzjr zzjrVar) {
                this();
            }
        }

        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Type inference failed for: r12v12 */
        /* JADX WARN: Type inference failed for: r12v13, types: [com.google.android.gms.internal.mlkit_vision_pose_common.zzgd$zzc, com.google.android.gms.internal.mlkit_vision_pose_common.zzhv<com.google.android.gms.internal.mlkit_vision_pose_common.zzjp$zzb>] */
        /* JADX WARN: Type inference failed for: r12v16 */
        /* JADX WARN: Type inference failed for: r12v17 */
        @Override // com.google.android.gms.internal.mlkit_vision_pose_common.zzgd
        protected final Object zza(int i, Object obj, Object obj2) {
            ?? r12;
            zzjr zzjrVar = null;
            switch (zzjr.zza[i - 1]) {
                case 1:
                    return new zzb();
                case 2:
                    return new zza(zzjrVar);
                case 3:
                    return zza(zzn, "\u0001\n\u0000\u0001\u0001\n\n\u0000\u0000\u0000\u0001င\u0000\u0002ဈ\u0001\u0003ဈ\u0002\u0004ဈ\u0003\u0005ဈ\u0004\u0006ဈ\u0005\u0007ဈ\u0006\bဈ\u0007\tဈ\b\nဈ\t", new Object[]{"zzc", "zzd", "zze", "zzf", "zzg", "zzh", "zzi", "zzj", "zzk", "zzl", "zzm"});
                case 4:
                    return zzn;
                case 5:
                    zzhv<zzb> zzhvVar = zzo;
                    Object obj3 = zzhvVar;
                    if (zzhvVar == null) {
                        synchronized (zzb.class) {
                            zzhv<zzb> zzhvVar2 = zzo;
                            r12 = zzhvVar2;
                            if (zzhvVar2 == null) {
                                ?? zzcVar = new zzgd.zzc(zzn);
                                zzo = zzcVar;
                                r12 = zzcVar;
                            }
                            break;
                        }
                        obj3 = r12;
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
            zzn = zzbVar;
            zzgd.zza((Class<zzb>) zzb.class, zzbVar);
        }
    }

    /* JADX INFO: compiled from: com.google.mlkit:pose-detection-common@@17.0.0 */
    public static final class zzc extends zzgd<zzc, C0073zzc> implements zzhn {
        private static final zzc zzm;
        private static volatile zzhv<zzc> zzn;
        private int zzc;
        private int zzd;
        private int zze;
        private zza zzh;
        private zzd zzi;
        private int zzj;
        private int zzl;
        private String zzf = "";
        private String zzg = "";
        private zzgm<zzg> zzk = zzl();

        /* JADX INFO: compiled from: com.google.mlkit:pose-detection-common@@17.0.0 */
        public enum zza implements zzgf {
            UNKNOWN_ACTION(0),
            INITIALIZATION(1),
            COMPILATION(2),
            EXECUTION(3),
            TEARDOWN(4),
            VALIDATION(5);

            private static final zzgi<zza> zzg = new zzjv();
            private final int zzh;

            @Override // com.google.android.gms.internal.mlkit_vision_pose_common.zzgf
            public final int zza() {
                return this.zzh;
            }

            public static zzgh zzb() {
                return zzju.zza;
            }

            @Override // java.lang.Enum
            public final String toString() {
                StringBuilder sb = new StringBuilder("<");
                sb.append(getClass().getName()).append('@').append(Integer.toHexString(System.identityHashCode(this)));
                sb.append(" number=").append(this.zzh);
                return sb.append(" name=").append(name()).append('>').toString();
            }

            zza(int i) {
                this.zzh = i;
            }
        }

        /* JADX INFO: compiled from: com.google.mlkit:pose-detection-common@@17.0.0 */
        public static final class zzb extends zzgd<zzb, zza> implements zzhn {
            private static final zzb zzf;
            private static volatile zzhv<zzb> zzg;
            private int zzc;
            private int zzd;
            private int zze;

            private zzb() {
            }

            /* JADX INFO: compiled from: com.google.mlkit:pose-detection-common@@17.0.0 */
            public static final class zza extends zzgd.zza<zzb, zza> implements zzhn {
                private zza() {
                    super(zzb.zzf);
                }

                /* synthetic */ zza(zzjr zzjrVar) {
                    this();
                }
            }

            /* JADX WARN: Multi-variable type inference failed */
            /* JADX WARN: Type inference failed for: r1v13 */
            /* JADX WARN: Type inference failed for: r1v14, types: [com.google.android.gms.internal.mlkit_vision_pose_common.zzgd$zzc, com.google.android.gms.internal.mlkit_vision_pose_common.zzhv<com.google.android.gms.internal.mlkit_vision_pose_common.zzjp$zzc$zzb>] */
            /* JADX WARN: Type inference failed for: r1v17 */
            /* JADX WARN: Type inference failed for: r1v18 */
            @Override // com.google.android.gms.internal.mlkit_vision_pose_common.zzgd
            protected final Object zza(int i, Object obj, Object obj2) {
                ?? r1;
                zzjr zzjrVar = null;
                switch (zzjr.zza[i - 1]) {
                    case 1:
                        return new zzb();
                    case 2:
                        return new zza(zzjrVar);
                    case 3:
                        return zza(zzf, "\u0001\u0002\u0000\u0001\u0001\u0002\u0002\u0000\u0000\u0000\u0001င\u0000\u0002င\u0001", new Object[]{"zzc", "zzd", "zze"});
                    case 4:
                        return zzf;
                    case 5:
                        zzhv<zzb> zzhvVar = zzg;
                        Object obj3 = zzhvVar;
                        if (zzhvVar == null) {
                            synchronized (zzb.class) {
                                zzhv<zzb> zzhvVar2 = zzg;
                                r1 = zzhvVar2;
                                if (zzhvVar2 == null) {
                                    ?? zzcVar = new zzgd.zzc(zzf);
                                    zzg = zzcVar;
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

            static {
                zzb zzbVar = new zzb();
                zzf = zzbVar;
                zzgd.zza((Class<zzb>) zzb.class, zzbVar);
            }
        }

        /* JADX INFO: compiled from: com.google.mlkit:pose-detection-common@@17.0.0 */
        public static final class zzd extends zzgd<zzd, zza> implements zzhn {
            private static final zzd zzg;
            private static volatile zzhv<zzd> zzh;
            private int zzc;
            private zzb zzd;
            private zzb zze;
            private boolean zzf;

            private zzd() {
            }

            /* JADX INFO: compiled from: com.google.mlkit:pose-detection-common@@17.0.0 */
            public static final class zza extends zzgd.zza<zzd, zza> implements zzhn {
                private zza() {
                    super(zzd.zzg);
                }

                /* synthetic */ zza(zzjr zzjrVar) {
                    this();
                }
            }

            /* JADX WARN: Multi-variable type inference failed */
            /* JADX WARN: Type inference failed for: r2v13 */
            /* JADX WARN: Type inference failed for: r2v14, types: [com.google.android.gms.internal.mlkit_vision_pose_common.zzgd$zzc, com.google.android.gms.internal.mlkit_vision_pose_common.zzhv<com.google.android.gms.internal.mlkit_vision_pose_common.zzjp$zzc$zzd>] */
            /* JADX WARN: Type inference failed for: r2v17 */
            /* JADX WARN: Type inference failed for: r2v18 */
            @Override // com.google.android.gms.internal.mlkit_vision_pose_common.zzgd
            protected final Object zza(int i, Object obj, Object obj2) {
                ?? r2;
                zzjr zzjrVar = null;
                switch (zzjr.zza[i - 1]) {
                    case 1:
                        return new zzd();
                    case 2:
                        return new zza(zzjrVar);
                    case 3:
                        return zza(zzg, "\u0001\u0003\u0000\u0001\u0001\u0003\u0003\u0000\u0000\u0000\u0001ဉ\u0000\u0002ဉ\u0001\u0003ဇ\u0002", new Object[]{"zzc", "zzd", "zze", "zzf"});
                    case 4:
                        return zzg;
                    case 5:
                        zzhv<zzd> zzhvVar = zzh;
                        Object obj3 = zzhvVar;
                        if (zzhvVar == null) {
                            synchronized (zzd.class) {
                                zzhv<zzd> zzhvVar2 = zzh;
                                r2 = zzhvVar2;
                                if (zzhvVar2 == null) {
                                    ?? zzcVar = new zzgd.zzc(zzg);
                                    zzh = zzcVar;
                                    r2 = zzcVar;
                                }
                                break;
                            }
                            obj3 = r2;
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
                zzd zzdVar = new zzd();
                zzg = zzdVar;
                zzgd.zza((Class<zzd>) zzd.class, zzdVar);
            }
        }

        /* JADX INFO: compiled from: com.google.mlkit:pose-detection-common@@17.0.0 */
        public enum zze implements zzgf {
            UNKNOWN_STATUS(0),
            COMPLETED_EVENT(1),
            MISSING_END_EVENT(2),
            HANG(3),
            ABANDONED_FROM_HANG(4),
            FORCED_CRASH_FROM_HANG(5);

            private static final zzgi<zze> zzg = new zzjx();
            private final int zzh;

            @Override // com.google.android.gms.internal.mlkit_vision_pose_common.zzgf
            public final int zza() {
                return this.zzh;
            }

            public static zzgh zzb() {
                return zzjw.zza;
            }

            @Override // java.lang.Enum
            public final String toString() {
                StringBuilder sb = new StringBuilder("<");
                sb.append(getClass().getName()).append('@').append(Integer.toHexString(System.identityHashCode(this)));
                sb.append(" number=").append(this.zzh);
                return sb.append(" name=").append(name()).append('>').toString();
            }

            zze(int i) {
                this.zzh = i;
            }
        }

        private zzc() {
        }

        /* JADX INFO: renamed from: com.google.android.gms.internal.mlkit_vision_pose_common.zzjp$zzc$zzc, reason: collision with other inner class name */
        /* JADX INFO: compiled from: com.google.mlkit:pose-detection-common@@17.0.0 */
        public static final class C0073zzc extends zzgd.zza<zzc, C0073zzc> implements zzhn {
            private C0073zzc() {
                super(zzc.zzm);
            }

            /* synthetic */ C0073zzc(zzjr zzjrVar) {
                this();
            }
        }

        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Type inference failed for: r0v11 */
        /* JADX WARN: Type inference failed for: r0v12, types: [com.google.android.gms.internal.mlkit_vision_pose_common.zzgd$zzc, com.google.android.gms.internal.mlkit_vision_pose_common.zzhv<com.google.android.gms.internal.mlkit_vision_pose_common.zzjp$zzc>] */
        /* JADX WARN: Type inference failed for: r0v15 */
        /* JADX WARN: Type inference failed for: r0v16 */
        @Override // com.google.android.gms.internal.mlkit_vision_pose_common.zzgd
        protected final Object zza(int i, Object obj, Object obj2) {
            ?? r0;
            zzjr zzjrVar = null;
            switch (zzjr.zza[i - 1]) {
                case 1:
                    return new zzc();
                case 2:
                    return new C0073zzc(zzjrVar);
                case 3:
                    return zza(zzm, "\u0001\t\u0000\u0001\u0001\t\t\u0000\u0001\u0000\u0001ဌ\u0000\u0002ဌ\u0001\u0003ဈ\u0002\u0004ဈ\u0003\u0005ဉ\u0004\u0006ဉ\u0005\u0007င\u0006\b\u001b\tင\u0007", new Object[]{"zzc", "zzd", zza.zzb(), "zze", zze.zzb(), "zzf", "zzg", "zzh", "zzi", "zzj", "zzk", zzg.class, "zzl"});
                case 4:
                    return zzm;
                case 5:
                    zzhv<zzc> zzhvVar = zzn;
                    Object obj3 = zzhvVar;
                    if (zzhvVar == null) {
                        synchronized (zzc.class) {
                            zzhv<zzc> zzhvVar2 = zzn;
                            r0 = zzhvVar2;
                            if (zzhvVar2 == null) {
                                ?? zzcVar = new zzgd.zzc(zzm);
                                zzn = zzcVar;
                                r0 = zzcVar;
                            }
                            break;
                        }
                        obj3 = r0;
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
            zzc zzcVar = new zzc();
            zzm = zzcVar;
            zzgd.zza((Class<zzc>) zzc.class, zzcVar);
        }
    }

    /* JADX INFO: compiled from: com.google.mlkit:pose-detection-common@@17.0.0 */
    public static final class zzd extends zzgd<zzd, zza> implements zzhn {
        private static final zzd zzk;
        private static volatile zzhv<zzd> zzl;
        private int zzc;
        private int zzg;
        private int zzh;
        private int zzj;
        private String zzd = "";
        private String zze = "";
        private String zzf = "";
        private String zzi = "";

        private zzd() {
        }

        /* JADX INFO: compiled from: com.google.mlkit:pose-detection-common@@17.0.0 */
        public static final class zza extends zzgd.zza<zzd, zza> implements zzhn {
            private zza() {
                super(zzd.zzk);
            }

            /* synthetic */ zza(zzjr zzjrVar) {
                this();
            }
        }

        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Type inference failed for: r9v12 */
        /* JADX WARN: Type inference failed for: r9v13, types: [com.google.android.gms.internal.mlkit_vision_pose_common.zzgd$zzc, com.google.android.gms.internal.mlkit_vision_pose_common.zzhv<com.google.android.gms.internal.mlkit_vision_pose_common.zzjp$zzd>] */
        /* JADX WARN: Type inference failed for: r9v16 */
        /* JADX WARN: Type inference failed for: r9v17 */
        @Override // com.google.android.gms.internal.mlkit_vision_pose_common.zzgd
        protected final Object zza(int i, Object obj, Object obj2) {
            ?? r9;
            zzjr zzjrVar = null;
            switch (zzjr.zza[i - 1]) {
                case 1:
                    return new zzd();
                case 2:
                    return new zza(zzjrVar);
                case 3:
                    return zza(zzk, "\u0001\u0007\u0000\u0001\u0001\u0007\u0007\u0000\u0000\u0000\u0001ဈ\u0000\u0002ဈ\u0001\u0003ဈ\u0002\u0004င\u0003\u0005င\u0004\u0006ဈ\u0005\u0007င\u0006", new Object[]{"zzc", "zzd", "zze", "zzf", "zzg", "zzh", "zzi", "zzj"});
                case 4:
                    return zzk;
                case 5:
                    zzhv<zzd> zzhvVar = zzl;
                    Object obj3 = zzhvVar;
                    if (zzhvVar == null) {
                        synchronized (zzd.class) {
                            zzhv<zzd> zzhvVar2 = zzl;
                            r9 = zzhvVar2;
                            if (zzhvVar2 == null) {
                                ?? zzcVar = new zzgd.zzc(zzk);
                                zzl = zzcVar;
                                r9 = zzcVar;
                            }
                            break;
                        }
                        obj3 = r9;
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
            zzd zzdVar = new zzd();
            zzk = zzdVar;
            zzgd.zza((Class<zzd>) zzd.class, zzdVar);
        }
    }

    /* JADX INFO: compiled from: com.google.mlkit:pose-detection-common@@17.0.0 */
    public static final class zze extends zzgd<zze, zza> implements zzhn {
        private static final zze zze;
        private static volatile zzhv<zze> zzf;
        private int zzc;
        private int zzd;

        private zze() {
        }

        /* JADX INFO: compiled from: com.google.mlkit:pose-detection-common@@17.0.0 */
        public static final class zza extends zzgd.zza<zze, zza> implements zzhn {
            private zza() {
                super(zze.zze);
            }

            /* synthetic */ zza(zzjr zzjrVar) {
                this();
            }
        }

        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Type inference failed for: r1v13 */
        /* JADX WARN: Type inference failed for: r1v14, types: [com.google.android.gms.internal.mlkit_vision_pose_common.zzgd$zzc, com.google.android.gms.internal.mlkit_vision_pose_common.zzhv<com.google.android.gms.internal.mlkit_vision_pose_common.zzjp$zze>] */
        /* JADX WARN: Type inference failed for: r1v17 */
        /* JADX WARN: Type inference failed for: r1v18 */
        @Override // com.google.android.gms.internal.mlkit_vision_pose_common.zzgd
        protected final Object zza(int i, Object obj, Object obj2) {
            ?? r1;
            zzjr zzjrVar = null;
            switch (zzjr.zza[i - 1]) {
                case 1:
                    return new zze();
                case 2:
                    return new zza(zzjrVar);
                case 3:
                    return zza(zze, "\u0001\u0001\u0000\u0001\u0001\u0001\u0001\u0000\u0000\u0000\u0001င\u0000", new Object[]{"zzc", "zzd"});
                case 4:
                    return zze;
                case 5:
                    zzhv<zze> zzhvVar = zzf;
                    Object obj3 = zzhvVar;
                    if (zzhvVar == null) {
                        synchronized (zze.class) {
                            zzhv<zze> zzhvVar2 = zzf;
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

        static {
            zze zzeVar = new zze();
            zze = zzeVar;
            zzgd.zza((Class<zze>) zze.class, zzeVar);
        }
    }

    /* JADX INFO: compiled from: com.google.mlkit:pose-detection-common@@17.0.0 */
    public static final class zzf extends zzgd<zzf, zza> implements zzhn {
        private static final zzf zzo;
        private static volatile zzhv<zzf> zzp;
        private int zzc;
        private zzb zzd;
        private zzi zze;
        private zzd zzf;
        private int zzg;
        private zzc zzh;
        private zzl zzi;
        private long zzj;
        private long zzk;
        private boolean zzl;
        private int zzm;
        private byte zzn = 2;

        private zzf() {
        }

        /* JADX INFO: compiled from: com.google.mlkit:pose-detection-common@@17.0.0 */
        public static final class zza extends zzgd.zza<zzf, zza> implements zzhn {
            private zza() {
                super(zzf.zzo);
            }

            /* synthetic */ zza(zzjr zzjrVar) {
                this();
            }
        }

        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Type inference failed for: r13v12 */
        /* JADX WARN: Type inference failed for: r13v13, types: [com.google.android.gms.internal.mlkit_vision_pose_common.zzgd$zzc, com.google.android.gms.internal.mlkit_vision_pose_common.zzhv<com.google.android.gms.internal.mlkit_vision_pose_common.zzjp$zzf>] */
        /* JADX WARN: Type inference failed for: r13v18 */
        /* JADX WARN: Type inference failed for: r13v19 */
        @Override // com.google.android.gms.internal.mlkit_vision_pose_common.zzgd
        protected final Object zza(int i, Object obj, Object obj2) {
            ?? r13;
            zzjr zzjrVar = null;
            switch (zzjr.zza[i - 1]) {
                case 1:
                    return new zzf();
                case 2:
                    return new zza(zzjrVar);
                case 3:
                    return zza(zzo, "\u0001\n\u0000\u0001\u0001\n\n\u0000\u0000\u0001\u0001ဉ\u0000\u0002ဉ\u0001\u0003ဌ\u0003\u0004ဉ\u0004\u0005ᐉ\u0005\u0006ဂ\u0006\u0007ဂ\u0007\bဇ\b\tင\t\nဉ\u0002", new Object[]{"zzc", "zzd", "zze", "zzg", zzjz.zzb(), "zzh", "zzi", "zzj", "zzk", "zzl", "zzm", "zzf"});
                case 4:
                    return zzo;
                case 5:
                    zzhv<zzf> zzhvVar = zzp;
                    Object obj3 = zzhvVar;
                    if (zzhvVar == null) {
                        synchronized (zzf.class) {
                            zzhv<zzf> zzhvVar2 = zzp;
                            r13 = zzhvVar2;
                            if (zzhvVar2 == null) {
                                ?? zzcVar = new zzgd.zzc(zzo);
                                zzp = zzcVar;
                                r13 = zzcVar;
                            }
                            break;
                        }
                        obj3 = r13;
                    }
                    return obj3;
                case 6:
                    return Byte.valueOf(this.zzn);
                case 7:
                    this.zzn = (byte) (obj == null ? 0 : 1);
                    return null;
                default:
                    throw new UnsupportedOperationException();
            }
        }

        static {
            zzf zzfVar = new zzf();
            zzo = zzfVar;
            zzgd.zza((Class<zzf>) zzf.class, zzfVar);
        }
    }

    /* JADX INFO: compiled from: com.google.mlkit:pose-detection-common@@17.0.0 */
    public static final class zzg extends zzgd<zzg, zza> implements zzhn {
        private static final zzg zzd;
        private static volatile zzhv<zzg> zze;
        private zzgk zzc = zzk();

        private zzg() {
        }

        /* JADX INFO: compiled from: com.google.mlkit:pose-detection-common@@17.0.0 */
        public static final class zza extends zzgd.zza<zzg, zza> implements zzhn {
            private zza() {
                super(zzg.zzd);
            }

            /* synthetic */ zza(zzjr zzjrVar) {
                this();
            }
        }

        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Type inference failed for: r1v13 */
        /* JADX WARN: Type inference failed for: r1v14, types: [com.google.android.gms.internal.mlkit_vision_pose_common.zzgd$zzc, com.google.android.gms.internal.mlkit_vision_pose_common.zzhv<com.google.android.gms.internal.mlkit_vision_pose_common.zzjp$zzg>] */
        /* JADX WARN: Type inference failed for: r1v17 */
        /* JADX WARN: Type inference failed for: r1v18 */
        @Override // com.google.android.gms.internal.mlkit_vision_pose_common.zzgd
        protected final Object zza(int i, Object obj, Object obj2) {
            ?? r1;
            zzjr zzjrVar = null;
            switch (zzjr.zza[i - 1]) {
                case 1:
                    return new zzg();
                case 2:
                    return new zza(zzjrVar);
                case 3:
                    return zza(zzd, "\u0001\u0001\u0000\u0000\u0001\u0001\u0001\u0000\u0001\u0000\u0001\u0016", new Object[]{"zzc"});
                case 4:
                    return zzd;
                case 5:
                    zzhv<zzg> zzhvVar = zze;
                    Object obj3 = zzhvVar;
                    if (zzhvVar == null) {
                        synchronized (zzg.class) {
                            zzhv<zzg> zzhvVar2 = zze;
                            r1 = zzhvVar2;
                            if (zzhvVar2 == null) {
                                ?? zzcVar = new zzgd.zzc(zzd);
                                zze = zzcVar;
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

        static {
            zzg zzgVar = new zzg();
            zzd = zzgVar;
            zzgd.zza((Class<zzg>) zzg.class, zzgVar);
        }
    }

    /* JADX INFO: compiled from: com.google.mlkit:pose-detection-common@@17.0.0 */
    public static final class zzh extends zzgd<zzh, zza> implements zzhn {
        private static final zzh zzf;
        private static volatile zzhv<zzh> zzg;
        private int zzc;
        private String zzd = "";
        private int zze;

        private zzh() {
        }

        /* JADX INFO: compiled from: com.google.mlkit:pose-detection-common@@17.0.0 */
        public static final class zza extends zzgd.zza<zzh, zza> implements zzhn {
            private zza() {
                super(zzh.zzf);
            }

            /* synthetic */ zza(zzjr zzjrVar) {
                this();
            }
        }

        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Type inference failed for: r1v13 */
        /* JADX WARN: Type inference failed for: r1v14, types: [com.google.android.gms.internal.mlkit_vision_pose_common.zzgd$zzc, com.google.android.gms.internal.mlkit_vision_pose_common.zzhv<com.google.android.gms.internal.mlkit_vision_pose_common.zzjp$zzh>] */
        /* JADX WARN: Type inference failed for: r1v17 */
        /* JADX WARN: Type inference failed for: r1v18 */
        @Override // com.google.android.gms.internal.mlkit_vision_pose_common.zzgd
        protected final Object zza(int i, Object obj, Object obj2) {
            ?? r1;
            zzjr zzjrVar = null;
            switch (zzjr.zza[i - 1]) {
                case 1:
                    return new zzh();
                case 2:
                    return new zza(zzjrVar);
                case 3:
                    return zza(zzf, "\u0001\u0002\u0000\u0001\u0001\u0002\u0002\u0000\u0000\u0000\u0001ဈ\u0000\u0002င\u0001", new Object[]{"zzc", "zzd", "zze"});
                case 4:
                    return zzf;
                case 5:
                    zzhv<zzh> zzhvVar = zzg;
                    Object obj3 = zzhvVar;
                    if (zzhvVar == null) {
                        synchronized (zzh.class) {
                            zzhv<zzh> zzhvVar2 = zzg;
                            r1 = zzhvVar2;
                            if (zzhvVar2 == null) {
                                ?? zzcVar = new zzgd.zzc(zzf);
                                zzg = zzcVar;
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

        static {
            zzh zzhVar = new zzh();
            zzf = zzhVar;
            zzgd.zza((Class<zzh>) zzh.class, zzhVar);
        }
    }

    /* JADX INFO: compiled from: com.google.mlkit:pose-detection-common@@17.0.0 */
    public static final class zzi extends zzgd<zzi, zzb> implements zzhn {
        private static final zzi zzd;
        private static volatile zzhv<zzi> zze;
        private zzgm<zza> zzc = zzl();

        /* JADX INFO: compiled from: com.google.mlkit:pose-detection-common@@17.0.0 */
        public static final class zza extends zzgd<zza, C0074zza> implements zzhn {
            private static final zza zzh;
            private static volatile zzhv<zza> zzi;
            private int zzc;
            private int zze;
            private long zzg;
            private String zzd = "";
            private String zzf = "";

            private zza() {
            }

            /* JADX INFO: renamed from: com.google.android.gms.internal.mlkit_vision_pose_common.zzjp$zzi$zza$zza, reason: collision with other inner class name */
            /* JADX INFO: compiled from: com.google.mlkit:pose-detection-common@@17.0.0 */
            public static final class C0074zza extends zzgd.zza<zza, C0074zza> implements zzhn {
                private C0074zza() {
                    super(zza.zzh);
                }

                /* synthetic */ C0074zza(zzjr zzjrVar) {
                    this();
                }
            }

            /* JADX WARN: Multi-variable type inference failed */
            /* JADX WARN: Type inference failed for: r3v13 */
            /* JADX WARN: Type inference failed for: r3v14, types: [com.google.android.gms.internal.mlkit_vision_pose_common.zzgd$zzc, com.google.android.gms.internal.mlkit_vision_pose_common.zzhv<com.google.android.gms.internal.mlkit_vision_pose_common.zzjp$zzi$zza>] */
            /* JADX WARN: Type inference failed for: r3v17 */
            /* JADX WARN: Type inference failed for: r3v18 */
            @Override // com.google.android.gms.internal.mlkit_vision_pose_common.zzgd
            protected final Object zza(int i, Object obj, Object obj2) {
                ?? r3;
                zzjr zzjrVar = null;
                switch (zzjr.zza[i - 1]) {
                    case 1:
                        return new zza();
                    case 2:
                        return new C0074zza(zzjrVar);
                    case 3:
                        return zza(zzh, "\u0001\u0004\u0000\u0001\u0001\u0004\u0004\u0000\u0000\u0000\u0001ဈ\u0000\u0002င\u0001\u0003ဈ\u0002\u0004ဂ\u0003", new Object[]{"zzc", "zzd", "zze", "zzf", "zzg"});
                    case 4:
                        return zzh;
                    case 5:
                        zzhv<zza> zzhvVar = zzi;
                        Object obj3 = zzhvVar;
                        if (zzhvVar == null) {
                            synchronized (zza.class) {
                                zzhv<zza> zzhvVar2 = zzi;
                                r3 = zzhvVar2;
                                if (zzhvVar2 == null) {
                                    ?? zzcVar = new zzgd.zzc(zzh);
                                    zzi = zzcVar;
                                    r3 = zzcVar;
                                }
                                break;
                            }
                            obj3 = r3;
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
                zza zzaVar = new zza();
                zzh = zzaVar;
                zzgd.zza((Class<zza>) zza.class, zzaVar);
            }
        }

        private zzi() {
        }

        /* JADX INFO: compiled from: com.google.mlkit:pose-detection-common@@17.0.0 */
        public static final class zzb extends zzgd.zza<zzi, zzb> implements zzhn {
            private zzb() {
                super(zzi.zzd);
            }

            /* synthetic */ zzb(zzjr zzjrVar) {
                this();
            }
        }

        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Type inference failed for: r1v13 */
        /* JADX WARN: Type inference failed for: r1v14, types: [com.google.android.gms.internal.mlkit_vision_pose_common.zzgd$zzc, com.google.android.gms.internal.mlkit_vision_pose_common.zzhv<com.google.android.gms.internal.mlkit_vision_pose_common.zzjp$zzi>] */
        /* JADX WARN: Type inference failed for: r1v17 */
        /* JADX WARN: Type inference failed for: r1v18 */
        @Override // com.google.android.gms.internal.mlkit_vision_pose_common.zzgd
        protected final Object zza(int i, Object obj, Object obj2) {
            ?? r1;
            zzjr zzjrVar = null;
            switch (zzjr.zza[i - 1]) {
                case 1:
                    return new zzi();
                case 2:
                    return new zzb(zzjrVar);
                case 3:
                    return zza(zzd, "\u0001\u0001\u0000\u0000\u0001\u0001\u0001\u0000\u0001\u0000\u0001\u001b", new Object[]{"zzc", zza.class});
                case 4:
                    return zzd;
                case 5:
                    zzhv<zzi> zzhvVar = zze;
                    Object obj3 = zzhvVar;
                    if (zzhvVar == null) {
                        synchronized (zzi.class) {
                            zzhv<zzi> zzhvVar2 = zze;
                            r1 = zzhvVar2;
                            if (zzhvVar2 == null) {
                                ?? zzcVar = new zzgd.zzc(zzd);
                                zze = zzcVar;
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

        static {
            zzi zziVar = new zzi();
            zzd = zziVar;
            zzgd.zza((Class<zzi>) zzi.class, zziVar);
        }
    }

    /* JADX INFO: compiled from: com.google.mlkit:pose-detection-common@@17.0.0 */
    public static final class zzj extends zzgd<zzj, zzb> implements zzhn {
        private static final zzj zzg;
        private static volatile zzhv<zzj> zzh;
        private int zzc;
        private int zzd;
        private zzh zze;
        private zze zzf;

        /* JADX INFO: compiled from: com.google.mlkit:pose-detection-common@@17.0.0 */
        public enum zza implements zzgf {
            DELEGATE_NONE(0),
            NNAPI(1),
            GPU(2),
            HEXAGON(3),
            EDGETPU(4);

            private static final zzgi<zza> zzf = new zzkc();
            private final int zzg;

            @Override // com.google.android.gms.internal.mlkit_vision_pose_common.zzgf
            public final int zza() {
                return this.zzg;
            }

            public static zzgh zzb() {
                return zzkb.zza;
            }

            @Override // java.lang.Enum
            public final String toString() {
                StringBuilder sb = new StringBuilder("<");
                sb.append(getClass().getName()).append('@').append(Integer.toHexString(System.identityHashCode(this)));
                sb.append(" number=").append(this.zzg);
                return sb.append(" name=").append(name()).append('>').toString();
            }

            zza(int i) {
                this.zzg = i;
            }
        }

        private zzj() {
        }

        /* JADX INFO: compiled from: com.google.mlkit:pose-detection-common@@17.0.0 */
        public static final class zzb extends zzgd.zza<zzj, zzb> implements zzhn {
            private zzb() {
                super(zzj.zzg);
            }

            /* synthetic */ zzb(zzjr zzjrVar) {
                this();
            }
        }

        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Type inference failed for: r3v13 */
        /* JADX WARN: Type inference failed for: r3v14, types: [com.google.android.gms.internal.mlkit_vision_pose_common.zzgd$zzc, com.google.android.gms.internal.mlkit_vision_pose_common.zzhv<com.google.android.gms.internal.mlkit_vision_pose_common.zzjp$zzj>] */
        /* JADX WARN: Type inference failed for: r3v17 */
        /* JADX WARN: Type inference failed for: r3v18 */
        @Override // com.google.android.gms.internal.mlkit_vision_pose_common.zzgd
        protected final Object zza(int i, Object obj, Object obj2) {
            ?? r3;
            zzjr zzjrVar = null;
            switch (zzjr.zza[i - 1]) {
                case 1:
                    return new zzj();
                case 2:
                    return new zzb(zzjrVar);
                case 3:
                    return zza(zzg, "\u0001\u0003\u0000\u0001\u0001\u0003\u0003\u0000\u0000\u0000\u0001ဌ\u0000\u0002ဉ\u0001\u0003ဉ\u0002", new Object[]{"zzc", "zzd", zza.zzb(), "zze", "zzf"});
                case 4:
                    return zzg;
                case 5:
                    zzhv<zzj> zzhvVar = zzh;
                    Object obj3 = zzhvVar;
                    if (zzhvVar == null) {
                        synchronized (zzj.class) {
                            zzhv<zzj> zzhvVar2 = zzh;
                            r3 = zzhvVar2;
                            if (zzhvVar2 == null) {
                                ?? zzcVar = new zzgd.zzc(zzg);
                                zzh = zzcVar;
                                r3 = zzcVar;
                            }
                            break;
                        }
                        obj3 = r3;
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
            zzj zzjVar = new zzj();
            zzg = zzjVar;
            zzgd.zza((Class<zzj>) zzj.class, zzjVar);
        }
    }

    /* JADX INFO: compiled from: com.google.mlkit:pose-detection-common@@17.0.0 */
    public static final class zzk extends zzgd<zzk, zza> implements zzhn {
        private static final zzk zzf;
        private static volatile zzhv<zzk> zzg;
        private int zzc;
        private int zzd;
        private float zze;

        /* JADX INFO: compiled from: com.google.mlkit:pose-detection-common@@17.0.0 */
        public enum zzb implements zzgf {
            UNKNOWN_METRIC(0),
            MEAN_ABSOLUTE_ERROR(1),
            MEAN_SQUARED_ERROR(2),
            ROOT_MEAN_SQUARED_ERROR(3);

            private static final zzgi<zzb> zze = new zzkd();
            private final int zzf;

            @Override // com.google.android.gms.internal.mlkit_vision_pose_common.zzgf
            public final int zza() {
                return this.zzf;
            }

            public static zzgh zzb() {
                return zzke.zza;
            }

            @Override // java.lang.Enum
            public final String toString() {
                StringBuilder sb = new StringBuilder("<");
                sb.append(getClass().getName()).append('@').append(Integer.toHexString(System.identityHashCode(this)));
                sb.append(" number=").append(this.zzf);
                return sb.append(" name=").append(name()).append('>').toString();
            }

            zzb(int i) {
                this.zzf = i;
            }
        }

        private zzk() {
        }

        /* JADX INFO: compiled from: com.google.mlkit:pose-detection-common@@17.0.0 */
        public static final class zza extends zzgd.zza<zzk, zza> implements zzhn {
            private zza() {
                super(zzk.zzf);
            }

            /* synthetic */ zza(zzjr zzjrVar) {
                this();
            }
        }

        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Type inference failed for: r2v13 */
        /* JADX WARN: Type inference failed for: r2v14, types: [com.google.android.gms.internal.mlkit_vision_pose_common.zzgd$zzc, com.google.android.gms.internal.mlkit_vision_pose_common.zzhv<com.google.android.gms.internal.mlkit_vision_pose_common.zzjp$zzk>] */
        /* JADX WARN: Type inference failed for: r2v17 */
        /* JADX WARN: Type inference failed for: r2v18 */
        @Override // com.google.android.gms.internal.mlkit_vision_pose_common.zzgd
        protected final Object zza(int i, Object obj, Object obj2) {
            ?? r2;
            zzjr zzjrVar = null;
            switch (zzjr.zza[i - 1]) {
                case 1:
                    return new zzk();
                case 2:
                    return new zza(zzjrVar);
                case 3:
                    return zza(zzf, "\u0001\u0002\u0000\u0001\u0001\u0002\u0002\u0000\u0000\u0000\u0001ဌ\u0000\u0002ခ\u0001", new Object[]{"zzc", "zzd", zzb.zzb(), "zze"});
                case 4:
                    return zzf;
                case 5:
                    zzhv<zzk> zzhvVar = zzg;
                    Object obj3 = zzhvVar;
                    if (zzhvVar == null) {
                        synchronized (zzk.class) {
                            zzhv<zzk> zzhvVar2 = zzg;
                            r2 = zzhvVar2;
                            if (zzhvVar2 == null) {
                                ?? zzcVar = new zzgd.zzc(zzf);
                                zzg = zzcVar;
                                r2 = zzcVar;
                            }
                            break;
                        }
                        obj3 = r2;
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
            zzk zzkVar = new zzk();
            zzf = zzkVar;
            zzgd.zza((Class<zzk>) zzk.class, zzkVar);
        }
    }

    /* JADX INFO: compiled from: com.google.mlkit:pose-detection-common@@17.0.0 */
    public static final class zzl extends zzgd.zze<zzl, zzb> implements zzhn {
        private static final zzl zzf;
        private static volatile zzhv<zzl> zzg;
        private byte zze = 2;
        private zzgm<zza> zzd = zzl();

        /* JADX INFO: compiled from: com.google.mlkit:pose-detection-common@@17.0.0 */
        public static final class zza extends zzgd<zza, C0075zza> implements zzhn {
            private static final zza zzg;
            private static volatile zzhv<zza> zzh;
            private int zzc;
            private int zzd;
            private int zze;
            private zzgm<zzk> zzf = zzl();

            private zza() {
            }

            /* JADX INFO: renamed from: com.google.android.gms.internal.mlkit_vision_pose_common.zzjp$zzl$zza$zza, reason: collision with other inner class name */
            /* JADX INFO: compiled from: com.google.mlkit:pose-detection-common@@17.0.0 */
            public static final class C0075zza extends zzgd.zza<zza, C0075zza> implements zzhn {
                private C0075zza() {
                    super(zza.zzg);
                }

                /* synthetic */ C0075zza(zzjr zzjrVar) {
                    this();
                }
            }

            /* JADX WARN: Multi-variable type inference failed */
            /* JADX WARN: Type inference failed for: r3v13 */
            /* JADX WARN: Type inference failed for: r3v14, types: [com.google.android.gms.internal.mlkit_vision_pose_common.zzgd$zzc, com.google.android.gms.internal.mlkit_vision_pose_common.zzhv<com.google.android.gms.internal.mlkit_vision_pose_common.zzjp$zzl$zza>] */
            /* JADX WARN: Type inference failed for: r3v17 */
            /* JADX WARN: Type inference failed for: r3v18 */
            @Override // com.google.android.gms.internal.mlkit_vision_pose_common.zzgd
            protected final Object zza(int i, Object obj, Object obj2) {
                ?? r3;
                zzjr zzjrVar = null;
                switch (zzjr.zza[i - 1]) {
                    case 1:
                        return new zza();
                    case 2:
                        return new C0075zza(zzjrVar);
                    case 3:
                        return zza(zzg, "\u0001\u0003\u0000\u0001\u0001\u0003\u0003\u0000\u0001\u0000\u0001င\u0000\u0002င\u0001\u0003\u001b", new Object[]{"zzc", "zzd", "zze", "zzf", zzk.class});
                    case 4:
                        return zzg;
                    case 5:
                        zzhv<zza> zzhvVar = zzh;
                        Object obj3 = zzhvVar;
                        if (zzhvVar == null) {
                            synchronized (zza.class) {
                                zzhv<zza> zzhvVar2 = zzh;
                                r3 = zzhvVar2;
                                if (zzhvVar2 == null) {
                                    ?? zzcVar = new zzgd.zzc(zzg);
                                    zzh = zzcVar;
                                    r3 = zzcVar;
                                }
                                break;
                            }
                            obj3 = r3;
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
                zza zzaVar = new zza();
                zzg = zzaVar;
                zzgd.zza((Class<zza>) zza.class, zzaVar);
            }
        }

        private zzl() {
        }

        /* JADX INFO: compiled from: com.google.mlkit:pose-detection-common@@17.0.0 */
        public static final class zzb extends zzgd.zzb<zzl, zzb> implements zzhn {
            private zzb() {
                super(zzl.zzf);
            }

            /* synthetic */ zzb(zzjr zzjrVar) {
                this();
            }
        }

        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Type inference failed for: r2v13 */
        /* JADX WARN: Type inference failed for: r2v14, types: [com.google.android.gms.internal.mlkit_vision_pose_common.zzgd$zzc, com.google.android.gms.internal.mlkit_vision_pose_common.zzhv<com.google.android.gms.internal.mlkit_vision_pose_common.zzjp$zzl>] */
        /* JADX WARN: Type inference failed for: r2v19 */
        /* JADX WARN: Type inference failed for: r2v20 */
        @Override // com.google.android.gms.internal.mlkit_vision_pose_common.zzgd
        protected final Object zza(int i, Object obj, Object obj2) {
            ?? r2;
            zzjr zzjrVar = null;
            switch (zzjr.zza[i - 1]) {
                case 1:
                    return new zzl();
                case 2:
                    return new zzb(zzjrVar);
                case 3:
                    return zza(zzf, "\u0001\u0001\u0000\u0000\u0001\u0001\u0001\u0000\u0001\u0000\u0001\u001b", new Object[]{"zzd", zza.class});
                case 4:
                    return zzf;
                case 5:
                    zzhv<zzl> zzhvVar = zzg;
                    Object obj3 = zzhvVar;
                    if (zzhvVar == null) {
                        synchronized (zzl.class) {
                            zzhv<zzl> zzhvVar2 = zzg;
                            r2 = zzhvVar2;
                            if (zzhvVar2 == null) {
                                ?? zzcVar = new zzgd.zzc(zzf);
                                zzg = zzcVar;
                                r2 = zzcVar;
                            }
                            break;
                        }
                        obj3 = r2;
                    }
                    return obj3;
                case 6:
                    return Byte.valueOf(this.zze);
                case 7:
                    this.zze = (byte) (obj == null ? 0 : 1);
                    return null;
                default:
                    throw new UnsupportedOperationException();
            }
        }

        static {
            zzl zzlVar = new zzl();
            zzf = zzlVar;
            zzgd.zza((Class<zzl>) zzl.class, zzlVar);
        }
    }
}
