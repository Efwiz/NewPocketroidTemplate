package com.google.android.gms.internal.mlkit_vision_mediapipe;

import io.netty.util.internal.StringUtil;
import java.io.IOException;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Formattable;
import java.util.Formatter;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.SortedSet;
import net.bytebuddy.asm.Advice;
import okhttp3.internal.ws.WebSocketProtocol;
import org.eclipse.jdt.internal.compiler.util.Util;
import org.jetbrains.kotlin.cli.common.arguments.PreprocessCommandLineArgumentsKt;

/* JADX INFO: compiled from: com.google.mlkit:mediapipe-internal@@16.0.0 */
/* JADX INFO: loaded from: classes9.dex */
public final class zzat extends zzcc<StringBuilder> implements zzby {
    private static final Locale zza = Locale.ROOT;
    private static final zzau zzb = new zzas();
    private final Object[] zzc;
    private final StringBuilder zzd;
    private int zze;

    public static void zza(zzai zzaiVar, zzaw zzawVar, int i, zzau zzauVar) {
        String string;
        zzan zzanVarZzk = zzaiVar.zzk();
        Throwable th = (Throwable) zzanVarZzk.zza(zzk.zza.zza);
        boolean z = false;
        int i2 = 0;
        while (true) {
            if (i2 >= zzanVarZzk.zza()) {
                z = true;
                break;
            } else if (zza(zzanVarZzk.zza(i2), zzauVar)) {
                break;
            } else {
                i2++;
            }
        }
        if (zzaiVar.zzg() == null) {
            string = zza(zzaiVar.zzi());
            if (i != zzax.zza || !z) {
                StringBuilder sb = new StringBuilder(string);
                if (i == zzax.zzb) {
                    zza(sb, zzaiVar.zzf());
                }
                if (!z) {
                    zza(sb, zzaiVar.zzk(), zzauVar);
                }
                string = sb.toString();
            }
        } else {
            zzat zzatVar = new zzat(zzaiVar.zzg(), zzaiVar.zzh());
            StringBuilder sbZzg = zzatVar.zzg();
            if (zzaiVar.zzh().length > zzatVar.zzf()) {
                sbZzg.append(" [ERROR: UNUSED LOG ARGUMENTS]");
            }
            if (i == zzax.zzb) {
                zza(sbZzg, zzaiVar.zzf());
            }
            if (!z) {
                zza(sbZzg, zzanVarZzk, zzauVar);
            }
            string = sbZzg.toString();
        }
        zzawVar.zza(zzaiVar.zzd(), string, th);
    }

    public static String zza(Object obj) {
        if (obj != null) {
            try {
                if (!obj.getClass().isArray()) {
                    return String.valueOf(obj);
                }
                if (obj instanceof int[]) {
                    return Arrays.toString((int[]) obj);
                }
                if (obj instanceof long[]) {
                    return Arrays.toString((long[]) obj);
                }
                if (obj instanceof byte[]) {
                    return Arrays.toString((byte[]) obj);
                }
                if (obj instanceof char[]) {
                    return Arrays.toString((char[]) obj);
                }
                if (obj instanceof short[]) {
                    return Arrays.toString((short[]) obj);
                }
                if (obj instanceof float[]) {
                    return Arrays.toString((float[]) obj);
                }
                if (obj instanceof double[]) {
                    return Arrays.toString((double[]) obj);
                }
                if (obj instanceof boolean[]) {
                    return Arrays.toString((boolean[]) obj);
                }
                return Arrays.toString((Object[]) obj);
            } catch (RuntimeException e) {
                return zza(obj, e);
            }
        }
        return "null";
    }

    private static String zza(Object obj, RuntimeException runtimeException) {
        String simpleName;
        try {
            simpleName = runtimeException.toString();
        } catch (RuntimeException e) {
            simpleName = e.getClass().getSimpleName();
        }
        String name2 = obj.getClass().getName();
        return new StringBuilder(String.valueOf(name2).length() + 16 + String.valueOf(simpleName).length()).append("{").append(name2).append(PreprocessCommandLineArgumentsKt.ARGFILE_ARGUMENT).append(System.identityHashCode(obj)).append(": ").append(simpleName).append("}").toString();
    }

    private static void zza(StringBuilder sb, zzan zzanVar, zzau zzauVar) {
        zzag zzagVar = new zzag("[CONTEXT ", " ]", sb);
        zzbp zzbpVarZza = null;
        for (int i = 0; i < zzanVar.zza(); i++) {
            zzx<?> zzxVarZza = zzanVar.zza(i);
            if (zza(zzxVarZza, zzauVar)) {
                if (zzxVarZza.equals(zzk.zza.zzf)) {
                    zzbpVarZza = zzk.zza.zzf.zza(zzanVar.zzb(i));
                } else {
                    zzxVarZza.zza(zzanVar.zzb(i), zzagVar);
                }
            }
        }
        if (zzbpVarZza != null) {
            for (Map.Entry<String, SortedSet<Object>> entry : zzbpVarZza.zzb().entrySet()) {
                String key = entry.getKey();
                SortedSet<Object> value = entry.getValue();
                if (!value.isEmpty()) {
                    Iterator<Object> it = value.iterator();
                    while (it.hasNext()) {
                        zzagVar.zza(key, it.next());
                    }
                } else {
                    zzagVar.zza(key, (Object) null);
                }
            }
        }
        zzagVar.zza();
    }

    private static boolean zza(zzx<?> zzxVar, zzau zzauVar) {
        return !zzxVar.equals(zzk.zza.zza) && zzauVar.zza(zzxVar);
    }

    private zzat(zzaz zzazVar, Object[] objArr) {
        super(zzazVar);
        this.zzd = new StringBuilder();
        this.zze = 0;
        this.zzc = (Object[]) zzcj.zza(objArr, "log arguments");
    }

    @Override // com.google.android.gms.internal.mlkit_vision_mediapipe.zzcc
    public final void zza(int i, int i2, zzbz zzbzVar) {
        zzd().zza(this.zzd, zze(), this.zze, i);
        zzbzVar.zza((zzby) this, this.zzc);
        this.zze = i2;
    }

    @Override // com.google.android.gms.internal.mlkit_vision_mediapipe.zzby
    public final void zza(Object obj, zzy zzyVar, zzab zzabVar) {
        if (zzyVar.zzb().zza(obj)) {
            StringBuilder sb = this.zzd;
            int i = zzav.zza[zzyVar.ordinal()];
            if (i != 1) {
                if (i == 2 || i == 3) {
                    if (zzabVar.zzb()) {
                        sb.append(obj);
                        return;
                    }
                } else if (i == 4) {
                    if (zzabVar.zza(128, false, false).equals(zzabVar)) {
                        Number number = (Number) obj;
                        boolean zZzf = zzabVar.zzf();
                        long jLongValue = number.longValue();
                        if (number instanceof Long) {
                            zza(sb, jLongValue, zZzf);
                            return;
                        }
                        if (number instanceof Integer) {
                            zza(sb, jLongValue & 4294967295L, zZzf);
                            return;
                        }
                        if (number instanceof Byte) {
                            zza(sb, jLongValue & 255, zZzf);
                            return;
                        }
                        if (number instanceof Short) {
                            zza(sb, jLongValue & WebSocketProtocol.PAYLOAD_SHORT_MAX, zZzf);
                            return;
                        }
                        if (number instanceof BigInteger) {
                            String string = ((BigInteger) number).toString(16);
                            if (zZzf) {
                                string = string.toUpperCase(zza);
                            }
                            sb.append(string);
                            return;
                        }
                        String strValueOf = String.valueOf(number.getClass());
                        throw new RuntimeException(new StringBuilder(String.valueOf(strValueOf).length() + 25).append("unsupported number type: ").append(strValueOf).toString());
                    }
                } else if (i == 5 && zzabVar.zzb()) {
                    if (obj instanceof Character) {
                        sb.append(obj);
                        return;
                    }
                    int iIntValue = ((Number) obj).intValue();
                    if (Character.isBmpCodePoint(iIntValue)) {
                        sb.append((char) iIntValue);
                        return;
                    } else {
                        sb.append(Character.toChars(iIntValue));
                        return;
                    }
                }
            } else if (!(obj instanceof Formattable)) {
                if (zzabVar.zzb()) {
                    sb.append(zza(obj));
                    return;
                }
            } else {
                Formattable formattable = (Formattable) obj;
                int iZze = zzabVar.zze();
                int i2 = iZze & 162;
                if (i2 != 0) {
                    i2 = ((iZze & 128) != 0 ? 2 : 0) | ((iZze & 32) == 0 ? 0 : 1) | ((iZze & 2) == 0 ? 0 : 4);
                }
                int length = sb.length();
                Formatter formatter = new Formatter(sb, zza);
                try {
                    formattable.formatTo(formatter, i2, zzabVar.zzc(), zzabVar.zzd());
                    return;
                } catch (RuntimeException e) {
                    sb.setLength(length);
                    try {
                        formatter.out().append(zza(formattable, e));
                        return;
                    } catch (IOException unused) {
                        return;
                    }
                }
            }
            String strZzd = zzyVar.zzd();
            if (!zzabVar.zzb()) {
                char cZza = zzyVar.zza();
                if (zzabVar.zzf()) {
                    cZza = (char) (cZza & 65503);
                }
                strZzd = zzabVar.zza(new StringBuilder("%")).append(cZza).toString();
            }
            sb.append(String.format(zza, strZzd, obj));
            return;
        }
        zza(this.zzd, obj, zzyVar.zzd());
    }

    @Override // com.google.android.gms.internal.mlkit_vision_mediapipe.zzby
    public final void zza(Object obj, zzbx zzbxVar, zzab zzabVar) {
        if ((obj instanceof Date) || (obj instanceof Calendar) || (obj instanceof Long)) {
            this.zzd.append(String.format(zza, zzabVar.zza(new StringBuilder("%")).append(zzabVar.zzf() ? Util.C_TYPE_VARIABLE : Advice.OffsetMapping.ForOrigin.Renderer.ForTypeName.SYMBOL).append(zzbxVar.zza()).toString(), obj));
        } else {
            zza(this.zzd, obj, new StringBuilder(3).append("%t").append(zzbxVar.zza()).toString());
        }
    }

    @Override // com.google.android.gms.internal.mlkit_vision_mediapipe.zzby
    public final void zza() {
        this.zzd.append("[ERROR: MISSING LOG ARGUMENT]");
    }

    @Override // com.google.android.gms.internal.mlkit_vision_mediapipe.zzby
    public final void zzb() {
        this.zzd.append("null");
    }

    private static void zza(StringBuilder sb, long j, boolean z) {
        if (j == 0) {
            sb.append("0");
            return;
        }
        String str = z ? "0123456789ABCDEF" : "0123456789abcdef";
        for (int iNumberOfLeadingZeros = (63 - Long.numberOfLeadingZeros(j)) & (-4); iNumberOfLeadingZeros >= 0; iNumberOfLeadingZeros -= 4) {
            sb.append(str.charAt((int) ((j >>> iNumberOfLeadingZeros) & 15)));
        }
    }

    private static void zza(StringBuilder sb, Object obj, String str) {
        sb.append("[INVALID: format=").append(str).append(", type=").append(obj.getClass().getCanonicalName()).append(", value=").append(zza(obj)).append("]");
    }

    private static void zza(StringBuilder sb, zzo zzoVar) {
        if (zzoVar == zzo.zza) {
            return;
        }
        int length = sb.length();
        sb.insert(0, zzoVar.zza());
        sb.insert(sb.length() - length, '.');
        sb.insert(sb.length() - length, zzoVar.zzb());
        sb.insert(sb.length() - length, ':');
        sb.insert(sb.length() - length, zzoVar.zzc());
        sb.insert(sb.length() - length, StringUtil.SPACE);
    }

    @Override // com.google.android.gms.internal.mlkit_vision_mediapipe.zzcc
    public final /* synthetic */ StringBuilder zzc() {
        zzd().zza(this.zzd, zze(), this.zze, zze().length());
        return this.zzd;
    }
}
