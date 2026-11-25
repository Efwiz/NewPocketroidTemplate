package org.catrobat.catroid.content.actions;

import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.widget.Button;
import android.widget.FrameLayout;
import com.badlogic.gdx.scenes.scene2d.Action;
import java.lang.ref.WeakReference;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.catrobat.catroid.common.NativeViewBindingManager;
import org.catrobat.catroid.content.Scope;
import org.catrobat.catroid.formulaeditor.Formula;
import org.catrobat.catroid.stage.StageActivity;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/actions/CreateButtonAction.class
  classes14.dex
 */
/* JADX INFO: compiled from: CreateButtonAction.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u000b\n\u0002\u0010\u000b\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0016\n\u0002\u0010\u0007\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010-\u001a\u00020\u00102\u0006\u0010.\u001a\u00020/H\u0016J\b\u00100\u001a\u000201H\u0016J\b\u00102\u001a\u000201H\u0016J\b\u00103\u001a\u000201H\u0002R\u001c\u0010\u0003\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\bR\u001c\u0010\t\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\n\u0010\u0006\"\u0004\b\u000b\u0010\bR\u001c\u0010\f\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\r\u0010\u0006\"\u0004\b\u000e\u0010\bR\u000e\u0010\u000f\u001a\u00020\u0010X\u0082\u000e¢\u0006\u0002\n\u0000R\u001c\u0010\u0011\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0012\u0010\u0006\"\u0004\b\u0013\u0010\bR\u001c\u0010\u0014\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0015\u0010\u0006\"\u0004\b\u0016\u0010\bR\u001c\u0010\u0017\u001a\u0004\u0018\u00010\u0018X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0019\u0010\u001a\"\u0004\b\u001b\u0010\u001cR\u000e\u0010\u001d\u001a\u00020\u0010X\u0082\u000e¢\u0006\u0002\n\u0000R\u001c\u0010\u001e\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001f\u0010\u0006\"\u0004\b \u0010\bR\u001c\u0010!\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\"\u0010\u0006\"\u0004\b#\u0010\bR\u001c\u0010$\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b%\u0010\u0006\"\u0004\b&\u0010\bR\u001c\u0010'\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b(\u0010\u0006\"\u0004\b)\u0010\bR\u001c\u0010*\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b+\u0010\u0006\"\u0004\b,\u0010\b¨\u00064"}, d2 = {"Lorg/catrobat/catroid/content/actions/CreateButtonAction;", "Lcom/badlogic/gdx/scenes/scene2d/Action;", "()V", "bgColor", "Lorg/catrobat/catroid/formulaeditor/Formula;", "getBgColor", "()Lorg/catrobat/catroid/formulaeditor/Formula;", "setBgColor", "(Lorg/catrobat/catroid/formulaeditor/Formula;)V", "colorHex", "getColorHex", "setColorHex", StageActivity.STYLE_CORNER_RADIUS, "getCornerRadius", "setCornerRadius", "finished", "", "fontSize", "getFontSize", "setFontSize", "height", "getHeight", "setHeight", "scope", "Lorg/catrobat/catroid/content/Scope;", "getScope", "()Lorg/catrobat/catroid/content/Scope;", "setScope", "(Lorg/catrobat/catroid/content/Scope;)V", "started", "text", "getText", "setText", "viewId", "getViewId", "setViewId", "width", "getWidth", "setWidth", "x", "getX", "setX", "y", "getY", "setY", "act", "delta", "", "reset", "", "restart", "runAsyncCreate", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class CreateButtonAction extends Action {
    private Formula bgColor;
    private Formula colorHex;
    private Formula cornerRadius;
    private volatile boolean finished;
    private Formula fontSize;
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

    public final Formula getColorHex() {
        return this.colorHex;
    }

    public final void setColorHex(Formula formula) {
        this.colorHex = formula;
    }

    public final Formula getFontSize() {
        return this.fontSize;
    }

    public final void setFontSize(Formula formula) {
        this.fontSize = formula;
    }

    public final Formula getBgColor() {
        return this.bgColor;
    }

    public final void setBgColor(Formula formula) {
        this.bgColor = formula;
    }

    public final Formula getCornerRadius() {
        return this.cornerRadius;
    }

    public final void setCornerRadius(Formula formula) {
        this.cornerRadius = formula;
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
        Formula formula3 = this.colorHex;
        String strInterpretString3 = formula3 != null ? formula3.interpretString(this.scope) : null;
        if (strInterpretString3 == null) {
            strInterpretString3 = "#FFFFFF";
        }
        final String str3 = strInterpretString3;
        Formula formula4 = this.fontSize;
        Float fInterpretFloat = formula4 != null ? formula4.interpretFloat(this.scope) : null;
        final float fFloatValue = fInterpretFloat == null ? 18.0f : fInterpretFloat.floatValue();
        Formula formula5 = this.bgColor;
        String strInterpretString4 = formula5 != null ? formula5.interpretString(this.scope) : null;
        if (strInterpretString4 == null) {
            strInterpretString4 = "#2196F3";
        }
        final String str4 = strInterpretString4;
        Formula formula6 = this.cornerRadius;
        Integer numInterpretInteger = formula6 != null ? formula6.interpretInteger(this.scope) : null;
        final int iIntValue = numInterpretInteger == null ? 8 : numInterpretInteger.intValue();
        Formula formula7 = this.x;
        Integer numInterpretInteger2 = formula7 != null ? formula7.interpretInteger(this.scope) : null;
        final int iIntValue2 = numInterpretInteger2 == null ? 0 : numInterpretInteger2.intValue();
        Formula formula8 = this.y;
        Integer numInterpretInteger3 = formula8 != null ? formula8.interpretInteger(this.scope) : null;
        final int iIntValue3 = numInterpretInteger3 == null ? 0 : numInterpretInteger3.intValue();
        Formula formula9 = this.width;
        Integer numInterpretInteger4 = formula9 != null ? formula9.interpretInteger(this.scope) : null;
        final int iIntValue4 = numInterpretInteger4 == null ? 300 : numInterpretInteger4.intValue();
        Formula formula10 = this.height;
        Integer numInterpretInteger5 = formula10 != null ? formula10.interpretInteger(this.scope) : null;
        final int iIntValue5 = numInterpretInteger5 == null ? 100 : numInterpretInteger5.intValue();
        if (str.length() == 0) {
            this.finished = true;
        } else {
            final StageActivity stageActivity2 = stageActivity;
            stageActivity.runOnUiThread(new Runnable() { // from class: org.catrobat.catroid.content.actions.CreateButtonAction$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    CreateButtonAction.runAsyncCreate$lambda$2(stageActivity2, str2, fFloatValue, str3, iIntValue4, iIntValue5, str, this, iIntValue, str4, iIntValue2, iIntValue3);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void runAsyncCreate$lambda$2(StageActivity stageActivity, String rawText, float f, String cHex, int i, int i2, String id2, CreateButtonAction this$0, int i3, String bgHex, int i4, int i5) {
        Intrinsics.checkNotNullParameter(rawText, "$rawText");
        Intrinsics.checkNotNullParameter(cHex, "$cHex");
        Intrinsics.checkNotNullParameter(id2, "$id");
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNullParameter(bgHex, "$bgHex");
        try {
            Button button = new Button(stageActivity);
            button.setText(rawText);
            button.setTextSize(f);
            try {
                button.setTextColor(Color.parseColor(cHex));
            } catch (Exception unused) {
                button.setTextColor(-1);
            }
            GradientDrawable gradientDrawable = new GradientDrawable();
            gradientDrawable.setShape(0);
            gradientDrawable.setCornerRadius(i3);
            try {
                gradientDrawable.setColor(Color.parseColor(bgHex));
            } catch (Exception unused2) {
                gradientDrawable.setColor(-16776961);
            }
            button.setBackground(gradientDrawable);
            button.setVisibility(NativeViewBindingManager.getDefaultVisibility());
            FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(i, i2);
            layoutParams.leftMargin = i4;
            layoutParams.topMargin = i5;
            stageActivity.addViewToStage(id2, button, layoutParams);
            this$0.finished = true;
        } catch (Throwable th) {
            this$0.finished = true;
            throw th;
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
