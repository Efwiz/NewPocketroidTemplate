package org.catrobat.catroid.content.bricks;

import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.content.actions.ScriptSequenceAction;
import org.catrobat.catroid.formulaeditor.Formula;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/bricks/LoadPythonLibraryBrick.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class LoadPythonLibraryBrick extends FormulaBrick {
    private static final long serialVersionUID = 1;

    @Override // org.catrobat.catroid.content.bricks.BrickBaseType
    public int getViewResource() {
        return R.layout.brick_python_load_library;
    }

    public LoadPythonLibraryBrick() {
        addAllowedBrickField(Brick.BrickField.FILE_NAME, R.id.brick_python_library_file_name_text);
    }

    public LoadPythonLibraryBrick(String fileName) {
        this(new Formula(fileName));
    }

    public LoadPythonLibraryBrick(Formula fileName) {
        this();
        setFormulaWithBrickField(Brick.BrickField.FILE_NAME, fileName);
    }

    @Override // org.catrobat.catroid.content.bricks.Brick
    public void addActionToSequence(Sprite sprite, ScriptSequenceAction sequence) {
        sequence.addAction(sprite.getActionFactory().createLoadPythonLibraryAction(sprite, sequence, getFormulaWithBrickField(Brick.BrickField.FILE_NAME)));
    }
}
