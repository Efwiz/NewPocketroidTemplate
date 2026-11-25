package org.catrobat.catroid.ui.workspace;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.StateListDrawable;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.OvershootInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.widget.PopupMenu;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import com.google.android.material.badge.BadgeDrawable;
import com.huawei.hms.ml.language.common.utils.LanguageCodeUtil;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt;
import kotlin.text.StringsKt;
import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.antlr.stringtemplate.language.ASTExpr;
import org.catrobat.catroid.common.Constants;
import org.catrobat.catroid.content.bricks.Brick;
import org.catrobat.catroid.content.bricks.FormulaBrick;
import org.catrobat.catroid.ui.fragment.AddBrickFragment;
import org.catrobat.catroid.ui.fragment.BrickCategoryFragment;
import org.catrobat.catroid.ui.fragment.FormulaEditorFragment;
import org.catrobat.catroid.ui.recyclerview.fragment.CategoryListFragment;
import org.catrobat.catroid.ui.recyclerview.fragment.DataListFragment;
import org.catrobat.catroid.ui.recyclerview.fragment.LookListFragment;
import org.catrobat.catroid.ui.recyclerview.fragment.SceneListFragment;
import org.catrobat.catroid.ui.recyclerview.fragment.ScriptFragment;
import org.catrobat.catroid.ui.recyclerview.fragment.SoundListFragment;
import org.catrobat.catroid.ui.recyclerview.fragment.SpriteListFragment;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.metadata.jvm.deserialization.JvmProtoBufUtil;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/ui/workspace/WorkspaceLayout.class
  classes14.dex
 */
/* JADX INFO: compiled from: WorkspaceLayout.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000\u0095\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010%\n\u0002\u0010\u000e\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0002\b\u000e\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u001d\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\n\n\u0002\u0010$\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\n*\u0001\u0013\u0018\u00002\u00020\u0001B%\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u0005\u0012\b\b\u0002\u0010\u0006\u001a\u00020\u0007¢\u0006\u0002\u0010\bJ.\u0010/\u001a\u0002002\u0006\u00101\u001a\u00020\u000b2\u0006\u00102\u001a\u00020\u000b2\u0006\u00103\u001a\u00020\u00072\f\u00104\u001a\b\u0012\u0004\u0012\u00020605H\u0002J\u0006\u00107\u001a\u000200J*\u00108\u001a\u000e\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020\u0007092\u0006\u0010:\u001a\u00020\f2\u0006\u0010;\u001a\u00020\u00072\u0006\u0010<\u001a\u00020\u0007J:\u0010=\u001a\u000e\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020\u0007092\u0006\u0010:\u001a\u00020\f2\u0006\u0010>\u001a\u00020\u00072\u0006\u0010?\u001a\u00020\u00072\u0006\u0010@\u001a\u00020\u00072\u0006\u0010A\u001a\u00020\u0007J\u0010\u0010B\u001a\u00020\u00072\u0006\u0010C\u001a\u00020\u0007H\u0002J\u001e\u0010D\u001a\u0002002\u0006\u0010:\u001a\u00020\f2\u0006\u0010E\u001a\u00020\u00072\u0006\u0010F\u001a\u00020\u0007J\u001e\u0010G\u001a\u0002002\u0006\u0010:\u001a\u00020\f2\u0006\u0010H\u001a\u00020\u00072\u0006\u0010I\u001a\u00020\u0007J\u0010\u0010J\u001a\u00020\u000b2\u0006\u0010K\u001a\u00020\u000bH\u0002J\u0006\u0010L\u001a\u00020\u001cJ\u000e\u0010M\u001a\u00020\u001c2\u0006\u00102\u001a\u00020\u000bJ\u0018\u0010N\u001a\u0002002\u0006\u00102\u001a\u00020\u000b2\u0006\u0010O\u001a\u00020\fH\u0002J\u000e\u0010P\u001a\u0002002\u0006\u0010Q\u001a\u00020\u000bJ\u0006\u0010R\u001a\u000200J\u0016\u0010S\u001a\u0002002\u0006\u0010T\u001a\u00020\u000b2\u0006\u00101\u001a\u00020\u000bJ\u000e\u0010U\u001a\u0002002\u0006\u0010V\u001a\u00020WJ\u001e\u0010X\u001a\u0002002\u0006\u0010Y\u001a\u00020Z2\u0006\u0010[\u001a\u00020\\2\u0006\u0010]\u001a\u00020\u001cJ$\u0010^\u001a\u0002002\u0006\u00102\u001a\u00020\u000b2\u0006\u00101\u001a\u00020\u000b2\f\u00104\u001a\b\u0012\u0004\u0012\u00020605J\u0018\u0010_\u001a\u0002002\u0006\u00102\u001a\u00020\u000b2\b\b\u0002\u0010`\u001a\u00020\u001cJ\u0010\u0010a\u001a\u0002002\u0006\u00102\u001a\u00020\u000bH\u0002J\u001c\u0010b\u001a\u0002002\u0006\u00102\u001a\u00020\u000b2\f\u00104\u001a\b\u0012\u0004\u0012\u00020605J\u000e\u0010c\u001a\u0002002\u0006\u0010d\u001a\u00020\u000bJ,\u0010e\u001a\u0002002$\u0010f\u001a \u0012\u0004\u0012\u00020\u000b\u0012\u0016\u0012\u0014\u0012\u0004\u0012\u00020\u000b\u0012\n\u0012\b\u0012\u0004\u0012\u00020605090gJ\u0006\u0010h\u001a\u000200J\b\u0010i\u001a\u000200H\u0002J\"\u0010j\u001a\u0002002\u0006\u0010O\u001a\u00020\f2\u0006\u00102\u001a\u00020\u000b2\b\u0010k\u001a\u0004\u0018\u00010lH\u0002J\u000e\u0010m\u001a\u0002002\u0006\u0010:\u001a\u00020\fJ\u000e\u0010n\u001a\u0002002\u0006\u0010o\u001a\u00020\u001cJ*\u0010p\u001a\u000e\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020\u0007092\u0006\u0010:\u001a\u00020\f2\u0006\u0010>\u001a\u00020\u00072\u0006\u0010?\u001a\u00020\u0007J.\u0010q\u001a\u0002002\u0006\u0010:\u001a\u00020\f2\u0006\u0010r\u001a\u00020\u00072\u0006\u0010s\u001a\u00020\u00072\u0006\u0010t\u001a\u00020\u00072\u0006\u0010u\u001a\u00020\u0007R\u001a\u0010\t\u001a\u000e\u0012\u0004\u0012\u00020\u000b\u0012\u0004\u0012\u00020\f0\nX\u0082\u0004¢\u0006\u0002\n\u0000R\u001c\u0010\r\u001a\u0004\u0018\u00010\u000bX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000e\u0010\u000f\"\u0004\b\u0010\u0010\u0011R\u0010\u0010\u0012\u001a\u00020\u0013X\u0082\u0004¢\u0006\u0004\n\u0002\u0010\u0014R\u001b\u0010\u0015\u001a\u00020\u00168BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\u0019\u0010\u001a\u001a\u0004\b\u0017\u0010\u0018R\u000e\u0010\u001b\u001a\u00020\u001cX\u0082\u000e¢\u0006\u0002\n\u0000R\u001a\u0010\u001d\u001a\u00020\u001cX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001d\u0010\u001e\"\u0004\b\u001f\u0010 R\u000e\u0010!\u001a\u00020\u001cX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\"\u001a\u00020\u001cX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010#\u001a\u00020\u001cX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010$\u001a\u00020\u0001X\u0082.¢\u0006\u0002\n\u0000R\u0014\u0010%\u001a\u00020\u00078BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b&\u0010'R\u0014\u0010(\u001a\u00020\u00078BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b)\u0010'R\u0016\u0010*\u001a\n ,*\u0004\u0018\u00010+0+X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010-\u001a\u00020.X\u0082.¢\u0006\u0002\n\u0000¨\u0006v"}, d2 = {"Lorg/catrobat/catroid/ui/workspace/WorkspaceLayout;", "Landroid/widget/FrameLayout;", "context", "Landroid/content/Context;", "attrs", "Landroid/util/AttributeSet;", "defStyleAttr", "", "(Landroid/content/Context;Landroid/util/AttributeSet;I)V", "activeWindows", "", "", "Lorg/catrobat/catroid/ui/workspace/WindowContainer;", "focusedWindowTag", "getFocusedWindowTag", "()Ljava/lang/String;", "setFocusedWindowTag", "(Ljava/lang/String;)V", "fragmentLifecycleCallbacks", "org/catrobat/catroid/ui/workspace/WorkspaceLayout$fragmentLifecycleCallbacks$1", "Lorg/catrobat/catroid/ui/workspace/WorkspaceLayout$fragmentLifecycleCallbacks$1;", "ghostPreview", "Landroid/view/View;", "getGhostPreview", "()Landroid/view/View;", "ghostPreview$delegate", "Lkotlin/Lazy;", "isLayoutRestored", "", "isProgrammaticBackPress", "()Z", "setProgrammaticBackPress", "(Z)V", "isReplacing", "isRestoring", "isSidebarOpen", "menuButton", "minHeight", "getMinHeight", "()I", "minWidth", "getMinWidth", "sharedPrefs", "Landroid/content/SharedPreferences;", JvmProtoBufUtil.PLATFORM_TYPE_ID, "sidebar", "Landroid/widget/LinearLayout;", "addStyledSidebarRow", "", "title", "tag", "iconRes", "fragmentCreator", "Lkotlin/Function0;", "Landroidx/fragment/app/Fragment;", "bringNavigationToFront", "calculateResizeSnap", "Lkotlin/Pair;", "draggedWindow", "newWidth", "newHeight", "calculateSnap", "newLeft", "newTop", "width", "height", "dpToPx", "dp", "endGhostDrag", "finalLeft", "finalTop", "endGhostResize", "finalWidth", "finalHeight", "getLocalizedString", ASTExpr.DEFAULT_MAP_KEY_NAME, "handleBackPressed", "isWindowOpen", "loadSingleWindowLayout", ConfigConstants.CONFIG_KEY_WINDOW, "openAddBrickWindow", "category", "openBrickCategoryWindow", "openCategoryListWindow", CategoryListFragment.FRAGMENT_TAG_BUNDLE_ARGUMENT, "openDataListWindow", "parentBrick", "Lorg/catrobat/catroid/content/bricks/Brick;", "openFormulaEditorWindow", "formulaBrick", "Lorg/catrobat/catroid/content/bricks/FormulaBrick;", "formulaField", "Lorg/catrobat/catroid/content/bricks/Brick$FormulaField;", "showCustomView", "openWindow", "removeWindow", "force", "removeWindowViewOnly", "replaceWindowContent", "requestFocus", "targetTag", "restoreLayoutState", "fragmentCreators", "", "saveLayoutState", "setupSidebar", "setupWindowMenu", "activity", "Landroidx/fragment/app/FragmentActivity;", "startGhostDrag", "toggleSidebar", "open", "updateGhostDrag", "updateGhostResize", "left", "top", "snapWidth", "snapHeight", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class WorkspaceLayout extends FrameLayout {
    private final Map<String, WindowContainer> activeWindows;
    private String focusedWindowTag;
    private final WorkspaceLayout$fragmentLifecycleCallbacks$1 fragmentLifecycleCallbacks;

    /* JADX INFO: renamed from: ghostPreview$delegate, reason: from kotlin metadata */
    private final Lazy ghostPreview;
    private boolean isLayoutRestored;
    private boolean isProgrammaticBackPress;
    private boolean isReplacing;
    private boolean isRestoring;
    private boolean isSidebarOpen;
    private FrameLayout menuButton;
    private final SharedPreferences sharedPrefs;
    private LinearLayout sidebar;

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public WorkspaceLayout(Context context) {
        this(context, null, 0, 6, null);
        Intrinsics.checkNotNullParameter(context, "context");
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public WorkspaceLayout(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0, 4, null);
        Intrinsics.checkNotNullParameter(context, "context");
    }

    public /* synthetic */ WorkspaceLayout(Context context, AttributeSet attributeSet, int i, int i2, DefaultConstructorMarker defaultConstructorMarker) {
        this(context, (i2 & 2) != 0 ? null : attributeSet, (i2 & 4) != 0 ? 0 : i);
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    /* JADX WARN: Type inference failed for: r3v8, types: [org.catrobat.catroid.ui.workspace.WorkspaceLayout$fragmentLifecycleCallbacks$1] */
    public WorkspaceLayout(final Context context, AttributeSet attributeSet, int i) {
        FragmentManager supportFragmentManager;
        super(context, attributeSet, i);
        Intrinsics.checkNotNullParameter(context, "context");
        this.sharedPrefs = context.getSharedPreferences("workspace_prefs", 0);
        this.activeWindows = new LinkedHashMap();
        this.ghostPreview = LazyKt.lazy(new Function0<View>() { // from class: org.catrobat.catroid.ui.workspace.WorkspaceLayout$ghostPreview$2
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // kotlin.jvm.functions.Function0
            public final View invoke() {
                View view = new View(context);
                WorkspaceLayout workspaceLayout = this;
                Context context2 = context;
                GradientDrawable gradientDrawable = new GradientDrawable();
                gradientDrawable.setColor(Color.parseColor("#22A8DFF4"));
                gradientDrawable.setStroke(workspaceLayout.dpToPx(2), ContextCompat.getColor(context2, R.color.accent));
                gradientDrawable.setCornerRadius(workspaceLayout.dpToPx(16));
                view.setBackground(gradientDrawable);
                view.setVisibility(8);
                view.setClickable(false);
                view.setFocusable(false);
                return view;
            }
        });
        ?? r3 = new FragmentManager.FragmentLifecycleCallbacks() { // from class: org.catrobat.catroid.ui.workspace.WorkspaceLayout$fragmentLifecycleCallbacks$1
            @Override // androidx.fragment.app.FragmentManager.FragmentLifecycleCallbacks
            public void onFragmentDetached(FragmentManager fm, Fragment f) {
                String tag;
                Intrinsics.checkNotNullParameter(fm, "fm");
                Intrinsics.checkNotNullParameter(f, "f");
                super.onFragmentDetached(fm, f);
                if (this.this$0.isReplacing || (tag = f.getTag()) == null || !this.this$0.activeWindows.containsKey(tag)) {
                    return;
                }
                this.this$0.removeWindowViewOnly(tag);
            }
        };
        this.fragmentLifecycleCallbacks = r3;
        FragmentActivity fragmentActivity = context instanceof FragmentActivity ? (FragmentActivity) context : null;
        if (fragmentActivity != null && (supportFragmentManager = fragmentActivity.getSupportFragmentManager()) != null) {
            supportFragmentManager.registerFragmentLifecycleCallbacks((FragmentManager.FragmentLifecycleCallbacks) r3, false);
        }
        setupSidebar();
        addView(getGhostPreview());
    }

    public final String getFocusedWindowTag() {
        return this.focusedWindowTag;
    }

    public final void setFocusedWindowTag(String str) {
        this.focusedWindowTag = str;
    }

    private final int getMinWidth() {
        return dpToPx(240);
    }

    private final int getMinHeight() {
        return dpToPx(180);
    }

    private final View getGhostPreview() {
        return (View) this.ghostPreview.getValue();
    }

    /* JADX INFO: renamed from: isProgrammaticBackPress, reason: from getter */
    public final boolean getIsProgrammaticBackPress() {
        return this.isProgrammaticBackPress;
    }

    public final void setProgrammaticBackPress(boolean z) {
        this.isProgrammaticBackPress = z;
    }

    private final String getLocalizedString(String key) {
        boolean zAreEqual = Intrinsics.areEqual(getContext().getResources().getConfiguration().locale.getLanguage(), LanguageCodeUtil.RU);
        switch (key) {
            case "data_selection":
                return zAreEqual ? "Выбор данных" : "Select Data";
            case "workspace_title":
                return zAreEqual ? "ИНСТРУМЕНТЫ" : "WORKSPACE TOOLS";
            case "sounds":
                return zAreEqual ? "Звуки" : "Sounds";
            case "looks":
                return zAreEqual ? "Образы" : "Looks";
            case "add_brick":
                return zAreEqual ? "Добавить блок" : "Add Block";
            case "formula_editor":
                return zAreEqual ? "Редактор формул" : "Formula Editor";
            case "categories":
                return zAreEqual ? "Категории блоков" : "Block Categories";
            case "scripts":
                return zAreEqual ? "Скрипты" : "Scripts";
            default:
                return key;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void removeWindowViewOnly(String tag) {
        final WindowContainer windowContainerRemove = this.activeWindows.remove(tag);
        if (windowContainerRemove == null) {
            return;
        }
        windowContainerRemove.animate().alpha(0.0f).scaleX(0.85f).scaleY(0.85f).setDuration(150L).withEndAction(new Runnable() { // from class: org.catrobat.catroid.ui.workspace.WorkspaceLayout$$ExternalSyntheticLambda5
            @Override // java.lang.Runnable
            public final void run() {
                WorkspaceLayout.removeWindowViewOnly$lambda$0(this.f$0, windowContainerRemove);
            }
        }).start();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void removeWindowViewOnly$lambda$0(WorkspaceLayout this$0, WindowContainer window) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNullParameter(window, "$window");
        this$0.removeView(window);
    }

    public final void bringNavigationToFront() {
        FrameLayout frameLayout = this.menuButton;
        LinearLayout linearLayout = null;
        if (frameLayout == null) {
            Intrinsics.throwUninitializedPropertyAccessException("menuButton");
            frameLayout = null;
        }
        frameLayout.bringToFront();
        LinearLayout linearLayout2 = this.sidebar;
        if (linearLayout2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("sidebar");
        } else {
            linearLayout = linearLayout2;
        }
        linearLayout.bringToFront();
    }

    private final void setupSidebar() {
        FrameLayout frameLayout = new FrameLayout(getContext());
        int iDpToPx = dpToPx(48);
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(iDpToPx, iDpToPx);
        layoutParams.gravity = BadgeDrawable.BOTTOM_START;
        layoutParams.leftMargin = dpToPx(16);
        layoutParams.bottomMargin = dpToPx(16);
        frameLayout.setLayoutParams(layoutParams);
        GradientDrawable gradientDrawable = new GradientDrawable();
        gradientDrawable.setShape(1);
        gradientDrawable.setColor(Color.parseColor("#EE002B4D"));
        gradientDrawable.setStroke(dpToPx(1), Color.parseColor("#50FFFFFF"));
        frameLayout.setBackground(gradientDrawable);
        frameLayout.setElevation(dpToPx(8));
        TextView textView = new TextView(frameLayout.getContext());
        textView.setText("☰");
        textView.setTextColor(-1);
        textView.setTextSize(20.0f);
        textView.setTypeface(Typeface.DEFAULT);
        textView.setGravity(17);
        frameLayout.addView(textView);
        frameLayout.setOnClickListener(new View.OnClickListener() { // from class: org.catrobat.catroid.ui.workspace.WorkspaceLayout$$ExternalSyntheticLambda7
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                WorkspaceLayout.setupSidebar$lambda$5$lambda$4(this.f$0, view);
            }
        });
        this.menuButton = frameLayout;
        addView(frameLayout);
        LinearLayout linearLayout = new LinearLayout(getContext());
        linearLayout.setOrientation(1);
        linearLayout.setElevation(dpToPx(8));
        linearLayout.setVisibility(8);
        linearLayout.setPadding(0, 0, 0, dpToPx(16));
        FrameLayout.LayoutParams layoutParams2 = new FrameLayout.LayoutParams(dpToPx(240), dpToPx(320));
        layoutParams2.leftMargin = dpToPx(20);
        layoutParams2.topMargin = dpToPx(20);
        layoutParams2.gravity = BadgeDrawable.TOP_START;
        linearLayout.setLayoutParams(layoutParams2);
        GradientDrawable gradientDrawable2 = new GradientDrawable();
        gradientDrawable2.setColor(Color.parseColor("#D0003554"));
        gradientDrawable2.setStroke(dpToPx(1), Color.parseColor("#33FFFFFF"));
        gradientDrawable2.setCornerRadius(dpToPx(16));
        linearLayout.setBackground(gradientDrawable2);
        this.sidebar = linearLayout;
        LinearLayout linearLayout2 = new LinearLayout(getContext());
        linearLayout2.setOrientation(0);
        linearLayout2.setBackgroundColor(Color.parseColor("#B0002B4D"));
        linearLayout2.setPadding(dpToPx(14), dpToPx(10), dpToPx(14), dpToPx(10));
        linearLayout2.setGravity(16);
        TextView textView2 = new TextView(getContext());
        textView2.setText(getLocalizedString("workspace_title"));
        textView2.setTextColor(ContextCompat.getColor(textView2.getContext(), R.color.solid_white));
        textView2.setTextSize(14.0f);
        textView2.setTypeface(Typeface.DEFAULT_BOLD);
        textView2.setLayoutParams(new LinearLayout.LayoutParams(0, -2, 1.0f));
        ImageView imageView = new ImageView(getContext());
        imageView.setLayoutParams(new LinearLayout.LayoutParams(dpToPx(20), dpToPx(20)));
        imageView.setImageResource(R.drawable.ic_close);
        imageView.setColorFilter(ContextCompat.getColor(imageView.getContext(), R.color.accent));
        imageView.setOnClickListener(new View.OnClickListener() { // from class: org.catrobat.catroid.ui.workspace.WorkspaceLayout$$ExternalSyntheticLambda8
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                WorkspaceLayout.setupSidebar$lambda$12$lambda$11(this.f$0, view);
            }
        });
        linearLayout2.addView(textView2);
        linearLayout2.addView(imageView);
        LinearLayout linearLayout3 = this.sidebar;
        LinearLayout linearLayout4 = null;
        if (linearLayout3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("sidebar");
            linearLayout3 = null;
        }
        linearLayout3.addView(linearLayout2);
        View view = new View(getContext());
        view.setLayoutParams(new LinearLayout.LayoutParams(-1, dpToPx(12)));
        LinearLayout linearLayout5 = this.sidebar;
        if (linearLayout5 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("sidebar");
            linearLayout5 = null;
        }
        linearLayout5.addView(view);
        if (Intrinsics.areEqual(getContext().getClass().getSimpleName(), "ProjectActivity")) {
            addStyledSidebarRow("Сцены", SceneListFragment.INSTANCE.getTAG(), R.drawable.landscape_2_24px, new Function0<Fragment>() { // from class: org.catrobat.catroid.ui.workspace.WorkspaceLayout.setupSidebar.3
                /* JADX WARN: Can't rename method to resolve collision */
                @Override // kotlin.jvm.functions.Function0
                public final Fragment invoke() {
                    return new SceneListFragment();
                }
            });
            addStyledSidebarRow("Спрайты", SpriteListFragment.INSTANCE.getTAG(), R.drawable.ic_stat, new Function0<Fragment>() { // from class: org.catrobat.catroid.ui.workspace.WorkspaceLayout.setupSidebar.4
                /* JADX WARN: Can't rename method to resolve collision */
                @Override // kotlin.jvm.functions.Function0
                public final Fragment invoke() {
                    return new SpriteListFragment();
                }
            });
            View view2 = new View(getContext());
            LinearLayout.LayoutParams layoutParams3 = new LinearLayout.LayoutParams(-1, dpToPx(1));
            layoutParams3.topMargin = dpToPx(8);
            layoutParams3.bottomMargin = dpToPx(8);
            layoutParams3.leftMargin = dpToPx(12);
            layoutParams3.rightMargin = dpToPx(12);
            view2.setLayoutParams(layoutParams3);
            view2.setBackgroundColor(Color.parseColor("#15FFFFFF"));
            LinearLayout linearLayout6 = this.sidebar;
            if (linearLayout6 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("sidebar");
                linearLayout6 = null;
            }
            linearLayout6.addView(view2);
        }
        String localizedString = getLocalizedString("scripts");
        String TAG = ScriptFragment.TAG;
        Intrinsics.checkNotNullExpressionValue(TAG, "TAG");
        addStyledSidebarRow(localizedString, TAG, R.drawable.ic_play, new Function0<Fragment>() { // from class: org.catrobat.catroid.ui.workspace.WorkspaceLayout.setupSidebar.5
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // kotlin.jvm.functions.Function0
            public final Fragment invoke() {
                return new ScriptFragment();
            }
        });
        String localizedString2 = getLocalizedString("looks");
        String TAG2 = LookListFragment.TAG;
        Intrinsics.checkNotNullExpressionValue(TAG2, "TAG");
        addStyledSidebarRow(localizedString2, TAG2, R.drawable.ic_look_pos, new Function0<Fragment>() { // from class: org.catrobat.catroid.ui.workspace.WorkspaceLayout.setupSidebar.6
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // kotlin.jvm.functions.Function0
            public final Fragment invoke() {
                return new LookListFragment();
            }
        });
        String localizedString3 = getLocalizedString(Constants.SOUND_DIRECTORY_NAME);
        String TAG3 = SoundListFragment.TAG;
        Intrinsics.checkNotNullExpressionValue(TAG3, "TAG");
        addStyledSidebarRow(localizedString3, TAG3, R.drawable.ic_sound_pos, new Function0<Fragment>() { // from class: org.catrobat.catroid.ui.workspace.WorkspaceLayout.setupSidebar.7
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // kotlin.jvm.functions.Function0
            public final Fragment invoke() {
                return new SoundListFragment();
            }
        });
        LinearLayout linearLayout7 = this.sidebar;
        if (linearLayout7 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("sidebar");
        } else {
            linearLayout4 = linearLayout7;
        }
        addView(linearLayout4);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void setupSidebar$lambda$5$lambda$4(WorkspaceLayout this$0, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.toggleSidebar(true);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void setupSidebar$lambda$12$lambda$11(WorkspaceLayout this$0, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.toggleSidebar(false);
    }

    private final void addStyledSidebarRow(final String title, final String tag, int iconRes, final Function0<? extends Fragment> fragmentCreator) {
        LinearLayout linearLayout = new LinearLayout(getContext());
        linearLayout.setOrientation(0);
        linearLayout.setGravity(16);
        linearLayout.setPadding(dpToPx(16), dpToPx(10), dpToPx(16), dpToPx(10));
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(-1, -2);
        layoutParams.bottomMargin = dpToPx(4);
        layoutParams.leftMargin = dpToPx(12);
        layoutParams.rightMargin = dpToPx(12);
        linearLayout.setLayoutParams(layoutParams);
        StateListDrawable stateListDrawable = new StateListDrawable();
        int[] iArr = {android.R.attr.state_pressed};
        GradientDrawable gradientDrawable = new GradientDrawable();
        gradientDrawable.setColor(Color.parseColor("#25A8DFF4"));
        gradientDrawable.setCornerRadius(dpToPx(8));
        Unit unit = Unit.INSTANCE;
        stateListDrawable.addState(iArr, gradientDrawable);
        GradientDrawable gradientDrawable2 = new GradientDrawable();
        gradientDrawable2.setColor(0);
        Unit unit2 = Unit.INSTANCE;
        stateListDrawable.addState(new int[0], gradientDrawable2);
        linearLayout.setBackground(stateListDrawable);
        ImageView imageView = new ImageView(linearLayout.getContext());
        LinearLayout.LayoutParams layoutParams2 = new LinearLayout.LayoutParams(dpToPx(18), dpToPx(20));
        layoutParams2.rightMargin = dpToPx(14);
        imageView.setLayoutParams(layoutParams2);
        imageView.setImageResource(iconRes);
        imageView.setColorFilter(ContextCompat.getColor(imageView.getContext(), R.color.accent));
        TextView textView = new TextView(linearLayout.getContext());
        textView.setText(title);
        textView.setTextColor(-1);
        textView.setTextSize(14.0f);
        textView.setTypeface(Typeface.DEFAULT);
        linearLayout.addView(imageView);
        linearLayout.addView(textView);
        linearLayout.setOnClickListener(new View.OnClickListener() { // from class: org.catrobat.catroid.ui.workspace.WorkspaceLayout$$ExternalSyntheticLambda9
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                WorkspaceLayout.addStyledSidebarRow$lambda$24$lambda$23(this.f$0, tag, title, fragmentCreator, view);
            }
        });
        LinearLayout linearLayout2 = this.sidebar;
        if (linearLayout2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("sidebar");
            linearLayout2 = null;
        }
        linearLayout2.addView(linearLayout);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void addStyledSidebarRow$lambda$24$lambda$23(WorkspaceLayout this$0, String tag, String title, Function0 fragmentCreator, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNullParameter(tag, "$tag");
        Intrinsics.checkNotNullParameter(title, "$title");
        Intrinsics.checkNotNullParameter(fragmentCreator, "$fragmentCreator");
        this$0.openWindow(tag, title, fragmentCreator);
        this$0.toggleSidebar(false);
    }

    public final void toggleSidebar(boolean open) {
        this.isSidebarOpen = open;
        LinearLayout linearLayout = null;
        if (open) {
            LinearLayout linearLayout2 = this.sidebar;
            if (linearLayout2 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("sidebar");
                linearLayout2 = null;
            }
            linearLayout2.setVisibility(0);
            FrameLayout frameLayout = this.menuButton;
            if (frameLayout == null) {
                Intrinsics.throwUninitializedPropertyAccessException("menuButton");
                frameLayout = null;
            }
            frameLayout.animate().alpha(0.0f).scaleX(0.0f).scaleY(0.0f).setDuration(180L).start();
            bringNavigationToFront();
            LinearLayout linearLayout3 = this.sidebar;
            if (linearLayout3 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("sidebar");
                linearLayout3 = null;
            }
            linearLayout3.setAlpha(0.0f);
            LinearLayout linearLayout4 = this.sidebar;
            if (linearLayout4 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("sidebar");
                linearLayout4 = null;
            }
            linearLayout4.setScaleX(0.85f);
            LinearLayout linearLayout5 = this.sidebar;
            if (linearLayout5 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("sidebar");
                linearLayout5 = null;
            }
            linearLayout5.setScaleY(0.85f);
            LinearLayout linearLayout6 = this.sidebar;
            if (linearLayout6 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("sidebar");
            } else {
                linearLayout = linearLayout6;
            }
            linearLayout.animate().alpha(1.0f).scaleX(1.0f).scaleY(1.0f).setInterpolator(new OvershootInterpolator(1.05f)).setDuration(220L).start();
            return;
        }
        FrameLayout frameLayout2 = this.menuButton;
        if (frameLayout2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("menuButton");
            frameLayout2 = null;
        }
        frameLayout2.animate().alpha(1.0f).scaleX(1.0f).scaleY(1.0f).setDuration(220L).start();
        LinearLayout linearLayout7 = this.sidebar;
        if (linearLayout7 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("sidebar");
        } else {
            linearLayout = linearLayout7;
        }
        linearLayout.animate().alpha(0.0f).scaleX(0.85f).scaleY(0.85f).setInterpolator(new DecelerateInterpolator()).setDuration(180L).withEndAction(new Runnable() { // from class: org.catrobat.catroid.ui.workspace.WorkspaceLayout$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                WorkspaceLayout.toggleSidebar$lambda$25(this.f$0);
            }
        }).start();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void toggleSidebar$lambda$25(WorkspaceLayout this$0) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        LinearLayout linearLayout = this$0.sidebar;
        if (linearLayout == null) {
            Intrinsics.throwUninitializedPropertyAccessException("sidebar");
            linearLayout = null;
        }
        linearLayout.setVisibility(8);
    }

    public final void requestFocus(String targetTag) {
        Intrinsics.checkNotNullParameter(targetTag, "targetTag");
        this.focusedWindowTag = targetTag;
        for (Map.Entry<String, WindowContainer> entry : this.activeWindows.entrySet()) {
            entry.getValue().setFocusedState(Intrinsics.areEqual(entry.getKey(), targetTag));
        }
    }

    public final void openWindow(final String tag, String title, Function0<? extends Fragment> fragmentCreator) {
        FragmentManager supportFragmentManager;
        FragmentTransaction fragmentTransactionBeginTransaction;
        Intrinsics.checkNotNullParameter(tag, "tag");
        Intrinsics.checkNotNullParameter(title, "title");
        Intrinsics.checkNotNullParameter(fragmentCreator, "fragmentCreator");
        Context context = getContext();
        FragmentActivity fragmentActivity = context instanceof FragmentActivity ? (FragmentActivity) context : null;
        if (this.activeWindows.containsKey(tag)) {
            requestFocus(tag);
            WindowContainer windowContainer = this.activeWindows.get(tag);
            if (windowContainer != null) {
                windowContainer.bringToFront();
            }
            bringNavigationToFront();
            return;
        }
        Context context2 = getContext();
        Intrinsics.checkNotNullExpressionValue(context2, "getContext(...)");
        WindowContainer windowContainer2 = new WindowContainer(context2, null, 0, 6, null);
        windowContainer2.initWindow(tag, title);
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(dpToPx(400), dpToPx(500));
        layoutParams.leftMargin = dpToPx(50);
        layoutParams.topMargin = dpToPx(50);
        layoutParams.gravity = BadgeDrawable.TOP_START;
        windowContainer2.setLayoutParams(layoutParams);
        setupWindowMenu(windowContainer2, tag, fragmentActivity);
        windowContainer2.setOnCloseClickListener(new Function0<Unit>() { // from class: org.catrobat.catroid.ui.workspace.WorkspaceLayout.openWindow.2
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public /* bridge */ /* synthetic */ Unit invoke() {
                invoke2();
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2() {
                FragmentManager supportFragmentManager2;
                FragmentManager supportFragmentManager3;
                if (Intrinsics.areEqual(tag, FormulaEditorFragment.FORMULA_EDITOR_FRAGMENT_TAG)) {
                    Context context3 = this.getContext();
                    FragmentActivity fragmentActivity2 = context3 instanceof FragmentActivity ? (FragmentActivity) context3 : null;
                    Fragment fragmentFindFragmentByTag = (fragmentActivity2 == null || (supportFragmentManager3 = fragmentActivity2.getSupportFragmentManager()) == null) ? null : supportFragmentManager3.findFragmentByTag(tag);
                    FormulaEditorFragment formulaEditorFragment = fragmentFindFragmentByTag instanceof FormulaEditorFragment ? (FormulaEditorFragment) fragmentFindFragmentByTag : null;
                    if (formulaEditorFragment != null) {
                        formulaEditorFragment.saveFormulaIfPossible();
                    }
                    this.removeWindow(tag, true);
                    Fragment fragmentFindFragmentByTag2 = (fragmentActivity2 == null || (supportFragmentManager2 = fragmentActivity2.getSupportFragmentManager()) == null) ? null : supportFragmentManager2.findFragmentByTag(ScriptFragment.TAG);
                    ScriptFragment scriptFragment = fragmentFindFragmentByTag2 instanceof ScriptFragment ? (ScriptFragment) fragmentFindFragmentByTag2 : null;
                    if (scriptFragment != null) {
                        scriptFragment.notifyDataSetChanged();
                        return;
                    }
                    return;
                }
                this.removeWindow(tag, true);
            }
        });
        windowContainer2.setOnLayoutChangedListener(new Function0<Unit>() { // from class: org.catrobat.catroid.ui.workspace.WorkspaceLayout.openWindow.3
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public /* bridge */ /* synthetic */ Unit invoke() {
                invoke2();
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2() {
                WorkspaceLayout.this.saveLayoutState();
            }
        });
        addView(windowContainer2);
        this.activeWindows.put(tag, windowContainer2);
        if (fragmentActivity != null && (supportFragmentManager = fragmentActivity.getSupportFragmentManager()) != null && (fragmentTransactionBeginTransaction = supportFragmentManager.beginTransaction()) != null) {
            fragmentTransactionBeginTransaction.replace(windowContainer2.getContentFrame().getId(), fragmentCreator.invoke(), tag);
            fragmentTransactionBeginTransaction.commitAllowingStateLoss();
        }
        bringNavigationToFront();
        requestFocus(tag);
        loadSingleWindowLayout(tag, windowContainer2);
        if (this.isRestoring) {
            return;
        }
        saveLayoutState();
    }

    public static /* synthetic */ void removeWindow$default(WorkspaceLayout workspaceLayout, String str, boolean z, int i, Object obj) {
        if ((i & 2) != 0) {
            z = false;
        }
        workspaceLayout.removeWindow(str, z);
    }

    public final void removeWindow(String tag, boolean force) {
        FragmentManager supportFragmentManager;
        View view;
        FragmentManager supportFragmentManager2;
        Intrinsics.checkNotNullParameter(tag, "tag");
        final WindowContainer windowContainer = this.activeWindows.get(tag);
        if (windowContainer == null) {
            return;
        }
        if (!windowContainer.getIsPinned() || force) {
            this.activeWindows.remove(tag);
            Context context = getContext();
            Fragment fragmentFindFragmentByTag = null;
            FragmentActivity fragmentActivity = context instanceof FragmentActivity ? (FragmentActivity) context : null;
            if (CollectionsKt.listOf((Object[]) new String[]{DataListFragment.TAG, CategoryListFragment.FUNCTION_TAG, CategoryListFragment.LOGIC_TAG, CategoryListFragment.SENSOR_TAG, CategoryListFragment.OBJECT_TAG}).contains(tag)) {
                Fragment fragmentFindFragmentByTag2 = (fragmentActivity == null || (supportFragmentManager2 = fragmentActivity.getSupportFragmentManager()) == null) ? null : supportFragmentManager2.findFragmentByTag(FormulaEditorFragment.FORMULA_EDITOR_FRAGMENT_TAG);
                final FormulaEditorFragment formulaEditorFragment = fragmentFindFragmentByTag2 instanceof FormulaEditorFragment ? (FormulaEditorFragment) fragmentFindFragmentByTag2 : null;
                if (formulaEditorFragment != null && (view = formulaEditorFragment.getView()) != null) {
                    view.post(new Runnable() { // from class: org.catrobat.catroid.ui.workspace.WorkspaceLayout$$ExternalSyntheticLambda1
                        @Override // java.lang.Runnable
                        public final void run() {
                            formulaEditorFragment.onHiddenChanged(false);
                        }
                    });
                }
            }
            if (fragmentActivity != null && (supportFragmentManager = fragmentActivity.getSupportFragmentManager()) != null) {
                fragmentFindFragmentByTag = supportFragmentManager.findFragmentByTag(tag);
            }
            if (fragmentFindFragmentByTag != null) {
                fragmentActivity.getSupportFragmentManager().beginTransaction().remove(fragmentFindFragmentByTag).commitAllowingStateLoss();
            }
            windowContainer.animate().alpha(0.0f).scaleX(0.85f).scaleY(0.85f).setDuration(150L).withEndAction(new Runnable() { // from class: org.catrobat.catroid.ui.workspace.WorkspaceLayout$$ExternalSyntheticLambda2
                @Override // java.lang.Runnable
                public final void run() {
                    WorkspaceLayout.removeWindow$lambda$29(this.f$0, windowContainer);
                }
            }).start();
            saveLayoutState();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void removeWindow$lambda$29(WorkspaceLayout this$0, WindowContainer window) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNullParameter(window, "$window");
        this$0.removeView(window);
    }

    public final void saveLayoutState() {
        float width = getWidth();
        float height = getHeight();
        if (width <= 0.0f || height <= 0.0f) {
            return;
        }
        SharedPreferences sharedPrefs = this.sharedPrefs;
        Intrinsics.checkNotNullExpressionValue(sharedPrefs, "sharedPrefs");
        SharedPreferences.Editor editorEdit = sharedPrefs.edit();
        editorEdit.putString("active_tags", CollectionsKt.joinToString$default(this.activeWindows.keySet(), ",", null, null, 0, null, null, 62, null));
        for (Map.Entry<String, WindowContainer> entry : this.activeWindows.entrySet()) {
            String key = entry.getKey();
            WindowContainer value = entry.getValue();
            ViewGroup.LayoutParams layoutParams = value.getLayoutParams();
            Intrinsics.checkNotNull(layoutParams, "null cannot be cast to non-null type android.widget.FrameLayout.LayoutParams");
            FrameLayout.LayoutParams layoutParams2 = (FrameLayout.LayoutParams) layoutParams;
            editorEdit.putString(key + "_layout", (layoutParams2.leftMargin / width) + ";" + (layoutParams2.topMargin / height) + ";" + layoutParams2.width + ";" + layoutParams2.height + ";" + value.getIsPinned());
        }
        editorEdit.apply();
    }

    private final void loadSingleWindowLayout(String tag, final WindowContainer window) {
        String string = this.sharedPrefs.getString(tag + "_layout", null);
        if (string == null) {
            return;
        }
        final List listSplit$default = StringsKt.split$default((CharSequence) string, new String[]{";"}, false, 0, 6, (Object) null);
        if (listSplit$default.size() < 4) {
            return;
        }
        int width = getWidth();
        int height = getHeight();
        if (width > 0 && height > 0) {
            ViewGroup.LayoutParams layoutParams = window.getLayoutParams();
            Intrinsics.checkNotNull(layoutParams, "null cannot be cast to non-null type android.widget.FrameLayout.LayoutParams");
            FrameLayout.LayoutParams layoutParams2 = (FrameLayout.LayoutParams) layoutParams;
            float f = Float.parseFloat((String) listSplit$default.get(2));
            if (f < 1.0f) {
                f *= width;
            }
            int iCoerceIn = RangesKt.coerceIn((int) f, getMinWidth(), width);
            float f2 = Float.parseFloat((String) listSplit$default.get(3));
            if (f2 < 1.0f) {
                f2 *= height;
            }
            int iCoerceIn2 = RangesKt.coerceIn((int) f2, getMinHeight(), height);
            int i = (int) (Float.parseFloat((String) listSplit$default.get(0)) * width);
            int i2 = (int) (Float.parseFloat((String) listSplit$default.get(1)) * height);
            int iCoerceIn3 = RangesKt.coerceIn(i, 0, RangesKt.coerceAtLeast(width - iCoerceIn, 0));
            int iCoerceIn4 = RangesKt.coerceIn(i2, 0, RangesKt.coerceAtLeast(height - iCoerceIn2, 0));
            layoutParams2.leftMargin = iCoerceIn3;
            layoutParams2.topMargin = iCoerceIn4;
            layoutParams2.width = iCoerceIn;
            layoutParams2.height = iCoerceIn2;
            window.setLayoutParams(layoutParams2);
            if (listSplit$default.size() == 5) {
                window.setPinned(Boolean.parseBoolean((String) listSplit$default.get(4)));
                window.updatePinIconState();
                return;
            }
            return;
        }
        post(new Runnable() { // from class: org.catrobat.catroid.ui.workspace.WorkspaceLayout$$ExternalSyntheticLambda6
            @Override // java.lang.Runnable
            public final void run() {
                WorkspaceLayout.loadSingleWindowLayout$lambda$31(this.f$0, window, listSplit$default);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void loadSingleWindowLayout$lambda$31(WorkspaceLayout this$0, WindowContainer window, List parts) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNullParameter(window, "$window");
        Intrinsics.checkNotNullParameter(parts, "$parts");
        int width = this$0.getWidth();
        int height = this$0.getHeight();
        if (width <= 0 || height <= 0) {
            return;
        }
        ViewGroup.LayoutParams layoutParams = window.getLayoutParams();
        Intrinsics.checkNotNull(layoutParams, "null cannot be cast to non-null type android.widget.FrameLayout.LayoutParams");
        FrameLayout.LayoutParams layoutParams2 = (FrameLayout.LayoutParams) layoutParams;
        float f = Float.parseFloat((String) parts.get(2));
        if (f < 1.0f) {
            f *= width;
        }
        int iCoerceIn = RangesKt.coerceIn((int) f, this$0.getMinWidth(), width);
        float f2 = Float.parseFloat((String) parts.get(3));
        if (f2 < 1.0f) {
            f2 *= height;
        }
        int iCoerceIn2 = RangesKt.coerceIn((int) f2, this$0.getMinHeight(), height);
        int i = (int) (Float.parseFloat((String) parts.get(0)) * width);
        int i2 = (int) (Float.parseFloat((String) parts.get(1)) * height);
        int iCoerceIn3 = RangesKt.coerceIn(i, 0, RangesKt.coerceAtLeast(width - iCoerceIn, 0));
        int iCoerceIn4 = RangesKt.coerceIn(i2, 0, RangesKt.coerceAtLeast(height - iCoerceIn2, 0));
        layoutParams2.leftMargin = iCoerceIn3;
        layoutParams2.topMargin = iCoerceIn4;
        layoutParams2.width = iCoerceIn;
        layoutParams2.height = iCoerceIn2;
        window.setLayoutParams(layoutParams2);
        if (parts.size() == 5) {
            window.setPinned(Boolean.parseBoolean((String) parts.get(4)));
            window.updatePinIconState();
        }
    }

    public final void restoreLayoutState(final Map<String, ? extends Pair<String, ? extends Function0<? extends Fragment>>> fragmentCreators) {
        Intrinsics.checkNotNullParameter(fragmentCreators, "fragmentCreators");
        if (this.isLayoutRestored) {
            return;
        }
        getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() { // from class: org.catrobat.catroid.ui.workspace.WorkspaceLayout$restoreLayoutState$listener$1
            @Override // android.view.ViewTreeObserver.OnGlobalLayoutListener
            public void onGlobalLayout() {
                if (this.this$0.getWidth() <= 0 || this.this$0.getHeight() <= 0) {
                    return;
                }
                this.this$0.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                if (this.this$0.isLayoutRestored) {
                    return;
                }
                this.this$0.isLayoutRestored = true;
                String string = this.this$0.sharedPrefs.getString("active_tags", "");
                String str = string != null ? string : "";
                if (str.length() > 0) {
                    this.this$0.isRestoring = true;
                    for (String str2 : StringsKt.split$default((CharSequence) str, new String[]{","}, false, 0, 6, (Object) null)) {
                        Pair<String, Function0<Fragment>> pair = fragmentCreators.get(str2);
                        if (pair != null) {
                            this.this$0.openWindow(str2, pair.getFirst(), pair.getSecond());
                        }
                    }
                    this.this$0.isRestoring = false;
                }
            }
        });
    }

    public final void startGhostDrag(WindowContainer draggedWindow) {
        Intrinsics.checkNotNullParameter(draggedWindow, "draggedWindow");
        getGhostPreview().setLayoutParams(new FrameLayout.LayoutParams(draggedWindow.getWidth(), draggedWindow.getHeight()));
        getGhostPreview().setVisibility(0);
        getGhostPreview().bringToFront();
        draggedWindow.bringToFront();
        bringNavigationToFront();
    }

    public final Pair<Integer, Integer> updateGhostDrag(WindowContainer draggedWindow, int newLeft, int newTop) {
        Intrinsics.checkNotNullParameter(draggedWindow, "draggedWindow");
        Pair<Integer, Integer> pairCalculateSnap = calculateSnap(draggedWindow, newLeft, newTop, draggedWindow.getWidth(), draggedWindow.getHeight());
        int iIntValue = pairCalculateSnap.component1().intValue();
        int iIntValue2 = pairCalculateSnap.component2().intValue();
        ViewGroup.LayoutParams layoutParams = getGhostPreview().getLayoutParams();
        Intrinsics.checkNotNull(layoutParams, "null cannot be cast to non-null type android.widget.FrameLayout.LayoutParams");
        FrameLayout.LayoutParams layoutParams2 = (FrameLayout.LayoutParams) layoutParams;
        layoutParams2.leftMargin = iIntValue;
        layoutParams2.topMargin = iIntValue2;
        getGhostPreview().setLayoutParams(layoutParams2);
        return new Pair<>(Integer.valueOf(iIntValue), Integer.valueOf(iIntValue2));
    }

    public final void endGhostDrag(WindowContainer draggedWindow, int finalLeft, int finalTop) {
        Intrinsics.checkNotNullParameter(draggedWindow, "draggedWindow");
        getGhostPreview().setVisibility(8);
        draggedWindow.animateToPosition(finalLeft, finalTop, new Function0<Unit>() { // from class: org.catrobat.catroid.ui.workspace.WorkspaceLayout.endGhostDrag.1
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public /* bridge */ /* synthetic */ Unit invoke() {
                invoke2();
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2() {
                WorkspaceLayout.this.saveLayoutState();
            }
        });
    }

    public final void updateGhostResize(WindowContainer draggedWindow, int left, int top, int snapWidth, int snapHeight) {
        Intrinsics.checkNotNullParameter(draggedWindow, "draggedWindow");
        ViewGroup.LayoutParams layoutParams = getGhostPreview().getLayoutParams();
        Intrinsics.checkNotNull(layoutParams, "null cannot be cast to non-null type android.widget.FrameLayout.LayoutParams");
        FrameLayout.LayoutParams layoutParams2 = (FrameLayout.LayoutParams) layoutParams;
        layoutParams2.leftMargin = left;
        layoutParams2.topMargin = top;
        layoutParams2.width = snapWidth;
        layoutParams2.height = snapHeight;
        getGhostPreview().setLayoutParams(layoutParams2);
    }

    public final void endGhostResize(WindowContainer draggedWindow, int finalWidth, int finalHeight) {
        Intrinsics.checkNotNullParameter(draggedWindow, "draggedWindow");
        getGhostPreview().setVisibility(8);
        draggedWindow.animateToSize(finalWidth, finalHeight, new Function0<Unit>() { // from class: org.catrobat.catroid.ui.workspace.WorkspaceLayout.endGhostResize.1
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public /* bridge */ /* synthetic */ Unit invoke() {
                invoke2();
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2() {
                WorkspaceLayout.this.saveLayoutState();
            }
        });
    }

    public final Pair<Integer, Integer> calculateResizeSnap(WindowContainer draggedWindow, int newWidth, int newHeight) {
        Intrinsics.checkNotNullParameter(draggedWindow, "draggedWindow");
        int iDpToPx = dpToPx(30);
        ViewGroup.LayoutParams layoutParams = draggedWindow.getLayoutParams();
        Intrinsics.checkNotNull(layoutParams, "null cannot be cast to non-null type android.widget.FrameLayout.LayoutParams");
        FrameLayout.LayoutParams layoutParams2 = (FrameLayout.LayoutParams) layoutParams;
        int i = layoutParams2.leftMargin;
        int i2 = layoutParams2.topMargin;
        int width = getWidth();
        int height = getHeight();
        int i3 = i + newWidth;
        if (Math.abs(i3 - width) < iDpToPx) {
            newWidth = width - i;
        }
        int i4 = i2 + newHeight;
        if (Math.abs(i4 - height) < iDpToPx) {
            newHeight = height - i2;
        }
        for (WindowContainer windowContainer : this.activeWindows.values()) {
            if (!Intrinsics.areEqual(windowContainer, draggedWindow)) {
                ViewGroup.LayoutParams layoutParams3 = windowContainer.getLayoutParams();
                Intrinsics.checkNotNull(layoutParams3, "null cannot be cast to non-null type android.widget.FrameLayout.LayoutParams");
                FrameLayout.LayoutParams layoutParams4 = (FrameLayout.LayoutParams) layoutParams3;
                int i5 = layoutParams4.leftMargin;
                int i6 = layoutParams4.topMargin;
                if (Math.abs(i3 - i5) < iDpToPx) {
                    newWidth = i5 - i;
                }
                if (Math.abs(i4 - i6) < iDpToPx) {
                    newHeight = i6 - i2;
                }
            }
        }
        return new Pair<>(Integer.valueOf(newWidth), Integer.valueOf(newHeight));
    }

    public final Pair<Integer, Integer> calculateSnap(WindowContainer draggedWindow, int newLeft, int newTop, int width, int height) {
        Intrinsics.checkNotNullParameter(draggedWindow, "draggedWindow");
        int iDpToPx = dpToPx(30);
        int width2 = getWidth();
        int height2 = getHeight();
        int i = newLeft < iDpToPx ? 0 : width2 - (newLeft + width) < iDpToPx ? width2 - width : newLeft;
        int i2 = newTop >= iDpToPx ? height2 - (newTop + height) < iDpToPx ? height2 - height : newTop : 0;
        for (WindowContainer windowContainer : this.activeWindows.values()) {
            if (!Intrinsics.areEqual(windowContainer, draggedWindow)) {
                ViewGroup.LayoutParams layoutParams = windowContainer.getLayoutParams();
                Intrinsics.checkNotNull(layoutParams, "null cannot be cast to non-null type android.widget.FrameLayout.LayoutParams");
                FrameLayout.LayoutParams layoutParams2 = (FrameLayout.LayoutParams) layoutParams;
                int i3 = layoutParams2.leftMargin;
                int i4 = layoutParams2.topMargin;
                int width3 = windowContainer.getWidth() + i3;
                int height3 = windowContainer.getHeight() + i4;
                if (Math.abs(newLeft - width3) < iDpToPx) {
                    i = width3;
                } else if (Math.abs((newLeft + width) - i3) < iDpToPx) {
                    i = i3 - width;
                }
                if (Math.abs(newTop - height3) < iDpToPx) {
                    i2 = height3;
                } else if (Math.abs((newTop + height) - i4) < iDpToPx) {
                    i2 = i4 - height;
                }
                if (Math.abs(newLeft - i3) < iDpToPx) {
                    i = i3;
                }
                if (Math.abs(newTop - i4) < iDpToPx) {
                    i2 = i4;
                }
            }
        }
        return new Pair<>(Integer.valueOf(i), Integer.valueOf(i2));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final int dpToPx(int dp) {
        return (int) (dp * getContext().getResources().getDisplayMetrics().density);
    }

    public final void openBrickCategoryWindow() {
        openWindow(BrickCategoryFragment.BRICK_CATEGORY_FRAGMENT_TAG, getLocalizedString("categories"), new Function0<Fragment>() { // from class: org.catrobat.catroid.ui.workspace.WorkspaceLayout.openBrickCategoryWindow.1
            {
                super(0);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // kotlin.jvm.functions.Function0
            public final Fragment invoke() {
                BrickCategoryFragment brickCategoryFragment = new BrickCategoryFragment();
                final WorkspaceLayout workspaceLayout = WorkspaceLayout.this;
                brickCategoryFragment.setOnCategorySelectedListener(new BrickCategoryFragment.OnCategorySelectedListener() { // from class: org.catrobat.catroid.ui.workspace.WorkspaceLayout$openBrickCategoryWindow$1$1$1
                    @Override // org.catrobat.catroid.ui.fragment.BrickCategoryFragment.OnCategorySelectedListener
                    public void onCategorySelected(String category) {
                        WorkspaceLayout workspaceLayout2 = workspaceLayout;
                        if (category == null) {
                            category = "";
                        }
                        workspaceLayout2.openAddBrickWindow(category);
                    }
                });
                return brickCategoryFragment;
            }
        });
    }

    public final void openAddBrickWindow(final String category) {
        Intrinsics.checkNotNullParameter(category, "category");
        String str = AddBrickFragment.ADD_BRICK_FRAGMENT_TAG;
        Intrinsics.checkNotNull(str);
        openWindow(str, getLocalizedString("add_brick") + ": " + category, new Function0<Fragment>() { // from class: org.catrobat.catroid.ui.workspace.WorkspaceLayout.openAddBrickWindow.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // kotlin.jvm.functions.Function0
            public final Fragment invoke() {
                FragmentManager supportFragmentManager;
                Context context = WorkspaceLayout.this.getContext();
                FragmentActivity fragmentActivity = context instanceof FragmentActivity ? (FragmentActivity) context : null;
                Fragment fragmentFindFragmentByTag = (fragmentActivity == null || (supportFragmentManager = fragmentActivity.getSupportFragmentManager()) == null) ? null : supportFragmentManager.findFragmentByTag(ScriptFragment.TAG);
                return AddBrickFragment.INSTANCE.newInstance(category, fragmentFindFragmentByTag instanceof ScriptFragment ? (ScriptFragment) fragmentFindFragmentByTag : null);
            }
        });
    }

    public final void openFormulaEditorWindow(final FormulaBrick formulaBrick, final Brick.FormulaField formulaField, final boolean showCustomView) {
        FragmentManager supportFragmentManager;
        Intrinsics.checkNotNullParameter(formulaBrick, "formulaBrick");
        Intrinsics.checkNotNullParameter(formulaField, "formulaField");
        String str = FormulaEditorFragment.FORMULA_EDITOR_FRAGMENT_TAG;
        String localizedString = getLocalizedString("formula_editor");
        WindowContainer windowContainer = this.activeWindows.get(str);
        if (windowContainer != null) {
            Intrinsics.checkNotNull(str);
            requestFocus(str);
            windowContainer.bringToFront();
            Context context = getContext();
            FragmentActivity fragmentActivity = context instanceof FragmentActivity ? (FragmentActivity) context : null;
            Fragment fragmentFindFragmentByTag = (fragmentActivity == null || (supportFragmentManager = fragmentActivity.getSupportFragmentManager()) == null) ? null : supportFragmentManager.findFragmentByTag(str);
            FormulaEditorFragment formulaEditorFragment = fragmentFindFragmentByTag instanceof FormulaEditorFragment ? (FormulaEditorFragment) fragmentFindFragmentByTag : null;
            if (formulaEditorFragment != null) {
                formulaEditorFragment.setInputFormula(formulaField, !showCustomView ? 1 : 0);
                return;
            }
            return;
        }
        Intrinsics.checkNotNull(str);
        openWindow(str, localizedString, new Function0<Fragment>() { // from class: org.catrobat.catroid.ui.workspace.WorkspaceLayout.openFormulaEditorWindow.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // kotlin.jvm.functions.Function0
            public final Fragment invoke() {
                FormulaEditorFragment formulaEditorFragment2 = new FormulaEditorFragment();
                boolean z = showCustomView;
                FormulaBrick formulaBrick2 = formulaBrick;
                Brick.FormulaField formulaField2 = formulaField;
                Bundle bundle = new Bundle();
                bundle.putSerializable(FormulaEditorFragment.FORMULA_BRICK_BUNDLE_ARGUMENT, formulaBrick2);
                bundle.putSerializable(FormulaEditorFragment.FORMULA_FIELD_BUNDLE_ARGUMENT, formulaField2);
                formulaEditorFragment2.setArguments(bundle);
                formulaEditorFragment2.setShowCustomView(z);
                return formulaEditorFragment2;
            }
        });
    }

    public final void openCategoryListWindow(final String fragmentTag, final String title) {
        Intrinsics.checkNotNullParameter(fragmentTag, "fragmentTag");
        Intrinsics.checkNotNullParameter(title, "title");
        openWindow(fragmentTag, title, new Function0<Fragment>() { // from class: org.catrobat.catroid.ui.workspace.WorkspaceLayout.openCategoryListWindow.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // kotlin.jvm.functions.Function0
            public final Fragment invoke() {
                CategoryListFragment categoryListFragment = new CategoryListFragment();
                String str = title;
                String str2 = fragmentTag;
                Bundle bundle = new Bundle();
                bundle.putString(CategoryListFragment.ACTION_BAR_TITLE_BUNDLE_ARGUMENT, str);
                bundle.putString(CategoryListFragment.FRAGMENT_TAG_BUNDLE_ARGUMENT, str2);
                categoryListFragment.setArguments(bundle);
                return categoryListFragment;
            }
        });
    }

    public final void openDataListWindow(final Brick parentBrick) {
        Intrinsics.checkNotNullParameter(parentBrick, "parentBrick");
        openWindow(DataListFragment.TAG, getLocalizedString("data_selection"), new Function0<Fragment>() { // from class: org.catrobat.catroid.ui.workspace.WorkspaceLayout.openDataListWindow.1
            {
                super(0);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // kotlin.jvm.functions.Function0
            public final Fragment invoke() {
                FragmentManager supportFragmentManager;
                DataListFragment dataListFragment = new DataListFragment();
                Brick brick = parentBrick;
                Context context = dataListFragment.getContext();
                FragmentActivity fragmentActivity = context instanceof FragmentActivity ? (FragmentActivity) context : null;
                Fragment fragmentFindFragmentByTag = (fragmentActivity == null || (supportFragmentManager = fragmentActivity.getSupportFragmentManager()) == null) ? null : supportFragmentManager.findFragmentByTag(FormulaEditorFragment.FORMULA_EDITOR_FRAGMENT_TAG);
                FormulaEditorFragment formulaEditorFragment = fragmentFindFragmentByTag instanceof FormulaEditorFragment ? (FormulaEditorFragment) fragmentFindFragmentByTag : null;
                if (formulaEditorFragment != null) {
                    dataListFragment.setFormulaEditorDataInterface(formulaEditorFragment);
                }
                Bundle bundle = new Bundle();
                bundle.putSerializable(DataListFragment.PARENT_SCRIPT_BRICK_BUNDLE_ARGUMENT, brick);
                dataListFragment.setArguments(bundle);
                return dataListFragment;
            }
        });
    }

    public final boolean handleBackPressed() {
        FragmentManager supportFragmentManager;
        FragmentManager supportFragmentManager2;
        Iterator<Map.Entry<String, WindowContainer>> it = this.activeWindows.entrySet().iterator();
        while (it.hasNext()) {
            WindowContainer value = it.next().getValue();
            if (value.getIsMaximized()) {
                value.toggleMaximize();
                return true;
            }
        }
        for (String str : CollectionsKt.listOf((Object[]) new String[]{DataListFragment.TAG, CategoryListFragment.FUNCTION_TAG, CategoryListFragment.LOGIC_TAG, CategoryListFragment.SENSOR_TAG, CategoryListFragment.OBJECT_TAG, AddBrickFragment.ADD_BRICK_FRAGMENT_TAG, BrickCategoryFragment.BRICK_CATEGORY_FRAGMENT_TAG})) {
            WindowContainer windowContainer = this.activeWindows.get(str);
            if (windowContainer != null && !windowContainer.getIsPinned()) {
                Intrinsics.checkNotNull(str);
                removeWindow(str, false);
                return true;
            }
        }
        String str2 = FormulaEditorFragment.FORMULA_EDITOR_FRAGMENT_TAG;
        WindowContainer windowContainer2 = this.activeWindows.get(str2);
        if (windowContainer2 != null && !windowContainer2.getIsPinned()) {
            Context context = getContext();
            FragmentActivity fragmentActivity = context instanceof FragmentActivity ? (FragmentActivity) context : null;
            Fragment fragmentFindFragmentByTag = (fragmentActivity == null || (supportFragmentManager2 = fragmentActivity.getSupportFragmentManager()) == null) ? null : supportFragmentManager2.findFragmentByTag(str2);
            FormulaEditorFragment formulaEditorFragment = fragmentFindFragmentByTag instanceof FormulaEditorFragment ? (FormulaEditorFragment) fragmentFindFragmentByTag : null;
            if (formulaEditorFragment != null) {
                formulaEditorFragment.saveFormulaIfPossible();
            }
            Intrinsics.checkNotNull(str2);
            removeWindow(str2, true);
            Fragment fragmentFindFragmentByTag2 = (fragmentActivity == null || (supportFragmentManager = fragmentActivity.getSupportFragmentManager()) == null) ? null : supportFragmentManager.findFragmentByTag(ScriptFragment.TAG);
            ScriptFragment scriptFragment = fragmentFindFragmentByTag2 instanceof ScriptFragment ? (ScriptFragment) fragmentFindFragmentByTag2 : null;
            if (scriptFragment != null) {
                scriptFragment.notifyDataSetChanged();
            }
            return true;
        }
        for (Map.Entry<String, WindowContainer> entry : this.activeWindows.entrySet()) {
            String key = entry.getKey();
            if (!entry.getValue().getIsPinned()) {
                removeWindow(key, false);
                return true;
            }
        }
        return false;
    }

    private final void setupWindowMenu(WindowContainer window, final String tag, final FragmentActivity activity) {
        final Integer numValueOf;
        if (Intrinsics.areEqual(tag, FormulaEditorFragment.FORMULA_EDITOR_FRAGMENT_TAG)) {
            numValueOf = Integer.valueOf(R.menu.menu_formulaeditor);
        } else {
            if (Intrinsics.areEqual(tag, ScriptFragment.TAG) ? true : Intrinsics.areEqual(tag, LookListFragment.TAG) ? true : Intrinsics.areEqual(tag, SoundListFragment.TAG)) {
                numValueOf = Integer.valueOf(R.menu.menu_script_activity);
            } else {
                numValueOf = Intrinsics.areEqual(tag, SpriteListFragment.INSTANCE.getTAG()) ? true : Intrinsics.areEqual(tag, SceneListFragment.INSTANCE.getTAG()) ? Integer.valueOf(R.menu.menu_project_activity) : null;
            }
        }
        if (numValueOf != null) {
            window.getOptionsButton().setVisibility(0);
            window.getOptionsButton().setOnClickListener(new View.OnClickListener() { // from class: org.catrobat.catroid.ui.workspace.WorkspaceLayout$$ExternalSyntheticLambda3
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    WorkspaceLayout.setupWindowMenu$lambda$33(this.f$0, numValueOf, activity, tag, view);
                }
            });
        } else {
            window.getOptionsButton().setVisibility(8);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void setupWindowMenu$lambda$33(WorkspaceLayout this$0, Integer num, final FragmentActivity fragmentActivity, String tag, View view) {
        FragmentManager supportFragmentManager;
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNullParameter(tag, "$tag");
        PopupMenu popupMenu = new PopupMenu(this$0.getContext(), view);
        popupMenu.inflate(num.intValue());
        final Fragment fragmentFindFragmentByTag = (fragmentActivity == null || (supportFragmentManager = fragmentActivity.getSupportFragmentManager()) == null) ? null : supportFragmentManager.findFragmentByTag(tag);
        if (fragmentFindFragmentByTag != null) {
            fragmentFindFragmentByTag.onPrepareOptionsMenu(popupMenu.getMenu());
        }
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() { // from class: org.catrobat.catroid.ui.workspace.WorkspaceLayout$$ExternalSyntheticLambda4
            @Override // androidx.appcompat.widget.PopupMenu.OnMenuItemClickListener
            public final boolean onMenuItemClick(MenuItem menuItem) {
                return WorkspaceLayout.setupWindowMenu$lambda$33$lambda$32(fragmentActivity, fragmentFindFragmentByTag, menuItem);
            }
        });
        popupMenu.show();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final boolean setupWindowMenu$lambda$33$lambda$32(FragmentActivity fragmentActivity, Fragment fragment, MenuItem menuItem) {
        if (CollectionsKt.listOf((Object[]) new Integer[]{Integer.valueOf(R.id.project_options), Integer.valueOf(R.id.project_files), Integer.valueOf(R.id.project_libs), Integer.valueOf(R.id.editor3d), Integer.valueOf(R.id.new_scene)}).contains(Integer.valueOf(menuItem.getItemId()))) {
            if (fragmentActivity != null) {
                return fragmentActivity.onOptionsItemSelected(menuItem);
            }
            return false;
        }
        if (fragment != null ? fragment.onOptionsItemSelected(menuItem) : false) {
            return true;
        }
        if (fragmentActivity != null) {
            return fragmentActivity.onOptionsItemSelected(menuItem);
        }
        return false;
    }

    public final boolean isWindowOpen(String tag) {
        Intrinsics.checkNotNullParameter(tag, "tag");
        return this.activeWindows.containsKey(tag);
    }

    public final void replaceWindowContent(String tag, Function0<? extends Fragment> fragmentCreator) {
        FragmentManager supportFragmentManager;
        FragmentManager supportFragmentManager2;
        FragmentTransaction fragmentTransactionBeginTransaction;
        Intrinsics.checkNotNullParameter(tag, "tag");
        Intrinsics.checkNotNullParameter(fragmentCreator, "fragmentCreator");
        WindowContainer windowContainer = this.activeWindows.get(tag);
        if (windowContainer == null) {
            return;
        }
        Context context = getContext();
        FragmentActivity fragmentActivity = context instanceof FragmentActivity ? (FragmentActivity) context : null;
        this.isReplacing = true;
        if (fragmentActivity != null && (supportFragmentManager2 = fragmentActivity.getSupportFragmentManager()) != null && (fragmentTransactionBeginTransaction = supportFragmentManager2.beginTransaction()) != null) {
            fragmentTransactionBeginTransaction.replace(windowContainer.getContentFrame().getId(), fragmentCreator.invoke(), tag);
            fragmentTransactionBeginTransaction.commitAllowingStateLoss();
        }
        if (fragmentActivity != null && (supportFragmentManager = fragmentActivity.getSupportFragmentManager()) != null) {
            supportFragmentManager.executePendingTransactions();
        }
        this.isReplacing = false;
    }
}
