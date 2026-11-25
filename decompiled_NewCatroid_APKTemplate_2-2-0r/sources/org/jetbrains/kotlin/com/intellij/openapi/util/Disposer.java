package org.jetbrains.kotlin.com.intellij.openapi.util;

import java.util.Map;
import org.antlr.stringtemplate.language.ASTExpr;
import org.jetbrains.kotlin.com.intellij.openapi.Disposable;
import org.jetbrains.kotlin.com.intellij.util.IncorrectOperationException;
import org.jetbrains.kotlin.com.intellij.util.ObjectUtils;
import org.jetbrains.kotlin.com.intellij.util.containers.ContainerUtil;

/* JADX INFO: loaded from: classes3.dex */
public final class Disposer {
    private static boolean ourDebugMode;
    private static final ObjectTree ourTree = new ObjectTree();
    private static final Map<String, Disposable> ourKeyDisposables = ContainerUtil.createConcurrentWeakMap();

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = (i == 3 || i == 17) ? "@NotNull method %s.%s must not return null" : "Argument for @NotNull parameter '%s' of %s.%s must not be null";
        Object[] objArr = new Object[(i == 3 || i == 17) ? 2 : 3];
        switch (i) {
            case 1:
            case 18:
                objArr[0] = "parentDisposable";
                break;
            case 2:
            default:
                objArr[0] = "debugName";
                break;
            case 3:
            case 17:
                objArr[0] = "org/jetbrains/kotlin/com/intellij/openapi/util/Disposer";
                break;
            case 4:
            case 6:
            case 8:
                objArr[0] = "parent";
                break;
            case 5:
            case 7:
            case 9:
                objArr[0] = "child";
                break;
            case 10:
            case 13:
                objArr[0] = ASTExpr.DEFAULT_MAP_KEY_NAME;
                break;
            case 11:
            case 12:
            case 14:
            case 15:
            case 16:
            case 20:
            case 21:
                objArr[0] = "disposable";
                break;
            case 19:
                objArr[0] = "object";
                break;
        }
        if (i == 3) {
            objArr[1] = "newDisposable";
        } else if (i != 17) {
            objArr[1] = "org/jetbrains/kotlin/com/intellij/openapi/util/Disposer";
        } else {
            objArr[1] = "getTree";
        }
        switch (i) {
            case 3:
            case 17:
                break;
            case 4:
            case 5:
            case 8:
            case 9:
            case 10:
                objArr[2] = "register";
                break;
            case 6:
            case 7:
                objArr[2] = "tryRegister";
                break;
            case 11:
                objArr[2] = "isDisposed";
                break;
            case 12:
                objArr[2] = "isDisposing";
                break;
            case 13:
                objArr[2] = "get";
                break;
            case 14:
            case 16:
                objArr[2] = "dispose";
                break;
            case 15:
                objArr[2] = "disposeChildren";
                break;
            case 18:
            case 19:
                objArr[2] = "findRegisteredObject";
                break;
            case 20:
                objArr[2] = "getDisposalTrace";
                break;
            case 21:
                objArr[2] = "getRegistrationTrace";
                break;
            default:
                objArr[2] = "newDisposable";
                break;
        }
        String str2 = String.format(str, objArr);
        if (i != 3 && i != 17) {
            throw new IllegalArgumentException(str2);
        }
        throw new IllegalStateException(str2);
    }

    public static Disposable newDisposable() {
        return new Disposable() { // from class: org.jetbrains.kotlin.com.intellij.openapi.util.Disposer.1
            @Override // org.jetbrains.kotlin.com.intellij.openapi.Disposable
            public void dispose() {
            }

            public String toString() {
                return "newDisposable";
            }
        };
    }

    public static Disposable newDisposable(final String str) {
        if (str == null) {
            $$$reportNull$$$0(0);
        }
        return new Disposable() { // from class: org.jetbrains.kotlin.com.intellij.openapi.util.Disposer.2
            @Override // org.jetbrains.kotlin.com.intellij.openapi.Disposable
            public void dispose() {
            }

            public String toString() {
                return str;
            }
        };
    }

    static class CheckedDisposableImpl implements CheckedDisposable {
        volatile boolean isDisposed;

        CheckedDisposableImpl() {
        }

        @Override // org.jetbrains.kotlin.com.intellij.openapi.Disposable
        public void dispose() {
            this.isDisposed = true;
        }

        public String toString() {
            return "CheckedDisposableImpl{isDisposed=" + this.isDisposed + "} " + super.toString();
        }
    }

    public static void register(Disposable disposable, Disposable disposable2) throws IncorrectOperationException {
        if (disposable == null) {
            $$$reportNull$$$0(4);
        }
        if (disposable2 == null) {
            $$$reportNull$$$0(5);
        }
        RuntimeException runtimeExceptionRegister = ourTree.register(disposable, disposable2);
        if (runtimeExceptionRegister != null) {
            throw runtimeExceptionRegister;
        }
    }

    public static void dispose(Disposable disposable) {
        if (disposable == null) {
            $$$reportNull$$$0(14);
        }
        dispose(disposable, true);
    }

    public static void dispose(Disposable disposable, boolean z) {
        if (disposable == null) {
            $$$reportNull$$$0(16);
        }
        ourTree.executeAll(disposable, z);
    }

    public static ObjectTree getTree() {
        ObjectTree objectTree = ourTree;
        if (objectTree == null) {
            $$$reportNull$$$0(17);
        }
        return objectTree;
    }

    public static boolean isDebugMode() {
        return ourDebugMode;
    }

    public static Throwable getDisposalTrace(Disposable disposable) {
        if (disposable == null) {
            $$$reportNull$$$0(20);
        }
        return (Throwable) ObjectUtils.tryCast(getTree().getDisposalInfo(disposable), Throwable.class);
    }
}
