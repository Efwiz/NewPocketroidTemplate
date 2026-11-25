package org.jetbrains.kotlin.com.intellij.openapi.application;

/* JADX INFO: loaded from: classes3.dex */
public interface AppUIExecutor extends BaseExpirableExecutor<AppUIExecutor> {
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = (i == 1 || i == 3) ? "@NotNull method %s.%s must not return null" : "Argument for @NotNull parameter '%s' of %s.%s must not be null";
        Object[] objArr = new Object[(i == 1 || i == 3) ? 2 : 3];
        if (i == 1 || i == 3) {
            objArr[0] = "org/jetbrains/kotlin/com/intellij/openapi/application/AppUIExecutor";
        } else {
            objArr[0] = "modality";
        }
        if (i == 1) {
            objArr[1] = "onUiThread";
        } else if (i != 3) {
            objArr[1] = "org/jetbrains/kotlin/com/intellij/openapi/application/AppUIExecutor";
        } else {
            objArr[1] = "onWriteThread";
        }
        if (i != 1) {
            if (i == 2) {
                objArr[2] = "onWriteThread";
            } else if (i != 3) {
                objArr[2] = "onUiThread";
            }
        }
        String str2 = String.format(str, objArr);
        if (i != 1 && i != 3) {
            throw new IllegalArgumentException(str2);
        }
        throw new IllegalStateException(str2);
    }

    AppUIExecutor later();

    static AppUIExecutor onWriteThread(ModalityState modalityState) {
        if (modalityState == null) {
            $$$reportNull$$$0(2);
        }
        AppUIExecutor appUIExecutorCreateWriteThreadExecutor = AsyncExecutionService.getService().createWriteThreadExecutor(modalityState);
        if (appUIExecutorCreateWriteThreadExecutor == null) {
            $$$reportNull$$$0(3);
        }
        return appUIExecutorCreateWriteThreadExecutor;
    }

    static AppUIExecutor onWriteThread() {
        return onWriteThread(ModalityState.defaultModalityState());
    }
}
