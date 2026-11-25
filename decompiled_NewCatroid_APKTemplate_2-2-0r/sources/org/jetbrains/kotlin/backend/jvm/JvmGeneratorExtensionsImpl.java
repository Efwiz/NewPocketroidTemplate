package org.jetbrains.kotlin.backend.jvm;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.sequences.SequencesKt;
import org.jetbrains.kotlin.backend.common.serialization.signature.PublicIdSignatureComputer;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.config.CommonConfigurationKeysKt;
import org.jetbrains.kotlin.config.CompilerConfiguration;
import org.jetbrains.kotlin.config.JVMConfigurationKeys;
import org.jetbrains.kotlin.config.JvmClosureGenerationScheme;
import org.jetbrains.kotlin.config.JvmSerializeIrMode;
import org.jetbrains.kotlin.descriptors.ClassConstructorDescriptor;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.descriptors.DescriptorVisibility;
import org.jetbrains.kotlin.descriptors.FunctionDescriptor;
import org.jetbrains.kotlin.descriptors.PropertyAccessorDescriptor;
import org.jetbrains.kotlin.descriptors.PropertyDescriptor;
import org.jetbrains.kotlin.descriptors.annotations.FilteredAnnotations;
import org.jetbrains.kotlin.incremental.components.NoLookupLocation;
import org.jetbrains.kotlin.ir.IrBuiltIns;
import org.jetbrains.kotlin.ir.backend.jvm.serialization.JvmIrMangler;
import org.jetbrains.kotlin.ir.declarations.IrClass;
import org.jetbrains.kotlin.ir.declarations.IrConstructor;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationOrigin;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationOriginImpl;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationParent;
import org.jetbrains.kotlin.ir.declarations.IrFactory;
import org.jetbrains.kotlin.ir.declarations.IrPackageFragment;
import org.jetbrains.kotlin.ir.declarations.impl.IrExternalPackageFragmentImpl;
import org.jetbrains.kotlin.ir.declarations.impl.IrFactoryImpl;
import org.jetbrains.kotlin.ir.expressions.IrDelegatingConstructorCall;
import org.jetbrains.kotlin.ir.expressions.impl.IrDelegatingConstructorCallImpl;
import org.jetbrains.kotlin.ir.symbols.impl.DescriptorlessExternalPackageFragmentSymbol;
import org.jetbrains.kotlin.ir.types.IrType;
import org.jetbrains.kotlin.ir.util.DeclarationStubGenerator;
import org.jetbrains.kotlin.ir.util.DescriptorSymbolTableExtension;
import org.jetbrains.kotlin.ir.util.IrUtilsKt;
import org.jetbrains.kotlin.ir.util.StubGeneratorExtensions;
import org.jetbrains.kotlin.ir.util.SymbolTable;
import org.jetbrains.kotlin.load.java.JvmAnnotationNames;
import org.jetbrains.kotlin.load.java.descriptors.JavaCallableMemberDescriptor;
import org.jetbrains.kotlin.load.java.descriptors.JavaClassDescriptor;
import org.jetbrains.kotlin.load.java.descriptors.UtilKt;
import org.jetbrains.kotlin.load.java.sam.JavaSingleAbstractMethodUtils;
import org.jetbrains.kotlin.load.java.typeEnhancement.TypeEnhancementKt;
import org.jetbrains.kotlin.load.kotlin.FacadeClassSource;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.psi.KtPureClassOrObject;
import org.jetbrains.kotlin.psi.psiUtil.PsiUtilsKt;
import org.jetbrains.kotlin.psi2ir.generators.GeneratorContext;
import org.jetbrains.kotlin.psi2ir.generators.GeneratorExtensions;
import org.jetbrains.kotlin.resolve.DescriptorUtils;
import org.jetbrains.kotlin.resolve.annotations.AnnotationUtilKt;
import org.jetbrains.kotlin.resolve.descriptorUtil.DescriptorUtilsKt;
import org.jetbrains.kotlin.resolve.jvm.JdkClassesKt;
import org.jetbrains.kotlin.resolve.jvm.JvmClassName;
import org.jetbrains.kotlin.resolve.jvm.annotations.JvmAnnotationUtilKt;
import org.jetbrains.kotlin.resolve.scopes.MemberScope;
import org.jetbrains.kotlin.serialization.deserialization.descriptors.DescriptorWithContainerSource;
import org.jetbrains.kotlin.serialization.deserialization.descriptors.DeserializedContainerSource;
import org.jetbrains.kotlin.synthetic.SyntheticJavaPropertyDescriptor;
import org.jetbrains.kotlin.types.KotlinType;
import org.jetbrains.kotlin.types.typeUtil.TypeUtilsKt;

/* JADX INFO: compiled from: JvmGeneratorExtensionsImpl.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000Ò\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010%\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010!\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0016\u0018\u00002\u00020\u00012\u00020\u0002:\u0002]^B\u0017\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\b\b\u0002\u0010\u0005\u001a\u00020\u0006¢\u0006\u0002\u0010\u0007J\u0012\u00104\u001a\u0004\u0018\u0001052\u0006\u00106\u001a\u000207H\u0016J\u0012\u00108\u001a\u0004\u0018\u0001092\u0006\u00106\u001a\u00020:H\u0016J\"\u0010;\u001a\u0004\u0018\u00010<2\u0006\u0010=\u001a\u00020>2\u0006\u00106\u001a\u00020?2\u0006\u0010@\u001a\u00020AH\u0016J\u0018\u0010B\u001a\u00020\u000e2\u0006\u0010C\u001a\u00020D2\u0006\u0010E\u001a\u00020FH\u0002J \u0010G\u001a\u00020\u00062\u0006\u0010H\u001a\u00020\u000e2\u0006\u0010I\u001a\u00020J2\u0006\u0010E\u001a\u00020KH\u0016J\"\u0010L\u001a\u0004\u0018\u00010\u000e2\u0006\u0010M\u001a\u00020N2\u0006\u0010O\u001a\u00020P2\u0006\u0010I\u001a\u00020JH\u0016J\u0012\u0010Q\u001a\u0004\u0018\u00010P2\u0006\u00106\u001a\u000207H\u0016J\u0012\u0010R\u001a\u0004\u0018\u00010S2\u0006\u00106\u001a\u00020?H\u0016J\u0010\u0010T\u001a\u00020\u00062\u0006\u00106\u001a\u00020:H\u0016J\u0010\u0010U\u001a\u00020\u00062\u0006\u00106\u001a\u00020VH\u0016J\u0010\u0010W\u001a\u00020X2\u0006\u0010Y\u001a\u00020ZH\u0016J \u0010[\u001a\u0012\u0012\u0004\u0012\u00020V\u0012\u0006\u0012\u0004\u0018\u00010V\u0018\u00010\\2\u0006\u00106\u001a\u00020:H\u0016R\u0014\u0010\b\u001a\u00020\tX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000bR \u0010\f\u001a\u000e\u0012\u0004\u0012\u00020\u000e\u0012\u0004\u0012\u00020\u000f0\rX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0011R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u0012\u001a\u00020\u00068VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0013\u0010\u0014R\u0014\u0010\u0015\u001a\u00020\u00168VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0017\u0010\u0018R\u000e\u0010\u0019\u001a\u00020\u000eX\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u001a\u001a\u00020\u001bX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u001c\u0010\u001dR\u0014\u0010\u001e\u001a\u00020\u001bX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u001f\u0010\u001dR\u0014\u0010 \u001a\u00020\u001bX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b!\u0010\u001dR\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\"\u001a\u00020\u0006X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b#\u0010\u0014R\u000e\u0010$\u001a\u00020%X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010&\u001a\u00020%X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010'\u001a\u00020\u00068VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b(\u0010\u0014R\u000e\u0010)\u001a\u00020\u000eX\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010*\u001a\u00020\u001bX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b+\u0010\u001dR\u0014\u0010,\u001a\u00020-X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b.\u0010/R\u0014\u00100\u001a\u00020\u00068VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b1\u0010\u0014R\u0014\u00102\u001a\b\u0012\u0004\u0012\u00020\u001b03X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006_"}, d2 = {"Lorg/jetbrains/kotlin/backend/jvm/JvmGeneratorExtensionsImpl;", "Lorg/jetbrains/kotlin/psi2ir/generators/GeneratorExtensions;", "Lorg/jetbrains/kotlin/backend/jvm/JvmGeneratorExtensions;", "configuration", "Lorg/jetbrains/kotlin/config/CompilerConfiguration;", "generateFacades", "", "(Lorg/jetbrains/kotlin/config/CompilerConfiguration;Z)V", "cachedFields", "Lorg/jetbrains/kotlin/backend/jvm/CachedFieldsForObjectInstances;", "getCachedFields", "()Lorg/jetbrains/kotlin/backend/jvm/CachedFieldsForObjectInstances;", "classNameOverride", "", "Lorg/jetbrains/kotlin/ir/declarations/IrClass;", "Lorg/jetbrains/kotlin/resolve/jvm/JvmClassName;", "getClassNameOverride", "()Ljava/util/Map;", "debugInfoOnlyOnVariablesInDestructuringDeclarations", "getDebugInfoOnlyOnVariablesInDestructuringDeclarations", "()Z", "enhancedNullability", "Lorg/jetbrains/kotlin/ir/util/StubGeneratorExtensions$EnhancedNullability;", "getEnhancedNullability", "()Lorg/jetbrains/kotlin/ir/util/StubGeneratorExtensions$EnhancedNullability;", "enhancedNullabilityAnnotationClass", "enhancedNullabilityAnnotationConstructor", "Lorg/jetbrains/kotlin/ir/declarations/IrConstructor;", "getEnhancedNullabilityAnnotationConstructor", "()Lorg/jetbrains/kotlin/ir/declarations/IrConstructor;", "flexibleMutabilityAnnotationConstructor", "getFlexibleMutabilityAnnotationConstructor", "flexibleNullabilityAnnotationConstructor", "getFlexibleNullabilityAnnotationConstructor", "irDeserializationEnabled", "getIrDeserializationEnabled", "kotlinIrInternalPackage", "Lorg/jetbrains/kotlin/ir/declarations/impl/IrExternalPackageFragmentImpl;", "kotlinJvmInternalPackage", "parametersAreAssignable", "getParametersAreAssignable", "rawTypeAnnotationClass", "rawTypeAnnotationConstructor", "getRawTypeAnnotationConstructor", "samConversion", "Lorg/jetbrains/kotlin/psi2ir/generators/GeneratorExtensions$SamConversion;", "getSamConversion", "()Lorg/jetbrains/kotlin/psi2ir/generators/GeneratorExtensions$SamConversion;", "shouldPreventDeprecatedIntegerValueTypeLiteralConversion", "getShouldPreventDeprecatedIntegerValueTypeLiteralConversion", "specialAnnotationConstructors", "", "computeExternalDeclarationOrigin", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationOrigin;", "descriptor", "Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;", "computeFieldVisibility", "Lorg/jetbrains/kotlin/descriptors/DescriptorVisibility;", "Lorg/jetbrains/kotlin/descriptors/PropertyDescriptor;", "createCustomSuperConstructorCall", "Lorg/jetbrains/kotlin/ir/expressions/IrDelegatingConstructorCall;", "ktPureClassOrObject", "Lorg/jetbrains/kotlin/psi/KtPureClassOrObject;", "Lorg/jetbrains/kotlin/descriptors/ClassDescriptor;", "context", "Lorg/jetbrains/kotlin/psi2ir/generators/GeneratorContext;", "createSpecialAnnotationClass", "fqn", "Lorg/jetbrains/kotlin/name/FqName;", "parent", "Lorg/jetbrains/kotlin/ir/declarations/IrPackageFragment;", "deserializeClass", "irClass", "stubGenerator", "Lorg/jetbrains/kotlin/ir/util/DeclarationStubGenerator;", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationParent;", "generateFacadeClass", "irFactory", "Lorg/jetbrains/kotlin/ir/declarations/IrFactory;", "deserializedSource", "Lorg/jetbrains/kotlin/serialization/deserialization/descriptors/DeserializedContainerSource;", "getContainerSource", "getParentClassStaticScope", "Lorg/jetbrains/kotlin/resolve/scopes/MemberScope;", "isPropertyWithPlatformField", "isStaticFunction", "Lorg/jetbrains/kotlin/descriptors/FunctionDescriptor;", "registerDeclarations", "", "symbolTable", "Lorg/jetbrains/kotlin/ir/util/SymbolTable;", "unwrapSyntheticJavaProperty", "Lkotlin/Pair;", "JvmEnhancedNullability", "JvmSamConversion", "backend.jvm.entrypoint"}, k = 1, mv = {1, 9, 0}, xi = 48)
public class JvmGeneratorExtensionsImpl extends GeneratorExtensions implements JvmGeneratorExtensions {
    private final CachedFieldsForObjectInstances cachedFields;
    private final Map<IrClass, JvmClassName> classNameOverride;
    private final CompilerConfiguration configuration;
    private final IrClass enhancedNullabilityAnnotationClass;
    private final IrConstructor enhancedNullabilityAnnotationConstructor;
    private final IrConstructor flexibleMutabilityAnnotationConstructor;
    private final IrConstructor flexibleNullabilityAnnotationConstructor;
    private final boolean generateFacades;
    private final boolean irDeserializationEnabled;
    private final IrExternalPackageFragmentImpl kotlinIrInternalPackage;
    private final IrExternalPackageFragmentImpl kotlinJvmInternalPackage;
    private final IrClass rawTypeAnnotationClass;
    private final IrConstructor rawTypeAnnotationConstructor;
    private final GeneratorExtensions.SamConversion samConversion;
    private final List<IrConstructor> specialAnnotationConstructors;

    @Override // org.jetbrains.kotlin.psi2ir.generators.GeneratorExtensions
    public boolean getDebugInfoOnlyOnVariablesInDestructuringDeclarations() {
        return true;
    }

    @Override // org.jetbrains.kotlin.psi2ir.generators.GeneratorExtensions
    public boolean getParametersAreAssignable() {
        return true;
    }

    @Override // org.jetbrains.kotlin.psi2ir.generators.GeneratorExtensions
    public boolean getShouldPreventDeprecatedIntegerValueTypeLiteralConversion() {
        return true;
    }

    public /* synthetic */ JvmGeneratorExtensionsImpl(CompilerConfiguration compilerConfiguration, boolean z, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(compilerConfiguration, (i & 2) != 0 ? true : z);
    }

    public JvmGeneratorExtensionsImpl(CompilerConfiguration configuration, boolean z) {
        Intrinsics.checkNotNullParameter(configuration, "configuration");
        this.configuration = configuration;
        this.generateFacades = z;
        this.classNameOverride = new LinkedHashMap();
        this.irDeserializationEnabled = configuration.get(JVMConfigurationKeys.SERIALIZE_IR) != JvmSerializeIrMode.NONE;
        this.cachedFields = new CachedFieldsForObjectInstances(IrFactoryImpl.INSTANCE, CommonConfigurationKeysKt.getLanguageVersionSettings(configuration));
        this.samConversion = new JvmSamConversion();
        IrExternalPackageFragmentImpl irExternalPackageFragmentImpl = new IrExternalPackageFragmentImpl(new DescriptorlessExternalPackageFragmentSymbol(), IrBuiltIns.INSTANCE.getKOTLIN_INTERNAL_IR_FQN());
        this.kotlinIrInternalPackage = irExternalPackageFragmentImpl;
        DescriptorlessExternalPackageFragmentSymbol descriptorlessExternalPackageFragmentSymbol = new DescriptorlessExternalPackageFragmentSymbol();
        FqName KOTLIN_JVM_INTERNAL = JvmAnnotationNames.KOTLIN_JVM_INTERNAL;
        Intrinsics.checkNotNullExpressionValue(KOTLIN_JVM_INTERNAL, "KOTLIN_JVM_INTERNAL");
        IrExternalPackageFragmentImpl irExternalPackageFragmentImpl2 = new IrExternalPackageFragmentImpl(descriptorlessExternalPackageFragmentSymbol, KOTLIN_JVM_INTERNAL);
        this.kotlinJvmInternalPackage = irExternalPackageFragmentImpl2;
        this.specialAnnotationConstructors = new ArrayList();
        IrClass irClassCreateSpecialAnnotationClass = createSpecialAnnotationClass(JvmSymbols.INSTANCE.getRAW_TYPE_ANNOTATION_FQ_NAME(), irExternalPackageFragmentImpl);
        this.rawTypeAnnotationClass = irClassCreateSpecialAnnotationClass;
        FqName ENHANCED_NULLABILITY_ANNOTATION = JvmAnnotationNames.ENHANCED_NULLABILITY_ANNOTATION;
        Intrinsics.checkNotNullExpressionValue(ENHANCED_NULLABILITY_ANNOTATION, "ENHANCED_NULLABILITY_ANNOTATION");
        IrClass irClassCreateSpecialAnnotationClass2 = createSpecialAnnotationClass(ENHANCED_NULLABILITY_ANNOTATION, irExternalPackageFragmentImpl2);
        this.enhancedNullabilityAnnotationClass = irClassCreateSpecialAnnotationClass2;
        this.flexibleNullabilityAnnotationConstructor = (IrConstructor) SequencesKt.single(IrUtilsKt.getConstructors(createSpecialAnnotationClass(JvmSymbols.INSTANCE.getFLEXIBLE_NULLABILITY_ANNOTATION_FQ_NAME(), irExternalPackageFragmentImpl)));
        this.flexibleMutabilityAnnotationConstructor = (IrConstructor) SequencesKt.single(IrUtilsKt.getConstructors(createSpecialAnnotationClass(JvmSymbols.INSTANCE.getFLEXIBLE_MUTABILITY_ANNOTATION_FQ_NAME(), irExternalPackageFragmentImpl)));
        this.enhancedNullabilityAnnotationConstructor = (IrConstructor) SequencesKt.single(IrUtilsKt.getConstructors(irClassCreateSpecialAnnotationClass2));
        this.rawTypeAnnotationConstructor = (IrConstructor) SequencesKt.single(IrUtilsKt.getConstructors(irClassCreateSpecialAnnotationClass));
    }

    @Override // org.jetbrains.kotlin.backend.jvm.JvmGeneratorExtensions
    public Map<IrClass, JvmClassName> getClassNameOverride() {
        return this.classNameOverride;
    }

    @Override // org.jetbrains.kotlin.ir.util.StubGeneratorExtensions
    public boolean getIrDeserializationEnabled() {
        return this.irDeserializationEnabled;
    }

    @Override // org.jetbrains.kotlin.backend.jvm.JvmGeneratorExtensions
    public CachedFieldsForObjectInstances getCachedFields() {
        return this.cachedFields;
    }

    @Override // org.jetbrains.kotlin.psi2ir.generators.GeneratorExtensions
    public GeneratorExtensions.SamConversion getSamConversion() {
        return this.samConversion;
    }

    /* JADX INFO: compiled from: JvmGeneratorExtensionsImpl.kt */
    @Metadata(d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\b\u0086\u0004\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\b\u0010\u0003\u001a\u00020\u0004H\u0016J\u0010\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0006\u001a\u00020\u0007H\u0016¨\u0006\b"}, d2 = {"Lorg/jetbrains/kotlin/backend/jvm/JvmGeneratorExtensionsImpl$JvmSamConversion;", "Lorg/jetbrains/kotlin/psi2ir/generators/GeneratorExtensions$SamConversion;", "(Lorg/jetbrains/kotlin/backend/jvm/JvmGeneratorExtensionsImpl;)V", "isCarefulApproximationOfContravariantProjection", "", "isPlatformSamType", ModuleXmlParser.TYPE, "Lorg/jetbrains/kotlin/types/KotlinType;", "backend.jvm.entrypoint"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public final class JvmSamConversion extends GeneratorExtensions.SamConversion {
        public JvmSamConversion() {
        }

        @Override // org.jetbrains.kotlin.psi2ir.generators.GeneratorExtensions.SamConversion
        public boolean isPlatformSamType(KotlinType type) {
            Intrinsics.checkNotNullParameter(type, "type");
            return JavaSingleAbstractMethodUtils.isSamType(type);
        }

        @Override // org.jetbrains.kotlin.psi2ir.generators.GeneratorExtensions.SamConversion
        public boolean isCarefulApproximationOfContravariantProjection() {
            return JvmGeneratorExtensionsImpl.this.configuration.get(JVMConfigurationKeys.SAM_CONVERSIONS) != JvmClosureGenerationScheme.CLASS;
        }
    }

    @Override // org.jetbrains.kotlin.ir.util.StubGeneratorExtensions
    public DeserializedContainerSource getContainerSource(DeclarationDescriptor descriptor) {
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        DescriptorWithContainerSource descriptorWithContainerSource = descriptor instanceof DescriptorWithContainerSource ? (DescriptorWithContainerSource) descriptor : null;
        if (descriptorWithContainerSource != null) {
            return descriptorWithContainerSource.getContainerSource();
        }
        return null;
    }

    @Override // org.jetbrains.kotlin.psi2ir.generators.GeneratorExtensions
    public DescriptorVisibility computeFieldVisibility(PropertyDescriptor descriptor) {
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        if (JvmAnnotationUtilKt.hasJvmFieldAnnotation(descriptor) || (descriptor instanceof JavaCallableMemberDescriptor)) {
            return descriptor.getVisibility();
        }
        return null;
    }

    @Override // org.jetbrains.kotlin.ir.util.StubGeneratorExtensions
    public IrDeclarationOrigin computeExternalDeclarationOrigin(DeclarationDescriptor descriptor) {
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        if ((descriptor instanceof JavaCallableMemberDescriptor) || (descriptor instanceof JavaClassDescriptor)) {
            return IrDeclarationOrigin.IR_EXTERNAL_JAVA_DECLARATION_STUB.INSTANCE;
        }
        return IrDeclarationOrigin.IR_EXTERNAL_DECLARATION_STUB.INSTANCE;
    }

    @Override // org.jetbrains.kotlin.ir.util.StubGeneratorExtensions
    public IrClass generateFacadeClass(IrFactory irFactory, DeserializedContainerSource deserializedSource, final DeclarationStubGenerator stubGenerator) {
        Intrinsics.checkNotNullParameter(irFactory, "irFactory");
        Intrinsics.checkNotNullParameter(deserializedSource, "deserializedSource");
        Intrinsics.checkNotNullParameter(stubGenerator, "stubGenerator");
        if (!this.generateFacades || !(deserializedSource instanceof FacadeClassSource)) {
            return null;
        }
        FacadeClassSource facadeClassSource = (FacadeClassSource) deserializedSource;
        JvmClassName facadeClassName = facadeClassSource.getFacadeClassName();
        if (facadeClassName == null) {
            facadeClassName = facadeClassSource.getClassName();
        }
        IrDeclarationOriginImpl irDeclarationOriginImpl = facadeClassSource.getFacadeClassName() != null ? IrDeclarationOrigin.JVM_MULTIFILE_CLASS.INSTANCE : IrDeclarationOrigin.FILE_CLASS.INSTANCE;
        Name nameShortName = facadeClassName.getFqNameForTopLevelClassMaybeWithDollars().shortName();
        Intrinsics.checkNotNullExpressionValue(nameShortName, "shortName(...)");
        JvmFileFacadeClass jvmFileFacadeClass = new JvmFileFacadeClass(irDeclarationOriginImpl, nameShortName, deserializedSource, new Function1<IrClass, Boolean>() { // from class: org.jetbrains.kotlin.backend.jvm.JvmGeneratorExtensionsImpl.generateFacadeClass.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final Boolean invoke(IrClass facade) {
                Intrinsics.checkNotNullParameter(facade, "facade");
                return Boolean.valueOf(JvmGeneratorExtensionsImpl.this.deserializeClass(facade, stubGenerator, facade.getParent()));
            }
        });
        JvmFileFacadeClass jvmFileFacadeClass2 = jvmFileFacadeClass;
        IrUtilsKt.createParameterDeclarations(jvmFileFacadeClass2);
        getClassNameOverride().put(jvmFileFacadeClass, facadeClassName);
        return jvmFileFacadeClass2;
    }

    @Override // org.jetbrains.kotlin.ir.util.StubGeneratorExtensions
    public boolean deserializeClass(IrClass irClass, DeclarationStubGenerator stubGenerator, IrDeclarationParent parent) {
        Intrinsics.checkNotNullParameter(irClass, "irClass");
        Intrinsics.checkNotNullParameter(stubGenerator, "stubGenerator");
        Intrinsics.checkNotNullParameter(parent, "parent");
        return new JvmIrDeserializerImpl().deserializeTopLevelClass(irClass, stubGenerator.getIrBuiltIns(), stubGenerator.getSymbolTable(), CollectionsKt.listOf(stubGenerator), this);
    }

    @Override // org.jetbrains.kotlin.ir.util.StubGeneratorExtensions
    public boolean isPropertyWithPlatformField(PropertyDescriptor descriptor) {
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        return JvmAnnotationUtilKt.hasJvmFieldAnnotation(descriptor);
    }

    @Override // org.jetbrains.kotlin.ir.util.StubGeneratorExtensions
    public boolean isStaticFunction(FunctionDescriptor descriptor) {
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        if (DescriptorUtils.isNonCompanionObject(descriptor.getContainingModule())) {
            if (!AnnotationUtilKt.hasJvmStaticAnnotation(descriptor)) {
                if (descriptor instanceof PropertyAccessorDescriptor) {
                    PropertyDescriptor correspondingProperty = ((PropertyAccessorDescriptor) descriptor).getCorrespondingProperty();
                    Intrinsics.checkNotNullExpressionValue(correspondingProperty, "getCorrespondingProperty(...)");
                    if (AnnotationUtilKt.hasJvmStaticAnnotation(correspondingProperty)) {
                    }
                }
            }
            return true;
        }
        return false;
    }

    @Override // org.jetbrains.kotlin.ir.util.StubGeneratorExtensions
    public StubGeneratorExtensions.EnhancedNullability getEnhancedNullability() {
        return JvmEnhancedNullability.INSTANCE;
    }

    /* JADX INFO: compiled from: JvmGeneratorExtensionsImpl.kt */
    @Metadata(d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0016\u0018\u0000 \b2\u00020\u0001:\u0001\bB\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0016J\u0010\u0010\u0007\u001a\u00020\u00062\u0006\u0010\u0005\u001a\u00020\u0006H\u0016¨\u0006\t"}, d2 = {"Lorg/jetbrains/kotlin/backend/jvm/JvmGeneratorExtensionsImpl$JvmEnhancedNullability;", "Lorg/jetbrains/kotlin/ir/util/StubGeneratorExtensions$EnhancedNullability;", "()V", "hasEnhancedNullability", "", "kotlinType", "Lorg/jetbrains/kotlin/types/KotlinType;", "stripEnhancedNullability", "Instance", "backend.jvm.entrypoint"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static class JvmEnhancedNullability extends StubGeneratorExtensions.EnhancedNullability {

        /* JADX INFO: renamed from: Instance, reason: from kotlin metadata */
        public static final Companion INSTANCE = new Companion(null);

        @Override // org.jetbrains.kotlin.ir.util.StubGeneratorExtensions.EnhancedNullability
        public boolean hasEnhancedNullability(KotlinType kotlinType) {
            Intrinsics.checkNotNullParameter(kotlinType, "kotlinType");
            return TypeEnhancementKt.hasEnhancedNullability(kotlinType);
        }

        @Override // org.jetbrains.kotlin.ir.util.StubGeneratorExtensions.EnhancedNullability
        public KotlinType stripEnhancedNullability(KotlinType kotlinType) {
            Intrinsics.checkNotNullParameter(kotlinType, "kotlinType");
            return TypeEnhancementKt.hasEnhancedNullability(kotlinType) ? TypeUtilsKt.replaceAnnotations(kotlinType, new FilteredAnnotations(kotlinType.getAnnotations(), true, new Function1<FqName, Boolean>() { // from class: org.jetbrains.kotlin.backend.jvm.JvmGeneratorExtensionsImpl$JvmEnhancedNullability$stripEnhancedNullability$1
                @Override // kotlin.jvm.functions.Function1
                public final Boolean invoke(FqName it) {
                    Intrinsics.checkNotNullParameter(it, "it");
                    return Boolean.valueOf(!Intrinsics.areEqual(it, JvmAnnotationNames.ENHANCED_NULLABILITY_ANNOTATION));
                }
            })) : kotlinType;
        }

        /* JADX INFO: renamed from: org.jetbrains.kotlin.backend.jvm.JvmGeneratorExtensionsImpl$JvmEnhancedNullability$Instance, reason: from kotlin metadata */
        /* JADX INFO: compiled from: JvmGeneratorExtensionsImpl.kt */
        @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002¨\u0006\u0003"}, d2 = {"Lorg/jetbrains/kotlin/backend/jvm/JvmGeneratorExtensionsImpl$JvmEnhancedNullability$Instance;", "Lorg/jetbrains/kotlin/backend/jvm/JvmGeneratorExtensionsImpl$JvmEnhancedNullability;", "()V", "backend.jvm.entrypoint"}, k = 1, mv = {1, 9, 0}, xi = 48)
        public static final class Companion extends JvmEnhancedNullability {
            public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
                this();
            }

            private Companion() {
            }
        }
    }

    @Override // org.jetbrains.kotlin.psi2ir.generators.GeneratorExtensions
    public MemberScope getParentClassStaticScope(ClassDescriptor descriptor) {
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        return UtilKt.getParentJavaStaticClassScope(descriptor);
    }

    private final IrClass createSpecialAnnotationClass(FqName fqn, IrPackageFragment parent) {
        IrClass irClassCreateSpecialAnnotationClass = IrUtilsKt.createSpecialAnnotationClass(IrFactoryImpl.INSTANCE, fqn, parent);
        this.specialAnnotationConstructors.add((IrConstructor) SequencesKt.single(IrUtilsKt.getConstructors(irClassCreateSpecialAnnotationClass)));
        return irClassCreateSpecialAnnotationClass;
    }

    @Override // org.jetbrains.kotlin.psi2ir.generators.GeneratorExtensions
    public IrDelegatingConstructorCall createCustomSuperConstructorCall(KtPureClassOrObject ktPureClassOrObject, ClassDescriptor descriptor, GeneratorContext context) {
        Intrinsics.checkNotNullParameter(ktPureClassOrObject, "ktPureClassOrObject");
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        Intrinsics.checkNotNullParameter(context, "context");
        if (!JvmAnnotationUtilKt.isJvmRecord(descriptor)) {
            return null;
        }
        ClassDescriptor classDescriptorResolveTopLevelClass = DescriptorUtilsKt.resolveTopLevelClass(DescriptorUtilsKt.getModule(descriptor), JdkClassesKt.getJAVA_LANG_RECORD_FQ_NAME(), NoLookupLocation.FROM_BACKEND);
        if (classDescriptorResolveTopLevelClass == null) {
            throw new IllegalStateException(("Class not found: " + JdkClassesKt.getJAVA_LANG_RECORD_FQ_NAME()).toString());
        }
        Collection<ClassConstructorDescriptor> constructors = classDescriptorResolveTopLevelClass.getConstructors();
        Intrinsics.checkNotNullExpressionValue(constructors, "getConstructors(...)");
        ClassConstructorDescriptor classConstructorDescriptor = (ClassConstructorDescriptor) CollectionsKt.single(constructors);
        IrDelegatingConstructorCallImpl.Companion companion = IrDelegatingConstructorCallImpl.INSTANCE;
        KtPureClassOrObject ktPureClassOrObject2 = ktPureClassOrObject;
        int pureStartOffset = PsiUtilsKt.getPureStartOffset(ktPureClassOrObject2);
        int pureEndOffset = PsiUtilsKt.getPureEndOffset(ktPureClassOrObject2);
        IrType unitType = context.getIrBuiltIns().getUnitType();
        DescriptorSymbolTableExtension descriptorExtension = context.getSymbolTable().getDescriptorExtension();
        Intrinsics.checkNotNull(classConstructorDescriptor);
        return IrDelegatingConstructorCallImpl.Companion.fromSymbolDescriptor$default(companion, pureStartOffset, pureEndOffset, unitType, descriptorExtension.referenceConstructor(classConstructorDescriptor), 0, 0, 48, null);
    }

    @Override // org.jetbrains.kotlin.ir.util.StubGeneratorExtensions
    public void registerDeclarations(SymbolTable symbolTable) {
        Intrinsics.checkNotNullParameter(symbolTable, "symbolTable");
        PublicIdSignatureComputer publicIdSignatureComputer = new PublicIdSignatureComputer(JvmIrMangler.INSTANCE);
        for (IrConstructor irConstructor : this.specialAnnotationConstructors) {
            symbolTable.declareConstructorWithSignature(publicIdSignatureComputer.composePublicIdSignature(irConstructor, false), irConstructor.getSymbol());
        }
        super.registerDeclarations(symbolTable);
    }

    @Override // org.jetbrains.kotlin.ir.util.StubGeneratorExtensions
    public IrConstructor getFlexibleNullabilityAnnotationConstructor() {
        return this.flexibleNullabilityAnnotationConstructor;
    }

    @Override // org.jetbrains.kotlin.ir.util.StubGeneratorExtensions
    public IrConstructor getFlexibleMutabilityAnnotationConstructor() {
        return this.flexibleMutabilityAnnotationConstructor;
    }

    @Override // org.jetbrains.kotlin.ir.util.StubGeneratorExtensions
    public IrConstructor getEnhancedNullabilityAnnotationConstructor() {
        return this.enhancedNullabilityAnnotationConstructor;
    }

    @Override // org.jetbrains.kotlin.ir.util.StubGeneratorExtensions, org.jetbrains.kotlin.backend.jvm.JvmGeneratorExtensions
    public IrConstructor getRawTypeAnnotationConstructor() {
        return this.rawTypeAnnotationConstructor;
    }

    @Override // org.jetbrains.kotlin.psi2ir.generators.GeneratorExtensions
    public Pair<FunctionDescriptor, FunctionDescriptor> unwrapSyntheticJavaProperty(PropertyDescriptor descriptor) {
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        if (!(descriptor instanceof SyntheticJavaPropertyDescriptor)) {
            return null;
        }
        SyntheticJavaPropertyDescriptor syntheticJavaPropertyDescriptor = (SyntheticJavaPropertyDescriptor) descriptor;
        return TuplesKt.to(syntheticJavaPropertyDescriptor.getGetMethod(), syntheticJavaPropertyDescriptor.getSetMethod());
    }
}
