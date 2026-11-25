package org.jetbrains.kotlin.ir.declarations.lazy;

import com.google.firebase.analytics.FirebaseAnalytics;
import java.util.List;
import kotlin.Deprecated;
import kotlin.DeprecationLevel;
import kotlin.Metadata;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import net.bytebuddy.implementation.MethodDelegation;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.descriptors.ClassKind;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.descriptors.DescriptorVisibility;
import org.jetbrains.kotlin.descriptors.Modality;
import org.jetbrains.kotlin.descriptors.SourceElement;
import org.jetbrains.kotlin.ir.declarations.IrAnonymousInitializer;
import org.jetbrains.kotlin.ir.declarations.IrClass;
import org.jetbrains.kotlin.ir.declarations.IrConstructor;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationOrigin;
import org.jetbrains.kotlin.ir.declarations.IrEnumEntry;
import org.jetbrains.kotlin.ir.declarations.IrErrorDeclaration;
import org.jetbrains.kotlin.ir.declarations.IrFactory;
import org.jetbrains.kotlin.ir.declarations.IrField;
import org.jetbrains.kotlin.ir.declarations.IrFunctionWithLateBinding;
import org.jetbrains.kotlin.ir.declarations.IrLocalDelegatedProperty;
import org.jetbrains.kotlin.ir.declarations.IrProperty;
import org.jetbrains.kotlin.ir.declarations.IrPropertyWithLateBinding;
import org.jetbrains.kotlin.ir.declarations.IrSimpleFunction;
import org.jetbrains.kotlin.ir.declarations.IrTypeAlias;
import org.jetbrains.kotlin.ir.declarations.IrTypeParameter;
import org.jetbrains.kotlin.ir.declarations.IrValueParameter;
import org.jetbrains.kotlin.ir.declarations.StageController;
import org.jetbrains.kotlin.ir.expressions.IrBlockBody;
import org.jetbrains.kotlin.ir.expressions.IrExpression;
import org.jetbrains.kotlin.ir.expressions.IrExpressionBody;
import org.jetbrains.kotlin.ir.symbols.IrAnonymousInitializerSymbol;
import org.jetbrains.kotlin.ir.symbols.IrClassSymbol;
import org.jetbrains.kotlin.ir.symbols.IrConstructorSymbol;
import org.jetbrains.kotlin.ir.symbols.IrEnumEntrySymbol;
import org.jetbrains.kotlin.ir.symbols.IrFieldSymbol;
import org.jetbrains.kotlin.ir.symbols.IrLocalDelegatedPropertySymbol;
import org.jetbrains.kotlin.ir.symbols.IrPropertySymbol;
import org.jetbrains.kotlin.ir.symbols.IrSimpleFunctionSymbol;
import org.jetbrains.kotlin.ir.symbols.IrTypeAliasSymbol;
import org.jetbrains.kotlin.ir.symbols.IrTypeParameterSymbol;
import org.jetbrains.kotlin.ir.symbols.IrValueParameterSymbol;
import org.jetbrains.kotlin.ir.types.IrType;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.serialization.deserialization.descriptors.DeserializedContainerSource;
import org.jetbrains.kotlin.types.Variance;
import org.mozilla.javascript.NativeSymbol;

/* JADX INFO: compiled from: LazyIrFactory.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000\u009e\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0001¢\u0006\u0002\u0010\u0003J3\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\u000b2\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u00102\b\b\u0002\u0010\u0011\u001a\u00020\u0012H\u0096\u0001J\u0019\u0010\u0013\u001a\u00020\u00142\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\u000bH\u0096\u0001J'\u0010\u0013\u001a\u00020\u00142\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\u000b2\f\u0010\u0015\u001a\b\u0012\u0004\u0012\u00020\u00170\u0016H\u0097\u0001J2\u0010\u0013\u001a\u00020\u00142\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\u000b2\u0017\u0010\u0018\u001a\u0013\u0012\u0004\u0012\u00020\u0014\u0012\u0004\u0012\u00020\u001a0\u0019¢\u0006\u0002\b\u001bH\u0097\u0001J\u0099\u0001\u0010\u001c\u001a\u00020\u001d2\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\u000b2\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u001e2\u0006\u0010\u001f\u001a\u00020 2\u0006\u0010!\u001a\u00020\"2\u0006\u0010#\u001a\u00020$2\u0006\u0010%\u001a\u00020&2\b\b\u0002\u0010'\u001a\u00020\u00122\b\b\u0002\u0010(\u001a\u00020\u00122\b\b\u0002\u0010)\u001a\u00020\u00122\b\b\u0002\u0010*\u001a\u00020\u00122\b\b\u0002\u0010+\u001a\u00020\u00122\b\b\u0002\u0010,\u001a\u00020\u00122\b\b\u0002\u0010-\u001a\u00020\u00122\b\b\u0002\u0010.\u001a\u00020/H\u0097\u0001J\u0088\u0001\u0010\u001c\u001a\u00020\u001d2\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\u000b2\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u001f\u001a\u00020 2\u0006\u0010#\u001a\u00020$2\u0006\u0010\u000f\u001a\u00020\u001e2\u0006\u0010!\u001a\u00020\"2\u0006\u0010%\u001a\u00020&2\u0006\u0010*\u001a\u00020\u00122\u0006\u0010'\u001a\u00020\u00122\u0006\u0010(\u001a\u00020\u00122\u0006\u0010)\u001a\u00020\u00122\u0006\u0010+\u001a\u00020\u00122\u0006\u0010,\u001a\u00020\u00122\u0006\u0010-\u001a\u00020\u00122\u0006\u0010.\u001a\u00020/H\u0016Jm\u00100\u001a\u0002012\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\u000b2\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u0002022\u0006\u0010\u001f\u001a\u00020 2\u0006\u0010#\u001a\u00020$2\u0006\u00103\u001a\u0002042\u0006\u00105\u001a\u00020\u00122\u0006\u0010*\u001a\u00020\u00122\u0006\u00106\u001a\u00020\u00122\u0006\u0010,\u001a\u00020\u00122\n\b\u0002\u00107\u001a\u0004\u0018\u000108H\u0097\u0001Jj\u00100\u001a\u0002012\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\u000b2\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u001f\u001a\u00020 2\u0006\u0010#\u001a\u00020$2\u0006\u00105\u001a\u00020\u00122\u0006\u0010,\u001a\u00020\u00122\u0006\u00103\u001a\u0002042\u0006\u0010\u000f\u001a\u0002022\u0006\u00106\u001a\u00020\u00122\u0006\u0010*\u001a\u00020\u00122\b\u00107\u001a\u0004\u0018\u000108H\u0016J1\u00109\u001a\u00020:2\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\u000b2\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020;2\u0006\u0010\u001f\u001a\u00020 H\u0097\u0001J0\u00109\u001a\u00020:2\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\u000b2\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u001f\u001a\u00020 2\u0006\u0010\u000f\u001a\u00020;H\u0016J%\u0010<\u001a\u00020=2\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\u000b2\n\b\u0002\u0010>\u001a\u0004\u0018\u00010?H\u0096\u0001J!\u0010@\u001a\u00020A2\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\u000b2\u0006\u0010B\u001a\u00020CH\u0096\u0001J\u0011\u0010@\u001a\u00020A2\u0006\u0010B\u001a\u00020CH\u0097\u0001JY\u0010D\u001a\u00020E2\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\u000b2\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020F2\u0006\u0010\u001f\u001a\u00020 2\u0006\u0010G\u001a\u0002042\u0006\u0010#\u001a\u00020$2\u0006\u0010H\u001a\u00020\u00122\u0006\u0010*\u001a\u00020\u00122\u0006\u0010\u0011\u001a\u00020\u0012H\u0097\u0001JX\u0010D\u001a\u00020E2\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\u000b2\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u001f\u001a\u00020 2\u0006\u0010#\u001a\u00020$2\u0006\u0010\u000f\u001a\u00020F2\u0006\u0010G\u001a\u0002042\u0006\u0010H\u001a\u00020\u00122\u0006\u0010\u0011\u001a\u00020\u00122\u0006\u0010*\u001a\u00020\u0012H\u0016J\u0097\u0001\u0010I\u001a\u00020J2\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\u000b2\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020K2\u0006\u0010\u001f\u001a\u00020 2\u0006\u0010#\u001a\u00020$2\u0006\u0010%\u001a\u00020&2\u0006\u00103\u001a\u0002042\u0006\u00105\u001a\u00020\u00122\u0006\u0010*\u001a\u00020\u00122\u0006\u0010L\u001a\u00020\u00122\u0006\u0010M\u001a\u00020\u00122\u0006\u0010N\u001a\u00020\u00122\u0006\u0010O\u001a\u00020\u00122\u0006\u0010,\u001a\u00020\u00122\b\b\u0002\u0010P\u001a\u00020\u00122\n\b\u0002\u00107\u001a\u0004\u0018\u000108H\u0097\u0001J\u0085\u0001\u0010Q\u001a\u00020R2\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\u000b2\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u001f\u001a\u00020 2\u0006\u0010#\u001a\u00020$2\u0006\u00105\u001a\u00020\u00122\u0006\u0010,\u001a\u00020\u00122\u0006\u00103\u001a\u0002042\u0006\u0010%\u001a\u00020&2\u0006\u0010L\u001a\u00020\u00122\u0006\u0010M\u001a\u00020\u00122\u0006\u0010N\u001a\u00020\u00122\u0006\u0010O\u001a\u00020\u00122\b\b\u0002\u0010*\u001a\u00020\u00122\b\b\u0002\u0010P\u001a\u00020\u0012H\u0096\u0001Jy\u0010Q\u001a\u00020J2\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\u000b2\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u001f\u001a\u00020 2\u0006\u0010#\u001a\u00020$2\u0006\u0010%\u001a\u00020&2\u0006\u00103\u001a\u0002042\u0006\u00105\u001a\u00020\u00122\u0006\u0010*\u001a\u00020\u00122\u0006\u0010L\u001a\u00020\u00122\u0006\u0010M\u001a\u00020\u00122\u0006\u0010N\u001a\u00020\u00122\u0006\u0010O\u001a\u00020\u00122\u0006\u0010,\u001a\u00020\u0012H\u0097\u0001JA\u0010S\u001a\u00020T2\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\u000b2\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020U2\u0006\u0010\u001f\u001a\u00020 2\u0006\u0010G\u001a\u0002042\u0006\u0010V\u001a\u00020\u0012H\u0097\u0001JA\u0010S\u001a\u00020T2\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\u000b2\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u001f\u001a\u00020 2\u0006\u0010\u000f\u001a\u00020U2\u0006\u0010G\u001a\u0002042\u0006\u0010V\u001a\u00020\u0012H\u0096\u0001J\u0089\u0001\u0010W\u001a\u00020X2\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\u000b2\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020Y2\u0006\u0010\u001f\u001a\u00020 2\u0006\u0010#\u001a\u00020$2\u0006\u0010%\u001a\u00020&2\u0006\u0010V\u001a\u00020\u00122\u0006\u0010Z\u001a\u00020\u00122\u0006\u0010[\u001a\u00020\u00122\u0006\u0010\\\u001a\u00020\u00122\u0006\u0010*\u001a\u00020\u00122\b\b\u0002\u0010,\u001a\u00020\u00122\b\b\u0002\u0010P\u001a\u00020\u00122\n\b\u0002\u00107\u001a\u0004\u0018\u000108H\u0097\u0001J\u0082\u0001\u0010W\u001a\u00020X2\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\u000b2\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u001f\u001a\u00020 2\u0006\u0010#\u001a\u00020$2\u0006\u0010%\u001a\u00020&2\u0006\u0010\u000f\u001a\u00020Y2\u0006\u0010V\u001a\u00020\u00122\u0006\u0010Z\u001a\u00020\u00122\u0006\u0010[\u001a\u00020\u00122\u0006\u0010\\\u001a\u00020\u00122\u0006\u0010*\u001a\u00020\u00122\b\u00107\u001a\u0004\u0018\u0001082\u0006\u0010,\u001a\u00020\u00122\u0006\u0010P\u001a\u00020\u0012H\u0016Ji\u0010]\u001a\u00020X2\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\u000b2\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u001f\u001a\u00020 2\u0006\u0010#\u001a\u00020$2\u0006\u0010%\u001a\u00020&2\u0006\u0010V\u001a\u00020\u00122\u0006\u0010Z\u001a\u00020\u00122\u0006\u0010[\u001a\u00020\u00122\u0006\u0010\\\u001a\u00020\u00122\u0006\u0010*\u001a\u00020\u00122\u0006\u0010,\u001a\u00020\u0012H\u0097\u0001Jw\u0010]\u001a\u00020^2\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\u000b2\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u001f\u001a\u00020 2\u0006\u0010#\u001a\u00020$2\u0006\u0010%\u001a\u00020&2\u0006\u0010V\u001a\u00020\u00122\u0006\u0010Z\u001a\u00020\u00122\u0006\u0010[\u001a\u00020\u00122\u0006\u0010\\\u001a\u00020\u00122\b\b\u0002\u0010*\u001a\u00020\u00122\b\b\u0002\u0010,\u001a\u00020\u00122\b\b\u0002\u0010P\u001a\u00020\u0012H\u0096\u0001J\u0092\u0001\u0010_\u001a\u00020J2\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\u000b2\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u001f\u001a\u00020 2\u0006\u0010#\u001a\u00020$2\u0006\u00105\u001a\u00020\u00122\u0006\u0010,\u001a\u00020\u00122\u0006\u00103\u001a\u0002042\u0006\u0010%\u001a\u00020&2\u0006\u0010\u000f\u001a\u00020K2\u0006\u0010L\u001a\u00020\u00122\u0006\u0010M\u001a\u00020\u00122\u0006\u0010N\u001a\u00020\u00122\u0006\u0010O\u001a\u00020\u00122\u0006\u0010*\u001a\u00020\u00122\b\u00107\u001a\u0004\u0018\u0001082\u0006\u0010P\u001a\u00020\u0012H\u0016JH\u0010`\u001a\u00020a2\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\u000b2\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u001f\u001a\u00020 2\u0006\u0010#\u001a\u00020$2\u0006\u0010\u000f\u001a\u00020b2\u0006\u0010c\u001a\u00020\u00122\u0006\u0010d\u001a\u000204H\u0016JI\u0010`\u001a\u00020a2\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\u000b2\u0006\u0010\u000f\u001a\u00020b2\u0006\u0010\u001f\u001a\u00020 2\u0006\u0010#\u001a\u00020$2\u0006\u0010d\u001a\u0002042\u0006\u0010c\u001a\u00020\u00122\u0006\u0010\r\u001a\u00020\u000eH\u0097\u0001JI\u0010e\u001a\u00020f2\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\u000b2\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020g2\u0006\u0010\u001f\u001a\u00020 2\u0006\u0010h\u001a\u00020\u000b2\u0006\u0010i\u001a\u00020\u00122\u0006\u0010j\u001a\u00020kH\u0097\u0001JH\u0010e\u001a\u00020f2\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\u000b2\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u001f\u001a\u00020 2\u0006\u0010\u000f\u001a\u00020g2\u0006\u0010j\u001a\u00020k2\u0006\u0010h\u001a\u00020\u000b2\u0006\u0010i\u001a\u00020\u0012H\u0016Jk\u0010l\u001a\u00020m2\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\u000b2\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020n2\u0006\u0010\u001f\u001a\u00020 2\u0006\u0010h\u001a\u00020\u000b2\u0006\u0010G\u001a\u0002042\b\u0010o\u001a\u0004\u0018\u0001042\u0006\u0010p\u001a\u00020\u00122\u0006\u0010q\u001a\u00020\u00122\u0006\u0010r\u001a\u00020\u00122\u0006\u0010s\u001a\u00020\u0012H\u0097\u0001Jk\u0010l\u001a\u00020m2\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\u000b2\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u001f\u001a\u00020 2\u0006\u0010G\u001a\u0002042\u0006\u0010s\u001a\u00020\u00122\u0006\u0010\u000f\u001a\u00020n2\u0006\u0010h\u001a\u00020\u000b2\b\u0010o\u001a\u0004\u0018\u0001042\u0006\u0010p\u001a\u00020\u00122\u0006\u0010q\u001a\u00020\u00122\u0006\u0010r\u001a\u00020\u0012H\u0096\u0001R\u000e\u0010\u0002\u001a\u00020\u0001X\u0082\u0004¢\u0006\u0002\n\u0000R\u0012\u0010\u0004\u001a\u00020\u0005X\u0096\u0005¢\u0006\u0006\u001a\u0004\b\u0006\u0010\u0007¨\u0006t"}, d2 = {"Lorg/jetbrains/kotlin/ir/declarations/lazy/LazyIrFactory;", "Lorg/jetbrains/kotlin/ir/declarations/IrFactory;", MethodDelegation.ImplementationDelegate.FIELD_NAME_PREFIX, "(Lorg/jetbrains/kotlin/ir/declarations/IrFactory;)V", "stageController", "Lorg/jetbrains/kotlin/ir/declarations/StageController;", "getStageController", "()Lorg/jetbrains/kotlin/ir/declarations/StageController;", "createAnonymousInitializer", "Lorg/jetbrains/kotlin/ir/declarations/IrAnonymousInitializer;", "startOffset", "", "endOffset", "origin", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationOrigin;", NativeSymbol.TYPE_NAME, "Lorg/jetbrains/kotlin/ir/symbols/IrAnonymousInitializerSymbol;", "isStatic", "", "createBlockBody", "Lorg/jetbrains/kotlin/ir/expressions/IrBlockBody;", "statements", "", "Lorg/jetbrains/kotlin/ir/IrStatement;", "initializer", "Lkotlin/Function1;", "", "Lkotlin/ExtensionFunctionType;", "createClass", "Lorg/jetbrains/kotlin/ir/declarations/IrClass;", "Lorg/jetbrains/kotlin/ir/symbols/IrClassSymbol;", "name", "Lorg/jetbrains/kotlin/name/Name;", Namer.METADATA_CLASS_KIND, "Lorg/jetbrains/kotlin/descriptors/ClassKind;", "visibility", "Lorg/jetbrains/kotlin/descriptors/DescriptorVisibility;", "modality", "Lorg/jetbrains/kotlin/descriptors/Modality;", "isCompanion", "isInner", "isData", "isExternal", "isValue", "isExpect", "isFun", FirebaseAnalytics.Param.SOURCE, "Lorg/jetbrains/kotlin/descriptors/SourceElement;", "createConstructor", "Lorg/jetbrains/kotlin/ir/declarations/IrConstructor;", "Lorg/jetbrains/kotlin/ir/symbols/IrConstructorSymbol;", "returnType", "Lorg/jetbrains/kotlin/ir/types/IrType;", "isInline", "isPrimary", "containerSource", "Lorg/jetbrains/kotlin/serialization/deserialization/descriptors/DeserializedContainerSource;", "createEnumEntry", "Lorg/jetbrains/kotlin/ir/declarations/IrEnumEntry;", "Lorg/jetbrains/kotlin/ir/symbols/IrEnumEntrySymbol;", "createErrorDeclaration", "Lorg/jetbrains/kotlin/ir/declarations/IrErrorDeclaration;", "descriptor", "Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;", "createExpressionBody", "Lorg/jetbrains/kotlin/ir/expressions/IrExpressionBody;", "expression", "Lorg/jetbrains/kotlin/ir/expressions/IrExpression;", "createField", "Lorg/jetbrains/kotlin/ir/declarations/IrField;", "Lorg/jetbrains/kotlin/ir/symbols/IrFieldSymbol;", ModuleXmlParser.TYPE, "isFinal", "createFunction", "Lorg/jetbrains/kotlin/ir/declarations/IrSimpleFunction;", "Lorg/jetbrains/kotlin/ir/symbols/IrSimpleFunctionSymbol;", "isTailrec", "isSuspend", "isOperator", "isInfix", "isFakeOverride", "createFunctionWithLateBinding", "Lorg/jetbrains/kotlin/ir/declarations/IrFunctionWithLateBinding;", "createLocalDelegatedProperty", "Lorg/jetbrains/kotlin/ir/declarations/IrLocalDelegatedProperty;", "Lorg/jetbrains/kotlin/ir/symbols/IrLocalDelegatedPropertySymbol;", "isVar", "createProperty", "Lorg/jetbrains/kotlin/ir/declarations/IrProperty;", "Lorg/jetbrains/kotlin/ir/symbols/IrPropertySymbol;", "isConst", "isLateinit", "isDelegated", "createPropertyWithLateBinding", "Lorg/jetbrains/kotlin/ir/declarations/IrPropertyWithLateBinding;", "createSimpleFunction", "createTypeAlias", "Lorg/jetbrains/kotlin/ir/declarations/IrTypeAlias;", "Lorg/jetbrains/kotlin/ir/symbols/IrTypeAliasSymbol;", "isActual", "expandedType", "createTypeParameter", "Lorg/jetbrains/kotlin/ir/declarations/IrTypeParameter;", "Lorg/jetbrains/kotlin/ir/symbols/IrTypeParameterSymbol;", "index", "isReified", "variance", "Lorg/jetbrains/kotlin/types/Variance;", "createValueParameter", "Lorg/jetbrains/kotlin/ir/declarations/IrValueParameter;", "Lorg/jetbrains/kotlin/ir/symbols/IrValueParameterSymbol;", "varargElementType", "isCrossinline", "isNoinline", "isHidden", "isAssignable", "ir.tree"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class LazyIrFactory implements IrFactory {
    private final IrFactory delegate;

    @Override // org.jetbrains.kotlin.ir.declarations.IrFactory
    public IrAnonymousInitializer createAnonymousInitializer(int startOffset, int endOffset, IrDeclarationOrigin origin, IrAnonymousInitializerSymbol symbol, boolean isStatic) {
        Intrinsics.checkNotNullParameter(origin, "origin");
        Intrinsics.checkNotNullParameter(symbol, "symbol");
        return this.delegate.createAnonymousInitializer(startOffset, endOffset, origin, symbol, isStatic);
    }

    @Override // org.jetbrains.kotlin.ir.declarations.IrFactory
    public IrBlockBody createBlockBody(int startOffset, int endOffset) {
        return this.delegate.createBlockBody(startOffset, endOffset);
    }

    @Override // org.jetbrains.kotlin.ir.declarations.IrFactory
    @Deprecated(level = DeprecationLevel.HIDDEN, message = "This method was moved to an extension. This variant of the method will be removed when the 2024.2 IntelliJ platform is shipped (see KTIJ-26314).")
    public /* synthetic */ IrBlockBody createBlockBody(int startOffset, int endOffset, List statements) {
        Intrinsics.checkNotNullParameter(statements, "statements");
        return this.delegate.createBlockBody(startOffset, endOffset, statements);
    }

    @Override // org.jetbrains.kotlin.ir.declarations.IrFactory
    @Deprecated(level = DeprecationLevel.HIDDEN, message = "This method was moved to an extension. This variant of the method will be removed when the 2024.2 IntelliJ platform is shipped (see KTIJ-26314).")
    public /* synthetic */ IrBlockBody createBlockBody(int startOffset, int endOffset, Function1 initializer) {
        Intrinsics.checkNotNullParameter(initializer, "initializer");
        return this.delegate.createBlockBody(startOffset, endOffset, initializer);
    }

    @Override // org.jetbrains.kotlin.ir.declarations.IrFactory
    @Deprecated(level = DeprecationLevel.HIDDEN, message = "The method's parameters were reordered. This variant of the method will be removed when the 2024.2 IntelliJ platform is shipped (see KTIJ-26314).")
    public /* synthetic */ IrClass createClass(int startOffset, int endOffset, IrDeclarationOrigin origin, IrClassSymbol symbol, Name name2, ClassKind kind, DescriptorVisibility visibility, Modality modality, boolean isCompanion, boolean isInner, boolean isData, boolean isExternal, boolean isValue, boolean isExpect, boolean isFun, SourceElement source) {
        Intrinsics.checkNotNullParameter(origin, "origin");
        Intrinsics.checkNotNullParameter(symbol, "symbol");
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(kind, "kind");
        Intrinsics.checkNotNullParameter(visibility, "visibility");
        Intrinsics.checkNotNullParameter(modality, "modality");
        Intrinsics.checkNotNullParameter(source, "source");
        return this.delegate.createClass(startOffset, endOffset, origin, symbol, name2, kind, visibility, modality, isCompanion, isInner, isData, isExternal, isValue, isExpect, isFun, source);
    }

    @Override // org.jetbrains.kotlin.ir.declarations.IrFactory
    @Deprecated(level = DeprecationLevel.HIDDEN, message = "The method's parameters were reordered. This variant of the method will be removed when the 2024.2 IntelliJ platform is shipped (see KTIJ-26314).")
    public /* synthetic */ IrConstructor createConstructor(int startOffset, int endOffset, IrDeclarationOrigin origin, IrConstructorSymbol symbol, Name name2, DescriptorVisibility visibility, IrType returnType, boolean isInline, boolean isExternal, boolean isPrimary, boolean isExpect, DeserializedContainerSource containerSource) {
        Intrinsics.checkNotNullParameter(origin, "origin");
        Intrinsics.checkNotNullParameter(symbol, "symbol");
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(visibility, "visibility");
        Intrinsics.checkNotNullParameter(returnType, "returnType");
        return this.delegate.createConstructor(startOffset, endOffset, origin, symbol, name2, visibility, returnType, isInline, isExternal, isPrimary, isExpect, containerSource);
    }

    @Override // org.jetbrains.kotlin.ir.declarations.IrFactory
    @Deprecated(level = DeprecationLevel.HIDDEN, message = "The method's parameters were reordered. This variant of the method will be removed when the 2024.2 IntelliJ platform is shipped (see KTIJ-26314).")
    public /* synthetic */ IrEnumEntry createEnumEntry(int startOffset, int endOffset, IrDeclarationOrigin origin, IrEnumEntrySymbol symbol, Name name2) {
        Intrinsics.checkNotNullParameter(origin, "origin");
        Intrinsics.checkNotNullParameter(symbol, "symbol");
        Intrinsics.checkNotNullParameter(name2, "name");
        return this.delegate.createEnumEntry(startOffset, endOffset, origin, symbol, name2);
    }

    @Override // org.jetbrains.kotlin.ir.declarations.IrFactory
    public IrErrorDeclaration createErrorDeclaration(int startOffset, int endOffset, DeclarationDescriptor descriptor) {
        return this.delegate.createErrorDeclaration(startOffset, endOffset, descriptor);
    }

    @Override // org.jetbrains.kotlin.ir.declarations.IrFactory
    public IrExpressionBody createExpressionBody(int startOffset, int endOffset, IrExpression expression) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        return this.delegate.createExpressionBody(startOffset, endOffset, expression);
    }

    @Override // org.jetbrains.kotlin.ir.declarations.IrFactory
    @Deprecated(level = DeprecationLevel.HIDDEN, message = "This method was moved to an extension. This variant of the method will be removed when the 2024.2 IntelliJ platform is shipped (see KTIJ-26314).")
    public /* synthetic */ IrExpressionBody createExpressionBody(IrExpression expression) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        return this.delegate.createExpressionBody(expression);
    }

    @Override // org.jetbrains.kotlin.ir.declarations.IrFactory
    @Deprecated(level = DeprecationLevel.HIDDEN, message = "The method's parameters were reordered. This variant of the method will be removed when the 2024.2 IntelliJ platform is shipped (see KTIJ-26314).")
    public /* synthetic */ IrField createField(int startOffset, int endOffset, IrDeclarationOrigin origin, IrFieldSymbol symbol, Name name2, IrType type, DescriptorVisibility visibility, boolean isFinal, boolean isExternal, boolean isStatic) {
        Intrinsics.checkNotNullParameter(origin, "origin");
        Intrinsics.checkNotNullParameter(symbol, "symbol");
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(type, "type");
        Intrinsics.checkNotNullParameter(visibility, "visibility");
        return this.delegate.createField(startOffset, endOffset, origin, symbol, name2, type, visibility, isFinal, isExternal, isStatic);
    }

    @Override // org.jetbrains.kotlin.ir.declarations.IrFactory
    @Deprecated(level = DeprecationLevel.HIDDEN, message = "The method has been renamed, and its parameters were reordered. This variant of the method will be removed when the 2024.2 IntelliJ platform is shipped (see KTIJ-26314).")
    public /* synthetic */ IrSimpleFunction createFunction(int startOffset, int endOffset, IrDeclarationOrigin origin, IrSimpleFunctionSymbol symbol, Name name2, DescriptorVisibility visibility, Modality modality, IrType returnType, boolean isInline, boolean isExternal, boolean isTailrec, boolean isSuspend, boolean isOperator, boolean isInfix, boolean isExpect, boolean isFakeOverride, DeserializedContainerSource containerSource) {
        Intrinsics.checkNotNullParameter(origin, "origin");
        Intrinsics.checkNotNullParameter(symbol, "symbol");
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(visibility, "visibility");
        Intrinsics.checkNotNullParameter(modality, "modality");
        Intrinsics.checkNotNullParameter(returnType, "returnType");
        return this.delegate.createFunction(startOffset, endOffset, origin, symbol, name2, visibility, modality, returnType, isInline, isExternal, isTailrec, isSuspend, isOperator, isInfix, isExpect, isFakeOverride, containerSource);
    }

    @Override // org.jetbrains.kotlin.ir.declarations.IrFactory
    public IrFunctionWithLateBinding createFunctionWithLateBinding(int startOffset, int endOffset, IrDeclarationOrigin origin, Name name2, DescriptorVisibility visibility, boolean isInline, boolean isExpect, IrType returnType, Modality modality, boolean isTailrec, boolean isSuspend, boolean isOperator, boolean isInfix, boolean isExternal, boolean isFakeOverride) {
        Intrinsics.checkNotNullParameter(origin, "origin");
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(visibility, "visibility");
        Intrinsics.checkNotNullParameter(returnType, "returnType");
        Intrinsics.checkNotNullParameter(modality, "modality");
        return this.delegate.createFunctionWithLateBinding(startOffset, endOffset, origin, name2, visibility, isInline, isExpect, returnType, modality, isTailrec, isSuspend, isOperator, isInfix, isExternal, isFakeOverride);
    }

    @Override // org.jetbrains.kotlin.ir.declarations.IrFactory
    @Deprecated(level = DeprecationLevel.HIDDEN, message = "The method's parameters were reordered. This variant of the method will be removed when the 2024.2 IntelliJ platform is shipped (see KTIJ-26314).")
    public /* synthetic */ IrSimpleFunction createFunctionWithLateBinding(int startOffset, int endOffset, IrDeclarationOrigin origin, Name name2, DescriptorVisibility visibility, Modality modality, IrType returnType, boolean isInline, boolean isExternal, boolean isTailrec, boolean isSuspend, boolean isOperator, boolean isInfix, boolean isExpect) {
        Intrinsics.checkNotNullParameter(origin, "origin");
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(visibility, "visibility");
        Intrinsics.checkNotNullParameter(modality, "modality");
        Intrinsics.checkNotNullParameter(returnType, "returnType");
        return this.delegate.createFunctionWithLateBinding(startOffset, endOffset, origin, name2, visibility, modality, returnType, isInline, isExternal, isTailrec, isSuspend, isOperator, isInfix, isExpect);
    }

    @Override // org.jetbrains.kotlin.ir.declarations.IrFactory
    @Deprecated(level = DeprecationLevel.HIDDEN, message = "The method's parameters were reordered. This variant of the method will be removed when the 2024.2 IntelliJ platform is shipped (see KTIJ-26314).")
    public /* synthetic */ IrLocalDelegatedProperty createLocalDelegatedProperty(int startOffset, int endOffset, IrDeclarationOrigin origin, IrLocalDelegatedPropertySymbol symbol, Name name2, IrType type, boolean isVar) {
        Intrinsics.checkNotNullParameter(origin, "origin");
        Intrinsics.checkNotNullParameter(symbol, "symbol");
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(type, "type");
        return this.delegate.createLocalDelegatedProperty(startOffset, endOffset, origin, symbol, name2, type, isVar);
    }

    @Override // org.jetbrains.kotlin.ir.declarations.IrFactory
    public IrLocalDelegatedProperty createLocalDelegatedProperty(int startOffset, int endOffset, IrDeclarationOrigin origin, Name name2, IrLocalDelegatedPropertySymbol symbol, IrType type, boolean isVar) {
        Intrinsics.checkNotNullParameter(origin, "origin");
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(symbol, "symbol");
        Intrinsics.checkNotNullParameter(type, "type");
        return this.delegate.createLocalDelegatedProperty(startOffset, endOffset, origin, name2, symbol, type, isVar);
    }

    @Override // org.jetbrains.kotlin.ir.declarations.IrFactory
    @Deprecated(level = DeprecationLevel.HIDDEN, message = "The method's parameters were reordered. This variant of the method will be removed when the 2024.2 IntelliJ platform is shipped (see KTIJ-26314).")
    public /* synthetic */ IrProperty createProperty(int startOffset, int endOffset, IrDeclarationOrigin origin, IrPropertySymbol symbol, Name name2, DescriptorVisibility visibility, Modality modality, boolean isVar, boolean isConst, boolean isLateinit, boolean isDelegated, boolean isExternal, boolean isExpect, boolean isFakeOverride, DeserializedContainerSource containerSource) {
        Intrinsics.checkNotNullParameter(origin, "origin");
        Intrinsics.checkNotNullParameter(symbol, "symbol");
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(visibility, "visibility");
        Intrinsics.checkNotNullParameter(modality, "modality");
        return this.delegate.createProperty(startOffset, endOffset, origin, symbol, name2, visibility, modality, isVar, isConst, isLateinit, isDelegated, isExternal, isExpect, isFakeOverride, containerSource);
    }

    @Override // org.jetbrains.kotlin.ir.declarations.IrFactory
    @Deprecated(level = DeprecationLevel.HIDDEN, message = "The method's parameters were reordered. This variant of the method will be removed when the 2024.2 IntelliJ platform is shipped (see KTIJ-26314).")
    public /* synthetic */ IrProperty createPropertyWithLateBinding(int startOffset, int endOffset, IrDeclarationOrigin origin, Name name2, DescriptorVisibility visibility, Modality modality, boolean isVar, boolean isConst, boolean isLateinit, boolean isDelegated, boolean isExternal, boolean isExpect) {
        Intrinsics.checkNotNullParameter(origin, "origin");
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(visibility, "visibility");
        Intrinsics.checkNotNullParameter(modality, "modality");
        return this.delegate.createPropertyWithLateBinding(startOffset, endOffset, origin, name2, visibility, modality, isVar, isConst, isLateinit, isDelegated, isExternal, isExpect);
    }

    @Override // org.jetbrains.kotlin.ir.declarations.IrFactory
    public IrPropertyWithLateBinding createPropertyWithLateBinding(int startOffset, int endOffset, IrDeclarationOrigin origin, Name name2, DescriptorVisibility visibility, Modality modality, boolean isVar, boolean isConst, boolean isLateinit, boolean isDelegated, boolean isExternal, boolean isExpect, boolean isFakeOverride) {
        Intrinsics.checkNotNullParameter(origin, "origin");
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(visibility, "visibility");
        Intrinsics.checkNotNullParameter(modality, "modality");
        return this.delegate.createPropertyWithLateBinding(startOffset, endOffset, origin, name2, visibility, modality, isVar, isConst, isLateinit, isDelegated, isExternal, isExpect, isFakeOverride);
    }

    @Override // org.jetbrains.kotlin.ir.declarations.IrFactory
    @Deprecated(level = DeprecationLevel.HIDDEN, message = "The method's parameters were reordered. This variant of the method will be removed when the 2024.2 IntelliJ platform is shipped (see KTIJ-26314).")
    public /* synthetic */ IrTypeAlias createTypeAlias(int startOffset, int endOffset, IrTypeAliasSymbol symbol, Name name2, DescriptorVisibility visibility, IrType expandedType, boolean isActual, IrDeclarationOrigin origin) {
        Intrinsics.checkNotNullParameter(symbol, "symbol");
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(visibility, "visibility");
        Intrinsics.checkNotNullParameter(expandedType, "expandedType");
        Intrinsics.checkNotNullParameter(origin, "origin");
        return this.delegate.createTypeAlias(startOffset, endOffset, symbol, name2, visibility, expandedType, isActual, origin);
    }

    @Override // org.jetbrains.kotlin.ir.declarations.IrFactory
    @Deprecated(level = DeprecationLevel.HIDDEN, message = "The method's parameters were reordered. This variant of the method will be removed when the 2024.2 IntelliJ platform is shipped (see KTIJ-26314).")
    public /* synthetic */ IrTypeParameter createTypeParameter(int startOffset, int endOffset, IrDeclarationOrigin origin, IrTypeParameterSymbol symbol, Name name2, int index, boolean isReified, Variance variance) {
        Intrinsics.checkNotNullParameter(origin, "origin");
        Intrinsics.checkNotNullParameter(symbol, "symbol");
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(variance, "variance");
        return this.delegate.createTypeParameter(startOffset, endOffset, origin, symbol, name2, index, isReified, variance);
    }

    @Override // org.jetbrains.kotlin.ir.declarations.IrFactory
    @Deprecated(level = DeprecationLevel.HIDDEN, message = "The method's parameters were reordered. This variant of the method will be removed when the 2024.2 IntelliJ platform is shipped (see KTIJ-26314).")
    public /* synthetic */ IrValueParameter createValueParameter(int startOffset, int endOffset, IrDeclarationOrigin origin, IrValueParameterSymbol symbol, Name name2, int index, IrType type, IrType varargElementType, boolean isCrossinline, boolean isNoinline, boolean isHidden, boolean isAssignable) {
        Intrinsics.checkNotNullParameter(origin, "origin");
        Intrinsics.checkNotNullParameter(symbol, "symbol");
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(type, "type");
        return this.delegate.createValueParameter(startOffset, endOffset, origin, symbol, name2, index, type, varargElementType, isCrossinline, isNoinline, isHidden, isAssignable);
    }

    @Override // org.jetbrains.kotlin.ir.declarations.IrFactory
    public IrValueParameter createValueParameter(int startOffset, int endOffset, IrDeclarationOrigin origin, Name name2, IrType type, boolean isAssignable, IrValueParameterSymbol symbol, int index, IrType varargElementType, boolean isCrossinline, boolean isNoinline, boolean isHidden) {
        Intrinsics.checkNotNullParameter(origin, "origin");
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(type, "type");
        Intrinsics.checkNotNullParameter(symbol, "symbol");
        return this.delegate.createValueParameter(startOffset, endOffset, origin, name2, type, isAssignable, symbol, index, varargElementType, isCrossinline, isNoinline, isHidden);
    }

    @Override // org.jetbrains.kotlin.ir.declarations.IrFactory
    public StageController getStageController() {
        return this.delegate.getStageController();
    }

    public LazyIrFactory(IrFactory delegate) {
        Intrinsics.checkNotNullParameter(delegate, "delegate");
        this.delegate = delegate;
    }

    @Override // org.jetbrains.kotlin.ir.declarations.IrFactory
    public IrClass createClass(int startOffset, int endOffset, IrDeclarationOrigin origin, Name name2, DescriptorVisibility visibility, IrClassSymbol symbol, ClassKind kind, Modality modality, boolean isExternal, boolean isCompanion, boolean isInner, boolean isData, boolean isValue, boolean isExpect, boolean isFun, SourceElement source) {
        Intrinsics.checkNotNullParameter(origin, "origin");
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(visibility, "visibility");
        Intrinsics.checkNotNullParameter(symbol, "symbol");
        Intrinsics.checkNotNullParameter(kind, "kind");
        Intrinsics.checkNotNullParameter(modality, "modality");
        Intrinsics.checkNotNullParameter(source, "source");
        if (symbol.isBound()) {
            return symbol.getOwner();
        }
        return this.delegate.createClass(startOffset, endOffset, origin, name2, visibility, symbol, kind, modality, isExternal, isCompanion, isInner, isData, isValue, isExpect, isFun, source);
    }

    @Override // org.jetbrains.kotlin.ir.declarations.IrFactory
    public IrConstructor createConstructor(int startOffset, int endOffset, IrDeclarationOrigin origin, Name name2, DescriptorVisibility visibility, boolean isInline, boolean isExpect, IrType returnType, IrConstructorSymbol symbol, boolean isPrimary, boolean isExternal, DeserializedContainerSource containerSource) {
        Intrinsics.checkNotNullParameter(origin, "origin");
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(visibility, "visibility");
        Intrinsics.checkNotNullParameter(returnType, "returnType");
        Intrinsics.checkNotNullParameter(symbol, "symbol");
        if (symbol.isBound()) {
            return symbol.getOwner();
        }
        return this.delegate.createConstructor(startOffset, endOffset, origin, name2, visibility, isInline, isExpect, returnType, symbol, isPrimary, isExternal, containerSource);
    }

    @Override // org.jetbrains.kotlin.ir.declarations.IrFactory
    public IrEnumEntry createEnumEntry(int startOffset, int endOffset, IrDeclarationOrigin origin, Name name2, IrEnumEntrySymbol symbol) {
        Intrinsics.checkNotNullParameter(origin, "origin");
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(symbol, "symbol");
        if (symbol.isBound()) {
            return symbol.getOwner();
        }
        return this.delegate.createEnumEntry(startOffset, endOffset, origin, name2, symbol);
    }

    @Override // org.jetbrains.kotlin.ir.declarations.IrFactory
    public IrField createField(int startOffset, int endOffset, IrDeclarationOrigin origin, Name name2, DescriptorVisibility visibility, IrFieldSymbol symbol, IrType type, boolean isFinal, boolean isStatic, boolean isExternal) {
        Intrinsics.checkNotNullParameter(origin, "origin");
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(visibility, "visibility");
        Intrinsics.checkNotNullParameter(symbol, "symbol");
        Intrinsics.checkNotNullParameter(type, "type");
        if (symbol.isBound()) {
            return symbol.getOwner();
        }
        return this.delegate.createField(startOffset, endOffset, origin, name2, visibility, symbol, type, isFinal, isStatic, isExternal);
    }

    @Override // org.jetbrains.kotlin.ir.declarations.IrFactory
    public IrSimpleFunction createSimpleFunction(int startOffset, int endOffset, IrDeclarationOrigin origin, Name name2, DescriptorVisibility visibility, boolean isInline, boolean isExpect, IrType returnType, Modality modality, IrSimpleFunctionSymbol symbol, boolean isTailrec, boolean isSuspend, boolean isOperator, boolean isInfix, boolean isExternal, DeserializedContainerSource containerSource, boolean isFakeOverride) {
        Intrinsics.checkNotNullParameter(origin, "origin");
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(visibility, "visibility");
        Intrinsics.checkNotNullParameter(returnType, "returnType");
        Intrinsics.checkNotNullParameter(modality, "modality");
        Intrinsics.checkNotNullParameter(symbol, "symbol");
        if (symbol.isBound()) {
            return symbol.getOwner();
        }
        return this.delegate.createSimpleFunction(startOffset, endOffset, origin, name2, visibility, isInline, isExpect, returnType, modality, symbol, isTailrec, isSuspend, isOperator, isInfix, isExternal, containerSource, isFakeOverride);
    }

    @Override // org.jetbrains.kotlin.ir.declarations.IrFactory
    public IrProperty createProperty(int startOffset, int endOffset, IrDeclarationOrigin origin, Name name2, DescriptorVisibility visibility, Modality modality, IrPropertySymbol symbol, boolean isVar, boolean isConst, boolean isLateinit, boolean isDelegated, boolean isExternal, DeserializedContainerSource containerSource, boolean isExpect, boolean isFakeOverride) {
        Intrinsics.checkNotNullParameter(origin, "origin");
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(visibility, "visibility");
        Intrinsics.checkNotNullParameter(modality, "modality");
        Intrinsics.checkNotNullParameter(symbol, "symbol");
        if (symbol.isBound()) {
            return symbol.getOwner();
        }
        return this.delegate.createProperty(startOffset, endOffset, origin, name2, visibility, modality, symbol, isVar, isConst, isLateinit, isDelegated, isExternal, containerSource, isExpect, isFakeOverride);
    }

    @Override // org.jetbrains.kotlin.ir.declarations.IrFactory
    public IrTypeAlias createTypeAlias(int startOffset, int endOffset, IrDeclarationOrigin origin, Name name2, DescriptorVisibility visibility, IrTypeAliasSymbol symbol, boolean isActual, IrType expandedType) {
        Intrinsics.checkNotNullParameter(origin, "origin");
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(visibility, "visibility");
        Intrinsics.checkNotNullParameter(symbol, "symbol");
        Intrinsics.checkNotNullParameter(expandedType, "expandedType");
        if (symbol.isBound()) {
            return symbol.getOwner();
        }
        return this.delegate.createTypeAlias(startOffset, endOffset, origin, name2, visibility, symbol, isActual, expandedType);
    }

    @Override // org.jetbrains.kotlin.ir.declarations.IrFactory
    public IrTypeParameter createTypeParameter(int startOffset, int endOffset, IrDeclarationOrigin origin, Name name2, IrTypeParameterSymbol symbol, Variance variance, int index, boolean isReified) {
        Intrinsics.checkNotNullParameter(origin, "origin");
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(symbol, "symbol");
        Intrinsics.checkNotNullParameter(variance, "variance");
        if (symbol.isBound()) {
            return symbol.getOwner();
        }
        return this.delegate.createTypeParameter(startOffset, endOffset, origin, name2, symbol, variance, index, isReified);
    }
}
