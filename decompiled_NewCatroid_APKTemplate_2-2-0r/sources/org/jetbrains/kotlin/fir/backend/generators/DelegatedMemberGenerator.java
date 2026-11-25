package org.jetbrains.kotlin.fir.backend.generators;

import androidx.exifinterface.media.ExifInterface;
import com.reandroid.apk.ApkUtil;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.LazyThreadSafetyMode;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Ref;
import org.eclipse.jdt.internal.compiler.impl.CompilerOptions;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.descriptors.Modality;
import org.jetbrains.kotlin.fir.ClassMembersKt;
import org.jetbrains.kotlin.fir.DelegatedWrapperData;
import org.jetbrains.kotlin.fir.FirSession;
import org.jetbrains.kotlin.fir.backend.ConversionUtilsKt;
import org.jetbrains.kotlin.fir.backend.Fir2IrAnnotationsFromPluginRegistrar;
import org.jetbrains.kotlin.fir.backend.Fir2IrBuiltIns;
import org.jetbrains.kotlin.fir.backend.Fir2IrClassifierStorage;
import org.jetbrains.kotlin.fir.backend.Fir2IrComponents;
import org.jetbrains.kotlin.fir.backend.Fir2IrConfiguration;
import org.jetbrains.kotlin.fir.backend.Fir2IrConverter;
import org.jetbrains.kotlin.fir.backend.Fir2IrDeclarationStorage;
import org.jetbrains.kotlin.fir.backend.Fir2IrExtensions;
import org.jetbrains.kotlin.fir.backend.Fir2IrImplicitCastInserter;
import org.jetbrains.kotlin.fir.backend.Fir2IrTypeConverter;
import org.jetbrains.kotlin.fir.backend.Fir2IrVisibilityConverter;
import org.jetbrains.kotlin.fir.backend.IrBuiltInsOverFir;
import org.jetbrains.kotlin.fir.declarations.DeclarationUtilsKt;
import org.jetbrains.kotlin.fir.declarations.FirAnnotationUtilsKt;
import org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration;
import org.jetbrains.kotlin.fir.declarations.FirClass;
import org.jetbrains.kotlin.fir.declarations.FirDeclaration;
import org.jetbrains.kotlin.fir.declarations.FirDeclarationOrigin;
import org.jetbrains.kotlin.fir.declarations.FirField;
import org.jetbrains.kotlin.fir.declarations.FirProperty;
import org.jetbrains.kotlin.fir.declarations.FirResolvePhase;
import org.jetbrains.kotlin.fir.declarations.FirSimpleFunction;
import org.jetbrains.kotlin.fir.expressions.FirExpression;
import org.jetbrains.kotlin.fir.resolve.ScopeSession;
import org.jetbrains.kotlin.fir.resolve.ScopeUtilsKt;
import org.jetbrains.kotlin.fir.resolve.TypeExpansionUtilsKt;
import org.jetbrains.kotlin.fir.scopes.FakeOverrideTypeCalculator;
import org.jetbrains.kotlin.fir.scopes.FirContainingNamesAwareScopeKt;
import org.jetbrains.kotlin.fir.scopes.FirKotlinScopeProviderKt;
import org.jetbrains.kotlin.fir.scopes.FirTypeScope;
import org.jetbrains.kotlin.fir.scopes.FirTypeScopeKt;
import org.jetbrains.kotlin.fir.scopes.ProcessorAction;
import org.jetbrains.kotlin.fir.signaturer.FirBasedSignatureComposer;
import org.jetbrains.kotlin.fir.symbols.ConeClassLikeLookupTag;
import org.jetbrains.kotlin.fir.symbols.impl.FirCallableSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirNamedFunctionSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirPropertySymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirVariableSymbol;
import org.jetbrains.kotlin.fir.types.ConeTypeUtilsKt;
import org.jetbrains.kotlin.fir.types.FirTypeUtilsKt;
import org.jetbrains.kotlin.ir.IrLock;
import org.jetbrains.kotlin.ir.declarations.IrClass;
import org.jetbrains.kotlin.ir.declarations.IrDeclaration;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationOrigin;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationParent;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationsKt;
import org.jetbrains.kotlin.ir.declarations.IrFactory;
import org.jetbrains.kotlin.ir.declarations.IrField;
import org.jetbrains.kotlin.ir.declarations.IrFunction;
import org.jetbrains.kotlin.ir.declarations.IrProperty;
import org.jetbrains.kotlin.ir.declarations.IrSimpleFunction;
import org.jetbrains.kotlin.ir.declarations.IrSymbolOwner;
import org.jetbrains.kotlin.ir.declarations.IrTypeParameter;
import org.jetbrains.kotlin.ir.declarations.IrValueParameter;
import org.jetbrains.kotlin.ir.expressions.IrBlockBody;
import org.jetbrains.kotlin.ir.expressions.IrExpression;
import org.jetbrains.kotlin.ir.expressions.impl.IrCallImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrGetFieldImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrGetValueImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrReturnImpl;
import org.jetbrains.kotlin.ir.linkage.IrProvider;
import org.jetbrains.kotlin.ir.symbols.IrClassifierSymbol;
import org.jetbrains.kotlin.ir.symbols.IrFieldSymbol;
import org.jetbrains.kotlin.ir.symbols.IrPropertySymbol;
import org.jetbrains.kotlin.ir.symbols.IrSimpleFunctionSymbol;
import org.jetbrains.kotlin.ir.symbols.IrValueParameterSymbol;
import org.jetbrains.kotlin.ir.types.IrType;
import org.jetbrains.kotlin.ir.types.IrTypeAbbreviation;
import org.jetbrains.kotlin.ir.types.IrTypePredicatesKt;
import org.jetbrains.kotlin.ir.types.IrTypeUtilsKt;
import org.jetbrains.kotlin.ir.types.IrTypesKt;
import org.jetbrains.kotlin.ir.types.impl.IrSimpleTypeImpl;
import org.jetbrains.kotlin.ir.util.SymbolTable;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.name.ClassId;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.name.JvmNames;
import org.jetbrains.kotlin.name.Name;

/* JADX INFO: compiled from: DelegatedMemberGenerator.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000Â\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010%\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010#\u0018\u0000 \u008b\u00012\u00020\u0001:\u0004\u008b\u0001\u008c\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0001¢\u0006\u0002\u0010\u0003J\u000e\u0010b\u001a\u00020c2\u0006\u0010d\u001a\u00020eJ(\u0010f\u001a\u00020g2\u0006\u0010h\u001a\u00020i2\u0006\u0010j\u001a\u00020k2\u0006\u0010l\u001a\u00020k2\u0006\u0010m\u001a\u00020nH\u0002J²\u0001\u0010o\u001a\u0004\u0018\u0001Hp\"\u000e\b\u0000\u0010p\u0018\u0001*\u0006\u0012\u0002\b\u00030q2\u0006\u0010r\u001a\u0002Hp2B\u0010s\u001a>\u0012\u0013\u0012\u00110u¢\u0006\f\bv\u0012\b\bw\u0012\u0004\b\b(w\u0012\u001f\u0012\u001d\u0012\u0004\u0012\u0002Hp\u0012\u0004\u0012\u00020c0x¢\u0006\f\bv\u0012\b\bw\u0012\u0004\b\b(y\u0012\u0004\u0012\u00020c0t2D\b\u0004\u0010z\u001a>\u0012\u0013\u0012\u0011Hp¢\u0006\f\bv\u0012\b\bw\u0012\u0004\b\b({\u0012\u001f\u0012\u001d\u0012\u0004\u0012\u0002Hp\u0012\u0004\u0012\u00020|0x¢\u0006\f\bv\u0012\b\bw\u0012\u0004\b\b(y\u0012\u0004\u0012\u00020|0tH\u0082\b¢\u0006\u0002\u0010}J*\u0010~\u001a\u00020c2\u0006\u0010h\u001a\u00020i2\u0007\u0010\u007f\u001a\u00030\u0080\u00012\b\u0010\u0081\u0001\u001a\u00030\u0082\u00012\u0007\u0010\u0083\u0001\u001a\u00020eJ\u0007\u0010\u0084\u0001\u001a\u00020cJ&\u0010\u0085\u0001\u001a\u00020k2\u0007\u0010\u0083\u0001\u001a\u00020e2\b\u0010\u0081\u0001\u001a\u00030\u0082\u00012\b\u0010\u0086\u0001\u001a\u00030\u0087\u0001H\u0002J&\u0010\u0088\u0001\u001a\u00020\u00122\u0007\u0010\u0083\u0001\u001a\u00020e2\b\u0010\u0081\u0001\u001a\u00030\u0082\u00012\b\u0010\u0089\u0001\u001a\u00030\u008a\u0001H\u0002R\u0012\u0010\u0004\u001a\u00020\u0005X\u0096\u0005¢\u0006\u0006\u001a\u0004\b\u0006\u0010\u0007R\u0012\u0010\b\u001a\u00020\tX\u0096\u0005¢\u0006\u0006\u001a\u0004\b\n\u0010\u000bR \u0010\f\u001a\u0014\u0012\u0004\u0012\u00020\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00100\u000f0\rX\u0082\u0004¢\u0006\u0002\n\u0000R \u0010\u0011\u001a\u0014\u0012\u0004\u0012\u00020\u0012\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00130\u000f0\rX\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\u00160\u0015X\u0082\u0004¢\u0006\u0002\n\u0000R\u0012\u0010\u0017\u001a\u00020\u0018X\u0096\u0005¢\u0006\u0006\u001a\u0004\b\u0019\u0010\u001aR\u0012\u0010\u001b\u001a\u00020\u001cX\u0096\u0005¢\u0006\u0006\u001a\u0004\b\u001d\u0010\u001eR\u0012\u0010\u001f\u001a\u00020 X\u0096\u0005¢\u0006\u0006\u001a\u0004\b!\u0010\"R\u000e\u0010\u0002\u001a\u00020\u0001X\u0082\u0004¢\u0006\u0002\n\u0000R\u0012\u0010#\u001a\u00020$X\u0096\u0005¢\u0006\u0006\u001a\u0004\b%\u0010&R\u0012\u0010'\u001a\u00020(X\u0096\u0005¢\u0006\u0006\u001a\u0004\b)\u0010*R\u0012\u0010+\u001a\u00020,X\u0096\u0005¢\u0006\u0006\u001a\u0004\b-\u0010.R\u0012\u0010/\u001a\u00020\u0000X\u0096\u0005¢\u0006\u0006\u001a\u0004\b0\u00101R\u0012\u00102\u001a\u000203X\u0096\u0005¢\u0006\u0006\u001a\u0004\b4\u00105R\u0012\u00106\u001a\u000207X\u0096\u0005¢\u0006\u0006\u001a\u0004\b8\u00109R\u0012\u0010:\u001a\u00020;X\u0096\u0005¢\u0006\u0006\u001a\u0004\b<\u0010=R\u0012\u0010>\u001a\u00020?X\u0096\u0005¢\u0006\u0006\u001a\u0004\b@\u0010AR\u0018\u0010B\u001a\b\u0012\u0004\u0012\u00020C0\u000fX\u0096\u0005¢\u0006\u0006\u001a\u0004\bD\u0010ER\u0012\u0010F\u001a\u00020GX\u0096\u0005¢\u0006\u0006\u001a\u0004\bH\u0010IR\u0012\u0010J\u001a\u00020KX\u0096\u0005¢\u0006\u0006\u001a\u0004\bL\u0010MR\u0012\u0010N\u001a\u00020OX\u0096\u0005¢\u0006\u0006\u001a\u0004\bP\u0010QR\u0012\u0010R\u001a\u00020SX\u0096\u0005¢\u0006\u0006\u001a\u0004\bT\u0010UR\u0012\u0010V\u001a\u00020WX\u0096\u0005¢\u0006\u0006\u001a\u0004\bX\u0010YR\u0012\u0010Z\u001a\u00020[X\u0096\u0005¢\u0006\u0006\u001a\u0004\b\\\u0010]R\u0012\u0010^\u001a\u00020_X\u0096\u0005¢\u0006\u0006\u001a\u0004\b`\u0010a¨\u0006\u008d\u0001²\u0006\u0012\u0010\u008e\u0001\u001a\t\u0012\u0004\u0012\u00020e0\u008f\u0001X\u008a\u0084\u0002"}, d2 = {"Lorg/jetbrains/kotlin/fir/backend/generators/DelegatedMemberGenerator;", "Lorg/jetbrains/kotlin/fir/backend/Fir2IrComponents;", "components", "(Lorg/jetbrains/kotlin/fir/backend/Fir2IrComponents;)V", "annotationGenerator", "Lorg/jetbrains/kotlin/fir/backend/generators/AnnotationGenerator;", "getAnnotationGenerator", "()Lorg/jetbrains/kotlin/fir/backend/generators/AnnotationGenerator;", "annotationsFromPluginRegistrar", "Lorg/jetbrains/kotlin/fir/backend/Fir2IrAnnotationsFromPluginRegistrar;", "getAnnotationsFromPluginRegistrar", "()Lorg/jetbrains/kotlin/fir/backend/Fir2IrAnnotationsFromPluginRegistrar;", "baseFunctionSymbols", "", "Lorg/jetbrains/kotlin/ir/declarations/IrFunction;", "", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirNamedFunctionSymbol;", "basePropertySymbols", "Lorg/jetbrains/kotlin/ir/declarations/IrProperty;", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirPropertySymbol;", "bodiesInfo", "", "Lorg/jetbrains/kotlin/fir/backend/generators/DelegatedMemberGenerator$DeclarationBodyInfo;", "builtIns", "Lorg/jetbrains/kotlin/fir/backend/Fir2IrBuiltIns;", "getBuiltIns", "()Lorg/jetbrains/kotlin/fir/backend/Fir2IrBuiltIns;", "callGenerator", "Lorg/jetbrains/kotlin/fir/backend/generators/CallAndReferenceGenerator;", "getCallGenerator", "()Lorg/jetbrains/kotlin/fir/backend/generators/CallAndReferenceGenerator;", "classifierStorage", "Lorg/jetbrains/kotlin/fir/backend/Fir2IrClassifierStorage;", "getClassifierStorage", "()Lorg/jetbrains/kotlin/fir/backend/Fir2IrClassifierStorage;", "configuration", "Lorg/jetbrains/kotlin/fir/backend/Fir2IrConfiguration;", "getConfiguration", "()Lorg/jetbrains/kotlin/fir/backend/Fir2IrConfiguration;", "converter", "Lorg/jetbrains/kotlin/fir/backend/Fir2IrConverter;", "getConverter", "()Lorg/jetbrains/kotlin/fir/backend/Fir2IrConverter;", "declarationStorage", "Lorg/jetbrains/kotlin/fir/backend/Fir2IrDeclarationStorage;", "getDeclarationStorage", "()Lorg/jetbrains/kotlin/fir/backend/Fir2IrDeclarationStorage;", "delegatedMemberGenerator", "getDelegatedMemberGenerator", "()Lorg/jetbrains/kotlin/fir/backend/generators/DelegatedMemberGenerator;", "extensions", "Lorg/jetbrains/kotlin/fir/backend/Fir2IrExtensions;", "getExtensions", "()Lorg/jetbrains/kotlin/fir/backend/Fir2IrExtensions;", "fakeOverrideGenerator", "Lorg/jetbrains/kotlin/fir/backend/generators/FakeOverrideGenerator;", "getFakeOverrideGenerator", "()Lorg/jetbrains/kotlin/fir/backend/generators/FakeOverrideGenerator;", "irBuiltIns", "Lorg/jetbrains/kotlin/fir/backend/IrBuiltInsOverFir;", "getIrBuiltIns", "()Lorg/jetbrains/kotlin/fir/backend/IrBuiltInsOverFir;", "irFactory", "Lorg/jetbrains/kotlin/ir/declarations/IrFactory;", "getIrFactory", "()Lorg/jetbrains/kotlin/ir/declarations/IrFactory;", "irProviders", "Lorg/jetbrains/kotlin/ir/linkage/IrProvider;", "getIrProviders", "()Ljava/util/List;", "lock", "Lorg/jetbrains/kotlin/ir/IrLock;", "getLock", "()Lorg/jetbrains/kotlin/ir/IrLock;", "scopeSession", "Lorg/jetbrains/kotlin/fir/resolve/ScopeSession;", "getScopeSession", "()Lorg/jetbrains/kotlin/fir/resolve/ScopeSession;", "session", "Lorg/jetbrains/kotlin/fir/FirSession;", "getSession", "()Lorg/jetbrains/kotlin/fir/FirSession;", "signatureComposer", "Lorg/jetbrains/kotlin/fir/signaturer/FirBasedSignatureComposer;", "getSignatureComposer", "()Lorg/jetbrains/kotlin/fir/signaturer/FirBasedSignatureComposer;", "symbolTable", "Lorg/jetbrains/kotlin/ir/util/SymbolTable;", "getSymbolTable", "()Lorg/jetbrains/kotlin/ir/util/SymbolTable;", "typeConverter", "Lorg/jetbrains/kotlin/fir/backend/Fir2IrTypeConverter;", "getTypeConverter", "()Lorg/jetbrains/kotlin/fir/backend/Fir2IrTypeConverter;", "visibilityConverter", "Lorg/jetbrains/kotlin/fir/backend/Fir2IrVisibilityConverter;", "getVisibilityConverter", "()Lorg/jetbrains/kotlin/fir/backend/Fir2IrVisibilityConverter;", "bindDelegatedMembersOverriddenSymbols", "", "irClass", "Lorg/jetbrains/kotlin/ir/declarations/IrClass;", "createDelegateBody", "Lorg/jetbrains/kotlin/ir/expressions/IrBlockBody;", "irField", "Lorg/jetbrains/kotlin/ir/declarations/IrField;", "delegateFunction", "Lorg/jetbrains/kotlin/ir/declarations/IrSimpleFunction;", "superFunction", "callTypeCanBeNullable", "", "findDelegateToSymbol", ExifInterface.LATITUDE_SOUTH, "Lorg/jetbrains/kotlin/fir/symbols/impl/FirCallableSymbol;", "unwrappedSymbol", "processCallables", "Lkotlin/Function2;", "Lorg/jetbrains/kotlin/name/Name;", "Lkotlin/ParameterName;", "name", "Lkotlin/Function1;", "processor", "processOverridden", ApkUtil.DEF_MODULE_NAME, "Lorg/jetbrains/kotlin/fir/scopes/ProcessorAction;", "(Lorg/jetbrains/kotlin/fir/symbols/impl/FirCallableSymbol;Lkotlin/jvm/functions/Function2;Lkotlin/jvm/functions/Function2;)Lorg/jetbrains/kotlin/fir/symbols/impl/FirCallableSymbol;", CompilerOptions.GENERATE, "firField", "Lorg/jetbrains/kotlin/fir/declarations/FirField;", "firSubClass", "Lorg/jetbrains/kotlin/fir/declarations/FirClass;", "subClass", "generateBodies", "generateDelegatedFunction", "delegateOverride", "Lorg/jetbrains/kotlin/fir/declarations/FirSimpleFunction;", "generateDelegatedProperty", "firDelegateProperty", "Lorg/jetbrains/kotlin/fir/declarations/FirProperty;", "Companion", "DeclarationBodyInfo", "fir2ir", "superClasses", ""}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class DelegatedMemberGenerator implements Fir2IrComponents {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private static final ClassId PLATFORM_DEPENDENT_CLASS_ID;
    private final Map<IrFunction, List<FirNamedFunctionSymbol>> baseFunctionSymbols;
    private final Map<IrProperty, List<FirPropertySymbol>> basePropertySymbols;
    private final List<DeclarationBodyInfo> bodiesInfo;
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

    public DelegatedMemberGenerator(Fir2IrComponents components) {
        Intrinsics.checkNotNullParameter(components, "components");
        this.components = components;
        this.baseFunctionSymbols = new LinkedHashMap();
        this.basePropertySymbols = new LinkedHashMap();
        this.bodiesInfo = new ArrayList();
    }

    /* JADX INFO: compiled from: DelegatedMemberGenerator.kt */
    @Metadata(d1 = {"\u00008\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u000f\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0082\b\u0018\u00002\u00020\u0001B+\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\n\u0010\u0006\u001a\u0006\u0012\u0002\b\u00030\u0007\u0012\b\u0010\b\u001a\u0004\u0018\u00010\t¢\u0006\u0002\u0010\nJ\t\u0010\u0013\u001a\u00020\u0003HÆ\u0003J\t\u0010\u0014\u001a\u00020\u0005HÆ\u0003J\r\u0010\u0015\u001a\u0006\u0012\u0002\b\u00030\u0007HÆ\u0003J\u000b\u0010\u0016\u001a\u0004\u0018\u00010\tHÆ\u0003J7\u0010\u0017\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00052\f\b\u0002\u0010\u0006\u001a\u0006\u0012\u0002\b\u00030\u00072\n\b\u0002\u0010\b\u001a\u0004\u0018\u00010\tHÆ\u0001J\u0013\u0010\u0018\u001a\u00020\u00192\b\u0010\u001a\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u001b\u001a\u00020\u001cHÖ\u0001J\t\u0010\u001d\u001a\u00020\u001eHÖ\u0001R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u0013\u0010\b\u001a\u0004\u0018\u00010\t¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000eR\u0015\u0010\u0006\u001a\u0006\u0012\u0002\b\u00030\u0007¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0010R\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0012¨\u0006\u001f"}, d2 = {"Lorg/jetbrains/kotlin/fir/backend/generators/DelegatedMemberGenerator$DeclarationBodyInfo;", "", "declaration", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclaration;", "field", "Lorg/jetbrains/kotlin/ir/declarations/IrField;", "delegateToSymbol", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirCallableSymbol;", "delegateToLookupTag", "Lorg/jetbrains/kotlin/fir/symbols/ConeClassLikeLookupTag;", "(Lorg/jetbrains/kotlin/ir/declarations/IrDeclaration;Lorg/jetbrains/kotlin/ir/declarations/IrField;Lorg/jetbrains/kotlin/fir/symbols/impl/FirCallableSymbol;Lorg/jetbrains/kotlin/fir/symbols/ConeClassLikeLookupTag;)V", "getDeclaration", "()Lorg/jetbrains/kotlin/ir/declarations/IrDeclaration;", "getDelegateToLookupTag", "()Lorg/jetbrains/kotlin/fir/symbols/ConeClassLikeLookupTag;", "getDelegateToSymbol", "()Lorg/jetbrains/kotlin/fir/symbols/impl/FirCallableSymbol;", "getField", "()Lorg/jetbrains/kotlin/ir/declarations/IrField;", "component1", "component2", "component3", "component4", ConfigConstants.CONFIG_RENAMELIMIT_COPY, Namer.EQUALS_METHOD_NAME, "", "other", "hashCode", "", "toString", "", "fir2ir"}, k = 1, mv = {1, 9, 0}, xi = 48)
    private static final /* data */ class DeclarationBodyInfo {
        private final IrDeclaration declaration;
        private final ConeClassLikeLookupTag delegateToLookupTag;
        private final FirCallableSymbol<?> delegateToSymbol;
        private final IrField field;

        /* JADX WARN: Multi-variable type inference failed */
        public static /* synthetic */ DeclarationBodyInfo copy$default(DeclarationBodyInfo declarationBodyInfo, IrDeclaration irDeclaration, IrField irField, FirCallableSymbol firCallableSymbol, ConeClassLikeLookupTag coneClassLikeLookupTag, int i, Object obj) {
            if ((i & 1) != 0) {
                irDeclaration = declarationBodyInfo.declaration;
            }
            if ((i & 2) != 0) {
                irField = declarationBodyInfo.field;
            }
            if ((i & 4) != 0) {
                firCallableSymbol = declarationBodyInfo.delegateToSymbol;
            }
            if ((i & 8) != 0) {
                coneClassLikeLookupTag = declarationBodyInfo.delegateToLookupTag;
            }
            return declarationBodyInfo.copy(irDeclaration, irField, firCallableSymbol, coneClassLikeLookupTag);
        }

        /* JADX INFO: renamed from: component1, reason: from getter */
        public final IrDeclaration getDeclaration() {
            return this.declaration;
        }

        /* JADX INFO: renamed from: component2, reason: from getter */
        public final IrField getField() {
            return this.field;
        }

        public final FirCallableSymbol<?> component3() {
            return this.delegateToSymbol;
        }

        /* JADX INFO: renamed from: component4, reason: from getter */
        public final ConeClassLikeLookupTag getDelegateToLookupTag() {
            return this.delegateToLookupTag;
        }

        public final DeclarationBodyInfo copy(IrDeclaration declaration, IrField field, FirCallableSymbol<?> delegateToSymbol, ConeClassLikeLookupTag delegateToLookupTag) {
            Intrinsics.checkNotNullParameter(declaration, "declaration");
            Intrinsics.checkNotNullParameter(field, "field");
            Intrinsics.checkNotNullParameter(delegateToSymbol, "delegateToSymbol");
            return new DeclarationBodyInfo(declaration, field, delegateToSymbol, delegateToLookupTag);
        }

        public boolean equals(Object other) {
            if (this == other) {
                return true;
            }
            if (!(other instanceof DeclarationBodyInfo)) {
                return false;
            }
            DeclarationBodyInfo declarationBodyInfo = (DeclarationBodyInfo) other;
            return Intrinsics.areEqual(this.declaration, declarationBodyInfo.declaration) && Intrinsics.areEqual(this.field, declarationBodyInfo.field) && Intrinsics.areEqual(this.delegateToSymbol, declarationBodyInfo.delegateToSymbol) && Intrinsics.areEqual(this.delegateToLookupTag, declarationBodyInfo.delegateToLookupTag);
        }

        public int hashCode() {
            int iHashCode = ((((this.declaration.hashCode() * 31) + this.field.hashCode()) * 31) + this.delegateToSymbol.hashCode()) * 31;
            ConeClassLikeLookupTag coneClassLikeLookupTag = this.delegateToLookupTag;
            return iHashCode + (coneClassLikeLookupTag == null ? 0 : coneClassLikeLookupTag.hashCode());
        }

        public String toString() {
            return "DeclarationBodyInfo(declaration=" + this.declaration + ", field=" + this.field + ", delegateToSymbol=" + this.delegateToSymbol + ", delegateToLookupTag=" + this.delegateToLookupTag + ')';
        }

        public DeclarationBodyInfo(IrDeclaration declaration, IrField field, FirCallableSymbol<?> delegateToSymbol, ConeClassLikeLookupTag coneClassLikeLookupTag) {
            Intrinsics.checkNotNullParameter(declaration, "declaration");
            Intrinsics.checkNotNullParameter(field, "field");
            Intrinsics.checkNotNullParameter(delegateToSymbol, "delegateToSymbol");
            this.declaration = declaration;
            this.field = field;
            this.delegateToSymbol = delegateToSymbol;
            this.delegateToLookupTag = coneClassLikeLookupTag;
        }

        public final IrDeclaration getDeclaration() {
            return this.declaration;
        }

        public final IrField getField() {
            return this.field;
        }

        public final FirCallableSymbol<?> getDelegateToSymbol() {
            return this.delegateToSymbol;
        }

        public final ConeClassLikeLookupTag getDelegateToLookupTag() {
            return this.delegateToLookupTag;
        }
    }

    public final void generateBodies() {
        for (DeclarationBodyInfo declarationBodyInfo : this.bodiesInfo) {
            IrDeclaration declaration = declarationBodyInfo.getDeclaration();
            IrField field = declarationBodyInfo.getField();
            FirCallableSymbol<?> firCallableSymbolComponent3 = declarationBodyInfo.component3();
            ConeClassLikeLookupTag delegateToLookupTag = declarationBodyInfo.getDelegateToLookupTag();
            boolean zTypeCanBeEnhancedOrFlexibleNullable$fir2ir = Fir2IrImplicitCastInserter.INSTANCE.typeCanBeEnhancedOrFlexibleNullable$fir2ir(TypeExpansionUtilsKt.fullyExpandedType(FirTypeUtilsKt.getConeType(((FirCallableDeclaration) firCallableSymbolComponent3.getFir()).getReturnTypeRef()), getSession()));
            if (declaration instanceof IrSimpleFunction) {
                Fir2IrDeclarationStorage declarationStorage = getDeclarationStorage();
                Intrinsics.checkNotNull(firCallableSymbolComponent3, "null cannot be cast to non-null type org.jetbrains.kotlin.fir.symbols.impl.FirNamedFunctionSymbol");
                IrFunction owner = declarationStorage.getIrFunctionSymbol((FirNamedFunctionSymbol) firCallableSymbolComponent3, delegateToLookupTag).getOwner();
                IrSimpleFunction irSimpleFunction = owner instanceof IrSimpleFunction ? (IrSimpleFunction) owner : null;
                if (irSimpleFunction != null) {
                    IrSimpleFunction irSimpleFunction2 = (IrSimpleFunction) declaration;
                    irSimpleFunction2.setBody(createDelegateBody(field, irSimpleFunction2, irSimpleFunction, zTypeCanBeEnhancedOrFlexibleNullable$fir2ir));
                }
            } else if (declaration instanceof IrProperty) {
                Fir2IrDeclarationStorage declarationStorage2 = getDeclarationStorage();
                Intrinsics.checkNotNull(firCallableSymbolComponent3, "null cannot be cast to non-null type org.jetbrains.kotlin.fir.symbols.impl.FirPropertySymbol");
                IrSymbolOwner owner2 = declarationStorage2.getIrPropertySymbol((FirPropertySymbol) firCallableSymbolComponent3, delegateToLookupTag).getOwner();
                IrProperty irProperty = owner2 instanceof IrProperty ? (IrProperty) owner2 : null;
                if (irProperty != null) {
                    IrProperty irProperty2 = (IrProperty) declaration;
                    IrSimpleFunction getter = irProperty2.getGetter();
                    Intrinsics.checkNotNull(getter);
                    IrSimpleFunction getter2 = irProperty.getGetter();
                    Intrinsics.checkNotNull(getter2);
                    getter.setBody(createDelegateBody(field, getter, getter2, zTypeCanBeEnhancedOrFlexibleNullable$fir2ir));
                    if (irProperty2.getIsVar()) {
                        IrSimpleFunction setter = irProperty2.getSetter();
                        Intrinsics.checkNotNull(setter);
                        IrSimpleFunction setter2 = irProperty.getSetter();
                        Intrinsics.checkNotNull(setter2);
                        setter.setBody(createDelegateBody(field, setter, setter2, false));
                    }
                }
            }
        }
        this.bodiesInfo.clear();
    }

    public final void generate(final IrField irField, final FirField firField, final FirClass firSubClass, final IrClass subClass) {
        Intrinsics.checkNotNullParameter(irField, "irField");
        Intrinsics.checkNotNullParameter(firField, "firField");
        Intrinsics.checkNotNullParameter(firSubClass, "firSubClass");
        Intrinsics.checkNotNullParameter(subClass, "subClass");
        FirTypeScope firTypeScopeUnsubstitutedScope = FirKotlinScopeProviderKt.unsubstitutedScope(firSubClass, getSession(), getScopeSession(), false, (FirResolvePhase) null);
        FirExpression initializer = firField.getInitializer();
        Intrinsics.checkNotNull(initializer);
        final FirTypeScope firTypeScopeScope = ScopeUtilsKt.scope(ConeTypeUtilsKt.lowerBoundIfFlexible(TypeExpansionUtilsKt.fullyExpandedType(FirTypeUtilsKt.getConeType(initializer.getTypeRef()), getSession())), getSession(), getScopeSession(), FakeOverrideTypeCalculator.DoNothing.INSTANCE, null);
        if (firTypeScopeScope == null) {
            return;
        }
        final ConeClassLikeLookupTag lookupTag = firSubClass.getSymbol().getLookupTag();
        FirTypeScope firTypeScope = firTypeScopeUnsubstitutedScope;
        FirContainingNamesAwareScopeKt.processAllFunctions(firTypeScope, new Function1<FirNamedFunctionSymbol, Unit>() { // from class: org.jetbrains.kotlin.fir.backend.generators.DelegatedMemberGenerator.generate.1
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
            public final void invoke2(FirNamedFunctionSymbol functionSymbol) {
                FirNamedFunctionSymbol firNamedFunctionSymbol;
                FirNamedFunctionSymbol firNamedFunctionSymbol2;
                ConeClassLikeLookupTag coneClassLikeLookupTagDispatchReceiverClassLookupTagOrNull;
                Intrinsics.checkNotNullParameter(functionSymbol, "functionSymbol");
                FirSimpleFunction firSimpleFunction = (FirSimpleFunction) Companion.access$unwrapDelegateTarget(DelegatedMemberGenerator.INSTANCE, functionSymbol, DelegatedMemberGenerator.this, lookupTag, firField);
                if (firSimpleFunction == null) {
                    return;
                }
                FirSimpleFunction firSimpleFunction2 = firSimpleFunction;
                while (true) {
                    firNamedFunctionSymbol = null;
                    FirCallableDeclaration originalForSubstitutionOverrideAttr = (ClassMembersKt.isSubstitutionOverride(firSimpleFunction2) || (firSimpleFunction2.getOrigin() instanceof FirDeclarationOrigin.Synthetic)) ? ClassMembersKt.getOriginalForSubstitutionOverrideAttr(firSimpleFunction2) : null;
                    if (originalForSubstitutionOverrideAttr == null) {
                        break;
                    } else {
                        firSimpleFunction2 = originalForSubstitutionOverrideAttr;
                    }
                }
                final FirNamedFunctionSymbol symbol = ((FirSimpleFunction) firSimpleFunction2).getSymbol();
                final FirTypeScope firTypeScope2 = firTypeScopeScope;
                final Ref.ObjectRef objectRef = new Ref.ObjectRef();
                firTypeScope2.processFunctionsByName(symbol.getName(), new Function1<FirNamedFunctionSymbol, Unit>() { // from class: org.jetbrains.kotlin.fir.backend.generators.DelegatedMemberGenerator$generate$1$invoke$$inlined$findDelegateToSymbol$1
                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    {
                        super(1);
                    }

                    @Override // kotlin.jvm.functions.Function1
                    public /* bridge */ /* synthetic */ Unit invoke(FirNamedFunctionSymbol firNamedFunctionSymbol3) {
                        invoke(firNamedFunctionSymbol3);
                        return Unit.INSTANCE;
                    }

                    /* JADX WARN: Multi-variable type inference failed */
                    public final void invoke(final FirNamedFunctionSymbol candidateSymbol) {
                        Intrinsics.checkNotNullParameter(candidateSymbol, "candidateSymbol");
                        if (objectRef.element != 0) {
                            return;
                        }
                        if (candidateSymbol == symbol) {
                            objectRef.element = candidateSymbol;
                            return;
                        }
                        final FirCallableSymbol firCallableSymbol = symbol;
                        final Ref.ObjectRef objectRef2 = objectRef;
                        Function1<FirNamedFunctionSymbol, ProcessorAction> function1 = new Function1<FirNamedFunctionSymbol, ProcessorAction>() { // from class: org.jetbrains.kotlin.fir.backend.generators.DelegatedMemberGenerator$generate$1$invoke$$inlined$findDelegateToSymbol$1.1
                            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                            {
                                super(1);
                            }

                            /* JADX WARN: Type inference failed for: r0v2, types: [T, org.jetbrains.kotlin.fir.symbols.impl.FirCallableSymbol] */
                            @Override // kotlin.jvm.functions.Function1
                            public final ProcessorAction invoke(FirNamedFunctionSymbol it) {
                                Intrinsics.checkNotNullParameter(it, "it");
                                if (it == firCallableSymbol) {
                                    objectRef2.element = candidateSymbol;
                                    return ProcessorAction.STOP;
                                }
                                return ProcessorAction.NEXT;
                            }
                        };
                        FirTypeScopeKt.processOverriddenFunctions(firTypeScope2, candidateSymbol, function1);
                    }
                });
                FirCallableSymbol firCallableSymbol = (FirCallableSymbol) objectRef.element;
                if (firCallableSymbol != null) {
                    FirCallableDeclaration firCallableDeclaration = (FirCallableDeclaration) firCallableSymbol.getFir();
                    while (true) {
                        FirCallableDeclaration originalForSubstitutionOverrideAttr2 = (ClassMembersKt.isSubstitutionOverride(firCallableDeclaration) || (firCallableDeclaration.getOrigin() instanceof FirDeclarationOrigin.Synthetic)) ? ClassMembersKt.getOriginalForSubstitutionOverrideAttr(firCallableDeclaration) : null;
                        if (originalForSubstitutionOverrideAttr2 == null) {
                            break;
                        } else {
                            firCallableDeclaration = originalForSubstitutionOverrideAttr2;
                        }
                    }
                    FirCallableSymbol<? extends FirCallableDeclaration> symbol2 = firCallableDeclaration.getSymbol();
                    if (symbol2 == null) {
                        throw new NullPointerException("null cannot be cast to non-null type org.jetbrains.kotlin.fir.symbols.impl.FirNamedFunctionSymbol");
                    }
                    firNamedFunctionSymbol = (FirNamedFunctionSymbol) symbol2;
                }
                FirNamedFunctionSymbol firNamedFunctionSymbol3 = firNamedFunctionSymbol;
                if (firNamedFunctionSymbol3 == null || (coneClassLikeLookupTagDispatchReceiverClassLookupTagOrNull = ClassMembersKt.dispatchReceiverClassLookupTagOrNull((firNamedFunctionSymbol2 = firNamedFunctionSymbol3))) == null) {
                    return;
                }
                IrSimpleFunction irSimpleFunctionGenerateDelegatedFunction = DelegatedMemberGenerator.this.generateDelegatedFunction(subClass, firSubClass, (FirSimpleFunction) functionSymbol.getFir());
                IrSimpleFunction irSimpleFunction = irSimpleFunctionGenerateDelegatedFunction;
                DelegatedMemberGenerator.this.bodiesInfo.add(new DeclarationBodyInfo(irSimpleFunction, irField, firNamedFunctionSymbol2, coneClassLikeLookupTagDispatchReceiverClassLookupTagOrNull));
                DelegatedMemberGenerator.this.getDeclarationStorage().cacheDelegationFunction$fir2ir((FirSimpleFunction) functionSymbol.getFir(), irSimpleFunctionGenerateDelegatedFunction);
                IrDeclarationsKt.addMember(subClass, irSimpleFunction);
            }
        });
        FirContainingNamesAwareScopeKt.processAllProperties(firTypeScope, new Function1<FirVariableSymbol<?>, Unit>() { // from class: org.jetbrains.kotlin.fir.backend.generators.DelegatedMemberGenerator.generate.2
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(FirVariableSymbol<?> firVariableSymbol) {
                invoke2(firVariableSymbol);
                return Unit.INSTANCE;
            }

            /* JADX WARN: Multi-variable type inference failed */
            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(FirVariableSymbol<?> propertySymbol) {
                FirProperty firProperty;
                FirPropertySymbol firPropertySymbol;
                FirPropertySymbol firPropertySymbol2;
                ConeClassLikeLookupTag coneClassLikeLookupTagDispatchReceiverClassLookupTagOrNull;
                Intrinsics.checkNotNullParameter(propertySymbol, "propertySymbol");
                if ((propertySymbol instanceof FirPropertySymbol) && (firProperty = (FirProperty) Companion.access$unwrapDelegateTarget(DelegatedMemberGenerator.INSTANCE, propertySymbol, DelegatedMemberGenerator.this, lookupTag, firField)) != null) {
                    FirProperty firProperty2 = firProperty;
                    while (true) {
                        firPropertySymbol = null;
                        FirCallableDeclaration originalForSubstitutionOverrideAttr = (ClassMembersKt.isSubstitutionOverride(firProperty2) || (firProperty2.getOrigin() instanceof FirDeclarationOrigin.Synthetic)) ? ClassMembersKt.getOriginalForSubstitutionOverrideAttr(firProperty2) : null;
                        if (originalForSubstitutionOverrideAttr == null) {
                            break;
                        } else {
                            firProperty2 = originalForSubstitutionOverrideAttr;
                        }
                    }
                    final FirPropertySymbol symbol = ((FirProperty) firProperty2).getSymbol();
                    final FirTypeScope firTypeScope2 = firTypeScopeScope;
                    final Ref.ObjectRef objectRef = new Ref.ObjectRef();
                    Name name2 = symbol.getName();
                    final Function1<FirPropertySymbol, Unit> function1 = new Function1<FirPropertySymbol, Unit>() { // from class: org.jetbrains.kotlin.fir.backend.generators.DelegatedMemberGenerator$generate$2$invoke$$inlined$findDelegateToSymbol$1
                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                        {
                            super(1);
                        }

                        @Override // kotlin.jvm.functions.Function1
                        public /* bridge */ /* synthetic */ Unit invoke(FirPropertySymbol firPropertySymbol3) {
                            invoke(firPropertySymbol3);
                            return Unit.INSTANCE;
                        }

                        /* JADX WARN: Multi-variable type inference failed */
                        public final void invoke(final FirPropertySymbol candidateSymbol) {
                            Intrinsics.checkNotNullParameter(candidateSymbol, "candidateSymbol");
                            if (objectRef.element != 0) {
                                return;
                            }
                            if (candidateSymbol == symbol) {
                                objectRef.element = candidateSymbol;
                                return;
                            }
                            final FirCallableSymbol firCallableSymbol = symbol;
                            final Ref.ObjectRef objectRef2 = objectRef;
                            Function1<FirPropertySymbol, ProcessorAction> function2 = new Function1<FirPropertySymbol, ProcessorAction>() { // from class: org.jetbrains.kotlin.fir.backend.generators.DelegatedMemberGenerator$generate$2$invoke$$inlined$findDelegateToSymbol$1.1
                                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                {
                                    super(1);
                                }

                                /* JADX WARN: Type inference failed for: r0v2, types: [T, org.jetbrains.kotlin.fir.symbols.impl.FirCallableSymbol] */
                                @Override // kotlin.jvm.functions.Function1
                                public final ProcessorAction invoke(FirPropertySymbol it) {
                                    Intrinsics.checkNotNullParameter(it, "it");
                                    if (it == firCallableSymbol) {
                                        objectRef2.element = candidateSymbol;
                                        return ProcessorAction.STOP;
                                    }
                                    return ProcessorAction.NEXT;
                                }
                            };
                            FirTypeScopeKt.processOverriddenProperties(firTypeScope2, candidateSymbol, function2);
                        }
                    };
                    firTypeScope2.processPropertiesByName(name2, new Function1<FirVariableSymbol<?>, Unit>() { // from class: org.jetbrains.kotlin.fir.backend.generators.DelegatedMemberGenerator$generate$2$delegateToSymbol$1$1
                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                        /* JADX WARN: Multi-variable type inference failed */
                        {
                            super(1);
                        }

                        @Override // kotlin.jvm.functions.Function1
                        public /* bridge */ /* synthetic */ Unit invoke(FirVariableSymbol<?> firVariableSymbol) {
                            invoke2(firVariableSymbol);
                            return Unit.INSTANCE;
                        }

                        /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
                        public final void invoke2(FirVariableSymbol<?> it) {
                            Intrinsics.checkNotNullParameter(it, "it");
                            if (it instanceof FirPropertySymbol) {
                                function1.invoke(it);
                            }
                        }
                    });
                    FirCallableSymbol firCallableSymbol = (FirCallableSymbol) objectRef.element;
                    if (firCallableSymbol != null) {
                        FirCallableDeclaration firCallableDeclaration = (FirCallableDeclaration) firCallableSymbol.getFir();
                        while (true) {
                            FirCallableDeclaration originalForSubstitutionOverrideAttr2 = (ClassMembersKt.isSubstitutionOverride(firCallableDeclaration) || (firCallableDeclaration.getOrigin() instanceof FirDeclarationOrigin.Synthetic)) ? ClassMembersKt.getOriginalForSubstitutionOverrideAttr(firCallableDeclaration) : null;
                            if (originalForSubstitutionOverrideAttr2 == null) {
                                break;
                            } else {
                                firCallableDeclaration = originalForSubstitutionOverrideAttr2;
                            }
                        }
                        FirCallableSymbol<? extends FirCallableDeclaration> symbol2 = firCallableDeclaration.getSymbol();
                        if (symbol2 == null) {
                            throw new NullPointerException("null cannot be cast to non-null type org.jetbrains.kotlin.fir.symbols.impl.FirPropertySymbol");
                        }
                        firPropertySymbol = (FirPropertySymbol) symbol2;
                    }
                    FirPropertySymbol firPropertySymbol3 = firPropertySymbol;
                    if (firPropertySymbol3 == null || (coneClassLikeLookupTagDispatchReceiverClassLookupTagOrNull = ClassMembersKt.dispatchReceiverClassLookupTagOrNull((firPropertySymbol2 = firPropertySymbol3))) == null) {
                        return;
                    }
                    FirPropertySymbol firPropertySymbol4 = (FirPropertySymbol) propertySymbol;
                    IrProperty irPropertyGenerateDelegatedProperty = DelegatedMemberGenerator.this.generateDelegatedProperty(subClass, firSubClass, (FirProperty) firPropertySymbol4.getFir());
                    IrProperty irProperty = irPropertyGenerateDelegatedProperty;
                    DelegatedMemberGenerator.this.bodiesInfo.add(new DeclarationBodyInfo(irProperty, irField, firPropertySymbol2, coneClassLikeLookupTagDispatchReceiverClassLookupTagOrNull));
                    DelegatedMemberGenerator.this.getDeclarationStorage().cacheDelegatedProperty$fir2ir((FirProperty) firPropertySymbol4.getFir(), irPropertyGenerateDelegatedProperty);
                    IrDeclarationsKt.addMember(subClass, irProperty);
                }
            }
        });
    }

    private static final Set<IrClass> bindDelegatedMembersOverriddenSymbols$lambda$0(Lazy<? extends Set<IrClass>> lazy) {
        return lazy.getValue();
    }

    public final void bindDelegatedMembersOverriddenSymbols(final IrClass irClass) {
        ArrayList arrayListEmptyList;
        Intrinsics.checkNotNullParameter(irClass, "irClass");
        Lazy lazy = LazyKt.lazy(LazyThreadSafetyMode.NONE, (Function0) new Function0<Set<IrClass>>() { // from class: org.jetbrains.kotlin.fir.backend.generators.DelegatedMemberGenerator$bindDelegatedMembersOverriddenSymbols$superClasses$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final Set<IrClass> invoke() {
                List<IrType> superTypes = irClass.getSuperTypes();
                LinkedHashSet linkedHashSet = new LinkedHashSet();
                Iterator<T> it = superTypes.iterator();
                while (it.hasNext()) {
                    IrClassifierSymbol classifierOrNull = IrTypesKt.getClassifierOrNull((IrType) it.next());
                    IrSymbolOwner owner = classifierOrNull != null ? classifierOrNull.getOwner() : null;
                    IrClass irClass2 = owner instanceof IrClass ? (IrClass) owner : null;
                    if (irClass2 != null) {
                        linkedHashSet.add(irClass2);
                    }
                }
                return linkedHashSet;
            }
        });
        for (IrDeclaration irDeclaration : irClass.getDeclarations()) {
            if (Intrinsics.areEqual(irDeclaration.getOrigin(), IrDeclarationOrigin.DELEGATED_MEMBER.INSTANCE)) {
                ArrayList arrayListEmptyList2 = null;
                if (irDeclaration instanceof IrSimpleFunction) {
                    IrSimpleFunction irSimpleFunction = (IrSimpleFunction) irDeclaration;
                    List<FirNamedFunctionSymbol> list = this.baseFunctionSymbols.get(irDeclaration);
                    if (list != null) {
                        ArrayList arrayList = new ArrayList();
                        Iterator<T> it = list.iterator();
                        while (it.hasNext()) {
                            CollectionsKt.addAll(arrayList, getFakeOverrideGenerator().getOverriddenSymbolsInSupertypes$fir2ir((FirNamedFunctionSymbol) it.next(), bindDelegatedMembersOverriddenSymbols$lambda$0(lazy)));
                        }
                        ArrayList arrayList2 = new ArrayList();
                        for (Object obj : arrayList) {
                            if (!Intrinsics.areEqual(((IrSimpleFunctionSymbol) obj).getOwner(), irDeclaration)) {
                                arrayList2.add(obj);
                            }
                        }
                        arrayListEmptyList2 = arrayList2;
                    }
                    if (arrayListEmptyList2 == null) {
                        arrayListEmptyList2 = CollectionsKt.emptyList();
                    }
                    irSimpleFunction.setOverriddenSymbols(arrayListEmptyList2);
                } else if (irDeclaration instanceof IrProperty) {
                    IrProperty irProperty = (IrProperty) irDeclaration;
                    List<FirPropertySymbol> list2 = this.basePropertySymbols.get(irDeclaration);
                    if (list2 != null) {
                        ArrayList arrayList3 = new ArrayList();
                        Iterator<T> it2 = list2.iterator();
                        while (it2.hasNext()) {
                            CollectionsKt.addAll(arrayList3, getFakeOverrideGenerator().getOverriddenSymbolsInSupertypes$fir2ir((FirPropertySymbol) it2.next(), bindDelegatedMembersOverriddenSymbols$lambda$0(lazy)));
                        }
                        ArrayList arrayList4 = new ArrayList();
                        for (Object obj2 : arrayList3) {
                            if (!Intrinsics.areEqual(((IrPropertySymbol) obj2).getOwner(), irDeclaration)) {
                                arrayList4.add(obj2);
                            }
                        }
                        arrayListEmptyList = arrayList4;
                    } else {
                        arrayListEmptyList = null;
                    }
                    if (arrayListEmptyList == null) {
                        arrayListEmptyList = CollectionsKt.emptyList();
                    }
                    irProperty.setOverriddenSymbols(arrayListEmptyList);
                    IrSimpleFunction getter = irProperty.getGetter();
                    Intrinsics.checkNotNull(getter);
                    List<IrPropertySymbol> overriddenSymbols = irProperty.getOverriddenSymbols();
                    ArrayList arrayList5 = new ArrayList();
                    Iterator<T> it3 = overriddenSymbols.iterator();
                    while (it3.hasNext()) {
                        IrSimpleFunction getter2 = ((IrPropertySymbol) it3.next()).getOwner().getGetter();
                        IrSimpleFunctionSymbol symbol = getter2 != null ? getter2.getSymbol() : null;
                        if (symbol != null) {
                            arrayList5.add(symbol);
                        }
                    }
                    getter.setOverriddenSymbols(arrayList5);
                    if (irProperty.getIsVar()) {
                        IrSimpleFunction setter = irProperty.getSetter();
                        Intrinsics.checkNotNull(setter);
                        List<IrPropertySymbol> overriddenSymbols2 = irProperty.getOverriddenSymbols();
                        ArrayList arrayList6 = new ArrayList();
                        Iterator<T> it4 = overriddenSymbols2.iterator();
                        while (it4.hasNext()) {
                            IrSimpleFunction setter2 = ((IrPropertySymbol) it4.next()).getOwner().getSetter();
                            IrSimpleFunctionSymbol symbol2 = setter2 != null ? setter2.getSymbol() : null;
                            if (symbol2 != null) {
                                arrayList6.add(symbol2);
                            }
                        }
                        setter.setOverriddenSymbols(arrayList6);
                    }
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final IrSimpleFunction generateDelegatedFunction(IrClass subClass, FirClass firSubClass, FirSimpleFunction delegateOverride) {
        IrSimpleFunction irSimpleFunctionCreateIrFunction$default = Fir2IrDeclarationStorage.createIrFunction$default(getDeclarationStorage(), delegateOverride, subClass, null, IrDeclarationOrigin.DELEGATED_MEMBER.INSTANCE, false, firSubClass.getSymbol().getLookupTag(), 20, null);
        final ArrayList arrayList = new ArrayList();
        ConversionUtilsKt.processOverriddenFunctionSymbols(this, delegateOverride, firSubClass, new Function1<FirNamedFunctionSymbol, Unit>() { // from class: org.jetbrains.kotlin.fir.backend.generators.DelegatedMemberGenerator.generateDelegatedFunction.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(FirNamedFunctionSymbol firNamedFunctionSymbol) {
                invoke2(firNamedFunctionSymbol);
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(FirNamedFunctionSymbol it) {
                Intrinsics.checkNotNullParameter(it, "it");
                arrayList.add(it);
            }
        });
        this.baseFunctionSymbols.put(irSimpleFunctionCreateIrFunction$default, arrayList);
        getAnnotationGenerator().generate(irSimpleFunctionCreateIrFunction$default, delegateOverride);
        return irSimpleFunctionCreateIrFunction$default;
    }

    /* JADX WARN: Multi-variable type inference failed */
    private final IrBlockBody createDelegateBody(IrField irField, IrSimpleFunction delegateFunction, IrSimpleFunction superFunction, boolean callTypeCanBeNullable) {
        IrGetFieldImpl irGetFieldImplImplicitCastOrExpression$fir2ir;
        int i = -2;
        int i2 = -2;
        IrBlockBody irBlockBodyCreateBlockBody = getIrFactory().createBlockBody(-2, -2);
        IrCallImpl irCallImpl = new IrCallImpl(i, i2, superFunction.getReturnType(), superFunction.getSymbol(), superFunction.getTypeParameters().size(), superFunction.getValueParameters().size(), null, null, 192, null);
        IrFieldSymbol symbol = irField.getSymbol();
        IrType type = irField.getType();
        IrValueParameter dispatchReceiverParameter = delegateFunction.getDispatchReceiverParameter();
        IrType type2 = dispatchReceiverParameter != null ? dispatchReceiverParameter.getType() : null;
        Intrinsics.checkNotNull(type2);
        IrValueParameter dispatchReceiverParameter2 = delegateFunction.getDispatchReceiverParameter();
        IrValueParameterSymbol symbol2 = dispatchReceiverParameter2 != null ? dispatchReceiverParameter2.getSymbol() : null;
        Intrinsics.checkNotNull(symbol2);
        IrGetFieldImpl irGetFieldImpl = new IrGetFieldImpl(i, i2, symbol, type, new IrGetValueImpl(i, i2, type2, symbol2, null, 16, null), null, 0 == true ? 1 : 0, 96, null);
        IrDeclarationParent parent = superFunction.getParent();
        IrClass irClass = parent instanceof IrClass ? (IrClass) parent : null;
        if (irClass == null || IrTypeUtilsKt.isSubtypeOfClass(irField.getType(), irClass.getSymbol())) {
            irGetFieldImplImplicitCastOrExpression$fir2ir = irGetFieldImpl;
        } else {
            IrValueParameter dispatchReceiverParameter3 = superFunction.getDispatchReceiverParameter();
            Intrinsics.checkNotNull(dispatchReceiverParameter3);
            irGetFieldImplImplicitCastOrExpression$fir2ir = Fir2IrImplicitCastInserter.INSTANCE.implicitCastOrExpression$fir2ir(irGetFieldImpl, dispatchReceiverParameter3.getType());
        }
        irCallImpl.setDispatchReceiver(irGetFieldImplImplicitCastOrExpression$fir2ir);
        IrValueParameter extensionReceiverParameter = delegateFunction.getExtensionReceiverParameter();
        irCallImpl.setExtensionReceiver(extensionReceiverParameter != null ? new IrGetValueImpl(i, i2, extensionReceiverParameter.getType(), extensionReceiverParameter.getSymbol(), null, 16, null) : null);
        for (IrValueParameter irValueParameter : delegateFunction.getValueParameters()) {
            irCallImpl.putValueArgument(irValueParameter.getIndex(), new IrGetValueImpl(i, i2, irValueParameter.getType(), irValueParameter.getSymbol(), null, 16, null));
        }
        for (IrTypeParameter irTypeParameter : superFunction.getTypeParameters()) {
            irCallImpl.putTypeArgument(irTypeParameter.getIndex(), new IrSimpleTypeImpl((IrClassifierSymbol) delegateFunction.getTypeParameters().get(irTypeParameter.getIndex()).getSymbol(), false, CollectionsKt.emptyList(), CollectionsKt.emptyList(), (IrTypeAbbreviation) null, 16, (DefaultConstructorMarker) null));
        }
        IrExpression irExpressionImplicitNotNullCast$fir2ir = (!callTypeCanBeNullable || IrTypeUtilsKt.isNullable(delegateFunction.getReturnType())) ? irCallImpl : Fir2IrImplicitCastInserter.INSTANCE.implicitNotNullCast$fir2ir(irCallImpl);
        if (IrTypePredicatesKt.isUnit(superFunction.getReturnType()) || IrTypePredicatesKt.isNothing(superFunction.getReturnType())) {
            irBlockBodyCreateBlockBody.getStatements().add(irExpressionImplicitNotNullCast$fir2ir);
        } else {
            irBlockBodyCreateBlockBody.getStatements().add(new IrReturnImpl(-2, -2, getIrBuiltIns().getNothingType(), delegateFunction.getSymbol(), irExpressionImplicitNotNullCast$fir2ir));
        }
        return irBlockBodyCreateBlockBody;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final IrProperty generateDelegatedProperty(IrClass subClass, FirClass firSubClass, FirProperty firDelegateProperty) {
        IrProperty irPropertyCreateIrProperty$default = Fir2IrDeclarationStorage.createIrProperty$default(getDeclarationStorage(), firDelegateProperty, subClass, null, IrDeclarationOrigin.DELEGATED_MEMBER.INSTANCE, false, firSubClass.getSymbol().getLookupTag(), 20, null);
        final ArrayList arrayList = new ArrayList();
        ConversionUtilsKt.processOverriddenPropertySymbols(this, firDelegateProperty, firSubClass, new Function1<FirPropertySymbol, Unit>() { // from class: org.jetbrains.kotlin.fir.backend.generators.DelegatedMemberGenerator.generateDelegatedProperty.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(FirPropertySymbol firPropertySymbol) {
                invoke2(firPropertySymbol);
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(FirPropertySymbol it) {
                Intrinsics.checkNotNullParameter(it, "it");
                arrayList.add(it);
            }
        });
        this.basePropertySymbols.put(irPropertyCreateIrProperty$default, arrayList);
        return irPropertyCreateIrProperty$default;
    }

    /* JADX INFO: compiled from: DelegatedMemberGenerator.kt */
    @Metadata(d1 = {"\u0000D\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0018\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH\u0002J\f\u0010\u000b\u001a\u00020\u0006*\u00020\fH\u0002JA\u0010\r\u001a\u0004\u0018\u0001H\u000e\"\u000e\b\u0000\u0010\u000f*\b\u0012\u0004\u0012\u0002H\u000e0\u0010\"\b\b\u0001\u0010\u000e*\u00020\b*\u0002H\u000f2\u0006\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\u0015H\u0002R\u00020\u0011¢\u0006\u0002\u0010\u0016R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0017"}, d2 = {"Lorg/jetbrains/kotlin/fir/backend/generators/DelegatedMemberGenerator$Companion;", "", "()V", "PLATFORM_DEPENDENT_CLASS_ID", "Lorg/jetbrains/kotlin/name/ClassId;", "shouldSkipDelegationFor", "", "unwrapped", "Lorg/jetbrains/kotlin/fir/declarations/FirCallableDeclaration;", "session", "Lorg/jetbrains/kotlin/fir/FirSession;", "isDefaultJavaMethod", "Lorg/jetbrains/kotlin/fir/declarations/FirSimpleFunction;", "unwrapDelegateTarget", "D", ExifInterface.LATITUDE_SOUTH, "Lorg/jetbrains/kotlin/fir/symbols/impl/FirCallableSymbol;", "Lorg/jetbrains/kotlin/fir/backend/Fir2IrComponents;", "subClassLookupTag", "Lorg/jetbrains/kotlin/fir/symbols/ConeClassLikeLookupTag;", "firField", "Lorg/jetbrains/kotlin/fir/declarations/FirField;", "(Lorg/jetbrains/kotlin/fir/backend/Fir2IrComponents;Lorg/jetbrains/kotlin/fir/symbols/impl/FirCallableSymbol;Lorg/jetbrains/kotlin/fir/symbols/ConeClassLikeLookupTag;Lorg/jetbrains/kotlin/fir/declarations/FirField;)Lorg/jetbrains/kotlin/fir/declarations/FirCallableDeclaration;", "fir2ir"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public static final /* synthetic */ FirCallableDeclaration access$unwrapDelegateTarget(Companion companion, FirCallableSymbol firCallableSymbol, Fir2IrComponents fir2IrComponents, ConeClassLikeLookupTag coneClassLikeLookupTag, FirField firField) {
            return companion.unwrapDelegateTarget(fir2IrComponents, firCallableSymbol, coneClassLikeLookupTag, firField);
        }

        /* JADX WARN: Type inference fix 'apply assigned field type' failed
        java.lang.UnsupportedOperationException: ArgType.getObject(), call class: class jadx.core.dex.instructions.args.ArgType$UnknownArg
        	at jadx.core.dex.instructions.args.ArgType.getObject(ArgType.java:596)
        	at jadx.core.dex.attributes.nodes.ClassTypeVarsAttr.getTypeVarsMapFor(ClassTypeVarsAttr.java:35)
        	at jadx.core.dex.nodes.utils.TypeUtils.replaceClassGenerics(TypeUtils.java:177)
        	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.insertExplicitUseCast(FixTypesVisitor.java:397)
        	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.tryFieldTypeWithNewCasts(FixTypesVisitor.java:359)
        	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.applyFieldType(FixTypesVisitor.java:309)
        	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.visit(FixTypesVisitor.java:94)
         */
        private final <S extends FirCallableSymbol<D>, D extends FirCallableDeclaration> D unwrapDelegateTarget(Fir2IrComponents fir2IrComponents, S s, ConeClassLikeLookupTag coneClassLikeLookupTag, FirField firField) {
            DelegatedWrapperData delegatedWrapperData;
            FirDeclaration fir = s.getFir();
            FirCallableDeclaration firCallableDeclaration = fir instanceof FirCallableDeclaration ? (FirCallableDeclaration) fir : null;
            if (firCallableDeclaration == null || (delegatedWrapperData = ClassMembersKt.getDelegatedWrapperData(firCallableDeclaration)) == null || !Intrinsics.areEqual(delegatedWrapperData.getContainingClass(), coneClassLikeLookupTag) || !Intrinsics.areEqual(delegatedWrapperData.getDelegateField(), firField)) {
                return null;
            }
            FirCallableDeclaration wrapped = delegatedWrapperData.getWrapped();
            if (!(wrapped instanceof FirCallableDeclaration)) {
                wrapped = null;
            }
            if (wrapped == null) {
                return null;
            }
            FirCallableSymbol<? extends FirCallableDeclaration> symbol = wrapped.getSymbol();
            if (!(symbol instanceof FirCallableSymbol)) {
                symbol = null;
            }
            if (symbol == null) {
                return null;
            }
            D fir2 = ConversionUtilsKt.unwrapCallRepresentative$default(fir2IrComponents, symbol, null, 2, null).getFir();
            Intrinsics.checkNotNull(fir2, "null cannot be cast to non-null type D of org.jetbrains.kotlin.fir.backend.generators.DelegatedMemberGenerator.Companion.unwrapDelegateTarget");
            D d = (D) fir2;
            if (DelegatedMemberGenerator.INSTANCE.shouldSkipDelegationFor(d, fir2IrComponents.getSession())) {
                return null;
            }
            return d;
        }

        private final boolean shouldSkipDelegationFor(FirCallableDeclaration unwrapped, FirSession session) {
            if (!(unwrapped instanceof FirSimpleFunction) || !isDefaultJavaMethod((FirSimpleFunction) unwrapped)) {
                FirCallableDeclaration firCallableDeclaration = unwrapped;
                if (!FirAnnotationUtilsKt.hasAnnotation((FirDeclaration) firCallableDeclaration, JvmNames.INSTANCE.getJVM_DEFAULT_CLASS_ID(), session) && !FirAnnotationUtilsKt.hasAnnotation((FirDeclaration) firCallableDeclaration, DelegatedMemberGenerator.PLATFORM_DEPENDENT_CLASS_ID, session)) {
                    return false;
                }
            }
            return true;
        }

        private final boolean isDefaultJavaMethod(FirSimpleFunction firSimpleFunction) {
            FirCallableDeclaration originalForSubstitutionOverrideAttr;
            FirSimpleFunction firSimpleFunction2 = firSimpleFunction;
            if (!ClassMembersKt.isIntersectionOverride(firSimpleFunction2)) {
                if (!ClassMembersKt.isSubstitutionOverride(firSimpleFunction2)) {
                    return DeclarationUtilsKt.isJavaOrEnhancement(firSimpleFunction) && firSimpleFunction.getStatus().getModality() == Modality.OPEN;
                }
                originalForSubstitutionOverrideAttr = (ClassMembersKt.isSubstitutionOverride(firSimpleFunction2) || (firSimpleFunction2.getOrigin() instanceof FirDeclarationOrigin.Synthetic)) ? ClassMembersKt.getOriginalForSubstitutionOverrideAttr(firSimpleFunction2) : null;
                Intrinsics.checkNotNull(originalForSubstitutionOverrideAttr);
                return isDefaultJavaMethod((FirSimpleFunction) originalForSubstitutionOverrideAttr);
            }
            originalForSubstitutionOverrideAttr = ClassMembersKt.isIntersectionOverride(firSimpleFunction2) ? ClassMembersKt.getOriginalForIntersectionOverrideAttr(firSimpleFunction2) : null;
            Intrinsics.checkNotNull(originalForSubstitutionOverrideAttr);
            return isDefaultJavaMethod((FirSimpleFunction) originalForSubstitutionOverrideAttr);
        }
    }

    static {
        ClassId classId = ClassId.topLevel(new FqName("kotlin.internal.PlatformDependent"));
        Intrinsics.checkNotNullExpressionValue(classId, "topLevel(...)");
        PLATFORM_DEPENDENT_CLASS_ID = classId;
    }
}
