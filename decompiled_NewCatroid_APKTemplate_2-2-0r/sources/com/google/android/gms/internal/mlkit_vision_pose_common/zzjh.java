package com.google.android.gms.internal.mlkit_vision_pose_common;

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
/* JADX INFO: compiled from: com.google.mlkit:pose-detection-common@@17.0.0 */
/* JADX INFO: loaded from: classes9.dex */
public class zzjh {
    public static final zzjh zza;
    public static final zzjh zzb;
    public static final zzjh zzc;
    public static final zzjh zzd;
    public static final zzjh zze;
    public static final zzjh zzf;
    public static final zzjh zzg;
    public static final zzjh zzh;
    public static final zzjh zzi;
    public static final zzjh zzj;
    public static final zzjh zzk;
    public static final zzjh zzl;
    public static final zzjh zzm;
    public static final zzjh zzn;
    public static final zzjh zzo;
    public static final zzjh zzp;
    public static final zzjh zzq;
    public static final zzjh zzr;
    private static final /* synthetic */ zzjh[] zzu;
    private final zzjo zzs;
    private final int zzt;

    public static zzjh[] values() {
        return (zzjh[]) zzu.clone();
    }

    private zzjh(String str, int i, zzjo zzjoVar, int i2) {
        super(str, i);
        this.zzs = zzjoVar;
        this.zzt = i2;
    }

    public final zzjo zza() {
        return this.zzs;
    }

    public final int zzb() {
        return this.zzt;
    }

    /* synthetic */ zzjh(String str, int i, zzjo zzjoVar, int i2, zzji zzjiVar) {
        this(str, i, zzjoVar, i2);
    }

    static {
        zzjh zzjhVar = new zzjh("DOUBLE", 0, zzjo.DOUBLE, 1);
        zza = zzjhVar;
        zzjh zzjhVar2 = new zzjh("FLOAT", 1, zzjo.FLOAT, 5);
        zzb = zzjhVar2;
        final int i = 2;
        zzjh zzjhVar3 = new zzjh("INT64", 2, zzjo.LONG, 0);
        zzc = zzjhVar3;
        final int i2 = 3;
        zzjh zzjhVar4 = new zzjh("UINT64", 3, zzjo.LONG, 0);
        zzd = zzjhVar4;
        zzjh zzjhVar5 = new zzjh("INT32", 4, zzjo.INT, 0);
        zze = zzjhVar5;
        zzjh zzjhVar6 = new zzjh("FIXED64", 5, zzjo.LONG, 1);
        zzf = zzjhVar6;
        zzjh zzjhVar7 = new zzjh("FIXED32", 6, zzjo.INT, 5);
        zzg = zzjhVar7;
        zzjh zzjhVar8 = new zzjh("BOOL", 7, zzjo.BOOLEAN, 0);
        zzh = zzjhVar8;
        final zzjo zzjoVar = zzjo.STRING;
        final String str = "STRING";
        final int i3 = 8;
        zzjh zzjhVar9 = new zzjh(str, i3, zzjoVar, i) { // from class: com.google.android.gms.internal.mlkit_vision_pose_common.zzjk
            {
                int i4 = 2;
                zzji zzjiVar = null;
                int i5 = 8;
            }
        };
        zzi = zzjhVar9;
        final zzjo zzjoVar2 = zzjo.MESSAGE;
        final String str2 = "GROUP";
        final int i4 = 9;
        zzjh zzjhVar10 = new zzjh(str2, i4, zzjoVar2, i2) { // from class: com.google.android.gms.internal.mlkit_vision_pose_common.zzjj
            {
                int i5 = 3;
                zzji zzjiVar = null;
                int i6 = 9;
            }
        };
        zzj = zzjhVar10;
        final zzjo zzjoVar3 = zzjo.MESSAGE;
        final String str3 = "MESSAGE";
        final int i5 = 10;
        zzjh zzjhVar11 = new zzjh(str3, i5, zzjoVar3, i) { // from class: com.google.android.gms.internal.mlkit_vision_pose_common.zzjm
            {
                int i6 = 2;
                zzji zzjiVar = null;
                int i7 = 10;
            }
        };
        zzk = zzjhVar11;
        final zzjo zzjoVar4 = zzjo.BYTE_STRING;
        final String str4 = "BYTES";
        final int i6 = 11;
        zzjh zzjhVar12 = new zzjh(str4, i6, zzjoVar4, i) { // from class: com.google.android.gms.internal.mlkit_vision_pose_common.zzjl
            {
                int i7 = 2;
                zzji zzjiVar = null;
                int i8 = 11;
            }
        };
        zzl = zzjhVar12;
        zzjh zzjhVar13 = new zzjh("UINT32", 12, zzjo.INT, 0);
        zzm = zzjhVar13;
        zzjh zzjhVar14 = new zzjh("ENUM", 13, zzjo.ENUM, 0);
        zzn = zzjhVar14;
        zzjh zzjhVar15 = new zzjh("SFIXED32", 14, zzjo.INT, 5);
        zzo = zzjhVar15;
        zzjh zzjhVar16 = new zzjh("SFIXED64", 15, zzjo.LONG, 1);
        zzp = zzjhVar16;
        zzjh zzjhVar17 = new zzjh("SINT32", 16, zzjo.INT, 0);
        zzq = zzjhVar17;
        zzjh zzjhVar18 = new zzjh("SINT64", 17, zzjo.LONG, 0);
        zzr = zzjhVar18;
        zzu = new zzjh[]{zzjhVar, zzjhVar2, zzjhVar3, zzjhVar4, zzjhVar5, zzjhVar6, zzjhVar7, zzjhVar8, zzjhVar9, zzjhVar10, zzjhVar11, zzjhVar12, zzjhVar13, zzjhVar14, zzjhVar15, zzjhVar16, zzjhVar17, zzjhVar18};
    }
}
