package org.catrobat.catroid.content.bricks;

import android.content.Context;
import android.view.View;
import android.widget.TextView;
import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.content.actions.ScriptSequenceAction;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/bricks/SetBackgroundAndWaitBrick.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class SetBackgroundAndWaitBrick extends SetBackgroundBrick {
    private static final long serialVersionUID = 1;

    @Override // org.catrobat.catroid.content.bricks.SetBackgroundBrick, org.catrobat.catroid.content.bricks.BrickBaseType, org.catrobat.catroid.content.bricks.Brick
    public View getView(Context context) {
        View view = super.getView(context);
        ((TextView) view.findViewById(R.id.brick_set_background_text_view)).setText(R.string.brick_set_background_and_wait);
        return view;
    }

    @Override // org.catrobat.catroid.content.bricks.SetBackgroundBrick, org.catrobat.catroid.content.bricks.Brick
    public void addActionToSequence(Sprite sprite, ScriptSequenceAction sequence) {
        sequence.addAction(sprite.getActionFactory().createSetBackgroundAction(this.look, true));
    }
}
