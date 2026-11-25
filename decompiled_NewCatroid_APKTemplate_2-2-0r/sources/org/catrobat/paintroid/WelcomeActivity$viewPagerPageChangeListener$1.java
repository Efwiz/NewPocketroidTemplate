package org.catrobat.paintroid;

import android.view.View;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.viewpager.widget.ViewPager;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.catrobat.paintroid.tools.ToolType;

/* JADX INFO: compiled from: WelcomeActivity.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000\u001b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0002\b\u0004*\u0001\u0000\b\n\u0018\u00002\u00020\u0001J\u0010\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u0003H\u0016J\u0010\u0010\u000b\u001a\u00020\t2\u0006\u0010\f\u001a\u00020\u0003H\u0016R\u001a\u0010\u0002\u001a\u00020\u0003X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0004\u0010\u0005\"\u0004\b\u0006\u0010\u0007¨\u0006\r"}, d2 = {"org/catrobat/paintroid/WelcomeActivity$viewPagerPageChangeListener$1", "Landroidx/viewpager/widget/ViewPager$SimpleOnPageChangeListener;", "pos", "", "getPos", "()I", "setPos", "(I)V", "onPageScrollStateChanged", "", "state", "onPageSelected", "position", "Paintroid_signedRelease"}, k = 1, mv = {1, 5, 1}, xi = 48)
public final class WelcomeActivity$viewPagerPageChangeListener$1 extends ViewPager.SimpleOnPageChangeListener {
    private int pos;
    final /* synthetic */ WelcomeActivity this$0;

    WelcomeActivity$viewPagerPageChangeListener$1(WelcomeActivity welcomeActivity) {
        this.this$0 = welcomeActivity;
    }

    public final int getPos() {
        return this.pos;
    }

    public final void setPos(int i) {
        this.pos = i;
    }

    @Override // androidx.viewpager.widget.ViewPager.SimpleOnPageChangeListener, androidx.viewpager.widget.ViewPager.OnPageChangeListener
    public void onPageSelected(int position) {
        super.onPageSelected(position);
        this.pos = position;
        this.this$0.addBottomDots(position);
        AppCompatButton appCompatButton = null;
        if (this.this$0.getDotsIndex(position) == this.this$0.getLayouts().length - 1) {
            AppCompatButton appCompatButton2 = this.this$0.btnNext;
            if (appCompatButton2 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("btnNext");
                appCompatButton2 = null;
            }
            appCompatButton2.setText(R.string.lets_go);
            AppCompatButton appCompatButton3 = this.this$0.btnSkip;
            if (appCompatButton3 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("btnSkip");
            } else {
                appCompatButton = appCompatButton3;
            }
            appCompatButton.setVisibility(8);
            return;
        }
        AppCompatButton appCompatButton4 = this.this$0.btnNext;
        if (appCompatButton4 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("btnNext");
            appCompatButton4 = null;
        }
        appCompatButton4.setText(R.string.next);
        AppCompatButton appCompatButton5 = this.this$0.btnSkip;
        if (appCompatButton5 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("btnSkip");
        } else {
            appCompatButton = appCompatButton5;
        }
        appCompatButton.setVisibility(0);
    }

    @Override // androidx.viewpager.widget.ViewPager.SimpleOnPageChangeListener, androidx.viewpager.widget.ViewPager.OnPageChangeListener
    public void onPageScrollStateChanged(int state) {
        super.onPageScrollStateChanged(state);
        if (state == 0) {
            ToolType[] toolTypeArrValues = ToolType.values();
            if (this.this$0.getLayouts()[this.pos] == R.layout.pocketpaint_slide_intro_possibilities) {
                View viewFindViewById = this.this$0.findViewById(R.id.pocketpaint_intro_possibilities_head);
                Intrinsics.checkNotNullExpressionValue(viewFindViewById, "findViewById(R.id.pocket…intro_possibilities_head)");
                AppCompatTextView appCompatTextView = (AppCompatTextView) viewFindViewById;
                View viewFindViewById2 = this.this$0.findViewById(R.id.pocketpaint_intro_possibilities_text);
                Intrinsics.checkNotNullExpressionValue(viewFindViewById2, "findViewById(R.id.pocket…intro_possibilities_text)");
                AppCompatTextView appCompatTextView2 = (AppCompatTextView) viewFindViewById2;
                this.this$0.setUpUndoAndRedoButtons(appCompatTextView, appCompatTextView2);
                this.this$0.setUpNavigationView(appCompatTextView, appCompatTextView2);
                return;
            }
            if (this.this$0.getLayouts()[this.pos] == R.layout.pocketpaint_slide_intro_tools_selection) {
                View viewFindViewById3 = this.this$0.findViewById(R.id.pocketpaint_intro_bottom_bar);
                int length = toolTypeArrValues.length;
                int i = 0;
                while (i < length) {
                    final ToolType toolType = toolTypeArrValues[i];
                    i++;
                    View viewFindViewById4 = viewFindViewById3.findViewById(toolType.getToolButtonID());
                    if (viewFindViewById4 != null) {
                        final WelcomeActivity welcomeActivity = this.this$0;
                        viewFindViewById4.setOnClickListener(new View.OnClickListener() { // from class: org.catrobat.paintroid.WelcomeActivity$viewPagerPageChangeListener$1$$ExternalSyntheticLambda0
                            @Override // android.view.View.OnClickListener
                            public final void onClick(View view) {
                                WelcomeActivity$viewPagerPageChangeListener$1.m5892onPageScrollStateChanged$lambda0(welcomeActivity, toolType, view);
                            }
                        });
                    }
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: onPageScrollStateChanged$lambda-0, reason: not valid java name */
    public static final void m5892onPageScrollStateChanged$lambda0(WelcomeActivity this$0, ToolType type, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNullParameter(type, "$type");
        ((AppCompatTextView) this$0.findViewById(R.id.pocketpaint_textview_intro_tools_header)).setText(type.getNameResource());
        ((AppCompatTextView) this$0.findViewById(R.id.pocketpaint_tools_info_description)).setText(type.getHelpTextResource());
        ((AppCompatImageView) this$0.findViewById(R.id.pocketpaint_tools_info_icon)).setImageResource(type.getDrawableResource());
    }
}
