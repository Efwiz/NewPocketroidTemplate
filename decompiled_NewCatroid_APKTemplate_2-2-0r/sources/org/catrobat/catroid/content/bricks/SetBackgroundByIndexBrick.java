package org.catrobat.catroid.content.bricks;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.content.actions.ScriptSequenceAction;
import org.catrobat.catroid.formulaeditor.Formula;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/bricks/SetBackgroundByIndexBrick.class
  classes14.dex
 */
/* JADX INFO: compiled from: SetBackgroundByIndexBrick.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0016¢\u0006\u0002\u0010\u0002B\u000f\b\u0016\u0012\u0006\u0010\u0003\u001a\u00020\u0004¢\u0006\u0002\u0010\u0005B\u000f\b\u0016\u0012\u0006\u0010\u0006\u001a\u00020\u0007¢\u0006\u0002\u0010\bJ\u0018\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000eH\u0016J\b\u0010\u000f\u001a\u00020\u0004H\u0016¨\u0006\u0010"}, d2 = {"Lorg/catrobat/catroid/content/bricks/SetBackgroundByIndexBrick;", "Lorg/catrobat/catroid/content/bricks/FormulaBrick;", "()V", "index", "", "(I)V", "formula", "Lorg/catrobat/catroid/formulaeditor/Formula;", "(Lorg/catrobat/catroid/formulaeditor/Formula;)V", "addActionToSequence", "", "sprite", "Lorg/catrobat/catroid/content/Sprite;", "sequence", "Lorg/catrobat/catroid/content/actions/ScriptSequenceAction;", "getViewResource", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class SetBackgroundByIndexBrick extends FormulaBrick {
    @Override // org.catrobat.catroid.content.bricks.BrickBaseType
    public int getViewResource() {
        return R.layout.brick_set_background_by_index;
    }

    public SetBackgroundByIndexBrick() {
        addAllowedBrickField(Brick.BrickField.BACKGROUND_INDEX, R.id.brick_set_background_by_index_edit_text);
    }

    public SetBackgroundByIndexBrick(int i) {
        this(new Formula(Integer.valueOf(i)));
    }

    public SetBackgroundByIndexBrick(Formula formula) {
        Intrinsics.checkNotNullParameter(formula, "formula");
        addAllowedBrickField(Brick.BrickField.BACKGROUND_INDEX, R.id.brick_set_background_by_index_edit_text);
        setFormulaWithBrickField(Brick.BrickField.BACKGROUND_INDEX, formula);
    }

    @Override // org.catrobat.catroid.content.bricks.Brick
    public void addActionToSequence(Sprite sprite, ScriptSequenceAction sequence) {
        Intrinsics.checkNotNullParameter(sprite, "sprite");
        Intrinsics.checkNotNullParameter(sequence, "sequence");
        sequence.addAction(sprite.getActionFactory().createSetBackgroundByIndexAction(sprite, sequence, getFormulaWithBrickField(Brick.BrickField.BACKGROUND_INDEX), false));
    }
}
