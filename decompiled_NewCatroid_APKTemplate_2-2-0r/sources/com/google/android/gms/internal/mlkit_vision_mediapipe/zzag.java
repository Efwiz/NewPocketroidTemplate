package com.google.android.gms.internal.mlkit_vision_mediapipe;

import io.netty.util.internal.StringUtil;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import net.bytebuddy.asm.Advice;
import okio.Utf8;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

/* JADX INFO: compiled from: com.google.mlkit:mediapipe-internal@@16.0.0 */
/* JADX INFO: loaded from: classes9.dex */
final class zzag implements zzaj {
    private static final Set<Class<?>> zza = new HashSet(Arrays.asList(Boolean.class, Byte.class, Short.class, Integer.class, Long.class, Float.class, Double.class));
    private final String zzb;
    private final String zzc;
    private final StringBuilder zzd;
    private boolean zze = false;

    zzag(String str, String str2, StringBuilder sb) {
        this.zzb = str;
        this.zzc = str2;
        this.zzd = sb;
    }

    public final void zza() {
        if (this.zze) {
            this.zzd.append(this.zzc);
        }
    }

    private static int zza(String str, int i) {
        while (i < str.length()) {
            char cCharAt = str.charAt(i);
            if (cCharAt < ' ' || cCharAt == '\"' || cCharAt == '\\') {
                return i;
            }
            i++;
        }
        return -1;
    }

    @Override // com.google.android.gms.internal.mlkit_vision_mediapipe.zzaj
    public final /* synthetic */ zzaj zza(String str, @NullableDecl Object obj) {
        boolean z = this.zze;
        char c = StringUtil.SPACE;
        if (z) {
            this.zzd.append(StringUtil.SPACE);
        } else {
            if (this.zzd.length() > 0) {
                StringBuilder sb = this.zzd;
                if (sb.length() > 1000 || this.zzd.indexOf("\n") != -1) {
                    c = '\n';
                }
                sb.append(c);
            }
            this.zzd.append(this.zzb);
            this.zze = true;
        }
        this.zzd.append(str).append('=');
        if (obj == null) {
            this.zzd.append(true);
        } else if (zza.contains(obj.getClass())) {
            this.zzd.append(obj);
        } else {
            this.zzd.append('\"');
            StringBuilder sb2 = this.zzd;
            String string = obj.toString();
            int i = 0;
            int iZza = zza(string, 0);
            while (iZza != -1) {
                sb2.append((CharSequence) string, i, iZza);
                i = iZza + 1;
                char cCharAt = string.charAt(iZza);
                if (cCharAt == '\t') {
                    cCharAt = Advice.OffsetMapping.ForOrigin.Renderer.ForTypeName.SYMBOL;
                } else if (cCharAt == '\n') {
                    cCharAt = 'n';
                } else if (cCharAt != '\r') {
                    if (cCharAt != '\"' && cCharAt != '\\') {
                        sb2.append(Utf8.REPLACEMENT_CHARACTER);
                    }
                    iZza = zza(string, i);
                } else {
                    cCharAt = Advice.OffsetMapping.ForOrigin.Renderer.ForReturnTypeName.SYMBOL;
                }
                sb2.append("\\").append(cCharAt);
                iZza = zza(string, i);
            }
            sb2.append((CharSequence) string, i, string.length());
            this.zzd.append('\"');
        }
        return this;
    }
}
