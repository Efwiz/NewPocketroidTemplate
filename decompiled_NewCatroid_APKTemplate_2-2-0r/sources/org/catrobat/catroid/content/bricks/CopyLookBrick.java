package org.catrobat.catroid.content.bricks;

import android.content.Context;
import android.view.View;
import android.widget.TextView;
import com.badlogic.gdx.scenes.scene2d.Action;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.ProjectManager;
import org.catrobat.catroid.content.ActionFactory;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.content.actions.ScriptSequenceAction;
import org.catrobat.catroid.content.actions.SetNextLookAction;
import org.catrobat.catroid.formulaeditor.Formula;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/bricks/CopyLookBrick.class
  classes14.dex
 */
/* JADX INFO: compiled from: CopyLookBrick.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000>\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\u0018\u00002\u00020\u0001B\u000f\b\u0016\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004B\u000f\b\u0016\u0012\u0006\u0010\u0005\u001a\u00020\u0006¢\u0006\u0002\u0010\u0007B\u0005¢\u0006\u0002\u0010\bJ\u0018\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000eH\u0016J\u0010\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u0012H\u0016J\b\u0010\u0013\u001a\u00020\u0014H\u0016¨\u0006\u0015"}, d2 = {"Lorg/catrobat/catroid/content/bricks/CopyLookBrick;", "Lorg/catrobat/catroid/content/bricks/FormulaBrick;", "value", "", "(Ljava/lang/String;)V", "formula", "Lorg/catrobat/catroid/formulaeditor/Formula;", "(Lorg/catrobat/catroid/formulaeditor/Formula;)V", "()V", "addActionToSequence", "", "sprite", "Lorg/catrobat/catroid/content/Sprite;", "sequence", "Lorg/catrobat/catroid/content/actions/ScriptSequenceAction;", "getView", "Landroid/view/View;", "context", "Landroid/content/Context;", "getViewResource", "", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class CopyLookBrick extends FormulaBrick {
    @Override // org.catrobat.catroid.content.bricks.BrickBaseType
    public int getViewResource() {
        return R.layout.brick_copy_look;
    }

    public CopyLookBrick() {
        addAllowedBrickField(Brick.BrickField.LOOK_COPY, R.id.brick_copy_look_edit_text);
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public CopyLookBrick(String value) {
        this(new Formula(value));
        Intrinsics.checkNotNullParameter(value, "value");
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public CopyLookBrick(Formula formula) {
        this();
        Intrinsics.checkNotNullParameter(formula, "formula");
        setFormulaWithBrickField(Brick.BrickField.LOOK_COPY, formula);
    }

    @Override // org.catrobat.catroid.content.bricks.FormulaBrick, org.catrobat.catroid.content.bricks.BrickBaseType, org.catrobat.catroid.content.bricks.Brick
    public View getView(Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        super.getView(context);
        if (ProjectManager.getInstance().getCurrentSprite().isBackgroundSprite(context)) {
            ((TextView) this.view.findViewById(R.id.brick_copy_look_text_view)).setText(R.string.brick_copy_background);
        }
        View view = this.view;
        Intrinsics.checkNotNullExpressionValue(view, "view");
        return view;
    }

    @Override // org.catrobat.catroid.content.bricks.Brick
    public void addActionToSequence(Sprite sprite, ScriptSequenceAction sequence) {
        Intrinsics.checkNotNullParameter(sprite, "sprite");
        Intrinsics.checkNotNullParameter(sequence, "sequence");
        ScriptSequenceAction scriptSequenceAction = sequence;
        Action actionCreateSetNextLookAction = sprite.getActionFactory().createSetNextLookAction(sprite, scriptSequenceAction);
        Formula formulaWithBrickField = getFormulaWithBrickField(Brick.BrickField.LOOK_COPY);
        ActionFactory actionFactory = sprite.getActionFactory();
        Intrinsics.checkNotNull(actionCreateSetNextLookAction, "null cannot be cast to non-null type org.catrobat.catroid.content.actions.SetNextLookAction");
        sequence.addAction(actionFactory.createCopyLookAction(sprite, scriptSequenceAction, formulaWithBrickField, (SetNextLookAction) actionCreateSetNextLookAction));
        sequence.addAction(actionCreateSetNextLookAction);
    }
}
