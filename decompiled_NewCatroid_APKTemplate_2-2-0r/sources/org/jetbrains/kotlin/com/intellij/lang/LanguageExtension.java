package org.jetbrains.kotlin.com.intellij.lang;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import org.antlr.stringtemplate.language.ASTExpr;
import org.jetbrains.kotlin.com.intellij.openapi.extensions.ExtensionPointName;
import org.jetbrains.kotlin.com.intellij.openapi.util.Key;
import org.jetbrains.kotlin.com.intellij.openapi.util.KeyedExtensionCollector;
import org.jetbrains.kotlin.com.intellij.util.KeyedLazyInstance;
import org.jetbrains.kotlin.com.intellij.util.containers.ContainerUtil;

/* JADX INFO: loaded from: classes3.dex */
public class LanguageExtension<T> extends KeyedExtensionCollector<T, Language> {
    private final Key<List<T>> myAllCacheKey;
    private final Key<T> myCacheKey;
    private final T myDefaultImplementation;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = (i == 4 || i == 13 || i == 10 || i == 11 || i == 16 || i == 17 || i == 19 || i == 20) ? "@NotNull method %s.%s must not return null" : "Argument for @NotNull parameter '%s' of %s.%s must not be null";
        Object[] objArr = new Object[(i == 4 || i == 13 || i == 10 || i == 11 || i == 16 || i == 17 || i == 19 || i == 20) ? 2 : 3];
        switch (i) {
            case 3:
            case 15:
            case 21:
            case 23:
                objArr[0] = ASTExpr.DEFAULT_MAP_KEY_NAME;
                break;
            case 4:
            case 10:
            case 11:
            case 13:
            case 16:
            case 17:
            case 19:
            case 20:
                objArr[0] = "org/jetbrains/kotlin/com/intellij/lang/LanguageExtension";
                break;
            case 5:
            case 6:
            case 8:
            case 9:
            case 12:
                objArr[0] = "language";
                break;
            case 7:
            case 18:
                objArr[0] = "l";
                break;
            case 14:
                objArr[0] = "stringKey";
                break;
            case 22:
            case 24:
                objArr[0] = "t";
                break;
            default:
                objArr[0] = "epName";
                break;
        }
        if (i == 4) {
            objArr[1] = "keyToString";
        } else if (i == 13) {
            objArr[1] = "collectAllForLanguage";
        } else if (i == 10 || i == 11) {
            objArr[1] = "allForLanguage";
        } else if (i == 16 || i == 17) {
            objArr[1] = "buildExtensions";
        } else if (i == 19 || i == 20) {
            objArr[1] = "allForLanguageOrAny";
        } else {
            objArr[1] = "org/jetbrains/kotlin/com/intellij/lang/LanguageExtension";
        }
        switch (i) {
            case 3:
                objArr[2] = "keyToString";
                break;
            case 4:
            case 10:
            case 11:
            case 13:
            case 16:
            case 17:
            case 19:
            case 20:
                break;
            case 5:
                objArr[2] = "clearCache";
                break;
            case 6:
                objArr[2] = "clearCacheForDerivedLanguages";
                break;
            case 7:
                objArr[2] = "forLanguage";
                break;
            case 8:
                objArr[2] = "findForLanguage";
                break;
            case 9:
                objArr[2] = "allForLanguage";
                break;
            case 12:
                objArr[2] = "collectAllForLanguage";
                break;
            case 14:
            case 15:
                objArr[2] = "buildExtensions";
                break;
            case 18:
                objArr[2] = "allForLanguageOrAny";
                break;
            case 21:
            case 22:
                objArr[2] = "addExplicitExtension";
                break;
            case 23:
            case 24:
                objArr[2] = "removeExplicitExtension";
                break;
            default:
                objArr[2] = "<init>";
                break;
        }
        String str2 = String.format(str, objArr);
        if (i != 4 && i != 13 && i != 10 && i != 11 && i != 16 && i != 17 && i != 19 && i != 20) {
            throw new IllegalArgumentException(str2);
        }
        throw new IllegalStateException(str2);
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public LanguageExtension(ExtensionPointName<? extends KeyedLazyInstance<T>> extensionPointName) {
        this(extensionPointName.getName(), (Object) null);
        if (extensionPointName == null) {
            $$$reportNull$$$0(0);
        }
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public LanguageExtension(String str) {
        this(str, (Object) null);
        if (str == null) {
            $$$reportNull$$$0(1);
        }
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public LanguageExtension(ExtensionPointName<? extends KeyedLazyInstance<T>> extensionPointName, T t) {
        this(extensionPointName.getName(), t);
        if (extensionPointName == null) {
            $$$reportNull$$$0(2);
        }
    }

    public LanguageExtension(String str, T t) {
        super(str);
        this.myDefaultImplementation = t;
        this.myCacheKey = Key.create("EXTENSIONS_IN_LANGUAGE_" + str);
        this.myAllCacheKey = Key.create("ALL_EXTENSIONS_IN_LANGUAGE_" + str);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // org.jetbrains.kotlin.com.intellij.openapi.util.KeyedExtensionCollector
    public String keyToString(Language language) {
        if (language == null) {
            $$$reportNull$$$0(3);
        }
        String id2 = language.getID();
        if (id2 == null) {
            $$$reportNull$$$0(4);
        }
        return id2;
    }

    public void clearCache(Language language) {
        if (language == null) {
            $$$reportNull$$$0(5);
        }
        clearCacheForDerivedLanguages(language);
        clearCache();
    }

    private void clearCacheForDerivedLanguages(Language language) {
        if (language == null) {
            $$$reportNull$$$0(6);
        }
        for (Language language2 : LanguageUtil.getAllDerivedLanguages(language)) {
            clearCacheForLanguage(language2);
            Iterator<MetaLanguage> it = LanguageUtil.matchingMetaLanguages(language2).iterator();
            while (it.hasNext()) {
                clearCacheForLanguage(it.next());
            }
        }
        if (language instanceof MetaLanguage) {
            Iterator<Language> it2 = ((MetaLanguage) language).getMatchingLanguages().iterator();
            while (it2.hasNext()) {
                Iterator<Language> it3 = LanguageUtil.getAllDerivedLanguages(it2.next()).iterator();
                while (it3.hasNext()) {
                    clearCacheForLanguage(it3.next());
                }
            }
        }
    }

    private void clearCacheForLanguage(Language language) {
        language.putUserData(this.myCacheKey, null);
        language.putUserData(this.myAllCacheKey, null);
        super.invalidateCacheForExtension(language.getID());
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.util.KeyedExtensionCollector
    public void invalidateCacheForExtension(String str) {
        super.invalidateCacheForExtension(str);
        Language languageFindLanguageByID = Language.findLanguageByID(str);
        if (languageFindLanguageByID != null) {
            clearCacheForDerivedLanguages(languageFindLanguageByID);
        }
    }

    public T forLanguage(Language language) {
        if (language == null) {
            $$$reportNull$$$0(7);
        }
        T t = (T) language.getUserData(this.myCacheKey);
        if (t != null) {
            return t;
        }
        T tFindForLanguage = findForLanguage(language);
        if (tFindForLanguage == null) {
            return null;
        }
        return (T) language.putUserDataIfAbsent(this.myCacheKey, tFindForLanguage);
    }

    protected T findForLanguage(Language language) {
        if (language == null) {
            $$$reportNull$$$0(8);
        }
        while (language != null) {
            List<T> listForKey = forKey(language);
            if (listForKey.isEmpty()) {
                language = language.getBaseLanguage();
            } else {
                return listForKey.get(0);
            }
        }
        return this.myDefaultImplementation;
    }

    public List<T> allForLanguage(Language language) {
        if (language == null) {
            $$$reportNull$$$0(9);
        }
        List<T> list = (List) language.getUserData(this.myAllCacheKey);
        if (list != null) {
            if (list == null) {
                $$$reportNull$$$0(10);
            }
            return list;
        }
        List<T> list2 = (List) language.putUserDataIfAbsent(this.myAllCacheKey, collectAllForLanguage(language));
        if (list2 == null) {
            $$$reportNull$$$0(11);
        }
        return list2;
    }

    private List<T> collectAllForLanguage(Language language) {
        if (language == null) {
            $$$reportNull$$$0(12);
        }
        boolean z = true;
        List<T> arrayList = null;
        while (language != null) {
            List<T> listForKey = forKey(language);
            if (arrayList == null) {
                arrayList = listForKey;
            } else if (!listForKey.isEmpty()) {
                if (z) {
                    arrayList = new ArrayList(ContainerUtil.concat((List) arrayList, (List) listForKey));
                    z = false;
                } else {
                    arrayList.addAll(listForKey);
                }
            }
            language = language.getBaseLanguage();
        }
        if (arrayList == null) {
            $$$reportNull$$$0(13);
        }
        return arrayList;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // org.jetbrains.kotlin.com.intellij.openapi.util.KeyedExtensionCollector
    public List<T> buildExtensions(String str, Language language) {
        if (str == null) {
            $$$reportNull$$$0(14);
        }
        if (language == null) {
            $$$reportNull$$$0(15);
        }
        Collection<MetaLanguage> collectionMatchingMetaLanguages = LanguageUtil.matchingMetaLanguages(language);
        if (collectionMatchingMetaLanguages.isEmpty()) {
            List<T> listBuildExtensions = super.buildExtensions(str, language);
            if (listBuildExtensions == null) {
                $$$reportNull$$$0(16);
            }
            return listBuildExtensions;
        }
        HashSet hashSet = new HashSet();
        hashSet.add(str);
        Iterator<MetaLanguage> it = collectionMatchingMetaLanguages.iterator();
        while (it.hasNext()) {
            hashSet.add(keyToString((Language) it.next()));
        }
        List<T> listBuildExtensions2 = buildExtensions(hashSet);
        if (listBuildExtensions2 == null) {
            $$$reportNull$$$0(17);
        }
        return listBuildExtensions2;
    }

    public List<T> allForLanguageOrAny(Language language) {
        if (language == null) {
            $$$reportNull$$$0(18);
        }
        List<T> listAllForLanguage = allForLanguage(language);
        if (language == Language.ANY) {
            if (listAllForLanguage == null) {
                $$$reportNull$$$0(19);
            }
            return listAllForLanguage;
        }
        List<T> listConcat = ContainerUtil.concat((List) listAllForLanguage, (List) allForLanguage(Language.ANY));
        if (listConcat == null) {
            $$$reportNull$$$0(20);
        }
        return listConcat;
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.util.KeyedExtensionCollector
    public void addExplicitExtension(Language language, T t) {
        if (language == null) {
            $$$reportNull$$$0(21);
        }
        if (t == null) {
            $$$reportNull$$$0(22);
        }
        clearCacheForLanguage(language);
        super.addExplicitExtension(language, (Object) t);
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.util.KeyedExtensionCollector
    /* JADX INFO: renamed from: removeExplicitExtension, reason: avoid collision after fix types in other method and merged with bridge method [inline-methods] */
    public void m6501x67ad86cc(Language language, T t) {
        if (language == null) {
            $$$reportNull$$$0(23);
        }
        if (t == null) {
            $$$reportNull$$$0(24);
        }
        clearCacheForLanguage(language);
        super.m6501x67ad86cc(language, (Object) t);
    }

    protected T getDefaultImplementation() {
        return this.myDefaultImplementation;
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.util.KeyedExtensionCollector
    protected void ensureValuesLoaded() {
        super.ensureValuesLoaded();
    }
}
