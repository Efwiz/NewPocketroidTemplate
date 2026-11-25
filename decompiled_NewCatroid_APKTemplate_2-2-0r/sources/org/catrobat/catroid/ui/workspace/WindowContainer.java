package org.catrobat.catroid.ui.workspace;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.OvershootInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.core.content.ContextCompat;
import com.google.android.gms.analytics.ecommerce.Promotion;
import com.google.android.material.badge.BadgeDrawable;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt;
import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.stage.StageActivity;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/ui/workspace/WindowContainer.class
  classes14.dex
 */
/* JADX INFO: compiled from: WindowContainer.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\u0007\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0002\b\t\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\b\u0015\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0011\n\u0002\u0018\u0002\n\u0002\b\b\u0018\u00002\u00020\u0001B%\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u0005\u0012\b\b\u0002\u0010\u0006\u001a\u00020\u0007¢\u0006\u0002\u0010\bJ\u0018\u0010A\u001a\u00020$2\u0006\u0010B\u001a\u00020C2\u0006\u0010D\u001a\u00020\u0014H\u0002J(\u0010E\u001a\u00020$2\u0006\u0010F\u001a\u00020\u00072\u0006\u0010G\u001a\u00020\u00072\u0010\b\u0002\u0010H\u001a\n\u0012\u0004\u0012\u00020$\u0018\u00010#J(\u0010I\u001a\u00020$2\u0006\u0010J\u001a\u00020\u00072\u0006\u0010K\u001a\u00020\u00072\u0010\b\u0002\u0010H\u001a\n\u0012\u0004\u0012\u00020$\u0018\u00010#J\u0010\u0010L\u001a\u00020$2\u0006\u0010M\u001a\u00020\u0019H\u0002J\u0010\u0010N\u001a\u00020\u00072\u0006\u0010O\u001a\u00020\u0007H\u0002J\u0016\u0010P\u001a\u00020$2\u0006\u0010Q\u001a\u00020<2\u0006\u0010R\u001a\u00020<J\u0010\u0010S\u001a\u00020\u00192\u0006\u0010T\u001a\u00020UH\u0016J\u000e\u0010V\u001a\u00020$2\u0006\u0010M\u001a\u00020\u0019J\u0010\u0010W\u001a\u00020$2\u0006\u0010X\u001a\u00020\u0019H\u0002J\b\u0010Y\u001a\u00020$H\u0003J\b\u0010Z\u001a\u00020$H\u0002J\u0006\u0010[\u001a\u00020$J\u0006\u0010\\\u001a\u00020$R\u000e\u0010\t\u001a\u00020\nX\u0082.¢\u0006\u0002\n\u0000R\u001a\u0010\u000b\u001a\u00020\fX\u0086.¢\u0006\u000e\n\u0000\u001a\u0004\b\r\u0010\u000e\"\u0004\b\u000f\u0010\u0010R\u000e\u0010\u0011\u001a\u00020\u0007X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0012\u001a\u00020\u0007X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0013\u001a\u00020\u0014X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0015\u001a\u00020\u0014X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0016\u001a\u00020\u0007X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0017\u001a\u00020\u0007X\u0082\u000e¢\u0006\u0002\n\u0000R\u001a\u0010\u0018\u001a\u00020\u0019X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0018\u0010\u001a\"\u0004\b\u001b\u0010\u001cR\u001a\u0010\u001d\u001a\u00020\u0019X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001d\u0010\u001a\"\u0004\b\u001e\u0010\u001cR\u000e\u0010\u001f\u001a\u00020\nX\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010 \u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010!\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\"\u0010\"\u001a\n\u0012\u0004\u0012\u00020$\u0018\u00010#X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b%\u0010&\"\u0004\b'\u0010(R\"\u0010)\u001a\n\u0012\u0004\u0012\u00020$\u0018\u00010#X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b*\u0010&\"\u0004\b+\u0010(R\u001a\u0010,\u001a\u00020\nX\u0086.¢\u0006\u000e\n\u0000\u001a\u0004\b-\u0010.\"\u0004\b/\u00100R\u001a\u00101\u001a\u00020\nX\u0086.¢\u0006\u000e\n\u0000\u001a\u0004\b2\u0010.\"\u0004\b3\u00100R\u000e\u00104\u001a\u00020\nX\u0082.¢\u0006\u0002\n\u0000R\u000e\u00105\u001a\u00020\u0007X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u00106\u001a\u00020\u0007X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u00107\u001a\u00020\u0007X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u00108\u001a\u00020\u0007X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u00109\u001a\u00020:X\u0082.¢\u0006\u0002\n\u0000R\u001a\u0010;\u001a\u00020<X\u0086.¢\u0006\u000e\n\u0000\u001a\u0004\b=\u0010>\"\u0004\b?\u0010@¨\u0006]"}, d2 = {"Lorg/catrobat/catroid/ui/workspace/WindowContainer;", "Landroid/widget/LinearLayout;", "context", "Landroid/content/Context;", "attrs", "Landroid/util/AttributeSet;", "defStyleAttr", "", "(Landroid/content/Context;Landroid/util/AttributeSet;I)V", "closeButton", "Landroid/widget/ImageView;", "contentFrame", "Landroid/widget/FrameLayout;", "getContentFrame", "()Landroid/widget/FrameLayout;", "setContentFrame", "(Landroid/widget/FrameLayout;)V", "initialHeight", "initialLeft", "initialRawX", "", "initialRawY", "initialTop", "initialWidth", "isMaximized", "", "()Z", "setMaximized", "(Z)V", "isPinned", "setPinned", "maximizeButton", "minHeight", "minWidth", "onCloseClickListener", "Lkotlin/Function0;", "", "getOnCloseClickListener", "()Lkotlin/jvm/functions/Function0;", "setOnCloseClickListener", "(Lkotlin/jvm/functions/Function0;)V", "onLayoutChangedListener", "getOnLayoutChangedListener", "setOnLayoutChangedListener", "optionsButton", "getOptionsButton", "()Landroid/widget/ImageView;", "setOptionsButton", "(Landroid/widget/ImageView;)V", "pinButton", "getPinButton", "setPinButton", "resizeHandle", "savedHeight", "savedLeft", "savedTop", "savedWidth", "titleTextView", "Landroid/widget/TextView;", "windowTag", "", "getWindowTag", "()Ljava/lang/String;", "setWindowTag", "(Ljava/lang/String;)V", "animateScale", Promotion.ACTION_VIEW, "Landroid/view/View;", "scale", "animateToPosition", "targetLeft", "targetTop", "onEnd", "animateToSize", "targetWidth", "targetHeight", "applyGlassStyle", "focused", "dpToPx", "dp", "initWindow", "tag", "title", "onInterceptTouchEvent", "ev", "Landroid/view/MotionEvent;", "setFocusedState", "setResizingState", "isResizing", "setupDragAndResize", "setupViews", "toggleMaximize", "updatePinIconState", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class WindowContainer extends LinearLayout {
    private ImageView closeButton;
    public FrameLayout contentFrame;
    private int initialHeight;
    private int initialLeft;
    private float initialRawX;
    private float initialRawY;
    private int initialTop;
    private int initialWidth;
    private boolean isMaximized;
    private boolean isPinned;
    private ImageView maximizeButton;
    private final int minHeight;
    private final int minWidth;
    private Function0<Unit> onCloseClickListener;
    private Function0<Unit> onLayoutChangedListener;
    public ImageView optionsButton;
    public ImageView pinButton;
    private ImageView resizeHandle;
    private int savedHeight;
    private int savedLeft;
    private int savedTop;
    private int savedWidth;
    private TextView titleTextView;
    public String windowTag;

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public WindowContainer(Context context) {
        this(context, null, 0, 6, null);
        Intrinsics.checkNotNullParameter(context, "context");
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public WindowContainer(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0, 4, null);
        Intrinsics.checkNotNullParameter(context, "context");
    }

    public /* synthetic */ WindowContainer(Context context, AttributeSet attributeSet, int i, int i2, DefaultConstructorMarker defaultConstructorMarker) {
        this(context, (i2 & 2) != 0 ? null : attributeSet, (i2 & 4) != 0 ? 0 : i);
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public WindowContainer(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        Intrinsics.checkNotNullParameter(context, "context");
        this.minWidth = dpToPx(240);
        this.minHeight = dpToPx(180);
        setOrientation(1);
        setElevation(dpToPx(8));
        applyGlassStyle(false);
        setupViews();
        setAlpha(0.0f);
        setScaleX(0.85f);
        setScaleY(0.85f);
    }

    public final String getWindowTag() {
        String str = this.windowTag;
        if (str != null) {
            return str;
        }
        Intrinsics.throwUninitializedPropertyAccessException("windowTag");
        return null;
    }

    public final void setWindowTag(String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        this.windowTag = str;
    }

    public final ImageView getPinButton() {
        ImageView imageView = this.pinButton;
        if (imageView != null) {
            return imageView;
        }
        Intrinsics.throwUninitializedPropertyAccessException("pinButton");
        return null;
    }

    public final void setPinButton(ImageView imageView) {
        Intrinsics.checkNotNullParameter(imageView, "<set-?>");
        this.pinButton = imageView;
    }

    public final FrameLayout getContentFrame() {
        FrameLayout frameLayout = this.contentFrame;
        if (frameLayout != null) {
            return frameLayout;
        }
        Intrinsics.throwUninitializedPropertyAccessException("contentFrame");
        return null;
    }

    public final void setContentFrame(FrameLayout frameLayout) {
        Intrinsics.checkNotNullParameter(frameLayout, "<set-?>");
        this.contentFrame = frameLayout;
    }

    /* JADX INFO: renamed from: isPinned, reason: from getter */
    public final boolean getIsPinned() {
        return this.isPinned;
    }

    public final void setPinned(boolean z) {
        this.isPinned = z;
    }

    public final Function0<Unit> getOnLayoutChangedListener() {
        return this.onLayoutChangedListener;
    }

    public final void setOnLayoutChangedListener(Function0<Unit> function0) {
        this.onLayoutChangedListener = function0;
    }

    public final Function0<Unit> getOnCloseClickListener() {
        return this.onCloseClickListener;
    }

    public final void setOnCloseClickListener(Function0<Unit> function0) {
        this.onCloseClickListener = function0;
    }

    public final ImageView getOptionsButton() {
        ImageView imageView = this.optionsButton;
        if (imageView != null) {
            return imageView;
        }
        Intrinsics.throwUninitializedPropertyAccessException("optionsButton");
        return null;
    }

    public final void setOptionsButton(ImageView imageView) {
        Intrinsics.checkNotNullParameter(imageView, "<set-?>");
        this.optionsButton = imageView;
    }

    /* JADX INFO: renamed from: isMaximized, reason: from getter */
    public final boolean getIsMaximized() {
        return this.isMaximized;
    }

    public final void setMaximized(boolean z) {
        this.isMaximized = z;
    }

    private final void setupViews() {
        LinearLayout linearLayout = new LinearLayout(getContext());
        linearLayout.setOrientation(0);
        linearLayout.setBackgroundColor(Color.parseColor("#B0002B4D"));
        linearLayout.setPadding(dpToPx(12), dpToPx(8), dpToPx(12), dpToPx(8));
        linearLayout.setGravity(16);
        TextView textView = new TextView(getContext());
        textView.setLayoutParams(new LinearLayout.LayoutParams(0, -2, 1.0f));
        textView.setTextColor(ContextCompat.getColor(textView.getContext(), R.color.solid_white));
        textView.setTextSize(14.0f);
        textView.setTypeface(Typeface.DEFAULT_BOLD);
        this.titleTextView = textView;
        final ImageView imageView = new ImageView(getContext());
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(dpToPx(20), dpToPx(20));
        layoutParams.rightMargin = dpToPx(12);
        imageView.setLayoutParams(layoutParams);
        imageView.setImageResource(R.drawable.ic_pin);
        imageView.setColorFilter(ContextCompat.getColor(imageView.getContext(), R.color.accent));
        imageView.setRotation(-45.0f);
        imageView.setAlpha(0.6f);
        imageView.setOnClickListener(new View.OnClickListener() { // from class: org.catrobat.catroid.ui.workspace.WindowContainer$$ExternalSyntheticLambda6
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                WindowContainer.setupViews$lambda$4$lambda$3(this.f$0, imageView, view);
            }
        });
        setPinButton(imageView);
        final ImageView imageView2 = new ImageView(getContext());
        LinearLayout.LayoutParams layoutParams2 = new LinearLayout.LayoutParams(dpToPx(20), dpToPx(20));
        layoutParams2.rightMargin = dpToPx(12);
        imageView2.setLayoutParams(layoutParams2);
        imageView2.setImageResource(R.drawable.ic_more_vert);
        imageView2.setColorFilter(ContextCompat.getColor(imageView2.getContext(), R.color.accent));
        imageView2.setVisibility(8);
        imageView2.setOnClickListener(new View.OnClickListener() { // from class: org.catrobat.catroid.ui.workspace.WindowContainer$$ExternalSyntheticLambda7
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                WindowContainer.setupViews$lambda$7$lambda$6(this.f$0, imageView2, view);
            }
        });
        setOptionsButton(imageView2);
        final ImageView imageView3 = new ImageView(getContext());
        LinearLayout.LayoutParams layoutParams3 = new LinearLayout.LayoutParams(dpToPx(20), dpToPx(20));
        layoutParams3.rightMargin = dpToPx(12);
        imageView3.setLayoutParams(layoutParams3);
        imageView3.setImageResource(R.drawable.ic_smartphone_dialog_orientation_landscape);
        imageView3.setColorFilter(ContextCompat.getColor(imageView3.getContext(), R.color.accent));
        imageView3.setOnClickListener(new View.OnClickListener() { // from class: org.catrobat.catroid.ui.workspace.WindowContainer$$ExternalSyntheticLambda8
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                WindowContainer.setupViews$lambda$10$lambda$9(this.f$0, imageView3, view);
            }
        });
        this.maximizeButton = imageView3;
        final ImageView imageView4 = new ImageView(getContext());
        imageView4.setLayoutParams(new LinearLayout.LayoutParams(dpToPx(22), dpToPx(22)));
        imageView4.setImageResource(R.drawable.ic_close);
        imageView4.setColorFilter(ContextCompat.getColor(imageView4.getContext(), R.color.accent));
        imageView4.setOnClickListener(new View.OnClickListener() { // from class: org.catrobat.catroid.ui.workspace.WindowContainer$$ExternalSyntheticLambda9
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                WindowContainer.setupViews$lambda$13$lambda$12(imageView4, this, view);
            }
        });
        this.closeButton = imageView4;
        TextView textView2 = this.titleTextView;
        ImageView imageView5 = null;
        if (textView2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("titleTextView");
            textView2 = null;
        }
        linearLayout.addView(textView2);
        linearLayout.addView(getOptionsButton());
        ImageView imageView6 = this.maximizeButton;
        if (imageView6 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("maximizeButton");
            imageView6 = null;
        }
        linearLayout.addView(imageView6);
        linearLayout.addView(getPinButton());
        ImageView imageView7 = this.closeButton;
        if (imageView7 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("closeButton");
        } else {
            imageView5 = imageView7;
        }
        linearLayout.addView(imageView5);
        addView(linearLayout);
        FrameLayout frameLayout = new FrameLayout(getContext());
        frameLayout.setLayoutParams(new LinearLayout.LayoutParams(-1, 0, 1.0f));
        FrameLayout frameLayout2 = new FrameLayout(getContext());
        frameLayout2.setLayoutParams(new FrameLayout.LayoutParams(-1, -1));
        setContentFrame(frameLayout2);
        frameLayout.addView(getContentFrame());
        ImageView imageView8 = new ImageView(getContext());
        int iDpToPx = dpToPx(20);
        FrameLayout.LayoutParams layoutParams4 = new FrameLayout.LayoutParams(iDpToPx, iDpToPx);
        layoutParams4.gravity = BadgeDrawable.BOTTOM_END;
        layoutParams4.rightMargin = dpToPx(4);
        layoutParams4.bottomMargin = dpToPx(4);
        imageView8.setLayoutParams(layoutParams4);
        imageView8.setImageResource(R.drawable.ic_pocketpaint_tool_resize_adjust);
        imageView8.setColorFilter(ContextCompat.getColor(imageView8.getContext(), R.color.accent));
        this.resizeHandle = imageView8;
        frameLayout.addView(imageView8);
        addView(frameLayout);
        setupDragAndResize();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void setupViews$lambda$4$lambda$3(WindowContainer this$0, ImageView this_apply, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNullParameter(this_apply, "$this_apply");
        this$0.isPinned = !this$0.isPinned;
        this$0.updatePinIconState();
        this$0.animateScale(this_apply, 1.2f);
        Function0<Unit> function0 = this$0.onLayoutChangedListener;
        if (function0 != null) {
            function0.invoke();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void setupViews$lambda$7$lambda$6(WindowContainer this$0, ImageView this_apply, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNullParameter(this_apply, "$this_apply");
        this$0.animateScale(this_apply, 1.2f);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void setupViews$lambda$10$lambda$9(WindowContainer this$0, ImageView this_apply, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNullParameter(this_apply, "$this_apply");
        this$0.toggleMaximize();
        this$0.animateScale(this_apply, 1.2f);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void setupViews$lambda$13$lambda$12(ImageView this_apply, final WindowContainer this$0, View view) {
        Intrinsics.checkNotNullParameter(this_apply, "$this_apply");
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this_apply.animate().alpha(0.0f).scaleX(0.9f).scaleY(0.9f).setDuration(150L).withEndAction(new Runnable() { // from class: org.catrobat.catroid.ui.workspace.WindowContainer$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                WindowContainer.setupViews$lambda$13$lambda$12$lambda$11(this.f$0);
            }
        }).start();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void setupViews$lambda$13$lambda$12$lambda$11(WindowContainer this$0) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Function0<Unit> function0 = this$0.onCloseClickListener;
        if (function0 != null) {
            function0.invoke();
        }
    }

    public final void initWindow(String tag, String title) {
        Intrinsics.checkNotNullParameter(tag, "tag");
        Intrinsics.checkNotNullParameter(title, "title");
        setWindowTag(tag);
        TextView textView = this.titleTextView;
        if (textView == null) {
            Intrinsics.throwUninitializedPropertyAccessException("titleTextView");
            textView = null;
        }
        textView.setText(title);
        getContentFrame().setId((tag.hashCode() & 16777215) | 65536);
        if (Intrinsics.areEqual(tag, "StageGame")) {
            setAlpha(1.0f);
            setScaleX(1.0f);
            setScaleY(1.0f);
            return;
        }
        animate().alpha(1.0f).scaleX(1.0f).scaleY(1.0f).setDuration(250L).setInterpolator(new OvershootInterpolator(1.05f)).start();
    }

    public final void updatePinIconState() {
        getPinButton().setRotation(this.isPinned ? 0.0f : -45.0f);
        getPinButton().setAlpha(this.isPinned ? 1.0f : 0.6f);
        ImageView imageView = this.resizeHandle;
        if (imageView == null) {
            Intrinsics.throwUninitializedPropertyAccessException("resizeHandle");
            imageView = null;
        }
        imageView.setVisibility(this.isPinned ? 8 : 0);
    }

    @Override // android.view.ViewGroup
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        Intrinsics.checkNotNullParameter(ev, "ev");
        if (ev.getAction() == 0) {
            ViewParent parent = getParent();
            WorkspaceLayout workspaceLayout = parent instanceof WorkspaceLayout ? (WorkspaceLayout) parent : null;
            if (workspaceLayout != null) {
                workspaceLayout.requestFocus(getWindowTag());
                bringToFront();
                workspaceLayout.bringNavigationToFront();
            }
        }
        return super.onInterceptTouchEvent(ev);
    }

    private final void setupDragAndResize() {
        getChildAt(0).setOnTouchListener(new View.OnTouchListener() { // from class: org.catrobat.catroid.ui.workspace.WindowContainer$$ExternalSyntheticLambda4
            @Override // android.view.View.OnTouchListener
            public final boolean onTouch(View view, MotionEvent motionEvent) {
                return WindowContainer.setupDragAndResize$lambda$20(this.f$0, view, motionEvent);
            }
        });
        ImageView imageView = this.resizeHandle;
        if (imageView == null) {
            Intrinsics.throwUninitializedPropertyAccessException("resizeHandle");
            imageView = null;
        }
        imageView.setOnTouchListener(new View.OnTouchListener() { // from class: org.catrobat.catroid.ui.workspace.WindowContainer$$ExternalSyntheticLambda5
            @Override // android.view.View.OnTouchListener
            public final boolean onTouch(View view, MotionEvent motionEvent) {
                return WindowContainer.setupDragAndResize$lambda$23(this.f$0, view, motionEvent);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final boolean setupDragAndResize$lambda$20(WindowContainer this$0, View view, MotionEvent motionEvent) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        ViewParent parent = this$0.getParent();
        ViewGroup viewGroup = parent instanceof ViewGroup ? (ViewGroup) parent : null;
        if (viewGroup == null) {
            return false;
        }
        ViewParent parent2 = this$0.getParent();
        WorkspaceLayout workspaceLayout = parent2 instanceof WorkspaceLayout ? (WorkspaceLayout) parent2 : null;
        if (this$0.isMaximized) {
            return true;
        }
        int action = motionEvent.getAction();
        if (action == 0) {
            this$0.initialRawX = motionEvent.getRawX();
            this$0.initialRawY = motionEvent.getRawY();
            ViewGroup.LayoutParams layoutParams = this$0.getLayoutParams();
            Intrinsics.checkNotNull(layoutParams, "null cannot be cast to non-null type android.widget.FrameLayout.LayoutParams");
            FrameLayout.LayoutParams layoutParams2 = (FrameLayout.LayoutParams) layoutParams;
            this$0.initialLeft = layoutParams2.leftMargin;
            this$0.initialTop = layoutParams2.topMargin;
            this$0.bringToFront();
            if (workspaceLayout != null) {
                workspaceLayout.bringNavigationToFront();
            }
            if (!this$0.isPinned && workspaceLayout != null) {
                workspaceLayout.startGhostDrag(this$0);
            }
            this$0.setFocusedState(true);
        } else if (action != 1) {
            if (action != 2 || this$0.isPinned) {
                return true;
            }
            float rawX = motionEvent.getRawX() - this$0.initialRawX;
            float rawY = motionEvent.getRawY() - this$0.initialRawY;
            ViewGroup.LayoutParams layoutParams3 = this$0.getLayoutParams();
            Intrinsics.checkNotNull(layoutParams3, "null cannot be cast to non-null type android.widget.FrameLayout.LayoutParams");
            FrameLayout.LayoutParams layoutParams4 = (FrameLayout.LayoutParams) layoutParams3;
            int i = (int) (this$0.initialLeft + rawX);
            int i2 = (int) (this$0.initialTop + rawY);
            if (workspaceLayout != null) {
                layoutParams4.leftMargin = i;
                layoutParams4.topMargin = i2;
                this$0.setLayoutParams(layoutParams4);
                workspaceLayout.updateGhostDrag(this$0, layoutParams4.leftMargin, layoutParams4.topMargin);
            } else {
                Integer numValueOf = Integer.valueOf(viewGroup.getWidth());
                if (numValueOf.intValue() <= 0) {
                    numValueOf = null;
                }
                int iIntValue = numValueOf != null ? numValueOf.intValue() : this$0.getContext().getResources().getDisplayMetrics().widthPixels;
                Integer numValueOf2 = Integer.valueOf(viewGroup.getHeight());
                Integer num = numValueOf2.intValue() > 0 ? numValueOf2 : null;
                int iIntValue2 = num != null ? num.intValue() : this$0.getContext().getResources().getDisplayMetrics().heightPixels;
                int iCoerceAtLeast = RangesKt.coerceAtLeast(iIntValue - this$0.getWidth(), 0);
                int iCoerceAtLeast2 = RangesKt.coerceAtLeast(iIntValue2 - this$0.getHeight(), 0);
                layoutParams4.leftMargin = RangesKt.coerceIn(i, 0, iCoerceAtLeast);
                layoutParams4.topMargin = RangesKt.coerceIn(i2, 0, iCoerceAtLeast2);
                this$0.setLayoutParams(layoutParams4);
            }
        } else {
            this$0.setFocusedState(false);
            if (this$0.isPinned) {
                return true;
            }
            ViewGroup.LayoutParams layoutParams5 = this$0.getLayoutParams();
            Intrinsics.checkNotNull(layoutParams5, "null cannot be cast to non-null type android.widget.FrameLayout.LayoutParams");
            FrameLayout.LayoutParams layoutParams6 = (FrameLayout.LayoutParams) layoutParams5;
            if (workspaceLayout != null) {
                Pair<Integer, Integer> pairCalculateSnap = workspaceLayout.calculateSnap(this$0, layoutParams6.leftMargin, layoutParams6.topMargin, this$0.getWidth(), this$0.getHeight());
                workspaceLayout.endGhostDrag(this$0, pairCalculateSnap.getFirst().intValue(), pairCalculateSnap.getSecond().intValue());
            } else {
                Function0<Unit> function0 = this$0.onLayoutChangedListener;
                if (function0 != null) {
                    function0.invoke();
                }
            }
        }
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final boolean setupDragAndResize$lambda$23(WindowContainer this$0, View view, MotionEvent motionEvent) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        if (!this$0.isPinned && !this$0.isMaximized) {
            ViewParent parent = this$0.getParent();
            ViewGroup viewGroup = parent instanceof ViewGroup ? (ViewGroup) parent : null;
            if (viewGroup == null) {
                return false;
            }
            ViewParent parent2 = this$0.getParent();
            WorkspaceLayout workspaceLayout = parent2 instanceof WorkspaceLayout ? (WorkspaceLayout) parent2 : null;
            int action = motionEvent.getAction();
            if (action == 0) {
                this$0.initialRawX = motionEvent.getRawX();
                this$0.initialRawY = motionEvent.getRawY();
                this$0.initialWidth = this$0.getWidth();
                this$0.initialHeight = this$0.getHeight();
                this$0.setResizingState(true);
                if (workspaceLayout != null) {
                    workspaceLayout.startGhostDrag(this$0);
                }
            } else if (action == 1) {
                this$0.setResizingState(false);
                ViewGroup.LayoutParams layoutParams = this$0.getLayoutParams();
                Intrinsics.checkNotNull(layoutParams, "null cannot be cast to non-null type android.widget.FrameLayout.LayoutParams");
                FrameLayout.LayoutParams layoutParams2 = (FrameLayout.LayoutParams) layoutParams;
                if (workspaceLayout != null) {
                    Pair<Integer, Integer> pairCalculateResizeSnap = workspaceLayout.calculateResizeSnap(this$0, layoutParams2.width, layoutParams2.height);
                    workspaceLayout.endGhostResize(this$0, pairCalculateResizeSnap.component1().intValue(), pairCalculateResizeSnap.component2().intValue());
                } else {
                    Function0<Unit> function0 = this$0.onLayoutChangedListener;
                    if (function0 != null) {
                        function0.invoke();
                    }
                }
            } else if (action == 2) {
                float rawX = motionEvent.getRawX() - this$0.initialRawX;
                float rawY = motionEvent.getRawY() - this$0.initialRawY;
                ViewGroup.LayoutParams layoutParams3 = this$0.getLayoutParams();
                Intrinsics.checkNotNull(layoutParams3, "null cannot be cast to non-null type android.widget.FrameLayout.LayoutParams");
                FrameLayout.LayoutParams layoutParams4 = (FrameLayout.LayoutParams) layoutParams3;
                int iCoerceAtLeast = RangesKt.coerceAtLeast((int) (this$0.initialWidth + rawX), this$0.minWidth);
                int iCoerceAtLeast2 = RangesKt.coerceAtLeast((int) (this$0.initialHeight + rawY), this$0.minHeight);
                Integer numValueOf = Integer.valueOf(viewGroup.getWidth());
                if (numValueOf.intValue() <= 0) {
                    numValueOf = null;
                }
                int iIntValue = numValueOf != null ? numValueOf.intValue() : this$0.getContext().getResources().getDisplayMetrics().widthPixels;
                Integer numValueOf2 = Integer.valueOf(viewGroup.getHeight());
                Integer num = numValueOf2.intValue() > 0 ? numValueOf2 : null;
                int iIntValue2 = num != null ? num.intValue() : this$0.getContext().getResources().getDisplayMetrics().heightPixels;
                if (workspaceLayout != null) {
                    if (!Intrinsics.areEqual(this$0.getWindowTag(), "StageGame")) {
                        layoutParams4.width = iCoerceAtLeast;
                        layoutParams4.height = iCoerceAtLeast2;
                        this$0.setLayoutParams(layoutParams4);
                    }
                    Pair<Integer, Integer> pairCalculateResizeSnap2 = workspaceLayout.calculateResizeSnap(this$0, iCoerceAtLeast, iCoerceAtLeast2);
                    workspaceLayout.updateGhostResize(this$0, layoutParams4.leftMargin, layoutParams4.topMargin, pairCalculateResizeSnap2.component1().intValue(), pairCalculateResizeSnap2.component2().intValue());
                } else {
                    int iCoerceAtLeast3 = RangesKt.coerceAtLeast(iIntValue - layoutParams4.leftMargin, this$0.minWidth);
                    int iCoerceAtLeast4 = RangesKt.coerceAtLeast(iIntValue2 - layoutParams4.topMargin, this$0.minHeight);
                    layoutParams4.width = RangesKt.coerceIn(iCoerceAtLeast, this$0.minWidth, iCoerceAtLeast3);
                    layoutParams4.height = RangesKt.coerceIn(iCoerceAtLeast2, this$0.minHeight, iCoerceAtLeast4);
                    this$0.setLayoutParams(layoutParams4);
                }
            }
        }
        return true;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static /* synthetic */ void animateToPosition$default(WindowContainer windowContainer, int i, int i2, Function0 function0, int i3, Object obj) {
        if ((i3 & 4) != 0) {
            function0 = null;
        }
        windowContainer.animateToPosition(i, i2, function0);
    }

    public final void animateToPosition(final int targetLeft, final int targetTop, final Function0<Unit> onEnd) {
        ViewGroup.LayoutParams layoutParams = getLayoutParams();
        Intrinsics.checkNotNull(layoutParams, "null cannot be cast to non-null type android.widget.FrameLayout.LayoutParams");
        final FrameLayout.LayoutParams layoutParams2 = (FrameLayout.LayoutParams) layoutParams;
        if (Intrinsics.areEqual(getWindowTag(), "StageGame")) {
            layoutParams2.leftMargin = targetLeft;
            layoutParams2.topMargin = targetTop;
            setLayoutParams(layoutParams2);
            if (onEnd != null) {
                onEnd.invoke();
                return;
            }
            return;
        }
        final int i = layoutParams2.leftMargin;
        final int i2 = layoutParams2.topMargin;
        ValueAnimator valueAnimatorOfFloat = ValueAnimator.ofFloat(0.0f, 1.0f);
        valueAnimatorOfFloat.setDuration(200L);
        valueAnimatorOfFloat.setInterpolator(new DecelerateInterpolator());
        valueAnimatorOfFloat.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: org.catrobat.catroid.ui.workspace.WindowContainer$$ExternalSyntheticLambda2
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                WindowContainer.animateToPosition$lambda$25$lambda$24(layoutParams2, i, targetLeft, i2, targetTop, this, valueAnimator);
            }
        });
        valueAnimatorOfFloat.addListener(new AnimatorListenerAdapter() { // from class: org.catrobat.catroid.ui.workspace.WindowContainer$animateToPosition$1$2
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator animation) {
                Intrinsics.checkNotNullParameter(animation, "animation");
                Function0<Unit> function0 = onEnd;
                if (function0 != null) {
                    function0.invoke();
                }
            }
        });
        valueAnimatorOfFloat.start();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void animateToPosition$lambda$25$lambda$24(FrameLayout.LayoutParams params, int i, int i2, int i3, int i4, WindowContainer this$0, ValueAnimator animator) {
        Intrinsics.checkNotNullParameter(params, "$params");
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNullParameter(animator, "animator");
        Object animatedValue = animator.getAnimatedValue();
        Intrinsics.checkNotNull(animatedValue, "null cannot be cast to non-null type kotlin.Float");
        float fFloatValue = ((Float) animatedValue).floatValue();
        params.leftMargin = (int) (i + ((i2 - i) * fFloatValue));
        params.topMargin = (int) (i3 + ((i4 - i3) * fFloatValue));
        this$0.setLayoutParams(params);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static /* synthetic */ void animateToSize$default(WindowContainer windowContainer, int i, int i2, Function0 function0, int i3, Object obj) {
        if ((i3 & 4) != 0) {
            function0 = null;
        }
        windowContainer.animateToSize(i, i2, function0);
    }

    public final void animateToSize(final int targetWidth, final int targetHeight, final Function0<Unit> onEnd) {
        ViewGroup.LayoutParams layoutParams = getLayoutParams();
        Intrinsics.checkNotNull(layoutParams, "null cannot be cast to non-null type android.widget.FrameLayout.LayoutParams");
        final FrameLayout.LayoutParams layoutParams2 = (FrameLayout.LayoutParams) layoutParams;
        if (Intrinsics.areEqual(getWindowTag(), "StageGame")) {
            layoutParams2.width = targetWidth;
            layoutParams2.height = targetHeight;
            setLayoutParams(layoutParams2);
            Context context = getContext();
            StageActivity stageActivity = context instanceof StageActivity ? (StageActivity) context : null;
            if (stageActivity != null) {
                stageActivity.updateStageSize(targetWidth, targetHeight);
            }
            if (onEnd != null) {
                onEnd.invoke();
                return;
            }
            return;
        }
        final int width = getWidth();
        final int height = getHeight();
        ValueAnimator valueAnimatorOfFloat = ValueAnimator.ofFloat(0.0f, 1.0f);
        valueAnimatorOfFloat.setDuration(180L);
        valueAnimatorOfFloat.setInterpolator(new DecelerateInterpolator());
        valueAnimatorOfFloat.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: org.catrobat.catroid.ui.workspace.WindowContainer$$ExternalSyntheticLambda0
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                WindowContainer.animateToSize$lambda$27$lambda$26(layoutParams2, width, targetWidth, height, targetHeight, this, valueAnimator);
            }
        });
        valueAnimatorOfFloat.addListener(new AnimatorListenerAdapter() { // from class: org.catrobat.catroid.ui.workspace.WindowContainer$animateToSize$1$2
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator animation) {
                Intrinsics.checkNotNullParameter(animation, "animation");
                Function0<Unit> function0 = onEnd;
                if (function0 != null) {
                    function0.invoke();
                }
            }
        });
        valueAnimatorOfFloat.start();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void animateToSize$lambda$27$lambda$26(FrameLayout.LayoutParams params, int i, int i2, int i3, int i4, WindowContainer this$0, ValueAnimator animator) {
        Intrinsics.checkNotNullParameter(params, "$params");
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNullParameter(animator, "animator");
        Object animatedValue = animator.getAnimatedValue();
        Intrinsics.checkNotNull(animatedValue, "null cannot be cast to non-null type kotlin.Float");
        float fFloatValue = ((Float) animatedValue).floatValue();
        params.width = (int) (i + ((i2 - i) * fFloatValue));
        params.height = (int) (i3 + ((i4 - i3) * fFloatValue));
        this$0.setLayoutParams(params);
    }

    public final void toggleMaximize() {
        ViewParent parent = getParent();
        ImageView imageView = null;
        ViewGroup viewGroup = parent instanceof ViewGroup ? (ViewGroup) parent : null;
        if (viewGroup == null) {
            return;
        }
        ViewGroup.LayoutParams layoutParams = getLayoutParams();
        Intrinsics.checkNotNull(layoutParams, "null cannot be cast to non-null type android.widget.FrameLayout.LayoutParams");
        final FrameLayout.LayoutParams layoutParams2 = (FrameLayout.LayoutParams) layoutParams;
        View childAt = getChildAt(0);
        if (!this.isMaximized) {
            this.savedWidth = layoutParams2.width;
            this.savedHeight = layoutParams2.height;
            this.savedLeft = layoutParams2.leftMargin;
            this.savedTop = layoutParams2.topMargin;
            childAt.setVisibility(8);
            ImageView imageView2 = this.resizeHandle;
            if (imageView2 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("resizeHandle");
            } else {
                imageView = imageView2;
            }
            imageView.setVisibility(8);
            setPadding(0, 0, 0, 0);
            animateToPosition$default(this, 0, 0, null, 4, null);
            animateToSize(viewGroup.getWidth(), viewGroup.getHeight(), new Function0<Unit>() { // from class: org.catrobat.catroid.ui.workspace.WindowContainer.toggleMaximize.1
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
                    layoutParams2.leftMargin = 0;
                    layoutParams2.topMargin = 0;
                    layoutParams2.width = -1;
                    layoutParams2.height = -1;
                    this.setLayoutParams(layoutParams2);
                    this.setMaximized(true);
                }
            });
            return;
        }
        childAt.setVisibility(0);
        updatePinIconState();
        layoutParams2.width = this.savedWidth;
        layoutParams2.height = this.savedHeight;
        setLayoutParams(layoutParams2);
        animateToPosition$default(this, this.savedLeft, this.savedTop, null, 4, null);
        animateToSize(this.savedWidth, this.savedHeight, new Function0<Unit>() { // from class: org.catrobat.catroid.ui.workspace.WindowContainer.toggleMaximize.2
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
                WindowContainer.this.setMaximized(false);
            }
        });
    }

    public final void setFocusedState(boolean focused) {
        setElevation(dpToPx(focused ? 12 : 4));
        applyGlassStyle(focused);
    }

    private final void applyGlassStyle(boolean focused) {
        int color;
        GradientDrawable gradientDrawable = new GradientDrawable();
        gradientDrawable.setColor(Color.parseColor("#D0003554"));
        if (focused) {
            color = ContextCompat.getColor(getContext(), R.color.accent);
        } else {
            color = Color.parseColor("#33FFFFFF");
        }
        gradientDrawable.setStroke(dpToPx(1), color);
        gradientDrawable.setCornerRadius(dpToPx(16));
        setBackground(gradientDrawable);
    }

    private final void setResizingState(boolean isResizing) {
        if (isResizing) {
            setAlpha(0.65f);
            GradientDrawable gradientDrawable = new GradientDrawable();
            gradientDrawable.setColor(Color.parseColor("#44A8DFF4"));
            gradientDrawable.setStroke(dpToPx(2), ContextCompat.getColor(getContext(), R.color.accent));
            gradientDrawable.setCornerRadius(dpToPx(16));
            setBackground(gradientDrawable);
            return;
        }
        setAlpha(1.0f);
        applyGlassStyle(true);
    }

    private final void animateScale(final View view, float scale) {
        view.animate().scaleX(scale).scaleY(scale).setDuration(100L).withEndAction(new Runnable() { // from class: org.catrobat.catroid.ui.workspace.WindowContainer$$ExternalSyntheticLambda3
            @Override // java.lang.Runnable
            public final void run() {
                WindowContainer.animateScale$lambda$30(view);
            }
        }).start();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void animateScale$lambda$30(View view) {
        Intrinsics.checkNotNullParameter(view, "$view");
        view.animate().scaleX(1.0f).scaleY(1.0f).setDuration(100L).start();
    }

    private final int dpToPx(int dp) {
        return (int) (dp * getContext().getResources().getDisplayMetrics().density);
    }
}
