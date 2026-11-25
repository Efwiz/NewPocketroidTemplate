package org.jetbrains.kotlin.com.intellij.psi.util;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.function.Function;
import org.antlr.stringtemplate.language.ASTExpr;
import org.catrobat.catroid.common.Constants;
import org.jetbrains.kotlin.com.intellij.openapi.project.Project;
import org.jetbrains.kotlin.com.intellij.openapi.util.Getter;
import org.jetbrains.kotlin.com.intellij.openapi.util.Key;
import org.jetbrains.kotlin.com.intellij.openapi.util.UserDataHolder;
import org.jetbrains.kotlin.com.intellij.openapi.util.UserDataHolderEx;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiFile;
import org.jetbrains.kotlin.com.intellij.util.ArrayFactory;
import org.jetbrains.kotlin.com.intellij.util.ArrayUtil;
import org.jetbrains.kotlin.com.intellij.util.ConcurrencyUtil;

/* JADX INFO: loaded from: classes3.dex */
public abstract class CachedValuesManager {
    private static final ConcurrentMap<String, Key<CachedValue<?>>> globalKeyForProvider = new ConcurrentHashMap();
    private final ConcurrentMap<String, Key<CachedValue<?>>> keyForProvider = new ConcurrentHashMap();

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = (i == 2 || i == 17) ? "@NotNull method %s.%s must not return null" : "Argument for @NotNull parameter '%s' of %s.%s must not be null";
        Object[] objArr = new Object[(i == 2 || i == 17) ? 2 : 3];
        switch (i) {
            case 1:
            case 5:
            case 7:
            case 9:
            case 11:
            case 14:
                objArr[0] = Constants.EXTRA_PROVIDER;
                break;
            case 2:
            case 17:
                objArr[0] = "org/jetbrains/kotlin/com/intellij/psi/util/CachedValuesManager";
                break;
            case 3:
            case 6:
                objArr[0] = "dataHolder";
                break;
            case 4:
            case 13:
                objArr[0] = ASTExpr.DEFAULT_MAP_KEY_NAME;
                break;
            case 8:
            case 10:
            case 12:
                objArr[0] = "context";
                break;
            case 15:
            case 16:
                objArr[0] = "providerClass";
                break;
            default:
                objArr[0] = "project";
                break;
        }
        if (i == 2) {
            objArr[1] = "createCachedValue";
        } else if (i != 17) {
            objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/util/CachedValuesManager";
        } else {
            objArr[1] = "getKeyForClass";
        }
        switch (i) {
            case 1:
                objArr[2] = "createCachedValue";
                break;
            case 2:
            case 17:
                break;
            case 3:
            case 4:
            case 5:
                objArr[2] = "getParameterizedCachedValue";
                break;
            case 6:
            case 7:
            case 8:
            case 9:
            case 12:
            case 13:
            case 14:
                objArr[2] = "getCachedValue";
                break;
            case 10:
            case 11:
                objArr[2] = "getProjectPsiDependentCache";
                break;
            case 15:
            case 16:
                objArr[2] = "getKeyForClass";
                break;
            default:
                objArr[2] = "getManager";
                break;
        }
        String str2 = String.format(str, objArr);
        if (i != 2 && i != 17) {
            throw new IllegalArgumentException(str2);
        }
        throw new IllegalStateException(str2);
    }

    public abstract <T> CachedValue<T> createCachedValue(CachedValueProvider<T> cachedValueProvider, boolean z);

    public abstract <T, P> ParameterizedCachedValue<T, P> createParameterizedCachedValue(ParameterizedCachedValueProvider<T, P> parameterizedCachedValueProvider, boolean z);

    public abstract <T> T getCachedValue(UserDataHolder userDataHolder, Key<CachedValue<T>> key, CachedValueProvider<T> cachedValueProvider, boolean z);

    protected abstract void trackKeyHolder(UserDataHolder userDataHolder, Key<?> key);

    public static CachedValuesManager getManager(Project project) {
        if (project == null) {
            $$$reportNull$$$0(0);
        }
        return (CachedValuesManager) project.getService(CachedValuesManager.class);
    }

    public <T> CachedValue<T> createCachedValue(CachedValueProvider<T> cachedValueProvider) {
        if (cachedValueProvider == null) {
            $$$reportNull$$$0(1);
        }
        CachedValue<T> cachedValueCreateCachedValue = createCachedValue(cachedValueProvider, false);
        if (cachedValueCreateCachedValue == null) {
            $$$reportNull$$$0(2);
        }
        return cachedValueCreateCachedValue;
    }

    public <T, P> T getParameterizedCachedValue(UserDataHolder userDataHolder, Key<ParameterizedCachedValue<T, P>> key, ParameterizedCachedValueProvider<T, P> parameterizedCachedValueProvider, boolean z, P p) {
        ParameterizedCachedValue<T, P> parameterizedCachedValue;
        if (userDataHolder == null) {
            $$$reportNull$$$0(3);
        }
        if (key == null) {
            $$$reportNull$$$0(4);
        }
        if (parameterizedCachedValueProvider == null) {
            $$$reportNull$$$0(5);
        }
        if (userDataHolder instanceof UserDataHolderEx) {
            UserDataHolderEx userDataHolderEx = (UserDataHolderEx) userDataHolder;
            parameterizedCachedValue = (ParameterizedCachedValue) userDataHolderEx.getUserData(key);
            if (parameterizedCachedValue == null) {
                trackKeyHolder(userDataHolder, key);
                parameterizedCachedValue = (ParameterizedCachedValue) userDataHolderEx.putUserDataIfAbsent(key, createParameterizedCachedValue(parameterizedCachedValueProvider, z));
            }
        } else {
            synchronized (userDataHolder) {
                ParameterizedCachedValue<T, P> parameterizedCachedValue2 = (ParameterizedCachedValue) userDataHolder.getUserData(key);
                if (parameterizedCachedValue2 == null) {
                    trackKeyHolder(userDataHolder, key);
                    ParameterizedCachedValue<T, P> parameterizedCachedValueCreateParameterizedCachedValue = createParameterizedCachedValue(parameterizedCachedValueProvider, z);
                    userDataHolder.putUserData(key, parameterizedCachedValueCreateParameterizedCachedValue);
                    parameterizedCachedValue = parameterizedCachedValueCreateParameterizedCachedValue;
                } else {
                    parameterizedCachedValue = parameterizedCachedValue2;
                }
            }
        }
        return parameterizedCachedValue.getValue(p);
    }

    public <T> T getCachedValue(UserDataHolder userDataHolder, CachedValueProvider<T> cachedValueProvider) {
        if (userDataHolder == null) {
            $$$reportNull$$$0(6);
        }
        if (cachedValueProvider == null) {
            $$$reportNull$$$0(7);
        }
        return (T) getCachedValue(userDataHolder, getKeyForClass(cachedValueProvider.getClass()), cachedValueProvider, false);
    }

    public static <T> T getCachedValue(PsiElement psiElement, CachedValueProvider<T> cachedValueProvider) {
        if (psiElement == null) {
            $$$reportNull$$$0(8);
        }
        if (cachedValueProvider == null) {
            $$$reportNull$$$0(9);
        }
        return (T) getCachedValue(psiElement, getKeyForClass(cachedValueProvider.getClass(), globalKeyForProvider), cachedValueProvider);
    }

    public static <E extends PsiElement, T> T getProjectPsiDependentCache(final E e, final Function<? super E, ? extends T> function) {
        if (e == null) {
            $$$reportNull$$$0(10);
        }
        if (function == null) {
            $$$reportNull$$$0(11);
        }
        return (T) getCachedValue(e, getKeyForClass(function.getClass(), globalKeyForProvider), new CachedValueProvider() { // from class: org.jetbrains.kotlin.com.intellij.psi.util.CachedValuesManager$$ExternalSyntheticLambda0
            @Override // org.jetbrains.kotlin.com.intellij.psi.util.CachedValueProvider
            public final CachedValueProvider.Result compute() {
                return CachedValuesManager.lambda$getProjectPsiDependentCache$0(function, e);
            }
        });
    }

    static /* synthetic */ CachedValueProvider.Result lambda$getProjectPsiDependentCache$0(Function function, PsiElement psiElement) {
        CachedValueProvider.Result resultCreate = CachedValueProvider.Result.create(function.apply(psiElement), PsiModificationTracker.MODIFICATION_COUNT);
        CachedValueProfiler.onResultCreated(resultCreate, function);
        return resultCreate;
    }

    public static <T> T getCachedValue(final PsiElement psiElement, Key<CachedValue<T>> key, final CachedValueProvider<T> cachedValueProvider) {
        Getter<T> upToDateOrNull;
        if (psiElement == null) {
            $$$reportNull$$$0(12);
        }
        if (key == null) {
            $$$reportNull$$$0(13);
        }
        if (cachedValueProvider == null) {
            $$$reportNull$$$0(14);
        }
        CachedValue cachedValue = (CachedValue) psiElement.getUserData(key);
        if (cachedValue != null && (upToDateOrNull = cachedValue.getUpToDateOrNull()) != null) {
            return upToDateOrNull.get();
        }
        return (T) getManager(psiElement.getProject()).getCachedValue(psiElement, key, new CachedValueProvider<T>() { // from class: org.jetbrains.kotlin.com.intellij.psi.util.CachedValuesManager.1
            @Override // org.jetbrains.kotlin.com.intellij.psi.util.CachedValueProvider
            public CachedValueProvider.Result<T> compute() {
                PsiFile containingFile;
                CachedValueProvider.Result<T> resultCompute = cachedValueProvider.compute();
                if (resultCompute == null || psiElement.isPhysical() || (containingFile = psiElement.getContainingFile()) == null) {
                    return resultCompute;
                }
                CachedValueProvider.Result<T> resultCreate = CachedValueProvider.Result.create(resultCompute.getValue(), ArrayUtil.append((PsiFile[]) resultCompute.getDependencyItems(), containingFile, (ArrayFactory<? extends PsiFile>) ArrayUtil.OBJECT_ARRAY_FACTORY));
                CachedValueProfiler.onResultCreated(resultCreate, resultCompute);
                return resultCreate;
            }

            public String toString() {
                return cachedValueProvider.toString();
            }
        }, false);
    }

    public <T> Key<CachedValue<T>> getKeyForClass(Class<?> cls) {
        if (cls == null) {
            $$$reportNull$$$0(15);
        }
        return getKeyForClass(cls, this.keyForProvider);
    }

    private static <T> Key<CachedValue<T>> getKeyForClass(Class<?> cls, ConcurrentMap<String, Key<CachedValue<?>>> concurrentMap) {
        if (cls == null) {
            $$$reportNull$$$0(16);
        }
        String name2 = cls.getName();
        Key<CachedValue<T>> key = (Key) concurrentMap.get(name2);
        if (key == null) {
            key = (Key) ConcurrencyUtil.cacheOrGet(concurrentMap, name2, Key.create(name2));
        }
        if (key == null) {
            $$$reportNull$$$0(17);
        }
        return key;
    }
}
