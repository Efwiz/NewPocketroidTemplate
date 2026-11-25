package org.jetbrains.kotlin.com.intellij.util;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Supplier;
import org.jetbrains.kotlin.com.intellij.injected.editor.VirtualFileWindow;
import org.jetbrains.kotlin.com.intellij.openapi.diagnostic.Attachment;
import org.jetbrains.kotlin.com.intellij.openapi.diagnostic.Logger;
import org.jetbrains.kotlin.com.intellij.openapi.util.ThrowableComputable;
import org.jetbrains.kotlin.com.intellij.openapi.util.registry.Registry;
import org.jetbrains.kotlin.com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.kotlin.com.intellij.psi.PsiFile;

/* JADX INFO: loaded from: classes3.dex */
public final class AstLoadingFilter {
    private static final Logger LOG = Logger.getInstance((Class<?>) AstLoadingFilter.class);
    private static final ThreadLocal<Supplier<String>> myDisallowedInfo = new ThreadLocal<>();
    private static final ThreadLocal<Set<VirtualFile>> myForcedAllowedFiles = ThreadLocal.withInitial(new Supplier() { // from class: org.jetbrains.kotlin.com.intellij.util.AstLoadingFilter$$ExternalSyntheticLambda0
        @Override // java.util.function.Supplier
        public final Object get() {
            return AstLoadingFilter.lambda$static$0();
        }
    });

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = i != 3 ? "Argument for @NotNull parameter '%s' of %s.%s must not be null" : "@NotNull method %s.%s must not return null";
        Object[] objArr = new Object[i != 3 ? 3 : 2];
        switch (i) {
            case 2:
                objArr[0] = "disabledInfo";
                break;
            case 3:
                objArr[0] = "org/jetbrains/kotlin/com/intellij/util/AstLoadingFilter";
                break;
            case 4:
            case 5:
            case 10:
            case 12:
                objArr[0] = "runnable";
                break;
            case 6:
            case 9:
                objArr[0] = "debugInfo";
                break;
            case 7:
            case 8:
            case 13:
            case 15:
                objArr[0] = "computable";
                break;
            case 11:
            case 14:
                objArr[0] = "virtualFile";
                break;
            default:
                objArr[0] = "file";
                break;
        }
        if (i != 3) {
            objArr[1] = "org/jetbrains/kotlin/com/intellij/util/AstLoadingFilter";
        } else {
            objArr[1] = "buildDebugInfo";
        }
        switch (i) {
            case 1:
            case 2:
                objArr[2] = "buildDebugInfo";
                break;
            case 3:
                break;
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
            case 9:
                objArr[2] = "disallowTreeLoading";
                break;
            case 10:
            case 11:
            case 12:
            case 13:
            case 14:
            case 15:
                objArr[2] = "forceAllowTreeLoading";
                break;
            default:
                objArr[2] = "assertTreeLoadingAllowed";
                break;
        }
        String str2 = String.format(str, objArr);
        if (i == 3) {
            throw new IllegalStateException(str2);
        }
    }

    static /* synthetic */ String lambda$disallowTreeLoading$1() {
        return null;
    }

    static /* synthetic */ Set lambda$static$0() {
        return new HashSet();
    }

    private AstLoadingFilter() {
    }

    public static void assertTreeLoadingAllowed(VirtualFile virtualFile) {
        Supplier<String> supplier;
        if (virtualFile == null) {
            $$$reportNull$$$0(0);
        }
        if ((virtualFile instanceof VirtualFileWindow) || !Registry.is("ast.loading.filter", false) || (supplier = myDisallowedInfo.get()) == null || myForcedAllowedFiles.get().contains(virtualFile)) {
            return;
        }
        LOG.error("Tree access disabled", new AstLoadingException(), new Attachment("debugInfo", buildDebugInfo(virtualFile, supplier)));
    }

    private static String buildDebugInfo(VirtualFile virtualFile, Supplier<String> supplier) {
        if (virtualFile == null) {
            $$$reportNull$$$0(1);
        }
        if (supplier == null) {
            $$$reportNull$$$0(2);
        }
        StringBuilder sb = new StringBuilder("Accessed file path: ");
        sb.append(virtualFile.getPath());
        String str = supplier.get();
        if (str != null) {
            sb.append("\nAdditional info: \n").append(str);
        }
        String string = sb.toString();
        if (string == null) {
            $$$reportNull$$$0(3);
        }
        return string;
    }

    public static <T, E extends Throwable> T disallowTreeLoading(ThrowableComputable<? extends T, E> throwableComputable) throws Throwable {
        if (throwableComputable == null) {
            $$$reportNull$$$0(7);
        }
        return (T) disallowTreeLoading(throwableComputable, new Supplier() { // from class: org.jetbrains.kotlin.com.intellij.util.AstLoadingFilter$$ExternalSyntheticLambda1
            @Override // java.util.function.Supplier
            public final Object get() {
                return AstLoadingFilter.lambda$disallowTreeLoading$1();
            }
        });
    }

    public static <T, E extends Throwable> T disallowTreeLoading(ThrowableComputable<? extends T, E> throwableComputable, Supplier<String> supplier) throws Throwable {
        if (throwableComputable == null) {
            $$$reportNull$$$0(8);
        }
        if (supplier == null) {
            $$$reportNull$$$0(9);
        }
        ThreadLocal<Supplier<String>> threadLocal = myDisallowedInfo;
        if (threadLocal.get() != null) {
            return throwableComputable.compute();
        }
        try {
            threadLocal.set(supplier);
            return throwableComputable.compute();
        } finally {
            myDisallowedInfo.set(null);
        }
    }

    public static <T, E extends Throwable> T forceAllowTreeLoading(PsiFile psiFile, ThrowableComputable<? extends T, E> throwableComputable) throws Throwable {
        if (throwableComputable == null) {
            $$$reportNull$$$0(13);
        }
        VirtualFile virtualFile = psiFile == null ? null : psiFile.getVirtualFile();
        return virtualFile == null ? throwableComputable.compute() : (T) forceAllowTreeLoading(virtualFile, throwableComputable);
    }

    public static <T, E extends Throwable> T forceAllowTreeLoading(VirtualFile virtualFile, ThrowableComputable<? extends T, E> throwableComputable) throws Throwable {
        if (virtualFile == null) {
            $$$reportNull$$$0(14);
        }
        if (throwableComputable == null) {
            $$$reportNull$$$0(15);
        }
        Set<VirtualFile> set = myForcedAllowedFiles.get();
        if (set.add(virtualFile)) {
            try {
                return throwableComputable.compute();
            } finally {
                set.remove(virtualFile);
            }
        }
        return throwableComputable.compute();
    }
}
