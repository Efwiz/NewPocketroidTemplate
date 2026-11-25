package org.jetbrains.kotlin.daemon.common;

import androidx.exifinterface.media.ExifInterface;
import com.beust.jcommander.Parameters;
import java.lang.management.ManagementFactory;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.collections.ArraysKt;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.MutablePropertyReference1;
import kotlin.jvm.internal.Reflection;
import kotlin.reflect.KDeclarationContainer;
import kotlin.reflect.KMutableProperty1;
import kotlin.text.Charsets;
import kotlin.text.MatchGroup;
import kotlin.text.MatchGroupCollection;
import kotlin.text.MatchResult;
import kotlin.text.Regex;
import kotlin.text.StringsKt;
import net.bytebuddy.asm.Advice;
import org.jetbrains.kotlin.cli.common.CompilerSystemProperties;
import org.jetbrains.kotlin.incremental.IncrementalJvmCacheKt;
import org.jetbrains.kotlin.load.java.JvmAnnotationNames;

/* JADX INFO: compiled from: DaemonParams.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000~\n\u0000\n\u0002\u0010\u000e\n\u0002\b\b\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010\t\n\u0002\b\n\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\u0011\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0005\n\u0002\u0010\u001c\n\u0002\b\u0003\n\u0002\u0010\u0012\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\t\u001a\u0016\u0010#\u001a\u00020\n2\u0006\u0010$\u001a\u00020\u001b2\u0006\u0010%\u001a\u00020\u001b\u001a7\u0010&\u001a\u00020\u001b2\u0012\u0010'\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00010(\"\u00020\u00012\u0006\u0010)\u001a\u00020*2\u0006\u0010+\u001a\u00020*2\u0006\u0010,\u001a\u00020*¢\u0006\u0002\u0010-\u001a?\u0010&\u001a\u00020\u001b2\u0006\u0010.\u001a\u00020\u001b2\u0012\u0010'\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00010(\"\u00020\u00012\u0006\u0010)\u001a\u00020*2\u0006\u0010+\u001a\u00020*2\u0006\u0010,\u001a\u00020*¢\u0006\u0002\u0010/\u001a4\u0010&\u001a\u00020\u001b2\u0006\u0010.\u001a\u00020\u001b2\f\u0010'\u001a\b\u0012\u0004\u0012\u00020\u0001002\u0006\u0010)\u001a\u00020*2\u0006\u0010+\u001a\u00020*2\u0006\u0010,\u001a\u00020*\u001a\u0006\u00101\u001a\u00020 \u001a\u000e\u00101\u001a\u00020 2\u0006\u0010.\u001a\u00020 \u001a\u0006\u00102\u001a\u00020*\u001a\u0010\u00103\u001a\u000204*\b\u0012\u0004\u0012\u00020\u000100\u001a7\u00105\u001a\b\u0012\u0004\u0012\u00020\u000100*\b\u0012\u0004\u0012\u00020\u0001002\u0012\u00106\u001a\n\u0012\u0006\b\u0001\u0012\u0002070(\"\u0002072\u0006\u00108\u001a\u00020\u0001¢\u0006\u0002\u00109\u001aL\u00105\u001a\b\u0012\u0004\u0012\u00020\u000100*\b\u0012\u0004\u0012\u00020\u0001002\u0018\u0010:\u001a\u0014\u0012\u0010\u0012\u000e\u0012\u0002\b\u0003\u0012\u0002\b\u0003\u0012\u0002\b\u00030;0\u00192\u0006\u00108\u001a\u00020\u00012\u0012\b\u0002\u0010<\u001a\f\u0012\u0002\b\u0003\u0012\u0002\b\u0003\u0018\u00010=\u001aO\u0010>\u001a\u0004\u0018\u0001H?\"\u0004\b\u0000\u0010@\"\b\b\u0001\u0010?*\u00020A*\b\u0012\u0004\u0012\u0002H@002 \u0010B\u001a\u001c\u0012\u0004\u0012\u0002H@\u0012\u0012\u0012\u0010\u0012\u0004\u0012\u00020*\u0012\u0006\u0012\u0004\u0018\u0001H?0D0CH\u0086\bø\u0001\u0000¢\u0006\u0002\u0010E\u001a\u0013\u0010F\u001a\u0004\u0018\u00010\u000e*\u00020\u0001H\u0002¢\u0006\u0002\u0010G\u001a\u0015\u0010H\u001a\u00020**\u00020\u001b2\u0006\u0010I\u001a\u00020\u001bH\u0086\u0004\u001a\n\u0010J\u001a\u00020\u0001*\u000204\u001a\n\u0010K\u001a\u00020\u0001*\u00020\u0001\u001a\u0012\u0010L\u001a\u00020\u001b*\u00020\u001b2\u0006\u0010I\u001a\u00020\u001b\"\u0014\u0010\u0000\u001a\u00020\u0001X\u0086D¢\u0006\b\n\u0000\u001a\u0004\b\u0002\u0010\u0003\"\u000e\u0010\u0004\u001a\u00020\u0001X\u0086T¢\u0006\u0002\n\u0000\"\u000e\u0010\u0005\u001a\u00020\u0001X\u0086T¢\u0006\u0002\n\u0000\"\u000e\u0010\u0006\u001a\u00020\u0001X\u0086T¢\u0006\u0002\n\u0000\"\u000e\u0010\u0007\u001a\u00020\u0001X\u0086T¢\u0006\u0002\n\u0000\"\u000e\u0010\b\u001a\u00020\u0001X\u0086T¢\u0006\u0002\n\u0000\"\u000e\u0010\t\u001a\u00020\nX\u0086T¢\u0006\u0002\n\u0000\"\u0011\u0010\u000b\u001a\u00020\u00018F¢\u0006\u0006\u001a\u0004\b\f\u0010\u0003\"\u000e\u0010\r\u001a\u00020\u000eX\u0086T¢\u0006\u0002\n\u0000\"\u000e\u0010\u000f\u001a\u00020\nX\u0086T¢\u0006\u0002\n\u0000\"\u000e\u0010\u0010\u001a\u00020\nX\u0086T¢\u0006\u0002\n\u0000\"\u000e\u0010\u0011\u001a\u00020\u000eX\u0086T¢\u0006\u0002\n\u0000\"\u000e\u0010\u0012\u001a\u00020\u0001X\u0086T¢\u0006\u0002\n\u0000\"\u000e\u0010\u0013\u001a\u00020\u000eX\u0086T¢\u0006\u0002\n\u0000\"\u000e\u0010\u0014\u001a\u00020\nX\u0086T¢\u0006\u0002\n\u0000\"\u000e\u0010\u0015\u001a\u00020\nX\u0086T¢\u0006\u0002\n\u0000\"\u000e\u0010\u0016\u001a\u00020\u000eX\u0086T¢\u0006\u0002\n\u0000\"\u000e\u0010\u0017\u001a\u00020\nX\u0086T¢\u0006\u0002\n\u0000\" \u0010\u0018\u001a\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020\u001b\u0012\u0004\u0012\u00020\u00010\u001a0\u0019X\u0082\u0004¢\u0006\u0002\n\u0000\"\u000e\u0010\u001c\u001a\u00020\u001dX\u0082\u0004¢\u0006\u0002\n\u0000\"\u000e\u0010\u001e\u001a\u00020\u001dX\u0082\u0004¢\u0006\u0002\n\u0000\"\u0015\u0010\u001f\u001a\u00020\u0001*\u00020 8F¢\u0006\u0006\u001a\u0004\b!\u0010\"\u0082\u0002\u0007\n\u0005\b\u009920\u0001¨\u0006M"}, d2 = {"CLASSPATH_ID_DIGEST", "", "getCLASSPATH_ID_DIGEST", "()Ljava/lang/String;", "COMPILER_DAEMON_CLASS_FQN", "COMPILER_JAR_NAME", "COMPILER_SERVICE_RMI_NAME", "COMPILE_DAEMON_CMDLINE_OPTIONS_PREFIX", "COMPILE_DAEMON_DEFAULT_FILES_PREFIX", "COMPILE_DAEMON_DEFAULT_IDLE_TIMEOUT_S", "", "COMPILE_DAEMON_DEFAULT_RUN_DIR_PATH", "getCOMPILE_DAEMON_DEFAULT_RUN_DIR_PATH", "COMPILE_DAEMON_DEFAULT_SHUTDOWN_DELAY_MS", "", "COMPILE_DAEMON_DEFAULT_UNUSED_TIMEOUT_S", "COMPILE_DAEMON_FIND_PORT_ATTEMPTS", "COMPILE_DAEMON_FORCE_SHUTDOWN_DEFAULT_TIMEOUT_MS", "COMPILE_DAEMON_IS_READY_MESSAGE", "COMPILE_DAEMON_MEMORY_THRESHOLD_INFINITE", "COMPILE_DAEMON_PORTS_RANGE_END", "COMPILE_DAEMON_PORTS_RANGE_START", "COMPILE_DAEMON_TIMEOUT_INFINITE_MS", "COMPILE_DAEMON_TIMEOUT_INFINITE_S", "daemonJVMOptionsMemoryProps", "", "Lkotlin/reflect/KMutableProperty1;", "Lorg/jetbrains/kotlin/daemon/common/DaemonJVMOptions;", "humanizedMemorySizeRegex", "Lkotlin/text/Regex;", "jvmAssertArgsRegex", "runFilesPathOrDefault", "Lorg/jetbrains/kotlin/daemon/common/DaemonOptions;", "getRunFilesPathOrDefault", "(Lorg/jetbrains/kotlin/daemon/common/DaemonOptions;)Ljava/lang/String;", "compareDaemonJVMOptionsMemory", "left", "right", "configureDaemonJVMOptions", "additionalParams", "", "inheritMemoryLimits", "", "inheritOtherJvmOptions", "inheritAdditionalProperties", "([Ljava/lang/String;ZZZ)Lorg/jetbrains/kotlin/daemon/common/DaemonJVMOptions;", "opts", "(Lorg/jetbrains/kotlin/daemon/common/DaemonJVMOptions;[Ljava/lang/String;ZZZ)Lorg/jetbrains/kotlin/daemon/common/DaemonJVMOptions;", "", "configureDaemonOptions", "isDaemonEnabled", "distinctStringsDigest", "", "filterExtractProps", "groups", "Lorg/jetbrains/kotlin/daemon/common/OptionsGroup;", "prefix", "(Ljava/lang/Iterable;[Lorg/jetbrains/kotlin/daemon/common/OptionsGroup;Ljava/lang/String;)Ljava/lang/Iterable;", "propMappers", "Lorg/jetbrains/kotlin/daemon/common/PropMapper;", "restParser", "Lorg/jetbrains/kotlin/daemon/common/RestPropMapper;", "findWithTransform", "R", ExifInterface.GPS_DIRECTION_TRUE, "", "mappingPredicate", "Lkotlin/Function1;", "Lkotlin/Pair;", "(Ljava/lang/Iterable;Lkotlin/jvm/functions/Function1;)Ljava/lang/Object;", "memToBytes", "(Ljava/lang/String;)Ljava/lang/Long;", "memorywiseFitsInto", "other", "toHexString", "trimQuotes", "updateMemoryUpperBounds", "daemon-common"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class DaemonParamsKt {
    private static final String CLASSPATH_ID_DIGEST = "MD5";
    public static final String COMPILER_DAEMON_CLASS_FQN = "org.jetbrains.kotlin.daemon.KotlinCompileDaemon";
    public static final String COMPILER_JAR_NAME = "kotlin-compiler.jar";
    public static final String COMPILER_SERVICE_RMI_NAME = "KotlinJvmCompilerService";
    public static final String COMPILE_DAEMON_CMDLINE_OPTIONS_PREFIX = "--daemon-";
    public static final String COMPILE_DAEMON_DEFAULT_FILES_PREFIX = "kotlin-daemon";
    public static final int COMPILE_DAEMON_DEFAULT_IDLE_TIMEOUT_S = 7200;
    public static final long COMPILE_DAEMON_DEFAULT_SHUTDOWN_DELAY_MS = 1000;
    public static final int COMPILE_DAEMON_DEFAULT_UNUSED_TIMEOUT_S = 60;
    public static final int COMPILE_DAEMON_FIND_PORT_ATTEMPTS = 10;
    public static final long COMPILE_DAEMON_FORCE_SHUTDOWN_DEFAULT_TIMEOUT_MS = 10000;
    public static final String COMPILE_DAEMON_IS_READY_MESSAGE = "Kotlin compile daemon is ready";
    public static final long COMPILE_DAEMON_MEMORY_THRESHOLD_INFINITE = 0;
    public static final int COMPILE_DAEMON_PORTS_RANGE_END = 18000;
    public static final int COMPILE_DAEMON_PORTS_RANGE_START = 17001;
    public static final long COMPILE_DAEMON_TIMEOUT_INFINITE_MS = 0;
    public static final int COMPILE_DAEMON_TIMEOUT_INFINITE_S = 0;
    private static final Regex jvmAssertArgsRegex = new Regex("(es?a|ds?a|(enable|disable)(system)?assertions)($|:)");
    private static final Regex humanizedMemorySizeRegex = new Regex("(\\d+)([kmg]?)");
    private static final List<KMutableProperty1<DaemonJVMOptions, String>> daemonJVMOptionsMemoryProps = CollectionsKt.listOf((Object[]) new KMutableProperty1[]{new MutablePropertyReference1() { // from class: org.jetbrains.kotlin.daemon.common.DaemonParamsKt$daemonJVMOptionsMemoryProps$1
        @Override // kotlin.jvm.internal.CallableReference
        public String getSignature() {
            return "getMaxMemory()Ljava/lang/String;";
        }

        @Override // kotlin.reflect.KProperty1
        public Object get(Object obj) {
            return ((DaemonJVMOptions) obj).getMaxMemory();
        }

        @Override // kotlin.jvm.internal.CallableReference, kotlin.reflect.KCallable
        public String getName() {
            return "maxMemory";
        }

        @Override // kotlin.jvm.internal.CallableReference
        public KDeclarationContainer getOwner() {
            return Reflection.getOrCreateKotlinClass(DaemonJVMOptions.class);
        }

        @Override // kotlin.reflect.KMutableProperty1
        public void set(Object obj, Object obj2) {
            ((DaemonJVMOptions) obj).setMaxMemory((String) obj2);
        }
    }, new MutablePropertyReference1() { // from class: org.jetbrains.kotlin.daemon.common.DaemonParamsKt$daemonJVMOptionsMemoryProps$2
        @Override // kotlin.jvm.internal.CallableReference
        public String getSignature() {
            return "getMaxMetaspaceSize()Ljava/lang/String;";
        }

        @Override // kotlin.reflect.KProperty1
        public Object get(Object obj) {
            return ((DaemonJVMOptions) obj).getMaxMetaspaceSize();
        }

        @Override // kotlin.jvm.internal.CallableReference, kotlin.reflect.KCallable
        public String getName() {
            return "maxMetaspaceSize";
        }

        @Override // kotlin.jvm.internal.CallableReference
        public KDeclarationContainer getOwner() {
            return Reflection.getOrCreateKotlinClass(DaemonJVMOptions.class);
        }

        @Override // kotlin.reflect.KMutableProperty1
        public void set(Object obj, Object obj2) {
            ((DaemonJVMOptions) obj).setMaxMetaspaceSize((String) obj2);
        }
    }, new MutablePropertyReference1() { // from class: org.jetbrains.kotlin.daemon.common.DaemonParamsKt$daemonJVMOptionsMemoryProps$3
        @Override // kotlin.jvm.internal.CallableReference
        public String getSignature() {
            return "getReservedCodeCacheSize()Ljava/lang/String;";
        }

        @Override // kotlin.reflect.KProperty1
        public Object get(Object obj) {
            return ((DaemonJVMOptions) obj).getReservedCodeCacheSize();
        }

        @Override // kotlin.jvm.internal.CallableReference, kotlin.reflect.KCallable
        public String getName() {
            return "reservedCodeCacheSize";
        }

        @Override // kotlin.jvm.internal.CallableReference
        public KDeclarationContainer getOwner() {
            return Reflection.getOrCreateKotlinClass(DaemonJVMOptions.class);
        }

        @Override // kotlin.reflect.KMutableProperty1
        public void set(Object obj, Object obj2) {
            ((DaemonJVMOptions) obj).setReservedCodeCacheSize((String) obj2);
        }
    }});

    public static final String getCOMPILE_DAEMON_DEFAULT_RUN_DIR_PATH() {
        String value = CompilerSystemProperties.COMPILE_DAEMON_CUSTOM_RUN_FILES_PATH_FOR_TESTS.getValue();
        return value == null ? FileSystem.INSTANCE.getRuntimeStateFilesPath(IncrementalJvmCacheKt.KOTLIN_CACHE_DIRECTORY_NAME, "daemon") : value;
    }

    public static final String getCLASSPATH_ID_DIGEST() {
        return CLASSPATH_ID_DIGEST;
    }

    public static final <T, R> R findWithTransform(Iterable<? extends T> iterable, Function1<? super T, ? extends Pair<Boolean, ? extends R>> mappingPredicate) {
        Intrinsics.checkNotNullParameter(iterable, "<this>");
        Intrinsics.checkNotNullParameter(mappingPredicate, "mappingPredicate");
        Iterator<? extends T> it = iterable.iterator();
        while (it.hasNext()) {
            Pair<Boolean, ? extends R> pairInvoke = mappingPredicate.invoke(it.next());
            boolean zBooleanValue = pairInvoke.component1().booleanValue();
            R rComponent2 = pairInvoke.component2();
            if (zBooleanValue) {
                return rComponent2;
            }
        }
        return null;
    }

    public static /* synthetic */ Iterable filterExtractProps$default(Iterable iterable, List list, String str, RestPropMapper restPropMapper, int i, Object obj) {
        if ((i & 4) != 0) {
            restPropMapper = null;
        }
        return filterExtractProps(iterable, list, str, restPropMapper);
    }

    public static final Iterable<String> filterExtractProps(Iterable<String> iterable, List<? extends PropMapper<?, ?, ?>> propMappers, String prefix, RestPropMapper<?, ?> restPropMapper) {
        Object objComponent2;
        Object next;
        boolean zBooleanValue;
        Intrinsics.checkNotNullParameter(iterable, "<this>");
        Intrinsics.checkNotNullParameter(propMappers, "propMappers");
        Intrinsics.checkNotNullParameter(prefix, "prefix");
        Iterator<String> it = iterable.iterator();
        ArrayList arrayList = new ArrayList();
        while (it.hasNext()) {
            String next2 = it.next();
            Iterator<T> it2 = propMappers.iterator();
            do {
                if (!it2.hasNext()) {
                    objComponent2 = null;
                    break;
                }
                PropMapper propMapper = (PropMapper) it2.next();
                Iterator<T> it3 = propMapper.getNames().iterator();
                do {
                    if (!it3.hasNext()) {
                        next = null;
                        break;
                    }
                    next = it3.next();
                } while (!StringsKt.startsWith$default(next2, prefix + ((String) next), false, 2, (Object) null));
                String str = (String) next;
                Pair pair = new Pair(Boolean.valueOf(str != null), new Pair(propMapper, str));
                zBooleanValue = ((Boolean) pair.component1()).booleanValue();
                objComponent2 = pair.component2();
            } while (!zBooleanValue);
            Pair pair2 = (Pair) objComponent2;
            if (pair2 == null) {
                pair2 = new Pair(null, null);
            }
            PropMapper propMapper2 = (PropMapper) pair2.component1();
            String str2 = (String) pair2.component2();
            if (propMapper2 != null) {
                int length = prefix.length();
                Intrinsics.checkNotNull(str2);
                int length2 = length + str2.length();
                if (propMapper2 instanceof BoolPropMapper) {
                    if (next2.length() > length2) {
                        throw new IllegalArgumentException("Invalid switch option '" + next2 + "', expecting " + prefix + str2 + " without arguments");
                    }
                    propMapper2.apply("");
                } else if (next2.length() > length2) {
                    if (next2.charAt(length2) != '=') {
                        if (propMapper2.getMergeDelimiter() == null) {
                            throw new IllegalArgumentException("Invalid option syntax '" + next2 + "', expecting " + prefix + str2 + "[= ]<arg>");
                        }
                        String strSubstring = next2.substring(length2);
                        Intrinsics.checkNotNullExpressionValue(strSubstring, "substring(...)");
                        propMapper2.apply(strSubstring);
                    } else {
                        String strSubstring2 = next2.substring(length2 + 1);
                        Intrinsics.checkNotNullExpressionValue(strSubstring2, "substring(...)");
                        propMapper2.apply(strSubstring2);
                    }
                } else {
                    if (!it.hasNext()) {
                        throw new IllegalArgumentException("Expecting argument for the option " + prefix + str2);
                    }
                    propMapper2.apply(it.next());
                }
            } else if (restPropMapper != null && StringsKt.startsWith$default(next2, prefix, false, 2, (Object) null)) {
                restPropMapper.add(StringsKt.removePrefix(next2, (CharSequence) prefix));
            } else {
                arrayList.add(next2);
            }
        }
        return arrayList;
    }

    public static final String trimQuotes(String str) {
        Intrinsics.checkNotNullParameter(str, "<this>");
        return StringsKt.trim(str, '\"', '\'');
    }

    public static final String getRunFilesPathOrDefault(DaemonOptions daemonOptions) {
        Intrinsics.checkNotNullParameter(daemonOptions, "<this>");
        return StringsKt.isBlank(daemonOptions.getRunFilesPath()) ? getCOMPILE_DAEMON_DEFAULT_RUN_DIR_PATH() : daemonOptions.getRunFilesPath();
    }

    public static final byte[] distinctStringsDigest(Iterable<String> iterable) throws NoSuchAlgorithmException {
        Intrinsics.checkNotNullParameter(iterable, "<this>");
        MessageDigest messageDigest = MessageDigest.getInstance(CLASSPATH_ID_DIGEST);
        byte[] bytes = CollectionsKt.joinToString$default(CollectionsKt.sorted(CollectionsKt.distinct(iterable)), "", null, null, 0, null, null, 62, null).getBytes(Charsets.UTF_8);
        Intrinsics.checkNotNullExpressionValue(bytes, "getBytes(...)");
        byte[] bArrDigest = messageDigest.digest(bytes);
        Intrinsics.checkNotNullExpressionValue(bArrDigest, "digest(...)");
        return bArrDigest;
    }

    public static final String toHexString(byte[] bArr) {
        Intrinsics.checkNotNullParameter(bArr, "<this>");
        return ArraysKt.joinToString$default(bArr, (CharSequence) "", (CharSequence) null, (CharSequence) null, 0, (CharSequence) null, (Function1) new Function1<Byte, CharSequence>() { // from class: org.jetbrains.kotlin.daemon.common.DaemonParamsKt.toHexString.1
            public final CharSequence invoke(byte b) {
                String str = String.format("%02x", Arrays.copyOf(new Object[]{Byte.valueOf(b)}, 1));
                Intrinsics.checkNotNullExpressionValue(str, "format(...)");
                return str;
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ CharSequence invoke(Byte b) {
                return invoke(b.byteValue());
            }
        }, 30, (Object) null);
    }

    public static final boolean isDaemonEnabled() {
        return CompilerSystemProperties.COMPILE_DAEMON_ENABLED_PROPERTY.getValue() != null;
    }

    public static final DaemonJVMOptions configureDaemonJVMOptions(DaemonJVMOptions opts, String[] additionalParams, boolean z, boolean z2, boolean z3) {
        Intrinsics.checkNotNullParameter(opts, "opts");
        Intrinsics.checkNotNullParameter(additionalParams, "additionalParams");
        return configureDaemonJVMOptions(opts, (Iterable<String>) ArraysKt.asIterable(additionalParams), z, z2, z3);
    }

    public static final DaemonJVMOptions configureDaemonJVMOptions(DaemonJVMOptions opts, Iterable<String> additionalParams, boolean z, boolean z2, boolean z3) {
        Intrinsics.checkNotNullParameter(opts, "opts");
        Intrinsics.checkNotNullParameter(additionalParams, "additionalParams");
        if (z || z2) {
            List inputArguments = ManagementFactory.getRuntimeMXBean().getInputArguments();
            DaemonJVMOptions daemonJVMOptions = z ? opts : new DaemonJVMOptions(null, null, null, null, 15, null);
            Intrinsics.checkNotNull(inputArguments);
            Iterable iterableFilterExtractProps$default = filterExtractProps$default(inputArguments, daemonJVMOptions.getMappers(), Parameters.DEFAULT_OPTION_PREFIXES, null, 4, null);
            if (z && StringsKt.isBlank(opts.getMaxMemory())) {
                long jMaxMemory = Runtime.getRuntime().maxMemory();
                long j = 1048576;
                opts.setMaxMemory(new StringBuilder().append((jMaxMemory / j) + ((long) (jMaxMemory % j == 0 ? 0 : 1))).append(Advice.OffsetMapping.ForOrigin.Renderer.ForMethodName.SYMBOL).toString());
            }
            if (z2) {
                Collection<String> jvmParams = opts.getJvmParams();
                ArrayList arrayList = new ArrayList();
                for (Object obj : iterableFilterExtractProps$default) {
                    String str = (String) obj;
                    if (!StringsKt.startsWith$default(str, "agentlib", false, 2, (Object) null) && !StringsKt.startsWith$default(str, "D" + CompilerSystemProperties.COMPILE_DAEMON_LOG_PATH_PROPERTY.getProperty(), false, 2, (Object) null) && !StringsKt.startsWith$default(str, "D" + CompilerSystemProperties.KOTLIN_COMPILER_ENVIRONMENT_KEEPALIVE_PROPERTY.getProperty(), false, 2, (Object) null) && !StringsKt.startsWith$default(str, "D" + CompilerSystemProperties.COMPILE_DAEMON_JVM_OPTIONS_PROPERTY.getProperty(), false, 2, (Object) null) && !StringsKt.startsWith$default(str, "D" + CompilerSystemProperties.COMPILE_DAEMON_OPTIONS_PROPERTY.getProperty(), false, 2, (Object) null)) {
                        arrayList.add(obj);
                    }
                }
                jvmParams.addAll(arrayList);
            }
        }
        String value = CompilerSystemProperties.COMPILE_DAEMON_JVM_OPTIONS_PROPERTY.getValue();
        if (value != null) {
            Collection<String> jvmParams2 = opts.getJvmParams();
            List<String> listSplit = new Regex("(?<!\\\\),").split(trimQuotes(value), 0);
            ArrayList arrayList2 = new ArrayList(CollectionsKt.collectionSizeOrDefault(listSplit, 10));
            Iterator<T> it = listSplit.iterator();
            while (it.hasNext()) {
                arrayList2.add(new Regex("\\\\(.)").replace((String) it.next(), "$1"));
            }
            CollectionsKt.addAll(jvmParams2, filterExtractProps(arrayList2, opts.getMappers(), Parameters.DEFAULT_OPTION_PREFIXES, opts.getRestMapper()));
        }
        CollectionsKt.addAll(opts.getJvmParams(), additionalParams);
        if (z3) {
            String value2 = CompilerSystemProperties.COMPILE_DAEMON_LOG_PATH_PROPERTY.getValue();
            if (value2 != null) {
                opts.getJvmParams().add("D" + CompilerSystemProperties.COMPILE_DAEMON_LOG_PATH_PROPERTY.getProperty() + "=\"" + value2 + '\"');
            }
            if (CompilerSystemProperties.KOTLIN_COMPILER_ENVIRONMENT_KEEPALIVE_PROPERTY.getValue() != null) {
                opts.getJvmParams().add("D" + CompilerSystemProperties.KOTLIN_COMPILER_ENVIRONMENT_KEEPALIVE_PROPERTY.getProperty());
            }
        }
        Collection<String> jvmParams3 = opts.getJvmParams();
        if ((jvmParams3 instanceof Collection) && jvmParams3.isEmpty()) {
            opts.getJvmParams().add("ea");
        } else {
            Iterator<T> it2 = jvmParams3.iterator();
            while (it2.hasNext()) {
                if (jvmAssertArgsRegex.matches((String) it2.next())) {
                }
            }
            opts.getJvmParams().add("ea");
        }
        return opts;
    }

    public static final DaemonJVMOptions configureDaemonJVMOptions(String[] additionalParams, boolean z, boolean z2, boolean z3) {
        Intrinsics.checkNotNullParameter(additionalParams, "additionalParams");
        return configureDaemonJVMOptions(new DaemonJVMOptions(null, null, null, null, 15, null), (String[]) Arrays.copyOf(additionalParams, additionalParams.length), z, z2, z3);
    }

    public static final DaemonOptions configureDaemonOptions(DaemonOptions opts) {
        Intrinsics.checkNotNullParameter(opts, "opts");
        String value = CompilerSystemProperties.COMPILE_DAEMON_OPTIONS_PROPERTY.getValue();
        if (value != null) {
            Iterable iterableFilterExtractProps$default = filterExtractProps$default(StringsKt.split$default((CharSequence) trimQuotes(value), new String[]{","}, false, 0, 6, (Object) null), opts.getMappers(), "", null, 4, null);
            if (CollectionsKt.any(iterableFilterExtractProps$default)) {
                throw new IllegalArgumentException("Unrecognized daemon options passed via property " + CompilerSystemProperties.COMPILE_DAEMON_OPTIONS_PROPERTY.getProperty() + ": " + CollectionsKt.joinToString$default(iterableFilterExtractProps$default, " ", null, null, 0, null, null, 62, null) + "\nSupported options: " + CollectionsKt.joinToString$default(opts.getMappers(), ", ", null, null, 0, null, new Function1<PropMapper<?, ?, ?>, CharSequence>() { // from class: org.jetbrains.kotlin.daemon.common.DaemonParamsKt$configureDaemonOptions$1$1
                    @Override // kotlin.jvm.functions.Function1
                    public final CharSequence invoke(PropMapper<?, ?, ?> it) {
                        Intrinsics.checkNotNullParameter(it, "it");
                        return (CharSequence) CollectionsKt.first((List) it.getNames());
                    }
                }, 30, null));
            }
        }
        if (CompilerSystemProperties.COMPILE_DAEMON_VERBOSE_REPORT_PROPERTY.getValue() != null) {
            opts.setVerbose(true);
        }
        if (CompilerSystemProperties.COMPILE_DAEMON_REPORT_PERF_PROPERTY.getValue() != null) {
            opts.setReportPerf(true);
        }
        return opts;
    }

    public static final DaemonOptions configureDaemonOptions() {
        return configureDaemonOptions(new DaemonOptions(null, 0L, 0, 0, 0L, 0L, false, false, 255, null));
    }

    private static final Long memToBytes(String str) {
        MatchGroupCollection groups;
        String value;
        Regex regex = humanizedMemorySizeRegex;
        String lowerCase = StringsKt.trim((CharSequence) str).toString().toLowerCase(Locale.ROOT);
        Intrinsics.checkNotNullExpressionValue(lowerCase, "toLowerCase(...)");
        MatchResult matchResultMatchEntire = regex.matchEntire(lowerCase);
        if (matchResultMatchEntire == null || (groups = matchResultMatchEntire.getGroups()) == null) {
            return null;
        }
        int i = 1;
        MatchGroup matchGroup = groups.get(1);
        if (matchGroup == null || (value = matchGroup.getValue()) == null) {
            return null;
        }
        long j = Long.parseLong(value);
        MatchGroup matchGroup2 = groups.get(2);
        String value2 = matchGroup2 != null ? matchGroup2.getValue() : null;
        if (value2 != null) {
            int iHashCode = value2.hashCode();
            if (iHashCode != 103) {
                if (iHashCode != 107) {
                    if (iHashCode == 109 && value2.equals("m")) {
                        i = 1048576;
                    }
                } else if (value2.equals(JvmAnnotationNames.KIND_FIELD_NAME)) {
                    i = 1024;
                }
            } else if (value2.equals("g")) {
                i = 1073741824;
            }
        }
        return Long.valueOf(j * ((long) i));
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static final boolean memorywiseFitsInto(DaemonJVMOptions daemonJVMOptions, DaemonJVMOptions other) {
        Intrinsics.checkNotNullParameter(daemonJVMOptions, "<this>");
        Intrinsics.checkNotNullParameter(other, "other");
        List<KMutableProperty1<DaemonJVMOptions, String>> list = daemonJVMOptionsMemoryProps;
        if ((list instanceof Collection) && list.isEmpty()) {
            return true;
        }
        Iterator<T> it = list.iterator();
        while (it.hasNext()) {
            KMutableProperty1 kMutableProperty1 = (KMutableProperty1) it.next();
            Long lMemToBytes = memToBytes((String) kMutableProperty1.get(daemonJVMOptions));
            long jLongValue = lMemToBytes != null ? lMemToBytes.longValue() : 0L;
            Long lMemToBytes2 = memToBytes((String) kMutableProperty1.get(other));
            if (jLongValue > (lMemToBytes2 != null ? lMemToBytes2.longValue() : 0L)) {
                return false;
            }
        }
        return true;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static final int compareDaemonJVMOptionsMemory(DaemonJVMOptions left, DaemonJVMOptions right) {
        Intrinsics.checkNotNullParameter(left, "left");
        Intrinsics.checkNotNullParameter(right, "right");
        List<KMutableProperty1<DaemonJVMOptions, String>> list = daemonJVMOptionsMemoryProps;
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(list, 10));
        Iterator<T> it = list.iterator();
        while (it.hasNext()) {
            KMutableProperty1 kMutableProperty1 = (KMutableProperty1) it.next();
            Long lMemToBytes = memToBytes((String) kMutableProperty1.get(left));
            long jLongValue = 0;
            Long lValueOf = Long.valueOf(lMemToBytes != null ? lMemToBytes.longValue() : 0L);
            Long lMemToBytes2 = memToBytes((String) kMutableProperty1.get(right));
            if (lMemToBytes2 != null) {
                jLongValue = lMemToBytes2.longValue();
            }
            arrayList.add(new Pair(lValueOf, Long.valueOf(jLongValue)));
        }
        ArrayList<Pair> arrayList2 = arrayList;
        boolean z = arrayList2 instanceof Collection;
        if (z && arrayList2.isEmpty()) {
            return 0;
        }
        for (Pair pair : arrayList2) {
            if (((Number) pair.getFirst()).longValue() != ((Number) pair.getSecond()).longValue()) {
                if (!z || !arrayList2.isEmpty()) {
                    for (Pair pair2 : arrayList2) {
                        if (((Number) pair2.getFirst()).longValue() > ((Number) pair2.getSecond()).longValue()) {
                            if (!z || !arrayList2.isEmpty()) {
                                for (Pair pair3 : arrayList2) {
                                    if (((Number) pair3.getFirst()).longValue() < ((Number) pair3.getSecond()).longValue()) {
                                        return 0;
                                    }
                                }
                            }
                            return 1;
                        }
                    }
                }
                return -1;
            }
        }
        return 0;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static final DaemonJVMOptions updateMemoryUpperBounds(DaemonJVMOptions daemonJVMOptions, DaemonJVMOptions other) {
        Intrinsics.checkNotNullParameter(daemonJVMOptions, "<this>");
        Intrinsics.checkNotNullParameter(other, "other");
        Iterator<T> it = daemonJVMOptionsMemoryProps.iterator();
        while (it.hasNext()) {
            KMutableProperty1 kMutableProperty1 = (KMutableProperty1) it.next();
            Long lMemToBytes = memToBytes((String) kMutableProperty1.get(daemonJVMOptions));
            long jLongValue = lMemToBytes != null ? lMemToBytes.longValue() : 0L;
            Long lMemToBytes2 = memToBytes((String) kMutableProperty1.get(other));
            if (jLongValue < (lMemToBytes2 != null ? lMemToBytes2.longValue() : 0L)) {
                kMutableProperty1.set(daemonJVMOptions, kMutableProperty1.get(other));
            }
        }
        return daemonJVMOptions;
    }

    public static final Iterable<String> filterExtractProps(Iterable<String> iterable, OptionsGroup[] groups, String prefix) {
        Intrinsics.checkNotNullParameter(iterable, "<this>");
        Intrinsics.checkNotNullParameter(groups, "groups");
        Intrinsics.checkNotNullParameter(prefix, "prefix");
        ArrayList arrayList = new ArrayList();
        for (OptionsGroup optionsGroup : groups) {
            CollectionsKt.addAll(arrayList, optionsGroup.getMappers());
        }
        return filterExtractProps$default(iterable, arrayList, prefix, null, 4, null);
    }
}
