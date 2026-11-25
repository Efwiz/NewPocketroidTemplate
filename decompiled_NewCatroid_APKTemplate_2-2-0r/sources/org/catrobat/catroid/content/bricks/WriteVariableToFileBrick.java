package org.catrobat.catroid.content.bricks;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.content.actions.ScriptSequenceAction;
import org.catrobat.catroid.formulaeditor.Formula;
import org.catrobat.catroid.formulaeditor.UserVariable;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/bricks/WriteVariableToFileBrick.class
  classes14.dex
 */
/* JADX INFO: compiled from: WriteVariableToFileBrick.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u00004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\u000f\b\u0016\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004B\u000f\b\u0016\u0012\u0006\u0010\u0005\u001a\u00020\u0006¢\u0006\u0002\u0010\u0007B\u0005¢\u0006\u0002\u0010\bJ\u0018\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000eH\u0016J\b\u0010\u000f\u001a\u00020\u0010H\u0014J\b\u0010\u0011\u001a\u00020\u0010H\u0016¨\u0006\u0012"}, d2 = {"Lorg/catrobat/catroid/content/bricks/WriteVariableToFileBrick;", "Lorg/catrobat/catroid/content/bricks/UserVariableBrickWithFormula;", "value", "", "(Ljava/lang/String;)V", "formula", "Lorg/catrobat/catroid/formulaeditor/Formula;", "(Lorg/catrobat/catroid/formulaeditor/Formula;)V", "()V", "addActionToSequence", "", "sprite", "Lorg/catrobat/catroid/content/Sprite;", "sequence", "Lorg/catrobat/catroid/content/actions/ScriptSequenceAction;", "getSpinnerId", "", "getViewResource", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class WriteVariableToFileBrick extends UserVariableBrickWithFormula {
    @Override // org.catrobat.catroid.content.bricks.UserVariableBrickWithFormula
    protected int getSpinnerId() {
        return R.id.brick_write_variable_to_file_spinner;
    }

    @Override // org.catrobat.catroid.content.bricks.BrickBaseType
    public int getViewResource() {
        return R.layout.brick_write_variable_to_file;
    }

    public WriteVariableToFileBrick() {
        addAllowedBrickField(Brick.BrickField.WRITE_FILENAME, R.id.brick_write_variable_to_file_edit_text);
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public WriteVariableToFileBrick(String value) {
        this(new Formula(value));
        Intrinsics.checkNotNullParameter(value, "value");
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public WriteVariableToFileBrick(Formula formula) {
        this();
        Intrinsics.checkNotNullParameter(formula, "formula");
        setFormulaWithBrickField(Brick.BrickField.WRITE_FILENAME, formula);
    }

    @Override // org.catrobat.catroid.content.bricks.Brick
    public void addActionToSequence(Sprite sprite, ScriptSequenceAction sequence) {
        Intrinsics.checkNotNullParameter(sprite, "sprite");
        Intrinsics.checkNotNullParameter(sequence, "sequence");
        UserVariable userVariable = this.userVariable;
        if (userVariable == null || userVariable.getName() == null) {
            return;
        }
        sequence.addAction(sprite.getActionFactory().createWriteVariableToFileAction(sprite, sequence, getFormulaWithBrickField(Brick.BrickField.WRITE_FILENAME), this.userVariable));
    }
}
