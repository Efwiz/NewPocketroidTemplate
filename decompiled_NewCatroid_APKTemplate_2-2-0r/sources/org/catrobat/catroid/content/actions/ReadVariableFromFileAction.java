package org.catrobat.catroid.content.actions;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.documentfile.provider.DocumentFile;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.google.firebase.analytics.FirebaseAnalytics;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.io.CloseableKt;
import kotlin.io.FilesKt;
import kotlin.io.TextStreamsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.Regex;
import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.CatroidApplication;
import org.catrobat.catroid.common.Constants;
import org.catrobat.catroid.content.Scope;
import org.catrobat.catroid.formulaeditor.Formula;
import org.catrobat.catroid.formulaeditor.UserVariable;
import org.catrobat.catroid.stage.StageActivity;
import org.catrobat.catroid.utils.Utils;
import org.jetbrains.kotlin.com.intellij.psi.PsiTreeChangeEvent;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/actions/ReadVariableFromFileAction.class
  classes14.dex
 */
/* JADX INFO: compiled from: ReadVariableFromFileAction.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000h\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u0007\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\n\n\u0002\u0018\u0002\n\u0002\b\u0004\u0018\u00002\u00020\u00012\u00020\u0002B\u0005¢\u0006\u0002\u0010\u0003J\u0010\u0010\u001c\u001a\u00020\u00052\u0006\u0010\u001d\u001a\u00020\u001eH\u0016J\u0010\u0010\u001f\u001a\u00020 2\u0006\u0010!\u001a\u00020\"H\u0002J\u0012\u0010#\u001a\u0004\u0018\u00010$2\u0006\u0010%\u001a\u00020&H\u0007J\b\u0010'\u001a\u00020&H\u0002J\b\u0010(\u001a\u00020)H\u0017J\"\u0010*\u001a\u00020\u00052\u0006\u0010+\u001a\u00020,2\u0006\u0010-\u001a\u00020,2\b\u0010.\u001a\u0004\u0018\u00010)H\u0016J\u0010\u0010/\u001a\u00020&2\u0006\u00100\u001a\u00020$H\u0007J\u0010\u00101\u001a\u00020 2\u0006\u0010!\u001a\u00020\"H\u0002J\u0010\u00102\u001a\u00020 2\u0006\u0010%\u001a\u00020&H\u0002J\b\u00103\u001a\u00020 H\u0002J\u0010\u00104\u001a\u00020 2\u0006\u0010%\u001a\u00020&H\u0007J\u000e\u00105\u001a\u00020 2\u0006\u00106\u001a\u000207J\u0010\u00108\u001a\u00020 2\u0006\u0010%\u001a\u00020&H\u0002J\u0010\u00109\u001a\u00020 2\u0006\u0010:\u001a\u00020&H\u0002R\u001a\u0010\u0004\u001a\u00020\u0005X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0006\u0010\u0007\"\u0004\b\b\u0010\tR\u001c\u0010\n\u001a\u0004\u0018\u00010\u000bX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\f\u0010\r\"\u0004\b\u000e\u0010\u000fR\u001c\u0010\u0010\u001a\u0004\u0018\u00010\u0011X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0012\u0010\u0013\"\u0004\b\u0014\u0010\u0015R\u001c\u0010\u0016\u001a\u0004\u0018\u00010\u0017X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0018\u0010\u0019\"\u0004\b\u001a\u0010\u001b¨\u0006;"}, d2 = {"Lorg/catrobat/catroid/content/actions/ReadVariableFromFileAction;", "Lcom/badlogic/gdx/scenes/scene2d/Action;", "Lorg/catrobat/catroid/stage/StageActivity$IntentListener;", "()V", "deleteFile", "", "getDeleteFile", "()Z", "setDeleteFile", "(Z)V", "formula", "Lorg/catrobat/catroid/formulaeditor/Formula;", "getFormula", "()Lorg/catrobat/catroid/formulaeditor/Formula;", "setFormula", "(Lorg/catrobat/catroid/formulaeditor/Formula;)V", "scope", "Lorg/catrobat/catroid/content/Scope;", "getScope", "()Lorg/catrobat/catroid/content/Scope;", "setScope", "(Lorg/catrobat/catroid/content/Scope;)V", "userVariable", "Lorg/catrobat/catroid/formulaeditor/UserVariable;", "getUserVariable", "()Lorg/catrobat/catroid/formulaeditor/UserVariable;", "setUserVariable", "(Lorg/catrobat/catroid/formulaeditor/UserVariable;)V", "act", "delta", "", "deleteFileWithUri", "", "uri", "Landroid/net/Uri;", "getFile", "Ljava/io/File;", PsiTreeChangeEvent.PROP_FILE_NAME, "", "getFileName", "getTargetIntent", "Landroid/content/Intent;", "onIntentResult", "requestCode", "", "resultCode", "data", "readFromFile", "file", "readUriContentAndWriteToVariable", "readUsingLegacyExternalStorage", "readUsingSystemFilePicker", "readVariableFromFile", "request", "activity", "Landroid/app/Activity;", "showSuccessMessage", "writeContentToVariable", FirebaseAnalytics.Param.CONTENT, "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ReadVariableFromFileAction extends Action implements StageActivity.IntentListener {
    private boolean deleteFile;
    private Formula formula;
    private Scope scope;
    private UserVariable userVariable;

    public final Scope getScope() {
        return this.scope;
    }

    public final void setScope(Scope scope) {
        this.scope = scope;
    }

    public final Formula getFormula() {
        return this.formula;
    }

    public final void setFormula(Formula formula) {
        this.formula = formula;
    }

    public final UserVariable getUserVariable() {
        return this.userVariable;
    }

    public final void setUserVariable(UserVariable userVariable) {
        this.userVariable = userVariable;
    }

    public final boolean getDeleteFile() {
        return this.deleteFile;
    }

    public final void setDeleteFile(boolean z) {
        this.deleteFile = z;
    }

    public final void request(Activity activity) {
        Intrinsics.checkNotNullParameter(activity, "activity");
        ActivityCompat.requestPermissions(activity, new String[]{"android.permission.READ_EXTERNAL_STORAGE"}, 1002);
    }

    @Override // com.badlogic.gdx.scenes.scene2d.Action
    public boolean act(float delta) {
        final StageActivity stageActivity;
        if (ContextCompat.checkSelfPermission(CatroidApplication.getAppContext(), "android.permission.READ_EXTERNAL_STORAGE") != 0 && (stageActivity = StageActivity.activeStageActivity.get()) != null) {
            stageActivity.runOnUiThread(new Runnable() { // from class: org.catrobat.catroid.content.actions.ReadVariableFromFileAction$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    ReadVariableFromFileAction.act$lambda$0(this.f$0, stageActivity);
                }
            });
        }
        if (this.userVariable != null && this.formula != null) {
            readVariableFromFile(getFileName());
        }
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void act$lambda$0(ReadVariableFromFileAction this$0, StageActivity stageActivity) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.request(stageActivity);
    }

    private final void showSuccessMessage(String fileName) {
        CatroidApplication.getAppContext();
        StageActivity.messageHandler.obtainMessage(3, new ArrayList(CollectionsKt.listOf(fileName))).sendToTarget();
    }

    public final void readVariableFromFile(String fileName) {
        Intrinsics.checkNotNullParameter(fileName, "fileName");
        if (Intrinsics.areEqual(fileName, Constants.TEXT_FILE_EXTENSION)) {
            readUsingSystemFilePicker();
        } else {
            readUsingLegacyExternalStorage(fileName);
        }
    }

    private final void readUsingSystemFilePicker() {
        Message messageObtainMessage;
        Handler handler = StageActivity.messageHandler;
        if (handler == null || (messageObtainMessage = handler.obtainMessage(0, CollectionsKt.arrayListOf(this))) == null) {
            return;
        }
        messageObtainMessage.sendToTarget();
    }

    private final void readUsingLegacyExternalStorage(String fileName) {
        File file = getFile(fileName);
        if (file != null) {
            writeContentToVariable(readFromFile(file));
            if (this.deleteFile) {
                file.delete();
            }
        }
    }

    public final File getFile(String fileName) {
        Intrinsics.checkNotNullParameter(fileName, "fileName");
        File file = new File(Constants.DOWNLOAD_DIRECTORY, fileName);
        if (file.exists()) {
            return file;
        }
        return null;
    }

    public final String readFromFile(File file) {
        Intrinsics.checkNotNullParameter(file, "file");
        try {
            return FilesKt.readText$default(file, null, 1, null);
        } catch (IOException unused) {
            Log.e(getClass().getSimpleName(), "Could not read variable value from storage.");
            return "0";
        }
    }

    private final void writeContentToVariable(String content) {
        UserVariable userVariable = this.userVariable;
        if (userVariable == null) {
            return;
        }
        userVariable.setValue(content);
    }

    private final String getFileName() {
        Formula formula = this.formula;
        String strSanitizeFileName = Utils.sanitizeFileName(formula != null ? formula.interpretString(this.scope) : null);
        Intrinsics.checkNotNull(strSanitizeFileName);
        if (!new Regex(Constants.ANY_EXTENSION_REGEX).containsMatchIn(strSanitizeFileName)) {
            strSanitizeFileName = strSanitizeFileName + Constants.TEXT_FILE_EXTENSION;
        }
        Intrinsics.checkNotNull(strSanitizeFileName);
        return strSanitizeFileName;
    }

    private final void readUriContentAndWriteToVariable(Uri uri) {
        StageActivity stageActivity = StageActivity.activeStageActivity.get();
        ContentResolver contentResolver = stageActivity != null ? stageActivity.getContentResolver() : null;
        if (contentResolver == null) {
            return;
        }
        try {
            InputStream inputStreamOpenInputStream = contentResolver.openInputStream(uri);
            if (inputStreamOpenInputStream != null) {
                InputStream inputStream = inputStreamOpenInputStream;
                try {
                    writeContentToVariable(TextStreamsKt.readText(new InputStreamReader(inputStream, "UTF-8")));
                    Unit unit = Unit.INSTANCE;
                    CloseableKt.closeFinally(inputStream, null);
                } catch (Throwable th) {
                    try {
                        throw th;
                    } catch (Throwable th2) {
                        CloseableKt.closeFinally(inputStream, th);
                        throw th2;
                    }
                }
            }
        } catch (IOException unused) {
            Log.e(getClass().getSimpleName(), "Could not read variable value from storage. (using uri)");
        }
    }

    private final void deleteFileWithUri(Uri uri) {
        Context context;
        DocumentFile documentFileFromSingleUri;
        StageActivity stageActivity = StageActivity.activeStageActivity.get();
        if (stageActivity == null || (context = stageActivity.getContext()) == null || (documentFileFromSingleUri = DocumentFile.fromSingleUri(context, uri)) == null || documentFileFromSingleUri.delete()) {
            return;
        }
        Log.e(getClass().getSimpleName(), "Could not delete file which stores the variable content.");
    }

    @Override // org.catrobat.catroid.stage.StageActivity.IntentListener
    /* JADX INFO: renamed from: getTargetIntent */
    public Intent get$intent() {
        String string;
        StageActivity stageActivity = StageActivity.activeStageActivity.get();
        Context context = stageActivity != null ? stageActivity.getContext() : null;
        if (context == null || (string = context.getString(R.string.brick_read_variable_from_file_top)) == null) {
            string = "";
        }
        Intent intent = new Intent("android.intent.action.OPEN_DOCUMENT");
        intent.setType("*/*");
        intent.putExtra("android.provider.extra.INITIAL_URI", Environment.DIRECTORY_DOWNLOADS);
        Intent intentCreateChooser = Intent.createChooser(intent, string);
        Intrinsics.checkNotNullExpressionValue(intentCreateChooser, "createChooser(...)");
        return intentCreateChooser;
    }

    @Override // org.catrobat.catroid.stage.StageActivity.IntentListener
    public boolean onIntentResult(int requestCode, int resultCode, Intent data) {
        Uri data2;
        if (resultCode != -1) {
            return false;
        }
        if (data == null || (data2 = data.getData()) == null) {
            return true;
        }
        readUriContentAndWriteToVariable(data2);
        if (!this.deleteFile) {
            return true;
        }
        deleteFileWithUri(data2);
        return true;
    }
}
