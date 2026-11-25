package com.google.android.gms.internal.mlkit_vision_mediapipe;

import java.util.Collections;
import java.util.Comparator;
import java.util.SortedMap;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.TreeSet;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

/* JADX INFO: compiled from: com.google.mlkit:mediapipe-internal@@16.0.0 */
/* JADX INFO: loaded from: classes9.dex */
public final class zzbp {
    private static final Comparator<Object> zza = new zzbr();
    private static final SortedSet<Object> zzb = Collections.unmodifiableSortedSet(new TreeSet());
    private static final zzbp zzc = new zzbp(new TreeMap());
    private final SortedMap<String, SortedSet<Object>> zzd;
    private Integer zze = null;
    private String zzf = null;

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
    /* JADX WARN: Unknown enum class pattern. Please report as an issue! */
    /* JADX INFO: compiled from: com.google.mlkit:mediapipe-internal@@16.0.0 */
    static abstract class zza {
        private static final zza zza;
        private static final zza zzb;
        private static final zza zzc;
        private static final zza zzd;
        private static final /* synthetic */ zza[] zze;

        public static zza[] values() {
            return (zza[]) zze.clone();
        }

        abstract int zza(Object obj, Object obj2);

        private zza(String str, int i) {
            super(str, i);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static zza zzb(Object obj) {
            if (obj instanceof String) {
                return zzb;
            }
            if (obj instanceof Boolean) {
                return zza;
            }
            if (obj instanceof Long) {
                return zzc;
            }
            if (obj instanceof Double) {
                return zzd;
            }
            String strValueOf = String.valueOf(obj.getClass());
            throw new AssertionError(new StringBuilder(String.valueOf(strValueOf).length() + 18).append("invalid tag type: ").append(strValueOf).toString());
        }

        /* synthetic */ zza(String str, int i, zzbr zzbrVar) {
            this(str, i);
        }

        static {
            zzbt zzbtVar = new zzbt("BOOLEAN", 0);
            zza = zzbtVar;
            zzbs zzbsVar = new zzbs("STRING", 1);
            zzb = zzbsVar;
            zzbv zzbvVar = new zzbv("LONG", 2);
            zzc = zzbvVar;
            zzbu zzbuVar = new zzbu("DOUBLE", 3);
            zzd = zzbuVar;
            zze = new zza[]{zzbtVar, zzbsVar, zzbvVar, zzbuVar};
        }
    }

    public static zzbp zza() {
        return zzc;
    }

    private zzbp(SortedMap<String, SortedSet<Object>> sortedMap) {
        this.zzd = Collections.unmodifiableSortedMap(sortedMap);
    }

    public final SortedMap<String, SortedSet<Object>> zzb() {
        return this.zzd;
    }

    public final boolean zzc() {
        return this.zzd.isEmpty();
    }

    public final boolean equals(@NullableDecl Object obj) {
        return (obj instanceof zzbp) && ((zzbp) obj).zzd.equals(this.zzd);
    }

    public final int hashCode() {
        if (this.zze == null) {
            this.zze = Integer.valueOf(this.zzd.hashCode());
        }
        return this.zze.intValue();
    }

    public final String toString() {
        if (this.zzf == null) {
            this.zzf = this.zzd.toString();
        }
        return this.zzf;
    }
}
