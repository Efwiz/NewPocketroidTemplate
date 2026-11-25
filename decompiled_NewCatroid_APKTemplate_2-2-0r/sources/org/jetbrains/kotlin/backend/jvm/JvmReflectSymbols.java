package org.jetbrains.kotlin.backend.jvm;

import java.util.Collection;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import net.bytebuddy.implementation.auxiliary.TypeProxy;
import org.apache.commons.beanutils.FluentPropertyBeanIntrospector;
import org.jetbrains.kotlin.descriptors.ClassKind;
import org.jetbrains.kotlin.descriptors.Modality;
import org.jetbrains.kotlin.ir.IrBuiltIns;
import org.jetbrains.kotlin.ir.builders.declarations.DeclarationBuildersKt;
import org.jetbrains.kotlin.ir.builders.declarations.IrClassBuilder;
import org.jetbrains.kotlin.ir.builders.declarations.IrValueParameterBuilder;
import org.jetbrains.kotlin.ir.declarations.IrClass;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationOrigin;
import org.jetbrains.kotlin.ir.declarations.IrFactory;
import org.jetbrains.kotlin.ir.declarations.IrPackageFragment;
import org.jetbrains.kotlin.ir.declarations.IrSimpleFunction;
import org.jetbrains.kotlin.ir.declarations.IrValueParameter;
import org.jetbrains.kotlin.ir.declarations.impl.IrExternalPackageFragmentImpl;
import org.jetbrains.kotlin.ir.symbols.IrClassSymbol;
import org.jetbrains.kotlin.ir.symbols.IrSimpleFunctionSymbol;
import org.jetbrains.kotlin.ir.types.IrTypesKt;
import org.jetbrains.kotlin.ir.util.IrUtilsKt;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.name.Name;

/* JADX INFO: compiled from: JvmReflectSymbols.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000P\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0015\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0000\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J:\u0010+\u001a\u00020\u00142\u0006\u0010,\u001a\u00020\u00122\b\b\u0002\u0010-\u001a\u00020.2\b\b\u0002\u0010/\u001a\u0002002\u0014\b\u0002\u00101\u001a\u000e\u0012\u0004\u0012\u000203\u0012\u0004\u0012\u00020402H\u0002R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006R\u0011\u0010\u0007\u001a\u00020\b¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u0011\u0010\u000b\u001a\u00020\b¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\nR\u0011\u0010\r\u001a\u00020\b¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\nR\u000e\u0010\u000f\u001a\u00020\u0010X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0011\u001a\u00020\u0012X\u0082\u0004¢\u0006\u0002\n\u0000R\u0011\u0010\u0013\u001a\u00020\u0014¢\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\u0016R\u0011\u0010\u0017\u001a\u00020\b¢\u0006\b\n\u0000\u001a\u0004\b\u0018\u0010\nR\u0011\u0010\u0019\u001a\u00020\b¢\u0006\b\n\u0000\u001a\u0004\b\u001a\u0010\nR\u0011\u0010\u001b\u001a\u00020\u0014¢\u0006\b\n\u0000\u001a\u0004\b\u001c\u0010\u0016R\u0011\u0010\u001d\u001a\u00020\b¢\u0006\b\n\u0000\u001a\u0004\b\u001e\u0010\nR\u0011\u0010\u001f\u001a\u00020\b¢\u0006\b\n\u0000\u001a\u0004\b \u0010\nR\u0011\u0010!\u001a\u00020\b¢\u0006\b\n\u0000\u001a\u0004\b\"\u0010\nR\u0011\u0010#\u001a\u00020\u0014¢\u0006\b\n\u0000\u001a\u0004\b$\u0010\u0016R\u0011\u0010%\u001a\u00020\b¢\u0006\b\n\u0000\u001a\u0004\b&\u0010\nR\u0011\u0010'\u001a\u00020\b¢\u0006\b\n\u0000\u001a\u0004\b(\u0010\nR\u000e\u0010)\u001a\u00020*X\u0082\u0004¢\u0006\u0002\n\u0000¨\u00065"}, d2 = {"Lorg/jetbrains/kotlin/backend/jvm/JvmReflectSymbols;", "", "context", "Lorg/jetbrains/kotlin/backend/jvm/JvmBackendContext;", "(Lorg/jetbrains/kotlin/backend/jvm/JvmBackendContext;)V", "getContext", "()Lorg/jetbrains/kotlin/backend/jvm/JvmBackendContext;", TypeProxy.SilentConstruction.Appender.GET_DECLARED_CONSTRUCTOR_METHOD_NAME, "Lorg/jetbrains/kotlin/ir/symbols/IrSimpleFunctionSymbol;", "getGetDeclaredConstructor", "()Lorg/jetbrains/kotlin/ir/symbols/IrSimpleFunctionSymbol;", "getDeclaredField", "getGetDeclaredField", "getDeclaredMethod", "getGetDeclaredMethod", "irBuiltIns", "Lorg/jetbrains/kotlin/ir/IrBuiltIns;", "javaLangReflect", "Lorg/jetbrains/kotlin/name/FqName;", "javaLangReflectConstructor", "Lorg/jetbrains/kotlin/ir/symbols/IrClassSymbol;", "getJavaLangReflectConstructor", "()Lorg/jetbrains/kotlin/ir/symbols/IrClassSymbol;", "javaLangReflectConstructorNewInstance", "getJavaLangReflectConstructorNewInstance", "javaLangReflectConstructorSetAccessible", "getJavaLangReflectConstructorSetAccessible", "javaLangReflectField", "getJavaLangReflectField", "javaLangReflectFieldGet", "getJavaLangReflectFieldGet", "javaLangReflectFieldSet", "getJavaLangReflectFieldSet", "javaLangReflectFieldSetAccessible", "getJavaLangReflectFieldSetAccessible", "javaLangReflectMethod", "getJavaLangReflectMethod", "javaLangReflectMethodInvoke", "getJavaLangReflectMethodInvoke", "javaLangReflectMethodSetAccessible", "getJavaLangReflectMethodSetAccessible", "javaLangReflectPackage", "Lorg/jetbrains/kotlin/ir/declarations/IrPackageFragment;", "createJavaLangReflectClass", "fqName", "classKind", "Lorg/jetbrains/kotlin/descriptors/ClassKind;", "classModality", "Lorg/jetbrains/kotlin/descriptors/Modality;", "block", "Lkotlin/Function1;", "Lorg/jetbrains/kotlin/ir/declarations/IrClass;", "", "backend.jvm"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class JvmReflectSymbols {
    private final JvmBackendContext context;
    private final IrSimpleFunctionSymbol getDeclaredConstructor;
    private final IrSimpleFunctionSymbol getDeclaredField;
    private final IrSimpleFunctionSymbol getDeclaredMethod;
    private final IrBuiltIns irBuiltIns;
    private final FqName javaLangReflect;
    private final IrClassSymbol javaLangReflectConstructor;
    private final IrSimpleFunctionSymbol javaLangReflectConstructorNewInstance;
    private final IrSimpleFunctionSymbol javaLangReflectConstructorSetAccessible;
    private final IrClassSymbol javaLangReflectField;
    private final IrSimpleFunctionSymbol javaLangReflectFieldGet;
    private final IrSimpleFunctionSymbol javaLangReflectFieldSet;
    private final IrSimpleFunctionSymbol javaLangReflectFieldSetAccessible;
    private final IrClassSymbol javaLangReflectMethod;
    private final IrSimpleFunctionSymbol javaLangReflectMethodInvoke;
    private final IrSimpleFunctionSymbol javaLangReflectMethodSetAccessible;
    private final IrPackageFragment javaLangReflectPackage;

    public JvmReflectSymbols(JvmBackendContext context) {
        Intrinsics.checkNotNullParameter(context, "context");
        this.context = context;
        IrBuiltIns irBuiltIns = context.getIrBuiltIns();
        this.irBuiltIns = irBuiltIns;
        FqName fqName = new FqName("java.lang.reflect");
        this.javaLangReflect = fqName;
        this.javaLangReflectPackage = IrExternalPackageFragmentImpl.INSTANCE.createEmptyExternalPackageFragment(context.getState().getModule(), fqName);
        IrClassSymbol irClassSymbolCreateJavaLangReflectClass$default = createJavaLangReflectClass$default(this, new FqName("java.lang.reflect.Field"), null, null, new Function1<IrClass, Unit>() { // from class: org.jetbrains.kotlin.backend.jvm.JvmReflectSymbols$javaLangReflectField$1
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(IrClass irClass) {
                invoke2(irClass);
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(IrClass klass) {
                Intrinsics.checkNotNullParameter(klass, "klass");
                DeclarationBuildersKt.addValueParameter$default(DeclarationBuildersKt.addFunction$default(klass, "setAccessible", this.this$0.irBuiltIns.getUnitType(), null, null, false, false, false, false, null, 0, 0, 2044, null), "isAccessible", this.this$0.irBuiltIns.getBooleanType(), (IrDeclarationOrigin) null, 4, (Object) null);
                DeclarationBuildersKt.addValueParameter$default(DeclarationBuildersKt.addFunction$default(klass, "get", this.this$0.irBuiltIns.getAnyNType(), null, null, false, false, false, false, null, 0, 0, 2044, null), "receiver", this.this$0.irBuiltIns.getAnyNType(), (IrDeclarationOrigin) null, 4, (Object) null);
                IrSimpleFunction irSimpleFunctionAddFunction$default = DeclarationBuildersKt.addFunction$default(klass, FluentPropertyBeanIntrospector.DEFAULT_WRITE_METHOD_PREFIX, this.this$0.irBuiltIns.getUnitType(), null, null, false, false, false, false, null, 0, 0, 2044, null);
                JvmReflectSymbols jvmReflectSymbols = this.this$0;
                IrSimpleFunction irSimpleFunction = irSimpleFunctionAddFunction$default;
                DeclarationBuildersKt.addValueParameter$default(irSimpleFunction, "receiver", jvmReflectSymbols.irBuiltIns.getAnyNType(), (IrDeclarationOrigin) null, 4, (Object) null);
                DeclarationBuildersKt.addValueParameter$default(irSimpleFunction, "value", jvmReflectSymbols.irBuiltIns.getAnyNType(), (IrDeclarationOrigin) null, 4, (Object) null);
            }
        }, 6, null);
        this.javaLangReflectField = irClassSymbolCreateJavaLangReflectClass$default;
        IrClassSymbol irClassSymbolCreateJavaLangReflectClass$default2 = createJavaLangReflectClass$default(this, new FqName("java.lang.reflect.Method"), null, null, new Function1<IrClass, Unit>() { // from class: org.jetbrains.kotlin.backend.jvm.JvmReflectSymbols$javaLangReflectMethod$1
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(IrClass irClass) {
                invoke2(irClass);
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(IrClass klass) {
                Intrinsics.checkNotNullParameter(klass, "klass");
                DeclarationBuildersKt.addValueParameter$default(DeclarationBuildersKt.addFunction$default(klass, "setAccessible", this.this$0.irBuiltIns.getUnitType(), null, null, false, false, false, false, null, 0, 0, 2044, null), "isAccessible", this.this$0.irBuiltIns.getBooleanType(), (IrDeclarationOrigin) null, 4, (Object) null);
                IrSimpleFunction irSimpleFunctionAddFunction$default = DeclarationBuildersKt.addFunction$default(klass, "invoke", this.this$0.irBuiltIns.getAnyNType(), null, null, false, false, false, false, null, 0, 0, 2044, null);
                JvmReflectSymbols jvmReflectSymbols = this.this$0;
                IrSimpleFunction irSimpleFunction = irSimpleFunctionAddFunction$default;
                DeclarationBuildersKt.addValueParameter$default(irSimpleFunction, "receiver", jvmReflectSymbols.irBuiltIns.getAnyNType(), (IrDeclarationOrigin) null, 4, (Object) null);
                IrValueParameterBuilder irValueParameterBuilder = new IrValueParameterBuilder();
                Name nameIdentifier = Name.identifier("args");
                Intrinsics.checkNotNullExpressionValue(nameIdentifier, "identifier(...)");
                irValueParameterBuilder.setName(nameIdentifier);
                irValueParameterBuilder.setType(IrTypesKt.typeWith(jvmReflectSymbols.irBuiltIns.getArrayClass(), jvmReflectSymbols.irBuiltIns.getAnyNType()));
                irValueParameterBuilder.setVarargElementType(jvmReflectSymbols.irBuiltIns.getAnyNType());
                if (irValueParameterBuilder.getIndex() == -1) {
                    irValueParameterBuilder.setIndex(irSimpleFunction.getValueParameters().size());
                }
                irSimpleFunction.setValueParameters(CollectionsKt.plus((Collection<? extends IrValueParameter>) irSimpleFunction.getValueParameters(), DeclarationBuildersKt.buildValueParameter(irSimpleFunction.getFactory(), irValueParameterBuilder, irSimpleFunction)));
            }
        }, 6, null);
        this.javaLangReflectMethod = irClassSymbolCreateJavaLangReflectClass$default2;
        IrClassSymbol irClassSymbolCreateJavaLangReflectClass$default3 = createJavaLangReflectClass$default(this, new FqName("java.lang.reflect.Constructor"), null, null, new Function1<IrClass, Unit>() { // from class: org.jetbrains.kotlin.backend.jvm.JvmReflectSymbols$javaLangReflectConstructor$1
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(IrClass irClass) {
                invoke2(irClass);
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(IrClass klass) {
                Intrinsics.checkNotNullParameter(klass, "klass");
                DeclarationBuildersKt.addValueParameter$default(DeclarationBuildersKt.addFunction$default(klass, "setAccessible", this.this$0.irBuiltIns.getUnitType(), null, null, false, false, false, false, null, 0, 0, 2044, null), "isAccessible", this.this$0.irBuiltIns.getBooleanType(), (IrDeclarationOrigin) null, 4, (Object) null);
                IrSimpleFunction irSimpleFunctionAddFunction$default = DeclarationBuildersKt.addFunction$default(klass, TypeProxy.SilentConstruction.Appender.NEW_INSTANCE_METHOD_NAME, this.this$0.irBuiltIns.getAnyNType(), null, null, false, false, false, false, null, 0, 0, 2044, null);
                JvmReflectSymbols jvmReflectSymbols = this.this$0;
                IrSimpleFunction irSimpleFunction = irSimpleFunctionAddFunction$default;
                IrValueParameterBuilder irValueParameterBuilder = new IrValueParameterBuilder();
                Name nameIdentifier = Name.identifier("args");
                Intrinsics.checkNotNullExpressionValue(nameIdentifier, "identifier(...)");
                irValueParameterBuilder.setName(nameIdentifier);
                irValueParameterBuilder.setType(IrTypesKt.typeWith(jvmReflectSymbols.irBuiltIns.getArrayClass(), jvmReflectSymbols.irBuiltIns.getAnyNType()));
                irValueParameterBuilder.setVarargElementType(jvmReflectSymbols.irBuiltIns.getAnyNType());
                if (irValueParameterBuilder.getIndex() == -1) {
                    irValueParameterBuilder.setIndex(irSimpleFunction.getValueParameters().size());
                }
                irSimpleFunction.setValueParameters(CollectionsKt.plus((Collection<? extends IrValueParameter>) irSimpleFunction.getValueParameters(), DeclarationBuildersKt.buildValueParameter(irSimpleFunction.getFactory(), irValueParameterBuilder, irSimpleFunction)));
            }
        }, 6, null);
        this.javaLangReflectConstructor = irClassSymbolCreateJavaLangReflectClass$default3;
        IrClass owner = context.getIr().getSymbols().getJavaLangClass().getOwner();
        IrSimpleFunction irSimpleFunctionAddFunction$default = DeclarationBuildersKt.addFunction$default(owner, "getDeclaredMethod", IrTypesKt.makeNullable(IrTypesKt.getDefaultType(irClassSymbolCreateJavaLangReflectClass$default2)), null, null, false, false, false, false, null, 0, 0, 2044, null);
        DeclarationBuildersKt.addValueParameter$default(irSimpleFunctionAddFunction$default, "methodName", IrTypesKt.makeNullable(irBuiltIns.getStringType()), (IrDeclarationOrigin) null, 4, (Object) null);
        IrValueParameterBuilder irValueParameterBuilder = new IrValueParameterBuilder();
        Name nameIdentifier = Name.identifier("args");
        Intrinsics.checkNotNullExpressionValue(nameIdentifier, "identifier(...)");
        irValueParameterBuilder.setName(nameIdentifier);
        irValueParameterBuilder.setType(IrTypesKt.makeNullable(IrTypesKt.typeWith(irBuiltIns.getArrayClass(), IrUtilsKt.getDefaultType(owner))));
        irValueParameterBuilder.setVarargElementType(IrUtilsKt.getDefaultType(owner));
        if (irValueParameterBuilder.getIndex() == -1) {
            irValueParameterBuilder.setIndex(irSimpleFunctionAddFunction$default.getValueParameters().size());
        }
        irSimpleFunctionAddFunction$default.setValueParameters(CollectionsKt.plus((Collection<? extends IrValueParameter>) irSimpleFunctionAddFunction$default.getValueParameters(), DeclarationBuildersKt.buildValueParameter(irSimpleFunctionAddFunction$default.getFactory(), irValueParameterBuilder, irSimpleFunctionAddFunction$default)));
        DeclarationBuildersKt.addValueParameter$default(DeclarationBuildersKt.addFunction$default(owner, "getDeclaredField", IrTypesKt.getDefaultType(irClassSymbolCreateJavaLangReflectClass$default), null, null, false, false, false, false, null, 0, 0, 2044, null), "fieldName", irBuiltIns.getStringType(), (IrDeclarationOrigin) null, 4, (Object) null);
        IrSimpleFunction irSimpleFunctionAddFunction$default2 = DeclarationBuildersKt.addFunction$default(owner, TypeProxy.SilentConstruction.Appender.GET_DECLARED_CONSTRUCTOR_METHOD_NAME, IrTypesKt.makeNullable(IrTypesKt.getDefaultType(irClassSymbolCreateJavaLangReflectClass$default3)), null, null, false, false, false, false, null, 0, 0, 2044, null);
        IrValueParameterBuilder irValueParameterBuilder2 = new IrValueParameterBuilder();
        Name nameIdentifier2 = Name.identifier("args");
        Intrinsics.checkNotNullExpressionValue(nameIdentifier2, "identifier(...)");
        irValueParameterBuilder2.setName(nameIdentifier2);
        irValueParameterBuilder2.setType(IrTypesKt.makeNullable(IrTypesKt.typeWith(irBuiltIns.getArrayClass(), IrUtilsKt.getDefaultType(owner))));
        irValueParameterBuilder2.setVarargElementType(IrUtilsKt.getDefaultType(owner));
        if (irValueParameterBuilder2.getIndex() == -1) {
            irValueParameterBuilder2.setIndex(irSimpleFunctionAddFunction$default2.getValueParameters().size());
        }
        irSimpleFunctionAddFunction$default2.setValueParameters(CollectionsKt.plus((Collection<? extends IrValueParameter>) irSimpleFunctionAddFunction$default2.getValueParameters(), DeclarationBuildersKt.buildValueParameter(irSimpleFunctionAddFunction$default2.getFactory(), irValueParameterBuilder2, irSimpleFunctionAddFunction$default2)));
        this.javaLangReflectFieldSetAccessible = JvmSymbolsKt.functionByName(irClassSymbolCreateJavaLangReflectClass$default, "setAccessible");
        this.javaLangReflectMethodSetAccessible = JvmSymbolsKt.functionByName(irClassSymbolCreateJavaLangReflectClass$default2, "setAccessible");
        this.javaLangReflectConstructorSetAccessible = JvmSymbolsKt.functionByName(irClassSymbolCreateJavaLangReflectClass$default3, "setAccessible");
        this.getDeclaredField = JvmSymbolsKt.functionByName(context.getIr().getSymbols().getJavaLangClass(), "getDeclaredField");
        this.getDeclaredMethod = JvmSymbolsKt.functionByName(context.getIr().getSymbols().getJavaLangClass(), "getDeclaredMethod");
        this.getDeclaredConstructor = JvmSymbolsKt.functionByName(context.getIr().getSymbols().getJavaLangClass(), TypeProxy.SilentConstruction.Appender.GET_DECLARED_CONSTRUCTOR_METHOD_NAME);
        this.javaLangReflectFieldGet = JvmSymbolsKt.functionByName(irClassSymbolCreateJavaLangReflectClass$default, "get");
        this.javaLangReflectFieldSet = JvmSymbolsKt.functionByName(irClassSymbolCreateJavaLangReflectClass$default, FluentPropertyBeanIntrospector.DEFAULT_WRITE_METHOD_PREFIX);
        this.javaLangReflectMethodInvoke = JvmSymbolsKt.functionByName(irClassSymbolCreateJavaLangReflectClass$default2, "invoke");
        this.javaLangReflectConstructorNewInstance = JvmSymbolsKt.functionByName(irClassSymbolCreateJavaLangReflectClass$default3, TypeProxy.SilentConstruction.Appender.NEW_INSTANCE_METHOD_NAME);
    }

    public final JvmBackendContext getContext() {
        return this.context;
    }

    public final IrClassSymbol getJavaLangReflectField() {
        return this.javaLangReflectField;
    }

    public final IrClassSymbol getJavaLangReflectMethod() {
        return this.javaLangReflectMethod;
    }

    public final IrClassSymbol getJavaLangReflectConstructor() {
        return this.javaLangReflectConstructor;
    }

    public final IrSimpleFunctionSymbol getJavaLangReflectFieldSetAccessible() {
        return this.javaLangReflectFieldSetAccessible;
    }

    public final IrSimpleFunctionSymbol getJavaLangReflectMethodSetAccessible() {
        return this.javaLangReflectMethodSetAccessible;
    }

    public final IrSimpleFunctionSymbol getJavaLangReflectConstructorSetAccessible() {
        return this.javaLangReflectConstructorSetAccessible;
    }

    public final IrSimpleFunctionSymbol getGetDeclaredField() {
        return this.getDeclaredField;
    }

    public final IrSimpleFunctionSymbol getGetDeclaredMethod() {
        return this.getDeclaredMethod;
    }

    public final IrSimpleFunctionSymbol getGetDeclaredConstructor() {
        return this.getDeclaredConstructor;
    }

    public final IrSimpleFunctionSymbol getJavaLangReflectFieldGet() {
        return this.javaLangReflectFieldGet;
    }

    public final IrSimpleFunctionSymbol getJavaLangReflectFieldSet() {
        return this.javaLangReflectFieldSet;
    }

    public final IrSimpleFunctionSymbol getJavaLangReflectMethodInvoke() {
        return this.javaLangReflectMethodInvoke;
    }

    public final IrSimpleFunctionSymbol getJavaLangReflectConstructorNewInstance() {
        return this.javaLangReflectConstructorNewInstance;
    }

    /* JADX WARN: Multi-variable type inference failed */
    static /* synthetic */ IrClassSymbol createJavaLangReflectClass$default(JvmReflectSymbols jvmReflectSymbols, FqName fqName, ClassKind classKind, Modality modality, Function1 function1, int i, Object obj) {
        if ((i & 2) != 0) {
            classKind = ClassKind.CLASS;
        }
        if ((i & 4) != 0) {
            modality = Modality.FINAL;
        }
        if ((i & 8) != 0) {
            function1 = new Function1<IrClass, Unit>() { // from class: org.jetbrains.kotlin.backend.jvm.JvmReflectSymbols.createJavaLangReflectClass.1
                /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
                public final void invoke2(IrClass it) {
                    Intrinsics.checkNotNullParameter(it, "it");
                }

                @Override // kotlin.jvm.functions.Function1
                public /* bridge */ /* synthetic */ Unit invoke(IrClass irClass) {
                    invoke2(irClass);
                    return Unit.INSTANCE;
                }
            };
        }
        return jvmReflectSymbols.createJavaLangReflectClass(fqName, classKind, modality, function1);
    }

    private final IrClassSymbol createJavaLangReflectClass(FqName fqName, ClassKind classKind, Modality classModality, Function1<? super IrClass, Unit> block) {
        IrFactory irFactory = this.context.getIrFactory();
        IrClassBuilder irClassBuilder = new IrClassBuilder();
        Name nameShortName = fqName.shortName();
        Intrinsics.checkNotNullExpressionValue(nameShortName, "shortName(...)");
        irClassBuilder.setName(nameShortName);
        irClassBuilder.setKind(classKind);
        irClassBuilder.setModality(classModality);
        IrClass irClassBuildClass = DeclarationBuildersKt.buildClass(irFactory, irClassBuilder);
        irClassBuildClass.setParent(this.javaLangReflectPackage);
        IrUtilsKt.createImplicitParameterDeclarationWithWrappedDescriptor(irClassBuildClass);
        block.invoke(irClassBuildClass);
        return irClassBuildClass.getSymbol();
    }
}
