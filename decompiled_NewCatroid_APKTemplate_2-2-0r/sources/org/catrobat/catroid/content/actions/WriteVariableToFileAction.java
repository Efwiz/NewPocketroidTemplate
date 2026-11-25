package org.catrobat.catroid.content.actions;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import com.badlogic.gdx.scenes.scene2d.actions.TemporalAction;
import com.google.firebase.analytics.FirebaseAnalytics;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.io.CloseableKt;
import kotlin.io.FilesKt;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DoubleCompanionObject;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.Charsets;
import kotlin.text.Regex;
import kotlin.text.StringsKt;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.BuildersKt__Builders_commonKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.CoroutineScopeKt;
import kotlinx.coroutines.Dispatchers;
import kotlinx.coroutines.MainCoroutineDispatcher;
import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.CatroidApplication;
import org.catrobat.catroid.common.Constants;
import org.catrobat.catroid.content.Scope;
import org.catrobat.catroid.formulaeditor.Formula;
import org.catrobat.catroid.formulaeditor.UserVariable;
import org.catrobat.catroid.stage.StageActivity;
import org.catrobat.catroid.utils.Utils;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.codegen.coroutines.CoroutineCodegenUtilKt;
import org.jetbrains.kotlin.codegen.coroutines.CoroutineConstantsKt;
import org.jetbrains.kotlin.com.intellij.psi.PsiTreeChangeEvent;
import org.jetbrains.kotlin.name.SpecialNames;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/actions/WriteVariableToFileAction.class
  classes14.dex
 */
/* JADX INFO: compiled from: WriteVariableToFileAction.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000`\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\b\n\u0002\b\u0006\n\u0002\u0010\u0007\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u00002\u00020\u00012\u00020\u0002B\u0005¢\u0006\u0002\u0010\u0003J\b\u0010\u0016\u001a\u00020\u0017H\u0007J\u0012\u0010\u0018\u001a\u0004\u0018\u00010\u00192\u0006\u0010\u001a\u001a\u00020\u001bH\u0007J\b\u0010\u001c\u001a\u00020\u001bH\u0002J\b\u0010\u001d\u001a\u00020\u001eH\u0017J\"\u0010\u001f\u001a\u00020 2\u0006\u0010!\u001a\u00020\"2\u0006\u0010#\u001a\u00020\"2\b\u0010$\u001a\u0004\u0018\u00010\u001eH\u0016J\u0016\u0010%\u001a\u00020\u00172\u0006\u0010$\u001a\u00020\u001b2\u0006\u0010\u001a\u001a\u00020\u001bJ\u0010\u0010&\u001a\u00020\u00172\u0006\u0010\u001a\u001a\u00020\u001bH\u0002J\u0010\u0010'\u001a\u00020\u00172\u0006\u0010(\u001a\u00020)H\u0014J\b\u0010*\u001a\u00020\u0017H\u0002J\u0018\u0010+\u001a\u00020\u00172\u0006\u0010,\u001a\u00020\u00192\u0006\u0010-\u001a\u00020\u001bH\u0007J\u0018\u0010.\u001a\u00020 2\u0006\u0010,\u001a\u00020\u00192\u0006\u0010-\u001a\u00020\u001bH\u0007J\u0018\u0010/\u001a\u00020\u00172\u0006\u00100\u001a\u0002012\u0006\u0010-\u001a\u00020\u001bH\u0002J\b\u00102\u001a\u00020\u0017H\u0002J\b\u00103\u001a\u00020\u0017H\u0002R\u001c\u0010\u0004\u001a\u0004\u0018\u00010\u0005X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0006\u0010\u0007\"\u0004\b\b\u0010\tR\u001c\u0010\n\u001a\u0004\u0018\u00010\u000bX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\f\u0010\r\"\u0004\b\u000e\u0010\u000fR\u001c\u0010\u0010\u001a\u0004\u0018\u00010\u0011X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0012\u0010\u0013\"\u0004\b\u0014\u0010\u0015¨\u00064"}, d2 = {"Lorg/catrobat/catroid/content/actions/WriteVariableToFileAction;", "Lcom/badlogic/gdx/scenes/scene2d/actions/TemporalAction;", "Lorg/catrobat/catroid/stage/StageActivity$IntentListener;", "()V", "formula", "Lorg/catrobat/catroid/formulaeditor/Formula;", "getFormula", "()Lorg/catrobat/catroid/formulaeditor/Formula;", "setFormula", "(Lorg/catrobat/catroid/formulaeditor/Formula;)V", "scope", "Lorg/catrobat/catroid/content/Scope;", "getScope", "()Lorg/catrobat/catroid/content/Scope;", "setScope", "(Lorg/catrobat/catroid/content/Scope;)V", "userVariable", "Lorg/catrobat/catroid/formulaeditor/UserVariable;", "getUserVariable", "()Lorg/catrobat/catroid/formulaeditor/UserVariable;", "setUserVariable", "(Lorg/catrobat/catroid/formulaeditor/UserVariable;)V", "createAndWriteToFile", "", "createFile", "Ljava/io/File;", PsiTreeChangeEvent.PROP_FILE_NAME, "", "getFileName", "getTargetIntent", "Landroid/content/Intent;", "onIntentResult", "", "requestCode", "", "resultCode", "data", "saveToFile", "showSuccessMessage", ConfigConstants.CONFIG_KEY_UPDATE, "percent", "", "writeNew", "writeToFile", "file", FirebaseAnalytics.Param.CONTENT, "writeToFile2", "writeToUri", "uri", "Landroid/net/Uri;", "writeUsingLegacyExternalStorage", "writeUsingSystemFilePicker", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class WriteVariableToFileAction extends TemporalAction implements StageActivity.IntentListener {
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

    @Override // com.badlogic.gdx.scenes.scene2d.actions.TemporalAction
    protected void update(float percent) {
        if (this.userVariable == null || this.formula == null) {
            return;
        }
        createAndWriteToFile();
    }

    public final void createAndWriteToFile() {
        writeNew();
    }

    private final void writeUsingSystemFilePicker() {
        Message messageObtainMessage;
        Handler handler = StageActivity.messageHandler;
        if (handler == null || (messageObtainMessage = handler.obtainMessage(0, CollectionsKt.arrayListOf(this))) == null) {
            return;
        }
        messageObtainMessage.sendToTarget();
    }

    private final void writeUsingLegacyExternalStorage() {
        File fileCreateFile = createFile(getFileName());
        if (fileCreateFile != null) {
            UserVariable userVariable = this.userVariable;
            String strValueOf = String.valueOf(userVariable != null ? userVariable.getValue() : null);
            if (strValueOf == null) {
                strValueOf = "0";
            }
            writeToFile(fileCreateFile, strValueOf);
        }
    }

    public final void saveToFile(String data, String fileName) {
        Intrinsics.checkNotNullParameter(data, "data");
        Intrinsics.checkNotNullParameter(fileName, "fileName");
        if (!StringsKt.contains$default((CharSequence) fileName, (CharSequence) ".", false, 2, (Object) null)) {
            fileName = fileName + Constants.TEXT_FILE_EXTENSION;
        }
        File file = new File(System.getProperty("user.home") + "/Downloads", fileName);
        FilesKt.writeText$default(file, data, null, 2, null);
        String name2 = file.getName();
        Intrinsics.checkNotNullExpressionValue(name2, "getName(...)");
        showSuccessMessage(name2);
    }

    private final void writeNew() {
        Formula formula = this.formula;
        showSuccessMessage(String.valueOf(formula != null ? formula.interpretString(this.scope) : null));
        String fileName = getFileName();
        showSuccessMessage(fileName);
        BuildersKt__Builders_commonKt.launch$default(CoroutineScopeKt.CoroutineScope(Dispatchers.getIO()), null, null, new AnonymousClass1(fileName, null), 3, null);
    }

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/actions/WriteVariableToFileAction$writeNew$1.class
      classes14.dex
     */
    /* JADX INFO: renamed from: org.catrobat.catroid.content.actions.WriteVariableToFileAction$writeNew$1, reason: invalid class name */
    /* JADX INFO: compiled from: WriteVariableToFileAction.kt */
    @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\u008a@"}, d2 = {SpecialNames.ANONYMOUS_STRING, "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {1, 9, 0}, xi = 48)
    @DebugMetadata(c = "org.catrobat.catroid.content.actions.WriteVariableToFileAction$writeNew$1", f = "WriteVariableToFileAction.kt", i = {}, l = {106}, m = CoroutineCodegenUtilKt.INVOKE_SUSPEND_METHOD_NAME, n = {}, s = {})
    static final class AnonymousClass1 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        final /* synthetic */ String $fileName;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        AnonymousClass1(String str, Continuation<? super AnonymousClass1> continuation) {
            super(2, continuation);
            this.$fileName = str;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
            return WriteVariableToFileAction.this.new AnonymousClass1(this.$fileName, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
            return ((AnonymousClass1) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) throws Throwable {
            Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                File fileCreateFile = WriteVariableToFileAction.this.createFile(this.$fileName);
                if (fileCreateFile != null) {
                    WriteVariableToFileAction writeVariableToFileAction = WriteVariableToFileAction.this;
                    UserVariable userVariable = writeVariableToFileAction.getUserVariable();
                    boolean zWriteToFile2 = writeVariableToFileAction.writeToFile2(fileCreateFile, String.valueOf(userVariable != null ? userVariable.getValue() : null));
                    MainCoroutineDispatcher main = Dispatchers.getMain();
                    WriteVariableToFileAction$writeNew$1$1$1 writeVariableToFileAction$writeNew$1$1$1 = new WriteVariableToFileAction$writeNew$1$1$1(zWriteToFile2, null);
                    this.label = 1;
                    if (BuildersKt.withContext(main, writeVariableToFileAction$writeNew$1$1$1, this) == coroutine_suspended) {
                        return coroutine_suspended;
                    }
                }
            } else {
                if (i != 1) {
                    throw new IllegalStateException(CoroutineConstantsKt.ILLEGAL_STATE_ERROR_MESSAGE);
                }
                ResultKt.throwOnFailure(obj);
            }
            return Unit.INSTANCE;
        }
    }

    public final File createFile(String fileName) {
        Intrinsics.checkNotNullParameter(fileName, "fileName");
        showSuccessMessage("create: " + fileName);
        File file = new File(Constants.DOWNLOAD_DIRECTORY, fileName);
        if (file.exists() || file.createNewFile()) {
            return file;
        }
        return null;
    }

    public final void writeToFile(File file, String content) {
        Intrinsics.checkNotNullParameter(file, "file");
        Intrinsics.checkNotNullParameter(content, "content");
        showSuccessMessage("write1");
        try {
            FilesKt.writeText$default(file, content, null, 2, null);
            String name2 = file.getName();
            Intrinsics.checkNotNullExpressionValue(name2, "getName(...)");
            showSuccessMessage(name2);
        } catch (IOException unused) {
            Log.e(getClass().getSimpleName(), "Could not write variable value to storage.");
        }
    }

    public final boolean writeToFile2(File file, String content) {
        Intrinsics.checkNotNullParameter(file, "file");
        Intrinsics.checkNotNullParameter(content, "content");
        showSuccessMessage("write2");
        try {
            FilesKt.writeText$default(file, content, null, 2, null);
            return true;
        } catch (IOException unused) {
            Log.e(getClass().getSimpleName(), "Could not write variable value to storage.");
            return false;
        }
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
        showSuccessMessage("getIntent");
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
        showSuccessMessage("result");
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
