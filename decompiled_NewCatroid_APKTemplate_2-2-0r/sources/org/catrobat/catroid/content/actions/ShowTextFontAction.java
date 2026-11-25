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
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/actions/ShowTextFontAction.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class ShowTextFontAction extends Action {
    private static final Map<String, Typeface> fontCache = new HashMap();
    private int alignment;
    private ShowTextUtils.AndroidStringProvider androidStringProvider;
    private Formula color;
    private Formula file;

    /* JADX INFO: renamed from: name, reason: collision with root package name */
    private Formula f552name;
    private Formula relativeTextSize;
    private Scope scope;
    private Formula text;
    private Formula xPosition;
    private Formula yPosition;

    @Override // com.badlogic.gdx.scenes.scene2d.Action
    public boolean act(float delta) {
        ShowTextActor showTextActor;
        try {
            String strInterpretString = this.f552name.interpretString(this.scope) != null ? this.f552name.interpretString(this.scope) : "dummyActor";
            String strInterpretString2 = this.text.interpretString(this.scope) != null ? this.text.interpretString(this.scope) : "NaN";
            String strInterpretString3 = this.file.interpretString(this.scope) != null ? this.file.interpretString(this.scope) : "font.ttf";
            float fFloatValue = this.xPosition.interpretFloat(this.scope).floatValue();
            float fFloatValue2 = this.yPosition.interpretFloat(this.scope).floatValue();
            float fFloatValue3 = this.relativeTextSize.interpretFloat(this.scope).floatValue() / 100.0f;
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
                        ShowTextActor showTextActor2 = (ShowTextActor) next;
                        if (showTextActor2.getVariableNameToCompare().equals(strInterpretString) && showTextActor2.getSprite().equals(this.scope.getSprite())) {
                            showTextActor = showTextActor2;
                            break;
                        }
                    }
                }
                if (showTextActor == null) {
                    UserVariable userVariable = new UserVariable(strInterpretString, strInterpretString2);
                    ShowTextActor showTextActor3 = new ShowTextActor(true, userVariable, strInterpretString2, fFloatValue, fFloatValue2, fFloatValue3, strInterpretString4, this.scope.getSprite(), this.alignment, this.androidStringProvider);
                    showTextActor3.setFont(typeface);
                    StageActivity.getActiveStageListener().addActor(showTextActor3);
                    userVariable.setVisible(true);
                } else {
                    showTextActor.updateProperties(strInterpretString2, fFloatValue, fFloatValue2, fFloatValue3, strInterpretString4, typeface);
                }
                return true;
            }
            Log.e("ShowTextFontAction", "Font file " + strInterpretString3 + " does not exist.");
            return true;
        } catch (InterpretationException e) {
            Log.d("ShowTextFontAction", "InterpretationException: " + e);
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

    public void setFile(Formula file) {
        this.file = file;
    }

    public void setName(Formula name2) {
        this.f552name = name2;
    }

    public void setAlignment(int alignment) {
        this.alignment = alignment;
    }

    public void setAndroidStringProvider(ShowTextUtils.AndroidStringProvider androidStringProvider) {
        this.androidStringProvider = androidStringProvider;
    }
}
