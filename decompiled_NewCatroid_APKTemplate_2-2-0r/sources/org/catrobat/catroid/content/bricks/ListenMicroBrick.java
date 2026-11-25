package org.catrobat.catroid.content.bricks;

import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.content.actions.ScriptSequenceAction;
import org.catrobat.catroid.formulaeditor.Formula;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/bricks/ListenMicroBrick.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class ListenMicroBrick extends FormulaBrick {
    private static final long serialVersionUID = 1;

    @Override // org.catrobat.catroid.content.bricks.BrickBaseType
    public int getViewResource() {
        return R.layout.brick_listen_micro;
    }

    public ListenMicroBrick() {
        addAllowedBrickField(Brick.BrickField.TIME, R.id.brick_listen_micro_edit_text);
    }

    public ListenMicroBrick(String value) {
        this(new Formula(value));
    }

    public ListenMicroBrick(Formula formula) {
        this();
        setFormulaWithBrickField(Brick.BrickField.TIME, formula);
    }

    @Override // org.catrobat.catroid.content.bricks.Brick
    public void addActionToSequence(Sprite sprite, ScriptSequenceAction sequence) {
        sequence.addAction(sprite.getActionFactory().createListenMicroAction(sprite, sequence, getFormulaWithBrickField(Brick.BrickField.TIME)));
    }
}
