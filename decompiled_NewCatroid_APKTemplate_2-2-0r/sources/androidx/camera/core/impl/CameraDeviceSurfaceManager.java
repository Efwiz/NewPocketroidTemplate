package androidx.camera.core.impl;

import android.content.Context;
import android.util.Size;
import androidx.camera.core.InitializationException;
import java.util.List;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
public interface CameraDeviceSurfaceManager {

    public interface Provider {
        CameraDeviceSurfaceManager newInstance(Context context) throws InitializationException;
    }

    boolean checkSupported(String str, List<SurfaceConfig> list);

    Size getMaxOutputSize(String str, int i);

    Size getPreviewSize();

    Map<UseCaseConfig<?>, Size> getSuggestedResolutions(String str, List<SurfaceConfig> list, List<UseCaseConfig<?>> list2);

    SurfaceConfig transformSurfaceConfig(String str, int i, Size size);
}
