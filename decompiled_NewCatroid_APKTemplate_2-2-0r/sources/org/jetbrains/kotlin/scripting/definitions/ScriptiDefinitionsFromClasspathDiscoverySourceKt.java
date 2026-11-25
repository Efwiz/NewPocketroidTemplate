package org.jetbrains.kotlin.scripting.definitions;

import androidx.exifinterface.media.ExifInterface;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.annotation.Annotation;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.RestrictedSuspendLambda;
import kotlin.io.ByteStreamsKt;
import kotlin.io.FilesKt;
import kotlin.jvm.JvmClassMappingKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Reflection;
import kotlin.reflect.KClass;
import kotlin.script.experimental.annotations.KotlinScript;
import kotlin.script.experimental.api.ScriptDiagnostic;
import kotlin.script.experimental.host.ScriptingHostConfiguration;
import kotlin.script.templates.ScriptTemplateDefinition;
import kotlin.sequences.Sequence;
import kotlin.sequences.SequenceScope;
import kotlin.sequences.SequencesKt;
import kotlin.text.StringsKt;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.codegen.coroutines.CoroutineCodegenUtilKt;
import org.jetbrains.kotlin.name.SpecialNames;
import org.jetbrains.kotlin.scripting.resolve.KotlinScriptDefinitionFromAnnotatedTemplate;
import org.jetbrains.kotlin.utils.PathUtil;

/* JADX INFO: compiled from: ScriptiDefinitionsFromClasspathDiscoverySource.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000t\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0012\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u001c\n\u0002\b\u0003\u001a:\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u00052\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\u001c\u0010\u000b\u001a\u0018\u0012\u0004\u0012\u00020\r\u0012\u0004\u0012\u00020\u0001\u0012\u0004\u0012\u00020\u000e0\fj\u0002`\u000f\u001aJ\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u00060\u00052\f\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\u00130\u00122\b\u0010\u0014\u001a\u0004\u0018\u00010\b2\u0006\u0010\t\u001a\u00020\n2\u001c\u0010\u000b\u001a\u0018\u0012\u0004\u0012\u00020\r\u0012\u0004\u0012\u00020\u0001\u0012\u0004\u0012\u00020\u000e0\fj\u0002`\u000f\u001a@\u0010\u0015\u001a\u0004\u0018\u00010\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\u0016\u001a\u00020\u00012\u0006\u0010\t\u001a\u00020\n2\u001c\u0010\u000b\u001a\u0018\u0012\u0004\u0012\u00020\r\u0012\u0004\u0012\u00020\u0001\u0012\u0004\u0012\u00020\u000e0\fj\u0002`\u000fH\u0002\u001aH\u0010\u0015\u001a\u0004\u0018\u00010\u00062\u0006\u0010\u0017\u001a\u00020\u00182\u0006\u0010\u0019\u001a\u00020\u00012\u0006\u0010\u001a\u001a\u00020\u001b2\u0006\u0010\t\u001a\u00020\n2\u001c\u0010\u000b\u001a\u0018\u0012\u0004\u0012\u00020\r\u0012\u0004\u0012\u00020\u0001\u0012\u0004\u0012\u00020\u000e0\fj\u0002`\u000fH\u0002\u001ad\u0010\u001c\u001a\b\u0012\u0004\u0012\u00020\u00060\u00052\f\u0010\u001d\u001a\b\u0012\u0004\u0012\u00020\u00010\u00122\f\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\u00130\u00122\f\u0010\u001e\u001a\b\u0012\u0004\u0012\u00020\u00130\u00122\u0006\u0010\u0014\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\u001c\u0010\u000b\u001a\u0018\u0012\u0004\u0012\u00020\r\u0012\u0004\u0012\u00020\u0001\u0012\u0004\u0012\u00020\u000e0\fj\u0002`\u000f\u001a<\u0010\u001f\u001a\b\u0012\u0004\u0012\u00020\u00060\u00052\u0006\u0010\u001a\u001a\u00020\u001b2\u0006\u0010\t\u001a\u00020\n2\u001c\u0010\u000b\u001a\u0018\u0012\u0004\u0012\u00020\r\u0012\u0004\u0012\u00020\u0001\u0012\u0004\u0012\u00020\u000e0\fj\u0002`\u000fH\u0002\u001aY\u0010 \u001a\u00020!*\b\u0012\u0004\u0012\u00020\u00010\u00122\u0006\u0010\u001a\u001a\u00020\u001b2\u0006\u0010\t\u001a\u00020\n2\u001e\b\b\u0010\u000b\u001a\u0018\u0012\u0004\u0012\u00020\r\u0012\u0004\u0012\u00020\u0001\u0012\u0004\u0012\u00020\u000e0\fj\u0002`\u000f2\u0014\u0010\"\u001a\u0010\u0012\u0004\u0012\u00020\u0001\u0012\u0006\u0012\u0004\u0018\u00010\u00180#H\u0082\b\u001aH\u0010$\u001a\u00020!*\b\u0012\u0004\u0012\u00020\u00010\u00122\u0006\u0010%\u001a\u00020\u00132\u0006\u0010\u001a\u001a\u00020\u001b2\u0006\u0010\t\u001a\u00020\n2\u001c\u0010\u000b\u001a\u0018\u0012\u0004\u0012\u00020\r\u0012\u0004\u0012\u00020\u0001\u0012\u0004\u0012\u00020\u000e0\fj\u0002`\u000fH\u0002\u001aH\u0010&\u001a\u00020!*\b\u0012\u0004\u0012\u00020\u00010\u00122\u0006\u0010'\u001a\u00020(2\u0006\u0010\u001a\u001a\u00020\u001b2\u0006\u0010\t\u001a\u00020\n2\u001c\u0010\u000b\u001a\u0018\u0012\u0004\u0012\u00020\r\u0012\u0004\u0012\u00020\u0001\u0012\u0004\u0012\u00020\u000e0\fj\u0002`\u000fH\u0002\u001aM\u0010)\u001a\u001a\u0012\n\u0012\b\u0012\u0004\u0012\u0002H+0\u0012\u0012\n\u0012\b\u0012\u0004\u0012\u0002H,0\u00120*\"\u0004\b\u0000\u0010,\"\u0004\b\u0001\u0010+*\b\u0012\u0004\u0012\u0002H,0-2\u0014\u0010.\u001a\u0010\u0012\u0004\u0012\u0002H,\u0012\u0006\u0012\u0004\u0018\u0001H+0#H\u0082\b\"\u000e\u0010\u0000\u001a\u00020\u0001X\u0082T¢\u0006\u0002\n\u0000\"\u000e\u0010\u0002\u001a\u00020\u0001X\u0086T¢\u0006\u0002\n\u0000\"\u000e\u0010\u0003\u001a\u00020\u0001X\u0086T¢\u0006\u0002\n\u0000*.\u0010/\"\u0014\u0012\u0004\u0012\u00020\r\u0012\u0004\u0012\u00020\u0001\u0012\u0004\u0012\u00020\u000e0\f2\u0014\u0012\u0004\u0012\u00020\r\u0012\u0004\u0012\u00020\u0001\u0012\u0004\u0012\u00020\u000e0\f¨\u00060"}, d2 = {"MANIFEST_RESOURCE_NAME", "", "SCRIPT_DEFINITION_MARKERS_EXTENSION_WITH_DOT", "SCRIPT_DEFINITION_MARKERS_PATH", "discoverScriptTemplatesInClassLoader", "Lkotlin/sequences/Sequence;", "Lorg/jetbrains/kotlin/scripting/definitions/ScriptDefinition;", "classLoader", "Ljava/lang/ClassLoader;", "hostConfiguration", "Lkotlin/script/experimental/host/ScriptingHostConfiguration;", "messageReporter", "Lkotlin/Function2;", "Lkotlin/script/experimental/api/ScriptDiagnostic$Severity;", "", "Lorg/jetbrains/kotlin/scripting/definitions/MessageReporter;", "discoverScriptTemplatesInClasspath", ModuleXmlParser.CLASSPATH, "", "Ljava/io/File;", "baseClassLoader", "loadScriptDefinition", ConfigConstants.CONFIG_KEY_COMMIT_TEMPLATE, "templateClassBytes", "", "templateClassName", "classpathWithLoader", "Lorg/jetbrains/kotlin/scripting/definitions/ClasspathWithClassLoader;", "loadScriptTemplatesFromClasspath", "scriptTemplates", "dependenciesClasspath", "scriptTemplatesDiscoverySequence", "partitionLoadDefinitions", "Lorg/jetbrains/kotlin/scripting/definitions/DefinitionsLoadPartitionResult;", "getBytes", "Lkotlin/Function1;", "partitionLoadDirDefinitions", "dir", "partitionLoadJarDefinitions", "jar", "Ljava/util/jar/JarFile;", "partitionMapNotNull", "Lkotlin/Pair;", "R", ExifInterface.GPS_DIRECTION_TRUE, "", "fn", "MessageReporter", PathUtil.KOTLIN_SCRIPTING_COMPILER_IMPL_NAME}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class ScriptiDefinitionsFromClasspathDiscoverySourceKt {
    private static final String MANIFEST_RESOURCE_NAME = "/META-INF/MANIFEST.MF";
    public static final String SCRIPT_DEFINITION_MARKERS_EXTENSION_WITH_DOT = ".classname";
    public static final String SCRIPT_DEFINITION_MARKERS_PATH = "META-INF/kotlin/script/templates/";

    public static final Sequence<ScriptDefinition> discoverScriptTemplatesInClassLoader(ClassLoader classLoader, ScriptingHostConfiguration hostConfiguration, Function2<? super ScriptDiagnostic.Severity, ? super String, Unit> messageReporter) throws IOException {
        Intrinsics.checkNotNullParameter(classLoader, "classLoader");
        Intrinsics.checkNotNullParameter(hostConfiguration, "hostConfiguration");
        Intrinsics.checkNotNullParameter(messageReporter, "messageReporter");
        Enumeration<URL> resources = classLoader.getResources(MANIFEST_RESOURCE_NAME);
        Intrinsics.checkNotNullExpressionValue(resources, "getResources(...)");
        return scriptTemplatesDiscoverySequence(new SimpleClasspathWithClassLoader(SequencesKt.toList(SequencesKt.mapNotNull(SequencesKt.asSequence(CollectionsKt.iterator(resources)), new Function1<URL, File>() { // from class: org.jetbrains.kotlin.scripting.definitions.ScriptiDefinitionsFromClasspathDiscoverySourceKt$discoverScriptTemplatesInClassLoader$classpath$1
            @Override // kotlin.jvm.functions.Function1
            public final File invoke(URL url) {
                try {
                    File file = new File(url.toURI());
                    if (file.exists()) {
                        return file;
                    }
                    return null;
                } catch (IllegalArgumentException unused) {
                    return null;
                }
            }
        })), classLoader), hostConfiguration, messageReporter);
    }

    public static final Sequence<ScriptDefinition> discoverScriptTemplatesInClasspath(final List<? extends File> classpath, ClassLoader classLoader, ScriptingHostConfiguration hostConfiguration, Function2<? super ScriptDiagnostic.Severity, ? super String, Unit> messageReporter) {
        Intrinsics.checkNotNullParameter(classpath, "classpath");
        Intrinsics.checkNotNullParameter(hostConfiguration, "hostConfiguration");
        Intrinsics.checkNotNullParameter(messageReporter, "messageReporter");
        return scriptTemplatesDiscoverySequence(new LazyClasspathWithClassLoader(classLoader, new Function0<List<? extends File>>() { // from class: org.jetbrains.kotlin.scripting.definitions.ScriptiDefinitionsFromClasspathDiscoverySourceKt$discoverScriptTemplatesInClasspath$classpathWithLoader$1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            /* JADX WARN: Multi-variable type inference failed */
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final List<? extends File> invoke() {
                return classpath;
            }
        }), hostConfiguration, messageReporter);
    }

    /* JADX INFO: renamed from: org.jetbrains.kotlin.scripting.definitions.ScriptiDefinitionsFromClasspathDiscoverySourceKt$scriptTemplatesDiscoverySequence$1, reason: invalid class name and case insensitive filesystem */
    /* JADX INFO: compiled from: ScriptiDefinitionsFromClasspathDiscoverySource.kt */
    @Metadata(d1 = {"\u0000\u000e\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\b\u0012\u0004\u0012\u00020\u00030\u0002H\u008a@"}, d2 = {SpecialNames.ANONYMOUS_STRING, "", "Lkotlin/sequences/SequenceScope;", "Lorg/jetbrains/kotlin/scripting/definitions/ScriptDefinition;"}, k = 3, mv = {1, 9, 0}, xi = 48)
    @DebugMetadata(c = "org.jetbrains.kotlin.scripting.definitions.ScriptiDefinitionsFromClasspathDiscoverySourceKt$scriptTemplatesDiscoverySequence$1", f = "ScriptiDefinitionsFromClasspathDiscoverySource.kt", i = {0, 0, 0, 0, 1, 1, 1, 1, 1, 2, 2, 2, 2}, l = {107, 122, 145}, m = CoroutineCodegenUtilKt.INVOKE_SUSPEND_METHOD_NAME, n = {"$this$sequence", "defferedDirDependencies", "defferedDefinitionCandidates", "dep", "$this$sequence", "defferedDirDependencies", "defferedDefinitionCandidates", "dep", "notFoundDefinitions", "$this$sequence", "remainingDefinitionCandidates", "dep", "notFoundDefinitions"}, s = {"L$0", "L$1", "L$2", "L$4", "L$0", "L$1", "L$2", "L$4", "L$5", "L$0", "L$1", "L$3", "L$4"})
    static final class C50241 extends RestrictedSuspendLambda implements Function2<SequenceScope<? super ScriptDefinition>, Continuation<? super Unit>, Object> {
        final /* synthetic */ ClasspathWithClassLoader $classpathWithLoader;
        final /* synthetic */ ScriptingHostConfiguration $hostConfiguration;
        final /* synthetic */ Function2<ScriptDiagnostic.Severity, String, Unit> $messageReporter;
        private /* synthetic */ Object L$0;
        Object L$1;
        Object L$2;
        Object L$3;
        Object L$4;
        Object L$5;
        Object L$6;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        /* JADX WARN: Multi-variable type inference failed */
        C50241(ClasspathWithClassLoader classpathWithClassLoader, ScriptingHostConfiguration scriptingHostConfiguration, Function2<? super ScriptDiagnostic.Severity, ? super String, Unit> function2, Continuation<? super C50241> continuation) {
            super(2, continuation);
            this.$classpathWithLoader = classpathWithClassLoader;
            this.$hostConfiguration = scriptingHostConfiguration;
            this.$messageReporter = function2;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
            C50241 c50241 = new C50241(this.$classpathWithLoader, this.$hostConfiguration, this.$messageReporter, continuation);
            c50241.L$0 = obj;
            return c50241;
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(SequenceScope<? super ScriptDefinition> sequenceScope, Continuation<? super Unit> continuation) {
            return ((C50241) create(sequenceScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        /* JADX WARN: Code duplicated, block: B:141:0x00c7 A[EXC_TOP_SPLITTER, SYNTHETIC] */
        /* JADX WARN: Code duplicated, block: B:145:0x011c A[EXC_TOP_SPLITTER, SYNTHETIC] */
        /* JADX WARN: Code duplicated, block: B:171:0x0188 A[SYNTHETIC] */
        /* JADX WARN: Code duplicated, block: B:27:0x00b8  */
        /* JADX WARN: Code duplicated, block: B:33:0x00d3 A[Catch: IOException -> 0x01a8, TRY_LEAVE, TryCatch #6 {IOException -> 0x01a8, blocks: (B:31:0x00c7, B:33:0x00d3), top: B:141:0x00c7 }] */
        /* JADX WARN: Code duplicated, block: B:36:0x00ea A[Catch: all -> 0x019d, TRY_LEAVE, TryCatch #13 {all -> 0x019d, blocks: (B:34:0x00e1, B:36:0x00ea, B:42:0x015c), top: B:155:0x00e1 }] */
        /* JADX WARN: Code duplicated, block: B:47:0x016a A[Catch: all -> 0x018a, TRY_LEAVE, TryCatch #4 {all -> 0x018a, blocks: (B:45:0x0164, B:47:0x016a), top: B:137:0x0164 }] */
        /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:105:0x02b0 -> B:149:0x02b6). Please report as a decompilation issue!!! */
        /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:125:0x02e7 -> B:100:0x0282). Please report as a decompilation issue!!! */
        /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:18:0x0069 -> B:97:0x0258). Please report as a decompilation issue!!! */
        /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:59:0x0196 -> B:25:0x00b2). Please report as a decompilation issue!!! */
        /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:81:0x01ee -> B:82:0x020a). Please report as a decompilation issue!!! */
        /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:95:0x023c -> B:98:0x0274). Please report as a decompilation issue!!! */
        /*  JADX ERROR: JadxOverflowException in pass: RegionMakerVisitor
            jadx.core.utils.exceptions.JadxOverflowException: Regions stack size limit reached
            	at jadx.core.utils.ErrorsCounter.addError(ErrorsCounter.java:59)
            	at jadx.core.utils.ErrorsCounter.error(ErrorsCounter.java:31)
            	at jadx.core.dex.attributes.nodes.NotificationAttrNode.addError(NotificationAttrNode.java:19)
            */
        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final java.lang.Object invokeSuspend(java.lang.Object r27) {
            /*
                Method dump skipped, instruction units count: 837
                To view this dump add '--comments-level debug' option
            */
            throw new UnsupportedOperationException("Method not decompiled: org.jetbrains.kotlin.scripting.definitions.ScriptiDefinitionsFromClasspathDiscoverySourceKt.C50241.invokeSuspend(java.lang.Object):java.lang.Object");
        }
    }

    private static final Sequence<ScriptDefinition> scriptTemplatesDiscoverySequence(ClasspathWithClassLoader classpathWithClassLoader, ScriptingHostConfiguration scriptingHostConfiguration, Function2<? super ScriptDiagnostic.Severity, ? super String, Unit> function2) {
        return SequencesKt.sequence(new C50241(classpathWithClassLoader, scriptingHostConfiguration, function2, null));
    }

    /* JADX INFO: renamed from: org.jetbrains.kotlin.scripting.definitions.ScriptiDefinitionsFromClasspathDiscoverySourceKt$loadScriptTemplatesFromClasspath$1, reason: invalid class name */
    /* JADX INFO: compiled from: ScriptiDefinitionsFromClasspathDiscoverySource.kt */
    @Metadata(d1 = {"\u0000\u000e\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\b\u0012\u0004\u0012\u00020\u00030\u0002H\u008a@"}, d2 = {SpecialNames.ANONYMOUS_STRING, "", "Lkotlin/sequences/SequenceScope;", "Lorg/jetbrains/kotlin/scripting/definitions/ScriptDefinition;"}, k = 3, mv = {1, 9, 0}, xi = 48)
    @DebugMetadata(c = "org.jetbrains.kotlin.scripting.definitions.ScriptiDefinitionsFromClasspathDiscoverySourceKt$loadScriptTemplatesFromClasspath$1", f = "ScriptiDefinitionsFromClasspathDiscoverySource.kt", i = {0, 0, 1, 1, 1, 1, 1}, l = {183, 214}, m = CoroutineCodegenUtilKt.INVOKE_SUSPEND_METHOD_NAME, n = {"$this$sequence", "initialNotFoundTemplates", "$this$sequence", "remainingTemplates", "classpathWithLoader", "dep", "notFoundTemplates"}, s = {"L$0", "L$1", "L$0", "L$1", "L$2", "L$4", "L$5"})
    static final class AnonymousClass1 extends RestrictedSuspendLambda implements Function2<SequenceScope<? super ScriptDefinition>, Continuation<? super Unit>, Object> {
        final /* synthetic */ ClassLoader $baseClassLoader;
        final /* synthetic */ List<File> $classpath;
        final /* synthetic */ List<File> $dependenciesClasspath;
        final /* synthetic */ ScriptingHostConfiguration $hostConfiguration;
        final /* synthetic */ Function2<ScriptDiagnostic.Severity, String, Unit> $messageReporter;
        final /* synthetic */ List<String> $scriptTemplates;
        private /* synthetic */ Object L$0;
        Object L$1;
        Object L$2;
        Object L$3;
        Object L$4;
        Object L$5;
        Object L$6;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        /* JADX WARN: Multi-variable type inference failed */
        AnonymousClass1(List<String> list, ClassLoader classLoader, List<? extends File> list2, ScriptingHostConfiguration scriptingHostConfiguration, Function2<? super ScriptDiagnostic.Severity, ? super String, Unit> function2, List<? extends File> list3, Continuation<? super AnonymousClass1> continuation) {
            super(2, continuation);
            this.$scriptTemplates = list;
            this.$baseClassLoader = classLoader;
            this.$classpath = list2;
            this.$hostConfiguration = scriptingHostConfiguration;
            this.$messageReporter = function2;
            this.$dependenciesClasspath = list3;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
            AnonymousClass1 anonymousClass1 = new AnonymousClass1(this.$scriptTemplates, this.$baseClassLoader, this.$classpath, this.$hostConfiguration, this.$messageReporter, this.$dependenciesClasspath, continuation);
            anonymousClass1.L$0 = obj;
            return anonymousClass1;
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(SequenceScope<? super ScriptDefinition> sequenceScope, Continuation<? super Unit> continuation) {
            return ((AnonymousClass1) create(sequenceScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        /* JADX WARN: Code duplicated, block: B:30:0x00e2  */
        /* JADX WARN: Code duplicated, block: B:32:0x00f3 A[Catch: IOException -> 0x002f, TRY_ENTER, TryCatch #2 {IOException -> 0x002f, blocks: (B:7:0x002a, B:53:0x0180, B:55:0x0186, B:58:0x01a3, B:32:0x00f3, B:34:0x00f9, B:36:0x0105, B:39:0x011f, B:50:0x0167, B:52:0x0178, B:44:0x0126, B:45:0x0129, B:46:0x012a, B:48:0x0130, B:49:0x0140, B:37:0x0110, B:42:0x0124), top: B:70:0x002a, inners: #0, #1 }] */
        /* JADX WARN: Code duplicated, block: B:34:0x00f9 A[Catch: IOException -> 0x002f, TryCatch #2 {IOException -> 0x002f, blocks: (B:7:0x002a, B:53:0x0180, B:55:0x0186, B:58:0x01a3, B:32:0x00f3, B:34:0x00f9, B:36:0x0105, B:39:0x011f, B:50:0x0167, B:52:0x0178, B:44:0x0126, B:45:0x0129, B:46:0x012a, B:48:0x0130, B:49:0x0140, B:37:0x0110, B:42:0x0124), top: B:70:0x002a, inners: #0, #1 }] */
        /* JADX WARN: Code duplicated, block: B:46:0x012a A[Catch: IOException -> 0x002f, TryCatch #2 {IOException -> 0x002f, blocks: (B:7:0x002a, B:53:0x0180, B:55:0x0186, B:58:0x01a3, B:32:0x00f3, B:34:0x00f9, B:36:0x0105, B:39:0x011f, B:50:0x0167, B:52:0x0178, B:44:0x0126, B:45:0x0129, B:46:0x012a, B:48:0x0130, B:49:0x0140, B:37:0x0110, B:42:0x0124), top: B:70:0x002a, inners: #0, #1 }] */
        /* JADX WARN: Code duplicated, block: B:48:0x0130 A[Catch: IOException -> 0x002f, TryCatch #2 {IOException -> 0x002f, blocks: (B:7:0x002a, B:53:0x0180, B:55:0x0186, B:58:0x01a3, B:32:0x00f3, B:34:0x00f9, B:36:0x0105, B:39:0x011f, B:50:0x0167, B:52:0x0178, B:44:0x0126, B:45:0x0129, B:46:0x012a, B:48:0x0130, B:49:0x0140, B:37:0x0110, B:42:0x0124), top: B:70:0x002a, inners: #0, #1 }] */
        /* JADX WARN: Code duplicated, block: B:49:0x0140 A[Catch: IOException -> 0x002f, TryCatch #2 {IOException -> 0x002f, blocks: (B:7:0x002a, B:53:0x0180, B:55:0x0186, B:58:0x01a3, B:32:0x00f3, B:34:0x00f9, B:36:0x0105, B:39:0x011f, B:50:0x0167, B:52:0x0178, B:44:0x0126, B:45:0x0129, B:46:0x012a, B:48:0x0130, B:49:0x0140, B:37:0x0110, B:42:0x0124), top: B:70:0x002a, inners: #0, #1 }] */
        /* JADX WARN: Code duplicated, block: B:55:0x0186 A[Catch: IOException -> 0x002f, TryCatch #2 {IOException -> 0x002f, blocks: (B:7:0x002a, B:53:0x0180, B:55:0x0186, B:58:0x01a3, B:32:0x00f3, B:34:0x00f9, B:36:0x0105, B:39:0x011f, B:50:0x0167, B:52:0x0178, B:44:0x0126, B:45:0x0129, B:46:0x012a, B:48:0x0130, B:49:0x0140, B:37:0x0110, B:42:0x0124), top: B:70:0x002a, inners: #0, #1 }] */
        /* JADX WARN: Code duplicated, block: B:73:0x01a2 A[SYNTHETIC] */
        /* JADX WARN: Code duplicated, block: B:75:? A[LOOP:0: B:53:0x0180->B:75:?, LOOP_END, SYNTHETIC] */
        /* JADX WARN: Code duplicated, block: B:76:0x0178 A[SYNTHETIC] */
        /* JADX WARN: Code duplicated, block: B:78:0x01c9 A[SYNTHETIC] */
        /* JADX WARN: Code duplicated, block: B:80:? A[LOOP:1: B:28:0x00dc->B:80:?, LOOP_END, SYNTHETIC] */
        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:52:0x0178 -> B:53:0x0180). Please report as a decompilation issue!!! */
        /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:60:0x01a7 -> B:28:0x00dc). Please report as a decompilation issue!!! */
        /*  JADX ERROR: JadxOverflowException in pass: RegionMakerVisitor
            jadx.core.utils.exceptions.JadxOverflowException: Regions stack size limit reached
            	at jadx.core.utils.ErrorsCounter.addError(ErrorsCounter.java:59)
            	at jadx.core.utils.ErrorsCounter.error(ErrorsCounter.java:31)
            	at jadx.core.dex.attributes.nodes.NotificationAttrNode.addError(NotificationAttrNode.java:19)
            */
        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final java.lang.Object invokeSuspend(java.lang.Object r14) {
            /*
                Method dump skipped, instruction units count: 514
                To view this dump add '--comments-level debug' option
            */
            throw new UnsupportedOperationException("Method not decompiled: org.jetbrains.kotlin.scripting.definitions.ScriptiDefinitionsFromClasspathDiscoverySourceKt.AnonymousClass1.invokeSuspend(java.lang.Object):java.lang.Object");
        }
    }

    public static final Sequence<ScriptDefinition> loadScriptTemplatesFromClasspath(List<String> scriptTemplates, List<? extends File> classpath, List<? extends File> dependenciesClasspath, ClassLoader baseClassLoader, ScriptingHostConfiguration hostConfiguration, Function2<? super ScriptDiagnostic.Severity, ? super String, Unit> messageReporter) {
        Intrinsics.checkNotNullParameter(scriptTemplates, "scriptTemplates");
        Intrinsics.checkNotNullParameter(classpath, "classpath");
        Intrinsics.checkNotNullParameter(dependenciesClasspath, "dependenciesClasspath");
        Intrinsics.checkNotNullParameter(baseClassLoader, "baseClassLoader");
        Intrinsics.checkNotNullParameter(hostConfiguration, "hostConfiguration");
        Intrinsics.checkNotNullParameter(messageReporter, "messageReporter");
        return scriptTemplates.isEmpty() ? SequencesKt.emptySequence() : SequencesKt.sequence(new AnonymousClass1(scriptTemplates, baseClassLoader, classpath, hostConfiguration, messageReporter, dependenciesClasspath, null));
    }

    private static final DefinitionsLoadPartitionResult partitionLoadDefinitions(List<String> list, ClasspathWithClassLoader classpathWithClassLoader, ScriptingHostConfiguration scriptingHostConfiguration, Function2<? super ScriptDiagnostic.Severity, ? super String, Unit> function2, Function1<? super String, byte[]> function1) {
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        for (String str : list) {
            byte[] bArrInvoke = function1.invoke(str);
            ScriptDefinition scriptDefinitionLoadScriptDefinition = bArrInvoke != null ? loadScriptDefinition(bArrInvoke, str, classpathWithClassLoader, scriptingHostConfiguration, function2) : null;
            if (scriptDefinitionLoadScriptDefinition != null) {
                arrayList.add(scriptDefinitionLoadScriptDefinition);
            } else if (bArrInvoke == null) {
                arrayList2.add(str);
            }
        }
        return new DefinitionsLoadPartitionResult(arrayList, arrayList2);
    }

    private static final ScriptDefinition loadScriptDefinition(byte[] bArr, String str, ClasspathWithClassLoader classpathWithClassLoader, ScriptingHostConfiguration scriptingHostConfiguration, Function2<? super ScriptDiagnostic.Severity, ? super String, Unit> function2) throws ClassNotFoundException {
        ScriptDefinition.FromLegacy fromLegacy;
        ArrayList<BinAnnData> arrayListLoadAnnotationsFromClass = AsmBasedAnnotationsLoadingKt.loadAnnotationsFromClass(bArr);
        Iterator<BinAnnData> it = arrayListLoadAnnotationsFromClass.iterator();
        do {
            fromLegacy = null;
            if (it.hasNext()) {
                BinAnnData next = it.next();
                if (Intrinsics.areEqual(next.getName(), KotlinScript.class.getSimpleName())) {
                    fromLegacy = new LazyScriptDefinitionFromDiscoveredClass(scriptingHostConfiguration, arrayListLoadAnnotationsFromClass, str, classpathWithClassLoader.getClasspath(), function2);
                } else if (Intrinsics.areEqual(next.getName(), ScriptTemplateDefinition.class.getSimpleName())) {
                    Class<?> clsLoadClass = classpathWithClassLoader.getClassLoader().loadClass(str);
                    Intrinsics.checkNotNullExpressionValue(clsLoadClass, "loadClass(...)");
                    KClass kotlinClass = JvmClassMappingKt.getKotlinClass(clsLoadClass);
                    Function0 function0 = (Function0) scriptingHostConfiguration.get(ScriptCompilationConfigurationFromDefinitionKt.getGetEnvironment(ScriptingHostConfiguration.INSTANCE));
                    Map mapEmptyMap = function0 != null ? (Map) function0.invoke() : null;
                    if (mapEmptyMap == null) {
                        mapEmptyMap = MapsKt.emptyMap();
                    }
                    fromLegacy = new ScriptDefinition.FromLegacy(scriptingHostConfiguration, new KotlinScriptDefinitionFromAnnotatedTemplate(kotlinClass, mapEmptyMap, classpathWithClassLoader.getClasspath()), null, 4, null);
                }
            } else {
                function2.invoke(ScriptDiagnostic.Severity.WARNING, "Configure scripting: " + str + " is not marked with any known kotlin script annotation");
                return null;
            }
        } while (fromLegacy == null);
        function2.invoke(ScriptDiagnostic.Severity.DEBUG, "Configure scripting: Added template " + str + " from " + CollectionsKt.sorted(classpathWithClassLoader.getClasspath()));
        return fromLegacy;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final ScriptDefinition loadScriptDefinition(ClassLoader classLoader, String str, ScriptingHostConfiguration scriptingHostConfiguration, Function2<? super ScriptDiagnostic.Severity, ? super String, Unit> function2) {
        Annotation annotation;
        ScriptDefinition.FromLegacyTemplate fromLegacyTemplate;
        try {
            Class<?> clsLoadClass = classLoader.loadClass(str);
            Annotation[] annotations = clsLoadClass.getAnnotations();
            Intrinsics.checkNotNullExpressionValue(annotations, "getAnnotations(...)");
            Annotation[] annotationArr = annotations;
            int length = annotationArr.length;
            int i = 0;
            while (true) {
                if (i >= length) {
                    annotation = null;
                    break;
                }
                annotation = annotationArr[i];
                if (annotation instanceof KotlinScript) {
                    break;
                }
                i++;
            }
            if (annotation != null) {
                Intrinsics.checkNotNull(clsLoadClass);
                fromLegacyTemplate = new ScriptDefinition.FromTemplate(scriptingHostConfiguration, JvmClassMappingKt.getKotlinClass(clsLoadClass), Reflection.getOrCreateKotlinClass(ScriptDefinition.class), null, 8, null);
            } else {
                Intrinsics.checkNotNull(clsLoadClass);
                fromLegacyTemplate = new ScriptDefinition.FromLegacyTemplate(scriptingHostConfiguration, JvmClassMappingKt.getKotlinClass(clsLoadClass), null, null, 12, null);
            }
            function2.invoke(ScriptDiagnostic.Severity.DEBUG, "Added script definition " + str + " to configuration: name = " + fromLegacyTemplate.getName());
            return fromLegacyTemplate;
        } catch (ClassNotFoundException unused) {
            return null;
        } catch (Exception e) {
            function2.invoke(ScriptDiagnostic.Severity.WARNING, "Error on loading script definition " + str + ": " + e.getMessage());
            return null;
        }
    }

    private static final <T, R> Pair<List<R>, List<T>> partitionMapNotNull(Iterable<? extends T> iterable, Function1<? super T, ? extends R> function1) {
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        for (T t : iterable) {
            R rInvoke = function1.invoke(t);
            if (rInvoke != null) {
                arrayList.add(rInvoke);
            } else {
                arrayList2.add(t);
            }
        }
        return TuplesKt.to(arrayList, arrayList2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final DefinitionsLoadPartitionResult partitionLoadJarDefinitions(List<String> list, JarFile jarFile, ClasspathWithClassLoader classpathWithClassLoader, ScriptingHostConfiguration scriptingHostConfiguration, Function2<? super ScriptDiagnostic.Severity, ? super String, Unit> function2) throws IOException {
        byte[] bytes;
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        for (String str : list) {
            JarEntry jarEntry = jarFile.getJarEntry(StringsKt.replace$default(str, '.', '/', false, 4, (Object) null) + ".class");
            if (jarEntry != null) {
                Intrinsics.checkNotNull(jarEntry);
                InputStream inputStream = jarFile.getInputStream(jarEntry);
                Intrinsics.checkNotNullExpressionValue(inputStream, "getInputStream(...)");
                bytes = ByteStreamsKt.readBytes(inputStream);
            } else {
                bytes = null;
            }
            ScriptDefinition scriptDefinitionLoadScriptDefinition = bytes != null ? loadScriptDefinition(bytes, str, classpathWithClassLoader, scriptingHostConfiguration, function2) : null;
            if (scriptDefinitionLoadScriptDefinition != null) {
                arrayList.add(scriptDefinitionLoadScriptDefinition);
            } else if (bytes == null) {
                arrayList2.add(str);
            }
        }
        return new DefinitionsLoadPartitionResult(arrayList, arrayList2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final DefinitionsLoadPartitionResult partitionLoadDirDefinitions(List<String> list, File file, ClasspathWithClassLoader classpathWithClassLoader, ScriptingHostConfiguration scriptingHostConfiguration, Function2<? super ScriptDiagnostic.Severity, ? super String, Unit> function2) {
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        for (String str : list) {
            File file2 = new File(file, StringsKt.replace$default(str, '.', '/', false, 4, (Object) null) + ".class");
            if (!file2.exists() || !file2.isFile()) {
                file2 = null;
            }
            byte[] bytes = file2 != null ? FilesKt.readBytes(file2) : null;
            ScriptDefinition scriptDefinitionLoadScriptDefinition = bytes != null ? loadScriptDefinition(bytes, str, classpathWithClassLoader, scriptingHostConfiguration, function2) : null;
            if (scriptDefinitionLoadScriptDefinition != null) {
                arrayList.add(scriptDefinitionLoadScriptDefinition);
            } else if (bytes == null) {
                arrayList2.add(str);
            }
        }
        return new DefinitionsLoadPartitionResult(arrayList, arrayList2);
    }
}
