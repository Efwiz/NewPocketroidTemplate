package com.google.android.gms.internal.mlkit_language_id;

import com.badlogic.gdx.net.HttpStatus;
import org.catrobat.catroid.common.Constants;

/* JADX INFO: compiled from: com.google.mlkit:language-id@@16.1.1 */
/* JADX INFO: loaded from: classes9.dex */
public final class zzy {

    /* JADX INFO: compiled from: com.google.mlkit:language-id@@16.1.1 */
    public static final class zza extends zzeo<zza, C0050zza> implements zzgb {
        private static final zza zzg;
        private static volatile zzgj<zza> zzh;
        private int zzc;
        private zzb zzd;
        private int zze;
        private zzab zzf;

        /* JADX INFO: compiled from: com.google.mlkit:language-id@@16.1.1 */
        public static final class zzb extends zzeo<zzb, C0051zza> implements zzgb {
            private static final zzb zzh;
            private static volatile zzgj<zzb> zzi;
            private int zzc;
            private int zzd;
            private boolean zze;
            private zzae zzf;
            private zzam zzg;

            private zzb() {
            }

            /* JADX INFO: renamed from: com.google.android.gms.internal.mlkit_language_id.zzy$zza$zzb$zza, reason: collision with other inner class name */
            /* JADX INFO: compiled from: com.google.mlkit:language-id@@16.1.1 */
            public static final class C0051zza extends zzeo.zzb<zzb, C0051zza> implements zzgb {
                private C0051zza() {
                    super(zzb.zzh);
                }

                /* synthetic */ C0051zza(com.google.android.gms.internal.mlkit_language_id.zzx zzxVar) {
                    this();
                }
            }

            /* JADX WARN: Multi-variable type inference failed */
            /* JADX WARN: Type inference failed for: r7v12 */
            /* JADX WARN: Type inference failed for: r7v13, types: [com.google.android.gms.internal.mlkit_language_id.zzeo$zza, com.google.android.gms.internal.mlkit_language_id.zzgj<com.google.android.gms.internal.mlkit_language_id.zzy$zza$zzb>] */
            /* JADX WARN: Type inference failed for: r7v16 */
            /* JADX WARN: Type inference failed for: r7v17 */
            @Override // com.google.android.gms.internal.mlkit_language_id.zzeo
            protected final Object zza(int i, Object obj, Object obj2) {
                ?? r7;
                com.google.android.gms.internal.mlkit_language_id.zzx zzxVar = null;
                switch (com.google.android.gms.internal.mlkit_language_id.zzx.zza[i - 1]) {
                    case 1:
                        return new zzb();
                    case 2:
                        return new C0051zza(zzxVar);
                    case 3:
                        return zza(zzh, "\u0001\u0004\u0000\u0001\u0001\u0004\u0004\u0000\u0000\u0000\u0001ဌ\u0000\u0002ဇ\u0001\u0003ဉ\u0002\u0004ဉ\u0003", new Object[]{"zzc", "zzd", com.google.android.gms.internal.mlkit_language_id.zzai.zzb(), "zze", "zzf", "zzg"});
                    case 4:
                        return zzh;
                    case 5:
                        zzgj<zzb> zzgjVar = zzi;
                        Object obj3 = zzgjVar;
                        if (zzgjVar == null) {
                            synchronized (zzb.class) {
                                zzgj<zzb> zzgjVar2 = zzi;
                                r7 = zzgjVar2;
                                if (zzgjVar2 == null) {
                                    ?? zzaVar = new zzeo.zza(zzh);
                                    zzi = zzaVar;
                                    r7 = zzaVar;
                                }
                                break;
                            }
                            obj3 = r7;
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

        private zza() {
        }

        /* JADX INFO: renamed from: com.google.android.gms.internal.mlkit_language_id.zzy$zza$zza, reason: collision with other inner class name */
        /* JADX INFO: compiled from: com.google.mlkit:language-id@@16.1.1 */
        public static final class C0050zza extends zzeo.zzb<zza, C0050zza> implements zzgb {
            private C0050zza() {
                super(zza.zzg);
            }

            /* synthetic */ C0050zza(com.google.android.gms.internal.mlkit_language_id.zzx zzxVar) {
                this();
            }
        }

        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Type inference failed for: r2v13 */
        /* JADX WARN: Type inference failed for: r2v14, types: [com.google.android.gms.internal.mlkit_language_id.zzeo$zza, com.google.android.gms.internal.mlkit_language_id.zzgj<com.google.android.gms.internal.mlkit_language_id.zzy$zza>] */
        /* JADX WARN: Type inference failed for: r2v17 */
        /* JADX WARN: Type inference failed for: r2v18 */
        @Override // com.google.android.gms.internal.mlkit_language_id.zzeo
        protected final Object zza(int i, Object obj, Object obj2) {
            ?? r2;
            com.google.android.gms.internal.mlkit_language_id.zzx zzxVar = null;
            switch (com.google.android.gms.internal.mlkit_language_id.zzx.zza[i - 1]) {
                case 1:
                    return new zza();
                case 2:
                    return new C0050zza(zzxVar);
                case 3:
                    return zza(zzg, "\u0001\u0003\u0000\u0001\u0001\u0003\u0003\u0000\u0000\u0000\u0001ဉ\u0000\u0002ဋ\u0001\u0003ဉ\u0002", new Object[]{"zzc", "zzd", "zze", "zzf"});
                case 4:
                    return zzg;
                case 5:
                    zzgj<zza> zzgjVar = zzh;
                    Object obj3 = zzgjVar;
                    if (zzgjVar == null) {
                        synchronized (zza.class) {
                            zzgj<zza> zzgjVar2 = zzh;
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
            zza zzaVar = new zza();
            zzg = zzaVar;
            zzeo.zza((Class<zza>) zza.class, zzaVar);
        }
    }

    /* JADX INFO: compiled from: com.google.mlkit:language-id@@16.1.1 */
    public static final class zzaa extends zzeo<zzaa, zza> implements zzgb {
        private static final zzaa zzg;
        private static volatile zzgj<zzaa> zzh;
        private int zzc;
        private int zzd;
        private boolean zze;
        private String zzf = "";

        private zzaa() {
        }

        /* JADX INFO: compiled from: com.google.mlkit:language-id@@16.1.1 */
        public static final class zza extends zzeo.zzb<zzaa, zza> implements zzgb {
            private zza() {
                super(zzaa.zzg);
            }

            /* synthetic */ zza(com.google.android.gms.internal.mlkit_language_id.zzx zzxVar) {
                this();
            }
        }

        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Type inference failed for: r3v13 */
        /* JADX WARN: Type inference failed for: r3v14, types: [com.google.android.gms.internal.mlkit_language_id.zzeo$zza, com.google.android.gms.internal.mlkit_language_id.zzgj<com.google.android.gms.internal.mlkit_language_id.zzy$zzaa>] */
        /* JADX WARN: Type inference failed for: r3v17 */
        /* JADX WARN: Type inference failed for: r3v18 */
        @Override // com.google.android.gms.internal.mlkit_language_id.zzeo
        protected final Object zza(int i, Object obj, Object obj2) {
            ?? r3;
            com.google.android.gms.internal.mlkit_language_id.zzx zzxVar = null;
            switch (com.google.android.gms.internal.mlkit_language_id.zzx.zza[i - 1]) {
                case 1:
                    return new zzaa();
                case 2:
                    return new zza(zzxVar);
                case 3:
                    return zza(zzg, "\u0001\u0003\u0000\u0001\u0001\u0003\u0003\u0000\u0000\u0000\u0001ဌ\u0000\u0002ဇ\u0001\u0003ဈ\u0002", new Object[]{"zzc", "zzd", zzal.zzb.zzb(), "zze", "zzf"});
                case 4:
                    return zzg;
                case 5:
                    zzgj<zzaa> zzgjVar = zzh;
                    Object obj3 = zzgjVar;
                    if (zzgjVar == null) {
                        synchronized (zzaa.class) {
                            zzgj<zzaa> zzgjVar2 = zzh;
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
            zzaa zzaaVar = new zzaa();
            zzg = zzaaVar;
            zzeo.zza((Class<zzaa>) zzaa.class, zzaaVar);
        }
    }

    /* JADX INFO: compiled from: com.google.mlkit:language-id@@16.1.1 */
    public static final class zzab extends zzeo<zzab, zza> implements zzgb {
        private static final zzab zzj;
        private static volatile zzgj<zzab> zzk;
        private int zzc;
        private long zzd;
        private long zze;
        private long zzf;
        private long zzg;
        private long zzh;
        private long zzi;

        /* JADX INFO: compiled from: com.google.mlkit:language-id@@16.1.1 */
        public static final class zza extends zzeo.zzb<zzab, zza> implements zzgb {
            private zza() {
                super(zzab.zzj);
            }

            /* synthetic */ zza(com.google.android.gms.internal.mlkit_language_id.zzx zzxVar) {
                this();
            }
        }

        static {
            zzab zzabVar = new zzab();
            zzj = zzabVar;
            zzeo.zza((Class<zzab>) zzab.class, zzabVar);
        }

        private zzab() {
        }

        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Type inference failed for: r8v12 */
        /* JADX WARN: Type inference failed for: r8v13, types: [com.google.android.gms.internal.mlkit_language_id.zzeo$zza, com.google.android.gms.internal.mlkit_language_id.zzgj<com.google.android.gms.internal.mlkit_language_id.zzy$zzab>] */
        /* JADX WARN: Type inference failed for: r8v16 */
        /* JADX WARN: Type inference failed for: r8v17 */
        @Override // com.google.android.gms.internal.mlkit_language_id.zzeo
        protected final Object zza(int i, Object obj, Object obj2) {
            ?? r8;
            com.google.android.gms.internal.mlkit_language_id.zzx zzxVar = null;
            switch (com.google.android.gms.internal.mlkit_language_id.zzx.zza[i - 1]) {
                case 1:
                    return new zzab();
                case 2:
                    return new zza(zzxVar);
                case 3:
                    return zza(zzj, "\u0001\u0006\u0000\u0001\u0001\u0006\u0006\u0000\u0000\u0000\u0001ဃ\u0000\u0002ဃ\u0001\u0003ဃ\u0002\u0004ဃ\u0003\u0005ဃ\u0004\u0006ဃ\u0005", new Object[]{"zzc", "zzd", "zze", "zzf", "zzg", "zzh", "zzi"});
                case 4:
                    return zzj;
                case 5:
                    zzgj<zzab> zzgjVar = zzk;
                    Object obj3 = zzgjVar;
                    if (zzgjVar == null) {
                        synchronized (zzab.class) {
                            zzgj<zzab> zzgjVar2 = zzk;
                            r8 = zzgjVar2;
                            if (zzgjVar2 == null) {
                                ?? zzaVar = new zzeo.zza(zzj);
                                zzk = zzaVar;
                                r8 = zzaVar;
                            }
                            break;
                        }
                        obj3 = r8;
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
    }

    /* JADX INFO: compiled from: com.google.mlkit:language-id@@16.1.1 */
    public static final class zzac extends zzeo<zzac, zza> implements zzgb {
        private static final zzac zzj;
        private static volatile zzgj<zzac> zzk;
        private int zzc;
        private int zzd;
        private int zze;
        private int zzf;
        private int zzg;
        private boolean zzh;
        private float zzi;

        /* JADX INFO: compiled from: com.google.mlkit:language-id@@16.1.1 */
        public enum zzb implements zzet {
            UNKNOWN_CLASSIFICATIONS(0),
            NO_CLASSIFICATIONS(1),
            ALL_CLASSIFICATIONS(2);

            private static final zzes<zzb> zzd = new com.google.android.gms.internal.mlkit_language_id.zzan();
            private final int zze;

            @Override // com.google.android.gms.internal.mlkit_language_id.zzet
            public final int zza() {
                return this.zze;
            }

            public static zzev zzb() {
                return com.google.android.gms.internal.mlkit_language_id.zzao.zza;
            }

            @Override // java.lang.Enum
            public final String toString() {
                StringBuilder sb = new StringBuilder("<");
                sb.append(getClass().getName()).append('@').append(Integer.toHexString(System.identityHashCode(this)));
                sb.append(" number=").append(this.zze);
                return sb.append(" name=").append(name()).append('>').toString();
            }

            zzb(int i) {
                this.zze = i;
            }
        }

        /* JADX INFO: compiled from: com.google.mlkit:language-id@@16.1.1 */
        public enum zzc implements zzet {
            UNKNOWN_CONTOURS(0),
            NO_CONTOURS(1),
            ALL_CONTOURS(2);

            private static final zzes<zzc> zzd = new com.google.android.gms.internal.mlkit_language_id.zzaq();
            private final int zze;

            @Override // com.google.android.gms.internal.mlkit_language_id.zzet
            public final int zza() {
                return this.zze;
            }

            public static zzev zzb() {
                return com.google.android.gms.internal.mlkit_language_id.zzap.zza;
            }

            @Override // java.lang.Enum
            public final String toString() {
                StringBuilder sb = new StringBuilder("<");
                sb.append(getClass().getName()).append('@').append(Integer.toHexString(System.identityHashCode(this)));
                sb.append(" number=").append(this.zze);
                return sb.append(" name=").append(name()).append('>').toString();
            }

            zzc(int i) {
                this.zze = i;
            }
        }

        /* JADX INFO: compiled from: com.google.mlkit:language-id@@16.1.1 */
        public enum zzd implements zzet {
            UNKNOWN_LANDMARKS(0),
            NO_LANDMARKS(1),
            ALL_LANDMARKS(2);

            private static final zzes<zzd> zzd = new com.google.android.gms.internal.mlkit_language_id.zzar();
            private final int zze;

            @Override // com.google.android.gms.internal.mlkit_language_id.zzet
            public final int zza() {
                return this.zze;
            }

            public static zzev zzb() {
                return com.google.android.gms.internal.mlkit_language_id.zzas.zza;
            }

            @Override // java.lang.Enum
            public final String toString() {
                StringBuilder sb = new StringBuilder("<");
                sb.append(getClass().getName()).append('@').append(Integer.toHexString(System.identityHashCode(this)));
                sb.append(" number=").append(this.zze);
                return sb.append(" name=").append(name()).append('>').toString();
            }

            zzd(int i) {
                this.zze = i;
            }
        }

        /* JADX INFO: compiled from: com.google.mlkit:language-id@@16.1.1 */
        public enum zze implements zzet {
            UNKNOWN_PERFORMANCE(0),
            FAST(1),
            ACCURATE(2);

            private static final zzes<zze> zzd = new com.google.android.gms.internal.mlkit_language_id.zzau();
            private final int zze;

            @Override // com.google.android.gms.internal.mlkit_language_id.zzet
            public final int zza() {
                return this.zze;
            }

            public static zzev zzb() {
                return com.google.android.gms.internal.mlkit_language_id.zzat.zza;
            }

            @Override // java.lang.Enum
            public final String toString() {
                StringBuilder sb = new StringBuilder("<");
                sb.append(getClass().getName()).append('@').append(Integer.toHexString(System.identityHashCode(this)));
                sb.append(" number=").append(this.zze);
                return sb.append(" name=").append(name()).append('>').toString();
            }

            zze(int i) {
                this.zze = i;
            }
        }

        private zzac() {
        }

        /* JADX INFO: compiled from: com.google.mlkit:language-id@@16.1.1 */
        public static final class zza extends zzeo.zzb<zzac, zza> implements zzgb {
            private zza() {
                super(zzac.zzj);
            }

            /* synthetic */ zza(com.google.android.gms.internal.mlkit_language_id.zzx zzxVar) {
                this();
            }
        }

        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Type inference failed for: r12v12 */
        /* JADX WARN: Type inference failed for: r12v13, types: [com.google.android.gms.internal.mlkit_language_id.zzeo$zza, com.google.android.gms.internal.mlkit_language_id.zzgj<com.google.android.gms.internal.mlkit_language_id.zzy$zzac>] */
        /* JADX WARN: Type inference failed for: r12v16 */
        /* JADX WARN: Type inference failed for: r12v17 */
        @Override // com.google.android.gms.internal.mlkit_language_id.zzeo
        protected final Object zza(int i, Object obj, Object obj2) {
            ?? r12;
            com.google.android.gms.internal.mlkit_language_id.zzx zzxVar = null;
            switch (com.google.android.gms.internal.mlkit_language_id.zzx.zza[i - 1]) {
                case 1:
                    return new zzac();
                case 2:
                    return new zza(zzxVar);
                case 3:
                    return zza(zzj, "\u0001\u0006\u0000\u0001\u0001\u0006\u0006\u0000\u0000\u0000\u0001ဌ\u0000\u0002ဌ\u0001\u0003ဌ\u0002\u0004ဌ\u0003\u0005ဇ\u0004\u0006ခ\u0005", new Object[]{"zzc", "zzd", zzd.zzb(), "zze", zzb.zzb(), "zzf", zze.zzb(), "zzg", zzc.zzb(), "zzh", "zzi"});
                case 4:
                    return zzj;
                case 5:
                    zzgj<zzac> zzgjVar = zzk;
                    Object obj3 = zzgjVar;
                    if (zzgjVar == null) {
                        synchronized (zzac.class) {
                            zzgj<zzac> zzgjVar2 = zzk;
                            r12 = zzgjVar2;
                            if (zzgjVar2 == null) {
                                ?? zzaVar = new zzeo.zza(zzj);
                                zzk = zzaVar;
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
            zzac zzacVar = new zzac();
            zzj = zzacVar;
            zzeo.zza((Class<zzac>) zzac.class, zzacVar);
        }
    }

    /* JADX INFO: compiled from: com.google.mlkit:language-id@@16.1.1 */
    public static final class zzad extends zzeo.zzc<zzad, zza> implements zzgb {
        private static final zzad zzbd;
        private static volatile zzgj<zzad> zzbe;
        private zzp zzaa;
        private zzm zzab;
        private zzo zzac;
        private zzr zzad;
        private zzq zzae;
        private zzs zzaf;
        private zzt zzag;
        private zzu zzah;
        private zzv zzai;
        private zzw zzaj;
        private zzj zzak;
        private zzl zzal;
        private zzk zzam;
        private zzah zzan;
        private zzaa zzao;
        private zza zzap;
        private zzb zzaq;
        private zzd zzar;
        private zzc zzas;
        private zze zzat;
        private zzf zzau;
        private zzi zzav;
        private zzg zzaw;
        private zzh zzax;
        private zzbg zzaz;
        private zzag zzba;
        private zzaj zzbb;
        private int zzd;
        private int zze;
        private zzbh zzf;
        private int zzg;
        private boolean zzh;
        private zzak zzi;
        private zzz zzj;
        private C0053zzy zzk;
        private zzx zzl;
        private zzap zzm;
        private zzbd zzn;
        private zzao zzo;
        private zzaq zzp;
        private zzas zzq;
        private zzar zzr;
        private zzav zzs;
        private zzay zzt;
        private zzax zzu;
        private zzaz zzv;
        private zzbb zzw;
        private zzbc zzx;
        private zzau zzy;
        private zzbe zzz;
        private byte zzbc = 2;
        private zzew<zzid.zzf> zzay = zzl();

        /* JADX INFO: compiled from: com.google.mlkit:language-id@@16.1.1 */
        public static final class zza extends zzeo.zzd<zzad, zza> implements zzgb {
            private zza() {
                super(zzad.zzbd);
            }

            /* synthetic */ zza(com.google.android.gms.internal.mlkit_language_id.zzx zzxVar) {
                this();
            }

            public final zzbh zza() {
                return ((zzad) this.zza).zza();
            }

            public final zza zza(zzbh.zza zzaVar) {
                if (this.zzb) {
                    zzc();
                    this.zzb = false;
                }
                ((zzad) this.zza).zza((zzbh) ((zzeo) zzaVar.zzg()));
                return this;
            }

            public final zza zza(com.google.android.gms.internal.mlkit_language_id.zzaj zzajVar) {
                if (this.zzb) {
                    zzc();
                    this.zzb = false;
                }
                ((zzad) this.zza).zza(zzajVar);
                return this;
            }

            public final zza zza(boolean z) {
                if (this.zzb) {
                    zzc();
                    this.zzb = false;
                }
                ((zzad) this.zza).zza(true);
                return this;
            }

            public final zza zza(zzau.zza zzaVar) {
                if (this.zzb) {
                    zzc();
                    this.zzb = false;
                }
                ((zzad) this.zza).zza((zzau) ((zzeo) zzaVar.zzg()));
                return this;
            }
        }

        static {
            zzad zzadVar = new zzad();
            zzbd = zzadVar;
            zzeo.zza((Class<zzad>) zzad.class, zzadVar);
        }

        private zzad() {
        }

        /* JADX WARN: Multi-variable type inference failed */
        public static zza zza(zzad zzadVar) {
            return (zza) zzbd.zza(zzadVar);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final void zza(com.google.android.gms.internal.mlkit_language_id.zzaj zzajVar) {
            this.zzg = zzajVar.zza();
            this.zzd |= 2;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final void zza(zzau zzauVar) {
            zzauVar.getClass();
            this.zzy = zzauVar;
            this.zzd |= 524288;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final void zza(zzbh zzbhVar) {
            zzbhVar.getClass();
            this.zzf = zzbhVar;
            this.zzd |= 1;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final void zza(boolean z) {
            this.zzd |= 4;
            this.zzh = true;
        }

        /* JADX WARN: Multi-variable type inference failed */
        public static zza zzb() {
            return (zza) zzbd.zzh();
        }

        public final zzbh zza() {
            zzbh zzbhVar = this.zzf;
            return zzbhVar == null ? zzbh.zzc() : zzbhVar;
        }

        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Type inference failed for: r0v11 */
        /* JADX WARN: Type inference failed for: r0v12, types: [com.google.android.gms.internal.mlkit_language_id.zzeo$zza, com.google.android.gms.internal.mlkit_language_id.zzgj<com.google.android.gms.internal.mlkit_language_id.zzy$zzad>] */
        /* JADX WARN: Type inference failed for: r0v17 */
        /* JADX WARN: Type inference failed for: r0v18 */
        @Override // com.google.android.gms.internal.mlkit_language_id.zzeo
        protected final Object zza(int i, Object obj, Object obj2) {
            ?? r0;
            com.google.android.gms.internal.mlkit_language_id.zzx zzxVar = null;
            switch (com.google.android.gms.internal.mlkit_language_id.zzx.zza[i - 1]) {
                case 1:
                    return new zzad();
                case 2:
                    return new zza(zzxVar);
                case 3:
                    return zza(zzbd, "\u00011\u0000\u0002\u000131\u0000\u0001\u0001\u0001ဉ\u0000\u0002ဌ\u0001\u0003ဉ\u0003\u0004ဉ\u0005\u0005ဉ\u0007\u0006ဉ\b\u0007ဉ\t\bဉ\u0015\tဉ\u0016\nဉ\u0017\u000bဉ\u0018\fဉ\u0019\rဉ\u001a\u000eဉ\u001b\u000fဉ\u001c\u0010ဉ\u001d\u0011ဉ\u001e\u0012ဉ\f\u0013ဉ\u0012\u0014ဉ\u0004\u0015ဉ\u0013\u0016ဉ\u0014\u0017ဉ\u001f\u0018ဉ \u0019ဉ!\u001aဉ\r\u001bဉ\u000e\u001cဉ\u000f\u001dဉ\u0006\u001eဉ$\u001fဉ% ဉ&!ဉ'\"ဉ(#ဉ)$ဉ*%ဇ\u0002'ဉ\"(ဉ#)Л*ဉ-,ဉ\u0010-ဉ\u0011.ဉ+/ဉ,0ဉ\n1ဉ\u000b2ဉ.3ဉ/", new Object[]{"zzd", "zze", "zzf", "zzg", com.google.android.gms.internal.mlkit_language_id.zzaj.zzb(), "zzi", "zzk", "zzm", "zzn", "zzo", "zzaa", "zzab", "zzac", "zzad", "zzae", "zzaf", "zzag", "zzah", "zzai", "zzaj", "zzr", "zzx", "zzj", "zzy", "zzz", "zzak", "zzal", "zzam", "zzs", "zzt", "zzu", "zzl", "zzap", "zzaq", "zzar", "zzas", "zzat", "zzau", "zzav", "zzh", "zzan", "zzao", "zzay", zzid.zzf.class, "zzaz", "zzv", "zzw", "zzaw", "zzax", "zzp", "zzq", "zzba", "zzbb"});
                case 4:
                    return zzbd;
                case 5:
                    zzgj<zzad> zzgjVar = zzbe;
                    Object obj3 = zzgjVar;
                    if (zzgjVar == null) {
                        synchronized (zzad.class) {
                            zzgj<zzad> zzgjVar2 = zzbe;
                            r0 = zzgjVar2;
                            if (zzgjVar2 == null) {
                                ?? zzaVar = new zzeo.zza(zzbd);
                                zzbe = zzaVar;
                                r0 = zzaVar;
                            }
                            break;
                        }
                        obj3 = r0;
                    }
                    return obj3;
                case 6:
                    return Byte.valueOf(this.zzbc);
                case 7:
                    this.zzbc = (byte) (obj == null ? 0 : 1);
                    return null;
                default:
                    throw new UnsupportedOperationException();
            }
        }
    }

    /* JADX INFO: compiled from: com.google.mlkit:language-id@@16.1.1 */
    public static final class zzae extends zzeo<zzae, zza> implements zzgb {
        private static final zzae zzg;
        private static volatile zzgj<zzae> zzh;
        private int zzc;
        private int zzd;
        private int zze;
        private int zzf;

        /* JADX INFO: compiled from: com.google.mlkit:language-id@@16.1.1 */
        public enum zzb implements zzet {
            UNKNOWN_FORMAT(0),
            NV16(1),
            NV21(2),
            YV12(3),
            YUV_420_888(7),
            JPEG(8),
            BITMAP(4),
            CM_SAMPLE_BUFFER_REF(5),
            UI_IMAGE(6);

            private static final zzes<zzb> zzj = new com.google.android.gms.internal.mlkit_language_id.zzav();
            private final int zzk;

            @Override // com.google.android.gms.internal.mlkit_language_id.zzet
            public final int zza() {
                return this.zzk;
            }

            public static zzev zzb() {
                return com.google.android.gms.internal.mlkit_language_id.zzaw.zza;
            }

            @Override // java.lang.Enum
            public final String toString() {
                StringBuilder sb = new StringBuilder("<");
                sb.append(getClass().getName()).append('@').append(Integer.toHexString(System.identityHashCode(this)));
                sb.append(" number=").append(this.zzk);
                return sb.append(" name=").append(name()).append('>').toString();
            }

            zzb(int i) {
                this.zzk = i;
            }
        }

        private zzae() {
        }

        /* JADX INFO: compiled from: com.google.mlkit:language-id@@16.1.1 */
        public static final class zza extends zzeo.zzb<zzae, zza> implements zzgb {
            private zza() {
                super(zzae.zzg);
            }

            /* synthetic */ zza(com.google.android.gms.internal.mlkit_language_id.zzx zzxVar) {
                this();
            }
        }

        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Type inference failed for: r3v13 */
        /* JADX WARN: Type inference failed for: r3v14, types: [com.google.android.gms.internal.mlkit_language_id.zzeo$zza, com.google.android.gms.internal.mlkit_language_id.zzgj<com.google.android.gms.internal.mlkit_language_id.zzy$zzae>] */
        /* JADX WARN: Type inference failed for: r3v17 */
        /* JADX WARN: Type inference failed for: r3v18 */
        @Override // com.google.android.gms.internal.mlkit_language_id.zzeo
        protected final Object zza(int i, Object obj, Object obj2) {
            ?? r3;
            com.google.android.gms.internal.mlkit_language_id.zzx zzxVar = null;
            switch (com.google.android.gms.internal.mlkit_language_id.zzx.zza[i - 1]) {
                case 1:
                    return new zzae();
                case 2:
                    return new zza(zzxVar);
                case 3:
                    return zza(zzg, "\u0001\u0003\u0000\u0001\u0001\u0003\u0003\u0000\u0000\u0000\u0001ဌ\u0000\u0002ဋ\u0001\u0003ဋ\u0002", new Object[]{"zzc", "zzd", zzb.zzb(), "zze", "zzf"});
                case 4:
                    return zzg;
                case 5:
                    zzgj<zzae> zzgjVar = zzh;
                    Object obj3 = zzgjVar;
                    if (zzgjVar == null) {
                        synchronized (zzae.class) {
                            zzgj<zzae> zzgjVar2 = zzh;
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
            zzae zzaeVar = new zzae();
            zzg = zzaeVar;
            zzeo.zza((Class<zzae>) zzae.class, zzaeVar);
        }
    }

    /* JADX INFO: compiled from: com.google.mlkit:language-id@@16.1.1 */
    public static final class zzag extends zzeo<zzag, zzb> implements zzgb {
        private static final zzag zzk;
        private static volatile zzgj<zzag> zzl;
        private int zzc;
        private long zzd;
        private int zze;
        private int zzf;
        private int zzg;
        private int zzh;
        private int zzi;
        private int zzj;

        /* JADX INFO: compiled from: com.google.mlkit:language-id@@16.1.1 */
        public enum zza implements zzet {
            SOURCE_UNKNOWN(0),
            BITMAP(1),
            BYTEARRAY(2),
            BYTEBUFFER(3),
            FILEPATH(4),
            ANDROID_MEDIA_IMAGE(5);

            private static final zzes<zza> zzg = new com.google.android.gms.internal.mlkit_language_id.zzay();
            private final int zzh;

            @Override // com.google.android.gms.internal.mlkit_language_id.zzet
            public final int zza() {
                return this.zzh;
            }

            public static zzev zzb() {
                return com.google.android.gms.internal.mlkit_language_id.zzax.zza;
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

        static {
            zzag zzagVar = new zzag();
            zzk = zzagVar;
            zzeo.zza((Class<zzag>) zzag.class, zzagVar);
        }

        private zzag() {
        }

        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Type inference failed for: r11v12 */
        /* JADX WARN: Type inference failed for: r11v13, types: [com.google.android.gms.internal.mlkit_language_id.zzeo$zza, com.google.android.gms.internal.mlkit_language_id.zzgj<com.google.android.gms.internal.mlkit_language_id.zzy$zzag>] */
        /* JADX WARN: Type inference failed for: r11v16 */
        /* JADX WARN: Type inference failed for: r11v17 */
        @Override // com.google.android.gms.internal.mlkit_language_id.zzeo
        protected final Object zza(int i, Object obj, Object obj2) {
            ?? r11;
            com.google.android.gms.internal.mlkit_language_id.zzx zzxVar = null;
            switch (com.google.android.gms.internal.mlkit_language_id.zzx.zza[i - 1]) {
                case 1:
                    return new zzag();
                case 2:
                    return new zzb(zzxVar);
                case 3:
                    return zza(zzk, "\u0001\u0007\u0000\u0001\u0001\u0007\u0007\u0000\u0000\u0000\u0001ဃ\u0000\u0002ဌ\u0001\u0003ဌ\u0002\u0004ဋ\u0003\u0005ဋ\u0004\u0006ဋ\u0005\u0007ဋ\u0006", new Object[]{"zzc", "zzd", "zze", zza.zzb(), "zzf", zzae.zzb.zzb(), "zzg", "zzh", "zzi", "zzj"});
                case 4:
                    return zzk;
                case 5:
                    zzgj<zzag> zzgjVar = zzl;
                    Object obj3 = zzgjVar;
                    if (zzgjVar == null) {
                        synchronized (zzag.class) {
                            zzgj<zzag> zzgjVar2 = zzl;
                            r11 = zzgjVar2;
                            if (zzgjVar2 == null) {
                                ?? zzaVar = new zzeo.zza(zzk);
                                zzl = zzaVar;
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

        /* JADX INFO: compiled from: com.google.mlkit:language-id@@16.1.1 */
        public static final class zzb extends zzeo.zzb<zzag, zzb> implements zzgb {
            private zzb() {
                super(zzag.zzk);
            }

            /* synthetic */ zzb(com.google.android.gms.internal.mlkit_language_id.zzx zzxVar) {
                this();
            }
        }
    }

    /* JADX INFO: compiled from: com.google.mlkit:language-id@@16.1.1 */
    public static final class zzah extends zzeo<zzah, zza> implements zzgb {
        private static final zzah zzg;
        private static volatile zzgj<zzah> zzh;
        private int zzc;
        private int zzd;
        private boolean zze;
        private String zzf = "";

        private zzah() {
        }

        /* JADX INFO: compiled from: com.google.mlkit:language-id@@16.1.1 */
        public static final class zza extends zzeo.zzb<zzah, zza> implements zzgb {
            private zza() {
                super(zzah.zzg);
            }

            /* synthetic */ zza(com.google.android.gms.internal.mlkit_language_id.zzx zzxVar) {
                this();
            }
        }

        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Type inference failed for: r3v13 */
        /* JADX WARN: Type inference failed for: r3v14, types: [com.google.android.gms.internal.mlkit_language_id.zzeo$zza, com.google.android.gms.internal.mlkit_language_id.zzgj<com.google.android.gms.internal.mlkit_language_id.zzy$zzah>] */
        /* JADX WARN: Type inference failed for: r3v17 */
        /* JADX WARN: Type inference failed for: r3v18 */
        @Override // com.google.android.gms.internal.mlkit_language_id.zzeo
        protected final Object zza(int i, Object obj, Object obj2) {
            ?? r3;
            com.google.android.gms.internal.mlkit_language_id.zzx zzxVar = null;
            switch (com.google.android.gms.internal.mlkit_language_id.zzx.zza[i - 1]) {
                case 1:
                    return new zzah();
                case 2:
                    return new zza(zzxVar);
                case 3:
                    return zza(zzg, "\u0001\u0003\u0000\u0001\u0001\u0003\u0003\u0000\u0000\u0000\u0001ဌ\u0000\u0002ဇ\u0001\u0003ဈ\u0002", new Object[]{"zzc", "zzd", zzal.zzb.zzb(), "zze", "zzf"});
                case 4:
                    return zzg;
                case 5:
                    zzgj<zzah> zzgjVar = zzh;
                    Object obj3 = zzgjVar;
                    if (zzgjVar == null) {
                        synchronized (zzah.class) {
                            zzgj<zzah> zzgjVar2 = zzh;
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
            zzah zzahVar = new zzah();
            zzg = zzahVar;
            zzeo.zza((Class<zzah>) zzah.class, zzahVar);
        }
    }

    /* JADX INFO: compiled from: com.google.mlkit:language-id@@16.1.1 */
    public static final class zzaj extends zzeo<zzaj, zzb> implements zzgb {
        private static final zzaj zze;
        private static volatile zzgj<zzaj> zzf;
        private int zzc;
        private int zzd;

        /* JADX INFO: compiled from: com.google.mlkit:language-id@@16.1.1 */
        public enum zza implements zzet {
            UNKNOWN(0),
            TRANSLATE(1);

            private static final zzes<zza> zzc = new com.google.android.gms.internal.mlkit_language_id.zzba();
            private final int zzd;

            zza(int i) {
                this.zzd = i;
            }

            public static zzev zzb() {
                return com.google.android.gms.internal.mlkit_language_id.zzaz.zza;
            }

            @Override // java.lang.Enum
            public final String toString() {
                StringBuilder sb = new StringBuilder("<");
                sb.append(getClass().getName()).append('@').append(Integer.toHexString(System.identityHashCode(this)));
                sb.append(" number=").append(this.zzd);
                return sb.append(" name=").append(name()).append('>').toString();
            }

            @Override // com.google.android.gms.internal.mlkit_language_id.zzet
            public final int zza() {
                return this.zzd;
            }
        }

        /* JADX INFO: compiled from: com.google.mlkit:language-id@@16.1.1 */
        public static final class zzb extends zzeo.zzb<zzaj, zzb> implements zzgb {
            private zzb() {
                super(zzaj.zze);
            }

            /* synthetic */ zzb(com.google.android.gms.internal.mlkit_language_id.zzx zzxVar) {
                this();
            }
        }

        static {
            zzaj zzajVar = new zzaj();
            zze = zzajVar;
            zzeo.zza((Class<zzaj>) zzaj.class, zzajVar);
        }

        private zzaj() {
        }

        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Type inference failed for: r1v13 */
        /* JADX WARN: Type inference failed for: r1v14, types: [com.google.android.gms.internal.mlkit_language_id.zzeo$zza, com.google.android.gms.internal.mlkit_language_id.zzgj<com.google.android.gms.internal.mlkit_language_id.zzy$zzaj>] */
        /* JADX WARN: Type inference failed for: r1v17 */
        /* JADX WARN: Type inference failed for: r1v18 */
        @Override // com.google.android.gms.internal.mlkit_language_id.zzeo
        protected final Object zza(int i, Object obj, Object obj2) {
            ?? r1;
            com.google.android.gms.internal.mlkit_language_id.zzx zzxVar = null;
            switch (com.google.android.gms.internal.mlkit_language_id.zzx.zza[i - 1]) {
                case 1:
                    return new zzaj();
                case 2:
                    return new zzb(zzxVar);
                case 3:
                    return zza(zze, "\u0001\u0001\u0000\u0001\u0001\u0001\u0001\u0000\u0000\u0000\u0001ဌ\u0000", new Object[]{"zzc", "zzd", zza.zzb()});
                case 4:
                    return zze;
                case 5:
                    zzgj<zzaj> zzgjVar = zzf;
                    Object obj3 = zzgjVar;
                    if (zzgjVar == null) {
                        synchronized (zzaj.class) {
                            zzgj<zzaj> zzgjVar2 = zzf;
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
    }

    /* JADX INFO: compiled from: com.google.mlkit:language-id@@16.1.1 */
    public static final class zzak extends zzeo<zzak, zzb> implements zzgb {
        private static final zzak zzk;
        private static volatile zzgj<zzak> zzl;
        private int zzc;
        private zzam zzd;
        private long zze;
        private int zzf;
        private long zzg;
        private int zzh;
        private long zzi;
        private zzeu zzj = zzk();

        /* JADX INFO: compiled from: com.google.mlkit:language-id@@16.1.1 */
        public enum zza implements zzet {
            UNKNOWN_STATUS(0),
            EXPLICITLY_REQUESTED(1),
            IMPLICITLY_REQUESTED(2),
            MODEL_INFO_RETRIEVAL_SUCCEEDED(3),
            MODEL_INFO_RETRIEVAL_FAILED(4),
            SCHEDULED(5),
            DOWNLOADING(6),
            SUCCEEDED(7),
            FAILED(8),
            LIVE(9),
            UPDATE_AVAILABLE(10),
            DOWNLOADED(11),
            STARTED(12);

            private static final zzes<zza> zzn = new com.google.android.gms.internal.mlkit_language_id.zzbc();
            private final int zzo;

            @Override // com.google.android.gms.internal.mlkit_language_id.zzet
            public final int zza() {
                return this.zzo;
            }

            public static zzev zzb() {
                return com.google.android.gms.internal.mlkit_language_id.zzbb.zza;
            }

            @Override // java.lang.Enum
            public final String toString() {
                StringBuilder sb = new StringBuilder("<");
                sb.append(getClass().getName()).append('@').append(Integer.toHexString(System.identityHashCode(this)));
                sb.append(" number=").append(this.zzo);
                return sb.append(" name=").append(name()).append('>').toString();
            }

            zza(int i) {
                this.zzo = i;
            }
        }

        private zzak() {
        }

        /* JADX INFO: compiled from: com.google.mlkit:language-id@@16.1.1 */
        public static final class zzb extends zzeo.zzb<zzak, zzb> implements zzgb {
            private zzb() {
                super(zzak.zzk);
            }

            /* synthetic */ zzb(com.google.android.gms.internal.mlkit_language_id.zzx zzxVar) {
                this();
            }
        }

        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Type inference failed for: r11v12 */
        /* JADX WARN: Type inference failed for: r11v13, types: [com.google.android.gms.internal.mlkit_language_id.zzeo$zza, com.google.android.gms.internal.mlkit_language_id.zzgj<com.google.android.gms.internal.mlkit_language_id.zzy$zzak>] */
        /* JADX WARN: Type inference failed for: r11v16 */
        /* JADX WARN: Type inference failed for: r11v17 */
        @Override // com.google.android.gms.internal.mlkit_language_id.zzeo
        protected final Object zza(int i, Object obj, Object obj2) {
            ?? r11;
            com.google.android.gms.internal.mlkit_language_id.zzx zzxVar = null;
            switch (com.google.android.gms.internal.mlkit_language_id.zzx.zza[i - 1]) {
                case 1:
                    return new zzak();
                case 2:
                    return new zzb(zzxVar);
                case 3:
                    return zza(zzk, "\u0001\u0007\u0000\u0001\u0001\u0007\u0007\u0000\u0001\u0000\u0001ဉ\u0000\u0002ဃ\u0001\u0003ဌ\u0002\u0004ဃ\u0003\u0005ဌ\u0004\u0006ဂ\u0005\u0007\u0016", new Object[]{"zzc", "zzd", "zze", "zzf", com.google.android.gms.internal.mlkit_language_id.zzai.zzb(), "zzg", "zzh", zza.zzb(), "zzi", "zzj"});
                case 4:
                    return zzk;
                case 5:
                    zzgj<zzak> zzgjVar = zzl;
                    Object obj3 = zzgjVar;
                    if (zzgjVar == null) {
                        synchronized (zzak.class) {
                            zzgj<zzak> zzgjVar2 = zzl;
                            r11 = zzgjVar2;
                            if (zzgjVar2 == null) {
                                ?? zzaVar = new zzeo.zza(zzk);
                                zzl = zzaVar;
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
            zzak zzakVar = new zzak();
            zzk = zzakVar;
            zzeo.zza((Class<zzak>) zzak.class, zzakVar);
        }
    }

    /* JADX INFO: compiled from: com.google.mlkit:language-id@@16.1.1 */
    public static final class zzal extends zzeo<zzal, zza> implements zzgb {
        private static final zzal zzl;
        private static volatile zzgj<zzal> zzm;
        private int zzc;
        private int zzf;
        private int zzi;
        private long zzj;
        private boolean zzk;
        private String zzd = "";
        private String zze = "";
        private String zzg = "";
        private String zzh = "";

        /* JADX INFO: compiled from: com.google.mlkit:language-id@@16.1.1 */
        public enum zzb implements zzet {
            TYPE_UNKNOWN(0),
            CUSTOM(1),
            AUTOML_IMAGE_LABELING(2),
            BASE_TRANSLATE(3),
            CUSTOM_OBJECT_DETECTION(4),
            CUSTOM_IMAGE_LABELING(5),
            BASE_ENTITY_EXTRACTION(6);

            private static final zzes<zzb> zzh = new com.google.android.gms.internal.mlkit_language_id.zzbd();
            private final int zzi;

            @Override // com.google.android.gms.internal.mlkit_language_id.zzet
            public final int zza() {
                return this.zzi;
            }

            public static zzev zzb() {
                return com.google.android.gms.internal.mlkit_language_id.zzbe.zza;
            }

            @Override // java.lang.Enum
            public final String toString() {
                StringBuilder sb = new StringBuilder("<");
                sb.append(getClass().getName()).append('@').append(Integer.toHexString(System.identityHashCode(this)));
                sb.append(" number=").append(this.zzi);
                return sb.append(" name=").append(name()).append('>').toString();
            }

            zzb(int i) {
                this.zzi = i;
            }
        }

        /* JADX INFO: compiled from: com.google.mlkit:language-id@@16.1.1 */
        public enum zzc implements zzet {
            SOURCE_UNKNOWN(0),
            APP_ASSET(1),
            LOCAL(2),
            CLOUD(3),
            SDK_BUILT_IN(4),
            URI(5);

            private static final zzes<zzc> zzg = new com.google.android.gms.internal.mlkit_language_id.zzbg();
            private final int zzh;

            @Override // com.google.android.gms.internal.mlkit_language_id.zzet
            public final int zza() {
                return this.zzh;
            }

            public static zzev zzb() {
                return com.google.android.gms.internal.mlkit_language_id.zzbf.zza;
            }

            @Override // java.lang.Enum
            public final String toString() {
                StringBuilder sb = new StringBuilder("<");
                sb.append(getClass().getName()).append('@').append(Integer.toHexString(System.identityHashCode(this)));
                sb.append(" number=").append(this.zzh);
                return sb.append(" name=").append(name()).append('>').toString();
            }

            zzc(int i) {
                this.zzh = i;
            }
        }

        private zzal() {
        }

        /* JADX INFO: compiled from: com.google.mlkit:language-id@@16.1.1 */
        public static final class zza extends zzeo.zzb<zzal, zza> implements zzgb {
            private zza() {
                super(zzal.zzl);
            }

            /* synthetic */ zza(com.google.android.gms.internal.mlkit_language_id.zzx zzxVar) {
                this();
            }
        }

        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Type inference failed for: r12v12 */
        /* JADX WARN: Type inference failed for: r12v13, types: [com.google.android.gms.internal.mlkit_language_id.zzeo$zza, com.google.android.gms.internal.mlkit_language_id.zzgj<com.google.android.gms.internal.mlkit_language_id.zzy$zzal>] */
        /* JADX WARN: Type inference failed for: r12v16 */
        /* JADX WARN: Type inference failed for: r12v17 */
        @Override // com.google.android.gms.internal.mlkit_language_id.zzeo
        protected final Object zza(int i, Object obj, Object obj2) {
            ?? r12;
            com.google.android.gms.internal.mlkit_language_id.zzx zzxVar = null;
            switch (com.google.android.gms.internal.mlkit_language_id.zzx.zza[i - 1]) {
                case 1:
                    return new zzal();
                case 2:
                    return new zza(zzxVar);
                case 3:
                    return zza(zzl, "\u0001\b\u0000\u0001\u0001\b\b\u0000\u0000\u0000\u0001ဈ\u0000\u0002ဈ\u0001\u0003ဌ\u0002\u0004ဈ\u0003\u0005ဈ\u0004\u0006ဌ\u0005\u0007ဃ\u0006\bဇ\u0007", new Object[]{"zzc", "zzd", "zze", "zzf", zzc.zzb(), "zzg", "zzh", "zzi", zzb.zzb(), "zzj", "zzk"});
                case 4:
                    return zzl;
                case 5:
                    zzgj<zzal> zzgjVar = zzm;
                    Object obj3 = zzgjVar;
                    if (zzgjVar == null) {
                        synchronized (zzal.class) {
                            zzgj<zzal> zzgjVar2 = zzm;
                            r12 = zzgjVar2;
                            if (zzgjVar2 == null) {
                                ?? zzaVar = new zzeo.zza(zzl);
                                zzm = zzaVar;
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
            zzal zzalVar = new zzal();
            zzl = zzalVar;
            zzeo.zza((Class<zzal>) zzal.class, zzalVar);
        }
    }

    /* JADX INFO: compiled from: com.google.mlkit:language-id@@16.1.1 */
    public static final class zzam extends zzeo<zzam, zza> implements zzgb {
        private static final zzam zzh;
        private static volatile zzgj<zzam> zzi;
        private int zzc;
        private zzal zzd;
        private zzb zze;
        private zzb zzf;
        private boolean zzg;

        /* JADX INFO: compiled from: com.google.mlkit:language-id@@16.1.1 */
        public static final class zzb extends zzeo<zzb, zza> implements zzgb {
            private static final zzb zzh;
            private static volatile zzgj<zzb> zzi;
            private int zzc;
            private boolean zzd;
            private boolean zze;
            private boolean zzf;
            private boolean zzg;

            private zzb() {
            }

            /* JADX INFO: compiled from: com.google.mlkit:language-id@@16.1.1 */
            public static final class zza extends zzeo.zzb<zzb, zza> implements zzgb {
                private zza() {
                    super(zzb.zzh);
                }

                /* synthetic */ zza(com.google.android.gms.internal.mlkit_language_id.zzx zzxVar) {
                    this();
                }
            }

            /* JADX WARN: Multi-variable type inference failed */
            /* JADX WARN: Type inference failed for: r3v13 */
            /* JADX WARN: Type inference failed for: r3v14, types: [com.google.android.gms.internal.mlkit_language_id.zzeo$zza, com.google.android.gms.internal.mlkit_language_id.zzgj<com.google.android.gms.internal.mlkit_language_id.zzy$zzam$zzb>] */
            /* JADX WARN: Type inference failed for: r3v17 */
            /* JADX WARN: Type inference failed for: r3v18 */
            @Override // com.google.android.gms.internal.mlkit_language_id.zzeo
            protected final Object zza(int i, Object obj, Object obj2) {
                ?? r3;
                com.google.android.gms.internal.mlkit_language_id.zzx zzxVar = null;
                switch (com.google.android.gms.internal.mlkit_language_id.zzx.zza[i - 1]) {
                    case 1:
                        return new zzb();
                    case 2:
                        return new zza(zzxVar);
                    case 3:
                        return zza(zzh, "\u0001\u0004\u0000\u0001\u0001\u0004\u0004\u0000\u0000\u0000\u0001ဇ\u0000\u0002ဇ\u0001\u0003ဇ\u0002\u0004ဇ\u0003", new Object[]{"zzc", "zzd", "zze", "zzf", "zzg"});
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

        private zzam() {
        }

        /* JADX INFO: compiled from: com.google.mlkit:language-id@@16.1.1 */
        public static final class zza extends zzeo.zzb<zzam, zza> implements zzgb {
            private zza() {
                super(zzam.zzh);
            }

            /* synthetic */ zza(com.google.android.gms.internal.mlkit_language_id.zzx zzxVar) {
                this();
            }
        }

        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Type inference failed for: r3v13 */
        /* JADX WARN: Type inference failed for: r3v14, types: [com.google.android.gms.internal.mlkit_language_id.zzeo$zza, com.google.android.gms.internal.mlkit_language_id.zzgj<com.google.android.gms.internal.mlkit_language_id.zzy$zzam>] */
        /* JADX WARN: Type inference failed for: r3v17 */
        /* JADX WARN: Type inference failed for: r3v18 */
        @Override // com.google.android.gms.internal.mlkit_language_id.zzeo
        protected final Object zza(int i, Object obj, Object obj2) {
            ?? r3;
            com.google.android.gms.internal.mlkit_language_id.zzx zzxVar = null;
            switch (com.google.android.gms.internal.mlkit_language_id.zzx.zza[i - 1]) {
                case 1:
                    return new zzam();
                case 2:
                    return new zza(zzxVar);
                case 3:
                    return zza(zzh, "\u0001\u0004\u0000\u0001\u0001\u0004\u0004\u0000\u0000\u0000\u0001ဉ\u0000\u0002ဉ\u0001\u0003ဉ\u0002\u0004ဇ\u0003", new Object[]{"zzc", "zzd", "zze", "zzf", "zzg"});
                case 4:
                    return zzh;
                case 5:
                    zzgj<zzam> zzgjVar = zzi;
                    Object obj3 = zzgjVar;
                    if (zzgjVar == null) {
                        synchronized (zzam.class) {
                            zzgj<zzam> zzgjVar2 = zzi;
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
            zzam zzamVar = new zzam();
            zzh = zzamVar;
            zzeo.zza((Class<zzam>) zzam.class, zzamVar);
        }
    }

    /* JADX INFO: compiled from: com.google.mlkit:language-id@@16.1.1 */
    public static final class zzan extends zzeo<zzan, zza> implements zzgb {
        private static final zzan zzh;
        private static volatile zzgj<zzan> zzi;
        private int zzc;
        private int zzd;
        private float zze;
        private int zzf;
        private int zzg;

        /* JADX INFO: compiled from: com.google.mlkit:language-id@@16.1.1 */
        public enum zzb implements zzet {
            CATEGORY_UNKNOWN(0),
            CATEGORY_HOME_GOOD(1),
            CATEGORY_FASHION_GOOD(2),
            CATEGORY_ANIMAL(3),
            CATEGORY_FOOD(4),
            CATEGORY_PLACE(5),
            CATEGORY_PLANT(6);

            private static final zzes<zzb> zzh = new com.google.android.gms.internal.mlkit_language_id.zzbh();
            private final int zzi;

            @Override // com.google.android.gms.internal.mlkit_language_id.zzet
            public final int zza() {
                return this.zzi;
            }

            public static zzev zzb() {
                return com.google.android.gms.internal.mlkit_language_id.zzbi.zza;
            }

            @Override // java.lang.Enum
            public final String toString() {
                StringBuilder sb = new StringBuilder("<");
                sb.append(getClass().getName()).append('@').append(Integer.toHexString(System.identityHashCode(this)));
                sb.append(" number=").append(this.zzi);
                return sb.append(" name=").append(name()).append('>').toString();
            }

            zzb(int i) {
                this.zzi = i;
            }
        }

        private zzan() {
        }

        /* JADX INFO: compiled from: com.google.mlkit:language-id@@16.1.1 */
        public static final class zza extends zzeo.zzb<zzan, zza> implements zzgb {
            private zza() {
                super(zzan.zzh);
            }

            /* synthetic */ zza(com.google.android.gms.internal.mlkit_language_id.zzx zzxVar) {
                this();
            }
        }

        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Type inference failed for: r7v12 */
        /* JADX WARN: Type inference failed for: r7v13, types: [com.google.android.gms.internal.mlkit_language_id.zzeo$zza, com.google.android.gms.internal.mlkit_language_id.zzgj<com.google.android.gms.internal.mlkit_language_id.zzy$zzan>] */
        /* JADX WARN: Type inference failed for: r7v16 */
        /* JADX WARN: Type inference failed for: r7v17 */
        @Override // com.google.android.gms.internal.mlkit_language_id.zzeo
        protected final Object zza(int i, Object obj, Object obj2) {
            ?? r7;
            com.google.android.gms.internal.mlkit_language_id.zzx zzxVar = null;
            switch (com.google.android.gms.internal.mlkit_language_id.zzx.zza[i - 1]) {
                case 1:
                    return new zzan();
                case 2:
                    return new zza(zzxVar);
                case 3:
                    return zza(zzh, "\u0001\u0004\u0000\u0001\u0001\u0004\u0004\u0000\u0000\u0000\u0001ဌ\u0000\u0002ခ\u0001\u0003င\u0002\u0004ဋ\u0003", new Object[]{"zzc", "zzd", zzb.zzb(), "zze", "zzf", "zzg"});
                case 4:
                    return zzh;
                case 5:
                    zzgj<zzan> zzgjVar = zzi;
                    Object obj3 = zzgjVar;
                    if (zzgjVar == null) {
                        synchronized (zzan.class) {
                            zzgj<zzan> zzgjVar2 = zzi;
                            r7 = zzgjVar2;
                            if (zzgjVar2 == null) {
                                ?? zzaVar = new zzeo.zza(zzh);
                                zzi = zzaVar;
                                r7 = zzaVar;
                            }
                            break;
                        }
                        obj3 = r7;
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
            zzan zzanVar = new zzan();
            zzh = zzanVar;
            zzeo.zza((Class<zzan>) zzan.class, zzanVar);
        }
    }

    /* JADX INFO: compiled from: com.google.mlkit:language-id@@16.1.1 */
    public static final class zzao extends zzeo<zzao, zzc> implements zzgb {
        private static final zzex<Integer, zza> zzg = new zzbk();
        private static final zzex<Integer, zzb> zzi = new zzbj();
        private static final zzao zzk;
        private static volatile zzgj<zzao> zzl;
        private int zzc;
        private zzaf zzd;
        private zzci.zza zze;
        private zzeu zzf = zzk();
        private zzeu zzh = zzk();
        private zzae zzj;

        /* JADX INFO: compiled from: com.google.mlkit:language-id@@16.1.1 */
        public enum zza implements zzet {
            FORMAT_UNKNOWN(0),
            FORMAT_CODE_128(1),
            FORMAT_CODE_39(2),
            FORMAT_CODE_93(4),
            FORMAT_CODABAR(8),
            FORMAT_DATA_MATRIX(16),
            FORMAT_EAN_13(32),
            FORMAT_EAN_8(64),
            FORMAT_ITF(128),
            FORMAT_QR_CODE(256),
            FORMAT_UPC_A(512),
            FORMAT_UPC_E(1024),
            FORMAT_PDF417(2048),
            FORMAT_AZTEC(4096);

            private static final zzes<zza> zzo = new zzbl();
            private final int zzp;

            @Override // com.google.android.gms.internal.mlkit_language_id.zzet
            public final int zza() {
                return this.zzp;
            }

            public static zzev zzb() {
                return zzbm.zza;
            }

            @Override // java.lang.Enum
            public final String toString() {
                StringBuilder sb = new StringBuilder("<");
                sb.append(getClass().getName()).append('@').append(Integer.toHexString(System.identityHashCode(this)));
                sb.append(" number=").append(this.zzp);
                return sb.append(" name=").append(name()).append('>').toString();
            }

            zza(int i) {
                this.zzp = i;
            }
        }

        /* JADX INFO: compiled from: com.google.mlkit:language-id@@16.1.1 */
        public enum zzb implements zzet {
            TYPE_UNKNOWN(0),
            TYPE_CONTACT_INFO(1),
            TYPE_EMAIL(2),
            TYPE_ISBN(3),
            TYPE_PHONE(4),
            TYPE_PRODUCT(5),
            TYPE_SMS(6),
            TYPE_TEXT(7),
            TYPE_URL(8),
            TYPE_WIFI(9),
            TYPE_GEO(10),
            TYPE_CALENDAR_EVENT(11),
            TYPE_DRIVER_LICENSE(12);

            private static final zzes<zzb> zzn = new zzbo();
            private final int zzo;

            @Override // com.google.android.gms.internal.mlkit_language_id.zzet
            public final int zza() {
                return this.zzo;
            }

            public static zzev zzb() {
                return zzbn.zza;
            }

            @Override // java.lang.Enum
            public final String toString() {
                StringBuilder sb = new StringBuilder("<");
                sb.append(getClass().getName()).append('@').append(Integer.toHexString(System.identityHashCode(this)));
                sb.append(" number=").append(this.zzo);
                return sb.append(" name=").append(name()).append('>').toString();
            }

            zzb(int i) {
                this.zzo = i;
            }
        }

        private zzao() {
        }

        /* JADX INFO: compiled from: com.google.mlkit:language-id@@16.1.1 */
        public static final class zzc extends zzeo.zzb<zzao, zzc> implements zzgb {
            private zzc() {
                super(zzao.zzk);
            }

            /* synthetic */ zzc(com.google.android.gms.internal.mlkit_language_id.zzx zzxVar) {
                this();
            }
        }

        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Type inference failed for: r9v12 */
        /* JADX WARN: Type inference failed for: r9v13, types: [com.google.android.gms.internal.mlkit_language_id.zzeo$zza, com.google.android.gms.internal.mlkit_language_id.zzgj<com.google.android.gms.internal.mlkit_language_id.zzy$zzao>] */
        /* JADX WARN: Type inference failed for: r9v16 */
        /* JADX WARN: Type inference failed for: r9v17 */
        @Override // com.google.android.gms.internal.mlkit_language_id.zzeo
        protected final Object zza(int i, Object obj, Object obj2) {
            ?? r9;
            com.google.android.gms.internal.mlkit_language_id.zzx zzxVar = null;
            switch (com.google.android.gms.internal.mlkit_language_id.zzx.zza[i - 1]) {
                case 1:
                    return new zzao();
                case 2:
                    return new zzc(zzxVar);
                case 3:
                    return zza(zzk, "\u0001\u0005\u0000\u0001\u0001\u0005\u0005\u0000\u0002\u0000\u0001ဉ\u0000\u0002ဉ\u0001\u0003\u001e\u0004\u001e\u0005ဉ\u0002", new Object[]{"zzc", "zzd", "zze", "zzf", zza.zzb(), "zzh", zzb.zzb(), "zzj"});
                case 4:
                    return zzk;
                case 5:
                    zzgj<zzao> zzgjVar = zzl;
                    Object obj3 = zzgjVar;
                    if (zzgjVar == null) {
                        synchronized (zzao.class) {
                            zzgj<zzao> zzgjVar2 = zzl;
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

        /* JADX WARN: Type inference failed for: r0v0, types: [com.google.android.gms.internal.mlkit_language_id.zzbk, com.google.android.gms.internal.mlkit_language_id.zzex<java.lang.Integer, com.google.android.gms.internal.mlkit_language_id.zzy$zzao$zza>] */
        /* JADX WARN: Type inference failed for: r0v1, types: [com.google.android.gms.internal.mlkit_language_id.zzbj, com.google.android.gms.internal.mlkit_language_id.zzex<java.lang.Integer, com.google.android.gms.internal.mlkit_language_id.zzy$zzao$zzb>] */
        static {
            zzao zzaoVar = new zzao();
            zzk = zzaoVar;
            zzeo.zza((Class<zzao>) zzao.class, zzaoVar);
        }
    }

    /* JADX INFO: compiled from: com.google.mlkit:language-id@@16.1.1 */
    public static final class zzap extends zzeo<zzap, zza> implements zzgb {
        private static final zzap zzj;
        private static volatile zzgj<zzap> zzk;
        private int zzc;
        private zzaf zzd;
        private zzci.zzb zze;
        private zzae zzf;
        private zzac zzg;
        private int zzh;
        private int zzi;

        private zzap() {
        }

        /* JADX INFO: compiled from: com.google.mlkit:language-id@@16.1.1 */
        public static final class zza extends zzeo.zzb<zzap, zza> implements zzgb {
            private zza() {
                super(zzap.zzj);
            }

            /* synthetic */ zza(com.google.android.gms.internal.mlkit_language_id.zzx zzxVar) {
                this();
            }
        }

        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Type inference failed for: r8v12 */
        /* JADX WARN: Type inference failed for: r8v13, types: [com.google.android.gms.internal.mlkit_language_id.zzeo$zza, com.google.android.gms.internal.mlkit_language_id.zzgj<com.google.android.gms.internal.mlkit_language_id.zzy$zzap>] */
        /* JADX WARN: Type inference failed for: r8v16 */
        /* JADX WARN: Type inference failed for: r8v17 */
        @Override // com.google.android.gms.internal.mlkit_language_id.zzeo
        protected final Object zza(int i, Object obj, Object obj2) {
            ?? r8;
            com.google.android.gms.internal.mlkit_language_id.zzx zzxVar = null;
            switch (com.google.android.gms.internal.mlkit_language_id.zzx.zza[i - 1]) {
                case 1:
                    return new zzap();
                case 2:
                    return new zza(zzxVar);
                case 3:
                    return zza(zzj, "\u0001\u0006\u0000\u0001\u0001\u0006\u0006\u0000\u0000\u0000\u0001ဉ\u0000\u0002ဉ\u0001\u0003ဉ\u0002\u0004ဉ\u0003\u0005ဋ\u0004\u0006ဋ\u0005", new Object[]{"zzc", "zzd", "zze", "zzf", "zzg", "zzh", "zzi"});
                case 4:
                    return zzj;
                case 5:
                    zzgj<zzap> zzgjVar = zzk;
                    Object obj3 = zzgjVar;
                    if (zzgjVar == null) {
                        synchronized (zzap.class) {
                            zzgj<zzap> zzgjVar2 = zzk;
                            r8 = zzgjVar2;
                            if (zzgjVar2 == null) {
                                ?? zzaVar = new zzeo.zza(zzj);
                                zzk = zzaVar;
                                r8 = zzaVar;
                            }
                            break;
                        }
                        obj3 = r8;
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
            zzap zzapVar = new zzap();
            zzj = zzapVar;
            zzeo.zza((Class<zzap>) zzap.class, zzapVar);
        }
    }

    /* JADX INFO: compiled from: com.google.mlkit:language-id@@16.1.1 */
    public static final class zzaq extends zzeo<zzaq, zza> implements zzgb {
        private static final zzaq zzf;
        private static volatile zzgj<zzaq> zzg;
        private int zzc;
        private zzat zzd;
        private int zze;

        private zzaq() {
        }

        /* JADX INFO: compiled from: com.google.mlkit:language-id@@16.1.1 */
        public static final class zza extends zzeo.zzb<zzaq, zza> implements zzgb {
            private zza() {
                super(zzaq.zzf);
            }

            /* synthetic */ zza(com.google.android.gms.internal.mlkit_language_id.zzx zzxVar) {
                this();
            }
        }

        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Type inference failed for: r2v13 */
        /* JADX WARN: Type inference failed for: r2v14, types: [com.google.android.gms.internal.mlkit_language_id.zzeo$zza, com.google.android.gms.internal.mlkit_language_id.zzgj<com.google.android.gms.internal.mlkit_language_id.zzy$zzaq>] */
        /* JADX WARN: Type inference failed for: r2v17 */
        /* JADX WARN: Type inference failed for: r2v18 */
        @Override // com.google.android.gms.internal.mlkit_language_id.zzeo
        protected final Object zza(int i, Object obj, Object obj2) {
            ?? r2;
            com.google.android.gms.internal.mlkit_language_id.zzx zzxVar = null;
            switch (com.google.android.gms.internal.mlkit_language_id.zzx.zza[i - 1]) {
                case 1:
                    return new zzaq();
                case 2:
                    return new zza(zzxVar);
                case 3:
                    return zza(zzf, "\u0001\u0002\u0000\u0001\u0001\u0002\u0002\u0000\u0000\u0000\u0001ဉ\u0000\u0002ဌ\u0001", new Object[]{"zzc", "zzd", "zze", com.google.android.gms.internal.mlkit_language_id.zzai.zzb()});
                case 4:
                    return zzf;
                case 5:
                    zzgj<zzaq> zzgjVar = zzg;
                    Object obj3 = zzgjVar;
                    if (zzgjVar == null) {
                        synchronized (zzaq.class) {
                            zzgj<zzaq> zzgjVar2 = zzg;
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
            zzaq zzaqVar = new zzaq();
            zzf = zzaqVar;
            zzeo.zza((Class<zzaq>) zzaq.class, zzaqVar);
        }
    }

    /* JADX INFO: compiled from: com.google.mlkit:language-id@@16.1.1 */
    public static final class zzar extends zzeo<zzar, zza> implements zzgb {
        private static final zzar zzi;
        private static volatile zzgj<zzar> zzj;
        private int zzc;
        private zzaf zzd;
        private zzat zze;
        private zzae zzf;
        private int zzg;
        private float zzh;

        private zzar() {
        }

        /* JADX INFO: compiled from: com.google.mlkit:language-id@@16.1.1 */
        public static final class zza extends zzeo.zzb<zzar, zza> implements zzgb {
            private zza() {
                super(zzar.zzi);
            }

            /* synthetic */ zza(com.google.android.gms.internal.mlkit_language_id.zzx zzxVar) {
                this();
            }
        }

        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Type inference failed for: r7v12 */
        /* JADX WARN: Type inference failed for: r7v13, types: [com.google.android.gms.internal.mlkit_language_id.zzeo$zza, com.google.android.gms.internal.mlkit_language_id.zzgj<com.google.android.gms.internal.mlkit_language_id.zzy$zzar>] */
        /* JADX WARN: Type inference failed for: r7v16 */
        /* JADX WARN: Type inference failed for: r7v17 */
        @Override // com.google.android.gms.internal.mlkit_language_id.zzeo
        protected final Object zza(int i, Object obj, Object obj2) {
            ?? r7;
            com.google.android.gms.internal.mlkit_language_id.zzx zzxVar = null;
            switch (com.google.android.gms.internal.mlkit_language_id.zzx.zza[i - 1]) {
                case 1:
                    return new zzar();
                case 2:
                    return new zza(zzxVar);
                case 3:
                    return zza(zzi, "\u0001\u0005\u0000\u0001\u0001\u0005\u0005\u0000\u0000\u0000\u0001ဉ\u0000\u0002ဉ\u0001\u0003ဉ\u0002\u0004ဋ\u0003\u0005ခ\u0004", new Object[]{"zzc", "zzd", "zze", "zzf", "zzg", "zzh"});
                case 4:
                    return zzi;
                case 5:
                    zzgj<zzar> zzgjVar = zzj;
                    Object obj3 = zzgjVar;
                    if (zzgjVar == null) {
                        synchronized (zzar.class) {
                            zzgj<zzar> zzgjVar2 = zzj;
                            r7 = zzgjVar2;
                            if (zzgjVar2 == null) {
                                ?? zzaVar = new zzeo.zza(zzi);
                                zzj = zzaVar;
                                r7 = zzaVar;
                            }
                            break;
                        }
                        obj3 = r7;
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
            zzar zzarVar = new zzar();
            zzi = zzarVar;
            zzeo.zza((Class<zzar>) zzar.class, zzarVar);
        }
    }

    /* JADX INFO: compiled from: com.google.mlkit:language-id@@16.1.1 */
    public static final class zzas extends zzeo<zzas, zza> implements zzgb {
        private static final zzex<Integer, com.google.android.gms.internal.mlkit_language_id.zzai> zzf = new zzbp();
        private static final zzas zzj;
        private static volatile zzgj<zzas> zzk;
        private int zzc;
        private zzat zzd;
        private long zzg;
        private long zzh;
        private zzeu zze = zzk();
        private zzew<zzbf> zzi = zzl();

        private zzas() {
        }

        /* JADX INFO: compiled from: com.google.mlkit:language-id@@16.1.1 */
        public static final class zza extends zzeo.zzb<zzas, zza> implements zzgb {
            private zza() {
                super(zzas.zzj);
            }

            /* synthetic */ zza(com.google.android.gms.internal.mlkit_language_id.zzx zzxVar) {
                this();
            }
        }

        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Type inference failed for: r9v12 */
        /* JADX WARN: Type inference failed for: r9v13, types: [com.google.android.gms.internal.mlkit_language_id.zzeo$zza, com.google.android.gms.internal.mlkit_language_id.zzgj<com.google.android.gms.internal.mlkit_language_id.zzy$zzas>] */
        /* JADX WARN: Type inference failed for: r9v16 */
        /* JADX WARN: Type inference failed for: r9v17 */
        @Override // com.google.android.gms.internal.mlkit_language_id.zzeo
        protected final Object zza(int i, Object obj, Object obj2) {
            ?? r9;
            com.google.android.gms.internal.mlkit_language_id.zzx zzxVar = null;
            switch (com.google.android.gms.internal.mlkit_language_id.zzx.zza[i - 1]) {
                case 1:
                    return new zzas();
                case 2:
                    return new zza(zzxVar);
                case 3:
                    return zza(zzj, "\u0001\u0005\u0000\u0001\u0001\u0005\u0005\u0000\u0002\u0000\u0001ဉ\u0000\u0002\u001e\u0003ဃ\u0001\u0004ဃ\u0002\u0005\u001b", new Object[]{"zzc", "zzd", "zze", com.google.android.gms.internal.mlkit_language_id.zzai.zzb(), "zzg", "zzh", "zzi", zzbf.class});
                case 4:
                    return zzj;
                case 5:
                    zzgj<zzas> zzgjVar = zzk;
                    Object obj3 = zzgjVar;
                    if (zzgjVar == null) {
                        synchronized (zzas.class) {
                            zzgj<zzas> zzgjVar2 = zzk;
                            r9 = zzgjVar2;
                            if (zzgjVar2 == null) {
                                ?? zzaVar = new zzeo.zza(zzj);
                                zzk = zzaVar;
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

        /* JADX WARN: Type inference failed for: r0v0, types: [com.google.android.gms.internal.mlkit_language_id.zzbp, com.google.android.gms.internal.mlkit_language_id.zzex<java.lang.Integer, com.google.android.gms.internal.mlkit_language_id.zzai>] */
        static {
            zzas zzasVar = new zzas();
            zzj = zzasVar;
            zzeo.zza((Class<zzas>) zzas.class, zzasVar);
        }
    }

    /* JADX INFO: compiled from: com.google.mlkit:language-id@@16.1.1 */
    public static final class zzat extends zzeo<zzat, zza> implements zzgb {
        private static final zzat zzg;
        private static volatile zzgj<zzat> zzh;
        private int zzc;
        private int zzd;
        private float zze;
        private zzam zzf;

        private zzat() {
        }

        /* JADX INFO: compiled from: com.google.mlkit:language-id@@16.1.1 */
        public static final class zza extends zzeo.zzb<zzat, zza> implements zzgb {
            private zza() {
                super(zzat.zzg);
            }

            /* synthetic */ zza(com.google.android.gms.internal.mlkit_language_id.zzx zzxVar) {
                this();
            }
        }

        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Type inference failed for: r2v13 */
        /* JADX WARN: Type inference failed for: r2v14, types: [com.google.android.gms.internal.mlkit_language_id.zzeo$zza, com.google.android.gms.internal.mlkit_language_id.zzgj<com.google.android.gms.internal.mlkit_language_id.zzy$zzat>] */
        /* JADX WARN: Type inference failed for: r2v17 */
        /* JADX WARN: Type inference failed for: r2v18 */
        @Override // com.google.android.gms.internal.mlkit_language_id.zzeo
        protected final Object zza(int i, Object obj, Object obj2) {
            ?? r2;
            com.google.android.gms.internal.mlkit_language_id.zzx zzxVar = null;
            switch (com.google.android.gms.internal.mlkit_language_id.zzx.zza[i - 1]) {
                case 1:
                    return new zzat();
                case 2:
                    return new zza(zzxVar);
                case 3:
                    return zza(zzg, "\u0001\u0003\u0000\u0001\u0001\u0003\u0003\u0000\u0000\u0000\u0001ဋ\u0000\u0002ခ\u0001\u0003ဉ\u0002", new Object[]{"zzc", "zzd", "zze", "zzf"});
                case 4:
                    return zzg;
                case 5:
                    zzgj<zzat> zzgjVar = zzh;
                    Object obj3 = zzgjVar;
                    if (zzgjVar == null) {
                        synchronized (zzat.class) {
                            zzgj<zzat> zzgjVar2 = zzh;
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
            zzat zzatVar = new zzat();
            zzg = zzatVar;
            zzeo.zza((Class<zzat>) zzat.class, zzatVar);
        }
    }

    /* JADX INFO: compiled from: com.google.mlkit:language-id@@16.1.1 */
    public static final class zzau extends zzeo<zzau, zza> implements zzgb {
        private static final zzau zzh;
        private static volatile zzgj<zzau> zzi;
        private int zzc;
        private zzaf zzd;
        private zzai zze;
        private zzc zzf;
        private zzd zzg;

        private zzau() {
        }

        /* JADX INFO: compiled from: com.google.mlkit:language-id@@16.1.1 */
        public static final class zzb extends zzeo<zzb, zza> implements zzgb {
            private static final zzb zzf;
            private static volatile zzgj<zzb> zzg;
            private int zzc;
            private float zzd;
            private String zze = "";

            private zzb() {
            }

            /* JADX INFO: compiled from: com.google.mlkit:language-id@@16.1.1 */
            public static final class zza extends zzeo.zzb<zzb, zza> implements zzgb {
                private zza() {
                    super(zzb.zzf);
                }

                public final zza zza(float f) {
                    if (this.zzb) {
                        zzc();
                        this.zzb = false;
                    }
                    ((zzb) this.zza).zza(f);
                    return this;
                }

                public final zza zza(String str) {
                    if (this.zzb) {
                        zzc();
                        this.zzb = false;
                    }
                    ((zzb) this.zza).zza(str);
                    return this;
                }

                /* synthetic */ zza(com.google.android.gms.internal.mlkit_language_id.zzx zzxVar) {
                    this();
                }
            }

            /* JADX INFO: Access modifiers changed from: private */
            public final void zza(float f) {
                this.zzc |= 1;
                this.zzd = f;
            }

            /* JADX INFO: Access modifiers changed from: private */
            public final void zza(String str) {
                str.getClass();
                this.zzc |= 2;
                this.zze = str;
            }

            public static zza zza() {
                return zzf.zzh();
            }

            /* JADX WARN: Multi-variable type inference failed */
            /* JADX WARN: Type inference failed for: r1v13 */
            /* JADX WARN: Type inference failed for: r1v14, types: [com.google.android.gms.internal.mlkit_language_id.zzeo$zza, com.google.android.gms.internal.mlkit_language_id.zzgj<com.google.android.gms.internal.mlkit_language_id.zzy$zzau$zzb>] */
            /* JADX WARN: Type inference failed for: r1v17 */
            /* JADX WARN: Type inference failed for: r1v18 */
            @Override // com.google.android.gms.internal.mlkit_language_id.zzeo
            protected final Object zza(int i, Object obj, Object obj2) {
                ?? r1;
                com.google.android.gms.internal.mlkit_language_id.zzx zzxVar = null;
                switch (com.google.android.gms.internal.mlkit_language_id.zzx.zza[i - 1]) {
                    case 1:
                        return new zzb();
                    case 2:
                        return new zza(zzxVar);
                    case 3:
                        return zza(zzf, "\u0001\u0002\u0000\u0001\u0001\u0002\u0002\u0000\u0000\u0000\u0001ခ\u0000\u0002ဈ\u0001", new Object[]{"zzc", "zzd", "zze"});
                    case 4:
                        return zzf;
                    case 5:
                        zzgj<zzb> zzgjVar = zzg;
                        Object obj3 = zzgjVar;
                        if (zzgjVar == null) {
                            synchronized (zzb.class) {
                                zzgj<zzb> zzgjVar2 = zzg;
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
                zzb zzbVar = new zzb();
                zzf = zzbVar;
                zzeo.zza((Class<zzb>) zzb.class, zzbVar);
            }
        }

        /* JADX INFO: compiled from: com.google.mlkit:language-id@@16.1.1 */
        public static final class zzc extends zzeo<zzc, zza> implements zzgb {
            private static final zzc zze;
            private static volatile zzgj<zzc> zzf;
            private int zzc;
            private zzb zzd;

            private zzc() {
            }

            /* JADX INFO: compiled from: com.google.mlkit:language-id@@16.1.1 */
            public static final class zza extends zzeo.zzb<zzc, zza> implements zzgb {
                private zza() {
                    super(zzc.zze);
                }

                public final zza zza(zzb.zza zzaVar) {
                    if (this.zzb) {
                        zzc();
                        this.zzb = false;
                    }
                    ((zzc) this.zza).zza((zzb) ((zzeo) zzaVar.zzg()));
                    return this;
                }

                /* synthetic */ zza(com.google.android.gms.internal.mlkit_language_id.zzx zzxVar) {
                    this();
                }
            }

            /* JADX INFO: Access modifiers changed from: private */
            public final void zza(zzb zzbVar) {
                zzbVar.getClass();
                this.zzd = zzbVar;
                this.zzc |= 1;
            }

            public static zza zza() {
                return zze.zzh();
            }

            /* JADX WARN: Multi-variable type inference failed */
            /* JADX WARN: Type inference failed for: r1v13 */
            /* JADX WARN: Type inference failed for: r1v14, types: [com.google.android.gms.internal.mlkit_language_id.zzeo$zza, com.google.android.gms.internal.mlkit_language_id.zzgj<com.google.android.gms.internal.mlkit_language_id.zzy$zzau$zzc>] */
            /* JADX WARN: Type inference failed for: r1v17 */
            /* JADX WARN: Type inference failed for: r1v18 */
            @Override // com.google.android.gms.internal.mlkit_language_id.zzeo
            protected final Object zza(int i, Object obj, Object obj2) {
                ?? r1;
                com.google.android.gms.internal.mlkit_language_id.zzx zzxVar = null;
                switch (com.google.android.gms.internal.mlkit_language_id.zzx.zza[i - 1]) {
                    case 1:
                        return new zzc();
                    case 2:
                        return new zza(zzxVar);
                    case 3:
                        return zza(zze, "\u0001\u0001\u0000\u0001\u0001\u0001\u0001\u0000\u0000\u0000\u0001ဉ\u0000", new Object[]{"zzc", "zzd"});
                    case 4:
                        return zze;
                    case 5:
                        zzgj<zzc> zzgjVar = zzf;
                        Object obj3 = zzgjVar;
                        if (zzgjVar == null) {
                            synchronized (zzc.class) {
                                zzgj<zzc> zzgjVar2 = zzf;
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

            public static zzc zzb() {
                return zze;
            }

            static {
                zzc zzcVar = new zzc();
                zze = zzcVar;
                zzeo.zza((Class<zzc>) zzc.class, zzcVar);
            }
        }

        /* JADX INFO: compiled from: com.google.mlkit:language-id@@16.1.1 */
        public static final class zzd extends zzeo<zzd, zza> implements zzgb {
            private static final zzd zzd;
            private static volatile zzgj<zzd> zze;
            private zzew<zzb> zzc = zzl();

            private zzd() {
            }

            /* JADX INFO: compiled from: com.google.mlkit:language-id@@16.1.1 */
            public static final class zza extends zzeo.zzb<zzd, zza> implements zzgb {
                private zza() {
                    super(zzd.zzd);
                }

                public final zza zza(zzb.zza zzaVar) {
                    if (this.zzb) {
                        zzc();
                        this.zzb = false;
                    }
                    ((zzd) this.zza).zza((zzb) ((zzeo) zzaVar.zzg()));
                    return this;
                }

                /* synthetic */ zza(com.google.android.gms.internal.mlkit_language_id.zzx zzxVar) {
                    this();
                }
            }

            /* JADX INFO: Access modifiers changed from: private */
            public final void zza(zzb zzbVar) {
                zzbVar.getClass();
                zzew<zzb> zzewVar = this.zzc;
                if (!zzewVar.zza()) {
                    this.zzc = zzeo.zza(zzewVar);
                }
                this.zzc.add(zzbVar);
            }

            public static zza zza() {
                return zzd.zzh();
            }

            /* JADX WARN: Multi-variable type inference failed */
            /* JADX WARN: Type inference failed for: r1v13 */
            /* JADX WARN: Type inference failed for: r1v14, types: [com.google.android.gms.internal.mlkit_language_id.zzeo$zza, com.google.android.gms.internal.mlkit_language_id.zzgj<com.google.android.gms.internal.mlkit_language_id.zzy$zzau$zzd>] */
            /* JADX WARN: Type inference failed for: r1v17 */
            /* JADX WARN: Type inference failed for: r1v18 */
            @Override // com.google.android.gms.internal.mlkit_language_id.zzeo
            protected final Object zza(int i, Object obj, Object obj2) {
                ?? r1;
                com.google.android.gms.internal.mlkit_language_id.zzx zzxVar = null;
                switch (com.google.android.gms.internal.mlkit_language_id.zzx.zza[i - 1]) {
                    case 1:
                        return new zzd();
                    case 2:
                        return new zza(zzxVar);
                    case 3:
                        return zza(zzd, "\u0001\u0001\u0000\u0000\u0001\u0001\u0001\u0000\u0001\u0000\u0001\u001b", new Object[]{"zzc", zzb.class});
                    case 4:
                        return zzd;
                    case 5:
                        zzgj<zzd> zzgjVar = zze;
                        Object obj3 = zzgjVar;
                        if (zzgjVar == null) {
                            synchronized (zzd.class) {
                                zzgj<zzd> zzgjVar2 = zze;
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

            public static zzd zzb() {
                return zzd;
            }

            static {
                zzd zzdVar = new zzd();
                zzd = zzdVar;
                zzeo.zza((Class<zzd>) zzd.class, zzdVar);
            }
        }

        /* JADX INFO: compiled from: com.google.mlkit:language-id@@16.1.1 */
        public static final class zza extends zzeo.zzb<zzau, zza> implements zzgb {
            private zza() {
                super(zzau.zzh);
            }

            public final zza zza(zzaf.zza zzaVar) {
                if (this.zzb) {
                    zzc();
                    this.zzb = false;
                }
                ((zzau) this.zza).zza((zzaf) ((zzeo) zzaVar.zzg()));
                return this;
            }

            public final zza zza(zzai zzaiVar) {
                if (this.zzb) {
                    zzc();
                    this.zzb = false;
                }
                ((zzau) this.zza).zza(zzaiVar);
                return this;
            }

            public final zza zza(zzc zzcVar) {
                if (this.zzb) {
                    zzc();
                    this.zzb = false;
                }
                ((zzau) this.zza).zza(zzcVar);
                return this;
            }

            public final zza zza(zzd zzdVar) {
                if (this.zzb) {
                    zzc();
                    this.zzb = false;
                }
                ((zzau) this.zza).zza(zzdVar);
                return this;
            }

            /* synthetic */ zza(com.google.android.gms.internal.mlkit_language_id.zzx zzxVar) {
                this();
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final void zza(zzaf zzafVar) {
            zzafVar.getClass();
            this.zzd = zzafVar;
            this.zzc |= 1;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final void zza(zzai zzaiVar) {
            zzaiVar.getClass();
            this.zze = zzaiVar;
            this.zzc |= 2;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final void zza(zzc zzcVar) {
            zzcVar.getClass();
            this.zzf = zzcVar;
            this.zzc |= 4;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final void zza(zzd zzdVar) {
            zzdVar.getClass();
            this.zzg = zzdVar;
            this.zzc |= 8;
        }

        public static zza zza() {
            return zzh.zzh();
        }

        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Type inference failed for: r3v13 */
        /* JADX WARN: Type inference failed for: r3v14, types: [com.google.android.gms.internal.mlkit_language_id.zzeo$zza, com.google.android.gms.internal.mlkit_language_id.zzgj<com.google.android.gms.internal.mlkit_language_id.zzy$zzau>] */
        /* JADX WARN: Type inference failed for: r3v17 */
        /* JADX WARN: Type inference failed for: r3v18 */
        @Override // com.google.android.gms.internal.mlkit_language_id.zzeo
        protected final Object zza(int i, Object obj, Object obj2) {
            ?? r3;
            com.google.android.gms.internal.mlkit_language_id.zzx zzxVar = null;
            switch (com.google.android.gms.internal.mlkit_language_id.zzx.zza[i - 1]) {
                case 1:
                    return new zzau();
                case 2:
                    return new zza(zzxVar);
                case 3:
                    return zza(zzh, "\u0001\u0004\u0000\u0001\u0001\u0004\u0004\u0000\u0000\u0000\u0001ဉ\u0000\u0002ဉ\u0001\u0003ဉ\u0002\u0004ဉ\u0003", new Object[]{"zzc", "zzd", "zze", "zzf", "zzg"});
                case 4:
                    return zzh;
                case 5:
                    zzgj<zzau> zzgjVar = zzi;
                    Object obj3 = zzgjVar;
                    if (zzgjVar == null) {
                        synchronized (zzau.class) {
                            zzgj<zzau> zzgjVar2 = zzi;
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
            zzau zzauVar = new zzau();
            zzh = zzauVar;
            zzeo.zza((Class<zzau>) zzau.class, zzauVar);
        }
    }

    /* JADX INFO: compiled from: com.google.mlkit:language-id@@16.1.1 */
    public static final class zzav extends zzeo<zzav, zza> implements zzgb {
        private static final zzav zzf;
        private static volatile zzgj<zzav> zzg;
        private int zzc;
        private zzaw zzd;
        private int zze;

        private zzav() {
        }

        /* JADX INFO: compiled from: com.google.mlkit:language-id@@16.1.1 */
        public static final class zza extends zzeo.zzb<zzav, zza> implements zzgb {
            private zza() {
                super(zzav.zzf);
            }

            /* synthetic */ zza(com.google.android.gms.internal.mlkit_language_id.zzx zzxVar) {
                this();
            }
        }

        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Type inference failed for: r2v13 */
        /* JADX WARN: Type inference failed for: r2v14, types: [com.google.android.gms.internal.mlkit_language_id.zzeo$zza, com.google.android.gms.internal.mlkit_language_id.zzgj<com.google.android.gms.internal.mlkit_language_id.zzy$zzav>] */
        /* JADX WARN: Type inference failed for: r2v17 */
        /* JADX WARN: Type inference failed for: r2v18 */
        @Override // com.google.android.gms.internal.mlkit_language_id.zzeo
        protected final Object zza(int i, Object obj, Object obj2) {
            ?? r2;
            com.google.android.gms.internal.mlkit_language_id.zzx zzxVar = null;
            switch (com.google.android.gms.internal.mlkit_language_id.zzx.zza[i - 1]) {
                case 1:
                    return new zzav();
                case 2:
                    return new zza(zzxVar);
                case 3:
                    return zza(zzf, "\u0001\u0002\u0000\u0001\u0001\u0002\u0002\u0000\u0000\u0000\u0001ဉ\u0000\u0002ဌ\u0001", new Object[]{"zzc", "zzd", "zze", com.google.android.gms.internal.mlkit_language_id.zzai.zzb()});
                case 4:
                    return zzf;
                case 5:
                    zzgj<zzav> zzgjVar = zzg;
                    Object obj3 = zzgjVar;
                    if (zzgjVar == null) {
                        synchronized (zzav.class) {
                            zzgj<zzav> zzgjVar2 = zzg;
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
            zzav zzavVar = new zzav();
            zzf = zzavVar;
            zzeo.zza((Class<zzav>) zzav.class, zzavVar);
        }
    }

    /* JADX INFO: compiled from: com.google.mlkit:language-id@@16.1.1 */
    public static final class zzaw extends zzeo<zzaw, zza> implements zzgb {
        private static final zzaw zzj;
        private static volatile zzgj<zzaw> zzk;
        private int zzc;
        private int zzd;
        private boolean zze;
        private boolean zzf;
        private int zzg;
        private float zzh;
        private zzam zzi;

        /* JADX INFO: compiled from: com.google.mlkit:language-id@@16.1.1 */
        public enum zzb implements zzet {
            MODE_UNSPECIFIED(0),
            STREAM(1),
            SINGLE_IMAGE(2);

            private static final zzes<zzb> zzd = new zzbq();
            private final int zze;

            @Override // com.google.android.gms.internal.mlkit_language_id.zzet
            public final int zza() {
                return this.zze;
            }

            public static zzev zzb() {
                return zzbr.zza;
            }

            @Override // java.lang.Enum
            public final String toString() {
                StringBuilder sb = new StringBuilder("<");
                sb.append(getClass().getName()).append('@').append(Integer.toHexString(System.identityHashCode(this)));
                sb.append(" number=").append(this.zze);
                return sb.append(" name=").append(name()).append('>').toString();
            }

            zzb(int i) {
                this.zze = i;
            }
        }

        private zzaw() {
        }

        /* JADX INFO: compiled from: com.google.mlkit:language-id@@16.1.1 */
        public static final class zza extends zzeo.zzb<zzaw, zza> implements zzgb {
            private zza() {
                super(zzaw.zzj);
            }

            /* synthetic */ zza(com.google.android.gms.internal.mlkit_language_id.zzx zzxVar) {
                this();
            }
        }

        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Type inference failed for: r9v12 */
        /* JADX WARN: Type inference failed for: r9v13, types: [com.google.android.gms.internal.mlkit_language_id.zzeo$zza, com.google.android.gms.internal.mlkit_language_id.zzgj<com.google.android.gms.internal.mlkit_language_id.zzy$zzaw>] */
        /* JADX WARN: Type inference failed for: r9v16 */
        /* JADX WARN: Type inference failed for: r9v17 */
        @Override // com.google.android.gms.internal.mlkit_language_id.zzeo
        protected final Object zza(int i, Object obj, Object obj2) {
            ?? r9;
            com.google.android.gms.internal.mlkit_language_id.zzx zzxVar = null;
            switch (com.google.android.gms.internal.mlkit_language_id.zzx.zza[i - 1]) {
                case 1:
                    return new zzaw();
                case 2:
                    return new zza(zzxVar);
                case 3:
                    return zza(zzj, "\u0001\u0006\u0000\u0001\u0001\u0006\u0006\u0000\u0000\u0000\u0001ဌ\u0000\u0002ဇ\u0001\u0003ဇ\u0002\u0004ဋ\u0003\u0005ခ\u0004\u0006ဉ\u0005", new Object[]{"zzc", "zzd", zzb.zzb(), "zze", "zzf", "zzg", "zzh", "zzi"});
                case 4:
                    return zzj;
                case 5:
                    zzgj<zzaw> zzgjVar = zzk;
                    Object obj3 = zzgjVar;
                    if (zzgjVar == null) {
                        synchronized (zzaw.class) {
                            zzgj<zzaw> zzgjVar2 = zzk;
                            r9 = zzgjVar2;
                            if (zzgjVar2 == null) {
                                ?? zzaVar = new zzeo.zza(zzj);
                                zzk = zzaVar;
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
            zzaw zzawVar = new zzaw();
            zzj = zzawVar;
            zzeo.zza((Class<zzaw>) zzaw.class, zzawVar);
        }
    }

    /* JADX INFO: compiled from: com.google.mlkit:language-id@@16.1.1 */
    public static final class zzax extends zzeo<zzax, zza> implements zzgb {
        private static final zzax zzh;
        private static volatile zzgj<zzax> zzi;
        private int zzc;
        private zzaf zzd;
        private zzae zze;
        private zzaw zzf;
        private zzew<zzan> zzg = zzl();

        private zzax() {
        }

        /* JADX INFO: compiled from: com.google.mlkit:language-id@@16.1.1 */
        public static final class zza extends zzeo.zzb<zzax, zza> implements zzgb {
            private zza() {
                super(zzax.zzh);
            }

            /* synthetic */ zza(com.google.android.gms.internal.mlkit_language_id.zzx zzxVar) {
                this();
            }
        }

        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Type inference failed for: r7v12 */
        /* JADX WARN: Type inference failed for: r7v13, types: [com.google.android.gms.internal.mlkit_language_id.zzeo$zza, com.google.android.gms.internal.mlkit_language_id.zzgj<com.google.android.gms.internal.mlkit_language_id.zzy$zzax>] */
        /* JADX WARN: Type inference failed for: r7v16 */
        /* JADX WARN: Type inference failed for: r7v17 */
        @Override // com.google.android.gms.internal.mlkit_language_id.zzeo
        protected final Object zza(int i, Object obj, Object obj2) {
            ?? r7;
            com.google.android.gms.internal.mlkit_language_id.zzx zzxVar = null;
            switch (com.google.android.gms.internal.mlkit_language_id.zzx.zza[i - 1]) {
                case 1:
                    return new zzax();
                case 2:
                    return new zza(zzxVar);
                case 3:
                    return zza(zzh, "\u0001\u0004\u0000\u0001\u0001\u0004\u0004\u0000\u0001\u0000\u0001ဉ\u0000\u0002ဉ\u0001\u0003ဉ\u0002\u0004\u001b", new Object[]{"zzc", "zzd", "zze", "zzf", "zzg", zzan.class});
                case 4:
                    return zzh;
                case 5:
                    zzgj<zzax> zzgjVar = zzi;
                    Object obj3 = zzgjVar;
                    if (zzgjVar == null) {
                        synchronized (zzax.class) {
                            zzgj<zzax> zzgjVar2 = zzi;
                            r7 = zzgjVar2;
                            if (zzgjVar2 == null) {
                                ?? zzaVar = new zzeo.zza(zzh);
                                zzi = zzaVar;
                                r7 = zzaVar;
                            }
                            break;
                        }
                        obj3 = r7;
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
            zzax zzaxVar = new zzax();
            zzh = zzaxVar;
            zzeo.zza((Class<zzax>) zzax.class, zzaxVar);
        }
    }

    /* JADX INFO: compiled from: com.google.mlkit:language-id@@16.1.1 */
    public static final class zzay extends zzeo<zzay, zza> implements zzgb {
        private static final zzay zzi;
        private static volatile zzgj<zzay> zzj;
        private int zzc;
        private zzaw zzd;
        private int zze;
        private long zzf;
        private long zzg;
        private zzew<zzbf> zzh = zzl();

        private zzay() {
        }

        /* JADX INFO: compiled from: com.google.mlkit:language-id@@16.1.1 */
        public static final class zza extends zzeo.zzb<zzay, zza> implements zzgb {
            private zza() {
                super(zzay.zzi);
            }

            /* synthetic */ zza(com.google.android.gms.internal.mlkit_language_id.zzx zzxVar) {
                this();
            }
        }

        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Type inference failed for: r9v12 */
        /* JADX WARN: Type inference failed for: r9v13, types: [com.google.android.gms.internal.mlkit_language_id.zzeo$zza, com.google.android.gms.internal.mlkit_language_id.zzgj<com.google.android.gms.internal.mlkit_language_id.zzy$zzay>] */
        /* JADX WARN: Type inference failed for: r9v16 */
        /* JADX WARN: Type inference failed for: r9v17 */
        @Override // com.google.android.gms.internal.mlkit_language_id.zzeo
        protected final Object zza(int i, Object obj, Object obj2) {
            ?? r9;
            com.google.android.gms.internal.mlkit_language_id.zzx zzxVar = null;
            switch (com.google.android.gms.internal.mlkit_language_id.zzx.zza[i - 1]) {
                case 1:
                    return new zzay();
                case 2:
                    return new zza(zzxVar);
                case 3:
                    return zza(zzi, "\u0001\u0005\u0000\u0001\u0001\u0005\u0005\u0000\u0001\u0000\u0001ဉ\u0000\u0002ဌ\u0001\u0003ဃ\u0002\u0004ဃ\u0003\u0005\u001b", new Object[]{"zzc", "zzd", "zze", com.google.android.gms.internal.mlkit_language_id.zzai.zzb(), "zzf", "zzg", "zzh", zzbf.class});
                case 4:
                    return zzi;
                case 5:
                    zzgj<zzay> zzgjVar = zzj;
                    Object obj3 = zzgjVar;
                    if (zzgjVar == null) {
                        synchronized (zzay.class) {
                            zzgj<zzay> zzgjVar2 = zzj;
                            r9 = zzgjVar2;
                            if (zzgjVar2 == null) {
                                ?? zzaVar = new zzeo.zza(zzi);
                                zzj = zzaVar;
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
            zzay zzayVar = new zzay();
            zzi = zzayVar;
            zzeo.zza((Class<zzay>) zzay.class, zzayVar);
        }
    }

    /* JADX INFO: compiled from: com.google.mlkit:language-id@@16.1.1 */
    public static final class zzaz extends zzeo<zzaz, zza> implements zzgb {
        private static final zzaz zzg;
        private static volatile zzgj<zzaz> zzh;
        private int zzc;
        private zzaf zzd;
        private zzae zze;
        private zzba zzf;

        private zzaz() {
        }

        /* JADX INFO: compiled from: com.google.mlkit:language-id@@16.1.1 */
        public static final class zza extends zzeo.zzb<zzaz, zza> implements zzgb {
            private zza() {
                super(zzaz.zzg);
            }

            /* synthetic */ zza(com.google.android.gms.internal.mlkit_language_id.zzx zzxVar) {
                this();
            }
        }

        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Type inference failed for: r2v13 */
        /* JADX WARN: Type inference failed for: r2v14, types: [com.google.android.gms.internal.mlkit_language_id.zzeo$zza, com.google.android.gms.internal.mlkit_language_id.zzgj<com.google.android.gms.internal.mlkit_language_id.zzy$zzaz>] */
        /* JADX WARN: Type inference failed for: r2v17 */
        /* JADX WARN: Type inference failed for: r2v18 */
        @Override // com.google.android.gms.internal.mlkit_language_id.zzeo
        protected final Object zza(int i, Object obj, Object obj2) {
            ?? r2;
            com.google.android.gms.internal.mlkit_language_id.zzx zzxVar = null;
            switch (com.google.android.gms.internal.mlkit_language_id.zzx.zza[i - 1]) {
                case 1:
                    return new zzaz();
                case 2:
                    return new zza(zzxVar);
                case 3:
                    return zza(zzg, "\u0001\u0003\u0000\u0001\u0001\u0003\u0003\u0000\u0000\u0000\u0001ဉ\u0000\u0002ဉ\u0001\u0003ဉ\u0002", new Object[]{"zzc", "zzd", "zze", "zzf"});
                case 4:
                    return zzg;
                case 5:
                    zzgj<zzaz> zzgjVar = zzh;
                    Object obj3 = zzgjVar;
                    if (zzgjVar == null) {
                        synchronized (zzaz.class) {
                            zzgj<zzaz> zzgjVar2 = zzh;
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
            zzaz zzazVar = new zzaz();
            zzg = zzazVar;
            zzeo.zza((Class<zzaz>) zzaz.class, zzazVar);
        }
    }

    /* JADX INFO: compiled from: com.google.mlkit:language-id@@16.1.1 */
    public static final class zzb extends zzeo<zzb, zza> implements zzgb {
        private static final zzb zzg;
        private static volatile zzgj<zzb> zzh;
        private int zzc;
        private C0052zzb zzd;
        private int zze;
        private zzab zzf;

        /* JADX INFO: renamed from: com.google.android.gms.internal.mlkit_language_id.zzy$zzb$zzb, reason: collision with other inner class name */
        /* JADX INFO: compiled from: com.google.mlkit:language-id@@16.1.1 */
        public static final class C0052zzb extends zzeo<C0052zzb, zza> implements zzgb {
            private static final C0052zzb zzi;
            private static volatile zzgj<C0052zzb> zzj;
            private int zzc;
            private int zzd;
            private boolean zze;
            private zzew<C0053zzy.zzb> zzf = zzl();
            private zzew<C0053zzy.zzb> zzg = zzl();
            private zzam zzh;

            private C0052zzb() {
            }

            /* JADX INFO: renamed from: com.google.android.gms.internal.mlkit_language_id.zzy$zzb$zzb$zza */
            /* JADX INFO: compiled from: com.google.mlkit:language-id@@16.1.1 */
            public static final class zza extends zzeo.zzb<C0052zzb, zza> implements zzgb {
                private zza() {
                    super(C0052zzb.zzi);
                }

                /* synthetic */ zza(com.google.android.gms.internal.mlkit_language_id.zzx zzxVar) {
                    this();
                }
            }

            /* JADX WARN: Multi-variable type inference failed */
            /* JADX WARN: Type inference failed for: r10v12 */
            /* JADX WARN: Type inference failed for: r10v13, types: [com.google.android.gms.internal.mlkit_language_id.zzeo$zza, com.google.android.gms.internal.mlkit_language_id.zzgj<com.google.android.gms.internal.mlkit_language_id.zzy$zzb$zzb>] */
            /* JADX WARN: Type inference failed for: r10v16 */
            /* JADX WARN: Type inference failed for: r10v17 */
            @Override // com.google.android.gms.internal.mlkit_language_id.zzeo
            protected final Object zza(int i, Object obj, Object obj2) {
                ?? r10;
                com.google.android.gms.internal.mlkit_language_id.zzx zzxVar = null;
                switch (com.google.android.gms.internal.mlkit_language_id.zzx.zza[i - 1]) {
                    case 1:
                        return new C0052zzb();
                    case 2:
                        return new zza(zzxVar);
                    case 3:
                        return zza(zzi, "\u0001\u0005\u0000\u0001\u0001\u0005\u0005\u0000\u0002\u0000\u0001ဌ\u0000\u0002ဇ\u0001\u0003\u001b\u0004\u001b\u0005ဉ\u0002", new Object[]{"zzc", "zzd", com.google.android.gms.internal.mlkit_language_id.zzai.zzb(), "zze", "zzf", C0053zzy.zzb.class, "zzg", C0053zzy.zzb.class, "zzh"});
                    case 4:
                        return zzi;
                    case 5:
                        zzgj<C0052zzb> zzgjVar = zzj;
                        Object obj3 = zzgjVar;
                        if (zzgjVar == null) {
                            synchronized (C0052zzb.class) {
                                zzgj<C0052zzb> zzgjVar2 = zzj;
                                r10 = zzgjVar2;
                                if (zzgjVar2 == null) {
                                    ?? zzaVar = new zzeo.zza(zzi);
                                    zzj = zzaVar;
                                    r10 = zzaVar;
                                }
                                break;
                            }
                            obj3 = r10;
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
                C0052zzb c0052zzb = new C0052zzb();
                zzi = c0052zzb;
                zzeo.zza((Class<C0052zzb>) C0052zzb.class, c0052zzb);
            }
        }

        private zzb() {
        }

        /* JADX INFO: compiled from: com.google.mlkit:language-id@@16.1.1 */
        public static final class zza extends zzeo.zzb<zzb, zza> implements zzgb {
            private zza() {
                super(zzb.zzg);
            }

            /* synthetic */ zza(com.google.android.gms.internal.mlkit_language_id.zzx zzxVar) {
                this();
            }
        }

        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Type inference failed for: r2v13 */
        /* JADX WARN: Type inference failed for: r2v14, types: [com.google.android.gms.internal.mlkit_language_id.zzeo$zza, com.google.android.gms.internal.mlkit_language_id.zzgj<com.google.android.gms.internal.mlkit_language_id.zzy$zzb>] */
        /* JADX WARN: Type inference failed for: r2v17 */
        /* JADX WARN: Type inference failed for: r2v18 */
        @Override // com.google.android.gms.internal.mlkit_language_id.zzeo
        protected final Object zza(int i, Object obj, Object obj2) {
            ?? r2;
            com.google.android.gms.internal.mlkit_language_id.zzx zzxVar = null;
            switch (com.google.android.gms.internal.mlkit_language_id.zzx.zza[i - 1]) {
                case 1:
                    return new zzb();
                case 2:
                    return new zza(zzxVar);
                case 3:
                    return zza(zzg, "\u0001\u0003\u0000\u0001\u0001\u0003\u0003\u0000\u0000\u0000\u0001ဉ\u0000\u0002ဋ\u0001\u0003ဉ\u0002", new Object[]{"zzc", "zzd", "zze", "zzf"});
                case 4:
                    return zzg;
                case 5:
                    zzgj<zzb> zzgjVar = zzh;
                    Object obj3 = zzgjVar;
                    if (zzgjVar == null) {
                        synchronized (zzb.class) {
                            zzgj<zzb> zzgjVar2 = zzh;
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
            zzb zzbVar = new zzb();
            zzg = zzbVar;
            zzeo.zza((Class<zzb>) zzb.class, zzbVar);
        }
    }

    /* JADX INFO: compiled from: com.google.mlkit:language-id@@16.1.1 */
    public static final class zzba extends zzeo<zzba, zzb> implements zzgb {
        private static final zzba zzg;
        private static volatile zzgj<zzba> zzh;
        private int zzc;
        private int zzd;
        private int zze;
        private int zzf;

        /* JADX INFO: compiled from: com.google.mlkit:language-id@@16.1.1 */
        public enum zza implements zzet {
            INVALID_MODE(0),
            STREAM(1),
            SINGLE_IMAGE(2);

            private static final zzes<zza> zzd = new zzbt();
            private final int zze;

            @Override // com.google.android.gms.internal.mlkit_language_id.zzet
            public final int zza() {
                return this.zze;
            }

            public static zzev zzb() {
                return zzbs.zza;
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
            UNKNOWN_PERFORMANCE(0),
            FAST(1),
            ACCURATE(2);

            private static final zzes<zzc> zzd = new zzbu();
            private final int zze;

            @Override // com.google.android.gms.internal.mlkit_language_id.zzet
            public final int zza() {
                return this.zze;
            }

            public static zzev zzb() {
                return zzbv.zza;
            }

            @Override // java.lang.Enum
            public final String toString() {
                StringBuilder sb = new StringBuilder("<");
                sb.append(getClass().getName()).append('@').append(Integer.toHexString(System.identityHashCode(this)));
                sb.append(" number=").append(this.zze);
                return sb.append(" name=").append(name()).append('>').toString();
            }

            zzc(int i) {
                this.zze = i;
            }
        }

        private zzba() {
        }

        /* JADX INFO: compiled from: com.google.mlkit:language-id@@16.1.1 */
        public static final class zzb extends zzeo.zzb<zzba, zzb> implements zzgb {
            private zzb() {
                super(zzba.zzg);
            }

            /* synthetic */ zzb(com.google.android.gms.internal.mlkit_language_id.zzx zzxVar) {
                this();
            }
        }

        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Type inference failed for: r8v12 */
        /* JADX WARN: Type inference failed for: r8v13, types: [com.google.android.gms.internal.mlkit_language_id.zzeo$zza, com.google.android.gms.internal.mlkit_language_id.zzgj<com.google.android.gms.internal.mlkit_language_id.zzy$zzba>] */
        /* JADX WARN: Type inference failed for: r8v16 */
        /* JADX WARN: Type inference failed for: r8v17 */
        @Override // com.google.android.gms.internal.mlkit_language_id.zzeo
        protected final Object zza(int i, Object obj, Object obj2) {
            ?? r8;
            com.google.android.gms.internal.mlkit_language_id.zzx zzxVar = null;
            switch (com.google.android.gms.internal.mlkit_language_id.zzx.zza[i - 1]) {
                case 1:
                    return new zzba();
                case 2:
                    return new zzb(zzxVar);
                case 3:
                    return zza(zzg, "\u0001\u0003\u0000\u0001\u0001\u0003\u0003\u0000\u0000\u0000\u0001ဌ\u0000\u0002ဌ\u0001\u0003ဌ\u0002", new Object[]{"zzc", "zzd", zza.zzb(), "zze", zzc.zzb(), "zzf", zzc.zzb()});
                case 4:
                    return zzg;
                case 5:
                    zzgj<zzba> zzgjVar = zzh;
                    Object obj3 = zzgjVar;
                    if (zzgjVar == null) {
                        synchronized (zzba.class) {
                            zzgj<zzba> zzgjVar2 = zzh;
                            r8 = zzgjVar2;
                            if (zzgjVar2 == null) {
                                ?? zzaVar = new zzeo.zza(zzg);
                                zzh = zzaVar;
                                r8 = zzaVar;
                            }
                            break;
                        }
                        obj3 = r8;
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
            zzba zzbaVar = new zzba();
            zzg = zzbaVar;
            zzeo.zza((Class<zzba>) zzba.class, zzbaVar);
        }
    }

    /* JADX INFO: compiled from: com.google.mlkit:language-id@@16.1.1 */
    public static final class zzbb extends zzeo<zzbb, zza> implements zzgb {
        private static final zzbb zzf;
        private static volatile zzgj<zzbb> zzg;
        private int zzc;
        private zzaf zzd;
        private zzae zze;

        private zzbb() {
        }

        /* JADX INFO: compiled from: com.google.mlkit:language-id@@16.1.1 */
        public static final class zza extends zzeo.zzb<zzbb, zza> implements zzgb {
            private zza() {
                super(zzbb.zzf);
            }

            /* synthetic */ zza(com.google.android.gms.internal.mlkit_language_id.zzx zzxVar) {
                this();
            }
        }

        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Type inference failed for: r1v13 */
        /* JADX WARN: Type inference failed for: r1v14, types: [com.google.android.gms.internal.mlkit_language_id.zzeo$zza, com.google.android.gms.internal.mlkit_language_id.zzgj<com.google.android.gms.internal.mlkit_language_id.zzy$zzbb>] */
        /* JADX WARN: Type inference failed for: r1v17 */
        /* JADX WARN: Type inference failed for: r1v18 */
        @Override // com.google.android.gms.internal.mlkit_language_id.zzeo
        protected final Object zza(int i, Object obj, Object obj2) {
            ?? r1;
            com.google.android.gms.internal.mlkit_language_id.zzx zzxVar = null;
            switch (com.google.android.gms.internal.mlkit_language_id.zzx.zza[i - 1]) {
                case 1:
                    return new zzbb();
                case 2:
                    return new zza(zzxVar);
                case 3:
                    return zza(zzf, "\u0001\u0002\u0000\u0001\u0001\u0002\u0002\u0000\u0000\u0000\u0001ဉ\u0000\u0002ဉ\u0001", new Object[]{"zzc", "zzd", "zze"});
                case 4:
                    return zzf;
                case 5:
                    zzgj<zzbb> zzgjVar = zzg;
                    Object obj3 = zzgjVar;
                    if (zzgjVar == null) {
                        synchronized (zzbb.class) {
                            zzgj<zzbb> zzgjVar2 = zzg;
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
            zzbb zzbbVar = new zzbb();
            zzf = zzbbVar;
            zzeo.zza((Class<zzbb>) zzbb.class, zzbbVar);
        }
    }

    /* JADX INFO: compiled from: com.google.mlkit:language-id@@16.1.1 */
    public static final class zzbc extends zzeo<zzbc, zzb> implements zzgb {
        private static final zzbc zzi;
        private static volatile zzgj<zzbc> zzj;
        private int zzc;
        private zzaf zzd;
        private zzew<zzc> zze = zzl();
        private int zzf;
        private int zzg;
        private int zzh;

        /* JADX INFO: compiled from: com.google.mlkit:language-id@@16.1.1 */
        public enum zza implements zzet {
            NO_ERROR(0),
            STATUS_SENSITIVE_TOPIC(1),
            STATUS_QUALITY_THRESHOLDED(2),
            STATUS_INTERNAL_ERROR(3),
            STATUS_NOT_SUPPORTED_LANGUAGE(101),
            STATUS_32_BIT_CPU(1001),
            STATUS_32_BIT_APP(1002);

            private static final zzes<zza> zzh = new zzbx();
            private final int zzi;

            @Override // com.google.android.gms.internal.mlkit_language_id.zzet
            public final int zza() {
                return this.zzi;
            }

            public static zzev zzb() {
                return zzbw.zza;
            }

            @Override // java.lang.Enum
            public final String toString() {
                StringBuilder sb = new StringBuilder("<");
                sb.append(getClass().getName()).append('@').append(Integer.toHexString(System.identityHashCode(this)));
                sb.append(" number=").append(this.zzi);
                return sb.append(" name=").append(name()).append('>').toString();
            }

            zza(int i) {
                this.zzi = i;
            }
        }

        /* JADX INFO: compiled from: com.google.mlkit:language-id@@16.1.1 */
        public static final class zzc extends zzeo<zzc, zza> implements zzgb {
            private static final zzc zze;
            private static volatile zzgj<zzc> zzf;
            private int zzc;
            private float zzd;

            private zzc() {
            }

            /* JADX INFO: compiled from: com.google.mlkit:language-id@@16.1.1 */
            public static final class zza extends zzeo.zzb<zzc, zza> implements zzgb {
                private zza() {
                    super(zzc.zze);
                }

                /* synthetic */ zza(com.google.android.gms.internal.mlkit_language_id.zzx zzxVar) {
                    this();
                }
            }

            /* JADX WARN: Multi-variable type inference failed */
            /* JADX WARN: Type inference failed for: r1v13 */
            /* JADX WARN: Type inference failed for: r1v14, types: [com.google.android.gms.internal.mlkit_language_id.zzeo$zza, com.google.android.gms.internal.mlkit_language_id.zzgj<com.google.android.gms.internal.mlkit_language_id.zzy$zzbc$zzc>] */
            /* JADX WARN: Type inference failed for: r1v17 */
            /* JADX WARN: Type inference failed for: r1v18 */
            @Override // com.google.android.gms.internal.mlkit_language_id.zzeo
            protected final Object zza(int i, Object obj, Object obj2) {
                ?? r1;
                com.google.android.gms.internal.mlkit_language_id.zzx zzxVar = null;
                switch (com.google.android.gms.internal.mlkit_language_id.zzx.zza[i - 1]) {
                    case 1:
                        return new zzc();
                    case 2:
                        return new zza(zzxVar);
                    case 3:
                        return zza(zze, "\u0001\u0001\u0000\u0001\u0001\u0001\u0001\u0000\u0000\u0000\u0001ခ\u0000", new Object[]{"zzc", "zzd"});
                    case 4:
                        return zze;
                    case 5:
                        zzgj<zzc> zzgjVar = zzf;
                        Object obj3 = zzgjVar;
                        if (zzgjVar == null) {
                            synchronized (zzc.class) {
                                zzgj<zzc> zzgjVar2 = zzf;
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
                zzc zzcVar = new zzc();
                zze = zzcVar;
                zzeo.zza((Class<zzc>) zzc.class, zzcVar);
            }
        }

        private zzbc() {
        }

        /* JADX INFO: compiled from: com.google.mlkit:language-id@@16.1.1 */
        public static final class zzb extends zzeo.zzb<zzbc, zzb> implements zzgb {
            private zzb() {
                super(zzbc.zzi);
            }

            /* synthetic */ zzb(com.google.android.gms.internal.mlkit_language_id.zzx zzxVar) {
                this();
            }
        }

        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Type inference failed for: r9v12 */
        /* JADX WARN: Type inference failed for: r9v13, types: [com.google.android.gms.internal.mlkit_language_id.zzeo$zza, com.google.android.gms.internal.mlkit_language_id.zzgj<com.google.android.gms.internal.mlkit_language_id.zzy$zzbc>] */
        /* JADX WARN: Type inference failed for: r9v16 */
        /* JADX WARN: Type inference failed for: r9v17 */
        @Override // com.google.android.gms.internal.mlkit_language_id.zzeo
        protected final Object zza(int i, Object obj, Object obj2) {
            ?? r9;
            com.google.android.gms.internal.mlkit_language_id.zzx zzxVar = null;
            switch (com.google.android.gms.internal.mlkit_language_id.zzx.zza[i - 1]) {
                case 1:
                    return new zzbc();
                case 2:
                    return new zzb(zzxVar);
                case 3:
                    return zza(zzi, "\u0001\u0005\u0000\u0001\u0001\u0005\u0005\u0000\u0001\u0000\u0001ဉ\u0000\u0002\u001b\u0003ဌ\u0001\u0004င\u0002\u0005င\u0003", new Object[]{"zzc", "zzd", "zze", zzc.class, "zzf", zza.zzb(), "zzg", "zzh"});
                case 4:
                    return zzi;
                case 5:
                    zzgj<zzbc> zzgjVar = zzj;
                    Object obj3 = zzgjVar;
                    if (zzgjVar == null) {
                        synchronized (zzbc.class) {
                            zzgj<zzbc> zzgjVar2 = zzj;
                            r9 = zzgjVar2;
                            if (zzgjVar2 == null) {
                                ?? zzaVar = new zzeo.zza(zzi);
                                zzj = zzaVar;
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
            zzbc zzbcVar = new zzbc();
            zzi = zzbcVar;
            zzeo.zza((Class<zzbc>) zzbc.class, zzbcVar);
        }
    }

    /* JADX INFO: compiled from: com.google.mlkit:language-id@@16.1.1 */
    public static final class zzbd extends zzeo<zzbd, zza> implements zzgb {
        private static final zzbd zzf;
        private static volatile zzgj<zzbd> zzg;
        private int zzc;
        private zzaf zzd;
        private zzae zze;

        private zzbd() {
        }

        /* JADX INFO: compiled from: com.google.mlkit:language-id@@16.1.1 */
        public static final class zza extends zzeo.zzb<zzbd, zza> implements zzgb {
            private zza() {
                super(zzbd.zzf);
            }

            /* synthetic */ zza(com.google.android.gms.internal.mlkit_language_id.zzx zzxVar) {
                this();
            }
        }

        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Type inference failed for: r1v13 */
        /* JADX WARN: Type inference failed for: r1v14, types: [com.google.android.gms.internal.mlkit_language_id.zzeo$zza, com.google.android.gms.internal.mlkit_language_id.zzgj<com.google.android.gms.internal.mlkit_language_id.zzy$zzbd>] */
        /* JADX WARN: Type inference failed for: r1v17 */
        /* JADX WARN: Type inference failed for: r1v18 */
        @Override // com.google.android.gms.internal.mlkit_language_id.zzeo
        protected final Object zza(int i, Object obj, Object obj2) {
            ?? r1;
            com.google.android.gms.internal.mlkit_language_id.zzx zzxVar = null;
            switch (com.google.android.gms.internal.mlkit_language_id.zzx.zza[i - 1]) {
                case 1:
                    return new zzbd();
                case 2:
                    return new zza(zzxVar);
                case 3:
                    return zza(zzf, "\u0001\u0002\u0000\u0001\u0001\u0002\u0002\u0000\u0000\u0000\u0001ဉ\u0000\u0002ဉ\u0001", new Object[]{"zzc", "zzd", "zze"});
                case 4:
                    return zzf;
                case 5:
                    zzgj<zzbd> zzgjVar = zzg;
                    Object obj3 = zzgjVar;
                    if (zzgjVar == null) {
                        synchronized (zzbd.class) {
                            zzgj<zzbd> zzgjVar2 = zzg;
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
            zzbd zzbdVar = new zzbd();
            zzf = zzbdVar;
            zzeo.zza((Class<zzbd>) zzbd.class, zzbdVar);
        }
    }

    /* JADX INFO: compiled from: com.google.mlkit:language-id@@16.1.1 */
    public static final class zzbe extends zzeo<zzbe, zza> implements zzgb {
        private static final zzbe zzl;
        private static volatile zzgj<zzbe> zzm;
        private int zzc;
        private zzaf zzd;
        private zzbi zze;
        private int zzf;
        private int zzg;
        private int zzh;
        private int zzi;
        private int zzj;
        private int zzk;

        /* JADX INFO: compiled from: com.google.mlkit:language-id@@16.1.1 */
        public enum zzb implements zzet {
            NO_ERROR(0),
            METADATA_FILE_UNAVAILABLE(1),
            METADATA_ENTRY_NOT_FOUND(2),
            METADATA_JSON_INVALID(3),
            METADATA_HASH_NOT_FOUND(4),
            DOWNLOAD_MANAGER_SERVICE_MISSING(5),
            DOWNLOAD_MANAGER_HTTP_UNKNOWN_STATUS(6),
            DOWNLOAD_MANAGER_HTTP_BAD_REQUEST(400),
            DOWNLOAD_MANAGER_HTTP_UNAUTHORIZED(401),
            DOWNLOAD_MANAGER_HTTP_FORBIDDEN(403),
            DOWNLOAD_MANAGER_HTTP_NOT_FOUND(404),
            DOWNLOAD_MANAGER_HTTP_REQUEST_TIMEOUT(HttpStatus.SC_REQUEST_TIMEOUT),
            DOWNLOAD_MANAGER_HTTP_ABORTED(HttpStatus.SC_CONFLICT),
            DOWNLOAD_MANAGER_HTTP_TOO_MANY_REQUESTS(Constants.ERROR_TOO_MANY_REQUESTS),
            DOWNLOAD_MANAGER_HTTP_CANCELLED(499),
            DOWNLOAD_MANAGER_HTTP_UNIMPLEMENTED(501),
            DOWNLOAD_MANAGER_HTTP_INTERNAL_SERVICE_ERROR(500),
            DOWNLOAD_MANAGER_HTTP_SERVICE_UNAVAILABLE(503),
            DOWNLOAD_MANAGER_HTTP_DEADLINE_EXCEEDED(504),
            DOWNLOAD_MANAGER_HTTP_NETWORK_AUTHENTICATION_REQUIRED(511),
            DOWNLOAD_MANAGER_FILE_ERROR(7),
            DOWNLOAD_MANAGER_UNHANDLED_HTTP_CODE(8),
            DOWNLOAD_MANAGER_HTTP_DATA_ERROR(9),
            DOWNLOAD_MANAGER_TOO_MANY_REDIRECTS(10),
            DOWNLOAD_MANAGER_INSUFFICIENT_SPACE(11),
            DOWNLOAD_MANAGER_DEVICE_NOT_FOUND(12),
            DOWNLOAD_MANAGER_CANNOT_RESUME(13),
            DOWNLOAD_MANAGER_FILE_ALREADY_EXISTS(14),
            DOWNLOAD_MANAGER_UNKNOWN_ERROR(15),
            POST_DOWNLOAD_FILE_NOT_FOUND(16),
            POST_DOWNLOAD_MOVE_FILE_FAILED(17),
            POST_DOWNLOAD_UNZIP_FAILED(18),
            RAPID_RESPONSE_COULD_NOT_BE_WRITTEN(19),
            DRIVER_OBJECT_DEALLOCATED(20);

            private static final zzes<zzb> zzai = new zzby();
            private final int zzaj;

            @Override // com.google.android.gms.internal.mlkit_language_id.zzet
            public final int zza() {
                return this.zzaj;
            }

            public static zzev zzb() {
                return zzbz.zza;
            }

            @Override // java.lang.Enum
            public final String toString() {
                StringBuilder sb = new StringBuilder("<");
                sb.append(getClass().getName()).append('@').append(Integer.toHexString(System.identityHashCode(this)));
                sb.append(" number=").append(this.zzaj);
                return sb.append(" name=").append(name()).append('>').toString();
            }

            zzb(int i) {
                this.zzaj = i;
            }
        }

        private zzbe() {
        }

        /* JADX INFO: compiled from: com.google.mlkit:language-id@@16.1.1 */
        public static final class zza extends zzeo.zzb<zzbe, zza> implements zzgb {
            private zza() {
                super(zzbe.zzl);
            }

            /* synthetic */ zza(com.google.android.gms.internal.mlkit_language_id.zzx zzxVar) {
                this();
            }
        }

        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Type inference failed for: r11v12 */
        /* JADX WARN: Type inference failed for: r11v13, types: [com.google.android.gms.internal.mlkit_language_id.zzeo$zza, com.google.android.gms.internal.mlkit_language_id.zzgj<com.google.android.gms.internal.mlkit_language_id.zzy$zzbe>] */
        /* JADX WARN: Type inference failed for: r11v16 */
        /* JADX WARN: Type inference failed for: r11v17 */
        @Override // com.google.android.gms.internal.mlkit_language_id.zzeo
        protected final Object zza(int i, Object obj, Object obj2) {
            ?? r11;
            com.google.android.gms.internal.mlkit_language_id.zzx zzxVar = null;
            switch (com.google.android.gms.internal.mlkit_language_id.zzx.zza[i - 1]) {
                case 1:
                    return new zzbe();
                case 2:
                    return new zza(zzxVar);
                case 3:
                    return zza(zzl, "\u0001\b\u0000\u0001\u0001\b\b\u0000\u0000\u0000\u0001ဉ\u0000\u0002ဉ\u0001\u0003င\u0002\u0004င\u0003\u0005င\u0004\u0006င\u0005\u0007ဌ\u0006\bင\u0007", new Object[]{"zzc", "zzd", "zze", "zzf", "zzg", "zzh", "zzi", "zzj", zzb.zzb(), "zzk"});
                case 4:
                    return zzl;
                case 5:
                    zzgj<zzbe> zzgjVar = zzm;
                    Object obj3 = zzgjVar;
                    if (zzgjVar == null) {
                        synchronized (zzbe.class) {
                            zzgj<zzbe> zzgjVar2 = zzm;
                            r11 = zzgjVar2;
                            if (zzgjVar2 == null) {
                                ?? zzaVar = new zzeo.zza(zzl);
                                zzm = zzaVar;
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
            zzbe zzbeVar = new zzbe();
            zzl = zzbeVar;
            zzeo.zza((Class<zzbe>) zzbe.class, zzbeVar);
        }
    }

    /* JADX INFO: compiled from: com.google.mlkit:language-id@@16.1.1 */
    public static final class zzbf extends zzeo<zzbf, zzb> implements zzgb {
        private static final zzbf zzf;
        private static volatile zzgj<zzbf> zzg;
        private int zzc;
        private int zzd;
        private int zze;

        /* JADX INFO: compiled from: com.google.mlkit:language-id@@16.1.1 */
        public enum zza implements zzet {
            UNKNOWN(0),
            CANONICAL(1),
            TFLITE(2),
            TFLITE_SUPPORT(3);

            private static final zzes<zza> zze = new zzcb();
            private final int zzf;

            @Override // com.google.android.gms.internal.mlkit_language_id.zzet
            public final int zza() {
                return this.zzf;
            }

            public static zzev zzb() {
                return zzca.zza;
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

        private zzbf() {
        }

        /* JADX INFO: compiled from: com.google.mlkit:language-id@@16.1.1 */
        public static final class zzb extends zzeo.zzb<zzbf, zzb> implements zzgb {
            private zzb() {
                super(zzbf.zzf);
            }

            /* synthetic */ zzb(com.google.android.gms.internal.mlkit_language_id.zzx zzxVar) {
                this();
            }
        }

        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Type inference failed for: r2v13 */
        /* JADX WARN: Type inference failed for: r2v14, types: [com.google.android.gms.internal.mlkit_language_id.zzeo$zza, com.google.android.gms.internal.mlkit_language_id.zzgj<com.google.android.gms.internal.mlkit_language_id.zzy$zzbf>] */
        /* JADX WARN: Type inference failed for: r2v17 */
        /* JADX WARN: Type inference failed for: r2v18 */
        @Override // com.google.android.gms.internal.mlkit_language_id.zzeo
        protected final Object zza(int i, Object obj, Object obj2) {
            ?? r2;
            com.google.android.gms.internal.mlkit_language_id.zzx zzxVar = null;
            switch (com.google.android.gms.internal.mlkit_language_id.zzx.zza[i - 1]) {
                case 1:
                    return new zzbf();
                case 2:
                    return new zzb(zzxVar);
                case 3:
                    return zza(zzf, "\u0001\u0002\u0000\u0001\u0001\u0002\u0002\u0000\u0000\u0000\u0001ဌ\u0000\u0002င\u0001", new Object[]{"zzc", "zzd", zza.zzb(), "zze"});
                case 4:
                    return zzf;
                case 5:
                    zzgj<zzbf> zzgjVar = zzg;
                    Object obj3 = zzgjVar;
                    if (zzgjVar == null) {
                        synchronized (zzbf.class) {
                            zzgj<zzbf> zzgjVar2 = zzg;
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
            zzbf zzbfVar = new zzbf();
            zzf = zzbfVar;
            zzeo.zza((Class<zzbf>) zzbf.class, zzbfVar);
        }
    }

    /* JADX INFO: compiled from: com.google.mlkit:language-id@@16.1.1 */
    public static final class zzbg extends zzeo<zzbg, zzb> implements zzgb {
        private static final zzex<Integer, zza> zzf = new zzcc();
        private static final zzex<Integer, zza> zzh = new zzce();
        private static final zzex<Integer, zza> zzj = new zzcd();
        private static final zzbg zzl;
        private static volatile zzgj<zzbg> zzm;
        private int zzc;
        private long zzd;
        private zzeu zze = zzk();
        private zzeu zzg = zzk();
        private zzeu zzi = zzk();
        private int zzk;

        /* JADX INFO: compiled from: com.google.mlkit:language-id@@16.1.1 */
        public enum zza implements zzet {
            UNKNOWN_ERROR(0),
            NO_CONNECTION(1),
            RPC_ERROR(2),
            RPC_RETURNED_INVALID_RESULT(3),
            RPC_RETURNED_MALFORMED_RESULT(4),
            RPC_EXPONENTIAL_BACKOFF_FAILED(5),
            DIRECTORY_CREATION_FAILED(10),
            FILE_WRITE_FAILED_DISK_FULL(11),
            FILE_WRITE_FAILED(12),
            FILE_READ_FAILED(13),
            FILE_READ_RETURNED_INVALID_DATA(14),
            FILE_READ_RETURNED_MALFORMED_DATA(15);

            private static final zzes<zza> zzm = new zzcg();
            private final int zzn;

            @Override // com.google.android.gms.internal.mlkit_language_id.zzet
            public final int zza() {
                return this.zzn;
            }

            public static zzev zzb() {
                return zzcf.zza;
            }

            @Override // java.lang.Enum
            public final String toString() {
                StringBuilder sb = new StringBuilder("<");
                sb.append(getClass().getName()).append('@').append(Integer.toHexString(System.identityHashCode(this)));
                sb.append(" number=").append(this.zzn);
                return sb.append(" name=").append(name()).append('>').toString();
            }

            zza(int i) {
                this.zzn = i;
            }
        }

        private zzbg() {
        }

        /* JADX INFO: compiled from: com.google.mlkit:language-id@@16.1.1 */
        public static final class zzb extends zzeo.zzb<zzbg, zzb> implements zzgb {
            private zzb() {
                super(zzbg.zzl);
            }

            /* synthetic */ zzb(com.google.android.gms.internal.mlkit_language_id.zzx zzxVar) {
                this();
            }
        }

        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Type inference failed for: r10v12 */
        /* JADX WARN: Type inference failed for: r10v13, types: [com.google.android.gms.internal.mlkit_language_id.zzeo$zza, com.google.android.gms.internal.mlkit_language_id.zzgj<com.google.android.gms.internal.mlkit_language_id.zzy$zzbg>] */
        /* JADX WARN: Type inference failed for: r10v16 */
        /* JADX WARN: Type inference failed for: r10v17 */
        @Override // com.google.android.gms.internal.mlkit_language_id.zzeo
        protected final Object zza(int i, Object obj, Object obj2) {
            ?? r10;
            com.google.android.gms.internal.mlkit_language_id.zzx zzxVar = null;
            switch (com.google.android.gms.internal.mlkit_language_id.zzx.zza[i - 1]) {
                case 1:
                    return new zzbg();
                case 2:
                    return new zzb(zzxVar);
                case 3:
                    return zza(zzl, "\u0001\u0005\u0000\u0001\u0001\u0005\u0005\u0000\u0003\u0000\u0001ဃ\u0000\u0002\u001e\u0003\u001e\u0004\u001e\u0005င\u0001", new Object[]{"zzc", "zzd", "zze", zza.zzb(), "zzg", zza.zzb(), "zzi", zza.zzb(), "zzk"});
                case 4:
                    return zzl;
                case 5:
                    zzgj<zzbg> zzgjVar = zzm;
                    Object obj3 = zzgjVar;
                    if (zzgjVar == null) {
                        synchronized (zzbg.class) {
                            zzgj<zzbg> zzgjVar2 = zzm;
                            r10 = zzgjVar2;
                            if (zzgjVar2 == null) {
                                ?? zzaVar = new zzeo.zza(zzl);
                                zzm = zzaVar;
                                r10 = zzaVar;
                            }
                            break;
                        }
                        obj3 = r10;
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

        /* JADX WARN: Type inference failed for: r0v0, types: [com.google.android.gms.internal.mlkit_language_id.zzcc, com.google.android.gms.internal.mlkit_language_id.zzex<java.lang.Integer, com.google.android.gms.internal.mlkit_language_id.zzy$zzbg$zza>] */
        /* JADX WARN: Type inference failed for: r0v1, types: [com.google.android.gms.internal.mlkit_language_id.zzce, com.google.android.gms.internal.mlkit_language_id.zzex<java.lang.Integer, com.google.android.gms.internal.mlkit_language_id.zzy$zzbg$zza>] */
        /* JADX WARN: Type inference failed for: r0v2, types: [com.google.android.gms.internal.mlkit_language_id.zzcd, com.google.android.gms.internal.mlkit_language_id.zzex<java.lang.Integer, com.google.android.gms.internal.mlkit_language_id.zzy$zzbg$zza>] */
        static {
            zzbg zzbgVar = new zzbg();
            zzl = zzbgVar;
            zzeo.zza((Class<zzbg>) zzbg.class, zzbgVar);
        }
    }

    /* JADX INFO: compiled from: com.google.mlkit:language-id@@16.1.1 */
    public static final class zzbi extends zzeo<zzbi, zza> implements zzgb {
        private static final zzbi zzf;
        private static volatile zzgj<zzbi> zzg;
        private int zzc;
        private String zzd = "";
        private String zze = "";

        private zzbi() {
        }

        /* JADX INFO: compiled from: com.google.mlkit:language-id@@16.1.1 */
        public static final class zza extends zzeo.zzb<zzbi, zza> implements zzgb {
            private zza() {
                super(zzbi.zzf);
            }

            /* synthetic */ zza(com.google.android.gms.internal.mlkit_language_id.zzx zzxVar) {
                this();
            }
        }

        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Type inference failed for: r1v13 */
        /* JADX WARN: Type inference failed for: r1v14, types: [com.google.android.gms.internal.mlkit_language_id.zzeo$zza, com.google.android.gms.internal.mlkit_language_id.zzgj<com.google.android.gms.internal.mlkit_language_id.zzy$zzbi>] */
        /* JADX WARN: Type inference failed for: r1v17 */
        /* JADX WARN: Type inference failed for: r1v18 */
        @Override // com.google.android.gms.internal.mlkit_language_id.zzeo
        protected final Object zza(int i, Object obj, Object obj2) {
            ?? r1;
            com.google.android.gms.internal.mlkit_language_id.zzx zzxVar = null;
            switch (com.google.android.gms.internal.mlkit_language_id.zzx.zza[i - 1]) {
                case 1:
                    return new zzbi();
                case 2:
                    return new zza(zzxVar);
                case 3:
                    return zza(zzf, "\u0001\u0002\u0000\u0001\u0001\u0002\u0002\u0000\u0000\u0000\u0001ဈ\u0000\u0002ဈ\u0001", new Object[]{"zzc", "zzd", "zze"});
                case 4:
                    return zzf;
                case 5:
                    zzgj<zzbi> zzgjVar = zzg;
                    Object obj3 = zzgjVar;
                    if (zzgjVar == null) {
                        synchronized (zzbi.class) {
                            zzgj<zzbi> zzgjVar2 = zzg;
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
            zzbi zzbiVar = new zzbi();
            zzf = zzbiVar;
            zzeo.zza((Class<zzbi>) zzbi.class, zzbiVar);
        }
    }

    /* JADX INFO: compiled from: com.google.mlkit:language-id@@16.1.1 */
    public static final class zzc extends zzeo<zzc, zza> implements zzgb {
        private static final zzc zzg;
        private static volatile zzgj<zzc> zzh;
        private int zzc;
        private zzb zzd;
        private int zze;
        private zzab zzf;

        /* JADX INFO: compiled from: com.google.mlkit:language-id@@16.1.1 */
        public static final class zzb extends zzeo<zzb, zza> implements zzgb {
            private static final zzex<Integer, zzao.zza> zzj = new com.google.android.gms.internal.mlkit_language_id.zzz();
            private static final zzex<Integer, zzao.zzb> zzl = new com.google.android.gms.internal.mlkit_language_id.zzaa();
            private static final zzb zzm;
            private static volatile zzgj<zzb> zzn;
            private int zzc;
            private int zzd;
            private boolean zze;
            private boolean zzf;
            private zzae zzg;
            private zzci.zza zzh;
            private zzeu zzi = zzk();
            private zzeu zzk = zzk();

            private zzb() {
            }

            /* JADX INFO: compiled from: com.google.mlkit:language-id@@16.1.1 */
            public static final class zza extends zzeo.zzb<zzb, zza> implements zzgb {
                private zza() {
                    super(zzb.zzm);
                }

                /* synthetic */ zza(com.google.android.gms.internal.mlkit_language_id.zzx zzxVar) {
                    this();
                }
            }

            /* JADX WARN: Multi-variable type inference failed */
            /* JADX WARN: Type inference failed for: r12v12 */
            /* JADX WARN: Type inference failed for: r12v13, types: [com.google.android.gms.internal.mlkit_language_id.zzeo$zza, com.google.android.gms.internal.mlkit_language_id.zzgj<com.google.android.gms.internal.mlkit_language_id.zzy$zzc$zzb>] */
            /* JADX WARN: Type inference failed for: r12v16 */
            /* JADX WARN: Type inference failed for: r12v17 */
            @Override // com.google.android.gms.internal.mlkit_language_id.zzeo
            protected final Object zza(int i, Object obj, Object obj2) {
                ?? r12;
                com.google.android.gms.internal.mlkit_language_id.zzx zzxVar = null;
                switch (com.google.android.gms.internal.mlkit_language_id.zzx.zza[i - 1]) {
                    case 1:
                        return new zzb();
                    case 2:
                        return new zza(zzxVar);
                    case 3:
                        return zza(zzm, "\u0001\u0007\u0000\u0001\u0001\u0007\u0007\u0000\u0002\u0000\u0001ဌ\u0000\u0002ဇ\u0001\u0003ဇ\u0002\u0004ဉ\u0003\u0005ဉ\u0004\u0006\u001e\u0007\u001e", new Object[]{"zzc", "zzd", com.google.android.gms.internal.mlkit_language_id.zzai.zzb(), "zze", "zzf", "zzg", "zzh", "zzi", zzao.zza.zzb(), "zzk", zzao.zzb.zzb()});
                    case 4:
                        return zzm;
                    case 5:
                        zzgj<zzb> zzgjVar = zzn;
                        Object obj3 = zzgjVar;
                        if (zzgjVar == null) {
                            synchronized (zzb.class) {
                                zzgj<zzb> zzgjVar2 = zzn;
                                r12 = zzgjVar2;
                                if (zzgjVar2 == null) {
                                    ?? zzaVar = new zzeo.zza(zzm);
                                    zzn = zzaVar;
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

            /* JADX WARN: Type inference failed for: r0v0, types: [com.google.android.gms.internal.mlkit_language_id.zzex<java.lang.Integer, com.google.android.gms.internal.mlkit_language_id.zzy$zzao$zza>, com.google.android.gms.internal.mlkit_language_id.zzz] */
            /* JADX WARN: Type inference failed for: r0v1, types: [com.google.android.gms.internal.mlkit_language_id.zzaa, com.google.android.gms.internal.mlkit_language_id.zzex<java.lang.Integer, com.google.android.gms.internal.mlkit_language_id.zzy$zzao$zzb>] */
            static {
                zzb zzbVar = new zzb();
                zzm = zzbVar;
                zzeo.zza((Class<zzb>) zzb.class, zzbVar);
            }
        }

        private zzc() {
        }

        /* JADX INFO: compiled from: com.google.mlkit:language-id@@16.1.1 */
        public static final class zza extends zzeo.zzb<zzc, zza> implements zzgb {
            private zza() {
                super(zzc.zzg);
            }

            /* synthetic */ zza(com.google.android.gms.internal.mlkit_language_id.zzx zzxVar) {
                this();
            }
        }

        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Type inference failed for: r2v13 */
        /* JADX WARN: Type inference failed for: r2v14, types: [com.google.android.gms.internal.mlkit_language_id.zzeo$zza, com.google.android.gms.internal.mlkit_language_id.zzgj<com.google.android.gms.internal.mlkit_language_id.zzy$zzc>] */
        /* JADX WARN: Type inference failed for: r2v17 */
        /* JADX WARN: Type inference failed for: r2v18 */
        @Override // com.google.android.gms.internal.mlkit_language_id.zzeo
        protected final Object zza(int i, Object obj, Object obj2) {
            ?? r2;
            com.google.android.gms.internal.mlkit_language_id.zzx zzxVar = null;
            switch (com.google.android.gms.internal.mlkit_language_id.zzx.zza[i - 1]) {
                case 1:
                    return new zzc();
                case 2:
                    return new zza(zzxVar);
                case 3:
                    return zza(zzg, "\u0001\u0003\u0000\u0001\u0001\u0003\u0003\u0000\u0000\u0000\u0001ဉ\u0000\u0002ဋ\u0001\u0003ဉ\u0002", new Object[]{"zzc", "zzd", "zze", "zzf"});
                case 4:
                    return zzg;
                case 5:
                    zzgj<zzc> zzgjVar = zzh;
                    Object obj3 = zzgjVar;
                    if (zzgjVar == null) {
                        synchronized (zzc.class) {
                            zzgj<zzc> zzgjVar2 = zzh;
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
            zzc zzcVar = new zzc();
            zzg = zzcVar;
            zzeo.zza((Class<zzc>) zzc.class, zzcVar);
        }
    }

    /* JADX INFO: compiled from: com.google.mlkit:language-id@@16.1.1 */
    public static final class zzd extends zzeo<zzd, zza> implements zzgb {
        private static final zzd zzg;
        private static volatile zzgj<zzd> zzh;
        private int zzc;
        private zzb zzd;
        private int zze;
        private zzab zzf;

        /* JADX INFO: compiled from: com.google.mlkit:language-id@@16.1.1 */
        public static final class zzb extends zzeo<zzb, zza> implements zzgb {
            private static final zzb zzj;
            private static volatile zzgj<zzb> zzk;
            private int zzc;
            private int zzd;
            private boolean zze;
            private zzae zzf;
            private zzac zzg;
            private int zzh;
            private int zzi;

            private zzb() {
            }

            /* JADX INFO: compiled from: com.google.mlkit:language-id@@16.1.1 */
            public static final class zza extends zzeo.zzb<zzb, zza> implements zzgb {
                private zza() {
                    super(zzb.zzj);
                }

                /* synthetic */ zza(com.google.android.gms.internal.mlkit_language_id.zzx zzxVar) {
                    this();
                }
            }

            /* JADX WARN: Multi-variable type inference failed */
            /* JADX WARN: Type inference failed for: r9v12 */
            /* JADX WARN: Type inference failed for: r9v13, types: [com.google.android.gms.internal.mlkit_language_id.zzeo$zza, com.google.android.gms.internal.mlkit_language_id.zzgj<com.google.android.gms.internal.mlkit_language_id.zzy$zzd$zzb>] */
            /* JADX WARN: Type inference failed for: r9v16 */
            /* JADX WARN: Type inference failed for: r9v17 */
            @Override // com.google.android.gms.internal.mlkit_language_id.zzeo
            protected final Object zza(int i, Object obj, Object obj2) {
                ?? r9;
                com.google.android.gms.internal.mlkit_language_id.zzx zzxVar = null;
                switch (com.google.android.gms.internal.mlkit_language_id.zzx.zza[i - 1]) {
                    case 1:
                        return new zzb();
                    case 2:
                        return new zza(zzxVar);
                    case 3:
                        return zza(zzj, "\u0001\u0006\u0000\u0001\u0001\u0006\u0006\u0000\u0000\u0000\u0001ဌ\u0000\u0002ဇ\u0001\u0003ဉ\u0002\u0004ဉ\u0003\u0005ဋ\u0004\u0006ဋ\u0005", new Object[]{"zzc", "zzd", com.google.android.gms.internal.mlkit_language_id.zzai.zzb(), "zze", "zzf", "zzg", "zzh", "zzi"});
                    case 4:
                        return zzj;
                    case 5:
                        zzgj<zzb> zzgjVar = zzk;
                        Object obj3 = zzgjVar;
                        if (zzgjVar == null) {
                            synchronized (zzb.class) {
                                zzgj<zzb> zzgjVar2 = zzk;
                                r9 = zzgjVar2;
                                if (zzgjVar2 == null) {
                                    ?? zzaVar = new zzeo.zza(zzj);
                                    zzk = zzaVar;
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
                zzb zzbVar = new zzb();
                zzj = zzbVar;
                zzeo.zza((Class<zzb>) zzb.class, zzbVar);
            }
        }

        private zzd() {
        }

        /* JADX INFO: compiled from: com.google.mlkit:language-id@@16.1.1 */
        public static final class zza extends zzeo.zzb<zzd, zza> implements zzgb {
            private zza() {
                super(zzd.zzg);
            }

            /* synthetic */ zza(com.google.android.gms.internal.mlkit_language_id.zzx zzxVar) {
                this();
            }
        }

        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Type inference failed for: r2v13 */
        /* JADX WARN: Type inference failed for: r2v14, types: [com.google.android.gms.internal.mlkit_language_id.zzeo$zza, com.google.android.gms.internal.mlkit_language_id.zzgj<com.google.android.gms.internal.mlkit_language_id.zzy$zzd>] */
        /* JADX WARN: Type inference failed for: r2v17 */
        /* JADX WARN: Type inference failed for: r2v18 */
        @Override // com.google.android.gms.internal.mlkit_language_id.zzeo
        protected final Object zza(int i, Object obj, Object obj2) {
            ?? r2;
            com.google.android.gms.internal.mlkit_language_id.zzx zzxVar = null;
            switch (com.google.android.gms.internal.mlkit_language_id.zzx.zza[i - 1]) {
                case 1:
                    return new zzd();
                case 2:
                    return new zza(zzxVar);
                case 3:
                    return zza(zzg, "\u0001\u0003\u0000\u0001\u0001\u0003\u0003\u0000\u0000\u0000\u0001ဉ\u0000\u0002ဋ\u0001\u0003ဉ\u0002", new Object[]{"zzc", "zzd", "zze", "zzf"});
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
    public static final class zze extends zzeo<zze, zza> implements zzgb {
        private static final zze zzg;
        private static volatile zzgj<zze> zzh;
        private int zzc;
        private zzb zzd;
        private int zze;
        private zzab zzf;

        /* JADX INFO: compiled from: com.google.mlkit:language-id@@16.1.1 */
        public static final class zzb extends zzeo<zzb, zza> implements zzgb {
            private static final zzb zzh;
            private static volatile zzgj<zzb> zzi;
            private int zzc;
            private int zzd;
            private boolean zze;
            private zzae zzf;
            private zzat zzg;

            private zzb() {
            }

            /* JADX INFO: compiled from: com.google.mlkit:language-id@@16.1.1 */
            public static final class zza extends zzeo.zzb<zzb, zza> implements zzgb {
                private zza() {
                    super(zzb.zzh);
                }

                /* synthetic */ zza(com.google.android.gms.internal.mlkit_language_id.zzx zzxVar) {
                    this();
                }
            }

            /* JADX WARN: Multi-variable type inference failed */
            /* JADX WARN: Type inference failed for: r7v12 */
            /* JADX WARN: Type inference failed for: r7v13, types: [com.google.android.gms.internal.mlkit_language_id.zzeo$zza, com.google.android.gms.internal.mlkit_language_id.zzgj<com.google.android.gms.internal.mlkit_language_id.zzy$zze$zzb>] */
            /* JADX WARN: Type inference failed for: r7v16 */
            /* JADX WARN: Type inference failed for: r7v17 */
            @Override // com.google.android.gms.internal.mlkit_language_id.zzeo
            protected final Object zza(int i, Object obj, Object obj2) {
                ?? r7;
                com.google.android.gms.internal.mlkit_language_id.zzx zzxVar = null;
                switch (com.google.android.gms.internal.mlkit_language_id.zzx.zza[i - 1]) {
                    case 1:
                        return new zzb();
                    case 2:
                        return new zza(zzxVar);
                    case 3:
                        return zza(zzh, "\u0001\u0004\u0000\u0001\u0001\u0004\u0004\u0000\u0000\u0000\u0001ဌ\u0000\u0002ဇ\u0001\u0003ဉ\u0002\u0004ဉ\u0003", new Object[]{"zzc", "zzd", com.google.android.gms.internal.mlkit_language_id.zzai.zzb(), "zze", "zzf", "zzg"});
                    case 4:
                        return zzh;
                    case 5:
                        zzgj<zzb> zzgjVar = zzi;
                        Object obj3 = zzgjVar;
                        if (zzgjVar == null) {
                            synchronized (zzb.class) {
                                zzgj<zzb> zzgjVar2 = zzi;
                                r7 = zzgjVar2;
                                if (zzgjVar2 == null) {
                                    ?? zzaVar = new zzeo.zza(zzh);
                                    zzi = zzaVar;
                                    r7 = zzaVar;
                                }
                                break;
                            }
                            obj3 = r7;
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

        private zze() {
        }

        /* JADX INFO: compiled from: com.google.mlkit:language-id@@16.1.1 */
        public static final class zza extends zzeo.zzb<zze, zza> implements zzgb {
            private zza() {
                super(zze.zzg);
            }

            /* synthetic */ zza(com.google.android.gms.internal.mlkit_language_id.zzx zzxVar) {
                this();
            }
        }

        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Type inference failed for: r2v13 */
        /* JADX WARN: Type inference failed for: r2v14, types: [com.google.android.gms.internal.mlkit_language_id.zzeo$zza, com.google.android.gms.internal.mlkit_language_id.zzgj<com.google.android.gms.internal.mlkit_language_id.zzy$zze>] */
        /* JADX WARN: Type inference failed for: r2v17 */
        /* JADX WARN: Type inference failed for: r2v18 */
        @Override // com.google.android.gms.internal.mlkit_language_id.zzeo
        protected final Object zza(int i, Object obj, Object obj2) {
            ?? r2;
            com.google.android.gms.internal.mlkit_language_id.zzx zzxVar = null;
            switch (com.google.android.gms.internal.mlkit_language_id.zzx.zza[i - 1]) {
                case 1:
                    return new zze();
                case 2:
                    return new zza(zzxVar);
                case 3:
                    return zza(zzg, "\u0001\u0003\u0000\u0001\u0001\u0003\u0003\u0000\u0000\u0000\u0001ဉ\u0000\u0002ဋ\u0001\u0003ဉ\u0002", new Object[]{"zzc", "zzd", "zze", "zzf"});
                case 4:
                    return zzg;
                case 5:
                    zzgj<zze> zzgjVar = zzh;
                    Object obj3 = zzgjVar;
                    if (zzgjVar == null) {
                        synchronized (zze.class) {
                            zzgj<zze> zzgjVar2 = zzh;
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
            zze zzeVar = new zze();
            zzg = zzeVar;
            zzeo.zza((Class<zze>) zze.class, zzeVar);
        }
    }

    /* JADX INFO: compiled from: com.google.mlkit:language-id@@16.1.1 */
    public static final class zzf extends zzeo<zzf, zza> implements zzgb {
        private static final zzf zzg;
        private static volatile zzgj<zzf> zzh;
        private int zzc;
        private zzb zzd;
        private int zze;
        private zzab zzf;

        /* JADX INFO: compiled from: com.google.mlkit:language-id@@16.1.1 */
        public static final class zzb extends zzeo<zzb, zza> implements zzgb {
            private static final zzb zzi;
            private static volatile zzgj<zzb> zzj;
            private int zzc;
            private int zzd;
            private boolean zze;
            private boolean zzf;
            private zzae zzg;
            private zzaw zzh;

            private zzb() {
            }

            /* JADX INFO: compiled from: com.google.mlkit:language-id@@16.1.1 */
            public static final class zza extends zzeo.zzb<zzb, zza> implements zzgb {
                private zza() {
                    super(zzb.zzi);
                }

                /* synthetic */ zza(com.google.android.gms.internal.mlkit_language_id.zzx zzxVar) {
                    this();
                }
            }

            /* JADX WARN: Multi-variable type inference failed */
            /* JADX WARN: Type inference failed for: r8v12 */
            /* JADX WARN: Type inference failed for: r8v13, types: [com.google.android.gms.internal.mlkit_language_id.zzeo$zza, com.google.android.gms.internal.mlkit_language_id.zzgj<com.google.android.gms.internal.mlkit_language_id.zzy$zzf$zzb>] */
            /* JADX WARN: Type inference failed for: r8v16 */
            /* JADX WARN: Type inference failed for: r8v17 */
            @Override // com.google.android.gms.internal.mlkit_language_id.zzeo
            protected final Object zza(int i, Object obj, Object obj2) {
                ?? r8;
                com.google.android.gms.internal.mlkit_language_id.zzx zzxVar = null;
                switch (com.google.android.gms.internal.mlkit_language_id.zzx.zza[i - 1]) {
                    case 1:
                        return new zzb();
                    case 2:
                        return new zza(zzxVar);
                    case 3:
                        return zza(zzi, "\u0001\u0005\u0000\u0001\u0001\u0005\u0005\u0000\u0000\u0000\u0001ဌ\u0000\u0002ဇ\u0001\u0003ဇ\u0002\u0004ဉ\u0003\u0005ဉ\u0004", new Object[]{"zzc", "zzd", com.google.android.gms.internal.mlkit_language_id.zzai.zzb(), "zze", "zzf", "zzg", "zzh"});
                    case 4:
                        return zzi;
                    case 5:
                        zzgj<zzb> zzgjVar = zzj;
                        Object obj3 = zzgjVar;
                        if (zzgjVar == null) {
                            synchronized (zzb.class) {
                                zzgj<zzb> zzgjVar2 = zzj;
                                r8 = zzgjVar2;
                                if (zzgjVar2 == null) {
                                    ?? zzaVar = new zzeo.zza(zzi);
                                    zzj = zzaVar;
                                    r8 = zzaVar;
                                }
                                break;
                            }
                            obj3 = r8;
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
                zzi = zzbVar;
                zzeo.zza((Class<zzb>) zzb.class, zzbVar);
            }
        }

        private zzf() {
        }

        /* JADX INFO: compiled from: com.google.mlkit:language-id@@16.1.1 */
        public static final class zza extends zzeo.zzb<zzf, zza> implements zzgb {
            private zza() {
                super(zzf.zzg);
            }

            /* synthetic */ zza(com.google.android.gms.internal.mlkit_language_id.zzx zzxVar) {
                this();
            }
        }

        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Type inference failed for: r2v13 */
        /* JADX WARN: Type inference failed for: r2v14, types: [com.google.android.gms.internal.mlkit_language_id.zzeo$zza, com.google.android.gms.internal.mlkit_language_id.zzgj<com.google.android.gms.internal.mlkit_language_id.zzy$zzf>] */
        /* JADX WARN: Type inference failed for: r2v17 */
        /* JADX WARN: Type inference failed for: r2v18 */
        @Override // com.google.android.gms.internal.mlkit_language_id.zzeo
        protected final Object zza(int i, Object obj, Object obj2) {
            ?? r2;
            com.google.android.gms.internal.mlkit_language_id.zzx zzxVar = null;
            switch (com.google.android.gms.internal.mlkit_language_id.zzx.zza[i - 1]) {
                case 1:
                    return new zzf();
                case 2:
                    return new zza(zzxVar);
                case 3:
                    return zza(zzg, "\u0001\u0003\u0000\u0001\u0001\u0003\u0003\u0000\u0000\u0000\u0001ဉ\u0000\u0002ဋ\u0001\u0003ဉ\u0002", new Object[]{"zzc", "zzd", "zze", "zzf"});
                case 4:
                    return zzg;
                case 5:
                    zzgj<zzf> zzgjVar = zzh;
                    Object obj3 = zzgjVar;
                    if (zzgjVar == null) {
                        synchronized (zzf.class) {
                            zzgj<zzf> zzgjVar2 = zzh;
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
            zzf zzfVar = new zzf();
            zzg = zzfVar;
            zzeo.zza((Class<zzf>) zzf.class, zzfVar);
        }
    }

    /* JADX INFO: compiled from: com.google.mlkit:language-id@@16.1.1 */
    public static final class zzg extends zzeo<zzg, zza> implements zzgb {
        private static final zzg zzg;
        private static volatile zzgj<zzg> zzh;
        private int zzc;
        private zzb zzd;
        private int zze;
        private zzab zzf;

        /* JADX INFO: compiled from: com.google.mlkit:language-id@@16.1.1 */
        public static final class zzb extends zzeo<zzb, zza> implements zzgb {
            private static final zzb zzh;
            private static volatile zzgj<zzb> zzi;
            private int zzc;
            private int zzd;
            private boolean zze;
            private zzae zzf;
            private zzba zzg;

            private zzb() {
            }

            /* JADX INFO: compiled from: com.google.mlkit:language-id@@16.1.1 */
            public static final class zza extends zzeo.zzb<zzb, zza> implements zzgb {
                private zza() {
                    super(zzb.zzh);
                }

                /* synthetic */ zza(com.google.android.gms.internal.mlkit_language_id.zzx zzxVar) {
                    this();
                }
            }

            /* JADX WARN: Multi-variable type inference failed */
            /* JADX WARN: Type inference failed for: r7v12 */
            /* JADX WARN: Type inference failed for: r7v13, types: [com.google.android.gms.internal.mlkit_language_id.zzeo$zza, com.google.android.gms.internal.mlkit_language_id.zzgj<com.google.android.gms.internal.mlkit_language_id.zzy$zzg$zzb>] */
            /* JADX WARN: Type inference failed for: r7v16 */
            /* JADX WARN: Type inference failed for: r7v17 */
            @Override // com.google.android.gms.internal.mlkit_language_id.zzeo
            protected final Object zza(int i, Object obj, Object obj2) {
                ?? r7;
                com.google.android.gms.internal.mlkit_language_id.zzx zzxVar = null;
                switch (com.google.android.gms.internal.mlkit_language_id.zzx.zza[i - 1]) {
                    case 1:
                        return new zzb();
                    case 2:
                        return new zza(zzxVar);
                    case 3:
                        return zza(zzh, "\u0001\u0004\u0000\u0001\u0001\u0004\u0004\u0000\u0000\u0000\u0001ဌ\u0000\u0002ဇ\u0001\u0003ဉ\u0002\u0004ဉ\u0003", new Object[]{"zzc", "zzd", com.google.android.gms.internal.mlkit_language_id.zzai.zzb(), "zze", "zzf", "zzg"});
                    case 4:
                        return zzh;
                    case 5:
                        zzgj<zzb> zzgjVar = zzi;
                        Object obj3 = zzgjVar;
                        if (zzgjVar == null) {
                            synchronized (zzb.class) {
                                zzgj<zzb> zzgjVar2 = zzi;
                                r7 = zzgjVar2;
                                if (zzgjVar2 == null) {
                                    ?? zzaVar = new zzeo.zza(zzh);
                                    zzi = zzaVar;
                                    r7 = zzaVar;
                                }
                                break;
                            }
                            obj3 = r7;
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

        private zzg() {
        }

        /* JADX INFO: compiled from: com.google.mlkit:language-id@@16.1.1 */
        public static final class zza extends zzeo.zzb<zzg, zza> implements zzgb {
            private zza() {
                super(zzg.zzg);
            }

            /* synthetic */ zza(com.google.android.gms.internal.mlkit_language_id.zzx zzxVar) {
                this();
            }
        }

        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Type inference failed for: r2v13 */
        /* JADX WARN: Type inference failed for: r2v14, types: [com.google.android.gms.internal.mlkit_language_id.zzeo$zza, com.google.android.gms.internal.mlkit_language_id.zzgj<com.google.android.gms.internal.mlkit_language_id.zzy$zzg>] */
        /* JADX WARN: Type inference failed for: r2v17 */
        /* JADX WARN: Type inference failed for: r2v18 */
        @Override // com.google.android.gms.internal.mlkit_language_id.zzeo
        protected final Object zza(int i, Object obj, Object obj2) {
            ?? r2;
            com.google.android.gms.internal.mlkit_language_id.zzx zzxVar = null;
            switch (com.google.android.gms.internal.mlkit_language_id.zzx.zza[i - 1]) {
                case 1:
                    return new zzg();
                case 2:
                    return new zza(zzxVar);
                case 3:
                    return zza(zzg, "\u0001\u0003\u0000\u0001\u0001\u0003\u0003\u0000\u0000\u0000\u0001ဉ\u0000\u0002ဋ\u0001\u0003ဉ\u0002", new Object[]{"zzc", "zzd", "zze", "zzf"});
                case 4:
                    return zzg;
                case 5:
                    zzgj<zzg> zzgjVar = zzh;
                    Object obj3 = zzgjVar;
                    if (zzgjVar == null) {
                        synchronized (zzg.class) {
                            zzgj<zzg> zzgjVar2 = zzh;
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
            zzg zzgVar = new zzg();
            zzg = zzgVar;
            zzeo.zza((Class<zzg>) zzg.class, zzgVar);
        }
    }

    /* JADX INFO: compiled from: com.google.mlkit:language-id@@16.1.1 */
    public static final class zzh extends zzeo<zzh, zza> implements zzgb {
        private static final zzh zzg;
        private static volatile zzgj<zzh> zzh;
        private int zzc;
        private zzb zzd;
        private int zze;
        private zzab zzf;

        /* JADX INFO: compiled from: com.google.mlkit:language-id@@16.1.1 */
        public static final class zzb extends zzeo<zzb, zza> implements zzgb {
            private static final zzb zzg;
            private static volatile zzgj<zzb> zzh;
            private int zzc;
            private int zzd;
            private boolean zze;
            private zzae zzf;

            private zzb() {
            }

            /* JADX INFO: compiled from: com.google.mlkit:language-id@@16.1.1 */
            public static final class zza extends zzeo.zzb<zzb, zza> implements zzgb {
                private zza() {
                    super(zzb.zzg);
                }

                /* synthetic */ zza(com.google.android.gms.internal.mlkit_language_id.zzx zzxVar) {
                    this();
                }
            }

            /* JADX WARN: Multi-variable type inference failed */
            /* JADX WARN: Type inference failed for: r3v13 */
            /* JADX WARN: Type inference failed for: r3v14, types: [com.google.android.gms.internal.mlkit_language_id.zzeo$zza, com.google.android.gms.internal.mlkit_language_id.zzgj<com.google.android.gms.internal.mlkit_language_id.zzy$zzh$zzb>] */
            /* JADX WARN: Type inference failed for: r3v17 */
            /* JADX WARN: Type inference failed for: r3v18 */
            @Override // com.google.android.gms.internal.mlkit_language_id.zzeo
            protected final Object zza(int i, Object obj, Object obj2) {
                ?? r3;
                com.google.android.gms.internal.mlkit_language_id.zzx zzxVar = null;
                switch (com.google.android.gms.internal.mlkit_language_id.zzx.zza[i - 1]) {
                    case 1:
                        return new zzb();
                    case 2:
                        return new zza(zzxVar);
                    case 3:
                        return zza(zzg, "\u0001\u0003\u0000\u0001\u0001\u0003\u0003\u0000\u0000\u0000\u0001ဌ\u0000\u0002ဇ\u0001\u0003ဉ\u0002", new Object[]{"zzc", "zzd", com.google.android.gms.internal.mlkit_language_id.zzai.zzb(), "zze", "zzf"});
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

        private zzh() {
        }

        /* JADX INFO: compiled from: com.google.mlkit:language-id@@16.1.1 */
        public static final class zza extends zzeo.zzb<zzh, zza> implements zzgb {
            private zza() {
                super(zzh.zzg);
            }

            /* synthetic */ zza(com.google.android.gms.internal.mlkit_language_id.zzx zzxVar) {
                this();
            }
        }

        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Type inference failed for: r2v13 */
        /* JADX WARN: Type inference failed for: r2v14, types: [com.google.android.gms.internal.mlkit_language_id.zzeo$zza, com.google.android.gms.internal.mlkit_language_id.zzgj<com.google.android.gms.internal.mlkit_language_id.zzy$zzh>] */
        /* JADX WARN: Type inference failed for: r2v17 */
        /* JADX WARN: Type inference failed for: r2v18 */
        @Override // com.google.android.gms.internal.mlkit_language_id.zzeo
        protected final Object zza(int i, Object obj, Object obj2) {
            ?? r2;
            com.google.android.gms.internal.mlkit_language_id.zzx zzxVar = null;
            switch (com.google.android.gms.internal.mlkit_language_id.zzx.zza[i - 1]) {
                case 1:
                    return new zzh();
                case 2:
                    return new zza(zzxVar);
                case 3:
                    return zza(zzg, "\u0001\u0003\u0000\u0001\u0001\u0003\u0003\u0000\u0000\u0000\u0001ဉ\u0000\u0002ဋ\u0001\u0003ဉ\u0002", new Object[]{"zzc", "zzd", "zze", "zzf"});
                case 4:
                    return zzg;
                case 5:
                    zzgj<zzh> zzgjVar = zzh;
                    Object obj3 = zzgjVar;
                    if (zzgjVar == null) {
                        synchronized (zzh.class) {
                            zzgj<zzh> zzgjVar2 = zzh;
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
            zzh zzhVar = new zzh();
            zzg = zzhVar;
            zzeo.zza((Class<zzh>) zzh.class, zzhVar);
        }
    }

    /* JADX INFO: compiled from: com.google.mlkit:language-id@@16.1.1 */
    public static final class zzi extends zzeo<zzi, zza> implements zzgb {
        private static final zzi zzg;
        private static volatile zzgj<zzi> zzh;
        private int zzc;
        private zzb zzd;
        private int zze;
        private zzab zzf;

        /* JADX INFO: compiled from: com.google.mlkit:language-id@@16.1.1 */
        public static final class zzb extends zzeo<zzb, zza> implements zzgb {
            private static final zzb zzh;
            private static volatile zzgj<zzb> zzi;
            private int zzc;
            private int zzd;
            private boolean zze;
            private boolean zzf;
            private zzae zzg;

            private zzb() {
            }

            /* JADX INFO: compiled from: com.google.mlkit:language-id@@16.1.1 */
            public static final class zza extends zzeo.zzb<zzb, zza> implements zzgb {
                private zza() {
                    super(zzb.zzh);
                }

                /* synthetic */ zza(com.google.android.gms.internal.mlkit_language_id.zzx zzxVar) {
                    this();
                }
            }

            /* JADX WARN: Multi-variable type inference failed */
            /* JADX WARN: Type inference failed for: r7v12 */
            /* JADX WARN: Type inference failed for: r7v13, types: [com.google.android.gms.internal.mlkit_language_id.zzeo$zza, com.google.android.gms.internal.mlkit_language_id.zzgj<com.google.android.gms.internal.mlkit_language_id.zzy$zzi$zzb>] */
            /* JADX WARN: Type inference failed for: r7v16 */
            /* JADX WARN: Type inference failed for: r7v17 */
            @Override // com.google.android.gms.internal.mlkit_language_id.zzeo
            protected final Object zza(int i, Object obj, Object obj2) {
                ?? r7;
                com.google.android.gms.internal.mlkit_language_id.zzx zzxVar = null;
                switch (com.google.android.gms.internal.mlkit_language_id.zzx.zza[i - 1]) {
                    case 1:
                        return new zzb();
                    case 2:
                        return new zza(zzxVar);
                    case 3:
                        return zza(zzh, "\u0001\u0004\u0000\u0001\u0001\u0004\u0004\u0000\u0000\u0000\u0001ဌ\u0000\u0002ဇ\u0001\u0003ဇ\u0002\u0004ဉ\u0003", new Object[]{"zzc", "zzd", com.google.android.gms.internal.mlkit_language_id.zzai.zzb(), "zze", "zzf", "zzg"});
                    case 4:
                        return zzh;
                    case 5:
                        zzgj<zzb> zzgjVar = zzi;
                        Object obj3 = zzgjVar;
                        if (zzgjVar == null) {
                            synchronized (zzb.class) {
                                zzgj<zzb> zzgjVar2 = zzi;
                                r7 = zzgjVar2;
                                if (zzgjVar2 == null) {
                                    ?? zzaVar = new zzeo.zza(zzh);
                                    zzi = zzaVar;
                                    r7 = zzaVar;
                                }
                                break;
                            }
                            obj3 = r7;
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
                super(zzi.zzg);
            }

            /* synthetic */ zza(com.google.android.gms.internal.mlkit_language_id.zzx zzxVar) {
                this();
            }
        }

        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Type inference failed for: r2v13 */
        /* JADX WARN: Type inference failed for: r2v14, types: [com.google.android.gms.internal.mlkit_language_id.zzeo$zza, com.google.android.gms.internal.mlkit_language_id.zzgj<com.google.android.gms.internal.mlkit_language_id.zzy$zzi>] */
        /* JADX WARN: Type inference failed for: r2v17 */
        /* JADX WARN: Type inference failed for: r2v18 */
        @Override // com.google.android.gms.internal.mlkit_language_id.zzeo
        protected final Object zza(int i, Object obj, Object obj2) {
            ?? r2;
            com.google.android.gms.internal.mlkit_language_id.zzx zzxVar = null;
            switch (com.google.android.gms.internal.mlkit_language_id.zzx.zza[i - 1]) {
                case 1:
                    return new zzi();
                case 2:
                    return new zza(zzxVar);
                case 3:
                    return zza(zzg, "\u0001\u0003\u0000\u0001\u0001\u0003\u0003\u0000\u0000\u0000\u0001ဉ\u0000\u0002ဋ\u0001\u0003ဉ\u0002", new Object[]{"zzc", "zzd", "zze", "zzf"});
                case 4:
                    return zzg;
                case 5:
                    zzgj<zzi> zzgjVar = zzh;
                    Object obj3 = zzgjVar;
                    if (zzgjVar == null) {
                        synchronized (zzi.class) {
                            zzgj<zzi> zzgjVar2 = zzh;
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
            zzi zziVar = new zzi();
            zzg = zziVar;
            zzeo.zza((Class<zzi>) zzi.class, zziVar);
        }
    }

    /* JADX INFO: compiled from: com.google.mlkit:language-id@@16.1.1 */
    public static final class zzj extends zzeo<zzj, zza> implements zzgb {
        private static final zzj zze;
        private static volatile zzgj<zzj> zzf;
        private int zzc;
        private int zzd;

        private zzj() {
        }

        /* JADX INFO: compiled from: com.google.mlkit:language-id@@16.1.1 */
        public static final class zza extends zzeo.zzb<zzj, zza> implements zzgb {
            private zza() {
                super(zzj.zze);
            }

            /* synthetic */ zza(com.google.android.gms.internal.mlkit_language_id.zzx zzxVar) {
                this();
            }
        }

        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Type inference failed for: r1v13 */
        /* JADX WARN: Type inference failed for: r1v14, types: [com.google.android.gms.internal.mlkit_language_id.zzeo$zza, com.google.android.gms.internal.mlkit_language_id.zzgj<com.google.android.gms.internal.mlkit_language_id.zzy$zzj>] */
        /* JADX WARN: Type inference failed for: r1v17 */
        /* JADX WARN: Type inference failed for: r1v18 */
        @Override // com.google.android.gms.internal.mlkit_language_id.zzeo
        protected final Object zza(int i, Object obj, Object obj2) {
            ?? r1;
            com.google.android.gms.internal.mlkit_language_id.zzx zzxVar = null;
            switch (com.google.android.gms.internal.mlkit_language_id.zzx.zza[i - 1]) {
                case 1:
                    return new zzj();
                case 2:
                    return new zza(zzxVar);
                case 3:
                    return zza(zze, "\u0001\u0001\u0000\u0001\u0001\u0001\u0001\u0000\u0000\u0000\u0001ဌ\u0000", new Object[]{"zzc", "zzd", com.google.android.gms.internal.mlkit_language_id.zzai.zzb()});
                case 4:
                    return zze;
                case 5:
                    zzgj<zzj> zzgjVar = zzf;
                    Object obj3 = zzgjVar;
                    if (zzgjVar == null) {
                        synchronized (zzj.class) {
                            zzgj<zzj> zzgjVar2 = zzf;
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
            zzj zzjVar = new zzj();
            zze = zzjVar;
            zzeo.zza((Class<zzj>) zzj.class, zzjVar);
        }
    }

    /* JADX INFO: compiled from: com.google.mlkit:language-id@@16.1.1 */
    public static final class zzk extends zzeo<zzk, zza> implements zzgb {
        private static final zzk zzi;
        private static volatile zzgj<zzk> zzj;
        private int zzc;
        private zzaf zzd;
        private zzam zze;
        private long zzf;
        private float zzg;
        private zzae zzh;

        private zzk() {
        }

        /* JADX INFO: compiled from: com.google.mlkit:language-id@@16.1.1 */
        public static final class zza extends zzeo.zzb<zzk, zza> implements zzgb {
            private zza() {
                super(zzk.zzi);
            }

            /* synthetic */ zza(com.google.android.gms.internal.mlkit_language_id.zzx zzxVar) {
                this();
            }
        }

        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Type inference failed for: r7v12 */
        /* JADX WARN: Type inference failed for: r7v13, types: [com.google.android.gms.internal.mlkit_language_id.zzeo$zza, com.google.android.gms.internal.mlkit_language_id.zzgj<com.google.android.gms.internal.mlkit_language_id.zzy$zzk>] */
        /* JADX WARN: Type inference failed for: r7v16 */
        /* JADX WARN: Type inference failed for: r7v17 */
        @Override // com.google.android.gms.internal.mlkit_language_id.zzeo
        protected final Object zza(int i, Object obj, Object obj2) {
            ?? r7;
            com.google.android.gms.internal.mlkit_language_id.zzx zzxVar = null;
            switch (com.google.android.gms.internal.mlkit_language_id.zzx.zza[i - 1]) {
                case 1:
                    return new zzk();
                case 2:
                    return new zza(zzxVar);
                case 3:
                    return zza(zzi, "\u0001\u0005\u0000\u0001\u0001\u0005\u0005\u0000\u0000\u0000\u0001ဉ\u0000\u0002ဉ\u0001\u0003ဃ\u0002\u0004ခ\u0003\u0005ဉ\u0004", new Object[]{"zzc", "zzd", "zze", "zzf", "zzg", "zzh"});
                case 4:
                    return zzi;
                case 5:
                    zzgj<zzk> zzgjVar = zzj;
                    Object obj3 = zzgjVar;
                    if (zzgjVar == null) {
                        synchronized (zzk.class) {
                            zzgj<zzk> zzgjVar2 = zzj;
                            r7 = zzgjVar2;
                            if (zzgjVar2 == null) {
                                ?? zzaVar = new zzeo.zza(zzi);
                                zzj = zzaVar;
                                r7 = zzaVar;
                            }
                            break;
                        }
                        obj3 = r7;
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
            zzi = zzkVar;
            zzeo.zza((Class<zzk>) zzk.class, zzkVar);
        }
    }

    /* JADX INFO: compiled from: com.google.mlkit:language-id@@16.1.1 */
    public static final class zzl extends zzeo<zzl, zza> implements zzgb {
        private static final zzex<Integer, com.google.android.gms.internal.mlkit_language_id.zzai> zzg = new com.google.android.gms.internal.mlkit_language_id.zzab();
        private static final zzl zzi;
        private static volatile zzgj<zzl> zzj;
        private int zzc;
        private zzam zzd;
        private zzam zze;
        private zzeu zzf = zzk();
        private long zzh;

        private zzl() {
        }

        /* JADX INFO: compiled from: com.google.mlkit:language-id@@16.1.1 */
        public static final class zza extends zzeo.zzb<zzl, zza> implements zzgb {
            private zza() {
                super(zzl.zzi);
            }

            /* synthetic */ zza(com.google.android.gms.internal.mlkit_language_id.zzx zzxVar) {
                this();
            }
        }

        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Type inference failed for: r7v12 */
        /* JADX WARN: Type inference failed for: r7v13, types: [com.google.android.gms.internal.mlkit_language_id.zzeo$zza, com.google.android.gms.internal.mlkit_language_id.zzgj<com.google.android.gms.internal.mlkit_language_id.zzy$zzl>] */
        /* JADX WARN: Type inference failed for: r7v16 */
        /* JADX WARN: Type inference failed for: r7v17 */
        @Override // com.google.android.gms.internal.mlkit_language_id.zzeo
        protected final Object zza(int i, Object obj, Object obj2) {
            ?? r7;
            com.google.android.gms.internal.mlkit_language_id.zzx zzxVar = null;
            switch (com.google.android.gms.internal.mlkit_language_id.zzx.zza[i - 1]) {
                case 1:
                    return new zzl();
                case 2:
                    return new zza(zzxVar);
                case 3:
                    return zza(zzi, "\u0001\u0004\u0000\u0001\u0001\u0004\u0004\u0000\u0001\u0000\u0001ဉ\u0000\u0002ဉ\u0001\u0003\u001e\u0004ဃ\u0002", new Object[]{"zzc", "zzd", "zze", "zzf", com.google.android.gms.internal.mlkit_language_id.zzai.zzb(), "zzh"});
                case 4:
                    return zzi;
                case 5:
                    zzgj<zzl> zzgjVar = zzj;
                    Object obj3 = zzgjVar;
                    if (zzgjVar == null) {
                        synchronized (zzl.class) {
                            zzgj<zzl> zzgjVar2 = zzj;
                            r7 = zzgjVar2;
                            if (zzgjVar2 == null) {
                                ?? zzaVar = new zzeo.zza(zzi);
                                zzj = zzaVar;
                                r7 = zzaVar;
                            }
                            break;
                        }
                        obj3 = r7;
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

        /* JADX WARN: Type inference failed for: r0v0, types: [com.google.android.gms.internal.mlkit_language_id.zzab, com.google.android.gms.internal.mlkit_language_id.zzex<java.lang.Integer, com.google.android.gms.internal.mlkit_language_id.zzai>] */
        static {
            zzl zzlVar = new zzl();
            zzi = zzlVar;
            zzeo.zza((Class<zzl>) zzl.class, zzlVar);
        }
    }

    /* JADX INFO: compiled from: com.google.mlkit:language-id@@16.1.1 */
    public static final class zzm extends zzeo<zzm, zza> implements zzgb {
        private static final zzm zzg;
        private static volatile zzgj<zzm> zzh;
        private int zzc;
        private zzaf zzd;
        private zzn zze;
        private zzae zzf;

        private zzm() {
        }

        /* JADX INFO: compiled from: com.google.mlkit:language-id@@16.1.1 */
        public static final class zza extends zzeo.zzb<zzm, zza> implements zzgb {
            private zza() {
                super(zzm.zzg);
            }

            /* synthetic */ zza(com.google.android.gms.internal.mlkit_language_id.zzx zzxVar) {
                this();
            }
        }

        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Type inference failed for: r2v13 */
        /* JADX WARN: Type inference failed for: r2v14, types: [com.google.android.gms.internal.mlkit_language_id.zzeo$zza, com.google.android.gms.internal.mlkit_language_id.zzgj<com.google.android.gms.internal.mlkit_language_id.zzy$zzm>] */
        /* JADX WARN: Type inference failed for: r2v17 */
        /* JADX WARN: Type inference failed for: r2v18 */
        @Override // com.google.android.gms.internal.mlkit_language_id.zzeo
        protected final Object zza(int i, Object obj, Object obj2) {
            ?? r2;
            com.google.android.gms.internal.mlkit_language_id.zzx zzxVar = null;
            switch (com.google.android.gms.internal.mlkit_language_id.zzx.zza[i - 1]) {
                case 1:
                    return new zzm();
                case 2:
                    return new zza(zzxVar);
                case 3:
                    return zza(zzg, "\u0001\u0003\u0000\u0001\u0001\u0003\u0003\u0000\u0000\u0000\u0001ဉ\u0000\u0002ဉ\u0001\u0003ဉ\u0002", new Object[]{"zzc", "zzd", "zze", "zzf"});
                case 4:
                    return zzg;
                case 5:
                    zzgj<zzm> zzgjVar = zzh;
                    Object obj3 = zzgjVar;
                    if (zzgjVar == null) {
                        synchronized (zzm.class) {
                            zzgj<zzm> zzgjVar2 = zzh;
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
            zzm zzmVar = new zzm();
            zzg = zzmVar;
            zzeo.zza((Class<zzm>) zzm.class, zzmVar);
        }
    }

    /* JADX INFO: compiled from: com.google.mlkit:language-id@@16.1.1 */
    public static final class zzn extends zzeo<zzn, zzb> implements zzgb {
        private static final zzn zzf;
        private static volatile zzgj<zzn> zzg;
        private int zzc;
        private int zzd;
        private int zze;

        /* JADX INFO: compiled from: com.google.mlkit:language-id@@16.1.1 */
        public enum zza implements zzet {
            UNKNOWN_MODEL_TYPE(0),
            STABLE_MODEL(1),
            LATEST_MODEL(2);

            private static final zzes<zza> zzd = new com.google.android.gms.internal.mlkit_language_id.zzad();
            private final int zze;

            @Override // com.google.android.gms.internal.mlkit_language_id.zzet
            public final int zza() {
                return this.zze;
            }

            public static zzev zzb() {
                return com.google.android.gms.internal.mlkit_language_id.zzac.zza;
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

        private zzn() {
        }

        /* JADX INFO: compiled from: com.google.mlkit:language-id@@16.1.1 */
        public static final class zzb extends zzeo.zzb<zzn, zzb> implements zzgb {
            private zzb() {
                super(zzn.zzf);
            }

            /* synthetic */ zzb(com.google.android.gms.internal.mlkit_language_id.zzx zzxVar) {
                this();
            }
        }

        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Type inference failed for: r2v13 */
        /* JADX WARN: Type inference failed for: r2v14, types: [com.google.android.gms.internal.mlkit_language_id.zzeo$zza, com.google.android.gms.internal.mlkit_language_id.zzgj<com.google.android.gms.internal.mlkit_language_id.zzy$zzn>] */
        /* JADX WARN: Type inference failed for: r2v17 */
        /* JADX WARN: Type inference failed for: r2v18 */
        @Override // com.google.android.gms.internal.mlkit_language_id.zzeo
        protected final Object zza(int i, Object obj, Object obj2) {
            ?? r2;
            com.google.android.gms.internal.mlkit_language_id.zzx zzxVar = null;
            switch (com.google.android.gms.internal.mlkit_language_id.zzx.zza[i - 1]) {
                case 1:
                    return new zzn();
                case 2:
                    return new zzb(zzxVar);
                case 3:
                    return zza(zzf, "\u0001\u0002\u0000\u0001\u0001\u0002\u0002\u0000\u0000\u0000\u0001င\u0000\u0002ဌ\u0001", new Object[]{"zzc", "zzd", "zze", zza.zzb()});
                case 4:
                    return zzf;
                case 5:
                    zzgj<zzn> zzgjVar = zzg;
                    Object obj3 = zzgjVar;
                    if (zzgjVar == null) {
                        synchronized (zzn.class) {
                            zzgj<zzn> zzgjVar2 = zzg;
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
            zzn zznVar = new zzn();
            zzf = zznVar;
            zzeo.zza((Class<zzn>) zzn.class, zznVar);
        }
    }

    /* JADX INFO: compiled from: com.google.mlkit:language-id@@16.1.1 */
    public static final class zzo extends zzeo<zzo, zza> implements zzgb {
        private static final zzo zzg;
        private static volatile zzgj<zzo> zzh;
        private int zzc;
        private zzaf zzd;
        private zzn zze;
        private zzae zzf;

        private zzo() {
        }

        /* JADX INFO: compiled from: com.google.mlkit:language-id@@16.1.1 */
        public static final class zza extends zzeo.zzb<zzo, zza> implements zzgb {
            private zza() {
                super(zzo.zzg);
            }

            /* synthetic */ zza(com.google.android.gms.internal.mlkit_language_id.zzx zzxVar) {
                this();
            }
        }

        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Type inference failed for: r2v13 */
        /* JADX WARN: Type inference failed for: r2v14, types: [com.google.android.gms.internal.mlkit_language_id.zzeo$zza, com.google.android.gms.internal.mlkit_language_id.zzgj<com.google.android.gms.internal.mlkit_language_id.zzy$zzo>] */
        /* JADX WARN: Type inference failed for: r2v17 */
        /* JADX WARN: Type inference failed for: r2v18 */
        @Override // com.google.android.gms.internal.mlkit_language_id.zzeo
        protected final Object zza(int i, Object obj, Object obj2) {
            ?? r2;
            com.google.android.gms.internal.mlkit_language_id.zzx zzxVar = null;
            switch (com.google.android.gms.internal.mlkit_language_id.zzx.zza[i - 1]) {
                case 1:
                    return new zzo();
                case 2:
                    return new zza(zzxVar);
                case 3:
                    return zza(zzg, "\u0001\u0003\u0000\u0001\u0001\u0003\u0003\u0000\u0000\u0000\u0001ဉ\u0000\u0002ဉ\u0001\u0003ဉ\u0002", new Object[]{"zzc", "zzd", "zze", "zzf"});
                case 4:
                    return zzg;
                case 5:
                    zzgj<zzo> zzgjVar = zzh;
                    Object obj3 = zzgjVar;
                    if (zzgjVar == null) {
                        synchronized (zzo.class) {
                            zzgj<zzo> zzgjVar2 = zzh;
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
            zzo zzoVar = new zzo();
            zzg = zzoVar;
            zzeo.zza((Class<zzo>) zzo.class, zzoVar);
        }
    }

    /* JADX INFO: compiled from: com.google.mlkit:language-id@@16.1.1 */
    public static final class zzp extends zzeo<zzp, zza> implements zzgb {
        private static final zzp zzg;
        private static volatile zzgj<zzp> zzh;
        private int zzc;
        private zzaf zzd;
        private zzn zze;
        private zzae zzf;

        private zzp() {
        }

        /* JADX INFO: compiled from: com.google.mlkit:language-id@@16.1.1 */
        public static final class zza extends zzeo.zzb<zzp, zza> implements zzgb {
            private zza() {
                super(zzp.zzg);
            }

            /* synthetic */ zza(com.google.android.gms.internal.mlkit_language_id.zzx zzxVar) {
                this();
            }
        }

        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Type inference failed for: r2v13 */
        /* JADX WARN: Type inference failed for: r2v14, types: [com.google.android.gms.internal.mlkit_language_id.zzeo$zza, com.google.android.gms.internal.mlkit_language_id.zzgj<com.google.android.gms.internal.mlkit_language_id.zzy$zzp>] */
        /* JADX WARN: Type inference failed for: r2v17 */
        /* JADX WARN: Type inference failed for: r2v18 */
        @Override // com.google.android.gms.internal.mlkit_language_id.zzeo
        protected final Object zza(int i, Object obj, Object obj2) {
            ?? r2;
            com.google.android.gms.internal.mlkit_language_id.zzx zzxVar = null;
            switch (com.google.android.gms.internal.mlkit_language_id.zzx.zza[i - 1]) {
                case 1:
                    return new zzp();
                case 2:
                    return new zza(zzxVar);
                case 3:
                    return zza(zzg, "\u0001\u0003\u0000\u0001\u0001\u0003\u0003\u0000\u0000\u0000\u0001ဉ\u0000\u0002ဉ\u0001\u0003ဉ\u0002", new Object[]{"zzc", "zzd", "zze", "zzf"});
                case 4:
                    return zzg;
                case 5:
                    zzgj<zzp> zzgjVar = zzh;
                    Object obj3 = zzgjVar;
                    if (zzgjVar == null) {
                        synchronized (zzp.class) {
                            zzgj<zzp> zzgjVar2 = zzh;
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
            zzp zzpVar = new zzp();
            zzg = zzpVar;
            zzeo.zza((Class<zzp>) zzp.class, zzpVar);
        }
    }

    /* JADX INFO: compiled from: com.google.mlkit:language-id@@16.1.1 */
    public static final class zzq extends zzeo<zzq, zza> implements zzgb {
        private static final zzq zzg;
        private static volatile zzgj<zzq> zzh;
        private int zzc;
        private zzaf zzd;
        private zzn zze;
        private zzae zzf;

        private zzq() {
        }

        /* JADX INFO: compiled from: com.google.mlkit:language-id@@16.1.1 */
        public static final class zza extends zzeo.zzb<zzq, zza> implements zzgb {
            private zza() {
                super(zzq.zzg);
            }

            /* synthetic */ zza(com.google.android.gms.internal.mlkit_language_id.zzx zzxVar) {
                this();
            }
        }

        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Type inference failed for: r2v13 */
        /* JADX WARN: Type inference failed for: r2v14, types: [com.google.android.gms.internal.mlkit_language_id.zzeo$zza, com.google.android.gms.internal.mlkit_language_id.zzgj<com.google.android.gms.internal.mlkit_language_id.zzy$zzq>] */
        /* JADX WARN: Type inference failed for: r2v17 */
        /* JADX WARN: Type inference failed for: r2v18 */
        @Override // com.google.android.gms.internal.mlkit_language_id.zzeo
        protected final Object zza(int i, Object obj, Object obj2) {
            ?? r2;
            com.google.android.gms.internal.mlkit_language_id.zzx zzxVar = null;
            switch (com.google.android.gms.internal.mlkit_language_id.zzx.zza[i - 1]) {
                case 1:
                    return new zzq();
                case 2:
                    return new zza(zzxVar);
                case 3:
                    return zza(zzg, "\u0001\u0003\u0000\u0001\u0001\u0003\u0003\u0000\u0000\u0000\u0001ဉ\u0000\u0002ဉ\u0001\u0003ဉ\u0002", new Object[]{"zzc", "zzd", "zze", "zzf"});
                case 4:
                    return zzg;
                case 5:
                    zzgj<zzq> zzgjVar = zzh;
                    Object obj3 = zzgjVar;
                    if (zzgjVar == null) {
                        synchronized (zzq.class) {
                            zzgj<zzq> zzgjVar2 = zzh;
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
            zzq zzqVar = new zzq();
            zzg = zzqVar;
            zzeo.zza((Class<zzq>) zzq.class, zzqVar);
        }
    }

    /* JADX INFO: compiled from: com.google.mlkit:language-id@@16.1.1 */
    public static final class zzr extends zzeo<zzr, zza> implements zzgb {
        private static final zzr zzg;
        private static volatile zzgj<zzr> zzh;
        private int zzc;
        private zzaf zzd;
        private zzn zze;
        private zzae zzf;

        private zzr() {
        }

        /* JADX INFO: compiled from: com.google.mlkit:language-id@@16.1.1 */
        public static final class zza extends zzeo.zzb<zzr, zza> implements zzgb {
            private zza() {
                super(zzr.zzg);
            }

            /* synthetic */ zza(com.google.android.gms.internal.mlkit_language_id.zzx zzxVar) {
                this();
            }
        }

        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Type inference failed for: r2v13 */
        /* JADX WARN: Type inference failed for: r2v14, types: [com.google.android.gms.internal.mlkit_language_id.zzeo$zza, com.google.android.gms.internal.mlkit_language_id.zzgj<com.google.android.gms.internal.mlkit_language_id.zzy$zzr>] */
        /* JADX WARN: Type inference failed for: r2v17 */
        /* JADX WARN: Type inference failed for: r2v18 */
        @Override // com.google.android.gms.internal.mlkit_language_id.zzeo
        protected final Object zza(int i, Object obj, Object obj2) {
            ?? r2;
            com.google.android.gms.internal.mlkit_language_id.zzx zzxVar = null;
            switch (com.google.android.gms.internal.mlkit_language_id.zzx.zza[i - 1]) {
                case 1:
                    return new zzr();
                case 2:
                    return new zza(zzxVar);
                case 3:
                    return zza(zzg, "\u0001\u0003\u0000\u0001\u0001\u0003\u0003\u0000\u0000\u0000\u0001ဉ\u0000\u0002ဉ\u0001\u0003ဉ\u0002", new Object[]{"zzc", "zzd", "zze", "zzf"});
                case 4:
                    return zzg;
                case 5:
                    zzgj<zzr> zzgjVar = zzh;
                    Object obj3 = zzgjVar;
                    if (zzgjVar == null) {
                        synchronized (zzr.class) {
                            zzgj<zzr> zzgjVar2 = zzh;
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
            zzr zzrVar = new zzr();
            zzg = zzrVar;
            zzeo.zza((Class<zzr>) zzr.class, zzrVar);
        }
    }

    /* JADX INFO: compiled from: com.google.mlkit:language-id@@16.1.1 */
    public static final class zzs extends zzeo<zzs, zza> implements zzgb {
        private static final zzs zzg;
        private static volatile zzgj<zzs> zzh;
        private int zzc;
        private zzaf zzd;
        private zzn zze;
        private zzae zzf;

        private zzs() {
        }

        /* JADX INFO: compiled from: com.google.mlkit:language-id@@16.1.1 */
        public static final class zza extends zzeo.zzb<zzs, zza> implements zzgb {
            private zza() {
                super(zzs.zzg);
            }

            /* synthetic */ zza(com.google.android.gms.internal.mlkit_language_id.zzx zzxVar) {
                this();
            }
        }

        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Type inference failed for: r2v13 */
        /* JADX WARN: Type inference failed for: r2v14, types: [com.google.android.gms.internal.mlkit_language_id.zzeo$zza, com.google.android.gms.internal.mlkit_language_id.zzgj<com.google.android.gms.internal.mlkit_language_id.zzy$zzs>] */
        /* JADX WARN: Type inference failed for: r2v17 */
        /* JADX WARN: Type inference failed for: r2v18 */
        @Override // com.google.android.gms.internal.mlkit_language_id.zzeo
        protected final Object zza(int i, Object obj, Object obj2) {
            ?? r2;
            com.google.android.gms.internal.mlkit_language_id.zzx zzxVar = null;
            switch (com.google.android.gms.internal.mlkit_language_id.zzx.zza[i - 1]) {
                case 1:
                    return new zzs();
                case 2:
                    return new zza(zzxVar);
                case 3:
                    return zza(zzg, "\u0001\u0003\u0000\u0001\u0001\u0003\u0003\u0000\u0000\u0000\u0001ဉ\u0000\u0002ဉ\u0001\u0003ဉ\u0002", new Object[]{"zzc", "zzd", "zze", "zzf"});
                case 4:
                    return zzg;
                case 5:
                    zzgj<zzs> zzgjVar = zzh;
                    Object obj3 = zzgjVar;
                    if (zzgjVar == null) {
                        synchronized (zzs.class) {
                            zzgj<zzs> zzgjVar2 = zzh;
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
            zzs zzsVar = new zzs();
            zzg = zzsVar;
            zzeo.zza((Class<zzs>) zzs.class, zzsVar);
        }
    }

    /* JADX INFO: compiled from: com.google.mlkit:language-id@@16.1.1 */
    public static final class zzt extends zzeo<zzt, zza> implements zzgb {
        private static final zzt zzg;
        private static volatile zzgj<zzt> zzh;
        private int zzc;
        private zzaf zzd;
        private zzn zze;
        private zzae zzf;

        private zzt() {
        }

        /* JADX INFO: compiled from: com.google.mlkit:language-id@@16.1.1 */
        public static final class zza extends zzeo.zzb<zzt, zza> implements zzgb {
            private zza() {
                super(zzt.zzg);
            }

            /* synthetic */ zza(com.google.android.gms.internal.mlkit_language_id.zzx zzxVar) {
                this();
            }
        }

        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Type inference failed for: r2v13 */
        /* JADX WARN: Type inference failed for: r2v14, types: [com.google.android.gms.internal.mlkit_language_id.zzeo$zza, com.google.android.gms.internal.mlkit_language_id.zzgj<com.google.android.gms.internal.mlkit_language_id.zzy$zzt>] */
        /* JADX WARN: Type inference failed for: r2v17 */
        /* JADX WARN: Type inference failed for: r2v18 */
        @Override // com.google.android.gms.internal.mlkit_language_id.zzeo
        protected final Object zza(int i, Object obj, Object obj2) {
            ?? r2;
            com.google.android.gms.internal.mlkit_language_id.zzx zzxVar = null;
            switch (com.google.android.gms.internal.mlkit_language_id.zzx.zza[i - 1]) {
                case 1:
                    return new zzt();
                case 2:
                    return new zza(zzxVar);
                case 3:
                    return zza(zzg, "\u0001\u0003\u0000\u0001\u0001\u0003\u0003\u0000\u0000\u0000\u0001ဉ\u0000\u0002ဉ\u0001\u0003ဉ\u0002", new Object[]{"zzc", "zzd", "zze", "zzf"});
                case 4:
                    return zzg;
                case 5:
                    zzgj<zzt> zzgjVar = zzh;
                    Object obj3 = zzgjVar;
                    if (zzgjVar == null) {
                        synchronized (zzt.class) {
                            zzgj<zzt> zzgjVar2 = zzh;
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
            zzt zztVar = new zzt();
            zzg = zztVar;
            zzeo.zza((Class<zzt>) zzt.class, zztVar);
        }
    }

    /* JADX INFO: compiled from: com.google.mlkit:language-id@@16.1.1 */
    public static final class zzu extends zzeo<zzu, zza> implements zzgb {
        private static final zzu zzg;
        private static volatile zzgj<zzu> zzh;
        private int zzc;
        private zzaf zzd;
        private zzn zze;
        private zzae zzf;

        private zzu() {
        }

        /* JADX INFO: compiled from: com.google.mlkit:language-id@@16.1.1 */
        public static final class zza extends zzeo.zzb<zzu, zza> implements zzgb {
            private zza() {
                super(zzu.zzg);
            }

            /* synthetic */ zza(com.google.android.gms.internal.mlkit_language_id.zzx zzxVar) {
                this();
            }
        }

        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Type inference failed for: r2v13 */
        /* JADX WARN: Type inference failed for: r2v14, types: [com.google.android.gms.internal.mlkit_language_id.zzeo$zza, com.google.android.gms.internal.mlkit_language_id.zzgj<com.google.android.gms.internal.mlkit_language_id.zzy$zzu>] */
        /* JADX WARN: Type inference failed for: r2v17 */
        /* JADX WARN: Type inference failed for: r2v18 */
        @Override // com.google.android.gms.internal.mlkit_language_id.zzeo
        protected final Object zza(int i, Object obj, Object obj2) {
            ?? r2;
            com.google.android.gms.internal.mlkit_language_id.zzx zzxVar = null;
            switch (com.google.android.gms.internal.mlkit_language_id.zzx.zza[i - 1]) {
                case 1:
                    return new zzu();
                case 2:
                    return new zza(zzxVar);
                case 3:
                    return zza(zzg, "\u0001\u0003\u0000\u0001\u0001\u0003\u0003\u0000\u0000\u0000\u0001ဉ\u0000\u0002ဉ\u0001\u0003ဉ\u0002", new Object[]{"zzc", "zzd", "zze", "zzf"});
                case 4:
                    return zzg;
                case 5:
                    zzgj<zzu> zzgjVar = zzh;
                    Object obj3 = zzgjVar;
                    if (zzgjVar == null) {
                        synchronized (zzu.class) {
                            zzgj<zzu> zzgjVar2 = zzh;
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
            zzu zzuVar = new zzu();
            zzg = zzuVar;
            zzeo.zza((Class<zzu>) zzu.class, zzuVar);
        }
    }

    /* JADX INFO: compiled from: com.google.mlkit:language-id@@16.1.1 */
    public static final class zzv extends zzeo<zzv, zza> implements zzgb {
        private static final zzv zzg;
        private static volatile zzgj<zzv> zzh;
        private int zzc;
        private zzaf zzd;
        private zzn zze;
        private zzae zzf;

        private zzv() {
        }

        /* JADX INFO: compiled from: com.google.mlkit:language-id@@16.1.1 */
        public static final class zza extends zzeo.zzb<zzv, zza> implements zzgb {
            private zza() {
                super(zzv.zzg);
            }

            /* synthetic */ zza(com.google.android.gms.internal.mlkit_language_id.zzx zzxVar) {
                this();
            }
        }

        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Type inference failed for: r2v13 */
        /* JADX WARN: Type inference failed for: r2v14, types: [com.google.android.gms.internal.mlkit_language_id.zzeo$zza, com.google.android.gms.internal.mlkit_language_id.zzgj<com.google.android.gms.internal.mlkit_language_id.zzy$zzv>] */
        /* JADX WARN: Type inference failed for: r2v17 */
        /* JADX WARN: Type inference failed for: r2v18 */
        @Override // com.google.android.gms.internal.mlkit_language_id.zzeo
        protected final Object zza(int i, Object obj, Object obj2) {
            ?? r2;
            com.google.android.gms.internal.mlkit_language_id.zzx zzxVar = null;
            switch (com.google.android.gms.internal.mlkit_language_id.zzx.zza[i - 1]) {
                case 1:
                    return new zzv();
                case 2:
                    return new zza(zzxVar);
                case 3:
                    return zza(zzg, "\u0001\u0003\u0000\u0001\u0001\u0003\u0003\u0000\u0000\u0000\u0001ဉ\u0000\u0002ဉ\u0001\u0003ဉ\u0002", new Object[]{"zzc", "zzd", "zze", "zzf"});
                case 4:
                    return zzg;
                case 5:
                    zzgj<zzv> zzgjVar = zzh;
                    Object obj3 = zzgjVar;
                    if (zzgjVar == null) {
                        synchronized (zzv.class) {
                            zzgj<zzv> zzgjVar2 = zzh;
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
            zzv zzvVar = new zzv();
            zzg = zzvVar;
            zzeo.zza((Class<zzv>) zzv.class, zzvVar);
        }
    }

    /* JADX INFO: compiled from: com.google.mlkit:language-id@@16.1.1 */
    public static final class zzw extends zzeo<zzw, zza> implements zzgb {
        private static final zzw zzg;
        private static volatile zzgj<zzw> zzh;
        private int zzc;
        private zzaf zzd;
        private zzn zze;
        private zzae zzf;

        private zzw() {
        }

        /* JADX INFO: compiled from: com.google.mlkit:language-id@@16.1.1 */
        public static final class zza extends zzeo.zzb<zzw, zza> implements zzgb {
            private zza() {
                super(zzw.zzg);
            }

            /* synthetic */ zza(com.google.android.gms.internal.mlkit_language_id.zzx zzxVar) {
                this();
            }
        }

        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Type inference failed for: r2v13 */
        /* JADX WARN: Type inference failed for: r2v14, types: [com.google.android.gms.internal.mlkit_language_id.zzeo$zza, com.google.android.gms.internal.mlkit_language_id.zzgj<com.google.android.gms.internal.mlkit_language_id.zzy$zzw>] */
        /* JADX WARN: Type inference failed for: r2v17 */
        /* JADX WARN: Type inference failed for: r2v18 */
        @Override // com.google.android.gms.internal.mlkit_language_id.zzeo
        protected final Object zza(int i, Object obj, Object obj2) {
            ?? r2;
            com.google.android.gms.internal.mlkit_language_id.zzx zzxVar = null;
            switch (com.google.android.gms.internal.mlkit_language_id.zzx.zza[i - 1]) {
                case 1:
                    return new zzw();
                case 2:
                    return new zza(zzxVar);
                case 3:
                    return zza(zzg, "\u0001\u0003\u0000\u0001\u0001\u0003\u0003\u0000\u0000\u0000\u0001ဉ\u0000\u0002ဉ\u0001\u0003ဉ\u0002", new Object[]{"zzc", "zzd", "zze", "zzf"});
                case 4:
                    return zzg;
                case 5:
                    zzgj<zzw> zzgjVar = zzh;
                    Object obj3 = zzgjVar;
                    if (zzgjVar == null) {
                        synchronized (zzw.class) {
                            zzgj<zzw> zzgjVar2 = zzh;
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
            zzw zzwVar = new zzw();
            zzg = zzwVar;
            zzeo.zza((Class<zzw>) zzw.class, zzwVar);
        }
    }

    /* JADX INFO: compiled from: com.google.mlkit:language-id@@16.1.1 */
    public static final class zzx extends zzeo<zzx, zza> implements zzgb {
        private static final zzx zzf;
        private static volatile zzgj<zzx> zzg;
        private int zzc;
        private zzam zzd;
        private int zze;

        private zzx() {
        }

        /* JADX INFO: compiled from: com.google.mlkit:language-id@@16.1.1 */
        public static final class zza extends zzeo.zzb<zzx, zza> implements zzgb {
            private zza() {
                super(zzx.zzf);
            }

            /* synthetic */ zza(com.google.android.gms.internal.mlkit_language_id.zzx zzxVar) {
                this();
            }
        }

        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Type inference failed for: r2v13 */
        /* JADX WARN: Type inference failed for: r2v14, types: [com.google.android.gms.internal.mlkit_language_id.zzeo$zza, com.google.android.gms.internal.mlkit_language_id.zzgj<com.google.android.gms.internal.mlkit_language_id.zzy$zzx>] */
        /* JADX WARN: Type inference failed for: r2v17 */
        /* JADX WARN: Type inference failed for: r2v18 */
        @Override // com.google.android.gms.internal.mlkit_language_id.zzeo
        protected final Object zza(int i, Object obj, Object obj2) {
            ?? r2;
            com.google.android.gms.internal.mlkit_language_id.zzx zzxVar = null;
            switch (com.google.android.gms.internal.mlkit_language_id.zzx.zza[i - 1]) {
                case 1:
                    return new zzx();
                case 2:
                    return new zza(zzxVar);
                case 3:
                    return zza(zzf, "\u0001\u0002\u0000\u0001\u0001\u0002\u0002\u0000\u0000\u0000\u0001ဉ\u0000\u0002ဌ\u0001", new Object[]{"zzc", "zzd", "zze", com.google.android.gms.internal.mlkit_language_id.zzai.zzb()});
                case 4:
                    return zzf;
                case 5:
                    zzgj<zzx> zzgjVar = zzg;
                    Object obj3 = zzgjVar;
                    if (zzgjVar == null) {
                        synchronized (zzx.class) {
                            zzgj<zzx> zzgjVar2 = zzg;
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
            zzx zzxVar = new zzx();
            zzf = zzxVar;
            zzeo.zza((Class<zzx>) zzx.class, zzxVar);
        }
    }

    /* JADX INFO: renamed from: com.google.android.gms.internal.mlkit_language_id.zzy$zzy, reason: collision with other inner class name */
    /* JADX INFO: compiled from: com.google.mlkit:language-id@@16.1.1 */
    public static final class C0053zzy extends zzeo<C0053zzy, zza> implements zzgb {
        private static final C0053zzy zzi;
        private static volatile zzgj<C0053zzy> zzj;
        private int zzc;
        private zzaf zzd;
        private zzam zze;
        private zzew<zzb> zzf = zzl();
        private zzew<zzb> zzg = zzl();
        private long zzh;

        /* JADX INFO: renamed from: com.google.android.gms.internal.mlkit_language_id.zzy$zzy$zzb */
        /* JADX INFO: compiled from: com.google.mlkit:language-id@@16.1.1 */
        public static final class zzb extends zzeo<zzb, zza> implements zzgb {
            private static final zzb zzf;
            private static volatile zzgj<zzb> zzg;
            private int zzc;
            private int zzd;
            private zzeu zze = zzk();

            /* JADX INFO: renamed from: com.google.android.gms.internal.mlkit_language_id.zzy$zzy$zzb$zzb, reason: collision with other inner class name */
            /* JADX INFO: compiled from: com.google.mlkit:language-id@@16.1.1 */
            public enum EnumC0054zzb implements zzet {
                UNKNOWN_DATA_TYPE(0),
                TYPE_FLOAT32(1),
                TYPE_INT32(2),
                TYPE_BYTE(3),
                TYPE_LONG(4);

                private static final zzes<EnumC0054zzb> zzf = new com.google.android.gms.internal.mlkit_language_id.zzae();
                private final int zzg;

                @Override // com.google.android.gms.internal.mlkit_language_id.zzet
                public final int zza() {
                    return this.zzg;
                }

                public static zzev zzb() {
                    return com.google.android.gms.internal.mlkit_language_id.zzaf.zza;
                }

                @Override // java.lang.Enum
                public final String toString() {
                    StringBuilder sb = new StringBuilder("<");
                    sb.append(getClass().getName()).append('@').append(Integer.toHexString(System.identityHashCode(this)));
                    sb.append(" number=").append(this.zzg);
                    return sb.append(" name=").append(name()).append('>').toString();
                }

                EnumC0054zzb(int i) {
                    this.zzg = i;
                }
            }

            private zzb() {
            }

            /* JADX INFO: renamed from: com.google.android.gms.internal.mlkit_language_id.zzy$zzy$zzb$zza */
            /* JADX INFO: compiled from: com.google.mlkit:language-id@@16.1.1 */
            public static final class zza extends zzeo.zzb<zzb, zza> implements zzgb {
                private zza() {
                    super(zzb.zzf);
                }

                /* synthetic */ zza(com.google.android.gms.internal.mlkit_language_id.zzx zzxVar) {
                    this();
                }
            }

            /* JADX WARN: Multi-variable type inference failed */
            /* JADX WARN: Type inference failed for: r2v13 */
            /* JADX WARN: Type inference failed for: r2v14, types: [com.google.android.gms.internal.mlkit_language_id.zzeo$zza, com.google.android.gms.internal.mlkit_language_id.zzgj<com.google.android.gms.internal.mlkit_language_id.zzy$zzy$zzb>] */
            /* JADX WARN: Type inference failed for: r2v17 */
            /* JADX WARN: Type inference failed for: r2v18 */
            @Override // com.google.android.gms.internal.mlkit_language_id.zzeo
            protected final Object zza(int i, Object obj, Object obj2) {
                ?? r2;
                com.google.android.gms.internal.mlkit_language_id.zzx zzxVar = null;
                switch (com.google.android.gms.internal.mlkit_language_id.zzx.zza[i - 1]) {
                    case 1:
                        return new zzb();
                    case 2:
                        return new zza(zzxVar);
                    case 3:
                        return zza(zzf, "\u0001\u0002\u0000\u0001\u0001\u0002\u0002\u0000\u0001\u0000\u0001ဌ\u0000\u0002\u0016", new Object[]{"zzc", "zzd", EnumC0054zzb.zzb(), "zze"});
                    case 4:
                        return zzf;
                    case 5:
                        zzgj<zzb> zzgjVar = zzg;
                        Object obj3 = zzgjVar;
                        if (zzgjVar == null) {
                            synchronized (zzb.class) {
                                zzgj<zzb> zzgjVar2 = zzg;
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
                zzb zzbVar = new zzb();
                zzf = zzbVar;
                zzeo.zza((Class<zzb>) zzb.class, zzbVar);
            }
        }

        private C0053zzy() {
        }

        /* JADX INFO: renamed from: com.google.android.gms.internal.mlkit_language_id.zzy$zzy$zza */
        /* JADX INFO: compiled from: com.google.mlkit:language-id@@16.1.1 */
        public static final class zza extends zzeo.zzb<C0053zzy, zza> implements zzgb {
            private zza() {
                super(C0053zzy.zzi);
            }

            /* synthetic */ zza(com.google.android.gms.internal.mlkit_language_id.zzx zzxVar) {
                this();
            }
        }

        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Type inference failed for: r9v12 */
        /* JADX WARN: Type inference failed for: r9v13, types: [com.google.android.gms.internal.mlkit_language_id.zzeo$zza, com.google.android.gms.internal.mlkit_language_id.zzgj<com.google.android.gms.internal.mlkit_language_id.zzy$zzy>] */
        /* JADX WARN: Type inference failed for: r9v16 */
        /* JADX WARN: Type inference failed for: r9v17 */
        @Override // com.google.android.gms.internal.mlkit_language_id.zzeo
        protected final Object zza(int i, Object obj, Object obj2) {
            ?? r9;
            com.google.android.gms.internal.mlkit_language_id.zzx zzxVar = null;
            switch (com.google.android.gms.internal.mlkit_language_id.zzx.zza[i - 1]) {
                case 1:
                    return new C0053zzy();
                case 2:
                    return new zza(zzxVar);
                case 3:
                    return zza(zzi, "\u0001\u0005\u0000\u0001\u0001\u0005\u0005\u0000\u0002\u0000\u0001ဉ\u0000\u0002ဉ\u0001\u0003\u001b\u0004\u001b\u0005ဃ\u0002", new Object[]{"zzc", "zzd", "zze", "zzf", zzb.class, "zzg", zzb.class, "zzh"});
                case 4:
                    return zzi;
                case 5:
                    zzgj<C0053zzy> zzgjVar = zzj;
                    Object obj3 = zzgjVar;
                    if (zzgjVar == null) {
                        synchronized (C0053zzy.class) {
                            zzgj<C0053zzy> zzgjVar2 = zzj;
                            r9 = zzgjVar2;
                            if (zzgjVar2 == null) {
                                ?? zzaVar = new zzeo.zza(zzi);
                                zzj = zzaVar;
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
            C0053zzy c0053zzy = new C0053zzy();
            zzi = c0053zzy;
            zzeo.zza((Class<C0053zzy>) C0053zzy.class, c0053zzy);
        }
    }

    /* JADX INFO: compiled from: com.google.mlkit:language-id@@16.1.1 */
    public static final class zzz extends zzeo<zzz, zza> implements zzgb {
        private static final zzex<Integer, com.google.android.gms.internal.mlkit_language_id.zzai> zzg = new com.google.android.gms.internal.mlkit_language_id.zzag();
        private static final zzz zzj;
        private static volatile zzgj<zzz> zzk;
        private int zzc;
        private zzam zzd;
        private zzam zze;
        private zzeu zzf = zzk();
        private long zzh;
        private boolean zzi;

        private zzz() {
        }

        /* JADX INFO: compiled from: com.google.mlkit:language-id@@16.1.1 */
        public static final class zza extends zzeo.zzb<zzz, zza> implements zzgb {
            private zza() {
                super(zzz.zzj);
            }

            /* synthetic */ zza(com.google.android.gms.internal.mlkit_language_id.zzx zzxVar) {
                this();
            }
        }

        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Type inference failed for: r8v12 */
        /* JADX WARN: Type inference failed for: r8v13, types: [com.google.android.gms.internal.mlkit_language_id.zzeo$zza, com.google.android.gms.internal.mlkit_language_id.zzgj<com.google.android.gms.internal.mlkit_language_id.zzy$zzz>] */
        /* JADX WARN: Type inference failed for: r8v16 */
        /* JADX WARN: Type inference failed for: r8v17 */
        @Override // com.google.android.gms.internal.mlkit_language_id.zzeo
        protected final Object zza(int i, Object obj, Object obj2) {
            ?? r8;
            com.google.android.gms.internal.mlkit_language_id.zzx zzxVar = null;
            switch (com.google.android.gms.internal.mlkit_language_id.zzx.zza[i - 1]) {
                case 1:
                    return new zzz();
                case 2:
                    return new zza(zzxVar);
                case 3:
                    return zza(zzj, "\u0001\u0005\u0000\u0001\u0001\u0005\u0005\u0000\u0001\u0000\u0001ဉ\u0000\u0002ဉ\u0001\u0003\u001e\u0004ဃ\u0002\u0005ဇ\u0003", new Object[]{"zzc", "zzd", "zze", "zzf", com.google.android.gms.internal.mlkit_language_id.zzai.zzb(), "zzh", "zzi"});
                case 4:
                    return zzj;
                case 5:
                    zzgj<zzz> zzgjVar = zzk;
                    Object obj3 = zzgjVar;
                    if (zzgjVar == null) {
                        synchronized (zzz.class) {
                            zzgj<zzz> zzgjVar2 = zzk;
                            r8 = zzgjVar2;
                            if (zzgjVar2 == null) {
                                ?? zzaVar = new zzeo.zza(zzj);
                                zzk = zzaVar;
                                r8 = zzaVar;
                            }
                            break;
                        }
                        obj3 = r8;
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

        /* JADX WARN: Type inference failed for: r0v0, types: [com.google.android.gms.internal.mlkit_language_id.zzag, com.google.android.gms.internal.mlkit_language_id.zzex<java.lang.Integer, com.google.android.gms.internal.mlkit_language_id.zzai>] */
        static {
            zzz zzzVar = new zzz();
            zzj = zzzVar;
            zzeo.zza((Class<zzz>) zzz.class, zzzVar);
        }
    }

    /* JADX INFO: compiled from: com.google.mlkit:language-id@@16.1.1 */
    public static final class zzaf extends zzeo<zzaf, zza> implements zzgb {
        private static final zzaf zzl;
        private static volatile zzgj<zzaf> zzm;
        private int zzc;
        private long zzd;
        private int zze;
        private boolean zzf;
        private boolean zzg;
        private boolean zzh;
        private boolean zzi;
        private int zzj;
        private zzew<zzbf> zzk = zzl();

        private zzaf() {
        }

        /* JADX INFO: compiled from: com.google.mlkit:language-id@@16.1.1 */
        public static final class zza extends zzeo.zzb<zzaf, zza> implements zzgb {
            private zza() {
                super(zzaf.zzl);
            }

            public final zza zza(long j) {
                if (this.zzb) {
                    zzc();
                    this.zzb = false;
                }
                ((zzaf) this.zza).zza(j);
                return this;
            }

            public final zza zza(com.google.android.gms.internal.mlkit_language_id.zzai zzaiVar) {
                if (this.zzb) {
                    zzc();
                    this.zzb = false;
                }
                ((zzaf) this.zza).zza(zzaiVar);
                return this;
            }

            public final zza zza(boolean z) {
                if (this.zzb) {
                    zzc();
                    this.zzb = false;
                }
                ((zzaf) this.zza).zza(z);
                return this;
            }

            /* synthetic */ zza(com.google.android.gms.internal.mlkit_language_id.zzx zzxVar) {
                this();
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final void zza(long j) {
            this.zzc |= 1;
            this.zzd = j;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final void zza(com.google.android.gms.internal.mlkit_language_id.zzai zzaiVar) {
            this.zze = zzaiVar.zza();
            this.zzc |= 2;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final void zza(boolean z) {
            this.zzc |= 4;
            this.zzf = z;
        }

        public static zza zza() {
            return zzl.zzh();
        }

        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Type inference failed for: r12v12 */
        /* JADX WARN: Type inference failed for: r12v13, types: [com.google.android.gms.internal.mlkit_language_id.zzeo$zza, com.google.android.gms.internal.mlkit_language_id.zzgj<com.google.android.gms.internal.mlkit_language_id.zzy$zzaf>] */
        /* JADX WARN: Type inference failed for: r12v16 */
        /* JADX WARN: Type inference failed for: r12v17 */
        @Override // com.google.android.gms.internal.mlkit_language_id.zzeo
        protected final Object zza(int i, Object obj, Object obj2) {
            ?? r12;
            com.google.android.gms.internal.mlkit_language_id.zzx zzxVar = null;
            switch (com.google.android.gms.internal.mlkit_language_id.zzx.zza[i - 1]) {
                case 1:
                    return new zzaf();
                case 2:
                    return new zza(zzxVar);
                case 3:
                    return zza(zzl, "\u0001\b\u0000\u0001\u0001\b\b\u0000\u0001\u0000\u0001ဃ\u0000\u0002ဌ\u0001\u0003ဇ\u0002\u0004ဇ\u0003\u0005ဇ\u0004\u0006ဇ\u0005\u0007ဋ\u0006\b\u001b", new Object[]{"zzc", "zzd", "zze", com.google.android.gms.internal.mlkit_language_id.zzai.zzb(), "zzf", "zzg", "zzh", "zzi", "zzj", "zzk", zzbf.class});
                case 4:
                    return zzl;
                case 5:
                    zzgj<zzaf> zzgjVar = zzm;
                    Object obj3 = zzgjVar;
                    if (zzgjVar == null) {
                        synchronized (zzaf.class) {
                            zzgj<zzaf> zzgjVar2 = zzm;
                            r12 = zzgjVar2;
                            if (zzgjVar2 == null) {
                                ?? zzaVar = new zzeo.zza(zzl);
                                zzm = zzaVar;
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
            zzaf zzafVar = new zzaf();
            zzl = zzafVar;
            zzeo.zza((Class<zzaf>) zzaf.class, zzafVar);
        }
    }

    /* JADX INFO: compiled from: com.google.mlkit:language-id@@16.1.1 */
    public static final class zzai extends zzeo<zzai, zza> implements zzgb {
        private static final zzai zzg;
        private static volatile zzgj<zzai> zzh;
        private int zzc;
        private float zzd;
        private float zze;
        private float zzf;

        private zzai() {
        }

        /* JADX INFO: compiled from: com.google.mlkit:language-id@@16.1.1 */
        public static final class zza extends zzeo.zzb<zzai, zza> implements zzgb {
            private zza() {
                super(zzai.zzg);
            }

            public final zza zza(float f) {
                if (this.zzb) {
                    zzc();
                    this.zzb = false;
                }
                ((zzai) this.zza).zza(f);
                return this;
            }

            /* synthetic */ zza(com.google.android.gms.internal.mlkit_language_id.zzx zzxVar) {
                this();
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final void zza(float f) {
            this.zzc |= 4;
            this.zzf = f;
        }

        public static zza zza() {
            return zzg.zzh();
        }

        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Type inference failed for: r2v13 */
        /* JADX WARN: Type inference failed for: r2v14, types: [com.google.android.gms.internal.mlkit_language_id.zzeo$zza, com.google.android.gms.internal.mlkit_language_id.zzgj<com.google.android.gms.internal.mlkit_language_id.zzy$zzai>] */
        /* JADX WARN: Type inference failed for: r2v17 */
        /* JADX WARN: Type inference failed for: r2v18 */
        @Override // com.google.android.gms.internal.mlkit_language_id.zzeo
        protected final Object zza(int i, Object obj, Object obj2) {
            ?? r2;
            com.google.android.gms.internal.mlkit_language_id.zzx zzxVar = null;
            switch (com.google.android.gms.internal.mlkit_language_id.zzx.zza[i - 1]) {
                case 1:
                    return new zzai();
                case 2:
                    return new zza(zzxVar);
                case 3:
                    return zza(zzg, "\u0001\u0003\u0000\u0001\u0001\u0003\u0003\u0000\u0000\u0000\u0001ခ\u0000\u0002ခ\u0001\u0003ခ\u0002", new Object[]{"zzc", "zzd", "zze", "zzf"});
                case 4:
                    return zzg;
                case 5:
                    zzgj<zzai> zzgjVar = zzh;
                    Object obj3 = zzgjVar;
                    if (zzgjVar == null) {
                        synchronized (zzai.class) {
                            zzgj<zzai> zzgjVar2 = zzh;
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

        public static zzai zzb() {
            return zzg;
        }

        static {
            zzai zzaiVar = new zzai();
            zzg = zzaiVar;
            zzeo.zza((Class<zzai>) zzai.class, zzaiVar);
        }
    }

    /* JADX INFO: compiled from: com.google.mlkit:language-id@@16.1.1 */
    public static final class zzbh extends zzeo<zzbh, zza> implements zzgb {
        private static final zzbh zzo;
        private static volatile zzgj<zzbh> zzp;
        private int zzc;
        private String zzd = "";
        private String zze = "";
        private String zzf = "";
        private String zzg = "";
        private String zzh = "";
        private String zzi = "";
        private String zzj = "";
        private zzew<String> zzk = zzeo.zzl();
        private String zzl = "";
        private boolean zzm;
        private boolean zzn;

        private zzbh() {
        }

        /* JADX INFO: compiled from: com.google.mlkit:language-id@@16.1.1 */
        public static final class zza extends zzeo.zzb<zzbh, zza> implements zzgb {
            private zza() {
                super(zzbh.zzo);
            }

            public final zza zza(String str) {
                if (this.zzb) {
                    zzc();
                    this.zzb = false;
                }
                ((zzbh) this.zza).zza(str);
                return this;
            }

            public final zza zzb(String str) {
                if (this.zzb) {
                    zzc();
                    this.zzb = false;
                }
                ((zzbh) this.zza).zzb(str);
                return this;
            }

            public final zza zzc(String str) {
                if (this.zzb) {
                    zzc();
                    this.zzb = false;
                }
                ((zzbh) this.zza).zzc(str);
                return this;
            }

            public final zza zzd(String str) {
                if (this.zzb) {
                    zzc();
                    this.zzb = false;
                }
                ((zzbh) this.zza).zzd(str);
                return this;
            }

            public final zza zza(Iterable<String> iterable) {
                if (this.zzb) {
                    zzc();
                    this.zzb = false;
                }
                ((zzbh) this.zza).zza(iterable);
                return this;
            }

            public final zza zze(String str) {
                if (this.zzb) {
                    zzc();
                    this.zzb = false;
                }
                ((zzbh) this.zza).zze(str);
                return this;
            }

            public final zza zza(boolean z) {
                if (this.zzb) {
                    zzc();
                    this.zzb = false;
                }
                ((zzbh) this.zza).zza(true);
                return this;
            }

            public final zza zzb(boolean z) {
                if (this.zzb) {
                    zzc();
                    this.zzb = false;
                }
                ((zzbh) this.zza).zzb(true);
                return this;
            }

            /* synthetic */ zza(com.google.android.gms.internal.mlkit_language_id.zzx zzxVar) {
                this();
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final void zza(String str) {
            str.getClass();
            this.zzc |= 1;
            this.zzd = str;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final void zzb(String str) {
            str.getClass();
            this.zzc |= 2;
            this.zze = str;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final void zzc(String str) {
            str.getClass();
            this.zzc |= 8;
            this.zzg = str;
        }

        public final String zza() {
            return this.zzh;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final void zzd(String str) {
            str.getClass();
            this.zzc |= 16;
            this.zzh = str;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final void zza(Iterable<String> iterable) {
            zzew<String> zzewVar = this.zzk;
            if (!zzewVar.zza()) {
                this.zzk = zzeo.zza(zzewVar);
            }
            zzde.zza(iterable, this.zzk);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final void zze(String str) {
            str.getClass();
            this.zzc |= 128;
            this.zzl = str;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final void zza(boolean z) {
            this.zzc |= 256;
            this.zzm = true;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final void zzb(boolean z) {
            this.zzc |= 512;
            this.zzn = true;
        }

        public static zza zzb() {
            return zzo.zzh();
        }

        public static zza zza(zzbh zzbhVar) {
            return zzo.zza(zzbhVar);
        }

        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Type inference failed for: r13v12 */
        /* JADX WARN: Type inference failed for: r13v13, types: [com.google.android.gms.internal.mlkit_language_id.zzeo$zza, com.google.android.gms.internal.mlkit_language_id.zzgj<com.google.android.gms.internal.mlkit_language_id.zzy$zzbh>] */
        /* JADX WARN: Type inference failed for: r13v16 */
        /* JADX WARN: Type inference failed for: r13v17 */
        @Override // com.google.android.gms.internal.mlkit_language_id.zzeo
        protected final Object zza(int i, Object obj, Object obj2) {
            ?? r13;
            com.google.android.gms.internal.mlkit_language_id.zzx zzxVar = null;
            switch (com.google.android.gms.internal.mlkit_language_id.zzx.zza[i - 1]) {
                case 1:
                    return new zzbh();
                case 2:
                    return new zza(zzxVar);
                case 3:
                    return zza(zzo, "\u0001\u000b\u0000\u0001\u0001\u000b\u000b\u0000\u0001\u0000\u0001ဈ\u0000\u0002ဈ\u0001\u0003ဈ\u0002\u0004ဈ\u0003\u0005ဈ\u0004\u0006ဈ\u0005\u0007ဈ\u0006\b\u001a\tဈ\u0007\nဇ\b\u000bဇ\t", new Object[]{"zzc", "zzd", "zze", "zzf", "zzg", "zzh", "zzi", "zzj", "zzk", "zzl", "zzm", "zzn"});
                case 4:
                    return zzo;
                case 5:
                    zzgj<zzbh> zzgjVar = zzp;
                    Object obj3 = zzgjVar;
                    if (zzgjVar == null) {
                        synchronized (zzbh.class) {
                            zzgj<zzbh> zzgjVar2 = zzp;
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
                    return (byte) 1;
                case 7:
                    return null;
                default:
                    throw new UnsupportedOperationException();
            }
        }

        public static zzbh zzc() {
            return zzo;
        }

        static {
            zzbh zzbhVar = new zzbh();
            zzo = zzbhVar;
            zzeo.zza((Class<zzbh>) zzbh.class, zzbhVar);
        }
    }
}
