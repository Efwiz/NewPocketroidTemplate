package org.jetbrains.kotlin.com.intellij.lang;

import com.reandroid.apk.ApkUtil;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;
import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;
import org.jetbrains.kotlin.com.intellij.lexer.Lexer;
import org.jetbrains.kotlin.com.intellij.openapi.application.ApplicationManager;
import org.jetbrains.kotlin.com.intellij.openapi.fileTypes.FileType;
import org.jetbrains.kotlin.com.intellij.openapi.fileTypes.LanguageFileType;
import org.jetbrains.kotlin.com.intellij.openapi.project.Project;
import org.jetbrains.kotlin.com.intellij.openapi.util.Key;
import org.jetbrains.kotlin.com.intellij.openapi.util.text.StringUtil;
import org.jetbrains.kotlin.com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.kotlin.com.intellij.psi.FileViewProvider;
import org.jetbrains.kotlin.com.intellij.psi.LanguageSubstitutor;
import org.jetbrains.kotlin.com.intellij.psi.LanguageSubstitutors;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiFile;
import org.jetbrains.kotlin.com.intellij.testFramework.LightVirtualFile;

/* JADX INFO: loaded from: classes3.dex */
public final class LanguageUtil {
    public static final Comparator<Language> LANGUAGE_COMPARATOR = new Comparator() { // from class: org.jetbrains.kotlin.com.intellij.lang.LanguageUtil$$ExternalSyntheticLambda0
        @Override // java.util.Comparator
        public final int compare(Object obj, Object obj2) {
            return StringUtil.naturalCompare(((Language) obj).getDisplayName(), ((Language) obj2).getDisplayName());
        }
    };
    private static final Key<Collection<MetaLanguage>> MATCHING_LANGUAGES = Key.create("language.matching");

    /* JADX WARN: Code duplicated, block: B:11:0x001f A[FALL_THROUGH] */
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str;
        int i2;
        if (i != 12 && i != 19 && i != 29 && i != 21 && i != 22) {
            switch (i) {
                default:
                    switch (i) {
                        case 24:
                        case 25:
                        case 26:
                            break;
                        default:
                            str = "Argument for @NotNull parameter '%s' of %s.%s must not be null";
                            break;
                    }
                case 6:
                case 7:
                case 8:
                case 9:
                case 10:
                    str = "@NotNull method %s.%s must not return null";
                    break;
            }
        } else {
            str = "@NotNull method %s.%s must not return null";
        }
        if (i != 12 && i != 19 && i != 29 && i != 21 && i != 22) {
            switch (i) {
                case 6:
                case 7:
                case 8:
                case 9:
                case 10:
                    i2 = 2;
                    break;
                default:
                    switch (i) {
                        case 24:
                        case 25:
                        case 26:
                            i2 = 2;
                            break;
                        default:
                            i2 = 3;
                            break;
                    }
                    break;
            }
        } else {
            i2 = 2;
        }
        Object[] objArr = new Object[i2];
        switch (i) {
            case 2:
                objArr[0] = "fileType";
                break;
            case 3:
                objArr[0] = "left";
                break;
            case 4:
                objArr[0] = "right";
                break;
            case 5:
                objArr[0] = "lexer";
                break;
            case 6:
            case 7:
            case 8:
            case 9:
            case 10:
            case 12:
            case 19:
            case 21:
            case 22:
            case 24:
            case 25:
            case 26:
            case 29:
                objArr[0] = "org/jetbrains/kotlin/com/intellij/lang/LanguageUtil";
                break;
            case 11:
            case 13:
                objArr[0] = ApkUtil.DEF_MODULE_NAME;
                break;
            case 14:
                objArr[0] = "result";
                break;
            case 15:
            case 20:
                objArr[0] = CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD;
                break;
            case 16:
            case 17:
            case 23:
            case 27:
            case 28:
                objArr[0] = "language";
                break;
            case 18:
                objArr[0] = "filter";
                break;
            default:
                objArr[0] = "project";
                break;
        }
        if (i == 12) {
            objArr[1] = "getAllDerivedLanguages";
        } else if (i == 19) {
            objArr[1] = "getLanguages";
        } else if (i == 29) {
            objArr[1] = "getBaseLanguages";
        } else if (i != 21 && i != 22) {
            switch (i) {
                case 6:
                case 7:
                case 8:
                case 9:
                case 10:
                    objArr[1] = "canStickTokensTogetherByLexer";
                    break;
                default:
                    switch (i) {
                        case 24:
                        case 25:
                        case 26:
                            objArr[1] = "matchingMetaLanguages";
                            break;
                        default:
                            objArr[1] = "org/jetbrains/kotlin/com/intellij/lang/LanguageUtil";
                            break;
                    }
                    break;
            }
        } else {
            objArr[1] = "getRootLanguage";
        }
        switch (i) {
            case 2:
                objArr[2] = "getFileTypeLanguage";
                break;
            case 3:
            case 4:
            case 5:
                objArr[2] = "canStickTokensTogetherByLexer";
                break;
            case 6:
            case 7:
            case 8:
            case 9:
            case 10:
            case 12:
            case 19:
            case 21:
            case 22:
            case 24:
            case 25:
            case 26:
            case 29:
                break;
            case 11:
            case 13:
            case 14:
                objArr[2] = "getAllDerivedLanguages";
                break;
            case 15:
                objArr[2] = "isInTemplateLanguageFile";
                break;
            case 16:
                objArr[2] = "isInjectableLanguage";
                break;
            case 17:
                objArr[2] = "isFileLanguage";
                break;
            case 18:
                objArr[2] = "getLanguages";
                break;
            case 20:
                objArr[2] = "getRootLanguage";
                break;
            case 23:
                objArr[2] = "matchingMetaLanguages";
                break;
            case 27:
                objArr[2] = "clearMatchingMetaLanguages";
                break;
            case 28:
                objArr[2] = "getBaseLanguages";
                break;
            default:
                objArr[2] = "getLanguageForPsi";
                break;
        }
        String str2 = String.format(str, objArr);
        if (i != 12 && i != 19 && i != 29 && i != 21 && i != 22) {
            switch (i) {
                case 6:
                case 7:
                case 8:
                case 9:
                case 10:
                    break;
                default:
                    switch (i) {
                        case 24:
                        case 25:
                        case 26:
                            break;
                        default:
                            throw new IllegalArgumentException(str2);
                    }
                    break;
            }
        }
        throw new IllegalStateException(str2);
    }

    public static Language getLanguageForPsi(Project project, VirtualFile virtualFile) {
        if (project == null) {
            $$$reportNull$$$0(0);
        }
        return getLanguageForPsi(project, virtualFile, null);
    }

    public static Language getLanguageForPsi(Project project, VirtualFile virtualFile, FileType fileType) {
        if (project == null) {
            $$$reportNull$$$0(1);
        }
        if (virtualFile == null) {
            return null;
        }
        Language language = virtualFile instanceof LightVirtualFile ? ((LightVirtualFile) virtualFile).getLanguage() : null;
        if (language == null) {
            if (fileType == null) {
                fileType = virtualFile.getFileType();
            }
            language = getFileTypeLanguage(fileType);
        }
        if (language == null) {
            return null;
        }
        Iterator<LanguageSubstitutor> it = LanguageSubstitutors.getInstance().forKey(Language.ANY).iterator();
        while (it.hasNext()) {
            Language language2 = it.next().getLanguage(virtualFile, project);
            if (language2 != null && language2 != Language.ANY) {
                language = language2;
                break;
            }
        }
        return LanguageSubstitutors.getInstance().substituteLanguage(language, virtualFile, project);
    }

    public static Language getFileTypeLanguage(FileType fileType) {
        if (fileType == null) {
            $$$reportNull$$$0(2);
        }
        if (fileType instanceof LanguageFileType) {
            return ((LanguageFileType) fileType).getLanguage();
        }
        return null;
    }

    public static ParserDefinition.SpaceRequirements canStickTokensTogetherByLexer(ASTNode aSTNode, ASTNode aSTNode2, Lexer lexer) {
        if (aSTNode == null) {
            $$$reportNull$$$0(3);
        }
        if (aSTNode2 == null) {
            $$$reportNull$$$0(4);
        }
        if (lexer == null) {
            $$$reportNull$$$0(5);
        }
        String str = aSTNode.getText() + aSTNode2.getText();
        lexer.start(str, 0, str.length());
        if (lexer.getTokenType() != aSTNode.getElementType()) {
            ParserDefinition.SpaceRequirements spaceRequirements = ParserDefinition.SpaceRequirements.MUST;
            if (spaceRequirements == null) {
                $$$reportNull$$$0(6);
            }
            return spaceRequirements;
        }
        if (lexer.getTokenEnd() != aSTNode.getTextLength()) {
            ParserDefinition.SpaceRequirements spaceRequirements2 = ParserDefinition.SpaceRequirements.MUST;
            if (spaceRequirements2 == null) {
                $$$reportNull$$$0(7);
            }
            return spaceRequirements2;
        }
        lexer.advance();
        if (lexer.getTokenEnd() != str.length()) {
            ParserDefinition.SpaceRequirements spaceRequirements3 = ParserDefinition.SpaceRequirements.MUST;
            if (spaceRequirements3 == null) {
                $$$reportNull$$$0(8);
            }
            return spaceRequirements3;
        }
        if (lexer.getTokenType() != aSTNode2.getElementType()) {
            ParserDefinition.SpaceRequirements spaceRequirements4 = ParserDefinition.SpaceRequirements.MUST;
            if (spaceRequirements4 == null) {
                $$$reportNull$$$0(9);
            }
            return spaceRequirements4;
        }
        ParserDefinition.SpaceRequirements spaceRequirements5 = ParserDefinition.SpaceRequirements.MAY;
        if (spaceRequirements5 == null) {
            $$$reportNull$$$0(10);
        }
        return spaceRequirements5;
    }

    public static Set<Language> getAllDerivedLanguages(Language language) {
        if (language == null) {
            $$$reportNull$$$0(11);
        }
        HashSet hashSet = new HashSet();
        getAllDerivedLanguages(language, hashSet);
        return hashSet;
    }

    private static void getAllDerivedLanguages(Language language, Set<? super Language> set) {
        if (language == null) {
            $$$reportNull$$$0(13);
        }
        if (set == null) {
            $$$reportNull$$$0(14);
        }
        set.add(language);
        Iterator<Language> it = language.getDialects().iterator();
        while (it.hasNext()) {
            getAllDerivedLanguages(it.next(), set);
        }
    }

    public static Language getRootLanguage(PsiElement psiElement) {
        if (psiElement == null) {
            $$$reportNull$$$0(20);
        }
        PsiFile containingFile = psiElement.getContainingFile();
        FileViewProvider viewProvider = containingFile.getViewProvider();
        Set<Language> languages = viewProvider.getLanguages();
        if (languages.size() > 1) {
            Language language = containingFile.getLanguage();
            if (languages.contains(language)) {
                if (language == null) {
                    $$$reportNull$$$0(21);
                }
                return language;
            }
        }
        Language baseLanguage = viewProvider.getBaseLanguage();
        if (baseLanguage == null) {
            $$$reportNull$$$0(22);
        }
        return baseLanguage;
    }

    static Collection<MetaLanguage> matchingMetaLanguages(final Language language) {
        final Set hashSet;
        if (language == null) {
            $$$reportNull$$$0(23);
        }
        Key<Collection<MetaLanguage>> key = MATCHING_LANGUAGES;
        Collection<MetaLanguage> collection = (Collection) language.getUserData(key);
        if (collection != null) {
            if (collection == null) {
                $$$reportNull$$$0(24);
            }
            return collection;
        }
        if (!ApplicationManager.getApplication().getExtensionArea().hasExtensionPoint(MetaLanguage.EP_NAME)) {
            List listEmptyList = Collections.emptyList();
            if (listEmptyList == null) {
                $$$reportNull$$$0(25);
            }
            return listEmptyList;
        }
        if (language instanceof MetaLanguage) {
            hashSet = Collections.emptySet();
        } else {
            hashSet = new HashSet();
            MetaLanguage.EP_NAME.forEachExtensionSafe(new Consumer() { // from class: org.jetbrains.kotlin.com.intellij.lang.LanguageUtil$$ExternalSyntheticLambda1
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    LanguageUtil.lambda$matchingMetaLanguages$3(language, hashSet, (MetaLanguage) obj);
                }
            });
        }
        Collection<MetaLanguage> collection2 = (Collection) language.putUserDataIfAbsent(key, hashSet);
        if (collection2 == null) {
            $$$reportNull$$$0(26);
        }
        return collection2;
    }

    static /* synthetic */ void lambda$matchingMetaLanguages$3(Language language, Set set, MetaLanguage metaLanguage) {
        if (metaLanguage.matchesLanguage(language)) {
            set.add(metaLanguage);
        }
    }
}
