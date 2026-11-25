package org.catrobat.catroid.content.actions;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import com.badlogic.gdx.scenes.scene2d.Action;
import java.io.File;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.apache.xerces.impl.xs.SchemaSymbols;
import org.catrobat.catroid.CatroidApplication;
import org.catrobat.catroid.content.Project;
import org.catrobat.catroid.content.Scope;
import org.catrobat.catroid.formulaeditor.UserVariable;
import org.catrobat.catroid.io.StorageOperations;
import org.catrobat.catroid.stage.StageActivity;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/actions/ChooseFileAction.class
  classes14.dex
 */
/* JADX INFO: compiled from: ChooseFileAction.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000D\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u0007\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0005\u0018\u0000 $2\u00020\u0001:\u0001$B\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0018\u001a\u00020\n2\u0006\u0010\u0019\u001a\u00020\u001aH\u0016J\u001c\u0010\u001b\u001a\u00020\u001c2\b\u0010\u001d\u001a\u0004\u0018\u00010\u001e2\b\b\u0002\u0010\u001f\u001a\u00020 H\u0002J\b\u0010!\u001a\u00020\u001cH\u0016J\b\u0010\"\u001a\u00020\u001cH\u0016J\b\u0010#\u001a\u00020\u001cH\u0002R\u001a\u0010\u0003\u001a\u00020\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\bR\u000e\u0010\t\u001a\u00020\nX\u0082\u000e¢\u0006\u0002\n\u0000R\u001c\u0010\u000b\u001a\u0004\u0018\u00010\fX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\r\u0010\u000e\"\u0004\b\u000f\u0010\u0010R\u000e\u0010\u0011\u001a\u00020\nX\u0082\u000e¢\u0006\u0002\n\u0000R\u001c\u0010\u0012\u001a\u0004\u0018\u00010\u0013X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0014\u0010\u0015\"\u0004\b\u0016\u0010\u0017¨\u0006%"}, d2 = {"Lorg/catrobat/catroid/content/actions/ChooseFileAction;", "Lcom/badlogic/gdx/scenes/scene2d/Action;", "()V", "fileType", "", "getFileType", "()I", "setFileType", "(I)V", "finished", "", "scope", "Lorg/catrobat/catroid/content/Scope;", "getScope", "()Lorg/catrobat/catroid/content/Scope;", "setScope", "(Lorg/catrobat/catroid/content/Scope;)V", "started", "variable", "Lorg/catrobat/catroid/formulaeditor/UserVariable;", "getVariable", "()Lorg/catrobat/catroid/formulaeditor/UserVariable;", "setVariable", "(Lorg/catrobat/catroid/formulaeditor/UserVariable;)V", "act", "delta", "", "addSpriteObjectFromUri", "", "uri", "Landroid/net/Uri;", SchemaSymbols.ATTVAL_EXTENSION, "", "reset", "restart", "runAsyncChoose", "Companion", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ChooseFileAction extends Action {
    private int fileType = 4;
    private volatile boolean finished;
    private Scope scope;
    private boolean started;
    private UserVariable variable;
    private static final String TAG = "ChooseFileAction";
    private static final String[] MIME_TYPES = {"image/*", "video/*", "audio/*", "application/*", "*/*"};
    private static File TARGET_DIRECTORY = new File(CatroidApplication.getAppContext().getFilesDir(), "chosen_files");

    public final Scope getScope() {
        return this.scope;
    }

    public final void setScope(Scope scope) {
        this.scope = scope;
    }

    public final int getFileType() {
        return this.fileType;
    }

    public final void setFileType(int i) {
        this.fileType = i;
    }

    public final UserVariable getVariable() {
        return this.variable;
    }

    public final void setVariable(UserVariable userVariable) {
        this.variable = userVariable;
    }

    @Override // com.badlogic.gdx.scenes.scene2d.Action
    public boolean act(float delta) {
        if (!this.started) {
            this.started = true;
            runAsyncChoose();
        }
        return this.finished;
    }

    private final void runAsyncChoose() {
        Scope scope = this.scope;
        Project project = scope != null ? scope.getProject() : null;
        if (project == null) {
            this.finished = true;
            return;
        }
        File filesDir = project.getFilesDir();
        Intrinsics.checkNotNullExpressionValue(filesDir, "getFilesDir(...)");
        TARGET_DIRECTORY = filesDir;
        final StageActivity stageActivity = StageActivity.activeStageActivity.get();
        if (stageActivity == null) {
            this.finished = true;
        } else {
            stageActivity.runOnUiThread(new Runnable() { // from class: org.catrobat.catroid.content.actions.ChooseFileAction$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    ChooseFileAction.runAsyncChoose$lambda$1(stageActivity, this);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void runAsyncChoose$lambda$1(StageActivity stageActivity, final ChooseFileAction this$0) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        final Intent intent = new Intent("android.intent.action.GET_CONTENT");
        intent.setType(MIME_TYPES[this$0.fileType]);
        intent.addCategory("android.intent.category.OPENABLE");
        stageActivity.queueIntent(new StageActivity.IntentListener() { // from class: org.catrobat.catroid.content.actions.ChooseFileAction$runAsyncChoose$1$1
            @Override // org.catrobat.catroid.stage.StageActivity.IntentListener
            public Intent getTargetIntent() {
                Intent intentCreateChooser = Intent.createChooser(intent, "Choose a file");
                Intrinsics.checkNotNullExpressionValue(intentCreateChooser, "createChooser(...)");
                return intentCreateChooser;
            }

            @Override // org.catrobat.catroid.stage.StageActivity.IntentListener
            public boolean onIntentResult(int requestCode, int resultCode, Intent data) {
                if (resultCode == -1 && data != null && data.getData() != null) {
                    ChooseFileAction.addSpriteObjectFromUri$default(this$0, data.getData(), null, 2, null);
                }
                this$0.finished = true;
                return true;
            }
        });
    }

    static /* synthetic */ void addSpriteObjectFromUri$default(ChooseFileAction chooseFileAction, Uri uri, String str, int i, Object obj) {
        if ((i & 2) != 0) {
            str = "png";
        }
        chooseFileAction.addSpriteObjectFromUri(uri, str);
    }

    private final void addSpriteObjectFromUri(Uri uri, String extension) {
        UserVariable userVariable;
        Project project;
        ContentResolver contentResolver = CatroidApplication.getAppContext().getContentResolver();
        String strResolveFileName = StorageOperations.resolveFileName(contentResolver, uri);
        if (strResolveFileName == null) {
            strResolveFileName = "file_" + System.currentTimeMillis() + extension;
        }
        try {
            Scope scope = this.scope;
            File fileCopyUriToDir = StorageOperations.copyUriToDir(contentResolver, uri, (scope == null || (project = scope.getProject()) == null) ? null : project.getFilesDir(), strResolveFileName);
            if (fileCopyUriToDir != null && fileCopyUriToDir.exists() && (userVariable = this.variable) != null) {
                userVariable.setValue(fileCopyUriToDir.getName());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override // com.badlogic.gdx.scenes.scene2d.Action
    public void restart() {
        super.restart();
        this.started = false;
        this.finished = false;
    }

    @Override // com.badlogic.gdx.scenes.scene2d.Action, com.badlogic.gdx.utils.Pool.Poolable
    public void reset() {
        super.reset();
        this.started = false;
        this.finished = false;
        this.scope = null;
        this.variable = null;
    }
}
