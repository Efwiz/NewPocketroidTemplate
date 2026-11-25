package org.jetbrains.kotlin.com.intellij.psi;

import com.huawei.hms.ml.language.common.utils.Constant;
import java.lang.ref.SoftReference;
import org.eclipse.jdt.internal.compiler.impl.CompilerOptions;
import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;
import org.jetbrains.kotlin.com.intellij.lang.ASTNode;
import org.jetbrains.kotlin.com.intellij.lang.FileASTNode;
import org.jetbrains.kotlin.com.intellij.lang.Language;
import org.jetbrains.kotlin.com.intellij.openapi.diagnostic.Attachment;
import org.jetbrains.kotlin.com.intellij.openapi.diagnostic.ExceptionWithAttachments;
import org.jetbrains.kotlin.com.intellij.openapi.util.Condition;
import org.jetbrains.kotlin.com.intellij.openapi.util.Key;
import org.jetbrains.kotlin.com.intellij.openapi.util.UserDataHolder;
import org.jetbrains.kotlin.com.intellij.openapi.util.registry.Registry;
import org.jetbrains.kotlin.com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.kotlin.com.intellij.psi.stubs.PsiFileStub;
import org.jetbrains.kotlin.com.intellij.psi.stubs.StubElement;
import org.jetbrains.kotlin.com.intellij.psi.tree.IElementType;
import org.jetbrains.kotlin.com.intellij.psi.util.PsiUtilCore;
import org.jetbrains.kotlin.com.intellij.util.containers.JBIterable;

/* JADX INFO: loaded from: classes3.dex */
public final class PsiInvalidElementAccessException extends RuntimeException implements ExceptionWithAttachments {
    private static final Key<Object> INVALIDATION_TRACE = Key.create("INVALIDATION_TRACE");
    private static final Key<Boolean> REPORTING_EXCEPTION = Key.create("REPORTING_EXCEPTION");
    private final Attachment[] myDiagnostic;
    private final SoftReference<PsiElement> myElementReference;
    private final String myMessage;

    /* JADX WARN: Code duplicated, block: B:8:0x000f  */
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str;
        int i2;
        if (i != 2 && i != 6 && i != 7) {
            switch (i) {
                case 9:
                case 10:
                case 11:
                case 12:
                case 13:
                case 14:
                case 15:
                case 16:
                case 17:
                    str = "@NotNull method %s.%s must not return null";
                    break;
                default:
                    str = "Argument for @NotNull parameter '%s' of %s.%s must not be null";
                    break;
            }
        } else {
            str = "@NotNull method %s.%s must not return null";
        }
        if (i != 2 && i != 6 && i != 7) {
            switch (i) {
                case 9:
                case 10:
                case 11:
                case 12:
                case 13:
                case 14:
                case 15:
                case 16:
                case 17:
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
            case 6:
            case 7:
            case 9:
            case 10:
            case 11:
            case 12:
            case 13:
            case 14:
            case 15:
            case 16:
            case 17:
                objArr[0] = "org/jetbrains/kotlin/com/intellij/psi/PsiInvalidElementAccessException";
                break;
            case 3:
            case 4:
            case 5:
            case 18:
            case 19:
                objArr[0] = CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD;
                break;
            case 8:
                objArr[0] = "root";
                break;
            default:
                objArr[0] = "node";
                break;
        }
        if (i == 2) {
            objArr[1] = "createAttachments";
        } else if (i == 6) {
            objArr[1] = "getLanguage";
        } else if (i != 7) {
            switch (i) {
                case 9:
                case 10:
                case 11:
                case 12:
                case 13:
                case 14:
                case 15:
                case 16:
                case 17:
                    objArr[1] = "findOutInvalidationReason";
                    break;
                default:
                    objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/PsiInvalidElementAccessException";
                    break;
            }
        } else {
            objArr[1] = "getAttachments";
        }
        switch (i) {
            case 1:
                objArr[2] = "createByNode";
                break;
            case 2:
            case 6:
            case 7:
            case 9:
            case 10:
            case 11:
            case 12:
            case 13:
            case 14:
            case 15:
            case 16:
            case 17:
                break;
            case 3:
                objArr[2] = "getPsiInvalidationTrace";
                break;
            case 4:
                objArr[2] = "getMessageWithReason";
                break;
            case 5:
                objArr[2] = "getLanguage";
                break;
            case 8:
                objArr[2] = "findOutInvalidationReason";
                break;
            case 18:
                objArr[2] = "setInvalidationTrace";
                break;
            case 19:
                objArr[2] = "getInvalidationTrace";
                break;
            default:
                objArr[2] = "<init>";
                break;
        }
        String str2 = String.format(str, objArr);
        if (i != 2 && i != 6 && i != 7) {
            switch (i) {
                case 9:
                case 10:
                case 11:
                case 12:
                case 13:
                case 14:
                case 15:
                case 16:
                case 17:
                    break;
                default:
                    throw new IllegalArgumentException(str2);
            }
        }
        throw new IllegalStateException(str2);
    }

    public PsiInvalidElementAccessException(PsiElement psiElement) {
        this(psiElement, null, null);
    }

    public PsiInvalidElementAccessException(PsiElement psiElement, String str) {
        this(psiElement, str, null);
    }

    public PsiInvalidElementAccessException(PsiElement psiElement, Throwable th) {
        this(psiElement, null, th);
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public PsiInvalidElementAccessException(PsiElement psiElement, String str, Throwable th) {
        Object psiInvalidationTrace;
        super(null, th);
        this.myElementReference = new SoftReference<>(psiElement);
        if (psiElement == null) {
            this.myMessage = str;
            this.myDiagnostic = Attachment.EMPTY_ARRAY;
            return;
        }
        if (psiElement == PsiUtilCore.NULL_PSI_ELEMENT) {
            this.myMessage = "NULL_PSI_ELEMENT ;" + str;
            this.myDiagnostic = Attachment.EMPTY_ARRAY;
            return;
        }
        Boolean bool = Boolean.TRUE;
        Key<Boolean> key = REPORTING_EXCEPTION;
        boolean zEquals = bool.equals(psiElement.getUserData(key));
        psiElement.putUserData(key, Boolean.TRUE);
        if (zEquals) {
            psiInvalidationTrace = null;
        } else {
            try {
                psiInvalidationTrace = getPsiInvalidationTrace(psiElement);
            } finally {
                psiElement.putUserData(REPORTING_EXCEPTION, null);
            }
        }
        this.myMessage = getMessageWithReason(psiElement, str, zEquals, psiInvalidationTrace);
        this.myDiagnostic = createAttachments(psiInvalidationTrace);
    }

    private PsiInvalidElementAccessException(ASTNode aSTNode, String str) {
        if (aSTNode == null) {
            $$$reportNull$$$0(0);
        }
        this.myElementReference = new SoftReference<>(null);
        IElementType elementType = aSTNode.getElementType();
        this.myMessage = "Element " + aSTNode.getClass() + " of type " + elementType + " (" + elementType.getClass() + Constant.AFTER_QUTO + (str == null ? "" : "; " + str);
        this.myDiagnostic = createAttachments(findInvalidationTrace(aSTNode));
    }

    public static PsiInvalidElementAccessException createByNode(ASTNode aSTNode, String str) {
        if (aSTNode == null) {
            $$$reportNull$$$0(1);
        }
        return new PsiInvalidElementAccessException(aSTNode, str);
    }

    private static Attachment[] createAttachments(Object obj) {
        Attachment[] attachmentArr;
        if (obj == null) {
            attachmentArr = Attachment.EMPTY_ARRAY;
        } else {
            Attachment[] attachmentArr2 = new Attachment[1];
            attachmentArr2[0] = obj instanceof Throwable ? new Attachment("invalidation", (Throwable) obj) : new Attachment("diagnostic.txt", obj.toString());
            attachmentArr = attachmentArr2;
        }
        if (attachmentArr == null) {
            $$$reportNull$$$0(2);
        }
        return attachmentArr;
    }

    private static Object getPsiInvalidationTrace(PsiElement psiElement) {
        if (psiElement == null) {
            $$$reportNull$$$0(3);
        }
        Object invalidationTrace = getInvalidationTrace(psiElement);
        if (invalidationTrace != null) {
            return invalidationTrace;
        }
        if (psiElement instanceof PsiFile) {
            return getInvalidationTrace(((PsiFile) psiElement).getOriginalFile());
        }
        return findInvalidationTrace(psiElement.getNode());
    }

    private static String getMessageWithReason(PsiElement psiElement, String str, boolean z, Object obj) {
        String str2;
        if (psiElement == null) {
            $$$reportNull$$$0(4);
        }
        String str3 = "Element: " + psiElement.getClass();
        if (!z) {
            try {
                str3 = str3 + " #" + getLanguage(psiElement).getID() + " ";
            } catch (PsiInvalidElementAccessException unused) {
            }
            if (!isTrackingInvalidation()) {
                str2 = CompilerOptions.DISABLED;
            } else if (obj != null) {
                str2 = "see attachment";
            } else {
                str2 = "no info";
            }
            try {
                str3 = str3 + " because: " + findOutInvalidationReason(psiElement);
            } catch (PsiInvalidElementAccessException unused2) {
            }
            str3 = str3 + "\ninvalidated at: " + str2;
        }
        return str3 + (str == null ? "" : "; " + str);
    }

    private static Language getLanguage(PsiElement psiElement) {
        if (psiElement == null) {
            $$$reportNull$$$0(5);
        }
        Language language = psiElement instanceof ASTNode ? ((ASTNode) psiElement).getElementType().getLanguage() : psiElement.getLanguage();
        if (language == null) {
            $$$reportNull$$$0(6);
        }
        return language;
    }

    @Override // java.lang.Throwable
    public String getMessage() {
        return this.myMessage;
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.diagnostic.ExceptionWithAttachments
    public Attachment[] getAttachments() {
        Attachment[] attachmentArr = this.myDiagnostic;
        if (attachmentArr == null) {
            $$$reportNull$$$0(7);
        }
        return attachmentArr;
    }

    public static Object findInvalidationTrace(ASTNode aSTNode) {
        while (true) {
            if (aSTNode == null) {
                return null;
            }
            Key<Object> key = INVALIDATION_TRACE;
            Object userData = aSTNode.getUserData(key);
            if (userData != null) {
                return userData;
            }
            ASTNode treeParent = aSTNode.getTreeParent();
            if (treeParent == null && (aSTNode instanceof FileASTNode)) {
                PsiElement psi = aSTNode.getPsi();
                Object userData2 = psi != null ? psi.getUserData(key) : null;
                if (userData2 != null) {
                    return userData2;
                }
            }
            aSTNode = treeParent;
        }
    }

    public static String findOutInvalidationReason(PsiElement psiElement) {
        PsiElement psiElement2;
        PsiElement context;
        if (psiElement == null) {
            $$$reportNull$$$0(8);
        }
        if (psiElement == PsiUtilCore.NULL_PSI_ELEMENT) {
            return "NULL_PSI_ELEMENT";
        }
        PsiElement parent = psiElement instanceof PsiFile ? psiElement : psiElement.getLightParent();
        if (parent == null) {
            String str = "parent is null";
            if (psiElement instanceof StubBasedPsiElement) {
                for (StubElement stub = ((StubBasedPsiElement) psiElement).getStub(); stub != null; stub = stub.getParentStub()) {
                    String str2 = str + "\n  each stub=" + stub;
                    if (stub instanceof PsiFileStub) {
                        str2 = str2 + "; fileStub.psi=" + stub.getPsi() + "; reason=" + ((PsiFileStub) stub).getInvalidationReason();
                    }
                    str = str2;
                }
            }
            if (str == null) {
                $$$reportNull$$$0(9);
            }
            return str;
        }
        String str3 = "";
        while (true) {
            PsiElement psiElement3 = parent;
            psiElement2 = psiElement;
            psiElement = psiElement3;
            if (psiElement == null || (psiElement instanceof PsiFile)) {
                break;
            }
            str3 = str3 + (str3.isEmpty() ? "" : ", ") + psiElement.getClass();
            parent = psiElement.getLightParent();
        }
        PsiFile psiFile = (PsiFile) psiElement;
        if (psiFile == null) {
            PsiElement context2 = psiElement2.getContext();
            String str4 = "containing file is null; hierarchy=" + str3 + ", context=" + context2 + ", contextFile=" + JBIterable.generate(context2, new PsiInvalidElementAccessException$$ExternalSyntheticLambda0()).find(new Condition() { // from class: org.jetbrains.kotlin.com.intellij.psi.PsiInvalidElementAccessException$$ExternalSyntheticLambda1
                @Override // org.jetbrains.kotlin.com.intellij.openapi.util.Condition
                public final boolean value(Object obj) {
                    return PsiInvalidElementAccessException.lambda$findOutInvalidationReason$0((PsiElement) obj);
                }
            });
            if (str4 == null) {
                $$$reportNull$$$0(10);
            }
            return str4;
        }
        FileViewProvider viewProvider = psiFile.getViewProvider();
        VirtualFile virtualFile = viewProvider.getVirtualFile();
        if (!virtualFile.isValid()) {
            String str5 = virtualFile + " is invalid";
            if (str5 == null) {
                $$$reportNull$$$0(11);
            }
            return str5;
        }
        if (!viewProvider.isPhysical() && (context = psiFile.getContext()) != null && !context.isValid()) {
            String str6 = "invalid context: " + findOutInvalidationReason(context);
            if (str6 == null) {
                $$$reportNull$$$0(12);
            }
            return str6;
        }
        PsiFile originalFile = psiFile.getOriginalFile();
        if (originalFile != psiFile && !originalFile.isValid()) {
            String str7 = "invalid original: " + findOutInvalidationReason(originalFile);
            if (str7 == null) {
                $$$reportNull$$$0(13);
            }
            return str7;
        }
        PsiManager manager = psiFile.getManager();
        if (manager.getProject().isDisposed()) {
            String str8 = "project is disposed: " + manager.getProject();
            if (str8 == null) {
                $$$reportNull$$$0(14);
            }
            return str8;
        }
        Language language = psiFile.getLanguage();
        if (language != viewProvider.getBaseLanguage()) {
            String str9 = "File language:" + language + " != Provider base language:" + viewProvider.getBaseLanguage();
            if (str9 == null) {
                $$$reportNull$$$0(15);
            }
            return str9;
        }
        FileViewProvider fileViewProviderFindViewProvider = manager.findViewProvider(virtualFile);
        if (viewProvider != fileViewProviderFindViewProvider) {
            String str10 = "different providers: " + viewProvider + "(" + id(viewProvider) + "); " + fileViewProviderFindViewProvider + "(" + id(fileViewProviderFindViewProvider) + Constant.AFTER_QUTO;
            if (str10 == null) {
                $$$reportNull$$$0(16);
            }
            return str10;
        }
        if (!viewProvider.isPhysical()) {
            String str11 = "non-physical provider: " + viewProvider;
            if (str11 == null) {
                $$$reportNull$$$0(17);
            }
            return str11;
        }
        return "psi is outdated";
    }

    static /* synthetic */ boolean lambda$findOutInvalidationReason$0(PsiElement psiElement) {
        return psiElement instanceof PsiFile;
    }

    private static String id(FileViewProvider fileViewProvider) {
        return Integer.toHexString(System.identityHashCode(fileViewProvider));
    }

    public static void setInvalidationTrace(UserDataHolder userDataHolder, Object obj) {
        if (userDataHolder == null) {
            $$$reportNull$$$0(18);
        }
        userDataHolder.putUserData(INVALIDATION_TRACE, obj);
    }

    public static Object getInvalidationTrace(UserDataHolder userDataHolder) {
        if (userDataHolder == null) {
            $$$reportNull$$$0(19);
        }
        return userDataHolder.getUserData(INVALIDATION_TRACE);
    }

    public static boolean isTrackingInvalidation() {
        return Registry.is("psi.track.invalidation", true);
    }

    public PsiElement getPsiElement() {
        return this.myElementReference.get();
    }
}
