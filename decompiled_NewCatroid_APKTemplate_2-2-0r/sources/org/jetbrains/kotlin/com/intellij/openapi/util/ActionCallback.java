package org.jetbrains.kotlin.com.intellij.openapi.util;

import org.jetbrains.kotlin.com.intellij.openapi.Disposable;
import org.mozilla.javascript.ES6Iterator;

/* JADX INFO: loaded from: classes3.dex */
public class ActionCallback implements Disposable {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    public static final ActionCallback DONE = new Done();
    public static final ActionCallback REJECTED = new Rejected();
    private final ExecutionCallback myDone;
    private final String myName;
    private final ExecutionCallback myRejected;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = (i == 2 || i == 4 || i == 6 || i == 8 || i == 10 || i == 15 || i == 16) ? "@NotNull method %s.%s must not return null" : "Argument for @NotNull parameter '%s' of %s.%s must not be null";
        Object[] objArr = new Object[(i == 2 || i == 4 || i == 6 || i == 8 || i == 10 || i == 15 || i == 16) ? 2 : 3];
        switch (i) {
            case 1:
                objArr[0] = "rejected";
                break;
            case 2:
            case 4:
            case 6:
            case 8:
            case 10:
            case 15:
            case 16:
                objArr[0] = "org/jetbrains/kotlin/com/intellij/openapi/util/ActionCallback";
                break;
            case 3:
            case 5:
            case 9:
            case 14:
                objArr[0] = "runnable";
                break;
            case 7:
                objArr[0] = "consumer";
                break;
            case 11:
            case 12:
            case 13:
                objArr[0] = "child";
                break;
            default:
                objArr[0] = ES6Iterator.DONE_PROPERTY;
                break;
        }
        if (i == 2) {
            objArr[1] = "reject";
        } else if (i == 4) {
            objArr[1] = "doWhenDone";
        } else if (i == 6 || i == 8) {
            objArr[1] = "doWhenRejected";
        } else if (i == 10) {
            objArr[1] = "doWhenProcessed";
        } else if (i == 15) {
            objArr[1] = "processOnDone";
        } else if (i != 16) {
            objArr[1] = "org/jetbrains/kotlin/com/intellij/openapi/util/ActionCallback";
        } else {
            objArr[1] = "createSetDoneRunnable";
        }
        switch (i) {
            case 2:
            case 4:
            case 6:
            case 8:
            case 10:
            case 15:
            case 16:
                break;
            case 3:
                objArr[2] = "doWhenDone";
                break;
            case 5:
            case 7:
                objArr[2] = "doWhenRejected";
                break;
            case 9:
                objArr[2] = "doWhenProcessed";
                break;
            case 11:
                objArr[2] = "notifyWhenDone";
                break;
            case 12:
                objArr[2] = "notifyWhenRejected";
                break;
            case 13:
                objArr[2] = "notify";
                break;
            case 14:
                objArr[2] = "processOnDone";
                break;
            default:
                objArr[2] = "<init>";
                break;
        }
        String str2 = String.format(str, objArr);
        if (i != 2 && i != 4 && i != 6 && i != 8 && i != 10 && i != 15 && i != 16) {
            throw new IllegalArgumentException(str2);
        }
        throw new IllegalStateException(str2);
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.Disposable
    public void dispose() {
    }

    public ActionCallback() {
        this(null);
    }

    public ActionCallback(String str) {
        this.myName = str;
        this.myDone = new ExecutionCallback();
        this.myRejected = new ExecutionCallback();
    }

    private ActionCallback(ExecutionCallback executionCallback, ExecutionCallback executionCallback2) {
        if (executionCallback == null) {
            $$$reportNull$$$0(0);
        }
        if (executionCallback2 == null) {
            $$$reportNull$$$0(1);
        }
        this.myDone = executionCallback;
        this.myRejected = executionCallback2;
        this.myName = null;
    }

    public void setDone() {
        if (this.myDone.setExecuted()) {
            this.myRejected.clear();
            Disposer.dispose(this);
        }
    }

    public static class Done extends ActionCallback {
        /* JADX WARN: Illegal instructions before constructor call */
        public Done() {
            super(new ExecutedExecutionCallback(), new IgnoreExecutionCallback());
        }
    }

    public static class Rejected extends ActionCallback {
        /* JADX WARN: Illegal instructions before constructor call */
        public Rejected() {
            super(new IgnoreExecutionCallback(), new ExecutedExecutionCallback());
        }
    }

    private static class ExecutedExecutionCallback extends ExecutionCallback {
        ExecutedExecutionCallback() {
            super(0);
        }

        @Override // org.jetbrains.kotlin.com.intellij.openapi.util.ExecutionCallback
        boolean setExecuted() {
            throw new IllegalStateException("Forbidden");
        }
    }

    private static class IgnoreExecutionCallback extends ExecutionCallback {
        private IgnoreExecutionCallback() {
        }

        @Override // org.jetbrains.kotlin.com.intellij.openapi.util.ExecutionCallback
        boolean setExecuted() {
            throw new IllegalStateException("Forbidden");
        }
    }

    public String toString() {
        String string = this.myName;
        if (string == null) {
            string = super.toString();
        }
        return string + " done=[" + this.myDone + "] rejected=[" + this.myRejected + "]";
    }
}
