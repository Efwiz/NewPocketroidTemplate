package org.jetbrains.kotlin.cli.common;

import androidx.exifinterface.media.ExifInterface;
import io.reactivex.annotations.SchedulerSupport;
import kotlin.Metadata;
import kotlin.enums.EnumEntries;
import kotlin.enums.EnumEntriesKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.FunctionReference;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Reflection;
import kotlin.reflect.KDeclarationContainer;
import org.apache.commons.lang3.SystemProperties;
import org.jetbrains.kotlin.org.jline.reader.LineReader;

/* JADX WARN: Enum visitor error
jadx.core.utils.exceptions.JadxRuntimeException: Init of enum field 'COMPILE_DAEMON_JVM_OPTIONS_PROPERTY' uses external variables
	at jadx.core.dex.visitors.EnumVisitor.createEnumFieldByConstructor(EnumVisitor.java:485)
	at jadx.core.dex.visitors.EnumVisitor.processEnumFieldByField(EnumVisitor.java:399)
	at jadx.core.dex.visitors.EnumVisitor.processEnumFieldByWrappedInsn(EnumVisitor.java:364)
	at jadx.core.dex.visitors.EnumVisitor.extractEnumFieldsFromFilledArray(EnumVisitor.java:349)
	at jadx.core.dex.visitors.EnumVisitor.extractEnumFieldsFromInsn(EnumVisitor.java:284)
	at jadx.core.dex.visitors.EnumVisitor.extractEnumFieldsFromInvoke(EnumVisitor.java:315)
	at jadx.core.dex.visitors.EnumVisitor.extractEnumFieldsFromInsn(EnumVisitor.java:288)
	at jadx.core.dex.visitors.EnumVisitor.convertToEnum(EnumVisitor.java:160)
	at jadx.core.dex.visitors.EnumVisitor.visit(EnumVisitor.java:102)
 */
/* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
/* JADX INFO: compiled from: Properties.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u000b\n\u0002\b+\b\u0086\u0081\u0002\u0018\u0000 /2\b\u0012\u0004\u0012\u00020\u00000\u0001:\u0001/B\u0019\b\u0002\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\b\u0010\u0011\u001a\u0004\u0018\u00010\u0003J%\u0010\u0012\u001a\u0002H\u0013\"\u0004\b\u0000\u0010\u00132\b\u0010\u0014\u001a\u0004\u0018\u0001H\u00132\u0006\u0010\u0015\u001a\u0002H\u0013H\u0002¢\u0006\u0002\u0010\u0016R\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u0011\u0010\u000b\u001a\u00020\u00038F¢\u0006\u0006\u001a\u0004\b\f\u0010\nR(\u0010\r\u001a\u0004\u0018\u00010\u00032\b\u0010\r\u001a\u0004\u0018\u00010\u00038F@FX\u0086\u000e¢\u0006\f\u001a\u0004\b\u000e\u0010\n\"\u0004\b\u000f\u0010\u0010j\u0002\b\u0017j\u0002\b\u0018j\u0002\b\u0019j\u0002\b\u001aj\u0002\b\u001bj\u0002\b\u001cj\u0002\b\u001dj\u0002\b\u001ej\u0002\b\u001fj\u0002\b j\u0002\b!j\u0002\b\"j\u0002\b#j\u0002\b$j\u0002\b%j\u0002\b&j\u0002\b'j\u0002\b(j\u0002\b)j\u0002\b*j\u0002\b+j\u0002\b,j\u0002\b-j\u0002\b.¨\u00060"}, d2 = {"Lorg/jetbrains/kotlin/cli/common/CompilerSystemProperties;", "", "property", "", "alwaysDirectAccess", "", "(Ljava/lang/String;ILjava/lang/String;Z)V", "getAlwaysDirectAccess", "()Z", "getProperty", "()Ljava/lang/String;", "safeValue", "getSafeValue", "value", "getValue", "setValue", "(Ljava/lang/String;)V", LineReader.CLEAR, "getProperFunction", ExifInterface.GPS_DIRECTION_TRUE, SchedulerSupport.CUSTOM, "default", "(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;", "COMPILE_DAEMON_ENABLED_PROPERTY", "COMPILE_DAEMON_JVM_OPTIONS_PROPERTY", "COMPILE_DAEMON_OPTIONS_PROPERTY", "COMPILE_DAEMON_CLIENT_OPTIONS_PROPERTY", "COMPILE_DAEMON_CLIENT_ALIVE_PATH_PROPERTY", "COMPILE_DAEMON_LOG_PATH_PROPERTY", "COMPILE_DAEMON_REPORT_PERF_PROPERTY", "COMPILE_DAEMON_VERBOSE_REPORT_PROPERTY", "COMPILE_DAEMON_STARTUP_TIMEOUT_PROPERTY", "JAVA_RMI_SERVER_HOSTNAME", "DAEMON_RMI_SOCKET_BACKLOG_SIZE_PROPERTY", "DAEMON_RMI_SOCKET_CONNECT_ATTEMPTS_PROPERTY", "DAEMON_RMI_SOCKET_CONNECT_INTERVAL_PROPERTY", "KOTLIN_COMPILER_ENVIRONMENT_KEEPALIVE_PROPERTY", "COMPILE_DAEMON_CUSTOM_RUN_FILES_PATH_FOR_TESTS", "COMPILE_INCREMENTAL_WITH_ARTIFACT_TRANSFORM", "KOTLIN_COLORS_ENABLED_PROPERTY", "LANGUAGE_VERSION_SETTINGS", "OS_NAME", "TMP_DIR", "USER_HOME", "JAVA_VERSION", "JAVA_HOME", "JAVA_CLASS_PATH", "Companion", "cli-common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class CompilerSystemProperties {
    private static final /* synthetic */ EnumEntries $ENTRIES;
    private static final /* synthetic */ CompilerSystemProperties[] $VALUES;
    public static final CompilerSystemProperties COMPILE_DAEMON_CLIENT_ALIVE_PATH_PROPERTY;
    public static final CompilerSystemProperties COMPILE_DAEMON_CLIENT_OPTIONS_PROPERTY;
    public static final CompilerSystemProperties COMPILE_DAEMON_CUSTOM_RUN_FILES_PATH_FOR_TESTS;
    public static final CompilerSystemProperties COMPILE_DAEMON_JVM_OPTIONS_PROPERTY;
    public static final CompilerSystemProperties COMPILE_DAEMON_LOG_PATH_PROPERTY;
    public static final CompilerSystemProperties COMPILE_DAEMON_OPTIONS_PROPERTY;
    public static final CompilerSystemProperties COMPILE_DAEMON_REPORT_PERF_PROPERTY;
    public static final CompilerSystemProperties COMPILE_DAEMON_STARTUP_TIMEOUT_PROPERTY;
    public static final CompilerSystemProperties COMPILE_DAEMON_VERBOSE_REPORT_PROPERTY;
    public static final CompilerSystemProperties COMPILE_INCREMENTAL_WITH_ARTIFACT_TRANSFORM;

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE;
    public static final CompilerSystemProperties DAEMON_RMI_SOCKET_BACKLOG_SIZE_PROPERTY;
    public static final CompilerSystemProperties DAEMON_RMI_SOCKET_CONNECT_ATTEMPTS_PROPERTY;
    public static final CompilerSystemProperties DAEMON_RMI_SOCKET_CONNECT_INTERVAL_PROPERTY;
    public static final CompilerSystemProperties JAVA_RMI_SERVER_HOSTNAME;
    public static final CompilerSystemProperties KOTLIN_COLORS_ENABLED_PROPERTY;
    public static final CompilerSystemProperties KOTLIN_COMPILER_ENVIRONMENT_KEEPALIVE_PROPERTY;
    public static final CompilerSystemProperties LANGUAGE_VERSION_SETTINGS;
    private static Function1<? super String, String> systemPropertyCleaner;
    private static Function1<? super String, String> systemPropertyGetter;
    private static Function2<? super String, ? super String, String> systemPropertySetter;
    private final boolean alwaysDirectAccess;
    private final String property;
    public static final CompilerSystemProperties COMPILE_DAEMON_ENABLED_PROPERTY = new CompilerSystemProperties("COMPILE_DAEMON_ENABLED_PROPERTY", 0, "kotlin.daemon.enabled", false, 2, null);
    public static final CompilerSystemProperties OS_NAME = new CompilerSystemProperties("OS_NAME", 18, SystemProperties.OS_NAME, true);
    public static final CompilerSystemProperties TMP_DIR = new CompilerSystemProperties("TMP_DIR", 19, "java.io.tmpdir", false, 2, null);
    public static final CompilerSystemProperties USER_HOME = new CompilerSystemProperties("USER_HOME", 20, "user.home", true);
    public static final CompilerSystemProperties JAVA_VERSION = new CompilerSystemProperties("JAVA_VERSION", 21, SystemProperties.JAVA_SPECIFICATION_VERSION, true);
    public static final CompilerSystemProperties JAVA_HOME = new CompilerSystemProperties("JAVA_HOME", 22, "java.home", true);
    public static final CompilerSystemProperties JAVA_CLASS_PATH = new CompilerSystemProperties("JAVA_CLASS_PATH", 23, SystemProperties.JAVA_CLASS_PATH, true);

    private static final /* synthetic */ CompilerSystemProperties[] $values() {
        return new CompilerSystemProperties[]{COMPILE_DAEMON_ENABLED_PROPERTY, COMPILE_DAEMON_JVM_OPTIONS_PROPERTY, COMPILE_DAEMON_OPTIONS_PROPERTY, COMPILE_DAEMON_CLIENT_OPTIONS_PROPERTY, COMPILE_DAEMON_CLIENT_ALIVE_PATH_PROPERTY, COMPILE_DAEMON_LOG_PATH_PROPERTY, COMPILE_DAEMON_REPORT_PERF_PROPERTY, COMPILE_DAEMON_VERBOSE_REPORT_PROPERTY, COMPILE_DAEMON_STARTUP_TIMEOUT_PROPERTY, JAVA_RMI_SERVER_HOSTNAME, DAEMON_RMI_SOCKET_BACKLOG_SIZE_PROPERTY, DAEMON_RMI_SOCKET_CONNECT_ATTEMPTS_PROPERTY, DAEMON_RMI_SOCKET_CONNECT_INTERVAL_PROPERTY, KOTLIN_COMPILER_ENVIRONMENT_KEEPALIVE_PROPERTY, COMPILE_DAEMON_CUSTOM_RUN_FILES_PATH_FOR_TESTS, COMPILE_INCREMENTAL_WITH_ARTIFACT_TRANSFORM, KOTLIN_COLORS_ENABLED_PROPERTY, LANGUAGE_VERSION_SETTINGS, OS_NAME, TMP_DIR, USER_HOME, JAVA_VERSION, JAVA_HOME, JAVA_CLASS_PATH};
    }

    public static EnumEntries<CompilerSystemProperties> getEntries() {
        return $ENTRIES;
    }

    public static CompilerSystemProperties valueOf(String str) {
        return (CompilerSystemProperties) Enum.valueOf(CompilerSystemProperties.class, str);
    }

    public static CompilerSystemProperties[] values() {
        return (CompilerSystemProperties[]) $VALUES.clone();
    }

    public final String getSafeValue() {
        String value = getValue();
        if (value != null) {
            return value;
        }
        throw new IllegalStateException(("No value for " + this.property + " system property").toString());
    }

    private CompilerSystemProperties(String str, int i, String str2, boolean z) {
        super(str, i);
        this.property = str2;
        this.alwaysDirectAccess = z;
    }

    /* synthetic */ CompilerSystemProperties(String str, int i, String str2, boolean z, int i2, DefaultConstructorMarker defaultConstructorMarker) {
        this(str, i, str2, (i2 & 2) != 0 ? false : z);
    }

    public final boolean getAlwaysDirectAccess() {
        return this.alwaysDirectAccess;
    }

    public final String getProperty() {
        return this.property;
    }

    static {
        int i = 2;
        DefaultConstructorMarker defaultConstructorMarker = null;
        boolean z = false;
        COMPILE_DAEMON_JVM_OPTIONS_PROPERTY = new CompilerSystemProperties("COMPILE_DAEMON_JVM_OPTIONS_PROPERTY", 1, "kotlin.daemon.jvm.options", z, i, defaultConstructorMarker);
        int i2 = 2;
        DefaultConstructorMarker defaultConstructorMarker2 = null;
        boolean z2 = false;
        COMPILE_DAEMON_OPTIONS_PROPERTY = new CompilerSystemProperties("COMPILE_DAEMON_OPTIONS_PROPERTY", 2, "kotlin.daemon.options", z2, i2, defaultConstructorMarker2);
        COMPILE_DAEMON_CLIENT_OPTIONS_PROPERTY = new CompilerSystemProperties("COMPILE_DAEMON_CLIENT_OPTIONS_PROPERTY", 3, "kotlin.daemon.client.options", z, i, defaultConstructorMarker);
        COMPILE_DAEMON_CLIENT_ALIVE_PATH_PROPERTY = new CompilerSystemProperties("COMPILE_DAEMON_CLIENT_ALIVE_PATH_PROPERTY", 4, "kotlin.daemon.client.alive.path", z2, i2, defaultConstructorMarker2);
        COMPILE_DAEMON_LOG_PATH_PROPERTY = new CompilerSystemProperties("COMPILE_DAEMON_LOG_PATH_PROPERTY", 5, "kotlin.daemon.log.path", z, i, defaultConstructorMarker);
        COMPILE_DAEMON_REPORT_PERF_PROPERTY = new CompilerSystemProperties("COMPILE_DAEMON_REPORT_PERF_PROPERTY", 6, "kotlin.daemon.perf", z2, i2, defaultConstructorMarker2);
        COMPILE_DAEMON_VERBOSE_REPORT_PROPERTY = new CompilerSystemProperties("COMPILE_DAEMON_VERBOSE_REPORT_PROPERTY", 7, "kotlin.daemon.verbose", z, i, defaultConstructorMarker);
        COMPILE_DAEMON_STARTUP_TIMEOUT_PROPERTY = new CompilerSystemProperties("COMPILE_DAEMON_STARTUP_TIMEOUT_PROPERTY", 8, "kotlin.daemon.startup.timeout", z2, i2, defaultConstructorMarker2);
        JAVA_RMI_SERVER_HOSTNAME = new CompilerSystemProperties("JAVA_RMI_SERVER_HOSTNAME", 9, "java.rmi.server.hostname", z, i, defaultConstructorMarker);
        DAEMON_RMI_SOCKET_BACKLOG_SIZE_PROPERTY = new CompilerSystemProperties("DAEMON_RMI_SOCKET_BACKLOG_SIZE_PROPERTY", 10, "kotlin.daemon.socket.backlog.size", z2, i2, defaultConstructorMarker2);
        DAEMON_RMI_SOCKET_CONNECT_ATTEMPTS_PROPERTY = new CompilerSystemProperties("DAEMON_RMI_SOCKET_CONNECT_ATTEMPTS_PROPERTY", 11, "kotlin.daemon.socket.connect.attempts", z, i, defaultConstructorMarker);
        DAEMON_RMI_SOCKET_CONNECT_INTERVAL_PROPERTY = new CompilerSystemProperties("DAEMON_RMI_SOCKET_CONNECT_INTERVAL_PROPERTY", 12, "kotlin.daemon.socket.connect.interval", z2, i2, defaultConstructorMarker2);
        KOTLIN_COMPILER_ENVIRONMENT_KEEPALIVE_PROPERTY = new CompilerSystemProperties("KOTLIN_COMPILER_ENVIRONMENT_KEEPALIVE_PROPERTY", 13, "kotlin.environment.keepalive", z, i, defaultConstructorMarker);
        COMPILE_DAEMON_CUSTOM_RUN_FILES_PATH_FOR_TESTS = new CompilerSystemProperties("COMPILE_DAEMON_CUSTOM_RUN_FILES_PATH_FOR_TESTS", 14, "kotlin.daemon.custom.run.files.path.for.tests", z2, i2, defaultConstructorMarker2);
        COMPILE_INCREMENTAL_WITH_ARTIFACT_TRANSFORM = new CompilerSystemProperties("COMPILE_INCREMENTAL_WITH_ARTIFACT_TRANSFORM", 15, "kotlin.incremental.useClasspathSnapshot", z, i, defaultConstructorMarker);
        KOTLIN_COLORS_ENABLED_PROPERTY = new CompilerSystemProperties("KOTLIN_COLORS_ENABLED_PROPERTY", 16, "kotlin.colors.enabled", z2, i2, defaultConstructorMarker2);
        LANGUAGE_VERSION_SETTINGS = new CompilerSystemProperties("LANGUAGE_VERSION_SETTINGS", 17, "kotlin.language.settings", z, i, defaultConstructorMarker);
        CompilerSystemProperties[] compilerSystemPropertiesArr$values = $values();
        $VALUES = compilerSystemPropertiesArr$values;
        $ENTRIES = EnumEntriesKt.enumEntries(compilerSystemPropertiesArr$values);
        INSTANCE = new Companion(null);
    }

    private final <T> T getProperFunction(T custom, T t) {
        return (this.alwaysDirectAccess || custom == null) ? t : custom;
    }

    public final String getValue() {
        return (String) ((Function1) getProperFunction(systemPropertyGetter, CompilerSystemProperties$value$1.INSTANCE)).invoke(this.property);
    }

    public final void setValue(String str) {
        Function2 function2 = (Function2) getProperFunction(systemPropertySetter, CompilerSystemProperties$value$2.INSTANCE);
        String str2 = this.property;
        Intrinsics.checkNotNull(str);
        function2.invoke(str2, str);
    }

    /* JADX INFO: renamed from: org.jetbrains.kotlin.cli.common.CompilerSystemProperties$clear$1, reason: invalid class name */
    /* JADX INFO: compiled from: Properties.kt */
    @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
    /* synthetic */ class AnonymousClass1 extends FunctionReference implements Function1<String, String> {
        public static final AnonymousClass1 INSTANCE = new AnonymousClass1();

        AnonymousClass1() {
            super(1);
        }

        @Override // kotlin.jvm.internal.CallableReference
        public final String getSignature() {
            return "clearProperty(Ljava/lang/String;)Ljava/lang/String;";
        }

        @Override // kotlin.jvm.internal.CallableReference, kotlin.reflect.KCallable
        public final String getName() {
            return "clearProperty";
        }

        @Override // kotlin.jvm.internal.CallableReference
        public final KDeclarationContainer getOwner() {
            return Reflection.getOrCreateKotlinClass(System.class);
        }

        @Override // kotlin.jvm.functions.Function1
        public final String invoke(String str) {
            return System.clearProperty(str);
        }
    }

    public final String clear() {
        return (String) ((Function1) getProperFunction(systemPropertyCleaner, AnonymousClass1.INSTANCE)).invoke(this.property);
    }

    /* JADX INFO: compiled from: Properties.kt */
    @Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0005\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R*\u0010\u0003\u001a\u0012\u0012\u0004\u0012\u00020\u0005\u0012\u0006\u0012\u0004\u0018\u00010\u0005\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0006\u0010\u0007\"\u0004\b\b\u0010\tR*\u0010\n\u001a\u0012\u0012\u0004\u0012\u00020\u0005\u0012\u0006\u0012\u0004\u0018\u00010\u0005\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000b\u0010\u0007\"\u0004\b\f\u0010\tR0\u0010\r\u001a\u0018\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u0005\u0012\u0006\u0012\u0004\u0018\u00010\u0005\u0018\u00010\u000eX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000f\u0010\u0010\"\u0004\b\u0011\u0010\u0012¨\u0006\u0013"}, d2 = {"Lorg/jetbrains/kotlin/cli/common/CompilerSystemProperties$Companion;", "", "()V", "systemPropertyCleaner", "Lkotlin/Function1;", "", "getSystemPropertyCleaner", "()Lkotlin/jvm/functions/Function1;", "setSystemPropertyCleaner", "(Lkotlin/jvm/functions/Function1;)V", "systemPropertyGetter", "getSystemPropertyGetter", "setSystemPropertyGetter", "systemPropertySetter", "Lkotlin/Function2;", "getSystemPropertySetter", "()Lkotlin/jvm/functions/Function2;", "setSystemPropertySetter", "(Lkotlin/jvm/functions/Function2;)V", "cli-common"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final Function1<String, String> getSystemPropertyGetter() {
            return CompilerSystemProperties.systemPropertyGetter;
        }

        public final void setSystemPropertyGetter(Function1<? super String, String> function1) {
            CompilerSystemProperties.systemPropertyGetter = function1;
        }

        public final Function2<String, String, String> getSystemPropertySetter() {
            return CompilerSystemProperties.systemPropertySetter;
        }

        public final void setSystemPropertySetter(Function2<? super String, ? super String, String> function2) {
            CompilerSystemProperties.systemPropertySetter = function2;
        }

        public final Function1<String, String> getSystemPropertyCleaner() {
            return CompilerSystemProperties.systemPropertyCleaner;
        }

        public final void setSystemPropertyCleaner(Function1<? super String, String> function1) {
            CompilerSystemProperties.systemPropertyCleaner = function1;
        }
    }
}
