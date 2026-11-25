package org.catrobat.catroid.content.actions;

import com.badlogic.gdx.scenes.scene2d.actions.TemporalAction;
import kotlin.Metadata;
import org.catrobat.catroid.camera.CameraManager;
import org.catrobat.catroid.stage.StageActivity;
import org.eclipse.jgit.lib.ConfigConstants;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/actions/ChooseCameraAction.class
  classes14.dex
 */
/* JADX INFO: compiled from: ChooseCameraAction.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0010\u0007\n\u0002\b\u0002\u0018\u0000 \u000b2\u00020\u0001:\u0001\u000bB\u0005¢\u0006\u0002\u0010\u0002J\u0006\u0010\u0005\u001a\u00020\u0006J\u0006\u0010\u0007\u001a\u00020\u0006J\u0010\u0010\b\u001a\u00020\u00062\u0006\u0010\t\u001a\u00020\nH\u0014R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006\f"}, d2 = {"Lorg/catrobat/catroid/content/actions/ChooseCameraAction;", "Lcom/badlogic/gdx/scenes/scene2d/actions/TemporalAction;", "()V", "cameraFacing", "", "setBackCamera", "", "setFrontCamera", ConfigConstants.CONFIG_KEY_UPDATE, "percent", "", "Companion", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ChooseCameraAction extends TemporalAction {
    private static final int BACK = 0;
    private static final int FRONT = 1;
    private int cameraFacing = 1;

    @Override // com.badlogic.gdx.scenes.scene2d.actions.TemporalAction
    protected void update(float percent) {
        CameraManager activeCameraManager = StageActivity.getActiveCameraManager();
        if (this.cameraFacing == 1) {
            if (activeCameraManager != null) {
                activeCameraManager.switchToFrontCamera();
            }
        } else if (activeCameraManager != null) {
            activeCameraManager.switchToBackCamera();
        }
    }

    public final void setFrontCamera() {
        this.cameraFacing = 1;
    }

    public final void setBackCamera() {
        this.cameraFacing = 0;
    }
}
