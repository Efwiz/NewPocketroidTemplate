package org.catrobat.catroid.content.actions;

import android.util.Log;
import com.badlogic.gdx.scenes.scene2d.actions.TemporalAction;
import org.catrobat.catroid.content.Scope;
import org.catrobat.catroid.formulaeditor.Formula;
import org.catrobat.catroid.formulaeditor.InterpretationException;
import org.catrobat.catroid.stage.ShowBubbleActor;
import org.catrobat.catroid.stage.StageActivity;
import org.catrobat.catroid.utils.ShowTextUtils;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/actions/ThinkSayBubbleAction.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class ThinkSayBubbleAction extends TemporalAction {
    private static final String TAG = "ThinkSayBubbleAction";
    private ShowTextUtils.AndroidStringProvider androidStringProvider;
    private Scope scope;
    private Formula text;
    private int type;

    @Override // com.badlogic.gdx.scenes.scene2d.actions.TemporalAction
    protected void update(float delta) {
        try {
            ShowBubbleActor showBubbleActorCreateBubbleActor = createBubbleActor(this.androidStringProvider);
            if (StageActivity.getActiveStageListener().getBubbleActorForSprite(this.scope.getSprite()) != null) {
                StageActivity.getActiveStageListener().removeBubbleActorForSprite(this.scope.getSprite());
            }
            if (showBubbleActorCreateBubbleActor != null) {
                StageActivity.getActiveStageListener().setBubbleActorForSprite(this.scope.getSprite(), showBubbleActorCreateBubbleActor);
            }
        } catch (InterpretationException e) {
            Log.d(TAG, "Failed to create Bubble Actor", e);
        }
    }

    public ShowBubbleActor createBubbleActor(ShowTextUtils.AndroidStringProvider stringProvider) throws InterpretationException {
        Formula formula = this.text;
        String userFriendlyString = formula == null ? "" : formula.getUserFriendlyString(stringProvider, this.scope);
        if (userFriendlyString.isEmpty()) {
            return null;
        }
        return new ShowBubbleActor(userFriendlyString, this.scope.getSprite(), this.type);
    }

    public void setAndroidStringProvider(ShowTextUtils.AndroidStringProvider androidStringProvider) {
        this.androidStringProvider = androidStringProvider;
    }

    public void setScope(Scope scope) {
        this.scope = scope;
    }

    public void setText(Formula text) {
        this.text = text;
    }

    public void setType(int type) {
        this.type = type;
    }
}
