package org.catrobat.catroid.content.bricks;

import android.content.Context;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.content.actions.ScriptSequenceAction;
import org.catrobat.catroid.formulaeditor.Formula;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/bricks/CameraTrackingBrick.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class CameraTrackingBrick extends FormulaBrick {
    private static final long serialVersionUID = 1;
    private int trackMode;

    @Override // org.catrobat.catroid.content.bricks.BrickBaseType
    public int getViewResource() {
        return R.layout.brick_camera_tracking;
    }

    public CameraTrackingBrick() {
        this.trackMode = 0;
        addAllowedBrickField(Brick.BrickField.NAME, R.id.brick_camera_track_id);
        addAllowedBrickField(Brick.BrickField.X_POSITION, R.id.brick_camera_track_px);
        addAllowedBrickField(Brick.BrickField.Y_POSITION, R.id.brick_camera_track_py);
        addAllowedBrickField(Brick.BrickField.Z_POSITION, R.id.brick_camera_track_pz);
        addAllowedBrickField(Brick.BrickField.YAW, R.id.brick_camera_track_ry);
        addAllowedBrickField(Brick.BrickField.PITCH, R.id.brick_camera_track_rp);
        addAllowedBrickField(Brick.BrickField.ROLL, R.id.brick_camera_track_rr);
    }

    public CameraTrackingBrick(String objectId, int mode, float px, float py, float pz, float ry, float rp, float rr) {
        this();
        setFormulaWithBrickField(Brick.BrickField.NAME, new Formula(objectId));
        this.trackMode = mode;
        setFormulaWithBrickField(Brick.BrickField.X_POSITION, new Formula(Float.valueOf(px)));
        setFormulaWithBrickField(Brick.BrickField.Y_POSITION, new Formula(Float.valueOf(py)));
        setFormulaWithBrickField(Brick.BrickField.Z_POSITION, new Formula(Float.valueOf(pz)));
        setFormulaWithBrickField(Brick.BrickField.YAW, new Formula(Float.valueOf(ry)));
        setFormulaWithBrickField(Brick.BrickField.PITCH, new Formula(Float.valueOf(rp)));
        setFormulaWithBrickField(Brick.BrickField.ROLL, new Formula(Float.valueOf(rr)));
    }

    @Override // org.catrobat.catroid.content.bricks.FormulaBrick, org.catrobat.catroid.content.bricks.BrickBaseType, org.catrobat.catroid.content.bricks.Brick
    public View getView(Context context) {
        super.getView(context);
        Spinner spinner = (Spinner) this.view.findViewById(R.id.brick_camera_track_spinner);
        ArrayAdapter<CharSequence> arrayAdapterCreateFromResource = ArrayAdapter.createFromResource(context, R.array.brick_camera_track_modes, android.R.layout.simple_spinner_item);
        arrayAdapterCreateFromResource.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter((SpinnerAdapter) arrayAdapterCreateFromResource);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() { // from class: org.catrobat.catroid.content.bricks.CameraTrackingBrick.1
            @Override // android.widget.AdapterView.OnItemSelectedListener
            public void onNothingSelected(AdapterView<?> parent) {
            }

            @Override // android.widget.AdapterView.OnItemSelectedListener
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id2) {
                CameraTrackingBrick.this.trackMode = position;
                CameraTrackingBrick.this.updateVisibility();
            }
        });
        spinner.setSelection(this.trackMode);
        updateVisibility();
        return this.view;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateVisibility() {
        LinearLayout linearLayout = (LinearLayout) this.view.findViewById(R.id.brick_camera_track_pos_layout);
        LinearLayout linearLayout2 = (LinearLayout) this.view.findViewById(R.id.brick_camera_track_rot_layout);
        int i = this.trackMode;
        linearLayout.setVisibility((i == 1 || i == 3) ? 0 : 8);
        int i2 = this.trackMode;
        linearLayout2.setVisibility((i2 == 2 || i2 == 3) ? 0 : 8);
    }

    @Override // org.catrobat.catroid.content.bricks.Brick
    public void addActionToSequence(Sprite sprite, ScriptSequenceAction sequence) {
        sequence.addAction(sprite.getActionFactory().createCameraTrackingAction(sprite, sequence, getFormulaWithBrickField(Brick.BrickField.NAME), this.trackMode, getFormulaWithBrickField(Brick.BrickField.X_POSITION), getFormulaWithBrickField(Brick.BrickField.Y_POSITION), getFormulaWithBrickField(Brick.BrickField.Z_POSITION), getFormulaWithBrickField(Brick.BrickField.YAW), getFormulaWithBrickField(Brick.BrickField.PITCH), getFormulaWithBrickField(Brick.BrickField.ROLL)));
    }
}
