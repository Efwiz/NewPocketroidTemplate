package org.catrobat.catroid.content.actions;

import com.badlogic.gdx.scenes.scene2d.actions.TemporalAction;
import java.io.File;
import java.io.IOException;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.sequences.SequencesKt;
import org.catrobat.catroid.CatroidApplication;
import org.catrobat.catroid.content.Project;
import org.catrobat.catroid.content.Scope;
import org.catrobat.catroid.formulaeditor.Formula;
import org.catrobat.catroid.formulaeditor.UserVariable;
import org.eclipse.jgit.lib.ConfigConstants;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/actions/GetZipFileNamesAction.class
  classes14.dex
 */
/* JADX INFO: compiled from: GetZipFileNamesAction.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u0007\n\u0000\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0015\u001a\u00020\u00162\u0006\u0010\u0017\u001a\u00020\u0018H\u0014R\u001c\u0010\u0003\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\bR\u001c\u0010\t\u001a\u0004\u0018\u00010\nX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000b\u0010\f\"\u0004\b\r\u0010\u000eR\u001c\u0010\u000f\u001a\u0004\u0018\u00010\u0010X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0011\u0010\u0012\"\u0004\b\u0013\u0010\u0014¨\u0006\u0019"}, d2 = {"Lorg/catrobat/catroid/content/actions/GetZipFileNamesAction;", "Lcom/badlogic/gdx/scenes/scene2d/actions/TemporalAction;", "()V", "scope", "Lorg/catrobat/catroid/content/Scope;", "getScope", "()Lorg/catrobat/catroid/content/Scope;", "setScope", "(Lorg/catrobat/catroid/content/Scope;)V", "userVariable", "Lorg/catrobat/catroid/formulaeditor/UserVariable;", "getUserVariable", "()Lorg/catrobat/catroid/formulaeditor/UserVariable;", "setUserVariable", "(Lorg/catrobat/catroid/formulaeditor/UserVariable;)V", "zipFileName", "Lorg/catrobat/catroid/formulaeditor/Formula;", "getZipFileName", "()Lorg/catrobat/catroid/formulaeditor/Formula;", "setZipFileName", "(Lorg/catrobat/catroid/formulaeditor/Formula;)V", ConfigConstants.CONFIG_KEY_UPDATE, "", "percent", "", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class GetZipFileNamesAction extends TemporalAction {
    private Scope scope;
    private UserVariable userVariable;
    private Formula zipFileName;

    public final Scope getScope() {
        return this.scope;
    }

    public final void setScope(Scope scope) {
        this.scope = scope;
    }

    public final Formula getZipFileName() {
        return this.zipFileName;
    }

    public final void setZipFileName(Formula formula) {
        this.zipFileName = formula;
    }

    public final UserVariable getUserVariable() {
        return this.userVariable;
    }

    public final void setUserVariable(UserVariable userVariable) {
        this.userVariable = userVariable;
    }

    @Override // com.badlogic.gdx.scenes.scene2d.actions.TemporalAction
    protected void update(float percent) {
        Scope scope = this.scope;
        Project project = scope != null ? scope.getProject() : null;
        UserVariable userVariable = this.userVariable;
        if (project == null || userVariable == null) {
            return;
        }
        CatroidApplication.getAppContext();
        Formula formula = this.zipFileName;
        String strInterpretString = formula != null ? formula.interpretString(this.scope) : null;
        String str = strInterpretString;
        if (str == null || str.length() == 0) {
            userVariable.setValue("Error: ZIP file name is empty");
            return;
        }
        try {
            File file = project.getFile(strInterpretString);
            if (!file.exists()) {
                userVariable.setValue("Error: File not found: " + strInterpretString);
                return;
            }
            ZipFile zipFile = new ZipFile(file);
            Enumeration<? extends ZipEntry> enumerationEntries = zipFile.entries();
            Intrinsics.checkNotNullExpressionValue(enumerationEntries, "entries(...)");
            String strJoinToString$default = SequencesKt.joinToString$default(SequencesKt.map(SequencesKt.asSequence(CollectionsKt.iterator(enumerationEntries)), new Function1<ZipEntry, String>() { // from class: org.catrobat.catroid.content.actions.GetZipFileNamesAction$update$fileNames$1
                @Override // kotlin.jvm.functions.Function1
                public final String invoke(ZipEntry zipEntry) {
                    return zipEntry.getName();
                }
            }), ",", null, null, 0, null, null, 62, null);
            zipFile.close();
            userVariable.setValue(strJoinToString$default);
        } catch (IOException e) {
            userVariable.setValue("Error: Failed to read ZIP file: " + e.getMessage());
        }
    }
}
