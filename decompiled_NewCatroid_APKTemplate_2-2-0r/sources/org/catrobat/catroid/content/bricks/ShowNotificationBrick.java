package org.catrobat.catroid.content.bricks;

import android.content.Context;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.content.actions.ScriptSequenceAction;
import org.catrobat.catroid.formulaeditor.Formula;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/bricks/ShowNotificationBrick.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class ShowNotificationBrick extends FormulaBrick {
    private static final long serialVersionUID = 1;
    private int importanceSelection;
    private boolean isOngoing;

    @Override // org.catrobat.catroid.content.bricks.BrickBaseType
    public int getViewResource() {
        return R.layout.brick_show_notification;
    }

    public ShowNotificationBrick() {
        this.importanceSelection = 1;
        this.isOngoing = false;
        addAllowedBrickField(Brick.BrickField.VALUE_1, R.id.brick_notification_edit_id);
        addAllowedBrickField(Brick.BrickField.VALUE_2, R.id.brick_notification_edit_channel);
        addAllowedBrickField(Brick.BrickField.VALUE_3, R.id.brick_notification_edit_title);
        addAllowedBrickField(Brick.BrickField.VALUE_4, R.id.brick_notification_edit_text);
        addAllowedBrickField(Brick.BrickField.VALUE_5, R.id.brick_notification_edit_large_icon);
    }

    public ShowNotificationBrick(String id2, String channel, String title, String text, String largeIcon) {
        this();
        setFormulaWithBrickField(Brick.BrickField.VALUE_1, new Formula(id2));
        setFormulaWithBrickField(Brick.BrickField.VALUE_2, new Formula(channel));
        setFormulaWithBrickField(Brick.BrickField.VALUE_3, new Formula(title));
        setFormulaWithBrickField(Brick.BrickField.VALUE_4, new Formula(text));
        setFormulaWithBrickField(Brick.BrickField.VALUE_5, new Formula(largeIcon));
    }

    @Override // org.catrobat.catroid.content.bricks.FormulaBrick, org.catrobat.catroid.content.bricks.BrickBaseType, org.catrobat.catroid.content.bricks.Brick
    public View getView(Context context) {
        super.getView(context);
        Spinner spinner = (Spinner) this.view.findViewById(R.id.brick_notification_spinner_importance);
        ArrayAdapter<CharSequence> arrayAdapterCreateFromResource = ArrayAdapter.createFromResource(context, R.array.brick_notification_priorities, android.R.layout.simple_spinner_item);
        arrayAdapterCreateFromResource.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter((SpinnerAdapter) arrayAdapterCreateFromResource);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() { // from class: org.catrobat.catroid.content.bricks.ShowNotificationBrick.1
            @Override // android.widget.AdapterView.OnItemSelectedListener
            public void onNothingSelected(AdapterView<?> parent) {
            }

            @Override // android.widget.AdapterView.OnItemSelectedListener
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id2) {
                ShowNotificationBrick.this.importanceSelection = position;
            }
        });
        spinner.setSelection(this.importanceSelection);
        CheckBox checkBox = (CheckBox) this.view.findViewById(R.id.brick_notification_checkbox_ongoing);
        checkBox.setChecked(this.isOngoing);
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() { // from class: org.catrobat.catroid.content.bricks.ShowNotificationBrick$$ExternalSyntheticLambda0
            @Override // android.widget.CompoundButton.OnCheckedChangeListener
            public final void onCheckedChanged(CompoundButton compoundButton, boolean z) {
                this.f$0.m4978xa2dc32d9(compoundButton, z);
            }
        });
        return this.view;
    }

    /* JADX INFO: renamed from: lambda$getView$0$org-catrobat-catroid-content-bricks-ShowNotificationBrick, reason: not valid java name */
    /* synthetic */ void m4978xa2dc32d9(CompoundButton compoundButton, boolean z) {
        this.isOngoing = z;
    }

    @Override // org.catrobat.catroid.content.bricks.Brick
    public void addActionToSequence(Sprite sprite, ScriptSequenceAction sequence) {
        sequence.addAction(sprite.getActionFactory().createShowNotificationAction(sprite, sequence, getFormulaWithBrickField(Brick.BrickField.VALUE_1), getFormulaWithBrickField(Brick.BrickField.VALUE_2), getFormulaWithBrickField(Brick.BrickField.VALUE_3), getFormulaWithBrickField(Brick.BrickField.VALUE_4), this.importanceSelection, getFormulaWithBrickField(Brick.BrickField.VALUE_5), this.isOngoing));
    }
}
