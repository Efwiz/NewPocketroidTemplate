package com.huawei.hms.mlsdk.model.download.impl;

import android.content.Context;
import com.huawei.hms.ml.common.utils.SmartLog;
import com.huawei.hms.mlsdk.common.MLException;
import com.huawei.hms.mlsdk.model.download.MLModelDownloadListener;
import com.huawei.hms.mlsdk.model.download.MLModelDownloadStrategy;
import com.huawei.hms.mlsdk.model.download.MLRemoteModel;
import com.huawei.hms.mlsdk.model.download.strategy.ModelConfigManagerStrategy;
import java.io.File;
import java.lang.ref.WeakReference;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/* JADX INFO: compiled from: ModelManagerDelegate.java */
/* JADX INFO: loaded from: classes10.dex */
public class f {
    private final ReadWriteLock a = new ReentrantReadWriteLock();
    private d b;
    private b c;
    private e d;
    private c e;

    public f(Context context) {
        WeakReference weakReference = new WeakReference(context);
        com.huawei.hms.mlsdk.model.download.strategy.a aVar = new com.huawei.hms.mlsdk.model.download.strategy.a();
        this.b = new d(weakReference);
        this.d = new e(weakReference, aVar);
        this.c = new b(weakReference, aVar);
        this.e = new c(weakReference, aVar);
    }

    public Void a(MLRemoteModel mLRemoteModel, MLModelDownloadStrategy mLModelDownloadStrategy, MLModelDownloadListener mLModelDownloadListener) throws MLException {
        SmartLog.i("MLSDK_MODEL_ModelManagerDelegate", com.huawei.hms.mlsdk.model.download.p.a.a("start download: ").append(mLRemoteModel.getModelName()).toString());
        if (this.e.a(mLRemoteModel)) {
            SmartLog.w("MLSDK_MODEL_ModelManagerDelegate", com.huawei.hms.mlsdk.model.download.p.a.a("The Model: ").append(mLRemoteModel.getModelName()).append(" already in the download progress").toString());
            throw new MLException("The model is already in the download process", 8);
        }
        if (!this.b.a(mLModelDownloadStrategy)) {
            SmartLog.e("MLSDK_MODEL_ModelManagerDelegate", "The download conditions are not met");
            throw new MLException("The download conditions are not met", 3);
        }
        try {
            this.a.writeLock().lock();
            ModelResponse modelResponseA = this.e.a(mLRemoteModel, mLModelDownloadStrategy.getRegion());
            if (modelResponseA != null && !modelResponseA.isSuccessful()) {
                SmartLog.e("MLSDK_MODEL_ModelManagerDelegate", "Failed to query model information from the cloud");
                this.e.b(mLRemoteModel, modelResponseA);
            }
            String strA = this.e.a(mLRemoteModel, modelResponseA);
            if (strA == null || strA.isEmpty()) {
                SmartLog.w("MLSDK_MODEL_ModelManagerDelegate", "download getHaField failed, use default value");
                strA = "ModelDownload";
            }
            if (!a(mLRemoteModel, modelResponseA)) {
                SmartLog.i("MLSDK_MODEL_ModelManagerDelegate", "download model: " + mLRemoteModel.getModelName() + " no need download");
                this.a.writeLock().unlock();
                return null;
            }
            com.huawei.hms.mlsdk.model.download.p.b bVar = new com.huawei.hms.mlsdk.model.download.p.b();
            a aVar = new a(mLModelDownloadListener);
            this.e.a(mLRemoteModel, aVar, modelResponseA);
            File fileA = aVar.a();
            if (fileA == null) {
                bVar.a(strA, String.valueOf(2));
                SmartLog.e("MLSDK_MODEL_ModelManagerDelegate", "Download model file failed");
                throw new MLException("Download model file failed", 2);
            }
            if (!this.d.a(fileA, this.c.a(mLRemoteModel, modelResponseA))) {
                this.d.b(fileA);
                bVar.a(strA, String.valueOf(18));
                SmartLog.e("MLSDK_MODEL_ModelManagerDelegate", "The model downloaded from the cloud side, integrity check failed");
                throw new MLException("The model downloaded from the cloud side, integrity check failed", 18);
            }
            String strA2 = this.d.a(mLRemoteModel, fileA);
            if (strA2 == null || strA2.isEmpty()) {
                bVar.a(strA, String.valueOf(2));
                SmartLog.e("MLSDK_MODEL_ModelManagerDelegate", "Failed to save the model file");
                throw new MLException("Failed to save the model file", 2);
            }
            if (!this.c.a(mLRemoteModel, strA2, modelResponseA)) {
                bVar.a(strA, String.valueOf(2));
                SmartLog.e("MLSDK_MODEL_ModelManagerDelegate", "Failed to update the model config");
                throw new MLException("Failed to update the model config", 2);
            }
            bVar.a(strA);
            SmartLog.i("MLSDK_MODEL_ModelManagerDelegate", "finish download: " + mLRemoteModel.getModelName());
            this.a.writeLock().unlock();
            return null;
        } catch (Throwable th) {
            this.a.writeLock().unlock();
            throw th;
        }
    }

    public File b(MLRemoteModel mLRemoteModel) throws MLException {
        try {
            SmartLog.i("MLSDK_MODEL_ModelManagerDelegate", "start getRecentModelFile: " + mLRemoteModel.getModelName());
            this.a.readLock().lock();
            Map<String, String> mapC = this.c.c(mLRemoteModel);
            if (mapC == null || mapC.isEmpty()) {
                SmartLog.e("MLSDK_MODEL_ModelManagerDelegate", "The model: " + mLRemoteModel.getModelName() + " not download already");
                throw new MLException("The model not download already", 7);
            }
            String strB = this.d.b(mLRemoteModel, mapC);
            if (strB.isEmpty()) {
                SmartLog.e("MLSDK_MODEL_ModelManagerDelegate", "The model: " + mLRemoteModel.getModelName() + " not download already");
                throw new MLException("The model not download already", 7);
            }
            SmartLog.i("MLSDK_MODEL_ModelManagerDelegate", "finish getRecentModelFile: " + mLRemoteModel.getModelName());
            File file = new File(strB);
            this.a.readLock().unlock();
            return file;
        } catch (Throwable th) {
            this.a.readLock().unlock();
            throw th;
        }
    }

    public Boolean c(MLRemoteModel mLRemoteModel) throws MLException {
        try {
            SmartLog.i("MLSDK_MODEL_ModelManagerDelegate", "start isModelDownloaded: " + mLRemoteModel.getModelName());
            this.a.readLock().lock();
            Map<String, String> mapC = this.c.c(mLRemoteModel);
            if (mapC != null && !mapC.isEmpty()) {
                String strA = this.d.a(b(mLRemoteModel));
                if (strA != null && !strA.isEmpty()) {
                    mapC.put(ModelConfigManagerStrategy.CURRENT_HASH, strA);
                    SmartLog.i("MLSDK_MODEL_ModelManagerDelegate", "finish isModelDownloaded: " + mLRemoteModel.getModelName());
                    return Boolean.valueOf(!this.c.a(mLRemoteModel, mapC));
                }
                SmartLog.e("MLSDK_MODEL_ModelManagerDelegate", "The model: " + mLRemoteModel.getModelName() + " not download already");
                return false;
            }
            SmartLog.e("MLSDK_MODEL_ModelManagerDelegate", "The model: " + mLRemoteModel.getModelName() + " not download already");
            return false;
        } finally {
            this.a.readLock().unlock();
        }
    }

    public Void a(MLRemoteModel mLRemoteModel) throws MLException {
        try {
            SmartLog.i("MLSDK_MODEL_ModelManagerDelegate", "start deleteModel: " + mLRemoteModel.getModelName());
            this.a.writeLock().lock();
            Map<String, String> mapC = this.c.c(mLRemoteModel);
            if (mapC != null && !mapC.isEmpty()) {
                if (this.d.a(mLRemoteModel, mapC)) {
                    if (this.c.a(mLRemoteModel)) {
                        SmartLog.i("MLSDK_MODEL_ModelManagerDelegate", "finish deleteModel: " + mLRemoteModel.getModelName());
                        this.a.writeLock().unlock();
                        return null;
                    }
                    SmartLog.e("MLSDK_MODEL_ModelManagerDelegate", "Delete config failed");
                    throw new MLException("Delete model file failed", 2);
                }
                SmartLog.e("MLSDK_MODEL_ModelManagerDelegate", "Delete model file failed");
                throw new MLException("Delete model file failed", 2);
            }
            SmartLog.i("MLSDK_MODEL_ModelManagerDelegate", "The model: " + mLRemoteModel.getModelName() + " not download already");
            throw new MLException("The model not download already", 7);
        } catch (Throwable th) {
            this.a.writeLock().unlock();
            throw th;
        }
    }

    public <T extends MLRemoteModel> Set<T> a(Class<T> cls) throws MLException {
        try {
            SmartLog.i("MLSDK_MODEL_ModelManagerDelegate", "start getModels: " + cls.getName());
            this.a.readLock().lock();
            String name2 = cls.getName();
            try {
                Constructor<?> declaredConstructor = Class.forName(name2).getDeclaredConstructor(String.class);
                declaredConstructor.setAccessible(true);
                MLRemoteModel mLRemoteModel = (MLRemoteModel) declaredConstructor.newInstance("");
                declaredConstructor.setAccessible(false);
                Map<String, String> mapB = this.c.b(mLRemoteModel);
                if (mapB != null) {
                    int iLastIndexOf = name2.lastIndexOf(".");
                    if (iLastIndexOf != -1) {
                        try {
                            RemoteModelManagerInterface remoteModelManagerInterface = (RemoteModelManagerInterface) Class.forName(name2.substring(0, iLastIndexOf + 1) + "ModelManager").newInstance();
                            SmartLog.i("MLSDK_MODEL_ModelManagerDelegate", "finish getModels: " + cls.getName());
                            Set<T> downloadedModels = remoteModelManagerInterface.getDownloadedModels(mapB);
                            this.a.readLock().unlock();
                            return downloadedModels;
                        } catch (ClassNotFoundException | IllegalAccessException | InstantiationException e) {
                            SmartLog.e("MLSDK_MODEL_ModelManagerDelegate", "getModels failed: " + e.getMessage());
                            throw new MLException("getModels failed: " + e.getMessage(), 7);
                        }
                    }
                    SmartLog.e("MLSDK_MODEL_ModelManagerDelegate", "Invalid className: " + name2);
                    throw new MLException("GetModels invalid className", 5);
                }
                throw new MLException("The specificed model type not found", 7);
            } catch (ClassNotFoundException e2) {
                e = e2;
                SmartLog.e("MLSDK_MODEL_ModelManagerDelegate", "getModels failed: " + e.getMessage());
                throw new MLException("getModels failed: " + e.getMessage(), 7);
            } catch (IllegalAccessException e3) {
                e = e3;
                SmartLog.e("MLSDK_MODEL_ModelManagerDelegate", "getModels failed: " + e.getMessage());
                throw new MLException("getModels failed: " + e.getMessage(), 7);
            } catch (InstantiationException e4) {
                e = e4;
                SmartLog.e("MLSDK_MODEL_ModelManagerDelegate", "getModels failed: " + e.getMessage());
                throw new MLException("getModels failed: " + e.getMessage(), 7);
            } catch (NoSuchMethodException e5) {
                e = e5;
                SmartLog.e("MLSDK_MODEL_ModelManagerDelegate", "getModels failed: " + e.getMessage());
                throw new MLException("getModels failed: " + e.getMessage(), 7);
            } catch (InvocationTargetException e6) {
                e = e6;
                SmartLog.e("MLSDK_MODEL_ModelManagerDelegate", "getModels failed: " + e.getMessage());
                throw new MLException("getModels failed: " + e.getMessage(), 7);
            }
        } catch (Throwable th) {
            this.a.readLock().unlock();
            throw th;
        }
    }

    private boolean a(MLRemoteModel mLRemoteModel, ModelResponse modelResponse) throws MLException {
        if (!c(mLRemoteModel).booleanValue()) {
            SmartLog.w("MLSDK_MODEL_ModelManagerDelegate", com.huawei.hms.mlsdk.model.download.p.a.a("the model: ").append(mLRemoteModel.getModelName()).append(" not download already").toString());
            return true;
        }
        Map<String, String> mapC = this.c.c(mLRemoteModel);
        if (mapC != null && !mapC.isEmpty()) {
            if (!this.c.a(mLRemoteModel, modelResponse, mapC)) {
                return false;
            }
            SmartLog.w("MLSDK_MODEL_ModelManagerDelegate", com.huawei.hms.mlsdk.model.download.p.a.a("The model: ").append(mLRemoteModel.getModelName()).append(" not download already").toString());
            return true;
        }
        SmartLog.w("MLSDK_MODEL_ModelManagerDelegate", com.huawei.hms.mlsdk.model.download.p.a.a("The model: ").append(mLRemoteModel.getModelName()).append(" not download already").toString());
        return true;
    }
}
