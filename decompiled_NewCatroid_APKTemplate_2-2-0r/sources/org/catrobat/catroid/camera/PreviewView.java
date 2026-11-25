package org.catrobat.catroid.camera;

import android.content.Context;
import android.view.SurfaceView;
import android.widget.FrameLayout;
import androidx.camera.core.Preview;
import androidx.camera.core.SurfaceRequest;
import androidx.core.util.Consumer;
import java.util.concurrent.Executors;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/camera/PreviewView.class
  classes12.dex
 */
/* JADX INFO: compiled from: PreviewView.kt */
/* JADX INFO: loaded from: classes12.dex */
@Metadata(d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0006\u0010\t\u001a\u00020\nJ\u0018\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u000eH\u0002R\u0011\u0010\u0005\u001a\u00020\u0006¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\b¨\u0006\u0010"}, d2 = {"Lorg/catrobat/catroid/camera/PreviewView;", "Landroid/widget/FrameLayout;", "context", "Landroid/content/Context;", "(Landroid/content/Context;)V", "surfaceView", "Landroid/view/SurfaceView;", "getSurfaceView", "()Landroid/view/SurfaceView;", "createSurfaceProvider", "Landroidx/camera/core/Preview$SurfaceProvider;", "scaleView", "", "imageWidth", "", "imageHeight", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class PreviewView extends FrameLayout {
    private final SurfaceView surfaceView;

    /* JADX INFO: Access modifiers changed from: private */
    public static final void createSurfaceProvider$lambda$2$lambda$0(SurfaceRequest.Result result) {
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public PreviewView(Context context) {
        super(context);
        Intrinsics.checkNotNullParameter(context, "context");
        SurfaceView surfaceView = new SurfaceView(context);
        this.surfaceView = surfaceView;
        addView(surfaceView);
    }

    public final SurfaceView getSurfaceView() {
        return this.surfaceView;
    }

    public final Preview.SurfaceProvider createSurfaceProvider() {
        return new Preview.SurfaceProvider() { // from class: org.catrobat.catroid.camera.PreviewView$$ExternalSyntheticLambda0
            @Override // androidx.camera.core.Preview.SurfaceProvider
            public final void onSurfaceRequested(SurfaceRequest surfaceRequest) {
                PreviewView.createSurfaceProvider$lambda$2(this.f$0, surfaceRequest);
            }
        };
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void createSurfaceProvider$lambda$2(final PreviewView this$0, final SurfaceRequest request) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNullParameter(request, "request");
        request.provideSurface(this$0.surfaceView.getHolder().getSurface(), Executors.newSingleThreadExecutor(), new Consumer() { // from class: org.catrobat.catroid.camera.PreviewView$$ExternalSyntheticLambda1
            @Override // androidx.core.util.Consumer
            public final void accept(Object obj) {
                PreviewView.createSurfaceProvider$lambda$2$lambda$0((SurfaceRequest.Result) obj);
            }
        });
        this$0.post(new Runnable() { // from class: org.catrobat.catroid.camera.PreviewView$$ExternalSyntheticLambda2
            @Override // java.lang.Runnable
            public final void run() {
                PreviewView.createSurfaceProvider$lambda$2$lambda$1(this.f$0, request);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void createSurfaceProvider$lambda$2$lambda$1(PreviewView this$0, SurfaceRequest request) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNullParameter(request, "$request");
        this$0.scaleView(request.getResolution().getWidth(), request.getResolution().getHeight());
    }

    private final void scaleView(int imageWidth, int imageHeight) {
        if (getWidth() == 0 || getHeight() == 0) {
            return;
        }
        float f = imageHeight / imageWidth;
        float width = getWidth() / getHeight();
        this.surfaceView.setScaleX(1.0f);
        this.surfaceView.setScaleY(1.0f);
        if (width < 1.0f) {
            this.surfaceView.setScaleX(f / width);
        } else {
            this.surfaceView.setScaleY(f * width);
        }
    }
}
