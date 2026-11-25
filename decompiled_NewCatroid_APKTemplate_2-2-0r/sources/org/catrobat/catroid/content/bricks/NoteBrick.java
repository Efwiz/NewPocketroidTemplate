package org.catrobat.catroid.content.bricks;

import android.view.View;
import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.content.actions.ScriptSequenceAction;
import org.catrobat.catroid.formulaeditor.Formula;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/bricks/NoteBrick.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class NoteBrick extends FormulaBrick implements View.OnClickListener {
    private static final long serialVersionUID = 1;

    @Override // org.catrobat.catroid.content.bricks.Brick
    public void addActionToSequence(Sprite sprite, ScriptSequenceAction sequence) {
    }

    @Override // org.catrobat.catroid.content.bricks.BrickBaseType
    public int getViewResource() {
        return R.layout.brick_note;
    }

    public NoteBrick() {
        addAllowedBrickField(Brick.BrickField.NOTE, R.id.brick_note_edit_text);
    }

    public NoteBrick(String note) {
        this(new Formula(note));
    }

    public NoteBrick(Formula formula) {
        this();
        setFormulaWithBrickField(Brick.BrickField.NOTE, formula);
    }
}
