package org.catrobat.catroid.content.actions;

import android.util.Log;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.catrobat.catroid.content.Scope;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.formulaeditor.Formula;
import org.catrobat.catroid.formulaeditor.InterpretationException;
import org.catrobat.catroid.formulaeditor.UserVariable;
import org.catrobat.catroid.stage.ShowTextActor;
import org.catrobat.catroid.stage.StageActivity;
import org.catrobat.catroid.stage.StageListener;
import org.catrobat.catroid.utils.ShowTextUtils;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/actions/ShowTextAction.class
  classes14.dex
 */
/* JADX INFO: compiled from: ShowTextAction.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000:\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0007\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0006\u0018\u0000 \u00162\u00020\u0001:\u0001\u0016B\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000fH\u0016J\u0010\u0010\u0010\u001a\u00020\u00112\b\u0010\u0003\u001a\u0004\u0018\u00010\u0004J\u0016\u0010\u0012\u001a\u00020\u00112\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\nJ\u0010\u0010\u0013\u001a\u00020\u00112\b\u0010\u0005\u001a\u0004\u0018\u00010\u0006J\u0010\u0010\u0014\u001a\u00020\u00112\b\u0010\u0015\u001a\u0004\u0018\u00010\bR\u0010\u0010\u0003\u001a\u0004\u0018\u00010\u0004X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u0005\u001a\u0004\u0018\u00010\u0006X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u0007\u001a\u0004\u0018\u00010\bX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\nX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\nX\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006\u0017"}, d2 = {"Lorg/catrobat/catroid/content/actions/ShowTextAction;", "Lcom/badlogic/gdx/scenes/scene2d/Action;", "()V", "androidStringProvider", "Lorg/catrobat/catroid/utils/ShowTextUtils$AndroidStringProvider;", "scope", "Lorg/catrobat/catroid/content/Scope;", "variableToShow", "Lorg/catrobat/catroid/formulaeditor/UserVariable;", "xPosition", "Lorg/catrobat/catroid/formulaeditor/Formula;", "yPosition", "act", "", "delta", "", "setAndroidStringProvider", "", "setPosition", "setScope", "setVariableToShow", "userVariable", "Companion", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ShowTextAction extends Action {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private static final String TAG;
    private ShowTextUtils.AndroidStringProvider androidStringProvider;
    private Scope scope;
    private UserVariable variableToShow;
    private Formula xPosition = new Formula((Integer) 0);
    private Formula yPosition = new Formula((Integer) 0);

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/actions/ShowTextAction$Companion.class
      classes14.dex
     */
    /* JADX INFO: compiled from: ShowTextAction.kt */
    @Metadata(d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0003\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u0011\u0010\u0003\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u0007"}, d2 = {"Lorg/catrobat/catroid/content/actions/ShowTextAction$Companion;", "", "()V", "TAG", "", "getTAG", "()Ljava/lang/String;", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final String getTAG() {
            return ShowTextAction.TAG;
        }
    }

    static {
        Intrinsics.checkNotNullExpressionValue("ShowTextAction", "getSimpleName(...)");
        TAG = "ShowTextAction";
    }

    @Override // com.badlogic.gdx.scenes.scene2d.Action
    public boolean act(float delta) {
        ShowTextActor showTextActor;
        try {
            UserVariable userVariable = this.variableToShow;
            if (userVariable == null) {
                return true;
            }
            userVariable.setVisible(true);
            Float fInterpretFloat = this.xPosition.interpretFloat(this.scope);
            Float fInterpretFloat2 = this.yPosition.interpretFloat(this.scope);
            StageActivity stageActivity = StageActivity.activeStageActivity.get();
            StageListener stageListener = stageActivity != null ? stageActivity.stageListener : null;
            if (stageListener == null) {
                return true;
            }
            Stage stage = stageListener.getStage();
            Array<Actor> actors = stage != null ? stage.getActors() : null;
            if (actors == null) {
                return true;
            }
            int i = actors.size;
            int i2 = 0;
            while (true) {
                if (i2 >= i) {
                    showTextActor = null;
                    break;
                }
                Actor actor = actors.get(i2);
                if ((actor instanceof ShowTextActor) && Intrinsics.areEqual(((ShowTextActor) actor).getVariableNameToCompare(), userVariable.getName())) {
                    Sprite sprite = ((ShowTextActor) actor).getSprite();
                    Scope scope = this.scope;
                    if (Intrinsics.areEqual(sprite, scope != null ? scope.getSprite() : null)) {
                        showTextActor = (ShowTextActor) actor;
                        break;
                    }
                }
                i2++;
            }
            if (showTextActor == null) {
                Intrinsics.checkNotNull(fInterpretFloat);
                float fFloatValue = fInterpretFloat.floatValue();
                Intrinsics.checkNotNull(fInterpretFloat2);
                float fFloatValue2 = fInterpretFloat2.floatValue();
                Scope scope2 = this.scope;
                stageListener.addActor(new ShowTextActor(false, userVariable, "", fFloatValue, fFloatValue2, 1.0f, null, scope2 != null ? scope2.getSprite() : null, 1, this.androidStringProvider));
            } else {
                String name2 = userVariable.getName();
                Intrinsics.checkNotNull(fInterpretFloat);
                float fFloatValue3 = fInterpretFloat.floatValue();
                Intrinsics.checkNotNull(fInterpretFloat2);
                showTextActor.updateProperties(name2, fFloatValue3, fInterpretFloat2.floatValue(), 1.0f, null, null);
            }
            return true;
        } catch (InterpretationException e) {
            Log.d(TAG, "InterpretationException: " + e);
        }
    }

    public final void setPosition(Formula xPosition, Formula yPosition) {
        Intrinsics.checkNotNullParameter(xPosition, "xPosition");
        Intrinsics.checkNotNullParameter(yPosition, "yPosition");
        this.xPosition = xPosition;
        this.yPosition = yPosition;
    }

    public final void setScope(Scope scope) {
        this.scope = scope;
    }

    public final void setVariableToShow(UserVariable userVariable) {
        this.variableToShow = userVariable;
    }

    public final void setAndroidStringProvider(ShowTextUtils.AndroidStringProvider androidStringProvider) {
        this.androidStringProvider = androidStringProvider;
    }
}
