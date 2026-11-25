package androidx.camera.camera2.internal;

import android.os.Build;
import android.util.Range;
import android.util.Size;
import com.huawei.hms.mlsdk.asr.engine.AsrError;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

/* JADX INFO: loaded from: classes.dex */
final class SupportedSizeConstraints {
    private static final Range<Integer> ALL_API_LEVELS;
    private static final String ALL_MODELS = "allmodels";
    private static final Map<CameraDeviceId, List<ExcludedSizeConstraint>> EXCLUDED_SIZES_MAP;

    interface Constraint {
        Range<Integer> getAffectedApiLevels();

        Set<Integer> getAffectedFormats();
    }

    static {
        Range<Integer> range = new Range<>(0, Integer.MAX_VALUE);
        ALL_API_LEVELS = range;
        TreeMap treeMap = new TreeMap(new Comparator<CameraDeviceId>() { // from class: androidx.camera.camera2.internal.SupportedSizeConstraints.1
            @Override // java.util.Comparator
            public int compare(CameraDeviceId cameraDeviceId, CameraDeviceId cameraDeviceId2) {
                int iCompareTo;
                if (cameraDeviceId.equals(cameraDeviceId2)) {
                    return 0;
                }
                int iCompareTo2 = cameraDeviceId.getBrand().compareTo(cameraDeviceId2.getBrand());
                if (iCompareTo2 != 0) {
                    return iCompareTo2;
                }
                int iCompareTo3 = cameraDeviceId.getDevice().compareTo(cameraDeviceId2.getDevice());
                if (iCompareTo3 != 0) {
                    return iCompareTo3;
                }
                if (!SupportedSizeConstraints.ALL_MODELS.equals(cameraDeviceId.getModel()) && !SupportedSizeConstraints.ALL_MODELS.equals(cameraDeviceId2.getModel()) && (iCompareTo = cameraDeviceId.getModel().compareTo(cameraDeviceId2.getModel())) != 0) {
                    return iCompareTo;
                }
                int iCompareTo4 = cameraDeviceId.getCameraId().compareTo(cameraDeviceId2.getCameraId());
                if (iCompareTo4 != 0) {
                    return iCompareTo4;
                }
                return 0;
            }
        });
        EXCLUDED_SIZES_MAP = treeMap;
        treeMap.put(CameraDeviceId.create("OnePlus", "OnePlus6T", ALL_MODELS, "0"), Collections.singletonList(ExcludedSizeConstraint.create(Collections.singleton(256), range, Arrays.asList(new Size(4160, 3120), new Size(AsrError.SUBCODE_INVALID, 3000)))));
        treeMap.put(CameraDeviceId.create("OnePlus", "OnePlus6", ALL_MODELS, "0"), Collections.singletonList(ExcludedSizeConstraint.create(Collections.singleton(256), range, Arrays.asList(new Size(4160, 3120), new Size(AsrError.SUBCODE_INVALID, 3000)))));
    }

    static List<Size> getExcludedSizes(String str, int i) {
        CameraDeviceId cameraDeviceIdCreate = CameraDeviceId.create(Build.BRAND, Build.DEVICE, Build.MODEL, str);
        Map<CameraDeviceId, List<ExcludedSizeConstraint>> map = EXCLUDED_SIZES_MAP;
        if (map.containsKey(cameraDeviceIdCreate)) {
            ArrayList arrayList = new ArrayList();
            for (ExcludedSizeConstraint excludedSizeConstraint : map.get(cameraDeviceIdCreate)) {
                if (excludedSizeConstraint.getAffectedFormats().contains(Integer.valueOf(i)) && excludedSizeConstraint.getAffectedApiLevels().contains(Integer.valueOf(Build.VERSION.SDK_INT))) {
                    arrayList.addAll(excludedSizeConstraint.getExcludedSizes());
                }
            }
            return arrayList;
        }
        return Collections.emptyList();
    }

    private SupportedSizeConstraints() {
    }

    static abstract class ExcludedSizeConstraint implements Constraint {
        @Override // androidx.camera.camera2.internal.SupportedSizeConstraints.Constraint
        public abstract Range<Integer> getAffectedApiLevels();

        @Override // androidx.camera.camera2.internal.SupportedSizeConstraints.Constraint
        public abstract Set<Integer> getAffectedFormats();

        public abstract List<Size> getExcludedSizes();

        ExcludedSizeConstraint() {
        }

        public static ExcludedSizeConstraint create(Set<Integer> set, Range<Integer> range, List<Size> list) {
            return new AutoValue_SupportedSizeConstraints_ExcludedSizeConstraint(set, range, list);
        }
    }
}
