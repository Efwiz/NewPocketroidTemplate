package org.jetbrains.kotlin.com.intellij.psi.text;

import org.jetbrains.kotlin.com.intellij.lang.ASTNode;
import org.jetbrains.kotlin.com.intellij.lang.FileASTNode;
import org.jetbrains.kotlin.com.intellij.openapi.diagnostic.ControlFlowException;
import org.jetbrains.kotlin.com.intellij.openapi.progress.ProgressIndicator;
import org.jetbrains.kotlin.com.intellij.openapi.project.Project;
import org.jetbrains.kotlin.com.intellij.openapi.util.Key;
import org.jetbrains.kotlin.com.intellij.openapi.util.Pair;
import org.jetbrains.kotlin.com.intellij.openapi.util.TextRange;
import org.jetbrains.kotlin.com.intellij.openapi.util.UserDataHolder;
import org.jetbrains.kotlin.com.intellij.openapi.util.registry.Registry;
import org.jetbrains.kotlin.com.intellij.psi.PsiFile;
import org.jetbrains.kotlin.com.intellij.psi.impl.DiffLog;
import org.jetbrains.kotlin.com.intellij.util.IncorrectOperationException;

/* JADX INFO: loaded from: classes3.dex */
public abstract class BlockSupport {
    public static final Key<Boolean> DO_NOT_REPARSE_INCREMENTALLY = Key.create("DO_NOT_REPARSE_INCREMENTALLY");
    public static final Key<Pair<ASTNode, CharSequence>> TREE_TO_BE_REPARSED = Key.create("TREE_TO_BE_REPARSED");
    public static final int INCREMENTAL_REPARSE_DEPTH_LIMIT = Registry.intValue("psi.incremental.reparse.depth.limit");
    public static final Key<Boolean> TREE_DEPTH_LIMIT_EXCEEDED = Key.create("TREE_IS_TOO_DEEP");

    public abstract DiffLog reparseRange(PsiFile psiFile, FileASTNode fileASTNode, TextRange textRange, CharSequence charSequence, ProgressIndicator progressIndicator, CharSequence charSequence2) throws IncorrectOperationException;

    public abstract void reparseRange(PsiFile psiFile, int i, int i2, CharSequence charSequence) throws IncorrectOperationException;

    public static BlockSupport getInstance(Project project) {
        return (BlockSupport) project.getService(BlockSupport.class);
    }

    public static class ReparsedSuccessfullyException extends RuntimeException implements ControlFlowException {
        private final DiffLog myDiffLog;

        private static /* synthetic */ void $$$reportNull$$$0(int i) {
            String str = (i == 1 || i == 2) ? "@NotNull method %s.%s must not return null" : "Argument for @NotNull parameter '%s' of %s.%s must not be null";
            Object[] objArr = new Object[(i == 1 || i == 2) ? 2 : 3];
            if (i == 1 || i == 2) {
                objArr[0] = "org/jetbrains/kotlin/com/intellij/psi/text/BlockSupport$ReparsedSuccessfullyException";
            } else {
                objArr[0] = "diffLog";
            }
            if (i == 1) {
                objArr[1] = "getDiffLog";
            } else if (i != 2) {
                objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/text/BlockSupport$ReparsedSuccessfullyException";
            } else {
                objArr[1] = "fillInStackTrace";
            }
            if (i != 1 && i != 2) {
                objArr[2] = "<init>";
            }
            String str2 = String.format(str, objArr);
            if (i != 1 && i != 2) {
                throw new IllegalArgumentException(str2);
            }
            throw new IllegalStateException(str2);
        }

        public ReparsedSuccessfullyException(DiffLog diffLog) {
            if (diffLog == null) {
                $$$reportNull$$$0(0);
            }
            this.myDiffLog = diffLog;
        }

        public DiffLog getDiffLog() {
            DiffLog diffLog = this.myDiffLog;
            if (diffLog == null) {
                $$$reportNull$$$0(1);
            }
            return diffLog;
        }

        @Override // java.lang.Throwable
        public synchronized Throwable fillInStackTrace() {
            return this;
        }
    }

    public static boolean isTooDeep(UserDataHolder userDataHolder) {
        return userDataHolder != null && Boolean.TRUE.equals(userDataHolder.getUserData(TREE_DEPTH_LIMIT_EXCEEDED));
    }
}
