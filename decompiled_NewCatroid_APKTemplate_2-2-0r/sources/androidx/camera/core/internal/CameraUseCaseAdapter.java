package androidx.camera.core.internal;

import android.graphics.Rect;
import android.util.Log;
import android.util.Size;
import androidx.camera.core.UseCase;
import androidx.camera.core.ViewPort;
import androidx.camera.core.impl.CameraControlInternal;
import androidx.camera.core.impl.CameraDeviceSurfaceManager;
import androidx.camera.core.impl.CameraInfoInternal;
import androidx.camera.core.impl.CameraInternal;
import androidx.camera.core.impl.UseCaseConfig;
import androidx.core.util.Preconditions;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
public final class CameraUseCaseAdapter {
    private static final String TAG = "CameraUseCaseAdapter";
    private final CameraDeviceSurfaceManager mCameraDeviceSurfaceManager;
    private final CameraInternal mCameraInternal;
    private final LinkedHashSet<CameraInternal> mCameraInternals;
    private final CameraId mId;
    private ViewPort mViewPort;
    private final List<UseCase> mUseCases = new ArrayList();
    private final Object mLock = new Object();
    private boolean mAttached = true;

    public CameraUseCaseAdapter(CameraInternal cameraInternal, LinkedHashSet<CameraInternal> linkedHashSet, CameraDeviceSurfaceManager cameraDeviceSurfaceManager) {
        this.mCameraInternal = cameraInternal;
        LinkedHashSet<CameraInternal> linkedHashSet2 = new LinkedHashSet<>(linkedHashSet);
        this.mCameraInternals = linkedHashSet2;
        this.mId = new CameraId(linkedHashSet2);
        this.mCameraDeviceSurfaceManager = cameraDeviceSurfaceManager;
    }

    public static CameraId generateCameraId(LinkedHashSet<CameraInternal> linkedHashSet) {
        return new CameraId(linkedHashSet);
    }

    public CameraId getCameraId() {
        return this.mId;
    }

    public boolean isEquivalent(CameraUseCaseAdapter cameraUseCaseAdapter) {
        return this.mId.equals(cameraUseCaseAdapter.getCameraId());
    }

    public void setViewPort(ViewPort viewPort) {
        synchronized (this.mLock) {
            this.mViewPort = viewPort;
        }
    }

    public void checkAttachUseCases(List<UseCase> list) throws CameraException {
        if (!UseCaseOccupancy.checkUseCaseLimitNotExceeded(list)) {
            throw new CameraException("Attempting to bind too many ImageCapture or VideoCapture instances");
        }
        try {
            calculateSuggestedResolutions(list, Collections.emptyList());
        } catch (IllegalArgumentException e) {
            throw new CameraException(e.getMessage());
        }
    }

    public void addUseCases(Collection<UseCase> collection) throws CameraException {
        synchronized (this.mLock) {
            ArrayList arrayList = new ArrayList(this.mUseCases);
            ArrayList arrayList2 = new ArrayList();
            for (UseCase useCase : collection) {
                if (this.mUseCases.contains(useCase)) {
                    Log.e(TAG, "Attempting to attach already attached UseCase");
                } else {
                    arrayList.add(useCase);
                    arrayList2.add(useCase);
                }
            }
            if (!UseCaseOccupancy.checkUseCaseLimitNotExceeded(arrayList)) {
                throw new CameraException("Attempting to bind too many ImageCapture or VideoCapture instances");
            }
            try {
                Map<UseCase, Size> mapCalculateSuggestedResolutions = calculateSuggestedResolutions(arrayList2, this.mUseCases);
                if (this.mViewPort != null) {
                    Map<UseCase, Rect> mapCalculateViewPortRects = ViewPorts.calculateViewPortRects(this.mCameraInternal.getCameraControlInternal().getSensorRect(), this.mViewPort.getAspectRatio(), this.mCameraInternal.getCameraInfoInternal().getSensorRotationDegrees(this.mViewPort.getRotation()), this.mViewPort.getScaleType(), this.mViewPort.getLayoutDirection(), mapCalculateSuggestedResolutions);
                    for (UseCase useCase2 : collection) {
                        useCase2.setViewPortCropRect(mapCalculateViewPortRects.get(useCase2));
                    }
                }
                for (UseCase useCase3 : arrayList2) {
                    useCase3.onAttach(this.mCameraInternal);
                    useCase3.updateSuggestedResolution((Size) Preconditions.checkNotNull(mapCalculateSuggestedResolutions.get(useCase3)));
                }
                this.mUseCases.addAll(arrayList2);
                if (this.mAttached) {
                    this.mCameraInternal.attachUseCases(arrayList2);
                }
                Iterator<UseCase> it = arrayList2.iterator();
                while (it.hasNext()) {
                    it.next().notifyState();
                }
            } catch (IllegalArgumentException e) {
                throw new CameraException(e.getMessage());
            }
        }
    }

    public void removeUseCases(Collection<UseCase> collection) {
        synchronized (this.mLock) {
            this.mCameraInternal.detachUseCases(collection);
            for (UseCase useCase : collection) {
                if (this.mUseCases.contains(useCase)) {
                    useCase.onDetach(this.mCameraInternal);
                    useCase.onDestroy();
                } else {
                    Log.e(TAG, "Attempting to detach non-attached UseCase: " + useCase);
                }
            }
            this.mUseCases.removeAll(collection);
        }
    }

    public List<UseCase> getUseCases() {
        ArrayList arrayList;
        synchronized (this.mLock) {
            arrayList = new ArrayList(this.mUseCases);
        }
        return arrayList;
    }

    public void attachUseCases() {
        synchronized (this.mLock) {
            if (!this.mAttached) {
                this.mCameraInternal.attachUseCases(this.mUseCases);
                this.mAttached = true;
            }
        }
    }

    public void detachUseCases() {
        synchronized (this.mLock) {
            if (this.mAttached) {
                this.mCameraInternal.detachUseCases(this.mUseCases);
                this.mAttached = false;
            }
        }
    }

    private Map<UseCase, Size> calculateSuggestedResolutions(List<UseCase> list, List<UseCase> list2) {
        ArrayList arrayList = new ArrayList();
        String cameraId = this.mCameraInternal.getCameraInfoInternal().getCameraId();
        HashMap map = new HashMap();
        for (UseCase useCase : list2) {
            arrayList.add(this.mCameraDeviceSurfaceManager.transformSurfaceConfig(cameraId, useCase.getImageFormat(), useCase.getAttachedSurfaceResolution()));
        }
        for (UseCase useCase2 : list) {
            map.put(useCase2.applyDefaults(useCase2.getUseCaseConfig(), useCase2.getDefaultBuilder(this.mCameraInternal.getCameraInfoInternal())), useCase2);
        }
        Map<UseCaseConfig<?>, Size> suggestedResolutions = this.mCameraDeviceSurfaceManager.getSuggestedResolutions(cameraId, arrayList, new ArrayList(map.keySet()));
        HashMap map2 = new HashMap();
        for (Map.Entry entry : map.entrySet()) {
            map2.put((UseCase) entry.getValue(), suggestedResolutions.get(entry.getKey()));
        }
        return map2;
    }

    public CameraInfoInternal getCameraInfoInternal() {
        return this.mCameraInternal.getCameraInfoInternal();
    }

    public CameraControlInternal getCameraControlInternal() {
        return this.mCameraInternal.getCameraControlInternal();
    }

    public static final class CameraId {
        private final List<String> mIds = new ArrayList();

        CameraId(LinkedHashSet<CameraInternal> linkedHashSet) {
            Iterator<CameraInternal> it = linkedHashSet.iterator();
            while (it.hasNext()) {
                this.mIds.add(it.next().getCameraInfoInternal().getCameraId());
            }
        }

        public boolean equals(Object obj) {
            if (obj instanceof CameraId) {
                return this.mIds.equals(((CameraId) obj).mIds);
            }
            return false;
        }

        public int hashCode() {
            return this.mIds.hashCode() * 53;
        }
    }

    public static final class CameraException extends Exception {
        public CameraException() {
        }

        public CameraException(String str) {
            super(str);
        }

        public CameraException(Throwable th) {
            super(th);
        }
    }
}
