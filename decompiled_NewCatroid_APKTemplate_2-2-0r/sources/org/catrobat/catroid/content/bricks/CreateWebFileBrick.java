package org.catrobat.catroid.content.bricks;

import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.content.actions.ScriptSequenceAction;
import org.catrobat.catroid.formulaeditor.Formula;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/bricks/CreateWebFileBrick.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class CreateWebFileBrick extends FormulaBrick {
    private static final long serialVersionUID = 1;

    @Override // org.catrobat.catroid.content.bricks.BrickBaseType
    public int getViewResource() {
        return R.layout.brick_create_web_file;
    }

    public CreateWebFileBrick() {
        addAllowedBrickField(Brick.BrickField.NAME, R.id.create_web_file_name);
        addAllowedBrickField(Brick.BrickField.HTML, R.id.create_web_file_file);
        addAllowedBrickField(Brick.BrickField.POSX, R.id.create_web_file_x);
        addAllowedBrickField(Brick.BrickField.POSY, R.id.create_web_file_y);
        addAllowedBrickField(Brick.BrickField.WIDTH, R.id.create_web_file_width);
        addAllowedBrickField(Brick.BrickField.HEIGHT, R.id.create_web_file_height);
    }

    public CreateWebFileBrick(String name2, String url, String x, String y, String width, String height) {
        this(new Formula(name2), new Formula(url), new Formula(x), new Formula(y), new Formula(width), new Formula(height));
    }

    public CreateWebFileBrick(Formula name2, Formula url, Formula x, Formula y, Formula width, Formula height) {
        this();
        setFormulaWithBrickField(Brick.BrickField.NAME, name2);
        setFormulaWithBrickField(Brick.BrickField.HTML, url);
        setFormulaWithBrickField(Brick.BrickField.POSX, x);
        setFormulaWithBrickField(Brick.BrickField.POSY, y);
        setFormulaWithBrickField(Brick.BrickField.WIDTH, width);
        setFormulaWithBrickField(Brick.BrickField.HEIGHT, height);
    }

    @Override // org.catrobat.catroid.content.bricks.Brick
    public void addActionToSequence(Sprite sprite, ScriptSequenceAction sequence) {
        sequence.addAction(sprite.getActionFactory().createWebFileAction(sprite, sequence, getFormulaWithBrickField(Brick.BrickField.NAME), getFormulaWithBrickField(Brick.BrickField.HTML), getFormulaWithBrickField(Brick.BrickField.POSX), getFormulaWithBrickField(Brick.BrickField.POSY), getFormulaWithBrickField(Brick.BrickField.WIDTH), getFormulaWithBrickField(Brick.BrickField.HEIGHT)));
    }
}
