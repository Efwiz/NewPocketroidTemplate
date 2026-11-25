package org.jetbrains.kotlin.com.intellij.psi.stubs;

import com.huawei.hms.framework.common.hianalytics.CrashHianalyticsData;
import com.huawei.hms.ml.language.common.utils.Constant;
import java.util.ArrayList;
import java.util.Collection;
import org.catrobat.catroid.common.Constants;
import org.jetbrains.kotlin.com.intellij.lang.Language;
import org.jetbrains.kotlin.com.intellij.openapi.application.ApplicationManager;
import org.jetbrains.kotlin.com.intellij.openapi.diagnostic.Attachment;
import org.jetbrains.kotlin.com.intellij.openapi.diagnostic.RuntimeExceptionWithAttachments;
import org.jetbrains.kotlin.com.intellij.openapi.editor.Document;
import org.jetbrains.kotlin.com.intellij.openapi.fileEditor.FileDocumentManager;
import org.jetbrains.kotlin.com.intellij.openapi.progress.ProgressManager;
import org.jetbrains.kotlin.com.intellij.openapi.project.Project;
import org.jetbrains.kotlin.com.intellij.openapi.util.Pair;
import org.jetbrains.kotlin.com.intellij.openapi.util.ThrowableComputable;
import org.jetbrains.kotlin.com.intellij.openapi.util.text.StringUtil;
import org.jetbrains.kotlin.com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.kotlin.com.intellij.psi.FileViewProvider;
import org.jetbrains.kotlin.com.intellij.psi.PsiCompiledElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiDocumentManager;
import org.jetbrains.kotlin.com.intellij.psi.PsiFile;
import org.jetbrains.kotlin.com.intellij.psi.PsiFileFactory;
import org.jetbrains.kotlin.com.intellij.psi.impl.DebugUtil;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.PsiFileWithStubSupport;
import org.jetbrains.kotlin.com.intellij.psi.tree.IStubFileElementType;
import org.jetbrains.kotlin.com.intellij.util.Function;

/* JADX INFO: loaded from: classes3.dex */
public abstract class StubTreeLoader {
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = (i == 4 || i == 8) ? "@NotNull method %s.%s must not return null" : "Argument for @NotNull parameter '%s' of %s.%s must not be null";
        Object[] objArr = new Object[(i == 4 || i == 8) ? 2 : 3];
        switch (i) {
            case 1:
            case 2:
                objArr[0] = "file";
                break;
            case 3:
            case 7:
                objArr[0] = "psiFile";
                break;
            case 4:
            case 8:
                objArr[0] = "org/jetbrains/kotlin/com/intellij/psi/stubs/StubTreeLoader";
                break;
            case 5:
            case 6:
                objArr[0] = CrashHianalyticsData.MESSAGE;
                break;
            case 9:
                objArr[0] = Constants.EXTRA_PROVIDER;
                break;
            default:
                objArr[0] = "virtualFile";
                break;
        }
        if (i == 4) {
            objArr[1] = "stubTreeAndIndexDoNotMatch";
        } else if (i != 8) {
            objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/stubs/StubTreeLoader";
        } else {
            objArr[1] = "createAttachments";
        }
        switch (i) {
            case 1:
                objArr[2] = "getIndexingStampInfo";
                break;
            case 2:
                objArr[2] = "isTooLarge";
                break;
            case 3:
                objArr[2] = "stubTreeAndIndexDoNotMatch";
                break;
            case 4:
            case 8:
                break;
            case 5:
                objArr[2] = "handleUpToDateMismatch";
                break;
            case 6:
                objArr[2] = "handleManyProjectsMismatch";
                break;
            case 7:
                objArr[2] = "createAttachments";
                break;
            case 9:
                objArr[2] = "getFileViewProviderMismatchDiagnostics";
                break;
            default:
                objArr[2] = "hasPsiInManyProjects";
                break;
        }
        String str2 = String.format(str, objArr);
        if (i != 4 && i != 8) {
            throw new IllegalArgumentException(str2);
        }
        throw new IllegalStateException(str2);
    }

    public abstract ObjectStubTree<?> build(Project project, VirtualFile virtualFile, PsiFile psiFile);

    public abstract boolean canHaveStub(VirtualFile virtualFile);

    protected IndexingStampInfo getIndexingStampInfo(VirtualFile virtualFile) {
        if (virtualFile != null) {
            return null;
        }
        $$$reportNull$$$0(1);
        return null;
    }

    protected boolean hasPsiInManyProjects(VirtualFile virtualFile) {
        if (virtualFile == null) {
            $$$reportNull$$$0(0);
        }
        return false;
    }

    protected boolean isTooLarge(VirtualFile virtualFile) {
        if (virtualFile != null) {
            return false;
        }
        $$$reportNull$$$0(2);
        return false;
    }

    public abstract ObjectStubTree<?> readFromVFile(Project project, VirtualFile virtualFile);

    public abstract ObjectStubTree<?> readOrBuild(Project project, VirtualFile virtualFile, PsiFile psiFile);

    public abstract void rebuildStubTree(VirtualFile virtualFile);

    public static StubTreeLoader getInstance() {
        return (StubTreeLoader) ApplicationManager.getApplication().getService(StubTreeLoader.class);
    }

    public RuntimeException stubTreeAndIndexDoNotMatch(final ObjectStubTree objectStubTree, final PsiFileWithStubSupport psiFileWithStubSupport, final Throwable th) {
        if (psiFileWithStubSupport == null) {
            $$$reportNull$$$0(3);
        }
        RuntimeException runtimeException = (RuntimeException) ProgressManager.getInstance().computeInNonCancelableSection(new ThrowableComputable() { // from class: org.jetbrains.kotlin.com.intellij.psi.stubs.StubTreeLoader$$ExternalSyntheticLambda0
            @Override // org.jetbrains.kotlin.com.intellij.openapi.util.ThrowableComputable
            public final Object compute() {
                return this.f$0.m6697x71d60b43(psiFileWithStubSupport, objectStubTree, th);
            }
        });
        if (runtimeException == null) {
            $$$reportNull$$$0(4);
        }
        return runtimeException;
    }

    /* JADX INFO: renamed from: lambda$stubTreeAndIndexDoNotMatch$0$org-jetbrains-kotlin-com-intellij-psi-stubs-StubTreeLoader, reason: not valid java name */
    /* synthetic */ RuntimeExceptionWithAttachments m6697x71d60b43(PsiFileWithStubSupport psiFileWithStubSupport, ObjectStubTree objectStubTree, Throwable th) throws RuntimeException {
        String str;
        VirtualFile virtualFile = psiFileWithStubSupport.getViewProvider().getVirtualFile();
        StubTree stubTree = (StubTree) readFromVFile(psiFileWithStubSupport.getProject(), virtualFile);
        boolean z = psiFileWithStubSupport instanceof PsiCompiledElement;
        Document document = z ? null : FileDocumentManager.getInstance().getDocument(virtualFile);
        IndexingStampInfo indexingStampInfo = getIndexingStampInfo(virtualFile);
        boolean z2 = indexingStampInfo != null && indexingStampInfo.isUpToDate(document, virtualFile, psiFileWithStubSupport);
        if (!z2) {
            str = "PSI and index do not match.\nPlease report the problem to JetBrains with the files attached\n";
        } else {
            str = "PSI and index do not match.\nPlease report the problem to JetBrains with the files attached\nINDEXED VERSION IS THE CURRENT ONE";
        }
        String str2 = (((str + " file=" + psiFileWithStubSupport) + ", file.class=" + psiFileWithStubSupport.getClass()) + ", file.lang=" + psiFileWithStubSupport.getLanguage()) + ", modStamp=" + psiFileWithStubSupport.getModificationStamp();
        if (!z) {
            PsiFile psiFileCreateFileFromText = PsiFileFactory.getInstance(psiFileWithStubSupport.getProject()).createFileFromText(psiFileWithStubSupport.getMyName(), psiFileWithStubSupport.getFileType(), psiFileWithStubSupport.getText());
            if (psiFileCreateFileFromText.getLanguage().equals(psiFileWithStubSupport.getLanguage())) {
                str2 = DebugUtil.psiToString(psiFileWithStubSupport, false).equals(DebugUtil.psiToString(psiFileCreateFileFromText, false)) ? str2 + "\n tree consistent" : str2 + "\n AST INCONSISTENT, perhaps after incremental reparse; " + psiFileCreateFileFromText;
            }
        }
        if (objectStubTree != null) {
            str2 = str2 + "\n stub debugInfo=" + objectStubTree.getDebugInfo();
        }
        String str3 = str2 + "\nlatestIndexedStub=" + stubTree;
        if (stubTree != null) {
            if (objectStubTree != null) {
                str3 = str3 + "\n   same size=" + (objectStubTree.getPlainList().size() == stubTree.getPlainList().size());
            }
            str3 = str3 + "\n   debugInfo=" + stubTree.getDebugInfo();
        }
        FileViewProvider viewProvider = psiFileWithStubSupport.getViewProvider();
        String str4 = ((((str3 + "\n viewProvider=" + viewProvider) + "\n viewProvider stamp: " + viewProvider.getModificationStamp()) + "; file stamp: " + virtualFile.getModificationStamp()) + "; file modCount: " + virtualFile.getModificationCount()) + "; file length: " + virtualFile.getLength();
        if (document != null) {
            str4 = (((str4 + "\n doc saved: " + (true ^ FileDocumentManager.getInstance().isDocumentUnsaved(document))) + "; doc stamp: " + document.getModificationStamp()) + "; doc size: " + document.getTextLength()) + "; committed: " + PsiDocumentManager.getInstance(psiFileWithStubSupport.getProject()).isCommitted(document);
        }
        String str5 = str4 + "\nindexing info: " + indexingStampInfo;
        Attachment[] attachmentArrCreateAttachments = createAttachments(objectStubTree, psiFileWithStubSupport, virtualFile, stubTree);
        if (hasPsiInManyProjects(virtualFile)) {
            return handleManyProjectsMismatch(str5, attachmentArrCreateAttachments, th);
        }
        if (z2) {
            return handleUpToDateMismatch(str5, attachmentArrCreateAttachments, th);
        }
        return new RuntimeExceptionWithAttachments(str5, th, attachmentArrCreateAttachments);
    }

    private static RuntimeExceptionWithAttachments handleUpToDateMismatch(String str, Attachment[] attachmentArr, Throwable th) {
        if (str == null) {
            $$$reportNull$$$0(5);
        }
        return new UpToDateStubIndexMismatch(str, th, attachmentArr);
    }

    private static RuntimeExceptionWithAttachments handleManyProjectsMismatch(String str, Attachment[] attachmentArr, Throwable th) {
        if (str == null) {
            $$$reportNull$$$0(6);
        }
        return new ManyProjectsStubIndexMismatch(str, th, attachmentArr);
    }

    private static Attachment[] createAttachments(ObjectStubTree objectStubTree, PsiFileWithStubSupport psiFileWithStubSupport, VirtualFile virtualFile, StubTree stubTree) {
        if (psiFileWithStubSupport == null) {
            $$$reportNull$$$0(7);
        }
        ArrayList arrayList = new ArrayList();
        arrayList.add(new Attachment(virtualFile.getPath() + "_file.txt", psiFileWithStubSupport instanceof PsiCompiledElement ? "compiled" : psiFileWithStubSupport.getText()));
        if (objectStubTree != null) {
            arrayList.add(new Attachment("stubTree.txt", ((PsiFileStubImpl) objectStubTree.getRoot()).printTree()));
        }
        if (stubTree != null) {
            arrayList.add(new Attachment("stubTreeFromIndex.txt", ((PsiFileStubImpl) stubTree.getRoot()).printTree()));
        }
        Attachment[] attachmentArr = (Attachment[]) arrayList.toArray(Attachment.EMPTY_ARRAY);
        if (attachmentArr == null) {
            $$$reportNull$$$0(8);
        }
        return attachmentArr;
    }

    public static String getFileViewProviderMismatchDiagnostics(FileViewProvider fileViewProvider) {
        if (fileViewProvider == null) {
            $$$reportNull$$$0(9);
        }
        final Function function = new Function() { // from class: org.jetbrains.kotlin.com.intellij.psi.stubs.StubTreeLoader$$ExternalSyntheticLambda1
            @Override // org.jetbrains.kotlin.com.intellij.util.Function
            public final Object fun(Object obj) {
                return ((PsiFile) obj).getClass().getSimpleName();
            }
        };
        return ", stubBindingRoot = " + ((String) function.fun(fileViewProvider.getStubBindingRoot())) + ", languages = [" + StringUtil.join((Collection) fileViewProvider.getLanguages(), new Function() { // from class: org.jetbrains.kotlin.com.intellij.psi.stubs.StubTreeLoader$$ExternalSyntheticLambda3
            @Override // org.jetbrains.kotlin.com.intellij.util.Function
            public final Object fun(Object obj) {
                return ((Language) obj).getID();
            }
        }, ", ") + "], fileTypes = [" + StringUtil.join((Collection) fileViewProvider.getAllFiles(), new Function() { // from class: org.jetbrains.kotlin.com.intellij.psi.stubs.StubTreeLoader$$ExternalSyntheticLambda4
            @Override // org.jetbrains.kotlin.com.intellij.util.Function
            public final Object fun(Object obj) {
                return ((PsiFile) obj).getFileType().getName();
            }
        }, ", ") + "], files = [" + StringUtil.join((Collection) fileViewProvider.getAllFiles(), function, ", ") + "], roots = [" + StringUtil.join((Collection) StubTreeBuilder.getStubbedRoots(fileViewProvider), new Function() { // from class: org.jetbrains.kotlin.com.intellij.psi.stubs.StubTreeLoader$$ExternalSyntheticLambda2
            @Override // org.jetbrains.kotlin.com.intellij.util.Function
            public final Object fun(Object obj) {
                return StubTreeLoader.lambda$getFileViewProviderMismatchDiagnostics$2(function, (Pair) obj);
            }
        }, ", ") + "], indexingInfo = " + getInstance().getIndexingStampInfo(fileViewProvider.getVirtualFile()) + ", isTooLarge = " + getInstance().isTooLarge(fileViewProvider.getVirtualFile());
    }

    /* JADX WARN: Multi-variable type inference failed */
    static /* synthetic */ String lambda$getFileViewProviderMismatchDiagnostics$2(Function function, Pair pair) {
        return "(" + ((IStubFileElementType) pair.first).toString() + ", " + ((IStubFileElementType) pair.first).getLanguage() + " -> " + ((String) function.fun((PsiFile) pair.second)) + Constant.AFTER_QUTO;
    }
}
