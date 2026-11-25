package org.catrobat.catroid.content.bricks;

import android.content.Context;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.content.AdapterViewOnItemSelectedListenerImpl;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.content.actions.ScriptSequenceAction;
import org.catrobat.catroid.formulaeditor.Formula;
import org.catrobat.catroid.formulaeditor.UserVariable;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/bricks/ReadVariableFromFileBrick.class
  classes14.dex
 */
/* JADX INFO: compiled from: ReadVariableFromFileBrick.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000H\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u0000 \u00192\u00020\u0001:\u0001\u0019B\u000f\b\u0016\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004B\u000f\b\u0016\u0012\u0006\u0010\u0005\u001a\u00020\u0006¢\u0006\u0002\u0010\u0007B\u0005¢\u0006\u0002\u0010\bJ\u0018\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u0010H\u0016J\u0018\u0010\u0011\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00030\u00122\u0006\u0010\u0013\u001a\u00020\u0014H\u0002J\b\u0010\u0015\u001a\u00020\nH\u0014J\u0010\u0010\u0016\u001a\u00020\u00172\u0006\u0010\u0013\u001a\u00020\u0014H\u0016J\b\u0010\u0018\u001a\u00020\nH\u0016R\u000e\u0010\t\u001a\u00020\nX\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006\u001a"}, d2 = {"Lorg/catrobat/catroid/content/bricks/ReadVariableFromFileBrick;", "Lorg/catrobat/catroid/content/bricks/UserVariableBrickWithFormula;", "value", "", "(Ljava/lang/String;)V", "formula", "Lorg/catrobat/catroid/formulaeditor/Formula;", "(Lorg/catrobat/catroid/formulaeditor/Formula;)V", "()V", "spinnerSelectionID", "", "addActionToSequence", "", "sprite", "Lorg/catrobat/catroid/content/Sprite;", "sequence", "Lorg/catrobat/catroid/content/actions/ScriptSequenceAction;", "createArrayAdapter", "Landroid/widget/ArrayAdapter;", "context", "Landroid/content/Context;", "getSpinnerId", "getView", "Landroid/view/View;", "getViewResource", "Mode", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ReadVariableFromFileBrick extends UserVariableBrickWithFormula {
    private static final int DELETE = 1;
    private static final int KEEP = 0;
    private int spinnerSelectionID;

    @Override // org.catrobat.catroid.content.bricks.UserVariableBrickWithFormula
    protected int getSpinnerId() {
        return R.id.brick_read_variable_from_file_spinner_variable;
    }

    @Override // org.catrobat.catroid.content.bricks.BrickBaseType
    public int getViewResource() {
        return R.layout.brick_read_variable_from_file;
    }

    public ReadVariableFromFileBrick() {
        addAllowedBrickField(Brick.BrickField.READ_FILENAME, R.id.brick_read_variable_from_file_edit_text);
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public ReadVariableFromFileBrick(String value) {
        this(new Formula(value));
        Intrinsics.checkNotNullParameter(value, "value");
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public ReadVariableFromFileBrick(Formula formula) {
        this();
        Intrinsics.checkNotNullParameter(formula, "formula");
        setFormulaWithBrickField(Brick.BrickField.READ_FILENAME, formula);
    }

    @Override // org.catrobat.catroid.content.bricks.UserVariableBrickWithFormula, org.catrobat.catroid.content.bricks.FormulaBrick, org.catrobat.catroid.content.bricks.BrickBaseType, org.catrobat.catroid.content.bricks.Brick
    public View getView(Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        super.getView(context);
        Spinner spinner = (Spinner) this.view.findViewById(R.id.brick_read_variable_from_file_spinner_mode);
        spinner.setAdapter((SpinnerAdapter) createArrayAdapter(context));
        spinner.setOnItemSelectedListener(new AdapterViewOnItemSelectedListenerImpl(new Function1<Integer, Unit>() { // from class: org.catrobat.catroid.content.bricks.ReadVariableFromFileBrick$getView$1$1
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(Integer num) {
                invoke(num.intValue());
                return Unit.INSTANCE;
            }

            public final void invoke(int i) {
                this.this$0.spinnerSelectionID = i;
            }
        }));
        spinner.setSelection(this.spinnerSelectionID);
        View view = this.view;
        Intrinsics.checkNotNullExpressionValue(view, "view");
        return view;
    }

    private final ArrayAdapter<String> createArrayAdapter(Context context) {
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_item, new String[]{context.getString(R.string.brick_read_variable_from_file_keep), context.getString(R.string.brick_read_variable_from_file_delete)});
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        return arrayAdapter;
    }

    @Override // org.catrobat.catroid.content.bricks.Brick
    public void addActionToSequence(Sprite sprite, ScriptSequenceAction sequence) {
        Intrinsics.checkNotNullParameter(sprite, "sprite");
        Intrinsics.checkNotNullParameter(sequence, "sequence");
        UserVariable userVariable = this.userVariable;
        if (userVariable == null || userVariable.getName() == null) {
            return;
        }
        sequence.addAction(sprite.getActionFactory().createReadVariableFromFileAction(sprite, sequence, getFormulaWithBrickField(Brick.BrickField.READ_FILENAME), this.userVariable, this.spinnerSelectionID == 1));
    }
}
