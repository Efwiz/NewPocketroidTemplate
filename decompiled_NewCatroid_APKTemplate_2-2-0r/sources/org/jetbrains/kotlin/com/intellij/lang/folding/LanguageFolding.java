package org.jetbrains.kotlin.com.intellij.lang.folding;

import java.util.Collections;
import java.util.List;
import org.jetbrains.kotlin.com.intellij.lang.Language;
import org.jetbrains.kotlin.com.intellij.lang.LanguageExtension;
import org.jetbrains.kotlin.com.intellij.openapi.diagnostic.Logger;
import org.jetbrains.kotlin.com.intellij.openapi.extensions.ExtensionPointName;
import org.jetbrains.kotlin.com.intellij.util.KeyedLazyInstance;

/* JADX INFO: loaded from: classes3.dex */
public final class LanguageFolding extends LanguageExtension<FoldingBuilder> {
    public static final ExtensionPointName<KeyedLazyInstance<FoldingBuilder>> EP_NAME = ExtensionPointName.create("org.jetbrains.kotlin.com.intellij.lang.foldingBuilder");
    public static final LanguageFolding INSTANCE = new LanguageFolding();
    private static final Logger LOG = Logger.getInstance((Class<?>) LanguageFolding.class);

    /* JADX WARN: Code duplicated, block: B:8:0x000f  */
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str;
        int i2;
        if (i != 3 && i != 4 && i != 7) {
            switch (i) {
                case 10:
                case 11:
                case 12:
                case 13:
                case 14:
                    str = "@NotNull method %s.%s must not return null";
                    break;
                default:
                    str = "Argument for @NotNull parameter '%s' of %s.%s must not be null";
                    break;
            }
        } else {
            str = "@NotNull method %s.%s must not return null";
        }
        if (i != 3 && i != 4 && i != 7) {
            switch (i) {
                case 10:
                case 11:
                case 12:
                case 13:
                case 14:
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
                objArr[0] = "language";
                break;
            case 3:
            case 4:
            case 7:
            case 10:
            case 11:
            case 12:
            case 13:
            case 14:
                objArr[0] = "org/jetbrains/kotlin/com/intellij/lang/folding/LanguageFolding";
                break;
            case 5:
            case 8:
                objArr[0] = "root";
                break;
            case 6:
            case 9:
                objArr[0] = "document";
                break;
            default:
                objArr[0] = "l";
                break;
        }
        if (i == 3 || i == 4) {
            objArr[1] = "allForLanguage";
        } else if (i != 7) {
            switch (i) {
                case 10:
                case 11:
                case 12:
                case 13:
                case 14:
                    objArr[1] = "buildFoldingDescriptorsNoPlaceholderCaching";
                    break;
                default:
                    objArr[1] = "org/jetbrains/kotlin/com/intellij/lang/folding/LanguageFolding";
                    break;
            }
        } else {
            objArr[1] = "buildFoldingDescriptors";
        }
        switch (i) {
            case 1:
                objArr[2] = "findForLanguage";
                break;
            case 2:
                objArr[2] = "allForLanguage";
                break;
            case 3:
            case 4:
            case 7:
            case 10:
            case 11:
            case 12:
            case 13:
            case 14:
                break;
            case 5:
            case 6:
                objArr[2] = "buildFoldingDescriptors";
                break;
            case 8:
            case 9:
                objArr[2] = "buildFoldingDescriptorsNoPlaceholderCaching";
                break;
            default:
                objArr[2] = "forLanguage";
                break;
        }
        String str2 = String.format(str, objArr);
        if (i != 3 && i != 4 && i != 7) {
            switch (i) {
                case 10:
                case 11:
                case 12:
                case 13:
                case 14:
                    break;
                default:
                    throw new IllegalArgumentException(str2);
            }
        }
        throw new IllegalStateException(str2);
    }

    private LanguageFolding() {
        super(EP_NAME);
    }

    @Override // org.jetbrains.kotlin.com.intellij.lang.LanguageExtension
    public FoldingBuilder forLanguage(Language language) {
        if (language == null) {
            $$$reportNull$$$0(0);
        }
        return (FoldingBuilder) super.forLanguage(language);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // org.jetbrains.kotlin.com.intellij.lang.LanguageExtension
    public FoldingBuilder findForLanguage(Language language) {
        if (language == null) {
            $$$reportNull$$$0(1);
        }
        List<FoldingBuilder> listAllForLanguage = allForLanguage(language);
        if (listAllForLanguage.isEmpty()) {
            return null;
        }
        if (listAllForLanguage.size() == 1) {
            return listAllForLanguage.get(0);
        }
        return new CompositeFoldingBuilder(listAllForLanguage);
    }

    @Override // org.jetbrains.kotlin.com.intellij.lang.LanguageExtension
    public List<FoldingBuilder> allForLanguage(Language language) {
        if (language == null) {
            $$$reportNull$$$0(2);
        }
        while (language != null) {
            List<FoldingBuilder> listForKey = forKey(language);
            if (!listForKey.isEmpty()) {
                if (listForKey == null) {
                    $$$reportNull$$$0(3);
                }
                return listForKey;
            }
            language = language.getBaseLanguage();
        }
        List<FoldingBuilder> listEmptyList = Collections.emptyList();
        if (listEmptyList == null) {
            $$$reportNull$$$0(4);
        }
        return listEmptyList;
    }
}
