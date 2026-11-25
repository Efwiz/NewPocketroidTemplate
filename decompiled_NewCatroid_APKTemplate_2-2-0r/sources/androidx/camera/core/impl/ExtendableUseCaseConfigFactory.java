package androidx.camera.core.impl;

import androidx.camera.core.CameraInfo;
import java.util.HashMap;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
public final class ExtendableUseCaseConfigFactory implements UseCaseConfigFactory {
    private final Map<Class<?>, ConfigProvider<?>> mDefaultProviders = new HashMap();

    public <C extends Config> void installDefaultProvider(Class<C> cls, ConfigProvider<C> configProvider) {
        this.mDefaultProviders.put(cls, configProvider);
    }

    @Override // androidx.camera.core.impl.UseCaseConfigFactory
    public <C extends UseCaseConfig<?>> C getConfig(Class<C> cls, CameraInfo cameraInfo) {
        ConfigProvider<?> configProvider = this.mDefaultProviders.get(cls);
        if (configProvider != null) {
            return (C) configProvider.getConfig(cameraInfo);
        }
        return null;
    }
}
