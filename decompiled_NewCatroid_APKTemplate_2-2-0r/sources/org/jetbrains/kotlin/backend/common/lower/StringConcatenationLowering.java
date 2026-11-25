package org.jetbrains.kotlin.backend.common.lower;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import kotlin.Metadata;
import kotlin.TuplesKt;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.sequences.SequencesKt;
import org.jetbrains.kotlin.backend.common.CollectionUtilKt;
import org.jetbrains.kotlin.backend.common.CommonBackendContext;
import org.jetbrains.kotlin.backend.common.FileLoweringPass;
import org.jetbrains.kotlin.backend.common.ir.Symbols;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.ir.IrBuiltIns;
import org.jetbrains.kotlin.ir.builders.ExpressionHelpersKt;
import org.jetbrains.kotlin.ir.builders.IrBlockBuilder;
import org.jetbrains.kotlin.ir.builders.IrBuilderKt;
import org.jetbrains.kotlin.ir.builders.IrBuilderWithScope;
import org.jetbrains.kotlin.ir.builders.IrStatementsBuilder;
import org.jetbrains.kotlin.ir.declarations.IrClass;
import org.jetbrains.kotlin.ir.declarations.IrConstructor;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationOrigin;
import org.jetbrains.kotlin.ir.declarations.IrFile;
import org.jetbrains.kotlin.ir.declarations.IrSimpleFunction;
import org.jetbrains.kotlin.ir.declarations.IrValueParameter;
import org.jetbrains.kotlin.ir.declarations.IrVariable;
import org.jetbrains.kotlin.ir.expressions.IrCall;
import org.jetbrains.kotlin.ir.expressions.IrExpression;
import org.jetbrains.kotlin.ir.expressions.IrFunctionAccessExpression;
import org.jetbrains.kotlin.ir.expressions.IrStringConcatenation;
import org.jetbrains.kotlin.ir.types.IrType;
import org.jetbrains.kotlin.ir.types.IrTypePredicatesKt;
import org.jetbrains.kotlin.ir.types.IrTypeUtilsKt;
import org.jetbrains.kotlin.ir.util.IrUtilsKt;
import org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoidKt;
import org.jetbrains.kotlin.load.java.JvmAbi;
import org.jetbrains.kotlin.name.Name;

/* JADX INFO: compiled from: StringConcatenationLowering.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000d\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u00012\u00020\u0002B\r\u0012\u0006\u0010\u0003\u001a\u00020\u0004¢\u0006\u0002\u0010\u0005J\u0010\u0010\u0017\u001a\u00020\u00182\u0006\u0010\u0019\u001a\u00020\u001aH\u0016J\u0010\u0010\u001b\u001a\u00020\t2\u0006\u0010\u001c\u001a\u00020\bH\u0002J\u0010\u0010\u001d\u001a\u00020\u001e2\u0006\u0010\u001f\u001a\u00020 H\u0016R\u001c\u0010\u0006\u001a\u0010\u0012\u0004\u0012\u00020\b\u0012\u0006\u0012\u0004\u0018\u00010\t0\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u000bX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\tX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\u000eX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u000f\u001a\u00020\u0010X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0011\u001a\u00020\u0012X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0013\u001a\u00020\u0014X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u0015\u001a\b\u0012\u0004\u0012\u00020\b0\u0016X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006!"}, d2 = {"Lorg/jetbrains/kotlin/backend/common/lower/StringConcatenationLowering;", "Lorg/jetbrains/kotlin/backend/common/FileLoweringPass;", "Lorg/jetbrains/kotlin/backend/common/lower/IrBuildingTransformer;", "context", "Lorg/jetbrains/kotlin/backend/common/CommonBackendContext;", "(Lorg/jetbrains/kotlin/backend/common/CommonBackendContext;)V", "appendFunctions", "", "Lorg/jetbrains/kotlin/ir/types/IrType;", "Lorg/jetbrains/kotlin/ir/declarations/IrSimpleFunction;", JvmAbi.ERASED_INLINE_CONSTRUCTOR_NAME, "Lorg/jetbrains/kotlin/ir/declarations/IrConstructor;", "defaultAppendFunction", "irBuiltIns", "Lorg/jetbrains/kotlin/ir/IrBuiltIns;", "nameAppend", "Lorg/jetbrains/kotlin/name/Name;", "stringBuilder", "Lorg/jetbrains/kotlin/ir/declarations/IrClass;", "symbols", "Lorg/jetbrains/kotlin/backend/common/ir/Symbols;", "typesWithSpecialAppendFunction", "", "lower", "", "irFile", "Lorg/jetbrains/kotlin/ir/declarations/IrFile;", "typeToAppendFunction", ModuleXmlParser.TYPE, "visitStringConcatenation", "Lorg/jetbrains/kotlin/ir/expressions/IrExpression;", "expression", "Lorg/jetbrains/kotlin/ir/expressions/IrStringConcatenation;", "ir.backend.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class StringConcatenationLowering extends IrBuildingTransformer implements FileLoweringPass {
    private final Map<IrType, IrSimpleFunction> appendFunctions;
    private final IrConstructor constructor;
    private final IrSimpleFunction defaultAppendFunction;
    private final IrBuiltIns irBuiltIns;
    private final Name nameAppend;
    private final IrClass stringBuilder;
    private final Symbols symbols;
    private final List<IrType> typesWithSpecialAppendFunction;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public StringConcatenationLowering(CommonBackendContext context) {
        super(context);
        Intrinsics.checkNotNullParameter(context, "context");
        IrBuiltIns irBuiltIns = context.getIrBuiltIns();
        this.irBuiltIns = irBuiltIns;
        this.symbols = context.getIr().getSymbols();
        this.typesWithSpecialAppendFunction = CollectionsKt.plus((Collection<? extends IrType>) irBuiltIns.getPrimitiveIrTypes(), irBuiltIns.getStringType());
        Name nameIdentifier = Name.identifier("append");
        Intrinsics.checkNotNullExpressionValue(nameIdentifier, "identifier(...)");
        this.nameAppend = nameIdentifier;
        IrClass owner = context.getIr().getSymbols().getStringBuilder().getOwner();
        this.stringBuilder = owner;
        boolean z = false;
        IrConstructor irConstructor = null;
        boolean z2 = false;
        for (IrConstructor irConstructor2 : IrUtilsKt.getConstructors(owner)) {
            if (irConstructor2.getValueParameters().isEmpty()) {
                if (z2) {
                    throw new IllegalArgumentException("Sequence contains more than one matching element.");
                }
                irConstructor = irConstructor2;
                z2 = true;
            }
        }
        if (z2) {
            this.constructor = irConstructor;
            IrSimpleFunction irSimpleFunction = null;
            for (IrSimpleFunction irSimpleFunction2 : IrUtilsKt.getFunctions(this.stringBuilder)) {
                IrSimpleFunction irSimpleFunction3 = irSimpleFunction2;
                if (Intrinsics.areEqual(irSimpleFunction3.getName(), this.nameAppend) && irSimpleFunction3.getValueParameters().size() == 1 && IrTypePredicatesKt.isNullableAny(((IrValueParameter) CollectionsKt.single((List) irSimpleFunction3.getValueParameters())).getType())) {
                    if (z) {
                        throw new IllegalArgumentException("Sequence contains more than one matching element.");
                    }
                    irSimpleFunction = irSimpleFunction2;
                    z = true;
                }
            }
            if (z) {
                this.defaultAppendFunction = irSimpleFunction;
                List<IrType> list = this.typesWithSpecialAppendFunction;
                ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(list, 10));
                for (IrType irType : list) {
                    List list2 = SequencesKt.toList(IrUtilsKt.getFunctions(this.stringBuilder));
                    ArrayList arrayList2 = new ArrayList();
                    for (Object obj : list2) {
                        IrSimpleFunction irSimpleFunction4 = (IrSimpleFunction) obj;
                        if (Intrinsics.areEqual(irSimpleFunction4.getName(), this.nameAppend)) {
                            IrValueParameter irValueParameter = (IrValueParameter) CollectionsKt.singleOrNull((List) irSimpleFunction4.getValueParameters());
                            if (Intrinsics.areEqual(irValueParameter != null ? irValueParameter.getType() : null, irType)) {
                                arrayList2.add(obj);
                            }
                        }
                    }
                    arrayList.add(TuplesKt.to(irType, CollectionUtilKt.atMostOne(arrayList2)));
                }
                this.appendFunctions = MapsKt.toMap(arrayList);
                return;
            }
            throw new NoSuchElementException("Sequence contains no element matching the predicate.");
        }
        throw new NoSuchElementException("Sequence contains no element matching the predicate.");
    }

    @Override // org.jetbrains.kotlin.backend.common.FileLoweringPass
    public void lower(IrFile irFile) {
        Intrinsics.checkNotNullParameter(irFile, "irFile");
        IrElementTransformerVoidKt.transformChildrenVoid(irFile, this);
    }

    private final IrSimpleFunction typeToAppendFunction(IrType type) {
        IrSimpleFunction irSimpleFunction = this.appendFunctions.get(type);
        return irSimpleFunction == null ? this.defaultAppendFunction : irSimpleFunction;
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
    public IrExpression visitStringConcatenation(IrStringConcatenation expression) {
        IrCall irCall;
        IrCall irCall2;
        Intrinsics.checkNotNullParameter(expression, "expression");
        IrStringConcatenation irStringConcatenation = expression;
        IrElementTransformerVoidKt.transformChildrenVoid(irStringConcatenation, this);
        LowerUtilsKt.at(getBuilder(), irStringConcatenation);
        List<IrExpression> arguments = expression.getArguments();
        if (arguments.isEmpty()) {
            return ExpressionHelpersKt.irString(getBuilder(), "");
        }
        if (arguments.size() == 1) {
            IrExpression irExpression = arguments.get(0);
            if (IrTypeUtilsKt.isNullable(irExpression.getType())) {
                irCall2 = ExpressionHelpersKt.irCall(getBuilder(), this.symbols.getExtensionToString());
                irCall2.setExtensionReceiver(irExpression);
            } else {
                irCall2 = ExpressionHelpersKt.irCall(getBuilder(), this.symbols.getMemberToString());
                irCall2.setDispatchReceiver(irExpression);
            }
            return irCall2;
        }
        if (arguments.size() == 2 && IrTypePredicatesKt.isStringClassType(arguments.get(0).getType())) {
            if (IrTypeUtilsKt.isNullable(arguments.get(0).getType())) {
                irCall = ExpressionHelpersKt.irCall(getBuilder(), this.symbols.getExtensionStringPlus());
                irCall.setExtensionReceiver(arguments.get(0));
                irCall.putValueArgument(0, arguments.get(1));
            } else {
                irCall = ExpressionHelpersKt.irCall(getBuilder(), this.symbols.getMemberStringPlus());
                irCall.setDispatchReceiver(arguments.get(0));
                irCall.putValueArgument(0, arguments.get(1));
            }
            return irCall;
        }
        IrBuilderWithScope builder = getBuilder();
        IrStringConcatenation irStringConcatenation2 = expression;
        IrType type = irStringConcatenation2.getType();
        IrBlockBuilder irBlockBuilder = new IrBlockBuilder(builder.getContext(), builder.getScope(), irStringConcatenation2.getStartOffset(), irStringConcatenation2.getEndOffset(), null, type, false, 64, null);
        IrBlockBuilder irBlockBuilder2 = irBlockBuilder;
        IrVariable irVariableCreateTmpVariable$default = IrBuilderKt.createTmpVariable$default((IrStatementsBuilder) irBlockBuilder, (IrExpression) ExpressionHelpersKt.irCall(irBlockBuilder2, this.constructor), (String) null, false, (IrDeclarationOrigin) null, (IrType) null, 30, (Object) null);
        for (IrExpression irExpression2 : expression.getArguments()) {
            IrFunctionAccessExpression irFunctionAccessExpressionIrCall = ExpressionHelpersKt.irCall(irBlockBuilder2, typeToAppendFunction(irExpression2.getType()));
            irFunctionAccessExpressionIrCall.setDispatchReceiver(ExpressionHelpersKt.irGet(irBlockBuilder2, irVariableCreateTmpVariable$default));
            irFunctionAccessExpressionIrCall.putValueArgument(0, irExpression2);
            irBlockBuilder.unaryPlus(irFunctionAccessExpressionIrCall);
        }
        IrCall irCall3 = ExpressionHelpersKt.irCall((IrBuilderWithScope) irBlockBuilder2, this.symbols.getMemberToString());
        irCall3.setDispatchReceiver(ExpressionHelpersKt.irGet(irBlockBuilder2, irVariableCreateTmpVariable$default));
        irBlockBuilder.unaryPlus(irCall3);
        return irBlockBuilder.getIrBlockBody();
    }
}
