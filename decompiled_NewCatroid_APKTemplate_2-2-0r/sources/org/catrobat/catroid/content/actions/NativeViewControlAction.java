package org.catrobat.catroid.content.actions;

import android.view.View;
import android.view.ViewParent;
import android.view.inputmethod.InputMethodManager;
import android.webkit.CookieManager;
import android.webkit.WebView;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.VideoView;
import com.badlogic.gdx.scenes.scene2d.actions.TemporalAction;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt;
import kotlin.text.StringsKt;
import org.apache.xerces.impl.xs.SchemaSymbols;
import org.catrobat.catroid.content.Scope;
import org.catrobat.catroid.formulaeditor.Formula;
import org.catrobat.catroid.stage.StageActivity;
import org.eclipse.jgit.lib.ConfigConstants;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/actions/NativeViewControlAction.class
  classes14.dex
 */
/* JADX INFO: compiled from: NativeViewControlAction.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000@\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\u0007\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\b\u0010\u0018\u001a\u00020\u0019H\u0016J\u0010\u0010\u001a\u001a\u00020\u00192\u0006\u0010\u001b\u001a\u00020\u001cH\u0014J\u0018\u0010\u001d\u001a\u00020\u001e*\u0004\u0018\u00010\n2\b\u0010\u000f\u001a\u0004\u0018\u00010\u0010H\u0002J\u001c\u0010\u001f\u001a\u00020\u0019*\u00020 2\u0006\u0010!\u001a\u00020\u00042\u0006\u0010\"\u001a\u00020\u0004H\u0002R\u001a\u0010\u0003\u001a\u00020\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\bR\u001c\u0010\t\u001a\u0004\u0018\u00010\nX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000b\u0010\f\"\u0004\b\r\u0010\u000eR\u001c\u0010\u000f\u001a\u0004\u0018\u00010\u0010X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0011\u0010\u0012\"\u0004\b\u0013\u0010\u0014R\u001c\u0010\u0015\u001a\u0004\u0018\u00010\nX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0016\u0010\f\"\u0004\b\u0017\u0010\u000e¨\u0006#"}, d2 = {"Lorg/catrobat/catroid/content/actions/NativeViewControlAction;", "Lcom/badlogic/gdx/scenes/scene2d/actions/TemporalAction;", "()V", "commandSelection", "", "getCommandSelection", "()I", "setCommandSelection", "(I)V", "parameter", "Lorg/catrobat/catroid/formulaeditor/Formula;", "getParameter", "()Lorg/catrobat/catroid/formulaeditor/Formula;", "setParameter", "(Lorg/catrobat/catroid/formulaeditor/Formula;)V", "scope", "Lorg/catrobat/catroid/content/Scope;", "getScope", "()Lorg/catrobat/catroid/content/Scope;", "setScope", "(Lorg/catrobat/catroid/content/Scope;)V", "viewId", "getViewId", "setViewId", "restart", "", ConfigConstants.CONFIG_KEY_UPDATE, "percent", "", "interpretSafeBoolean", "", "smoothScrollTo", "Landroid/view/View;", "x", "y", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class NativeViewControlAction extends TemporalAction {
    private int commandSelection;
    private Formula parameter;
    private Scope scope;
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

    public final Formula getParameter() {
        return this.parameter;
    }

    public final void setParameter(Formula formula) {
        this.parameter = formula;
    }

    public final int getCommandSelection() {
        return this.commandSelection;
    }

    public final void setCommandSelection(int i) {
        this.commandSelection = i;
    }

    /* JADX WARN: Code duplicated, block: B:23:0x004a  */
    private final boolean interpretSafeBoolean(Formula formula, Scope scope) {
        String lowerCase;
        String string;
        String string2;
        if (formula == null) {
            return false;
        }
        try {
            Object objInterpretObject = formula.interpretObject(scope);
            if (objInterpretObject instanceof Boolean) {
                return ((Boolean) objInterpretObject).booleanValue();
            }
            if (objInterpretObject instanceof Number) {
                return !(((Number) objInterpretObject).doubleValue() == 0.0d);
            }
            if (objInterpretObject == null || (string = objInterpretObject.toString()) == null || (string2 = StringsKt.trim((CharSequence) string).toString()) == null) {
                lowerCase = "";
            } else {
                lowerCase = string2.toLowerCase(Locale.ROOT);
                Intrinsics.checkNotNullExpressionValue(lowerCase, "toLowerCase(...)");
                if (lowerCase == null) {
                    lowerCase = "";
                }
            }
            return Intrinsics.areEqual(lowerCase, "true") || Intrinsics.areEqual(lowerCase, "истина") || Intrinsics.areEqual(lowerCase, "да") || Intrinsics.areEqual(lowerCase, "yes") || Intrinsics.areEqual(lowerCase, SchemaSymbols.ATTVAL_TRUE_1);
        } catch (Exception unused) {
            return false;
        }
    }

    @Override // com.badlogic.gdx.scenes.scene2d.actions.TemporalAction
    protected void update(float percent) {
        final StageActivity stageActivity;
        WeakReference<StageActivity> weakReference = StageActivity.activeStageActivity;
        if (weakReference == null || (stageActivity = weakReference.get()) == null) {
            return;
        }
        Formula formula = this.viewId;
        String strInterpretString = formula != null ? formula.interpretString(this.scope) : null;
        if (strInterpretString == null) {
            return;
        }
        Formula formula2 = this.parameter;
        final String strInterpretString2 = formula2 != null ? formula2.interpretString(this.scope) : null;
        if (strInterpretString2 == null) {
            strInterpretString2 = "";
        }
        final View viewFromStage = stageActivity.getViewFromStage(strInterpretString);
        if (viewFromStage == null) {
            return;
        }
        stageActivity.runOnUiThread(new Runnable() { // from class: org.catrobat.catroid.content.actions.NativeViewControlAction$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                NativeViewControlAction.update$lambda$2(this.f$0, viewFromStage, strInterpretString2, stageActivity);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void update$lambda$2(NativeViewControlAction this$0, View view, String paramStr, StageActivity stage) {
        int iIntValue;
        View view2 = view;
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNullParameter(view2, "$view");
        Intrinsics.checkNotNullParameter(paramStr, "$paramStr");
        Intrinsics.checkNotNullParameter(stage, "$stage");
        try {
            switch (this$0.commandSelection) {
                case 0:
                    if ((view2 instanceof WebView) && ((WebView) view2).canGoBack()) {
                        ((WebView) view2).goBack();
                        break;
                    }
                    break;
                case 1:
                    if ((view2 instanceof WebView) && ((WebView) view2).canGoForward()) {
                        ((WebView) view2).goForward();
                        break;
                    }
                    break;
                case 2:
                    if (view2 instanceof WebView) {
                        ((WebView) view2).reload();
                    }
                    break;
                case 3:
                    if (view2 instanceof WebView) {
                        ((WebView) view2).stopLoading();
                    }
                    break;
                case 4:
                    if (view2 instanceof WebView) {
                        ((WebView) view2).clearCache(true);
                        CookieManager.getInstance().removeAllCookies(null);
                    }
                    break;
                case 5:
                    if (view2 instanceof WebView) {
                        ((WebView) view2).loadUrl(paramStr);
                    }
                    break;
                case 6:
                    if (view2 instanceof WebView) {
                        ((WebView) view2).loadDataWithBaseURL("https://", paramStr, "text/html", "UTF-8", null);
                    }
                    break;
                case 7:
                    if (view2 instanceof VideoView) {
                        ((VideoView) view2).start();
                    }
                    break;
                case 8:
                    if ((view2 instanceof VideoView) && ((VideoView) view2).isPlaying()) {
                        ((VideoView) view2).pause();
                        break;
                    }
                    break;
                case 9:
                    if (view2 instanceof VideoView) {
                        ((VideoView) view2).stopPlayback();
                    }
                    break;
                case 10:
                    if (view2 instanceof VideoView) {
                        Double doubleOrNull = StringsKt.toDoubleOrNull(paramStr);
                        ((VideoView) view2).seekTo((int) ((doubleOrNull != null ? doubleOrNull.doubleValue() : 0.0d) * ((double) 1000)));
                    }
                    break;
                case 11:
                    if (view2 instanceof TextView) {
                        ((TextView) view2).setText(paramStr);
                    }
                    break;
                case 12:
                    if (view2 instanceof TextView) {
                        ((TextView) view2).append(paramStr);
                    }
                    break;
                case 13:
                    if (view2 instanceof TextView) {
                        ((TextView) view2).setText("");
                    }
                    break;
                case 14:
                    if (view2 instanceof EditText) {
                        ((EditText) view2).selectAll();
                    }
                    break;
                case 15:
                    if (view2 instanceof EditText) {
                        Integer intOrNull = StringsKt.toIntOrNull(paramStr);
                        ((EditText) view2).setSelection(RangesKt.coerceAtLeast(0, RangesKt.coerceAtMost(((EditText) view2).getText().length(), intOrNull != null ? intOrNull.intValue() : 0)));
                    }
                    break;
                case 16:
                    List listSplit$default = StringsKt.split$default((CharSequence) paramStr, new String[]{","}, false, 0, 6, (Object) null);
                    ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(listSplit$default, 10));
                    Iterator it = listSplit$default.iterator();
                    while (it.hasNext()) {
                        Integer intOrNull2 = StringsKt.toIntOrNull(StringsKt.trim((CharSequence) it.next()).toString());
                        arrayList.add(Integer.valueOf(intOrNull2 != null ? intOrNull2.intValue() : 0));
                    }
                    ArrayList arrayList2 = arrayList;
                    Integer num = (Integer) CollectionsKt.getOrNull(arrayList2, 0);
                    int iIntValue2 = num != null ? num.intValue() : 0;
                    Integer num2 = (Integer) CollectionsKt.getOrNull(arrayList2, 1);
                    iIntValue = num2 != null ? num2.intValue() : 0;
                    View viewFindViewWithTag = view2.findViewWithTag("scroll_content");
                    Object parent = viewFindViewWithTag != null ? viewFindViewWithTag.getParent() : null;
                    View view3 = parent instanceof View ? (View) parent : null;
                    if (view3 != null) {
                        view2 = view3;
                    }
                    ScrollView scrollView = view2 instanceof ScrollView ? (ScrollView) view2 : null;
                    if (scrollView != null) {
                        scrollView.smoothScrollTo(iIntValue2, iIntValue);
                    }
                    break;
                case 17:
                    List listSplit$default2 = StringsKt.split$default((CharSequence) paramStr, new String[]{","}, false, 0, 6, (Object) null);
                    ArrayList arrayList3 = new ArrayList(CollectionsKt.collectionSizeOrDefault(listSplit$default2, 10));
                    Iterator it2 = listSplit$default2.iterator();
                    while (it2.hasNext()) {
                        Integer intOrNull3 = StringsKt.toIntOrNull(StringsKt.trim((CharSequence) it2.next()).toString());
                        arrayList3.add(Integer.valueOf(intOrNull3 != null ? intOrNull3.intValue() : 0));
                    }
                    ArrayList arrayList4 = arrayList3;
                    Integer num3 = (Integer) CollectionsKt.getOrNull(arrayList4, 0);
                    int iIntValue3 = num3 != null ? num3.intValue() : 0;
                    Integer num4 = (Integer) CollectionsKt.getOrNull(arrayList4, 1);
                    iIntValue = num4 != null ? num4.intValue() : 0;
                    View viewFindViewWithTag2 = view2.findViewWithTag("scroll_content");
                    Object parent2 = viewFindViewWithTag2 != null ? viewFindViewWithTag2.getParent() : null;
                    View view4 = parent2 instanceof View ? (View) parent2 : null;
                    if (view4 != null) {
                        view2 = view4;
                    }
                    ScrollView scrollView2 = view2 instanceof ScrollView ? (ScrollView) view2 : null;
                    if (scrollView2 != null) {
                        scrollView2.scrollBy(iIntValue3, iIntValue);
                    }
                    break;
                case 18:
                    View viewFindViewWithTag3 = view2.findViewWithTag("scroll_content");
                    ViewParent parent3 = viewFindViewWithTag3 != null ? viewFindViewWithTag3.getParent() : null;
                    if (parent3 instanceof ScrollView) {
                        ((ScrollView) parent3).fullScroll(33);
                    }
                    break;
                case 19:
                    View viewFindViewWithTag4 = view2.findViewWithTag("scroll_content");
                    ViewParent parent4 = viewFindViewWithTag4 != null ? viewFindViewWithTag4.getParent() : null;
                    if (parent4 instanceof ScrollView) {
                        ((ScrollView) parent4).fullScroll(130);
                    }
                    break;
                case 20:
                    view.requestFocus();
                    Object systemService = stage.getSystemService("input_method");
                    InputMethodManager inputMethodManager = systemService instanceof InputMethodManager ? (InputMethodManager) systemService : null;
                    if (inputMethodManager != null) {
                        inputMethodManager.showSoftInput(view2, 1);
                    }
                    break;
                case 21:
                    view.clearFocus();
                    Object systemService2 = stage.getSystemService("input_method");
                    InputMethodManager inputMethodManager2 = systemService2 instanceof InputMethodManager ? (InputMethodManager) systemService2 : null;
                    if (inputMethodManager2 != null) {
                        inputMethodManager2.hideSoftInputFromWindow(view.getWindowToken(), 0);
                    }
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private final void smoothScrollTo(View view, int i, int i2) {
        if (view instanceof ScrollView) {
            ((ScrollView) view).smoothScrollTo(i, i2);
        } else if (view instanceof HorizontalScrollView) {
            ((HorizontalScrollView) view).smoothScrollTo(i, i2);
        }
    }

    @Override // com.badlogic.gdx.scenes.scene2d.actions.TemporalAction, com.badlogic.gdx.scenes.scene2d.Action
    public void restart() {
        super.restart();
    }
}
