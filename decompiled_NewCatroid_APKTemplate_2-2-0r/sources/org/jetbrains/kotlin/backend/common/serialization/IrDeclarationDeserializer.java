package org.jetbrains.kotlin.backend.common.serialization;

import androidx.exifinterface.media.ExifInterface;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.Pair;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Ref;
import kotlin.jvm.internal.Reflection;
import kotlin.ranges.RangesKt;
import kotlin.reflect.KClass;
import org.catrobat.catroid.web.ServerAuthenticationConstants;
import org.jetbrains.kotlin.backend.common.linkage.issues.IrDisallowedErrorNode;
import org.jetbrains.kotlin.backend.common.linkage.issues.IrSymbolTypeMismatchException;
import org.jetbrains.kotlin.backend.common.overrides.FakeOverrideBuilder;
import org.jetbrains.kotlin.backend.common.overrides.FakeOverrideClassFilter;
import org.jetbrains.kotlin.backend.common.serialization.encodings.BinaryCoordinates;
import org.jetbrains.kotlin.backend.common.serialization.encodings.BinaryNameAndType;
import org.jetbrains.kotlin.backend.common.serialization.encodings.BinarySymbolData;
import org.jetbrains.kotlin.backend.common.serialization.encodings.BinaryTypeProjection;
import org.jetbrains.kotlin.backend.common.serialization.encodings.ClassFlags;
import org.jetbrains.kotlin.backend.common.serialization.encodings.FieldFlags;
import org.jetbrains.kotlin.backend.common.serialization.encodings.FunctionFlags;
import org.jetbrains.kotlin.backend.common.serialization.encodings.LocalVariableFlags;
import org.jetbrains.kotlin.backend.common.serialization.encodings.PropertyFlags;
import org.jetbrains.kotlin.backend.common.serialization.encodings.TypeAliasFlags;
import org.jetbrains.kotlin.backend.common.serialization.encodings.TypeParameterFlags;
import org.jetbrains.kotlin.backend.common.serialization.encodings.ValueParameterFlags;
import org.jetbrains.kotlin.backend.common.serialization.proto.IrAnonymousInit;
import org.jetbrains.kotlin.backend.common.serialization.proto.IrDeclaration;
import org.jetbrains.kotlin.backend.common.serialization.proto.IrDeclarationBase;
import org.jetbrains.kotlin.backend.common.serialization.proto.IrDefinitelyNotNullType;
import org.jetbrains.kotlin.backend.common.serialization.proto.IrExpression;
import org.jetbrains.kotlin.backend.common.serialization.proto.IrFunctionBase;
import org.jetbrains.kotlin.backend.common.serialization.proto.IrInlineClassRepresentation;
import org.jetbrains.kotlin.backend.common.serialization.proto.IrMultiFieldValueClassRepresentation;
import org.jetbrains.kotlin.backend.common.serialization.proto.IrSimpleTypeLegacy;
import org.jetbrains.kotlin.backend.common.serialization.proto.IrSimpleTypeNullability;
import org.jetbrains.kotlin.backend.common.serialization.proto.IrStatement;
import org.jetbrains.kotlin.descriptors.ClassKind;
import org.jetbrains.kotlin.descriptors.InlineClassRepresentation;
import org.jetbrains.kotlin.descriptors.Modality;
import org.jetbrains.kotlin.descriptors.MultiFieldValueClassRepresentation;
import org.jetbrains.kotlin.descriptors.SourceElement;
import org.jetbrains.kotlin.ir.IrBuiltIns;
import org.jetbrains.kotlin.ir.IrElement;
import org.jetbrains.kotlin.ir.declarations.IrAnonymousInitializer;
import org.jetbrains.kotlin.ir.declarations.IrClass;
import org.jetbrains.kotlin.ir.declarations.IrConstructor;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationOrigin;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationOriginImpl;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationParent;
import org.jetbrains.kotlin.ir.declarations.IrEnumEntry;
import org.jetbrains.kotlin.ir.declarations.IrErrorDeclaration;
import org.jetbrains.kotlin.ir.declarations.IrFactory;
import org.jetbrains.kotlin.ir.declarations.IrFactoryHelpersKt;
import org.jetbrains.kotlin.ir.declarations.IrField;
import org.jetbrains.kotlin.ir.declarations.IrFunction;
import org.jetbrains.kotlin.ir.declarations.IrLocalDelegatedProperty;
import org.jetbrains.kotlin.ir.declarations.IrProperty;
import org.jetbrains.kotlin.ir.declarations.IrSimpleFunction;
import org.jetbrains.kotlin.ir.declarations.IrTypeAlias;
import org.jetbrains.kotlin.ir.declarations.IrTypeParameter;
import org.jetbrains.kotlin.ir.declarations.IrValueParameter;
import org.jetbrains.kotlin.ir.declarations.IrVariable;
import org.jetbrains.kotlin.ir.declarations.impl.IrVariableImpl;
import org.jetbrains.kotlin.ir.expressions.IrBlockBody;
import org.jetbrains.kotlin.ir.expressions.IrConstructorCall;
import org.jetbrains.kotlin.ir.expressions.IrExpressionBody;
import org.jetbrains.kotlin.ir.expressions.impl.IrCompositeImpl;
import org.jetbrains.kotlin.ir.symbols.IrAnonymousInitializerSymbol;
import org.jetbrains.kotlin.ir.symbols.IrClassSymbol;
import org.jetbrains.kotlin.ir.symbols.IrClassifierSymbol;
import org.jetbrains.kotlin.ir.symbols.IrConstructorSymbol;
import org.jetbrains.kotlin.ir.symbols.IrDelegatingSymbol;
import org.jetbrains.kotlin.ir.symbols.IrEnumEntrySymbol;
import org.jetbrains.kotlin.ir.symbols.IrFieldSymbol;
import org.jetbrains.kotlin.ir.symbols.IrFunctionSymbol;
import org.jetbrains.kotlin.ir.symbols.IrLocalDelegatedPropertySymbol;
import org.jetbrains.kotlin.ir.symbols.IrPropertySymbol;
import org.jetbrains.kotlin.ir.symbols.IrSimpleFunctionSymbol;
import org.jetbrains.kotlin.ir.symbols.IrSymbol;
import org.jetbrains.kotlin.ir.symbols.IrTypeAliasSymbol;
import org.jetbrains.kotlin.ir.symbols.IrTypeParameterSymbol;
import org.jetbrains.kotlin.ir.symbols.IrValueParameterSymbol;
import org.jetbrains.kotlin.ir.symbols.IrVariableSymbol;
import org.jetbrains.kotlin.ir.symbols.impl.IrPublicSymbolBase;
import org.jetbrains.kotlin.ir.symbols.impl.IrTypeParameterSymbolImpl;
import org.jetbrains.kotlin.ir.types.IrDynamicType;
import org.jetbrains.kotlin.ir.types.IrErrorType;
import org.jetbrains.kotlin.ir.types.IrSimpleType;
import org.jetbrains.kotlin.ir.types.IrType;
import org.jetbrains.kotlin.ir.types.IrTypeAbbreviation;
import org.jetbrains.kotlin.ir.types.IrTypeArgument;
import org.jetbrains.kotlin.ir.types.IrTypesKt;
import org.jetbrains.kotlin.ir.types.SimpleTypeNullability;
import org.jetbrains.kotlin.ir.types.impl.IrDynamicTypeImpl;
import org.jetbrains.kotlin.ir.types.impl.IrErrorTypeImpl;
import org.jetbrains.kotlin.ir.types.impl.IrSimpleTypeImpl;
import org.jetbrains.kotlin.ir.types.impl.IrSimpleTypeImplKt;
import org.jetbrains.kotlin.ir.types.impl.IrStarProjectionImpl;
import org.jetbrains.kotlin.ir.types.impl.IrTypeAbbreviationImpl;
import org.jetbrains.kotlin.ir.types.impl.IrUninitializedType;
import org.jetbrains.kotlin.ir.util.DumpIrTreeOptions;
import org.jetbrains.kotlin.ir.util.IdSignature;
import org.jetbrains.kotlin.ir.util.IrUtilsKt;
import org.jetbrains.kotlin.ir.util.ReferenceSymbolTable;
import org.jetbrains.kotlin.ir.util.RenderIrElementKt;
import org.jetbrains.kotlin.ir.util.SymbolTable;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.serialization.deserialization.descriptors.DeserializedContainerSource;
import org.jetbrains.kotlin.types.Variance;
import org.jetbrains.kotlin.utils.MemoryOptimizedCollectionUtilKt;
import org.mozilla.javascript.NativeSymbol;

/* JADX INFO: compiled from: IrDeclarationDeserializer.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000\u008e\u0004\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\t\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\b\u0018\u0000 Ä\u00012\u00020\u0001:\u0002Ä\u0001Bu\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\t\u0012\u0006\u0010\n\u001a\u00020\u000b\u0012\u0006\u0010\f\u001a\u00020\r\u0012\u0006\u0010\u000e\u001a\u00020\r\u0012\u0006\u0010\u000f\u001a\u00020\r\u0012\u0006\u0010\u0010\u001a\u00020\u0011\u0012\u0006\u0010\u0012\u001a\u00020\u0013\u0012\u0006\u0010\u0014\u001a\u00020\u0015\u0012\u0006\u0010\u0016\u001a\u00020\u0017\u0012\u0006\u0010\u0018\u001a\u00020\r\u0012\u0006\u0010\u0019\u001a\u00020\u001a¢\u0006\u0002\u0010\u001bJ\u0016\u0010/\u001a\b\u0012\u0004\u0012\u000201002\u0006\u00102\u001a\u000203H\u0002J!\u00104\u001a\b\u0012\u0004\u0012\u000206052\f\u00107\u001a\b\u0012\u0004\u0012\u00020805H\u0000¢\u0006\u0002\b9J\u0018\u0010:\u001a\u00020;2\u0006\u0010<\u001a\u00020=2\b\b\u0002\u0010>\u001a\u00020\rJ\u0010\u0010?\u001a\u0002012\u0006\u0010<\u001a\u00020@H\u0002J\u0010\u0010A\u001a\u00020B2\u0006\u0010<\u001a\u00020CH\u0002J\u001a\u0010D\u001a\u00020E2\u0006\u0010<\u001a\u00020F2\b\b\u0002\u0010>\u001a\u00020\rH\u0002J\u0010\u0010G\u001a\u00020H2\u0006\u0010<\u001a\u00020IH\u0002J\u0010\u0010J\u001a\u0004\u0018\u00010K2\u0006\u0010L\u001a\u00020*J\u0016\u0010M\u001a\b\u0012\u0004\u0012\u000201002\u0006\u0010<\u001a\u00020NH\u0002J\u001a\u0010O\u001a\u00020P2\u0006\u0010<\u001a\u00020Q2\b\b\u0002\u0010>\u001a\u00020\rH\u0002J\u001a\u0010R\u001a\u0002032\u0006\u0010<\u001a\u00020S2\b\b\u0002\u0010>\u001a\u00020\rH\u0002J\u001a\u0010T\u001a\u00020U2\u0006\u0010<\u001a\u00020V2\b\b\u0002\u0010>\u001a\u00020\rH\u0002J\u0010\u0010W\u001a\u00020X2\u0006\u0010Y\u001a\u00020*H\u0002J\u001a\u0010Z\u001a\u00020[2\u0006\u0010<\u001a\u00020\\2\b\b\u0002\u0010>\u001a\u00020\rH\u0002J\"\u0010]\u001a\u00020^2\u0006\u0010<\u001a\u00020_2\u0006\u0010`\u001a\u00020\r2\b\b\u0002\u0010>\u001a\u00020\rH\u0002J\u001f\u0010a\u001a\u00020b2\u0006\u0010<\u001a\u00020c2\b\b\u0002\u0010>\u001a\u00020\rH\u0000¢\u0006\u0002\bdJ\u001a\u0010e\u001a\u00020f2\u0006\u0010<\u001a\u00020g2\b\b\u0002\u0010>\u001a\u00020\rH\u0002J\u001a\u0010h\u001a\u00020i2\u0006\u0010<\u001a\u00020j2\b\b\u0002\u0010>\u001a\u00020\rH\u0002J\u0015\u0010k\u001a\u00020%2\u0006\u0010l\u001a\u00020mH\u0000¢\u0006\u0002\bnJ\u0015\u0010o\u001a\u00020%2\u0006\u0010l\u001a\u00020mH\u0000¢\u0006\u0002\bpJ\u000e\u0010q\u001a\u00020+2\u0006\u0010L\u001a\u00020*J\u001a\u0010r\u001a\u00020s2\u0006\u0010<\u001a\u00020t2\b\b\u0002\u0010>\u001a\u00020\rH\u0002J\u0010\u0010u\u001a\u00020v2\u0006\u0010<\u001a\u00020mH\u0002J\u0010\u0010w\u001a\u00020+2\u0006\u0010<\u001a\u00020xH\u0002J*\u0010y\u001a\u00020z2\u0006\u0010<\u001a\u00020{2\u0006\u0010L\u001a\u00020*2\u0006\u0010|\u001a\u00020\r2\b\b\u0002\u0010>\u001a\u00020\rH\u0002J\"\u0010}\u001a\u00020~2\u0006\u0010<\u001a\u00020\u007f2\u0006\u0010L\u001a\u00020*2\b\b\u0002\u0010>\u001a\u00020\rH\u0002J\u001b\u0010\u0080\u0001\u001a\u00030\u0081\u00012\u0007\u0010<\u001a\u00030\u0082\u00012\b\b\u0002\u0010>\u001a\u00020\rJ\u0012\u0010\u0083\u0001\u001a\u0002012\u0007\u0010<\u001a\u00030\u0084\u0001H\u0002J\u0019\u0010\u0085\u0001\u001a\t\u0012\u0004\u0012\u0002010\u0086\u00012\u0007\u0010<\u001a\u00030\u0087\u0001H\u0002J\u0012\u0010\u0088\u0001\u001a\u00030\u0089\u00012\u0006\u0010L\u001a\u00020*H\u0002J\u0011\u0010\u008a\u0001\u001a\u0004\u0018\u00010+2\u0006\u0010L\u001a\u00020*J\u0012\u0010\u008b\u0001\u001a\u0002012\u0007\u0010<\u001a\u00030\u008c\u0001H\u0002J\u0013\u0010\u008d\u0001\u001a\u00030\u008e\u00012\u0007\u0010<\u001a\u00030\u008f\u0001H\u0002J\u0012\u0010\u0090\u0001\u001a\u0005\u0018\u00010\u0091\u00012\u0006\u0010L\u001a\u00020*J\u0012\u0010\u0092\u0001\u001a\u00030\u0093\u00012\u0006\u0010L\u001a\u00020*H\u0002J\u0013\u0010\u0094\u0001\u001a\u00030\u0095\u00012\u0007\u0010<\u001a\u00030\u0096\u0001H\u0002J&\u0010\u0097\u0001\u001a\b\u0012\u0004\u0012\u00020z052\r\u0010\u0098\u0001\u001a\b\u0012\u0004\u0012\u00020{052\u0006\u0010|\u001a\u00020\rH\u0002J\u001e\u0010\u0099\u0001\u001a\b\u0012\u0004\u0012\u00020~052\r\u0010\u0098\u0001\u001a\b\u0012\u0004\u0012\u00020\u007f05H\u0002J\u0013\u0010\u009a\u0001\u001a\u00030\u009b\u00012\u0007\u0010\u009c\u0001\u001a\u00020%H\u0002J\u0019\u0010\u009d\u0001\u001a\u00020\r2\u0006\u0010<\u001a\u00020=2\u0006\u0010\n\u001a\u000203H\u0002J\u0012\u0010\u009e\u0001\u001a\u00030\u009f\u00012\u0006\u0010L\u001a\u00020*H\u0002J\u0012\u0010 \u0001\u001a\u00030¡\u00012\u0006\u0010L\u001a\u00020*H\u0002J\u0013\u0010¢\u0001\u001a\u00030\u009b\u00012\u0007\u0010\u009c\u0001\u001a\u00020%H\u0002Jj\u0010£\u0001\u001a\u0003H¤\u0001\"\u000e\b\u0000\u0010¤\u0001*\u00020;*\u00030¥\u00012\u0007\u0010<\u001a\u00030¦\u00012\b\b\u0002\u0010>\u001a\u00020\r24\u0010§\u0001\u001a/\u0012\u0004\u0012\u00020%\u0012\u0005\u0012\u00030©\u0001\u0012\u0004\u0012\u00020*\u0012\u0004\u0012\u00020*\u0012\u0004\u0012\u00020X\u0012\u0004\u0012\u00020m\u0012\u0005\u0012\u0003H¤\u00010¨\u0001H\u0082\b¢\u0006\u0003\u0010ª\u0001J\u007f\u0010«\u0001\u001a\u0003H¤\u0001\"\f\b\u0000\u0010¬\u0001\u0018\u0001*\u00030\u00ad\u0001\"\n\b\u0001\u0010¤\u0001*\u00030®\u00012\u0007\u0010<\u001a\u00030¯\u00012\b\b\u0002\u0010>\u001a\u00020\r2\b\u0010°\u0001\u001a\u00030±\u000125\u0010§\u0001\u001a0\u0012\u0005\u0012\u0003H¬\u0001\u0012\u0005\u0012\u00030©\u0001\u0012\u0004\u0012\u00020*\u0012\u0004\u0012\u00020*\u0012\u0004\u0012\u00020X\u0012\u0004\u0012\u00020m\u0012\u0005\u0012\u0003H¤\u00010¨\u0001H\u0082\b¢\u0006\u0003\u0010²\u0001J%\u0010³\u0001\u001a\u00030\u009b\u00012\u0007\u0010´\u0001\u001a\u00020\r2\u000f\u0010µ\u0001\u001a\n\u0012\u0005\u0012\u00030\u009b\u00010¶\u0001H\u0082\bJ\r\u0010·\u0001\u001a\u00020\r*\u00020+H\u0002J1\u0010¸\u0001\u001a\u0003H¬\u0001\"\u000b\b\u0000\u0010¬\u0001\u0018\u0001*\u00020%*\u00020%2\n\u0010°\u0001\u001a\u0005\u0018\u00010±\u0001H\u0080\b¢\u0006\u0006\b¹\u0001\u0010º\u0001J?\u0010»\u0001\u001a\u0003H¤\u0001\"\t\b\u0000\u0010¤\u0001*\u00020\u000b*\u0003H¤\u00012\u001c\u0010§\u0001\u001a\u0017\u0012\u0005\u0012\u0003H¤\u0001\u0012\u0005\u0012\u00030\u009b\u00010¼\u0001¢\u0006\u0003\b½\u0001H\u0082\b¢\u0006\u0003\u0010¾\u0001J?\u0010¿\u0001\u001a\u00030\u009b\u0001\"\n\b\u0000\u0010¤\u0001*\u00030®\u0001*\u0003H¤\u00012\u001c\u0010§\u0001\u001a\u0017\u0012\u0005\u0012\u0003H¤\u0001\u0012\u0005\u0012\u00030\u009b\u00010¼\u0001¢\u0006\u0003\b½\u0001H\u0002¢\u0006\u0003\u0010À\u0001J=\u0010Á\u0001\u001a\u00030\u009b\u0001\"\n\b\u0000\u0010¤\u0001*\u00030®\u0001*\u0003H¤\u00012\u001c\u0010§\u0001\u001a\u0017\u0012\u0005\u0012\u0003H¤\u0001\u0012\u0005\u0012\u00030\u009b\u00010¼\u0001¢\u0006\u0003\b½\u0001¢\u0006\u0003\u0010À\u0001J3\u0010Â\u0001\u001a\u00030\u009b\u0001*\u00020^2\u0006\u0010`\u001a\u00020\r2\u001b\u0010Ã\u0001\u001a\u0016\u0012\u0004\u0012\u00020^\u0012\u0005\u0012\u00030\u009b\u00010¼\u0001¢\u0006\u0003\b½\u0001H\u0002R\u000e\u0010\u001c\u001a\u00020\u001dX\u0082\u0004¢\u0006\u0002\n\u0000R\u0011\u0010\f\u001a\u00020\r¢\u0006\b\n\u0000\u001a\u0004\b\u001e\u0010\u001fR\u000e\u0010 \u001a\u00020!X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0016\u001a\u00020\u0017X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\"\u001a\u00020\u000bX\u0082\u000e¢\u0006\u0002\n\u0000R*\u0010#\u001a\u001e\u0012\u0004\u0012\u00020%\u0012\u0004\u0012\u00020%0$j\u000e\u0012\u0004\u0012\u00020%\u0012\u0004\u0012\u00020%`&X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u000f\u001a\u00020\rX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\rX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0014\u001a\u00020\u0015X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0019\u001a\u00020\u001aX\u0082\u0004¢\u0006\u0002\n\u0000R\u0011\u0010\u0006\u001a\u00020\u0007¢\u0006\b\n\u0000\u001a\u0004\b'\u0010(R*\u0010)\u001a\u001e\u0012\u0004\u0012\u00020*\u0012\u0004\u0012\u00020+0$j\u000e\u0012\u0004\u0012\u00020*\u0012\u0004\u0012\u00020+`&X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010,\u001a\u00020\rX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\tX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0018\u001a\u00020\rX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0012\u001a\u00020\u0013X\u0082\u0004¢\u0006\u0002\n\u0000R\u0011\u0010\u0010\u001a\u00020\u0011¢\u0006\b\n\u0000\u001a\u0004\b-\u0010.R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006Å\u0001"}, d2 = {"Lorg/jetbrains/kotlin/backend/common/serialization/IrDeclarationDeserializer;", "", "builtIns", "Lorg/jetbrains/kotlin/ir/IrBuiltIns;", "symbolTable", "Lorg/jetbrains/kotlin/ir/util/SymbolTable;", "irFactory", "Lorg/jetbrains/kotlin/ir/declarations/IrFactory;", "libraryFile", "Lorg/jetbrains/kotlin/backend/common/serialization/IrLibraryFile;", "parent", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationParent;", "allowErrorNodes", "", "deserializeInlineFunctions", "deserializeBodies", "symbolDeserializer", "Lorg/jetbrains/kotlin/backend/common/serialization/IrSymbolDeserializer;", "platformFakeOverrideClassFilter", "Lorg/jetbrains/kotlin/backend/common/overrides/FakeOverrideClassFilter;", "fakeOverrideBuilder", "Lorg/jetbrains/kotlin/backend/common/overrides/FakeOverrideBuilder;", "compatibilityMode", "Lorg/jetbrains/kotlin/backend/common/serialization/CompatibilityMode;", "partialLinkageEnabled", "internationService", "Lorg/jetbrains/kotlin/backend/common/serialization/IrInterningService;", "(Lorg/jetbrains/kotlin/ir/IrBuiltIns;Lorg/jetbrains/kotlin/ir/util/SymbolTable;Lorg/jetbrains/kotlin/ir/declarations/IrFactory;Lorg/jetbrains/kotlin/backend/common/serialization/IrLibraryFile;Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationParent;ZZZLorg/jetbrains/kotlin/backend/common/serialization/IrSymbolDeserializer;Lorg/jetbrains/kotlin/backend/common/overrides/FakeOverrideClassFilter;Lorg/jetbrains/kotlin/backend/common/overrides/FakeOverrideBuilder;Lorg/jetbrains/kotlin/backend/common/serialization/CompatibilityMode;ZLorg/jetbrains/kotlin/backend/common/serialization/IrInterningService;)V", "SIMPLE_DYNAMIC_TYPE", "Lorg/jetbrains/kotlin/ir/types/impl/IrDynamicTypeImpl;", "getAllowErrorNodes", "()Z", "bodyDeserializer", "Lorg/jetbrains/kotlin/backend/common/serialization/IrBodyDeserializer;", "currentParent", "delegatedSymbolMap", "Ljava/util/HashMap;", "Lorg/jetbrains/kotlin/ir/symbols/IrSymbol;", "Lkotlin/collections/HashMap;", "getIrFactory", "()Lorg/jetbrains/kotlin/ir/declarations/IrFactory;", "irTypeCache", "", "Lorg/jetbrains/kotlin/ir/types/IrType;", "isEffectivelyExternal", "getSymbolDeserializer", "()Lorg/jetbrains/kotlin/backend/common/serialization/IrSymbolDeserializer;", "computeMissingInlineClassRepresentationForCompatibility", "Lorg/jetbrains/kotlin/descriptors/InlineClassRepresentation;", "Lorg/jetbrains/kotlin/ir/types/IrSimpleType;", "irClass", "Lorg/jetbrains/kotlin/ir/declarations/IrClass;", "deserializeAnnotations", "", "Lorg/jetbrains/kotlin/ir/expressions/IrConstructorCall;", "annotations", "Lorg/jetbrains/kotlin/backend/common/serialization/proto/IrConstructorCall;", "deserializeAnnotations$ir_serialization_common", "deserializeDeclaration", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclaration;", "proto", "Lorg/jetbrains/kotlin/backend/common/serialization/proto/IrDeclaration;", "setParent", "deserializeDefinitelyNotNullType", "Lorg/jetbrains/kotlin/backend/common/serialization/proto/IrDefinitelyNotNullType;", "deserializeDynamicType", "Lorg/jetbrains/kotlin/ir/types/IrDynamicType;", "Lorg/jetbrains/kotlin/backend/common/serialization/proto/IrDynamicType;", "deserializeErrorDeclaration", "Lorg/jetbrains/kotlin/ir/declarations/IrErrorDeclaration;", "Lorg/jetbrains/kotlin/backend/common/serialization/proto/IrErrorDeclaration;", "deserializeErrorType", "Lorg/jetbrains/kotlin/ir/types/IrErrorType;", "Lorg/jetbrains/kotlin/backend/common/serialization/proto/IrErrorType;", "deserializeExpressionBody", "Lorg/jetbrains/kotlin/ir/expressions/IrExpressionBody;", "index", "deserializeInlineClassRepresentation", "Lorg/jetbrains/kotlin/backend/common/serialization/proto/IrInlineClassRepresentation;", "deserializeIrAnonymousInit", "Lorg/jetbrains/kotlin/ir/declarations/IrAnonymousInitializer;", "Lorg/jetbrains/kotlin/backend/common/serialization/proto/IrAnonymousInit;", "deserializeIrClass", "Lorg/jetbrains/kotlin/backend/common/serialization/proto/IrClass;", "deserializeIrConstructor", "Lorg/jetbrains/kotlin/ir/declarations/IrConstructor;", "Lorg/jetbrains/kotlin/backend/common/serialization/proto/IrConstructor;", "deserializeIrDeclarationOrigin", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationOrigin;", "protoName", "deserializeIrEnumEntry", "Lorg/jetbrains/kotlin/ir/declarations/IrEnumEntry;", "Lorg/jetbrains/kotlin/backend/common/serialization/proto/IrEnumEntry;", "deserializeIrField", "Lorg/jetbrains/kotlin/ir/declarations/IrField;", "Lorg/jetbrains/kotlin/backend/common/serialization/proto/IrField;", "isConst", "deserializeIrFunction", "Lorg/jetbrains/kotlin/ir/declarations/IrSimpleFunction;", "Lorg/jetbrains/kotlin/backend/common/serialization/proto/IrFunction;", "deserializeIrFunction$ir_serialization_common", "deserializeIrLocalDelegatedProperty", "Lorg/jetbrains/kotlin/ir/declarations/IrLocalDelegatedProperty;", "Lorg/jetbrains/kotlin/backend/common/serialization/proto/IrLocalDelegatedProperty;", "deserializeIrProperty", "Lorg/jetbrains/kotlin/ir/declarations/IrProperty;", "Lorg/jetbrains/kotlin/backend/common/serialization/proto/IrProperty;", "deserializeIrSymbol", ServerAuthenticationConstants.SIGNIN_GOOGLE_CODE_KEY, "", "deserializeIrSymbol$ir_serialization_common", "deserializeIrSymbolAndRemap", "deserializeIrSymbolAndRemap$ir_serialization_common", "deserializeIrType", "deserializeIrTypeAlias", "Lorg/jetbrains/kotlin/ir/declarations/IrTypeAlias;", "Lorg/jetbrains/kotlin/backend/common/serialization/proto/IrTypeAlias;", "deserializeIrTypeArgument", "Lorg/jetbrains/kotlin/ir/types/IrTypeArgument;", "deserializeIrTypeData", "Lorg/jetbrains/kotlin/backend/common/serialization/proto/IrType;", "deserializeIrTypeParameter", "Lorg/jetbrains/kotlin/ir/declarations/IrTypeParameter;", "Lorg/jetbrains/kotlin/backend/common/serialization/proto/IrTypeParameter;", "isGlobal", "deserializeIrValueParameter", "Lorg/jetbrains/kotlin/ir/declarations/IrValueParameter;", "Lorg/jetbrains/kotlin/backend/common/serialization/proto/IrValueParameter;", "deserializeIrVariable", "Lorg/jetbrains/kotlin/ir/declarations/IrVariable;", "Lorg/jetbrains/kotlin/backend/common/serialization/proto/IrVariable;", "deserializeLegacySimpleType", "Lorg/jetbrains/kotlin/backend/common/serialization/proto/IrSimpleTypeLegacy;", "deserializeMultiFieldValueClassRepresentation", "Lorg/jetbrains/kotlin/descriptors/MultiFieldValueClassRepresentation;", "Lorg/jetbrains/kotlin/backend/common/serialization/proto/IrMultiFieldValueClassRepresentation;", "deserializeName", "Lorg/jetbrains/kotlin/name/Name;", "deserializeNullableIrType", "deserializeSimpleType", "Lorg/jetbrains/kotlin/backend/common/serialization/proto/IrSimpleType;", "deserializeSimpleTypeNullability", "Lorg/jetbrains/kotlin/ir/types/SimpleTypeNullability;", "Lorg/jetbrains/kotlin/backend/common/serialization/proto/IrSimpleTypeNullability;", "deserializeStatementBody", "Lorg/jetbrains/kotlin/ir/IrElement;", "deserializeString", "", "deserializeTypeAbbreviation", "Lorg/jetbrains/kotlin/ir/types/IrTypeAbbreviation;", "Lorg/jetbrains/kotlin/backend/common/serialization/proto/IrTypeAbbreviation;", "deserializeTypeParameters", "protos", "deserializeValueParameters", "eraseDelegatedSymbol", "", NativeSymbol.TYPE_NAME, "isSkippableFakeOverride", "loadExpressionBodyProto", "Lorg/jetbrains/kotlin/backend/common/serialization/proto/IrExpression;", "loadStatementBodyProto", "Lorg/jetbrains/kotlin/backend/common/serialization/proto/IrStatement;", "recordDelegatedSymbol", "withDeserializedIrDeclarationBase", ExifInterface.GPS_DIRECTION_TRUE, "Lorg/jetbrains/kotlin/ir/declarations/IrSymbolOwner;", "Lorg/jetbrains/kotlin/backend/common/serialization/proto/IrDeclarationBase;", "block", "Lkotlin/Function6;", "Lorg/jetbrains/kotlin/ir/util/IdSignature;", "(Lorg/jetbrains/kotlin/backend/common/serialization/proto/IrDeclarationBase;ZLkotlin/jvm/functions/Function6;)Lorg/jetbrains/kotlin/ir/declarations/IrDeclaration;", "withDeserializedIrFunctionBase", ExifInterface.LATITUDE_SOUTH, "Lorg/jetbrains/kotlin/ir/symbols/IrFunctionSymbol;", "Lorg/jetbrains/kotlin/ir/declarations/IrFunction;", "Lorg/jetbrains/kotlin/backend/common/serialization/proto/IrFunctionBase;", "fallbackSymbolKind", "Lorg/jetbrains/kotlin/backend/common/serialization/encodings/BinarySymbolData$SymbolKind;", "(Lorg/jetbrains/kotlin/backend/common/serialization/proto/IrFunctionBase;ZLorg/jetbrains/kotlin/backend/common/serialization/encodings/BinarySymbolData$SymbolKind;Lkotlin/jvm/functions/Function6;)Lorg/jetbrains/kotlin/ir/declarations/IrFunction;", "withExternalValue", "value", "fn", "Lkotlin/Function0;", "checkObjectLeak", "checkSymbolType", "checkSymbolType$ir_serialization_common", "(Lorg/jetbrains/kotlin/ir/symbols/IrSymbol;Lorg/jetbrains/kotlin/backend/common/serialization/encodings/BinarySymbolData$SymbolKind;)Lorg/jetbrains/kotlin/ir/symbols/IrSymbol;", "usingParent", "Lkotlin/Function1;", "Lkotlin/ExtensionFunctionType;", "(Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationParent;Lkotlin/jvm/functions/Function1;)Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationParent;", "withBodyGuard", "(Lorg/jetbrains/kotlin/ir/declarations/IrFunction;Lkotlin/jvm/functions/Function1;)V", "withDeserializeBodies", "withInitializerGuard", "f", "Companion", "ir.serialization.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class IrDeclarationDeserializer {
    private static final List<KClass<?>> allKnownDeclarationOrigins;
    private static final Map<String, IrDeclarationOriginImpl> declarationOriginIndex;
    private final IrDynamicTypeImpl SIMPLE_DYNAMIC_TYPE;
    private final boolean allowErrorNodes;
    private final IrBodyDeserializer bodyDeserializer;
    private final CompatibilityMode compatibilityMode;
    private IrDeclarationParent currentParent;
    private final HashMap<IrSymbol, IrSymbol> delegatedSymbolMap;
    private boolean deserializeBodies;
    private final boolean deserializeInlineFunctions;
    private final FakeOverrideBuilder fakeOverrideBuilder;
    private final IrInterningService internationService;
    private final IrFactory irFactory;
    private final HashMap<Integer, IrType> irTypeCache;
    private boolean isEffectivelyExternal;
    private final IrLibraryFile libraryFile;
    private final boolean partialLinkageEnabled;
    private final FakeOverrideClassFilter platformFakeOverrideClassFilter;
    private final IrSymbolDeserializer symbolDeserializer;
    private final SymbolTable symbolTable;

    /* JADX INFO: compiled from: IrDeclarationDeserializer.kt */
    @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
    public /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;
        public static final /* synthetic */ int[] $EnumSwitchMapping$1;
        public static final /* synthetic */ int[] $EnumSwitchMapping$2;

        static {
            int[] iArr = new int[IrSimpleTypeNullability.values().length];
            try {
                iArr[IrSimpleTypeNullability.MARKED_NULLABLE.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[IrSimpleTypeNullability.NOT_SPECIFIED.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                iArr[IrSimpleTypeNullability.DEFINITELY_NOT_NULL.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            $EnumSwitchMapping$0 = iArr;
            int[] iArr2 = new int[org.jetbrains.kotlin.backend.common.serialization.proto.IrType.KindCase.values().length];
            try {
                iArr2[org.jetbrains.kotlin.backend.common.serialization.proto.IrType.KindCase.DNN.ordinal()] = 1;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                iArr2[org.jetbrains.kotlin.backend.common.serialization.proto.IrType.KindCase.SIMPLE.ordinal()] = 2;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                iArr2[org.jetbrains.kotlin.backend.common.serialization.proto.IrType.KindCase.LEGACYSIMPLE.ordinal()] = 3;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                iArr2[org.jetbrains.kotlin.backend.common.serialization.proto.IrType.KindCase.DYNAMIC.ordinal()] = 4;
            } catch (NoSuchFieldError unused7) {
            }
            try {
                iArr2[org.jetbrains.kotlin.backend.common.serialization.proto.IrType.KindCase.ERROR.ordinal()] = 5;
            } catch (NoSuchFieldError unused8) {
            }
            $EnumSwitchMapping$1 = iArr2;
            int[] iArr3 = new int[IrDeclaration.DeclaratorCase.values().length];
            try {
                iArr3[IrDeclaration.DeclaratorCase.IR_ANONYMOUS_INIT.ordinal()] = 1;
            } catch (NoSuchFieldError unused9) {
            }
            try {
                iArr3[IrDeclaration.DeclaratorCase.IR_CONSTRUCTOR.ordinal()] = 2;
            } catch (NoSuchFieldError unused10) {
            }
            try {
                iArr3[IrDeclaration.DeclaratorCase.IR_FIELD.ordinal()] = 3;
            } catch (NoSuchFieldError unused11) {
            }
            try {
                iArr3[IrDeclaration.DeclaratorCase.IR_CLASS.ordinal()] = 4;
            } catch (NoSuchFieldError unused12) {
            }
            try {
                iArr3[IrDeclaration.DeclaratorCase.IR_FUNCTION.ordinal()] = 5;
            } catch (NoSuchFieldError unused13) {
            }
            try {
                iArr3[IrDeclaration.DeclaratorCase.IR_PROPERTY.ordinal()] = 6;
            } catch (NoSuchFieldError unused14) {
            }
            try {
                iArr3[IrDeclaration.DeclaratorCase.IR_TYPE_PARAMETER.ordinal()] = 7;
            } catch (NoSuchFieldError unused15) {
            }
            try {
                iArr3[IrDeclaration.DeclaratorCase.IR_VARIABLE.ordinal()] = 8;
            } catch (NoSuchFieldError unused16) {
            }
            try {
                iArr3[IrDeclaration.DeclaratorCase.IR_VALUE_PARAMETER.ordinal()] = 9;
            } catch (NoSuchFieldError unused17) {
            }
            try {
                iArr3[IrDeclaration.DeclaratorCase.IR_ENUM_ENTRY.ordinal()] = 10;
            } catch (NoSuchFieldError unused18) {
            }
            try {
                iArr3[IrDeclaration.DeclaratorCase.IR_LOCAL_DELEGATED_PROPERTY.ordinal()] = 11;
            } catch (NoSuchFieldError unused19) {
            }
            try {
                iArr3[IrDeclaration.DeclaratorCase.IR_TYPE_ALIAS.ordinal()] = 12;
            } catch (NoSuchFieldError unused20) {
            }
            try {
                iArr3[IrDeclaration.DeclaratorCase.IR_ERROR_DECLARATION.ordinal()] = 13;
            } catch (NoSuchFieldError unused21) {
            }
            try {
                iArr3[IrDeclaration.DeclaratorCase.DECLARATOR_NOT_SET.ordinal()] = 14;
            } catch (NoSuchFieldError unused22) {
            }
            $EnumSwitchMapping$2 = iArr3;
        }
    }

    public IrDeclarationDeserializer(IrBuiltIns builtIns, SymbolTable symbolTable, IrFactory irFactory, IrLibraryFile libraryFile, IrDeclarationParent parent, boolean z, boolean z2, boolean z3, IrSymbolDeserializer symbolDeserializer, FakeOverrideClassFilter platformFakeOverrideClassFilter, FakeOverrideBuilder fakeOverrideBuilder, CompatibilityMode compatibilityMode, boolean z4, IrInterningService internationService) {
        Intrinsics.checkNotNullParameter(builtIns, "builtIns");
        Intrinsics.checkNotNullParameter(symbolTable, "symbolTable");
        Intrinsics.checkNotNullParameter(irFactory, "irFactory");
        Intrinsics.checkNotNullParameter(libraryFile, "libraryFile");
        Intrinsics.checkNotNullParameter(parent, "parent");
        Intrinsics.checkNotNullParameter(symbolDeserializer, "symbolDeserializer");
        Intrinsics.checkNotNullParameter(platformFakeOverrideClassFilter, "platformFakeOverrideClassFilter");
        Intrinsics.checkNotNullParameter(fakeOverrideBuilder, "fakeOverrideBuilder");
        Intrinsics.checkNotNullParameter(compatibilityMode, "compatibilityMode");
        Intrinsics.checkNotNullParameter(internationService, "internationService");
        this.symbolTable = symbolTable;
        this.irFactory = irFactory;
        this.libraryFile = libraryFile;
        this.allowErrorNodes = z;
        this.deserializeInlineFunctions = z2;
        this.deserializeBodies = z3;
        this.symbolDeserializer = symbolDeserializer;
        this.platformFakeOverrideClassFilter = platformFakeOverrideClassFilter;
        this.fakeOverrideBuilder = fakeOverrideBuilder;
        this.compatibilityMode = compatibilityMode;
        this.partialLinkageEnabled = z4;
        this.internationService = internationService;
        this.bodyDeserializer = new IrBodyDeserializer(builtIns, z, irFactory, libraryFile, this);
        this.irTypeCache = new HashMap<>();
        this.SIMPLE_DYNAMIC_TYPE = new IrDynamicTypeImpl(null, CollectionsKt.emptyList(), Variance.INVARIANT);
        this.currentParent = parent;
        this.delegatedSymbolMap = new HashMap<>();
    }

    public final IrFactory getIrFactory() {
        return this.irFactory;
    }

    public final boolean getAllowErrorNodes() {
        return this.allowErrorNodes;
    }

    public final IrSymbolDeserializer getSymbolDeserializer() {
        return this.symbolDeserializer;
    }

    private final String deserializeString(int index) {
        return this.libraryFile.string(index);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final Name deserializeName(int index) {
        IrInterningService irInterningService = this.internationService;
        Name nameGuessByFirstCharacter = Name.guessByFirstCharacter(deserializeString(index));
        Intrinsics.checkNotNullExpressionValue(nameGuessByFirstCharacter, "guessByFirstCharacter(...)");
        return irInterningService.name(nameGuessByFirstCharacter);
    }

    public final IrType deserializeNullableIrType(int index) {
        if (index == -1) {
            return null;
        }
        return deserializeIrType(index);
    }

    public final IrType deserializeIrType(int index) {
        HashMap<Integer, IrType> map = this.irTypeCache;
        Integer numValueOf = Integer.valueOf(index);
        IrType irTypeDeserializeIrTypeData = map.get(numValueOf);
        if (irTypeDeserializeIrTypeData == null) {
            irTypeDeserializeIrTypeData = deserializeIrTypeData(this.libraryFile.type(index));
            map.put(numValueOf, irTypeDeserializeIrTypeData);
        }
        return irTypeDeserializeIrTypeData;
    }

    private final IrTypeArgument deserializeIrTypeArgument(long proto) {
        long jM6223decode2ztXSlc = BinaryTypeProjection.INSTANCE.m6223decode2ztXSlc(proto);
        return BinaryTypeProjection.m6219isStarProjectionimpl(jM6223decode2ztXSlc) ? IrStarProjectionImpl.INSTANCE : IrSimpleTypeImplKt.makeTypeProjection(deserializeIrType(BinaryTypeProjection.m6216getTypeIndeximpl(jM6223decode2ztXSlc)), BinaryTypeProjection.m6217getVarianceimpl(jM6223decode2ztXSlc));
    }

    public final List<IrConstructorCall> deserializeAnnotations$ir_serialization_common(List<org.jetbrains.kotlin.backend.common.serialization.proto.IrConstructorCall> annotations) {
        Intrinsics.checkNotNullParameter(annotations, "annotations");
        List<org.jetbrains.kotlin.backend.common.serialization.proto.IrConstructorCall> list = annotations;
        ArrayList arrayList = new ArrayList(list.size());
        Iterator<T> it = list.iterator();
        while (it.hasNext()) {
            arrayList.add(this.bodyDeserializer.deserializeAnnotation((org.jetbrains.kotlin.backend.common.serialization.proto.IrConstructorCall) it.next()));
        }
        return org.jetbrains.kotlin.utils.CollectionsKt.compactIfPossible(arrayList);
    }

    private final SimpleTypeNullability deserializeSimpleTypeNullability(IrSimpleTypeNullability proto) {
        int i = WhenMappings.$EnumSwitchMapping$0[proto.ordinal()];
        if (i == 1) {
            return SimpleTypeNullability.MARKED_NULLABLE;
        }
        if (i == 2) {
            return SimpleTypeNullability.NOT_SPECIFIED;
        }
        if (i == 3) {
            return SimpleTypeNullability.DEFINITELY_NOT_NULL;
        }
        throw new NoWhenBranchMatchedException();
    }

    private final IrSimpleType deserializeSimpleType(org.jetbrains.kotlin.backend.common.serialization.proto.IrSimpleType proto) throws IrSymbolTypeMismatchException {
        IrClassifierSymbol irClassifierSymbolDeserializeIrSymbolAndRemap$ir_serialization_common = deserializeIrSymbolAndRemap$ir_serialization_common(proto.getClassifier());
        BinarySymbolData.SymbolKind symbolKind = BinarySymbolData.SymbolKind.CLASS_SYMBOL;
        IrTypeAbbreviation irTypeAbbreviationDeserializeTypeAbbreviation = null;
        if (!(irClassifierSymbolDeserializeIrSymbolAndRemap$ir_serialization_common instanceof IrClassifierSymbol)) {
            if (!this.partialLinkageEnabled) {
                throw new IrSymbolTypeMismatchException(IrClassifierSymbol.class, irClassifierSymbolDeserializeIrSymbolAndRemap$ir_serialization_common);
            }
            ReferenceSymbolTable symbolTable = getSymbolDeserializer().getSymbolTable();
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
        IrClassifierSymbol irClassifierSymbol = (IrClassifierSymbol) irClassifierSymbolDeserializeIrSymbolAndRemap$ir_serialization_common;
        List<Long> argumentList = proto.getArgumentList();
        Intrinsics.checkNotNullExpressionValue(argumentList, "getArgumentList(...)");
        List<Long> list = argumentList;
        ArrayList arrayList = new ArrayList(list.size());
        for (Long l : list) {
            Intrinsics.checkNotNull(l);
            arrayList.add(deserializeIrTypeArgument(l.longValue()));
        }
        List listCompactIfPossible = org.jetbrains.kotlin.utils.CollectionsKt.compactIfPossible(arrayList);
        List<org.jetbrains.kotlin.backend.common.serialization.proto.IrConstructorCall> annotationList = proto.getAnnotationList();
        Intrinsics.checkNotNullExpressionValue(annotationList, "getAnnotationList(...)");
        List<IrConstructorCall> listDeserializeAnnotations$ir_serialization_common = deserializeAnnotations$ir_serialization_common(annotationList);
        if (proto.hasAbbreviation()) {
            org.jetbrains.kotlin.backend.common.serialization.proto.IrTypeAbbreviation abbreviation = proto.getAbbreviation();
            Intrinsics.checkNotNullExpressionValue(abbreviation, "getAbbreviation(...)");
            irTypeAbbreviationDeserializeTypeAbbreviation = deserializeTypeAbbreviation(abbreviation);
        }
        IrSimpleTypeNullability nullability = proto.getNullability();
        Intrinsics.checkNotNullExpressionValue(nullability, "getNullability(...)");
        return new IrSimpleTypeImpl(null, irClassifierSymbol, deserializeSimpleTypeNullability(nullability), listCompactIfPossible, listDeserializeAnnotations$ir_serialization_common, irTypeAbbreviationDeserializeTypeAbbreviation);
    }

    private final IrSimpleType deserializeLegacySimpleType(IrSimpleTypeLegacy proto) throws IrSymbolTypeMismatchException {
        IrClassifierSymbol irClassifierSymbolDeserializeIrSymbolAndRemap$ir_serialization_common = deserializeIrSymbolAndRemap$ir_serialization_common(proto.getClassifier());
        BinarySymbolData.SymbolKind symbolKind = BinarySymbolData.SymbolKind.CLASS_SYMBOL;
        IrTypeAbbreviation irTypeAbbreviationDeserializeTypeAbbreviation = null;
        if (!(irClassifierSymbolDeserializeIrSymbolAndRemap$ir_serialization_common instanceof IrClassifierSymbol)) {
            if (!this.partialLinkageEnabled) {
                throw new IrSymbolTypeMismatchException(IrClassifierSymbol.class, irClassifierSymbolDeserializeIrSymbolAndRemap$ir_serialization_common);
            }
            ReferenceSymbolTable symbolTable = getSymbolDeserializer().getSymbolTable();
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
        IrClassifierSymbol irClassifierSymbol = (IrClassifierSymbol) irClassifierSymbolDeserializeIrSymbolAndRemap$ir_serialization_common;
        List<Long> argumentList = proto.getArgumentList();
        Intrinsics.checkNotNullExpressionValue(argumentList, "getArgumentList(...)");
        List<Long> list = argumentList;
        ArrayList arrayList = new ArrayList(list.size());
        for (Long l : list) {
            Intrinsics.checkNotNull(l);
            arrayList.add(deserializeIrTypeArgument(l.longValue()));
        }
        List listCompactIfPossible = org.jetbrains.kotlin.utils.CollectionsKt.compactIfPossible(arrayList);
        List<org.jetbrains.kotlin.backend.common.serialization.proto.IrConstructorCall> annotationList = proto.getAnnotationList();
        Intrinsics.checkNotNullExpressionValue(annotationList, "getAnnotationList(...)");
        List<IrConstructorCall> listDeserializeAnnotations$ir_serialization_common = deserializeAnnotations$ir_serialization_common(annotationList);
        if (proto.hasAbbreviation()) {
            org.jetbrains.kotlin.backend.common.serialization.proto.IrTypeAbbreviation abbreviation = proto.getAbbreviation();
            Intrinsics.checkNotNullExpressionValue(abbreviation, "getAbbreviation(...)");
            irTypeAbbreviationDeserializeTypeAbbreviation = deserializeTypeAbbreviation(abbreviation);
        }
        return new IrSimpleTypeImpl(null, irClassifierSymbol, SimpleTypeNullability.INSTANCE.fromHasQuestionMark(proto.getHasQuestionMark()), listCompactIfPossible, listDeserializeAnnotations$ir_serialization_common, irTypeAbbreviationDeserializeTypeAbbreviation);
    }

    private final IrTypeAbbreviation deserializeTypeAbbreviation(org.jetbrains.kotlin.backend.common.serialization.proto.IrTypeAbbreviation proto) throws IrSymbolTypeMismatchException {
        IrTypeAliasSymbol irTypeAliasSymbolDeserializeIrSymbolAndRemap$ir_serialization_common = deserializeIrSymbolAndRemap$ir_serialization_common(proto.getTypeAlias());
        BinarySymbolData.SymbolKind symbolKind = BinarySymbolData.SymbolKind.TYPEALIAS_SYMBOL;
        if (!(irTypeAliasSymbolDeserializeIrSymbolAndRemap$ir_serialization_common instanceof IrTypeAliasSymbol)) {
            if (!this.partialLinkageEnabled) {
                throw new IrSymbolTypeMismatchException(IrTypeAliasSymbol.class, irTypeAliasSymbolDeserializeIrSymbolAndRemap$ir_serialization_common);
            }
            ReferenceSymbolTable symbolTable = getSymbolDeserializer().getSymbolTable();
            if (symbolKind == null) {
                throw new IllegalStateException(("No fallback symbol kind specified for symbol " + irTypeAliasSymbolDeserializeIrSymbolAndRemap$ir_serialization_common).toString());
            }
            IdSignature signature = irTypeAliasSymbolDeserializeIrSymbolAndRemap$ir_serialization_common.getSignature();
            if (signature != null) {
                if (!signature.isPubliclyVisible()) {
                    signature = null;
                }
                if (signature != null) {
                    IrSymbol irSymbolReferenceDeserializedSymbol = IrSymbolDeserializerKt.referenceDeserializedSymbol(symbolTable, null, symbolKind, signature);
                    if (irSymbolReferenceDeserializedSymbol == null) {
                        throw new NullPointerException("null cannot be cast to non-null type org.jetbrains.kotlin.ir.symbols.IrTypeAliasSymbol");
                    }
                    irTypeAliasSymbolDeserializeIrSymbolAndRemap$ir_serialization_common = (IrTypeAliasSymbol) irSymbolReferenceDeserializedSymbol;
                }
            }
            throw new IllegalStateException(("No public signature for symbol " + irTypeAliasSymbolDeserializeIrSymbolAndRemap$ir_serialization_common).toString());
        }
        IrTypeAliasSymbol irTypeAliasSymbol = (IrTypeAliasSymbol) irTypeAliasSymbolDeserializeIrSymbolAndRemap$ir_serialization_common;
        boolean hasQuestionMark = proto.getHasQuestionMark();
        List<Long> argumentList = proto.getArgumentList();
        Intrinsics.checkNotNullExpressionValue(argumentList, "getArgumentList(...)");
        List<Long> list = argumentList;
        ArrayList arrayList = new ArrayList(list.size());
        for (Long l : list) {
            Intrinsics.checkNotNull(l);
            arrayList.add(deserializeIrTypeArgument(l.longValue()));
        }
        List listCompactIfPossible = org.jetbrains.kotlin.utils.CollectionsKt.compactIfPossible(arrayList);
        List<org.jetbrains.kotlin.backend.common.serialization.proto.IrConstructorCall> annotationList = proto.getAnnotationList();
        Intrinsics.checkNotNullExpressionValue(annotationList, "getAnnotationList(...)");
        return new IrTypeAbbreviationImpl(irTypeAliasSymbol, hasQuestionMark, listCompactIfPossible, deserializeAnnotations$ir_serialization_common(annotationList));
    }

    private final IrDynamicType deserializeDynamicType(org.jetbrains.kotlin.backend.common.serialization.proto.IrDynamicType proto) {
        if (proto.getAnnotationCount() == 0) {
            return this.SIMPLE_DYNAMIC_TYPE;
        }
        List<org.jetbrains.kotlin.backend.common.serialization.proto.IrConstructorCall> annotationList = proto.getAnnotationList();
        Intrinsics.checkNotNullExpressionValue(annotationList, "getAnnotationList(...)");
        return new IrDynamicTypeImpl(null, deserializeAnnotations$ir_serialization_common(annotationList), Variance.INVARIANT);
    }

    private final IrErrorType deserializeErrorType(org.jetbrains.kotlin.backend.common.serialization.proto.IrErrorType proto) throws IrDisallowedErrorNode {
        if (!this.allowErrorNodes) {
            throw new IrDisallowedErrorNode(IrErrorType.class);
        }
        List<org.jetbrains.kotlin.backend.common.serialization.proto.IrConstructorCall> annotationList = proto.getAnnotationList();
        Intrinsics.checkNotNullExpressionValue(annotationList, "getAnnotationList(...)");
        return new IrErrorTypeImpl(null, deserializeAnnotations$ir_serialization_common(annotationList), Variance.INVARIANT, false, 8, null);
    }

    private final IrSimpleType deserializeDefinitelyNotNullType(IrDefinitelyNotNullType proto) {
        proto.getTypesCount();
        Integer num = proto.getTypesList().get(0);
        Intrinsics.checkNotNullExpressionValue(num, "get(...)");
        IrType irTypeMakeNotNull = IrTypesKt.makeNotNull(deserializeIrType(num.intValue()));
        Intrinsics.checkNotNull(irTypeMakeNotNull, "null cannot be cast to non-null type org.jetbrains.kotlin.ir.types.IrSimpleType");
        return (IrSimpleType) irTypeMakeNotNull;
    }

    private final IrType deserializeIrTypeData(org.jetbrains.kotlin.backend.common.serialization.proto.IrType proto) {
        org.jetbrains.kotlin.backend.common.serialization.proto.IrType.KindCase kindCase = proto.getKindCase();
        int i = kindCase == null ? -1 : WhenMappings.$EnumSwitchMapping$1[kindCase.ordinal()];
        if (i == 1) {
            IrDefinitelyNotNullType dnn = proto.getDnn();
            Intrinsics.checkNotNullExpressionValue(dnn, "getDnn(...)");
            return deserializeDefinitelyNotNullType(dnn);
        }
        if (i == 2) {
            org.jetbrains.kotlin.backend.common.serialization.proto.IrSimpleType simple = proto.getSimple();
            Intrinsics.checkNotNullExpressionValue(simple, "getSimple(...)");
            return deserializeSimpleType(simple);
        }
        if (i == 3) {
            IrSimpleTypeLegacy legacySimple = proto.getLegacySimple();
            Intrinsics.checkNotNullExpressionValue(legacySimple, "getLegacySimple(...)");
            return deserializeLegacySimpleType(legacySimple);
        }
        if (i == 4) {
            org.jetbrains.kotlin.backend.common.serialization.proto.IrDynamicType dynamic = proto.getDynamic();
            Intrinsics.checkNotNullExpressionValue(dynamic, "getDynamic(...)");
            return deserializeDynamicType(dynamic);
        }
        if (i == 5) {
            org.jetbrains.kotlin.backend.common.serialization.proto.IrErrorType error = proto.getError();
            Intrinsics.checkNotNullExpressionValue(error, "getError(...)");
            return deserializeErrorType(error);
        }
        throw new IllegalStateException(("Unexpected IrType kind: " + proto.getKindCase()).toString());
    }

    public final IrSymbol deserializeIrSymbol$ir_serialization_common(long code) {
        return this.symbolDeserializer.deserializeIrSymbol(code);
    }

    public final IrSymbol deserializeIrSymbolAndRemap$ir_serialization_common(long code) {
        IrSymbol irSymbolDeserializeIrSymbol = this.symbolDeserializer.deserializeIrSymbol(code);
        IrSymbol irSymbol = this.delegatedSymbolMap.get(irSymbolDeserializeIrSymbol);
        if (irSymbol != null) {
            irSymbolDeserializeIrSymbol = irSymbol;
        }
        Intrinsics.checkNotNullExpressionValue(irSymbolDeserializeIrSymbol, "let(...)");
        return irSymbolDeserializeIrSymbol;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void recordDelegatedSymbol(IrSymbol symbol) {
        if (symbol instanceof IrDelegatingSymbol) {
            this.delegatedSymbolMap.put(symbol, ((IrDelegatingSymbol) symbol).getDelegate());
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void eraseDelegatedSymbol(IrSymbol symbol) {
        if (symbol instanceof IrDelegatingSymbol) {
            this.delegatedSymbolMap.remove(symbol);
        }
    }

    static /* synthetic */ IrTypeParameter deserializeIrTypeParameter$default(IrDeclarationDeserializer irDeclarationDeserializer, org.jetbrains.kotlin.backend.common.serialization.proto.IrTypeParameter irTypeParameter, int i, boolean z, boolean z2, int i2, Object obj) {
        if ((i2 & 8) != 0) {
            z2 = true;
        }
        return irDeclarationDeserializer.deserializeIrTypeParameter(irTypeParameter, i, z, z2);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r13v10, types: [T, java.lang.Object] */
    /* JADX WARN: Type inference failed for: r13v3, types: [T, org.jetbrains.kotlin.ir.util.IdSignature] */
    private final IrTypeParameter deserializeIrTypeParameter(final org.jetbrains.kotlin.backend.common.serialization.proto.IrTypeParameter proto, final int index, boolean isGlobal, boolean setParent) throws IrSymbolTypeMismatchException {
        IrTypeParameter irTypeParameterDeclareScopedTypeParameter;
        final Name nameDeserializeName = deserializeName(proto.getName());
        final long jM6180decodeViYoqTc = BinaryCoordinates.INSTANCE.m6180decodeViYoqTc(proto.getBase().getCoordinates());
        final long jM6322decodedTrNHuw = TypeParameterFlags.INSTANCE.m6322decodedTrNHuw(proto.getBase().getFlags());
        Function1<IrTypeParameterSymbol, IrTypeParameter> function1 = new Function1<IrTypeParameterSymbol, IrTypeParameter>() { // from class: org.jetbrains.kotlin.backend.common.serialization.IrDeclarationDeserializer$deserializeIrTypeParameter$factory$1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final IrTypeParameter invoke(IrTypeParameterSymbol symbol) {
                Intrinsics.checkNotNullParameter(symbol, "symbol");
                return this.this$0.getIrFactory().createTypeParameter(BinaryCoordinates.m6176getStartOffsetimpl(jM6180decodeViYoqTc), BinaryCoordinates.m6175getEndOffsetimpl(jM6180decodeViYoqTc), this.this$0.deserializeIrDeclarationOrigin(proto.getBase().getOriginName()), nameDeserializeName, symbol, TypeParameterFlags.m6317getVarianceimpl(jM6322decodedTrNHuw), index, TypeParameterFlags.m6319isReifiedimpl(jM6322decodedTrNHuw));
            }
        };
        final Ref.ObjectRef objectRef = new Ref.ObjectRef();
        SymbolTable symbolTable = this.symbolTable;
        if (isGlobal) {
            Pair<IrSymbol, IdSignature> pairDeserializeIrSymbolToDeclare = this.symbolDeserializer.deserializeIrSymbolToDeclare(proto.getBase().getSymbol());
            IrTypeParameterSymbol first = pairDeserializeIrSymbolToDeclare.getFirst();
            BinarySymbolData.SymbolKind symbolKind = BinarySymbolData.SymbolKind.TYPE_PARAMETER_SYMBOL;
            if (!(first instanceof IrTypeParameterSymbol)) {
                if (!this.partialLinkageEnabled) {
                    throw new IrSymbolTypeMismatchException(IrTypeParameterSymbol.class, first);
                }
                ReferenceSymbolTable symbolTable2 = getSymbolDeserializer().getSymbolTable();
                if (symbolKind == null) {
                    throw new IllegalStateException(("No fallback symbol kind specified for symbol " + first).toString());
                }
                IdSignature signature = first.getSignature();
                if (signature != null) {
                    if (!signature.isPubliclyVisible()) {
                        signature = null;
                    }
                    if (signature != null) {
                        IrSymbol irSymbolReferenceDeserializedSymbol = IrSymbolDeserializerKt.referenceDeserializedSymbol(symbolTable2, null, symbolKind, signature);
                        if (irSymbolReferenceDeserializedSymbol == null) {
                            throw new NullPointerException("null cannot be cast to non-null type org.jetbrains.kotlin.ir.symbols.IrTypeParameterSymbol");
                        }
                        first = (IrTypeParameterSymbol) irSymbolReferenceDeserializedSymbol;
                    }
                }
                throw new IllegalStateException(("No public signature for symbol " + first).toString());
            }
            final IrTypeParameterSymbol irTypeParameterSymbol = (IrTypeParameterSymbol) first;
            objectRef.element = pairDeserializeIrSymbolToDeclare.getSecond();
            irTypeParameterDeclareScopedTypeParameter = symbolTable.declareGlobalTypeParameter((IdSignature) objectRef.element, new Function0<IrTypeParameterSymbol>() { // from class: org.jetbrains.kotlin.backend.common.serialization.IrDeclarationDeserializer$deserializeIrTypeParameter$result$1$1
                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.Function0
                public final IrTypeParameterSymbol invoke() {
                    return irTypeParameterSymbol;
                }
            }, function1);
        } else {
            objectRef.element = this.symbolDeserializer.deserializeIdSignature(BinarySymbolData.m6206getSignatureIdimpl(BinarySymbolData.INSTANCE.m6211decode9x8F8T0(proto.getBase().getSymbol())));
            irTypeParameterDeclareScopedTypeParameter = symbolTable.declareScopedTypeParameter((IdSignature) objectRef.element, new Function1<IdSignature, IrTypeParameterSymbol>() { // from class: org.jetbrains.kotlin.backend.common.serialization.IrDeclarationDeserializer$deserializeIrTypeParameter$result$1$2
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(1);
                }

                @Override // kotlin.jvm.functions.Function1
                public final IrTypeParameterSymbol invoke(IdSignature it) throws IrSymbolTypeMismatchException {
                    Intrinsics.checkNotNullParameter(it, "it");
                    if (it.isPubliclyVisible()) {
                        IrDeclarationDeserializer irDeclarationDeserializer = this.this$0;
                        IrTypeParameterSymbol irTypeParameterSymbolDeserializeIrSymbol = irDeclarationDeserializer.getSymbolDeserializer().deserializeIrSymbol(objectRef.element, BinarySymbolData.SymbolKind.TYPE_PARAMETER_SYMBOL);
                        BinarySymbolData.SymbolKind symbolKind2 = BinarySymbolData.SymbolKind.TYPE_PARAMETER_SYMBOL;
                        if (!(irTypeParameterSymbolDeserializeIrSymbol instanceof IrTypeParameterSymbol)) {
                            if (!irDeclarationDeserializer.partialLinkageEnabled) {
                                throw new IrSymbolTypeMismatchException(IrTypeParameterSymbol.class, irTypeParameterSymbolDeserializeIrSymbol);
                            }
                            ReferenceSymbolTable symbolTable3 = irDeclarationDeserializer.getSymbolDeserializer().getSymbolTable();
                            if (symbolKind2 == null) {
                                throw new IllegalStateException(("No fallback symbol kind specified for symbol " + irTypeParameterSymbolDeserializeIrSymbol).toString());
                            }
                            IdSignature signature2 = irTypeParameterSymbolDeserializeIrSymbol.getSignature();
                            if (signature2 != null) {
                                if (!signature2.isPubliclyVisible()) {
                                    signature2 = null;
                                }
                                if (signature2 != null) {
                                    IrSymbol irSymbolReferenceDeserializedSymbol2 = IrSymbolDeserializerKt.referenceDeserializedSymbol(symbolTable3, null, symbolKind2, signature2);
                                    if (irSymbolReferenceDeserializedSymbol2 == null) {
                                        throw new NullPointerException("null cannot be cast to non-null type org.jetbrains.kotlin.ir.symbols.IrTypeParameterSymbol");
                                    }
                                    irTypeParameterSymbolDeserializeIrSymbol = (IrTypeParameterSymbol) irSymbolReferenceDeserializedSymbol2;
                                }
                            }
                            throw new IllegalStateException(("No public signature for symbol " + irTypeParameterSymbolDeserializeIrSymbol).toString());
                        }
                        return (IrTypeParameterSymbol) irTypeParameterSymbolDeserializeIrSymbol;
                    }
                    return new IrTypeParameterSymbolImpl(null, 1, null);
                }
            }, function1);
        }
        this.symbolDeserializer.referenceLocalIrSymbol(irTypeParameterDeclareScopedTypeParameter.getSymbol(), (IdSignature) objectRef.element);
        List<org.jetbrains.kotlin.backend.common.serialization.proto.IrConstructorCall> annotationList = proto.getBase().getAnnotationList();
        Intrinsics.checkNotNullExpressionValue(annotationList, "getAnnotationList(...)");
        irTypeParameterDeclareScopedTypeParameter.setAnnotations(deserializeAnnotations$ir_serialization_common(annotationList));
        if (setParent) {
            irTypeParameterDeclareScopedTypeParameter.setParent(this.currentParent);
        }
        return irTypeParameterDeclareScopedTypeParameter;
    }

    static /* synthetic */ IrValueParameter deserializeIrValueParameter$default(IrDeclarationDeserializer irDeclarationDeserializer, org.jetbrains.kotlin.backend.common.serialization.proto.IrValueParameter irValueParameter, int i, boolean z, int i2, Object obj) {
        if ((i2 & 4) != 0) {
            z = true;
        }
        return irDeclarationDeserializer.deserializeIrValueParameter(irValueParameter, i, z);
    }

    private final IrValueParameter deserializeIrValueParameter(org.jetbrains.kotlin.backend.common.serialization.proto.IrValueParameter proto, int index, boolean setParent) {
        IrDeclarationBase base = proto.getBase();
        Intrinsics.checkNotNullExpressionValue(base, "getBase(...)");
        Pair<IrSymbol, IdSignature> pairDeserializeIrSymbolToDeclare = getSymbolDeserializer().deserializeIrSymbolToDeclare(base.getSymbol());
        IrSymbol irSymbolComponent1 = pairDeserializeIrSymbolToDeclare.component1();
        pairDeserializeIrSymbolToDeclare.component2();
        long jM6180decodeViYoqTc = BinaryCoordinates.INSTANCE.m6180decodeViYoqTc(base.getCoordinates());
        try {
            recordDelegatedSymbol(irSymbolComponent1);
            int iM6176getStartOffsetimpl = BinaryCoordinates.m6176getStartOffsetimpl(jM6180decodeViYoqTc);
            int iM6175getEndOffsetimpl = BinaryCoordinates.m6175getEndOffsetimpl(jM6180decodeViYoqTc);
            IrDeclarationOrigin irDeclarationOriginDeserializeIrDeclarationOrigin = deserializeIrDeclarationOrigin(base.getOriginName());
            long jM6334decodeeyNVhwA = ValueParameterFlags.INSTANCE.m6334decodeeyNVhwA(base.getFlags());
            long jM6200decodeWXC2TjU = BinaryNameAndType.INSTANCE.m6200decodeWXC2TjU(proto.getNameType());
            IrFactory irFactory = this.irFactory;
            Name nameDeserializeName = deserializeName(BinaryNameAndType.m6195getNameIndeximpl(jM6200decodeWXC2TjU));
            IrType irTypeDeserializeIrType = deserializeIrType(BinaryNameAndType.m6196getTypeIndeximpl(jM6200decodeWXC2TjU));
            boolean zM6328isAssignableimpl = ValueParameterFlags.m6328isAssignableimpl(jM6334decodeeyNVhwA);
            if (!(irSymbolComponent1 instanceof IrValueParameterSymbol)) {
                if (!this.partialLinkageEnabled) {
                    throw new IrSymbolTypeMismatchException(IrValueParameterSymbol.class, irSymbolComponent1);
                }
                getSymbolDeserializer().getSymbolTable();
                throw new IllegalStateException(("No fallback symbol kind specified for symbol " + irSymbolComponent1).toString());
            }
            IrValueParameter irValueParameterCreateValueParameter = irFactory.createValueParameter(iM6176getStartOffsetimpl, iM6175getEndOffsetimpl, irDeclarationOriginDeserializeIrDeclarationOrigin, nameDeserializeName, irTypeDeserializeIrType, zM6328isAssignableimpl, (IrValueParameterSymbol) irSymbolComponent1, index, proto.hasVarargElementType() ? deserializeIrType(proto.getVarargElementType()) : null, ValueParameterFlags.m6329isCrossInlineimpl(jM6334decodeeyNVhwA), ValueParameterFlags.m6331isNoInlineimpl(jM6334decodeeyNVhwA), ValueParameterFlags.m6330isHiddenimpl(jM6334decodeeyNVhwA));
            if (proto.hasDefaultValue()) {
                IrExpressionBody irExpressionBodyDeserializeExpressionBody = deserializeExpressionBody(proto.getDefaultValue());
                if (irExpressionBodyDeserializeExpressionBody == null) {
                    irExpressionBodyDeserializeExpressionBody = IrFactoryHelpersKt.createExpressionBody(this.irFactory, new IrCompositeImpl(iM6176getStartOffsetimpl, iM6175getEndOffsetimpl, irValueParameterCreateValueParameter.getType(), null, 8, null));
                }
                irValueParameterCreateValueParameter.setDefaultValue(irExpressionBodyDeserializeExpressionBody);
            }
            IrValueParameter irValueParameter = irValueParameterCreateValueParameter;
            List<org.jetbrains.kotlin.backend.common.serialization.proto.IrConstructorCall> annotationList = base.getAnnotationList();
            Intrinsics.checkNotNullExpressionValue(annotationList, "getAnnotationList(...)");
            irValueParameter.setAnnotations(deserializeAnnotations$ir_serialization_common(annotationList));
            if (setParent) {
                irValueParameter.setParent(this.currentParent);
            }
            eraseDelegatedSymbol(irSymbolComponent1);
            return irValueParameter;
        } catch (Throwable th) {
            eraseDelegatedSymbol(irSymbolComponent1);
            throw th;
        }
    }

    static /* synthetic */ IrClass deserializeIrClass$default(IrDeclarationDeserializer irDeclarationDeserializer, org.jetbrains.kotlin.backend.common.serialization.proto.IrClass irClass, boolean z, int i, Object obj) {
        if ((i & 2) != 0) {
            z = true;
        }
        return irDeclarationDeserializer.deserializeIrClass(irClass, z);
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
    private final IrClass deserializeIrClass(final org.jetbrains.kotlin.backend.common.serialization.proto.IrClass proto, boolean setParent) throws Throwable {
        IrSymbol irSymbol;
        Modality modalityM6229getModalityimpl;
        InlineClassRepresentation<IrSimpleType> inlineClassRepresentationComputeMissingInlineClassRepresentationForCompatibility;
        IrDeclarationBase base = proto.getBase();
        Intrinsics.checkNotNullExpressionValue(base, "getBase(...)");
        Pair<IrSymbol, IdSignature> pairDeserializeIrSymbolToDeclare = getSymbolDeserializer().deserializeIrSymbolToDeclare(base.getSymbol());
        final IrSymbol irSymbolComponent1 = pairDeserializeIrSymbolToDeclare.component1();
        IdSignature idSignatureComponent2 = pairDeserializeIrSymbolToDeclare.component2();
        long jM6180decodeViYoqTc = BinaryCoordinates.INSTANCE.m6180decodeViYoqTc(base.getCoordinates());
        try {
            recordDelegatedSymbol(irSymbolComponent1);
            final int iM6176getStartOffsetimpl = BinaryCoordinates.m6176getStartOffsetimpl(jM6180decodeViYoqTc);
            final int iM6175getEndOffsetimpl = BinaryCoordinates.m6175getEndOffsetimpl(jM6180decodeViYoqTc);
            final IrDeclarationOrigin irDeclarationOriginDeserializeIrDeclarationOrigin = deserializeIrDeclarationOrigin(base.getOriginName());
            final long jM6241decodeK2PyDQs = ClassFlags.INSTANCE.m6241decodeK2PyDQs(base.getFlags());
            if (ClassFlags.m6228getKindimpl(jM6241decodeK2PyDQs) == ClassKind.ANNOTATION_CLASS) {
                modalityM6229getModalityimpl = Modality.OPEN;
            } else {
                modalityM6229getModalityimpl = ClassFlags.m6229getModalityimpl(jM6241decodeK2PyDQs);
            }
            final Modality modality = modalityM6229getModalityimpl;
            irSymbol = irSymbolComponent1;
            try {
                IrClass irClassDeclareClass = this.symbolTable.declareClass(idSignatureComponent2, new Function0<IrClassSymbol>() { // from class: org.jetbrains.kotlin.backend.common.serialization.IrDeclarationDeserializer$deserializeIrClass$1$1
                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    {
                        super(0);
                    }

                    @Override // kotlin.jvm.functions.Function0
                    public final IrClassSymbol invoke() throws IrSymbolTypeMismatchException {
                        IrDeclarationDeserializer irDeclarationDeserializer = this.this$0;
                        IrClassSymbol irClassSymbol = irSymbolComponent1;
                        BinarySymbolData.SymbolKind symbolKind = BinarySymbolData.SymbolKind.CLASS_SYMBOL;
                        if (!(irClassSymbol instanceof IrClassSymbol)) {
                            if (!irDeclarationDeserializer.partialLinkageEnabled) {
                                throw new IrSymbolTypeMismatchException(IrClassSymbol.class, irClassSymbol);
                            }
                            ReferenceSymbolTable symbolTable = irDeclarationDeserializer.getSymbolDeserializer().getSymbolTable();
                            if (symbolKind == null) {
                                throw new IllegalStateException(("No fallback symbol kind specified for symbol " + irClassSymbol).toString());
                            }
                            IdSignature signature = irClassSymbol.getSignature();
                            if (signature != null) {
                                if (!signature.isPubliclyVisible()) {
                                    signature = null;
                                }
                                if (signature != null) {
                                    IrSymbol irSymbolReferenceDeserializedSymbol = IrSymbolDeserializerKt.referenceDeserializedSymbol(symbolTable, null, symbolKind, signature);
                                    if (irSymbolReferenceDeserializedSymbol == null) {
                                        throw new NullPointerException("null cannot be cast to non-null type org.jetbrains.kotlin.ir.symbols.IrClassSymbol");
                                    }
                                    irClassSymbol = (IrClassSymbol) irSymbolReferenceDeserializedSymbol;
                                }
                            }
                            throw new IllegalStateException(("No public signature for symbol " + irClassSymbol).toString());
                        }
                        return (IrClassSymbol) irClassSymbol;
                    }
                }, new Function1<IrClassSymbol, IrClass>() { // from class: org.jetbrains.kotlin.backend.common.serialization.IrDeclarationDeserializer$deserializeIrClass$1$2
                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    {
                        super(1);
                    }

                    @Override // kotlin.jvm.functions.Function1
                    public final IrClass invoke(IrClassSymbol it) {
                        Intrinsics.checkNotNullParameter(it, "it");
                        return IrFactory.DefaultImpls.createClass$default(this.this$0.getIrFactory(), iM6176getStartOffsetimpl, iM6175getEndOffsetimpl, irDeclarationOriginDeserializeIrDeclarationOrigin, this.this$0.deserializeName(proto.getName()), ClassFlags.m6230getVisibilityimpl(jM6241decodeK2PyDQs), it, ClassFlags.m6228getKindimpl(jM6241decodeK2PyDQs), modality, ClassFlags.m6235isExternalimpl(jM6241decodeK2PyDQs) || this.this$0.isEffectivelyExternal, ClassFlags.m6232isCompanionimpl(jM6241decodeK2PyDQs), ClassFlags.m6237isInnerimpl(jM6241decodeK2PyDQs), ClassFlags.m6233isDataimpl(jM6241decodeK2PyDQs), ClassFlags.m6238isValueimpl(jM6241decodeK2PyDQs), ClassFlags.m6234isExpectimpl(jM6241decodeK2PyDQs), ClassFlags.m6236isFunimpl(jM6241decodeK2PyDQs), (SourceElement) null, 32768, (Object) null);
                    }
                });
                IrDeclarationParent irDeclarationParent = this.currentParent;
                this.currentParent = irClassDeclareClass;
                try {
                    IrClass irClass = irClassDeclareClass;
                    List<org.jetbrains.kotlin.backend.common.serialization.proto.IrTypeParameter> typeParameterList = proto.getTypeParameterList();
                    Intrinsics.checkNotNullExpressionValue(typeParameterList, "getTypeParameterList(...)");
                    irClass.setTypeParameters(deserializeTypeParameters(typeParameterList, true));
                    List<Integer> superTypeList = proto.getSuperTypeList();
                    Intrinsics.checkNotNullExpressionValue(superTypeList, "getSuperTypeList(...)");
                    List<Integer> list = superTypeList;
                    ArrayList arrayList = new ArrayList(list.size());
                    for (Integer num : list) {
                        Intrinsics.checkNotNull(num);
                        arrayList.add(deserializeIrType(num.intValue()));
                    }
                    irClass.setSuperTypes(org.jetbrains.kotlin.utils.CollectionsKt.compactIfPossible(arrayList));
                    boolean zIsExternal = irClass.getIsExternal();
                    boolean z = this.isEffectivelyExternal;
                    this.isEffectivelyExternal = zIsExternal;
                    try {
                        Set set = CollectionsKt.toSet(irClass.getDeclarations());
                        List<IrDeclaration> declarationList = proto.getDeclarationList();
                        Intrinsics.checkNotNullExpressionValue(declarationList, "getDeclarationList(...)");
                        ArrayList arrayList2 = new ArrayList();
                        for (Object obj : declarationList) {
                            IrDeclaration irDeclaration = (IrDeclaration) obj;
                            Intrinsics.checkNotNull(irDeclaration);
                            if (!isSkippableFakeOverride(irDeclaration, irClass)) {
                                arrayList2.add(obj);
                            }
                        }
                        List<org.jetbrains.kotlin.ir.declarations.IrDeclaration> declarations = irClass.getDeclarations();
                        Iterator it = arrayList2.iterator();
                        while (true) {
                            inlineClassRepresentationComputeMissingInlineClassRepresentationForCompatibility = null;
                            if (!it.hasNext()) {
                                break;
                            }
                            IrDeclaration irDeclaration2 = (IrDeclaration) it.next();
                            Intrinsics.checkNotNull(irDeclaration2);
                            org.jetbrains.kotlin.ir.declarations.IrDeclaration irDeclarationDeserializeDeclaration$default = deserializeDeclaration$default(this, irDeclaration2, false, 2, null);
                            org.jetbrains.kotlin.ir.declarations.IrDeclaration irDeclaration3 = set.contains(irDeclarationDeserializeDeclaration$default) ? null : irDeclarationDeserializeDeclaration$default;
                            if (irDeclaration3 != null) {
                                declarations.add(irDeclaration3);
                            }
                            eraseDelegatedSymbol(irSymbol);
                            throw th;
                        }
                        this.isEffectivelyExternal = z;
                        org.jetbrains.kotlin.backend.common.serialization.proto.IrValueParameter thisReceiver = proto.getThisReceiver();
                        Intrinsics.checkNotNullExpressionValue(thisReceiver, "getThisReceiver(...)");
                        irClass.setThisReceiver(deserializeIrValueParameter$default(this, thisReceiver, -1, false, 4, null));
                        if (ClassFlags.m6238isValueimpl(jM6241decodeK2PyDQs)) {
                            if (proto.hasMultiFieldValueClassRepresentation() && proto.hasInlineClassRepresentation()) {
                                throw new IllegalStateException(("Class cannot be both inline and multi-field value: " + irClass.getName()).toString());
                            }
                            if (proto.hasInlineClassRepresentation()) {
                                IrInlineClassRepresentation inlineClassRepresentation = proto.getInlineClassRepresentation();
                                Intrinsics.checkNotNullExpressionValue(inlineClassRepresentation, "getInlineClassRepresentation(...)");
                                inlineClassRepresentationComputeMissingInlineClassRepresentationForCompatibility = deserializeInlineClassRepresentation(inlineClassRepresentation);
                            } else if (proto.hasMultiFieldValueClassRepresentation()) {
                                IrMultiFieldValueClassRepresentation multiFieldValueClassRepresentation = proto.getMultiFieldValueClassRepresentation();
                                Intrinsics.checkNotNullExpressionValue(multiFieldValueClassRepresentation, "getMultiFieldValueClassRepresentation(...)");
                                inlineClassRepresentationComputeMissingInlineClassRepresentationForCompatibility = deserializeMultiFieldValueClassRepresentation(multiFieldValueClassRepresentation);
                            } else {
                                inlineClassRepresentationComputeMissingInlineClassRepresentationForCompatibility = computeMissingInlineClassRepresentationForCompatibility(irClass);
                            }
                        }
                        irClass.setValueClassRepresentation(inlineClassRepresentationComputeMissingInlineClassRepresentationForCompatibility);
                        this.fakeOverrideBuilder.enqueueClass(irClass, idSignatureComponent2, this.compatibilityMode);
                        this.currentParent = irDeclarationParent;
                        IrClass irClass2 = irClassDeclareClass;
                        List<org.jetbrains.kotlin.backend.common.serialization.proto.IrConstructorCall> annotationList = base.getAnnotationList();
                        Intrinsics.checkNotNullExpressionValue(annotationList, "getAnnotationList(...)");
                        irClass2.setAnnotations(deserializeAnnotations$ir_serialization_common(annotationList));
                        if (setParent) {
                            irClass2.setParent(this.currentParent);
                        }
                        eraseDelegatedSymbol(irSymbol);
                        return irClass2;
                    } catch (Throwable th) {
                        try {
                            this.isEffectivelyExternal = z;
                            throw th;
                        } catch (Throwable th2) {
                            th = th2;
                            try {
                                this.currentParent = irDeclarationParent;
                                throw th;
                            } catch (Throwable th3) {
                                th = th3;
                            }
                        }
                    }
                } catch (Throwable th4) {
                    th = th4;
                }
            } catch (Throwable th5) {
                th = th5;
                irSymbol = irSymbol;
            }
        } catch (Throwable th6) {
            th = th6;
            irSymbol = irSymbolComponent1;
        }
    }

    private final InlineClassRepresentation<IrSimpleType> deserializeInlineClassRepresentation(IrInlineClassRepresentation proto) {
        Name nameDeserializeName = deserializeName(proto.getUnderlyingPropertyName());
        IrType irTypeDeserializeIrType = deserializeIrType(proto.getUnderlyingPropertyType());
        Intrinsics.checkNotNull(irTypeDeserializeIrType, "null cannot be cast to non-null type org.jetbrains.kotlin.ir.types.IrSimpleType");
        return new InlineClassRepresentation<>(nameDeserializeName, (IrSimpleType) irTypeDeserializeIrType);
    }

    private final MultiFieldValueClassRepresentation<IrSimpleType> deserializeMultiFieldValueClassRepresentation(IrMultiFieldValueClassRepresentation proto) {
        List<Integer> underlyingPropertyNameList = proto.getUnderlyingPropertyNameList();
        Intrinsics.checkNotNullExpressionValue(underlyingPropertyNameList, "getUnderlyingPropertyNameList(...)");
        List<Integer> list = underlyingPropertyNameList;
        ArrayList arrayList = new ArrayList(list.size());
        for (Integer num : list) {
            Intrinsics.checkNotNull(num);
            arrayList.add(deserializeName(num.intValue()));
        }
        List listCompactIfPossible = org.jetbrains.kotlin.utils.CollectionsKt.compactIfPossible(arrayList);
        List<Integer> underlyingPropertyTypeList = proto.getUnderlyingPropertyTypeList();
        Intrinsics.checkNotNullExpressionValue(underlyingPropertyTypeList, "getUnderlyingPropertyTypeList(...)");
        List<Integer> list2 = underlyingPropertyTypeList;
        ArrayList arrayList2 = new ArrayList(list2.size());
        for (Integer num2 : list2) {
            Intrinsics.checkNotNull(num2);
            IrType irTypeDeserializeIrType = deserializeIrType(num2.intValue());
            Intrinsics.checkNotNull(irTypeDeserializeIrType, "null cannot be cast to non-null type org.jetbrains.kotlin.ir.types.IrSimpleType");
            arrayList2.add((IrSimpleType) irTypeDeserializeIrType);
        }
        return new MultiFieldValueClassRepresentation<>(MemoryOptimizedCollectionUtilKt.memoryOptimizedZip(listCompactIfPossible, org.jetbrains.kotlin.utils.CollectionsKt.compactIfPossible(arrayList2)));
    }

    private final InlineClassRepresentation<IrSimpleType> computeMissingInlineClassRepresentationForCompatibility(IrClass irClass) {
        IrConstructor primaryConstructor = IrUtilsKt.getPrimaryConstructor(irClass);
        if (primaryConstructor == null) {
            throw new IllegalStateException(("Inline class has no primary constructor: " + RenderIrElementKt.render$default(irClass, (DumpIrTreeOptions) null, 1, (Object) null)).toString());
        }
        IrValueParameter irValueParameter = (IrValueParameter) CollectionsKt.singleOrNull((List) primaryConstructor.getValueParameters());
        if (irValueParameter == null) {
            throw new IllegalStateException(("Failed to get single parameter of inline class constructor: " + RenderIrElementKt.render$default(primaryConstructor, (DumpIrTreeOptions) null, 1, (Object) null)).toString());
        }
        Name name2 = irValueParameter.getName();
        IrType type = irValueParameter.getType();
        Intrinsics.checkNotNull(type, "null cannot be cast to non-null type org.jetbrains.kotlin.ir.types.IrSimpleType");
        return new InlineClassRepresentation<>(name2, (IrSimpleType) type);
    }

    private final IrTypeAlias deserializeIrTypeAlias(final org.jetbrains.kotlin.backend.common.serialization.proto.IrTypeAlias proto, boolean setParent) {
        IrDeclarationBase base = proto.getBase();
        Intrinsics.checkNotNullExpressionValue(base, "getBase(...)");
        Pair<IrSymbol, IdSignature> pairDeserializeIrSymbolToDeclare = getSymbolDeserializer().deserializeIrSymbolToDeclare(base.getSymbol());
        final IrSymbol irSymbolComponent1 = pairDeserializeIrSymbolToDeclare.component1();
        IdSignature idSignatureComponent2 = pairDeserializeIrSymbolToDeclare.component2();
        long jM6180decodeViYoqTc = BinaryCoordinates.INSTANCE.m6180decodeViYoqTc(base.getCoordinates());
        try {
            recordDelegatedSymbol(irSymbolComponent1);
            final int iM6176getStartOffsetimpl = BinaryCoordinates.m6176getStartOffsetimpl(jM6180decodeViYoqTc);
            final int iM6175getEndOffsetimpl = BinaryCoordinates.m6175getEndOffsetimpl(jM6180decodeViYoqTc);
            final IrDeclarationOrigin irDeclarationOriginDeserializeIrDeclarationOrigin = deserializeIrDeclarationOrigin(base.getOriginName());
            final long flags = base.getFlags();
            IrTypeAlias irTypeAliasDeclareTypeAlias = this.symbolTable.declareTypeAlias(idSignatureComponent2, new Function0<IrTypeAliasSymbol>() { // from class: org.jetbrains.kotlin.backend.common.serialization.IrDeclarationDeserializer$deserializeIrTypeAlias$1$1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.Function0
                public final IrTypeAliasSymbol invoke() throws IrSymbolTypeMismatchException {
                    IrDeclarationDeserializer irDeclarationDeserializer = this.this$0;
                    IrTypeAliasSymbol irTypeAliasSymbol = irSymbolComponent1;
                    BinarySymbolData.SymbolKind symbolKind = BinarySymbolData.SymbolKind.TYPEALIAS_SYMBOL;
                    if (!(irTypeAliasSymbol instanceof IrTypeAliasSymbol)) {
                        if (!irDeclarationDeserializer.partialLinkageEnabled) {
                            throw new IrSymbolTypeMismatchException(IrTypeAliasSymbol.class, irTypeAliasSymbol);
                        }
                        ReferenceSymbolTable symbolTable = irDeclarationDeserializer.getSymbolDeserializer().getSymbolTable();
                        if (symbolKind == null) {
                            throw new IllegalStateException(("No fallback symbol kind specified for symbol " + irTypeAliasSymbol).toString());
                        }
                        IdSignature signature = irTypeAliasSymbol.getSignature();
                        if (signature != null) {
                            if (!signature.isPubliclyVisible()) {
                                signature = null;
                            }
                            if (signature != null) {
                                IrSymbol irSymbolReferenceDeserializedSymbol = IrSymbolDeserializerKt.referenceDeserializedSymbol(symbolTable, null, symbolKind, signature);
                                if (irSymbolReferenceDeserializedSymbol == null) {
                                    throw new NullPointerException("null cannot be cast to non-null type org.jetbrains.kotlin.ir.symbols.IrTypeAliasSymbol");
                                }
                                irTypeAliasSymbol = (IrTypeAliasSymbol) irSymbolReferenceDeserializedSymbol;
                            }
                        }
                        throw new IllegalStateException(("No public signature for symbol " + irTypeAliasSymbol).toString());
                    }
                    return (IrTypeAliasSymbol) irTypeAliasSymbol;
                }
            }, new Function1<IrTypeAliasSymbol, IrTypeAlias>() { // from class: org.jetbrains.kotlin.backend.common.serialization.IrDeclarationDeserializer$deserializeIrTypeAlias$1$2
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(1);
                }

                @Override // kotlin.jvm.functions.Function1
                public final IrTypeAlias invoke(IrTypeAliasSymbol it) {
                    Intrinsics.checkNotNullParameter(it, "it");
                    long jM6312decodeOrzgV44 = TypeAliasFlags.INSTANCE.m6312decodeOrzgV44(flags);
                    long jM6200decodeWXC2TjU = BinaryNameAndType.INSTANCE.m6200decodeWXC2TjU(proto.getNameType());
                    return this.getIrFactory().createTypeAlias(iM6176getStartOffsetimpl, iM6175getEndOffsetimpl, irDeclarationOriginDeserializeIrDeclarationOrigin, this.deserializeName(BinaryNameAndType.m6195getNameIndeximpl(jM6200decodeWXC2TjU)), TypeAliasFlags.m6307getVisibilityimpl(jM6312decodeOrzgV44), it, TypeAliasFlags.m6309isActualimpl(jM6312decodeOrzgV44), this.deserializeIrType(BinaryNameAndType.m6196getTypeIndeximpl(jM6200decodeWXC2TjU)));
                }
            });
            IrDeclarationParent irDeclarationParent = this.currentParent;
            this.currentParent = irTypeAliasDeclareTypeAlias;
            try {
                List<org.jetbrains.kotlin.backend.common.serialization.proto.IrTypeParameter> typeParameterList = proto.getTypeParameterList();
                Intrinsics.checkNotNullExpressionValue(typeParameterList, "getTypeParameterList(...)");
                irTypeAliasDeclareTypeAlias.setTypeParameters(deserializeTypeParameters(typeParameterList, true));
                this.currentParent = irDeclarationParent;
                IrTypeAlias irTypeAlias = irTypeAliasDeclareTypeAlias;
                List<org.jetbrains.kotlin.backend.common.serialization.proto.IrConstructorCall> annotationList = base.getAnnotationList();
                Intrinsics.checkNotNullExpressionValue(annotationList, "getAnnotationList(...)");
                irTypeAlias.setAnnotations(deserializeAnnotations$ir_serialization_common(annotationList));
                if (setParent) {
                    irTypeAlias.setParent(this.currentParent);
                }
                eraseDelegatedSymbol(irSymbolComponent1);
                return irTypeAlias;
            } catch (Throwable th) {
                this.currentParent = irDeclarationParent;
                throw th;
            }
        } catch (Throwable th2) {
            eraseDelegatedSymbol(irSymbolComponent1);
            throw th2;
        }
    }

    private final IrErrorDeclaration deserializeErrorDeclaration(org.jetbrains.kotlin.backend.common.serialization.proto.IrErrorDeclaration proto, boolean setParent) throws IrDisallowedErrorNode {
        if (!this.allowErrorNodes) {
            throw new IrDisallowedErrorNode(IrErrorDeclaration.class);
        }
        long jM6180decodeViYoqTc = BinaryCoordinates.INSTANCE.m6180decodeViYoqTc(proto.getCoordinates());
        IrErrorDeclaration irErrorDeclarationCreateErrorDeclaration$default = IrFactory.DefaultImpls.createErrorDeclaration$default(this.irFactory, BinaryCoordinates.m6176getStartOffsetimpl(jM6180decodeViYoqTc), BinaryCoordinates.m6175getEndOffsetimpl(jM6180decodeViYoqTc), null, 4, null);
        if (setParent) {
            irErrorDeclarationCreateErrorDeclaration$default.setParent(this.currentParent);
        }
        return irErrorDeclarationCreateErrorDeclaration$default;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final List<IrTypeParameter> deserializeTypeParameters(List<org.jetbrains.kotlin.backend.common.serialization.proto.IrTypeParameter> protos, boolean isGlobal) {
        List<org.jetbrains.kotlin.backend.common.serialization.proto.IrTypeParameter> list = protos;
        ArrayList arrayList = new ArrayList(list.size());
        int i = 0;
        for (Object obj : list) {
            int i2 = i + 1;
            if (i < 0) {
                CollectionsKt.throwIndexOverflow();
            }
            org.jetbrains.kotlin.backend.common.serialization.proto.IrTypeParameter irTypeParameter = (org.jetbrains.kotlin.backend.common.serialization.proto.IrTypeParameter) obj;
            IrTypeParameter irTypeParameterDeserializeIrTypeParameter$default = deserializeIrTypeParameter$default(this, irTypeParameter, i, isGlobal, false, 8, null);
            List<Integer> superTypeList = irTypeParameter.getSuperTypeList();
            Intrinsics.checkNotNullExpressionValue(superTypeList, "getSuperTypeList(...)");
            List<Integer> list2 = superTypeList;
            ArrayList arrayList2 = new ArrayList(list2.size());
            for (Integer num : list2) {
                Intrinsics.checkNotNull(num);
                arrayList2.add(deserializeIrType(num.intValue()));
            }
            irTypeParameterDeserializeIrTypeParameter$default.setSuperTypes(org.jetbrains.kotlin.utils.CollectionsKt.compactIfPossible(arrayList2));
            arrayList.add(irTypeParameterDeserializeIrTypeParameter$default);
            i = i2;
        }
        return org.jetbrains.kotlin.utils.CollectionsKt.compactIfPossible(arrayList);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final List<IrValueParameter> deserializeValueParameters(List<org.jetbrains.kotlin.backend.common.serialization.proto.IrValueParameter> protos) {
        List<org.jetbrains.kotlin.backend.common.serialization.proto.IrValueParameter> list = protos;
        ArrayList arrayList = new ArrayList(list.size());
        int i = 0;
        for (Object obj : list) {
            int i2 = i + 1;
            if (i < 0) {
                CollectionsKt.throwIndexOverflow();
            }
            arrayList.add(deserializeIrValueParameter$default(this, (org.jetbrains.kotlin.backend.common.serialization.proto.IrValueParameter) obj, i, false, 4, null));
            i = i2;
        }
        return org.jetbrains.kotlin.utils.CollectionsKt.compactIfPossible(arrayList);
    }

    private final boolean checkObjectLeak(IrType irType) {
        if (!(irType instanceof IrSimpleType)) {
            return false;
        }
        IrSimpleType irSimpleType = (IrSimpleType) irType;
        IdSignature signature = irSimpleType.getClassifier().getSignature();
        if ((signature != null && !signature.isLocal()) || (irSimpleType.getClassifier() instanceof IrTypeParameterSymbol)) {
            List<IrTypeArgument> arguments = irSimpleType.getArguments();
            if ((arguments instanceof Collection) && arguments.isEmpty()) {
                return false;
            }
            Iterator<T> it = arguments.iterator();
            while (it.hasNext()) {
                IrType typeOrNull = IrTypesKt.getTypeOrNull((IrTypeArgument) it.next());
                if (typeOrNull == null || !checkObjectLeak(typeOrNull)) {
                }
            }
            return false;
        }
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Code duplicated, block: B:12:0x0019  */
    public final <T extends IrFunction> void withBodyGuard(T t, Function1<? super T, Unit> function1) {
        boolean z;
        boolean z2 = this.deserializeBodies;
        if (z2) {
            z = true;
        } else {
            try {
                if (withBodyGuard$checkInlineBody(this, t) || checkObjectLeak(t.getReturnType())) {
                    z = true;
                } else {
                    z = false;
                }
            } finally {
                this.deserializeBodies = z2;
            }
        }
        this.deserializeBodies = z;
        function1.invoke(t);
    }

    private static final <T extends IrFunction> boolean withBodyGuard$checkInlineBody(IrDeclarationDeserializer irDeclarationDeserializer, T t) {
        return irDeclarationDeserializer.deserializeInlineFunctions && (t instanceof IrSimpleFunction) && t.getIsInline();
    }

    private final void withInitializerGuard(IrField irField, boolean z, Function1<? super IrField, Unit> function1) {
        boolean z2;
        boolean z3 = this.deserializeBodies;
        if (!z && !z3) {
            try {
                z2 = checkObjectLeak(irField.getType());
            } finally {
                this.deserializeBodies = z3;
            }
        }
        this.deserializeBodies = z2;
        function1.invoke(irField);
    }

    private final IrStatement loadStatementBodyProto(int index) {
        return this.libraryFile.statementBody(index);
    }

    private final IrExpression loadExpressionBodyProto(int index) {
        return this.libraryFile.expressionBody(index);
    }

    public final IrExpressionBody deserializeExpressionBody(int index) {
        if (!this.deserializeBodies) {
            return null;
        }
        return IrFactoryHelpersKt.createExpressionBody(this.irFactory, this.bodyDeserializer.deserializeExpression(loadExpressionBodyProto(index)));
    }

    public final IrElement deserializeStatementBody(int index) {
        if (!this.deserializeBodies) {
            return null;
        }
        return this.bodyDeserializer.deserializeStatement$ir_serialization_common(loadStatementBodyProto(index));
    }

    public final <T extends IrFunction> void withDeserializeBodies(T t, Function1<? super T, Unit> block) {
        Intrinsics.checkNotNullParameter(t, "<this>");
        Intrinsics.checkNotNullParameter(block, "block");
        boolean z = this.deserializeBodies;
        try {
            this.deserializeBodies = true;
            T t2 = t;
            IrDeclarationParent irDeclarationParent = this.currentParent;
            this.currentParent = t2;
            try {
                block.invoke(t2);
                this.currentParent = irDeclarationParent;
                this.deserializeBodies = z;
            } catch (Throwable th) {
                this.currentParent = irDeclarationParent;
                throw th;
            }
        } catch (Throwable th2) {
            this.deserializeBodies = z;
            throw th2;
        }
    }

    public static /* synthetic */ IrSimpleFunction deserializeIrFunction$ir_serialization_common$default(IrDeclarationDeserializer irDeclarationDeserializer, org.jetbrains.kotlin.backend.common.serialization.proto.IrFunction irFunction, boolean z, int i, Object obj) {
        if ((i & 2) != 0) {
            z = true;
        }
        return irDeclarationDeserializer.deserializeIrFunction$ir_serialization_common(irFunction, z);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r2v1, types: [org.jetbrains.kotlin.backend.common.serialization.encodings.FunctionFlags$Companion] */
    /* JADX WARN: Type inference failed for: r3v17 */
    /* JADX WARN: Type inference failed for: r3v21, types: [boolean] */
    /* JADX WARN: Type inference failed for: r3v24, types: [org.jetbrains.kotlin.ir.declarations.IrFunction] */
    /* JADX WARN: Type inference failed for: r3v4 */
    /* JADX WARN: Type inference failed for: r3v5, types: [org.jetbrains.kotlin.ir.symbols.IrSymbol] */
    /* JADX WARN: Type inference failed for: r3v6, types: [long] */
    /* JADX WARN: Type inference failed for: r3v7 */
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
    public final IrSimpleFunction deserializeIrFunction$ir_serialization_common(final org.jetbrains.kotlin.backend.common.serialization.proto.IrFunction proto, boolean setParent) throws Throwable {
        IrSimpleFunction flags;
        IrSimpleFunctionSymbol irSimpleFunctionSymbol;
        Intrinsics.checkNotNullParameter(proto, "proto");
        IrFunctionBase base = proto.getBase();
        Intrinsics.checkNotNullExpressionValue(base, "getBase(...)");
        BinarySymbolData.SymbolKind symbolKind = BinarySymbolData.SymbolKind.FUNCTION_SYMBOL;
        IrDeclarationBase base2 = base.getBase();
        Intrinsics.checkNotNullExpressionValue(base2, "getBase(...)");
        Pair<IrSymbol, IdSignature> pairDeserializeIrSymbolToDeclare = getSymbolDeserializer().deserializeIrSymbolToDeclare(base2.getSymbol());
        IrSymbol irSymbolComponent1 = pairDeserializeIrSymbolToDeclare.component1();
        IdSignature idSignatureComponent2 = pairDeserializeIrSymbolToDeclare.component2();
        long jM6180decodeViYoqTc = BinaryCoordinates.INSTANCE.m6180decodeViYoqTc(base2.getCoordinates());
        try {
            recordDelegatedSymbol(irSymbolComponent1);
            final int iM6176getStartOffsetimpl = BinaryCoordinates.m6176getStartOffsetimpl(jM6180decodeViYoqTc);
            final int iM6175getEndOffsetimpl = BinaryCoordinates.m6175getEndOffsetimpl(jM6180decodeViYoqTc);
            final IrDeclarationOrigin irDeclarationOriginDeserializeIrDeclarationOrigin = deserializeIrDeclarationOrigin(base2.getOriginName());
            flags = base2.getFlags();
            String str = "No public signature for symbol ";
            try {
                if (!(irSymbolComponent1 instanceof IrSimpleFunctionSymbol)) {
                    if (!this.partialLinkageEnabled) {
                        throw new IrSymbolTypeMismatchException(IrSimpleFunctionSymbol.class, irSymbolComponent1);
                    }
                    ReferenceSymbolTable symbolTable = getSymbolDeserializer().getSymbolTable();
                    if (symbolKind == null) {
                        throw new IllegalStateException(("No fallback symbol kind specified for symbol " + irSymbolComponent1).toString());
                    }
                    IdSignature signature = irSymbolComponent1.getSignature();
                    if (signature != null) {
                        IdSignature idSignature = signature.isPubliclyVisible() ? signature : null;
                        if (idSignature != null) {
                            IrSymbol irSymbolReferenceDeserializedSymbol = IrSymbolDeserializerKt.referenceDeserializedSymbol(symbolTable, null, symbolKind, idSignature);
                            if (irSymbolReferenceDeserializedSymbol == null) {
                                throw new NullPointerException("null cannot be cast to non-null type org.jetbrains.kotlin.ir.symbols.IrSimpleFunctionSymbol");
                            }
                            irSimpleFunctionSymbol = (IrSimpleFunctionSymbol) irSymbolReferenceDeserializedSymbol;
                        }
                    }
                    throw new IllegalStateException((str + irSymbolComponent1).toString());
                }
                str = str;
                irSimpleFunctionSymbol = irSymbolComponent1;
                IrFunctionSymbol irFunctionSymbol = (IrFunctionSymbol) irSimpleFunctionSymbol;
                SymbolTable symbolTable2 = this.symbolTable;
                symbolTable2.enterScope(irFunctionSymbol);
                final IrSimpleFunctionSymbol irSimpleFunctionSymbol2 = (IrSimpleFunctionSymbol) irFunctionSymbol;
                final long jM6273decodeUIJRpeM = FunctionFlags.INSTANCE.m6273decodeUIJRpeM(flags);
                String str2 = str;
                try {
                    IrSimpleFunction irSimpleFunctionDeclareSimpleFunction = this.symbolTable.declareSimpleFunction(idSignatureComponent2, new Function0<IrSimpleFunctionSymbol>() { // from class: org.jetbrains.kotlin.backend.common.serialization.IrDeclarationDeserializer$deserializeIrFunction$1$1
                        {
                            super(0);
                        }

                        @Override // kotlin.jvm.functions.Function0
                        public final IrSimpleFunctionSymbol invoke() {
                            return irSimpleFunctionSymbol2;
                        }
                    }, new Function1<IrSimpleFunctionSymbol, IrSimpleFunction>() { // from class: org.jetbrains.kotlin.backend.common.serialization.IrDeclarationDeserializer$deserializeIrFunction$1$2
                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                        {
                            super(1);
                        }

                        @Override // kotlin.jvm.functions.Function1
                        public final IrSimpleFunction invoke(IrSimpleFunctionSymbol it) {
                            Intrinsics.checkNotNullParameter(it, "it");
                            return IrFactory.DefaultImpls.createSimpleFunction$default(this.getIrFactory(), iM6176getStartOffsetimpl, iM6175getEndOffsetimpl, irDeclarationOriginDeserializeIrDeclarationOrigin, this.deserializeName(BinaryNameAndType.m6195getNameIndeximpl(BinaryNameAndType.INSTANCE.m6200decodeWXC2TjU(proto.getBase().getNameType()))), FunctionFlags.m6259getVisibilityimpl(jM6273decodeUIJRpeM), FunctionFlags.m6265isInlineimpl(jM6273decodeUIJRpeM), FunctionFlags.m6261isExpectimpl(jM6273decodeUIJRpeM), IrUninitializedType.INSTANCE, FunctionFlags.m6258getModalityimpl(jM6273decodeUIJRpeM), it, FunctionFlags.m6269isTailrecimpl(jM6273decodeUIJRpeM), FunctionFlags.m6268isSuspendimpl(jM6273decodeUIJRpeM), FunctionFlags.m6266isOperatorimpl(jM6273decodeUIJRpeM), FunctionFlags.m6264isInfiximpl(jM6273decodeUIJRpeM), FunctionFlags.m6262isExternalimpl(jM6273decodeUIJRpeM) || this.isEffectivelyExternal, null, FunctionFlags.m6263isFakeOverrideimpl(jM6273decodeUIJRpeM), 32768, null);
                        }
                    });
                    List<Long> overriddenList = proto.getOverriddenList();
                    Intrinsics.checkNotNullExpressionValue(overriddenList, "getOverriddenList(...)");
                    List<Long> list = overriddenList;
                    ArrayList arrayList = new ArrayList(list.size());
                    Iterator it = list.iterator();
                    while (true) {
                        flags = it.hasNext();
                        if (flags == 0) {
                            irSimpleFunctionDeclareSimpleFunction.setOverriddenSymbols(org.jetbrains.kotlin.utils.CollectionsKt.compactIfPossible(arrayList));
                            IrSimpleFunction irSimpleFunction = irSimpleFunctionDeclareSimpleFunction;
                            IrDeclarationParent irDeclarationParent = this.currentParent;
                            this.currentParent = irSimpleFunction;
                            try {
                                flags = irSimpleFunction;
                                List<org.jetbrains.kotlin.backend.common.serialization.proto.IrTypeParameter> typeParameterList = base.getTypeParameterList();
                                Intrinsics.checkNotNullExpressionValue(typeParameterList, "getTypeParameterList(...)");
                                flags.setTypeParameters(deserializeTypeParameters(typeParameterList, false));
                                flags.setReturnType(deserializeIrType(BinaryNameAndType.m6196getTypeIndeximpl(BinaryNameAndType.INSTANCE.m6200decodeWXC2TjU(base.getNameType()))));
                                withBodyGuard(flags, new IrDeclarationDeserializer$withDeserializedIrFunctionBase$1$1$1$1(this, base));
                                this.currentParent = irDeclarationParent;
                                symbolTable2.leaveScope(irFunctionSymbol);
                                IrSimpleFunction irSimpleFunction2 = irSimpleFunction;
                                List<org.jetbrains.kotlin.backend.common.serialization.proto.IrConstructorCall> annotationList = base2.getAnnotationList();
                                Intrinsics.checkNotNullExpressionValue(annotationList, "getAnnotationList(...)");
                                irSimpleFunction2.setAnnotations(deserializeAnnotations$ir_serialization_common(annotationList));
                                if (setParent) {
                                    irSimpleFunction2.setParent(this.currentParent);
                                }
                                eraseDelegatedSymbol(irSymbolComponent1);
                                return irSimpleFunction2;
                            } catch (Throwable th) {
                                this.currentParent = irDeclarationParent;
                                throw th;
                            }
                        }
                        Long l = (Long) it.next();
                        Intrinsics.checkNotNull(l);
                        IrSimpleFunctionSymbol irSimpleFunctionSymbolDeserializeIrSymbolAndRemap$ir_serialization_common = deserializeIrSymbolAndRemap$ir_serialization_common(l.longValue());
                        BinarySymbolData.SymbolKind symbolKind2 = BinarySymbolData.SymbolKind.FUNCTION_SYMBOL;
                        if (!(irSimpleFunctionSymbolDeserializeIrSymbolAndRemap$ir_serialization_common instanceof IrSimpleFunctionSymbol)) {
                            if (!this.partialLinkageEnabled) {
                                throw new IrSymbolTypeMismatchException(IrSimpleFunctionSymbol.class, irSimpleFunctionSymbolDeserializeIrSymbolAndRemap$ir_serialization_common);
                            }
                            ReferenceSymbolTable symbolTable3 = getSymbolDeserializer().getSymbolTable();
                            if (symbolKind2 == null) {
                                throw new IllegalStateException(("No fallback symbol kind specified for symbol " + irSimpleFunctionSymbolDeserializeIrSymbolAndRemap$ir_serialization_common).toString());
                            }
                            IdSignature signature2 = irSimpleFunctionSymbolDeserializeIrSymbolAndRemap$ir_serialization_common.getSignature();
                            if (signature2 != null) {
                                if (!signature2.isPubliclyVisible()) {
                                    signature2 = null;
                                }
                                if (signature2 != null) {
                                    IrSymbol irSymbolReferenceDeserializedSymbol2 = IrSymbolDeserializerKt.referenceDeserializedSymbol(symbolTable3, null, symbolKind2, signature2);
                                    if (irSymbolReferenceDeserializedSymbol2 == null) {
                                        throw new NullPointerException("null cannot be cast to non-null type org.jetbrains.kotlin.ir.symbols.IrSimpleFunctionSymbol");
                                    }
                                    irSimpleFunctionSymbolDeserializeIrSymbolAndRemap$ir_serialization_common = (IrSimpleFunctionSymbol) irSymbolReferenceDeserializedSymbol2;
                                }
                            }
                            throw new IllegalStateException((str2 + irSimpleFunctionSymbolDeserializeIrSymbolAndRemap$ir_serialization_common).toString());
                        }
                        arrayList.add((IrSimpleFunctionSymbol) irSimpleFunctionSymbolDeserializeIrSymbolAndRemap$ir_serialization_common);
                        eraseDelegatedSymbol(flags);
                        throw th;
                    }
                } catch (Throwable th2) {
                    th = th2;
                    flags = irSymbolComponent1;
                }
            } catch (Throwable th3) {
                th = th3;
            }
        } catch (Throwable th4) {
            th = th4;
            flags = irSymbolComponent1;
        }
    }

    public static /* synthetic */ IrVariable deserializeIrVariable$default(IrDeclarationDeserializer irDeclarationDeserializer, org.jetbrains.kotlin.backend.common.serialization.proto.IrVariable irVariable, boolean z, int i, Object obj) {
        if ((i & 2) != 0) {
            z = true;
        }
        return irDeclarationDeserializer.deserializeIrVariable(irVariable, z);
    }

    public final IrVariable deserializeIrVariable(org.jetbrains.kotlin.backend.common.serialization.proto.IrVariable proto, boolean setParent) {
        Intrinsics.checkNotNullParameter(proto, "proto");
        IrDeclarationBase base = proto.getBase();
        Intrinsics.checkNotNullExpressionValue(base, "getBase(...)");
        Pair<IrSymbol, IdSignature> pairDeserializeIrSymbolToDeclare = getSymbolDeserializer().deserializeIrSymbolToDeclare(base.getSymbol());
        IrSymbol irSymbolComponent1 = pairDeserializeIrSymbolToDeclare.component1();
        pairDeserializeIrSymbolToDeclare.component2();
        long jM6180decodeViYoqTc = BinaryCoordinates.INSTANCE.m6180decodeViYoqTc(base.getCoordinates());
        try {
            recordDelegatedSymbol(irSymbolComponent1);
            int iM6176getStartOffsetimpl = BinaryCoordinates.m6176getStartOffsetimpl(jM6180decodeViYoqTc);
            int iM6175getEndOffsetimpl = BinaryCoordinates.m6175getEndOffsetimpl(jM6180decodeViYoqTc);
            IrDeclarationOrigin irDeclarationOriginDeserializeIrDeclarationOrigin = deserializeIrDeclarationOrigin(base.getOriginName());
            long jM6284decodeQnIyQoc = LocalVariableFlags.INSTANCE.m6284decodeQnIyQoc(base.getFlags());
            long jM6200decodeWXC2TjU = BinaryNameAndType.INSTANCE.m6200decodeWXC2TjU(proto.getNameType());
            if (!(irSymbolComponent1 instanceof IrVariableSymbol)) {
                if (!this.partialLinkageEnabled) {
                    throw new IrSymbolTypeMismatchException(IrVariableSymbol.class, irSymbolComponent1);
                }
                getSymbolDeserializer().getSymbolTable();
                throw new IllegalStateException(("No fallback symbol kind specified for symbol " + irSymbolComponent1).toString());
            }
            IrVariableImpl irVariableImpl = new IrVariableImpl(iM6176getStartOffsetimpl, iM6175getEndOffsetimpl, irDeclarationOriginDeserializeIrDeclarationOrigin, (IrVariableSymbol) irSymbolComponent1, deserializeName(BinaryNameAndType.m6195getNameIndeximpl(jM6200decodeWXC2TjU)), deserializeIrType(BinaryNameAndType.m6196getTypeIndeximpl(jM6200decodeWXC2TjU)), LocalVariableFlags.m6281isVarimpl(jM6284decodeQnIyQoc), LocalVariableFlags.m6279isConstimpl(jM6284decodeQnIyQoc), LocalVariableFlags.m6280isLateinitimpl(jM6284decodeQnIyQoc));
            if (proto.hasInitializer()) {
                IrBodyDeserializer irBodyDeserializer = this.bodyDeserializer;
                IrExpression initializer = proto.getInitializer();
                Intrinsics.checkNotNullExpressionValue(initializer, "getInitializer(...)");
                irVariableImpl.setInitializer(irBodyDeserializer.deserializeExpression(initializer));
            }
            IrVariableImpl irVariableImpl2 = irVariableImpl;
            List<org.jetbrains.kotlin.backend.common.serialization.proto.IrConstructorCall> annotationList = base.getAnnotationList();
            Intrinsics.checkNotNullExpressionValue(annotationList, "getAnnotationList(...)");
            irVariableImpl2.setAnnotations(deserializeAnnotations$ir_serialization_common(annotationList));
            if (setParent) {
                irVariableImpl2.setParent(this.currentParent);
            }
            eraseDelegatedSymbol(irSymbolComponent1);
            return irVariableImpl2;
        } catch (Throwable th) {
            eraseDelegatedSymbol(irSymbolComponent1);
            throw th;
        }
    }

    private final IrEnumEntry deserializeIrEnumEntry(final org.jetbrains.kotlin.backend.common.serialization.proto.IrEnumEntry proto, boolean setParent) {
        IrDeclarationBase base = proto.getBase();
        Intrinsics.checkNotNullExpressionValue(base, "getBase(...)");
        Pair<IrSymbol, IdSignature> pairDeserializeIrSymbolToDeclare = getSymbolDeserializer().deserializeIrSymbolToDeclare(base.getSymbol());
        final IrSymbol irSymbolComponent1 = pairDeserializeIrSymbolToDeclare.component1();
        IdSignature idSignatureComponent2 = pairDeserializeIrSymbolToDeclare.component2();
        long jM6180decodeViYoqTc = BinaryCoordinates.INSTANCE.m6180decodeViYoqTc(base.getCoordinates());
        try {
            recordDelegatedSymbol(irSymbolComponent1);
            final int iM6176getStartOffsetimpl = BinaryCoordinates.m6176getStartOffsetimpl(jM6180decodeViYoqTc);
            final int iM6175getEndOffsetimpl = BinaryCoordinates.m6175getEndOffsetimpl(jM6180decodeViYoqTc);
            final IrDeclarationOrigin irDeclarationOriginDeserializeIrDeclarationOrigin = deserializeIrDeclarationOrigin(base.getOriginName());
            base.getFlags();
            IrEnumEntry irEnumEntryDeclareEnumEntry = this.symbolTable.declareEnumEntry(idSignatureComponent2, new Function0<IrEnumEntrySymbol>() { // from class: org.jetbrains.kotlin.backend.common.serialization.IrDeclarationDeserializer$deserializeIrEnumEntry$1$1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.Function0
                public final IrEnumEntrySymbol invoke() throws IrSymbolTypeMismatchException {
                    IrDeclarationDeserializer irDeclarationDeserializer = this.this$0;
                    IrEnumEntrySymbol irEnumEntrySymbol = irSymbolComponent1;
                    BinarySymbolData.SymbolKind symbolKind = BinarySymbolData.SymbolKind.ENUM_ENTRY_SYMBOL;
                    if (!(irEnumEntrySymbol instanceof IrEnumEntrySymbol)) {
                        if (!irDeclarationDeserializer.partialLinkageEnabled) {
                            throw new IrSymbolTypeMismatchException(IrEnumEntrySymbol.class, irEnumEntrySymbol);
                        }
                        ReferenceSymbolTable symbolTable = irDeclarationDeserializer.getSymbolDeserializer().getSymbolTable();
                        if (symbolKind == null) {
                            throw new IllegalStateException(("No fallback symbol kind specified for symbol " + irEnumEntrySymbol).toString());
                        }
                        IdSignature signature = irEnumEntrySymbol.getSignature();
                        if (signature != null) {
                            if (!signature.isPubliclyVisible()) {
                                signature = null;
                            }
                            if (signature != null) {
                                IrSymbol irSymbolReferenceDeserializedSymbol = IrSymbolDeserializerKt.referenceDeserializedSymbol(symbolTable, null, symbolKind, signature);
                                if (irSymbolReferenceDeserializedSymbol == null) {
                                    throw new NullPointerException("null cannot be cast to non-null type org.jetbrains.kotlin.ir.symbols.IrEnumEntrySymbol");
                                }
                                irEnumEntrySymbol = (IrEnumEntrySymbol) irSymbolReferenceDeserializedSymbol;
                            }
                        }
                        throw new IllegalStateException(("No public signature for symbol " + irEnumEntrySymbol).toString());
                    }
                    return (IrEnumEntrySymbol) irEnumEntrySymbol;
                }
            }, new Function1<IrEnumEntrySymbol, IrEnumEntry>() { // from class: org.jetbrains.kotlin.backend.common.serialization.IrDeclarationDeserializer$deserializeIrEnumEntry$1$2
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(1);
                }

                @Override // kotlin.jvm.functions.Function1
                public final IrEnumEntry invoke(IrEnumEntrySymbol it) {
                    Intrinsics.checkNotNullParameter(it, "it");
                    return this.this$0.getIrFactory().createEnumEntry(iM6176getStartOffsetimpl, iM6175getEndOffsetimpl, irDeclarationOriginDeserializeIrDeclarationOrigin, this.this$0.deserializeName(proto.getName()), it);
                }
            });
            if (proto.hasCorrespondingClass()) {
                org.jetbrains.kotlin.backend.common.serialization.proto.IrClass correspondingClass = proto.getCorrespondingClass();
                Intrinsics.checkNotNullExpressionValue(correspondingClass, "getCorrespondingClass(...)");
                irEnumEntryDeclareEnumEntry.setCorrespondingClass(deserializeIrClass$default(this, correspondingClass, false, 2, null));
            }
            if (proto.hasInitializer()) {
                irEnumEntryDeclareEnumEntry.setInitializerExpression(deserializeExpressionBody(proto.getInitializer()));
            }
            IrEnumEntry irEnumEntry = irEnumEntryDeclareEnumEntry;
            List<org.jetbrains.kotlin.backend.common.serialization.proto.IrConstructorCall> annotationList = base.getAnnotationList();
            Intrinsics.checkNotNullExpressionValue(annotationList, "getAnnotationList(...)");
            irEnumEntry.setAnnotations(deserializeAnnotations$ir_serialization_common(annotationList));
            if (setParent) {
                irEnumEntry.setParent(this.currentParent);
            }
            return irEnumEntry;
        } finally {
            eraseDelegatedSymbol(irSymbolComponent1);
        }
    }

    private final IrAnonymousInitializer deserializeIrAnonymousInit(IrAnonymousInit proto, boolean setParent) {
        IrDeclarationBase base = proto.getBase();
        Intrinsics.checkNotNullExpressionValue(base, "getBase(...)");
        Pair<IrSymbol, IdSignature> pairDeserializeIrSymbolToDeclare = getSymbolDeserializer().deserializeIrSymbolToDeclare(base.getSymbol());
        IrSymbol irSymbolComponent1 = pairDeserializeIrSymbolToDeclare.component1();
        pairDeserializeIrSymbolToDeclare.component2();
        long jM6180decodeViYoqTc = BinaryCoordinates.INSTANCE.m6180decodeViYoqTc(base.getCoordinates());
        try {
            recordDelegatedSymbol(irSymbolComponent1);
            int iM6176getStartOffsetimpl = BinaryCoordinates.m6176getStartOffsetimpl(jM6180decodeViYoqTc);
            int iM6175getEndOffsetimpl = BinaryCoordinates.m6175getEndOffsetimpl(jM6180decodeViYoqTc);
            IrDeclarationOrigin irDeclarationOriginDeserializeIrDeclarationOrigin = deserializeIrDeclarationOrigin(base.getOriginName());
            base.getFlags();
            IrFactory irFactory = this.irFactory;
            if (!(irSymbolComponent1 instanceof IrAnonymousInitializerSymbol)) {
                if (!this.partialLinkageEnabled) {
                    throw new IrSymbolTypeMismatchException(IrAnonymousInitializerSymbol.class, irSymbolComponent1);
                }
                getSymbolDeserializer().getSymbolTable();
                throw new IllegalStateException(("No fallback symbol kind specified for symbol " + irSymbolComponent1).toString());
            }
            IrAnonymousInitializer irAnonymousInitializerCreateAnonymousInitializer$default = IrFactory.DefaultImpls.createAnonymousInitializer$default(irFactory, iM6176getStartOffsetimpl, iM6175getEndOffsetimpl, irDeclarationOriginDeserializeIrDeclarationOrigin, (IrAnonymousInitializerSymbol) irSymbolComponent1, false, 16, null);
            IrBlockBody irBlockBodyCreateBlockBody = (IrBlockBody) deserializeStatementBody(proto.getBody());
            if (irBlockBodyCreateBlockBody == null) {
                irBlockBodyCreateBlockBody = this.irFactory.createBlockBody(iM6176getStartOffsetimpl, iM6175getEndOffsetimpl);
            }
            irAnonymousInitializerCreateAnonymousInitializer$default.setBody(irBlockBodyCreateBlockBody);
            IrAnonymousInitializer irAnonymousInitializer = irAnonymousInitializerCreateAnonymousInitializer$default;
            List<org.jetbrains.kotlin.backend.common.serialization.proto.IrConstructorCall> annotationList = base.getAnnotationList();
            Intrinsics.checkNotNullExpressionValue(annotationList, "getAnnotationList(...)");
            irAnonymousInitializer.setAnnotations(deserializeAnnotations$ir_serialization_common(annotationList));
            if (setParent) {
                irAnonymousInitializer.setParent(this.currentParent);
            }
            eraseDelegatedSymbol(irSymbolComponent1);
            return irAnonymousInitializer;
        } catch (Throwable th) {
            eraseDelegatedSymbol(irSymbolComponent1);
            throw th;
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r3v7, types: [org.jetbrains.kotlin.backend.common.serialization.encodings.BinaryNameAndType$Companion] */
    /* JADX WARN: Type inference failed for: r4v12 */
    /* JADX WARN: Type inference failed for: r4v16, types: [long] */
    /* JADX WARN: Type inference failed for: r4v4 */
    /* JADX WARN: Type inference failed for: r4v5, types: [org.jetbrains.kotlin.ir.symbols.IrSymbol] */
    /* JADX WARN: Type inference failed for: r4v6, types: [int] */
    /* JADX WARN: Type inference failed for: r4v7 */
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
    private final IrConstructor deserializeIrConstructor(org.jetbrains.kotlin.backend.common.serialization.proto.IrConstructor proto, boolean setParent) throws Throwable {
        final ?? M6175getEndOffsetimpl;
        IrConstructorSymbol irConstructorSymbol;
        IrFunctionBase base = proto.getBase();
        Intrinsics.checkNotNullExpressionValue(base, "getBase(...)");
        BinarySymbolData.SymbolKind symbolKind = BinarySymbolData.SymbolKind.CONSTRUCTOR_SYMBOL;
        IrDeclarationBase base2 = base.getBase();
        Intrinsics.checkNotNullExpressionValue(base2, "getBase(...)");
        Pair<IrSymbol, IdSignature> pairDeserializeIrSymbolToDeclare = getSymbolDeserializer().deserializeIrSymbolToDeclare(base2.getSymbol());
        IrSymbol irSymbolComponent1 = pairDeserializeIrSymbolToDeclare.component1();
        IdSignature idSignatureComponent2 = pairDeserializeIrSymbolToDeclare.component2();
        long jM6180decodeViYoqTc = BinaryCoordinates.INSTANCE.m6180decodeViYoqTc(base2.getCoordinates());
        try {
            recordDelegatedSymbol(irSymbolComponent1);
            final int iM6176getStartOffsetimpl = BinaryCoordinates.m6176getStartOffsetimpl(jM6180decodeViYoqTc);
            M6175getEndOffsetimpl = BinaryCoordinates.m6175getEndOffsetimpl(jM6180decodeViYoqTc);
            final IrDeclarationOrigin irDeclarationOriginDeserializeIrDeclarationOrigin = deserializeIrDeclarationOrigin(base2.getOriginName());
            long flags = base2.getFlags();
            try {
                if (!(irSymbolComponent1 instanceof IrConstructorSymbol)) {
                    if (!this.partialLinkageEnabled) {
                        throw new IrSymbolTypeMismatchException(IrConstructorSymbol.class, irSymbolComponent1);
                    }
                    ReferenceSymbolTable symbolTable = getSymbolDeserializer().getSymbolTable();
                    if (symbolKind == null) {
                        throw new IllegalStateException(("No fallback symbol kind specified for symbol " + irSymbolComponent1).toString());
                    }
                    IdSignature signature = irSymbolComponent1.getSignature();
                    if (signature != null) {
                        if (!signature.isPubliclyVisible()) {
                            signature = null;
                        }
                        if (signature != null) {
                            IrSymbol irSymbolReferenceDeserializedSymbol = IrSymbolDeserializerKt.referenceDeserializedSymbol(symbolTable, null, symbolKind, signature);
                            if (irSymbolReferenceDeserializedSymbol == null) {
                                throw new NullPointerException("null cannot be cast to non-null type org.jetbrains.kotlin.ir.symbols.IrConstructorSymbol");
                            }
                            irConstructorSymbol = (IrConstructorSymbol) irSymbolReferenceDeserializedSymbol;
                        }
                    }
                    throw new IllegalStateException(("No public signature for symbol " + irSymbolComponent1).toString());
                }
                irConstructorSymbol = irSymbolComponent1;
                IrFunctionSymbol irFunctionSymbol = (IrFunctionSymbol) irConstructorSymbol;
                SymbolTable symbolTable2 = this.symbolTable;
                symbolTable2.enterScope(irFunctionSymbol);
                final IrConstructorSymbol irConstructorSymbol2 = (IrConstructorSymbol) irFunctionSymbol;
                final long jM6273decodeUIJRpeM = FunctionFlags.INSTANCE.m6273decodeUIJRpeM(flags);
                try {
                    final long jM6200decodeWXC2TjU = BinaryNameAndType.INSTANCE.m6200decodeWXC2TjU(proto.getBase().getNameType());
                    IrConstructor irConstructorDeclareConstructor = this.symbolTable.declareConstructor(idSignatureComponent2, new Function0<IrConstructorSymbol>() { // from class: org.jetbrains.kotlin.backend.common.serialization.IrDeclarationDeserializer$deserializeIrConstructor$1$1
                        {
                            super(0);
                        }

                        @Override // kotlin.jvm.functions.Function0
                        public final IrConstructorSymbol invoke() {
                            return irConstructorSymbol2;
                        }
                    }, new Function1<IrConstructorSymbol, IrConstructor>() { // from class: org.jetbrains.kotlin.backend.common.serialization.IrDeclarationDeserializer$deserializeIrConstructor$1$2
                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                        {
                            super(1);
                        }

                        @Override // kotlin.jvm.functions.Function1
                        public final IrConstructor invoke(IrConstructorSymbol it) {
                            Intrinsics.checkNotNullParameter(it, "it");
                            return IrFactory.DefaultImpls.createConstructor$default(this.this$0.getIrFactory(), iM6176getStartOffsetimpl, M6175getEndOffsetimpl, irDeclarationOriginDeserializeIrDeclarationOrigin, this.this$0.deserializeName(BinaryNameAndType.m6195getNameIndeximpl(jM6200decodeWXC2TjU)), FunctionFlags.m6259getVisibilityimpl(jM6273decodeUIJRpeM), FunctionFlags.m6265isInlineimpl(jM6273decodeUIJRpeM), FunctionFlags.m6261isExpectimpl(jM6273decodeUIJRpeM), IrUninitializedType.INSTANCE, it, FunctionFlags.m6267isPrimaryimpl(jM6273decodeUIJRpeM), FunctionFlags.m6262isExternalimpl(jM6273decodeUIJRpeM) || this.this$0.isEffectivelyExternal, (DeserializedContainerSource) null, 2048, (Object) null);
                        }
                    });
                    IrDeclarationParent irDeclarationParent = this.currentParent;
                    this.currentParent = irConstructorDeclareConstructor;
                    try {
                        IrConstructor irConstructor = irConstructorDeclareConstructor;
                        List<org.jetbrains.kotlin.backend.common.serialization.proto.IrTypeParameter> typeParameterList = base.getTypeParameterList();
                        Intrinsics.checkNotNullExpressionValue(typeParameterList, "getTypeParameterList(...)");
                        irConstructor.setTypeParameters(deserializeTypeParameters(typeParameterList, false));
                        ?? r3 = BinaryNameAndType.INSTANCE;
                        M6175getEndOffsetimpl = base.getNameType();
                        irConstructor.setReturnType(deserializeIrType(BinaryNameAndType.m6196getTypeIndeximpl(r3.m6200decodeWXC2TjU(M6175getEndOffsetimpl))));
                        withBodyGuard(irConstructor, new IrDeclarationDeserializer$withDeserializedIrFunctionBase$1$1$1$1(this, base));
                        this.currentParent = irDeclarationParent;
                        symbolTable2.leaveScope(irFunctionSymbol);
                        IrConstructor irConstructor2 = irConstructorDeclareConstructor;
                        List<org.jetbrains.kotlin.backend.common.serialization.proto.IrConstructorCall> annotationList = base2.getAnnotationList();
                        Intrinsics.checkNotNullExpressionValue(annotationList, "getAnnotationList(...)");
                        irConstructor2.setAnnotations(deserializeAnnotations$ir_serialization_common(annotationList));
                        if (setParent) {
                            irConstructor2.setParent(this.currentParent);
                        }
                        eraseDelegatedSymbol(irSymbolComponent1);
                        return irConstructor2;
                    } catch (Throwable th) {
                        this.currentParent = irDeclarationParent;
                        throw th;
                    }
                } catch (Throwable th2) {
                    th = th2;
                    M6175getEndOffsetimpl = irSymbolComponent1;
                    eraseDelegatedSymbol(M6175getEndOffsetimpl);
                    throw th;
                }
            } catch (Throwable th3) {
                th = th3;
            }
        } catch (Throwable th4) {
            th = th4;
            M6175getEndOffsetimpl = irSymbolComponent1;
        }
    }

    static /* synthetic */ IrField deserializeIrField$default(IrDeclarationDeserializer irDeclarationDeserializer, org.jetbrains.kotlin.backend.common.serialization.proto.IrField irField, boolean z, boolean z2, int i, Object obj) {
        if ((i & 4) != 0) {
            z2 = true;
        }
        return irDeclarationDeserializer.deserializeIrField(irField, z, z2);
    }

    private final IrField deserializeIrField(final org.jetbrains.kotlin.backend.common.serialization.proto.IrField proto, boolean isConst, boolean setParent) {
        IrDeclarationBase base = proto.getBase();
        Intrinsics.checkNotNullExpressionValue(base, "getBase(...)");
        Pair<IrSymbol, IdSignature> pairDeserializeIrSymbolToDeclare = getSymbolDeserializer().deserializeIrSymbolToDeclare(base.getSymbol());
        final IrSymbol irSymbolComponent1 = pairDeserializeIrSymbolToDeclare.component1();
        IdSignature idSignatureComponent2 = pairDeserializeIrSymbolToDeclare.component2();
        long jM6180decodeViYoqTc = BinaryCoordinates.INSTANCE.m6180decodeViYoqTc(base.getCoordinates());
        try {
            recordDelegatedSymbol(irSymbolComponent1);
            final int iM6176getStartOffsetimpl = BinaryCoordinates.m6176getStartOffsetimpl(jM6180decodeViYoqTc);
            final int iM6175getEndOffsetimpl = BinaryCoordinates.m6175getEndOffsetimpl(jM6180decodeViYoqTc);
            final IrDeclarationOrigin irDeclarationOriginDeserializeIrDeclarationOrigin = deserializeIrDeclarationOrigin(base.getOriginName());
            long flags = base.getFlags();
            final long jM6200decodeWXC2TjU = BinaryNameAndType.INSTANCE.m6200decodeWXC2TjU(proto.getNameType());
            final IrType irTypeDeserializeIrType = deserializeIrType(BinaryNameAndType.m6196getTypeIndeximpl(jM6200decodeWXC2TjU));
            final long jM6253decodeTfoGUTA = FieldFlags.INSTANCE.m6253decodeTfoGUTA(flags);
            IrField irFieldDeclareField = this.symbolTable.declareField(idSignatureComponent2, new Function0<IrFieldSymbol>() { // from class: org.jetbrains.kotlin.backend.common.serialization.IrDeclarationDeserializer$deserializeIrField$1$field$1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.Function0
                public final IrFieldSymbol invoke() throws IrSymbolTypeMismatchException {
                    IrDeclarationDeserializer irDeclarationDeserializer = this.this$0;
                    IrFieldSymbol irFieldSymbol = irSymbolComponent1;
                    BinarySymbolData.SymbolKind symbolKind = BinarySymbolData.SymbolKind.FIELD_SYMBOL;
                    if (!(irFieldSymbol instanceof IrFieldSymbol)) {
                        if (!irDeclarationDeserializer.partialLinkageEnabled) {
                            throw new IrSymbolTypeMismatchException(IrFieldSymbol.class, irFieldSymbol);
                        }
                        ReferenceSymbolTable symbolTable = irDeclarationDeserializer.getSymbolDeserializer().getSymbolTable();
                        if (symbolKind == null) {
                            throw new IllegalStateException(("No fallback symbol kind specified for symbol " + irFieldSymbol).toString());
                        }
                        IdSignature signature = irFieldSymbol.getSignature();
                        if (signature != null) {
                            if (!signature.isPubliclyVisible()) {
                                signature = null;
                            }
                            if (signature != null) {
                                IrSymbol irSymbolReferenceDeserializedSymbol = IrSymbolDeserializerKt.referenceDeserializedSymbol(symbolTable, null, symbolKind, signature);
                                if (irSymbolReferenceDeserializedSymbol == null) {
                                    throw new NullPointerException("null cannot be cast to non-null type org.jetbrains.kotlin.ir.symbols.IrFieldSymbol");
                                }
                                irFieldSymbol = (IrFieldSymbol) irSymbolReferenceDeserializedSymbol;
                            }
                        }
                        throw new IllegalStateException(("No public signature for symbol " + irFieldSymbol).toString());
                    }
                    return (IrFieldSymbol) irFieldSymbol;
                }
            }, new Function1<IrFieldSymbol, IrField>() { // from class: org.jetbrains.kotlin.backend.common.serialization.IrDeclarationDeserializer$deserializeIrField$1$field$2
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(1);
                }

                @Override // kotlin.jvm.functions.Function1
                public final IrField invoke(IrFieldSymbol it) {
                    Intrinsics.checkNotNullParameter(it, "it");
                    return this.this$0.getIrFactory().createField(iM6176getStartOffsetimpl, iM6175getEndOffsetimpl, irDeclarationOriginDeserializeIrDeclarationOrigin, this.this$0.deserializeName(BinaryNameAndType.m6195getNameIndeximpl(jM6200decodeWXC2TjU)), FieldFlags.m6246getVisibilityimpl(jM6253decodeTfoGUTA), it, irTypeDeserializeIrType, FieldFlags.m6249isFinalimpl(jM6253decodeTfoGUTA), FieldFlags.m6250isStaticimpl(jM6253decodeTfoGUTA), FieldFlags.m6248isExternalimpl(jM6253decodeTfoGUTA) || this.this$0.isEffectivelyExternal);
                }
            });
            IrField irField = irFieldDeclareField;
            IrDeclarationParent irDeclarationParent = this.currentParent;
            this.currentParent = irField;
            try {
                IrField irField2 = irField;
                if (proto.hasInitializer()) {
                    withInitializerGuard(irField2, isConst, new Function1<IrField, Unit>() { // from class: org.jetbrains.kotlin.backend.common.serialization.IrDeclarationDeserializer$deserializeIrField$1$1$1
                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                        {
                            super(1);
                        }

                        @Override // kotlin.jvm.functions.Function1
                        public /* bridge */ /* synthetic */ Unit invoke(IrField irField3) {
                            invoke2(irField3);
                            return Unit.INSTANCE;
                        }

                        /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
                        public final void invoke2(IrField withInitializerGuard) {
                            Intrinsics.checkNotNullParameter(withInitializerGuard, "$this$withInitializerGuard");
                            withInitializerGuard.setInitializer(this.this$0.deserializeExpressionBody(proto.getInitializer()));
                        }
                    });
                }
                this.currentParent = irDeclarationParent;
                IrField irField3 = irFieldDeclareField;
                List<org.jetbrains.kotlin.backend.common.serialization.proto.IrConstructorCall> annotationList = base.getAnnotationList();
                Intrinsics.checkNotNullExpressionValue(annotationList, "getAnnotationList(...)");
                irField3.setAnnotations(deserializeAnnotations$ir_serialization_common(annotationList));
                if (setParent) {
                    irField3.setParent(this.currentParent);
                }
                eraseDelegatedSymbol(irSymbolComponent1);
                return irField3;
            } catch (Throwable th) {
                this.currentParent = irDeclarationParent;
                throw th;
            }
        } catch (Throwable th2) {
            eraseDelegatedSymbol(irSymbolComponent1);
            throw th2;
        }
    }

    private final IrLocalDelegatedProperty deserializeIrLocalDelegatedProperty(org.jetbrains.kotlin.backend.common.serialization.proto.IrLocalDelegatedProperty proto, boolean setParent) {
        IrDeclarationBase base = proto.getBase();
        Intrinsics.checkNotNullExpressionValue(base, "getBase(...)");
        Pair<IrSymbol, IdSignature> pairDeserializeIrSymbolToDeclare = getSymbolDeserializer().deserializeIrSymbolToDeclare(base.getSymbol());
        IrSymbol irSymbolComponent1 = pairDeserializeIrSymbolToDeclare.component1();
        pairDeserializeIrSymbolToDeclare.component2();
        long jM6180decodeViYoqTc = BinaryCoordinates.INSTANCE.m6180decodeViYoqTc(base.getCoordinates());
        try {
            recordDelegatedSymbol(irSymbolComponent1);
            int iM6176getStartOffsetimpl = BinaryCoordinates.m6176getStartOffsetimpl(jM6180decodeViYoqTc);
            int iM6175getEndOffsetimpl = BinaryCoordinates.m6175getEndOffsetimpl(jM6180decodeViYoqTc);
            IrDeclarationOrigin irDeclarationOriginDeserializeIrDeclarationOrigin = deserializeIrDeclarationOrigin(base.getOriginName());
            long jM6284decodeQnIyQoc = LocalVariableFlags.INSTANCE.m6284decodeQnIyQoc(base.getFlags());
            long jM6200decodeWXC2TjU = BinaryNameAndType.INSTANCE.m6200decodeWXC2TjU(proto.getNameType());
            IrFactory irFactory = this.irFactory;
            Name nameDeserializeName = deserializeName(BinaryNameAndType.m6195getNameIndeximpl(jM6200decodeWXC2TjU));
            if (!(irSymbolComponent1 instanceof IrLocalDelegatedPropertySymbol)) {
                if (!this.partialLinkageEnabled) {
                    throw new IrSymbolTypeMismatchException(IrLocalDelegatedPropertySymbol.class, irSymbolComponent1);
                }
                getSymbolDeserializer().getSymbolTable();
                throw new IllegalStateException(("No fallback symbol kind specified for symbol " + irSymbolComponent1).toString());
            }
            IrLocalDelegatedProperty irLocalDelegatedPropertyCreateLocalDelegatedProperty = irFactory.createLocalDelegatedProperty(iM6176getStartOffsetimpl, iM6175getEndOffsetimpl, irDeclarationOriginDeserializeIrDeclarationOrigin, nameDeserializeName, (IrLocalDelegatedPropertySymbol) irSymbolComponent1, deserializeIrType(BinaryNameAndType.m6196getTypeIndeximpl(jM6200decodeWXC2TjU)), LocalVariableFlags.m6281isVarimpl(jM6284decodeQnIyQoc));
            org.jetbrains.kotlin.backend.common.serialization.proto.IrVariable delegate = proto.getDelegate();
            Intrinsics.checkNotNullExpressionValue(delegate, "getDelegate(...)");
            irLocalDelegatedPropertyCreateLocalDelegatedProperty.setDelegate(deserializeIrVariable$default(this, delegate, false, 2, null));
            org.jetbrains.kotlin.backend.common.serialization.proto.IrFunction getter = proto.getGetter();
            Intrinsics.checkNotNullExpressionValue(getter, "getGetter(...)");
            irLocalDelegatedPropertyCreateLocalDelegatedProperty.setGetter(deserializeIrFunction$ir_serialization_common$default(this, getter, false, 2, null));
            if (proto.hasSetter()) {
                org.jetbrains.kotlin.backend.common.serialization.proto.IrFunction setter = proto.getSetter();
                Intrinsics.checkNotNullExpressionValue(setter, "getSetter(...)");
                irLocalDelegatedPropertyCreateLocalDelegatedProperty.setSetter(deserializeIrFunction$ir_serialization_common$default(this, setter, false, 2, null));
            }
            IrLocalDelegatedProperty irLocalDelegatedProperty = irLocalDelegatedPropertyCreateLocalDelegatedProperty;
            List<org.jetbrains.kotlin.backend.common.serialization.proto.IrConstructorCall> annotationList = base.getAnnotationList();
            Intrinsics.checkNotNullExpressionValue(annotationList, "getAnnotationList(...)");
            irLocalDelegatedProperty.setAnnotations(deserializeAnnotations$ir_serialization_common(annotationList));
            if (setParent) {
                irLocalDelegatedProperty.setParent(this.currentParent);
            }
            eraseDelegatedSymbol(irSymbolComponent1);
            return irLocalDelegatedProperty;
        } catch (Throwable th) {
            eraseDelegatedSymbol(irSymbolComponent1);
            throw th;
        }
    }

    private final IrProperty deserializeIrProperty(final org.jetbrains.kotlin.backend.common.serialization.proto.IrProperty proto, boolean setParent) {
        IrPropertySymbol irPropertySymbol;
        IrDeclarationBase base = proto.getBase();
        Intrinsics.checkNotNullExpressionValue(base, "getBase(...)");
        Pair<IrSymbol, IdSignature> pairDeserializeIrSymbolToDeclare = getSymbolDeserializer().deserializeIrSymbolToDeclare(base.getSymbol());
        IrSymbol irSymbolComponent1 = pairDeserializeIrSymbolToDeclare.component1();
        IdSignature idSignatureComponent2 = pairDeserializeIrSymbolToDeclare.component2();
        long jM6180decodeViYoqTc = BinaryCoordinates.INSTANCE.m6180decodeViYoqTc(base.getCoordinates());
        try {
            recordDelegatedSymbol(irSymbolComponent1);
            final int iM6176getStartOffsetimpl = BinaryCoordinates.m6176getStartOffsetimpl(jM6180decodeViYoqTc);
            final int iM6175getEndOffsetimpl = BinaryCoordinates.m6175getEndOffsetimpl(jM6180decodeViYoqTc);
            final IrDeclarationOrigin irDeclarationOriginDeserializeIrDeclarationOrigin = deserializeIrDeclarationOrigin(base.getOriginName());
            final long jM6302decode85tB66k = PropertyFlags.INSTANCE.m6302decode85tB66k(base.getFlags());
            BinarySymbolData.SymbolKind symbolKind = BinarySymbolData.SymbolKind.PROPERTY_SYMBOL;
            if (!(irSymbolComponent1 instanceof IrPropertySymbol)) {
                if (!this.partialLinkageEnabled) {
                    throw new IrSymbolTypeMismatchException(IrPropertySymbol.class, irSymbolComponent1);
                }
                ReferenceSymbolTable symbolTable = getSymbolDeserializer().getSymbolTable();
                if (symbolKind == null) {
                    throw new IllegalStateException(("No fallback symbol kind specified for symbol " + irSymbolComponent1).toString());
                }
                IdSignature signature = irSymbolComponent1.getSignature();
                if (signature != null) {
                    if (!signature.isPubliclyVisible()) {
                        signature = null;
                    }
                    if (signature != null) {
                        IrSymbol irSymbolReferenceDeserializedSymbol = IrSymbolDeserializerKt.referenceDeserializedSymbol(symbolTable, null, symbolKind, signature);
                        if (irSymbolReferenceDeserializedSymbol == null) {
                            throw new NullPointerException("null cannot be cast to non-null type org.jetbrains.kotlin.ir.symbols.IrPropertySymbol");
                        }
                        irPropertySymbol = (IrPropertySymbol) irSymbolReferenceDeserializedSymbol;
                    }
                }
                throw new IllegalStateException(("No public signature for symbol " + irSymbolComponent1).toString());
            }
            irPropertySymbol = irSymbolComponent1;
            final IrPropertySymbol irPropertySymbol2 = (IrPropertySymbol) irPropertySymbol;
            IrProperty irPropertyDeclareProperty = this.symbolTable.declareProperty(idSignatureComponent2, new Function0<IrPropertySymbol>() { // from class: org.jetbrains.kotlin.backend.common.serialization.IrDeclarationDeserializer$deserializeIrProperty$1$prop$1
                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.Function0
                public final IrPropertySymbol invoke() {
                    return irPropertySymbol2;
                }
            }, new Function1<IrPropertySymbol, IrProperty>() { // from class: org.jetbrains.kotlin.backend.common.serialization.IrDeclarationDeserializer$deserializeIrProperty$1$prop$2
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(1);
                }

                @Override // kotlin.jvm.functions.Function1
                public final IrProperty invoke(IrPropertySymbol it) {
                    Intrinsics.checkNotNullParameter(it, "it");
                    return IrFactory.DefaultImpls.createProperty$default(this.this$0.getIrFactory(), iM6176getStartOffsetimpl, iM6175getEndOffsetimpl, irDeclarationOriginDeserializeIrDeclarationOrigin, this.this$0.deserializeName(proto.getName()), PropertyFlags.m6290getVisibilityimpl(jM6302decode85tB66k), PropertyFlags.m6289getModalityimpl(jM6302decode85tB66k), it, PropertyFlags.m6298isVarimpl(jM6302decode85tB66k), PropertyFlags.m6292isConstimpl(jM6302decode85tB66k), PropertyFlags.m6297isLateinitimpl(jM6302decode85tB66k), PropertyFlags.m6293isDelegatedimpl(jM6302decode85tB66k), PropertyFlags.m6295isExternalimpl(jM6302decode85tB66k) || this.this$0.isEffectivelyExternal, (DeserializedContainerSource) null, PropertyFlags.m6294isExpectimpl(jM6302decode85tB66k), PropertyFlags.m6296isFakeOverrideimpl(jM6302decode85tB66k), 4096, (Object) null);
                }
            });
            boolean zIsExternal = irPropertyDeclareProperty.getIsExternal();
            boolean z = this.isEffectivelyExternal;
            this.isEffectivelyExternal = zIsExternal;
            try {
                if (proto.hasGetter()) {
                    org.jetbrains.kotlin.backend.common.serialization.proto.IrFunction getter = proto.getGetter();
                    Intrinsics.checkNotNullExpressionValue(getter, "getGetter(...)");
                    IrSimpleFunction irSimpleFunctionDeserializeIrFunction$ir_serialization_common$default = deserializeIrFunction$ir_serialization_common$default(this, getter, false, 2, null);
                    irSimpleFunctionDeserializeIrFunction$ir_serialization_common$default.setCorrespondingPropertySymbol(irPropertySymbol2);
                    irPropertyDeclareProperty.setGetter(irSimpleFunctionDeserializeIrFunction$ir_serialization_common$default);
                }
                if (proto.hasSetter()) {
                    org.jetbrains.kotlin.backend.common.serialization.proto.IrFunction setter = proto.getSetter();
                    Intrinsics.checkNotNullExpressionValue(setter, "getSetter(...)");
                    IrSimpleFunction irSimpleFunctionDeserializeIrFunction$ir_serialization_common$default2 = deserializeIrFunction$ir_serialization_common$default(this, setter, false, 2, null);
                    irSimpleFunctionDeserializeIrFunction$ir_serialization_common$default2.setCorrespondingPropertySymbol(irPropertySymbol2);
                    irPropertyDeclareProperty.setSetter(irSimpleFunctionDeserializeIrFunction$ir_serialization_common$default2);
                }
                if (proto.hasBackingField()) {
                    org.jetbrains.kotlin.backend.common.serialization.proto.IrField backingField = proto.getBackingField();
                    Intrinsics.checkNotNullExpressionValue(backingField, "getBackingField(...)");
                    IrField irFieldDeserializeIrField$default = deserializeIrField$default(this, backingField, irPropertyDeclareProperty.getIsConst(), false, 4, null);
                    irFieldDeserializeIrField$default.setCorrespondingPropertySymbol(irPropertySymbol2);
                    irPropertyDeclareProperty.setBackingField(irFieldDeserializeIrField$default);
                }
                this.isEffectivelyExternal = z;
                IrProperty irProperty = irPropertyDeclareProperty;
                List<org.jetbrains.kotlin.backend.common.serialization.proto.IrConstructorCall> annotationList = base.getAnnotationList();
                Intrinsics.checkNotNullExpressionValue(annotationList, "getAnnotationList(...)");
                irProperty.setAnnotations(deserializeAnnotations$ir_serialization_common(annotationList));
                if (setParent) {
                    irProperty.setParent(this.currentParent);
                }
                eraseDelegatedSymbol(irSymbolComponent1);
                return irProperty;
            } catch (Throwable th) {
                this.isEffectivelyExternal = z;
                throw th;
            }
        } catch (Throwable th2) {
            eraseDelegatedSymbol(irSymbolComponent1);
            throw th2;
        }
    }

    static {
        List<KClass<?>> list = CollectionsKt.toList(Reflection.getOrCreateKotlinClass(IrDeclarationOrigin.class).getNestedClasses());
        allKnownDeclarationOrigins = list;
        ArrayList arrayList = new ArrayList();
        Iterator<T> it = list.iterator();
        while (it.hasNext()) {
            Object objectInstance = ((KClass) it.next()).getObjectInstance();
            IrDeclarationOriginImpl irDeclarationOriginImpl = objectInstance instanceof IrDeclarationOriginImpl ? (IrDeclarationOriginImpl) objectInstance : null;
            if (irDeclarationOriginImpl != null) {
                arrayList.add(irDeclarationOriginImpl);
            }
        }
        ArrayList arrayList2 = arrayList;
        LinkedHashMap linkedHashMap = new LinkedHashMap(RangesKt.coerceAtLeast(MapsKt.mapCapacity(CollectionsKt.collectionSizeOrDefault(arrayList2, 10)), 16));
        for (Object obj : arrayList2) {
            linkedHashMap.put(((IrDeclarationOriginImpl) obj).getName(), obj);
        }
        declarationOriginIndex = linkedHashMap;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final IrDeclarationOrigin deserializeIrDeclarationOrigin(int protoName) {
        String strString = this.libraryFile.string(protoName);
        IrDeclarationOriginImpl irDeclarationOriginImplFromSerializedString = IrDeclarationOrigin.GeneratedByPlugin.INSTANCE.fromSerializedString(strString);
        return (irDeclarationOriginImplFromSerializedString == null && (irDeclarationOriginImplFromSerializedString = declarationOriginIndex.get(strString)) == null) ? new IrDeclarationOriginImpl(strString) { // from class: org.jetbrains.kotlin.backend.common.serialization.IrDeclarationDeserializer.deserializeIrDeclarationOrigin.1
        } : irDeclarationOriginImplFromSerializedString;
    }

    public static /* synthetic */ org.jetbrains.kotlin.ir.declarations.IrDeclaration deserializeDeclaration$default(IrDeclarationDeserializer irDeclarationDeserializer, IrDeclaration irDeclaration, boolean z, int i, Object obj) {
        if ((i & 2) != 0) {
            z = true;
        }
        return irDeclarationDeserializer.deserializeDeclaration(irDeclaration, z);
    }

    public final org.jetbrains.kotlin.ir.declarations.IrDeclaration deserializeDeclaration(IrDeclaration proto, boolean setParent) {
        Intrinsics.checkNotNullParameter(proto, "proto");
        IrDeclaration.DeclaratorCase declaratorCase = proto.getDeclaratorCase();
        Intrinsics.checkNotNull(declaratorCase);
        switch (WhenMappings.$EnumSwitchMapping$2[declaratorCase.ordinal()]) {
            case 1:
                IrAnonymousInit irAnonymousInit = proto.getIrAnonymousInit();
                Intrinsics.checkNotNullExpressionValue(irAnonymousInit, "getIrAnonymousInit(...)");
                return deserializeIrAnonymousInit(irAnonymousInit, setParent);
            case 2:
                org.jetbrains.kotlin.backend.common.serialization.proto.IrConstructor irConstructor = proto.getIrConstructor();
                Intrinsics.checkNotNullExpressionValue(irConstructor, "getIrConstructor(...)");
                return deserializeIrConstructor(irConstructor, setParent);
            case 3:
                org.jetbrains.kotlin.backend.common.serialization.proto.IrField irField = proto.getIrField();
                Intrinsics.checkNotNullExpressionValue(irField, "getIrField(...)");
                return deserializeIrField(irField, false, setParent);
            case 4:
                org.jetbrains.kotlin.backend.common.serialization.proto.IrClass irClass = proto.getIrClass();
                Intrinsics.checkNotNullExpressionValue(irClass, "getIrClass(...)");
                return deserializeIrClass(irClass, setParent);
            case 5:
                org.jetbrains.kotlin.backend.common.serialization.proto.IrFunction irFunction = proto.getIrFunction();
                Intrinsics.checkNotNullExpressionValue(irFunction, "getIrFunction(...)");
                return deserializeIrFunction$ir_serialization_common(irFunction, setParent);
            case 6:
                org.jetbrains.kotlin.backend.common.serialization.proto.IrProperty irProperty = proto.getIrProperty();
                Intrinsics.checkNotNullExpressionValue(irProperty, "getIrProperty(...)");
                return deserializeIrProperty(irProperty, setParent);
            case 7:
                throw new IllegalStateException("".toString());
            case 8:
                org.jetbrains.kotlin.backend.common.serialization.proto.IrVariable irVariable = proto.getIrVariable();
                Intrinsics.checkNotNullExpressionValue(irVariable, "getIrVariable(...)");
                return deserializeIrVariable(irVariable, setParent);
            case 9:
                throw new IllegalStateException("".toString());
            case 10:
                org.jetbrains.kotlin.backend.common.serialization.proto.IrEnumEntry irEnumEntry = proto.getIrEnumEntry();
                Intrinsics.checkNotNullExpressionValue(irEnumEntry, "getIrEnumEntry(...)");
                return deserializeIrEnumEntry(irEnumEntry, setParent);
            case 11:
                org.jetbrains.kotlin.backend.common.serialization.proto.IrLocalDelegatedProperty irLocalDelegatedProperty = proto.getIrLocalDelegatedProperty();
                Intrinsics.checkNotNullExpressionValue(irLocalDelegatedProperty, "getIrLocalDelegatedProperty(...)");
                return deserializeIrLocalDelegatedProperty(irLocalDelegatedProperty, setParent);
            case 12:
                org.jetbrains.kotlin.backend.common.serialization.proto.IrTypeAlias irTypeAlias = proto.getIrTypeAlias();
                Intrinsics.checkNotNullExpressionValue(irTypeAlias, "getIrTypeAlias(...)");
                return deserializeIrTypeAlias(irTypeAlias, setParent);
            case 13:
                org.jetbrains.kotlin.backend.common.serialization.proto.IrErrorDeclaration irErrorDeclaration = proto.getIrErrorDeclaration();
                Intrinsics.checkNotNullExpressionValue(irErrorDeclaration, "getIrErrorDeclaration(...)");
                return deserializeErrorDeclaration(irErrorDeclaration, setParent);
            case 14:
                throw new IllegalStateException(("Declaration deserialization not implemented: " + proto.getDeclaratorCase()).toString());
            default:
                throw new NoWhenBranchMatchedException();
        }
    }

    private final boolean isSkippableFakeOverride(IrDeclaration proto, IrClass parent) {
        IrSymbol irSymbolDeserializeIrSymbol;
        if (!this.platformFakeOverrideClassFilter.needToConstructFakeOverrides(parent)) {
            return false;
        }
        IrDeclaration.DeclaratorCase declaratorCase = proto.getDeclaratorCase();
        Intrinsics.checkNotNull(declaratorCase);
        int i = WhenMappings.$EnumSwitchMapping$2[declaratorCase.ordinal()];
        if (i == 5) {
            irSymbolDeserializeIrSymbol = this.symbolDeserializer.deserializeIrSymbol(proto.getIrFunction().getBase().getBase().getSymbol());
        } else {
            if (i != 6) {
                return false;
            }
            irSymbolDeserializeIrSymbol = this.symbolDeserializer.deserializeIrSymbol(proto.getIrProperty().getBase().getSymbol());
        }
        if (!(irSymbolDeserializeIrSymbol instanceof IrPublicSymbolBase) || !((IrPublicSymbolBase) irSymbolDeserializeIrSymbol).getSignature().isPubliclyVisible()) {
            return false;
        }
        IrDeclaration.DeclaratorCase declaratorCase2 = proto.getDeclaratorCase();
        Intrinsics.checkNotNull(declaratorCase2);
        int i2 = WhenMappings.$EnumSwitchMapping$2[declaratorCase2.ordinal()];
        if (i2 == 5) {
            return FunctionFlags.m6263isFakeOverrideimpl(FunctionFlags.INSTANCE.m6273decodeUIJRpeM(proto.getIrFunction().getBase().getBase().getFlags()));
        }
        if (i2 != 6) {
            return false;
        }
        return PropertyFlags.m6296isFakeOverrideimpl(PropertyFlags.INSTANCE.m6302decode85tB66k(proto.getIrProperty().getBase().getFlags()));
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final /* synthetic */ <S extends IrSymbol> S checkSymbolType$ir_serialization_common(IrSymbol irSymbol, BinarySymbolData.SymbolKind symbolKind) throws IrSymbolTypeMismatchException {
        Intrinsics.checkNotNullParameter(irSymbol, "<this>");
        Intrinsics.reifiedOperationMarker(3, ExifInterface.LATITUDE_SOUTH);
        if (irSymbol instanceof IrSymbol) {
            return irSymbol;
        }
        if (!this.partialLinkageEnabled) {
            Intrinsics.reifiedOperationMarker(4, ExifInterface.LATITUDE_SOUTH);
            throw new IrSymbolTypeMismatchException(IrSymbol.class, irSymbol);
        }
        ReferenceSymbolTable symbolTable = getSymbolDeserializer().getSymbolTable();
        if (symbolKind == null) {
            throw new IllegalStateException(("No fallback symbol kind specified for symbol " + irSymbol).toString());
        }
        IdSignature signature = irSymbol.getSignature();
        if (signature != null) {
            if (!signature.isPubliclyVisible()) {
                signature = null;
            }
            if (signature != null) {
                S s = (S) IrSymbolDeserializerKt.referenceDeserializedSymbol(symbolTable, null, symbolKind, signature);
                Intrinsics.reifiedOperationMarker(1, ExifInterface.LATITUDE_SOUTH);
                return s;
            }
        }
        throw new IllegalStateException(("No public signature for symbol " + irSymbol).toString());
    }
}
