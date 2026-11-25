package org.jetbrains.kotlin.fir.backend;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.comparisons.ComparisonsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Reflection;
import org.jetbrains.kotlin.KtDiagnosticReporterWithImplicitIrBasedContext;
import org.jetbrains.kotlin.KtFakeSourceElementKind;
import org.jetbrains.kotlin.KtPsiSourceFileLinesMapping;
import org.jetbrains.kotlin.KtSourceElement;
import org.jetbrains.kotlin.KtSourceFile;
import org.jetbrains.kotlin.KtSourceFileLinesMapping;
import org.jetbrains.kotlin.KtSourceFileLinesMappingFromLineStartOffsets;
import org.jetbrains.kotlin.backend.common.CommonBackendErrors;
import org.jetbrains.kotlin.builtins.DefaultBuiltIns;
import org.jetbrains.kotlin.builtins.KotlinBuiltIns;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.config.AnalysisFlags;
import org.jetbrains.kotlin.config.LanguageFeature;
import org.jetbrains.kotlin.config.LanguageVersionSettings;
import org.jetbrains.kotlin.descriptors.DescriptorVisibilities;
import org.jetbrains.kotlin.descriptors.DescriptorVisibility;
import org.jetbrains.kotlin.descriptors.Modality;
import org.jetbrains.kotlin.descriptors.ModuleDescriptor;
import org.jetbrains.kotlin.descriptors.PackageFragmentDescriptor;
import org.jetbrains.kotlin.fir.FirAnnotationContainer;
import org.jetbrains.kotlin.fir.FirLanguageSettingsComponentKt;
import org.jetbrains.kotlin.fir.FirSession;
import org.jetbrains.kotlin.fir.UtilsKt;
import org.jetbrains.kotlin.fir.backend.generators.AnnotationGenerator;
import org.jetbrains.kotlin.fir.backend.generators.CallAndReferenceGenerator;
import org.jetbrains.kotlin.fir.backend.generators.DataClassMembersGenerator;
import org.jetbrains.kotlin.fir.backend.generators.DelegatedMemberGenerator;
import org.jetbrains.kotlin.fir.backend.generators.FakeOverrideGenerator;
import org.jetbrains.kotlin.fir.declarations.DeclarationUtilsKt;
import org.jetbrains.kotlin.fir.declarations.FirAnonymousInitializer;
import org.jetbrains.kotlin.fir.declarations.FirAnonymousObject;
import org.jetbrains.kotlin.fir.declarations.FirClass;
import org.jetbrains.kotlin.fir.declarations.FirClassLikeDeclaration;
import org.jetbrains.kotlin.fir.declarations.FirCodeFragment;
import org.jetbrains.kotlin.fir.declarations.FirConstructor;
import org.jetbrains.kotlin.fir.declarations.FirDeclaration;
import org.jetbrains.kotlin.fir.declarations.FirDeclarationOrigin;
import org.jetbrains.kotlin.fir.declarations.FirEnumEntry;
import org.jetbrains.kotlin.fir.declarations.FirField;
import org.jetbrains.kotlin.fir.declarations.FirFile;
import org.jetbrains.kotlin.fir.declarations.FirProperty;
import org.jetbrains.kotlin.fir.declarations.FirRegularClass;
import org.jetbrains.kotlin.fir.declarations.FirScript;
import org.jetbrains.kotlin.fir.declarations.FirSimpleFunction;
import org.jetbrains.kotlin.fir.declarations.FirTypeAlias;
import org.jetbrains.kotlin.fir.declarations.FirVariable;
import org.jetbrains.kotlin.fir.descriptors.FirModuleDescriptor;
import org.jetbrains.kotlin.fir.expressions.FirExpression;
import org.jetbrains.kotlin.fir.expressions.FirStatement;
import org.jetbrains.kotlin.fir.extensions.FirDeclarationGenerationExtension;
import org.jetbrains.kotlin.fir.extensions.FirDeclarationGenerationExtensionKt;
import org.jetbrains.kotlin.fir.extensions.FirExtensionServiceKt;
import org.jetbrains.kotlin.fir.extensions.GeneratedDeclarationsUtilsKt;
import org.jetbrains.kotlin.fir.resolve.ScopeSession;
import org.jetbrains.kotlin.fir.signaturer.FirBasedSignatureComposer;
import org.jetbrains.kotlin.fir.symbols.Fir2IrConstructorSymbol;
import org.jetbrains.kotlin.fir.symbols.Fir2IrSimpleFunctionSymbol;
import org.jetbrains.kotlin.fir.symbols.FirLazyDeclarationResolverKt;
import org.jetbrains.kotlin.fir.symbols.impl.FirConstructorSymbol;
import org.jetbrains.kotlin.fir.types.FirTypeRef;
import org.jetbrains.kotlin.ir.IrElement;
import org.jetbrains.kotlin.ir.IrLock;
import org.jetbrains.kotlin.ir.IrStatement;
import org.jetbrains.kotlin.ir.PsiIrFileEntry;
import org.jetbrains.kotlin.ir.declarations.IrClass;
import org.jetbrains.kotlin.ir.declarations.IrConstructor;
import org.jetbrains.kotlin.ir.declarations.IrDeclaration;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationOrigin;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationOriginImpl;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationParent;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationsKt;
import org.jetbrains.kotlin.ir.declarations.IrFactory;
import org.jetbrains.kotlin.ir.declarations.IrFile;
import org.jetbrains.kotlin.ir.declarations.IrModuleFragment;
import org.jetbrains.kotlin.ir.declarations.IrScript;
import org.jetbrains.kotlin.ir.declarations.IrSimpleFunction;
import org.jetbrains.kotlin.ir.declarations.IrValueParameter;
import org.jetbrains.kotlin.ir.declarations.impl.IrFileImpl;
import org.jetbrains.kotlin.ir.declarations.impl.IrModuleFragmentImpl;
import org.jetbrains.kotlin.ir.expressions.IrBlockBody;
import org.jetbrains.kotlin.ir.expressions.IrErrorExpression;
import org.jetbrains.kotlin.ir.expressions.impl.IrDelegatingConstructorCallImpl;
import org.jetbrains.kotlin.ir.interpreter.IrInterpreter;
import org.jetbrains.kotlin.ir.interpreter.IrInterpreterConfiguration;
import org.jetbrains.kotlin.ir.interpreter.IrInterpreterEnvironment;
import org.jetbrains.kotlin.ir.interpreter.checker.EvaluationMode;
import org.jetbrains.kotlin.ir.interpreter.transformer.IrConstTransformerKt;
import org.jetbrains.kotlin.ir.linkage.IrProvider;
import org.jetbrains.kotlin.ir.symbols.IrConstructorSymbol;
import org.jetbrains.kotlin.ir.symbols.IrSimpleFunctionSymbol;
import org.jetbrains.kotlin.ir.types.IrSimpleType;
import org.jetbrains.kotlin.ir.types.IrType;
import org.jetbrains.kotlin.ir.util.IdSignature;
import org.jetbrains.kotlin.ir.util.IrUtilsKt;
import org.jetbrains.kotlin.ir.util.KotlinMangler;
import org.jetbrains.kotlin.ir.util.NaiveSourceBasedFileEntryImpl;
import org.jetbrains.kotlin.ir.util.SymbolTable;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.platform.TargetPlatform;
import org.jetbrains.kotlin.psi.KtFile;
import org.jetbrains.kotlin.serialization.deserialization.descriptors.DeserializedContainerSource;

/* JADX INFO: compiled from: Fir2IrConverter.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000¦\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u001c\n\u0002\b\u0002\u0018\u0000 \u008f\u00012\u00020\u0001:\u0002\u008f\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0001¢\u0006\u0002\u0010\u0005J\u000e\u0010a\u001a\u00020b2\u0006\u0010c\u001a\u00020dJ\u0010\u0010e\u001a\u00020b2\u0006\u0010f\u001a\u00020gH\u0002J\u0014\u0010h\u001a\u00020b2\f\u0010i\u001a\b\u0012\u0004\u0012\u00020F02J\u0016\u0010j\u001a\b\u0012\u0004\u0012\u00020k022\u0006\u0010l\u001a\u00020dH\u0002J\u0016\u0010m\u001a\u00020b2\u0006\u0010n\u001a\u00020o2\u0006\u0010l\u001a\u00020dJ\u0010\u0010p\u001a\u00020b2\u0006\u0010c\u001a\u00020qH\u0002J\u0010\u0010r\u001a\u00020b2\u0006\u0010f\u001a\u00020gH\u0002J\u001d\u0010s\u001a\u00020d2\u0006\u0010c\u001a\u00020q2\u0006\u0010l\u001a\u00020dH\u0000¢\u0006\u0002\btJ\u001a\u0010u\u001a\u00020d2\u0006\u0010v\u001a\u00020w2\b\b\u0002\u0010l\u001a\u00020dH\u0002J\u0010\u0010x\u001a\u00020b2\u0006\u0010f\u001a\u00020gH\u0002J\u0016\u0010y\u001a\u00020d2\u0006\u0010c\u001a\u00020q2\u0006\u0010z\u001a\u00020{J\u0016\u0010|\u001a\u00020d2\u0006\u0010c\u001a\u00020q2\u0006\u0010z\u001a\u00020{J$\u0010}\u001a\u0004\u0018\u00010F2\u0006\u0010~\u001a\u00020k2\b\u0010\u007f\u001a\u0004\u0018\u00010q2\u0006\u0010z\u001a\u00020{H\u0002J\u0011\u0010\u0080\u0001\u001a\u00020b2\u0006\u0010c\u001a\u00020qH\u0002J\u0019\u0010\u0081\u0001\u001a\u00020d2\u0006\u0010c\u001a\u00020q2\u0006\u0010z\u001a\u00020{H\u0002J\u001b\u0010\u0082\u0001\u001a\u00020b2\u0006\u0010f\u001a\u00020g2\b\u0010\u0083\u0001\u001a\u00030\u0084\u0001H\u0002J\u0019\u0010\u0085\u0001\u001a\u00020b2\u0006\u0010c\u001a\u00020q2\u0006\u0010l\u001a\u00020dH\u0002J5\u0010\u0086\u0001\u001a\u00020b2\r\u0010\u0087\u0001\u001a\b\u0012\u0004\u0012\u00020g022\b\u0010\u0088\u0001\u001a\u00030\u0089\u00012\b\u0010\u008a\u0001\u001a\u00030\u008b\u00012\u0007\u0010\u008c\u0001\u001a\u00020`H\u0002J\u001f\u0010\u008d\u0001\u001a\t\u0012\u0004\u0012\u00020k0\u008e\u00012\r\u0010i\u001a\t\u0012\u0004\u0012\u00020k0\u008e\u0001H\u0002R\u0012\u0010\u0006\u001a\u00020\u0007X\u0096\u0005¢\u0006\u0006\u001a\u0004\b\b\u0010\tR\u0012\u0010\n\u001a\u00020\u000bX\u0096\u0005¢\u0006\u0006\u001a\u0004\b\f\u0010\rR\u0012\u0010\u000e\u001a\u00020\u000fX\u0096\u0005¢\u0006\u0006\u001a\u0004\b\u0010\u0010\u0011R\u0012\u0010\u0012\u001a\u00020\u0013X\u0096\u0005¢\u0006\u0006\u001a\u0004\b\u0014\u0010\u0015R\u0012\u0010\u0016\u001a\u00020\u0017X\u0096\u0005¢\u0006\u0006\u001a\u0004\b\u0018\u0010\u0019R\u000e\u0010\u0004\u001a\u00020\u0001X\u0082\u0004¢\u0006\u0002\n\u0000R\u0012\u0010\u001a\u001a\u00020\u001bX\u0096\u0005¢\u0006\u0006\u001a\u0004\b\u001c\u0010\u001dR\u0012\u0010\u001e\u001a\u00020\u0000X\u0096\u0005¢\u0006\u0006\u001a\u0004\b\u001f\u0010 R\u0012\u0010!\u001a\u00020\"X\u0096\u0005¢\u0006\u0006\u001a\u0004\b#\u0010$R\u0012\u0010%\u001a\u00020&X\u0096\u0005¢\u0006\u0006\u001a\u0004\b'\u0010(R\u0012\u0010)\u001a\u00020*X\u0096\u0005¢\u0006\u0006\u001a\u0004\b+\u0010,R\u0012\u0010-\u001a\u00020.X\u0096\u0005¢\u0006\u0006\u001a\u0004\b/\u00100R\u0014\u00101\u001a\b\u0012\u0004\u0012\u00020302X\u0082\u0004¢\u0006\u0002\n\u0000R\u0012\u00104\u001a\u000205X\u0096\u0005¢\u0006\u0006\u001a\u0004\b6\u00107R\u0012\u00108\u001a\u000209X\u0096\u0005¢\u0006\u0006\u001a\u0004\b:\u0010;R\u0018\u0010<\u001a\b\u0012\u0004\u0012\u00020=02X\u0096\u0005¢\u0006\u0006\u001a\u0004\b>\u0010?R\u0012\u0010@\u001a\u00020AX\u0096\u0005¢\u0006\u0006\u001a\u0004\bB\u0010CR\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010D\u001a\b\u0012\u0004\u0012\u00020F0EX\u0082\u0004¢\u0006\u0002\n\u0000R\u0012\u0010G\u001a\u00020HX\u0096\u0005¢\u0006\u0006\u001a\u0004\bI\u0010JR\u0012\u0010K\u001a\u00020LX\u0096\u0005¢\u0006\u0006\u001a\u0004\bM\u0010NR\u0012\u0010O\u001a\u00020PX\u0096\u0005¢\u0006\u0006\u001a\u0004\bQ\u0010RR\u0012\u0010S\u001a\u00020TX\u0096\u0005¢\u0006\u0006\u001a\u0004\bU\u0010VR\u0012\u0010W\u001a\u00020XX\u0096\u0005¢\u0006\u0006\u001a\u0004\bY\u0010ZR\u0012\u0010[\u001a\u00020\\X\u0096\u0005¢\u0006\u0006\u001a\u0004\b]\u0010^R\u000e\u0010_\u001a\u00020`X\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006\u0090\u0001"}, d2 = {"Lorg/jetbrains/kotlin/fir/backend/Fir2IrConverter;", "Lorg/jetbrains/kotlin/fir/backend/Fir2IrComponents;", "moduleDescriptor", "Lorg/jetbrains/kotlin/fir/descriptors/FirModuleDescriptor;", "components", "(Lorg/jetbrains/kotlin/fir/descriptors/FirModuleDescriptor;Lorg/jetbrains/kotlin/fir/backend/Fir2IrComponents;)V", "annotationGenerator", "Lorg/jetbrains/kotlin/fir/backend/generators/AnnotationGenerator;", "getAnnotationGenerator", "()Lorg/jetbrains/kotlin/fir/backend/generators/AnnotationGenerator;", "annotationsFromPluginRegistrar", "Lorg/jetbrains/kotlin/fir/backend/Fir2IrAnnotationsFromPluginRegistrar;", "getAnnotationsFromPluginRegistrar", "()Lorg/jetbrains/kotlin/fir/backend/Fir2IrAnnotationsFromPluginRegistrar;", "builtIns", "Lorg/jetbrains/kotlin/fir/backend/Fir2IrBuiltIns;", "getBuiltIns", "()Lorg/jetbrains/kotlin/fir/backend/Fir2IrBuiltIns;", "callGenerator", "Lorg/jetbrains/kotlin/fir/backend/generators/CallAndReferenceGenerator;", "getCallGenerator", "()Lorg/jetbrains/kotlin/fir/backend/generators/CallAndReferenceGenerator;", "classifierStorage", "Lorg/jetbrains/kotlin/fir/backend/Fir2IrClassifierStorage;", "getClassifierStorage", "()Lorg/jetbrains/kotlin/fir/backend/Fir2IrClassifierStorage;", "configuration", "Lorg/jetbrains/kotlin/fir/backend/Fir2IrConfiguration;", "getConfiguration", "()Lorg/jetbrains/kotlin/fir/backend/Fir2IrConfiguration;", "converter", "getConverter", "()Lorg/jetbrains/kotlin/fir/backend/Fir2IrConverter;", "declarationStorage", "Lorg/jetbrains/kotlin/fir/backend/Fir2IrDeclarationStorage;", "getDeclarationStorage", "()Lorg/jetbrains/kotlin/fir/backend/Fir2IrDeclarationStorage;", "delegatedMemberGenerator", "Lorg/jetbrains/kotlin/fir/backend/generators/DelegatedMemberGenerator;", "getDelegatedMemberGenerator", "()Lorg/jetbrains/kotlin/fir/backend/generators/DelegatedMemberGenerator;", "extensions", "Lorg/jetbrains/kotlin/fir/backend/Fir2IrExtensions;", "getExtensions", "()Lorg/jetbrains/kotlin/fir/backend/Fir2IrExtensions;", "fakeOverrideGenerator", "Lorg/jetbrains/kotlin/fir/backend/generators/FakeOverrideGenerator;", "getFakeOverrideGenerator", "()Lorg/jetbrains/kotlin/fir/backend/generators/FakeOverrideGenerator;", "generatorExtensions", "", "Lorg/jetbrains/kotlin/fir/extensions/FirDeclarationGenerationExtension;", "irBuiltIns", "Lorg/jetbrains/kotlin/fir/backend/IrBuiltInsOverFir;", "getIrBuiltIns", "()Lorg/jetbrains/kotlin/fir/backend/IrBuiltInsOverFir;", "irFactory", "Lorg/jetbrains/kotlin/ir/declarations/IrFactory;", "getIrFactory", "()Lorg/jetbrains/kotlin/ir/declarations/IrFactory;", "irProviders", "Lorg/jetbrains/kotlin/ir/linkage/IrProvider;", "getIrProviders", "()Ljava/util/List;", "lock", "Lorg/jetbrains/kotlin/ir/IrLock;", "getLock", "()Lorg/jetbrains/kotlin/ir/IrLock;", "postponedDeclarationsForFakeOverridesBinding", "", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclaration;", "scopeSession", "Lorg/jetbrains/kotlin/fir/resolve/ScopeSession;", "getScopeSession", "()Lorg/jetbrains/kotlin/fir/resolve/ScopeSession;", "session", "Lorg/jetbrains/kotlin/fir/FirSession;", "getSession", "()Lorg/jetbrains/kotlin/fir/FirSession;", "signatureComposer", "Lorg/jetbrains/kotlin/fir/signaturer/FirBasedSignatureComposer;", "getSignatureComposer", "()Lorg/jetbrains/kotlin/fir/signaturer/FirBasedSignatureComposer;", "symbolTable", "Lorg/jetbrains/kotlin/ir/util/SymbolTable;", "getSymbolTable", "()Lorg/jetbrains/kotlin/ir/util/SymbolTable;", "typeConverter", "Lorg/jetbrains/kotlin/fir/backend/Fir2IrTypeConverter;", "getTypeConverter", "()Lorg/jetbrains/kotlin/fir/backend/Fir2IrTypeConverter;", "visibilityConverter", "Lorg/jetbrains/kotlin/fir/backend/Fir2IrVisibilityConverter;", "getVisibilityConverter", "()Lorg/jetbrains/kotlin/fir/backend/Fir2IrVisibilityConverter;", "wereSourcesFakeOverridesBound", "", "bindFakeOverridesInClass", "", "klass", "Lorg/jetbrains/kotlin/ir/declarations/IrClass;", "bindFakeOverridesInFile", "file", "Lorg/jetbrains/kotlin/fir/declarations/FirFile;", "bindFakeOverridesOrPostpone", "declarations", "delegatedMembers", "Lorg/jetbrains/kotlin/fir/declarations/FirDeclaration;", "irClass", "processAnonymousObjectHeaders", "anonymousObject", "Lorg/jetbrains/kotlin/fir/declarations/FirAnonymousObject;", "processClassAndNestedClassHeaders", "Lorg/jetbrains/kotlin/fir/declarations/FirClass;", "processClassHeaders", "processClassMembers", "processClassMembers$fir2ir", "processCodeFragmentMembers", "codeFragment", "Lorg/jetbrains/kotlin/fir/declarations/FirCodeFragment;", "processFileAndClassMembers", "processLocalClassAndNestedClasses", "parent", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationParent;", "processLocalClassAndNestedClassesOnTheFly", "processMemberDeclaration", "declaration", "containingClass", "processNestedClassHeaders", "registerClassAndNestedClasses", "registerFileAndClasses", "moduleFragment", "Lorg/jetbrains/kotlin/ir/declarations/IrModuleFragment;", "registerNestedClasses", "runSourcesConversion", "allFirFiles", "irModuleFragment", "Lorg/jetbrains/kotlin/ir/declarations/impl/IrModuleFragmentImpl;", "fir2irVisitor", "Lorg/jetbrains/kotlin/fir/backend/Fir2IrVisitor;", "runPreCacheBuiltinClasses", "syntheticPropertiesLast", "", "Companion", "fir2ir"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class Fir2IrConverter implements Fir2IrComponents {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private final Fir2IrComponents components;
    private final List<FirDeclarationGenerationExtension> generatorExtensions;
    private final FirModuleDescriptor moduleDescriptor;
    private final List<IrDeclaration> postponedDeclarationsForFakeOverridesBinding;
    private boolean wereSourcesFakeOverridesBound;

    @Override // org.jetbrains.kotlin.fir.backend.Fir2IrComponents
    public AnnotationGenerator getAnnotationGenerator() {
        return this.components.getAnnotationGenerator();
    }

    @Override // org.jetbrains.kotlin.fir.backend.Fir2IrComponents
    public Fir2IrAnnotationsFromPluginRegistrar getAnnotationsFromPluginRegistrar() {
        return this.components.getAnnotationsFromPluginRegistrar();
    }

    @Override // org.jetbrains.kotlin.fir.backend.Fir2IrComponents
    public Fir2IrBuiltIns getBuiltIns() {
        return this.components.getBuiltIns();
    }

    @Override // org.jetbrains.kotlin.fir.backend.Fir2IrComponents
    public CallAndReferenceGenerator getCallGenerator() {
        return this.components.getCallGenerator();
    }

    @Override // org.jetbrains.kotlin.fir.backend.Fir2IrComponents
    public Fir2IrClassifierStorage getClassifierStorage() {
        return this.components.getClassifierStorage();
    }

    @Override // org.jetbrains.kotlin.fir.backend.Fir2IrComponents
    public Fir2IrConfiguration getConfiguration() {
        return this.components.getConfiguration();
    }

    @Override // org.jetbrains.kotlin.fir.backend.Fir2IrComponents
    public Fir2IrConverter getConverter() {
        return this.components.getConverter();
    }

    @Override // org.jetbrains.kotlin.fir.backend.Fir2IrComponents
    public Fir2IrDeclarationStorage getDeclarationStorage() {
        return this.components.getDeclarationStorage();
    }

    @Override // org.jetbrains.kotlin.fir.backend.Fir2IrComponents
    public DelegatedMemberGenerator getDelegatedMemberGenerator() {
        return this.components.getDelegatedMemberGenerator();
    }

    @Override // org.jetbrains.kotlin.fir.backend.Fir2IrComponents
    public Fir2IrExtensions getExtensions() {
        return this.components.getExtensions();
    }

    @Override // org.jetbrains.kotlin.fir.backend.Fir2IrComponents
    public FakeOverrideGenerator getFakeOverrideGenerator() {
        return this.components.getFakeOverrideGenerator();
    }

    @Override // org.jetbrains.kotlin.fir.backend.Fir2IrComponents
    public IrBuiltInsOverFir getIrBuiltIns() {
        return this.components.getIrBuiltIns();
    }

    @Override // org.jetbrains.kotlin.fir.backend.Fir2IrComponents
    public IrFactory getIrFactory() {
        return this.components.getIrFactory();
    }

    @Override // org.jetbrains.kotlin.fir.backend.Fir2IrComponents
    public List<IrProvider> getIrProviders() {
        return this.components.getIrProviders();
    }

    @Override // org.jetbrains.kotlin.fir.backend.Fir2IrComponents
    public IrLock getLock() {
        return this.components.getLock();
    }

    @Override // org.jetbrains.kotlin.fir.backend.Fir2IrComponents
    public ScopeSession getScopeSession() {
        return this.components.getScopeSession();
    }

    @Override // org.jetbrains.kotlin.fir.backend.Fir2IrComponents
    public FirSession getSession() {
        return this.components.getSession();
    }

    @Override // org.jetbrains.kotlin.fir.backend.Fir2IrComponents
    public FirBasedSignatureComposer getSignatureComposer() {
        return this.components.getSignatureComposer();
    }

    @Override // org.jetbrains.kotlin.fir.backend.Fir2IrComponents
    public SymbolTable getSymbolTable() {
        return this.components.getSymbolTable();
    }

    @Override // org.jetbrains.kotlin.fir.backend.Fir2IrComponents
    public Fir2IrTypeConverter getTypeConverter() {
        return this.components.getTypeConverter();
    }

    @Override // org.jetbrains.kotlin.fir.backend.Fir2IrComponents
    public Fir2IrVisibilityConverter getVisibilityConverter() {
        return this.components.getVisibilityConverter();
    }

    public Fir2IrConverter(FirModuleDescriptor moduleDescriptor, Fir2IrComponents components) {
        Intrinsics.checkNotNullParameter(moduleDescriptor, "moduleDescriptor");
        Intrinsics.checkNotNullParameter(components, "components");
        this.moduleDescriptor = moduleDescriptor;
        this.components = components;
        this.generatorExtensions = FirDeclarationGenerationExtensionKt.getDeclarationGenerators(FirExtensionServiceKt.getExtensionService(getSession()));
        this.postponedDeclarationsForFakeOverridesBinding = new ArrayList();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void runSourcesConversion(List<? extends FirFile> allFirFiles, IrModuleFragmentImpl irModuleFragment, Fir2IrVisitor fir2irVisitor, boolean runPreCacheBuiltinClasses) {
        FirLazyDeclarationResolverKt.getLazyDeclarationResolver(getSession()).disableLazyResolveContractChecks();
        Iterator<? extends FirFile> it = allFirFiles.iterator();
        while (it.hasNext()) {
            registerFileAndClasses(it.next(), irModuleFragment);
        }
        if (runPreCacheBuiltinClasses) {
            getClassifierStorage().preCacheBuiltinClasses();
        }
        Iterator<? extends FirFile> it2 = allFirFiles.iterator();
        while (it2.hasNext()) {
            processClassHeaders(it2.next());
        }
        Iterator<? extends FirFile> it3 = allFirFiles.iterator();
        while (it3.hasNext()) {
            processFileAndClassMembers(it3.next());
        }
        Iterator<? extends FirFile> it4 = allFirFiles.iterator();
        while (it4.hasNext()) {
            bindFakeOverridesInFile(it4.next());
        }
        this.wereSourcesFakeOverridesBound = true;
        getFakeOverrideGenerator().bindOverriddenSymbols(this.postponedDeclarationsForFakeOverridesBinding);
        this.postponedDeclarationsForFakeOverridesBinding.clear();
        getClassifierStorage().processMembersOfClassesCreatedOnTheFly();
        getDelegatedMemberGenerator().generateBodies();
        for (FirFile firFile : allFirFiles) {
            try {
            } catch (Throwable th) {
                UtilsKt.getExceptionHandler(firFile.getModuleData().getSession()).handleExceptionOnFileAnalysis(firFile, th);
                throw null;
            }
        }
        INSTANCE.evaluateConstants(irModuleFragment, getConfiguration());
    }

    public final void bindFakeOverridesOrPostpone(List<? extends IrDeclaration> declarations) {
        Intrinsics.checkNotNullParameter(declarations, "declarations");
        if (this.wereSourcesFakeOverridesBound) {
            getFakeOverrideGenerator().bindOverriddenSymbols(declarations);
        } else {
            CollectionsKt.addAll(this.postponedDeclarationsForFakeOverridesBinding, declarations);
        }
    }

    public final IrClass processLocalClassAndNestedClassesOnTheFly(FirClass klass, IrDeclarationParent parent) {
        Intrinsics.checkNotNullParameter(klass, "klass");
        Intrinsics.checkNotNullParameter(parent, "parent");
        IrClass irClassRegisterClassAndNestedClasses = registerClassAndNestedClasses(klass, parent);
        processClassAndNestedClassHeaders(klass);
        return irClassRegisterClassAndNestedClasses;
    }

    public final IrClass processLocalClassAndNestedClasses(FirClass klass, IrDeclarationParent parent) {
        Intrinsics.checkNotNullParameter(klass, "klass");
        Intrinsics.checkNotNullParameter(parent, "parent");
        IrClass irClassRegisterClassAndNestedClasses = registerClassAndNestedClasses(klass, parent);
        processClassAndNestedClassHeaders(klass);
        processClassMembers$fir2ir(klass, irClassRegisterClassAndNestedClasses);
        bindFakeOverridesInClass(irClassRegisterClassAndNestedClasses);
        return irClassRegisterClassAndNestedClasses;
    }

    private final void registerFileAndClasses(FirFile file, IrModuleFragment moduleFragment) {
        NaiveSourceBasedFileEntryImpl naiveSourceBasedFileEntryImpl;
        String name2;
        String name3;
        FirDeclarationOrigin origin = file.getOrigin();
        if (Intrinsics.areEqual(origin, FirDeclarationOrigin.Source.INSTANCE)) {
            PsiElement psi = UtilsKt.getPsi(file);
            if (psi != null) {
                naiveSourceBasedFileEntryImpl = new PsiIrFileEntry((KtFile) psi);
            } else {
                KtSourceFileLinesMapping sourceFileLinesMapping = file.getSourceFileLinesMapping();
                if (sourceFileLinesMapping instanceof KtSourceFileLinesMappingFromLineStartOffsets) {
                    KtSourceFile sourceFile = file.getSourceFile();
                    if (sourceFile == null || (name3 = sourceFile.getPath()) == null) {
                        KtSourceFile sourceFile2 = file.getSourceFile();
                        name3 = sourceFile2 != null ? sourceFile2.getName() : file.getName();
                    }
                    KtSourceFileLinesMappingFromLineStartOffsets ktSourceFileLinesMappingFromLineStartOffsets = (KtSourceFileLinesMappingFromLineStartOffsets) sourceFileLinesMapping;
                    naiveSourceBasedFileEntryImpl = new NaiveSourceBasedFileEntryImpl(name3, ktSourceFileLinesMappingFromLineStartOffsets.getLineStartOffsets(), ktSourceFileLinesMappingFromLineStartOffsets.getLastOffset());
                } else if (sourceFileLinesMapping instanceof KtPsiSourceFileLinesMapping) {
                    naiveSourceBasedFileEntryImpl = new PsiIrFileEntry(((KtPsiSourceFileLinesMapping) sourceFileLinesMapping).getPsiFile());
                } else {
                    KtSourceFile sourceFile3 = file.getSourceFile();
                    if (sourceFile3 == null || (name2 = sourceFile3.getPath()) == null) {
                        KtSourceFile sourceFile4 = file.getSourceFile();
                        name2 = sourceFile4 != null ? sourceFile4.getName() : file.getName();
                    }
                    naiveSourceBasedFileEntryImpl = new NaiveSourceBasedFileEntryImpl(name2, null, 0, 6, null);
                }
            }
        } else {
            if (!(origin instanceof FirDeclarationOrigin.Synthetic)) {
                throw new IllegalStateException(("Unsupported file origin: " + file.getOrigin()).toString());
            }
            naiveSourceBasedFileEntryImpl = new NaiveSourceBasedFileEntryImpl(file.getName(), null, 0, 6, null);
        }
        IrFileImpl irFileImpl = new IrFileImpl(naiveSourceBasedFileEntryImpl, (PackageFragmentDescriptor) CollectionsKt.first((List) this.moduleDescriptor.getPackage(UtilsKt.getPackageFqName(file)).getFragments()), moduleFragment);
        IrFileImpl irFileImpl2 = irFileImpl;
        getDeclarationStorage().registerFile(file, irFileImpl2);
        for (FirDeclaration firDeclaration : file.getDeclarations()) {
            if (firDeclaration instanceof FirRegularClass) {
                registerClassAndNestedClasses((FirClass) firDeclaration, irFileImpl);
            } else if (firDeclaration instanceof FirCodeFragment) {
                getClassifierStorage().registerCodeFragmentClass((FirCodeFragment) firDeclaration, irFileImpl2);
            }
        }
        moduleFragment.getFiles().add(irFileImpl);
    }

    private final void processClassHeaders(FirFile file) {
        for (FirDeclaration firDeclaration : file.getDeclarations()) {
            if (firDeclaration instanceof FirRegularClass) {
                processClassAndNestedClassHeaders((FirClass) firDeclaration);
            } else if (firDeclaration instanceof FirTypeAlias) {
                getClassifierStorage().registerTypeAlias((FirTypeAlias) firDeclaration, getDeclarationStorage().getIrFile(file));
            }
        }
    }

    private final void processFileAndClassMembers(FirFile file) {
        IrFile irFile = getDeclarationStorage().getIrFile(file);
        Iterator<FirDeclaration> it = file.getDeclarations().iterator();
        while (it.hasNext()) {
            IrDeclaration irDeclarationProcessMemberDeclaration = processMemberDeclaration(it.next(), null, irFile);
            if (irDeclarationProcessMemberDeclaration != null) {
                irFile.getDeclarations().add(irDeclarationProcessMemberDeclaration);
            }
        }
    }

    public final void processAnonymousObjectHeaders(FirAnonymousObject anonymousObject, IrClass irClass) {
        Intrinsics.checkNotNullParameter(anonymousObject, "anonymousObject");
        Intrinsics.checkNotNullParameter(irClass, "irClass");
        FirAnonymousObject firAnonymousObject = anonymousObject;
        registerNestedClasses(firAnonymousObject, irClass);
        processNestedClassHeaders(firAnonymousObject);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final IrClass processClassMembers$fir2ir(FirClass klass, IrClass irClass) {
        Intrinsics.checkNotNullParameter(klass, "klass");
        Intrinsics.checkNotNullParameter(irClass, "irClass");
        ArrayList arrayList = new ArrayList();
        arrayList.addAll(klass.getDeclarations());
        boolean z = klass instanceof FirRegularClass;
        if (z && !this.generatorExtensions.isEmpty()) {
            FirRegularClass firRegularClass = (FirRegularClass) klass;
            arrayList.addAll(GeneratedDeclarationsUtilsKt.generatedMembers(firRegularClass, getSession()));
            arrayList.addAll(GeneratedDeclarationsUtilsKt.generatedNestedClassifiers(firRegularClass, getSession()));
        }
        FirConstructorSymbol firConstructorSymbolPrimaryConstructorIfAny = DeclarationUtilsKt.primaryConstructorIfAny(klass, getSession());
        IrConstructor orCreateIrConstructor$default = firConstructorSymbolPrimaryConstructorIfAny != null ? Fir2IrDeclarationStorage.getOrCreateIrConstructor$default(getDeclarationStorage(), (FirConstructor) firConstructorSymbolPrimaryConstructorIfAny.getFir(), irClass, null, klass.getSymbol().getClassId().isLocal(), 4, null) : null;
        if (orCreateIrConstructor$default != null) {
            irClass.getDeclarations().add(orCreateIrConstructor$default);
        }
        Iterator<FirDeclaration> it = syntheticPropertiesLast(arrayList).iterator();
        while (it.hasNext()) {
            IrDeclaration irDeclarationProcessMemberDeclaration = processMemberDeclaration(it.next(), klass, irClass);
            if (irDeclarationProcessMemberDeclaration != null) {
                irClass.getDeclarations().add(irDeclarationProcessMemberDeclaration);
            }
        }
        ArrayList arrayList2 = arrayList;
        CollectionsKt.addAll(arrayList2, delegatedMembers(irClass));
        if (z && orCreateIrConstructor$default != null && (irClass.getIsValue() || irClass.getIsData())) {
            IrConstructor irConstructor = orCreateIrConstructor$default;
            getDeclarationStorage().enterScope(irConstructor);
            DataClassMembersGenerator dataClassMembersGenerator = new DataClassMembersGenerator(this.components);
            if (IrDeclarationsKt.isSingleFieldValueClass(irClass)) {
                CollectionsKt.addAll(arrayList2, dataClassMembersGenerator.generateSingleFieldValueClassMembers((FirRegularClass) klass, irClass));
            }
            if (IrDeclarationsKt.isMultiFieldValueClass(irClass)) {
                CollectionsKt.addAll(arrayList2, dataClassMembersGenerator.generateMultiFieldValueClassMembers((FirRegularClass) klass, irClass));
            }
            if (irClass.getIsData()) {
                CollectionsKt.addAll(arrayList2, dataClassMembersGenerator.generateDataClassMembers((FirRegularClass) klass, irClass));
            }
            getDeclarationStorage().leaveScope(irConstructor);
        }
        getFakeOverrideGenerator().addFakeOverrides(irClass, klass, arrayList2);
        return irClass;
    }

    static /* synthetic */ IrClass processCodeFragmentMembers$default(Fir2IrConverter fir2IrConverter, FirCodeFragment firCodeFragment, IrClass irClass, int i, Object obj) {
        if ((i & 2) != 0) {
            irClass = fir2IrConverter.getClassifierStorage().getCachedIrCodeFragment(firCodeFragment);
            Intrinsics.checkNotNull(irClass);
        }
        return fir2IrConverter.processCodeFragmentMembers(firCodeFragment, irClass);
    }

    private final IrClass processCodeFragmentMembers(final FirCodeFragment codeFragment, final IrClass irClass) {
        final CodeFragmentConversionData conversionData = CodeFragmentUtilsKt.getConversionData(codeFragment);
        IrClass irClass2 = irClass;
        getDeclarationStorage().enterScope(irClass2);
        final IdSignature signature = irClass.getSymbol().getSignature();
        Intrinsics.checkNotNull(signature);
        IrConstructor irConstructorDeclareConstructor = getSymbolTable().declareConstructor(signature, new Function0<IrConstructorSymbol>() { // from class: org.jetbrains.kotlin.fir.backend.Fir2IrConverter$processCodeFragmentMembers$irPrimaryConstructor$1
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final IrConstructorSymbol invoke() {
                return new Fir2IrConstructorSymbol(signature);
            }
        }, new Function1<IrConstructorSymbol, IrConstructor>() { // from class: org.jetbrains.kotlin.fir.backend.Fir2IrConverter$processCodeFragmentMembers$irPrimaryConstructor$2
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final IrConstructor invoke(IrConstructorSymbol irSymbol) {
                Intrinsics.checkNotNullParameter(irSymbol, "irSymbol");
                IrFactory irFactory = this.this$0.getIrFactory();
                IrDeclarationOrigin.DEFINED defined = IrDeclarationOrigin.DEFINED.INSTANCE;
                Name nameSpecial = Name.special("<init>");
                DescriptorVisibility visibility = irClass.getVisibility();
                IrSimpleType defaultType = IrUtilsKt.getDefaultType(irClass);
                Intrinsics.checkNotNull(nameSpecial);
                IrConstructor irConstructorCreateConstructor$default = IrFactory.DefaultImpls.createConstructor$default(irFactory, -1, -1, (IrDeclarationOrigin) defined, nameSpecial, visibility, false, false, (IrType) defaultType, irSymbol, true, false, (DeserializedContainerSource) null, 2048, (Object) null);
                IrClass irClass3 = irClass;
                Fir2IrConverter fir2IrConverter = this.this$0;
                irConstructorCreateConstructor$default.setParent(irClass3);
                IrBlockBody irBlockBodyCreateBlockBody = fir2IrConverter.getIrFactory().createBlockBody(-1, -1);
                List<IrStatement> statements = irBlockBodyCreateBlockBody.getStatements();
                IrDelegatingConstructorCallImpl.Companion companion = IrDelegatingConstructorCallImpl.INSTANCE;
                IrType unitType = fir2IrConverter.getIrBuiltIns().getUnitType();
                for (Object obj : fir2IrConverter.getIrBuiltIns().getAnyClass().getOwner().getDeclarations()) {
                    if (obj instanceof IrConstructor) {
                        statements.add(IrDelegatingConstructorCallImpl.Companion.fromSymbolOwner$default(companion, -1, -1, unitType, ((IrConstructor) obj).getSymbol(), 0, 0, 48, null));
                        irConstructorCreateConstructor$default.setBody(irBlockBodyCreateBlockBody);
                        return irConstructorCreateConstructor$default;
                    }
                }
                throw new NoSuchElementException("No element of given type found");
            }
        });
        IrSimpleFunction irSimpleFunctionDeclareSimpleFunction = getSymbolTable().declareSimpleFunction(signature, new Function0<IrSimpleFunctionSymbol>() { // from class: org.jetbrains.kotlin.fir.backend.Fir2IrConverter$processCodeFragmentMembers$irFragmentFunction$1
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final IrSimpleFunctionSymbol invoke() {
                return new Fir2IrSimpleFunctionSymbol(signature);
            }
        }, new Function1<IrSimpleFunctionSymbol, IrSimpleFunction>() { // from class: org.jetbrains.kotlin.fir.backend.Fir2IrConverter$processCodeFragmentMembers$irFragmentFunction$2
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final IrSimpleFunction invoke(IrSimpleFunctionSymbol irSymbol) {
                IrType unitType;
                FirTypeRef typeRef;
                Intrinsics.checkNotNullParameter(irSymbol, "irSymbol");
                FirStatement firStatement = (FirStatement) CollectionsKt.lastOrNull((List) codeFragment.getBlock().getStatements());
                FirExpression firExpression = firStatement instanceof FirExpression ? (FirExpression) firStatement : null;
                if (firExpression == null || (typeRef = firExpression.getTypeRef()) == null || (unitType = Fir2IrTypeConverterKt.toIrType$default(typeRef, this.getTypeConverter(), null, 2, null)) == null) {
                    unitType = this.getIrBuiltIns().getUnitType();
                }
                IrFactory irFactory = this.getIrFactory();
                IrDeclarationOrigin.DEFINED defined = IrDeclarationOrigin.DEFINED.INSTANCE;
                Name methodName = conversionData.getMethodName();
                DescriptorVisibility PUBLIC = DescriptorVisibilities.PUBLIC;
                Intrinsics.checkNotNullExpressionValue(PUBLIC, "PUBLIC");
                IrSimpleFunction irSimpleFunctionCreateSimpleFunction = irFactory.createSimpleFunction(-1, -1, defined, methodName, PUBLIC, false, false, unitType, Modality.FINAL, irSymbol, false, false, false, false, false, null, false);
                IrClass irClass3 = irClass;
                CodeFragmentConversionData codeFragmentConversionData = conversionData;
                Fir2IrConverter fir2IrConverter = this;
                irSimpleFunctionCreateSimpleFunction.setParent(irClass3);
                List<InjectedValue> injectedValues = codeFragmentConversionData.getInjectedValues();
                ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(injectedValues, 10));
                int i = 0;
                for (Object obj : injectedValues) {
                    int i2 = i + 1;
                    if (i < 0) {
                        CollectionsKt.throwIndexOverflow();
                    }
                    InjectedValue injectedValue = (InjectedValue) obj;
                    boolean isMutated = injectedValue.getIsMutated();
                    IrFactory irFactory2 = fir2IrConverter.getIrFactory();
                    IrDeclarationOriginImpl irDeclarationOriginImpl = isMutated ? IrDeclarationOrigin.SHARED_VARIABLE_IN_EVALUATOR_FRAGMENT.INSTANCE : IrDeclarationOrigin.DEFINED.INSTANCE;
                    Name nameIdentifier = Name.identifier("p" + i);
                    Intrinsics.checkNotNullExpressionValue(nameIdentifier, "identifier(...)");
                    IrValueParameter irValueParameterCreateValueParameter = irFactory2.createValueParameter(-1, -1, (IrDeclarationOrigin) irDeclarationOriginImpl, nameIdentifier, Fir2IrTypeConverterKt.toIrType$default(injectedValue.getTypeRef(), fir2IrConverter.getTypeConverter(), null, 2, null), isMutated, injectedValue.getIrParameterSymbol(), i, (IrType) null, false, false, false);
                    irValueParameterCreateValueParameter.setParent(irSimpleFunctionCreateSimpleFunction);
                    arrayList.add(irValueParameterCreateValueParameter);
                    i = i2;
                }
                irSimpleFunctionCreateSimpleFunction.setValueParameters(arrayList);
                return irSimpleFunctionCreateSimpleFunction;
            }
        });
        irClass.getDeclarations().add(irConstructorDeclareConstructor);
        irClass.getDeclarations().add(irSimpleFunctionDeclareSimpleFunction);
        getDeclarationStorage().leaveScope(irClass2);
        return irClass;
    }

    private final void bindFakeOverridesInFile(FirFile file) {
        for (IrDeclaration irDeclaration : getDeclarationStorage().getIrFile(file).getDeclarations()) {
            if (irDeclaration instanceof IrClass) {
                bindFakeOverridesInClass((IrClass) irDeclaration);
            }
        }
    }

    public final void bindFakeOverridesInClass(IrClass klass) {
        Intrinsics.checkNotNullParameter(klass, "klass");
        getFakeOverrideGenerator().bindOverriddenSymbols(klass.getDeclarations());
        getDelegatedMemberGenerator().bindDelegatedMembersOverriddenSymbols(klass);
        for (IrDeclaration irDeclaration : klass.getDeclarations()) {
            if (irDeclaration instanceof IrClass) {
                bindFakeOverridesInClass((IrClass) irDeclaration);
            }
        }
    }

    private final List<FirDeclaration> delegatedMembers(IrClass irClass) {
        List<IrDeclaration> declarations = irClass.getDeclarations();
        ArrayList arrayList = new ArrayList();
        for (Object obj : declarations) {
            if (Intrinsics.areEqual(((IrDeclaration) obj).getOrigin(), IrDeclarationOrigin.DELEGATED_MEMBER.INSTANCE)) {
                arrayList.add(obj);
            }
        }
        ArrayList arrayList2 = new ArrayList();
        Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            FirDeclaration firDeclarationOriginalDeclarationForDelegated = this.components.getDeclarationStorage().originalDeclarationForDelegated((IrDeclaration) it.next());
            if (firDeclarationOriginalDeclarationForDelegated != null) {
                arrayList2.add(firDeclarationOriginalDeclarationForDelegated);
            }
        }
        return arrayList2;
    }

    private final IrClass registerClassAndNestedClasses(FirClass klass, IrDeclarationParent parent) {
        IrClass cachedIrClass = getClassifierStorage().getCachedIrClass(klass);
        if (cachedIrClass != null) {
            cachedIrClass.setParent(parent);
        } else if (klass instanceof FirRegularClass) {
            cachedIrClass = Fir2IrClassifierStorage.registerIrClass$default(getClassifierStorage(), (FirRegularClass) klass, parent, null, 4, null);
        } else {
            if (!(klass instanceof FirAnonymousObject)) {
                throw new NoWhenBranchMatchedException();
            }
            cachedIrClass = Fir2IrClassifierStorage.registerIrAnonymousObject$default(getClassifierStorage(), (FirAnonymousObject) klass, null, null, parent, 6, null);
        }
        registerNestedClasses(klass, cachedIrClass);
        return cachedIrClass;
    }

    private final void registerNestedClasses(FirClass klass, IrClass irClass) {
        for (FirDeclaration firDeclaration : klass.getDeclarations()) {
            if (firDeclaration instanceof FirRegularClass) {
                registerClassAndNestedClasses((FirClass) firDeclaration, irClass);
            }
        }
        if (!(klass instanceof FirRegularClass) || this.generatorExtensions.isEmpty()) {
            return;
        }
        for (FirClassLikeDeclaration firClassLikeDeclaration : GeneratedDeclarationsUtilsKt.generatedNestedClassifiers((FirRegularClass) klass, getSession())) {
            if (firClassLikeDeclaration instanceof FirRegularClass) {
                registerClassAndNestedClasses((FirClass) firClassLikeDeclaration, irClass);
            }
        }
    }

    private final void processClassAndNestedClassHeaders(FirClass klass) {
        Fir2IrClassifierStorage.processClassHeader$default(getClassifierStorage(), klass, null, 2, null);
        processNestedClassHeaders(klass);
    }

    private final void processNestedClassHeaders(FirClass klass) {
        for (FirDeclaration firDeclaration : klass.getDeclarations()) {
            if (firDeclaration instanceof FirRegularClass) {
                processClassAndNestedClassHeaders((FirClass) firDeclaration);
            }
        }
        if (!(klass instanceof FirRegularClass) || this.generatorExtensions.isEmpty()) {
            return;
        }
        for (FirClassLikeDeclaration firClassLikeDeclaration : GeneratedDeclarationsUtilsKt.generatedNestedClassifiers((FirRegularClass) klass, getSession())) {
            if (firClassLikeDeclaration instanceof FirRegularClass) {
                processClassAndNestedClassHeaders((FirClass) firClassLikeDeclaration);
            }
        }
    }

    private final IrDeclaration processMemberDeclaration(FirDeclaration declaration, FirClass containingClass, IrDeclarationParent parent) {
        boolean z = containingClass != null && (!(containingClass instanceof FirRegularClass) || containingClass.getSymbol().getClassId().isLocal());
        if (declaration instanceof FirRegularClass) {
            FirClass firClass = (FirClass) declaration;
            IrClass cachedIrClass = getClassifierStorage().getCachedIrClass(firClass);
            Intrinsics.checkNotNull(cachedIrClass);
            return processClassMembers$fir2ir(firClass, cachedIrClass);
        }
        IrDeclaration orCreateIrConstructor$default = null;
        if (declaration instanceof FirScript) {
            Intrinsics.checkNotNull(parent, "null cannot be cast to non-null type org.jetbrains.kotlin.ir.declarations.IrFile");
            FirScript firScript = (FirScript) declaration;
            IrScript orCreateIrScript = getDeclarationStorage().getOrCreateIrScript(firScript);
            IrScript irScript = orCreateIrScript;
            getDeclarationStorage().enterScope(irScript);
            orCreateIrScript.setParent(parent);
            for (FirStatement firStatement : firScript.getStatements()) {
                if (firStatement instanceof FirRegularClass) {
                    FirClass firClass2 = (FirClass) firStatement;
                    registerClassAndNestedClasses(firClass2, orCreateIrScript);
                    processClassAndNestedClassHeaders(firClass2);
                } else if (firStatement instanceof FirTypeAlias) {
                    getClassifierStorage().registerTypeAlias((FirTypeAlias) firStatement, orCreateIrScript);
                }
            }
            for (FirAnnotationContainer firAnnotationContainer : firScript.getStatements()) {
                if (firAnnotationContainer instanceof FirDeclaration) {
                    processMemberDeclaration((FirDeclaration) firAnnotationContainer, null, orCreateIrScript);
                }
            }
            getDeclarationStorage().leaveScope(irScript);
            return irScript;
        }
        if (declaration instanceof FirSimpleFunction) {
            return getDeclarationStorage().getOrCreateIrFunction((FirSimpleFunction) declaration, parent, z);
        }
        if (declaration instanceof FirProperty) {
            return (containingClass == null || !UtilsKt.isEnumEntries((FirVariable) declaration, containingClass) || FirLanguageSettingsComponentKt.getLanguageVersionSettings(getSession()).supportsFeature(LanguageFeature.EnumEntries)) ? getDeclarationStorage().getOrCreateIrProperty((FirProperty) declaration, parent, z) : null;
        }
        if (!(declaration instanceof FirField)) {
            if (declaration instanceof FirConstructor) {
                FirConstructor firConstructor = (FirConstructor) declaration;
                if (!firConstructor.getIsPrimary()) {
                    Fir2IrDeclarationStorage declarationStorage = getDeclarationStorage();
                    Intrinsics.checkNotNull(parent, "null cannot be cast to non-null type org.jetbrains.kotlin.ir.declarations.IrClass");
                    orCreateIrConstructor$default = Fir2IrDeclarationStorage.getOrCreateIrConstructor$default(declarationStorage, firConstructor, (IrClass) parent, null, z, 4, null);
                }
                return orCreateIrConstructor$default;
            }
            if (declaration instanceof FirEnumEntry) {
                Intrinsics.checkNotNull(parent, "null cannot be cast to non-null type org.jetbrains.kotlin.ir.declarations.IrClass");
                return Fir2IrClassifierStorage.getIrEnumEntry$default(getClassifierStorage(), (FirEnumEntry) declaration, (IrClass) parent, null, 4, null);
            }
            if (declaration instanceof FirAnonymousInitializer) {
                Intrinsics.checkNotNull(parent, "null cannot be cast to non-null type org.jetbrains.kotlin.ir.declarations.IrClass");
                return getDeclarationStorage().createIrAnonymousInitializer((FirAnonymousInitializer) declaration, (IrClass) parent);
            }
            if (declaration instanceof FirTypeAlias) {
                return null;
            }
            if (declaration instanceof FirCodeFragment) {
                return processCodeFragmentMembers$default(this, (FirCodeFragment) declaration, null, 2, null);
            }
            throw new IllegalStateException(("Unexpected member: " + Reflection.getOrCreateKotlinClass(declaration.getClass())).toString());
        }
        if (!(declaration.getOrigin() instanceof FirDeclarationOrigin.Synthetic)) {
            throw new AssertionError("Unexpected non-synthetic field: " + Reflection.getOrCreateKotlinClass(declaration.getClass()));
        }
        Intrinsics.checkNotNull(containingClass);
        Intrinsics.checkNotNull(parent, "null cannot be cast to non-null type org.jetbrains.kotlin.ir.declarations.IrClass");
        return getDeclarationStorage().createIrFieldAndDelegatedMembers((FirField) declaration, containingClass, (IrClass) parent);
    }

    /* JADX INFO: compiled from: Fir2IrConverter.kt */
    @Metadata(d1 = {"\u0000j\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002Jn\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\f\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u000b0\n2\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\u00152\u0006\u0010\u0016\u001a\u00020\u00172\u0006\u0010\u0018\u001a\u00020\u00192\u0006\u0010\u001a\u001a\u00020\u001b2\b\u0010\u001c\u001a\u0004\u0018\u00010\u001dJ\u0018\u0010\u001e\u001a\u00020\u001f2\u0006\u0010 \u001a\u00020!2\u0006\u0010\u000e\u001a\u00020\u000fH\u0002¨\u0006\""}, d2 = {"Lorg/jetbrains/kotlin/fir/backend/Fir2IrConverter$Companion;", "", "()V", "createModuleFragmentWithSignaturesIfNeeded", "Lorg/jetbrains/kotlin/fir/backend/Fir2IrResult;", "session", "Lorg/jetbrains/kotlin/fir/FirSession;", "scopeSession", "Lorg/jetbrains/kotlin/fir/resolve/ScopeSession;", "firFiles", "", "Lorg/jetbrains/kotlin/fir/declarations/FirFile;", "fir2IrExtensions", "Lorg/jetbrains/kotlin/fir/backend/Fir2IrExtensions;", "fir2IrConfiguration", "Lorg/jetbrains/kotlin/fir/backend/Fir2IrConfiguration;", "irMangler", "Lorg/jetbrains/kotlin/ir/util/KotlinMangler$IrMangler;", "irFactory", "Lorg/jetbrains/kotlin/ir/declarations/IrFactory;", "visibilityConverter", "Lorg/jetbrains/kotlin/fir/backend/Fir2IrVisibilityConverter;", "specialSymbolProvider", "Lorg/jetbrains/kotlin/fir/backend/Fir2IrSpecialSymbolProvider;", "kotlinBuiltIns", "Lorg/jetbrains/kotlin/builtins/KotlinBuiltIns;", "commonMemberStorage", "Lorg/jetbrains/kotlin/fir/backend/Fir2IrCommonMemberStorage;", "initializedIrBuiltIns", "Lorg/jetbrains/kotlin/fir/backend/IrBuiltInsOverFir;", "evaluateConstants", "", "irModuleFragment", "Lorg/jetbrains/kotlin/ir/declarations/IrModuleFragment;", "fir2ir"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        /* JADX INFO: Access modifiers changed from: private */
        /* JADX WARN: Multi-variable type inference failed */
        public final void evaluateConstants(IrModuleFragment irModuleFragment, Fir2IrConfiguration fir2IrConfiguration) {
            FirSession session;
            LanguageVersionSettings languageVersionSettings;
            ModuleDescriptor descriptor = irModuleFragment.getDescriptor();
            Map map = null;
            Object[] objArr = 0;
            FirModuleDescriptor firModuleDescriptor = descriptor instanceof FirModuleDescriptor ? (FirModuleDescriptor) descriptor : null;
            TargetPlatform platform = firModuleDescriptor != null ? firModuleDescriptor.getPlatform() : null;
            if (firModuleDescriptor == null || (session = firModuleDescriptor.getSession()) == null || (languageVersionSettings = FirLanguageSettingsComponentKt.getLanguageVersionSettings(session)) == null) {
                return;
            }
            boolean z = languageVersionSettings.supportsFeature(LanguageFeature.IntrinsicConstEvaluation);
            IrInterpreter irInterpreter = new IrInterpreter(new IrInterpreterEnvironment(irModuleFragment.getIrBuiltins(), new IrInterpreterConfiguration(platform, 0, 0, false, true, false, false, 110, null)), map, 2, (DefaultConstructorMarker) (objArr == true ? 1 : 0));
            EvaluationMode evaluationMode = z ? EvaluationMode.ONLY_INTRINSIC_CONST : EvaluationMode.ONLY_BUILTINS;
            final KtDiagnosticReporterWithImplicitIrBasedContext ktDiagnosticReporterWithImplicitIrBasedContext = new KtDiagnosticReporterWithImplicitIrBasedContext(fir2IrConfiguration.getDiagnosticReporter(), languageVersionSettings);
            Iterator<T> it = irModuleFragment.getFiles().iterator();
            while (it.hasNext()) {
                IrConstTransformerKt.transformConst((IrFile) it.next(), irInterpreter, evaluationMode, (80 & 4) != 0 ? null : fir2IrConfiguration.getEvaluatedConstTracker(), (80 & 8) != 0 ? null : fir2IrConfiguration.getInlineConstTracker(), (80 & 16) != 0 ? IrConstTransformerKt.AnonymousClass1.INSTANCE : null, (80 & 32) != 0 ? IrConstTransformerKt.AnonymousClass2.INSTANCE : new Function3<IrFile, IrElement, IrErrorExpression, Unit>() { // from class: org.jetbrains.kotlin.fir.backend.Fir2IrConverter$Companion$evaluateConstants$1$1
                    {
                        super(3);
                    }

                    @Override // kotlin.jvm.functions.Function3
                    public /* bridge */ /* synthetic */ Unit invoke(IrFile irFile, IrElement irElement, IrErrorExpression irErrorExpression) {
                        invoke2(irFile, irElement, irErrorExpression);
                        return Unit.INSTANCE;
                    }

                    /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
                    public final void invoke2(IrFile irFile, IrElement element, IrErrorExpression error) {
                        Intrinsics.checkNotNullParameter(irFile, "irFile");
                        Intrinsics.checkNotNullParameter(element, "element");
                        Intrinsics.checkNotNullParameter(error, "error");
                        ktDiagnosticReporterWithImplicitIrBasedContext.at(org.jetbrains.kotlin.backend.common.UtilsKt.sourceElement(element), element, irFile).report(CommonBackendErrors.INSTANCE.getEVALUATION_ERROR(), error.getDescription());
                    }
                }, (80 & 64) != 0 ? false : false);
            }
        }

        public final Fir2IrResult createModuleFragmentWithSignaturesIfNeeded(FirSession session, ScopeSession scopeSession, List<? extends FirFile> firFiles, Fir2IrExtensions fir2IrExtensions, Fir2IrConfiguration fir2IrConfiguration, KotlinMangler.IrMangler irMangler, IrFactory irFactory, Fir2IrVisibilityConverter visibilityConverter, Fir2IrSpecialSymbolProvider specialSymbolProvider, KotlinBuiltIns kotlinBuiltIns, Fir2IrCommonMemberStorage commonMemberStorage, IrBuiltInsOverFir initializedIrBuiltIns) {
            IrBuiltInsOverFir irBuiltInsOverFir;
            Intrinsics.checkNotNullParameter(session, "session");
            Intrinsics.checkNotNullParameter(scopeSession, "scopeSession");
            Intrinsics.checkNotNullParameter(firFiles, "firFiles");
            Intrinsics.checkNotNullParameter(fir2IrExtensions, "fir2IrExtensions");
            Intrinsics.checkNotNullParameter(fir2IrConfiguration, "fir2IrConfiguration");
            Intrinsics.checkNotNullParameter(irMangler, "irMangler");
            Intrinsics.checkNotNullParameter(irFactory, "irFactory");
            Intrinsics.checkNotNullParameter(visibilityConverter, "visibilityConverter");
            Intrinsics.checkNotNullParameter(specialSymbolProvider, "specialSymbolProvider");
            Intrinsics.checkNotNullParameter(kotlinBuiltIns, "kotlinBuiltIns");
            Intrinsics.checkNotNullParameter(commonMemberStorage, "commonMemberStorage");
            FirModuleDescriptor firModuleDescriptor = new FirModuleDescriptor(session, kotlinBuiltIns);
            Fir2IrComponentsStorage fir2IrComponentsStorage = new Fir2IrComponentsStorage(session, scopeSession, commonMemberStorage.getSymbolTable(), irFactory, commonMemberStorage.getFirSignatureComposer(), fir2IrExtensions, fir2IrConfiguration);
            Fir2IrComponentsStorage fir2IrComponentsStorage2 = fir2IrComponentsStorage;
            Fir2IrConverter fir2IrConverter = new Fir2IrConverter(firModuleDescriptor, fir2IrComponentsStorage2);
            fir2IrComponentsStorage.setConverter(fir2IrConverter);
            fir2IrComponentsStorage.setClassifierStorage(new Fir2IrClassifierStorage(fir2IrComponentsStorage2, commonMemberStorage));
            fir2IrComponentsStorage.setDelegatedMemberGenerator(new DelegatedMemberGenerator(fir2IrComponentsStorage2));
            fir2IrComponentsStorage.setDeclarationStorage(new Fir2IrDeclarationStorage(fir2IrComponentsStorage2, firModuleDescriptor, commonMemberStorage));
            fir2IrComponentsStorage.setVisibilityConverter(visibilityConverter);
            fir2IrComponentsStorage.setTypeConverter(new Fir2IrTypeConverter(fir2IrComponentsStorage2));
            if (initializedIrBuiltIns == null) {
                irBuiltInsOverFir = new IrBuiltInsOverFir(fir2IrComponentsStorage2, fir2IrConfiguration.getLanguageVersionSettings(), firModuleDescriptor, irMangler, ((Boolean) fir2IrConfiguration.getLanguageVersionSettings().getFlag(AnalysisFlags.getBuiltInsFromSources())).booleanValue() || kotlinBuiltIns != DefaultBuiltIns.INSTANCE.getInstance());
            } else {
                irBuiltInsOverFir = initializedIrBuiltIns;
            }
            fir2IrComponentsStorage.setIrBuiltIns(irBuiltInsOverFir);
            Fir2IrConversionScope fir2IrConversionScope = new Fir2IrConversionScope();
            Fir2IrVisitor fir2IrVisitor = new Fir2IrVisitor(fir2IrComponentsStorage2, fir2IrConversionScope);
            fir2IrComponentsStorage.setBuiltIns(new Fir2IrBuiltIns(fir2IrComponentsStorage2, specialSymbolProvider));
            fir2IrComponentsStorage.setAnnotationGenerator(new AnnotationGenerator(fir2IrComponentsStorage2));
            fir2IrComponentsStorage.setFakeOverrideGenerator(new FakeOverrideGenerator(fir2IrComponentsStorage2, fir2IrConversionScope));
            fir2IrComponentsStorage.setCallGenerator(new CallAndReferenceGenerator(fir2IrComponentsStorage2, fir2IrVisitor, fir2IrConversionScope));
            fir2IrComponentsStorage.setIrProviders(CollectionsKt.listOf(new FirIrProvider(fir2IrComponentsStorage2)));
            fir2IrComponentsStorage.setAnnotationsFromPluginRegistrar(new Fir2IrAnnotationsFromPluginRegistrar(fir2IrComponentsStorage2));
            fir2IrExtensions.registerDeclarations(commonMemberStorage.getSymbolTable());
            IrModuleFragmentImpl irModuleFragmentImpl = new IrModuleFragmentImpl(firModuleDescriptor, irBuiltInsOverFir, null, 4, null);
            List listCreateListBuilder = CollectionsKt.createListBuilder();
            listCreateListBuilder.addAll(firFiles);
            listCreateListBuilder.addAll(ConversionUtilsKt.createFilesWithGeneratedDeclarations(session));
            fir2IrConverter.runSourcesConversion(CollectionsKt.build(listCreateListBuilder), irModuleFragmentImpl, fir2IrVisitor, initializedIrBuiltIns == null);
            return new Fir2IrResult(irModuleFragmentImpl, fir2IrComponentsStorage2, firModuleDescriptor);
        }
    }

    private final Iterable<FirDeclaration> syntheticPropertiesLast(Iterable<? extends FirDeclaration> declarations) {
        return CollectionsKt.sortedWith(declarations, new Comparator() { // from class: org.jetbrains.kotlin.fir.backend.Fir2IrConverter$syntheticPropertiesLast$$inlined$sortedBy$1
            /* JADX WARN: Code duplicated, block: B:15:0x0031  */
            /* JADX WARN: Code duplicated, block: B:4:0x000d  */
            /* JADX WARN: Multi-variable type inference failed */
            @Override // java.util.Comparator
            public final int compare(T t, T t2) {
                boolean z;
                boolean z2;
                FirDeclaration firDeclaration = (FirDeclaration) t;
                if (firDeclaration.getOrigin() instanceof FirDeclarationOrigin.Synthetic) {
                    KtSourceElement source = firDeclaration.getSource();
                    if ((source != null ? source.getKind() : null) instanceof KtFakeSourceElementKind.ClassDelegationField) {
                        z = false;
                    } else {
                        z = true;
                    }
                } else {
                    z = false;
                }
                Boolean boolValueOf = Boolean.valueOf(z);
                FirDeclaration firDeclaration2 = (FirDeclaration) t2;
                if (firDeclaration2.getOrigin() instanceof FirDeclarationOrigin.Synthetic) {
                    KtSourceElement source2 = firDeclaration2.getSource();
                    z2 = (source2 != null ? source2.getKind() : null) instanceof KtFakeSourceElementKind.ClassDelegationField ? false : true;
                }
                return ComparisonsKt.compareValues(boolValueOf, Boolean.valueOf(z2));
            }
        });
    }
}
