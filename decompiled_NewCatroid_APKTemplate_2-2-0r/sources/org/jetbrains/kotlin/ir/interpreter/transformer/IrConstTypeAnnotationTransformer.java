package org.jetbrains.kotlin.ir.interpreter.transformer;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.constant.EvaluatedConstTracker;
import org.jetbrains.kotlin.incremental.components.InlineConstTracker;
import org.jetbrains.kotlin.ir.IrElement;
import org.jetbrains.kotlin.ir.IrStatement;
import org.jetbrains.kotlin.ir.declarations.IrClass;
import org.jetbrains.kotlin.ir.declarations.IrField;
import org.jetbrains.kotlin.ir.declarations.IrFile;
import org.jetbrains.kotlin.ir.declarations.IrFunction;
import org.jetbrains.kotlin.ir.declarations.IrLocalDelegatedProperty;
import org.jetbrains.kotlin.ir.declarations.IrScript;
import org.jetbrains.kotlin.ir.declarations.IrTypeAlias;
import org.jetbrains.kotlin.ir.declarations.IrTypeParameter;
import org.jetbrains.kotlin.ir.declarations.IrValueParameter;
import org.jetbrains.kotlin.ir.declarations.IrVariable;
import org.jetbrains.kotlin.ir.expressions.IrClassReference;
import org.jetbrains.kotlin.ir.expressions.IrConstantObject;
import org.jetbrains.kotlin.ir.expressions.IrConstantValue;
import org.jetbrains.kotlin.ir.expressions.IrErrorExpression;
import org.jetbrains.kotlin.ir.expressions.IrExpression;
import org.jetbrains.kotlin.ir.expressions.IrMemberAccessExpression;
import org.jetbrains.kotlin.ir.expressions.IrTypeOperatorCall;
import org.jetbrains.kotlin.ir.expressions.IrVararg;
import org.jetbrains.kotlin.ir.interpreter.IrInterpreter;
import org.jetbrains.kotlin.ir.interpreter.checker.EvaluationMode;
import org.jetbrains.kotlin.ir.interpreter.checker.IrInterpreterChecker;
import org.jetbrains.kotlin.ir.types.IrSimpleType;
import org.jetbrains.kotlin.ir.types.IrType;
import org.jetbrains.kotlin.ir.types.IrTypeArgument;
import org.jetbrains.kotlin.ir.types.IrTypesKt;
import org.jetbrains.kotlin.ir.visitors.IrTypeTransformer;

/* JADX INFO: compiled from: IrConstTypeAnnotationTransformer.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000Z\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\b\u0000\u0018\u00002\u00020\u00012\b\u0012\u0004\u0012\u00020\u00030\u0002B\u0081\u0001\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\t\u0012\u0006\u0010\n\u001a\u00020\u000b\u0012\b\u0010\f\u001a\u0004\u0018\u00010\r\u0012\b\u0010\u000e\u001a\u0004\u0018\u00010\u000f\u0012\u001e\u0010\u0010\u001a\u001a\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020\u0012\u0012\u0004\u0012\u00020\u0013\u0012\u0004\u0012\u00020\u00140\u0011\u0012\u001e\u0010\u0015\u001a\u001a\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020\u0012\u0012\u0004\u0012\u00020\u0013\u0012\u0004\u0012\u00020\u00140\u0011\u0012\u0006\u0010\u0016\u001a\u00020\u0017¢\u0006\u0002\u0010\u0018J1\u0010\u0019\u001a\u0002H\u001a\"\n\b\u0000\u0010\u001a*\u0004\u0018\u00010\u001b2\u0006\u0010\u001c\u001a\u00020\u00122\u0006\u0010\u001d\u001a\u0002H\u001a2\u0006\u0010\u001e\u001a\u00020\u0003H\u0016¢\u0006\u0002\u0010\u001f¨\u0006 "}, d2 = {"Lorg/jetbrains/kotlin/ir/interpreter/transformer/IrConstTypeAnnotationTransformer;", "Lorg/jetbrains/kotlin/ir/interpreter/transformer/IrConstAnnotationTransformer;", "Lorg/jetbrains/kotlin/ir/visitors/IrTypeTransformer;", "Lorg/jetbrains/kotlin/ir/interpreter/transformer/IrConstTransformer$Data;", "interpreter", "Lorg/jetbrains/kotlin/ir/interpreter/IrInterpreter;", "irFile", "Lorg/jetbrains/kotlin/ir/declarations/IrFile;", "mode", "Lorg/jetbrains/kotlin/ir/interpreter/checker/EvaluationMode;", "checker", "Lorg/jetbrains/kotlin/ir/interpreter/checker/IrInterpreterChecker;", "evaluatedConstTracker", "Lorg/jetbrains/kotlin/constant/EvaluatedConstTracker;", "inlineConstTracker", "Lorg/jetbrains/kotlin/incremental/components/InlineConstTracker;", "onWarning", "Lkotlin/Function3;", "Lorg/jetbrains/kotlin/ir/IrElement;", "Lorg/jetbrains/kotlin/ir/expressions/IrErrorExpression;", "", "onError", "suppressExceptions", "", "(Lorg/jetbrains/kotlin/ir/interpreter/IrInterpreter;Lorg/jetbrains/kotlin/ir/declarations/IrFile;Lorg/jetbrains/kotlin/ir/interpreter/checker/EvaluationMode;Lorg/jetbrains/kotlin/ir/interpreter/checker/IrInterpreterChecker;Lorg/jetbrains/kotlin/constant/EvaluatedConstTracker;Lorg/jetbrains/kotlin/incremental/components/InlineConstTracker;Lkotlin/jvm/functions/Function3;Lkotlin/jvm/functions/Function3;Z)V", "transformType", "Type", "Lorg/jetbrains/kotlin/ir/types/IrType;", "container", ModuleXmlParser.TYPE, "data", "(Lorg/jetbrains/kotlin/ir/IrElement;Lorg/jetbrains/kotlin/ir/types/IrType;Lorg/jetbrains/kotlin/ir/interpreter/transformer/IrConstTransformer$Data;)Lorg/jetbrains/kotlin/ir/types/IrType;", "ir.interpreter"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class IrConstTypeAnnotationTransformer extends IrConstAnnotationTransformer implements IrTypeTransformer<IrConstTransformer.Data> {
    @Override // org.jetbrains.kotlin.ir.interpreter.transformer.IrConstTransformer, org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitClass */
    public IrStatement visitClass2(IrClass irClass, IrConstTransformer.Data data) {
        return IrTypeTransformer.DefaultImpls.visitClass(this, irClass, data);
    }

    @Override // org.jetbrains.kotlin.ir.interpreter.transformer.IrConstTransformer, org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitClassReference */
    public IrExpression visitClassReference2(IrClassReference irClassReference, IrConstTransformer.Data data) {
        return IrTypeTransformer.DefaultImpls.visitClassReference(this, irClassReference, data);
    }

    @Override // org.jetbrains.kotlin.ir.interpreter.transformer.IrConstTransformer, org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitConstantObject */
    public IrConstantValue visitConstantObject2(IrConstantObject irConstantObject, IrConstTransformer.Data data) {
        return IrTypeTransformer.DefaultImpls.visitConstantObject(this, irConstantObject, data);
    }

    @Override // org.jetbrains.kotlin.ir.interpreter.transformer.IrConstTransformer, org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitExpression */
    public IrExpression visitExpression2(IrExpression irExpression, IrConstTransformer.Data data) {
        return IrTypeTransformer.DefaultImpls.visitExpression(this, irExpression, data);
    }

    @Override // org.jetbrains.kotlin.ir.interpreter.transformer.IrConstTransformer, org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitField */
    public IrStatement visitField2(IrField irField, IrConstTransformer.Data data) {
        return IrTypeTransformer.DefaultImpls.visitField(this, irField, data);
    }

    @Override // org.jetbrains.kotlin.ir.interpreter.transformer.IrConstTransformer, org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitFunction */
    public IrStatement visitFunction2(IrFunction irFunction, IrConstTransformer.Data data) {
        return IrTypeTransformer.DefaultImpls.visitFunction(this, irFunction, data);
    }

    @Override // org.jetbrains.kotlin.ir.interpreter.transformer.IrConstTransformer, org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitLocalDelegatedProperty */
    public IrStatement visitLocalDelegatedProperty2(IrLocalDelegatedProperty irLocalDelegatedProperty, IrConstTransformer.Data data) {
        return IrTypeTransformer.DefaultImpls.visitLocalDelegatedProperty(this, irLocalDelegatedProperty, data);
    }

    @Override // org.jetbrains.kotlin.ir.interpreter.transformer.IrConstTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    public /* bridge */ /* synthetic */ IrElement visitMemberAccess(IrMemberAccessExpression irMemberAccessExpression, Object obj) {
        return visitMemberAccess((IrMemberAccessExpression<?>) irMemberAccessExpression, (IrConstTransformer.Data) obj);
    }

    @Override // org.jetbrains.kotlin.ir.interpreter.transformer.IrConstTransformer, org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitMemberAccess, reason: avoid collision after fix types in other method */
    public /* bridge */ /* synthetic */ IrElement visitMemberAccess2(IrMemberAccessExpression irMemberAccessExpression, Object obj) {
        return visitMemberAccess((IrMemberAccessExpression<?>) irMemberAccessExpression, (IrConstTransformer.Data) obj);
    }

    @Override // org.jetbrains.kotlin.ir.interpreter.transformer.IrConstTransformer
    public IrElement visitMemberAccess(IrMemberAccessExpression<?> irMemberAccessExpression, IrConstTransformer.Data data) {
        return IrTypeTransformer.DefaultImpls.visitMemberAccess(this, irMemberAccessExpression, data);
    }

    @Override // org.jetbrains.kotlin.ir.interpreter.transformer.IrConstTransformer, org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitScript */
    public IrStatement visitScript2(IrScript irScript, IrConstTransformer.Data data) {
        return IrTypeTransformer.DefaultImpls.visitScript(this, irScript, data);
    }

    @Override // org.jetbrains.kotlin.ir.interpreter.transformer.IrConstTransformer, org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitTypeAlias */
    public IrStatement visitTypeAlias2(IrTypeAlias irTypeAlias, IrConstTransformer.Data data) {
        return IrTypeTransformer.DefaultImpls.visitTypeAlias(this, irTypeAlias, data);
    }

    @Override // org.jetbrains.kotlin.ir.interpreter.transformer.IrConstTransformer, org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitTypeOperator */
    public IrExpression visitTypeOperator2(IrTypeOperatorCall irTypeOperatorCall, IrConstTransformer.Data data) {
        return IrTypeTransformer.DefaultImpls.visitTypeOperator(this, irTypeOperatorCall, data);
    }

    @Override // org.jetbrains.kotlin.ir.interpreter.transformer.IrConstTransformer, org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitTypeParameter */
    public IrStatement visitTypeParameter2(IrTypeParameter irTypeParameter, IrConstTransformer.Data data) {
        return IrTypeTransformer.DefaultImpls.visitTypeParameter(this, irTypeParameter, data);
    }

    @Override // org.jetbrains.kotlin.ir.interpreter.transformer.IrConstTransformer, org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitValueParameter */
    public IrStatement visitValueParameter2(IrValueParameter irValueParameter, IrConstTransformer.Data data) {
        return IrTypeTransformer.DefaultImpls.visitValueParameter(this, irValueParameter, data);
    }

    @Override // org.jetbrains.kotlin.ir.interpreter.transformer.IrConstTransformer, org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitVararg */
    public IrExpression visitVararg2(IrVararg irVararg, IrConstTransformer.Data data) {
        return IrTypeTransformer.DefaultImpls.visitVararg(this, irVararg, data);
    }

    @Override // org.jetbrains.kotlin.ir.interpreter.transformer.IrConstTransformer, org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitVariable */
    public IrStatement visitVariable2(IrVariable irVariable, IrConstTransformer.Data data) {
        return IrTypeTransformer.DefaultImpls.visitVariable(this, irVariable, data);
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public IrConstTypeAnnotationTransformer(IrInterpreter interpreter, IrFile irFile, EvaluationMode mode, IrInterpreterChecker checker, EvaluatedConstTracker evaluatedConstTracker, InlineConstTracker inlineConstTracker, Function3<? super IrFile, ? super IrElement, ? super IrErrorExpression, Unit> onWarning, Function3<? super IrFile, ? super IrElement, ? super IrErrorExpression, Unit> onError, boolean z) {
        super(interpreter, irFile, mode, checker, evaluatedConstTracker, inlineConstTracker, onWarning, onError, z);
        Intrinsics.checkNotNullParameter(interpreter, "interpreter");
        Intrinsics.checkNotNullParameter(irFile, "irFile");
        Intrinsics.checkNotNullParameter(mode, "mode");
        Intrinsics.checkNotNullParameter(checker, "checker");
        Intrinsics.checkNotNullParameter(onWarning, "onWarning");
        Intrinsics.checkNotNullParameter(onError, "onError");
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // org.jetbrains.kotlin.ir.visitors.IrTypeTransformer
    public <Type extends IrType> Type transformType(IrElement container, Type type, IrConstTransformer.Data data) {
        Intrinsics.checkNotNullParameter(container, "container");
        Intrinsics.checkNotNullParameter(data, "data");
        if (type == null) {
            return type;
        }
        transformAnnotations(type);
        if (type instanceof IrSimpleType) {
            List<IrTypeArgument> arguments = ((IrSimpleType) type).getArguments();
            ArrayList arrayList = new ArrayList();
            Iterator<T> it = arguments.iterator();
            while (it.hasNext()) {
                IrType typeOrNull = IrTypesKt.getTypeOrNull((IrTypeArgument) it.next());
                if (typeOrNull != null) {
                    arrayList.add(typeOrNull);
                }
            }
            Iterator it2 = arrayList.iterator();
            while (it2.hasNext()) {
                transformType(container, (IrType) it2.next(), data);
            }
        }
        return type;
    }
}
