package org.jetbrains.kotlin.backend.common.ir;

import com.google.firebase.analytics.FirebaseAnalytics;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.NoSuchElementException;
import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.Pair;
import kotlin.collections.CollectionsKt;
import kotlin.collections.IntIterator;
import kotlin.collections.MapsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt;
import org.jetbrains.kotlin.backend.common.lower.VariableRemapper;
import org.jetbrains.kotlin.backend.common.lower.inline.FunctionInliningKt;
import org.jetbrains.kotlin.backend.common.lower.inline.INLINED_FUNCTION_ARGUMENTS;
import org.jetbrains.kotlin.backend.common.lower.inline.INLINED_FUNCTION_DEFAULT_ARGUMENTS;
import org.jetbrains.kotlin.ir.IrElement;
import org.jetbrains.kotlin.ir.IrStatement;
import org.jetbrains.kotlin.ir.builders.ExpressionHelpersKt;
import org.jetbrains.kotlin.ir.builders.IrBlockBuilder;
import org.jetbrains.kotlin.ir.builders.IrBuilderWithScope;
import org.jetbrains.kotlin.ir.builders.IrStatementsBuilder;
import org.jetbrains.kotlin.ir.declarations.IrAttributeContainer;
import org.jetbrains.kotlin.ir.declarations.IrClass;
import org.jetbrains.kotlin.ir.declarations.IrDeclaration;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationBase;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationOrigin;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationParent;
import org.jetbrains.kotlin.ir.declarations.IrFunction;
import org.jetbrains.kotlin.ir.declarations.IrSimpleFunction;
import org.jetbrains.kotlin.ir.declarations.IrValueDeclaration;
import org.jetbrains.kotlin.ir.declarations.IrValueParameter;
import org.jetbrains.kotlin.ir.declarations.IrVariable;
import org.jetbrains.kotlin.ir.expressions.IrBlock;
import org.jetbrains.kotlin.ir.expressions.IrBody;
import org.jetbrains.kotlin.ir.expressions.IrComposite;
import org.jetbrains.kotlin.ir.expressions.IrExpression;
import org.jetbrains.kotlin.ir.expressions.IrExpressionBody;
import org.jetbrains.kotlin.ir.expressions.IrFunctionExpression;
import org.jetbrains.kotlin.ir.expressions.IrFunctionReference;
import org.jetbrains.kotlin.ir.expressions.IrInlinedFunctionBlock;
import org.jetbrains.kotlin.ir.expressions.IrReturn;
import org.jetbrains.kotlin.ir.expressions.IrReturnableBlock;
import org.jetbrains.kotlin.ir.expressions.IrStatementOrigin;
import org.jetbrains.kotlin.ir.expressions.impl.IrCallImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrGetValueImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrReturnImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrReturnableBlockImpl;
import org.jetbrains.kotlin.ir.symbols.IrReturnTargetSymbol;
import org.jetbrains.kotlin.ir.symbols.impl.IrReturnableBlockSymbolImpl;
import org.jetbrains.kotlin.ir.types.IrTypesKt;
import org.jetbrains.kotlin.ir.util.AdditionalIrUtilsKt;
import org.jetbrains.kotlin.ir.visitors.IrElementTransformer;
import org.jetbrains.kotlin.util.OperatorNameConventions;

/* JADX INFO: compiled from: IrInlineUtils.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000\u0082\u0001\n\u0000\n\u0002\u0010\u000b\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\u001a$\u0010\u0004\u001a\u000e\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u00060\u00052\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\bH\u0002\u001a\u0016\u0010\n\u001a\u00020\u000b*\u00020\f2\n\u0010\r\u001a\u0006\u0012\u0002\b\u00030\u000e\u001a\f\u0010\u000f\u001a\u0004\u0018\u00010\u0010*\u00020\f\u001a\u001a\u0010\u0011\u001a\u0004\u0018\u00010\u0012*\u00020\f2\n\u0010\r\u001a\u0006\u0012\u0002\b\u00030\u000eH\u0002\u001a\u001a\u0010\u0013\u001a\u0004\u0018\u00010\u0014*\b\u0012\u0004\u0012\u00020\u00160\u00152\u0006\u0010\u0017\u001a\u00020\u0018\u001a\u0010\u0010\u0019\u001a\b\u0012\u0004\u0012\u00020\u001a0\u0015*\u00020\u0016\u001a\u0010\u0010\u001b\u001a\b\u0012\u0004\u0012\u00020\u001a0\u0015*\u00020\u0016\u001a\u0010\u0010\u001c\u001a\b\u0012\u0004\u0012\u00020\u001a0\u0015*\u00020\u0016\u001a\u0010\u0010\u001d\u001a\b\u0012\u0004\u0012\u00020\u001a0\u0015*\u00020\u0016\u001a\"\u0010\u001e\u001a\u00020\f*\u00020\u000b2\u0006\u0010\t\u001a\u00020\u001f2\u000e\b\u0002\u0010 \u001a\b\u0012\u0004\u0012\u00020!0\u0015\u001a\"\u0010\u001e\u001a\u00020\"*\u00020\b2\u0006\u0010\t\u001a\u00020\u001f2\u000e\b\u0002\u0010 \u001a\b\u0012\u0004\u0012\u00020!0\u0015\u001a8\u0010#\u001a\u00020$*\u00020$2\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\u001f2\u0006\u0010%\u001a\u00020&2\u0012\u0010 \u001a\u000e\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020!0\u0005H\u0002\u001a\u0014\u0010'\u001a\u0004\u0018\u00010$*\u00020\b2\u0006\u0010\t\u001a\u00020\b\u001a(\u0010'\u001a\u0004\u0018\u00010$*\u00020\b2\u0006\u0010\t\u001a\u00020\b2\u0012\u0010 \u001a\u000e\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020!0\u0005\u001a\u001a\u0010(\u001a\u00020)*\u00020\u00162\u0006\u0010\r\u001a\u00020*2\u0006\u0010+\u001a\u00020\u001a\u001a\u0018\u0010,\u001a\u00020)*\u00020\u00162\f\u0010-\u001a\b\u0012\u0004\u0012\u00020\u001a0\u0015\"\u0015\u0010\u0000\u001a\u00020\u0001*\u00020\u00028F¢\u0006\u0006\u001a\u0004\b\u0000\u0010\u0003¨\u0006."}, d2 = {"isTmpForInline", "", "Lorg/jetbrains/kotlin/ir/declarations/IrVariable;", "(Lorg/jetbrains/kotlin/ir/declarations/IrVariable;)Z", "createParameterMapping", "", "Lorg/jetbrains/kotlin/ir/declarations/IrValueParameter;", FirebaseAnalytics.Param.SOURCE, "Lorg/jetbrains/kotlin/ir/declarations/IrFunction;", "target", "asInlinable", "Lorg/jetbrains/kotlin/backend/common/ir/IrInlinable;", "Lorg/jetbrains/kotlin/ir/expressions/IrExpression;", "builder", "Lorg/jetbrains/kotlin/ir/builders/IrStatementsBuilder;", "asInlinableFunctionReference", "Lorg/jetbrains/kotlin/ir/expressions/IrFunctionReference;", "asInlinableLambda", "Lorg/jetbrains/kotlin/backend/common/ir/IrInlinableLambda;", "extractDeclarationWhereGivenElementWasInlined", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclaration;", "", "Lorg/jetbrains/kotlin/ir/expressions/IrInlinedFunctionBlock;", "inlinedElement", "Lorg/jetbrains/kotlin/ir/IrElement;", "getAdditionalStatementsFromInlinedBlock", "Lorg/jetbrains/kotlin/ir/IrStatement;", "getDefaultAdditionalStatementsFromInlinedBlock", "getNonDefaultAdditionalStatementsFromInlinedBlock", "getOriginalStatementsFromInlinedBlock", "inline", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationParent;", "arguments", "Lorg/jetbrains/kotlin/ir/declarations/IrValueDeclaration;", "Lorg/jetbrains/kotlin/ir/expressions/IrReturnableBlock;", "move", "Lorg/jetbrains/kotlin/ir/expressions/IrBody;", "targetSymbol", "Lorg/jetbrains/kotlin/ir/symbols/IrReturnTargetSymbol;", "moveBodyTo", "putStatementBeforeActualInline", "", "Lorg/jetbrains/kotlin/ir/builders/IrBuilderWithScope;", "statement", "putStatementsInFrontOfInlinedFunction", "statements", "ir.backend.common"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class IrInlineUtilsKt {
    public static final IrFunctionReference asInlinableFunctionReference(IrExpression irExpression) {
        Intrinsics.checkNotNullParameter(irExpression, "<this>");
        if (irExpression instanceof IrBlock) {
            IrBlock irBlock = (IrBlock) irExpression;
            if (irBlock.getStatements().size() == 2) {
                List<IrStatement> statements = irBlock.getStatements();
                IrStatement irStatement = statements.get(0);
                IrStatement irStatement2 = statements.get(1);
                if ((irStatement instanceof IrSimpleFunction) && (irStatement2 instanceof IrFunctionReference)) {
                    IrSimpleFunction irSimpleFunction = (IrSimpleFunction) irStatement;
                    IrFunctionReference irFunctionReference = (IrFunctionReference) irStatement2;
                    if (!Intrinsics.areEqual(irSimpleFunction.getSymbol(), irFunctionReference.getSymbol()) || irSimpleFunction.getDispatchReceiverParameter() != null) {
                        return null;
                    }
                    Iterable iterableUntil = RangesKt.until(0, irFunctionReference.getValueArgumentsCount());
                    if (!(iterableUntil instanceof Collection) || !((Collection) iterableUntil).isEmpty()) {
                        Iterator it = iterableUntil.iterator();
                        while (it.hasNext()) {
                            if (irFunctionReference.getValueArgument(((IntIterator) it).nextInt()) != null) {
                                return null;
                            }
                        }
                    }
                    List<IrValueParameter> valueParameters = irSimpleFunction.getValueParameters();
                    if (!(valueParameters instanceof Collection) || !valueParameters.isEmpty()) {
                        for (IrValueParameter irValueParameter : valueParameters) {
                            if (AdditionalIrUtilsKt.isVararg(irValueParameter) || irValueParameter.getDefaultValue() != null) {
                                return null;
                            }
                        }
                    }
                    return irFunctionReference;
                }
            }
        }
        return null;
    }

    private static final IrInlinableLambda asInlinableLambda(IrExpression irExpression, IrStatementsBuilder<?> irStatementsBuilder) {
        if (irExpression instanceof IrFunctionExpression) {
            IrFunctionExpression irFunctionExpression = (IrFunctionExpression) irExpression;
            List<IrValueParameter> valueParameters = irFunctionExpression.getFunction().getValueParameters();
            if (!(valueParameters instanceof Collection) || !valueParameters.isEmpty()) {
                for (IrValueParameter irValueParameter : valueParameters) {
                    if (AdditionalIrUtilsKt.isVararg(irValueParameter) || irValueParameter.getDefaultValue() != null) {
                        return null;
                    }
                }
            }
            return new IrInlinableLambda(irFunctionExpression.getFunction(), null);
        }
        IrFunctionReference irFunctionReferenceAsInlinableFunctionReference = asInlinableFunctionReference(irExpression);
        if (irFunctionReferenceAsInlinableFunctionReference == null) {
            return null;
        }
        IrFunction owner = irFunctionReferenceAsInlinableFunctionReference.getSymbol().getOwner();
        Intrinsics.checkNotNull(owner, "null cannot be cast to non-null type org.jetbrains.kotlin.ir.declarations.IrSimpleFunction");
        IrSimpleFunction irSimpleFunction = (IrSimpleFunction) owner;
        IrExpression extensionReceiver = irFunctionReferenceAsInlinableFunctionReference.getExtensionReceiver();
        return new IrInlinableLambda(irSimpleFunction, extensionReceiver != null ? ExpressionHelpersKt.irTemporary$default(irStatementsBuilder, extensionReceiver, null, null, false, null, 30, null) : null);
    }

    public static final IrInlinable asInlinable(IrExpression irExpression, IrStatementsBuilder<?> builder) {
        Intrinsics.checkNotNullParameter(irExpression, "<this>");
        Intrinsics.checkNotNullParameter(builder, "builder");
        IrInlinable irInlinableAsInlinableLambda = asInlinableLambda(irExpression, builder);
        if (irInlinableAsInlinableLambda == null) {
            irInlinableAsInlinableLambda = new IrInvokable(ExpressionHelpersKt.irTemporary$default(builder, irExpression, null, null, false, null, 30, null));
        }
        return irInlinableAsInlinableLambda;
    }

    private static final Map<IrValueParameter, IrValueParameter> createParameterMapping(IrFunction irFunction, IrFunction irFunction2) {
        List<IrValueParameter> explicitParameters = org.jetbrains.kotlin.ir.util.IrUtilsKt.getExplicitParameters(irFunction);
        List<IrValueParameter> explicitParameters2 = org.jetbrains.kotlin.ir.util.IrUtilsKt.getExplicitParameters(irFunction2);
        explicitParameters.size();
        explicitParameters2.size();
        return MapsKt.toMap(CollectionsKt.zip(explicitParameters, explicitParameters2));
    }

    public static final IrBody moveBodyTo(IrFunction irFunction, IrFunction target) {
        Intrinsics.checkNotNullParameter(irFunction, "<this>");
        Intrinsics.checkNotNullParameter(target, "target");
        return moveBodyTo(irFunction, target, createParameterMapping(irFunction, target));
    }

    public static final IrBody moveBodyTo(IrFunction irFunction, IrFunction target, Map<IrValueParameter, ? extends IrValueDeclaration> arguments) {
        Intrinsics.checkNotNullParameter(irFunction, "<this>");
        Intrinsics.checkNotNullParameter(target, "target");
        Intrinsics.checkNotNullParameter(arguments, "arguments");
        IrBody body = irFunction.getBody();
        if (body != null) {
            return move(body, irFunction, target, target.getSymbol(), arguments);
        }
        return null;
    }

    private static final IrBody move(IrBody irBody, final IrFunction irFunction, final IrDeclarationParent irDeclarationParent, final IrReturnTargetSymbol irReturnTargetSymbol, Map<IrValueParameter, ? extends IrValueDeclaration> map) {
        return irBody.transform((IrElementTransformer<? super Object>) new VariableRemapper(map) { // from class: org.jetbrains.kotlin.backend.common.ir.IrInlineUtilsKt.move.1
            @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
            public IrExpression visitReturn(IrReturn expression) {
                Intrinsics.checkNotNullParameter(expression, "expression");
                if (Intrinsics.areEqual(expression.getReturnTargetSymbol(), irFunction.getSymbol())) {
                    expression = new IrReturnImpl(expression.getStartOffset(), expression.getEndOffset(), expression.getType(), irReturnTargetSymbol, expression.getValue());
                }
                return super.visitReturn(expression);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
            public IrExpression visitBlock(IrBlock expression) {
                Intrinsics.checkNotNullParameter(expression, "expression");
                if ((Intrinsics.areEqual(expression.getOrigin(), IrStatementOrigin.LAMBDA.INSTANCE) || Intrinsics.areEqual(expression.getOrigin(), IrStatementOrigin.ANONYMOUS_FUNCTION.INSTANCE)) && (CollectionsKt.lastOrNull((List) expression.getStatements()) instanceof IrFunctionReference)) {
                    List<IrStatement> statements = expression.getStatements();
                    if ((statements instanceof Collection) && statements.isEmpty()) {
                        Object objLast = CollectionsKt.last((List<? extends Object>) expression.getStatements());
                        Intrinsics.checkNotNull(objLast, "null cannot be cast to non-null type org.jetbrains.kotlin.ir.expressions.IrFunctionReference");
                        transformChildrenVoid(((IrFunctionReference) objLast).getSymbol().getOwner());
                    } else {
                        Iterator<T> it = statements.iterator();
                        while (it.hasNext()) {
                            if (((IrStatement) it.next()) instanceof IrFunction) {
                            }
                        }
                        Object objLast2 = CollectionsKt.last((List<? extends Object>) expression.getStatements());
                        Intrinsics.checkNotNull(objLast2, "null cannot be cast to non-null type org.jetbrains.kotlin.ir.expressions.IrFunctionReference");
                        transformChildrenVoid(((IrFunctionReference) objLast2).getSymbol().getOwner());
                    }
                }
                return super.visitBlock(expression);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
            public IrStatement visitDeclaration(IrDeclarationBase declaration) {
                Intrinsics.checkNotNullParameter(declaration, "declaration");
                if (Intrinsics.areEqual(declaration.getParent(), irFunction)) {
                    declaration.setParent(irDeclarationParent);
                }
                return super.visitDeclaration(declaration);
            }
        }, (Object) null);
    }

    public static /* synthetic */ IrReturnableBlock inline$default(IrFunction irFunction, IrDeclarationParent irDeclarationParent, List list, int i, Object obj) {
        if ((i & 2) != 0) {
            list = CollectionsKt.emptyList();
        }
        return inline(irFunction, irDeclarationParent, (List<? extends IrValueDeclaration>) list);
    }

    public static final IrReturnableBlock inline(IrFunction irFunction, IrDeclarationParent target, List<? extends IrValueDeclaration> arguments) {
        Intrinsics.checkNotNullParameter(irFunction, "<this>");
        Intrinsics.checkNotNullParameter(target, "target");
        Intrinsics.checkNotNullParameter(arguments, "arguments");
        IrReturnableBlockImpl irReturnableBlockImpl = new IrReturnableBlockImpl(irFunction.getStartOffset(), irFunction.getEndOffset(), irFunction.getReturnType(), new IrReturnableBlockSymbolImpl(null, 1, null), null);
        List<IrStatement> statements = irReturnableBlockImpl.getStatements();
        IrBody body = irFunction.getBody();
        Intrinsics.checkNotNull(body);
        CollectionsKt.addAll(statements, org.jetbrains.kotlin.ir.util.IrUtilsKt.getStatements(move(body, irFunction, target, irReturnableBlockImpl.getSymbol(), MapsKt.toMap(CollectionsKt.zip(org.jetbrains.kotlin.ir.util.IrUtilsKt.getExplicitParameters(irFunction), arguments)))));
        return irReturnableBlockImpl;
    }

    public static /* synthetic */ IrExpression inline$default(IrInlinable irInlinable, IrDeclarationParent irDeclarationParent, List list, int i, Object obj) {
        if ((i & 2) != 0) {
            list = CollectionsKt.emptyList();
        }
        return inline(irInlinable, irDeclarationParent, (List<? extends IrValueDeclaration>) list);
    }

    public static final IrExpression inline(IrInlinable irInlinable, IrDeclarationParent target, List<? extends IrValueDeclaration> arguments) {
        Intrinsics.checkNotNullParameter(irInlinable, "<this>");
        Intrinsics.checkNotNullParameter(target, "target");
        Intrinsics.checkNotNullParameter(arguments, "arguments");
        if (irInlinable instanceof IrInlinableLambda) {
            IrInlinableLambda irInlinableLambda = (IrInlinableLambda) irInlinable;
            return inline(irInlinableLambda.getFunction(), target, (List<? extends IrValueDeclaration>) CollectionsKt.plus((Collection) CollectionsKt.listOfNotNull(irInlinableLambda.getBoundReceiver()), (Iterable) arguments));
        }
        if (irInlinable instanceof IrInvokable) {
            IrInvokable irInvokable = (IrInvokable) irInlinable;
            IrClass irClass = IrTypesKt.getClass(irInvokable.getInvokable().getType());
            Intrinsics.checkNotNull(irClass);
            int i = 0;
            IrSimpleFunction irSimpleFunction = null;
            boolean z = false;
            for (IrSimpleFunction irSimpleFunction2 : org.jetbrains.kotlin.ir.util.IrUtilsKt.getFunctions(irClass)) {
                if (Intrinsics.areEqual(irSimpleFunction2.getName(), OperatorNameConventions.INVOKE)) {
                    if (z) {
                        throw new IllegalArgumentException("Sequence contains more than one matching element.");
                    }
                    z = true;
                    irSimpleFunction = irSimpleFunction2;
                }
            }
            if (z) {
                IrSimpleFunction irSimpleFunction3 = irSimpleFunction;
                IrCallImpl irCallImpl = new IrCallImpl(-1, -1, irSimpleFunction3.getReturnType(), irSimpleFunction3.getSymbol(), 0, arguments.size(), null, null, 192, null);
                irCallImpl.setDispatchReceiver(new IrGetValueImpl(-1, -1, irInvokable.getInvokable().getSymbol(), null, 8, null));
                Iterator<? extends IrValueDeclaration> it = arguments.iterator();
                while (it.hasNext()) {
                    irCallImpl.putValueArgument(i, new IrGetValueImpl(-1, -1, it.next().getSymbol(), null, 8, null));
                    i++;
                }
                return irCallImpl;
            }
            throw new NoSuchElementException("Sequence contains no element matching the predicate.");
        }
        throw new NoWhenBranchMatchedException();
    }

    public static final List<IrStatement> getAdditionalStatementsFromInlinedBlock(IrInlinedFunctionBlock irInlinedFunctionBlock) {
        Intrinsics.checkNotNullParameter(irInlinedFunctionBlock, "<this>");
        List<IrStatement> statements = irInlinedFunctionBlock.getStatements();
        ArrayList arrayList = new ArrayList();
        for (Object obj : statements) {
            if (obj instanceof IrComposite) {
                arrayList.add(obj);
            }
        }
        ArrayList arrayList2 = new ArrayList();
        for (Object obj2 : arrayList) {
            IrComposite irComposite = (IrComposite) obj2;
            if (Intrinsics.areEqual(irComposite.getOrigin(), INLINED_FUNCTION_ARGUMENTS.INSTANCE) || Intrinsics.areEqual(irComposite.getOrigin(), INLINED_FUNCTION_DEFAULT_ARGUMENTS.INSTANCE)) {
                arrayList2.add(obj2);
            }
        }
        ArrayList arrayList3 = new ArrayList();
        Iterator it = arrayList2.iterator();
        while (it.hasNext()) {
            CollectionsKt.addAll(arrayList3, ((IrComposite) it.next()).getStatements());
        }
        return arrayList3;
    }

    public static final List<IrStatement> getNonDefaultAdditionalStatementsFromInlinedBlock(IrInlinedFunctionBlock irInlinedFunctionBlock) {
        List<IrStatement> statements;
        Intrinsics.checkNotNullParameter(irInlinedFunctionBlock, "<this>");
        List<IrStatement> statements2 = irInlinedFunctionBlock.getStatements();
        ArrayList arrayList = new ArrayList();
        for (Object obj : statements2) {
            if (obj instanceof IrComposite) {
                arrayList.add(obj);
            }
        }
        Iterator it = arrayList.iterator();
        Object obj2 = null;
        boolean z = false;
        Object obj3 = null;
        while (true) {
            if (!it.hasNext()) {
                if (!z) {
                    break;
                }
                obj2 = obj3;
                break;
            }
            Object next = it.next();
            if (Intrinsics.areEqual(((IrComposite) next).getOrigin(), INLINED_FUNCTION_ARGUMENTS.INSTANCE)) {
                if (z) {
                    break;
                }
                z = true;
                obj3 = next;
            }
        }
        IrComposite irComposite = (IrComposite) obj2;
        return (irComposite == null || (statements = irComposite.getStatements()) == null) ? CollectionsKt.emptyList() : statements;
    }

    public static final List<IrStatement> getDefaultAdditionalStatementsFromInlinedBlock(IrInlinedFunctionBlock irInlinedFunctionBlock) {
        List<IrStatement> statements;
        Intrinsics.checkNotNullParameter(irInlinedFunctionBlock, "<this>");
        List<IrStatement> statements2 = irInlinedFunctionBlock.getStatements();
        ArrayList arrayList = new ArrayList();
        for (Object obj : statements2) {
            if (obj instanceof IrComposite) {
                arrayList.add(obj);
            }
        }
        Iterator it = arrayList.iterator();
        Object obj2 = null;
        boolean z = false;
        Object obj3 = null;
        while (true) {
            if (!it.hasNext()) {
                if (!z) {
                    break;
                }
                obj2 = obj3;
                break;
            }
            Object next = it.next();
            if (Intrinsics.areEqual(((IrComposite) next).getOrigin(), INLINED_FUNCTION_DEFAULT_ARGUMENTS.INSTANCE)) {
                if (z) {
                    break;
                }
                z = true;
                obj3 = next;
            }
        }
        IrComposite irComposite = (IrComposite) obj2;
        return (irComposite == null || (statements = irComposite.getStatements()) == null) ? CollectionsKt.emptyList() : statements;
    }

    public static final List<IrStatement> getOriginalStatementsFromInlinedBlock(IrInlinedFunctionBlock irInlinedFunctionBlock) {
        Intrinsics.checkNotNullParameter(irInlinedFunctionBlock, "<this>");
        List<IrStatement> statements = irInlinedFunctionBlock.getStatements();
        ArrayList arrayList = new ArrayList();
        for (Object obj : statements) {
            IrStatement irStatement = (IrStatement) obj;
            if (irStatement instanceof IrComposite) {
                IrComposite irComposite = (IrComposite) irStatement;
                if (Intrinsics.areEqual(irComposite.getOrigin(), INLINED_FUNCTION_ARGUMENTS.INSTANCE) || Intrinsics.areEqual(irComposite.getOrigin(), INLINED_FUNCTION_DEFAULT_ARGUMENTS.INSTANCE)) {
                }
            }
            arrayList.add(obj);
        }
        return arrayList;
    }

    public static final void putStatementBeforeActualInline(IrInlinedFunctionBlock irInlinedFunctionBlock, IrBuilderWithScope builder, IrStatement statement) {
        Intrinsics.checkNotNullParameter(irInlinedFunctionBlock, "<this>");
        Intrinsics.checkNotNullParameter(builder, "builder");
        Intrinsics.checkNotNullParameter(statement, "statement");
        List<IrStatement> statements = irInlinedFunctionBlock.getStatements();
        ArrayList arrayList = new ArrayList();
        for (Object obj : statements) {
            if (obj instanceof IrComposite) {
                arrayList.add(obj);
            }
        }
        Iterator it = arrayList.iterator();
        boolean z = false;
        Object obj2 = null;
        while (true) {
            if (!it.hasNext()) {
                if (z) {
                    break;
                } else {
                    break;
                }
            } else {
                Object next = it.next();
                if (Intrinsics.areEqual(((IrComposite) next).getOrigin(), INLINED_FUNCTION_ARGUMENTS.INSTANCE)) {
                    if (!z) {
                        z = true;
                        obj2 = next;
                    }
                }
            }
            obj2 = null;
            break;
        }
        IrComposite irComposite = (IrComposite) obj2;
        List<IrStatement> statements2 = irComposite != null ? irComposite.getStatements() : null;
        if (statements2 != null) {
            statements2.add(0, statement);
            return;
        }
        IrBlockBuilder irBlockBuilder = new IrBlockBuilder(builder.getContext(), builder.getScope(), -1, -1, INLINED_FUNCTION_ARGUMENTS.INSTANCE, builder.getContext().getIrBuiltIns().getUnitType(), true);
        irBlockBuilder.unaryPlus(statement);
        irInlinedFunctionBlock.getStatements().add(0, irBlockBuilder.getIrBlockBody());
    }

    public static final void putStatementsInFrontOfInlinedFunction(IrInlinedFunctionBlock irInlinedFunctionBlock, List<? extends IrStatement> statements) {
        int iNextIndex;
        Intrinsics.checkNotNullParameter(irInlinedFunctionBlock, "<this>");
        Intrinsics.checkNotNullParameter(statements, "statements");
        List<IrStatement> statements2 = irInlinedFunctionBlock.getStatements();
        ListIterator<IrStatement> listIterator = statements2.listIterator(statements2.size());
        while (true) {
            if (!listIterator.hasPrevious()) {
                iNextIndex = -1;
                break;
            }
            IrStatement irStatementPrevious = listIterator.previous();
            if (irStatementPrevious instanceof IrComposite) {
                IrComposite irComposite = (IrComposite) irStatementPrevious;
                if (Intrinsics.areEqual(irComposite.getOrigin(), INLINED_FUNCTION_ARGUMENTS.INSTANCE) || Intrinsics.areEqual(irComposite.getOrigin(), INLINED_FUNCTION_DEFAULT_ARGUMENTS.INSTANCE)) {
                    iNextIndex = listIterator.nextIndex();
                    break;
                }
            }
        }
        irInlinedFunctionBlock.getStatements().addAll(iNextIndex == -1 ? 0 : iNextIndex + 1, statements);
    }

    public static final IrDeclaration extractDeclarationWhereGivenElementWasInlined(List<? extends IrInlinedFunctionBlock> list, IrElement inlinedElement) {
        IrClass irClass;
        List<IrStatement> statements;
        IrExpression expression;
        IrAttributeContainer attributeOwnerId;
        Intrinsics.checkNotNullParameter(list, "<this>");
        Intrinsics.checkNotNullParameter(inlinedElement, "inlinedElement");
        IrAttributeContainer irAttributeContainer = inlinedElement instanceof IrAttributeContainer ? (IrAttributeContainer) inlinedElement : null;
        if (irAttributeContainer != null && (attributeOwnerId = irAttributeContainer.getAttributeOwnerId()) != null) {
            inlinedElement = attributeOwnerId;
        }
        ArrayList<IrInlinedFunctionBlock> arrayList = new ArrayList();
        for (Object obj : list) {
            if (org.jetbrains.kotlin.ir.util.IrInlineUtilsKt.isFunctionInlining((IrInlinedFunctionBlock) obj)) {
                arrayList.add(obj);
            }
        }
        for (IrInlinedFunctionBlock irInlinedFunctionBlock : arrayList) {
            Iterator<T> it = org.jetbrains.kotlin.ir.util.IrUtilsKt.getAllArgumentsWithIr(irInlinedFunctionBlock.getInlineCall()).iterator();
            while (it.hasNext()) {
                Pair pair = (Pair) it.next();
                if (pair.getSecond() == null) {
                    IrExpressionBody defaultValue = ((IrValueParameter) pair.getFirst()).getDefaultValue();
                    IrAttributeContainer attributeOwnerId2 = (defaultValue == null || (expression = defaultValue.getExpression()) == null) ? null : expression.getAttributeOwnerId();
                    IrBlock irBlock = attributeOwnerId2 instanceof IrBlock ? (IrBlock) attributeOwnerId2 : null;
                    IrStatement irStatement = (irBlock == null || (statements = irBlock.getStatements()) == null) ? null : (IrStatement) CollectionsKt.firstOrNull((List) statements);
                    irClass = irStatement instanceof IrClass ? (IrClass) irStatement : null;
                } else {
                    irClass = (IrElement) pair.getSecond();
                }
                IrAttributeContainer attributeOwnerId3 = irClass != null ? ((IrAttributeContainer) irClass).getAttributeOwnerId() : null;
                IrFunctionReference irFunctionReference = attributeOwnerId3 instanceof IrExpression ? (IrExpression) attributeOwnerId3 : null;
                if (irFunctionReference != null && FunctionInliningKt.isAdaptedFunctionReference(irFunctionReference)) {
                    Intrinsics.checkNotNull(irFunctionReference, "null cannot be cast to non-null type org.jetbrains.kotlin.ir.expressions.IrBlock");
                    Object objLast = CollectionsKt.last((List<? extends Object>) ((IrBlock) irFunctionReference).getStatements());
                    Intrinsics.checkNotNull(objLast, "null cannot be cast to non-null type org.jetbrains.kotlin.ir.expressions.IrFunctionReference");
                    irFunctionReference = (IrFunctionReference) objLast;
                }
                if (Intrinsics.areEqual(irFunctionReference != null ? irFunctionReference.getAttributeOwnerId() : null, inlinedElement)) {
                    return org.jetbrains.kotlin.ir.util.IrInlineUtilsKt.getInlineDeclaration(irInlinedFunctionBlock);
                }
            }
        }
        return null;
    }

    public static final boolean isTmpForInline(IrVariable irVariable) {
        Intrinsics.checkNotNullParameter(irVariable, "<this>");
        return Intrinsics.areEqual(irVariable.getOrigin(), IrDeclarationOrigin.IR_TEMPORARY_VARIABLE_FOR_INLINED_PARAMETER.INSTANCE) || Intrinsics.areEqual(irVariable.getOrigin(), IrDeclarationOrigin.IR_TEMPORARY_VARIABLE_FOR_INLINED_EXTENSION_RECEIVER.INSTANCE);
    }
}
