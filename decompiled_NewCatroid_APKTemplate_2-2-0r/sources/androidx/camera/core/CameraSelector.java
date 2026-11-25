package androidx.camera.core;

import androidx.camera.core.impl.CameraInternal;
import androidx.camera.core.impl.LensFacingCameraFilter;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Iterator;
import java.util.LinkedHashSet;

/* JADX INFO: loaded from: classes.dex */
public final class CameraSelector {
    public static final int LENS_FACING_BACK = 1;
    public static final int LENS_FACING_FRONT = 0;
    private LinkedHashSet<CameraFilter> mCameraFilterSet;
    public static final CameraSelector DEFAULT_FRONT_CAMERA = new Builder().requireLensFacing(0).build();
    public static final CameraSelector DEFAULT_BACK_CAMERA = new Builder().requireLensFacing(1).build();

    @Retention(RetentionPolicy.SOURCE)
    public @interface LensFacing {
    }

    CameraSelector(LinkedHashSet<CameraFilter> linkedHashSet) {
        this.mCameraFilterSet = linkedHashSet;
    }

    public CameraInternal select(LinkedHashSet<CameraInternal> linkedHashSet) {
        return filter(linkedHashSet).iterator().next();
    }

    public LinkedHashSet<CameraInternal> filter(LinkedHashSet<CameraInternal> linkedHashSet) {
        LinkedHashSet linkedHashSet2 = new LinkedHashSet(linkedHashSet);
        LinkedHashSet<Camera> linkedHashSet3 = new LinkedHashSet<>(linkedHashSet);
        Iterator<CameraFilter> it = this.mCameraFilterSet.iterator();
        while (it.hasNext()) {
            linkedHashSet3 = it.next().filter(linkedHashSet3);
            if (linkedHashSet3.isEmpty()) {
                throw new IllegalArgumentException("No available camera can be found.");
            }
            if (!linkedHashSet2.containsAll(linkedHashSet3)) {
                throw new IllegalArgumentException("The output isn't contained in the input.");
            }
            linkedHashSet2.retainAll(linkedHashSet3);
        }
        LinkedHashSet<CameraInternal> linkedHashSet4 = new LinkedHashSet<>();
        Iterator<Camera> it2 = linkedHashSet3.iterator();
        while (it2.hasNext()) {
            linkedHashSet4.add((CameraInternal) it2.next());
        }
        return linkedHashSet4;
    }

    public LinkedHashSet<CameraFilter> getCameraFilterSet() {
        return this.mCameraFilterSet;
    }

    public Integer getLensFacing() {
        Integer num = null;
        for (CameraFilter cameraFilter : this.mCameraFilterSet) {
            if (cameraFilter instanceof LensFacingCameraFilter) {
                Integer numValueOf = Integer.valueOf(((LensFacingCameraFilter) cameraFilter).getLensFacing());
                if (num == null) {
                    num = numValueOf;
                } else if (!num.equals(numValueOf)) {
                    throw new IllegalStateException("Multiple conflicting lens facing requirements exist.");
                }
            }
        }
        return num;
    }

    public static final class Builder {
        private final LinkedHashSet<CameraFilter> mCameraFilterSet;

        public Builder() {
            this.mCameraFilterSet = new LinkedHashSet<>();
        }

        private Builder(LinkedHashSet<CameraFilter> linkedHashSet) {
            this.mCameraFilterSet = new LinkedHashSet<>(linkedHashSet);
        }

        public Builder requireLensFacing(int i) {
            this.mCameraFilterSet.add(new LensFacingCameraFilter(i));
            return this;
        }

        public Builder addCameraFilter(CameraFilter cameraFilter) {
            this.mCameraFilterSet.add(cameraFilter);
            return this;
        }

        public static Builder fromSelector(CameraSelector cameraSelector) {
            return new Builder(cameraSelector.getCameraFilterSet());
        }

        public CameraSelector build() {
            return new CameraSelector(this.mCameraFilterSet);
        }
    }
}
