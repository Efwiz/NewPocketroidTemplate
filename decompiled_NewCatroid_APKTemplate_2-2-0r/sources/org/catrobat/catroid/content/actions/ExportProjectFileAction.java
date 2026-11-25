package org.catrobat.catroid.content.actions;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.widget.Toast;
import com.badlogic.gdx.scenes.scene2d.Action;
import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.lang.ref.WeakReference;
import kotlin.Metadata;
import kotlin.io.ByteStreamsKt;
import kotlin.io.CloseableKt;
import kotlin.jvm.internal.Intrinsics;
import org.catrobat.catroid.CatroidApplication;
import org.catrobat.catroid.content.Project;
import org.catrobat.catroid.content.Scope;
import org.catrobat.catroid.formulaeditor.Formula;
import org.catrobat.catroid.stage.StageActivity;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/actions/ExportProjectFileAction.class
  classes14.dex
 */
/* JADX INFO: compiled from: ExportProjectFileAction.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\u0007\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0012\u001a\u00020\u00042\u0006\u0010\u0013\u001a\u00020\u0014H\u0016J\b\u0010\u0015\u001a\u00020\u0016H\u0016J\b\u0010\u0017\u001a\u00020\u0016H\u0016J\b\u0010\u0018\u001a\u00020\u0016H\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u000e¢\u0006\u0002\n\u0000R\u001c\u0010\u0005\u001a\u0004\u0018\u00010\u0006X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0007\u0010\b\"\u0004\b\t\u0010\nR\u001c\u0010\u000b\u001a\u0004\u0018\u00010\fX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\r\u0010\u000e\"\u0004\b\u000f\u0010\u0010R\u000e\u0010\u0011\u001a\u00020\u0004X\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006\u0019"}, d2 = {"Lorg/catrobat/catroid/content/actions/ExportProjectFileAction;", "Lcom/badlogic/gdx/scenes/scene2d/Action;", "()V", "finished", "", "projectFileName", "Lorg/catrobat/catroid/formulaeditor/Formula;", "getProjectFileName", "()Lorg/catrobat/catroid/formulaeditor/Formula;", "setProjectFileName", "(Lorg/catrobat/catroid/formulaeditor/Formula;)V", "scope", "Lorg/catrobat/catroid/content/Scope;", "getScope", "()Lorg/catrobat/catroid/content/Scope;", "setScope", "(Lorg/catrobat/catroid/content/Scope;)V", "started", "act", "delta", "", "reset", "", "restart", "runExport", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ExportProjectFileAction extends Action {
    private volatile boolean finished;
    private Formula projectFileName;
    private Scope scope;
    private boolean started;

    public final Scope getScope() {
        return this.scope;
    }

    public final void setScope(Scope scope) {
        this.scope = scope;
    }

    public final Formula getProjectFileName() {
        return this.projectFileName;
    }

    public final void setProjectFileName(Formula formula) {
        this.projectFileName = formula;
    }

    @Override // com.badlogic.gdx.scenes.scene2d.Action
    public boolean act(float delta) {
        if (!this.started) {
            this.started = true;
            runExport();
        }
        return this.finished;
    }

    private final void runExport() {
        Scope scope = this.scope;
        Project project = scope != null ? scope.getProject() : null;
        if (project == null) {
            this.finished = true;
            return;
        }
        Context appContext = CatroidApplication.getAppContext();
        WeakReference<StageActivity> weakReference = StageActivity.activeStageActivity;
        final StageActivity stageActivity = weakReference != null ? weakReference.get() : null;
        if (stageActivity == null) {
            Toast.makeText(appContext, "Ошибка: не удалось получить доступ к Activity", 0).show();
            this.finished = true;
            return;
        }
        Formula formula = this.projectFileName;
        final String strInterpretString = formula != null ? formula.interpretString(this.scope) : null;
        String str = strInterpretString;
        if (str == null || str.length() == 0) {
            this.finished = true;
            return;
        }
        final File file = project.getFile(strInterpretString);
        Intrinsics.checkNotNullExpressionValue(file, "getFile(...)");
        if (!file.exists()) {
            this.finished = true;
        } else {
            stageActivity.runOnUiThread(new Runnable() { // from class: org.catrobat.catroid.content.actions.ExportProjectFileAction$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    ExportProjectFileAction.runExport$lambda$1(stageActivity, strInterpretString, this, file);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void runExport$lambda$1(final StageActivity stageActivity, String str, final ExportProjectFileAction this$0, final File sourceFile) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNullParameter(sourceFile, "$sourceFile");
        final Intent intent = new Intent("android.intent.action.CREATE_DOCUMENT");
        intent.addCategory("android.intent.category.OPENABLE");
        intent.setType("*/*");
        intent.putExtra("android.intent.extra.TITLE", str);
        stageActivity.queueIntent(new StageActivity.IntentListener() { // from class: org.catrobat.catroid.content.actions.ExportProjectFileAction$runExport$1$1
            @Override // org.catrobat.catroid.stage.StageActivity.IntentListener
            /* JADX INFO: renamed from: getTargetIntent, reason: from getter */
            public Intent get$intent() {
                return intent;
            }

            @Override // org.catrobat.catroid.stage.StageActivity.IntentListener
            public boolean onIntentResult(int requestCode, int resultCode, Intent data) {
                if (resultCode == -1 && data != null && data.getData() != null) {
                    Uri data2 = data.getData();
                    Intrinsics.checkNotNull(data2);
                    try {
                        OutputStream outputStreamOpenOutputStream = stageActivity.getContentResolver().openOutputStream(data2);
                        if (outputStreamOpenOutputStream != null) {
                            OutputStream outputStream = outputStreamOpenOutputStream;
                            try {
                                OutputStream outputStream2 = outputStream;
                                FileInputStream fileInputStream = new FileInputStream(sourceFile);
                                try {
                                    long jCopyTo$default = ByteStreamsKt.copyTo$default(fileInputStream, outputStream2, 0, 2, null);
                                    CloseableKt.closeFinally(fileInputStream, null);
                                    Long.valueOf(jCopyTo$default);
                                    CloseableKt.closeFinally(outputStream, null);
                                } catch (Throwable th) {
                                    try {
                                        throw th;
                                    } catch (Throwable th2) {
                                        CloseableKt.closeFinally(fileInputStream, th);
                                        throw th2;
                                    }
                                }
                            } catch (Throwable th3) {
                                try {
                                    throw th3;
                                } catch (Throwable th4) {
                                    CloseableKt.closeFinally(outputStream, th3);
                                    throw th4;
                                }
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                this$0.finished = true;
                return true;
            }
        });
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
        this.projectFileName = null;
    }
}
