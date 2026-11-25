package org.catrobat.catroid.content.actions;

import com.badlogic.gdx.scenes.scene2d.actions.TemporalAction;
import java.io.File;
import kotlin.Metadata;
import org.catrobat.catroid.content.Project;
import org.catrobat.catroid.content.Scope;
import org.catrobat.catroid.formulaeditor.Formula;
import org.catrobat.catroid.utils.NewCatroidNotificationManager;
import org.eclipse.jgit.lib.ConfigConstants;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/actions/NotificationCreateAction.class
  classes14.dex
 */
/* JADX INFO: compiled from: NotificationCreateAction.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u00008\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0002\b\n\n\u0002\u0018\u0002\n\u0002\b\u000b\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u0007\n\u0000\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010&\u001a\u00020'2\u0006\u0010(\u001a\u00020)H\u0014R\u001c\u0010\u0003\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\bR\u001a\u0010\t\u001a\u00020\nX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000b\u0010\f\"\u0004\b\r\u0010\u000eR\u001a\u0010\u000f\u001a\u00020\u0010X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000f\u0010\u0011\"\u0004\b\u0012\u0010\u0013R\u001c\u0010\u0014\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0015\u0010\u0006\"\u0004\b\u0016\u0010\bR\u001c\u0010\u0017\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0018\u0010\u0006\"\u0004\b\u0019\u0010\bR\u001c\u0010\u001a\u001a\u0004\u0018\u00010\u001bX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001c\u0010\u001d\"\u0004\b\u001e\u0010\u001fR\u001c\u0010 \u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b!\u0010\u0006\"\u0004\b\"\u0010\bR\u001c\u0010#\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b$\u0010\u0006\"\u0004\b%\u0010\b¨\u0006*"}, d2 = {"Lorg/catrobat/catroid/content/actions/NotificationCreateAction;", "Lcom/badlogic/gdx/scenes/scene2d/actions/TemporalAction;", "()V", "channelName", "Lorg/catrobat/catroid/formulaeditor/Formula;", "getChannelName", "()Lorg/catrobat/catroid/formulaeditor/Formula;", "setChannelName", "(Lorg/catrobat/catroid/formulaeditor/Formula;)V", "importanceSelection", "", "getImportanceSelection", "()I", "setImportanceSelection", "(I)V", "isOngoing", "", "()Z", "setOngoing", "(Z)V", "largeIconFile", "getLargeIconFile", "setLargeIconFile", "notifId", "getNotifId", "setNotifId", "scope", "Lorg/catrobat/catroid/content/Scope;", "getScope", "()Lorg/catrobat/catroid/content/Scope;", "setScope", "(Lorg/catrobat/catroid/content/Scope;)V", "text", "getText", "setText", "title", "getTitle", "setTitle", ConfigConstants.CONFIG_KEY_UPDATE, "", "percent", "", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class NotificationCreateAction extends TemporalAction {
    private Formula channelName;
    private int importanceSelection = 1;
    private boolean isOngoing;
    private Formula largeIconFile;
    private Formula notifId;
    private Scope scope;
    private Formula text;
    private Formula title;

    public final Scope getScope() {
        return this.scope;
    }

    public final void setScope(Scope scope) {
        this.scope = scope;
    }

    public final Formula getNotifId() {
        return this.notifId;
    }

    public final void setNotifId(Formula formula) {
        this.notifId = formula;
    }

    public final Formula getChannelName() {
        return this.channelName;
    }

    public final void setChannelName(Formula formula) {
        this.channelName = formula;
    }

    public final Formula getTitle() {
        return this.title;
    }

    public final void setTitle(Formula formula) {
        this.title = formula;
    }

    public final Formula getText() {
        return this.text;
    }

    public final void setText(Formula formula) {
        this.text = formula;
    }

    public final Formula getLargeIconFile() {
        return this.largeIconFile;
    }

    public final void setLargeIconFile(Formula formula) {
        this.largeIconFile = formula;
    }

    public final int getImportanceSelection() {
        return this.importanceSelection;
    }

    public final void setImportanceSelection(int i) {
        this.importanceSelection = i;
    }

    /* JADX INFO: renamed from: isOngoing, reason: from getter */
    public final boolean getIsOngoing() {
        return this.isOngoing;
    }

    public final void setOngoing(boolean z) {
        this.isOngoing = z;
    }

    /* JADX WARN: Code duplicated, block: B:50:0x0084  */
    @Override // com.badlogic.gdx.scenes.scene2d.actions.TemporalAction
    protected void update(float percent) {
        String str;
        Project project;
        File file;
        Formula formula = this.notifId;
        String absolutePath = null;
        String strInterpretString = formula != null ? formula.interpretString(this.scope) : null;
        if (strInterpretString == null) {
            strInterpretString = "";
        }
        String strCleanStringId = NewCatroidNotificationManager.cleanStringId(strInterpretString);
        if (strCleanStringId.length() == 0) {
            return;
        }
        Formula formula2 = this.channelName;
        String strInterpretString2 = formula2 != null ? formula2.interpretString(this.scope) : null;
        if (strInterpretString2 == null) {
            strInterpretString2 = "App Notifications";
        }
        String str2 = strInterpretString2;
        Formula formula3 = this.title;
        String strInterpretString3 = formula3 != null ? formula3.interpretString(this.scope) : null;
        String str3 = strInterpretString3 == null ? "" : strInterpretString3;
        Formula formula4 = this.text;
        String strInterpretString4 = formula4 != null ? formula4.interpretString(this.scope) : null;
        String str4 = strInterpretString4 == null ? "" : strInterpretString4;
        Formula formula5 = this.largeIconFile;
        String strInterpretString5 = formula5 != null ? formula5.interpretString(this.scope) : null;
        if (strInterpretString5 == null) {
            strInterpretString5 = "";
        }
        if (strInterpretString5.length() <= 0) {
            str = "";
        } else {
            Scope scope = this.scope;
            if (scope != null && (project = scope.getProject()) != null && (file = project.getFile(strInterpretString5)) != null) {
                absolutePath = file.getAbsolutePath();
            }
            if (absolutePath == null) {
                str = "";
            } else {
                str = absolutePath;
            }
        }
        NewCatroidNotificationManager.INSTANCE.configureDraft(strCleanStringId, str2, str3, str4, str, this.importanceSelection, this.isOngoing);
    }
}
