package org.catrobat.catroid.content.actions;

import android.graphics.Bitmap;
import android.media.MediaPlayer;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.ScrollView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.VideoView;
import androidx.appcompat.widget.SwitchCompat;
import com.badlogic.gdx.scenes.scene2d.actions.TemporalAction;
import com.huawei.hms.ml.language.common.utils.Constant;
import java.lang.ref.WeakReference;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import org.catrobat.catroid.content.Scope;
import org.catrobat.catroid.formulaeditor.Formula;
import org.catrobat.catroid.formulaeditor.UserVariable;
import org.catrobat.catroid.stage.StageActivity;
import org.eclipse.jgit.lib.ConfigConstants;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/actions/NativeViewListenerAction.class
  classes14.dex
 */
/* JADX INFO: compiled from: NativeViewListenerAction.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000:\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\u0007\n\u0000\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\b\u0010\u001b\u001a\u00020\u001cH\u0016J\u0010\u0010\u001d\u001a\u00020\u001c2\u0006\u0010\u001e\u001a\u00020\u001fH\u0014R\u001a\u0010\u0003\u001a\u00020\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\bR\u001c\u0010\t\u001a\u0004\u0018\u00010\nX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000b\u0010\f\"\u0004\b\r\u0010\u000eR\u001c\u0010\u000f\u001a\u0004\u0018\u00010\u0010X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0011\u0010\u0012\"\u0004\b\u0013\u0010\u0014R\u001c\u0010\u0015\u001a\u0004\u0018\u00010\u0016X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0017\u0010\u0018\"\u0004\b\u0019\u0010\u001a¨\u0006 "}, d2 = {"Lorg/catrobat/catroid/content/actions/NativeViewListenerAction;", "Lcom/badlogic/gdx/scenes/scene2d/actions/TemporalAction;", "()V", "eventSelection", "", "getEventSelection", "()I", "setEventSelection", "(I)V", "scope", "Lorg/catrobat/catroid/content/Scope;", "getScope", "()Lorg/catrobat/catroid/content/Scope;", "setScope", "(Lorg/catrobat/catroid/content/Scope;)V", "userVariable", "Lorg/catrobat/catroid/formulaeditor/UserVariable;", "getUserVariable", "()Lorg/catrobat/catroid/formulaeditor/UserVariable;", "setUserVariable", "(Lorg/catrobat/catroid/formulaeditor/UserVariable;)V", "viewId", "Lorg/catrobat/catroid/formulaeditor/Formula;", "getViewId", "()Lorg/catrobat/catroid/formulaeditor/Formula;", "setViewId", "(Lorg/catrobat/catroid/formulaeditor/Formula;)V", "restart", "", ConfigConstants.CONFIG_KEY_UPDATE, "percent", "", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class NativeViewListenerAction extends TemporalAction {
    private int eventSelection;
    private Scope scope;
    private UserVariable userVariable;
    private Formula viewId;

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

    public final UserVariable getUserVariable() {
        return this.userVariable;
    }

    public final void setUserVariable(UserVariable userVariable) {
        this.userVariable = userVariable;
    }

    public final int getEventSelection() {
        return this.eventSelection;
    }

    public final void setEventSelection(int i) {
        this.eventSelection = i;
    }

    @Override // com.badlogic.gdx.scenes.scene2d.actions.TemporalAction
    protected void update(float percent) {
        final StageActivity stageActivity;
        final UserVariable userVariable;
        final View viewFromStage;
        WeakReference<StageActivity> weakReference = StageActivity.activeStageActivity;
        if (weakReference == null || (stageActivity = weakReference.get()) == null) {
            return;
        }
        Formula formula = this.viewId;
        String strInterpretString = formula != null ? formula.interpretString(this.scope) : null;
        if (strInterpretString == null || (userVariable = this.userVariable) == null || (viewFromStage = stageActivity.getViewFromStage(strInterpretString)) == null) {
            return;
        }
        stageActivity.runOnUiThread(new Runnable() { // from class: org.catrobat.catroid.content.actions.NativeViewListenerAction$$ExternalSyntheticLambda3
            @Override // java.lang.Runnable
            public final void run() {
                NativeViewListenerAction.update$lambda$12(this.f$0, viewFromStage, stageActivity, userVariable);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void update$lambda$12(final NativeViewListenerAction this$0, final View view, StageActivity stage, final UserVariable variable) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNullParameter(view, "$view");
        Intrinsics.checkNotNullParameter(stage, "$stage");
        Intrinsics.checkNotNullParameter(variable, "$variable");
        try {
            final boolean z = true;
            final boolean z2 = false;
            switch (this$0.eventSelection) {
                case 0:
                    if (view instanceof WebView) {
                        ((WebView) view).addJavascriptInterface(new NativeViewListenerAction$update$1$1(stage, variable), "Android");
                    }
                    break;
                case 1:
                case 2:
                case 3:
                    if (view instanceof WebView) {
                        ((WebView) view).setWebViewClient(new WebViewClient() { // from class: org.catrobat.catroid.content.actions.NativeViewListenerAction$update$1$2
                            @Override // android.webkit.WebViewClient
                            public void onPageStarted(WebView v, String url, Bitmap favicon) {
                                if (this.this$0.getEventSelection() == 1) {
                                    UserVariable userVariable = variable;
                                    if (url == null) {
                                        url = "";
                                    }
                                    userVariable.setValue(url);
                                }
                            }

                            @Override // android.webkit.WebViewClient
                            public void onPageFinished(WebView v, String url) {
                                if (this.this$0.getEventSelection() == 2) {
                                    UserVariable userVariable = variable;
                                    if (url == null) {
                                        url = "";
                                    }
                                    userVariable.setValue(url);
                                }
                            }

                            @Override // android.webkit.WebViewClient
                            public void onReceivedError(WebView v, WebResourceRequest req, WebResourceError err) {
                                String string;
                                CharSequence description;
                                if (this.this$0.getEventSelection() == 3) {
                                    UserVariable userVariable = variable;
                                    if (err == null || (description = err.getDescription()) == null || (string = description.toString()) == null) {
                                        string = "Error";
                                    }
                                    userVariable.setValue(string);
                                }
                            }
                        });
                    }
                    break;
                case 4:
                case 5:
                    if (view instanceof WebView) {
                        ((WebView) view).setWebChromeClient(new WebChromeClient() { // from class: org.catrobat.catroid.content.actions.NativeViewListenerAction$update$1$3
                            @Override // android.webkit.WebChromeClient
                            public void onProgressChanged(WebView v, int newProgress) {
                                if (this.this$0.getEventSelection() == 4) {
                                    variable.setValue(Double.valueOf(newProgress));
                                }
                            }

                            @Override // android.webkit.WebChromeClient
                            public void onReceivedTitle(WebView v, String title) {
                                if (this.this$0.getEventSelection() == 5) {
                                    UserVariable userVariable = variable;
                                    if (title == null) {
                                        title = "";
                                    }
                                    userVariable.setValue(title);
                                }
                            }
                        });
                    }
                    break;
                case 6:
                    if (view instanceof TextView) {
                        ((TextView) view).addTextChangedListener(new TextWatcher() { // from class: org.catrobat.catroid.content.actions.NativeViewListenerAction$update$1$4
                            @Override // android.text.TextWatcher
                            public void afterTextChanged(Editable s) {
                            }

                            @Override // android.text.TextWatcher
                            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                            }

                            @Override // android.text.TextWatcher
                            public void onTextChanged(CharSequence s, int start, int before, int count) {
                                String string;
                                UserVariable userVariable = variable;
                                if (s == null || (string = s.toString()) == null) {
                                    string = "";
                                }
                                userVariable.setValue(string);
                            }
                        });
                    }
                    break;
                case 7:
                case 8:
                    if (view instanceof TextView) {
                        view.setOnFocusChangeListener(new View.OnFocusChangeListener() { // from class: org.catrobat.catroid.content.actions.NativeViewListenerAction$$ExternalSyntheticLambda5
                            @Override // android.view.View.OnFocusChangeListener
                            public final void onFocusChange(View view2, boolean z3) {
                                NativeViewListenerAction.update$lambda$12$lambda$0(this.f$0, variable, z, z2, view2, z3);
                            }
                        });
                    }
                    break;
                case 9:
                    if (view instanceof TextView) {
                        ((TextView) view).setOnEditorActionListener(new TextView.OnEditorActionListener() { // from class: org.catrobat.catroid.content.actions.NativeViewListenerAction$$ExternalSyntheticLambda6
                            @Override // android.widget.TextView.OnEditorActionListener
                            public final boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                                return NativeViewListenerAction.update$lambda$12$lambda$1(variable, view, textView, i, keyEvent);
                            }
                        });
                    }
                    break;
                case 10:
                    if (view instanceof VideoView) {
                        ((VideoView) view).setOnCompletionListener(new MediaPlayer.OnCompletionListener() { // from class: org.catrobat.catroid.content.actions.NativeViewListenerAction$$ExternalSyntheticLambda7
                            @Override // android.media.MediaPlayer.OnCompletionListener
                            public final void onCompletion(MediaPlayer mediaPlayer) {
                                NativeViewListenerAction.update$lambda$12$lambda$2(variable, z, mediaPlayer);
                            }
                        });
                    }
                    break;
                case 11:
                    if (view instanceof VideoView) {
                        ((VideoView) view).setOnPreparedListener(new MediaPlayer.OnPreparedListener() { // from class: org.catrobat.catroid.content.actions.NativeViewListenerAction$$ExternalSyntheticLambda8
                            @Override // android.media.MediaPlayer.OnPreparedListener
                            public final void onPrepared(MediaPlayer mediaPlayer) {
                                NativeViewListenerAction.update$lambda$12$lambda$3(variable, z, mediaPlayer);
                            }
                        });
                    }
                    break;
                case 12:
                    if (view instanceof VideoView) {
                        ((VideoView) view).setOnErrorListener(new MediaPlayer.OnErrorListener() { // from class: org.catrobat.catroid.content.actions.NativeViewListenerAction$$ExternalSyntheticLambda9
                            @Override // android.media.MediaPlayer.OnErrorListener
                            public final boolean onError(MediaPlayer mediaPlayer, int i, int i2) {
                                return NativeViewListenerAction.update$lambda$12$lambda$4(variable, mediaPlayer, i, i2);
                            }
                        });
                    }
                    break;
                case 13:
                case 14:
                case 15:
                    if ((view instanceof ScrollView) || (view instanceof HorizontalScrollView)) {
                        view.setOnScrollChangeListener(new View.OnScrollChangeListener() { // from class: org.catrobat.catroid.content.actions.NativeViewListenerAction$$ExternalSyntheticLambda10
                            @Override // android.view.View.OnScrollChangeListener
                            public final void onScrollChange(View view2, int i, int i2, int i3, int i4) {
                                NativeViewListenerAction.update$lambda$12$lambda$5(this.f$0, variable, view, z, view2, i, i2, i3, i4);
                            }
                        });
                    }
                    break;
                case 16:
                    view.setOnClickListener(new View.OnClickListener() { // from class: org.catrobat.catroid.content.actions.NativeViewListenerAction$$ExternalSyntheticLambda11
                        @Override // android.view.View.OnClickListener
                        public final void onClick(View view2) {
                            NativeViewListenerAction.update$lambda$12$lambda$6(variable, z, view2);
                        }
                    });
                    break;
                case 17:
                    if (view instanceof SeekBar) {
                        ((SeekBar) view).setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() { // from class: org.catrobat.catroid.content.actions.NativeViewListenerAction$update$1$12
                            @Override // android.widget.SeekBar.OnSeekBarChangeListener
                            public void onStartTrackingTouch(SeekBar sb) {
                            }

                            @Override // android.widget.SeekBar.OnSeekBarChangeListener
                            public void onStopTrackingTouch(SeekBar sb) {
                            }

                            @Override // android.widget.SeekBar.OnSeekBarChangeListener
                            public void onProgressChanged(SeekBar sb, int progress, boolean fromUser) {
                                Object tag = sb != null ? sb.getTag() : null;
                                Pair pair = tag instanceof Pair ? (Pair) tag : null;
                                if (pair == null) {
                                    pair = new Pair(Float.valueOf(0.0f), Float.valueOf(100.0f));
                                }
                                float fFloatValue = ((Number) pair.getFirst()).floatValue();
                                variable.setValue(Double.valueOf(fFloatValue + ((progress / 1000.0f) * (((Number) pair.getSecond()).floatValue() - fFloatValue))));
                            }
                        });
                    }
                    break;
                case 18:
                    if (view instanceof SwitchCompat) {
                        ((SwitchCompat) view).setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() { // from class: org.catrobat.catroid.content.actions.NativeViewListenerAction$$ExternalSyntheticLambda12
                            @Override // android.widget.CompoundButton.OnCheckedChangeListener
                            public final void onCheckedChanged(CompoundButton compoundButton, boolean z3) {
                                NativeViewListenerAction.update$lambda$12$lambda$7(variable, z, z2, compoundButton, z3);
                            }
                        });
                    }
                    break;
                case 19:
                    if (view instanceof EditText) {
                        final Function0<Unit> function0 = new Function0<Unit>() { // from class: org.catrobat.catroid.content.actions.NativeViewListenerAction$update$1$updateCursor$1
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
                                variable.setValue(Double.valueOf(((EditText) view).getSelectionStart()));
                            }
                        };
                        function0.invoke();
                        view.setOnTouchListener(new View.OnTouchListener() { // from class: org.catrobat.catroid.content.actions.NativeViewListenerAction$$ExternalSyntheticLambda1
                            @Override // android.view.View.OnTouchListener
                            public final boolean onTouch(View view2, MotionEvent motionEvent) {
                                return NativeViewListenerAction.update$lambda$12$lambda$9(view, function0, view2, motionEvent);
                            }
                        });
                        view.setOnKeyListener(new View.OnKeyListener() { // from class: org.catrobat.catroid.content.actions.NativeViewListenerAction$$ExternalSyntheticLambda2
                            @Override // android.view.View.OnKeyListener
                            public final boolean onKey(View view2, int i, KeyEvent keyEvent) {
                                return NativeViewListenerAction.update$lambda$12$lambda$11(view, function0, view2, i, keyEvent);
                            }
                        });
                        ((EditText) view).addTextChangedListener(new NativeViewListenerAction$update$1$16(view, function0));
                    }
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void update$lambda$12$lambda$0(NativeViewListenerAction this$0, UserVariable variable, boolean z, boolean z2, View view, boolean z3) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNullParameter(variable, "$variable");
        int i = this$0.eventSelection;
        if (i == 7 && z3) {
            variable.setValue(Boolean.valueOf(z));
        } else {
            if (i != 8 || z3) {
                return;
            }
            variable.setValue(Boolean.valueOf(z2));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final boolean update$lambda$12$lambda$1(UserVariable variable, View view, TextView textView, int i, KeyEvent keyEvent) {
        Intrinsics.checkNotNullParameter(variable, "$variable");
        Intrinsics.checkNotNullParameter(view, "$view");
        variable.setValue(((TextView) view).getText().toString());
        return false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void update$lambda$12$lambda$2(UserVariable variable, boolean z, MediaPlayer mediaPlayer) {
        Intrinsics.checkNotNullParameter(variable, "$variable");
        variable.setValue(Boolean.valueOf(z));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void update$lambda$12$lambda$3(UserVariable variable, boolean z, MediaPlayer mediaPlayer) {
        Intrinsics.checkNotNullParameter(variable, "$variable");
        variable.setValue(Boolean.valueOf(z));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final boolean update$lambda$12$lambda$4(UserVariable variable, MediaPlayer mediaPlayer, int i, int i2) {
        Intrinsics.checkNotNullParameter(variable, "$variable");
        variable.setValue("Error " + i + " (" + i2 + Constant.AFTER_QUTO);
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void update$lambda$12$lambda$5(NativeViewListenerAction this$0, UserVariable variable, View view, boolean z, View view2, int i, int i2, int i3, int i4) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNullParameter(variable, "$variable");
        Intrinsics.checkNotNullParameter(view, "$view");
        int i5 = this$0.eventSelection;
        if (i5 == 13) {
            variable.setValue(Double.valueOf(i2));
            return;
        }
        if (i5 == 14) {
            variable.setValue(Double.valueOf(i));
            return;
        }
        if (i5 == 15 && (view instanceof ScrollView)) {
            ScrollView scrollView = (ScrollView) view;
            if (scrollView.getChildAt(0).getBottom() - (scrollView.getHeight() + scrollView.getScrollY()) <= 0) {
                variable.setValue(Boolean.valueOf(z));
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void update$lambda$12$lambda$6(UserVariable variable, boolean z, View view) {
        Intrinsics.checkNotNullParameter(variable, "$variable");
        variable.setValue(Boolean.valueOf(z));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void update$lambda$12$lambda$7(UserVariable variable, boolean z, boolean z2, CompoundButton compoundButton, boolean z3) {
        Intrinsics.checkNotNullParameter(variable, "$variable");
        if (!z3) {
            z = z2;
        }
        variable.setValue(Boolean.valueOf(z));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final boolean update$lambda$12$lambda$9(View view, final Function0 updateCursor, View view2, MotionEvent motionEvent) {
        Intrinsics.checkNotNullParameter(view, "$view");
        Intrinsics.checkNotNullParameter(updateCursor, "$updateCursor");
        view.post(new Runnable() { // from class: org.catrobat.catroid.content.actions.NativeViewListenerAction$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                NativeViewListenerAction.update$lambda$12$lambda$9$lambda$8(updateCursor);
            }
        });
        return false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void update$lambda$12$lambda$9$lambda$8(Function0 tmp0) {
        Intrinsics.checkNotNullParameter(tmp0, "$tmp0");
        tmp0.invoke();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final boolean update$lambda$12$lambda$11(View view, final Function0 updateCursor, View view2, int i, KeyEvent keyEvent) {
        Intrinsics.checkNotNullParameter(view, "$view");
        Intrinsics.checkNotNullParameter(updateCursor, "$updateCursor");
        view.post(new Runnable() { // from class: org.catrobat.catroid.content.actions.NativeViewListenerAction$$ExternalSyntheticLambda4
            @Override // java.lang.Runnable
            public final void run() {
                NativeViewListenerAction.update$lambda$12$lambda$11$lambda$10(updateCursor);
            }
        });
        return false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void update$lambda$12$lambda$11$lambda$10(Function0 tmp0) {
        Intrinsics.checkNotNullParameter(tmp0, "$tmp0");
        tmp0.invoke();
    }

    @Override // com.badlogic.gdx.scenes.scene2d.actions.TemporalAction, com.badlogic.gdx.scenes.scene2d.Action
    public void restart() {
        super.restart();
    }
}
