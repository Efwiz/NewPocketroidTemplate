package androidx.camera.camera2;

import android.content.Context;
import androidx.camera.camera2.internal.Camera2CameraFactory;
import androidx.camera.camera2.internal.Camera2DeviceSurfaceManager;
import androidx.camera.camera2.internal.ImageAnalysisConfigProvider;
import androidx.camera.camera2.internal.ImageCaptureConfigProvider;
import androidx.camera.camera2.internal.PreviewConfigProvider;
import androidx.camera.camera2.internal.VideoCaptureConfigProvider;
import androidx.camera.core.CameraUnavailableException;
import androidx.camera.core.CameraXConfig;
import androidx.camera.core.InitializationException;
import androidx.camera.core.impl.CameraDeviceSurfaceManager;
import androidx.camera.core.impl.CameraFactory;
import androidx.camera.core.impl.CameraThreadConfig;
import androidx.camera.core.impl.ExtendableUseCaseConfigFactory;
import androidx.camera.core.impl.ImageAnalysisConfig;
import androidx.camera.core.impl.ImageCaptureConfig;
import androidx.camera.core.impl.PreviewConfig;
import androidx.camera.core.impl.UseCaseConfigFactory;
import androidx.camera.core.impl.VideoCaptureConfig;

/* JADX INFO: loaded from: classes.dex */
public final class Camera2Config {
    private Camera2Config() {
    }

    public static CameraXConfig defaultConfig() {
        CameraFactory.Provider provider = new CameraFactory.Provider() { // from class: androidx.camera.camera2.Camera2Config$$ExternalSyntheticLambda0
            @Override // androidx.camera.core.impl.CameraFactory.Provider
            public final CameraFactory newInstance(Context context, CameraThreadConfig cameraThreadConfig) {
                return new Camera2CameraFactory(context, cameraThreadConfig);
            }
        };
        CameraDeviceSurfaceManager.Provider provider2 = new CameraDeviceSurfaceManager.Provider() { // from class: androidx.camera.camera2.Camera2Config$$ExternalSyntheticLambda1
            @Override // androidx.camera.core.impl.CameraDeviceSurfaceManager.Provider
            public final CameraDeviceSurfaceManager newInstance(Context context) {
                return Camera2Config.lambda$defaultConfig$0(context);
            }
        };
        return new CameraXConfig.Builder().setCameraFactoryProvider(provider).setDeviceSurfaceManagerProvider(provider2).setUseCaseConfigFactoryProvider(new UseCaseConfigFactory.Provider() { // from class: androidx.camera.camera2.Camera2Config$$ExternalSyntheticLambda2
            @Override // androidx.camera.core.impl.UseCaseConfigFactory.Provider
            public final UseCaseConfigFactory newInstance(Context context) {
                return Camera2Config.lambda$defaultConfig$1(context);
            }
        }).build();
    }

    static /* synthetic */ CameraDeviceSurfaceManager lambda$defaultConfig$0(Context context) throws InitializationException {
        try {
            return new Camera2DeviceSurfaceManager(context);
        } catch (CameraUnavailableException e) {
            throw new InitializationException(e);
        }
    }

    static /* synthetic */ UseCaseConfigFactory lambda$defaultConfig$1(Context context) throws InitializationException {
        ExtendableUseCaseConfigFactory extendableUseCaseConfigFactory = new ExtendableUseCaseConfigFactory();
        extendableUseCaseConfigFactory.installDefaultProvider(ImageAnalysisConfig.class, new ImageAnalysisConfigProvider(context));
        extendableUseCaseConfigFactory.installDefaultProvider(ImageCaptureConfig.class, new ImageCaptureConfigProvider(context));
        extendableUseCaseConfigFactory.installDefaultProvider(VideoCaptureConfig.class, new VideoCaptureConfigProvider(context));
        extendableUseCaseConfigFactory.installDefaultProvider(PreviewConfig.class, new PreviewConfigProvider(context));
        return extendableUseCaseConfigFactory;
    }

    public static final class DefaultProvider implements CameraXConfig.Provider {
        @Override // androidx.camera.core.CameraXConfig.Provider
        public CameraXConfig getCameraXConfig() {
            return Camera2Config.defaultConfig();
        }
    }
}
