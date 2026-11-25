package com.google.android.gms.auth.api.signin.internal;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.PendingResults;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.internal.StatusPendingResult;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.logging.Logger;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

/* JADX INFO: loaded from: classes9.dex */
public final class zzd implements Runnable {
    private static final Logger zzbd = new Logger("RevokeAccessOperation", new String[0]);
    private final String zzbe;
    private final StatusPendingResult zzbf;

    private zzd(String str) {
        Preconditions.checkNotEmpty(str);
        this.zzbe = str;
        this.zzbf = new StatusPendingResult((GoogleApiClient) null);
    }

    @Override // java.lang.Runnable
    public final void run() {
        Status status = Status.RESULT_INTERNAL_ERROR;
        try {
            String strValueOf = String.valueOf("https://accounts.google.com/o/oauth2/revoke?token=");
            String strValueOf2 = String.valueOf(this.zzbe);
            HttpURLConnection httpURLConnection = (HttpURLConnection) new URL(strValueOf2.length() != 0 ? strValueOf.concat(strValueOf2) : new String(strValueOf)).openConnection();
            httpURLConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            int responseCode = httpURLConnection.getResponseCode();
            if (responseCode == 200) {
                status = Status.RESULT_SUCCESS;
            } else {
                zzbd.e("Unable to revoke access!", new Object[0]);
            }
            zzbd.d(new StringBuilder(26).append("Response Code: ").append(responseCode).toString(), new Object[0]);
        } catch (IOException e) {
            Logger logger = zzbd;
            String strValueOf3 = String.valueOf(e.toString());
            logger.e(strValueOf3.length() != 0 ? "IOException when revoking access: ".concat(strValueOf3) : new String("IOException when revoking access: "), new Object[0]);
        } catch (Exception e2) {
            Logger logger2 = zzbd;
            String strValueOf4 = String.valueOf(e2.toString());
            logger2.e(strValueOf4.length() != 0 ? "Exception when revoking access: ".concat(strValueOf4) : new String("Exception when revoking access: "), new Object[0]);
        }
        this.zzbf.setResult(status);
    }

    public static PendingResult<Status> zzc(String str) {
        if (str == null) {
            return PendingResults.immediateFailedResult(new Status(4), null);
        }
        zzd zzdVar = new zzd(str);
        new Thread(zzdVar).start();
        return zzdVar.zzbf;
    }
}
