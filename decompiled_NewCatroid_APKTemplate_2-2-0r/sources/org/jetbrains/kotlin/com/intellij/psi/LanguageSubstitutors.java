package org.jetbrains.kotlin.com.intellij.psi;

import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicBoolean;
import org.jetbrains.kotlin.com.intellij.injected.editor.VirtualFileWindow;
import org.jetbrains.kotlin.com.intellij.lang.Language;
import org.jetbrains.kotlin.com.intellij.lang.LanguageExtension;
import org.jetbrains.kotlin.com.intellij.openapi.application.ApplicationManager;
import org.jetbrains.kotlin.com.intellij.openapi.components.Service;
import org.jetbrains.kotlin.com.intellij.openapi.diagnostic.Logger;
import org.jetbrains.kotlin.com.intellij.openapi.project.Project;
import org.jetbrains.kotlin.com.intellij.openapi.util.Key;
import org.jetbrains.kotlin.com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.kotlin.com.intellij.util.FileContentUtilCore;
import org.jetbrains.kotlin.com.intellij.util.ObjectUtils;

/* JADX INFO: loaded from: classes3.dex */
@Service
public final class LanguageSubstitutors extends LanguageExtension<LanguageSubstitutor> {

    @Deprecated
    public static final LanguageSubstitutors INSTANCE = getInstance();
    private static final Logger LOG = Logger.getInstance((Class<?>) LanguageSubstitutors.class);
    private static final Key<Key<String>> PROJECT_KEY_FOR_SUBSTITUTED_LANG_KEY = Key.create("PROJECT_KEY_FOR_SUBSTITUTED_LANG_KEY");
    private static final AtomicBoolean REQUESTS_DRAIN_NEEDED = new AtomicBoolean(true);
    private static final ConcurrentMap<VirtualFile, SubstitutionInfo> ourReparsingRequests = new ConcurrentHashMap();

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str;
        int i2;
        switch (i) {
            case 1:
            case 2:
            case 3:
            case 6:
            case 7:
            case 8:
            case 9:
            case 10:
            case 12:
            case 13:
            case 14:
            case 15:
                str = "Argument for @NotNull parameter '%s' of %s.%s must not be null";
                break;
            case 4:
            case 5:
            case 11:
            default:
                str = "@NotNull method %s.%s must not return null";
                break;
        }
        switch (i) {
            case 1:
            case 2:
            case 3:
            case 6:
            case 7:
            case 8:
            case 9:
            case 10:
            case 12:
            case 13:
            case 14:
            case 15:
                i2 = 3;
                break;
            case 4:
            case 5:
            case 11:
            default:
                i2 = 2;
                break;
        }
        Object[] objArr = new Object[i2];
        switch (i) {
            case 1:
            case 7:
                objArr[0] = "originalLang";
                break;
            case 2:
            case 6:
            case 12:
            case 15:
                objArr[0] = "file";
                break;
            case 3:
            case 9:
            case 10:
                objArr[0] = "project";
                break;
            case 4:
            case 5:
            case 11:
            default:
                objArr[0] = "org/jetbrains/kotlin/com/intellij/psi/LanguageSubstitutors";
                break;
            case 8:
            case 14:
                objArr[0] = "substitutedLang";
                break;
            case 13:
                objArr[0] = "prevLang";
                break;
        }
        switch (i) {
            case 1:
            case 2:
            case 3:
            case 6:
            case 7:
            case 8:
            case 9:
            case 10:
            case 12:
            case 13:
            case 14:
            case 15:
                objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/LanguageSubstitutors";
                break;
            case 4:
            case 5:
                objArr[1] = "substituteLanguage";
                break;
            case 11:
                objArr[1] = "getOrCreateProjectKey";
                break;
            default:
                objArr[1] = "getInstance";
                break;
        }
        switch (i) {
            case 1:
            case 2:
            case 3:
                objArr[2] = "substituteLanguage";
                break;
            case 6:
            case 7:
            case 8:
            case 9:
                objArr[2] = "processLanguageSubstitution";
                break;
            case 10:
                objArr[2] = "getOrCreateProjectKey";
                break;
            case 12:
            case 13:
            case 14:
                objArr[2] = "requestReparsing";
                break;
            case 15:
                objArr[2] = "cancelReparsing";
                break;
        }
        String str2 = String.format(str, objArr);
        switch (i) {
            case 1:
            case 2:
            case 3:
            case 6:
            case 7:
            case 8:
            case 9:
            case 10:
            case 12:
            case 13:
            case 14:
            case 15:
                throw new IllegalArgumentException(str2);
            case 4:
            case 5:
            case 11:
            default:
                throw new IllegalStateException(str2);
        }
    }

    public static LanguageSubstitutors getInstance() {
        LanguageSubstitutors languageSubstitutors = (LanguageSubstitutors) ApplicationManager.getApplication().getService(LanguageSubstitutors.class);
        if (languageSubstitutors == null) {
            $$$reportNull$$$0(0);
        }
        return languageSubstitutors;
    }

    private LanguageSubstitutors() {
        super("org.jetbrains.kotlin.com.intellij.lang.substitutor");
    }

    public Language substituteLanguage(Language language, VirtualFile virtualFile, Project project) {
        if (language == null) {
            $$$reportNull$$$0(1);
        }
        if (virtualFile == null) {
            $$$reportNull$$$0(2);
        }
        if (project == null) {
            $$$reportNull$$$0(3);
        }
        for (LanguageSubstitutor languageSubstitutor : forKey(language)) {
            Language language2 = languageSubstitutor.getLanguage(virtualFile, project);
            if (language2 != null) {
                if (language2 == Language.ANY) {
                    LOG.error("For " + language + " and " + virtualFile + ", " + languageSubstitutor.getClass().getName() + " returned Language.ANY, which is not allowed");
                } else {
                    Logger logger = LOG;
                    if (logger.isDebugEnabled()) {
                        logger.debug("For " + language + " and " + virtualFile + ", " + languageSubstitutor.getClass().getName() + " returned '" + language2 + "' of " + language2.getClass());
                    }
                    processLanguageSubstitution(virtualFile, language, language2, project);
                    if (language2 == null) {
                        $$$reportNull$$$0(4);
                    }
                    return language2;
                }
            }
        }
        if (language == null) {
            $$$reportNull$$$0(5);
        }
        return language;
    }

    private static void processLanguageSubstitution(VirtualFile virtualFile, Language language, Language language2, Project project) {
        if (virtualFile == null) {
            $$$reportNull$$$0(6);
        }
        if (language == null) {
            $$$reportNull$$$0(7);
        }
        if (language2 == null) {
            $$$reportNull$$$0(8);
        }
        if (project == null) {
            $$$reportNull$$$0(9);
        }
        if (virtualFile instanceof VirtualFileWindow) {
            return;
        }
        Key<String> orCreateProjectKey = getOrCreateProjectKey(project);
        Language languageFindLanguageByID = Language.findLanguageByID(orCreateProjectKey.get(virtualFile));
        Language language3 = (Language) ObjectUtils.notNull(languageFindLanguageByID, language);
        if (language3.is(language2)) {
            return;
        }
        if (!virtualFile.replace(orCreateProjectKey, languageFindLanguageByID != null ? languageFindLanguageByID.getID() : null, language2.getID()) || languageFindLanguageByID == null || ApplicationManager.getApplication().isUnitTestMode()) {
            return;
        }
        requestReparsing(virtualFile, language3, language2);
    }

    private static Key<String> getOrCreateProjectKey(Project project) {
        if (project == null) {
            $$$reportNull$$$0(10);
        }
        Key<Key<String>> key = PROJECT_KEY_FOR_SUBSTITUTED_LANG_KEY;
        Key<String> key2 = key.get(project);
        if (key2 == null) {
            synchronized (key) {
                key2 = key.get(project);
                if (key2 == null) {
                    key2 = new Key<>("Substituted lang key for " + project.getName());
                    key.set(project, key2);
                }
            }
        }
        if (key2 == null) {
            $$$reportNull$$$0(11);
        }
        return key2;
    }

    private static void requestReparsing(VirtualFile virtualFile, Language language, Language language2) {
        if (virtualFile == null) {
            $$$reportNull$$$0(12);
        }
        if (language == null) {
            $$$reportNull$$$0(13);
        }
        if (language2 == null) {
            $$$reportNull$$$0(14);
        }
        ourReparsingRequests.put(virtualFile, new SubstitutionInfo(language, language2));
        if (REQUESTS_DRAIN_NEEDED.compareAndSet(true, false)) {
            ApplicationManager.getApplication().invokeLater(new Runnable() { // from class: org.jetbrains.kotlin.com.intellij.psi.LanguageSubstitutors$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    LanguageSubstitutors.lambda$requestReparsing$0();
                }
            });
        }
    }

    static /* synthetic */ void lambda$requestReparsing$0() {
        int i = 1;
        REQUESTS_DRAIN_NEEDED.set(true);
        ArrayList<Map.Entry> arrayList = new ArrayList(ourReparsingRequests.entrySet());
        ArrayList arrayList2 = new ArrayList(arrayList.size());
        for (Map.Entry entry : arrayList) {
            VirtualFile virtualFile = (VirtualFile) entry.getKey();
            SubstitutionInfo substitutionInfo = (SubstitutionInfo) entry.getValue();
            ourReparsingRequests.remove(virtualFile);
            if (virtualFile.isValid()) {
                LOG.info("Reparsing " + virtualFile.getPath() + " because of language substitution " + substitutionInfo.myPrevLang.getID() + "->" + substitutionInfo.mySubstitutedLang.getID() + ", #" + i);
                arrayList2.add(virtualFile);
                i++;
            }
        }
        if (arrayList2.size() > 0) {
            FileContentUtilCore.reparseFiles(arrayList2);
        }
    }

    public static void cancelReparsing(VirtualFile virtualFile) {
        if (virtualFile == null) {
            $$$reportNull$$$0(15);
        }
        ourReparsingRequests.remove(virtualFile);
    }

    /* JADX INFO: Access modifiers changed from: private */
    static class SubstitutionInfo {
        private final Language myPrevLang;
        private final Language mySubstitutedLang;

        private static /* synthetic */ void $$$reportNull$$$0(int i) {
            Object[] objArr = new Object[3];
            if (i != 1) {
                objArr[0] = "prevLang";
            } else {
                objArr[0] = "substitutedLang";
            }
            objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/LanguageSubstitutors$SubstitutionInfo";
            objArr[2] = "<init>";
            throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
        }

        SubstitutionInfo(Language language, Language language2) {
            if (language == null) {
                $$$reportNull$$$0(0);
            }
            if (language2 == null) {
                $$$reportNull$$$0(1);
            }
            this.myPrevLang = language;
            this.mySubstitutedLang = language2;
        }
    }
}
