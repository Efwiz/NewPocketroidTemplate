package androidx.camera.camera2.interop;

import android.hardware.camera2.CameraCharacteristics;
import androidx.camera.camera2.internal.Camera2CameraInfoImpl;
import androidx.camera.core.Camera;
import androidx.camera.core.CameraFilter;
import androidx.camera.core.CameraInfo;
import androidx.core.util.Preconditions;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
public final class Camera2CameraFilter {

    public interface Camera2Filter {
        LinkedHashMap<String, CameraCharacteristics> filter(LinkedHashMap<String, CameraCharacteristics> linkedHashMap);
    }

    public static CameraFilter createCameraFilter(final Camera2Filter camera2Filter) {
        return new CameraFilter() { // from class: androidx.camera.camera2.interop.Camera2CameraFilter$$ExternalSyntheticLambda0
            @Override // androidx.camera.core.CameraFilter
            public final LinkedHashSet filter(LinkedHashSet linkedHashSet) {
                return Camera2CameraFilter.lambda$createCameraFilter$0(camera2Filter, linkedHashSet);
            }
        };
    }

    static /* synthetic */ LinkedHashSet lambda$createCameraFilter$0(Camera2Filter camera2Filter, LinkedHashSet linkedHashSet) {
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        LinkedHashMap<String, CameraCharacteristics> linkedHashMap2 = new LinkedHashMap<>();
        Iterator it = linkedHashSet.iterator();
        while (it.hasNext()) {
            Camera camera = (Camera) it.next();
            CameraInfo cameraInfo = camera.getCameraInfo();
            Preconditions.checkState(cameraInfo instanceof Camera2CameraInfoImpl, "CameraInfo does not contain any Camera2 information.");
            Camera2CameraInfoImpl camera2CameraInfoImpl = (Camera2CameraInfoImpl) cameraInfo;
            linkedHashMap.put(camera2CameraInfoImpl.getCameraId(), camera);
            linkedHashMap2.put(camera2CameraInfoImpl.getCameraId(), camera2CameraInfoImpl.getCameraCharacteristics());
        }
        LinkedHashMap<String, CameraCharacteristics> linkedHashMapFilter = camera2Filter.filter(linkedHashMap2);
        LinkedHashSet linkedHashSet2 = new LinkedHashSet();
        Iterator<Map.Entry<String, CameraCharacteristics>> it2 = linkedHashMapFilter.entrySet().iterator();
        while (it2.hasNext()) {
            String key = it2.next().getKey();
            if (linkedHashMap.containsKey(key)) {
                linkedHashSet2.add((Camera) linkedHashMap.get(key));
            } else {
                throw new IllegalArgumentException("There are camera IDs not contained in the original camera map.");
            }
        }
        return linkedHashSet2;
    }

    private Camera2CameraFilter() {
    }
}
