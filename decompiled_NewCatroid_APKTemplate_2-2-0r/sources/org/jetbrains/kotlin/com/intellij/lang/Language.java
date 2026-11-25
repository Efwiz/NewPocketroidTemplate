package org.jetbrains.kotlin.com.intellij.lang;

import com.reandroid.arsc.container.SpecTypePair;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import name.antonsmirnov.firmata.FormatHelper;
import org.apache.xerces.impl.xs.SchemaSymbols;
import org.jetbrains.kotlin.com.intellij.diagnostic.ImplementationConflictException;
import org.jetbrains.kotlin.com.intellij.openapi.application.ApplicationManager;
import org.jetbrains.kotlin.com.intellij.openapi.fileTypes.FileType;
import org.jetbrains.kotlin.com.intellij.openapi.fileTypes.FileTypeRegistry;
import org.jetbrains.kotlin.com.intellij.openapi.fileTypes.LanguageFileType;
import org.jetbrains.kotlin.com.intellij.openapi.util.UserDataHolderBase;
import org.jetbrains.kotlin.com.intellij.openapi.util.text.StringUtil;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.resolve.reference.ReferenceProvidersRegistry;
import org.jetbrains.kotlin.com.intellij.psi.tree.IElementType;
import org.jetbrains.kotlin.com.intellij.util.ArrayUtilRt;
import org.jetbrains.kotlin.com.intellij.util.ConcurrencyUtil;
import org.jetbrains.kotlin.com.intellij.util.containers.ContainerUtil;

/* JADX INFO: loaded from: classes3.dex */
public abstract class Language extends UserDataHolderBase {
    private final Language myBaseLanguage;
    private final List<Language> myDialects;
    private final String myID;
    private final String[] myMimeTypes;
    private static final Map<Class<? extends Language>, Language> ourRegisteredLanguages = new ConcurrentHashMap();
    private static final ConcurrentMap<String, List<Language>> ourRegisteredMimeTypes = new ConcurrentHashMap();
    private static final Map<String, Language> ourRegisteredIDs = new ConcurrentHashMap();
    public static final Language ANY = new Language("") { // from class: org.jetbrains.kotlin.com.intellij.lang.Language.1
        @Override // org.jetbrains.kotlin.com.intellij.lang.Language
        public LanguageFileType getAssociatedFileType() {
            return null;
        }

        @Override // org.jetbrains.kotlin.com.intellij.lang.Language, java.util.concurrent.atomic.AtomicReference
        public String toString() {
            return "Language: ANY";
        }
    };

    /* JADX WARN: Code duplicated, block: B:8:0x0011  */
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str;
        int i2;
        if (i != 5 && i != 14 && i != 16) {
            switch (i) {
                case 10:
                case 11:
                case 12:
                    str = "@NotNull method %s.%s must not return null";
                    break;
                default:
                    str = "Argument for @NotNull parameter '%s' of %s.%s must not be null";
                    break;
            }
        } else {
            str = "@NotNull method %s.%s must not return null";
        }
        if (i != 5 && i != 14 && i != 16) {
            switch (i) {
                case 10:
                case 11:
                case 12:
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
        switch (i) {
            case 2:
            case 4:
                objArr[0] = "mimeTypes";
                break;
            case 3:
            default:
                objArr[0] = SchemaSymbols.ATTVAL_ID;
                break;
            case 5:
            case 10:
            case 11:
            case 12:
            case 14:
            case 16:
                objArr[0] = "org/jetbrains/kotlin/com/intellij/lang/Language";
                break;
            case 6:
                objArr[0] = "classLoader";
                break;
            case 7:
            case 8:
                objArr[0] = "language";
                break;
            case 9:
                objArr[0] = "klass";
                break;
            case 13:
                objArr[0] = SpecTypePair.NAME_types;
                break;
            case 15:
                objArr[0] = "anotherLanguageId";
                break;
        }
        if (i == 5) {
            objArr[1] = "getRegisteredLanguages";
        } else if (i == 14) {
            objArr[1] = "getDisplayName";
        } else if (i != 16) {
            switch (i) {
                case 10:
                    objArr[1] = "findInstancesByMimeType";
                    break;
                case 11:
                    objArr[1] = "getMimeTypes";
                    break;
                case 12:
                    objArr[1] = "getID";
                    break;
                default:
                    objArr[1] = "org/jetbrains/kotlin/com/intellij/lang/Language";
                    break;
            }
        } else {
            objArr[1] = "getDialects";
        }
        switch (i) {
            case 5:
            case 10:
            case 11:
            case 12:
            case 14:
            case 16:
                break;
            case 6:
                objArr[2] = "unregisterLanguages";
                break;
            case 7:
                objArr[2] = "unregisterLanguage";
                break;
            case 8:
                objArr[2] = "unregisterDialect";
                break;
            case 9:
                objArr[2] = "findInstance";
                break;
            case 13:
                objArr[2] = "findMyFileType";
                break;
            case 15:
                objArr[2] = "isKindOf";
                break;
            default:
                objArr[2] = "<init>";
                break;
        }
        String str2 = String.format(str, objArr);
        if (i != 5 && i != 14 && i != 16) {
            switch (i) {
                case 10:
                case 11:
                case 12:
                    break;
                default:
                    throw new IllegalArgumentException(str2);
            }
        }
        throw new IllegalStateException(str2);
    }

    public final boolean is(Language language) {
        return this == language;
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    protected Language(String str) {
        this(str, ArrayUtilRt.EMPTY_STRING_ARRAY);
        if (str == null) {
            $$$reportNull$$$0(0);
        }
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    protected Language(String str, String... strArr) {
        this(null, str, strArr);
        if (str == null) {
            $$$reportNull$$$0(1);
        }
        if (strArr == null) {
            $$$reportNull$$$0(2);
        }
    }

    protected Language(Language language, String str, String... strArr) {
        if (str == null) {
            $$$reportNull$$$0(3);
        }
        if (strArr == null) {
            $$$reportNull$$$0(4);
        }
        this.myDialects = ContainerUtil.createLockFreeCopyOnWriteList();
        if (language instanceof MetaLanguage) {
            throw new ImplementationConflictException("MetaLanguage cannot be a base language.\nThis language: '" + str + "'\nBase language: '" + language.getID() + FormatHelper.QUOTE, null, this, language);
        }
        this.myBaseLanguage = language;
        this.myID = str;
        this.myMimeTypes = strArr.length == 0 ? ArrayUtilRt.EMPTY_STRING_ARRAY : strArr;
        Class<?> cls = getClass();
        Language languagePut = ourRegisteredLanguages.put((Class<? extends Language>) cls, this);
        if (languagePut != null) {
            throw new ImplementationConflictException("Language of '" + cls + "' is already registered: " + languagePut, null, languagePut, this);
        }
        Language languagePut2 = ourRegisteredIDs.put(str, this);
        if (languagePut2 != null) {
            throw new ImplementationConflictException("Language with ID '" + str + "' is already registered: " + languagePut2.getClass(), null, languagePut2, this);
        }
        for (String str2 : strArr) {
            if (!StringUtil.isEmpty(str2)) {
                ConcurrentMap<String, List<Language>> concurrentMap = ourRegisteredMimeTypes;
                List<Language> list = concurrentMap.get(str2);
                (list == null ? (List) ConcurrencyUtil.cacheOrGet(concurrentMap, str2, ContainerUtil.createConcurrentList()) : list).add(this);
            }
        }
        if (language != null) {
            language.myDialects.add(this);
        }
    }

    public static Collection<Language> getRegisteredLanguages() {
        Collection<Language> collectionUnmodifiableCollection = Collections.unmodifiableCollection(new ArrayList(ourRegisteredLanguages.values()));
        if (collectionUnmodifiableCollection == null) {
            $$$reportNull$$$0(5);
        }
        return collectionUnmodifiableCollection;
    }

    public static void unregisterLanguages(ClassLoader classLoader) {
        if (classLoader == null) {
            $$$reportNull$$$0(6);
        }
        for (Class cls : new ArrayList(ourRegisteredLanguages.keySet())) {
            if (cls.getClassLoader() == classLoader) {
                unregisterLanguage(ourRegisteredLanguages.get(cls));
            }
        }
        IElementType.unregisterElementTypes(classLoader);
    }

    public static void unregisterLanguage(Language language) {
        if (language == null) {
            $$$reportNull$$$0(7);
        }
        IElementType.unregisterElementTypes(language);
        ReferenceProvidersRegistry referenceProvidersRegistry = (ReferenceProvidersRegistry) ApplicationManager.getApplication().getServiceIfCreated(ReferenceProvidersRegistry.class);
        if (referenceProvidersRegistry != null) {
            referenceProvidersRegistry.unloadProvidersFor(language);
        }
        ourRegisteredLanguages.remove(language.getClass());
        ourRegisteredIDs.remove(language.getID());
        for (String str : language.getMimeTypes()) {
            ourRegisteredMimeTypes.remove(str);
        }
        Language baseLanguage = language.getBaseLanguage();
        if (baseLanguage != null) {
            baseLanguage.unregisterDialect(language);
        }
    }

    public void unregisterDialect(Language language) {
        if (language == null) {
            $$$reportNull$$$0(8);
        }
        this.myDialects.remove(language);
    }

    public static <T extends Language> T findInstance(Class<T> cls) {
        if (cls == null) {
            $$$reportNull$$$0(9);
        }
        return (T) ourRegisteredLanguages.get(cls);
    }

    public static Collection<Language> findInstancesByMimeType(String str) {
        List<Language> list = str == null ? null : ourRegisteredMimeTypes.get(str);
        Collection<Language> collectionEmptyList = list == null ? Collections.emptyList() : Collections.unmodifiableCollection(list);
        if (collectionEmptyList == null) {
            $$$reportNull$$$0(10);
        }
        return collectionEmptyList;
    }

    @Override // java.util.concurrent.atomic.AtomicReference
    public String toString() {
        return "Language: " + this.myID;
    }

    public String[] getMimeTypes() {
        String[] strArr = this.myMimeTypes;
        if (strArr == null) {
            $$$reportNull$$$0(11);
        }
        return strArr;
    }

    public String getID() {
        String str = this.myID;
        if (str == null) {
            $$$reportNull$$$0(12);
        }
        return str;
    }

    public LanguageFileType getAssociatedFileType() {
        return FileTypeRegistry.getInstance().findFileTypeByLanguage(this);
    }

    public LanguageFileType findMyFileType(FileType[] fileTypeArr) {
        if (fileTypeArr == null) {
            $$$reportNull$$$0(13);
        }
        for (FileType fileType : fileTypeArr) {
            if (fileType instanceof LanguageFileType) {
                LanguageFileType languageFileType = (LanguageFileType) fileType;
                if (languageFileType.getLanguage() == this && !languageFileType.isSecondary()) {
                    return languageFileType;
                }
            }
        }
        for (FileType fileType2 : fileTypeArr) {
            if (fileType2 instanceof LanguageFileType) {
                LanguageFileType languageFileType2 = (LanguageFileType) fileType2;
                if (isKindOf(languageFileType2.getLanguage()) && !languageFileType2.isSecondary()) {
                    return languageFileType2;
                }
            }
        }
        return null;
    }

    public Language getBaseLanguage() {
        return this.myBaseLanguage;
    }

    public String getDisplayName() {
        String id2 = getID();
        if (id2 == null) {
            $$$reportNull$$$0(14);
        }
        return id2;
    }

    public boolean isCaseSensitive() {
        Language language = this.myBaseLanguage;
        return language != null && language.isCaseSensitive();
    }

    public final boolean isKindOf(Language language) {
        for (Language baseLanguage = this; baseLanguage != null; baseLanguage = baseLanguage.getBaseLanguage()) {
            if (baseLanguage.is(language)) {
                return true;
            }
        }
        return false;
    }

    public final boolean isKindOf(String str) {
        if (str == null) {
            $$$reportNull$$$0(15);
        }
        for (Language baseLanguage = this; baseLanguage != null; baseLanguage = baseLanguage.getBaseLanguage()) {
            if (baseLanguage.getID().equals(str)) {
                return true;
            }
        }
        return false;
    }

    public List<Language> getDialects() {
        List<Language> list = this.myDialects;
        if (list == null) {
            $$$reportNull$$$0(16);
        }
        return list;
    }

    public static Language findLanguageByID(String str) {
        if (str == null) {
            return null;
        }
        return ourRegisteredIDs.get(str);
    }

    protected Language(String str, boolean z) {
        if (str == null) {
            $$$reportNull$$$0(17);
        }
        this.myDialects = ContainerUtil.createLockFreeCopyOnWriteList();
        Language languageFindLanguageByID = findLanguageByID(str);
        if (languageFindLanguageByID != null) {
            throw new IllegalArgumentException("Language with ID=" + str + " already registered: " + languageFindLanguageByID + "; " + languageFindLanguageByID.getClass());
        }
        this.myID = str;
        this.myBaseLanguage = null;
        this.myMimeTypes = null;
    }
}
