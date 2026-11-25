package org.jetbrains.kotlin.codegen.state;

import com.android.apksig.internal.apk.AndroidBinXmlParser;
import com.android.dx.rop.code.RegisterSpec;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import com.reandroid.dex.sections.Marker;
import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import kotlin.KotlinVersion;
import kotlin.Metadata;
import kotlin.Triple;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.collections.SetsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.codegen.ClassBuilderFactory;
import org.jetbrains.kotlin.codegen.ClassBuilderMode;
import org.jetbrains.kotlin.codegen.ClassFileFactory;
import org.jetbrains.kotlin.codegen.CodegenFactory;
import org.jetbrains.kotlin.codegen.JvmBackendClassResolver;
import org.jetbrains.kotlin.codegen.JvmBackendClassResolverForModuleWithDependencies;
import org.jetbrains.kotlin.codegen.JvmCodegenUtil;
import org.jetbrains.kotlin.codegen.JvmRuntimeTypes;
import org.jetbrains.kotlin.codegen.SamWrapperClasses;
import org.jetbrains.kotlin.codegen.binding.CodegenBinding;
import org.jetbrains.kotlin.codegen.context.CodegenContext;
import org.jetbrains.kotlin.codegen.context.RootContext;
import org.jetbrains.kotlin.codegen.extensions.ClassBuilderInterceptorExtension;
import org.jetbrains.kotlin.codegen.extensions.ClassFileFactoryFinalizerExtension;
import org.jetbrains.kotlin.codegen.inline.GlobalInlineContext;
import org.jetbrains.kotlin.codegen.inline.InlineCache;
import org.jetbrains.kotlin.codegen.intrinsics.IntrinsicMethods;
import org.jetbrains.kotlin.codegen.optimization.OptimizationClassBuilderFactory;
import org.jetbrains.kotlin.codegen.serialization.JvmSerializationBindings;
import org.jetbrains.kotlin.codegen.when.MappingsClassesForWhenByEnum;
import org.jetbrains.kotlin.com.intellij.openapi.project.Project;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.config.ApiVersion;
import org.jetbrains.kotlin.config.CommonConfigurationKeys;
import org.jetbrains.kotlin.config.CompilerConfiguration;
import org.jetbrains.kotlin.config.JVMConfigurationKeys;
import org.jetbrains.kotlin.config.JvmDefaultMode;
import org.jetbrains.kotlin.config.LanguageFeature;
import org.jetbrains.kotlin.config.LanguageVersionSettings;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.descriptors.ModuleDescriptor;
import org.jetbrains.kotlin.descriptors.ScriptDescriptor;
import org.jetbrains.kotlin.diagnostics.DiagnosticReporter;
import org.jetbrains.kotlin.diagnostics.DiagnosticReporterFactory;
import org.jetbrains.kotlin.diagnostics.DiagnosticSink;
import org.jetbrains.kotlin.diagnostics.impl.BaseDiagnosticsCollector;
import org.jetbrains.kotlin.idea.MainFunctionDetector;
import org.jetbrains.kotlin.load.java.components.JavaDeprecationSettings;
import org.jetbrains.kotlin.load.kotlin.incremental.components.IncrementalCache;
import org.jetbrains.kotlin.load.kotlin.incremental.components.IncrementalCompilationComponents;
import org.jetbrains.kotlin.modules.TargetId;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.psi.KtClassOrObject;
import org.jetbrains.kotlin.psi.KtCodeFragment;
import org.jetbrains.kotlin.psi.KtFile;
import org.jetbrains.kotlin.psi.KtScript;
import org.jetbrains.kotlin.resolve.BindingContext;
import org.jetbrains.kotlin.resolve.BindingTrace;
import org.jetbrains.kotlin.resolve.BindingTraceFilter;
import org.jetbrains.kotlin.resolve.DelegatingBindingTrace;
import org.jetbrains.kotlin.resolve.deprecation.DeprecationResolver;
import org.jetbrains.kotlin.resolve.diagnostics.Diagnostics;
import org.jetbrains.kotlin.resolve.diagnostics.OnDemandSuppressCache;
import org.jetbrains.kotlin.resolve.jvm.JvmClassName;
import org.jetbrains.kotlin.resolve.jvm.JvmCompilerDeserializationConfiguration;
import org.jetbrains.kotlin.resolve.jvm.diagnostics.ErrorsJvm;
import org.jetbrains.kotlin.resolve.jvm.diagnostics.JvmDeclarationOrigin;
import org.jetbrains.kotlin.serialization.deserialization.DeserializationConfiguration;
import org.jetbrains.kotlin.storage.LockBasedStorageManager;
import org.jetbrains.kotlin.storage.StorageManager;
import org.jetbrains.kotlin.types.KotlinType;
import org.jetbrains.kotlin.types.SimpleType;
import org.jetbrains.kotlin.types.TypeApproximator;
import org.jetbrains.org.objectweb.asm.Type;

/* JADX INFO: compiled from: GenerationState.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000\u008c\u0003\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\"\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u001e\n\u0002\b\u000b\u0018\u00002\u00020\u0001:\bÖ\u0001×\u0001Ø\u0001Ù\u0001B\u008d\u0001\b\u0002\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\t\u0012\u0006\u0010\n\u001a\u00020\u000b\u0012\u0006\u0010\f\u001a\u00020\r\u0012\b\u0010\u000e\u001a\u0004\u0018\u00010\u000f\u0012\b\u0010\u0010\u001a\u0004\u0018\u00010\u0011\u0012\b\u0010\u0012\u001a\u0004\u0018\u00010\u0013\u0012\u0006\u0010\u0014\u001a\u00020\u0015\u0012\u0006\u0010\u0016\u001a\u00020\u0017\u0012\u0006\u0010\u0018\u001a\u00020\u0019\u0012\u0006\u0010\u001a\u001a\u00020\u0017\u0012\u0006\u0010\u001b\u001a\u00020\u0017\u0012\u0006\u0010\u001c\u001a\u00020\u001d\u0012\u0006\u0010\u001e\u001a\u00020\u0017¢\u0006\u0002\u0010\u001fJ\b\u0010Ç\u0001\u001a\u00030«\u0001J\b\u0010È\u0001\u001a\u00030«\u0001J\b\u0010É\u0001\u001a\u00030«\u0001J\u0010\u0010Ê\u0001\u001a\t\u0012\u0005\u0012\u00030Ë\u00010QH\u0002J\n\u0010Ì\u0001\u001a\u00030«\u0001H\u0002J\u0018\u0010Í\u0001\u001a\u00030«\u00012\u000e\u0010Î\u0001\u001a\t\u0012\u0004\u0012\u00020R0Ï\u0001J%\u0010Ð\u0001\u001a\u00030«\u00012\u0007\u0010Ñ\u0001\u001a\u00020\u00112\b\u0010Ò\u0001\u001a\u00030\u0099\u00012\b\u0010Ó\u0001\u001a\u00030\u009a\u0001J\u0013\u0010Ô\u0001\u001a\u00020\u00172\b\u0010Õ\u0001\u001a\u00030ª\u0001H\u0002R\u0011\u0010 \u001a\u00020\t¢\u0006\b\n\u0000\u001a\u0004\b!\u0010\"R\u0011\u0010#\u001a\u00020$¢\u0006\b\n\u0000\u001a\u0004\b%\u0010&R\u0011\u0010'\u001a\u00020(¢\u0006\b\n\u0000\u001a\u0004\b)\u0010*R\u001c\u0010+\u001a\u0004\u0018\u00010,X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b-\u0010.\"\u0004\b/\u00100R\u0011\u00101\u001a\u000202¢\u0006\b\n\u0000\u001a\u0004\b3\u00104R\u0011\u00105\u001a\u000206¢\u0006\b\n\u0000\u001a\u0004\b7\u00108R\u0011\u0010\n\u001a\u00020\u000b¢\u0006\b\n\u0000\u001a\u0004\b9\u0010:R\u0011\u0010;\u001a\u00020<¢\u0006\b\n\u0000\u001a\u0004\b=\u0010>R\u0011\u0010?\u001a\u00020@¢\u0006\b\n\u0000\u001a\u0004\bA\u0010BR\u0011\u0010\u001c\u001a\u00020\u001d¢\u0006\b\n\u0000\u001a\u0004\bC\u0010DR\u0011\u0010E\u001a\u00020F8F¢\u0006\u0006\u001a\u0004\bG\u0010HR\u0010\u0010I\u001a\u0004\u0018\u00010JX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010K\u001a\u00020$X\u0082\u0004¢\u0006\u0002\n\u0000R\u0011\u0010L\u001a\u00020M¢\u0006\b\n\u0000\u001a\u0004\bN\u0010OR\"\u0010P\u001a\n\u0012\u0004\u0012\u00020R\u0018\u00010QX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bS\u0010T\"\u0004\bU\u0010VR\u0011\u0010\f\u001a\u00020\r¢\u0006\b\n\u0000\u001a\u0004\bW\u0010XR\u0011\u0010Y\u001a\u00020Z¢\u0006\b\n\u0000\u001a\u0004\b[\u0010\\R\u0011\u0010]\u001a\u00020^¢\u0006\b\n\u0000\u001a\u0004\b_\u0010`R\u0011\u0010\u001b\u001a\u00020\u0017¢\u0006\b\n\u0000\u001a\u0004\ba\u0010bR\u0013\u0010c\u001a\u0004\u0018\u00010d¢\u0006\b\n\u0000\u001a\u0004\be\u0010fR\u0011\u0010g\u001a\u00020h¢\u0006\b\n\u0000\u001a\u0004\bi\u0010jR\u000e\u0010k\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u0011\u0010l\u001a\u00020m¢\u0006\b\n\u0000\u001a\u0004\bn\u0010oR\u0011\u0010\u001e\u001a\u00020\u0017¢\u0006\b\n\u0000\u001a\u0004\b\u001e\u0010bR\u0011\u0010\u001a\u001a\u00020\u0017¢\u0006\b\n\u0000\u001a\u0004\b\u001a\u0010bR\u0011\u0010\u0018\u001a\u00020\u0019¢\u0006\b\n\u0000\u001a\u0004\bp\u0010qR\u0011\u0010r\u001a\u00020s8F¢\u0006\u0006\u001a\u0004\bt\u0010uR\u0011\u0010v\u001a\u00020w¢\u0006\b\n\u0000\u001a\u0004\bx\u0010yR\u0011\u0010z\u001a\u00020{¢\u0006\b\n\u0000\u001a\u0004\b|\u0010}R\u0013\u0010~\u001a\u00020\u007f¢\u0006\n\n\u0000\u001a\u0006\b\u0080\u0001\u0010\u0081\u0001R.\u0010\u0082\u0001\u001a\u0011\u0012\u0005\u0012\u00030\u0084\u0001\u0012\u0005\u0012\u00030\u0085\u00010\u0083\u0001X\u0086\u000e¢\u0006\u0012\n\u0000\u001a\u0006\b\u0086\u0001\u0010\u0087\u0001\"\u0006\b\u0088\u0001\u0010\u0089\u0001R\u0015\u0010\u008a\u0001\u001a\u00030\u008b\u0001¢\u0006\n\n\u0000\u001a\u0006\b\u008c\u0001\u0010\u008d\u0001R\u0013\u0010\u0006\u001a\u00020\u0007¢\u0006\n\n\u0000\u001a\u0006\b\u008e\u0001\u0010\u008f\u0001R\u0013\u0010\u0010\u001a\u00020\u0011¢\u0006\n\n\u0000\u001a\u0006\b\u0090\u0001\u0010\u0091\u0001R0\u0010\u0092\u0001\u001a\u0013\u0012\u0005\u0012\u00030\u0084\u0001\u0012\u0007\u0012\u0005\u0018\u00010\u0093\u00010\u0083\u0001X\u0086\u000e¢\u0006\u0012\n\u0000\u001a\u0006\b\u0094\u0001\u0010\u0087\u0001\"\u0006\b\u0095\u0001\u0010\u0089\u0001R/\u0010\u0096\u0001\u001a\u001e\u0012\u0019\u0012\u0017\u0012\u0004\u0012\u00020\u0011\u0012\u0005\u0012\u00030\u0099\u0001\u0012\u0005\u0012\u00030\u009a\u00010\u0098\u00010\u0097\u0001¢\u0006\t\n\u0000\u001a\u0005\b\u009b\u0001\u0010TR\u001a\u0010\u009c\u0001\u001a\t\u0012\u0005\u0012\u00030\u009d\u00010Q¢\u0006\t\n\u0000\u001a\u0005\b\u009e\u0001\u0010TR\u000e\u0010\u0014\u001a\u00020\u0015X\u0082\u0004¢\u0006\u0002\n\u0000R\u0012\u0010\b\u001a\u00020\t¢\u0006\t\n\u0000\u001a\u0005\b\u009f\u0001\u0010\"R\u0015\u0010\u0012\u001a\u0004\u0018\u00010\u0013¢\u0006\n\n\u0000\u001a\u0006\b \u0001\u0010¡\u0001R\u001c\u0010¢\u0001\u001a\n\u0012\u0005\u0012\u00030\u009d\u00010£\u0001¢\u0006\n\n\u0000\u001a\u0006\b¤\u0001\u0010¥\u0001R\u0013\u0010\u0002\u001a\u00020\u0003¢\u0006\n\n\u0000\u001a\u0006\b¦\u0001\u0010§\u0001R:\u0010¨\u0001\u001a\u001d\u0012\u0005\u0012\u00030ª\u0001\u0012\u0004\u0012\u00020\u0011\u0012\u0004\u0012\u00020\u0011\u0012\u0005\u0012\u00030«\u00010©\u0001X\u0086\u000e¢\u0006\u0012\n\u0000\u001a\u0006\b¬\u0001\u0010\u00ad\u0001\"\u0006\b®\u0001\u0010¯\u0001R\u0019\u0010°\u0001\u001a\u0007\u0012\u0002\b\u00030±\u0001¢\u0006\n\n\u0000\u001a\u0006\b²\u0001\u0010³\u0001R\u0015\u0010´\u0001\u001a\u00030µ\u0001¢\u0006\n\n\u0000\u001a\u0006\b¶\u0001\u0010·\u0001R\u0015\u0010¸\u0001\u001a\u00030¹\u0001¢\u0006\n\n\u0000\u001a\u0006\bº\u0001\u0010»\u0001R\u0015\u0010\u000e\u001a\u0004\u0018\u00010\u000f¢\u0006\n\n\u0000\u001a\u0006\b¼\u0001\u0010½\u0001R\u0017\u0010¾\u0001\u001a\u0005\u0018\u00010¿\u0001¢\u0006\n\n\u0000\u001a\u0006\bÀ\u0001\u0010Á\u0001R\u0015\u0010Â\u0001\u001a\u00030Ã\u0001¢\u0006\n\n\u0000\u001a\u0006\bÄ\u0001\u0010Å\u0001R\u000f\u0010Æ\u0001\u001a\u00020\u0017X\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006Ú\u0001"}, d2 = {"Lorg/jetbrains/kotlin/codegen/state/GenerationState;", "", "project", "Lorg/jetbrains/kotlin/com/intellij/openapi/project/Project;", "builderFactory", "Lorg/jetbrains/kotlin/codegen/ClassBuilderFactory;", "module", "Lorg/jetbrains/kotlin/descriptors/ModuleDescriptor;", "originalFrontendBindingContext", "Lorg/jetbrains/kotlin/resolve/BindingContext;", "configuration", "Lorg/jetbrains/kotlin/config/CompilerConfiguration;", "generateDeclaredClassFilter", "Lorg/jetbrains/kotlin/codegen/state/GenerationState$GenerateClassFilter;", "targetId", "Lorg/jetbrains/kotlin/modules/TargetId;", "moduleName", "", "outDirectory", "Ljava/io/File;", "onIndependentPartCompilationEnd", "Lorg/jetbrains/kotlin/codegen/state/GenerationStateEventCallback;", "wantsDiagnostics", "", "jvmBackendClassResolver", "Lorg/jetbrains/kotlin/codegen/JvmBackendClassResolver;", "isIrBackend", "ignoreErrors", "diagnosticReporter", "Lorg/jetbrains/kotlin/diagnostics/DiagnosticReporter;", "isIncrementalCompilation", "(Lcom/intellij/openapi/project/Project;Lorg/jetbrains/kotlin/codegen/ClassBuilderFactory;Lorg/jetbrains/kotlin/descriptors/ModuleDescriptor;Lorg/jetbrains/kotlin/resolve/BindingContext;Lorg/jetbrains/kotlin/config/CompilerConfiguration;Lorg/jetbrains/kotlin/codegen/state/GenerationState$GenerateClassFilter;Lorg/jetbrains/kotlin/modules/TargetId;Ljava/lang/String;Ljava/io/File;Lorg/jetbrains/kotlin/codegen/state/GenerationStateEventCallback;ZLorg/jetbrains/kotlin/codegen/JvmBackendClassResolver;ZZLorg/jetbrains/kotlin/diagnostics/DiagnosticReporter;Z)V", "bindingContext", "getBindingContext", "()Lorg/jetbrains/kotlin/resolve/BindingContext;", "bindingTrace", "Lorg/jetbrains/kotlin/resolve/BindingTrace;", "getBindingTrace", "()Lorg/jetbrains/kotlin/resolve/BindingTrace;", "classBuilderMode", "Lorg/jetbrains/kotlin/codegen/ClassBuilderMode;", "getClassBuilderMode", "()Lorg/jetbrains/kotlin/codegen/ClassBuilderMode;", "codegenFactory", "Lorg/jetbrains/kotlin/codegen/CodegenFactory;", "getCodegenFactory", "()Lorg/jetbrains/kotlin/codegen/CodegenFactory;", "setCodegenFactory", "(Lorg/jetbrains/kotlin/codegen/CodegenFactory;)V", "collectedExtraJvmDiagnostics", "Lorg/jetbrains/kotlin/resolve/diagnostics/Diagnostics;", "getCollectedExtraJvmDiagnostics", "()Lorg/jetbrains/kotlin/resolve/diagnostics/Diagnostics;", "config", "Lorg/jetbrains/kotlin/codegen/state/JvmBackendConfig;", "getConfig", "()Lorg/jetbrains/kotlin/codegen/state/JvmBackendConfig;", "getConfiguration", "()Lorg/jetbrains/kotlin/config/CompilerConfiguration;", "deprecationProvider", "Lorg/jetbrains/kotlin/resolve/deprecation/DeprecationResolver;", "getDeprecationProvider", "()Lorg/jetbrains/kotlin/resolve/deprecation/DeprecationResolver;", "deserializationConfiguration", "Lorg/jetbrains/kotlin/serialization/deserialization/DeserializationConfiguration;", "getDeserializationConfiguration", "()Lorg/jetbrains/kotlin/serialization/deserialization/DeserializationConfiguration;", "getDiagnosticReporter", "()Lorg/jetbrains/kotlin/diagnostics/DiagnosticReporter;", "diagnostics", "Lorg/jetbrains/kotlin/diagnostics/DiagnosticSink;", "getDiagnostics", "()Lorg/jetbrains/kotlin/diagnostics/DiagnosticSink;", "duplicateSignatureFactory", "Lorg/jetbrains/kotlin/codegen/state/BuilderFactoryForDuplicateSignatureDiagnostics;", "extraJvmDiagnosticsTrace", "factory", "Lorg/jetbrains/kotlin/codegen/ClassFileFactory;", "getFactory", "()Lorg/jetbrains/kotlin/codegen/ClassFileFactory;", "files", "", "Lorg/jetbrains/kotlin/psi/KtFile;", "getFiles", "()Ljava/util/List;", "setFiles", "(Ljava/util/List;)V", "getGenerateDeclaredClassFilter", "()Lorg/jetbrains/kotlin/codegen/state/GenerationState$GenerateClassFilter;", "globalInlineContext", "Lorg/jetbrains/kotlin/codegen/inline/GlobalInlineContext;", "getGlobalInlineContext", "()Lorg/jetbrains/kotlin/codegen/inline/GlobalInlineContext;", "globalSerializationBindings", "Lorg/jetbrains/kotlin/codegen/serialization/JvmSerializationBindings;", "getGlobalSerializationBindings", "()Lorg/jetbrains/kotlin/codegen/serialization/JvmSerializationBindings;", "getIgnoreErrors", "()Z", "incrementalCacheForThisTarget", "Lorg/jetbrains/kotlin/load/kotlin/incremental/components/IncrementalCache;", "getIncrementalCacheForThisTarget", "()Lorg/jetbrains/kotlin/load/kotlin/incremental/components/IncrementalCache;", "inlineCache", "Lorg/jetbrains/kotlin/codegen/inline/InlineCache;", "getInlineCache", "()Lorg/jetbrains/kotlin/codegen/inline/InlineCache;", "interceptedBuilderFactory", "intrinsics", "Lorg/jetbrains/kotlin/codegen/intrinsics/IntrinsicMethods;", "getIntrinsics", "()Lorg/jetbrains/kotlin/codegen/intrinsics/IntrinsicMethods;", "getJvmBackendClassResolver", "()Lorg/jetbrains/kotlin/codegen/JvmBackendClassResolver;", "jvmDefaultMode", "Lorg/jetbrains/kotlin/config/JvmDefaultMode;", "getJvmDefaultMode", "()Lorg/jetbrains/kotlin/config/JvmDefaultMode;", "jvmRuntimeTypes", "Lorg/jetbrains/kotlin/codegen/JvmRuntimeTypes;", "getJvmRuntimeTypes", "()Lorg/jetbrains/kotlin/codegen/JvmRuntimeTypes;", "languageVersionSettings", "Lorg/jetbrains/kotlin/config/LanguageVersionSettings;", "getLanguageVersionSettings", "()Lorg/jetbrains/kotlin/config/LanguageVersionSettings;", "mainFunctionDetector", "Lorg/jetbrains/kotlin/idea/MainFunctionDetector;", "getMainFunctionDetector", "()Lorg/jetbrains/kotlin/idea/MainFunctionDetector;", "mapInlineClass", "Lkotlin/Function1;", "Lorg/jetbrains/kotlin/descriptors/ClassDescriptor;", "Lorg/jetbrains/org/objectweb/asm/Type;", "getMapInlineClass", "()Lkotlin/jvm/functions/Function1;", "setMapInlineClass", "(Lkotlin/jvm/functions/Function1;)V", "mappingsClassesForWhenByEnum", "Lorg/jetbrains/kotlin/codegen/when/MappingsClassesForWhenByEnum;", "getMappingsClassesForWhenByEnum", "()Lorg/jetbrains/kotlin/codegen/when/MappingsClassesForWhenByEnum;", "getModule", "()Lorg/jetbrains/kotlin/descriptors/ModuleDescriptor;", "getModuleName", "()Ljava/lang/String;", "multiFieldValueClassUnboxInfo", "Lorg/jetbrains/kotlin/codegen/state/GenerationState$MultiFieldValueClassUnboxInfo;", "getMultiFieldValueClassUnboxInfo", "setMultiFieldValueClassUnboxInfo", "newFragmentCaptureParameters", "", "Lkotlin/Triple;", "Lorg/jetbrains/kotlin/types/KotlinType;", "Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;", "getNewFragmentCaptureParameters", "obsoleteMultifileClasses", "Lorg/jetbrains/kotlin/name/FqName;", "getObsoleteMultifileClasses", "getOriginalFrontendBindingContext", "getOutDirectory", "()Ljava/io/File;", "packagesWithObsoleteParts", "", "getPackagesWithObsoleteParts", "()Ljava/util/Set;", "getProject", "()Lcom/intellij/openapi/project/Project;", "reportDuplicateClassNameError", "Lkotlin/Function3;", "Lorg/jetbrains/kotlin/resolve/jvm/diagnostics/JvmDeclarationOrigin;", "", "getReportDuplicateClassNameError", "()Lkotlin/jvm/functions/Function3;", "setReportDuplicateClassNameError", "(Lkotlin/jvm/functions/Function3;)V", "rootContext", "Lorg/jetbrains/kotlin/codegen/context/CodegenContext;", "getRootContext", "()Lorg/jetbrains/kotlin/codegen/context/CodegenContext;", "samWrapperClasses", "Lorg/jetbrains/kotlin/codegen/SamWrapperClasses;", "getSamWrapperClasses", "()Lorg/jetbrains/kotlin/codegen/SamWrapperClasses;", "scriptSpecific", "Lorg/jetbrains/kotlin/codegen/state/GenerationState$ForScript;", "getScriptSpecific", "()Lorg/jetbrains/kotlin/codegen/state/GenerationState$ForScript;", "getTargetId", "()Lorg/jetbrains/kotlin/modules/TargetId;", "typeApproximator", "Lorg/jetbrains/kotlin/types/TypeApproximator;", "getTypeApproximator", "()Lorg/jetbrains/kotlin/types/TypeApproximator;", "typeMapper", "Lorg/jetbrains/kotlin/codegen/state/KotlinTypeMapper;", "getTypeMapper", "()Lorg/jetbrains/kotlin/codegen/state/KotlinTypeMapper;", "used", "afterIndependentPart", "beforeCompile", "destroy", "loadClassBuilderInterceptors", "Lorg/jetbrains/kotlin/codegen/extensions/ClassBuilderInterceptorExtension;", "markUsed", "oldBEInitTrace", "ktFiles", "", "recordNewFragmentCaptureParameter", "string", ModuleXmlParser.TYPE, "descriptor", "shouldOnlyCollectSignatures", "origin", "Builder", "ForScript", "GenerateClassFilter", "MultiFieldValueClassUnboxInfo", Marker.BACKEND}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class GenerationState {
    private final BindingContext bindingContext;
    private final BindingTrace bindingTrace;
    private final ClassBuilderMode classBuilderMode;
    private CodegenFactory codegenFactory;
    private final Diagnostics collectedExtraJvmDiagnostics;
    private final JvmBackendConfig config;
    private final CompilerConfiguration configuration;
    private final DeprecationResolver deprecationProvider;
    private final DeserializationConfiguration deserializationConfiguration;
    private final DiagnosticReporter diagnosticReporter;
    private BuilderFactoryForDuplicateSignatureDiagnostics duplicateSignatureFactory;
    private final BindingTrace extraJvmDiagnosticsTrace;
    private final ClassFileFactory factory;
    private List<? extends KtFile> files;
    private final GenerateClassFilter generateDeclaredClassFilter;
    private final GlobalInlineContext globalInlineContext;
    private final JvmSerializationBindings globalSerializationBindings;
    private final boolean ignoreErrors;
    private final IncrementalCache incrementalCacheForThisTarget;
    private final InlineCache inlineCache;
    private final ClassBuilderFactory interceptedBuilderFactory;
    private final IntrinsicMethods intrinsics;
    private final boolean isIncrementalCompilation;
    private final boolean isIrBackend;
    private final JvmBackendClassResolver jvmBackendClassResolver;
    private final JvmRuntimeTypes jvmRuntimeTypes;
    private final LanguageVersionSettings languageVersionSettings;
    private final MainFunctionDetector mainFunctionDetector;
    private Function1<? super ClassDescriptor, Type> mapInlineClass;
    private final MappingsClassesForWhenByEnum mappingsClassesForWhenByEnum;
    private final ModuleDescriptor module;
    private final String moduleName;
    private Function1<? super ClassDescriptor, MultiFieldValueClassUnboxInfo> multiFieldValueClassUnboxInfo;
    private final List<Triple<String, KotlinType, DeclarationDescriptor>> newFragmentCaptureParameters;
    private final List<FqName> obsoleteMultifileClasses;
    private final GenerationStateEventCallback onIndependentPartCompilationEnd;
    private final BindingContext originalFrontendBindingContext;
    private final File outDirectory;
    private final Set<FqName> packagesWithObsoleteParts;
    private final Project project;
    private Function3<? super JvmDeclarationOrigin, ? super String, ? super String, Unit> reportDuplicateClassNameError;
    private final CodegenContext<?> rootContext;
    private final SamWrapperClasses samWrapperClasses;
    private final ForScript scriptSpecific;
    private final TargetId targetId;
    private final TypeApproximator typeApproximator;
    private final KotlinTypeMapper typeMapper;
    private boolean used;

    public /* synthetic */ GenerationState(Project project, ClassBuilderFactory classBuilderFactory, ModuleDescriptor moduleDescriptor, BindingContext bindingContext, CompilerConfiguration compilerConfiguration, GenerateClassFilter generateClassFilter, TargetId targetId, String str, File file, GenerationStateEventCallback generationStateEventCallback, boolean z, JvmBackendClassResolver jvmBackendClassResolver, boolean z2, boolean z3, DiagnosticReporter diagnosticReporter, boolean z4, DefaultConstructorMarker defaultConstructorMarker) {
        this(project, classBuilderFactory, moduleDescriptor, bindingContext, compilerConfiguration, generateClassFilter, targetId, str, file, generationStateEventCallback, z, jvmBackendClassResolver, z2, z3, diagnosticReporter, z4);
    }

    /* JADX WARN: Multi-variable type inference failed */
    private GenerationState(Project project, ClassBuilderFactory classBuilderFactory, ModuleDescriptor moduleDescriptor, BindingContext bindingContext, CompilerConfiguration compilerConfiguration, GenerateClassFilter generateClassFilter, TargetId targetId, String str, File file, GenerationStateEventCallback generationStateEventCallback, boolean z, JvmBackendClassResolver jvmBackendClassResolver, boolean z2, boolean z3, DiagnosticReporter diagnosticReporter, boolean z4) {
        String moduleName;
        TargetId targetId2 = targetId;
        this.project = project;
        this.module = moduleDescriptor;
        this.originalFrontendBindingContext = bindingContext;
        this.configuration = compilerConfiguration;
        this.generateDeclaredClassFilter = generateClassFilter;
        this.targetId = targetId2;
        this.outDirectory = file;
        this.onIndependentPartCompilationEnd = generationStateEventCallback;
        this.jvmBackendClassResolver = jvmBackendClassResolver;
        this.isIrBackend = z2;
        this.ignoreErrors = z3;
        this.diagnosticReporter = diagnosticReporter;
        this.isIncrementalCompilation = z4;
        JvmBackendConfig jvmBackendConfig = new JvmBackendConfig(compilerConfiguration);
        this.config = jvmBackendConfig;
        LanguageVersionSettings languageVersionSettings = jvmBackendConfig.getLanguageVersionSettings();
        this.languageVersionSettings = languageVersionSettings;
        this.inlineCache = new InlineCache();
        this.deserializationConfiguration = new JvmCompilerDeserializationConfiguration(languageVersionSettings);
        StorageManager NO_LOCKS = LockBasedStorageManager.NO_LOCKS;
        Intrinsics.checkNotNullExpressionValue(NO_LOCKS, "NO_LOCKS");
        this.deprecationProvider = new DeprecationResolver(NO_LOCKS, languageVersionSettings, JavaDeprecationSettings.INSTANCE);
        IncrementalCompilationComponents incrementalCompilationComponents = (IncrementalCompilationComponents) compilerConfiguration.get(JVMConfigurationKeys.INCREMENTAL_COMPILATION_COMPONENTS);
        if (incrementalCompilationComponents != null) {
            if (targetId2 == null) {
                targetId2 = str != null ? new TargetId(str, ModuleXmlParser.TYPE_PRODUCTION) : null;
                if (targetId2 == null) {
                    throw new IllegalStateException("Target ID should be specified for incremental compilation".toString());
                }
            }
            IncrementalCache incrementalCache = incrementalCompilationComponents.getIncrementalCache(targetId2);
            this.incrementalCacheForThisTarget = incrementalCache;
            Collection<String> obsoletePackageParts = incrementalCache.getObsoletePackageParts();
            ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(obsoletePackageParts, 10));
            Iterator<T> it = obsoletePackageParts.iterator();
            while (it.hasNext()) {
                arrayList.add(JvmClassName.byInternalName((String) it.next()).getPackageFqName());
            }
            this.packagesWithObsoleteParts = CollectionsKt.toSet(arrayList);
            Collection<String> obsoleteMultifileClasses = this.incrementalCacheForThisTarget.getObsoleteMultifileClasses();
            ArrayList arrayList2 = new ArrayList(CollectionsKt.collectionSizeOrDefault(obsoleteMultifileClasses, 10));
            Iterator<T> it2 = obsoleteMultifileClasses.iterator();
            while (it2.hasNext()) {
                arrayList2.add(JvmClassName.byInternalName((String) it2.next()).getFqNameForClassNameWithoutDollars());
            }
            this.obsoleteMultifileClasses = arrayList2;
        } else {
            this.incrementalCacheForThisTarget = null;
            this.packagesWithObsoleteParts = SetsKt.emptySet();
            this.obsoleteMultifileClasses = CollectionsKt.emptyList();
        }
        this.extraJvmDiagnosticsTrace = new DelegatingBindingTrace(this.originalFrontendBindingContext, "For extra diagnostics in " + getClass(), false, null, false, this.isIrBackend ? new OnDemandSuppressCache(this.originalFrontendBindingContext) : null, 24, null);
        this.collectedExtraJvmDiagnostics = new LazyJvmDiagnostics(new Function0<Diagnostics>() { // from class: org.jetbrains.kotlin.codegen.state.GenerationState$collectedExtraJvmDiagnostics$1
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final Diagnostics invoke() {
                BuilderFactoryForDuplicateSignatureDiagnostics builderFactoryForDuplicateSignatureDiagnostics = this.this$0.duplicateSignatureFactory;
                if (builderFactoryForDuplicateSignatureDiagnostics != null) {
                    builderFactoryForDuplicateSignatureDiagnostics.reportDiagnostics();
                }
                Diagnostics diagnostics = this.this$0.extraJvmDiagnosticsTrace.getContext().getDiagnostics();
                Intrinsics.checkNotNullExpressionValue(diagnostics, "getDiagnostics(...)");
                return diagnostics;
            }
        });
        if (str == null) {
            moduleName = JvmCodegenUtil.getModuleName(this.module);
            Intrinsics.checkNotNullExpressionValue(moduleName, "getModuleName(...)");
        } else {
            moduleName = str;
        }
        this.moduleName = moduleName;
        ClassBuilderMode classBuilderMode = classBuilderFactory.getClassBuilderMode();
        Intrinsics.checkNotNullExpressionValue(classBuilderMode, "getClassBuilderMode(...)");
        this.classBuilderMode = classBuilderMode;
        BindingContext bindingContext2 = this.originalFrontendBindingContext;
        BindingTraceFilter.Companion companion = BindingTraceFilter.INSTANCE;
        DelegatingBindingTrace delegatingBindingTrace = new DelegatingBindingTrace(bindingContext2, "trace in GenerationState", false, z ? companion.getACCEPT_ALL() : companion.getNO_DIAGNOSTICS(), false, null, 52, null);
        this.bindingTrace = delegatingBindingTrace;
        BindingContext bindingContext3 = delegatingBindingTrace.getContext();
        Intrinsics.checkNotNullExpressionValue(bindingContext3, "getBindingContext(...)");
        this.bindingContext = bindingContext3;
        this.mainFunctionDetector = new MainFunctionDetector(this.originalFrontendBindingContext, this.languageVersionSettings);
        this.typeMapper = new KotlinTypeMapper(bindingContext3, classBuilderMode, moduleName, this.languageVersionSettings, this.config.getUseOldManglingSchemeForFunctionsWithInlineClassesInSignatures(), this.config.getTarget(), this.isIrBackend, 0 == true ? 1 : 0, null, AndroidBinXmlParser.Chunk.RES_XML_TYPE_RESOURCE_MAP, null);
        ApiVersion apiVersion = this.languageVersionSettings.getApiVersion();
        ApiVersion apiVersion2 = ApiVersion.INSTANCE.parse(KotlinVersion.CURRENT.toString());
        Intrinsics.checkNotNull(apiVersion2);
        this.intrinsics = new IntrinsicMethods(apiVersion.compareTo(apiVersion2) <= 0);
        this.samWrapperClasses = new SamWrapperClasses(this);
        this.globalInlineContext = new GlobalInlineContext();
        this.mappingsClassesForWhenByEnum = new MappingsClassesForWhenByEnum(this);
        this.jvmRuntimeTypes = new JvmRuntimeTypes(this.module, this.languageVersionSettings, this.config.getGenerateOptimizedCallableReferenceSuperClasses());
        this.scriptSpecific = new ForScript();
        this.rootContext = new RootContext(this);
        this.globalSerializationBindings = new JvmSerializationBindings();
        this.mapInlineClass = new Function1<ClassDescriptor, Type>() { // from class: org.jetbrains.kotlin.codegen.state.GenerationState$mapInlineClass$1
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final Type invoke(ClassDescriptor descriptor) {
                Intrinsics.checkNotNullParameter(descriptor, "descriptor");
                KotlinTypeMapper typeMapper = this.this$0.getTypeMapper();
                SimpleType defaultType = descriptor.getDefaultType();
                Intrinsics.checkNotNullExpressionValue(defaultType, "getDefaultType(...)");
                return KotlinTypeMapper.mapType$default(typeMapper, defaultType, null, null, 6, null);
            }
        };
        this.multiFieldValueClassUnboxInfo = new Function1() { // from class: org.jetbrains.kotlin.codegen.state.GenerationState$multiFieldValueClassUnboxInfo$1
            @Override // kotlin.jvm.functions.Function1
            public final Void invoke(ClassDescriptor it3) {
                Intrinsics.checkNotNullParameter(it3, "it");
                return null;
            }
        };
        this.reportDuplicateClassNameError = new Function3<JvmDeclarationOrigin, String, String, Unit>() { // from class: org.jetbrains.kotlin.codegen.state.GenerationState$reportDuplicateClassNameError$1
            {
                super(3);
            }

            @Override // kotlin.jvm.functions.Function3
            public /* bridge */ /* synthetic */ Unit invoke(JvmDeclarationOrigin jvmDeclarationOrigin, String str2, String str3) {
                invoke2(jvmDeclarationOrigin, str2, str3);
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(JvmDeclarationOrigin origin, String internalName, String duplicateClasses) {
                Intrinsics.checkNotNullParameter(origin, "origin");
                Intrinsics.checkNotNullParameter(internalName, "internalName");
                Intrinsics.checkNotNullParameter(duplicateClasses, "duplicateClasses");
                PsiElement element = origin.getElement();
                if (element != null) {
                    this.this$0.getDiagnostics().report(ErrorsJvm.DUPLICATE_CLASS_NAMES.on(element, internalName, duplicateClasses));
                }
            }
        };
        this.typeApproximator = this.languageVersionSettings.supportsFeature(LanguageFeature.NewInference) ? new TypeApproximator(this.module.getBuiltIns(), this.languageVersionSettings) : null;
        ClassBuilderFactory classBuilderFactoryWrapWith = GenerationStateKt.wrapWith(classBuilderFactory, new Function1<ClassBuilderFactory, ClassBuilderFactory>() { // from class: org.jetbrains.kotlin.codegen.state.GenerationState.3
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final ClassBuilderFactory invoke(ClassBuilderFactory it3) {
                Intrinsics.checkNotNullParameter(it3, "it");
                return GenerationState.this.getClassBuilderMode().generateBodies ? new OptimizationClassBuilderFactory(it3, GenerationState.this) : it3;
            }
        }, new Function1<ClassBuilderFactory, ClassBuilderFactory>() { // from class: org.jetbrains.kotlin.codegen.state.GenerationState.4
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final ClassBuilderFactory invoke(ClassBuilderFactory it3) {
                Intrinsics.checkNotNullParameter(it3, "it");
                if (GenerationState.this.getIsIrBackend()) {
                    return it3;
                }
                BindingContext bindingContext4 = GenerationState.this.getBindingContext();
                DiagnosticSink diagnostics = GenerationState.this.getDiagnostics();
                String moduleName2 = GenerationState.this.getModuleName();
                LanguageVersionSettings languageVersionSettings2 = GenerationState.this.getLanguageVersionSettings();
                boolean useOldManglingSchemeForFunctionsWithInlineClassesInSignatures = GenerationState.this.getConfig().getUseOldManglingSchemeForFunctionsWithInlineClassesInSignatures();
                final GenerationState generationState = GenerationState.this;
                BuilderFactoryForDuplicateSignatureDiagnostics builderFactoryForDuplicateSignatureDiagnostics = new BuilderFactoryForDuplicateSignatureDiagnostics(it3, bindingContext4, diagnostics, moduleName2, languageVersionSettings2, useOldManglingSchemeForFunctionsWithInlineClassesInSignatures, new Function1<JvmDeclarationOrigin, Boolean>() { // from class: org.jetbrains.kotlin.codegen.state.GenerationState.4.1
                    {
                        super(1);
                    }

                    @Override // kotlin.jvm.functions.Function1
                    public final Boolean invoke(JvmDeclarationOrigin origin) {
                        Intrinsics.checkNotNullParameter(origin, "origin");
                        return Boolean.valueOf(!generationState.shouldOnlyCollectSignatures(origin));
                    }
                });
                GenerationState.this.duplicateSignatureFactory = builderFactoryForDuplicateSignatureDiagnostics;
                return builderFactoryForDuplicateSignatureDiagnostics;
            }
        }, new Function1<ClassBuilderFactory, ClassBuilderFactory>() { // from class: org.jetbrains.kotlin.codegen.state.GenerationState.5
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final ClassBuilderFactory invoke(ClassBuilderFactory it3) {
                Intrinsics.checkNotNullParameter(it3, "it");
                return new BuilderFactoryForDuplicateClassNameDiagnostics(it3, GenerationState.this);
            }
        }, new Function1<ClassBuilderFactory, ClassBuilderFactory>() { // from class: org.jetbrains.kotlin.codegen.state.GenerationState.6
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final ClassBuilderFactory invoke(ClassBuilderFactory it3) {
                Intrinsics.checkNotNullParameter(it3, "it");
                String str2 = (String) GenerationState.this.getConfiguration().get(JVMConfigurationKeys.DECLARATIONS_JSON_PATH);
                return str2 != null ? new SignatureDumpingBuilderFactory(it3, new File(str2)) : it3;
            }
        });
        Iterator<T> it3 = loadClassBuilderInterceptors().iterator();
        while (it3.hasNext()) {
            classBuilderFactoryWrapWith = ((ClassBuilderInterceptorExtension) it3.next()).interceptClassBuilderFactory(classBuilderFactoryWrapWith, this.originalFrontendBindingContext, getDiagnostics());
        }
        this.interceptedBuilderFactory = classBuilderFactoryWrapWith;
        this.factory = new ClassFileFactory(this, classBuilderFactoryWrapWith, ClassFileFactoryFinalizerExtension.INSTANCE.getInstances(this.project));
        this.newFragmentCaptureParameters = new ArrayList();
    }

    public final Project getProject() {
        return this.project;
    }

    public final ModuleDescriptor getModule() {
        return this.module;
    }

    public final BindingContext getOriginalFrontendBindingContext() {
        return this.originalFrontendBindingContext;
    }

    public final CompilerConfiguration getConfiguration() {
        return this.configuration;
    }

    public final GenerateClassFilter getGenerateDeclaredClassFilter() {
        return this.generateDeclaredClassFilter;
    }

    public final TargetId getTargetId() {
        return this.targetId;
    }

    public final File getOutDirectory() {
        return this.outDirectory;
    }

    public final JvmBackendClassResolver getJvmBackendClassResolver() {
        return this.jvmBackendClassResolver;
    }

    /* JADX INFO: renamed from: isIrBackend, reason: from getter */
    public final boolean getIsIrBackend() {
        return this.isIrBackend;
    }

    public final boolean getIgnoreErrors() {
        return this.ignoreErrors;
    }

    public final DiagnosticReporter getDiagnosticReporter() {
        return this.diagnosticReporter;
    }

    /* JADX INFO: renamed from: isIncrementalCompilation, reason: from getter */
    public final boolean getIsIncrementalCompilation() {
        return this.isIncrementalCompilation;
    }

    /* JADX INFO: compiled from: GenerationState.kt */
    @Metadata(d1 = {"\u0000x\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\b\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001B=\b\u0016\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\t\u0012\f\u0010\n\u001a\b\u0012\u0004\u0012\u00020\f0\u000b\u0012\u0006\u0010\r\u001a\u00020\u000e¢\u0006\u0002\u0010\u000fB-\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\t\u0012\u0006\u0010\r\u001a\u00020\u000e¢\u0006\u0002\u0010\u0010J\u0006\u0010/\u001a\u000200J\u000e\u0010\u0011\u001a\u00020\u00002\u0006\u00101\u001a\u00020\u0012J\u000e\u0010\u0013\u001a\u00020\u00002\u0006\u00101\u001a\u00020\u0014J\u000e\u0010\u0019\u001a\u00020\u00002\u0006\u00101\u001a\u00020\u001aJ\u000e\u0010\u001b\u001a\u00020\u00002\u0006\u00101\u001a\u00020\u001cJ\u000e\u0010\"\u001a\u00020\u00002\u0006\u00101\u001a\u00020\u001cJ\u000e\u0010$\u001a\u00020\u00002\u0006\u00101\u001a\u00020%J\u0010\u0010&\u001a\u00020\u00002\b\u00101\u001a\u0004\u0018\u00010'J\u000e\u0010(\u001a\u00020\u00002\u0006\u00101\u001a\u00020)J\u0010\u0010*\u001a\u00020\u00002\b\u00101\u001a\u0004\u0018\u00010+J\u0010\u0010,\u001a\u00020\u00002\b\u00101\u001a\u0004\u0018\u00010-J\u000e\u0010.\u001a\u00020\u00002\u0006\u00101\u001a\u00020\u001cR\u000e\u0010\b\u001a\u00020\tX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\u0011\u001a\u0004\u0018\u00010\u0012X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\u000eX\u0082\u0004¢\u0006\u0002\n\u0000R\u001c\u0010\u0013\u001a\u0004\u0018\u00010\u0014X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0015\u0010\u0016\"\u0004\b\u0017\u0010\u0018R\u0016\u0010\n\u001a\n\u0012\u0004\u0012\u00020\f\u0018\u00010\u000bX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0019\u001a\u00020\u001aX\u0082\u000e¢\u0006\u0002\n\u0000R\u001a\u0010\u001b\u001a\u00020\u001cX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001d\u0010\u001e\"\u0004\b\u001f\u0010 R\u0011\u0010!\u001a\u00020\u001c¢\u0006\b\n\u0000\u001a\u0004\b!\u0010\u001eR\u001a\u0010\"\u001a\u00020\u001cX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\"\u0010\u001e\"\u0004\b#\u0010 R\u000e\u0010$\u001a\u00020%X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u0010\u0010&\u001a\u0004\u0018\u00010'X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010(\u001a\u00020)X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010*\u001a\u0004\u0018\u00010+X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u0010\u0010,\u001a\u0004\u0018\u00010-X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010.\u001a\u00020\u001cX\u0082\u000e¢\u0006\u0002\n\u0000¨\u00062"}, d2 = {"Lorg/jetbrains/kotlin/codegen/state/GenerationState$Builder;", "", "project", "Lorg/jetbrains/kotlin/com/intellij/openapi/project/Project;", "builderFactory", "Lorg/jetbrains/kotlin/codegen/ClassBuilderFactory;", "module", "Lorg/jetbrains/kotlin/descriptors/ModuleDescriptor;", "bindingContext", "Lorg/jetbrains/kotlin/resolve/BindingContext;", "files", "", "Lorg/jetbrains/kotlin/psi/KtFile;", "configuration", "Lorg/jetbrains/kotlin/config/CompilerConfiguration;", "(Lcom/intellij/openapi/project/Project;Lorg/jetbrains/kotlin/codegen/ClassBuilderFactory;Lorg/jetbrains/kotlin/descriptors/ModuleDescriptor;Lorg/jetbrains/kotlin/resolve/BindingContext;Ljava/util/List;Lorg/jetbrains/kotlin/config/CompilerConfiguration;)V", "(Lcom/intellij/openapi/project/Project;Lorg/jetbrains/kotlin/codegen/ClassBuilderFactory;Lorg/jetbrains/kotlin/descriptors/ModuleDescriptor;Lorg/jetbrains/kotlin/resolve/BindingContext;Lorg/jetbrains/kotlin/config/CompilerConfiguration;)V", "codegenFactory", "Lorg/jetbrains/kotlin/codegen/CodegenFactory;", "diagnosticReporter", "Lorg/jetbrains/kotlin/diagnostics/DiagnosticReporter;", "getDiagnosticReporter", "()Lorg/jetbrains/kotlin/diagnostics/DiagnosticReporter;", "setDiagnosticReporter", "(Lorg/jetbrains/kotlin/diagnostics/DiagnosticReporter;)V", "generateDeclaredClassFilter", "Lorg/jetbrains/kotlin/codegen/state/GenerationState$GenerateClassFilter;", "ignoreErrors", "", "getIgnoreErrors", "()Z", "setIgnoreErrors", "(Z)V", "isIncrementalCompilation", "isIrBackend", "setIrBackend", "jvmBackendClassResolver", "Lorg/jetbrains/kotlin/codegen/JvmBackendClassResolver;", "moduleName", "", "onIndependentPartCompilationEnd", "Lorg/jetbrains/kotlin/codegen/state/GenerationStateEventCallback;", "outDirectory", "Ljava/io/File;", "targetId", "Lorg/jetbrains/kotlin/modules/TargetId;", "wantsDiagnostics", JsonPOJOBuilder.DEFAULT_BUILD_METHOD, "Lorg/jetbrains/kotlin/codegen/state/GenerationState;", RegisterSpec.PREFIX, Marker.BACKEND}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Builder {
        private final BindingContext bindingContext;
        private final ClassBuilderFactory builderFactory;
        private CodegenFactory codegenFactory;
        private final CompilerConfiguration configuration;
        private DiagnosticReporter diagnosticReporter;
        private List<? extends KtFile> files;
        private GenerateClassFilter generateDeclaredClassFilter;
        private boolean ignoreErrors;
        private final boolean isIncrementalCompilation;
        private boolean isIrBackend;
        private JvmBackendClassResolver jvmBackendClassResolver;
        private final ModuleDescriptor module;
        private String moduleName;
        private GenerationStateEventCallback onIndependentPartCompilationEnd;
        private File outDirectory;
        private final Project project;
        private TargetId targetId;
        private boolean wantsDiagnostics;

        public Builder(Project project, ClassBuilderFactory builderFactory, ModuleDescriptor module, BindingContext bindingContext, CompilerConfiguration configuration) {
            Intrinsics.checkNotNullParameter(project, "project");
            Intrinsics.checkNotNullParameter(builderFactory, "builderFactory");
            Intrinsics.checkNotNullParameter(module, "module");
            Intrinsics.checkNotNullParameter(bindingContext, "bindingContext");
            Intrinsics.checkNotNullParameter(configuration, "configuration");
            this.project = project;
            this.builderFactory = builderFactory;
            this.module = module;
            this.bindingContext = bindingContext;
            this.configuration = configuration;
            this.generateDeclaredClassFilter = GenerateClassFilter.GENERATE_ALL;
            this.moduleName = (String) configuration.get(CommonConfigurationKeys.MODULE_NAME);
            this.onIndependentPartCompilationEnd = GenerationStateEventCallback.INSTANCE.getDO_NOTHING();
            this.wantsDiagnostics = true;
            this.jvmBackendClassResolver = new JvmBackendClassResolverForModuleWithDependencies(module);
            this.isIrBackend = configuration.getBoolean(JVMConfigurationKeys.IR);
            this.isIncrementalCompilation = configuration.getBoolean(CommonConfigurationKeys.INCREMENTAL_COMPILATION);
        }

        /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
        public Builder(Project project, ClassBuilderFactory builderFactory, ModuleDescriptor module, BindingContext bindingContext, List<? extends KtFile> files, CompilerConfiguration configuration) {
            this(project, builderFactory, module, bindingContext, configuration);
            Intrinsics.checkNotNullParameter(project, "project");
            Intrinsics.checkNotNullParameter(builderFactory, "builderFactory");
            Intrinsics.checkNotNullParameter(module, "module");
            Intrinsics.checkNotNullParameter(bindingContext, "bindingContext");
            Intrinsics.checkNotNullParameter(files, "files");
            Intrinsics.checkNotNullParameter(configuration, "configuration");
            this.files = files;
        }

        public final Builder generateDeclaredClassFilter(GenerateClassFilter v) {
            Intrinsics.checkNotNullParameter(v, "v");
            this.generateDeclaredClassFilter = v;
            return this;
        }

        public final Builder targetId(TargetId v) {
            this.targetId = v;
            return this;
        }

        public final Builder moduleName(String v) {
            this.moduleName = v;
            return this;
        }

        public final Builder outDirectory(File v) {
            this.outDirectory = v;
            return this;
        }

        public final Builder onIndependentPartCompilationEnd(GenerationStateEventCallback v) {
            Intrinsics.checkNotNullParameter(v, "v");
            this.onIndependentPartCompilationEnd = v;
            return this;
        }

        public final Builder wantsDiagnostics(boolean v) {
            this.wantsDiagnostics = v;
            return this;
        }

        public final Builder jvmBackendClassResolver(JvmBackendClassResolver v) {
            Intrinsics.checkNotNullParameter(v, "v");
            this.jvmBackendClassResolver = v;
            return this;
        }

        /* JADX INFO: renamed from: isIrBackend, reason: from getter */
        public final boolean getIsIrBackend() {
            return this.isIrBackend;
        }

        public final void setIrBackend(boolean z) {
            this.isIrBackend = z;
        }

        public final Builder isIrBackend(boolean v) {
            this.isIrBackend = v;
            return this;
        }

        public final boolean getIgnoreErrors() {
            return this.ignoreErrors;
        }

        public final void setIgnoreErrors(boolean z) {
            this.ignoreErrors = z;
        }

        public final Builder ignoreErrors(boolean v) {
            this.ignoreErrors = v;
            return this;
        }

        public final DiagnosticReporter getDiagnosticReporter() {
            return this.diagnosticReporter;
        }

        public final void setDiagnosticReporter(DiagnosticReporter diagnosticReporter) {
            this.diagnosticReporter = diagnosticReporter;
        }

        public final Builder diagnosticReporter(DiagnosticReporter v) {
            Intrinsics.checkNotNullParameter(v, "v");
            this.diagnosticReporter = v;
            return this;
        }

        /* JADX INFO: renamed from: isIncrementalCompilation, reason: from getter */
        public final boolean getIsIncrementalCompilation() {
            return this.isIncrementalCompilation;
        }

        public final Builder codegenFactory(CodegenFactory v) {
            Intrinsics.checkNotNullParameter(v, "v");
            this.codegenFactory = v;
            return this;
        }

        public final GenerationState build() {
            Project project = this.project;
            ClassBuilderFactory classBuilderFactory = this.builderFactory;
            ModuleDescriptor moduleDescriptor = this.module;
            BindingContext bindingContext = this.bindingContext;
            CompilerConfiguration compilerConfiguration = this.configuration;
            GenerateClassFilter generateClassFilter = this.generateDeclaredClassFilter;
            TargetId targetId = this.targetId;
            String str = this.moduleName;
            File file = this.outDirectory;
            GenerationStateEventCallback generationStateEventCallback = this.onIndependentPartCompilationEnd;
            boolean z = this.wantsDiagnostics;
            JvmBackendClassResolver jvmBackendClassResolver = this.jvmBackendClassResolver;
            boolean z2 = this.isIrBackend;
            boolean z3 = this.ignoreErrors;
            BaseDiagnosticsCollector baseDiagnosticsCollectorCreateReporter$default = this.diagnosticReporter;
            if (baseDiagnosticsCollectorCreateReporter$default == null) {
                baseDiagnosticsCollectorCreateReporter$default = DiagnosticReporterFactory.createReporter$default(DiagnosticReporterFactory.INSTANCE, null, false, 3, null);
            }
            GenerationState generationState = new GenerationState(project, classBuilderFactory, moduleDescriptor, bindingContext, compilerConfiguration, generateClassFilter, targetId, str, file, generationStateEventCallback, z, jvmBackendClassResolver, z2, z3, baseDiagnosticsCollectorCreateReporter$default, this.isIncrementalCompilation, null);
            generationState.setFiles(this.files);
            generationState.setCodegenFactory(this.codegenFactory);
            return generationState;
        }
    }

    /* JADX INFO: compiled from: GenerationState.kt */
    @Metadata(d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\b&\u0018\u0000 \u00112\u00020\u0001:\u0001\u0011B\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H&J\u0010\u0010\u0007\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H&J\u0010\u0010\b\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0016J\u0010\u0010\t\u001a\u00020\u00042\u0006\u0010\n\u001a\u00020\u000bH&J\u0010\u0010\f\u001a\u00020\u00042\u0006\u0010\r\u001a\u00020\u000eH&J\u0010\u0010\u000f\u001a\u00020\u00042\u0006\u0010\n\u001a\u00020\u0010H&¨\u0006\u0012"}, d2 = {"Lorg/jetbrains/kotlin/codegen/state/GenerationState$GenerateClassFilter;", "", "()V", "shouldAnnotateClass", "", "processingClassOrObject", "Lorg/jetbrains/kotlin/psi/KtClassOrObject;", "shouldGenerateClass", "shouldGenerateClassMembers", "shouldGenerateCodeFragment", "script", "Lorg/jetbrains/kotlin/psi/KtCodeFragment;", "shouldGeneratePackagePart", "ktFile", "Lorg/jetbrains/kotlin/psi/KtFile;", "shouldGenerateScript", "Lorg/jetbrains/kotlin/psi/KtScript;", "Companion", Marker.BACKEND}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static abstract class GenerateClassFilter {
        public static final GenerateClassFilter GENERATE_ALL = new GenerateClassFilter() { // from class: org.jetbrains.kotlin.codegen.state.GenerationState$GenerateClassFilter$Companion$GENERATE_ALL$1
            @Override // org.jetbrains.kotlin.codegen.state.GenerationState.GenerateClassFilter
            public boolean shouldAnnotateClass(KtClassOrObject processingClassOrObject) {
                Intrinsics.checkNotNullParameter(processingClassOrObject, "processingClassOrObject");
                return true;
            }

            @Override // org.jetbrains.kotlin.codegen.state.GenerationState.GenerateClassFilter
            public boolean shouldGenerateClass(KtClassOrObject processingClassOrObject) {
                Intrinsics.checkNotNullParameter(processingClassOrObject, "processingClassOrObject");
                return true;
            }

            @Override // org.jetbrains.kotlin.codegen.state.GenerationState.GenerateClassFilter
            public boolean shouldGenerateCodeFragment(KtCodeFragment script) {
                Intrinsics.checkNotNullParameter(script, "script");
                return true;
            }

            @Override // org.jetbrains.kotlin.codegen.state.GenerationState.GenerateClassFilter
            public boolean shouldGeneratePackagePart(KtFile ktFile) {
                Intrinsics.checkNotNullParameter(ktFile, "ktFile");
                return true;
            }

            @Override // org.jetbrains.kotlin.codegen.state.GenerationState.GenerateClassFilter
            public boolean shouldGenerateScript(KtScript script) {
                Intrinsics.checkNotNullParameter(script, "script");
                return true;
            }
        };

        public abstract boolean shouldAnnotateClass(KtClassOrObject processingClassOrObject);

        public abstract boolean shouldGenerateClass(KtClassOrObject processingClassOrObject);

        public abstract boolean shouldGenerateCodeFragment(KtCodeFragment script);

        public abstract boolean shouldGeneratePackagePart(KtFile ktFile);

        public abstract boolean shouldGenerateScript(KtScript script);

        public boolean shouldGenerateClassMembers(KtClassOrObject processingClassOrObject) {
            Intrinsics.checkNotNullParameter(processingClassOrObject, "processingClassOrObject");
            return shouldGenerateClass(processingClassOrObject);
        }
    }

    public final JvmBackendConfig getConfig() {
        return this.config;
    }

    public final LanguageVersionSettings getLanguageVersionSettings() {
        return this.languageVersionSettings;
    }

    public final InlineCache getInlineCache() {
        return this.inlineCache;
    }

    public final IncrementalCache getIncrementalCacheForThisTarget() {
        return this.incrementalCacheForThisTarget;
    }

    public final Set<FqName> getPackagesWithObsoleteParts() {
        return this.packagesWithObsoleteParts;
    }

    public final List<FqName> getObsoleteMultifileClasses() {
        return this.obsoleteMultifileClasses;
    }

    public final DeserializationConfiguration getDeserializationConfiguration() {
        return this.deserializationConfiguration;
    }

    public final DeprecationResolver getDeprecationProvider() {
        return this.deprecationProvider;
    }

    public final List<KtFile> getFiles() {
        return this.files;
    }

    public final void setFiles(List<? extends KtFile> list) {
        this.files = list;
    }

    public final CodegenFactory getCodegenFactory() {
        return this.codegenFactory;
    }

    public final void setCodegenFactory(CodegenFactory codegenFactory) {
        this.codegenFactory = codegenFactory;
    }

    public final DiagnosticSink getDiagnostics() {
        return this.extraJvmDiagnosticsTrace;
    }

    public final Diagnostics getCollectedExtraJvmDiagnostics() {
        return this.collectedExtraJvmDiagnostics;
    }

    public final String getModuleName() {
        return this.moduleName;
    }

    public final ClassBuilderMode getClassBuilderMode() {
        return this.classBuilderMode;
    }

    public final BindingTrace getBindingTrace() {
        return this.bindingTrace;
    }

    public final BindingContext getBindingContext() {
        return this.bindingContext;
    }

    public final MainFunctionDetector getMainFunctionDetector() {
        return this.mainFunctionDetector;
    }

    public final KotlinTypeMapper getTypeMapper() {
        return this.typeMapper;
    }

    public final IntrinsicMethods getIntrinsics() {
        return this.intrinsics;
    }

    public final SamWrapperClasses getSamWrapperClasses() {
        return this.samWrapperClasses;
    }

    public final GlobalInlineContext getGlobalInlineContext() {
        return this.globalInlineContext;
    }

    public final MappingsClassesForWhenByEnum getMappingsClassesForWhenByEnum() {
        return this.mappingsClassesForWhenByEnum;
    }

    public final JvmRuntimeTypes getJvmRuntimeTypes() {
        return this.jvmRuntimeTypes;
    }

    public final ClassFileFactory getFactory() {
        return this.factory;
    }

    public final ForScript getScriptSpecific() {
        return this.scriptSpecific;
    }

    /* JADX INFO: compiled from: GenerationState.kt */
    @Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000e\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002R\"\u0010\u0003\u001a\n\u0012\u0004\u0012\u00020\u0005\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0006\u0010\u0007\"\u0004\b\b\u0010\tR\u001c\u0010\n\u001a\u0004\u0018\u00010\u000bX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\f\u0010\r\"\u0004\b\u000e\u0010\u000fR\u001c\u0010\u0010\u001a\u0004\u0018\u00010\u0011X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0012\u0010\u0013\"\u0004\b\u0014\u0010\u0015¨\u0006\u0016"}, d2 = {"Lorg/jetbrains/kotlin/codegen/state/GenerationState$ForScript;", "", "()V", "earlierScriptsForReplInterpreter", "", "Lorg/jetbrains/kotlin/descriptors/ScriptDescriptor;", "getEarlierScriptsForReplInterpreter", "()Ljava/util/List;", "setEarlierScriptsForReplInterpreter", "(Ljava/util/List;)V", "resultFieldName", "", "getResultFieldName", "()Ljava/lang/String;", "setResultFieldName", "(Ljava/lang/String;)V", "resultType", "Lorg/jetbrains/kotlin/types/KotlinType;", "getResultType", "()Lorg/jetbrains/kotlin/types/KotlinType;", "setResultType", "(Lorg/jetbrains/kotlin/types/KotlinType;)V", Marker.BACKEND}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class ForScript {
        private List<? extends ScriptDescriptor> earlierScriptsForReplInterpreter;
        private String resultFieldName;
        private KotlinType resultType;

        public final List<ScriptDescriptor> getEarlierScriptsForReplInterpreter() {
            return this.earlierScriptsForReplInterpreter;
        }

        public final void setEarlierScriptsForReplInterpreter(List<? extends ScriptDescriptor> list) {
            this.earlierScriptsForReplInterpreter = list;
        }

        public final String getResultFieldName() {
            return this.resultFieldName;
        }

        public final void setResultFieldName(String str) {
            this.resultFieldName = str;
        }

        public final KotlinType getResultType() {
            return this.resultType;
        }

        public final void setResultType(KotlinType kotlinType) {
            this.resultType = kotlinType;
        }
    }

    public final CodegenContext<?> getRootContext() {
        return this.rootContext;
    }

    public final JvmDefaultMode getJvmDefaultMode() {
        return this.config.getJvmDefaultMode();
    }

    public final JvmSerializationBindings getGlobalSerializationBindings() {
        return this.globalSerializationBindings;
    }

    public final Function1<ClassDescriptor, Type> getMapInlineClass() {
        return this.mapInlineClass;
    }

    public final void setMapInlineClass(Function1<? super ClassDescriptor, Type> function1) {
        Intrinsics.checkNotNullParameter(function1, "<set-?>");
        this.mapInlineClass = function1;
    }

    /* JADX INFO: compiled from: GenerationState.kt */
    @Metadata(d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\b\n\u0018\u00002\u00020\u0001B%\u0012\u001e\u0010\u0002\u001a\u001a\u0012\u0016\u0012\u0014\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u00060\u00040\u0003¢\u0006\u0002\u0010\u0007R\u0017\u0010\b\u001a\b\u0012\u0004\u0012\u00020\u00060\u0003¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u0017\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\u00060\u0003¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\nR\u0017\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u00050\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\nR)\u0010\u0002\u001a\u001a\u0012\u0016\u0012\u0014\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u00060\u00040\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\n¨\u0006\u0010"}, d2 = {"Lorg/jetbrains/kotlin/codegen/state/GenerationState$MultiFieldValueClassUnboxInfo;", "", "unboxedTypesAndMethodNamesAndFieldNames", "", "Lkotlin/Triple;", "Lorg/jetbrains/org/objectweb/asm/Type;", "", "(Ljava/util/List;)V", "unboxedFieldNames", "getUnboxedFieldNames", "()Ljava/util/List;", "unboxedMethodNames", "getUnboxedMethodNames", "unboxedTypes", "getUnboxedTypes", "getUnboxedTypesAndMethodNamesAndFieldNames", Marker.BACKEND}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class MultiFieldValueClassUnboxInfo {
        private final List<String> unboxedFieldNames;
        private final List<String> unboxedMethodNames;
        private final List<Type> unboxedTypes;
        private final List<Triple<Type, String, String>> unboxedTypesAndMethodNamesAndFieldNames;

        public MultiFieldValueClassUnboxInfo(List<Triple<Type, String, String>> unboxedTypesAndMethodNamesAndFieldNames) {
            Intrinsics.checkNotNullParameter(unboxedTypesAndMethodNamesAndFieldNames, "unboxedTypesAndMethodNamesAndFieldNames");
            this.unboxedTypesAndMethodNamesAndFieldNames = unboxedTypesAndMethodNamesAndFieldNames;
            List<Triple<Type, String, String>> list = unboxedTypesAndMethodNamesAndFieldNames;
            ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(list, 10));
            Iterator<T> it = list.iterator();
            while (it.hasNext()) {
                arrayList.add((Type) ((Triple) it.next()).component1());
            }
            this.unboxedTypes = arrayList;
            List<Triple<Type, String, String>> list2 = this.unboxedTypesAndMethodNamesAndFieldNames;
            ArrayList arrayList2 = new ArrayList(CollectionsKt.collectionSizeOrDefault(list2, 10));
            Iterator<T> it2 = list2.iterator();
            while (it2.hasNext()) {
                arrayList2.add((String) ((Triple) it2.next()).component2());
            }
            this.unboxedMethodNames = arrayList2;
            List<Triple<Type, String, String>> list3 = this.unboxedTypesAndMethodNamesAndFieldNames;
            ArrayList arrayList3 = new ArrayList(CollectionsKt.collectionSizeOrDefault(list3, 10));
            Iterator<T> it3 = list3.iterator();
            while (it3.hasNext()) {
                arrayList3.add((String) ((Triple) it3.next()).component3());
            }
            this.unboxedFieldNames = arrayList3;
        }

        public final List<Triple<Type, String, String>> getUnboxedTypesAndMethodNamesAndFieldNames() {
            return this.unboxedTypesAndMethodNamesAndFieldNames;
        }

        public final List<Type> getUnboxedTypes() {
            return this.unboxedTypes;
        }

        public final List<String> getUnboxedMethodNames() {
            return this.unboxedMethodNames;
        }

        public final List<String> getUnboxedFieldNames() {
            return this.unboxedFieldNames;
        }
    }

    public final Function1<ClassDescriptor, MultiFieldValueClassUnboxInfo> getMultiFieldValueClassUnboxInfo() {
        return this.multiFieldValueClassUnboxInfo;
    }

    public final void setMultiFieldValueClassUnboxInfo(Function1<? super ClassDescriptor, MultiFieldValueClassUnboxInfo> function1) {
        Intrinsics.checkNotNullParameter(function1, "<set-?>");
        this.multiFieldValueClassUnboxInfo = function1;
    }

    public final Function3<JvmDeclarationOrigin, String, String, Unit> getReportDuplicateClassNameError() {
        return this.reportDuplicateClassNameError;
    }

    public final void setReportDuplicateClassNameError(Function3<? super JvmDeclarationOrigin, ? super String, ? super String, Unit> function3) {
        Intrinsics.checkNotNullParameter(function3, "<set-?>");
        this.reportDuplicateClassNameError = function3;
    }

    public final TypeApproximator getTypeApproximator() {
        return this.typeApproximator;
    }

    private final List<ClassBuilderInterceptorExtension> loadClassBuilderInterceptors() throws IllegalAccessException, InvocationTargetException {
        Object objInvoke = Class.forName("org.jetbrains.kotlin.backend.jvm.extensions.ClassBuilderExtensionAdapter").getDeclaredMethod("getExtensions", Project.class).invoke(null, this.project);
        Intrinsics.checkNotNull(objInvoke, "null cannot be cast to non-null type kotlin.collections.List<org.jetbrains.kotlin.codegen.extensions.ClassBuilderInterceptorExtension>");
        return CollectionsKt.plus((Collection) ClassBuilderInterceptorExtension.INSTANCE.getInstances(this.project), (Iterable) objInvoke);
    }

    public final void beforeCompile() {
        markUsed();
    }

    public final void oldBEInitTrace(Collection<? extends KtFile> ktFiles) {
        Intrinsics.checkNotNullParameter(ktFiles, "ktFiles");
        if (this.isIrBackend) {
            return;
        }
        CodegenBinding.initTrace(this, ktFiles);
    }

    public final void afterIndependentPart() {
        this.onIndependentPartCompilationEnd.invoke(this);
    }

    private final void markUsed() {
        if (this.used) {
            throw new IllegalStateException(GenerationState.class + " cannot be used more than once");
        }
        this.used = true;
    }

    public final void destroy() {
        this.interceptedBuilderFactory.close();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final boolean shouldOnlyCollectSignatures(JvmDeclarationOrigin origin) {
        return Intrinsics.areEqual(this.classBuilderMode, ClassBuilderMode.LIGHT_CLASSES) && GenerationStateKt.doNotGenerateInLightClassMode.contains(origin.getOriginKind());
    }

    public final List<Triple<String, KotlinType, DeclarationDescriptor>> getNewFragmentCaptureParameters() {
        return this.newFragmentCaptureParameters;
    }

    public final void recordNewFragmentCaptureParameter(String string, KotlinType type, DeclarationDescriptor descriptor) {
        Intrinsics.checkNotNullParameter(string, "string");
        Intrinsics.checkNotNullParameter(type, "type");
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        this.newFragmentCaptureParameters.add(new Triple<>(string, type, descriptor));
    }
}
