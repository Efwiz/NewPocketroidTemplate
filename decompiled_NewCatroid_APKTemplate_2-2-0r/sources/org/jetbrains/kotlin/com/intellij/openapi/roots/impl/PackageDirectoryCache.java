package org.jetbrains.kotlin.com.intellij.openapi.roots.impl;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Supplier;
import org.jetbrains.kotlin.com.intellij.openapi.diagnostic.Logger;
import org.jetbrains.kotlin.com.intellij.openapi.progress.ProgressManager;
import org.jetbrains.kotlin.com.intellij.openapi.util.NotNullLazyValue;
import org.jetbrains.kotlin.com.intellij.openapi.util.registry.Registry;
import org.jetbrains.kotlin.com.intellij.openapi.util.text.StringUtil;
import org.jetbrains.kotlin.com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.kotlin.com.intellij.psi.PsiTreeChangeEvent;
import org.jetbrains.kotlin.com.intellij.psi.search.GlobalSearchScope;
import org.jetbrains.kotlin.com.intellij.util.SmartList;
import org.jetbrains.kotlin.com.intellij.util.containers.ContainerUtil;
import org.jetbrains.kotlin.com.intellij.util.containers.MultiMap;

/* JADX INFO: loaded from: classes3.dex */
public class PackageDirectoryCache {
    private static final Logger LOG = Logger.getInstance((Class<?>) PackageDirectoryCache.class);
    private final Map<String, PackageInfo> myDirectoriesByPackageNameCache;
    private final Set<String> myNonExistentPackages;
    private final MultiMap<String, VirtualFile> myRootsByPackagePrefix;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = (i == 2 || i == 6 || i == 7) ? "@NotNull method %s.%s must not return null" : "Argument for @NotNull parameter '%s' of %s.%s must not be null";
        Object[] objArr = new Object[(i == 2 || i == 6 || i == 7) ? 2 : 3];
        switch (i) {
            case 1:
            case 3:
            case 4:
            case 10:
                objArr[0] = "packageName";
                break;
            case 2:
            case 6:
            case 7:
                objArr[0] = "org/jetbrains/kotlin/com/intellij/openapi/roots/impl/PackageDirectoryCache";
                break;
            case 5:
                objArr[0] = "scope";
                break;
            case 8:
                objArr[0] = PsiTreeChangeEvent.PROP_ROOTS;
                break;
            case 9:
                objArr[0] = "dir";
                break;
            default:
                objArr[0] = "rootsByPackagePrefix";
                break;
        }
        if (i == 2) {
            objArr[1] = "getDirectoriesByPackageName";
        } else if (i == 6 || i == 7) {
            objArr[1] = "getSubpackageNames";
        } else {
            objArr[1] = "org/jetbrains/kotlin/com/intellij/openapi/roots/impl/PackageDirectoryCache";
        }
        switch (i) {
            case 1:
                objArr[2] = "getDirectoriesByPackageName";
                break;
            case 2:
            case 6:
            case 7:
                break;
            case 3:
                objArr[2] = "getPackageInfo";
                break;
            case 4:
            case 5:
                objArr[2] = "getSubpackageNames";
                break;
            case 8:
                objArr[2] = "createCache";
                break;
            case 9:
            case 10:
                objArr[2] = "isPackageDirectory";
                break;
            default:
                objArr[2] = "<init>";
                break;
        }
        String str2 = String.format(str, objArr);
        if (i != 2 && i != 6 && i != 7) {
            throw new IllegalArgumentException(str2);
        }
        throw new IllegalStateException(str2);
    }

    protected boolean isPackageDirectory(VirtualFile virtualFile, String str) {
        if (virtualFile == null) {
            $$$reportNull$$$0(9);
        }
        if (str != null) {
            return true;
        }
        $$$reportNull$$$0(10);
        return true;
    }

    public PackageDirectoryCache(MultiMap<String, VirtualFile> multiMap) {
        if (multiMap == null) {
            $$$reportNull$$$0(0);
        }
        this.myRootsByPackagePrefix = MultiMap.create();
        this.myDirectoriesByPackageNameCache = new ConcurrentHashMap();
        this.myNonExistentPackages = ContainerUtil.newConcurrentSet();
        for (String str : multiMap.keySet()) {
            for (VirtualFile virtualFile : multiMap.get(str)) {
                if (!virtualFile.isValid()) {
                    LOG.error("Invalid root: " + virtualFile);
                } else {
                    this.myRootsByPackagePrefix.putValue(str, virtualFile);
                }
            }
        }
    }

    public List<VirtualFile> getDirectoriesByPackageName(String str) {
        if (str == null) {
            $$$reportNull$$$0(1);
        }
        PackageInfo packageInfo = getPackageInfo(str);
        List<VirtualFile> listEmptyList = packageInfo == null ? Collections.emptyList() : Collections.unmodifiableList(packageInfo.myPackageDirectories);
        if (listEmptyList == null) {
            $$$reportNull$$$0(2);
        }
        return listEmptyList;
    }

    private PackageInfo getPackageInfo(String str) {
        if (str == null) {
            $$$reportNull$$$0(3);
        }
        PackageInfo packageInfo = this.myDirectoriesByPackageNameCache.get(str);
        if (packageInfo != null || this.myNonExistentPackages.contains(str)) {
            return packageInfo;
        }
        if (str.length() > Registry.intValue("java.max.package.name.length") || StringUtil.containsAnyChar(str, ";[/")) {
            return null;
        }
        SmartList smartList = new SmartList();
        if (StringUtil.isNotEmpty(str) && !StringUtil.startsWithChar(str, '.')) {
            int iLastIndexOf = str.lastIndexOf(46);
            while (true) {
                PackageInfo packageInfo2 = getPackageInfo(iLastIndexOf > 0 ? str.substring(0, iLastIndexOf) : "");
                if (packageInfo2 != null) {
                    smartList.addAll(packageInfo2.getSubPackageDirectories(str.substring(iLastIndexOf + 1)));
                }
                if (iLastIndexOf < 0) {
                    break;
                }
                iLastIndexOf = str.lastIndexOf(46, iLastIndexOf - 1);
                ProgressManager.checkCanceled();
            }
        }
        for (VirtualFile virtualFile : this.myRootsByPackagePrefix.get(str)) {
            if (virtualFile.isDirectory() && virtualFile.isValid()) {
                smartList.add(virtualFile);
            }
        }
        if (!smartList.isEmpty()) {
            Map<String, PackageInfo> map = this.myDirectoriesByPackageNameCache;
            PackageInfo packageInfo3 = new PackageInfo(this, str, smartList);
            map.put(str, packageInfo3);
            return packageInfo3;
        }
        this.myNonExistentPackages.add(str);
        return packageInfo;
    }

    public Set<String> getSubpackageNames(String str, GlobalSearchScope globalSearchScope) {
        if (str == null) {
            $$$reportNull$$$0(4);
        }
        if (globalSearchScope == null) {
            $$$reportNull$$$0(5);
        }
        PackageInfo packageInfo = getPackageInfo(str);
        if (packageInfo == null) {
            Set<String> setEmptySet = Collections.emptySet();
            if (setEmptySet == null) {
                $$$reportNull$$$0(6);
            }
            return setEmptySet;
        }
        HashSet hashSet = new HashSet();
        for (Map.Entry<String, Collection<VirtualFile>> entry : packageInfo.mySubPackages.getValue().entrySet()) {
            String key = entry.getKey();
            Collection<VirtualFile> value = entry.getValue();
            Objects.requireNonNull(globalSearchScope);
            if (ContainerUtil.exists(value, new PackageDirectoryCache$$ExternalSyntheticLambda0(globalSearchScope))) {
                hashSet.add(key);
            }
        }
        Set<String> setUnmodifiableSet = Collections.unmodifiableSet(hashSet);
        if (setUnmodifiableSet == null) {
            $$$reportNull$$$0(7);
        }
        return setUnmodifiableSet;
    }

    public static PackageDirectoryCache createCache(List<? extends VirtualFile> list) {
        if (list == null) {
            $$$reportNull$$$0(8);
        }
        MultiMap multiMapCreate = MultiMap.create();
        multiMapCreate.putValues("", list);
        return new PackageDirectoryCache(multiMapCreate);
    }

    /* JADX INFO: Access modifiers changed from: private */
    final class PackageInfo {
        final List<? extends VirtualFile> myPackageDirectories;
        final String myQname;
        final NotNullLazyValue<MultiMap<String, VirtualFile>> mySubPackages;
        final /* synthetic */ PackageDirectoryCache this$0;

        private static /* synthetic */ void $$$reportNull$$$0(int i) {
            String str = i != 2 ? "Argument for @NotNull parameter '%s' of %s.%s must not be null" : "@NotNull method %s.%s must not return null";
            Object[] objArr = new Object[i != 2 ? 3 : 2];
            if (i == 1) {
                objArr[0] = "packageDirectories";
            } else if (i != 2) {
                objArr[0] = "qname";
            } else {
                objArr[0] = "org/jetbrains/kotlin/com/intellij/openapi/roots/impl/PackageDirectoryCache$PackageInfo";
            }
            if (i != 2) {
                objArr[1] = "org/jetbrains/kotlin/com/intellij/openapi/roots/impl/PackageDirectoryCache$PackageInfo";
            } else {
                objArr[1] = "getSubPackageDirectories";
            }
            if (i != 2) {
                objArr[2] = "<init>";
            }
            String str2 = String.format(str, objArr);
            if (i == 2) {
                throw new IllegalStateException(str2);
            }
        }

        PackageInfo(PackageDirectoryCache packageDirectoryCache, String str, List<? extends VirtualFile> list) {
            if (str == null) {
                $$$reportNull$$$0(0);
            }
            if (list == null) {
                $$$reportNull$$$0(1);
            }
            this.this$0 = packageDirectoryCache;
            this.myQname = str;
            this.myPackageDirectories = list;
            this.mySubPackages = NotNullLazyValue.volatileLazy(new Supplier() { // from class: org.jetbrains.kotlin.com.intellij.openapi.roots.impl.PackageDirectoryCache$PackageInfo$$ExternalSyntheticLambda0
                @Override // java.util.function.Supplier
                public final Object get() {
                    return this.f$0.m6500x229efd6a();
                }
            });
        }

        /* JADX INFO: renamed from: lambda$new$0$org-jetbrains-kotlin-com-intellij-openapi-roots-impl-PackageDirectoryCache$PackageInfo, reason: not valid java name */
        /* synthetic */ MultiMap m6500x229efd6a() {
            MultiMap multiMapCreateLinked = MultiMap.createLinked();
            for (VirtualFile virtualFile : this.myPackageDirectories) {
                ProgressManager.checkCanceled();
                for (VirtualFile virtualFile2 : virtualFile.getChildren()) {
                    String name2 = virtualFile2.getName();
                    String str = this.myQname.isEmpty() ? name2 : this.myQname + "." + name2;
                    if (virtualFile2.isDirectory() && this.this$0.isPackageDirectory(virtualFile2, str)) {
                        multiMapCreateLinked.putValue(name2, virtualFile2);
                    }
                }
            }
            return multiMapCreateLinked;
        }

        Collection<VirtualFile> getSubPackageDirectories(String str) {
            Collection<VirtualFile> collection = this.mySubPackages.getValue().get(str);
            if (collection == null) {
                $$$reportNull$$$0(2);
            }
            return collection;
        }
    }
}
