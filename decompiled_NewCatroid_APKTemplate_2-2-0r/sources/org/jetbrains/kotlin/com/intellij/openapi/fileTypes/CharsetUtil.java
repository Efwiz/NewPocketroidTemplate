package org.jetbrains.kotlin.com.intellij.openapi.fileTypes;

import java.nio.charset.Charset;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import org.jetbrains.kotlin.com.intellij.openapi.project.Project;
import org.jetbrains.kotlin.com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.kotlin.com.intellij.util.ReflectionUtil;

/* JADX INFO: loaded from: classes3.dex */
public final class CharsetUtil {
    private static final Map<String, Boolean> ourSupportsCharsetDetection = new ConcurrentHashMap();

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", "text", "org/jetbrains/kotlin/com/intellij/openapi/fileTypes/CharsetUtil", "extractCharsetFromFileContent"));
    }

    private static boolean overridesExtractCharsetFromContent(LanguageFileType languageFileType) {
        Class<?> cls = languageFileType.getClass();
        return (LanguageFileType.class.equals(ReflectionUtil.getMethodDeclaringClass(cls, "extractCharsetFromFileContent", Project.class, VirtualFile.class, String.class)) && LanguageFileType.class.equals(ReflectionUtil.getMethodDeclaringClass(cls, "extractCharsetFromFileContent", Project.class, VirtualFile.class, CharSequence.class))) ? false : true;
    }

    public static Charset extractCharsetFromFileContent(Project project, VirtualFile virtualFile, final FileType fileType, CharSequence charSequence) {
        if (charSequence == null) {
            $$$reportNull$$$0(0);
        }
        if ((fileType instanceof LanguageFileType) && ourSupportsCharsetDetection.computeIfAbsent(fileType.getName(), new Function() { // from class: org.jetbrains.kotlin.com.intellij.openapi.fileTypes.CharsetUtil$$ExternalSyntheticLambda0
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return Boolean.valueOf(CharsetUtil.overridesExtractCharsetFromContent((LanguageFileType) fileType));
            }
        }).booleanValue()) {
            return ((LanguageFileType) fileType).extractCharsetFromFileContent(project, virtualFile, charSequence);
        }
        return null;
    }
}
