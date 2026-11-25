package org.jetbrains.kotlin.backend.jvm.ir;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.TypeIntrinsics;
import org.jetbrains.kotlin.backend.jvm.JvmBackendContext;
import org.jetbrains.kotlin.descriptors.DescriptorVisibilities;
import org.jetbrains.kotlin.ir.IrElement;
import org.jetbrains.kotlin.ir.declarations.IrClass;
import org.jetbrains.kotlin.ir.declarations.IrDeclaration;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationContainer;
import org.jetbrains.kotlin.ir.declarations.IrFunction;
import org.jetbrains.kotlin.ir.declarations.IrPackageFragment;
import org.jetbrains.kotlin.ir.declarations.IrSimpleFunction;
import org.jetbrains.kotlin.ir.declarations.IrValueParameter;
import org.jetbrains.kotlin.ir.expressions.IrBlock;
import org.jetbrains.kotlin.ir.expressions.IrCall;
import org.jetbrains.kotlin.ir.expressions.IrFunctionReference;
import org.jetbrains.kotlin.ir.expressions.IrInlinedFunctionBlock;
import org.jetbrains.kotlin.ir.util.IrInlineUtilsKt;
import org.jetbrains.kotlin.ir.util.IrUtilsKt;
import org.jetbrains.kotlin.name.FqName;

/* JADX INFO: compiled from: IrInlineReferenceLocator.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000h\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010%\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\"\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\u0018\u00002\u00020\u0001:\u0001%B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0010\u0010\u0010\u001a\u0004\u0018\u00010\u00112\u0006\u0010\u0012\u001a\u00020\u000bJ\u001d\u0010\u0010\u001a\u0004\u0018\u00010\u00112\b\u0010\u0002\u001a\u0004\u0018\u00010\u000b2\u0006\u0010\u0013\u001a\u00020\rH\u0082\u0010J\u001a\u0010\u0014\u001a\u00020\u00152\u0006\u0010\u0016\u001a\u00020\u00172\b\u0010\u0018\u001a\u0004\u0018\u00010\u0019H\u0016J\u001a\u0010\u001a\u001a\u00020\u00152\u0006\u0010\u0016\u001a\u00020\u001b2\b\u0010\u0018\u001a\u0004\u0018\u00010\u0019H\u0016J(\u0010\u001c\u001a\u00020\u00152\u0006\u0010\u001d\u001a\u00020\u001e2\u0006\u0010\u001f\u001a\u00020\u00072\u0006\u0010 \u001a\u00020!2\u0006\u0010\u0012\u001a\u00020\u0019H\u0016J\u001a\u0010\"\u001a\u00020\u00152\u0006\u0010#\u001a\u00020\u000e2\b\u0010\u0018\u001a\u0004\u0018\u00010\u0019H\u0016J\f\u0010$\u001a\u00020\r*\u00020\u0007H\u0002R\u001a\u0010\u0005\u001a\u000e\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020\b0\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R \u0010\t\u001a\u0014\u0012\u0004\u0012\u00020\u0007\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u000b0\n0\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u0019\u0010\f\u001a\u00020\r*\u00020\u000e8Â\u0002X\u0082\u0004¢\u0006\u0006\u001a\u0004\b\f\u0010\u000f¨\u0006&"}, d2 = {"Lorg/jetbrains/kotlin/backend/jvm/ir/IrInlineScopeResolver;", "Lorg/jetbrains/kotlin/backend/jvm/ir/IrInlineReferenceLocator;", "context", "Lorg/jetbrains/kotlin/backend/jvm/JvmBackendContext;", "(Lorg/jetbrains/kotlin/backend/jvm/JvmBackendContext;)V", "inlineCallSites", "", "Lorg/jetbrains/kotlin/ir/declarations/IrFunction;", "Lorg/jetbrains/kotlin/backend/jvm/ir/IrInlineScopeResolver$CallSite;", "inlineFunctionCallSites", "", "Lorg/jetbrains/kotlin/ir/IrElement;", "isPrivateInline", "", "Lorg/jetbrains/kotlin/ir/declarations/IrSimpleFunction;", "(Lorg/jetbrains/kotlin/ir/declarations/IrSimpleFunction;)Z", "findContainer", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationContainer;", "scope", "approximateToPackage", "visitBlock", "", "expression", "Lorg/jetbrains/kotlin/ir/expressions/IrBlock;", "data", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclaration;", "visitCall", "Lorg/jetbrains/kotlin/ir/expressions/IrCall;", "visitInlineLambda", "argument", "Lorg/jetbrains/kotlin/ir/expressions/IrFunctionReference;", "callee", "parameter", "Lorg/jetbrains/kotlin/ir/declarations/IrValueParameter;", "visitSimpleFunction", "declaration", "isCoroutineIntrinsic", "CallSite", "backend.jvm"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class IrInlineScopeResolver extends IrInlineReferenceLocator {
    private final Map<IrFunction, CallSite> inlineCallSites;
    private final Map<IrFunction, Set<IrElement>> inlineFunctionCallSites;

    /* JADX INFO: compiled from: IrInlineReferenceLocator.kt */
    @Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0006\b\u0002\u0018\u00002\u00020\u0001B\u0017\u0012\b\u0010\u0002\u001a\u0004\u0018\u00010\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006R\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0013\u0010\u0002\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\n¨\u0006\u000b"}, d2 = {"Lorg/jetbrains/kotlin/backend/jvm/ir/IrInlineScopeResolver$CallSite;", "", "parent", "Lorg/jetbrains/kotlin/ir/IrElement;", "approximateToPackage", "", "(Lorg/jetbrains/kotlin/ir/IrElement;Z)V", "getApproximateToPackage", "()Z", "getParent", "()Lorg/jetbrains/kotlin/ir/IrElement;", "backend.jvm"}, k = 1, mv = {1, 9, 0}, xi = 48)
    private static final class CallSite {
        private final boolean approximateToPackage;
        private final IrElement parent;

        public CallSite(IrElement irElement, boolean z) {
            this.parent = irElement;
            this.approximateToPackage = z;
        }

        public final boolean getApproximateToPackage() {
            return this.approximateToPackage;
        }

        public final IrElement getParent() {
            return this.parent;
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public IrInlineScopeResolver(JvmBackendContext context) {
        super(context);
        Intrinsics.checkNotNullParameter(context, "context");
        this.inlineCallSites = new LinkedHashMap();
        this.inlineFunctionCallSites = new LinkedHashMap();
    }

    @Override // org.jetbrains.kotlin.backend.jvm.ir.IrInlineReferenceLocator, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitBlock */
    public /* bridge */ /* synthetic */ Unit visitBlock2(IrBlock irBlock, IrDeclaration irDeclaration) {
        visitBlock2(irBlock, irDeclaration);
        return Unit.INSTANCE;
    }

    @Override // org.jetbrains.kotlin.backend.jvm.ir.IrInlineReferenceLocator, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitCall */
    public /* bridge */ /* synthetic */ Unit visitCall2(IrCall irCall, IrDeclaration irDeclaration) {
        visitCall2(irCall, irDeclaration);
        return Unit.INSTANCE;
    }

    @Override // org.jetbrains.kotlin.backend.jvm.ir.IrInlineReferenceLocator, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitSimpleFunction */
    public /* bridge */ /* synthetic */ Unit visitSimpleFunction2(IrSimpleFunction irSimpleFunction, IrDeclaration irDeclaration) {
        visitSimpleFunction2(irSimpleFunction, irDeclaration);
        return Unit.INSTANCE;
    }

    @Override // org.jetbrains.kotlin.backend.jvm.ir.IrInlineReferenceLocator
    public void visitInlineLambda(IrFunctionReference argument, IrFunction callee, IrValueParameter parameter, IrDeclaration scope) {
        Intrinsics.checkNotNullParameter(argument, "argument");
        Intrinsics.checkNotNullParameter(callee, "callee");
        Intrinsics.checkNotNullParameter(parameter, "parameter");
        Intrinsics.checkNotNullParameter(scope, "scope");
        this.inlineCallSites.put(argument.getSymbol().getOwner(), new CallSite(scope, parameter.getIsCrossinline() && !isCoroutineIntrinsic(callee)));
    }

    @Override // org.jetbrains.kotlin.backend.jvm.ir.IrInlineReferenceLocator
    /* JADX INFO: renamed from: visitCall, reason: avoid collision after fix types in other method */
    public void visitCall2(IrCall expression, IrDeclaration data) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        IrSimpleFunction owner = expression.getSymbol().getOwner();
        if (owner.getIsInline() && DescriptorVisibilities.isPrivate(owner.getVisibility()) && data != null) {
            Map<IrFunction, Set<IrElement>> map = this.inlineFunctionCallSites;
            LinkedHashSet linkedHashSet = map.get(owner);
            if (linkedHashSet == null) {
                linkedHashSet = new LinkedHashSet();
                map.put(owner, linkedHashSet);
            }
            TypeIntrinsics.asMutableSet(linkedHashSet).add(data);
        }
        super.visitCall2(expression, data);
    }

    @Override // org.jetbrains.kotlin.backend.jvm.ir.IrInlineReferenceLocator
    /* JADX INFO: renamed from: visitBlock, reason: avoid collision after fix types in other method */
    public void visitBlock2(IrBlock expression, IrDeclaration data) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        if (expression instanceof IrInlinedFunctionBlock) {
            IrInlinedFunctionBlock irInlinedFunctionBlock = (IrInlinedFunctionBlock) expression;
            if (IrInlineUtilsKt.isFunctionInlining(irInlinedFunctionBlock)) {
                IrDeclaration inlineDeclaration = IrInlineUtilsKt.getInlineDeclaration(irInlinedFunctionBlock);
                if (inlineDeclaration instanceof IrSimpleFunction) {
                    IrSimpleFunction irSimpleFunction = (IrSimpleFunction) inlineDeclaration;
                    if (irSimpleFunction.getIsInline() && DescriptorVisibilities.isPrivate(irSimpleFunction.getVisibility()) && data != null) {
                        Map<IrFunction, Set<IrElement>> map = this.inlineFunctionCallSites;
                        LinkedHashSet linkedHashSet = map.get(inlineDeclaration);
                        if (linkedHashSet == null) {
                            linkedHashSet = new LinkedHashSet();
                            map.put((IrFunction) inlineDeclaration, linkedHashSet);
                        }
                        TypeIntrinsics.asMutableSet(linkedHashSet).add(data);
                    }
                }
            }
        }
        super.visitBlock2(expression, data);
    }

    private final boolean isCoroutineIntrinsic(IrFunction irFunction) {
        return (Intrinsics.areEqual(irFunction.getName().asString(), "suspendCoroutine") && Intrinsics.areEqual(IrUtilsKt.getPackageFragment((IrDeclaration) irFunction).getPackageFqName(), new FqName("kotlin.coroutines"))) || (Intrinsics.areEqual(irFunction.getName().asString(), "suspendCoroutineUninterceptedOrReturn") && Intrinsics.areEqual(IrUtilsKt.getPackageFragment((IrDeclaration) irFunction).getPackageFqName(), new FqName("kotlin.coroutines.intrinsics")));
    }

    public final IrDeclarationContainer findContainer(IrElement scope) {
        Intrinsics.checkNotNullParameter(scope, "scope");
        return findContainer(scope, false);
    }

    private final IrDeclarationContainer findContainer(IrElement context, boolean approximateToPackage) {
        CallSite callSite;
        while (true) {
            CallSite callSite2 = this.inlineCallSites.get(context);
            if (callSite2 != null) {
                context = callSite2.getParent();
                approximateToPackage = approximateToPackage || callSite2.getApproximateToPackage();
            } else {
                Object obj = null;
                if (context instanceof IrFunction) {
                    IrFunction irFunction = (IrFunction) context;
                    if (irFunction.getIsInline()) {
                        Set<IrElement> set = this.inlineFunctionCallSites.get(context);
                        if (set == null) {
                            return null;
                        }
                        this.inlineCallSites.put((IrFunction) context, new CallSite(null, false));
                        if (set.isEmpty()) {
                            callSite = new CallSite(irFunction.getParent(), false);
                        } else if (set.size() == 1) {
                            callSite = new CallSite((IrElement) CollectionsKt.single(set), false);
                        } else {
                            Set<IrElement> set2 = set;
                            ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(set2, 10));
                            Iterator<T> it = set2.iterator();
                            while (it.hasNext()) {
                                IrDeclarationContainer irDeclarationContainerFindContainer = findContainer((IrElement) it.next(), false);
                                if (irDeclarationContainerFindContainer == null) {
                                    return null;
                                }
                                arrayList.add(irDeclarationContainerFindContainer);
                            }
                            ArrayList arrayList2 = arrayList;
                            Object objFirst = CollectionsKt.first((List<? extends Object>) arrayList2);
                            IrDeclarationContainer irDeclarationContainer = (IrDeclarationContainer) objFirst;
                            ArrayList arrayList3 = arrayList2;
                            if (!(arrayList3 instanceof Collection) || !arrayList3.isEmpty()) {
                                Iterator it2 = arrayList3.iterator();
                                do {
                                    if (!it2.hasNext()) {
                                        obj = objFirst;
                                        break;
                                    }
                                } while (irDeclarationContainer == ((IrDeclarationContainer) it2.next()));
                            } else {
                                obj = objFirst;
                                break;
                            }
                            IrDeclarationContainer irDeclarationContainer2 = (IrDeclarationContainer) obj;
                            callSite = new CallSite(irDeclarationContainer2 != null ? irDeclarationContainer2 : irFunction.getParent(), irDeclarationContainer2 == null);
                        }
                        this.inlineCallSites.put((IrFunction) context, callSite);
                        context = callSite.getParent();
                        if (approximateToPackage || callSite.getApproximateToPackage()) {
                        }
                    }
                }
                if ((context instanceof IrClass) && !approximateToPackage) {
                    return (IrDeclarationContainer) context;
                }
                if (!(context instanceof IrDeclaration)) {
                    return (IrDeclarationContainer) (context instanceof IrPackageFragment ? (IrPackageFragment) context : null);
                }
                context = ((IrDeclaration) context).getParent();
            }
        }
    }

    @Override // org.jetbrains.kotlin.backend.jvm.ir.IrInlineReferenceLocator
    /* JADX INFO: renamed from: visitSimpleFunction, reason: avoid collision after fix types in other method */
    public void visitSimpleFunction2(IrSimpleFunction declaration, IrDeclaration data) {
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        if (declaration.getIsInline() && DescriptorVisibilities.isPrivate(declaration.getVisibility())) {
            this.inlineFunctionCallSites.putIfAbsent(declaration, new LinkedHashSet());
        }
        super.visitSimpleFunction2(declaration, data);
    }
}
