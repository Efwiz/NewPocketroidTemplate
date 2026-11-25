package org.catrobat.catroid.content.bricks;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.View;
import android.widget.RadioButton;
import androidx.appcompat.app.AppCompatActivity;
import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.ProjectManager;
import org.catrobat.catroid.content.Project;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.content.bricks.brickspinner.BrickSpinner;
import org.catrobat.catroid.content.bricks.brickspinner.NewOption;
import org.catrobat.catroid.formulaeditor.UserData;
import org.catrobat.catroid.formulaeditor.UserList;
import org.catrobat.catroid.formulaeditor.UserVariable;
import org.catrobat.catroid.ui.UiUtils;
import org.catrobat.catroid.ui.recyclerview.dialog.TextInputDialog;
import org.catrobat.catroid.ui.recyclerview.dialog.textwatcher.DuplicateInputTextWatcher;
import org.catrobat.catroid.ui.recyclerview.fragment.ScriptFragment;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/bricks/UserDataBrick.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public abstract class UserDataBrick extends FormulaBrick implements BrickSpinner.OnItemSelectedListener<UserData> {
    public transient BiMap<Brick.BrickData, Integer> brickDataToTextViewIdMap = HashBiMap.create(2);
    protected UserDataHashMap userDataList = new UserDataHashMap();
    private transient HashMap<Brick.BrickData, BrickSpinner<UserData>> spinnerMap = new HashMap<>();

    @Override // org.catrobat.catroid.content.bricks.brickspinner.BrickSpinner.OnItemSelectedListener
    public void onEditOptionSelected(Integer spinnerId) {
    }

    @Override // org.catrobat.catroid.content.bricks.brickspinner.BrickSpinner.OnItemSelectedListener
    public void onStringOptionSelected(Integer spinnerId, String string) {
    }

    @Override // org.catrobat.catroid.content.bricks.FormulaBrick, org.catrobat.catroid.content.bricks.BrickBaseType
    public Brick clone() throws CloneNotSupportedException {
        UserDataBrick userDataBrick = (UserDataBrick) super.clone();
        userDataBrick.userDataList = this.userDataList.clone();
        userDataBrick.spinnerMap = new HashMap<>();
        return userDataBrick;
    }

    public Map<Brick.BrickData, UserData> getAllBrickDataWithValues() {
        return this.userDataList;
    }

    public UserList getUserListWithBrickData(Brick.BrickData brickData) {
        if (this.userDataList.containsKey(brickData)) {
            UserData userData = this.userDataList.get(brickData);
            if (userData instanceof UserList) {
                return (UserList) userData;
            }
            return null;
        }
        throw new IllegalArgumentException("Incompatible Brick data: " + getClass().getSimpleName() + " does not have BrickField." + brickData.toString());
    }

    public UserVariable getUserVariableWithBrickData(Brick.BrickData brickData) {
        if (this.userDataList.containsKey(brickData)) {
            UserData userData = this.userDataList.get(brickData);
            if (userData instanceof UserVariable) {
                return (UserVariable) userData;
            }
            return null;
        }
        throw new IllegalArgumentException("Incompatible Brick data: " + getClass().getSimpleName() + " does not have BrickField." + brickData.toString());
    }

    public HashMap<Brick.BrickData, UserData> getUserDataMap() {
        return this.userDataList;
    }

    protected void addAllowedBrickData(Brick.BrickData brickData, int textViewResourceId) {
        if (!this.userDataList.containsKey(brickData)) {
            this.userDataList.put(brickData, null);
        }
        this.brickDataToTextViewIdMap.put(brickData, Integer.valueOf(textViewResourceId));
    }

    public Brick.BrickData getBrickDataFromTextViewId(int textViewId) {
        return this.brickDataToTextViewIdMap.inverse().get(Integer.valueOf(textViewId));
    }

    @Override // org.catrobat.catroid.content.bricks.FormulaBrick, org.catrobat.catroid.content.bricks.BrickBaseType, org.catrobat.catroid.content.bricks.Brick
    public View getView(Context context) {
        BrickSpinner<UserData> brickSpinner;
        super.getView(context);
        Sprite currentSprite = ProjectManager.getInstance().getCurrentSprite();
        ArrayList arrayList = new ArrayList();
        arrayList.add(new NewOption(context.getString(R.string.new_option)));
        arrayList.addAll(currentSprite.getUserLists());
        arrayList.addAll(ProjectManager.getInstance().getCurrentProject().getUserLists());
        ArrayList arrayList2 = new ArrayList();
        arrayList2.add(new NewOption(context.getString(R.string.new_option)));
        arrayList2.addAll(currentSprite.getUserVariables());
        arrayList2.addAll(ProjectManager.getInstance().getCurrentProject().getUserVariables());
        for (Map.Entry<Brick.BrickData, UserData> entry : this.userDataList.entrySet()) {
            Integer num = this.brickDataToTextViewIdMap.get(entry.getKey());
            if (Brick.BrickData.isUserList(entry.getKey())) {
                brickSpinner = new BrickSpinner<>(num, this.view, arrayList);
            } else {
                brickSpinner = new BrickSpinner<>(num, this.view, arrayList2);
            }
            brickSpinner.setOnItemSelectedListener(this);
            brickSpinner.setSelection(entry.getValue());
            this.spinnerMap.put(entry.getKey(), brickSpinner);
        }
        return this.view;
    }

    @Override // org.catrobat.catroid.content.bricks.brickspinner.BrickSpinner.OnItemSelectedListener
    public void onNewOptionSelected(Integer spinnerId) {
        int i;
        int i2;
        final AppCompatActivity activityFromView = UiUtils.getActivityFromView(this.view);
        if (activityFromView == null) {
            return;
        }
        final Project currentProject = ProjectManager.getInstance().getCurrentProject();
        final Sprite currentSprite = ProjectManager.getInstance().getCurrentSprite();
        final Brick.BrickData brickDataFromTextViewId = getBrickDataFromTextViewId(spinnerId.intValue());
        if (Brick.BrickData.isUserList(getBrickDataFromTextViewId(spinnerId.intValue()))) {
            i = R.string.default_list_name;
            i2 = R.string.formula_editor_list_dialog_title;
        } else {
            i = R.string.default_variable_name;
            i2 = R.string.formula_editor_variable_dialog_title;
        }
        int i3 = i2;
        TextInputDialog.Builder builder = new TextInputDialog.Builder(activityFromView);
        builder.setHint(activityFromView.getString(R.string.data_label)).setTextWatcher(new DuplicateInputTextWatcher(this.spinnerMap.get(brickDataFromTextViewId).getItems())).setText(builder.createUniqueNameProvider(i).getUniqueName(activityFromView.getString(i), null)).setPositiveButton(activityFromView.getString(R.string.ok), new TextInputDialog.OnClickListener() { // from class: org.catrobat.catroid.content.bricks.UserDataBrick.1
            /* JADX WARN: Type inference fix 'apply assigned field type' failed
            java.lang.UnsupportedOperationException: ArgType.getObject(), call class: class jadx.core.dex.instructions.args.ArgType$UnknownArg
            	at jadx.core.dex.instructions.args.ArgType.getObject(ArgType.java:596)
            	at jadx.core.dex.attributes.nodes.ClassTypeVarsAttr.getTypeVarsMapFor(ClassTypeVarsAttr.java:35)
            	at jadx.core.dex.nodes.utils.TypeUtils.replaceClassGenerics(TypeUtils.java:177)
            	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.insertExplicitUseCast(FixTypesVisitor.java:397)
            	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.tryFieldTypeWithNewCasts(FixTypesVisitor.java:359)
            	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.applyFieldType(FixTypesVisitor.java:309)
            	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.visit(FixTypesVisitor.java:94)
             */
            @Override // org.catrobat.catroid.ui.recyclerview.dialog.TextInputDialog.OnClickListener
            public void onPositiveButtonClick(DialogInterface dialogInterface, String str) {
                Object obj;
                boolean zIsChecked = ((RadioButton) ((Dialog) dialogInterface).findViewById(R.id.global)).isChecked();
                boolean zIsUserList = Brick.BrickData.isUserList(brickDataFromTextViewId);
                if (zIsUserList) {
                    UserList userList = new UserList(str);
                    if (zIsChecked) {
                        currentProject.addUserList(userList);
                        obj = userList;
                    } else {
                        currentSprite.addUserList(userList);
                        obj = userList;
                    }
                } else {
                    UserVariable userVariable = new UserVariable(str);
                    if (zIsChecked) {
                        currentProject.addUserVariable(userVariable);
                        obj = userVariable;
                    } else {
                        currentSprite.addUserVariable(userVariable);
                        obj = userVariable;
                    }
                }
                for (Map.Entry entry : UserDataBrick.this.spinnerMap.entrySet()) {
                    if (Brick.BrickData.isUserList((Brick.BrickData) entry.getKey()) == zIsUserList) {
                        ((BrickSpinner) entry.getValue()).add(obj);
                    }
                }
                ((BrickSpinner) UserDataBrick.this.spinnerMap.get(brickDataFromTextViewId)).setSelection(obj);
                ScriptFragment scriptFragment = (ScriptFragment) activityFromView.getSupportFragmentManager().findFragmentByTag(ScriptFragment.TAG);
                if (scriptFragment != null) {
                    scriptFragment.notifyDataSetChanged();
                }
            }
        });
        builder.setTitle(i3).setView(R.layout.dialog_new_user_data).setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() { // from class: org.catrobat.catroid.content.bricks.UserDataBrick.3
            @Override // android.content.DialogInterface.OnClickListener
            public void onClick(DialogInterface dialog, int which) {
                ((BrickSpinner) UserDataBrick.this.spinnerMap.get(brickDataFromTextViewId)).setSelection(UserDataBrick.this.userDataList.get(brickDataFromTextViewId));
            }
        }).setOnCancelListener(new DialogInterface.OnCancelListener() { // from class: org.catrobat.catroid.content.bricks.UserDataBrick.2
            @Override // android.content.DialogInterface.OnCancelListener
            public void onCancel(DialogInterface dialog) {
                ((BrickSpinner) UserDataBrick.this.spinnerMap.get(brickDataFromTextViewId)).setSelection(UserDataBrick.this.userDataList.get(brickDataFromTextViewId));
            }
        }).show();
    }

    @Override // org.catrobat.catroid.content.bricks.brickspinner.BrickSpinner.OnItemSelectedListener
    public void onItemSelected(Integer spinnerId, UserData item) {
        this.userDataList.put(getBrickDataFromTextViewId(spinnerId.intValue()), item);
    }
}
