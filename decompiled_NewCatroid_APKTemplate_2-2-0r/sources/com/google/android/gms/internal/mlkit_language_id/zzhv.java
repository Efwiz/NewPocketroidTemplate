package com.google.android.gms.internal.mlkit_language_id;

/* JADX WARN: Enum visitor error
jadx.core.utils.exceptions.JadxRuntimeException: Init of enum field 'zzi' uses external variables
	at jadx.core.dex.visitors.EnumVisitor.createEnumFieldByConstructor(EnumVisitor.java:485)
	at jadx.core.dex.visitors.EnumVisitor.processEnumFieldByRegister(EnumVisitor.java:422)
	at jadx.core.dex.visitors.EnumVisitor.extractEnumFieldsFromFilledArray(EnumVisitor.java:351)
	at jadx.core.dex.visitors.EnumVisitor.extractEnumFieldsFromInsn(EnumVisitor.java:284)
	at jadx.core.dex.visitors.EnumVisitor.convertToEnum(EnumVisitor.java:153)
	at jadx.core.dex.visitors.EnumVisitor.visit(EnumVisitor.java:102)
 */
/* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
/* JADX INFO: compiled from: com.google.mlkit:language-id@@16.1.1 */
/* JADX INFO: loaded from: classes9.dex */
public class zzhv {
    public static final zzhv zza;
    public static final zzhv zzb;
    public static final zzhv zzc;
    public static final zzhv zzd;
    public static final zzhv zze;
    public static final zzhv zzf;
    public static final zzhv zzg;
    public static final zzhv zzh;
    public static final zzhv zzi;
    public static final zzhv zzj;
    public static final zzhv zzk;
    public static final zzhv zzl;
    public static final zzhv zzm;
    public static final zzhv zzn;
    public static final zzhv zzo;
    public static final zzhv zzp;
    public static final zzhv zzq;
    public static final zzhv zzr;
    private static final /* synthetic */ zzhv[] zzu;
    private final zzhy zzs;
    private final int zzt;

    public static zzhv[] values() {
        return (zzhv[]) zzu.clone();
    }

    private zzhv(String str, int i, zzhy zzhyVar, int i2) {
        super(str, i);
        this.zzs = zzhyVar;
        this.zzt = i2;
    }

    public final zzhy zza() {
        return this.zzs;
    }

    public final int zzb() {
        return this.zzt;
    }

    /* synthetic */ zzhv(String str, int i, zzhy zzhyVar, int i2, zzhs zzhsVar) {
        this(str, i, zzhyVar, i2);
    }

    static {
        zzhv zzhvVar = new zzhv("DOUBLE", 0, zzhy.DOUBLE, 1);
        zza = zzhvVar;
        zzhv zzhvVar2 = new zzhv("FLOAT", 1, zzhy.FLOAT, 5);
        zzb = zzhvVar2;
        final int i = 2;
        zzhv zzhvVar3 = new zzhv("INT64", 2, zzhy.LONG, 0);
        zzc = zzhvVar3;
        final int i2 = 3;
        zzhv zzhvVar4 = new zzhv("UINT64", 3, zzhy.LONG, 0);
        zzd = zzhvVar4;
        zzhv zzhvVar5 = new zzhv("INT32", 4, zzhy.INT, 0);
        zze = zzhvVar5;
        zzhv zzhvVar6 = new zzhv("FIXED64", 5, zzhy.LONG, 1);
        zzf = zzhvVar6;
        zzhv zzhvVar7 = new zzhv("FIXED32", 6, zzhy.INT, 5);
        zzg = zzhvVar7;
        zzhv zzhvVar8 = new zzhv("BOOL", 7, zzhy.BOOLEAN, 0);
        zzh = zzhvVar8;
        final zzhy zzhyVar = zzhy.STRING;
        final String str = "STRING";
        final int i3 = 8;
        zzhv zzhvVar9 = new zzhv(str, i3, zzhyVar, i) { // from class: com.google.android.gms.internal.mlkit_language_id.zzhu
            {
                int i4 = 2;
                zzhs zzhsVar = null;
                int i5 = 8;
            }
        };
        zzi = zzhvVar9;
        final zzhy zzhyVar2 = zzhy.MESSAGE;
        final String str2 = "GROUP";
        final int i4 = 9;
        zzhv zzhvVar10 = new zzhv(str2, i4, zzhyVar2, i2) { // from class: com.google.android.gms.internal.mlkit_language_id.zzhx
            {
                int i5 = 3;
                zzhs zzhsVar = null;
                int i6 = 9;
            }
        };
        zzj = zzhvVar10;
        final zzhy zzhyVar3 = zzhy.MESSAGE;
        final String str3 = "MESSAGE";
        final int i5 = 10;
        zzhv zzhvVar11 = new zzhv(str3, i5, zzhyVar3, i) { // from class: com.google.android.gms.internal.mlkit_language_id.zzhw
            {
                int i6 = 2;
                zzhs zzhsVar = null;
                int i7 = 10;
            }
        };
        zzk = zzhvVar11;
        final zzhy zzhyVar4 = zzhy.BYTE_STRING;
        final String str4 = "BYTES";
        final int i6 = 11;
        zzhv zzhvVar12 = new zzhv(str4, i6, zzhyVar4, i) { // from class: com.google.android.gms.internal.mlkit_language_id.zzhz
            {
                int i7 = 2;
                zzhs zzhsVar = null;
                int i8 = 11;
            }
        };
        zzl = zzhvVar12;
        zzhv zzhvVar13 = new zzhv("UINT32", 12, zzhy.INT, 0);
        zzm = zzhvVar13;
        zzhv zzhvVar14 = new zzhv("ENUM", 13, zzhy.ENUM, 0);
        zzn = zzhvVar14;
        zzhv zzhvVar15 = new zzhv("SFIXED32", 14, zzhy.INT, 5);
        zzo = zzhvVar15;
        zzhv zzhvVar16 = new zzhv("SFIXED64", 15, zzhy.LONG, 1);
        zzp = zzhvVar16;
        zzhv zzhvVar17 = new zzhv("SINT32", 16, zzhy.INT, 0);
        zzq = zzhvVar17;
        zzhv zzhvVar18 = new zzhv("SINT64", 17, zzhy.LONG, 0);
        zzr = zzhvVar18;
        zzu = new zzhv[]{zzhvVar, zzhvVar2, zzhvVar3, zzhvVar4, zzhvVar5, zzhvVar6, zzhvVar7, zzhvVar8, zzhvVar9, zzhvVar10, zzhvVar11, zzhvVar12, zzhvVar13, zzhvVar14, zzhvVar15, zzhvVar16, zzhvVar17, zzhvVar18};
    }
}
