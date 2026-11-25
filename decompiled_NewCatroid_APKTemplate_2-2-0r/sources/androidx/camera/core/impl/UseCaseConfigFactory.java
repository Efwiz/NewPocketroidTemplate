package androidx.camera.core.impl;

import android.content.Context;
import androidx.camera.core.CameraInfo;
import androidx.camera.core.InitializationException;

/* JADX INFO: loaded from: classes.dex */
public interface UseCaseConfigFactory {

    public interface Provider {
        UseCaseConfigFactory newInstance(Context context) throws InitializationException;
    }

    <C extends UseCaseConfig<?>> C getConfig(Class<C> cls, CameraInfo cameraInfo);
}
