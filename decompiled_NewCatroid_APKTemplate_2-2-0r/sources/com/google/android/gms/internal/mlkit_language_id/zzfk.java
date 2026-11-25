package com.google.android.gms.internal.mlkit_language_id;

/* JADX INFO: compiled from: com.google.mlkit:language-id@@16.1.1 */
/* JADX INFO: loaded from: classes9.dex */
final class zzfk extends zzfj {
    private zzfk() {
        super();
    }

    @Override // com.google.android.gms.internal.mlkit_language_id.zzfj
    final void zza(Object obj, long j) {
        zzb(obj, j).b_();
    }

    @Override // com.google.android.gms.internal.mlkit_language_id.zzfj
    final <E> void zza(Object obj, Object obj2, long j) {
        zzew zzewVarZzb = zzb(obj, j);
        zzew zzewVarZzb2 = zzb(obj2, j);
        int size = zzewVarZzb.size();
        int size2 = zzewVarZzb2.size();
        if (size > 0 && size2 > 0) {
            if (!zzewVarZzb.zza()) {
                zzewVarZzb = zzewVarZzb.zzb(size2 + size);
            }
            zzewVarZzb.addAll(zzewVarZzb2);
        }
        if (size > 0) {
            zzewVarZzb2 = zzewVarZzb;
        }
        zzhn.zza(obj, j, zzewVarZzb2);
    }

    private static <E> zzew<E> zzb(Object obj, long j) {
        return (zzew) zzhn.zzf(obj, j);
    }
}
