package org.jetbrains.kotlin.fir.backend.generators;

import java.util.List;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.KtSourceElement;
import org.jetbrains.kotlin.KtSourceElementKt;
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
import org.jetbrains.kotlin.fir.backend.PrimitiveComparisonKt;
import org.jetbrains.kotlin.fir.backend.PrimitiveConeNumericComparisonInfo;
import org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration;
import org.jetbrains.kotlin.fir.declarations.FirReceiverParameter;
import org.jetbrains.kotlin.fir.expressions.FirComparisonExpression;
import org.jetbrains.kotlin.fir.expressions.FirEqualityOperatorCall;
import org.jetbrains.kotlin.fir.expressions.FirExpression;
import org.jetbrains.kotlin.fir.expressions.FirExpressionUtilKt;
import org.jetbrains.kotlin.fir.expressions.FirOperation;
import org.jetbrains.kotlin.fir.expressions.FirSmartCastExpression;
import org.jetbrains.kotlin.fir.expressions.FirVarargArgumentsExpression;
import org.jetbrains.kotlin.fir.resolve.ScopeSession;
import org.jetbrains.kotlin.fir.signaturer.FirBasedSignatureComposer;
import org.jetbrains.kotlin.fir.symbols.impl.FirCallableSymbol;
import org.jetbrains.kotlin.fir.types.ConeClassLikeType;
import org.jetbrains.kotlin.fir.types.ConeDynamicType;
import org.jetbrains.kotlin.fir.types.ConeTypeUtilsKt;
import org.jetbrains.kotlin.fir.types.FirTypeRef;
import org.jetbrains.kotlin.fir.types.FirTypeUtilsKt;
import org.jetbrains.kotlin.ir.IrLock;
import org.jetbrains.kotlin.ir.builders.PrimitivesKt;
import org.jetbrains.kotlin.ir.declarations.IrFactory;
import org.jetbrains.kotlin.ir.declarations.IrVariable;
import org.jetbrains.kotlin.ir.expressions.IrDynamicOperator;
import org.jetbrains.kotlin.ir.expressions.IrExpression;
import org.jetbrains.kotlin.ir.expressions.IrStatementOrigin;
import org.jetbrains.kotlin.ir.expressions.IrStatementOriginImpl;
import org.jetbrains.kotlin.ir.expressions.IrTypeOperator;
import org.jetbrains.kotlin.ir.expressions.IrTypeOperatorCall;
import org.jetbrains.kotlin.ir.expressions.impl.IrCallImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrConstImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrDynamicOperatorExpressionImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrGetValueImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrTypeOperatorCallImpl;
import org.jetbrains.kotlin.ir.linkage.IrProvider;
import org.jetbrains.kotlin.ir.symbols.IrClassSymbol;
import org.jetbrains.kotlin.ir.symbols.IrClassifierSymbol;
import org.jetbrains.kotlin.ir.symbols.IrSimpleFunctionSymbol;
import org.jetbrains.kotlin.ir.symbols.IrValueSymbol;
import org.jetbrains.kotlin.ir.types.IrDynamicType;
import org.jetbrains.kotlin.ir.types.IrSimpleType;
import org.jetbrains.kotlin.ir.types.IrType;
import org.jetbrains.kotlin.ir.types.IrTypePredicatesKt;
import org.jetbrains.kotlin.ir.types.IrTypeUtilsKt;
import org.jetbrains.kotlin.ir.types.IrTypesKt;
import org.jetbrains.kotlin.ir.util.AdditionalIrUtilsKt;
import org.jetbrains.kotlin.ir.util.SymbolTable;
import org.jetbrains.kotlin.name.ClassId;

/* JADX INFO: compiled from: OperatorExpressionGenerator.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000ª\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\b\u0000\u0018\u00002\u00020\u0001B\u001d\u0012\u0006\u0010\u0002\u001a\u00020\u0001\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\u0006\u0010\u0005\u001a\u00020\u0006¢\u0006\u0002\u0010\u0007J\u000e\u0010]\u001a\u00020^2\u0006\u0010_\u001a\u00020`J\u000e\u0010a\u001a\u00020^2\u0006\u0010b\u001a\u00020cJ \u0010d\u001a\u00020^2\u0006\u0010e\u001a\u00020f2\u0006\u0010g\u001a\u00020f2\u0006\u0010_\u001a\u00020`H\u0002J.\u0010h\u001a\u00020^2\u0006\u0010e\u001a\u00020f2\u0006\u0010g\u001a\u00020f2\u0006\u0010i\u001a\u00020j2\f\u0010k\u001a\b\u0012\u0004\u0012\u00020l0=H\u0002J&\u0010m\u001a\u0010\u0012\u0006\u0012\u0004\u0018\u00010o\u0012\u0004\u0012\u00020p0n2\u0006\u0010i\u001a\u00020j2\u0006\u0010q\u001a\u00020rH\u0002J.\u0010s\u001a\u00020^2\u0006\u0010e\u001a\u00020f2\u0006\u0010g\u001a\u00020f2\u0006\u0010i\u001a\u00020j2\f\u0010k\u001a\b\u0012\u0004\u0012\u00020l0=H\u0002J.\u0010t\u001a\u00020^2\u0006\u0010e\u001a\u00020f2\u0006\u0010g\u001a\u00020f2\u0006\u0010i\u001a\u00020j2\f\u0010k\u001a\b\u0012\u0004\u0012\u00020l0=H\u0002J2\u0010u\u001a\u0004\u0018\u00010v2\u0006\u0010e\u001a\u00020f2\u0006\u0010g\u001a\u00020f2\u0006\u0010w\u001a\u00020^2\u0006\u0010x\u001a\u00020^2\u0006\u0010y\u001a\u00020zH\u0002J\u001e\u0010{\u001a\u00020^*\u00020l2\b\u0010|\u001a\u0004\u0018\u00010}2\u0006\u0010~\u001a\u00020\u007fH\u0002J\u0015\u0010\u0080\u0001\u001a\u00020^*\u00020^2\u0006\u0010y\u001a\u00020zH\u0002J\u0010\u0010\u0081\u0001\u001a\u0005\u0018\u00010\u0082\u0001*\u00020jH\u0002J\u0010\u0010\u0081\u0001\u001a\u0005\u0018\u00010\u0082\u0001*\u00020zH\u0002R\u0012\u0010\b\u001a\u00020\tX\u0096\u0005¢\u0006\u0006\u001a\u0004\b\n\u0010\u000bR\u0012\u0010\f\u001a\u00020\rX\u0096\u0005¢\u0006\u0006\u001a\u0004\b\u000e\u0010\u000fR\u0012\u0010\u0010\u001a\u00020\u0011X\u0096\u0005¢\u0006\u0006\u001a\u0004\b\u0012\u0010\u0013R\u0012\u0010\u0014\u001a\u00020\u0015X\u0096\u0005¢\u0006\u0006\u001a\u0004\b\u0016\u0010\u0017R\u0012\u0010\u0018\u001a\u00020\u0019X\u0096\u0005¢\u0006\u0006\u001a\u0004\b\u001a\u0010\u001bR\u000e\u0010\u0002\u001a\u00020\u0001X\u0082\u0004¢\u0006\u0002\n\u0000R\u0012\u0010\u001c\u001a\u00020\u001dX\u0096\u0005¢\u0006\u0006\u001a\u0004\b\u001e\u0010\u001fR\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u0012\u0010 \u001a\u00020!X\u0096\u0005¢\u0006\u0006\u001a\u0004\b\"\u0010#R\u0012\u0010$\u001a\u00020%X\u0096\u0005¢\u0006\u0006\u001a\u0004\b&\u0010'R\u0012\u0010(\u001a\u00020)X\u0096\u0005¢\u0006\u0006\u001a\u0004\b*\u0010+R\u0012\u0010,\u001a\u00020-X\u0096\u0005¢\u0006\u0006\u001a\u0004\b.\u0010/R\u0012\u00100\u001a\u000201X\u0096\u0005¢\u0006\u0006\u001a\u0004\b2\u00103R\u0012\u00104\u001a\u000205X\u0096\u0005¢\u0006\u0006\u001a\u0004\b6\u00107R\u0012\u00108\u001a\u000209X\u0096\u0005¢\u0006\u0006\u001a\u0004\b:\u0010;R\u0018\u0010<\u001a\b\u0012\u0004\u0012\u00020>0=X\u0096\u0005¢\u0006\u0006\u001a\u0004\b?\u0010@R\u0012\u0010A\u001a\u00020BX\u0096\u0005¢\u0006\u0006\u001a\u0004\bC\u0010DR\u0012\u0010E\u001a\u00020FX\u0096\u0005¢\u0006\u0006\u001a\u0004\bG\u0010HR\u0012\u0010I\u001a\u00020JX\u0096\u0005¢\u0006\u0006\u001a\u0004\bK\u0010LR\u0012\u0010M\u001a\u00020NX\u0096\u0005¢\u0006\u0006\u001a\u0004\bO\u0010PR\u0012\u0010Q\u001a\u00020RX\u0096\u0005¢\u0006\u0006\u001a\u0004\bS\u0010TR\u0012\u0010U\u001a\u00020VX\u0096\u0005¢\u0006\u0006\u001a\u0004\bW\u0010XR\u0012\u0010Y\u001a\u00020ZX\u0096\u0005¢\u0006\u0006\u001a\u0004\b[\u0010\\R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0083\u0001"}, d2 = {"Lorg/jetbrains/kotlin/fir/backend/generators/OperatorExpressionGenerator;", "Lorg/jetbrains/kotlin/fir/backend/Fir2IrComponents;", "components", "visitor", "Lorg/jetbrains/kotlin/fir/backend/Fir2IrVisitor;", "conversionScope", "Lorg/jetbrains/kotlin/fir/backend/Fir2IrConversionScope;", "(Lorg/jetbrains/kotlin/fir/backend/Fir2IrComponents;Lorg/jetbrains/kotlin/fir/backend/Fir2IrVisitor;Lorg/jetbrains/kotlin/fir/backend/Fir2IrConversionScope;)V", "annotationGenerator", "Lorg/jetbrains/kotlin/fir/backend/generators/AnnotationGenerator;", "getAnnotationGenerator", "()Lorg/jetbrains/kotlin/fir/backend/generators/AnnotationGenerator;", "annotationsFromPluginRegistrar", "Lorg/jetbrains/kotlin/fir/backend/Fir2IrAnnotationsFromPluginRegistrar;", "getAnnotationsFromPluginRegistrar", "()Lorg/jetbrains/kotlin/fir/backend/Fir2IrAnnotationsFromPluginRegistrar;", "builtIns", "Lorg/jetbrains/kotlin/fir/backend/Fir2IrBuiltIns;", "getBuiltIns", "()Lorg/jetbrains/kotlin/fir/backend/Fir2IrBuiltIns;", "callGenerator", "Lorg/jetbrains/kotlin/fir/backend/generators/CallAndReferenceGenerator;", "getCallGenerator", "()Lorg/jetbrains/kotlin/fir/backend/generators/CallAndReferenceGenerator;", "classifierStorage", "Lorg/jetbrains/kotlin/fir/backend/Fir2IrClassifierStorage;", "getClassifierStorage", "()Lorg/jetbrains/kotlin/fir/backend/Fir2IrClassifierStorage;", "configuration", "Lorg/jetbrains/kotlin/fir/backend/Fir2IrConfiguration;", "getConfiguration", "()Lorg/jetbrains/kotlin/fir/backend/Fir2IrConfiguration;", "converter", "Lorg/jetbrains/kotlin/fir/backend/Fir2IrConverter;", "getConverter", "()Lorg/jetbrains/kotlin/fir/backend/Fir2IrConverter;", "declarationStorage", "Lorg/jetbrains/kotlin/fir/backend/Fir2IrDeclarationStorage;", "getDeclarationStorage", "()Lorg/jetbrains/kotlin/fir/backend/Fir2IrDeclarationStorage;", "delegatedMemberGenerator", "Lorg/jetbrains/kotlin/fir/backend/generators/DelegatedMemberGenerator;", "getDelegatedMemberGenerator", "()Lorg/jetbrains/kotlin/fir/backend/generators/DelegatedMemberGenerator;", "extensions", "Lorg/jetbrains/kotlin/fir/backend/Fir2IrExtensions;", "getExtensions", "()Lorg/jetbrains/kotlin/fir/backend/Fir2IrExtensions;", "fakeOverrideGenerator", "Lorg/jetbrains/kotlin/fir/backend/generators/FakeOverrideGenerator;", "getFakeOverrideGenerator", "()Lorg/jetbrains/kotlin/fir/backend/generators/FakeOverrideGenerator;", "irBuiltIns", "Lorg/jetbrains/kotlin/fir/backend/IrBuiltInsOverFir;", "getIrBuiltIns", "()Lorg/jetbrains/kotlin/fir/backend/IrBuiltInsOverFir;", "irFactory", "Lorg/jetbrains/kotlin/ir/declarations/IrFactory;", "getIrFactory", "()Lorg/jetbrains/kotlin/ir/declarations/IrFactory;", "irProviders", "", "Lorg/jetbrains/kotlin/ir/linkage/IrProvider;", "getIrProviders", "()Ljava/util/List;", "lock", "Lorg/jetbrains/kotlin/ir/IrLock;", "getLock", "()Lorg/jetbrains/kotlin/ir/IrLock;", "scopeSession", "Lorg/jetbrains/kotlin/fir/resolve/ScopeSession;", "getScopeSession", "()Lorg/jetbrains/kotlin/fir/resolve/ScopeSession;", "session", "Lorg/jetbrains/kotlin/fir/FirSession;", "getSession", "()Lorg/jetbrains/kotlin/fir/FirSession;", "signatureComposer", "Lorg/jetbrains/kotlin/fir/signaturer/FirBasedSignatureComposer;", "getSignatureComposer", "()Lorg/jetbrains/kotlin/fir/signaturer/FirBasedSignatureComposer;", "symbolTable", "Lorg/jetbrains/kotlin/ir/util/SymbolTable;", "getSymbolTable", "()Lorg/jetbrains/kotlin/ir/util/SymbolTable;", "typeConverter", "Lorg/jetbrains/kotlin/fir/backend/Fir2IrTypeConverter;", "getTypeConverter", "()Lorg/jetbrains/kotlin/fir/backend/Fir2IrTypeConverter;", "visibilityConverter", "Lorg/jetbrains/kotlin/fir/backend/Fir2IrVisibilityConverter;", "getVisibilityConverter", "()Lorg/jetbrains/kotlin/fir/backend/Fir2IrVisibilityConverter;", "convertComparisonExpression", "Lorg/jetbrains/kotlin/ir/expressions/IrExpression;", "comparisonExpression", "Lorg/jetbrains/kotlin/fir/expressions/FirComparisonExpression;", "convertEqualityOperatorCall", "equalityOperatorCall", "Lorg/jetbrains/kotlin/fir/expressions/FirEqualityOperatorCall;", "generateComparisonCall", "startOffset", "", "endOffset", "generateEqualityOperatorCall", "operation", "Lorg/jetbrains/kotlin/fir/expressions/FirOperation;", "arguments", "Lorg/jetbrains/kotlin/fir/expressions/FirExpression;", "getSymbolAndOriginForComparison", "Lkotlin/Pair;", "Lorg/jetbrains/kotlin/ir/symbols/IrSimpleFunctionSymbol;", "Lorg/jetbrains/kotlin/ir/expressions/IrStatementOriginImpl;", "classifier", "Lorg/jetbrains/kotlin/ir/symbols/IrClassifierSymbol;", "transformEqualityOperatorCall", "transformIdentityOperatorCall", "tryGenerateDynamicOperatorCall", "Lorg/jetbrains/kotlin/ir/expressions/impl/IrDynamicOperatorExpressionImpl;", "firstArgument", "secondArgument", "origin", "Lorg/jetbrains/kotlin/ir/expressions/IrStatementOrigin;", "convertToIrExpression", "comparisonInfo", "Lorg/jetbrains/kotlin/fir/backend/PrimitiveConeNumericComparisonInfo;", "isLeftType", "", "negate", "toIrDynamicOperator", "Lorg/jetbrains/kotlin/ir/expressions/IrDynamicOperator;", "fir2ir"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class OperatorExpressionGenerator implements Fir2IrComponents {
    private final Fir2IrComponents components;
    private final Fir2IrConversionScope conversionScope;
    private final Fir2IrVisitor visitor;

    /* JADX INFO: compiled from: OperatorExpressionGenerator.kt */
    @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
    public /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[FirOperation.values().length];
            try {
                iArr[FirOperation.LT.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[FirOperation.GT.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                iArr[FirOperation.LT_EQ.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                iArr[FirOperation.GT_EQ.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                iArr[FirOperation.EQ.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                iArr[FirOperation.NOT_EQ.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                iArr[FirOperation.IDENTITY.ordinal()] = 7;
            } catch (NoSuchFieldError unused7) {
            }
            try {
                iArr[FirOperation.NOT_IDENTITY.ordinal()] = 8;
            } catch (NoSuchFieldError unused8) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

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

    public OperatorExpressionGenerator(Fir2IrComponents components, Fir2IrVisitor visitor, Fir2IrConversionScope conversionScope) {
        Intrinsics.checkNotNullParameter(components, "components");
        Intrinsics.checkNotNullParameter(visitor, "visitor");
        Intrinsics.checkNotNullParameter(conversionScope, "conversionScope");
        this.components = components;
        this.visitor = visitor;
        this.conversionScope = conversionScope;
    }

    public final IrExpression convertComparisonExpression(FirComparisonExpression comparisonExpression) {
        int startOffset;
        int endOffset;
        Integer numStartOffsetSkippingComments;
        Intrinsics.checkNotNullParameter(comparisonExpression, "comparisonExpression");
        KtSourceElement source = comparisonExpression.getSource();
        KtSourceElement ktSourceElement = source;
        int i = -1;
        if (ConversionUtilsKt.isCompiledElement(KtSourceElementKt.getPsi(ktSourceElement))) {
            endOffset = -1;
        } else {
            if (source == null || (numStartOffsetSkippingComments = ConversionUtilsKt.startOffsetSkippingComments(ktSourceElement)) == null) {
                startOffset = source != null ? source.getStartOffset() : -1;
            } else {
                startOffset = numStartOffsetSkippingComments.intValue();
            }
            endOffset = source != null ? source.getEndOffset() : -1;
            i = startOffset;
        }
        return generateComparisonCall(i, endOffset, comparisonExpression);
    }

    public final IrExpression convertEqualityOperatorCall(FirEqualityOperatorCall equalityOperatorCall) {
        int startOffset;
        int endOffset;
        Integer numStartOffsetSkippingComments;
        Intrinsics.checkNotNullParameter(equalityOperatorCall, "equalityOperatorCall");
        KtSourceElement source = equalityOperatorCall.getSource();
        KtSourceElement ktSourceElement = source;
        int i = -1;
        if (ConversionUtilsKt.isCompiledElement(KtSourceElementKt.getPsi(ktSourceElement))) {
            endOffset = -1;
        } else {
            if (source == null || (numStartOffsetSkippingComments = ConversionUtilsKt.startOffsetSkippingComments(ktSourceElement)) == null) {
                startOffset = source != null ? source.getStartOffset() : -1;
            } else {
                startOffset = numStartOffsetSkippingComments.intValue();
            }
            endOffset = source != null ? source.getEndOffset() : -1;
            i = startOffset;
        }
        return generateEqualityOperatorCall(i, endOffset, equalityOperatorCall.getOperation(), equalityOperatorCall.getArgumentList().getArguments());
    }

    private final IrExpression generateComparisonCall(int startOffset, int endOffset, FirComparisonExpression comparisonExpression) throws Exception {
        FirCallableDeclaration firCallableDeclaration;
        FirExpression firExpression;
        FirTypeRef typeRef;
        FirOperation operation = comparisonExpression.getOperation();
        FirExpression explicitReceiver = comparisonExpression.getCompareToCall().getExplicitReceiver();
        FirReceiverParameter receiverParameter = null;
        if (((explicitReceiver == null || (typeRef = explicitReceiver.getTypeRef()) == null) ? null : FirTypeUtilsKt.getConeType(typeRef)) instanceof ConeDynamicType) {
            IrDynamicOperator irDynamicOperator = toIrDynamicOperator(operation);
            if (irDynamicOperator == null) {
                throw new Exception("Can't convert to the corresponding IrDynamicOperator");
            }
            Object objFirstOrNull = CollectionsKt.firstOrNull((List<? extends Object>) comparisonExpression.getCompareToCall().getArgumentList().getArguments());
            FirVarargArgumentsExpression firVarargArgumentsExpression = objFirstOrNull instanceof FirVarargArgumentsExpression ? (FirVarargArgumentsExpression) objFirstOrNull : null;
            List<FirExpression> arguments = firVarargArgumentsExpression != null ? firVarargArgumentsExpression.getArguments() : null;
            if (arguments == null || (firExpression = (FirExpression) CollectionsKt.firstOrNull((List) arguments)) == null) {
                throw new Exception("Comparison with a dynamic function should have a vararg with the rhs-argument");
            }
            IrDynamicOperatorExpressionImpl irDynamicOperatorExpressionImpl = new IrDynamicOperatorExpressionImpl(startOffset, endOffset, getIrBuiltIns().getBooleanType(), irDynamicOperator);
            Object objAccept = explicitReceiver.accept(this.visitor, null);
            Intrinsics.checkNotNull(objAccept, "null cannot be cast to non-null type org.jetbrains.kotlin.ir.expressions.IrExpression");
            irDynamicOperatorExpressionImpl.setReceiver((IrExpression) objAccept);
            List<IrExpression> arguments2 = irDynamicOperatorExpressionImpl.getArguments();
            Object objAccept2 = firExpression.accept(this.visitor, null);
            Intrinsics.checkNotNull(objAccept2, "null cannot be cast to non-null type org.jetbrains.kotlin.ir.expressions.IrExpression");
            arguments2.add((IrExpression) objAccept2);
            return irDynamicOperatorExpressionImpl;
        }
        FirCallableSymbol<?> resolvedCallableSymbol = FirExpressionUtilKt.toResolvedCallableSymbol(comparisonExpression.getCompareToCall());
        if (resolvedCallableSymbol != null && (firCallableDeclaration = (FirCallableDeclaration) resolvedCallableSymbol.getFir()) != null) {
            receiverParameter = firCallableDeclaration.getReceiverParameter();
        }
        if (receiverParameter != null) {
            return generateComparisonCall$fallbackToRealCall(this, operation, startOffset, endOffset, comparisonExpression);
        }
        PrimitiveConeNumericComparisonInfo primitiveConeNumericComparisonInfoInferPrimitiveNumericComparisonInfo = PrimitiveComparisonKt.inferPrimitiveNumericComparisonInfo(comparisonExpression);
        if (primitiveConeNumericComparisonInfoInferPrimitiveNumericComparisonInfo == null) {
            return generateComparisonCall$fallbackToRealCall(this, operation, startOffset, endOffset, comparisonExpression);
        }
        IrType irType = getTypeConverter().getClassIdToTypeMap$fir2ir().get(primitiveConeNumericComparisonInfoInferPrimitiveNumericComparisonInfo.getComparisonType().getLookupTag().getClassId());
        if (irType == null) {
            return generateComparisonCall$fallbackToRealCall(this, operation, startOffset, endOffset, comparisonExpression);
        }
        Pair<IrSimpleFunctionSymbol, IrStatementOriginImpl> symbolAndOriginForComparison = getSymbolAndOriginForComparison(operation, IrTypesKt.getClassifierOrFail(irType));
        IrSimpleFunctionSymbol irSimpleFunctionSymbolComponent1 = symbolAndOriginForComparison.component1();
        IrStatementOriginImpl irStatementOriginImplComponent2 = symbolAndOriginForComparison.component2();
        Intrinsics.checkNotNull(irSimpleFunctionSymbolComponent1);
        return PrimitivesKt.primitiveOp2(startOffset, endOffset, irSimpleFunctionSymbolComponent1, getIrBuiltIns().getBooleanType(), irStatementOriginImplComponent2, convertToIrExpression(PrimitiveComparisonKt.getLeft(comparisonExpression), primitiveConeNumericComparisonInfoInferPrimitiveNumericComparisonInfo, true), convertToIrExpression(PrimitiveComparisonKt.getRight(comparisonExpression), primitiveConeNumericComparisonInfoInferPrimitiveNumericComparisonInfo, false));
    }

    private static final IrExpression generateComparisonCall$fallbackToRealCall(OperatorExpressionGenerator operatorExpressionGenerator, FirOperation firOperation, int i, int i2, FirComparisonExpression firComparisonExpression) {
        Pair<IrSimpleFunctionSymbol, IrStatementOriginImpl> symbolAndOriginForComparison = operatorExpressionGenerator.getSymbolAndOriginForComparison(firOperation, IrTypesKt.getClassifierOrFail(operatorExpressionGenerator.getIrBuiltIns().getIntType()));
        IrSimpleFunctionSymbol irSimpleFunctionSymbolComponent1 = symbolAndOriginForComparison.component1();
        IrStatementOriginImpl irStatementOriginImplComponent2 = symbolAndOriginForComparison.component2();
        Intrinsics.checkNotNull(irSimpleFunctionSymbolComponent1);
        IrType booleanType = operatorExpressionGenerator.getIrBuiltIns().getBooleanType();
        IrStatementOriginImpl irStatementOriginImpl = irStatementOriginImplComponent2;
        Object objAccept = firComparisonExpression.getCompareToCall().accept(operatorExpressionGenerator.visitor, null);
        Intrinsics.checkNotNull(objAccept, "null cannot be cast to non-null type org.jetbrains.kotlin.ir.expressions.IrExpression");
        return PrimitivesKt.primitiveOp2(i, i2, irSimpleFunctionSymbolComponent1, booleanType, irStatementOriginImpl, (IrExpression) objAccept, IrConstImpl.INSTANCE.m7211int(i, i2, operatorExpressionGenerator.getIrBuiltIns().getIntType(), 0));
    }

    private final Pair<IrSimpleFunctionSymbol, IrStatementOriginImpl> getSymbolAndOriginForComparison(FirOperation operation, IrClassifierSymbol classifier) {
        int i = WhenMappings.$EnumSwitchMapping$0[operation.ordinal()];
        if (i == 1) {
            return TuplesKt.to(getIrBuiltIns().getLessFunByOperandType().get(classifier), IrStatementOrigin.LT.INSTANCE);
        }
        if (i == 2) {
            return TuplesKt.to(getIrBuiltIns().getGreaterFunByOperandType().get(classifier), IrStatementOrigin.GT.INSTANCE);
        }
        if (i == 3) {
            return TuplesKt.to(getIrBuiltIns().getLessOrEqualFunByOperandType().get(classifier), IrStatementOrigin.LTEQ.INSTANCE);
        }
        if (i == 4) {
            return TuplesKt.to(getIrBuiltIns().getGreaterOrEqualFunByOperandType().get(classifier), IrStatementOrigin.GTEQ.INSTANCE);
        }
        throw new IllegalStateException(("Unexpected comparison operation: " + operation).toString());
    }

    private final IrDynamicOperator toIrDynamicOperator(FirOperation firOperation) {
        int i = WhenMappings.$EnumSwitchMapping$0[firOperation.ordinal()];
        if (i == 1) {
            return IrDynamicOperator.LT;
        }
        if (i == 2) {
            return IrDynamicOperator.GT;
        }
        if (i == 3) {
            return IrDynamicOperator.LE;
        }
        if (i != 4) {
            return null;
        }
        return IrDynamicOperator.GE;
    }

    private final IrExpression generateEqualityOperatorCall(int startOffset, int endOffset, FirOperation operation, List<? extends FirExpression> arguments) {
        int i = WhenMappings.$EnumSwitchMapping$0[operation.ordinal()];
        if (i == 5 || i == 6) {
            return transformEqualityOperatorCall(startOffset, endOffset, operation, arguments);
        }
        if (i == 7 || i == 8) {
            return transformIdentityOperatorCall(startOffset, endOffset, operation, arguments);
        }
        throw new IllegalStateException(("Unexpected operation: " + operation).toString());
    }

    private final IrDynamicOperator toIrDynamicOperator(IrStatementOrigin irStatementOrigin) {
        if (irStatementOrigin instanceof IrStatementOrigin.EQEQ) {
            return IrDynamicOperator.EQEQ;
        }
        if (irStatementOrigin instanceof IrStatementOrigin.EXCLEQ) {
            return IrDynamicOperator.EXCLEQ;
        }
        if (irStatementOrigin instanceof IrStatementOrigin.EQEQEQ) {
            return IrDynamicOperator.EQEQEQ;
        }
        if (irStatementOrigin instanceof IrStatementOrigin.EXCLEQEQ) {
            return IrDynamicOperator.EXCLEQEQ;
        }
        return null;
    }

    private final IrDynamicOperatorExpressionImpl tryGenerateDynamicOperatorCall(int startOffset, int endOffset, IrExpression firstArgument, IrExpression secondArgument, IrStatementOrigin origin) throws Exception {
        if (!(firstArgument.getType() instanceof IrDynamicType)) {
            return null;
        }
        IrDynamicOperator irDynamicOperator = toIrDynamicOperator(origin);
        if (irDynamicOperator == null) {
            throw new Exception("Couldn't convert to the corresponding IrDynamicOperator");
        }
        IrDynamicOperatorExpressionImpl irDynamicOperatorExpressionImpl = new IrDynamicOperatorExpressionImpl(startOffset, endOffset, getIrBuiltIns().getBooleanType(), irDynamicOperator);
        irDynamicOperatorExpressionImpl.setReceiver(firstArgument);
        irDynamicOperatorExpressionImpl.getArguments().add(secondArgument);
        return irDynamicOperatorExpressionImpl;
    }

    private final IrExpression transformEqualityOperatorCall(int startOffset, int endOffset, FirOperation operation, List<? extends FirExpression> arguments) throws Exception {
        IrStatementOrigin.EQEQ eqeq;
        IrSimpleFunctionSymbol eqeqSymbol;
        IrClassSymbol irClassSymbol;
        int i = WhenMappings.$EnumSwitchMapping$0[operation.ordinal()];
        if (i == 5) {
            eqeq = IrStatementOrigin.EQEQ.INSTANCE;
        } else if (i == 6) {
            eqeq = IrStatementOrigin.EXCLEQ.INSTANCE;
        } else {
            throw new IllegalStateException(("Not an equality operation: " + operation).toString());
        }
        PrimitiveConeNumericComparisonInfo primitiveConeNumericComparisonInfoInferPrimitiveNumericComparisonInfo = PrimitiveComparisonKt.inferPrimitiveNumericComparisonInfo(arguments.get(0), arguments.get(1));
        IrExpression irExpressionConvertToIrExpression = convertToIrExpression(arguments.get(0), primitiveConeNumericComparisonInfoInferPrimitiveNumericComparisonInfo, true);
        IrExpression irExpressionConvertToIrExpression2 = convertToIrExpression(arguments.get(1), primitiveConeNumericComparisonInfoInferPrimitiveNumericComparisonInfo, false);
        IrStatementOrigin irStatementOrigin = eqeq;
        IrDynamicOperatorExpressionImpl irDynamicOperatorExpressionImplTryGenerateDynamicOperatorCall = tryGenerateDynamicOperatorCall(startOffset, endOffset, irExpressionConvertToIrExpression, irExpressionConvertToIrExpression2, irStatementOrigin);
        if (irDynamicOperatorExpressionImplTryGenerateDynamicOperatorCall != null) {
            return irDynamicOperatorExpressionImplTryGenerateDynamicOperatorCall;
        }
        ConeClassLikeType comparisonType = primitiveConeNumericComparisonInfoInferPrimitiveNumericComparisonInfo != null ? primitiveConeNumericComparisonInfoInferPrimitiveNumericComparisonInfo.getComparisonType() : null;
        if (comparisonType == null || (irClassSymbol = getTypeConverter().getClassIdToSymbolMap$fir2ir().get(comparisonType.getLookupTag().getClassId())) == null || (eqeqSymbol = getIrBuiltIns().getIeee754equalsFunByOperandType().get(irClassSymbol)) == null) {
            eqeqSymbol = getIrBuiltIns().getEqeqSymbol();
        }
        IrExpression irExpressionPrimitiveOp2 = PrimitivesKt.primitiveOp2(startOffset, endOffset, eqeqSymbol, getIrBuiltIns().getBooleanType(), irStatementOrigin, irExpressionConvertToIrExpression, irExpressionConvertToIrExpression2);
        return operation == FirOperation.EQ ? irExpressionPrimitiveOp2 : negate(irExpressionPrimitiveOp2, irStatementOrigin);
    }

    private final IrExpression transformIdentityOperatorCall(int startOffset, int endOffset, FirOperation operation, List<? extends FirExpression> arguments) throws Exception {
        IrStatementOrigin.EQEQEQ eqeqeq;
        int i = WhenMappings.$EnumSwitchMapping$0[operation.ordinal()];
        if (i == 7) {
            eqeqeq = IrStatementOrigin.EQEQEQ.INSTANCE;
        } else if (i == 8) {
            eqeqeq = IrStatementOrigin.EXCLEQEQ.INSTANCE;
        } else {
            throw new IllegalStateException(("Not an identity operation: " + operation).toString());
        }
        IrExpression irExpressionConvertToIrExpression$fir2ir$default = Fir2IrVisitor.convertToIrExpression$fir2ir$default(this.visitor, arguments.get(0), false, 2, null);
        IrExpression irExpressionConvertToIrExpression$fir2ir$default2 = Fir2IrVisitor.convertToIrExpression$fir2ir$default(this.visitor, arguments.get(1), false, 2, null);
        IrStatementOrigin irStatementOrigin = eqeqeq;
        IrDynamicOperatorExpressionImpl irDynamicOperatorExpressionImplTryGenerateDynamicOperatorCall = tryGenerateDynamicOperatorCall(startOffset, endOffset, irExpressionConvertToIrExpression$fir2ir$default, irExpressionConvertToIrExpression$fir2ir$default2, irStatementOrigin);
        if (irDynamicOperatorExpressionImplTryGenerateDynamicOperatorCall != null) {
            return irDynamicOperatorExpressionImplTryGenerateDynamicOperatorCall;
        }
        IrExpression irExpressionPrimitiveOp2 = PrimitivesKt.primitiveOp2(startOffset, endOffset, getIrBuiltIns().getEqeqeqSymbol(), getIrBuiltIns().getBooleanType(), irStatementOrigin, irExpressionConvertToIrExpression$fir2ir$default, irExpressionConvertToIrExpression$fir2ir$default2);
        return operation == FirOperation.IDENTITY ? irExpressionPrimitiveOp2 : negate(irExpressionPrimitiveOp2, irStatementOrigin);
    }

    private final IrExpression negate(IrExpression irExpression, IrStatementOrigin irStatementOrigin) {
        return PrimitivesKt.primitiveOp1(irExpression.getStartOffset(), irExpression.getEndOffset(), getIrBuiltIns().getBooleanNotSymbol(), getIrBuiltIns().getBooleanType(), irStatementOrigin, irExpression);
    }

    /* JADX WARN: Code duplicated, block: B:22:0x0040  */
    private final IrExpression convertToIrExpression(FirExpression firExpression, PrimitiveConeNumericComparisonInfo primitiveConeNumericComparisonInfo, boolean z) {
        ConeClassLikeType rightType;
        IrSimpleFunctionSymbol simpleFunction;
        FirExpression originalExpression;
        FirTypeRef typeRef;
        Boolean boolIsMarkedNullable;
        FirSmartCastExpression firSmartCastExpression = firExpression instanceof FirSmartCastExpression ? (FirSmartCastExpression) firExpression : null;
        boolean zBooleanValue = (firSmartCastExpression == null || (originalExpression = firSmartCastExpression.getOriginalExpression()) == null || (typeRef = originalExpression.getTypeRef()) == null || (boolIsMarkedNullable = FirTypeUtilsKt.isMarkedNullable(typeRef)) == null) ? false : boolIsMarkedNullable.booleanValue();
        IrExpression irExpressionConvertToIrExpression$fir2ir$default = Fir2IrVisitor.convertToIrExpression$fir2ir$default(this.visitor, firExpression, false, 2, null);
        if (z) {
            if (primitiveConeNumericComparisonInfo != null) {
                rightType = primitiveConeNumericComparisonInfo.getLeftType();
            } else {
                rightType = null;
            }
        } else if (primitiveConeNumericComparisonInfo != null) {
            rightType = primitiveConeNumericComparisonInfo.getRightType();
        } else {
            rightType = null;
        }
        ConeClassLikeType comparisonType = primitiveConeNumericComparisonInfo != null ? primitiveConeNumericComparisonInfo.getComparisonType() : null;
        boolean zAreEqual = Intrinsics.areEqual(primitiveConeNumericComparisonInfo != null ? primitiveConeNumericComparisonInfo.getLeftType() : null, primitiveConeNumericComparisonInfo != null ? primitiveConeNumericComparisonInfo.getRightType() : null);
        if (comparisonType == null) {
            return convertToIrExpression$eraseImplicitCast(irExpressionConvertToIrExpression$fir2ir$default, zAreEqual, zBooleanValue);
        }
        if (rightType == null) {
            throw new IllegalStateException("operandType should be non-null if targetType is non-null".toString());
        }
        ClassId classId = rightType.getLookupTag().getClassId();
        if (Intrinsics.areEqual(classId, comparisonType.getLookupTag().getClassId())) {
            return convertToIrExpression$eraseImplicitCast(irExpressionConvertToIrExpression$fir2ir$default, zAreEqual, zBooleanValue);
        }
        IrClassSymbol irClassSymbol = getTypeConverter().getClassIdToSymbolMap$fir2ir().get(classId);
        if (irClassSymbol == null || (simpleFunction = AdditionalIrUtilsKt.getSimpleFunction(irClassSymbol, "to" + comparisonType.getLookupTag().getClassId().getShortClassName().asString())) == null) {
            throw new IllegalStateException(("No conversion function for " + rightType + " ~> " + comparisonType).toString());
        }
        IrCallImpl irCallImpl = new IrCallImpl(irExpressionConvertToIrExpression$fir2ir$default.getStartOffset(), irExpressionConvertToIrExpression$fir2ir$default.getEndOffset(), simpleFunction.getOwner().getReturnType(), simpleFunction, 0, 0, null, null, 192, null);
        irCallImpl.setDispatchReceiver(irExpressionConvertToIrExpression$fir2ir$default);
        if (ConeTypeUtilsKt.isNullable(rightType)) {
            Pair<IrVariable, IrValueSymbol> pairCreateTemporaryVariableForSafeCallConstruction = ConversionUtilsKt.createTemporaryVariableForSafeCallConstruction(this.components, irExpressionConvertToIrExpression$fir2ir$default, this.conversionScope);
            IrVariable irVariableComponent1 = pairCreateTemporaryVariableForSafeCallConstruction.component1();
            IrValueSymbol irValueSymbolComponent2 = pairCreateTemporaryVariableForSafeCallConstruction.component2();
            irCallImpl.setDispatchReceiver(new IrGetValueImpl(irExpressionConvertToIrExpression$fir2ir$default.getStartOffset(), irExpressionConvertToIrExpression$fir2ir$default.getEndOffset(), irValueSymbolComponent2, null, 8, null));
            return ConversionUtilsKt.createSafeCallConstruction(this.components, irVariableComponent1, irValueSymbolComponent2, irCallImpl);
        }
        return irCallImpl;
    }

    private static final IrExpression convertToIrExpression$eraseImplicitCast(IrExpression irExpression, boolean z, boolean z2) {
        if (irExpression instanceof IrTypeOperatorCall) {
            boolean zIsDoubleOrFloatWithoutNullability = IrTypePredicatesKt.isDoubleOrFloatWithoutNullability(irExpression.getType());
            if (z && !zIsDoubleOrFloatWithoutNullability) {
                IrTypeOperatorCall irTypeOperatorCall = (IrTypeOperatorCall) irExpression;
                if (irTypeOperatorCall.getOperator() == IrTypeOperator.IMPLICIT_CAST) {
                    return irTypeOperatorCall.getArgument();
                }
            }
            IrType type = irExpression.getType();
            if (zIsDoubleOrFloatWithoutNullability && z2 && (type instanceof IrSimpleType) && !IrTypeUtilsKt.isNullable(type)) {
                IrType irTypeMakeNullable = IrTypesKt.makeNullable(type);
                int startOffset = irExpression.getStartOffset();
                int endOffset = irExpression.getEndOffset();
                IrTypeOperatorCall irTypeOperatorCall2 = (IrTypeOperatorCall) irExpression;
                return new IrTypeOperatorCallImpl(startOffset, endOffset, irTypeMakeNullable, irTypeOperatorCall2.getOperator(), irTypeMakeNullable, irTypeOperatorCall2.getArgument());
            }
        }
        return irExpression;
    }
}
