package com.huawei.hms.mlsdk.model.download;

import com.huawei.hmf.tasks.Task;
import com.huawei.hmf.tasks.Tasks;
import com.huawei.hms.ml.common.utils.SmartLog;
import com.huawei.hms.mlsdk.common.MLApplication;
import com.huawei.hms.mlsdk.common.MLException;
import com.huawei.hms.mlsdk.model.download.impl.f;
import java.io.File;
import java.util.Set;
import java.util.concurrent.Callable;

/* JADX INFO: loaded from: classes10.dex */
public class MLLocalModelManager {
    private static final MLModelDownloadStrategy b = new MLModelDownloadStrategy.Factory().create();
    private static volatile MLLocalModelManager c;
    private f a;

    class a implements Callable<Void> {
        final /* synthetic */ MLRemoteModel a;

        a(MLRemoteModel mLRemoteModel) {
            this.a = mLRemoteModel;
        }

        @Override // java.util.concurrent.Callable
        public Void call() throws Exception {
            SmartLog.d("MLSDK_MODEL_MLLocalModelManager", com.huawei.hms.mlsdk.model.download.p.a.a("MLLocalModelManager::deleteModel delete: ").append(this.a.getModelName()).toString());
            MLLocalModelManager.this.a.a(this.a);
            return null;
        }
    }

    class b implements Callable<Void> {
        final /* synthetic */ MLRemoteModel a;
        final /* synthetic */ MLModelDownloadStrategy b;
        final /* synthetic */ MLModelDownloadListener c;

        b(MLRemoteModel mLRemoteModel, MLModelDownloadStrategy mLModelDownloadStrategy, MLModelDownloadListener mLModelDownloadListener) {
            this.a = mLRemoteModel;
            this.b = mLModelDownloadStrategy;
            this.c = mLModelDownloadListener;
        }

        @Override // java.util.concurrent.Callable
        public Void call() throws Exception {
            f fVar = MLLocalModelManager.this.a;
            MLRemoteModel mLRemoteModel = this.a;
            MLModelDownloadStrategy mLModelDownloadStrategy = this.b;
            if (mLModelDownloadStrategy == null) {
                mLModelDownloadStrategy = MLLocalModelManager.b;
            }
            return fVar.a(mLRemoteModel, mLModelDownloadStrategy, this.c);
        }
    }

    /* JADX INFO: Add missing generic type declarations: [T] */
    class c<T> implements Callable<Set<T>> {
        final /* synthetic */ Class a;

        c(Class cls) {
            this.a = cls;
        }

        @Override // java.util.concurrent.Callable
        public Object call() throws Exception {
            return MLLocalModelManager.this.a.a(this.a);
        }
    }

    class d implements Callable<File> {
        final /* synthetic */ MLRemoteModel a;

        d(MLRemoteModel mLRemoteModel) {
            this.a = mLRemoteModel;
        }

        @Override // java.util.concurrent.Callable
        public File call() throws Exception {
            return MLLocalModelManager.this.a.b(this.a);
        }
    }

    class e implements Callable<Boolean> {
        final /* synthetic */ MLRemoteModel a;

        e(MLRemoteModel mLRemoteModel) {
            this.a = mLRemoteModel;
        }

        @Override // java.util.concurrent.Callable
        public Boolean call() throws Exception {
            return MLLocalModelManager.this.a.c(this.a);
        }
    }

    private MLLocalModelManager(MLApplication mLApplication) {
        this.a = new f(mLApplication.getAppContext());
    }

    public static MLLocalModelManager getInstance() {
        return getInstance(MLApplication.getInstance());
    }

    public Task<Void> deleteModel(MLRemoteModel mLRemoteModel) {
        if (mLRemoteModel != null) {
            return Tasks.callInBackground(new a(mLRemoteModel));
        }
        throw new IllegalArgumentException("MLLocalModelManager::deleteModel param is null");
    }

    public Task<Void> downloadModel(MLRemoteModel mLRemoteModel) {
        if (mLRemoteModel != null) {
            return downloadModel(mLRemoteModel, b);
        }
        throw new IllegalArgumentException("MLLocalModelManager::downloadModel param is null");
    }

    public <T extends MLRemoteModel> Task<Set<T>> getModels(Class<T> cls) {
        if (cls != null) {
            return Tasks.callInBackground(new c(cls));
        }
        throw new IllegalArgumentException("MLLocalModelManager::getModels param is null");
    }

    public Task<File> getRecentModelFile(MLRemoteModel mLRemoteModel) {
        if (mLRemoteModel != null) {
            return Tasks.callInBackground(new d(mLRemoteModel));
        }
        throw new IllegalArgumentException("MLLocalModelManager::getRecentModelFile param is null");
    }

    public File getSyncRecentModelFile(MLRemoteModel mLRemoteModel) throws MLException {
        if (mLRemoteModel != null) {
            return this.a.b(mLRemoteModel);
        }
        throw new IllegalArgumentException("MLLocalModelManager::getRecentModelFile param is null");
    }

    public Task<Boolean> isModelExist(MLRemoteModel mLRemoteModel) {
        if (mLRemoteModel != null) {
            return Tasks.callInBackground(new e(mLRemoteModel));
        }
        throw new IllegalArgumentException("MLLocalModelManager::isModelExist param is null");
    }

    public static MLLocalModelManager getInstance(MLApplication mLApplication) {
        if (mLApplication == null) {
            throw new IllegalArgumentException("MLLocalModelManager::getInstance param is null");
        }
        if (c == null) {
            synchronized (MLLocalModelManager.class) {
                if (c == null) {
                    c = new MLLocalModelManager(mLApplication);
                }
            }
        }
        return c;
    }

    public Task<Void> downloadModel(MLRemoteModel mLRemoteModel, MLModelDownloadStrategy mLModelDownloadStrategy) {
        if (mLRemoteModel != null) {
            return downloadModel(mLRemoteModel, mLModelDownloadStrategy, null);
        }
        throw new IllegalArgumentException("MLLocalModelManager::downloadModel param is null");
    }

    public Task<Void> downloadModel(MLRemoteModel mLRemoteModel, MLModelDownloadStrategy mLModelDownloadStrategy, MLModelDownloadListener mLModelDownloadListener) {
        if (mLRemoteModel != null) {
            return Tasks.callInBackground(new b(mLRemoteModel, mLModelDownloadStrategy, mLModelDownloadListener));
        }
        throw new IllegalArgumentException("MLLocalModelManager::downloadModel param is null");
    }
}
