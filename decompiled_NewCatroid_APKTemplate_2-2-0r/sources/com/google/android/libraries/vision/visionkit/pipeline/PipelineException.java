package com.google.android.libraries.vision.visionkit.pipeline;

import com.google.android.gms.internal.mlkit_vision_internal_vkp.zzanc;
import com.google.android.gms.internal.mlkit_vision_internal_vkp.zzaoe;
import com.google.android.gms.internal.mlkit_vision_internal_vkp.zzkg;
import com.google.android.gms.internal.mlkit_vision_internal_vkp.zzkp;
import com.google.android.gms.internal.mlkit_vision_internal_vkp.zzkz;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

/* JADX INFO: compiled from: com.google.mlkit:vision-internal-vkp@@18.2.0 */
/* JADX INFO: loaded from: classes9.dex */
public class PipelineException extends Exception {
    private static final String ROOT_CAUSE_DELIMITER = "#vk ";
    private final zzch statusCode;
    private final String statusMessage;
    private final zzes visionkitStatus;

    public PipelineException(int i, String str) {
        String strZza = zzch.values()[i].zza();
        StringBuilder sb = new StringBuilder(String.valueOf(strZza).length() + 2 + String.valueOf(str).length());
        sb.append(strZza);
        sb.append(": ");
        sb.append(str);
        super(sb.toString());
        this.statusCode = zzch.values()[i];
        this.statusMessage = str;
        this.visionkitStatus = null;
    }

    public List<zzag> getComponentStatuses() {
        zzes zzesVar = this.visionkitStatus;
        return zzesVar != null ? zzesVar.zzf() : zzkz.zzi();
    }

    public zzkg<String> getRootCauseMessage() {
        Object next;
        Object obj;
        if (!this.statusMessage.contains(ROOT_CAUSE_DELIMITER)) {
            return zzkg.zzd();
        }
        List<String> listZzc = zzkp.zzb(ROOT_CAUSE_DELIMITER).zzc(this.statusMessage);
        if (!(listZzc instanceof List)) {
            Iterator<T> it = listZzc.iterator();
            do {
                next = it.next();
            } while (it.hasNext());
            obj = next;
        } else {
            if (listZzc.isEmpty()) {
                throw new NoSuchElementException();
            }
            obj = listZzc.get(listZzc.size() - 1);
        }
        return zzkg.zze((String) obj);
    }

    public zzch getStatusCode() {
        return this.statusCode;
    }

    public String getStatusMessage() {
        return this.statusMessage;
    }

    private PipelineException(zzes zzesVar) {
        String strZza = zzch.values()[zzesVar.zza()].zza();
        String strZze = zzesVar.zze();
        StringBuilder sb = new StringBuilder(String.valueOf(strZza).length() + 2 + String.valueOf(strZze).length());
        sb.append(strZza);
        sb.append(": ");
        sb.append(strZze);
        super(sb.toString());
        this.statusCode = zzch.values()[zzesVar.zza()];
        this.statusMessage = zzesVar.zze();
        this.visionkitStatus = zzesVar;
    }

    PipelineException(byte[] bArr) throws zzaoe {
        this(zzes.zzd(bArr, zzanc.zza()));
    }
}
