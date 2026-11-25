package org.catrobat.catroid.content.actions;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import com.badlogic.gdx.scenes.scene2d.actions.TemporalAction;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.catrobat.catroid.CatroidApplication;
import org.catrobat.catroid.content.Scope;
import org.catrobat.catroid.formulaeditor.Formula;
import org.eclipse.jgit.lib.ConfigConstants;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/actions/CopyTextAction.class
  classes14.dex
 */
/* JADX INFO: compiled from: CopyTextAction.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u00004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\u0007\n\u0000\u0018\u00002\u00020\u0001B\u000f\b\u0016\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004B\u0005¢\u0006\u0002\u0010\u0005J\u0010\u0010\u0013\u001a\u00020\u00142\u0006\u0010\r\u001a\u00020\u0015H\u0002J\u0010\u0010\u0016\u001a\u00020\u00142\u0006\u0010\u0017\u001a\u00020\u0018H\u0014R\u0010\u0010\u0006\u001a\u0004\u0018\u00010\u0003X\u0082\u000e¢\u0006\u0002\n\u0000R\u001c\u0010\u0007\u001a\u0004\u0018\u00010\bX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\t\u0010\n\"\u0004\b\u000b\u0010\fR\u001c\u0010\r\u001a\u0004\u0018\u00010\u000eX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000f\u0010\u0010\"\u0004\b\u0011\u0010\u0012¨\u0006\u0019"}, d2 = {"Lorg/catrobat/catroid/content/actions/CopyTextAction;", "Lcom/badlogic/gdx/scenes/scene2d/actions/TemporalAction;", "context", "Landroid/content/Context;", "(Landroid/content/Context;)V", "()V", "contextt", "scope", "Lorg/catrobat/catroid/content/Scope;", "getScope", "()Lorg/catrobat/catroid/content/Scope;", "setScope", "(Lorg/catrobat/catroid/content/Scope;)V", "text", "Lorg/catrobat/catroid/formulaeditor/Formula;", "getText", "()Lorg/catrobat/catroid/formulaeditor/Formula;", "setText", "(Lorg/catrobat/catroid/formulaeditor/Formula;)V", "copyToClipboard", "", "", ConfigConstants.CONFIG_KEY_UPDATE, "percent", "", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class CopyTextAction extends TemporalAction {
    private Context contextt;
    private Scope scope;
    private Formula text;

    public CopyTextAction() {
    }

    public final Scope getScope() {
        return this.scope;
    }

    public final void setScope(Scope scope) {
        this.scope = scope;
    }

    public final Formula getText() {
        return this.text;
    }

    public final void setText(Formula formula) {
        this.text = formula;
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public CopyTextAction(Context context) {
        this();
        Intrinsics.checkNotNullParameter(context, "context");
        this.contextt = context;
    }

    @Override // com.badlogic.gdx.scenes.scene2d.actions.TemporalAction
    protected void update(float percent) {
        Formula formula = this.text;
        Object objInterpretObject = formula != null ? formula.interpretObject(this.scope) : null;
        if (objInterpretObject == null) {
            objInterpretObject = "";
        }
        copyToClipboard(objInterpretObject.toString());
    }

    private final void copyToClipboard(String text) {
        Object systemService = CatroidApplication.getAppContext().getSystemService("clipboard");
        Intrinsics.checkNotNull(systemService, "null cannot be cast to non-null type android.content.ClipboardManager");
        ((ClipboardManager) systemService).setPrimaryClip(ClipData.newPlainText("Response", text));
    }
}
