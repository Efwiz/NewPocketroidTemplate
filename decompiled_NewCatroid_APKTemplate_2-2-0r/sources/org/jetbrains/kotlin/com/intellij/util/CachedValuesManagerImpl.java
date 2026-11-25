package org.jetbrains.kotlin.com.intellij.util;

import java.util.Set;
import java.util.concurrent.ConcurrentMap;
import org.antlr.stringtemplate.language.ASTExpr;
import org.catrobat.catroid.common.Constants;
import org.jetbrains.kotlin.com.intellij.lang.ASTNode;
import org.jetbrains.kotlin.com.intellij.openapi.project.Project;
import org.jetbrains.kotlin.com.intellij.openapi.util.Getter;
import org.jetbrains.kotlin.com.intellij.openapi.util.Key;
import org.jetbrains.kotlin.com.intellij.openapi.util.UserDataHolder;
import org.jetbrains.kotlin.com.intellij.openapi.util.UserDataHolderEx;
import org.jetbrains.kotlin.com.intellij.psi.FileViewProvider;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.util.CachedValue;
import org.jetbrains.kotlin.com.intellij.psi.util.CachedValueProvider;
import org.jetbrains.kotlin.com.intellij.psi.util.CachedValuesManager;
import org.jetbrains.kotlin.com.intellij.psi.util.ParameterizedCachedValue;
import org.jetbrains.kotlin.com.intellij.psi.util.ParameterizedCachedValueProvider;
import org.jetbrains.kotlin.com.intellij.util.containers.CollectionFactory;
import org.jetbrains.kotlin.com.intellij.util.containers.ContainerUtil;

/* JADX INFO: loaded from: classes3.dex */
public final class CachedValuesManagerImpl extends CachedValuesManager {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private static final Object NULL = new Object();
    private final CachedValuesFactory myFactory;
    private final Project myProject;
    private ConcurrentMap<UserDataHolder, Object> myCacheHolders = CollectionFactory.createConcurrentWeakIdentityMap();
    private Set<Key<?>> myKeys = ContainerUtil.newConcurrentSet();

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = (i == 1 || i == 3) ? "@NotNull method %s.%s must not return null" : "Argument for @NotNull parameter '%s' of %s.%s must not be null";
        Object[] objArr = new Object[(i == 1 || i == 3) ? 2 : 3];
        switch (i) {
            case 1:
            case 3:
                objArr[0] = "org/jetbrains/kotlin/com/intellij/util/CachedValuesManagerImpl";
                break;
            case 2:
            case 6:
            default:
                objArr[0] = Constants.EXTRA_PROVIDER;
                break;
            case 4:
            case 7:
            case 9:
            case 11:
                objArr[0] = "dataHolder";
                break;
            case 5:
            case 8:
            case 10:
                objArr[0] = ASTExpr.DEFAULT_MAP_KEY_NAME;
                break;
        }
        if (i == 1) {
            objArr[1] = "createCachedValue";
        } else if (i != 3) {
            objArr[1] = "org/jetbrains/kotlin/com/intellij/util/CachedValuesManagerImpl";
        } else {
            objArr[1] = "createParameterizedCachedValue";
        }
        switch (i) {
            case 1:
            case 3:
                break;
            case 2:
                objArr[2] = "createParameterizedCachedValue";
                break;
            case 4:
            case 5:
            case 6:
                objArr[2] = "getCachedValue";
                break;
            case 7:
            case 8:
                objArr[2] = "saveInUserData";
                break;
            case 9:
            case 10:
                objArr[2] = "trackKeyHolder";
                break;
            case 11:
                objArr[2] = "isClearedOnPluginUnload";
                break;
            default:
                objArr[2] = "createCachedValue";
                break;
        }
        String str2 = String.format(str, objArr);
        if (i != 1 && i != 3) {
            throw new IllegalArgumentException(str2);
        }
        throw new IllegalStateException(str2);
    }

    public CachedValuesManagerImpl(Project project, CachedValuesFactory cachedValuesFactory) {
        this.myProject = project;
        this.myFactory = cachedValuesFactory == null ? new DefaultCachedValuesFactory(project) : cachedValuesFactory;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.util.CachedValuesManager
    public <T> CachedValue<T> createCachedValue(CachedValueProvider<T> cachedValueProvider, boolean z) {
        if (cachedValueProvider == null) {
            $$$reportNull$$$0(0);
        }
        CachedValue<T> cachedValueCreateCachedValue = this.myFactory.createCachedValue(cachedValueProvider, z);
        if (cachedValueCreateCachedValue == null) {
            $$$reportNull$$$0(1);
        }
        return cachedValueCreateCachedValue;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.util.CachedValuesManager
    public <T, P> ParameterizedCachedValue<T, P> createParameterizedCachedValue(ParameterizedCachedValueProvider<T, P> parameterizedCachedValueProvider, boolean z) {
        if (parameterizedCachedValueProvider == null) {
            $$$reportNull$$$0(2);
        }
        ParameterizedCachedValue<T, P> parameterizedCachedValueCreateParameterizedCachedValue = this.myFactory.createParameterizedCachedValue(parameterizedCachedValueProvider, z);
        if (parameterizedCachedValueCreateParameterizedCachedValue == null) {
            $$$reportNull$$$0(3);
        }
        return parameterizedCachedValueCreateParameterizedCachedValue;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // org.jetbrains.kotlin.com.intellij.psi.util.CachedValuesManager
    public <T> T getCachedValue(UserDataHolder userDataHolder, Key<CachedValue<T>> key, CachedValueProvider<T> cachedValueProvider, boolean z) {
        if (userDataHolder == null) {
            $$$reportNull$$$0(4);
        }
        if (key == null) {
            $$$reportNull$$$0(5);
        }
        if (cachedValueProvider == null) {
            $$$reportNull$$$0(6);
        }
        CachedValue cachedValueSaveInUserData = (CachedValue) userDataHolder.getUserData(key);
        if ((cachedValueSaveInUserData instanceof CachedValueBase) && ((CachedValueBase) cachedValueSaveInUserData).isFromMyProject(this.myProject)) {
            Getter upToDateOrNull = cachedValueSaveInUserData.getUpToDateOrNull();
            if (upToDateOrNull != null) {
                return (T) upToDateOrNull.get();
            }
            CachedValueStabilityChecker.checkProvidersEquivalent(cachedValueProvider, cachedValueSaveInUserData.getValueProvider(), key);
        }
        if (cachedValueSaveInUserData == 0) {
            cachedValueSaveInUserData = saveInUserData(userDataHolder, key, freshCachedValue(userDataHolder, key, cachedValueProvider, z));
        }
        return (T) cachedValueSaveInUserData.getValue();
    }

    private <T> CachedValue<T> saveInUserData(UserDataHolder userDataHolder, Key<CachedValue<T>> key, CachedValue<T> cachedValue) {
        if (userDataHolder == null) {
            $$$reportNull$$$0(7);
        }
        if (key == null) {
            $$$reportNull$$$0(8);
        }
        trackKeyHolder(userDataHolder, key);
        if (userDataHolder instanceof UserDataHolderEx) {
            return (CachedValue) ((UserDataHolderEx) userDataHolder).putUserDataIfAbsent(key, cachedValue);
        }
        synchronized (userDataHolder) {
            CachedValue<T> cachedValue2 = (CachedValue) userDataHolder.getUserData(key);
            if (cachedValue2 != null) {
                return cachedValue2;
            }
            userDataHolder.putUserData(key, cachedValue);
            return cachedValue;
        }
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.util.CachedValuesManager
    protected void trackKeyHolder(UserDataHolder userDataHolder, Key<?> key) {
        if (userDataHolder == null) {
            $$$reportNull$$$0(9);
        }
        if (key == null) {
            $$$reportNull$$$0(10);
        }
        if (isClearedOnPluginUnload(userDataHolder)) {
            return;
        }
        this.myCacheHolders.put(userDataHolder, NULL);
        this.myKeys.add(key);
    }

    private static boolean isClearedOnPluginUnload(UserDataHolder userDataHolder) {
        if (userDataHolder == null) {
            $$$reportNull$$$0(11);
        }
        return (userDataHolder instanceof PsiElement) || (userDataHolder instanceof ASTNode) || (userDataHolder instanceof FileViewProvider);
    }

    private <T> CachedValue<T> freshCachedValue(UserDataHolder userDataHolder, Key<CachedValue<T>> key, CachedValueProvider<T> cachedValueProvider, boolean z) {
        CachedValueLeakChecker.checkProviderDoesNotLeakPSI(cachedValueProvider, key, userDataHolder);
        return createCachedValue(cachedValueProvider, z);
    }
}
