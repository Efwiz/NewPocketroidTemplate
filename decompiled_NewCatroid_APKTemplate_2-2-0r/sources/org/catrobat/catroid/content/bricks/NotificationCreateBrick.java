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
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/bricks/NotificationCreateBrick.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class NotificationCreateBrick extends FormulaBrick {
    private static final long serialVersionUID = 1;
    private int importanceSelection;
    private boolean isOngoing;

    @Override // org.catrobat.catroid.content.bricks.BrickBaseType
    public int getViewResource() {
        return R.layout.brick_notification_create;
    }

    public NotificationCreateBrick() {
        this.importanceSelection = 1;
        this.isOngoing = false;
        addAllowedBrickField(Brick.BrickField.VALUE_1, R.id.brick_notif_create_id);
        addAllowedBrickField(Brick.BrickField.VALUE_2, R.id.brick_notif_create_channel);
        addAllowedBrickField(Brick.BrickField.VALUE_3, R.id.brick_notif_create_title);
        addAllowedBrickField(Brick.BrickField.VALUE_4, R.id.brick_notif_create_text);
        addAllowedBrickField(Brick.BrickField.VALUE_5, R.id.brick_notif_create_icon);
    }

    public NotificationCreateBrick(String id2, String channel, String title, String text, String iconFile) {
        this();
        setFormulaWithBrickField(Brick.BrickField.VALUE_1, new Formula(id2));
        setFormulaWithBrickField(Brick.BrickField.VALUE_2, new Formula(channel));
        setFormulaWithBrickField(Brick.BrickField.VALUE_3, new Formula(title));
        setFormulaWithBrickField(Brick.BrickField.VALUE_4, new Formula(text));
        setFormulaWithBrickField(Brick.BrickField.VALUE_5, new Formula(iconFile));
    }

    @Override // org.catrobat.catroid.content.bricks.FormulaBrick, org.catrobat.catroid.content.bricks.BrickBaseType, org.catrobat.catroid.content.bricks.Brick
    public View getView(Context context) {
        super.getView(context);
        Spinner spinner = (Spinner) this.view.findViewById(R.id.brick_notif_create_importance);
        ArrayAdapter<CharSequence> arrayAdapterCreateFromResource = ArrayAdapter.createFromResource(context, R.array.brick_notification_priorities, android.R.layout.simple_spinner_item);
        arrayAdapterCreateFromResource.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter((SpinnerAdapter) arrayAdapterCreateFromResource);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() { // from class: org.catrobat.catroid.content.bricks.NotificationCreateBrick.1
            @Override // android.widget.AdapterView.OnItemSelectedListener
            public void onNothingSelected(AdapterView<?> parent) {
            }

            @Override // android.widget.AdapterView.OnItemSelectedListener
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id2) {
                NotificationCreateBrick.this.importanceSelection = position;
            }
        });
        spinner.setSelection(this.importanceSelection);
        CheckBox checkBox = (CheckBox) this.view.findViewById(R.id.brick_notif_create_ongoing);
        checkBox.setChecked(this.isOngoing);
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() { // from class: org.catrobat.catroid.content.bricks.NotificationCreateBrick$$ExternalSyntheticLambda0
            @Override // android.widget.CompoundButton.OnCheckedChangeListener
            public final void onCheckedChanged(CompoundButton compoundButton, boolean z) {
                this.f$0.m4943x2eabbcfa(compoundButton, z);
            }
        });
        return this.view;
    }

    /* JADX INFO: renamed from: lambda$getView$0$org-catrobat-catroid-content-bricks-NotificationCreateBrick, reason: not valid java name */
    /* synthetic */ void m4943x2eabbcfa(CompoundButton compoundButton, boolean z) {
        this.isOngoing = z;
    }

    @Override // org.catrobat.catroid.content.bricks.Brick
    public void addActionToSequence(Sprite sprite, ScriptSequenceAction sequence) {
        sequence.addAction(sprite.getActionFactory().createNotificationCreateAction(sprite, sequence, getFormulaWithBrickField(Brick.BrickField.VALUE_1), getFormulaWithBrickField(Brick.BrickField.VALUE_2), getFormulaWithBrickField(Brick.BrickField.VALUE_3), getFormulaWithBrickField(Brick.BrickField.VALUE_4), getFormulaWithBrickField(Brick.BrickField.VALUE_5), this.importanceSelection, this.isOngoing));
    }
}
