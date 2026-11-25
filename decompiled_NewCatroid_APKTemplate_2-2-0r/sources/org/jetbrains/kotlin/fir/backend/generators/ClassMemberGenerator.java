package org.jetbrains.kotlin.fir.backend.generators;

import androidx.exifinterface.media.ExifInterface;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.KtSourceElement;
import org.jetbrains.kotlin.KtSourceElementKt;
import org.jetbrains.kotlin.descriptors.ClassKind;
import org.jetbrains.kotlin.descriptors.Visibilities;
import org.jetbrains.kotlin.fir.ClassMembersKt;
import org.jetbrains.kotlin.fir.FirSession;
import org.jetbrains.kotlin.fir.backend.ConversionUtilsKt;
import org.jetbrains.kotlin.fir.backend.Fir2IrAnnotationsFromPluginRegistrar;
import org.jetbrains.kotlin.fir.backend.Fir2IrBuiltIns;
import org.jetbrains.kotlin.fir.backend.Fir2IrClassifierStorage;
import org.jetbrains.kotlin.fir.backend.Fir2IrComponents;
import org.jetbrains.kotlin.fir.backend.Fir2IrConfiguration;
import org.jetbrains.kotlin.fir.backend.Fir2IrConversionScope;
import org.jetbrains.kotlin.fir.backend.Fir2IrConverter;
import org.jetbrains.kotlin.fir.backend.Fir2IrDeclarationStorage;
import org.jetbrains.kotlin.fir.backend.Fir2IrExtensions;
import org.jetbrains.kotlin.fir.backend.Fir2IrTypeConverter;
import org.jetbrains.kotlin.fir.backend.Fir2IrVisibilityConverter;
import org.jetbrains.kotlin.fir.backend.Fir2IrVisitor;
import org.jetbrains.kotlin.fir.backend.IrBuiltInsOverFir;
import org.jetbrains.kotlin.fir.declarations.DestructuringDeclarationAttributesKt;
import org.jetbrains.kotlin.fir.declarations.FirBackingField;
import org.jetbrains.kotlin.fir.declarations.FirClass;
import org.jetbrains.kotlin.fir.declarations.FirConstructor;
import org.jetbrains.kotlin.fir.declarations.FirDeclaration;
import org.jetbrains.kotlin.fir.declarations.FirField;
import org.jetbrains.kotlin.fir.declarations.FirFunction;
import org.jetbrains.kotlin.fir.declarations.FirMemberDeclaration;
import org.jetbrains.kotlin.fir.declarations.FirProperty;
import org.jetbrains.kotlin.fir.declarations.FirPropertyAccessor;
import org.jetbrains.kotlin.fir.declarations.FirRegularClass;
import org.jetbrains.kotlin.fir.declarations.FirSimpleFunction;
import org.jetbrains.kotlin.fir.declarations.FirTypeAlias;
import org.jetbrains.kotlin.fir.declarations.FirValueParameter;
import org.jetbrains.kotlin.fir.declarations.comparators.FirCallableDeclarationComparator;
import org.jetbrains.kotlin.fir.declarations.comparators.FirMemberDeclarationComparator;
import org.jetbrains.kotlin.fir.declarations.impl.FirDefaultPropertyGetter;
import org.jetbrains.kotlin.fir.declarations.impl.FirDefaultPropertySetter;
import org.jetbrains.kotlin.fir.expressions.FirBlock;
import org.jetbrains.kotlin.fir.expressions.FirDelegatedConstructorCall;
import org.jetbrains.kotlin.fir.expressions.FirExpression;
import org.jetbrains.kotlin.fir.expressions.impl.FirNoReceiverExpression;
import org.jetbrains.kotlin.fir.extensions.FirDeclarationGenerationExtensionKt;
import org.jetbrains.kotlin.fir.extensions.FirExtensionServiceKt;
import org.jetbrains.kotlin.fir.extensions.GeneratedDeclarationsUtilsKt;
import org.jetbrains.kotlin.fir.references.FirReferenceUtilsKt;
import org.jetbrains.kotlin.fir.resolve.ScopeSession;
import org.jetbrains.kotlin.fir.resolve.TypeExpansionUtilsKt;
import org.jetbrains.kotlin.fir.signaturer.FirBasedSignatureComposer;
import org.jetbrains.kotlin.fir.symbols.ConeClassLikeLookupTag;
import org.jetbrains.kotlin.fir.symbols.impl.FirCallableSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirConstructorSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirFunctionSymbol;
import org.jetbrains.kotlin.fir.types.ConeClassLikeType;
import org.jetbrains.kotlin.fir.types.ConeKotlinType;
import org.jetbrains.kotlin.fir.types.ConeKotlinTypeProjection;
import org.jetbrains.kotlin.fir.types.ConeTypeProjection;
import org.jetbrains.kotlin.fir.types.FirTypeRef;
import org.jetbrains.kotlin.fir.types.FirTypeUtilsKt;
import org.jetbrains.kotlin.ir.IrLock;
import org.jetbrains.kotlin.ir.IrStatement;
import org.jetbrains.kotlin.ir.declarations.IrClass;
import org.jetbrains.kotlin.ir.declarations.IrConstructor;
import org.jetbrains.kotlin.ir.declarations.IrDeclaration;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationOrigin;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationParent;
import org.jetbrains.kotlin.ir.declarations.IrFactory;
import org.jetbrains.kotlin.ir.declarations.IrFactoryHelpersKt;
import org.jetbrains.kotlin.ir.declarations.IrField;
import org.jetbrains.kotlin.ir.declarations.IrFunction;
import org.jetbrains.kotlin.ir.declarations.IrProperty;
import org.jetbrains.kotlin.ir.declarations.IrScript;
import org.jetbrains.kotlin.ir.declarations.IrSimpleFunction;
import org.jetbrains.kotlin.ir.declarations.IrValueParameter;
import org.jetbrains.kotlin.ir.expressions.IrBlockBody;
import org.jetbrains.kotlin.ir.expressions.IrExpression;
import org.jetbrains.kotlin.ir.expressions.IrFieldAccessExpression;
import org.jetbrains.kotlin.ir.expressions.IrMemberAccessExpression;
import org.jetbrains.kotlin.ir.expressions.IrStatementOrigin;
import org.jetbrains.kotlin.ir.expressions.IrSyntheticBodyKind;
import org.jetbrains.kotlin.ir.expressions.impl.IrDelegatingConstructorCallImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrEnumConstructorCallImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrErrorCallExpressionImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrGetFieldImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrGetValueImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrInstanceInitializerCallImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrReturnImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrSetFieldImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrSyntheticBodyImpl;
import org.jetbrains.kotlin.ir.linkage.IrProvider;
import org.jetbrains.kotlin.ir.symbols.IrClassSymbol;
import org.jetbrains.kotlin.ir.symbols.IrConstructorSymbol;
import org.jetbrains.kotlin.ir.symbols.IrFieldSymbol;
import org.jetbrains.kotlin.ir.symbols.IrFunctionSymbol;
import org.jetbrains.kotlin.ir.symbols.IrPropertySymbol;
import org.jetbrains.kotlin.ir.types.IrType;
import org.jetbrains.kotlin.ir.util.AdditionalIrUtilsKt;
import org.jetbrains.kotlin.ir.util.IrUtilsKt;
import org.jetbrains.kotlin.ir.util.SymbolTable;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.resolve.DataClassResolver;

/* JADX INFO: compiled from: ClassMemberGenerator.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000Ø\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b\u0000\u0018\u00002\u00020\u0001B\u001d\u0012\u0006\u0010\u0002\u001a\u00020\u0001\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\u0006\u0010\u0005\u001a\u00020\u0006¢\u0006\u0002\u0010\u0007J\u001f\u0010]\u001a\u0002H^\"\b\b\u0000\u0010^*\u00020_2\u0006\u0010`\u001a\u0002H^H\u0002¢\u0006\u0002\u0010aJ\u0016\u0010b\u001a\u00020c2\u0006\u0010d\u001a\u00020e2\u0006\u0010f\u001a\u00020gJ\u0016\u0010h\u001a\u00020i2\u0006\u0010j\u001a\u00020i2\u0006\u0010k\u001a\u00020lJ1\u0010m\u001a\u0002H^\"\b\b\u0000\u0010^*\u00020n2\u0006\u0010o\u001a\u0002H^2\b\u0010p\u001a\u0004\u0018\u00010q2\b\u0010r\u001a\u0004\u0018\u00010g¢\u0006\u0002\u0010sJ \u0010t\u001a\u00020u2\u0006\u0010v\u001a\u00020u2\u0006\u0010w\u001a\u00020x2\b\u0010r\u001a\u0004\u0018\u00010gJ\u001e\u0010y\u001a\u00020c*\u00020u2\u0006\u0010w\u001a\u00020x2\b\u0010z\u001a\u0004\u0018\u00010{H\u0002J\u0014\u0010|\u001a\u00020c*\u00020}2\u0006\u0010~\u001a\u00020\u007fH\u0002JS\u0010\u0080\u0001\u001a\u00020c*\u00030\u0081\u00012\u0006\u0010w\u001a\u00020x2\n\u0010\u0082\u0001\u001a\u0005\u0018\u00010\u0083\u00012\u0007\u0010\u0084\u0001\u001a\u00020u2\b\u0010\u0085\u0001\u001a\u00030\u0086\u00012\b\u0010\u0087\u0001\u001a\u00030\u0088\u00012\b\u0010\u0089\u0001\u001a\u00030\u0088\u00012\b\u0010r\u001a\u0004\u0018\u00010gH\u0002J\u0017\u0010\u008a\u0001\u001a\u00030\u008b\u0001*\u00030\u008b\u00012\u0006\u0010`\u001a\u00020_H\u0002J\u0015\u0010\u008c\u0001\u001a\u00030\u008d\u0001*\u00030\u008e\u0001H\u0000¢\u0006\u0003\b\u008f\u0001J\u000f\u0010\u0090\u0001\u001a\u00030\u0086\u0001*\u00030\u0091\u0001H\u0002J\u000f\u0010\u0090\u0001\u001a\u00030\u0086\u0001*\u00030\u0092\u0001H\u0002R\u0012\u0010\b\u001a\u00020\tX\u0096\u0005¢\u0006\u0006\u001a\u0004\b\n\u0010\u000bR\u0012\u0010\f\u001a\u00020\rX\u0096\u0005¢\u0006\u0006\u001a\u0004\b\u000e\u0010\u000fR\u0012\u0010\u0010\u001a\u00020\u0011X\u0096\u0005¢\u0006\u0006\u001a\u0004\b\u0012\u0010\u0013R\u0012\u0010\u0014\u001a\u00020\u0015X\u0096\u0005¢\u0006\u0006\u001a\u0004\b\u0016\u0010\u0017R\u0012\u0010\u0018\u001a\u00020\u0019X\u0096\u0005¢\u0006\u0006\u001a\u0004\b\u001a\u0010\u001bR\u000e\u0010\u0002\u001a\u00020\u0001X\u0082\u0004¢\u0006\u0002\n\u0000R\u0012\u0010\u001c\u001a\u00020\u001dX\u0096\u0005¢\u0006\u0006\u001a\u0004\b\u001e\u0010\u001fR\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u0012\u0010 \u001a\u00020!X\u0096\u0005¢\u0006\u0006\u001a\u0004\b\"\u0010#R\u0012\u0010$\u001a\u00020%X\u0096\u0005¢\u0006\u0006\u001a\u0004\b&\u0010'R\u0012\u0010(\u001a\u00020)X\u0096\u0005¢\u0006\u0006\u001a\u0004\b*\u0010+R\u0012\u0010,\u001a\u00020-X\u0096\u0005¢\u0006\u0006\u001a\u0004\b.\u0010/R\u0012\u00100\u001a\u000201X\u0096\u0005¢\u0006\u0006\u001a\u0004\b2\u00103R\u0012\u00104\u001a\u000205X\u0096\u0005¢\u0006\u0006\u001a\u0004\b6\u00107R\u0012\u00108\u001a\u000209X\u0096\u0005¢\u0006\u0006\u001a\u0004\b:\u0010;R\u0018\u0010<\u001a\b\u0012\u0004\u0012\u00020>0=X\u0096\u0005¢\u0006\u0006\u001a\u0004\b?\u0010@R\u0012\u0010A\u001a\u00020BX\u0096\u0005¢\u0006\u0006\u001a\u0004\bC\u0010DR\u0012\u0010E\u001a\u00020FX\u0096\u0005¢\u0006\u0006\u001a\u0004\bG\u0010HR\u0012\u0010I\u001a\u00020JX\u0096\u0005¢\u0006\u0006\u001a\u0004\bK\u0010LR\u0012\u0010M\u001a\u00020NX\u0096\u0005¢\u0006\u0006\u001a\u0004\bO\u0010PR\u0012\u0010Q\u001a\u00020RX\u0096\u0005¢\u0006\u0006\u001a\u0004\bS\u0010TR\u0012\u0010U\u001a\u00020VX\u0096\u0005¢\u0006\u0006\u001a\u0004\bW\u0010XR\u0012\u0010Y\u001a\u00020ZX\u0096\u0005¢\u0006\u0006\u001a\u0004\b[\u0010\\R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0093\u0001"}, d2 = {"Lorg/jetbrains/kotlin/fir/backend/generators/ClassMemberGenerator;", "Lorg/jetbrains/kotlin/fir/backend/Fir2IrComponents;", "components", "visitor", "Lorg/jetbrains/kotlin/fir/backend/Fir2IrVisitor;", "conversionScope", "Lorg/jetbrains/kotlin/fir/backend/Fir2IrConversionScope;", "(Lorg/jetbrains/kotlin/fir/backend/Fir2IrComponents;Lorg/jetbrains/kotlin/fir/backend/Fir2IrVisitor;Lorg/jetbrains/kotlin/fir/backend/Fir2IrConversionScope;)V", "annotationGenerator", "Lorg/jetbrains/kotlin/fir/backend/generators/AnnotationGenerator;", "getAnnotationGenerator", "()Lorg/jetbrains/kotlin/fir/backend/generators/AnnotationGenerator;", "annotationsFromPluginRegistrar", "Lorg/jetbrains/kotlin/fir/backend/Fir2IrAnnotationsFromPluginRegistrar;", "getAnnotationsFromPluginRegistrar", "()Lorg/jetbrains/kotlin/fir/backend/Fir2IrAnnotationsFromPluginRegistrar;", "builtIns", "Lorg/jetbrains/kotlin/fir/backend/Fir2IrBuiltIns;", "getBuiltIns", "()Lorg/jetbrains/kotlin/fir/backend/Fir2IrBuiltIns;", "callGenerator", "Lorg/jetbrains/kotlin/fir/backend/generators/CallAndReferenceGenerator;", "getCallGenerator", "()Lorg/jetbrains/kotlin/fir/backend/generators/CallAndReferenceGenerator;", "classifierStorage", "Lorg/jetbrains/kotlin/fir/backend/Fir2IrClassifierStorage;", "getClassifierStorage", "()Lorg/jetbrains/kotlin/fir/backend/Fir2IrClassifierStorage;", "configuration", "Lorg/jetbrains/kotlin/fir/backend/Fir2IrConfiguration;", "getConfiguration", "()Lorg/jetbrains/kotlin/fir/backend/Fir2IrConfiguration;", "converter", "Lorg/jetbrains/kotlin/fir/backend/Fir2IrConverter;", "getConverter", "()Lorg/jetbrains/kotlin/fir/backend/Fir2IrConverter;", "declarationStorage", "Lorg/jetbrains/kotlin/fir/backend/Fir2IrDeclarationStorage;", "getDeclarationStorage", "()Lorg/jetbrains/kotlin/fir/backend/Fir2IrDeclarationStorage;", "delegatedMemberGenerator", "Lorg/jetbrains/kotlin/fir/backend/generators/DelegatedMemberGenerator;", "getDelegatedMemberGenerator", "()Lorg/jetbrains/kotlin/fir/backend/generators/DelegatedMemberGenerator;", "extensions", "Lorg/jetbrains/kotlin/fir/backend/Fir2IrExtensions;", "getExtensions", "()Lorg/jetbrains/kotlin/fir/backend/Fir2IrExtensions;", "fakeOverrideGenerator", "Lorg/jetbrains/kotlin/fir/backend/generators/FakeOverrideGenerator;", "getFakeOverrideGenerator", "()Lorg/jetbrains/kotlin/fir/backend/generators/FakeOverrideGenerator;", "irBuiltIns", "Lorg/jetbrains/kotlin/fir/backend/IrBuiltInsOverFir;", "getIrBuiltIns", "()Lorg/jetbrains/kotlin/fir/backend/IrBuiltInsOverFir;", "irFactory", "Lorg/jetbrains/kotlin/ir/declarations/IrFactory;", "getIrFactory", "()Lorg/jetbrains/kotlin/ir/declarations/IrFactory;", "irProviders", "", "Lorg/jetbrains/kotlin/ir/linkage/IrProvider;", "getIrProviders", "()Ljava/util/List;", "lock", "Lorg/jetbrains/kotlin/ir/IrLock;", "getLock", "()Lorg/jetbrains/kotlin/ir/IrLock;", "scopeSession", "Lorg/jetbrains/kotlin/fir/resolve/ScopeSession;", "getScopeSession", "()Lorg/jetbrains/kotlin/fir/resolve/ScopeSession;", "session", "Lorg/jetbrains/kotlin/fir/FirSession;", "getSession", "()Lorg/jetbrains/kotlin/fir/FirSession;", "signatureComposer", "Lorg/jetbrains/kotlin/fir/signaturer/FirBasedSignatureComposer;", "getSignatureComposer", "()Lorg/jetbrains/kotlin/fir/signaturer/FirBasedSignatureComposer;", "symbolTable", "Lorg/jetbrains/kotlin/ir/util/SymbolTable;", "getSymbolTable", "()Lorg/jetbrains/kotlin/ir/util/SymbolTable;", "typeConverter", "Lorg/jetbrains/kotlin/fir/backend/Fir2IrTypeConverter;", "getTypeConverter", "()Lorg/jetbrains/kotlin/fir/backend/Fir2IrTypeConverter;", "visibilityConverter", "Lorg/jetbrains/kotlin/fir/backend/Fir2IrVisibilityConverter;", "getVisibilityConverter", "()Lorg/jetbrains/kotlin/fir/backend/Fir2IrVisibilityConverter;", "applyParentFromStackTo", ExifInterface.GPS_DIRECTION_TRUE, "Lorg/jetbrains/kotlin/ir/declarations/IrDeclaration;", "declaration", "(Lorg/jetbrains/kotlin/ir/declarations/IrDeclaration;)Lorg/jetbrains/kotlin/ir/declarations/IrDeclaration;", "convertClassContent", "", "irClass", "Lorg/jetbrains/kotlin/ir/declarations/IrClass;", "klass", "Lorg/jetbrains/kotlin/fir/declarations/FirClass;", "convertFieldContent", "Lorg/jetbrains/kotlin/ir/declarations/IrField;", "irField", "field", "Lorg/jetbrains/kotlin/fir/declarations/FirField;", "convertFunctionContent", "Lorg/jetbrains/kotlin/ir/declarations/IrFunction;", "irFunction", "firFunction", "Lorg/jetbrains/kotlin/fir/declarations/FirFunction;", "containingClass", "(Lorg/jetbrains/kotlin/ir/declarations/IrFunction;Lorg/jetbrains/kotlin/fir/declarations/FirFunction;Lorg/jetbrains/kotlin/fir/declarations/FirClass;)Lorg/jetbrains/kotlin/ir/declarations/IrFunction;", "convertPropertyContent", "Lorg/jetbrains/kotlin/ir/declarations/IrProperty;", "irProperty", "property", "Lorg/jetbrains/kotlin/fir/declarations/FirProperty;", "initializeBackingField", "initializerExpression", "Lorg/jetbrains/kotlin/fir/expressions/FirExpression;", "setDefaultValue", "Lorg/jetbrains/kotlin/ir/declarations/IrValueParameter;", "firValueParameter", "Lorg/jetbrains/kotlin/fir/declarations/FirValueParameter;", "setPropertyAccessorContent", "Lorg/jetbrains/kotlin/ir/declarations/IrSimpleFunction;", "propertyAccessor", "Lorg/jetbrains/kotlin/fir/declarations/FirPropertyAccessor;", "correspondingProperty", "propertyType", "Lorg/jetbrains/kotlin/ir/types/IrType;", "isDefault", "", "isGetter", "setReceiver", "Lorg/jetbrains/kotlin/ir/expressions/IrFieldAccessExpression;", "toIrDelegatingConstructorCall", "Lorg/jetbrains/kotlin/ir/expressions/IrExpression;", "Lorg/jetbrains/kotlin/fir/expressions/FirDelegatedConstructorCall;", "toIrDelegatingConstructorCall$fir2ir", "toIrType", "Lorg/jetbrains/kotlin/fir/types/ConeKotlinType;", "Lorg/jetbrains/kotlin/fir/types/FirTypeRef;", "fir2ir"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ClassMemberGenerator implements Fir2IrComponents {
    private final Fir2IrComponents components;
    private final Fir2IrConversionScope conversionScope;
    private final Fir2IrVisitor visitor;

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

    public ClassMemberGenerator(Fir2IrComponents components, Fir2IrVisitor visitor, Fir2IrConversionScope conversionScope) {
        Intrinsics.checkNotNullParameter(components, "components");
        Intrinsics.checkNotNullParameter(visitor, "visitor");
        Intrinsics.checkNotNullParameter(conversionScope, "conversionScope");
        this.components = components;
        this.visitor = visitor;
        this.conversionScope = conversionScope;
    }

    private final IrType toIrType(FirTypeRef firTypeRef) {
        return Fir2IrTypeConverter.toIrType$default(getTypeConverter(), firTypeRef, null, 1, null);
    }

    private final IrType toIrType(ConeKotlinType coneKotlinType) {
        return Fir2IrTypeConverter.toIrType$default(getTypeConverter(), coneKotlinType, null, null, false, false, false, 31, null);
    }

    private final <T extends IrDeclaration> T applyParentFromStackTo(T declaration) {
        return (T) this.conversionScope.applyParentFromStackTo(declaration);
    }

    public final void convertClassContent(IrClass irClass, FirClass klass) {
        Object next;
        IrConstructor cachedIrConstructor$default;
        Intrinsics.checkNotNullParameter(irClass, "irClass");
        Intrinsics.checkNotNullParameter(klass, "klass");
        Fir2IrConversionScope fir2IrConversionScope = this.conversionScope;
        fir2IrConversionScope.getContainingFirClassStack().add(klass);
        try {
            getDeclarationStorage().enterScope(irClass);
            Fir2IrConversionScope fir2IrConversionScope2 = this.conversionScope;
            fir2IrConversionScope2.getClassStack().add(irClass);
            try {
                List listCreateListBuilder = CollectionsKt.createListBuilder();
                listCreateListBuilder.addAll(klass.getDeclarations());
                if ((klass instanceof FirRegularClass) && !FirDeclarationGenerationExtensionKt.getDeclarationGenerators(FirExtensionServiceKt.getExtensionService(getSession())).isEmpty()) {
                    listCreateListBuilder.addAll(CollectionsKt.sortedWith(GeneratedDeclarationsUtilsKt.generatedMembers((FirRegularClass) klass, getSession()), FirCallableDeclarationComparator.INSTANCE));
                    listCreateListBuilder.addAll(CollectionsKt.sortedWith(GeneratedDeclarationsUtilsKt.generatedNestedClassifiers((FirRegularClass) klass, getSession()), FirMemberDeclarationComparator.INSTANCE));
                }
                List<FirDeclaration> listBuild = CollectionsKt.build(listCreateListBuilder);
                Iterator it = listBuild.iterator();
                while (true) {
                    if (!it.hasNext()) {
                        next = null;
                        break;
                    }
                    next = it.next();
                    FirDeclaration firDeclaration = (FirDeclaration) next;
                    if ((firDeclaration instanceof FirConstructor) && ((FirConstructor) firDeclaration).getIsPrimary()) {
                        break;
                    }
                }
                FirConstructor firConstructor = (FirConstructor) next;
                if (firConstructor != null) {
                    cachedIrConstructor$default = Fir2IrDeclarationStorage.getCachedIrConstructor$default(getDeclarationStorage(), firConstructor, null, 2, null);
                    Intrinsics.checkNotNull(cachedIrConstructor$default);
                } else {
                    cachedIrConstructor$default = null;
                }
                if (cachedIrConstructor$default != null) {
                    Fir2IrDeclarationStorage declarationStorage = getDeclarationStorage();
                    declarationStorage.enterScope(cachedIrConstructor$default);
                    declarationStorage.putParametersInScope(cachedIrConstructor$default, firConstructor);
                }
                for (FirDeclaration firDeclaration2 : listBuild) {
                    if (!(firDeclaration2 instanceof FirTypeAlias) && (!(firDeclaration2 instanceof FirConstructor) || !((FirConstructor) firDeclaration2).getIsPrimary())) {
                        if ((firDeclaration2 instanceof FirRegularClass) && Intrinsics.areEqual(((FirMemberDeclaration) firDeclaration2).getStatus().getVisibility(), Visibilities.Local.INSTANCE)) {
                            IrClass cachedIrClass = getClassifierStorage().getCachedIrClass((FirClass) firDeclaration2);
                            Intrinsics.checkNotNull(cachedIrClass);
                            cachedIrClass.setParent(irClass);
                            Fir2IrConversionScope fir2IrConversionScope3 = this.conversionScope;
                            fir2IrConversionScope3.getParentStack().add(cachedIrClass);
                            try {
                                IrClass irClass2 = cachedIrClass;
                                convertClassContent(cachedIrClass, (FirClass) firDeclaration2);
                                Unit unit = Unit.INSTANCE;
                                fir2IrConversionScope3.getParentStack().remove(fir2IrConversionScope3.getParentStack().size() - 1);
                            } catch (Throwable th) {
                                fir2IrConversionScope3.getParentStack().remove(fir2IrConversionScope3.getParentStack().size() - 1);
                                throw th;
                            }
                        } else {
                            firDeclaration2.accept(this.visitor, null);
                        }
                    }
                }
                getAnnotationGenerator().generate(irClass, klass);
                if (cachedIrConstructor$default != null) {
                    getDeclarationStorage().leaveScope(cachedIrConstructor$default);
                }
                Unit unit2 = Unit.INSTANCE;
                fir2IrConversionScope2.getClassStack().remove(fir2IrConversionScope2.getClassStack().size() - 1);
                getDeclarationStorage().leaveScope(irClass);
                fir2IrConversionScope.getContainingFirClassStack().remove(fir2IrConversionScope.getContainingFirClassStack().size() - 1);
            } catch (Throwable th2) {
                fir2IrConversionScope2.getClassStack().remove(fir2IrConversionScope2.getClassStack().size() - 1);
                throw th2;
            }
        } catch (Throwable th3) {
            fir2IrConversionScope.getContainingFirClassStack().remove(fir2IrConversionScope.getContainingFirClassStack().size() - 1);
            throw th3;
        }
    }

    public final <T extends IrFunction> T convertFunctionContent(T irFunction, FirFunction firFunction, FirClass containingClass) {
        FirBlock body;
        FirBlock body2;
        Unit unit;
        FirBlock body3;
        Unit unit2;
        FirBlock body4;
        FirFunctionSymbol<? extends FirFunction> symbol;
        Name name2;
        IrPropertySymbol correspondingPropertySymbol;
        IrProperty owner;
        Intrinsics.checkNotNullParameter(irFunction, "irFunction");
        Fir2IrConversionScope fir2IrConversionScope = this.conversionScope;
        fir2IrConversionScope.getParentStack().add(irFunction);
        try {
            T t = irFunction;
            if (firFunction != null) {
                if (!(irFunction instanceof IrConstructor) || !((IrConstructor) irFunction).getIsPrimary()) {
                    Fir2IrDeclarationStorage declarationStorage = getDeclarationStorage();
                    declarationStorage.enterScope(irFunction);
                    declarationStorage.putParametersInScope(irFunction, firFunction);
                }
                List listDrop = CollectionsKt.drop(t.getValueParameters(), firFunction.getContextReceivers().size());
                boolean z = (containingClass != null ? containingClass.getClassKind() : null) == ClassKind.ANNOTATION_CLASS && (irFunction instanceof IrConstructor);
                for (Pair pair : CollectionsKt.zip(listDrop, firFunction.getValueParameters())) {
                    IrValueParameter irValueParameter = (IrValueParameter) pair.component1();
                    FirValueParameter firValueParameter = (FirValueParameter) pair.component2();
                    Fir2IrVisitor fir2IrVisitor = this.visitor;
                    boolean z2 = fir2IrVisitor._annotationMode;
                    fir2IrVisitor._annotationMode = z;
                    try {
                        setDefaultValue(irValueParameter, firValueParameter);
                        Unit unit3 = Unit.INSTANCE;
                        fir2IrVisitor._annotationMode = z2;
                    } catch (Throwable th) {
                        fir2IrVisitor._annotationMode = z2;
                        throw th;
                    }
                }
                getAnnotationGenerator().generate(irFunction, firFunction);
            }
            if ((firFunction instanceof FirConstructor) && (irFunction instanceof IrConstructor) && !firFunction.getStatus().isExpect()) {
                IrBlockBody irBlockBodyCreateBlockBody = t.getFactory().createBlockBody(t.getStartOffset(), t.getEndOffset());
                final FirDelegatedConstructorCall delegatedConstructor = ((FirConstructor) firFunction).getDelegatedConstructor();
                IrDeclarationParent parent = t.getParent();
                Intrinsics.checkNotNull(parent, "null cannot be cast to non-null type org.jetbrains.kotlin.ir.declarations.IrClass");
                IrClass irClass = (IrClass) parent;
                if (delegatedConstructor != null) {
                    irBlockBodyCreateBlockBody.getStatements().add((IrExpression) this.conversionScope.forDelegatingConstructorCall$fir2ir((IrConstructor) irFunction, irClass, new Function0<IrExpression>() { // from class: org.jetbrains.kotlin.fir.backend.generators.ClassMemberGenerator$convertFunctionContent$1$irDelegatingConstructorCall$1
                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                        {
                            super(0);
                        }

                        @Override // kotlin.jvm.functions.Function0
                        public final IrExpression invoke() {
                            return this.this$0.toIrDelegatingConstructorCall$fir2ir(delegatedConstructor);
                        }
                    }));
                }
                if ((containingClass instanceof FirRegularClass) && !((FirRegularClass) containingClass).getContextReceivers().isEmpty()) {
                    List<IrField> fieldsWithContextReceiversForClass = this.components.getClassifierStorage().getFieldsWithContextReceiversForClass(irClass);
                    if (fieldsWithContextReceiversForClass == null) {
                        throw new IllegalStateException("Not found context receiver fields".toString());
                    }
                    IrValueParameter irValueParameterDispatchReceiverParameter = this.conversionScope.dispatchReceiverParameter(irClass);
                    if (irValueParameterDispatchReceiverParameter == null) {
                        throw new IllegalStateException(("No found this parameter for " + irClass).toString());
                    }
                    int size = ((FirRegularClass) containingClass).getContextReceivers().size();
                    for (int i = 0; i < size; i++) {
                        IrValueParameter irValueParameter2 = t.getValueParameters().get(i);
                        irBlockBodyCreateBlockBody.getStatements().add(new IrSetFieldImpl(-1, -1, fieldsWithContextReceiversForClass.get(i).getSymbol(), new IrGetValueImpl(-1, -1, irValueParameterDispatchReceiverParameter.getType(), irValueParameterDispatchReceiverParameter.getSymbol(), null, 16, null), new IrGetValueImpl(-1, -1, irValueParameter2.getType(), irValueParameter2.getSymbol(), null, 16, null), this.components.getIrBuiltIns().getUnitType(), null, null, 192, null));
                    }
                }
                if (delegatedConstructor != null && !delegatedConstructor.getIsThis()) {
                    irBlockBodyCreateBlockBody.getStatements().add(new IrInstanceInitializerCallImpl(t.getStartOffset(), t.getEndOffset(), irClass.getSymbol(), IrUtilsKt.getConstructedClassType((IrConstructor) irFunction)));
                }
                FirBlock body5 = ((FirConstructor) firFunction).getBody();
                IrBlockBody irBlockBodyConvertToIrBlockBody$fir2ir = body5 != null ? this.visitor.convertToIrBlockBody$fir2ir(body5) : null;
                if (irBlockBodyConvertToIrBlockBody$fir2ir != null) {
                    CollectionsKt.addAll(irBlockBodyCreateBlockBody.getStatements(), irBlockBodyConvertToIrBlockBody$fir2ir.getStatements());
                }
                if (!irBlockBodyCreateBlockBody.getStatements().isEmpty()) {
                    irFunction.setBody(irBlockBodyCreateBlockBody);
                }
            } else if (!(irFunction instanceof IrConstructor) && !irFunction.getIsExpect()) {
                if (Intrinsics.areEqual(irFunction.getOrigin(), IrDeclarationOrigin.ENUM_CLASS_SPECIAL_MEMBER.INSTANCE)) {
                    IrSimpleFunction irSimpleFunction = irFunction instanceof IrSimpleFunction ? (IrSimpleFunction) irFunction : null;
                    if (irSimpleFunction == null || (correspondingPropertySymbol = irSimpleFunction.getCorrespondingPropertySymbol()) == null || (owner = correspondingPropertySymbol.getOwner()) == null || (name2 = owner.getName()) == null) {
                        name2 = irFunction.getName();
                    }
                    irFunction.setBody(new IrSyntheticBodyImpl(t.getStartOffset(), t.getEndOffset(), (IrSyntheticBodyKind) MapsKt.getValue(Fir2IrDeclarationStorage.INSTANCE.getENUM_SYNTHETIC_NAMES$fir2ir(), name2)));
                } else if ((irFunction.getParent() instanceof IrClass) && IrUtilsKt.getParentAsClass(irFunction).getIsData()) {
                    ConeClassLikeLookupTag coneClassLikeLookupTagDispatchReceiverClassLookupTagOrNull = (firFunction == null || (symbol = firFunction.getSymbol()) == null) ? null : ClassMembersKt.dispatchReceiverClassLookupTagOrNull(symbol);
                    if (DataClassResolver.INSTANCE.isComponentLike(irFunction.getName())) {
                        if (firFunction == null || (body4 = firFunction.getBody()) == null) {
                            unit2 = null;
                        } else {
                            irFunction.setBody(this.visitor.convertToIrBlockBody$fir2ir(body4));
                            unit2 = Unit.INSTANCE;
                        }
                        if (unit2 == null) {
                            DataClassMembersGenerator dataClassMembersGenerator = new DataClassMembersGenerator(this.components);
                            Intrinsics.checkNotNull(coneClassLikeLookupTagDispatchReceiverClassLookupTagOrNull);
                            dataClassMembersGenerator.generateDataClassComponentBody(irFunction, coneClassLikeLookupTagDispatchReceiverClassLookupTagOrNull);
                        }
                    } else if (DataClassResolver.INSTANCE.isCopy(irFunction.getName())) {
                        if (firFunction == null || (body3 = firFunction.getBody()) == null) {
                            unit = null;
                        } else {
                            irFunction.setBody(this.visitor.convertToIrBlockBody$fir2ir(body3));
                            unit = Unit.INSTANCE;
                        }
                        if (unit == null) {
                            DataClassMembersGenerator dataClassMembersGenerator2 = new DataClassMembersGenerator(this.components);
                            Intrinsics.checkNotNull(coneClassLikeLookupTagDispatchReceiverClassLookupTagOrNull);
                            dataClassMembersGenerator2.generateDataClassCopyBody(irFunction, coneClassLikeLookupTagDispatchReceiverClassLookupTagOrNull);
                        }
                    } else {
                        irFunction.setBody((firFunction == null || (body2 = firFunction.getBody()) == null) ? null : this.visitor.convertToIrBlockBody$fir2ir(body2));
                    }
                } else {
                    irFunction.setBody((firFunction == null || (body = firFunction.getBody()) == null) ? null : this.visitor.convertToIrBlockBody$fir2ir(body));
                }
            }
            if (firFunction != null && (!(irFunction instanceof IrConstructor) || !((IrConstructor) irFunction).getIsPrimary())) {
                getDeclarationStorage().leaveScope(irFunction);
            }
            if ((irFunction instanceof IrSimpleFunction) && (firFunction instanceof FirSimpleFunction) && containingClass != null) {
                ((IrSimpleFunction) irFunction).setOverriddenSymbols(ConversionUtilsKt.generateOverriddenFunctionSymbols(this, (FirSimpleFunction) firFunction, containingClass));
            }
            Unit unit4 = Unit.INSTANCE;
            fir2IrConversionScope.getParentStack().remove(fir2IrConversionScope.getParentStack().size() - 1);
            return irFunction;
        } catch (Throwable th2) {
            fir2IrConversionScope.getParentStack().remove(fir2IrConversionScope.getParentStack().size() - 1);
            throw th2;
        }
    }

    public final IrProperty convertPropertyContent(IrProperty irProperty, FirProperty property, FirClass containingClass) throws Throwable {
        FirExpression initializer;
        IrSimpleFunction setter;
        Intrinsics.checkNotNullParameter(irProperty, "irProperty");
        Intrinsics.checkNotNullParameter(property, "property");
        FirBackingField backingField = property.getBackingField();
        if (backingField == null || (initializer = backingField.getInitializer()) == null) {
            initializer = property.getInitializer();
        }
        FirExpression delegate = property.getDelegate();
        IrType irType = toIrType(property.getReturnTypeRef());
        if (initializer == null) {
            initializer = delegate;
        }
        initializeBackingField(irProperty, property, initializer);
        if (containingClass != null) {
            irProperty.setOverriddenSymbols(ConversionUtilsKt.generateOverriddenPropertySymbols(this, property, containingClass));
        }
        boolean z = (property.getGetter() instanceof FirDefaultPropertyGetter) || (property.getGetter() == null && (irProperty.getParent() instanceof IrScript) && DestructuringDeclarationAttributesKt.getDestructuringDeclarationContainerVariable(property) != null);
        IrSimpleFunction getter = irProperty.getGetter();
        if (getter != null) {
            setPropertyAccessorContent(getter, property, property.getGetter(), irProperty, irType, z, true, containingClass);
        }
        if (Intrinsics.areEqual(irProperty.getOrigin(), IrDeclarationOrigin.ENUM_CLASS_SPECIAL_MEMBER.INSTANCE)) {
            IrSyntheticBodyKind irSyntheticBodyKind = (IrSyntheticBodyKind) MapsKt.getValue(Fir2IrDeclarationStorage.INSTANCE.getENUM_SYNTHETIC_NAMES$fir2ir(), irProperty.getName());
            IrSimpleFunction getter2 = irProperty.getGetter();
            Intrinsics.checkNotNull(getter2);
            getter2.setBody(new IrSyntheticBodyImpl(irProperty.getStartOffset(), irProperty.getEndOffset(), irSyntheticBodyKind));
        }
        if (property.getIsVar() && (setter = irProperty.getSetter()) != null) {
            setPropertyAccessorContent(setter, property, property.getSetter(), irProperty, irType, property.getSetter() instanceof FirDefaultPropertySetter, false, containingClass);
        }
        getAnnotationGenerator().generate(irProperty, property);
        return irProperty;
    }

    public final IrField convertFieldContent(IrField irField, FirField field) {
        Intrinsics.checkNotNullParameter(irField, "irField");
        Intrinsics.checkNotNullParameter(field, "field");
        Fir2IrConversionScope fir2IrConversionScope = this.conversionScope;
        fir2IrConversionScope.getParentStack().add(irField);
        try {
            getDeclarationStorage().enterScope(irField);
            FirExpression initializer = field.getInitializer();
            if (irField.getInitializer() == null && initializer != null) {
                irField.setInitializer(IrFactoryHelpersKt.createExpressionBody(getIrFactory(), Fir2IrVisitor.convertToIrExpression$fir2ir$default(this.visitor, initializer, false, 2, null)));
            }
            getDeclarationStorage().leaveScope(irField);
            Unit unit = Unit.INSTANCE;
            return irField;
        } finally {
            fir2IrConversionScope.getParentStack().remove(fir2IrConversionScope.getParentStack().size() - 1);
        }
    }

    private final void initializeBackingField(IrProperty irProperty, FirProperty firProperty, FirExpression firExpression) {
        IrField backingField = irProperty.getBackingField();
        if (backingField == null) {
            return;
        }
        Fir2IrConversionScope fir2IrConversionScope = this.conversionScope;
        fir2IrConversionScope.getParentStack().add(backingField);
        try {
            IrField irField = backingField;
            getDeclarationStorage().enterScope(irProperty);
            if (irField.getInitializer() == null && firExpression != null) {
                IrFactory irFactory = getIrFactory();
                IrExpression irExpressionConvertToIrExpression$fir2ir = this.visitor.convertToIrExpression$fir2ir(firExpression, firProperty.getDelegate() != null);
                if (firProperty.getDelegate() == null) {
                    irExpressionConvertToIrExpression$fir2ir = this.visitor.getImplicitCastInserter().cast$fir2ir(irExpressionConvertToIrExpression$fir2ir, firExpression, firExpression.getTypeRef(), firProperty.getReturnTypeRef());
                }
                irField.setInitializer(IrFactoryHelpersKt.createExpressionBody(irFactory, irExpressionConvertToIrExpression$fir2ir));
            }
            getDeclarationStorage().leaveScope(irProperty);
            Unit unit = Unit.INSTANCE;
            fir2IrConversionScope.getParentStack().remove(fir2IrConversionScope.getParentStack().size() - 1);
            FirBackingField backingField2 = firProperty.getBackingField();
            if (backingField2 != null) {
                getAnnotationGenerator().generate(backingField, backingField2);
            }
        } catch (Throwable th) {
            fir2IrConversionScope.getParentStack().remove(fir2IrConversionScope.getParentStack().size() - 1);
            throw th;
        }
    }

    private final void setPropertyAccessorContent(IrSimpleFunction irSimpleFunction, FirProperty firProperty, FirPropertyAccessor firPropertyAccessor, IrProperty irProperty, IrType irType, boolean z, boolean z2, FirClass firClass) throws Throwable {
        IrReturnImpl irReturnImpl;
        Fir2IrConversionScope fir2IrConversionScope = this.conversionScope;
        fir2IrConversionScope.getFunctionStack().add(irSimpleFunction);
        try {
            IrSimpleFunction irSimpleFunction2 = irSimpleFunction;
            applyParentFromStackTo(irSimpleFunction2);
            convertFunctionContent(irSimpleFunction2, firPropertyAccessor, null);
            try {
                if (z) {
                    Fir2IrConversionScope fir2IrConversionScope2 = this.conversionScope;
                    fir2IrConversionScope2.getParentStack().add(irSimpleFunction2);
                    try {
                        IrSimpleFunction irSimpleFunction3 = irSimpleFunction2;
                        getDeclarationStorage().enterScope(irSimpleFunction3);
                        IrField backingField = irProperty.getBackingField();
                        IrFieldSymbol symbol = backingField != null ? backingField.getSymbol() : null;
                        try {
                            if (symbol != null) {
                                IrFactory factory = irSimpleFunction3.getFactory();
                                int startOffset = irSimpleFunction3.getStartOffset();
                                int endOffset = irSimpleFunction3.getEndOffset();
                                if (AdditionalIrUtilsKt.isSetter(irSimpleFunction3)) {
                                    IrSetFieldImpl irSetFieldImpl = new IrSetFieldImpl(irSimpleFunction3.getStartOffset(), irSimpleFunction3.getEndOffset(), symbol, getIrBuiltIns().getUnitType(), (IrStatementOrigin) null, (IrClassSymbol) null, 48, (DefaultConstructorMarker) null);
                                    setReceiver(irSetFieldImpl, irSimpleFunction3);
                                    irSetFieldImpl.setValue(new IrGetValueImpl(irSetFieldImpl.getStartOffset(), irSetFieldImpl.getEndOffset(), irType, ((IrValueParameter) CollectionsKt.first((List) irSimpleFunction3.getValueParameters())).getSymbol(), null, 16, null));
                                    Unit unit = Unit.INSTANCE;
                                    irReturnImpl = irSetFieldImpl;
                                } else {
                                    irReturnImpl = new IrReturnImpl(irSimpleFunction3.getStartOffset(), irSimpleFunction3.getEndOffset(), getIrBuiltIns().getNothingType(), irSimpleFunction3.getSymbol(), setReceiver(new IrGetFieldImpl(irSimpleFunction3.getStartOffset(), irSimpleFunction3.getEndOffset(), symbol, irType, null, null, 48, null), irSimpleFunction3));
                                }
                                irSimpleFunction3.setBody(IrFactoryHelpersKt.createBlockBody(factory, startOffset, endOffset, (List<? extends IrStatement>) CollectionsKt.listOf(irReturnImpl)));
                            } else {
                                fir2IrConversionScope = fir2IrConversionScope;
                            }
                            getDeclarationStorage().leaveScope(irSimpleFunction3);
                            Unit unit2 = Unit.INSTANCE;
                            fir2IrConversionScope2.getParentStack().remove(fir2IrConversionScope2.getParentStack().size() - 1);
                        } catch (Throwable th) {
                            th = th;
                            fir2IrConversionScope2.getParentStack().remove(fir2IrConversionScope2.getParentStack().size() - 1);
                            throw th;
                        }
                    } catch (Throwable th2) {
                        th = th2;
                    }
                } else {
                    fir2IrConversionScope = fir2IrConversionScope;
                }
                if (firClass != null) {
                    irSimpleFunction2.setOverriddenSymbols(ConversionUtilsKt.generateOverriddenAccessorSymbols(this, firProperty, firClass, z2));
                }
                Unit unit3 = Unit.INSTANCE;
                fir2IrConversionScope.getFunctionStack().remove(fir2IrConversionScope.getFunctionStack().size() - 1);
            } catch (Throwable th3) {
                th = th3;
                fir2IrConversionScope.getFunctionStack().remove(fir2IrConversionScope.getFunctionStack().size() - 1);
                throw th;
            }
        } catch (Throwable th4) {
            th = th4;
        }
    }

    private final IrFieldAccessExpression setReceiver(IrFieldAccessExpression irFieldAccessExpression, IrDeclaration irDeclaration) {
        IrValueParameter dispatchReceiverParameter;
        if ((irDeclaration instanceof IrFunction) && (dispatchReceiverParameter = ((IrFunction) irDeclaration).getDispatchReceiverParameter()) != null) {
            irFieldAccessExpression.setReceiver(new IrGetValueImpl(irFieldAccessExpression.getStartOffset(), irFieldAccessExpression.getEndOffset(), dispatchReceiverParameter.getSymbol(), null, 8, null));
        }
        return irFieldAccessExpression;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final IrExpression toIrDelegatingConstructorCall$fir2ir(FirDelegatedConstructorCall firDelegatedConstructorCall) {
        int endOffset;
        int i;
        Integer numStartOffsetSkippingComments;
        IrEnumConstructorCallImpl irEnumConstructorCallImpl;
        ConeTypeProjection[] coneTypeProjectionArr;
        int endOffset2;
        Integer numStartOffsetSkippingComments2;
        Intrinsics.checkNotNullParameter(firDelegatedConstructorCall, "<this>");
        IrType irType = toIrType(firDelegatedConstructorCall.getConstructedTypeRef());
        FirConstructorSymbol resolvedConstructorSymbol$default = FirReferenceUtilsKt.toResolvedConstructorSymbol$default(firDelegatedConstructorCall.getCalleeReference(), false, 1, null);
        int i2 = -1;
        if (resolvedConstructorSymbol$default != null) {
            ClassMemberGenerator classMemberGenerator = this;
            FirConstructorSymbol firConstructorSymbol = resolvedConstructorSymbol$default;
            FirCallableSymbol<?> firCallableSymbolUnwrapCallRepresentative = ConversionUtilsKt.unwrapCallRepresentative(classMemberGenerator, firConstructorSymbol, ClassMembersKt.containingClassLookupTag(firConstructorSymbol));
            ConeKotlinType resolvedReturnType = resolvedConstructorSymbol$default.getResolvedReturnType();
            ConeClassLikeType coneClassLikeType = resolvedReturnType instanceof ConeClassLikeType ? (ConeClassLikeType) resolvedReturnType : null;
            FirCallableSymbol<?> firCallableSymbolUnwrapCallRepresentative2 = ConversionUtilsKt.unwrapCallRepresentative(classMemberGenerator, firCallableSymbolUnwrapCallRepresentative, coneClassLikeType != null ? coneClassLikeType.getLookupTag() : null);
            if (!(firCallableSymbolUnwrapCallRepresentative2 instanceof FirConstructorSymbol)) {
                throw new IllegalStateException("Check failed.".toString());
            }
            FirExpression dispatchReceiver = firDelegatedConstructorCall.getDispatchReceiver();
            KtSourceElement source = firDelegatedConstructorCall.getSource();
            KtSourceElement ktSourceElement = source;
            if (ConversionUtilsKt.isCompiledElement(KtSourceElementKt.getPsi(ktSourceElement))) {
                i = -1;
                endOffset = -1;
            } else {
                int startOffset = (source == null || (numStartOffsetSkippingComments = ConversionUtilsKt.startOffsetSkippingComments(ktSourceElement)) == null) ? source != null ? source.getStartOffset() : -1 : numStartOffsetSkippingComments.intValue();
                endOffset = source != null ? source.getEndOffset() : -1;
                i = startOffset;
            }
            IrFunctionSymbol irFunctionSymbol$default = Fir2IrDeclarationStorage.getIrFunctionSymbol$default(getDeclarationStorage(), (FirFunctionSymbol) firCallableSymbolUnwrapCallRepresentative2, null, 2, null);
            Intrinsics.checkNotNull(irFunctionSymbol$default, "null cannot be cast to non-null type org.jetbrains.kotlin.ir.symbols.IrConstructorSymbol");
            IrConstructorSymbol irConstructorSymbol = (IrConstructorSymbol) irFunctionSymbol$default;
            ConeTypeProjection[] typeArguments = TypeExpansionUtilsKt.fullyExpandedType(FirTypeUtilsKt.getConeType(firDelegatedConstructorCall.getConstructedTypeRef()), getSession()).getTypeArguments();
            FirConstructor firConstructor = (FirConstructor) ((FirConstructorSymbol) firCallableSymbolUnwrapCallRepresentative2).getFir();
            if ((firConstructor.getStatus().isFromEnumClass() || FirTypeUtilsKt.isEnum(firConstructor.getReturnTypeRef())) && firDelegatedConstructorCall.isSuper()) {
                irEnumConstructorCallImpl = new IrEnumConstructorCallImpl(i, endOffset, irType, irConstructorSymbol, firConstructor.getTypeParameters().size(), firConstructor.getValueParameters().size());
                coneTypeProjectionArr = typeArguments;
            } else {
                coneTypeProjectionArr = typeArguments;
                irEnumConstructorCallImpl = new IrDelegatingConstructorCallImpl(i, endOffset, getIrBuiltIns().getUnitType(), irConstructorSymbol, firConstructor.getTypeParameters().size(), irConstructorSymbol.getOwner().getValueParameters().size());
            }
            IrMemberAccessExpression irMemberAccessExpression = irEnumConstructorCallImpl;
            if (!firConstructor.getTypeParameters().isEmpty()) {
                if (!(coneTypeProjectionArr.length == 0)) {
                    int length = coneTypeProjectionArr.length;
                    for (int i3 = 0; i3 < length; i3++) {
                        ConeTypeProjection coneTypeProjection = coneTypeProjectionArr[i3];
                        if (i3 >= firConstructor.getTypeParameters().size()) {
                            break;
                        }
                        Intrinsics.checkNotNull(coneTypeProjection, "null cannot be cast to non-null type org.jetbrains.kotlin.fir.types.ConeKotlinTypeProjection");
                        irMemberAccessExpression.putTypeArgument(i3, toIrType(((ConeKotlinTypeProjection) coneTypeProjection).getType()));
                    }
                }
            }
            if (!(dispatchReceiver instanceof FirNoReceiverExpression)) {
                irMemberAccessExpression.setDispatchReceiver(Fir2IrVisitor.convertToIrExpression$fir2ir$default(this.visitor, dispatchReceiver, false, 2, null));
            }
            CallAndReferenceGenerator callGenerator = getCallGenerator();
            callGenerator.getDeclarationStorage().enterScope(irConstructorSymbol.getOwner());
            IrExpression irExpressionApplyCallArguments$fir2ir = callGenerator.applyCallArguments$fir2ir(irMemberAccessExpression, firDelegatedConstructorCall);
            callGenerator.getDeclarationStorage().leaveScope(irConstructorSymbol.getOwner());
            return irExpressionApplyCallArguments$fir2ir;
        }
        KtSourceElement source2 = firDelegatedConstructorCall.getSource();
        KtSourceElement ktSourceElement2 = source2;
        if (ConversionUtilsKt.isCompiledElement(KtSourceElementKt.getPsi(ktSourceElement2))) {
            endOffset2 = -1;
        } else {
            int startOffset2 = (source2 == null || (numStartOffsetSkippingComments2 = ConversionUtilsKt.startOffsetSkippingComments(ktSourceElement2)) == null) ? source2 != null ? source2.getStartOffset() : -1 : numStartOffsetSkippingComments2.intValue();
            endOffset2 = source2 != null ? source2.getEndOffset() : -1;
            i2 = startOffset2;
        }
        return new IrErrorCallExpressionImpl(i2, endOffset2, irType, "Cannot find delegated constructor call");
    }

    private final void setDefaultValue(IrValueParameter irValueParameter, FirValueParameter firValueParameter) {
        FirExpression defaultValue = firValueParameter.getDefaultValue();
        if (defaultValue != null) {
            irValueParameter.setDefaultValue(IrFactoryHelpersKt.createExpressionBody(irValueParameter.getFactory(), Fir2IrVisitor.convertToIrExpression$fir2ir$default(this.visitor, defaultValue, false, 2, null)));
        }
    }
}
