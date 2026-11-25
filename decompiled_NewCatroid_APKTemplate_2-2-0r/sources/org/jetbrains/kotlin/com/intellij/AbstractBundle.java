package org.jetbrains.kotlin.com.intellij;

import com.huawei.hms.framework.common.hianalytics.CrashHianalyticsData;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.ref.Reference;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;
import java.util.function.Supplier;
import org.antlr.stringtemplate.language.ASTExpr;
import org.jetbrains.kotlin.com.intellij.openapi.diagnostic.Logger;
import org.jetbrains.kotlin.com.intellij.reference.SoftReference;
import org.jetbrains.kotlin.com.intellij.util.DefaultBundleService;
import org.jetbrains.kotlin.com.intellij.util.containers.CollectionFactory;

/* JADX INFO: loaded from: classes3.dex */
public class AbstractBundle {
    private static final Logger LOG = Logger.getInstance((Class<?>) AbstractBundle.class);
    private static final Map<ClassLoader, Map<String, ResourceBundle>> ourCache = CollectionFactory.createConcurrentWeakMap();
    private static final Map<ClassLoader, Map<String, ResourceBundle>> ourDefaultCache = CollectionFactory.createConcurrentWeakMap();
    private Reference<ResourceBundle> myBundle;
    private Reference<ResourceBundle> myDefaultBundle;
    private final String myPathToBundle;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = (i == 1 || i == 4 || i == 7 || i == 10 || i == 20 || i == 26 || i == 29 || i == 32 || i == 33) ? "@NotNull method %s.%s must not return null" : "Argument for @NotNull parameter '%s' of %s.%s must not be null";
        Object[] objArr = new Object[(i == 1 || i == 4 || i == 7 || i == 10 || i == 20 || i == 26 || i == 29 || i == 32 || i == 33) ? 2 : 3];
        switch (i) {
            case 1:
            case 4:
            case 7:
            case 10:
            case 20:
            case 26:
            case 29:
            case 32:
            case 33:
                objArr[0] = "org/jetbrains/kotlin/com/intellij/AbstractBundle";
                break;
            case 2:
            case 5:
            case 8:
            case 11:
            case 13:
            case 15:
            case 18:
            case 22:
            case 24:
                objArr[0] = ASTExpr.DEFAULT_MAP_KEY_NAME;
                break;
            case 3:
            case 6:
            case 9:
            case 12:
            case 14:
            case 16:
            case 19:
            case 23:
                objArr[0] = "params";
                break;
            case 17:
            case 21:
                objArr[0] = "bundle";
                break;
            case 25:
                objArr[0] = "classLoader";
                break;
            case 27:
            case 30:
            case 34:
            default:
                objArr[0] = "pathToBundle";
                break;
            case 28:
            case 31:
            case 35:
                objArr[0] = "loader";
                break;
            case 36:
                objArr[0] = "control";
                break;
        }
        if (i == 1) {
            objArr[1] = "getControl";
        } else if (i == 4) {
            objArr[1] = "getMessage";
        } else if (i == 7) {
            objArr[1] = "getPartialMessage";
        } else if (i == 10) {
            objArr[1] = "getLazyMessage";
        } else if (i == 20) {
            objArr[1] = CrashHianalyticsData.MESSAGE;
        } else if (i == 26 || i == 29) {
            objArr[1] = "getResourceBundle";
        } else if (i == 32 || i == 33) {
            objArr[1] = "resolveResourceBundle";
        } else {
            objArr[1] = "org/jetbrains/kotlin/com/intellij/AbstractBundle";
        }
        switch (i) {
            case 1:
            case 4:
            case 7:
            case 10:
            case 20:
            case 26:
            case 29:
            case 32:
            case 33:
                break;
            case 2:
            case 3:
                objArr[2] = "getMessage";
                break;
            case 5:
            case 6:
                objArr[2] = "getPartialMessage";
                break;
            case 8:
            case 9:
                objArr[2] = "getLazyMessage";
                break;
            case 11:
            case 12:
            case 21:
            case 22:
            case 23:
                objArr[2] = "messageOrNull";
                break;
            case 13:
            case 14:
            case 15:
            case 16:
                objArr[2] = "messageOrDefault";
                break;
            case 17:
            case 18:
            case 19:
                objArr[2] = CrashHianalyticsData.MESSAGE;
                break;
            case 24:
                objArr[2] = "containsKey";
                break;
            case 25:
            case 27:
            case 28:
                objArr[2] = "getResourceBundle";
                break;
            case 30:
            case 31:
                objArr[2] = "resolveResourceBundle";
                break;
            case 34:
            case 35:
            case 36:
                objArr[2] = "findBundle";
                break;
            default:
                objArr[2] = "<init>";
                break;
        }
        String str2 = String.format(str, objArr);
        if (i != 1 && i != 4 && i != 7 && i != 10 && i != 20 && i != 26 && i != 29 && i != 32 && i != 33) {
            throw new IllegalArgumentException(str2);
        }
        throw new IllegalStateException(str2);
    }

    public AbstractBundle(String str) {
        if (str == null) {
            $$$reportNull$$$0(0);
        }
        this.myPathToBundle = str;
    }

    /* JADX INFO: renamed from: getMessage, reason: merged with bridge method [inline-methods] */
    public String m6457xe250ffe0(String str, Object... objArr) {
        if (str == null) {
            $$$reportNull$$$0(2);
        }
        if (objArr == null) {
            $$$reportNull$$$0(3);
        }
        String strMessageOrDefault = BundleBase.messageOrDefault(getResourceBundle(getClass().getClassLoader()), str, null, objArr);
        if (strMessageOrDefault == null) {
            $$$reportNull$$$0(4);
        }
        return strMessageOrDefault;
    }

    public Supplier<String> getLazyMessage(final String str, final Object... objArr) {
        if (str == null) {
            $$$reportNull$$$0(8);
        }
        if (objArr == null) {
            $$$reportNull$$$0(9);
        }
        return new Supplier() { // from class: org.jetbrains.kotlin.com.intellij.AbstractBundle$$ExternalSyntheticLambda0
            @Override // java.util.function.Supplier
            public final Object get() {
                return this.f$0.m6457xe250ffe0(str, objArr);
            }
        };
    }

    public final ResourceBundle getResourceBundle(ClassLoader classLoader) {
        if (classLoader == null) {
            $$$reportNull$$$0(25);
        }
        boolean zIsDefaultBundle = DefaultBundleService.isDefaultBundle();
        ResourceBundle resourceBundleResolveResourceBundle = (ResourceBundle) SoftReference.dereference(zIsDefaultBundle ? this.myDefaultBundle : this.myBundle);
        if (resourceBundleResolveResourceBundle == null) {
            resourceBundleResolveResourceBundle = resolveResourceBundle(this.myPathToBundle, classLoader);
            java.lang.ref.SoftReference softReference = new java.lang.ref.SoftReference(resourceBundleResolveResourceBundle);
            if (zIsDefaultBundle) {
                this.myDefaultBundle = softReference;
            } else {
                this.myBundle = softReference;
            }
        }
        if (resourceBundleResolveResourceBundle == null) {
            $$$reportNull$$$0(26);
        }
        return resourceBundleResolveResourceBundle;
    }

    private ResourceBundle resolveResourceBundle(String str, ClassLoader classLoader) {
        if (str == null) {
            $$$reportNull$$$0(30);
        }
        if (classLoader == null) {
            $$$reportNull$$$0(31);
        }
        try {
            ResourceBundle resourceBundleFindBundle = findBundle(str, classLoader, MyResourceControl.INSTANCE);
            if (resourceBundleFindBundle == null) {
                $$$reportNull$$$0(32);
            }
            return resourceBundleFindBundle;
        } catch (MissingResourceException unused) {
            LOG.info("Cannot load resource bundle from *.properties file, falling back to slow class loading: " + str);
            ResourceBundle.clearCache(classLoader);
            ResourceBundle bundle = ResourceBundle.getBundle(str, Locale.getDefault(), classLoader);
            if (bundle == null) {
                $$$reportNull$$$0(33);
            }
            return bundle;
        }
    }

    protected ResourceBundle findBundle(String str, ClassLoader classLoader, ResourceBundle.Control control) {
        if (str == null) {
            $$$reportNull$$$0(34);
        }
        if (classLoader == null) {
            $$$reportNull$$$0(35);
        }
        if (control == null) {
            $$$reportNull$$$0(36);
        }
        return ResourceBundle.getBundle(str, Locale.getDefault(), classLoader, control);
    }

    private static final class MyResourceControl extends ResourceBundle.Control {
        static final MyResourceControl INSTANCE = new MyResourceControl();

        private MyResourceControl() {
        }

        @Override // java.util.ResourceBundle.Control
        public List<String> getFormats(String str) {
            return FORMAT_PROPERTIES;
        }

        @Override // java.util.ResourceBundle.Control
        public ResourceBundle newBundle(String str, Locale locale, String str2, ClassLoader classLoader, boolean z) throws IOException {
            InputStream resourceAsStream;
            String bundleName = toBundleName(str, locale);
            String resourceName = bundleName.contains("://") ? null : toResourceName(bundleName, "properties");
            if (resourceName == null || (resourceAsStream = classLoader.getResourceAsStream(resourceName)) == null) {
                return null;
            }
            try {
                return new PropertyResourceBundle(new InputStreamReader(resourceAsStream, StandardCharsets.UTF_8));
            } finally {
                resourceAsStream.close();
            }
        }
    }
}
