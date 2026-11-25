package org.jetbrains.kotlin.ir.backend.js;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.LazyThreadSafetyMode;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.backend.common.CollectionUtilKt;
import org.jetbrains.kotlin.cli.common.arguments.K2JsArgumentConstants;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.descriptors.ClassifierDescriptor;
import org.jetbrains.kotlin.descriptors.ModuleDescriptor;
import org.jetbrains.kotlin.descriptors.PackageViewDescriptor;
import org.jetbrains.kotlin.descriptors.PropertyDescriptor;
import org.jetbrains.kotlin.descriptors.PropertyGetterDescriptor;
import org.jetbrains.kotlin.incremental.IncrementalJvmCacheKt;
import org.jetbrains.kotlin.incremental.components.NoLookupLocation;
import org.jetbrains.kotlin.ir.declarations.IrDeclaration;
import org.jetbrains.kotlin.ir.declarations.IrProperty;
import org.jetbrains.kotlin.ir.declarations.IrSimpleFunction;
import org.jetbrains.kotlin.ir.symbols.IrClassSymbol;
import org.jetbrains.kotlin.ir.symbols.IrSimpleFunctionSymbol;
import org.jetbrains.kotlin.ir.util.AdditionalIrUtilsKt;
import org.jetbrains.kotlin.ir.util.DescriptorSymbolTableExtension;
import org.jetbrains.kotlin.ir.util.SymbolTable;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.name.Name;

/* JADX INFO: compiled from: JsCommonBackendContext.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000F\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u001a\n\u0002\u0018\u0002\n\u0002\b\n\u0018\u0000 >2\u00020\u0001:\u0001>B\u001d\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007¢\u0006\u0002\u0010\bR\u0011\u0010\u0006\u001a\u00020\u0007¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u0011\u0010\u000b\u001a\u00020\f¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000eR\u0011\u0010\u000f\u001a\u00020\u00108F¢\u0006\u0006\u001a\u0004\b\u0011\u0010\u0012R\u0011\u0010\u0013\u001a\u00020\u00148F¢\u0006\u0006\u001a\u0004\b\u0015\u0010\u0016R\u0011\u0010\u0017\u001a\u00020\f¢\u0006\b\n\u0000\u001a\u0004\b\u0018\u0010\u000eR\u001b\u0010\u0019\u001a\u00020\u001a8FX\u0086\u0084\u0002¢\u0006\f\n\u0004\b\u001d\u0010\u001e\u001a\u0004\b\u001b\u0010\u001cR\u001b\u0010\u001f\u001a\u00020\u001a8FX\u0086\u0084\u0002¢\u0006\f\n\u0004\b!\u0010\u001e\u001a\u0004\b \u0010\u001cR\u001b\u0010\"\u001a\u00020\u001a8FX\u0086\u0084\u0002¢\u0006\f\n\u0004\b$\u0010\u001e\u001a\u0004\b#\u0010\u001cR\u001b\u0010%\u001a\u00020\u001a8FX\u0086\u0084\u0002¢\u0006\f\n\u0004\b'\u0010\u001e\u001a\u0004\b&\u0010\u001cR\u001b\u0010(\u001a\u00020\u001a8FX\u0086\u0084\u0002¢\u0006\f\n\u0004\b*\u0010\u001e\u001a\u0004\b)\u0010\u001cR\u001b\u0010+\u001a\u00020\u001a8FX\u0086\u0084\u0002¢\u0006\f\n\u0004\b-\u0010\u001e\u001a\u0004\b,\u0010\u001cR\u001b\u0010.\u001a\u00020\u001a8FX\u0086\u0084\u0002¢\u0006\f\n\u0004\b0\u0010\u001e\u001a\u0004\b/\u0010\u001cR\u001b\u00101\u001a\u00020\u001a8FX\u0086\u0084\u0002¢\u0006\f\n\u0004\b3\u0010\u001e\u001a\u0004\b2\u0010\u001cR\u0011\u00104\u001a\u000205¢\u0006\b\n\u0000\u001a\u0004\b6\u00107R\u0011\u00108\u001a\u000205¢\u0006\b\n\u0000\u001a\u0004\b9\u00107R\u0011\u0010:\u001a\u00020\u0014¢\u0006\b\n\u0000\u001a\u0004\b;\u0010\u0016R\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b<\u0010=¨\u0006?"}, d2 = {"Lorg/jetbrains/kotlin/ir/backend/js/JsCommonCoroutineSymbols;", "", "symbolTable", "Lorg/jetbrains/kotlin/ir/util/SymbolTable;", "module", "Lorg/jetbrains/kotlin/descriptors/ModuleDescriptor;", "context", "Lorg/jetbrains/kotlin/ir/backend/js/JsCommonBackendContext;", "(Lorg/jetbrains/kotlin/ir/util/SymbolTable;Lorg/jetbrains/kotlin/descriptors/ModuleDescriptor;Lorg/jetbrains/kotlin/ir/backend/js/JsCommonBackendContext;)V", "getContext", "()Lorg/jetbrains/kotlin/ir/backend/js/JsCommonBackendContext;", "continuationClass", "Lorg/jetbrains/kotlin/ir/symbols/IrClassSymbol;", "getContinuationClass", "()Lorg/jetbrains/kotlin/ir/symbols/IrClassSymbol;", "coroutineContextProperty", "Lorg/jetbrains/kotlin/descriptors/PropertyDescriptor;", "getCoroutineContextProperty", "()Lorg/jetbrains/kotlin/descriptors/PropertyDescriptor;", "coroutineGetContext", "Lorg/jetbrains/kotlin/ir/symbols/IrSimpleFunctionSymbol;", "getCoroutineGetContext", "()Lorg/jetbrains/kotlin/ir/symbols/IrSimpleFunctionSymbol;", "coroutineImpl", "getCoroutineImpl", "coroutineImplExceptionPropertyGetter", "Lorg/jetbrains/kotlin/ir/declarations/IrSimpleFunction;", "getCoroutineImplExceptionPropertyGetter", "()Lorg/jetbrains/kotlin/ir/declarations/IrSimpleFunction;", "coroutineImplExceptionPropertyGetter$delegate", "Lkotlin/Lazy;", "coroutineImplExceptionPropertySetter", "getCoroutineImplExceptionPropertySetter", "coroutineImplExceptionPropertySetter$delegate", "coroutineImplExceptionStatePropertyGetter", "getCoroutineImplExceptionStatePropertyGetter", "coroutineImplExceptionStatePropertyGetter$delegate", "coroutineImplExceptionStatePropertySetter", "getCoroutineImplExceptionStatePropertySetter", "coroutineImplExceptionStatePropertySetter$delegate", "coroutineImplLabelPropertyGetter", "getCoroutineImplLabelPropertyGetter", "coroutineImplLabelPropertyGetter$delegate", "coroutineImplLabelPropertySetter", "getCoroutineImplLabelPropertySetter", "coroutineImplLabelPropertySetter$delegate", "coroutineImplResultSymbolGetter", "getCoroutineImplResultSymbolGetter", "coroutineImplResultSymbolGetter$delegate", "coroutineImplResultSymbolSetter", "getCoroutineImplResultSymbolSetter", "coroutineImplResultSymbolSetter$delegate", "coroutineIntrinsicsPackage", "Lorg/jetbrains/kotlin/descriptors/PackageViewDescriptor;", "getCoroutineIntrinsicsPackage", "()Lorg/jetbrains/kotlin/descriptors/PackageViewDescriptor;", "coroutinePackage", "getCoroutinePackage", "coroutineSuspendedGetter", "getCoroutineSuspendedGetter", "getModule", "()Lorg/jetbrains/kotlin/descriptors/ModuleDescriptor;", "Companion", "backend.js"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class JsCommonCoroutineSymbols {
    private static final Name CONTINUATION_CONTEXT_GETTER_NAME;
    private static final Name CONTINUATION_CONTEXT_PROPERTY_NAME;
    private static final Name CONTINUATION_NAME;
    private static final Name COROUTINE_CONTEXT_NAME;
    private static final Name COROUTINE_IMPL_NAME;
    private static final FqName COROUTINE_INTRINSICS_PACKAGE_FQNAME;
    private static final FqName COROUTINE_PACKAGE_FQNAME;
    private static final Name COROUTINE_SUSPENDED_NAME;
    private static final Name INTRINSICS_PACKAGE_NAME;
    private final JsCommonBackendContext context;
    private final IrClassSymbol continuationClass;
    private final IrClassSymbol coroutineImpl;

    /* JADX INFO: renamed from: coroutineImplExceptionPropertyGetter$delegate, reason: from kotlin metadata */
    private final Lazy coroutineImplExceptionPropertyGetter;

    /* JADX INFO: renamed from: coroutineImplExceptionPropertySetter$delegate, reason: from kotlin metadata */
    private final Lazy coroutineImplExceptionPropertySetter;

    /* JADX INFO: renamed from: coroutineImplExceptionStatePropertyGetter$delegate, reason: from kotlin metadata */
    private final Lazy coroutineImplExceptionStatePropertyGetter;

    /* JADX INFO: renamed from: coroutineImplExceptionStatePropertySetter$delegate, reason: from kotlin metadata */
    private final Lazy coroutineImplExceptionStatePropertySetter;

    /* JADX INFO: renamed from: coroutineImplLabelPropertyGetter$delegate, reason: from kotlin metadata */
    private final Lazy coroutineImplLabelPropertyGetter;

    /* JADX INFO: renamed from: coroutineImplLabelPropertySetter$delegate, reason: from kotlin metadata */
    private final Lazy coroutineImplLabelPropertySetter;

    /* JADX INFO: renamed from: coroutineImplResultSymbolGetter$delegate, reason: from kotlin metadata */
    private final Lazy coroutineImplResultSymbolGetter;

    /* JADX INFO: renamed from: coroutineImplResultSymbolSetter$delegate, reason: from kotlin metadata */
    private final Lazy coroutineImplResultSymbolSetter;
    private final PackageViewDescriptor coroutineIntrinsicsPackage;
    private final PackageViewDescriptor coroutinePackage;
    private final IrSimpleFunctionSymbol coroutineSuspendedGetter;
    private final ModuleDescriptor module;

    public JsCommonCoroutineSymbols(SymbolTable symbolTable, ModuleDescriptor module, JsCommonBackendContext context) {
        Intrinsics.checkNotNullParameter(symbolTable, "symbolTable");
        Intrinsics.checkNotNullParameter(module, "module");
        Intrinsics.checkNotNullParameter(context, "context");
        this.module = module;
        this.context = context;
        PackageViewDescriptor packageViewDescriptor = module.getPackage(COROUTINE_PACKAGE_FQNAME);
        this.coroutinePackage = packageViewDescriptor;
        PackageViewDescriptor packageViewDescriptor2 = module.getPackage(COROUTINE_INTRINSICS_PACKAGE_FQNAME);
        this.coroutineIntrinsicsPackage = packageViewDescriptor2;
        this.coroutineImpl = symbolTable.getDescriptorExtension().referenceClass(JsCommonBackendContextKt.findClass(packageViewDescriptor.getMemberScope(), COROUTINE_IMPL_NAME));
        this.coroutineImplLabelPropertyGetter = LazyKt.lazy(LazyThreadSafetyMode.NONE, (Function0) new Function0<IrSimpleFunction>() { // from class: org.jetbrains.kotlin.ir.backend.js.JsCommonCoroutineSymbols$coroutineImplLabelPropertyGetter$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final IrSimpleFunction invoke() {
                IrSimpleFunctionSymbol propertyGetter = AdditionalIrUtilsKt.getPropertyGetter(this.this$0.getCoroutineImpl(), "state");
                Intrinsics.checkNotNull(propertyGetter);
                return propertyGetter.getOwner();
            }
        });
        this.coroutineImplLabelPropertySetter = LazyKt.lazy(LazyThreadSafetyMode.NONE, (Function0) new Function0<IrSimpleFunction>() { // from class: org.jetbrains.kotlin.ir.backend.js.JsCommonCoroutineSymbols$coroutineImplLabelPropertySetter$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final IrSimpleFunction invoke() {
                IrSimpleFunctionSymbol propertySetter = AdditionalIrUtilsKt.getPropertySetter(this.this$0.getCoroutineImpl(), "state");
                Intrinsics.checkNotNull(propertySetter);
                return propertySetter.getOwner();
            }
        });
        this.coroutineImplResultSymbolGetter = LazyKt.lazy(LazyThreadSafetyMode.NONE, (Function0) new Function0<IrSimpleFunction>() { // from class: org.jetbrains.kotlin.ir.backend.js.JsCommonCoroutineSymbols$coroutineImplResultSymbolGetter$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final IrSimpleFunction invoke() {
                IrSimpleFunctionSymbol propertyGetter = AdditionalIrUtilsKt.getPropertyGetter(this.this$0.getCoroutineImpl(), "result");
                Intrinsics.checkNotNull(propertyGetter);
                return propertyGetter.getOwner();
            }
        });
        this.coroutineImplResultSymbolSetter = LazyKt.lazy(LazyThreadSafetyMode.NONE, (Function0) new Function0<IrSimpleFunction>() { // from class: org.jetbrains.kotlin.ir.backend.js.JsCommonCoroutineSymbols$coroutineImplResultSymbolSetter$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final IrSimpleFunction invoke() {
                IrSimpleFunctionSymbol propertySetter = AdditionalIrUtilsKt.getPropertySetter(this.this$0.getCoroutineImpl(), "result");
                Intrinsics.checkNotNull(propertySetter);
                return propertySetter.getOwner();
            }
        });
        this.coroutineImplExceptionPropertyGetter = LazyKt.lazy(LazyThreadSafetyMode.NONE, (Function0) new Function0<IrSimpleFunction>() { // from class: org.jetbrains.kotlin.ir.backend.js.JsCommonCoroutineSymbols$coroutineImplExceptionPropertyGetter$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final IrSimpleFunction invoke() {
                IrSimpleFunctionSymbol propertyGetter = AdditionalIrUtilsKt.getPropertyGetter(this.this$0.getCoroutineImpl(), K2JsArgumentConstants.RUNTIME_DIAGNOSTIC_EXCEPTION);
                Intrinsics.checkNotNull(propertyGetter);
                return propertyGetter.getOwner();
            }
        });
        this.coroutineImplExceptionPropertySetter = LazyKt.lazy(LazyThreadSafetyMode.NONE, (Function0) new Function0<IrSimpleFunction>() { // from class: org.jetbrains.kotlin.ir.backend.js.JsCommonCoroutineSymbols$coroutineImplExceptionPropertySetter$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final IrSimpleFunction invoke() {
                IrSimpleFunctionSymbol propertySetter = AdditionalIrUtilsKt.getPropertySetter(this.this$0.getCoroutineImpl(), K2JsArgumentConstants.RUNTIME_DIAGNOSTIC_EXCEPTION);
                Intrinsics.checkNotNull(propertySetter);
                return propertySetter.getOwner();
            }
        });
        this.coroutineImplExceptionStatePropertyGetter = LazyKt.lazy(LazyThreadSafetyMode.NONE, (Function0) new Function0<IrSimpleFunction>() { // from class: org.jetbrains.kotlin.ir.backend.js.JsCommonCoroutineSymbols$coroutineImplExceptionStatePropertyGetter$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final IrSimpleFunction invoke() {
                IrSimpleFunctionSymbol propertyGetter = AdditionalIrUtilsKt.getPropertyGetter(this.this$0.getCoroutineImpl(), "exceptionState");
                Intrinsics.checkNotNull(propertyGetter);
                return propertyGetter.getOwner();
            }
        });
        this.coroutineImplExceptionStatePropertySetter = LazyKt.lazy(LazyThreadSafetyMode.NONE, (Function0) new Function0<IrSimpleFunction>() { // from class: org.jetbrains.kotlin.ir.backend.js.JsCommonCoroutineSymbols$coroutineImplExceptionStatePropertySetter$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final IrSimpleFunction invoke() {
                IrSimpleFunctionSymbol propertySetter = AdditionalIrUtilsKt.getPropertySetter(this.this$0.getCoroutineImpl(), "exceptionState");
                Intrinsics.checkNotNull(propertySetter);
                return propertySetter.getOwner();
            }
        });
        DescriptorSymbolTableExtension descriptorExtension = symbolTable.getDescriptorExtension();
        ClassifierDescriptor contributedClassifier = packageViewDescriptor.getMemberScope().mo7465getContributedClassifier(CONTINUATION_NAME, NoLookupLocation.FROM_BACKEND);
        Intrinsics.checkNotNull(contributedClassifier, "null cannot be cast to non-null type org.jetbrains.kotlin.descriptors.ClassDescriptor");
        this.continuationClass = descriptorExtension.referenceClass((ClassDescriptor) contributedClassifier);
        DescriptorSymbolTableExtension descriptorExtension2 = symbolTable.getDescriptorExtension();
        Collection<? extends PropertyDescriptor> contributedVariables = packageViewDescriptor2.getMemberScope().getContributedVariables(COROUTINE_SUSPENDED_NAME, NoLookupLocation.FROM_BACKEND);
        ArrayList arrayList = new ArrayList();
        for (Object obj : contributedVariables) {
            if (!((PropertyDescriptor) obj).get$isExpect()) {
                arrayList.add(obj);
            }
        }
        PropertyGetterDescriptor getter = ((PropertyDescriptor) CollectionsKt.single((List) arrayList)).getGetter();
        Intrinsics.checkNotNull(getter);
        this.coroutineSuspendedGetter = descriptorExtension2.referenceSimpleFunction(getter);
    }

    public final ModuleDescriptor getModule() {
        return this.module;
    }

    public final JsCommonBackendContext getContext() {
        return this.context;
    }

    public final PackageViewDescriptor getCoroutinePackage() {
        return this.coroutinePackage;
    }

    public final PackageViewDescriptor getCoroutineIntrinsicsPackage() {
        return this.coroutineIntrinsicsPackage;
    }

    public final IrClassSymbol getCoroutineImpl() {
        return this.coroutineImpl;
    }

    public final IrSimpleFunction getCoroutineImplLabelPropertyGetter() {
        return (IrSimpleFunction) this.coroutineImplLabelPropertyGetter.getValue();
    }

    public final IrSimpleFunction getCoroutineImplLabelPropertySetter() {
        return (IrSimpleFunction) this.coroutineImplLabelPropertySetter.getValue();
    }

    public final IrSimpleFunction getCoroutineImplResultSymbolGetter() {
        return (IrSimpleFunction) this.coroutineImplResultSymbolGetter.getValue();
    }

    public final IrSimpleFunction getCoroutineImplResultSymbolSetter() {
        return (IrSimpleFunction) this.coroutineImplResultSymbolSetter.getValue();
    }

    public final IrSimpleFunction getCoroutineImplExceptionPropertyGetter() {
        return (IrSimpleFunction) this.coroutineImplExceptionPropertyGetter.getValue();
    }

    public final IrSimpleFunction getCoroutineImplExceptionPropertySetter() {
        return (IrSimpleFunction) this.coroutineImplExceptionPropertySetter.getValue();
    }

    public final IrSimpleFunction getCoroutineImplExceptionStatePropertyGetter() {
        return (IrSimpleFunction) this.coroutineImplExceptionStatePropertyGetter.getValue();
    }

    public final IrSimpleFunction getCoroutineImplExceptionStatePropertySetter() {
        return (IrSimpleFunction) this.coroutineImplExceptionStatePropertySetter.getValue();
    }

    public final IrClassSymbol getContinuationClass() {
        return this.continuationClass;
    }

    public final IrSimpleFunctionSymbol getCoroutineSuspendedGetter() {
        return this.coroutineSuspendedGetter;
    }

    public final IrSimpleFunctionSymbol getCoroutineGetContext() {
        List<IrDeclaration> declarations = this.continuationClass.getOwner().getDeclarations();
        ArrayList arrayList = new ArrayList();
        for (Object obj : declarations) {
            if (obj instanceof IrSimpleFunction) {
                arrayList.add(obj);
            }
        }
        ArrayList arrayList2 = new ArrayList();
        for (Object obj2 : arrayList) {
            if (Intrinsics.areEqual(((IrSimpleFunction) obj2).getName(), CONTINUATION_CONTEXT_GETTER_NAME)) {
                arrayList2.add(obj2);
            }
        }
        IrSimpleFunction getter = (IrSimpleFunction) CollectionUtilKt.atMostOne(arrayList2);
        if (getter == null) {
            List<IrDeclaration> declarations2 = this.continuationClass.getOwner().getDeclarations();
            ArrayList arrayList3 = new ArrayList();
            for (Object obj3 : declarations2) {
                if (obj3 instanceof IrProperty) {
                    arrayList3.add(obj3);
                }
            }
            ArrayList arrayList4 = new ArrayList();
            for (Object obj4 : arrayList3) {
                if (Intrinsics.areEqual(((IrProperty) obj4).getName(), CONTINUATION_CONTEXT_PROPERTY_NAME)) {
                    arrayList4.add(obj4);
                }
            }
            IrProperty irProperty = (IrProperty) CollectionUtilKt.atMostOne(arrayList4);
            getter = irProperty != null ? irProperty.getGetter() : null;
            Intrinsics.checkNotNull(getter);
        }
        return getter.getSymbol();
    }

    public final PropertyDescriptor getCoroutineContextProperty() {
        return (PropertyDescriptor) CollectionsKt.single(this.coroutinePackage.getMemberScope().getContributedVariables(COROUTINE_CONTEXT_NAME, NoLookupLocation.FROM_BACKEND));
    }

    static {
        Name nameIdentifier = Name.identifier("intrinsics");
        Intrinsics.checkNotNullExpressionValue(nameIdentifier, "identifier(...)");
        INTRINSICS_PACKAGE_NAME = nameIdentifier;
        Name nameIdentifier2 = Name.identifier("COROUTINE_SUSPENDED");
        Intrinsics.checkNotNullExpressionValue(nameIdentifier2, "identifier(...)");
        COROUTINE_SUSPENDED_NAME = nameIdentifier2;
        Name nameIdentifier3 = Name.identifier("coroutineContext");
        Intrinsics.checkNotNullExpressionValue(nameIdentifier3, "identifier(...)");
        COROUTINE_CONTEXT_NAME = nameIdentifier3;
        Name nameIdentifier4 = Name.identifier("CoroutineImpl");
        Intrinsics.checkNotNullExpressionValue(nameIdentifier4, "identifier(...)");
        COROUTINE_IMPL_NAME = nameIdentifier4;
        Name nameIdentifier5 = Name.identifier("Continuation");
        Intrinsics.checkNotNullExpressionValue(nameIdentifier5, "identifier(...)");
        CONTINUATION_NAME = nameIdentifier5;
        Name nameSpecial = Name.special("<get-context>");
        Intrinsics.checkNotNullExpressionValue(nameSpecial, "special(...)");
        CONTINUATION_CONTEXT_GETTER_NAME = nameSpecial;
        Name nameIdentifier6 = Name.identifier("context");
        Intrinsics.checkNotNullExpressionValue(nameIdentifier6, "identifier(...)");
        CONTINUATION_CONTEXT_PROPERTY_NAME = nameIdentifier6;
        FqName fqNameFromSegments = FqName.fromSegments(CollectionsKt.listOf((Object[]) new String[]{IncrementalJvmCacheKt.KOTLIN_CACHE_DIRECTORY_NAME, "coroutines"}));
        Intrinsics.checkNotNullExpressionValue(fqNameFromSegments, "fromSegments(...)");
        COROUTINE_PACKAGE_FQNAME = fqNameFromSegments;
        FqName fqNameChild = fqNameFromSegments.child(nameIdentifier);
        Intrinsics.checkNotNullExpressionValue(fqNameChild, "child(...)");
        COROUTINE_INTRINSICS_PACKAGE_FQNAME = fqNameChild;
    }
}
