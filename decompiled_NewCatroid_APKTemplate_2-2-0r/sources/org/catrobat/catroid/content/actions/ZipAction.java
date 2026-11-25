package org.catrobat.catroid.content.actions;

import android.os.Environment;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import com.badlogic.gdx.scenes.scene2d.actions.TemporalAction;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.io.ByteStreamsKt;
import kotlin.io.CloseableKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import org.catrobat.catroid.CatroidApplication;
import org.catrobat.catroid.common.Constants;
import org.catrobat.catroid.content.Scope;
import org.catrobat.catroid.formulaeditor.Formula;
import org.catrobat.catroid.formulaeditor.InterpretationException;
import org.catrobat.catroid.stage.StageActivity;
import org.eclipse.jgit.lib.ConfigConstants;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/actions/ZipAction.class
  classes14.dex
 */
/* JADX INFO: compiled from: ZipAction.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u00006\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010 \n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u0007\n\u0002\b\u0004\b\u0016\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0014\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\u00140\u00132\u0006\u0010\u0015\u001a\u00020\u0014J\u0012\u0010\n\u001a\u0004\u0018\u00010\u00142\b\u0010\u0016\u001a\u0004\u0018\u00010\u0004J\u0010\u0010\u0017\u001a\u00020\u00182\u0006\u0010\u0019\u001a\u00020\u001aH\u0014J\u001c\u0010\u001b\u001a\u00020\u00182\f\u0010\u001c\u001a\b\u0012\u0004\u0012\u00020\u00140\u00132\u0006\u0010\u001d\u001a\u00020\u0014R\u001c\u0010\u0003\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\bR\u001c\u0010\t\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\n\u0010\u0006\"\u0004\b\u000b\u0010\bR\u001c\u0010\f\u001a\u0004\u0018\u00010\rX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000e\u0010\u000f\"\u0004\b\u0010\u0010\u0011¨\u0006\u001e"}, d2 = {"Lorg/catrobat/catroid/content/actions/ZipAction;", "Lcom/badlogic/gdx/scenes/scene2d/actions/TemporalAction;", "()V", "files", "Lorg/catrobat/catroid/formulaeditor/Formula;", "getFiles", "()Lorg/catrobat/catroid/formulaeditor/Formula;", "setFiles", "(Lorg/catrobat/catroid/formulaeditor/Formula;)V", "name", "getName", "setName", "scope", "Lorg/catrobat/catroid/content/Scope;", "getScope", "()Lorg/catrobat/catroid/content/Scope;", "setScope", "(Lorg/catrobat/catroid/content/Scope;)V", "getFilePaths", "", "", "input", "inputName", ConfigConstants.CONFIG_KEY_UPDATE, "", "percent", "", "zipFiles", "fileList", "zipFilePath", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public class ZipAction extends TemporalAction {
    private Formula files;
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

    public final Formula getFiles() {
        return this.files;
    }

    public final void setFiles(Formula formula) {
        this.files = formula;
    }

    @Override // com.badlogic.gdx.scenes.scene2d.actions.TemporalAction
    protected void update(float percent) throws InterpretationException {
        final StageActivity stageActivity = StageActivity.activeStageActivity.get();
        if (stageActivity != null) {
            stageActivity.runOnUiThread(new Runnable() { // from class: org.catrobat.catroid.content.actions.ZipAction$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    ZipAction.update$lambda$0(stageActivity);
                }
            });
        }
        String name2 = getName(this.name);
        if (name2 == null) {
            name2 = "myZip.zip";
        }
        Formula formula = this.files;
        String strInterpretString = formula != null ? formula.interpretString(this.scope) : null;
        if (strInterpretString == null) {
            strInterpretString = "";
        }
        List<String> filePaths = getFilePaths(strInterpretString);
        String absolutePath = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), name2).getAbsolutePath();
        Intrinsics.checkNotNull(absolutePath);
        zipFiles(filePaths, absolutePath);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void update$lambda$0(StageActivity stageActivity) {
        if (ContextCompat.checkSelfPermission(CatroidApplication.getAppContext(), "android.permission.WRITE_EXTERNAL_STORAGE") != 0) {
            ActivityCompat.requestPermissions(stageActivity, new String[]{"android.permission.WRITE_EXTERNAL_STORAGE"}, 1);
        }
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
        return (iLastIndexOf$default > 0 || iLastIndexOf$default < strInterpretString.length() + (-1)) ? strInterpretString : strInterpretString + Constants.ZIP_EXTENSION;
    }

    public final List<String> getFilePaths(String input) {
        Intrinsics.checkNotNullParameter(input, "input");
        File externalStoragePublicDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
        List listSplit$default = StringsKt.split$default((CharSequence) input, new String[]{","}, false, 0, 6, (Object) null);
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(listSplit$default, 10));
        Iterator it = listSplit$default.iterator();
        while (it.hasNext()) {
            arrayList.add(StringsKt.trim((CharSequence) it.next()).toString());
        }
        ArrayList arrayList2 = new ArrayList();
        for (Object obj : arrayList) {
            if (((String) obj).length() > 0) {
                arrayList2.add(obj);
            }
        }
        ArrayList arrayList3 = arrayList2;
        ArrayList arrayList4 = new ArrayList(CollectionsKt.collectionSizeOrDefault(arrayList3, 10));
        Iterator it2 = arrayList3.iterator();
        while (it2.hasNext()) {
            arrayList4.add(new File(externalStoragePublicDirectory, (String) it2.next()).getAbsolutePath());
        }
        return arrayList4;
    }

    public final void zipFiles(List<String> fileList, String zipFilePath) {
        Intrinsics.checkNotNullParameter(fileList, "fileList");
        Intrinsics.checkNotNullParameter(zipFilePath, "zipFilePath");
        try {
            ZipOutputStream zipOutputStream = new ZipOutputStream(new FileOutputStream(zipFilePath));
            try {
                ZipOutputStream zipOutputStream2 = zipOutputStream;
                Iterator<String> it = fileList.iterator();
                while (it.hasNext()) {
                    File file = new File(it.next());
                    zipOutputStream2.putNextEntry(new ZipEntry(file.getName()));
                    FileInputStream fileInputStream = new FileInputStream(file);
                    try {
                        ByteStreamsKt.copyTo$default(fileInputStream, zipOutputStream2, 0, 2, null);
                        CloseableKt.closeFinally(fileInputStream, null);
                        zipOutputStream2.closeEntry();
                    } catch (Throwable th) {
                        try {
                            throw th;
                        } catch (Throwable th2) {
                            CloseableKt.closeFinally(fileInputStream, th);
                            throw th2;
                        }
                    }
                }
                Unit unit = Unit.INSTANCE;
                CloseableKt.closeFinally(zipOutputStream, null);
            } catch (Throwable th3) {
                try {
                    throw th3;
                } catch (Throwable th4) {
                    CloseableKt.closeFinally(zipOutputStream, th3);
                    throw th4;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
