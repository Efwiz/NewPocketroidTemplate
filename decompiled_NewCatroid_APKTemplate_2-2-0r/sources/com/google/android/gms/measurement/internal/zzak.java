package com.google.android.gms.measurement.internal;

import org.eclipse.jdt.internal.compiler.classfmt.ExternalAnnotationProvider;

/* JADX INFO: compiled from: com.google.android.gms:play-services-measurement@@22.0.2 */
/* JADX INFO: loaded from: classes9.dex */
enum zzak {
    UNSET(ExternalAnnotationProvider.NULLABLE),
    REMOTE_DEFAULT(ExternalAnnotationProvider.NONNULL),
    REMOTE_DELEGATION('2'),
    MANIFEST('3'),
    INITIALIZATION('4'),
    API('5'),
    CHILD_ACCOUNT('6'),
    TCF('7'),
    REMOTE_ENFORCED_DEFAULT('8'),
    FAILSAFE('9');

    private final char zzl;

    public static zzak zza(char c) {
        for (zzak zzakVar : values()) {
            if (zzakVar.zzl == c) {
                return zzakVar;
            }
        }
        return UNSET;
    }

    zzak(char c) {
        this.zzl = c;
    }
}
