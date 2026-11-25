package org.jetbrains.kotlin.scripting.definitions;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.LazyThreadSafetyMode;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Reflection;
import kotlin.script.experimental.annotations.KotlinScript;
import kotlin.script.experimental.api.KotlinType;
import kotlin.script.experimental.api.ScriptCompilationConfiguration;
import kotlin.script.experimental.api.ScriptCompilationKt;
import kotlin.script.experimental.api.ScriptDiagnostic;
import kotlin.script.experimental.api.ScriptEvaluationConfiguration;
import kotlin.script.experimental.host.ConfigurationFromTemplateKt;
import kotlin.script.experimental.host.HostConfigurationKt;
import kotlin.script.experimental.host.ScriptingHostConfiguration;
import kotlin.script.experimental.jvm.JvmDependency;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.utils.PathUtil;

/* JADX INFO: compiled from: LazyScriptDefinitionFromDiscoveredClass.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000b\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0012\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\r\u0018\u00002\u00020\u0001BK\b\u0016\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\f\u0010\b\u001a\b\u0012\u0004\u0012\u00020\n0\t\u0012\u001c\u0010\u000b\u001a\u0018\u0012\u0004\u0012\u00020\r\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020\u000e0\fj\u0002`\u000f¢\u0006\u0002\u0010\u0010B[\b\u0000\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0016\u0010\u0011\u001a\u0012\u0012\u0004\u0012\u00020\u00130\u0012j\b\u0012\u0004\u0012\u00020\u0013`\u0014\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\f\u0010\b\u001a\b\u0012\u0004\u0012\u00020\n0\t\u0012\u001c\u0010\u000b\u001a\u0018\u0012\u0004\u0012\u00020\r\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020\u000e0\fj\u0002`\u000f¢\u0006\u0002\u0010\u0015R\u001e\u0010\u0011\u001a\u0012\u0012\u0004\u0012\u00020\u00130\u0012j\b\u0012\u0004\u0012\u00020\u0013`\u0014X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\b\u001a\b\u0012\u0004\u0012\u00020\n0\tX\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u0016\u001a\u00020\u00178VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0018\u0010\u0019R\u001b\u0010\u001a\u001a\u00020\u001b8BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\u001e\u0010\u001f\u001a\u0004\b\u001c\u0010\u001dR\u0014\u0010 \u001a\u00020!8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\"\u0010#R\u001b\u0010$\u001a\u00020\u00078VX\u0096\u0084\u0002¢\u0006\f\n\u0004\b'\u0010\u001f\u001a\u0004\b%\u0010&R\u0014\u0010(\u001a\u00020\u00038VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b)\u0010*R$\u0010\u000b\u001a\u0018\u0012\u0004\u0012\u00020\r\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020\u000e0\fj\u0002`\u000fX\u0082\u0004¢\u0006\u0002\n\u0000R\u001b\u0010+\u001a\u00020\u00078VX\u0096\u0084\u0002¢\u0006\f\n\u0004\b-\u0010\u001f\u001a\u0004\b,\u0010&¨\u0006."}, d2 = {"Lorg/jetbrains/kotlin/scripting/definitions/LazyScriptDefinitionFromDiscoveredClass;", "Lorg/jetbrains/kotlin/scripting/definitions/ScriptDefinition$FromConfigurationsBase;", "baseHostConfiguration", "Lkotlin/script/experimental/host/ScriptingHostConfiguration;", "classBytes", "", "className", "", ModuleXmlParser.CLASSPATH, "", "Ljava/io/File;", "messageReporter", "Lkotlin/Function2;", "Lkotlin/script/experimental/api/ScriptDiagnostic$Severity;", "", "Lorg/jetbrains/kotlin/scripting/definitions/MessageReporter;", "(Lkotlin/script/experimental/host/ScriptingHostConfiguration;[BLjava/lang/String;Ljava/util/List;Lkotlin/jvm/functions/Function2;)V", "annotationsFromAsm", "Ljava/util/ArrayList;", "Lorg/jetbrains/kotlin/scripting/definitions/BinAnnData;", "Lkotlin/collections/ArrayList;", "(Lkotlin/script/experimental/host/ScriptingHostConfiguration;Ljava/util/ArrayList;Ljava/lang/String;Ljava/util/List;Lkotlin/jvm/functions/Function2;)V", "compilationConfiguration", "Lkotlin/script/experimental/api/ScriptCompilationConfiguration;", "getCompilationConfiguration", "()Lkotlin/script/experimental/api/ScriptCompilationConfiguration;", "definition", "Lkotlin/script/experimental/host/ScriptDefinition;", "getDefinition", "()Lkotlin/script/experimental/host/ScriptDefinition;", "definition$delegate", "Lkotlin/Lazy;", "evaluationConfiguration", "Lkotlin/script/experimental/api/ScriptEvaluationConfiguration;", "getEvaluationConfiguration", "()Lkotlin/script/experimental/api/ScriptEvaluationConfiguration;", "fileExtension", "getFileExtension", "()Ljava/lang/String;", "fileExtension$delegate", "hostConfiguration", "getHostConfiguration", "()Lkotlin/script/experimental/host/ScriptingHostConfiguration;", "name", "getName", "name$delegate", PathUtil.KOTLIN_SCRIPTING_COMPILER_IMPL_NAME}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class LazyScriptDefinitionFromDiscoveredClass extends ScriptDefinition.FromConfigurationsBase {
    private final ArrayList<BinAnnData> annotationsFromAsm;
    private final ScriptingHostConfiguration baseHostConfiguration;
    private final String className;
    private final List<File> classpath;

    /* JADX INFO: renamed from: definition$delegate, reason: from kotlin metadata */
    private final Lazy definition;

    /* JADX INFO: renamed from: fileExtension$delegate, reason: from kotlin metadata */
    private final Lazy fileExtension;
    private final Function2<ScriptDiagnostic.Severity, String, Unit> messageReporter;

    /* JADX INFO: renamed from: name$delegate, reason: from kotlin metadata */
    private final Lazy name;

    /* JADX WARN: Multi-variable type inference failed */
    public LazyScriptDefinitionFromDiscoveredClass(ScriptingHostConfiguration baseHostConfiguration, ArrayList<BinAnnData> annotationsFromAsm, String className, List<? extends File> classpath, Function2<? super ScriptDiagnostic.Severity, ? super String, Unit> messageReporter) {
        Intrinsics.checkNotNullParameter(baseHostConfiguration, "baseHostConfiguration");
        Intrinsics.checkNotNullParameter(annotationsFromAsm, "annotationsFromAsm");
        Intrinsics.checkNotNullParameter(className, "className");
        Intrinsics.checkNotNullParameter(classpath, "classpath");
        Intrinsics.checkNotNullParameter(messageReporter, "messageReporter");
        this.baseHostConfiguration = baseHostConfiguration;
        this.annotationsFromAsm = annotationsFromAsm;
        this.className = className;
        this.classpath = classpath;
        this.messageReporter = messageReporter;
        this.definition = LazyKt.lazy(LazyThreadSafetyMode.PUBLICATION, (Function0) new Function0<kotlin.script.experimental.host.ScriptDefinition>() { // from class: org.jetbrains.kotlin.scripting.definitions.LazyScriptDefinitionFromDiscoveredClass$definition$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final kotlin.script.experimental.host.ScriptDefinition invoke() {
                this.this$0.messageReporter.invoke(ScriptDiagnostic.Severity.DEBUG, "Configure scripting: loading script definition class " + this.this$0.className + " using classpath " + this.this$0.classpath + "\n.  " + Thread.currentThread().getStackTrace());
                try {
                    KotlinType kotlinType = new KotlinType(this.this$0.className, false, 2, (DefaultConstructorMarker) null);
                    ScriptingHostConfiguration scriptingHostConfiguration = this.this$0.baseHostConfiguration;
                    final LazyScriptDefinitionFromDiscoveredClass lazyScriptDefinitionFromDiscoveredClass = this.this$0;
                    return ConfigurationFromTemplateKt.createScriptDefinitionFromTemplate$default(kotlinType, HostConfigurationKt.with(scriptingHostConfiguration, new Function1<ScriptingHostConfiguration.Builder, Unit>() { // from class: org.jetbrains.kotlin.scripting.definitions.LazyScriptDefinitionFromDiscoveredClass$definition$2.1
                        {
                            super(1);
                        }

                        @Override // kotlin.jvm.functions.Function1
                        public /* bridge */ /* synthetic */ Unit invoke(ScriptingHostConfiguration.Builder builder) {
                            invoke2(builder);
                            return Unit.INSTANCE;
                        }

                        /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
                        public final void invoke2(ScriptingHostConfiguration.Builder with) {
                            Intrinsics.checkNotNullParameter(with, "$this$with");
                            if (lazyScriptDefinitionFromDiscoveredClass.classpath.isEmpty()) {
                                return;
                            }
                            with.append(HostConfigurationKt.getConfigurationDependencies(with), new JvmDependency((List<? extends File>) lazyScriptDefinitionFromDiscoveredClass.classpath));
                        }
                    }), Reflection.getOrCreateKotlinClass(LazyScriptDefinitionFromDiscoveredClass.class), null, null, 24, null);
                } catch (ClassNotFoundException unused) {
                    this.this$0.messageReporter.invoke(ScriptDiagnostic.Severity.ERROR, "Cannot find script definition class " + this.this$0.className);
                    return LazyScriptDefinitionFromDiscoveredClassKt.getInvalidScriptDefinition();
                } catch (Exception e) {
                    this.this$0.messageReporter.invoke(ScriptDiagnostic.Severity.ERROR, "Error processing script definition class " + this.this$0.className + ": " + e.getMessage() + "\nclasspath:\n" + CollectionsKt.joinToString$default(this.this$0.classpath, "\n", "    ", null, 0, null, null, 60, null));
                    return LazyScriptDefinitionFromDiscoveredClassKt.getInvalidScriptDefinition();
                }
            }
        });
        this.fileExtension = LazyKt.lazy(LazyThreadSafetyMode.PUBLICATION, (Function0) new Function0<String>() { // from class: org.jetbrains.kotlin.scripting.definitions.LazyScriptDefinitionFromDiscoveredClass$fileExtension$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final String invoke() {
                Object obj;
                Object next;
                ArrayList<BinAnnArgData> args;
                String value;
                Iterator it = this.this$0.annotationsFromAsm.iterator();
                do {
                    obj = null;
                    if (!it.hasNext()) {
                        next = null;
                        break;
                    }
                    next = it.next();
                } while (!Intrinsics.areEqual(((BinAnnData) next).getName(), KotlinScript.class.getSimpleName()));
                BinAnnData binAnnData = (BinAnnData) next;
                if (binAnnData != null && (args = binAnnData.getArgs()) != null) {
                    for (Object obj2 : args) {
                        if (Intrinsics.areEqual(((BinAnnArgData) obj2).getName(), "fileExtension")) {
                            obj = obj2;
                            break;
                        }
                    }
                    BinAnnArgData binAnnArgData = (BinAnnArgData) obj;
                    if (binAnnArgData != null && (value = binAnnArgData.getValue()) != null) {
                        return value;
                    }
                }
                ScriptCompilationConfiguration compilationConfiguration = this.this$0.getCompilationConfiguration();
                LazyScriptDefinitionFromDiscoveredClass lazyScriptDefinitionFromDiscoveredClass = this.this$0;
                String str = (String) compilationConfiguration.get(ScriptCompilationKt.getFileExtension(ScriptCompilationConfiguration.INSTANCE));
                return str == null ? super/*org.jetbrains.kotlin.scripting.definitions.ScriptDefinition.FromConfigurationsBase*/.getFileExtension() : str;
            }
        });
        this.name = LazyKt.lazy(LazyThreadSafetyMode.PUBLICATION, (Function0) new Function0<String>() { // from class: org.jetbrains.kotlin.scripting.definitions.LazyScriptDefinitionFromDiscoveredClass$name$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final String invoke() {
                Object obj;
                Object next;
                ArrayList<BinAnnArgData> args;
                String value;
                String name2;
                String simpleName;
                Iterator it = this.this$0.annotationsFromAsm.iterator();
                do {
                    obj = null;
                    if (!it.hasNext()) {
                        next = null;
                        break;
                    }
                    next = it.next();
                    name2 = ((BinAnnData) next).getName();
                    simpleName = KotlinScript.class.getSimpleName();
                    Intrinsics.checkNotNull(simpleName);
                } while (!Intrinsics.areEqual(name2, simpleName));
                BinAnnData binAnnData = (BinAnnData) next;
                if (binAnnData != null && (args = binAnnData.getArgs()) != null) {
                    for (Object obj2 : args) {
                        if (Intrinsics.areEqual(((BinAnnArgData) obj2).getName(), "name")) {
                            obj = obj2;
                            break;
                        }
                    }
                    BinAnnArgData binAnnArgData = (BinAnnArgData) obj;
                    if (binAnnArgData != null && (value = binAnnArgData.getValue()) != null) {
                        return value;
                    }
                }
                return super/*org.jetbrains.kotlin.scripting.definitions.ScriptDefinition.FromConfigurationsBase*/.getName();
            }
        });
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public LazyScriptDefinitionFromDiscoveredClass(ScriptingHostConfiguration baseHostConfiguration, byte[] classBytes, String className, List<? extends File> classpath, Function2<? super ScriptDiagnostic.Severity, ? super String, Unit> messageReporter) {
        this(baseHostConfiguration, AsmBasedAnnotationsLoadingKt.loadAnnotationsFromClass(classBytes), className, classpath, messageReporter);
        Intrinsics.checkNotNullParameter(baseHostConfiguration, "baseHostConfiguration");
        Intrinsics.checkNotNullParameter(classBytes, "classBytes");
        Intrinsics.checkNotNullParameter(className, "className");
        Intrinsics.checkNotNullParameter(classpath, "classpath");
        Intrinsics.checkNotNullParameter(messageReporter, "messageReporter");
    }

    private final kotlin.script.experimental.host.ScriptDefinition getDefinition() {
        return (kotlin.script.experimental.host.ScriptDefinition) this.definition.getValue();
    }

    @Override // org.jetbrains.kotlin.scripting.definitions.ScriptDefinition
    public ScriptingHostConfiguration getHostConfiguration() {
        ScriptingHostConfiguration scriptingHostConfiguration = (ScriptingHostConfiguration) getDefinition().getCompilationConfiguration().get(ScriptCompilationKt.getHostConfiguration(ScriptCompilationConfiguration.INSTANCE));
        return scriptingHostConfiguration == null ? this.baseHostConfiguration : scriptingHostConfiguration;
    }

    @Override // org.jetbrains.kotlin.scripting.definitions.ScriptDefinition
    public ScriptCompilationConfiguration getCompilationConfiguration() {
        return getDefinition().getCompilationConfiguration();
    }

    @Override // org.jetbrains.kotlin.scripting.definitions.ScriptDefinition
    public ScriptEvaluationConfiguration getEvaluationConfiguration() {
        return getDefinition().getEvaluationConfiguration();
    }

    @Override // org.jetbrains.kotlin.scripting.definitions.ScriptDefinition.FromConfigurationsBase, org.jetbrains.kotlin.scripting.definitions.ScriptDefinition
    public String getFileExtension() {
        return (String) this.fileExtension.getValue();
    }

    @Override // org.jetbrains.kotlin.scripting.definitions.ScriptDefinition.FromConfigurationsBase, org.jetbrains.kotlin.scripting.definitions.ScriptDefinition
    public String getName() {
        return (String) this.name.getValue();
    }
}
