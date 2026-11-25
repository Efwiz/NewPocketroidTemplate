package com.google.android.gms.tagmanager;

import android.content.Context;
import android.content.res.Resources;
import com.google.android.gms.internal.gtm.zzfz;
import com.google.android.gms.internal.gtm.zzrl;
import com.google.android.gms.internal.gtm.zzro;
import com.google.android.gms.internal.gtm.zzrq;
import com.google.android.gms.internal.gtm.zzrs;
import com.google.android.gms.internal.gtm.zzru;
import com.google.android.gms.internal.gtm.zzrz;
import com.google.android.gms.internal.gtm.zzsa;
import com.google.android.gms.internal.gtm.zzuj;
import com.google.android.gms.internal.gtm.zzvk;
import com.huawei.hms.ml.language.common.utils.Constant;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.concurrent.ExecutorService;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: compiled from: com.google.android.gms:play-services-tagmanager-v4-impl@@17.0.1 */
/* JADX INFO: loaded from: classes9.dex */
final class zzem implements zzak {
    private final Context zza;
    private final String zzb;
    private final ExecutorService zzc = zzfz.zza().zza(2);
    private zzdg<zzrl> zzd;

    zzem(Context context, String str) {
        this.zza = context;
        this.zzb = str;
    }

    @Override // com.google.android.gms.common.api.Releasable
    public final synchronized void release() {
        this.zzc.shutdown();
    }

    @Override // com.google.android.gms.tagmanager.zzak
    public final zzrs zza(int i) {
        zzrs zzrsVarZza;
        try {
            InputStream inputStreamOpenRawResource = this.zza.getResources().openRawResource(i);
            String resourceName = this.zza.getResources().getResourceName(i);
            StringBuilder sb = new StringBuilder(String.valueOf(resourceName).length() + 66);
            sb.append("Attempting to load a container from the resource ID ");
            sb.append(i);
            sb.append(" (");
            sb.append(resourceName);
            sb.append(Constant.AFTER_QUTO);
            zzdh.zzb.zzd(sb.toString());
            try {
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                zzsa.zzc(inputStreamOpenRawResource, byteArrayOutputStream);
                try {
                    com.google.android.gms.internal.gtm.zzak zzakVarZzc = zzfv.zzc(zzcz.zza(new JSONObject(byteArrayOutputStream.toString("UTF-8"))));
                    zzru zzruVarZza = zzrs.zza();
                    for (int i2 = 0; i2 < zzakVarZzc.zzc(); i2++) {
                        zzrq zzrqVarZzb = zzro.zzb();
                        zzrqVarZzb.zzb(com.google.android.gms.internal.gtm.zzb.INSTANCE_NAME.toString(), zzakVarZzc.zzk(i2));
                        zzrqVarZzb.zzb(com.google.android.gms.internal.gtm.zzb.FUNCTION.toString(), zzfv.zza(zzt.zzc()));
                        zzrqVarZzb.zzb(zzt.zzd(), zzakVarZzc.zzl(i2));
                        zzruVarZza.zzb(zzrqVarZzb.zza());
                    }
                    zzrsVarZza = zzruVarZza.zza();
                } catch (UnsupportedEncodingException unused) {
                    zzdh.zzb.zza("Failed to convert binary resource to string for JSON parsing; the file format is not UTF-8 format.");
                    zzrsVarZza = null;
                } catch (JSONException unused2) {
                    zzdh.zzc("Failed to extract the container from the resource file. Resource is a UTF-8 encoded string but doesn't contain a JSON container");
                    zzrsVarZza = null;
                }
                if (zzrsVarZza != null) {
                    zzdh.zzb.zzd("The container was successfully loaded from the resource (using JSON file format)");
                    return zzrsVarZza;
                }
                try {
                    zzrs zzrsVarZzb = zzsa.zzb(com.google.android.gms.internal.gtm.zzaa.zzm(byteArrayOutputStream.toByteArray(), zzuj.zzb()));
                    zzdh.zzb.zzd("The container was successfully loaded from the resource (using binary file)");
                    return zzrsVarZzb;
                } catch (zzrz unused3) {
                    zzdh.zzc("The resource file is invalid. The container from the binary file is invalid");
                    return null;
                } catch (zzvk unused4) {
                    zzdh.zza("The resource file is corrupted. The container cannot be extracted from the binary file");
                    return null;
                }
            } catch (IOException unused5) {
                String resourceName2 = this.zza.getResources().getResourceName(i);
                StringBuilder sb2 = new StringBuilder(String.valueOf(resourceName2).length() + 67);
                sb2.append("Error reading the default container with resource ID ");
                sb2.append(i);
                sb2.append(" (");
                sb2.append(resourceName2);
                sb2.append(Constant.AFTER_QUTO);
                zzdh.zzc(sb2.toString());
                return null;
            }
        } catch (Resources.NotFoundException unused6) {
            StringBuilder sb3 = new StringBuilder(98);
            sb3.append("Failed to load the container. No default container resource found with the resource ID ");
            sb3.append(i);
            zzdh.zzc(sb3.toString());
            return null;
        }
    }

    @Override // com.google.android.gms.tagmanager.zzak
    public final void zzb() {
        this.zzc.execute(new zzek(this));
    }

    @Override // com.google.android.gms.tagmanager.zzak
    public final void zzc(zzrl zzrlVar) {
        this.zzc.execute(new zzel(this, zzrlVar));
    }

    @Override // com.google.android.gms.tagmanager.zzak
    public final void zzd(zzdg<zzrl> zzdgVar) {
        this.zzd = zzdgVar;
    }

    final File zze() {
        String strValueOf = String.valueOf(this.zzb);
        return new File(this.zza.getDir("google_tagmanager", 0), strValueOf.length() != 0 ? "resource_".concat(strValueOf) : new String("resource_"));
    }

    final void zzf() {
        com.google.android.gms.internal.gtm.zzah zzahVarZzZ;
        if (this.zzd == null) {
            throw new IllegalStateException("Callback must be set before execute");
        }
        zzdh.zzb.zzd("Attempting to load resource from disk");
        if ((zzea.zza().zze() == 2 || zzea.zza().zze() == 3) && this.zzb.equals(zzea.zza().zzc())) {
            this.zzd.zza(1);
            return;
        }
        try {
            FileInputStream fileInputStream = new FileInputStream(zze());
            try {
                try {
                    zzrl zzrlVarZzg = zzrl.zzg(fileInputStream, zzuj.zzb());
                    if (!zzrlVarZzg.zzk() && !zzrlVarZzg.zzl()) {
                        throw new IllegalArgumentException("Resource and SupplementedResource are NULL.");
                    }
                    zzdg<zzrl> zzdgVar = this.zzd;
                    if (zzrlVarZzg.zzl()) {
                        zzahVarZzZ = zzrlVarZzg.zzd().zzZ();
                    } else {
                        com.google.android.gms.internal.gtm.zzaa zzaaVarZzc = zzrlVarZzg.zzc();
                        com.google.android.gms.internal.gtm.zzah zzahVarZzd = com.google.android.gms.internal.gtm.zzai.zzd();
                        zzahVarZzd.zzc(zzaaVarZzc);
                        zzahVarZzd.zza();
                        zzahVarZzd.zzb(zzaaVarZzc.zzo());
                        zzahVarZzZ = zzahVarZzd;
                    }
                    ((zzae) zzdgVar).zza.zzu(zzahVarZzZ.zzC(), zzrlVarZzg.zza(), true);
                } catch (Throwable th) {
                    try {
                        fileInputStream.close();
                    } catch (IOException unused) {
                        zzdh.zzc("Error closing stream for reading resource from disk");
                    }
                    throw th;
                }
            } catch (IOException unused2) {
                this.zzd.zza(2);
                zzdh.zzc("Failed to read the resource from disk");
            } catch (IllegalArgumentException unused3) {
                this.zzd.zza(2);
                zzdh.zzc("Failed to read the resource from disk. The resource is inconsistent");
            }
            try {
                fileInputStream.close();
            } catch (IOException unused4) {
                zzdh.zzc("Error closing stream for reading resource from disk");
            }
            zzdh.zzb.zzd("The Disk resource was successfully read.");
        } catch (FileNotFoundException unused5) {
            zzdh.zzb.zza("Failed to find the resource in the disk");
            this.zzd.zza(1);
        }
    }
}
