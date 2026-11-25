package org.catrobat.catroid.content.actions;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import com.badlogic.gdx.scenes.scene2d.actions.TemporalAction;
import java.util.ArrayList;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import kotlinx.coroutines.BuildersKt__Builders_commonKt;
import kotlinx.coroutines.CoroutineScopeKt;
import kotlinx.coroutines.Dispatchers;
import org.catrobat.catroid.CatroidApplication;
import org.catrobat.catroid.common.Constants;
import org.catrobat.catroid.content.Project;
import org.catrobat.catroid.content.Scope;
import org.catrobat.catroid.formulaeditor.Formula;
import org.catrobat.catroid.formulaeditor.InterpretationException;
import org.catrobat.catroid.stage.StageActivity;
import org.eclipse.jgit.lib.ConfigConstants;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/actions/FilesUrlAction.class
  classes14.dex
 */
/* JADX INFO: compiled from: FilesUrlAction.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0006\n\u0002\u0010\u0007\n\u0000\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0016\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\u00152\u0006\u0010\u0016\u001a\u00020\u0015J\u0012\u0010\u0005\u001a\u0004\u0018\u00010\u00152\b\u0010\u0017\u001a\u0004\u0018\u00010\u0004J\u000e\u0010\u0018\u001a\u00020\u00132\u0006\u0010\u0019\u001a\u00020\u0015J\u0010\u0010\u001a\u001a\u00020\u00132\u0006\u0010\u001b\u001a\u00020\u001cH\u0014R\u001c\u0010\u0003\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\bR\u001c\u0010\t\u001a\u0004\u0018\u00010\nX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000b\u0010\f\"\u0004\b\r\u0010\u000eR\u001c\u0010\u000f\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0010\u0010\u0006\"\u0004\b\u0011\u0010\b¨\u0006\u001d"}, d2 = {"Lorg/catrobat/catroid/content/actions/FilesUrlAction;", "Lcom/badlogic/gdx/scenes/scene2d/actions/TemporalAction;", "()V", "name", "Lorg/catrobat/catroid/formulaeditor/Formula;", "getName", "()Lorg/catrobat/catroid/formulaeditor/Formula;", "setName", "(Lorg/catrobat/catroid/formulaeditor/Formula;)V", "scope", "Lorg/catrobat/catroid/content/Scope;", "getScope", "()Lorg/catrobat/catroid/content/Scope;", "setScope", "(Lorg/catrobat/catroid/content/Scope;)V", "url", "getUrl", "setUrl", "downloadFileFromUrl", "", "fileUrl", "", "newFileName", "inputName", "showToast", "toast", ConfigConstants.CONFIG_KEY_UPDATE, "percent", "", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FilesUrlAction extends TemporalAction {
    private Formula name;
    private Scope scope;
    private Formula url;

    public final Scope getScope() {
        return this.scope;
    }

    public final void setScope(Scope scope) {
        this.scope = scope;
    }

    public final Formula getUrl() {
        return this.url;
    }

    public final void setUrl(Formula formula) {
        this.url = formula;
    }

    public final Formula getName() {
        return this.name;
    }

    public final void setName(Formula formula) {
        this.name = formula;
    }

    @Override // com.badlogic.gdx.scenes.scene2d.actions.TemporalAction
    protected void update(float percent) throws InterpretationException {
        final StageActivity stageActivity = StageActivity.activeStageActivity.get();
        if (stageActivity != null) {
            stageActivity.runOnUiThread(new Runnable() { // from class: org.catrobat.catroid.content.actions.FilesUrlAction$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    FilesUrlAction.update$lambda$0(stageActivity);
                }
            });
        }
        Formula formula = this.url;
        String strInterpretString = formula != null ? formula.interpretString(this.scope) : null;
        if (strInterpretString == null) {
            strInterpretString = "http://e95814zx.beget.tech/map.jpg";
        }
        String name2 = getName(this.name);
        if (name2 == null) {
            name2 = "fileFromUrl.jpg";
        }
        downloadFileFromUrl(strInterpretString, name2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void update$lambda$0(StageActivity stageActivity) {
        if (ContextCompat.checkSelfPermission(CatroidApplication.getAppContext(), "android.permission.WRITE_EXTERNAL_STORAGE") != 0) {
            ActivityCompat.requestPermissions(stageActivity, new String[]{"android.permission.WRITE_EXTERNAL_STORAGE"}, 1);
        }
    }

    public final String getName(Formula inputName) throws InterpretationException {
        if (inputName == null) {
            return null;
        }
        String strInterpretString = inputName.interpretString(this.scope);
        Intrinsics.checkNotNull(strInterpretString);
        int iLastIndexOf$default = StringsKt.lastIndexOf$default((CharSequence) strInterpretString, '.', 0, false, 6, (Object) null);
        return (iLastIndexOf$default > 0 || iLastIndexOf$default < strInterpretString.length() + (-1)) ? strInterpretString : strInterpretString + Constants.JPEG_IMAGE_EXTENSION;
    }

    public final void showToast(String toast) {
        Intrinsics.checkNotNullParameter(toast, "toast");
        StageActivity.messageHandler.obtainMessage(3, new ArrayList(CollectionsKt.listOf(toast))).sendToTarget();
    }

    public final void downloadFileFromUrl(String fileUrl, String newFileName) {
        Project project;
        Intrinsics.checkNotNullParameter(fileUrl, "fileUrl");
        Intrinsics.checkNotNullParameter(newFileName, "newFileName");
        Scope scope = this.scope;
        if (scope == null || (project = scope.getProject()) == null) {
            return;
        }
        BuildersKt__Builders_commonKt.launch$default(CoroutineScopeKt.CoroutineScope(Dispatchers.getIO()), null, null, new FilesUrlAction$downloadFileFromUrl$1$1(fileUrl, this, project, newFileName, null), 3, null);
    }
}
