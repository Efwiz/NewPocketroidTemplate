package com.google.android.gms.tagmanager;

import android.content.Context;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.internal.gtm.zzrs;
import com.google.android.gms.internal.gtm.zzrz;
import com.google.android.gms.internal.gtm.zzsa;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/* JADX INFO: compiled from: com.google.android.gms:play-services-tagmanager-v4-impl@@17.0.1 */
/* JADX INFO: loaded from: classes9.dex */
public class Container {
    private final Context zza;
    private final String zzb;
    private final DataLayer zzc;
    private zzeu zzd;
    private Map<String, FunctionCallMacroCallback> zze;
    private Map<String, FunctionCallTagCallback> zzf;
    private volatile long zzg;
    private volatile String zzh;

    /* JADX INFO: compiled from: com.google.android.gms:play-services-tagmanager-v4-impl@@17.0.1 */
    public interface FunctionCallMacroCallback {
        Object getValue(String str, Map<String, Object> map);
    }

    /* JADX INFO: compiled from: com.google.android.gms:play-services-tagmanager-v4-impl@@17.0.1 */
    public interface FunctionCallTagCallback {
        void execute(String str, Map<String, Object> map);
    }

    Container(Context context, DataLayer dataLayer, String str, long j, com.google.android.gms.internal.gtm.zzai zzaiVar) {
        this.zze = new HashMap();
        this.zzf = new HashMap();
        this.zzh = "";
        this.zza = context;
        this.zzc = dataLayer;
        this.zzb = str;
        this.zzg = j;
        com.google.android.gms.internal.gtm.zzaa zzaaVarZzc = zzaiVar.zzc();
        zzaaVarZzc.getClass();
        try {
            zzg(zzsa.zzb(zzaaVarZzc));
        } catch (zzrz e) {
            String strValueOf = String.valueOf(zzaaVarZzc);
            String string = e.toString();
            StringBuilder sb = new StringBuilder(String.valueOf(strValueOf).length() + 46 + String.valueOf(string).length());
            sb.append("Not loading resource: ");
            sb.append(strValueOf);
            sb.append(" because it is invalid: ");
            sb.append(string);
            zzdh.zza(sb.toString());
        }
        if (zzaiVar.zza() != 0) {
            com.google.android.gms.internal.gtm.zzag[] zzagVarArr = (com.google.android.gms.internal.gtm.zzag[]) zzaiVar.zzi().toArray(new com.google.android.gms.internal.gtm.zzag[0]);
            zzeu zzeuVarZzf = zzf();
            if (zzeuVarZzf == null) {
                zzdh.zza("evaluateTags called for closed container.");
                return;
            }
            ArrayList arrayList = new ArrayList();
            for (com.google.android.gms.internal.gtm.zzag zzagVar : zzagVarArr) {
                arrayList.add(zzagVar);
            }
            zzeuVarZzf.zze(arrayList);
        }
    }

    private final synchronized zzeu zzf() {
        return this.zzd;
    }

    private final void zzg(zzrs zzrsVar) {
        this.zzh = zzrsVar.zzb();
        zzh(new zzeu((Context) Preconditions.checkNotNull(this.zza), zzrsVar, (DataLayer) Preconditions.checkNotNull(this.zzc), new zzv(this, null), new zzx(this, null), new zzdl(), null));
        if (getBoolean("_gtm.loadEventEnabled")) {
            this.zzc.pushEvent("gtm.load", DataLayer.mapOf("gtm.id", Preconditions.checkNotNull(this.zzb)));
        }
    }

    private final synchronized void zzh(zzeu zzeuVar) {
        this.zzd = zzeuVar;
    }

    public boolean getBoolean(String str) {
        zzeu zzeuVarZzf = zzf();
        if (zzeuVarZzf == null) {
            zzdh.zza("getBoolean called for closed container.");
            return zzfv.zzf().booleanValue();
        }
        try {
            return zzfv.zzg(zzfv.zzl(zzeuVarZzf.zza(str).zza())).booleanValue();
        } catch (Exception e) {
            String message = e.getMessage();
            StringBuilder sb = new StringBuilder(String.valueOf(message).length() + 66);
            sb.append("Calling getBoolean() threw an exception: ");
            sb.append(message);
            sb.append(" Returning default value.");
            zzdh.zza(sb.toString());
            return zzfv.zzf().booleanValue();
        }
    }

    public String getContainerId() {
        return this.zzb;
    }

    public double getDouble(String str) {
        zzeu zzeuVarZzf = zzf();
        if (zzeuVarZzf == null) {
            zzdh.zza("getDouble called for closed container.");
            return zzfv.zzh().doubleValue();
        }
        try {
            return zzfv.zzi(zzfv.zzl(zzeuVarZzf.zza(str).zza())).doubleValue();
        } catch (Exception e) {
            String message = e.getMessage();
            StringBuilder sb = new StringBuilder(String.valueOf(message).length() + 65);
            sb.append("Calling getDouble() threw an exception: ");
            sb.append(message);
            sb.append(" Returning default value.");
            zzdh.zza(sb.toString());
            return zzfv.zzh().doubleValue();
        }
    }

    public long getLastRefreshTime() {
        return this.zzg;
    }

    public long getLong(String str) {
        zzeu zzeuVarZzf = zzf();
        if (zzeuVarZzf == null) {
            zzdh.zza("getLong called for closed container.");
            return zzfv.zzj().longValue();
        }
        try {
            return zzfv.zzk(zzfv.zzl(zzeuVarZzf.zza(str).zza())).longValue();
        } catch (Exception e) {
            String message = e.getMessage();
            StringBuilder sb = new StringBuilder(String.valueOf(message).length() + 63);
            sb.append("Calling getLong() threw an exception: ");
            sb.append(message);
            sb.append(" Returning default value.");
            zzdh.zza(sb.toString());
            return zzfv.zzj().longValue();
        }
    }

    public String getString(String str) {
        zzeu zzeuVarZzf = zzf();
        if (zzeuVarZzf == null) {
            zzdh.zza("getString called for closed container.");
            return zzfv.zzm();
        }
        try {
            return zzfv.zzn(zzfv.zzl(zzeuVarZzf.zza(str).zza()));
        } catch (Exception e) {
            String message = e.getMessage();
            StringBuilder sb = new StringBuilder(String.valueOf(message).length() + 65);
            sb.append("Calling getString() threw an exception: ");
            sb.append(message);
            sb.append(" Returning default value.");
            zzdh.zza(sb.toString());
            return zzfv.zzm();
        }
    }

    public boolean isDefault() {
        return getLastRefreshTime() == 0;
    }

    public void registerFunctionCallMacroCallback(String str, FunctionCallMacroCallback functionCallMacroCallback) {
        if (functionCallMacroCallback == null) {
            throw new NullPointerException("Macro handler must be non-null");
        }
        synchronized (this.zze) {
            this.zze.put(str, functionCallMacroCallback);
        }
    }

    public void registerFunctionCallTagCallback(String str, FunctionCallTagCallback functionCallTagCallback) {
        if (functionCallTagCallback == null) {
            throw new NullPointerException("Tag callback must be non-null");
        }
        synchronized (this.zzf) {
            this.zzf.put(str, functionCallTagCallback);
        }
    }

    public void unregisterFunctionCallMacroCallback(String str) {
        synchronized (this.zze) {
            this.zze.remove(str);
        }
    }

    public void unregisterFunctionCallTagCallback(String str) {
        synchronized (this.zzf) {
            this.zzf.remove(str);
        }
    }

    final FunctionCallMacroCallback zza(String str) {
        FunctionCallMacroCallback functionCallMacroCallback;
        synchronized (this.zze) {
            functionCallMacroCallback = this.zze.get(str);
        }
        return functionCallMacroCallback;
    }

    public final FunctionCallTagCallback zzb(String str) {
        FunctionCallTagCallback functionCallTagCallback;
        synchronized (this.zzf) {
            functionCallTagCallback = this.zzf.get(str);
        }
        return functionCallTagCallback;
    }

    public final String zzc() {
        return this.zzh;
    }

    public final void zzd(String str) {
        zzeu zzeuVarZzf = zzf();
        if (zzeuVarZzf == null) {
            zzdh.zza("evaluateTags called for closed container.");
        } else {
            zzeuVarZzf.zzc(str);
        }
    }

    final void zze() {
        this.zzd = null;
    }

    Container(Context context, DataLayer dataLayer, String str, long j, zzrs zzrsVar) {
        this.zze = new HashMap();
        this.zzf = new HashMap();
        this.zzh = "";
        this.zza = context;
        this.zzc = dataLayer;
        this.zzb = str;
        this.zzg = 0L;
        zzg(zzrsVar);
    }
}
