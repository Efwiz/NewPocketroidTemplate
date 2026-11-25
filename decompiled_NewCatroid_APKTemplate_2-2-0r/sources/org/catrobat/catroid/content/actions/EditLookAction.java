package org.catrobat.catroid.content.actions;

import android.content.ComponentName;
import android.content.Intent;
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
import org.catrobat.catroid.content.Scope;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.io.StorageOperations;
import org.catrobat.catroid.stage.StageActivity;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/actions/EditLookAction.class
  classes14.dex
 */
/* JADX INFO: compiled from: EditLookAction.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0007\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0002\u0018\u0000 \u00102\u00020\u0001:\u0001\u0010B\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0016J\n\u0010\u0007\u001a\u0004\u0018\u00010\bH\u0016J\"\u0010\t\u001a\u00020\u00042\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\u000b2\b\u0010\r\u001a\u0004\u0018\u00010\bH\u0016J\b\u0010\u000e\u001a\u00020\u000fH\u0007¨\u0006\u0011"}, d2 = {"Lorg/catrobat/catroid/content/actions/EditLookAction;", "Lorg/catrobat/catroid/content/actions/PocketPaintAction;", "()V", "act", "", "delta", "", "getTargetIntent", "Landroid/content/Intent;", "onIntentResult", "requestCode", "", "resultCode", "data", "setLookData", "", "Companion", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class EditLookAction extends PocketPaintAction {
    private static final String TAG = "EditLookAction";

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
        Sprite sprite;
        Look look;
        LookData lookData;
        File file;
        StageActivity stageActivity;
        Sprite sprite2;
        Look look2;
        LookData lookData2;
        File file2;
        Scope scope = getScope();
        if (scope == null || (sprite = scope.getSprite()) == null || (look = sprite.look) == null || (lookData = look.getLookData()) == null || (file = lookData.getFile()) == null || !file.exists()) {
            return null;
        }
        Scope scope2 = getScope();
        String absolutePath = (scope2 == null || (sprite2 = scope2.getSprite()) == null || (look2 = sprite2.look) == null || (lookData2 = look2.getLookData()) == null || (file2 = lookData2.getFile()) == null) ? null : file2.getAbsolutePath();
        if (absolutePath == null || (stageActivity = StageActivity.activeStageActivity.get()) == null) {
            return null;
        }
        Intent component = new Intent("android.intent.action.MAIN").setComponent(new ComponentName(stageActivity, Constants.POCKET_PAINT_INTENT_ACTIVITY_NAME));
        Intrinsics.checkNotNullExpressionValue(component, "setComponent(...)");
        Bundle bundle = new Bundle();
        bundle.putString("org.catrobat.extra.PAINTROID_PICTURE_PATH", absolutePath);
        component.putExtras(bundle);
        component.addCategory("android.intent.category.LAUNCHER");
        stageActivity.onPause();
        return component;
    }

    @Override // org.catrobat.catroid.stage.StageActivity.IntentListener
    public boolean onIntentResult(int requestCode, int resultCode, Intent data) {
        StageActivity stageActivity = StageActivity.activeStageActivity.get();
        if (resultCode == -1 && stageActivity != null) {
            setLookData();
            return true;
        }
        LookRequester.INSTANCE.setAnyAsked(false);
        setResponseReceived(true);
        if (stageActivity != null) {
            stageActivity.onResume();
        }
        return false;
    }

    public final void setLookData() {
        Sprite sprite;
        String str;
        LookData lookData;
        Scope scope = getScope();
        if (scope == null || (sprite = scope.getSprite()) == null) {
            return;
        }
        Look look = sprite.look;
        File file = null;
        LookData lookData2 = look != null ? look.getLookData() : null;
        if (lookData2 == null || (str = lookData2.f512name) == null) {
            return;
        }
        Look look2 = sprite.look;
        if (look2 != null && (lookData = look2.getLookData()) != null) {
            file = lookData.getFile();
        }
        if (file == null) {
            return;
        }
        try {
            LookData lookData3 = new LookData(str, StorageOperations.duplicateFile(file));
            int iIndexOf = sprite.getLookList().indexOf(sprite.look.getLookData());
            sprite.look.setLookListIndexBeforeLookRequest(iIndexOf);
            sprite.getLookList().remove(iIndexOf);
            sprite.getLookList().add(iIndexOf, lookData3);
            file.delete();
            lookData3.getCollisionInformation().calculate();
        } catch (IOException e) {
            Log.e(TAG, Log.getStackTraceString(e));
        }
        getXstreamSerializer().saveProject(ProjectManager.getInstance().getCurrentProject());
    }
}
