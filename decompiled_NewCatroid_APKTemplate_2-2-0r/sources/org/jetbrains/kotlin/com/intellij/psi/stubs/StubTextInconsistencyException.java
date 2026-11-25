package org.jetbrains.kotlin.com.intellij.psi.stubs;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import org.jetbrains.kotlin.com.intellij.openapi.diagnostic.Attachment;
import org.jetbrains.kotlin.com.intellij.openapi.diagnostic.ExceptionWithAttachments;
import org.jetbrains.kotlin.com.intellij.openapi.project.Project;
import org.jetbrains.kotlin.com.intellij.openapi.util.Pair;
import org.jetbrains.kotlin.com.intellij.openapi.util.text.StringUtil;
import org.jetbrains.kotlin.com.intellij.psi.FileViewProvider;
import org.jetbrains.kotlin.com.intellij.psi.PsiFile;
import org.jetbrains.kotlin.com.intellij.psi.impl.DebugUtil;
import org.jetbrains.kotlin.com.intellij.psi.impl.FreeThreadedFileViewProvider;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.PsiFileImpl;
import org.jetbrains.kotlin.com.intellij.psi.tree.IStubFileElementType;
import org.jetbrains.kotlin.com.intellij.psi.util.PsiUtilCore;
import org.jetbrains.kotlin.com.intellij.testFramework.LightVirtualFile;
import org.jetbrains.kotlin.com.intellij.util.Function;
import org.jetbrains.kotlin.com.intellij.util.containers.ContainerUtil;
import org.jetbrains.kotlin.com.intellij.util.indexing.FileContentImpl;

/* JADX INFO: loaded from: classes3.dex */
public final class StubTextInconsistencyException extends RuntimeException implements ExceptionWithAttachments {
    private final String myFileName;
    private final String myFileText;
    private final String myStubsFromPsi;
    private final String myStubsFromText;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = i != 3 ? "@NotNull method %s.%s must not return null" : "Argument for @NotNull parameter '%s' of %s.%s must not be null";
        Object[] objArr = new Object[i != 3 ? 2 : 3];
        if (i != 3) {
            objArr[0] = "org/jetbrains/kotlin/com/intellij/psi/stubs/StubTextInconsistencyException";
        } else {
            objArr[0] = "file";
        }
        if (i == 1) {
            objArr[1] = "getStubsFromPsi";
        } else if (i == 2) {
            objArr[1] = "getAttachments";
        } else if (i == 3) {
            objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/stubs/StubTextInconsistencyException";
        } else if (i != 4) {
            objArr[1] = "getStubsFromText";
        } else {
            objArr[1] = "restoreStubsFromText";
        }
        if (i == 3) {
            objArr[2] = "checkStubTextConsistency";
        }
        String str2 = String.format(str, objArr);
        if (i == 3) {
            throw new IllegalArgumentException(str2);
        }
    }

    private StubTextInconsistencyException(String str, PsiFile psiFile, List<PsiFileStub> list, List<PsiFileStub> list2) {
        super(str);
        this.myStubsFromText = StringUtil.join((Collection) list, new Function() { // from class: org.jetbrains.kotlin.com.intellij.psi.stubs.StubTextInconsistencyException$$ExternalSyntheticLambda3
            @Override // org.jetbrains.kotlin.com.intellij.util.Function
            public final Object fun(Object obj) {
                return DebugUtil.stubTreeToString((PsiFileStub) obj);
            }
        }, "\n");
        this.myStubsFromPsi = StringUtil.join((Collection) list2, new Function() { // from class: org.jetbrains.kotlin.com.intellij.psi.stubs.StubTextInconsistencyException$$ExternalSyntheticLambda3
            @Override // org.jetbrains.kotlin.com.intellij.util.Function
            public final Object fun(Object obj) {
                return DebugUtil.stubTreeToString((PsiFileStub) obj);
            }
        }, "\n");
        this.myFileName = psiFile.getName();
        this.myFileText = psiFile.getText();
    }

    public String getStubsFromText() {
        String str = this.myStubsFromText;
        if (str == null) {
            $$$reportNull$$$0(0);
        }
        return str;
    }

    public String getStubsFromPsi() {
        String str = this.myStubsFromPsi;
        if (str == null) {
            $$$reportNull$$$0(1);
        }
        return str;
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.diagnostic.ExceptionWithAttachments
    public Attachment[] getAttachments() {
        return new Attachment[]{new Attachment(this.myFileName, this.myFileText), new Attachment("stubsRestoredFromText.txt", this.myStubsFromText), new Attachment("stubsFromExistingPsi.txt", this.myStubsFromPsi)};
    }

    public static void checkStubTextConsistency(PsiFile psiFile) throws StubTextInconsistencyException {
        IStubFileElementType<?> elementTypeForStubBuilder;
        if (psiFile == null) {
            $$$reportNull$$$0(3);
        }
        PsiUtilCore.ensureValid(psiFile);
        FileViewProvider viewProvider = psiFile.getViewProvider();
        if ((viewProvider instanceof FreeThreadedFileViewProvider) || (viewProvider.getVirtualFile() instanceof LightVirtualFile)) {
            return;
        }
        PsiFile stubBindingRoot = viewProvider.getStubBindingRoot();
        if ((stubBindingRoot instanceof PsiFileImpl) && (elementTypeForStubBuilder = ((PsiFileImpl) stubBindingRoot).getElementTypeForStubBuilder()) != null && elementTypeForStubBuilder.shouldBuildStubFor(viewProvider.getVirtualFile())) {
            List<PsiFileStub> listRestoreStubsFromText = restoreStubsFromText(viewProvider);
            List map = ContainerUtil.map((Collection) StubTreeBuilder.getStubbedRoots(viewProvider), new Function() { // from class: org.jetbrains.kotlin.com.intellij.psi.stubs.StubTextInconsistencyException$$ExternalSyntheticLambda0
                @Override // org.jetbrains.kotlin.com.intellij.util.Function
                public final Object fun(Object obj) {
                    return ((PsiFileImpl) ((Pair) obj).getSecond()).calcStubTree().getRoot();
                }
            });
            if (map.size() != listRestoreStubsFromText.size()) {
                throw new StubTextInconsistencyException("Inconsistent stub roots: PSI says it's " + ContainerUtil.map((Collection) map, new Function() { // from class: org.jetbrains.kotlin.com.intellij.psi.stubs.StubTextInconsistencyException$$ExternalSyntheticLambda1
                    @Override // org.jetbrains.kotlin.com.intellij.util.Function
                    public final Object fun(Object obj) {
                        return ((PsiFileStub) obj).getType();
                    }
                }) + " but re-parsing the text gives " + ContainerUtil.map((Collection) listRestoreStubsFromText, new Function() { // from class: org.jetbrains.kotlin.com.intellij.psi.stubs.StubTextInconsistencyException$$ExternalSyntheticLambda2
                    @Override // org.jetbrains.kotlin.com.intellij.util.Function
                    public final Object fun(Object obj) {
                        return ((PsiFileStub) obj).getType();
                    }
                }), psiFile, listRestoreStubsFromText, map);
            }
            for (int i = 0; i < map.size(); i++) {
                if (!DebugUtil.stubTreeToString((PsiFileStub) map.get(i)).equals(DebugUtil.stubTreeToString(listRestoreStubsFromText.get(i)))) {
                    throw new StubTextInconsistencyException("Stub is inconsistent with text in " + psiFile.getLanguage(), psiFile, listRestoreStubsFromText, map);
                }
            }
        }
    }

    private static List<PsiFileStub> restoreStubsFromText(FileViewProvider fileViewProvider) {
        Project project = fileViewProvider.getManager().getProject();
        FileContentImpl fileContentImpl = (FileContentImpl) FileContentImpl.createByText(fileViewProvider.getVirtualFile(), fileViewProvider.getContents(), project);
        fileContentImpl.setProject(project);
        PsiFileStubImpl psiFileStubImpl = (PsiFileStubImpl) StubTreeBuilder.buildStubTree(fileContentImpl);
        List<PsiFileStub> listEmptyList = psiFileStubImpl == null ? Collections.emptyList() : Arrays.asList(psiFileStubImpl.getStubRoots());
        if (listEmptyList == null) {
            $$$reportNull$$$0(4);
        }
        return listEmptyList;
    }
}
