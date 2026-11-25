package org.catrobat.catroid.content.bricks;

import android.content.Context;
import android.view.View;
import java.util.ArrayList;
import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.common.Nameable;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.content.actions.ScriptSequenceAction;
import org.catrobat.catroid.content.bricks.brickspinner.BrickSpinner;
import org.catrobat.catroid.formulaeditor.Formula;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/bricks/SetNfcTagBrick.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class SetNfcTagBrick extends FormulaBrick implements BrickSpinner.OnItemSelectedListener<NfcTypeOption> {
    private static final long serialVersionUID = 1;
    private int nfcTagNdefType;

    @Override // org.catrobat.catroid.content.bricks.BrickBaseType
    public int getViewResource() {
        return R.layout.brick_set_nfc_tag;
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

    public SetNfcTagBrick() {
        this.nfcTagNdefType = 2;
        addAllowedBrickField(Brick.BrickField.NFC_NDEF_MESSAGE, R.id.brick_set_nfc_tag_edit_text);
    }

    public SetNfcTagBrick(String messageString) {
        this(new Formula(messageString));
    }

    public SetNfcTagBrick(Formula messageFormula) {
        this();
        setFormulaWithBrickField(Brick.BrickField.NFC_NDEF_MESSAGE, messageFormula);
    }

    @Override // org.catrobat.catroid.content.bricks.FormulaBrick, org.catrobat.catroid.content.bricks.BrickBaseType, org.catrobat.catroid.content.bricks.Brick
    public View getView(Context context) {
        super.getView(context);
        ArrayList arrayList = new ArrayList();
        arrayList.add(new NfcTypeOption(context.getString(R.string.tnf_mime_media), 0));
        arrayList.add(new NfcTypeOption(context.getString(R.string.tnf_well_known_http), 1));
        arrayList.add(new NfcTypeOption(context.getString(R.string.tnf_well_known_https), 2));
        arrayList.add(new NfcTypeOption(context.getString(R.string.tnf_well_known_sms), 3));
        arrayList.add(new NfcTypeOption(context.getString(R.string.tnf_well_known_tel), 4));
        arrayList.add(new NfcTypeOption(context.getString(R.string.tnf_well_known_mailto), 5));
        arrayList.add(new NfcTypeOption(context.getString(R.string.tnf_external_type), 6));
        arrayList.add(new NfcTypeOption(context.getString(R.string.tnf_empty), 7));
        BrickSpinner brickSpinner = new BrickSpinner(Integer.valueOf(R.id.brick_set_nfc_tag_ndef_record_spinner), this.view, arrayList);
        brickSpinner.setOnItemSelectedListener(this);
        brickSpinner.setSelection(this.nfcTagNdefType);
        return this.view;
    }

    @Override // org.catrobat.catroid.content.bricks.brickspinner.BrickSpinner.OnItemSelectedListener
    public void onItemSelected(Integer spinnerId, NfcTypeOption item) {
        this.nfcTagNdefType = item.getNfcTagNdefType();
    }

    @Override // org.catrobat.catroid.content.bricks.FormulaBrick, org.catrobat.catroid.content.bricks.BrickBaseType, org.catrobat.catroid.content.bricks.Brick
    public void addRequiredResources(final Brick.ResourcesSet requiredResourcesSet) {
        requiredResourcesSet.add((Integer) 16);
        super.addRequiredResources(requiredResourcesSet);
    }

    @Override // org.catrobat.catroid.content.bricks.Brick
    public void addActionToSequence(Sprite sprite, ScriptSequenceAction sequence) {
        sequence.addAction(sprite.getActionFactory().createSetNfcTagAction(sprite, sequence, getFormulaWithBrickField(Brick.BrickField.NFC_NDEF_MESSAGE), this.nfcTagNdefType));
    }

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/bricks/SetNfcTagBrick$NfcTypeOption.class
      classes14.dex
     */
    class NfcTypeOption implements Nameable {

        /* JADX INFO: renamed from: name, reason: collision with root package name */
        private String f560name;
        private int nfcTagNdefType;

        NfcTypeOption(String name2, int nfcTagNdefType) {
            this.f560name = name2;
            this.nfcTagNdefType = nfcTagNdefType;
        }

        @Override // org.catrobat.catroid.common.Nameable
        public String getName() {
            return this.f560name;
        }

        @Override // org.catrobat.catroid.common.Nameable
        public void setName(String name2) {
            this.f560name = name2;
        }

        int getNfcTagNdefType() {
            return this.nfcTagNdefType;
        }
    }
}
