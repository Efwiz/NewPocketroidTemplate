package org.catrobat.catroid.content.bricks;

import android.content.Context;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.content.actions.ScriptSequenceAction;
import org.catrobat.catroid.formulaeditor.Formula;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/bricks/NotificationAddButtonBrick.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class NotificationAddButtonBrick extends FormulaBrick {
    private static final long serialVersionUID = 1;
    private boolean autoClose;
    private int behavior;
    private boolean hasInput;

    @Override // org.catrobat.catroid.content.bricks.BrickBaseType
    public int getViewResource() {
        return R.layout.brick_notification_add_button;
    }

    public NotificationAddButtonBrick() {
        this.behavior = 0;
        this.hasInput = false;
        this.autoClose = false;
        addAllowedBrickField(Brick.BrickField.VALUE_1, R.id.brick_notif_btn_id);
        addAllowedBrickField(Brick.BrickField.VALUE_2, R.id.brick_notif_btn_action);
        addAllowedBrickField(Brick.BrickField.VALUE_3, R.id.brick_notif_btn_text);
        addAllowedBrickField(Brick.BrickField.VALUE_4, R.id.brick_notif_btn_icon);
        addAllowedBrickField(Brick.BrickField.VALUE_5, R.id.brick_notif_btn_hint);
    }

    public NotificationAddButtonBrick(String notifId, String actionId, String text, String icon, String hint) {
        this();
        setFormulaWithBrickField(Brick.BrickField.VALUE_1, new Formula(notifId));
        setFormulaWithBrickField(Brick.BrickField.VALUE_2, new Formula(actionId));
        setFormulaWithBrickField(Brick.BrickField.VALUE_3, new Formula(text));
        setFormulaWithBrickField(Brick.BrickField.VALUE_4, new Formula(icon));
        setFormulaWithBrickField(Brick.BrickField.VALUE_5, new Formula(hint));
    }

    @Override // org.catrobat.catroid.content.bricks.FormulaBrick, org.catrobat.catroid.content.bricks.BrickBaseType, org.catrobat.catroid.content.bricks.Brick
    public View getView(Context context) {
        super.getView(context);
        Spinner spinner = (Spinner) this.view.findViewById(R.id.brick_notif_btn_spinner_behavior);
        ArrayAdapter<CharSequence> arrayAdapterCreateFromResource = ArrayAdapter.createFromResource(context, R.array.brick_notification_behaviors, android.R.layout.simple_spinner_item);
        arrayAdapterCreateFromResource.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter((SpinnerAdapter) arrayAdapterCreateFromResource);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() { // from class: org.catrobat.catroid.content.bricks.NotificationAddButtonBrick.1
            @Override // android.widget.AdapterView.OnItemSelectedListener
            public void onNothingSelected(AdapterView<?> parent) {
            }

            @Override // android.widget.AdapterView.OnItemSelectedListener
            public void onItemSelected(AdapterView<?> parent, View v, int pos, long id2) {
                NotificationAddButtonBrick.this.behavior = pos;
            }
        });
        spinner.setSelection(this.behavior);
        final LinearLayout linearLayout = (LinearLayout) this.view.findViewById(R.id.brick_notif_btn_hint_layout);
        CheckBox checkBox = (CheckBox) this.view.findViewById(R.id.brick_notif_btn_checkbox_input);
        checkBox.setChecked(this.hasInput);
        linearLayout.setVisibility(this.hasInput ? 0 : 8);
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() { // from class: org.catrobat.catroid.content.bricks.NotificationAddButtonBrick$$ExternalSyntheticLambda0
            @Override // android.widget.CompoundButton.OnCheckedChangeListener
            public final void onCheckedChanged(CompoundButton compoundButton, boolean z) {
                this.f$0.m4940x9b632c59(linearLayout, compoundButton, z);
            }
        });
        CheckBox checkBox2 = (CheckBox) this.view.findViewById(R.id.brick_notif_btn_checkbox_close);
        checkBox2.setChecked(this.autoClose);
        checkBox2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() { // from class: org.catrobat.catroid.content.bricks.NotificationAddButtonBrick$$ExternalSyntheticLambda1
            @Override // android.widget.CompoundButton.OnCheckedChangeListener
            public final void onCheckedChanged(CompoundButton compoundButton, boolean z) {
                this.f$0.m4941x19c43038(compoundButton, z);
            }
        });
        return this.view;
    }

    /* JADX INFO: renamed from: lambda$getView$0$org-catrobat-catroid-content-bricks-NotificationAddButtonBrick, reason: not valid java name */
    /* synthetic */ void m4940x9b632c59(LinearLayout linearLayout, CompoundButton compoundButton, boolean z) {
        this.hasInput = z;
        linearLayout.setVisibility(z ? 0 : 8);
    }

    /* JADX INFO: renamed from: lambda$getView$1$org-catrobat-catroid-content-bricks-NotificationAddButtonBrick, reason: not valid java name */
    /* synthetic */ void m4941x19c43038(CompoundButton compoundButton, boolean z) {
        this.autoClose = z;
    }

    @Override // org.catrobat.catroid.content.bricks.Brick
    public void addActionToSequence(Sprite sprite, ScriptSequenceAction sequence) {
        sequence.addAction(sprite.getActionFactory().createNotificationAddButtonAction(sprite, sequence, getFormulaWithBrickField(Brick.BrickField.VALUE_1), getFormulaWithBrickField(Brick.BrickField.VALUE_2), getFormulaWithBrickField(Brick.BrickField.VALUE_3), getFormulaWithBrickField(Brick.BrickField.VALUE_4), getFormulaWithBrickField(Brick.BrickField.VALUE_5), this.behavior, this.hasInput, this.autoClose));
    }
}
