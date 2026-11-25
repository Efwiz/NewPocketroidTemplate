package com.google.android.gms.internal.mlkit_vision_mediapipe;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import net.bytebuddy.asm.Advice;
import org.bouncycastle.pqc.math.linearalgebra.Matrix;
import org.eclipse.jdt.internal.compiler.util.Util;

/* JADX INFO: compiled from: com.google.mlkit:mediapipe-internal@@16.0.0 */
/* JADX INFO: loaded from: classes9.dex */
public enum zzbx {
    TIME_HOUR_OF_DAY_PADDED('H'),
    TIME_HOUR_OF_DAY('k'),
    TIME_HOUR_12H_PADDED('I'),
    TIME_HOUR_12H('l'),
    TIME_MINUTE_OF_HOUR_PADDED('M'),
    TIME_SECONDS_OF_MINUTE_PADDED(Util.C_SHORT),
    TIME_MILLIS_OF_SECOND_PADDED('L'),
    TIME_NANOS_OF_SECOND_PADDED('N'),
    TIME_AM_PM(Advice.OffsetMapping.ForOrigin.Renderer.ForPropertyName.SYMBOL),
    TIME_TZ_NUMERIC('z'),
    TIME_TZ_SHORT('Z'),
    TIME_EPOCH_SECONDS(Advice.OffsetMapping.ForOrigin.Renderer.ForJavaSignature.SYMBOL),
    TIME_EPOCH_MILLIS(Util.C_UNRESOLVED),
    DATE_MONTH_FULL(Util.C_BYTE),
    DATE_MONTH_SHORT('b'),
    DATE_MONTH_SHORT_ALT('h'),
    DATE_DAY_FULL('A'),
    DATE_DAY_SHORT('a'),
    DATE_CENTURY_PADDED(Util.C_CHAR),
    DATE_YEAR_PADDED('Y'),
    DATE_YEAR_OF_CENTURY_PADDED('y'),
    DATE_DAY_OF_YEAR_PADDED('j'),
    DATE_MONTH_PADDED(Advice.OffsetMapping.ForOrigin.Renderer.ForMethodName.SYMBOL),
    DATE_DAY_OF_MONTH_PADDED(Advice.OffsetMapping.ForOrigin.Renderer.ForDescriptor.SYMBOL),
    DATE_DAY_OF_MONTH('e'),
    DATETIME_HOURS_MINUTES(Matrix.MATRIX_TYPE_RANDOM_REGULAR),
    DATETIME_HOURS_MINUTES_SECONDS(Util.C_TYPE_VARIABLE),
    DATETIME_HOURS_MINUTES_SECONDS_12H(Advice.OffsetMapping.ForOrigin.Renderer.ForReturnTypeName.SYMBOL),
    DATETIME_MONTH_DAY_YEAR(Util.C_DOUBLE),
    DATETIME_YEAR_MONTH_DAY(Util.C_FLOAT),
    DATETIME_FULL('c');

    private static final Map<Character, zzbx> zzaf;
    private final char zzag;

    public static final zzbx zza(char c) {
        return zzaf.get(Character.valueOf(c));
    }

    zzbx(char c) {
        this.zzag = c;
    }

    public final char zza() {
        return this.zzag;
    }

    static {
        HashMap map = new HashMap();
        for (zzbx zzbxVar : values()) {
            if (map.put(Character.valueOf(zzbxVar.zzag), zzbxVar) != null) {
                String strValueOf = String.valueOf(zzbxVar);
                throw new IllegalStateException(new StringBuilder(String.valueOf(strValueOf).length() + 28).append("duplicate format character: ").append(strValueOf).toString());
            }
        }
        zzaf = Collections.unmodifiableMap(map);
    }
}
