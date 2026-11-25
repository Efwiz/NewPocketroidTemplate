package org.catrobat.catroid.ui.dialogs;

import android.app.Dialog;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.widget.TextView;
import androidx.core.view.ViewCompat;
import java.util.LinkedList;
import java.util.Queue;
import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.ui.fragment.FormulaEditorFragment;
import org.catrobat.catroid.utils.SnackbarUtil;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/ui/dialogs/FormulaEditorIntroDialog.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class FormulaEditorIntroDialog extends Dialog implements View.OnClickListener {
    private static final int NONE = -1;
    private FormulaEditorFragment formulaEditorFragment;
    private Queue<IntroSlide> introSlides;
    private TextView introSummary;
    private TextView introTitle;

    public FormulaEditorIntroDialog(FormulaEditorFragment formulaEditorFragment, int style) {
        super(formulaEditorFragment.getActivity(), style);
        this.formulaEditorFragment = formulaEditorFragment;
        this.introSlides = getIntroDialogSlides();
    }

    @Override // android.app.Dialog
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setCanceledOnTouchOutside(false);
        setContentView(R.layout.dialog_formula_editor_intro);
        getWindow().setLayout(-1, -2);
        ColorDrawable colorDrawable = new ColorDrawable(ViewCompat.MEASURED_STATE_MASK);
        colorDrawable.setAlpha(200);
        getWindow().setBackgroundDrawable(colorDrawable);
        getWindow().setDimAmount(0.0f);
        this.introTitle = (TextView) findViewById(R.id.intro_dialog_title);
        this.introSummary = (TextView) findViewById(R.id.intro_dialog_summary);
        findViewById(R.id.intro_dialog_skip_button).setOnClickListener(this);
        findViewById(R.id.intro_dialog_next_button).setOnClickListener(this);
        nextSlide();
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.intro_dialog_next_button /* 2131364570 */:
                nextSlide();
                break;
            case R.id.intro_dialog_skip_button /* 2131364571 */:
                onBackPressed();
                break;
        }
    }

    private void nextSlide() {
        if (this.introSlides.isEmpty()) {
            onBackPressed();
        } else {
            this.introSlides.remove().applySlide();
        }
    }

    @Override // android.app.Dialog
    public void onBackPressed() {
        SnackbarUtil.setHintShown(this.formulaEditorFragment.getActivity(), this.formulaEditorFragment.getActivity().getResources().getResourceName(R.string.formula_editor_intro_title_formula_editor));
        super.onBackPressed();
    }

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/ui/dialogs/FormulaEditorIntroDialog$IntroSlide.class
      classes14.dex
     */
    private class IntroSlide {
        int summaryStringId;
        int targetViewId;
        int titleStringId;

        IntroSlide(int titleStringId, int summaryStringId, int targetViewId) {
            this.titleStringId = titleStringId;
            this.summaryStringId = summaryStringId;
            this.targetViewId = targetViewId;
        }

        void applySlide() {
            setText();
            if (this.targetViewId != -1) {
                setAnimation();
                setPosition();
            } else {
                FormulaEditorIntroDialog.this.getWindow().setGravity(17);
            }
        }

        private void setText() {
            FormulaEditorIntroDialog.this.introTitle.setText(this.titleStringId);
            FormulaEditorIntroDialog.this.introSummary.setText(this.summaryStringId);
        }

        private void setAnimation() {
            View viewFindViewById = FormulaEditorIntroDialog.this.formulaEditorFragment.getView().findViewById(this.targetViewId);
            AlphaAnimation alphaAnimation = new AlphaAnimation(0.1f, 1.0f);
            alphaAnimation.setDuration(1000L);
            alphaAnimation.setRepeatCount(2);
            alphaAnimation.setFillAfter(true);
            viewFindViewById.startAnimation(alphaAnimation);
        }

        private int getViewYCoordinates(int resourceId) {
            Rect rect = new Rect();
            Window window = FormulaEditorIntroDialog.this.getWindow();
            window.getDecorView().getWindowVisibleDisplayFrame(rect);
            int top = window.findViewById(android.R.id.content).getTop() - rect.top;
            int[] iArr = new int[2];
            FormulaEditorIntroDialog.this.formulaEditorFragment.getView().findViewById(resourceId).getLocationOnScreen(iArr);
            return iArr[1] + top;
        }

        private void setPosition() {
            int viewYCoordinates = getViewYCoordinates(R.id.formula_editor_keyboard_compute);
            int viewYCoordinates2 = getViewYCoordinates(this.targetViewId);
            int viewYCoordinates3 = getViewYCoordinates(R.id.formula_editor_brick_space);
            FormulaEditorIntroDialog.this.getWindow().setGravity(48);
            WindowManager.LayoutParams attributes = FormulaEditorIntroDialog.this.getWindow().getAttributes();
            if (viewYCoordinates2 >= viewYCoordinates) {
                attributes.y = viewYCoordinates3;
            } else {
                attributes.y = viewYCoordinates;
            }
            FormulaEditorIntroDialog.this.getWindow().setAttributes(attributes);
        }
    }

    private Queue<IntroSlide> getIntroDialogSlides() {
        LinkedList linkedList = new LinkedList();
        linkedList.add(new IntroSlide(R.string.formula_editor_intro_title_formula_editor, R.string.formula_editor_intro_summary_formula_editor, -1));
        linkedList.add(new IntroSlide(R.string.formula_editor_intro_title_input_field, R.string.formula_editor_intro_summary_input_field, R.id.formula_editor_edit_field));
        linkedList.add(new IntroSlide(R.string.formula_editor_intro_title_keyboard, R.string.formula_editor_intro_summary_keyboard, R.id.formula_editor_keyboard_7));
        linkedList.add(new IntroSlide(R.string.formula_editor_intro_title_compute, R.string.formula_editor_intro_summary_compute, R.id.formula_editor_keyboard_compute));
        linkedList.add(new IntroSlide(R.string.formula_editor_intro_title_object, R.string.formula_editor_intro_summary_object, R.id.formula_editor_keyboard_object));
        linkedList.add(new IntroSlide(R.string.formula_editor_intro_title_functions, R.string.formula_editor_intro_summary_functions, R.id.formula_editor_keyboard_function));
        linkedList.add(new IntroSlide(R.string.formula_editor_intro_title_logic, R.string.formula_editor_intro_summary_logic, R.id.formula_editor_keyboard_logic));
        linkedList.add(new IntroSlide(R.string.formula_editor_intro_title_device, R.string.formula_editor_intro_summary_device, R.id.formula_editor_keyboard_sensors));
        linkedList.add(new IntroSlide(R.string.formula_editor_intro_title_data, R.string.formula_editor_intro_summary_data, R.id.formula_editor_keyboard_data));
        return linkedList;
    }
}
