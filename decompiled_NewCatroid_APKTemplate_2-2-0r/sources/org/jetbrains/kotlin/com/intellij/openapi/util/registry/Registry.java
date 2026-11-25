package org.jetbrains.kotlin.com.intellij.openapi.util.registry;

import com.huawei.hms.ml.language.common.utils.LanguageCodeUtil;
import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;
import java.lang.ref.Reference;
import java.lang.ref.SoftReference;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import org.antlr.stringtemplate.language.ASTExpr;
import org.jetbrains.kotlin.com.intellij.diagnostic.LoadingState;

/* JADX INFO: loaded from: classes3.dex */
public final class Registry {
    private static Reference<Map<String, String>> bundledRegistry;
    private static final Registry ourInstance = new Registry();
    private volatile boolean isLoaded;
    private final Map<String, String> myUserProperties = new LinkedHashMap();
    private final Map<String, RegistryValue> myValues = new ConcurrentHashMap();
    private Map<String, RegistryKeyDescriptor> myContributedKeys = Collections.emptyMap();

    /* JADX WARN: Code duplicated, block: B:22:0x003a  */
    /* JADX WARN: Code duplicated, block: B:7:0x000d  */
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str;
        int i2;
        if (i != 3 && i != 11) {
            switch (i) {
                case 15:
                case 16:
                case 17:
                case 18:
                case 19:
                case 20:
                    str = "@NotNull method %s.%s must not return null";
                    break;
                default:
                    str = "Argument for @NotNull parameter '%s' of %s.%s must not be null";
                    break;
            }
        } else {
            str = "@NotNull method %s.%s must not return null";
        }
        if (i != 3 && i != 11) {
            switch (i) {
                case 15:
                case 16:
                case 17:
                case 18:
                case 19:
                case 20:
                    i2 = 2;
                    break;
                default:
                    i2 = 3;
                    break;
            }
        } else {
            i2 = 2;
        }
        Object[] objArr = new Object[i2];
        if (i != 3 && i != 11) {
            switch (i) {
                case 15:
                case 16:
                case 17:
                case 18:
                case 19:
                case 20:
                    objArr[0] = "org/jetbrains/kotlin/com/intellij/openapi/util/registry/Registry";
                    break;
                case 21:
                    objArr[0] = "map";
                    break;
                case 22:
                    objArr[0] = "descriptors";
                    break;
                case 23:
                    objArr[0] = "mutator";
                    break;
                default:
                    objArr[0] = ASTExpr.DEFAULT_MAP_KEY_NAME;
                    break;
            }
        } else {
            objArr[0] = "org/jetbrains/kotlin/com/intellij/openapi/util/registry/Registry";
        }
        if (i == 3) {
            objArr[1] = "doGet";
        } else if (i != 11) {
            switch (i) {
                case 15:
                case 16:
                    objArr[1] = "getBundleValue";
                    break;
                case 17:
                    objArr[1] = "getInstance";
                    break;
                case 18:
                    objArr[1] = "getState";
                    break;
                case 19:
                    objArr[1] = "getUserProperties";
                    break;
                case 20:
                    objArr[1] = "getAll";
                    break;
                default:
                    objArr[1] = "org/jetbrains/kotlin/com/intellij/openapi/util/registry/Registry";
                    break;
            }
        } else {
            objArr[1] = "stringValue";
        }
        switch (i) {
            case 1:
                objArr[2] = "_getWithoutStateCheck";
                break;
            case 2:
                objArr[2] = "doGet";
                break;
            case 3:
            case 11:
            case 15:
            case 16:
            case 17:
            case 18:
            case 19:
            case 20:
                break;
            case 4:
            case 5:
                objArr[2] = LanguageCodeUtil.IS;
                break;
            case 6:
            case 7:
            case 8:
                objArr[2] = "intValue";
                break;
            case 9:
                objArr[2] = "doubleValue";
                break;
            case 10:
                objArr[2] = "stringValue";
                break;
            case 12:
                objArr[2] = "getColor";
                break;
            case 13:
                objArr[2] = "getBundleValueOrNull";
                break;
            case 14:
                objArr[2] = "getBundleValue";
                break;
            case 21:
                objArr[2] = "isRestartNeeded";
                break;
            case 22:
                objArr[2] = "setKeys";
                break;
            case 23:
                objArr[2] = "mutateContributedKeys";
                break;
            default:
                objArr[2] = "get";
                break;
        }
        String str2 = String.format(str, objArr);
        if (i != 3 && i != 11) {
            switch (i) {
                case 15:
                case 16:
                case 17:
                case 18:
                case 19:
                case 20:
                    break;
                default:
                    throw new IllegalArgumentException(str2);
            }
        }
        throw new IllegalStateException(str2);
    }

    public static RegistryValue get(String str) {
        if (str == null) {
            $$$reportNull$$$0(0);
        }
        return getInstance().doGet(str);
    }

    private RegistryValue doGet(String str) {
        if (str == null) {
            $$$reportNull$$$0(2);
        }
        RegistryValue registryValueComputeIfAbsent = this.myValues.computeIfAbsent(str, new Function() { // from class: org.jetbrains.kotlin.com.intellij.openapi.util.registry.Registry$$ExternalSyntheticLambda0
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return this.f$0.m6505x27c4d547((String) obj);
            }
        });
        if (registryValueComputeIfAbsent == null) {
            $$$reportNull$$$0(3);
        }
        return registryValueComputeIfAbsent;
    }

    /* JADX INFO: renamed from: lambda$doGet$0$org-jetbrains-kotlin-com-intellij-openapi-util-registry-Registry, reason: not valid java name */
    /* synthetic */ RegistryValue m6505x27c4d547(String str) {
        return new RegistryValue(this, str, this.myContributedKeys.get(str));
    }

    public static boolean is(String str) throws MissingResourceException {
        if (str == null) {
            $$$reportNull$$$0(4);
        }
        return get(str).asBoolean();
    }

    public static boolean is(String str, boolean z) {
        if (str == null) {
            $$$reportNull$$$0(5);
        }
        if (!LoadingState.COMPONENTS_LOADED.isOccurred()) {
            LoadingState.LAF_INITIALIZED.checkOccurred();
            return z;
        }
        try {
            return getInstance().doGet(str).asBoolean();
        } catch (MissingResourceException unused) {
            return z;
        }
    }

    public static int intValue(String str) throws MissingResourceException {
        if (str == null) {
            $$$reportNull$$$0(6);
        }
        return getInstance().doGet(str).asInteger();
    }

    /* JADX WARN: Type inference failed for: r2v2, types: [org.jetbrains.kotlin.com.intellij.openapi.util.registry.Registry$1] */
    private static Map<String, String> loadFromBundledConfig() throws IOException {
        Reference<Map<String, String>> reference = bundledRegistry;
        Map<String, String> map = reference == null ? null : reference.get();
        if (map != null) {
            return map;
        }
        InputStream resourceAsStream = Registry.class.getClassLoader().getResourceAsStream("misc/registry.properties");
        if (resourceAsStream == null) {
            return null;
        }
        final HashMap map2 = new HashMap(1800);
        try {
            new Properties() { // from class: org.jetbrains.kotlin.com.intellij.openapi.util.registry.Registry.1
                @Override // java.util.Hashtable, java.util.Dictionary, java.util.Map
                public Object put(Object obj, Object obj2) {
                    return map2.put((String) obj, (String) obj2);
                }
            }.load(resourceAsStream);
            resourceAsStream.close();
            bundledRegistry = new SoftReference(map2);
            return map2;
        } catch (Throwable th) {
            resourceAsStream.close();
            throw th;
        }
    }

    public String getBundleValueOrNull(String str) {
        if (str == null) {
            $$$reportNull$$$0(13);
        }
        RegistryKeyDescriptor registryKeyDescriptor = this.myContributedKeys.get(str);
        if (registryKeyDescriptor != null) {
            return registryKeyDescriptor.getDefaultValue();
        }
        try {
            Map<String, String> mapLoadFromBundledConfig = loadFromBundledConfig();
            if (mapLoadFromBundledConfig == null) {
                return null;
            }
            return mapLoadFromBundledConfig.get(str);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    String getBundleValue(String str) throws MissingResourceException {
        if (str == null) {
            $$$reportNull$$$0(14);
        }
        RegistryKeyDescriptor registryKeyDescriptor = this.myContributedKeys.get(str);
        if (registryKeyDescriptor != null) {
            String defaultValue = registryKeyDescriptor.getDefaultValue();
            if (defaultValue == null) {
                $$$reportNull$$$0(15);
            }
            return defaultValue;
        }
        String bundleValueOrNull = getBundleValueOrNull(str);
        if (bundleValueOrNull == null) {
            throw new MissingResourceException("Registry key " + str + " is not defined", "misc.registry", str);
        }
        if (bundleValueOrNull == null) {
            $$$reportNull$$$0(16);
        }
        return bundleValueOrNull;
    }

    public static Registry getInstance() {
        LoadingState.COMPONENTS_LOADED.checkOccurred();
        Registry registry = ourInstance;
        if (registry == null) {
            $$$reportNull$$$0(17);
        }
        return registry;
    }

    public boolean isLoaded() {
        return this.isLoaded;
    }

    Map<String, String> getUserProperties() {
        Map<String, String> map = this.myUserProperties;
        if (map == null) {
            $$$reportNull$$$0(19);
        }
        return map;
    }
}
