package org.catrobat.catroid.content.bricks;

import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.content.actions.ScriptSequenceAction;
import org.catrobat.catroid.formulaeditor.Formula;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/bricks/AttachSOBrick.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class AttachSOBrick extends FormulaBrick {
    private static final long serialVersionUID = 1;

    @Override // org.catrobat.catroid.content.bricks.BrickBaseType
    public int getViewResource() {
        return R.layout.brick_gl_attach_so;
    }

    public AttachSOBrick() {
        addAllowedBrickField(Brick.BrickField.GL_VIEW_NAME, R.id.brick_gl_view_name);
        addAllowedBrickField(Brick.BrickField.FILE, R.id.brick_gl_so_name);
    }

    public AttachSOBrick(String name2, String file) {
        this(new Formula(name2), new Formula(file));
    }

    public AttachSOBrick(Formula name2, Formula file) {
        this();
        setFormulaWithBrickField(Brick.BrickField.GL_VIEW_NAME, name2);
        setFormulaWithBrickField(Brick.BrickField.FILE, file);
    }

    @Override // org.catrobat.catroid.content.bricks.Brick
    public void addActionToSequence(Sprite sprite, ScriptSequenceAction sequence) {
        sequence.addAction(sprite.getActionFactory().createAttachSOAction(sprite, sequence, getFormulaWithBrickField(Brick.BrickField.GL_VIEW_NAME), getFormulaWithBrickField(Brick.BrickField.FILE)));
    }
}
