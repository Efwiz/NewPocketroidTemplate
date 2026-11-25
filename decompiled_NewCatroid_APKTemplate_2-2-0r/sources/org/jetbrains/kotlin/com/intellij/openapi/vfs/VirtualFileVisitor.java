package org.jetbrains.kotlin.com.intellij.openapi.vfs;

import com.huawei.hms.ml.language.common.utils.Constant;
import org.jetbrains.kotlin.com.intellij.psi.PsiKeyword;
import org.jetbrains.kotlin.com.intellij.util.containers.Stack;

/* JADX INFO: loaded from: classes3.dex */
public abstract class VirtualFileVisitor<T> {
    private int myDepthLimit;
    private boolean myFollowSymLinks;
    private int myLevel;
    private boolean mySkipRoot;
    private T myValue;
    private Stack<T> myValueStack;
    public static final Option NO_FOLLOW_SYMLINKS = new Option();
    public static final Option SKIP_ROOT = new Option();
    public static final Option ONE_LEVEL_DEEP = limit(1);
    public static final Result CONTINUE = new Result(false, 0 == true ? 1 : 0);
    public static final Result SKIP_CHILDREN = new Result(true, 0 == true ? 1 : 0);

    protected static class VisitorException extends RuntimeException {
    }

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = i != 4 ? "Argument for @NotNull parameter '%s' of %s.%s must not be null" : "@NotNull method %s.%s must not return null";
        Object[] objArr = new Object[i != 4 ? 3 : 2];
        switch (i) {
            case 1:
                objArr[0] = "parentToSkipTo";
                break;
            case 2:
            case 3:
            case 5:
            case 6:
            case 7:
            case 8:
                objArr[0] = "file";
                break;
            case 4:
                objArr[0] = "org/jetbrains/kotlin/com/intellij/openapi/vfs/VirtualFileVisitor";
                break;
            default:
                objArr[0] = "options";
                break;
        }
        if (i != 4) {
            objArr[1] = "org/jetbrains/kotlin/com/intellij/openapi/vfs/VirtualFileVisitor";
        } else {
            objArr[1] = "visitFileEx";
        }
        switch (i) {
            case 1:
                objArr[2] = "skipTo";
                break;
            case 2:
                objArr[2] = "visitFile";
                break;
            case 3:
                objArr[2] = "visitFileEx";
                break;
            case 4:
                break;
            case 5:
                objArr[2] = "afterChildrenVisited";
                break;
            case 6:
                objArr[2] = "getChildrenIterable";
                break;
            case 7:
                objArr[2] = "allowVisitFile";
                break;
            case 8:
                objArr[2] = "allowVisitChildren";
                break;
            default:
                objArr[2] = "<init>";
                break;
        }
        String str2 = String.format(str, objArr);
        if (i == 4) {
            throw new IllegalStateException(str2);
        }
    }

    public void afterChildrenVisited(VirtualFile virtualFile) {
        if (virtualFile == null) {
            $$$reportNull$$$0(5);
        }
    }

    public Iterable<VirtualFile> getChildrenIterable(VirtualFile virtualFile) {
        if (virtualFile != null) {
            return null;
        }
        $$$reportNull$$$0(6);
        return null;
    }

    public boolean visitFile(VirtualFile virtualFile) {
        if (virtualFile != null) {
            return true;
        }
        $$$reportNull$$$0(2);
        return true;
    }

    protected VirtualFileVisitor(Option... optionArr) {
        if (optionArr == null) {
            $$$reportNull$$$0(0);
        }
        this.myFollowSymLinks = true;
        this.myDepthLimit = -1;
        for (Option option : optionArr) {
            if (option == NO_FOLLOW_SYMLINKS) {
                this.myFollowSymLinks = false;
            } else if (option == SKIP_ROOT) {
                this.mySkipRoot = true;
            } else if (option instanceof Option.LimitOption) {
                this.myDepthLimit = ((Option.LimitOption) option).limit;
            }
        }
    }

    public static class Option {
        private Option() {
        }

        private static final class LimitOption extends Option {
            private final int limit;

            private LimitOption(int i) {
                super();
                this.limit = i;
            }
        }
    }

    public static Option limit(int i) {
        return new Option.LimitOption(i);
    }

    public static final class Result {
        public final boolean skipChildren;
        public final VirtualFile skipToParent;

        private Result(boolean z, VirtualFile virtualFile) {
            this.skipChildren = z;
            this.skipToParent = virtualFile;
        }

        public String toString() {
            return "(" + (this.skipChildren ? "skip," + this.skipToParent : PsiKeyword.CONTINUE) + Constant.AFTER_QUTO;
        }
    }

    public static Result skipTo(VirtualFile virtualFile) {
        boolean z = true;
        if (virtualFile == null) {
            $$$reportNull$$$0(1);
        }
        return new Result(z, virtualFile);
    }

    public Result visitFileEx(VirtualFile virtualFile) {
        if (virtualFile == null) {
            $$$reportNull$$$0(3);
        }
        Result result = visitFile(virtualFile) ? CONTINUE : SKIP_CHILDREN;
        if (result == null) {
            $$$reportNull$$$0(4);
        }
        return result;
    }

    public final void setValueForChildren(T t) {
        this.myValue = t;
        if (this.myValueStack == null) {
            this.myValueStack = new Stack<>();
        }
    }

    public final T getCurrentValue() {
        return this.myValue;
    }

    final boolean allowVisitFile(VirtualFile virtualFile) {
        if (virtualFile == null) {
            $$$reportNull$$$0(7);
        }
        return this.myLevel > 0 || !this.mySkipRoot;
    }

    final boolean allowVisitChildren(VirtualFile virtualFile) {
        if (virtualFile == null) {
            $$$reportNull$$$0(8);
        }
        if (!virtualFile.is(VFileProperty.SYMLINK)) {
            return true;
        }
        if (this.myFollowSymLinks) {
            return !virtualFile.isRecursiveOrCircularSymlink();
        }
        return false;
    }

    final boolean depthLimitReached() {
        int i = this.myDepthLimit;
        return i >= 0 && this.myLevel >= i;
    }

    final void saveValue() {
        this.myLevel++;
        Stack<T> stack = this.myValueStack;
        if (stack != null) {
            stack.push(this.myValue);
        }
    }

    final void restoreValue(boolean z) {
        if (z) {
            this.myLevel--;
            Stack<T> stack = this.myValueStack;
            if (stack != null && !stack.isEmpty()) {
                this.myValueStack.pop();
            }
        }
        Stack<T> stack2 = this.myValueStack;
        if (stack2 != null) {
            this.myValue = stack2.isEmpty() ? null : this.myValueStack.peek();
        }
    }
}
