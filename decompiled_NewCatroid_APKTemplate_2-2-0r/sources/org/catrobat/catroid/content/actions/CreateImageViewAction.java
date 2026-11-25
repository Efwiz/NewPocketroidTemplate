package org.catrobat.catroid.content.actions;

import android.graphics.BitmapFactory;
import android.graphics.Outline;
import android.view.View;
import android.view.ViewOutlineProvider;
import android.widget.FrameLayout;
import android.widget.ImageView;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.google.firebase.analytics.FirebaseAnalytics;
import java.io.File;
import java.lang.ref.WeakReference;
import kotlin.Metadata;
import kotlin.concurrent.ThreadsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import org.catrobat.catroid.common.NativeViewBindingManager;
import org.catrobat.catroid.content.Project;
import org.catrobat.catroid.content.Scope;
import org.catrobat.catroid.formulaeditor.Formula;
import org.catrobat.catroid.stage.StageActivity;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/actions/CreateImageViewAction.class
  classes14.dex
 */
/* JADX INFO: compiled from: CreateImageViewAction.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000:\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0016\n\u0002\u0010\u0007\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010*\u001a\u00020\n2\u0006\u0010+\u001a\u00020,H\u0016J\b\u0010-\u001a\u00020.H\u0016J\b\u0010/\u001a\u00020.H\u0016J\b\u00100\u001a\u00020.H\u0002R\u001c\u0010\u0003\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\bR\u000e\u0010\t\u001a\u00020\nX\u0082\u000e¢\u0006\u0002\n\u0000R\u001c\u0010\u000b\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\f\u0010\u0006\"\u0004\b\r\u0010\bR\u001a\u0010\u000e\u001a\u00020\u000fX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0010\u0010\u0011\"\u0004\b\u0012\u0010\u0013R\u001c\u0010\u0014\u001a\u0004\u0018\u00010\u0015X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0016\u0010\u0017\"\u0004\b\u0018\u0010\u0019R\u001c\u0010\u001a\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001b\u0010\u0006\"\u0004\b\u001c\u0010\bR\u000e\u0010\u001d\u001a\u00020\nX\u0082\u000e¢\u0006\u0002\n\u0000R\u001c\u0010\u001e\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001f\u0010\u0006\"\u0004\b \u0010\bR\u001c\u0010!\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\"\u0010\u0006\"\u0004\b#\u0010\bR\u001c\u0010$\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b%\u0010\u0006\"\u0004\b&\u0010\bR\u001c\u0010'\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b(\u0010\u0006\"\u0004\b)\u0010\b¨\u00061"}, d2 = {"Lorg/catrobat/catroid/content/actions/CreateImageViewAction;", "Lcom/badlogic/gdx/scenes/scene2d/Action;", "()V", StageActivity.STYLE_CORNER_RADIUS, "Lorg/catrobat/catroid/formulaeditor/Formula;", "getCornerRadius", "()Lorg/catrobat/catroid/formulaeditor/Formula;", "setCornerRadius", "(Lorg/catrobat/catroid/formulaeditor/Formula;)V", "finished", "", "height", "getHeight", "setHeight", "scaleSelection", "", "getScaleSelection", "()I", "setScaleSelection", "(I)V", "scope", "Lorg/catrobat/catroid/content/Scope;", "getScope", "()Lorg/catrobat/catroid/content/Scope;", "setScope", "(Lorg/catrobat/catroid/content/Scope;)V", FirebaseAnalytics.Param.SOURCE, "getSource", "setSource", "started", "viewId", "getViewId", "setViewId", "width", "getWidth", "setWidth", "x", "getX", "setX", "y", "getY", "setY", "act", "delta", "", "reset", "", "restart", "runAsyncCreate", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class CreateImageViewAction extends Action {
    private Formula cornerRadius;
    private volatile boolean finished;
    private Formula height;
    private int scaleSelection;
    private Scope scope;
    private Formula source;
    private boolean started;
    private Formula viewId;
    private Formula width;
    private Formula x;
    private Formula y;

    public final Scope getScope() {
        return this.scope;
    }

    public final void setScope(Scope scope) {
        this.scope = scope;
    }

    public final Formula getViewId() {
        return this.viewId;
    }

    public final void setViewId(Formula formula) {
        this.viewId = formula;
    }

    public final Formula getSource() {
        return this.source;
    }

    public final void setSource(Formula formula) {
        this.source = formula;
    }

    public final Formula getCornerRadius() {
        return this.cornerRadius;
    }

    public final void setCornerRadius(Formula formula) {
        this.cornerRadius = formula;
    }

    public final int getScaleSelection() {
        return this.scaleSelection;
    }

    public final void setScaleSelection(int i) {
        this.scaleSelection = i;
    }

    public final Formula getX() {
        return this.x;
    }

    public final void setX(Formula formula) {
        this.x = formula;
    }

    public final Formula getY() {
        return this.y;
    }

    public final void setY(Formula formula) {
        this.y = formula;
    }

    public final Formula getWidth() {
        return this.width;
    }

    public final void setWidth(Formula formula) {
        this.width = formula;
    }

    public final Formula getHeight() {
        return this.height;
    }

    public final void setHeight(Formula formula) {
        this.height = formula;
    }

    @Override // com.badlogic.gdx.scenes.scene2d.Action
    public boolean act(float delta) {
        if (!this.started) {
            this.started = true;
            runAsyncCreate();
        }
        return this.finished;
    }

    private final void runAsyncCreate() {
        WeakReference<StageActivity> weakReference = StageActivity.activeStageActivity;
        StageActivity stageActivity = weakReference != null ? weakReference.get() : null;
        if (stageActivity == null) {
            this.finished = true;
            return;
        }
        Formula formula = this.viewId;
        String strInterpretString = formula != null ? formula.interpretString(this.scope) : null;
        final String str = strInterpretString == null ? "" : strInterpretString;
        Formula formula2 = this.source;
        String strInterpretString2 = formula2 != null ? formula2.interpretString(this.scope) : null;
        final String str2 = strInterpretString2 == null ? "" : strInterpretString2;
        Formula formula3 = this.cornerRadius;
        Integer numInterpretInteger = formula3 != null ? formula3.interpretInteger(this.scope) : null;
        final int iIntValue = numInterpretInteger == null ? 0 : numInterpretInteger.intValue();
        Formula formula4 = this.x;
        Integer numInterpretInteger2 = formula4 != null ? formula4.interpretInteger(this.scope) : null;
        final int iIntValue2 = numInterpretInteger2 == null ? 0 : numInterpretInteger2.intValue();
        Formula formula5 = this.y;
        Integer numInterpretInteger3 = formula5 != null ? formula5.interpretInteger(this.scope) : null;
        final int iIntValue3 = numInterpretInteger3 == null ? 0 : numInterpretInteger3.intValue();
        Formula formula6 = this.width;
        Integer numInterpretInteger4 = formula6 != null ? formula6.interpretInteger(this.scope) : null;
        final int iIntValue4 = numInterpretInteger4 == null ? 300 : numInterpretInteger4.intValue();
        Formula formula7 = this.height;
        Integer numInterpretInteger5 = formula7 != null ? formula7.interpretInteger(this.scope) : null;
        final int iIntValue5 = numInterpretInteger5 == null ? 300 : numInterpretInteger5.intValue();
        if (str.length() == 0) {
            this.finished = true;
        } else {
            final StageActivity stageActivity2 = stageActivity;
            stageActivity.runOnUiThread(new Runnable() { // from class: org.catrobat.catroid.content.actions.CreateImageViewAction$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    CreateImageViewAction.runAsyncCreate$lambda$1(stageActivity2, this, iIntValue, str2, iIntValue4, iIntValue5, str, iIntValue2, iIntValue3);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void runAsyncCreate$lambda$1(StageActivity stageActivity, CreateImageViewAction this$0, final int i, String srcStr, int i2, int i3, String id2, int i4, int i5) {
        ImageView.ScaleType scaleType;
        Project project;
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNullParameter(srcStr, "$srcStr");
        Intrinsics.checkNotNullParameter(id2, "$id");
        try {
            ImageView imageView = new ImageView(stageActivity);
            int i6 = this$0.scaleSelection;
            if (i6 == 1) {
                scaleType = ImageView.ScaleType.CENTER_CROP;
            } else if (i6 == 2) {
                scaleType = ImageView.ScaleType.FIT_XY;
            } else if (i6 == 3) {
                scaleType = ImageView.ScaleType.CENTER;
            } else {
                scaleType = ImageView.ScaleType.FIT_CENTER;
            }
            imageView.setScaleType(scaleType);
            if (i > 0) {
                imageView.setOutlineProvider(new ViewOutlineProvider() { // from class: org.catrobat.catroid.content.actions.CreateImageViewAction$runAsyncCreate$1$1
                    @Override // android.view.ViewOutlineProvider
                    public void getOutline(View view, Outline outline) {
                        Intrinsics.checkNotNullParameter(view, "view");
                        Intrinsics.checkNotNullParameter(outline, "outline");
                        outline.setRoundRect(0, 0, view.getWidth(), view.getHeight(), i);
                    }
                });
                imageView.setClipToOutline(true);
            }
            File file = null;
            if (StringsKt.startsWith$default(srcStr, "http://", false, 2, (Object) null) || StringsKt.startsWith$default(srcStr, "https://", false, 2, (Object) null)) {
                ThreadsKt.thread$default(true, false, null, null, 0, new CreateImageViewAction$runAsyncCreate$1$2(srcStr, stageActivity, imageView), 30, null);
            } else {
                Scope scope = this$0.scope;
                if (scope != null && (project = scope.getProject()) != null) {
                    file = project.getFile(srcStr);
                }
                if (file != null && file.exists()) {
                    try {
                        imageView.setImageBitmap(BitmapFactory.decodeFile(file.getAbsolutePath()));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
            imageView.setVisibility(NativeViewBindingManager.getDefaultVisibility());
            FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(i2, i3);
            layoutParams.leftMargin = i4;
            layoutParams.topMargin = i5;
            stageActivity.addViewToStage(id2, imageView, layoutParams);
            this$0.finished = true;
        } catch (Throwable th) {
            this$0.finished = true;
            throw th;
        }
    }

    @Override // com.badlogic.gdx.scenes.scene2d.Action
    public void restart() {
        super.restart();
        this.started = false;
        this.finished = false;
    }

    @Override // com.badlogic.gdx.scenes.scene2d.Action, com.badlogic.gdx.utils.Pool.Poolable
    public void reset() {
        super.reset();
        this.started = false;
        this.finished = false;
    }
}
