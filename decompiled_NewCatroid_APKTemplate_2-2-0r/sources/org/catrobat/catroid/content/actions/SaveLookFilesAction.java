package org.catrobat.catroid.content.actions;

import android.util.Log;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import com.badlogic.gdx.scenes.scene2d.actions.TemporalAction;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import kotlin.Metadata;
import kotlin.io.ByteStreamsKt;
import kotlin.io.CloseableKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import org.catrobat.catroid.CatroidApplication;
import org.catrobat.catroid.common.Constants;
import org.catrobat.catroid.common.LookData;
import org.catrobat.catroid.content.Look;
import org.catrobat.catroid.content.Project;
import org.catrobat.catroid.content.Scope;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.formulaeditor.Formula;
import org.catrobat.catroid.formulaeditor.InterpretationException;
import org.catrobat.catroid.stage.StageActivity;
import org.eclipse.jgit.lib.ConfigConstants;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/actions/SaveLookFilesAction.class
  classes14.dex
 */
/* JADX INFO: compiled from: SaveLookFilesAction.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000<\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u0007\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0006\b\u0016\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0011\u001a\u00020\u00042\u0006\u0010\u0012\u001a\u00020\u0013H\u0016J\u0016\u0010\u0014\u001a\u00020\u00152\u0006\u0010\u0016\u001a\u00020\u00172\u0006\u0010\u0018\u001a\u00020\u0019J\u0012\u0010\u0007\u001a\u0004\u0018\u00010\u00192\b\u0010\u001a\u001a\u0004\u0018\u00010\u0006J\b\u0010\u001b\u001a\u00020\u0015H\u0016J\b\u0010\u001c\u001a\u00020\u0015H\u0016J\u0010\u0010\u001d\u001a\u00020\u00152\u0006\u0010\u001e\u001a\u00020\u0013H\u0014R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u000e¢\u0006\u0002\n\u0000R\u001c\u0010\u0005\u001a\u0004\u0018\u00010\u0006X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0007\u0010\b\"\u0004\b\t\u0010\nR\u001c\u0010\u000b\u001a\u0004\u0018\u00010\fX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\r\u0010\u000e\"\u0004\b\u000f\u0010\u0010¨\u0006\u001f"}, d2 = {"Lorg/catrobat/catroid/content/actions/SaveLookFilesAction;", "Lcom/badlogic/gdx/scenes/scene2d/actions/TemporalAction;", "()V", "copied", "", "name", "Lorg/catrobat/catroid/formulaeditor/Formula;", "getName", "()Lorg/catrobat/catroid/formulaeditor/Formula;", "setName", "(Lorg/catrobat/catroid/formulaeditor/Formula;)V", "scope", "Lorg/catrobat/catroid/content/Scope;", "getScope", "()Lorg/catrobat/catroid/content/Scope;", "setScope", "(Lorg/catrobat/catroid/content/Scope;)V", "act", "delta", "", "copyFileToDownloads", "", "sourceFile", "Ljava/io/File;", "newFileName", "", "inputName", "reset", "restart", ConfigConstants.CONFIG_KEY_UPDATE, "percent", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public class SaveLookFilesAction extends TemporalAction {
    private boolean copied;
    private Formula name;
    private Scope scope;

    @Override // com.badlogic.gdx.scenes.scene2d.actions.TemporalAction
    protected void update(float percent) {
    }

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

    @Override // com.badlogic.gdx.scenes.scene2d.actions.TemporalAction, com.badlogic.gdx.scenes.scene2d.Action
    public boolean act(float delta) {
        if (this.copied) {
            return true;
        }
        final StageActivity stageActivity = StageActivity.activeStageActivity.get();
        if (stageActivity != null) {
            stageActivity.runOnUiThread(new Runnable() { // from class: org.catrobat.catroid.content.actions.SaveLookFilesAction$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    SaveLookFilesAction.act$lambda$0(stageActivity);
                }
            });
        }
        new Thread(new Runnable() { // from class: org.catrobat.catroid.content.actions.SaveLookFilesAction$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() throws InterpretationException {
                SaveLookFilesAction.act$lambda$2(this.f$0);
            }
        }).start();
        this.copied = true;
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void act$lambda$0(StageActivity stageActivity) {
        if (ContextCompat.checkSelfPermission(CatroidApplication.getAppContext(), "android.permission.WRITE_EXTERNAL_STORAGE") != 0) {
            ActivityCompat.requestPermissions(stageActivity, new String[]{"android.permission.WRITE_EXTERNAL_STORAGE"}, 1);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void act$lambda$2(SaveLookFilesAction this$0) throws InterpretationException {
        Sprite sprite;
        Look look;
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Scope scope = this$0.scope;
        if (scope == null || (sprite = scope.getSprite()) == null || (look = sprite.look) == null) {
            return;
        }
        Intrinsics.checkNotNull(look);
        LookData lookData = look.getLookData();
        if (lookData == null) {
            return;
        }
        Intrinsics.checkNotNull(lookData);
        File file = lookData.getFile();
        if (file == null) {
            return;
        }
        Intrinsics.checkNotNull(file);
        if (!file.exists()) {
            Log.e("SaveLookFilesAction", "Source file does not exist: " + file.getAbsolutePath());
            return;
        }
        String name2 = this$0.getName(this$0.name);
        if (name2 == null) {
            name2 = file.getName();
        }
        Intrinsics.checkNotNull(name2);
        this$0.copyFileToDownloads(file, name2);
    }

    @Override // com.badlogic.gdx.scenes.scene2d.actions.TemporalAction, com.badlogic.gdx.scenes.scene2d.Action
    public void restart() {
        super.restart();
        this.copied = false;
    }

    @Override // com.badlogic.gdx.scenes.scene2d.actions.TemporalAction, com.badlogic.gdx.scenes.scene2d.Action, com.badlogic.gdx.utils.Pool.Poolable
    public void reset() {
        super.reset();
        this.copied = false;
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

    public final void copyFileToDownloads(File sourceFile, String newFileName) {
        Project project;
        Intrinsics.checkNotNullParameter(sourceFile, "sourceFile");
        Intrinsics.checkNotNullParameter(newFileName, "newFileName");
        Scope scope = this.scope;
        if (scope == null || (project = scope.getProject()) == null) {
            return;
        }
        File file = new File(project.getFilesDir(), newFileName);
        try {
            FileInputStream fileInputStream = new FileInputStream(sourceFile);
            try {
                FileInputStream fileInputStream2 = fileInputStream;
                FileOutputStream fileOutputStream = new FileOutputStream(file);
                try {
                    ByteStreamsKt.copyTo$default(fileInputStream2, fileOutputStream, 0, 2, null);
                    CloseableKt.closeFinally(fileOutputStream, null);
                    CloseableKt.closeFinally(fileInputStream, null);
                    Log.d("SaveLook", "Файл скопирован: " + file.getAbsolutePath());
                } catch (Throwable th) {
                    try {
                        throw th;
                    } catch (Throwable th2) {
                        CloseableKt.closeFinally(fileOutputStream, th);
                        throw th2;
                    }
                }
            } catch (Throwable th3) {
                try {
                    throw th3;
                } catch (Throwable th4) {
                    CloseableKt.closeFinally(fileInputStream, th3);
                    throw th4;
                }
            }
        } catch (IOException unused) {
            Log.e("SaveLook", "Ошибка при копировании файла");
        }
    }
}
