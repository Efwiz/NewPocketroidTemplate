package org.catrobat.catroid.content.bricks;

import android.content.Context;
import android.view.View;
import java.util.ArrayList;
import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.common.Nameable;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.content.actions.ScriptSequenceAction;
import org.catrobat.catroid.content.bricks.brickspinner.BrickSpinner;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/bricks/SetRotationStyleBrick.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class SetRotationStyleBrick extends BrickBaseType implements BrickSpinner.OnItemSelectedListener<RotationStyleOption> {
    private static final long serialVersionUID = 1;
    private int selection;

    @Override // org.catrobat.catroid.content.bricks.BrickBaseType
    public int getViewResource() {
        return R.layout.brick_set_rotation_style;
    }

    @Override // org.catrobat.catroid.content.bricks.brickspinner.BrickSpinner.OnItemSelectedListener
    public void onEditOptionSelected(Integer spinnerId) {
    }

    @Override // org.catrobat.catroid.content.bricks.brickspinner.BrickSpinner.OnItemSelectedListener
    public void onNewOptionSelected(Integer spinnerId) {
    }

    @Override // org.catrobat.catroid.content.bricks.brickspinner.BrickSpinner.OnItemSelectedListener
    public void onStringOptionSelected(Integer spinnerId, String string) {
    }

    @Override // org.catrobat.catroid.content.bricks.BrickBaseType, org.catrobat.catroid.content.bricks.Brick
    public View getView(Context context) {
        super.getView(context);
        ArrayList arrayList = new ArrayList();
        arrayList.add(new RotationStyleOption(context.getString(R.string.brick_set_rotation_style_lr), 0));
        arrayList.add(new RotationStyleOption(context.getString(R.string.brick_set_rotation_style_normal), 1));
        arrayList.add(new RotationStyleOption(context.getString(R.string.brick_set_rotation_style_no), 2));
        BrickSpinner brickSpinner = new BrickSpinner(Integer.valueOf(R.id.brick_set_rotation_style_spinner), this.view, arrayList);
        brickSpinner.setOnItemSelectedListener(this);
        brickSpinner.setSelection(this.selection);
        return this.view;
    }

    @Override // org.catrobat.catroid.content.bricks.Brick
    public void addActionToSequence(Sprite sprite, ScriptSequenceAction sequence) {
        sequence.addAction(sprite.getActionFactory().createSetRotationStyleAction(sprite, this.selection));
    }

    @Override // org.catrobat.catroid.content.bricks.brickspinner.BrickSpinner.OnItemSelectedListener
    public void onItemSelected(Integer spinnerId, RotationStyleOption item) {
        this.selection = item != null ? item.getRotationStyle() : 0;
    }

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/bricks/SetRotationStyleBrick$RotationStyleOption.class
      classes14.dex
     */
    class RotationStyleOption implements Nameable {

        /* JADX INFO: renamed from: name, reason: collision with root package name */
        private String f561name;
        private int rotationStyle;

        RotationStyleOption(String name2, int rotationStyle) {
            this.f561name = name2;
            this.rotationStyle = rotationStyle;
        }

        @Override // org.catrobat.catroid.common.Nameable
        public String getName() {
            return this.f561name;
        }

        @Override // org.catrobat.catroid.common.Nameable
        public void setName(String name2) {
            this.f561name = name2;
        }

        int getRotationStyle() {
            return this.rotationStyle;
        }
    }
}
