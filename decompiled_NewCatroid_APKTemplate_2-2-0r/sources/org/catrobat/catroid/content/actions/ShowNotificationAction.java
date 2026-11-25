package org.catrobat.catroid.content.actions;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import androidx.core.app.NotificationCompat;
import androidx.preference.PreferenceManager;
import com.badlogic.gdx.scenes.scene2d.actions.TemporalAction;
import java.io.File;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.catrobat.catroid.CatroidApplication;
import org.catrobat.catroid.content.Project;
import org.catrobat.catroid.content.Scope;
import org.catrobat.catroid.formulaeditor.Formula;
import org.catrobat.catroid.stage.StageActivity;
import org.catrobat.catroid.stage.StageWorkspaceActivity;
import org.catrobat.catroid.utils.NewCatroidNotificationManager;
import org.eclipse.jgit.lib.ConfigConstants;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/actions/ShowNotificationAction.class
  classes14.dex
 */
/* JADX INFO: compiled from: ShowNotificationAction.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000:\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0002\b\n\n\u0002\u0018\u0002\n\u0002\b\u000b\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0010\u0007\n\u0000\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\b\u0010&\u001a\u00020'H\u0016J\b\u0010(\u001a\u00020'H\u0016J\u0010\u0010)\u001a\u00020'2\u0006\u0010*\u001a\u00020+H\u0014R\u001c\u0010\u0003\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\bR\u001a\u0010\t\u001a\u00020\nX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000b\u0010\f\"\u0004\b\r\u0010\u000eR\u001a\u0010\u000f\u001a\u00020\u0010X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000f\u0010\u0011\"\u0004\b\u0012\u0010\u0013R\u001c\u0010\u0014\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0015\u0010\u0006\"\u0004\b\u0016\u0010\bR\u001c\u0010\u0017\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0018\u0010\u0006\"\u0004\b\u0019\u0010\bR\u001c\u0010\u001a\u001a\u0004\u0018\u00010\u001bX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001c\u0010\u001d\"\u0004\b\u001e\u0010\u001fR\u001c\u0010 \u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b!\u0010\u0006\"\u0004\b\"\u0010\bR\u001c\u0010#\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b$\u0010\u0006\"\u0004\b%\u0010\b¨\u0006,"}, d2 = {"Lorg/catrobat/catroid/content/actions/ShowNotificationAction;", "Lcom/badlogic/gdx/scenes/scene2d/actions/TemporalAction;", "()V", "channelName", "Lorg/catrobat/catroid/formulaeditor/Formula;", "getChannelName", "()Lorg/catrobat/catroid/formulaeditor/Formula;", "setChannelName", "(Lorg/catrobat/catroid/formulaeditor/Formula;)V", "importanceSelection", "", "getImportanceSelection", "()I", "setImportanceSelection", "(I)V", "isOngoing", "", "()Z", "setOngoing", "(Z)V", "largeIconFile", "getLargeIconFile", "setLargeIconFile", "notificationId", "getNotificationId", "setNotificationId", "scope", "Lorg/catrobat/catroid/content/Scope;", "getScope", "()Lorg/catrobat/catroid/content/Scope;", "setScope", "(Lorg/catrobat/catroid/content/Scope;)V", "text", "getText", "setText", "title", "getTitle", "setTitle", "reset", "", "restart", ConfigConstants.CONFIG_KEY_UPDATE, "percent", "", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ShowNotificationAction extends TemporalAction {
    private Formula channelName;
    private int importanceSelection = 1;
    private boolean isOngoing;
    private Formula largeIconFile;
    private Formula notificationId;
    private Scope scope;
    private Formula text;
    private Formula title;

    public final Scope getScope() {
        return this.scope;
    }

    public final void setScope(Scope scope) {
        this.scope = scope;
    }

    public final Formula getNotificationId() {
        return this.notificationId;
    }

    public final void setNotificationId(Formula formula) {
        this.notificationId = formula;
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

    @Override // com.badlogic.gdx.scenes.scene2d.actions.TemporalAction
    protected void update(float percent) {
        int i;
        Class cls;
        Project project;
        Project project2;
        File directory;
        Context appContext = CatroidApplication.getAppContext();
        if (appContext == null) {
            return;
        }
        Formula formula = this.notificationId;
        File file = null;
        String strInterpretString = formula != null ? formula.interpretString(this.scope) : null;
        if (strInterpretString == null) {
            strInterpretString = "";
        }
        String strCleanStringId = NewCatroidNotificationManager.cleanStringId(strInterpretString);
        int iHashCode = strCleanStringId.hashCode();
        Formula formula2 = this.channelName;
        String strInterpretString2 = formula2 != null ? formula2.interpretString(this.scope) : null;
        if (strInterpretString2 == null) {
            strInterpretString2 = "App Notifications";
        }
        String str = "newcatroid_chan_" + strInterpretString2.hashCode();
        Formula formula3 = this.title;
        String strInterpretString3 = formula3 != null ? formula3.interpretString(this.scope) : null;
        if (strInterpretString3 == null) {
            strInterpretString3 = "";
        }
        Formula formula4 = this.text;
        String strInterpretString4 = formula4 != null ? formula4.interpretString(this.scope) : null;
        if (strInterpretString4 == null) {
            strInterpretString4 = "";
        }
        Formula formula5 = this.largeIconFile;
        String strInterpretString5 = formula5 != null ? formula5.interpretString(this.scope) : null;
        String str2 = strInterpretString5 != null ? strInterpretString5 : "";
        Object systemService = appContext.getSystemService("notification");
        Intrinsics.checkNotNull(systemService, "null cannot be cast to non-null type android.app.NotificationManager");
        NotificationManager notificationManager = (NotificationManager) systemService;
        int i2 = this.importanceSelection;
        if (i2 != 0) {
            i = i2 != 2 ? 3 : 4;
        } else {
            i = 2;
        }
        NotificationChannel notificationChannel = new NotificationChannel(str, strInterpretString2, i);
        notificationChannel.setDescription("Notifications triggered from NewCatroid");
        notificationManager.createNotificationChannel(notificationChannel);
        int i3 = 0;
        if (PreferenceManager.getDefaultSharedPreferences(appContext).getBoolean("pref_workspace_stage", false)) {
            cls = StageWorkspaceActivity.class;
        } else {
            cls = StageActivity.class;
        }
        Intent intent = new Intent(appContext, (Class<?>) cls);
        Scope scope = this.scope;
        intent.putExtra(StageActivity.EXTRA_PROJECT_PATH, (scope == null || (project2 = scope.getProject()) == null || (directory = project2.getDirectory()) == null) ? null : directory.getAbsolutePath());
        intent.putExtra("NOTIFICATION_CLICKED_ID", strCleanStringId);
        intent.setFlags(872415232);
        PendingIntent activity = PendingIntent.getActivity(appContext, iHashCode, intent, 67108864 | 134217728);
        NotificationCompat.Builder smallIcon = new NotificationCompat.Builder(appContext, str).setContentTitle(strInterpretString3).setContentText(strInterpretString4).setAutoCancel(!this.isOngoing).setOngoing(this.isOngoing).setSmallIcon(appContext.getApplicationInfo().icon);
        Intrinsics.checkNotNullExpressionValue(smallIcon, "setSmallIcon(...)");
        if (activity != null) {
            smallIcon.setContentIntent(activity);
        }
        int i4 = this.importanceSelection;
        if (i4 == 0) {
            i3 = -1;
        } else if (i4 == 2) {
            i3 = 1;
        }
        smallIcon.setPriority(i3);
        if (str2.length() > 0) {
            Scope scope2 = this.scope;
            if (scope2 != null && (project = scope2.getProject()) != null) {
                file = project.getFile(str2);
            }
            if (file != null && file.exists()) {
                try {
                    Bitmap bitmapDecodeFile = BitmapFactory.decodeFile(file.getAbsolutePath());
                    if (bitmapDecodeFile != null) {
                        smallIcon.setLargeIcon(bitmapDecodeFile);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        try {
            notificationManager.notify(iHashCode, smallIcon.build());
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    @Override // com.badlogic.gdx.scenes.scene2d.actions.TemporalAction, com.badlogic.gdx.scenes.scene2d.Action
    public void restart() {
        super.restart();
    }

    @Override // com.badlogic.gdx.scenes.scene2d.actions.TemporalAction, com.badlogic.gdx.scenes.scene2d.Action, com.badlogic.gdx.utils.Pool.Poolable
    public void reset() {
        super.reset();
        this.scope = null;
        this.notificationId = null;
        this.channelName = null;
        this.title = null;
        this.text = null;
        this.largeIconFile = null;
        this.importanceSelection = 1;
        this.isOngoing = false;
    }
}
