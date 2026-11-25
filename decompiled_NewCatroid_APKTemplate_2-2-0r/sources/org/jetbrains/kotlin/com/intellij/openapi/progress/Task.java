package org.jetbrains.kotlin.com.intellij.openapi.progress;

import javax.swing.JComponent;
import org.jetbrains.kotlin.com.intellij.core.CoreBundle;
import org.jetbrains.kotlin.com.intellij.openapi.application.ApplicationManager;
import org.jetbrains.kotlin.com.intellij.openapi.application.EdtReplacementThread;
import org.jetbrains.kotlin.com.intellij.openapi.diagnostic.Logger;
import org.jetbrains.kotlin.com.intellij.openapi.project.Project;
import org.jetbrains.kotlin.com.intellij.openapi.util.text.StringUtil;
import org.jetbrains.kotlin.com.intellij.util.ExceptionUtil;
import org.jetbrains.kotlin.com.intellij.util.ObjectUtils;

/* JADX INFO: loaded from: classes3.dex */
public abstract class Task implements Progressive, TaskInfo {
    private static final Logger LOG = Logger.getInstance((Class<?>) Task.class);
    private final boolean myCanBeCancelled;
    private String myCancelText;
    private String myCancelTooltipText;
    protected final JComponent myParentComponent;
    protected final Project myProject;

    @Deprecated
    protected String myTitle;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str;
        int i2;
        switch (i) {
            case 3:
            case 4:
            case 6:
            case 7:
            case 8:
            case 9:
            case 10:
                str = "@NotNull method %s.%s must not return null";
                break;
            case 5:
            default:
                str = "Argument for @NotNull parameter '%s' of %s.%s must not be null";
                break;
        }
        switch (i) {
            case 3:
            case 4:
            case 6:
            case 7:
            case 8:
            case 9:
            case 10:
                i2 = 2;
                break;
            case 5:
            default:
                i2 = 3;
                break;
        }
        Object[] objArr = new Object[i2];
        switch (i) {
            case 1:
            case 2:
                objArr[0] = "error";
                break;
            case 3:
            case 4:
            case 6:
            case 7:
            case 8:
            case 9:
            case 10:
                objArr[0] = "org/jetbrains/kotlin/com/intellij/openapi/progress/Task";
                break;
            case 5:
            default:
                objArr[0] = "title";
                break;
        }
        switch (i) {
            case 3:
                objArr[1] = "whereToRunCallbacks";
                break;
            case 4:
                objArr[1] = "getTitle";
                break;
            case 5:
            default:
                objArr[1] = "org/jetbrains/kotlin/com/intellij/openapi/progress/Task";
                break;
            case 6:
                objArr[1] = "setTitle";
                break;
            case 7:
                objArr[1] = "setCancelText";
                break;
            case 8:
                objArr[1] = "setCancelTooltipText";
                break;
            case 9:
                objArr[1] = "asModal";
                break;
            case 10:
                objArr[1] = "asBackgroundable";
                break;
        }
        switch (i) {
            case 1:
                objArr[2] = "onError";
                break;
            case 2:
                objArr[2] = "onThrowable";
                break;
            case 3:
            case 4:
            case 6:
            case 7:
            case 8:
            case 9:
            case 10:
                break;
            case 5:
                objArr[2] = "setTitle";
                break;
            default:
                objArr[2] = "<init>";
                break;
        }
        String str2 = String.format(str, objArr);
        switch (i) {
            case 3:
            case 4:
            case 6:
            case 7:
            case 8:
            case 9:
            case 10:
                throw new IllegalStateException(str2);
            case 5:
            default:
                throw new IllegalArgumentException(str2);
        }
    }

    public abstract boolean isModal();

    public void onCancel() {
    }

    public void onFinished() {
    }

    public void onSuccess() {
    }

    private Task(Project project, JComponent jComponent, String str, boolean z) {
        if (str == null) {
            $$$reportNull$$$0(0);
        }
        this.myCancelText = CoreBundle.message("button.cancel", new Object[0]);
        this.myCancelTooltipText = CoreBundle.message("button.cancel", new Object[0]);
        this.myProject = project;
        this.myParentComponent = jComponent;
        this.myTitle = str;
        this.myCanBeCancelled = z;
    }

    @Deprecated
    public void onError(Exception exc) {
        if (exc == null) {
            $$$reportNull$$$0(1);
        }
        LOG.error((Throwable) exc);
    }

    public void onThrowable(Throwable th) {
        if (th == null) {
            $$$reportNull$$$0(2);
        }
        if (th instanceof Exception) {
            onError((Exception) th);
        } else {
            LOG.error(th);
        }
    }

    public EdtReplacementThread whereToRunCallbacks() {
        EdtReplacementThread edtReplacementThread = EdtReplacementThread.EDT_WITH_IW;
        if (edtReplacementThread == null) {
            $$$reportNull$$$0(3);
        }
        return edtReplacementThread;
    }

    public final Project getProject() {
        return this.myProject;
    }

    public final JComponent getParentComponent() {
        return this.myParentComponent;
    }

    public final String getTitle() {
        String str = this.myTitle;
        if (str == null) {
            $$$reportNull$$$0(4);
        }
        return str;
    }

    public final String getCancelText() {
        return this.myCancelText;
    }

    public boolean isHeadless() {
        return ApplicationManager.getApplication().isUnitTestMode() || ApplicationManager.getApplication().isHeadlessEnvironment();
    }

    public final boolean isCancellable() {
        return this.myCanBeCancelled;
    }

    public final Modal asModal() {
        if (isModal()) {
            return (Modal) this;
        }
        throw new IllegalStateException("Not a modal task");
    }

    public final Backgroundable asBackgroundable() {
        if (!isModal()) {
            return (Backgroundable) this;
        }
        throw new IllegalStateException("Not a backgroundable task");
    }

    public static abstract class Backgroundable extends Task implements PerformInBackgroundOption {
        private final PerformInBackgroundOption myBackgroundOption;

        private static /* synthetic */ void $$$reportNull$$$0(int i) {
            throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", "title", "org/jetbrains/kotlin/com/intellij/openapi/progress/Task$Backgroundable", "<init>"));
        }

        public boolean isConditionalModal() {
            return false;
        }

        @Override // org.jetbrains.kotlin.com.intellij.openapi.progress.Task
        public final boolean isModal() {
            return false;
        }

        /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
        public Backgroundable(Project project, String str, boolean z, PerformInBackgroundOption performInBackgroundOption) {
            this(project, null, str, z, performInBackgroundOption);
            if (str == null) {
                $$$reportNull$$$0(2);
            }
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public Backgroundable(Project project, JComponent jComponent, String str, boolean z, PerformInBackgroundOption performInBackgroundOption) {
            super(project, jComponent, str, z);
            if (str == null) {
                $$$reportNull$$$0(3);
            }
            this.myBackgroundOption = (PerformInBackgroundOption) ObjectUtils.notNull(performInBackgroundOption, ALWAYS_BACKGROUND);
            if (StringUtil.isEmptyOrSpaces(str)) {
                Task.LOG.warn("Empty title for backgroundable task.", new Throwable());
            }
        }

        @Override // org.jetbrains.kotlin.com.intellij.openapi.progress.PerformInBackgroundOption
        public boolean shouldStartInBackground() {
            return this.myBackgroundOption.shouldStartInBackground();
        }
    }

    public static abstract class Modal extends Task {
        private static /* synthetic */ void $$$reportNull$$$0(int i) {
            throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", "title", "org/jetbrains/kotlin/com/intellij/openapi/progress/Task$Modal", "<init>"));
        }

        @Override // org.jetbrains.kotlin.com.intellij.openapi.progress.Task
        public final boolean isModal() {
            return true;
        }

        /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
        public Modal(Project project, String str, boolean z) {
            this(project, null, str, z);
            if (str == null) {
                $$$reportNull$$$0(0);
            }
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public Modal(Project project, JComponent jComponent, String str, boolean z) {
            super(project, jComponent, str, z);
            if (str == null) {
                $$$reportNull$$$0(1);
            }
        }
    }

    public static abstract class WithResult<T, E extends Exception> extends Modal {
        private volatile Throwable myError;
        private volatile T myResult;

        public T getResult() throws Exception {
            Throwable th = this.myError;
            if (th != null) {
                ExceptionUtil.rethrowUnchecked(th);
                throw ((Exception) th);
            }
            return this.myResult;
        }
    }
}
