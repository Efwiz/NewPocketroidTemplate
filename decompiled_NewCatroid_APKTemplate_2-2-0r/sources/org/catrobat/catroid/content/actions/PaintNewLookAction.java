package org.catrobat.catroid.content.actions;

import android.content.ComponentName;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import java.io.File;
import java.io.IOException;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.catrobat.catroid.ProjectManager;
import org.catrobat.catroid.common.Constants;
import org.catrobat.catroid.common.LookData;
import org.catrobat.catroid.content.Look;
import org.catrobat.catroid.content.Project;
import org.catrobat.catroid.content.Scope;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.formulaeditor.Formula;
import org.catrobat.catroid.io.StorageOperations;
import org.catrobat.catroid.stage.StageActivity;
import org.catrobat.catroid.ui.recyclerview.util.UniqueNameProvider;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/actions/PaintNewLookAction.class
  classes14.dex
 */
/* JADX INFO: compiled from: PaintNewLookAction.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u00006\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0007\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0004\u0018\u0000 \u00132\u00020\u0001:\u0001\u0013B\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0016J\u0010\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH\u0007J\b\u0010\u000b\u001a\u00020\nH\u0002J\n\u0010\f\u001a\u0004\u0018\u00010\rH\u0016J\"\u0010\u000e\u001a\u00020\u00042\u0006\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u00102\b\u0010\u0012\u001a\u0004\u0018\u00010\rH\u0016¨\u0006\u0014"}, d2 = {"Lorg/catrobat/catroid/content/actions/PaintNewLookAction;", "Lorg/catrobat/catroid/content/actions/PocketPaintAction;", "()V", "act", "", "delta", "", "addLookFromFile", "", "file", "Ljava/io/File;", "createEmptyImageFile", "getTargetIntent", "Landroid/content/Intent;", "onIntentResult", "requestCode", "", "resultCode", "data", "Companion", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class PaintNewLookAction extends PocketPaintAction {
    private static final String TAG = "PaintNewLookAction";

    @Override // com.badlogic.gdx.scenes.scene2d.Action
    public boolean act(float delta) {
        if (!getQuestionAsked() && LookRequester.INSTANCE.requestNewLook(this)) {
            SetNextLookAction nextLookAction = getNextLookAction();
            if (nextLookAction != null) {
                nextLookAction.setChange(0);
            }
            setQuestionAsked(true);
        }
        return getResponseReceived();
    }

    @Override // org.catrobat.catroid.stage.StageActivity.IntentListener
    /* JADX INFO: renamed from: getTargetIntent */
    public Intent get$intent() {
        StageActivity stageActivity = StageActivity.activeStageActivity.get();
        if (stageActivity == null) {
            return null;
        }
        Intent component = new Intent("android.intent.action.MAIN").setComponent(new ComponentName(stageActivity, Constants.POCKET_PAINT_INTENT_ACTIVITY_NAME));
        Intrinsics.checkNotNullExpressionValue(component, "setComponent(...)");
        Bundle bundle = new Bundle();
        bundle.putString("org.catrobat.extra.PAINTROID_PICTURE_PATH", createEmptyImageFile().getAbsolutePath());
        component.putExtras(bundle);
        component.addCategory("android.intent.category.LAUNCHER");
        StageActivity stageActivity2 = StageActivity.activeStageActivity.get();
        if (stageActivity2 == null) {
            return component;
        }
        stageActivity2.onPause();
        return component;
    }

    private final File createEmptyImageFile() throws IOException {
        Constants.POCKET_PAINT_CACHE_DIRECTORY.mkdirs();
        if (!Constants.POCKET_PAINT_CACHE_DIRECTORY.isDirectory()) {
            Log.e(TAG, "Failed to create directory!");
        }
        Project currentProject = ProjectManager.getInstance().getCurrentProject();
        Bitmap bitmapCreateBitmap = Bitmap.createBitmap(currentProject.getXmlHeader().virtualScreenWidth, currentProject.getXmlHeader().getVirtualScreenHeight(), Bitmap.Config.ARGB_8888);
        Intrinsics.checkNotNullExpressionValue(bitmapCreateBitmap, "createBitmap(...)");
        File fileCompressBitmapToPng = StorageOperations.compressBitmapToPng(bitmapCreateBitmap, new File(Constants.POCKET_PAINT_CACHE_DIRECTORY, "image.png"));
        Intrinsics.checkNotNullExpressionValue(fileCompressBitmapToPng, "compressBitmapToPng(...)");
        return fileCompressBitmapToPng;
    }

    @Override // org.catrobat.catroid.stage.StageActivity.IntentListener
    public boolean onIntentResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == -1) {
            File file = LookRequester.INSTANCE.getFile();
            if (file != null) {
                addLookFromFile(file);
                getXstreamSerializer().saveProject(ProjectManager.getInstance().getCurrentProject());
            }
        } else {
            LookRequester.INSTANCE.setAnyAsked(false);
        }
        StageActivity stageActivity = StageActivity.activeStageActivity.get();
        if (stageActivity != null) {
            stageActivity.onResume();
        }
        setResponseReceived(true);
        return false;
    }

    public final void addLookFromFile(File file) {
        Sprite sprite;
        Look look;
        Formula formula;
        int iIndexOf;
        Intrinsics.checkNotNullParameter(file, "file");
        Scope scope = getScope();
        if (scope == null || (sprite = scope.getSprite()) == null || (look = sprite.look) == null || (formula = getFormula()) == null) {
            return;
        }
        String uniqueNameInNameables = new UniqueNameProvider().getUniqueNameInNameables(formula.interpretObject(getScope()).toString(), sprite.getLookList());
        Intrinsics.checkNotNullExpressionValue(uniqueNameInNameables, "getUniqueNameInNameables(...)");
        LookData lookData = new LookData(uniqueNameInNameables, file);
        if (look.getLookListIndexBeforeLookRequest() > -1) {
            iIndexOf = look.getLookListIndexBeforeLookRequest();
        } else {
            iIndexOf = sprite.getLookList().indexOf(sprite.look.getLookData());
        }
        sprite.getLookList().add(iIndexOf + 1, lookData);
        lookData.getCollisionInformation().calculate();
        SetNextLookAction nextLookAction = getNextLookAction();
        if (nextLookAction == null) {
            return;
        }
        nextLookAction.setChange(1);
    }
}
