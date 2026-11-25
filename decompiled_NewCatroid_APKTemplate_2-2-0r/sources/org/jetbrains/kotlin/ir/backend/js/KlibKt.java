package org.jetbrains.kotlin.ir.backend.js;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.comparisons.ComparisonsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt;
import kotlin.text.Charsets;
import kotlin.text.StringsKt;
import org.jetbrains.kotlin.KtIoFileSourceFile;
import org.jetbrains.kotlin.KtPsiSourceFile;
import org.jetbrains.kotlin.KtSourceFile;
import org.jetbrains.kotlin.KtVirtualFileSourceFile;
import org.jetbrains.kotlin.analyzer.AnalysisResult;
import org.jetbrains.kotlin.analyzer.CompilationErrorException;
import org.jetbrains.kotlin.backend.common.extensions.IrGenerationExtension;
import org.jetbrains.kotlin.backend.common.extensions.IrPluginContext;
import org.jetbrains.kotlin.backend.common.extensions.IrPluginContextImpl;
import org.jetbrains.kotlin.backend.common.linkage.partial.PartialLinkageSupportForLinkerImplKt;
import org.jetbrains.kotlin.backend.common.overrides.FakeOverrideChecker;
import org.jetbrains.kotlin.backend.common.serialization.CompatibilityMode;
import org.jetbrains.kotlin.backend.common.serialization.DeclarationTableKt;
import org.jetbrains.kotlin.backend.common.serialization.DeserializationStrategy;
import org.jetbrains.kotlin.backend.common.serialization.FingerprintHash;
import org.jetbrains.kotlin.backend.common.serialization.Hash128Bits;
import org.jetbrains.kotlin.backend.common.serialization.KotlinIrLinker;
import org.jetbrains.kotlin.backend.common.serialization.SerializedIrFileFingerprint;
import org.jetbrains.kotlin.backend.common.serialization.SerializedKlibFingerprint;
import org.jetbrains.kotlin.backend.common.serialization.mangle.ManglerChecker;
import org.jetbrains.kotlin.backend.common.serialization.mangle.descriptor.Ir2DescriptorManglerAdapter;
import org.jetbrains.kotlin.backend.common.serialization.metadata.DynamicTypeDeserializer;
import org.jetbrains.kotlin.backend.common.serialization.metadata.KlibMetadataIncrementalSerializer;
import org.jetbrains.kotlin.backend.common.serialization.metadata.KlibMetadataIncrementalSerializerKt;
import org.jetbrains.kotlin.backend.common.serialization.metadata.KlibMetadataSerializerKt;
import org.jetbrains.kotlin.backend.common.serialization.signature.IdSignatureDescriptor;
import org.jetbrains.kotlin.builtins.KotlinBuiltIns;
import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;
import org.jetbrains.kotlin.com.intellij.openapi.project.Project;
import org.jetbrains.kotlin.com.intellij.openapi.vfs.VfsUtilCore;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiFile;
import org.jetbrains.kotlin.config.CommonConfigurationKeys;
import org.jetbrains.kotlin.config.CommonConfigurationKeysKt;
import org.jetbrains.kotlin.config.CompilerConfiguration;
import org.jetbrains.kotlin.config.KotlinCompilerVersion;
import org.jetbrains.kotlin.config.LanguageVersionSettings;
import org.jetbrains.kotlin.config.LanguageVersionSettingsImpl;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.descriptors.ModuleDescriptor;
import org.jetbrains.kotlin.descriptors.impl.ModuleDescriptorImpl;
import org.jetbrains.kotlin.incremental.components.LookupTracker;
import org.jetbrains.kotlin.incremental.js.IncrementalDataProvider;
import org.jetbrains.kotlin.incremental.js.IncrementalNextRoundChecker;
import org.jetbrains.kotlin.incremental.js.IncrementalResultsConsumer;
import org.jetbrains.kotlin.incremental.js.IrTranslationResultValue;
import org.jetbrains.kotlin.incremental.js.TranslationResultValue;
import org.jetbrains.kotlin.ir.IrBuiltIns;
import org.jetbrains.kotlin.ir.backend.js.lower.serialization.ir.JsIrFileMetadata;
import org.jetbrains.kotlin.ir.backend.js.lower.serialization.ir.JsIrLinker;
import org.jetbrains.kotlin.ir.backend.js.lower.serialization.ir.JsIrModuleSerializer;
import org.jetbrains.kotlin.ir.backend.js.lower.serialization.ir.JsManglerDesc;
import org.jetbrains.kotlin.ir.backend.js.lower.serialization.ir.JsManglerIr;
import org.jetbrains.kotlin.ir.declarations.IrDeclaration;
import org.jetbrains.kotlin.ir.declarations.IrFactory;
import org.jetbrains.kotlin.ir.declarations.IrModuleFragment;
import org.jetbrains.kotlin.ir.descriptors.IrDescriptorBasedFunctionFactory;
import org.jetbrains.kotlin.ir.linkage.IrDeserializer;
import org.jetbrains.kotlin.ir.linkage.partial.PartialLinkageConfigKt;
import org.jetbrains.kotlin.ir.symbols.IrSymbol;
import org.jetbrains.kotlin.ir.util.DeclarationStubGenerator;
import org.jetbrains.kotlin.ir.util.ExternalDependenciesGenerator;
import org.jetbrains.kotlin.ir.util.IrMessageLogger;
import org.jetbrains.kotlin.ir.util.IrMessageLoggerKt;
import org.jetbrains.kotlin.ir.util.KotlinMangler;
import org.jetbrains.kotlin.ir.util.SymbolTable;
import org.jetbrains.kotlin.ir.visitors.IrVisitorsKt;
import org.jetbrains.kotlin.js.config.ErrorTolerancePolicy;
import org.jetbrains.kotlin.js.config.JSConfigurationKeys;
import org.jetbrains.kotlin.konan.properties.PropertiesKt;
import org.jetbrains.kotlin.library.KotlinAbiVersion;
import org.jetbrains.kotlin.library.KotlinLibrary;
import org.jetbrains.kotlin.library.KotlinLibraryKt;
import org.jetbrains.kotlin.library.KotlinLibraryVersioning;
import org.jetbrains.kotlin.library.SerializedIrFile;
import org.jetbrains.kotlin.library.SerializedIrModule;
import org.jetbrains.kotlin.library.SerializedMetadata;
import org.jetbrains.kotlin.library.impl.BuiltInsPlatform;
import org.jetbrains.kotlin.library.impl.KotlinLibraryWriterImplKt;
import org.jetbrains.kotlin.library.metadata.KlibMetadataFactories;
import org.jetbrains.kotlin.library.metadata.KlibMetadataModuleDescriptorFactory;
import org.jetbrains.kotlin.library.metadata.KlibMetadataVersion;
import org.jetbrains.kotlin.metadata.ProtoBuf;
import org.jetbrains.kotlin.progress.IncrementalNextRoundException;
import org.jetbrains.kotlin.psi.KtDeclaration;
import org.jetbrains.kotlin.psi.KtFile;
import org.jetbrains.kotlin.psi2ir.Psi2IrConfiguration;
import org.jetbrains.kotlin.psi2ir.Psi2IrPostprocessingStep;
import org.jetbrains.kotlin.psi2ir.Psi2IrTranslator;
import org.jetbrains.kotlin.psi2ir.descriptors.IrBuiltInsOverDescriptors;
import org.jetbrains.kotlin.psi2ir.generators.GeneratorContext;
import org.jetbrains.kotlin.psi2ir.generators.TypeTranslatorImpl;
import org.jetbrains.kotlin.resolve.BindingContext;
import org.jetbrains.kotlin.resolve.BindingContextUtils;
import org.jetbrains.kotlin.storage.LockBasedStorageManager;
import org.jetbrains.kotlin.storage.StorageManager;
import org.jetbrains.kotlin.util.DummyLogger;
import org.jetbrains.kotlin.util.Logger;
import org.jetbrains.kotlin.utils.DFS;

/* JADX INFO: compiled from: klib.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000Í\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u000b\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0012\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u001e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\"\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010%\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u000b\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u000b\n\u0002\u0018\u0002\n\u0000*\u0001>\u001a\u001e\u00100\u001a\u0002012\u0006\u00102\u001a\u00020\u000b2\u0006\u00103\u001a\u0002042\u0006\u00105\u001a\u00020\n\u001a6\u00106\u001a\u0002072\u0006\u00108\u001a\u0002092\u0006\u0010:\u001a\u00020\u000b2\u0006\u00103\u001a\u0002042\f\u0010;\u001a\b\u0012\u0004\u0012\u00020<0(2\u0006\u00105\u001a\u00020\nH\u0002\u001a\u0015\u0010=\u001a\u00020>2\u0006\u0010?\u001a\u00020@H\u0002¢\u0006\u0002\u0010A\u001aV\u0010B\u001a\u000e\u0012\u0004\u0012\u00020D\u0012\u0004\u0012\u00020\u00180C2\f\u0010E\u001a\b\u0012\u0004\u0012\u00020\u00180F2\u0006\u0010G\u001a\u00020H2\b\u0010I\u001a\u0004\u0018\u00010\u00182\u000e\u0010J\u001a\n\u0012\u0004\u0012\u00020\u0001\u0018\u00010K2\u0012\u0010L\u001a\u000e\u0012\u0004\u0012\u00020\u0018\u0012\u0004\u0012\u00020N0M\u001az\u0010O\u001a\u0002072\u0006\u0010P\u001a\u00020Q2\u0006\u0010R\u001a\u00020\u00012\u0006\u0010S\u001a\u00020\n2\b\b\u0002\u0010T\u001a\u00020U2\b\u0010\u001a\u001a\u0004\u0018\u00010\u00012\f\u0010V\u001a\b\u0012\u0004\u0012\u00020W0(2\u0012\u0010X\u001a\u000e\u0012\u0004\u0012\u00020Z\u0012\u0004\u0012\u00020[0Y2\u0006\u0010\\\u001a\u00020D2\b\b\u0002\u0010]\u001a\u00020^2\u0012\u0010_\u001a\u000e\u0012\u0004\u0012\u00020`\u0012\u0004\u0012\u00020a0M\u001a\u0018\u0010b\u001a\u00020Z2\u0006\u0010c\u001a\u00020d2\u0006\u0010e\u001a\u00020fH\u0002\u001az\u0010g\u001a\u00020h2\u0006\u0010i\u001a\u00020N2\f\u0010E\u001a\b\u0012\u0004\u0012\u00020\u00180F2\u0018\u0010j\u001a\u0014\u0012\u0004\u0012\u00020\u0001\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00010(0C2\u000e\u0010J\u001a\n\u0012\u0004\u0012\u00020\u0001\u0018\u00010K2\u0006\u00102\u001a\u00020\u000b2\u0006\u0010k\u001a\u00020l2\u0006\u0010m\u001a\u00020n2\u0006\u0010o\u001a\u00020\n2\u0012\u0010L\u001a\u000e\u0012\u0004\u0012\u00020\u0018\u0012\u0004\u0012\u00020N0M\u001a\u0088\u0001\u0010p\u001a\u00020h2\u0006\u0010q\u001a\u00020r2\u0006\u00103\u001a\u0002042\u0006\u00102\u001a\u00020\u000b2\f\u0010;\u001a\b\u0012\u0004\u0012\u00020<0(2\f\u0010s\u001a\b\u0012\u0004\u0012\u00020\u00180F2\u0018\u0010j\u001a\u0014\u0012\u0004\u0012\u00020\u0001\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00010(0C2\u0006\u0010k\u001a\u00020l2\u0006\u0010m\u001a\u00020n2\u0006\u0010o\u001a\u00020\n2\u0006\u0010t\u001a\u00020\n2\u0012\u0010L\u001a\u000e\u0012\u0004\u0012\u00020\u0018\u0012\u0004\u0012\u00020N0M\u001a\"\u0010u\u001a\u00020v2\u0006\u0010w\u001a\u00020\u00182\u0012\u0010L\u001a\u000e\u0012\u0004\u0012\u00020\u0001\u0012\u0004\u0012\u00020v0C\u001a:\u0010x\u001a\u00020h2\u0006\u0010P\u001a\u00020Q2\u0006\u0010y\u001a\u00020z2\u0006\u0010t\u001a\u00020\n2\u0010\b\u0002\u0010J\u001a\n\u0012\u0004\u0012\u00020\u0001\u0018\u00010K2\b\b\u0002\u0010o\u001a\u00020\n\u001a(\u0010{\u001a\u00020r2\u0006\u0010P\u001a\u00020Q2\u0006\u0010|\u001a\u00020}2\u0006\u0010k\u001a\u00020l2\u0006\u0010~\u001a\u00020\nH\u0002\u001a»\u0001\u0010\u007f\u001a\u0002072\u0006\u0010!\u001a\u00020\u00012\u0006\u00102\u001a\u00020\u000b2\u0006\u0010m\u001a\u00020n2\f\u0010;\u001a\b\u0012\u0004\u0012\u00020`0(2\u0007\u0010\u0080\u0001\u001a\u00020\u00012\r\u0010\u0081\u0001\u001a\b\u0012\u0004\u0012\u00020\u00180(2\u0006\u0010\\\u001a\u00020D2\u0012\u0010X\u001a\u000e\u0012\u0004\u0012\u00020Z\u0012\u0004\u0012\u00020[0Y2\r\u0010\u0082\u0001\u001a\b\u0012\u0004\u0012\u00020W0(2\u0006\u0010S\u001a\u00020\n2\u0007\u0010\u0083\u0001\u001a\u00020\n2\t\b\u0002\u0010\u0084\u0001\u001a\u00020\n2\u0006\u0010T\u001a\u00020U2\b\u0010\u001a\u001a\u0004\u0018\u00010\u00012\b\b\u0002\u0010]\u001a\u00020^2\u0012\u0010_\u001a\u000e\u0012\u0004\u0012\u00020`\u0012\u0004\u0012\u00020a0M\u001a(\u0010\u0085\u0001\u001a\b\u0012\u0004\u0012\u00020\u00180F2\u0019\u0010\u0086\u0001\u001a\u0014\u0012\u0004\u0012\u00020\u0018\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00180(0C\u001a#\u0010\u0087\u0001\u001a\u000207*\u00020\u000b2\u0006\u0010!\u001a\u00020\u00012\f\u0010;\u001a\b\u0012\u0004\u0012\u00020W0(H\u0002\u001af\u0010\u0088\u0001\u001a\u0010\u0012\u0004\u0012\u00020D\u0012\u0005\u0012\u00030\u008a\u00010\u0089\u0001*\u00020r2\u0006\u00103\u001a\u0002042\f\u0010;\u001a\b\u0012\u0004\u0012\u00020<0(2\u0007\u0010G\u001a\u00030\u008b\u00012\u0006\u0010m\u001a\u00020n2\u0016\b\u0002\u0010X\u001a\u0010\u0012\u0004\u0012\u00020Z\u0012\u0004\u0012\u00020[\u0018\u00010Y2\f\b\u0002\u0010\u008c\u0001\u001a\u0005\u0018\u00010\u008d\u0001\u001a \u0010\u008e\u0001\u001a\b\u0012\u0004\u0012\u00020W0(*\u00020\u00142\r\u0010\u008f\u0001\u001a\b\u0012\u0004\u0012\u00020`0(\u001a(\u0010\u008e\u0001\u001a\b\u0012\u0004\u0012\u00020W0(*\u00020\u00142\r\u0010\u008f\u0001\u001a\b\u0012\u0004\u0012\u00020<0(H\u0007¢\u0006\u0003\b\u0090\u0001\u001a%\u0010\u0091\u0001\u001a\u000e\u0012\u0004\u0012\u00020D\u0012\u0004\u0012\u00020\u00010C*\u000e\u0012\u0004\u0012\u00020D\u0012\u0004\u0012\u00020\u00180CH\u0002\u001a\u0013\u0010\u0092\u0001\u001a\u00020\u0001*\b\u0012\u0004\u0012\u00020)0(H\u0002\u001a\u0013\u0010\u0093\u0001\u001a\b\u0012\u0004\u0012\u00020)0(*\u00020\u0001H\u0002\u001a%\u0010\u0094\u0001\u001a\u00020a*\u0002012\u0007\u0010\u0095\u0001\u001a\u00020`2\u0007\u0010\u0096\u0001\u001a\u00020d2\u0006\u0010i\u001a\u00020N\u001a%\u0010\u0094\u0001\u001a\u00020a*\u0002012\u0007\u0010\u0097\u0001\u001a\u00020<2\u0007\u0010\u0096\u0001\u001a\u00020d2\u0006\u0010i\u001a\u00020N\u001a\u000e\u0010\u0098\u0001\u001a\u0005\u0018\u00010\u0099\u0001*\u00020`\"\u000e\u0010\u0000\u001a\u00020\u0001X\u0082T¢\u0006\u0002\n\u0000\"\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0004\u0010\u0005\"\u000e\u0010\u0006\u001a\u00020\u0001X\u0086T¢\u0006\u0002\n\u0000\"\u000e\u0010\u0007\u001a\u00020\u0001X\u0086T¢\u0006\u0002\n\u0000\"\u000e\u0010\b\u001a\u00020\u0001X\u0086T¢\u0006\u0002\n\u0000\"\u0018\u0010\t\u001a\u00020\n*\u00020\u000b8BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\f\u0010\r\"\u0018\u0010\u000e\u001a\u00020\u000f*\u00020\u00108BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\u0011\u0010\u0012\"\u0017\u0010\u0013\u001a\u0004\u0018\u00010\u0014*\u00020\u000b8F¢\u0006\u0006\u001a\u0004\b\u0015\u0010\u0016\"\u0015\u0010\u0017\u001a\u00020\n*\u00020\u00188F¢\u0006\u0006\u001a\u0004\b\u0017\u0010\u0019\"\u0017\u0010\u001a\u001a\u0004\u0018\u00010\u0001*\u00020\u00188F¢\u0006\u0006\u001a\u0004\b\u001b\u0010\u001c\"\u0018\u0010\u001d\u001a\u00020\u001e*\u00020\u000b8BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\u001f\u0010 \"\u0015\u0010!\u001a\u00020\u0001*\u00020\u00188F¢\u0006\u0006\u001a\u0004\b\"\u0010\u001c\"\u0015\u0010#\u001a\u00020$*\u00020\u000b8F¢\u0006\u0006\u001a\u0004\b%\u0010&\"\u001d\u0010'\u001a\n\u0012\u0004\u0012\u00020)\u0018\u00010(*\u00020\u00188F¢\u0006\u0006\u001a\u0004\b*\u0010+\"\u0017\u0010,\u001a\u0004\u0018\u00010-*\u00020\u00188F¢\u0006\u0006\u001a\u0004\b.\u0010/¨\u0006\u009a\u0001"}, d2 = {"FILE_FINGERPRINTS_SEPARATOR", "", "JsFactories", "Lorg/jetbrains/kotlin/library/metadata/KlibMetadataFactories;", "getJsFactories", "()Lorg/jetbrains/kotlin/library/metadata/KlibMetadataFactories;", "KLIB_PROPERTY_JS_OUTPUT_NAME", "KLIB_PROPERTY_SERIALIZED_IR_FILE_FINGERPRINTS", "KLIB_PROPERTY_SERIALIZED_KLIB_FINGERPRINT", "expectActualLinker", "", "Lorg/jetbrains/kotlin/config/CompilerConfiguration;", "getExpectActualLinker", "(Lorg/jetbrains/kotlin/config/CompilerConfiguration;)Z", "fileMetadata", "", "Lorg/jetbrains/kotlin/library/SerializedIrFile;", "getFileMetadata", "(Lorg/jetbrains/kotlin/library/SerializedIrFile;)[B", "incrementalDataProvider", "Lorg/jetbrains/kotlin/incremental/js/IncrementalDataProvider;", "getIncrementalDataProvider", "(Lorg/jetbrains/kotlin/config/CompilerConfiguration;)Lorg/jetbrains/kotlin/incremental/js/IncrementalDataProvider;", "isBuiltIns", "Lorg/jetbrains/kotlin/library/KotlinLibrary;", "(Lorg/jetbrains/kotlin/library/KotlinLibrary;)Z", KlibKt.KLIB_PROPERTY_JS_OUTPUT_NAME, "getJsOutputName", "(Lorg/jetbrains/kotlin/library/KotlinLibrary;)Ljava/lang/String;", "metadataVersion", "Lorg/jetbrains/kotlin/library/metadata/KlibMetadataVersion;", "getMetadataVersion", "(Lorg/jetbrains/kotlin/config/CompilerConfiguration;)Lorg/jetbrains/kotlin/library/metadata/KlibMetadataVersion;", "moduleName", "getModuleName", "resolverLogger", "Lorg/jetbrains/kotlin/util/Logger;", "getResolverLogger", "(Lorg/jetbrains/kotlin/config/CompilerConfiguration;)Lorg/jetbrains/kotlin/util/Logger;", KlibKt.KLIB_PROPERTY_SERIALIZED_IR_FILE_FINGERPRINTS, "", "Lorg/jetbrains/kotlin/backend/common/serialization/SerializedIrFileFingerprint;", "getSerializedIrFileFingerprints", "(Lorg/jetbrains/kotlin/library/KotlinLibrary;)Ljava/util/List;", KlibKt.KLIB_PROPERTY_SERIALIZED_KLIB_FINGERPRINT, "Lorg/jetbrains/kotlin/backend/common/serialization/SerializedKlibFingerprint;", "getSerializedKlibFingerprint", "(Lorg/jetbrains/kotlin/library/KotlinLibrary;)Lorg/jetbrains/kotlin/backend/common/serialization/Hash128Bits;", "KlibMetadataIncrementalSerializer", "Lorg/jetbrains/kotlin/backend/common/serialization/metadata/KlibMetadataIncrementalSerializer;", "configuration", "project", "Lorg/jetbrains/kotlin/com/intellij/openapi/project/Project;", "allowErrors", "compareMetadataAndGoToNextICRoundIfNeeded", "", "analysisResult", "Lorg/jetbrains/kotlin/analyzer/AnalysisResult;", "config", "files", "Lorg/jetbrains/kotlin/psi/KtFile;", "createBuiltIns", "org/jetbrains/kotlin/ir/backend/js/KlibKt$createBuiltIns$1", "storageManager", "Lorg/jetbrains/kotlin/storage/StorageManager;", "(Lorg/jetbrains/kotlin/storage/StorageManager;)Lorg/jetbrains/kotlin/ir/backend/js/KlibKt$createBuiltIns$1;", "deserializeDependencies", "", "Lorg/jetbrains/kotlin/ir/declarations/IrModuleFragment;", "sortedDependencies", "", "irLinker", "Lorg/jetbrains/kotlin/ir/backend/js/lower/serialization/ir/JsIrLinker;", "mainModuleLib", "filesToLoad", "", "mapping", "Lkotlin/Function1;", "Lorg/jetbrains/kotlin/descriptors/ModuleDescriptor;", "generateKLib", "depsDescriptors", "Lorg/jetbrains/kotlin/ir/backend/js/ModulesStructure;", "outputKlibPath", "nopack", "abiVersion", "Lorg/jetbrains/kotlin/library/KotlinAbiVersion;", "icData", "Lorg/jetbrains/kotlin/ir/backend/js/KotlinFileSerializedData;", "expectDescriptorToSymbol", "", "Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;", "Lorg/jetbrains/kotlin/ir/symbols/IrSymbol;", "moduleFragment", "builtInsPlatform", "Lorg/jetbrains/kotlin/library/impl/BuiltInsPlatform;", "serializeSingleFile", "Lorg/jetbrains/kotlin/KtSourceFile;", "Lorg/jetbrains/kotlin/metadata/ProtoBuf$PackageFragment;", "getDescriptorForElement", "context", "Lorg/jetbrains/kotlin/resolve/BindingContext;", CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD, "Lorg/jetbrains/kotlin/com/intellij/psi/PsiElement;", "getIrModuleInfoForKlib", "Lorg/jetbrains/kotlin/ir/backend/js/IrModuleInfo;", "moduleDescriptor", "friendModules", "symbolTable", "Lorg/jetbrains/kotlin/ir/util/SymbolTable;", "messageLogger", "Lorg/jetbrains/kotlin/ir/util/IrMessageLogger;", "loadFunctionInterfacesIntoStdlib", "getIrModuleInfoForSourceFiles", "psi2IrContext", "Lorg/jetbrains/kotlin/psi2ir/generators/GeneratorContext;", "allSortedDependencies", "verifySignatures", "getModuleDescriptorByLibrary", "Lorg/jetbrains/kotlin/descriptors/impl/ModuleDescriptorImpl;", "current", "loadIr", "irFactory", "Lorg/jetbrains/kotlin/ir/declarations/IrFactory;", "preparePsi2Ir", "errorIgnorancePolicy", "Lorg/jetbrains/kotlin/js/config/ErrorTolerancePolicy;", "partialLinkageEnabled", "serializeModuleIntoKlib", "klibPath", "dependencies", "cleanFiles", "perFile", "containsErrorCode", "sortDependencies", "moduleDependencies", "assertNoExportedNamesClashes", "generateModuleFragmentWithPlugins", "Lkotlin/Pair;", "Lorg/jetbrains/kotlin/backend/common/extensions/IrPluginContext;", "Lorg/jetbrains/kotlin/ir/linkage/IrDeserializer;", "stubGenerator", "Lorg/jetbrains/kotlin/ir/util/DeclarationStubGenerator;", "getSerializedData", "newSources", "getSerializedDataByPsiFiles", "getUniqueNameForEachFragment", "joinIrFileFingerprints", "parseSerializedIrFileFingerprints", "serializeScope", "ktSourceFile", "bindingContext", "ktFile", "toIoFileOrNull", "Ljava/io/File;", "ir.serialization.js"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class KlibKt {
    private static final KlibMetadataFactories JsFactories = new KlibMetadataFactories(KlibKt$JsFactories$1.INSTANCE, DynamicTypeDeserializer.INSTANCE);
    public static final String KLIB_PROPERTY_JS_OUTPUT_NAME = "jsOutputName";
    public static final String KLIB_PROPERTY_SERIALIZED_IR_FILE_FINGERPRINTS = "serializedIrFileFingerprints";
    public static final String KLIB_PROPERTY_SERIALIZED_KLIB_FINGERPRINT = "serializedKlibFingerprint";

    private static final byte[] getFileMetadata(SerializedIrFile serializedIrFile) {
        byte[] backendSpecificMetadata = serializedIrFile.getBackendSpecificMetadata();
        if (backendSpecificMetadata != null) {
            return backendSpecificMetadata;
        }
        throw new IllegalStateException(("Expect file caches to have backendSpecificMetadata, but '" + serializedIrFile.getPath() + "' doesn't").toString());
    }

    public static final String getModuleName(KotlinLibrary kotlinLibrary) {
        Intrinsics.checkNotNullParameter(kotlinLibrary, "<this>");
        String property = kotlinLibrary.getManifestProperties().getProperty(KotlinLibraryKt.KLIB_PROPERTY_UNIQUE_NAME);
        Intrinsics.checkNotNullExpressionValue(property, "getProperty(...)");
        return property;
    }

    public static final boolean isBuiltIns(KotlinLibrary kotlinLibrary) {
        Intrinsics.checkNotNullParameter(kotlinLibrary, "<this>");
        return PropertiesKt.propertyList$default(kotlinLibrary.getManifestProperties(), KotlinLibraryKt.KLIB_PROPERTY_DEPENDS, null, true, 2, null).isEmpty();
    }

    public static final String getJsOutputName(KotlinLibrary kotlinLibrary) {
        Intrinsics.checkNotNullParameter(kotlinLibrary, "<this>");
        return kotlinLibrary.getManifestProperties().getProperty(KLIB_PROPERTY_JS_OUTPUT_NAME);
    }

    public static final List<SerializedIrFileFingerprint> getSerializedIrFileFingerprints(KotlinLibrary kotlinLibrary) {
        Intrinsics.checkNotNullParameter(kotlinLibrary, "<this>");
        String property = kotlinLibrary.getManifestProperties().getProperty(KLIB_PROPERTY_SERIALIZED_IR_FILE_FINGERPRINTS);
        if (property != null) {
            return parseSerializedIrFileFingerprints(property);
        }
        return null;
    }

    public static final Hash128Bits getSerializedKlibFingerprint(KotlinLibrary kotlinLibrary) {
        Intrinsics.checkNotNullParameter(kotlinLibrary, "<this>");
        String property = kotlinLibrary.getManifestProperties().getProperty(KLIB_PROPERTY_SERIALIZED_KLIB_FINGERPRINT);
        if (property == null) {
            return null;
        }
        Hash128Bits hash128BitsM6169fromString68069Vk = SerializedKlibFingerprint.INSTANCE.m6169fromString68069Vk(property);
        SerializedKlibFingerprint serializedKlibFingerprintM6157boximpl = hash128BitsM6169fromString68069Vk != null ? SerializedKlibFingerprint.m6157boximpl(hash128BitsM6169fromString68069Vk) : null;
        if (serializedKlibFingerprintM6157boximpl != null) {
            return serializedKlibFingerprintM6157boximpl.m6166unboximpl();
        }
        return null;
    }

    private static final KlibMetadataVersion getMetadataVersion(CompilerConfiguration compilerConfiguration) {
        Object obj = compilerConfiguration.get(CommonConfigurationKeys.METADATA_VERSION);
        KlibMetadataVersion klibMetadataVersion = obj instanceof KlibMetadataVersion ? (KlibMetadataVersion) obj : null;
        return klibMetadataVersion == null ? KlibMetadataVersion.INSTANCE : klibMetadataVersion;
    }

    private static final boolean getExpectActualLinker(CompilerConfiguration compilerConfiguration) {
        Boolean bool = (Boolean) compilerConfiguration.get(CommonConfigurationKeys.EXPECT_ACTUAL_LINKER);
        if (bool == null) {
            return false;
        }
        return bool.booleanValue();
    }

    public static final Logger getResolverLogger(CompilerConfiguration compilerConfiguration) {
        Intrinsics.checkNotNullParameter(compilerConfiguration, "<this>");
        final IrMessageLogger irMessageLogger = (IrMessageLogger) compilerConfiguration.get(IrMessageLogger.INSTANCE.getIR_MESSAGE_LOGGER());
        if (irMessageLogger == null) {
            return DummyLogger.INSTANCE;
        }
        return new Logger() { // from class: org.jetbrains.kotlin.ir.backend.js.KlibKt$resolverLogger$1
            @Override // org.jetbrains.kotlin.util.Logger
            public void log(String message) {
                Intrinsics.checkNotNullParameter(message, "message");
                irMessageLogger.report(IrMessageLogger.Severity.INFO, message, null);
            }

            @Override // org.jetbrains.kotlin.util.Logger
            /* JADX INFO: renamed from: error */
            public void mo7307error(String message) {
                Intrinsics.checkNotNullParameter(message, "message");
                irMessageLogger.report(IrMessageLogger.Severity.ERROR, message, null);
            }

            @Override // org.jetbrains.kotlin.util.Logger
            public void warning(String message) {
                Intrinsics.checkNotNullParameter(message, "message");
                irMessageLogger.report(IrMessageLogger.Severity.WARNING, message, null);
            }

            @Override // org.jetbrains.kotlin.util.Logger
            public Void fatal(String message) {
                Intrinsics.checkNotNullParameter(message, "message");
                irMessageLogger.report(IrMessageLogger.Severity.ERROR, message, null);
                throw new CompilationErrorException();
            }
        };
    }

    public static final void generateKLib(ModulesStructure depsDescriptors, String outputKlibPath, boolean z, KotlinAbiVersion abiVersion, String str, List<KotlinFileSerializedData> icData, Map<DeclarationDescriptor, IrSymbol> expectDescriptorToSymbol, IrModuleFragment moduleFragment, BuiltInsPlatform builtInsPlatform, Function1<? super KtSourceFile, ProtoBuf.PackageFragment> serializeSingleFile) throws IOException {
        Intrinsics.checkNotNullParameter(depsDescriptors, "depsDescriptors");
        Intrinsics.checkNotNullParameter(outputKlibPath, "outputKlibPath");
        Intrinsics.checkNotNullParameter(abiVersion, "abiVersion");
        Intrinsics.checkNotNullParameter(icData, "icData");
        Intrinsics.checkNotNullParameter(expectDescriptorToSymbol, "expectDescriptorToSymbol");
        Intrinsics.checkNotNullParameter(moduleFragment, "moduleFragment");
        Intrinsics.checkNotNullParameter(builtInsPlatform, "builtInsPlatform");
        Intrinsics.checkNotNullParameter(serializeSingleFile, "serializeSingleFile");
        MainModule mainModule = depsDescriptors.getMainModule();
        Intrinsics.checkNotNull(mainModule, "null cannot be cast to non-null type org.jetbrains.kotlin.ir.backend.js.MainModule.SourceFiles");
        List<KtFile> files = ((MainModule.SourceFiles) mainModule).getFiles();
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(files, 10));
        Iterator<T> it = files.iterator();
        while (it.hasNext()) {
            arrayList.add(new KtPsiSourceFile((PsiFile) it.next()));
        }
        ArrayList arrayList2 = arrayList;
        CompilerConfiguration compilerConfiguration = depsDescriptors.getCompilerConfiguration();
        List<KotlinLibrary> allDependencies = depsDescriptors.getAllDependencies();
        IrMessageLogger irMessageLogger = IrMessageLoggerKt.getIrMessageLogger(compilerConfiguration);
        Object obj = compilerConfiguration.get(CommonConfigurationKeys.MODULE_NAME);
        Intrinsics.checkNotNull(obj);
        serializeModuleIntoKlib((String) obj, compilerConfiguration, irMessageLogger, arrayList2, outputKlibPath, allDependencies, moduleFragment, expectDescriptorToSymbol, icData, z, false, depsDescriptors.getJsFrontEndResult().getHasErrors(), abiVersion, str, builtInsPlatform, serializeSingleFile);
    }

    public static final Collection<KotlinLibrary> sortDependencies(final Map<KotlinLibrary, ? extends List<? extends KotlinLibrary>> moduleDependencies) {
        Intrinsics.checkNotNullParameter(moduleDependencies, "moduleDependencies");
        List list = DFS.topologicalOrder(moduleDependencies.keySet(), new DFS.Neighbors() { // from class: org.jetbrains.kotlin.ir.backend.js.KlibKt$$ExternalSyntheticLambda1
            @Override // org.jetbrains.kotlin.utils.DFS.Neighbors
            public final Iterable getNeighbors(Object obj) {
                return KlibKt.sortDependencies$lambda$1(moduleDependencies, (KotlinLibrary) obj);
            }
        });
        Intrinsics.checkNotNullExpressionValue(list, "topologicalOrder(...)");
        return CollectionsKt.reversed(list);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Iterable sortDependencies$lambda$1(Map moduleDependencies, KotlinLibrary kotlinLibrary) {
        Intrinsics.checkNotNullParameter(moduleDependencies, "$moduleDependencies");
        Intrinsics.checkNotNull(kotlinLibrary);
        return (Iterable) MapsKt.getValue(moduleDependencies, kotlinLibrary);
    }

    public static final Map<IrModuleFragment, KotlinLibrary> deserializeDependencies(Collection<? extends KotlinLibrary> sortedDependencies, JsIrLinker irLinker, KotlinLibrary kotlinLibrary, Set<String> set, Function1<? super KotlinLibrary, ? extends ModuleDescriptor> mapping) {
        IrModuleFragment irModuleFragmentDeserializeIrModuleHeader$default;
        Intrinsics.checkNotNullParameter(sortedDependencies, "sortedDependencies");
        Intrinsics.checkNotNullParameter(irLinker, "irLinker");
        Intrinsics.checkNotNullParameter(mapping, "mapping");
        Collection<? extends KotlinLibrary> collection = sortedDependencies;
        LinkedHashMap linkedHashMap = new LinkedHashMap(RangesKt.coerceAtLeast(MapsKt.mapCapacity(CollectionsKt.collectionSizeOrDefault(collection, 10)), 16));
        for (Object obj : collection) {
            KotlinLibrary kotlinLibrary2 = (KotlinLibrary) obj;
            ModuleDescriptor moduleDescriptorInvoke = mapping.invoke(kotlinLibrary2);
            if (kotlinLibrary == null) {
                irModuleFragmentDeserializeIrModuleHeader$default = KotlinIrLinker.deserializeIrModuleHeader$default(irLinker, moduleDescriptorInvoke, kotlinLibrary2, new Function1<String, DeserializationStrategy>() { // from class: org.jetbrains.kotlin.ir.backend.js.KlibKt$deserializeDependencies$1$1
                    @Override // kotlin.jvm.functions.Function1
                    public final DeserializationStrategy invoke(String it) {
                        Intrinsics.checkNotNullParameter(it, "it");
                        return DeserializationStrategy.EXPLICITLY_EXPORTED;
                    }
                }, null, 8, null);
            } else if (set != null && Intrinsics.areEqual(kotlinLibrary2, kotlinLibrary)) {
                irModuleFragmentDeserializeIrModuleHeader$default = irLinker.deserializeDirtyFiles(moduleDescriptorInvoke, kotlinLibrary2, set);
            } else if (set == null || Intrinsics.areEqual(kotlinLibrary2, kotlinLibrary)) {
                irModuleFragmentDeserializeIrModuleHeader$default = Intrinsics.areEqual(kotlinLibrary2, kotlinLibrary) ? KotlinIrLinker.deserializeIrModuleHeader$default(irLinker, moduleDescriptorInvoke, kotlinLibrary2, new Function1<String, DeserializationStrategy>() { // from class: org.jetbrains.kotlin.ir.backend.js.KlibKt$deserializeDependencies$1$2
                    @Override // kotlin.jvm.functions.Function1
                    public final DeserializationStrategy invoke(String it) {
                        Intrinsics.checkNotNullParameter(it, "it");
                        return DeserializationStrategy.ALL;
                    }
                }, null, 8, null) : KotlinIrLinker.deserializeIrModuleHeader$default(irLinker, moduleDescriptorInvoke, kotlinLibrary2, new Function1<String, DeserializationStrategy>() { // from class: org.jetbrains.kotlin.ir.backend.js.KlibKt$deserializeDependencies$1$3
                    @Override // kotlin.jvm.functions.Function1
                    public final DeserializationStrategy invoke(String it) {
                        Intrinsics.checkNotNullParameter(it, "it");
                        return DeserializationStrategy.EXPLICITLY_EXPORTED;
                    }
                }, null, 8, null);
            } else {
                irModuleFragmentDeserializeIrModuleHeader$default = irLinker.deserializeHeadersWithInlineBodies(moduleDescriptorInvoke, kotlinLibrary2);
            }
            linkedHashMap.put(irModuleFragmentDeserializeIrModuleHeader$default, obj);
        }
        return linkedHashMap;
    }

    public static /* synthetic */ IrModuleInfo loadIr$default(ModulesStructure modulesStructure, IrFactory irFactory, boolean z, Set set, boolean z2, int i, Object obj) {
        if ((i & 8) != 0) {
            set = null;
        }
        if ((i & 16) != 0) {
            z2 = false;
        }
        return loadIr(modulesStructure, irFactory, z, set, z2);
    }

    public static final IrModuleInfo loadIr(final ModulesStructure depsDescriptors, IrFactory irFactory, boolean z, Set<String> set, boolean z2) throws IOException {
        Object next;
        Intrinsics.checkNotNullParameter(depsDescriptors, "depsDescriptors");
        Intrinsics.checkNotNullParameter(irFactory, "irFactory");
        Project project = depsDescriptors.getProject();
        MainModule mainModule = depsDescriptors.getMainModule();
        CompilerConfiguration compilerConfiguration = depsDescriptors.getCompilerConfiguration();
        List<KotlinLibrary> allDependencies = depsDescriptors.getAllDependencies();
        ErrorTolerancePolicy errorTolerancePolicy = (ErrorTolerancePolicy) compilerConfiguration.get(JSConfigurationKeys.ERROR_TOLERANCE_POLICY);
        if (errorTolerancePolicy == null) {
            errorTolerancePolicy = ErrorTolerancePolicy.INSTANCE.getDEFAULT();
        }
        ErrorTolerancePolicy errorTolerancePolicy2 = errorTolerancePolicy;
        Intrinsics.checkNotNull(errorTolerancePolicy2);
        IrMessageLogger irMessageLogger = IrMessageLoggerKt.getIrMessageLogger(compilerConfiguration);
        boolean zIsEnabled = PartialLinkageConfigKt.getPartialLinkageConfig(compilerConfiguration).isEnabled();
        SymbolTable symbolTable = new SymbolTable(new IdSignatureDescriptor(JsManglerDesc.INSTANCE), irFactory, null, 4, null);
        if (mainModule instanceof MainModule.SourceFiles) {
            GeneratorContext generatorContextPreparePsi2Ir = preparePsi2Ir(depsDescriptors, errorTolerancePolicy2, symbolTable, zIsEnabled);
            String strAsString = generatorContextPreparePsi2Ir.getModuleDescriptor().getShortName().asString();
            List<KotlinLibrary> friendDependencies = depsDescriptors.getFriendDependencies();
            ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(friendDependencies, 10));
            Iterator<T> it = friendDependencies.iterator();
            while (it.hasNext()) {
                arrayList.add(KotlinLibraryKt.getUniqueName((KotlinLibrary) it.next()));
            }
            return getIrModuleInfoForSourceFiles(generatorContextPreparePsi2Ir, project, compilerConfiguration, ((MainModule.SourceFiles) mainModule).getFiles(), sortDependencies(depsDescriptors.getModuleDependencies()), MapsKt.mapOf(TuplesKt.to(strAsString, arrayList)), symbolTable, irMessageLogger, z2, z, new Function1<KotlinLibrary, ModuleDescriptor>() { // from class: org.jetbrains.kotlin.ir.backend.js.KlibKt.loadIr.1
                {
                    super(1);
                }

                @Override // kotlin.jvm.functions.Function1
                public final ModuleDescriptor invoke(KotlinLibrary it2) {
                    Intrinsics.checkNotNullParameter(it2, "it");
                    return depsDescriptors.getModuleDescriptor(it2);
                }
            });
        }
        if (mainModule instanceof MainModule.Klib) {
            MainModule.Klib klib = (MainModule.Klib) mainModule;
            String canonicalPath = new File(klib.getLibPath()).getCanonicalPath();
            Iterator<T> it2 = allDependencies.iterator();
            do {
                if (!it2.hasNext()) {
                    next = null;
                    break;
                }
                next = it2.next();
            } while (!Intrinsics.areEqual(((KotlinLibrary) next).getLibraryFile().getCanonicalPath(), canonicalPath));
            KotlinLibrary kotlinLibrary = (KotlinLibrary) next;
            if (kotlinLibrary == null) {
                throw new IllegalStateException(("No module with " + klib.getLibPath() + " found").toString());
            }
            ModuleDescriptor moduleDescriptor = depsDescriptors.getModuleDescriptor(kotlinLibrary);
            Collection<KotlinLibrary> collectionSortDependencies = sortDependencies(depsDescriptors.getModuleDependencies());
            String uniqueName = KotlinLibraryKt.getUniqueName(kotlinLibrary);
            List<KotlinLibrary> friendDependencies2 = depsDescriptors.getFriendDependencies();
            ArrayList arrayList2 = new ArrayList(CollectionsKt.collectionSizeOrDefault(friendDependencies2, 10));
            Iterator<T> it3 = friendDependencies2.iterator();
            while (it3.hasNext()) {
                arrayList2.add(KotlinLibraryKt.getUniqueName((KotlinLibrary) it3.next()));
            }
            return getIrModuleInfoForKlib(moduleDescriptor, collectionSortDependencies, MapsKt.mapOf(TuplesKt.to(uniqueName, arrayList2)), set, compilerConfiguration, symbolTable, irMessageLogger, z2, new Function1<KotlinLibrary, ModuleDescriptor>() { // from class: org.jetbrains.kotlin.ir.backend.js.KlibKt.loadIr.2
                {
                    super(1);
                }

                @Override // kotlin.jvm.functions.Function1
                public final ModuleDescriptor invoke(KotlinLibrary it4) {
                    Intrinsics.checkNotNullParameter(it4, "it");
                    return depsDescriptors.getModuleDescriptor(it4);
                }
            });
        }
        throw new NoWhenBranchMatchedException();
    }

    public static final IrModuleInfo getIrModuleInfoForKlib(ModuleDescriptor moduleDescriptor, Collection<? extends KotlinLibrary> sortedDependencies, Map<String, ? extends List<String>> friendModules, Set<String> set, CompilerConfiguration configuration, SymbolTable symbolTable, IrMessageLogger messageLogger, boolean z, Function1<? super KotlinLibrary, ? extends ModuleDescriptor> mapping) {
        Intrinsics.checkNotNullParameter(moduleDescriptor, "moduleDescriptor");
        Intrinsics.checkNotNullParameter(sortedDependencies, "sortedDependencies");
        Intrinsics.checkNotNullParameter(friendModules, "friendModules");
        Intrinsics.checkNotNullParameter(configuration, "configuration");
        Intrinsics.checkNotNullParameter(symbolTable, "symbolTable");
        Intrinsics.checkNotNullParameter(messageLogger, "messageLogger");
        Intrinsics.checkNotNullParameter(mapping, "mapping");
        KotlinLibrary kotlinLibrary = (KotlinLibrary) CollectionsKt.last(sortedDependencies);
        TypeTranslatorImpl typeTranslatorImpl = new TypeTranslatorImpl(symbolTable, CommonConfigurationKeysKt.getLanguageVersionSettings(configuration), moduleDescriptor, null, false, null, null, false, 248, null);
        IrBuiltInsOverDescriptors irBuiltInsOverDescriptors = new IrBuiltInsOverDescriptors(moduleDescriptor.getBuiltIns(), typeTranslatorImpl, symbolTable);
        ErrorTolerancePolicy errorTolerancePolicy = (ErrorTolerancePolicy) configuration.get(JSConfigurationKeys.ERROR_TOLERANCE_POLICY);
        if (errorTolerancePolicy == null) {
            errorTolerancePolicy = ErrorTolerancePolicy.INSTANCE.getDEFAULT();
        }
        Intrinsics.checkNotNull(errorTolerancePolicy);
        IrBuiltInsOverDescriptors irBuiltInsOverDescriptors2 = irBuiltInsOverDescriptors;
        JsIrLinker jsIrLinker = new JsIrLinker(null, messageLogger, irBuiltInsOverDescriptors2, symbolTable, PartialLinkageSupportForLinkerImplKt.createPartialLinkageSupportForLinker(PartialLinkageConfigKt.getPartialLinkageConfig(configuration), errorTolerancePolicy.getAllowErrors(), irBuiltInsOverDescriptors2, messageLogger), null, null, friendModules, null, 256, null);
        Map<IrModuleFragment, KotlinLibrary> mapDeserializeDependencies = deserializeDependencies(sortedDependencies, jsIrLinker, kotlinLibrary, set, mapping);
        List list = CollectionsKt.toList(mapDeserializeDependencies.keySet());
        irBuiltInsOverDescriptors.setFunctionFactory(new IrDescriptorBasedFunctionFactory(irBuiltInsOverDescriptors, symbolTable, typeTranslatorImpl, z ? new FunctionTypeInterfacePackages().makePackageAccessor((IrModuleFragment) CollectionsKt.first(list)) : null, true));
        IrModuleFragment irModuleFragment = (IrModuleFragment) CollectionsKt.last(list);
        jsIrLinker.init(null, CollectionsKt.emptyList());
        new ExternalDependenciesGenerator(symbolTable, CollectionsKt.listOf(jsIrLinker)).generateUnboundSymbolsAsDependencies();
        jsIrLinker.postProcess(true);
        return new IrModuleInfo(irModuleFragment, list, irBuiltInsOverDescriptors2, symbolTable, jsIrLinker, getUniqueNameForEachFragment(mapDeserializeDependencies));
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static final IrModuleInfo getIrModuleInfoForSourceFiles(GeneratorContext psi2IrContext, Project project, CompilerConfiguration configuration, List<? extends KtFile> files, Collection<? extends KotlinLibrary> allSortedDependencies, Map<String, ? extends List<String>> friendModules, SymbolTable symbolTable, IrMessageLogger messageLogger, boolean z, boolean z2, Function1<? super KotlinLibrary, ? extends ModuleDescriptor> mapping) {
        Intrinsics.checkNotNullParameter(psi2IrContext, "psi2IrContext");
        Intrinsics.checkNotNullParameter(project, "project");
        Intrinsics.checkNotNullParameter(configuration, "configuration");
        Intrinsics.checkNotNullParameter(files, "files");
        Intrinsics.checkNotNullParameter(allSortedDependencies, "allSortedDependencies");
        Intrinsics.checkNotNullParameter(friendModules, "friendModules");
        Intrinsics.checkNotNullParameter(symbolTable, "symbolTable");
        Intrinsics.checkNotNullParameter(messageLogger, "messageLogger");
        Intrinsics.checkNotNullParameter(mapping, "mapping");
        IrBuiltIns irBuiltIns = psi2IrContext.getIrBuiltIns();
        JsIrLinker.JsFePluginContext jsFePluginContext = new JsIrLinker.JsFePluginContext(psi2IrContext.getModuleDescriptor(), symbolTable, psi2IrContext.getTypeTranslator(), irBuiltIns);
        ErrorTolerancePolicy errorTolerancePolicy = (ErrorTolerancePolicy) configuration.get(JSConfigurationKeys.ERROR_TOLERANCE_POLICY);
        if (errorTolerancePolicy == null) {
            errorTolerancePolicy = ErrorTolerancePolicy.INSTANCE.getDEFAULT();
        }
        Intrinsics.checkNotNull(errorTolerancePolicy);
        JsIrLinker jsIrLinker = new JsIrLinker(psi2IrContext.getModuleDescriptor(), messageLogger, irBuiltIns, symbolTable, PartialLinkageSupportForLinkerImplKt.createPartialLinkageSupportForLinker(PartialLinkageConfigKt.getPartialLinkageConfig(configuration), errorTolerancePolicy.getAllowErrors(), irBuiltIns, messageLogger), jsFePluginContext, null, friendModules, null, 256, null);
        Function1 function1 = null;
        Object[] objArr = 0;
        Map<IrModuleFragment, KotlinLibrary> mapDeserializeDependencies = deserializeDependencies(allSortedDependencies, jsIrLinker, null, null, mapping);
        List list = CollectionsKt.toList(mapDeserializeDependencies.keySet());
        Intrinsics.checkNotNull(irBuiltIns, "null cannot be cast to non-null type org.jetbrains.kotlin.psi2ir.descriptors.IrBuiltInsOverDescriptors");
        IrBuiltInsOverDescriptors irBuiltInsOverDescriptors = (IrBuiltInsOverDescriptors) irBuiltIns;
        irBuiltInsOverDescriptors.setFunctionFactory(new IrDescriptorBasedFunctionFactory(irBuiltInsOverDescriptors, symbolTable, psi2IrContext.getTypeTranslator(), z ? new FunctionTypeInterfacePackages().makePackageAccessor((IrModuleFragment) CollectionsKt.first(list)) : null, true));
        IrModuleFragment irModuleFragment = (IrModuleFragment) generateModuleFragmentWithPlugins$default(psi2IrContext, project, files, jsIrLinker, messageLogger, null, null, 48, null).component1();
        ManglerChecker manglerChecker = new ManglerChecker(new KotlinMangler[]{JsManglerIr.INSTANCE, new Ir2DescriptorManglerAdapter(JsManglerDesc.INSTANCE)}, function1, 2, objArr == true ? 1 : 0);
        if (z2) {
            IrVisitorsKt.acceptVoid(irModuleFragment, manglerChecker);
        }
        if (configuration.getBoolean(JSConfigurationKeys.FAKE_OVERRIDE_VALIDATOR)) {
            FakeOverrideChecker fakeOverrideChecker = new FakeOverrideChecker(JsManglerIr.INSTANCE, JsManglerDesc.INSTANCE);
            Iterator<T> it = jsIrLinker.getModules().iterator();
            while (it.hasNext()) {
                fakeOverrideChecker.check((IrModuleFragment) it.next());
            }
        }
        if (z2) {
            Iterator<T> it2 = DeclarationTableKt.getKnownBuiltins(irBuiltIns).iterator();
            while (it2.hasNext()) {
                IrVisitorsKt.acceptVoid((IrDeclaration) it2.next(), manglerChecker);
            }
        }
        return new IrModuleInfo(irModuleFragment, list, irBuiltIns, symbolTable, jsIrLinker, getUniqueNameForEachFragment(mapDeserializeDependencies));
    }

    private static final GeneratorContext preparePsi2Ir(ModulesStructure modulesStructure, ErrorTolerancePolicy errorTolerancePolicy, SymbolTable symbolTable, boolean z) {
        ModulesStructure.JsFrontEndResult jsFrontEndResult = modulesStructure.getJsFrontEndResult();
        return Psi2IrTranslator.createGeneratorContext$default(new Psi2IrTranslator(CommonConfigurationKeysKt.getLanguageVersionSettings(modulesStructure.getCompilerConfiguration()), new Psi2IrConfiguration(errorTolerancePolicy.getAllowErrors(), z, false, 4, null), new KlibKt$preparePsi2Ir$psi2Ir$1(modulesStructure.getCompilerConfiguration())), jsFrontEndResult.getModuleDescriptor(), jsFrontEndResult.getBindingContext(), symbolTable, null, null, 24, null);
    }

    public static /* synthetic */ Pair generateModuleFragmentWithPlugins$default(GeneratorContext generatorContext, Project project, List list, IrDeserializer irDeserializer, IrMessageLogger irMessageLogger, Map map, DeclarationStubGenerator declarationStubGenerator, int i, Object obj) {
        return generateModuleFragmentWithPlugins(generatorContext, project, list, irDeserializer, irMessageLogger, (i & 16) != 0 ? null : map, (i & 32) != 0 ? null : declarationStubGenerator);
    }

    public static final Pair<IrModuleFragment, IrPluginContext> generateModuleFragmentWithPlugins(GeneratorContext generatorContext, Project project, List<? extends KtFile> files, IrDeserializer irLinker, IrMessageLogger messageLogger, Map<DeclarationDescriptor, IrSymbol> map, final DeclarationStubGenerator declarationStubGenerator) {
        Intrinsics.checkNotNullParameter(generatorContext, "<this>");
        Intrinsics.checkNotNullParameter(project, "project");
        Intrinsics.checkNotNullParameter(files, "files");
        Intrinsics.checkNotNullParameter(irLinker, "irLinker");
        Intrinsics.checkNotNullParameter(messageLogger, "messageLogger");
        Psi2IrTranslator psi2IrTranslator = new Psi2IrTranslator(generatorContext.getLanguageVersionSettings(), generatorContext.getConfiguration(), new KlibKt$generateModuleFragmentWithPlugins$psi2Ir$1(messageLogger));
        List<IrGenerationExtension> instances = IrGenerationExtension.INSTANCE.getInstances(project);
        final IrPluginContextImpl irPluginContextImpl = new IrPluginContextImpl(generatorContext.getModuleDescriptor(), generatorContext.getBindingContext(), generatorContext.getLanguageVersionSettings(), generatorContext.getSymbolTable(), generatorContext.getTypeTranslator(), generatorContext.getIrBuiltIns(), irLinker, messageLogger, null, 256, null);
        List<IrGenerationExtension> list = instances;
        if (!list.isEmpty()) {
            for (final IrGenerationExtension irGenerationExtension : instances) {
                psi2IrTranslator.addPostprocessingStep(new Psi2IrPostprocessingStep() { // from class: org.jetbrains.kotlin.ir.backend.js.KlibKt$$ExternalSyntheticLambda0
                    @Override // org.jetbrains.kotlin.psi2ir.Psi2IrPostprocessingStep
                    public final void invoke(IrModuleFragment irModuleFragment) {
                        KlibKt.generateModuleFragmentWithPlugins$lambda$11(declarationStubGenerator, irGenerationExtension, irPluginContextImpl, irModuleFragment);
                    }
                });
            }
        }
        return TuplesKt.to(psi2IrTranslator.generateModuleFragment(generatorContext, files, CollectionsKt.listOf(declarationStubGenerator != null ? declarationStubGenerator : irLinker), list, (32 & 16) != 0 ? null : map, (32 & 32) != 0 ? null : null), irPluginContextImpl);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void generateModuleFragmentWithPlugins$lambda$11(DeclarationStubGenerator declarationStubGenerator, IrGenerationExtension extension, IrPluginContextImpl pluginContext, IrModuleFragment module) {
        Intrinsics.checkNotNullParameter(extension, "$extension");
        Intrinsics.checkNotNullParameter(pluginContext, "$pluginContext");
        Intrinsics.checkNotNullParameter(module, "module");
        Boolean boolValueOf = declarationStubGenerator != null ? Boolean.valueOf(declarationStubGenerator.getUnboundSymbolGeneration()) : null;
        if (declarationStubGenerator != null) {
            try {
                declarationStubGenerator.setUnboundSymbolGeneration(true);
            } finally {
                if (declarationStubGenerator != null) {
                    Intrinsics.checkNotNull(boolValueOf);
                    declarationStubGenerator.setUnboundSymbolGeneration(boolValueOf.booleanValue());
                }
            }
        }
        extension.generate(module, pluginContext);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Type inference failed for: r0v0, types: [org.jetbrains.kotlin.ir.backend.js.KlibKt$createBuiltIns$1] */
    public static final AnonymousClass1 createBuiltIns(StorageManager storageManager) {
        return new KotlinBuiltIns(storageManager) { // from class: org.jetbrains.kotlin.ir.backend.js.KlibKt.createBuiltIns.1
        };
    }

    public static final KlibMetadataFactories getJsFactories() {
        return JsFactories;
    }

    public static final ModuleDescriptorImpl getModuleDescriptorByLibrary(KotlinLibrary current, Map<String, ModuleDescriptorImpl> mapping) {
        Intrinsics.checkNotNullParameter(current, "current");
        Intrinsics.checkNotNullParameter(mapping, "mapping");
        KlibMetadataModuleDescriptorFactory defaultDeserializedDescriptorFactory = JsFactories.getDefaultDeserializedDescriptorFactory();
        LanguageVersionSettingsImpl languageVersionSettingsImpl = LanguageVersionSettingsImpl.DEFAULT;
        StorageManager NO_LOCKS = LockBasedStorageManager.NO_LOCKS;
        Intrinsics.checkNotNullExpressionValue(NO_LOCKS, "NO_LOCKS");
        ModuleDescriptorImpl moduleDescriptorImplCreateDescriptorOptionalBuiltIns = defaultDeserializedDescriptorFactory.createDescriptorOptionalBuiltIns(current, languageVersionSettingsImpl, NO_LOCKS, null, null, LookupTracker.DO_NOTHING.INSTANCE);
        List listPropertyList$default = PropertiesKt.propertyList$default(current.getManifestProperties(), KotlinLibraryKt.KLIB_PROPERTY_DEPENDS, null, true, 2, null);
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(listPropertyList$default, 10));
        Iterator it = listPropertyList$default.iterator();
        while (it.hasNext()) {
            arrayList.add((ModuleDescriptorImpl) MapsKt.getValue(mapping, (String) it.next()));
        }
        moduleDescriptorImplCreateDescriptorOptionalBuiltIns.setDependencies(CollectionsKt.plus((Collection) CollectionsKt.listOf(moduleDescriptorImplCreateDescriptorOptionalBuiltIns), (Iterable) arrayList));
        return moduleDescriptorImplCreateDescriptorOptionalBuiltIns;
    }

    private static final DeclarationDescriptor getDescriptorForElement(BindingContext bindingContext, PsiElement psiElement) {
        Object notNull = BindingContextUtils.getNotNull(bindingContext, BindingContext.DECLARATION_TO_DESCRIPTOR, psiElement);
        Intrinsics.checkNotNullExpressionValue(notNull, "getNotNull(...)");
        return (DeclarationDescriptor) notNull;
    }

    private static final String joinIrFileFingerprints(List<SerializedIrFileFingerprint> list) {
        return CollectionsKt.joinToString$default(list, " ", null, null, 0, null, null, 62, null);
    }

    private static final List<SerializedIrFileFingerprint> parseSerializedIrFileFingerprints(String str) {
        List listSplit$default = StringsKt.split$default((CharSequence) str, new String[]{" "}, false, 0, 6, (Object) null);
        SerializedIrFileFingerprint.Companion companion = SerializedIrFileFingerprint.INSTANCE;
        ArrayList arrayList = new ArrayList();
        Iterator it = listSplit$default.iterator();
        while (it.hasNext()) {
            Hash128Bits hash128BitsM6156fromString8eX_dPE = companion.m6156fromString8eX_dPE((String) it.next());
            SerializedIrFileFingerprint serializedIrFileFingerprintM6142boximpl = hash128BitsM6156fromString8eX_dPE != null ? SerializedIrFileFingerprint.m6142boximpl(hash128BitsM6156fromString8eX_dPE) : null;
            if (serializedIrFileFingerprintM6142boximpl != null) {
                arrayList.add(serializedIrFileFingerprintM6142boximpl);
            }
        }
        return arrayList;
    }

    public static final void serializeModuleIntoKlib(String moduleName, CompilerConfiguration configuration, IrMessageLogger messageLogger, List<? extends KtSourceFile> files, String klibPath, List<? extends KotlinLibrary> dependencies, IrModuleFragment moduleFragment, Map<DeclarationDescriptor, IrSymbol> expectDescriptorToSymbol, List<KotlinFileSerializedData> cleanFiles, boolean z, boolean z2, boolean z3, KotlinAbiVersion abiVersion, String str, BuiltInsPlatform builtInsPlatform, Function1<? super KtSourceFile, ProtoBuf.PackageFragment> serializeSingleFile) throws IOException {
        Intrinsics.checkNotNullParameter(moduleName, "moduleName");
        Intrinsics.checkNotNullParameter(configuration, "configuration");
        Intrinsics.checkNotNullParameter(messageLogger, "messageLogger");
        Intrinsics.checkNotNullParameter(files, "files");
        Intrinsics.checkNotNullParameter(klibPath, "klibPath");
        Intrinsics.checkNotNullParameter(dependencies, "dependencies");
        Intrinsics.checkNotNullParameter(moduleFragment, "moduleFragment");
        Intrinsics.checkNotNullParameter(expectDescriptorToSymbol, "expectDescriptorToSymbol");
        Intrinsics.checkNotNullParameter(cleanFiles, "cleanFiles");
        Intrinsics.checkNotNullParameter(abiVersion, "abiVersion");
        Intrinsics.checkNotNullParameter(builtInsPlatform, "builtInsPlatform");
        Intrinsics.checkNotNullParameter(serializeSingleFile, "serializeSingleFile");
        files.size();
        moduleFragment.getFiles().size();
        CompatibilityMode compatibilityMode = new CompatibilityMode(abiVersion);
        List listEmptyList = (Collection) configuration.get(CommonConfigurationKeys.KLIB_RELATIVE_PATH_BASES);
        if (listEmptyList == null) {
            listEmptyList = CollectionsKt.emptyList();
        }
        Collection collection = listEmptyList;
        Boolean bool = (Boolean) configuration.get(CommonConfigurationKeys.KLIB_NORMALIZE_ABSOLUTE_PATH);
        if (bool == null) {
            bool = false;
        }
        boolean zBooleanValue = bool.booleanValue();
        Boolean bool2 = (Boolean) configuration.get(CommonConfigurationKeys.PRODUCE_KLIB_SIGNATURES_CLASH_CHECKS);
        if (bool2 == null) {
            bool2 = false;
        }
        SerializedIrModule serializedIrModule = new JsIrModuleSerializer(messageLogger, moduleFragment.getIrBuiltins(), expectDescriptorToSymbol, compatibilityMode, !getExpectActualLinker(configuration), zBooleanValue, collection, CommonConfigurationKeysKt.getLanguageVersionSettings(configuration), bool2.booleanValue()).serializedIrModule(moduleFragment);
        ModuleDescriptor descriptor = moduleFragment.getDescriptor();
        IncrementalResultsConsumer incrementalResultsConsumer = (IncrementalResultsConsumer) configuration.get(JSConfigurationKeys.INCREMENTAL_RESULTS_CONSUMER);
        byte[] bArr = new byte[0];
        ArrayList arrayList = new ArrayList();
        Iterator it = CollectionsKt.zip(files, serializedIrModule.getFiles()).iterator();
        while (it.hasNext()) {
            Pair pair = (Pair) it.next();
            KtSourceFile ktSourceFile = (KtSourceFile) pair.component1();
            SerializedIrFile serializedIrFile = (SerializedIrFile) pair.component2();
            Intrinsics.areEqual(ktSourceFile.getPath(), serializedIrFile.getPath());
            byte[] byteArray = serializeSingleFile.invoke(ktSourceFile).toByteArray();
            Iterator it2 = it;
            Intrinsics.checkNotNullExpressionValue(byteArray, "toByteArray(...)");
            KotlinFileSerializedData kotlinFileSerializedData = new KotlinFileSerializedData(byteArray, serializedIrFile);
            arrayList.add(kotlinFileSerializedData);
            File ioFileOrNull = toIoFileOrNull(ktSourceFile);
            Intrinsics.checkNotNull(ioFileOrNull);
            serializeModuleIntoKlib$processCompiledFileData(incrementalResultsConsumer, bArr, ioFileOrNull, kotlinFileSerializedData);
            it = it2;
        }
        List listPlus = CollectionsKt.plus((Collection) cleanFiles, (Iterable) arrayList);
        if (builtInsPlatform == BuiltInsPlatform.JS) {
            assertNoExportedNamesClashes(configuration, moduleName, listPlus);
        }
        LanguageVersionSettings languageVersionSettings = CommonConfigurationKeysKt.getLanguageVersionSettings(configuration);
        List list = listPlus;
        ArrayList arrayList2 = new ArrayList(CollectionsKt.collectionSizeOrDefault(list, 10));
        Iterator it3 = list.iterator();
        while (it3.hasNext()) {
            arrayList2.add(((KotlinFileSerializedData) it3.next()).getIrData().getFqName());
        }
        byte[] byteArray2 = KlibMetadataSerializerKt.serializeKlibHeader(languageVersionSettings, descriptor, (List<String>) CollectionsKt.sorted(CollectionsKt.distinct(arrayList2)), (List<String>) CollectionsKt.emptyList()).toByteArray();
        if (incrementalResultsConsumer != null) {
            Intrinsics.checkNotNull(byteArray2);
            incrementalResultsConsumer.processHeader(byteArray2);
        }
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        for (Object obj : list) {
            String fqName = ((KotlinFileSerializedData) obj).getIrData().getFqName();
            Object obj2 = linkedHashMap.get(fqName);
            if (obj2 == null) {
                obj2 = (List) new ArrayList();
                linkedHashMap.put(fqName, obj2);
            }
            ((List) obj2).add(obj);
        }
        ArrayList arrayList3 = new ArrayList(linkedHashMap.size());
        for (Map.Entry entry : linkedHashMap.entrySet()) {
            String str2 = (String) entry.getKey();
            List listSortedWith = CollectionsKt.sortedWith((List) entry.getValue(), new Comparator() { // from class: org.jetbrains.kotlin.ir.backend.js.KlibKt$serializeModuleIntoKlib$lambda$29$$inlined$sortedBy$1
                /* JADX WARN: Multi-variable type inference failed */
                @Override // java.util.Comparator
                public final int compare(T t, T t2) {
                    return ComparisonsKt.compareValues(((KotlinFileSerializedData) t).getIrData().getPath(), ((KotlinFileSerializedData) t2).getIrData().getPath());
                }
            });
            ArrayList arrayList4 = new ArrayList(CollectionsKt.collectionSizeOrDefault(listSortedWith, 10));
            Iterator it4 = listSortedWith.iterator();
            while (it4.hasNext()) {
                arrayList4.add(((KotlinFileSerializedData) it4.next()).getMetadata());
            }
            arrayList3.add(TuplesKt.to(str2, arrayList4));
        }
        Map map = MapsKt.toMap(arrayList3);
        Intrinsics.checkNotNull(byteArray2);
        SerializedMetadata serializedMetadataMakeSerializedKlibMetadata = KlibMetadataIncrementalSerializerKt.makeSerializedKlibMetadata(map, byteArray2);
        ArrayList arrayList5 = new ArrayList(CollectionsKt.collectionSizeOrDefault(list, 10));
        Iterator it5 = list.iterator();
        while (it5.hasNext()) {
            arrayList5.add(((KotlinFileSerializedData) it5.next()).getIrData());
        }
        SerializedIrModule serializedIrModule2 = new SerializedIrModule(arrayList5);
        KotlinLibraryVersioning kotlinLibraryVersioning = new KotlinLibraryVersioning(null, KotlinCompilerVersion.VERSION, compatibilityMode.getAbiVersion(), KlibMetadataVersion.INSTANCE.toString(), null, 16, null);
        Properties properties = new Properties();
        if (str != null) {
            properties.setProperty(KLIB_PROPERTY_JS_OUTPUT_NAME, str);
        }
        if (z3) {
            properties.setProperty(KotlinLibraryKt.KLIB_PROPERTY_CONTAINS_ERROR_CODE, "true");
        }
        List listSortedWith2 = CollectionsKt.sortedWith(serializedIrModule2.getFiles(), new Comparator() { // from class: org.jetbrains.kotlin.ir.backend.js.KlibKt$serializeModuleIntoKlib$lambda$33$$inlined$sortedBy$1
            /* JADX WARN: Multi-variable type inference failed */
            @Override // java.util.Comparator
            public final int compare(T t, T t2) {
                return ComparisonsKt.compareValues(((SerializedIrFile) t).getPath(), ((SerializedIrFile) t2).getPath());
            }
        });
        ArrayList arrayList6 = new ArrayList(CollectionsKt.collectionSizeOrDefault(listSortedWith2, 10));
        Iterator it6 = listSortedWith2.iterator();
        while (it6.hasNext()) {
            arrayList6.add(SerializedIrFileFingerprint.m6142boximpl(SerializedIrFileFingerprint.m6145constructorimpl((SerializedIrFile) it6.next())));
        }
        ArrayList arrayList7 = arrayList6;
        properties.setProperty(KLIB_PROPERTY_SERIALIZED_IR_FILE_FINGERPRINTS, joinIrFileFingerprints(arrayList7));
        properties.setProperty(KLIB_PROPERTY_SERIALIZED_KLIB_FINGERPRINT, FingerprintHash.m6129toStringimpl(SerializedKlibFingerprint.m6159constructorimpl(arrayList7)));
        KotlinLibraryWriterImplKt.buildKotlinLibrary(dependencies, serializedMetadataMakeSerializedKlibMetadata, serializedIrModule2, kotlinLibraryVersioning, klibPath, moduleName, z, z2, properties, null, builtInsPlatform, (2048 & 2048) != 0 ? CollectionsKt.emptyList() : null);
    }

    private static final void serializeModuleIntoKlib$processCompiledFileData(IncrementalResultsConsumer incrementalResultsConsumer, byte[] bArr, File file, KotlinFileSerializedData kotlinFileSerializedData) {
        if (incrementalResultsConsumer != null) {
            incrementalResultsConsumer.processPackagePart(file, kotlinFileSerializedData.getMetadata(), bArr, bArr);
            SerializedIrFile irData = kotlinFileSerializedData.getIrData();
            byte[] fileData = irData.getFileData();
            byte[] types = irData.getTypes();
            byte[] signatures = irData.getSignatures();
            byte[] strings = irData.getStrings();
            byte[] declarations = irData.getDeclarations();
            byte[] bodies = irData.getBodies();
            byte[] bytes = irData.getFqName().getBytes(Charsets.UTF_8);
            Intrinsics.checkNotNullExpressionValue(bytes, "getBytes(...)");
            incrementalResultsConsumer.processIrFile(file, fileData, types, signatures, strings, declarations, bodies, bytes, getFileMetadata(irData), irData.getDebugInfo());
        }
    }

    public static final ProtoBuf.PackageFragment serializeScope(KlibMetadataIncrementalSerializer klibMetadataIncrementalSerializer, KtFile ktFile, BindingContext bindingContext, ModuleDescriptor moduleDescriptor) {
        Intrinsics.checkNotNullParameter(klibMetadataIncrementalSerializer, "<this>");
        Intrinsics.checkNotNullParameter(ktFile, "ktFile");
        Intrinsics.checkNotNullParameter(bindingContext, "bindingContext");
        Intrinsics.checkNotNullParameter(moduleDescriptor, "moduleDescriptor");
        List<KtDeclaration> declarations = ktFile.getDeclarations();
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(declarations, 10));
        Iterator<T> it = declarations.iterator();
        while (it.hasNext()) {
            arrayList.add(getDescriptorForElement(bindingContext, (KtDeclaration) it.next()));
        }
        return klibMetadataIncrementalSerializer.serializePackageFragment(moduleDescriptor, arrayList, ktFile.getPackageFqName());
    }

    public static final ProtoBuf.PackageFragment serializeScope(KlibMetadataIncrementalSerializer klibMetadataIncrementalSerializer, KtSourceFile ktSourceFile, BindingContext bindingContext, ModuleDescriptor moduleDescriptor) {
        Intrinsics.checkNotNullParameter(klibMetadataIncrementalSerializer, "<this>");
        Intrinsics.checkNotNullParameter(ktSourceFile, "ktSourceFile");
        Intrinsics.checkNotNullParameter(bindingContext, "bindingContext");
        Intrinsics.checkNotNullParameter(moduleDescriptor, "moduleDescriptor");
        PsiFile psiFile = ((KtPsiSourceFile) ktSourceFile).getPsiFile();
        Intrinsics.checkNotNull(psiFile, "null cannot be cast to non-null type org.jetbrains.kotlin.psi.KtFile");
        KtFile ktFile = (KtFile) psiFile;
        List<KtDeclaration> declarations = ktFile.getDeclarations();
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(declarations, 10));
        Iterator<T> it = declarations.iterator();
        while (it.hasNext()) {
            arrayList.add(getDescriptorForElement(bindingContext, (KtDeclaration) it.next()));
        }
        return klibMetadataIncrementalSerializer.serializePackageFragment(moduleDescriptor, arrayList, ktFile.getPackageFqName());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void compareMetadataAndGoToNextICRoundIfNeeded(AnalysisResult analysisResult, CompilerConfiguration compilerConfiguration, Project project, List<? extends KtFile> list, boolean z) {
        IncrementalNextRoundChecker incrementalNextRoundChecker = (IncrementalNextRoundChecker) compilerConfiguration.get(JSConfigurationKeys.INCREMENTAL_NEXT_ROUND_CHECKER);
        if (incrementalNextRoundChecker == null) {
            return;
        }
        BindingContext bindingContext = analysisResult.getBindingContext();
        KlibMetadataIncrementalSerializer KlibMetadataIncrementalSerializer = KlibMetadataIncrementalSerializer(compilerConfiguration, project, z);
        for (KtFile ktFile : list) {
            ProtoBuf.PackageFragment packageFragmentSerializeScope = serializeScope(KlibMetadataIncrementalSerializer, ktFile, bindingContext, analysisResult.getModuleDescriptor());
            File fileVirtualToIoFile = VfsUtilCore.virtualToIoFile(ktFile.getVirtualFile());
            Intrinsics.checkNotNullExpressionValue(fileVirtualToIoFile, "virtualToIoFile(...)");
            byte[] byteArray = packageFragmentSerializeScope.toByteArray();
            Intrinsics.checkNotNullExpressionValue(byteArray, "toByteArray(...)");
            incrementalNextRoundChecker.checkProtoChanges(fileVirtualToIoFile, byteArray);
        }
        if (incrementalNextRoundChecker.shouldGoToNextRound()) {
            throw new IncrementalNextRoundException();
        }
    }

    public static final KlibMetadataIncrementalSerializer KlibMetadataIncrementalSerializer(CompilerConfiguration configuration, Project project, boolean z) {
        Intrinsics.checkNotNullParameter(configuration, "configuration");
        Intrinsics.checkNotNullParameter(project, "project");
        return new KlibMetadataIncrementalSerializer(CommonConfigurationKeysKt.getLanguageVersionSettings(configuration), getMetadataVersion(configuration), project, false, !getExpectActualLinker(configuration), z);
    }

    private static final Map<IrModuleFragment, String> getUniqueNameForEachFragment(Map<IrModuleFragment, ? extends KotlinLibrary> map) {
        Set<Map.Entry<IrModuleFragment, ? extends KotlinLibrary>> setEntrySet = map.entrySet();
        ArrayList arrayList = new ArrayList();
        Iterator<T> it = setEntrySet.iterator();
        while (it.hasNext()) {
            Map.Entry entry = (Map.Entry) it.next();
            IrModuleFragment irModuleFragment = (IrModuleFragment) entry.getKey();
            String jsOutputName = getJsOutputName((KotlinLibrary) entry.getValue());
            Pair pair = jsOutputName != null ? TuplesKt.to(irModuleFragment, jsOutputName) : null;
            if (pair != null) {
                arrayList.add(pair);
            }
        }
        return MapsKt.toMap(arrayList);
    }

    public static final File toIoFileOrNull(KtSourceFile ktSourceFile) {
        Intrinsics.checkNotNullParameter(ktSourceFile, "<this>");
        if (ktSourceFile instanceof KtIoFileSourceFile) {
            return ((KtIoFileSourceFile) ktSourceFile).getFile();
        }
        if (ktSourceFile instanceof KtVirtualFileSourceFile) {
            return VfsUtilCore.virtualToIoFile(((KtVirtualFileSourceFile) ktSourceFile).getVirtualFile());
        }
        if (ktSourceFile instanceof KtPsiSourceFile) {
            return VfsUtilCore.virtualToIoFile(((KtPsiSourceFile) ktSourceFile).getPsiFile().getVirtualFile());
        }
        String path = ktSourceFile.getPath();
        if (path != null) {
            return new File(path);
        }
        return null;
    }

    public static final List<KotlinFileSerializedData> getSerializedData(IncrementalDataProvider incrementalDataProvider, List<? extends KtSourceFile> newSources) {
        Intrinsics.checkNotNullParameter(incrementalDataProvider, "<this>");
        Intrinsics.checkNotNullParameter(newSources, "newSources");
        List<? extends KtSourceFile> list = newSources;
        LinkedHashMap linkedHashMap = new LinkedHashMap(RangesKt.coerceAtLeast(MapsKt.mapCapacity(CollectionsKt.collectionSizeOrDefault(list, 10)), 16));
        for (Object obj : list) {
            File ioFileOrNull = toIoFileOrNull((KtSourceFile) obj);
            Intrinsics.checkNotNull(ioFileOrNull);
            linkedHashMap.put(ioFileOrNull, obj);
        }
        Map<File, IrTranslationResultValue> serializedIrFiles = incrementalDataProvider.getSerializedIrFiles();
        Map<File, TranslationResultValue> compiledPackageParts = incrementalDataProvider.getCompiledPackageParts();
        serializedIrFiles.size();
        compiledPackageParts.size();
        ArrayList arrayList = new ArrayList();
        for (File file : serializedIrFiles.keySet()) {
            if (!linkedHashMap.containsKey(file)) {
                IrTranslationResultValue irTranslationResultValue = serializedIrFiles.get(file);
                if (irTranslationResultValue == null) {
                    throw new IllegalStateException(("No Ir Data found for file " + file).toString());
                }
                TranslationResultValue translationResultValue = compiledPackageParts.get(file);
                if (translationResultValue == null) {
                    throw new IllegalStateException(("No Meta Data found for file " + file).toString());
                }
                byte[] fileData = irTranslationResultValue.getFileData();
                String str = new String(irTranslationResultValue.getFqn(), Charsets.UTF_8);
                String path = file.getPath();
                Intrinsics.checkNotNullExpressionValue(path, "getPath(...)");
                arrayList.add(new KotlinFileSerializedData(translationResultValue.getMetadata(), new SerializedIrFile(fileData, str, StringsKt.replace$default(path, '\\', '/', false, 4, (Object) null), irTranslationResultValue.getTypes(), irTranslationResultValue.getSignatures(), irTranslationResultValue.getStrings(), irTranslationResultValue.getBodies(), irTranslationResultValue.getDeclarations(), irTranslationResultValue.getDebugInfo(), irTranslationResultValue.getFileMetadata())));
            }
        }
        return arrayList;
    }

    public static final List<KotlinFileSerializedData> getSerializedDataByPsiFiles(IncrementalDataProvider incrementalDataProvider, List<? extends KtFile> newSources) {
        Intrinsics.checkNotNullParameter(incrementalDataProvider, "<this>");
        Intrinsics.checkNotNullParameter(newSources, "newSources");
        List<? extends KtFile> list = newSources;
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(list, 10));
        Iterator<T> it = list.iterator();
        while (it.hasNext()) {
            arrayList.add(new KtPsiSourceFile((PsiFile) it.next()));
        }
        return getSerializedData(incrementalDataProvider, arrayList);
    }

    public static final IncrementalDataProvider getIncrementalDataProvider(CompilerConfiguration compilerConfiguration) {
        Intrinsics.checkNotNullParameter(compilerConfiguration, "<this>");
        return (IncrementalDataProvider) compilerConfiguration.get(JSConfigurationKeys.INCREMENTAL_DATA_PROVIDER);
    }

    private static final void assertNoExportedNamesClashes(CompilerConfiguration compilerConfiguration, String str, List<KotlinFileSerializedData> list) {
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        for (KotlinFileSerializedData kotlinFileSerializedData : list) {
            for (String str2 : JsIrFileMetadata.INSTANCE.fromByteArray(getFileMetadata(kotlinFileSerializedData.getIrData())).getExportedNames()) {
                ArrayList arrayList = (List) linkedHashMap.get(str2);
                if (arrayList == null) {
                    arrayList = new ArrayList();
                    linkedHashMap.put(str2, arrayList);
                }
                arrayList.add(kotlinFileSerializedData);
            }
        }
        LinkedHashMap linkedHashMap2 = new LinkedHashMap();
        for (Map.Entry entry : linkedHashMap.entrySet()) {
            if (((List) entry.getValue()).size() > 1) {
                linkedHashMap2.put(entry.getKey(), entry.getValue());
            }
        }
        LinkedHashMap linkedHashMap3 = linkedHashMap2;
        if (linkedHashMap3.isEmpty()) {
            return;
        }
        StringBuilder sb = new StringBuilder();
        for (Map.Entry entry2 : linkedHashMap3.entrySet()) {
            String str3 = (String) entry2.getKey();
            List list2 = (List) entry2.getValue();
            StringBuilder sbAppend = sb.append("  * Next files contain declarations with @JsExport and name '" + str3 + '\'');
            Intrinsics.checkNotNullExpressionValue(sbAppend, "append(...)");
            Intrinsics.checkNotNullExpressionValue(sbAppend.append('\n'), "append(...)");
            Iterator it = list2.iterator();
            while (it.hasNext()) {
                StringBuilder sbAppend2 = sb.append("    - " + ((KotlinFileSerializedData) it.next()).getIrData().getPath());
                Intrinsics.checkNotNullExpressionValue(sbAppend2, "append(...)");
                Intrinsics.checkNotNullExpressionValue(sbAppend2.append('\n'), "append(...)");
            }
        }
        String string = sb.toString();
        Intrinsics.checkNotNullExpressionValue(string, "toString(...)");
        IrMessageLoggerKt.getIrMessageLogger(compilerConfiguration).report(IrMessageLogger.Severity.WARNING, StringsKt.trimMargin$default("\n              |There are clashes of declaration names that annotated with @JsExport in module '" + str + "'.\n              |" + string + "\n              |Note, that this clash could affect the generated JS code in case of ES module kind usage\n        ", null, 1, null), null);
    }
}
