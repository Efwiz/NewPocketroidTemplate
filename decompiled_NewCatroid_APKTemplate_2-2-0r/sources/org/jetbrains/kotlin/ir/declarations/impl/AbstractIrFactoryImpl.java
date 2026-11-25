package org.jetbrains.kotlin.ir.declarations.impl;

import com.google.firebase.analytics.FirebaseAnalytics;
import java.util.List;
import kotlin.Deprecated;
import kotlin.DeprecationLevel;
import kotlin.Metadata;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
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
import org.jetbrains.kotlin.ir.expressions.IrBlockBody;
import org.jetbrains.kotlin.ir.expressions.IrExpression;
import org.jetbrains.kotlin.ir.expressions.IrExpressionBody;
import org.jetbrains.kotlin.ir.expressions.impl.IrBlockBodyImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrExpressionBodyImpl;
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

/* JADX INFO: compiled from: IrFactoryImpl.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000\u0080\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\b&\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J0\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u00062\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\rH\u0016J\u0018\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u0006H\u0016J\u0088\u0001\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u00062\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\u00152\u0006\u0010\n\u001a\u00020\u00162\u0006\u0010\u0017\u001a\u00020\u00182\u0006\u0010\u0019\u001a\u00020\u001a2\u0006\u0010\u001b\u001a\u00020\r2\u0006\u0010\u001c\u001a\u00020\r2\u0006\u0010\u001d\u001a\u00020\r2\u0006\u0010\u001e\u001a\u00020\r2\u0006\u0010\u001f\u001a\u00020\r2\u0006\u0010 \u001a\u00020\r2\u0006\u0010!\u001a\u00020\r2\u0006\u0010\"\u001a\u00020#H\u0016Jj\u0010$\u001a\u00020%2\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u00062\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\u00152\u0006\u0010&\u001a\u00020\r2\u0006\u0010 \u001a\u00020\r2\u0006\u0010'\u001a\u00020(2\u0006\u0010\n\u001a\u00020)2\u0006\u0010*\u001a\u00020\r2\u0006\u0010\u001b\u001a\u00020\r2\b\u0010+\u001a\u0004\u0018\u00010,H\u0016J0\u0010-\u001a\u00020.2\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u00062\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\u0012\u001a\u00020\u00132\u0006\u0010\n\u001a\u00020/H\u0016J\"\u00100\u001a\u0002012\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u00062\b\u00102\u001a\u0004\u0018\u000103H\u0016J \u00104\u001a\u0002052\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u00062\u0006\u00106\u001a\u000207H\u0016JX\u00108\u001a\u0002092\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u00062\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\u00152\u0006\u0010\n\u001a\u00020:2\u0006\u0010;\u001a\u00020(2\u0006\u0010<\u001a\u00020\r2\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\u001b\u001a\u00020\rH\u0016J\u0080\u0001\u0010=\u001a\u00020>2\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u00062\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\u00152\u0006\u0010&\u001a\u00020\r2\u0006\u0010 \u001a\u00020\r2\u0006\u0010'\u001a\u00020(2\u0006\u0010\u0019\u001a\u00020\u001a2\u0006\u0010?\u001a\u00020\r2\u0006\u0010@\u001a\u00020\r2\u0006\u0010A\u001a\u00020\r2\u0006\u0010B\u001a\u00020\r2\u0006\u0010\u001b\u001a\u00020\r2\u0006\u0010C\u001a\u00020\rH\u0016J@\u0010D\u001a\u00020E2\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u00062\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\u0012\u001a\u00020\u00132\u0006\u0010\n\u001a\u00020F2\u0006\u0010;\u001a\u00020(2\u0006\u0010G\u001a\u00020\rH\u0016J\u0082\u0001\u0010H\u001a\u00020I2\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u00062\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\u00152\u0006\u0010\u0019\u001a\u00020\u001a2\u0006\u0010\n\u001a\u00020J2\u0006\u0010G\u001a\u00020\r2\u0006\u0010K\u001a\u00020\r2\u0006\u0010L\u001a\u00020\r2\u0006\u0010M\u001a\u00020\r2\u0006\u0010\u001b\u001a\u00020\r2\b\u0010+\u001a\u0004\u0018\u00010,2\u0006\u0010 \u001a\u00020\r2\u0006\u0010C\u001a\u00020\rH\u0016Jp\u0010N\u001a\u00020O2\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u00062\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\u00152\u0006\u0010\u0019\u001a\u00020\u001a2\u0006\u0010G\u001a\u00020\r2\u0006\u0010K\u001a\u00020\r2\u0006\u0010L\u001a\u00020\r2\u0006\u0010M\u001a\u00020\r2\u0006\u0010\u001b\u001a\u00020\r2\u0006\u0010 \u001a\u00020\r2\u0006\u0010C\u001a\u00020\rH\u0016J\u0092\u0001\u0010P\u001a\u00020Q2\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u00062\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\u00152\u0006\u0010&\u001a\u00020\r2\u0006\u0010 \u001a\u00020\r2\u0006\u0010'\u001a\u00020(2\u0006\u0010\u0019\u001a\u00020\u001a2\u0006\u0010\n\u001a\u00020R2\u0006\u0010?\u001a\u00020\r2\u0006\u0010@\u001a\u00020\r2\u0006\u0010A\u001a\u00020\r2\u0006\u0010B\u001a\u00020\r2\u0006\u0010\u001b\u001a\u00020\r2\b\u0010+\u001a\u0004\u0018\u00010,2\u0006\u0010C\u001a\u00020\rH\u0016JH\u0010S\u001a\u00020T2\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u00062\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\u00152\u0006\u0010\n\u001a\u00020U2\u0006\u0010V\u001a\u00020\r2\u0006\u0010W\u001a\u00020(H\u0016JH\u0010X\u001a\u00020Y2\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u00062\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\u0012\u001a\u00020\u00132\u0006\u0010\n\u001a\u00020Z2\u0006\u0010[\u001a\u00020\\2\u0006\u0010]\u001a\u00020\u00062\u0006\u0010^\u001a\u00020\rH\u0016Jj\u0010_\u001a\u00020`2\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u00062\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\u0012\u001a\u00020\u00132\u0006\u0010;\u001a\u00020(2\u0006\u0010a\u001a\u00020\r2\u0006\u0010\n\u001a\u00020b2\u0006\u0010]\u001a\u00020\u00062\b\u0010c\u001a\u0004\u0018\u00010(2\u0006\u0010d\u001a\u00020\r2\u0006\u0010e\u001a\u00020\r2\u0006\u0010f\u001a\u00020\rH\u0016¨\u0006g"}, d2 = {"Lorg/jetbrains/kotlin/ir/declarations/impl/AbstractIrFactoryImpl;", "Lorg/jetbrains/kotlin/ir/declarations/IrFactory;", "()V", "createAnonymousInitializer", "Lorg/jetbrains/kotlin/ir/declarations/IrAnonymousInitializer;", "startOffset", "", "endOffset", "origin", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationOrigin;", NativeSymbol.TYPE_NAME, "Lorg/jetbrains/kotlin/ir/symbols/IrAnonymousInitializerSymbol;", "isStatic", "", "createBlockBody", "Lorg/jetbrains/kotlin/ir/expressions/IrBlockBody;", "createClass", "Lorg/jetbrains/kotlin/ir/declarations/IrClass;", "name", "Lorg/jetbrains/kotlin/name/Name;", "visibility", "Lorg/jetbrains/kotlin/descriptors/DescriptorVisibility;", "Lorg/jetbrains/kotlin/ir/symbols/IrClassSymbol;", Namer.METADATA_CLASS_KIND, "Lorg/jetbrains/kotlin/descriptors/ClassKind;", "modality", "Lorg/jetbrains/kotlin/descriptors/Modality;", "isExternal", "isCompanion", "isInner", "isData", "isValue", "isExpect", "isFun", FirebaseAnalytics.Param.SOURCE, "Lorg/jetbrains/kotlin/descriptors/SourceElement;", "createConstructor", "Lorg/jetbrains/kotlin/ir/declarations/IrConstructor;", "isInline", "returnType", "Lorg/jetbrains/kotlin/ir/types/IrType;", "Lorg/jetbrains/kotlin/ir/symbols/IrConstructorSymbol;", "isPrimary", "containerSource", "Lorg/jetbrains/kotlin/serialization/deserialization/descriptors/DeserializedContainerSource;", "createEnumEntry", "Lorg/jetbrains/kotlin/ir/declarations/IrEnumEntry;", "Lorg/jetbrains/kotlin/ir/symbols/IrEnumEntrySymbol;", "createErrorDeclaration", "Lorg/jetbrains/kotlin/ir/declarations/IrErrorDeclaration;", "descriptor", "Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;", "createExpressionBody", "Lorg/jetbrains/kotlin/ir/expressions/IrExpressionBody;", "expression", "Lorg/jetbrains/kotlin/ir/expressions/IrExpression;", "createField", "Lorg/jetbrains/kotlin/ir/declarations/IrField;", "Lorg/jetbrains/kotlin/ir/symbols/IrFieldSymbol;", ModuleXmlParser.TYPE, "isFinal", "createFunctionWithLateBinding", "Lorg/jetbrains/kotlin/ir/declarations/IrFunctionWithLateBinding;", "isTailrec", "isSuspend", "isOperator", "isInfix", "isFakeOverride", "createLocalDelegatedProperty", "Lorg/jetbrains/kotlin/ir/declarations/IrLocalDelegatedProperty;", "Lorg/jetbrains/kotlin/ir/symbols/IrLocalDelegatedPropertySymbol;", "isVar", "createProperty", "Lorg/jetbrains/kotlin/ir/declarations/IrProperty;", "Lorg/jetbrains/kotlin/ir/symbols/IrPropertySymbol;", "isConst", "isLateinit", "isDelegated", "createPropertyWithLateBinding", "Lorg/jetbrains/kotlin/ir/declarations/IrPropertyWithLateBinding;", "createSimpleFunction", "Lorg/jetbrains/kotlin/ir/declarations/IrSimpleFunction;", "Lorg/jetbrains/kotlin/ir/symbols/IrSimpleFunctionSymbol;", "createTypeAlias", "Lorg/jetbrains/kotlin/ir/declarations/IrTypeAlias;", "Lorg/jetbrains/kotlin/ir/symbols/IrTypeAliasSymbol;", "isActual", "expandedType", "createTypeParameter", "Lorg/jetbrains/kotlin/ir/declarations/IrTypeParameter;", "Lorg/jetbrains/kotlin/ir/symbols/IrTypeParameterSymbol;", "variance", "Lorg/jetbrains/kotlin/types/Variance;", "index", "isReified", "createValueParameter", "Lorg/jetbrains/kotlin/ir/declarations/IrValueParameter;", "isAssignable", "Lorg/jetbrains/kotlin/ir/symbols/IrValueParameterSymbol;", "varargElementType", "isCrossinline", "isNoinline", "isHidden", "ir.tree"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class AbstractIrFactoryImpl implements IrFactory {
    @Override // org.jetbrains.kotlin.ir.declarations.IrFactory
    @Deprecated(level = DeprecationLevel.HIDDEN, message = "This method was moved to an extension. This variant of the method will be removed when the 2024.2 IntelliJ platform is shipped (see KTIJ-26314).")
    public /* synthetic */ IrBlockBody createBlockBody(int i, int i2, List list) {
        return IrFactory.DefaultImpls.createBlockBody(this, i, i2, list);
    }

    @Override // org.jetbrains.kotlin.ir.declarations.IrFactory
    @Deprecated(level = DeprecationLevel.HIDDEN, message = "This method was moved to an extension. This variant of the method will be removed when the 2024.2 IntelliJ platform is shipped (see KTIJ-26314).")
    public /* synthetic */ IrBlockBody createBlockBody(int i, int i2, Function1 function1) {
        return IrFactory.DefaultImpls.createBlockBody(this, i, i2, function1);
    }

    @Override // org.jetbrains.kotlin.ir.declarations.IrFactory
    @Deprecated(level = DeprecationLevel.HIDDEN, message = "The method's parameters were reordered. This variant of the method will be removed when the 2024.2 IntelliJ platform is shipped (see KTIJ-26314).")
    public /* synthetic */ IrClass createClass(int i, int i2, IrDeclarationOrigin irDeclarationOrigin, IrClassSymbol irClassSymbol, Name name2, ClassKind classKind, DescriptorVisibility descriptorVisibility, Modality modality, boolean z, boolean z2, boolean z3, boolean z4, boolean z5, boolean z6, boolean z7, SourceElement sourceElement) {
        return IrFactory.DefaultImpls.createClass(this, i, i2, irDeclarationOrigin, irClassSymbol, name2, classKind, descriptorVisibility, modality, z, z2, z3, z4, z5, z6, z7, sourceElement);
    }

    @Override // org.jetbrains.kotlin.ir.declarations.IrFactory
    @Deprecated(level = DeprecationLevel.HIDDEN, message = "The method's parameters were reordered. This variant of the method will be removed when the 2024.2 IntelliJ platform is shipped (see KTIJ-26314).")
    public /* synthetic */ IrConstructor createConstructor(int i, int i2, IrDeclarationOrigin irDeclarationOrigin, IrConstructorSymbol irConstructorSymbol, Name name2, DescriptorVisibility descriptorVisibility, IrType irType, boolean z, boolean z2, boolean z3, boolean z4, DeserializedContainerSource deserializedContainerSource) {
        return IrFactory.DefaultImpls.createConstructor(this, i, i2, irDeclarationOrigin, irConstructorSymbol, name2, descriptorVisibility, irType, z, z2, z3, z4, deserializedContainerSource);
    }

    @Override // org.jetbrains.kotlin.ir.declarations.IrFactory
    @Deprecated(level = DeprecationLevel.HIDDEN, message = "The method's parameters were reordered. This variant of the method will be removed when the 2024.2 IntelliJ platform is shipped (see KTIJ-26314).")
    public /* synthetic */ IrEnumEntry createEnumEntry(int i, int i2, IrDeclarationOrigin irDeclarationOrigin, IrEnumEntrySymbol irEnumEntrySymbol, Name name2) {
        return IrFactory.DefaultImpls.createEnumEntry(this, i, i2, irDeclarationOrigin, irEnumEntrySymbol, name2);
    }

    @Override // org.jetbrains.kotlin.ir.declarations.IrFactory
    @Deprecated(level = DeprecationLevel.HIDDEN, message = "This method was moved to an extension. This variant of the method will be removed when the 2024.2 IntelliJ platform is shipped (see KTIJ-26314).")
    public /* synthetic */ IrExpressionBody createExpressionBody(IrExpression irExpression) {
        return IrFactory.DefaultImpls.createExpressionBody(this, irExpression);
    }

    @Override // org.jetbrains.kotlin.ir.declarations.IrFactory
    @Deprecated(level = DeprecationLevel.HIDDEN, message = "The method's parameters were reordered. This variant of the method will be removed when the 2024.2 IntelliJ platform is shipped (see KTIJ-26314).")
    public /* synthetic */ IrField createField(int i, int i2, IrDeclarationOrigin irDeclarationOrigin, IrFieldSymbol irFieldSymbol, Name name2, IrType irType, DescriptorVisibility descriptorVisibility, boolean z, boolean z2, boolean z3) {
        return IrFactory.DefaultImpls.createField(this, i, i2, irDeclarationOrigin, irFieldSymbol, name2, irType, descriptorVisibility, z, z2, z3);
    }

    @Override // org.jetbrains.kotlin.ir.declarations.IrFactory
    @Deprecated(level = DeprecationLevel.HIDDEN, message = "The method has been renamed, and its parameters were reordered. This variant of the method will be removed when the 2024.2 IntelliJ platform is shipped (see KTIJ-26314).")
    public /* synthetic */ IrSimpleFunction createFunction(int i, int i2, IrDeclarationOrigin irDeclarationOrigin, IrSimpleFunctionSymbol irSimpleFunctionSymbol, Name name2, DescriptorVisibility descriptorVisibility, Modality modality, IrType irType, boolean z, boolean z2, boolean z3, boolean z4, boolean z5, boolean z6, boolean z7, boolean z8, DeserializedContainerSource deserializedContainerSource) {
        return IrFactory.DefaultImpls.createFunction(this, i, i2, irDeclarationOrigin, irSimpleFunctionSymbol, name2, descriptorVisibility, modality, irType, z, z2, z3, z4, z5, z6, z7, z8, deserializedContainerSource);
    }

    @Override // org.jetbrains.kotlin.ir.declarations.IrFactory
    @Deprecated(level = DeprecationLevel.HIDDEN, message = "The method's parameters were reordered. This variant of the method will be removed when the 2024.2 IntelliJ platform is shipped (see KTIJ-26314).")
    public /* synthetic */ IrSimpleFunction createFunctionWithLateBinding(int i, int i2, IrDeclarationOrigin irDeclarationOrigin, Name name2, DescriptorVisibility descriptorVisibility, Modality modality, IrType irType, boolean z, boolean z2, boolean z3, boolean z4, boolean z5, boolean z6, boolean z7) {
        return IrFactory.DefaultImpls.createFunctionWithLateBinding(this, i, i2, irDeclarationOrigin, name2, descriptorVisibility, modality, irType, z, z2, z3, z4, z5, z6, z7);
    }

    @Override // org.jetbrains.kotlin.ir.declarations.IrFactory
    @Deprecated(level = DeprecationLevel.HIDDEN, message = "The method's parameters were reordered. This variant of the method will be removed when the 2024.2 IntelliJ platform is shipped (see KTIJ-26314).")
    public /* synthetic */ IrLocalDelegatedProperty createLocalDelegatedProperty(int i, int i2, IrDeclarationOrigin irDeclarationOrigin, IrLocalDelegatedPropertySymbol irLocalDelegatedPropertySymbol, Name name2, IrType irType, boolean z) {
        return IrFactory.DefaultImpls.createLocalDelegatedProperty(this, i, i2, irDeclarationOrigin, irLocalDelegatedPropertySymbol, name2, irType, z);
    }

    @Override // org.jetbrains.kotlin.ir.declarations.IrFactory
    @Deprecated(level = DeprecationLevel.HIDDEN, message = "The method's parameters were reordered. This variant of the method will be removed when the 2024.2 IntelliJ platform is shipped (see KTIJ-26314).")
    public /* synthetic */ IrProperty createProperty(int i, int i2, IrDeclarationOrigin irDeclarationOrigin, IrPropertySymbol irPropertySymbol, Name name2, DescriptorVisibility descriptorVisibility, Modality modality, boolean z, boolean z2, boolean z3, boolean z4, boolean z5, boolean z6, boolean z7, DeserializedContainerSource deserializedContainerSource) {
        return IrFactory.DefaultImpls.createProperty(this, i, i2, irDeclarationOrigin, irPropertySymbol, name2, descriptorVisibility, modality, z, z2, z3, z4, z5, z6, z7, deserializedContainerSource);
    }

    @Override // org.jetbrains.kotlin.ir.declarations.IrFactory
    @Deprecated(level = DeprecationLevel.HIDDEN, message = "The method's parameters were reordered. This variant of the method will be removed when the 2024.2 IntelliJ platform is shipped (see KTIJ-26314).")
    public /* synthetic */ IrProperty createPropertyWithLateBinding(int i, int i2, IrDeclarationOrigin irDeclarationOrigin, Name name2, DescriptorVisibility descriptorVisibility, Modality modality, boolean z, boolean z2, boolean z3, boolean z4, boolean z5, boolean z6) {
        return IrFactory.DefaultImpls.createPropertyWithLateBinding(this, i, i2, irDeclarationOrigin, name2, descriptorVisibility, modality, z, z2, z3, z4, z5, z6);
    }

    @Override // org.jetbrains.kotlin.ir.declarations.IrFactory
    @Deprecated(level = DeprecationLevel.HIDDEN, message = "The method's parameters were reordered. This variant of the method will be removed when the 2024.2 IntelliJ platform is shipped (see KTIJ-26314).")
    public /* synthetic */ IrTypeAlias createTypeAlias(int i, int i2, IrTypeAliasSymbol irTypeAliasSymbol, Name name2, DescriptorVisibility descriptorVisibility, IrType irType, boolean z, IrDeclarationOrigin irDeclarationOrigin) {
        return IrFactory.DefaultImpls.createTypeAlias(this, i, i2, irTypeAliasSymbol, name2, descriptorVisibility, irType, z, irDeclarationOrigin);
    }

    @Override // org.jetbrains.kotlin.ir.declarations.IrFactory
    @Deprecated(level = DeprecationLevel.HIDDEN, message = "The method's parameters were reordered. This variant of the method will be removed when the 2024.2 IntelliJ platform is shipped (see KTIJ-26314).")
    public /* synthetic */ IrTypeParameter createTypeParameter(int i, int i2, IrDeclarationOrigin irDeclarationOrigin, IrTypeParameterSymbol irTypeParameterSymbol, Name name2, int i3, boolean z, Variance variance) {
        return IrFactory.DefaultImpls.createTypeParameter(this, i, i2, irDeclarationOrigin, irTypeParameterSymbol, name2, i3, z, variance);
    }

    @Override // org.jetbrains.kotlin.ir.declarations.IrFactory
    @Deprecated(level = DeprecationLevel.HIDDEN, message = "The method's parameters were reordered. This variant of the method will be removed when the 2024.2 IntelliJ platform is shipped (see KTIJ-26314).")
    public /* synthetic */ IrValueParameter createValueParameter(int i, int i2, IrDeclarationOrigin irDeclarationOrigin, IrValueParameterSymbol irValueParameterSymbol, Name name2, int i3, IrType irType, IrType irType2, boolean z, boolean z2, boolean z3, boolean z4) {
        return IrFactory.DefaultImpls.createValueParameter(this, i, i2, irDeclarationOrigin, irValueParameterSymbol, name2, i3, irType, irType2, z, z2, z3, z4);
    }

    @Override // org.jetbrains.kotlin.ir.declarations.IrFactory
    public IrAnonymousInitializer createAnonymousInitializer(int startOffset, int endOffset, IrDeclarationOrigin origin, IrAnonymousInitializerSymbol symbol, boolean isStatic) {
        Intrinsics.checkNotNullParameter(origin, "origin");
        Intrinsics.checkNotNullParameter(symbol, "symbol");
        return new IrAnonymousInitializerImpl(startOffset, endOffset, origin, symbol, isStatic, this);
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
        return new IrClassImpl(startOffset, endOffset, origin, symbol, name2, kind, visibility, modality, isCompanion, isInner, isData, isExternal, isValue, isExpect, isFun, source, this);
    }

    @Override // org.jetbrains.kotlin.ir.declarations.IrFactory
    public IrConstructor createConstructor(int startOffset, int endOffset, IrDeclarationOrigin origin, Name name2, DescriptorVisibility visibility, boolean isInline, boolean isExpect, IrType returnType, IrConstructorSymbol symbol, boolean isPrimary, boolean isExternal, DeserializedContainerSource containerSource) {
        Intrinsics.checkNotNullParameter(origin, "origin");
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(visibility, "visibility");
        Intrinsics.checkNotNullParameter(returnType, "returnType");
        Intrinsics.checkNotNullParameter(symbol, "symbol");
        return new IrConstructorImpl(startOffset, endOffset, origin, symbol, name2, visibility, returnType, isInline, isExternal, isPrimary, isExpect, containerSource, this);
    }

    @Override // org.jetbrains.kotlin.ir.declarations.IrFactory
    public IrEnumEntry createEnumEntry(int startOffset, int endOffset, IrDeclarationOrigin origin, Name name2, IrEnumEntrySymbol symbol) {
        Intrinsics.checkNotNullParameter(origin, "origin");
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(symbol, "symbol");
        return new IrEnumEntryImpl(startOffset, endOffset, origin, symbol, name2, this);
    }

    @Override // org.jetbrains.kotlin.ir.declarations.IrFactory
    public IrErrorDeclaration createErrorDeclaration(int startOffset, int endOffset, DeclarationDescriptor descriptor) {
        return new IrErrorDeclarationImpl(startOffset, endOffset, descriptor, this);
    }

    @Override // org.jetbrains.kotlin.ir.declarations.IrFactory
    public IrField createField(int startOffset, int endOffset, IrDeclarationOrigin origin, Name name2, DescriptorVisibility visibility, IrFieldSymbol symbol, IrType type, boolean isFinal, boolean isStatic, boolean isExternal) {
        Intrinsics.checkNotNullParameter(origin, "origin");
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(visibility, "visibility");
        Intrinsics.checkNotNullParameter(symbol, "symbol");
        Intrinsics.checkNotNullParameter(type, "type");
        return new IrFieldImpl(startOffset, endOffset, origin, symbol, name2, type, visibility, isFinal, isExternal, isStatic, this);
    }

    @Override // org.jetbrains.kotlin.ir.declarations.IrFactory
    public IrSimpleFunction createSimpleFunction(int startOffset, int endOffset, IrDeclarationOrigin origin, Name name2, DescriptorVisibility visibility, boolean isInline, boolean isExpect, IrType returnType, Modality modality, IrSimpleFunctionSymbol symbol, boolean isTailrec, boolean isSuspend, boolean isOperator, boolean isInfix, boolean isExternal, DeserializedContainerSource containerSource, boolean isFakeOverride) {
        Intrinsics.checkNotNullParameter(origin, "origin");
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(visibility, "visibility");
        Intrinsics.checkNotNullParameter(returnType, "returnType");
        Intrinsics.checkNotNullParameter(modality, "modality");
        Intrinsics.checkNotNullParameter(symbol, "symbol");
        return new IrFunctionImpl(startOffset, endOffset, origin, symbol, name2, visibility, modality, returnType, isInline, isExternal, isTailrec, isSuspend, isOperator, isInfix, isExpect, isFakeOverride, containerSource, this);
    }

    @Override // org.jetbrains.kotlin.ir.declarations.IrFactory
    public IrFunctionWithLateBinding createFunctionWithLateBinding(int startOffset, int endOffset, IrDeclarationOrigin origin, Name name2, DescriptorVisibility visibility, boolean isInline, boolean isExpect, IrType returnType, Modality modality, boolean isTailrec, boolean isSuspend, boolean isOperator, boolean isInfix, boolean isExternal, boolean isFakeOverride) {
        Intrinsics.checkNotNullParameter(origin, "origin");
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(visibility, "visibility");
        Intrinsics.checkNotNullParameter(returnType, "returnType");
        Intrinsics.checkNotNullParameter(modality, "modality");
        return new IrFunctionWithLateBindingImpl(startOffset, endOffset, origin, name2, visibility, modality, returnType, isInline, isExternal, isTailrec, isSuspend, isOperator, isInfix, isExpect, isFakeOverride, this);
    }

    @Override // org.jetbrains.kotlin.ir.declarations.IrFactory
    public IrLocalDelegatedProperty createLocalDelegatedProperty(int startOffset, int endOffset, IrDeclarationOrigin origin, Name name2, IrLocalDelegatedPropertySymbol symbol, IrType type, boolean isVar) {
        Intrinsics.checkNotNullParameter(origin, "origin");
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(symbol, "symbol");
        Intrinsics.checkNotNullParameter(type, "type");
        return new IrLocalDelegatedPropertyImpl(startOffset, endOffset, origin, symbol, name2, type, isVar, this);
    }

    @Override // org.jetbrains.kotlin.ir.declarations.IrFactory
    public IrProperty createProperty(int startOffset, int endOffset, IrDeclarationOrigin origin, Name name2, DescriptorVisibility visibility, Modality modality, IrPropertySymbol symbol, boolean isVar, boolean isConst, boolean isLateinit, boolean isDelegated, boolean isExternal, DeserializedContainerSource containerSource, boolean isExpect, boolean isFakeOverride) {
        Intrinsics.checkNotNullParameter(origin, "origin");
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(visibility, "visibility");
        Intrinsics.checkNotNullParameter(modality, "modality");
        Intrinsics.checkNotNullParameter(symbol, "symbol");
        return new IrPropertyImpl(startOffset, endOffset, origin, symbol, name2, visibility, modality, isVar, isConst, isLateinit, isDelegated, isExternal, isExpect, isFakeOverride, containerSource, this);
    }

    @Override // org.jetbrains.kotlin.ir.declarations.IrFactory
    public IrPropertyWithLateBinding createPropertyWithLateBinding(int startOffset, int endOffset, IrDeclarationOrigin origin, Name name2, DescriptorVisibility visibility, Modality modality, boolean isVar, boolean isConst, boolean isLateinit, boolean isDelegated, boolean isExternal, boolean isExpect, boolean isFakeOverride) {
        Intrinsics.checkNotNullParameter(origin, "origin");
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(visibility, "visibility");
        Intrinsics.checkNotNullParameter(modality, "modality");
        return new IrPropertyWithLateBindingImpl(startOffset, endOffset, origin, name2, visibility, modality, isVar, isConst, isLateinit, isDelegated, isExternal, isExpect, isFakeOverride, this);
    }

    @Override // org.jetbrains.kotlin.ir.declarations.IrFactory
    public IrTypeAlias createTypeAlias(int startOffset, int endOffset, IrDeclarationOrigin origin, Name name2, DescriptorVisibility visibility, IrTypeAliasSymbol symbol, boolean isActual, IrType expandedType) {
        Intrinsics.checkNotNullParameter(origin, "origin");
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(visibility, "visibility");
        Intrinsics.checkNotNullParameter(symbol, "symbol");
        Intrinsics.checkNotNullParameter(expandedType, "expandedType");
        return new IrTypeAliasImpl(startOffset, endOffset, symbol, name2, visibility, expandedType, isActual, origin, this);
    }

    @Override // org.jetbrains.kotlin.ir.declarations.IrFactory
    public IrTypeParameter createTypeParameter(int startOffset, int endOffset, IrDeclarationOrigin origin, Name name2, IrTypeParameterSymbol symbol, Variance variance, int index, boolean isReified) {
        Intrinsics.checkNotNullParameter(origin, "origin");
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(symbol, "symbol");
        Intrinsics.checkNotNullParameter(variance, "variance");
        return new IrTypeParameterImpl(startOffset, endOffset, origin, symbol, name2, index, isReified, variance, this);
    }

    @Override // org.jetbrains.kotlin.ir.declarations.IrFactory
    public IrValueParameter createValueParameter(int startOffset, int endOffset, IrDeclarationOrigin origin, Name name2, IrType type, boolean isAssignable, IrValueParameterSymbol symbol, int index, IrType varargElementType, boolean isCrossinline, boolean isNoinline, boolean isHidden) {
        Intrinsics.checkNotNullParameter(origin, "origin");
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(type, "type");
        Intrinsics.checkNotNullParameter(symbol, "symbol");
        return new IrValueParameterImpl(startOffset, endOffset, origin, symbol, name2, index, type, varargElementType, isCrossinline, isNoinline, isHidden, isAssignable, this);
    }

    @Override // org.jetbrains.kotlin.ir.declarations.IrFactory
    public IrExpressionBody createExpressionBody(int startOffset, int endOffset, IrExpression expression) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        return new IrExpressionBodyImpl(startOffset, endOffset, expression);
    }

    @Override // org.jetbrains.kotlin.ir.declarations.IrFactory
    public IrBlockBody createBlockBody(int startOffset, int endOffset) {
        return new IrBlockBodyImpl(startOffset, endOffset);
    }
}
