package com.google.android.gms.internal.gtm;

import android.text.TextUtils;
import com.google.android.gms.ads.identifier.AdvertisingIdClient;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.util.Locale;

/* JADX INFO: compiled from: com.google.android.gms:play-services-analytics-impl@@17.0.1 */
/* JADX INFO: loaded from: classes9.dex */
public final class zzbi extends zzbs {
    public static boolean zza;
    private AdvertisingIdClient.Info zzb;
    private final zzfo zzc;
    private String zzd;
    private boolean zze;
    private final Object zzf;

    zzbi(zzbv zzbvVar) {
        super(zzbvVar);
        this.zze = false;
        this.zzf = new Object();
        this.zzc = new zzfo(zzbvVar.zzr());
    }

    private final synchronized AdvertisingIdClient.Info zzc() {
        AdvertisingIdClient.Info advertisingIdInfo;
        String str;
        IOException e;
        boolean zZzf;
        if (this.zzc.zzc(1000L)) {
            this.zzc.zzb();
            String id2 = null;
            try {
                try {
                    advertisingIdInfo = AdvertisingIdClient.getAdvertisingIdInfo(zzo());
                } catch (Exception e2) {
                    if (!zza) {
                        zza = true;
                        zzS("Error getting advertiser id", e2);
                    }
                    advertisingIdInfo = null;
                }
            } catch (IllegalStateException unused) {
                zzR("IllegalStateException getting Ad Id Info. If you would like to see Audience reports, please ensure that you have added '<meta-data android:name=\"com.google.android.gms.version\" android:value=\"@integer/google_play_services_version\" />' to your application manifest file. See http://goo.gl/naFqQk for details.");
                advertisingIdInfo = null;
            }
            AdvertisingIdClient.Info info = this.zzb;
            String id3 = advertisingIdInfo == null ? null : advertisingIdInfo.getId();
            if (!TextUtils.isEmpty(id3)) {
                String strZzb = zzv().zzb();
                synchronized (this.zzf) {
                    if (!this.zze) {
                        try {
                            FileInputStream fileInputStreamOpenFileInput = zzo().openFileInput("gaClientIdData");
                            byte[] bArr = new byte[128];
                            int i = fileInputStreamOpenFileInput.read(bArr, 0, 128);
                            if (fileInputStreamOpenFileInput.available() > 0) {
                                zzR("Hash file seems corrupted, deleting it.");
                                fileInputStreamOpenFileInput.close();
                                zzo().deleteFile("gaClientIdData");
                            } else if (i <= 0) {
                                zzO("Hash file is empty.");
                                fileInputStreamOpenFileInput.close();
                            } else {
                                str = new String(bArr, 0, i);
                                try {
                                    fileInputStreamOpenFileInput.close();
                                } catch (FileNotFoundException unused2) {
                                } catch (IOException e3) {
                                    e = e3;
                                    zzS("Error reading Hash file, deleting it", e);
                                    zzo().deleteFile("gaClientIdData");
                                }
                                id2 = str;
                            }
                        } catch (FileNotFoundException unused3) {
                        } catch (IOException e4) {
                            str = null;
                            e = e4;
                        }
                        this.zzd = id2;
                        this.zze = true;
                    } else if (TextUtils.isEmpty(this.zzd)) {
                        if (info != null) {
                            id2 = info.getId();
                        }
                        if (id2 == null) {
                            String strValueOf = String.valueOf(id3);
                            String strValueOf2 = String.valueOf(strZzb);
                            zZzf = zzf(strValueOf2.length() != 0 ? strValueOf.concat(strValueOf2) : new String(strValueOf));
                        } else {
                            String strValueOf3 = String.valueOf(strZzb);
                            this.zzd = zze(strValueOf3.length() != 0 ? id2.concat(strValueOf3) : new String(id2));
                        }
                        if (!zZzf) {
                            zzJ("Failed to reset client id on adid change. Not using adid");
                            this.zzb = new AdvertisingIdClient.Info("", false);
                        }
                    }
                    String strValueOf4 = String.valueOf(id3);
                    String strValueOf5 = String.valueOf(strZzb);
                    String strZze = zze(strValueOf5.length() != 0 ? strValueOf4.concat(strValueOf5) : new String(strValueOf4));
                    if (!TextUtils.isEmpty(strZze)) {
                        if (!strZze.equals(this.zzd)) {
                            if (!TextUtils.isEmpty(this.zzd)) {
                                zzO("Resetting the client id because Advertising Id changed.");
                                strZzb = zzv().zze();
                                zzP("New client Id", strZzb);
                            }
                            String strValueOf6 = String.valueOf(id3);
                            String strValueOf7 = String.valueOf(strZzb);
                            zZzf = zzf(strValueOf7.length() != 0 ? strValueOf6.concat(strValueOf7) : new String(strValueOf6));
                            if (!zZzf) {
                            }
                        }
                    }
                    zzJ("Failed to reset client id on adid change. Not using adid");
                    this.zzb = new AdvertisingIdClient.Info("", false);
                }
            }
            this.zzb = advertisingIdInfo;
        }
        return this.zzb;
    }

    private static String zze(String str) {
        MessageDigest messageDigestZze = zzfs.zze("MD5");
        if (messageDigestZze == null) {
            return null;
        }
        return String.format(Locale.US, "%032X", new BigInteger(1, messageDigestZze.digest(str.getBytes())));
    }

    private final boolean zzf(String str) {
        try {
            String strZze = zze(str);
            zzO("Storing hashed adid.");
            FileOutputStream fileOutputStreamOpenFileOutput = zzo().openFileOutput("gaClientIdData", 0);
            fileOutputStreamOpenFileOutput.write(strZze.getBytes());
            fileOutputStreamOpenFileOutput.close();
            this.zzd = strZze;
            return true;
        } catch (IOException e) {
            zzK("Error creating hash file", e);
            return false;
        }
    }

    public final String zza() {
        zzW();
        AdvertisingIdClient.Info infoZzc = zzc();
        String id2 = infoZzc != null ? infoZzc.getId() : null;
        if (TextUtils.isEmpty(id2)) {
            return null;
        }
        return id2;
    }

    public final boolean zzb() {
        zzW();
        AdvertisingIdClient.Info infoZzc = zzc();
        return (infoZzc == null || infoZzc.isLimitAdTrackingEnabled()) ? false : true;
    }

    @Override // com.google.android.gms.internal.gtm.zzbs
    protected final void zzd() {
    }
}
