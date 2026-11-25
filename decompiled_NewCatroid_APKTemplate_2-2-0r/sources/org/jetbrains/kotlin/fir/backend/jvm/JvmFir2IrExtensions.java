package org.jetbrains.kotlin.fir.backend.jvm;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import kotlin.Metadata;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.sequences.SequencesKt;
import org.jetbrains.kotlin.backend.common.serialization.signature.PublicIdSignatureComputer;
import org.jetbrains.kotlin.backend.jvm.CachedFieldsForObjectInstances;
import org.jetbrains.kotlin.backend.jvm.JvmFileFacadeClass;
import org.jetbrains.kotlin.backend.jvm.JvmGeneratorExtensions;
import org.jetbrains.kotlin.backend.jvm.JvmIrDeserializer;
import org.jetbrains.kotlin.backend.jvm.JvmSymbols;
import org.jetbrains.kotlin.config.CommonConfigurationKeysKt;
import org.jetbrains.kotlin.config.CompilerConfiguration;
import org.jetbrains.kotlin.config.JVMConfigurationKeys;
import org.jetbrains.kotlin.config.JvmSerializeIrMode;
import org.jetbrains.kotlin.fir.backend.Fir2IrComponents;
import org.jetbrains.kotlin.fir.backend.Fir2IrConversionScope;
import org.jetbrains.kotlin.fir.backend.Fir2IrExtensions;
import org.jetbrains.kotlin.fir.backend.InjectedValue;
import org.jetbrains.kotlin.fir.references.FirReference;
import org.jetbrains.kotlin.ir.IrBuiltIns;
import org.jetbrains.kotlin.ir.declarations.IrClass;
import org.jetbrains.kotlin.ir.declarations.IrConstructor;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationOrigin;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationOriginImpl;
import org.jetbrains.kotlin.ir.declarations.IrMemberWithContainerSource;
import org.jetbrains.kotlin.ir.declarations.IrPackageFragment;
import org.jetbrains.kotlin.ir.declarations.impl.IrExternalPackageFragmentImpl;
import org.jetbrains.kotlin.ir.declarations.impl.IrFactoryImpl;
import org.jetbrains.kotlin.ir.symbols.impl.DescriptorlessExternalPackageFragmentSymbol;
import org.jetbrains.kotlin.ir.util.IrUtilsKt;
import org.jetbrains.kotlin.ir.util.KotlinMangler;
import org.jetbrains.kotlin.ir.util.SymbolTable;
import org.jetbrains.kotlin.load.java.JvmAnnotationNames;
import org.jetbrains.kotlin.load.kotlin.FacadeClassSource;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.resolve.jvm.JvmClassName;
import org.jetbrains.kotlin.serialization.deserialization.descriptors.DeserializedContainerSource;

/* JADX INFO: compiled from: JvmFir2IrExtensions.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\u0094\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010%\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010!\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u00012\u00020\u0002B\u001d\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\u0006\u0010\u0007\u001a\u00020\b¢\u0006\u0002\u0010\tJ\u0018\u0010\"\u001a\u00020\u00102\u0006\u0010#\u001a\u00020$2\u0006\u0010%\u001a\u00020&H\u0002J\u0018\u0010'\u001a\u00020\u00152\u0006\u0010(\u001a\u00020\u00102\u0006\u0010)\u001a\u00020*H\u0016J\u001a\u0010+\u001a\u0004\u0018\u00010,2\u0006\u0010-\u001a\u00020.2\u0006\u0010/\u001a\u000200H\u0016J\u001a\u00101\u001a\u0004\u0018\u00010\u00102\u0006\u00102\u001a\u0002032\u0006\u0010)\u001a\u00020*H\u0016J\u0010\u00104\u001a\u0002052\u0006\u00106\u001a\u000207H\u0016R\u0014\u0010\n\u001a\u00020\u000bX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR \u0010\u000e\u001a\u000e\u0012\u0004\u0012\u00020\u0010\u0012\u0004\u0012\u00020\u00110\u000fX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u0013R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u0014\u001a\u00020\u0015X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0016\u0010\u0017R\u000e\u0010\u0018\u001a\u00020\u0019X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u001a\u001a\u00020\u0019X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u001b\u001a\u00020\u0010X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u001c\u001a\u00020\u001dX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u001e\u0010\u001fR\u0014\u0010 \u001a\b\u0012\u0004\u0012\u00020\u001d0!X\u0082\u0004¢\u0006\u0002\n\u0000¨\u00068"}, d2 = {"Lorg/jetbrains/kotlin/fir/backend/jvm/JvmFir2IrExtensions;", "Lorg/jetbrains/kotlin/fir/backend/Fir2IrExtensions;", "Lorg/jetbrains/kotlin/backend/jvm/JvmGeneratorExtensions;", "configuration", "Lorg/jetbrains/kotlin/config/CompilerConfiguration;", "irDeserializer", "Lorg/jetbrains/kotlin/backend/jvm/JvmIrDeserializer;", "mangler", "Lorg/jetbrains/kotlin/ir/util/KotlinMangler$IrMangler;", "(Lorg/jetbrains/kotlin/config/CompilerConfiguration;Lorg/jetbrains/kotlin/backend/jvm/JvmIrDeserializer;Lorg/jetbrains/kotlin/ir/util/KotlinMangler$IrMangler;)V", "cachedFields", "Lorg/jetbrains/kotlin/backend/jvm/CachedFieldsForObjectInstances;", "getCachedFields", "()Lorg/jetbrains/kotlin/backend/jvm/CachedFieldsForObjectInstances;", "classNameOverride", "", "Lorg/jetbrains/kotlin/ir/declarations/IrClass;", "Lorg/jetbrains/kotlin/resolve/jvm/JvmClassName;", "getClassNameOverride", "()Ljava/util/Map;", "irNeedsDeserialization", "", "getIrNeedsDeserialization", "()Z", "kotlinIrInternalPackage", "Lorg/jetbrains/kotlin/ir/declarations/impl/IrExternalPackageFragmentImpl;", "kotlinJvmInternalPackage", "rawTypeAnnotationClass", "rawTypeAnnotationConstructor", "Lorg/jetbrains/kotlin/ir/declarations/IrConstructor;", "getRawTypeAnnotationConstructor", "()Lorg/jetbrains/kotlin/ir/declarations/IrConstructor;", "specialAnnotationConstructors", "", "createSpecialAnnotationClass", "fqn", "Lorg/jetbrains/kotlin/name/FqName;", "parent", "Lorg/jetbrains/kotlin/ir/declarations/IrPackageFragment;", "deserializeToplevelClass", "irClass", "components", "Lorg/jetbrains/kotlin/fir/backend/Fir2IrComponents;", "findInjectedValue", "Lorg/jetbrains/kotlin/fir/backend/InjectedValue;", "calleeReference", "Lorg/jetbrains/kotlin/fir/references/FirReference;", "conversionScope", "Lorg/jetbrains/kotlin/fir/backend/Fir2IrConversionScope;", "generateOrGetFacadeClass", "declaration", "Lorg/jetbrains/kotlin/ir/declarations/IrMemberWithContainerSource;", "registerDeclarations", "", "symbolTable", "Lorg/jetbrains/kotlin/ir/util/SymbolTable;", "jvm-backend"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class JvmFir2IrExtensions implements JvmGeneratorExtensions, Fir2IrExtensions {
    private final CachedFieldsForObjectInstances cachedFields;
    private final Map<IrClass, JvmClassName> classNameOverride;
    private final JvmIrDeserializer irDeserializer;
    private final boolean irNeedsDeserialization;
    private final IrExternalPackageFragmentImpl kotlinIrInternalPackage;
    private final IrExternalPackageFragmentImpl kotlinJvmInternalPackage;
    private final KotlinMangler.IrMangler mangler;
    private final IrClass rawTypeAnnotationClass;
    private final IrConstructor rawTypeAnnotationConstructor;
    private final List<IrConstructor> specialAnnotationConstructors;

    @Override // org.jetbrains.kotlin.fir.backend.Fir2IrExtensions
    /* JADX INFO: renamed from: findInjectedValue */
    public InjectedValue mo6785findInjectedValue(FirReference calleeReference, Fir2IrConversionScope conversionScope) {
        Intrinsics.checkNotNullParameter(calleeReference, "calleeReference");
        Intrinsics.checkNotNullParameter(conversionScope, "conversionScope");
        return null;
    }

    public JvmFir2IrExtensions(CompilerConfiguration configuration, JvmIrDeserializer irDeserializer, KotlinMangler.IrMangler mangler) {
        Intrinsics.checkNotNullParameter(configuration, "configuration");
        Intrinsics.checkNotNullParameter(irDeserializer, "irDeserializer");
        Intrinsics.checkNotNullParameter(mangler, "mangler");
        this.irDeserializer = irDeserializer;
        this.mangler = mangler;
        this.classNameOverride = new LinkedHashMap();
        this.cachedFields = new CachedFieldsForObjectInstances(IrFactoryImpl.INSTANCE, CommonConfigurationKeysKt.getLanguageVersionSettings(configuration));
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
        this.rawTypeAnnotationConstructor = (IrConstructor) SequencesKt.single(IrUtilsKt.getConstructors(irClassCreateSpecialAnnotationClass));
        FqName ENHANCED_NULLABILITY_ANNOTATION = JvmAnnotationNames.ENHANCED_NULLABILITY_ANNOTATION;
        Intrinsics.checkNotNullExpressionValue(ENHANCED_NULLABILITY_ANNOTATION, "ENHANCED_NULLABILITY_ANNOTATION");
        createSpecialAnnotationClass(ENHANCED_NULLABILITY_ANNOTATION, irExternalPackageFragmentImpl2);
        createSpecialAnnotationClass(JvmSymbols.INSTANCE.getFLEXIBLE_NULLABILITY_ANNOTATION_FQ_NAME(), irExternalPackageFragmentImpl);
        createSpecialAnnotationClass(JvmSymbols.INSTANCE.getFLEXIBLE_MUTABILITY_ANNOTATION_FQ_NAME(), irExternalPackageFragmentImpl);
        this.irNeedsDeserialization = configuration.get(JVMConfigurationKeys.SERIALIZE_IR, JvmSerializeIrMode.NONE) != JvmSerializeIrMode.NONE;
    }

    @Override // org.jetbrains.kotlin.backend.jvm.JvmGeneratorExtensions
    public Map<IrClass, JvmClassName> getClassNameOverride() {
        return this.classNameOverride;
    }

    @Override // org.jetbrains.kotlin.backend.jvm.JvmGeneratorExtensions
    public CachedFieldsForObjectInstances getCachedFields() {
        return this.cachedFields;
    }

    @Override // org.jetbrains.kotlin.backend.jvm.JvmGeneratorExtensions
    public IrConstructor getRawTypeAnnotationConstructor() {
        return this.rawTypeAnnotationConstructor;
    }

    private final IrClass createSpecialAnnotationClass(FqName fqn, IrPackageFragment parent) {
        IrClass irClassCreateSpecialAnnotationClass = IrUtilsKt.createSpecialAnnotationClass(IrFactoryImpl.INSTANCE, fqn, parent);
        this.specialAnnotationConstructors.add((IrConstructor) SequencesKt.single(IrUtilsKt.getConstructors(irClassCreateSpecialAnnotationClass)));
        return irClassCreateSpecialAnnotationClass;
    }

    @Override // org.jetbrains.kotlin.fir.backend.Fir2IrExtensions
    public void registerDeclarations(SymbolTable symbolTable) {
        Intrinsics.checkNotNullParameter(symbolTable, "symbolTable");
        PublicIdSignatureComputer publicIdSignatureComputer = new PublicIdSignatureComputer(this.mangler);
        for (IrConstructor irConstructor : this.specialAnnotationConstructors) {
            symbolTable.declareConstructorWithSignature(publicIdSignatureComputer.composePublicIdSignature(irConstructor, false), irConstructor.getSymbol());
        }
    }

    @Override // org.jetbrains.kotlin.fir.backend.Fir2IrExtensions
    public boolean getIrNeedsDeserialization() {
        return this.irNeedsDeserialization;
    }

    @Override // org.jetbrains.kotlin.fir.backend.Fir2IrExtensions
    public IrClass generateOrGetFacadeClass(IrMemberWithContainerSource declaration, final Fir2IrComponents components) {
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        Intrinsics.checkNotNullParameter(components, "components");
        DeserializedContainerSource containerSource = declaration.getContainerSource();
        if (containerSource == null || !(containerSource instanceof FacadeClassSource)) {
            return null;
        }
        FacadeClassSource facadeClassSource = (FacadeClassSource) containerSource;
        JvmClassName facadeClassName = facadeClassSource.getFacadeClassName();
        if (facadeClassName == null) {
            facadeClassName = facadeClassSource.getClassName();
        }
        IrDeclarationOriginImpl irDeclarationOriginImpl = facadeClassSource.getFacadeClassName() != null ? IrDeclarationOrigin.JVM_MULTIFILE_CLASS.INSTANCE : IrDeclarationOrigin.FILE_CLASS.INSTANCE;
        Name nameShortName = facadeClassName.getFqNameForTopLevelClassMaybeWithDollars().shortName();
        Intrinsics.checkNotNullExpressionValue(nameShortName, "shortName(...)");
        JvmFileFacadeClass jvmFileFacadeClass = new JvmFileFacadeClass(irDeclarationOriginImpl, nameShortName, containerSource, new Function1<IrClass, Boolean>() { // from class: org.jetbrains.kotlin.fir.backend.jvm.JvmFir2IrExtensions.generateOrGetFacadeClass.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final Boolean invoke(IrClass irClass) {
                Intrinsics.checkNotNullParameter(irClass, "irClass");
                return Boolean.valueOf(JvmFir2IrExtensions.this.deserializeToplevelClass(irClass, components));
            }
        });
        JvmFileFacadeClass jvmFileFacadeClass2 = jvmFileFacadeClass;
        IrUtilsKt.createParameterDeclarations(jvmFileFacadeClass2);
        getClassNameOverride().put(jvmFileFacadeClass, facadeClassName);
        return jvmFileFacadeClass2;
    }

    @Override // org.jetbrains.kotlin.fir.backend.Fir2IrExtensions
    public boolean deserializeToplevelClass(IrClass irClass, Fir2IrComponents components) {
        Intrinsics.checkNotNullParameter(irClass, "irClass");
        Intrinsics.checkNotNullParameter(components, "components");
        return this.irDeserializer.deserializeTopLevelClass(irClass, components.getIrBuiltIns(), components.getSymbolTable(), components.getIrProviders(), this);
    }
}
