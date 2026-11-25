package org.catrobat.catroid.ui.fragment;

import android.content.Context;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;
import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.content.bricks.Brick;
import org.catrobat.catroid.content.bricks.FormulaBrick;
import org.catrobat.catroid.formulaeditor.Formula;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/ui/fragment/SingleSeekBar.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class SingleSeekBar {
    private Brick.BrickField brickField;
    private FormulaBrick formulaBrick;
    private int seekBarTitleId;
    private TextView valueTextView;

    public SingleSeekBar(FormulaBrick formulaBrick, Brick.BrickField brickField, int seekBarTitleId) {
        this.formulaBrick = formulaBrick;
        this.brickField = brickField;
        this.seekBarTitleId = seekBarTitleId;
    }

    public View getView(final Context context) {
        View viewInflate = View.inflate(context, R.layout.single_seek_bar_view, null);
        viewInflate.setFocusableInTouchMode(true);
        viewInflate.requestFocus();
        ((TextView) viewInflate.findViewById(R.id.single_seekbar_title)).setText(this.seekBarTitleId);
        TextView textView = (TextView) viewInflate.findViewById(R.id.single_seekbar_value);
        this.valueTextView = textView;
        textView.setOnClickListener(new View.OnClickListener() { // from class: org.catrobat.catroid.ui.fragment.SingleSeekBar$$ExternalSyntheticLambda0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.m5737lambda$getView$0$orgcatrobatcatroiduifragmentSingleSeekBar(context, view);
            }
        });
        SeekBar seekBar = (SeekBar) viewInflate.findViewById(R.id.single_seekbar_seekbar);
        seekBar.setProgress(Double.valueOf(this.formulaBrick.getFormulaWithBrickField(this.brickField).getTrimmedFormulaString(context).replace(",", ".")).intValue());
        this.valueTextView.setText(String.valueOf(seekBar.getProgress()));
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() { // from class: org.catrobat.catroid.ui.fragment.SingleSeekBar.1
            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onStartTrackingTouch(SeekBar seekBar2) {
            }

            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onProgressChanged(SeekBar seekBar2, int i, boolean b) {
                SingleSeekBar.this.valueTextView.setText(String.valueOf(seekBar2.getProgress()));
            }

            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onStopTrackingTouch(SeekBar seekBar2) {
                SingleSeekBar.this.formulaBrick.setFormulaWithBrickField(SingleSeekBar.this.brickField, new Formula(Integer.valueOf(seekBar2.getProgress())));
            }
        });
        return viewInflate;
    }

    /* JADX INFO: renamed from: lambda$getView$0$org-catrobat-catroid-ui-fragment-SingleSeekBar, reason: not valid java name */
    /* synthetic */ void m5737lambda$getView$0$orgcatrobatcatroiduifragmentSingleSeekBar(Context context, View view) {
        FormulaEditorFragment.showFragment(context, this.formulaBrick, this.brickField);
    }
}
