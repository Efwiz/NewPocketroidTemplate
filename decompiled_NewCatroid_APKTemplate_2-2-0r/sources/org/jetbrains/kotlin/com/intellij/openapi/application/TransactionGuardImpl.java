package org.jetbrains.kotlin.com.intellij.openapi.application;

import java.util.Map;
import java.util.concurrent.ConcurrentMap;
import javax.swing.SwingUtilities;
import org.jetbrains.kotlin.com.intellij.diagnostic.LoadingState;
import org.jetbrains.kotlin.com.intellij.openapi.diagnostic.Logger;
import org.jetbrains.kotlin.com.intellij.openapi.util.registry.Registry;
import org.jetbrains.kotlin.com.intellij.util.containers.CollectionFactory;

/* JADX INFO: loaded from: classes3.dex */
public final class TransactionGuardImpl extends TransactionGuard {
    private static final Logger LOG = Logger.getInstance((Class<?>) TransactionGuardImpl.class);
    private boolean myErrorReported;
    private final Map<ModalityState, Boolean> myWriteSafeModalities;
    private boolean myWritingAllowed;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = i != 3 ? "Argument for @NotNull parameter '%s' of %s.%s must not be null" : "@NotNull method %s.%s must not return null";
        Object[] objArr = new Object[i != 3 ? 3 : 2];
        switch (i) {
            case 1:
            case 7:
                objArr[0] = "transaction";
                break;
            case 2:
            case 9:
            case 11:
                objArr[0] = "runnable";
                break;
            case 3:
                objArr[0] = "org/jetbrains/kotlin/com/intellij/openapi/application/TransactionGuardImpl";
                break;
            case 4:
            case 5:
            case 8:
                objArr[0] = "modality";
                break;
            case 6:
            default:
                objArr[0] = "parentDisposable";
                break;
            case 10:
                objArr[0] = "modalityState";
                break;
        }
        if (i != 3) {
            objArr[1] = "org/jetbrains/kotlin/com/intellij/openapi/application/TransactionGuardImpl";
        } else {
            objArr[1] = "startActivity";
        }
        switch (i) {
            case 2:
                objArr[2] = "submitTransactionAndWait";
                break;
            case 3:
                break;
            case 4:
                objArr[2] = "reportWriteUnsafeContext";
                break;
            case 5:
                objArr[2] = "assertWriteSafeContext";
                break;
            case 6:
            case 7:
                objArr[2] = "submitTransactionLater";
                break;
            case 8:
                objArr[2] = "enteredModality";
                break;
            case 9:
            case 10:
                objArr[2] = "wrapLaterInvocation";
                break;
            case 11:
                objArr[2] = "runWithWritingAllowed";
                break;
            default:
                objArr[2] = "submitTransaction";
                break;
        }
        String str2 = String.format(str, objArr);
        if (i == 3) {
            throw new IllegalStateException(str2);
        }
    }

    public TransactionGuardImpl() {
        ConcurrentMap concurrentMapCreateConcurrentWeakMap = CollectionFactory.createConcurrentWeakMap();
        this.myWriteSafeModalities = concurrentMapCreateConcurrentWeakMap;
        concurrentMapCreateConcurrentWeakMap.put(ModalityState.NON_MODAL, true);
        this.myWritingAllowed = SwingUtilities.isEventDispatchThread();
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.application.TransactionGuard
    public boolean isWriteSafeModality(ModalityState modalityState) {
        return Boolean.TRUE.equals(this.myWriteSafeModalities.get(modalityState));
    }

    public void assertWriteActionAllowed() {
        ApplicationManager.getApplication().assertIsWriteThread();
        if (this.myWritingAllowed || !areAssertionsEnabled() || this.myErrorReported) {
            return;
        }
        LOG.error(reportWriteUnsafeContext(ModalityState.current()));
        this.myErrorReported = true;
    }

    private static String reportWriteUnsafeContext(ModalityState modalityState) {
        if (modalityState == null) {
            $$$reportNull$$$0(4);
        }
        return "Write-unsafe context! Model changes are allowed from write-safe contexts only. Please ensure you're using invokeLater/invokeAndWait with a correct modality state (not \"any\"). See TransactionGuard documentation for details.\n  current modality=" + modalityState;
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.application.TransactionGuard
    public void assertWriteSafeContext(ModalityState modalityState) {
        if (modalityState == null) {
            $$$reportNull$$$0(5);
        }
        if (isWriteSafeModality(modalityState) || !areAssertionsEnabled()) {
            return;
        }
        LOG.error(reportWriteUnsafeContext(modalityState));
    }

    private static boolean areAssertionsEnabled() {
        return LoadingState.COMPONENTS_LOADED.isOccurred() && Registry.is("ide.require.transaction.for.model.changes", false);
    }

    public void enteredModality(ModalityState modalityState) {
        if (modalityState == null) {
            $$$reportNull$$$0(8);
        }
        this.myWriteSafeModalities.put(modalityState, Boolean.valueOf(this.myWritingAllowed));
    }

    public String toString() {
        return "TransactionGuardImpl{myWritingAllowed=" + this.myWritingAllowed + '}';
    }
}
