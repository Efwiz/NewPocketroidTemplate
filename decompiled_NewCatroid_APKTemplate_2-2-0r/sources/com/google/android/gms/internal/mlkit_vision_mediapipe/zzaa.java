package com.google.android.gms.internal.mlkit_vision_mediapipe;

import java.math.BigDecimal;
import java.math.BigInteger;

/* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
/* JADX WARN: Unknown enum class pattern. Please report as an issue! */
/* JADX INFO: compiled from: com.google.mlkit:mediapipe-internal@@16.0.0 */
/* JADX INFO: loaded from: classes9.dex */
public abstract class zzaa {
    public static final zzaa zza;
    public static final zzaa zzb;
    public static final zzaa zzc;
    public static final zzaa zzd;
    public static final zzaa zze;
    private static final /* synthetic */ zzaa[] zzh;
    private final boolean zzf;
    private final boolean zzg;

    public static zzaa[] values() {
        return (zzaa[]) zzh.clone();
    }

    public abstract boolean zza(Object obj);

    private zzaa(String str, int i, boolean z, boolean z2) {
        super(str, i);
        this.zzf = z;
        this.zzg = z2;
    }

    final boolean zza() {
        return this.zzg;
    }

    /* synthetic */ zzaa(String str, int i, boolean z, boolean z2, zzad zzadVar) {
        this(str, i, z, z2);
    }

    static {
        final boolean z = false;
        final int i = 1;
        zzad zzadVar = new zzad("GENERAL", 0, false, true);
        zza = zzadVar;
        final String str = "BOOLEAN";
        zzaa zzaaVar = new zzaa(str, i, z, z) { // from class: com.google.android.gms.internal.mlkit_vision_mediapipe.zzac
            {
                boolean z2 = false;
                zzad zzadVar2 = null;
                int i2 = 1;
                boolean z3 = false;
            }

            @Override // com.google.android.gms.internal.mlkit_vision_mediapipe.zzaa
            public final boolean zza(Object obj) {
                return obj instanceof Boolean;
            }
        };
        zzb = zzaaVar;
        final String str2 = "CHARACTER";
        final int i2 = 2;
        zzaa zzaaVar2 = new zzaa(str2, i2, z, z) { // from class: com.google.android.gms.internal.mlkit_vision_mediapipe.zzaf
            {
                boolean z2 = false;
                zzad zzadVar2 = null;
                int i3 = 2;
                boolean z3 = false;
            }

            @Override // com.google.android.gms.internal.mlkit_vision_mediapipe.zzaa
            public final boolean zza(Object obj) {
                if (obj instanceof Character) {
                    return true;
                }
                if ((obj instanceof Integer) || (obj instanceof Byte) || (obj instanceof Short)) {
                    return Character.isValidCodePoint(((Number) obj).intValue());
                }
                return false;
            }
        };
        zzc = zzaaVar2;
        final String str3 = "INTEGRAL";
        final int i3 = 3;
        final char c = 1 == true ? 1 : 0;
        zzaa zzaaVar3 = new zzaa(str3, i3, c, z) { // from class: com.google.android.gms.internal.mlkit_vision_mediapipe.zzae
            {
                boolean z2 = false;
                zzad zzadVar2 = null;
                int i4 = 3;
                boolean z3 = true;
            }

            @Override // com.google.android.gms.internal.mlkit_vision_mediapipe.zzaa
            public final boolean zza(Object obj) {
                return (obj instanceof Integer) || (obj instanceof Long) || (obj instanceof Byte) || (obj instanceof Short) || (obj instanceof BigInteger);
            }
        };
        zzd = zzaaVar3;
        final String str4 = "FLOAT";
        final int i4 = 4;
        final char c2 = 1 == true ? 1 : 0;
        final char c3 = 1 == true ? 1 : 0;
        zzaa zzaaVar4 = new zzaa(str4, i4, c2, c3) { // from class: com.google.android.gms.internal.mlkit_vision_mediapipe.zzah
            {
                boolean z2 = true;
                zzad zzadVar2 = null;
                int i5 = 4;
                boolean z3 = true;
            }

            @Override // com.google.android.gms.internal.mlkit_vision_mediapipe.zzaa
            public final boolean zza(Object obj) {
                return (obj instanceof Double) || (obj instanceof Float) || (obj instanceof BigDecimal);
            }
        };
        zze = zzaaVar4;
        zzh = new zzaa[]{zzadVar, zzaaVar, zzaaVar2, zzaaVar3, zzaaVar4};
    }
}
