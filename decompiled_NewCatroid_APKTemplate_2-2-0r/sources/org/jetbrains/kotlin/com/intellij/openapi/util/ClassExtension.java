package org.jetbrains.kotlin.com.intellij.openapi.util;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;
import org.antlr.stringtemplate.language.ASTExpr;
import org.jetbrains.kotlin.com.intellij.util.KeyedLazyInstance;
import org.jetbrains.kotlin.com.intellij.util.containers.ContainerUtil;

/* JADX INFO: loaded from: classes3.dex */
public class ClassExtension<T> extends KeyedExtensionCollector<T, Class> {
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = (i == 2 || i == 5) ? "@NotNull method %s.%s must not return null" : "Argument for @NotNull parameter '%s' of %s.%s must not be null";
        Object[] objArr = new Object[(i == 2 || i == 5) ? 2 : 3];
        switch (i) {
            case 1:
            case 3:
                objArr[0] = ASTExpr.DEFAULT_MAP_KEY_NAME;
                break;
            case 2:
            case 5:
                objArr[0] = "org/jetbrains/kotlin/com/intellij/openapi/util/ClassExtension";
                break;
            case 4:
            case 6:
                objArr[0] = "classKey";
                break;
            case 7:
                objArr[0] = "allSupers";
                break;
            case 8:
                objArr[0] = "t";
                break;
            default:
                objArr[0] = "epName";
                break;
        }
        if (i == 2) {
            objArr[1] = "keyToString";
        } else if (i != 5) {
            objArr[1] = "org/jetbrains/kotlin/com/intellij/openapi/util/ClassExtension";
        } else {
            objArr[1] = "buildExtensions";
        }
        switch (i) {
            case 1:
                objArr[2] = "keyToString";
                break;
            case 2:
            case 5:
                break;
            case 3:
            case 4:
                objArr[2] = "buildExtensions";
                break;
            case 6:
            case 7:
                objArr[2] = "collectSupers";
                break;
            case 8:
                objArr[2] = "forClass";
                break;
            default:
                objArr[2] = "<init>";
                break;
        }
        String str2 = String.format(str, objArr);
        if (i != 2 && i != 5) {
            throw new IllegalArgumentException(str2);
        }
        throw new IllegalStateException(str2);
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ClassExtension(String str) {
        super(str);
        if (str == null) {
            $$$reportNull$$$0(0);
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // org.jetbrains.kotlin.com.intellij.openapi.util.KeyedExtensionCollector
    public String keyToString(Class cls) {
        if (cls == null) {
            $$$reportNull$$$0(1);
        }
        String name2 = cls.getName();
        if (name2 == null) {
            $$$reportNull$$$0(2);
        }
        return name2;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // org.jetbrains.kotlin.com.intellij.openapi.util.KeyedExtensionCollector
    public List<T> buildExtensions(String str, Class cls) {
        if (str == null) {
            $$$reportNull$$$0(3);
        }
        if (cls == null) {
            $$$reportNull$$$0(4);
        }
        LinkedHashSet linkedHashSet = new LinkedHashSet();
        collectSupers(cls, linkedHashSet);
        List<T> listBuildExtensionsWithInheritance = buildExtensionsWithInheritance(linkedHashSet);
        if (listBuildExtensionsWithInheritance == null) {
            $$$reportNull$$$0(5);
        }
        return listBuildExtensionsWithInheritance;
    }

    private List<T> buildExtensionsWithInheritance(Set<String> set) {
        List<T> listNotNullize;
        List<KeyedLazyInstance<T>> extensions = getExtensions();
        synchronized (this.myLock) {
            List<T> listBuildExtensionsFromExtensionPoint = null;
            for (final String str : set) {
                listBuildExtensionsFromExtensionPoint = buildExtensionsFromExplicitRegistration(listBuildExtensionsFromExtensionPoint, new Predicate() { // from class: org.jetbrains.kotlin.com.intellij.openapi.util.ClassExtension$$ExternalSyntheticLambda0
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return str.equals((String) obj);
                    }
                });
            }
            for (final String str2 : set) {
                listBuildExtensionsFromExtensionPoint = buildExtensionsFromExtensionPoint(listBuildExtensionsFromExtensionPoint, new Predicate() { // from class: org.jetbrains.kotlin.com.intellij.openapi.util.ClassExtension$$ExternalSyntheticLambda1
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return str2.equals(((KeyedLazyInstance) obj).getKey());
                    }
                }, extensions);
            }
            listNotNullize = ContainerUtil.notNullize(listBuildExtensionsFromExtensionPoint);
        }
        return listNotNullize;
    }

    private static void collectSupers(Class cls, Set<? super String> set) {
        if (cls == null) {
            $$$reportNull$$$0(6);
        }
        if (set == null) {
            $$$reportNull$$$0(7);
        }
        set.add(cls.getName());
        for (Class<?> cls2 : cls.getInterfaces()) {
            collectSupers(cls2, set);
        }
        Class<? super T> superclass = cls.getSuperclass();
        if (superclass != null) {
            collectSupers(superclass, set);
        }
    }

    public T forClass(Class cls) {
        if (cls == null) {
            $$$reportNull$$$0(8);
        }
        List<T> listForKey = forKey(cls);
        if (listForKey.isEmpty()) {
            return null;
        }
        return listForKey.get(0);
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.util.KeyedExtensionCollector
    protected void invalidateCacheForExtension(String str) {
        clearCache();
    }
}
