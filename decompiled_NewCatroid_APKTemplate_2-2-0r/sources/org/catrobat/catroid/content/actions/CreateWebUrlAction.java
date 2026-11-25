package org.catrobat.catroid.content.actions;

import com.badlogic.gdx.scenes.scene2d.Action;
import java.lang.ref.WeakReference;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.catrobat.catroid.content.Scope;
import org.catrobat.catroid.formulaeditor.Formula;
import org.catrobat.catroid.stage.StageActivity;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/actions/CreateWebUrlAction.class
  classes14.dex
 */
/* JADX INFO: compiled from: CreateWebUrlAction.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u000e\n\u0002\u0018\u0002\n\u0002\b\r\n\u0002\u0010\u0007\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010!\u001a\u00020\u00042\u0006\u0010\"\u001a\u00020#H\u0016J\b\u0010$\u001a\u00020%H\u0016J\b\u0010&\u001a\u00020%H\u0016J\b\u0010'\u001a\u00020%H\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u000e¢\u0006\u0002\n\u0000R\u001c\u0010\u0005\u001a\u0004\u0018\u00010\u0006X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0007\u0010\b\"\u0004\b\t\u0010\nR\u001c\u0010\u000b\u001a\u0004\u0018\u00010\u0006X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\f\u0010\b\"\u0004\b\r\u0010\nR\u001c\u0010\u000e\u001a\u0004\u0018\u00010\u0006X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000f\u0010\b\"\u0004\b\u0010\u0010\nR\u001c\u0010\u0011\u001a\u0004\u0018\u00010\u0006X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0012\u0010\b\"\u0004\b\u0013\u0010\nR\u001c\u0010\u0014\u001a\u0004\u0018\u00010\u0015X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0016\u0010\u0017\"\u0004\b\u0018\u0010\u0019R\u000e\u0010\u001a\u001a\u00020\u0004X\u0082\u000e¢\u0006\u0002\n\u0000R\u001c\u0010\u001b\u001a\u0004\u0018\u00010\u0006X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001c\u0010\b\"\u0004\b\u001d\u0010\nR\u001c\u0010\u001e\u001a\u0004\u0018\u00010\u0006X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001f\u0010\b\"\u0004\b \u0010\n¨\u0006("}, d2 = {"Lorg/catrobat/catroid/content/actions/CreateWebUrlAction;", "Lcom/badlogic/gdx/scenes/scene2d/Action;", "()V", "finished", "", "height", "Lorg/catrobat/catroid/formulaeditor/Formula;", "getHeight", "()Lorg/catrobat/catroid/formulaeditor/Formula;", "setHeight", "(Lorg/catrobat/catroid/formulaeditor/Formula;)V", "name", "getName", "setName", "posX", "getPosX", "setPosX", "posY", "getPosY", "setPosY", "scope", "Lorg/catrobat/catroid/content/Scope;", "getScope", "()Lorg/catrobat/catroid/content/Scope;", "setScope", "(Lorg/catrobat/catroid/content/Scope;)V", "started", "url", "getUrl", "setUrl", "width", "getWidth", "setWidth", "act", "delta", "", "reset", "", "restart", "runAsyncCreate", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class CreateWebUrlAction extends Action {
    private volatile boolean finished;
    private Formula height;
    private Formula name;
    private Formula posX;
    private Formula posY;
    private Scope scope;
    private boolean started;
    private Formula url;
    private Formula width;

    public final Scope getScope() {
        return this.scope;
    }

    public final void setScope(Scope scope) {
        this.scope = scope;
    }

    public final Formula getUrl() {
        return this.url;
    }

    public final void setUrl(Formula formula) {
        this.url = formula;
    }

    public final Formula getName() {
        return this.name;
    }

    public final void setName(Formula formula) {
        this.name = formula;
    }

    public final Formula getPosX() {
        return this.posX;
    }

    public final void setPosX(Formula formula) {
        this.posX = formula;
    }

    public final Formula getPosY() {
        return this.posY;
    }

    public final void setPosY(Formula formula) {
        this.posY = formula;
    }

    public final Formula getWidth() {
        return this.width;
    }

    public final void setWidth(Formula formula) {
        this.width = formula;
    }

    public final Formula getHeight() {
        return this.height;
    }

    public final void setHeight(Formula formula) {
        this.height = formula;
    }

    @Override // com.badlogic.gdx.scenes.scene2d.Action
    public boolean act(float delta) {
        if (!this.started) {
            this.started = true;
            runAsyncCreate();
        }
        return this.finished;
    }

    private final void runAsyncCreate() {
        WeakReference<StageActivity> weakReference = StageActivity.activeStageActivity;
        StageActivity stageActivity = weakReference != null ? weakReference.get() : null;
        if (stageActivity == null) {
            this.finished = true;
            return;
        }
        Formula formula = this.url;
        String strInterpretString = formula != null ? formula.interpretString(this.scope) : null;
        final String str = strInterpretString == null ? "" : strInterpretString;
        Formula formula2 = this.name;
        String strInterpretString2 = formula2 != null ? formula2.interpretString(this.scope) : null;
        final String str2 = strInterpretString2 != null ? strInterpretString2 : "";
        Formula formula3 = this.posX;
        Integer numInterpretInteger = formula3 != null ? formula3.interpretInteger(this.scope) : null;
        int iIntValue = numInterpretInteger == null ? 0 : numInterpretInteger.intValue();
        Formula formula4 = this.posY;
        Integer numInterpretInteger2 = formula4 != null ? formula4.interpretInteger(this.scope) : null;
        int iIntValue2 = numInterpretInteger2 == null ? 0 : numInterpretInteger2.intValue();
        Formula formula5 = this.width;
        Integer numInterpretInteger3 = formula5 != null ? formula5.interpretInteger(this.scope) : null;
        int iIntValue3 = numInterpretInteger3 == null ? 0 : numInterpretInteger3.intValue();
        Formula formula6 = this.height;
        Integer numInterpretInteger4 = formula6 != null ? formula6.interpretInteger(this.scope) : null;
        int iIntValue4 = numInterpretInteger4 == null ? 0 : numInterpretInteger4.intValue();
        if (str2.length() == 0) {
            this.finished = true;
            return;
        }
        final StageActivity stageActivity2 = stageActivity;
        final int i = iIntValue;
        final int i2 = iIntValue2;
        final int i3 = iIntValue3;
        final int i4 = iIntValue4;
        stageActivity.runOnUiThread(new Runnable() { // from class: org.catrobat.catroid.content.actions.CreateWebUrlAction$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                CreateWebUrlAction.runAsyncCreate$lambda$0(stageActivity2, str2, str, i, i2, i3, i4, this);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void runAsyncCreate$lambda$0(StageActivity stageActivity, String namev, String urlv, int i, int i2, int i3, int i4, CreateWebUrlAction this$0) {
        Intrinsics.checkNotNullParameter(namev, "$namev");
        Intrinsics.checkNotNullParameter(urlv, "$urlv");
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        try {
            stageActivity.createWebViewWithUrl(namev, urlv, i, i2, i3, i4);
        } finally {
            this$0.finished = true;
        }
    }

    @Override // com.badlogic.gdx.scenes.scene2d.Action
    public void restart() {
        super.restart();
        this.started = false;
        this.finished = false;
    }

    @Override // com.badlogic.gdx.scenes.scene2d.Action, com.badlogic.gdx.utils.Pool.Poolable
    public void reset() {
        super.reset();
        this.started = false;
        this.finished = false;
    }
}
