package org.jetbrains.kotlin.backend.wasm.lower;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.collections.CollectionsKt;
import kotlin.comparisons.ComparisonsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.sequences.SequencesKt;
import org.jetbrains.kotlin.backend.common.FileLoweringPass;
import org.jetbrains.kotlin.backend.common.IrElementTransformerVoidWithContext;
import org.jetbrains.kotlin.backend.common.ScopeWithIr;
import org.jetbrains.kotlin.backend.common.lower.DeclarationIrBuilder;
import org.jetbrains.kotlin.backend.common.lower.LowerUtilsKt;
import org.jetbrains.kotlin.backend.wasm.WasmBackendContext;
import org.jetbrains.kotlin.backend.wasm.WasmSymbols;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.descriptors.DescriptorVisibilities;
import org.jetbrains.kotlin.descriptors.DescriptorVisibility;
import org.jetbrains.kotlin.ir.builders.ExpressionHelpersKt;
import org.jetbrains.kotlin.ir.builders.IrBlockBuilder;
import org.jetbrains.kotlin.ir.builders.IrBuilderWithScope;
import org.jetbrains.kotlin.ir.declarations.IrClass;
import org.jetbrains.kotlin.ir.declarations.IrDeclaration;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationOrigin;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationOriginImpl;
import org.jetbrains.kotlin.ir.declarations.IrFactory;
import org.jetbrains.kotlin.ir.declarations.IrField;
import org.jetbrains.kotlin.ir.declarations.IrFile;
import org.jetbrains.kotlin.ir.declarations.IrLocalDelegatedProperty;
import org.jetbrains.kotlin.ir.declarations.IrProperty;
import org.jetbrains.kotlin.ir.declarations.IrSimpleFunction;
import org.jetbrains.kotlin.ir.declarations.IrValueParameter;
import org.jetbrains.kotlin.ir.declarations.IrVariable;
import org.jetbrains.kotlin.ir.expressions.IrExpression;
import org.jetbrains.kotlin.ir.expressions.IrFunctionAccessExpression;
import org.jetbrains.kotlin.ir.expressions.IrLocalDelegatedPropertyReference;
import org.jetbrains.kotlin.ir.expressions.IrMemberAccessExpression;
import org.jetbrains.kotlin.ir.expressions.IrPropertyReference;
import org.jetbrains.kotlin.ir.expressions.impl.IrFunctionReferenceImpl;
import org.jetbrains.kotlin.ir.symbols.IrClassSymbol;
import org.jetbrains.kotlin.ir.symbols.IrConstructorSymbol;
import org.jetbrains.kotlin.ir.symbols.IrFieldSymbol;
import org.jetbrains.kotlin.ir.symbols.IrSimpleFunctionSymbol;
import org.jetbrains.kotlin.ir.symbols.impl.IrFieldSymbolImpl;
import org.jetbrains.kotlin.ir.types.IrSimpleType;
import org.jetbrains.kotlin.ir.types.IrType;
import org.jetbrains.kotlin.ir.types.IrTypesKt;
import org.jetbrains.kotlin.ir.util.IrUtilsKt;
import org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoidKt;
import org.jetbrains.kotlin.name.Name;

/* JADX INFO: compiled from: WasmPropertyReferenceLowering.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000l\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0000\u0018\u00002\u00020\u0001:\u0002*+B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J \u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u0014H\u0002J\u0006\u0010\u0015\u001a\u00020\u0012J(\u0010\u0016\u001a\u00020\u000e2\u0006\u0010\u0017\u001a\u00020\u00182\u0006\u0010\u0019\u001a\u00020\u001a2\u0006\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u0014H\u0002J@\u0010\u001b\u001a\u0014\u0012\u0004\u0012\u00020\u001d\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u001a0\u001e0\u001c2\f\u0010\u001f\u001a\b\u0012\u0004\u0012\u00020\u001a0\u001e2\u0006\u0010 \u001a\u00020\u001a2\u0006\u0010!\u001a\u00020\"2\u0006\u0010#\u001a\u00020\"H\u0002J\u0010\u0010$\u001a\u00020\"2\u0006\u0010%\u001a\u00020\u001aH\u0002J\u0010\u0010&\u001a\u00020'2\u0006\u0010(\u001a\u00020)H\u0016R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006R\u0011\u0010\u0007\u001a\u00020\b¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u000e\u0010\u000b\u001a\u00020\fX\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006,"}, d2 = {"Lorg/jetbrains/kotlin/backend/wasm/lower/WasmPropertyReferenceLowering;", "Lorg/jetbrains/kotlin/backend/common/FileLoweringPass;", "context", "Lorg/jetbrains/kotlin/backend/wasm/WasmBackendContext;", "(Lorg/jetbrains/kotlin/backend/wasm/WasmBackendContext;)V", "getContext", "()Lorg/jetbrains/kotlin/backend/wasm/WasmBackendContext;", "symbols", "Lorg/jetbrains/kotlin/backend/wasm/WasmSymbols;", "getSymbols", "()Lorg/jetbrains/kotlin/backend/wasm/WasmSymbols;", "tempIndex", "", "createKProperty", "Lorg/jetbrains/kotlin/ir/expressions/IrExpression;", "expression", "Lorg/jetbrains/kotlin/ir/expressions/IrPropertyReference;", "kTypeGenerator", "Lorg/jetbrains/kotlin/backend/wasm/lower/WasmPropertyReferenceLowering$KTypeGeneratorInterface;", "irBuilder", "Lorg/jetbrains/kotlin/ir/builders/IrBuilderWithScope;", "createKTypeGenerator", "createLocalKProperty", "propertyName", "", "propertyType", "Lorg/jetbrains/kotlin/ir/types/IrType;", "getKPropertyImplConstructor", "Lkotlin/Pair;", "Lorg/jetbrains/kotlin/ir/symbols/IrConstructorSymbol;", "", "receiverTypes", "returnType", "isLocal", "", "isMutable", "isKMutablePropertyType", ModuleXmlParser.TYPE, "lower", "", "irFile", "Lorg/jetbrains/kotlin/ir/declarations/IrFile;", "DECLARATION_ORIGIN_KPROPERTIES_FOR_DELEGATION", "KTypeGeneratorInterface", "backend.wasm"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class WasmPropertyReferenceLowering implements FileLoweringPass {
    private final WasmBackendContext context;
    private final WasmSymbols symbols;
    private int tempIndex;

    /* JADX INFO: compiled from: WasmPropertyReferenceLowering.kt */
    @Metadata(d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\bæ\u0080\u0001\u0018\u00002\u00020\u0001J\u0014\u0010\u0002\u001a\u00020\u0003*\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H&ø\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001¨\u0006\u0007À\u0006\u0001"}, d2 = {"Lorg/jetbrains/kotlin/backend/wasm/lower/WasmPropertyReferenceLowering$KTypeGeneratorInterface;", "", "irKType", "Lorg/jetbrains/kotlin/ir/expressions/IrExpression;", "Lorg/jetbrains/kotlin/ir/builders/IrBuilderWithScope;", ModuleXmlParser.TYPE, "Lorg/jetbrains/kotlin/ir/types/IrType;", "backend.wasm"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public interface KTypeGeneratorInterface {
        IrExpression irKType(IrBuilderWithScope irBuilderWithScope, IrType irType);
    }

    public WasmPropertyReferenceLowering(WasmBackendContext context) {
        Intrinsics.checkNotNullParameter(context, "context");
        this.context = context;
        this.symbols = context.getWasmSymbols();
    }

    public final WasmBackendContext getContext() {
        return this.context;
    }

    public final WasmSymbols getSymbols() {
        return this.symbols;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final IrExpression createKTypeGenerator$lambda$0(WasmPropertyReferenceLowering this$0, IrBuilderWithScope KTypeGeneratorInterface2, IrType it) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNullParameter(KTypeGeneratorInterface2, "$this$KTypeGeneratorInterface");
        Intrinsics.checkNotNullParameter(it, "it");
        return ExpressionHelpersKt.irCall(KTypeGeneratorInterface2, this$0.symbols.getKTypeStub());
    }

    public final KTypeGeneratorInterface createKTypeGenerator() {
        return new KTypeGeneratorInterface() { // from class: org.jetbrains.kotlin.backend.wasm.lower.WasmPropertyReferenceLowering$$ExternalSyntheticLambda0
            @Override // org.jetbrains.kotlin.backend.wasm.lower.WasmPropertyReferenceLowering.KTypeGeneratorInterface
            public final IrExpression irKType(IrBuilderWithScope irBuilderWithScope, IrType irType) {
                return WasmPropertyReferenceLowering.createKTypeGenerator$lambda$0(this.f$0, irBuilderWithScope, irType);
            }
        };
    }

    private final Pair<IrConstructorSymbol, List<IrType>> getKPropertyImplConstructor(List<? extends IrType> receiverTypes, IrType returnType, boolean isLocal, boolean isMutable) {
        IrClassSymbol kProperty0Impl;
        if (isLocal) {
            receiverTypes.isEmpty();
            if (isMutable) {
                kProperty0Impl = this.symbols.getKLocalDelegatedMutablePropertyImpl();
            } else {
                kProperty0Impl = this.symbols.getKLocalDelegatedPropertyImpl();
            }
        } else {
            int size = receiverTypes.size();
            if (size != 0) {
                if (size != 1) {
                    if (size != 2) {
                        throw new IllegalStateException("More than 2 receivers is not allowed".toString());
                    }
                    if (isMutable) {
                        kProperty0Impl = this.symbols.getKMutableProperty2Impl();
                    } else {
                        kProperty0Impl = this.symbols.getKProperty2Impl();
                    }
                } else if (isMutable) {
                    kProperty0Impl = this.symbols.getKMutableProperty1Impl();
                } else {
                    kProperty0Impl = this.symbols.getKProperty1Impl();
                }
            } else if (isMutable) {
                kProperty0Impl = this.symbols.getKMutableProperty0Impl();
            } else {
                kProperty0Impl = this.symbols.getKProperty0Impl();
            }
        }
        return TuplesKt.to(SequencesKt.single(IrUtilsKt.getConstructors(kProperty0Impl)), CollectionsKt.plus((Collection) receiverTypes, (Iterable) CollectionsKt.listOf(returnType)));
    }

    @Override // org.jetbrains.kotlin.backend.common.FileLoweringPass
    public void lower(IrFile irFile) {
        Intrinsics.checkNotNullParameter(irFile, "irFile");
        final LinkedHashMap linkedHashMap = new LinkedHashMap();
        IrClass owner = this.context.getIrBuiltIns().getArrayClass().getOwner();
        IrSimpleFunction irSimpleFunction = null;
        boolean z = false;
        for (IrSimpleFunction irSimpleFunction2 : IrUtilsKt.getFunctions(owner)) {
            if (Intrinsics.areEqual(irSimpleFunction2.getName(), Name.identifier("get"))) {
                if (z) {
                    throw new IllegalArgumentException("Sequence contains more than one matching element.");
                }
                irSimpleFunction = irSimpleFunction2;
                z = true;
            }
        }
        if (z) {
            final IrSimpleFunction irSimpleFunction3 = irSimpleFunction;
            IrType anyType = this.context.getIrBuiltIns().getAnyType();
            IrSimpleType irSimpleTypeTypeWith = IrTypesKt.typeWith(this.symbols.getKProperty1Impl(), anyType, anyType);
            IrSimpleType irSimpleTypeTypeWith2 = IrTypesKt.typeWith(owner, irSimpleTypeTypeWith);
            IrFactory irFactory = this.context.getIrFactory();
            DECLARATION_ORIGIN_KPROPERTIES_FOR_DELEGATION declaration_origin_kproperties_for_delegation = DECLARATION_ORIGIN_KPROPERTIES_FOR_DELEGATION.INSTANCE;
            Name nameIdentifier = Name.identifier("$KPROPERTIES");
            Intrinsics.checkNotNullExpressionValue(nameIdentifier, "identifier(...)");
            DescriptorVisibility PRIVATE = DescriptorVisibilities.PRIVATE;
            Intrinsics.checkNotNullExpressionValue(PRIVATE, "PRIVATE");
            final IrField irFieldCreateField = irFactory.createField(-2, -2, (IrDeclarationOrigin) declaration_origin_kproperties_for_delegation, nameIdentifier, PRIVATE, (IrFieldSymbol) new IrFieldSymbolImpl(null, 1, null), (IrType) irSimpleTypeTypeWith2, true, true, false);
            irFieldCreateField.setParent(irFile);
            IrElementTransformerVoidKt.transformChildrenVoid(irFile, new IrElementTransformerVoidWithContext() { // from class: org.jetbrains.kotlin.backend.wasm.lower.WasmPropertyReferenceLowering.lower.1
                @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
                public IrExpression visitPropertyReference(IrPropertyReference expression) {
                    int i;
                    Intrinsics.checkNotNullParameter(expression, "expression");
                    IrElementTransformerVoidKt.transformChildrenVoid(expression, this);
                    KTypeGeneratorInterface kTypeGeneratorInterfaceCreateKTypeGenerator = WasmPropertyReferenceLowering.this.createKTypeGenerator();
                    int startOffset = expression.getStartOffset();
                    int endOffset = expression.getEndOffset();
                    WasmBackendContext context = WasmPropertyReferenceLowering.this.getContext();
                    ScopeWithIr currentScope = getCurrentScope();
                    Intrinsics.checkNotNull(currentScope);
                    DeclarationIrBuilder declarationIrBuilderCreateIrBuilder = LowerUtilsKt.createIrBuilder(context, currentScope.getScope().getScopeOwnerSymbol(), startOffset, endOffset);
                    Map<IrDeclaration, Pair<IrExpression, Integer>> map = linkedHashMap;
                    IrSimpleFunction irSimpleFunction4 = irSimpleFunction3;
                    WasmPropertyReferenceLowering wasmPropertyReferenceLowering = WasmPropertyReferenceLowering.this;
                    IrField irField = irFieldCreateField;
                    List listListOf = CollectionsKt.listOf((Object[]) new IrExpression[]{expression.getDispatchReceiver(), expression.getExtensionReceiver()});
                    if ((listListOf instanceof Collection) && listListOf.isEmpty()) {
                        i = 0;
                    } else {
                        Iterator it = listListOf.iterator();
                        i = 0;
                        while (it.hasNext()) {
                            if (((IrExpression) it.next()) != null && (i = i + 1) < 0) {
                                CollectionsKt.throwCountOverflow();
                            }
                        }
                    }
                    if (i != 0) {
                        if (i == 1) {
                            return wasmPropertyReferenceLowering.createKProperty(expression, kTypeGeneratorInterfaceCreateKTypeGenerator, declarationIrBuilderCreateIrBuilder);
                        }
                        throw new IllegalStateException(("Callable reference to properties with two receivers is not allowed: " + expression.getSymbol().getOwner().getName()).toString());
                    }
                    IrProperty owner2 = expression.getSymbol().getOwner();
                    Pair<IrExpression, Integer> pair = map.get(owner2);
                    if (pair == null) {
                        pair = TuplesKt.to(wasmPropertyReferenceLowering.createKProperty(expression, kTypeGeneratorInterfaceCreateKTypeGenerator, declarationIrBuilderCreateIrBuilder), Integer.valueOf(map.size()));
                        map.put(owner2, pair);
                    }
                    DeclarationIrBuilder declarationIrBuilder = declarationIrBuilderCreateIrBuilder;
                    IrFunctionAccessExpression irFunctionAccessExpressionIrCall = ExpressionHelpersKt.irCall(declarationIrBuilder, irSimpleFunction4);
                    irFunctionAccessExpressionIrCall.setDispatchReceiver(ExpressionHelpersKt.irGetField$default(declarationIrBuilder, null, irField, null, 4, null));
                    irFunctionAccessExpressionIrCall.putValueArgument(0, ExpressionHelpersKt.irInt$default(declarationIrBuilder, pair.getSecond().intValue(), null, 2, null));
                    return irFunctionAccessExpressionIrCall;
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
                public IrExpression visitLocalDelegatedPropertyReference(IrLocalDelegatedPropertyReference expression) {
                    int i;
                    Intrinsics.checkNotNullParameter(expression, "expression");
                    IrElementTransformerVoidKt.transformChildrenVoid(expression, this);
                    int startOffset = expression.getStartOffset();
                    int endOffset = expression.getEndOffset();
                    WasmBackendContext context = WasmPropertyReferenceLowering.this.getContext();
                    ScopeWithIr currentScope = getCurrentScope();
                    Intrinsics.checkNotNull(currentScope);
                    DeclarationIrBuilder declarationIrBuilderCreateIrBuilder = LowerUtilsKt.createIrBuilder(context, currentScope.getScope().getScopeOwnerSymbol(), startOffset, endOffset);
                    Map<IrDeclaration, Pair<IrExpression, Integer>> map = linkedHashMap;
                    IrSimpleFunction irSimpleFunction4 = irSimpleFunction3;
                    WasmPropertyReferenceLowering wasmPropertyReferenceLowering = WasmPropertyReferenceLowering.this;
                    IrField irField = irFieldCreateField;
                    List listListOf = CollectionsKt.listOf((Object[]) new IrExpression[]{expression.getDispatchReceiver(), expression.getExtensionReceiver()});
                    if ((listListOf instanceof Collection) && listListOf.isEmpty()) {
                        i = 0;
                    } else {
                        Iterator it = listListOf.iterator();
                        i = 0;
                        while (it.hasNext()) {
                            if ((((IrExpression) it.next()) != null) && (i = i + 1) < 0) {
                                CollectionsKt.throwCountOverflow();
                            }
                        }
                    }
                    if (i == 2) {
                        throw new IllegalStateException(("Callable reference to properties with two receivers is not allowed: " + expression).toString());
                    }
                    IrLocalDelegatedProperty owner2 = expression.getSymbol().getOwner();
                    Pair<IrExpression, Integer> pair = map.get(owner2);
                    if (pair == null) {
                        String strAsString = expression.getSymbol().getOwner().getName().asString();
                        Intrinsics.checkNotNullExpressionValue(strAsString, "asString(...)");
                        pair = TuplesKt.to(wasmPropertyReferenceLowering.createLocalKProperty(strAsString, expression.getGetter().getOwner().getReturnType(), wasmPropertyReferenceLowering.createKTypeGenerator(), declarationIrBuilderCreateIrBuilder), Integer.valueOf(map.size()));
                        map.put(owner2, pair);
                    }
                    DeclarationIrBuilder declarationIrBuilder = declarationIrBuilderCreateIrBuilder;
                    IrFunctionAccessExpression irFunctionAccessExpressionIrCall = ExpressionHelpersKt.irCall(declarationIrBuilder, irSimpleFunction4);
                    irFunctionAccessExpressionIrCall.setDispatchReceiver(ExpressionHelpersKt.irGetField$default(declarationIrBuilder, null, irField, null, 4, null));
                    irFunctionAccessExpressionIrCall.putValueArgument(0, ExpressionHelpersKt.irInt$default(declarationIrBuilder, pair.getSecond().intValue(), null, 2, null));
                    return irFunctionAccessExpressionIrCall;
                }
            });
            if (linkedHashMap.isEmpty()) {
                return;
            }
            List listSortedWith = CollectionsKt.sortedWith(linkedHashMap.values(), new Comparator() { // from class: org.jetbrains.kotlin.backend.wasm.lower.WasmPropertyReferenceLowering$lower$$inlined$sortedBy$1
                /* JADX WARN: Multi-variable type inference failed */
                @Override // java.util.Comparator
                public final int compare(T t, T t2) {
                    return ComparisonsKt.compareValues((Integer) ((Pair) t).getSecond(), (Integer) ((Pair) t2).getSecond());
                }
            });
            ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(listSortedWith, 10));
            Iterator it = listSortedWith.iterator();
            while (it.hasNext()) {
                arrayList.add((IrExpression) ((Pair) it.next()).getFirst());
            }
            irFieldCreateField.setInitializer(this.context.getIrFactory().createExpressionBody(-2, -2, org.jetbrains.kotlin.backend.common.ir.IrUtilsKt.createArrayOfExpression(this.context, -2, -2, irSimpleTypeTypeWith, arrayList)));
            irFile.getDeclarations().add(0, irFieldCreateField);
            return;
        }
        throw new NoSuchElementException("Sequence contains no element matching the predicate.");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final IrExpression createKProperty(IrPropertyReference expression, KTypeGeneratorInterface kTypeGenerator, IrBuilderWithScope irBuilder) {
        IrVariable irVariableIrTemporary$default;
        IrVariable irVariableIrTemporary$default2;
        IrType type;
        IrBlockBuilder irBlockBuilder;
        IrFunctionReferenceImpl irFunctionReferenceImpl;
        IrType irType;
        IrSimpleFunction owner;
        IrSimpleFunction owner2;
        IrSimpleFunction owner3;
        int startOffset = expression.getStartOffset();
        int endOffset = expression.getEndOffset();
        IrBuilderWithScope irBuilderWithScope = irBuilder;
        IrPropertyReference irPropertyReference = expression;
        IrBlockBuilder irBlockBuilder2 = new IrBlockBuilder(irBuilderWithScope.getContext(), irBuilderWithScope.getScope(), irPropertyReference.getStartOffset(), irPropertyReference.getEndOffset(), null, irPropertyReference.getType(), false, 64, null);
        ArrayList arrayList = new ArrayList();
        IrExpression dispatchReceiver = expression.getDispatchReceiver();
        IrExpression irExpression = null;
        if (dispatchReceiver != null) {
            StringBuilder sb = new StringBuilder("$dispatchReceiver");
            int i = this.tempIndex;
            this.tempIndex = i + 1;
            irVariableIrTemporary$default = ExpressionHelpersKt.irTemporary$default(irBlockBuilder2, dispatchReceiver, sb.append(i).toString(), null, false, null, 28, null);
        } else {
            irVariableIrTemporary$default = null;
        }
        IrExpression extensionReceiver = expression.getExtensionReceiver();
        if (extensionReceiver != null) {
            StringBuilder sb2 = new StringBuilder("$extensionReceiver");
            int i2 = this.tempIndex;
            this.tempIndex = i2 + 1;
            irVariableIrTemporary$default2 = ExpressionHelpersKt.irTemporary$default(irBlockBuilder2, extensionReceiver, sb2.append(i2).toString(), null, false, null, 28, null);
        } else {
            irVariableIrTemporary$default2 = null;
        }
        IrSimpleFunctionSymbol getter = expression.getGetter();
        if (getter == null || (owner3 = getter.getOwner()) == null || (type = owner3.getReturnType()) == null) {
            IrFieldSymbol field = expression.getField();
            Intrinsics.checkNotNull(field);
            type = field.getOwner().getType();
        }
        IrType irType2 = type;
        IrSimpleFunctionSymbol getter2 = expression.getGetter();
        if (getter2 == null || (owner2 = getter2.getOwner()) == null) {
            irBlockBuilder = irBlockBuilder2;
            irFunctionReferenceImpl = null;
        } else {
            IrValueParameter dispatchReceiverParameter = owner2.getDispatchReceiverParameter();
            if (dispatchReceiverParameter != null && expression.getDispatchReceiver() == null) {
                arrayList.add(dispatchReceiverParameter.getType());
            }
            IrValueParameter extensionReceiverParameter = owner2.getExtensionReceiverParameter();
            if (extensionReceiverParameter != null && expression.getExtensionReceiver() == null) {
                arrayList.add(extensionReceiverParameter.getType());
            }
            IrSimpleType kFunctionType = IrUtilsKt.getKFunctionType(this.context.getIrBuiltIns(), irType2, arrayList);
            IrSimpleFunctionSymbol getter3 = expression.getGetter();
            Intrinsics.checkNotNull(getter3);
            int size = owner2.getTypeParameters().size();
            int size2 = owner2.getValueParameters().size();
            IrSimpleFunctionSymbol getter4 = expression.getGetter();
            Intrinsics.checkNotNull(getter4);
            irBlockBuilder = irBlockBuilder2;
            IrFunctionReferenceImpl irFunctionReferenceImpl2 = new IrFunctionReferenceImpl(startOffset, endOffset, kFunctionType, getter3, size, size2, getter4, null, 128, null);
            irFunctionReferenceImpl2.setDispatchReceiver(irVariableIrTemporary$default != null ? ExpressionHelpersKt.irGet(irBlockBuilder, irVariableIrTemporary$default) : null);
            irFunctionReferenceImpl2.setExtensionReceiver(irVariableIrTemporary$default2 != null ? ExpressionHelpersKt.irGet(irBlockBuilder, irVariableIrTemporary$default2) : null);
            int typeArgumentsCount = expression.getTypeArgumentsCount();
            for (int i3 = 0; i3 < typeArgumentsCount; i3++) {
                irFunctionReferenceImpl2.putTypeArgument(i3, expression.getTypeArgument(i3));
            }
            irFunctionReferenceImpl = irFunctionReferenceImpl2;
        }
        IrSimpleFunctionSymbol setter = expression.getSetter();
        if (setter == null || (owner = setter.getOwner()) == null || !isKMutablePropertyType(expression.getType())) {
            irType = irType2;
        } else {
            IrSimpleType kFunctionType2 = IrUtilsKt.getKFunctionType(this.context.getIrBuiltIns(), irBlockBuilder.getContext().getIrBuiltIns().getUnitType(), CollectionsKt.plus((Collection<? extends IrType>) arrayList, irType2));
            IrSimpleFunctionSymbol setter2 = expression.getSetter();
            Intrinsics.checkNotNull(setter2);
            int size3 = owner.getTypeParameters().size();
            int size4 = owner.getValueParameters().size();
            IrSimpleFunctionSymbol setter3 = expression.getSetter();
            Intrinsics.checkNotNull(setter3);
            irType = irType2;
            IrFunctionReferenceImpl irFunctionReferenceImpl3 = new IrFunctionReferenceImpl(startOffset, endOffset, kFunctionType2, setter2, size3, size4, setter3, null, 128, null);
            irFunctionReferenceImpl3.setDispatchReceiver(irVariableIrTemporary$default != null ? ExpressionHelpersKt.irGet(irBlockBuilder, irVariableIrTemporary$default) : null);
            irFunctionReferenceImpl3.setExtensionReceiver(irVariableIrTemporary$default2 != null ? ExpressionHelpersKt.irGet(irBlockBuilder, irVariableIrTemporary$default2) : null);
            int typeArgumentsCount2 = expression.getTypeArgumentsCount();
            for (int i4 = 0; i4 < typeArgumentsCount2; i4++) {
                irFunctionReferenceImpl3.putTypeArgument(i4, expression.getTypeArgument(i4));
            }
            irExpression = irFunctionReferenceImpl3;
        }
        Pair<IrConstructorSymbol, List<IrType>> kPropertyImplConstructor = getKPropertyImplConstructor(arrayList, irType, false, irExpression != null);
        IrConstructorSymbol irConstructorSymbolComponent1 = kPropertyImplConstructor.component1();
        List<IrType> listComponent2 = kPropertyImplConstructor.component2();
        IrBlockBuilder irBlockBuilder3 = irBlockBuilder;
        IrMemberAccessExpression<?> irMemberAccessExpressionIrCall = ExpressionHelpersKt.irCall(irBlockBuilder3, irConstructorSymbolComponent1, IrTypesKt.typeWith(IrTypesKt.getClassifierOrFail(irConstructorSymbolComponent1.getOwner().getReturnType()), listComponent2), listComponent2);
        String strAsString = expression.getSymbol().getOwner().getName().asString();
        Intrinsics.checkNotNullExpressionValue(strAsString, "asString(...)");
        irMemberAccessExpressionIrCall.putValueArgument(0, ExpressionHelpersKt.irString(irBlockBuilder3, strAsString));
        irMemberAccessExpressionIrCall.putValueArgument(1, kTypeGenerator.irKType(irBlockBuilder3, irType));
        if (irFunctionReferenceImpl != null) {
            irMemberAccessExpressionIrCall.putValueArgument(2, irFunctionReferenceImpl);
        }
        if (irExpression != null) {
            irMemberAccessExpressionIrCall.putValueArgument(3, irExpression);
        }
        irBlockBuilder.unaryPlus(irMemberAccessExpressionIrCall);
        return irBlockBuilder.getIrBlockBody();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final IrExpression createLocalKProperty(String propertyName, IrType propertyType, KTypeGeneratorInterface kTypeGenerator, IrBuilderWithScope irBuilder) {
        Pair<IrConstructorSymbol, List<IrType>> kPropertyImplConstructor = getKPropertyImplConstructor(CollectionsKt.emptyList(), propertyType, true, false);
        IrConstructorSymbol irConstructorSymbolComponent1 = kPropertyImplConstructor.component1();
        List<IrType> listComponent2 = kPropertyImplConstructor.component2();
        IrMemberAccessExpression<?> irMemberAccessExpressionIrCall = ExpressionHelpersKt.irCall(irBuilder, irConstructorSymbolComponent1, IrTypesKt.typeWith(IrTypesKt.getClassifierOrFail(irConstructorSymbolComponent1.getOwner().getReturnType()), listComponent2), listComponent2);
        irMemberAccessExpressionIrCall.putValueArgument(0, ExpressionHelpersKt.irString(irBuilder, propertyName));
        irMemberAccessExpressionIrCall.putValueArgument(1, kTypeGenerator.irKType(irBuilder, propertyType));
        return irMemberAccessExpressionIrCall;
    }

    private final boolean isKMutablePropertyType(IrType type) {
        IrClassSymbol kMutableProperty0;
        if (!(type instanceof IrSimpleType)) {
            return false;
        }
        IrSimpleType irSimpleType = (IrSimpleType) type;
        int size = irSimpleType.getArguments().size();
        if (size == 0) {
            return false;
        }
        if (size == 1) {
            kMutableProperty0 = this.symbols.getKMutableProperty0();
        } else if (size == 2) {
            kMutableProperty0 = this.symbols.getKMutableProperty1();
        } else if (size == 3) {
            kMutableProperty0 = this.symbols.getKMutableProperty2();
        } else {
            throw new IllegalStateException("More than 2 receivers is not allowed".toString());
        }
        return Intrinsics.areEqual(irSimpleType.getClassifier(), kMutableProperty0);
    }

    /* JADX INFO: compiled from: WasmPropertyReferenceLowering.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bÂ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002¨\u0006\u0003"}, d2 = {"Lorg/jetbrains/kotlin/backend/wasm/lower/WasmPropertyReferenceLowering$DECLARATION_ORIGIN_KPROPERTIES_FOR_DELEGATION;", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationOriginImpl;", "()V", "backend.wasm"}, k = 1, mv = {1, 9, 0}, xi = 48)
    private static final class DECLARATION_ORIGIN_KPROPERTIES_FOR_DELEGATION extends IrDeclarationOriginImpl {
        public static final DECLARATION_ORIGIN_KPROPERTIES_FOR_DELEGATION INSTANCE = new DECLARATION_ORIGIN_KPROPERTIES_FOR_DELEGATION();

        private DECLARATION_ORIGIN_KPROPERTIES_FOR_DELEGATION() {
            super("KPROPERTIES_FOR_DELEGATION", false, 2, null);
        }
    }
}
