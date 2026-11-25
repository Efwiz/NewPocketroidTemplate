package org.jetbrains.kotlin.backend.common.lower.inline;

import androidx.exifinterface.media.ExifInterface;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.collections.IntIterator;
import kotlin.collections.MapsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.IntRange;
import kotlin.ranges.RangesKt;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.backend.common.BodyLoweringPass;
import org.jetbrains.kotlin.backend.common.CommonBackendContext;
import org.jetbrains.kotlin.backend.common.IrElementTransformerVoidWithContext;
import org.jetbrains.kotlin.backend.common.ScopeWithIr;
import org.jetbrains.kotlin.backend.common.lower.DeclarationIrBuilder;
import org.jetbrains.kotlin.backend.common.lower.InnerClassesSupport;
import org.jetbrains.kotlin.backend.common.lower.LowerUtilsKt;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;
import org.jetbrains.kotlin.contracts.parsing.ContractsDslNames;
import org.jetbrains.kotlin.descriptors.DescriptorVisibilities;
import org.jetbrains.kotlin.descriptors.DescriptorVisibility;
import org.jetbrains.kotlin.ir.IrElement;
import org.jetbrains.kotlin.ir.IrStatement;
import org.jetbrains.kotlin.ir.builders.ExpressionHelpersKt;
import org.jetbrains.kotlin.ir.builders.IrBuilderWithScope;
import org.jetbrains.kotlin.ir.builders.Scope;
import org.jetbrains.kotlin.ir.builders.declarations.DeclarationBuildersKt;
import org.jetbrains.kotlin.ir.builders.declarations.IrFunctionBuilder;
import org.jetbrains.kotlin.ir.declarations.IrAnonymousInitializer;
import org.jetbrains.kotlin.ir.declarations.IrAttributeContainer;
import org.jetbrains.kotlin.ir.declarations.IrClass;
import org.jetbrains.kotlin.ir.declarations.IrConstructor;
import org.jetbrains.kotlin.ir.declarations.IrDeclaration;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationBase;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationOrigin;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationParent;
import org.jetbrains.kotlin.ir.declarations.IrEnumEntry;
import org.jetbrains.kotlin.ir.declarations.IrErrorDeclaration;
import org.jetbrains.kotlin.ir.declarations.IrExternalPackageFragment;
import org.jetbrains.kotlin.ir.declarations.IrFactory;
import org.jetbrains.kotlin.ir.declarations.IrField;
import org.jetbrains.kotlin.ir.declarations.IrFile;
import org.jetbrains.kotlin.ir.declarations.IrFunction;
import org.jetbrains.kotlin.ir.declarations.IrLocalDelegatedProperty;
import org.jetbrains.kotlin.ir.declarations.IrModuleFragment;
import org.jetbrains.kotlin.ir.declarations.IrPackageFragment;
import org.jetbrains.kotlin.ir.declarations.IrProperty;
import org.jetbrains.kotlin.ir.declarations.IrScript;
import org.jetbrains.kotlin.ir.declarations.IrSimpleFunction;
import org.jetbrains.kotlin.ir.declarations.IrSymbolOwner;
import org.jetbrains.kotlin.ir.declarations.IrTypeAlias;
import org.jetbrains.kotlin.ir.declarations.IrTypeParameter;
import org.jetbrains.kotlin.ir.declarations.IrValueDeclaration;
import org.jetbrains.kotlin.ir.declarations.IrValueParameter;
import org.jetbrains.kotlin.ir.declarations.IrVariable;
import org.jetbrains.kotlin.ir.expressions.IrBlock;
import org.jetbrains.kotlin.ir.expressions.IrBlockBody;
import org.jetbrains.kotlin.ir.expressions.IrBody;
import org.jetbrains.kotlin.ir.expressions.IrBranch;
import org.jetbrains.kotlin.ir.expressions.IrBreak;
import org.jetbrains.kotlin.ir.expressions.IrBreakContinue;
import org.jetbrains.kotlin.ir.expressions.IrCall;
import org.jetbrains.kotlin.ir.expressions.IrCallableReference;
import org.jetbrains.kotlin.ir.expressions.IrCatch;
import org.jetbrains.kotlin.ir.expressions.IrClassReference;
import org.jetbrains.kotlin.ir.expressions.IrComposite;
import org.jetbrains.kotlin.ir.expressions.IrConst;
import org.jetbrains.kotlin.ir.expressions.IrConstantArray;
import org.jetbrains.kotlin.ir.expressions.IrConstantObject;
import org.jetbrains.kotlin.ir.expressions.IrConstantPrimitive;
import org.jetbrains.kotlin.ir.expressions.IrConstantValue;
import org.jetbrains.kotlin.ir.expressions.IrConstructorCall;
import org.jetbrains.kotlin.ir.expressions.IrContainerExpression;
import org.jetbrains.kotlin.ir.expressions.IrContinue;
import org.jetbrains.kotlin.ir.expressions.IrDeclarationReference;
import org.jetbrains.kotlin.ir.expressions.IrDelegatingConstructorCall;
import org.jetbrains.kotlin.ir.expressions.IrDoWhileLoop;
import org.jetbrains.kotlin.ir.expressions.IrDynamicExpression;
import org.jetbrains.kotlin.ir.expressions.IrDynamicMemberExpression;
import org.jetbrains.kotlin.ir.expressions.IrDynamicOperatorExpression;
import org.jetbrains.kotlin.ir.expressions.IrElseBranch;
import org.jetbrains.kotlin.ir.expressions.IrEnumConstructorCall;
import org.jetbrains.kotlin.ir.expressions.IrErrorCallExpression;
import org.jetbrains.kotlin.ir.expressions.IrErrorExpression;
import org.jetbrains.kotlin.ir.expressions.IrExpression;
import org.jetbrains.kotlin.ir.expressions.IrExpressionBody;
import org.jetbrains.kotlin.ir.expressions.IrFieldAccessExpression;
import org.jetbrains.kotlin.ir.expressions.IrFunctionAccessExpression;
import org.jetbrains.kotlin.ir.expressions.IrFunctionExpression;
import org.jetbrains.kotlin.ir.expressions.IrFunctionReference;
import org.jetbrains.kotlin.ir.expressions.IrGetClass;
import org.jetbrains.kotlin.ir.expressions.IrGetEnumValue;
import org.jetbrains.kotlin.ir.expressions.IrGetField;
import org.jetbrains.kotlin.ir.expressions.IrGetObjectValue;
import org.jetbrains.kotlin.ir.expressions.IrGetSingletonValue;
import org.jetbrains.kotlin.ir.expressions.IrGetValue;
import org.jetbrains.kotlin.ir.expressions.IrInstanceInitializerCall;
import org.jetbrains.kotlin.ir.expressions.IrLocalDelegatedPropertyReference;
import org.jetbrains.kotlin.ir.expressions.IrLoop;
import org.jetbrains.kotlin.ir.expressions.IrMemberAccessExpression;
import org.jetbrains.kotlin.ir.expressions.IrPropertyReference;
import org.jetbrains.kotlin.ir.expressions.IrRawFunctionReference;
import org.jetbrains.kotlin.ir.expressions.IrReturn;
import org.jetbrains.kotlin.ir.expressions.IrReturnableBlock;
import org.jetbrains.kotlin.ir.expressions.IrSetField;
import org.jetbrains.kotlin.ir.expressions.IrSetValue;
import org.jetbrains.kotlin.ir.expressions.IrSpreadElement;
import org.jetbrains.kotlin.ir.expressions.IrStatementOrigin;
import org.jetbrains.kotlin.ir.expressions.IrStringConcatenation;
import org.jetbrains.kotlin.ir.expressions.IrSuspendableExpression;
import org.jetbrains.kotlin.ir.expressions.IrSuspensionPoint;
import org.jetbrains.kotlin.ir.expressions.IrSyntheticBody;
import org.jetbrains.kotlin.ir.expressions.IrThrow;
import org.jetbrains.kotlin.ir.expressions.IrTry;
import org.jetbrains.kotlin.ir.expressions.IrTypeOperator;
import org.jetbrains.kotlin.ir.expressions.IrTypeOperatorCall;
import org.jetbrains.kotlin.ir.expressions.IrValueAccessExpression;
import org.jetbrains.kotlin.ir.expressions.IrVararg;
import org.jetbrains.kotlin.ir.expressions.IrWhen;
import org.jetbrains.kotlin.ir.expressions.IrWhileLoop;
import org.jetbrains.kotlin.ir.expressions.impl.IrBlockImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrCallImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrCompositeImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrConstructorCallImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrGetFieldImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrGetValueImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrInlinedFunctionBlockImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrReturnImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrReturnableBlockImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrSpreadElementImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrVarargImpl;
import org.jetbrains.kotlin.ir.symbols.IrClassifierSymbol;
import org.jetbrains.kotlin.ir.symbols.IrFieldSymbol;
import org.jetbrains.kotlin.ir.symbols.IrFunctionSymbol;
import org.jetbrains.kotlin.ir.symbols.IrSimpleFunctionSymbol;
import org.jetbrains.kotlin.ir.symbols.IrTypeParameterSymbol;
import org.jetbrains.kotlin.ir.symbols.IrValueParameterSymbol;
import org.jetbrains.kotlin.ir.symbols.IrValueSymbol;
import org.jetbrains.kotlin.ir.symbols.impl.IrReturnableBlockSymbolImpl;
import org.jetbrains.kotlin.ir.types.IrSimpleType;
import org.jetbrains.kotlin.ir.types.IrType;
import org.jetbrains.kotlin.ir.types.IrTypeArgument;
import org.jetbrains.kotlin.ir.types.IrTypesKt;
import org.jetbrains.kotlin.ir.util.AdditionalIrUtilsKt;
import org.jetbrains.kotlin.ir.util.DumpIrTreeOptions;
import org.jetbrains.kotlin.ir.util.IrTypeUtilsKt;
import org.jetbrains.kotlin.ir.util.IrUtilsKt;
import org.jetbrains.kotlin.ir.util.PatchDeclarationParentsKt;
import org.jetbrains.kotlin.ir.util.RenderIrElementKt;
import org.jetbrains.kotlin.ir.visitors.IrElementTransformer;
import org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid;
import org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoidKt;
import org.jetbrains.kotlin.ir.visitors.IrElementVisitor;
import org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid;
import org.jetbrains.kotlin.ir.visitors.IrVisitorsKt;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.util.OperatorNameConventions;
import org.mozilla.javascript.NativeSymbol;

/* JADX INFO: compiled from: FunctionInlining.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000j\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u00002\u00020\u00012\u00020\u0002:\u0002)*B_\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\b\b\u0002\u0010\u0005\u001a\u00020\u0006\u0012\n\b\u0002\u0010\u0007\u001a\u0004\u0018\u00010\b\u0012\b\b\u0002\u0010\t\u001a\u00020\n\u0012\b\b\u0002\u0010\u000b\u001a\u00020\n\u0012\b\b\u0002\u0010\f\u001a\u00020\n\u0012\b\b\u0002\u0010\r\u001a\u00020\n\u0012\b\b\u0002\u0010\u000e\u001a\u00020\n\u0012\b\b\u0002\u0010\u000f\u001a\u00020\n¢\u0006\u0002\u0010\u0010J\u000e\u0010\u0019\u001a\u00020\u001a2\u0006\u0010\u001b\u001a\u00020\u001cJ\u0018\u0010\u001d\u001a\u00020\u001e2\u0006\u0010\u001f\u001a\u00020 2\u0006\u0010!\u001a\u00020\"H\u0016J\u0010\u0010#\u001a\u00020$2\u0006\u0010%\u001a\u00020&H\u0016J\f\u0010'\u001a\u00020(*\u00020(H\u0002R\u000e\u0010\u000e\u001a\u00020\nX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\nX\u0082\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\u0011\u001a\u0004\u0018\u00010\u0012X\u0082\u000e¢\u0006\u0002\n\u0000R\u0011\u0010\u0003\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u0014R\u000e\u0010\r\u001a\u00020\nX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\u0007\u001a\u0004\u0018\u00010\bX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\nX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\nX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u000f\u001a\u00020\nX\u0082\u0004¢\u0006\u0002\n\u0000R\u0018\u0010\u0015\u001a\u00020\n*\u00020\u00168BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\u0017\u0010\u0018¨\u0006+"}, d2 = {"Lorg/jetbrains/kotlin/backend/common/lower/inline/FunctionInlining;", "Lorg/jetbrains/kotlin/backend/common/IrElementTransformerVoidWithContext;", "Lorg/jetbrains/kotlin/backend/common/BodyLoweringPass;", "context", "Lorg/jetbrains/kotlin/backend/common/CommonBackendContext;", "inlineFunctionResolver", "Lorg/jetbrains/kotlin/backend/common/lower/inline/InlineFunctionResolver;", "innerClassesSupport", "Lorg/jetbrains/kotlin/backend/common/lower/InnerClassesSupport;", "insertAdditionalImplicitCasts", "", "alwaysCreateTemporaryVariablesForArguments", "regenerateInlinedAnonymousObjects", "inlineArgumentsWithTheirOriginalTypeAndOffset", "allowExternalInlining", "useTypeParameterUpperBound", "(Lorg/jetbrains/kotlin/backend/common/CommonBackendContext;Lorg/jetbrains/kotlin/backend/common/lower/inline/InlineFunctionResolver;Lorg/jetbrains/kotlin/backend/common/lower/InnerClassesSupport;ZZZZZZ)V", "containerScope", "Lorg/jetbrains/kotlin/backend/common/ScopeWithIr;", "getContext", "()Lorg/jetbrains/kotlin/backend/common/CommonBackendContext;", "needsInlining", "Lorg/jetbrains/kotlin/ir/declarations/IrFunction;", "getNeedsInlining", "(Lorg/jetbrains/kotlin/ir/declarations/IrFunction;)Z", "inline", "Lorg/jetbrains/kotlin/ir/IrElement;", "irModule", "Lorg/jetbrains/kotlin/ir/declarations/IrModuleFragment;", "lower", "", "irBody", "Lorg/jetbrains/kotlin/ir/expressions/IrBody;", "container", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclaration;", "visitFunctionAccess", "Lorg/jetbrains/kotlin/ir/expressions/IrExpression;", "expression", "Lorg/jetbrains/kotlin/ir/expressions/IrFunctionAccessExpression;", "markAsRegenerated", "Lorg/jetbrains/kotlin/ir/expressions/IrReturnableBlock;", "Inliner", "IrGetValueWithoutLocation", "ir.backend.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FunctionInlining extends IrElementTransformerVoidWithContext implements BodyLoweringPass {
    private final boolean allowExternalInlining;
    private final boolean alwaysCreateTemporaryVariablesForArguments;
    private ScopeWithIr containerScope;
    private final CommonBackendContext context;
    private final boolean inlineArgumentsWithTheirOriginalTypeAndOffset;
    private final InlineFunctionResolver inlineFunctionResolver;
    private final InnerClassesSupport innerClassesSupport;
    private final boolean insertAdditionalImplicitCasts;
    private final boolean regenerateInlinedAnonymousObjects;
    private final boolean useTypeParameterUpperBound;

    public final CommonBackendContext getContext() {
        return this.context;
    }

    public /* synthetic */ FunctionInlining(CommonBackendContext commonBackendContext, InlineFunctionResolver inlineFunctionResolver, InnerClassesSupport innerClassesSupport, boolean z, boolean z2, boolean z3, boolean z4, boolean z5, boolean z6, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(commonBackendContext, (i & 2) != 0 ? new DefaultInlineFunctionResolver(commonBackendContext) : inlineFunctionResolver, (i & 4) != 0 ? null : innerClassesSupport, (i & 8) != 0 ? false : z, (i & 16) != 0 ? false : z2, (i & 32) != 0 ? false : z3, (i & 64) != 0 ? false : z4, (i & 128) != 0 ? false : z5, (i & 256) == 0 ? z6 : false);
    }

    public FunctionInlining(CommonBackendContext context, InlineFunctionResolver inlineFunctionResolver, InnerClassesSupport innerClassesSupport, boolean z, boolean z2, boolean z3, boolean z4, boolean z5, boolean z6) {
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(inlineFunctionResolver, "inlineFunctionResolver");
        this.context = context;
        this.inlineFunctionResolver = inlineFunctionResolver;
        this.innerClassesSupport = innerClassesSupport;
        this.insertAdditionalImplicitCasts = z;
        this.alwaysCreateTemporaryVariablesForArguments = z2;
        this.regenerateInlinedAnonymousObjects = z3;
        this.inlineArgumentsWithTheirOriginalTypeAndOffset = z4;
        this.allowExternalInlining = z5;
        this.useTypeParameterUpperBound = z6;
    }

    @Override // org.jetbrains.kotlin.backend.common.BodyLoweringPass
    public void lower(IrBody irBody, IrDeclaration container) {
        Intrinsics.checkNotNullParameter(irBody, "irBody");
        Intrinsics.checkNotNullParameter(container, "container");
        this.containerScope = createScope(container);
        irBody.accept(this, null);
        this.containerScope = null;
        IrBody irBody2 = irBody;
        IrDeclarationParent parent = container instanceof IrDeclarationParent ? (IrDeclarationParent) container : null;
        if (parent == null) {
            parent = container.getParent();
        }
        PatchDeclarationParentsKt.patchDeclarationParents(irBody2, parent);
    }

    public final IrElement inline(IrModuleFragment irModule) {
        Intrinsics.checkNotNullParameter(irModule, "irModule");
        return (IrElement) irModule.accept(this, null);
    }

    /* JADX WARN: Code duplicated, block: B:67:0x014f A[PHI: r0
      0x014f: PHI (r0v22 org.jetbrains.kotlin.ir.declarations.IrDeclarationParent) = 
      (r0v21 org.jetbrains.kotlin.ir.declarations.IrDeclarationParent)
      (r0v33 org.jetbrains.kotlin.ir.declarations.IrDeclarationParent)
      (r0v38 org.jetbrains.kotlin.ir.declarations.IrDeclarationParent)
      (r0v44 org.jetbrains.kotlin.ir.declarations.IrDeclarationParent)
     binds: [B:31:0x00c1, B:46:0x011e, B:55:0x0132, B:65:0x0148] A[DONT_GENERATE, DONT_INLINE]] */
    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
    public IrExpression visitFunctionAccess(IrFunctionAccessExpression expression) {
        IrFunction owner;
        IrDeclarationParent irDeclarationParent;
        Intrinsics.checkNotNullParameter(expression, "expression");
        IrElementTransformerVoidKt.transformChildrenVoid(expression, this);
        if (expression instanceof IrCall) {
            owner = ((IrCall) expression).getSymbol().getOwner();
        } else {
            if (!(expression instanceof IrConstructorCall)) {
                return expression;
            }
            owner = ((IrConstructorCall) expression).getSymbol().getOwner();
        }
        if (!getNeedsInlining(owner) || this.inlineFunctionResolver.shouldExcludeFunctionFromInlining(owner.getSymbol())) {
            return expression;
        }
        IrFunction functionDeclaration = this.inlineFunctionResolver.getFunctionDeclaration(owner.getSymbol());
        if (functionDeclaration.getBody() == null) {
            return expression;
        }
        FunctionInlining functionInlining = this;
        functionInlining.unsafeEnterScope(functionDeclaration);
        IrBody body = functionDeclaration.getBody();
        if (body != null) {
            transformChildrenVoid(body);
            Unit unit = Unit.INSTANCE;
        }
        functionInlining.unsafeLeaveScope();
        List<ScopeWithIr> allScopes = getAllScopes();
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(allScopes, 10));
        Iterator<T> it = allScopes.iterator();
        while (it.hasNext()) {
            arrayList.add(((ScopeWithIr) it.next()).getIrElement());
        }
        ArrayList arrayList2 = new ArrayList();
        for (Object obj : arrayList) {
            if (obj instanceof IrDeclarationParent) {
                arrayList2.add(obj);
            }
        }
        IrDeclarationParent parent = (IrDeclarationParent) CollectionsKt.lastOrNull((List) arrayList2);
        if (parent == null) {
            List<ScopeWithIr> allScopes2 = getAllScopes();
            ArrayList arrayList3 = new ArrayList(CollectionsKt.collectionSizeOrDefault(allScopes2, 10));
            Iterator<T> it2 = allScopes2.iterator();
            while (it2.hasNext()) {
                arrayList3.add(((ScopeWithIr) it2.next()).getIrElement());
            }
            ArrayList arrayList4 = new ArrayList();
            for (Object obj2 : arrayList3) {
                if (obj2 instanceof IrDeclaration) {
                    arrayList4.add(obj2);
                }
            }
            IrDeclaration irDeclaration = (IrDeclaration) CollectionsKt.lastOrNull((List) arrayList4);
            parent = irDeclaration != null ? irDeclaration.getParent() : null;
            if (parent != null) {
                irDeclarationParent = parent;
            } else {
                ScopeWithIr scopeWithIr = this.containerScope;
                IrElement irElement = scopeWithIr != null ? scopeWithIr.getIrElement() : null;
                parent = irElement instanceof IrDeclarationParent ? (IrDeclarationParent) irElement : null;
                if (parent != null) {
                    irDeclarationParent = parent;
                } else {
                    ScopeWithIr scopeWithIr2 = this.containerScope;
                    IrElement irElement2 = scopeWithIr2 != null ? scopeWithIr2.getIrElement() : null;
                    IrDeclaration irDeclaration2 = irElement2 instanceof IrDeclaration ? (IrDeclaration) irElement2 : null;
                    if (irDeclaration2 != null) {
                        parent = irDeclaration2.getParent();
                        irDeclarationParent = parent;
                    } else {
                        irDeclarationParent = null;
                    }
                }
            }
        } else {
            irDeclarationParent = parent;
        }
        ScopeWithIr currentScope = getCurrentScope();
        if (currentScope == null) {
            currentScope = this.containerScope;
            Intrinsics.checkNotNull(currentScope);
        }
        return markAsRegenerated(new Inliner(this, expression, functionDeclaration, currentScope, irDeclarationParent, this.context).inline());
    }

    private final IrReturnableBlock markAsRegenerated(IrReturnableBlock irReturnableBlock) {
        if (!this.regenerateInlinedAnonymousObjects) {
            return irReturnableBlock;
        }
        IrVisitorsKt.acceptVoid(irReturnableBlock, new IrElementVisitorVoid() { // from class: org.jetbrains.kotlin.backend.common.lower.inline.FunctionInlining.markAsRegenerated.1
            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitAnonymousInitializer */
            public /* bridge */ /* synthetic */ Object visitAnonymousInitializer2(IrAnonymousInitializer irAnonymousInitializer, Object obj) {
                visitAnonymousInitializer(irAnonymousInitializer, (Void) obj);
                return Unit.INSTANCE;
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitAnonymousInitializer(IrAnonymousInitializer irAnonymousInitializer) {
                IrElementVisitorVoid.DefaultImpls.visitAnonymousInitializer(this, irAnonymousInitializer);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitAnonymousInitializer(IrAnonymousInitializer irAnonymousInitializer, Void r2) {
                IrElementVisitorVoid.DefaultImpls.visitAnonymousInitializer(this, irAnonymousInitializer, r2);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitBlock */
            public /* bridge */ /* synthetic */ Object visitBlock2(IrBlock irBlock, Object obj) {
                visitBlock(irBlock, (Void) obj);
                return Unit.INSTANCE;
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitBlock(IrBlock irBlock) {
                IrElementVisitorVoid.DefaultImpls.visitBlock(this, irBlock);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitBlock(IrBlock irBlock, Void r2) {
                IrElementVisitorVoid.DefaultImpls.visitBlock(this, irBlock, r2);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitBlockBody */
            public /* bridge */ /* synthetic */ Object visitBlockBody2(IrBlockBody irBlockBody, Object obj) {
                visitBlockBody(irBlockBody, (Void) obj);
                return Unit.INSTANCE;
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitBlockBody(IrBlockBody irBlockBody) {
                IrElementVisitorVoid.DefaultImpls.visitBlockBody(this, irBlockBody);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitBlockBody(IrBlockBody irBlockBody, Void r2) {
                IrElementVisitorVoid.DefaultImpls.visitBlockBody(this, irBlockBody, r2);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitBody */
            public /* bridge */ /* synthetic */ Object visitBody2(IrBody irBody, Object obj) {
                visitBody(irBody, (Void) obj);
                return Unit.INSTANCE;
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitBody(IrBody irBody) {
                IrElementVisitorVoid.DefaultImpls.visitBody(this, irBody);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitBody(IrBody irBody, Void r2) {
                IrElementVisitorVoid.DefaultImpls.visitBody(this, irBody, r2);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitBranch */
            public /* bridge */ /* synthetic */ Object visitBranch2(IrBranch irBranch, Object obj) {
                visitBranch(irBranch, (Void) obj);
                return Unit.INSTANCE;
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitBranch(IrBranch irBranch) {
                IrElementVisitorVoid.DefaultImpls.visitBranch(this, irBranch);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitBranch(IrBranch irBranch, Void r2) {
                IrElementVisitorVoid.DefaultImpls.visitBranch(this, irBranch, r2);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitBreak */
            public /* bridge */ /* synthetic */ Object visitBreak2(IrBreak irBreak, Object obj) {
                visitBreak(irBreak, (Void) obj);
                return Unit.INSTANCE;
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitBreak(IrBreak irBreak) {
                IrElementVisitorVoid.DefaultImpls.visitBreak(this, irBreak);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitBreak(IrBreak irBreak, Void r2) {
                IrElementVisitorVoid.DefaultImpls.visitBreak(this, irBreak, r2);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitBreakContinue */
            public /* bridge */ /* synthetic */ Object visitBreakContinue2(IrBreakContinue irBreakContinue, Object obj) {
                visitBreakContinue(irBreakContinue, (Void) obj);
                return Unit.INSTANCE;
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitBreakContinue(IrBreakContinue irBreakContinue) {
                IrElementVisitorVoid.DefaultImpls.visitBreakContinue(this, irBreakContinue);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitBreakContinue(IrBreakContinue irBreakContinue, Void r2) {
                IrElementVisitorVoid.DefaultImpls.visitBreakContinue(this, irBreakContinue, r2);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitCall */
            public /* bridge */ /* synthetic */ Object visitCall2(IrCall irCall, Object obj) {
                visitCall(irCall, (Void) obj);
                return Unit.INSTANCE;
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitCall(IrCall irCall) {
                IrElementVisitorVoid.DefaultImpls.visitCall(this, irCall);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitCall(IrCall irCall, Void r2) {
                IrElementVisitorVoid.DefaultImpls.visitCall(this, irCall, r2);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            public /* bridge */ /* synthetic */ Object visitCallableReference(IrCallableReference irCallableReference, Object obj) {
                visitCallableReference((IrCallableReference<?>) irCallableReference, (Void) obj);
                return Unit.INSTANCE;
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitCallableReference(IrCallableReference<?> irCallableReference) {
                IrElementVisitorVoid.DefaultImpls.visitCallableReference(this, irCallableReference);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitCallableReference(IrCallableReference<?> irCallableReference, Void r2) {
                IrElementVisitorVoid.DefaultImpls.visitCallableReference(this, irCallableReference, r2);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitCatch */
            public /* bridge */ /* synthetic */ Object visitCatch2(IrCatch irCatch, Object obj) {
                visitCatch(irCatch, (Void) obj);
                return Unit.INSTANCE;
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitCatch(IrCatch irCatch) {
                IrElementVisitorVoid.DefaultImpls.visitCatch(this, irCatch);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitCatch(IrCatch irCatch, Void r2) {
                IrElementVisitorVoid.DefaultImpls.visitCatch(this, irCatch, r2);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitClass */
            public /* bridge */ /* synthetic */ Object visitClass2(IrClass irClass, Object obj) {
                visitClass(irClass, (Void) obj);
                return Unit.INSTANCE;
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            /* JADX INFO: renamed from: visitClass */
            public void mo6112visitClass(IrClass irClass) {
                IrElementVisitorVoid.DefaultImpls.visitClass(this, irClass);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitClass(IrClass irClass, Void r2) {
                IrElementVisitorVoid.DefaultImpls.visitClass(this, irClass, r2);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitClassReference */
            public /* bridge */ /* synthetic */ Object visitClassReference2(IrClassReference irClassReference, Object obj) {
                visitClassReference(irClassReference, (Void) obj);
                return Unit.INSTANCE;
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitClassReference(IrClassReference irClassReference) {
                IrElementVisitorVoid.DefaultImpls.visitClassReference(this, irClassReference);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitClassReference(IrClassReference irClassReference, Void r2) {
                IrElementVisitorVoid.DefaultImpls.visitClassReference(this, irClassReference, r2);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitComposite */
            public /* bridge */ /* synthetic */ Object visitComposite2(IrComposite irComposite, Object obj) {
                visitComposite(irComposite, (Void) obj);
                return Unit.INSTANCE;
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitComposite(IrComposite irComposite) {
                IrElementVisitorVoid.DefaultImpls.visitComposite(this, irComposite);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitComposite(IrComposite irComposite, Void r2) {
                IrElementVisitorVoid.DefaultImpls.visitComposite(this, irComposite, r2);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            public /* bridge */ /* synthetic */ Object visitConst(IrConst irConst, Object obj) {
                visitConst((IrConst<?>) irConst, (Void) obj);
                return Unit.INSTANCE;
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitConst(IrConst<?> irConst) {
                IrElementVisitorVoid.DefaultImpls.visitConst(this, irConst);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitConst(IrConst<?> irConst, Void r2) {
                IrElementVisitorVoid.DefaultImpls.visitConst(this, irConst, r2);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitConstantArray */
            public /* bridge */ /* synthetic */ Object visitConstantArray2(IrConstantArray irConstantArray, Object obj) {
                visitConstantArray(irConstantArray, (Void) obj);
                return Unit.INSTANCE;
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitConstantArray(IrConstantArray irConstantArray) {
                IrElementVisitorVoid.DefaultImpls.visitConstantArray(this, irConstantArray);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitConstantArray(IrConstantArray irConstantArray, Void r2) {
                IrElementVisitorVoid.DefaultImpls.visitConstantArray(this, irConstantArray, r2);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitConstantObject */
            public /* bridge */ /* synthetic */ Object visitConstantObject2(IrConstantObject irConstantObject, Object obj) {
                visitConstantObject(irConstantObject, (Void) obj);
                return Unit.INSTANCE;
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitConstantObject(IrConstantObject irConstantObject) {
                IrElementVisitorVoid.DefaultImpls.visitConstantObject(this, irConstantObject);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitConstantObject(IrConstantObject irConstantObject, Void r2) {
                IrElementVisitorVoid.DefaultImpls.visitConstantObject(this, irConstantObject, r2);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitConstantPrimitive */
            public /* bridge */ /* synthetic */ Object visitConstantPrimitive2(IrConstantPrimitive irConstantPrimitive, Object obj) {
                visitConstantPrimitive(irConstantPrimitive, (Void) obj);
                return Unit.INSTANCE;
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitConstantPrimitive(IrConstantPrimitive irConstantPrimitive) {
                IrElementVisitorVoid.DefaultImpls.visitConstantPrimitive(this, irConstantPrimitive);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitConstantPrimitive(IrConstantPrimitive irConstantPrimitive, Void r2) {
                IrElementVisitorVoid.DefaultImpls.visitConstantPrimitive(this, irConstantPrimitive, r2);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitConstantValue */
            public /* bridge */ /* synthetic */ Object visitConstantValue2(IrConstantValue irConstantValue, Object obj) {
                visitConstantValue(irConstantValue, (Void) obj);
                return Unit.INSTANCE;
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitConstantValue(IrConstantValue irConstantValue) {
                IrElementVisitorVoid.DefaultImpls.visitConstantValue(this, irConstantValue);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitConstantValue(IrConstantValue irConstantValue, Void r2) {
                IrElementVisitorVoid.DefaultImpls.visitConstantValue(this, irConstantValue, r2);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitConstructor */
            public /* bridge */ /* synthetic */ Object visitConstructor2(IrConstructor irConstructor, Object obj) {
                visitConstructor(irConstructor, (Void) obj);
                return Unit.INSTANCE;
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitConstructor(IrConstructor irConstructor) {
                IrElementVisitorVoid.DefaultImpls.visitConstructor(this, irConstructor);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitConstructor(IrConstructor irConstructor, Void r2) {
                IrElementVisitorVoid.DefaultImpls.visitConstructor(this, irConstructor, r2);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitConstructorCall */
            public /* bridge */ /* synthetic */ Object visitConstructorCall2(IrConstructorCall irConstructorCall, Object obj) {
                visitConstructorCall(irConstructorCall, (Void) obj);
                return Unit.INSTANCE;
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitConstructorCall(IrConstructorCall irConstructorCall) {
                IrElementVisitorVoid.DefaultImpls.visitConstructorCall(this, irConstructorCall);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitConstructorCall(IrConstructorCall irConstructorCall, Void r2) {
                IrElementVisitorVoid.DefaultImpls.visitConstructorCall(this, irConstructorCall, r2);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitContainerExpression */
            public /* bridge */ /* synthetic */ Object visitContainerExpression2(IrContainerExpression irContainerExpression, Object obj) {
                visitContainerExpression(irContainerExpression, (Void) obj);
                return Unit.INSTANCE;
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitContainerExpression(IrContainerExpression irContainerExpression) {
                IrElementVisitorVoid.DefaultImpls.visitContainerExpression(this, irContainerExpression);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitContainerExpression(IrContainerExpression irContainerExpression, Void r2) {
                IrElementVisitorVoid.DefaultImpls.visitContainerExpression(this, irContainerExpression, r2);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitContinue */
            public /* bridge */ /* synthetic */ Object visitContinue2(IrContinue irContinue, Object obj) {
                visitContinue(irContinue, (Void) obj);
                return Unit.INSTANCE;
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitContinue(IrContinue irContinue) {
                IrElementVisitorVoid.DefaultImpls.visitContinue(this, irContinue);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitContinue(IrContinue irContinue, Void r2) {
                IrElementVisitorVoid.DefaultImpls.visitContinue(this, irContinue, r2);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitDeclaration */
            public /* bridge */ /* synthetic */ Object visitDeclaration2(IrDeclarationBase irDeclarationBase, Object obj) {
                visitDeclaration(irDeclarationBase, (Void) obj);
                return Unit.INSTANCE;
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitDeclaration(IrDeclarationBase irDeclarationBase) {
                IrElementVisitorVoid.DefaultImpls.visitDeclaration(this, irDeclarationBase);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitDeclaration(IrDeclarationBase irDeclarationBase, Void r2) {
                IrElementVisitorVoid.DefaultImpls.visitDeclaration(this, irDeclarationBase, r2);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitDeclarationReference */
            public /* bridge */ /* synthetic */ Object visitDeclarationReference2(IrDeclarationReference irDeclarationReference, Object obj) {
                visitDeclarationReference(irDeclarationReference, (Void) obj);
                return Unit.INSTANCE;
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitDeclarationReference(IrDeclarationReference irDeclarationReference) {
                IrElementVisitorVoid.DefaultImpls.visitDeclarationReference(this, irDeclarationReference);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitDeclarationReference(IrDeclarationReference irDeclarationReference, Void r2) {
                IrElementVisitorVoid.DefaultImpls.visitDeclarationReference(this, irDeclarationReference, r2);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitDelegatingConstructorCall */
            public /* bridge */ /* synthetic */ Object visitDelegatingConstructorCall2(IrDelegatingConstructorCall irDelegatingConstructorCall, Object obj) {
                visitDelegatingConstructorCall(irDelegatingConstructorCall, (Void) obj);
                return Unit.INSTANCE;
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitDelegatingConstructorCall(IrDelegatingConstructorCall irDelegatingConstructorCall) {
                IrElementVisitorVoid.DefaultImpls.visitDelegatingConstructorCall(this, irDelegatingConstructorCall);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitDelegatingConstructorCall(IrDelegatingConstructorCall irDelegatingConstructorCall, Void r2) {
                IrElementVisitorVoid.DefaultImpls.visitDelegatingConstructorCall(this, irDelegatingConstructorCall, r2);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitDoWhileLoop */
            public /* bridge */ /* synthetic */ Object visitDoWhileLoop2(IrDoWhileLoop irDoWhileLoop, Object obj) {
                visitDoWhileLoop(irDoWhileLoop, (Void) obj);
                return Unit.INSTANCE;
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitDoWhileLoop(IrDoWhileLoop irDoWhileLoop) {
                IrElementVisitorVoid.DefaultImpls.visitDoWhileLoop(this, irDoWhileLoop);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitDoWhileLoop(IrDoWhileLoop irDoWhileLoop, Void r2) {
                IrElementVisitorVoid.DefaultImpls.visitDoWhileLoop(this, irDoWhileLoop, r2);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitDynamicExpression */
            public /* bridge */ /* synthetic */ Object visitDynamicExpression2(IrDynamicExpression irDynamicExpression, Object obj) {
                visitDynamicExpression(irDynamicExpression, (Void) obj);
                return Unit.INSTANCE;
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitDynamicExpression(IrDynamicExpression irDynamicExpression) {
                IrElementVisitorVoid.DefaultImpls.visitDynamicExpression(this, irDynamicExpression);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitDynamicExpression(IrDynamicExpression irDynamicExpression, Void r2) {
                IrElementVisitorVoid.DefaultImpls.visitDynamicExpression(this, irDynamicExpression, r2);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitDynamicMemberExpression */
            public /* bridge */ /* synthetic */ Object visitDynamicMemberExpression2(IrDynamicMemberExpression irDynamicMemberExpression, Object obj) {
                visitDynamicMemberExpression(irDynamicMemberExpression, (Void) obj);
                return Unit.INSTANCE;
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitDynamicMemberExpression(IrDynamicMemberExpression irDynamicMemberExpression) {
                IrElementVisitorVoid.DefaultImpls.visitDynamicMemberExpression(this, irDynamicMemberExpression);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitDynamicMemberExpression(IrDynamicMemberExpression irDynamicMemberExpression, Void r2) {
                IrElementVisitorVoid.DefaultImpls.visitDynamicMemberExpression(this, irDynamicMemberExpression, r2);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitDynamicOperatorExpression */
            public /* bridge */ /* synthetic */ Object visitDynamicOperatorExpression2(IrDynamicOperatorExpression irDynamicOperatorExpression, Object obj) {
                visitDynamicOperatorExpression(irDynamicOperatorExpression, (Void) obj);
                return Unit.INSTANCE;
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitDynamicOperatorExpression(IrDynamicOperatorExpression irDynamicOperatorExpression) {
                IrElementVisitorVoid.DefaultImpls.visitDynamicOperatorExpression(this, irDynamicOperatorExpression);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitDynamicOperatorExpression(IrDynamicOperatorExpression irDynamicOperatorExpression, Void r2) {
                IrElementVisitorVoid.DefaultImpls.visitDynamicOperatorExpression(this, irDynamicOperatorExpression, r2);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitElement */
            public /* bridge */ /* synthetic */ Object visitElement2(IrElement irElement, Object obj) {
                visitElement(irElement, (Void) obj);
                return Unit.INSTANCE;
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitElement(IrElement irElement, Void r2) {
                IrElementVisitorVoid.DefaultImpls.visitElement(this, irElement, r2);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitElseBranch */
            public /* bridge */ /* synthetic */ Object visitElseBranch2(IrElseBranch irElseBranch, Object obj) {
                visitElseBranch(irElseBranch, (Void) obj);
                return Unit.INSTANCE;
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitElseBranch(IrElseBranch irElseBranch) {
                IrElementVisitorVoid.DefaultImpls.visitElseBranch(this, irElseBranch);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitElseBranch(IrElseBranch irElseBranch, Void r2) {
                IrElementVisitorVoid.DefaultImpls.visitElseBranch(this, irElseBranch, r2);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitEnumConstructorCall */
            public /* bridge */ /* synthetic */ Object visitEnumConstructorCall2(IrEnumConstructorCall irEnumConstructorCall, Object obj) {
                visitEnumConstructorCall(irEnumConstructorCall, (Void) obj);
                return Unit.INSTANCE;
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitEnumConstructorCall(IrEnumConstructorCall irEnumConstructorCall) {
                IrElementVisitorVoid.DefaultImpls.visitEnumConstructorCall(this, irEnumConstructorCall);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitEnumConstructorCall(IrEnumConstructorCall irEnumConstructorCall, Void r2) {
                IrElementVisitorVoid.DefaultImpls.visitEnumConstructorCall(this, irEnumConstructorCall, r2);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitEnumEntry */
            public /* bridge */ /* synthetic */ Object visitEnumEntry2(IrEnumEntry irEnumEntry, Object obj) {
                visitEnumEntry(irEnumEntry, (Void) obj);
                return Unit.INSTANCE;
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitEnumEntry(IrEnumEntry irEnumEntry) {
                IrElementVisitorVoid.DefaultImpls.visitEnumEntry(this, irEnumEntry);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitEnumEntry(IrEnumEntry irEnumEntry, Void r2) {
                IrElementVisitorVoid.DefaultImpls.visitEnumEntry(this, irEnumEntry, r2);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitErrorCallExpression */
            public /* bridge */ /* synthetic */ Object visitErrorCallExpression2(IrErrorCallExpression irErrorCallExpression, Object obj) {
                visitErrorCallExpression(irErrorCallExpression, (Void) obj);
                return Unit.INSTANCE;
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitErrorCallExpression(IrErrorCallExpression irErrorCallExpression) {
                IrElementVisitorVoid.DefaultImpls.visitErrorCallExpression(this, irErrorCallExpression);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitErrorCallExpression(IrErrorCallExpression irErrorCallExpression, Void r2) {
                IrElementVisitorVoid.DefaultImpls.visitErrorCallExpression(this, irErrorCallExpression, r2);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitErrorDeclaration */
            public /* bridge */ /* synthetic */ Object visitErrorDeclaration2(IrErrorDeclaration irErrorDeclaration, Object obj) {
                visitErrorDeclaration(irErrorDeclaration, (Void) obj);
                return Unit.INSTANCE;
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitErrorDeclaration(IrErrorDeclaration irErrorDeclaration) {
                IrElementVisitorVoid.DefaultImpls.visitErrorDeclaration(this, irErrorDeclaration);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitErrorDeclaration(IrErrorDeclaration irErrorDeclaration, Void r2) {
                IrElementVisitorVoid.DefaultImpls.visitErrorDeclaration(this, irErrorDeclaration, r2);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitErrorExpression */
            public /* bridge */ /* synthetic */ Object visitErrorExpression2(IrErrorExpression irErrorExpression, Object obj) {
                visitErrorExpression(irErrorExpression, (Void) obj);
                return Unit.INSTANCE;
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitErrorExpression(IrErrorExpression irErrorExpression) {
                IrElementVisitorVoid.DefaultImpls.visitErrorExpression(this, irErrorExpression);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitErrorExpression(IrErrorExpression irErrorExpression, Void r2) {
                IrElementVisitorVoid.DefaultImpls.visitErrorExpression(this, irErrorExpression, r2);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitExpression */
            public /* bridge */ /* synthetic */ Object visitExpression2(IrExpression irExpression, Object obj) {
                visitExpression(irExpression, (Void) obj);
                return Unit.INSTANCE;
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitExpression(IrExpression irExpression) {
                IrElementVisitorVoid.DefaultImpls.visitExpression(this, irExpression);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitExpression(IrExpression irExpression, Void r2) {
                IrElementVisitorVoid.DefaultImpls.visitExpression(this, irExpression, r2);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitExpressionBody */
            public /* bridge */ /* synthetic */ Object visitExpressionBody2(IrExpressionBody irExpressionBody, Object obj) {
                visitExpressionBody(irExpressionBody, (Void) obj);
                return Unit.INSTANCE;
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitExpressionBody(IrExpressionBody irExpressionBody) {
                IrElementVisitorVoid.DefaultImpls.visitExpressionBody(this, irExpressionBody);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitExpressionBody(IrExpressionBody irExpressionBody, Void r2) {
                IrElementVisitorVoid.DefaultImpls.visitExpressionBody(this, irExpressionBody, r2);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitExternalPackageFragment */
            public /* bridge */ /* synthetic */ Object visitExternalPackageFragment2(IrExternalPackageFragment irExternalPackageFragment, Object obj) {
                visitExternalPackageFragment(irExternalPackageFragment, (Void) obj);
                return Unit.INSTANCE;
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitExternalPackageFragment(IrExternalPackageFragment irExternalPackageFragment) {
                IrElementVisitorVoid.DefaultImpls.visitExternalPackageFragment(this, irExternalPackageFragment);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitExternalPackageFragment(IrExternalPackageFragment irExternalPackageFragment, Void r2) {
                IrElementVisitorVoid.DefaultImpls.visitExternalPackageFragment(this, irExternalPackageFragment, r2);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitField */
            public /* bridge */ /* synthetic */ Object visitField2(IrField irField, Object obj) {
                visitField(irField, (Void) obj);
                return Unit.INSTANCE;
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitField(IrField irField) {
                IrElementVisitorVoid.DefaultImpls.visitField(this, irField);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitField(IrField irField, Void r2) {
                IrElementVisitorVoid.DefaultImpls.visitField(this, irField, r2);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitFieldAccess */
            public /* bridge */ /* synthetic */ Object visitFieldAccess2(IrFieldAccessExpression irFieldAccessExpression, Object obj) {
                visitFieldAccess(irFieldAccessExpression, (Void) obj);
                return Unit.INSTANCE;
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitFieldAccess(IrFieldAccessExpression irFieldAccessExpression) {
                IrElementVisitorVoid.DefaultImpls.visitFieldAccess(this, irFieldAccessExpression);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitFieldAccess(IrFieldAccessExpression irFieldAccessExpression, Void r2) {
                IrElementVisitorVoid.DefaultImpls.visitFieldAccess(this, irFieldAccessExpression, r2);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitFile */
            public /* bridge */ /* synthetic */ Object visitFile2(IrFile irFile, Object obj) {
                visitFile(irFile, (Void) obj);
                return Unit.INSTANCE;
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitFile(IrFile irFile) {
                IrElementVisitorVoid.DefaultImpls.visitFile(this, irFile);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitFile(IrFile irFile, Void r2) {
                IrElementVisitorVoid.DefaultImpls.visitFile(this, irFile, r2);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitFunction */
            public /* bridge */ /* synthetic */ Object visitFunction2(IrFunction irFunction, Object obj) {
                visitFunction(irFunction, (Void) obj);
                return Unit.INSTANCE;
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitFunction(IrFunction irFunction) {
                IrElementVisitorVoid.DefaultImpls.visitFunction(this, irFunction);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitFunction(IrFunction irFunction, Void r2) {
                IrElementVisitorVoid.DefaultImpls.visitFunction(this, irFunction, r2);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitFunctionAccess */
            public /* bridge */ /* synthetic */ Object visitFunctionAccess2(IrFunctionAccessExpression irFunctionAccessExpression, Object obj) {
                visitFunctionAccess(irFunctionAccessExpression, (Void) obj);
                return Unit.INSTANCE;
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitFunctionAccess(IrFunctionAccessExpression irFunctionAccessExpression) {
                IrElementVisitorVoid.DefaultImpls.visitFunctionAccess(this, irFunctionAccessExpression);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitFunctionAccess(IrFunctionAccessExpression irFunctionAccessExpression, Void r2) {
                IrElementVisitorVoid.DefaultImpls.visitFunctionAccess(this, irFunctionAccessExpression, r2);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitFunctionExpression */
            public /* bridge */ /* synthetic */ Object visitFunctionExpression2(IrFunctionExpression irFunctionExpression, Object obj) {
                visitFunctionExpression(irFunctionExpression, (Void) obj);
                return Unit.INSTANCE;
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitFunctionExpression(IrFunctionExpression irFunctionExpression) {
                IrElementVisitorVoid.DefaultImpls.visitFunctionExpression(this, irFunctionExpression);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitFunctionExpression(IrFunctionExpression irFunctionExpression, Void r2) {
                IrElementVisitorVoid.DefaultImpls.visitFunctionExpression(this, irFunctionExpression, r2);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitFunctionReference */
            public /* bridge */ /* synthetic */ Object visitFunctionReference2(IrFunctionReference irFunctionReference, Object obj) {
                visitFunctionReference(irFunctionReference, (Void) obj);
                return Unit.INSTANCE;
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitFunctionReference(IrFunctionReference irFunctionReference) {
                IrElementVisitorVoid.DefaultImpls.visitFunctionReference(this, irFunctionReference);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitFunctionReference(IrFunctionReference irFunctionReference, Void r2) {
                IrElementVisitorVoid.DefaultImpls.visitFunctionReference(this, irFunctionReference, r2);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitGetClass */
            public /* bridge */ /* synthetic */ Object visitGetClass2(IrGetClass irGetClass, Object obj) {
                visitGetClass(irGetClass, (Void) obj);
                return Unit.INSTANCE;
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitGetClass(IrGetClass irGetClass) {
                IrElementVisitorVoid.DefaultImpls.visitGetClass(this, irGetClass);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitGetClass(IrGetClass irGetClass, Void r2) {
                IrElementVisitorVoid.DefaultImpls.visitGetClass(this, irGetClass, r2);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitGetEnumValue */
            public /* bridge */ /* synthetic */ Object visitGetEnumValue2(IrGetEnumValue irGetEnumValue, Object obj) {
                visitGetEnumValue(irGetEnumValue, (Void) obj);
                return Unit.INSTANCE;
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitGetEnumValue(IrGetEnumValue irGetEnumValue) {
                IrElementVisitorVoid.DefaultImpls.visitGetEnumValue(this, irGetEnumValue);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitGetEnumValue(IrGetEnumValue irGetEnumValue, Void r2) {
                IrElementVisitorVoid.DefaultImpls.visitGetEnumValue(this, irGetEnumValue, r2);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitGetField */
            public /* bridge */ /* synthetic */ Object visitGetField2(IrGetField irGetField, Object obj) {
                visitGetField(irGetField, (Void) obj);
                return Unit.INSTANCE;
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitGetField(IrGetField irGetField) {
                IrElementVisitorVoid.DefaultImpls.visitGetField(this, irGetField);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitGetField(IrGetField irGetField, Void r2) {
                IrElementVisitorVoid.DefaultImpls.visitGetField(this, irGetField, r2);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitGetObjectValue */
            public /* bridge */ /* synthetic */ Object visitGetObjectValue2(IrGetObjectValue irGetObjectValue, Object obj) {
                visitGetObjectValue(irGetObjectValue, (Void) obj);
                return Unit.INSTANCE;
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitGetObjectValue(IrGetObjectValue irGetObjectValue) {
                IrElementVisitorVoid.DefaultImpls.visitGetObjectValue(this, irGetObjectValue);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitGetObjectValue(IrGetObjectValue irGetObjectValue, Void r2) {
                IrElementVisitorVoid.DefaultImpls.visitGetObjectValue(this, irGetObjectValue, r2);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitGetValue */
            public /* bridge */ /* synthetic */ Object visitGetValue2(IrGetValue irGetValue, Object obj) {
                visitGetValue(irGetValue, (Void) obj);
                return Unit.INSTANCE;
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitGetValue(IrGetValue irGetValue) {
                IrElementVisitorVoid.DefaultImpls.visitGetValue(this, irGetValue);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitGetValue(IrGetValue irGetValue, Void r2) {
                IrElementVisitorVoid.DefaultImpls.visitGetValue(this, irGetValue, r2);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitInstanceInitializerCall */
            public /* bridge */ /* synthetic */ Object visitInstanceInitializerCall2(IrInstanceInitializerCall irInstanceInitializerCall, Object obj) {
                visitInstanceInitializerCall(irInstanceInitializerCall, (Void) obj);
                return Unit.INSTANCE;
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitInstanceInitializerCall(IrInstanceInitializerCall irInstanceInitializerCall) {
                IrElementVisitorVoid.DefaultImpls.visitInstanceInitializerCall(this, irInstanceInitializerCall);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitInstanceInitializerCall(IrInstanceInitializerCall irInstanceInitializerCall, Void r2) {
                IrElementVisitorVoid.DefaultImpls.visitInstanceInitializerCall(this, irInstanceInitializerCall, r2);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitLocalDelegatedProperty */
            public /* bridge */ /* synthetic */ Object visitLocalDelegatedProperty2(IrLocalDelegatedProperty irLocalDelegatedProperty, Object obj) {
                visitLocalDelegatedProperty(irLocalDelegatedProperty, (Void) obj);
                return Unit.INSTANCE;
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitLocalDelegatedProperty(IrLocalDelegatedProperty irLocalDelegatedProperty) {
                IrElementVisitorVoid.DefaultImpls.visitLocalDelegatedProperty(this, irLocalDelegatedProperty);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitLocalDelegatedProperty(IrLocalDelegatedProperty irLocalDelegatedProperty, Void r2) {
                IrElementVisitorVoid.DefaultImpls.visitLocalDelegatedProperty(this, irLocalDelegatedProperty, r2);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitLocalDelegatedPropertyReference */
            public /* bridge */ /* synthetic */ Object visitLocalDelegatedPropertyReference2(IrLocalDelegatedPropertyReference irLocalDelegatedPropertyReference, Object obj) {
                visitLocalDelegatedPropertyReference(irLocalDelegatedPropertyReference, (Void) obj);
                return Unit.INSTANCE;
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitLocalDelegatedPropertyReference(IrLocalDelegatedPropertyReference irLocalDelegatedPropertyReference) {
                IrElementVisitorVoid.DefaultImpls.visitLocalDelegatedPropertyReference(this, irLocalDelegatedPropertyReference);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitLocalDelegatedPropertyReference(IrLocalDelegatedPropertyReference irLocalDelegatedPropertyReference, Void r2) {
                IrElementVisitorVoid.DefaultImpls.visitLocalDelegatedPropertyReference(this, irLocalDelegatedPropertyReference, r2);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitLoop */
            public /* bridge */ /* synthetic */ Object visitLoop2(IrLoop irLoop, Object obj) {
                visitLoop(irLoop, (Void) obj);
                return Unit.INSTANCE;
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitLoop(IrLoop irLoop) {
                IrElementVisitorVoid.DefaultImpls.visitLoop(this, irLoop);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitLoop(IrLoop irLoop, Void r2) {
                IrElementVisitorVoid.DefaultImpls.visitLoop(this, irLoop, r2);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            public /* bridge */ /* synthetic */ Object visitMemberAccess(IrMemberAccessExpression irMemberAccessExpression, Object obj) {
                visitMemberAccess((IrMemberAccessExpression<?>) irMemberAccessExpression, (Void) obj);
                return Unit.INSTANCE;
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitMemberAccess(IrMemberAccessExpression<?> irMemberAccessExpression) {
                IrElementVisitorVoid.DefaultImpls.visitMemberAccess(this, irMemberAccessExpression);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitMemberAccess(IrMemberAccessExpression<?> irMemberAccessExpression, Void r2) {
                IrElementVisitorVoid.DefaultImpls.visitMemberAccess(this, irMemberAccessExpression, r2);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitModuleFragment */
            public /* bridge */ /* synthetic */ Object visitModuleFragment2(IrModuleFragment irModuleFragment, Object obj) {
                visitModuleFragment(irModuleFragment, (Void) obj);
                return Unit.INSTANCE;
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitModuleFragment(IrModuleFragment irModuleFragment) {
                IrElementVisitorVoid.DefaultImpls.visitModuleFragment(this, irModuleFragment);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitModuleFragment(IrModuleFragment irModuleFragment, Void r2) {
                IrElementVisitorVoid.DefaultImpls.visitModuleFragment(this, irModuleFragment, r2);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitPackageFragment */
            public /* bridge */ /* synthetic */ Object visitPackageFragment2(IrPackageFragment irPackageFragment, Object obj) {
                visitPackageFragment(irPackageFragment, (Void) obj);
                return Unit.INSTANCE;
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitPackageFragment(IrPackageFragment irPackageFragment) {
                IrElementVisitorVoid.DefaultImpls.visitPackageFragment(this, irPackageFragment);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitPackageFragment(IrPackageFragment irPackageFragment, Void r2) {
                IrElementVisitorVoid.DefaultImpls.visitPackageFragment(this, irPackageFragment, r2);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitProperty */
            public /* bridge */ /* synthetic */ Object visitProperty2(IrProperty irProperty, Object obj) {
                visitProperty(irProperty, (Void) obj);
                return Unit.INSTANCE;
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitProperty(IrProperty irProperty) {
                IrElementVisitorVoid.DefaultImpls.visitProperty(this, irProperty);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitProperty(IrProperty irProperty, Void r2) {
                IrElementVisitorVoid.DefaultImpls.visitProperty(this, irProperty, r2);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitPropertyReference */
            public /* bridge */ /* synthetic */ Object visitPropertyReference2(IrPropertyReference irPropertyReference, Object obj) {
                visitPropertyReference(irPropertyReference, (Void) obj);
                return Unit.INSTANCE;
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitPropertyReference(IrPropertyReference irPropertyReference) {
                IrElementVisitorVoid.DefaultImpls.visitPropertyReference(this, irPropertyReference);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitPropertyReference(IrPropertyReference irPropertyReference, Void r2) {
                IrElementVisitorVoid.DefaultImpls.visitPropertyReference(this, irPropertyReference, r2);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitRawFunctionReference */
            public /* bridge */ /* synthetic */ Object visitRawFunctionReference2(IrRawFunctionReference irRawFunctionReference, Object obj) {
                visitRawFunctionReference(irRawFunctionReference, (Void) obj);
                return Unit.INSTANCE;
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitRawFunctionReference(IrRawFunctionReference irRawFunctionReference) {
                IrElementVisitorVoid.DefaultImpls.visitRawFunctionReference(this, irRawFunctionReference);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitRawFunctionReference(IrRawFunctionReference irRawFunctionReference, Void r2) {
                IrElementVisitorVoid.DefaultImpls.visitRawFunctionReference(this, irRawFunctionReference, r2);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitReturn */
            public /* bridge */ /* synthetic */ Object visitReturn2(IrReturn irReturn, Object obj) {
                visitReturn(irReturn, (Void) obj);
                return Unit.INSTANCE;
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitReturn(IrReturn irReturn) {
                IrElementVisitorVoid.DefaultImpls.visitReturn(this, irReturn);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitReturn(IrReturn irReturn, Void r2) {
                IrElementVisitorVoid.DefaultImpls.visitReturn(this, irReturn, r2);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitScript */
            public /* bridge */ /* synthetic */ Object visitScript2(IrScript irScript, Object obj) {
                visitScript(irScript, (Void) obj);
                return Unit.INSTANCE;
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitScript(IrScript irScript) {
                IrElementVisitorVoid.DefaultImpls.visitScript(this, irScript);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitScript(IrScript irScript, Void r2) {
                IrElementVisitorVoid.DefaultImpls.visitScript(this, irScript, r2);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitSetField */
            public /* bridge */ /* synthetic */ Object visitSetField2(IrSetField irSetField, Object obj) {
                visitSetField(irSetField, (Void) obj);
                return Unit.INSTANCE;
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitSetField(IrSetField irSetField) {
                IrElementVisitorVoid.DefaultImpls.visitSetField(this, irSetField);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitSetField(IrSetField irSetField, Void r2) {
                IrElementVisitorVoid.DefaultImpls.visitSetField(this, irSetField, r2);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitSetValue */
            public /* bridge */ /* synthetic */ Object visitSetValue2(IrSetValue irSetValue, Object obj) {
                visitSetValue(irSetValue, (Void) obj);
                return Unit.INSTANCE;
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitSetValue(IrSetValue irSetValue) {
                IrElementVisitorVoid.DefaultImpls.visitSetValue(this, irSetValue);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitSetValue(IrSetValue irSetValue, Void r2) {
                IrElementVisitorVoid.DefaultImpls.visitSetValue(this, irSetValue, r2);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitSimpleFunction */
            public /* bridge */ /* synthetic */ Object visitSimpleFunction2(IrSimpleFunction irSimpleFunction, Object obj) {
                visitSimpleFunction(irSimpleFunction, (Void) obj);
                return Unit.INSTANCE;
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitSimpleFunction(IrSimpleFunction irSimpleFunction) {
                IrElementVisitorVoid.DefaultImpls.visitSimpleFunction(this, irSimpleFunction);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitSimpleFunction(IrSimpleFunction irSimpleFunction, Void r2) {
                IrElementVisitorVoid.DefaultImpls.visitSimpleFunction(this, irSimpleFunction, r2);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitSingletonReference */
            public /* bridge */ /* synthetic */ Object visitSingletonReference2(IrGetSingletonValue irGetSingletonValue, Object obj) {
                visitSingletonReference(irGetSingletonValue, (Void) obj);
                return Unit.INSTANCE;
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitSingletonReference(IrGetSingletonValue irGetSingletonValue) {
                IrElementVisitorVoid.DefaultImpls.visitSingletonReference(this, irGetSingletonValue);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitSingletonReference(IrGetSingletonValue irGetSingletonValue, Void r2) {
                IrElementVisitorVoid.DefaultImpls.visitSingletonReference(this, irGetSingletonValue, r2);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitSpreadElement */
            public /* bridge */ /* synthetic */ Object visitSpreadElement2(IrSpreadElement irSpreadElement, Object obj) {
                visitSpreadElement(irSpreadElement, (Void) obj);
                return Unit.INSTANCE;
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitSpreadElement(IrSpreadElement irSpreadElement) {
                IrElementVisitorVoid.DefaultImpls.visitSpreadElement(this, irSpreadElement);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitSpreadElement(IrSpreadElement irSpreadElement, Void r2) {
                IrElementVisitorVoid.DefaultImpls.visitSpreadElement(this, irSpreadElement, r2);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitStringConcatenation */
            public /* bridge */ /* synthetic */ Object visitStringConcatenation2(IrStringConcatenation irStringConcatenation, Object obj) {
                visitStringConcatenation(irStringConcatenation, (Void) obj);
                return Unit.INSTANCE;
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitStringConcatenation(IrStringConcatenation irStringConcatenation) {
                IrElementVisitorVoid.DefaultImpls.visitStringConcatenation(this, irStringConcatenation);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitStringConcatenation(IrStringConcatenation irStringConcatenation, Void r2) {
                IrElementVisitorVoid.DefaultImpls.visitStringConcatenation(this, irStringConcatenation, r2);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitSuspendableExpression */
            public /* bridge */ /* synthetic */ Object visitSuspendableExpression2(IrSuspendableExpression irSuspendableExpression, Object obj) {
                visitSuspendableExpression(irSuspendableExpression, (Void) obj);
                return Unit.INSTANCE;
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitSuspendableExpression(IrSuspendableExpression irSuspendableExpression) {
                IrElementVisitorVoid.DefaultImpls.visitSuspendableExpression(this, irSuspendableExpression);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitSuspendableExpression(IrSuspendableExpression irSuspendableExpression, Void r2) {
                IrElementVisitorVoid.DefaultImpls.visitSuspendableExpression(this, irSuspendableExpression, r2);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitSuspensionPoint */
            public /* bridge */ /* synthetic */ Object visitSuspensionPoint2(IrSuspensionPoint irSuspensionPoint, Object obj) {
                visitSuspensionPoint(irSuspensionPoint, (Void) obj);
                return Unit.INSTANCE;
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitSuspensionPoint(IrSuspensionPoint irSuspensionPoint) {
                IrElementVisitorVoid.DefaultImpls.visitSuspensionPoint(this, irSuspensionPoint);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitSuspensionPoint(IrSuspensionPoint irSuspensionPoint, Void r2) {
                IrElementVisitorVoid.DefaultImpls.visitSuspensionPoint(this, irSuspensionPoint, r2);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitSyntheticBody */
            public /* bridge */ /* synthetic */ Object visitSyntheticBody2(IrSyntheticBody irSyntheticBody, Object obj) {
                visitSyntheticBody(irSyntheticBody, (Void) obj);
                return Unit.INSTANCE;
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitSyntheticBody(IrSyntheticBody irSyntheticBody) {
                IrElementVisitorVoid.DefaultImpls.visitSyntheticBody(this, irSyntheticBody);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitSyntheticBody(IrSyntheticBody irSyntheticBody, Void r2) {
                IrElementVisitorVoid.DefaultImpls.visitSyntheticBody(this, irSyntheticBody, r2);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitThrow */
            public /* bridge */ /* synthetic */ Object visitThrow2(IrThrow irThrow, Object obj) {
                visitThrow(irThrow, (Void) obj);
                return Unit.INSTANCE;
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitThrow(IrThrow irThrow) {
                IrElementVisitorVoid.DefaultImpls.visitThrow(this, irThrow);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitThrow(IrThrow irThrow, Void r2) {
                IrElementVisitorVoid.DefaultImpls.visitThrow(this, irThrow, r2);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitTry */
            public /* bridge */ /* synthetic */ Object visitTry2(IrTry irTry, Object obj) {
                visitTry(irTry, (Void) obj);
                return Unit.INSTANCE;
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitTry(IrTry irTry) {
                IrElementVisitorVoid.DefaultImpls.visitTry(this, irTry);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitTry(IrTry irTry, Void r2) {
                IrElementVisitorVoid.DefaultImpls.visitTry(this, irTry, r2);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitTypeAlias */
            public /* bridge */ /* synthetic */ Object visitTypeAlias2(IrTypeAlias irTypeAlias, Object obj) {
                visitTypeAlias(irTypeAlias, (Void) obj);
                return Unit.INSTANCE;
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitTypeAlias(IrTypeAlias irTypeAlias) {
                IrElementVisitorVoid.DefaultImpls.visitTypeAlias(this, irTypeAlias);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitTypeAlias(IrTypeAlias irTypeAlias, Void r2) {
                IrElementVisitorVoid.DefaultImpls.visitTypeAlias(this, irTypeAlias, r2);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitTypeOperator */
            public /* bridge */ /* synthetic */ Object visitTypeOperator2(IrTypeOperatorCall irTypeOperatorCall, Object obj) {
                visitTypeOperator(irTypeOperatorCall, (Void) obj);
                return Unit.INSTANCE;
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitTypeOperator(IrTypeOperatorCall irTypeOperatorCall) {
                IrElementVisitorVoid.DefaultImpls.visitTypeOperator(this, irTypeOperatorCall);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitTypeOperator(IrTypeOperatorCall irTypeOperatorCall, Void r2) {
                IrElementVisitorVoid.DefaultImpls.visitTypeOperator(this, irTypeOperatorCall, r2);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitTypeParameter */
            public /* bridge */ /* synthetic */ Object visitTypeParameter2(IrTypeParameter irTypeParameter, Object obj) {
                visitTypeParameter(irTypeParameter, (Void) obj);
                return Unit.INSTANCE;
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitTypeParameter(IrTypeParameter irTypeParameter) {
                IrElementVisitorVoid.DefaultImpls.visitTypeParameter(this, irTypeParameter);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitTypeParameter(IrTypeParameter irTypeParameter, Void r2) {
                IrElementVisitorVoid.DefaultImpls.visitTypeParameter(this, irTypeParameter, r2);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitValueAccess */
            public /* bridge */ /* synthetic */ Object visitValueAccess2(IrValueAccessExpression irValueAccessExpression, Object obj) {
                visitValueAccess(irValueAccessExpression, (Void) obj);
                return Unit.INSTANCE;
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitValueAccess(IrValueAccessExpression irValueAccessExpression) {
                IrElementVisitorVoid.DefaultImpls.visitValueAccess(this, irValueAccessExpression);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitValueAccess(IrValueAccessExpression irValueAccessExpression, Void r2) {
                IrElementVisitorVoid.DefaultImpls.visitValueAccess(this, irValueAccessExpression, r2);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitValueParameter */
            public /* bridge */ /* synthetic */ Object visitValueParameter2(IrValueParameter irValueParameter, Object obj) {
                visitValueParameter(irValueParameter, (Void) obj);
                return Unit.INSTANCE;
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitValueParameter(IrValueParameter irValueParameter) {
                IrElementVisitorVoid.DefaultImpls.visitValueParameter(this, irValueParameter);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitValueParameter(IrValueParameter irValueParameter, Void r2) {
                IrElementVisitorVoid.DefaultImpls.visitValueParameter(this, irValueParameter, r2);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitVararg */
            public /* bridge */ /* synthetic */ Object visitVararg2(IrVararg irVararg, Object obj) {
                visitVararg(irVararg, (Void) obj);
                return Unit.INSTANCE;
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitVararg(IrVararg irVararg) {
                IrElementVisitorVoid.DefaultImpls.visitVararg(this, irVararg);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitVararg(IrVararg irVararg, Void r2) {
                IrElementVisitorVoid.DefaultImpls.visitVararg(this, irVararg, r2);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitVariable */
            public /* bridge */ /* synthetic */ Object visitVariable2(IrVariable irVariable, Object obj) {
                visitVariable(irVariable, (Void) obj);
                return Unit.INSTANCE;
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitVariable(IrVariable irVariable) {
                IrElementVisitorVoid.DefaultImpls.visitVariable(this, irVariable);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitVariable(IrVariable irVariable, Void r2) {
                IrElementVisitorVoid.DefaultImpls.visitVariable(this, irVariable, r2);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitWhen */
            public /* bridge */ /* synthetic */ Object visitWhen2(IrWhen irWhen, Object obj) {
                visitWhen(irWhen, (Void) obj);
                return Unit.INSTANCE;
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitWhen(IrWhen irWhen) {
                IrElementVisitorVoid.DefaultImpls.visitWhen(this, irWhen);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitWhen(IrWhen irWhen, Void r2) {
                IrElementVisitorVoid.DefaultImpls.visitWhen(this, irWhen, r2);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitWhileLoop */
            public /* bridge */ /* synthetic */ Object visitWhileLoop2(IrWhileLoop irWhileLoop, Object obj) {
                visitWhileLoop(irWhileLoop, (Void) obj);
                return Unit.INSTANCE;
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitWhileLoop(IrWhileLoop irWhileLoop) {
                IrElementVisitorVoid.DefaultImpls.visitWhileLoop(this, irWhileLoop);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitWhileLoop(IrWhileLoop irWhileLoop, Void r2) {
                IrElementVisitorVoid.DefaultImpls.visitWhileLoop(this, irWhileLoop, r2);
            }

            private final void setUpCorrectAttributeOwner(IrAttributeContainer irAttributeContainer) {
                if (Intrinsics.areEqual(irAttributeContainer.getAttributeOwnerId(), irAttributeContainer)) {
                    return;
                }
                irAttributeContainer.setOriginalBeforeInline(irAttributeContainer.getAttributeOwnerId());
                irAttributeContainer.setAttributeOwnerId(irAttributeContainer);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            /* JADX INFO: renamed from: visitElement */
            public void mo6335visitElement(IrElement element) {
                Intrinsics.checkNotNullParameter(element, "element");
                if (element instanceof IrAttributeContainer) {
                    setUpCorrectAttributeOwner((IrAttributeContainer) element);
                }
                IrVisitorsKt.acceptChildrenVoid(element, this);
            }
        });
        return irReturnableBlock;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final boolean getNeedsInlining(IrFunction irFunction) {
        return irFunction.getIsInline() && (this.allowExternalInlining || !irFunction.getIsExternal());
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: compiled from: FunctionInlining.kt */
    @Metadata(d1 = {"\u0000\u0096\u0001\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010%\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0007\b\u0082\u0004\u0018\u00002\u00020\u0001:\u0002GHB/\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\b\u0010\b\u001a\u0004\u0018\u00010\t\u0012\u0006\u0010\n\u001a\u00020\u000b¢\u0006\u0002\u0010\fJ&\u0010!\u001a\u0010\u0012\f\u0012\n0#R\u00060\u0000R\u00020$0\"2\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H\u0002J(\u0010%\u001a\u00020&2\u0006\u0010'\u001a\u00020\u001d2\u0006\u0010(\u001a\u00020\u001e2\u0006\u0010)\u001a\u00020*2\u0006\u0010\u0004\u001a\u00020\u0005H\u0002J\u001a\u0010+\u001a\b\u0012\u0004\u0012\u00020&0\"2\n\u0010,\u001a\u0006\u0012\u0002\b\u00030-H\u0002J\u001e\u0010+\u001a\b\u0012\u0004\u0012\u00020.0\"2\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H\u0002J\u0012\u0010/\u001a\u0004\u0018\u00010&2\u0006\u0010,\u001a\u000200H\u0002J\u0006\u00101\u001a\u000202J(\u00103\u001a\u0002022\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u00104\u001a\u0002052\u0006\u00106\u001a\u00020*H\u0002J\u0010\u00107\u001a\u00020*2\u0006\u00108\u001a\u000209H\u0002J\"\u0010:\u001a\u0010\u0012\f\u0012\n0#R\u00060\u0000R\u00020$0\"*\n0#R\u00060\u0000R\u00020$H\u0002J\u001b\u0010;\u001a\u0002H<\"\b\b\u0000\u0010<*\u000205*\u0002H<H\u0002¢\u0006\u0002\u0010=J\u0014\u0010>\u001a\u00020\u001e*\u00020\u001e2\u0006\u0010?\u001a\u00020@H\u0002J\u000e\u0010A\u001a\u00020@*\u0004\u0018\u00010BH\u0002J\f\u0010C\u001a\u00020\u001d*\u00020\u001dH\u0002J\f\u0010D\u001a\u00020@*\u00020\u001dH\u0002J\u0014\u0010E\u001a\u00020**\n0#R\u00060\u0000R\u00020$H\u0002J\f\u0010F\u001a\u00020\u001e*\u00020\u001eH\u0002R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000eR\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0010R\u0011\u0010\n\u001a\u00020\u000b¢\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0012R\u0011\u0010\u0013\u001a\u00020\u0014¢\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\u0016R\u0011\u0010\u0006\u001a\u00020\u0007¢\u0006\b\n\u0000\u001a\u0004\b\u0017\u0010\u0018R\u0013\u0010\b\u001a\u0004\u0018\u00010\t¢\u0006\b\n\u0000\u001a\u0004\b\u0019\u0010\u001aR\u001d\u0010\u001b\u001a\u000e\u0012\u0004\u0012\u00020\u001d\u0012\u0004\u0012\u00020\u001e0\u001c¢\u0006\b\n\u0000\u001a\u0004\b\u001f\u0010 ¨\u0006I"}, d2 = {"Lorg/jetbrains/kotlin/backend/common/lower/inline/FunctionInlining$Inliner;", "", "callSite", "Lorg/jetbrains/kotlin/ir/expressions/IrFunctionAccessExpression;", "callee", "Lorg/jetbrains/kotlin/ir/declarations/IrFunction;", "currentScope", "Lorg/jetbrains/kotlin/backend/common/ScopeWithIr;", "parent", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationParent;", "context", "Lorg/jetbrains/kotlin/backend/common/CommonBackendContext;", "(Lorg/jetbrains/kotlin/backend/common/lower/inline/FunctionInlining;Lorg/jetbrains/kotlin/ir/expressions/IrFunctionAccessExpression;Lorg/jetbrains/kotlin/ir/declarations/IrFunction;Lorg/jetbrains/kotlin/backend/common/ScopeWithIr;Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationParent;Lorg/jetbrains/kotlin/backend/common/CommonBackendContext;)V", "getCallSite", "()Lorg/jetbrains/kotlin/ir/expressions/IrFunctionAccessExpression;", "getCallee", "()Lorg/jetbrains/kotlin/ir/declarations/IrFunction;", "getContext", "()Lorg/jetbrains/kotlin/backend/common/CommonBackendContext;", "copyIrElement", "Lorg/jetbrains/kotlin/backend/common/lower/inline/DeepCopyIrTreeWithSymbolsForInliner;", "getCopyIrElement", "()Lorg/jetbrains/kotlin/backend/common/lower/inline/DeepCopyIrTreeWithSymbolsForInliner;", "getCurrentScope", "()Lorg/jetbrains/kotlin/backend/common/ScopeWithIr;", "getParent", "()Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationParent;", "substituteMap", "", "Lorg/jetbrains/kotlin/ir/declarations/IrValueParameter;", "Lorg/jetbrains/kotlin/ir/expressions/IrExpression;", "getSubstituteMap", "()Ljava/util/Map;", "buildParameterToArgument", "", "Lorg/jetbrains/kotlin/backend/common/lower/inline/FunctionInlining$Inliner$ParameterToArgument;", "Lorg/jetbrains/kotlin/backend/common/lower/inline/FunctionInlining;", "createTemporaryVariable", "Lorg/jetbrains/kotlin/ir/declarations/IrVariable;", "parameter", "variableInitializer", "isDefaultArg", "", "evaluateArguments", "reference", "Lorg/jetbrains/kotlin/ir/expressions/IrCallableReference;", "Lorg/jetbrains/kotlin/ir/IrStatement;", "evaluateReceiverForPropertyWithField", "Lorg/jetbrains/kotlin/ir/expressions/IrPropertyReference;", "inline", "Lorg/jetbrains/kotlin/ir/expressions/IrReturnableBlock;", "inlineFunction", "originalInlinedElement", "Lorg/jetbrains/kotlin/ir/IrElement;", "performRecursiveInline", "isLambdaCall", "irCall", "Lorg/jetbrains/kotlin/ir/expressions/IrCall;", "andAllOuterClasses", ConfigConstants.CONFIG_RENAMELIMIT_COPY, ExifInterface.LONGITUDE_EAST, "(Lorg/jetbrains/kotlin/ir/IrElement;)Lorg/jetbrains/kotlin/ir/IrElement;", "doImplicitCastIfNeededTo", ModuleXmlParser.TYPE, "Lorg/jetbrains/kotlin/ir/types/IrType;", "firstRealUpperBound", "Lorg/jetbrains/kotlin/ir/declarations/IrTypeParameter;", "getOriginalParameter", "getOriginalType", "shouldBeSubstitutedViaTemporaryVariable", "unwrapAdditionalImplicitCastsIfNeeded", "ParameterSubstitutor", "ParameterToArgument", "ir.backend.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
    final class Inliner {
        private final IrFunctionAccessExpression callSite;
        private final IrFunction callee;
        private final CommonBackendContext context;
        private final DeepCopyIrTreeWithSymbolsForInliner copyIrElement;
        private final ScopeWithIr currentScope;
        private final IrDeclarationParent parent;
        private final Map<IrValueParameter, IrExpression> substituteMap;
        final /* synthetic */ FunctionInlining this$0;

        public Inliner(FunctionInlining functionInlining, IrFunctionAccessExpression callSite, IrFunction callee, ScopeWithIr currentScope, IrDeclarationParent irDeclarationParent, CommonBackendContext context) {
            List<IrTypeParameter> typeParameters;
            Intrinsics.checkNotNullParameter(callSite, "callSite");
            Intrinsics.checkNotNullParameter(callee, "callee");
            Intrinsics.checkNotNullParameter(currentScope, "currentScope");
            Intrinsics.checkNotNullParameter(context, "context");
            this.this$0 = functionInlining;
            this.callSite = callSite;
            this.callee = callee;
            this.currentScope = currentScope;
            this.parent = irDeclarationParent;
            this.context = context;
            IrFunction irFunction = this.callee;
            if (irFunction instanceof IrConstructor) {
                typeParameters = IrUtilsKt.getParentAsClass(irFunction).getTypeParameters();
            } else {
                typeParameters = irFunction.getTypeParameters();
            }
            IntRange intRangeUntil = RangesKt.until(0, this.callSite.getTypeArgumentsCount());
            LinkedHashMap linkedHashMap = new LinkedHashMap(RangesKt.coerceAtLeast(MapsKt.mapCapacity(CollectionsKt.collectionSizeOrDefault(intRangeUntil, 10)), 16));
            Iterator<Integer> it = intRangeUntil.iterator();
            while (it.hasNext()) {
                int iNextInt = ((IntIterator) it).nextInt();
                Pair pair = TuplesKt.to(typeParameters.get(iNextInt).getSymbol(), this.callSite.getTypeArgument(iNextInt));
                linkedHashMap.put(pair.getFirst(), pair.getSecond());
            }
            this.copyIrElement = new DeepCopyIrTreeWithSymbolsForInliner(linkedHashMap, this.parent);
            this.substituteMap = new LinkedHashMap();
        }

        public final IrFunctionAccessExpression getCallSite() {
            return this.callSite;
        }

        public final IrFunction getCallee() {
            return this.callee;
        }

        public final ScopeWithIr getCurrentScope() {
            return this.currentScope;
        }

        public final IrDeclarationParent getParent() {
            return this.parent;
        }

        public final CommonBackendContext getContext() {
            return this.context;
        }

        public final DeepCopyIrTreeWithSymbolsForInliner getCopyIrElement() {
            return this.copyIrElement;
        }

        public final Map<IrValueParameter, IrExpression> getSubstituteMap() {
            return this.substituteMap;
        }

        public final IrReturnableBlock inline() {
            return inlineFunction(this.callSite, this.callee, this.this$0.inlineFunctionResolver.getFunctionSymbol(this.callee).getOwner(), true);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final <E extends IrElement> E copy(E e) {
            E e2 = (E) this.copyIrElement.copy(e);
            Intrinsics.checkNotNull(e2, "null cannot be cast to non-null type E of org.jetbrains.kotlin.backend.common.lower.inline.FunctionInlining.Inliner.copy");
            return e2;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final IrReturnableBlock inlineFunction(final IrFunctionAccessExpression callSite, IrFunction callee, IrElement originalInlinedElement, boolean performRecursiveInline) {
            List<IrStatement> statements;
            IrFunction irFunction = callee;
            IrElement irElementCopy = copy(irFunction);
            FunctionInlining functionInlining = this.this$0;
            final IrFunction irFunction2 = (IrFunction) irElementCopy;
            irFunction2.setParent(callee.getParent());
            if (performRecursiveInline) {
                IrBody body = irFunction2.getBody();
                if (body != null) {
                    functionInlining.transformChildrenVoid(body);
                }
                int i = 0;
                for (Object obj : irFunction2.getValueParameters()) {
                    int i2 = i + 1;
                    if (i < 0) {
                        CollectionsKt.throwIndexOverflow();
                    }
                    IrValueParameter irValueParameter = (IrValueParameter) obj;
                    if (callSite.getValueArgument(i) == null) {
                        IrExpressionBody defaultValue = irValueParameter.getDefaultValue();
                        irValueParameter.setDefaultValue(defaultValue != null ? defaultValue.transform((IrElementTransformer<? super Object>) functionInlining, (Object) null) : null);
                    }
                    i = i2;
                }
            }
            List<IrStatement> listEvaluateArguments = evaluateArguments(callSite, irFunction2);
            IrBody body2 = irFunction2.getBody();
            IrBlockBody irBlockBody = body2 instanceof IrBlockBody ? (IrBlockBody) body2 : null;
            if (irBlockBody == null || (statements = irBlockBody.getStatements()) == null) {
                throw new IllegalStateException(("Body not found for function " + RenderIrElementKt.render$default(irFunction, (DumpIrTreeOptions) null, 1, (Object) null)).toString());
            }
            IrReturnableBlockSymbolImpl irReturnableBlockSymbolImpl = new IrReturnableBlockSymbolImpl(null, 1, null);
            IrStatement irStatement = (IrStatement) CollectionsKt.lastOrNull((List) statements);
            int endOffset = irStatement != null ? irStatement.getEndOffset() : callee.getEndOffset();
            final DeclarationIrBuilder declarationIrBuilderCreateIrBuilder = LowerUtilsKt.createIrBuilder(this.context, irReturnableBlockSymbolImpl, endOffset, endOffset);
            ParameterSubstitutor parameterSubstitutor = new ParameterSubstitutor();
            List<IrStatement> list = statements;
            ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(list, 10));
            Iterator<T> it = list.iterator();
            while (it.hasNext()) {
                IrElement irElementTransform = ((IrStatement) it.next()).transform(parameterSubstitutor, null);
                Intrinsics.checkNotNull(irElementTransform, "null cannot be cast to non-null type org.jetbrains.kotlin.ir.IrStatement");
                arrayList.add((IrStatement) irElementTransform);
            }
            IrReturnableBlockImpl irReturnableBlockImpl = new IrReturnableBlockImpl(callSite.getStartOffset(), callSite.getEndOffset(), callSite.getType(), irReturnableBlockSymbolImpl, null, CollectionsKt.listOf(new IrInlinedFunctionBlockImpl(callSite.getStartOffset(), callSite.getEndOffset(), callSite.getType(), callSite, originalInlinedElement, null, CollectionsKt.plus((Collection) listEvaluateArguments, (Iterable) arrayList))));
            IrReturnableBlockImpl irReturnableBlockImpl2 = irReturnableBlockImpl;
            IrElementTransformerVoidKt.transformChildrenVoid(irReturnableBlockImpl2, new IrElementTransformerVoid() { // from class: org.jetbrains.kotlin.backend.common.lower.inline.FunctionInlining$Inliner$inlineFunction$1$1
                @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
                public IrExpression visitReturn(IrReturn expression) {
                    Intrinsics.checkNotNullParameter(expression, "expression");
                    IrReturn irReturn = expression;
                    IrElementTransformerVoidKt.transformChildrenVoid(irReturn, this);
                    if (Intrinsics.areEqual(expression.getReturnTargetSymbol(), irFunction2.getSymbol())) {
                        return ExpressionHelpersKt.irReturn((IrBuilderWithScope) LowerUtilsKt.at(declarationIrBuilderCreateIrBuilder, irReturn), this.doImplicitCastIfNeededTo(expression.getValue(), callSite.getType()));
                    }
                    return expression;
                }
            });
            PatchDeclarationParentsKt.patchDeclarationParents(irReturnableBlockImpl2, this.parent);
            return irReturnableBlockImpl;
        }

        /* JADX INFO: compiled from: FunctionInlining.kt */
        @Metadata(d1 = {"\u0000d\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0082\u0004\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0016\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bJ\u0018\u0010\t\u001a\u00020\u00042\u0006\u0010\n\u001a\u00020\u00062\u0006\u0010\u000b\u001a\u00020\fH\u0002J\u0016\u0010\r\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u000e\u001a\u00020\u000fJ\u001e\u0010\u0010\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u0014J\u001e\u0010\u0010\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0015\u001a\u00020\u0016J\u0018\u0010\u0017\u001a\u00020\u00042\u0006\u0010\u0018\u001a\u00020\u00062\u0006\u0010\u000b\u001a\u00020\fH\u0002J\u0010\u0010\u0019\u001a\u00020\u00042\u0006\u0010\u0018\u001a\u00020\u0006H\u0016J\u0010\u0010\u001a\u001a\u00020\u001b2\u0006\u0010\u001c\u001a\u00020\u001bH\u0016J\u0010\u0010\u001d\u001a\u00020\u00042\u0006\u0010\u0018\u001a\u00020\u001eH\u0016J$\u0010\u001f\u001a\u00020 2\u0006\u0010!\u001a\u00020\u00042\u0006\u0010\n\u001a\u00020\"2\n\u0010#\u001a\u0006\u0012\u0002\b\u00030$H\u0002¨\u0006%"}, d2 = {"Lorg/jetbrains/kotlin/backend/common/lower/inline/FunctionInlining$Inliner$ParameterSubstitutor;", "Lorg/jetbrains/kotlin/ir/visitors/IrElementTransformerVoid;", "(Lorg/jetbrains/kotlin/backend/common/lower/inline/FunctionInlining$Inliner;)V", "inlineAdaptedFunctionReference", "Lorg/jetbrains/kotlin/ir/expressions/IrExpression;", "irCall", "Lorg/jetbrains/kotlin/ir/expressions/IrCall;", "irBlock", "Lorg/jetbrains/kotlin/ir/expressions/IrBlock;", "inlineField", "invokeCall", "propertyReference", "Lorg/jetbrains/kotlin/ir/expressions/IrPropertyReference;", "inlineFunctionExpression", "irFunctionExpression", "Lorg/jetbrains/kotlin/ir/expressions/IrFunctionExpression;", "inlineFunctionReference", "irFunctionReference", "Lorg/jetbrains/kotlin/ir/expressions/IrFunctionReference;", "inlinedFunction", "Lorg/jetbrains/kotlin/ir/declarations/IrFunction;", "inlinedFunctionSymbol", "Lorg/jetbrains/kotlin/ir/symbols/IrFunctionSymbol;", "inlinePropertyReference", "expression", "visitCall", "visitElement", "Lorg/jetbrains/kotlin/ir/IrElement;", CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD, "visitGetValue", "Lorg/jetbrains/kotlin/ir/expressions/IrGetValue;", "wrapInStubFunction", "Lorg/jetbrains/kotlin/ir/expressions/IrReturnableBlock;", "inlinedCall", "Lorg/jetbrains/kotlin/ir/expressions/IrFunctionAccessExpression;", "reference", "Lorg/jetbrains/kotlin/ir/expressions/IrCallableReference;", "ir.backend.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
        private final class ParameterSubstitutor extends IrElementTransformerVoid {
            public ParameterSubstitutor() {
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
            public IrExpression visitGetValue(IrGetValue expression) {
                IrGetValueImpl irGetValueImplWithLocation;
                Intrinsics.checkNotNullParameter(expression, "expression");
                IrExpression irExpressionVisitGetValue = super.visitGetValue(expression);
                Intrinsics.checkNotNull(irExpressionVisitGetValue, "null cannot be cast to non-null type org.jetbrains.kotlin.ir.expressions.IrGetValue");
                IrGetValue irGetValue = (IrGetValue) irExpressionVisitGetValue;
                IrExpression irExpression = Inliner.this.getSubstituteMap().get(irGetValue.getSymbol().getOwner());
                if (irExpression == null) {
                    return irGetValue;
                }
                IrExpression irExpression2 = irExpression;
                IrElementTransformerVoidKt.transformChildrenVoid(irExpression2, this);
                if (!(irExpression instanceof IrGetValueWithoutLocation)) {
                    irGetValueImplWithLocation = (IrExpression) Inliner.this.copy(irExpression2);
                } else {
                    irGetValueImplWithLocation = ((IrGetValueWithoutLocation) irExpression).withLocation(irGetValue.getStartOffset(), irGetValue.getEndOffset());
                }
                return Inliner.this.doImplicitCastIfNeededTo(irGetValueImplWithLocation, irGetValue.getType());
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
            public IrExpression visitCall(IrCall expression) {
                Intrinsics.checkNotNullParameter(expression, "expression");
                if (!IrUtilsKt.hasAnnotation(expression.getSymbol().getOwner(), ContractsDslNames.INSTANCE.getCONTRACTS_DSL_ANNOTATION_FQN())) {
                    if (!Inliner.this.isLambdaCall(expression)) {
                        return super.visitCall(expression);
                    }
                    IrExpression dispatchReceiver = expression.getDispatchReceiver();
                    IrExpression irExpressionUnwrapAdditionalImplicitCastsIfNeeded = dispatchReceiver != null ? Inliner.this.unwrapAdditionalImplicitCastsIfNeeded(dispatchReceiver) : null;
                    Intrinsics.checkNotNull(irExpressionUnwrapAdditionalImplicitCastsIfNeeded, "null cannot be cast to non-null type org.jetbrains.kotlin.ir.expressions.IrGetValue");
                    IrGetValue irGetValue = (IrGetValue) irExpressionUnwrapAdditionalImplicitCastsIfNeeded;
                    IrExpression irExpression = Inliner.this.getSubstituteMap().get(irGetValue.getSymbol().getOwner());
                    if (irExpression == null) {
                        return super.visitCall(expression);
                    }
                    IrValueDeclaration owner = irGetValue.getSymbol().getOwner();
                    IrValueParameter irValueParameter = owner instanceof IrValueParameter ? (IrValueParameter) owner : null;
                    if (irValueParameter != null && irValueParameter.getIsNoinline()) {
                        return super.visitCall(expression);
                    }
                    if (irExpression instanceof IrFunctionReference) {
                        IrFunctionReference irFunctionReference = (IrFunctionReference) irExpression;
                        return inlineFunctionReference(expression, irFunctionReference, irFunctionReference.getSymbol());
                    }
                    boolean z = irExpression instanceof IrPropertyReference;
                    if (z) {
                        IrPropertyReference irPropertyReference = (IrPropertyReference) irExpression;
                        if (irPropertyReference.getField() != null) {
                            return inlineField(expression, irPropertyReference);
                        }
                    }
                    if (z) {
                        return inlinePropertyReference(expression, (IrPropertyReference) irExpression);
                    }
                    if (FunctionInliningKt.isAdaptedFunctionReference(irExpression)) {
                        return inlineAdaptedFunctionReference(expression, (IrBlock) irExpression);
                    }
                    if (irExpression instanceof IrFunctionExpression) {
                        return inlineFunctionExpression(expression, (IrFunctionExpression) irExpression);
                    }
                    return super.visitCall(expression);
                }
                return new IrCompositeImpl(expression.getStartOffset(), expression.getEndOffset(), Inliner.this.getContext().getIrBuiltIns().getUnitType(), null, 8, null);
            }

            public final IrExpression inlineFunctionExpression(IrCall irCall, IrFunctionExpression irFunctionExpression) {
                Intrinsics.checkNotNullParameter(irCall, "irCall");
                Intrinsics.checkNotNullParameter(irFunctionExpression, "irFunctionExpression");
                return Inliner.this.inlineFunction(irCall, irFunctionExpression.getFunction(), irFunctionExpression, false).transform((IrElementTransformer) this, (Object) null);
            }

            private final IrExpression inlineField(IrCall invokeCall, IrPropertyReference propertyReference) {
                return wrapInStubFunction(invokeCall, invokeCall, propertyReference);
            }

            private final IrExpression inlinePropertyReference(IrCall expression, IrPropertyReference propertyReference) {
                IrExpression irExpressionInlinePropertyReference$tryToGetArg;
                IrCallImpl.Companion companion = IrCallImpl.INSTANCE;
                int startOffset = expression.getStartOffset();
                int endOffset = expression.getEndOffset();
                IrType type = expression.getType();
                IrSimpleFunctionSymbol getter = propertyReference.getGetter();
                Intrinsics.checkNotNull(getter);
                IrCallImpl irCallImplFromSymbolOwner = companion.fromSymbolOwner(startOffset, endOffset, type, getter, (240 & 16) != 0 ? getter.getOwner().getTypeParameters().size() : 0, (240 & 32) != 0 ? getter.getOwner().getValueParameters().size() : 0, (240 & 64) != 0 ? null : INLINED_FUNCTION_REFERENCE.INSTANCE, (240 & 128) != 0 ? null : null);
                IrExpression dispatchReceiver = propertyReference.getDispatchReceiver();
                if (dispatchReceiver == null) {
                    dispatchReceiver = propertyReference.getExtensionReceiver();
                }
                IrExpression irExpression = null;
                if (irCallImplFromSymbolOwner.getSymbol().getOwner().getDispatchReceiverParameter() != null) {
                    irExpressionInlinePropertyReference$tryToGetArg = dispatchReceiver == null ? inlinePropertyReference$tryToGetArg(expression, this, 0) : dispatchReceiver;
                } else {
                    irExpressionInlinePropertyReference$tryToGetArg = null;
                }
                irCallImplFromSymbolOwner.setDispatchReceiver(irExpressionInlinePropertyReference$tryToGetArg);
                if (irCallImplFromSymbolOwner.getSymbol().getOwner().getExtensionReceiverParameter() != null) {
                    if (irCallImplFromSymbolOwner.getSymbol().getOwner().getDispatchReceiverParameter() != null) {
                        dispatchReceiver = inlinePropertyReference$tryToGetArg(expression, this, dispatchReceiver == null ? 1 : 0);
                    } else if (dispatchReceiver == null) {
                        dispatchReceiver = inlinePropertyReference$tryToGetArg(expression, this, 0);
                    }
                    irExpression = dispatchReceiver;
                }
                irCallImplFromSymbolOwner.setExtensionReceiver(irExpression);
                return wrapInStubFunction(super.visitExpression(irCallImplFromSymbolOwner), expression, propertyReference);
            }

            private static final IrExpression inlinePropertyReference$tryToGetArg(IrCall irCall, ParameterSubstitutor parameterSubstitutor, int i) {
                IrExpression valueArgument;
                if (i < irCall.getValueArgumentsCount() && (valueArgument = irCall.getValueArgument(i)) != null) {
                    return valueArgument.transform((IrElementTransformer<? super Object>) parameterSubstitutor, (Object) null);
                }
                return null;
            }

            /* JADX WARN: Code duplicated, block: B:13:0x00a3  */
            private final IrReturnableBlock wrapInStubFunction(IrExpression inlinedCall, IrFunctionAccessExpression invokeCall, IrCallableReference<?> reference) {
                IrReturnImpl irReturnImpl;
                IrFactory irFactory = Inliner.this.getContext().getIrFactory();
                IrFunctionBuilder irFunctionBuilder = new IrFunctionBuilder();
                irFunctionBuilder.setStartOffset(inlinedCall.getStartOffset());
                irFunctionBuilder.setEndOffset(inlinedCall.getEndOffset());
                Name nameIdentifier = Name.identifier("stub_for_ir_inlining");
                Intrinsics.checkNotNullExpressionValue(nameIdentifier, "identifier(...)");
                irFunctionBuilder.setName(nameIdentifier);
                DescriptorVisibility LOCAL = DescriptorVisibilities.LOCAL;
                Intrinsics.checkNotNullExpressionValue(LOCAL, "LOCAL");
                irFunctionBuilder.setVisibility(LOCAL);
                irFunctionBuilder.setReturnType(inlinedCall.getType());
                irFunctionBuilder.setSuspend(IrUtilsKt.isSuspend(reference.getSymbol()));
                IrSimpleFunction irSimpleFunctionBuildFunction = DeclarationBuildersKt.buildFunction(irFactory, irFunctionBuilder);
                Inliner inliner = Inliner.this;
                IrBlockBody irBlockBodyCreateBlockBody = inliner.getContext().getIrFactory().createBlockBody(-1, -1);
                if (reference instanceof IrPropertyReference) {
                    IrPropertyReference irPropertyReference = (IrPropertyReference) reference;
                    if (irPropertyReference.getField() != null) {
                        IrFieldSymbol field = irPropertyReference.getField();
                        Intrinsics.checkNotNull(field);
                        IrField owner = field.getOwner();
                        IrExpression dispatchReceiver = reference.getDispatchReceiver();
                        if (dispatchReceiver == null) {
                            dispatchReceiver = reference.getExtensionReceiver();
                        }
                        if (owner.getIsStatic()) {
                            dispatchReceiver = null;
                        }
                        irReturnImpl = new IrGetFieldImpl(-1, -1, owner.getSymbol(), owner.getType(), dispatchReceiver, null, null, 96, null);
                    } else {
                        irReturnImpl = new IrReturnImpl(-1, -1, inliner.getContext().getIrBuiltIns().getNothingType(), irSimpleFunctionBuildFunction.getSymbol(), inlinedCall);
                    }
                } else {
                    irReturnImpl = new IrReturnImpl(-1, -1, inliner.getContext().getIrBuiltIns().getNothingType(), irSimpleFunctionBuildFunction.getSymbol(), inlinedCall);
                }
                irBlockBodyCreateBlockBody.getStatements().add(irReturnImpl);
                irSimpleFunctionBuildFunction.setBody(irBlockBodyCreateBlockBody);
                irSimpleFunctionBuildFunction.setParent(inliner.getCallee().getParent());
                return Inliner.this.inlineFunction(invokeCall, irSimpleFunctionBuildFunction, reference, false);
            }

            public final IrExpression inlineAdaptedFunctionReference(IrCall irCall, IrBlock irBlock) {
                Intrinsics.checkNotNullParameter(irCall, "irCall");
                Intrinsics.checkNotNullParameter(irBlock, "irBlock");
                IrStatement irStatement = irBlock.getStatements().get(0);
                Inliner inliner = Inliner.this;
                IrStatement irStatement2 = irStatement;
                IrElementTransformerVoidKt.transformChildrenVoid(irStatement2, this);
                IrElement irElementCopy = inliner.getCopyIrElement().copy(irStatement2);
                Intrinsics.checkNotNull(irElementCopy, "null cannot be cast to non-null type org.jetbrains.kotlin.ir.declarations.IrFunction");
                IrFunction irFunction = (IrFunction) irElementCopy;
                IrStatement irStatement3 = irBlock.getStatements().get(1);
                Intrinsics.checkNotNull(irStatement3, "null cannot be cast to non-null type org.jetbrains.kotlin.ir.expressions.IrFunctionReference");
                IrExpression irExpressionInlineFunctionReference = inlineFunctionReference(irCall, (IrFunctionReference) irStatement3, irFunction);
                return new IrBlockImpl(irCall.getStartOffset(), irCall.getEndOffset(), irExpressionInlineFunctionReference.getType(), null, CollectionsKt.listOf((Object[]) new IrElement[]{irFunction, irExpressionInlineFunctionReference}));
            }

            public final IrExpression inlineFunctionReference(IrCall irCall, IrFunctionReference irFunctionReference, IrFunctionSymbol inlinedFunctionSymbol) {
                Intrinsics.checkNotNullParameter(irCall, "irCall");
                Intrinsics.checkNotNullParameter(irFunctionReference, "irFunctionReference");
                Intrinsics.checkNotNullParameter(inlinedFunctionSymbol, "inlinedFunctionSymbol");
                IrFunction owner = inlinedFunctionSymbol.getOwner();
                if (Inliner.this.this$0.getNeedsInlining(owner)) {
                    owner = Inliner.this.this$0.inlineFunctionResolver.getFunctionDeclaration(owner.getSymbol());
                }
                return inlineFunctionReference(irCall, irFunctionReference, owner);
            }

            /* JADX WARN: Code duplicated, block: B:67:0x0301  */
            /* JADX WARN: Code duplicated, block: B:68:0x0314  */
            /* JADX WARN: Code duplicated, block: B:70:0x031e  */
            /* JADX WARN: Code duplicated, block: B:71:0x0331  */
            public final IrExpression inlineFunctionReference(IrCall irCall, IrFunctionReference irFunctionReference, IrFunction inlinedFunction) {
                IrCallImpl irCallImpl;
                IrReturnableBlock irReturnableBlockWrapInStubFunction;
                Iterator<IrValueParameter> it;
                Set set;
                LinkedHashMap linkedHashMap;
                IrVarargImpl irVarargImpl;
                Intrinsics.checkNotNullParameter(irCall, "irCall");
                Intrinsics.checkNotNullParameter(irFunctionReference, "irFunctionReference");
                Intrinsics.checkNotNullParameter(inlinedFunction, "inlinedFunction");
                IrFunctionReference irFunctionReference2 = irFunctionReference;
                IrElementTransformerVoidKt.transformChildrenVoid(irFunctionReference2, this);
                IrFunction owner = irFunctionReference.getSymbol().getOwner();
                List<IrValueParameter> explicitParameters = IrUtilsKt.getExplicitParameters(owner);
                List<IrValueParameter> list = explicitParameters;
                List<Pair<IrValueParameter, IrExpression>> argumentsWithIr = IrUtilsKt.getArgumentsWithIr(irFunctionReference);
                ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(argumentsWithIr, 10));
                Iterator<T> it2 = argumentsWithIr.iterator();
                while (it2.hasNext()) {
                    arrayList.add((IrValueParameter) ((Pair) it2.next()).getFirst());
                }
                List listMinus = CollectionsKt.minus((Iterable) list, (Iterable) arrayList);
                LinkedHashMap linkedHashMap2 = new LinkedHashMap(RangesKt.coerceAtLeast(MapsKt.mapCapacity(CollectionsKt.collectionSizeOrDefault(argumentsWithIr, 10)), 16));
                Iterator<T> it3 = argumentsWithIr.iterator();
                while (it3.hasNext()) {
                    Pair pair = (Pair) it3.next();
                    Pair pair2 = TuplesKt.to(pair.getFirst(), pair.getSecond());
                    linkedHashMap2.put(pair2.getFirst(), pair2.getSecond());
                }
                Set set2 = CollectionsKt.toSet(listMinus);
                List listDrop = CollectionsKt.drop(IrUtilsKt.getArgumentsWithIr(irCall), 1);
                IrType type = irFunctionReference.getType();
                Intrinsics.checkNotNull(type, "null cannot be cast to non-null type org.jetbrains.kotlin.ir.types.IrSimpleType");
                IrSimpleType irSimpleType = (IrSimpleType) type;
                List<IrTypeParameter> typeParameters = IrUtilsKt.getParentAsClass(irCall.getSymbol().getOwner()).getTypeParameters();
                LinkedHashMap linkedHashMap3 = new LinkedHashMap(RangesKt.coerceAtLeast(MapsKt.mapCapacity(CollectionsKt.collectionSizeOrDefault(typeParameters, 10)), 16));
                for (IrTypeParameter irTypeParameter : typeParameters) {
                    IrTypeParameterSymbol symbol = irTypeParameter.getSymbol();
                    IrType typeOrNull = IrTypesKt.getTypeOrNull(irSimpleType.getArguments().get(irTypeParameter.getIndex()));
                    Intrinsics.checkNotNull(typeOrNull);
                    Pair pair3 = TuplesKt.to(symbol, typeOrNull);
                    linkedHashMap3.put(pair3.getFirst(), pair3.getSecond());
                }
                if (irSimpleType.getArguments().isEmpty()) {
                    throw new IllegalArgumentException(("type should have at least one type argument: " + RenderIrElementKt.render$default(irSimpleType, (DumpIrTreeOptions) null, 1, (Object) null)).toString());
                }
                IrType typeOrFail = IrTypesKt.getTypeOrFail((IrTypeArgument) CollectionsKt.last((List) irSimpleType.getArguments()));
                if (inlinedFunction instanceof IrConstructor) {
                    irCallImpl = IrConstructorCallImpl.INSTANCE.fromSymbolOwner(Inliner.this.this$0.inlineArgumentsWithTheirOriginalTypeAndOffset ? irFunctionReference.getStartOffset() : irCall.getStartOffset(), Inliner.this.this$0.inlineArgumentsWithTheirOriginalTypeAndOffset ? irFunctionReference.getEndOffset() : irCall.getEndOffset(), typeOrFail, ((IrConstructor) inlinedFunction).getSymbol(), IrUtilsKt.getParentAsClass(inlinedFunction).getTypeParameters().size(), INLINED_FUNCTION_REFERENCE.INSTANCE);
                } else if (inlinedFunction instanceof IrSimpleFunction) {
                    irCallImpl = new IrCallImpl(Inliner.this.this$0.inlineArgumentsWithTheirOriginalTypeAndOffset ? irFunctionReference.getStartOffset() : irCall.getStartOffset(), Inliner.this.this$0.inlineArgumentsWithTheirOriginalTypeAndOffset ? irFunctionReference.getEndOffset() : irCall.getEndOffset(), typeOrFail, ((IrSimpleFunction) inlinedFunction).getSymbol(), inlinedFunction.getTypeParameters().size(), inlinedFunction.getValueParameters().size(), INLINED_FUNCTION_REFERENCE.INSTANCE, null, 128, null);
                } else {
                    throw new IllegalStateException(("Unknown function kind : " + RenderIrElementKt.render$default(inlinedFunction, (DumpIrTreeOptions) null, 1, (Object) null)).toString());
                }
                Inliner inliner = Inliner.this;
                Iterator<IrValueParameter> it4 = explicitParameters.iterator();
                int i = 0;
                while (it4.hasNext()) {
                    IrValueParameter next = it4.next();
                    if (!set2.contains(next)) {
                        Object obj = linkedHashMap2.get(next);
                        Intrinsics.checkNotNull(obj);
                        IrExpression irExpression = (IrExpression) obj;
                        it = it4;
                        if (!(irExpression instanceof IrGetValueWithoutLocation)) {
                            set = set2;
                            irVarargImpl = (IrExpression) inliner.copy(irExpression);
                        } else {
                            set = set2;
                            irVarargImpl = ((IrGetValueWithoutLocation) irExpression).withLocation(irCall.getStartOffset(), irCall.getEndOffset());
                        }
                    } else {
                        it = it4;
                        set = set2;
                        if (i == listDrop.size() && next.getDefaultValue() != null) {
                            IrExpressionBody defaultValue = next.getDefaultValue();
                            Intrinsics.checkNotNull(defaultValue);
                            irVarargImpl = (IrExpression) inliner.copy(defaultValue.getExpression());
                        } else {
                            if (!AdditionalIrUtilsKt.isVararg(next)) {
                                listDrop.size();
                                IrExpression irExpression2 = (IrExpression) ((Pair) listDrop.get(i)).getSecond();
                                i++;
                                irVarargImpl = irExpression2;
                            } else {
                                ArrayList arrayList2 = new ArrayList();
                                while (i < listDrop.size()) {
                                    int i2 = i + 1;
                                    Pair pair4 = (Pair) listDrop.get(i);
                                    IrValueParameter irValueParameter = (IrValueParameter) pair4.component1();
                                    IrExpression irExpression3 = (IrExpression) pair4.component2();
                                    IrType irTypeSubstitute = IrTypeUtilsKt.substitute(irValueParameter.getType(), linkedHashMap3);
                                    IrType varargElementType = next.getVarargElementType();
                                    Intrinsics.checkNotNull(varargElementType);
                                    if (Intrinsics.areEqual(irTypeSubstitute, varargElementType)) {
                                        arrayList2.add(irExpression3);
                                    } else {
                                        arrayList2.add(new IrSpreadElementImpl(irCall.getStartOffset(), irCall.getEndOffset(), irExpression3));
                                    }
                                    i = i2;
                                    linkedHashMap3 = linkedHashMap3;
                                }
                                linkedHashMap = linkedHashMap3;
                                int startOffset = irCall.getStartOffset();
                                int endOffset = irCall.getEndOffset();
                                IrType type2 = next.getType();
                                IrType varargElementType2 = next.getVarargElementType();
                                Intrinsics.checkNotNull(varargElementType2);
                                irVarargImpl = new IrVarargImpl(startOffset, endOffset, type2, varargElementType2, arrayList2);
                            }
                            if (Intrinsics.areEqual(next, owner.getDispatchReceiverParameter())) {
                                IrValueParameter dispatchReceiverParameter = inlinedFunction.getDispatchReceiverParameter();
                                Intrinsics.checkNotNull(dispatchReceiverParameter);
                                irCallImpl.setDispatchReceiver(inliner.doImplicitCastIfNeededTo(irVarargImpl, dispatchReceiverParameter.getType()));
                            } else if (Intrinsics.areEqual(next, owner.getExtensionReceiverParameter())) {
                                IrValueParameter extensionReceiverParameter = inlinedFunction.getExtensionReceiverParameter();
                                Intrinsics.checkNotNull(extensionReceiverParameter);
                                irCallImpl.setExtensionReceiver(inliner.doImplicitCastIfNeededTo(irVarargImpl, extensionReceiverParameter.getType()));
                            } else {
                                irCallImpl.putValueArgument(next.getIndex(), inliner.doImplicitCastIfNeededTo(irVarargImpl, inlinedFunction.getValueParameters().get(next.getIndex()).getType()));
                            }
                            it4 = it;
                            set2 = set;
                            linkedHashMap3 = linkedHashMap;
                        }
                    }
                    linkedHashMap = linkedHashMap3;
                    if (Intrinsics.areEqual(next, owner.getDispatchReceiverParameter())) {
                        IrValueParameter dispatchReceiverParameter2 = inlinedFunction.getDispatchReceiverParameter();
                        Intrinsics.checkNotNull(dispatchReceiverParameter2);
                        irCallImpl.setDispatchReceiver(inliner.doImplicitCastIfNeededTo(irVarargImpl, dispatchReceiverParameter2.getType()));
                    } else if (Intrinsics.areEqual(next, owner.getExtensionReceiverParameter())) {
                        IrValueParameter extensionReceiverParameter2 = inlinedFunction.getExtensionReceiverParameter();
                        Intrinsics.checkNotNull(extensionReceiverParameter2);
                        irCallImpl.setExtensionReceiver(inliner.doImplicitCastIfNeededTo(irVarargImpl, extensionReceiverParameter2.getType()));
                    } else {
                        irCallImpl.putValueArgument(next.getIndex(), inliner.doImplicitCastIfNeededTo(irVarargImpl, inlinedFunction.getValueParameters().get(next.getIndex()).getType()));
                    }
                    it4 = it;
                    set2 = set;
                    linkedHashMap3 = linkedHashMap;
                }
                listDrop.size();
                int typeArgumentsCount = irFunctionReference.getTypeArgumentsCount();
                for (int i3 = 0; i3 < typeArgumentsCount; i3++) {
                    irCallImpl.putTypeArgument(i3, irFunctionReference.getTypeArgument(i3));
                }
                Inliner inliner2 = Inliner.this;
                if (inliner2.this$0.getNeedsInlining(inlinedFunction) && inlinedFunction.getBody() != null) {
                    irReturnableBlockWrapInStubFunction = Inliner.this.inlineFunction(irCallImpl, inlinedFunction, irFunctionReference2, true);
                } else {
                    irReturnableBlockWrapInStubFunction = wrapInStubFunction(super.visitExpression(irCallImpl).transform((IrElementTransformer<? super Object>) Inliner.this.this$0, (Object) null), irCall, irFunctionReference);
                }
                return inliner2.doImplicitCastIfNeededTo(irReturnableBlockWrapInStubFunction, irCall.getType());
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
            public IrElement visitElement(IrElement element) {
                Intrinsics.checkNotNullParameter(element, "element");
                return (IrElement) element.accept(this, null);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final IrExpression doImplicitCastIfNeededTo(IrExpression irExpression, IrType irType) {
            return !this.this$0.insertAdditionalImplicitCasts ? irExpression : IrUtilsKt.implicitCastIfNeededTo(irExpression, irType);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final IrExpression unwrapAdditionalImplicitCastsIfNeeded(IrExpression irExpression) {
            if (!this.this$0.insertAdditionalImplicitCasts || !(irExpression instanceof IrTypeOperatorCall)) {
                return irExpression;
            }
            IrTypeOperatorCall irTypeOperatorCall = (IrTypeOperatorCall) irExpression;
            return irTypeOperatorCall.getOperator() == IrTypeOperator.IMPLICIT_CAST ? unwrapAdditionalImplicitCastsIfNeeded(irTypeOperatorCall.getArgument()) : irExpression;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final boolean isLambdaCall(IrCall irCall) {
            IrSimpleFunction owner = irCall.getSymbol().getOwner();
            IrValueParameter dispatchReceiverParameter = owner.getDispatchReceiverParameter();
            if (dispatchReceiverParameter == null) {
                return false;
            }
            if ((!IrTypeUtilsKt.isFunctionOrKFunction(dispatchReceiverParameter.getType()) && !IrTypeUtilsKt.isSuspendFunctionOrKFunction(dispatchReceiverParameter.getType())) || !Intrinsics.areEqual(owner.getName(), OperatorNameConventions.INVOKE)) {
                return false;
            }
            IrExpression dispatchReceiver = irCall.getDispatchReceiver();
            return (dispatchReceiver != null ? unwrapAdditionalImplicitCastsIfNeeded(dispatchReceiver) : null) instanceof IrGetValue;
        }

        /* JADX INFO: compiled from: FunctionInlining.kt */
        @Metadata(d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\n\b\u0082\u0004\u0018\u00002\u00020\u0001B\u001f\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\b\b\u0002\u0010\u0006\u001a\u00020\u0007¢\u0006\u0002\u0010\bR\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u0011\u0010\u0006\u001a\u00020\u0007¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u000bR\u0011\u0010\f\u001a\u00020\u00078F¢\u0006\u0006\u001a\u0004\b\f\u0010\u000bR\u0011\u0010\r\u001a\u00020\u00078F¢\u0006\u0006\u001a\u0004\b\r\u0010\u000bR\u0011\u0010\u000e\u001a\u00020\u00078F¢\u0006\u0006\u001a\u0004\b\u000e\u0010\u000bR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0010¨\u0006\u0011"}, d2 = {"Lorg/jetbrains/kotlin/backend/common/lower/inline/FunctionInlining$Inliner$ParameterToArgument;", "", "parameter", "Lorg/jetbrains/kotlin/ir/declarations/IrValueParameter;", "argumentExpression", "Lorg/jetbrains/kotlin/ir/expressions/IrExpression;", "isDefaultArg", "", "(Lorg/jetbrains/kotlin/backend/common/lower/inline/FunctionInlining$Inliner;Lorg/jetbrains/kotlin/ir/declarations/IrValueParameter;Lorg/jetbrains/kotlin/ir/expressions/IrExpression;Z)V", "getArgumentExpression", "()Lorg/jetbrains/kotlin/ir/expressions/IrExpression;", "()Z", "isImmutableVariableLoad", "isInlinableLambdaArgument", "isInlinablePropertyReference", "getParameter", "()Lorg/jetbrains/kotlin/ir/declarations/IrValueParameter;", "ir.backend.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
        private final class ParameterToArgument {
            private final IrExpression argumentExpression;
            private final boolean isDefaultArg;
            private final IrValueParameter parameter;
            final /* synthetic */ Inliner this$0;

            public ParameterToArgument(Inliner inliner, IrValueParameter parameter, IrExpression argumentExpression, boolean z) {
                Intrinsics.checkNotNullParameter(parameter, "parameter");
                Intrinsics.checkNotNullParameter(argumentExpression, "argumentExpression");
                this.this$0 = inliner;
                this.parameter = parameter;
                this.argumentExpression = argumentExpression;
                this.isDefaultArg = z;
            }

            public /* synthetic */ ParameterToArgument(Inliner inliner, IrValueParameter irValueParameter, IrExpression irExpression, boolean z, int i, DefaultConstructorMarker defaultConstructorMarker) {
                this(inliner, irValueParameter, irExpression, (i & 4) != 0 ? false : z);
            }

            public final IrValueParameter getParameter() {
                return this.parameter;
            }

            public final IrExpression getArgumentExpression() {
                return this.argumentExpression;
            }

            /* JADX INFO: renamed from: isDefaultArg, reason: from getter */
            public final boolean getIsDefaultArg() {
                return this.isDefaultArg;
            }

            public final boolean isInlinableLambdaArgument() {
                if (FunctionInliningKt.isInlineParameter$default(this.this$0.getOriginalParameter(this.parameter), null, 1, null)) {
                    IrExpression irExpression = this.argumentExpression;
                    if ((irExpression instanceof IrFunctionReference) || (irExpression instanceof IrFunctionExpression) || FunctionInliningKt.isAdaptedFunctionReference(irExpression)) {
                        return true;
                    }
                }
                return false;
            }

            public final boolean isInlinablePropertyReference() {
                return FunctionInliningKt.isInlineParameter$default(this.this$0.getOriginalParameter(this.parameter), null, 1, null) && (this.argumentExpression instanceof IrPropertyReference);
            }

            public final boolean isImmutableVariableLoad() {
                IrExpression irExpression = this.argumentExpression;
                if (irExpression instanceof IrGetValue) {
                    IrValueDeclaration owner = ((IrGetValue) irExpression).getSymbol().getOwner();
                    if (!(owner instanceof IrVariable) || !((IrVariable) owner).getIsVar()) {
                        return true;
                    }
                }
                return false;
            }
        }

        private final List<ParameterToArgument> andAllOuterClasses(ParameterToArgument parameterToArgument) {
            List<ParameterToArgument> listMutableListOf = CollectionsKt.mutableListOf(parameterToArgument);
            if (this.this$0.innerClassesSupport == null) {
                return listMutableListOf;
            }
            IrValueParameterSymbol symbol = parameterToArgument.getParameter().getSymbol();
            IrClassifierSymbol classifierOrNull = IrTypesKt.getClassifierOrNull(parameterToArgument.getParameter().getType());
            IrSymbolOwner owner = classifierOrNull != null ? classifierOrNull.getOwner() : null;
            IrClass irClass = owner instanceof IrClass ? (IrClass) owner : null;
            if (irClass == null) {
                return listMutableListOf;
            }
            while (irClass.getIsInner()) {
                IrClass parentAsClass = IrUtilsKt.getParentAsClass(irClass);
                IrValueParameter thisReceiver = parentAsClass.getThisReceiver();
                if (thisReceiver == null) {
                    throw new IllegalStateException((parentAsClass.getName() + " has a null `thisReceiver` property").toString());
                }
                listMutableListOf.add(new ParameterToArgument(this, thisReceiver, new IrGetFieldImpl(-1, -1, this.this$0.innerClassesSupport.getOuterThisField(irClass).getSymbol(), thisReceiver.getType(), new IrGetValueImpl(-1, -1, symbol, null, 8, null), null, null, 96, null), false, 4, null));
                symbol = thisReceiver.getSymbol();
                irClass = parentAsClass;
            }
            return listMutableListOf;
        }

        private final List<ParameterToArgument> buildParameterToArgument(IrFunctionAccessExpression callSite, IrFunction callee) {
            IrExpression extensionReceiver;
            ArrayList arrayList = new ArrayList();
            if (callSite.getDispatchReceiver() != null && callee.getDispatchReceiverParameter() != null) {
                IrValueParameter dispatchReceiverParameter = callee.getDispatchReceiverParameter();
                Intrinsics.checkNotNull(dispatchReceiverParameter);
                IrExpression dispatchReceiver = callSite.getDispatchReceiver();
                Intrinsics.checkNotNull(dispatchReceiver);
                CollectionsKt.addAll(arrayList, andAllOuterClasses(new ParameterToArgument(this, dispatchReceiverParameter, dispatchReceiver, false, 4, null)));
            }
            List<IrValueParameter> valueParameters = callSite.getSymbol().getOwner().getValueParameters();
            ArrayList arrayList2 = new ArrayList(CollectionsKt.collectionSizeOrDefault(valueParameters, 10));
            Iterator<T> it = valueParameters.iterator();
            while (it.hasNext()) {
                arrayList2.add(callSite.getValueArgument(((IrValueParameter) it.next()).getIndex()));
            }
            List mutableList = CollectionsKt.toMutableList((Collection) arrayList2);
            if (callee.getExtensionReceiverParameter() != null) {
                ArrayList arrayList3 = arrayList;
                IrValueParameter extensionReceiverParameter = callee.getExtensionReceiverParameter();
                Intrinsics.checkNotNull(extensionReceiverParameter);
                if (callSite.getExtensionReceiver() != null) {
                    extensionReceiver = callSite.getExtensionReceiver();
                    Intrinsics.checkNotNull(extensionReceiver);
                } else {
                    Object objRemove = mutableList.remove(0);
                    Intrinsics.checkNotNull(objRemove);
                    extensionReceiver = (IrExpression) objRemove;
                }
                arrayList3.add(new ParameterToArgument(this, extensionReceiverParameter, extensionReceiver, false, 4, null));
            } else if (callSite.getExtensionReceiver() != null) {
                IrExpression extensionReceiver2 = callSite.getExtensionReceiver();
                Intrinsics.checkNotNull(extensionReceiver2);
                mutableList.add(0, extensionReceiver2);
            }
            ArrayList arrayList4 = new ArrayList();
            for (IrValueParameter irValueParameter : callee.getValueParameters()) {
                IrExpression irExpression = (IrExpression) mutableList.get(irValueParameter.getIndex());
                if (irExpression != null) {
                    arrayList.add(new ParameterToArgument(this, irValueParameter, irExpression, false, 4, null));
                } else if (irValueParameter.getDefaultValue() != null) {
                    IrExpressionBody defaultValue = irValueParameter.getDefaultValue();
                    Intrinsics.checkNotNull(defaultValue);
                    arrayList4.add(new ParameterToArgument(this, irValueParameter, defaultValue.getExpression(), true));
                } else if (irValueParameter.getVarargElementType() != null) {
                    int startOffset = callSite.getStartOffset();
                    int endOffset = callSite.getEndOffset();
                    IrType type = irValueParameter.getType();
                    IrType varargElementType = irValueParameter.getVarargElementType();
                    Intrinsics.checkNotNull(varargElementType);
                    arrayList.add(new ParameterToArgument(this, irValueParameter, new IrVarargImpl(startOffset, endOffset, type, varargElementType), false, 4, null));
                } else {
                    throw new Error("Incomplete expression: call to " + RenderIrElementKt.render$default(callee, (DumpIrTreeOptions) null, 1, (Object) null) + " has no argument at index " + irValueParameter.getIndex());
                }
            }
            return CollectionsKt.plus((Collection) arrayList, (Iterable) arrayList4);
        }

        /* JADX WARN: Multi-variable type inference failed */
        private final List<IrVariable> evaluateArguments(IrCallableReference<?> reference) {
            IrFunction owner;
            IrGetValueWithoutLocation irGetValueWithoutLocation;
            List<Pair<IrValueParameter, IrExpression>> argumentsWithIr = IrUtilsKt.getArgumentsWithIr(reference);
            ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(argumentsWithIr, 10));
            Iterator<T> it = argumentsWithIr.iterator();
            while (it.hasNext()) {
                Pair pair = (Pair) it.next();
                arrayList.add(new ParameterToArgument(this, (IrValueParameter) pair.getFirst(), (IrExpression) pair.getSecond(), false, 4, null));
            }
            ArrayList<ParameterToArgument> arrayList2 = arrayList;
            ArrayList arrayList3 = new ArrayList();
            ParameterSubstitutor parameterSubstitutor = new ParameterSubstitutor();
            if (reference instanceof IrFunctionReference) {
                owner = ((IrFunctionReference) reference).getSymbol().getOwner();
            } else {
                if (!(reference instanceof IrPropertyReference)) {
                    throw new IllegalStateException(toString());
                }
                IrSimpleFunctionSymbol getter = ((IrPropertyReference) reference).getGetter();
                Intrinsics.checkNotNull(getter);
                owner = getter.getOwner();
            }
            FunctionInlining functionInlining = this.this$0;
            for (ParameterToArgument parameterToArgument : arrayList2) {
                IrStatementOrigin irStatementOrigin = null;
                Object[] objArr = 0;
                Object[] objArr2 = 0;
                Object[] objArr3 = 0;
                IrExpression irExpressionTransform = parameterToArgument.getArgumentExpression().transform((IrElementTransformer<? super Object>) parameterSubstitutor, (Object) null);
                int i = 2;
                if (!parameterToArgument.isImmutableVariableLoad()) {
                    IrVariable irVariableCreateTemporaryVariable = this.currentScope.getScope().createTemporaryVariable(irExpressionTransform, (108 & 2) != 0 ? null : this.callee.getSymbol().getOwner().getName().asStringStripSpecialMarkers() + '_' + parameterToArgument.getParameter().getName().asStringStripSpecialMarkers(), (108 & 4) != 0 ? false : false, (108 & 8) != 0 ? IrDeclarationOrigin.IR_TEMPORARY_VARIABLE.INSTANCE : null, (108 & 16) == 0 ? functionInlining.inlineArgumentsWithTheirOriginalTypeAndOffset ? getOriginalType(parameterToArgument.getParameter()) : irExpressionTransform.getType() : null, (108 & 32) != 0 ? irExpressionTransform.getStartOffset() : parameterToArgument.getIsDefaultArg() ? irExpressionTransform.getStartOffset() : -1, (108 & 64) != 0 ? irExpressionTransform.getEndOffset() : parameterToArgument.getIsDefaultArg() ? irExpressionTransform.getStartOffset() : -1);
                    arrayList3.add(irVariableCreateTemporaryVariable);
                    irGetValueWithoutLocation = new IrGetValueWithoutLocation(irVariableCreateTemporaryVariable.getSymbol(), objArr2 == true ? 1 : 0, i, objArr == true ? 1 : 0);
                } else {
                    Intrinsics.checkNotNull(irExpressionTransform, "null cannot be cast to non-null type org.jetbrains.kotlin.ir.expressions.IrGetValue");
                    irGetValueWithoutLocation = new IrGetValueWithoutLocation(((IrGetValue) irExpressionTransform).getSymbol(), irStatementOrigin, i, objArr3 == true ? 1 : 0);
                }
                IrValueParameter parameter = parameterToArgument.getParameter();
                if (Intrinsics.areEqual(parameter, owner.getDispatchReceiverParameter())) {
                    reference.setDispatchReceiver(irGetValueWithoutLocation);
                } else if (Intrinsics.areEqual(parameter, owner.getExtensionReceiverParameter())) {
                    reference.setExtensionReceiver(irGetValueWithoutLocation);
                } else {
                    reference.putValueArgument(parameterToArgument.getParameter().getIndex(), irGetValueWithoutLocation);
                }
            }
            return arrayList3;
        }

        /* JADX WARN: Multi-variable type inference failed */
        private final IrVariable evaluateReceiverForPropertyWithField(IrPropertyReference reference) {
            IrExpression dispatchReceiver = reference.getDispatchReceiver();
            IrStatementOrigin irStatementOrigin = null;
            Object[] objArr = 0;
            if (dispatchReceiver == null && (dispatchReceiver = reference.getExtensionReceiver()) == null) {
                return null;
            }
            IrExpression irExpressionTransform = dispatchReceiver.transform((IrElementTransformer<? super Object>) new ParameterSubstitutor(), (Object) null);
            IrVariable irVariableCreateTemporaryVariable = this.currentScope.getScope().createTemporaryVariable(irExpressionTransform, (108 & 2) != 0 ? null : this.callee.getSymbol().getOwner().getName().asStringStripSpecialMarkers() + "_this", (108 & 4) != 0 ? false : false, (108 & 8) != 0 ? IrDeclarationOrigin.IR_TEMPORARY_VARIABLE.INSTANCE : null, (108 & 16) == 0 ? null : null, (108 & 32) != 0 ? irExpressionTransform.getStartOffset() : -1, (108 & 64) != 0 ? irExpressionTransform.getEndOffset() : -1);
            IrGetValueWithoutLocation irGetValueWithoutLocation = new IrGetValueWithoutLocation(irVariableCreateTemporaryVariable.getSymbol(), irStatementOrigin, 2, objArr == true ? 1 : 0);
            if (reference.getDispatchReceiver() != null) {
                reference.setDispatchReceiver(irGetValueWithoutLocation);
            } else if (reference.getExtensionReceiver() != null) {
                reference.setExtensionReceiver(irGetValueWithoutLocation);
            }
            return irVariableCreateTemporaryVariable;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final IrValueParameter getOriginalParameter(IrValueParameter irValueParameter) {
            if (!(irValueParameter.getParent() instanceof IrFunction)) {
                return irValueParameter;
            }
            IrDeclarationParent parent = irValueParameter.getParent();
            Intrinsics.checkNotNull(parent, "null cannot be cast to non-null type org.jetbrains.kotlin.ir.declarations.IrFunction");
            Iterator<T> it = IrUtilsKt.getAllParameters(IrUtilsKt.getOriginalFunction((IrFunction) parent)).iterator();
            Object obj = null;
            boolean z = false;
            Object obj2 = null;
            while (true) {
                if (!it.hasNext()) {
                    if (!z) {
                        break;
                    }
                    obj = obj2;
                    break;
                }
                Object next = it.next();
                IrValueParameter irValueParameter2 = (IrValueParameter) next;
                if (Intrinsics.areEqual(irValueParameter2.getName(), irValueParameter.getName()) && irValueParameter2.getStartOffset() == irValueParameter.getStartOffset()) {
                    if (z) {
                        break;
                    }
                    z = true;
                    obj2 = next;
                }
            }
            IrValueParameter irValueParameter3 = (IrValueParameter) obj;
            return irValueParameter3 == null ? irValueParameter : irValueParameter3;
        }

        private final IrType getOriginalType(IrValueParameter irValueParameter) {
            IrType originalType$getTypeIfFromTypeParameter;
            IrType originalType$getTypeIfFromTypeParameter2;
            IrType originalType$getTypeIfFromTypeParameter3;
            if (!(irValueParameter.getParent() instanceof IrFunction)) {
                return irValueParameter.getType();
            }
            IrDeclarationParent parent = irValueParameter.getParent();
            Intrinsics.checkNotNull(parent, "null cannot be cast to non-null type org.jetbrains.kotlin.ir.declarations.IrFunction");
            IrFunction irFunction = (IrFunction) parent;
            IrFunction originalFunction = IrUtilsKt.getOriginalFunction(irFunction);
            if (Intrinsics.areEqual(irValueParameter, irFunction.getDispatchReceiverParameter())) {
                IrValueParameter dispatchReceiverParameter = originalFunction.getDispatchReceiverParameter();
                if (dispatchReceiverParameter != null && (originalType$getTypeIfFromTypeParameter3 = getOriginalType$getTypeIfFromTypeParameter(dispatchReceiverParameter, irFunction, this.this$0, this, originalFunction)) != null) {
                    return originalType$getTypeIfFromTypeParameter3;
                }
                IrValueParameter dispatchReceiverParameter2 = irFunction.getDispatchReceiverParameter();
                Intrinsics.checkNotNull(dispatchReceiverParameter2);
                return dispatchReceiverParameter2.getType();
            }
            if (Intrinsics.areEqual(irValueParameter, irFunction.getExtensionReceiverParameter())) {
                IrValueParameter extensionReceiverParameter = originalFunction.getExtensionReceiverParameter();
                if (extensionReceiverParameter != null && (originalType$getTypeIfFromTypeParameter2 = getOriginalType$getTypeIfFromTypeParameter(extensionReceiverParameter, irFunction, this.this$0, this, originalFunction)) != null) {
                    return originalType$getTypeIfFromTypeParameter2;
                }
                IrValueParameter extensionReceiverParameter2 = irFunction.getExtensionReceiverParameter();
                Intrinsics.checkNotNull(extensionReceiverParameter2);
                return extensionReceiverParameter2.getType();
            }
            for (IrValueParameter irValueParameter2 : irFunction.getValueParameters()) {
                if (Intrinsics.areEqual(irValueParameter2, irValueParameter)) {
                    FunctionInlining functionInlining = this.this$0;
                    IrValueParameter irValueParameter3 = (IrValueParameter) CollectionsKt.getOrNull(originalFunction.getValueParameters(), irValueParameter2.getIndex());
                    return (irValueParameter3 == null || (originalType$getTypeIfFromTypeParameter = getOriginalType$getTypeIfFromTypeParameter(irValueParameter3, irFunction, functionInlining, this, originalFunction)) == null) ? irValueParameter2.getType() : originalType$getTypeIfFromTypeParameter;
                }
            }
            throw new NoSuchElementException("Collection contains no element matching the predicate.");
        }

        private static final IrType getOriginalType$substituteSuperTypes(IrType irType, IrFunction irFunction, IrFunction irFunction2) {
            IrClassifierSymbol classifierOrNull = IrTypesKt.getClassifierOrNull(irType);
            IrSymbolOwner owner = classifierOrNull != null ? classifierOrNull.getOwner() : null;
            IrTypeParameter irTypeParameter = owner instanceof IrTypeParameter ? (IrTypeParameter) owner : null;
            if (irTypeParameter == null) {
                return irType;
            }
            List<IrType> superTypes = irFunction.getTypeParameters().get(irTypeParameter.getIndex()).getSuperTypes();
            ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(superTypes, 10));
            for (IrType originalType$substituteSuperTypes : superTypes) {
                IrClassifierSymbol classifierOrNull2 = IrTypesKt.getClassifierOrNull(originalType$substituteSuperTypes);
                IrSymbolOwner owner2 = classifierOrNull2 != null ? classifierOrNull2.getOwner() : null;
                IrTypeParameter irTypeParameter2 = owner2 instanceof IrTypeParameter ? (IrTypeParameter) owner2 : null;
                if (irTypeParameter2 != null) {
                    originalType$substituteSuperTypes = getOriginalType$substituteSuperTypes(IrTypesKt.getDefaultType(irFunction2.getTypeParameters().get(irTypeParameter2.getIndex())), irFunction, irFunction2);
                }
                arrayList.add(originalType$substituteSuperTypes);
            }
            irTypeParameter.setSuperTypes(arrayList);
            return irType;
        }

        private static final IrType getOriginalType$getTypeIfFromTypeParameter(IrValueParameter irValueParameter, IrFunction irFunction, FunctionInlining functionInlining, Inliner inliner, IrFunction irFunction2) {
            IrType type;
            IrClassifierSymbol classifierOrNull;
            IrSymbolOwner owner = (irValueParameter == null || (type = irValueParameter.getType()) == null || (classifierOrNull = IrTypesKt.getClassifierOrNull(type)) == null) ? null : classifierOrNull.getOwner();
            IrTypeParameter irTypeParameter = owner instanceof IrTypeParameter ? (IrTypeParameter) owner : null;
            if (irTypeParameter != null && Intrinsics.areEqual(irTypeParameter.getParent(), irValueParameter.getParent())) {
                return functionInlining.useTypeParameterUpperBound ? IrTypesKt.mergeNullability(inliner.firstRealUpperBound(irTypeParameter), irValueParameter.getType()) : getOriginalType$substituteSuperTypes(IrTypesKt.getDefaultType(irFunction.getTypeParameters().get(irTypeParameter.getIndex())), irFunction2, irFunction);
            }
            return null;
        }

        private final IrType firstRealUpperBound(IrTypeParameter irTypeParameter) {
            ArrayList arrayList;
            IrSymbolOwner owner;
            List<IrType> superTypes;
            if (irTypeParameter == null || (superTypes = irTypeParameter.getSuperTypes()) == null || (arrayList = CollectionsKt.toMutableList((Collection) superTypes)) == null) {
                arrayList = new ArrayList();
            }
            while (!arrayList.isEmpty()) {
                IrType irType = (IrType) CollectionsKt.removeFirst(arrayList);
                IrClassifierSymbol classifierOrNull = IrTypesKt.getClassifierOrNull(irType);
                if (classifierOrNull != null && (owner = classifierOrNull.getOwner()) != null) {
                    if (!(owner instanceof IrTypeParameter)) {
                        return irType;
                    }
                    arrayList.addAll(((IrTypeParameter) owner).getSuperTypes());
                }
            }
            return this.context.getIrBuiltIns().getAnyNType();
        }

        /* JADX WARN: Multi-variable type inference failed */
        private final List<IrStatement> evaluateArguments(IrFunctionAccessExpression callSite, IrFunction callee) {
            List<ParameterToArgument> listBuildParameterToArgument = buildParameterToArgument(callSite, callee);
            ArrayList arrayList = new ArrayList();
            ArrayList arrayList2 = new ArrayList();
            ParameterSubstitutor parameterSubstitutor = new ParameterSubstitutor();
            FunctionInlining functionInlining = this.this$0;
            Iterator<T> it = listBuildParameterToArgument.iterator();
            while (true) {
                int i = 2;
                Object[] objArr = 0;
                Object[] objArr2 = 0;
                Object[] objArr3 = 0;
                Object[] objArr4 = 0;
                if (!it.hasNext()) {
                    break;
                }
                ParameterToArgument parameterToArgument = (ParameterToArgument) it.next();
                IrValueParameter parameter = parameterToArgument.getParameter();
                if (parameterToArgument.isInlinableLambdaArgument() || parameterToArgument.isInlinablePropertyReference()) {
                    this.substituteMap.put(parameter, parameterToArgument.getArgumentExpression());
                    IrExpression argumentExpression = parameterToArgument.getArgumentExpression();
                    if (argumentExpression instanceof IrPropertyReference) {
                        IrPropertyReference irPropertyReference = (IrPropertyReference) argumentExpression;
                        if (irPropertyReference.getField() != null) {
                            IrVariable irVariableEvaluateReceiverForPropertyWithField = evaluateReceiverForPropertyWithField(irPropertyReference);
                            if (irVariableEvaluateReceiverForPropertyWithField != null) {
                                arrayList.add(irVariableEvaluateReceiverForPropertyWithField);
                            }
                        }
                    }
                    if (argumentExpression instanceof IrCallableReference) {
                        CollectionsKt.addAll(arrayList, evaluateArguments((IrCallableReference) argumentExpression));
                    } else if (argumentExpression instanceof IrBlock) {
                        IrBlock irBlock = (IrBlock) argumentExpression;
                        if (Intrinsics.areEqual(irBlock.getOrigin(), IrStatementOrigin.ADAPTED_FUNCTION_REFERENCE.INSTANCE)) {
                            Object objLast = CollectionsKt.last((List<? extends Object>) irBlock.getStatements());
                            Intrinsics.checkNotNull(objLast, "null cannot be cast to non-null type org.jetbrains.kotlin.ir.expressions.IrFunctionReference");
                            CollectionsKt.addAll(arrayList, evaluateArguments((IrFunctionReference) objLast));
                        }
                    }
                } else {
                    IrGetValueWithoutLocation irGetValueWithoutLocationTransform = parameterToArgument.getArgumentExpression().transform((IrElementTransformer<? super Object>) parameterSubstitutor, (Object) null);
                    if ((functionInlining.alwaysCreateTemporaryVariablesForArguments && !FunctionInliningKt.isInlineParameter$default(parameter, null, 1, null)) || shouldBeSubstitutedViaTemporaryVariable(parameterToArgument)) {
                        IrVariable irVariableCreateTemporaryVariable = createTemporaryVariable(parameter, irGetValueWithoutLocationTransform, parameterToArgument.getIsDefaultArg(), callee);
                        if (parameterToArgument.getIsDefaultArg()) {
                            arrayList2.add(irVariableCreateTemporaryVariable);
                        } else {
                            arrayList.add(irVariableCreateTemporaryVariable);
                        }
                        this.substituteMap.put(parameter, new IrGetValueWithoutLocation(irVariableCreateTemporaryVariable.getSymbol(), objArr4 == true ? 1 : 0, i, objArr3 == true ? 1 : 0));
                    } else {
                        Map<IrValueParameter, IrExpression> map = this.substituteMap;
                        if (irGetValueWithoutLocationTransform instanceof IrGetValue) {
                            irGetValueWithoutLocationTransform = new IrGetValueWithoutLocation(((IrGetValue) irGetValueWithoutLocationTransform).getSymbol(), objArr2 == true ? 1 : 0, i, objArr == true ? 1 : 0);
                        }
                        map.put(parameter, irGetValueWithoutLocationTransform);
                    }
                }
            }
            IrCompositeImpl irCompositeImpl = new IrCompositeImpl(-1, -1, this.context.getIrBuiltIns().getUnitType(), INLINED_FUNCTION_ARGUMENTS.INSTANCE, arrayList);
            IrCompositeImpl irCompositeImpl2 = new IrCompositeImpl(-1, -1, this.context.getIrBuiltIns().getUnitType(), INLINED_FUNCTION_DEFAULT_ARGUMENTS.INSTANCE, arrayList2);
            IrCompositeImpl[] irCompositeImplArr = new IrCompositeImpl[2];
            if (arrayList.isEmpty()) {
                irCompositeImpl = null;
            }
            irCompositeImplArr[0] = irCompositeImpl;
            irCompositeImplArr[1] = arrayList2.isEmpty() ? null : irCompositeImpl2;
            return CollectionsKt.listOfNotNull((Object[]) irCompositeImplArr);
        }

        private final boolean shouldBeSubstitutedViaTemporaryVariable(ParameterToArgument parameterToArgument) {
            return (!parameterToArgument.isImmutableVariableLoad() || parameterToArgument.getParameter().getIndex() < 0) && !org.jetbrains.kotlin.backend.common.ir.IrUtilsKt.isPure$default(parameterToArgument.getArgumentExpression(), false, false, this.context, 2, null);
        }

        private final IrVariable createTemporaryVariable(IrValueParameter parameter, IrExpression variableInitializer, boolean isDefaultArg, IrFunction callee) {
            IrDeclarationOrigin.IR_TEMPORARY_VARIABLE_FOR_INLINED_PARAMETER ir_temporary_variable_for_inlined_parameter;
            Scope scope = this.currentScope.getScope();
            int startOffset = isDefaultArg ? variableInitializer.getStartOffset() : -1;
            int endOffset = isDefaultArg ? variableInitializer.getEndOffset() : -1;
            IrType originalType = this.this$0.inlineArgumentsWithTheirOriginalTypeAndOffset ? getOriginalType(parameter) : variableInitializer.getType();
            IrElement irElement = this.currentScope.getIrElement();
            Intrinsics.checkNotNull(irElement, "null cannot be cast to non-null type org.jetbrains.kotlin.ir.declarations.IrSymbolOwner");
            IrBlockImpl irBlockImpl = new IrBlockImpl(startOffset, endOffset, originalType, new InlinerExpressionLocationHint(((IrSymbolOwner) irElement).getSymbol()));
            irBlockImpl.getStatements().add(variableInitializer);
            IrBlockImpl irBlockImpl2 = irBlockImpl;
            String strAsStringStripSpecialMarkers = callee.getSymbol().getOwner().getName().asStringStripSpecialMarkers();
            if (Intrinsics.areEqual(parameter, callee.getExtensionReceiverParameter())) {
                ir_temporary_variable_for_inlined_parameter = IrDeclarationOrigin.IR_TEMPORARY_VARIABLE_FOR_INLINED_EXTENSION_RECEIVER.INSTANCE;
            } else {
                ir_temporary_variable_for_inlined_parameter = IrDeclarationOrigin.IR_TEMPORARY_VARIABLE_FOR_INLINED_PARAMETER.INSTANCE;
            }
            IrVariable irVariableCreateTemporaryVariable = scope.createTemporaryVariable(irBlockImpl2, (108 & 2) != 0 ? null : strAsStringStripSpecialMarkers, (108 & 4) != 0 ? false : false, (108 & 8) != 0 ? IrDeclarationOrigin.IR_TEMPORARY_VARIABLE.INSTANCE : ir_temporary_variable_for_inlined_parameter, (108 & 16) == 0 ? null : null, (108 & 32) != 0 ? irBlockImpl2.getStartOffset() : 0, (108 & 64) != 0 ? irBlockImpl2.getEndOffset() : 0);
            if (this.this$0.alwaysCreateTemporaryVariablesForArguments) {
                Name nameIdentifier = Name.identifier(parameter.getName().asStringStripSpecialMarkers());
                Intrinsics.checkNotNullExpressionValue(nameIdentifier, "identifier(...)");
                irVariableCreateTemporaryVariable.setName(nameIdentifier);
            }
            return irVariableCreateTemporaryVariable;
        }
    }

    /* JADX INFO: compiled from: FunctionInlining.kt */
    @Metadata(d1 = {"\u00006\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\r\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\b\u0002\u0018\u00002\u00020\u0001B\u0019\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u0005¢\u0006\u0002\u0010\u0006J5\u0010\u001c\u001a\u0002H\u001d\"\u0004\b\u0000\u0010\u001d\"\u0004\b\u0001\u0010\u001e2\u0012\u0010\u001f\u001a\u000e\u0012\u0004\u0012\u0002H\u001d\u0012\u0004\u0012\u0002H\u001e0 2\u0006\u0010!\u001a\u0002H\u001eH\u0016¢\u0006\u0002\u0010\"J\u0016\u0010#\u001a\u00020$2\u0006\u0010\u000f\u001a\u00020\b2\u0006\u0010\u0007\u001a\u00020\bR\u0014\u0010\u0007\u001a\u00020\b8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\t\u0010\nR\u001c\u0010\u0004\u001a\u0004\u0018\u00010\u0005X\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000b\u0010\f\"\u0004\b\r\u0010\u000eR\u0014\u0010\u000f\u001a\u00020\b8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0010\u0010\nR\u001a\u0010\u0002\u001a\u00020\u0003X\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0011\u0010\u0012\"\u0004\b\u0013\u0010\u0014R$\u0010\u0017\u001a\u00020\u00162\u0006\u0010\u0015\u001a\u00020\u00168V@VX\u0096\u000e¢\u0006\f\u001a\u0004\b\u0018\u0010\u0019\"\u0004\b\u001a\u0010\u001b¨\u0006%"}, d2 = {"Lorg/jetbrains/kotlin/backend/common/lower/inline/FunctionInlining$IrGetValueWithoutLocation;", "Lorg/jetbrains/kotlin/ir/expressions/IrGetValue;", NativeSymbol.TYPE_NAME, "Lorg/jetbrains/kotlin/ir/symbols/IrValueSymbol;", "origin", "Lorg/jetbrains/kotlin/ir/expressions/IrStatementOrigin;", "(Lorg/jetbrains/kotlin/ir/symbols/IrValueSymbol;Lorg/jetbrains/kotlin/ir/expressions/IrStatementOrigin;)V", "endOffset", "", "getEndOffset", "()I", "getOrigin", "()Lorg/jetbrains/kotlin/ir/expressions/IrStatementOrigin;", "setOrigin", "(Lorg/jetbrains/kotlin/ir/expressions/IrStatementOrigin;)V", "startOffset", "getStartOffset", "getSymbol", "()Lorg/jetbrains/kotlin/ir/symbols/IrValueSymbol;", "setSymbol", "(Lorg/jetbrains/kotlin/ir/symbols/IrValueSymbol;)V", "value", "Lorg/jetbrains/kotlin/ir/types/IrType;", ModuleXmlParser.TYPE, "getType", "()Lorg/jetbrains/kotlin/ir/types/IrType;", "setType", "(Lorg/jetbrains/kotlin/ir/types/IrType;)V", "accept", "R", "D", "visitor", "Lorg/jetbrains/kotlin/ir/visitors/IrElementVisitor;", "data", "(Lorg/jetbrains/kotlin/ir/visitors/IrElementVisitor;Ljava/lang/Object;)Ljava/lang/Object;", "withLocation", "Lorg/jetbrains/kotlin/ir/expressions/impl/IrGetValueImpl;", "ir.backend.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
    private static final class IrGetValueWithoutLocation extends IrGetValue {
        private IrStatementOrigin origin;
        private IrValueSymbol symbol;

        @Override // org.jetbrains.kotlin.ir.IrElement
        public int getEndOffset() {
            return -1;
        }

        @Override // org.jetbrains.kotlin.ir.IrElement
        public int getStartOffset() {
            return -1;
        }

        public /* synthetic */ IrGetValueWithoutLocation(IrValueSymbol irValueSymbol, IrStatementOrigin irStatementOrigin, int i, DefaultConstructorMarker defaultConstructorMarker) {
            this(irValueSymbol, (i & 2) != 0 ? null : irStatementOrigin);
        }

        @Override // org.jetbrains.kotlin.ir.expressions.IrDeclarationReference
        public IrValueSymbol getSymbol() {
            return this.symbol;
        }

        @Override // org.jetbrains.kotlin.ir.expressions.IrValueAccessExpression
        public void setSymbol(IrValueSymbol irValueSymbol) {
            Intrinsics.checkNotNullParameter(irValueSymbol, "<set-?>");
            this.symbol = irValueSymbol;
        }

        @Override // org.jetbrains.kotlin.ir.expressions.IrValueAccessExpression
        public IrStatementOrigin getOrigin() {
            return this.origin;
        }

        @Override // org.jetbrains.kotlin.ir.expressions.IrValueAccessExpression
        public void setOrigin(IrStatementOrigin irStatementOrigin) {
            this.origin = irStatementOrigin;
        }

        public IrGetValueWithoutLocation(IrValueSymbol symbol, IrStatementOrigin irStatementOrigin) {
            Intrinsics.checkNotNullParameter(symbol, "symbol");
            this.symbol = symbol;
            this.origin = irStatementOrigin;
        }

        @Override // org.jetbrains.kotlin.ir.expressions.IrExpression
        public IrType getType() {
            return getSymbol().getOwner().getType();
        }

        @Override // org.jetbrains.kotlin.ir.expressions.IrExpression
        public void setType(IrType value) {
            Intrinsics.checkNotNullParameter(value, "value");
            getSymbol().getOwner().setType(value);
        }

        /* JADX WARN: Multi-variable type inference failed */
        @Override // org.jetbrains.kotlin.ir.expressions.IrGetValue, org.jetbrains.kotlin.ir.IrElement
        public <R, D> R accept(IrElementVisitor<? extends R, ? super D> visitor, D data) {
            Intrinsics.checkNotNullParameter(visitor, "visitor");
            return visitor.visitGetValue2(this, data);
        }

        public final IrGetValueImpl withLocation(int startOffset, int endOffset) {
            return new IrGetValueImpl(startOffset, endOffset, getType(), getSymbol(), getOrigin());
        }
    }
}
