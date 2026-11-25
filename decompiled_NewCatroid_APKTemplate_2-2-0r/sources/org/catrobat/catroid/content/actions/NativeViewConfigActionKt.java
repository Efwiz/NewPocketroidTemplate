package org.catrobat.catroid.content.actions;

import android.text.Editable;
import android.text.style.BackgroundColorSpan;
import android.text.style.ForegroundColorSpan;
import java.util.Map;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/actions/NativeViewConfigActionKt.class
  classes14.dex
 */
/* JADX INFO: compiled from: NativeViewConfigAction.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000\u0014\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u001a\u0018\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H\u0002¨\u0006\u0006"}, d2 = {"applySyntaxHighlighting", "", "editable", "Landroid/text/Editable;", "config", "Lorg/catrobat/catroid/content/actions/SyntaxConfig;", "catroid_apktemplateTemplateRelease"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class NativeViewConfigActionKt {
    /* JADX INFO: Access modifiers changed from: private */
    public static final void applySyntaxHighlighting(Editable editable, SyntaxConfig syntaxConfig) {
        ForegroundColorSpan[] foregroundColorSpanArr = (ForegroundColorSpan[]) editable.getSpans(0, editable.length(), ForegroundColorSpan.class);
        Intrinsics.checkNotNull(foregroundColorSpanArr);
        for (ForegroundColorSpan foregroundColorSpan : foregroundColorSpanArr) {
            editable.removeSpan(foregroundColorSpan);
        }
        BackgroundColorSpan[] backgroundColorSpanArr = (BackgroundColorSpan[]) editable.getSpans(0, editable.length(), BackgroundColorSpan.class);
        Intrinsics.checkNotNull(backgroundColorSpanArr);
        for (BackgroundColorSpan backgroundColorSpan : backgroundColorSpanArr) {
            editable.removeSpan(backgroundColorSpan);
        }
        for (Map.Entry<String, Integer> entry : syntaxConfig.getForegroundWords().entrySet()) {
            String key = entry.getKey();
            int iIntValue = entry.getValue().intValue();
            Editable editable2 = editable;
            int iIndexOf$default = StringsKt.indexOf$default((CharSequence) editable2, key, 0, false, 6, (Object) null);
            while (iIndexOf$default >= 0) {
                char cCharAt = iIndexOf$default > 0 ? editable.charAt(iIndexOf$default - 1) : ' ';
                char cCharAt2 = key.length() + iIndexOf$default < editable.length() ? editable.charAt(key.length() + iIndexOf$default) : ' ';
                if (!Character.isLetterOrDigit(cCharAt) && !Character.isLetterOrDigit(cCharAt2)) {
                    editable.setSpan(new ForegroundColorSpan(iIntValue), iIndexOf$default, key.length() + iIndexOf$default, 33);
                }
                iIndexOf$default = StringsKt.indexOf$default((CharSequence) editable2, key, iIndexOf$default + key.length(), false, 4, (Object) null);
            }
        }
        for (Map.Entry<String, Integer> entry2 : syntaxConfig.getBackgroundWords().entrySet()) {
            String key2 = entry2.getKey();
            int iIntValue2 = entry2.getValue().intValue();
            Editable editable3 = editable;
            int iIndexOf$default2 = StringsKt.indexOf$default((CharSequence) editable3, key2, 0, false, 6, (Object) null);
            while (iIndexOf$default2 >= 0) {
                char cCharAt3 = iIndexOf$default2 > 0 ? editable.charAt(iIndexOf$default2 - 1) : ' ';
                char cCharAt4 = key2.length() + iIndexOf$default2 < editable.length() ? editable.charAt(key2.length() + iIndexOf$default2) : ' ';
                if (!Character.isLetterOrDigit(cCharAt3) && !Character.isLetterOrDigit(cCharAt4)) {
                    editable.setSpan(new BackgroundColorSpan(iIntValue2), iIndexOf$default2, key2.length() + iIndexOf$default2, 33);
                }
                iIndexOf$default2 = StringsKt.indexOf$default((CharSequence) editable3, key2, iIndexOf$default2 + key2.length(), false, 4, (Object) null);
            }
        }
    }
}
