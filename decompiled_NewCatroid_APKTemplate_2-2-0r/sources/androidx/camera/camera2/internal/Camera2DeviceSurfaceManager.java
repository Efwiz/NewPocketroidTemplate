package androidx.camera.camera2.internal;

import android.content.Context;
import android.media.CamcorderProfile;
import android.util.Size;
import androidx.camera.camera2.internal.compat.CameraAccessExceptionCompat;
import androidx.camera.camera2.internal.compat.CameraManagerCompat;
import androidx.camera.core.CameraUnavailableException;
import androidx.camera.core.impl.CameraDeviceSurfaceManager;
import androidx.camera.core.impl.SurfaceConfig;
import androidx.camera.core.impl.UseCaseConfig;
import androidx.core.util.Preconditions;
import com.hivemq.client.mqtt.MqttProxyConfig;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
public final class Camera2DeviceSurfaceManager implements CameraDeviceSurfaceManager {
    private static final Size MAXIMUM_PREVIEW_SIZE = new Size(1920, MqttProxyConfig.DEFAULT_SOCKS_PROXY_PORT);
    private static final String TAG = "Camera2DeviceSurfaceManager";
    private final CamcorderProfileHelper mCamcorderProfileHelper;
    private final Map<String, SupportedSurfaceCombination> mCameraSupportedSurfaceCombinationMap;

    public Camera2DeviceSurfaceManager(Context context) throws CameraUnavailableException {
        this(context, new CamcorderProfileHelper() { // from class: androidx.camera.camera2.internal.Camera2DeviceSurfaceManager$$ExternalSyntheticLambda0
            @Override // androidx.camera.camera2.internal.CamcorderProfileHelper
            public final boolean hasProfile(int i, int i2) {
                return CamcorderProfile.hasProfile(i, i2);
            }
        });
    }

    Camera2DeviceSurfaceManager(Context context, CamcorderProfileHelper camcorderProfileHelper) throws CameraUnavailableException {
        this.mCameraSupportedSurfaceCombinationMap = new HashMap();
        Preconditions.checkNotNull(camcorderProfileHelper);
        this.mCamcorderProfileHelper = camcorderProfileHelper;
        init(context);
    }

    private void init(Context context) throws CameraUnavailableException {
        Preconditions.checkNotNull(context);
        try {
            for (String str : CameraManagerCompat.from(context).getCameraIdList()) {
                this.mCameraSupportedSurfaceCombinationMap.put(str, new SupportedSurfaceCombination(context, str, this.mCamcorderProfileHelper));
            }
        } catch (CameraAccessExceptionCompat e) {
            throw CameraUnavailableExceptionHelper.createFrom(e);
        }
    }

    @Override // androidx.camera.core.impl.CameraDeviceSurfaceManager
    public boolean checkSupported(String str, List<SurfaceConfig> list) {
        if (list == null || list.isEmpty()) {
            return true;
        }
        SupportedSurfaceCombination supportedSurfaceCombination = this.mCameraSupportedSurfaceCombinationMap.get(str);
        if (supportedSurfaceCombination != null) {
            return supportedSurfaceCombination.checkSupported(list);
        }
        return false;
    }

    @Override // androidx.camera.core.impl.CameraDeviceSurfaceManager
    public SurfaceConfig transformSurfaceConfig(String str, int i, Size size) {
        SupportedSurfaceCombination supportedSurfaceCombination = this.mCameraSupportedSurfaceCombinationMap.get(str);
        if (supportedSurfaceCombination != null) {
            return supportedSurfaceCombination.transformSurfaceConfig(i, size);
        }
        return null;
    }

    @Override // androidx.camera.core.impl.CameraDeviceSurfaceManager
    public Map<UseCaseConfig<?>, Size> getSuggestedResolutions(String str, List<SurfaceConfig> list, List<UseCaseConfig<?>> list2) {
        Preconditions.checkArgument(!list2.isEmpty(), "No new use cases to be bound.");
        ArrayList arrayList = new ArrayList(list);
        Iterator<UseCaseConfig<?>> it = list2.iterator();
        while (it.hasNext()) {
            arrayList.add(transformSurfaceConfig(str, it.next().getInputFormat(), new Size(640, 480)));
        }
        SupportedSurfaceCombination supportedSurfaceCombination = this.mCameraSupportedSurfaceCombinationMap.get(str);
        if (supportedSurfaceCombination == null) {
            throw new IllegalArgumentException("No such camera id in supported combination list: " + str);
        }
        if (!supportedSurfaceCombination.checkSupported(arrayList)) {
            throw new IllegalArgumentException("No supported surface combination is found for camera device - Id : " + str + ".  May be attempting to bind too many use cases. Existing surfaces: " + list + " New configs: " + list2);
        }
        return supportedSurfaceCombination.getSuggestedResolutions(list, list2);
    }

    @Override // androidx.camera.core.impl.CameraDeviceSurfaceManager
    public Size getMaxOutputSize(String str, int i) {
        SupportedSurfaceCombination supportedSurfaceCombination = this.mCameraSupportedSurfaceCombinationMap.get(str);
        if (supportedSurfaceCombination == null) {
            throw new IllegalArgumentException("Fail to find supported surface info - CameraId:" + str);
        }
        return supportedSurfaceCombination.getMaxOutputSizeByFormat(i);
    }

    @Override // androidx.camera.core.impl.CameraDeviceSurfaceManager
    public Size getPreviewSize() {
        Size size = MAXIMUM_PREVIEW_SIZE;
        if (this.mCameraSupportedSurfaceCombinationMap.isEmpty()) {
            return size;
        }
        return this.mCameraSupportedSurfaceCombinationMap.get((String) this.mCameraSupportedSurfaceCombinationMap.keySet().toArray()[0]).getSurfaceSizeDefinition().getPreviewSize();
    }
}
