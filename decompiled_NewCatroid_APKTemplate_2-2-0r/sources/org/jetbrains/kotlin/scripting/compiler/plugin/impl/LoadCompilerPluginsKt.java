package org.jetbrains.kotlin.scripting.compiler.plugin.impl;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.huawei.hms.ml.language.common.utils.Constant;
import io.netty.util.internal.StringUtil;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.ArraysKt;
import kotlin.collections.CollectionsKt;
import kotlin.io.CloseableKt;
import kotlin.io.TextStreamsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Lambda;
import kotlin.script.experimental.jvm.util.JvmClassLoaderUtilKt;
import kotlin.text.Charsets;
import kotlin.text.StringsKt;
import org.eclipse.jdt.internal.compiler.impl.CompilerOptions;
import org.jetbrains.kotlin.cli.common.arguments.K2JVMCompilerArguments;
import org.jetbrains.kotlin.cli.plugins.PluginsOptionsParserKt;
import org.jetbrains.kotlin.compiler.plugin.CommandLineProcessor;
import org.jetbrains.kotlin.compiler.plugin.CompilerPluginRegistrar;
import org.jetbrains.kotlin.compiler.plugin.ComponentRegistrar;
import org.jetbrains.kotlin.config.CompilerConfiguration;
import org.jetbrains.kotlin.metadata.jvm.deserialization.JvmProtoBufUtil;
import org.jetbrains.kotlin.name.SpecialNames;
import org.jetbrains.kotlin.scripting.compiler.plugin.ScriptingCommandLineProcessor;
import org.jetbrains.kotlin.scripting.compiler.plugin.ScriptingCompilerConfigurationComponentRegistrar;
import org.jetbrains.kotlin.utils.PathUtil;

/* JADX INFO: compiled from: loadCompilerPlugins.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000<\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010 \n\u0002\b\u0006\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\u001a\u001a\u0010\b\u001a\u0004\u0018\u00010\u00012\u0006\u0010\t\u001a\u00020\u00012\u0006\u0010\n\u001a\u00020\u0001H\u0002\u001a\u0016\u0010\u000b\u001a\u00020\f*\u00020\r2\b\b\u0002\u0010\u000e\u001a\u00020\u000fH\u0000\u001a3\u0010\u0010\u001a\b\u0012\u0004\u0012\u0002H\u00110\u0005\"\n\b\u0000\u0010\u0011\u0018\u0001*\u00020\u0012*\u00020\u000f2\u0012\u0010\u0013\u001a\u000e\u0012\u0004\u0012\u00020\u0001\u0012\u0004\u0012\u00020\u00150\u0014H\u0082\b\u001a5\u0010\u0010\u001a\b\u0012\u0004\u0012\u0002H\u00110\u0005\"\n\b\u0000\u0010\u0011\u0018\u0001*\u00020\u0012*\u00020\u000f2\f\u0010\u0016\u001a\b\u0012\u0004\u0012\u00020\u00010\u00052\u0006\u0010\u0017\u001a\u00020\u0001H\u0082\b\u001a\u0014\u0010\u0018\u001a\u00020\f*\u00020\r2\u0006\u0010\u0019\u001a\u00020\u001aH\u0000\"\u000e\u0010\u0000\u001a\u00020\u0001X\u0082T¢\u0006\u0002\n\u0000\"\u000e\u0010\u0002\u001a\u00020\u0001X\u0082T¢\u0006\u0002\n\u0000\"\u000e\u0010\u0003\u001a\u00020\u0001X\u0082T¢\u0006\u0002\n\u0000\"\u001c\u0010\u0004\u001a\u0010\u0012\f\u0012\n \u0006*\u0004\u0018\u00010\u00010\u00010\u0005X\u0082\u0004¢\u0006\u0002\n\u0000\"\u001c\u0010\u0007\u001a\u0010\u0012\f\u0012\n \u0006*\u0004\u0018\u00010\u00010\u00010\u0005X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u001b"}, d2 = {"SCRIPT_COMPILATION_DISABLE_COMMANDLINE_PROCESSORS_PROPERTY", "", "SCRIPT_COMPILATION_DISABLE_PLUGINS_PROPERTY", "SERVICE_DIRECTORY_LOCATION", "scriptCompilationDisabledCommandlineProcessors", "", JvmProtoBufUtil.PLATFORM_TYPE_ID, "scriptCompilationDisabledPlugins", "parseServiceFileLine", FirebaseAnalytics.Param.LOCATION, Constant.LINE, "loadPlugins", "", "Lorg/jetbrains/kotlin/config/CompilerConfiguration;", "classLoader", "Ljava/lang/ClassLoader;", "loadServices", "Service", "", "isEnabled", "Lkotlin/Function1;", "", CompilerOptions.DISABLED, "disablingProperty", "processPluginsCommandLine", "arguments", "Lorg/jetbrains/kotlin/cli/common/arguments/K2JVMCompilerArguments;", PathUtil.KOTLIN_SCRIPTING_COMPILER_PLUGIN_NAME}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class LoadCompilerPluginsKt {
    private static final String SCRIPT_COMPILATION_DISABLE_COMMANDLINE_PROCESSORS_PROPERTY = "script.compilation.disable.commandline.processors";
    private static final String SCRIPT_COMPILATION_DISABLE_PLUGINS_PROPERTY = "script.compilation.disable.plugins";
    private static final String SERVICE_DIRECTORY_LOCATION = "META-INF/services/";
    private static final List<String> scriptCompilationDisabledPlugins = CollectionsKt.listOf(ScriptingCompilerConfigurationComponentRegistrar.class.getName());
    private static final List<String> scriptCompilationDisabledCommandlineProcessors = CollectionsKt.listOf(ScriptingCommandLineProcessor.class.getName());

    public static /* synthetic */ void loadPlugins$default(CompilerConfiguration compilerConfiguration, ClassLoader classLoader, int i, Object obj) throws IllegalAccessException, InstantiationException {
        if ((i & 1) != 0) {
            classLoader = CompilerConfiguration.class.getClassLoader();
            Intrinsics.checkNotNullExpressionValue(classLoader, "getClassLoader(...)");
        }
        loadPlugins(compilerConfiguration, classLoader);
    }

    public static final void loadPlugins(CompilerConfiguration compilerConfiguration, ClassLoader classLoader) throws IllegalAccessException, InstantiationException {
        CompilerPluginRegistrar compilerPluginRegistrar;
        Intrinsics.checkNotNullParameter(compilerConfiguration, "<this>");
        Intrinsics.checkNotNullParameter(classLoader, "classLoader");
        HashSet hashSet = CollectionsKt.toHashSet(scriptCompilationDisabledPlugins);
        String property = System.getProperty(SCRIPT_COMPILATION_DISABLE_PLUGINS_PROPERTY);
        if (property != null) {
            Intrinsics.checkNotNull(property);
            Iterator it = StringsKt.split$default((CharSequence) property, new char[]{',', ';', StringUtil.SPACE}, false, 0, 6, (Object) null).iterator();
            while (it.hasNext()) {
                hashSet.add(StringsKt.trim((CharSequence) it.next()).toString());
            }
        }
        HashSet hashSet2 = new HashSet();
        String str = SERVICE_DIRECTORY_LOCATION + ComponentRegistrar.class.getName();
        JvmClassLoaderUtilKt.forAllMatchingFiles(classLoader, str, new String[]{str}, new AnonymousClass3(hashSet2));
        ArrayList arrayList = new ArrayList();
        Iterator it2 = hashSet2.iterator();
        while (true) {
            ComponentRegistrar componentRegistrar = null;
            if (it2.hasNext()) {
                String str2 = (String) it2.next();
                if (!hashSet.contains(str2) && !hashSet.contains(StringsKt.substringAfterLast$default(str2, '.', (String) null, 2, (Object) null))) {
                    Object objNewInstance = classLoader.loadClass(str2).newInstance();
                    if (objNewInstance == null) {
                        throw new NullPointerException("null cannot be cast to non-null type org.jetbrains.kotlin.compiler.plugin.ComponentRegistrar");
                    }
                    componentRegistrar = (ComponentRegistrar) objNewInstance;
                }
                if (componentRegistrar != null) {
                    arrayList.add(componentRegistrar);
                }
            } else {
                compilerConfiguration.addAll(ComponentRegistrar.INSTANCE.getPLUGIN_COMPONENT_REGISTRARS(), arrayList);
                HashSet hashSet3 = CollectionsKt.toHashSet(scriptCompilationDisabledPlugins);
                String property2 = System.getProperty(SCRIPT_COMPILATION_DISABLE_PLUGINS_PROPERTY);
                if (property2 != null) {
                    Intrinsics.checkNotNull(property2);
                    Iterator it3 = StringsKt.split$default((CharSequence) property2, new char[]{',', ';', StringUtil.SPACE}, false, 0, 6, (Object) null).iterator();
                    while (it3.hasNext()) {
                        hashSet3.add(StringsKt.trim((CharSequence) it3.next()).toString());
                    }
                }
                HashSet<String> hashSet4 = new HashSet();
                String str3 = SERVICE_DIRECTORY_LOCATION + CompilerPluginRegistrar.class.getName();
                JvmClassLoaderUtilKt.forAllMatchingFiles(classLoader, str3, new String[]{str3}, new AnonymousClass3(hashSet4));
                ArrayList arrayList2 = new ArrayList();
                for (String str4 : hashSet4) {
                    if (hashSet3.contains(str4) || hashSet3.contains(StringsKt.substringAfterLast$default(str4, '.', (String) null, 2, (Object) null))) {
                        compilerPluginRegistrar = null;
                    } else {
                        Object objNewInstance2 = classLoader.loadClass(str4).newInstance();
                        if (objNewInstance2 == null) {
                            throw new NullPointerException("null cannot be cast to non-null type org.jetbrains.kotlin.compiler.plugin.CompilerPluginRegistrar");
                        }
                        compilerPluginRegistrar = (CompilerPluginRegistrar) objNewInstance2;
                    }
                    if (compilerPluginRegistrar != null) {
                        arrayList2.add(compilerPluginRegistrar);
                    }
                }
                compilerConfiguration.addAll(CompilerPluginRegistrar.INSTANCE.getCOMPILER_PLUGIN_REGISTRARS(), arrayList2);
                return;
            }
        }
    }

    public static final void processPluginsCommandLine(CompilerConfiguration compilerConfiguration, K2JVMCompilerArguments arguments) throws IllegalAccessException, InstantiationException {
        List listEmptyList;
        Intrinsics.checkNotNullParameter(compilerConfiguration, "<this>");
        Intrinsics.checkNotNullParameter(arguments, "arguments");
        ClassLoader classLoader = CompilerConfiguration.class.getClassLoader();
        String[] pluginOptions = arguments.getPluginOptions();
        if (pluginOptions == null || (listEmptyList = ArraysKt.asIterable(pluginOptions)) == null) {
            listEmptyList = CollectionsKt.emptyList();
        }
        Intrinsics.checkNotNull(classLoader);
        HashSet hashSet = CollectionsKt.toHashSet(scriptCompilationDisabledCommandlineProcessors);
        String property = System.getProperty(SCRIPT_COMPILATION_DISABLE_COMMANDLINE_PROCESSORS_PROPERTY);
        if (property != null) {
            Intrinsics.checkNotNull(property);
            Iterator it = StringsKt.split$default((CharSequence) property, new char[]{',', ';', StringUtil.SPACE}, false, 0, 6, (Object) null).iterator();
            while (it.hasNext()) {
                hashSet.add(StringsKt.trim((CharSequence) it.next()).toString());
            }
        }
        HashSet<String> hashSet2 = new HashSet();
        String str = SERVICE_DIRECTORY_LOCATION + CommandLineProcessor.class.getName();
        JvmClassLoaderUtilKt.forAllMatchingFiles(classLoader, str, new String[]{str}, new AnonymousClass3(hashSet2));
        ArrayList arrayList = new ArrayList();
        for (String str2 : hashSet2) {
            CommandLineProcessor commandLineProcessor = null;
            if (!hashSet.contains(str2) && !hashSet.contains(StringsKt.substringAfterLast$default(str2, '.', (String) null, 2, (Object) null))) {
                Object objNewInstance = classLoader.loadClass(str2).newInstance();
                if (objNewInstance == null) {
                    throw new NullPointerException("null cannot be cast to non-null type org.jetbrains.kotlin.compiler.plugin.CommandLineProcessor");
                }
                commandLineProcessor = (CommandLineProcessor) objNewInstance;
            }
            if (commandLineProcessor != null) {
                arrayList.add(commandLineProcessor);
            }
        }
        PluginsOptionsParserKt.processCompilerPluginsOptions(compilerConfiguration, listEmptyList, arrayList);
    }

    private static final /* synthetic */ <Service> List<Service> loadServices(ClassLoader classLoader, List<String> list, String str) throws IllegalAccessException, InstantiationException {
        HashSet hashSet = CollectionsKt.toHashSet(list);
        String property = System.getProperty(str);
        if (property != null) {
            Iterator it = StringsKt.split$default((CharSequence) property, new char[]{',', ';', StringUtil.SPACE}, false, 0, 6, (Object) null).iterator();
            while (it.hasNext()) {
                hashSet.add(StringsKt.trim((CharSequence) it.next()).toString());
            }
        }
        HashSet<String> hashSet2 = new HashSet();
        StringBuilder sb = new StringBuilder(SERVICE_DIRECTORY_LOCATION);
        Intrinsics.reifiedOperationMarker(4, "Service");
        String string = sb.append(Object.class.getName()).toString();
        JvmClassLoaderUtilKt.forAllMatchingFiles(classLoader, string, new String[]{string}, new AnonymousClass3(hashSet2));
        ArrayList arrayList = new ArrayList();
        for (String str2 : hashSet2) {
            Object objNewInstance = null;
            if (!hashSet.contains(str2) && !hashSet.contains(StringsKt.substringAfterLast$default(str2, '.', (String) null, 2, (Object) null))) {
                objNewInstance = classLoader.loadClass(str2).newInstance();
                Intrinsics.reifiedOperationMarker(1, "Service");
            }
            if (objNewInstance != null) {
                arrayList.add(objNewInstance);
            }
        }
        return arrayList;
    }

    private static final /* synthetic */ <Service> List<Service> loadServices(ClassLoader classLoader, Function1<? super String, Boolean> function1) throws IllegalAccessException, InstantiationException {
        Object objNewInstance;
        HashSet<String> hashSet = new HashSet();
        StringBuilder sb = new StringBuilder(SERVICE_DIRECTORY_LOCATION);
        Intrinsics.reifiedOperationMarker(4, "Service");
        String string = sb.append(Object.class.getName()).toString();
        JvmClassLoaderUtilKt.forAllMatchingFiles(classLoader, string, new String[]{string}, new AnonymousClass3(hashSet));
        ArrayList arrayList = new ArrayList();
        for (String str : hashSet) {
            if (function1.invoke(str).booleanValue()) {
                objNewInstance = classLoader.loadClass(str).newInstance();
                Intrinsics.reifiedOperationMarker(1, "Service");
            } else {
                objNewInstance = null;
            }
            if (objNewInstance != null) {
                arrayList.add(objNewInstance);
            }
        }
        return arrayList;
    }

    /* JADX INFO: renamed from: org.jetbrains.kotlin.scripting.compiler.plugin.impl.LoadCompilerPluginsKt$loadServices$3, reason: invalid class name */
    /* JADX INFO: compiled from: loadCompilerPlugins.kt */
    @Metadata(d1 = {"\u0000\u001a\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\u0010\u0000\u001a\u00020\u0001\"\n\b\u0000\u0010\u0002\u0018\u0001*\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u0007H\n¢\u0006\u0002\b\b"}, d2 = {SpecialNames.ANONYMOUS_STRING, "", "Service", "", "name", "", "stream", "Ljava/io/InputStream;", "invoke"}, k = 3, mv = {1, 9, 0}, xi = 48)
    public static final class AnonymousClass3 extends Lambda implements Function2<String, InputStream, Unit> {
        final /* synthetic */ HashSet<String> $registrarsNames;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass3(HashSet<String> hashSet) {
            super(2);
            this.$registrarsNames = hashSet;
        }

        @Override // kotlin.jvm.functions.Function2
        public /* bridge */ /* synthetic */ Unit invoke(String str, InputStream inputStream) {
            invoke2(str, inputStream);
            return Unit.INSTANCE;
        }

        /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
        public final void invoke2(String name2, InputStream stream) {
            Intrinsics.checkNotNullParameter(name2, "name");
            Intrinsics.checkNotNullParameter(stream, "stream");
            Reader inputStreamReader = new InputStreamReader(stream, Charsets.UTF_8);
            HashSet<String> hashSet = this.$registrarsNames;
            BufferedReader bufferedReader = inputStreamReader instanceof BufferedReader ? (BufferedReader) inputStreamReader : new BufferedReader(inputStreamReader, 8192);
            try {
                Iterator<String> it = TextStreamsKt.lineSequence(bufferedReader).iterator();
                while (it.hasNext()) {
                    String serviceFileLine = LoadCompilerPluginsKt.parseServiceFileLine(name2, it.next());
                    if (serviceFileLine != null) {
                        hashSet.add(serviceFileLine);
                    }
                }
                Unit unit = Unit.INSTANCE;
                CloseableKt.closeFinally(bufferedReader, null);
            } catch (Throwable th) {
                try {
                    throw th;
                } catch (Throwable th2) {
                    CloseableKt.closeFinally(bufferedReader, th);
                    throw th2;
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final String parseServiceFileLine(String str, String str2) {
        boolean zIsJavaIdentifierStart;
        String string = StringsKt.trim((CharSequence) StringsKt.substringBefore$default(str2, '#', (String) null, 2, (Object) null)).toString();
        if (string.length() <= 0) {
            string = null;
        }
        if (string == null) {
            return null;
        }
        String str3 = string;
        int i = 0;
        int i2 = 0;
        while (i < str3.length()) {
            char cCharAt = str3.charAt(i);
            int i3 = i2 + 1;
            if (i2 == 0) {
                zIsJavaIdentifierStart = Character.isJavaIdentifierStart(cCharAt);
            } else {
                zIsJavaIdentifierStart = Character.isJavaIdentifierPart(cCharAt) || cCharAt == '.';
            }
            if (!zIsJavaIdentifierStart) {
                throw new RuntimeException("Error loading services from " + str + " : " + ("Invalid Java identifier: " + str2));
            }
            i++;
            i2 = i3;
        }
        return string;
    }
}
