package org.catrobat.catroid.content.actions;

import com.badlogic.gdx.scenes.scene2d.actions.TemporalAction;
import com.huawei.hms.framework.common.hianalytics.CrashHianalyticsData;
import kotlin.Metadata;
import org.catrobat.catroid.content.Scope;
import org.catrobat.catroid.formulaeditor.Formula;
import org.catrobat.catroid.utils.NewCatroidMqttManager;
import org.eclipse.jgit.lib.ConfigConstants;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/actions/MqttPublishAction.class
  classes14.dex
 */
/* JADX INFO: compiled from: MqttPublishAction.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u000e\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u0007\n\u0000\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0018\u001a\u00020\u00192\u0006\u0010\u001a\u001a\u00020\u001bH\u0014R\u001c\u0010\u0003\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\bR\u001c\u0010\t\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\n\u0010\u0006\"\u0004\b\u000b\u0010\bR\u001c\u0010\f\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\r\u0010\u0006\"\u0004\b\u000e\u0010\bR\u001c\u0010\u000f\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0010\u0010\u0006\"\u0004\b\u0011\u0010\bR\u001c\u0010\u0012\u001a\u0004\u0018\u00010\u0013X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0014\u0010\u0015\"\u0004\b\u0016\u0010\u0017¨\u0006\u001c"}, d2 = {"Lorg/catrobat/catroid/content/actions/MqttPublishAction;", "Lcom/badlogic/gdx/scenes/scene2d/actions/TemporalAction;", "()V", "clientId", "Lorg/catrobat/catroid/formulaeditor/Formula;", "getClientId", "()Lorg/catrobat/catroid/formulaeditor/Formula;", "setClientId", "(Lorg/catrobat/catroid/formulaeditor/Formula;)V", CrashHianalyticsData.MESSAGE, "getMessage", "setMessage", "roomId", "getRoomId", "setRoomId", "salt", "getSalt", "setSalt", "scope", "Lorg/catrobat/catroid/content/Scope;", "getScope", "()Lorg/catrobat/catroid/content/Scope;", "setScope", "(Lorg/catrobat/catroid/content/Scope;)V", ConfigConstants.CONFIG_KEY_UPDATE, "", "percent", "", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class MqttPublishAction extends TemporalAction {
    private Formula clientId;
    private Formula message;
    private Formula roomId;
    private Formula salt;
    private Scope scope;

    public final Scope getScope() {
        return this.scope;
    }

    public final void setScope(Scope scope) {
        this.scope = scope;
    }

    public final Formula getClientId() {
        return this.clientId;
    }

    public final void setClientId(Formula formula) {
        this.clientId = formula;
    }

    public final Formula getRoomId() {
        return this.roomId;
    }

    public final void setRoomId(Formula formula) {
        this.roomId = formula;
    }

    public final Formula getSalt() {
        return this.salt;
    }

    public final void setSalt(Formula formula) {
        this.salt = formula;
    }

    public final Formula getMessage() {
        return this.message;
    }

    public final void setMessage(Formula formula) {
        this.message = formula;
    }

    @Override // com.badlogic.gdx.scenes.scene2d.actions.TemporalAction
    protected void update(float percent) {
        Formula formula = this.clientId;
        String strInterpretString = formula != null ? formula.interpretString(this.scope) : null;
        if (strInterpretString == null) {
            strInterpretString = "lobby1";
        }
        Formula formula2 = this.roomId;
        String strInterpretString2 = formula2 != null ? formula2.interpretString(this.scope) : null;
        if (strInterpretString2 == null) {
            strInterpretString2 = "room1";
        }
        Formula formula3 = this.salt;
        String strInterpretString3 = formula3 != null ? formula3.interpretString(this.scope) : null;
        if (strInterpretString3 == null) {
            strInterpretString3 = "my_project";
        }
        Formula formula4 = this.message;
        String strInterpretString4 = formula4 != null ? formula4.interpretString(this.scope) : null;
        if (strInterpretString4 == null) {
            strInterpretString4 = "";
        }
        if (strInterpretString.length() <= 0 || strInterpretString2.length() <= 0) {
            return;
        }
        NewCatroidMqttManager.INSTANCE.publishMessage(strInterpretString, strInterpretString2, strInterpretString3, strInterpretString4);
    }
}
