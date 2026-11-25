package org.catrobat.paintroid;

import android.content.Context;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.RelativeLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.ViewPager;
import com.badlogic.gdx.graphics.GL20;
import com.google.android.gms.cast.MediaTrack;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import java.util.Locale;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.ArraysKt;
import kotlin.jvm.internal.Intrinsics;
import org.antlr.stringtemplate.language.ASTExpr;
import org.catrobat.paintroid.intro.IntroPageViewAdapter;
import org.catrobat.paintroid.tools.ToolType;

/* JADX INFO: compiled from: WelcomeActivity.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000`\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0015\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010\u001b\u001a\u00020\u001c2\u0006\u0010\u001d\u001a\u00020\u0007H\u0002J\b\u0010\u001e\u001a\u00020\u001cH\u0002J\b\u0010\u001f\u001a\u00020 H\u0002J\u0010\u0010!\u001a\u00020\u00072\u0006\u0010\"\u001a\u00020\u0007H\u0002J\u0010\u0010#\u001a\u00020\u00072\u0006\u0010$\u001a\u00020\u0007H\u0002J\b\u0010%\u001a\u00020\u001cH\u0002J\u0010\u0010&\u001a\u00020 2\u0006\u0010'\u001a\u00020(H\u0002J\b\u0010)\u001a\u00020\u001cH\u0016J\u0012\u0010*\u001a\u00020\u001c2\b\u0010+\u001a\u0004\u0018\u00010,H\u0014J\u0018\u0010-\u001a\u00020\u001c2\u0006\u0010.\u001a\u00020/2\u0006\u00100\u001a\u00020/H\u0002J\u0018\u00101\u001a\u00020\u001c2\u0006\u0010.\u001a\u00020/2\u0006\u00100\u001a\u00020/H\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\u0007X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\nX\u0082.¢\u0006\u0002\n\u0000R$\u0010\u000b\u001a\u00020\f8\u0006@\u0006X\u0087.¢\u0006\u0014\n\u0000\u0012\u0004\b\r\u0010\u0002\u001a\u0004\b\u000e\u0010\u000f\"\u0004\b\u0010\u0010\u0011R$\u0010\u0012\u001a\u00020\u00138\u0006@\u0006X\u0087.¢\u0006\u0014\n\u0000\u0012\u0004\b\u0014\u0010\u0002\u001a\u0004\b\u0015\u0010\u0016\"\u0004\b\u0017\u0010\u0018R\u000e\u0010\u0019\u001a\u00020\u001aX\u0082\u000e¢\u0006\u0002\n\u0000¨\u00062"}, d2 = {"Lorg/catrobat/paintroid/WelcomeActivity;", "Landroidx/appcompat/app/AppCompatActivity;", "()V", "btnNext", "Landroidx/appcompat/widget/AppCompatButton;", "btnSkip", "colorActive", "", "colorInactive", "dotsLayout", "Landroidx/appcompat/widget/LinearLayoutCompat;", "layouts", "", "getLayouts$annotations", "getLayouts", "()[I", "setLayouts", "([I)V", "viewPager", "Landroidx/viewpager/widget/ViewPager;", "getViewPager$annotations", "getViewPager", "()Landroidx/viewpager/widget/ViewPager;", "setViewPager", "(Landroidx/viewpager/widget/ViewPager;)V", "viewPagerPageChangeListener", "Landroidx/viewpager/widget/ViewPager$OnPageChangeListener;", "addBottomDots", "", "currentPage", "changeStatusBarColor", "defaultLocaleIsRTL", "", "getDotsIndex", "position", "getItem", ASTExpr.DEFAULT_INDEX_VARIABLE_NAME, "initViewPager", "isRTL", "context", "Landroid/content/Context;", "onBackPressed", "onCreate", "savedInstanceState", "Landroid/os/Bundle;", "setUpNavigationView", "head", "Landroidx/appcompat/widget/AppCompatTextView;", MediaTrack.ROLE_DESCRIPTION, "setUpUndoAndRedoButtons", "Paintroid_signedRelease"}, k = 1, mv = {1, 5, 1}, xi = 48)
public final class WelcomeActivity extends AppCompatActivity {
    private AppCompatButton btnNext;
    private AppCompatButton btnSkip;
    private int colorActive;
    private int colorInactive;
    private LinearLayoutCompat dotsLayout;
    public int[] layouts;
    public ViewPager viewPager;
    private ViewPager.OnPageChangeListener viewPagerPageChangeListener = new WelcomeActivity$viewPagerPageChangeListener$1(this);

    public static /* synthetic */ void getLayouts$annotations() {
    }

    public static /* synthetic */ void getViewPager$annotations() {
    }

    public final ViewPager getViewPager() {
        ViewPager viewPager = this.viewPager;
        if (viewPager != null) {
            return viewPager;
        }
        Intrinsics.throwUninitializedPropertyAccessException("viewPager");
        return null;
    }

    public final void setViewPager(ViewPager viewPager) {
        Intrinsics.checkNotNullParameter(viewPager, "<set-?>");
        this.viewPager = viewPager;
    }

    public final int[] getLayouts() {
        int[] iArr = this.layouts;
        if (iArr != null) {
            return iArr;
        }
        Intrinsics.throwUninitializedPropertyAccessException("layouts");
        return null;
    }

    public final void setLayouts(int[] iArr) {
        Intrinsics.checkNotNullParameter(iArr, "<set-?>");
        this.layouts = iArr;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void setUpUndoAndRedoButtons(final AppCompatTextView head, final AppCompatTextView description) {
        View viewFindViewById = findViewById(R.id.pocketpaint_intro_possibilities_topbar);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById, "findViewById(R.id.pocket…tro_possibilities_topbar)");
        AppBarLayout appBarLayout = (AppBarLayout) viewFindViewById;
        View viewFindViewById2 = appBarLayout.findViewById(R.id.pocketpaint_btn_top_undo);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById2, "topBar.findViewById(R.id.pocketpaint_btn_top_undo)");
        View viewFindViewById3 = appBarLayout.findViewById(R.id.pocketpaint_btn_top_redo);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById3, "topBar.findViewById(R.id.pocketpaint_btn_top_redo)");
        ((AppCompatImageButton) viewFindViewById2).setOnClickListener(new View.OnClickListener() { // from class: org.catrobat.paintroid.WelcomeActivity$$ExternalSyntheticLambda0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                WelcomeActivity.m5890setUpUndoAndRedoButtons$lambda0(head, description, view);
            }
        });
        ((AppCompatImageButton) viewFindViewById3).setOnClickListener(new View.OnClickListener() { // from class: org.catrobat.paintroid.WelcomeActivity$$ExternalSyntheticLambda1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                WelcomeActivity.m5891setUpUndoAndRedoButtons$lambda1(head, description, view);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: setUpUndoAndRedoButtons$lambda-0, reason: not valid java name */
    public static final void m5890setUpUndoAndRedoButtons$lambda0(AppCompatTextView head, AppCompatTextView description, View view) {
        Intrinsics.checkNotNullParameter(head, "$head");
        Intrinsics.checkNotNullParameter(description, "$description");
        head.setText(ToolType.UNDO.getNameResource());
        description.setText(ToolType.UNDO.getHelpTextResource());
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: setUpUndoAndRedoButtons$lambda-1, reason: not valid java name */
    public static final void m5891setUpUndoAndRedoButtons$lambda1(AppCompatTextView head, AppCompatTextView description, View view) {
        Intrinsics.checkNotNullParameter(head, "$head");
        Intrinsics.checkNotNullParameter(description, "$description");
        head.setText(ToolType.REDO.getNameResource());
        description.setText(ToolType.REDO.getHelpTextResource());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void setUpNavigationView(final AppCompatTextView head, final AppCompatTextView description) {
        View viewFindViewById = findViewById(R.id.pocketpaint_intro_possibilities_bottom_bar);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById, "findViewById(R.id.pocket…possibilities_bottom_bar)");
        View viewFindViewById2 = ((RelativeLayout) viewFindViewById).findViewById(R.id.pocketpaint_bottom_navigation);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById2, "relativeLayout.findViewB…tpaint_bottom_navigation)");
        ((BottomNavigationView) viewFindViewById2).setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() { // from class: org.catrobat.paintroid.WelcomeActivity$$ExternalSyntheticLambda4
            @Override // com.google.android.material.bottomnavigation.BottomNavigationView.OnNavigationItemSelectedListener
            public final boolean onNavigationItemSelected(MenuItem menuItem) {
                return WelcomeActivity.m5889setUpNavigationView$lambda2(head, description, this, menuItem);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: setUpNavigationView$lambda-2, reason: not valid java name */
    public static final boolean m5889setUpNavigationView$lambda2(AppCompatTextView head, AppCompatTextView description, WelcomeActivity this$0, MenuItem menuItem) {
        Intrinsics.checkNotNullParameter(head, "$head");
        Intrinsics.checkNotNullParameter(description, "$description");
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNullParameter(menuItem, "menuItem");
        head.setText(menuItem.getTitle());
        int itemId = menuItem.getItemId();
        if (itemId == R.id.action_tools) {
            description.setText(this$0.getResources().getText(R.string.intro_bottom_navigation_tools_description));
            return false;
        }
        if (itemId == R.id.action_current_tool) {
            description.setText(this$0.getResources().getText(R.string.intro_bottom_navigation_current_description));
            return false;
        }
        if (itemId == R.id.action_color_picker) {
            description.setText(this$0.getResources().getText(R.string.intro_bottom_navigation_color_description));
            return false;
        }
        description.setText(this$0.getResources().getText(R.string.intro_bottom_navigation_layers_description));
        return false;
    }

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.PocketPaintWelcomeActivityTheme);
        super.onCreate(savedInstanceState);
        getWindow().getDecorView().setSystemUiVisibility(GL20.GL_INVALID_ENUM);
        if (isInMultiWindowMode()) {
            setResult(10);
            finish();
            return;
        }
        setContentView(R.layout.activity_pocketpaint_welcome);
        View viewFindViewById = findViewById(R.id.pocketpaint_view_pager);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById, "findViewById(R.id.pocketpaint_view_pager)");
        setViewPager((ViewPager) viewFindViewById);
        View viewFindViewById2 = findViewById(R.id.pocketpaint_layout_dots);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById2, "findViewById(R.id.pocketpaint_layout_dots)");
        this.dotsLayout = (LinearLayoutCompat) viewFindViewById2;
        View viewFindViewById3 = findViewById(R.id.pocketpaint_btn_skip);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById3, "findViewById(R.id.pocketpaint_btn_skip)");
        this.btnSkip = (AppCompatButton) viewFindViewById3;
        View viewFindViewById4 = findViewById(R.id.pocketpaint_btn_next);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById4, "findViewById(R.id.pocketpaint_btn_next)");
        this.btnNext = (AppCompatButton) viewFindViewById4;
        WelcomeActivity welcomeActivity = this;
        this.colorActive = ContextCompat.getColor(welcomeActivity, R.color.pocketpaint_welcome_dot_active);
        this.colorInactive = ContextCompat.getColor(welcomeActivity, R.color.pocketpaint_welcome_dot_inactive);
        setLayouts(new int[]{R.layout.pocketpaint_slide_intro_welcome, R.layout.pocketpaint_slide_intro_possibilities, R.layout.pocketpaint_slide_intro_tools_selection, R.layout.pocketpaint_slide_intro_landscape, R.layout.pocketpaint_slide_intro_getstarted});
        changeStatusBarColor();
        initViewPager();
        AppCompatButton appCompatButton = this.btnSkip;
        AppCompatButton appCompatButton2 = null;
        if (appCompatButton == null) {
            Intrinsics.throwUninitializedPropertyAccessException("btnSkip");
            appCompatButton = null;
        }
        appCompatButton.setOnClickListener(new View.OnClickListener() { // from class: org.catrobat.paintroid.WelcomeActivity$$ExternalSyntheticLambda2
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                WelcomeActivity.m5887onCreate$lambda3(this.f$0, view);
            }
        });
        AppCompatButton appCompatButton3 = this.btnNext;
        if (appCompatButton3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("btnNext");
        } else {
            appCompatButton2 = appCompatButton3;
        }
        appCompatButton2.setOnClickListener(new View.OnClickListener() { // from class: org.catrobat.paintroid.WelcomeActivity$$ExternalSyntheticLambda3
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                WelcomeActivity.m5888onCreate$lambda4(this.f$0, view);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: onCreate$lambda-3, reason: not valid java name */
    public static final void m5887onCreate$lambda3(WelcomeActivity this$0, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.finish();
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: onCreate$lambda-4, reason: not valid java name */
    public static final void m5888onCreate$lambda4(WelcomeActivity this$0, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        int item = this$0.getItem(1);
        boolean z = item > this$0.getLayouts().length - 1;
        if (this$0.isRTL(this$0)) {
            item = this$0.getItem(-1);
            z = item < 0;
        }
        if (z) {
            this$0.finish();
        } else {
            this$0.getViewPager().setCurrentItem(item);
        }
    }

    private final void initViewPager() {
        WelcomeActivity welcomeActivity = this;
        if (isRTL(welcomeActivity)) {
            ArraysKt.reverse(getLayouts());
        }
        getViewPager().setAdapter(new IntroPageViewAdapter(getLayouts()));
        getViewPager().addOnPageChangeListener(this.viewPagerPageChangeListener);
        if (isRTL(welcomeActivity)) {
            getViewPager().setCurrentItem(getLayouts().length);
            addBottomDots(getLayouts().length - 1);
            return;
        }
        addBottomDots(0);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void addBottomDots(int currentPage) {
        AppCompatTextView appCompatTextView;
        int length = getLayouts().length;
        AppCompatTextView[] appCompatTextViewArr = new AppCompatTextView[length];
        int dotsIndex = getDotsIndex(currentPage);
        LinearLayoutCompat linearLayoutCompat = this.dotsLayout;
        if (linearLayoutCompat == null) {
            Intrinsics.throwUninitializedPropertyAccessException("dotsLayout");
            linearLayoutCompat = null;
        }
        linearLayoutCompat.removeAllViews();
        int i = length - 1;
        if (i >= 0) {
            int i2 = 0;
            while (true) {
                int i3 = i2 + 1;
                AppCompatTextView appCompatTextView2 = new AppCompatTextView(this);
                appCompatTextView2.setText("•");
                appCompatTextView2.setTextSize(30.0f);
                appCompatTextView2.setTextColor(this.colorInactive);
                Unit unit = Unit.INSTANCE;
                appCompatTextViewArr[i2] = appCompatTextView2;
                LinearLayoutCompat linearLayoutCompat2 = this.dotsLayout;
                if (linearLayoutCompat2 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("dotsLayout");
                    linearLayoutCompat2 = null;
                }
                linearLayoutCompat2.addView(appCompatTextViewArr[i2]);
                if (i3 > i) {
                    break;
                } else {
                    i2 = i3;
                }
            }
        }
        if ((length == 0) || (appCompatTextView = appCompatTextViewArr[dotsIndex]) == null) {
            return;
        }
        appCompatTextView.setTextColor(this.colorActive);
    }

    private final int getItem(int i) {
        return getViewPager().getCurrentItem() + i;
    }

    private final void changeStatusBarColor() {
        Window window = getWindow();
        window.addFlags(Integer.MIN_VALUE);
        window.setStatusBarColor(0);
    }

    private final boolean defaultLocaleIsRTL() {
        Locale locale = Locale.getDefault();
        String string = locale.toString();
        Intrinsics.checkNotNullExpressionValue(string, "locale.toString()");
        if (string.length() == 0) {
            return false;
        }
        byte directionality = Character.getDirectionality(locale.getDisplayName().charAt(0));
        return directionality == 1 || directionality == 2;
    }

    private final boolean isRTL(Context context) {
        return context.getResources().getConfiguration().getLayoutDirection() == 1 || defaultLocaleIsRTL();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final int getDotsIndex(int position) {
        return isRTL(this) ? (getLayouts().length - position) - 1 : position;
    }

    @Override // androidx.activity.ComponentActivity, android.app.Activity
    public void onBackPressed() {
        finish();
    }
}
