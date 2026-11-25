package org.jetbrains.kotlin.ir.backend.js.dce;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.sequences.Sequence;
import org.jetbrains.kotlin.ir.backend.js.JsIrBackendContext;
import org.jetbrains.kotlin.ir.backend.js.JsStatementOrigins;
import org.jetbrains.kotlin.ir.backend.js.export.ExportModelGeneratorKt;
import org.jetbrains.kotlin.ir.backend.js.lower.ES6ConstructorLoweringKt;
import org.jetbrains.kotlin.ir.backend.js.lower.MoveBodilessDeclarationsToSeparatePlaceKt;
import org.jetbrains.kotlin.ir.backend.js.utils.AnnotationUtilsKt;
import org.jetbrains.kotlin.ir.backend.js.utils.IrJsUtilsKt;
import org.jetbrains.kotlin.ir.backend.js.utils.JsInlineClassesUtils;
import org.jetbrains.kotlin.ir.backend.js.utils.MiscKt;
import org.jetbrains.kotlin.ir.declarations.IrClass;
import org.jetbrains.kotlin.ir.declarations.IrConstructor;
import org.jetbrains.kotlin.ir.declarations.IrDeclaration;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationWithName;
import org.jetbrains.kotlin.ir.declarations.IrField;
import org.jetbrains.kotlin.ir.declarations.IrFunction;
import org.jetbrains.kotlin.ir.declarations.IrProperty;
import org.jetbrains.kotlin.ir.declarations.IrSimpleFunction;
import org.jetbrains.kotlin.ir.declarations.IrSymbolOwner;
import org.jetbrains.kotlin.ir.declarations.IrValueParameter;
import org.jetbrains.kotlin.ir.expressions.IrCall;
import org.jetbrains.kotlin.ir.expressions.IrConstructorCall;
import org.jetbrains.kotlin.ir.expressions.IrExpression;
import org.jetbrains.kotlin.ir.expressions.IrGetValue;
import org.jetbrains.kotlin.ir.symbols.IrClassSymbol;
import org.jetbrains.kotlin.ir.symbols.IrClassifierSymbol;
import org.jetbrains.kotlin.ir.symbols.IrPropertySymbol;
import org.jetbrains.kotlin.ir.symbols.IrSimpleFunctionSymbol;
import org.jetbrains.kotlin.ir.types.IrType;
import org.jetbrains.kotlin.ir.types.IrTypesKt;
import org.jetbrains.kotlin.ir.util.AdditionalIrUtilsKt;
import org.jetbrains.kotlin.ir.util.IrTypeUtilsKt;
import org.jetbrains.kotlin.ir.util.IrUtilsKt;
import org.jetbrains.kotlin.js.config.JSConfigurationKeys;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.serialization.js.ModuleKind;

/* JADX INFO: compiled from: JsUsefulDeclarationProcessor.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000P\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0000\u0018\u00002\u00020\u0001B\u001d\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0005¢\u0006\u0002\u0010\u0007J\u0010\u0010\u0017\u001a\u00020\u00182\u0006\u0010\u0019\u001a\u00020\u0016H\u0014J\b\u0010\u001a\u001a\u00020\u0018H\u0014J\u0010\u0010\u001b\u001a\u00020\u00052\u0006\u0010\u001c\u001a\u00020\u000fH\u0014J\u0010\u0010\u001d\u001a\u00020\u00182\u0006\u0010\u0019\u001a\u00020\u0016H\u0014J\u0010\u0010\u001e\u001a\u00020\u00182\u0006\u0010\u001c\u001a\u00020\u000fH\u0014J\u0010\u0010\u001f\u001a\u00020\u00182\u0006\u0010 \u001a\u00020!H\u0014J\u0010\u0010\"\u001a\u00020\u00182\u0006\u0010\u0019\u001a\u00020\u0016H\u0014J\f\u0010#\u001a\u00020\u0005*\u00020\u0016H\u0002J\f\u0010$\u001a\u00020\u0005*\u00020\u0016H\u0002J\u0016\u0010%\u001a\u00020\u0005*\u00020&2\b\u0010'\u001a\u0004\u0018\u00010\u000fH\u0002R\u0018\u0010\b\u001a\u00060\tR\u00020\u0001X\u0094\u0004¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000bR\u0014\u0010\u0002\u001a\u00020\u0003X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR\u000e\u0010\u000e\u001a\u00020\u000fX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0010\u001a\u00020\u000fX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0011\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u001e\u0010\u0012\u001a\u0012\u0012\u0004\u0012\u00020\u000f0\u0013j\b\u0012\u0004\u0012\u00020\u000f`\u0014X\u0082\u0004¢\u0006\u0002\n\u0000R\u001e\u0010\u0015\u001a\u0012\u0012\u0004\u0012\u00020\u00160\u0013j\b\u0012\u0004\u0012\u00020\u0016`\u0014X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006("}, d2 = {"Lorg/jetbrains/kotlin/ir/backend/js/dce/JsUsefulDeclarationProcessor;", "Lorg/jetbrains/kotlin/ir/backend/js/dce/UsefulDeclarationProcessor;", "context", "Lorg/jetbrains/kotlin/ir/backend/js/JsIrBackendContext;", "printReachabilityInfo", "", "removeUnusedAssociatedObjects", "(Lorg/jetbrains/kotlin/ir/backend/js/JsIrBackendContext;ZZ)V", "bodyVisitor", "Lorg/jetbrains/kotlin/ir/backend/js/dce/UsefulDeclarationProcessor$BodyVisitorBase;", "getBodyVisitor", "()Lorg/jetbrains/kotlin/ir/backend/js/dce/UsefulDeclarationProcessor$BodyVisitorBase;", "getContext", "()Lorg/jetbrains/kotlin/ir/backend/js/JsIrBackendContext;", "equalsMethod", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclaration;", "hashCodeMethod", "isEsModules", "referencedJsClasses", "Ljava/util/HashSet;", "Lkotlin/collections/HashSet;", "referencedJsClassesFromExpressions", "Lorg/jetbrains/kotlin/ir/declarations/IrClass;", "addConstructedClass", "", "irClass", "handleAssociatedObjects", "isExported", "declaration", "processClass", "processConstructedClassDeclaration", "processSimpleFunction", "irFunction", "Lorg/jetbrains/kotlin/ir/declarations/IrSimpleFunction;", "processSuperTypes", "containsInterfaceDefaultImplementation", "containsMetadata", "usePrototype", "Lorg/jetbrains/kotlin/ir/expressions/IrCall;", "container", "backend.js"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class JsUsefulDeclarationProcessor extends UsefulDeclarationProcessor {
    private final UsefulDeclarationProcessor.BodyVisitorBase bodyVisitor;
    private final JsIrBackendContext context;
    private final IrDeclaration equalsMethod;
    private final IrDeclaration hashCodeMethod;
    private final boolean isEsModules;
    private final HashSet<IrDeclaration> referencedJsClasses;
    private final HashSet<IrClass> referencedJsClassesFromExpressions;

    @Override // org.jetbrains.kotlin.ir.backend.js.dce.UsefulDeclarationProcessor
    public JsIrBackendContext getContext() {
        return this.context;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public JsUsefulDeclarationProcessor(JsIrBackendContext context, boolean z, boolean z2) {
        super(z, z2, null, 4, null);
        Intrinsics.checkNotNullParameter(context, "context");
        this.context = context;
        this.equalsMethod = getMethodOfAny(Namer.EQUALS_METHOD_NAME);
        this.hashCodeMethod = getMethodOfAny("hashCode");
        this.isEsModules = getContext().getConfiguration().get(JSConfigurationKeys.MODULE_KIND) == ModuleKind.ES;
        this.bodyVisitor = new UsefulDeclarationProcessor.BodyVisitorBase() { // from class: org.jetbrains.kotlin.ir.backend.js.dce.JsUsefulDeclarationProcessor$bodyVisitor$1
            {
                super();
            }

            @Override // org.jetbrains.kotlin.ir.backend.js.dce.UsefulDeclarationProcessor.BodyVisitorBase, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitCall */
            public /* bridge */ /* synthetic */ Unit visitCall2(IrCall irCall, IrDeclaration irDeclaration) {
                visitCall2(irCall, irDeclaration);
                return Unit.INSTANCE;
            }

            @Override // org.jetbrains.kotlin.ir.backend.js.dce.UsefulDeclarationProcessor.BodyVisitorBase
            /* JADX INFO: renamed from: visitCall, reason: avoid collision after fix types in other method */
            public void visitCall2(IrCall expression, IrDeclaration data) {
                IrType type;
                IrType type2;
                IrClassSymbol classOrNull;
                IrClass owner;
                IrClassSymbol anyClass;
                Intrinsics.checkNotNullParameter(expression, "expression");
                Intrinsics.checkNotNullParameter(data, "data");
                super.visitCall(expression, data);
                if (this.this$0.usePrototype(expression, data)) {
                    JsUsefulDeclarationProcessor jsUsefulDeclarationProcessor = this.this$0;
                    UsefulDeclarationProcessor.enqueue$default(jsUsefulDeclarationProcessor, jsUsefulDeclarationProcessor.getContext().getIntrinsics().getJsPrototypeOfSymbol().getOwner(), expression.getSymbol().getOwner(), "access to super type", false, 4, null);
                }
                IrSimpleFunctionSymbol symbol = expression.getSymbol();
                Object classOrNull2 = null;
                boolean z3 = false;
                if (Intrinsics.areEqual(symbol, this.this$0.getContext().getIntrinsics().getJsBoxIntrinsic())) {
                    JsInlineClassesUtils inlineClassesUtils = this.this$0.getContext().getInlineClassesUtils();
                    IrType typeArgument = expression.getTypeArgument(0);
                    Intrinsics.checkNotNull(typeArgument);
                    IrClass inlinedClass = inlineClassesUtils.getInlinedClass(typeArgument);
                    Intrinsics.checkNotNull(inlinedClass);
                    List<IrDeclaration> declarations = inlinedClass.getDeclarations();
                    ArrayList arrayList = new ArrayList();
                    for (Object obj : declarations) {
                        if (obj instanceof IrConstructor) {
                            arrayList.add(obj);
                        }
                    }
                    for (Object obj2 : arrayList) {
                        if (((IrConstructor) obj2).getIsPrimary()) {
                            if (z3) {
                                throw new IllegalArgumentException("Collection contains more than one matching element.");
                            }
                            classOrNull2 = obj2;
                            z3 = true;
                        }
                    }
                    if (z3) {
                        UsefulDeclarationProcessor.enqueue$default(this.this$0, (IrConstructor) classOrNull2, data, "intrinsic: jsBoxIntrinsic", false, 4, null);
                        return;
                    }
                    throw new NoSuchElementException("Collection contains no element matching the predicate.");
                }
                if (Intrinsics.areEqual(symbol, this.this$0.getContext().getIntrinsics().getJsClass())) {
                    IrType typeArgument2 = expression.getTypeArgument(0);
                    Intrinsics.checkNotNull(typeArgument2);
                    IrSymbolOwner owner2 = IrTypesKt.getClassifierOrFail(typeArgument2).getOwner();
                    Intrinsics.checkNotNull(owner2, "null cannot be cast to non-null type org.jetbrains.kotlin.ir.declarations.IrDeclaration");
                    IrDeclaration irDeclaration = (IrDeclaration) owner2;
                    UsefulDeclarationProcessor.enqueue$default(this.this$0, irDeclaration, data, "intrinsic: jsClass", false, 4, null);
                    this.this$0.referencedJsClasses.add(irDeclaration);
                    if (Intrinsics.areEqual(expression.getOrigin(), JsStatementOrigins.CLASS_REFERENCE.INSTANCE)) {
                        Sequence<IrConstructor> constructors = IrUtilsKt.getConstructors((IrClass) irDeclaration);
                        JsUsefulDeclarationProcessor jsUsefulDeclarationProcessor2 = this.this$0;
                        Iterator<IrConstructor> it = constructors.iterator();
                        while (it.hasNext()) {
                            UsefulDeclarationProcessor.enqueue$default(jsUsefulDeclarationProcessor2, it.next(), data, "intrinsic: jsClass (constructor)", false, 4, null);
                        }
                        return;
                    }
                    return;
                }
                if (Intrinsics.areEqual(symbol, this.this$0.getContext().getReflectionSymbols().getGetKClassFromExpression())) {
                    IrType typeArgument3 = expression.getTypeArgument(0);
                    if (typeArgument3 == null || (anyClass = IrTypesKt.getClassOrNull(typeArgument3)) == null) {
                        anyClass = this.this$0.getContext().getIrBuiltIns().getAnyClass();
                    }
                    this.this$0.referencedJsClassesFromExpressions.add(anyClass.getOwner());
                    return;
                }
                if (Intrinsics.areEqual(symbol, this.this$0.getContext().getReflectionSymbols().getGetKClass())) {
                    IrType typeArgument4 = expression.getTypeArgument(0);
                    if (typeArgument4 == null || (classOrNull = IrTypesKt.getClassOrNull(typeArgument4)) == null || (owner = classOrNull.getOwner()) == null) {
                        return;
                    }
                    this.this$0.addConstructedClass(owner);
                    return;
                }
                if (Intrinsics.areEqual(symbol, this.this$0.getContext().getIntrinsics().getJsObjectCreateSymbol())) {
                    IrType typeArgument5 = expression.getTypeArgument(0);
                    Intrinsics.checkNotNull(typeArgument5);
                    IrSymbolOwner owner3 = IrTypesKt.getClassifierOrFail(typeArgument5).getOwner();
                    Intrinsics.checkNotNull(owner3, "null cannot be cast to non-null type org.jetbrains.kotlin.ir.declarations.IrClass");
                    IrClass irClass = (IrClass) owner3;
                    UsefulDeclarationProcessor.enqueue$default(this.this$0, irClass, data, "intrinsic: jsObjectCreateSymbol", false, 4, null);
                    this.this$0.addConstructedClass(irClass);
                    return;
                }
                if (Intrinsics.areEqual(symbol, this.this$0.getContext().getIntrinsics().getJsCreateThisSymbol())) {
                    IrExpression valueArgument = expression.getValueArgument(0);
                    if (valueArgument instanceof IrCall) {
                        type2 = ((IrCall) valueArgument).getTypeArgument(0);
                        Intrinsics.checkNotNull(type2);
                    } else {
                        if (!(valueArgument instanceof IrGetValue)) {
                            throw new IllegalStateException("Unexpected first argument of createThis function call".toString());
                        }
                        type2 = ((IrGetValue) valueArgument).getType();
                    }
                    IrSymbolOwner owner4 = IrTypesKt.getClassifierOrFail(type2).getOwner();
                    Intrinsics.checkNotNull(owner4, "null cannot be cast to non-null type org.jetbrains.kotlin.ir.declarations.IrClass");
                    IrClass irClass2 = (IrClass) owner4;
                    UsefulDeclarationProcessor.enqueue$default(this.this$0, irClass2, data, "intrinsic: jsCreateThis", false, 4, null);
                    this.this$0.addConstructedClass(irClass2);
                    return;
                }
                if (Intrinsics.areEqual(symbol, this.this$0.getContext().getIntrinsics().getJsEquals())) {
                    JsUsefulDeclarationProcessor jsUsefulDeclarationProcessor3 = this.this$0;
                    UsefulDeclarationProcessor.enqueue$default(jsUsefulDeclarationProcessor3, jsUsefulDeclarationProcessor3.equalsMethod, data, "intrinsic: jsEquals", false, 4, null);
                    return;
                }
                if (Intrinsics.areEqual(symbol, this.this$0.getContext().getIntrinsics().getJsToString())) {
                    JsUsefulDeclarationProcessor jsUsefulDeclarationProcessor4 = this.this$0;
                    UsefulDeclarationProcessor.enqueue$default(jsUsefulDeclarationProcessor4, jsUsefulDeclarationProcessor4.getToStringMethod(), data, "intrinsic: jsToString", false, 4, null);
                    return;
                }
                if (Intrinsics.areEqual(symbol, this.this$0.getContext().getIntrinsics().getJsHashCode())) {
                    JsUsefulDeclarationProcessor jsUsefulDeclarationProcessor5 = this.this$0;
                    UsefulDeclarationProcessor.enqueue$default(jsUsefulDeclarationProcessor5, jsUsefulDeclarationProcessor5.hashCodeMethod, data, "intrinsic: jsHashCode", false, 4, null);
                    return;
                }
                if (Intrinsics.areEqual(symbol, this.this$0.getContext().getIntrinsics().getJsPlus())) {
                    IrExpression valueArgument2 = expression.getValueArgument(0);
                    if (valueArgument2 != null && (type = valueArgument2.getType()) != null) {
                        classOrNull2 = IrTypesKt.getClassOrNull(type);
                    }
                    if (Intrinsics.areEqual(classOrNull2, this.this$0.getContext().getIrBuiltIns().getStringClass())) {
                        JsUsefulDeclarationProcessor jsUsefulDeclarationProcessor6 = this.this$0;
                        UsefulDeclarationProcessor.enqueue$default(jsUsefulDeclarationProcessor6, jsUsefulDeclarationProcessor6.getToStringMethod(), data, "intrinsic: jsPlus", false, 4, null);
                        return;
                    }
                    return;
                }
                if (Intrinsics.areEqual(symbol, this.this$0.getContext().getIntrinsics().getJsInvokeSuspendSuperType()) ? true : Intrinsics.areEqual(symbol, this.this$0.getContext().getIntrinsics().getJsInvokeSuspendSuperTypeWithReceiver()) ? true : Intrinsics.areEqual(symbol, this.this$0.getContext().getIntrinsics().getJsInvokeSuspendSuperTypeWithReceiverAndParam())) {
                    UsefulDeclarationProcessor.enqueue$default(this.this$0, MiscKt.invokeFunForLambda(expression), data, "intrinsic: suspendSuperType", false, 4, null);
                }
            }
        };
        this.referencedJsClasses = new HashSet<>();
        this.referencedJsClassesFromExpressions = new HashSet<>();
    }

    @Override // org.jetbrains.kotlin.ir.backend.js.dce.UsefulDeclarationProcessor
    protected UsefulDeclarationProcessor.BodyVisitorBase getBodyVisitor() {
        return this.bodyVisitor;
    }

    @Override // org.jetbrains.kotlin.ir.backend.js.dce.UsefulDeclarationProcessor
    protected void addConstructedClass(IrClass irClass) {
        IrFunction irFunctionFindDefaultConstructorFor;
        Intrinsics.checkNotNullParameter(irClass, "irClass");
        super.addConstructedClass(irClass);
        if (!IrUtilsKt.isClass(irClass) || (irFunctionFindDefaultConstructorFor = IrJsUtilsKt.findDefaultConstructorFor(getContext(), irClass)) == null) {
            return;
        }
        UsefulDeclarationProcessor.enqueue$default(this, irFunctionFindDefaultConstructorFor, irClass, "intrinsic: KClass<*>.createInstance", false, 4, null);
    }

    @Override // org.jetbrains.kotlin.ir.backend.js.dce.UsefulDeclarationProcessor
    protected void processSuperTypes(IrClass irClass) {
        IrClass owner;
        Intrinsics.checkNotNullParameter(irClass, "irClass");
        for (IrType irType : irClass.getSuperTypes()) {
            IrClassSymbol classOrNull = IrTypesKt.getClassOrNull(irType);
            boolean zShouldKeep = classOrNull != null ? getContext().getKeeper().shouldKeep(classOrNull.getOwner()) : false;
            if (!IrTypeUtilsKt.isInterface(irType) || zShouldKeep) {
                IrClassifierSymbol classifierOrNull = IrTypesKt.getClassifierOrNull(irType);
                IrClassSymbol irClassSymbol = classifierOrNull instanceof IrClassSymbol ? (IrClassSymbol) classifierOrNull : null;
                if (irClassSymbol != null && (owner = irClassSymbol.getOwner()) != null) {
                    UsefulDeclarationProcessor.enqueue$default(this, owner, irClass, "superTypes", false, 4, null);
                }
            }
        }
    }

    @Override // org.jetbrains.kotlin.ir.backend.js.dce.UsefulDeclarationProcessor
    protected void processClass(IrClass irClass) {
        Intrinsics.checkNotNullParameter(irClass, "irClass");
        super.processClass(irClass);
        IrClass irClass2 = irClass;
        if (getContext().getKeeper().shouldKeep(irClass2)) {
            List<IrDeclaration> declarations = irClass.getDeclarations();
            ArrayList arrayList = new ArrayList();
            for (Object obj : declarations) {
                if (getContext().getKeeper().shouldKeep((IrDeclaration) obj)) {
                    arrayList.add(obj);
                }
            }
            Iterator it = arrayList.iterator();
            while (it.hasNext()) {
                UsefulDeclarationProcessor.enqueue$default(this, (IrDeclaration) it.next(), irClass2, "kept declaration", false, 4, null);
            }
        }
        if (containsMetadata(irClass)) {
            if (IrUtilsKt.isObject(irClass)) {
                UsefulDeclarationProcessor.enqueue$default(this, getContext().getIntrinsics().getMetadataObjectConstructorSymbol().getOwner(), irClass2, "object metadata", false, 4, null);
            } else if (IrUtilsKt.isInterface(irClass)) {
                JsUsefulDeclarationProcessor jsUsefulDeclarationProcessor = this;
                UsefulDeclarationProcessor.enqueue$default(jsUsefulDeclarationProcessor, getContext().getIntrinsics().getImplementSymbol().getOwner(), irClass2, "interface metadata", false, 4, null);
                UsefulDeclarationProcessor.enqueue$default(jsUsefulDeclarationProcessor, getContext().getIntrinsics().getMetadataInterfaceConstructorSymbol().getOwner(), irClass2, "interface metadata", false, 4, null);
            } else {
                UsefulDeclarationProcessor.enqueue$default(this, getContext().getIntrinsics().getMetadataClassConstructorSymbol().getOwner(), irClass2, "class metadata", false, 4, null);
            }
            JsUsefulDeclarationProcessor jsUsefulDeclarationProcessor2 = this;
            UsefulDeclarationProcessor.enqueue$default(jsUsefulDeclarationProcessor2, getContext().getIntrinsics().getSetMetadataForSymbol().getOwner(), irClass2, "metadata", false, 4, null);
            if (containsInterfaceDefaultImplementation(irClass)) {
                UsefulDeclarationProcessor.enqueue$default(jsUsefulDeclarationProcessor2, getContext().getIntrinsics().getJsPrototypeOfSymbol().getOwner(), irClass2, "interface default implementation", false, 4, null);
            }
            if (irClass.getIsInner() || IrUtilsKt.isObject(irClass)) {
                UsefulDeclarationProcessor.enqueue$default(jsUsefulDeclarationProcessor2, getContext().getIntrinsics().getJsDefinePropertySymbol().getOwner(), irClass2, "object lazy initialization", false, 4, null);
            }
            if (getContext().getEs6mode()) {
                return;
            }
            if (!IrUtilsKt.isInterface(irClass)) {
                UsefulDeclarationProcessor.enqueue$default(jsUsefulDeclarationProcessor2, getContext().getIntrinsics().getJsPrototypeOfSymbol().getOwner(), irClass2, "class prototype access", false, 4, null);
            }
            List<IrType> superTypes = irClass.getSuperTypes();
            if ((superTypes instanceof Collection) && superTypes.isEmpty()) {
                return;
            }
            Iterator<T> it2 = superTypes.iterator();
            while (it2.hasNext()) {
                if (!IrTypeUtilsKt.isInterface((IrType) it2.next())) {
                    UsefulDeclarationProcessor.enqueue$default(jsUsefulDeclarationProcessor2, getContext().getIntrinsics().getJsObjectCreateSymbol().getOwner(), irClass2, "class inheritance code", false, 4, null);
                    return;
                }
            }
        }
    }

    @Override // org.jetbrains.kotlin.ir.backend.js.dce.UsefulDeclarationProcessor
    protected void processSimpleFunction(IrSimpleFunction irFunction) {
        IrProperty owner;
        IrClass parentClassOrNull;
        IrType type;
        IrClassSymbol classOrNull;
        Intrinsics.checkNotNullParameter(irFunction, "irFunction");
        super.processSimpleFunction(irFunction);
        IrSimpleFunction irSimpleFunction = irFunction;
        if (ES6ConstructorLoweringKt.isEs6ConstructorReplacement(irSimpleFunction)) {
            IrValueParameter dispatchReceiverParameter = irFunction.getDispatchReceiverParameter();
            IrClass owner2 = (dispatchReceiverParameter == null || (type = dispatchReceiverParameter.getType()) == null || (classOrNull = IrTypesKt.getClassOrNull(type)) == null) ? null : classOrNull.getOwner();
            Intrinsics.checkNotNull(owner2);
            addConstructedClass(owner2);
        }
        if (AdditionalIrUtilsKt.isReal(irFunction) && irFunction.getBody() != null && (parentClassOrNull = IrUtilsKt.getParentClassOrNull(irSimpleFunction)) != null) {
            IrClass irClass = IrUtilsKt.isInterface(parentClassOrNull) ? parentClassOrNull : null;
            if (irClass != null) {
                UsefulDeclarationProcessor.enqueue$default(this, irClass, irSimpleFunction, "interface default method is used", false, 4, null);
            }
        }
        IrPropertySymbol correspondingPropertySymbol = irFunction.getCorrespondingPropertySymbol();
        if (correspondingPropertySymbol == null || (owner = correspondingPropertySymbol.getOwner()) == null) {
            return;
        }
        if (ExportModelGeneratorKt.isExported(owner, getContext()) || AnnotationUtilsKt.getJsName(owner) != null || isOverriddenExternal(owner)) {
            JsUsefulDeclarationProcessor jsUsefulDeclarationProcessor = this;
            UsefulDeclarationProcessor.enqueue$default(jsUsefulDeclarationProcessor, getContext().getIntrinsics().getJsPrototypeOfSymbol().getOwner(), irSimpleFunction, "property for export", false, 4, null);
            UsefulDeclarationProcessor.enqueue$default(jsUsefulDeclarationProcessor, getContext().getIntrinsics().getJsDefinePropertySymbol().getOwner(), irSimpleFunction, "property for export", false, 4, null);
        }
    }

    private final boolean containsMetadata(IrClass irClass) {
        return (irClass.getIsExternal() || irClass.getIsExpect() || MoveBodilessDeclarationsToSeparatePlaceKt.isBuiltInClass(irClass)) ? false : true;
    }

    /* JADX WARN: Code duplicated, block: B:21:0x005c A[DONT_INVERT] */
    /* JADX WARN: Code duplicated, block: B:22:0x005e  */
    /* JADX WARN: Code duplicated, block: B:30:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:31:? A[RETURN, SYNTHETIC] */
    @Override // org.jetbrains.kotlin.ir.backend.js.dce.UsefulDeclarationProcessor
    protected void processConstructedClassDeclaration(IrDeclaration declaration) {
        IrPropertySymbol correspondingPropertySymbol;
        IrProperty owner;
        IrProperty owner2;
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        if (isReachable(declaration)) {
            return;
        }
        super.processConstructedClassDeclaration(declaration);
        boolean z = declaration instanceof IrSimpleFunction;
        if (z && Intrinsics.areEqual(AnnotationUtilsKt.getJsNameOrKotlinName((IrDeclarationWithName) declaration).asString(), "valueOf")) {
            UsefulDeclarationProcessor.enqueue$default(this, declaration, declaration, "valueOf", false, 4, null);
        }
        if (AnnotationUtilsKt.getJsName(declaration) == null) {
            String jsName = null;
            if (declaration instanceof IrField) {
                IrPropertySymbol correspondingPropertySymbol2 = ((IrField) declaration).getCorrespondingPropertySymbol();
                if (((correspondingPropertySymbol2 == null || (owner2 = correspondingPropertySymbol2.getOwner()) == null) ? null : AnnotationUtilsKt.getJsName(owner2)) == null) {
                    if (z) {
                        return;
                    }
                    correspondingPropertySymbol = ((IrSimpleFunction) declaration).getCorrespondingPropertySymbol();
                    if (correspondingPropertySymbol != null) {
                        jsName = AnnotationUtilsKt.getJsName(owner);
                    }
                    if (jsName == null) {
                        return;
                    }
                }
            } else {
                if (z) {
                    return;
                }
                correspondingPropertySymbol = ((IrSimpleFunction) declaration).getCorrespondingPropertySymbol();
                if (correspondingPropertySymbol != null && (owner = correspondingPropertySymbol.getOwner()) != null) {
                    jsName = AnnotationUtilsKt.getJsName(owner);
                }
                if (jsName == null) {
                    return;
                }
            }
        }
        UsefulDeclarationProcessor.enqueue$default(this, declaration, declaration, "annotated by @JsName", false, 4, null);
    }

    @Override // org.jetbrains.kotlin.ir.backend.js.dce.UsefulDeclarationProcessor
    protected void handleAssociatedObjects() {
        IrSimpleFunction irSimpleFunction;
        CollectionsKt.addAll(this.referencedJsClassesFromExpressions, JsUsefulDeclarationProcessorKt.filterDescendantsOf(getConstructedClasses(), this.referencedJsClassesFromExpressions));
        for (IrClass irClass : getClassesWithObjectAssociations()) {
            if (!getRemoveUnusedAssociatedObjects() || this.referencedJsClasses.contains(irClass) || this.referencedJsClassesFromExpressions.contains(irClass)) {
                for (IrConstructorCall irConstructorCall : irClass.getAnnotations()) {
                    IrClass constructedClass = AdditionalIrUtilsKt.getConstructedClass(irConstructorCall.getSymbol().getOwner());
                    if (!getRemoveUnusedAssociatedObjects() || this.referencedJsClasses.contains(constructedClass)) {
                        IrClass irClassAssociatedObject = AnnotationUtilsKt.associatedObject(irConstructorCall);
                        if (irClassAssociatedObject != null && (irSimpleFunction = getContext().getMapping().getObjectToGetInstanceFunction().get(irClassAssociatedObject)) != null) {
                            Intrinsics.checkNotNull(irClass);
                            UsefulDeclarationProcessor.enqueue$default(this, irSimpleFunction, irClass, "associated object factory", false, 4, null);
                        }
                    }
                }
            }
        }
    }

    @Override // org.jetbrains.kotlin.ir.backend.js.dce.UsefulDeclarationProcessor
    protected boolean isExported(IrDeclaration declaration) {
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        return ExportModelGeneratorKt.isExported(declaration, getContext());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final boolean usePrototype(IrCall irCall, IrDeclaration irDeclaration) {
        if (irCall.getSuperQualifierSymbol() == null) {
            return false;
        }
        IrSimpleFunction irSimpleFunction = irDeclaration instanceof IrSimpleFunction ? (IrSimpleFunction) irDeclaration : null;
        IrClass parentClassOrNull = irSimpleFunction != null ? IrUtilsKt.getParentClassOrNull(irSimpleFunction) : null;
        if (getContext().getEs6mode()) {
            if ((irSimpleFunction != null ? irSimpleFunction.getDispatchReceiverParameter() : null) != null) {
                if (parentClassOrNull == null) {
                    return false;
                }
                if (!parentClassOrNull.getIsInner() && !AdditionalIrUtilsKt.isLocal(parentClassOrNull)) {
                    return false;
                }
            }
        }
        return true;
    }

    private final boolean containsInterfaceDefaultImplementation(IrClass irClass) {
        IrClass owner;
        List<IrType> superTypes = irClass.getSuperTypes();
        if (!(superTypes instanceof Collection) || !superTypes.isEmpty()) {
            Iterator<T> it = superTypes.iterator();
            while (it.hasNext()) {
                IrClassSymbol classOrNull = IrTypesKt.getClassOrNull((IrType) it.next());
                if (classOrNull != null && (owner = classOrNull.getOwner()) != null && owner.getIsExternal()) {
                    return true;
                }
            }
        }
        if (ExportModelGeneratorKt.isExported(irClass, getContext())) {
            return true;
        }
        if (IrUtilsKt.isInterface(irClass)) {
            List<IrDeclaration> declarations = irClass.getDeclarations();
            if (!(declarations instanceof Collection) || !declarations.isEmpty()) {
                for (IrDeclaration irDeclaration : declarations) {
                    if ((irDeclaration instanceof IrFunction) && ((IrFunction) irDeclaration).getBody() != null) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
