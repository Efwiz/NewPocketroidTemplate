package androidx.camera.core;

import android.graphics.Rect;
import android.util.Size;
import androidx.camera.core.impl.CameraControlInternal;
import androidx.camera.core.impl.CameraInternal;
import androidx.camera.core.impl.Config;
import androidx.camera.core.impl.ImageOutputConfig;
import androidx.camera.core.impl.MutableConfig;
import androidx.camera.core.impl.SessionConfig;
import androidx.camera.core.impl.UseCaseConfig;
import androidx.core.util.Preconditions;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Objects;
import java.util.Set;

/* JADX INFO: loaded from: classes.dex */
public abstract class UseCase {
    private Size mAttachedResolution;
    private CameraInternal mCamera;
    private UseCaseConfig<?> mUseCaseConfig;
    private Rect mViewPortCropRect;
    private final Set<StateChangeCallback> mStateChangeCallbacks = new HashSet();
    private SessionConfig mAttachedSessionConfig = SessionConfig.defaultEmptySessionConfig();
    private State mState = State.INACTIVE;
    private final Object mCameraLock = new Object();

    public interface EventCallback {
        void onBind(String str);

        void onUnbind();
    }

    enum State {
        ACTIVE,
        INACTIVE
    }

    public interface StateChangeCallback {
        void onUseCaseActive(UseCase useCase);

        void onUseCaseInactive(UseCase useCase);

        void onUseCaseReset(UseCase useCase);

        void onUseCaseUpdated(UseCase useCase);
    }

    public void clear() {
    }

    public UseCaseConfig.Builder<?, ?, ?> getDefaultBuilder(CameraInfo cameraInfo) {
        return null;
    }

    protected void onCameraControlReady() {
    }

    public void onDestroy() {
    }

    public void onStateAttached() {
    }

    public void onStateDetached() {
    }

    protected abstract Size onSuggestedResolutionUpdated(Size size);

    protected UseCase(UseCaseConfig<?> useCaseConfig) {
        updateUseCaseConfig(useCaseConfig);
    }

    protected final void updateUseCaseConfig(UseCaseConfig<?> useCaseConfig) {
        this.mUseCaseConfig = applyDefaults(useCaseConfig, getDefaultBuilder(getCamera() == null ? null : getCamera().getCameraInfoInternal()));
    }

    public UseCaseConfig<?> applyDefaults(UseCaseConfig<?> useCaseConfig, UseCaseConfig.Builder<?, ?, ?> builder) {
        if (builder == null) {
            return useCaseConfig;
        }
        MutableConfig mutableConfig = builder.getMutableConfig();
        if (useCaseConfig.containsOption(ImageOutputConfig.OPTION_TARGET_ASPECT_RATIO) && mutableConfig.containsOption(ImageOutputConfig.OPTION_TARGET_ASPECT_RATIO_CUSTOM)) {
            mutableConfig.removeOption(ImageOutputConfig.OPTION_TARGET_ASPECT_RATIO_CUSTOM);
        }
        for (Config.Option<?> option : useCaseConfig.listOptions()) {
            mutableConfig.insertOption(option, useCaseConfig.getOptionPriority(option), useCaseConfig.retrieveOption(option));
        }
        return builder.getUseCaseConfig();
    }

    protected void updateSessionConfig(SessionConfig sessionConfig) {
        this.mAttachedSessionConfig = sessionConfig;
    }

    private void addStateChangeCallback(StateChangeCallback stateChangeCallback) {
        this.mStateChangeCallbacks.add(stateChangeCallback);
    }

    private void removeStateChangeCallback(StateChangeCallback stateChangeCallback) {
        this.mStateChangeCallbacks.remove(stateChangeCallback);
    }

    public SessionConfig getSessionConfig() {
        return this.mAttachedSessionConfig;
    }

    protected final void notifyActive() {
        this.mState = State.ACTIVE;
        notifyState();
    }

    protected final void notifyInactive() {
        this.mState = State.INACTIVE;
        notifyState();
    }

    protected final void notifyUpdated() {
        Iterator<StateChangeCallback> it = this.mStateChangeCallbacks.iterator();
        while (it.hasNext()) {
            it.next().onUseCaseUpdated(this);
        }
    }

    protected final void notifyReset() {
        Iterator<StateChangeCallback> it = this.mStateChangeCallbacks.iterator();
        while (it.hasNext()) {
            it.next().onUseCaseReset(this);
        }
    }

    /* JADX INFO: renamed from: androidx.camera.core.UseCase$1, reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$androidx$camera$core$UseCase$State;

        static {
            int[] iArr = new int[State.values().length];
            $SwitchMap$androidx$camera$core$UseCase$State = iArr;
            try {
                iArr[State.INACTIVE.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$androidx$camera$core$UseCase$State[State.ACTIVE.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
        }
    }

    public final void notifyState() {
        int i = AnonymousClass1.$SwitchMap$androidx$camera$core$UseCase$State[this.mState.ordinal()];
        if (i == 1) {
            Iterator<StateChangeCallback> it = this.mStateChangeCallbacks.iterator();
            while (it.hasNext()) {
                it.next().onUseCaseInactive(this);
            }
        } else {
            if (i != 2) {
                return;
            }
            Iterator<StateChangeCallback> it2 = this.mStateChangeCallbacks.iterator();
            while (it2.hasNext()) {
                it2.next().onUseCaseActive(this);
            }
        }
    }

    protected String getCameraId() {
        return ((CameraInternal) Preconditions.checkNotNull(getCamera(), "No camera attached to use case: " + this)).getCameraInfoInternal().getCameraId();
    }

    protected boolean isCurrentCamera(String str) {
        if (getCamera() == null) {
            return false;
        }
        return Objects.equals(str, getCameraId());
    }

    public String getName() {
        return this.mUseCaseConfig.getTargetName("<UnknownUseCase-" + hashCode() + ">");
    }

    public UseCaseConfig<?> getUseCaseConfig() {
        return this.mUseCaseConfig;
    }

    public CameraInternal getCamera() {
        CameraInternal cameraInternal;
        synchronized (this.mCameraLock) {
            cameraInternal = this.mCamera;
        }
        return cameraInternal;
    }

    public Size getAttachedSurfaceResolution() {
        return this.mAttachedResolution;
    }

    public void updateSuggestedResolution(Size size) {
        this.mAttachedResolution = onSuggestedResolutionUpdated(size);
    }

    public void onAttach(CameraInternal cameraInternal) {
        synchronized (this.mCameraLock) {
            this.mCamera = cameraInternal;
            addStateChangeCallback(cameraInternal);
        }
        updateUseCaseConfig(this.mUseCaseConfig);
        EventCallback useCaseEventCallback = this.mUseCaseConfig.getUseCaseEventCallback(null);
        if (useCaseEventCallback != null) {
            useCaseEventCallback.onBind(cameraInternal.getCameraInfoInternal().getCameraId());
        }
        onCameraControlReady();
    }

    public void onDetach(CameraInternal cameraInternal) {
        clear();
        EventCallback useCaseEventCallback = this.mUseCaseConfig.getUseCaseEventCallback(null);
        if (useCaseEventCallback != null) {
            useCaseEventCallback.onUnbind();
        }
        synchronized (this.mCameraLock) {
            Preconditions.checkArgument(cameraInternal == this.mCamera);
            this.mCamera.detachUseCases(Collections.singleton(this));
            removeStateChangeCallback(this.mCamera);
            this.mCamera = null;
        }
    }

    protected CameraControlInternal getCameraControl() {
        synchronized (this.mCameraLock) {
            CameraInternal cameraInternal = this.mCamera;
            if (cameraInternal == null) {
                return CameraControlInternal.DEFAULT_EMPTY_INSTANCE;
            }
            return cameraInternal.getCameraControlInternal();
        }
    }

    public void setViewPortCropRect(Rect rect) {
        this.mViewPortCropRect = rect;
    }

    protected Rect getViewPortCropRect() {
        return this.mViewPortCropRect;
    }

    public int getImageFormat() {
        return this.mUseCaseConfig.getInputFormat();
    }
}
