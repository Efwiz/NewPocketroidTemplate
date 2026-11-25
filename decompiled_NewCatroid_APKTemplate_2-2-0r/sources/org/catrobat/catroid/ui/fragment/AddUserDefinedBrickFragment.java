package org.catrobat.catroid.ui.fragment;

import android.content.ComponentCallbacks;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import java.io.Serializable;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.LazyThreadSafetyMode;
import kotlin.Metadata;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Reflection;
import net.bytebuddy.implementation.auxiliary.TypeProxy;
import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.ProjectManager;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.content.bricks.UserDefinedBrick;
import org.catrobat.catroid.content.bricks.UserDefinedReceiverBrick;
import org.catrobat.catroid.userbrick.UserDefinedBrickData;
import org.catrobat.catroid.utils.ToastUtil;
import org.jetbrains.kotlin.org.jline.reader.LineReader;
import org.koin.android.ext.android.ComponentCallbackExtKt;
import org.koin.core.qualifier.Qualifier;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/ui/fragment/AddUserDefinedBrickFragment.class
  classes14.dex
 */
/* JADX INFO: compiled from: AddUserDefinedBrickFragment.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000|\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0005\u0018\u0000 22\u00020\u0001:\u00012B\u0005¢\u0006\u0002\u0010\u0002J\u001a\u0010\u0018\u001a\u00020\u00192\b\u0010\u001a\u001a\u0004\u0018\u00010\u001b2\b\u0010\u001c\u001a\u0004\u0018\u00010\u001dJ\u0012\u0010\u001e\u001a\u00020\u00192\b\u0010\u001f\u001a\u0004\u0018\u00010\u0017H\u0002J\u0010\u0010 \u001a\u00020\u00192\u0006\u0010\u001c\u001a\u00020\u001dH\u0002J\u0018\u0010!\u001a\u00020\u00192\u0006\u0010\"\u001a\u00020#2\u0006\u0010$\u001a\u00020%H\u0016J&\u0010&\u001a\u0004\u0018\u00010\u00152\u0006\u0010$\u001a\u00020'2\b\u0010(\u001a\u0004\u0018\u00010)2\b\u0010*\u001a\u0004\u0018\u00010+H\u0016J\b\u0010,\u001a\u00020\u0019H\u0016J\u0010\u0010-\u001a\u00020.2\u0006\u0010/\u001a\u00020\tH\u0016J\u0010\u00100\u001a\u00020\u00192\u0006\u0010\"\u001a\u00020#H\u0016J\b\u00101\u001a\u00020\u0019H\u0002R\u0010\u0010\u0003\u001a\u0004\u0018\u00010\u0004X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u0005\u001a\u0004\u0018\u00010\u0006X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u0007\u001a\u0004\u0018\u00010\u0006X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\b\u001a\u0004\u0018\u00010\tX\u0082\u000e¢\u0006\u0002\n\u0000R\u001b\u0010\n\u001a\u00020\u000b8BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\u000e\u0010\u000f\u001a\u0004\b\f\u0010\rR\u0010\u0010\u0010\u001a\u0004\u0018\u00010\u0011X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u0012\u001a\u0004\u0018\u00010\u0013X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u0014\u001a\u0004\u0018\u00010\u0015X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u0016\u001a\u0004\u0018\u00010\u0017X\u0082\u000e¢\u0006\u0002\n\u0000¨\u00063"}, d2 = {"Lorg/catrobat/catroid/ui/fragment/AddUserDefinedBrickFragment;", "Landroidx/fragment/app/Fragment;", "()V", "addBrickListener", "Lorg/catrobat/catroid/ui/fragment/AddBrickFragment$OnAddBrickListener;", "addInput", "Landroid/widget/Button;", "addLabel", "confirmItem", "Landroid/view/MenuItem;", "projectManager", "Lorg/catrobat/catroid/ProjectManager;", "getProjectManager", "()Lorg/catrobat/catroid/ProjectManager;", "projectManager$delegate", "Lkotlin/Lazy;", "scrollView", "Landroid/widget/ScrollView;", "userBrickSpace", "Landroid/widget/LinearLayout;", "userBrickView", "Landroid/view/View;", "userDefinedBrick", "Lorg/catrobat/catroid/content/bricks/UserDefinedBrick;", "addUserDataToUserBrick", "", "input", "", "dataType", "Lorg/catrobat/catroid/userbrick/UserDefinedBrickData$UserDefinedBrickDataType;", "addUserDefinedScriptToScript", "brickToAddScript", "handleAddUserDefinedBrickData", "onCreateOptionsMenu", LineReader.MENU, "Landroid/view/Menu;", "inflater", "Landroid/view/MenuInflater;", "onCreateView", "Landroid/view/LayoutInflater;", "container", "Landroid/view/ViewGroup;", "savedInstanceState", "Landroid/os/Bundle;", "onDestroyView", "onOptionsItemSelected", "", "item", "onPrepareOptionsMenu", "updateBrickView", "Companion", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class AddUserDefinedBrickFragment extends Fragment {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    public static final String TAG = "add_user_defined_brick_fragment";
    private AddBrickFragment.OnAddBrickListener addBrickListener;
    private Button addInput;
    private Button addLabel;
    private MenuItem confirmItem;

    /* JADX INFO: renamed from: projectManager$delegate, reason: from kotlin metadata */
    private final Lazy projectManager;
    private ScrollView scrollView;
    private LinearLayout userBrickSpace;
    private View userBrickView;
    private UserDefinedBrick userDefinedBrick;

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/ui/fragment/AddUserDefinedBrickFragment$Companion.class
      classes14.dex
     */
    /* JADX INFO: compiled from: AddUserDefinedBrickFragment.kt */
    @Metadata(d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u000e\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bR\u000e\u0010\u0003\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000¨\u0006\t"}, d2 = {"Lorg/catrobat/catroid/ui/fragment/AddUserDefinedBrickFragment$Companion;", "", "()V", "TAG", "", TypeProxy.SilentConstruction.Appender.NEW_INSTANCE_METHOD_NAME, "Lorg/catrobat/catroid/ui/fragment/AddUserDefinedBrickFragment;", "addBrickListener", "Lorg/catrobat/catroid/ui/fragment/AddBrickFragment$OnAddBrickListener;", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final AddUserDefinedBrickFragment newInstance(AddBrickFragment.OnAddBrickListener addBrickListener) {
            Intrinsics.checkNotNullParameter(addBrickListener, "addBrickListener");
            AddUserDefinedBrickFragment addUserDefinedBrickFragment = new AddUserDefinedBrickFragment();
            addUserDefinedBrickFragment.addBrickListener = addBrickListener;
            return addUserDefinedBrickFragment;
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public AddUserDefinedBrickFragment() {
        final AddUserDefinedBrickFragment addUserDefinedBrickFragment = this;
        LazyThreadSafetyMode lazyThreadSafetyMode = LazyThreadSafetyMode.NONE;
        final Qualifier qualifier = null;
        final Object[] objArr = 0 == true ? 1 : 0;
        this.projectManager = LazyKt.lazy(lazyThreadSafetyMode, (Function0) new Function0<ProjectManager>() { // from class: org.catrobat.catroid.ui.fragment.AddUserDefinedBrickFragment$special$$inlined$inject$default$1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            /* JADX WARN: Type inference failed for: r0v4, types: [java.lang.Object, org.catrobat.catroid.ProjectManager] */
            @Override // kotlin.jvm.functions.Function0
            public final ProjectManager invoke() {
                ComponentCallbacks componentCallbacks = addUserDefinedBrickFragment;
                return ComponentCallbackExtKt.getKoin(componentCallbacks).get_scopeRegistry().getRootScope().get(Reflection.getOrCreateKotlinClass(ProjectManager.class), qualifier, objArr);
            }
        });
    }

    private final ProjectManager getProjectManager() {
        return (ProjectManager) this.projectManager.getValue();
    }

    @Override // androidx.fragment.app.Fragment
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Intrinsics.checkNotNullParameter(inflater, "inflater");
        View viewInflate = inflater.inflate(R.layout.fragment_add_user_defined_brick, container, false);
        this.userBrickSpace = (LinearLayout) viewInflate.findViewById(R.id.user_brick_space);
        this.scrollView = (ScrollView) viewInflate.findViewById(R.id.fragment_add_user_defined_brick);
        this.addLabel = (Button) viewInflate.findViewById(R.id.button_add_label);
        this.addInput = (Button) viewInflate.findViewById(R.id.button_add_input);
        Button button = this.addLabel;
        if (button != null) {
            button.setOnClickListener(new View.OnClickListener() { // from class: org.catrobat.catroid.ui.fragment.AddUserDefinedBrickFragment$$ExternalSyntheticLambda0
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    AddUserDefinedBrickFragment.onCreateView$lambda$0(this.f$0, view);
                }
            });
        }
        Button button2 = this.addInput;
        if (button2 != null) {
            button2.setOnClickListener(new View.OnClickListener() { // from class: org.catrobat.catroid.ui.fragment.AddUserDefinedBrickFragment$$ExternalSyntheticLambda1
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    AddUserDefinedBrickFragment.onCreateView$lambda$1(this.f$0, view);
                }
            });
        }
        Bundle arguments = getArguments();
        if (arguments != null) {
            Serializable serializable = arguments.getSerializable(UserDefinedBrick.USER_BRICK_BUNDLE_ARGUMENT);
            Intrinsics.checkNotNull(serializable, "null cannot be cast to non-null type org.catrobat.catroid.content.bricks.UserDefinedBrick");
            UserDefinedBrick userDefinedBrick = (UserDefinedBrick) serializable;
            this.userDefinedBrick = userDefinedBrick;
            if (userDefinedBrick != null) {
                View view = userDefinedBrick != null ? userDefinedBrick.getView(getActivity()) : null;
                this.userBrickView = view;
                LinearLayout linearLayout = this.userBrickSpace;
                if (linearLayout != null) {
                    linearLayout.addView(view);
                }
            }
        }
        FragmentActivity activity = getActivity();
        Intrinsics.checkNotNull(activity, "null cannot be cast to non-null type androidx.appcompat.app.AppCompatActivity");
        ActionBar supportActionBar = ((AppCompatActivity) activity).getSupportActionBar();
        if (supportActionBar != null) {
            supportActionBar.setTitle(R.string.category_user_bricks);
        }
        setHasOptionsMenu(true);
        return viewInflate;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onCreateView$lambda$0(AddUserDefinedBrickFragment this$0, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.handleAddUserDefinedBrickData(UserDefinedBrickData.UserDefinedBrickDataType.LABEL);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onCreateView$lambda$1(AddUserDefinedBrickFragment this$0, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.handleAddUserDefinedBrickData(UserDefinedBrickData.UserDefinedBrickDataType.INPUT);
    }

    @Override // androidx.fragment.app.Fragment
    public void onDestroyView() {
        super.onDestroyView();
        FragmentActivity activity = getActivity();
        Intrinsics.checkNotNull(activity, "null cannot be cast to non-null type androidx.appcompat.app.AppCompatActivity");
        AppCompatActivity appCompatActivity = (AppCompatActivity) activity;
        if (appCompatActivity.getSupportActionBar() != null) {
            appCompatActivity.getSupportActionBar();
        }
    }

    @Override // androidx.fragment.app.Fragment
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        Intrinsics.checkNotNullParameter(menu, "menu");
        Intrinsics.checkNotNullParameter(inflater, "inflater");
        inflater.inflate(R.menu.menu_confirm_userdefined, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override // androidx.fragment.app.Fragment
    public void onPrepareOptionsMenu(Menu menu) {
        Intrinsics.checkNotNullParameter(menu, "menu");
        int size = menu.size();
        for (int i = 0; i < size; i++) {
            menu.getItem(i).setVisible(false);
        }
        MenuItem menuItemFindItem = menu.findItem(R.id.confirm);
        this.confirmItem = menuItemFindItem;
        if (menuItemFindItem != null) {
            menuItemFindItem.setVisible(true);
        }
        MenuItem menuItem = this.confirmItem;
        if (menuItem != null) {
            menuItem.setEnabled(true);
        }
        super.onPrepareOptionsMenu(menu);
    }

    @Override // androidx.fragment.app.Fragment
    public boolean onOptionsItemSelected(MenuItem item) {
        UserDefinedBrick userDefinedBrick;
        UserDefinedBrick userDefinedBrick2;
        Intrinsics.checkNotNullParameter(item, "item");
        if (item.getItemId() == R.id.confirm) {
            Sprite currentSprite = getProjectManager().getCurrentSprite();
            UserDefinedBrick userDefinedBrick3 = this.userDefinedBrick;
            Boolean boolValueOf = userDefinedBrick3 != null ? Boolean.valueOf(userDefinedBrick3.isEmpty()) : null;
            Intrinsics.checkNotNull(boolValueOf, "null cannot be cast to non-null type kotlin.Boolean");
            boolean zBooleanValue = boolValueOf.booleanValue();
            if (zBooleanValue && (userDefinedBrick2 = this.userDefinedBrick) != null) {
                userDefinedBrick2.addLabel("");
            }
            if (currentSprite.doesUserBrickAlreadyExist(this.userDefinedBrick)) {
                ToastUtil.showErrorWithColor(getContext(), R.string.brick_user_defined_already_exists, -65536);
                if (zBooleanValue && (userDefinedBrick = this.userDefinedBrick) != null) {
                    userDefinedBrick.removeLastLabel();
                }
            } else {
                currentSprite.addUserDefinedBrick(this.userDefinedBrick);
                addUserDefinedScriptToScript(this.userDefinedBrick);
            }
        }
        return super.onOptionsItemSelected(item);
    }

    private final void addUserDefinedScriptToScript(UserDefinedBrick brickToAddScript) {
        UserDefinedReceiverBrick userDefinedReceiverBrick = new UserDefinedReceiverBrick(brickToAddScript);
        AddBrickFragment.OnAddBrickListener onAddBrickListener = this.addBrickListener;
        if (onAddBrickListener != null) {
            onAddBrickListener.addBrick(userDefinedReceiverBrick);
        }
        FragmentManager parentFragmentManager = getParentFragmentManager();
        Intrinsics.checkNotNullExpressionValue(parentFragmentManager, "getParentFragmentManager(...)");
        FragmentTransaction fragmentTransactionBeginTransaction = parentFragmentManager.beginTransaction();
        Intrinsics.checkNotNullExpressionValue(fragmentTransactionBeginTransaction, "beginTransaction(...)");
        Fragment fragmentFindFragmentByTag = parentFragmentManager.findFragmentByTag(BrickCategoryFragment.BRICK_CATEGORY_FRAGMENT_TAG);
        if (fragmentFindFragmentByTag != null) {
            fragmentTransactionBeginTransaction.remove(fragmentFindFragmentByTag);
            parentFragmentManager.popBackStack();
        }
        Fragment fragmentFindFragmentByTag2 = parentFragmentManager.findFragmentByTag(UserDefinedBrickListFragment.USER_DEFINED_BRICK_LIST_FRAGMENT_TAG);
        if (fragmentFindFragmentByTag2 != null) {
            fragmentTransactionBeginTransaction.remove(fragmentFindFragmentByTag2);
            parentFragmentManager.popBackStack();
        }
        Fragment fragmentFindFragmentByTag3 = parentFragmentManager.findFragmentByTag(TAG);
        if (fragmentFindFragmentByTag3 != null) {
            fragmentTransactionBeginTransaction.remove(fragmentFindFragmentByTag3);
            parentFragmentManager.popBackStack();
        }
        fragmentTransactionBeginTransaction.commit();
    }

    private final void handleAddUserDefinedBrickData(UserDefinedBrickData.UserDefinedBrickDataType dataType) {
        AddUserDataToUserDefinedBrickFragment addUserDataToUserDefinedBrickFragment = new AddUserDataToUserDefinedBrickFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(UserDefinedBrick.USER_BRICK_BUNDLE_ARGUMENT, this.userDefinedBrick);
        bundle.putSerializable(UserDefinedBrick.ADD_INPUT_OR_LABEL_BUNDLE_ARGUMENT, dataType);
        addUserDataToUserDefinedBrickFragment.setArguments(bundle);
        FragmentManager parentFragmentManager = getParentFragmentManager();
        Intrinsics.checkNotNullExpressionValue(parentFragmentManager, "getParentFragmentManager(...)");
        parentFragmentManager.beginTransaction().add(R.id.fragment_container, addUserDataToUserDefinedBrickFragment, AddUserDataToUserDefinedBrickFragment.TAG).addToBackStack(AddUserDataToUserDefinedBrickFragment.TAG).commit();
    }

    public final void addUserDataToUserBrick(String input, UserDefinedBrickData.UserDefinedBrickDataType dataType) {
        if (dataType == UserDefinedBrickData.UserDefinedBrickDataType.INPUT) {
            UserDefinedBrick userDefinedBrick = this.userDefinedBrick;
            if (userDefinedBrick != null) {
                userDefinedBrick.addInput(input);
            }
        } else {
            UserDefinedBrick userDefinedBrick2 = this.userDefinedBrick;
            if (userDefinedBrick2 != null) {
                userDefinedBrick2.addLabel(input);
            }
        }
        updateBrickView();
        ScrollView scrollView = this.scrollView;
        if (scrollView != null) {
            scrollView.fullScroll(130);
        }
    }

    private final void updateBrickView() {
        LinearLayout linearLayout = this.userBrickSpace;
        if (linearLayout != null) {
            linearLayout.removeView(this.userBrickView);
        }
        UserDefinedBrick userDefinedBrick = this.userDefinedBrick;
        View view = userDefinedBrick != null ? userDefinedBrick.getView(getActivity()) : null;
        this.userBrickView = view;
        LinearLayout linearLayout2 = this.userBrickSpace;
        if (linearLayout2 != null) {
            linearLayout2.addView(view);
        }
    }
}
