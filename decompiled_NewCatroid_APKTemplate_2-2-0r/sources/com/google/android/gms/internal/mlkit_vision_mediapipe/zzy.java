package com.google.android.gms.internal.mlkit_vision_mediapipe;

import com.beust.jcommander.Parameters;
import io.netty.util.internal.StringUtil;
import net.bytebuddy.asm.Advice;

/* JADX INFO: compiled from: com.google.mlkit:mediapipe-internal@@16.0.0 */
/* JADX INFO: loaded from: classes9.dex */
public enum zzy {
    STRING(Advice.OffsetMapping.ForOrigin.Renderer.ForJavaSignature.SYMBOL, zzaa.zza, "-#", true),
    BOOLEAN('b', zzaa.zzb, Parameters.DEFAULT_OPTION_PREFIXES, true),
    CHAR('c', zzaa.zzc, Parameters.DEFAULT_OPTION_PREFIXES, true),
    DECIMAL(Advice.OffsetMapping.ForOrigin.Renderer.ForDescriptor.SYMBOL, zzaa.zzd, "-0+ ,(", false),
    OCTAL('o', zzaa.zzd, "-#0(", false),
    HEX('x', zzaa.zzd, "-#0(", true),
    FLOAT('f', zzaa.zze, "-#0+ ,(", false),
    EXPONENT('e', zzaa.zze, "-#0+ (", true),
    GENERAL('g', zzaa.zze, "-0+ ,(", true),
    EXPONENT_HEX('a', zzaa.zze, "-#0+ ", true);

    private static final zzy[] zzk = new zzy[26];
    private final char zzl;
    private final zzaa zzm;
    private final int zzn;
    private final String zzo;

    public static zzy zza(char c) {
        zzy zzyVar = zzk[(c | StringUtil.SPACE) - 97];
        if ((c & StringUtil.SPACE) != 0) {
            return zzyVar;
        }
        if (zzyVar == null || (zzyVar.zzn & 128) == 0) {
            return null;
        }
        return zzyVar;
    }

    zzy(char c, zzaa zzaaVar, String str, boolean z) {
        this.zzl = c;
        this.zzm = zzaaVar;
        this.zzn = zzab.zza(str, z);
        this.zzo = new StringBuilder(2).append("%").append(c).toString();
    }

    public final char zza() {
        return this.zzl;
    }

    public final zzaa zzb() {
        return this.zzm;
    }

    final int zzc() {
        return this.zzn;
    }

    public final String zzd() {
        return this.zzo;
    }

    static {
        for (zzy zzyVar : values()) {
            zzk[(zzyVar.zzl | StringUtil.SPACE) - 97] = zzyVar;
        }
    }
}
