package org.catrobat.catroid.content.bricks;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.content.actions.ScriptSequenceAction;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/bricks/UnknownBrick.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class UnknownBrick extends BrickBaseType {
    public static transient String lastUnknownClassName = "UnknownBrick";
    private static final long serialVersionUID = 1;
    private String unknownClassName = lastUnknownClassName;

    @Override // org.catrobat.catroid.content.bricks.Brick
    public void addActionToSequence(Sprite sprite, ScriptSequenceAction sequence) {
    }

    @Override // org.catrobat.catroid.content.bricks.BrickBaseType, org.catrobat.catroid.content.bricks.Brick
    public void addRequiredResources(Brick.ResourcesSet requiredResourcesSet) {
    }

    @Override // org.catrobat.catroid.content.bricks.BrickBaseType
    public int getViewResource() {
        return R.layout.brick_unknow;
    }

    @Override // org.catrobat.catroid.content.bricks.BrickBaseType, org.catrobat.catroid.content.bricks.Brick
    public View getView(Context context) {
        View view = super.getView(context);
        replaceLabelText(view);
        return view;
    }

    @Override // org.catrobat.catroid.content.bricks.BrickBaseType, org.catrobat.catroid.content.bricks.Brick
    public View getPrototypeView(Context context) {
        View prototypeView = super.getPrototypeView(context);
        replaceLabelText(prototypeView);
        return prototypeView;
    }

    private void replaceLabelText(View view) {
        TextView textViewFindFirstTextView = findFirstTextView(view);
        if (textViewFindFirstTextView != null) {
            textViewFindFirstTextView.setText("Неизвестный блок: " + getShortClassName(this.unknownClassName));
        }
    }

    private String getShortClassName(String fullName) {
        if (fullName == null) {
            return "Unknown";
        }
        int iLastIndexOf = fullName.lastIndexOf(46);
        return iLastIndexOf != -1 ? fullName.substring(iLastIndexOf + 1) : fullName;
    }

    private TextView findFirstTextView(View view) {
        if ((view instanceof TextView) && !(view instanceof EditText) && !(view instanceof CheckBox)) {
            return (TextView) view;
        }
        if (!(view instanceof ViewGroup)) {
            return null;
        }
        ViewGroup viewGroup = (ViewGroup) view;
        for (int i = 0; i < viewGroup.getChildCount(); i++) {
            TextView textViewFindFirstTextView = findFirstTextView(viewGroup.getChildAt(i));
            if (textViewFindFirstTextView != null) {
                return textViewFindFirstTextView;
            }
        }
        return null;
    }
}
