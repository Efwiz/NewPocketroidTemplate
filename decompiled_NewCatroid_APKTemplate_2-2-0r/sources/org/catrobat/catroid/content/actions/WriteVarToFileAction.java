package org.catrobat.catroid.content.actions;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import com.badlogic.gdx.scenes.scene2d.actions.TemporalAction;
import com.google.firebase.analytics.FirebaseAnalytics;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.io.CloseableKt;
import kotlin.io.FilesKt;
import kotlin.jvm.internal.DoubleCompanionObject;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.Charsets;
import kotlin.text.StringsKt;
import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.CatroidApplication;
import org.catrobat.catroid.common.Constants;
import org.catrobat.catroid.content.Scope;
import org.catrobat.catroid.formulaeditor.Formula;
import org.catrobat.catroid.formulaeditor.UserVariable;
import org.catrobat.catroid.stage.StageActivity;
import org.catrobat.catroid.utils.Utils;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.com.intellij.psi.PsiTreeChangeEvent;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/actions/WriteVarToFileAction.class
  classes14.dex
 */
/* JADX INFO: compiled from: WriteVarToFIleAction.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000d\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0007\n\u0002\b\u0003\u0018\u00002\u00020\u00012\u00020\u0002B\u0005¢\u0006\u0002\u0010\u0003J\b\u0010\u0016\u001a\u00020\u0017H\u0002J\b\u0010\u0018\u001a\u00020\u0019H\u0017J\"\u0010\u001a\u001a\u00020\u001b2\u0006\u0010\u001c\u001a\u00020\u001d2\u0006\u0010\u001e\u001a\u00020\u001d2\b\u0010\u001f\u001a\u0004\u0018\u00010\u0019H\u0016J\u000e\u0010 \u001a\u00020!2\u0006\u0010\"\u001a\u00020#J \u0010$\u001a\u0004\u0018\u00010%2\u0006\u0010&\u001a\u00020'2\u0006\u0010(\u001a\u00020\u00172\u0006\u0010)\u001a\u00020\u0017J\u0010\u0010*\u001a\u00020!2\u0006\u0010(\u001a\u00020\u0017H\u0002J\u0010\u0010+\u001a\u00020!2\u0006\u0010,\u001a\u00020-H\u0014J\u0018\u0010.\u001a\u00020!2\u0006\u0010/\u001a\u00020%2\u0006\u0010)\u001a\u00020\u0017H\u0002R\u001c\u0010\u0004\u001a\u0004\u0018\u00010\u0005X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0006\u0010\u0007\"\u0004\b\b\u0010\tR\u001c\u0010\n\u001a\u0004\u0018\u00010\u000bX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\f\u0010\r\"\u0004\b\u000e\u0010\u000fR\u001c\u0010\u0010\u001a\u0004\u0018\u00010\u0011X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0012\u0010\u0013\"\u0004\b\u0014\u0010\u0015¨\u00060"}, d2 = {"Lorg/catrobat/catroid/content/actions/WriteVarToFileAction;", "Lcom/badlogic/gdx/scenes/scene2d/actions/TemporalAction;", "Lorg/catrobat/catroid/stage/StageActivity$IntentListener;", "()V", "formula", "Lorg/catrobat/catroid/formulaeditor/Formula;", "getFormula", "()Lorg/catrobat/catroid/formulaeditor/Formula;", "setFormula", "(Lorg/catrobat/catroid/formulaeditor/Formula;)V", "scope", "Lorg/catrobat/catroid/content/Scope;", "getScope", "()Lorg/catrobat/catroid/content/Scope;", "setScope", "(Lorg/catrobat/catroid/content/Scope;)V", "userVariable", "Lorg/catrobat/catroid/formulaeditor/UserVariable;", "getUserVariable", "()Lorg/catrobat/catroid/formulaeditor/UserVariable;", "setUserVariable", "(Lorg/catrobat/catroid/formulaeditor/UserVariable;)V", "getFileName", "", "getTargetIntent", "Landroid/content/Intent;", "onIntentResult", "", "requestCode", "", "resultCode", "data", "request", "", "activity", "Landroid/app/Activity;", "saveOrOverwriteInDownloads", "Landroid/net/Uri;", "context", "Landroid/content/Context;", PsiTreeChangeEvent.PROP_FILE_NAME, FirebaseAnalytics.Param.CONTENT, "showSuccessMessage", ConfigConstants.CONFIG_KEY_UPDATE, "percent", "", "writeToUri", "uri", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class WriteVarToFileAction extends TemporalAction implements StageActivity.IntentListener {
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

    public final void request(Activity activity) {
        Intrinsics.checkNotNullParameter(activity, "activity");
        ActivityCompat.requestPermissions(activity, new String[]{"android.permission.WRITE_EXTERNAL_STORAGE"}, 1001);
    }

    @Override // com.badlogic.gdx.scenes.scene2d.actions.TemporalAction
    protected void update(float percent) {
        final StageActivity stageActivity;
        if (ContextCompat.checkSelfPermission(CatroidApplication.getAppContext(), "android.permission.WRITE_EXTERNAL_STORAGE") != 0 && (stageActivity = StageActivity.activeStageActivity.get()) != null) {
            stageActivity.runOnUiThread(new Runnable() { // from class: org.catrobat.catroid.content.actions.WriteVarToFileAction$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    WriteVarToFileAction.update$lambda$0(this.f$0, stageActivity);
                }
            });
        }
        if (this.userVariable == null || this.formula == null) {
            return;
        }
        Context appContext = CatroidApplication.getAppContext();
        Intrinsics.checkNotNullExpressionValue(appContext, "getAppContext(...)");
        String fileName = getFileName();
        UserVariable userVariable = this.userVariable;
        Intrinsics.checkNotNull(userVariable);
        saveOrOverwriteInDownloads(appContext, fileName, userVariable.getValue().toString());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void update$lambda$0(WriteVarToFileAction this$0, StageActivity stageActivity) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.request(stageActivity);
    }

    public final Uri saveOrOverwriteInDownloads(Context context, String fileName, String content) {
        Uri uriWithAppendedId;
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(fileName, "fileName");
        Intrinsics.checkNotNullParameter(content, "content");
        if (Build.VERSION.SDK_INT >= 29) {
            ContentResolver contentResolver = context.getContentResolver();
            Cursor cursorQuery = contentResolver.query(MediaStore.Downloads.getContentUri("external_primary"), new String[]{"_id"}, "relative_path = ? AND _display_name = ?", new String[]{Environment.DIRECTORY_DOWNLOADS + "/", fileName}, null);
            if (cursorQuery != null) {
                Cursor cursor = cursorQuery;
                try {
                    Cursor cursor2 = cursor;
                    uriWithAppendedId = cursor2.moveToFirst() ? ContentUris.withAppendedId(MediaStore.Downloads.getContentUri("external_primary"), cursor2.getLong(cursor2.getColumnIndexOrThrow("_id"))) : null;
                    Unit unit = Unit.INSTANCE;
                    CloseableKt.closeFinally(cursor, null);
                } catch (Throwable th) {
                    try {
                        throw th;
                    } catch (Throwable th2) {
                        CloseableKt.closeFinally(cursor, th);
                        throw th2;
                    }
                }
            } else {
                uriWithAppendedId = null;
            }
            try {
                if (uriWithAppendedId != null) {
                    OutputStream outputStreamOpenOutputStream = contentResolver.openOutputStream(uriWithAppendedId, "w");
                    if (outputStreamOpenOutputStream != null) {
                        OutputStream outputStream = outputStreamOpenOutputStream;
                        try {
                            byte[] bytes = content.getBytes(Charsets.UTF_8);
                            Intrinsics.checkNotNullExpressionValue(bytes, "getBytes(...)");
                            outputStream.write(bytes);
                            Unit unit2 = Unit.INSTANCE;
                            CloseableKt.closeFinally(outputStream, null);
                        } catch (Throwable th3) {
                            try {
                                throw th3;
                            } catch (Throwable th4) {
                                CloseableKt.closeFinally(outputStream, th3);
                                throw th4;
                            }
                        }
                    }
                    System.out.println((Object) ("Файл успешно перезаписан: " + uriWithAppendedId));
                    return uriWithAppendedId;
                }
                ContentValues contentValues = new ContentValues();
                contentValues.put("_display_name", fileName);
                contentValues.put("mime_type", "*/*");
                contentValues.put("relative_path", Environment.DIRECTORY_DOWNLOADS);
                Uri uriInsert = contentResolver.insert(MediaStore.Downloads.getContentUri("external_primary"), contentValues);
                if (uriInsert != null) {
                    OutputStream outputStreamOpenOutputStream2 = contentResolver.openOutputStream(uriInsert);
                    if (outputStreamOpenOutputStream2 != null) {
                        OutputStream outputStream2 = outputStreamOpenOutputStream2;
                        try {
                            byte[] bytes2 = content.getBytes(Charsets.UTF_8);
                            Intrinsics.checkNotNullExpressionValue(bytes2, "getBytes(...)");
                            outputStream2.write(bytes2);
                            Unit unit3 = Unit.INSTANCE;
                            CloseableKt.closeFinally(outputStream2, null);
                        } catch (Throwable th5) {
                            try {
                                throw th5;
                            } catch (Throwable th6) {
                                CloseableKt.closeFinally(outputStream2, th5);
                                throw th6;
                            }
                        }
                    }
                    System.out.println((Object) ("Новый файл успешно создан: " + uriInsert));
                }
                return uriInsert;
            } catch (Exception e) {
                System.out.println((Object) ("Ошибка при сохранении/перезаписи файла: " + e.getMessage()));
                return null;
            }
            System.out.println((Object) ("Ошибка при сохранении/перезаписи файла: " + e.getMessage()));
            return null;
        }
        File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), fileName);
        try {
            FilesKt.writeText(file, content, Charsets.UTF_8);
            System.out.println((Object) ("Файл успешно сохранен/перезаписан: " + file.getAbsolutePath()));
            return Uri.fromFile(file);
        } catch (Exception e2) {
            System.out.println((Object) ("Ошибка при сохранении/перезаписи файла: " + e2.getMessage()));
            return null;
        }
    }

    private final String getFileName() {
        Formula formula = this.formula;
        String strSanitizeFileName = Utils.sanitizeFileName(formula != null ? formula.interpretString(this.scope) : null);
        Intrinsics.checkNotNull(strSanitizeFileName);
        if (!StringsKt.contains$default((CharSequence) strSanitizeFileName, (CharSequence) ".", false, 2, (Object) null)) {
            strSanitizeFileName = strSanitizeFileName + Constants.TEXT_FILE_EXTENSION;
        }
        Intrinsics.checkNotNull(strSanitizeFileName);
        return strSanitizeFileName;
    }

    private final void showSuccessMessage(String fileName) {
        String string = CatroidApplication.getAppContext().getString(R.string.brick_write_variable_to_file_success, fileName);
        Intrinsics.checkNotNullExpressionValue(string, "getString(...)");
        StageActivity.messageHandler.obtainMessage(3, new ArrayList(CollectionsKt.listOf(string))).sendToTarget();
    }

    private final void writeToUri(Uri uri, String content) {
        try {
            Context appContext = CatroidApplication.getAppContext();
            Intrinsics.checkNotNullExpressionValue(appContext, "getAppContext(...)");
            OutputStream outputStreamOpenOutputStream = appContext.getContentResolver().openOutputStream(uri);
            try {
                OutputStream outputStream = outputStreamOpenOutputStream;
                if (outputStream != null) {
                    byte[] bytes = content.getBytes(Charsets.UTF_8);
                    Intrinsics.checkNotNullExpressionValue(bytes, "getBytes(...)");
                    outputStream.write(bytes);
                    Unit unit = Unit.INSTANCE;
                }
                CloseableKt.closeFinally(outputStreamOpenOutputStream, null);
                showSuccessMessage(getFileName());
            } catch (Throwable th) {
                try {
                    throw th;
                } catch (Throwable th2) {
                    CloseableKt.closeFinally(outputStreamOpenOutputStream, th);
                    throw th2;
                }
            }
        } catch (IOException unused) {
            Log.e(getClass().getSimpleName(), "Could not write variable value to storage.");
        }
    }

    @Override // org.catrobat.catroid.stage.StageActivity.IntentListener
    /* JADX INFO: renamed from: getTargetIntent */
    public Intent get$intent() {
        String string;
        String fileName = getFileName();
        StageActivity stageActivity = StageActivity.activeStageActivity.get();
        Context context = stageActivity != null ? stageActivity.getContext() : null;
        if (context == null || (string = context.getString(R.string.brick_write_variable_to_file_top)) == null) {
            string = "";
        }
        Intent intent = new Intent("android.intent.action.CREATE_DOCUMENT");
        intent.setType("*/*");
        intent.putExtra("android.intent.extra.TITLE", fileName);
        intent.putExtra("android.provider.extra.INITIAL_URI", Environment.DIRECTORY_DOWNLOADS);
        Intent intentCreateChooser = Intent.createChooser(intent, string);
        Intrinsics.checkNotNullExpressionValue(intentCreateChooser, "createChooser(...)");
        return intentCreateChooser;
    }

    @Override // org.catrobat.catroid.stage.StageActivity.IntentListener
    public boolean onIntentResult(int requestCode, int resultCode, Intent data) {
        Uri data2;
        String string;
        if (resultCode != -1) {
            return false;
        }
        if (data == null || (data2 = data.getData()) == null) {
            return true;
        }
        UserVariable userVariable = this.userVariable;
        Object value = userVariable != null ? userVariable.getValue() : null;
        if (value == null) {
            value = 0;
        } else {
            Intrinsics.checkNotNull(value);
        }
        if (Intrinsics.areEqual(value, DoubleCompanionObject.INSTANCE)) {
            string = new BigDecimal(String.valueOf(((Double) value).doubleValue())).toPlainString();
            Intrinsics.checkNotNullExpressionValue(string, "toPlainString(...)");
        } else {
            string = value.toString();
        }
        writeToUri(data2, string);
        return true;
    }
}
