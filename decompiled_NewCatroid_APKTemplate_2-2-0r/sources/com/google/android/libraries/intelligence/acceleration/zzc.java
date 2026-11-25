package com.google.android.libraries.intelligence.acceleration;

import com.google.android.gms.internal.mlkit_vision_internal_vkp.zzaoe;

/* JADX INFO: compiled from: com.google.mlkit:vision-internal-vkp@@18.2.0 */
/* JADX INFO: loaded from: classes9.dex */
final class zzc extends RuntimeException {
    public zzc(String str, zzaoe zzaoeVar) {
        StringBuilder sb = new StringBuilder(String.valueOf(str).length() + 34);
        sb.append("Unable to parse ");
        sb.append(str);
        sb.append(" protocol message.");
        super(sb.toString(), zzaoeVar);
    }
}
