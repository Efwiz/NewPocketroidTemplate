package org.catrobat.catroid.camera;

import android.util.Log;
import android.widget.FrameLayout;
import androidx.camera.core.Camera;
import androidx.camera.core.CameraControl;
import androidx.camera.core.CameraInfo;
import androidx.camera.core.CameraInfoUnavailableException;
import androidx.camera.core.CameraSelector;
import androidx.camera.core.ImageAnalysis;
import androidx.camera.core.ImageCapture;
import androidx.camera.core.ImageCaptureException;
import androidx.camera.core.Preview;
import androidx.camera.core.UseCase;
import androidx.camera.lifecycle.ProcessCameraProvider;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LifecycleRegistry;
import com.google.firebase.analytics.FirebaseAnalytics;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executors;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.ProjectManager;
import org.catrobat.catroid.stage.StageActivity;
import org.catrobat.catroid.utils.MobileServiceAvailability;
import org.catrobat.catroid.utils.ToastUtil;
import org.jetbrains.kotlin.metadata.jvm.deserialization.JvmProtoBufUtil;
import org.koin.java.KoinJavaComponent;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/camera/CameraManager.class
  classes12.dex
 */
/* JADX INFO: compiled from: CameraManager.kt */
/* JADX INFO: loaded from: classes12.dex */
@Metadata(d1 = {"\u0000\u008a\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\f\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0010\u0011\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\t\n\u0002\u0018\u0002\n\u0002\b\n\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\u0018\u0000 T2\u00020\u0001:\u0001TB\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\b\u0010-\u001a\u00020\u0010H\u0003J\b\u0010.\u001a\u00020\u0010H\u0002J!\u0010/\u001a\u00020\u00102\u0012\u00100\u001a\n\u0012\u0006\b\u0001\u0012\u00020201\"\u000202H\u0003¢\u0006\u0002\u00103J\u0006\u00104\u001a\u000205J\u0006\u00106\u001a\u000205J\u0006\u00107\u001a\u000205J\b\u00108\u001a\u000205H\u0002J\u0006\u00109\u001a\u000205J\b\u0010:\u001a\u000205H\u0003J\u0006\u0010;\u001a\u000205J\u0006\u0010<\u001a\u000205J\u0010\u0010=\u001a\u0002052\u0006\u0010>\u001a\u00020?H\u0002J\u0006\u0010@\u001a\u00020\u0010J\u0006\u0010A\u001a\u000205J\u0006\u0010B\u001a\u000205J\u0010\u0010C\u001a\u00020\u00102\u0006\u0010D\u001a\u00020\rH\u0002J\u0006\u0010E\u001a\u000205J\b\u0010F\u001a\u00020\u0010H\u0002J\u0006\u0010G\u001a\u000205J1\u0010H\u001a\u0002052\u0006\u0010I\u001a\u00020J2!\u0010K\u001a\u001d\u0012\u0013\u0012\u00110\u0010¢\u0006\f\bM\u0012\b\bN\u0012\u0004\b\b(O\u0012\u0004\u0012\u0002050LJ@\u0010P\u001a\u00020528\u0010K\u001a4\u0012\u0013\u0012\u00110\u0010¢\u0006\f\bM\u0012\b\bN\u0012\u0004\b\b(O\u0012\u0015\u0012\u0013\u0018\u00010J¢\u0006\f\bM\u0012\b\bN\u0012\u0004\b\b(R\u0012\u0004\u0012\u0002050QJ\b\u0010S\u001a\u000205H\u0003R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u0016\u0010\u0007\u001a\n \t*\u0004\u0018\u00010\b0\bX\u0082\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\n\u001a\u0004\u0018\u00010\u000bX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\rX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\rX\u0082\u0004¢\u0006\u0002\n\u0000R\u001e\u0010\u0011\u001a\u00020\u00102\u0006\u0010\u000f\u001a\u00020\u0010@BX\u0086\u000e¢\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u0013R\u001e\u0010\u0014\u001a\u00020\u00102\u0006\u0010\u000f\u001a\u00020\u0010@BX\u0086\u000e¢\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\u0013R\u0011\u0010\u0016\u001a\u00020\u0010¢\u0006\b\n\u0000\u001a\u0004\b\u0017\u0010\u0013R\u0011\u0010\u0018\u001a\u00020\u0010¢\u0006\b\n\u0000\u001a\u0004\b\u0019\u0010\u0013R\u0011\u0010\u001a\u001a\u00020\u0010¢\u0006\b\n\u0000\u001a\u0004\b\u001b\u0010\u0013R\u000e\u0010\u001c\u001a\u00020\u001dX\u0082\u0004¢\u0006\u0002\n\u0000R\u0011\u0010\u001e\u001a\u00020\u00108F¢\u0006\u0006\u001a\u0004\b\u001e\u0010\u0013R\u0011\u0010\u001f\u001a\u00020\u00108F¢\u0006\u0006\u001a\u0004\b\u001f\u0010\u0013R\u000e\u0010 \u001a\u00020\u0010X\u0082\u000e¢\u0006\u0002\n\u0000R\u0014\u0010!\u001a\u00020\"X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b#\u0010$R\u000e\u0010%\u001a\u00020&X\u0082\u0004¢\u0006\u0002\n\u0000R\u0011\u0010'\u001a\u00020(¢\u0006\b\n\u0000\u001a\u0004\b)\u0010*R\u001e\u0010+\u001a\u00020\u00102\u0006\u0010\u000f\u001a\u00020\u0010@BX\u0086\u000e¢\u0006\b\n\u0000\u001a\u0004\b,\u0010\u0013R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006U"}, d2 = {"Lorg/catrobat/catroid/camera/CameraManager;", "Landroidx/lifecycle/LifecycleOwner;", "stageActivity", "Lorg/catrobat/catroid/stage/StageActivity;", "(Lorg/catrobat/catroid/stage/StageActivity;)V", "analysisUseCase", "Landroidx/camera/core/ImageAnalysis;", "cameraProvider", "Landroidx/camera/lifecycle/ProcessCameraProvider;", JvmProtoBufUtil.PLATFORM_TYPE_ID, "currentCamera", "Landroidx/camera/core/Camera;", "currentCameraSelector", "Landroidx/camera/core/CameraSelector;", "defaultCameraSelector", "<set-?>", "", "detectionOn", "getDetectionOn", "()Z", "flashOn", "getFlashOn", "hasBackCamera", "getHasBackCamera", "hasFlash", "getHasFlash", "hasFrontCamera", "getHasFrontCamera", "imageCaptureUseCase", "Landroidx/camera/core/ImageCapture;", "isCameraActive", "isCameraFacingFront", "isFirst", "lifecycle", "Landroidx/lifecycle/LifecycleRegistry;", "getLifecycle", "()Landroidx/lifecycle/LifecycleRegistry;", "previewUseCase", "Landroidx/camera/core/Preview;", "previewView", "Lorg/catrobat/catroid/camera/PreviewView;", "getPreviewView", "()Lorg/catrobat/catroid/camera/PreviewView;", "previewVisible", "getPreviewVisible", "bindFaceAndTextDetector", "bindPreview", "bindUseCase", "useCases", "", "Landroidx/camera/core/UseCase;", "([Landroidx/camera/core/UseCase;)Z", "destroy", "", "disableFlash", "enableFlash", "handleError", "pause", "rebindUseCases", "reset", "resume", "runInMainThreadAndWait", "runnable", "Ljava/lang/Runnable;", "startDetection", "startPreview", "stopPreview", "switchCamera", "cameraSelector", "switchToBackCamera", "switchToDefaultCamera", "switchToFrontCamera", "takePicture", "outputFile", "Ljava/io/File;", "callback", "Lkotlin/Function1;", "Lkotlin/ParameterName;", "name", FirebaseAnalytics.Param.SUCCESS, "takePicture2", "Lkotlin/Function2;", "file", "unbindPreview", "Companion", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class CameraManager implements LifecycleOwner {
    private static final String TAG = "CameraManager";
    private final ImageAnalysis analysisUseCase;
    private final ProcessCameraProvider cameraProvider;
    private Camera currentCamera;
    private CameraSelector currentCameraSelector;
    private final CameraSelector defaultCameraSelector;
    private boolean detectionOn;
    private boolean flashOn;
    private final boolean hasBackCamera;
    private final boolean hasFlash;
    private final boolean hasFrontCamera;
    private final ImageCapture imageCaptureUseCase;
    private boolean isFirst;
    private final LifecycleRegistry lifecycle;
    private final Preview previewUseCase;
    private final PreviewView previewView;
    private boolean previewVisible;
    private final StageActivity stageActivity;

    public CameraManager(StageActivity stageActivity) throws CameraInfoUnavailableException {
        CameraSelector cameraSelector;
        Intrinsics.checkNotNullParameter(stageActivity, "stageActivity");
        this.stageActivity = stageActivity;
        ProcessCameraProvider processCameraProvider = ProcessCameraProvider.getInstance(stageActivity).get();
        this.cameraProvider = processCameraProvider;
        this.lifecycle = new LifecycleRegistry(this);
        PreviewView previewView = new PreviewView(stageActivity);
        previewView.setVisibility(4);
        this.previewView = previewView;
        Preview previewBuild = new Preview.Builder().build();
        Intrinsics.checkNotNullExpressionValue(previewBuild, "build(...)");
        this.previewUseCase = previewBuild;
        ImageAnalysis imageAnalysisBuild = new ImageAnalysis.Builder().build();
        Intrinsics.checkNotNullExpressionValue(imageAnalysisBuild, "build(...)");
        this.analysisUseCase = imageAnalysisBuild;
        ImageCapture imageCaptureBuild = new ImageCapture.Builder().build();
        Intrinsics.checkNotNullExpressionValue(imageCaptureBuild, "build(...)");
        this.imageCaptureUseCase = imageCaptureBuild;
        this.isFirst = true;
        boolean zHasCamera = processCameraProvider.hasCamera(CameraSelector.DEFAULT_FRONT_CAMERA);
        this.hasFrontCamera = zHasCamera;
        boolean zHasCamera2 = processCameraProvider.hasCamera(CameraSelector.DEFAULT_BACK_CAMERA);
        this.hasBackCamera = zHasCamera2;
        this.hasFlash = stageActivity.getPackageManager().hasSystemFeature("android.hardware.camera.flash");
        if (zHasCamera || zHasCamera2) {
            stageActivity.getCameraContainer().addView(previewView, new FrameLayout.LayoutParams(-1, -1));
        }
        if (zHasCamera) {
            cameraSelector = CameraSelector.DEFAULT_FRONT_CAMERA;
            Intrinsics.checkNotNull(cameraSelector);
        } else {
            cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA;
            Intrinsics.checkNotNull(cameraSelector);
        }
        this.defaultCameraSelector = cameraSelector;
        this.currentCameraSelector = cameraSelector;
        getLifecycle().setCurrentState(Lifecycle.State.CREATED);
    }

    @Override // androidx.lifecycle.LifecycleOwner
    public LifecycleRegistry getLifecycle() {
        return this.lifecycle;
    }

    public final PreviewView getPreviewView() {
        return this.previewView;
    }

    public final boolean getHasFrontCamera() {
        return this.hasFrontCamera;
    }

    public final boolean getHasBackCamera() {
        return this.hasBackCamera;
    }

    public final boolean getHasFlash() {
        return this.hasFlash;
    }

    public final boolean getPreviewVisible() {
        return this.previewVisible;
    }

    public final boolean getDetectionOn() {
        return this.detectionOn;
    }

    public final boolean getFlashOn() {
        return this.flashOn;
    }

    public final boolean isCameraFacingFront() {
        return Intrinsics.areEqual(this.currentCameraSelector, CameraSelector.DEFAULT_FRONT_CAMERA);
    }

    public final boolean isCameraActive() {
        if (CollectionsKt.listOf((Object[]) new Lifecycle.State[]{Lifecycle.State.STARTED, Lifecycle.State.RESUMED}).contains(getLifecycle().getState())) {
            return this.cameraProvider.isBound(this.previewUseCase) || this.cameraProvider.isBound(this.analysisUseCase);
        }
        return false;
    }

    public final synchronized void reset() {
        this.flashOn = false;
        this.previewVisible = false;
        this.detectionOn = false;
        unbindPreview();
        switchToDefaultCamera();
    }

    public final synchronized void destroy() {
        getLifecycle().setCurrentState(Lifecycle.State.DESTROYED);
    }

    public final synchronized void pause() {
        getLifecycle().setCurrentState(Lifecycle.State.CREATED);
    }

    public final synchronized void resume() {
        CameraControl cameraControl;
        getLifecycle().setCurrentState(Lifecycle.State.RESUMED);
        Camera camera = this.currentCamera;
        if (camera != null && (cameraControl = camera.getCameraControl()) != null) {
            cameraControl.enableTorch(this.flashOn);
        }
    }

    public final synchronized void switchToFrontCamera() {
        if (this.hasFrontCamera) {
            runInMainThreadAndWait(new Runnable() { // from class: org.catrobat.catroid.camera.CameraManager$$ExternalSyntheticLambda2
                @Override // java.lang.Runnable
                public final void run() {
                    CameraManager.switchToFrontCamera$lambda$1(this.f$0);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void switchToFrontCamera$lambda$1(CameraManager this$0) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        CameraSelector DEFAULT_FRONT_CAMERA = CameraSelector.DEFAULT_FRONT_CAMERA;
        Intrinsics.checkNotNullExpressionValue(DEFAULT_FRONT_CAMERA, "DEFAULT_FRONT_CAMERA");
        this$0.switchCamera(DEFAULT_FRONT_CAMERA);
    }

    public final synchronized void switchToBackCamera() {
        if (this.hasBackCamera) {
            runInMainThreadAndWait(new Runnable() { // from class: org.catrobat.catroid.camera.CameraManager$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    CameraManager.switchToBackCamera$lambda$2(this.f$0);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void switchToBackCamera$lambda$2(CameraManager this$0) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        CameraSelector DEFAULT_BACK_CAMERA = CameraSelector.DEFAULT_BACK_CAMERA;
        Intrinsics.checkNotNullExpressionValue(DEFAULT_BACK_CAMERA, "DEFAULT_BACK_CAMERA");
        this$0.switchCamera(DEFAULT_BACK_CAMERA);
    }

    private final boolean switchToDefaultCamera() {
        return switchCamera(this.defaultCameraSelector);
    }

    private final boolean switchCamera(CameraSelector cameraSelector) {
        CameraControl cameraControl;
        if (Intrinsics.areEqual(this.currentCameraSelector, cameraSelector)) {
            return false;
        }
        this.currentCameraSelector = cameraSelector;
        this.currentCamera = null;
        ArrayList arrayList = new ArrayList();
        arrayList.add(this.imageCaptureUseCase);
        if (this.previewVisible) {
            arrayList.add(this.previewUseCase);
        }
        if (this.detectionOn) {
            arrayList.add(this.analysisUseCase);
            MobileServiceAvailability mobileServiceAvailability = (MobileServiceAvailability) KoinJavaComponent.get$default(MobileServiceAvailability.class, null, null, 6, null);
            if (mobileServiceAvailability.isGmsAvailable(this.stageActivity)) {
                CatdroidImageAnalyzer.INSTANCE.setActiveDetectorsWithContext(this.stageActivity.getContext());
                this.analysisUseCase.setAnalyzer(Executors.newSingleThreadExecutor(), CatdroidImageAnalyzer.INSTANCE);
            } else if (mobileServiceAvailability.isHmsAvailable(this.stageActivity)) {
                this.analysisUseCase.setAnalyzer(Executors.newSingleThreadExecutor(), FaceTextPoseDetectorHuawei.INSTANCE);
            }
        }
        try {
            this.cameraProvider.unbindAll();
            if (arrayList.isEmpty()) {
                return true;
            }
            CameraSelector cameraSelector2 = this.currentCameraSelector;
            UseCase[] useCaseArr = (UseCase[]) arrayList.toArray(new UseCase[0]);
            this.currentCamera = this.cameraProvider.bindToLifecycle(this, cameraSelector2, (UseCase[]) Arrays.copyOf(useCaseArr, useCaseArr.length));
            if (this.previewVisible) {
                this.previewUseCase.setSurfaceProvider(this.previewView.createSurfaceProvider());
            }
            Camera camera = this.currentCamera;
            if (camera != null && (cameraControl = camera.getCameraControl()) != null) {
                cameraControl.enableTorch(this.flashOn);
            }
            getLifecycle().setCurrentState(Lifecycle.State.STARTED);
            return true;
        } catch (Exception e) {
            Log.e(TAG, "Could not rebind use cases on camera switch.", e);
            handleError();
            return false;
        }
    }

    public final synchronized void startPreview() {
        if (!this.previewVisible) {
            this.previewVisible = true;
            runInMainThreadAndWait(new Runnable() { // from class: org.catrobat.catroid.camera.CameraManager$$ExternalSyntheticLambda5
                @Override // java.lang.Runnable
                public final void run() {
                    CameraManager.startPreview$lambda$3(this.f$0);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void startPreview$lambda$3(CameraManager this$0) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.previewView.setVisibility(0);
        this$0.rebindUseCases();
    }

    public final synchronized void stopPreview() {
        if (this.previewVisible) {
            this.previewVisible = false;
            runInMainThreadAndWait(new Runnable() { // from class: org.catrobat.catroid.camera.CameraManager$$ExternalSyntheticLambda3
                @Override // java.lang.Runnable
                public final void run() {
                    CameraManager.stopPreview$lambda$4(this.f$0);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void stopPreview$lambda$4(CameraManager this$0) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.previewView.setVisibility(4);
        if (this$0.flashOn) {
            return;
        }
        this$0.rebindUseCases();
    }

    public final synchronized boolean startDetection() {
        if (!this.detectionOn) {
            this.detectionOn = true;
            rebindUseCases();
        }
        return true;
    }

    public final synchronized void enableFlash() {
        CameraControl cameraControl;
        CameraInfo cameraInfo;
        if (!this.flashOn) {
            this.flashOn = true;
            Camera camera = this.currentCamera;
            if ((camera == null || (cameraInfo = camera.getCameraInfo()) == null || !cameraInfo.hasFlashUnit()) && isCameraFacingFront()) {
                switchToBackCamera();
            } else {
                Camera camera2 = this.currentCamera;
                if (camera2 != null && (cameraControl = camera2.getCameraControl()) != null) {
                    cameraControl.enableTorch(true);
                }
            }
        }
    }

    public final synchronized void disableFlash() {
        CameraControl cameraControl;
        if (this.flashOn) {
            this.flashOn = false;
            Camera camera = this.currentCamera;
            if (camera != null && (cameraControl = camera.getCameraControl()) != null) {
                cameraControl.enableTorch(false);
            }
            if (!this.previewVisible) {
                runInMainThreadAndWait(new Runnable() { // from class: org.catrobat.catroid.camera.CameraManager$$ExternalSyntheticLambda4
                    @Override // java.lang.Runnable
                    public final void run() {
                        CameraManager.disableFlash$lambda$5(this.f$0);
                    }
                });
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void disableFlash$lambda$5(CameraManager this$0) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.rebindUseCases();
    }

    private final boolean bindPreview() {
        this.previewView.setVisibility(0);
        boolean zBindUseCase = bindUseCase(this.previewUseCase, this.imageCaptureUseCase);
        this.previewUseCase.setSurfaceProvider(this.previewView.createSurfaceProvider());
        if (!this.previewVisible) {
            this.previewView.setVisibility(4);
        }
        return zBindUseCase;
    }

    private final void unbindPreview() {
        this.cameraProvider.unbind(this.previewUseCase);
        if (this.cameraProvider.isBound(this.analysisUseCase)) {
            return;
        }
        this.currentCamera = null;
    }

    public final void takePicture(File outputFile, Function1<? super Boolean, Unit> callback) {
        Intrinsics.checkNotNullParameter(outputFile, "outputFile");
        Intrinsics.checkNotNullParameter(callback, "callback");
        ImageCapture.OutputFileOptions outputFileOptionsBuild = new ImageCapture.OutputFileOptions.Builder(outputFile).build();
        Intrinsics.checkNotNullExpressionValue(outputFileOptionsBuild, "build(...)");
        this.imageCaptureUseCase.m80lambda$takePicture$4$androidxcameracoreImageCapture(outputFileOptionsBuild, Executors.newSingleThreadExecutor(), new AnonymousClass1(outputFile, callback));
    }

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/camera/CameraManager$takePicture$1.class
      classes12.dex
     */
    /* JADX INFO: renamed from: org.catrobat.catroid.camera.CameraManager$takePicture$1, reason: invalid class name */
    /* JADX INFO: compiled from: CameraManager.kt */
    @Metadata(d1 = {"\u0000\u001f\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000*\u0001\u0000\b\n\u0018\u00002\u00020\u0001J\u0010\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H\u0016J\u0010\u0010\u0006\u001a\u00020\u00032\u0006\u0010\u0007\u001a\u00020\bH\u0016¨\u0006\t"}, d2 = {"org/catrobat/catroid/camera/CameraManager$takePicture$1", "Landroidx/camera/core/ImageCapture$OnImageSavedCallback;", "onError", "", "exc", "Landroidx/camera/core/ImageCaptureException;", "onImageSaved", "output", "Landroidx/camera/core/ImageCapture$OutputFileResults;", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class AnonymousClass1 implements ImageCapture.OnImageSavedCallback {
        final /* synthetic */ Function1<Boolean, Unit> $callback;
        final /* synthetic */ File $outputFile;

        /* JADX WARN: Multi-variable type inference failed */
        AnonymousClass1(File file, Function1<? super Boolean, Unit> function1) {
            this.$outputFile = file;
            this.$callback = function1;
        }

        @Override // androidx.camera.core.ImageCapture.OnImageSavedCallback
        public void onImageSaved(ImageCapture.OutputFileResults output) {
            Intrinsics.checkNotNullParameter(output, "output");
            StageActivity stageActivity = CameraManager.this.stageActivity;
            final File file = this.$outputFile;
            final Function1<Boolean, Unit> function1 = this.$callback;
            stageActivity.runOnUiThread(new Runnable() { // from class: org.catrobat.catroid.camera.CameraManager$takePicture$1$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    CameraManager.AnonymousClass1.onImageSaved$lambda$0(file, function1);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final void onImageSaved$lambda$0(File outputFile, Function1 callback) {
            Intrinsics.checkNotNullParameter(outputFile, "$outputFile");
            Intrinsics.checkNotNullParameter(callback, "$callback");
            Log.d(CameraManager.TAG, "Photo capture succeeded: " + outputFile.getAbsolutePath());
            callback.invoke(true);
        }

        @Override // androidx.camera.core.ImageCapture.OnImageSavedCallback
        public void onError(final ImageCaptureException exc) {
            Intrinsics.checkNotNullParameter(exc, "exc");
            StageActivity stageActivity = CameraManager.this.stageActivity;
            final Function1<Boolean, Unit> function1 = this.$callback;
            stageActivity.runOnUiThread(new Runnable() { // from class: org.catrobat.catroid.camera.CameraManager$takePicture$1$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    CameraManager.AnonymousClass1.onError$lambda$1(exc, function1);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final void onError$lambda$1(ImageCaptureException exc, Function1 callback) {
            Intrinsics.checkNotNullParameter(exc, "$exc");
            Intrinsics.checkNotNullParameter(callback, "$callback");
            Log.e(CameraManager.TAG, "Photo capture failed: " + exc.getMessage(), exc);
            callback.invoke(false);
        }
    }

    public final void takePicture2(Function2<? super Boolean, ? super File, Unit> callback) {
        Intrinsics.checkNotNullParameter(callback, "callback");
        if (ProjectManager.getInstance().getCurrentProject() == null) {
            Log.e("CameraManager", "Project is null, cannot save photo.");
            callback.invoke(false, null);
            return;
        }
        File filesDir = ProjectManager.getInstance().getCurrentProject().getFilesDir();
        if (!filesDir.exists()) {
            filesDir.mkdirs();
        }
        File file = new File(filesDir, "photo.png");
        ImageCapture.OutputFileOptions outputFileOptionsBuild = new ImageCapture.OutputFileOptions.Builder(file).build();
        Intrinsics.checkNotNullExpressionValue(outputFileOptionsBuild, "build(...)");
        this.imageCaptureUseCase.m80lambda$takePicture$4$androidxcameracoreImageCapture(outputFileOptionsBuild, Executors.newSingleThreadExecutor(), new C41701(file, callback));
    }

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/camera/CameraManager$takePicture2$1.class
      classes12.dex
     */
    /* JADX INFO: renamed from: org.catrobat.catroid.camera.CameraManager$takePicture2$1, reason: invalid class name and case insensitive filesystem */
    /* JADX INFO: compiled from: CameraManager.kt */
    @Metadata(d1 = {"\u0000\u001f\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000*\u0001\u0000\b\n\u0018\u00002\u00020\u0001J\u0010\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H\u0016J\u0010\u0010\u0006\u001a\u00020\u00032\u0006\u0010\u0007\u001a\u00020\bH\u0016¨\u0006\t"}, d2 = {"org/catrobat/catroid/camera/CameraManager$takePicture2$1", "Landroidx/camera/core/ImageCapture$OnImageSavedCallback;", "onError", "", "exc", "Landroidx/camera/core/ImageCaptureException;", "onImageSaved", "output", "Landroidx/camera/core/ImageCapture$OutputFileResults;", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class C41701 implements ImageCapture.OnImageSavedCallback {
        final /* synthetic */ Function2<Boolean, File, Unit> $callback;
        final /* synthetic */ File $photoFile;

        /* JADX WARN: Multi-variable type inference failed */
        C41701(File file, Function2<? super Boolean, ? super File, Unit> function2) {
            this.$photoFile = file;
            this.$callback = function2;
        }

        @Override // androidx.camera.core.ImageCapture.OnImageSavedCallback
        public void onImageSaved(ImageCapture.OutputFileResults output) {
            Intrinsics.checkNotNullParameter(output, "output");
            StageActivity stageActivity = CameraManager.this.stageActivity;
            final File file = this.$photoFile;
            final Function2<Boolean, File, Unit> function2 = this.$callback;
            stageActivity.runOnUiThread(new Runnable() { // from class: org.catrobat.catroid.camera.CameraManager$takePicture2$1$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    CameraManager.C41701.onImageSaved$lambda$0(file, function2);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final void onImageSaved$lambda$0(File photoFile, Function2 callback) {
            Intrinsics.checkNotNullParameter(photoFile, "$photoFile");
            Intrinsics.checkNotNullParameter(callback, "$callback");
            Log.d(CameraManager.TAG, "Photo capture succeeded: " + photoFile.getAbsolutePath());
            callback.invoke(true, photoFile);
        }

        @Override // androidx.camera.core.ImageCapture.OnImageSavedCallback
        public void onError(final ImageCaptureException exc) {
            Intrinsics.checkNotNullParameter(exc, "exc");
            StageActivity stageActivity = CameraManager.this.stageActivity;
            final Function2<Boolean, File, Unit> function2 = this.$callback;
            stageActivity.runOnUiThread(new Runnable() { // from class: org.catrobat.catroid.camera.CameraManager$takePicture2$1$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    CameraManager.C41701.onError$lambda$1(exc, function2);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final void onError$lambda$1(ImageCaptureException exc, Function2 callback) {
            Intrinsics.checkNotNullParameter(exc, "$exc");
            Intrinsics.checkNotNullParameter(callback, "$callback");
            Log.e(CameraManager.TAG, "Photo capture failed: " + exc.getMessage(), exc);
            callback.invoke(false, null);
        }
    }

    private final void rebindUseCases() {
        CameraControl cameraControl;
        ArrayList arrayList = new ArrayList();
        arrayList.add(this.imageCaptureUseCase);
        if (this.previewVisible) {
            arrayList.add(this.previewUseCase);
            this.previewUseCase.setSurfaceProvider(this.previewView.createSurfaceProvider());
        }
        if (this.detectionOn) {
            MobileServiceAvailability mobileServiceAvailability = (MobileServiceAvailability) KoinJavaComponent.get$default(MobileServiceAvailability.class, null, null, 6, null);
            if (mobileServiceAvailability.isGmsAvailable(this.stageActivity)) {
                CatdroidImageAnalyzer.INSTANCE.setActiveDetectorsWithContext(this.stageActivity.getContext());
                this.analysisUseCase.setAnalyzer(Executors.newSingleThreadExecutor(), CatdroidImageAnalyzer.INSTANCE);
            } else if (mobileServiceAvailability.isHmsAvailable(this.stageActivity)) {
                this.analysisUseCase.setAnalyzer(Executors.newSingleThreadExecutor(), FaceTextPoseDetectorHuawei.INSTANCE);
            }
            arrayList.add(this.analysisUseCase);
        }
        if (!arrayList.isEmpty()) {
            try {
                CameraSelector cameraSelector = this.currentCameraSelector;
                UseCase[] useCaseArr = (UseCase[]) arrayList.toArray(new UseCase[0]);
                Camera cameraBindToLifecycle = this.cameraProvider.bindToLifecycle(this, cameraSelector, (UseCase[]) Arrays.copyOf(useCaseArr, useCaseArr.length));
                this.currentCamera = cameraBindToLifecycle;
                if (cameraBindToLifecycle != null && (cameraControl = cameraBindToLifecycle.getCameraControl()) != null) {
                    cameraControl.enableTorch(this.flashOn);
                }
                getLifecycle().setCurrentState(Lifecycle.State.STARTED);
                return;
            } catch (Exception e) {
                Log.e(TAG, "Failed to bind use cases", e);
                handleError();
                return;
            }
        }
        this.currentCamera = null;
    }

    private final boolean bindFaceAndTextDetector() {
        boolean zBindUseCase = bindUseCase(this.analysisUseCase, this.imageCaptureUseCase);
        MobileServiceAvailability mobileServiceAvailability = (MobileServiceAvailability) KoinJavaComponent.get$default(MobileServiceAvailability.class, null, null, 6, null);
        if (mobileServiceAvailability.isGmsAvailable(this.stageActivity)) {
            CatdroidImageAnalyzer.INSTANCE.setActiveDetectorsWithContext(this.stageActivity.getContext());
            this.analysisUseCase.setAnalyzer(Executors.newSingleThreadExecutor(), CatdroidImageAnalyzer.INSTANCE);
        } else if (mobileServiceAvailability.isHmsAvailable(this.stageActivity)) {
            this.analysisUseCase.setAnalyzer(Executors.newSingleThreadExecutor(), FaceTextPoseDetectorHuawei.INSTANCE);
        }
        return zBindUseCase;
    }

    private final boolean bindUseCase(UseCase... useCases) {
        CameraControl cameraControl;
        try {
            this.cameraProvider.unbind((UseCase[]) Arrays.copyOf(useCases, useCases.length));
            Camera cameraBindToLifecycle = this.cameraProvider.bindToLifecycle(this, this.currentCameraSelector, (UseCase[]) Arrays.copyOf(useCases, useCases.length));
            this.currentCamera = cameraBindToLifecycle;
            if (cameraBindToLifecycle != null && (cameraControl = cameraBindToLifecycle.getCameraControl()) != null) {
                cameraControl.enableTorch(this.flashOn);
            }
            getLifecycle().setCurrentState(Lifecycle.State.STARTED);
            return true;
        } catch (Exception e) {
            Log.e(TAG, "Could not bind use case(s).", e);
            handleError();
            return false;
        }
    }

    private final void runInMainThreadAndWait(final Runnable runnable) throws InterruptedException {
        final CountDownLatch countDownLatch = new CountDownLatch(1);
        this.stageActivity.runOnUiThread(new Runnable() { // from class: org.catrobat.catroid.camera.CameraManager$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                CameraManager.runInMainThreadAndWait$lambda$8(runnable, countDownLatch);
            }
        });
        countDownLatch.await();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void runInMainThreadAndWait$lambda$8(Runnable runnable, CountDownLatch executionLatch) {
        Intrinsics.checkNotNullParameter(runnable, "$runnable");
        Intrinsics.checkNotNullParameter(executionLatch, "$executionLatch");
        runnable.run();
        executionLatch.countDown();
    }

    private final void handleError() {
        StageActivity stageActivity = this.stageActivity;
        ToastUtil.showError(stageActivity, stageActivity.getString(R.string.camera_error_generic));
        destroy();
    }
}
