package org.jetbrains.kotlin.com.intellij.codeWithMe;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.com.intellij.diagnostic.LoadingState;
import org.jetbrains.kotlin.com.intellij.openapi.application.Application;
import org.jetbrains.kotlin.com.intellij.openapi.application.ApplicationManager;

/* JADX INFO: compiled from: ClientIdService.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\bf\u0018\u0000 \u00112\u00020\u0001:\u0001\u0011J\u0012\u0010\f\u001a\u00020\u00032\b\u0010\r\u001a\u0004\u0018\u00010\u000eH&J\u0012\u0010\u000f\u001a\u00020\u00102\b\u0010\r\u001a\u0004\u0018\u00010\u000eH&R\u0012\u0010\u0002\u001a\u00020\u0003X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0004\u0010\u0005R\u001a\u0010\u0006\u001a\u0004\u0018\u00010\u0007X¦\u000e¢\u0006\f\u001a\u0004\b\b\u0010\t\"\u0004\b\n\u0010\u000b¨\u0006\u0012"}, d2 = {"Lorg/jetbrains/kotlin/com/intellij/codeWithMe/ClientIdService;", "", "checkLongActivity", "", "getCheckLongActivity", "()Z", "clientIdValue", "", "getClientIdValue", "()Ljava/lang/String;", "setClientIdValue", "(Ljava/lang/String;)V", "isValid", "clientId", "Lorg/jetbrains/kotlin/com/intellij/codeWithMe/ClientId;", "toDisposable", "Lorg/jetbrains/kotlin/com/intellij/openapi/Disposable;", "Companion", "intellij.platform.core"}, k = 1, mv = {1, 5, 1})
public interface ClientIdService {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = Companion.$$INSTANCE;

    String getClientIdValue();

    boolean isValid(ClientId clientId);

    void setClientIdValue(String str);

    /* JADX INFO: compiled from: ClientIdService.kt */
    @Metadata(d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\b\u0010\u0003\u001a\u0004\u0018\u00010\u0004¨\u0006\u0005"}, d2 = {"Lorg/jetbrains/kotlin/com/intellij/codeWithMe/ClientIdService$Companion;", "", "()V", "tryGetInstance", "Lorg/jetbrains/kotlin/com/intellij/codeWithMe/ClientIdService;", "intellij.platform.core"}, k = 1, mv = {1, 5, 1})
    public static final class Companion {
        static final /* synthetic */ Companion $$INSTANCE = new Companion();

        private Companion() {
        }

        public final ClientIdService tryGetInstance() {
            if (!LoadingState.CONFIGURATION_STORE_INITIALIZED.isOccurred()) {
                return null;
            }
            Application application = ApplicationManager.getApplication();
            Intrinsics.checkNotNullExpressionValue(application, "ApplicationManager.getApplication()");
            if (application.isDisposed()) {
                return null;
            }
            return (ClientIdService) ApplicationManager.getApplication().getService(ClientIdService.class);
        }
    }
}
