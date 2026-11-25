package org.catrobat.catroid.ui.fragment;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.cursoradapter.widget.CursorAdapter;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.ListFragment;
import com.google.android.gms.analytics.ecommerce.Promotion;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import kotlin.Metadata;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Ref;
import kotlin.text.Regex;
import kotlin.text.StringsKt;
import net.bytebuddy.implementation.auxiliary.TypeProxy;
import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.ProjectManager;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.content.bricks.Brick;
import org.catrobat.catroid.ui.BottomBar;
import org.catrobat.catroid.ui.SpriteActivity;
import org.catrobat.catroid.ui.ViewUtils;
import org.catrobat.catroid.ui.adapter.PrototypeBrickAdapter;
import org.catrobat.catroid.ui.settingsfragments.AccessibilityProfile;
import org.catrobat.catroid.ui.settingsfragments.SettingsFragment;
import org.catrobat.catroid.utils.ExtensionsKt;
import org.catrobat.catroid.utils.ToastUtil;
import org.jetbrains.kotlin.metadata.jvm.deserialization.JvmProtoBufUtil;
import org.jetbrains.kotlin.org.jline.reader.LineReader;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/ui/fragment/BrickSearchFragment.class
  classes14.dex
 */
/* JADX INFO: compiled from: BrickSearchFragment.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000\u0082\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\r\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u000e\u0018\u0000 72\u00020\u0001:\u00017B\u0005¢\u0006\u0002\u0010\u0002J\u0012\u0010\u001b\u001a\u00020\u000b2\b\u0010\u001c\u001a\u0004\u0018\u00010\u001dH\u0002J\u0006\u0010\u001e\u001a\u00020\u001fJ\u0018\u0010 \u001a\u00020\u001f2\u0006\u0010!\u001a\u00020\"2\u0006\u0010#\u001a\u00020$H\u0016J&\u0010%\u001a\u0004\u0018\u00010\u001d2\u0006\u0010#\u001a\u00020&2\b\u0010'\u001a\u0004\u0018\u00010(2\b\u0010)\u001a\u0004\u0018\u00010*H\u0016J\b\u0010+\u001a\u00020\u001fH\u0016J\u0010\u0010,\u001a\u00020\u001f2\u0006\u0010!\u001a\u00020\"H\u0016J\b\u0010-\u001a\u00020\u001fH\u0016J\b\u0010.\u001a\u00020\rH\u0002J\u0010\u0010/\u001a\u00020\u001f2\b\b\u0002\u0010\n\u001a\u00020\u000bJ\u0010\u00100\u001a\u00020\u001f2\u0006\u00101\u001a\u00020\u000bH\u0002J\u0010\u00102\u001a\u00020\u001f2\u0006\u00101\u001a\u00020\u000bH\u0002J\u0010\u00103\u001a\u00020\r2\u0006\u00104\u001a\u00020\tH\u0002J\u0010\u00105\u001a\u00020\u001f2\u0006\u00106\u001a\u00020\rH\u0002R\u0010\u0010\u0003\u001a\u0004\u0018\u00010\u0004X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u0005\u001a\u0004\u0018\u00010\u0006X\u0082\u000e¢\u0006\u0002\n\u0000R\u0014\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\t0\bX\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\n\u001a\u0004\u0018\u00010\u000bX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\rX\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u000e\u001a\u0004\u0018\u00010\u000fX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0010\u001a\u00020\u000bX\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u0011\u001a\u0004\u0018\u00010\u0012X\u0082\u000e¢\u0006\u0002\n\u0000R\u0014\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\t0\bX\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u0014\u001a\u0004\u0018\u00010\u0015X\u0082\u000e¢\u0006\u0002\n\u0000R\u0014\u0010\u0016\u001a\b\u0012\u0004\u0012\u00020\t0\bX\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u0017\u001a\u0004\u0018\u00010\u0018X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u0019\u001a\u0004\u0018\u00010\u001aX\u0082\u000e¢\u0006\u0002\n\u0000¨\u00068"}, d2 = {"Lorg/catrobat/catroid/ui/fragment/BrickSearchFragment;", "Landroidx/fragment/app/ListFragment;", "()V", "adapter", "Lorg/catrobat/catroid/ui/adapter/PrototypeBrickAdapter;", "addBrickListener", "Lorg/catrobat/catroid/ui/fragment/AddBrickFragment$OnAddBrickListener;", "availableBricks", "", "Lorg/catrobat/catroid/content/bricks/Brick;", "category", "", "emptyQuery", "", "previousActionBarTitle", "", "previousQuery", "queryTextListener", "Landroidx/appcompat/widget/SearchView$OnQueryTextListener;", "recentlyUsedBricks", "recentlyUsedTitle", "Landroid/widget/TextView;", "searchResults", "searchView", "Landroidx/appcompat/widget/SearchView;", "suggestionListener", "Landroidx/appcompat/widget/SearchView$OnSuggestionListener;", "findBrickString", Promotion.ACTION_VIEW, "Landroid/view/View;", "getRecentlyUsedBricks", "", "onCreateOptionsMenu", LineReader.MENU, "Landroid/view/Menu;", "inflater", "Landroid/view/MenuInflater;", "onCreateView", "Landroid/view/LayoutInflater;", "container", "Landroid/view/ViewGroup;", "savedInstanceState", "Landroid/os/Bundle;", "onDestroy", "onPrepareOptionsMenu", "onStart", "onlyBeginnerBricks", "prepareBrickList", "searchAndFillBrickList", "query", "searchBrick", "searchResultContains", "brick", "setShowProgressBar", "visible", "Companion", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class BrickSearchFragment extends ListFragment {
    private PrototypeBrickAdapter adapter;
    private AddBrickFragment.OnAddBrickListener addBrickListener;
    private String category;
    private CharSequence previousActionBarTitle;
    private SearchView.OnQueryTextListener queryTextListener;
    private TextView recentlyUsedTitle;
    private SearchView searchView;
    private SearchView.OnSuggestionListener suggestionListener;

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    public static final String BRICK_SEARCH_FRAGMENT_TAG = "BrickSearchFragment";
    private static int listIndexToFocus = -1;
    private List<Brick> availableBricks = new ArrayList();
    private List<Brick> recentlyUsedBricks = new ArrayList();
    private List<Brick> searchResults = new ArrayList();
    private volatile boolean emptyQuery = true;
    private volatile String previousQuery = "";

    @JvmStatic
    public static final BrickSearchFragment newInstance(AddBrickFragment.OnAddBrickListener onAddBrickListener, String str) {
        return INSTANCE.newInstance(onAddBrickListener, str);
    }

    @Override // androidx.fragment.app.ListFragment, androidx.fragment.app.Fragment
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Intrinsics.checkNotNullParameter(inflater, "inflater");
        View viewInflate = inflater.inflate(R.layout.fragment_brick_search, container, false);
        FragmentActivity activity = getActivity();
        AppCompatActivity appCompatActivity = activity instanceof AppCompatActivity ? (AppCompatActivity) activity : null;
        ActionBar supportActionBar = appCompatActivity != null ? appCompatActivity.getSupportActionBar() : null;
        this.previousActionBarTitle = supportActionBar != null ? supportActionBar.getTitle() : null;
        this.recentlyUsedTitle = (TextView) viewInflate.findViewById(R.id.recent_used_header);
        BottomBar.hideBottomBar(getActivity());
        setHasOptionsMenu(true);
        getRecentlyUsedBricks();
        String str = this.category;
        if (str != null) {
            prepareBrickList(str);
        }
        return viewInflate;
    }

    @Override // androidx.fragment.app.Fragment
    public void onStart() {
        super.onStart();
        if (listIndexToFocus != -1) {
            getListView().setSelection(listIndexToFocus);
            listIndexToFocus = -1;
        }
        getListView().setOnItemClickListener(new AdapterView.OnItemClickListener() { // from class: org.catrobat.catroid.ui.fragment.BrickSearchFragment$$ExternalSyntheticLambda0
            @Override // android.widget.AdapterView.OnItemClickListener
            public final void onItemClick(AdapterView adapterView, View view, int i, long j) {
                BrickSearchFragment.onStart$lambda$2(this.f$0, adapterView, view, i, j);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onStart$lambda$2(BrickSearchFragment this$0, AdapterView adapterView, View view, int i, long j) {
        Brick item;
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        PrototypeBrickAdapter prototypeBrickAdapter = this$0.adapter;
        if (prototypeBrickAdapter == null || (item = prototypeBrickAdapter.getItem(i)) == null) {
            return;
        }
        FragmentActivity activity = this$0.getActivity();
        Intrinsics.checkNotNull(activity, "null cannot be cast to non-null type org.catrobat.catroid.ui.SpriteActivity");
        AddBrickFragment.OnAddBrickListener onAddBrickListener = this$0.addBrickListener;
        FragmentManager parentFragmentManager = this$0.getParentFragmentManager();
        Intrinsics.checkNotNullExpressionValue(parentFragmentManager, "getParentFragmentManager(...)");
        String BRICK_SEARCH_FRAGMENT_TAG2 = BRICK_SEARCH_FRAGMENT_TAG;
        Intrinsics.checkNotNullExpressionValue(BRICK_SEARCH_FRAGMENT_TAG2, "BRICK_SEARCH_FRAGMENT_TAG");
        AddBrickFragmentKt.addBrickToScript(item, (SpriteActivity) activity, onAddBrickListener, parentFragmentManager, BRICK_SEARCH_FRAGMENT_TAG2);
    }

    @Override // androidx.fragment.app.Fragment
    public void onDestroy() {
        FragmentActivity activity = getActivity();
        AppCompatActivity appCompatActivity = activity instanceof AppCompatActivity ? (AppCompatActivity) activity : null;
        ActionBar supportActionBar = appCompatActivity != null ? appCompatActivity.getSupportActionBar() : null;
        if (supportActionBar != null && supportActionBar != null) {
            supportActionBar.setTitle(this.previousActionBarTitle);
        }
        ViewUtils.hideKeyboard(this.searchView);
        super.onDestroy();
    }

    @Override // androidx.fragment.app.Fragment
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        Intrinsics.checkNotNullParameter(menu, "menu");
        Intrinsics.checkNotNullParameter(inflater, "inflater");
        inflater.inflate(R.menu.menu_search, menu);
        View actionView = menu.findItem(R.id.search_bar).getActionView();
        Intrinsics.checkNotNull(actionView, "null cannot be cast to non-null type androidx.appcompat.widget.SearchView");
        SearchView searchView = (SearchView) actionView;
        searchView.setIconified(false);
        searchView.setQueryHint(searchView.getContext().getString(R.string.search_hint));
        this.searchResults.addAll(this.recentlyUsedBricks);
        PrototypeBrickAdapter prototypeBrickAdapter = new PrototypeBrickAdapter(this.searchResults);
        this.adapter = prototypeBrickAdapter;
        setListAdapter(prototypeBrickAdapter);
        getListView().setOnScrollListener(new AbsListView.OnScrollListener() { // from class: org.catrobat.catroid.ui.fragment.BrickSearchFragment.onCreateOptionsMenu.2
            @Override // android.widget.AbsListView.OnScrollListener
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                Intrinsics.checkNotNullParameter(view, "view");
            }

            @Override // android.widget.AbsListView.OnScrollListener
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                Intrinsics.checkNotNullParameter(view, "view");
                ViewUtils.hideKeyboard(BrickSearchFragment.this.searchView);
            }
        });
        this.searchView = searchView;
        if (searchView != null) {
            final Ref.ObjectRef objectRef = new Ref.ObjectRef();
            PrototypeBrickAdapter prototypeBrickAdapter2 = new PrototypeBrickAdapter(this.searchResults);
            this.adapter = prototypeBrickAdapter2;
            setListAdapter(prototypeBrickAdapter2);
            this.queryTextListener = new SearchView.OnQueryTextListener() { // from class: org.catrobat.catroid.ui.fragment.BrickSearchFragment.onCreateOptionsMenu.3
                /* JADX WARN: Type inference failed for: r3v0, types: [T, org.catrobat.catroid.ui.fragment.BrickSearchFragment$onCreateOptionsMenu$3$onQueryTextChange$1] */
                @Override // androidx.appcompat.widget.SearchView.OnQueryTextListener
                public boolean onQueryTextChange(final String query) {
                    Intrinsics.checkNotNullParameter(query, "query");
                    BrickSearchFragment.this.previousQuery = query;
                    TextView textView = BrickSearchFragment.this.recentlyUsedTitle;
                    if (textView != null) {
                        ExtensionsKt.setVisibleOrGone(textView, query.length() == 0);
                    }
                    Ref.ObjectRef<CountDownTimer> objectRef2 = objectRef;
                    final BrickSearchFragment brickSearchFragment = BrickSearchFragment.this;
                    objectRef2.element = new CountDownTimer() { // from class: org.catrobat.catroid.ui.fragment.BrickSearchFragment$onCreateOptionsMenu$3$onQueryTextChange$1
                        @Override // android.os.CountDownTimer
                        public void onTick(long millisUntilFinished) {
                        }

                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                        {
                            super(400L, 500L);
                        }

                        @Override // android.os.CountDownTimer
                        public void onFinish() {
                            if (Intrinsics.areEqual(query, brickSearchFragment.previousQuery)) {
                                brickSearchFragment.searchAndFillBrickList(query);
                            }
                        }
                    };
                    String str = query;
                    BrickSearchFragment.this.emptyQuery = str.length() == 0;
                    if (str.length() == 0) {
                        BrickSearchFragment.this.searchResults.clear();
                        BrickSearchFragment.this.searchResults.addAll(BrickSearchFragment.this.recentlyUsedBricks);
                        PrototypeBrickAdapter prototypeBrickAdapter3 = BrickSearchFragment.this.adapter;
                        if (prototypeBrickAdapter3 != null) {
                            PrototypeBrickAdapter.replaceList$default(prototypeBrickAdapter3, BrickSearchFragment.this.searchResults, null, 2, null);
                        }
                        objectRef.element.cancel();
                        BrickSearchFragment.this.setShowProgressBar(false);
                    } else {
                        objectRef.element.start();
                        BrickSearchFragment.this.setShowProgressBar(true);
                    }
                    return true;
                }

                @Override // androidx.appcompat.widget.SearchView.OnQueryTextListener
                public boolean onQueryTextSubmit(String query) {
                    Intrinsics.checkNotNullParameter(query, "query");
                    BrickSearchFragment.this.searchResults.clear();
                    BrickSearchFragment.this.searchBrick(query);
                    PrototypeBrickAdapter prototypeBrickAdapter3 = BrickSearchFragment.this.adapter;
                    if (prototypeBrickAdapter3 != null) {
                        PrototypeBrickAdapter.replaceList$default(prototypeBrickAdapter3, BrickSearchFragment.this.searchResults, null, 2, null);
                    }
                    if (!BrickSearchFragment.this.searchResults.isEmpty()) {
                        SearchView searchView2 = BrickSearchFragment.this.searchView;
                        if (searchView2 == null) {
                            return true;
                        }
                        searchView2.clearFocus();
                        return true;
                    }
                    Context context = BrickSearchFragment.this.getContext();
                    Context context2 = BrickSearchFragment.this.getContext();
                    ToastUtil.showError(context, context2 != null ? context2.getString(R.string.no_results_found) : null);
                    return true;
                }
            };
            this.suggestionListener = new SearchView.OnSuggestionListener() { // from class: org.catrobat.catroid.ui.fragment.BrickSearchFragment.onCreateOptionsMenu.4
                @Override // androidx.appcompat.widget.SearchView.OnSuggestionListener
                public boolean onSuggestionSelect(int position) {
                    return false;
                }

                @Override // androidx.appcompat.widget.SearchView.OnSuggestionListener
                public boolean onSuggestionClick(int position) {
                    CursorAdapter suggestionsAdapter;
                    SearchView searchView2 = BrickSearchFragment.this.searchView;
                    Cursor cursor = (searchView2 == null || (suggestionsAdapter = searchView2.getSuggestionsAdapter()) == null) ? null : suggestionsAdapter.getCursor();
                    if (cursor != null) {
                        cursor.moveToPosition(position);
                    }
                    String string = cursor != null ? cursor.getString(2) : null;
                    SearchView searchView3 = BrickSearchFragment.this.searchView;
                    if (searchView3 != null) {
                        searchView3.setQuery(string, true);
                    }
                    return true;
                }
            };
        }
        SearchView searchView2 = this.searchView;
        if (searchView2 != null) {
            searchView2.setOnQueryTextListener(this.queryTextListener);
        }
        SearchView searchView3 = this.searchView;
        if (searchView3 != null) {
            searchView3.setOnSuggestionListener(this.suggestionListener);
        }
        SearchView searchView4 = this.searchView;
        if (searchView4 != null) {
            searchView4.requestFocus();
        }
        super.onCreateOptionsMenu(menu, inflater);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void setShowProgressBar(boolean visible) {
        ProgressBar progressBar;
        if (visible) {
            View view = getView();
            progressBar = view != null ? (ProgressBar) view.findViewById(R.id.progress_bar) : null;
            if (progressBar == null) {
                return;
            }
            progressBar.setVisibility(0);
            return;
        }
        View view2 = getView();
        progressBar = view2 != null ? (ProgressBar) view2.findViewById(R.id.progress_bar) : null;
        if (progressBar == null) {
            return;
        }
        progressBar.setVisibility(4);
    }

    @Override // androidx.fragment.app.Fragment
    public void onPrepareOptionsMenu(Menu menu) {
        Intrinsics.checkNotNullParameter(menu, "menu");
        super.onPrepareOptionsMenu(menu);
        int size = menu.size();
        for (int i = 0; i < size; i++) {
            menu.getItem(i).setVisible(false);
        }
        menu.findItem(R.id.search_bar).setVisible(true);
    }

    private final boolean onlyBeginnerBricks() {
        return PreferenceManager.getDefaultSharedPreferences(getActivity()).getBoolean(AccessibilityProfile.BEGINNER_BRICKS, false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void searchAndFillBrickList(String query) {
        this.searchResults.clear();
        if (this.emptyQuery) {
            return;
        }
        PrototypeBrickAdapter prototypeBrickAdapter = this.adapter;
        if (prototypeBrickAdapter != null) {
            PrototypeBrickAdapter.replaceList$default(prototypeBrickAdapter, this.searchResults, null, 2, null);
        }
        searchBrick(query);
        if (this.searchResults.isEmpty()) {
            Context context = getContext();
            Context context2 = getContext();
            ToastUtil.showError(context, context2 != null ? context2.getString(R.string.no_results_found) : null);
        }
        if (this.emptyQuery) {
            return;
        }
        PrototypeBrickAdapter prototypeBrickAdapter2 = this.adapter;
        if (prototypeBrickAdapter2 != null) {
            PrototypeBrickAdapter.replaceList$default(prototypeBrickAdapter2, this.searchResults, null, 2, null);
        }
        setShowProgressBar(false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void searchBrick(String query) {
        Context context = getContext();
        if (context == null) {
            return;
        }
        for (Brick brick : this.availableBricks) {
            Locale ROOT = Locale.ROOT;
            Intrinsics.checkNotNullExpressionValue(ROOT, "ROOT");
            String lowerCase = query.toLowerCase(ROOT);
            Intrinsics.checkNotNullExpressionValue(lowerCase, "toLowerCase(...)");
            Regex regex = new Regex(".*" + new Regex("\\s").replace(lowerCase, ".*") + ".*");
            View view = brick.getView(context);
            if (view != null && regex.containsMatchIn(findBrickString(view)) && !searchResultContains(brick)) {
                this.searchResults.add(brick);
            }
        }
    }

    private final boolean searchResultContains(Brick brick) {
        Iterator<T> it = this.searchResults.iterator();
        while (it.hasNext()) {
            if (Intrinsics.areEqual(brick.getClass(), ((Brick) it.next()).getClass())) {
                return true;
            }
        }
        return false;
    }

    private final String findBrickString(View view) {
        String str = "";
        if (view == null) {
            return "";
        }
        if (view instanceof ViewGroup) {
            ViewGroup viewGroup = (ViewGroup) view;
            int childCount = viewGroup.getChildCount();
            for (int i = 0; i < childCount; i++) {
                String strFindBrickString = findBrickString(viewGroup.getChildAt(i));
                if (!StringsKt.isBlank(strFindBrickString)) {
                    str = str + strFindBrickString;
                }
            }
        } else if (view instanceof TextView) {
            String string = ((TextView) view).getText().toString();
            Locale ROOT = Locale.ROOT;
            Intrinsics.checkNotNullExpressionValue(ROOT, "ROOT");
            String lowerCase = string.toLowerCase(ROOT);
            Intrinsics.checkNotNullExpressionValue(lowerCase, "toLowerCase(...)");
            return lowerCase;
        }
        return str;
    }

    public final void getRecentlyUsedBricks() {
        CategoryBeginnerBricksFactory categoryBeginnerBricksFactory = onlyBeginnerBricks() ? new CategoryBeginnerBricksFactory() : new CategoryBricksFactory();
        Sprite backgroundSprite = ProjectManager.getInstance().getCurrentlyEditedScene().getBackgroundSprite();
        Sprite currentSprite = ProjectManager.getInstance().getCurrentSprite();
        List<Brick> list = this.recentlyUsedBricks;
        String string = requireContext().getString(R.string.category_recently_used);
        Intrinsics.checkNotNullExpressionValue(string, "getString(...)");
        boolean zEquals = backgroundSprite.equals(currentSprite);
        Context contextRequireContext = requireContext();
        Intrinsics.checkNotNullExpressionValue(contextRequireContext, "requireContext(...)");
        list.addAll(categoryBeginnerBricksFactory.getBricks(string, zEquals, contextRequireContext));
    }

    public static /* synthetic */ void prepareBrickList$default(BrickSearchFragment brickSearchFragment, String str, int i, Object obj) {
        if ((i & 1) != 0) {
            str = "";
        }
        brickSearchFragment.prepareBrickList(str);
    }

    public final void prepareBrickList(String category) {
        Intrinsics.checkNotNullParameter(category, "category");
        CategoryBeginnerBricksFactory categoryBeginnerBricksFactory = onlyBeginnerBricks() ? new CategoryBeginnerBricksFactory() : new CategoryBricksFactory();
        Sprite backgroundSprite = ProjectManager.getInstance().getCurrentlyEditedScene().getBackgroundSprite();
        Sprite currentSprite = ProjectManager.getInstance().getCurrentSprite();
        Context context = getContext();
        if (!Intrinsics.areEqual(category, context != null ? context.getString(R.string.category_search_bricks) : null)) {
            this.availableBricks.clear();
            List<Brick> list = this.availableBricks;
            boolean zEquals = backgroundSprite.equals(currentSprite);
            Context contextRequireContext = requireContext();
            Intrinsics.checkNotNullExpressionValue(contextRequireContext, "requireContext(...)");
            list.addAll(categoryBeginnerBricksFactory.getBricks(category, zEquals, contextRequireContext));
            return;
        }
        List<Brick> list2 = this.availableBricks;
        String string = requireContext().getString(R.string.category_recently_used);
        Intrinsics.checkNotNullExpressionValue(string, "getString(...)");
        boolean zEquals2 = backgroundSprite.equals(currentSprite);
        Context contextRequireContext2 = requireContext();
        Intrinsics.checkNotNullExpressionValue(contextRequireContext2, "requireContext(...)");
        list2.addAll(categoryBeginnerBricksFactory.getBricks(string, zEquals2, contextRequireContext2));
        List<Brick> list3 = this.availableBricks;
        String string2 = requireContext().getString(R.string.category_event);
        Intrinsics.checkNotNullExpressionValue(string2, "getString(...)");
        boolean zEquals3 = backgroundSprite.equals(currentSprite);
        Context contextRequireContext3 = requireContext();
        Intrinsics.checkNotNullExpressionValue(contextRequireContext3, "requireContext(...)");
        list3.addAll(categoryBeginnerBricksFactory.getBricks(string2, zEquals3, contextRequireContext3));
        List<Brick> list4 = this.availableBricks;
        String string3 = requireContext().getString(R.string.category_control);
        Intrinsics.checkNotNullExpressionValue(string3, "getString(...)");
        boolean zEquals4 = backgroundSprite.equals(currentSprite);
        Context contextRequireContext4 = requireContext();
        Intrinsics.checkNotNullExpressionValue(contextRequireContext4, "requireContext(...)");
        list4.addAll(categoryBeginnerBricksFactory.getBricks(string3, zEquals4, contextRequireContext4));
        List<Brick> list5 = this.availableBricks;
        String string4 = requireContext().getString(R.string.category_motion);
        Intrinsics.checkNotNullExpressionValue(string4, "getString(...)");
        boolean zEquals5 = backgroundSprite.equals(currentSprite);
        Context contextRequireContext5 = requireContext();
        Intrinsics.checkNotNullExpressionValue(contextRequireContext5, "requireContext(...)");
        list5.addAll(categoryBeginnerBricksFactory.getBricks(string4, zEquals5, contextRequireContext5));
        List<Brick> list6 = this.availableBricks;
        String string5 = requireContext().getString(R.string.category_sound);
        Intrinsics.checkNotNullExpressionValue(string5, "getString(...)");
        boolean zEquals6 = backgroundSprite.equals(currentSprite);
        Context contextRequireContext6 = requireContext();
        Intrinsics.checkNotNullExpressionValue(contextRequireContext6, "requireContext(...)");
        list6.addAll(categoryBeginnerBricksFactory.getBricks(string5, zEquals6, contextRequireContext6));
        List<Brick> list7 = this.availableBricks;
        String string6 = requireContext().getString(R.string.category_looks);
        Intrinsics.checkNotNullExpressionValue(string6, "getString(...)");
        boolean zEquals7 = backgroundSprite.equals(currentSprite);
        Context contextRequireContext7 = requireContext();
        Intrinsics.checkNotNullExpressionValue(contextRequireContext7, "requireContext(...)");
        list7.addAll(categoryBeginnerBricksFactory.getBricks(string6, zEquals7, contextRequireContext7));
        if (!onlyBeginnerBricks()) {
            List<Brick> list8 = this.availableBricks;
            String string7 = requireContext().getString(R.string.category_pen);
            Intrinsics.checkNotNullExpressionValue(string7, "getString(...)");
            boolean zEquals8 = backgroundSprite.equals(currentSprite);
            Context contextRequireContext8 = requireContext();
            Intrinsics.checkNotNullExpressionValue(contextRequireContext8, "requireContext(...)");
            list8.addAll(categoryBeginnerBricksFactory.getBricks(string7, zEquals8, contextRequireContext8));
        }
        List<Brick> list9 = this.availableBricks;
        String string8 = requireContext().getString(R.string.category_data);
        Intrinsics.checkNotNullExpressionValue(string8, "getString(...)");
        boolean zEquals9 = backgroundSprite.equals(currentSprite);
        Context contextRequireContext9 = requireContext();
        Intrinsics.checkNotNullExpressionValue(contextRequireContext9, "requireContext(...)");
        list9.addAll(categoryBeginnerBricksFactory.getBricks(string8, zEquals9, contextRequireContext9));
        List<Brick> list10 = this.availableBricks;
        String string9 = requireContext().getString(R.string.category_device);
        Intrinsics.checkNotNullExpressionValue(string9, "getString(...)");
        boolean zEquals10 = backgroundSprite.equals(currentSprite);
        Context contextRequireContext10 = requireContext();
        Intrinsics.checkNotNullExpressionValue(contextRequireContext10, "requireContext(...)");
        list10.addAll(categoryBeginnerBricksFactory.getBricks(string9, zEquals10, contextRequireContext10));
        if (!onlyBeginnerBricks()) {
            List<Brick> list11 = this.availableBricks;
            String string10 = requireContext().getString(R.string.category_user_bricks);
            Intrinsics.checkNotNullExpressionValue(string10, "getString(...)");
            boolean zEquals11 = backgroundSprite.equals(currentSprite);
            Context contextRequireContext11 = requireContext();
            Intrinsics.checkNotNullExpressionValue(contextRequireContext11, "requireContext(...)");
            list11.addAll(categoryBeginnerBricksFactory.getBricks(string10, zEquals11, contextRequireContext11));
        }
        if (SettingsFragment.isTestSharedPreferenceEnabled(getActivity())) {
            List<Brick> list12 = this.availableBricks;
            String string11 = requireContext().getString(R.string.category_assertions);
            Intrinsics.checkNotNullExpressionValue(string11, "getString(...)");
            boolean zEquals12 = backgroundSprite.equals(currentSprite);
            Context contextRequireContext12 = requireContext();
            Intrinsics.checkNotNullExpressionValue(contextRequireContext12, "requireContext(...)");
            list12.addAll(categoryBeginnerBricksFactory.getBricks(string11, zEquals12, contextRequireContext12));
        }
    }

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/ui/fragment/BrickSearchFragment$Companion.class
      classes14.dex
     */
    /* JADX INFO: compiled from: BrickSearchFragment.kt */
    @Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u001c\u0010\b\u001a\u00020\t2\b\u0010\n\u001a\u0004\u0018\u00010\u000b2\b\u0010\f\u001a\u0004\u0018\u00010\u0004H\u0007R\u0018\u0010\u0003\u001a\n \u0005*\u0004\u0018\u00010\u00040\u00048\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006\r"}, d2 = {"Lorg/catrobat/catroid/ui/fragment/BrickSearchFragment$Companion;", "", "()V", "BRICK_SEARCH_FRAGMENT_TAG", "", JvmProtoBufUtil.PLATFORM_TYPE_ID, "listIndexToFocus", "", TypeProxy.SilentConstruction.Appender.NEW_INSTANCE_METHOD_NAME, "Lorg/catrobat/catroid/ui/fragment/BrickSearchFragment;", "addBrickListener", "Lorg/catrobat/catroid/ui/fragment/AddBrickFragment$OnAddBrickListener;", "selectedCategory", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        @JvmStatic
        public final BrickSearchFragment newInstance(AddBrickFragment.OnAddBrickListener addBrickListener, String selectedCategory) {
            BrickSearchFragment brickSearchFragment = new BrickSearchFragment();
            brickSearchFragment.category = selectedCategory;
            brickSearchFragment.addBrickListener = addBrickListener;
            return brickSearchFragment;
        }
    }
}
