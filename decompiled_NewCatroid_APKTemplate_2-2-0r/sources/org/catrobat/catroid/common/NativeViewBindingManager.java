package org.catrobat.catroid.common;

import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.viewport.Viewport;
import java.lang.ref.WeakReference;
import java.util.concurrent.ConcurrentHashMap;
import kotlin.Metadata;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.Intrinsics;
import org.catrobat.catroid.content.Look;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.stage.StageActivity;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/common/NativeViewBindingManager.class
  classes12.dex
 */
/* JADX INFO: compiled from: NativeViewBindingManager.kt */
/* JADX INFO: loaded from: classes12.dex */
@Metadata(d1 = {"\u0000F\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0006\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0007\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001:\u0001\u001eB\u0007\b\u0002¢\u0006\u0002\u0010\u0002J0\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u00052\u0006\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00020\u00142\u0006\u0010\u0016\u001a\u00020\bH\u0007J\b\u0010\u0017\u001a\u00020\u000fH\u0007J\u0010\u0010\u0018\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u0005H\u0007J\u0018\u0010\u0019\u001a\u00020\u000f2\u0006\u0010\u001a\u001a\u00020\u001b2\u0006\u0010\u001c\u001a\u00020\u001dH\u0007R\u001a\u0010\u0003\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00060\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R$\u0010\u0007\u001a\u00020\b8\u0006@\u0006X\u0087\u000e¢\u0006\u0014\n\u0000\u0012\u0004\b\t\u0010\u0002\u001a\u0004\b\n\u0010\u000b\"\u0004\b\f\u0010\r¨\u0006\u001f"}, d2 = {"Lorg/catrobat/catroid/common/NativeViewBindingManager;", "", "()V", "activeBindings", "Ljava/util/concurrent/ConcurrentHashMap;", "", "Lorg/catrobat/catroid/common/NativeViewBindingManager$Binding;", "defaultVisibility", "", "getDefaultVisibility$annotations", "getDefaultVisibility", "()I", "setDefaultVisibility", "(I)V", "bind", "", "viewId", "sprite", "Lorg/catrobat/catroid/content/Sprite;", "offsetX", "", "offsetY", "alignMode", "clearAll", "unbind", "updateBindings", "camera", "Lcom/badlogic/gdx/graphics/Camera;", "viewport", "Lcom/badlogic/gdx/utils/viewport/Viewport;", "Binding", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class NativeViewBindingManager {
    public static final NativeViewBindingManager INSTANCE = new NativeViewBindingManager();
    private static final ConcurrentHashMap<String, Binding> activeBindings = new ConcurrentHashMap<>();
    private static int defaultVisibility;

    @JvmStatic
    public static /* synthetic */ void getDefaultVisibility$annotations() {
    }

    private NativeViewBindingManager() {
    }

    public static final int getDefaultVisibility() {
        return defaultVisibility;
    }

    public static final void setDefaultVisibility(int i) {
        defaultVisibility = i;
    }

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/common/NativeViewBindingManager$Binding.class
      classes12.dex
     */
    /* JADX INFO: compiled from: NativeViewBindingManager.kt */
    @Metadata(d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0007\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u000b\u0018\u00002\u00020\u0001B-\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\u0007\u0012\u0006\u0010\t\u001a\u00020\n¢\u0006\u0002\u0010\u000bR\u0011\u0010\t\u001a\u00020\n¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR\u0011\u0010\u0006\u001a\u00020\u0007¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000fR\u0011\u0010\b\u001a\u00020\u0007¢\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u000fR\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0012R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u0014¨\u0006\u0015"}, d2 = {"Lorg/catrobat/catroid/common/NativeViewBindingManager$Binding;", "", "viewId", "", "sprite", "Lorg/catrobat/catroid/content/Sprite;", "offsetX", "", "offsetY", "alignMode", "", "(Ljava/lang/String;Lorg/catrobat/catroid/content/Sprite;FFI)V", "getAlignMode", "()I", "getOffsetX", "()F", "getOffsetY", "getSprite", "()Lorg/catrobat/catroid/content/Sprite;", "getViewId", "()Ljava/lang/String;", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Binding {
        private final int alignMode;
        private final float offsetX;
        private final float offsetY;
        private final Sprite sprite;
        private final String viewId;

        public Binding(String viewId, Sprite sprite, float f, float f2, int i) {
            Intrinsics.checkNotNullParameter(viewId, "viewId");
            Intrinsics.checkNotNullParameter(sprite, "sprite");
            this.viewId = viewId;
            this.sprite = sprite;
            this.offsetX = f;
            this.offsetY = f2;
            this.alignMode = i;
        }

        public final String getViewId() {
            return this.viewId;
        }

        public final Sprite getSprite() {
            return this.sprite;
        }

        public final float getOffsetX() {
            return this.offsetX;
        }

        public final float getOffsetY() {
            return this.offsetY;
        }

        public final int getAlignMode() {
            return this.alignMode;
        }
    }

    @JvmStatic
    public static final void bind(String viewId, Sprite sprite, float offsetX, float offsetY, int alignMode) {
        Intrinsics.checkNotNullParameter(viewId, "viewId");
        Intrinsics.checkNotNullParameter(sprite, "sprite");
        activeBindings.put(viewId, new Binding(viewId, sprite, offsetX, offsetY, alignMode));
    }

    @JvmStatic
    public static final void unbind(String viewId) {
        Intrinsics.checkNotNullParameter(viewId, "viewId");
        activeBindings.remove(viewId);
    }

    @JvmStatic
    public static final void clearAll() {
        activeBindings.clear();
    }

    @JvmStatic
    public static final void updateBindings(Camera camera, final Viewport viewport) {
        WeakReference<StageActivity> weakReference;
        final StageActivity stageActivity;
        Intrinsics.checkNotNullParameter(camera, "camera");
        Intrinsics.checkNotNullParameter(viewport, "viewport");
        if (activeBindings.isEmpty() || (weakReference = StageActivity.activeStageActivity) == null || (stageActivity = weakReference.get()) == null) {
            return;
        }
        stageActivity.runOnUiThread(new Runnable() { // from class: org.catrobat.catroid.common.NativeViewBindingManager$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                NativeViewBindingManager.updateBindings$lambda$0(stageActivity, viewport);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void updateBindings$lambda$0(StageActivity stage, Viewport viewport) {
        Look look;
        Intrinsics.checkNotNullParameter(stage, "$stage");
        Intrinsics.checkNotNullParameter(viewport, "$viewport");
        Vector3 vector3 = new Vector3();
        Vector3 vector4 = new Vector3();
        for (Binding binding : activeBindings.values()) {
            View viewFromStage = stage.getViewFromStage(binding.getViewId());
            if (viewFromStage != null && (look = binding.getSprite().look) != null) {
                if (!look.isVisible()) {
                    viewFromStage.setVisibility(8);
                } else {
                    viewFromStage.setVisibility(0);
                    float x = look.getX() + (look.getWidth() / 2.0f);
                    float y = look.getY() + (look.getHeight() / 2.0f);
                    float width = look.getWidth() * look.getScaleX();
                    float height = look.getHeight() * look.getScaleY();
                    vector3.set(x, y, 0.0f);
                    viewport.project(vector3);
                    vector4.set(x + (width / 2.0f), y + (height / 2.0f), 0.0f);
                    viewport.project(vector4);
                    float f = vector3.x;
                    float height2 = Gdx.graphics.getHeight() - vector3.y;
                    float fAbs = Math.abs(vector4.x - vector3.x) * 2.0f;
                    float fAbs2 = Math.abs(vector4.y - vector3.y) * 2.0f;
                    float offsetX = (f - (fAbs / 2.0f)) + binding.getOffsetX();
                    float offsetY = (height2 - (fAbs2 / 2.0f)) + binding.getOffsetY();
                    ViewGroup.LayoutParams layoutParams = viewFromStage.getLayoutParams();
                    FrameLayout.LayoutParams layoutParams2 = layoutParams instanceof FrameLayout.LayoutParams ? (FrameLayout.LayoutParams) layoutParams : null;
                    if (layoutParams2 != null) {
                        layoutParams2.width = (int) fAbs;
                        layoutParams2.height = (int) fAbs2;
                        layoutParams2.leftMargin = (int) offsetX;
                        layoutParams2.topMargin = (int) offsetY;
                        viewFromStage.setLayoutParams(layoutParams2);
                    }
                    viewFromStage.setRotation(-look.getRotation());
                    viewFromStage.setAlpha(look.getColor().a);
                }
            }
        }
    }
}
