package org.catrobat.catroid.content.actions;

import android.graphics.Typeface;
import android.util.Log;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Array;
import java.io.File;
import java.util.HashMap;
import java.util.Map;
import org.catrobat.catroid.content.Scope;
import org.catrobat.catroid.formulaeditor.Formula;
import org.catrobat.catroid.formulaeditor.InterpretationException;
import org.catrobat.catroid.formulaeditor.UserVariable;
import org.catrobat.catroid.stage.ShowTextActor;
import org.catrobat.catroid.stage.StageActivity;
import org.catrobat.catroid.utils.ShowTextUtils;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/actions/ShowTextRotationAction.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class ShowTextRotationAction extends Action {
    private static final Map<String, Typeface> fontCache = new HashMap();
    private int alignment;
    private ShowTextUtils.AndroidStringProvider androidStringProvider;
    private Formula color;
    private Formula file;

    /* JADX INFO: renamed from: name, reason: collision with root package name */
    private Formula f553name;
    private Formula relativeTextSize;
    private Formula rotation;
    private Scope scope;
    private Formula text;
    private Formula xPosition;
    private Formula yPosition;

    @Override // com.badlogic.gdx.scenes.scene2d.Action
    public boolean act(float delta) {
        ShowTextActor showTextActor;
        try {
            String strInterpretString = this.f553name.interpretString(this.scope) != null ? this.f553name.interpretString(this.scope) : "dummyActor";
            String strInterpretString2 = this.text.interpretString(this.scope) != null ? this.text.interpretString(this.scope) : "NaN";
            String strInterpretString3 = this.file.interpretString(this.scope) != null ? this.file.interpretString(this.scope) : "font.ttf";
            float fFloatValue = this.rotation.interpretFloat(this.scope).floatValue();
            float fFloatValue2 = this.xPosition.interpretFloat(this.scope).floatValue();
            float fFloatValue3 = this.yPosition.interpretFloat(this.scope).floatValue();
            float fFloatValue4 = this.relativeTextSize.interpretFloat(this.scope).floatValue() / 100.0f;
            String strInterpretString4 = this.color.interpretString(this.scope);
            File file = this.scope.getProject().getFile(strInterpretString3);
            if (file != null && file.exists()) {
                String absolutePath = file.getAbsolutePath();
                Map<String, Typeface> map = fontCache;
                if (!map.containsKey(absolutePath)) {
                    map.put(absolutePath, Typeface.createFromFile(file));
                }
                Typeface typeface = map.get(absolutePath);
                if (StageActivity.getActiveStageListener() == null) {
                    return true;
                }
                Array.ArrayIterator<Actor> it = StageActivity.getActiveStageListener().getStage().getActors().iterator();
                while (true) {
                    if (!it.hasNext()) {
                        showTextActor = null;
                        break;
                    }
                    Actor next = it.next();
                    if (next instanceof ShowTextActor) {
                        showTextActor = (ShowTextActor) next;
                        if (showTextActor.getVariableNameToCompare().equals(strInterpretString) && showTextActor.getSprite().equals(this.scope.getSprite())) {
                            break;
                        }
                    }
                }
                ShowTextActor showTextActor2 = showTextActor;
                if (showTextActor2 == null) {
                    UserVariable userVariable = new UserVariable(strInterpretString, strInterpretString2);
                    ShowTextActor showTextActor3 = new ShowTextActor(true, userVariable, strInterpretString2, fFloatValue2, fFloatValue3, fFloatValue4, strInterpretString4, this.scope.getSprite(), this.alignment, this.androidStringProvider);
                    showTextActor3.setFont(typeface);
                    showTextActor3.setRotation(fFloatValue);
                    StageActivity.getActiveStageListener().addActor(showTextActor3);
                    userVariable.setVisible(true);
                    return true;
                }
                showTextActor2.updateProperties(strInterpretString2, fFloatValue2, fFloatValue3, fFloatValue4, strInterpretString4, typeface);
                showTextActor2.setRotation(fFloatValue);
                return true;
            }
            return true;
        } catch (InterpretationException e) {
            Log.d("ShowTextRotation", "InterpretationException: " + e);
            return true;
        }
    }

    public void setPosition(Formula xPosition, Formula yPosition) {
        this.xPosition = xPosition;
        this.yPosition = yPosition;
    }

    public void setRelativeTextSize(Formula relativeTextSize) {
        this.relativeTextSize = relativeTextSize;
    }

    public void setColor(Formula color) {
        this.color = color;
    }

    public void setScope(Scope scope) {
        this.scope = scope;
    }

    public void setText(Formula text) {
        this.text = text;
    }

    public void setRotation(Formula rotation) {
        this.rotation = rotation;
    }

    public void setFile(Formula file) {
        this.file = file;
    }

    public void setName(Formula name2) {
        this.f553name = name2;
    }

    public void setAlignment(int alignment) {
        this.alignment = alignment;
    }

    public void setAndroidStringProvider(ShowTextUtils.AndroidStringProvider androidStringProvider) {
        this.androidStringProvider = androidStringProvider;
    }
}
