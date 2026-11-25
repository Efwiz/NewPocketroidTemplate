package org.catrobat.catroid.ui.fragment;

import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import java.io.Serializable;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.content.bricks.UserDefinedBrick;
import org.catrobat.catroid.userbrick.UserDefinedBrickData;
import org.catrobat.catroid.utils.Utils;
import org.eclipse.jdt.internal.compiler.impl.CompilerOptions;
import org.jetbrains.kotlin.org.jline.reader.LineReader;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/ui/fragment/AddUserDataToUserDefinedBrickFragment.class
  classes14.dex
 */
/* JADX INFO: compiled from: AddUserDataToUserDefinedBrickFragment.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000r\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0006\u0018\u0000 )2\u00020\u0001:\u0001)B\u0005¢\u0006\u0002\u0010\u0002J\b\u0010\u0011\u001a\u0004\u0018\u00010\bJ\u0010\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\u0015H\u0016J\u0018\u0010\u0016\u001a\u00020\u00132\u0006\u0010\u0017\u001a\u00020\u00182\u0006\u0010\u0019\u001a\u00020\u001aH\u0016J&\u0010\u001b\u001a\u0004\u0018\u00010\u001c2\u0006\u0010\u0019\u001a\u00020\u001d2\b\u0010\u001e\u001a\u0004\u0018\u00010\u001f2\b\u0010 \u001a\u0004\u0018\u00010!H\u0016J\b\u0010\"\u001a\u00020\u0013H\u0016J\u0010\u0010#\u001a\u00020$2\u0006\u0010%\u001a\u00020\nH\u0016J\u0010\u0010&\u001a\u00020\u00132\u0006\u0010\u0017\u001a\u00020\u0018H\u0016J\u0010\u0010'\u001a\u00020\u00132\u0006\u0010(\u001a\u00020$H\u0002R\u0010\u0010\u0003\u001a\u0004\u0018\u00010\u0004X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u0005\u001a\u0004\u0018\u00010\u0006X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u0007\u001a\u0004\u0018\u00010\bX\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\t\u001a\u0004\u0018\u00010\nX\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u000b\u001a\u0004\u0018\u00010\fX\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\r\u001a\u0004\u0018\u00010\u000eX\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u000f\u001a\u0004\u0018\u00010\u0010X\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006*"}, d2 = {"Lorg/catrobat/catroid/ui/fragment/AddUserDataToUserDefinedBrickFragment;", "Landroidx/fragment/app/Fragment;", "()V", "addUserDataUserBrickEditText", "Lcom/google/android/material/textfield/TextInputEditText;", "addUserDataUserBrickTextLayout", "Lcom/google/android/material/textfield/TextInputLayout;", "dataTypeToAdd", "Lorg/catrobat/catroid/userbrick/UserDefinedBrickData$UserDefinedBrickDataType;", "nextItem", "Landroid/view/MenuItem;", "scrollView", "Landroid/widget/ScrollView;", "userBrickTextView", "Landroid/widget/TextView;", "userDefinedBrick", "Lorg/catrobat/catroid/content/bricks/UserDefinedBrick;", "getDataTypeToAdd", "onAttach", "", "context", "Landroid/content/Context;", "onCreateOptionsMenu", LineReader.MENU, "Landroid/view/Menu;", "inflater", "Landroid/view/MenuInflater;", "onCreateView", "Landroid/view/View;", "Landroid/view/LayoutInflater;", "container", "Landroid/view/ViewGroup;", "savedInstanceState", "Landroid/os/Bundle;", "onDetach", "onOptionsItemSelected", "", "item", "onPrepareOptionsMenu", "setNextItemEnabled", CompilerOptions.ENABLED, "Companion", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class AddUserDataToUserDefinedBrickFragment extends Fragment {
    public static final String TAG = "add_user_data_to_user_defined_brick_fragment";
    private TextInputEditText addUserDataUserBrickEditText;
    private TextInputLayout addUserDataUserBrickTextLayout;
    private UserDefinedBrickData.UserDefinedBrickDataType dataTypeToAdd;
    private MenuItem nextItem;
    private ScrollView scrollView;
    private TextView userBrickTextView;
    private UserDefinedBrick userDefinedBrick;

    @Override // androidx.fragment.app.Fragment
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ActionBar supportActionBar;
        Resources resources;
        Resources resources2;
        Intrinsics.checkNotNullParameter(inflater, "inflater");
        View viewInflate = inflater.inflate(R.layout.fragment_add_user_data_to_user_brick, container, false);
        LinearLayout linearLayout = (LinearLayout) viewInflate.findViewById(R.id.user_brick_space);
        this.scrollView = (ScrollView) viewInflate.findViewById(R.id.fragment_add_user_data_to_user_brick);
        Bundle arguments = getArguments();
        if (arguments != null) {
            Serializable serializable = arguments.getSerializable(UserDefinedBrick.USER_BRICK_BUNDLE_ARGUMENT);
            Intrinsics.checkNotNull(serializable, "null cannot be cast to non-null type org.catrobat.catroid.content.bricks.UserDefinedBrick");
            this.userDefinedBrick = (UserDefinedBrick) serializable;
            Serializable serializable2 = arguments.getSerializable(UserDefinedBrick.ADD_INPUT_OR_LABEL_BUNDLE_ARGUMENT);
            Intrinsics.checkNotNull(serializable2, "null cannot be cast to non-null type org.catrobat.catroid.userbrick.UserDefinedBrickData.UserDefinedBrickDataType");
            this.dataTypeToAdd = (UserDefinedBrickData.UserDefinedBrickDataType) serializable2;
        }
        UserDefinedBrick userDefinedBrick = this.userDefinedBrick;
        String string = null;
        if (userDefinedBrick != null) {
            linearLayout.addView(userDefinedBrick != null ? userDefinedBrick.getView(requireActivity()) : null);
            UserDefinedBrick userDefinedBrick2 = this.userDefinedBrick;
            this.userBrickTextView = userDefinedBrick2 != null ? userDefinedBrick2.currentUserDefinedDataTextView : null;
        }
        this.addUserDataUserBrickEditText = (TextInputEditText) viewInflate.findViewById(R.id.user_data_user_brick_edit_field);
        this.addUserDataUserBrickTextLayout = (TextInputLayout) viewInflate.findViewById(R.id.user_data_user_brick_text_layout);
        TextView textView = (TextView) viewInflate.findViewById(R.id.brick_user_defined_add_user_data_description);
        TextInputEditText textInputEditText = this.addUserDataUserBrickEditText;
        if (textInputEditText != null) {
            TextView textView2 = this.userBrickTextView;
            textInputEditText.setText(textView2 != null ? textView2.getText() : null);
        }
        AddUserDataToUserDefinedBrickFragment$onCreateView$textWatcher$1 addUserDataToUserDefinedBrickFragment$onCreateView$textWatcher$1 = new AddUserDataToUserDefinedBrickFragment$onCreateView$textWatcher$1(this);
        if (getContext() != null) {
            UserDefinedBrick userDefinedBrick3 = this.userDefinedBrick;
            addUserDataToUserDefinedBrickFragment$onCreateView$textWatcher$1.setScope(userDefinedBrick3 != null ? userDefinedBrick3.getUserDataListAsStrings(UserDefinedBrickData.UserDefinedBrickDataType.INPUT) : null);
            TextInputEditText textInputEditText2 = this.addUserDataUserBrickEditText;
            if (textInputEditText2 != null) {
                textInputEditText2.addTextChangedListener(addUserDataToUserDefinedBrickFragment$onCreateView$textWatcher$1);
            }
            if (this.dataTypeToAdd == UserDefinedBrickData.UserDefinedBrickDataType.INPUT) {
                Context context = getContext();
                if (context != null && (resources2 = context.getResources()) != null) {
                    string = resources2.getString(R.string.brick_user_defined_add_input_description);
                }
                textView.setText(string);
            } else {
                Context context2 = getContext();
                if (context2 != null && (resources = context2.getResources()) != null) {
                    string = resources.getString(R.string.brick_user_defined_add_label_description);
                }
                textView.setText(string);
            }
        }
        FragmentActivity fragmentActivityRequireActivity = requireActivity();
        Intrinsics.checkNotNull(fragmentActivityRequireActivity, "null cannot be cast to non-null type androidx.appcompat.app.AppCompatActivity");
        AppCompatActivity appCompatActivity = (AppCompatActivity) fragmentActivityRequireActivity;
        Utils.showStandardSystemKeyboard(appCompatActivity);
        if (appCompatActivity.getSupportActionBar() != null && (supportActionBar = appCompatActivity.getSupportActionBar()) != null) {
            supportActionBar.setTitle(R.string.category_user_bricks);
        }
        ((LinearLayout) viewInflate.findViewById(R.id.bottom_constraint)).requestFocus();
        setHasOptionsMenu(true);
        return viewInflate;
    }

    @Override // androidx.fragment.app.Fragment
    public void onAttach(Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        super.onAttach(context);
        Utils.showStandardSystemKeyboard(requireActivity());
    }

    @Override // androidx.fragment.app.Fragment
    public void onDetach() {
        super.onDetach();
        Utils.hideStandardSystemKeyboard(requireActivity());
    }

    @Override // androidx.fragment.app.Fragment
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        Intrinsics.checkNotNullParameter(menu, "menu");
        Intrinsics.checkNotNullParameter(inflater, "inflater");
        inflater.inflate(R.menu.menu_next, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override // androidx.fragment.app.Fragment
    public void onPrepareOptionsMenu(Menu menu) {
        Intrinsics.checkNotNullParameter(menu, "menu");
        int size = menu.size();
        for (int i = 0; i < size; i++) {
            menu.getItem(i).setVisible(false);
        }
        MenuItem menuItemFindItem = menu.findItem(R.id.next);
        this.nextItem = menuItemFindItem;
        if (menuItemFindItem != null) {
            menuItemFindItem.setVisible(true);
        }
        MenuItem menuItem = this.nextItem;
        if (menuItem != null) {
            menuItem.setEnabled(true);
        }
        super.onPrepareOptionsMenu(menu);
    }

    @Override // androidx.fragment.app.Fragment
    public boolean onOptionsItemSelected(MenuItem item) {
        Intrinsics.checkNotNullParameter(item, "item");
        if (item.getItemId() == R.id.next) {
            FragmentManager parentFragmentManager = getParentFragmentManager();
            Intrinsics.checkNotNullExpressionValue(parentFragmentManager, "getParentFragmentManager(...)");
            Utils.hideStandardSystemKeyboard(requireActivity());
            Fragment fragmentFindFragmentByTag = parentFragmentManager.findFragmentByTag(AddUserDefinedBrickFragment.TAG);
            Intrinsics.checkNotNull(fragmentFindFragmentByTag, "null cannot be cast to non-null type org.catrobat.catroid.ui.fragment.AddUserDefinedBrickFragment");
            AddUserDefinedBrickFragment addUserDefinedBrickFragment = (AddUserDefinedBrickFragment) fragmentFindFragmentByTag;
            parentFragmentManager.popBackStackImmediate();
            TextInputEditText textInputEditText = this.addUserDataUserBrickEditText;
            if ((textInputEditText != null ? textInputEditText.getText() : null) != null) {
                TextInputEditText textInputEditText2 = this.addUserDataUserBrickEditText;
                addUserDefinedBrickFragment.addUserDataToUserBrick(String.valueOf(textInputEditText2 != null ? textInputEditText2.getText() : null), this.dataTypeToAdd);
            }
        }
        return super.onOptionsItemSelected(item);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void setNextItemEnabled(boolean enabled) {
        MenuItem menuItem = this.nextItem;
        if (menuItem == null) {
            return;
        }
        menuItem.setEnabled(enabled);
    }

    public final UserDefinedBrickData.UserDefinedBrickDataType getDataTypeToAdd() {
        return this.dataTypeToAdd;
    }
}
