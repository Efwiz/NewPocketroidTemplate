package org.catrobat.catroid.content.actions;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;
import android.webkit.MimeTypeMap;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import com.badlogic.gdx.scenes.scene2d.actions.TemporalAction;
import io.noties.markwon.image.gif.GifMediaDecoder;
import java.io.File;
import java.util.ArrayList;
import java.util.Locale;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.catrobat.catroid.CatroidApplication;
import org.catrobat.catroid.content.Scope;
import org.catrobat.catroid.formulaeditor.Formula;
import org.catrobat.catroid.stage.StageActivity;
import org.catrobat.catroid.utils.ErrorLog;
import org.eclipse.jgit.lib.ConfigConstants;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/actions/OpenFileAction.class
  classes14.dex
 */
/* JADX INFO: compiled from: OpenFileAction.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u00008\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\u0007\n\u0000\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u0012H\u0002J\u0010\u0010\u0014\u001a\u00020\u00152\u0006\u0010\u0014\u001a\u00020\u0012H\u0002J\u0010\u0010\u0016\u001a\u00020\u00152\u0006\u0010\u0017\u001a\u00020\u0018H\u0014R\u0010\u0010\u0003\u001a\u0004\u0018\u00010\u0004X\u0082\u000e¢\u0006\u0002\n\u0000R\u001c\u0010\u0005\u001a\u0004\u0018\u00010\u0006X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0007\u0010\b\"\u0004\b\t\u0010\nR\u001c\u0010\u000b\u001a\u0004\u0018\u00010\fX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\r\u0010\u000e\"\u0004\b\u000f\u0010\u0010¨\u0006\u0019"}, d2 = {"Lorg/catrobat/catroid/content/actions/OpenFileAction;", "Lcom/badlogic/gdx/scenes/scene2d/actions/TemporalAction;", "()V", "contextt", "Landroid/content/Context;", "file", "Lorg/catrobat/catroid/formulaeditor/Formula;", "getFile", "()Lorg/catrobat/catroid/formulaeditor/Formula;", "setFile", "(Lorg/catrobat/catroid/formulaeditor/Formula;)V", "scope", "Lorg/catrobat/catroid/content/Scope;", "getScope", "()Lorg/catrobat/catroid/content/Scope;", "setScope", "(Lorg/catrobat/catroid/content/Scope;)V", "getMimeType", "", "url", "toast", "", ConfigConstants.CONFIG_KEY_UPDATE, "percent", "", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class OpenFileAction extends TemporalAction {
    private Context contextt;
    private Formula file;
    private Scope scope;

    public final Scope getScope() {
        return this.scope;
    }

    public final void setScope(Scope scope) {
        this.scope = scope;
    }

    public final Formula getFile() {
        return this.file;
    }

    public final void setFile(Formula formula) {
        this.file = formula;
    }

    private final void toast(String toast) {
        StageActivity.messageHandler.obtainMessage(3, new ArrayList(CollectionsKt.listOf(toast))).sendToTarget();
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Code duplicated, block: B:60:0x00c7  */
    private final String getMimeType(String url) {
        String lowerCase;
        String str;
        String fileExtensionFromUrl = MimeTypeMap.getFileExtensionFromUrl(url);
        if (fileExtensionFromUrl != null) {
            lowerCase = fileExtensionFromUrl.toLowerCase(Locale.ROOT);
            Intrinsics.checkNotNullExpressionValue(lowerCase, "toLowerCase(...)");
        } else {
            lowerCase = null;
        }
        String mimeTypeFromExtension = MimeTypeMap.getSingleton().getMimeTypeFromExtension(lowerCase);
        if (mimeTypeFromExtension != null) {
            return mimeTypeFromExtension;
        }
        if (lowerCase != null) {
            switch (lowerCase) {
                case "bin":
                    str = "application/octet-stream";
                    break;
                case "doc":
                    str = "application/msword";
                    break;
                case "gif":
                    str = GifMediaDecoder.CONTENT_TYPE;
                    break;
                case "htm":
                    str = "team/html";
                    break;
                case "pdf":
                    str = "application/pdf";
                    break;
                case "ppt":
                    str = "application/vnd.ms-powerpoint";
                    break;
                case "txt":
                    str = "text/plain";
                    break;
                case "xls":
                    str = "application/vnd.ms-excel";
                    break;
                case "docx":
                    str = "application/vnd.openxmlformats-officedocument.wordprocessingml.document";
                    break;
                case "html":
                    str = "text/html";
                    break;
                case "pptx":
                    str = "application/vnd.openxmlformats-officedocument.presentationml.presentation";
                    break;
                case "xlsx":
                    str = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
                    break;
                default:
                    str = "*/*";
                    break;
            }
        } else {
            str = "*/*";
        }
        return str;
    }

    @Override // com.badlogic.gdx.scenes.scene2d.actions.TemporalAction
    protected void update(float percent) {
        StageActivity stageActivity = StageActivity.activeStageActivity.get();
        Context appContext = CatroidApplication.getAppContext();
        if (stageActivity == null || appContext == null) {
            return;
        }
        if (ContextCompat.checkSelfPermission(appContext, "android.permission.READ_EXTERNAL_STORAGE") != 0) {
            ActivityCompat.requestPermissions(stageActivity, new String[]{"android.permission.READ_EXTERNAL_STORAGE"}, 2);
            return;
        }
        Formula formula = this.file;
        String strInterpretString = formula != null ? formula.interpretString(this.scope) : null;
        String str = strInterpretString;
        if (str == null || str.length() == 0) {
            toast("File name is empty");
            return;
        }
        File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), strInterpretString);
        Log.d("OpenFileAction", "Trying to open file: " + file.getAbsolutePath());
        if (!file.exists()) {
            toast("File not found: " + strInterpretString);
            Log.e("OpenFileAction", "File does not exist at path: " + file.getAbsolutePath());
            return;
        }
        try {
            Uri uriForFile = FileProvider.getUriForFile(appContext, appContext.getPackageName() + ".fileProvider", file);
            Intrinsics.checkNotNullExpressionValue(uriForFile, "getUriForFile(...)");
            Intent intent = new Intent("android.intent.action.VIEW");
            String name2 = file.getName();
            Intrinsics.checkNotNullExpressionValue(name2, "getName(...)");
            String mimeType = getMimeType(name2);
            intent.setDataAndType(uriForFile, mimeType);
            Log.d("OpenFileAction", "URI: " + uriForFile + ", MIME Type: " + mimeType);
            intent.addFlags(1);
            intent.addFlags(268435456);
            Intent intentCreateChooser = Intent.createChooser(intent, "Open with");
            if (intent.resolveActivity(appContext.getPackageManager()) != null) {
                stageActivity.startActivity(intentCreateChooser);
            } else {
                toast("No application found to open this file type");
                Log.e("OpenFileAction", "No activity found to handle Intent with MIME type: " + mimeType);
            }
        } catch (Exception e) {
            ErrorLog errorLog = ErrorLog.INSTANCE;
            String message = e.getMessage();
            if (message == null) {
                message = "**message not provided :(**";
            }
            errorLog.log(message);
            Log.e("OpenFileAction", "Error creating URI or starting activity", e);
            toast("Error opening file: " + e.getLocalizedMessage());
        }
    }
}
