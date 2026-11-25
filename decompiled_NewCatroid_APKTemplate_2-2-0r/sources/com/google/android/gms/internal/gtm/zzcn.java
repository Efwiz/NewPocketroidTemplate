package com.google.android.gms.internal.gtm;

import android.content.Context;
import com.google.android.gms.common.internal.Preconditions;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Locale;
import java.util.UUID;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/* JADX INFO: compiled from: com.google.android.gms:play-services-analytics-impl@@17.0.1 */
/* JADX INFO: loaded from: classes9.dex */
public final class zzcn extends zzbs {
    private volatile String zza;
    private Future<String> zzb;

    protected zzcn(zzbv zzbvVar) {
        super(zzbvVar);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final String zzf() {
        String lowerCase = UUID.randomUUID().toString().toLowerCase(Locale.US);
        try {
            Context contextZza = zzq().zza();
            Preconditions.checkNotEmpty(lowerCase);
            Preconditions.checkNotMainThread("ClientId should be saved from worker thread");
            FileOutputStream fileOutputStreamOpenFileOutput = null;
            try {
                try {
                    try {
                        zzP("Storing clientId", lowerCase);
                        fileOutputStreamOpenFileOutput = contextZza.openFileOutput("gaClientId", 0);
                        fileOutputStreamOpenFileOutput.write(lowerCase.getBytes());
                        if (fileOutputStreamOpenFileOutput != null) {
                            try {
                                fileOutputStreamOpenFileOutput.close();
                            } catch (IOException e) {
                                zzK("Failed to close clientId writing stream", e);
                            }
                        }
                        return lowerCase;
                    } catch (FileNotFoundException e2) {
                        zzK("Error creating clientId file", e2);
                        if (fileOutputStreamOpenFileOutput != null) {
                            try {
                                fileOutputStreamOpenFileOutput.close();
                            } catch (IOException e3) {
                                e = e3;
                                zzK("Failed to close clientId writing stream", e);
                                return "0";
                            }
                        }
                        return "0";
                    }
                } catch (IOException e4) {
                    zzK("Error writing to clientId file", e4);
                    if (fileOutputStreamOpenFileOutput != null) {
                        try {
                            fileOutputStreamOpenFileOutput.close();
                        } catch (IOException e5) {
                            e = e5;
                            zzK("Failed to close clientId writing stream", e);
                            return "0";
                        }
                    }
                    return "0";
                }
            } catch (Throwable th) {
                if (fileOutputStreamOpenFileOutput != null) {
                    try {
                        fileOutputStreamOpenFileOutput.close();
                    } catch (IOException e6) {
                        zzK("Failed to close clientId writing stream", e6);
                    }
                }
                throw th;
            }
        } catch (Exception e7) {
            zzK("Error saving clientId file", e7);
            return "0";
        }
    }

    public final String zzb() {
        String str;
        zzW();
        synchronized (this) {
            if (this.zza == null) {
                this.zzb = zzq().zzg(new zzcl(this));
            }
            Future<String> future = this.zzb;
            if (future != null) {
                try {
                    this.zza = future.get();
                } catch (InterruptedException e) {
                    zzS("ClientId loading or generation was interrupted", e);
                    this.zza = "0";
                } catch (ExecutionException e2) {
                    zzK("Failed to load or generate client id", e2);
                    this.zza = "0";
                }
                if (this.zza == null) {
                    this.zza = "0";
                }
                zzP("Loaded clientId", this.zza);
                this.zzb = null;
                str = this.zza;
            } else {
                str = this.zza;
            }
            throw th;
        }
        return str;
    }

    /* JADX INFO: Removed unreachable split cross block B:64:0x0090 */
    /* JADX WARN: Code duplicated, block: B:49:0x0092  */
    /* JADX WARN: Code duplicated, block: B:51:0x0097 A[RETURN] */
    /* JADX WARN: Code duplicated, block: B:56:0x007c A[EXC_TOP_SPLITTER, SYNTHETIC] */
    final String zzc() throws Throwable {
        Throwable th;
        FileInputStream fileInputStreamOpenFileInput;
        Context contextZza = zzq().zza();
        Preconditions.checkNotMainThread("ClientId should be loaded from worker thread");
        String str = null;
        str = null;
        str = null;
        str = null;
        str = null;
        str = null;
        str = null;
        str = null;
        str = null;
        FileInputStream fileInputStream = null;
        try {
            try {
                try {
                    fileInputStreamOpenFileInput = contextZza.openFileInput("gaClientId");
                    try {
                        byte[] bArr = new byte[36];
                        int i = fileInputStreamOpenFileInput.read(bArr, 0, 36);
                        if (fileInputStreamOpenFileInput.available() > 0) {
                            zzR("clientId file seems corrupted, deleting it.");
                            fileInputStreamOpenFileInput.close();
                            contextZza.deleteFile("gaClientId");
                            if (fileInputStreamOpenFileInput != null) {
                                fileInputStreamOpenFileInput.close();
                            }
                        } else if (i < 14) {
                            zzR("clientId file is empty, deleting it.");
                            fileInputStreamOpenFileInput.close();
                            contextZza.deleteFile("gaClientId");
                            if (fileInputStreamOpenFileInput != null) {
                                fileInputStreamOpenFileInput.close();
                            }
                        } else {
                            fileInputStreamOpenFileInput.close();
                            String str2 = new String(bArr, 0, i);
                            zzP("Read client id from disk", str2);
                            if (fileInputStreamOpenFileInput != null) {
                                try {
                                    fileInputStreamOpenFileInput.close();
                                } catch (IOException e) {
                                    zzK("Failed to close client id reading stream", e);
                                }
                            }
                            str = str2;
                        }
                    } catch (FileNotFoundException unused) {
                        if (fileInputStreamOpenFileInput != null) {
                            fileInputStreamOpenFileInput.close();
                        }
                        if (str == null) {
                            return zzf();
                        }
                        return str;
                    } catch (IOException e2) {
                        e = e2;
                        zzK("Error reading client id file, deleting it", e);
                        contextZza.deleteFile("gaClientId");
                        if (fileInputStreamOpenFileInput != null) {
                            fileInputStreamOpenFileInput.close();
                        }
                    }
                } catch (Throwable th2) {
                    th = th2;
                    fileInputStream = fileInputStreamOpenFileInput;
                    if (fileInputStream != null) {
                        try {
                            fileInputStream.close();
                        } catch (IOException e3) {
                            zzK("Failed to close client id reading stream", e3);
                        }
                    }
                    throw th;
                }
            } catch (FileNotFoundException unused2) {
                fileInputStreamOpenFileInput = null;
            } catch (IOException e4) {
                e = e4;
                fileInputStreamOpenFileInput = null;
            } catch (Throwable th3) {
                th = th3;
                if (fileInputStream != null) {
                    fileInputStream.close();
                }
                throw th;
            }
        } catch (IOException e5) {
            zzK("Failed to close client id reading stream", e5);
        }
        if (str == null) {
            return zzf();
        }
        return str;
    }

    @Override // com.google.android.gms.internal.gtm.zzbs
    protected final void zzd() {
    }

    final String zze() {
        synchronized (this) {
            this.zza = null;
            this.zzb = zzq().zzg(new zzcm(this));
        }
        return zzb();
    }
}
