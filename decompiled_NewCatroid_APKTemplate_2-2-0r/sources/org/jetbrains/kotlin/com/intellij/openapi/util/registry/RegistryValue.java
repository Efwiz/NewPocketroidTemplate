package org.jetbrains.kotlin.com.intellij.openapi.util.registry;

import com.google.android.gms.common.internal.ServiceSpecificExtraArgs;
import com.huawei.hms.framework.common.ContainerUtils;
import java.util.Iterator;
import java.util.List;
import java.util.MissingResourceException;
import org.antlr.stringtemplate.language.ASTExpr;
import org.jetbrains.kotlin.com.intellij.openapi.Disposable;
import org.jetbrains.kotlin.com.intellij.openapi.diagnostic.Logger;
import org.jetbrains.kotlin.com.intellij.openapi.util.Disposer;
import org.jetbrains.kotlin.com.intellij.util.containers.ContainerUtil;

/* JADX INFO: loaded from: classes3.dex */
public class RegistryValue {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private static final Logger LOG = Logger.getInstance((Class<?>) RegistryValue.class);
    private Boolean myBooleanCachedValue;
    private boolean myChangedSinceStart;
    private double myDoubleCachedValue;
    private Integer myIntCachedValue;
    private final String myKey;
    private final RegistryKeyDescriptor myKeyDescriptor;
    private final List<RegistryValueListener> myListeners;
    private final Registry myRegistry;
    private String myStringCachedValue;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = (i == 2 || i == 3 || i == 4 || i == 5 || i == 7 || i == 8) ? "@NotNull method %s.%s must not return null" : "Argument for @NotNull parameter '%s' of %s.%s must not be null";
        Object[] objArr = new Object[(i == 2 || i == 3 || i == 4 || i == 5 || i == 7 || i == 8) ? 2 : 3];
        switch (i) {
            case 1:
            case 11:
            case 12:
                objArr[0] = ASTExpr.DEFAULT_MAP_KEY_NAME;
                break;
            case 2:
            case 3:
            case 4:
            case 5:
            case 7:
            case 8:
                objArr[0] = "org/jetbrains/kotlin/com/intellij/openapi/util/registry/RegistryValue";
                break;
            case 6:
                objArr[0] = "option";
                break;
            case 9:
                objArr[0] = "newValue";
                break;
            case 10:
            default:
                objArr[0] = "registry";
                break;
            case 13:
            case 14:
            case 15:
                objArr[0] = "parentDisposable";
                break;
            case 16:
                objArr[0] = ServiceSpecificExtraArgs.CastExtraArgs.LISTENER;
                break;
            case 17:
                objArr[0] = "parent";
                break;
        }
        if (i == 2) {
            objArr[1] = "getKey";
        } else if (i == 3) {
            objArr[1] = "asString";
        } else if (i == 4 || i == 5) {
            objArr[1] = "calcInt";
        } else if (i == 7 || i == 8) {
            objArr[1] = "getDescription";
        } else {
            objArr[1] = "org/jetbrains/kotlin/com/intellij/openapi/util/registry/RegistryValue";
        }
        switch (i) {
            case 2:
            case 3:
            case 4:
            case 5:
            case 7:
            case 8:
                break;
            case 6:
                objArr[2] = "isOptionEnabled";
                break;
            case 9:
            case 10:
                objArr[2] = "isChangedFromDefault";
                break;
            case 11:
                objArr[2] = "get";
                break;
            case 12:
                objArr[2] = "_get";
                break;
            case 13:
            case 14:
            case 15:
                objArr[2] = "setValue";
                break;
            case 16:
            case 17:
                objArr[2] = "addListener";
                break;
            default:
                objArr[2] = "<init>";
                break;
        }
        String str2 = String.format(str, objArr);
        if (i != 2 && i != 3 && i != 4 && i != 5 && i != 7 && i != 8) {
            throw new IllegalArgumentException(str2);
        }
        throw new IllegalStateException(str2);
    }

    RegistryValue(Registry registry, String str, RegistryKeyDescriptor registryKeyDescriptor) {
        if (registry == null) {
            $$$reportNull$$$0(0);
        }
        if (str == null) {
            $$$reportNull$$$0(1);
        }
        this.myListeners = ContainerUtil.createLockFreeCopyOnWriteList();
        this.myDoubleCachedValue = Double.NaN;
        this.myRegistry = registry;
        this.myKey = str;
        this.myKeyDescriptor = registryKeyDescriptor;
    }

    public String asString() {
        String str = get(this.myKey, null, true);
        if (str == null) {
            $$$reportNull$$$0(3);
        }
        return str;
    }

    public boolean asBoolean() {
        Boolean boolValueOf = this.myBooleanCachedValue;
        if (boolValueOf == null) {
            boolValueOf = Boolean.valueOf(get(this.myKey, "false", true));
            this.myBooleanCachedValue = boolValueOf;
        }
        return boolValueOf.booleanValue();
    }

    public int asInteger() {
        Integer numCalcInt = this.myIntCachedValue;
        if (numCalcInt == null) {
            numCalcInt = calcInt();
            this.myIntCachedValue = numCalcInt;
        }
        return numCalcInt.intValue();
    }

    private Integer calcInt() {
        try {
            Integer numValueOf = Integer.valueOf(get(this.myKey, "0", true));
            if (numValueOf == null) {
                $$$reportNull$$$0(4);
            }
            return numValueOf;
        } catch (NumberFormatException unused) {
            Integer numValueOf2 = Integer.valueOf(this.myRegistry.getBundleValue(this.myKey));
            if (numValueOf2 == null) {
                $$$reportNull$$$0(5);
            }
            return numValueOf2;
        }
    }

    boolean isRestartRequired() {
        RegistryKeyDescriptor registryKeyDescriptor = this.myKeyDescriptor;
        if (registryKeyDescriptor != null) {
            return registryKeyDescriptor.isRestartRequired();
        }
        return Boolean.parseBoolean(get(this.myKey + ".restartRequired", "false", false));
    }

    public boolean isChangedFromDefault() {
        return isChangedFromDefault(asString(), this.myRegistry);
    }

    final boolean isChangedFromDefault(String str, Registry registry) {
        if (str == null) {
            $$$reportNull$$$0(9);
        }
        if (registry == null) {
            $$$reportNull$$$0(10);
        }
        return !str.equals(registry.getBundleValueOrNull(this.myKey));
    }

    protected String get(String str, String str2, boolean z) throws MissingResourceException {
        if (str == null) {
            $$$reportNull$$$0(11);
        }
        if (z) {
            if (this.myStringCachedValue == null) {
                this.myStringCachedValue = _get(str, str2, true);
            }
            return this.myStringCachedValue;
        }
        return _get(str, str2, false);
    }

    private String _get(String str, String str2, boolean z) throws MissingResourceException {
        if (str == null) {
            $$$reportNull$$$0(12);
        }
        String str3 = this.myRegistry.getUserProperties().get(str);
        if (str3 != null) {
            return str3;
        }
        String property = System.getProperty(str);
        if (property != null) {
            return property;
        }
        if (!this.myRegistry.isLoaded()) {
            String str4 = "Attempt to load key '" + str + "' for not yet loaded registry";
            if (Disposer.isDebugMode()) {
                LOG.error(str4 + ". Use system properties instead of registry values to configure behaviour at early startup stages.");
            } else {
                LOG.warn(str4);
            }
        }
        if (z) {
            return this.myRegistry.getBundleValue(str);
        }
        String bundleValueOrNull = this.myRegistry.getBundleValueOrNull(str);
        return bundleValueOrNull == null ? str2 : bundleValueOrNull;
    }

    /* JADX INFO: renamed from: setValue, reason: merged with bridge method [inline-methods] */
    public void m6506xafda0928(boolean z) {
        setValue(Boolean.toString(z));
    }

    public void setValue(String str) {
        resetCache();
        Iterator<RegistryValueListener> it = this.myListeners.iterator();
        while (it.hasNext()) {
            it.next().beforeValueChanged(this);
        }
        this.myRegistry.getUserProperties().put(this.myKey, str);
        Iterator<RegistryValueListener> it2 = this.myListeners.iterator();
        while (it2.hasNext()) {
            it2.next().afterValueChanged(this);
        }
        if (!isChangedFromDefault() && !isRestartRequired()) {
            this.myRegistry.getUserProperties().remove(this.myKey);
        }
        this.myChangedSinceStart = true;
        LOG.info("Registry value '" + this.myKey + "' has changed to '" + str + '\'');
    }

    public void setValue(boolean z, Disposable disposable) {
        if (disposable == null) {
            $$$reportNull$$$0(13);
        }
        final boolean zAsBoolean = asBoolean();
        m6506xafda0928(z);
        Disposer.register(disposable, new Disposable() { // from class: org.jetbrains.kotlin.com.intellij.openapi.util.registry.RegistryValue$$ExternalSyntheticLambda0
            @Override // org.jetbrains.kotlin.com.intellij.openapi.Disposable
            public final void dispose() {
                this.f$0.m6506xafda0928(zAsBoolean);
            }
        });
    }

    public String toString() {
        return this.myKey + ContainerUtils.KEY_VALUE_DELIMITER + asString();
    }

    public void resetCache() {
        this.myStringCachedValue = null;
        this.myIntCachedValue = null;
        this.myDoubleCachedValue = Double.NaN;
        this.myBooleanCachedValue = null;
    }
}
