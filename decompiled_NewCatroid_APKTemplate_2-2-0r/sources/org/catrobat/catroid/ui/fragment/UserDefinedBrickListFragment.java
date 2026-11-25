package org.catrobat.catroid.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.RelativeLayout;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.fragment.app.ListFragment;
import com.android.dx.rop.code.RegisterSpec;
import com.google.android.gms.analytics.ecommerce.Promotion;
import kotlin.Metadata;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import net.bytebuddy.implementation.auxiliary.TypeProxy;
import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.content.bricks.Brick;
import org.catrobat.catroid.content.bricks.UserDefinedBrick;
import org.catrobat.catroid.databinding.FragmentUserDefinedBrickListBinding;
import org.catrobat.catroid.ui.adapter.PrototypeBrickAdapter;
import org.catrobat.catroid.utils.ToastUtil;
import org.jetbrains.kotlin.metadata.jvm.deserialization.JvmProtoBufUtil;
import org.jetbrains.kotlin.org.jline.reader.LineReader;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/ui/fragment/UserDefinedBrickListFragment.class
  classes14.dex
 */
/* JADX INFO: compiled from: UserDefinedBrickListFragment.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000^\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\b\n\u0002\b\u0003\u0018\u0000 &2\u00020\u00012\u00020\u0002:\u0001&B\u0005¢\u0006\u0002\u0010\u0003J\u0010\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u0010H\u0002J\u0010\u0010\u0011\u001a\u00020\u000e2\u0006\u0010\u0012\u001a\u00020\u0013H\u0016J$\u0010\u0014\u001a\u00020\u00132\u0006\u0010\u0015\u001a\u00020\u00162\b\u0010\u0017\u001a\u0004\u0018\u00010\u00182\b\u0010\u0019\u001a\u0004\u0018\u00010\u001aH\u0016J\b\u0010\u001b\u001a\u00020\u000eH\u0016J\u0010\u0010\u001c\u001a\u00020\u000e2\u0006\u0010\u001d\u001a\u00020\u001eH\u0016J\b\u0010\u001f\u001a\u00020\u000eH\u0016J\u001a\u0010 \u001a\u00020\u000e2\u0006\u0010!\u001a\u00020\u00132\b\u0010\u0019\u001a\u0004\u0018\u00010\u001aH\u0016J\u0010\u0010\"\u001a\u00020\u000e2\u0006\u0010#\u001a\u00020$H\u0002J\b\u0010%\u001a\u00020\u000eH\u0002R\u0010\u0010\u0004\u001a\u0004\u0018\u00010\u0005X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u0006\u001a\u0004\u0018\u00010\u0007X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\b\u001a\u0004\u0018\u00010\tX\u0082\u000e¢\u0006\u0002\n\u0000R\u0014\u0010\n\u001a\u00020\u00058BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\u000b\u0010\f¨\u0006'"}, d2 = {"Lorg/catrobat/catroid/ui/fragment/UserDefinedBrickListFragment;", "Landroidx/fragment/app/ListFragment;", "Landroid/view/View$OnClickListener;", "()V", "_binding", "Lorg/catrobat/catroid/databinding/FragmentUserDefinedBrickListBinding;", "adapter", "Lorg/catrobat/catroid/ui/adapter/PrototypeBrickAdapter;", "addBrickListener", "Lorg/catrobat/catroid/ui/fragment/AddBrickFragment$OnAddBrickListener;", "binding", "getBinding", "()Lorg/catrobat/catroid/databinding/FragmentUserDefinedBrickListBinding;", "addUserDefinedBrickToScript", "", "userDefinedBrickToAdd", "Lorg/catrobat/catroid/content/bricks/Brick;", "onClick", RegisterSpec.PREFIX, "Landroid/view/View;", "onCreateView", "inflater", "Landroid/view/LayoutInflater;", "container", "Landroid/view/ViewGroup;", "savedInstanceState", "Landroid/os/Bundle;", "onDestroyView", "onPrepareOptionsMenu", LineReader.MENU, "Landroid/view/Menu;", "onStart", "onViewCreated", Promotion.ACTION_VIEW, "setActionBar", "resId", "", "setupUserDefinedBrickListView", "Companion", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class UserDefinedBrickListFragment extends ListFragment implements View.OnClickListener {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    public static final String USER_DEFINED_BRICK_LIST_FRAGMENT_TAG = "AddBrickFragment";
    private FragmentUserDefinedBrickListBinding _binding;
    private PrototypeBrickAdapter adapter;
    private AddBrickFragment.OnAddBrickListener addBrickListener;

    @JvmStatic
    public static final UserDefinedBrickListFragment newInstance(AddBrickFragment.OnAddBrickListener onAddBrickListener) {
        return INSTANCE.newInstance(onAddBrickListener);
    }

    private final FragmentUserDefinedBrickListBinding getBinding() {
        FragmentUserDefinedBrickListBinding fragmentUserDefinedBrickListBinding = this._binding;
        Intrinsics.checkNotNull(fragmentUserDefinedBrickListBinding);
        return fragmentUserDefinedBrickListBinding;
    }

    @Override // androidx.fragment.app.ListFragment, androidx.fragment.app.Fragment
    public void onDestroyView() {
        super.onDestroyView();
        setActionBar(R.string.categories);
        this._binding = null;
    }

    @Override // androidx.fragment.app.ListFragment, androidx.fragment.app.Fragment
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Intrinsics.checkNotNullParameter(inflater, "inflater");
        this._binding = FragmentUserDefinedBrickListBinding.inflate(inflater, container, false);
        RelativeLayout root = getBinding().getRoot();
        Intrinsics.checkNotNullExpressionValue(root, "getRoot(...)");
        return root;
    }

    @Override // androidx.fragment.app.ListFragment, androidx.fragment.app.Fragment
    public void onViewCreated(View view, Bundle savedInstanceState) {
        Intrinsics.checkNotNullParameter(view, "view");
        super.onViewCreated(view, savedInstanceState);
        getBinding().buttonAddUserBrick.setOnClickListener(this);
        setupUserDefinedBrickListView();
        setActionBar(R.string.category_user_bricks);
    }

    private final void setActionBar(int resId) {
        AppCompatActivity appCompatActivity = (AppCompatActivity) getActivity();
        ActionBar supportActionBar = appCompatActivity != null ? appCompatActivity.getSupportActionBar() : null;
        if (supportActionBar != null) {
            supportActionBar.setTitle(resId);
        }
    }

    @Override // androidx.fragment.app.Fragment
    public void onPrepareOptionsMenu(Menu menu) {
        Intrinsics.checkNotNullParameter(menu, "menu");
        super.onPrepareOptionsMenu(menu);
        menu.findItem(R.id.search).setVisible(false);
    }

    @Override // androidx.fragment.app.Fragment
    public void onStart() {
        super.onStart();
        getListView().setOnItemClickListener(new AdapterView.OnItemClickListener() { // from class: org.catrobat.catroid.ui.fragment.UserDefinedBrickListFragment$$ExternalSyntheticLambda0
            @Override // android.widget.AdapterView.OnItemClickListener
            public final void onItemClick(AdapterView adapterView, View view, int i, long j) {
                UserDefinedBrickListFragment.onStart$lambda$1(this.f$0, adapterView, view, i, j);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onStart$lambda$1(UserDefinedBrickListFragment this$0, AdapterView adapterView, View view, int i, long j) {
        Brick item;
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        PrototypeBrickAdapter prototypeBrickAdapter = this$0.adapter;
        if (prototypeBrickAdapter == null || (item = prototypeBrickAdapter.getItem(i)) == null) {
            return;
        }
        this$0.addUserDefinedBrickToScript(item);
    }

    private final void setupUserDefinedBrickListView() {
        CategoryBricksFactory categoryBricksFactory = new CategoryBricksFactory();
        Context contextRequireContext = requireContext();
        String string = getString(R.string.category_user_bricks);
        Intrinsics.checkNotNullExpressionValue(string, "getString(...)");
        Intrinsics.checkNotNull(contextRequireContext);
        PrototypeBrickAdapter prototypeBrickAdapter = new PrototypeBrickAdapter(categoryBricksFactory.getBricks(string, false, contextRequireContext));
        this.adapter = prototypeBrickAdapter;
        setListAdapter(prototypeBrickAdapter);
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View v) {
        Intrinsics.checkNotNullParameter(v, "v");
        AddUserDefinedBrickFragment.Companion companion = AddUserDefinedBrickFragment.INSTANCE;
        AddBrickFragment.OnAddBrickListener onAddBrickListener = this.addBrickListener;
        Intrinsics.checkNotNull(onAddBrickListener);
        AddUserDefinedBrickFragment addUserDefinedBrickFragmentNewInstance = companion.newInstance(onAddBrickListener);
        UserDefinedBrick userDefinedBrick = new UserDefinedBrick();
        Bundle bundle = new Bundle();
        bundle.putSerializable(UserDefinedBrick.USER_BRICK_BUNDLE_ARGUMENT, userDefinedBrick);
        addUserDefinedBrickFragmentNewInstance.setArguments(bundle);
        FragmentManager parentFragmentManager = getParentFragmentManager();
        Intrinsics.checkNotNullExpressionValue(parentFragmentManager, "getParentFragmentManager(...)");
        parentFragmentManager.beginTransaction().add(R.id.fragment_container, addUserDefinedBrickFragmentNewInstance, AddUserDefinedBrickFragment.TAG).addToBackStack(AddUserDefinedBrickFragment.TAG).commit();
    }

    private final void addUserDefinedBrickToScript(Brick userDefinedBrickToAdd) {
        try {
            Brick brickClone = userDefinedBrickToAdd.clone();
            Intrinsics.checkNotNullExpressionValue(brickClone, "clone(...)");
            if (userDefinedBrickToAdd instanceof UserDefinedBrick) {
                ((UserDefinedBrick) brickClone).setCallingBrick(true);
            }
            AddBrickFragment.OnAddBrickListener onAddBrickListener = this.addBrickListener;
            if (onAddBrickListener != null) {
                onAddBrickListener.addBrick(brickClone);
            }
            FragmentTransaction fragmentTransactionBeginTransaction = getParentFragmentManager().beginTransaction();
            Intrinsics.checkNotNullExpressionValue(fragmentTransactionBeginTransaction, "beginTransaction(...)");
            Fragment fragmentFindFragmentByTag = getParentFragmentManager().findFragmentByTag(BrickCategoryFragment.BRICK_CATEGORY_FRAGMENT_TAG);
            if (fragmentFindFragmentByTag != null) {
                fragmentTransactionBeginTransaction.remove(fragmentFindFragmentByTag);
                getParentFragmentManager().popBackStack();
            }
            Fragment fragmentFindFragmentByTag2 = getParentFragmentManager().findFragmentByTag(USER_DEFINED_BRICK_LIST_FRAGMENT_TAG);
            if (fragmentFindFragmentByTag2 != null) {
                fragmentTransactionBeginTransaction.remove(fragmentFindFragmentByTag2);
                getParentFragmentManager().popBackStack();
            }
            fragmentTransactionBeginTransaction.commit();
        } catch (CloneNotSupportedException e) {
            String message = e.getMessage();
            if (message != null) {
                Log.e(getTag(), message);
            }
            ToastUtil.showError(requireContext(), R.string.error_adding_brick);
        }
    }

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/ui/fragment/UserDefinedBrickListFragment$Companion.class
      classes14.dex
     */
    /* JADX INFO: compiled from: UserDefinedBrickListFragment.kt */
    @Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0012\u0010\u0006\u001a\u00020\u00072\b\u0010\b\u001a\u0004\u0018\u00010\tH\u0007R\u0018\u0010\u0003\u001a\n \u0005*\u0004\u0018\u00010\u00040\u00048\u0006X\u0087\u0004¢\u0006\u0002\n\u0000¨\u0006\n"}, d2 = {"Lorg/catrobat/catroid/ui/fragment/UserDefinedBrickListFragment$Companion;", "", "()V", "USER_DEFINED_BRICK_LIST_FRAGMENT_TAG", "", JvmProtoBufUtil.PLATFORM_TYPE_ID, TypeProxy.SilentConstruction.Appender.NEW_INSTANCE_METHOD_NAME, "Lorg/catrobat/catroid/ui/fragment/UserDefinedBrickListFragment;", "addBrickListener", "Lorg/catrobat/catroid/ui/fragment/AddBrickFragment$OnAddBrickListener;", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        @JvmStatic
        public final UserDefinedBrickListFragment newInstance(AddBrickFragment.OnAddBrickListener addBrickListener) {
            UserDefinedBrickListFragment userDefinedBrickListFragment = new UserDefinedBrickListFragment();
            userDefinedBrickListFragment.addBrickListener = addBrickListener;
            return userDefinedBrickListFragment;
        }
    }
}
