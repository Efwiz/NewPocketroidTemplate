package androidx.camera.core.impl;

import androidx.camera.core.Camera;
import androidx.camera.core.CameraFilter;
import androidx.core.util.Preconditions;
import java.util.LinkedHashSet;

/* JADX INFO: loaded from: classes.dex */
public class LensFacingCameraFilter implements CameraFilter {
    private int mLensFacing;

    public LensFacingCameraFilter(int i) {
        this.mLensFacing = i;
    }

    @Override // androidx.camera.core.CameraFilter
    public LinkedHashSet<Camera> filter(LinkedHashSet<Camera> linkedHashSet) {
        LinkedHashSet<Camera> linkedHashSet2 = new LinkedHashSet<>();
        for (Camera camera : linkedHashSet) {
            Preconditions.checkState(camera instanceof CameraInternal, "The camera doesn't contain internal implementation.");
            Integer lensFacing = ((CameraInternal) camera).getCameraInfoInternal().getLensFacing();
            if (lensFacing != null && lensFacing.intValue() == this.mLensFacing) {
                linkedHashSet2.add(camera);
            }
        }
        return linkedHashSet2;
    }

    public int getLensFacing() {
        return this.mLensFacing;
    }
}
