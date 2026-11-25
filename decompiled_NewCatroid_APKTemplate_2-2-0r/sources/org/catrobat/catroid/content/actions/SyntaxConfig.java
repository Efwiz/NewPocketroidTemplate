package org.catrobat.catroid.content.actions;

import android.text.TextWatcher;
import java.util.LinkedHashMap;
import java.util.Map;
import kotlin.Metadata;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/actions/SyntaxConfig.class
  classes14.dex
 */
/* JADX INFO: compiled from: NativeViewConfigAction.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010%\n\u0002\u0010\u000e\n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\b\u0002\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002R\u001d\u0010\u0003\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00060\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u001d\u0010\t\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00060\u0004¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\bR\u001c\u0010\u000b\u001a\u0004\u0018\u00010\fX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\r\u0010\u000e\"\u0004\b\u000f\u0010\u0010¨\u0006\u0011"}, d2 = {"Lorg/catrobat/catroid/content/actions/SyntaxConfig;", "", "()V", "backgroundWords", "", "", "", "getBackgroundWords", "()Ljava/util/Map;", "foregroundWords", "getForegroundWords", "textWatcher", "Landroid/text/TextWatcher;", "getTextWatcher", "()Landroid/text/TextWatcher;", "setTextWatcher", "(Landroid/text/TextWatcher;)V", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
final class SyntaxConfig {
    private TextWatcher textWatcher;
    private final Map<String, Integer> foregroundWords = new LinkedHashMap();
    private final Map<String, Integer> backgroundWords = new LinkedHashMap();

    public final Map<String, Integer> getForegroundWords() {
        return this.foregroundWords;
    }

    public final Map<String, Integer> getBackgroundWords() {
        return this.backgroundWords;
    }

    public final TextWatcher getTextWatcher() {
        return this.textWatcher;
    }

    public final void setTextWatcher(TextWatcher textWatcher) {
        this.textWatcher = textWatcher;
    }
}
