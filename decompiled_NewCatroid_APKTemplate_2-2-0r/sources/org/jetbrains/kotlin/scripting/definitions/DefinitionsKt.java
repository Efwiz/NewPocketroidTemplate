package org.jetbrains.kotlin.scripting.definitions;

import androidx.exifinterface.media.ExifInterface;
import kotlin.Deprecated;
import kotlin.Metadata;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import kotlin.script.experimental.api.SourceCode;
import kotlin.text.StringsKt;
import org.eclipse.jdt.internal.compiler.util.SuffixConstants;
import org.jetbrains.kotlin.com.intellij.injected.editor.VirtualFileWindow;
import org.jetbrains.kotlin.com.intellij.openapi.application.ApplicationManager;
import org.jetbrains.kotlin.com.intellij.openapi.diagnostic.Logger;
import org.jetbrains.kotlin.com.intellij.openapi.fileTypes.FileTypeRegistry;
import org.jetbrains.kotlin.com.intellij.openapi.project.Project;
import org.jetbrains.kotlin.com.intellij.openapi.util.Computable;
import org.jetbrains.kotlin.com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.kotlin.com.intellij.psi.PsiFile;
import org.jetbrains.kotlin.com.intellij.psi.PsiManager;
import org.jetbrains.kotlin.com.intellij.psi.PsiNamedElement;
import org.jetbrains.kotlin.idea.KotlinFileType;
import org.jetbrains.kotlin.parsing.KotlinParserDefinition;
import org.jetbrains.kotlin.psi.KtFile;
import org.jetbrains.kotlin.scripting.resolve.KtFileScriptSource;
import org.jetbrains.kotlin.scripting.resolve.VirtualFileScriptSource;
import org.jetbrains.kotlin.utils.PathUtil;

/* JADX INFO: compiled from: definitions.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000:\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\u001a\u0016\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u0007\u001a'\u0010\b\u001a\u0002H\t\"\u0004\b\u0000\u0010\t2\u000e\b\u0004\u0010\n\u001a\b\u0012\u0004\u0012\u0002H\t0\u000bH\u0086\bø\u0001\u0000¢\u0006\u0002\u0010\f\u001a\u0016\u0010\u0002\u001a\u0004\u0018\u00010\u0003*\u00020\r2\u0006\u0010\u0004\u001a\u00020\u0005H\u0007\u001a\f\u0010\u0002\u001a\u0004\u0018\u00010\u0003*\u00020\u000e\u001a\f\u0010\u000f\u001a\u00020\u0010*\u00020\rH\u0002\u001a\n\u0010\u0011\u001a\u00020\u0010*\u00020\r\u001a\u001b\u0010\u0012\u001a\u00020\u0010*\u00020\u000e\u0082\u0002\u000e\n\f\b\u0000\u0012\u0002\u0018\u0000\u001a\u0004\u0010\u0000(\u0000\"\u000e\u0010\u0000\u001a\u00020\u0001X\u0082T¢\u0006\u0002\n\u0000ò\u0001\u0004\n\u00020\u0013\u0082\u0002\u0007\n\u0005\b\u009920\u0001¨\u0006\u0014"}, d2 = {"JAVA_CLASS_FILE_TYPE_DOT_DEFAULT_EXTENSION", "", "findScriptDefinition", "Lorg/jetbrains/kotlin/scripting/definitions/ScriptDefinition;", "project", "Lorg/jetbrains/kotlin/com/intellij/openapi/project/Project;", "script", "Lkotlin/script/experimental/api/SourceCode;", "runReadAction", ExifInterface.GPS_DIRECTION_TRUE, "runnable", "Lkotlin/Function0;", "(Lkotlin/jvm/functions/Function0;)Ljava/lang/Object;", "Lorg/jetbrains/kotlin/com/intellij/openapi/vfs/VirtualFile;", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiFile;", "isKotlinFileType", "", "isNonScript", "isScript", "Lorg/jetbrains/kotlin/psi/KtFile;", PathUtil.KOTLIN_SCRIPTING_COMPILER_IMPL_NAME}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class DefinitionsKt {
    private static final String JAVA_CLASS_FILE_TYPE_DOT_DEFAULT_EXTENSION = ".class";

    public static final <T> T runReadAction(final Function0<? extends T> runnable) {
        Intrinsics.checkNotNullParameter(runnable, "runnable");
        return (T) ApplicationManager.getApplication().runReadAction(new Computable() { // from class: org.jetbrains.kotlin.scripting.definitions.DefinitionsKt.runReadAction.1
            @Override // org.jetbrains.kotlin.com.intellij.openapi.util.Computable
            public final T compute() {
                return runnable.invoke();
            }
        });
    }

    public static final boolean isScript(PsiFile psiFile) {
        Intrinsics.checkNotNullParameter(psiFile, "<this>");
        if (!(psiFile instanceof KtFile)) {
            return false;
        }
        KtFile ktFile = (KtFile) psiFile;
        VirtualFile virtualFile = ktFile.getVirtualFile();
        if (virtualFile == null) {
            virtualFile = ktFile.getOriginalFile().getVirtualFile();
        }
        if (virtualFile == null) {
            virtualFile = ktFile.getViewProvider().getVirtualFile();
            Intrinsics.checkNotNullExpressionValue(virtualFile, "getVirtualFile(...)");
        }
        return !isNonScript(virtualFile);
    }

    public static final ScriptDefinition findScriptDefinition(PsiFile psiFile) {
        Intrinsics.checkNotNullParameter(psiFile, "<this>");
        if (!isScript(psiFile)) {
            return null;
        }
        KtFile ktFile = (KtFile) psiFile;
        Project project = ktFile.getProject();
        Intrinsics.checkNotNullExpressionValue(project, "getProject(...)");
        return findScriptDefinition(project, new KtFileScriptSource(ktFile, null, 2, null));
    }

    @Deprecated(message = "Use PsiFile.findScriptDefinition() instead")
    public static final ScriptDefinition findScriptDefinition(final VirtualFile virtualFile, final Project project) {
        Intrinsics.checkNotNullParameter(virtualFile, "<this>");
        Intrinsics.checkNotNullParameter(project, "project");
        if (!virtualFile.isValid() || isNonScript(virtualFile) || ApplicationManager.getApplication().runReadAction(new Computable() { // from class: org.jetbrains.kotlin.scripting.definitions.DefinitionsKt$findScriptDefinition$$inlined$runReadAction$1
            @Override // org.jetbrains.kotlin.com.intellij.openapi.util.Computable
            public final T compute() {
                PsiNamedElement psiNamedElementFindFile = PsiManager.getInstance(project).findFile(virtualFile);
                if (psiNamedElementFindFile instanceof KtFile) {
                    return (T) ((KtFile) psiNamedElementFindFile);
                }
                return null;
            }
        }) == null) {
            return null;
        }
        return findScriptDefinition(project, new VirtualFileScriptSource(virtualFile, null, 2, null));
    }

    public static final ScriptDefinition findScriptDefinition(Project project, SourceCode script) {
        Intrinsics.checkNotNullParameter(project, "project");
        Intrinsics.checkNotNullParameter(script, "script");
        ScriptDefinitionProvider companion = ScriptDefinitionProvider.INSTANCE.getInstance(project);
        if (companion == null) {
            throw new IllegalStateException("Unable to get script definition: ScriptDefinitionProvider is not configured.".toString());
        }
        ScriptDefinition scriptDefinitionFindDefinition = companion.findDefinition(script);
        if (scriptDefinitionFindDefinition != null) {
            return scriptDefinitionFindDefinition;
        }
        ScriptDefinition defaultDefinition = companion.getDefaultDefinition();
        Logger.getInstance("org.jetbrains.kotlin.scripting.definitions").debug("Default definition is used for " + script.getLocationId());
        return defaultDefinition;
    }

    public static final boolean isNonScript(VirtualFile virtualFile) {
        Intrinsics.checkNotNullParameter(virtualFile, "<this>");
        if (virtualFile instanceof VirtualFileWindow) {
            if (!isKotlinFileType(virtualFile)) {
                return true;
            }
        } else {
            if (virtualFile.isDirectory()) {
                return true;
            }
            CharSequence name2 = virtualFile.getName();
            Intrinsics.checkNotNullExpressionValue(name2, "getNameSequence(...)");
            if (StringsKt.endsWith$default(name2, (CharSequence) KotlinFileType.DOT_DEFAULT_EXTENSION, false, 2, (Object) null) || StringsKt.endsWith$default(name2, (CharSequence) SuffixConstants.SUFFIX_STRING_java, false, 2, (Object) null) || StringsKt.endsWith$default(name2, (CharSequence) ".class", false, 2, (Object) null) || !isKotlinFileType(virtualFile)) {
                return true;
            }
        }
        return false;
    }

    private static final boolean isKotlinFileType(VirtualFile virtualFile) {
        CharSequence name2 = virtualFile.getName();
        Intrinsics.checkNotNullExpressionValue(name2, "getNameSequence(...)");
        if (StringsKt.endsWith$default(name2, (CharSequence) KotlinParserDefinition.STD_SCRIPT_EXT, false, 2, (Object) null)) {
            return true;
        }
        FileTypeRegistry fileTypeRegistry = FileTypeRegistry.getInstance();
        return Intrinsics.areEqual(fileTypeRegistry.getFileTypeByFile(virtualFile), KotlinFileType.INSTANCE) || Intrinsics.areEqual(fileTypeRegistry.getFileTypeByFileName(virtualFile.getName()), KotlinFileType.INSTANCE);
    }
}
