package androidx.camera.camera2.internal;

import android.content.Context;
import android.util.Rational;
import android.view.WindowManager;
import androidx.camera.core.CameraInfo;
import androidx.camera.core.Preview;
import androidx.camera.core.impl.CaptureConfig;
import androidx.camera.core.impl.ConfigProvider;
import androidx.camera.core.impl.ImageOutputConfig;
import androidx.camera.core.impl.PreviewConfig;
import androidx.camera.core.impl.SessionConfig;
import org.eclipse.jgit.lib.ConfigConstants;

/* JADX INFO: loaded from: classes.dex */
public final class PreviewConfigProvider implements ConfigProvider<PreviewConfig> {
    private static final String TAG = "PreviewConfigProvider";
    private final WindowManager mWindowManager;

    public PreviewConfigProvider(Context context) {
        this.mWindowManager = (WindowManager) context.getSystemService(ConfigConstants.CONFIG_KEY_WINDOW);
    }

    @Override // androidx.camera.core.impl.ConfigProvider
    public PreviewConfig getConfig(CameraInfo cameraInfo) {
        Rational rational;
        Preview.Builder builderFromConfig = Preview.Builder.fromConfig(Preview.DEFAULT_CONFIG.getConfig(cameraInfo));
        SessionConfig.Builder builder = new SessionConfig.Builder();
        builder.setTemplateType(1);
        builderFromConfig.setDefaultSessionConfig(builder.build());
        builderFromConfig.setSessionOptionUnpacker((SessionConfig.OptionUnpacker) Camera2SessionOptionUnpacker.INSTANCE);
        CaptureConfig.Builder builder2 = new CaptureConfig.Builder();
        builder2.setTemplateType(1);
        builderFromConfig.setDefaultCaptureConfig(builder2.build());
        builderFromConfig.setCaptureOptionUnpacker((CaptureConfig.OptionUnpacker) Camera2CaptureOptionUnpacker.INSTANCE);
        int rotation = this.mWindowManager.getDefaultDisplay().getRotation();
        builderFromConfig.setTargetRotation(rotation);
        if (cameraInfo != null) {
            int sensorRotationDegrees = cameraInfo.getSensorRotationDegrees(rotation);
            if (sensorRotationDegrees == 90 || sensorRotationDegrees == 270) {
                rational = ImageOutputConfig.DEFAULT_ASPECT_RATIO_PORTRAIT;
            } else {
                rational = ImageOutputConfig.DEFAULT_ASPECT_RATIO_LANDSCAPE;
            }
            builderFromConfig.setTargetAspectRatioCustom(rational);
        }
        return builderFromConfig.getUseCaseConfig();
    }
}
