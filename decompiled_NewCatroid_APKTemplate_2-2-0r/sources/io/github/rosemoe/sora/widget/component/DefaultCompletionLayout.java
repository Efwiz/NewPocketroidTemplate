package io.github.rosemoe.sora.widget.component;

import android.R;
import android.animation.LayoutTransition;
import android.content.Context;
import android.graphics.Outline;
import android.graphics.drawable.GradientDrawable;
import android.os.SystemClock;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewOutlineProvider;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;
import io.github.rosemoe.sora.widget.schemes.EditorColorScheme;

/* JADX INFO: loaded from: classes11.dex */
public class DefaultCompletionLayout implements CompletionLayout {
    private EditorAutoCompletion editorAutoCompletion;
    private boolean enabledAnimation = false;
    private ListView listView;
    private ProgressBar progressBar;
    private LinearLayout rootView;

    @Override // io.github.rosemoe.sora.widget.component.CompletionLayout
    public void setEditorCompletion(EditorAutoCompletion editorAutoCompletion) {
        this.editorAutoCompletion = editorAutoCompletion;
    }

    @Override // io.github.rosemoe.sora.widget.component.CompletionLayout
    public void setEnabledAnimation(boolean z) {
        this.enabledAnimation = z;
        if (z) {
            LayoutTransition layoutTransition = new LayoutTransition();
            layoutTransition.enableTransitionType(4);
            layoutTransition.enableTransitionType(2);
            layoutTransition.enableTransitionType(3);
            layoutTransition.enableTransitionType(0);
            layoutTransition.enableTransitionType(1);
            layoutTransition.addTransitionListener(new LayoutTransition.TransitionListener() { // from class: io.github.rosemoe.sora.widget.component.DefaultCompletionLayout.1
                @Override // android.animation.LayoutTransition.TransitionListener
                public void startTransition(LayoutTransition layoutTransition2, ViewGroup viewGroup, View view, int i) {
                }

                @Override // android.animation.LayoutTransition.TransitionListener
                public void endTransition(LayoutTransition layoutTransition2, ViewGroup viewGroup, View view, int i) {
                    if (view != DefaultCompletionLayout.this.listView) {
                        return;
                    }
                    view.requestLayout();
                }
            });
            this.rootView.setLayoutTransition(layoutTransition);
            this.listView.setLayoutTransition(layoutTransition);
            return;
        }
        this.rootView.setLayoutTransition(null);
        this.listView.setLayoutTransition(null);
    }

    @Override // io.github.rosemoe.sora.widget.component.CompletionLayout
    public View inflate(final Context context) {
        LinearLayout linearLayout = new LinearLayout(context);
        this.rootView = linearLayout;
        this.listView = new ListView(context);
        this.progressBar = new ProgressBar(context, null, R.attr.progressBarStyleHorizontal);
        linearLayout.setOrientation(1);
        setEnabledAnimation(false);
        linearLayout.addView(this.progressBar, new LinearLayout.LayoutParams(-1, (int) TypedValue.applyDimension(1, 20.0f, context.getResources().getDisplayMetrics())));
        linearLayout.addView(this.listView, new LinearLayout.LayoutParams(-1, -1));
        this.progressBar.setIndeterminate(true);
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) this.progressBar.getLayoutParams();
        layoutParams.topMargin = (int) TypedValue.applyDimension(1, -8.0f, context.getResources().getDisplayMetrics());
        layoutParams.bottomMargin = (int) TypedValue.applyDimension(1, -8.0f, context.getResources().getDisplayMetrics());
        layoutParams.leftMargin = (int) TypedValue.applyDimension(1, 4.0f, context.getResources().getDisplayMetrics());
        layoutParams.rightMargin = (int) TypedValue.applyDimension(1, 4.0f, context.getResources().getDisplayMetrics());
        GradientDrawable gradientDrawable = new GradientDrawable();
        gradientDrawable.setCornerRadius(TypedValue.applyDimension(1, 8.0f, context.getResources().getDisplayMetrics()));
        linearLayout.setBackground(gradientDrawable);
        setRootViewOutlineProvider(this.rootView);
        this.listView.setDividerHeight(0);
        setLoading(true);
        this.listView.setOnItemClickListener(new AdapterView.OnItemClickListener() { // from class: io.github.rosemoe.sora.widget.component.DefaultCompletionLayout$$ExternalSyntheticLambda1
            @Override // android.widget.AdapterView.OnItemClickListener
            public final void onItemClick(AdapterView adapterView, View view, int i, long j) {
                this.f$0.lambda$inflate$0(context, adapterView, view, i, j);
            }
        });
        return linearLayout;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$inflate$0(Context context, AdapterView adapterView, View view, int i, long j) {
        try {
            this.editorAutoCompletion.select(i);
        } catch (Exception e) {
            e.printStackTrace(System.err);
            Toast.makeText(context, e.toString(), 0).show();
        }
    }

    @Override // io.github.rosemoe.sora.widget.component.CompletionLayout
    public void onApplyColorScheme(EditorColorScheme editorColorScheme) {
        GradientDrawable gradientDrawable = new GradientDrawable();
        gradientDrawable.setCornerRadius(TypedValue.applyDimension(1, 8.0f, this.editorAutoCompletion.getContext().getResources().getDisplayMetrics()));
        gradientDrawable.setStroke(1, editorColorScheme.getColor(20));
        gradientDrawable.setColor(editorColorScheme.getColor(19));
        this.rootView.setBackground(gradientDrawable);
        setRootViewOutlineProvider(this.rootView);
    }

    @Override // io.github.rosemoe.sora.widget.component.CompletionLayout
    public void setLoading(boolean z) {
        this.progressBar.setVisibility(z ? 0 : 8);
    }

    @Override // io.github.rosemoe.sora.widget.component.CompletionLayout
    public ListView getCompletionList() {
        return this.listView;
    }

    private void performScrollList(int i) {
        ListView completionList = getCompletionList();
        long jUptimeMillis = SystemClock.uptimeMillis();
        MotionEvent motionEventObtain = MotionEvent.obtain(jUptimeMillis, jUptimeMillis, 0, 0.0f, 0.0f, 0);
        completionList.onTouchEvent(motionEventObtain);
        motionEventObtain.recycle();
        float f = i;
        MotionEvent motionEventObtain2 = MotionEvent.obtain(jUptimeMillis, jUptimeMillis, 2, 0.0f, f, 0);
        completionList.onTouchEvent(motionEventObtain2);
        motionEventObtain2.recycle();
        MotionEvent motionEventObtain3 = MotionEvent.obtain(jUptimeMillis, jUptimeMillis, 3, 0.0f, f, 0);
        completionList.onTouchEvent(motionEventObtain3);
        motionEventObtain3.recycle();
    }

    private void setRootViewOutlineProvider(View view) {
        view.setOutlineProvider(new ViewOutlineProvider() { // from class: io.github.rosemoe.sora.widget.component.DefaultCompletionLayout.2
            @Override // android.view.ViewOutlineProvider
            public void getOutline(View view2, Outline outline) {
                outline.setRoundRect(0, 0, view2.getWidth(), view2.getHeight(), TypedValue.applyDimension(1, 8.0f, view2.getContext().getResources().getDisplayMetrics()));
            }
        });
        view.setClipToOutline(true);
    }

    @Override // io.github.rosemoe.sora.widget.component.CompletionLayout
    public void ensureListPositionVisible(final int i, final int i2) {
        this.listView.post(new Runnable() { // from class: io.github.rosemoe.sora.widget.component.DefaultCompletionLayout$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$ensureListPositionVisible$1(i, i2);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$ensureListPositionVisible$1(int i, int i2) {
        while (this.listView.getFirstVisiblePosition() + 1 > i && this.listView.canScrollList(-1)) {
            performScrollList(i2 / 2);
        }
        while (this.listView.getLastVisiblePosition() - 1 < i && this.listView.canScrollList(1)) {
            performScrollList((-i2) / 2);
        }
    }
}
