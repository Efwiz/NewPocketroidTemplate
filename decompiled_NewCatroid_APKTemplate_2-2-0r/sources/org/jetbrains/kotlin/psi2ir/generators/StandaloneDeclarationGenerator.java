package org.jetbrains.kotlin.psi2ir.generators;

import androidx.constraintlayout.core.motion.utils.TypedValues;
import com.badlogic.gdx.physics.bullet.collision.ContactCallbackEvent;
import com.gaurav.avnc.vnc.XKeySym;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.descriptors.ClassConstructorDescriptor;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.descriptors.DescriptorVisibility;
import org.jetbrains.kotlin.descriptors.FunctionDescriptor;
import org.jetbrains.kotlin.descriptors.Modality;
import org.jetbrains.kotlin.descriptors.ParameterDescriptor;
import org.jetbrains.kotlin.descriptors.PropertyDescriptor;
import org.jetbrains.kotlin.descriptors.ReceiverParameterDescriptor;
import org.jetbrains.kotlin.descriptors.TypeAliasDescriptor;
import org.jetbrains.kotlin.descriptors.TypeParameterDescriptor;
import org.jetbrains.kotlin.descriptors.ValueClassRepresentation;
import org.jetbrains.kotlin.descriptors.ValueParameterDescriptor;
import org.jetbrains.kotlin.ir.IrElement;
import org.jetbrains.kotlin.ir.declarations.DescriptorMetadataSource;
import org.jetbrains.kotlin.ir.declarations.IrClass;
import org.jetbrains.kotlin.ir.declarations.IrConstructor;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationOrigin;
import org.jetbrains.kotlin.ir.declarations.IrEnumEntry;
import org.jetbrains.kotlin.ir.declarations.IrFactory;
import org.jetbrains.kotlin.ir.declarations.IrFunction;
import org.jetbrains.kotlin.ir.declarations.IrProperty;
import org.jetbrains.kotlin.ir.declarations.IrSimpleFunction;
import org.jetbrains.kotlin.ir.declarations.IrTypeAlias;
import org.jetbrains.kotlin.ir.declarations.IrTypeParameter;
import org.jetbrains.kotlin.ir.declarations.IrTypeParametersContainer;
import org.jetbrains.kotlin.ir.declarations.IrValueParameter;
import org.jetbrains.kotlin.ir.expressions.IrExpressionBody;
import org.jetbrains.kotlin.ir.symbols.IrClassSymbol;
import org.jetbrains.kotlin.ir.symbols.IrConstructorSymbol;
import org.jetbrains.kotlin.ir.symbols.IrEnumEntrySymbol;
import org.jetbrains.kotlin.ir.symbols.IrPropertySymbol;
import org.jetbrains.kotlin.ir.symbols.IrSimpleFunctionSymbol;
import org.jetbrains.kotlin.ir.symbols.IrTypeAliasSymbol;
import org.jetbrains.kotlin.ir.types.IrSimpleType;
import org.jetbrains.kotlin.ir.types.IrType;
import org.jetbrains.kotlin.ir.types.impl.IrUninitializedType;
import org.jetbrains.kotlin.ir.util.DescriptorSymbolTableExtension;
import org.jetbrains.kotlin.ir.util.DescriptorToIrUtilKt;
import org.jetbrains.kotlin.ir.util.SymbolTable;
import org.jetbrains.kotlin.ir.util.SymbolTableExtension;
import org.jetbrains.kotlin.ir.util.TypeTranslator;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.psi.KtParameter;
import org.jetbrains.kotlin.psi.KtPureElement;
import org.jetbrains.kotlin.psi.psiUtil.PsiUtilsKt;
import org.jetbrains.kotlin.psi2ir.KotlinUtilsKt;
import org.jetbrains.kotlin.resolve.DescriptorToSourceUtils;
import org.jetbrains.kotlin.resolve.descriptorUtil.DescriptorUtilsKt;
import org.jetbrains.kotlin.serialization.deserialization.descriptors.DeserializedContainerSource;
import org.jetbrains.kotlin.types.KotlinType;
import org.jetbrains.kotlin.types.SimpleType;
import org.mozilla.javascript.NativeSymbol;

/* JADX INFO: compiled from: StandaloneDeclarationGenerator.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000è\u0001\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u001e\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b\u0000\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\"\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000e2\b\u0010\u000f\u001a\u0004\u0018\u00010\u00102\u0006\u0010\u0011\u001a\u00020\u0012H\u0004J.\u0010\u0013\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00020\u00162\u0006\u0010\u0017\u001a\u00020\u00162\u0006\u0010\u0018\u001a\u00020\u00192\u0006\u0010\r\u001a\u00020\u001a2\u0006\u0010\u001b\u001a\u00020\u001cJQ\u0010\u001d\u001a\u00020\u001e2\u0006\u0010\u0015\u001a\u00020\u00162\u0006\u0010\u0017\u001a\u00020\u00162\u0006\u0010\u0018\u001a\u00020\u00192\u0006\u0010\r\u001a\u00020\u001f2\u0006\u0010\u001b\u001a\u00020 2!\b\u0002\u0010!\u001a\u001b\u0012\u0004\u0012\u00020#\u0012\u0004\u0012\u00020\f\u0012\u0006\u0012\u0004\u0018\u00010$0\"¢\u0006\u0002\b%J.\u0010&\u001a\u00020'2\u0006\u0010\u0015\u001a\u00020\u00162\u0006\u0010\u0017\u001a\u00020\u00162\u0006\u0010\u0018\u001a\u00020\u00192\u0006\u0010\r\u001a\u00020\u001a2\u0006\u0010\u001b\u001a\u00020(J\u001e\u0010)\u001a\u00020*2\u0006\u0010+\u001a\u00020,2\f\u0010-\u001a\b\u0012\u0004\u0012\u00020/0.H\u0004J\u001e\u00100\u001a\u00020*2\u0006\u00101\u001a\u0002022\f\u00103\u001a\b\u0012\u0004\u0012\u00020504H\u0004J.\u00106\u001a\u0002072\u0006\u0010\u0015\u001a\u00020\u00162\u0006\u0010\u0017\u001a\u00020\u00162\u0006\u0010\u0018\u001a\u00020\u00192\u0006\u0010\r\u001a\u0002082\u0006\u0010\u001b\u001a\u000209J\u001c\u0010:\u001a\u00020*2\u0006\u0010+\u001a\u00020,2\f\u0010-\u001a\b\u0012\u0004\u0012\u00020/0.JQ\u0010;\u001a\u0002022\u0006\u0010\u0015\u001a\u00020\u00162\u0006\u0010\u0017\u001a\u00020\u00162\u0006\u0010\u0018\u001a\u00020\u00192\u0006\u0010\r\u001a\u0002052\u0006\u0010\u001b\u001a\u00020<2!\b\u0002\u0010!\u001a\u001b\u0012\u0004\u0012\u00020#\u0012\u0004\u0012\u00020\f\u0012\u0006\u0012\u0004\u0018\u00010$0\"¢\u0006\u0002\b%J.\u0010=\u001a\u00020>2\u0006\u0010\u0015\u001a\u00020\u00162\u0006\u0010\u0017\u001a\u00020\u00162\u0006\u0010\u0018\u001a\u00020\u00192\u0006\u0010\r\u001a\u00020?2\u0006\u0010\u001b\u001a\u00020@J>\u0010A\u001a\u00020*2\u0006\u0010+\u001a\u00020,2\f\u0010-\u001a\b\u0012\u0004\u0012\u00020/0.2\u001e\u0010B\u001a\u001a\u0012\u0004\u0012\u00020\u0016\u0012\u0004\u0012\u00020\u0016\u0012\u0004\u0012\u00020/\u0012\u0004\u0012\u00020D0CH\u0002J9\u0010E\u001a\u00020*2\u0006\u00101\u001a\u00020#2\u0006\u0010F\u001a\u0002052\u001f\u0010!\u001a\u001b\u0012\u0004\u0012\u00020#\u0012\u0004\u0012\u00020\f\u0012\u0006\u0012\u0004\u0018\u00010$0\"¢\u0006\u0002\b%H\u0004J\n\u0010G\u001a\u00020H*\u00020IR\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\nX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006J"}, d2 = {"Lorg/jetbrains/kotlin/psi2ir/generators/StandaloneDeclarationGenerator;", "", "context", "Lorg/jetbrains/kotlin/psi2ir/generators/GeneratorContext;", "(Lorg/jetbrains/kotlin/psi2ir/generators/GeneratorContext;)V", "irFactory", "Lorg/jetbrains/kotlin/ir/declarations/IrFactory;", "symbolTable", "Lorg/jetbrains/kotlin/ir/util/SymbolTable;", "typeTranslator", "Lorg/jetbrains/kotlin/ir/util/TypeTranslator;", "declareParameter", "Lorg/jetbrains/kotlin/ir/declarations/IrValueParameter;", "descriptor", "Lorg/jetbrains/kotlin/descriptors/ParameterDescriptor;", "ktElement", "Lorg/jetbrains/kotlin/psi/KtPureElement;", "irOwnerElement", "Lorg/jetbrains/kotlin/ir/IrElement;", "generateClass", "Lorg/jetbrains/kotlin/ir/declarations/IrClass;", "startOffset", "", "endOffset", "origin", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationOrigin;", "Lorg/jetbrains/kotlin/descriptors/ClassDescriptor;", NativeSymbol.TYPE_NAME, "Lorg/jetbrains/kotlin/ir/symbols/IrClassSymbol;", "generateConstructor", "Lorg/jetbrains/kotlin/ir/declarations/IrConstructor;", "Lorg/jetbrains/kotlin/descriptors/ClassConstructorDescriptor;", "Lorg/jetbrains/kotlin/ir/symbols/IrConstructorSymbol;", "defaultArgumentFactory", "Lkotlin/Function2;", "Lorg/jetbrains/kotlin/ir/declarations/IrFunction;", "Lorg/jetbrains/kotlin/ir/expressions/IrExpressionBody;", "Lkotlin/ExtensionFunctionType;", "generateEnumEntry", "Lorg/jetbrains/kotlin/ir/declarations/IrEnumEntry;", "Lorg/jetbrains/kotlin/ir/symbols/IrEnumEntrySymbol;", "generateGlobalTypeParametersDeclarations", "", "irTypeParametersOwner", "Lorg/jetbrains/kotlin/ir/declarations/IrTypeParametersContainer;", TypedValues.TransitionType.S_FROM, "", "Lorg/jetbrains/kotlin/descriptors/TypeParameterDescriptor;", "generateOverridenSymbols", "irFunction", "Lorg/jetbrains/kotlin/ir/declarations/IrSimpleFunction;", "overridens", "", "Lorg/jetbrains/kotlin/descriptors/FunctionDescriptor;", "generateProperty", "Lorg/jetbrains/kotlin/ir/declarations/IrProperty;", "Lorg/jetbrains/kotlin/descriptors/PropertyDescriptor;", "Lorg/jetbrains/kotlin/ir/symbols/IrPropertySymbol;", "generateScopedTypeParameterDeclarations", "generateSimpleFunction", "Lorg/jetbrains/kotlin/ir/symbols/IrSimpleFunctionSymbol;", "generateTypeAlias", "Lorg/jetbrains/kotlin/ir/declarations/IrTypeAlias;", "Lorg/jetbrains/kotlin/descriptors/TypeAliasDescriptor;", "Lorg/jetbrains/kotlin/ir/symbols/IrTypeAliasSymbol;", "generateTypeParameterDeclarations", "declareTypeParameter", "Lkotlin/Function3;", "Lorg/jetbrains/kotlin/ir/declarations/IrTypeParameter;", "generateValueParameterDeclarations", "functionDescriptor", "toIrType", "Lorg/jetbrains/kotlin/ir/types/IrType;", "Lorg/jetbrains/kotlin/types/KotlinType;", "ir.psi2ir"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class StandaloneDeclarationGenerator {
    private final GeneratorContext context;
    private final IrFactory irFactory;
    private final SymbolTable symbolTable;
    private final TypeTranslator typeTranslator;

    public StandaloneDeclarationGenerator(GeneratorContext context) {
        Intrinsics.checkNotNullParameter(context, "context");
        this.context = context;
        this.typeTranslator = context.getTypeTranslator();
        this.symbolTable = context.getSymbolTable();
        this.irFactory = context.getIrFactory();
    }

    public final IrType toIrType(KotlinType kotlinType) {
        Intrinsics.checkNotNullParameter(kotlinType, "<this>");
        return this.typeTranslator.translateType(kotlinType);
    }

    protected final void generateGlobalTypeParametersDeclarations(IrTypeParametersContainer irTypeParametersOwner, List<? extends TypeParameterDescriptor> from) {
        Intrinsics.checkNotNullParameter(irTypeParametersOwner, "irTypeParametersOwner");
        Intrinsics.checkNotNullParameter(from, "from");
        generateTypeParameterDeclarations(irTypeParametersOwner, from, new Function3<Integer, Integer, TypeParameterDescriptor, IrTypeParameter>() { // from class: org.jetbrains.kotlin.psi2ir.generators.StandaloneDeclarationGenerator.generateGlobalTypeParametersDeclarations.1
            {
                super(3);
            }

            @Override // kotlin.jvm.functions.Function3
            public /* bridge */ /* synthetic */ IrTypeParameter invoke(Integer num, Integer num2, TypeParameterDescriptor typeParameterDescriptor) {
                return invoke(num.intValue(), num2.intValue(), typeParameterDescriptor);
            }

            public final IrTypeParameter invoke(int i, int i2, TypeParameterDescriptor typeParameterDescriptor) {
                Intrinsics.checkNotNullParameter(typeParameterDescriptor, "typeParameterDescriptor");
                return StandaloneDeclarationGenerator.this.symbolTable.getDescriptorExtension().declareGlobalTypeParameter(i, i2, IrDeclarationOrigin.DEFINED.INSTANCE, typeParameterDescriptor);
            }
        });
    }

    public final void generateScopedTypeParameterDeclarations(IrTypeParametersContainer irTypeParametersOwner, List<? extends TypeParameterDescriptor> from) {
        Intrinsics.checkNotNullParameter(irTypeParametersOwner, "irTypeParametersOwner");
        Intrinsics.checkNotNullParameter(from, "from");
        generateTypeParameterDeclarations(irTypeParametersOwner, from, new Function3<Integer, Integer, TypeParameterDescriptor, IrTypeParameter>() { // from class: org.jetbrains.kotlin.psi2ir.generators.StandaloneDeclarationGenerator.generateScopedTypeParameterDeclarations.1
            {
                super(3);
            }

            @Override // kotlin.jvm.functions.Function3
            public /* bridge */ /* synthetic */ IrTypeParameter invoke(Integer num, Integer num2, TypeParameterDescriptor typeParameterDescriptor) {
                return invoke(num.intValue(), num2.intValue(), typeParameterDescriptor);
            }

            public final IrTypeParameter invoke(int i, int i2, TypeParameterDescriptor typeParameterDescriptor) {
                Intrinsics.checkNotNullParameter(typeParameterDescriptor, "typeParameterDescriptor");
                return SymbolTableExtension.declareScopedTypeParameter$default(StandaloneDeclarationGenerator.this.symbolTable.getDescriptorExtension(), i, i2, IrDeclarationOrigin.DEFINED.INSTANCE, typeParameterDescriptor, null, 16, null);
            }
        });
    }

    private final void generateTypeParameterDeclarations(IrTypeParametersContainer irTypeParametersOwner, List<? extends TypeParameterDescriptor> from, Function3<? super Integer, ? super Integer, ? super TypeParameterDescriptor, ? extends IrTypeParameter> declareTypeParameter) {
        List<IrTypeParameter> typeParameters = irTypeParametersOwner.getTypeParameters();
        List<? extends TypeParameterDescriptor> list = from;
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(list, 10));
        for (TypeParameterDescriptor typeParameterDescriptor : list) {
            PsiElement sourceFromDescriptor = DescriptorToSourceUtils.getSourceFromDescriptor(typeParameterDescriptor);
            IrTypeParameter irTypeParameterInvoke = declareTypeParameter.invoke(Integer.valueOf(KotlinUtilsKt.getStartOffsetOrUndefined(sourceFromDescriptor)), Integer.valueOf(KotlinUtilsKt.getEndOffsetOrUndefined(sourceFromDescriptor)), typeParameterDescriptor);
            irTypeParameterInvoke.setParent(irTypeParametersOwner);
            arrayList.add(irTypeParameterInvoke);
        }
        irTypeParametersOwner.setTypeParameters(CollectionsKt.plus((Collection) typeParameters, (Iterable) arrayList));
        for (IrTypeParameter irTypeParameter : irTypeParametersOwner.getTypeParameters()) {
            List<KotlinType> upperBounds = irTypeParameter.getDescriptor().getUpperBounds();
            Intrinsics.checkNotNullExpressionValue(upperBounds, "getUpperBounds(...)");
            List<KotlinType> list2 = upperBounds;
            ArrayList arrayList2 = new ArrayList(CollectionsKt.collectionSizeOrDefault(list2, 10));
            for (KotlinType kotlinType : list2) {
                Intrinsics.checkNotNull(kotlinType);
                arrayList2.add(toIrType(kotlinType));
            }
            irTypeParameter.setSuperTypes(arrayList2);
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final IrClass generateClass(int startOffset, int endOffset, IrDeclarationOrigin origin, ClassDescriptor descriptor, IrClassSymbol symbol) {
        Intrinsics.checkNotNullParameter(origin, "origin");
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        Intrinsics.checkNotNullParameter(symbol, "symbol");
        IrClass irClassCreateIrClassFromDescriptor$default = DescriptorToIrUtilKt.createIrClassFromDescriptor$default(this.irFactory, startOffset, endOffset, origin, symbol, descriptor, null, null, null, 224, null);
        SymbolTable symbolTable = this.symbolTable;
        IrClass irClass = irClassCreateIrClassFromDescriptor$default;
        symbolTable.enterScope(irClass);
        irClassCreateIrClassFromDescriptor$default.setMetadata(new DescriptorMetadataSource.Class(descriptor));
        List<TypeParameterDescriptor> declaredTypeParameters = descriptor.getDeclaredTypeParameters();
        Intrinsics.checkNotNullExpressionValue(declaredTypeParameters, "getDeclaredTypeParameters(...)");
        generateGlobalTypeParametersDeclarations(irClassCreateIrClassFromDescriptor$default, declaredTypeParameters);
        Collection<KotlinType> collectionMo7438getSupertypes = descriptor.getTypeConstructor().mo7438getSupertypes();
        Intrinsics.checkNotNullExpressionValue(collectionMo7438getSupertypes, "getSupertypes(...)");
        Collection<KotlinType> collection = collectionMo7438getSupertypes;
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(collection, 10));
        for (KotlinType kotlinType : collection) {
            Intrinsics.checkNotNull(kotlinType);
            arrayList.add(toIrType(kotlinType));
        }
        irClassCreateIrClassFromDescriptor$default.setSuperTypes(arrayList);
        DescriptorSymbolTableExtension descriptorExtension = this.context.getSymbolTable().getDescriptorExtension();
        IrDeclarationOrigin.INSTANCE_RECEIVER instance_receiver = IrDeclarationOrigin.INSTANCE_RECEIVER.INSTANCE;
        ReceiverParameterDescriptor thisAsReceiverParameter = descriptor.getThisAsReceiverParameter();
        Intrinsics.checkNotNullExpressionValue(thisAsReceiverParameter, "getThisAsReceiverParameter(...)");
        ReceiverParameterDescriptor receiverParameterDescriptor = thisAsReceiverParameter;
        KotlinType type = descriptor.getThisAsReceiverParameter().getType();
        Intrinsics.checkNotNullExpressionValue(type, "getType(...)");
        IrValueParameter irValueParameterDeclareValueParameter$default = DescriptorSymbolTableExtension.declareValueParameter$default(descriptorExtension, startOffset, endOffset, instance_receiver, receiverParameterDescriptor, toIrType(type), null, null, null, false, null, XKeySym.XK_amacron, null);
        irValueParameterDeclareValueParameter$default.setParent(irClassCreateIrClassFromDescriptor$default);
        irClassCreateIrClassFromDescriptor$default.setThisReceiver(irValueParameterDeclareValueParameter$default);
        ValueClassRepresentation<SimpleType> valueClassRepresentation = descriptor.getValueClassRepresentation();
        irClassCreateIrClassFromDescriptor$default.setValueClassRepresentation(valueClassRepresentation != null ? valueClassRepresentation.mapUnderlyingType(new Function1<SimpleType, IrSimpleType>() { // from class: org.jetbrains.kotlin.psi2ir.generators.StandaloneDeclarationGenerator$generateClass$1$3
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final IrSimpleType invoke(SimpleType simpleType) {
                StandaloneDeclarationGenerator standaloneDeclarationGenerator = this.this$0;
                Intrinsics.checkNotNull(simpleType);
                IrType irType = standaloneDeclarationGenerator.toIrType(simpleType);
                Intrinsics.checkNotNull(irType, "null cannot be cast to non-null type org.jetbrains.kotlin.ir.types.IrSimpleType");
                return (IrSimpleType) irType;
            }
        }) : null);
        Unit unit = Unit.INSTANCE;
        symbolTable.leaveScope(irClass);
        return irClassCreateIrClassFromDescriptor$default;
    }

    public final IrEnumEntry generateEnumEntry(int startOffset, int endOffset, IrDeclarationOrigin origin, ClassDescriptor descriptor, IrEnumEntrySymbol symbol) {
        Intrinsics.checkNotNullParameter(origin, "origin");
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        Intrinsics.checkNotNullParameter(symbol, "symbol");
        IrFactory irFactory = this.irFactory;
        Name name2 = descriptor.getShortName();
        Intrinsics.checkNotNullExpressionValue(name2, "getName(...)");
        return irFactory.createEnumEntry(startOffset, endOffset, origin, name2, symbol);
    }

    public final IrTypeAlias generateTypeAlias(int startOffset, int endOffset, IrDeclarationOrigin origin, TypeAliasDescriptor descriptor, IrTypeAliasSymbol symbol) {
        Intrinsics.checkNotNullParameter(origin, "origin");
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        Intrinsics.checkNotNullParameter(symbol, "symbol");
        IrFactory irFactory = this.irFactory;
        Name name2 = descriptor.getShortName();
        Intrinsics.checkNotNullExpressionValue(name2, "getName(...)");
        DescriptorVisibility visibility = descriptor.getVisibility();
        Intrinsics.checkNotNullExpressionValue(visibility, "getVisibility(...)");
        IrTypeAlias irTypeAliasCreateTypeAlias = irFactory.createTypeAlias(startOffset, endOffset, origin, name2, visibility, symbol, descriptor.getIsActual(), toIrType(descriptor.getExpandedType()));
        List<TypeParameterDescriptor> declaredTypeParameters = descriptor.getDeclaredTypeParameters();
        Intrinsics.checkNotNullExpressionValue(declaredTypeParameters, "getDeclaredTypeParameters(...)");
        generateGlobalTypeParametersDeclarations(irTypeAliasCreateTypeAlias, declaredTypeParameters);
        return irTypeAliasCreateTypeAlias;
    }

    protected final IrValueParameter declareParameter(ParameterDescriptor descriptor, KtPureElement ktElement, IrElement irOwnerElement) {
        KotlinType varargElementType;
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        Intrinsics.checkNotNullParameter(irOwnerElement, "irOwnerElement");
        DescriptorSymbolTableExtension descriptorExtension = this.symbolTable.getDescriptorExtension();
        int pureStartOffset = ktElement != null ? PsiUtilsKt.getPureStartOffset(ktElement) : irOwnerElement.getStartOffset();
        int pureEndOffset = ktElement != null ? PsiUtilsKt.getPureEndOffset(ktElement) : irOwnerElement.getEndOffset();
        IrDeclarationOrigin.DEFINED defined = IrDeclarationOrigin.DEFINED.INSTANCE;
        KotlinType type = descriptor.getType();
        Intrinsics.checkNotNullExpressionValue(type, "getType(...)");
        IrType irType = toIrType(type);
        IrType irType2 = null;
        ValueParameterDescriptor valueParameterDescriptor = descriptor instanceof ValueParameterDescriptor ? (ValueParameterDescriptor) descriptor : null;
        if (valueParameterDescriptor != null && (varargElementType = valueParameterDescriptor.getVarargElementType()) != null) {
            irType2 = toIrType(varargElementType);
        }
        return DescriptorSymbolTableExtension.declareValueParameter$default(descriptorExtension, pureStartOffset, pureEndOffset, defined, descriptor, irType, irType2, null, null, false, null, XKeySym.XK_Amacron, null);
    }

    protected final void generateValueParameterDeclarations(IrFunction irFunction, FunctionDescriptor functionDescriptor, Function2<? super IrFunction, ? super IrValueParameter, ? extends IrExpressionBody> defaultArgumentFactory) {
        Intrinsics.checkNotNullParameter(irFunction, "irFunction");
        Intrinsics.checkNotNullParameter(functionDescriptor, "functionDescriptor");
        Intrinsics.checkNotNullParameter(defaultArgumentFactory, "defaultArgumentFactory");
        ReceiverParameterDescriptor dispatchReceiverParameter = functionDescriptor.mo6753getDispatchReceiverParameter();
        irFunction.setDispatchReceiverParameter(dispatchReceiverParameter != null ? declareParameter(dispatchReceiverParameter, null, irFunction) : null);
        ReceiverParameterDescriptor extensionReceiverParameter = functionDescriptor.getExtensionReceiverParameter();
        irFunction.setExtensionReceiverParameter(extensionReceiverParameter != null ? declareParameter(extensionReceiverParameter, null, irFunction) : null);
        List<ValueParameterDescriptor> valueParameters = functionDescriptor.getValueParameters();
        Intrinsics.checkNotNullExpressionValue(valueParameters, "getValueParameters(...)");
        List<ValueParameterDescriptor> list = valueParameters;
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(list, 10));
        for (ValueParameterDescriptor valueParameterDescriptor : list) {
            Intrinsics.checkNotNull(valueParameterDescriptor);
            PsiElement sourceFromDescriptor = DescriptorToSourceUtils.getSourceFromDescriptor(valueParameterDescriptor);
            IrValueParameter irValueParameterDeclareParameter = declareParameter(valueParameterDescriptor, sourceFromDescriptor instanceof KtParameter ? (KtParameter) sourceFromDescriptor : null, irFunction);
            irValueParameterDeclareParameter.setDefaultValue(defaultArgumentFactory.invoke(irFunction, irValueParameterDeclareParameter));
            arrayList.add(irValueParameterDeclareParameter);
        }
        irFunction.setValueParameters(arrayList);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static /* synthetic */ IrConstructor generateConstructor$default(StandaloneDeclarationGenerator standaloneDeclarationGenerator, int i, int i2, IrDeclarationOrigin irDeclarationOrigin, ClassConstructorDescriptor classConstructorDescriptor, IrConstructorSymbol irConstructorSymbol, Function2 function2, int i3, Object obj) {
        if ((i3 & 32) != 0) {
            function2 = new Function2() { // from class: org.jetbrains.kotlin.psi2ir.generators.StandaloneDeclarationGenerator.generateConstructor.1
                @Override // kotlin.jvm.functions.Function2
                public final Void invoke(IrFunction irFunction, IrValueParameter it) {
                    Intrinsics.checkNotNullParameter(irFunction, "$this$null");
                    Intrinsics.checkNotNullParameter(it, "it");
                    return null;
                }
            };
        }
        return standaloneDeclarationGenerator.generateConstructor(i, i2, irDeclarationOrigin, classConstructorDescriptor, irConstructorSymbol, function2);
    }

    public final IrConstructor generateConstructor(int startOffset, int endOffset, IrDeclarationOrigin origin, ClassConstructorDescriptor descriptor, IrConstructorSymbol symbol, Function2<? super IrFunction, ? super IrValueParameter, ? extends IrExpressionBody> defaultArgumentFactory) {
        Intrinsics.checkNotNullParameter(origin, "origin");
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        Intrinsics.checkNotNullParameter(symbol, "symbol");
        Intrinsics.checkNotNullParameter(defaultArgumentFactory, "defaultArgumentFactory");
        IrFactory irFactory = this.irFactory;
        Name name2 = descriptor.getShortName();
        Intrinsics.checkNotNullExpressionValue(name2, "getName(...)");
        DescriptorVisibility visibility = descriptor.getVisibility();
        Intrinsics.checkNotNullExpressionValue(visibility, "getVisibility(...)");
        IrConstructor irConstructorCreateConstructor$default = IrFactory.DefaultImpls.createConstructor$default(irFactory, startOffset, endOffset, origin, name2, visibility, descriptor.isInline(), descriptor.get$isExpect(), IrUninitializedType.INSTANCE, symbol, descriptor.isPrimary(), DescriptorUtilsKt.isEffectivelyExternal(descriptor), (DeserializedContainerSource) null, 2048, (Object) null);
        ClassConstructorDescriptor classConstructorDescriptor = descriptor;
        irConstructorCreateConstructor$default.setMetadata(new DescriptorMetadataSource.Function(classConstructorDescriptor));
        SymbolTable symbolTable = this.symbolTable;
        IrConstructor irConstructor = irConstructorCreateConstructor$default;
        symbolTable.enterScope(irConstructor);
        List<TypeParameterDescriptor> typeParameters = descriptor.getTypeParameters();
        Intrinsics.checkNotNullExpressionValue(typeParameters, "getTypeParameters(...)");
        ArrayList arrayList = new ArrayList();
        for (Object obj : typeParameters) {
            if (((TypeParameterDescriptor) obj).getContainingModule() == descriptor) {
                arrayList.add(obj);
            }
        }
        generateScopedTypeParameterDeclarations(irConstructorCreateConstructor$default, arrayList);
        generateValueParameterDeclarations(irConstructorCreateConstructor$default, classConstructorDescriptor, defaultArgumentFactory);
        KotlinType returnType = descriptor.getReturnType();
        Intrinsics.checkNotNullExpressionValue(returnType, "getReturnType(...)");
        irConstructorCreateConstructor$default.setReturnType(toIrType(returnType));
        Unit unit = Unit.INSTANCE;
        symbolTable.leaveScope(irConstructor);
        return irConstructorCreateConstructor$default;
    }

    protected final void generateOverridenSymbols(IrSimpleFunction irFunction, Collection<? extends FunctionDescriptor> overridens) {
        Intrinsics.checkNotNullParameter(irFunction, "irFunction");
        Intrinsics.checkNotNullParameter(overridens, "overridens");
        Collection<? extends FunctionDescriptor> collection = overridens;
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(collection, 10));
        for (FunctionDescriptor functionDescriptor : collection) {
            DescriptorSymbolTableExtension descriptorExtension = this.symbolTable.getDescriptorExtension();
            FunctionDescriptor original = functionDescriptor.getOriginal();
            Intrinsics.checkNotNullExpressionValue(original, "getOriginal(...)");
            arrayList.add(descriptorExtension.referenceSimpleFunction(original));
        }
        irFunction.setOverriddenSymbols(arrayList);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static /* synthetic */ IrSimpleFunction generateSimpleFunction$default(StandaloneDeclarationGenerator standaloneDeclarationGenerator, int i, int i2, IrDeclarationOrigin irDeclarationOrigin, FunctionDescriptor functionDescriptor, IrSimpleFunctionSymbol irSimpleFunctionSymbol, Function2 function2, int i3, Object obj) {
        if ((i3 & 32) != 0) {
            function2 = new Function2() { // from class: org.jetbrains.kotlin.psi2ir.generators.StandaloneDeclarationGenerator.generateSimpleFunction.1
                @Override // kotlin.jvm.functions.Function2
                public final Void invoke(IrFunction irFunction, IrValueParameter it) {
                    Intrinsics.checkNotNullParameter(irFunction, "$this$null");
                    Intrinsics.checkNotNullParameter(it, "it");
                    return null;
                }
            };
        }
        return standaloneDeclarationGenerator.generateSimpleFunction(i, i2, irDeclarationOrigin, functionDescriptor, irSimpleFunctionSymbol, function2);
    }

    public final IrSimpleFunction generateSimpleFunction(int startOffset, int endOffset, IrDeclarationOrigin origin, FunctionDescriptor descriptor, IrSimpleFunctionSymbol symbol, Function2<? super IrFunction, ? super IrValueParameter, ? extends IrExpressionBody> defaultArgumentFactory) {
        Intrinsics.checkNotNullParameter(origin, "origin");
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        Intrinsics.checkNotNullParameter(symbol, "symbol");
        Intrinsics.checkNotNullParameter(defaultArgumentFactory, "defaultArgumentFactory");
        IrFactory irFactory = this.irFactory;
        Name name2 = descriptor.getShortName();
        Intrinsics.checkNotNullExpressionValue(name2, "getName(...)");
        DescriptorVisibility visibility = descriptor.getVisibility();
        Intrinsics.checkNotNullExpressionValue(visibility, "getVisibility(...)");
        boolean zIsInline = descriptor.isInline();
        boolean zIsExpect = descriptor.get$isExpect();
        IrUninitializedType irUninitializedType = IrUninitializedType.INSTANCE;
        Modality modality = descriptor.getModality();
        Intrinsics.checkNotNullExpressionValue(modality, "getModality(...)");
        IrSimpleFunction irSimpleFunctionCreateSimpleFunction$default = IrFactory.DefaultImpls.createSimpleFunction$default(irFactory, startOffset, endOffset, origin, name2, visibility, zIsInline, zIsExpect, irUninitializedType, modality, symbol, descriptor.isTailrec(), descriptor.isSuspend(), descriptor.isOperator(), descriptor.isInfix(), DescriptorUtilsKt.isEffectivelyExternal(descriptor), null, false, 98304, null);
        irSimpleFunctionCreateSimpleFunction$default.setMetadata(new DescriptorMetadataSource.Function(descriptor));
        SymbolTable symbolTable = this.symbolTable;
        IrSimpleFunction irSimpleFunction = irSimpleFunctionCreateSimpleFunction$default;
        symbolTable.enterScope(irSimpleFunction);
        Collection<? extends FunctionDescriptor> overriddenDescriptors = descriptor.getOverriddenDescriptors();
        Intrinsics.checkNotNullExpressionValue(overriddenDescriptors, "getOverriddenDescriptors(...)");
        generateOverridenSymbols(irSimpleFunctionCreateSimpleFunction$default, overriddenDescriptors);
        List<TypeParameterDescriptor> typeParameters = DescriptorUtilsKt.getPropertyIfAccessor(descriptor).getTypeParameters();
        Intrinsics.checkNotNullExpressionValue(typeParameters, "getTypeParameters(...)");
        generateScopedTypeParameterDeclarations(irSimpleFunctionCreateSimpleFunction$default, typeParameters);
        generateValueParameterDeclarations(irSimpleFunctionCreateSimpleFunction$default, descriptor, defaultArgumentFactory);
        KotlinType returnType = descriptor.getReturnType();
        if (returnType != null) {
            Intrinsics.checkNotNull(returnType);
            IrType irType = toIrType(returnType);
            if (irType != null) {
                irSimpleFunctionCreateSimpleFunction$default.setReturnType(irType);
                Unit unit = Unit.INSTANCE;
                symbolTable.leaveScope(irSimpleFunction);
                return irSimpleFunctionCreateSimpleFunction$default;
            }
        }
        throw new IllegalStateException(("Expected return type " + descriptor).toString());
    }

    public final IrProperty generateProperty(int startOffset, int endOffset, IrDeclarationOrigin origin, PropertyDescriptor descriptor, IrPropertySymbol symbol) {
        Intrinsics.checkNotNullParameter(origin, "origin");
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        Intrinsics.checkNotNullParameter(symbol, "symbol");
        IrFactory irFactory = this.irFactory;
        Name name2 = descriptor.getShortName();
        Intrinsics.checkNotNullExpressionValue(name2, "getName(...)");
        DescriptorVisibility visibility = descriptor.getVisibility();
        Intrinsics.checkNotNullExpressionValue(visibility, "getVisibility(...)");
        Modality modality = descriptor.getModality();
        Intrinsics.checkNotNullExpressionValue(modality, "getModality(...)");
        IrProperty irPropertyCreateProperty$default = IrFactory.DefaultImpls.createProperty$default(irFactory, startOffset, endOffset, origin, name2, visibility, modality, symbol, descriptor.isVar(), descriptor.isConst(), descriptor.isLateInit(), false, DescriptorUtilsKt.isEffectivelyExternal(descriptor), (DeserializedContainerSource) null, descriptor.get$isExpect(), false, ContactCallbackEvent.ON_STARTED_FILTERED_MANIFOLD, (Object) null);
        irPropertyCreateProperty$default.setMetadata(new DescriptorMetadataSource.Property(descriptor));
        return irPropertyCreateProperty$default;
    }
}
