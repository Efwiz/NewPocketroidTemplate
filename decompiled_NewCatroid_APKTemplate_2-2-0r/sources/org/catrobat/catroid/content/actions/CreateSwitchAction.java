package org.catrobat.catroid.content.actions;

import android.widget.FrameLayout;
import androidx.appcompat.widget.SwitchCompat;
import com.badlogic.gdx.scenes.scene2d.Action;
import java.lang.ref.WeakReference;
import java.util.Locale;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import org.apache.xerces.impl.xs.SchemaSymbols;
import org.catrobat.catroid.common.NativeViewBindingManager;
import org.catrobat.catroid.content.Scope;
import org.catrobat.catroid.formulaeditor.Formula;
import org.catrobat.catroid.stage.StageActivity;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/actions/CreateSwitchAction.class
  classes14.dex
 */
/* JADX INFO: compiled from: CreateSwitchAction.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0016\n\u0002\u0010\u0007\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0004\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010$\u001a\u00020\n2\u0006\u0010%\u001a\u00020&H\u0016J\b\u0010'\u001a\u00020(H\u0016J\b\u0010)\u001a\u00020(H\u0016J\b\u0010*\u001a\u00020(H\u0002J\u0018\u0010+\u001a\u00020\n*\u0004\u0018\u00010\u00042\b\u0010\u000e\u001a\u0004\u0018\u00010\u000fH\u0002R\u001c\u0010\u0003\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\bR\u000e\u0010\t\u001a\u00020\nX\u0082\u000e¢\u0006\u0002\n\u0000R\u001c\u0010\u000b\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\f\u0010\u0006\"\u0004\b\r\u0010\bR\u001c\u0010\u000e\u001a\u0004\u0018\u00010\u000fX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0010\u0010\u0011\"\u0004\b\u0012\u0010\u0013R\u000e\u0010\u0014\u001a\u00020\nX\u0082\u000e¢\u0006\u0002\n\u0000R\u001c\u0010\u0015\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0016\u0010\u0006\"\u0004\b\u0017\u0010\bR\u001c\u0010\u0018\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0019\u0010\u0006\"\u0004\b\u001a\u0010\bR\u001c\u0010\u001b\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001c\u0010\u0006\"\u0004\b\u001d\u0010\bR\u001c\u0010\u001e\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001f\u0010\u0006\"\u0004\b \u0010\bR\u001c\u0010!\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\"\u0010\u0006\"\u0004\b#\u0010\b¨\u0006,"}, d2 = {"Lorg/catrobat/catroid/content/actions/CreateSwitchAction;", "Lcom/badlogic/gdx/scenes/scene2d/Action;", "()V", "defaultState", "Lorg/catrobat/catroid/formulaeditor/Formula;", "getDefaultState", "()Lorg/catrobat/catroid/formulaeditor/Formula;", "setDefaultState", "(Lorg/catrobat/catroid/formulaeditor/Formula;)V", "finished", "", "height", "getHeight", "setHeight", "scope", "Lorg/catrobat/catroid/content/Scope;", "getScope", "()Lorg/catrobat/catroid/content/Scope;", "setScope", "(Lorg/catrobat/catroid/content/Scope;)V", "started", "text", "getText", "setText", "viewId", "getViewId", "setViewId", "width", "getWidth", "setWidth", "x", "getX", "setX", "y", "getY", "setY", "act", "delta", "", "reset", "", "restart", "runAsyncCreate", "interpretSafeBoolean", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class CreateSwitchAction extends Action {
    private Formula defaultState;
    private volatile boolean finished;
    private Formula height;
    private Scope scope;
    private boolean started;
    private Formula text;
    private Formula viewId;
    private Formula width;
    private Formula x;
    private Formula y;

    public final Scope getScope() {
        return this.scope;
    }

    public final void setScope(Scope scope) {
        this.scope = scope;
    }

    public final Formula getViewId() {
        return this.viewId;
    }

    public final void setViewId(Formula formula) {
        this.viewId = formula;
    }

    public final Formula getText() {
        return this.text;
    }

    public final void setText(Formula formula) {
        this.text = formula;
    }

    public final Formula getDefaultState() {
        return this.defaultState;
    }

    public final void setDefaultState(Formula formula) {
        this.defaultState = formula;
    }

    public final Formula getX() {
        return this.x;
    }

    public final void setX(Formula formula) {
        this.x = formula;
    }

    public final Formula getY() {
        return this.y;
    }

    public final void setY(Formula formula) {
        this.y = formula;
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

    /* JADX WARN: Code duplicated, block: B:23:0x004a  */
    private final boolean interpretSafeBoolean(Formula formula, Scope scope) {
        String lowerCase;
        String string;
        String string2;
        if (formula == null) {
            return false;
        }
        try {
            Object objInterpretObject = formula.interpretObject(scope);
            if (objInterpretObject instanceof Boolean) {
                return ((Boolean) objInterpretObject).booleanValue();
            }
            if (objInterpretObject instanceof Number) {
                return !(((Number) objInterpretObject).doubleValue() == 0.0d);
            }
            if (objInterpretObject == null || (string = objInterpretObject.toString()) == null || (string2 = StringsKt.trim((CharSequence) string).toString()) == null) {
                lowerCase = "";
            } else {
                lowerCase = string2.toLowerCase(Locale.ROOT);
                Intrinsics.checkNotNullExpressionValue(lowerCase, "toLowerCase(...)");
                if (lowerCase == null) {
                    lowerCase = "";
                }
            }
            return Intrinsics.areEqual(lowerCase, "true") || Intrinsics.areEqual(lowerCase, "истина") || Intrinsics.areEqual(lowerCase, "да") || Intrinsics.areEqual(lowerCase, "yes") || Intrinsics.areEqual(lowerCase, SchemaSymbols.ATTVAL_TRUE_1);
        } catch (Exception unused) {
            return false;
        }
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
        Formula formula = this.viewId;
        String strInterpretString = formula != null ? formula.interpretString(this.scope) : null;
        final String str = strInterpretString == null ? "" : strInterpretString;
        Formula formula2 = this.text;
        String strInterpretString2 = formula2 != null ? formula2.interpretString(this.scope) : null;
        final String str2 = strInterpretString2 != null ? strInterpretString2 : "";
        final boolean zInterpretSafeBoolean = interpretSafeBoolean(this.defaultState, this.scope);
        Formula formula3 = this.x;
        Integer numInterpretInteger = formula3 != null ? formula3.interpretInteger(this.scope) : null;
        final int iIntValue = numInterpretInteger == null ? 0 : numInterpretInteger.intValue();
        Formula formula4 = this.y;
        Integer numInterpretInteger2 = formula4 != null ? formula4.interpretInteger(this.scope) : null;
        final int iIntValue2 = numInterpretInteger2 == null ? 0 : numInterpretInteger2.intValue();
        Formula formula5 = this.width;
        Integer numInterpretInteger3 = formula5 != null ? formula5.interpretInteger(this.scope) : null;
        final int iIntValue3 = numInterpretInteger3 == null ? 300 : numInterpretInteger3.intValue();
        Formula formula6 = this.height;
        Integer numInterpretInteger4 = formula6 != null ? formula6.interpretInteger(this.scope) : null;
        final int iIntValue4 = numInterpretInteger4 == null ? 100 : numInterpretInteger4.intValue();
        if (str.length() == 0) {
            this.finished = true;
        } else {
            final StageActivity stageActivity2 = stageActivity;
            stageActivity.runOnUiThread(new Runnable() { // from class: org.catrobat.catroid.content.actions.CreateSwitchAction$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    CreateSwitchAction.runAsyncCreate$lambda$1(stageActivity2, str2, zInterpretSafeBoolean, iIntValue3, iIntValue4, str, this, iIntValue, iIntValue2);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void runAsyncCreate$lambda$1(StageActivity stageActivity, String labelText, boolean z, int i, int i2, String id2, CreateSwitchAction this$0, int i3, int i4) {
        Intrinsics.checkNotNullParameter(labelText, "$labelText");
        Intrinsics.checkNotNullParameter(id2, "$id");
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        try {
            SwitchCompat switchCompat = new SwitchCompat(stageActivity);
            switchCompat.setText(labelText);
            switchCompat.setChecked(z);
            switchCompat.setTextColor(-1);
            switchCompat.setVisibility(NativeViewBindingManager.getDefaultVisibility());
            FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(i, i2);
            layoutParams.leftMargin = i3;
            layoutParams.topMargin = i4;
            stageActivity.addViewToStage(id2, switchCompat, layoutParams);
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
