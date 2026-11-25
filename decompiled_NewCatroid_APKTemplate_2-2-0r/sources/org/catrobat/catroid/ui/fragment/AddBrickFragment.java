package org.catrobat.catroid.ui.fragment;

import android.animation.TimeInterpolator;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Typeface;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.transition.ChangeBounds;
import android.transition.Fade;
import android.transition.TransitionManager;
import android.transition.TransitionSet;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.ListFragment;
import androidx.interpolator.view.animation.FastOutSlowInInterpolator;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import kotlin.Lazy;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import net.bytebuddy.implementation.auxiliary.TypeProxy;
import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.ProjectManager;
import org.catrobat.catroid.content.BrickInfo;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.content.bricks.Brick;
import org.catrobat.catroid.content.bricks.FormulaBrick;
import org.catrobat.catroid.content.bricks.SubCategoryHeaderBrick;
import org.catrobat.catroid.content.bricks.UserDataBrick;
import org.catrobat.catroid.formulaeditor.Formula;
import org.catrobat.catroid.formulaeditor.UserData;
import org.catrobat.catroid.ui.adapter.PrototypeBrickAdapter;
import org.catrobat.catroid.ui.settingsfragments.AccessibilityProfile;
import org.catrobat.catroid.utils.FavoriteBricksManager;
import org.catrobat.catroid.utils.SnackbarUtil;
import org.catrobat.catroid.utils.SubCategoryStateManager;
import org.catrobat.catroid.utils.ToastUtil;
import org.jetbrains.kotlin.com.intellij.psi.CommonClassNames;
import org.jetbrains.kotlin.metadata.jvm.deserialization.JvmProtoBufUtil;
import org.jetbrains.kotlin.org.jline.reader.LineReader;
import org.koin.java.KoinJavaComponent;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/ui/fragment/AddBrickFragment.class
  classes14.dex
 */
/* JADX INFO: compiled from: AddBrickFragment.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000\u0082\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\r\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0004\u0018\u0000 52\u00020\u0001:\u000256B\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0014\u001a\u00020\u00152\u0006\u0010\u0016\u001a\u00020\tH\u0002J\u0012\u0010\u0017\u001a\u00020\u00182\b\u0010\u0019\u001a\u0004\u0018\u00010\u001aH\u0016J\u0018\u0010\u001b\u001a\u00020\u00182\u0006\u0010\u001c\u001a\u00020\u001d2\u0006\u0010\u001e\u001a\u00020\u001fH\u0016J&\u0010 \u001a\u0004\u0018\u00010!2\u0006\u0010\"\u001a\u00020#2\b\u0010$\u001a\u0004\u0018\u00010%2\b\u0010\u0019\u001a\u0004\u0018\u00010\u001aH\u0016J\b\u0010&\u001a\u00020\u0018H\u0016J\u0010\u0010'\u001a\u00020(2\u0006\u0010)\u001a\u00020*H\u0016J\b\u0010+\u001a\u00020\u0018H\u0016J\b\u0010,\u001a\u00020\u0018H\u0016J\b\u0010-\u001a\u00020(H\u0002J\b\u0010.\u001a\u00020\u0018H\u0002J\u0010\u0010/\u001a\u00020\u00182\u0006\u0010\u0016\u001a\u00020\tH\u0002J\u0010\u00100\u001a\u00020\u00182\u0006\u0010\u0016\u001a\u00020\tH\u0002J\u0018\u00101\u001a\u00020\u00182\u0006\u00102\u001a\u00020\r2\u0006\u0010\u0016\u001a\u000203H\u0002J\b\u00104\u001a\u00020\u0018H\u0002R\u0010\u0010\u0003\u001a\u0004\u0018\u00010\u0004X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u0005\u001a\u0004\u0018\u00010\u0006X\u0082\u000e¢\u0006\u0002\n\u0000R\u0014\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\t0\bX\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\n\u001a\u0004\u0018\u00010\u000bX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\rX\u0082\u000e¢\u0006\u0002\n\u0000R\u001b\u0010\u000e\u001a\u00020\u000f8BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\u0012\u0010\u0013\u001a\u0004\b\u0010\u0010\u0011¨\u00067"}, d2 = {"Lorg/catrobat/catroid/ui/fragment/AddBrickFragment;", "Landroidx/fragment/app/ListFragment;", "()V", "adapter", "Lorg/catrobat/catroid/ui/adapter/PrototypeBrickAdapter;", "addBrickListener", "Lorg/catrobat/catroid/ui/fragment/AddBrickFragment$OnAddBrickListener;", "masterBrickList", "", "Lorg/catrobat/catroid/content/bricks/Brick;", "previousActionBarTitle", "", "previousVisibleListSize", "", "projectManager", "Lorg/catrobat/catroid/ProjectManager;", "getProjectManager", "()Lorg/catrobat/catroid/ProjectManager;", "projectManager$delegate", "Lkotlin/Lazy;", "getBrickSystemInfo", "", "brick", "onCreate", "", "savedInstanceState", "Landroid/os/Bundle;", "onCreateOptionsMenu", LineReader.MENU, "Landroid/view/Menu;", "menuInflater", "Landroid/view/MenuInflater;", "onCreateView", "Landroid/view/View;", "inflater", "Landroid/view/LayoutInflater;", "container", "Landroid/view/ViewGroup;", "onDestroy", "onOptionsItemSelected", "", "item", "Landroid/view/MenuItem;", "onResume", "onStart", "onlyBeginnerBricks", "setupSelectedBrickCategory", "showAddBrickPreviewDialog", "showDetailedSystemInfoDialog", "toggleHeaderState", "position", "Lorg/catrobat/catroid/content/bricks/SubCategoryHeaderBrick;", "updateVisibleBricks", "Companion", "OnAddBrickListener", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class AddBrickFragment extends ListFragment {
    private static final String BUNDLE_ARGUMENTS_SELECTED_CATEGORY = "selected_category";
    private PrototypeBrickAdapter adapter;
    private OnAddBrickListener addBrickListener;
    private CharSequence previousActionBarTitle;
    private int previousVisibleListSize;

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    public static final String ADD_BRICK_FRAGMENT_TAG = "AddBrickFragment";
    private static int listIndexToFocus = -1;
    private List<? extends Brick> masterBrickList = CollectionsKt.emptyList();

    /* JADX INFO: renamed from: projectManager$delegate, reason: from kotlin metadata */
    private final Lazy projectManager = KoinJavaComponent.inject$default(ProjectManager.class, null, null, 6, null);

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/ui/fragment/AddBrickFragment$OnAddBrickListener.class
      classes14.dex
     */
    /* JADX INFO: compiled from: AddBrickFragment.kt */
    @Metadata(d1 = {"\u0000\u0016\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\bf\u0018\u00002\u00020\u0001J\u0012\u0010\u0002\u001a\u00020\u00032\b\u0010\u0004\u001a\u0004\u0018\u00010\u0005H&¨\u0006\u0006"}, d2 = {"Lorg/catrobat/catroid/ui/fragment/AddBrickFragment$OnAddBrickListener;", "", "addBrick", "", "brick", "Lorg/catrobat/catroid/content/bricks/Brick;", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public interface OnAddBrickListener {
        void addBrick(Brick brick);
    }

    @JvmStatic
    public static final AddBrickFragment newInstance(String str, OnAddBrickListener onAddBrickListener) {
        return INSTANCE.newInstance(str, onAddBrickListener);
    }

    private final boolean onlyBeginnerBricks() {
        return PreferenceManager.getDefaultSharedPreferences(getActivity()).getBoolean(AccessibilityProfile.BEGINNER_BRICKS, false);
    }

    private final ProjectManager getProjectManager() {
        return (ProjectManager) this.projectManager.getValue();
    }

    @Override // androidx.fragment.app.ListFragment, androidx.fragment.app.Fragment
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ActionBar supportActionBar;
        Intrinsics.checkNotNullParameter(inflater, "inflater");
        View viewInflate = inflater.inflate(R.layout.fragment_brick_add, container, false);
        FragmentActivity activity = getActivity();
        AppCompatActivity appCompatActivity = activity instanceof AppCompatActivity ? (AppCompatActivity) activity : null;
        this.previousActionBarTitle = (appCompatActivity == null || (supportActionBar = appCompatActivity.getSupportActionBar()) == null) ? null : supportActionBar.getTitle();
        FragmentActivity activity2 = getActivity();
        AppCompatActivity appCompatActivity2 = activity2 instanceof AppCompatActivity ? (AppCompatActivity) activity2 : null;
        ActionBar supportActionBar2 = appCompatActivity2 != null ? appCompatActivity2.getSupportActionBar() : null;
        if (supportActionBar2 != null) {
            Bundle arguments = getArguments();
            supportActionBar2.setTitle(arguments != null ? arguments.getString(BUNDLE_ARGUMENTS_SELECTED_CATEGORY) : null);
        }
        setupSelectedBrickCategory();
        return viewInflate;
    }

    /* JADX WARN: Code duplicated, block: B:14:0x004a  */
    private final void setupSelectedBrickCategory() {
        List<Brick> listEmptyList;
        FragmentActivity activity = getActivity();
        Sprite currentSprite = getProjectManager().getCurrentSprite();
        Sprite backgroundSprite = getProjectManager().getCurrentlyEditedScene().getBackgroundSprite();
        Bundle arguments = getArguments();
        String string = arguments != null ? arguments.getString(BUNDLE_ARGUMENTS_SELECTED_CATEGORY) : null;
        CategoryBeginnerBricksFactory categoryBeginnerBricksFactory = onlyBeginnerBricks() ? new CategoryBeginnerBricksFactory() : new CategoryBricksFactory();
        if (string != null) {
            listEmptyList = activity != null ? categoryBeginnerBricksFactory.getBricks(string, Intrinsics.areEqual(backgroundSprite, currentSprite), activity) : null;
            if (listEmptyList == null) {
                listEmptyList = CollectionsKt.emptyList();
            }
        } else {
            listEmptyList = CollectionsKt.emptyList();
        }
        this.masterBrickList = listEmptyList;
        for (Brick brick : listEmptyList) {
            if (brick instanceof SubCategoryHeaderBrick) {
                SubCategoryHeaderBrick subCategoryHeaderBrick = (SubCategoryHeaderBrick) brick;
                SubCategoryStateManager subCategoryStateManager = SubCategoryStateManager.INSTANCE;
                Context contextRequireContext = requireContext();
                Intrinsics.checkNotNullExpressionValue(contextRequireContext, "requireContext(...)");
                subCategoryHeaderBrick.setExpanded(SubCategoryStateManager.isExpanded$default(subCategoryStateManager, contextRequireContext, subCategoryHeaderBrick.getTitle(), false, 4, null));
            }
        }
        updateVisibleBricks();
    }

    @Override // androidx.fragment.app.Fragment
    public void onStart() {
        super.onStart();
        if (listIndexToFocus != -1) {
            getListView().setSelection(listIndexToFocus);
            listIndexToFocus = -1;
        }
        getListView().setOnItemClickListener(new AdapterView.OnItemClickListener() { // from class: org.catrobat.catroid.ui.fragment.AddBrickFragment$$ExternalSyntheticLambda4
            @Override // android.widget.AdapterView.OnItemClickListener
            public final void onItemClick(AdapterView adapterView, View view, int i, long j) {
                AddBrickFragment.onStart$lambda$5(this.f$0, adapterView, view, i, j);
            }
        });
        getListView().setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() { // from class: org.catrobat.catroid.ui.fragment.AddBrickFragment$$ExternalSyntheticLambda5
            @Override // android.widget.AdapterView.OnItemLongClickListener
            public final boolean onItemLongClick(AdapterView adapterView, View view, int i, long j) {
                return AddBrickFragment.onStart$lambda$7(this.f$0, adapterView, view, i, j);
            }
        });
        getListView().setOnScrollListener(new AbsListView.OnScrollListener() { // from class: org.catrobat.catroid.ui.fragment.AddBrickFragment.onStart.3
            @Override // android.widget.AbsListView.OnScrollListener
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
            }

            @Override // android.widget.AbsListView.OnScrollListener
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                boolean z = true;
                if (scrollState != 2 && scrollState != 1) {
                    z = false;
                }
                PrototypeBrickAdapter prototypeBrickAdapter = AddBrickFragment.this.adapter;
                if (prototypeBrickAdapter == null) {
                    return;
                }
                prototypeBrickAdapter.setScrolling(z);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onStart$lambda$5(AddBrickFragment this$0, AdapterView adapterView, View view, int i, long j) {
        Brick item;
        TextView textView;
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        PrototypeBrickAdapter prototypeBrickAdapter = this$0.adapter;
        if (prototypeBrickAdapter == null || (item = prototypeBrickAdapter.getItem(i)) == null) {
            return;
        }
        if (item instanceof SubCategoryHeaderBrick) {
            TransitionSet transitionSet = new TransitionSet();
            transitionSet.setOrdering(0);
            transitionSet.addTransition(new ChangeBounds());
            transitionSet.setDuration(250L);
            transitionSet.setInterpolator((TimeInterpolator) new FastOutSlowInInterpolator());
            TransitionManager.beginDelayedTransition(this$0.getListView(), transitionSet);
            SubCategoryHeaderBrick subCategoryHeaderBrick = (SubCategoryHeaderBrick) item;
            subCategoryHeaderBrick.setExpanded(!subCategoryHeaderBrick.getIsExpanded());
            if (view != null && (textView = (TextView) view.findViewById(R.id.brick_header_arrow)) != null) {
                textView.setText(subCategoryHeaderBrick.getIsExpanded() ? "▼" : "▶");
            }
            SubCategoryStateManager subCategoryStateManager = SubCategoryStateManager.INSTANCE;
            Context contextRequireContext = this$0.requireContext();
            Intrinsics.checkNotNullExpressionValue(contextRequireContext, "requireContext(...)");
            subCategoryStateManager.setExpanded(contextRequireContext, subCategoryHeaderBrick.getTitle(), subCategoryHeaderBrick.getIsExpanded());
            this$0.updateVisibleBricks();
            return;
        }
        FragmentActivity fragmentActivityRequireActivity = this$0.requireActivity();
        Intrinsics.checkNotNullExpressionValue(fragmentActivityRequireActivity, "requireActivity(...)");
        OnAddBrickListener onAddBrickListener = this$0.addBrickListener;
        FragmentManager parentFragmentManager = this$0.getParentFragmentManager();
        Intrinsics.checkNotNullExpressionValue(parentFragmentManager, "getParentFragmentManager(...)");
        String ADD_BRICK_FRAGMENT_TAG2 = ADD_BRICK_FRAGMENT_TAG;
        Intrinsics.checkNotNullExpressionValue(ADD_BRICK_FRAGMENT_TAG2, "ADD_BRICK_FRAGMENT_TAG");
        AddBrickFragmentKt.addBrickToScript(item, fragmentActivityRequireActivity, onAddBrickListener, parentFragmentManager, ADD_BRICK_FRAGMENT_TAG2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final boolean onStart$lambda$7(AddBrickFragment this$0, AdapterView adapterView, View view, int i, long j) {
        Brick item;
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        PrototypeBrickAdapter prototypeBrickAdapter = this$0.adapter;
        if (prototypeBrickAdapter == null || (item = prototypeBrickAdapter.getItem(i)) == null || (item instanceof SubCategoryHeaderBrick)) {
            return true;
        }
        this$0.showAddBrickPreviewDialog(item);
        return true;
    }

    private final void toggleHeaderState(int position, SubCategoryHeaderBrick brick) {
        TransitionSet transitionSet = new TransitionSet();
        transitionSet.setOrdering(0);
        transitionSet.addTransition(new ChangeBounds());
        Fade fade = new Fade(2);
        fade.setDuration(150L);
        transitionSet.addTransition(fade);
        transitionSet.setDuration(250L);
        transitionSet.setInterpolator((TimeInterpolator) new FastOutSlowInInterpolator());
        TransitionManager.beginDelayedTransition(getListView(), transitionSet);
        brick.setExpanded(!brick.getIsExpanded());
        int firstVisiblePosition = position - getListView().getFirstVisiblePosition();
        if (firstVisiblePosition >= 0 && firstVisiblePosition < getListView().getChildCount()) {
            View childAt = getListView().getChildAt(firstVisiblePosition);
            TextView textView = childAt != null ? (TextView) childAt.findViewById(R.id.brick_header_arrow) : null;
            if (textView != null) {
                textView.setText(brick.getIsExpanded() ? "▼" : "▶");
            }
        }
        SubCategoryStateManager subCategoryStateManager = SubCategoryStateManager.INSTANCE;
        Context contextRequireContext = requireContext();
        Intrinsics.checkNotNullExpressionValue(contextRequireContext, "requireContext(...)");
        subCategoryStateManager.setExpanded(contextRequireContext, brick.getTitle(), brick.getIsExpanded());
        updateVisibleBricks();
    }

    private final void updateVisibleBricks() {
        int iMax;
        int iMin;
        Brick item;
        ArrayList arrayList = new ArrayList();
        boolean isExpanded = true;
        boolean z = true;
        for (Brick brick : this.masterBrickList) {
            if (brick instanceof SubCategoryHeaderBrick) {
                SubCategoryHeaderBrick subCategoryHeaderBrick = (SubCategoryHeaderBrick) brick;
                subCategoryHeaderBrick.setStacked(z);
                arrayList.add(brick);
                isExpanded = subCategoryHeaderBrick.getIsExpanded();
                z = true;
            } else if (isExpanded) {
                arrayList.add(brick);
                z = false;
            }
        }
        this.previousVisibleListSize = arrayList.size();
        LinkedHashSet linkedHashSet = new LinkedHashSet();
        if (getView() != null) {
            try {
                ListView listView = getListView();
                Intrinsics.checkNotNullExpressionValue(listView, "getListView(...)");
                int firstVisiblePosition = listView.getFirstVisiblePosition();
                int lastVisiblePosition = listView.getLastVisiblePosition();
                int count = listView.getCount();
                if (firstVisiblePosition >= 0 && lastVisiblePosition >= 0 && (iMax = Math.max(0, firstVisiblePosition)) <= (iMin = Math.min(count - 1, lastVisiblePosition)) && iMax <= iMin) {
                    while (true) {
                        PrototypeBrickAdapter prototypeBrickAdapter = this.adapter;
                        if (prototypeBrickAdapter != null && (item = prototypeBrickAdapter.getItem(iMax)) != null) {
                            linkedHashSet.add(item);
                        }
                        if (iMax == iMin) {
                            break;
                        } else {
                            iMax++;
                        }
                    }
                }
            } catch (Exception unused) {
            }
        }
        PrototypeBrickAdapter prototypeBrickAdapter2 = this.adapter;
        if (prototypeBrickAdapter2 == null) {
            PrototypeBrickAdapter prototypeBrickAdapter3 = new PrototypeBrickAdapter(arrayList);
            this.adapter = prototypeBrickAdapter3;
            setListAdapter(prototypeBrickAdapter3);
        } else if (prototypeBrickAdapter2 != null) {
            prototypeBrickAdapter2.replaceList(arrayList, linkedHashSet);
        }
    }

    @Override // androidx.fragment.app.Fragment
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override // androidx.fragment.app.Fragment
    public void onCreateOptionsMenu(Menu menu, MenuInflater menuInflater) {
        Intrinsics.checkNotNullParameter(menu, "menu");
        Intrinsics.checkNotNullParameter(menuInflater, "menuInflater");
        super.onCreateOptionsMenu(menu, menuInflater);
        MenuItem menuItemFindItem = menu.findItem(R.id.comment_in_out);
        if (menuItemFindItem == null) {
            return;
        }
        menuItemFindItem.setVisible(false);
    }

    @Override // androidx.fragment.app.Fragment
    public void onDestroy() {
        PrototypeBrickAdapter prototypeBrickAdapter = this.adapter;
        if (prototypeBrickAdapter != null) {
            prototypeBrickAdapter.clearCache();
        }
        FragmentActivity activity = getActivity();
        AppCompatActivity appCompatActivity = activity instanceof AppCompatActivity ? (AppCompatActivity) activity : null;
        ActionBar supportActionBar = appCompatActivity != null ? appCompatActivity.getSupportActionBar() : null;
        if (supportActionBar != null && supportActionBar != null) {
            supportActionBar.setTitle(this.previousActionBarTitle);
        }
        super.onDestroy();
    }

    @Override // androidx.fragment.app.Fragment
    public void onResume() {
        super.onResume();
        setupSelectedBrickCategory();
        SnackbarUtil.showHintSnackbar(getActivity(), R.string.hint_bricks);
    }

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/ui/fragment/AddBrickFragment$Companion.class
      classes14.dex
     */
    /* JADX INFO: compiled from: AddBrickFragment.kt */
    @Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u001c\u0010\t\u001a\u00020\n2\b\u0010\u000b\u001a\u0004\u0018\u00010\u00042\b\u0010\f\u001a\u0004\u0018\u00010\rH\u0007R\u0018\u0010\u0003\u001a\n \u0005*\u0004\u0018\u00010\u00040\u00048\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006\u000e"}, d2 = {"Lorg/catrobat/catroid/ui/fragment/AddBrickFragment$Companion;", "", "()V", "ADD_BRICK_FRAGMENT_TAG", "", JvmProtoBufUtil.PLATFORM_TYPE_ID, "BUNDLE_ARGUMENTS_SELECTED_CATEGORY", "listIndexToFocus", "", TypeProxy.SilentConstruction.Appender.NEW_INSTANCE_METHOD_NAME, "Lorg/catrobat/catroid/ui/fragment/AddBrickFragment;", "selectedCategory", "addBrickListener", "Lorg/catrobat/catroid/ui/fragment/AddBrickFragment$OnAddBrickListener;", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        @JvmStatic
        public final AddBrickFragment newInstance(String selectedCategory, OnAddBrickListener addBrickListener) {
            AddBrickFragment addBrickFragment = new AddBrickFragment();
            Bundle bundle = new Bundle();
            bundle.putString(AddBrickFragment.BUNDLE_ARGUMENTS_SELECTED_CATEGORY, selectedCategory);
            addBrickFragment.setArguments(bundle);
            addBrickFragment.addBrickListener = addBrickListener;
            return addBrickFragment;
        }
    }

    @Override // androidx.fragment.app.Fragment
    public boolean onOptionsItemSelected(MenuItem item) {
        Intrinsics.checkNotNullParameter(item, "item");
        if (item.getItemId() == R.id.search) {
            OnAddBrickListener onAddBrickListener = this.addBrickListener;
            Intrinsics.checkNotNull(onAddBrickListener, "null cannot be cast to non-null type org.catrobat.catroid.ui.fragment.BrickCategoryFragment.OnCategorySelectedListener");
            BrickCategoryFragment.OnCategorySelectedListener onCategorySelectedListener = (BrickCategoryFragment.OnCategorySelectedListener) onAddBrickListener;
            Bundle arguments = getArguments();
            onCategorySelectedListener.onCategorySelected(arguments != null ? arguments.getString(BUNDLE_ARGUMENTS_SELECTED_CATEGORY) : null);
        }
        return super.onOptionsItemSelected(item);
    }

    /* JADX WARN: Type inference failed for: r5v3, types: [org.catrobat.catroid.ui.fragment.AddBrickFragment$showAddBrickPreviewDialog$wrapperScrollView$1] */
    private final void showAddBrickPreviewDialog(final Brick brick) {
        String string;
        final Context contextRequireContext = requireContext();
        Intrinsics.checkNotNullExpressionValue(contextRequireContext, "requireContext(...)");
        View viewInflate = LayoutInflater.from(contextRequireContext).inflate(R.layout.dialog_brick_context, (ViewGroup) null);
        View view = brick.getView(contextRequireContext);
        brick.disableSpinners();
        final int i = (int) (200 * contextRequireContext.getResources().getDisplayMetrics().density);
        ?? r5 = new ScrollView(contextRequireContext) { // from class: org.catrobat.catroid.ui.fragment.AddBrickFragment$showAddBrickPreviewDialog$wrapperScrollView$1
            @Override // android.widget.ScrollView, android.widget.FrameLayout, android.view.View
            protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
                int mode = View.MeasureSpec.getMode(heightMeasureSpec);
                int size = View.MeasureSpec.getSize(heightMeasureSpec);
                int i2 = i;
                if (size > i2 || mode == 0) {
                    heightMeasureSpec = View.MeasureSpec.makeMeasureSpec(i2, Integer.MIN_VALUE);
                }
                super.onMeasure(widthMeasureSpec, heightMeasureSpec);
            }
        };
        r5.addView(view);
        ((ViewGroup) viewInflate.findViewById(R.id.brick_view_container)).addView((View) r5);
        TextView textView = (TextView) viewInflate.findViewById(R.id.brick_description);
        textView.setText(BrickInfo.getDescription(brick));
        float f = contextRequireContext.getResources().getDisplayMetrics().density;
        ViewParent parent = textView != null ? textView.getParent() : null;
        ViewGroup viewGroup = parent instanceof ViewGroup ? (ViewGroup) parent : null;
        if (viewGroup != null) {
            TextView textView2 = new TextView(contextRequireContext);
            textView2.setText(contextRequireContext.getString(R.string.dialog_add_brick_system_info_button));
            textView2.setTextSize(13.0f);
            textView2.setTextColor(contextRequireContext.getResources().getColor(R.color.accent));
            textView2.setTypeface(Typeface.create(Typeface.DEFAULT, 1));
            int i2 = (int) (12 * f);
            textView2.setPadding(0, i2, 0, i2);
            textView2.setGravity(17);
            textView2.setOnClickListener(new View.OnClickListener() { // from class: org.catrobat.catroid.ui.fragment.AddBrickFragment$$ExternalSyntheticLambda0
                @Override // android.view.View.OnClickListener
                public final void onClick(View view2) {
                    AddBrickFragment.showAddBrickPreviewDialog$lambda$12$lambda$11(this.f$0, brick, view2);
                }
            });
            viewGroup.addView(textView2, viewGroup.indexOfChild(textView) + 1);
        }
        View viewFindViewById = viewInflate.findViewById(R.id.warning_layout);
        if (viewFindViewById != null) {
            viewFindViewById.setVisibility(8);
        }
        final boolean zIsFavorite = FavoriteBricksManager.INSTANCE.isFavorite(contextRequireContext, brick);
        if (zIsFavorite) {
            string = contextRequireContext.getString(R.string.dialog_add_brick_remove_from_favorites);
        } else {
            string = contextRequireContext.getString(R.string.dialog_add_brick_add_to_favorites);
        }
        Intrinsics.checkNotNull(string);
        new AlertDialog.Builder(contextRequireContext).setTitle(R.string.dialog_add_brick_title).setView(viewInflate).setPositiveButton(R.string.dialog_add_brick_add, new DialogInterface.OnClickListener() { // from class: org.catrobat.catroid.ui.fragment.AddBrickFragment$$ExternalSyntheticLambda1
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i3) {
                AddBrickFragment.showAddBrickPreviewDialog$lambda$13(brick, this, dialogInterface, i3);
            }
        }).setNeutralButton(string, new DialogInterface.OnClickListener() { // from class: org.catrobat.catroid.ui.fragment.AddBrickFragment$$ExternalSyntheticLambda2
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i3) {
                AddBrickFragment.showAddBrickPreviewDialog$lambda$14(zIsFavorite, contextRequireContext, brick, this, dialogInterface, i3);
            }
        }).setNegativeButton(R.string.dialog_add_brick_cancel, new DialogInterface.OnClickListener() { // from class: org.catrobat.catroid.ui.fragment.AddBrickFragment$$ExternalSyntheticLambda3
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i3) {
                dialogInterface.dismiss();
            }
        }).show();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void showAddBrickPreviewDialog$lambda$12$lambda$11(AddBrickFragment this$0, Brick brick, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNullParameter(brick, "$brick");
        this$0.showDetailedSystemInfoDialog(brick);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void showAddBrickPreviewDialog$lambda$13(Brick brick, AddBrickFragment this$0, DialogInterface dialogInterface, int i) {
        Intrinsics.checkNotNullParameter(brick, "$brick");
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        FragmentActivity fragmentActivityRequireActivity = this$0.requireActivity();
        Intrinsics.checkNotNullExpressionValue(fragmentActivityRequireActivity, "requireActivity(...)");
        OnAddBrickListener onAddBrickListener = this$0.addBrickListener;
        FragmentManager parentFragmentManager = this$0.getParentFragmentManager();
        Intrinsics.checkNotNullExpressionValue(parentFragmentManager, "getParentFragmentManager(...)");
        String ADD_BRICK_FRAGMENT_TAG2 = ADD_BRICK_FRAGMENT_TAG;
        Intrinsics.checkNotNullExpressionValue(ADD_BRICK_FRAGMENT_TAG2, "ADD_BRICK_FRAGMENT_TAG");
        AddBrickFragmentKt.addBrickToScript(brick, fragmentActivityRequireActivity, onAddBrickListener, parentFragmentManager, ADD_BRICK_FRAGMENT_TAG2);
        dialogInterface.dismiss();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void showAddBrickPreviewDialog$lambda$14(boolean z, Context context, Brick brick, AddBrickFragment this$0, DialogInterface dialogInterface, int i) {
        Intrinsics.checkNotNullParameter(context, "$context");
        Intrinsics.checkNotNullParameter(brick, "$brick");
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        if (z) {
            FavoriteBricksManager.INSTANCE.removeFavorite(context, brick);
            ToastUtil.showSuccess(this$0.getActivity(), context.getString(R.string.toast_removed_from_favorites));
        } else {
            FavoriteBricksManager.INSTANCE.addFavorite(context, brick);
            ToastUtil.showSuccess(this$0.getActivity(), context.getString(R.string.toast_added_to_favorites));
        }
        this$0.setupSelectedBrickCategory();
        dialogInterface.dismiss();
    }

    private final void showDetailedSystemInfoDialog(Brick brick) {
        Context contextRequireContext = requireContext();
        Intrinsics.checkNotNullExpressionValue(contextRequireContext, "requireContext(...)");
        float f = contextRequireContext.getResources().getDisplayMetrics().density;
        ScrollView scrollView = new ScrollView(contextRequireContext);
        int i = (int) (16 * f);
        int i2 = (int) (12 * f);
        scrollView.setPadding(i, i2, i, i2);
        TextView textView = new TextView(contextRequireContext);
        textView.setText(getBrickSystemInfo(brick));
        textView.setTextColor(contextRequireContext.getResources().getColor(R.color.solid_white));
        textView.setTextSize(13.0f);
        textView.setTypeface(Typeface.create(Typeface.MONOSPACE, 0));
        scrollView.addView(textView);
        new AlertDialog.Builder(contextRequireContext).setTitle(R.string.dialog_add_brick_system_info_title).setView(scrollView).setPositiveButton("OK", new DialogInterface.OnClickListener() { // from class: org.catrobat.catroid.ui.fragment.AddBrickFragment$$ExternalSyntheticLambda6
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i3) {
                dialogInterface.dismiss();
            }
        }).show();
    }

    private final String getBrickSystemInfo(Brick brick) {
        StringBuilder sb = new StringBuilder("Class Name:\n");
        sb.append(brick.getClass().getName()).append("\n---------------------------------------\n\n");
        Class<? super Object> superclass = brick.getClass().getSuperclass();
        String simpleName = superclass != null ? superclass.getSimpleName() : null;
        if (simpleName == null) {
            simpleName = "None";
        }
        sb.append("Superclass:\n");
        sb.append(simpleName).append("\n---------------------------------------\n\n");
        if (brick instanceof FormulaBrick) {
            Map<Brick.BrickField, Formula> allFormulaFieldsWithFormulas = ((FormulaBrick) brick).getAllFormulaFieldsWithFormulas();
            Intrinsics.checkNotNull(allFormulaFieldsWithFormulas);
            if (!allFormulaFieldsWithFormulas.isEmpty()) {
                sb.append("Formula Fields (BrickFields):\n");
                for (Map.Entry<Brick.BrickField, Formula> entry : allFormulaFieldsWithFormulas.entrySet()) {
                    sb.append("  * " + entry.getKey() + " = " + entry.getValue().getTrimmedFormulaString(requireContext()) + "\n");
                }
                sb.append("---------------------------------------\n\n");
            }
        }
        if (brick instanceof UserDataBrick) {
            Map<Brick.BrickData, UserData> allBrickDataWithValues = ((UserDataBrick) brick).getAllBrickDataWithValues();
            Intrinsics.checkNotNull(allBrickDataWithValues);
            if (!allBrickDataWithValues.isEmpty()) {
                sb.append("Data Fields (UserData):\n");
                for (Map.Entry<Brick.BrickData, UserData> entry2 : allBrickDataWithValues.entrySet()) {
                    sb.append("  * " + entry2.getKey() + " = " + entry2.getValue() + "\n");
                }
                sb.append("---------------------------------------\n\n");
            }
        }
        sb.append("Declared Fields:\n");
        Field[] declaredFields = brick.getClass().getDeclaredFields();
        Intrinsics.checkNotNull(declaredFields);
        if (declaredFields.length == 0) {
            sb.append("  * No declared fields found\n");
        } else {
            for (Field field : declaredFields) {
                try {
                    field.setAccessible(true);
                    String name2 = field.getName();
                    Intrinsics.checkNotNull(name2);
                    if (!StringsKt.startsWith$default(name2, "$", false, 2, (Object) null) && !Intrinsics.areEqual(name2, CommonClassNames.SERIAL_VERSION_UID_FIELD_NAME) && !Intrinsics.areEqual(name2, "Companion")) {
                        String simpleName2 = field.getType().getSimpleName();
                        Object obj = field.get(brick);
                        if (obj == null) {
                            obj = "null";
                        } else {
                            Intrinsics.checkNotNull(obj);
                        }
                        sb.append("  * " + name2 + " (" + simpleName2 + ") = " + obj + "\n");
                    }
                } catch (Exception unused) {
                }
            }
        }
        String string = sb.toString();
        Intrinsics.checkNotNullExpressionValue(string, "toString(...)");
        return string;
    }
}
