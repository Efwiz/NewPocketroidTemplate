package org.jetbrains.kotlin.ir.util;

import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.PropertyReference1Impl;
import kotlin.jvm.internal.Reflection;
import kotlin.properties.ReadWriteProperty;
import kotlin.reflect.KProperty;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.descriptors.CallableMemberDescriptor;
import org.jetbrains.kotlin.descriptors.ClassConstructorDescriptor;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.descriptors.DescriptorVisibility;
import org.jetbrains.kotlin.descriptors.FunctionDescriptor;
import org.jetbrains.kotlin.descriptors.Modality;
import org.jetbrains.kotlin.descriptors.PackageFragmentDescriptor;
import org.jetbrains.kotlin.descriptors.ParameterDescriptor;
import org.jetbrains.kotlin.descriptors.PropertyDescriptor;
import org.jetbrains.kotlin.descriptors.ScriptDescriptor;
import org.jetbrains.kotlin.descriptors.TypeAliasDescriptor;
import org.jetbrains.kotlin.descriptors.TypeParameterDescriptor;
import org.jetbrains.kotlin.descriptors.ValueDescriptor;
import org.jetbrains.kotlin.descriptors.VariableDescriptor;
import org.jetbrains.kotlin.descriptors.VariableDescriptorWithAccessors;
import org.jetbrains.kotlin.ir.ObsoleteDescriptorBasedAPI;
import org.jetbrains.kotlin.ir.declarations.DescriptorMetadataSource;
import org.jetbrains.kotlin.ir.declarations.IrAnonymousInitializer;
import org.jetbrains.kotlin.ir.declarations.IrClass;
import org.jetbrains.kotlin.ir.declarations.IrConstructor;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationOrigin;
import org.jetbrains.kotlin.ir.declarations.IrEnumEntry;
import org.jetbrains.kotlin.ir.declarations.IrExternalPackageFragment;
import org.jetbrains.kotlin.ir.declarations.IrFactory;
import org.jetbrains.kotlin.ir.declarations.IrField;
import org.jetbrains.kotlin.ir.declarations.IrLocalDelegatedProperty;
import org.jetbrains.kotlin.ir.declarations.IrProperty;
import org.jetbrains.kotlin.ir.declarations.IrScript;
import org.jetbrains.kotlin.ir.declarations.IrSimpleFunction;
import org.jetbrains.kotlin.ir.declarations.IrTypeParameter;
import org.jetbrains.kotlin.ir.declarations.IrValueParameter;
import org.jetbrains.kotlin.ir.declarations.IrVariable;
import org.jetbrains.kotlin.ir.declarations.impl.IrExternalPackageFragmentImpl;
import org.jetbrains.kotlin.ir.declarations.impl.IrScriptImpl;
import org.jetbrains.kotlin.ir.declarations.impl.IrVariableImpl;
import org.jetbrains.kotlin.ir.descriptors.IrBasedClassDescriptor;
import org.jetbrains.kotlin.ir.expressions.IrExpression;
import org.jetbrains.kotlin.ir.symbols.IrBindableSymbol;
import org.jetbrains.kotlin.ir.symbols.IrClassSymbol;
import org.jetbrains.kotlin.ir.symbols.IrConstructorSymbol;
import org.jetbrains.kotlin.ir.symbols.IrEnumEntrySymbol;
import org.jetbrains.kotlin.ir.symbols.IrExternalPackageFragmentSymbol;
import org.jetbrains.kotlin.ir.symbols.IrFieldSymbol;
import org.jetbrains.kotlin.ir.symbols.IrLocalDelegatedPropertySymbol;
import org.jetbrains.kotlin.ir.symbols.IrPropertySymbol;
import org.jetbrains.kotlin.ir.symbols.IrScriptSymbol;
import org.jetbrains.kotlin.ir.symbols.IrSimpleFunctionSymbol;
import org.jetbrains.kotlin.ir.symbols.IrTypeAliasSymbol;
import org.jetbrains.kotlin.ir.symbols.IrTypeParameterSymbol;
import org.jetbrains.kotlin.ir.symbols.IrValueParameterSymbol;
import org.jetbrains.kotlin.ir.symbols.IrValueSymbol;
import org.jetbrains.kotlin.ir.symbols.IrVariableSymbol;
import org.jetbrains.kotlin.ir.symbols.impl.IrAnonymousInitializerSymbolImpl;
import org.jetbrains.kotlin.ir.symbols.impl.IrClassPublicSymbolImpl;
import org.jetbrains.kotlin.ir.symbols.impl.IrClassSymbolImpl;
import org.jetbrains.kotlin.ir.symbols.impl.IrConstructorPublicSymbolImpl;
import org.jetbrains.kotlin.ir.symbols.impl.IrConstructorSymbolImpl;
import org.jetbrains.kotlin.ir.symbols.impl.IrEnumEntryPublicSymbolImpl;
import org.jetbrains.kotlin.ir.symbols.impl.IrEnumEntrySymbolImpl;
import org.jetbrains.kotlin.ir.symbols.impl.IrExternalPackageFragmentSymbolImpl;
import org.jetbrains.kotlin.ir.symbols.impl.IrFieldPublicSymbolImpl;
import org.jetbrains.kotlin.ir.symbols.impl.IrFieldSymbolImpl;
import org.jetbrains.kotlin.ir.symbols.impl.IrLocalDelegatedPropertySymbolImpl;
import org.jetbrains.kotlin.ir.symbols.impl.IrPropertyPublicSymbolImpl;
import org.jetbrains.kotlin.ir.symbols.impl.IrPropertySymbolImpl;
import org.jetbrains.kotlin.ir.symbols.impl.IrScriptSymbolImpl;
import org.jetbrains.kotlin.ir.symbols.impl.IrSimpleFunctionPublicSymbolImpl;
import org.jetbrains.kotlin.ir.symbols.impl.IrSimpleFunctionSymbolImpl;
import org.jetbrains.kotlin.ir.symbols.impl.IrTypeAliasPublicSymbolImpl;
import org.jetbrains.kotlin.ir.symbols.impl.IrTypeAliasSymbolImpl;
import org.jetbrains.kotlin.ir.symbols.impl.IrTypeParameterPublicSymbolImpl;
import org.jetbrains.kotlin.ir.symbols.impl.IrTypeParameterSymbolImpl;
import org.jetbrains.kotlin.ir.symbols.impl.IrValueParameterSymbolImpl;
import org.jetbrains.kotlin.ir.symbols.impl.IrVariableSymbolImpl;
import org.jetbrains.kotlin.ir.types.IrType;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.resolve.descriptorUtil.DescriptorUtilsKt;
import org.jetbrains.kotlin.serialization.deserialization.descriptors.DeserializedContainerSource;
import org.jetbrains.kotlin.types.Variance;
import org.jetbrains.kotlin.utils.ThreadLocalKt;
import org.mozilla.javascript.NativeSymbol;

/* JADX INFO: compiled from: DescriptorSymbolTableExtension.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000\u0092\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\n\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010!\n\u0000\b\u0017\u0018\u000028\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020\b\u0012\u0004\u0012\u00020\t\u0012\u0004\u0012\u00020\n0\u0001B\r\u0012\u0006\u0010\u000b\u001a\u00020\f¢\u0006\u0002\u0010\rJ\u0012\u00103\u001a\u0004\u0018\u0001042\u0006\u00105\u001a\u00020\u0003H\u0014J\u0012\u00106\u001a\u0004\u0018\u0001042\u0006\u00105\u001a\u00020\bH\u0014J\u0012\u00107\u001a\u0004\u0018\u0001042\u0006\u00105\u001a\u00020\u0002H\u0014J\u0010\u00108\u001a\u0002092\u0006\u0010:\u001a\u00020\u0003H\u0014J\u0010\u0010;\u001a\u00020<2\u0006\u00105\u001a\u00020\u0007H\u0014J\u0010\u0010=\u001a\u00020>2\u0006\u00105\u001a\u00020\u0003H\u0014J\u0010\u0010?\u001a\u00020@2\u0006\u00105\u001a\u00020\bH\u0014J\u0010\u0010A\u001a\u00020B2\u0006\u00105\u001a\u00020\u0006H\u0014J\u0010\u0010C\u001a\u00020D2\u0006\u00105\u001a\u00020\bH\u0014J\u0010\u0010E\u001a\u00020F2\u0006\u00105\u001a\u00020\u0004H\u0014J\u0010\u0010G\u001a\u00020H2\u0006\u00105\u001a\u00020\nH\u0014J\u0018\u0010I\u001a\u0002092\u0006\u00105\u001a\u00020\u00032\u0006\u0010J\u001a\u000204H\u0014J\u0018\u0010K\u001a\u00020<2\u0006\u00105\u001a\u00020\u00072\u0006\u0010J\u001a\u000204H\u0014J\u0018\u0010L\u001a\u00020>2\u0006\u00105\u001a\u00020\u00032\u0006\u0010J\u001a\u000204H\u0014J\u0018\u0010M\u001a\u00020@2\u0006\u00105\u001a\u00020\b2\u0006\u0010J\u001a\u000204H\u0014J\u0018\u0010N\u001a\u00020B2\u0006\u00105\u001a\u00020\u00062\u0006\u0010J\u001a\u000204H\u0014J\u0018\u0010O\u001a\u00020D2\u0006\u00105\u001a\u00020\b2\u0006\u0010J\u001a\u000204H\u0014J\u0018\u0010P\u001a\u00020F2\u0006\u00105\u001a\u00020\u00042\u0006\u0010J\u001a\u000204H\u0014J\u0018\u0010Q\u001a\u00020H2\u0006\u00105\u001a\u00020\n2\u0006\u0010J\u001a\u000204H\u0014J\u001a\u0010R\u001a\u00020S2\u0006\u00105\u001a\u00020\u00052\b\u0010J\u001a\u0004\u0018\u000104H\u0014J&\u0010T\u001a\u00020U2\u0006\u0010V\u001a\u00020W2\u0006\u0010X\u001a\u00020W2\u0006\u0010Y\u001a\u00020Z2\u0006\u0010:\u001a\u00020\u0003J*\u0010[\u001a\u00020\\2\u0006\u0010:\u001a\u00020\u00032\u0006\u0010J\u001a\u0002042\u0012\u0010]\u001a\u000e\u0012\u0004\u0012\u000209\u0012\u0004\u0012\u00020\\0^J*\u0010_\u001a\u00020`2\u0006\u0010:\u001a\u00020\u00072\u0006\u0010J\u001a\u0002042\u0012\u0010a\u001a\u000e\u0012\u0004\u0012\u00020<\u0012\u0004\u0012\u00020`0^J*\u0010b\u001a\u00020c2\u0006\u0010:\u001a\u00020\u00032\u0006\u0010J\u001a\u0002042\u0012\u0010d\u001a\u000e\u0012\u0004\u0012\u00020>\u0012\u0004\u0012\u00020c0^J\u000e\u0010e\u001a\u00020\u00112\u0006\u0010:\u001a\u00020\u0010J\u000e\u0010f\u001a\u00020\u00112\u0006\u0010:\u001a\u00020\u0010J\u009d\u0002\u0010g\u001a\u0002Hh\"\b\b\u0000\u0010i*\u00020\u0002\"\u0012\b\u0001\u0010j*\f\u0012\u0002\b\u0003\u0012\u0004\u0012\u0002Hh0k\"\b\b\u0002\u0010h*\u00020l2\u0006\u0010:\u001a\u0002Hi2\u0006\u0010J\u001a\u0002042K\u0010m\u001aG\u0012\u0004\u0012\u00020\f\u0012\u0004\u0012\u000204\u0012\n\u0012\b\u0012\u0004\u0012\u0002Hj0o\u0012 \u0012\u001e\u0012\u0004\u0012\u0002Hj\u0012\u0004\u0012\u0002Hh0^j\u000e\u0012\u0004\u0012\u0002Hj\u0012\u0004\u0012\u0002Hh`p\u0012\u0004\u0012\u0002Hh0n¢\u0006\u0002\bq24\u0010r\u001a0\u0012\u0004\u0012\u0002Hi\u0012 \u0012\u001e\u0012\u0004\u0012\u0002Hj\u0012\u0004\u0012\u0002Hh0^j\u000e\u0012\u0004\u0012\u0002Hj\u0012\u0004\u0012\u0002Hh`p\u0012\u0004\u0012\u0002Hh0s2,\b\u0004\u0010t\u001a&\u0012\u0004\u0012\u0002Hi\u0012\u0006\u0012\u0004\u0018\u000104\u0012\u0004\u0012\u0002Hj0sj\u000e\u0012\u0004\u0012\u0002Hi\u0012\u0004\u0012\u0002Hj`u2$\b\b\u0010v\u001a\u001e\u0012\u0004\u0012\u0002Hj\u0012\u0004\u0012\u0002Hh0^j\u000e\u0012\u0004\u0012\u0002Hj\u0012\u0004\u0012\u0002Hh`pH\u0082\b¢\u0006\u0002\u0010wJ.\u0010x\u001a\u00020\u001a2\u0006\u0010V\u001a\u00020W2\u0006\u0010X\u001a\u00020W2\u0006\u0010Y\u001a\u00020Z2\u0006\u0010:\u001a\u00020\u00192\u0006\u0010y\u001a\u00020zJ*\u0010{\u001a\u00020|2\u0006\u0010:\u001a\u00020\b2\u0006\u0010J\u001a\u0002042\u0012\u0010}\u001a\u000e\u0012\u0004\u0012\u00020D\u0012\u0004\u0012\u00020|0^J+\u0010~\u001a\u00020\u007f2\u0006\u0010:\u001a\u00020\u00062\u0006\u0010J\u001a\u0002042\u0013\u0010\u0080\u0001\u001a\u000e\u0012\u0004\u0012\u00020B\u0012\u0004\u0012\u00020\u007f0^J\u0080\u0001\u0010\u0081\u0001\u001a\u00020)2\u0006\u0010V\u001a\u00020W2\u0006\u0010X\u001a\u00020W2\u0006\u0010Y\u001a\u00020Z2\u0006\u0010:\u001a\u00020\t2\u0006\u0010y\u001a\u00020z2\u000b\b\u0002\u0010\u0082\u0001\u001a\u0004\u0018\u00010z2\f\b\u0002\u0010\u0083\u0001\u001a\u0005\u0018\u00010\u0084\u00012\u000b\b\u0002\u0010\u0085\u0001\u001a\u0004\u0018\u00010W2\n\b\u0002\u0010\u0086\u0001\u001a\u00030\u0087\u00012\u0015\b\u0002\u0010\u0088\u0001\u001a\u000e\u0012\u0004\u0012\u00020*\u0012\u0004\u0012\u00020)0^¢\u0006\u0003\u0010\u0089\u0001J/\u0010\u008a\u0001\u001a\u00020/2\u0006\u0010V\u001a\u00020W2\u0006\u0010X\u001a\u00020W2\u0006\u0010Y\u001a\u00020Z2\u0006\u0010:\u001a\u00020.2\u0006\u0010y\u001a\u00020zJ;\u0010\u008a\u0001\u001a\u00020/2\u0006\u0010V\u001a\u00020W2\u0006\u0010X\u001a\u00020W2\u0006\u0010Y\u001a\u00020Z2\u0006\u0010:\u001a\u00020.2\u0006\u0010y\u001a\u00020z2\n\u0010\u008b\u0001\u001a\u0005\u0018\u00010\u008c\u0001J3\u0010\u008d\u0001\u001a\u00020c2\u0006\u0010V\u001a\u00020W2\u0006\u0010X\u001a\u00020W2\u0006\u0010Y\u001a\u00020Z2\u0007\u0010\u008e\u0001\u001a\u00020\u00032\u0007\u0010\u008f\u0001\u001a\u00020>H\u0014JG\u0010\u0090\u0001\u001a\u00030\u0091\u00012\u0006\u0010V\u001a\u00020W2\u0006\u0010X\u001a\u00020W2\u0006\u0010Y\u001a\u00020Z2\u0006\u00105\u001a\u00020\b2\u0006\u0010y\u001a\u00020z2\n\u0010\u0092\u0001\u001a\u0005\u0018\u00010\u0093\u00012\u0007\u0010\u008f\u0001\u001a\u00020@H\u0014J<\u0010\u0094\u0001\u001a\u00020|2\u0006\u0010V\u001a\u00020W2\u0006\u0010X\u001a\u00020W2\u0006\u0010Y\u001a\u00020Z2\u0006\u00105\u001a\u00020\b2\b\u0010\u0095\u0001\u001a\u00030\u0087\u00012\u0007\u0010\u008f\u0001\u001a\u00020DH\u0014J,\u0010\u0096\u0001\u001a\u00030\u0097\u00012\u0006\u0010V\u001a\u00020W2\u0006\u0010X\u001a\u00020W2\u0007\u0010\u0098\u0001\u001a\u00020\u00052\u0007\u0010\u008f\u0001\u001a\u00020SH\u0014J3\u0010\u0099\u0001\u001a\u00030\u009a\u00012\u0006\u0010V\u001a\u00020W2\u0006\u0010X\u001a\u00020W2\u0006\u0010Y\u001a\u00020Z2\u0006\u00105\u001a\u00020\n2\u0007\u0010\u008f\u0001\u001a\u00020HH\u0014J\u0011\u0010\u009b\u0001\u001a\u00030\u009c\u00012\u0007\u0010\u009d\u0001\u001a\u00020)J\u0011\u0010\u009e\u0001\u001a\u0002092\u0006\u00105\u001a\u00020\u0003H\u0016J\u000f\u0010\u009f\u0001\u001a\u00020\u00122\u0006\u0010:\u001a\u00020\u0010J\u000f\u0010 \u0001\u001a\u00020\u001b2\u0006\u0010:\u001a\u00020\u0019J\u0014\u0010¡\u0001\u001a\u00030¢\u00012\b\u0010£\u0001\u001a\u00030¤\u0001H\u0016J\u0011\u0010¥\u0001\u001a\u00020*2\u0006\u00105\u001a\u00020\tH\u0016J!\u0010¦\u0001\u001a\u00030\u009c\u0001*\u0015\u0012\u0010\u0012\u000e\u0012\u0002\b\u0003\u0012\u0002\b\u0003\u0012\u0002\b\u00030\u00180§\u0001H\u0014R \u0010\u000e\u001a\u0014\u0012\u0004\u0012\u00020\u0010\u0012\u0004\u0012\u00020\u0011\u0012\u0004\u0012\u00020\u00120\u000fX\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u0013\u001a\u00020\u00148BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\u0015\u0010\u0016R-\u0010\u0017\u001a\u0014\u0012\u0004\u0012\u00020\u0019\u0012\u0004\u0012\u00020\u001a\u0012\u0004\u0012\u00020\u001b0\u00188BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\u001e\u0010\u001f\u001a\u0004\b\u001c\u0010\u001dR\u0014\u0010 \u001a\u00020!8BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\"\u0010#R\u0014\u0010$\u001a\u00020%8BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b&\u0010'R-\u0010(\u001a\u0014\u0012\u0004\u0012\u00020\t\u0012\u0004\u0012\u00020)\u0012\u0004\u0012\u00020*0\u00188BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b,\u0010\u001f\u001a\u0004\b+\u0010\u001dR-\u0010-\u001a\u0014\u0012\u0004\u0012\u00020.\u0012\u0004\u0012\u00020/\u0012\u0004\u0012\u0002000\u00188BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b2\u0010\u001f\u001a\u0004\b1\u0010\u001d¨\u0006¨\u0001"}, d2 = {"Lorg/jetbrains/kotlin/ir/util/DescriptorSymbolTableExtension;", "Lorg/jetbrains/kotlin/ir/util/SymbolTableExtension;", "Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;", "Lorg/jetbrains/kotlin/descriptors/ClassDescriptor;", "Lorg/jetbrains/kotlin/descriptors/TypeAliasDescriptor;", "Lorg/jetbrains/kotlin/descriptors/ScriptDescriptor;", "Lorg/jetbrains/kotlin/descriptors/FunctionDescriptor;", "Lorg/jetbrains/kotlin/descriptors/ClassConstructorDescriptor;", "Lorg/jetbrains/kotlin/descriptors/PropertyDescriptor;", "Lorg/jetbrains/kotlin/descriptors/ParameterDescriptor;", "Lorg/jetbrains/kotlin/descriptors/TypeParameterDescriptor;", "table", "Lorg/jetbrains/kotlin/ir/util/SymbolTable;", "(Lorg/jetbrains/kotlin/ir/util/SymbolTable;)V", "externalPackageFragmentSlice", "Lorg/jetbrains/kotlin/ir/util/SymbolTableSlice;", "Lorg/jetbrains/kotlin/descriptors/PackageFragmentDescriptor;", "Lorg/jetbrains/kotlin/ir/declarations/IrExternalPackageFragment;", "Lorg/jetbrains/kotlin/ir/symbols/IrExternalPackageFragmentSymbol;", "irFactory", "Lorg/jetbrains/kotlin/ir/declarations/IrFactory;", "getIrFactory", "()Lorg/jetbrains/kotlin/ir/declarations/IrFactory;", "localDelegatedPropertySlice", "Lorg/jetbrains/kotlin/ir/util/SymbolTableSlice$Scoped;", "Lorg/jetbrains/kotlin/descriptors/VariableDescriptorWithAccessors;", "Lorg/jetbrains/kotlin/ir/declarations/IrLocalDelegatedProperty;", "Lorg/jetbrains/kotlin/ir/symbols/IrLocalDelegatedPropertySymbol;", "getLocalDelegatedPropertySlice", "()Lorg/jetbrains/kotlin/ir/util/SymbolTableSlice$Scoped;", "localDelegatedPropertySlice$delegate", "Lkotlin/properties/ReadWriteProperty;", "nameProvider", "Lorg/jetbrains/kotlin/ir/util/NameProvider;", "getNameProvider", "()Lorg/jetbrains/kotlin/ir/util/NameProvider;", "signatureComposer", "Lorg/jetbrains/kotlin/ir/util/IdSignatureComposer;", "getSignatureComposer", "()Lorg/jetbrains/kotlin/ir/util/IdSignatureComposer;", "valueParameterSlice", "Lorg/jetbrains/kotlin/ir/declarations/IrValueParameter;", "Lorg/jetbrains/kotlin/ir/symbols/IrValueParameterSymbol;", "getValueParameterSlice", "valueParameterSlice$delegate", "variableSlice", "Lorg/jetbrains/kotlin/descriptors/VariableDescriptor;", "Lorg/jetbrains/kotlin/ir/declarations/IrVariable;", "Lorg/jetbrains/kotlin/ir/symbols/IrVariableSymbol;", "getVariableSlice", "variableSlice$delegate", "calculateEnumEntrySignature", "Lorg/jetbrains/kotlin/ir/util/IdSignature;", "declaration", "calculateFieldSignature", "calculateSignature", "createPrivateClassSymbol", "Lorg/jetbrains/kotlin/ir/symbols/IrClassSymbol;", "descriptor", "createPrivateConstructorSymbol", "Lorg/jetbrains/kotlin/ir/symbols/IrConstructorSymbol;", "createPrivateEnumEntrySymbol", "Lorg/jetbrains/kotlin/ir/symbols/IrEnumEntrySymbol;", "createPrivateFieldSymbol", "Lorg/jetbrains/kotlin/ir/symbols/IrFieldSymbol;", "createPrivateFunctionSymbol", "Lorg/jetbrains/kotlin/ir/symbols/IrSimpleFunctionSymbol;", "createPrivatePropertySymbol", "Lorg/jetbrains/kotlin/ir/symbols/IrPropertySymbol;", "createPrivateTypeAliasSymbol", "Lorg/jetbrains/kotlin/ir/symbols/IrTypeAliasSymbol;", "createPrivateTypeParameterSymbol", "Lorg/jetbrains/kotlin/ir/symbols/IrTypeParameterSymbol;", "createPublicClassSymbol", "signature", "createPublicConstructorSymbol", "createPublicEnumEntrySymbol", "createPublicFieldSymbol", "createPublicFunctionSymbol", "createPublicPropertySymbol", "createPublicTypeAliasSymbol", "createPublicTypeParameterSymbol", "createScriptSymbol", "Lorg/jetbrains/kotlin/ir/symbols/IrScriptSymbol;", "declareAnonymousInitializer", "Lorg/jetbrains/kotlin/ir/declarations/IrAnonymousInitializer;", "startOffset", "", "endOffset", "origin", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationOrigin;", "declareClassFromLinker", "Lorg/jetbrains/kotlin/ir/declarations/IrClass;", "classFactory", "Lkotlin/Function1;", "declareConstructorFromLinker", "Lorg/jetbrains/kotlin/ir/declarations/IrConstructor;", "constructorFactory", "declareEnumEntryFromLinker", "Lorg/jetbrains/kotlin/ir/declarations/IrEnumEntry;", "factory", "declareExternalPackageFragment", "declareExternalPackageFragmentIfNotExists", "declareFromLinker", "SymbolOwner", "D", NativeSymbol.CLASS_NAME, "Lorg/jetbrains/kotlin/ir/symbols/IrBindableSymbol;", "Lorg/jetbrains/kotlin/ir/declarations/IrSymbolOwner;", "declareBySignature", "Lkotlin/Function4;", "Lkotlin/Function0;", "Lorg/jetbrains/kotlin/ir/util/OwnerFactory;", "Lkotlin/ExtensionFunctionType;", "declareByDescriptor", "Lkotlin/Function2;", "symbolFactory", "Lorg/jetbrains/kotlin/ir/util/SymbolFactory;", "ownerFactory", "(Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;Lorg/jetbrains/kotlin/ir/util/IdSignature;Lkotlin/jvm/functions/Function4;Lkotlin/jvm/functions/Function2;Lkotlin/jvm/functions/Function2;Lkotlin/jvm/functions/Function1;)Lorg/jetbrains/kotlin/ir/declarations/IrSymbolOwner;", "declareLocalDelegatedProperty", ModuleXmlParser.TYPE, "Lorg/jetbrains/kotlin/ir/types/IrType;", "declarePropertyFromLinker", "Lorg/jetbrains/kotlin/ir/declarations/IrProperty;", "propertyFactory", "declareSimpleFunctionFromLinker", "Lorg/jetbrains/kotlin/ir/declarations/IrSimpleFunction;", "functionFactory", "declareValueParameter", "varargElementType", "name", "Lorg/jetbrains/kotlin/name/Name;", "index", "isAssignable", "", "valueParameterFactory", "(IILorg/jetbrains/kotlin/ir/declarations/IrDeclarationOrigin;Lorg/jetbrains/kotlin/descriptors/ParameterDescriptor;Lorg/jetbrains/kotlin/ir/types/IrType;Lorg/jetbrains/kotlin/ir/types/IrType;Lorg/jetbrains/kotlin/name/Name;Ljava/lang/Integer;ZLkotlin/jvm/functions/Function1;)Lorg/jetbrains/kotlin/ir/declarations/IrValueParameter;", "declareVariable", "irInitializerExpression", "Lorg/jetbrains/kotlin/ir/expressions/IrExpression;", "defaultEnumEntryFactory", "enumEntry", NativeSymbol.TYPE_NAME, "defaultFieldFactory", "Lorg/jetbrains/kotlin/ir/declarations/IrField;", "visibility", "Lorg/jetbrains/kotlin/descriptors/DescriptorVisibility;", "defaultPropertyFactory", "isDelegated", "defaultScriptFactory", "Lorg/jetbrains/kotlin/ir/declarations/IrScript;", "script", "defaultTypeParameterFactory", "Lorg/jetbrains/kotlin/ir/declarations/IrTypeParameter;", "introduceValueParameter", "", "irValueParameter", "referenceClass", "referenceExternalPackageFragment", "referenceLocalDelegatedProperty", "referenceValue", "Lorg/jetbrains/kotlin/ir/symbols/IrValueSymbol;", "value", "Lorg/jetbrains/kotlin/descriptors/ValueDescriptor;", "referenceValueParameter", "initializeScopedSlices", "", "ir.tree"}, k = 1, mv = {1, 9, 0}, xi = 48)
@ObsoleteDescriptorBasedAPI
public class DescriptorSymbolTableExtension extends SymbolTableExtension<DeclarationDescriptor, ClassDescriptor, TypeAliasDescriptor, ScriptDescriptor, FunctionDescriptor, ClassConstructorDescriptor, PropertyDescriptor, ParameterDescriptor, TypeParameterDescriptor> {
    static final /* synthetic */ KProperty<Object>[] $$delegatedProperties = {Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinClass(DescriptorSymbolTableExtension.class), "valueParameterSlice", "getValueParameterSlice()Lorg/jetbrains/kotlin/ir/util/SymbolTableSlice$Scoped;")), Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinClass(DescriptorSymbolTableExtension.class), "variableSlice", "getVariableSlice()Lorg/jetbrains/kotlin/ir/util/SymbolTableSlice$Scoped;")), Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinClass(DescriptorSymbolTableExtension.class), "localDelegatedPropertySlice", "getLocalDelegatedPropertySlice()Lorg/jetbrains/kotlin/ir/util/SymbolTableSlice$Scoped;"))};
    private final SymbolTableSlice<PackageFragmentDescriptor, IrExternalPackageFragment, IrExternalPackageFragmentSymbol> externalPackageFragmentSlice;

    /* JADX INFO: renamed from: localDelegatedPropertySlice$delegate, reason: from kotlin metadata */
    private final ReadWriteProperty localDelegatedPropertySlice;

    /* JADX INFO: renamed from: valueParameterSlice$delegate, reason: from kotlin metadata */
    private final ReadWriteProperty valueParameterSlice;

    /* JADX INFO: renamed from: variableSlice$delegate, reason: from kotlin metadata */
    private final ReadWriteProperty variableSlice;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    /* JADX WARN: Multi-variable type inference failed */
    public DescriptorSymbolTableExtension(SymbolTable table) {
        super(table);
        Intrinsics.checkNotNullParameter(table, "table");
        this.externalPackageFragmentSlice = new SymbolTableSlice.Flat(getLock(), null, 2, 0 == true ? 1 : 0);
        this.valueParameterSlice = ThreadLocalKt.threadLocal(new Function0<SymbolTableSlice.Scoped<ParameterDescriptor, IrValueParameter, IrValueParameterSymbol>>() { // from class: org.jetbrains.kotlin.ir.util.DescriptorSymbolTableExtension$valueParameterSlice$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final SymbolTableSlice.Scoped<ParameterDescriptor, IrValueParameter, IrValueParameterSymbol> invoke() {
                return new SymbolTableSlice.Scoped<>(this.this$0.getLock());
            }
        });
        this.variableSlice = ThreadLocalKt.threadLocal(new Function0<SymbolTableSlice.Scoped<VariableDescriptor, IrVariable, IrVariableSymbol>>() { // from class: org.jetbrains.kotlin.ir.util.DescriptorSymbolTableExtension$variableSlice$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final SymbolTableSlice.Scoped<VariableDescriptor, IrVariable, IrVariableSymbol> invoke() {
                return new SymbolTableSlice.Scoped<>(this.this$0.getLock());
            }
        });
        this.localDelegatedPropertySlice = ThreadLocalKt.threadLocal(new Function0<SymbolTableSlice.Scoped<VariableDescriptorWithAccessors, IrLocalDelegatedProperty, IrLocalDelegatedPropertySymbol>>() { // from class: org.jetbrains.kotlin.ir.util.DescriptorSymbolTableExtension$localDelegatedPropertySlice$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final SymbolTableSlice.Scoped<VariableDescriptorWithAccessors, IrLocalDelegatedProperty, IrLocalDelegatedPropertySymbol> invoke() {
                return new SymbolTableSlice.Scoped<>(this.this$0.getLock());
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final IrFactory getIrFactory() {
        return getTable().getIrFactory();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final NameProvider getNameProvider() {
        return getTable().getNameProvider();
    }

    private final IdSignatureComposer getSignatureComposer() {
        return getTable().getSignaturer();
    }

    private final SymbolTableSlice.Scoped<ParameterDescriptor, IrValueParameter, IrValueParameterSymbol> getValueParameterSlice() {
        return (SymbolTableSlice.Scoped) this.valueParameterSlice.getValue(this, $$delegatedProperties[0]);
    }

    private final SymbolTableSlice.Scoped<VariableDescriptor, IrVariable, IrVariableSymbol> getVariableSlice() {
        return (SymbolTableSlice.Scoped) this.variableSlice.getValue(this, $$delegatedProperties[1]);
    }

    private final SymbolTableSlice.Scoped<VariableDescriptorWithAccessors, IrLocalDelegatedProperty, IrLocalDelegatedPropertySymbol> getLocalDelegatedPropertySlice() {
        return (SymbolTableSlice.Scoped) this.localDelegatedPropertySlice.getValue(this, $$delegatedProperties[2]);
    }

    @Override // org.jetbrains.kotlin.ir.util.SymbolTableExtension
    protected void initializeScopedSlices(List<SymbolTableSlice.Scoped<?, ?, ?>> list) {
        Intrinsics.checkNotNullParameter(list, "<this>");
        list.add(getValueParameterSlice());
        list.add(getVariableSlice());
        list.add(getLocalDelegatedPropertySlice());
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // org.jetbrains.kotlin.ir.util.SymbolTableExtension
    public IdSignature calculateSignature(DeclarationDescriptor declaration) {
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        return getSignatureComposer().composeSignature(declaration);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // org.jetbrains.kotlin.ir.util.SymbolTableExtension
    public IdSignature calculateEnumEntrySignature(ClassDescriptor declaration) {
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        return getSignatureComposer().composeEnumEntrySignature(declaration);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // org.jetbrains.kotlin.ir.util.SymbolTableExtension
    public IdSignature calculateFieldSignature(PropertyDescriptor declaration) {
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        return getSignatureComposer().composeFieldSignature(declaration);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // org.jetbrains.kotlin.ir.util.SymbolTableExtension
    public IrScript defaultScriptFactory(int startOffset, int endOffset, ScriptDescriptor script, IrScriptSymbol symbol) {
        Intrinsics.checkNotNullParameter(script, "script");
        Intrinsics.checkNotNullParameter(symbol, "symbol");
        return new IrScriptImpl(symbol, getNameProvider().nameForDeclaration(script), getIrFactory(), startOffset, endOffset);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // org.jetbrains.kotlin.ir.util.SymbolTableExtension
    public IrScriptSymbol createScriptSymbol(ScriptDescriptor declaration, IdSignature signature) {
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        return new IrScriptSymbolImpl(declaration);
    }

    @Override // org.jetbrains.kotlin.ir.util.SymbolTableExtension, org.jetbrains.kotlin.ir.util.ReferenceSymbolTableExtension
    public IrClassSymbol referenceClass(ClassDescriptor declaration) {
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        if (declaration instanceof IrBasedClassDescriptor) {
            return ((IrBasedClassDescriptor) declaration).getOwner().getSymbol();
        }
        return super.referenceClass(declaration);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // org.jetbrains.kotlin.ir.util.SymbolTableExtension
    public IrClassSymbol createPublicClassSymbol(ClassDescriptor declaration, IdSignature signature) {
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        Intrinsics.checkNotNullParameter(signature, "signature");
        return new IrClassPublicSymbolImpl(signature, declaration);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // org.jetbrains.kotlin.ir.util.SymbolTableExtension
    public IrClassSymbol createPrivateClassSymbol(ClassDescriptor descriptor) {
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        return new IrClassSymbolImpl(descriptor);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // org.jetbrains.kotlin.ir.util.SymbolTableExtension
    public IrConstructorSymbol createPublicConstructorSymbol(ClassConstructorDescriptor declaration, IdSignature signature) {
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        Intrinsics.checkNotNullParameter(signature, "signature");
        return new IrConstructorPublicSymbolImpl(signature, declaration);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // org.jetbrains.kotlin.ir.util.SymbolTableExtension
    public IrConstructorSymbol createPrivateConstructorSymbol(ClassConstructorDescriptor declaration) {
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        return new IrConstructorSymbolImpl(declaration);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // org.jetbrains.kotlin.ir.util.SymbolTableExtension
    public IrEnumEntrySymbol createPublicEnumEntrySymbol(ClassDescriptor declaration, IdSignature signature) {
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        Intrinsics.checkNotNullParameter(signature, "signature");
        return new IrEnumEntryPublicSymbolImpl(signature, declaration);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // org.jetbrains.kotlin.ir.util.SymbolTableExtension
    public IrEnumEntrySymbol createPrivateEnumEntrySymbol(ClassDescriptor declaration) {
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        return new IrEnumEntrySymbolImpl(declaration);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // org.jetbrains.kotlin.ir.util.SymbolTableExtension
    public IrEnumEntry defaultEnumEntryFactory(int startOffset, int endOffset, IrDeclarationOrigin origin, ClassDescriptor enumEntry, IrEnumEntrySymbol symbol) {
        Intrinsics.checkNotNullParameter(origin, "origin");
        Intrinsics.checkNotNullParameter(enumEntry, "enumEntry");
        Intrinsics.checkNotNullParameter(symbol, "symbol");
        return getIrFactory().createEnumEntry(startOffset, endOffset, origin, getNameProvider().nameForDeclaration(enumEntry), symbol);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // org.jetbrains.kotlin.ir.util.SymbolTableExtension
    public IrFieldSymbol createPublicFieldSymbol(PropertyDescriptor declaration, IdSignature signature) {
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        Intrinsics.checkNotNullParameter(signature, "signature");
        return new IrFieldPublicSymbolImpl(signature, declaration);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // org.jetbrains.kotlin.ir.util.SymbolTableExtension
    public IrFieldSymbol createPrivateFieldSymbol(PropertyDescriptor declaration) {
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        return new IrFieldSymbolImpl(declaration);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // org.jetbrains.kotlin.ir.util.SymbolTableExtension
    public IrField defaultFieldFactory(int startOffset, int endOffset, IrDeclarationOrigin origin, PropertyDescriptor declaration, IrType type, DescriptorVisibility visibility, IrFieldSymbol symbol) {
        DescriptorVisibility descriptorVisibility;
        Intrinsics.checkNotNullParameter(origin, "origin");
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        Intrinsics.checkNotNullParameter(type, "type");
        Intrinsics.checkNotNullParameter(symbol, "symbol");
        IrFactory irFactory = getIrFactory();
        Name nameNameForDeclaration = getNameProvider().nameForDeclaration(declaration);
        if (visibility == null) {
            DescriptorVisibility visibility2 = symbol.getDescriptor().getVisibility();
            Intrinsics.checkNotNullExpressionValue(visibility2, "getVisibility(...)");
            descriptorVisibility = visibility2;
        } else {
            descriptorVisibility = visibility;
        }
        IrField irFieldCreateField = irFactory.createField(startOffset, endOffset, origin, nameNameForDeclaration, descriptorVisibility, symbol, type, !symbol.getDescriptor().isVar(), symbol.getDescriptor().mo6753getDispatchReceiverParameter() == null, DescriptorUtilsKt.isEffectivelyExternal(symbol.getDescriptor()));
        irFieldCreateField.setMetadata(new DescriptorMetadataSource.Property(symbol.getDescriptor()));
        return irFieldCreateField;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // org.jetbrains.kotlin.ir.util.SymbolTableExtension
    public IrPropertySymbol createPublicPropertySymbol(PropertyDescriptor declaration, IdSignature signature) {
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        Intrinsics.checkNotNullParameter(signature, "signature");
        return new IrPropertyPublicSymbolImpl(signature, declaration);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // org.jetbrains.kotlin.ir.util.SymbolTableExtension
    public IrPropertySymbol createPrivatePropertySymbol(PropertyDescriptor declaration) {
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        return new IrPropertySymbolImpl(declaration);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // org.jetbrains.kotlin.ir.util.SymbolTableExtension
    public IrProperty defaultPropertyFactory(int startOffset, int endOffset, IrDeclarationOrigin origin, PropertyDescriptor declaration, boolean isDelegated, IrPropertySymbol symbol) {
        Intrinsics.checkNotNullParameter(origin, "origin");
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        Intrinsics.checkNotNullParameter(symbol, "symbol");
        IrFactory irFactory = getIrFactory();
        Name nameNameForDeclaration = getNameProvider().nameForDeclaration(declaration);
        DescriptorVisibility visibility = declaration.getVisibility();
        Intrinsics.checkNotNullExpressionValue(visibility, "getVisibility(...)");
        Modality modality = declaration.getModality();
        Intrinsics.checkNotNullExpressionValue(modality, "getModality(...)");
        IrProperty irPropertyCreateProperty$default = IrFactory.DefaultImpls.createProperty$default(irFactory, startOffset, endOffset, origin, nameNameForDeclaration, visibility, modality, symbol, declaration.isVar(), declaration.isConst(), declaration.isLateInit(), isDelegated, DescriptorUtilsKt.isEffectivelyExternal(declaration), (DeserializedContainerSource) null, declaration.get$isExpect(), declaration.getKind() == CallableMemberDescriptor.Kind.FAKE_OVERRIDE, 4096, (Object) null);
        irPropertyCreateProperty$default.setMetadata(new DescriptorMetadataSource.Property(symbol.getDescriptor()));
        return irPropertyCreateProperty$default;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // org.jetbrains.kotlin.ir.util.SymbolTableExtension
    public IrTypeAliasSymbol createPublicTypeAliasSymbol(TypeAliasDescriptor declaration, IdSignature signature) {
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        Intrinsics.checkNotNullParameter(signature, "signature");
        return new IrTypeAliasPublicSymbolImpl(signature, declaration);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // org.jetbrains.kotlin.ir.util.SymbolTableExtension
    public IrTypeAliasSymbol createPrivateTypeAliasSymbol(TypeAliasDescriptor declaration) {
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        return new IrTypeAliasSymbolImpl(declaration);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // org.jetbrains.kotlin.ir.util.SymbolTableExtension
    public IrSimpleFunctionSymbol createPublicFunctionSymbol(FunctionDescriptor declaration, IdSignature signature) {
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        Intrinsics.checkNotNullParameter(signature, "signature");
        return new IrSimpleFunctionPublicSymbolImpl(signature, declaration);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // org.jetbrains.kotlin.ir.util.SymbolTableExtension
    public IrSimpleFunctionSymbol createPrivateFunctionSymbol(FunctionDescriptor declaration) {
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        return new IrSimpleFunctionSymbolImpl(declaration);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // org.jetbrains.kotlin.ir.util.SymbolTableExtension
    public IrTypeParameterSymbol createPublicTypeParameterSymbol(TypeParameterDescriptor declaration, IdSignature signature) {
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        Intrinsics.checkNotNullParameter(signature, "signature");
        return new IrTypeParameterPublicSymbolImpl(signature, declaration);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // org.jetbrains.kotlin.ir.util.SymbolTableExtension
    public IrTypeParameterSymbol createPrivateTypeParameterSymbol(TypeParameterDescriptor declaration) {
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        return new IrTypeParameterSymbolImpl(declaration);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // org.jetbrains.kotlin.ir.util.SymbolTableExtension
    public IrTypeParameter defaultTypeParameterFactory(int startOffset, int endOffset, IrDeclarationOrigin origin, TypeParameterDescriptor declaration, IrTypeParameterSymbol symbol) {
        Intrinsics.checkNotNullParameter(origin, "origin");
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        Intrinsics.checkNotNullParameter(symbol, "symbol");
        IrFactory irFactory = getIrFactory();
        Name nameNameForDeclaration = getNameProvider().nameForDeclaration(declaration);
        Variance variance = symbol.getDescriptor().getVariance();
        Intrinsics.checkNotNullExpressionValue(variance, "getVariance(...)");
        return irFactory.createTypeParameter(startOffset, endOffset, origin, nameNameForDeclaration, symbol, variance, symbol.getDescriptor().getIndex(), symbol.getDescriptor().isReified());
    }

    public final IrExternalPackageFragmentSymbol referenceExternalPackageFragment(PackageFragmentDescriptor descriptor) {
        IrExternalPackageFragmentSymbolImpl irExternalPackageFragmentSymbolImpl;
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        SymbolTableSlice<PackageFragmentDescriptor, IrExternalPackageFragment, IrExternalPackageFragmentSymbol> symbolTableSlice = this.externalPackageFragmentSlice;
        synchronized (symbolTableSlice.getLock()) {
            irExternalPackageFragmentSymbolImpl = symbolTableSlice.get(descriptor);
            if (irExternalPackageFragmentSymbolImpl == null) {
                irExternalPackageFragmentSymbolImpl = new IrExternalPackageFragmentSymbolImpl(descriptor);
                symbolTableSlice.getUnboundSymbols().add(irExternalPackageFragmentSymbolImpl);
                symbolTableSlice.set(descriptor, irExternalPackageFragmentSymbolImpl);
            }
        }
        return (IrExternalPackageFragmentSymbol) irExternalPackageFragmentSymbolImpl;
    }

    public final IrExternalPackageFragment declareExternalPackageFragment(PackageFragmentDescriptor descriptor) {
        IrExternalPackageFragmentImpl irExternalPackageFragmentImpl;
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        SymbolTableSlice<PackageFragmentDescriptor, IrExternalPackageFragment, IrExternalPackageFragmentSymbol> symbolTableSlice = this.externalPackageFragmentSlice;
        synchronized (symbolTableSlice.getLock()) {
            IrExternalPackageFragmentSymbolImpl irExternalPackageFragmentSymbolImpl = symbolTableSlice.get(descriptor);
            if (irExternalPackageFragmentSymbolImpl == null) {
                IrExternalPackageFragmentSymbolImpl irExternalPackageFragmentSymbolImpl2 = new IrExternalPackageFragmentSymbolImpl(descriptor);
                symbolTableSlice.set(descriptor, irExternalPackageFragmentSymbolImpl2);
                irExternalPackageFragmentSymbolImpl = irExternalPackageFragmentSymbolImpl2;
            } else {
                symbolTableSlice.getUnboundSymbols().remove(irExternalPackageFragmentSymbolImpl);
            }
            irExternalPackageFragmentImpl = new IrExternalPackageFragmentImpl((IrExternalPackageFragmentSymbol) irExternalPackageFragmentSymbolImpl, descriptor.getFqName());
            if (!irExternalPackageFragmentSymbolImpl.isBound()) {
                throw new IllegalArgumentException("Failed requirement.".toString());
            }
            if (irExternalPackageFragmentSymbolImpl.getOwner() != irExternalPackageFragmentImpl) {
                throw new IllegalArgumentException(("Attempt to rebind an IR symbol or to re-create its owner: old owner " + RenderIrElementKt.render$default(irExternalPackageFragmentSymbolImpl.getOwner(), (DumpIrTreeOptions) null, 1, (Object) null) + ", new owner " + RenderIrElementKt.render$default(irExternalPackageFragmentImpl, (DumpIrTreeOptions) null, 1, (Object) null)).toString());
            }
        }
        return irExternalPackageFragmentImpl;
    }

    public final IrExternalPackageFragment declareExternalPackageFragmentIfNotExists(PackageFragmentDescriptor descriptor) {
        IrExternalPackageFragmentImpl owner;
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        SymbolTableSlice<PackageFragmentDescriptor, IrExternalPackageFragment, IrExternalPackageFragmentSymbol> symbolTableSlice = this.externalPackageFragmentSlice;
        synchronized (symbolTableSlice.getLock()) {
            IrExternalPackageFragmentSymbolImpl irExternalPackageFragmentSymbolImpl = symbolTableSlice.get(descriptor);
            if (irExternalPackageFragmentSymbolImpl == null) {
                irExternalPackageFragmentSymbolImpl = new IrExternalPackageFragmentSymbolImpl(descriptor);
                symbolTableSlice.set(descriptor, irExternalPackageFragmentSymbolImpl);
            } else if (irExternalPackageFragmentSymbolImpl.isBound()) {
                symbolTableSlice.getUnboundSymbols().remove(irExternalPackageFragmentSymbolImpl);
                owner = irExternalPackageFragmentSymbolImpl.getOwner();
            }
            owner = new IrExternalPackageFragmentImpl((IrExternalPackageFragmentSymbol) irExternalPackageFragmentSymbolImpl, descriptor.getFqName());
            if (!irExternalPackageFragmentSymbolImpl.isBound()) {
                throw new IllegalArgumentException("Failed requirement.".toString());
            }
            if (irExternalPackageFragmentSymbolImpl.getOwner() != owner) {
                throw new IllegalArgumentException(("Attempt to rebind an IR symbol or to re-create its owner: old owner " + RenderIrElementKt.render$default(irExternalPackageFragmentSymbolImpl.getOwner(), (DumpIrTreeOptions) null, 1, (Object) null) + ", new owner " + RenderIrElementKt.render$default(owner, (DumpIrTreeOptions) null, 1, (Object) null)).toString());
            }
        }
        return (IrExternalPackageFragment) owner;
    }

    public final IrAnonymousInitializer declareAnonymousInitializer(int startOffset, int endOffset, IrDeclarationOrigin origin, ClassDescriptor descriptor) {
        Intrinsics.checkNotNullParameter(origin, "origin");
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        return IrFactory.DefaultImpls.createAnonymousInitializer$default(getIrFactory(), startOffset, endOffset, origin, new IrAnonymousInitializerSymbolImpl(descriptor), false, 16, null);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static /* synthetic */ IrValueParameter declareValueParameter$default(DescriptorSymbolTableExtension descriptorSymbolTableExtension, final int i, final int i2, final IrDeclarationOrigin irDeclarationOrigin, final ParameterDescriptor parameterDescriptor, final IrType irType, IrType irType2, Name name2, Integer num, boolean z, Function1 function1, int i3, Object obj) {
        Function1 function2;
        if (obj != null) {
            throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: declareValueParameter");
        }
        IrType irType3 = (i3 & 32) != 0 ? null : irType2;
        Name name3 = (i3 & 64) != 0 ? null : name2;
        Integer num2 = (i3 & 128) == 0 ? num : null;
        boolean z2 = (i3 & 256) != 0 ? false : z;
        if ((i3 & 512) != 0) {
            final Name name4 = name3;
            final boolean z3 = z2;
            final Integer num3 = num2;
            final IrType irType4 = irType3;
            function2 = new Function1<IrValueParameterSymbol, IrValueParameter>() { // from class: org.jetbrains.kotlin.ir.util.DescriptorSymbolTableExtension.declareValueParameter.1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(1);
                }

                @Override // kotlin.jvm.functions.Function1
                public final IrValueParameter invoke(IrValueParameterSymbol it) {
                    Intrinsics.checkNotNullParameter(it, "it");
                    IrFactory irFactory = DescriptorSymbolTableExtension.this.getIrFactory();
                    int i4 = i;
                    int i5 = i2;
                    IrDeclarationOrigin irDeclarationOrigin2 = irDeclarationOrigin;
                    Name nameNameForDeclaration = name4;
                    if (nameNameForDeclaration == null) {
                        nameNameForDeclaration = DescriptorSymbolTableExtension.this.getNameProvider().nameForDeclaration(parameterDescriptor);
                    }
                    Name name5 = nameNameForDeclaration;
                    IrType irType5 = irType;
                    boolean z4 = z3;
                    Integer num4 = num3;
                    return irFactory.createValueParameter(i4, i5, irDeclarationOrigin2, name5, irType5, z4, it, num4 != null ? num4.intValue() : DescriptorToIrUtilKt.getIndexOrMinusOne(parameterDescriptor), irType4, DescriptorToIrUtilKt.isCrossinline(parameterDescriptor), DescriptorToIrUtilKt.isNoinline(parameterDescriptor), false);
                }
            };
        } else {
            function2 = function1;
        }
        return descriptorSymbolTableExtension.declareValueParameter(i, i2, irDeclarationOrigin, parameterDescriptor, irType, irType3, name3, num2, z2, function2);
    }

    public final IrValueParameter declareValueParameter(int startOffset, int endOffset, IrDeclarationOrigin origin, ParameterDescriptor descriptor, IrType type, IrType varargElementType, Name name2, Integer index, boolean isAssignable, Function1<? super IrValueParameterSymbol, ? extends IrValueParameter> valueParameterFactory) {
        Intrinsics.checkNotNullParameter(origin, "origin");
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        Intrinsics.checkNotNullParameter(type, "type");
        Intrinsics.checkNotNullParameter(valueParameterFactory, "valueParameterFactory");
        SymbolTableSlice.Scoped<ParameterDescriptor, IrValueParameter, IrValueParameterSymbol> valueParameterSlice = getValueParameterSlice();
        SymbolTableSlice.Scoped.SliceScope<ParameterDescriptor, Symbol> currentScope = valueParameterSlice.getCurrentScope();
        if (currentScope == 0) {
            valueParameterSlice.noScope();
            throw null;
        }
        IrValueParameterSymbolImpl irValueParameterSymbolImpl = (IrBindableSymbol) currentScope.getLocal(descriptor);
        if (irValueParameterSymbolImpl == null) {
            irValueParameterSymbolImpl = new IrValueParameterSymbolImpl(descriptor);
            currentScope.set(descriptor, irValueParameterSymbolImpl);
        }
        IrValueParameter irValueParameterInvoke = valueParameterFactory.invoke(irValueParameterSymbolImpl);
        if (!irValueParameterSymbolImpl.isBound()) {
            throw new IllegalArgumentException("Failed requirement.".toString());
        }
        if (irValueParameterSymbolImpl.getOwner() == irValueParameterInvoke) {
            return irValueParameterInvoke;
        }
        throw new IllegalArgumentException(("Attempt to rebind an IR symbol or to re-create its owner: old owner " + RenderIrElementKt.render$default(irValueParameterSymbolImpl.getOwner(), (DumpIrTreeOptions) null, 1, (Object) null) + ", new owner " + RenderIrElementKt.render$default(irValueParameterInvoke, (DumpIrTreeOptions) null, 1, (Object) null)).toString());
    }

    public final void introduceValueParameter(IrValueParameter irValueParameter) {
        Intrinsics.checkNotNullParameter(irValueParameter, "irValueParameter");
        getValueParameterSlice().introduceLocal(irValueParameter.getDescriptor(), irValueParameter.getSymbol());
    }

    @Override // org.jetbrains.kotlin.ir.util.SymbolTableExtension, org.jetbrains.kotlin.ir.util.ReferenceSymbolTableExtension
    public IrValueParameterSymbol referenceValueParameter(ParameterDescriptor declaration) {
        IrValueParameterSymbol irValueParameterSymbol;
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        SymbolTableSlice.Scoped<ParameterDescriptor, IrValueParameter, IrValueParameterSymbol> valueParameterSlice = getValueParameterSlice();
        synchronized (valueParameterSlice.getLock()) {
            irValueParameterSymbol = valueParameterSlice.get(declaration);
            if (irValueParameterSymbol == null) {
                throw new IllegalStateException(("Undefined parameter referenced: " + declaration + '\n' + getValueParameterSlice().dump()).toString());
            }
        }
        return irValueParameterSymbol;
    }

    public IrValueSymbol referenceValue(ValueDescriptor value) {
        IrVariableSymbol irVariableSymbol;
        IrValueParameterSymbol irValueParameterSymbol;
        Intrinsics.checkNotNullParameter(value, "value");
        if (!(value instanceof ParameterDescriptor)) {
            if (!(value instanceof VariableDescriptor)) {
                throw new IllegalStateException(("Unexpected value descriptor: " + value).toString());
            }
            SymbolTableSlice.Scoped<VariableDescriptor, IrVariable, IrVariableSymbol> variableSlice = getVariableSlice();
            synchronized (variableSlice.getLock()) {
                irVariableSymbol = variableSlice.get(value);
                if (irVariableSymbol == null) {
                    throw new IllegalStateException(("Undefined variable referenced: " + value).toString());
                }
            }
            return irVariableSymbol;
        }
        SymbolTableSlice.Scoped<ParameterDescriptor, IrValueParameter, IrValueParameterSymbol> valueParameterSlice = getValueParameterSlice();
        synchronized (valueParameterSlice.getLock()) {
            irValueParameterSymbol = valueParameterSlice.get(value);
            if (irValueParameterSymbol == null) {
                throw new IllegalStateException(("Undefined parameter referenced: " + value).toString());
            }
        }
        return irValueParameterSymbol;
    }

    public final IrVariable declareVariable(int startOffset, int endOffset, IrDeclarationOrigin origin, VariableDescriptor descriptor, IrType type) {
        Intrinsics.checkNotNullParameter(origin, "origin");
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        Intrinsics.checkNotNullParameter(type, "type");
        SymbolTableSlice.Scoped<VariableDescriptor, IrVariable, IrVariableSymbol> variableSlice = getVariableSlice();
        SymbolTableSlice.Scoped.SliceScope<VariableDescriptor, Symbol> currentScope = variableSlice.getCurrentScope();
        if (currentScope == 0) {
            variableSlice.noScope();
            throw null;
        }
        IrVariableSymbolImpl irVariableSymbolImpl = (IrBindableSymbol) currentScope.getLocal(descriptor);
        if (irVariableSymbolImpl == null) {
            irVariableSymbolImpl = new IrVariableSymbolImpl(descriptor);
            currentScope.set(descriptor, irVariableSymbolImpl);
        }
        IrBindableSymbol irBindableSymbol = irVariableSymbolImpl;
        IrVariableImpl irVariableImpl = new IrVariableImpl(startOffset, endOffset, origin, (IrVariableSymbol) irBindableSymbol, getNameProvider().nameForDeclaration(descriptor), type, descriptor.isVar(), descriptor.isConst(), descriptor.isLateInit());
        if (!irBindableSymbol.isBound()) {
            throw new IllegalArgumentException("Failed requirement.".toString());
        }
        if (irBindableSymbol.getOwner() == irVariableImpl) {
            return irVariableImpl;
        }
        throw new IllegalArgumentException(("Attempt to rebind an IR symbol or to re-create its owner: old owner " + RenderIrElementKt.render$default(irBindableSymbol.getOwner(), (DumpIrTreeOptions) null, 1, (Object) null) + ", new owner " + RenderIrElementKt.render$default(irVariableImpl, (DumpIrTreeOptions) null, 1, (Object) null)).toString());
    }

    public final IrVariable declareVariable(int startOffset, int endOffset, IrDeclarationOrigin origin, VariableDescriptor descriptor, IrType type, IrExpression irInitializerExpression) {
        Intrinsics.checkNotNullParameter(origin, "origin");
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        Intrinsics.checkNotNullParameter(type, "type");
        IrVariable irVariableDeclareVariable = declareVariable(startOffset, endOffset, origin, descriptor, type);
        irVariableDeclareVariable.setInitializer(irInitializerExpression);
        return irVariableDeclareVariable;
    }

    public final IrLocalDelegatedProperty declareLocalDelegatedProperty(int startOffset, int endOffset, IrDeclarationOrigin origin, VariableDescriptorWithAccessors descriptor, IrType type) {
        Intrinsics.checkNotNullParameter(origin, "origin");
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        Intrinsics.checkNotNullParameter(type, "type");
        SymbolTableSlice.Scoped<VariableDescriptorWithAccessors, IrLocalDelegatedProperty, IrLocalDelegatedPropertySymbol> localDelegatedPropertySlice = getLocalDelegatedPropertySlice();
        SymbolTableSlice.Scoped.SliceScope<VariableDescriptorWithAccessors, Symbol> currentScope = localDelegatedPropertySlice.getCurrentScope();
        if (currentScope == 0) {
            localDelegatedPropertySlice.noScope();
            throw null;
        }
        IrLocalDelegatedPropertySymbolImpl irLocalDelegatedPropertySymbolImpl = (IrBindableSymbol) currentScope.getLocal(descriptor);
        if (irLocalDelegatedPropertySymbolImpl == null) {
            irLocalDelegatedPropertySymbolImpl = new IrLocalDelegatedPropertySymbolImpl(descriptor);
            currentScope.set(descriptor, irLocalDelegatedPropertySymbolImpl);
        }
        IrBindableSymbol irBindableSymbol = irLocalDelegatedPropertySymbolImpl;
        IrLocalDelegatedProperty irLocalDelegatedPropertyCreateLocalDelegatedProperty = getIrFactory().createLocalDelegatedProperty(startOffset, endOffset, origin, getNameProvider().nameForDeclaration(descriptor), (IrLocalDelegatedPropertySymbol) irBindableSymbol, type, descriptor.isVar());
        if (!irBindableSymbol.isBound()) {
            throw new IllegalArgumentException("Failed requirement.".toString());
        }
        if (irBindableSymbol.getOwner() == irLocalDelegatedPropertyCreateLocalDelegatedProperty) {
            IrLocalDelegatedProperty irLocalDelegatedProperty = irLocalDelegatedPropertyCreateLocalDelegatedProperty;
            irLocalDelegatedProperty.setMetadata(new DescriptorMetadataSource.LocalDelegatedProperty(descriptor));
            return irLocalDelegatedProperty;
        }
        throw new IllegalArgumentException(("Attempt to rebind an IR symbol or to re-create its owner: old owner " + RenderIrElementKt.render$default(irBindableSymbol.getOwner(), (DumpIrTreeOptions) null, 1, (Object) null) + ", new owner " + RenderIrElementKt.render$default(irLocalDelegatedPropertyCreateLocalDelegatedProperty, (DumpIrTreeOptions) null, 1, (Object) null)).toString());
    }

    public final IrLocalDelegatedPropertySymbol referenceLocalDelegatedProperty(VariableDescriptorWithAccessors descriptor) {
        IrLocalDelegatedPropertySymbol irLocalDelegatedPropertySymbol;
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        SymbolTableSlice.Scoped<VariableDescriptorWithAccessors, IrLocalDelegatedProperty, IrLocalDelegatedPropertySymbol> localDelegatedPropertySlice = getLocalDelegatedPropertySlice();
        synchronized (localDelegatedPropertySlice.getLock()) {
            irLocalDelegatedPropertySymbol = localDelegatedPropertySlice.get(descriptor);
            if (irLocalDelegatedPropertySymbol == null) {
                throw new IllegalStateException(("Undefined local delegated property referenced: " + descriptor).toString());
            }
        }
        return irLocalDelegatedPropertySymbol;
    }

    public final IrClass declareClassFromLinker(ClassDescriptor descriptor, final IdSignature signature, Function1<? super IrClassSymbol, ? extends IrClass> classFactory) {
        IrClass irClassDeclareClass;
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        Intrinsics.checkNotNullParameter(signature, "signature");
        Intrinsics.checkNotNullParameter(classFactory, "classFactory");
        if (signature.isPubliclyVisible()) {
            final ClassDescriptor classDescriptor = descriptor;
            irClassDeclareClass = getTable().declareClass(signature, new Function0<IrClassSymbol>() { // from class: org.jetbrains.kotlin.ir.util.DescriptorSymbolTableExtension$declareClassFromLinker$$inlined$declareFromLinker$1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.Function0
                public final IrClassSymbol invoke() {
                    DeclarationDescriptor declarationDescriptor = classDescriptor;
                    IdSignature idSignature = signature;
                    return this.createClassSymbol((ClassDescriptor) declarationDescriptor, idSignature);
                }
            }, classFactory);
        } else {
            irClassDeclareClass = declareClass(descriptor, classFactory);
        }
        return irClassDeclareClass;
    }

    public final IrConstructor declareConstructorFromLinker(ClassConstructorDescriptor descriptor, final IdSignature signature, Function1<? super IrConstructorSymbol, ? extends IrConstructor> constructorFactory) {
        IrConstructor irConstructorDeclareConstructor;
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        Intrinsics.checkNotNullParameter(signature, "signature");
        Intrinsics.checkNotNullParameter(constructorFactory, "constructorFactory");
        if (signature.isPubliclyVisible()) {
            final ClassConstructorDescriptor classConstructorDescriptor = descriptor;
            irConstructorDeclareConstructor = getTable().declareConstructor(signature, new Function0<IrConstructorSymbol>() { // from class: org.jetbrains.kotlin.ir.util.DescriptorSymbolTableExtension$declareConstructorFromLinker$$inlined$declareFromLinker$1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.Function0
                public final IrConstructorSymbol invoke() {
                    DeclarationDescriptor declarationDescriptor = classConstructorDescriptor;
                    IdSignature idSignature = signature;
                    return this.createConstructorSymbol((ClassConstructorDescriptor) declarationDescriptor, idSignature);
                }
            }, constructorFactory);
        } else {
            irConstructorDeclareConstructor = declareConstructor(descriptor, constructorFactory);
        }
        return irConstructorDeclareConstructor;
    }

    public final IrEnumEntry declareEnumEntryFromLinker(ClassDescriptor descriptor, final IdSignature signature, Function1<? super IrEnumEntrySymbol, ? extends IrEnumEntry> factory) {
        IrEnumEntry irEnumEntryDeclareEnumEntry;
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        Intrinsics.checkNotNullParameter(signature, "signature");
        Intrinsics.checkNotNullParameter(factory, "factory");
        if (signature.isPubliclyVisible()) {
            final ClassDescriptor classDescriptor = descriptor;
            irEnumEntryDeclareEnumEntry = getTable().declareEnumEntry(signature, new Function0<IrEnumEntrySymbol>() { // from class: org.jetbrains.kotlin.ir.util.DescriptorSymbolTableExtension$declareEnumEntryFromLinker$$inlined$declareFromLinker$1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.Function0
                public final IrEnumEntrySymbol invoke() {
                    DeclarationDescriptor declarationDescriptor = classDescriptor;
                    IdSignature idSignature = signature;
                    return this.createEnumEntrySymbol((ClassDescriptor) declarationDescriptor, idSignature);
                }
            }, factory);
        } else {
            irEnumEntryDeclareEnumEntry = declareEnumEntry(descriptor, factory);
        }
        return irEnumEntryDeclareEnumEntry;
    }

    public final IrProperty declarePropertyFromLinker(PropertyDescriptor descriptor, final IdSignature signature, Function1<? super IrPropertySymbol, ? extends IrProperty> propertyFactory) {
        IrProperty irPropertyDeclareProperty;
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        Intrinsics.checkNotNullParameter(signature, "signature");
        Intrinsics.checkNotNullParameter(propertyFactory, "propertyFactory");
        if (signature.isPubliclyVisible()) {
            final PropertyDescriptor propertyDescriptor = descriptor;
            irPropertyDeclareProperty = getTable().declareProperty(signature, new Function0<IrPropertySymbol>() { // from class: org.jetbrains.kotlin.ir.util.DescriptorSymbolTableExtension$declarePropertyFromLinker$$inlined$declareFromLinker$1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.Function0
                public final IrPropertySymbol invoke() {
                    DeclarationDescriptor declarationDescriptor = propertyDescriptor;
                    IdSignature idSignature = signature;
                    return this.createPropertySymbol((PropertyDescriptor) declarationDescriptor, idSignature);
                }
            }, propertyFactory);
        } else {
            irPropertyDeclareProperty = declareProperty(descriptor, propertyFactory);
        }
        return irPropertyDeclareProperty;
    }

    public final IrSimpleFunction declareSimpleFunctionFromLinker(FunctionDescriptor descriptor, final IdSignature signature, Function1<? super IrSimpleFunctionSymbol, ? extends IrSimpleFunction> functionFactory) {
        IrSimpleFunction irSimpleFunctionDeclareSimpleFunction;
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        Intrinsics.checkNotNullParameter(signature, "signature");
        Intrinsics.checkNotNullParameter(functionFactory, "functionFactory");
        if (signature.isPubliclyVisible()) {
            final FunctionDescriptor functionDescriptor = descriptor;
            irSimpleFunctionDeclareSimpleFunction = getTable().declareSimpleFunction(signature, new Function0<IrSimpleFunctionSymbol>() { // from class: org.jetbrains.kotlin.ir.util.DescriptorSymbolTableExtension$declareSimpleFunctionFromLinker$$inlined$declareFromLinker$1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.Function0
                public final IrSimpleFunctionSymbol invoke() {
                    DeclarationDescriptor declarationDescriptor = functionDescriptor;
                    IdSignature idSignature = signature;
                    return this.createFunctionSymbol((FunctionDescriptor) declarationDescriptor, idSignature);
                }
            }, functionFactory);
        } else {
            irSimpleFunctionDeclareSimpleFunction = declareSimpleFunction(descriptor, functionFactory);
        }
        return irSimpleFunctionDeclareSimpleFunction;
    }
}
