package com.google.android.gms.internal.mlkit_vision_mediapipe;

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
/* JADX INFO: compiled from: com.google.mlkit:mediapipe-internal@@16.0.0 */
/* JADX INFO: loaded from: classes9.dex */
public class zzig {
    public static final zzig zza;
    public static final zzig zzb;
    public static final zzig zzc;
    public static final zzig zzd;
    public static final zzig zze;
    public static final zzig zzf;
    public static final zzig zzg;
    public static final zzig zzh;
    public static final zzig zzi;
    public static final zzig zzj;
    public static final zzig zzk;
    public static final zzig zzl;
    public static final zzig zzm;
    public static final zzig zzn;
    public static final zzig zzo;
    public static final zzig zzp;
    public static final zzig zzq;
    public static final zzig zzr;
    private static final /* synthetic */ zzig[] zzu;
    private final zzij zzs;
    private final int zzt;

    public static zzig[] values() {
        return (zzig[]) zzu.clone();
    }

    private zzig(String str, int i, zzij zzijVar, int i2) {
        super(str, i);
        this.zzs = zzijVar;
        this.zzt = i2;
    }

    public final zzij zza() {
        return this.zzs;
    }

    public final int zzb() {
        return this.zzt;
    }

    /* synthetic */ zzig(String str, int i, zzij zzijVar, int i2, zzid zzidVar) {
        this(str, i, zzijVar, i2);
    }

    static {
        zzig zzigVar = new zzig("DOUBLE", 0, zzij.DOUBLE, 1);
        zza = zzigVar;
        zzig zzigVar2 = new zzig("FLOAT", 1, zzij.FLOAT, 5);
        zzb = zzigVar2;
        final int i = 2;
        zzig zzigVar3 = new zzig("INT64", 2, zzij.LONG, 0);
        zzc = zzigVar3;
        final int i2 = 3;
        zzig zzigVar4 = new zzig("UINT64", 3, zzij.LONG, 0);
        zzd = zzigVar4;
        zzig zzigVar5 = new zzig("INT32", 4, zzij.INT, 0);
        zze = zzigVar5;
        zzig zzigVar6 = new zzig("FIXED64", 5, zzij.LONG, 1);
        zzf = zzigVar6;
        zzig zzigVar7 = new zzig("FIXED32", 6, zzij.INT, 5);
        zzg = zzigVar7;
        zzig zzigVar8 = new zzig("BOOL", 7, zzij.BOOLEAN, 0);
        zzh = zzigVar8;
        final zzij zzijVar = zzij.STRING;
        final String str = "STRING";
        final int i3 = 8;
        zzig zzigVar9 = new zzig(str, i3, zzijVar, i) { // from class: com.google.android.gms.internal.mlkit_vision_mediapipe.zzif
            {
                int i4 = 2;
                zzid zzidVar = null;
                int i5 = 8;
            }
        };
        zzi = zzigVar9;
        final zzij zzijVar2 = zzij.MESSAGE;
        final String str2 = "GROUP";
        final int i4 = 9;
        zzig zzigVar10 = new zzig(str2, i4, zzijVar2, i2) { // from class: com.google.android.gms.internal.mlkit_vision_mediapipe.zzii
            {
                int i5 = 3;
                zzid zzidVar = null;
                int i6 = 9;
            }
        };
        zzj = zzigVar10;
        final zzij zzijVar3 = zzij.MESSAGE;
        final String str3 = "MESSAGE";
        final int i5 = 10;
        zzig zzigVar11 = new zzig(str3, i5, zzijVar3, i) { // from class: com.google.android.gms.internal.mlkit_vision_mediapipe.zzih
            {
                int i6 = 2;
                zzid zzidVar = null;
                int i7 = 10;
            }
        };
        zzk = zzigVar11;
        final zzij zzijVar4 = zzij.BYTE_STRING;
        final String str4 = "BYTES";
        final int i6 = 11;
        zzig zzigVar12 = new zzig(str4, i6, zzijVar4, i) { // from class: com.google.android.gms.internal.mlkit_vision_mediapipe.zzik
            {
                int i7 = 2;
                zzid zzidVar = null;
                int i8 = 11;
            }
        };
        zzl = zzigVar12;
        zzig zzigVar13 = new zzig("UINT32", 12, zzij.INT, 0);
        zzm = zzigVar13;
        zzig zzigVar14 = new zzig("ENUM", 13, zzij.ENUM, 0);
        zzn = zzigVar14;
        zzig zzigVar15 = new zzig("SFIXED32", 14, zzij.INT, 5);
        zzo = zzigVar15;
        zzig zzigVar16 = new zzig("SFIXED64", 15, zzij.LONG, 1);
        zzp = zzigVar16;
        zzig zzigVar17 = new zzig("SINT32", 16, zzij.INT, 0);
        zzq = zzigVar17;
        zzig zzigVar18 = new zzig("SINT64", 17, zzij.LONG, 0);
        zzr = zzigVar18;
        zzu = new zzig[]{zzigVar, zzigVar2, zzigVar3, zzigVar4, zzigVar5, zzigVar6, zzigVar7, zzigVar8, zzigVar9, zzigVar10, zzigVar11, zzigVar12, zzigVar13, zzigVar14, zzigVar15, zzigVar16, zzigVar17, zzigVar18};
    }
}
