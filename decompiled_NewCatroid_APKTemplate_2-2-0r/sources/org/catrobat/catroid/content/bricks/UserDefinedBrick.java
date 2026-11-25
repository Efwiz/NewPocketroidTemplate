package org.catrobat.catroid.content.bricks;

import android.content.Context;
import android.view.ContextThemeWrapper;
import android.view.View;
import android.widget.TextView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.content.actions.ScriptSequenceAction;
import org.catrobat.catroid.formulaeditor.Formula;
import org.catrobat.catroid.ui.BrickLayout;
import org.catrobat.catroid.ui.fragment.AddUserDataToUserDefinedBrickFragment;
import org.catrobat.catroid.ui.fragment.FormulaEditorFragment;
import org.catrobat.catroid.ui.recyclerview.util.UniqueNameProvider;
import org.catrobat.catroid.userbrick.InputFormulaField;
import org.catrobat.catroid.userbrick.UserDefinedBrickData;
import org.catrobat.catroid.userbrick.UserDefinedBrickInput;
import org.catrobat.catroid.userbrick.UserDefinedBrickLabel;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/bricks/UserDefinedBrick.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class UserDefinedBrick extends FormulaBrick {
    public static final String ADD_INPUT_OR_LABEL_BUNDLE_ARGUMENT = "addInputOrLabel";
    public static final String USER_BRICK_BUNDLE_ARGUMENT = "user_brick";
    private static final long serialVersionUID = 1;
    public transient TextView currentUserDefinedDataTextView;
    public transient BiMap<Brick.FormulaField, TextView> formulaFieldToTextViewMap;
    public transient int horizontalSpacing;

    @XStreamAlias("callingBrick")
    private boolean isCallingBrick;

    @XStreamAlias("userDefinedBrickDataList")
    private List<UserDefinedBrickData> userDefinedBrickDataList;

    @XStreamAlias("userDefinedBrickID")
    private UUID userDefinedBrickID;
    private transient BrickLayout userDefinedBrickLayout;

    @Override // org.catrobat.catroid.content.bricks.BrickBaseType
    public int getViewResource() {
        return R.layout.brick_user_brick;
    }

    public UserDefinedBrick() {
        this.formulaFieldToTextViewMap = HashBiMap.create(2);
        this.userDefinedBrickDataList = new ArrayList();
        this.userDefinedBrickID = UUID.randomUUID();
    }

    public UserDefinedBrick(UserDefinedBrick userDefinedBrick) {
        this.formulaFieldToTextViewMap = HashBiMap.create(2);
        copyUserDefinedDataList(userDefinedBrick);
        this.userDefinedBrickID = userDefinedBrick.getUserDefinedBrickID();
        this.isCallingBrick = userDefinedBrick.isCallingBrick;
    }

    public UserDefinedBrick(List<UserDefinedBrickData> userBrickDataList) {
        this.formulaFieldToTextViewMap = HashBiMap.create(2);
        this.userDefinedBrickDataList = userBrickDataList;
    }

    @Override // org.catrobat.catroid.content.bricks.FormulaBrick, org.catrobat.catroid.content.bricks.BrickBaseType
    public Brick clone() throws CloneNotSupportedException {
        UserDefinedBrick userDefinedBrick = (UserDefinedBrick) super.clone();
        userDefinedBrick.userDefinedBrickID = getUserDefinedBrickID();
        userDefinedBrick.isCallingBrick = this.isCallingBrick;
        return userDefinedBrick;
    }

    private void copyUserDefinedDataList(UserDefinedBrick userDefinedBrick) {
        this.userDefinedBrickDataList = new ArrayList();
        for (UserDefinedBrickData userDefinedBrickData : userDefinedBrick.getUserDefinedBrickDataList()) {
            if (userDefinedBrickData instanceof UserDefinedBrickInput) {
                UserDefinedBrickInput userDefinedBrickInput = (UserDefinedBrickInput) userDefinedBrickData;
                UserDefinedBrickInput userDefinedBrickInput2 = new UserDefinedBrickInput(userDefinedBrickInput);
                this.userDefinedBrickDataList.add(userDefinedBrickInput2);
                if (userDefinedBrick.isCallingBrick) {
                    copyFormulaOfInput(userDefinedBrickInput, userDefinedBrickInput2);
                }
            } else {
                this.userDefinedBrickDataList.add(new UserDefinedBrickLabel((UserDefinedBrickLabel) userDefinedBrickData));
            }
        }
    }

    private void copyFormulaOfInput(UserDefinedBrickInput oldInput, UserDefinedBrickInput newInput) {
        InputFormulaField inputFormulaField = oldInput.getInputFormulaField();
        Formula formula = this.formulaMap.get(inputFormulaField);
        this.formulaMap.remove(inputFormulaField);
        this.formulaMap.putIfAbsent(newInput.getInputFormulaField(), formula);
    }

    public void setCallingBrick(boolean callingBrick) {
        this.isCallingBrick = callingBrick;
    }

    public UUID getUserDefinedBrickID() {
        return this.userDefinedBrickID;
    }

    public void addLabel(String label) {
        removeLastLabel();
        this.userDefinedBrickDataList.add(new UserDefinedBrickLabel(label));
    }

    public void removeLastLabel() {
        if (lastContentIsLabel()) {
            List<UserDefinedBrickData> list = this.userDefinedBrickDataList;
            list.remove(list.size() - 1);
        }
    }

    public void addInput(String input) {
        this.userDefinedBrickDataList.add(new UserDefinedBrickInput(input));
    }

    public boolean isEmpty() {
        return this.userDefinedBrickDataList.isEmpty();
    }

    public List<UserDefinedBrickData> getUserDefinedBrickDataList() {
        return this.userDefinedBrickDataList;
    }

    public List<UserDefinedBrickInput> getUserDefinedBrickInputs() {
        ArrayList arrayList = new ArrayList();
        for (UserDefinedBrickData userDefinedBrickData : this.userDefinedBrickDataList) {
            if (userDefinedBrickData.isInput()) {
                arrayList.add((UserDefinedBrickInput) userDefinedBrickData);
            }
        }
        return arrayList;
    }

    public boolean containsInputs() {
        Iterator<UserDefinedBrickData> it = this.userDefinedBrickDataList.iterator();
        while (it.hasNext()) {
            if (it.next().isInput()) {
                return true;
            }
        }
        return false;
    }

    public List<String> getUserDataListAsStrings(UserDefinedBrickData.UserDefinedBrickDataType dataType) {
        ArrayList arrayList = new ArrayList();
        for (UserDefinedBrickData userDefinedBrickData : this.userDefinedBrickDataList) {
            if (userDefinedBrickData.getType() == dataType) {
                arrayList.add(userDefinedBrickData.getName());
            }
        }
        return arrayList;
    }

    private boolean lastContentIsLabel() {
        if (this.userDefinedBrickDataList.isEmpty()) {
            return false;
        }
        List<UserDefinedBrickData> list = this.userDefinedBrickDataList;
        return list.get(list.size() - 1).isLabel();
    }

    public boolean isUserDefinedBrickDataEqual(Brick brick) {
        if (!(brick instanceof UserDefinedBrick)) {
            return false;
        }
        UserDefinedBrick userDefinedBrick = (UserDefinedBrick) brick;
        if (this.userDefinedBrickDataList.size() != userDefinedBrick.userDefinedBrickDataList.size()) {
            return false;
        }
        for (int i = 0; i < this.userDefinedBrickDataList.size(); i++) {
            UserDefinedBrickData userDefinedBrickData = this.userDefinedBrickDataList.get(i);
            UserDefinedBrickData userDefinedBrickData2 = userDefinedBrick.userDefinedBrickDataList.get(i);
            if (!userDefinedBrickData.getClass().equals(userDefinedBrickData2.getClass())) {
                return false;
            }
            if ((userDefinedBrickData instanceof UserDefinedBrickLabel) && !userDefinedBrickData.equals(userDefinedBrickData2)) {
                return false;
            }
        }
        return true;
    }

    @Override // org.catrobat.catroid.content.bricks.FormulaBrick, org.catrobat.catroid.content.bricks.BrickBaseType, org.catrobat.catroid.content.bricks.Brick
    public View getView(Context context) {
        TextView textView;
        super.getView(context);
        this.userDefinedBrickLayout = (BrickLayout) this.view.findViewById(R.id.brick_user_brick);
        this.horizontalSpacing = context.getResources().getDimensionPixelOffset(R.dimen.material_design_spacing_small);
        TextView textView2 = null;
        for (UserDefinedBrickData userDefinedBrickData : this.userDefinedBrickDataList) {
            if (userDefinedBrickData.isInput()) {
                textView = new TextView(context, null, 0, R.style.BrickEditText);
            } else {
                textView = new TextView(new ContextThemeWrapper(context, R.style.BrickText));
            }
            textView.setText(userDefinedBrickData.getName());
            if (userDefinedBrickData.isInput() && this.isCallingBrick) {
                InputFormulaField inputFormulaField = ((UserDefinedBrickInput) userDefinedBrickData).getInputFormulaField();
                addAllowedBrickField(inputFormulaField, textView);
                textView.setText(getFormulaWithBrickField(inputFormulaField).getTrimmedFormulaString(context));
            }
            this.userDefinedBrickLayout.addView(textView);
            if (userDefinedBrickData.isInput()) {
                ((BrickLayout.LayoutParams) textView.getLayoutParams()).setEditText(true);
            }
            ((BrickLayout.LayoutParams) textView.getLayoutParams()).setHorizontalSpacing(this.horizontalSpacing);
            textView2 = textView;
        }
        Fragment fragmentFindFragmentById = ((FragmentActivity) context).getSupportFragmentManager().findFragmentById(R.id.fragment_container);
        if (fragmentFindFragmentById instanceof AddUserDataToUserDefinedBrickFragment) {
            addTextViewForUserData(context, textView2, ((AddUserDataToUserDefinedBrickFragment) fragmentFindFragmentById).getDataTypeToAdd());
        }
        return this.view;
    }

    private void addTextViewForUserData(Context context, TextView textView, UserDefinedBrickData.UserDefinedBrickDataType dataType) {
        String defaultText = getDefaultText(context, dataType);
        if (dataType == UserDefinedBrickData.UserDefinedBrickDataType.INPUT) {
            textView = new TextView(context, null, 0, R.style.BrickEditText);
            this.userDefinedBrickLayout.addView(textView);
            ((BrickLayout.LayoutParams) textView.getLayoutParams()).setEditText(true);
        } else if (lastContentIsLabel()) {
            List<UserDefinedBrickData> list = this.userDefinedBrickDataList;
            defaultText = list.get(list.size() - 1).getName();
        } else {
            textView = new TextView(new ContextThemeWrapper(context, R.style.BrickText));
            this.userDefinedBrickLayout.addView(textView);
        }
        ((BrickLayout.LayoutParams) textView.getLayoutParams()).setHorizontalSpacing(this.horizontalSpacing);
        textView.setText(defaultText);
        this.currentUserDefinedDataTextView = textView;
    }

    private String getDefaultText(Context context, UserDefinedBrickData.UserDefinedBrickDataType dataTypeToAdd) {
        String string;
        UniqueNameProvider uniqueNameProvider = new UniqueNameProvider();
        if (dataTypeToAdd == UserDefinedBrickData.UserDefinedBrickDataType.INPUT) {
            string = context.getResources().getString(R.string.brick_user_defined_default_input_name);
        } else {
            string = context.getResources().getString(R.string.brick_user_defined_default_label);
        }
        return uniqueNameProvider.getUniqueName(string, getUserDataListAsStrings(dataTypeToAdd));
    }

    protected void addAllowedBrickField(Brick.FormulaField formulaField, TextView textView) {
        this.formulaMap.putIfAbsent(formulaField, new Formula((Integer) 0));
        this.formulaFieldToTextViewMap.put(formulaField, textView);
    }

    private void updateUserDefinedBrickDataValues() {
        for (UserDefinedBrickData userDefinedBrickData : this.userDefinedBrickDataList) {
            if (userDefinedBrickData.isInput()) {
                UserDefinedBrickInput userDefinedBrickInput = (UserDefinedBrickInput) userDefinedBrickData;
                userDefinedBrickInput.setValue(getFormulaWithBrickField(userDefinedBrickInput.getInputFormulaField()));
            }
        }
    }

    @Override // org.catrobat.catroid.content.bricks.FormulaBrick
    public boolean hasEditableFormulaField() {
        return containsInputs();
    }

    @Override // org.catrobat.catroid.content.bricks.FormulaBrick
    public void setClickListeners() {
        Iterator<Map.Entry<Brick.FormulaField, TextView>> it = this.formulaFieldToTextViewMap.entrySet().iterator();
        while (it.hasNext()) {
            it.next().getValue().setOnClickListener(this);
        }
    }

    @Override // org.catrobat.catroid.content.bricks.FormulaBrick
    public void showFormulaEditorToEditFormula(View view) {
        if (this.formulaFieldToTextViewMap.inverse().containsKey(view)) {
            FormulaEditorFragment.showFragment(view.getContext(), this, getBrickFieldFromTextView((TextView) view));
        } else {
            FormulaEditorFragment.showFragment(view.getContext(), this, getDefaultBrickField());
        }
    }

    public Brick.FormulaField getBrickFieldFromTextView(TextView view) {
        return this.formulaFieldToTextViewMap.inverse().get(view);
    }

    @Override // org.catrobat.catroid.content.bricks.FormulaBrick
    public TextView getTextView(Brick.FormulaField formulaField) {
        return this.formulaFieldToTextViewMap.get(formulaField);
    }

    @Override // org.catrobat.catroid.content.bricks.Brick
    public void addActionToSequence(Sprite sprite, ScriptSequenceAction sequence) {
        updateUserDefinedBrickDataValues();
        sequence.addAction(sprite.getActionFactory().createUserBrickAction(sprite, sequence, getUserDefinedBrickInputs(), this.userDefinedBrickID));
    }
}
