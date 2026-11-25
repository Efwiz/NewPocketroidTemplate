package com.google.android.gms.internal.mlkit_language_id;

/* JADX INFO: compiled from: com.google.mlkit:language-id@@16.1.1 */
/* JADX INFO: loaded from: classes9.dex */
public final class zzid {

    /* JADX INFO: compiled from: com.google.mlkit:language-id@@16.1.1 */
    public static final class zza extends zzeo<zza, C0048zza> implements zzgb {
        private static final zza zzf;
        private static volatile zzgj<zza> zzg;
        private int zzc;
        private int zzd;
        private zzj zze;

        /* JADX INFO: compiled from: com.google.mlkit:language-id@@16.1.1 */
        public enum zzb implements zzet {
            UNKNOWN_ENGINE(0),
            TFLITE(1);

            private static final zzes<zzb> zzc = new zzie();
            private final int zzd;

            @Override // com.google.android.gms.internal.mlkit_language_id.zzet
            public final int zza() {
                return this.zzd;
            }

            public static zzev zzb() {
                return zzif.zza;
            }

            @Override // java.lang.Enum
            public final String toString() {
                StringBuilder sb = new StringBuilder("<");
                sb.append(getClass().getName()).append('@').append(Integer.toHexString(System.identityHashCode(this)));
                sb.append(" number=").append(this.zzd);
                return sb.append(" name=").append(name()).append('>').toString();
            }

            zzb(int i) {
                this.zzd = i;
            }
        }

        private zza() {
        }

        /* JADX INFO: renamed from: com.google.android.gms.internal.mlkit_language_id.zzid$zza$zza, reason: collision with other inner class name */
        /* JADX INFO: compiled from: com.google.mlkit:language-id@@16.1.1 */
        public static final class C0048zza extends zzeo.zzb<zza, C0048zza> implements zzgb {
            private C0048zza() {
                super(zza.zzf);
            }

            /* synthetic */ C0048zza(zzic zzicVar) {
                this();
            }
        }

        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Type inference failed for: r2v13 */
        /* JADX WARN: Type inference failed for: r2v14, types: [com.google.android.gms.internal.mlkit_language_id.zzeo$zza, com.google.android.gms.internal.mlkit_language_id.zzgj<com.google.android.gms.internal.mlkit_language_id.zzid$zza>] */
        /* JADX WARN: Type inference failed for: r2v17 */
        /* JADX WARN: Type inference failed for: r2v18 */
        @Override // com.google.android.gms.internal.mlkit_language_id.zzeo
        protected final Object zza(int i, Object obj, Object obj2) {
            ?? r2;
            zzic zzicVar = null;
            switch (zzic.zza[i - 1]) {
                case 1:
                    return new zza();
                case 2:
                    return new C0048zza(zzicVar);
                case 3:
                    return zza(zzf, "\u0001\u0002\u0000\u0001\u0001\u0002\u0002\u0000\u0000\u0000\u0001ဌ\u0000\u0002ဉ\u0001", new Object[]{"zzc", "zzd", zzb.zzb(), "zze"});
                case 4:
                    return zzf;
                case 5:
                    zzgj<zza> zzgjVar = zzg;
                    Object obj3 = zzgjVar;
                    if (zzgjVar == null) {
                        synchronized (zza.class) {
                            zzgj<zza> zzgjVar2 = zzg;
                            r2 = zzgjVar2;
                            if (zzgjVar2 == null) {
                                ?? zzaVar = new zzeo.zza(zzf);
                                zzg = zzaVar;
                                r2 = zzaVar;
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
            zzeo.zza((Class<zza>) zza.class, zzaVar);
        }
    }

    /* JADX INFO: compiled from: com.google.mlkit:language-id@@16.1.1 */
    public static final class zzb extends zzeo<zzb, zza> implements zzgb {
        private static final zzb zzn;
        private static volatile zzgj<zzb> zzo;
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

        /* JADX INFO: compiled from: com.google.mlkit:language-id@@16.1.1 */
        public static final class zza extends zzeo.zzb<zzb, zza> implements zzgb {
            private zza() {
                super(zzb.zzn);
            }

            /* synthetic */ zza(zzic zzicVar) {
                this();
            }
        }

        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Type inference failed for: r12v12 */
        /* JADX WARN: Type inference failed for: r12v13, types: [com.google.android.gms.internal.mlkit_language_id.zzeo$zza, com.google.android.gms.internal.mlkit_language_id.zzgj<com.google.android.gms.internal.mlkit_language_id.zzid$zzb>] */
        /* JADX WARN: Type inference failed for: r12v16 */
        /* JADX WARN: Type inference failed for: r12v17 */
        @Override // com.google.android.gms.internal.mlkit_language_id.zzeo
        protected final Object zza(int i, Object obj, Object obj2) {
            ?? r12;
            zzic zzicVar = null;
            switch (zzic.zza[i - 1]) {
                case 1:
                    return new zzb();
                case 2:
                    return new zza(zzicVar);
                case 3:
                    return zza(zzn, "\u0001\n\u0000\u0001\u0001\n\n\u0000\u0000\u0000\u0001င\u0000\u0002ဈ\u0001\u0003ဈ\u0002\u0004ဈ\u0003\u0005ဈ\u0004\u0006ဈ\u0005\u0007ဈ\u0006\bဈ\u0007\tဈ\b\nဈ\t", new Object[]{"zzc", "zzd", "zze", "zzf", "zzg", "zzh", "zzi", "zzj", "zzk", "zzl", "zzm"});
                case 4:
                    return zzn;
                case 5:
                    zzgj<zzb> zzgjVar = zzo;
                    Object obj3 = zzgjVar;
                    if (zzgjVar == null) {
                        synchronized (zzb.class) {
                            zzgj<zzb> zzgjVar2 = zzo;
                            r12 = zzgjVar2;
                            if (zzgjVar2 == null) {
                                ?? zzaVar = new zzeo.zza(zzn);
                                zzo = zzaVar;
                                r12 = zzaVar;
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
            zzeo.zza((Class<zzb>) zzb.class, zzbVar);
        }
    }

    /* JADX INFO: compiled from: com.google.mlkit:language-id@@16.1.1 */
    public static final class zzc extends zzeo<zzc, zzb> implements zzgb {
        private static final zzc zzm;
        private static volatile zzgj<zzc> zzn;
        private int zzc;
        private int zzd;
        private int zze;
        private zza zzh;
        private zzd zzi;
        private int zzj;
        private int zzl;
        private String zzf = "";
        private String zzg = "";
        private zzew<zzg> zzk = zzl();

        /* JADX INFO: compiled from: com.google.mlkit:language-id@@16.1.1 */
        public enum zza implements zzet {
            UNKNOWN_ACTION(0),
            INITIALIZATION(1),
            COMPILATION(2),
            EXECUTION(3),
            TEARDOWN(4),
            VALIDATION(5);

            private static final zzes<zza> zzg = new zzig();
            private final int zzh;

            @Override // com.google.android.gms.internal.mlkit_language_id.zzet
            public final int zza() {
                return this.zzh;
            }

            public static zzev zzb() {
                return zzih.zza;
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

        /* JADX INFO: renamed from: com.google.android.gms.internal.mlkit_language_id.zzid$zzc$zzc, reason: collision with other inner class name */
        /* JADX INFO: compiled from: com.google.mlkit:language-id@@16.1.1 */
        public static final class C0049zzc extends zzeo<C0049zzc, zza> implements zzgb {
            private static final C0049zzc zzf;
            private static volatile zzgj<C0049zzc> zzg;
            private int zzc;
            private int zzd;
            private int zze;

            private C0049zzc() {
            }

            /* JADX INFO: renamed from: com.google.android.gms.internal.mlkit_language_id.zzid$zzc$zzc$zza */
            /* JADX INFO: compiled from: com.google.mlkit:language-id@@16.1.1 */
            public static final class zza extends zzeo.zzb<C0049zzc, zza> implements zzgb {
                private zza() {
                    super(C0049zzc.zzf);
                }

                /* synthetic */ zza(zzic zzicVar) {
                    this();
                }
            }

            /* JADX WARN: Multi-variable type inference failed */
            /* JADX WARN: Type inference failed for: r1v13 */
            /* JADX WARN: Type inference failed for: r1v14, types: [com.google.android.gms.internal.mlkit_language_id.zzeo$zza, com.google.android.gms.internal.mlkit_language_id.zzgj<com.google.android.gms.internal.mlkit_language_id.zzid$zzc$zzc>] */
            /* JADX WARN: Type inference failed for: r1v17 */
            /* JADX WARN: Type inference failed for: r1v18 */
            @Override // com.google.android.gms.internal.mlkit_language_id.zzeo
            protected final Object zza(int i, Object obj, Object obj2) {
                ?? r1;
                zzic zzicVar = null;
                switch (zzic.zza[i - 1]) {
                    case 1:
                        return new C0049zzc();
                    case 2:
                        return new zza(zzicVar);
                    case 3:
                        return zza(zzf, "\u0001\u0002\u0000\u0001\u0001\u0002\u0002\u0000\u0000\u0000\u0001င\u0000\u0002င\u0001", new Object[]{"zzc", "zzd", "zze"});
                    case 4:
                        return zzf;
                    case 5:
                        zzgj<C0049zzc> zzgjVar = zzg;
                        Object obj3 = zzgjVar;
                        if (zzgjVar == null) {
                            synchronized (C0049zzc.class) {
                                zzgj<C0049zzc> zzgjVar2 = zzg;
                                r1 = zzgjVar2;
                                if (zzgjVar2 == null) {
                                    ?? zzaVar = new zzeo.zza(zzf);
                                    zzg = zzaVar;
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

            static {
                C0049zzc c0049zzc = new C0049zzc();
                zzf = c0049zzc;
                zzeo.zza((Class<C0049zzc>) C0049zzc.class, c0049zzc);
            }
        }

        /* JADX INFO: compiled from: com.google.mlkit:language-id@@16.1.1 */
        public static final class zzd extends zzeo<zzd, zza> implements zzgb {
            private static final zzd zzg;
            private static volatile zzgj<zzd> zzh;
            private int zzc;
            private C0049zzc zzd;
            private C0049zzc zze;
            private boolean zzf;

            private zzd() {
            }

            /* JADX INFO: compiled from: com.google.mlkit:language-id@@16.1.1 */
            public static final class zza extends zzeo.zzb<zzd, zza> implements zzgb {
                private zza() {
                    super(zzd.zzg);
                }

                /* synthetic */ zza(zzic zzicVar) {
                    this();
                }
            }

            /* JADX WARN: Multi-variable type inference failed */
            /* JADX WARN: Type inference failed for: r2v13 */
            /* JADX WARN: Type inference failed for: r2v14, types: [com.google.android.gms.internal.mlkit_language_id.zzeo$zza, com.google.android.gms.internal.mlkit_language_id.zzgj<com.google.android.gms.internal.mlkit_language_id.zzid$zzc$zzd>] */
            /* JADX WARN: Type inference failed for: r2v17 */
            /* JADX WARN: Type inference failed for: r2v18 */
            @Override // com.google.android.gms.internal.mlkit_language_id.zzeo
            protected final Object zza(int i, Object obj, Object obj2) {
                ?? r2;
                zzic zzicVar = null;
                switch (zzic.zza[i - 1]) {
                    case 1:
                        return new zzd();
                    case 2:
                        return new zza(zzicVar);
                    case 3:
                        return zza(zzg, "\u0001\u0003\u0000\u0001\u0001\u0003\u0003\u0000\u0000\u0000\u0001ဉ\u0000\u0002ဉ\u0001\u0003ဇ\u0002", new Object[]{"zzc", "zzd", "zze", "zzf"});
                    case 4:
                        return zzg;
                    case 5:
                        zzgj<zzd> zzgjVar = zzh;
                        Object obj3 = zzgjVar;
                        if (zzgjVar == null) {
                            synchronized (zzd.class) {
                                zzgj<zzd> zzgjVar2 = zzh;
                                r2 = zzgjVar2;
                                if (zzgjVar2 == null) {
                                    ?? zzaVar = new zzeo.zza(zzg);
                                    zzh = zzaVar;
                                    r2 = zzaVar;
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
                zzeo.zza((Class<zzd>) zzd.class, zzdVar);
            }
        }

        /* JADX INFO: compiled from: com.google.mlkit:language-id@@16.1.1 */
        public enum zze implements zzet {
            UNKNOWN_STATUS(0),
            COMPLETED_EVENT(1),
            MISSING_END_EVENT(2),
            HANG(3),
            ABANDONED_FROM_HANG(4),
            FORCED_CRASH_FROM_HANG(5);

            private static final zzes<zze> zzg = new zzii();
            private final int zzh;

            @Override // com.google.android.gms.internal.mlkit_language_id.zzet
            public final int zza() {
                return this.zzh;
            }

            public static zzev zzb() {
                return zzik.zza;
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

        /* JADX INFO: compiled from: com.google.mlkit:language-id@@16.1.1 */
        public static final class zzb extends zzeo.zzb<zzc, zzb> implements zzgb {
            private zzb() {
                super(zzc.zzm);
            }

            /* synthetic */ zzb(zzic zzicVar) {
                this();
            }
        }

        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Type inference failed for: r0v11 */
        /* JADX WARN: Type inference failed for: r0v12, types: [com.google.android.gms.internal.mlkit_language_id.zzeo$zza, com.google.android.gms.internal.mlkit_language_id.zzgj<com.google.android.gms.internal.mlkit_language_id.zzid$zzc>] */
        /* JADX WARN: Type inference failed for: r0v15 */
        /* JADX WARN: Type inference failed for: r0v16 */
        @Override // com.google.android.gms.internal.mlkit_language_id.zzeo
        protected final Object zza(int i, Object obj, Object obj2) {
            ?? r0;
            zzic zzicVar = null;
            switch (zzic.zza[i - 1]) {
                case 1:
                    return new zzc();
                case 2:
                    return new zzb(zzicVar);
                case 3:
                    return zza(zzm, "\u0001\t\u0000\u0001\u0001\t\t\u0000\u0001\u0000\u0001ဌ\u0000\u0002ဌ\u0001\u0003ဈ\u0002\u0004ဈ\u0003\u0005ဉ\u0004\u0006ဉ\u0005\u0007င\u0006\b\u001b\tင\u0007", new Object[]{"zzc", "zzd", zza.zzb(), "zze", zze.zzb(), "zzf", "zzg", "zzh", "zzi", "zzj", "zzk", zzg.class, "zzl"});
                case 4:
                    return zzm;
                case 5:
                    zzgj<zzc> zzgjVar = zzn;
                    Object obj3 = zzgjVar;
                    if (zzgjVar == null) {
                        synchronized (zzc.class) {
                            zzgj<zzc> zzgjVar2 = zzn;
                            r0 = zzgjVar2;
                            if (zzgjVar2 == null) {
                                ?? zzaVar = new zzeo.zza(zzm);
                                zzn = zzaVar;
                                r0 = zzaVar;
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
            zzeo.zza((Class<zzc>) zzc.class, zzcVar);
        }
    }

    /* JADX INFO: compiled from: com.google.mlkit:language-id@@16.1.1 */
    public static final class zzd extends zzeo<zzd, zza> implements zzgb {
        private static final zzd zzk;
        private static volatile zzgj<zzd> zzl;
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

        /* JADX INFO: compiled from: com.google.mlkit:language-id@@16.1.1 */
        public static final class zza extends zzeo.zzb<zzd, zza> implements zzgb {
            private zza() {
                super(zzd.zzk);
            }

            /* synthetic */ zza(zzic zzicVar) {
                this();
            }
        }

        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Type inference failed for: r9v12 */
        /* JADX WARN: Type inference failed for: r9v13, types: [com.google.android.gms.internal.mlkit_language_id.zzeo$zza, com.google.android.gms.internal.mlkit_language_id.zzgj<com.google.android.gms.internal.mlkit_language_id.zzid$zzd>] */
        /* JADX WARN: Type inference failed for: r9v16 */
        /* JADX WARN: Type inference failed for: r9v17 */
        @Override // com.google.android.gms.internal.mlkit_language_id.zzeo
        protected final Object zza(int i, Object obj, Object obj2) {
            ?? r9;
            zzic zzicVar = null;
            switch (zzic.zza[i - 1]) {
                case 1:
                    return new zzd();
                case 2:
                    return new zza(zzicVar);
                case 3:
                    return zza(zzk, "\u0001\u0007\u0000\u0001\u0001\u0007\u0007\u0000\u0000\u0000\u0001ဈ\u0000\u0002ဈ\u0001\u0003ဈ\u0002\u0004င\u0003\u0005င\u0004\u0006ဈ\u0005\u0007င\u0006", new Object[]{"zzc", "zzd", "zze", "zzf", "zzg", "zzh", "zzi", "zzj"});
                case 4:
                    return zzk;
                case 5:
                    zzgj<zzd> zzgjVar = zzl;
                    Object obj3 = zzgjVar;
                    if (zzgjVar == null) {
                        synchronized (zzd.class) {
                            zzgj<zzd> zzgjVar2 = zzl;
                            r9 = zzgjVar2;
                            if (zzgjVar2 == null) {
                                ?? zzaVar = new zzeo.zza(zzk);
                                zzl = zzaVar;
                                r9 = zzaVar;
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
            zzeo.zza((Class<zzd>) zzd.class, zzdVar);
        }
    }

    /* JADX INFO: compiled from: com.google.mlkit:language-id@@16.1.1 */
    public static final class zze extends zzeo<zze, zza> implements zzgb {
        private static final zze zze;
        private static volatile zzgj<zze> zzf;
        private int zzc;
        private int zzd;

        private zze() {
        }

        /* JADX INFO: compiled from: com.google.mlkit:language-id@@16.1.1 */
        public static final class zza extends zzeo.zzb<zze, zza> implements zzgb {
            private zza() {
                super(zze.zze);
            }

            /* synthetic */ zza(zzic zzicVar) {
                this();
            }
        }

        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Type inference failed for: r1v13 */
        /* JADX WARN: Type inference failed for: r1v14, types: [com.google.android.gms.internal.mlkit_language_id.zzeo$zza, com.google.android.gms.internal.mlkit_language_id.zzgj<com.google.android.gms.internal.mlkit_language_id.zzid$zze>] */
        /* JADX WARN: Type inference failed for: r1v17 */
        /* JADX WARN: Type inference failed for: r1v18 */
        @Override // com.google.android.gms.internal.mlkit_language_id.zzeo
        protected final Object zza(int i, Object obj, Object obj2) {
            ?? r1;
            zzic zzicVar = null;
            switch (zzic.zza[i - 1]) {
                case 1:
                    return new zze();
                case 2:
                    return new zza(zzicVar);
                case 3:
                    return zza(zze, "\u0001\u0001\u0000\u0001\u0001\u0001\u0001\u0000\u0000\u0000\u0001င\u0000", new Object[]{"zzc", "zzd"});
                case 4:
                    return zze;
                case 5:
                    zzgj<zze> zzgjVar = zzf;
                    Object obj3 = zzgjVar;
                    if (zzgjVar == null) {
                        synchronized (zze.class) {
                            zzgj<zze> zzgjVar2 = zzf;
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

        static {
            zze zzeVar = new zze();
            zze = zzeVar;
            zzeo.zza((Class<zze>) zze.class, zzeVar);
        }
    }

    /* JADX INFO: compiled from: com.google.mlkit:language-id@@16.1.1 */
    public static final class zzf extends zzeo<zzf, zza> implements zzgb {
        private static final zzf zzo;
        private static volatile zzgj<zzf> zzp;
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

        /* JADX INFO: compiled from: com.google.mlkit:language-id@@16.1.1 */
        public static final class zza extends zzeo.zzb<zzf, zza> implements zzgb {
            private zza() {
                super(zzf.zzo);
            }

            /* synthetic */ zza(zzic zzicVar) {
                this();
            }
        }

        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Type inference failed for: r13v12 */
        /* JADX WARN: Type inference failed for: r13v13, types: [com.google.android.gms.internal.mlkit_language_id.zzeo$zza, com.google.android.gms.internal.mlkit_language_id.zzgj<com.google.android.gms.internal.mlkit_language_id.zzid$zzf>] */
        /* JADX WARN: Type inference failed for: r13v18 */
        /* JADX WARN: Type inference failed for: r13v19 */
        @Override // com.google.android.gms.internal.mlkit_language_id.zzeo
        protected final Object zza(int i, Object obj, Object obj2) {
            ?? r13;
            zzic zzicVar = null;
            switch (zzic.zza[i - 1]) {
                case 1:
                    return new zzf();
                case 2:
                    return new zza(zzicVar);
                case 3:
                    return zza(zzo, "\u0001\n\u0000\u0001\u0001\n\n\u0000\u0000\u0001\u0001ဉ\u0000\u0002ဉ\u0001\u0003ဌ\u0003\u0004ဉ\u0004\u0005ᐉ\u0005\u0006ဂ\u0006\u0007ဂ\u0007\bဇ\b\tင\t\nဉ\u0002", new Object[]{"zzc", "zzd", "zze", "zzg", zzij.zzb(), "zzh", "zzi", "zzj", "zzk", "zzl", "zzm", "zzf"});
                case 4:
                    return zzo;
                case 5:
                    zzgj<zzf> zzgjVar = zzp;
                    Object obj3 = zzgjVar;
                    if (zzgjVar == null) {
                        synchronized (zzf.class) {
                            zzgj<zzf> zzgjVar2 = zzp;
                            r13 = zzgjVar2;
                            if (zzgjVar2 == null) {
                                ?? zzaVar = new zzeo.zza(zzo);
                                zzp = zzaVar;
                                r13 = zzaVar;
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
            zzeo.zza((Class<zzf>) zzf.class, zzfVar);
        }
    }

    /* JADX INFO: compiled from: com.google.mlkit:language-id@@16.1.1 */
    public static final class zzg extends zzeo<zzg, zza> implements zzgb {
        private static final zzg zzd;
        private static volatile zzgj<zzg> zze;
        private zzeu zzc = zzk();

        private zzg() {
        }

        /* JADX INFO: compiled from: com.google.mlkit:language-id@@16.1.1 */
        public static final class zza extends zzeo.zzb<zzg, zza> implements zzgb {
            private zza() {
                super(zzg.zzd);
            }

            /* synthetic */ zza(zzic zzicVar) {
                this();
            }
        }

        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Type inference failed for: r1v13 */
        /* JADX WARN: Type inference failed for: r1v14, types: [com.google.android.gms.internal.mlkit_language_id.zzeo$zza, com.google.android.gms.internal.mlkit_language_id.zzgj<com.google.android.gms.internal.mlkit_language_id.zzid$zzg>] */
        /* JADX WARN: Type inference failed for: r1v17 */
        /* JADX WARN: Type inference failed for: r1v18 */
        @Override // com.google.android.gms.internal.mlkit_language_id.zzeo
        protected final Object zza(int i, Object obj, Object obj2) {
            ?? r1;
            zzic zzicVar = null;
            switch (zzic.zza[i - 1]) {
                case 1:
                    return new zzg();
                case 2:
                    return new zza(zzicVar);
                case 3:
                    return zza(zzd, "\u0001\u0001\u0000\u0000\u0001\u0001\u0001\u0000\u0001\u0000\u0001\u0016", new Object[]{"zzc"});
                case 4:
                    return zzd;
                case 5:
                    zzgj<zzg> zzgjVar = zze;
                    Object obj3 = zzgjVar;
                    if (zzgjVar == null) {
                        synchronized (zzg.class) {
                            zzgj<zzg> zzgjVar2 = zze;
                            r1 = zzgjVar2;
                            if (zzgjVar2 == null) {
                                ?? zzaVar = new zzeo.zza(zzd);
                                zze = zzaVar;
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

        static {
            zzg zzgVar = new zzg();
            zzd = zzgVar;
            zzeo.zza((Class<zzg>) zzg.class, zzgVar);
        }
    }

    /* JADX INFO: compiled from: com.google.mlkit:language-id@@16.1.1 */
    public static final class zzh extends zzeo<zzh, zza> implements zzgb {
        private static final zzh zzf;
        private static volatile zzgj<zzh> zzg;
        private int zzc;
        private String zzd = "";
        private int zze;

        private zzh() {
        }

        /* JADX INFO: compiled from: com.google.mlkit:language-id@@16.1.1 */
        public static final class zza extends zzeo.zzb<zzh, zza> implements zzgb {
            private zza() {
                super(zzh.zzf);
            }

            /* synthetic */ zza(zzic zzicVar) {
                this();
            }
        }

        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Type inference failed for: r1v13 */
        /* JADX WARN: Type inference failed for: r1v14, types: [com.google.android.gms.internal.mlkit_language_id.zzeo$zza, com.google.android.gms.internal.mlkit_language_id.zzgj<com.google.android.gms.internal.mlkit_language_id.zzid$zzh>] */
        /* JADX WARN: Type inference failed for: r1v17 */
        /* JADX WARN: Type inference failed for: r1v18 */
        @Override // com.google.android.gms.internal.mlkit_language_id.zzeo
        protected final Object zza(int i, Object obj, Object obj2) {
            ?? r1;
            zzic zzicVar = null;
            switch (zzic.zza[i - 1]) {
                case 1:
                    return new zzh();
                case 2:
                    return new zza(zzicVar);
                case 3:
                    return zza(zzf, "\u0001\u0002\u0000\u0001\u0001\u0002\u0002\u0000\u0000\u0000\u0001ဈ\u0000\u0002င\u0001", new Object[]{"zzc", "zzd", "zze"});
                case 4:
                    return zzf;
                case 5:
                    zzgj<zzh> zzgjVar = zzg;
                    Object obj3 = zzgjVar;
                    if (zzgjVar == null) {
                        synchronized (zzh.class) {
                            zzgj<zzh> zzgjVar2 = zzg;
                            r1 = zzgjVar2;
                            if (zzgjVar2 == null) {
                                ?? zzaVar = new zzeo.zza(zzf);
                                zzg = zzaVar;
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

        static {
            zzh zzhVar = new zzh();
            zzf = zzhVar;
            zzeo.zza((Class<zzh>) zzh.class, zzhVar);
        }
    }

    /* JADX INFO: compiled from: com.google.mlkit:language-id@@16.1.1 */
    public static final class zzi extends zzeo<zzi, zza> implements zzgb {
        private static final zzi zzd;
        private static volatile zzgj<zzi> zze;
        private zzew<zzb> zzc = zzl();

        /* JADX INFO: compiled from: com.google.mlkit:language-id@@16.1.1 */
        public static final class zzb extends zzeo<zzb, zza> implements zzgb {
            private static final zzb zzh;
            private static volatile zzgj<zzb> zzi;
            private int zzc;
            private int zze;
            private long zzg;
            private String zzd = "";
            private String zzf = "";

            private zzb() {
            }

            /* JADX INFO: compiled from: com.google.mlkit:language-id@@16.1.1 */
            public static final class zza extends zzeo.zzb<zzb, zza> implements zzgb {
                private zza() {
                    super(zzb.zzh);
                }

                /* synthetic */ zza(zzic zzicVar) {
                    this();
                }
            }

            /* JADX WARN: Multi-variable type inference failed */
            /* JADX WARN: Type inference failed for: r3v13 */
            /* JADX WARN: Type inference failed for: r3v14, types: [com.google.android.gms.internal.mlkit_language_id.zzeo$zza, com.google.android.gms.internal.mlkit_language_id.zzgj<com.google.android.gms.internal.mlkit_language_id.zzid$zzi$zzb>] */
            /* JADX WARN: Type inference failed for: r3v17 */
            /* JADX WARN: Type inference failed for: r3v18 */
            @Override // com.google.android.gms.internal.mlkit_language_id.zzeo
            protected final Object zza(int i, Object obj, Object obj2) {
                ?? r3;
                zzic zzicVar = null;
                switch (zzic.zza[i - 1]) {
                    case 1:
                        return new zzb();
                    case 2:
                        return new zza(zzicVar);
                    case 3:
                        return zza(zzh, "\u0001\u0004\u0000\u0001\u0001\u0004\u0004\u0000\u0000\u0000\u0001ဈ\u0000\u0002င\u0001\u0003ဈ\u0002\u0004ဂ\u0003", new Object[]{"zzc", "zzd", "zze", "zzf", "zzg"});
                    case 4:
                        return zzh;
                    case 5:
                        zzgj<zzb> zzgjVar = zzi;
                        Object obj3 = zzgjVar;
                        if (zzgjVar == null) {
                            synchronized (zzb.class) {
                                zzgj<zzb> zzgjVar2 = zzi;
                                r3 = zzgjVar2;
                                if (zzgjVar2 == null) {
                                    ?? zzaVar = new zzeo.zza(zzh);
                                    zzi = zzaVar;
                                    r3 = zzaVar;
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
                zzb zzbVar = new zzb();
                zzh = zzbVar;
                zzeo.zza((Class<zzb>) zzb.class, zzbVar);
            }
        }

        private zzi() {
        }

        /* JADX INFO: compiled from: com.google.mlkit:language-id@@16.1.1 */
        public static final class zza extends zzeo.zzb<zzi, zza> implements zzgb {
            private zza() {
                super(zzi.zzd);
            }

            /* synthetic */ zza(zzic zzicVar) {
                this();
            }
        }

        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Type inference failed for: r1v13 */
        /* JADX WARN: Type inference failed for: r1v14, types: [com.google.android.gms.internal.mlkit_language_id.zzeo$zza, com.google.android.gms.internal.mlkit_language_id.zzgj<com.google.android.gms.internal.mlkit_language_id.zzid$zzi>] */
        /* JADX WARN: Type inference failed for: r1v17 */
        /* JADX WARN: Type inference failed for: r1v18 */
        @Override // com.google.android.gms.internal.mlkit_language_id.zzeo
        protected final Object zza(int i, Object obj, Object obj2) {
            ?? r1;
            zzic zzicVar = null;
            switch (zzic.zza[i - 1]) {
                case 1:
                    return new zzi();
                case 2:
                    return new zza(zzicVar);
                case 3:
                    return zza(zzd, "\u0001\u0001\u0000\u0000\u0001\u0001\u0001\u0000\u0001\u0000\u0001\u001b", new Object[]{"zzc", zzb.class});
                case 4:
                    return zzd;
                case 5:
                    zzgj<zzi> zzgjVar = zze;
                    Object obj3 = zzgjVar;
                    if (zzgjVar == null) {
                        synchronized (zzi.class) {
                            zzgj<zzi> zzgjVar2 = zze;
                            r1 = zzgjVar2;
                            if (zzgjVar2 == null) {
                                ?? zzaVar = new zzeo.zza(zzd);
                                zze = zzaVar;
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

        static {
            zzi zziVar = new zzi();
            zzd = zziVar;
            zzeo.zza((Class<zzi>) zzi.class, zziVar);
        }
    }

    /* JADX INFO: compiled from: com.google.mlkit:language-id@@16.1.1 */
    public static final class zzj extends zzeo<zzj, zza> implements zzgb {
        private static final zzj zzg;
        private static volatile zzgj<zzj> zzh;
        private int zzc;
        private int zzd;
        private zzh zze;
        private zze zzf;

        /* JADX INFO: compiled from: com.google.mlkit:language-id@@16.1.1 */
        public enum zzb implements zzet {
            DELEGATE_NONE(0),
            NNAPI(1),
            GPU(2),
            HEXAGON(3),
            EDGETPU(4);

            private static final zzes<zzb> zzf = new zzin();
            private final int zzg;

            @Override // com.google.android.gms.internal.mlkit_language_id.zzet
            public final int zza() {
                return this.zzg;
            }

            public static zzev zzb() {
                return zzio.zza;
            }

            @Override // java.lang.Enum
            public final String toString() {
                StringBuilder sb = new StringBuilder("<");
                sb.append(getClass().getName()).append('@').append(Integer.toHexString(System.identityHashCode(this)));
                sb.append(" number=").append(this.zzg);
                return sb.append(" name=").append(name()).append('>').toString();
            }

            zzb(int i) {
                this.zzg = i;
            }
        }

        private zzj() {
        }

        /* JADX INFO: compiled from: com.google.mlkit:language-id@@16.1.1 */
        public static final class zza extends zzeo.zzb<zzj, zza> implements zzgb {
            private zza() {
                super(zzj.zzg);
            }

            /* synthetic */ zza(zzic zzicVar) {
                this();
            }
        }

        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Type inference failed for: r3v13 */
        /* JADX WARN: Type inference failed for: r3v14, types: [com.google.android.gms.internal.mlkit_language_id.zzeo$zza, com.google.android.gms.internal.mlkit_language_id.zzgj<com.google.android.gms.internal.mlkit_language_id.zzid$zzj>] */
        /* JADX WARN: Type inference failed for: r3v17 */
        /* JADX WARN: Type inference failed for: r3v18 */
        @Override // com.google.android.gms.internal.mlkit_language_id.zzeo
        protected final Object zza(int i, Object obj, Object obj2) {
            ?? r3;
            zzic zzicVar = null;
            switch (zzic.zza[i - 1]) {
                case 1:
                    return new zzj();
                case 2:
                    return new zza(zzicVar);
                case 3:
                    return zza(zzg, "\u0001\u0003\u0000\u0001\u0001\u0003\u0003\u0000\u0000\u0000\u0001ဌ\u0000\u0002ဉ\u0001\u0003ဉ\u0002", new Object[]{"zzc", "zzd", zzb.zzb(), "zze", "zzf"});
                case 4:
                    return zzg;
                case 5:
                    zzgj<zzj> zzgjVar = zzh;
                    Object obj3 = zzgjVar;
                    if (zzgjVar == null) {
                        synchronized (zzj.class) {
                            zzgj<zzj> zzgjVar2 = zzh;
                            r3 = zzgjVar2;
                            if (zzgjVar2 == null) {
                                ?? zzaVar = new zzeo.zza(zzg);
                                zzh = zzaVar;
                                r3 = zzaVar;
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
            zzeo.zza((Class<zzj>) zzj.class, zzjVar);
        }
    }

    /* JADX INFO: compiled from: com.google.mlkit:language-id@@16.1.1 */
    public static final class zzk extends zzeo<zzk, zzb> implements zzgb {
        private static final zzk zzf;
        private static volatile zzgj<zzk> zzg;
        private int zzc;
        private int zzd;
        private float zze;

        /* JADX INFO: compiled from: com.google.mlkit:language-id@@16.1.1 */
        public enum zza implements zzet {
            UNKNOWN_METRIC(0),
            MEAN_ABSOLUTE_ERROR(1),
            MEAN_SQUARED_ERROR(2),
            ROOT_MEAN_SQUARED_ERROR(3);

            private static final zzes<zza> zze = new zziq();
            private final int zzf;

            @Override // com.google.android.gms.internal.mlkit_language_id.zzet
            public final int zza() {
                return this.zzf;
            }

            public static zzev zzb() {
                return zzip.zza;
            }

            @Override // java.lang.Enum
            public final String toString() {
                StringBuilder sb = new StringBuilder("<");
                sb.append(getClass().getName()).append('@').append(Integer.toHexString(System.identityHashCode(this)));
                sb.append(" number=").append(this.zzf);
                return sb.append(" name=").append(name()).append('>').toString();
            }

            zza(int i) {
                this.zzf = i;
            }
        }

        private zzk() {
        }

        /* JADX INFO: compiled from: com.google.mlkit:language-id@@16.1.1 */
        public static final class zzb extends zzeo.zzb<zzk, zzb> implements zzgb {
            private zzb() {
                super(zzk.zzf);
            }

            /* synthetic */ zzb(zzic zzicVar) {
                this();
            }
        }

        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Type inference failed for: r2v13 */
        /* JADX WARN: Type inference failed for: r2v14, types: [com.google.android.gms.internal.mlkit_language_id.zzeo$zza, com.google.android.gms.internal.mlkit_language_id.zzgj<com.google.android.gms.internal.mlkit_language_id.zzid$zzk>] */
        /* JADX WARN: Type inference failed for: r2v17 */
        /* JADX WARN: Type inference failed for: r2v18 */
        @Override // com.google.android.gms.internal.mlkit_language_id.zzeo
        protected final Object zza(int i, Object obj, Object obj2) {
            ?? r2;
            zzic zzicVar = null;
            switch (zzic.zza[i - 1]) {
                case 1:
                    return new zzk();
                case 2:
                    return new zzb(zzicVar);
                case 3:
                    return zza(zzf, "\u0001\u0002\u0000\u0001\u0001\u0002\u0002\u0000\u0000\u0000\u0001ဌ\u0000\u0002ခ\u0001", new Object[]{"zzc", "zzd", zza.zzb(), "zze"});
                case 4:
                    return zzf;
                case 5:
                    zzgj<zzk> zzgjVar = zzg;
                    Object obj3 = zzgjVar;
                    if (zzgjVar == null) {
                        synchronized (zzk.class) {
                            zzgj<zzk> zzgjVar2 = zzg;
                            r2 = zzgjVar2;
                            if (zzgjVar2 == null) {
                                ?? zzaVar = new zzeo.zza(zzf);
                                zzg = zzaVar;
                                r2 = zzaVar;
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
            zzeo.zza((Class<zzk>) zzk.class, zzkVar);
        }
    }

    /* JADX INFO: compiled from: com.google.mlkit:language-id@@16.1.1 */
    public static final class zzl extends zzeo.zzc<zzl, zza> implements zzgb {
        private static final zzl zzf;
        private static volatile zzgj<zzl> zzg;
        private byte zze = 2;
        private zzew<zzb> zzd = zzl();

        /* JADX INFO: compiled from: com.google.mlkit:language-id@@16.1.1 */
        public static final class zzb extends zzeo<zzb, zza> implements zzgb {
            private static final zzb zzg;
            private static volatile zzgj<zzb> zzh;
            private int zzc;
            private int zzd;
            private int zze;
            private zzew<zzk> zzf = zzl();

            private zzb() {
            }

            /* JADX INFO: compiled from: com.google.mlkit:language-id@@16.1.1 */
            public static final class zza extends zzeo.zzb<zzb, zza> implements zzgb {
                private zza() {
                    super(zzb.zzg);
                }

                /* synthetic */ zza(zzic zzicVar) {
                    this();
                }
            }

            /* JADX WARN: Multi-variable type inference failed */
            /* JADX WARN: Type inference failed for: r3v13 */
            /* JADX WARN: Type inference failed for: r3v14, types: [com.google.android.gms.internal.mlkit_language_id.zzeo$zza, com.google.android.gms.internal.mlkit_language_id.zzgj<com.google.android.gms.internal.mlkit_language_id.zzid$zzl$zzb>] */
            /* JADX WARN: Type inference failed for: r3v17 */
            /* JADX WARN: Type inference failed for: r3v18 */
            @Override // com.google.android.gms.internal.mlkit_language_id.zzeo
            protected final Object zza(int i, Object obj, Object obj2) {
                ?? r3;
                zzic zzicVar = null;
                switch (zzic.zza[i - 1]) {
                    case 1:
                        return new zzb();
                    case 2:
                        return new zza(zzicVar);
                    case 3:
                        return zza(zzg, "\u0001\u0003\u0000\u0001\u0001\u0003\u0003\u0000\u0001\u0000\u0001င\u0000\u0002င\u0001\u0003\u001b", new Object[]{"zzc", "zzd", "zze", "zzf", zzk.class});
                    case 4:
                        return zzg;
                    case 5:
                        zzgj<zzb> zzgjVar = zzh;
                        Object obj3 = zzgjVar;
                        if (zzgjVar == null) {
                            synchronized (zzb.class) {
                                zzgj<zzb> zzgjVar2 = zzh;
                                r3 = zzgjVar2;
                                if (zzgjVar2 == null) {
                                    ?? zzaVar = new zzeo.zza(zzg);
                                    zzh = zzaVar;
                                    r3 = zzaVar;
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
                zzb zzbVar = new zzb();
                zzg = zzbVar;
                zzeo.zza((Class<zzb>) zzb.class, zzbVar);
            }
        }

        private zzl() {
        }

        /* JADX INFO: compiled from: com.google.mlkit:language-id@@16.1.1 */
        public static final class zza extends zzeo.zzd<zzl, zza> implements zzgb {
            private zza() {
                super(zzl.zzf);
            }

            /* synthetic */ zza(zzic zzicVar) {
                this();
            }
        }

        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Type inference failed for: r2v13 */
        /* JADX WARN: Type inference failed for: r2v14, types: [com.google.android.gms.internal.mlkit_language_id.zzeo$zza, com.google.android.gms.internal.mlkit_language_id.zzgj<com.google.android.gms.internal.mlkit_language_id.zzid$zzl>] */
        /* JADX WARN: Type inference failed for: r2v19 */
        /* JADX WARN: Type inference failed for: r2v20 */
        @Override // com.google.android.gms.internal.mlkit_language_id.zzeo
        protected final Object zza(int i, Object obj, Object obj2) {
            ?? r2;
            zzic zzicVar = null;
            switch (zzic.zza[i - 1]) {
                case 1:
                    return new zzl();
                case 2:
                    return new zza(zzicVar);
                case 3:
                    return zza(zzf, "\u0001\u0001\u0000\u0000\u0001\u0001\u0001\u0000\u0001\u0000\u0001\u001b", new Object[]{"zzd", zzb.class});
                case 4:
                    return zzf;
                case 5:
                    zzgj<zzl> zzgjVar = zzg;
                    Object obj3 = zzgjVar;
                    if (zzgjVar == null) {
                        synchronized (zzl.class) {
                            zzgj<zzl> zzgjVar2 = zzg;
                            r2 = zzgjVar2;
                            if (zzgjVar2 == null) {
                                ?? zzaVar = new zzeo.zza(zzf);
                                zzg = zzaVar;
                                r2 = zzaVar;
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
            zzeo.zza((Class<zzl>) zzl.class, zzlVar);
        }
    }
}
