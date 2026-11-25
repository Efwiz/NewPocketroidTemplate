package org.catrobat.catroid.content.actions;

import android.os.Environment;
import android.util.Log;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import com.badlogic.gdx.scenes.scene2d.actions.TemporalAction;
import java.io.File;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import org.catrobat.catroid.CatroidApplication;
import org.catrobat.catroid.common.Constants;
import org.catrobat.catroid.common.LookData;
import org.catrobat.catroid.content.Look;
import org.catrobat.catroid.content.Scope;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.formulaeditor.Formula;
import org.catrobat.catroid.formulaeditor.InterpretationException;
import org.catrobat.catroid.sensing.CollisionInformation;
import org.catrobat.catroid.stage.StageActivity;
import org.eclipse.jgit.lib.ConfigConstants;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/actions/LookFileAction.class
  classes14.dex
 */
/* JADX INFO: compiled from: LookFileAction.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000>\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0010\u0007\n\u0002\b\u0002\b\u0016\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u000e\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u0012J\u0012\u0010\u0005\u001a\u0004\u0018\u00010\u00132\b\u0010\u0014\u001a\u0004\u0018\u00010\u0004J\u000e\u0010\u0015\u001a\u00020\u00162\u0006\u0010\u0017\u001a\u00020\u0010J\u0010\u0010\u0018\u001a\u00020\u00162\u0006\u0010\u0019\u001a\u00020\u001aH\u0014J\b\u0010\u001b\u001a\u00020\u0016H\u0002R\u001c\u0010\u0003\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\bR\u001c\u0010\t\u001a\u0004\u0018\u00010\nX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000b\u0010\f\"\u0004\b\r\u0010\u000e¨\u0006\u001c"}, d2 = {"Lorg/catrobat/catroid/content/actions/LookFileAction;", "Lcom/badlogic/gdx/scenes/scene2d/actions/TemporalAction;", "()V", "name", "Lorg/catrobat/catroid/formulaeditor/Formula;", "getName", "()Lorg/catrobat/catroid/formulaeditor/Formula;", "setName", "(Lorg/catrobat/catroid/formulaeditor/Formula;)V", "scope", "Lorg/catrobat/catroid/content/Scope;", "getScope", "()Lorg/catrobat/catroid/content/Scope;", "setScope", "(Lorg/catrobat/catroid/content/Scope;)V", "createlook", "Lorg/catrobat/catroid/common/LookData;", "file", "Ljava/io/File;", "", "inputName", "setLook", "", "look", ConfigConstants.CONFIG_KEY_UPDATE, "percent", "", "updateLookListIndex", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public class LookFileAction extends TemporalAction {
    private Formula name;
    private Scope scope;

    public final Scope getScope() {
        return this.scope;
    }

    public final void setScope(Scope scope) {
        this.scope = scope;
    }

    public final Formula getName() {
        return this.name;
    }

    public final void setName(Formula formula) {
        this.name = formula;
    }

    @Override // com.badlogic.gdx.scenes.scene2d.actions.TemporalAction
    protected void update(float percent) throws InterruptedException, InterpretationException {
        final StageActivity stageActivity = StageActivity.activeStageActivity.get();
        if (stageActivity != null) {
            stageActivity.runOnUiThread(new Runnable() { // from class: org.catrobat.catroid.content.actions.LookFileAction$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    LookFileAction.update$lambda$0(stageActivity);
                }
            });
        }
        File externalStoragePublicDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
        String name2 = getName(this.name);
        if (name2 == null) {
            name2 = "my_actor.png";
        }
        File file = new File(externalStoragePublicDirectory, name2);
        if (file.exists()) {
            setLook(createlook(file));
        } else {
            Log.e("LookFile", "File has not exists");
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void update$lambda$0(StageActivity stageActivity) {
        if (ContextCompat.checkSelfPermission(CatroidApplication.getAppContext(), "android.permission.READ_EXTERNAL_STORAGE") != 0) {
            ActivityCompat.requestPermissions(stageActivity, new String[]{"android.permission.READ_EXTERNAL_STORAGE"}, 2);
        }
    }

    public final String getName(Formula inputName) throws InterpretationException {
        if (inputName == null) {
            return null;
        }
        String strInterpretString = inputName.interpretString(this.scope);
        Intrinsics.checkNotNull(strInterpretString);
        int iLastIndexOf$default = StringsKt.lastIndexOf$default((CharSequence) strInterpretString, '.', 0, false, 6, (Object) null);
        return (iLastIndexOf$default > 0 || iLastIndexOf$default < strInterpretString.length() + (-1)) ? strInterpretString : strInterpretString + Constants.DEFAULT_IMAGE_EXTENSION;
    }

    public final LookData createlook(File file) {
        Intrinsics.checkNotNullParameter(file, "file");
        LookData lookData = new LookData(file.getName(), file);
        lookData.getCollisionInformation().calculate();
        return lookData;
    }

    public final void setLook(LookData look) throws InterruptedException {
        Thread thread;
        Sprite sprite;
        Intrinsics.checkNotNullParameter(look, "look");
        updateLookListIndex();
        Scope scope = this.scope;
        Look look2 = (scope == null || (sprite = scope.getSprite()) == null) ? null : sprite.look;
        if (look2 != null) {
            look2.setLookData(look);
        }
        CollisionInformation collisionInformation = look.getCollisionInformation();
        if (collisionInformation != null && (thread = collisionInformation.collisionPolygonCalculationThread) != null) {
            thread.join();
        }
        look.setIsWebRequest(false);
    }

    private final void updateLookListIndex() {
        Sprite sprite;
        List<LookData> lookList;
        Sprite sprite2;
        Look look;
        Sprite sprite3;
        Sprite sprite4;
        Scope scope = this.scope;
        LookData lookData = null;
        Look look2 = (scope == null || (sprite4 = scope.getSprite()) == null) ? null : sprite4.look;
        int iIndexOf = -1;
        if (look2 == null || look2.getLookListIndexBeforeLookRequest() <= -1) {
            Scope scope2 = this.scope;
            Look look3 = (scope2 == null || (sprite3 = scope2.getSprite()) == null) ? null : sprite3.look;
            if (look3 == null) {
                return;
            }
            Scope scope3 = this.scope;
            if (scope3 != null && (sprite = scope3.getSprite()) != null && (lookList = sprite.getLookList()) != null) {
                Scope scope4 = this.scope;
                if (scope4 != null && (sprite2 = scope4.getSprite()) != null && (look = sprite2.look) != null) {
                    lookData = look.getLookData();
                }
                iIndexOf = lookList.indexOf(lookData);
            }
            look3.setLookListIndexBeforeLookRequest(iIndexOf);
        }
    }
}
