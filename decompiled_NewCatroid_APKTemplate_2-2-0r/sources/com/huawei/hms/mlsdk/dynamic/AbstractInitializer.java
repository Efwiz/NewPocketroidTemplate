package com.huawei.hms.mlsdk.dynamic;

import android.content.Context;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import com.huawei.hms.common.PackageConstants;
import com.huawei.hms.feature.dynamic.DynamicModule;
import com.huawei.hms.ml.common.utils.SmartLog;
import com.huawei.hms.mlsdk.dynamic.policy.IContextPolicy;
import com.huawei.hms.mlsdk.dynamic.policy.LocalPolicy;
import com.huawei.hms.mlsdk.dynamic.policy.PreferHighestOrLocal;
import com.huawei.hms.mlsdk.dynamic.policy.PreferHighestOrRemote;
import com.huawei.hms.mlsdk.dynamic.policy.RemotePolicy;
import java.util.Locale;

/* JADX INFO: loaded from: classes10.dex */
public abstract class AbstractInitializer implements IInitializer {
    private static final String TAG = "AbstractInitializer";
    private Context mApplicationContext;
    private volatile Context mDynamicContext;
    private volatile IInterface mDynamicDelegate;
    public static final IContextPolicy POLICY_LOCAL = new LocalPolicy();
    public static final IContextPolicy POLICY_REMOTE = new RemotePolicy();
    public static final IContextPolicy POLICY_PREFER_HIGHEST_OR_LOCAL = new PreferHighestOrLocal();
    public static final IContextPolicy POLICY_PREFER_HIGHEST_OR_REMOTE = new PreferHighestOrRemote();

    protected abstract IInterface generateDynamicDelegateBridge(IBinder iBinder) throws Exception;

    protected abstract String getCreatorClass();

    protected abstract int getMinApkVersion();

    protected abstract String getModuleName();

    protected Context getApplicationContext() {
        Context context = this.mApplicationContext;
        if (context != null) {
            return context;
        }
        throw new IllegalStateException("initial must be called first");
    }

    @Override // com.huawei.hms.mlsdk.dynamic.IInitializer
    public void initial(Context context) {
        if (context == null) {
            throw new IllegalArgumentException("applicationContext can't be null");
        }
        Context applicationContext = context.getApplicationContext();
        if (applicationContext != null) {
            context = applicationContext;
        }
        this.mApplicationContext = context;
    }

    @Override // com.huawei.hms.mlsdk.dynamic.IInitializer
    public IInterface getDynamicDelegate() {
        if (this.mDynamicDelegate != null) {
            return this.mDynamicDelegate;
        }
        Context dynamicContext = getDynamicContext();
        if (dynamicContext == null) {
            return null;
        }
        try {
            this.mDynamicDelegate = generateDynamicDelegateBridge((IBinder) dynamicContext.getClassLoader().loadClass(getCreatorClass()).newInstance());
        } catch (Exception e) {
            SmartLog.e(TAG, "getDynamicDelegate Exception e: " + e);
            switchDynamicContextIfNeed(dynamicContext, true);
        }
        return this.mDynamicDelegate;
    }

    @Override // com.huawei.hms.mlsdk.dynamic.IInitializer
    public Context getDynamicContext() {
        if (this.mDynamicContext != null) {
            return this.mDynamicContext;
        }
        switchDynamicContextIfNeed(getContextPolicy().getDynamicContext(getApplicationContext(), getModuleName()), false);
        return this.mDynamicContext;
    }

    private Context switchDynamicContextIfNeed(Context context, boolean z) {
        String str;
        if (context == null || context == getApplicationContext()) {
            SmartLog.e(TAG, String.format(Locale.ENGLISH, "%s -> No need to switch because the context of the dynamic module is null or the context of full sdk", getModuleName()));
        } else {
            boolean zIsCreatorClassRemote = isCreatorClassRemote(context);
            boolean zIsLocalExisted = isLocalExisted();
            SmartLog.e(TAG, String.format(Locale.ENGLISH, "%s -> isForce = %b, isCreatorClassRemote = %b, isLocalExisted = %b", getModuleName(), Boolean.valueOf(z), Boolean.valueOf(zIsCreatorClassRemote), Boolean.valueOf(zIsLocalExisted)));
            if ((z || !zIsCreatorClassRemote) && zIsLocalExisted) {
                context = getApplicationContext();
                SmartLog.e(TAG, String.format(Locale.ENGLISH, "%s -> Force to switch the context of the dynamic module to the context of full sdk", getModuleName()));
            }
        }
        Locale locale = Locale.ENGLISH;
        String moduleName = getModuleName();
        if (context == null) {
            str = "NULL";
        } else {
            str = context == getApplicationContext() ? "LOCAL" : "REMOTE";
        }
        SmartLog.e(TAG, String.format(locale, "%s -> The context of the dynamic module belongs to %s", moduleName, str));
        this.mDynamicContext = context;
        return context;
    }

    @Override // com.huawei.hms.mlsdk.dynamic.IInitializer
    public boolean switchDynamicContext() {
        String str;
        Locale locale = Locale.ENGLISH;
        String moduleName = getModuleName();
        if (this.mDynamicContext == null) {
            str = "NULL";
        } else {
            str = this.mDynamicContext == getApplicationContext() ? "LOCAL" : "REMOTE";
        }
        SmartLog.e(TAG, String.format(locale, "%s -> Something wrong, need to switch context, current is %s", moduleName, str));
        if (!isLocalExisted() || this.mDynamicContext == getApplicationContext()) {
            return false;
        }
        this.mDynamicContext = getApplicationContext();
        this.mDynamicDelegate = null;
        SmartLog.e(TAG, String.format(Locale.ENGLISH, "%s -> Switch the context of the dynamic module to %s", getModuleName(), "LOCAL"));
        return true;
    }

    private boolean isCreatorClassRemote(Context context) {
        try {
            ClassLoader classLoader = context.getClassLoader().loadClass(getCreatorClass()).getClassLoader();
            if (classLoader != null) {
                return classLoader.getClass().getName().contains(PackageConstants.SERVICES_PACKAGE_ALL_SCENE);
            }
            return false;
        } catch (ClassNotFoundException e) {
            SmartLog.e(TAG, "isCreatorClassRemote ClassNotFoundException e: " + e);
            return false;
        }
    }

    @Override // com.huawei.hms.mlsdk.dynamic.IInitializer
    public int getLocalVersion() {
        return DynamicModule.getLocalVersion(getApplicationContext(), getModuleName());
    }

    @Override // com.huawei.hms.mlsdk.dynamic.IInitializer
    public int getRemoteVersion() {
        try {
            return DynamicModule.getRemoteVersion(getApplicationContext(), getModuleName());
        } catch (DynamicModule.LoadingException e) {
            SmartLog.e(TAG, "getRemoteVersion DynamicModule.LoadingException e: " + e);
            return -1;
        }
    }

    @Override // com.huawei.hms.mlsdk.dynamic.IInitializer
    public boolean isLocalExisted() {
        return getLocalVersion() > 0;
    }

    @Override // com.huawei.hms.mlsdk.dynamic.IInitializer
    public boolean isRemoteExisted() {
        return getRemoteVersion() > 0;
    }

    @Override // com.huawei.hms.mlsdk.dynamic.IInitializer
    public synchronized Bundle getLoadExceptionBundle() {
        Bundle bundle = null;
        if (this.mDynamicContext == null && getContextPolicy() != POLICY_LOCAL) {
            if (getDynamicContext() != null) {
                return null;
            }
            try {
                DynamicModule.enable3rdPhone(getModuleName(), true);
                DynamicModule.enableLowEMUI(getModuleName(), true);
                DynamicModule.load(getApplicationContext(), DynamicModule.PREFER_REMOTE, getModuleName()).getModuleContext();
            } catch (DynamicModule.LoadingException e) {
                SmartLog.e(TAG, "getLoadExceptionBundle DynamicModule.LoadingException e: " + e);
                bundle = e.getBundle();
            }
            return bundle;
        }
        return null;
    }

    @Override // com.huawei.hms.mlsdk.dynamic.IInitializer
    public boolean isOperational() {
        return getDynamicDelegate() != null;
    }

    @Override // com.huawei.hms.mlsdk.dynamic.IInitializer
    public void release() {
        releaseDynamicContext();
        releaseDynamicDelegate();
    }

    protected void releaseDynamicContext() {
        this.mDynamicContext = null;
    }

    protected void releaseDynamicDelegate() {
        this.mDynamicDelegate = null;
    }

    protected IContextPolicy getContextPolicy() {
        return POLICY_PREFER_HIGHEST_OR_REMOTE;
    }
}
