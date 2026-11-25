package org.jetbrains.kotlin.com.intellij;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.reflect.Method;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import org.jetbrains.kotlin.com.intellij.diagnostic.LoadingState;
import org.jetbrains.kotlin.com.intellij.openapi.application.Application;
import org.jetbrains.kotlin.com.intellij.openapi.application.ApplicationManager;
import org.jetbrains.kotlin.com.intellij.openapi.diagnostic.Logger;
import org.jetbrains.kotlin.com.intellij.openapi.extensions.ExtensionPointName;
import org.jetbrains.kotlin.com.intellij.openapi.extensions.PluginAware;
import org.jetbrains.kotlin.com.intellij.openapi.extensions.PluginDescriptor;
import org.jetbrains.kotlin.com.intellij.openapi.progress.ProcessCanceledException;
import org.jetbrains.kotlin.com.intellij.util.DefaultBundleService;
import org.jetbrains.kotlin.com.intellij.util.containers.CollectionFactory;
import org.jetbrains.kotlin.com.intellij.util.xmlb.annotations.Attribute;

/* JADX INFO: loaded from: classes3.dex */
public class DynamicBundle extends AbstractBundle {
    private static final Logger LOG = Logger.getInstance((Class<?>) DynamicBundle.class);
    private static String ourLangTag = Locale.ENGLISH.toLanguageTag();
    private static final Map<String, DynamicBundle> ourBundlesForForms = CollectionFactory.createConcurrentSoftValueMap();
    public static final DynamicBundle INSTANCE = new DynamicBundle("") { // from class: org.jetbrains.kotlin.com.intellij.DynamicBundle.1
    };

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = i != 7 ? "Argument for @NotNull parameter '%s' of %s.%s must not be null" : "@NotNull method %s.%s must not return null";
        Object[] objArr = new Object[i != 7 ? 3 : 2];
        switch (i) {
            case 2:
                objArr[0] = "baseLoader";
                break;
            case 3:
                objArr[0] = "control";
                break;
            case 4:
            case 5:
                objArr[0] = "baseName";
                break;
            case 6:
                objArr[0] = "formClass";
                break;
            case 7:
                objArr[0] = "org/jetbrains/kotlin/com/intellij/DynamicBundle";
                break;
            default:
                objArr[0] = "pathToBundle";
                break;
        }
        if (i != 7) {
            objArr[1] = "org/jetbrains/kotlin/com/intellij/DynamicBundle";
        } else {
            objArr[1] = "getLocale";
        }
        switch (i) {
            case 1:
            case 2:
            case 3:
                objArr[2] = "findBundle";
                break;
            case 4:
            case 5:
            case 6:
                objArr[2] = "getBundle";
                break;
            case 7:
                break;
            default:
                objArr[2] = "<init>";
                break;
        }
        String str2 = String.format(str, objArr);
        if (i == 7) {
            throw new IllegalStateException(str2);
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public DynamicBundle(String str) {
        super(str);
        if (str == null) {
            $$$reportNull$$$0(0);
        }
    }

    @Override // org.jetbrains.kotlin.com.intellij.AbstractBundle
    protected ResourceBundle findBundle(String str, ClassLoader classLoader, ResourceBundle.Control control) {
        LanguageBundleEP languageBundleEPFindLanguageBundle;
        if (str == null) {
            $$$reportNull$$$0(1);
        }
        if (classLoader == null) {
            $$$reportNull$$$0(2);
        }
        if (control == null) {
            $$$reportNull$$$0(3);
        }
        ResourceBundle resourceBundleFindBundle = super.findBundle(str, classLoader, control);
        if (!DefaultBundleService.isDefaultBundle() && (languageBundleEPFindLanguageBundle = findLanguageBundle()) != null) {
            PluginDescriptor pluginDescriptor = languageBundleEPFindLanguageBundle.pluginDescriptor;
            ResourceBundle resourceBundleFindBundle2 = super.findBundle(str, pluginDescriptor == null ? getClass().getClassLoader() : pluginDescriptor.getClassLoader(), control);
            if (resourceBundleFindBundle2 != null) {
                try {
                    if (DynamicBundleInternal.SET_PARENT != null && resourceBundleFindBundle2 != resourceBundleFindBundle) {
                        (void) DynamicBundleInternal.SET_PARENT.bindTo(resourceBundleFindBundle2).invoke(resourceBundleFindBundle);
                    }
                    return resourceBundleFindBundle2;
                } catch (Throwable th) {
                    LOG.warn(th);
                }
            }
        }
        return resourceBundleFindBundle;
    }

    private static class DynamicBundleInternal {
        private static final MethodHandle SET_PARENT;

        static {
            try {
                Method declaredMethod = ResourceBundle.class.getDeclaredMethod("setParent", ResourceBundle.class);
                declaredMethod.setAccessible(true);
                SET_PARENT = MethodHandles.lookup().unreflect(declaredMethod);
            } catch (IllegalAccessException | NoSuchMethodException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public static LanguageBundleEP findLanguageBundle() {
        Application application;
        try {
            if (LoadingState.COMPONENTS_REGISTERED.isOccurred() && (application = ApplicationManager.getApplication()) != null && application.getExtensionArea().hasExtensionPoint(LanguageBundleEP.EP_NAME)) {
                return (LanguageBundleEP) LanguageBundleEP.EP_NAME.findExtension(LanguageBundleEP.class);
            }
            return null;
        } catch (ProcessCanceledException e) {
            throw e;
        } catch (Exception e2) {
            LOG.error((Throwable) e2);
            return null;
        }
    }

    public static final class LanguageBundleEP implements PluginAware {
        public static final ExtensionPointName<LanguageBundleEP> EP_NAME = new ExtensionPointName<>("org.jetbrains.kotlin.com.intellij.languageBundle");

        @Attribute("locale")
        public String locale = Locale.ENGLISH.getLanguage();
        public PluginDescriptor pluginDescriptor;

        private static /* synthetic */ void $$$reportNull$$$0(int i) {
            throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", "pluginDescriptor", "org/jetbrains/kotlin/com/intellij/DynamicBundle$LanguageBundleEP", "setPluginDescriptor"));
        }

        @Override // org.jetbrains.kotlin.com.intellij.openapi.extensions.PluginAware
        public void setPluginDescriptor(PluginDescriptor pluginDescriptor) {
            if (pluginDescriptor == null) {
                $$$reportNull$$$0(0);
            }
            this.pluginDescriptor = pluginDescriptor;
        }
    }
}
