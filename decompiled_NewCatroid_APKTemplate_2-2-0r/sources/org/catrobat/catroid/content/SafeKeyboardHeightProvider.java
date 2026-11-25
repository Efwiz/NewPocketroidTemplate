package org.catrobat.catroid.content;

import android.util.Log;
import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.keyboardheight.AndroidXKeyboardHeightProvider;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/SafeKeyboardHeightProvider.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class SafeKeyboardHeightProvider extends AndroidXKeyboardHeightProvider {
    public SafeKeyboardHeightProvider(AndroidApplication activity) {
        super(activity);
    }

    @Override // com.badlogic.gdx.backends.android.keyboardheight.AndroidXKeyboardHeightProvider, com.badlogic.gdx.backends.android.keyboardheight.KeyboardHeightProvider
    public void close() {
        try {
            super.close();
        } catch (Throwable th) {
            Log.w("SafeKeyboardProvider", "Caught a throwable while closing KeyboardHeightProvider. This is a known issue and is being safely ignored.", th);
        }
    }
}
