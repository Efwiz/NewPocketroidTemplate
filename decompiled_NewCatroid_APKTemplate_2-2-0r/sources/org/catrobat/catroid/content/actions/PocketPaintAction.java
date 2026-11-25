package org.catrobat.catroid.content.actions;

import com.badlogic.gdx.scenes.scene2d.Action;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.catrobat.catroid.content.Scope;
import org.catrobat.catroid.formulaeditor.Formula;
import org.catrobat.catroid.io.XstreamSerializer;
import org.catrobat.catroid.stage.StageActivity;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/actions/PocketPaintAction.class
  classes14.dex
 */
/* JADX INFO: compiled from: PocketPaintAction.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000@\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0002\b\u0002\b&\u0018\u00002\u00020\u00012\u00020\u0002B\u0005¢\u0006\u0002\u0010\u0003J\u000e\u0010\n\u001a\u00020%2\u0006\u0010\n\u001a\u00020\u000bJ\b\u0010&\u001a\u00020%H\u0016R\u001c\u0010\u0004\u001a\u0004\u0018\u00010\u0005X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0006\u0010\u0007\"\u0004\b\b\u0010\tR\u001c\u0010\n\u001a\u0004\u0018\u00010\u000bX\u0084\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\f\u0010\r\"\u0004\b\u000e\u0010\u000fR\u001a\u0010\u0010\u001a\u00020\u0011X\u0084\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0012\u0010\u0013\"\u0004\b\u0014\u0010\u0015R\u001a\u0010\u0016\u001a\u00020\u0011X\u0084\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0017\u0010\u0013\"\u0004\b\u0018\u0010\u0015R\u001c\u0010\u0019\u001a\u0004\u0018\u00010\u001aX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001b\u0010\u001c\"\u0004\b\u001d\u0010\u001eR\u001a\u0010\u001f\u001a\u00020 X\u0084\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b!\u0010\"\"\u0004\b#\u0010$¨\u0006'"}, d2 = {"Lorg/catrobat/catroid/content/actions/PocketPaintAction;", "Lcom/badlogic/gdx/scenes/scene2d/Action;", "Lorg/catrobat/catroid/stage/StageActivity$IntentListener;", "()V", "formula", "Lorg/catrobat/catroid/formulaeditor/Formula;", "getFormula", "()Lorg/catrobat/catroid/formulaeditor/Formula;", "setFormula", "(Lorg/catrobat/catroid/formulaeditor/Formula;)V", "nextLookAction", "Lorg/catrobat/catroid/content/actions/SetNextLookAction;", "getNextLookAction", "()Lorg/catrobat/catroid/content/actions/SetNextLookAction;", "setNextLookAction", "(Lorg/catrobat/catroid/content/actions/SetNextLookAction;)V", "questionAsked", "", "getQuestionAsked", "()Z", "setQuestionAsked", "(Z)V", "responseReceived", "getResponseReceived", "setResponseReceived", "scope", "Lorg/catrobat/catroid/content/Scope;", "getScope", "()Lorg/catrobat/catroid/content/Scope;", "setScope", "(Lorg/catrobat/catroid/content/Scope;)V", "xstreamSerializer", "Lorg/catrobat/catroid/io/XstreamSerializer;", "getXstreamSerializer", "()Lorg/catrobat/catroid/io/XstreamSerializer;", "setXstreamSerializer", "(Lorg/catrobat/catroid/io/XstreamSerializer;)V", "", "restart", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class PocketPaintAction extends Action implements StageActivity.IntentListener {
    private Formula formula;
    private SetNextLookAction nextLookAction;
    private boolean questionAsked;
    private boolean responseReceived;
    private Scope scope;
    private XstreamSerializer xstreamSerializer;

    public PocketPaintAction() {
        XstreamSerializer xstreamSerializer = XstreamSerializer.getInstance();
        Intrinsics.checkNotNullExpressionValue(xstreamSerializer, "getInstance(...)");
        this.xstreamSerializer = xstreamSerializer;
    }

    public final Formula getFormula() {
        return this.formula;
    }

    public final void setFormula(Formula formula) {
        this.formula = formula;
    }

    public final Scope getScope() {
        return this.scope;
    }

    public final void setScope(Scope scope) {
        this.scope = scope;
    }

    protected final boolean getResponseReceived() {
        return this.responseReceived;
    }

    protected final void setResponseReceived(boolean z) {
        this.responseReceived = z;
    }

    protected final boolean getQuestionAsked() {
        return this.questionAsked;
    }

    protected final void setQuestionAsked(boolean z) {
        this.questionAsked = z;
    }

    protected final SetNextLookAction getNextLookAction() {
        return this.nextLookAction;
    }

    protected final void setNextLookAction(SetNextLookAction setNextLookAction) {
        this.nextLookAction = setNextLookAction;
    }

    protected final XstreamSerializer getXstreamSerializer() {
        return this.xstreamSerializer;
    }

    protected final void setXstreamSerializer(XstreamSerializer xstreamSerializer) {
        Intrinsics.checkNotNullParameter(xstreamSerializer, "<set-?>");
        this.xstreamSerializer = xstreamSerializer;
    }

    @Override // com.badlogic.gdx.scenes.scene2d.Action
    public void restart() {
        this.questionAsked = false;
        this.responseReceived = false;
        super.restart();
    }

    public final void nextLookAction(SetNextLookAction nextLookAction) {
        Intrinsics.checkNotNullParameter(nextLookAction, "nextLookAction");
        this.nextLookAction = nextLookAction;
    }
}
