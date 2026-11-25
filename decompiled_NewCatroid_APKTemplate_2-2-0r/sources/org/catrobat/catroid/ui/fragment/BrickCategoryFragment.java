package org.catrobat.catroid.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.ListFragment;
import com.google.android.gms.common.internal.ServiceSpecificExtraArgs;
import java.util.concurrent.locks.Lock;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.internal.Intrinsics;
import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.ui.BottomBar;
import org.catrobat.catroid.ui.SpriteActivityOnTabSelectedListenerKt;
import org.catrobat.catroid.ui.ViewSwitchLock;
import org.catrobat.catroid.ui.adapter.BrickCategoryAdapter;
import org.catrobat.catroid.utils.SnackbarUtil;
import org.jetbrains.kotlin.org.jline.reader.LineReader;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/ui/fragment/BrickCategoryFragment.class
  classes14.dex
 */
/* JADX INFO: compiled from: BrickCategoryFragment.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000b\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\r\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\t\u0018\u0000 (2\u00020\u0001:\u0002()B\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000eH\u0016J\u0012\u0010\u000f\u001a\u00020\f2\b\u0010\u0010\u001a\u0004\u0018\u00010\u0011H\u0016J&\u0010\u0012\u001a\u0004\u0018\u00010\u00132\u0006\u0010\u0014\u001a\u00020\u00152\b\u0010\u0016\u001a\u0004\u0018\u00010\u00172\b\u0010\u0010\u001a\u0004\u0018\u00010\u0011H\u0016J\b\u0010\u0018\u001a\u00020\fH\u0016J\b\u0010\u0019\u001a\u00020\fH\u0016J\u0010\u0010\u001a\u001a\u00020\u001b2\u0006\u0010\u001c\u001a\u00020\u001dH\u0016J\b\u0010\u001e\u001a\u00020\fH\u0016J\u0010\u0010\u001f\u001a\u00020\f2\u0006\u0010 \u001a\u00020!H\u0016J\b\u0010\"\u001a\u00020\fH\u0016J\b\u0010#\u001a\u00020\fH\u0016J\u000e\u0010$\u001a\u00020\f2\u0006\u0010%\u001a\u00020\bJ\b\u0010&\u001a\u00020\fH\u0002J\b\u0010'\u001a\u00020\fH\u0002R\u0010\u0010\u0003\u001a\u0004\u0018\u00010\u0004X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u0005\u001a\u0004\u0018\u00010\u0006X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u0007\u001a\u0004\u0018\u00010\bX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\nX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006*"}, d2 = {"Lorg/catrobat/catroid/ui/fragment/BrickCategoryFragment;", "Landroidx/fragment/app/ListFragment;", "()V", "adapter", "Lorg/catrobat/catroid/ui/adapter/BrickCategoryAdapter;", "previousActionBarTitle", "", "scriptFragment", "Lorg/catrobat/catroid/ui/fragment/BrickCategoryFragment$OnCategorySelectedListener;", "viewSwitchLock", "Ljava/util/concurrent/locks/Lock;", "onAttach", "", "context", "Landroid/content/Context;", "onCreate", "savedInstanceState", "Landroid/os/Bundle;", "onCreateView", "Landroid/view/View;", "inflater", "Landroid/view/LayoutInflater;", "container", "Landroid/view/ViewGroup;", "onDestroy", "onDetach", "onOptionsItemSelected", "", "item", "Landroid/view/MenuItem;", "onPause", "onPrepareOptionsMenu", LineReader.MENU, "Landroid/view/Menu;", "onResume", "onStart", "setOnCategorySelectedListener", ServiceSpecificExtraArgs.CastExtraArgs.LISTENER, "setUpActionBar", "setupBrickCategories", "Companion", "OnCategorySelectedListener", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class BrickCategoryFragment extends ListFragment {
    public static final String BRICK_CATEGORY_FRAGMENT_TAG = "brick_category_fragment";
    private BrickCategoryAdapter adapter;
    private CharSequence previousActionBarTitle;
    private OnCategorySelectedListener scriptFragment;
    private final Lock viewSwitchLock = new ViewSwitchLock();

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/ui/fragment/BrickCategoryFragment$OnCategorySelectedListener.class
      classes14.dex
     */
    /* JADX INFO: compiled from: BrickCategoryFragment.kt */
    @Metadata(d1 = {"\u0000\u0016\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\bf\u0018\u00002\u00020\u0001J\u0012\u0010\u0002\u001a\u00020\u00032\b\u0010\u0004\u001a\u0004\u0018\u00010\u0005H&¨\u0006\u0006"}, d2 = {"Lorg/catrobat/catroid/ui/fragment/BrickCategoryFragment$OnCategorySelectedListener;", "", "onCategorySelected", "", "category", "", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public interface OnCategorySelectedListener {
        void onCategorySelected(String category);
    }

    public final void setOnCategorySelectedListener(OnCategorySelectedListener listener) {
        Intrinsics.checkNotNullParameter(listener, "listener");
        this.scriptFragment = listener;
    }

    @Override // androidx.fragment.app.Fragment
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            getParentFragmentManager().popBackStack(BRICK_CATEGORY_FRAGMENT_TAG, 1);
        } else {
            setHasOptionsMenu(true);
        }
    }

    @Override // androidx.fragment.app.ListFragment, androidx.fragment.app.Fragment
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Intrinsics.checkNotNullParameter(inflater, "inflater");
        View viewInflate = inflater.inflate(R.layout.fragment_brick_categories, container, false);
        setUpActionBar();
        BottomBar.hideBottomBar(getActivity());
        setupBrickCategories();
        return viewInflate;
    }

    @Override // androidx.fragment.app.Fragment
    public void onStart() {
        super.onStart();
        getListView().setOnItemClickListener(new AdapterView.OnItemClickListener() { // from class: org.catrobat.catroid.ui.fragment.BrickCategoryFragment$$ExternalSyntheticLambda0
            @Override // android.widget.AdapterView.OnItemClickListener
            public final void onItemClick(AdapterView adapterView, View view, int i, long j) {
                BrickCategoryFragment.onStart$lambda$0(this.f$0, adapterView, view, i, j);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onStart$lambda$0(BrickCategoryFragment this$0, AdapterView parent, View view, int i, long j) {
        OnCategorySelectedListener onCategorySelectedListener;
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNullParameter(parent, "parent");
        Intrinsics.checkNotNullParameter(view, "view");
        if (this$0.viewSwitchLock.tryLock() && (onCategorySelectedListener = this$0.scriptFragment) != null) {
            BrickCategoryAdapter brickCategoryAdapter = this$0.adapter;
            onCategorySelectedListener.onCategorySelected(brickCategoryAdapter != null ? brickCategoryAdapter.getItem(i) : null);
            Unit unit = Unit.INSTANCE;
        }
    }

    @Override // androidx.fragment.app.Fragment
    public void onResume() {
        super.onResume();
        BottomBar.hideBottomBar(getActivity());
        setupBrickCategories();
        SnackbarUtil.showHintSnackbar(getActivity(), R.string.hint_category);
    }

    @Override // androidx.fragment.app.Fragment
    public void onPause() {
        super.onPause();
        BottomBar.showBottomBar(getActivity());
        BottomBar.showPlayButton(getActivity());
    }

    @Override // androidx.fragment.app.Fragment
    public void onDestroy() {
        super.onDestroy();
        FragmentActivity activity = getActivity();
        AppCompatActivity appCompatActivity = activity instanceof AppCompatActivity ? (AppCompatActivity) activity : null;
        ActionBar supportActionBar = appCompatActivity != null ? appCompatActivity.getSupportActionBar() : null;
        if (supportActionBar == null) {
            return;
        }
        if (supportActionBar != null) {
            supportActionBar.setDisplayShowTitleEnabled(true);
        }
        if (supportActionBar != null) {
            supportActionBar.setTitle(this.previousActionBarTitle);
        }
        BottomBar.showBottomBar(getActivity());
        BottomBar.showPlayButton(getActivity());
    }

    @Override // androidx.fragment.app.Fragment
    public void onPrepareOptionsMenu(Menu menu) {
        Intrinsics.checkNotNullParameter(menu, "menu");
        super.onPrepareOptionsMenu(menu);
        MenuItem menuItemFindItem = menu.findItem(R.id.delete);
        if (menuItemFindItem != null) {
            menuItemFindItem.setVisible(false);
        }
        MenuItem menuItemFindItem2 = menu.findItem(R.id.copy);
        if (menuItemFindItem2 != null) {
            menuItemFindItem2.setVisible(false);
        }
        MenuItem menuItemFindItem3 = menu.findItem(R.id.backpack);
        if (menuItemFindItem3 != null) {
            menuItemFindItem3.setVisible(false);
        }
        MenuItem menuItemFindItem4 = menu.findItem(R.id.comment_in_out);
        if (menuItemFindItem4 != null) {
            menuItemFindItem4.setVisible(false);
        }
        MenuItem menuItemFindItem5 = menu.findItem(R.id.catblocks_reorder_scripts);
        if (menuItemFindItem5 != null) {
            menuItemFindItem5.setVisible(false);
        }
        MenuItem menuItemFindItem6 = menu.findItem(R.id.find);
        if (menuItemFindItem6 != null) {
            menuItemFindItem6.setVisible(false);
        }
        MenuItem menuItemFindItem7 = menu.findItem(R.id.search);
        if (menuItemFindItem7 == null) {
            return;
        }
        menuItemFindItem7.setVisible(true);
    }

    private final void setUpActionBar() {
        ActionBar supportActionBar;
        ActionBar supportActionBar2;
        ActionBar supportActionBar3;
        FragmentActivity activity = getActivity();
        AppCompatActivity appCompatActivity = activity instanceof AppCompatActivity ? (AppCompatActivity) activity : null;
        if (appCompatActivity != null && (supportActionBar3 = appCompatActivity.getSupportActionBar()) != null) {
            supportActionBar3.setDisplayShowTitleEnabled(true);
        }
        FragmentActivity activity2 = getActivity();
        AppCompatActivity appCompatActivity2 = activity2 instanceof AppCompatActivity ? (AppCompatActivity) activity2 : null;
        this.previousActionBarTitle = String.valueOf((appCompatActivity2 == null || (supportActionBar2 = appCompatActivity2.getSupportActionBar()) == null) ? null : supportActionBar2.getTitle());
        FragmentActivity activity3 = getActivity();
        AppCompatActivity appCompatActivity3 = activity3 instanceof AppCompatActivity ? (AppCompatActivity) activity3 : null;
        if (appCompatActivity3 == null || (supportActionBar = appCompatActivity3.getSupportActionBar()) == null) {
            return;
        }
        supportActionBar.setTitle(R.string.categories);
    }

    private final void setupBrickCategories() {
        FragmentActivity fragmentActivityRequireActivity = requireActivity();
        Intrinsics.checkNotNullExpressionValue(fragmentActivityRequireActivity, "requireActivity(...)");
        BrickCategoryAdapter brickCategoryAdapter = new BrickCategoryAdapter(new BrickCategoryListBuilder(fragmentActivityRequireActivity).getBrickCategoryViews());
        this.adapter = brickCategoryAdapter;
        setListAdapter(brickCategoryAdapter);
    }

    @Override // androidx.fragment.app.Fragment
    public void onAttach(Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        super.onAttach(context);
        SpriteActivityOnTabSelectedListenerKt.removeTabLayout(getActivity());
    }

    @Override // androidx.fragment.app.Fragment
    public void onDetach() {
        SpriteActivityOnTabSelectedListenerKt.addTabLayout(getActivity(), 0);
        super.onDetach();
    }

    @Override // androidx.fragment.app.Fragment
    public boolean onOptionsItemSelected(MenuItem item) {
        OnCategorySelectedListener onCategorySelectedListener;
        Intrinsics.checkNotNullParameter(item, "item");
        Fragment fragmentFindFragmentById = getParentFragmentManager().findFragmentById(R.id.fragment_container);
        if (item.getItemId() == R.id.search && (fragmentFindFragmentById instanceof BrickCategoryFragment) && (onCategorySelectedListener = this.scriptFragment) != null) {
            Context context = getContext();
            onCategorySelectedListener.onCategorySelected(context != null ? context.getString(R.string.category_search_bricks) : null);
        }
        return super.onOptionsItemSelected(item);
    }
}
