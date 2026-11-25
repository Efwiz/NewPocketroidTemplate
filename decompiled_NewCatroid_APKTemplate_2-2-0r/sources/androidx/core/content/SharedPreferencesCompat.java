package androidx.core.content;

import android.content.SharedPreferences;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/core-1.8.0.aar:classes.jar:androidx/core/content/SharedPreferencesCompat.class
  NewCatroid_APKTemplate_2-2-0r.apk:assets/core.jar:androidx/core/content/SharedPreferencesCompat.class
  classes.dex
 */
/* JADX INFO: loaded from: classes.dex */
@Deprecated
public final class SharedPreferencesCompat {

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/core-1.8.0.aar:classes.jar:androidx/core/content/SharedPreferencesCompat$EditorCompat.class
      NewCatroid_APKTemplate_2-2-0r.apk:assets/core.jar:androidx/core/content/SharedPreferencesCompat$EditorCompat.class
      classes.dex
     */
    @Deprecated
    public static final class EditorCompat {
        private static EditorCompat sInstance;
        private final Helper mHelper = new Helper();

        /* JADX WARN: Classes with same name are omitted, all sources:
          NewCatroid_APKTemplate_2-2-0r.apk:assets/core-1.8.0.aar:classes.jar:androidx/core/content/SharedPreferencesCompat$EditorCompat$Helper.class
          NewCatroid_APKTemplate_2-2-0r.apk:assets/core.jar:androidx/core/content/SharedPreferencesCompat$EditorCompat$Helper.class
          classes.dex
         */
        private static class Helper {
            Helper() {
            }

            public void apply(SharedPreferences.Editor editor) {
                try {
                    editor.apply();
                } catch (AbstractMethodError unused) {
                    editor.commit();
                }
            }
        }

        private EditorCompat() {
        }

        @Deprecated
        public static EditorCompat getInstance() {
            if (sInstance == null) {
                sInstance = new EditorCompat();
            }
            return sInstance;
        }

        @Deprecated
        public void apply(SharedPreferences.Editor editor) {
            this.mHelper.apply(editor);
        }
    }

    private SharedPreferencesCompat() {
    }
}
