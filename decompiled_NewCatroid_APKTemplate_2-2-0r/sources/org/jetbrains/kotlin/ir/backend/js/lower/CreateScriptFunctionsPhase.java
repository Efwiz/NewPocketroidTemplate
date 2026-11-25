package org.jetbrains.kotlin.ir.backend.js.lower;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Predicate;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.sequences.Sequence;
import kotlin.sequences.SequencesKt;
import org.jetbrains.kotlin.backend.common.CommonBackendContext;
import org.jetbrains.kotlin.backend.common.FileLoweringPass;
import org.jetbrains.kotlin.descriptors.DescriptorVisibilities;
import org.jetbrains.kotlin.descriptors.DescriptorVisibility;
import org.jetbrains.kotlin.ir.IrStatement;
import org.jetbrains.kotlin.ir.builders.declarations.DeclarationBuildersKt;
import org.jetbrains.kotlin.ir.builders.declarations.IrFunctionBuilder;
import org.jetbrains.kotlin.ir.declarations.IrDeclaration;
import org.jetbrains.kotlin.ir.declarations.IrFactory;
import org.jetbrains.kotlin.ir.declarations.IrFactoryHelpersKt;
import org.jetbrains.kotlin.ir.declarations.IrField;
import org.jetbrains.kotlin.ir.declarations.IrFile;
import org.jetbrains.kotlin.ir.declarations.IrFunction;
import org.jetbrains.kotlin.ir.declarations.IrProperty;
import org.jetbrains.kotlin.ir.declarations.IrScript;
import org.jetbrains.kotlin.ir.declarations.IrSimpleFunction;
import org.jetbrains.kotlin.ir.expressions.IrCall;
import org.jetbrains.kotlin.ir.expressions.IrExpression;
import org.jetbrains.kotlin.ir.expressions.IrExpressionBody;
import org.jetbrains.kotlin.ir.expressions.IrSetField;
import org.jetbrains.kotlin.ir.expressions.impl.IrCallImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrReturnImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrSetFieldImpl;
import org.jetbrains.kotlin.ir.symbols.IrFunctionSymbol;
import org.jetbrains.kotlin.ir.types.IrType;
import org.jetbrains.kotlin.ir.util.PatchDeclarationParentsKt;
import org.jetbrains.kotlin.ir.util.TransformKt;
import org.jetbrains.kotlin.name.Name;

/* JADX INFO: compiled from: CreateScriptFunctionsPhase.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000p\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0010\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH\u0002J \u0010\u000b\u001a\u00020\n2\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u0011H\u0002J\u0018\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\u00152\u0006\u0010\u0016\u001a\u00020\u0017H\u0002J\u001c\u0010\u0018\u001a\u000e\u0012\u0004\u0012\u00020\u001a\u0012\u0004\u0012\u00020\u001a0\u00192\u0006\u0010\f\u001a\u00020\rH\u0002J\u0010\u0010\u001b\u001a\u00020\u00112\u0006\u0010\f\u001a\u00020\rH\u0002J\u0010\u0010\u001c\u001a\u00020\u001d2\u0006\u0010\u001e\u001a\u00020\u001fH\u0016J\u0016\u0010\u001c\u001a\b\u0012\u0004\u0012\u00020!0 2\u0006\u0010\f\u001a\u00020\rH\u0002J \u0010\"\u001a\b\u0012\u0004\u0012\u00020#0 *\b\u0012\u0004\u0012\u00020#0 2\u0006\u0010$\u001a\u00020%H\u0002R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006&"}, d2 = {"Lorg/jetbrains/kotlin/ir/backend/js/lower/CreateScriptFunctionsPhase;", "Lorg/jetbrains/kotlin/backend/common/FileLoweringPass;", "context", "Lorg/jetbrains/kotlin/backend/common/CommonBackendContext;", "(Lorg/jetbrains/kotlin/backend/common/CommonBackendContext;)V", "getContext", "()Lorg/jetbrains/kotlin/backend/common/CommonBackendContext;", "createCall", "Lorg/jetbrains/kotlin/ir/expressions/IrCall;", "function", "Lorg/jetbrains/kotlin/ir/declarations/IrSimpleFunction;", "createFunction", "irScript", "Lorg/jetbrains/kotlin/ir/declarations/IrScript;", "name", "", "returnType", "Lorg/jetbrains/kotlin/ir/types/IrType;", "createIrSetField", "Lorg/jetbrains/kotlin/ir/expressions/IrSetField;", "field", "Lorg/jetbrains/kotlin/ir/declarations/IrField;", "expression", "Lorg/jetbrains/kotlin/ir/expressions/IrExpression;", "getFunctionBodyOffsets", "Lkotlin/Pair;", "", "getReturnType", "lower", "", "irFile", "Lorg/jetbrains/kotlin/ir/declarations/IrFile;", "", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclaration;", "prepareForEvaluateScriptFunction", "Lorg/jetbrains/kotlin/ir/IrStatement;", "evaluateScriptFunction", "Lorg/jetbrains/kotlin/ir/declarations/IrFunction;", "backend.js"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class CreateScriptFunctionsPhase implements FileLoweringPass {
    private final CommonBackendContext context;

    public CreateScriptFunctionsPhase(CommonBackendContext context) {
        Intrinsics.checkNotNullParameter(context, "context");
        this.context = context;
    }

    public final CommonBackendContext getContext() {
        return this.context;
    }

    @Override // org.jetbrains.kotlin.backend.common.FileLoweringPass
    public void lower(IrFile irFile) {
        Intrinsics.checkNotNullParameter(irFile, "irFile");
        List<IrDeclaration> declarations = irFile.getDeclarations();
        int iReplaceInPlace = 0;
        while (iReplaceInPlace < declarations.size()) {
            IrDeclaration irDeclaration = declarations.get(iReplaceInPlace);
            iReplaceInPlace = TransformKt.replaceInPlace(declarations, irDeclaration instanceof IrScript ? lower((IrScript) irDeclaration) : null, iReplaceInPlace);
        }
    }

    /* JADX WARN: Code duplicated, block: B:14:0x0094  */
    private final List<IrDeclaration> lower(IrScript irScript) {
        List listDropLast;
        IrField irField;
        Pair<Integer, Integer> functionBodyOffsets = getFunctionBodyOffsets(irScript);
        int iIntValue = functionBodyOffsets.component1().intValue();
        int iIntValue2 = functionBodyOffsets.component2().intValue();
        Sequence sequenceFilter = SequencesKt.filter(CollectionsKt.asSequence(irScript.getStatements()), new Function1<Object, Boolean>() { // from class: org.jetbrains.kotlin.ir.backend.js.lower.CreateScriptFunctionsPhase$lower$$inlined$filterIsInstance$1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // kotlin.jvm.functions.Function1
            public final Boolean invoke(Object obj) {
                return Boolean.valueOf(obj instanceof IrProperty);
            }
        });
        Intrinsics.checkNotNull(sequenceFilter, "null cannot be cast to non-null type kotlin.sequences.Sequence<R of kotlin.sequences.SequencesKt___SequencesKt.filterIsInstance>");
        List list = SequencesKt.toList(SequencesKt.onEach(SequencesKt.map(SequencesKt.filter(SequencesKt.mapNotNull(sequenceFilter, new Function1<IrProperty, IrField>() { // from class: org.jetbrains.kotlin.ir.backend.js.lower.CreateScriptFunctionsPhase$lower$initializeStatements$1
            @Override // kotlin.jvm.functions.Function1
            public final IrField invoke(IrProperty it) {
                Intrinsics.checkNotNullParameter(it, "it");
                return it.getBackingField();
            }
        }), new Function1<IrField, Boolean>() { // from class: org.jetbrains.kotlin.ir.backend.js.lower.CreateScriptFunctionsPhase$lower$initializeStatements$2
            @Override // kotlin.jvm.functions.Function1
            public final Boolean invoke(IrField it) {
                Intrinsics.checkNotNullParameter(it, "it");
                return Boolean.valueOf(it.getInitializer() != null);
            }
        }), new Function1<IrField, Pair<? extends IrField, ? extends IrExpression>>() { // from class: org.jetbrains.kotlin.ir.backend.js.lower.CreateScriptFunctionsPhase$lower$initializeStatements$3
            @Override // kotlin.jvm.functions.Function1
            public final Pair<IrField, IrExpression> invoke(IrField it) {
                Intrinsics.checkNotNullParameter(it, "it");
                IrExpressionBody initializer = it.getInitializer();
                Intrinsics.checkNotNull(initializer);
                return new Pair<>(it, initializer.getExpression());
            }
        }), new Function1<Pair<? extends IrField, ? extends IrExpression>, Unit>() { // from class: org.jetbrains.kotlin.ir.backend.js.lower.CreateScriptFunctionsPhase$lower$initializeStatements$4
            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(Pair<? extends IrField, ? extends IrExpression> pair) {
                invoke2(pair);
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(Pair<? extends IrField, ? extends IrExpression> it) {
                Intrinsics.checkNotNullParameter(it, "it");
                it.getFirst().setInitializer(null);
            }
        }));
        IrSimpleFunction irSimpleFunctionCreateFunction = createFunction(irScript, "$initializeScript$", this.context.getIrBuiltIns().getUnitType());
        IrFactory factory = irSimpleFunctionCreateFunction.getFactory();
        if (irScript.getResultProperty() == null) {
            listDropLast = list;
        } else {
            Pair pair = (Pair) CollectionsKt.lastOrNull(list);
            if (Intrinsics.areEqual((pair == null || (irField = (IrField) pair.getFirst()) == null) ? null : irField.getCorrespondingPropertySymbol(), irScript.getResultProperty())) {
                listDropLast = CollectionsKt.dropLast(list, 1);
            } else {
                listDropLast = list;
            }
        }
        List<Pair> list2 = listDropLast;
        ArrayList arrayList = new ArrayList(list2.size());
        for (Pair pair2 : list2) {
            arrayList.add(createIrSetField((IrField) pair2.component1(), (IrExpression) pair2.component2()));
        }
        irSimpleFunctionCreateFunction.setBody(IrFactoryHelpersKt.createBlockBody(factory, iIntValue, iIntValue2, (List<? extends IrStatement>) org.jetbrains.kotlin.utils.CollectionsKt.compactIfPossible(arrayList)));
        IrSimpleFunction irSimpleFunctionCreateFunction2 = createFunction(irScript, "$evaluateScript$", getReturnType(irScript));
        IrFactory factory2 = irSimpleFunctionCreateFunction2.getFactory();
        List<IrStatement> statements = irScript.getStatements();
        ArrayList arrayList2 = new ArrayList();
        for (Object obj : statements) {
            if (!(((IrStatement) obj) instanceof IrDeclaration)) {
                arrayList2.add(obj);
            }
        }
        ArrayList arrayListPlus = arrayList2;
        Pair pair3 = (Pair) CollectionsKt.lastOrNull(list);
        if (pair3 != null && irScript.getResultProperty() != null && Intrinsics.areEqual(((IrField) pair3.getFirst()).getCorrespondingPropertySymbol(), irScript.getResultProperty())) {
            arrayListPlus = CollectionsKt.plus((Collection<? extends Object>) arrayListPlus, pair3.getSecond());
        }
        irSimpleFunctionCreateFunction2.setBody(IrFactoryHelpersKt.createBlockBody(factory2, iIntValue, iIntValue2, prepareForEvaluateScriptFunction(arrayListPlus, irSimpleFunctionCreateFunction2)));
        List<IrStatement> statements2 = irScript.getStatements();
        final CreateScriptFunctionsPhase$lower$2$1 createScriptFunctionsPhase$lower$2$1 = new Function1<IrStatement, Boolean>() { // from class: org.jetbrains.kotlin.ir.backend.js.lower.CreateScriptFunctionsPhase$lower$2$1
            @Override // kotlin.jvm.functions.Function1
            public final Boolean invoke(IrStatement it) {
                Intrinsics.checkNotNullParameter(it, "it");
                return Boolean.valueOf(!(it instanceof IrDeclaration));
            }
        };
        statements2.removeIf(new Predicate() { // from class: org.jetbrains.kotlin.ir.backend.js.lower.CreateScriptFunctionsPhase$$ExternalSyntheticLambda0
            @Override // java.util.function.Predicate
            public final boolean test(Object obj2) {
                return CreateScriptFunctionsPhase.lower$lambda$8$lambda$7(createScriptFunctionsPhase$lower$2$1, obj2);
            }
        });
        irScript.getStatements().add(irSimpleFunctionCreateFunction);
        IrScript irScript2 = irScript;
        PatchDeclarationParentsKt.patchDeclarationParents(irSimpleFunctionCreateFunction, irScript2);
        irScript.getStatements().add(irSimpleFunctionCreateFunction2);
        PatchDeclarationParentsKt.patchDeclarationParents(irSimpleFunctionCreateFunction2, irScript2);
        irScript.getStatements().add(createCall(irSimpleFunctionCreateFunction));
        irScript.getStatements().add(createCall(irSimpleFunctionCreateFunction2));
        return CollectionsKt.listOf(irScript);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final boolean lower$lambda$8$lambda$7(Function1 tmp0, Object obj) {
        Intrinsics.checkNotNullParameter(tmp0, "$tmp0");
        return ((Boolean) tmp0.invoke(obj)).booleanValue();
    }

    private final Pair<Integer, Integer> getFunctionBodyOffsets(IrScript irScript) {
        if (irScript.getStatements().isEmpty()) {
            return new Pair<>(-1, -1);
        }
        return new Pair<>(Integer.valueOf(((IrStatement) CollectionsKt.first((List) irScript.getStatements())).getStartOffset()), Integer.valueOf(((IrStatement) CollectionsKt.last((List) irScript.getStatements())).getEndOffset()));
    }

    private final IrType getReturnType(IrScript irScript) {
        IrType type;
        Object objLastOrNull = CollectionsKt.lastOrNull((List<? extends Object>) irScript.getStatements());
        IrExpression irExpression = objLastOrNull instanceof IrExpression ? (IrExpression) objLastOrNull : null;
        return (irExpression == null || (type = irExpression.getType()) == null) ? this.context.getIrBuiltIns().getUnitType() : type;
    }

    private final IrSimpleFunction createFunction(IrScript irScript, String name2, IrType returnType) {
        IrFactory irFactory = this.context.getIrFactory();
        IrFunctionBuilder irFunctionBuilder = new IrFunctionBuilder();
        Pair<Integer, Integer> functionBodyOffsets = getFunctionBodyOffsets(irScript);
        int iIntValue = functionBodyOffsets.component1().intValue();
        int iIntValue2 = functionBodyOffsets.component2().intValue();
        irFunctionBuilder.setStartOffset(iIntValue);
        irFunctionBuilder.setEndOffset(iIntValue2);
        irFunctionBuilder.setOrigin(SCRIPT_FUNCTION.INSTANCE);
        Name nameIdentifier = Name.identifier(name2);
        Intrinsics.checkNotNullExpressionValue(nameIdentifier, "identifier(...)");
        irFunctionBuilder.setName(nameIdentifier);
        DescriptorVisibility PRIVATE = DescriptorVisibilities.PRIVATE;
        Intrinsics.checkNotNullExpressionValue(PRIVATE, "PRIVATE");
        irFunctionBuilder.setVisibility(PRIVATE);
        irFunctionBuilder.setReturnType(returnType);
        IrSimpleFunction irSimpleFunctionBuildFunction = DeclarationBuildersKt.buildFunction(irFactory, irFunctionBuilder);
        irSimpleFunctionBuildFunction.setParent(irScript);
        return irSimpleFunctionBuildFunction;
    }

    private final List<IrStatement> prepareForEvaluateScriptFunction(List<? extends IrStatement> list, IrFunction irFunction) {
        if (!list.isEmpty()) {
            int startOffset = ((IrStatement) CollectionsKt.last((List) list)).getStartOffset();
            int endOffset = ((IrStatement) CollectionsKt.last((List) list)).getEndOffset();
            IrType nothingType = this.context.getIrBuiltIns().getNothingType();
            IrFunctionSymbol symbol = irFunction.getSymbol();
            Object objLast = CollectionsKt.last((List<? extends Object>) list);
            Intrinsics.checkNotNull(objLast, "null cannot be cast to non-null type org.jetbrains.kotlin.ir.expressions.IrExpression");
            return CollectionsKt.plus((Collection<? extends IrReturnImpl>) CollectionsKt.dropLast(list, 1), new IrReturnImpl(startOffset, endOffset, nothingType, symbol, (IrExpression) objLast));
        }
        return CollectionsKt.emptyList();
    }

    private final IrSetField createIrSetField(IrField field, IrExpression expression) {
        return new IrSetFieldImpl(field.getStartOffset(), field.getEndOffset(), field.getSymbol(), null, expression, expression.getType(), null, null, 192, null);
    }

    private final IrCall createCall(IrSimpleFunction function) {
        return new IrCallImpl(-1, -1, function.getReturnType(), function.getSymbol(), function.getTypeParameters().size(), function.getValueParameters().size(), null, null, 192, null);
    }
}
