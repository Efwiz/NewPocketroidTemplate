package org.catrobat.catroid.content.actions;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import com.badlogic.gdx.scenes.scene2d.Action;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.CatroidApplication;
import org.catrobat.catroid.common.Constants;
import org.catrobat.catroid.content.Scope;
import org.catrobat.catroid.embroidery.DSTFileGenerator;
import org.catrobat.catroid.embroidery.EmbroideryPatternManager;
import org.catrobat.catroid.formulaeditor.Formula;
import org.catrobat.catroid.io.StorageOperations;
import org.catrobat.catroid.stage.StageActivity;
import org.catrobat.catroid.stage.StageListener;
import org.catrobat.catroid.utils.Utils;
import org.jetbrains.kotlin.com.intellij.psi.PsiTreeChangeEvent;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/actions/WriteEmbroideryToFileAction.class
  classes14.dex
 */
/* JADX INFO: compiled from: WriteEmbroideryToFileAction.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000b\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0007\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0006\u0018\u00002\u00020\u00012\u00020\u0002B\u0005¢\u0006\u0002\u0010\u0003J\u0010\u0010\u0014\u001a\u00020\u00152\u0006\u0010\u0016\u001a\u00020\u0017H\u0016J\u0012\u0010\u0018\u001a\u0004\u0018\u00010\u00192\u0006\u0010\u001a\u001a\u00020\u001bH\u0007J\b\u0010\u001c\u001a\u00020\u001bH\u0002J\b\u0010\u001d\u001a\u00020\u001eH\u0017J\"\u0010\u001f\u001a\u00020\u00152\u0006\u0010 \u001a\u00020!2\u0006\u0010\"\u001a\u00020!2\b\u0010#\u001a\u0004\u0018\u00010\u001eH\u0016J\b\u0010$\u001a\u00020%H\u0002J\u0010\u0010&\u001a\u00020%2\u0006\u0010'\u001a\u00020\u0019H\u0002J\u0010\u0010(\u001a\u00020%2\u0006\u0010)\u001a\u00020*H\u0002J\b\u0010+\u001a\u00020%H\u0002J\u0010\u0010,\u001a\u00020%2\u0006\u0010-\u001a\u00020\u0019H\u0007J\b\u0010.\u001a\u00020%H\u0002J\b\u0010/\u001a\u00020%H\u0002R\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007R\u001c\u0010\b\u001a\u0004\u0018\u00010\tX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\n\u0010\u000b\"\u0004\b\f\u0010\rR\u001c\u0010\u000e\u001a\u0004\u0018\u00010\u000fX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0010\u0010\u0011\"\u0004\b\u0012\u0010\u0013¨\u00060"}, d2 = {"Lorg/catrobat/catroid/content/actions/WriteEmbroideryToFileAction;", "Lcom/badlogic/gdx/scenes/scene2d/Action;", "Lorg/catrobat/catroid/stage/StageActivity$IntentListener;", "()V", "context", "Landroid/content/Context;", "getContext", "()Landroid/content/Context;", "formula", "Lorg/catrobat/catroid/formulaeditor/Formula;", "getFormula", "()Lorg/catrobat/catroid/formulaeditor/Formula;", "setFormula", "(Lorg/catrobat/catroid/formulaeditor/Formula;)V", "scope", "Lorg/catrobat/catroid/content/Scope;", "getScope", "()Lorg/catrobat/catroid/content/Scope;", "setScope", "(Lorg/catrobat/catroid/content/Scope;)V", "act", "", "delta", "", "createFile", "Ljava/io/File;", PsiTreeChangeEvent.PROP_FILE_NAME, "", "getFileName", "getTargetIntent", "Landroid/content/Intent;", "onIntentResult", "requestCode", "", "resultCode", "data", "showMessageEmbroideryIsMissing", "", "writeEmbroideryDataToFile", "destinationFile", "writeEmbroideryDataToUri", "uri", "Landroid/net/Uri;", "writeEmbroideryToFile", "writeToFile", "file", "writeUsingLegacyExternalStorage", "writeUsingSystemFilePicker", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class WriteEmbroideryToFileAction extends Action implements StageActivity.IntentListener {
    private final Context context;
    private Formula formula;
    private Scope scope;

    public WriteEmbroideryToFileAction() {
        Context appContext = CatroidApplication.getAppContext();
        Intrinsics.checkNotNullExpressionValue(appContext, "getAppContext(...)");
        this.context = appContext;
    }

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

    public final Context getContext() {
        return this.context;
    }

    @Override // com.badlogic.gdx.scenes.scene2d.Action
    public boolean act(float delta) throws Throwable {
        StageListener stageListener;
        EmbroideryPatternManager embroideryPatternManager;
        if (this.formula == null) {
            return true;
        }
        StageActivity stageActivity = StageActivity.activeStageActivity.get();
        if (stageActivity != null && (stageListener = stageActivity.stageListener) != null && (embroideryPatternManager = stageListener.embroideryPatternManager) != null && embroideryPatternManager.validPatternExists()) {
            writeEmbroideryToFile();
        } else {
            showMessageEmbroideryIsMissing();
        }
        return true;
    }

    private final void writeEmbroideryToFile() throws Throwable {
        if (Build.VERSION.SDK_INT >= 29) {
            writeUsingSystemFilePicker();
        } else {
            writeUsingLegacyExternalStorage();
        }
    }

    private final void writeUsingSystemFilePicker() {
        Message messageObtainMessage;
        Handler handler = StageActivity.messageHandler;
        if (handler == null || (messageObtainMessage = handler.obtainMessage(0, CollectionsKt.arrayListOf(this))) == null) {
            return;
        }
        messageObtainMessage.sendToTarget();
    }

    private final void writeUsingLegacyExternalStorage() throws Throwable {
        Formula formula = this.formula;
        String strSanitizeFileName = Utils.sanitizeFileName(formula != null ? formula.interpretString(this.scope) : null);
        Intrinsics.checkNotNull(strSanitizeFileName);
        if (!StringsKt.endsWith$default(strSanitizeFileName, Constants.EMBROIDERY_FILE_EXTENSION, false, 2, (Object) null)) {
            strSanitizeFileName = strSanitizeFileName + Constants.EMBROIDERY_FILE_EXTENSION;
        }
        Intrinsics.checkNotNull(strSanitizeFileName);
        File fileCreateFile = createFile(strSanitizeFileName);
        if (fileCreateFile != null) {
            writeToFile(fileCreateFile);
        }
    }

    private final void showMessageEmbroideryIsMissing() {
        String string = this.context.getString(R.string.error_embroidery_data_not_found);
        Intrinsics.checkNotNullExpressionValue(string, "getString(...)");
        StageActivity.messageHandler.obtainMessage(3, new ArrayList(CollectionsKt.listOf(string))).sendToTarget();
    }

    public final File createFile(String fileName) {
        Intrinsics.checkNotNullParameter(fileName, "fileName");
        File file = new File(Constants.EXTERNAL_STORAGE_ROOT_EXPORT_DIRECTORY, fileName);
        if (file.exists() || file.createNewFile()) {
            return file;
        }
        return null;
    }

    public final void writeToFile(File file) throws Throwable {
        Throwable th;
        Message messageObtainMessage;
        Intrinsics.checkNotNullParameter(file, "file");
        Object string = this.context.getString(R.string.brick_write_variable_to_file_success, file);
        Intrinsics.checkNotNullExpressionValue(string, "getString(...)");
        try {
            try {
                writeEmbroideryDataToFile(file);
                ArrayList arrayList = new ArrayList(CollectionsKt.listOf(string));
                Handler handler = StageActivity.messageHandler;
                messageObtainMessage = handler.obtainMessage(3, arrayList);
                string = handler;
            } catch (IOException unused) {
                Object string2 = this.context.getString(R.string.error_embroidery_file_write);
                Intrinsics.checkNotNullExpressionValue(string2, "getString(...)");
                try {
                    Log.e(getClass().getSimpleName(), "Writing embroidery data to file failed");
                    Object arrayList2 = new ArrayList(CollectionsKt.listOf(string2));
                    messageObtainMessage = StageActivity.messageHandler.obtainMessage(3, arrayList2);
                    string = arrayList2;
                } catch (Throwable th2) {
                    string = string2;
                    th = th2;
                    StageActivity.messageHandler.obtainMessage(3, new ArrayList(CollectionsKt.listOf(string))).sendToTarget();
                    throw th;
                }
            }
            messageObtainMessage.sendToTarget();
        } catch (Throwable th3) {
            th = th3;
            StageActivity.messageHandler.obtainMessage(3, new ArrayList(CollectionsKt.listOf(string))).sendToTarget();
            throw th;
        }
    }

    private final void writeEmbroideryDataToUri(Uri uri) throws Throwable {
        Message messageObtainMessage;
        ContentResolver contentResolver = this.context.getContentResolver();
        String strResolveFileName = StorageOperations.resolveFileName(contentResolver, uri);
        if (strResolveFileName == null) {
            return;
        }
        String string = this.context.getString(R.string.brick_write_variable_to_file_success, strResolveFileName);
        Intrinsics.checkNotNullExpressionValue(string, "getString(...)");
        try {
            try {
                File file = new File(Constants.CACHE_DIRECTORY, strResolveFileName);
                if (!file.exists()) {
                    file.createNewFile();
                }
                writeEmbroideryDataToFile(file);
                StorageOperations.copyFileContentToUri(contentResolver, uri, file);
                ArrayList arrayList = new ArrayList();
                arrayList.add(string);
                messageObtainMessage = StageActivity.messageHandler.obtainMessage(3, arrayList);
            } catch (IOException unused) {
                String string2 = this.context.getString(R.string.error_embroidery_file_write);
                Intrinsics.checkNotNullExpressionValue(string2, "getString(...)");
                try {
                    Log.e(StageActivity.TAG, "Writing embroidery data to file failed");
                    ArrayList arrayList2 = new ArrayList();
                    arrayList2.add(string2);
                    messageObtainMessage = StageActivity.messageHandler.obtainMessage(3, arrayList2);
                } catch (Throwable th) {
                    string = string2;
                    th = th;
                    ArrayList arrayList3 = new ArrayList();
                    arrayList3.add(string);
                    StageActivity.messageHandler.obtainMessage(3, arrayList3).sendToTarget();
                    throw th;
                }
            }
            messageObtainMessage.sendToTarget();
        } catch (Throwable th2) {
            th = th2;
            ArrayList arrayList4 = new ArrayList();
            arrayList4.add(string);
            StageActivity.messageHandler.obtainMessage(3, arrayList4).sendToTarget();
            throw th;
        }
    }

    private final void writeEmbroideryDataToFile(File destinationFile) throws IOException {
        StageListener stageListener;
        EmbroideryPatternManager embroideryPatternManager;
        StageActivity stageActivity = StageActivity.activeStageActivity.get();
        new DSTFileGenerator((stageActivity == null || (stageListener = stageActivity.stageListener) == null || (embroideryPatternManager = stageListener.embroideryPatternManager) == null) ? null : embroideryPatternManager.getEmbroideryStream()).writeToDSTFile(destinationFile);
    }

    private final String getFileName() {
        Formula formula = this.formula;
        String strSanitizeFileName = Utils.sanitizeFileName(formula != null ? formula.interpretString(this.scope) : null);
        Intrinsics.checkNotNull(strSanitizeFileName);
        if (!StringsKt.endsWith$default(strSanitizeFileName, Constants.EMBROIDERY_FILE_EXTENSION, false, 2, (Object) null)) {
            strSanitizeFileName = strSanitizeFileName + Constants.EMBROIDERY_FILE_EXTENSION;
        }
        Intrinsics.checkNotNull(strSanitizeFileName);
        return strSanitizeFileName;
    }

    @Override // org.catrobat.catroid.stage.StageActivity.IntentListener
    /* JADX INFO: renamed from: getTargetIntent */
    public Intent get$intent() {
        String fileName = getFileName();
        String string = this.context.getString(R.string.brick_write_embroidery);
        Intrinsics.checkNotNullExpressionValue(string, "getString(...)");
        Intent intent = new Intent("android.intent.action.CREATE_DOCUMENT");
        intent.setType("*/*");
        intent.putExtra("android.intent.extra.TITLE", fileName);
        intent.putExtra("android.provider.extra.INITIAL_URI", Environment.DIRECTORY_DOWNLOADS);
        Intent intentCreateChooser = Intent.createChooser(intent, string);
        Intrinsics.checkNotNullExpressionValue(intentCreateChooser, "createChooser(...)");
        return intentCreateChooser;
    }

    @Override // org.catrobat.catroid.stage.StageActivity.IntentListener
    public boolean onIntentResult(int requestCode, int resultCode, Intent data) throws Throwable {
        Uri data2;
        if (resultCode != -1) {
            return false;
        }
        if (data == null || (data2 = data.getData()) == null) {
            return true;
        }
        writeEmbroideryDataToUri(data2);
        return true;
    }
}
