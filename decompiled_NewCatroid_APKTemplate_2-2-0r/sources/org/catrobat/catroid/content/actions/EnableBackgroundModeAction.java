package org.catrobat.catroid.content.actions;

import android.content.Context;
import android.content.Intent;
import com.badlogic.gdx.scenes.scene2d.actions.TemporalAction;
import kotlin.Metadata;
import org.catrobat.catroid.CatroidApplication;
import org.catrobat.catroid.common.NewCatroidBackgroundService;
import org.catrobat.catroid.content.Scope;
import org.catrobat.catroid.formulaeditor.Formula;
import org.catrobat.catroid.stage.StageActivity;
import org.catrobat.catroid.stage.StageListener;
import org.catrobat.catroid.ui.SpriteActivity;
import org.eclipse.jgit.lib.ConfigConstants;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/actions/EnableBackgroundModeAction.class
  classes14.dex
 */
/* JADX INFO: compiled from: EnableBackgroundModeAction.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u0007\n\u0000\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\u0015H\u0014R\u001c\u0010\u0003\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\bR\u001c\u0010\t\u001a\u0004\u0018\u00010\nX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000b\u0010\f\"\u0004\b\r\u0010\u000eR\u001c\u0010\u000f\u001a\u0004\u0018\u00010\nX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0010\u0010\f\"\u0004\b\u0011\u0010\u000e¨\u0006\u0016"}, d2 = {"Lorg/catrobat/catroid/content/actions/EnableBackgroundModeAction;", "Lcom/badlogic/gdx/scenes/scene2d/actions/TemporalAction;", "()V", "scope", "Lorg/catrobat/catroid/content/Scope;", "getScope", "()Lorg/catrobat/catroid/content/Scope;", "setScope", "(Lorg/catrobat/catroid/content/Scope;)V", "text", "Lorg/catrobat/catroid/formulaeditor/Formula;", "getText", "()Lorg/catrobat/catroid/formulaeditor/Formula;", "setText", "(Lorg/catrobat/catroid/formulaeditor/Formula;)V", "title", "getTitle", "setTitle", ConfigConstants.CONFIG_KEY_UPDATE, "", "percent", "", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class EnableBackgroundModeAction extends TemporalAction {
    private Scope scope;
    private Formula text;
    private Formula title;

    public final Scope getScope() {
        return this.scope;
    }

    public final void setScope(Scope scope) {
        this.scope = scope;
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

    @Override // com.badlogic.gdx.scenes.scene2d.actions.TemporalAction
    protected void update(float percent) {
        Context appContext = CatroidApplication.getAppContext();
        if (appContext == null) {
            return;
        }
        Formula formula = this.title;
        String strInterpretString = formula != null ? formula.interpretString(this.scope) : null;
        if (strInterpretString == null) {
            strInterpretString = "Работа в фоне";
        }
        Formula formula2 = this.text;
        String strInterpretString2 = formula2 != null ? formula2.interpretString(this.scope) : null;
        if (strInterpretString2 == null) {
            strInterpretString2 = "Скрипты активны";
        }
        StageListener activeStageListener = StageActivity.getActiveStageListener();
        if (activeStageListener != null) {
            activeStageListener.isBackgroundModeEnabled = true;
        }
        Intent intent = new Intent(appContext, (Class<?>) NewCatroidBackgroundService.class);
        intent.putExtra("TITLE", strInterpretString);
        intent.putExtra(SpriteActivity.EXTRA_TEXT, strInterpretString2);
        try {
            appContext.startForegroundService(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
