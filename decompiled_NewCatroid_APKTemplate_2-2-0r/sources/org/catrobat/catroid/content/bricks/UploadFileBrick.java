package org.catrobat.catroid.content.bricks;

import android.content.Context;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.content.actions.ScriptSequenceAction;
import org.catrobat.catroid.formulaeditor.Formula;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/bricks/UploadFileBrick.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class UploadFileBrick extends FormulaBrick {
    private static final long serialVersionUID = 1;
    private int fileTypeSelection;
    private int storageTypeSelection;

    @Override // org.catrobat.catroid.content.bricks.BrickBaseType
    public int getViewResource() {
        return R.layout.brick_upload_file;
    }

    public UploadFileBrick() {
        this.fileTypeSelection = 0;
        this.storageTypeSelection = 0;
        addAllowedBrickField(Brick.BrickField.URL, R.id.brick_upload_url_edit);
        addAllowedBrickField(Brick.BrickField.FILE, R.id.brick_upload_file_path_edit);
        addAllowedBrickField(Brick.BrickField.TEXT, R.id.brick_upload_mime_type_edit);
    }

    public UploadFileBrick(Formula url, Formula filePath, int fileType, Formula mimeType, int storageType) {
        this();
        setFormulaWithBrickField(Brick.BrickField.URL, url);
        setFormulaWithBrickField(Brick.BrickField.FILE, filePath);
        setFormulaWithBrickField(Brick.BrickField.TEXT, mimeType);
        this.fileTypeSelection = fileType;
        this.storageTypeSelection = storageType;
    }

    @Override // org.catrobat.catroid.content.bricks.FormulaBrick, org.catrobat.catroid.content.bricks.BrickBaseType, org.catrobat.catroid.content.bricks.Brick
    public View getView(Context context) {
        super.getView(context);
        Spinner spinner = (Spinner) this.view.findViewById(R.id.brick_upload_file_type_spinner);
        ArrayAdapter<CharSequence> arrayAdapterCreateFromResource = ArrayAdapter.createFromResource(context, R.array.upload_file_types, android.R.layout.simple_spinner_item);
        arrayAdapterCreateFromResource.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter((SpinnerAdapter) arrayAdapterCreateFromResource);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() { // from class: org.catrobat.catroid.content.bricks.UploadFileBrick.1
            @Override // android.widget.AdapterView.OnItemSelectedListener
            public void onNothingSelected(AdapterView<?> parent) {
            }

            @Override // android.widget.AdapterView.OnItemSelectedListener
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id2) {
                UploadFileBrick.this.fileTypeSelection = position;
            }
        });
        spinner.setSelection(this.fileTypeSelection);
        Spinner spinner2 = (Spinner) this.view.findViewById(R.id.brick_upload_storage_type_spinner);
        ArrayAdapter<CharSequence> arrayAdapterCreateFromResource2 = ArrayAdapter.createFromResource(context, R.array.upload_storage_types, android.R.layout.simple_spinner_item);
        arrayAdapterCreateFromResource2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner2.setAdapter((SpinnerAdapter) arrayAdapterCreateFromResource2);
        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() { // from class: org.catrobat.catroid.content.bricks.UploadFileBrick.2
            @Override // android.widget.AdapterView.OnItemSelectedListener
            public void onNothingSelected(AdapterView<?> parent) {
            }

            @Override // android.widget.AdapterView.OnItemSelectedListener
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id2) {
                UploadFileBrick.this.storageTypeSelection = position;
            }
        });
        spinner2.setSelection(this.storageTypeSelection);
        return this.view;
    }

    @Override // org.catrobat.catroid.content.bricks.Brick
    public void addActionToSequence(Sprite sprite, ScriptSequenceAction sequence) {
        sequence.addAction(sprite.getActionFactory().createUploadFileAction(sprite, sequence, getFormulaWithBrickField(Brick.BrickField.URL), getFormulaWithBrickField(Brick.BrickField.FILE), this.fileTypeSelection, getFormulaWithBrickField(Brick.BrickField.TEXT), this.storageTypeSelection));
    }
}
