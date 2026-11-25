package org.jetbrains.kotlin.com.intellij.psi.stubs;

import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import java.io.IOException;
import org.jetbrains.kotlin.com.intellij.lang.LanguageParserDefinitions;
import org.jetbrains.kotlin.com.intellij.lang.ParserDefinition;
import org.jetbrains.kotlin.com.intellij.openapi.fileTypes.FileType;
import org.jetbrains.kotlin.com.intellij.openapi.fileTypes.LanguageFileType;
import org.jetbrains.kotlin.com.intellij.openapi.project.Project;
import org.jetbrains.kotlin.com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.kotlin.com.intellij.psi.PsiFile;
import org.jetbrains.kotlin.com.intellij.psi.tree.IFileElementType;
import org.jetbrains.kotlin.com.intellij.psi.tree.IStubFileElementType;
import org.jetbrains.kotlin.com.intellij.util.indexing.FileContentImpl;

/* JADX INFO: loaded from: classes3.dex */
public class CoreStubTreeLoader extends StubTreeLoader {
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        Object[] objArr = new Object[3];
        if (i == 1 || i == 2 || i == 4) {
            objArr[0] = "vFile";
        } else {
            objArr[0] = "project";
        }
        objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/stubs/CoreStubTreeLoader";
        if (i == 2) {
            objArr[2] = JsonPOJOBuilder.DEFAULT_BUILD_METHOD;
        } else if (i == 3 || i == 4) {
            objArr[2] = "readFromVFile";
        } else {
            objArr[2] = "readOrBuild";
        }
        throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.stubs.StubTreeLoader
    public ObjectStubTree<?> readFromVFile(Project project, VirtualFile virtualFile) {
        if (project == null) {
            $$$reportNull$$$0(3);
        }
        if (virtualFile != null) {
            return null;
        }
        $$$reportNull$$$0(4);
        return null;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.stubs.StubTreeLoader
    public void rebuildStubTree(VirtualFile virtualFile) {
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.stubs.StubTreeLoader
    public ObjectStubTree<?> readOrBuild(Project project, VirtualFile virtualFile, PsiFile psiFile) {
        if (project == null) {
            $$$reportNull$$$0(0);
        }
        if (virtualFile == null) {
            $$$reportNull$$$0(1);
        }
        if (canHaveStub(virtualFile)) {
            return build(project, virtualFile, psiFile);
        }
        return null;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.stubs.StubTreeLoader
    public ObjectStubTree<?> build(Project project, VirtualFile virtualFile, PsiFile psiFile) {
        if (virtualFile == null) {
            $$$reportNull$$$0(2);
        }
        try {
            Stub stubBuildStubTree = StubTreeBuilder.buildStubTree(FileContentImpl.createByFile(virtualFile, project));
            if (stubBuildStubTree instanceof PsiFileStub) {
                return new StubTree((PsiFileStub) stubBuildStubTree);
            }
            return null;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.stubs.StubTreeLoader
    public boolean canHaveStub(VirtualFile virtualFile) {
        FileType fileType = virtualFile.getFileType();
        if (fileType instanceof LanguageFileType) {
            ParserDefinition parserDefinitionForLanguage = LanguageParserDefinitions.INSTANCE.forLanguage(((LanguageFileType) fileType).getLanguage());
            if (parserDefinitionForLanguage == null) {
                return false;
            }
            IFileElementType fileNodeType = parserDefinitionForLanguage.getFileNodeType();
            return (fileNodeType instanceof IStubFileElementType) && ((IStubFileElementType) fileNodeType).shouldBuildStubFor(virtualFile);
        }
        if (!fileType.isBinary()) {
            return false;
        }
        BinaryFileStubBuilder binaryFileStubBuilderForFileType = BinaryFileStubBuilders.INSTANCE.forFileType(fileType);
        return binaryFileStubBuilderForFileType != null && binaryFileStubBuilderForFileType.acceptsFile(virtualFile);
    }
}
