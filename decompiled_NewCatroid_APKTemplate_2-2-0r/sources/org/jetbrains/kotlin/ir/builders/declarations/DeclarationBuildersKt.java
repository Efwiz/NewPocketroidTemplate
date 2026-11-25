package org.jetbrains.kotlin.ir.builders.declarations;

import java.util.Collection;
import java.util.List;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.descriptors.DescriptorVisibilities;
import org.jetbrains.kotlin.descriptors.DescriptorVisibility;
import org.jetbrains.kotlin.descriptors.Modality;
import org.jetbrains.kotlin.descriptors.SourceElement;
import org.jetbrains.kotlin.ir.IrBuiltIns;
import org.jetbrains.kotlin.ir.IrStatement;
import org.jetbrains.kotlin.ir.declarations.IrClass;
import org.jetbrains.kotlin.ir.declarations.IrConstructor;
import org.jetbrains.kotlin.ir.declarations.IrDeclaration;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationContainer;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationOrigin;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationParent;
import org.jetbrains.kotlin.ir.declarations.IrFactory;
import org.jetbrains.kotlin.ir.declarations.IrFactoryHelpersKt;
import org.jetbrains.kotlin.ir.declarations.IrField;
import org.jetbrains.kotlin.ir.declarations.IrFunction;
import org.jetbrains.kotlin.ir.declarations.IrProperty;
import org.jetbrains.kotlin.ir.declarations.IrSimpleFunction;
import org.jetbrains.kotlin.ir.declarations.IrTypeParameter;
import org.jetbrains.kotlin.ir.declarations.IrTypeParametersContainer;
import org.jetbrains.kotlin.ir.declarations.IrValueParameter;
import org.jetbrains.kotlin.ir.declarations.IrVariable;
import org.jetbrains.kotlin.ir.declarations.impl.IrVariableImpl;
import org.jetbrains.kotlin.ir.expressions.IrStatementOrigin;
import org.jetbrains.kotlin.ir.expressions.impl.IrGetFieldImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrGetValueImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrReturnImpl;
import org.jetbrains.kotlin.ir.symbols.IrClassSymbol;
import org.jetbrains.kotlin.ir.symbols.IrFieldSymbol;
import org.jetbrains.kotlin.ir.symbols.IrSimpleFunctionSymbol;
import org.jetbrains.kotlin.ir.symbols.IrValueParameterSymbol;
import org.jetbrains.kotlin.ir.symbols.impl.IrClassSymbolImpl;
import org.jetbrains.kotlin.ir.symbols.impl.IrConstructorSymbolImpl;
import org.jetbrains.kotlin.ir.symbols.impl.IrFieldSymbolImpl;
import org.jetbrains.kotlin.ir.symbols.impl.IrPropertySymbolImpl;
import org.jetbrains.kotlin.ir.symbols.impl.IrSimpleFunctionSymbolImpl;
import org.jetbrains.kotlin.ir.symbols.impl.IrTypeParameterSymbolImpl;
import org.jetbrains.kotlin.ir.symbols.impl.IrValueParameterSymbolImpl;
import org.jetbrains.kotlin.ir.symbols.impl.IrVariableSymbolImpl;
import org.jetbrains.kotlin.ir.types.IrType;
import org.jetbrains.kotlin.ir.util.IrUtilsKt;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.name.SpecialNames;
import org.jetbrains.kotlin.types.Variance;

/* JADX INFO: compiled from: declarationBuilders.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000Ð\u0001\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\u001aE\u0010\u0000\u001a\u00020\u0001\"\f\b\u0000\u0010\u0002*\u00020\u0003*\u00020\u00042\u0006\u0010\u0005\u001a\u0002H\u00022\u0006\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\t2\b\b\u0002\u0010\n\u001a\u00020\u000b2\b\b\u0002\u0010\f\u001a\u00020\u000b¢\u0006\u0002\u0010\r\u001a-\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0005\u001a\u00020\u00102\u0017\u0010\u0011\u001a\u0013\u0012\u0004\u0012\u00020\u0013\u0012\u0004\u0012\u00020\u00140\u0012¢\u0006\u0002\b\u0015H\u0086\bø\u0001\u0000\u001a@\u0010\u0016\u001a\u00020\u0001\"\f\b\u0000\u0010\u0002*\u00020\u0003*\u00020\u00042\u0006\u0010\u0017\u001a\u0002H\u00022\u0017\u0010\u0011\u001a\u0013\u0012\u0004\u0012\u00020\u0018\u0012\u0004\u0012\u00020\u00140\u0012¢\u0006\u0002\b\u0015H\u0086\bø\u0001\u0000¢\u0006\u0002\u0010\u0019\u001aV\u0010\u001a\u001a\u00020\u001b2\b\u0010\u0005\u001a\u0004\u0018\u00010\u00042\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\u000b2\u0006\u0010\u0006\u001a\u00020\u00072\u0006\u0010\u001c\u001a\u00020\u001d2\u0006\u0010\b\u001a\u00020\t2\b\b\u0002\u0010\u001e\u001a\u00020\u001f2\b\b\u0002\u0010 \u001a\u00020\u001f2\b\b\u0002\u0010!\u001a\u00020\u001f\u001a+\u0010\"\u001a\u00020#*\u00020$2\u0019\b\u0002\u0010\u0011\u001a\u0013\u0012\u0004\u0012\u00020%\u0012\u0004\u0012\u00020\u00140\u0012¢\u0006\u0002\b\u0015H\u0086\bø\u0001\u0000\u001a+\u0010&\u001a\u00020'*\u00020(2\u0019\b\u0002\u0010\u0011\u001a\u0013\u0012\u0004\u0012\u00020)\u0012\u0004\u0012\u00020\u00140\u0012¢\u0006\u0002\b\u0015H\u0086\bø\u0001\u0000\u001a\u001a\u0010*\u001a\u00020\u0014*\u00020$2\u0006\u0010+\u001a\u00020(2\u0006\u0010,\u001a\u00020-\u001a$\u0010.\u001a\u00020#*\u00020(2\u0006\u0010/\u001a\u0002002\u0006\u00101\u001a\u00020\t2\b\b\u0002\u00102\u001a\u000203\u001a)\u0010.\u001a\u00020#*\u00020(2\u0017\u0010\u0011\u001a\u0013\u0012\u0004\u0012\u00020%\u0012\u0004\u0012\u00020\u00140\u0012¢\u0006\u0002\b\u0015H\u0086\bø\u0001\u0000\u001a$\u0010.\u001a\u00020#*\u00020(2\u0006\u0010/\u001a\u00020\u001d2\u0006\u00101\u001a\u00020\t2\b\b\u0002\u00102\u001a\u000203\u001at\u00104\u001a\u000205*\u00020(2\u0006\u0010\u001c\u001a\u0002002\u0006\u00106\u001a\u00020\t2\b\b\u0002\u00107\u001a\u0002082\b\b\u0002\u00109\u001a\u0002032\b\b\u0002\u0010:\u001a\u00020\u001f2\b\b\u0002\u0010;\u001a\u00020\u001f2\b\b\u0002\u0010<\u001a\u00020\u001f2\b\b\u0002\u0010=\u001a\u00020\u001f2\b\b\u0002\u0010\u0006\u001a\u00020\u00072\b\b\u0002\u0010\n\u001a\u00020\u000b2\b\b\u0002\u0010\f\u001a\u00020\u000b\u001a)\u00104\u001a\u000205*\u00020(2\u0017\u0010\u0011\u001a\u0013\u0012\u0004\u0012\u00020)\u0012\u0004\u0012\u00020\u00140\u0012¢\u0006\u0002\b\u0015H\u0086\bø\u0001\u0000\u001a1\u00104\u001a\u000205*\u00020>2\u0006\u0010?\u001a\u00020@2\u0017\u0010\u0011\u001a\u0013\u0012\u0004\u0012\u00020)\u0012\u0004\u0012\u00020\u00140\u0012¢\u0006\u0002\b\u0015H\u0086\bø\u0001\u0000\u001a+\u0010A\u001a\u000205*\u00020$2\u0019\b\u0002\u0010\u0011\u001a\u0013\u0012\u0004\u0012\u00020)\u0012\u0004\u0012\u00020\u00140\u0012¢\u0006\u0002\b\u0015H\u0086\bø\u0001\u0000\u001a)\u0010B\u001a\u00020$*\u00020(2\u0017\u0010\u0011\u001a\u0013\u0012\u0004\u0012\u00020C\u0012\u0004\u0012\u00020\u00140\u0012¢\u0006\u0002\b\u0015H\u0086\bø\u0001\u0000\u001a+\u0010D\u001a\u000205*\u00020$2\u0019\b\u0002\u0010\u0011\u001a\u0013\u0012\u0004\u0012\u00020)\u0012\u0004\u0012\u00020\u00140\u0012¢\u0006\u0002\b\u0015H\u0086\bø\u0001\u0000\u001a$\u0010E\u001a\u00020\u000f*\u00020\u00102\u0006\u0010\u001c\u001a\u0002002\u0006\u0010F\u001a\u00020\t2\b\b\u0002\u0010G\u001a\u00020H\u001a)\u0010E\u001a\u00020\u000f*\u00020\u00102\u0017\u0010\u0011\u001a\u0013\u0012\u0004\u0012\u00020\u0013\u0012\u0004\u0012\u00020\u00140\u0012¢\u0006\u0002\b\u0015H\u0086\bø\u0001\u0000\u001a$\u0010I\u001a\u00020\u0001*\u00020J2\u0006\u0010\u001c\u001a\u0002002\u0006\u0010\b\u001a\u00020\t2\b\b\u0002\u0010\u0006\u001a\u00020\u0007\u001a)\u0010I\u001a\u00020\u0001*\u00020J2\u0017\u0010\u0011\u001a\u0013\u0012\u0004\u0012\u00020\u0018\u0012\u0004\u0012\u00020\u00140\u0012¢\u0006\u0002\b\u0015H\u0086\bø\u0001\u0000\u001a$\u0010I\u001a\u00020\u0001*\u00020J2\u0006\u0010\u001c\u001a\u00020\u001d2\u0006\u0010\b\u001a\u00020\t2\b\b\u0002\u0010\u0006\u001a\u00020\u0007\u001a\u0014\u0010K\u001a\u00020(*\u00020>2\u0006\u0010\u0011\u001a\u00020LH\u0001\u001a)\u0010K\u001a\u00020(*\u00020>2\u0017\u0010\u0011\u001a\u0013\u0012\u0004\u0012\u00020L\u0012\u0004\u0012\u00020\u00140\u0012¢\u0006\u0002\b\u0015H\u0086\bø\u0001\u0000\u001a\u0014\u0010M\u001a\u00020'*\u00020>2\u0006\u0010\u0011\u001a\u00020)H\u0001\u001a)\u0010M\u001a\u00020'*\u00020>2\u0017\u0010\u0011\u001a\u0013\u0012\u0004\u0012\u00020)\u0012\u0004\u0012\u00020\u00140\u0012¢\u0006\u0002\b\u0015H\u0086\bø\u0001\u0000\u001a\u0014\u0010N\u001a\u00020#*\u00020>2\u0006\u0010\u0011\u001a\u00020%H\u0001\u001a)\u0010N\u001a\u00020#*\u00020>2\u0017\u0010\u0011\u001a\u0013\u0012\u0004\u0012\u00020%\u0012\u0004\u0012\u00020\u00140\u0012¢\u0006\u0002\b\u0015H\u0086\bø\u0001\u0000\u001a)\u0010O\u001a\u000205*\u00020>2\u0017\u0010\u0011\u001a\u0013\u0012\u0004\u0012\u00020)\u0012\u0004\u0012\u00020\u00140\u0012¢\u0006\u0002\b\u0015H\u0086\bø\u0001\u0000\u001a\u0014\u0010P\u001a\u000205*\u00020>2\u0006\u0010\u0011\u001a\u00020)H\u0001\u001a\u0014\u0010Q\u001a\u00020$*\u00020>2\u0006\u0010\u0011\u001a\u00020CH\u0001\u001a)\u0010Q\u001a\u00020$*\u00020>2\u0017\u0010\u0011\u001a\u0013\u0012\u0004\u0012\u00020C\u0012\u0004\u0012\u00020\u00140\u0012¢\u0006\u0002\b\u0015H\u0086\bø\u0001\u0000\u001a\u001c\u0010\u000e\u001a\u00020\u000f*\u00020>2\u0006\u0010\u0011\u001a\u00020\u00132\u0006\u0010\u0005\u001a\u00020\u0004H\u0001\u001a\u001a\u0010\u0016\u001a\u00020\u0001*\u00020>2\u0006\u0010\u0011\u001a\u00020\u00182\u0006\u0010\u0005\u001a\u00020\u0004\u0082\u0002\u0007\n\u0005\b\u009920\u0001¨\u0006R"}, d2 = {"buildReceiverParameter", "Lorg/jetbrains/kotlin/ir/declarations/IrValueParameter;", "D", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclaration;", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationParent;", "parent", "origin", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationOrigin;", ModuleXmlParser.TYPE, "Lorg/jetbrains/kotlin/ir/types/IrType;", "startOffset", "", "endOffset", "(Lorg/jetbrains/kotlin/ir/declarations/IrDeclaration;Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationOrigin;Lorg/jetbrains/kotlin/ir/types/IrType;II)Lorg/jetbrains/kotlin/ir/declarations/IrValueParameter;", "buildTypeParameter", "Lorg/jetbrains/kotlin/ir/declarations/IrTypeParameter;", "Lorg/jetbrains/kotlin/ir/declarations/IrTypeParametersContainer;", "builder", "Lkotlin/Function1;", "Lorg/jetbrains/kotlin/ir/builders/declarations/IrTypeParameterBuilder;", "", "Lkotlin/ExtensionFunctionType;", "buildValueParameter", "declaration", "Lorg/jetbrains/kotlin/ir/builders/declarations/IrValueParameterBuilder;", "(Lorg/jetbrains/kotlin/ir/declarations/IrDeclaration;Lkotlin/jvm/functions/Function1;)Lorg/jetbrains/kotlin/ir/declarations/IrValueParameter;", "buildVariable", "Lorg/jetbrains/kotlin/ir/declarations/IrVariable;", "name", "Lorg/jetbrains/kotlin/name/Name;", "isVar", "", "isConst", "isLateinit", "addBackingField", "Lorg/jetbrains/kotlin/ir/declarations/IrField;", "Lorg/jetbrains/kotlin/ir/declarations/IrProperty;", "Lorg/jetbrains/kotlin/ir/builders/declarations/IrFieldBuilder;", "addConstructor", "Lorg/jetbrains/kotlin/ir/declarations/IrConstructor;", "Lorg/jetbrains/kotlin/ir/declarations/IrClass;", "Lorg/jetbrains/kotlin/ir/builders/declarations/IrFunctionBuilder;", "addDefaultGetter", "parentClass", "builtIns", "Lorg/jetbrains/kotlin/ir/IrBuiltIns;", "addField", "fieldName", "", "fieldType", "fieldVisibility", "Lorg/jetbrains/kotlin/descriptors/DescriptorVisibility;", "addFunction", "Lorg/jetbrains/kotlin/ir/declarations/IrSimpleFunction;", "returnType", "modality", "Lorg/jetbrains/kotlin/descriptors/Modality;", "visibility", "isStatic", "isSuspend", "isFakeOverride", "isInline", "Lorg/jetbrains/kotlin/ir/declarations/IrFactory;", "klass", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationContainer;", "addGetter", "addProperty", "Lorg/jetbrains/kotlin/ir/builders/declarations/IrPropertyBuilder;", "addSetter", "addTypeParameter", "upperBound", "variance", "Lorg/jetbrains/kotlin/types/Variance;", "addValueParameter", "Lorg/jetbrains/kotlin/ir/declarations/IrFunction;", "buildClass", "Lorg/jetbrains/kotlin/ir/builders/declarations/IrClassBuilder;", "buildConstructor", "buildField", "buildFun", "buildFunction", "buildProperty", "ir.tree"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class DeclarationBuildersKt {
    /* JADX WARN: Multi-variable type inference failed */
    public static final IrClass buildClass(IrFactory irFactory, IrClassBuilder builder) {
        Intrinsics.checkNotNullParameter(irFactory, "<this>");
        Intrinsics.checkNotNullParameter(builder, "builder");
        return IrFactory.DefaultImpls.createClass$default(irFactory, builder.getStartOffset(), builder.getEndOffset(), builder.getOrigin(), builder.getName(), builder.getVisibility(), new IrClassSymbolImpl(null, 1, 0 == true ? 1 : 0), builder.getKind(), builder.getModality(), builder.getIsExternal(), builder.getIsCompanion(), builder.getIsInner(), builder.getIsData(), builder.getIsValue(), builder.getIsExpect(), builder.getIsFun(), (SourceElement) null, 32768, (Object) null);
    }

    public static final IrClass buildClass(IrFactory irFactory, Function1<? super IrClassBuilder, Unit> builder) {
        Intrinsics.checkNotNullParameter(irFactory, "<this>");
        Intrinsics.checkNotNullParameter(builder, "builder");
        IrClassBuilder irClassBuilder = new IrClassBuilder();
        builder.invoke(irClassBuilder);
        return buildClass(irFactory, irClassBuilder);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static final IrField buildField(IrFactory irFactory, IrFieldBuilder builder) {
        Intrinsics.checkNotNullParameter(irFactory, "<this>");
        Intrinsics.checkNotNullParameter(builder, "builder");
        IrField irFieldCreateField = irFactory.createField(builder.getStartOffset(), builder.getEndOffset(), builder.getOrigin(), builder.getName(), builder.getVisibility(), new IrFieldSymbolImpl(null, 1, 0 == true ? 1 : 0), builder.getType(), builder.getIsFinal(), builder.getIsStatic(), builder.getIsExternal());
        irFieldCreateField.setMetadata(builder.getMetadata());
        return irFieldCreateField;
    }

    public static final IrField buildField(IrFactory irFactory, Function1<? super IrFieldBuilder, Unit> builder) {
        Intrinsics.checkNotNullParameter(irFactory, "<this>");
        Intrinsics.checkNotNullParameter(builder, "builder");
        IrFieldBuilder irFieldBuilder = new IrFieldBuilder();
        builder.invoke(irFieldBuilder);
        return buildField(irFactory, irFieldBuilder);
    }

    public static final IrField addField(IrClass irClass, Function1<? super IrFieldBuilder, Unit> builder) {
        Intrinsics.checkNotNullParameter(irClass, "<this>");
        Intrinsics.checkNotNullParameter(builder, "builder");
        IrFactory factory = irClass.getFactory();
        IrFieldBuilder irFieldBuilder = new IrFieldBuilder();
        builder.invoke(irFieldBuilder);
        IrField irFieldBuildField = buildField(factory, irFieldBuilder);
        irFieldBuildField.setParent(irClass);
        irClass.getDeclarations().add(irFieldBuildField);
        return irFieldBuildField;
    }

    public static /* synthetic */ IrField addField$default(IrClass irClass, Name name2, IrType irType, DescriptorVisibility PRIVATE, int i, Object obj) {
        if ((i & 4) != 0) {
            PRIVATE = DescriptorVisibilities.PRIVATE;
            Intrinsics.checkNotNullExpressionValue(PRIVATE, "PRIVATE");
        }
        return addField(irClass, name2, irType, PRIVATE);
    }

    public static /* synthetic */ IrField addField$default(IrClass irClass, String str, IrType irType, DescriptorVisibility PRIVATE, int i, Object obj) {
        if ((i & 4) != 0) {
            PRIVATE = DescriptorVisibilities.PRIVATE;
            Intrinsics.checkNotNullExpressionValue(PRIVATE, "PRIVATE");
        }
        return addField(irClass, str, irType, PRIVATE);
    }

    public static final IrField addField(IrClass irClass, String fieldName, IrType fieldType, DescriptorVisibility fieldVisibility) {
        Intrinsics.checkNotNullParameter(irClass, "<this>");
        Intrinsics.checkNotNullParameter(fieldName, "fieldName");
        Intrinsics.checkNotNullParameter(fieldType, "fieldType");
        Intrinsics.checkNotNullParameter(fieldVisibility, "fieldVisibility");
        Name nameIdentifier = Name.identifier(fieldName);
        Intrinsics.checkNotNullExpressionValue(nameIdentifier, "identifier(...)");
        return addField(irClass, nameIdentifier, fieldType, fieldVisibility);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static final IrProperty buildProperty(IrFactory irFactory, IrPropertyBuilder builder) {
        Intrinsics.checkNotNullParameter(irFactory, "<this>");
        Intrinsics.checkNotNullParameter(builder, "builder");
        return irFactory.createProperty(builder.getStartOffset(), builder.getEndOffset(), builder.getOrigin(), builder.getName(), builder.getVisibility(), builder.getModality(), new IrPropertySymbolImpl(null, 1, 0 == true ? 1 : 0), builder.getIsVar(), builder.getIsConst(), builder.getIsLateinit(), builder.getIsDelegated(), builder.getIsExternal(), builder.getContainerSource(), builder.getIsExpect(), builder.getIsFakeOverride());
    }

    public static final IrProperty buildProperty(IrFactory irFactory, Function1<? super IrPropertyBuilder, Unit> builder) {
        Intrinsics.checkNotNullParameter(irFactory, "<this>");
        Intrinsics.checkNotNullParameter(builder, "builder");
        IrPropertyBuilder irPropertyBuilder = new IrPropertyBuilder();
        builder.invoke(irPropertyBuilder);
        return buildProperty(irFactory, irPropertyBuilder);
    }

    public static final IrProperty addProperty(IrClass irClass, Function1<? super IrPropertyBuilder, Unit> builder) {
        Intrinsics.checkNotNullParameter(irClass, "<this>");
        Intrinsics.checkNotNullParameter(builder, "builder");
        IrFactory factory = irClass.getFactory();
        IrPropertyBuilder irPropertyBuilder = new IrPropertyBuilder();
        builder.invoke(irPropertyBuilder);
        IrProperty irPropertyBuildProperty = buildProperty(factory, irPropertyBuilder);
        irClass.getDeclarations().add(irPropertyBuildProperty);
        irPropertyBuildProperty.setParent(irClass);
        return irPropertyBuildProperty;
    }

    public static /* synthetic */ IrSimpleFunction addGetter$default(IrProperty irProperty, Function1 builder, int i, Object obj) {
        if ((i & 1) != 0) {
            builder = new Function1<IrFunctionBuilder, Unit>() { // from class: org.jetbrains.kotlin.ir.builders.declarations.DeclarationBuildersKt.addGetter.1
                /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
                public final void invoke2(IrFunctionBuilder irFunctionBuilder) {
                    Intrinsics.checkNotNullParameter(irFunctionBuilder, "$this$null");
                }

                @Override // kotlin.jvm.functions.Function1
                public /* bridge */ /* synthetic */ Unit invoke(IrFunctionBuilder irFunctionBuilder) {
                    invoke2(irFunctionBuilder);
                    return Unit.INSTANCE;
                }
            };
        }
        Intrinsics.checkNotNullParameter(irProperty, "<this>");
        Intrinsics.checkNotNullParameter(builder, "builder");
        IrFunctionBuilder irFunctionBuilder = new IrFunctionBuilder();
        Name nameSpecial = Name.special("<get-" + irProperty.getName() + '>');
        Intrinsics.checkNotNullExpressionValue(nameSpecial, "special(...)");
        irFunctionBuilder.setName(nameSpecial);
        builder.invoke(irFunctionBuilder);
        IrSimpleFunction irSimpleFunctionBuildFunction = buildFunction(irProperty.getFactory(), irFunctionBuilder);
        irProperty.setGetter(irSimpleFunctionBuildFunction);
        irSimpleFunctionBuildFunction.setCorrespondingPropertySymbol(irProperty.getSymbol());
        irSimpleFunctionBuildFunction.setParent(irProperty.getParent());
        return irSimpleFunctionBuildFunction;
    }

    public static final IrSimpleFunction addGetter(IrProperty irProperty, Function1<? super IrFunctionBuilder, Unit> builder) {
        Intrinsics.checkNotNullParameter(irProperty, "<this>");
        Intrinsics.checkNotNullParameter(builder, "builder");
        IrFunctionBuilder irFunctionBuilder = new IrFunctionBuilder();
        Name nameSpecial = Name.special("<get-" + irProperty.getName() + '>');
        Intrinsics.checkNotNullExpressionValue(nameSpecial, "special(...)");
        irFunctionBuilder.setName(nameSpecial);
        builder.invoke(irFunctionBuilder);
        IrSimpleFunction irSimpleFunctionBuildFunction = buildFunction(irProperty.getFactory(), irFunctionBuilder);
        irProperty.setGetter(irSimpleFunctionBuildFunction);
        irSimpleFunctionBuildFunction.setCorrespondingPropertySymbol(irProperty.getSymbol());
        irSimpleFunctionBuildFunction.setParent(irProperty.getParent());
        return irSimpleFunctionBuildFunction;
    }

    public static /* synthetic */ IrSimpleFunction addSetter$default(IrProperty irProperty, Function1 builder, int i, Object obj) {
        if ((i & 1) != 0) {
            builder = new Function1<IrFunctionBuilder, Unit>() { // from class: org.jetbrains.kotlin.ir.builders.declarations.DeclarationBuildersKt.addSetter.1
                /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
                public final void invoke2(IrFunctionBuilder irFunctionBuilder) {
                    Intrinsics.checkNotNullParameter(irFunctionBuilder, "$this$null");
                }

                @Override // kotlin.jvm.functions.Function1
                public /* bridge */ /* synthetic */ Unit invoke(IrFunctionBuilder irFunctionBuilder) {
                    invoke2(irFunctionBuilder);
                    return Unit.INSTANCE;
                }
            };
        }
        Intrinsics.checkNotNullParameter(irProperty, "<this>");
        Intrinsics.checkNotNullParameter(builder, "builder");
        IrFunctionBuilder irFunctionBuilder = new IrFunctionBuilder();
        Name nameSpecial = Name.special("<set-" + irProperty.getName() + '>');
        Intrinsics.checkNotNullExpressionValue(nameSpecial, "special(...)");
        irFunctionBuilder.setName(nameSpecial);
        builder.invoke(irFunctionBuilder);
        IrSimpleFunction irSimpleFunctionBuildFunction = buildFunction(irProperty.getFactory(), irFunctionBuilder);
        irProperty.setSetter(irSimpleFunctionBuildFunction);
        irSimpleFunctionBuildFunction.setCorrespondingPropertySymbol(irProperty.getSymbol());
        irSimpleFunctionBuildFunction.setParent(irProperty.getParent());
        return irSimpleFunctionBuildFunction;
    }

    public static final IrSimpleFunction addSetter(IrProperty irProperty, Function1<? super IrFunctionBuilder, Unit> builder) {
        Intrinsics.checkNotNullParameter(irProperty, "<this>");
        Intrinsics.checkNotNullParameter(builder, "builder");
        IrFunctionBuilder irFunctionBuilder = new IrFunctionBuilder();
        Name nameSpecial = Name.special("<set-" + irProperty.getName() + '>');
        Intrinsics.checkNotNullExpressionValue(nameSpecial, "special(...)");
        irFunctionBuilder.setName(nameSpecial);
        builder.invoke(irFunctionBuilder);
        IrSimpleFunction irSimpleFunctionBuildFunction = buildFunction(irProperty.getFactory(), irFunctionBuilder);
        irProperty.setSetter(irSimpleFunctionBuildFunction);
        irSimpleFunctionBuildFunction.setCorrespondingPropertySymbol(irProperty.getSymbol());
        irSimpleFunctionBuildFunction.setParent(irProperty.getParent());
        return irSimpleFunctionBuildFunction;
    }

    public static final void addDefaultGetter(IrProperty irProperty, IrClass parentClass, IrBuiltIns builtIns) {
        Intrinsics.checkNotNullParameter(irProperty, "<this>");
        Intrinsics.checkNotNullParameter(parentClass, "parentClass");
        Intrinsics.checkNotNullParameter(builtIns, "builtIns");
        IrField backingField = irProperty.getBackingField();
        Intrinsics.checkNotNull(backingField);
        IrFunctionBuilder irFunctionBuilder = new IrFunctionBuilder();
        Name nameSpecial = Name.special("<get-" + irProperty.getName() + '>');
        Intrinsics.checkNotNullExpressionValue(nameSpecial, "special(...)");
        irFunctionBuilder.setName(nameSpecial);
        irFunctionBuilder.setOrigin(IrDeclarationOrigin.DEFAULT_PROPERTY_ACCESSOR.INSTANCE);
        irFunctionBuilder.setReturnType(backingField.getType());
        IrSimpleFunction irSimpleFunctionBuildFunction = buildFunction(irProperty.getFactory(), irFunctionBuilder);
        irProperty.setGetter(irSimpleFunctionBuildFunction);
        irSimpleFunctionBuildFunction.setCorrespondingPropertySymbol(irProperty.getSymbol());
        irSimpleFunctionBuildFunction.setParent(irProperty.getParent());
        IrValueParameter thisReceiver = parentClass.getThisReceiver();
        Intrinsics.checkNotNull(thisReceiver);
        irSimpleFunctionBuildFunction.setDispatchReceiverParameter(IrUtilsKt.copyTo$default(thisReceiver, irSimpleFunctionBuildFunction, null, 0, 0, 0, null, null, null, null, null, false, false, false, 8190, null));
        IrFactory factory = irSimpleFunctionBuildFunction.getFactory();
        IrType nothingType = builtIns.getNothingType();
        IrSimpleFunctionSymbol symbol = irSimpleFunctionBuildFunction.getSymbol();
        IrFieldSymbol symbol2 = backingField.getSymbol();
        IrType type = backingField.getType();
        IrValueParameter dispatchReceiverParameter = irSimpleFunctionBuildFunction.getDispatchReceiverParameter();
        Intrinsics.checkNotNull(dispatchReceiverParameter);
        IrType type2 = dispatchReceiverParameter.getType();
        IrValueParameter dispatchReceiverParameter2 = irSimpleFunctionBuildFunction.getDispatchReceiverParameter();
        Intrinsics.checkNotNull(dispatchReceiverParameter2);
        int i = -1;
        int i2 = -1;
        IrStatementOrigin irStatementOrigin = null;
        IrClassSymbol irClassSymbol = null;
        irSimpleFunctionBuildFunction.setBody(IrFactoryHelpersKt.createBlockBody(factory, -1, -1, (List<? extends IrStatement>) CollectionsKt.listOf(new IrReturnImpl(-1, -1, nothingType, symbol, new IrGetFieldImpl(i, i2, symbol2, type, new IrGetValueImpl(-1, -1, type2, dispatchReceiverParameter2.getSymbol(), null, 16, null), irStatementOrigin, irClassSymbol, 96, null)))));
    }

    public static /* synthetic */ IrField addBackingField$default(IrProperty irProperty, Function1 builder, int i, Object obj) {
        if ((i & 1) != 0) {
            builder = new Function1<IrFieldBuilder, Unit>() { // from class: org.jetbrains.kotlin.ir.builders.declarations.DeclarationBuildersKt.addBackingField.1
                /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
                public final void invoke2(IrFieldBuilder irFieldBuilder) {
                    Intrinsics.checkNotNullParameter(irFieldBuilder, "$this$null");
                }

                @Override // kotlin.jvm.functions.Function1
                public /* bridge */ /* synthetic */ Unit invoke(IrFieldBuilder irFieldBuilder) {
                    invoke2(irFieldBuilder);
                    return Unit.INSTANCE;
                }
            };
        }
        Intrinsics.checkNotNullParameter(irProperty, "<this>");
        Intrinsics.checkNotNullParameter(builder, "builder");
        IrFieldBuilder irFieldBuilder = new IrFieldBuilder();
        irFieldBuilder.setName(irProperty.getName());
        irFieldBuilder.setOrigin(IrDeclarationOrigin.PROPERTY_BACKING_FIELD.INSTANCE);
        builder.invoke(irFieldBuilder);
        IrField irFieldBuildField = buildField(irProperty.getFactory(), irFieldBuilder);
        irProperty.setBackingField(irFieldBuildField);
        irFieldBuildField.setCorrespondingPropertySymbol(irProperty.getSymbol());
        irFieldBuildField.setParent(irProperty.getParent());
        return irFieldBuildField;
    }

    public static final IrField addBackingField(IrProperty irProperty, Function1<? super IrFieldBuilder, Unit> builder) {
        Intrinsics.checkNotNullParameter(irProperty, "<this>");
        Intrinsics.checkNotNullParameter(builder, "builder");
        IrFieldBuilder irFieldBuilder = new IrFieldBuilder();
        irFieldBuilder.setName(irProperty.getName());
        irFieldBuilder.setOrigin(IrDeclarationOrigin.PROPERTY_BACKING_FIELD.INSTANCE);
        builder.invoke(irFieldBuilder);
        IrField irFieldBuildField = buildField(irProperty.getFactory(), irFieldBuilder);
        irProperty.setBackingField(irFieldBuildField);
        irFieldBuildField.setCorrespondingPropertySymbol(irProperty.getSymbol());
        irFieldBuildField.setParent(irProperty.getParent());
        return irFieldBuildField;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static final IrSimpleFunction buildFunction(IrFactory irFactory, IrFunctionBuilder builder) {
        Intrinsics.checkNotNullParameter(irFactory, "<this>");
        Intrinsics.checkNotNullParameter(builder, "builder");
        return irFactory.createSimpleFunction(builder.getStartOffset(), builder.getEndOffset(), builder.getOrigin(), builder.getName(), builder.getVisibility(), builder.getIsInline(), builder.getIsExpect(), builder.getReturnType(), builder.getModality(), new IrSimpleFunctionSymbolImpl(null, 1, 0 == true ? 1 : 0), builder.getIsTailrec(), builder.getIsSuspend(), builder.getIsOperator(), builder.getIsInfix(), builder.getIsExternal(), builder.getContainerSource(), builder.getIsFakeOverride());
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static final IrConstructor buildConstructor(IrFactory irFactory, IrFunctionBuilder builder) {
        Intrinsics.checkNotNullParameter(irFactory, "<this>");
        Intrinsics.checkNotNullParameter(builder, "builder");
        return irFactory.createConstructor(builder.getStartOffset(), builder.getEndOffset(), builder.getOrigin(), SpecialNames.INIT, builder.getVisibility(), builder.getIsInline(), builder.getIsExpect(), builder.getReturnType(), new IrConstructorSymbolImpl(null, 1, 0 == true ? 1 : 0), builder.getIsPrimary(), builder.getIsExternal(), builder.getContainerSource());
    }

    public static final IrSimpleFunction buildFun(IrFactory irFactory, Function1<? super IrFunctionBuilder, Unit> builder) {
        Intrinsics.checkNotNullParameter(irFactory, "<this>");
        Intrinsics.checkNotNullParameter(builder, "builder");
        IrFunctionBuilder irFunctionBuilder = new IrFunctionBuilder();
        builder.invoke(irFunctionBuilder);
        return buildFunction(irFactory, irFunctionBuilder);
    }

    public static final IrSimpleFunction addFunction(IrClass irClass, Function1<? super IrFunctionBuilder, Unit> builder) {
        Intrinsics.checkNotNullParameter(irClass, "<this>");
        Intrinsics.checkNotNullParameter(builder, "builder");
        IrFactory factory = irClass.getFactory();
        IrFunctionBuilder irFunctionBuilder = new IrFunctionBuilder();
        builder.invoke(irFunctionBuilder);
        IrSimpleFunction irSimpleFunctionBuildFunction = buildFunction(factory, irFunctionBuilder);
        IrClass irClass2 = irClass;
        irClass2.getDeclarations().add(irSimpleFunctionBuildFunction);
        irSimpleFunctionBuildFunction.setParent(irClass2);
        return irSimpleFunctionBuildFunction;
    }

    public static /* synthetic */ IrSimpleFunction addFunction$default(IrClass irClass, String str, IrType irType, Modality modality, DescriptorVisibility descriptorVisibility, boolean z, boolean z2, boolean z3, boolean z4, IrDeclarationOrigin irDeclarationOrigin, int i, int i2, int i3, Object obj) {
        DescriptorVisibility descriptorVisibility2;
        Modality modality2 = (i3 & 4) != 0 ? Modality.FINAL : modality;
        if ((i3 & 8) != 0) {
            DescriptorVisibility PUBLIC = DescriptorVisibilities.PUBLIC;
            Intrinsics.checkNotNullExpressionValue(PUBLIC, "PUBLIC");
            descriptorVisibility2 = PUBLIC;
        } else {
            descriptorVisibility2 = descriptorVisibility;
        }
        return addFunction(irClass, str, irType, modality2, descriptorVisibility2, (i3 & 16) != 0 ? false : z, (i3 & 32) != 0 ? false : z2, (i3 & 64) != 0 ? false : z3, (i3 & 128) != 0 ? false : z4, (i3 & 256) != 0 ? IrDeclarationOrigin.DEFINED.INSTANCE : irDeclarationOrigin, (i3 & 512) != 0 ? -1 : i, (i3 & 1024) != 0 ? -1 : i2);
    }

    public static final IrConstructor buildConstructor(IrFactory irFactory, Function1<? super IrFunctionBuilder, Unit> builder) {
        Intrinsics.checkNotNullParameter(irFactory, "<this>");
        Intrinsics.checkNotNullParameter(builder, "builder");
        IrFunctionBuilder irFunctionBuilder = new IrFunctionBuilder();
        builder.invoke(irFunctionBuilder);
        return buildConstructor(irFactory, irFunctionBuilder);
    }

    public static /* synthetic */ IrConstructor addConstructor$default(IrClass irClass, Function1 builder, int i, Object obj) {
        if ((i & 1) != 0) {
            builder = new Function1<IrFunctionBuilder, Unit>() { // from class: org.jetbrains.kotlin.ir.builders.declarations.DeclarationBuildersKt.addConstructor.1
                /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
                public final void invoke2(IrFunctionBuilder irFunctionBuilder) {
                    Intrinsics.checkNotNullParameter(irFunctionBuilder, "$this$null");
                }

                @Override // kotlin.jvm.functions.Function1
                public /* bridge */ /* synthetic */ Unit invoke(IrFunctionBuilder irFunctionBuilder) {
                    invoke2(irFunctionBuilder);
                    return Unit.INSTANCE;
                }
            };
        }
        Intrinsics.checkNotNullParameter(irClass, "<this>");
        Intrinsics.checkNotNullParameter(builder, "builder");
        IrFactory factory = irClass.getFactory();
        IrFunctionBuilder irFunctionBuilder = new IrFunctionBuilder();
        builder.invoke(irFunctionBuilder);
        irFunctionBuilder.setReturnType(IrUtilsKt.getDefaultType(irClass));
        IrConstructor irConstructorBuildConstructor = buildConstructor(factory, irFunctionBuilder);
        irClass.getDeclarations().add(irConstructorBuildConstructor);
        irConstructorBuildConstructor.setParent(irClass);
        return irConstructorBuildConstructor;
    }

    public static final IrConstructor addConstructor(IrClass irClass, Function1<? super IrFunctionBuilder, Unit> builder) {
        Intrinsics.checkNotNullParameter(irClass, "<this>");
        Intrinsics.checkNotNullParameter(builder, "builder");
        IrFactory factory = irClass.getFactory();
        IrFunctionBuilder irFunctionBuilder = new IrFunctionBuilder();
        builder.invoke(irFunctionBuilder);
        irFunctionBuilder.setReturnType(IrUtilsKt.getDefaultType(irClass));
        IrConstructor irConstructorBuildConstructor = buildConstructor(factory, irFunctionBuilder);
        irClass.getDeclarations().add(irConstructorBuildConstructor);
        irConstructorBuildConstructor.setParent(irClass);
        return irConstructorBuildConstructor;
    }

    public static /* synthetic */ IrValueParameter buildReceiverParameter$default(IrDeclaration irDeclaration, IrDeclarationOrigin irDeclarationOrigin, IrType irType, int i, int i2, int i3, Object obj) {
        if ((i3 & 8) != 0) {
            i = irDeclaration.getStartOffset();
        }
        if ((i3 & 16) != 0) {
            i2 = irDeclaration.getEndOffset();
        }
        return buildReceiverParameter(irDeclaration, irDeclarationOrigin, irType, i, i2);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static final <D extends IrDeclaration & IrDeclarationParent> IrValueParameter buildReceiverParameter(D parent, IrDeclarationOrigin origin, IrType type, int i, int i2) {
        Intrinsics.checkNotNullParameter(parent, "parent");
        Intrinsics.checkNotNullParameter(origin, "origin");
        Intrinsics.checkNotNullParameter(type, "type");
        IrValueParameter irValueParameterCreateValueParameter = parent.getFactory().createValueParameter(i, i2, origin, SpecialNames.THIS, type, false, (IrValueParameterSymbol) new IrValueParameterSymbolImpl(null, 1, 0 == true ? 1 : 0), -1, (IrType) null, false, false, false);
        irValueParameterCreateValueParameter.setParent(parent);
        return irValueParameterCreateValueParameter;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static final IrValueParameter buildValueParameter(IrFactory irFactory, IrValueParameterBuilder builder, IrDeclarationParent parent) {
        Intrinsics.checkNotNullParameter(irFactory, "<this>");
        Intrinsics.checkNotNullParameter(builder, "builder");
        Intrinsics.checkNotNullParameter(parent, "parent");
        IrValueParameter irValueParameterCreateValueParameter = irFactory.createValueParameter(builder.getStartOffset(), builder.getEndOffset(), builder.getOrigin(), builder.getName(), builder.getType(), builder.getIsAssignable(), new IrValueParameterSymbolImpl(null, 1, 0 == true ? 1 : 0), builder.getIndex(), builder.getVarargElementType(), builder.getIsCrossInline(), builder.getIsNoinline(), builder.getIsHidden());
        irValueParameterCreateValueParameter.setParent(parent);
        return irValueParameterCreateValueParameter;
    }

    public static final <D extends IrDeclaration & IrDeclarationParent> IrValueParameter buildValueParameter(D declaration, Function1<? super IrValueParameterBuilder, Unit> builder) {
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        Intrinsics.checkNotNullParameter(builder, "builder");
        IrValueParameterBuilder irValueParameterBuilder = new IrValueParameterBuilder();
        builder.invoke(irValueParameterBuilder);
        return buildValueParameter(declaration.getFactory(), irValueParameterBuilder, declaration);
    }

    public static final IrValueParameter addValueParameter(IrFunction irFunction, Function1<? super IrValueParameterBuilder, Unit> builder) {
        Intrinsics.checkNotNullParameter(irFunction, "<this>");
        Intrinsics.checkNotNullParameter(builder, "builder");
        IrValueParameterBuilder irValueParameterBuilder = new IrValueParameterBuilder();
        builder.invoke(irValueParameterBuilder);
        if (irValueParameterBuilder.getIndex() == -1) {
            irValueParameterBuilder.setIndex(irFunction.getValueParameters().size());
        }
        IrValueParameter irValueParameterBuildValueParameter = buildValueParameter(irFunction.getFactory(), irValueParameterBuilder, irFunction);
        irFunction.setValueParameters(CollectionsKt.plus((Collection<? extends IrValueParameter>) irFunction.getValueParameters(), irValueParameterBuildValueParameter));
        return irValueParameterBuildValueParameter;
    }

    public static /* synthetic */ IrValueParameter addValueParameter$default(IrFunction irFunction, String str, IrType irType, IrDeclarationOrigin irDeclarationOrigin, int i, Object obj) {
        if ((i & 4) != 0) {
            irDeclarationOrigin = IrDeclarationOrigin.DEFINED.INSTANCE;
        }
        return addValueParameter(irFunction, str, irType, irDeclarationOrigin);
    }

    public static final IrValueParameter addValueParameter(IrFunction irFunction, String name2, IrType type, IrDeclarationOrigin origin) {
        Intrinsics.checkNotNullParameter(irFunction, "<this>");
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(type, "type");
        Intrinsics.checkNotNullParameter(origin, "origin");
        Name nameIdentifier = Name.identifier(name2);
        Intrinsics.checkNotNullExpressionValue(nameIdentifier, "identifier(...)");
        return addValueParameter(irFunction, nameIdentifier, type, origin);
    }

    public static /* synthetic */ IrValueParameter addValueParameter$default(IrFunction irFunction, Name name2, IrType irType, IrDeclarationOrigin irDeclarationOrigin, int i, Object obj) {
        if ((i & 4) != 0) {
            irDeclarationOrigin = IrDeclarationOrigin.DEFINED.INSTANCE;
        }
        return addValueParameter(irFunction, name2, irType, irDeclarationOrigin);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static final IrTypeParameter buildTypeParameter(IrFactory irFactory, IrTypeParameterBuilder builder, IrDeclarationParent parent) {
        Intrinsics.checkNotNullParameter(irFactory, "<this>");
        Intrinsics.checkNotNullParameter(builder, "builder");
        Intrinsics.checkNotNullParameter(parent, "parent");
        IrTypeParameter irTypeParameterCreateTypeParameter = irFactory.createTypeParameter(builder.getStartOffset(), builder.getEndOffset(), builder.getOrigin(), builder.getName(), new IrTypeParameterSymbolImpl(null, 1, 0 == true ? 1 : 0), builder.getVariance(), builder.getIndex(), builder.getIsReified());
        irTypeParameterCreateTypeParameter.setSuperTypes(builder.getSuperTypes());
        irTypeParameterCreateTypeParameter.setParent(parent);
        return irTypeParameterCreateTypeParameter;
    }

    public static final IrTypeParameter buildTypeParameter(IrTypeParametersContainer parent, Function1<? super IrTypeParameterBuilder, Unit> builder) {
        Intrinsics.checkNotNullParameter(parent, "parent");
        Intrinsics.checkNotNullParameter(builder, "builder");
        IrTypeParameterBuilder irTypeParameterBuilder = new IrTypeParameterBuilder();
        builder.invoke(irTypeParameterBuilder);
        return buildTypeParameter(parent.getFactory(), irTypeParameterBuilder, parent);
    }

    public static final IrTypeParameter addTypeParameter(IrTypeParametersContainer irTypeParametersContainer, Function1<? super IrTypeParameterBuilder, Unit> builder) {
        Intrinsics.checkNotNullParameter(irTypeParametersContainer, "<this>");
        Intrinsics.checkNotNullParameter(builder, "builder");
        IrTypeParameterBuilder irTypeParameterBuilder = new IrTypeParameterBuilder();
        builder.invoke(irTypeParameterBuilder);
        if (irTypeParameterBuilder.getIndex() == -1) {
            irTypeParameterBuilder.setIndex(irTypeParametersContainer.getTypeParameters().size());
        }
        IrTypeParameter irTypeParameterBuildTypeParameter = buildTypeParameter(irTypeParametersContainer.getFactory(), irTypeParameterBuilder, irTypeParametersContainer);
        irTypeParametersContainer.setTypeParameters(CollectionsKt.plus((Collection<? extends IrTypeParameter>) irTypeParametersContainer.getTypeParameters(), irTypeParameterBuildTypeParameter));
        return irTypeParameterBuildTypeParameter;
    }

    public static /* synthetic */ IrTypeParameter addTypeParameter$default(IrTypeParametersContainer irTypeParametersContainer, String str, IrType irType, Variance variance, int i, Object obj) {
        if ((i & 4) != 0) {
            variance = Variance.INVARIANT;
        }
        return addTypeParameter(irTypeParametersContainer, str, irType, variance);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static final IrVariable buildVariable(IrDeclarationParent irDeclarationParent, int i, int i2, IrDeclarationOrigin origin, Name name2, IrType type, boolean z, boolean z2, boolean z3) {
        Intrinsics.checkNotNullParameter(origin, "origin");
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(type, "type");
        IrVariableImpl irVariableImpl = new IrVariableImpl(i, i2, origin, new IrVariableSymbolImpl(null, 1, 0 == true ? 1 : 0), name2, type, z, z2, z3);
        if (irDeclarationParent != null) {
            irVariableImpl.setParent(irDeclarationParent);
        }
        return irVariableImpl;
    }

    public static final IrField addField(IrClass irClass, Name fieldName, IrType fieldType, DescriptorVisibility fieldVisibility) {
        Intrinsics.checkNotNullParameter(irClass, "<this>");
        Intrinsics.checkNotNullParameter(fieldName, "fieldName");
        Intrinsics.checkNotNullParameter(fieldType, "fieldType");
        Intrinsics.checkNotNullParameter(fieldVisibility, "fieldVisibility");
        IrFactory factory = irClass.getFactory();
        IrFieldBuilder irFieldBuilder = new IrFieldBuilder();
        irFieldBuilder.setName(fieldName);
        irFieldBuilder.setType(fieldType);
        irFieldBuilder.setVisibility(fieldVisibility);
        IrField irFieldBuildField = buildField(factory, irFieldBuilder);
        irFieldBuildField.setParent(irClass);
        irClass.getDeclarations().add(irFieldBuildField);
        return irFieldBuildField;
    }

    public static final IrSimpleFunction addFunction(IrFactory irFactory, IrDeclarationContainer klass, Function1<? super IrFunctionBuilder, Unit> builder) {
        Intrinsics.checkNotNullParameter(irFactory, "<this>");
        Intrinsics.checkNotNullParameter(klass, "klass");
        Intrinsics.checkNotNullParameter(builder, "builder");
        IrFunctionBuilder irFunctionBuilder = new IrFunctionBuilder();
        builder.invoke(irFunctionBuilder);
        IrSimpleFunction irSimpleFunctionBuildFunction = buildFunction(irFactory, irFunctionBuilder);
        klass.getDeclarations().add(irSimpleFunctionBuildFunction);
        irSimpleFunctionBuildFunction.setParent(klass);
        return irSimpleFunctionBuildFunction;
    }

    public static final IrSimpleFunction addFunction(IrClass irClass, String name2, IrType returnType, Modality modality, DescriptorVisibility visibility, boolean z, boolean z2, boolean z3, boolean z4, IrDeclarationOrigin origin, int i, int i2) {
        Intrinsics.checkNotNullParameter(irClass, "<this>");
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(returnType, "returnType");
        Intrinsics.checkNotNullParameter(modality, "modality");
        Intrinsics.checkNotNullParameter(visibility, "visibility");
        Intrinsics.checkNotNullParameter(origin, "origin");
        IrFactory factory = irClass.getFactory();
        IrFunctionBuilder irFunctionBuilder = new IrFunctionBuilder();
        irFunctionBuilder.setStartOffset(i);
        irFunctionBuilder.setEndOffset(i2);
        Name nameIdentifier = Name.identifier(name2);
        Intrinsics.checkNotNullExpressionValue(nameIdentifier, "identifier(...)");
        irFunctionBuilder.setName(nameIdentifier);
        irFunctionBuilder.setReturnType(returnType);
        irFunctionBuilder.setModality(modality);
        irFunctionBuilder.setVisibility(visibility);
        irFunctionBuilder.setSuspend(z2);
        irFunctionBuilder.setFakeOverride(z3);
        irFunctionBuilder.setInline(z4);
        irFunctionBuilder.setOrigin(origin);
        IrSimpleFunction irSimpleFunctionBuildFunction = buildFunction(factory, irFunctionBuilder);
        IrClass irClass2 = irClass;
        irClass2.getDeclarations().add(irSimpleFunctionBuildFunction);
        irSimpleFunctionBuildFunction.setParent(irClass2);
        if (!z) {
            IrValueParameter thisReceiver = IrUtilsKt.getParentAsClass(irSimpleFunctionBuildFunction).getThisReceiver();
            Intrinsics.checkNotNull(thisReceiver);
            irSimpleFunctionBuildFunction.setDispatchReceiverParameter(IrUtilsKt.copyTo$default(thisReceiver, irSimpleFunctionBuildFunction, null, 0, 0, 0, null, null, thisReceiver.getType(), null, null, false, false, false, 8062, null));
        }
        return irSimpleFunctionBuildFunction;
    }

    public static final IrValueParameter addValueParameter(IrFunction irFunction, Name name2, IrType type, IrDeclarationOrigin origin) {
        Intrinsics.checkNotNullParameter(irFunction, "<this>");
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(type, "type");
        Intrinsics.checkNotNullParameter(origin, "origin");
        IrValueParameterBuilder irValueParameterBuilder = new IrValueParameterBuilder();
        irValueParameterBuilder.setName(name2);
        irValueParameterBuilder.setType(type);
        irValueParameterBuilder.setOrigin(origin);
        if (irValueParameterBuilder.getIndex() == -1) {
            irValueParameterBuilder.setIndex(irFunction.getValueParameters().size());
        }
        IrValueParameter irValueParameterBuildValueParameter = buildValueParameter(irFunction.getFactory(), irValueParameterBuilder, irFunction);
        irFunction.setValueParameters(CollectionsKt.plus((Collection<? extends IrValueParameter>) irFunction.getValueParameters(), irValueParameterBuildValueParameter));
        return irValueParameterBuildValueParameter;
    }

    public static final IrTypeParameter addTypeParameter(IrTypeParametersContainer irTypeParametersContainer, String name2, IrType upperBound, Variance variance) {
        Intrinsics.checkNotNullParameter(irTypeParametersContainer, "<this>");
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(upperBound, "upperBound");
        Intrinsics.checkNotNullParameter(variance, "variance");
        IrTypeParameterBuilder irTypeParameterBuilder = new IrTypeParameterBuilder();
        Name nameIdentifier = Name.identifier(name2);
        Intrinsics.checkNotNullExpressionValue(nameIdentifier, "identifier(...)");
        irTypeParameterBuilder.setName(nameIdentifier);
        irTypeParameterBuilder.setVariance(variance);
        irTypeParameterBuilder.getSuperTypes().add(upperBound);
        if (irTypeParameterBuilder.getIndex() == -1) {
            irTypeParameterBuilder.setIndex(irTypeParametersContainer.getTypeParameters().size());
        }
        IrTypeParameter irTypeParameterBuildTypeParameter = buildTypeParameter(irTypeParametersContainer.getFactory(), irTypeParameterBuilder, irTypeParametersContainer);
        irTypeParametersContainer.setTypeParameters(CollectionsKt.plus((Collection<? extends IrTypeParameter>) irTypeParametersContainer.getTypeParameters(), irTypeParameterBuildTypeParameter));
        return irTypeParameterBuildTypeParameter;
    }
}
