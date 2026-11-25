package androidx.core.os;

import android.content.res.Configuration;
import android.os.LocaleList;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/core-1.8.0.aar:classes.jar:androidx/core/os/ConfigurationCompat.class
  NewCatroid_APKTemplate_2-2-0r.apk:assets/core.jar:androidx/core/os/ConfigurationCompat.class
  classes.dex
 */
/* JADX INFO: loaded from: classes.dex */
public final class ConfigurationCompat {
    private ConfigurationCompat() {
    }

    public static LocaleListCompat getLocales(Configuration configuration) {
        return LocaleListCompat.wrap(Api24Impl.getLocales(configuration));
    }

    public static void setLocales(Configuration configuration, LocaleListCompat localeListCompat) {
        Api24Impl.setLocales(configuration, localeListCompat);
    }

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/core-1.8.0.aar:classes.jar:androidx/core/os/ConfigurationCompat$Api24Impl.class
      classes.dex
     */
    static class Api24Impl {
        private Api24Impl() {
        }

        static LocaleList getLocales(Configuration configuration) {
            return configuration.getLocales();
        }

        static void setLocales(Configuration configuration, LocaleListCompat localeListCompat) {
            configuration.setLocales((LocaleList) localeListCompat.unwrap());
        }
    }
}
