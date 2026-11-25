package org.jetbrains.kotlin.backend.common.serialization;

import androidx.exifinterface.media.ExifInterface;
import io.netty.util.internal.StringUtil;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.NotImplementedError;
import kotlin.collections.MapsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DoubleCompanionObject;
import kotlin.jvm.internal.FloatCompanionObject;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Reflection;
import kotlin.ranges.RangesKt;
import kotlin.reflect.KClass;
import kotlin.text.StringsKt;
import net.bytebuddy.implementation.MethodDelegation;
import org.catrobat.catroid.web.ServerAuthenticationConstants;
import org.jetbrains.kotlin.backend.common.linkage.issues.IrSymbolTypeMismatchException;
import org.jetbrains.kotlin.backend.common.serialization.encodings.BinaryCoordinates;
import org.jetbrains.kotlin.backend.common.serialization.encodings.BinarySymbolData;
import org.jetbrains.kotlin.backend.common.serialization.proto.FieldAccessCommon;
import org.jetbrains.kotlin.backend.common.serialization.proto.IrConst;
import org.jetbrains.kotlin.backend.common.serialization.proto.IrDeclaration;
import org.jetbrains.kotlin.backend.common.serialization.proto.IrDoWhile;
import org.jetbrains.kotlin.backend.common.serialization.proto.IrDynamicMemberExpression;
import org.jetbrains.kotlin.backend.common.serialization.proto.IrDynamicOperatorExpression;
import org.jetbrains.kotlin.backend.common.serialization.proto.IrExpression;
import org.jetbrains.kotlin.backend.common.serialization.proto.IrFunction;
import org.jetbrains.kotlin.backend.common.serialization.proto.IrFunctionExpression;
import org.jetbrains.kotlin.backend.common.serialization.proto.IrGetObject;
import org.jetbrains.kotlin.backend.common.serialization.proto.IrOperation;
import org.jetbrains.kotlin.backend.common.serialization.proto.IrStatement;
import org.jetbrains.kotlin.backend.common.serialization.proto.IrStringConcat;
import org.jetbrains.kotlin.backend.common.serialization.proto.IrSyntheticBodyKind;
import org.jetbrains.kotlin.backend.common.serialization.proto.IrThrow;
import org.jetbrains.kotlin.backend.common.serialization.proto.IrTry;
import org.jetbrains.kotlin.backend.common.serialization.proto.IrTypeOp;
import org.jetbrains.kotlin.backend.common.serialization.proto.IrTypeOperator;
import org.jetbrains.kotlin.backend.common.serialization.proto.IrVarargElement;
import org.jetbrains.kotlin.backend.common.serialization.proto.IrVariable;
import org.jetbrains.kotlin.backend.common.serialization.proto.IrWhile;
import org.jetbrains.kotlin.backend.common.serialization.proto.Loop;
import org.jetbrains.kotlin.backend.common.serialization.proto.MemberAccessCommon;
import org.jetbrains.kotlin.backend.common.serialization.proto.NullableIrExpression;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;
import org.jetbrains.kotlin.ir.IrBuiltIns;
import org.jetbrains.kotlin.ir.IrElement;
import org.jetbrains.kotlin.ir.declarations.IrClass;
import org.jetbrains.kotlin.ir.declarations.IrFactory;
import org.jetbrains.kotlin.ir.declarations.IrFactoryHelpersKt;
import org.jetbrains.kotlin.ir.declarations.IrTypeParameter;
import org.jetbrains.kotlin.ir.expressions.IrBlock;
import org.jetbrains.kotlin.ir.expressions.IrBlockBody;
import org.jetbrains.kotlin.ir.expressions.IrBranch;
import org.jetbrains.kotlin.ir.expressions.IrBreak;
import org.jetbrains.kotlin.ir.expressions.IrCall;
import org.jetbrains.kotlin.ir.expressions.IrCatch;
import org.jetbrains.kotlin.ir.expressions.IrClassReference;
import org.jetbrains.kotlin.ir.expressions.IrComposite;
import org.jetbrains.kotlin.ir.expressions.IrConstructorCall;
import org.jetbrains.kotlin.ir.expressions.IrContinue;
import org.jetbrains.kotlin.ir.expressions.IrDelegatingConstructorCall;
import org.jetbrains.kotlin.ir.expressions.IrDynamicOperator;
import org.jetbrains.kotlin.ir.expressions.IrEnumConstructorCall;
import org.jetbrains.kotlin.ir.expressions.IrErrorCallExpression;
import org.jetbrains.kotlin.ir.expressions.IrErrorExpression;
import org.jetbrains.kotlin.ir.expressions.IrFunctionReference;
import org.jetbrains.kotlin.ir.expressions.IrGetClass;
import org.jetbrains.kotlin.ir.expressions.IrGetEnumValue;
import org.jetbrains.kotlin.ir.expressions.IrGetField;
import org.jetbrains.kotlin.ir.expressions.IrGetObjectValue;
import org.jetbrains.kotlin.ir.expressions.IrGetValue;
import org.jetbrains.kotlin.ir.expressions.IrInstanceInitializerCall;
import org.jetbrains.kotlin.ir.expressions.IrLocalDelegatedPropertyReference;
import org.jetbrains.kotlin.ir.expressions.IrLoop;
import org.jetbrains.kotlin.ir.expressions.IrMemberAccessExpression;
import org.jetbrains.kotlin.ir.expressions.IrPropertyReference;
import org.jetbrains.kotlin.ir.expressions.IrReturn;
import org.jetbrains.kotlin.ir.expressions.IrSetField;
import org.jetbrains.kotlin.ir.expressions.IrSetValue;
import org.jetbrains.kotlin.ir.expressions.IrSpreadElement;
import org.jetbrains.kotlin.ir.expressions.IrStatementOrigin;
import org.jetbrains.kotlin.ir.expressions.IrStatementOriginImpl;
import org.jetbrains.kotlin.ir.expressions.IrStringConcatenation;
import org.jetbrains.kotlin.ir.expressions.IrSyntheticBody;
import org.jetbrains.kotlin.ir.expressions.IrTypeOperatorCall;
import org.jetbrains.kotlin.ir.expressions.IrVararg;
import org.jetbrains.kotlin.ir.expressions.IrWhen;
import org.jetbrains.kotlin.ir.expressions.impl.IrBlockImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrBranchImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrBreakImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrCallImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrCatchImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrClassReferenceImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrCompositeImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrConstImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrConstructorCallImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrContinueImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrDelegatingConstructorCallImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrDoWhileLoopImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrDynamicMemberExpressionImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrDynamicOperatorExpressionImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrEnumConstructorCallImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrErrorCallExpressionImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrErrorExpressionImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrFunctionExpressionImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrFunctionReferenceImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrGetClassImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrGetEnumValueImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrGetFieldImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrGetObjectValueImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrGetValueImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrInstanceInitializerCallImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrLocalDelegatedPropertyReferenceImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrPropertyReferenceImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrReturnImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrSetFieldImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrSetValueImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrSpreadElementImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrStringConcatenationImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrSyntheticBodyImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrThrowImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrTryImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrTypeOperatorCallImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrVarargImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrWhenImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrWhileLoopImpl;
import org.jetbrains.kotlin.ir.symbols.IrClassSymbol;
import org.jetbrains.kotlin.ir.symbols.IrClassifierSymbol;
import org.jetbrains.kotlin.ir.symbols.IrConstructorSymbol;
import org.jetbrains.kotlin.ir.symbols.IrEnumEntrySymbol;
import org.jetbrains.kotlin.ir.symbols.IrFieldSymbol;
import org.jetbrains.kotlin.ir.symbols.IrFunctionSymbol;
import org.jetbrains.kotlin.ir.symbols.IrLocalDelegatedPropertySymbol;
import org.jetbrains.kotlin.ir.symbols.IrPropertySymbol;
import org.jetbrains.kotlin.ir.symbols.IrReturnTargetSymbol;
import org.jetbrains.kotlin.ir.symbols.IrSimpleFunctionSymbol;
import org.jetbrains.kotlin.ir.symbols.IrSymbol;
import org.jetbrains.kotlin.ir.symbols.IrValueSymbol;
import org.jetbrains.kotlin.ir.symbols.IrVariableSymbol;
import org.jetbrains.kotlin.ir.types.IrSimpleType;
import org.jetbrains.kotlin.ir.types.IrType;
import org.jetbrains.kotlin.ir.types.IrTypeSubstitutor;
import org.jetbrains.kotlin.ir.types.IrTypeSystemContextKt;
import org.jetbrains.kotlin.ir.types.impl.IrDelegatedSimpleType;
import org.jetbrains.kotlin.ir.types.impl.IrSimpleTypeBuilder;
import org.jetbrains.kotlin.ir.types.impl.IrSimpleTypeImpl;
import org.jetbrains.kotlin.ir.types.impl.IrSimpleTypeImplKt;
import org.jetbrains.kotlin.ir.util.IdSignature;
import org.jetbrains.kotlin.ir.util.IrUtilsKt;
import org.jetbrains.kotlin.ir.util.ReferenceSymbolTable;
import org.jetbrains.kotlin.utils.CollectionsKt;

/* JADX INFO: compiled from: IrBodyDeserializer.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000Ê\u0004\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\b\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\t\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u0000 ¾\u00012\u00020\u0001:\u0004¾\u0001¿\u0001B-\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\t\u0012\u0006\u0010\n\u001a\u00020\u000b¢\u0006\u0002\u0010\fJ\u000e\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\u0015J(\u0010\u0016\u001a\u00020\u00172\u0006\u0010\u0014\u001a\u00020\u00182\u0006\u0010\u0019\u001a\u00020\u000f2\u0006\u0010\u001a\u001a\u00020\u000f2\u0006\u0010\u001b\u001a\u00020\u001cH\u0002J \u0010\u001d\u001a\u00020\u001e2\u0006\u0010\u0014\u001a\u00020\u001f2\u0006\u0010\u0019\u001a\u00020\u000f2\u0006\u0010\u001a\u001a\u00020\u000fH\u0002J \u0010 \u001a\u00020!2\u0006\u0010\u0014\u001a\u00020\"2\u0006\u0010\u0019\u001a\u00020\u000f2\u0006\u0010\u001a\u001a\u00020\u000fH\u0002J(\u0010#\u001a\u00020$2\u0006\u0010\u0014\u001a\u00020%2\u0006\u0010\u0019\u001a\u00020\u000f2\u0006\u0010\u001a\u001a\u00020\u000f2\u0006\u0010\u001b\u001a\u00020\u001cH\u0002J(\u0010&\u001a\u00020'2\u0006\u0010\u0014\u001a\u00020(2\u0006\u0010\u0019\u001a\u00020\u000f2\u0006\u0010\u001a\u001a\u00020\u000f2\u0006\u0010\u001b\u001a\u00020\u001cH\u0002J \u0010)\u001a\u00020*2\u0006\u0010\u0014\u001a\u00020+2\u0006\u0010\u0019\u001a\u00020\u000f2\u0006\u0010\u001a\u001a\u00020\u000fH\u0002J(\u0010,\u001a\u00020-2\u0006\u0010\u0014\u001a\u00020.2\u0006\u0010\u0019\u001a\u00020\u000f2\u0006\u0010\u001a\u001a\u00020\u000f2\u0006\u0010\u001b\u001a\u00020\u001cH\u0002J(\u0010/\u001a\u0002002\u0006\u0010\u0014\u001a\u0002012\u0006\u0010\u0019\u001a\u00020\u000f2\u0006\u0010\u001a\u001a\u00020\u000f2\u0006\u0010\u001b\u001a\u00020\u001cH\u0002J(\u00102\u001a\u0002032\u0006\u0010\u0014\u001a\u0002042\u0006\u0010\u0019\u001a\u00020\u000f2\u0006\u0010\u001a\u001a\u00020\u000f2\u0006\u0010\u001b\u001a\u00020\u001cH\u0002J(\u00105\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\u00152\u0006\u0010\u0019\u001a\u00020\u000f2\u0006\u0010\u001a\u001a\u00020\u000f2\u0006\u0010\u001b\u001a\u00020\u001cH\u0002J(\u00106\u001a\u0002072\u0006\u0010\u0014\u001a\u0002082\u0006\u0010\u0019\u001a\u00020\u000f2\u0006\u0010\u001a\u001a\u00020\u000f2\u0006\u0010\u001b\u001a\u00020\u001cH\u0002J \u00109\u001a\u00020:2\u0006\u0010\u0014\u001a\u00020;2\u0006\u0010\u0019\u001a\u00020\u000f2\u0006\u0010\u001a\u001a\u00020\u000fH\u0002J(\u0010<\u001a\u00020\u00102\u0006\u0010\u0014\u001a\u00020=2\u0006\u0010\u0019\u001a\u00020\u000f2\u0006\u0010\u001a\u001a\u00020\u000f2\u0006\u0010\u001b\u001a\u00020\u001cH\u0002J(\u0010>\u001a\u00020?2\u0006\u0010\u0014\u001a\u00020@2\u0006\u0010\u0019\u001a\u00020\u000f2\u0006\u0010\u001a\u001a\u00020\u000f2\u0006\u0010\u001b\u001a\u00020\u001cH\u0002J\u0010\u0010A\u001a\u00020B2\u0006\u0010C\u001a\u00020DH\u0002J(\u0010E\u001a\u00020F2\u0006\u0010\u0014\u001a\u00020G2\u0006\u0010\u0019\u001a\u00020\u000f2\u0006\u0010\u001a\u001a\u00020\u000f2\u0006\u0010\u001b\u001a\u00020\u001cH\u0002J \u0010H\u001a\u00020I2\u0006\u0010\u0014\u001a\u00020J2\u0006\u0010\u0019\u001a\u00020\u000f2\u0006\u0010\u001a\u001a\u00020\u000fH\u0002J(\u0010K\u001a\u00020L2\u0006\u0010\u0014\u001a\u00020M2\u0006\u0010\u0019\u001a\u00020\u000f2\u0006\u0010\u001a\u001a\u00020\u000f2\u0006\u0010\u001b\u001a\u00020\u001cH\u0002J(\u0010N\u001a\u00020O2\u0006\u0010\u0014\u001a\u00020P2\u0006\u0010\u0019\u001a\u00020\u000f2\u0006\u0010\u001a\u001a\u00020\u000f2\u0006\u0010\u001b\u001a\u00020\u001cH\u0002J\u000e\u0010Q\u001a\u0002032\u0006\u0010\u0014\u001a\u00020RJ(\u0010S\u001a\u00020T2\u0006\u0010U\u001a\u00020V2\u0006\u0010\u0019\u001a\u00020\u000f2\u0006\u0010\u001a\u001a\u00020\u000f2\u0006\u0010\u001b\u001a\u00020\u001cH\u0002J(\u0010W\u001a\u00020X2\u0006\u0010\u0014\u001a\u00020Y2\u0006\u0010\u0019\u001a\u00020\u000f2\u0006\u0010\u001a\u001a\u00020\u000f2\u0006\u0010\u001b\u001a\u00020\u001cH\u0002J(\u0010Z\u001a\u00020[2\u0006\u0010\u0014\u001a\u00020\\2\u0006\u0010\u0019\u001a\u00020\u000f2\u0006\u0010\u001a\u001a\u00020\u000f2\u0006\u0010\u001b\u001a\u00020\u001cH\u0002J(\u0010]\u001a\u00020^2\u0006\u0010\u0014\u001a\u00020_2\u0006\u0010\u0019\u001a\u00020\u000f2\u0006\u0010\u001a\u001a\u00020\u000f2\u0006\u0010\u001b\u001a\u00020\u001cH\u0002J(\u0010`\u001a\u00020a2\u0006\u0010\u0014\u001a\u00020b2\u0006\u0010\u0019\u001a\u00020\u000f2\u0006\u0010\u001a\u001a\u00020\u000f2\u0006\u0010\u001b\u001a\u00020\u001cH\u0002J(\u0010c\u001a\u00020d2\u0006\u0010\u0014\u001a\u00020e2\u0006\u0010\u0019\u001a\u00020\u000f2\u0006\u0010\u001a\u001a\u00020\u000f2\u0006\u0010\u001b\u001a\u00020\u001cH\u0002J(\u0010f\u001a\u00020g2\u0006\u0010\u0014\u001a\u00020h2\u0006\u0010\u0019\u001a\u00020\u000f2\u0006\u0010\u001a\u001a\u00020\u000f2\u0006\u0010\u001b\u001a\u00020\u001cH\u0002J \u0010i\u001a\u00020j2\u0006\u0010\u0014\u001a\u00020k2\u0006\u0010\u0019\u001a\u00020\u000f2\u0006\u0010\u001a\u001a\u00020\u000fH\u0002J(\u0010l\u001a\u00020m2\u0006\u0010\u0014\u001a\u00020n2\u0006\u0010\u0019\u001a\u00020\u000f2\u0006\u0010\u001a\u001a\u00020\u000f2\u0006\u0010\u001b\u001a\u00020\u001cH\u0002J!\u0010o\u001a\u0004\u0018\u00010p2\u0006\u0010q\u001a\u00020\u00052\f\u0010r\u001a\b\u0012\u0004\u0012\u00020\u000f0sH\u0082\bJ\u0010\u0010o\u001a\u00020p2\u0006\u0010r\u001a\u00020\u000fH\u0002J\u0018\u0010t\u001a\u00020\u00102\u0006\u0010\u0014\u001a\u00020u2\u0006\u0010v\u001a\u00020\u0010H\u0002J\u001e\u0010w\u001a\u00020\u00102\u0006\u0010x\u001a\u00020\u000f2\f\u0010y\u001a\b\u0012\u0004\u0012\u00020\u00100sH\u0002J\u001c\u0010z\u001a\u00020{2\n\u0010|\u001a\u0006\u0012\u0002\b\u00030}2\u0006\u0010\u0014\u001a\u00020~H\u0002J)\u0010\u007f\u001a\u0002032\u0007\u0010\u0014\u001a\u00030\u0080\u00012\u0006\u0010\u0019\u001a\u00020\u000f2\u0006\u0010\u001a\u001a\u00020\u000f2\u0006\u0010\u001b\u001a\u00020\u001cH\u0002J+\u0010\u0081\u0001\u001a\u00030\u0082\u00012\u0007\u0010\u0014\u001a\u00030\u0083\u00012\u0006\u0010\u0019\u001a\u00020\u000f2\u0006\u0010\u001a\u001a\u00020\u000f2\u0006\u0010\u001b\u001a\u00020\u001cH\u0002J#\u0010\u0084\u0001\u001a\u00030\u0085\u00012\u0007\u0010\u0014\u001a\u00030\u0086\u00012\u0006\u0010\u0019\u001a\u00020\u000f2\u0006\u0010\u001a\u001a\u00020\u000fH\u0002J#\u0010\u0087\u0001\u001a\u00030\u0088\u00012\u0007\u0010\u0014\u001a\u00030\u0089\u00012\u0006\u0010\u0019\u001a\u00020\u000f2\u0006\u0010\u001a\u001a\u00020\u000fH\u0002J#\u0010\u008a\u0001\u001a\u00030\u008b\u00012\u0007\u0010\u0014\u001a\u00030\u008c\u00012\u0006\u0010\u0019\u001a\u00020\u000f2\u0006\u0010\u001a\u001a\u00020\u000fH\u0002J\u0013\u0010\u008d\u0001\u001a\u00030\u008e\u00012\u0007\u0010\u0014\u001a\u00030\u008f\u0001H\u0002J\u0019\u0010\u0090\u0001\u001a\u00030\u0091\u00012\u0007\u0010\u0014\u001a\u00030\u0092\u0001H\u0000¢\u0006\u0003\b\u0093\u0001J+\u0010\u0094\u0001\u001a\u00030\u0095\u00012\u0007\u0010\u0014\u001a\u00030\u0096\u00012\u0006\u0010\u0019\u001a\u00020\u000f2\u0006\u0010\u001a\u001a\u00020\u000f2\u0006\u0010\u001b\u001a\u00020\u001cH\u0002J#\u0010\u0097\u0001\u001a\u00030\u0098\u00012\u0007\u0010\u0014\u001a\u00030\u0099\u00012\u0006\u0010\u0019\u001a\u00020\u000f2\u0006\u0010\u001a\u001a\u00020\u000fH\u0002J#\u0010\u009a\u0001\u001a\u00030\u009b\u00012\u0007\u0010\u0014\u001a\u00030\u009c\u00012\u0006\u0010\u0019\u001a\u00020\u000f2\u0006\u0010\u001a\u001a\u00020\u000fH\u0002J+\u0010\u009d\u0001\u001a\u00030\u009e\u00012\u0007\u0010\u0014\u001a\u00030\u009f\u00012\u0006\u0010\u0019\u001a\u00020\u000f2\u0006\u0010\u001a\u001a\u00020\u000f2\u0006\u0010\u001b\u001a\u00020\u001cH\u0002J+\u0010 \u0001\u001a\u00030¡\u00012\u0007\u0010\u0014\u001a\u00030¢\u00012\u0006\u0010\u0019\u001a\u00020\u000f2\u0006\u0010\u001a\u001a\u00020\u000f2\u0006\u0010\u001b\u001a\u00020\u001cH\u0002J\u0013\u0010£\u0001\u001a\u00030¤\u00012\u0007\u0010C\u001a\u00030¥\u0001H\u0002J@\u0010¦\u0001\u001a\u0003H§\u0001\"\f\b\u0000\u0010§\u0001\u0018\u0001*\u00030¨\u00012\b\u0010©\u0001\u001a\u00030ª\u00012\n\u0010«\u0001\u001a\u0005\u0018\u00010¬\u00012\t\b\u0002\u0010\u00ad\u0001\u001a\u00020\u0005H\u0082\b¢\u0006\u0003\u0010®\u0001JF\u0010¯\u0001\u001a\u0005\u0018\u0001H§\u0001\"\f\b\u0000\u0010§\u0001\u0018\u0001*\u00030¨\u00012\u0007\u0010°\u0001\u001a\u00020\u00052\n\u0010«\u0001\u001a\u0005\u0018\u00010¬\u00012\u000e\u0010©\u0001\u001a\t\u0012\u0005\u0012\u00030ª\u00010sH\u0082\b¢\u0006\u0003\u0010±\u0001J+\u0010²\u0001\u001a\u00030³\u00012\u0007\u0010\u0014\u001a\u00030´\u00012\u0006\u0010\u0019\u001a\u00020\u000f2\u0006\u0010\u001a\u001a\u00020\u000f2\u0006\u0010\u001b\u001a\u00020\u001cH\u0002J\u0014\u0010µ\u0001\u001a\u00030¶\u00012\b\u0010·\u0001\u001a\u00030¸\u0001H\u0002J+\u0010¹\u0001\u001a\u00030º\u00012\u0007\u0010\u0014\u001a\u00030»\u00012\u0006\u0010\u0019\u001a\u00020\u000f2\u0006\u0010\u001a\u001a\u00020\u000f2\u0006\u0010\u001b\u001a\u00020\u001cH\u0002J*\u0010¼\u0001\u001a\u00020\u00102\u0007\u0010\u0014\u001a\u00030½\u00012\u0006\u0010\u0019\u001a\u00020\u000f2\u0006\u0010\u001a\u001a\u00020\u000f2\u0006\u0010\u001b\u001a\u00020\u001cH\u0002R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u000bX\u0082\u0004¢\u0006\u0002\n\u0000R*\u0010\r\u001a\u001e\u0012\u0004\u0012\u00020\u000f\u0012\u0004\u0012\u00020\u00100\u000ej\u000e\u0012\u0004\u0012\u00020\u000f\u0012\u0004\u0012\u00020\u0010`\u0011X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\tX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006À\u0001"}, d2 = {"Lorg/jetbrains/kotlin/backend/common/serialization/IrBodyDeserializer;", "", "builtIns", "Lorg/jetbrains/kotlin/ir/IrBuiltIns;", "allowErrorNodes", "", "irFactory", "Lorg/jetbrains/kotlin/ir/declarations/IrFactory;", "libraryFile", "Lorg/jetbrains/kotlin/backend/common/serialization/IrLibraryFile;", "declarationDeserializer", "Lorg/jetbrains/kotlin/backend/common/serialization/IrDeclarationDeserializer;", "(Lorg/jetbrains/kotlin/ir/IrBuiltIns;ZLorg/jetbrains/kotlin/ir/declarations/IrFactory;Lorg/jetbrains/kotlin/backend/common/serialization/IrLibraryFile;Lorg/jetbrains/kotlin/backend/common/serialization/IrDeclarationDeserializer;)V", "fileLoops", "Ljava/util/HashMap;", "", "Lorg/jetbrains/kotlin/ir/expressions/IrLoop;", "Lkotlin/collections/HashMap;", "deserializeAnnotation", "Lorg/jetbrains/kotlin/ir/expressions/IrConstructorCall;", "proto", "Lorg/jetbrains/kotlin/backend/common/serialization/proto/IrConstructorCall;", "deserializeBlock", "Lorg/jetbrains/kotlin/ir/expressions/IrBlock;", "Lorg/jetbrains/kotlin/backend/common/serialization/proto/IrBlock;", "start", "end", ModuleXmlParser.TYPE, "Lorg/jetbrains/kotlin/ir/types/IrType;", "deserializeBlockBody", "Lorg/jetbrains/kotlin/ir/expressions/IrBlockBody;", "Lorg/jetbrains/kotlin/backend/common/serialization/proto/IrBlockBody;", "deserializeBranch", "Lorg/jetbrains/kotlin/ir/expressions/IrBranch;", "Lorg/jetbrains/kotlin/backend/common/serialization/proto/IrBranch;", "deserializeBreak", "Lorg/jetbrains/kotlin/ir/expressions/IrBreak;", "Lorg/jetbrains/kotlin/backend/common/serialization/proto/IrBreak;", "deserializeCall", "Lorg/jetbrains/kotlin/ir/expressions/IrCall;", "Lorg/jetbrains/kotlin/backend/common/serialization/proto/IrCall;", "deserializeCatch", "Lorg/jetbrains/kotlin/ir/expressions/IrCatch;", "Lorg/jetbrains/kotlin/backend/common/serialization/proto/IrCatch;", "deserializeClassReference", "Lorg/jetbrains/kotlin/ir/expressions/IrClassReference;", "Lorg/jetbrains/kotlin/backend/common/serialization/proto/IrClassReference;", "deserializeComposite", "Lorg/jetbrains/kotlin/ir/expressions/IrComposite;", "Lorg/jetbrains/kotlin/backend/common/serialization/proto/IrComposite;", "deserializeConst", "Lorg/jetbrains/kotlin/ir/expressions/IrExpression;", "Lorg/jetbrains/kotlin/backend/common/serialization/proto/IrConst;", "deserializeConstructorCall", "deserializeContinue", "Lorg/jetbrains/kotlin/ir/expressions/IrContinue;", "Lorg/jetbrains/kotlin/backend/common/serialization/proto/IrContinue;", "deserializeDelegatingConstructorCall", "Lorg/jetbrains/kotlin/ir/expressions/IrDelegatingConstructorCall;", "Lorg/jetbrains/kotlin/backend/common/serialization/proto/IrDelegatingConstructorCall;", "deserializeDoWhile", "Lorg/jetbrains/kotlin/backend/common/serialization/proto/IrDoWhile;", "deserializeDynamicMemberExpression", "Lorg/jetbrains/kotlin/ir/expressions/impl/IrDynamicMemberExpressionImpl;", "Lorg/jetbrains/kotlin/backend/common/serialization/proto/IrDynamicMemberExpression;", "deserializeDynamicOperator", "Lorg/jetbrains/kotlin/ir/expressions/IrDynamicOperator;", "operator", "Lorg/jetbrains/kotlin/backend/common/serialization/proto/IrDynamicOperatorExpression$IrDynamicOperator;", "deserializeDynamicOperatorExpression", "Lorg/jetbrains/kotlin/ir/expressions/impl/IrDynamicOperatorExpressionImpl;", "Lorg/jetbrains/kotlin/backend/common/serialization/proto/IrDynamicOperatorExpression;", "deserializeEnumConstructorCall", "Lorg/jetbrains/kotlin/ir/expressions/IrEnumConstructorCall;", "Lorg/jetbrains/kotlin/backend/common/serialization/proto/IrEnumConstructorCall;", "deserializeErrorCallExpression", "Lorg/jetbrains/kotlin/ir/expressions/IrErrorCallExpression;", "Lorg/jetbrains/kotlin/backend/common/serialization/proto/IrErrorCallExpression;", "deserializeErrorExpression", "Lorg/jetbrains/kotlin/ir/expressions/IrErrorExpression;", "Lorg/jetbrains/kotlin/backend/common/serialization/proto/IrErrorExpression;", "deserializeExpression", "Lorg/jetbrains/kotlin/backend/common/serialization/proto/IrExpression;", "deserializeFunctionExpression", "Lorg/jetbrains/kotlin/ir/expressions/impl/IrFunctionExpressionImpl;", "functionExpression", "Lorg/jetbrains/kotlin/backend/common/serialization/proto/IrFunctionExpression;", "deserializeFunctionReference", "Lorg/jetbrains/kotlin/ir/expressions/IrFunctionReference;", "Lorg/jetbrains/kotlin/backend/common/serialization/proto/IrFunctionReference;", "deserializeGetClass", "Lorg/jetbrains/kotlin/ir/expressions/IrGetClass;", "Lorg/jetbrains/kotlin/backend/common/serialization/proto/IrGetClass;", "deserializeGetEnumValue", "Lorg/jetbrains/kotlin/ir/expressions/IrGetEnumValue;", "Lorg/jetbrains/kotlin/backend/common/serialization/proto/IrGetEnumValue;", "deserializeGetField", "Lorg/jetbrains/kotlin/ir/expressions/IrGetField;", "Lorg/jetbrains/kotlin/backend/common/serialization/proto/IrGetField;", "deserializeGetObject", "Lorg/jetbrains/kotlin/ir/expressions/IrGetObjectValue;", "Lorg/jetbrains/kotlin/backend/common/serialization/proto/IrGetObject;", "deserializeGetValue", "Lorg/jetbrains/kotlin/ir/expressions/IrGetValue;", "Lorg/jetbrains/kotlin/backend/common/serialization/proto/IrGetValue;", "deserializeInstanceInitializerCall", "Lorg/jetbrains/kotlin/ir/expressions/IrInstanceInitializerCall;", "Lorg/jetbrains/kotlin/backend/common/serialization/proto/IrInstanceInitializerCall;", "deserializeIrLocalDelegatedPropertyReference", "Lorg/jetbrains/kotlin/ir/expressions/IrLocalDelegatedPropertyReference;", "Lorg/jetbrains/kotlin/backend/common/serialization/proto/IrLocalDelegatedPropertyReference;", "deserializeIrStatementOrigin", "Lorg/jetbrains/kotlin/ir/expressions/IrStatementOrigin;", "hasOriginName", "protoName", "Lkotlin/Function0;", "deserializeLoop", "Lorg/jetbrains/kotlin/backend/common/serialization/proto/Loop;", "loop", "deserializeLoopHeader", "loopIndex", "loopBuilder", "deserializeMemberAccessCommon", "", "access", "Lorg/jetbrains/kotlin/ir/expressions/IrMemberAccessExpression;", "Lorg/jetbrains/kotlin/backend/common/serialization/proto/MemberAccessCommon;", "deserializeOperation", "Lorg/jetbrains/kotlin/backend/common/serialization/proto/IrOperation;", "deserializePropertyReference", "Lorg/jetbrains/kotlin/ir/expressions/IrPropertyReference;", "Lorg/jetbrains/kotlin/backend/common/serialization/proto/IrPropertyReference;", "deserializeReturn", "Lorg/jetbrains/kotlin/ir/expressions/IrReturn;", "Lorg/jetbrains/kotlin/backend/common/serialization/proto/IrReturn;", "deserializeSetField", "Lorg/jetbrains/kotlin/ir/expressions/IrSetField;", "Lorg/jetbrains/kotlin/backend/common/serialization/proto/IrSetField;", "deserializeSetValue", "Lorg/jetbrains/kotlin/ir/expressions/IrSetValue;", "Lorg/jetbrains/kotlin/backend/common/serialization/proto/IrSetValue;", "deserializeSpreadElement", "Lorg/jetbrains/kotlin/ir/expressions/IrSpreadElement;", "Lorg/jetbrains/kotlin/backend/common/serialization/proto/IrSpreadElement;", "deserializeStatement", "Lorg/jetbrains/kotlin/ir/IrElement;", "Lorg/jetbrains/kotlin/backend/common/serialization/proto/IrStatement;", "deserializeStatement$ir_serialization_common", "deserializeStringConcat", "Lorg/jetbrains/kotlin/ir/expressions/IrStringConcatenation;", "Lorg/jetbrains/kotlin/backend/common/serialization/proto/IrStringConcat;", "deserializeSyntheticBody", "Lorg/jetbrains/kotlin/ir/expressions/IrSyntheticBody;", "Lorg/jetbrains/kotlin/backend/common/serialization/proto/IrSyntheticBody;", "deserializeThrow", "Lorg/jetbrains/kotlin/ir/expressions/impl/IrThrowImpl;", "Lorg/jetbrains/kotlin/backend/common/serialization/proto/IrThrow;", "deserializeTry", "Lorg/jetbrains/kotlin/ir/expressions/impl/IrTryImpl;", "Lorg/jetbrains/kotlin/backend/common/serialization/proto/IrTry;", "deserializeTypeOp", "Lorg/jetbrains/kotlin/ir/expressions/IrTypeOperatorCall;", "Lorg/jetbrains/kotlin/backend/common/serialization/proto/IrTypeOp;", "deserializeTypeOperator", "Lorg/jetbrains/kotlin/ir/expressions/IrTypeOperator;", "Lorg/jetbrains/kotlin/backend/common/serialization/proto/IrTypeOperator;", "deserializeTypedSymbol", ExifInterface.LATITUDE_SOUTH, "Lorg/jetbrains/kotlin/ir/symbols/IrSymbol;", ServerAuthenticationConstants.SIGNIN_GOOGLE_CODE_KEY, "", "fallbackSymbolKind", "Lorg/jetbrains/kotlin/backend/common/serialization/encodings/BinarySymbolData$SymbolKind;", "remap", "(JLorg/jetbrains/kotlin/backend/common/serialization/encodings/BinarySymbolData$SymbolKind;Z)Lorg/jetbrains/kotlin/ir/symbols/IrSymbol;", "deserializeTypedSymbolWhen", "condition", "(ZLorg/jetbrains/kotlin/backend/common/serialization/encodings/BinarySymbolData$SymbolKind;Lkotlin/jvm/functions/Function0;)Lorg/jetbrains/kotlin/ir/symbols/IrSymbol;", "deserializeVararg", "Lorg/jetbrains/kotlin/ir/expressions/IrVararg;", "Lorg/jetbrains/kotlin/backend/common/serialization/proto/IrVararg;", "deserializeVarargElement", "Lorg/jetbrains/kotlin/ir/expressions/IrVarargElement;", CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD, "Lorg/jetbrains/kotlin/backend/common/serialization/proto/IrVarargElement;", "deserializeWhen", "Lorg/jetbrains/kotlin/ir/expressions/IrWhen;", "Lorg/jetbrains/kotlin/backend/common/serialization/proto/IrWhen;", "deserializeWhile", "Lorg/jetbrains/kotlin/backend/common/serialization/proto/IrWhile;", "Companion", "IrAnnotationType", "ir.serialization.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class IrBodyDeserializer {
    private static final List<KClass<?>> allKnownStatementOrigins;
    private static final Map<String, IrStatementOriginImpl> statementOriginIndex;
    private final boolean allowErrorNodes;
    private final IrBuiltIns builtIns;
    private final IrDeclarationDeserializer declarationDeserializer;
    private final HashMap<Integer, IrLoop> fileLoops;
    private final IrFactory irFactory;
    private final IrLibraryFile libraryFile;

    /* JADX INFO: compiled from: IrBodyDeserializer.kt */
    @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
    public /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;
        public static final /* synthetic */ int[] $EnumSwitchMapping$1;
        public static final /* synthetic */ int[] $EnumSwitchMapping$2;
        public static final /* synthetic */ int[] $EnumSwitchMapping$3;
        public static final /* synthetic */ int[] $EnumSwitchMapping$4;
        public static final /* synthetic */ int[] $EnumSwitchMapping$5;
        public static final /* synthetic */ int[] $EnumSwitchMapping$6;

        static {
            int[] iArr = new int[IrSyntheticBodyKind.values().length];
            try {
                iArr[IrSyntheticBodyKind.ENUM_VALUES.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[IrSyntheticBodyKind.ENUM_VALUEOF.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                iArr[IrSyntheticBodyKind.ENUM_ENTRIES.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            $EnumSwitchMapping$0 = iArr;
            int[] iArr2 = new int[IrStatement.StatementCase.values().length];
            try {
                iArr2[IrStatement.StatementCase.BLOCK_BODY.ordinal()] = 1;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                iArr2[IrStatement.StatementCase.BRANCH.ordinal()] = 2;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                iArr2[IrStatement.StatementCase.CATCH.ordinal()] = 3;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                iArr2[IrStatement.StatementCase.DECLARATION.ordinal()] = 4;
            } catch (NoSuchFieldError unused7) {
            }
            try {
                iArr2[IrStatement.StatementCase.EXPRESSION.ordinal()] = 5;
            } catch (NoSuchFieldError unused8) {
            }
            try {
                iArr2[IrStatement.StatementCase.SYNTHETIC_BODY.ordinal()] = 6;
            } catch (NoSuchFieldError unused9) {
            }
            $EnumSwitchMapping$1 = iArr2;
            int[] iArr3 = new int[IrTypeOperator.values().length];
            try {
                iArr3[IrTypeOperator.CAST.ordinal()] = 1;
            } catch (NoSuchFieldError unused10) {
            }
            try {
                iArr3[IrTypeOperator.IMPLICIT_CAST.ordinal()] = 2;
            } catch (NoSuchFieldError unused11) {
            }
            try {
                iArr3[IrTypeOperator.IMPLICIT_NOTNULL.ordinal()] = 3;
            } catch (NoSuchFieldError unused12) {
            }
            try {
                iArr3[IrTypeOperator.IMPLICIT_COERCION_TO_UNIT.ordinal()] = 4;
            } catch (NoSuchFieldError unused13) {
            }
            try {
                iArr3[IrTypeOperator.IMPLICIT_INTEGER_COERCION.ordinal()] = 5;
            } catch (NoSuchFieldError unused14) {
            }
            try {
                iArr3[IrTypeOperator.SAFE_CAST.ordinal()] = 6;
            } catch (NoSuchFieldError unused15) {
            }
            try {
                iArr3[IrTypeOperator.INSTANCEOF.ordinal()] = 7;
            } catch (NoSuchFieldError unused16) {
            }
            try {
                iArr3[IrTypeOperator.NOT_INSTANCEOF.ordinal()] = 8;
            } catch (NoSuchFieldError unused17) {
            }
            try {
                iArr3[IrTypeOperator.SAM_CONVERSION.ordinal()] = 9;
            } catch (NoSuchFieldError unused18) {
            }
            try {
                iArr3[IrTypeOperator.IMPLICIT_DYNAMIC_CAST.ordinal()] = 10;
            } catch (NoSuchFieldError unused19) {
            }
            try {
                iArr3[IrTypeOperator.REINTERPRET_CAST.ordinal()] = 11;
            } catch (NoSuchFieldError unused20) {
            }
            $EnumSwitchMapping$2 = iArr3;
            int[] iArr4 = new int[IrVarargElement.VarargElementCase.values().length];
            try {
                iArr4[IrVarargElement.VarargElementCase.EXPRESSION.ordinal()] = 1;
            } catch (NoSuchFieldError unused21) {
            }
            try {
                iArr4[IrVarargElement.VarargElementCase.SPREAD_ELEMENT.ordinal()] = 2;
            } catch (NoSuchFieldError unused22) {
            }
            $EnumSwitchMapping$3 = iArr4;
            int[] iArr5 = new int[IrDynamicOperatorExpression.IrDynamicOperator.values().length];
            try {
                iArr5[IrDynamicOperatorExpression.IrDynamicOperator.UNARY_PLUS.ordinal()] = 1;
            } catch (NoSuchFieldError unused23) {
            }
            try {
                iArr5[IrDynamicOperatorExpression.IrDynamicOperator.UNARY_MINUS.ordinal()] = 2;
            } catch (NoSuchFieldError unused24) {
            }
            try {
                iArr5[IrDynamicOperatorExpression.IrDynamicOperator.EXCL.ordinal()] = 3;
            } catch (NoSuchFieldError unused25) {
            }
            try {
                iArr5[IrDynamicOperatorExpression.IrDynamicOperator.PREFIX_INCREMENT.ordinal()] = 4;
            } catch (NoSuchFieldError unused26) {
            }
            try {
                iArr5[IrDynamicOperatorExpression.IrDynamicOperator.PREFIX_DECREMENT.ordinal()] = 5;
            } catch (NoSuchFieldError unused27) {
            }
            try {
                iArr5[IrDynamicOperatorExpression.IrDynamicOperator.POSTFIX_INCREMENT.ordinal()] = 6;
            } catch (NoSuchFieldError unused28) {
            }
            try {
                iArr5[IrDynamicOperatorExpression.IrDynamicOperator.POSTFIX_DECREMENT.ordinal()] = 7;
            } catch (NoSuchFieldError unused29) {
            }
            try {
                iArr5[IrDynamicOperatorExpression.IrDynamicOperator.BINARY_PLUS.ordinal()] = 8;
            } catch (NoSuchFieldError unused30) {
            }
            try {
                iArr5[IrDynamicOperatorExpression.IrDynamicOperator.BINARY_MINUS.ordinal()] = 9;
            } catch (NoSuchFieldError unused31) {
            }
            try {
                iArr5[IrDynamicOperatorExpression.IrDynamicOperator.MUL.ordinal()] = 10;
            } catch (NoSuchFieldError unused32) {
            }
            try {
                iArr5[IrDynamicOperatorExpression.IrDynamicOperator.DIV.ordinal()] = 11;
            } catch (NoSuchFieldError unused33) {
            }
            try {
                iArr5[IrDynamicOperatorExpression.IrDynamicOperator.MOD.ordinal()] = 12;
            } catch (NoSuchFieldError unused34) {
            }
            try {
                iArr5[IrDynamicOperatorExpression.IrDynamicOperator.GT.ordinal()] = 13;
            } catch (NoSuchFieldError unused35) {
            }
            try {
                iArr5[IrDynamicOperatorExpression.IrDynamicOperator.LT.ordinal()] = 14;
            } catch (NoSuchFieldError unused36) {
            }
            try {
                iArr5[IrDynamicOperatorExpression.IrDynamicOperator.GE.ordinal()] = 15;
            } catch (NoSuchFieldError unused37) {
            }
            try {
                iArr5[IrDynamicOperatorExpression.IrDynamicOperator.LE.ordinal()] = 16;
            } catch (NoSuchFieldError unused38) {
            }
            try {
                iArr5[IrDynamicOperatorExpression.IrDynamicOperator.EQEQ.ordinal()] = 17;
            } catch (NoSuchFieldError unused39) {
            }
            try {
                iArr5[IrDynamicOperatorExpression.IrDynamicOperator.EXCLEQ.ordinal()] = 18;
            } catch (NoSuchFieldError unused40) {
            }
            try {
                iArr5[IrDynamicOperatorExpression.IrDynamicOperator.EQEQEQ.ordinal()] = 19;
            } catch (NoSuchFieldError unused41) {
            }
            try {
                iArr5[IrDynamicOperatorExpression.IrDynamicOperator.EXCLEQEQ.ordinal()] = 20;
            } catch (NoSuchFieldError unused42) {
            }
            try {
                iArr5[IrDynamicOperatorExpression.IrDynamicOperator.ANDAND.ordinal()] = 21;
            } catch (NoSuchFieldError unused43) {
            }
            try {
                iArr5[IrDynamicOperatorExpression.IrDynamicOperator.OROR.ordinal()] = 22;
            } catch (NoSuchFieldError unused44) {
            }
            try {
                iArr5[IrDynamicOperatorExpression.IrDynamicOperator.EQ.ordinal()] = 23;
            } catch (NoSuchFieldError unused45) {
            }
            try {
                iArr5[IrDynamicOperatorExpression.IrDynamicOperator.PLUSEQ.ordinal()] = 24;
            } catch (NoSuchFieldError unused46) {
            }
            try {
                iArr5[IrDynamicOperatorExpression.IrDynamicOperator.MINUSEQ.ordinal()] = 25;
            } catch (NoSuchFieldError unused47) {
            }
            try {
                iArr5[IrDynamicOperatorExpression.IrDynamicOperator.MULEQ.ordinal()] = 26;
            } catch (NoSuchFieldError unused48) {
            }
            try {
                iArr5[IrDynamicOperatorExpression.IrDynamicOperator.DIVEQ.ordinal()] = 27;
            } catch (NoSuchFieldError unused49) {
            }
            try {
                iArr5[IrDynamicOperatorExpression.IrDynamicOperator.MODEQ.ordinal()] = 28;
            } catch (NoSuchFieldError unused50) {
            }
            try {
                iArr5[IrDynamicOperatorExpression.IrDynamicOperator.ARRAY_ACCESS.ordinal()] = 29;
            } catch (NoSuchFieldError unused51) {
            }
            try {
                iArr5[IrDynamicOperatorExpression.IrDynamicOperator.INVOKE.ordinal()] = 30;
            } catch (NoSuchFieldError unused52) {
            }
            $EnumSwitchMapping$4 = iArr5;
            int[] iArr6 = new int[IrConst.ValueCase.values().length];
            try {
                iArr6[IrConst.ValueCase.NULL.ordinal()] = 1;
            } catch (NoSuchFieldError unused53) {
            }
            try {
                iArr6[IrConst.ValueCase.BOOLEAN.ordinal()] = 2;
            } catch (NoSuchFieldError unused54) {
            }
            try {
                iArr6[IrConst.ValueCase.BYTE.ordinal()] = 3;
            } catch (NoSuchFieldError unused55) {
            }
            try {
                iArr6[IrConst.ValueCase.CHAR.ordinal()] = 4;
            } catch (NoSuchFieldError unused56) {
            }
            try {
                iArr6[IrConst.ValueCase.SHORT.ordinal()] = 5;
            } catch (NoSuchFieldError unused57) {
            }
            try {
                iArr6[IrConst.ValueCase.INT.ordinal()] = 6;
            } catch (NoSuchFieldError unused58) {
            }
            try {
                iArr6[IrConst.ValueCase.LONG.ordinal()] = 7;
            } catch (NoSuchFieldError unused59) {
            }
            try {
                iArr6[IrConst.ValueCase.STRING.ordinal()] = 8;
            } catch (NoSuchFieldError unused60) {
            }
            try {
                iArr6[IrConst.ValueCase.FLOAT_BITS.ordinal()] = 9;
            } catch (NoSuchFieldError unused61) {
            }
            try {
                iArr6[IrConst.ValueCase.DOUBLE_BITS.ordinal()] = 10;
            } catch (NoSuchFieldError unused62) {
            }
            try {
                iArr6[IrConst.ValueCase.VALUE_NOT_SET.ordinal()] = 11;
            } catch (NoSuchFieldError unused63) {
            }
            $EnumSwitchMapping$5 = iArr6;
            int[] iArr7 = new int[IrOperation.OperationCase.values().length];
            try {
                iArr7[IrOperation.OperationCase.BLOCK.ordinal()] = 1;
            } catch (NoSuchFieldError unused64) {
            }
            try {
                iArr7[IrOperation.OperationCase.BREAK.ordinal()] = 2;
            } catch (NoSuchFieldError unused65) {
            }
            try {
                iArr7[IrOperation.OperationCase.CLASS_REFERENCE.ordinal()] = 3;
            } catch (NoSuchFieldError unused66) {
            }
            try {
                iArr7[IrOperation.OperationCase.CALL.ordinal()] = 4;
            } catch (NoSuchFieldError unused67) {
            }
            try {
                iArr7[IrOperation.OperationCase.COMPOSITE.ordinal()] = 5;
            } catch (NoSuchFieldError unused68) {
            }
            try {
                iArr7[IrOperation.OperationCase.CONST.ordinal()] = 6;
            } catch (NoSuchFieldError unused69) {
            }
            try {
                iArr7[IrOperation.OperationCase.CONTINUE.ordinal()] = 7;
            } catch (NoSuchFieldError unused70) {
            }
            try {
                iArr7[IrOperation.OperationCase.DELEGATING_CONSTRUCTOR_CALL.ordinal()] = 8;
            } catch (NoSuchFieldError unused71) {
            }
            try {
                iArr7[IrOperation.OperationCase.DO_WHILE.ordinal()] = 9;
            } catch (NoSuchFieldError unused72) {
            }
            try {
                iArr7[IrOperation.OperationCase.ENUM_CONSTRUCTOR_CALL.ordinal()] = 10;
            } catch (NoSuchFieldError unused73) {
            }
            try {
                iArr7[IrOperation.OperationCase.FUNCTION_REFERENCE.ordinal()] = 11;
            } catch (NoSuchFieldError unused74) {
            }
            try {
                iArr7[IrOperation.OperationCase.GET_ENUM_VALUE.ordinal()] = 12;
            } catch (NoSuchFieldError unused75) {
            }
            try {
                iArr7[IrOperation.OperationCase.GET_CLASS.ordinal()] = 13;
            } catch (NoSuchFieldError unused76) {
            }
            try {
                iArr7[IrOperation.OperationCase.GET_FIELD.ordinal()] = 14;
            } catch (NoSuchFieldError unused77) {
            }
            try {
                iArr7[IrOperation.OperationCase.GET_OBJECT.ordinal()] = 15;
            } catch (NoSuchFieldError unused78) {
            }
            try {
                iArr7[IrOperation.OperationCase.GET_VALUE.ordinal()] = 16;
            } catch (NoSuchFieldError unused79) {
            }
            try {
                iArr7[IrOperation.OperationCase.LOCAL_DELEGATED_PROPERTY_REFERENCE.ordinal()] = 17;
            } catch (NoSuchFieldError unused80) {
            }
            try {
                iArr7[IrOperation.OperationCase.INSTANCE_INITIALIZER_CALL.ordinal()] = 18;
            } catch (NoSuchFieldError unused81) {
            }
            try {
                iArr7[IrOperation.OperationCase.PROPERTY_REFERENCE.ordinal()] = 19;
            } catch (NoSuchFieldError unused82) {
            }
            try {
                iArr7[IrOperation.OperationCase.RETURN.ordinal()] = 20;
            } catch (NoSuchFieldError unused83) {
            }
            try {
                iArr7[IrOperation.OperationCase.SET_FIELD.ordinal()] = 21;
            } catch (NoSuchFieldError unused84) {
            }
            try {
                iArr7[IrOperation.OperationCase.SET_VALUE.ordinal()] = 22;
            } catch (NoSuchFieldError unused85) {
            }
            try {
                iArr7[IrOperation.OperationCase.STRING_CONCAT.ordinal()] = 23;
            } catch (NoSuchFieldError unused86) {
            }
            try {
                iArr7[IrOperation.OperationCase.THROW.ordinal()] = 24;
            } catch (NoSuchFieldError unused87) {
            }
            try {
                iArr7[IrOperation.OperationCase.TRY.ordinal()] = 25;
            } catch (NoSuchFieldError unused88) {
            }
            try {
                iArr7[IrOperation.OperationCase.TYPE_OP.ordinal()] = 26;
            } catch (NoSuchFieldError unused89) {
            }
            try {
                iArr7[IrOperation.OperationCase.VARARG.ordinal()] = 27;
            } catch (NoSuchFieldError unused90) {
            }
            try {
                iArr7[IrOperation.OperationCase.WHEN.ordinal()] = 28;
            } catch (NoSuchFieldError unused91) {
            }
            try {
                iArr7[IrOperation.OperationCase.WHILE.ordinal()] = 29;
            } catch (NoSuchFieldError unused92) {
            }
            try {
                iArr7[IrOperation.OperationCase.DYNAMIC_MEMBER.ordinal()] = 30;
            } catch (NoSuchFieldError unused93) {
            }
            try {
                iArr7[IrOperation.OperationCase.DYNAMIC_OPERATOR.ordinal()] = 31;
            } catch (NoSuchFieldError unused94) {
            }
            try {
                iArr7[IrOperation.OperationCase.CONSTRUCTOR_CALL.ordinal()] = 32;
            } catch (NoSuchFieldError unused95) {
            }
            try {
                iArr7[IrOperation.OperationCase.FUNCTION_EXPRESSION.ordinal()] = 33;
            } catch (NoSuchFieldError unused96) {
            }
            try {
                iArr7[IrOperation.OperationCase.ERROR_EXPRESSION.ordinal()] = 34;
            } catch (NoSuchFieldError unused97) {
            }
            try {
                iArr7[IrOperation.OperationCase.ERROR_CALL_EXPRESSION.ordinal()] = 35;
            } catch (NoSuchFieldError unused98) {
            }
            try {
                iArr7[IrOperation.OperationCase.OPERATION_NOT_SET.ordinal()] = 36;
            } catch (NoSuchFieldError unused99) {
            }
            $EnumSwitchMapping$6 = iArr7;
        }
    }

    public IrBodyDeserializer(IrBuiltIns builtIns, boolean z, IrFactory irFactory, IrLibraryFile libraryFile, IrDeclarationDeserializer declarationDeserializer) {
        Intrinsics.checkNotNullParameter(builtIns, "builtIns");
        Intrinsics.checkNotNullParameter(irFactory, "irFactory");
        Intrinsics.checkNotNullParameter(libraryFile, "libraryFile");
        Intrinsics.checkNotNullParameter(declarationDeserializer, "declarationDeserializer");
        this.builtIns = builtIns;
        this.allowErrorNodes = z;
        this.irFactory = irFactory;
        this.libraryFile = libraryFile;
        this.declarationDeserializer = declarationDeserializer;
        this.fileLoops = new HashMap<>();
    }

    private final IrLoop deserializeLoopHeader(int loopIndex, Function0<? extends IrLoop> loopBuilder) {
        HashMap<Integer, IrLoop> map = this.fileLoops;
        Integer numValueOf = Integer.valueOf(loopIndex);
        IrLoop irLoopInvoke = map.get(numValueOf);
        if (irLoopInvoke == null) {
            irLoopInvoke = loopBuilder.invoke();
            map.put(numValueOf, irLoopInvoke);
        }
        return irLoopInvoke;
    }

    private final IrBlockBody deserializeBlockBody(org.jetbrains.kotlin.backend.common.serialization.proto.IrBlockBody proto, int start, int end) {
        List<IrStatement> statementList = proto.getStatementList();
        Intrinsics.checkNotNullExpressionValue(statementList, "getStatementList(...)");
        List<IrStatement> list = statementList;
        ArrayList arrayList = new ArrayList(list.size());
        for (IrStatement irStatement : list) {
            Intrinsics.checkNotNull(irStatement);
            IrElement irElementDeserializeStatement$ir_serialization_common = deserializeStatement$ir_serialization_common(irStatement);
            Intrinsics.checkNotNull(irElementDeserializeStatement$ir_serialization_common, "null cannot be cast to non-null type org.jetbrains.kotlin.ir.IrStatement");
            arrayList.add((org.jetbrains.kotlin.ir.IrStatement) irElementDeserializeStatement$ir_serialization_common);
        }
        return IrFactoryHelpersKt.createBlockBody(this.irFactory, start, end, (List<? extends org.jetbrains.kotlin.ir.IrStatement>) CollectionsKt.compactIfPossible(arrayList));
    }

    private final IrBranch deserializeBranch(org.jetbrains.kotlin.backend.common.serialization.proto.IrBranch proto, int start, int end) {
        IrExpression condition = proto.getCondition();
        Intrinsics.checkNotNullExpressionValue(condition, "getCondition(...)");
        org.jetbrains.kotlin.ir.expressions.IrExpression irExpressionDeserializeExpression = deserializeExpression(condition);
        IrExpression result = proto.getResult();
        Intrinsics.checkNotNullExpressionValue(result, "getResult(...)");
        return new IrBranchImpl(start, end, irExpressionDeserializeExpression, deserializeExpression(result));
    }

    private final IrCatch deserializeCatch(org.jetbrains.kotlin.backend.common.serialization.proto.IrCatch proto, int start, int end) {
        IrDeclarationDeserializer irDeclarationDeserializer = this.declarationDeserializer;
        IrVariable catchParameter = proto.getCatchParameter();
        Intrinsics.checkNotNullExpressionValue(catchParameter, "getCatchParameter(...)");
        org.jetbrains.kotlin.ir.declarations.IrVariable irVariableDeserializeIrVariable$default = IrDeclarationDeserializer.deserializeIrVariable$default(irDeclarationDeserializer, catchParameter, false, 2, null);
        IrExpression result = proto.getResult();
        Intrinsics.checkNotNullExpressionValue(result, "getResult(...)");
        return new IrCatchImpl(start, end, irVariableDeserializeIrVariable$default, deserializeExpression(result));
    }

    private final IrSyntheticBody deserializeSyntheticBody(org.jetbrains.kotlin.backend.common.serialization.proto.IrSyntheticBody proto, int start, int end) {
        org.jetbrains.kotlin.ir.expressions.IrSyntheticBodyKind irSyntheticBodyKind;
        IrSyntheticBodyKind kind = proto.getKind();
        Intrinsics.checkNotNull(kind);
        int i = WhenMappings.$EnumSwitchMapping$0[kind.ordinal()];
        if (i == 1) {
            irSyntheticBodyKind = org.jetbrains.kotlin.ir.expressions.IrSyntheticBodyKind.ENUM_VALUES;
        } else if (i == 2) {
            irSyntheticBodyKind = org.jetbrains.kotlin.ir.expressions.IrSyntheticBodyKind.ENUM_VALUEOF;
        } else {
            if (i != 3) {
                throw new NoWhenBranchMatchedException();
            }
            irSyntheticBodyKind = org.jetbrains.kotlin.ir.expressions.IrSyntheticBodyKind.ENUM_ENTRIES;
        }
        return new IrSyntheticBodyImpl(start, end, irSyntheticBodyKind);
    }

    public final IrElement deserializeStatement$ir_serialization_common(IrStatement proto) {
        Intrinsics.checkNotNullParameter(proto, "proto");
        long jM6180decodeViYoqTc = BinaryCoordinates.INSTANCE.m6180decodeViYoqTc(proto.getCoordinates());
        int iM6176getStartOffsetimpl = BinaryCoordinates.m6176getStartOffsetimpl(jM6180decodeViYoqTc);
        int iM6175getEndOffsetimpl = BinaryCoordinates.m6175getEndOffsetimpl(jM6180decodeViYoqTc);
        IrStatement.StatementCase statementCase = proto.getStatementCase();
        switch (statementCase == null ? -1 : WhenMappings.$EnumSwitchMapping$1[statementCase.ordinal()]) {
            case 1:
                org.jetbrains.kotlin.backend.common.serialization.proto.IrBlockBody blockBody = proto.getBlockBody();
                Intrinsics.checkNotNullExpressionValue(blockBody, "getBlockBody(...)");
                return deserializeBlockBody(blockBody, iM6176getStartOffsetimpl, iM6175getEndOffsetimpl);
            case 2:
                org.jetbrains.kotlin.backend.common.serialization.proto.IrBranch branch = proto.getBranch();
                Intrinsics.checkNotNullExpressionValue(branch, "getBranch(...)");
                return deserializeBranch(branch, iM6176getStartOffsetimpl, iM6175getEndOffsetimpl);
            case 3:
                org.jetbrains.kotlin.backend.common.serialization.proto.IrCatch irCatch = proto.getCatch();
                Intrinsics.checkNotNullExpressionValue(irCatch, "getCatch(...)");
                return deserializeCatch(irCatch, iM6176getStartOffsetimpl, iM6175getEndOffsetimpl);
            case 4:
                IrDeclarationDeserializer irDeclarationDeserializer = this.declarationDeserializer;
                IrDeclaration declaration = proto.getDeclaration();
                Intrinsics.checkNotNullExpressionValue(declaration, "getDeclaration(...)");
                return IrDeclarationDeserializer.deserializeDeclaration$default(irDeclarationDeserializer, declaration, false, 2, null);
            case 5:
                IrExpression expression = proto.getExpression();
                Intrinsics.checkNotNullExpressionValue(expression, "getExpression(...)");
                return deserializeExpression(expression);
            case 6:
                org.jetbrains.kotlin.backend.common.serialization.proto.IrSyntheticBody syntheticBody = proto.getSyntheticBody();
                Intrinsics.checkNotNullExpressionValue(syntheticBody, "getSyntheticBody(...)");
                return deserializeSyntheticBody(syntheticBody, iM6176getStartOffsetimpl, iM6175getEndOffsetimpl);
            default:
                throw new NotImplementedError("An operation is not implemented: " + ("Statement deserialization not implemented: " + proto.getStatementCase()));
        }
    }

    private final IrBlock deserializeBlock(org.jetbrains.kotlin.backend.common.serialization.proto.IrBlock proto, int start, int end, IrType type) {
        ArrayList arrayList = new ArrayList();
        List<IrStatement> statementList = proto.getStatementList();
        IrStatementOrigin irStatementOriginDeserializeIrStatementOrigin = proto.hasOriginName() ? deserializeIrStatementOrigin(proto.getOriginName()) : null;
        Intrinsics.checkNotNull(statementList);
        for (IrStatement irStatement : statementList) {
            Intrinsics.checkNotNull(irStatement);
            IrElement irElementDeserializeStatement$ir_serialization_common = deserializeStatement$ir_serialization_common(irStatement);
            Intrinsics.checkNotNull(irElementDeserializeStatement$ir_serialization_common, "null cannot be cast to non-null type org.jetbrains.kotlin.ir.IrStatement");
            arrayList.add((org.jetbrains.kotlin.ir.IrStatement) irElementDeserializeStatement$ir_serialization_common);
        }
        return new IrBlockImpl(start, end, type, irStatementOriginDeserializeIrStatementOrigin, arrayList);
    }

    private final void deserializeMemberAccessCommon(IrMemberAccessExpression<?> access, MemberAccessCommon proto) {
        List<NullableIrExpression> valueArgumentList = proto.getValueArgumentList();
        Intrinsics.checkNotNullExpressionValue(valueArgumentList, "getValueArgumentList(...)");
        int i = 0;
        int i2 = 0;
        for (Object obj : valueArgumentList) {
            int i3 = i2 + 1;
            if (i2 < 0) {
                kotlin.collections.CollectionsKt.throwIndexOverflow();
            }
            NullableIrExpression nullableIrExpression = (NullableIrExpression) obj;
            if (nullableIrExpression.hasExpression()) {
                IrExpression expression = nullableIrExpression.getExpression();
                Intrinsics.checkNotNullExpressionValue(expression, "getExpression(...)");
                access.putValueArgument(i2, deserializeExpression(expression));
            }
            i2 = i3;
        }
        List<Integer> typeArgumentList = proto.getTypeArgumentList();
        Intrinsics.checkNotNullExpressionValue(typeArgumentList, "getTypeArgumentList(...)");
        for (Object obj2 : typeArgumentList) {
            int i4 = i + 1;
            if (i < 0) {
                kotlin.collections.CollectionsKt.throwIndexOverflow();
            }
            Integer num = (Integer) obj2;
            IrDeclarationDeserializer irDeclarationDeserializer = this.declarationDeserializer;
            Intrinsics.checkNotNull(num);
            access.putTypeArgument(i, irDeclarationDeserializer.deserializeNullableIrType(num.intValue()));
            i = i4;
        }
        if (proto.hasDispatchReceiver()) {
            IrExpression dispatchReceiver = proto.getDispatchReceiver();
            Intrinsics.checkNotNullExpressionValue(dispatchReceiver, "getDispatchReceiver(...)");
            access.setDispatchReceiver(deserializeExpression(dispatchReceiver));
        }
        if (proto.hasExtensionReceiver()) {
            IrExpression extensionReceiver = proto.getExtensionReceiver();
            Intrinsics.checkNotNullExpressionValue(extensionReceiver, "getExtensionReceiver(...)");
            access.setExtensionReceiver(deserializeExpression(extensionReceiver));
        }
    }

    private final IrClassReference deserializeClassReference(org.jetbrains.kotlin.backend.common.serialization.proto.IrClassReference proto, int start, int end, IrType type) throws IrSymbolTypeMismatchException {
        long classSymbol = proto.getClassSymbol();
        BinarySymbolData.SymbolKind symbolKind = BinarySymbolData.SymbolKind.CLASS_SYMBOL;
        IrDeclarationDeserializer irDeclarationDeserializer = this.declarationDeserializer;
        IrClassifierSymbol irClassifierSymbolDeserializeIrSymbolAndRemap$ir_serialization_common = irDeclarationDeserializer.deserializeIrSymbolAndRemap$ir_serialization_common(classSymbol);
        if (!(irClassifierSymbolDeserializeIrSymbolAndRemap$ir_serialization_common instanceof IrClassifierSymbol)) {
            if (!irDeclarationDeserializer.partialLinkageEnabled) {
                throw new IrSymbolTypeMismatchException(IrClassifierSymbol.class, irClassifierSymbolDeserializeIrSymbolAndRemap$ir_serialization_common);
            }
            ReferenceSymbolTable symbolTable = irDeclarationDeserializer.getSymbolDeserializer().getSymbolTable();
            if (symbolKind == null) {
                throw new IllegalStateException(("No fallback symbol kind specified for symbol " + irClassifierSymbolDeserializeIrSymbolAndRemap$ir_serialization_common).toString());
            }
            IdSignature signature = irClassifierSymbolDeserializeIrSymbolAndRemap$ir_serialization_common.getSignature();
            if (signature != null) {
                if (!signature.isPubliclyVisible()) {
                    signature = null;
                }
                if (signature != null) {
                    IrSymbol irSymbolReferenceDeserializedSymbol = IrSymbolDeserializerKt.referenceDeserializedSymbol(symbolTable, null, symbolKind, signature);
                    if (irSymbolReferenceDeserializedSymbol == null) {
                        throw new NullPointerException("null cannot be cast to non-null type org.jetbrains.kotlin.ir.symbols.IrClassifierSymbol");
                    }
                    irClassifierSymbolDeserializeIrSymbolAndRemap$ir_serialization_common = (IrClassifierSymbol) irSymbolReferenceDeserializedSymbol;
                }
            }
            throw new IllegalStateException(("No public signature for symbol " + irClassifierSymbolDeserializeIrSymbolAndRemap$ir_serialization_common).toString());
        }
        return new IrClassReferenceImpl(start, end, type, (IrClassifierSymbol) irClassifierSymbolDeserializeIrSymbolAndRemap$ir_serialization_common, this.declarationDeserializer.deserializeIrType(proto.getClassType()));
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: compiled from: IrBodyDeserializer.kt */
    @Metadata(d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0006\b\u0002\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\b\u0010\u0011\u001a\u00020\u0006H\u0002R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u001b\u0010\u0005\u001a\u00020\u00068TX\u0094\u0084\u0002¢\u0006\f\n\u0004\b\t\u0010\n\u001a\u0004\b\u0007\u0010\bR\u001c\u0010\u000b\u001a\u0004\u0018\u00010\fX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\r\u0010\u000e\"\u0004\b\u000f\u0010\u0010¨\u0006\u0012"}, d2 = {"Lorg/jetbrains/kotlin/backend/common/serialization/IrBodyDeserializer$IrAnnotationType;", "Lorg/jetbrains/kotlin/ir/types/impl/IrDelegatedSimpleType;", "builtIns", "Lorg/jetbrains/kotlin/ir/IrBuiltIns;", "(Lorg/jetbrains/kotlin/ir/IrBuiltIns;)V", MethodDelegation.ImplementationDelegate.FIELD_NAME_PREFIX, "Lorg/jetbrains/kotlin/ir/types/IrSimpleType;", "getDelegate", "()Lorg/jetbrains/kotlin/ir/types/IrSimpleType;", "delegate$delegate", "Lkotlin/Lazy;", "irConstructorCall", "Lorg/jetbrains/kotlin/ir/expressions/IrConstructorCall;", "getIrConstructorCall", "()Lorg/jetbrains/kotlin/ir/expressions/IrConstructorCall;", "setIrConstructorCall", "(Lorg/jetbrains/kotlin/ir/expressions/IrConstructorCall;)V", "resolveType", "ir.serialization.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
    static final class IrAnnotationType extends IrDelegatedSimpleType {
        private final IrBuiltIns builtIns;

        /* JADX INFO: renamed from: delegate$delegate, reason: from kotlin metadata */
        private final Lazy delegate;
        private IrConstructorCall irConstructorCall;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public IrAnnotationType(IrBuiltIns builtIns) {
            super(null, 1, null);
            Intrinsics.checkNotNullParameter(builtIns, "builtIns");
            this.builtIns = builtIns;
            this.delegate = LazyKt.lazy(new Function0<IrSimpleType>() { // from class: org.jetbrains.kotlin.backend.common.serialization.IrBodyDeserializer$IrAnnotationType$delegate$2
                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.Function0
                public final IrSimpleType invoke() {
                    return this.this$0.resolveType();
                }
            });
        }

        public final IrConstructorCall getIrConstructorCall() {
            return this.irConstructorCall;
        }

        public final void setIrConstructorCall(IrConstructorCall irConstructorCall) {
            this.irConstructorCall = irConstructorCall;
        }

        @Override // org.jetbrains.kotlin.ir.types.impl.IrDelegatedSimpleType
        protected IrSimpleType getDelegate() {
            return (IrSimpleType) this.delegate.getValue();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final IrSimpleType resolveType() {
            IrConstructorCall irConstructorCall = this.irConstructorCall;
            if (irConstructorCall == null) {
                throw new IllegalStateException("irConstructorCall should not be null at this stage".toString());
            }
            this.irConstructorCall = null;
            IrClass parentAsClass = IrUtilsKt.getParentAsClass(irConstructorCall.getSymbol().getOwner());
            List<IrTypeParameter> listExtractTypeParameters = IrTypeSystemContextKt.extractTypeParameters(parentAsClass);
            if (!listExtractTypeParameters.isEmpty()) {
                List<IrTypeParameter> list = listExtractTypeParameters;
                ArrayList arrayList = new ArrayList(list.size());
                ArrayList arrayList2 = new ArrayList(list.size());
                IrSimpleTypeBuilder irSimpleTypeBuilder = new IrSimpleTypeBuilder();
                List<IrTypeParameter> list2 = list;
                ArrayList arrayList3 = new ArrayList(list2.size());
                Iterator<T> it = list2.iterator();
                while (it.hasNext()) {
                    irSimpleTypeBuilder.setClassifier(((IrTypeParameter) it.next()).getSymbol());
                    arrayList3.add(IrSimpleTypeImplKt.buildTypeProjection(irSimpleTypeBuilder));
                }
                irSimpleTypeBuilder.setArguments(CollectionsKt.compactIfPossible(arrayList3));
                irSimpleTypeBuilder.setClassifier(parentAsClass.getSymbol());
                IrSimpleTypeImpl irSimpleTypeImplBuildSimpleType = IrSimpleTypeImplKt.buildSimpleType(irSimpleTypeBuilder);
                int size = list.size();
                for (int i = 0; i < size; i++) {
                    IrTypeParameter irTypeParameter = list.get(i);
                    IrType typeArgument = irConstructorCall.getTypeArgument(i);
                    if (typeArgument == null) {
                        throw new IllegalStateException(("No type argument for id " + i).toString());
                    }
                    arrayList.add(IrSimpleTypeImplKt.makeTypeProjection(typeArgument, irTypeParameter.getVariance()));
                    arrayList2.add(irTypeParameter.getSymbol());
                }
                IrType irTypeSubstitute = new IrTypeSubstitutor(arrayList2, arrayList, this.builtIns, false, 8, null).substitute(irSimpleTypeImplBuildSimpleType);
                Intrinsics.checkNotNull(irTypeSubstitute, "null cannot be cast to non-null type org.jetbrains.kotlin.ir.types.IrSimpleType");
                return (IrSimpleType) irTypeSubstitute;
            }
            IrSimpleTypeBuilder irSimpleTypeBuilder2 = new IrSimpleTypeBuilder();
            irSimpleTypeBuilder2.setClassifier(parentAsClass.getSymbol());
            return IrSimpleTypeImplKt.buildSimpleType(irSimpleTypeBuilder2);
        }
    }

    public final IrConstructorCall deserializeAnnotation(org.jetbrains.kotlin.backend.common.serialization.proto.IrConstructorCall proto) throws IrSymbolTypeMismatchException {
        Intrinsics.checkNotNullParameter(proto, "proto");
        IrAnnotationType irAnnotationType = new IrAnnotationType(this.builtIns);
        IrConstructorCall irConstructorCallDeserializeConstructorCall = deserializeConstructorCall(proto, 0, 0, irAnnotationType);
        irAnnotationType.setIrConstructorCall(irConstructorCallDeserializeConstructorCall);
        return irConstructorCallDeserializeConstructorCall;
    }

    private final IrConstructorCall deserializeConstructorCall(org.jetbrains.kotlin.backend.common.serialization.proto.IrConstructorCall proto, int start, int end, IrType type) throws IrSymbolTypeMismatchException {
        long symbol = proto.getSymbol();
        BinarySymbolData.SymbolKind symbolKind = BinarySymbolData.SymbolKind.CONSTRUCTOR_SYMBOL;
        IrDeclarationDeserializer irDeclarationDeserializer = this.declarationDeserializer;
        IrConstructorSymbol irConstructorSymbolDeserializeIrSymbolAndRemap$ir_serialization_common = irDeclarationDeserializer.deserializeIrSymbolAndRemap$ir_serialization_common(symbol);
        if (!(irConstructorSymbolDeserializeIrSymbolAndRemap$ir_serialization_common instanceof IrConstructorSymbol)) {
            if (!irDeclarationDeserializer.partialLinkageEnabled) {
                throw new IrSymbolTypeMismatchException(IrConstructorSymbol.class, irConstructorSymbolDeserializeIrSymbolAndRemap$ir_serialization_common);
            }
            ReferenceSymbolTable symbolTable = irDeclarationDeserializer.getSymbolDeserializer().getSymbolTable();
            if (symbolKind == null) {
                throw new IllegalStateException(("No fallback symbol kind specified for symbol " + irConstructorSymbolDeserializeIrSymbolAndRemap$ir_serialization_common).toString());
            }
            IdSignature signature = irConstructorSymbolDeserializeIrSymbolAndRemap$ir_serialization_common.getSignature();
            if (signature != null) {
                if (!signature.isPubliclyVisible()) {
                    signature = null;
                }
                if (signature != null) {
                    IrSymbol irSymbolReferenceDeserializedSymbol = IrSymbolDeserializerKt.referenceDeserializedSymbol(symbolTable, null, symbolKind, signature);
                    if (irSymbolReferenceDeserializedSymbol == null) {
                        throw new NullPointerException("null cannot be cast to non-null type org.jetbrains.kotlin.ir.symbols.IrConstructorSymbol");
                    }
                    irConstructorSymbolDeserializeIrSymbolAndRemap$ir_serialization_common = (IrConstructorSymbol) irSymbolReferenceDeserializedSymbol;
                }
            }
            throw new IllegalStateException(("No public signature for symbol " + irConstructorSymbolDeserializeIrSymbolAndRemap$ir_serialization_common).toString());
        }
        IrConstructorCallImpl irConstructorCallImpl = new IrConstructorCallImpl(start, end, type, (IrConstructorSymbol) irConstructorSymbolDeserializeIrSymbolAndRemap$ir_serialization_common, proto.getMemberAccess().getTypeArgumentCount(), proto.getConstructorTypeArgumentsCount(), proto.getMemberAccess().getValueArgumentCount(), proto.hasOriginName() ? deserializeIrStatementOrigin(proto.getOriginName()) : null, null, 256, null);
        MemberAccessCommon memberAccess = proto.getMemberAccess();
        Intrinsics.checkNotNullExpressionValue(memberAccess, "getMemberAccess(...)");
        deserializeMemberAccessCommon(irConstructorCallImpl, memberAccess);
        return irConstructorCallImpl;
    }

    private final IrCall deserializeCall(org.jetbrains.kotlin.backend.common.serialization.proto.IrCall proto, int start, int end, IrType type) throws IrSymbolTypeMismatchException {
        IrClassSymbol irClassSymbolDeserializeIrSymbolAndRemap$ir_serialization_common;
        long symbol = proto.getSymbol();
        BinarySymbolData.SymbolKind symbolKind = BinarySymbolData.SymbolKind.FUNCTION_SYMBOL;
        IrDeclarationDeserializer irDeclarationDeserializer = this.declarationDeserializer;
        IrSimpleFunctionSymbol irSimpleFunctionSymbolDeserializeIrSymbolAndRemap$ir_serialization_common = irDeclarationDeserializer.deserializeIrSymbolAndRemap$ir_serialization_common(symbol);
        if (!(irSimpleFunctionSymbolDeserializeIrSymbolAndRemap$ir_serialization_common instanceof IrSimpleFunctionSymbol)) {
            if (!irDeclarationDeserializer.partialLinkageEnabled) {
                throw new IrSymbolTypeMismatchException(IrSimpleFunctionSymbol.class, irSimpleFunctionSymbolDeserializeIrSymbolAndRemap$ir_serialization_common);
            }
            ReferenceSymbolTable symbolTable = irDeclarationDeserializer.getSymbolDeserializer().getSymbolTable();
            if (symbolKind == null) {
                throw new IllegalStateException(("No fallback symbol kind specified for symbol " + irSimpleFunctionSymbolDeserializeIrSymbolAndRemap$ir_serialization_common).toString());
            }
            IdSignature signature = irSimpleFunctionSymbolDeserializeIrSymbolAndRemap$ir_serialization_common.getSignature();
            if (signature != null) {
                if (!signature.isPubliclyVisible()) {
                    signature = null;
                }
                if (signature != null) {
                    IrSymbol irSymbolReferenceDeserializedSymbol = IrSymbolDeserializerKt.referenceDeserializedSymbol(symbolTable, null, symbolKind, signature);
                    if (irSymbolReferenceDeserializedSymbol == null) {
                        throw new NullPointerException("null cannot be cast to non-null type org.jetbrains.kotlin.ir.symbols.IrSimpleFunctionSymbol");
                    }
                    irSimpleFunctionSymbolDeserializeIrSymbolAndRemap$ir_serialization_common = (IrSimpleFunctionSymbol) irSymbolReferenceDeserializedSymbol;
                }
            }
            throw new IllegalStateException(("No public signature for symbol " + irSimpleFunctionSymbolDeserializeIrSymbolAndRemap$ir_serialization_common).toString());
        }
        IrSimpleFunctionSymbol irSimpleFunctionSymbol = (IrSimpleFunctionSymbol) irSimpleFunctionSymbolDeserializeIrSymbolAndRemap$ir_serialization_common;
        boolean zHasSuper = proto.hasSuper();
        BinarySymbolData.SymbolKind symbolKind2 = BinarySymbolData.SymbolKind.CLASS_SYMBOL;
        if (zHasSuper) {
            long j = proto.getSuper();
            IrDeclarationDeserializer irDeclarationDeserializer2 = this.declarationDeserializer;
            irClassSymbolDeserializeIrSymbolAndRemap$ir_serialization_common = irDeclarationDeserializer2.deserializeIrSymbolAndRemap$ir_serialization_common(j);
            if (!(irClassSymbolDeserializeIrSymbolAndRemap$ir_serialization_common instanceof IrClassSymbol)) {
                if (!irDeclarationDeserializer2.partialLinkageEnabled) {
                    throw new IrSymbolTypeMismatchException(IrClassSymbol.class, irClassSymbolDeserializeIrSymbolAndRemap$ir_serialization_common);
                }
                ReferenceSymbolTable symbolTable2 = irDeclarationDeserializer2.getSymbolDeserializer().getSymbolTable();
                if (symbolKind2 == null) {
                    throw new IllegalStateException(("No fallback symbol kind specified for symbol " + irClassSymbolDeserializeIrSymbolAndRemap$ir_serialization_common).toString());
                }
                IdSignature signature2 = irClassSymbolDeserializeIrSymbolAndRemap$ir_serialization_common.getSignature();
                if (signature2 != null) {
                    if (!signature2.isPubliclyVisible()) {
                        signature2 = null;
                    }
                    if (signature2 != null) {
                        IrSymbol irSymbolReferenceDeserializedSymbol2 = IrSymbolDeserializerKt.referenceDeserializedSymbol(symbolTable2, null, symbolKind2, signature2);
                        if (irSymbolReferenceDeserializedSymbol2 == null) {
                            throw new NullPointerException("null cannot be cast to non-null type org.jetbrains.kotlin.ir.symbols.IrClassSymbol");
                        }
                        irClassSymbolDeserializeIrSymbolAndRemap$ir_serialization_common = (IrClassSymbol) irSymbolReferenceDeserializedSymbol2;
                    }
                }
                throw new IllegalStateException(("No public signature for symbol " + irClassSymbolDeserializeIrSymbolAndRemap$ir_serialization_common).toString());
            }
        } else {
            irClassSymbolDeserializeIrSymbolAndRemap$ir_serialization_common = null;
        }
        IrCallImpl irCallImpl = new IrCallImpl(start, end, type, irSimpleFunctionSymbol, proto.getMemberAccess().getTypeArgumentCount(), proto.getMemberAccess().getValueArgumentList().size(), proto.hasOriginName() ? deserializeIrStatementOrigin(proto.getOriginName()) : null, (IrClassSymbol) irClassSymbolDeserializeIrSymbolAndRemap$ir_serialization_common);
        MemberAccessCommon memberAccess = proto.getMemberAccess();
        Intrinsics.checkNotNullExpressionValue(memberAccess, "getMemberAccess(...)");
        deserializeMemberAccessCommon(irCallImpl, memberAccess);
        return irCallImpl;
    }

    private final IrComposite deserializeComposite(org.jetbrains.kotlin.backend.common.serialization.proto.IrComposite proto, int start, int end, IrType type) {
        ArrayList arrayList = new ArrayList();
        List<IrStatement> statementList = proto.getStatementList();
        IrStatementOrigin irStatementOriginDeserializeIrStatementOrigin = proto.hasOriginName() ? deserializeIrStatementOrigin(proto.getOriginName()) : null;
        Intrinsics.checkNotNull(statementList);
        for (IrStatement irStatement : statementList) {
            Intrinsics.checkNotNull(irStatement);
            IrElement irElementDeserializeStatement$ir_serialization_common = deserializeStatement$ir_serialization_common(irStatement);
            Intrinsics.checkNotNull(irElementDeserializeStatement$ir_serialization_common, "null cannot be cast to non-null type org.jetbrains.kotlin.ir.IrStatement");
            arrayList.add((org.jetbrains.kotlin.ir.IrStatement) irElementDeserializeStatement$ir_serialization_common);
        }
        return new IrCompositeImpl(start, end, type, irStatementOriginDeserializeIrStatementOrigin, arrayList);
    }

    private final IrDelegatingConstructorCall deserializeDelegatingConstructorCall(org.jetbrains.kotlin.backend.common.serialization.proto.IrDelegatingConstructorCall proto, int start, int end) throws IrSymbolTypeMismatchException {
        long symbol = proto.getSymbol();
        BinarySymbolData.SymbolKind symbolKind = BinarySymbolData.SymbolKind.CONSTRUCTOR_SYMBOL;
        IrDeclarationDeserializer irDeclarationDeserializer = this.declarationDeserializer;
        IrConstructorSymbol irConstructorSymbolDeserializeIrSymbolAndRemap$ir_serialization_common = irDeclarationDeserializer.deserializeIrSymbolAndRemap$ir_serialization_common(symbol);
        if (!(irConstructorSymbolDeserializeIrSymbolAndRemap$ir_serialization_common instanceof IrConstructorSymbol)) {
            if (!irDeclarationDeserializer.partialLinkageEnabled) {
                throw new IrSymbolTypeMismatchException(IrConstructorSymbol.class, irConstructorSymbolDeserializeIrSymbolAndRemap$ir_serialization_common);
            }
            ReferenceSymbolTable symbolTable = irDeclarationDeserializer.getSymbolDeserializer().getSymbolTable();
            if (symbolKind == null) {
                throw new IllegalStateException(("No fallback symbol kind specified for symbol " + irConstructorSymbolDeserializeIrSymbolAndRemap$ir_serialization_common).toString());
            }
            IdSignature signature = irConstructorSymbolDeserializeIrSymbolAndRemap$ir_serialization_common.getSignature();
            if (signature != null) {
                if (!signature.isPubliclyVisible()) {
                    signature = null;
                }
                if (signature != null) {
                    IrSymbol irSymbolReferenceDeserializedSymbol = IrSymbolDeserializerKt.referenceDeserializedSymbol(symbolTable, null, symbolKind, signature);
                    if (irSymbolReferenceDeserializedSymbol == null) {
                        throw new NullPointerException("null cannot be cast to non-null type org.jetbrains.kotlin.ir.symbols.IrConstructorSymbol");
                    }
                    irConstructorSymbolDeserializeIrSymbolAndRemap$ir_serialization_common = (IrConstructorSymbol) irSymbolReferenceDeserializedSymbol;
                }
            }
            throw new IllegalStateException(("No public signature for symbol " + irConstructorSymbolDeserializeIrSymbolAndRemap$ir_serialization_common).toString());
        }
        IrDelegatingConstructorCallImpl irDelegatingConstructorCallImpl = new IrDelegatingConstructorCallImpl(start, end, this.builtIns.getUnitType(), (IrConstructorSymbol) irConstructorSymbolDeserializeIrSymbolAndRemap$ir_serialization_common, proto.getMemberAccess().getTypeArgumentCount(), proto.getMemberAccess().getValueArgumentCount());
        MemberAccessCommon memberAccess = proto.getMemberAccess();
        Intrinsics.checkNotNullExpressionValue(memberAccess, "getMemberAccess(...)");
        deserializeMemberAccessCommon(irDelegatingConstructorCallImpl, memberAccess);
        return irDelegatingConstructorCallImpl;
    }

    private final IrEnumConstructorCall deserializeEnumConstructorCall(org.jetbrains.kotlin.backend.common.serialization.proto.IrEnumConstructorCall proto, int start, int end) throws IrSymbolTypeMismatchException {
        long symbol = proto.getSymbol();
        BinarySymbolData.SymbolKind symbolKind = BinarySymbolData.SymbolKind.CONSTRUCTOR_SYMBOL;
        IrDeclarationDeserializer irDeclarationDeserializer = this.declarationDeserializer;
        IrConstructorSymbol irConstructorSymbolDeserializeIrSymbolAndRemap$ir_serialization_common = irDeclarationDeserializer.deserializeIrSymbolAndRemap$ir_serialization_common(symbol);
        if (!(irConstructorSymbolDeserializeIrSymbolAndRemap$ir_serialization_common instanceof IrConstructorSymbol)) {
            if (!irDeclarationDeserializer.partialLinkageEnabled) {
                throw new IrSymbolTypeMismatchException(IrConstructorSymbol.class, irConstructorSymbolDeserializeIrSymbolAndRemap$ir_serialization_common);
            }
            ReferenceSymbolTable symbolTable = irDeclarationDeserializer.getSymbolDeserializer().getSymbolTable();
            if (symbolKind == null) {
                throw new IllegalStateException(("No fallback symbol kind specified for symbol " + irConstructorSymbolDeserializeIrSymbolAndRemap$ir_serialization_common).toString());
            }
            IdSignature signature = irConstructorSymbolDeserializeIrSymbolAndRemap$ir_serialization_common.getSignature();
            if (signature != null) {
                if (!signature.isPubliclyVisible()) {
                    signature = null;
                }
                if (signature != null) {
                    IrSymbol irSymbolReferenceDeserializedSymbol = IrSymbolDeserializerKt.referenceDeserializedSymbol(symbolTable, null, symbolKind, signature);
                    if (irSymbolReferenceDeserializedSymbol == null) {
                        throw new NullPointerException("null cannot be cast to non-null type org.jetbrains.kotlin.ir.symbols.IrConstructorSymbol");
                    }
                    irConstructorSymbolDeserializeIrSymbolAndRemap$ir_serialization_common = (IrConstructorSymbol) irSymbolReferenceDeserializedSymbol;
                }
            }
            throw new IllegalStateException(("No public signature for symbol " + irConstructorSymbolDeserializeIrSymbolAndRemap$ir_serialization_common).toString());
        }
        IrEnumConstructorCallImpl irEnumConstructorCallImpl = new IrEnumConstructorCallImpl(start, end, this.builtIns.getUnitType(), (IrConstructorSymbol) irConstructorSymbolDeserializeIrSymbolAndRemap$ir_serialization_common, proto.getMemberAccess().getTypeArgumentCount(), proto.getMemberAccess().getValueArgumentCount());
        MemberAccessCommon memberAccess = proto.getMemberAccess();
        Intrinsics.checkNotNullExpressionValue(memberAccess, "getMemberAccess(...)");
        deserializeMemberAccessCommon(irEnumConstructorCallImpl, memberAccess);
        return irEnumConstructorCallImpl;
    }

    private final IrFunctionExpressionImpl deserializeFunctionExpression(IrFunctionExpression functionExpression, int start, int end, IrType type) {
        IrDeclarationDeserializer irDeclarationDeserializer = this.declarationDeserializer;
        IrFunction function = functionExpression.getFunction();
        Intrinsics.checkNotNullExpressionValue(function, "getFunction(...)");
        return new IrFunctionExpressionImpl(start, end, type, IrDeclarationDeserializer.deserializeIrFunction$ir_serialization_common$default(irDeclarationDeserializer, function, false, 2, null), deserializeIrStatementOrigin(functionExpression.getOriginName()));
    }

    private final IrErrorExpression deserializeErrorExpression(org.jetbrains.kotlin.backend.common.serialization.proto.IrErrorExpression proto, int start, int end, IrType type) {
        if (!this.allowErrorNodes) {
            throw new IllegalArgumentException(("IrErrorExpression(" + start + ", " + end + ", \"" + this.libraryFile.string(proto.getDescription()) + "\") found but error code is not allowed").toString());
        }
        return new IrErrorExpressionImpl(start, end, type, this.libraryFile.string(proto.getDescription()));
    }

    private final IrErrorCallExpression deserializeErrorCallExpression(org.jetbrains.kotlin.backend.common.serialization.proto.IrErrorCallExpression proto, int start, int end, IrType type) {
        if (!this.allowErrorNodes) {
            throw new IllegalArgumentException(("IrErrorCallExpressionImpl(" + start + ", " + end + ", \"" + this.libraryFile.string(proto.getDescription()) + "\") found but error code is not allowed").toString());
        }
        IrErrorCallExpressionImpl irErrorCallExpressionImpl = new IrErrorCallExpressionImpl(start, end, type, this.libraryFile.string(proto.getDescription()));
        if (proto.hasReceiver()) {
            IrExpression receiver = proto.getReceiver();
            Intrinsics.checkNotNullExpressionValue(receiver, "getReceiver(...)");
            irErrorCallExpressionImpl.setExplicitReceiver(deserializeExpression(receiver));
        }
        List<IrExpression> valueArgumentList = proto.getValueArgumentList();
        Intrinsics.checkNotNullExpressionValue(valueArgumentList, "getValueArgumentList(...)");
        for (IrExpression irExpression : valueArgumentList) {
            Intrinsics.checkNotNull(irExpression);
            irErrorCallExpressionImpl.addArgument(deserializeExpression(irExpression));
        }
        return irErrorCallExpressionImpl;
    }

    private final IrFunctionReference deserializeFunctionReference(org.jetbrains.kotlin.backend.common.serialization.proto.IrFunctionReference proto, int start, int end, IrType type) throws IrSymbolTypeMismatchException {
        long symbol = proto.getSymbol();
        BinarySymbolData.SymbolKind symbolKind = BinarySymbolData.SymbolKind.FUNCTION_SYMBOL;
        IrDeclarationDeserializer irDeclarationDeserializer = this.declarationDeserializer;
        IrFunctionSymbol irFunctionSymbolDeserializeIrSymbolAndRemap$ir_serialization_common = irDeclarationDeserializer.deserializeIrSymbolAndRemap$ir_serialization_common(symbol);
        IrSymbol irSymbol = null;
        if (!(irFunctionSymbolDeserializeIrSymbolAndRemap$ir_serialization_common instanceof IrFunctionSymbol)) {
            if (!irDeclarationDeserializer.partialLinkageEnabled) {
                throw new IrSymbolTypeMismatchException(IrFunctionSymbol.class, irFunctionSymbolDeserializeIrSymbolAndRemap$ir_serialization_common);
            }
            ReferenceSymbolTable symbolTable = irDeclarationDeserializer.getSymbolDeserializer().getSymbolTable();
            if (symbolKind == null) {
                throw new IllegalStateException(("No fallback symbol kind specified for symbol " + irFunctionSymbolDeserializeIrSymbolAndRemap$ir_serialization_common).toString());
            }
            IdSignature signature = irFunctionSymbolDeserializeIrSymbolAndRemap$ir_serialization_common.getSignature();
            if (signature != null) {
                if (!signature.isPubliclyVisible()) {
                    signature = null;
                }
                if (signature != null) {
                    IrSymbol irSymbolReferenceDeserializedSymbol = IrSymbolDeserializerKt.referenceDeserializedSymbol(symbolTable, null, symbolKind, signature);
                    if (irSymbolReferenceDeserializedSymbol == null) {
                        throw new NullPointerException("null cannot be cast to non-null type org.jetbrains.kotlin.ir.symbols.IrFunctionSymbol");
                    }
                    irFunctionSymbolDeserializeIrSymbolAndRemap$ir_serialization_common = (IrFunctionSymbol) irSymbolReferenceDeserializedSymbol;
                }
            }
            throw new IllegalStateException(("No public signature for symbol " + irFunctionSymbolDeserializeIrSymbolAndRemap$ir_serialization_common).toString());
        }
        IrFunctionSymbol irFunctionSymbol = (IrFunctionSymbol) irFunctionSymbolDeserializeIrSymbolAndRemap$ir_serialization_common;
        IrStatementOrigin irStatementOriginDeserializeIrStatementOrigin = proto.hasOriginName() ? deserializeIrStatementOrigin(proto.getOriginName()) : null;
        boolean zHasReflectionTargetSymbol = proto.hasReflectionTargetSymbol();
        BinarySymbolData.SymbolKind symbolKind2 = BinarySymbolData.SymbolKind.FUNCTION_SYMBOL;
        if (zHasReflectionTargetSymbol) {
            long reflectionTargetSymbol = proto.getReflectionTargetSymbol();
            IrDeclarationDeserializer irDeclarationDeserializer2 = this.declarationDeserializer;
            IrSymbol irSymbolDeserializeIrSymbolAndRemap$ir_serialization_common = irDeclarationDeserializer2.deserializeIrSymbolAndRemap$ir_serialization_common(reflectionTargetSymbol);
            if (!(irSymbolDeserializeIrSymbolAndRemap$ir_serialization_common instanceof IrFunctionSymbol)) {
                if (!irDeclarationDeserializer2.partialLinkageEnabled) {
                    throw new IrSymbolTypeMismatchException(IrFunctionSymbol.class, irSymbolDeserializeIrSymbolAndRemap$ir_serialization_common);
                }
                ReferenceSymbolTable symbolTable2 = irDeclarationDeserializer2.getSymbolDeserializer().getSymbolTable();
                if (symbolKind2 == null) {
                    throw new IllegalStateException(("No fallback symbol kind specified for symbol " + irSymbolDeserializeIrSymbolAndRemap$ir_serialization_common).toString());
                }
                IdSignature signature2 = irSymbolDeserializeIrSymbolAndRemap$ir_serialization_common.getSignature();
                if (signature2 != null) {
                    if (!signature2.isPubliclyVisible()) {
                        signature2 = null;
                    }
                    if (signature2 != null) {
                        IrSymbol irSymbolReferenceDeserializedSymbol2 = IrSymbolDeserializerKt.referenceDeserializedSymbol(symbolTable2, null, symbolKind2, signature2);
                        if (irSymbolReferenceDeserializedSymbol2 == null) {
                            throw new NullPointerException("null cannot be cast to non-null type org.jetbrains.kotlin.ir.symbols.IrFunctionSymbol");
                        }
                        irSymbol = (IrFunctionSymbol) irSymbolReferenceDeserializedSymbol2;
                    }
                }
                throw new IllegalStateException(("No public signature for symbol " + irSymbolDeserializeIrSymbolAndRemap$ir_serialization_common).toString());
            }
            irSymbol = irSymbolDeserializeIrSymbolAndRemap$ir_serialization_common;
        }
        IrFunctionReferenceImpl irFunctionReferenceImpl = new IrFunctionReferenceImpl(start, end, type, irFunctionSymbol, proto.getMemberAccess().getTypeArgumentCount(), proto.getMemberAccess().getValueArgumentCount(), (IrFunctionSymbol) irSymbol, irStatementOriginDeserializeIrStatementOrigin);
        MemberAccessCommon memberAccess = proto.getMemberAccess();
        Intrinsics.checkNotNullExpressionValue(memberAccess, "getMemberAccess(...)");
        deserializeMemberAccessCommon(irFunctionReferenceImpl, memberAccess);
        return irFunctionReferenceImpl;
    }

    private final IrGetClass deserializeGetClass(org.jetbrains.kotlin.backend.common.serialization.proto.IrGetClass proto, int start, int end, IrType type) {
        IrExpression argument = proto.getArgument();
        Intrinsics.checkNotNullExpressionValue(argument, "getArgument(...)");
        return new IrGetClassImpl(start, end, type, deserializeExpression(argument));
    }

    private final IrGetField deserializeGetField(org.jetbrains.kotlin.backend.common.serialization.proto.IrGetField proto, int start, int end, IrType type) throws IrSymbolTypeMismatchException {
        IrClassSymbol irClassSymbolDeserializeIrSymbolAndRemap$ir_serialization_common;
        FieldAccessCommon fieldAccess = proto.getFieldAccess();
        long symbol = fieldAccess.getSymbol();
        BinarySymbolData.SymbolKind symbolKind = BinarySymbolData.SymbolKind.FIELD_SYMBOL;
        IrDeclarationDeserializer irDeclarationDeserializer = this.declarationDeserializer;
        IrFieldSymbol irFieldSymbolDeserializeIrSymbolAndRemap$ir_serialization_common = irDeclarationDeserializer.deserializeIrSymbolAndRemap$ir_serialization_common(symbol);
        org.jetbrains.kotlin.ir.expressions.IrExpression irExpressionDeserializeExpression = null;
        if (!(irFieldSymbolDeserializeIrSymbolAndRemap$ir_serialization_common instanceof IrFieldSymbol)) {
            if (!irDeclarationDeserializer.partialLinkageEnabled) {
                throw new IrSymbolTypeMismatchException(IrFieldSymbol.class, irFieldSymbolDeserializeIrSymbolAndRemap$ir_serialization_common);
            }
            ReferenceSymbolTable symbolTable = irDeclarationDeserializer.getSymbolDeserializer().getSymbolTable();
            if (symbolKind == null) {
                throw new IllegalStateException(("No fallback symbol kind specified for symbol " + irFieldSymbolDeserializeIrSymbolAndRemap$ir_serialization_common).toString());
            }
            IdSignature signature = irFieldSymbolDeserializeIrSymbolAndRemap$ir_serialization_common.getSignature();
            if (signature != null) {
                if (!signature.isPubliclyVisible()) {
                    signature = null;
                }
                if (signature != null) {
                    IrSymbol irSymbolReferenceDeserializedSymbol = IrSymbolDeserializerKt.referenceDeserializedSymbol(symbolTable, null, symbolKind, signature);
                    if (irSymbolReferenceDeserializedSymbol == null) {
                        throw new NullPointerException("null cannot be cast to non-null type org.jetbrains.kotlin.ir.symbols.IrFieldSymbol");
                    }
                    irFieldSymbolDeserializeIrSymbolAndRemap$ir_serialization_common = (IrFieldSymbol) irSymbolReferenceDeserializedSymbol;
                }
            }
            throw new IllegalStateException(("No public signature for symbol " + irFieldSymbolDeserializeIrSymbolAndRemap$ir_serialization_common).toString());
        }
        IrFieldSymbol irFieldSymbol = (IrFieldSymbol) irFieldSymbolDeserializeIrSymbolAndRemap$ir_serialization_common;
        IrStatementOrigin irStatementOriginDeserializeIrStatementOrigin = proto.hasOriginName() ? deserializeIrStatementOrigin(proto.getOriginName()) : null;
        boolean zHasSuper = fieldAccess.hasSuper();
        BinarySymbolData.SymbolKind symbolKind2 = BinarySymbolData.SymbolKind.CLASS_SYMBOL;
        if (zHasSuper) {
            long j = fieldAccess.getSuper();
            IrDeclarationDeserializer irDeclarationDeserializer2 = this.declarationDeserializer;
            irClassSymbolDeserializeIrSymbolAndRemap$ir_serialization_common = irDeclarationDeserializer2.deserializeIrSymbolAndRemap$ir_serialization_common(j);
            if (!(irClassSymbolDeserializeIrSymbolAndRemap$ir_serialization_common instanceof IrClassSymbol)) {
                if (!irDeclarationDeserializer2.partialLinkageEnabled) {
                    throw new IrSymbolTypeMismatchException(IrClassSymbol.class, irClassSymbolDeserializeIrSymbolAndRemap$ir_serialization_common);
                }
                ReferenceSymbolTable symbolTable2 = irDeclarationDeserializer2.getSymbolDeserializer().getSymbolTable();
                if (symbolKind2 == null) {
                    throw new IllegalStateException(("No fallback symbol kind specified for symbol " + irClassSymbolDeserializeIrSymbolAndRemap$ir_serialization_common).toString());
                }
                IdSignature signature2 = irClassSymbolDeserializeIrSymbolAndRemap$ir_serialization_common.getSignature();
                if (signature2 != null) {
                    if (!signature2.isPubliclyVisible()) {
                        signature2 = null;
                    }
                    if (signature2 != null) {
                        IrSymbol irSymbolReferenceDeserializedSymbol2 = IrSymbolDeserializerKt.referenceDeserializedSymbol(symbolTable2, null, symbolKind2, signature2);
                        if (irSymbolReferenceDeserializedSymbol2 == null) {
                            throw new NullPointerException("null cannot be cast to non-null type org.jetbrains.kotlin.ir.symbols.IrClassSymbol");
                        }
                        irClassSymbolDeserializeIrSymbolAndRemap$ir_serialization_common = (IrClassSymbol) irSymbolReferenceDeserializedSymbol2;
                    }
                }
                throw new IllegalStateException(("No public signature for symbol " + irClassSymbolDeserializeIrSymbolAndRemap$ir_serialization_common).toString());
            }
        } else {
            irClassSymbolDeserializeIrSymbolAndRemap$ir_serialization_common = null;
        }
        IrClassSymbol irClassSymbol = (IrClassSymbol) irClassSymbolDeserializeIrSymbolAndRemap$ir_serialization_common;
        if (fieldAccess.hasReceiver()) {
            IrExpression receiver = fieldAccess.getReceiver();
            Intrinsics.checkNotNullExpressionValue(receiver, "getReceiver(...)");
            irExpressionDeserializeExpression = deserializeExpression(receiver);
        }
        return new IrGetFieldImpl(start, end, irFieldSymbol, type, irExpressionDeserializeExpression, irStatementOriginDeserializeIrStatementOrigin, irClassSymbol);
    }

    private final IrGetValue deserializeGetValue(org.jetbrains.kotlin.backend.common.serialization.proto.IrGetValue proto, int start, int end, IrType type) throws IrSymbolTypeMismatchException {
        long symbol = proto.getSymbol();
        IrDeclarationDeserializer irDeclarationDeserializer = this.declarationDeserializer;
        IrSymbol irSymbolDeserializeIrSymbol$ir_serialization_common = irDeclarationDeserializer.deserializeIrSymbol$ir_serialization_common(symbol);
        if (!(irSymbolDeserializeIrSymbol$ir_serialization_common instanceof IrValueSymbol)) {
            if (!irDeclarationDeserializer.partialLinkageEnabled) {
                throw new IrSymbolTypeMismatchException(IrValueSymbol.class, irSymbolDeserializeIrSymbol$ir_serialization_common);
            }
            irDeclarationDeserializer.getSymbolDeserializer().getSymbolTable();
            throw new IllegalStateException(("No fallback symbol kind specified for symbol " + irSymbolDeserializeIrSymbol$ir_serialization_common).toString());
        }
        return new IrGetValueImpl(start, end, type, (IrValueSymbol) irSymbolDeserializeIrSymbol$ir_serialization_common, proto.hasOriginName() ? deserializeIrStatementOrigin(proto.getOriginName()) : null);
    }

    private final IrGetEnumValue deserializeGetEnumValue(org.jetbrains.kotlin.backend.common.serialization.proto.IrGetEnumValue proto, int start, int end, IrType type) throws IrSymbolTypeMismatchException {
        long symbol = proto.getSymbol();
        BinarySymbolData.SymbolKind symbolKind = BinarySymbolData.SymbolKind.ENUM_ENTRY_SYMBOL;
        IrDeclarationDeserializer irDeclarationDeserializer = this.declarationDeserializer;
        IrEnumEntrySymbol irEnumEntrySymbolDeserializeIrSymbolAndRemap$ir_serialization_common = irDeclarationDeserializer.deserializeIrSymbolAndRemap$ir_serialization_common(symbol);
        if (!(irEnumEntrySymbolDeserializeIrSymbolAndRemap$ir_serialization_common instanceof IrEnumEntrySymbol)) {
            if (!irDeclarationDeserializer.partialLinkageEnabled) {
                throw new IrSymbolTypeMismatchException(IrEnumEntrySymbol.class, irEnumEntrySymbolDeserializeIrSymbolAndRemap$ir_serialization_common);
            }
            ReferenceSymbolTable symbolTable = irDeclarationDeserializer.getSymbolDeserializer().getSymbolTable();
            if (symbolKind == null) {
                throw new IllegalStateException(("No fallback symbol kind specified for symbol " + irEnumEntrySymbolDeserializeIrSymbolAndRemap$ir_serialization_common).toString());
            }
            IdSignature signature = irEnumEntrySymbolDeserializeIrSymbolAndRemap$ir_serialization_common.getSignature();
            if (signature != null) {
                if (!signature.isPubliclyVisible()) {
                    signature = null;
                }
                if (signature != null) {
                    IrSymbol irSymbolReferenceDeserializedSymbol = IrSymbolDeserializerKt.referenceDeserializedSymbol(symbolTable, null, symbolKind, signature);
                    if (irSymbolReferenceDeserializedSymbol == null) {
                        throw new NullPointerException("null cannot be cast to non-null type org.jetbrains.kotlin.ir.symbols.IrEnumEntrySymbol");
                    }
                    irEnumEntrySymbolDeserializeIrSymbolAndRemap$ir_serialization_common = (IrEnumEntrySymbol) irSymbolReferenceDeserializedSymbol;
                }
            }
            throw new IllegalStateException(("No public signature for symbol " + irEnumEntrySymbolDeserializeIrSymbolAndRemap$ir_serialization_common).toString());
        }
        return new IrGetEnumValueImpl(start, end, type, (IrEnumEntrySymbol) irEnumEntrySymbolDeserializeIrSymbolAndRemap$ir_serialization_common);
    }

    private final IrGetObjectValue deserializeGetObject(IrGetObject proto, int start, int end, IrType type) throws IrSymbolTypeMismatchException {
        long symbol = proto.getSymbol();
        BinarySymbolData.SymbolKind symbolKind = BinarySymbolData.SymbolKind.CLASS_SYMBOL;
        IrDeclarationDeserializer irDeclarationDeserializer = this.declarationDeserializer;
        IrClassSymbol irClassSymbolDeserializeIrSymbolAndRemap$ir_serialization_common = irDeclarationDeserializer.deserializeIrSymbolAndRemap$ir_serialization_common(symbol);
        if (!(irClassSymbolDeserializeIrSymbolAndRemap$ir_serialization_common instanceof IrClassSymbol)) {
            if (!irDeclarationDeserializer.partialLinkageEnabled) {
                throw new IrSymbolTypeMismatchException(IrClassSymbol.class, irClassSymbolDeserializeIrSymbolAndRemap$ir_serialization_common);
            }
            ReferenceSymbolTable symbolTable = irDeclarationDeserializer.getSymbolDeserializer().getSymbolTable();
            if (symbolKind == null) {
                throw new IllegalStateException(("No fallback symbol kind specified for symbol " + irClassSymbolDeserializeIrSymbolAndRemap$ir_serialization_common).toString());
            }
            IdSignature signature = irClassSymbolDeserializeIrSymbolAndRemap$ir_serialization_common.getSignature();
            if (signature != null) {
                if (!signature.isPubliclyVisible()) {
                    signature = null;
                }
                if (signature != null) {
                    IrSymbol irSymbolReferenceDeserializedSymbol = IrSymbolDeserializerKt.referenceDeserializedSymbol(symbolTable, null, symbolKind, signature);
                    if (irSymbolReferenceDeserializedSymbol == null) {
                        throw new NullPointerException("null cannot be cast to non-null type org.jetbrains.kotlin.ir.symbols.IrClassSymbol");
                    }
                    irClassSymbolDeserializeIrSymbolAndRemap$ir_serialization_common = (IrClassSymbol) irSymbolReferenceDeserializedSymbol;
                }
            }
            throw new IllegalStateException(("No public signature for symbol " + irClassSymbolDeserializeIrSymbolAndRemap$ir_serialization_common).toString());
        }
        return new IrGetObjectValueImpl(start, end, type, (IrClassSymbol) irClassSymbolDeserializeIrSymbolAndRemap$ir_serialization_common);
    }

    private final IrInstanceInitializerCall deserializeInstanceInitializerCall(org.jetbrains.kotlin.backend.common.serialization.proto.IrInstanceInitializerCall proto, int start, int end) throws IrSymbolTypeMismatchException {
        long symbol = proto.getSymbol();
        BinarySymbolData.SymbolKind symbolKind = BinarySymbolData.SymbolKind.CLASS_SYMBOL;
        IrDeclarationDeserializer irDeclarationDeserializer = this.declarationDeserializer;
        IrClassSymbol irClassSymbolDeserializeIrSymbolAndRemap$ir_serialization_common = irDeclarationDeserializer.deserializeIrSymbolAndRemap$ir_serialization_common(symbol);
        if (!(irClassSymbolDeserializeIrSymbolAndRemap$ir_serialization_common instanceof IrClassSymbol)) {
            if (!irDeclarationDeserializer.partialLinkageEnabled) {
                throw new IrSymbolTypeMismatchException(IrClassSymbol.class, irClassSymbolDeserializeIrSymbolAndRemap$ir_serialization_common);
            }
            ReferenceSymbolTable symbolTable = irDeclarationDeserializer.getSymbolDeserializer().getSymbolTable();
            if (symbolKind == null) {
                throw new IllegalStateException(("No fallback symbol kind specified for symbol " + irClassSymbolDeserializeIrSymbolAndRemap$ir_serialization_common).toString());
            }
            IdSignature signature = irClassSymbolDeserializeIrSymbolAndRemap$ir_serialization_common.getSignature();
            if (signature != null) {
                if (!signature.isPubliclyVisible()) {
                    signature = null;
                }
                if (signature != null) {
                    IrSymbol irSymbolReferenceDeserializedSymbol = IrSymbolDeserializerKt.referenceDeserializedSymbol(symbolTable, null, symbolKind, signature);
                    if (irSymbolReferenceDeserializedSymbol == null) {
                        throw new NullPointerException("null cannot be cast to non-null type org.jetbrains.kotlin.ir.symbols.IrClassSymbol");
                    }
                    irClassSymbolDeserializeIrSymbolAndRemap$ir_serialization_common = (IrClassSymbol) irSymbolReferenceDeserializedSymbol;
                }
            }
            throw new IllegalStateException(("No public signature for symbol " + irClassSymbolDeserializeIrSymbolAndRemap$ir_serialization_common).toString());
        }
        return new IrInstanceInitializerCallImpl(start, end, (IrClassSymbol) irClassSymbolDeserializeIrSymbolAndRemap$ir_serialization_common, this.builtIns.getUnitType());
    }

    private final IrLocalDelegatedPropertyReference deserializeIrLocalDelegatedPropertyReference(org.jetbrains.kotlin.backend.common.serialization.proto.IrLocalDelegatedPropertyReference proto, int start, int end, IrType type) throws IrSymbolTypeMismatchException {
        IrSimpleFunctionSymbol irSimpleFunctionSymbolDeserializeIrSymbolAndRemap$ir_serialization_common;
        long delegate = proto.getDelegate();
        IrDeclarationDeserializer irDeclarationDeserializer = this.declarationDeserializer;
        IrSymbol irSymbolDeserializeIrSymbolAndRemap$ir_serialization_common = irDeclarationDeserializer.deserializeIrSymbolAndRemap$ir_serialization_common(delegate);
        if (!(irSymbolDeserializeIrSymbolAndRemap$ir_serialization_common instanceof IrVariableSymbol)) {
            if (!irDeclarationDeserializer.partialLinkageEnabled) {
                throw new IrSymbolTypeMismatchException(IrVariableSymbol.class, irSymbolDeserializeIrSymbolAndRemap$ir_serialization_common);
            }
            irDeclarationDeserializer.getSymbolDeserializer().getSymbolTable();
            throw new IllegalStateException(("No fallback symbol kind specified for symbol " + irSymbolDeserializeIrSymbolAndRemap$ir_serialization_common).toString());
        }
        IrVariableSymbol irVariableSymbol = (IrVariableSymbol) irSymbolDeserializeIrSymbolAndRemap$ir_serialization_common;
        long getter = proto.getGetter();
        BinarySymbolData.SymbolKind symbolKind = BinarySymbolData.SymbolKind.FUNCTION_SYMBOL;
        IrDeclarationDeserializer irDeclarationDeserializer2 = this.declarationDeserializer;
        IrSimpleFunctionSymbol irSimpleFunctionSymbolDeserializeIrSymbolAndRemap$ir_serialization_common2 = irDeclarationDeserializer2.deserializeIrSymbolAndRemap$ir_serialization_common(getter);
        if (!(irSimpleFunctionSymbolDeserializeIrSymbolAndRemap$ir_serialization_common2 instanceof IrSimpleFunctionSymbol)) {
            if (!irDeclarationDeserializer2.partialLinkageEnabled) {
                throw new IrSymbolTypeMismatchException(IrSimpleFunctionSymbol.class, irSimpleFunctionSymbolDeserializeIrSymbolAndRemap$ir_serialization_common2);
            }
            ReferenceSymbolTable symbolTable = irDeclarationDeserializer2.getSymbolDeserializer().getSymbolTable();
            if (symbolKind == null) {
                throw new IllegalStateException(("No fallback symbol kind specified for symbol " + irSimpleFunctionSymbolDeserializeIrSymbolAndRemap$ir_serialization_common2).toString());
            }
            IdSignature signature = irSimpleFunctionSymbolDeserializeIrSymbolAndRemap$ir_serialization_common2.getSignature();
            if (signature != null) {
                if (!signature.isPubliclyVisible()) {
                    signature = null;
                }
                if (signature != null) {
                    IrSymbol irSymbolReferenceDeserializedSymbol = IrSymbolDeserializerKt.referenceDeserializedSymbol(symbolTable, null, symbolKind, signature);
                    if (irSymbolReferenceDeserializedSymbol == null) {
                        throw new NullPointerException("null cannot be cast to non-null type org.jetbrains.kotlin.ir.symbols.IrSimpleFunctionSymbol");
                    }
                    irSimpleFunctionSymbolDeserializeIrSymbolAndRemap$ir_serialization_common2 = (IrSimpleFunctionSymbol) irSymbolReferenceDeserializedSymbol;
                }
            }
            throw new IllegalStateException(("No public signature for symbol " + irSimpleFunctionSymbolDeserializeIrSymbolAndRemap$ir_serialization_common2).toString());
        }
        IrSimpleFunctionSymbol irSimpleFunctionSymbol = (IrSimpleFunctionSymbol) irSimpleFunctionSymbolDeserializeIrSymbolAndRemap$ir_serialization_common2;
        boolean zHasSetter = proto.hasSetter();
        BinarySymbolData.SymbolKind symbolKind2 = BinarySymbolData.SymbolKind.FUNCTION_SYMBOL;
        if (zHasSetter) {
            long setter = proto.getSetter();
            IrDeclarationDeserializer irDeclarationDeserializer3 = this.declarationDeserializer;
            irSimpleFunctionSymbolDeserializeIrSymbolAndRemap$ir_serialization_common = irDeclarationDeserializer3.deserializeIrSymbolAndRemap$ir_serialization_common(setter);
            if (!(irSimpleFunctionSymbolDeserializeIrSymbolAndRemap$ir_serialization_common instanceof IrSimpleFunctionSymbol)) {
                if (!irDeclarationDeserializer3.partialLinkageEnabled) {
                    throw new IrSymbolTypeMismatchException(IrSimpleFunctionSymbol.class, irSimpleFunctionSymbolDeserializeIrSymbolAndRemap$ir_serialization_common);
                }
                ReferenceSymbolTable symbolTable2 = irDeclarationDeserializer3.getSymbolDeserializer().getSymbolTable();
                if (symbolKind2 == null) {
                    throw new IllegalStateException(("No fallback symbol kind specified for symbol " + irSimpleFunctionSymbolDeserializeIrSymbolAndRemap$ir_serialization_common).toString());
                }
                IdSignature signature2 = irSimpleFunctionSymbolDeserializeIrSymbolAndRemap$ir_serialization_common.getSignature();
                if (signature2 != null) {
                    if (!signature2.isPubliclyVisible()) {
                        signature2 = null;
                    }
                    if (signature2 != null) {
                        IrSymbol irSymbolReferenceDeserializedSymbol2 = IrSymbolDeserializerKt.referenceDeserializedSymbol(symbolTable2, null, symbolKind2, signature2);
                        if (irSymbolReferenceDeserializedSymbol2 == null) {
                            throw new NullPointerException("null cannot be cast to non-null type org.jetbrains.kotlin.ir.symbols.IrSimpleFunctionSymbol");
                        }
                        irSimpleFunctionSymbolDeserializeIrSymbolAndRemap$ir_serialization_common = (IrSimpleFunctionSymbol) irSymbolReferenceDeserializedSymbol2;
                    }
                }
                throw new IllegalStateException(("No public signature for symbol " + irSimpleFunctionSymbolDeserializeIrSymbolAndRemap$ir_serialization_common).toString());
            }
        } else {
            irSimpleFunctionSymbolDeserializeIrSymbolAndRemap$ir_serialization_common = null;
        }
        IrSimpleFunctionSymbol irSimpleFunctionSymbol2 = (IrSimpleFunctionSymbol) irSimpleFunctionSymbolDeserializeIrSymbolAndRemap$ir_serialization_common;
        long symbol = proto.getSymbol();
        IrDeclarationDeserializer irDeclarationDeserializer4 = this.declarationDeserializer;
        IrSymbol irSymbolDeserializeIrSymbolAndRemap$ir_serialization_common2 = irDeclarationDeserializer4.deserializeIrSymbolAndRemap$ir_serialization_common(symbol);
        if (!(irSymbolDeserializeIrSymbolAndRemap$ir_serialization_common2 instanceof IrLocalDelegatedPropertySymbol)) {
            if (!irDeclarationDeserializer4.partialLinkageEnabled) {
                throw new IrSymbolTypeMismatchException(IrLocalDelegatedPropertySymbol.class, irSymbolDeserializeIrSymbolAndRemap$ir_serialization_common2);
            }
            irDeclarationDeserializer4.getSymbolDeserializer().getSymbolTable();
            throw new IllegalStateException(("No fallback symbol kind specified for symbol " + irSymbolDeserializeIrSymbolAndRemap$ir_serialization_common2).toString());
        }
        return new IrLocalDelegatedPropertyReferenceImpl(start, end, type, (IrLocalDelegatedPropertySymbol) irSymbolDeserializeIrSymbolAndRemap$ir_serialization_common2, irVariableSymbol, irSimpleFunctionSymbol, irSimpleFunctionSymbol2, proto.hasOriginName() ? deserializeIrStatementOrigin(proto.getOriginName()) : null);
    }

    private final IrPropertyReference deserializePropertyReference(org.jetbrains.kotlin.backend.common.serialization.proto.IrPropertyReference proto, int start, int end, IrType type) throws IrSymbolTypeMismatchException {
        IrFieldSymbol irFieldSymbolDeserializeIrSymbolAndRemap$ir_serialization_common;
        IrSimpleFunctionSymbol irSimpleFunctionSymbolDeserializeIrSymbolAndRemap$ir_serialization_common;
        IrSimpleFunctionSymbol irSimpleFunctionSymbolDeserializeIrSymbolAndRemap$ir_serialization_common2;
        long symbol = proto.getSymbol();
        BinarySymbolData.SymbolKind symbolKind = BinarySymbolData.SymbolKind.PROPERTY_SYMBOL;
        IrDeclarationDeserializer irDeclarationDeserializer = this.declarationDeserializer;
        IrPropertySymbol irPropertySymbolDeserializeIrSymbolAndRemap$ir_serialization_common = irDeclarationDeserializer.deserializeIrSymbolAndRemap$ir_serialization_common(symbol);
        if (!(irPropertySymbolDeserializeIrSymbolAndRemap$ir_serialization_common instanceof IrPropertySymbol)) {
            if (!irDeclarationDeserializer.partialLinkageEnabled) {
                throw new IrSymbolTypeMismatchException(IrPropertySymbol.class, irPropertySymbolDeserializeIrSymbolAndRemap$ir_serialization_common);
            }
            ReferenceSymbolTable symbolTable = irDeclarationDeserializer.getSymbolDeserializer().getSymbolTable();
            if (symbolKind == null) {
                throw new IllegalStateException(("No fallback symbol kind specified for symbol " + irPropertySymbolDeserializeIrSymbolAndRemap$ir_serialization_common).toString());
            }
            IdSignature signature = irPropertySymbolDeserializeIrSymbolAndRemap$ir_serialization_common.getSignature();
            if (signature != null) {
                if (!signature.isPubliclyVisible()) {
                    signature = null;
                }
                if (signature != null) {
                    IrSymbol irSymbolReferenceDeserializedSymbol = IrSymbolDeserializerKt.referenceDeserializedSymbol(symbolTable, null, symbolKind, signature);
                    if (irSymbolReferenceDeserializedSymbol == null) {
                        throw new NullPointerException("null cannot be cast to non-null type org.jetbrains.kotlin.ir.symbols.IrPropertySymbol");
                    }
                    irPropertySymbolDeserializeIrSymbolAndRemap$ir_serialization_common = (IrPropertySymbol) irSymbolReferenceDeserializedSymbol;
                }
            }
            throw new IllegalStateException(("No public signature for symbol " + irPropertySymbolDeserializeIrSymbolAndRemap$ir_serialization_common).toString());
        }
        IrPropertySymbol irPropertySymbol = (IrPropertySymbol) irPropertySymbolDeserializeIrSymbolAndRemap$ir_serialization_common;
        boolean zHasField = proto.hasField();
        BinarySymbolData.SymbolKind symbolKind2 = BinarySymbolData.SymbolKind.FIELD_SYMBOL;
        if (zHasField) {
            long field = proto.getField();
            IrDeclarationDeserializer irDeclarationDeserializer2 = this.declarationDeserializer;
            irFieldSymbolDeserializeIrSymbolAndRemap$ir_serialization_common = irDeclarationDeserializer2.deserializeIrSymbolAndRemap$ir_serialization_common(field);
            if (!(irFieldSymbolDeserializeIrSymbolAndRemap$ir_serialization_common instanceof IrFieldSymbol)) {
                if (!irDeclarationDeserializer2.partialLinkageEnabled) {
                    throw new IrSymbolTypeMismatchException(IrFieldSymbol.class, irFieldSymbolDeserializeIrSymbolAndRemap$ir_serialization_common);
                }
                ReferenceSymbolTable symbolTable2 = irDeclarationDeserializer2.getSymbolDeserializer().getSymbolTable();
                if (symbolKind2 == null) {
                    throw new IllegalStateException(("No fallback symbol kind specified for symbol " + irFieldSymbolDeserializeIrSymbolAndRemap$ir_serialization_common).toString());
                }
                IdSignature signature2 = irFieldSymbolDeserializeIrSymbolAndRemap$ir_serialization_common.getSignature();
                if (signature2 != null) {
                    if (!signature2.isPubliclyVisible()) {
                        signature2 = null;
                    }
                    if (signature2 != null) {
                        IrSymbol irSymbolReferenceDeserializedSymbol2 = IrSymbolDeserializerKt.referenceDeserializedSymbol(symbolTable2, null, symbolKind2, signature2);
                        if (irSymbolReferenceDeserializedSymbol2 == null) {
                            throw new NullPointerException("null cannot be cast to non-null type org.jetbrains.kotlin.ir.symbols.IrFieldSymbol");
                        }
                        irFieldSymbolDeserializeIrSymbolAndRemap$ir_serialization_common = (IrFieldSymbol) irSymbolReferenceDeserializedSymbol2;
                    }
                }
                throw new IllegalStateException(("No public signature for symbol " + irFieldSymbolDeserializeIrSymbolAndRemap$ir_serialization_common).toString());
            }
        } else {
            irFieldSymbolDeserializeIrSymbolAndRemap$ir_serialization_common = null;
        }
        IrFieldSymbol irFieldSymbol = (IrFieldSymbol) irFieldSymbolDeserializeIrSymbolAndRemap$ir_serialization_common;
        boolean zHasGetter = proto.hasGetter();
        BinarySymbolData.SymbolKind symbolKind3 = BinarySymbolData.SymbolKind.FUNCTION_SYMBOL;
        if (zHasGetter) {
            long getter = proto.getGetter();
            IrDeclarationDeserializer irDeclarationDeserializer3 = this.declarationDeserializer;
            irSimpleFunctionSymbolDeserializeIrSymbolAndRemap$ir_serialization_common = irDeclarationDeserializer3.deserializeIrSymbolAndRemap$ir_serialization_common(getter);
            if (!(irSimpleFunctionSymbolDeserializeIrSymbolAndRemap$ir_serialization_common instanceof IrSimpleFunctionSymbol)) {
                if (!irDeclarationDeserializer3.partialLinkageEnabled) {
                    throw new IrSymbolTypeMismatchException(IrSimpleFunctionSymbol.class, irSimpleFunctionSymbolDeserializeIrSymbolAndRemap$ir_serialization_common);
                }
                ReferenceSymbolTable symbolTable3 = irDeclarationDeserializer3.getSymbolDeserializer().getSymbolTable();
                if (symbolKind3 == null) {
                    throw new IllegalStateException(("No fallback symbol kind specified for symbol " + irSimpleFunctionSymbolDeserializeIrSymbolAndRemap$ir_serialization_common).toString());
                }
                IdSignature signature3 = irSimpleFunctionSymbolDeserializeIrSymbolAndRemap$ir_serialization_common.getSignature();
                if (signature3 != null) {
                    if (!signature3.isPubliclyVisible()) {
                        signature3 = null;
                    }
                    if (signature3 != null) {
                        IrSymbol irSymbolReferenceDeserializedSymbol3 = IrSymbolDeserializerKt.referenceDeserializedSymbol(symbolTable3, null, symbolKind3, signature3);
                        if (irSymbolReferenceDeserializedSymbol3 == null) {
                            throw new NullPointerException("null cannot be cast to non-null type org.jetbrains.kotlin.ir.symbols.IrSimpleFunctionSymbol");
                        }
                        irSimpleFunctionSymbolDeserializeIrSymbolAndRemap$ir_serialization_common = (IrSimpleFunctionSymbol) irSymbolReferenceDeserializedSymbol3;
                    }
                }
                throw new IllegalStateException(("No public signature for symbol " + irSimpleFunctionSymbolDeserializeIrSymbolAndRemap$ir_serialization_common).toString());
            }
        } else {
            irSimpleFunctionSymbolDeserializeIrSymbolAndRemap$ir_serialization_common = null;
        }
        IrSimpleFunctionSymbol irSimpleFunctionSymbol = (IrSimpleFunctionSymbol) irSimpleFunctionSymbolDeserializeIrSymbolAndRemap$ir_serialization_common;
        boolean zHasSetter = proto.hasSetter();
        BinarySymbolData.SymbolKind symbolKind4 = BinarySymbolData.SymbolKind.FUNCTION_SYMBOL;
        if (zHasSetter) {
            long setter = proto.getSetter();
            IrDeclarationDeserializer irDeclarationDeserializer4 = this.declarationDeserializer;
            irSimpleFunctionSymbolDeserializeIrSymbolAndRemap$ir_serialization_common2 = irDeclarationDeserializer4.deserializeIrSymbolAndRemap$ir_serialization_common(setter);
            if (!(irSimpleFunctionSymbolDeserializeIrSymbolAndRemap$ir_serialization_common2 instanceof IrSimpleFunctionSymbol)) {
                if (!irDeclarationDeserializer4.partialLinkageEnabled) {
                    throw new IrSymbolTypeMismatchException(IrSimpleFunctionSymbol.class, irSimpleFunctionSymbolDeserializeIrSymbolAndRemap$ir_serialization_common2);
                }
                ReferenceSymbolTable symbolTable4 = irDeclarationDeserializer4.getSymbolDeserializer().getSymbolTable();
                if (symbolKind4 == null) {
                    throw new IllegalStateException(("No fallback symbol kind specified for symbol " + irSimpleFunctionSymbolDeserializeIrSymbolAndRemap$ir_serialization_common2).toString());
                }
                IdSignature signature4 = irSimpleFunctionSymbolDeserializeIrSymbolAndRemap$ir_serialization_common2.getSignature();
                if (signature4 != null) {
                    if (!signature4.isPubliclyVisible()) {
                        signature4 = null;
                    }
                    if (signature4 != null) {
                        IrSymbol irSymbolReferenceDeserializedSymbol4 = IrSymbolDeserializerKt.referenceDeserializedSymbol(symbolTable4, null, symbolKind4, signature4);
                        if (irSymbolReferenceDeserializedSymbol4 == null) {
                            throw new NullPointerException("null cannot be cast to non-null type org.jetbrains.kotlin.ir.symbols.IrSimpleFunctionSymbol");
                        }
                        irSimpleFunctionSymbolDeserializeIrSymbolAndRemap$ir_serialization_common2 = (IrSimpleFunctionSymbol) irSymbolReferenceDeserializedSymbol4;
                    }
                }
                throw new IllegalStateException(("No public signature for symbol " + irSimpleFunctionSymbolDeserializeIrSymbolAndRemap$ir_serialization_common2).toString());
            }
        } else {
            irSimpleFunctionSymbolDeserializeIrSymbolAndRemap$ir_serialization_common2 = null;
        }
        IrPropertyReferenceImpl irPropertyReferenceImpl = new IrPropertyReferenceImpl(start, end, type, irPropertySymbol, proto.getMemberAccess().getTypeArgumentCount(), irFieldSymbol, irSimpleFunctionSymbol, (IrSimpleFunctionSymbol) irSimpleFunctionSymbolDeserializeIrSymbolAndRemap$ir_serialization_common2, proto.hasOriginName() ? deserializeIrStatementOrigin(proto.getOriginName()) : null);
        MemberAccessCommon memberAccess = proto.getMemberAccess();
        Intrinsics.checkNotNullExpressionValue(memberAccess, "getMemberAccess(...)");
        deserializeMemberAccessCommon(irPropertyReferenceImpl, memberAccess);
        return irPropertyReferenceImpl;
    }

    private final IrReturn deserializeReturn(org.jetbrains.kotlin.backend.common.serialization.proto.IrReturn proto, int start, int end) throws IrSymbolTypeMismatchException {
        long returnTarget = proto.getReturnTarget();
        BinarySymbolData.SymbolKind symbolKind = BinarySymbolData.SymbolKind.FUNCTION_SYMBOL;
        IrDeclarationDeserializer irDeclarationDeserializer = this.declarationDeserializer;
        IrReturnTargetSymbol irReturnTargetSymbolDeserializeIrSymbolAndRemap$ir_serialization_common = irDeclarationDeserializer.deserializeIrSymbolAndRemap$ir_serialization_common(returnTarget);
        if (!(irReturnTargetSymbolDeserializeIrSymbolAndRemap$ir_serialization_common instanceof IrReturnTargetSymbol)) {
            if (!irDeclarationDeserializer.partialLinkageEnabled) {
                throw new IrSymbolTypeMismatchException(IrReturnTargetSymbol.class, irReturnTargetSymbolDeserializeIrSymbolAndRemap$ir_serialization_common);
            }
            ReferenceSymbolTable symbolTable = irDeclarationDeserializer.getSymbolDeserializer().getSymbolTable();
            if (symbolKind == null) {
                throw new IllegalStateException(("No fallback symbol kind specified for symbol " + irReturnTargetSymbolDeserializeIrSymbolAndRemap$ir_serialization_common).toString());
            }
            IdSignature signature = irReturnTargetSymbolDeserializeIrSymbolAndRemap$ir_serialization_common.getSignature();
            if (signature != null) {
                if (!signature.isPubliclyVisible()) {
                    signature = null;
                }
                if (signature != null) {
                    IrSymbol irSymbolReferenceDeserializedSymbol = IrSymbolDeserializerKt.referenceDeserializedSymbol(symbolTable, null, symbolKind, signature);
                    if (irSymbolReferenceDeserializedSymbol == null) {
                        throw new NullPointerException("null cannot be cast to non-null type org.jetbrains.kotlin.ir.symbols.IrReturnTargetSymbol");
                    }
                    irReturnTargetSymbolDeserializeIrSymbolAndRemap$ir_serialization_common = (IrReturnTargetSymbol) irSymbolReferenceDeserializedSymbol;
                }
            }
            throw new IllegalStateException(("No public signature for symbol " + irReturnTargetSymbolDeserializeIrSymbolAndRemap$ir_serialization_common).toString());
        }
        IrReturnTargetSymbol irReturnTargetSymbol = (IrReturnTargetSymbol) irReturnTargetSymbolDeserializeIrSymbolAndRemap$ir_serialization_common;
        IrExpression value = proto.getValue();
        Intrinsics.checkNotNullExpressionValue(value, "getValue(...)");
        return new IrReturnImpl(start, end, this.builtIns.getNothingType(), irReturnTargetSymbol, deserializeExpression(value));
    }

    private final IrSetField deserializeSetField(org.jetbrains.kotlin.backend.common.serialization.proto.IrSetField proto, int start, int end) throws IrSymbolTypeMismatchException {
        IrClassSymbol irClassSymbolDeserializeIrSymbolAndRemap$ir_serialization_common;
        org.jetbrains.kotlin.ir.expressions.IrExpression irExpressionDeserializeExpression;
        FieldAccessCommon fieldAccess = proto.getFieldAccess();
        long symbol = fieldAccess.getSymbol();
        BinarySymbolData.SymbolKind symbolKind = BinarySymbolData.SymbolKind.FIELD_SYMBOL;
        IrDeclarationDeserializer irDeclarationDeserializer = this.declarationDeserializer;
        IrFieldSymbol irFieldSymbolDeserializeIrSymbolAndRemap$ir_serialization_common = irDeclarationDeserializer.deserializeIrSymbolAndRemap$ir_serialization_common(symbol);
        if (!(irFieldSymbolDeserializeIrSymbolAndRemap$ir_serialization_common instanceof IrFieldSymbol)) {
            if (!irDeclarationDeserializer.partialLinkageEnabled) {
                throw new IrSymbolTypeMismatchException(IrFieldSymbol.class, irFieldSymbolDeserializeIrSymbolAndRemap$ir_serialization_common);
            }
            ReferenceSymbolTable symbolTable = irDeclarationDeserializer.getSymbolDeserializer().getSymbolTable();
            if (symbolKind == null) {
                throw new IllegalStateException(("No fallback symbol kind specified for symbol " + irFieldSymbolDeserializeIrSymbolAndRemap$ir_serialization_common).toString());
            }
            IdSignature signature = irFieldSymbolDeserializeIrSymbolAndRemap$ir_serialization_common.getSignature();
            if (signature != null) {
                if (!signature.isPubliclyVisible()) {
                    signature = null;
                }
                if (signature != null) {
                    IrSymbol irSymbolReferenceDeserializedSymbol = IrSymbolDeserializerKt.referenceDeserializedSymbol(symbolTable, null, symbolKind, signature);
                    if (irSymbolReferenceDeserializedSymbol == null) {
                        throw new NullPointerException("null cannot be cast to non-null type org.jetbrains.kotlin.ir.symbols.IrFieldSymbol");
                    }
                    irFieldSymbolDeserializeIrSymbolAndRemap$ir_serialization_common = (IrFieldSymbol) irSymbolReferenceDeserializedSymbol;
                }
            }
            throw new IllegalStateException(("No public signature for symbol " + irFieldSymbolDeserializeIrSymbolAndRemap$ir_serialization_common).toString());
        }
        IrFieldSymbol irFieldSymbol = (IrFieldSymbol) irFieldSymbolDeserializeIrSymbolAndRemap$ir_serialization_common;
        boolean zHasSuper = fieldAccess.hasSuper();
        BinarySymbolData.SymbolKind symbolKind2 = BinarySymbolData.SymbolKind.CLASS_SYMBOL;
        if (zHasSuper) {
            long j = fieldAccess.getSuper();
            IrDeclarationDeserializer irDeclarationDeserializer2 = this.declarationDeserializer;
            irClassSymbolDeserializeIrSymbolAndRemap$ir_serialization_common = irDeclarationDeserializer2.deserializeIrSymbolAndRemap$ir_serialization_common(j);
            if (!(irClassSymbolDeserializeIrSymbolAndRemap$ir_serialization_common instanceof IrClassSymbol)) {
                if (!irDeclarationDeserializer2.partialLinkageEnabled) {
                    throw new IrSymbolTypeMismatchException(IrClassSymbol.class, irClassSymbolDeserializeIrSymbolAndRemap$ir_serialization_common);
                }
                ReferenceSymbolTable symbolTable2 = irDeclarationDeserializer2.getSymbolDeserializer().getSymbolTable();
                if (symbolKind2 == null) {
                    throw new IllegalStateException(("No fallback symbol kind specified for symbol " + irClassSymbolDeserializeIrSymbolAndRemap$ir_serialization_common).toString());
                }
                IdSignature signature2 = irClassSymbolDeserializeIrSymbolAndRemap$ir_serialization_common.getSignature();
                if (signature2 != null) {
                    if (!signature2.isPubliclyVisible()) {
                        signature2 = null;
                    }
                    if (signature2 != null) {
                        IrSymbol irSymbolReferenceDeserializedSymbol2 = IrSymbolDeserializerKt.referenceDeserializedSymbol(symbolTable2, null, symbolKind2, signature2);
                        if (irSymbolReferenceDeserializedSymbol2 == null) {
                            throw new NullPointerException("null cannot be cast to non-null type org.jetbrains.kotlin.ir.symbols.IrClassSymbol");
                        }
                        irClassSymbolDeserializeIrSymbolAndRemap$ir_serialization_common = (IrClassSymbol) irSymbolReferenceDeserializedSymbol2;
                    }
                }
                throw new IllegalStateException(("No public signature for symbol " + irClassSymbolDeserializeIrSymbolAndRemap$ir_serialization_common).toString());
            }
        } else {
            irClassSymbolDeserializeIrSymbolAndRemap$ir_serialization_common = null;
        }
        IrClassSymbol irClassSymbol = (IrClassSymbol) irClassSymbolDeserializeIrSymbolAndRemap$ir_serialization_common;
        if (fieldAccess.hasReceiver()) {
            IrExpression receiver = fieldAccess.getReceiver();
            Intrinsics.checkNotNullExpressionValue(receiver, "getReceiver(...)");
            irExpressionDeserializeExpression = deserializeExpression(receiver);
        } else {
            irExpressionDeserializeExpression = null;
        }
        IrExpression value = proto.getValue();
        Intrinsics.checkNotNullExpressionValue(value, "getValue(...)");
        return new IrSetFieldImpl(start, end, irFieldSymbol, irExpressionDeserializeExpression, deserializeExpression(value), this.builtIns.getUnitType(), proto.hasOriginName() ? deserializeIrStatementOrigin(proto.getOriginName()) : null, irClassSymbol);
    }

    private final IrSetValue deserializeSetValue(org.jetbrains.kotlin.backend.common.serialization.proto.IrSetValue proto, int start, int end) throws IrSymbolTypeMismatchException {
        long symbol = proto.getSymbol();
        IrDeclarationDeserializer irDeclarationDeserializer = this.declarationDeserializer;
        IrSymbol irSymbolDeserializeIrSymbol$ir_serialization_common = irDeclarationDeserializer.deserializeIrSymbol$ir_serialization_common(symbol);
        if (!(irSymbolDeserializeIrSymbol$ir_serialization_common instanceof IrValueSymbol)) {
            if (!irDeclarationDeserializer.partialLinkageEnabled) {
                throw new IrSymbolTypeMismatchException(IrValueSymbol.class, irSymbolDeserializeIrSymbol$ir_serialization_common);
            }
            irDeclarationDeserializer.getSymbolDeserializer().getSymbolTable();
            throw new IllegalStateException(("No fallback symbol kind specified for symbol " + irSymbolDeserializeIrSymbol$ir_serialization_common).toString());
        }
        IrValueSymbol irValueSymbol = (IrValueSymbol) irSymbolDeserializeIrSymbol$ir_serialization_common;
        IrExpression value = proto.getValue();
        Intrinsics.checkNotNullExpressionValue(value, "getValue(...)");
        return new IrSetValueImpl(start, end, this.builtIns.getUnitType(), irValueSymbol, deserializeExpression(value), proto.hasOriginName() ? deserializeIrStatementOrigin(proto.getOriginName()) : null);
    }

    private final IrSpreadElement deserializeSpreadElement(org.jetbrains.kotlin.backend.common.serialization.proto.IrSpreadElement proto) {
        IrExpression expression = proto.getExpression();
        Intrinsics.checkNotNullExpressionValue(expression, "getExpression(...)");
        org.jetbrains.kotlin.ir.expressions.IrExpression irExpressionDeserializeExpression = deserializeExpression(expression);
        long jM6180decodeViYoqTc = BinaryCoordinates.INSTANCE.m6180decodeViYoqTc(proto.getCoordinates());
        return new IrSpreadElementImpl(BinaryCoordinates.m6176getStartOffsetimpl(jM6180decodeViYoqTc), BinaryCoordinates.m6175getEndOffsetimpl(jM6180decodeViYoqTc), irExpressionDeserializeExpression);
    }

    private final IrStringConcatenation deserializeStringConcat(IrStringConcat proto, int start, int end, IrType type) {
        List<IrExpression> argumentList = proto.getArgumentList();
        ArrayList arrayList = new ArrayList();
        Intrinsics.checkNotNull(argumentList);
        for (IrExpression irExpression : argumentList) {
            Intrinsics.checkNotNull(irExpression);
            arrayList.add(deserializeExpression(irExpression));
        }
        return new IrStringConcatenationImpl(start, end, type, arrayList);
    }

    private final IrThrowImpl deserializeThrow(IrThrow proto, int start, int end) {
        IrType nothingType = this.builtIns.getNothingType();
        IrExpression value = proto.getValue();
        Intrinsics.checkNotNullExpressionValue(value, "getValue(...)");
        return new IrThrowImpl(start, end, nothingType, deserializeExpression(value));
    }

    private final IrTryImpl deserializeTry(IrTry proto, int start, int end, IrType type) {
        org.jetbrains.kotlin.ir.expressions.IrExpression irExpressionDeserializeExpression;
        IrExpression result = proto.getResult();
        Intrinsics.checkNotNullExpressionValue(result, "getResult(...)");
        org.jetbrains.kotlin.ir.expressions.IrExpression irExpressionDeserializeExpression2 = deserializeExpression(result);
        ArrayList arrayList = new ArrayList();
        List<IrStatement> catchList = proto.getCatchList();
        Intrinsics.checkNotNullExpressionValue(catchList, "getCatchList(...)");
        for (IrStatement irStatement : catchList) {
            Intrinsics.checkNotNull(irStatement);
            IrElement irElementDeserializeStatement$ir_serialization_common = deserializeStatement$ir_serialization_common(irStatement);
            Intrinsics.checkNotNull(irElementDeserializeStatement$ir_serialization_common, "null cannot be cast to non-null type org.jetbrains.kotlin.ir.expressions.IrCatch");
            arrayList.add((IrCatch) irElementDeserializeStatement$ir_serialization_common);
        }
        if (proto.hasFinally()) {
            IrExpression irExpression = proto.getFinally();
            Intrinsics.checkNotNullExpressionValue(irExpression, "getFinally(...)");
            irExpressionDeserializeExpression = deserializeExpression(irExpression);
        } else {
            irExpressionDeserializeExpression = null;
        }
        return new IrTryImpl(start, end, type, irExpressionDeserializeExpression2, arrayList, irExpressionDeserializeExpression);
    }

    private final org.jetbrains.kotlin.ir.expressions.IrTypeOperator deserializeTypeOperator(IrTypeOperator operator) {
        switch (WhenMappings.$EnumSwitchMapping$2[operator.ordinal()]) {
            case 1:
                return org.jetbrains.kotlin.ir.expressions.IrTypeOperator.CAST;
            case 2:
                return org.jetbrains.kotlin.ir.expressions.IrTypeOperator.IMPLICIT_CAST;
            case 3:
                return org.jetbrains.kotlin.ir.expressions.IrTypeOperator.IMPLICIT_NOTNULL;
            case 4:
                return org.jetbrains.kotlin.ir.expressions.IrTypeOperator.IMPLICIT_COERCION_TO_UNIT;
            case 5:
                return org.jetbrains.kotlin.ir.expressions.IrTypeOperator.IMPLICIT_INTEGER_COERCION;
            case 6:
                return org.jetbrains.kotlin.ir.expressions.IrTypeOperator.SAFE_CAST;
            case 7:
                return org.jetbrains.kotlin.ir.expressions.IrTypeOperator.INSTANCEOF;
            case 8:
                return org.jetbrains.kotlin.ir.expressions.IrTypeOperator.NOT_INSTANCEOF;
            case 9:
                return org.jetbrains.kotlin.ir.expressions.IrTypeOperator.SAM_CONVERSION;
            case 10:
                return org.jetbrains.kotlin.ir.expressions.IrTypeOperator.IMPLICIT_DYNAMIC_CAST;
            case 11:
                return org.jetbrains.kotlin.ir.expressions.IrTypeOperator.REINTERPRET_CAST;
            default:
                throw new NoWhenBranchMatchedException();
        }
    }

    private final IrTypeOperatorCall deserializeTypeOp(IrTypeOp proto, int start, int end, IrType type) {
        IrTypeOperator operator = proto.getOperator();
        Intrinsics.checkNotNullExpressionValue(operator, "getOperator(...)");
        org.jetbrains.kotlin.ir.expressions.IrTypeOperator irTypeOperatorDeserializeTypeOperator = deserializeTypeOperator(operator);
        IrType irTypeDeserializeIrType = this.declarationDeserializer.deserializeIrType(proto.getOperand());
        IrExpression argument = proto.getArgument();
        Intrinsics.checkNotNullExpressionValue(argument, "getArgument(...)");
        return new IrTypeOperatorCallImpl(start, end, type, irTypeOperatorDeserializeTypeOperator, irTypeDeserializeIrType, deserializeExpression(argument));
    }

    private final IrVararg deserializeVararg(org.jetbrains.kotlin.backend.common.serialization.proto.IrVararg proto, int start, int end, IrType type) {
        IrType irTypeDeserializeIrType = this.declarationDeserializer.deserializeIrType(proto.getElementType());
        ArrayList arrayList = new ArrayList();
        List<IrVarargElement> elementList = proto.getElementList();
        Intrinsics.checkNotNullExpressionValue(elementList, "getElementList(...)");
        for (IrVarargElement irVarargElement : elementList) {
            Intrinsics.checkNotNull(irVarargElement);
            arrayList.add(deserializeVarargElement(irVarargElement));
        }
        return new IrVarargImpl(start, end, type, irTypeDeserializeIrType, arrayList);
    }

    private final org.jetbrains.kotlin.ir.expressions.IrVarargElement deserializeVarargElement(IrVarargElement element) {
        IrVarargElement.VarargElementCase varargElementCase = element.getVarargElementCase();
        int i = varargElementCase == null ? -1 : WhenMappings.$EnumSwitchMapping$3[varargElementCase.ordinal()];
        if (i == 1) {
            IrExpression expression = element.getExpression();
            Intrinsics.checkNotNullExpressionValue(expression, "getExpression(...)");
            return deserializeExpression(expression);
        }
        if (i == 2) {
            org.jetbrains.kotlin.backend.common.serialization.proto.IrSpreadElement spreadElement = element.getSpreadElement();
            Intrinsics.checkNotNullExpressionValue(spreadElement, "getSpreadElement(...)");
            return deserializeSpreadElement(spreadElement);
        }
        throw new NotImplementedError("An operation is not implemented: Unexpected vararg element");
    }

    private final IrWhen deserializeWhen(org.jetbrains.kotlin.backend.common.serialization.proto.IrWhen proto, int start, int end, IrType type) {
        ArrayList arrayList = new ArrayList();
        IrStatementOrigin irStatementOriginDeserializeIrStatementOrigin = proto.hasOriginName() ? deserializeIrStatementOrigin(proto.getOriginName()) : null;
        List<IrStatement> branchList = proto.getBranchList();
        Intrinsics.checkNotNullExpressionValue(branchList, "getBranchList(...)");
        for (IrStatement irStatement : branchList) {
            Intrinsics.checkNotNull(irStatement);
            IrElement irElementDeserializeStatement$ir_serialization_common = deserializeStatement$ir_serialization_common(irStatement);
            Intrinsics.checkNotNull(irElementDeserializeStatement$ir_serialization_common, "null cannot be cast to non-null type org.jetbrains.kotlin.ir.expressions.IrBranch");
            arrayList.add((IrBranch) irElementDeserializeStatement$ir_serialization_common);
        }
        return new IrWhenImpl(start, end, type, irStatementOriginDeserializeIrStatementOrigin, arrayList);
    }

    private final IrLoop deserializeLoop(Loop proto, IrLoop loop) {
        org.jetbrains.kotlin.ir.expressions.IrExpression irExpressionDeserializeExpression = null;
        String strString = proto.hasLabel() ? this.libraryFile.string(proto.getLabel()) : null;
        if (proto.hasBody()) {
            IrExpression body = proto.getBody();
            Intrinsics.checkNotNullExpressionValue(body, "getBody(...)");
            irExpressionDeserializeExpression = deserializeExpression(body);
        }
        IrExpression condition = proto.getCondition();
        Intrinsics.checkNotNullExpressionValue(condition, "getCondition(...)");
        org.jetbrains.kotlin.ir.expressions.IrExpression irExpressionDeserializeExpression2 = deserializeExpression(condition);
        loop.setLabel(strString);
        loop.setCondition(irExpressionDeserializeExpression2);
        loop.setBody(irExpressionDeserializeExpression);
        return loop;
    }

    private final IrLoop deserializeDoWhile(final IrDoWhile proto, final int start, final int end, final IrType type) {
        Loop loop = proto.getLoop();
        Intrinsics.checkNotNullExpressionValue(loop, "getLoop(...)");
        return deserializeLoop(loop, deserializeLoopHeader(proto.getLoop().getLoopId(), new Function0<IrLoop>() { // from class: org.jetbrains.kotlin.backend.common.serialization.IrBodyDeserializer.deserializeDoWhile.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final IrLoop invoke() {
                return new IrDoWhileLoopImpl(start, end, type, proto.getLoop().hasOriginName() ? IrBodyDeserializer.this.deserializeIrStatementOrigin(proto.getLoop().getOriginName()) : null);
            }
        }));
    }

    private final IrLoop deserializeWhile(final IrWhile proto, final int start, final int end, final IrType type) {
        Loop loop = proto.getLoop();
        Intrinsics.checkNotNullExpressionValue(loop, "getLoop(...)");
        return deserializeLoop(loop, deserializeLoopHeader(proto.getLoop().getLoopId(), new Function0<IrLoop>() { // from class: org.jetbrains.kotlin.backend.common.serialization.IrBodyDeserializer.deserializeWhile.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final IrLoop invoke() {
                return new IrWhileLoopImpl(start, end, type, proto.getLoop().hasOriginName() ? IrBodyDeserializer.this.deserializeIrStatementOrigin(proto.getLoop().getOriginName()) : null);
            }
        }));
    }

    private final IrDynamicMemberExpressionImpl deserializeDynamicMemberExpression(IrDynamicMemberExpression proto, int start, int end, IrType type) {
        String strString = this.libraryFile.string(proto.getMemberName());
        IrExpression receiver = proto.getReceiver();
        Intrinsics.checkNotNullExpressionValue(receiver, "getReceiver(...)");
        return new IrDynamicMemberExpressionImpl(start, end, type, strString, deserializeExpression(receiver));
    }

    private final IrDynamicOperatorExpressionImpl deserializeDynamicOperatorExpression(IrDynamicOperatorExpression proto, int start, int end, IrType type) {
        IrDynamicOperatorExpression.IrDynamicOperator operator = proto.getOperator();
        Intrinsics.checkNotNullExpressionValue(operator, "getOperator(...)");
        IrDynamicOperatorExpressionImpl irDynamicOperatorExpressionImpl = new IrDynamicOperatorExpressionImpl(start, end, type, deserializeDynamicOperator(operator));
        IrExpression receiver = proto.getReceiver();
        Intrinsics.checkNotNullExpressionValue(receiver, "getReceiver(...)");
        irDynamicOperatorExpressionImpl.setReceiver(deserializeExpression(receiver));
        List<IrExpression> argumentList = proto.getArgumentList();
        Intrinsics.checkNotNullExpressionValue(argumentList, "getArgumentList(...)");
        List<org.jetbrains.kotlin.ir.expressions.IrExpression> arguments = irDynamicOperatorExpressionImpl.getArguments();
        for (IrExpression irExpression : argumentList) {
            Intrinsics.checkNotNull(irExpression);
            arguments.add(deserializeExpression(irExpression));
        }
        return irDynamicOperatorExpressionImpl;
    }

    private final IrDynamicOperator deserializeDynamicOperator(IrDynamicOperatorExpression.IrDynamicOperator operator) {
        switch (WhenMappings.$EnumSwitchMapping$4[operator.ordinal()]) {
            case 1:
                return IrDynamicOperator.UNARY_PLUS;
            case 2:
                return IrDynamicOperator.UNARY_MINUS;
            case 3:
                return IrDynamicOperator.EXCL;
            case 4:
                return IrDynamicOperator.PREFIX_INCREMENT;
            case 5:
                return IrDynamicOperator.PREFIX_DECREMENT;
            case 6:
                return IrDynamicOperator.POSTFIX_INCREMENT;
            case 7:
                return IrDynamicOperator.POSTFIX_DECREMENT;
            case 8:
                return IrDynamicOperator.BINARY_PLUS;
            case 9:
                return IrDynamicOperator.BINARY_MINUS;
            case 10:
                return IrDynamicOperator.MUL;
            case 11:
                return IrDynamicOperator.DIV;
            case 12:
                return IrDynamicOperator.MOD;
            case 13:
                return IrDynamicOperator.GT;
            case 14:
                return IrDynamicOperator.LT;
            case 15:
                return IrDynamicOperator.GE;
            case 16:
                return IrDynamicOperator.LE;
            case 17:
                return IrDynamicOperator.EQEQ;
            case 18:
                return IrDynamicOperator.EXCLEQ;
            case 19:
                return IrDynamicOperator.EQEQEQ;
            case 20:
                return IrDynamicOperator.EXCLEQEQ;
            case 21:
                return IrDynamicOperator.ANDAND;
            case 22:
                return IrDynamicOperator.OROR;
            case 23:
                return IrDynamicOperator.EQ;
            case 24:
                return IrDynamicOperator.PLUSEQ;
            case 25:
                return IrDynamicOperator.MINUSEQ;
            case 26:
                return IrDynamicOperator.MULEQ;
            case 27:
                return IrDynamicOperator.DIVEQ;
            case 28:
                return IrDynamicOperator.MODEQ;
            case 29:
                return IrDynamicOperator.ARRAY_ACCESS;
            case 30:
                return IrDynamicOperator.INVOKE;
            default:
                throw new NoWhenBranchMatchedException();
        }
    }

    private final IrBreak deserializeBreak(org.jetbrains.kotlin.backend.common.serialization.proto.IrBreak proto, int start, int end, IrType type) {
        String strString = proto.hasLabel() ? this.libraryFile.string(proto.getLabel()) : null;
        IrBreakImpl irBreakImpl = new IrBreakImpl(start, end, type, deserializeLoopHeader(proto.getLoopId(), new Function0<IrLoop>() { // from class: org.jetbrains.kotlin.backend.common.serialization.IrBodyDeserializer$deserializeBreak$loop$1
            @Override // kotlin.jvm.functions.Function0
            public final IrLoop invoke() {
                throw new IllegalStateException("break clause before loop header".toString());
            }
        }));
        irBreakImpl.setLabel(strString);
        return irBreakImpl;
    }

    private final IrContinue deserializeContinue(org.jetbrains.kotlin.backend.common.serialization.proto.IrContinue proto, int start, int end, IrType type) {
        String strString = proto.hasLabel() ? this.libraryFile.string(proto.getLabel()) : null;
        IrContinueImpl irContinueImpl = new IrContinueImpl(start, end, type, deserializeLoopHeader(proto.getLoopId(), new Function0<IrLoop>() { // from class: org.jetbrains.kotlin.backend.common.serialization.IrBodyDeserializer$deserializeContinue$loop$1
            @Override // kotlin.jvm.functions.Function0
            public final IrLoop invoke() {
                throw new IllegalStateException("break clause before loop header".toString());
            }
        }));
        irContinueImpl.setLabel(strString);
        return irContinueImpl;
    }

    private final org.jetbrains.kotlin.ir.expressions.IrExpression deserializeConst(IrConst proto, int start, int end, IrType type) {
        IrConst.ValueCase valueCase = proto.getValueCase();
        Intrinsics.checkNotNull(valueCase);
        switch (WhenMappings.$EnumSwitchMapping$5[valueCase.ordinal()]) {
            case 1:
                return IrConstImpl.INSTANCE.constNull(start, end, type);
            case 2:
                return IrConstImpl.INSTANCE.m7206boolean(start, end, type, proto.getBoolean());
            case 3:
                return IrConstImpl.INSTANCE.m7207byte(start, end, type, (byte) proto.getByte());
            case 4:
                return IrConstImpl.INSTANCE.m7208char(start, end, type, (char) proto.getChar());
            case 5:
                return IrConstImpl.INSTANCE.m7213short(start, end, type, (short) proto.getShort());
            case 6:
                return IrConstImpl.INSTANCE.m7211int(start, end, type, proto.getInt());
            case 7:
                return IrConstImpl.INSTANCE.m7212long(start, end, type, proto.getLong());
            case 8:
                return IrConstImpl.INSTANCE.string(start, end, type, this.libraryFile.string(proto.getString()));
            case 9:
                IrConstImpl.Companion companion = IrConstImpl.INSTANCE;
                FloatCompanionObject floatCompanionObject = FloatCompanionObject.INSTANCE;
                return companion.m7210float(start, end, type, Float.intBitsToFloat(proto.getFloatBits()));
            case 10:
                IrConstImpl.Companion companion2 = IrConstImpl.INSTANCE;
                DoubleCompanionObject doubleCompanionObject = DoubleCompanionObject.INSTANCE;
                return companion2.m7209double(start, end, type, Double.longBitsToDouble(proto.getDoubleBits()));
            case 11:
                throw new IllegalStateException(("Const deserialization error: " + proto.getValueCase() + StringUtil.SPACE).toString());
            default:
                throw new NoWhenBranchMatchedException();
        }
    }

    private final org.jetbrains.kotlin.ir.expressions.IrExpression deserializeOperation(IrOperation proto, int start, int end, IrType type) {
        IrOperation.OperationCase operationCase = proto.getOperationCase();
        Intrinsics.checkNotNull(operationCase);
        switch (WhenMappings.$EnumSwitchMapping$6[operationCase.ordinal()]) {
            case 1:
                org.jetbrains.kotlin.backend.common.serialization.proto.IrBlock block = proto.getBlock();
                Intrinsics.checkNotNullExpressionValue(block, "getBlock(...)");
                return deserializeBlock(block, start, end, type);
            case 2:
                org.jetbrains.kotlin.backend.common.serialization.proto.IrBreak irBreak = proto.getBreak();
                Intrinsics.checkNotNullExpressionValue(irBreak, "getBreak(...)");
                return deserializeBreak(irBreak, start, end, type);
            case 3:
                org.jetbrains.kotlin.backend.common.serialization.proto.IrClassReference classReference = proto.getClassReference();
                Intrinsics.checkNotNullExpressionValue(classReference, "getClassReference(...)");
                return deserializeClassReference(classReference, start, end, type);
            case 4:
                org.jetbrains.kotlin.backend.common.serialization.proto.IrCall call = proto.getCall();
                Intrinsics.checkNotNullExpressionValue(call, "getCall(...)");
                return deserializeCall(call, start, end, type);
            case 5:
                org.jetbrains.kotlin.backend.common.serialization.proto.IrComposite composite = proto.getComposite();
                Intrinsics.checkNotNullExpressionValue(composite, "getComposite(...)");
                return deserializeComposite(composite, start, end, type);
            case 6:
                IrConst irConst = proto.getConst();
                Intrinsics.checkNotNullExpressionValue(irConst, "getConst(...)");
                return deserializeConst(irConst, start, end, type);
            case 7:
                org.jetbrains.kotlin.backend.common.serialization.proto.IrContinue irContinue = proto.getContinue();
                Intrinsics.checkNotNullExpressionValue(irContinue, "getContinue(...)");
                return deserializeContinue(irContinue, start, end, type);
            case 8:
                org.jetbrains.kotlin.backend.common.serialization.proto.IrDelegatingConstructorCall delegatingConstructorCall = proto.getDelegatingConstructorCall();
                Intrinsics.checkNotNullExpressionValue(delegatingConstructorCall, "getDelegatingConstructorCall(...)");
                return deserializeDelegatingConstructorCall(delegatingConstructorCall, start, end);
            case 9:
                IrDoWhile doWhile = proto.getDoWhile();
                Intrinsics.checkNotNullExpressionValue(doWhile, "getDoWhile(...)");
                return deserializeDoWhile(doWhile, start, end, type);
            case 10:
                org.jetbrains.kotlin.backend.common.serialization.proto.IrEnumConstructorCall enumConstructorCall = proto.getEnumConstructorCall();
                Intrinsics.checkNotNullExpressionValue(enumConstructorCall, "getEnumConstructorCall(...)");
                return deserializeEnumConstructorCall(enumConstructorCall, start, end);
            case 11:
                org.jetbrains.kotlin.backend.common.serialization.proto.IrFunctionReference functionReference = proto.getFunctionReference();
                Intrinsics.checkNotNullExpressionValue(functionReference, "getFunctionReference(...)");
                return deserializeFunctionReference(functionReference, start, end, type);
            case 12:
                org.jetbrains.kotlin.backend.common.serialization.proto.IrGetEnumValue getEnumValue = proto.getGetEnumValue();
                Intrinsics.checkNotNullExpressionValue(getEnumValue, "getGetEnumValue(...)");
                return deserializeGetEnumValue(getEnumValue, start, end, type);
            case 13:
                org.jetbrains.kotlin.backend.common.serialization.proto.IrGetClass getClass = proto.getGetClass();
                Intrinsics.checkNotNullExpressionValue(getClass, "getGetClass(...)");
                return deserializeGetClass(getClass, start, end, type);
            case 14:
                org.jetbrains.kotlin.backend.common.serialization.proto.IrGetField getField = proto.getGetField();
                Intrinsics.checkNotNullExpressionValue(getField, "getGetField(...)");
                return deserializeGetField(getField, start, end, type);
            case 15:
                IrGetObject getObject = proto.getGetObject();
                Intrinsics.checkNotNullExpressionValue(getObject, "getGetObject(...)");
                return deserializeGetObject(getObject, start, end, type);
            case 16:
                org.jetbrains.kotlin.backend.common.serialization.proto.IrGetValue getValue = proto.getGetValue();
                Intrinsics.checkNotNullExpressionValue(getValue, "getGetValue(...)");
                return deserializeGetValue(getValue, start, end, type);
            case 17:
                org.jetbrains.kotlin.backend.common.serialization.proto.IrLocalDelegatedPropertyReference localDelegatedPropertyReference = proto.getLocalDelegatedPropertyReference();
                Intrinsics.checkNotNullExpressionValue(localDelegatedPropertyReference, "getLocalDelegatedPropertyReference(...)");
                return deserializeIrLocalDelegatedPropertyReference(localDelegatedPropertyReference, start, end, type);
            case 18:
                org.jetbrains.kotlin.backend.common.serialization.proto.IrInstanceInitializerCall instanceInitializerCall = proto.getInstanceInitializerCall();
                Intrinsics.checkNotNullExpressionValue(instanceInitializerCall, "getInstanceInitializerCall(...)");
                return deserializeInstanceInitializerCall(instanceInitializerCall, start, end);
            case 19:
                org.jetbrains.kotlin.backend.common.serialization.proto.IrPropertyReference propertyReference = proto.getPropertyReference();
                Intrinsics.checkNotNullExpressionValue(propertyReference, "getPropertyReference(...)");
                return deserializePropertyReference(propertyReference, start, end, type);
            case 20:
                org.jetbrains.kotlin.backend.common.serialization.proto.IrReturn irReturn = proto.getReturn();
                Intrinsics.checkNotNullExpressionValue(irReturn, "getReturn(...)");
                return deserializeReturn(irReturn, start, end);
            case 21:
                org.jetbrains.kotlin.backend.common.serialization.proto.IrSetField setField = proto.getSetField();
                Intrinsics.checkNotNullExpressionValue(setField, "getSetField(...)");
                return deserializeSetField(setField, start, end);
            case 22:
                org.jetbrains.kotlin.backend.common.serialization.proto.IrSetValue setValue = proto.getSetValue();
                Intrinsics.checkNotNullExpressionValue(setValue, "getSetValue(...)");
                return deserializeSetValue(setValue, start, end);
            case 23:
                IrStringConcat stringConcat = proto.getStringConcat();
                Intrinsics.checkNotNullExpressionValue(stringConcat, "getStringConcat(...)");
                return deserializeStringConcat(stringConcat, start, end, type);
            case 24:
                IrThrow irThrow = proto.getThrow();
                Intrinsics.checkNotNullExpressionValue(irThrow, "getThrow(...)");
                return deserializeThrow(irThrow, start, end);
            case 25:
                IrTry irTry = proto.getTry();
                Intrinsics.checkNotNullExpressionValue(irTry, "getTry(...)");
                return deserializeTry(irTry, start, end, type);
            case 26:
                IrTypeOp typeOp = proto.getTypeOp();
                Intrinsics.checkNotNullExpressionValue(typeOp, "getTypeOp(...)");
                return deserializeTypeOp(typeOp, start, end, type);
            case 27:
                org.jetbrains.kotlin.backend.common.serialization.proto.IrVararg vararg = proto.getVararg();
                Intrinsics.checkNotNullExpressionValue(vararg, "getVararg(...)");
                return deserializeVararg(vararg, start, end, type);
            case 28:
                org.jetbrains.kotlin.backend.common.serialization.proto.IrWhen when = proto.getWhen();
                Intrinsics.checkNotNullExpressionValue(when, "getWhen(...)");
                return deserializeWhen(when, start, end, type);
            case 29:
                IrWhile irWhile = proto.getWhile();
                Intrinsics.checkNotNullExpressionValue(irWhile, "getWhile(...)");
                return deserializeWhile(irWhile, start, end, type);
            case 30:
                IrDynamicMemberExpression dynamicMember = proto.getDynamicMember();
                Intrinsics.checkNotNullExpressionValue(dynamicMember, "getDynamicMember(...)");
                return deserializeDynamicMemberExpression(dynamicMember, start, end, type);
            case 31:
                IrDynamicOperatorExpression dynamicOperator = proto.getDynamicOperator();
                Intrinsics.checkNotNullExpressionValue(dynamicOperator, "getDynamicOperator(...)");
                return deserializeDynamicOperatorExpression(dynamicOperator, start, end, type);
            case 32:
                org.jetbrains.kotlin.backend.common.serialization.proto.IrConstructorCall constructorCall = proto.getConstructorCall();
                Intrinsics.checkNotNullExpressionValue(constructorCall, "getConstructorCall(...)");
                return deserializeConstructorCall(constructorCall, start, end, type);
            case 33:
                IrFunctionExpression functionExpression = proto.getFunctionExpression();
                Intrinsics.checkNotNullExpressionValue(functionExpression, "getFunctionExpression(...)");
                return deserializeFunctionExpression(functionExpression, start, end, type);
            case 34:
                org.jetbrains.kotlin.backend.common.serialization.proto.IrErrorExpression errorExpression = proto.getErrorExpression();
                Intrinsics.checkNotNullExpressionValue(errorExpression, "getErrorExpression(...)");
                return deserializeErrorExpression(errorExpression, start, end, type);
            case 35:
                org.jetbrains.kotlin.backend.common.serialization.proto.IrErrorCallExpression errorCallExpression = proto.getErrorCallExpression();
                Intrinsics.checkNotNullExpressionValue(errorCallExpression, "getErrorCallExpression(...)");
                return deserializeErrorCallExpression(errorCallExpression, start, end, type);
            case 36:
                throw new IllegalStateException(("Expression deserialization not implemented: " + proto.getOperationCase()).toString());
            default:
                throw new NoWhenBranchMatchedException();
        }
    }

    public final org.jetbrains.kotlin.ir.expressions.IrExpression deserializeExpression(IrExpression proto) {
        Intrinsics.checkNotNullParameter(proto, "proto");
        long jM6180decodeViYoqTc = BinaryCoordinates.INSTANCE.m6180decodeViYoqTc(proto.getCoordinates());
        int iM6176getStartOffsetimpl = BinaryCoordinates.m6176getStartOffsetimpl(jM6180decodeViYoqTc);
        int iM6175getEndOffsetimpl = BinaryCoordinates.m6175getEndOffsetimpl(jM6180decodeViYoqTc);
        IrType irTypeDeserializeIrType = this.declarationDeserializer.deserializeIrType(proto.getType());
        IrOperation operation = proto.getOperation();
        Intrinsics.checkNotNull(operation);
        return deserializeOperation(operation, iM6176getStartOffsetimpl, iM6175getEndOffsetimpl, irTypeDeserializeIrType);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final IrStatementOrigin deserializeIrStatementOrigin(int protoName) {
        String strString = this.libraryFile.string(protoName);
        if (StringsKt.startsWith$default(strString, "COMPONENT_", false, 2, (Object) null)) {
            return IrStatementOrigin.COMPONENT_N.INSTANCE.withIndex(Integer.parseInt(StringsKt.removePrefix(strString, (CharSequence) "COMPONENT_")));
        }
        IrStatementOriginImpl irStatementOriginImpl = statementOriginIndex.get(strString);
        if (irStatementOriginImpl != null) {
            return irStatementOriginImpl;
        }
        throw new IllegalStateException(("Unexpected statement origin: " + strString).toString());
    }

    static {
        List<KClass<?>> list = kotlin.collections.CollectionsKt.toList(Reflection.getOrCreateKotlinClass(IrStatementOrigin.class).getNestedClasses());
        allKnownStatementOrigins = list;
        ArrayList arrayList = new ArrayList();
        Iterator<T> it = list.iterator();
        while (it.hasNext()) {
            Object objectInstance = ((KClass) it.next()).getObjectInstance();
            IrStatementOriginImpl irStatementOriginImpl = objectInstance instanceof IrStatementOriginImpl ? (IrStatementOriginImpl) objectInstance : null;
            if (irStatementOriginImpl != null) {
                arrayList.add(irStatementOriginImpl);
            }
        }
        ArrayList arrayList2 = arrayList;
        LinkedHashMap linkedHashMap = new LinkedHashMap(RangesKt.coerceAtLeast(MapsKt.mapCapacity(kotlin.collections.CollectionsKt.collectionSizeOrDefault(arrayList2, 10)), 16));
        for (Object obj : arrayList2) {
            linkedHashMap.put(((IrStatementOriginImpl) obj).getDebugName(), obj);
        }
        statementOriginIndex = linkedHashMap;
    }
}
