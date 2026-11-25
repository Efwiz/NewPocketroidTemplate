package com.google.mlkit.common.sdkinternal.model;

import com.google.android.gms.common.internal.GmsLogger;
import com.google.android.gms.internal.mlkit_common.zzjr;
import com.google.android.gms.internal.mlkit_common.zzju;
import com.google.android.gms.internal.mlkit_common.zzkc;
import com.google.mlkit.common.MlKitException;
import com.google.mlkit.common.model.RemoteModel;
import com.google.mlkit.common.sdkinternal.MlKitContext;
import com.google.mlkit.common.sdkinternal.SharedPrefManager;
import java.io.File;
import java.nio.MappedByteBuffer;
import java.util.HashMap;
import java.util.Map;
import org.jetbrains.kotlin.konan.library.NativeLibraryConstantsKt;

/* JADX INFO: compiled from: com.google.mlkit:common@@17.4.0 */
/* JADX INFO: loaded from: classes10.dex */
public class RemoteModelLoader {
    private static final GmsLogger zza = new GmsLogger("RemoteModelLoader", "");
    private static final Map<String, RemoteModelLoader> zzb = new HashMap();
    private final MlKitContext zzc;
    private final RemoteModel zzd;
    private final RemoteModelDownloadManager zze;
    private final RemoteModelFileManager zzf;
    private final RemoteModelLoaderHelper zzg;
    private final zzjr zzh;
    private boolean zzi;

    private RemoteModelLoader(MlKitContext mlKitContext, RemoteModel remoteModel, ModelValidator modelValidator, RemoteModelLoaderHelper remoteModelLoaderHelper, RemoteModelFileMover remoteModelFileMover) {
        RemoteModelFileManager remoteModelFileManager = new RemoteModelFileManager(mlKitContext, remoteModel, modelValidator, new ModelFileHelper(mlKitContext), remoteModelFileMover);
        this.zzf = remoteModelFileManager;
        this.zzi = true;
        this.zze = RemoteModelDownloadManager.getInstance(mlKitContext, remoteModel, new ModelFileHelper(mlKitContext), remoteModelFileManager, (ModelInfoRetrieverInterop) mlKitContext.get(ModelInfoRetrieverInterop.class));
        this.zzg = remoteModelLoaderHelper;
        this.zzc = mlKitContext;
        this.zzd = remoteModel;
        this.zzh = zzkc.zzb(NativeLibraryConstantsKt.KONAN_DISTRIBUTION_COMMON_LIBS_DIR);
    }

    public static synchronized RemoteModelLoader getInstance(MlKitContext mlKitContext, RemoteModel remoteModel, ModelValidator modelValidator, RemoteModelLoaderHelper remoteModelLoaderHelper, RemoteModelFileMover remoteModelFileMover) {
        String uniqueModelNameForPersist;
        Map<String, RemoteModelLoader> map;
        uniqueModelNameForPersist = remoteModel.getUniqueModelNameForPersist();
        map = zzb;
        if (!map.containsKey(uniqueModelNameForPersist)) {
            map.put(uniqueModelNameForPersist, new RemoteModelLoader(mlKitContext, remoteModel, modelValidator, remoteModelLoaderHelper, remoteModelFileMover));
        }
        return map.get(uniqueModelNameForPersist);
    }

    private final MappedByteBuffer zza(String str) throws MlKitException {
        return this.zzg.loadModelAtPath(str);
    }

    private final MappedByteBuffer zzb(File file) throws MlKitException {
        try {
            return zza(file.getAbsolutePath());
        } catch (Exception e) {
            this.zzf.zzc(file);
            throw new MlKitException("Failed to load newly downloaded model.", 14, e);
        }
    }

    public RemoteModel getRemoteModel() {
        return this.zzd;
    }

    /* JADX WARN: Code duplicated, block: B:30:0x00d6 A[Catch: all -> 0x0117, TryCatch #0 {, blocks: (B:3:0x0001, B:7:0x0020, B:9:0x0028, B:30:0x00d6, B:32:0x00e5, B:34:0x00ed, B:37:0x00f3, B:38:0x0111, B:39:0x0112, B:10:0x002f, B:12:0x005b, B:15:0x0064, B:17:0x0078, B:19:0x0082, B:21:0x008e, B:23:0x0096, B:18:0x007d, B:24:0x00a8, B:26:0x00b0, B:27:0x00c7), top: B:46:0x0001, inners: #1 }] */
    /* JADX WARN: Code duplicated, block: B:32:0x00e5 A[Catch: all -> 0x0117, TRY_LEAVE, TryCatch #0 {, blocks: (B:3:0x0001, B:7:0x0020, B:9:0x0028, B:30:0x00d6, B:32:0x00e5, B:34:0x00ed, B:37:0x00f3, B:38:0x0111, B:39:0x0112, B:10:0x002f, B:12:0x005b, B:15:0x0064, B:17:0x0078, B:19:0x0082, B:21:0x008e, B:23:0x0096, B:18:0x007d, B:24:0x00a8, B:26:0x00b0, B:27:0x00c7), top: B:46:0x0001, inners: #1 }] */
    /* JADX WARN: Code duplicated, block: B:39:0x0112 A[Catch: all -> 0x0117, TRY_LEAVE, TryCatch #0 {, blocks: (B:3:0x0001, B:7:0x0020, B:9:0x0028, B:30:0x00d6, B:32:0x00e5, B:34:0x00ed, B:37:0x00f3, B:38:0x0111, B:39:0x0112, B:10:0x002f, B:12:0x005b, B:15:0x0064, B:17:0x0078, B:19:0x0082, B:21:0x008e, B:23:0x0096, B:18:0x007d, B:24:0x00a8, B:26:0x00b0, B:27:0x00c7), top: B:46:0x0001, inners: #1 }] */
    /* JADX WARN: Code duplicated, block: B:47:0x00ed A[EXC_TOP_SPLITTER, SYNTHETIC] */
    public synchronized MappedByteBuffer load() throws MlKitException {
        MappedByteBuffer mappedByteBufferZza;
        MappedByteBuffer mappedByteBufferZzb;
        String strZzb;
        GmsLogger gmsLogger = zza;
        gmsLogger.d("RemoteModelLoader", "Try to load newly downloaded model file.");
        boolean z = this.zzi;
        Long downloadingId = this.zze.getDownloadingId();
        String downloadingModelHash = this.zze.getDownloadingModelHash();
        mappedByteBufferZza = null;
        if (downloadingId == null || downloadingModelHash == null) {
            gmsLogger.d("RemoteModelLoader", "No new model is downloading.");
            this.zze.removeOrCancelDownload();
        } else {
            Integer downloadingModelStatusCode = this.zze.getDownloadingModelStatusCode();
            if (downloadingModelStatusCode == null) {
                this.zze.removeOrCancelDownload();
            } else {
                String strValueOf = String.valueOf(downloadingModelStatusCode);
                StringBuilder sb = new StringBuilder(String.valueOf(strValueOf).length() + 22);
                sb.append("Download Status code: ");
                sb.append(strValueOf);
                gmsLogger.d("RemoteModelLoader", sb.toString());
                if (downloadingModelStatusCode.intValue() == 8) {
                    File fileZzi = this.zze.zzi(downloadingModelHash);
                    if (fileZzi != null) {
                        mappedByteBufferZzb = zzb(fileZzi);
                        String strValueOf2 = String.valueOf(fileZzi.getParent());
                        gmsLogger.d("RemoteModelLoader", strValueOf2.length() != 0 ? "Moved the downloaded model to private folder successfully: ".concat(strValueOf2) : new String("Moved the downloaded model to private folder successfully: "));
                        this.zze.updateLatestModelHashAndType(downloadingModelHash);
                        if (z && this.zzf.zzd(fileZzi)) {
                            gmsLogger.d("RemoteModelLoader", "All old models are deleted.");
                            mappedByteBufferZzb = zzb(this.zzf.zza(fileZzi));
                        }
                    }
                    if (mappedByteBufferZzb == null) {
                        gmsLogger.d("RemoteModelLoader", "Loading existing model file.");
                        strZzb = this.zzf.zzb();
                        if (strZzb == null) {
                            gmsLogger.d("RemoteModelLoader", "No existing model file");
                        } else {
                            try {
                                mappedByteBufferZza = zza(strZzb);
                            } catch (Exception e) {
                                this.zzf.zzc(new File(strZzb));
                                SharedPrefManager.getInstance(this.zzc).clearLatestModelHash(this.zzd);
                                throw new MlKitException("Failed to load an already downloaded model.", 14, e);
                            }
                        }
                    } else {
                        this.zzi = false;
                        mappedByteBufferZza = mappedByteBufferZzb;
                    }
                } else if (downloadingModelStatusCode.intValue() == 16) {
                    this.zzh.zzd(zzju.zzg(), this.zzd, false, this.zze.getFailureReason(downloadingId));
                    this.zze.removeOrCancelDownload();
                }
            }
        }
        mappedByteBufferZzb = null;
        if (mappedByteBufferZzb == null) {
            gmsLogger.d("RemoteModelLoader", "Loading existing model file.");
            strZzb = this.zzf.zzb();
            if (strZzb == null) {
                gmsLogger.d("RemoteModelLoader", "No existing model file");
            } else {
                mappedByteBufferZza = zza(strZzb);
            }
        } else {
            this.zzi = false;
            mappedByteBufferZza = mappedByteBufferZzb;
        }
        return mappedByteBufferZza;
    }
}
