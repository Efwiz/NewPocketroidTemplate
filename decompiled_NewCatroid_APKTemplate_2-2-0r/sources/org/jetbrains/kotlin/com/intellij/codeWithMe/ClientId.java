package org.jetbrains.kotlin.com.intellij.codeWithMe;

import androidx.exifinterface.media.ExifInterface;
import com.huawei.hms.ml.language.common.utils.Constant;
import java.util.function.BiConsumer;
import java.util.function.Function;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.com.intellij.openapi.application.AccessToken;
import org.jetbrains.kotlin.com.intellij.openapi.diagnostic.Logger;
import org.jetbrains.kotlin.js.translate.context.Namer;

/* JADX INFO: compiled from: ClientId.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0006\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0004\b\u0086\b\u0018\u0000 \u00102\u00020\u0001:\u0002\u000f\u0010B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\t\u0010\u0007\u001a\u00020\u0003HÆ\u0003J\u0013\u0010\b\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u0003HÆ\u0001J\u0013\u0010\t\u001a\u00020\n2\b\u0010\u000b\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\f\u001a\u00020\rHÖ\u0001J\t\u0010\u000e\u001a\u00020\u0003HÖ\u0001R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u0011"}, d2 = {"Lorg/jetbrains/kotlin/com/intellij/codeWithMe/ClientId;", "", "value", "", "(Ljava/lang/String;)V", "getValue", "()Ljava/lang/String;", "component1", ConfigConstants.CONFIG_RENAMELIMIT_COPY, Namer.EQUALS_METHOD_NAME, "", "other", "hashCode", "", "toString", "AbsenceBehavior", "Companion", "intellij.platform.core"}, k = 1, mv = {1, 5, 1})
public final /* data */ class ClientId {
    private static AbsenceBehavior AbsenceBehaviorValue;

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private static final Logger LOG;
    private static final ClientId defaultLocalId;
    private static ClientId localId;
    private static boolean propagateAcrossThreads;
    private final String value;

    /* JADX INFO: compiled from: ClientId.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0002\b\u0004\b\u0086\u0001\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002j\u0002\b\u0003j\u0002\b\u0004¨\u0006\u0005"}, d2 = {"Lorg/jetbrains/kotlin/com/intellij/codeWithMe/ClientId$AbsenceBehavior;", "", "(Ljava/lang/String;I)V", "RETURN_LOCAL", "THROW", "intellij.platform.core"}, k = 1, mv = {1, 5, 1})
    public enum AbsenceBehavior {
        RETURN_LOCAL,
        THROW
    }

    @JvmStatic
    public static final <T, U> BiConsumer<T, U> decorateBiConsumer(BiConsumer<T, U> biConsumer) {
        return INSTANCE.decorateBiConsumer(biConsumer);
    }

    @JvmStatic
    public static final <T, R> Function<T, R> decorateFunction(Function<T, R> function) {
        return INSTANCE.decorateFunction(function);
    }

    @JvmStatic
    public static final Runnable decorateRunnable(Runnable runnable) {
        return INSTANCE.decorateRunnable(runnable);
    }

    public static final ClientId getCurrentOrNull() {
        return INSTANCE.getCurrentOrNull();
    }

    @JvmStatic
    public static final void trySetCurrentClientId(ClientId clientId) {
        INSTANCE.trySetCurrentClientId(clientId);
    }

    @JvmStatic
    public static final AccessToken withClientId(ClientId clientId) {
        return INSTANCE.withClientId(clientId);
    }

    public boolean equals(Object other) {
        if (this != other) {
            return (other instanceof ClientId) && Intrinsics.areEqual(this.value, ((ClientId) other).value);
        }
        return true;
    }

    public int hashCode() {
        String str = this.value;
        if (str != null) {
            return str.hashCode();
        }
        return 0;
    }

    public String toString() {
        return "ClientId(value=" + this.value + Constant.AFTER_QUTO;
    }

    public ClientId(String value) {
        Intrinsics.checkNotNullParameter(value, "value");
        this.value = value;
    }

    public final String getValue() {
        return this.value;
    }

    /* JADX INFO: compiled from: ClientId.kt */
    @Metadata(d1 = {"\u0000x\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0010\u000b\n\u0002\b\u0013\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0006\u0010*\u001a\u00020+J4\u0010,\u001a\u000e\u0012\u0004\u0012\u0002H.\u0012\u0004\u0012\u0002H/0-\"\u0004\b\u0000\u0010.\"\u0004\b\u0001\u0010/2\u0012\u00100\u001a\u000e\u0012\u0004\u0012\u0002H.\u0012\u0004\u0012\u0002H/0-H\u0007J\"\u00101\u001a\b\u0012\u0004\u0012\u0002H.02\"\u0004\b\u0000\u0010.2\f\u00103\u001a\b\u0012\u0004\u0012\u0002H.02H\u0007J\"\u00104\u001a\b\u0012\u0004\u0012\u0002H.05\"\u0004\b\u0000\u0010.2\f\u00106\u001a\b\u0012\u0004\u0012\u0002H.05H\u0007J4\u00104\u001a\u000e\u0012\u0004\u0012\u0002H.\u0012\u0004\u0012\u0002H807\"\u0004\b\u0000\u0010.\"\u0004\b\u0001\u001082\u0012\u00109\u001a\u000e\u0012\u0004\u0012\u0002H.\u0012\u0004\u0012\u0002H807H\u0007J\"\u0010:\u001a\b\u0012\u0004\u0012\u0002H.0;\"\u0004\b\u0000\u0010.2\f\u0010<\u001a\b\u0012\u0004\u0012\u0002H.0;H\u0007J\u0010\u0010=\u001a\u00020>2\u0006\u0010?\u001a\u00020>H\u0007J\u000b\u0010@\u001a\u00070\n¢\u0006\u0002\b\u000bJ\u0012\u0010A\u001a\u00020\u00172\b\u0010B\u001a\u0004\u0018\u00010\rH\u0007J\u0012\u0010C\u001a\u00020\u00172\b\u0010B\u001a\u0004\u0018\u00010\rH\u0007J\u0010\u0010D\u001a\u00020E2\u0006\u0010F\u001a\u00020\rH\u0007J\u0012\u0010G\u001a\u00020E2\b\u0010B\u001a\u0004\u0018\u00010\rH\u0007J\u0012\u0010H\u001a\u00020I2\b\u0010B\u001a\u0004\u0018\u00010\rH\u0007J/\u0010H\u001a\u0002H.\"\u0004\b\u0000\u0010.2\b\u0010B\u001a\u0004\u0018\u00010\r2\f\u00106\u001a\b\u0012\u0004\u0012\u0002H.05H\u0087\bø\u0001\u0000¢\u0006\u0002\u0010JJ\u001a\u0010H\u001a\u00020E2\b\u0010B\u001a\u0004\u0018\u00010\r2\u0006\u00106\u001a\u00020>H\u0007J+\u0010H\u001a\u0002H.\"\u0004\b\u0000\u0010.2\b\u0010B\u001a\u0004\u0018\u00010\r2\f\u00106\u001a\b\u0012\u0004\u0012\u0002H.02H\u0007¢\u0006\u0002\u0010KJ\u000e\u0010L\u001a\u00020M*\u0004\u0018\u00010\rH\u0007R\u001a\u0010\u0003\u001a\u00020\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\bR\u0013\u0010\t\u001a\u00070\n¢\u0006\u0002\b\u000bX\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u0010\f\u001a\u00020\r8FX\u0087\u0004¢\u0006\f\u0012\u0004\b\u000e\u0010\u0002\u001a\u0004\b\u000f\u0010\u0010R\u001c\u0010\u0011\u001a\u0004\u0018\u00010\r8FX\u0087\u0004¢\u0006\f\u0012\u0004\b\u0012\u0010\u0002\u001a\u0004\b\u0013\u0010\u0010R\u0011\u0010\u0014\u001a\u00020\r¢\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\u0010R\u001a\u0010\u0016\u001a\u00020\u00178FX\u0087\u0004¢\u0006\f\u0012\u0004\b\u0018\u0010\u0002\u001a\u0004\b\u0016\u0010\u0019R,\u0010\u001b\u001a\u00020\r2\u0006\u0010\u001a\u001a\u00020\r8\u0006@BX\u0087\u000e¢\u0006\u0014\n\u0000\u0012\u0004\b\u001c\u0010\u0002\u001a\u0004\b\u001d\u0010\u0010\"\u0004\b\u001e\u0010\u001fR$\u0010 \u001a\u00020\u00178\u0006@\u0006X\u0087\u000e¢\u0006\u0014\n\u0000\u0012\u0004\b!\u0010\u0002\u001a\u0004\b\"\u0010\u0019\"\u0004\b#\u0010$R \u0010%\u001a\u00020\u0017*\u0004\u0018\u00010\r8FX\u0087\u0004¢\u0006\f\u0012\u0004\b&\u0010\u001f\u001a\u0004\b%\u0010'R \u0010(\u001a\u00020\u0017*\u0004\u0018\u00010\r8FX\u0087\u0004¢\u0006\f\u0012\u0004\b)\u0010\u001f\u001a\u0004\b(\u0010'\u0082\u0002\u0007\n\u0005\b\u009920\u0001¨\u0006N"}, d2 = {"Lorg/jetbrains/kotlin/com/intellij/codeWithMe/ClientId$Companion;", "", "()V", "AbsenceBehaviorValue", "Lorg/jetbrains/kotlin/com/intellij/codeWithMe/ClientId$AbsenceBehavior;", "getAbsenceBehaviorValue", "()Lcom/intellij/codeWithMe/ClientId$AbsenceBehavior;", "setAbsenceBehaviorValue", "(Lcom/intellij/codeWithMe/ClientId$AbsenceBehavior;)V", "LOG", "Lorg/jetbrains/kotlin/com/intellij/openapi/diagnostic/Logger;", "Lorg/jetbrains/annotations/NotNull;", "current", "Lorg/jetbrains/kotlin/com/intellij/codeWithMe/ClientId;", "getCurrent$annotations", "getCurrent", "()Lcom/intellij/codeWithMe/ClientId;", "currentOrNull", "getCurrentOrNull$annotations", "getCurrentOrNull", "defaultLocalId", "getDefaultLocalId", "isCurrentlyUnderLocalId", "", "isCurrentlyUnderLocalId$annotations", "()Z", "<set-?>", "localId", "getLocalId$annotations", "getLocalId", "setLocalId", "(Lcom/intellij/codeWithMe/ClientId;)V", "propagateAcrossThreads", "getPropagateAcrossThreads$annotations", "getPropagateAcrossThreads", "setPropagateAcrossThreads", "(Z)V", "isLocal", "isLocal$annotations", "(Lcom/intellij/codeWithMe/ClientId;)Z", "isValid", "isValid$annotations", "coroutineContext", "Lkotlin/coroutines/CoroutineContext;", "decorateBiConsumer", "Ljava/util/function/BiConsumer;", ExifInterface.GPS_DIRECTION_TRUE, "U", "biConsumer", "decorateCallable", "Ljava/util/concurrent/Callable;", "callable", "decorateFunction", "Lkotlin/Function0;", "action", "Ljava/util/function/Function;", "R", "function", "decorateProcessor", "Lorg/jetbrains/kotlin/com/intellij/util/Processor;", "processor", "decorateRunnable", "Ljava/lang/Runnable;", "runnable", "getClientIdLogger", "isLocalId", "clientId", "isValidId", "overrideLocalId", "", "newId", "trySetCurrentClientId", "withClientId", "Lorg/jetbrains/kotlin/com/intellij/openapi/application/AccessToken;", "(Lcom/intellij/codeWithMe/ClientId;Lkotlin/jvm/functions/Function0;)Ljava/lang/Object;", "(Lcom/intellij/codeWithMe/ClientId;Ljava/util/concurrent/Callable;)Ljava/lang/Object;", "toDisposable", "Lorg/jetbrains/kotlin/com/intellij/openapi/Disposable;", "intellij.platform.core"}, k = 1, mv = {1, 5, 1})
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public final Logger getClientIdLogger() {
            return ClientId.LOG;
        }

        public final boolean getPropagateAcrossThreads() {
            return ClientId.propagateAcrossThreads;
        }

        public final ClientId getCurrentOrNull() {
            String clientIdValue;
            ClientIdService clientIdServiceTryGetInstance = ClientIdService.INSTANCE.tryGetInstance();
            if (clientIdServiceTryGetInstance == null || (clientIdValue = clientIdServiceTryGetInstance.getClientIdValue()) == null) {
                return null;
            }
            return new ClientId(clientIdValue);
        }

        @JvmStatic
        public final AccessToken withClientId(final ClientId clientId) {
            return new AccessToken(clientId) { // from class: org.jetbrains.kotlin.com.intellij.codeWithMe.ClientId$Companion$withClientId$3
                final /* synthetic */ ClientId $clientId;
                private String oldClientIdValue;
                private final ClientIdService service;

                {
                    this.$clientId = clientId;
                    ClientIdService clientIdServiceTryGetInstance = ClientIdService.INSTANCE.tryGetInstance();
                    this.service = clientIdServiceTryGetInstance;
                    if (clientIdServiceTryGetInstance != null) {
                        String value = null;
                        if (!clientIdServiceTryGetInstance.isValid(clientId)) {
                            Logger logger = ClientId.LOG;
                            if (logger.isTraceEnabled()) {
                                logger.trace("Invalid ClientId " + clientId + " replaced with null at " + new Throwable().fillInStackTrace());
                            }
                        } else if (clientId != null) {
                            value = clientId.getValue();
                        }
                        this.oldClientIdValue = clientIdServiceTryGetInstance.getClientIdValue();
                        clientIdServiceTryGetInstance.setClientIdValue(value);
                    }
                }

                @Override // org.jetbrains.kotlin.com.intellij.openapi.application.AccessToken
                public void finish() {
                    ClientIdService clientIdService = this.service;
                    if (clientIdService != null) {
                        clientIdService.setClientIdValue(this.oldClientIdValue);
                    }
                }
            };
        }

        @JvmStatic
        public final Runnable decorateRunnable(final Runnable runnable) {
            Intrinsics.checkNotNullParameter(runnable, "runnable");
            if (!getPropagateAcrossThreads()) {
                return runnable;
            }
            final ClientId currentOrNull = getCurrentOrNull();
            return new Runnable() { // from class: org.jetbrains.kotlin.com.intellij.codeWithMe.ClientId$Companion$decorateRunnable$1
                @Override // java.lang.Runnable
                public final void run() {
                    ClientId.Companion companion = ClientId.INSTANCE;
                    ClientId clientId = currentOrNull;
                    ClientIdService clientIdServiceTryGetInstance = ClientIdService.INSTANCE.tryGetInstance();
                    if (clientIdServiceTryGetInstance == null) {
                        runnable.run();
                        return;
                    }
                    String value = null;
                    if (!clientIdServiceTryGetInstance.isValid(clientId)) {
                        Logger clientIdLogger = companion.getClientIdLogger();
                        if (clientIdLogger.isTraceEnabled()) {
                            clientIdLogger.trace("Invalid ClientId " + clientId + " replaced with null at " + new Throwable().fillInStackTrace());
                        }
                    } else if (clientId != null) {
                        value = clientId.getValue();
                    }
                    String clientIdValue = clientIdServiceTryGetInstance.getClientIdValue();
                    try {
                        clientIdServiceTryGetInstance.setClientIdValue(value);
                        runnable.run();
                        Unit unit = Unit.INSTANCE;
                    } finally {
                        clientIdServiceTryGetInstance.setClientIdValue(clientIdValue);
                    }
                }
            };
        }

        @JvmStatic
        public final <T, R> Function<T, R> decorateFunction(final Function<T, R> function) {
            Intrinsics.checkNotNullParameter(function, "function");
            if (!getPropagateAcrossThreads()) {
                return function;
            }
            final ClientId currentOrNull = getCurrentOrNull();
            return new Function() { // from class: org.jetbrains.kotlin.com.intellij.codeWithMe.ClientId$Companion$decorateFunction$2
                @Override // java.util.function.Function
                public final R apply(T t) {
                    ClientId.Companion companion = ClientId.INSTANCE;
                    ClientId clientId = currentOrNull;
                    ClientIdService clientIdServiceTryGetInstance = ClientIdService.INSTANCE.tryGetInstance();
                    if (clientIdServiceTryGetInstance == null) {
                        return (R) function.apply(t);
                    }
                    String value = null;
                    if (!clientIdServiceTryGetInstance.isValid(clientId)) {
                        Logger clientIdLogger = companion.getClientIdLogger();
                        if (clientIdLogger.isTraceEnabled()) {
                            clientIdLogger.trace("Invalid ClientId " + clientId + " replaced with null at " + new Throwable().fillInStackTrace());
                        }
                    } else if (clientId != null) {
                        value = clientId.getValue();
                    }
                    String clientIdValue = clientIdServiceTryGetInstance.getClientIdValue();
                    try {
                        clientIdServiceTryGetInstance.setClientIdValue(value);
                        return (R) function.apply(t);
                    } finally {
                        clientIdServiceTryGetInstance.setClientIdValue(clientIdValue);
                    }
                }
            };
        }

        @JvmStatic
        public final <T, U> BiConsumer<T, U> decorateBiConsumer(final BiConsumer<T, U> biConsumer) {
            Intrinsics.checkNotNullParameter(biConsumer, "biConsumer");
            if (!getPropagateAcrossThreads()) {
                return biConsumer;
            }
            final ClientId currentOrNull = getCurrentOrNull();
            return new BiConsumer() { // from class: org.jetbrains.kotlin.com.intellij.codeWithMe.ClientId$Companion$decorateBiConsumer$1
                @Override // java.util.function.BiConsumer
                public final void accept(T t, U u) {
                    ClientId.Companion companion = ClientId.INSTANCE;
                    ClientId clientId = currentOrNull;
                    ClientIdService clientIdServiceTryGetInstance = ClientIdService.INSTANCE.tryGetInstance();
                    if (clientIdServiceTryGetInstance == null) {
                        biConsumer.accept(t, u);
                        return;
                    }
                    String value = null;
                    if (!clientIdServiceTryGetInstance.isValid(clientId)) {
                        Logger clientIdLogger = companion.getClientIdLogger();
                        if (clientIdLogger.isTraceEnabled()) {
                            clientIdLogger.trace("Invalid ClientId " + clientId + " replaced with null at " + new Throwable().fillInStackTrace());
                        }
                    } else if (clientId != null) {
                        value = clientId.getValue();
                    }
                    String clientIdValue = clientIdServiceTryGetInstance.getClientIdValue();
                    try {
                        clientIdServiceTryGetInstance.setClientIdValue(value);
                        biConsumer.accept(t, u);
                        Unit unit = Unit.INSTANCE;
                    } finally {
                        clientIdServiceTryGetInstance.setClientIdValue(clientIdValue);
                    }
                }
            };
        }

        @JvmStatic
        public final void trySetCurrentClientId(ClientId clientId) {
            ClientIdService clientIdServiceTryGetInstance = ClientIdService.INSTANCE.tryGetInstance();
            if (clientIdServiceTryGetInstance != null) {
                clientIdServiceTryGetInstance.setClientIdValue(clientId != null ? clientId.getValue() : null);
            }
        }
    }

    static {
        Logger logger = Logger.getInstance((Class<?>) ClientId.class);
        Intrinsics.checkNotNullExpressionValue(logger, "Logger.getInstance(ClientId::class.java)");
        LOG = logger;
        ClientId clientId = new ClientId("Host");
        defaultLocalId = clientId;
        AbsenceBehaviorValue = AbsenceBehavior.RETURN_LOCAL;
        localId = clientId;
    }
}
