package org.catrobat.catroid.content.bricks;

import android.util.Log;
import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.ProjectManager;
import org.catrobat.catroid.content.Scope;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.content.actions.ScriptSequenceAction;
import org.catrobat.catroid.formulaeditor.Formula;
import org.catrobat.catroid.formulaeditor.InterpretationException;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/bricks/SceneIdBrick.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class SceneIdBrick extends FormulaBrick {
    private static final long serialVersionUID = 1;

    @Override // org.catrobat.catroid.content.bricks.BrickBaseType
    public int getViewResource() {
        return R.layout.brick_scene_id;
    }

    public SceneIdBrick() {
        addAllowedBrickField(Brick.BrickField.SCENE_ID, R.id.brick_scene_id_edit_text);
    }

    public SceneIdBrick(String value) {
        this(new Formula(value));
    }

    public SceneIdBrick(Formula formula) {
        this();
        setFormulaWithBrickField(Brick.BrickField.SCENE_ID, formula);
    }

    @Override // org.catrobat.catroid.content.bricks.Brick
    public void addActionToSequence(Sprite sprite, ScriptSequenceAction sequence) {
        try {
            sequence.addAction(sprite.getActionFactory().createSceneIdAction(getFormulaWithBrickField(Brick.BrickField.SCENE_ID).interpretInteger(new Scope(ProjectManager.getInstance().getCurrentProject(), sprite, sequence)), sprite));
        } catch (InterpretationException e) {
            Log.e("SceneId", e.toString());
        }
    }
}
