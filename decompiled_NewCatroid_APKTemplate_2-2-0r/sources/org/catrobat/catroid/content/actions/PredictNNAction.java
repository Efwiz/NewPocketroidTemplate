package org.catrobat.catroid.content.actions;

import com.badlogic.gdx.scenes.scene2d.actions.TemporalAction;
import com.google.android.gms.cast.MediaError;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.ArraysKt;
import kotlin.jvm.functions.Function1;
import kotlin.text.StringsKt;
import org.catrobat.catroid.NN.OnnxSessionManager;
import org.catrobat.catroid.content.FloatArrayManager;
import org.catrobat.catroid.content.Scope;
import org.catrobat.catroid.formulaeditor.Formula;
import org.catrobat.catroid.formulaeditor.UserVariable;
import org.eclipse.jgit.lib.ConfigConstants;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/actions/PredictNNAction.class
  classes14.dex
 */
/* JADX INFO: compiled from: PredictNNAction.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u00008\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u0007\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0017\u001a\u00020\u00042\u0006\u0010\u0018\u001a\u00020\u0019H\u0016J\b\u0010\u001a\u001a\u00020\u001bH\u0016J\u0010\u0010\u001c\u001a\u00020\u001b2\u0006\u0010\u001d\u001a\u00020\u0019H\u0014R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u000e¢\u0006\u0002\n\u0000R\u001c\u0010\u0005\u001a\u0004\u0018\u00010\u0006X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0007\u0010\b\"\u0004\b\t\u0010\nR\u001c\u0010\u000b\u001a\u0004\u0018\u00010\fX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\r\u0010\u000e\"\u0004\b\u000f\u0010\u0010R\u001c\u0010\u0011\u001a\u0004\u0018\u00010\u0012X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0013\u0010\u0014\"\u0004\b\u0015\u0010\u0016¨\u0006\u001e"}, d2 = {"Lorg/catrobat/catroid/content/actions/PredictNNAction;", "Lcom/badlogic/gdx/scenes/scene2d/actions/TemporalAction;", "()V", "hasStarted", "", "input", "Lorg/catrobat/catroid/formulaeditor/Formula;", "getInput", "()Lorg/catrobat/catroid/formulaeditor/Formula;", "setInput", "(Lorg/catrobat/catroid/formulaeditor/Formula;)V", "scope", "Lorg/catrobat/catroid/content/Scope;", "getScope", "()Lorg/catrobat/catroid/content/Scope;", "setScope", "(Lorg/catrobat/catroid/content/Scope;)V", "variable", "Lorg/catrobat/catroid/formulaeditor/UserVariable;", "getVariable", "()Lorg/catrobat/catroid/formulaeditor/UserVariable;", "setVariable", "(Lorg/catrobat/catroid/formulaeditor/UserVariable;)V", "act", "delta", "", "restart", "", ConfigConstants.CONFIG_KEY_UPDATE, "percent", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class PredictNNAction extends TemporalAction {
    private boolean hasStarted;
    private Formula input;
    private Scope scope;
    private UserVariable variable;

    @Override // com.badlogic.gdx.scenes.scene2d.actions.TemporalAction
    protected void update(float percent) {
    }

    public final Scope getScope() {
        return this.scope;
    }

    public final void setScope(Scope scope) {
        this.scope = scope;
    }

    public final Formula getInput() {
        return this.input;
    }

    public final void setInput(Formula formula) {
        this.input = formula;
    }

    public final UserVariable getVariable() {
        return this.variable;
    }

    public final void setVariable(UserVariable userVariable) {
        this.variable = userVariable;
    }

    /* JADX WARN: Code duplicated, block: B:26:0x004a  */
    @Override // com.badlogic.gdx.scenes.scene2d.actions.TemporalAction, com.badlogic.gdx.scenes.scene2d.Action
    public boolean act(float delta) {
        if (!OnnxSessionManager.INSTANCE.isWorking()) {
            return false;
        }
        if (this.hasStarted) {
            return true;
        }
        this.hasStarted = true;
        Formula formula = this.input;
        String strInterpretString = formula != null ? formula.interpretString(this.scope) : null;
        final UserVariable userVariable = this.variable;
        String str = strInterpretString;
        if (str != null && !StringsKt.isBlank(str) && userVariable != null) {
            float[] array = FloatArrayManager.INSTANCE.getArray(strInterpretString);
            if (array != null) {
                if (!(array.length == 0)) {
                    OnnxSessionManager.INSTANCE.predict(array, (Function1) new Function1<float[], Unit>() { // from class: org.catrobat.catroid.content.actions.PredictNNAction.act.1
                        {
                            super(1);
                        }

                        @Override // kotlin.jvm.functions.Function1
                        public /* bridge */ /* synthetic */ Unit invoke(float[] fArr) {
                            invoke2(fArr);
                            return Unit.INSTANCE;
                        }

                        /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
                        public final void invoke2(float[] fArr) {
                            if (fArr != null) {
                                userVariable.setValue(FloatArrayManager.INSTANCE.findMaxIndex(fArr) + "\n" + ArraysKt.joinToString$default(fArr, (CharSequence) ",", (CharSequence) null, (CharSequence) null, 0, (CharSequence) null, (Function1) null, 62, (Object) null));
                                return;
                            }
                            userVariable.setValue(MediaError.ERROR_TYPE_ERROR);
                        }
                    });
                } else {
                    userVariable.setValue("ARRAY_ERROR");
                }
            } else {
                userVariable.setValue("ARRAY_ERROR");
            }
        }
        return true;
    }

    @Override // com.badlogic.gdx.scenes.scene2d.actions.TemporalAction, com.badlogic.gdx.scenes.scene2d.Action
    public void restart() {
        super.restart();
        this.hasStarted = false;
    }
}
