package org.jetbrains.kotlin.fir.backend.generators;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.sequences.SequencesKt;
import org.eclipse.jdt.internal.compiler.impl.CompilerOptions;
import org.jetbrains.kotlin.builtins.StandardNames;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.descriptors.DescriptorVisibilities;
import org.jetbrains.kotlin.descriptors.DescriptorVisibility;
import org.jetbrains.kotlin.descriptors.Modality;
import org.jetbrains.kotlin.fir.ClassMembersKt;
import org.jetbrains.kotlin.fir.FirSession;
import org.jetbrains.kotlin.fir.UtilsKt;
import org.jetbrains.kotlin.fir.backend.ConversionUtilsKt;
import org.jetbrains.kotlin.fir.backend.Fir2IrAnnotationsFromPluginRegistrar;
import org.jetbrains.kotlin.fir.backend.Fir2IrBuiltIns;
import org.jetbrains.kotlin.fir.backend.Fir2IrClassifierStorage;
import org.jetbrains.kotlin.fir.backend.Fir2IrComponents;
import org.jetbrains.kotlin.fir.backend.Fir2IrConfiguration;
import org.jetbrains.kotlin.fir.backend.Fir2IrConverter;
import org.jetbrains.kotlin.fir.backend.Fir2IrDeclarationStorage;
import org.jetbrains.kotlin.fir.backend.Fir2IrExtensions;
import org.jetbrains.kotlin.fir.backend.Fir2IrTypeConverter;
import org.jetbrains.kotlin.fir.backend.Fir2IrVisibilityConverter;
import org.jetbrains.kotlin.fir.backend.FirMetadataSource;
import org.jetbrains.kotlin.fir.backend.IrBuiltInsOverFir;
import org.jetbrains.kotlin.fir.declarations.FirClass;
import org.jetbrains.kotlin.fir.declarations.FirDeclaration;
import org.jetbrains.kotlin.fir.declarations.FirDeclarationOrigin;
import org.jetbrains.kotlin.fir.declarations.FirRegularClass;
import org.jetbrains.kotlin.fir.declarations.FirResolvePhase;
import org.jetbrains.kotlin.fir.declarations.FirSimpleFunction;
import org.jetbrains.kotlin.fir.declarations.FirValueParameter;
import org.jetbrains.kotlin.fir.resolve.ScopeSession;
import org.jetbrains.kotlin.fir.scopes.FirKotlinScopeProviderKt;
import org.jetbrains.kotlin.fir.scopes.FirTypeScope;
import org.jetbrains.kotlin.fir.signaturer.FirBasedSignatureComposer;
import org.jetbrains.kotlin.fir.symbols.ConeClassLikeLookupTag;
import org.jetbrains.kotlin.fir.symbols.impl.FirNamedFunctionSymbol;
import org.jetbrains.kotlin.ir.IrLock;
import org.jetbrains.kotlin.ir.builders.IrGeneratorContextBase;
import org.jetbrains.kotlin.ir.declarations.IrClass;
import org.jetbrains.kotlin.ir.declarations.IrConstructor;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationOrigin;
import org.jetbrains.kotlin.ir.declarations.IrFactory;
import org.jetbrains.kotlin.ir.declarations.IrFunction;
import org.jetbrains.kotlin.ir.declarations.IrProperty;
import org.jetbrains.kotlin.ir.declarations.IrSimpleFunction;
import org.jetbrains.kotlin.ir.declarations.IrValueParameter;
import org.jetbrains.kotlin.ir.linkage.IrProvider;
import org.jetbrains.kotlin.ir.symbols.IrSimpleFunctionSymbol;
import org.jetbrains.kotlin.ir.symbols.IrValueParameterSymbol;
import org.jetbrains.kotlin.ir.symbols.impl.IrValueParameterSymbolImpl;
import org.jetbrains.kotlin.ir.types.IrType;
import org.jetbrains.kotlin.ir.util.AdditionalIrUtilsKt;
import org.jetbrains.kotlin.ir.util.IdSignature;
import org.jetbrains.kotlin.ir.util.IrUtilsKt;
import org.jetbrains.kotlin.ir.util.SymbolTable;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.resolve.DataClassResolver;
import org.jetbrains.kotlin.util.OperatorNameConventions;

/* JADX INFO: compiled from: DataClassMembersGenerator.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000à\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\u0018\u00002\u00020\u0001:\u0001jB\r\u0012\u0006\u0010\u0002\u001a\u00020\u0001¢\u0006\u0002\u0010\u0003J\u0016\u0010[\u001a\u00020\\2\u0006\u0010]\u001a\u00020^2\u0006\u0010_\u001a\u00020`J\u0016\u0010a\u001a\u00020\\2\u0006\u0010]\u001a\u00020^2\u0006\u0010_\u001a\u00020`J\u001c\u0010b\u001a\b\u0012\u0004\u0012\u00020c0;2\u0006\u0010d\u001a\u00020e2\u0006\u0010f\u001a\u00020gJ\u001c\u0010h\u001a\b\u0012\u0004\u0012\u00020c0;2\u0006\u0010d\u001a\u00020e2\u0006\u0010f\u001a\u00020gJ\u001c\u0010i\u001a\b\u0012\u0004\u0012\u00020c0;2\u0006\u0010d\u001a\u00020e2\u0006\u0010f\u001a\u00020gR\u0012\u0010\u0004\u001a\u00020\u0005X\u0096\u0005¢\u0006\u0006\u001a\u0004\b\u0006\u0010\u0007R\u0012\u0010\b\u001a\u00020\tX\u0096\u0005¢\u0006\u0006\u001a\u0004\b\n\u0010\u000bR\u0012\u0010\f\u001a\u00020\rX\u0096\u0005¢\u0006\u0006\u001a\u0004\b\u000e\u0010\u000fR\u0012\u0010\u0010\u001a\u00020\u0011X\u0096\u0005¢\u0006\u0006\u001a\u0004\b\u0012\u0010\u0013R\u0012\u0010\u0014\u001a\u00020\u0015X\u0096\u0005¢\u0006\u0006\u001a\u0004\b\u0016\u0010\u0017R\u0011\u0010\u0002\u001a\u00020\u0001¢\u0006\b\n\u0000\u001a\u0004\b\u0018\u0010\u0019R\u0012\u0010\u001a\u001a\u00020\u001bX\u0096\u0005¢\u0006\u0006\u001a\u0004\b\u001c\u0010\u001dR\u0012\u0010\u001e\u001a\u00020\u001fX\u0096\u0005¢\u0006\u0006\u001a\u0004\b \u0010!R\u0012\u0010\"\u001a\u00020#X\u0096\u0005¢\u0006\u0006\u001a\u0004\b$\u0010%R\u0012\u0010&\u001a\u00020'X\u0096\u0005¢\u0006\u0006\u001a\u0004\b(\u0010)R\u0012\u0010*\u001a\u00020+X\u0096\u0005¢\u0006\u0006\u001a\u0004\b,\u0010-R\u0012\u0010.\u001a\u00020/X\u0096\u0005¢\u0006\u0006\u001a\u0004\b0\u00101R\u0012\u00102\u001a\u000203X\u0096\u0005¢\u0006\u0006\u001a\u0004\b4\u00105R\u0012\u00106\u001a\u000207X\u0096\u0005¢\u0006\u0006\u001a\u0004\b8\u00109R\u0018\u0010:\u001a\b\u0012\u0004\u0012\u00020<0;X\u0096\u0005¢\u0006\u0006\u001a\u0004\b=\u0010>R\u0012\u0010?\u001a\u00020@X\u0096\u0005¢\u0006\u0006\u001a\u0004\bA\u0010BR\u0012\u0010C\u001a\u00020DX\u0096\u0005¢\u0006\u0006\u001a\u0004\bE\u0010FR\u0012\u0010G\u001a\u00020HX\u0096\u0005¢\u0006\u0006\u001a\u0004\bI\u0010JR\u0012\u0010K\u001a\u00020LX\u0096\u0005¢\u0006\u0006\u001a\u0004\bM\u0010NR\u0012\u0010O\u001a\u00020PX\u0096\u0005¢\u0006\u0006\u001a\u0004\bQ\u0010RR\u0012\u0010S\u001a\u00020TX\u0096\u0005¢\u0006\u0006\u001a\u0004\bU\u0010VR\u0012\u0010W\u001a\u00020XX\u0096\u0005¢\u0006\u0006\u001a\u0004\bY\u0010Z¨\u0006k"}, d2 = {"Lorg/jetbrains/kotlin/fir/backend/generators/DataClassMembersGenerator;", "Lorg/jetbrains/kotlin/fir/backend/Fir2IrComponents;", "components", "(Lorg/jetbrains/kotlin/fir/backend/Fir2IrComponents;)V", "annotationGenerator", "Lorg/jetbrains/kotlin/fir/backend/generators/AnnotationGenerator;", "getAnnotationGenerator", "()Lorg/jetbrains/kotlin/fir/backend/generators/AnnotationGenerator;", "annotationsFromPluginRegistrar", "Lorg/jetbrains/kotlin/fir/backend/Fir2IrAnnotationsFromPluginRegistrar;", "getAnnotationsFromPluginRegistrar", "()Lorg/jetbrains/kotlin/fir/backend/Fir2IrAnnotationsFromPluginRegistrar;", "builtIns", "Lorg/jetbrains/kotlin/fir/backend/Fir2IrBuiltIns;", "getBuiltIns", "()Lorg/jetbrains/kotlin/fir/backend/Fir2IrBuiltIns;", "callGenerator", "Lorg/jetbrains/kotlin/fir/backend/generators/CallAndReferenceGenerator;", "getCallGenerator", "()Lorg/jetbrains/kotlin/fir/backend/generators/CallAndReferenceGenerator;", "classifierStorage", "Lorg/jetbrains/kotlin/fir/backend/Fir2IrClassifierStorage;", "getClassifierStorage", "()Lorg/jetbrains/kotlin/fir/backend/Fir2IrClassifierStorage;", "getComponents", "()Lorg/jetbrains/kotlin/fir/backend/Fir2IrComponents;", "configuration", "Lorg/jetbrains/kotlin/fir/backend/Fir2IrConfiguration;", "getConfiguration", "()Lorg/jetbrains/kotlin/fir/backend/Fir2IrConfiguration;", "converter", "Lorg/jetbrains/kotlin/fir/backend/Fir2IrConverter;", "getConverter", "()Lorg/jetbrains/kotlin/fir/backend/Fir2IrConverter;", "declarationStorage", "Lorg/jetbrains/kotlin/fir/backend/Fir2IrDeclarationStorage;", "getDeclarationStorage", "()Lorg/jetbrains/kotlin/fir/backend/Fir2IrDeclarationStorage;", "delegatedMemberGenerator", "Lorg/jetbrains/kotlin/fir/backend/generators/DelegatedMemberGenerator;", "getDelegatedMemberGenerator", "()Lorg/jetbrains/kotlin/fir/backend/generators/DelegatedMemberGenerator;", "extensions", "Lorg/jetbrains/kotlin/fir/backend/Fir2IrExtensions;", "getExtensions", "()Lorg/jetbrains/kotlin/fir/backend/Fir2IrExtensions;", "fakeOverrideGenerator", "Lorg/jetbrains/kotlin/fir/backend/generators/FakeOverrideGenerator;", "getFakeOverrideGenerator", "()Lorg/jetbrains/kotlin/fir/backend/generators/FakeOverrideGenerator;", "irBuiltIns", "Lorg/jetbrains/kotlin/fir/backend/IrBuiltInsOverFir;", "getIrBuiltIns", "()Lorg/jetbrains/kotlin/fir/backend/IrBuiltInsOverFir;", "irFactory", "Lorg/jetbrains/kotlin/ir/declarations/IrFactory;", "getIrFactory", "()Lorg/jetbrains/kotlin/ir/declarations/IrFactory;", "irProviders", "", "Lorg/jetbrains/kotlin/ir/linkage/IrProvider;", "getIrProviders", "()Ljava/util/List;", "lock", "Lorg/jetbrains/kotlin/ir/IrLock;", "getLock", "()Lorg/jetbrains/kotlin/ir/IrLock;", "scopeSession", "Lorg/jetbrains/kotlin/fir/resolve/ScopeSession;", "getScopeSession", "()Lorg/jetbrains/kotlin/fir/resolve/ScopeSession;", "session", "Lorg/jetbrains/kotlin/fir/FirSession;", "getSession", "()Lorg/jetbrains/kotlin/fir/FirSession;", "signatureComposer", "Lorg/jetbrains/kotlin/fir/signaturer/FirBasedSignatureComposer;", "getSignatureComposer", "()Lorg/jetbrains/kotlin/fir/signaturer/FirBasedSignatureComposer;", "symbolTable", "Lorg/jetbrains/kotlin/ir/util/SymbolTable;", "getSymbolTable", "()Lorg/jetbrains/kotlin/ir/util/SymbolTable;", "typeConverter", "Lorg/jetbrains/kotlin/fir/backend/Fir2IrTypeConverter;", "getTypeConverter", "()Lorg/jetbrains/kotlin/fir/backend/Fir2IrTypeConverter;", "visibilityConverter", "Lorg/jetbrains/kotlin/fir/backend/Fir2IrVisibilityConverter;", "getVisibilityConverter", "()Lorg/jetbrains/kotlin/fir/backend/Fir2IrVisibilityConverter;", "generateDataClassComponentBody", "", "irFunction", "Lorg/jetbrains/kotlin/ir/declarations/IrFunction;", "lookupTag", "Lorg/jetbrains/kotlin/fir/symbols/ConeClassLikeLookupTag;", "generateDataClassCopyBody", "generateDataClassMembers", "Lorg/jetbrains/kotlin/fir/declarations/FirDeclaration;", "klass", "Lorg/jetbrains/kotlin/fir/declarations/FirRegularClass;", "irClass", "Lorg/jetbrains/kotlin/ir/declarations/IrClass;", "generateMultiFieldValueClassMembers", "generateSingleFieldValueClassMembers", "MyDataClassMethodsGenerator", "fir2ir"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class DataClassMembersGenerator implements Fir2IrComponents {
    private final Fir2IrComponents components;

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

    public DataClassMembersGenerator(Fir2IrComponents components) {
        Intrinsics.checkNotNullParameter(components, "components");
        this.components = components;
    }

    public final Fir2IrComponents getComponents() {
        return this.components;
    }

    public final List<FirDeclaration> generateSingleFieldValueClassMembers(FirRegularClass klass, IrClass irClass) {
        Intrinsics.checkNotNullParameter(klass, "klass");
        Intrinsics.checkNotNullParameter(irClass, "irClass");
        return new MyDataClassMethodsGenerator(this, irClass, klass.getSymbol().getLookupTag(), IrDeclarationOrigin.GENERATED_SINGLE_FIELD_VALUE_CLASS_MEMBER.INSTANCE).generate(klass);
    }

    public final List<FirDeclaration> generateMultiFieldValueClassMembers(FirRegularClass klass, IrClass irClass) {
        Intrinsics.checkNotNullParameter(klass, "klass");
        Intrinsics.checkNotNullParameter(irClass, "irClass");
        return new MyDataClassMethodsGenerator(this, irClass, klass.getSymbol().getLookupTag(), IrDeclarationOrigin.GENERATED_MULTI_FIELD_VALUE_CLASS_MEMBER.INSTANCE).generate(klass);
    }

    public final List<FirDeclaration> generateDataClassMembers(FirRegularClass klass, IrClass irClass) {
        Intrinsics.checkNotNullParameter(klass, "klass");
        Intrinsics.checkNotNullParameter(irClass, "irClass");
        return new MyDataClassMethodsGenerator(this, irClass, klass.getSymbol().getLookupTag(), IrDeclarationOrigin.GENERATED_DATA_CLASS_MEMBER.INSTANCE).generate(klass);
    }

    public final void generateDataClassComponentBody(IrFunction irFunction, ConeClassLikeLookupTag lookupTag) {
        Intrinsics.checkNotNullParameter(irFunction, "irFunction");
        Intrinsics.checkNotNullParameter(lookupTag, "lookupTag");
        new MyDataClassMethodsGenerator(this, IrUtilsKt.getParentAsClass(irFunction), lookupTag, IrDeclarationOrigin.GENERATED_DATA_CLASS_MEMBER.INSTANCE).generateComponentBody(irFunction);
    }

    public final void generateDataClassCopyBody(IrFunction irFunction, ConeClassLikeLookupTag lookupTag) {
        Intrinsics.checkNotNullParameter(irFunction, "irFunction");
        Intrinsics.checkNotNullParameter(lookupTag, "lookupTag");
        new MyDataClassMethodsGenerator(this, IrUtilsKt.getParentAsClass(irFunction), lookupTag, IrDeclarationOrigin.GENERATED_DATA_CLASS_MEMBER.INSTANCE).generateCopyBody(irFunction);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: compiled from: DataClassMembersGenerator.kt */
    @Metadata(d1 = {"\u0000k\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003*\u0001\f\b\u0082\u0004\u0018\u00002\u00020\u0001B\u001d\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007¢\u0006\u0002\u0010\bJ4\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\u00152\u0006\u0010\u0016\u001a\u00020\u00172\u0006\u0010\u0018\u001a\u00020\u00192\b\b\u0002\u0010\u001a\u001a\u00020\u001b2\b\b\u0002\u0010\u001c\u001a\u00020\u001bH\u0002J*\u0010\u001d\u001a\u00020\u001e2\u0006\u0010\u001f\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\u00152\u0006\u0010 \u001a\u00020\u00192\b\b\u0002\u0010!\u001a\u00020\"H\u0002J\u0014\u0010#\u001a\b\u0012\u0004\u0012\u00020%0$2\u0006\u0010&\u001a\u00020'J\u000e\u0010(\u001a\u00020)2\u0006\u0010\u001f\u001a\u00020\u0013J\u000e\u0010*\u001a\u00020)2\u0006\u0010\u001f\u001a\u00020\u0013J\u000e\u0010+\u001a\u00020\u001e2\u0006\u0010\u001f\u001a\u00020\u0013R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u0010\u0010\u000b\u001a\u00020\fX\u0082\u0004¢\u0006\u0004\n\u0002\u0010\rR\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000fR\u0011\u0010\u0006\u001a\u00020\u0007¢\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0011¨\u0006,"}, d2 = {"Lorg/jetbrains/kotlin/fir/backend/generators/DataClassMembersGenerator$MyDataClassMethodsGenerator;", "", "irClass", "Lorg/jetbrains/kotlin/ir/declarations/IrClass;", "lookupTag", "Lorg/jetbrains/kotlin/fir/symbols/ConeClassLikeLookupTag;", "origin", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationOrigin;", "(Lorg/jetbrains/kotlin/fir/backend/generators/DataClassMembersGenerator;Lorg/jetbrains/kotlin/ir/declarations/IrClass;Lorg/jetbrains/kotlin/fir/symbols/ConeClassLikeLookupTag;Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationOrigin;)V", "getIrClass", "()Lorg/jetbrains/kotlin/ir/declarations/IrClass;", "irDataClassMembersGenerator", "org/jetbrains/kotlin/fir/backend/generators/DataClassMembersGenerator$MyDataClassMethodsGenerator$irDataClassMembersGenerator$1", "Lorg/jetbrains/kotlin/fir/backend/generators/DataClassMembersGenerator$MyDataClassMethodsGenerator$irDataClassMembersGenerator$1;", "getLookupTag", "()Lorg/jetbrains/kotlin/fir/symbols/ConeClassLikeLookupTag;", "getOrigin", "()Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationOrigin;", "createSyntheticIrFunction", "Lorg/jetbrains/kotlin/ir/declarations/IrFunction;", "name", "Lorg/jetbrains/kotlin/name/Name;", "syntheticCounterpart", "Lorg/jetbrains/kotlin/fir/declarations/FirSimpleFunction;", "returnType", "Lorg/jetbrains/kotlin/ir/types/IrType;", "otherParameterNeeded", "", "isOperator", "createSyntheticIrParameter", "Lorg/jetbrains/kotlin/ir/declarations/IrValueParameter;", "irFunction", ModuleXmlParser.TYPE, "index", "", CompilerOptions.GENERATE, "", "Lorg/jetbrains/kotlin/fir/declarations/FirDeclaration;", "klass", "Lorg/jetbrains/kotlin/fir/declarations/FirRegularClass;", "generateComponentBody", "", "generateCopyBody", "generateDispatchReceiverParameter", "fir2ir"}, k = 1, mv = {1, 9, 0}, xi = 48)
    final class MyDataClassMethodsGenerator {
        private final IrClass irClass;
        private final DataClassMembersGenerator$MyDataClassMethodsGenerator$irDataClassMembersGenerator$1 irDataClassMembersGenerator;
        private final ConeClassLikeLookupTag lookupTag;
        private final IrDeclarationOrigin origin;
        final /* synthetic */ DataClassMembersGenerator this$0;

        public MyDataClassMethodsGenerator(DataClassMembersGenerator dataClassMembersGenerator, IrClass irClass, ConeClassLikeLookupTag lookupTag, IrDeclarationOrigin origin) {
            Intrinsics.checkNotNullParameter(irClass, "irClass");
            Intrinsics.checkNotNullParameter(lookupTag, "lookupTag");
            Intrinsics.checkNotNullParameter(origin, "origin");
            this.this$0 = dataClassMembersGenerator;
            this.irClass = irClass;
            this.lookupTag = lookupTag;
            this.origin = origin;
            this.irDataClassMembersGenerator = new DataClassMembersGenerator$MyDataClassMethodsGenerator$irDataClassMembersGenerator$1(new IrGeneratorContextBase(dataClassMembersGenerator.getComponents().getIrBuiltIns()), dataClassMembersGenerator.getComponents().getSymbolTable(), irClass, AdditionalIrUtilsKt.getKotlinFqName(irClass), origin);
        }

        public final IrClass getIrClass() {
            return this.irClass;
        }

        public final ConeClassLikeLookupTag getLookupTag() {
            return this.lookupTag;
        }

        public final IrDeclarationOrigin getOrigin() {
            return this.origin;
        }

        public final IrValueParameter generateDispatchReceiverParameter(IrFunction irFunction) {
            Intrinsics.checkNotNullParameter(irFunction, "irFunction");
            return ConversionUtilsKt.declareThisReceiverParameter$default(this.this$0, irFunction, IrUtilsKt.getDefaultType(this.irClass), IrDeclarationOrigin.DEFINED.INSTANCE, -1, -1, null, null, 96, null);
        }

        public final List<FirDeclaration> generate(final FirRegularClass klass) {
            List<IrValueParameter> explicitParameters;
            Intrinsics.checkNotNullParameter(klass, "klass");
            IrConstructor primaryConstructor = IrUtilsKt.getPrimaryConstructor(this.irClass);
            List<? extends IrProperty> list = SequencesKt.toList(SequencesKt.take(SequencesKt.filter(IrUtilsKt.getProperties(this.irClass), new Function1<IrProperty, Boolean>() { // from class: org.jetbrains.kotlin.fir.backend.generators.DataClassMembersGenerator$MyDataClassMethodsGenerator$generate$properties$1
                @Override // kotlin.jvm.functions.Function1
                public final Boolean invoke(IrProperty it) {
                    Intrinsics.checkNotNullParameter(it, "it");
                    return Boolean.valueOf(it.getBackingField() != null);
                }
            }), (primaryConstructor == null || (explicitParameters = IrUtilsKt.getExplicitParameters(primaryConstructor)) == null) ? 0 : explicitParameters.size()));
            ArrayList arrayList = new ArrayList();
            FirTypeScope firTypeScopeUnsubstitutedScope = FirKotlinScopeProviderKt.unsubstitutedScope((FirClass) klass, this.this$0.getComponents().getSession(), this.this$0.getComponents().getScopeSession(), true, (FirResolvePhase) null);
            final Map mapCreateMapBuilder = MapsKt.createMapBuilder();
            for (final Name name2 : CollectionsKt.listOf((Object[]) new Name[]{OperatorNameConventions.EQUALS, StandardNames.HASHCODE_NAME, OperatorNameConventions.TO_STRING})) {
                firTypeScopeUnsubstitutedScope.processFunctionsByName(name2, new Function1<FirNamedFunctionSymbol, Unit>() { // from class: org.jetbrains.kotlin.fir.backend.generators.DataClassMembersGenerator$MyDataClassMethodsGenerator$generate$contributedSyntheticFunctions$1$1
                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    {
                        super(1);
                    }

                    @Override // kotlin.jvm.functions.Function1
                    public /* bridge */ /* synthetic */ Unit invoke(FirNamedFunctionSymbol firNamedFunctionSymbol) {
                        invoke2(firNamedFunctionSymbol);
                        return Unit.INSTANCE;
                    }

                    /* JADX WARN: Multi-variable type inference failed */
                    /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
                    public final void invoke2(FirNamedFunctionSymbol it) {
                        Intrinsics.checkNotNullParameter(it, "it");
                        if ((it.getOrigin() instanceof FirDeclarationOrigin.Synthetic) && Intrinsics.areEqual(ClassMembersKt.containingClassLookupTag(it), klass.getSymbol().getLookupTag())) {
                            boolean zContainsKey = mapCreateMapBuilder.containsKey(name2);
                            Name name3 = name2;
                            FirRegularClass firRegularClass = klass;
                            Map<Name, FirSimpleFunction> map = mapCreateMapBuilder;
                            if (zContainsKey) {
                                StringBuilder sbAppend = new StringBuilder("Two synthetic functions ").append(name3).append(" were found in data/value class ").append(firRegularClass.getName()).append(":\n");
                                FirSimpleFunction firSimpleFunction = map.get(name3);
                                throw new IllegalArgumentException(sbAppend.append(firSimpleFunction != null ? UtilsKt.render(firSimpleFunction) : null).append('\n').append(UtilsKt.render(it.getFir())).toString().toString());
                            }
                            map.put(name3, (FirSimpleFunction) it.getFir());
                        }
                    }
                });
            }
            Map mapBuild = MapsKt.build(mapCreateMapBuilder);
            FirSimpleFunction firSimpleFunction = (FirSimpleFunction) mapBuild.get(OperatorNameConventions.TO_STRING);
            if (firSimpleFunction != null) {
                arrayList.add(firSimpleFunction);
                IrFunction irFunctionCreateSyntheticIrFunction$default = createSyntheticIrFunction$default(this, OperatorNameConventions.TO_STRING, firSimpleFunction, this.this$0.getComponents().getIrBuiltIns().getStringType(), false, false, 24, null);
                this.irDataClassMembersGenerator.generateToStringMethod(irFunctionCreateSyntheticIrFunction$default, list);
                this.irClass.getDeclarations().add(irFunctionCreateSyntheticIrFunction$default);
            }
            FirSimpleFunction firSimpleFunction2 = (FirSimpleFunction) mapBuild.get(StandardNames.HASHCODE_NAME);
            if (firSimpleFunction2 != null) {
                arrayList.add(firSimpleFunction2);
                IrFunction irFunctionCreateSyntheticIrFunction$default2 = createSyntheticIrFunction$default(this, StandardNames.HASHCODE_NAME, firSimpleFunction2, this.this$0.getComponents().getIrBuiltIns().getIntType(), false, false, 24, null);
                this.irDataClassMembersGenerator.generateHashCodeMethod(irFunctionCreateSyntheticIrFunction$default2, list);
                this.irClass.getDeclarations().add(irFunctionCreateSyntheticIrFunction$default2);
            }
            FirSimpleFunction firSimpleFunction3 = (FirSimpleFunction) mapBuild.get(OperatorNameConventions.EQUALS);
            if (firSimpleFunction3 != null) {
                arrayList.add(firSimpleFunction3);
                IrFunction irFunctionCreateSyntheticIrFunction = createSyntheticIrFunction(OperatorNameConventions.EQUALS, firSimpleFunction3, this.this$0.getComponents().getIrBuiltIns().getBooleanType(), true, true);
                this.irDataClassMembersGenerator.generateEqualsMethod(irFunctionCreateSyntheticIrFunction, list);
                this.irClass.getDeclarations().add(irFunctionCreateSyntheticIrFunction);
            }
            return arrayList;
        }

        public final void generateComponentBody(IrFunction irFunction) {
            Intrinsics.checkNotNullParameter(irFunction, "irFunction");
            irFunction.setOrigin(this.origin);
            DataClassResolver dataClassResolver = DataClassResolver.INSTANCE;
            String strAsString = irFunction.getName().asString();
            Intrinsics.checkNotNullExpressionValue(strAsString, "asString(...)");
            int componentIndex = dataClassResolver.getComponentIndex(strAsString);
            IrConstructor primaryConstructor = IrUtilsKt.getPrimaryConstructor(this.irClass);
            Intrinsics.checkNotNull(primaryConstructor);
            this.irDataClassMembersGenerator.generateComponentFunction(irFunction, this.irDataClassMembersGenerator.getProperty(primaryConstructor.getValueParameters().get(componentIndex - 1)));
        }

        public final void generateCopyBody(IrFunction irFunction) {
            Intrinsics.checkNotNullParameter(irFunction, "irFunction");
            irFunction.setOrigin(this.origin);
            DataClassMembersGenerator$MyDataClassMethodsGenerator$irDataClassMembersGenerator$1 dataClassMembersGenerator$MyDataClassMethodsGenerator$irDataClassMembersGenerator$1 = this.irDataClassMembersGenerator;
            IrConstructor primaryConstructor = IrUtilsKt.getPrimaryConstructor(this.irClass);
            Intrinsics.checkNotNull(primaryConstructor);
            dataClassMembersGenerator$MyDataClassMethodsGenerator$irDataClassMembersGenerator$1.generateCopyFunction(irFunction, primaryConstructor.getSymbol());
        }

        static /* synthetic */ IrFunction createSyntheticIrFunction$default(MyDataClassMethodsGenerator myDataClassMethodsGenerator, Name name2, FirSimpleFunction firSimpleFunction, IrType irType, boolean z, boolean z2, int i, Object obj) {
            return myDataClassMethodsGenerator.createSyntheticIrFunction(name2, firSimpleFunction, irType, (i & 8) != 0 ? false : z, (i & 16) != 0 ? false : z2);
        }

        private final IrFunction createSyntheticIrFunction(final Name name2, final FirSimpleFunction syntheticCounterpart, final IrType returnType, final boolean otherParameterNeeded, final boolean isOperator) {
            IdSignature idSignatureComposeSignature$default = this.lookupTag.getClassId().isLocal() ? null : FirBasedSignatureComposer.composeSignature$default(this.this$0.getComponents().getSignatureComposer(), syntheticCounterpart, null, false, 6, null);
            Fir2IrDeclarationStorage declarationStorage = this.this$0.getComponents().getDeclarationStorage();
            final DataClassMembersGenerator dataClassMembersGenerator = this.this$0;
            IrSimpleFunction irSimpleFunctionDeclareIrSimpleFunction$fir2ir = declarationStorage.declareIrSimpleFunction$fir2ir(idSignatureComposeSignature$default, new Function1<IrSimpleFunctionSymbol, IrSimpleFunction>() { // from class: org.jetbrains.kotlin.fir.backend.generators.DataClassMembersGenerator$MyDataClassMethodsGenerator$createSyntheticIrFunction$1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(1);
                }

                @Override // kotlin.jvm.functions.Function1
                public final IrSimpleFunction invoke(IrSimpleFunctionSymbol symbol) {
                    Intrinsics.checkNotNullParameter(symbol, "symbol");
                    IrFactory irFactory = dataClassMembersGenerator.getComponents().getIrFactory();
                    IrDeclarationOrigin origin = this.getOrigin();
                    Name name3 = name2;
                    DescriptorVisibility PUBLIC = DescriptorVisibilities.PUBLIC;
                    Intrinsics.checkNotNullExpressionValue(PUBLIC, "PUBLIC");
                    IrSimpleFunction irSimpleFunctionCreateSimpleFunction$default = IrFactory.DefaultImpls.createSimpleFunction$default(irFactory, -1, -1, origin, name3, PUBLIC, false, false, returnType, Modality.OPEN, symbol, false, false, isOperator, false, false, null, false, 32768, null);
                    boolean z = otherParameterNeeded;
                    DataClassMembersGenerator.MyDataClassMethodsGenerator myDataClassMethodsGenerator = this;
                    FirSimpleFunction firSimpleFunction = syntheticCounterpart;
                    DataClassMembersGenerator dataClassMembersGenerator2 = dataClassMembersGenerator;
                    if (z) {
                        irSimpleFunctionCreateSimpleFunction$default.setValueParameters(CollectionsKt.listOf(DataClassMembersGenerator.MyDataClassMethodsGenerator.createSyntheticIrParameter$default(myDataClassMethodsGenerator, irSimpleFunctionCreateSimpleFunction$default, ((FirValueParameter) CollectionsKt.first((List) firSimpleFunction.getValueParameters())).getName(), dataClassMembersGenerator2.getComponents().getIrBuiltIns().getAnyNType(), 0, 8, null)));
                    }
                    irSimpleFunctionCreateSimpleFunction$default.setMetadata(new FirMetadataSource.Function(firSimpleFunction));
                    return irSimpleFunctionCreateSimpleFunction$default;
                }
            });
            DataClassMembersGenerator dataClassMembersGenerator2 = this.this$0;
            irSimpleFunctionDeclareIrSimpleFunction$fir2ir.setParent(this.irClass);
            IrSimpleFunction irSimpleFunction = irSimpleFunctionDeclareIrSimpleFunction$fir2ir;
            irSimpleFunctionDeclareIrSimpleFunction$fir2ir.setDispatchReceiverParameter(generateDispatchReceiverParameter(irSimpleFunction));
            IrSimpleFunctionSymbol irSimpleFunctionSymbol = (IrSimpleFunctionSymbol) CollectionsKt.singleOrNull(dataClassMembersGenerator2.getComponents().getIrBuiltIns().findBuiltInClassMemberFunctions(dataClassMembersGenerator2.getComponents().getIrBuiltIns().getAnyClass(), irSimpleFunctionDeclareIrSimpleFunction$fir2ir.getName()));
            if (irSimpleFunctionSymbol != null) {
                irSimpleFunctionDeclareIrSimpleFunction$fir2ir.setOverriddenSymbols(CollectionsKt.listOf(irSimpleFunctionSymbol));
            }
            return irSimpleFunction;
        }

        static /* synthetic */ IrValueParameter createSyntheticIrParameter$default(MyDataClassMethodsGenerator myDataClassMethodsGenerator, IrFunction irFunction, Name name2, IrType irType, int i, int i2, Object obj) {
            if ((i2 & 8) != 0) {
                i = 0;
            }
            return myDataClassMethodsGenerator.createSyntheticIrParameter(irFunction, name2, irType, i);
        }

        private final IrValueParameter createSyntheticIrParameter(IrFunction irFunction, Name name2, IrType type, int index) {
            IrValueParameter irValueParameterCreateValueParameter = this.this$0.getComponents().getIrFactory().createValueParameter(-1, -1, (IrDeclarationOrigin) IrDeclarationOrigin.DEFINED.INSTANCE, name2, type, false, (IrValueParameterSymbol) new IrValueParameterSymbolImpl(null, 1, null), index, (IrType) null, false, false, false);
            irValueParameterCreateValueParameter.setParent(irFunction);
            return irValueParameterCreateValueParameter;
        }
    }
}
