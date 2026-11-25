package org.catrobat.catroid.content.actions;

import com.badlogic.gdx.scenes.scene2d.actions.TemporalAction;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.catrobat.catroid.content.LocalServer;
import org.catrobat.catroid.content.Scope;
import org.catrobat.catroid.formulaeditor.UserVariable;
import org.eclipse.jgit.lib.ConfigConstants;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/actions/ListenServerAction.class
  classes14.dex
 */
/* JADX INFO: compiled from: ListenServerAction.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u0007\n\u0000\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u0012H\u0014R\u001c\u0010\u0003\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\bR\u001c\u0010\t\u001a\u0004\u0018\u00010\nX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000b\u0010\f\"\u0004\b\r\u0010\u000e¨\u0006\u0013"}, d2 = {"Lorg/catrobat/catroid/content/actions/ListenServerAction;", "Lcom/badlogic/gdx/scenes/scene2d/actions/TemporalAction;", "()V", "scope", "Lorg/catrobat/catroid/content/Scope;", "getScope", "()Lorg/catrobat/catroid/content/Scope;", "setScope", "(Lorg/catrobat/catroid/content/Scope;)V", "variable", "Lorg/catrobat/catroid/formulaeditor/UserVariable;", "getVariable", "()Lorg/catrobat/catroid/formulaeditor/UserVariable;", "setVariable", "(Lorg/catrobat/catroid/formulaeditor/UserVariable;)V", ConfigConstants.CONFIG_KEY_UPDATE, "", "percent", "", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ListenServerAction extends TemporalAction {
    private Scope scope;
    private UserVariable variable;

    public final Scope getScope() {
        return this.scope;
    }

    public final void setScope(Scope scope) {
        this.scope = scope;
    }

    public final UserVariable getVariable() {
        return this.variable;
    }

    public final void setVariable(UserVariable userVariable) {
        this.variable = userVariable;
    }

    @Override // com.badlogic.gdx.scenes.scene2d.actions.TemporalAction
    protected void update(float percent) {
        if (this.variable == null) {
            return;
        }
        Executors.newScheduledThreadPool(1).scheduleAtFixedRate(new Runnable() { // from class: org.catrobat.catroid.content.actions.ListenServerAction$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                ListenServerAction.update$lambda$0(this.f$0);
            }
        }, 0L, 30L, TimeUnit.MILLISECONDS);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void update$lambda$0(ListenServerAction this$0) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        UserVariable userVariable = this$0.variable;
        if (userVariable == null) {
            return;
        }
        userVariable.setValue(LocalServer.INSTANCE.getValue());
    }
}
