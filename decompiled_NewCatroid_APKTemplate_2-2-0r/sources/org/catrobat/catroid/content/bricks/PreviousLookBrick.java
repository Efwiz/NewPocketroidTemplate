package org.catrobat.catroid.content.bricks;

import android.content.Context;
import android.view.View;
import android.widget.TextView;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.ProjectManager;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.content.actions.ScriptSequenceAction;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/bricks/PreviousLookBrick.class
  classes14.dex
 */
/* JADX INFO: compiled from: PreviousLookBrick.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0018\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bH\u0016J\u0010\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\fH\u0016J\b\u0010\r\u001a\u00020\u000eH\u0016¨\u0006\u000f"}, d2 = {"Lorg/catrobat/catroid/content/bricks/PreviousLookBrick;", "Lorg/catrobat/catroid/content/bricks/BrickBaseType;", "()V", "addActionToSequence", "", "sprite", "Lorg/catrobat/catroid/content/Sprite;", "sequence", "Lorg/catrobat/catroid/content/actions/ScriptSequenceAction;", "getView", "Landroid/view/View;", "context", "Landroid/content/Context;", "getViewResource", "", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class PreviousLookBrick extends BrickBaseType {
    @Override // org.catrobat.catroid.content.bricks.BrickBaseType
    public int getViewResource() {
        return R.layout.brick_previous_look;
    }

    @Override // org.catrobat.catroid.content.bricks.BrickBaseType, org.catrobat.catroid.content.bricks.Brick
    public View getView(Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        super.getView(context);
        if (ProjectManager.getInstance().getCurrentSprite().isBackgroundSprite(context)) {
            ((TextView) this.view.findViewById(R.id.brick_previous_look_text_view)).setText(R.string.brick_previous_background);
        }
        View view = this.view;
        Intrinsics.checkNotNullExpressionValue(view, "view");
        return view;
    }

    @Override // org.catrobat.catroid.content.bricks.Brick
    public void addActionToSequence(Sprite sprite, ScriptSequenceAction sequence) {
        Intrinsics.checkNotNullParameter(sprite, "sprite");
        Intrinsics.checkNotNullParameter(sequence, "sequence");
        sequence.addAction(sprite.getActionFactory().createSetPreviousLookAction(sprite, sequence));
    }
}
